package com.sinosoft.app.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author 中科软
 */
public class CompressTools extends ZipOutputStream {
	public CompressTools(OutputStream outputStream) {
		this(outputStream, defaultEncoding, defaultLevel);
	}

	public CompressTools(String file) throws IOException {
		this(new FileOutputStream(new File(file)), defaultEncoding, defaultLevel);
	}

	public CompressTools(File file) throws IOException {
		this(new FileOutputStream(file), defaultEncoding, defaultLevel);
	}

	/**
	 * 统一调用的构造函数
	 * @param outputStream 输出流(输出路径),*.zip
	 * @param encoding 编码
	 * @param level 压缩级别 0-9
	 */
	public CompressTools(OutputStream outputStream, String encoding, int level) {
		super(outputStream);

		buf = new byte[1024];// 1024 KB缓冲

		if (encoding != null || !"".equals(encoding))
			this.setEncoding(encoding);

		if (level < 0 || level > 9)
			level = 7;
		this.setLevel(level);

		comment = new StringBuffer();
	}

	public String put(String fileName) throws IOException {
		return put(fileName, "");
	}

	/**
	 * 加入要压缩的文件或文件夹
	 * @param fileName 加入一个文件,或一个文件夹
	 * @param pathName 生成ZIP时加的文件夹路径
	 * @return fileName
	 */
	public String put(String fileName, String pathName) throws IOException {
		File file = new File(fileName);

		if (!file.exists()) {
			comment.append("發現一個不存在的文件或目錄: ").append(fileName).append("\n");
			return null;
		}
		// 递归加入文件
		if (file.isDirectory()) {
			pathName += file.getName() + "/";
			System.out.println("pathNamepathNamepathName==" + pathName);
			String fileNames[] = file.list();
			if (fileNames != null) {
				for (String f : fileNames)
					put(fileName + "\\" + f, pathName);
			}
			return fileName;
		}

		fileCount++;

		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(this);
			if (userFullPathName)
				pathName += file.getPath();
			this.putNextEntry(new ZipEntry(pathName + file.getName()));
			int len;
			// BufferedOutputStream会自动使用 this.buf,如果再使用in.read(buf)数据会错误
			while ((len = in.read()) > -1)
				out.write(len);
		} catch (IOException ex) {
			comment.append("一個文件讀取寫入時錯誤: ").append(fileName).append("\n");
		}

		if (out != null)
			out.flush();
		if (in != null)
			in.close();

		this.closeEntry();
		return file.getAbsolutePath();
	}

	public String[] put(String[] fileName) throws IOException {
		return put(fileName, "");
	}

	public String[] put(String[] fileName, String pathName) throws IOException {
		for (String file : fileName)
			put(file, pathName);
		return fileName;
	}

	/**
	 * 压缩的文件个数
	 * @return int
	 */
	public int getFileCount() {
		return this.fileCount;
	}

	// 压缩级别:0-9
	public static int defaultLevel = 7;
	// 编码,简体:GB2312,繁体:BIG5
	public static String defaultEncoding = "GB2312";
	// 压缩时用全路径,会生成对应的目录,false:不带路径,只有文件名
	public static boolean userFullPathName = false;
	// 注释
	public StringBuffer comment;

	private int fileCount = 0;

}
