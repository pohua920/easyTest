package com.tlg.aps.bs.smsService.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.smsService.ReceiptOtService;
import com.tlg.aps.bs.smsService.SMSRequestOtService;
import com.tlg.aps.bs.smsService.SmsService;
import com.tlg.aps.bs.smsService.UsagelogService;
import com.tlg.aps.vo.sms.SmsBatchDestVo;
import com.tlg.aps.vo.sms.SmsBatchSubmitReq;
import com.tlg.aps.vo.sms.SmsQueryDrReqVo;
import com.tlg.aps.vo.sms.SmsQueryDrResVo;
import com.tlg.aps.vo.sms.SmsReceiptVo;
import com.tlg.aps.vo.sms.SmsSubmitReqVo;
import com.tlg.aps.vo.sms.SubmitResVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.Receipt;
import com.tlg.msSqlSms.entity.SMSRequest;
import com.tlg.msSqlSms.entity.UsageLog;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GIGO;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class SmsServiceImpl implements SmsService {

	private ConfigUtil configUtil;
	private UsagelogService usagelogService;
	private SMSRequestOtService smsRequestOtService;
	private ReceiptOtService receiptOtService;
	private final String sysId = "CTBCINSA";
	private final String srcAddress = "01916800065210800223";
	
	private final String smsFailNotifyMail = "cf045@ctbcins.com";
	
	private final Logger logger = Logger.getLogger(SmsServiceImpl.class);

	@Override
	public Result batchSendMessage() throws SystemException, Exception {
		logger.info("SendMessage start - " + new Date());
		long startSmsTime = System.currentTimeMillis();
		System.out.println("startSmsTime = " + startSmsTime);
		try{
			//submitDate小於當前時間，且dr不等於2
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.TAIWAN);
			Map<String, String> params = new HashMap<String, String>();
			params.put("drFlagNotEq", "2");
			params.put("submitDateSt", timeFormatter.format(new Date()));
			params.put("targetIsNotEmpty", "Y");
			params.put("messageIsNotNull", "Y");
			Result result = smsRequestOtService.findTopSMSRequestByParams(params);
			if(result.getResObject() == null){
				result = new Result();
				result.setResObject(Boolean.TRUE);
				
				Message message = new Message();
				message.setMessageString("目前無可傳訊簡訊");
				result.setMessage(message);
				
				return result;
			}
			ArrayList<HashMap<String,SMSRequest>> dataList = new ArrayList<HashMap<String,SMSRequest>>();
			ArrayList<SMSRequest> smsRequestList = (ArrayList<SMSRequest>)result.getResObject();
			for(SMSRequest smsRequest : smsRequestList){
				logger.info("smsRequest.getTarget() = " + smsRequest.getTarget());
				//電話或訊息為空值則跳過
				if(StringUtil.isSpace(smsRequest.getTarget()) || StringUtil.isSpace(smsRequest.getMessage())){
					continue;
				}
				
				if(dataList.size() == 0){
					HashMap<String,SMSRequest> smsRequestMap = new HashMap<String,SMSRequest>();
					smsRequestMap.put(smsRequest.getTarget(), smsRequest);
					dataList.add(smsRequestMap);
				}else{
					int saveCount = 0;
					//從LIST中找既有的map是否已存有該組電話號碼
					for(Map<String,SMSRequest> smsRequestMap : dataList){
						if(!smsRequestMap.containsKey(smsRequest.getTarget())){
							smsRequestMap.put(smsRequest.getTarget(), smsRequest);
							saveCount++;
							break;
						}
					}
					//若saveCount == 0時，表示既有的Map都存有該支手機號碼
					if(saveCount == 0){
						HashMap<String,SMSRequest> smsRequestMap = new HashMap<String,SMSRequest>();
						smsRequestMap.put(smsRequest.getTarget(), smsRequest);
						dataList.add(smsRequestMap);
					}
				}
			}
			
			for(HashMap<String,SMSRequest> smsRequestMap : dataList){
				
				if(smsRequestMap != null && smsRequestMap.size() == 1){
					sendSms(smsRequestMap);
				}
				if(smsRequestMap != null && smsRequestMap.size() > 1){
					batchSendSms(smsRequestMap);
				}
			}
		}catch (SystemException se) {
			logger.error(se);
			se.printStackTrace();
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			logger.info("Sms耗時 = " + (startSmsTime - System.currentTimeMillis()));
			logger.info("SendMessage end - " + new Date());
		}
		
		return null;
	}
	
	@Override
	public void batchHandleExceMessage() throws SystemException, Exception {
		logger.info("Exception Message start - " + new Date());
		long startSmsTime = System.currentTimeMillis();
		System.out.println("startSmsTime = " + startSmsTime);
		try{
			//找出dr等於2未被正常更新為1的資料，則更新為 1，等下次排程送出 
			Map<String, String> params = new HashMap<String, String>();
			params.put("drFlag", "2");
			params.put("targetIsNotEmpty", "Y");
			params.put("messageIsNotNull", "Y");
			Result result = smsRequestOtService.findTopSMSRequestByParams(params);
			if(result.getResObject() != null){
				ArrayList<SMSRequest> smsRequestList = (ArrayList<SMSRequest>)result.getResObject();
				for(SMSRequest smsRequest : smsRequestList){
					logger.info("smsRequest.getTarget() = " + smsRequest.getTarget());
					//電話或訊息為空值則跳過
					if(StringUtil.isSpace(smsRequest.getTarget()) || StringUtil.isSpace(smsRequest.getMessage())){
						continue;
					}
					smsRequest.setDrFlag("1");
					this.smsRequestOtService.updateSMSRequest(smsRequest);
				}
			}
			//刪除
			this.smsRequestOtService.removeSMSRequestNullData();
			
		}catch (SystemException se) {
			logger.error(se);
			se.printStackTrace();
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			logger.info("Sms耗時 = " + (startSmsTime - System.currentTimeMillis()));
			logger.info("Exception Message end - " + new Date());
		}
	}
	
	private void sendSms(HashMap<String,SMSRequest> smsRequestMap){
		logger.info("single SendMessage start - " + new Date());
		long startSmsTime = System.currentTimeMillis();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.TAIWAN);
		
		try{
			
			if(smsRequestMap == null){
				return;
			}
			SmsSubmitReqVo vo = new SmsSubmitReqVo();
			vo.setSysId(sysId);
			vo.setSrcAddress(srcAddress);
			for (Map.Entry<String, SMSRequest> entry : smsRequestMap.entrySet()) {
				
				SMSRequest smsRequest = smsRequestMap.get(entry.getKey());
				
				//若沒有訊息內容則不處理
				if(StringUtil.isSpace(smsRequest.getMessage())){
					continue;
				}
				
				//檢查serial是否已發送過
				Map<String, String> params = new HashMap<String, String>();
				params.put("serial", smsRequest.getSerial());
				int usagelogCount = this.usagelogService.countUsageLog(params);
				if(usagelogCount > 0){
					logger.info(">>>>>> serial 已重複  = " + smsRequest.getSerial());
					continue;
				}
				
				//更新SMSRequest.drflag = '2'
				smsRequest.setDrFlag("2");
				Result result = this.smsRequestOtService.updateSMSRequest(smsRequest);
				if(result.getResObject() == null){
					logger.info("update SMSRequest drflag to 2 fail serial = " + smsRequest.getSerial());
					continue;
				}
				
				//若沒問題則進入web service發送
				ArrayList<String> destAddressList = new ArrayList<String>();
				destAddressList.add(smsRequest.getTarget());
				vo.setDestAddressList(destAddressList);
				vo.setSmsBody(Base64.encodeBase64String(smsRequest.getMessage().getBytes("UTF-8")));

				
				Date currentTime = new Date();
				String dateTime = timeFormatter.format(currentTime);
				SubmitResVo responsevo = null;
				//呼叫web service
				try{
					responsevo = sendSms(vo);
				}catch (Exception ee) {
					// 發生SocketException時，更新回1，下一次再執行
					logger.info(ee.getMessage() + " - update SMSRequest drflag to 1 fail serial = " + smsRequest.getSerial());
					smsRequest.setDrFlag("1");
					result = this.smsRequestOtService.updateSMSRequest(smsRequest);
					if(result.getResObject() == null){
						logger.info("update SMSRequest drflag to 1 fail serial = " + smsRequest.getSerial());
						continue;
					}
					continue;
				}
				
				//搬移
				UsageLog usagelog = new UsageLog();
				usagelog.setCorpId(smsRequest.getCorpId());
				usagelog.setDeliverDate(smsRequest.getDeliverDate());
				usagelog.setDrFlag("1");
				usagelog.setLanguage(smsRequest.getLanguage());
				usagelog.setMessage(smsRequest.getMessage());
				usagelog.setSerial(smsRequest.getSerial());
				usagelog.setSubmitDate(smsRequest.getSubmitDate());
				usagelog.setTarget(smsRequest.getTarget());
				usagelog.setMgwId(null);
				
				if(!"00000".equals(responsevo.getResultCode()) && !"00010".equals(responsevo.getResultCode())){
					usagelog.setErrCode(responsevo.getResultCode());
					
					//簡訊回傳非 00000及00010
					Mailer mailer = new Mailer();
					String smtpServer = configUtil.getString("smtp_host");
					String userName = configUtil.getString("smtp_username");
					String password = configUtil.getString("smtp_pwd");
					String contentType = "text/html; charset=utf-8";
					String auth = "smtp";
					String subject = "簡訊web service 發送失敗通知";
					String from = configUtil.getString("mail_from_address");
					String to = smsFailNotifyMail;
					String cc = "";
					String mailBody = usagelog.getSerial() + "-" + responsevo.getResultCode() + "-" + responsevo.getResultText();
					mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
				}
				
				usagelog.setMessageId(responsevo.getMessageId());
				usagelog.setStatus("");
				usagelog.setStatusDate(dateTime);
				usagelog.setErrCode(responsevo.getResultCode());
				
				usagelogService.insertUsageLog(usagelog);
				
				//刪除SMSRequest
				smsRequestOtService.removeSMSRequest(smsRequest.getSerial());
			}
			
		}catch (SystemException se) {
			logger.error(se);
			se.printStackTrace();
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			logger.debug("單筆Sms耗時 = " + (startSmsTime - System.currentTimeMillis()));
			logger.debug("batchSendMessage end - " + new Date());
		}
	}
	
	private void batchSendSms(HashMap<String,SMSRequest> smsRequestMap){
		logger.info("batchSendMessage start - " + new Date());
		long startSmsTime = System.currentTimeMillis();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.TAIWAN);
		
		try{
			
			if(smsRequestMap == null){
				return;
			}
			SmsBatchSubmitReq sbsr = new SmsBatchSubmitReq();
			sbsr.setSysId(sysId);
			sbsr.setSrcAddress(srcAddress);
			ArrayList<SmsBatchDestVo> smsBatchDestList = new ArrayList<SmsBatchDestVo>();
			System.out.println("smsRequestMap = " + smsRequestMap.size());
			//更新SMSRequest.drflag = '2'
			for (Map.Entry<String, SMSRequest> entry : smsRequestMap.entrySet()) {
				SMSRequest smsRequest = smsRequestMap.get(entry.getKey());
				
				//若沒有訊息內容則不處理
				if(StringUtil.isSpace(smsRequest.getMessage())){
					continue;
				}
				
				//檢查serial是否已發送過
				Map<String, String> params = new HashMap<String, String>();
				params.put("serial", smsRequest.getSerial());
				int usagelogCount = this.usagelogService.countUsageLog(params);
				if(usagelogCount > 0){
					logger.info(">>>>>> serial 已重複  = " + smsRequest.getSerial());
					continue;
				}
				
				//更新SMSRequest.drflag = '2'
				smsRequest.setDrFlag("2");
				Result result = this.smsRequestOtService.updateSMSRequest(smsRequest);
				if(result.getResObject() == null){
					logger.info("update SMSRequest drflag to 2 fail serial = " + smsRequest.getSerial());
					continue;
				}
				
				//若沒問題則進入web service發送
				SmsBatchDestVo vo = new SmsBatchDestVo();
				vo.setDestAddress(smsRequest.getTarget());
				vo.setSmsBody(Base64.encodeBase64String(smsRequest.getMessage().getBytes("UTF-8")));
				smsBatchDestList.add(vo);
			}
			
			sbsr.setSmsBatchDestList(smsBatchDestList);
			Date currentTime = new Date();
			String sendTime = timeFormatter.format(currentTime);
			
			//呼叫web service
			SubmitResVo responsevo = sendSms(sbsr);
			
			
			if(!"00000".equals(responsevo.getResultCode()) && !"00010".equals(responsevo.getResultCode())){
				
				//簡訊回傳非 00000及00010
				Mailer mailer = new Mailer();
				String smtpServer = configUtil.getString("smtp_host");
				String userName = configUtil.getString("smtp_username");
				String password = configUtil.getString("smtp_pwd");
				String contentType = "text/html; charset=utf-8";
				String auth = "smtp";
				String subject = "簡訊web service 發送失敗通知";
				String from = configUtil.getString("mail_from_address");
				String to = smsFailNotifyMail;
				String cc = "";
				String mailBody = responsevo.getResultCode() + "-" + responsevo.getResultText();
				mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
			}
			
			long startRemoveTime = System.currentTimeMillis();
			System.out.println("startRemoveTime = " + startRemoveTime);
			
			//搬移資料
			for (Map.Entry<String, SMSRequest> entry : smsRequestMap.entrySet()) {
				
				SMSRequest smsRequest = smsRequestMap.get(entry.getKey());
				
				try{
					//新增至UsageLog
					UsageLog usagelog = new UsageLog();
					usagelog.setCorpId(smsRequest.getCorpId());
					usagelog.setDeliverDate(smsRequest.getDeliverDate());
					usagelog.setDrFlag("1");
					usagelog.setLanguage(smsRequest.getLanguage());
					usagelog.setMessage(smsRequest.getMessage());
					usagelog.setSerial(smsRequest.getSerial());
					usagelog.setSubmitDate(smsRequest.getSubmitDate());
					usagelog.setTarget(smsRequest.getTarget());
					usagelog.setMgwId(null);
					usagelog.setMessageId(responsevo.getMessageId());
					usagelog.setStatus("");
					usagelog.setStatusDate(sendTime);
					usagelog.setErrCode(responsevo.getResultCode());
					
					usagelogService.insertUsageLog(usagelog);
					//刪除SMSRequest
					smsRequestOtService.removeSMSRequest(entry.getValue().getSerial());

				}catch (SystemException se1) {
					logger.error(se1);
					se1.printStackTrace();
				}catch (Exception e1) {
					logger.error(e1);
					e1.printStackTrace();
				}
			}
			long endRemoveTime = System.currentTimeMillis();
			System.out.println("搬移耗時 = " + (startRemoveTime - endRemoveTime));

		}catch (SystemException se) {
			logger.error(se);
			se.printStackTrace();
			
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			long endSmsTime = System.currentTimeMillis();
			logger.debug("批次Sms耗時 = " + (startSmsTime - endSmsTime));
			logger.debug("batchSendMessage end - " + new Date());
		}
	}
	
	
	private SubmitResVo sendSms(SmsBatchSubmitReq vo) throws Exception{
		String smsBatchSubmitUrl = "http://61.20.32.60:6600/mpushapi/smsbatchsubmit";
		
		SubmitResVo responseVo = null;
		StringBuilder stringBuilder = new StringBuilder();
		CloseableHttpClient httpClient = null;
		logger.info("send Batch SMS - start");
		try {
			
			if(vo == null){
				return null;
			}
			
			Marshaller marshaller = JAXBContext.newInstance(SmsBatchSubmitReq.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); //是否省略xml head訊息
		       
			ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
			marshaller.marshal(vo, objByteArrayOutputStream);
			byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
			String xml = new String(vbResultXML,"UTF-8");
			System.out.println(">>>>>>>xml  = " + xml);
			
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000).build();
//			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			httpClient = HttpClients.createDefault();
            
			HttpPost httpPost = new HttpPost(smsBatchSubmitUrl);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("xml", ""));
			params.add(new BasicNameValuePair("xml", xml));
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			httpPost.addHeader("Content-type", MediaType.APPLICATION_FORM_URLENCODED);
			
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode = " + statusCode);
			if (statusCode != 200) {
				throw new Exception("連線至遠傳簡訊發送系統失敗");
			}
			HttpEntity httpEntity = response.getEntity();
			// get the response content
			InputStream inputStream = httpEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String strReadLine = bufferedReader.readLine();
			// iterate to get the data and append in StringBuilder
			while (strReadLine != null) {
				stringBuilder.append(strReadLine);
				strReadLine = bufferedReader.readLine();
				if (strReadLine != null) {
					stringBuilder.append("\n");
				}
			}
			logger.info("stringBuilder = " + stringBuilder.toString());
			
			java.io.StringReader reader = new java.io.StringReader(stringBuilder.toString());
			JAXBContext context = JAXBContext.newInstance(SubmitResVo.class);
			Unmarshaller objUnmarshaller = context.createUnmarshaller();
			responseVo = (SubmitResVo)objUnmarshaller.unmarshal(reader);
			
		} catch (SystemException se) {
			logger.error(se.getMessage(), se);
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}finally{
			logger.info("send SMS - end");
			System.out.println("queryData - end");
			if(httpClient != null){
				httpClient.close();
				httpClient = null;
			}
		}
		return responseVo;
	}
	
	private SubmitResVo sendSms(SmsSubmitReqVo vo) throws Exception{
		String smsSubmitUrl = "http://61.20.32.60:6600/mpushapi/smssubmit";
		SubmitResVo responseVo = null;
		StringBuilder stringBuilder = new StringBuilder();
		CloseableHttpClient httpClient = null;
		logger.info("send SMS - start");
		try {
			
			if(vo == null){
				return null;
			}
			
			Marshaller marshaller = JAXBContext.newInstance(SmsSubmitReqVo.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); //是否省略xml head訊息
		       
			ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
			marshaller.marshal(vo, objByteArrayOutputStream);
			byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
			String xml = new String(vbResultXML,"UTF-8");
			System.out.println(">>>>>>>xml  = " + xml);
			
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000).build();
			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            
			HttpPost httpPost = new HttpPost(smsSubmitUrl);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("xml", xml));
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			httpPost.addHeader("Content-type", MediaType.APPLICATION_FORM_URLENCODED);
			
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode = " + statusCode);
			if (statusCode != 200) {
				throw new Exception("連線至遠傳簡訊發送系統失敗");
			}
			HttpEntity httpEntity = response.getEntity();
			// get the response content
			InputStream inputStream = httpEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String strReadLine = bufferedReader.readLine();
			// iterate to get the data and append in StringBuilder
			while (strReadLine != null) {
				stringBuilder.append(strReadLine);
				strReadLine = bufferedReader.readLine();
				if (strReadLine != null) {
					stringBuilder.append("\n");
				}
			}
			logger.info("stringBuilder = " + stringBuilder.toString());
			
			java.io.StringReader reader = new java.io.StringReader(stringBuilder.toString());
			JAXBContext context = JAXBContext.newInstance(SubmitResVo.class);
			Unmarshaller objUnmarshaller = context.createUnmarshaller();
			responseVo = (SubmitResVo)objUnmarshaller.unmarshal(reader);
			
		} catch (SystemException se) {
			logger.error(se.getMessage(), se);
			se.printStackTrace();
			throw se;
		} catch (SocketException soe) {
			logger.error(soe.getMessage(), soe);
			soe.printStackTrace();
			throw soe;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}finally{
			logger.info("send SMS - end");
			System.out.println("queryData - end");
			if(httpClient != null){
				httpClient.close();
				httpClient = null;
			}
		}
		return responseVo;
	}
	
	
	@Override
	public Result queryDeliverReport() throws SystemException, Exception {
		try{
			while(true){
				ArrayList<UsageLog> list = queryUsageLog();
				if(list == null){
					break;
				}
				
				Map<String, ArrayList<UsageLog>> tempMap = new HashMap<String, ArrayList<UsageLog>>();
				for(UsageLog usageLog : list){
					if(StringUtil.isSpace(usageLog.getStatus())){
						String messageId = usageLog.getMessageId();
						ArrayList<UsageLog> logList = null;
						if(tempMap.containsKey(messageId)){
							logList = tempMap.get(messageId);
						}else{
							logList = new ArrayList<UsageLog>();
						}
						logList.add(usageLog);
						tempMap.put(messageId, logList);
					}
				}
				
				for (Map.Entry<String, ArrayList<UsageLog>> entry : tempMap.entrySet()) {
					SmsQueryDrReqVo vo = new SmsQueryDrReqVo();
					vo.setSysId(this.sysId);
					vo.setMessageId(entry.getKey());
					ArrayList<String> destAddressList = new ArrayList<String>();
					ArrayList<UsageLog> usageLogList = tempMap.get(entry.getKey());
					for(UsageLog usageLog:usageLogList){
						destAddressList.add(usageLog.getTarget());
					}
					vo.setDestAddressList(destAddressList);
					//傳送至webservice查詢
					SmsQueryDrResVo resp = queryDr(vo);
					
					if(resp != null && resp.getReceiptList() != null){
						for(SmsReceiptVo smsReceiptVo:resp.getReceiptList()){
							//新增紀錄
							Receipt receipt = new Receipt();
							GIGO.fill(receipt, smsReceiptVo);
							Result result = receiptOtService.insertReceipt(receipt);
							if(result.getResObject() == null){
								continue;
							}
							//用messageId及destAddress找出對應的資料
							if(StringUtil.isSpace(receipt.getMessageId()) || StringUtil.isSpace(receipt.getDestAddress())){
								logger.info("KEY值有問題 receipt.getMessageId() = " + receipt.getMessageId() + ", receipt.getDestAddress() = " + receipt.getDestAddress());
								break;
							}
							Map<String, String> params = new HashMap<String, String>();
							params.put("messageId", receipt.getMessageId());
							params.put("target", receipt.getDestAddress());
							result = this.usagelogService.findUsageLogByParams(params);
							if(result.getResObject() != null){
								ArrayList<UsageLog> ulist = (ArrayList<UsageLog>)result.getResObject();
								UsageLog usageLog = ulist.get(0);
								usageLog.setMgwId(receipt.getSeq());
								this.usagelogService.updateUsageLogResp(usageLog);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private ArrayList<UsageLog> queryUsageLog(){
		try{
			
//			Date date = new Date();
//			Calendar calendar = GregorianCalendar.getInstance(); 
//			calendar.setTime(date); 
//			int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//			int lastHour = currentHour - 1;
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			//先找出status是空的
			Map<String, String> params = new HashMap<String, String>();
//			params.put("gtHour", sdf.format(date) + lastHour);
//			params.put("stHour", sdf.format(date) + currentHour);
			
			
			Result result = usagelogService.findUsageLogBySubmitHour(params);
			if(result.getResObject() != null){
				return (ArrayList<UsageLog>)result.getResObject();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SmsQueryDrResVo queryDr(SmsQueryDrReqVo vo) throws Exception{
		String smsDrUrl = "http://61.20.32.60:6600/mpushapi/smsquerydr";
		
		SmsQueryDrResVo responseVo = null;
		StringBuilder stringBuilder = new StringBuilder();
		CloseableHttpClient httpClient = null;
		logger.info("QUERY DR - start");
		try {
			
			if(vo == null){
				return null;
			}
			
			Marshaller marshaller = JAXBContext.newInstance(SmsQueryDrReqVo.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); //是否省略xml head訊息
		       
			ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
			marshaller.marshal(vo, objByteArrayOutputStream);
			byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
			String xml = new String(vbResultXML,"UTF-8");
			System.out.println(">>>>>>>xml  = " + xml);
			
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000).build();
//			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			httpClient = HttpClients.createDefault();
            
			HttpPost httpPost = new HttpPost(smsDrUrl);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("xml", xml));
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			httpPost.addHeader("Content-type", MediaType.APPLICATION_FORM_URLENCODED);
			
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode = " + statusCode);
			if (statusCode != 200) {
				throw new Exception("連線至遠傳簡訊發送系統失敗");
			}
			HttpEntity httpEntity = response.getEntity();
			// get the response content
			InputStream inputStream = httpEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String strReadLine = bufferedReader.readLine();
			// iterate to get the data and append in StringBuilder
			while (strReadLine != null) {
				stringBuilder.append(strReadLine);
				strReadLine = bufferedReader.readLine();
				if (strReadLine != null) {
					stringBuilder.append("\n");
				}
			}
			logger.info("stringBuilder = " + stringBuilder.toString());
			
			java.io.StringReader reader = new java.io.StringReader(stringBuilder.toString());
			JAXBContext context = JAXBContext.newInstance(SmsQueryDrResVo.class);
			Unmarshaller objUnmarshaller = context.createUnmarshaller();
			responseVo = (SmsQueryDrResVo)objUnmarshaller.unmarshal(reader);
			
//			ArrayList<SmsReceiptVo> receiptList = responseVo.getReceiptList();
//			if(receiptList != null){
//				for(SmsReceiptVo svo:receiptList){
//					if(svo == null){
//						continue;
//					}
//					Receipt receipt = new Receipt();
//					GIGO.fill(receipt, svo);
//					Result result = this.receiptOtService.insertReceipt(receipt);
//					if(result.getResObject() == null){
//						continue;
//					}
//					Map<String, String> param = new HashMap<String, String>();
//					param.put("message_id", svo.getMessageId());
//					param.put("target", svo.getDestAddress());
//					result = this.usagelogService.findUsageLogByParams(param);
//					if(result.getResObject() != null){
//						ArrayList<UsageLog> uList = (ArrayList<UsageLog>)result.getResObject();
//						UsageLog usageLog = uList.get(0);
//						usageLog.setMgwId(svo.getSeq());
//						this.usagelogService.updateUsageLogResp(usageLog);
//					}
//				}
//			}
		} catch (SystemException se) {
			logger.error(se.getMessage(), se);
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}finally{
			logger.info("send SMS - end");
			System.out.println("queryData - end");
			if(httpClient != null){
				httpClient.close();
				httpClient = null;
			}
		}
		return responseVo;
	}
	
	public static void main(String args[]) throws JAXBException, UnsupportedEncodingException{
//		SmsQueryDrReqVo vo = new SmsQueryDrReqVo();
//		vo.setMessageId("236233304955");
//		vo.setSysId("CTBCINSA");
//		
//		ArrayList<String> destAddressList = new ArrayList<String>();
//		destAddressList.add("0917922557");
//		vo.setDestAddressList(destAddressList);
//		
//		Marshaller marshaller = JAXBContext.newInstance(SmsQueryDrReqVo.class).createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true); //是否生成xml字串
//		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); //是否省略xml head訊息
//	       
//		ByteArrayOutputStream objByteArrayOutputStream = new ByteArrayOutputStream();
//		marshaller.marshal(vo, objByteArrayOutputStream);
//		byte[] vbResultXML = objByteArrayOutputStream.toByteArray();
//		String xml = new String(vbResultXML,"UTF-8");
//		System.out.println(">>>>>>>xml  = " + xml);
//		
//		Date date = new Date();
//		Calendar calendar = GregorianCalendar.getInstance(); 
//		calendar.setTime(date); 
//		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//		System.out.println(">>>>>>>currentHour  = " + currentHour);
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SmsQueryDrRes><ResultCode>00000</ResultCode><ResultText>Request successfully processed.</ResultText><Receipt><MessageId>237233448260</MessageId><DestAddress>0989391942</DestAddress><DeliveryStatus>delivered</DeliveryStatus><ErrorCode>000</ErrorCode><SubmitDate>220908141517</SubmitDate><DoneDate>220908141530</DoneDate><RetrieveDate>220908141530</RetrieveDate><Seq>1/1</Seq></Receipt><Receipt><MessageId>237233448260</MessageId><DestAddress>0972305200</DestAddress><DeliveryStatus>delivered</DeliveryStatus><ErrorCode>000</ErrorCode><SubmitDate>220908141517</SubmitDate><DoneDate>220908141529</DoneDate><RetrieveDate>220908141529</RetrieveDate><Seq>1/1</Seq></Receipt></SmsQueryDrRes>";
		java.io.StringReader reader = new java.io.StringReader(xml);
		JAXBContext context = JAXBContext.newInstance(SmsQueryDrResVo.class);
		Unmarshaller objUnmarshaller = context.createUnmarshaller();
		SmsQueryDrResVo responseVo = (SmsQueryDrResVo)objUnmarshaller.unmarshal(reader);
		System.out.println(responseVo.getReceiptList());
	}


	public UsagelogService getUsagelogService() {
		return usagelogService;
	}

	public void setUsagelogService(UsagelogService usagelogService) {
		this.usagelogService = usagelogService;
	}

	public SMSRequestOtService getSmsRequestOtService() {
		return smsRequestOtService;
	}

	public void setSmsRequestOtService(SMSRequestOtService smsRequestOtService) {
		this.smsRequestOtService = smsRequestOtService;
	}


	public ConfigUtil getConfigUtil() {
		return configUtil;
	}


	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ReceiptOtService getReceiptOtService() {
		return receiptOtService;
	}

	public void setReceiptOtService(ReceiptOtService receiptOtService) {
		this.receiptOtService = receiptOtService;
	}
	
}
