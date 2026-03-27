package com.ctbcins.api.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TableFormatter {
    public static void main(String[] args) {
        try {
            // 讀取data.txt文件
        	String inputFile = "D:\\data.txt";
            String outputFile = "D:\\data.txt";
            
            List<String> lines = readFile(inputFile);
            if (lines.isEmpty()) {
                System.out.println("錯誤：data.txt文件為空或不存在");
                return;
            }
            
            // 格式化表格
            String formattedTable = formatTable(lines);
            
            // 寫入result.txt
            writeFile(outputFile, formattedTable);
            
            System.out.println("格式化完成！結果已保存到 " + outputFile);
            
        } catch (IOException e) {
            System.err.println("文件操作錯誤: " + e.getMessage());
        }
    }
    
    private static List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }
    
    private static void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }
    
    public static String formatTable(List<String> lines) {
        List<String[]> rows = new ArrayList<>();
        
        // 解析每一行的數據
        for (String line : lines) {
            String[] cells = line.split("\\|", -1); // -1 保留空字符串
            rows.add(cells);
        }
        
        if (rows.isEmpty()) return "";
        
        // 計算每列的最大寬度
        int columnCount = rows.get(0).length;
        int[] maxWidths = new int[columnCount];
        
        for (String[] row : rows) {
            for (int i = 0; i < Math.min(row.length, columnCount); i++) {
                maxWidths[i] = Math.max(maxWidths[i], getDisplayWidth(row[i]));
            }
        }
        
        // 格式化輸出
        StringBuilder result = new StringBuilder();
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            String[] row = rows.get(rowIndex);
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < columnCount; i++) {
                String cell = i < row.length ? row[i] : "";
                
                // 對於分隔線行，使用連字符填充
                if (rowIndex == 1 && cell.contains("-")) {
                    //sb.append("-".repeat(maxWidths[i]));
                	for(int j = 0; j < maxWidths[i]; j++) {
                	    sb.append("-");
                	}
                } else {
                    // 左對齊，考慮中文字符寬度
                    int padding = maxWidths[i] - getDisplayWidth(cell);
                    //sb.append(cell).append(" ".repeat(Math.max(0, padding)));
                    sb.append(cell);
                    for(int j = 0; j < Math.max(0, padding); j++) {
                        sb.append(" ");
                    }
                }
                
                if (i < columnCount - 1) {
                    sb.append("|");
                }
            }
            result.append(sb.toString());
            if (rowIndex < rows.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
    
    // 計算字符串的顯示寬度（中文字符算2個寬度）
    private static int getDisplayWidth(String str) {
        int width = 0;
        for (char c : str.toCharArray()) {
            if (isCJK(c)) {
                width += 2;
            } else {
                width += 1;
            }
        }
        return width;
    }
    
    // 判斷是否為中日韓字符
    private static boolean isCJK(char c) {
        return (c >= 0x4E00 && c <= 0x9FFF) ||    // CJK統一漢字
               (c >= 0x3400 && c <= 0x4DBF) ||    // CJK擴展A
               (c >= 0x3040 && c <= 0x309F) ||    // 平假名
               (c >= 0x30A0 && c <= 0x30FF) ||    // 片假名
               (c >= 0xFF00 && c <= 0xFFEF);      // 全形字符
    }
}
