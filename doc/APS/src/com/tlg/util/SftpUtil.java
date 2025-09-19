/**
 * 
 */
package com.tlg.util;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


/**SFTP連線共用服務
 * @author bj016
 *
 */

public class SftpUtil {

	private static final Logger logger = Logger.getLogger(SftpUtil.class);
	private String host;
	private int port;
	private String user;
	private String pwd;
	
	public SftpUtil(String host, int port, String user, String pwd) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

//	private MailUtil mailUtil;
	
	/**SFTP上傳方法
	 * @param remoteDir
	 * @param workingDirPath
	 */
	public String  putFileToSftp(String remoteDir, String workingDirPath){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.debug("Local currentDir:" + workingDirPath);
				logger.info("currentDir:" + sftpChannel.pwd());

				sftpChannel.lcd(workingDirPath);
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			
			try{
				sftpChannel.cd(remoteDir);
			}catch (Exception e1) {
				try {
					sftpChannel.mkdir(remoteDir);
				} catch (SftpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Open the SFTP channel
			try {
				logger.info("Now opennig  SFTP channel...");
				logger.info("SFTP channel openned...");
				File filefolder = new File(workingDirPath);// 欲上傳資料夾位置
				File[] list1 = filefolder.listFiles();
				for (File file : list1) {
					logger.info("Upload file" + file.getAbsolutePath());
					sftpChannel.put(file.getAbsolutePath(), remoteDir+"/"+file.getName());
					//回押FTP傳送日期
					logger.info("fileName="+file.getName());
				}
				
			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	
	/**SFTP上傳方法
	 * @param remoteDir
	 * @param workingDirPath
	 */
	public String  putFileToSftp2(String remoteDir, String filePath){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}

			// Open the SFTP channel
			try {
				logger.info("Now opennig  SFTP channel...");
				logger.info("SFTP channel openned...");
				File file = new File(filePath);// 欲上傳檔案位置
				if(file.exists()) {
					logger.info("Local Upload file : " + file.getAbsolutePath());
					logger.info("SFTP Upload file : " + remoteDir+"/"+file.getName());
					sftpChannel.put(file.getAbsolutePath(), remoteDir+"/"+file.getName());
				}else {
					logger.info("Upload file : " + filePath);
					logger.info("Upload file does not exist...");
					return "fail";
				}

			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	
	/**SFTP上傳方法，增加遠端路徑不存在時，建立遠端路徑後再進行上傳
	 * @param remoteDir
	 * @param workingDirPath
	 */
	public String  putFileToSftp3(String remoteDir, String filePath){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			try{
				sftpChannel.cd(remoteDir);
			}catch (Exception e1) {
				try {
					sftpChannel.mkdir(remoteDir);
				} catch (SftpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Open the SFTP channel
			try {
				logger.info("Now opennig  SFTP channel...");
				logger.info("SFTP channel openned...");
				File file = new File(filePath);// 欲上傳檔案位置
				if(file.exists()) {
					logger.info("Local Upload file : " + file.getAbsolutePath());
					logger.info("SFTP Upload file : " + remoteDir+"/"+file.getName());
					sftpChannel.put(file.getAbsolutePath(), remoteDir+"/"+file.getName());
				}else {
					logger.info("Upload file : " + filePath);
					logger.info("Upload file does not exist...");
					return "fail";
				}

			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	
	/**SFTP下載方法
	 * @param remoteDir
	 * @param workingDirPath
	 * @param downloadFileList
	 */
	public String  getFileFromSftp(String remoteDir, String workingDirPath, List<String> downloadFileList){
//		String result ="success";
		//自properties檔取得SFTP連線參數
//		String ctbcSftpUser = "CX_TLG_GET";
//		String ctbcSftpHost = "175.184.243.49";
//		String ctbcSftpPwd = "xm3ip3in";
//		String ctbcSftpDir = "/aptoap/insco/180/offset";
//		int ctbcSftpPort = 22;
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}
		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to  SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig  SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.debug("Local currentDir:" + workingDirPath);
				logger.info("currentDir:" + sftpChannel.pwd());

				sftpChannel.lcd(workingDirPath);
				sftpChannel.cd(remoteDir);
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}

			// Open the SFTP channel
			try {
				logger.info("Now opennig  SFTP channel...");
				logger.info("SFTP channel openned...");
				for(String fileName : downloadFileList) {
					logger.info("Download file : " + fileName + " start......");
					sftpChannel.get(fileName,fileName);
					logger.info("Download file : " + fileName + " finish......");
				}
				
			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	
	/**SFTP下載方法
	 * @param remoteDir
	 * @param workingDirPath
	 * @param downloadFileList
	 */
	@SuppressWarnings("rawtypes")
	public List<String> getFileListFromSftp(String remoteDir){
		
		List<String> fileList = new ArrayList<String>();
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return null;
		}
		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to  SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return null;
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());

				sftpChannel.cd(remoteDir);
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return null;
			}

			// Open the SFTP channel
			try {
				logger.info("Now opennig SFTP channel...");
				logger.info("SFTP channel openned...");
				sftpChannel.ls(remoteDir);
				Vector filelist = sftpChannel.ls(remoteDir);
	            for(int i=0; i<filelist.size();i++){
	                LsEntry entry = (LsEntry) filelist.get(i);
	                logger.info(entry.getFilename());
	                fileList.add(entry.getFilename());
	            }
			} catch (SftpException e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return null;
			}
			
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return null;
			}
		}
		
		return fileList;
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  start **/
	/**SFTP下載方法
	 * @param remoteDir
	 * @param workingDirPath
	 * @param downloadFileList
	 */
	@SuppressWarnings("rawtypes")
	public List<String> getFileListFromSftp(String remoteDir,int month){
		
		List<String> fileList = new ArrayList<String>();
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return null;
		}
		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to  SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return null;
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());

				sftpChannel.cd(remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return null;
			}

			// Open the SFTP channel
			try {
				logger.info("Now opennig SFTP channel...");
				logger.info("SFTP channel openned...");
				Vector<LsEntry> filelist = sftpChannel.ls(remoteDir);
				Calendar calendar =Calendar.getInstance();
				calendar.add(Calendar.MONTH, month*-1);
				calendar.set(Calendar.HOUR_OF_DAY,0);
				calendar.set(Calendar.MINUTE,0);
				calendar.set(Calendar.SECOND,0);
				calendar.set(Calendar.MILLISECOND,0);
				
				Date dateScale = calendar.getTime();
				
				for(LsEntry entry:filelist){
					Date fileDate=new Date(entry.getAttrs().getMTime()*1000L);
					if(fileDate.after(dateScale)){
						 logger.info(entry.getFilename());
			             fileList.add(entry.getFilename());
					}
				}
				
			} catch (SftpException e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return null;
			}
			
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return null;
			}
		}
		
		return fileList;
	}
	
	/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 start */
	/**SFTP檔案刪除方法
	 * @param remoteDir
	 * @param workingDirPath
	 */
	public String deleteFileToSftp(String remoteDir, String fileName){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}

			// Open the SFTP channel
			try {
				logger.info("Now opennig  SFTP channel...");
				logger.info("SFTP channel openned...");
				logger.info("SFTP delete file : " + remoteDir+"/"+fileName);
				sftpChannel.rm(remoteDir+"/"+fileName);
			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 end */
	
	public String downloadFolder(String remoteDir, String destinationDir){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}

			// Open the SFTP channel
			try {
				String pathSeparator = "/";
				Vector<ChannelSftp.LsEntry> fileAndFolderList = sftpChannel.ls(remoteDir); // Let list of folder content
		        
		        //Iterate through list of folder content
		        for (ChannelSftp.LsEntry item : fileAndFolderList) {
		            
		            if (!item.getAttrs().isDir()) { // Check if it is a file (not a directory).
		                if (!(new File(destinationDir + pathSeparator + item.getFilename())).exists()
		                        || (item.getAttrs().getMTime() > Long
		                                .valueOf(new File(destinationDir + pathSeparator + item.getFilename()).lastModified()
		                                        / (long) 1000)
		                                .intValue())) { // Download only if changed later.

		                    new File(destinationDir + pathSeparator + item.getFilename());
		                    sftpChannel.get(remoteDir + pathSeparator + item.getFilename(),
		                            destinationDir + pathSeparator + item.getFilename()); // Download file from source (source filename, destination filename).
		                }
		            } else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
		                new File(destinationDir + pathSeparator + item.getFilename()).mkdirs(); // Empty folder copy.
		                this.downloadFolder(remoteDir + pathSeparator + item.getFilename(),
		                        destinationDir + pathSeparator + item.getFilename()); // Enter found folder on server to read its contents and create locally.
		            }
		        }
			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
	
public String uploadFolder(String remoteDir, String sourceDir){
		
		if(this.host == null || this.host.length() <= 0
				|| this.user == null || this.user.length() <= 0) {
			logger.info("can't get sftp host or sftp user data......");
			return "fail";
		}

		logger.info("SftpUser:"+this.user);
		logger.info("SftpHost:"+this.host);
		logger.info("SftpPwd:"+this.pwd);
		logger.info("SftpPort:"+this.port);
		logger.info("SftpDir:"+remoteDir);
		//SFTP connect start
		// *must_have using BouncyCastleProvider instead of default provider to avoid existing bug in JDK
		Security.addProvider(new BouncyCastleProvider());
		JSch jsch = null;
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			jsch = new JSch();
			try {
				logger.info("session create start... ");
				session = jsch.getSession(this.user, this.host, this.port);
				session.setTimeout(10 * 1000);//10秒
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(this.pwd);
				session.connect();
			} catch (JSchException e) {
				String msg = "Connect to SFTP (" + this.host + ":" + String.valueOf(this.port) + ") failed..";
				logger.error(msg, e);
				return "fail";
			}

			// open channel
			logger.info("Now opennig SFTP channel...");
			Channel channel;
			try {
				channel = session.openChannel("sftp");
				channel.connect();
				sftpChannel = (ChannelSftp) channel;
				logger.info("SFTP channel openned...");
				logger.debug("SFTP File:" + remoteDir);
				logger.info("currentDir:" + sftpChannel.pwd());
				logger.info("Local currentDir:" + sftpChannel.lpwd());
			} catch (Exception e) {
				String msg = "Failed to open SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}

			// Open the SFTP channel
			try {
				String pathSeparator = "/";
				
				try{
					sftpChannel.cd(remoteDir);
				}catch (Exception e1) {
					try {
						sftpChannel.mkdir(remoteDir);
					} catch (SftpException e) {
						e.printStackTrace();
					}
				}
				
				File sourceFolder = new File(sourceDir);
				File[] arrFile = null;
				if(sourceFolder.exists()) {
					arrFile = sourceFolder.listFiles();
					for (File file : arrFile) {
						if(!file.isDirectory()) {
							logger.info("Upload file" + file.getAbsolutePath());
							sftpChannel.put(file.getAbsolutePath(), remoteDir+"/"+file.getName());
							//回押FTP傳送日期
							logger.info("fileName="+file.getName());
						} else if (!(".".equals(file.getName()) || "..".equals(file.getName()))) {
							String newRemoteDir = remoteDir + pathSeparator + file.getName();
							try{
								sftpChannel.cd(newRemoteDir);
							}catch (Exception e1) {
								try {
									sftpChannel.mkdir(newRemoteDir);
								} catch (SftpException e) {
									e.printStackTrace();
								}
							}
			                this.uploadFolder(newRemoteDir,
			                		sourceDir + pathSeparator + file.getName()); // Enter found folder on server to read its contents and create locally.
			            }
					}
				}
			} catch (SftpException e) {
				String msg = "Failed to open  SFTP channel...";
				logger.error(msg, e);
				return "fail";
			}
			return "success";
		} finally {
			// always disconnect from sftp
			try {
				logger.info("Trying to disconnect from  SFTP....");
				if (sftpChannel != null) {
					sftpChannel.exit();
				}
				if (session != null) {
					session.disconnect();
				}
				if (jsch != null) {
					jsch = null;
				}
			} catch (Exception e) {
				logger.error("Error occurred while disconnect from  SFTP..", e);
				return "fail";
			}
		}
	}
}
