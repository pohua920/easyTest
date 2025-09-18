package com.tlg.aps.bs.generateEpolicyService.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.generateEpolicyService.GenerateEpolicyService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class GenerateEpolicyServiceImpl implements GenerateEpolicyService {
	
	private static final Logger logger = Logger.getLogger(GenerateEpolicyServiceImpl.class);
	private ConfigUtil configUtil;
	private PrpcmainService prpcmainService;
	
	
	@Override
	public Map<String, Object> queryToGeneratePAEpolicyService(String executeApp,String executeName) throws SystemException,
			Exception {
		
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "排程產生傷害險電子保單排程結果" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("coreEpolicyAnnounce");
		String cc = "";
		StringBuffer mailBody = new StringBuffer();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> successList = new ArrayList<String>();
		ArrayList<String> failList = new ArrayList<String>();
		ArrayList<String> existList = new ArrayList<String>();
		Set<String> handleredSet = new HashSet<String>();
		
		try{
			ArrayList<Prpcmain> prpcmainList = new ArrayList<Prpcmain>();
			
			Result result = prpcmainService.findPAAs400PrefilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			
			result = prpcmainService.findPAAs400HeccfilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			
			result = prpcmainService.findPAAs400TolfilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			if(prpcmainList.size() > 0){
				for(Prpcmain prpcmain : prpcmainList){
					
					if(handleredSet.contains(prpcmain.getPolicyno())){
						continue;
					}
					String resuslt = generatePAEpolicyService(prpcmain.getPolicyno(), executeApp, executeName, "PA");
					if("0".equals(resuslt)){
						successList.add(prpcmain.getPolicyno());
					}
					if("1".equals(resuslt)){
						existList.add(prpcmain.getPolicyno());
					}
					if("-1".equals(resuslt)){
						failList.add(prpcmain.getPolicyno());
					}
					handleredSet.add(prpcmain.getPolicyno());
				}
			}
			resultMap.put("total", String.valueOf(handleredSet.size()));
			resultMap.put("0", successList);
			resultMap.put("1", existList);
			resultMap.put("-1", failList);
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 產生傷害險電子保單發生異常" ;
			mailBody.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			throw e;
		}finally{
			
			mailBody.setLength(0);
			mailBody.append("執行結果：<br>" );
			mailBody.append("成功：" + successList.size() + "<br>");
			mailBody.append("失敗：" + failList.size() + "<br>");
			mailBody.append("檔案已產生過：" + existList.size() + "<br>");
			
			if(successList != null && successList.size() > 0){
				mailBody.append("成功清單：<br>" );
				for(String policyNo:successList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			if(failList != null && failList.size() > 0){
				mailBody.append("失敗清單：<br>" );
				for(String policyNo:failList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			if(existList != null && existList.size() > 0){
				mailBody.append("檔案已產生過清單：<br>" );
				for(String policyNo:existList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			
			successList = null;
			failList = null;
			existList = null;
			handleredSet = null;
		}
		return resultMap;
	}


	/**
	 * 傳入保單號以產生電子保單(傷害險及旅平險共用)
	 */
	@Override
	public String generatePAEpolicyService(String policyNo, String executeApp, String executeName, String riskCode) throws SystemException, Exception {

		String env = configUtil.getString("env");
		String coreEpolicyIp = configUtil.getString("coreEpolicyIp");
		String coreEpolicyPort = configUtil.getString("coreEpolicyPort");
		String baseUrl = "http://" + coreEpolicyIp +":" + coreEpolicyPort + "/prpins/";
		
		try{
			String url = baseUrl + "has/generateHPEpolicy.do?&riskCode=" + riskCode + "&classCode=C1&businessNo=" + policyNo + "&executeApp=" + executeApp + "&executeName="+ executeName;
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet get = new HttpGet(url);
				try (CloseableHttpResponse response = httpClient.execute(get)) {
					System.out.println("Executing url = " + url);
					//logger.info(url);
					String responseString = EntityUtils.toString(response.getEntity());
					if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						return "-1";
					}
					// 如果回傳是 200 OK 的話才輸出
					//logger.info(responseString);
					System.out.println(responseString);
					HashMap<String,Object> jsonMap = new ObjectMapper().readValue(responseString, HashMap.class);
					String msg = StringUtil.nullToSpace(jsonMap.get("msg").toString());
					System.out.println("msg = " + msg);
					if("".equals(msg)){
						//正常產生
						return "0";
					}
					if("fileExists".equals(msg)){
						return "1";
					}
				}
			}
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			throw e;
		}
		return "-1";
	}
	
	/**
	 * 產生旅平險電子保單
	 * 
	 */
	@Override
	public Map<String, Object> queryToGenerateTAEpolicyService(String executeApp,String executeName) throws SystemException,
			Exception {
		
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "排程產生旅平險電子保單排程結果" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("coreEpolicyAnnounce");
		String cc = "";
		StringBuffer mailBody = new StringBuffer();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> successList = new ArrayList<String>();
		ArrayList<String> failList = new ArrayList<String>();
		ArrayList<String> existList = new ArrayList<String>();
		Set<String> handleredSet = new HashSet<String>();
		
		try{
			ArrayList<Prpcmain> prpcmainList = new ArrayList<Prpcmain>();
			
			Result result = prpcmainService.findAs400TAPrefilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			
			result = prpcmainService.findAs400TAHeccfilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			
			result = prpcmainService.findAs400TATolfilEpolicy();
			if(result.getResObject() != null){
				ArrayList<Prpcmain> resultList = (ArrayList<Prpcmain>)result.getResObject();
				prpcmainList.addAll(resultList);
			}
			if(prpcmainList.size() > 0){
				for(Prpcmain prpcmain : prpcmainList){
					
					if(handleredSet.contains(prpcmain.getPolicyno())){
						continue;
					}
					String resuslt = generatePAEpolicyService(prpcmain.getPolicyno(), executeApp, executeName, "TA");
					if("0".equals(resuslt)){
						successList.add(prpcmain.getPolicyno());
					}
					if("1".equals(resuslt)){
						existList.add(prpcmain.getPolicyno());
					}
					if("-1".equals(resuslt)){
						failList.add(prpcmain.getPolicyno());
					}
					handleredSet.add(prpcmain.getPolicyno());
				}
			}
			resultMap.put("total", String.valueOf(handleredSet.size()));
			resultMap.put("0", successList);
			resultMap.put("1", existList);
			resultMap.put("-1", failList);
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 產生傷害險電子保單發生異常" ;
			mailBody.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			throw e;
		}finally{
			
			mailBody.setLength(0);
			mailBody.append("執行結果：<br>" );
			mailBody.append("成功：" + successList.size() + "<br>");
			mailBody.append("失敗：" + failList.size() + "<br>");
			mailBody.append("檔案已產生過：" + existList.size() + "<br>");
			
			if(successList != null && successList.size() > 0){
				mailBody.append("成功清單：<br>" );
				for(String policyNo:successList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			if(failList != null && failList.size() > 0){
				mailBody.append("失敗清單：<br>" );
				for(String policyNo:failList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			if(existList != null && existList.size() > 0){
				mailBody.append("檔案已產生過清單：<br>" );
				for(String policyNo:existList){
					mailBody.append(policyNo + "<br>" );
				}
			}
			
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			
			successList = null;
			failList = null;
			existList = null;
			handleredSet = null;
		}
		return resultMap;
	}



	public ConfigUtil getConfigUtil() {
		return configUtil;
	}


	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}


	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}
}
