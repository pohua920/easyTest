package com.tlg.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {

	@SuppressWarnings("unchecked")
	public void writeZip(String fileName) throws IOException {			
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(fileName + ".zip");
		} catch (ZipException e) {
			e.printStackTrace();
		}		
		@SuppressWarnings("rawtypes")
		ArrayList filesToAdd = new ArrayList();
		filesToAdd.add(new File(fileName));
		ZipParameters parameters = new ZipParameters();
		parameters.setEncryptFiles(true);
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		parameters.setPassword("ctbc180A");
		try {
			zipFile.addFiles(filesToAdd, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}  
	}	
	
	public void writeZip(String fileName, ArrayList<File> filesToAdd) throws IOException {			
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(fileName + ".zip");
		} catch (ZipException e) {
			e.printStackTrace();
		}		

		ZipParameters parameters = new ZipParameters();
		parameters.setEncryptFiles(true);
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		parameters.setPassword("ctbc180A");
		try {
			zipFile.addFiles(filesToAdd, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}  
	}
	
	public void unzip(String source, String destination, String pwd){
//	    String source = "some/compressed/file.zip";
//	    String destination = "some/destination/folder";
//	    String password = "password";

	    try {
	         ZipFile zipFile = new ZipFile(source);
	         if (zipFile.isEncrypted()) {
	            zipFile.setPassword(pwd);
	         }
	         zipFile.extractAll(destination);
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}
	
	public void zipAndEncryptFile(File srcFile, File targetZipFile, String zipPwd, boolean deleteSrcFileAfterZip)
			throws Exception {

		ZipParameters parms = new ZipParameters();
		parms.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parms.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (!StringUtil.isSpace(zipPwd)) {
			parms.setEncryptFiles(true);
			parms.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parms.setPassword(zipPwd);
		}

		ZipFile zipFile = new ZipFile(targetZipFile);
		zipFile.addFile(srcFile, parms);

		if (deleteSrcFileAfterZip) {
//			System.out.println("Test srcFile.delete()="+srcFile.delete());	
			srcFile.delete();	
		}
	}
}
