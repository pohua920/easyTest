package com.tlg.aps.bs.miTerminationNoticeService.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.miTerminationNoticeService.MiTerminationNoticeService;
import com.tlg.aps.bs.miTerminationNoticeService.MobileInsSmsNewTransService;
import com.tlg.aps.vo.MobileInsUnsendSmsVo;
import com.tlg.aps.vo.SmsVo;
import com.tlg.aps.vo.SmsVoList;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.MobileInsSms;
import com.tlg.xchg.service.MobileInsSmsService;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MiTerminationNoticeServiceImpl implements MiTerminationNoticeService {
	
	private static final Logger logger = Logger.getLogger(MiTerminationNoticeServiceImpl.class);
	private ConfigUtil configUtil;

	private MobileInsSmsNewTransService mobileInsSmsNewTransService;
	private MobileInsSmsService mobileInsSmsService;
	
	@Override
	public void send() throws SystemException, Exception {
		
		ArrayList<String> msgList = new ArrayList<String>();
		String smsUrl = this.configUtil.getString("smsUrl");
		if(StringUtil.isSpace(smsUrl)){
			throw new SystemException("無法取得簡訊發送位置設定");
		}
		
		Result result = new Result();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String executeDate = dateFormat.format(new Date());
		//終止通知書-客戶申請終止
		String cancelSuccessCount = "0";
		String cancelFailCount = "0";
		//終止通知書-逾期繳費終止
		String unpaidSuccessCount = "0";
		String unpaidFailCount = "0";
		
		try{
			//終止通知書-客戶申請終止
			result = mobileInsSmsService.findUnsendCancelNotice();
			if(result.getResObject() != null){
				ArrayList<MobileInsUnsendSmsVo> mobileInsUnsendSmsVoList = (ArrayList<MobileInsUnsendSmsVo>)result.getResObject();
				if(mobileInsUnsendSmsVoList != null && mobileInsUnsendSmsVoList.size() > 0){
					int resultCount[] = send("cancel", smsUrl, msgList, mobileInsUnsendSmsVoList, cancelSuccessCount, cancelFailCount);
					cancelSuccessCount = StringUtil.nullToSpace(resultCount[0] + "");
					cancelFailCount = StringUtil.nullToSpace(resultCount[1] + "");
				}
				
			}
			//終止通知書-逾期繳費終止
			result = mobileInsSmsService.findUnsendUnpaidNotice();
			if(result.getResObject() != null){
				ArrayList<MobileInsUnsendSmsVo> mobileInsUnsendSmsVoList = (ArrayList<MobileInsUnsendSmsVo>)result.getResObject();
				if(mobileInsUnsendSmsVoList != null && mobileInsUnsendSmsVoList.size() > 0){
					int resultCount[] = send("unpaid", smsUrl, msgList, mobileInsUnsendSmsVoList, unpaidSuccessCount, unpaidFailCount);
					unpaidSuccessCount = StringUtil.nullToSpace(resultCount[0] + "");
					unpaidFailCount = StringUtil.nullToSpace(resultCount[1] + "");
				}
			}
		} catch (SystemException se) {
			logger.debug(se);
			se.printStackTrace();
			throw se;
			
		} catch (Exception e) {
			logger.debug(e);
			e.printStackTrace();
			throw e;
		} finally {
			sendErrorMail(msgList, executeDate, "行動裝置險-終止通知書簡訊發送訊息", cancelSuccessCount, cancelFailCount, unpaidSuccessCount, unpaidFailCount);
		}
	}
	
	private int[] send(String type, String smsUrl, ArrayList<String> msgList, ArrayList<MobileInsUnsendSmsVo> dataList, String successCount, String failCount) throws Exception{
		int resultCount[] = {0,0};
		int sCount = 0;
		int fCount = 0;
		try{
			if(dataList.size() > 0){
				for(MobileInsUnsendSmsVo vo:dataList){
					
					try{
						
						String oid = vo.getOid();
						String url = vo.getShortpath();
						if(StringUtil.isSpace(url)){
							fCount++;
							msgList.add(oid + "," + "缺少短網址");
							continue;
						}
						Result result = mobileInsSmsService.findMobileInsSmsByOid(oid);
						if(result.getResObject() ==  null){
							fCount++;
							msgList.add(oid + "," + "無法查詢到Mobile_Ins_Sms資料");
							continue;
						}
						MobileInsSms mobileInsSms = (MobileInsSms)result.getResObject();
						//呼叫簡訊web service
						 SmsVo sms = new SmsVo();
						 sms.setTarget(mobileInsSms.getMobile());
						 if("cancel".equals(type)){
							 sms.setMessage(getCancelWords(url));
						 }
						 if("unpaid".equals(type)){
							 sms.setMessage(getUnpaidWords(url));
						 }
						String msg = sendSms(smsUrl, sms);
						if(!msg.startsWith("X0")){
							fCount++;
							msgList.add(oid + "," + msg);
							continue;
						}
						//更新時間
						mobileInsSms.setSmsdate(new Date());
						mobileInsSmsNewTransService.updateMobileInsSms(mobileInsSms);
						sCount++;
						
					}catch (Exception e) {
						e.printStackTrace();
						fCount++;
						continue;
					}
				}
			}else{
				//沒資料可以執行
				msgList.add( ",目前無資料可執行" );
			}
		}finally{
			resultCount[0] = sCount;
			resultCount[1] = fCount;
		}
		return resultCount;
	}
	
	private String getCancelWords(String url){
		/** mantis：MOB0027，處理人員：CE035，需求單編號：MOB0027 簡訊內容調整  START */
//		String s = "【中國信託產物保險行動裝置保險通知】\r\n" +
//				"親愛的保戶您好，感謝您投保本公司行動裝置保險。\r\n" +
//				"台端之行動裝置保險保險契約效力已自申請日之零時起終止特此通知。本保險契約已終止於最後繳費到期日。相關權利義務請參考" + url + " 。(密碼 : 身分證號字母大寫)\r\n" +
//				"若您有任何疑問，歡迎致電中國信託產物保險客戶服務專線0800-226-988，將有專人為您提供服務及說明(服務時間為周一至周五08:50~17:30)謝謝。\r\n" +
//				"中國信託產物保險珍惜每個為您服務的機會。願您事事平安順達。";
		String s = "您於中信產險投保的行動裝置險契約已自申請日之零時起終止，本保險契約已終止於最後繳費到期日，若有任何疑問，歡迎致電0800-226-988，相關保險契約權利義務請詳(密碼：身分證號字母大寫)" + url;
		/** mantis：MOB0027，處理人員：CE035，需求單編號：MOB0027 簡訊內容調整  END */
		return s;
	}
	
	private String getUnpaidWords(String url){
		String s = "【中國信託產物保險行動裝置保險通知】\r\n" +
				"親愛的保戶您好，由於您保險契約已逾繳款期限，本公司仍未收到應繳之保險費特以此專函通知您，本保險契約已終止於最後繳費到期日。相關權利義務請參考通知書連結" + url + " 。(密碼 : 身分證號字母大寫)\r\n" +
				"若您有任何疑問，歡迎致電中國信託產物保險客戶服務專線0800-226-988，將有專人為您提供服務及說明(服務時間為周一至周五08:50~17:30)謝謝。\r\n" +
				"中國信託產物保險珍惜每個為您服務的機會。願您事事平安順達。";
		
		return s;
	}
	
	private String sendSms(String smsUrl, SmsVo sms) throws IOException{
		
		if(sms == null){
			return "X1,簡訊sms物件為null";
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
        	SmsVoList vo = new SmsVoList();
        	vo.getSmsvoList().add(sms);
        	
        	HttpPost httpPost = new HttpPost(smsUrl);  
        	StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(vo), "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
        	httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
//        	httpPost.setHeader("charset","UTF-8");
        	
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);

            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
           	
            	String jsonStr = EntityUtils.toString(entity, "UTF-8");
            	System.out.println(jsonStr);
            	ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        		vo = objectMapper.readValue(jsonStr, SmsVoList.class);
            	if(!"00000".equals(vo.getCode())){
            		return "X4," + vo.getCode();
            	}
        		//因為一次只送一筆，直接抓第一筆判斷
            	SmsVo smsObj = vo.getSmsvoList().get(0);
            	if(!"00000".equals(smsObj.getCode())){
            		return "X5," + smsObj.getCode();
            	}
            	return "X0,00000";
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return "X2," + e1.getMessage();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "X3," + e2.getMessage();
        }finally{
        	httpClient.close();
        }
		return "X9,";
	}
	

	private void sendErrorMail(ArrayList<String> msgList, String executeDate, String title, String cancelSuccessCount, String cancelFailCount, String unpaidSuccessCount, String unpaidFailCount) throws AddressException, UnsupportedEncodingException, MessagingException{
		
		 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		 Mailer mailer = new Mailer();
		 String smtpServer = configUtil.getString("smtp_host");
		 String userName = configUtil.getString("smtp_username");
		 String password = configUtil.getString("smtp_pwd");
		 String contentType = "text/html; charset=utf-8";
		 String auth = "smtp";
		 String subject = title;
		 String from = configUtil.getString("mail_from_address");
		 String to = configUtil.getString("mail_to_TerminationNotice_address");
		 String cc = "";
		 StringBuffer mailBody = new StringBuffer();
		 mailBody.append("時間：" + executeDate + "<BR>");
		 mailBody.append("客戶申請終止，成功筆數：" + cancelSuccessCount + "，失敗筆數：" + cancelFailCount + "<BR>");
		 mailBody.append("逾期繳費終止，成功筆數：" + unpaidSuccessCount + "，失敗筆數：" + unpaidFailCount + "<BR>");
		 if(msgList != null && msgList.size() > 0){
			 mailBody.append("<table border='1' cellspacing='0'><tr><td></td><td>OID</td><td>訊息</td></tr>");
			 int count = 0;
			 for(String str:msgList){
				 String s1 = str.split(",")[0];
				 String s2 = str.split(",")[1];
				 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td></tr>");
			 }
			 mailBody.append("</table>");
		 }
		 mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public MobileInsSmsService getMobileInsSmsService() {
		return mobileInsSmsService;
	}

	public void setMobileInsSmsService(MobileInsSmsService mobileInsSmsService) {
		this.mobileInsSmsService = mobileInsSmsService;
	}

	public MobileInsSmsNewTransService getMobileInsSmsNewTransService() {
		return mobileInsSmsNewTransService;
	}

	public void setMobileInsSmsNewTransService(
			MobileInsSmsNewTransService mobileInsSmsNewTransService) {
		this.mobileInsSmsNewTransService = mobileInsSmsNewTransService;
	}


}
