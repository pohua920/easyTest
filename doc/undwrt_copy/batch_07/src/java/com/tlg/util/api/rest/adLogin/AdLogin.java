package com.tlg.util.api.rest.adLogin;

import ins.framework.common.ServiceFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.undwrt.common.service.facade.PlatConfigRuleService;
import com.tlg.util.api.rest.adLogin.entity.AdLoginVo;
import com.tlg.util.api.rest.adLogin.entity.ApplyTokenResponseVo;

/**
 * mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證  
 * @author DP0706
 *
 */
public class AdLogin {
	
	private static final Logger log = LoggerFactory.getLogger(AdLogin.class);
	
	public ApplyTokenResponseVo getToken() throws Exception{
		final String httpURL = ((PlatConfigRuleService)ServiceFactory.getService("platConfigRuleServiceNew")).getPlatConfigRuleAll("TOKEN_APPLY_WS_URL", "1").trim();
//		String httpURL = "http://192.168.190.32:8180/CWP/webService/token/apply";
		ApplyTokenResponseVo responseVo = new ApplyTokenResponseVo();
		URL url = null;
		HttpURLConnection con = null;
		try{
			url = new URL (httpURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			String jsonInputString = "{\"systemId\": \"NEWIMS_UNDWRT\"}";
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				
				JSONObject jsonObj = JSONObject.fromString(response.toString());
				responseVo = (ApplyTokenResponseVo) JSONObject.toBean(jsonObj,ApplyTokenResponseVo.class);
				
				if(!"00000".equals(responseVo.getCode())){
					log.debug("get token error code = " + responseVo.getCode());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}finally{
			url = null;
			con = null;
		}
		return responseVo;
	}
	
	public ApplyTokenResponseVo adValidate(AdLoginVo adLoginVo, String token) throws Exception{
		
		final String httpURL = ((PlatConfigRuleService)ServiceFactory.getService("platConfigRuleServiceNew")).getPlatConfigRuleAll("AD_LOGIN_WS_URL", "1").trim();
//		String httpURL = "http://192.168.190.32:8180/CWP/webService/token/login";
		String basicAuth = "Bearer " + token;
		URL url = null;
		HttpURLConnection con = null;
		ApplyTokenResponseVo responseVo = new ApplyTokenResponseVo();
		try{
			
			JSONObject jsonObject = JSONObject.fromBean(adLoginVo);
			String jsonStr = jsonObject.toString();
			
			url = new URL (httpURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty ("Authorization", basicAuth);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonStr.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				JSONObject jsonObj = JSONObject.fromString(response.toString());
				responseVo = (ApplyTokenResponseVo) JSONObject.toBean(jsonObj,ApplyTokenResponseVo.class);
				if(!"00000".equals(responseVo.getCode())){
					log.debug(adLoginVo.getUserId() + " login error , code = " + responseVo.getCode());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}finally{
			url = null;
			con = null;
		}
		return responseVo;
	}
	
	public static void main(String args[]) throws Exception{
		AdLogin ad = new AdLogin();
		ApplyTokenResponseVo responseVo = ad.getToken();
		
		AdLoginVo adLoginVo = new AdLoginVo();
		adLoginVo.setUserId("BI086");
		adLoginVo.setPwd("1qazxsw2#6");
		adLoginVo.setCheckCardNo("Y");
		responseVo = ad.adValidate(adLoginVo, responseVo.getToken());
		System.out.println(responseVo.getCode());
	}
}
