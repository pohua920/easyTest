package com.tlg.aps.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsUtil {
	public static HSSFWorkbook openXls(File xlsFile)throws IOException{
		if( xlsFile==null || !xlsFile.exists()){
			return null;
		}
		
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(xlsFile));
		return getWorkbook(fs);
	}
	
	public static HSSFWorkbook openXls(String classpathFilePath) throws IOException{
		InputStream in = XlsUtil.class.getClassLoader().getResourceAsStream(classpathFilePath);
		if( in==null ){
			return null;
		}
		POIFSFileSystem fs = new POIFSFileSystem(in);
		return getWorkbook(fs);
	}
	
	public static XSSFWorkbook openXlsx(String classpathFilePath) throws IOException{
		InputStream in = XlsUtil.class.getClassLoader().getResourceAsStream(classpathFilePath);
		//InputStream in = new FileInputStream("D:\\developer\\workspace\\residential-fire-renewal\\src\\main\\resources\\xls\\defaultFireRenewal.xlsx");
		if( in==null ){
			return null;
		}
		XSSFWorkbook wb = new XSSFWorkbook(in);
		return wb;
	}
	
	public static File writeToTemp(HSSFWorkbook wb) throws IOException{
		return writeToTemp(wb,"XlsUtil_");
	}
	
	public static void writeTo(HSSFWorkbook wb, File toFile) throws IOException{
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(toFile));
		wb.write(out);
		out.flush();
		out.close();
	}
	
	public static void writeTo(XSSFWorkbook wb, File toFile) throws IOException{
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(toFile));
		wb.write(out);
		out.flush();
		out.close();
	}
	
	public static File writeToTemp(HSSFWorkbook wb, String filePrefix) throws IOException{
		File tmpFile =File.createTempFile(filePrefix, ".xls");
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmpFile));
		wb.write(out);
		out.flush();
		out.close();
		return tmpFile;
	}
	
	protected static HSSFWorkbook getWorkbook(POIFSFileSystem fs) throws IOException{
		return new HSSFWorkbook(fs,true);
	};
}
