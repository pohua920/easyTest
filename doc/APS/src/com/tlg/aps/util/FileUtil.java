package com.tlg.aps.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileUtil {
	
	private final static Logger logger = LogManager.getLogger(FileUtil.class);

	/**
	 * 取得資料夾所有檔案檔名
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getAllFileName(String path) {

		List<String> fileNameList = new ArrayList<String>();
		File folder1 = new File(path);
		//20200717舊的查folder裡檔案的方法
//		String[] list1 = folder1.list();
//		for (int i = 0; i < list1.length; i++) {
//			fileNameList.add(list1[i]);
//		}
		
		//20200717新的查folder裡檔案的方法
		for (File fileEntry : folder1.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	logger.info(fileEntry.getName());
	            fileNameList.add(fileEntry.getName());
	        }
	    }

		logger.info("getAllFileName fileNameList.size():" + fileNameList.size());
		return fileNameList;
	}
	
	/**
	 * 取得資料夾所有檔案檔名，將檔案大小加總，以9MB為限制將檔案分批處理
	 * 因為電子保單認證
	 * @param path
	 * @return
	 */
	public static List<List<String>> getAllFileNameAndSize(String path) {

		List<String> fileNameList = new ArrayList<String>();
		List<List<String>> seperateFileNameList = new ArrayList<List<String>>();
		File folder1 = new File(path);
		File[] files = folder1.listFiles();
		double totalSize = 0.0;
		int totalFileSize = 0;
		int totalZipFileSize = 0;
		for (int i = 0; i < files.length; i++) {
			String fName = files[i].getName();
			if (files[i].isFile()) {
				totalSize += files[i].length();
				
				//判斷檔案加總的大小是否大於9MB = 9437184bytes
				if(totalSize > 9437184){
					seperateFileNameList.add(fileNameList);
					totalSize = files[i].length();
					fileNameList = new ArrayList<String>();
					fileNameList.add(fName);
					totalZipFileSize++;
				}else{
					fileNameList.add(fName);
				}
				
				totalFileSize++;
			}
		}
		
		if(fileNameList.size() > 0){
			seperateFileNameList.add(fileNameList);
			totalZipFileSize++;
		}
		logger.info("getAllFileNameAndSize totalFileSize:" + totalFileSize + " ,totalZipFileSize:" + totalZipFileSize);
		return seperateFileNameList;
	}

	public static List<File> getAllAbsolutePathFileName(String path) {

		List<File> fileNameList = new ArrayList<File>();

		File folder1 = new File(path);
		String[] list1 = folder1.list();

		for (int i = 0; i < list1.length; i++) {
			fileNameList.add(new File(folder1.getAbsolutePath() + File.separator + list1[i]));
		}
		return fileNameList;
	}

	/**
	 * 寫入txt檔案
	 * 
	 * @param filePath
	 * @param fileName
	 * @param extension
	 * @param context
	 */
	private static void writerTxt(String filePath, String fileName, String extension, String context) {
		logger.info("fileUtil writerTxt start..");
		BufferedWriter fw = null;
		try {
			logger.info("fileUtil writerTxt fileName:" + fileName);
			File file = new File(filePath + fileName + extension);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指點編碼格式，以免讀取時中文字符異常
			fw.append(context);
			fw.flush(); // 全部寫入緩存中的內容

			logger.info("fileUtil writerTxt fileName:" + file.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		logger.info("fileUtil writerTxt end..");
	}

	private static void writerTxt(String filePath, List<String> indexContentList, String extension, String fileName) {
		logger.info("fileUtil writerTxt start..");
		BufferedWriter fw = null;
		try {

			String context = "";
			for (String str : indexContentList) {
				context += str;
			}

			File file = new File(filePath + File.separator + fileName + extension);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指點編碼格式，以免讀取時中文字符異常
			fw.append(context);
			fw.flush(); // 全部寫入緩存中的內容

			logger.info("fileUtil writerTxt fileName:" + file.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		logger.info("fileUtil writerTxt end..");
	}

	/**
	 * 建立新資料夾
	 * 
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	public static void createNewFile(String path, String fileName) throws IOException {
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			file.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
	}

	/**
	 * 複製單個檔
	 * 
	 * @param oldPath
	 *            String原檔路徑 如：c:/fqf.txt
	 * @param newPath
	 *            String複製後路徑 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 檔存在時
				inStream = new FileInputStream(oldPath);// 讀入原檔
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 位元組數 檔案大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			logger.info("複製單個檔操作出錯");
			logger.error(e.getMessage(), e);
		} finally {
			if (null != inStream) {
				try {
					inStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (null != fs) {
				try {
					fs.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 將檔案移動方法
	 * 
	 * @param url
	 * @param toUrl
	 */
	public static void move(String url, String toUrl) {
		logger.info("move " + url + " to " + toUrl);
		File file = new File(url);
		if(file.exists()){
			file.renameTo(new java.io.File(toUrl));
			file.delete();
		}
	}

	/**
	 * 採用SHA256
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA256(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data.getBytes());

		StringBuffer result = new StringBuffer();
		for (byte byt : md.digest())
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		return result.toString();

	}

//	/**
//	 * 依據PDF檔名 依序建立.index及.pw檔
//	 * 
//	 * @param fileNameList
//	 * @param temp1File
//	 * @param temp2File
//	 * @param fileName
//	 */
//	public static void createFileIndexPwPdf(List<EpolicyMain> epolicymainList, File temp1File, File temp2File,
//			String fileName) {
//		String filePath = temp1File.getAbsolutePath();
//		String temp2FilePath = temp2File.getAbsolutePath();
//		try {
//
//			List<String> indexContentList = new ArrayList<String>();
//			List<String> pwContentList = new ArrayList<String>();
//
//			for (EpolicyMain main : epolicymainList) {
//				logger.info("main.getAppliidentifynumber():" + main.getAppliidentifynumber());
//				String fileNamePdf = main.getFilename() + IConstants.FILE_EXTENSION_PDF;
//
//				String toTemp1File = filePath + File.separator + fileNamePdf;
//				String toTemp2File = temp2FilePath + File.separator + fileNamePdf;
//				// PDF 加密後 傳至temp2
//				PdfUtil.encryptPdf(toTemp1File, toTemp2File, main.getAppliidentifynumber().getBytes(),
//						main.getAppliidentifynumber().getBytes());
//
//				indexContentList.add(getIndexContent(main.getFilename(), ""));
//				pwContentList.add(getPwContent(main.getFilename(), main.getAppliidentifynumber()));
//			}
//			// 建立.index檔案
//			writerTxt(filePath, indexContentList, IConstants.FILE_EXTENSION_INDEX, fileName);
//			// 建立.pw
//			writerTxt(filePath, pwContentList, IConstants.FILE_EXTENSION_PW, fileName);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * .index 內容 電子保單資訊 【保單唯一編號】=【電子文件檔明】||【保單險種代碼】||【交易類別代碼】||【備註】
//	 * 
//	 * @param policyNo
//	 * @param remark
//	 * @return
//	 */
//	private static String getIndexContent(String policyNo, String remark) {
//
//		return policyNo + "=" + policyNo + "||" + IConstants.INSURED_CODE + "||" + IConstants.TRANSACTION_CODE + "||"
//				+ remark + "\r\n";
//	}

	/**
	 * .pw 內容 密碼檔案 【保單唯一編號】 = 【密碼值】
	 * 
	 * @param appliIdentifynumber
	 *            身份證字號
	 * @return
	 * @throws Exception
	 */
	private static String getPwContent(String policyNo, String appliIdentifynumber) throws Exception {
		return policyNo + "=" + getSHA256(appliIdentifynumber) + "\r\n";
	}

//	public static void deleteFile(File file) {
//		//暫時註掉
////		if (file.exists()) {
////			if (file.isDirectory()) {
////				File[] files = file.listFiles();
////				for (File file1 : files) {// 遞迴刪除檔案或目錄
////					deleteFile(file1);
////					while (file1.exists()) {
////						file1.delete();
////					}
////				}
////			} else {
////				file.delete();
////			}
////		}
//		
//		//bi079  更新刪除方法 start
//		if (!file.isDirectory()) {
//			try {
//				org.apache.commons.io.FileUtils.forceDelete(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//		List<File> files = (List<File>)org.apache.commons.io.FileUtils.listFilesAndDirs(file, 
//				org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE, 
//				org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE);
//		if (files == null || files.size() < 1) {
//			return;
//		}
//		for (File lstFile : files) {
//			//System.out.println( lstFile.getPath() + " / " + lstFile.getName() + "/" + lstFile.toString() );
//			if (lstFile.getPath().equals(file.getPath())) {
//				continue;
//			}
//			if (lstFile.exists()) {
//				try {
//					org.apache.commons.io.FileUtils.forceDelete(lstFile);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		//bi079  更新刪除方法 end
//
//	}
	
//	public static void deleteFile(String filePath, List<String> fileList) {
//		String totalFilePath = "";
//		File file = null;
//		for(String fileName : fileList){
//			totalFilePath = filePath + File.separator + fileName;
//			file = new File(totalFilePath);
//			
//			//bi079  更新刪除方法 start
//			if (!file.isDirectory()) {
//				try {
//					if(file.exists()){
//						org.apache.commons.io.FileUtils.forceDelete(file);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				continue;
//			}
//			List<File> files = (List<File>)org.apache.commons.io.FileUtils.listFilesAndDirs(file, 
//					org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE, 
//					org.apache.commons.io.filefilter.TrueFileFilter.INSTANCE);
//			if (files == null || files.size() < 1) {
//				return;
//			}
//			for (File lstFile : files) {
//				//System.out.println( lstFile.getPath() + " / " + lstFile.getName() + "/" + lstFile.toString() );
//				if (lstFile.getPath().equals(file.getPath())) {
//					continue;
//				}
//				if (lstFile.exists()) {
//					try {
//						org.apache.commons.io.FileUtils.forceDelete(lstFile);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			//bi079  更新刪除方法 end
//		}
//	}
//	
//	public static void deleteFile2(File file) {
//		//bi053  更新刪除方法 start
//		if (!file.isDirectory()) {//不是資料夾
//		        try {
//		                org.apache.commons.io.FileUtils.forceDelete(file);
//		        } catch (IOException e) {
//		                e.printStackTrace();
//		        }
//		        return;
//		}else{
//		        try {
//		                org.apache.commons.io.FileUtils.deleteDirectory(file);
//		        } catch (IOException e) {
//		                e.printStackTrace();
//		        }
//		        return;
//		}
//		//bi053  更新刪除方法 end
//	}
	

	/**
	 * 讀取properties方法
	 * 
	 * @param key
	 * @return
	 */
	public static String loadProperties(String key) {
		String value = "";
		Properties properties = new Properties();
		InputStream input = null;
		try {
			// 方法一
			//原本的寫法----START
//			String filePath = FileUtil.class.getResource("/epolicy.properties").getPath();
			// System.out.println("oldPath="+filePath);
//			filePath = filePath.replaceAll("%20", " ");
			// System.out.println("newPath="+filePath);
//			File file = new File(filePath);
//			properties.load(new FileInputStream(file));
			//原本的寫法----END
			//此專案要export成JAR檔的話，要這樣寫才會執行JAR檔成功----START
			input = FileUtil.class.getClassLoader().getResourceAsStream("epolicy.properties");
			if(input != null) properties.load(input);
			//此專案要export成JAR檔的話，要這樣寫才會執行JAR檔成功----END

			// 方法二: 此寫法在2.13上的路徑(因為有空白)會出錯
			// properties.load(new
			// FileInputStream(FileUtil.class.getResource("/epolicy.properties").getFile()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			try {
				if(input != null) {
					input.close();
				}
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		value = properties.getProperty(key, "");
		return value;
	}

}
