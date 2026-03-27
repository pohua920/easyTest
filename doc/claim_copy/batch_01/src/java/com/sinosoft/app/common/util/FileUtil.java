package com.sinosoft.app.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

//import com.sinosoft.productconfig.common.util.ReadProperties;
//import com.sinosoft.prpins.common.util.ImportExcelTools;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 * 從 HAS_0270複製過來
 * 檔案處理相關方法
 * 
 */
public class FileUtil {
	
	static public void insertLOG(String funCtionName,String policyNo,String endorseNo,String action,Date startTime ,String remark,WebServiceContext context , Object obj) {
		DBManager dbManager=new DBManager();
		try{
			StringWriter sw = new StringWriter();
			if(obj != null){
				JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				jaxbMarshaller.marshal(obj, sw);	
			}
			HttpServletRequest httprequest = (HttpServletRequest)context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
			String ip ="";
			if (httprequest.getHeader("HTTP_X_FORWARDED_FOR") == null) {
		        ip = httprequest.getRemoteAddr();
		    } else {
		        ip = httprequest.getHeader("HTTP_X_FORWARDED_FOR");
		    }
			dbManager.open("ddccDataSource");
			dbManager.prepareStatement("DELETE WEBSERVICELOG WHERE STARTTIME < (SYSDATE - 180) ");
			dbManager.executePreparedUpdate();
			if("END".equals(action.toUpperCase())){
				dbManager.prepareStatement("insert into WEBSERVICELOG(FUNCTIONNAME,RID,IP,POLICYNO,ENDORSENO,STARTOREND,STARTTIME,ENDTIME,CONTEXT,REMARK) values(?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),SYSDATE,?,?)");	
			}else{
				dbManager.prepareStatement("insert into WEBSERVICELOG(FUNCTIONNAME,RID,IP,POLICYNO,ENDORSENO,STARTOREND,STARTTIME,ENDTIME,CONTEXT,REMARK) values(?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),null,?,?)");
				
			}
			
			int i = 1;
			dbManager.setString( i++ , funCtionName );
			dbManager.setString( i++ , Long.toString(startTime.getTime()) );
			dbManager.setString( i++ , ip );
			dbManager.setString( i++ , policyNo );
			dbManager.setString( i++ , endorseNo);
			dbManager.setString( i++ , action );
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dbManager.setString( i++ , df.format(startTime));
			dbManager.setString( i++ , sw.toString() );
			dbManager.setString( i++ , remark );
			dbManager.executePreparedUpdate();
		}catch(Exception e){
			System.out.println("insertLOG error:"+e.getMessage());	
		}finally{
			if(dbManager != null){
				try{
					dbManager.close();	
				}catch(Exception e){}
			}
		}
	}


	
//	/**
//	 * 紀錄操作軌跡
//	 * @param text
//	 */
//	public static void saveActionLog(String text) {
//		String fileString = ReadProperties.getString("FILTER_LOG_PATH") + DateUtil.formatDate(new Date(),"YYYYMMdd") + ".txt";
//		Writer writer = null;
//
//		try{
//			File file = new File(fileString);
//			if(file.exists() && file.isFile()){
//				if(file.length() >= ( 500 * 1024 *1024 )){
//					int nowNumber = 1 ;
//					boolean reNameOver = false;
//					while(!reNameOver){
//						String reNameTo = ReadProperties.getString("FILTER_LOG_PATH") + DateUtil.formatDate(new Date(),"YYYYMMdd") + "_" + nowNumber + ".txt";
//						File reNameToFile = new File(reNameTo);
//						if(!(reNameToFile.exists() && reNameToFile.isFile())){
//							reNameOver = file.renameTo(reNameToFile);
//							reNameOver = true;
//						}
//						nowNumber++;
//					}
//				}
//			}
//			
//			writer = new BufferedWriter(new OutputStreamWriter(
//			        new FileOutputStream(fileString, true), "UTF-8"));
//			writer.append(text);
//			writer.append("\r\n");
//		}catch(Exception e){
//			System.out.println("Exception");
//		}finally{
//			if(writer != null){
//				try{
//					writer.close();
//				}catch(Exception e){
//					
//				}
//			}
//		}
//	}
//	
//	public static void main(String arg[]){
//		File file = new File("D:\\aaa.xls");
//		String[][][] dataExcel = null;
//		try {
//			dataExcel = ImportExcelTools.getData1(file, 0, 1);
//		} catch (Exception e1) {
//			System.out.println("讀取Excel失敗");
//		}
//		String[][] data = new String[dataExcel[0].length][];
//	}
	
}
