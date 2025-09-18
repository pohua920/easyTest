package com.tlg.aps.bs.clmSmsService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tlg.aps.bs.clmSmsService.ClmSmsService;
import com.tlg.aps.vo.RptResultBaseVo;
import com.tlg.aps.vo.ShortUrlVo;
import com.tlg.aps.vo.SmsVo;
import com.tlg.aps.vo.SmsVoList;
import com.tlg.exception.SystemException;
import com.tlg.msSqlAs400.entity.CtbcClmsmsRm;
import com.tlg.msSqlAs400.service.CtbcClmsmsRmService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GUID;
import com.tlg.util.JsonUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.ClmSms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

//這隻程式用到2個不同的資料庫連線，因此不特別設定transaction，成功就寫入
public class ClmSmsServiceImpl implements ClmSmsService {
	
	private static final Logger logger = Logger.getLogger(ClmSmsServiceImpl.class);
	private ConfigUtil configUtil;
	private CtbcClmsmsRmService ctbcClmsmsRmService;
	private com.tlg.xchg.service.ClmSmsService clmSmsService;
	
	
	@Override
	public void createPdf() throws SystemException, Exception {
		
		ArrayList<String> msgList = new ArrayList<String>();
		String clmSmsPdfUrl = this.configUtil.getString("clmSmsPdfUrl");
		if(StringUtil.isSpace(clmSmsPdfUrl)){
			throw new SystemException("無法取得理賠簡訊產生PDF位置！");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String executeDate = dateFormat.format(new Date());
		String successCount = "0";
		String failCount = "0";
		Result result = new Result();
		
		try{
			Map<String, String> params = new HashMap<String, String>();
			//取得尚未產生PDF資料
			params.put("pdfmakedateNull", "Y");
			result = ctbcClmsmsRmService.findCtbcClmsmsRmByParams(params);
			if(result.getResObject() != null){
				ArrayList<CtbcClmsmsRm> ctbcClmsmsRmList = (ArrayList<CtbcClmsmsRm>)result.getResObject();
				if(ctbcClmsmsRmList != null && ctbcClmsmsRmList.size() > 0){
					int resultCount[] = createPdf(clmSmsPdfUrl, msgList, ctbcClmsmsRmList, successCount, failCount);
					successCount = StringUtil.nullToSpace(resultCount[0] + "");
					failCount = StringUtil.nullToSpace(resultCount[1] + "");
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
			sendErrorMail(msgList, executeDate, "理賠給付簡訊通知-產生PDF", successCount, failCount);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void sendSms() throws SystemException, Exception {
		
		ArrayList<String> msgList = new ArrayList<String>();
		String smsUrl = this.configUtil.getString("smsUrl");
		if(StringUtil.isSpace(smsUrl)){
			throw new SystemException("無法取得CWP發送簡訊位置！");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String executeDate = dateFormat.format(new Date());
		String successCount = "0";
		String failCount = "0";
		Result result = new Result();
		
		try{
			Map<String, String> params = new HashMap<String, String>();
			//取得尚未產生PDF資料
			params.put("smsdateNull", "Y");
			result = this.clmSmsService.findClmSmsByParams(params);
			if(result.getResObject() != null){
				ArrayList<ClmSms> clmSmsList = (ArrayList<ClmSms>)result.getResObject();
				if(clmSmsList != null && clmSmsList.size() > 0){
					int resultCount[] = send(smsUrl, msgList, clmSmsList, successCount, failCount);
					successCount = StringUtil.nullToSpace(resultCount[0] + "");
					failCount = StringUtil.nullToSpace(resultCount[1] + "");
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
			sendErrorMail(msgList, executeDate, "理賠給付簡訊通知-簡訊發送", successCount, failCount);
		}
	}
	
	private int[] createPdf(String clmSmsPdfUrl, ArrayList<String> msgList, ArrayList<CtbcClmsmsRm> dataList, String successCount, String failCount) throws Exception{
		String clmSmsPdfPath = this.configUtil.getString("clmSmsPdfPath");
		int resultCount[] = {0,0};
		int sCount = 0;
		int fCount = 0;
		try{
			if(dataList.size() > 0){
				Calendar rightNow = Calendar.getInstance();
				rightNow.add(Calendar.DAY_OF_YEAR, 7);
				Date closeDate = rightNow.getTime();
				for(CtbcClmsmsRm ctbcClmsmsRm : dataList){
					
					try{
						String goid = ctbcClmsmsRm.getGoid();
						String claimno = ctbcClmsmsRm.getClaimno();
						//檢查手機號碼是否正常
						Pattern pattern = Pattern.compile("09\\d{8}");
						Matcher matcher = pattern.matcher(ctbcClmsmsRm.getPayphone());
						boolean CheckCellPhone = matcher.matches();
						if(!CheckCellPhone ){
							fCount++;
							msgList.add(goid + "," + claimno + ",手機號碼格式不正確");
							continue;
						}
						
						
						
						String jsonStr = "{\"asGOID\":\"" + goid + "\",\"pw\":\"" + ctbcClmsmsRm.getPayacountid() + "\"}";
						String resultStr = getPdf(clmSmsPdfUrl, jsonStr);
						if(!resultStr.startsWith("X0")){
							fCount++;
							msgList.add(goid + "," +  claimno + "," + resultStr);
							continue;
						}
						//還原PDF
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
						String folder = clmSmsPdfPath + File.separator + sdf.format(new Date());
						File folderFile = new File(folder);
						if(!folderFile.exists()) {
							folderFile.mkdirs();
						}

						byte[] byteArray = Base64.decodeBase64(resultStr.replaceAll("X0,", ""));
						//檔案為空白PDF時
						if(byteArray != null && byteArray.length < 2000){
							logger.info("goid : " + goid + "：PDF 空白");
							msgList.add(goid + "," +  claimno + ",PDF檔案空白");
							fCount++;
							continue;
						}
						String filePath = folder + File.separator + goid + ".pdf";
						File pdfFile = new File(filePath);
						FileUtils.writeByteArrayToFile(pdfFile, byteArray);
						
						ctbcClmsmsRm.setPdfmakedate(new Date());
						Result result = this.ctbcClmsmsRmService.updateCtbcClmsmsRm(ctbcClmsmsRm);
						if(result.getResObject() == null){
							logger.info("goid : " + goid + "：更新產生PDF時間失敗");
							msgList.add(goid + "," +  claimno + ",更新產生PDF時間失敗");
							fCount++;
							continue;
						}
						//新增CLMSMS
						String guid = getGuid(8);
						String guidBase64 = Base64.encodeBase64String(guid.getBytes());
						String shortpath = genShortUrl(guidBase64);
						if(StringUtil.isSpace(shortpath)){
							logger.info("goid : " + goid + "：產生短網址失敗");
							msgList.add(goid + "," + claimno + ",產生短網址失敗");
							fCount++;
							continue;
						}
						try{
							ClmSms clmsms = new ClmSms();
							clmsms.setClaimno(ctbcClmsmsRm.getClaimno());
							clmsms.setPolicyno(ctbcClmsmsRm.getPolicyno());
							clmsms.setGoid(ctbcClmsmsRm.getGoid());
							clmsms.setGuid(guid);
							clmsms.setGuid64(guidBase64);
							clmsms.setMobile(ctbcClmsmsRm.getPayphone());
							clmsms.setPdfUrl(filePath);
							clmsms.setShortpath(shortpath);
							clmsms.setClosedate(closeDate);
							clmsms.setIcreate("SYSBATCH");
							clmsms.setDcreate(new Date());
							result = this.clmSmsService.insertClmSms(clmsms);
							if(result.getResObject() == null){
								logger.info("goid : " + goid + "：新增ClmSms失敗");
								msgList.add(goid + "," +  claimno + ",新增ClmSms失敗");
								fCount++;
								continue;
							}
							
						}catch (Exception e) {
							logger.info("goid : " + goid + "：新增ClmSms失敗," + e.getMessage());
							msgList.add(goid + "," +  claimno + ",新增ClmSms失敗");
							fCount++;
							//還原Pdfmakedate
							ctbcClmsmsRm.setPdfmakedate(null);
							result = this.ctbcClmsmsRmService.updateCtbcClmsmsRm(ctbcClmsmsRm);
							if(result.getResObject() == null){
								logger.info("goid : " + goid + "：還原時更新產生PDF時間失敗");
							}
							//刪除檔案
							FileUtils.deleteQuietly(pdfFile);
							continue;
						}
						
						System.out.println(">>> pdfFile.length() = " + pdfFile.length());
						logger.info("create pdf file successfully : " + filePath);
						sCount++;
					}catch (Exception e) {
						e.printStackTrace();
						fCount++;
						continue;
					}
				}
			}else{
				//沒資料可以執行
				msgList.add( ",,目前無資料可執行" );
			}
		}finally{
			resultCount[0] = sCount;
			resultCount[1] = fCount;
		}
		return resultCount;
	}
	
	private String getGuid(int length) throws Exception{
		String guid = new GUID().toString(length);
		Map<String, String> params = new HashMap<String, String>();
		params.put("guid", guid);
		int count = this.clmSmsService.countClmSms(params);
		while(count > 0){
			guid = new GUID().toString(length);
			params.put("guid", guid);
			count = this.clmSmsService.countClmSms(params);
		}
		return guid;	
	}
	
	private int[] send(String smsUrl, ArrayList<String> msgList, ArrayList<ClmSms> dataList, String successCount, String failCount) throws Exception{
		int resultCount[] = {0,0};
		int sCount = 0;
		int fCount = 0;
		try{
			if(dataList.size() > 0){
				for(ClmSms clmSms:dataList){
					
					try{
						
						if(clmSms.getSmsdate() != null){
							fCount++;
							msgList.add(clmSms.getGoid() + "," + clmSms.getClaimno() + ",已有產生簡訊時間");
							continue;
						}
						
						if(new Date().after(clmSms.getClosedate())){
							fCount++;
							msgList.add(clmSms.getGoid() + "," + clmSms.getClaimno() + ",已超過檔案可以開啟的日期");
							continue;
						}
						//呼叫簡訊web service
						SmsVo sms = new SmsVo();
						sms.setTarget(clmSms.getMobile());
						sms.setMessage(getSmsWords(clmSms.getPolicyno(), clmSms.getShortpath()));
						//mantis：CLM0273 ，處理人員：DP0713，需求單編號：將遠傳簡訊轉移至蘋信短碼簡訊平台發送 START
						//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 START
						sms.setSubacc("CARCLMNTFY001");//old:NONCARCLMNTFY001/new:CARCLMNTFY001
						sms.setCreatedBy("SP-CLM_CTBC_CLMSMS");
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						String createdTime = dateFormat.format(new Date());
						sms.setCreatedTime(createdTime);
						//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 END
						//mantis：CLM0273 ，處理人員：DP0713，需求單編號：將遠傳簡訊轉移至蘋信短碼簡訊平台發送 END
						
						String msg = sendSms(smsUrl, sms);
						if(!msg.startsWith("X0")){
							fCount++;
							msgList.add(clmSms.getGoid() + "," + clmSms.getClaimno() + "," + msg);
							continue;
						}
						//更新時間
						clmSms.setSmsdate(new Date());
						this.clmSmsService.updateClmSms(clmSms);
						sCount++;
						
					}catch (Exception e) {
						e.printStackTrace();
						fCount++;
						msgList.add(clmSms.getGoid() + "," + clmSms.getClaimno() + ",發生異常：" + e.getMessage());
						continue;
					}
				}
			}else{
				//沒資料可以執行
				msgList.add( ",,目前無資料可執行" );
			}
		}finally{
			resultCount[0] = sCount;
			resultCount[1] = fCount;
		}
		return resultCount;
	}
	
	private String getSmsWords(String policyNo, String url){
		//mantis：CLM0152，處理人員：DP0713，需求單編號：APS-傷害險旅責旅綜理賠給付簡訊通知(簡訊資料轉入)
		//mantis：CLM0273 ，處理人員：DP0713，需求單編號：將遠傳簡訊轉移至蘋信短碼簡訊平台發送
		String s = "【中國信託產險】親愛的中信產險保戶您好，保單" + (null!=policyNo?policyNo.trim():"") +"理賠作業已完成，請於７日內下載明細，連結如下" + url + " \r\n(密碼為領款人ID)";
		return s;
	}
	
	private String getPdf(String clmPdfUrl, String jsonStr) throws Exception{
		
		if(StringUtil.isSpace(jsonStr)){
			return "X1,Json字串為null";
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
        	
        	HttpPost httpPost = new HttpPost(clmPdfUrl);  
        	StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
        	httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
//        	httpPost.setHeader("charset","UTF-8");
        	
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);

            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
           	
            	String resultJsonStr = EntityUtils.toString(entity, "UTF-8");
            	System.out.println(jsonStr);
            	ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        		RptResultBaseVo vo = objectMapper.readValue(resultJsonStr, RptResultBaseVo.class);
            	if(!"Y".equals(vo.getStatus())){
            		return "X4,";
            	}
            	return "X0," + vo.getRptStr();
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
	

	private void sendErrorMail(ArrayList<String> msgList, String executeDate, String title, String cancelSuccessCount, String cancelFailCount) throws AddressException, UnsupportedEncodingException, MessagingException{
		
		 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		 Mailer mailer = new Mailer();
		 String smtpServer = configUtil.getString("smtp_host");
		 String userName = configUtil.getString("smtp_username");
		 String password = configUtil.getString("smtp_pwd");
		 String contentType = "text/html; charset=utf-8";
		 String auth = "smtp";
		 //mantis： CLM0232 ，處理人員： DP0713 ，需求單編號：依照 (SIT/UAT/PROD)不同寫入
		 String subject = "[SIT]"+title;
		 String from = configUtil.getString("mail_from_address");
		 String to = configUtil.getString("mail_to_clmSms_address");
		 String cc = "";
		 StringBuffer mailBody = new StringBuffer();
		 mailBody.append("時間：" + executeDate + "成功筆數：" + cancelSuccessCount + "，失敗筆數：" + cancelFailCount + "<BR>");
		 if(msgList != null && msgList.size() > 0){
			 mailBody.append("<table border='1' cellspacing='0'><tr><td></td><td>GOID</td><td>賠案號</td><td>訊息</td></tr>");
			 int count = 0;
			 for(String str:msgList){
				 String s1 = str.split(",")[0];
				 String s2 = str.split(",")[1];
				 String s3 = str.split(",")[2];
				 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td><td>" + s3 + "</td></tr>");
			 }
			 mailBody.append("</table>");
		 }
		 mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
	}
	
	private String genShortUrl(String guidBase64) throws SystemException, Exception{
		String shortUrl = "";
		String cwpGenShortUrl = StringUtil.nullToSpace(this.configUtil.getString("cwpGenShortUrl"));
		if(StringUtil.isSpace(cwpGenShortUrl)){
			logger.debug("無法取得CWP產生短網址資料");
			throw new SystemException("無法取得網址路徑資料");
		}
		String clmSmsDownloadUrl = StringUtil.nullToSpace(this.configUtil.getString("clmSmsDownloadUrl"));
		if(StringUtil.isSpace(clmSmsDownloadUrl)){
			logger.debug("無法取得網址路徑資料");
			throw new SystemException("無法取得網址路徑資料");
		}
		clmSmsDownloadUrl = clmSmsDownloadUrl + guidBase64;
		 
		logger.info("genShortUrl guidBase64 : " + guidBase64 + ", pdfDownloadUrl : "+ clmSmsDownloadUrl);

	    try{
	    	String urlParameters = "{\"url\": \""+ clmSmsDownloadUrl +"\"}";
	    	byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
	    	URL url = new URL(cwpGenShortUrl);
	    	HttpURLConnection conn= (HttpURLConnection) url.openConnection();    
	    	conn.setRequestMethod("POST");
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	conn.setRequestProperty("Accept", "application/json");
	    	conn.setDoOutput(true);
	    	try(OutputStream os = conn.getOutputStream()) {
	    	    os.write(postData, 0, postData.length);			
	    	}
			
			int responseCode = conn.getResponseCode();
			logger.info("call genShortUrl Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				try(BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					logger.info(response.toString());
					Gson g = new Gson();  
					ShortUrlVo s = g.fromJson(response.toString(), ShortUrlVo.class);
					if(s != null){
						shortUrl = "https://ctbcins.com/" + s.getCode();
					}
					logger.info("shortUrl : " + shortUrl);
				}
			} else {
				logger.info("genShortUrl request did not work.");
			}
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

		return shortUrl;
	}
	
	
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public CtbcClmsmsRmService getCtbcClmsmsRmService() {
		return ctbcClmsmsRmService;
	}

	public void setCtbcClmsmsRmService(CtbcClmsmsRmService ctbcClmsmsRmService) {
		this.ctbcClmsmsRmService = ctbcClmsmsRmService;
	}

	public com.tlg.xchg.service.ClmSmsService getClmSmsService() {
		return clmSmsService;
	}

	public void setClmSmsService(com.tlg.xchg.service.ClmSmsService clmSmsService) {
		this.clmSmsService = clmSmsService;
	}



}
