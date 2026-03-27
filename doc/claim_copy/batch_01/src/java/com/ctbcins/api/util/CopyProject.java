package com.ctbcins.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CopyProject {

    // ===== 設定區 =====
	private static final String  folderName = "claim";
	private static final String SOURCE_DIR = "D:\\workspace\\workSpaceBranch\\"+folderName;
    private static final String TARGET_DIR = "D:\\GitHub\\easyTest\\doc\\"+folderName+"_copy";
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<String>(Arrays.asList(
        ".java", ".jsp", ".js", ".xml", ".properties"
    ));
    private static final long BATCH_MAX_TOKENS = 160000L; // 每批上限（保守 80%）
    // ==================

    // 紀錄每個檔案與其 token 數
    private static final List<FileEntry> fileEntries = new ArrayList<FileEntry>();
    private static int skippedCount = 0;

    public static void main(String[] args) {
        File source = new File(SOURCE_DIR);

        if (!source.exists() || !source.isDirectory()) {
            System.out.println("[錯誤] 來源目錄不存在：" + SOURCE_DIR);
            return;
        }

        System.out.println("============================");
        System.out.println("掃描來源目錄：" + SOURCE_DIR);
        System.out.println("============================");

        // 第一步：掃描所有符合條件的檔案並估算 token
        scanDirectory(source, source);

        System.out.println("----------------------------");
        System.out.println("掃描完成，共 " + fileEntries.size() + " 個檔案，略過 " + skippedCount + " 個");
        System.out.println("============================");

        // 第二步：分批複製
        doBatchCopy();
    }

    private static void doBatchCopy() {
        int batchNo       = 1;
        long batchTokens  = 0L;
        int  batchFiles   = 0;
        int  totalCopied  = 0;

        for (int i = 0; i < fileEntries.size(); i++) {
            FileEntry entry = fileEntries.get(i);

            // 超過本批上限且本批已有檔案 → 換下一批
            if (batchTokens + entry.tokens > BATCH_MAX_TOKENS && batchFiles > 0) {
                printBatchSummary(batchNo, batchTokens, batchFiles);
                batchNo++;
                batchTokens = 0L;
                batchFiles  = 0;
            }

            // 決定目標路徑：TARGET_DIR/batch_01/原始相對路徑
            String batchFolder = TARGET_DIR + File.separator + String.format("batch_%02d", batchNo);
            File destFile = new File(batchFolder + entry.relativePath);
            destFile.getParentFile().mkdirs();

            try {
                copyFile(entry.file, destFile);
                batchTokens += entry.tokens;
                batchFiles++;
                totalCopied++;
                System.out.println(String.format("[batch_%02d] +%6d tokens (累計:%6d)  %s",
                        batchNo, entry.tokens, batchTokens, entry.relativePath));
            } catch (IOException e) {
                System.out.println("[失敗] " + entry.relativePath + " -> " + e.getMessage());
            }
        }

        // 最後一批
        if (batchFiles > 0) {
            printBatchSummary(batchNo, batchTokens, batchFiles);
        }

        System.out.println("============================");
        System.out.println("全部完成！共複製 " + totalCopied + " 個檔案，分成 " + batchNo + " 批");
        System.out.println("輸出目錄：" + TARGET_DIR);
        System.out.println("============================");
        System.out.println("上傳建議：");
        for (int b = 1; b <= batchNo; b++) {
            System.out.println("  第 " + b + " 批 → 上傳 batch_" + String.format("%02d", b) + " 資料夾內所有檔案");
        }
    }

    private static void printBatchSummary(int batchNo, long tokens, int files) {
        long percent = (tokens * 100) / BATCH_MAX_TOKENS;
        System.out.println("----------------------------");
        System.out.println(String.format("batch_%02d 完成：%d 個檔案，%d tokens（%d%%）",
                batchNo, files, tokens, percent));
        System.out.println("----------------------------");
    }

    private static void scanDirectory(File current, File sourceRoot) {
        File[] files = current.listFiles();
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            if (file.getName().startsWith(".")) {
                continue;
            }

            if (file.isDirectory()) {
                scanDirectory(file, sourceRoot);
            } else {
                String ext = getExtension(file.getName());
                if (ALLOWED_EXTENSIONS.contains(ext)) {
                    String relativePath = file.getAbsolutePath()
                            .substring(sourceRoot.getAbsolutePath().length());
                    try {
                        long tokens = estimateTokens(file);
                        fileEntries.add(new FileEntry(file, relativePath, tokens));
                        System.out.println("[掃描] " + relativePath + "  (" + tokens + " tokens)");
                    } catch (IOException e) {
                        System.out.println("[掃描失敗] " + relativePath + " -> " + e.getMessage());
                    }
                } else {
                    skippedCount++;
                }
            }
        }
    }

    private static long estimateTokens(File file) throws IOException {
        BufferedReader reader = null;
        long charCount = 0L;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (isCJK(c)) {
                        charCount += 2;
                    } else {
                        charCount += 1;
                    }
                }
            }
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { }
            }
        }
        return charCount / 4;
    }

    private static boolean isCJK(char c) {
        return (c >= '\u4E00' && c <= '\u9FFF')
            || (c >= '\u3400' && c <= '\u4DBF')
            || (c >= '\uF900' && c <= '\uFAFF');
    }

    private static void copyFile(File src, File dest) throws IOException {
        FileInputStream  fis = null;
        FileOutputStream fos = null;
        FileChannel      in  = null;
        FileChannel      out = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            in  = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
        } finally {
            if (in  != null) try { in.close();  } catch (IOException e) { }
            if (out != null) try { out.close(); } catch (IOException e) { }
            if (fis != null) try { fis.close(); } catch (IOException e) { }
            if (fos != null) try { fos.close(); } catch (IOException e) { }
        }
    }

    private static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex).toLowerCase();
    }

    // 內部類別：紀錄檔案資訊
    private static class FileEntry {
        File   file;
        String relativePath;
        long   tokens;

        FileEntry(File file, String relativePath, long tokens) {
            this.file         = file;
            this.relativePath = relativePath;
            this.tokens       = tokens;
        }
    }
}