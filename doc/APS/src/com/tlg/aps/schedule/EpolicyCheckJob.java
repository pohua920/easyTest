package com.tlg.aps.schedule;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;
/** mantis：OTH0155，處理人員：CD094，需求單編號：OTH0155 電子保單系統是否正常運作檢核(APS)*/
public class EpolicyCheckJob implements Job {
	protected Logger logger = Logger.getLogger(EpolicyCheckJob.class);	
	private static ConfigUtil configUtil; 
	public EpolicyCheckJob() {
		super();
		configUtil = (ConfigUtil)AppContext.getBean("configUtil");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("EpolicyCheckJob begin...");
		String folderPath=configUtil.getString("checkedFolder");
		int limit=100;
		try { 
			int count =countPDFFiles(folderPath);
			if(count==-1){
				sendEmail(count);	
			}
			else if(count>limit){
				sendEmail(count);
				logger.info("電子表單等候數過多，已寄送email:"+count+" "+DateUtils.getDateString(new Date()));
			}else {
				logger.info("電子表單等候數:"+count+" "+DateUtils.getDateString(new Date()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("電子表單排程異常" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("EpolicyCheckJob end...");
	}

	private static int countPDFFiles(String folderPath) {
	    File folder = new File(folderPath);

	    if (!folder.exists() || !folder.isDirectory()) {
	        System.out.println("資料夾不存在或不是一個有效的資料夾。");
	        return -1;
	    }

	    File[] pdfFiles = folder.listFiles(new FileFilter() {
	        @Override
	        public boolean accept(File file) {
	            return file.isFile() && file.getName().toLowerCase().endsWith(".pdf");
	        }
	    });
	    return pdfFiles.length;
	}
	
	private static void sendEmail(int count) throws Exception {
		Mailer mailer = new Mailer();
		String subject="電子保單排程異常";
		String mailTo = configUtil.getString("epolicyCheckMailTo");
		String mailCc = "";

		StringBuilder sb = new StringBuilder();
		if(count==-1){
			sb.append(" 資料夾不存在，請確認檔案路徑");
		}else{
			sb.append("電子保單等候數過多，請檢查程式是否異常");
			sb.append("<p>目前檔案數目：" + count + "</p>");			
		}
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
					mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");	
	 }
	public static  void main(String arg[]) throws Exception{
		String folderPath="D:\\temp";
		int count =countPDFFiles(folderPath);
		if(count>2){
			sendEmail(count);
			System.out.println("send email");
		}
	System.out.println("end");
	}
}
