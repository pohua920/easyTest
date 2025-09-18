package com.tlg.aps.webService.claimBlockChainService.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.webService.claimBlockChainService.client.util.ConfigUtil;
import com.tlg.aps.webService.claimBlockChainService.client.util.TokenUtil;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryQueryResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryQueryVo;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class CompulsoryCaseQuery310 {
	
	private static final Logger log = LoggerFactory.getLogger(CompulsoryCaseQuery310.class);
	
	/**
	 * 3.10 Get compulsory cases blockchain status 查詢案件<br>
	 * hit_time 新增驗證，須為帶時區的 ISO 格式. ex: '2023-09-28T12:00Z', '2023-09-28T12:00+08:00', '2023-09-28T12:00+00:00', '2023-09-28T12:00:00Z', '2023-09-28T12:00:00+08:00', '2023-09-28T12:00:00+00:00'<br>
	 * 
	 * @param queryVo 查詢物件(事故時間、身分證字號、身分證字號類型)
	 * @param userCode 操作的使用者帳號
	 * @return
	 * @throws Exception
	 */
	public CompulsoryQueryResultVo compulsoryQuery(CompulsoryQueryVo queryVo, String userCode) throws Exception{
		CompulsoryQueryResultVo compulsoryQueryResultVo = new CompulsoryQueryResultVo();

		final String ip = ConfigUtil.getIp();
		String httpURL = "http://" + ip + "/api/v1/compulsory/cases";
		final String apiKey = ConfigUtil.getApiKey();
		String token = TokenUtil.generateToken(userCode);
		
		String basicAuth = "Bearer";
		URL url = null;
		HttpURLConnection con = null;
		try{
			if("".equals(queryVo.getHitTime()) || queryVo.getHitTime() == null){
				compulsoryQueryResultVo.setStatus("ParamsError");
				return compulsoryQueryResultVo;
			}
			if("".equals(queryVo.getIdNumber()) || queryVo.getIdNumber() == null){
				compulsoryQueryResultVo.setStatus("ParamsError");
				return compulsoryQueryResultVo;
			}
			if("".equals(queryVo.getIdNumberType()) || queryVo.getIdNumberType() == null){
				compulsoryQueryResultVo.setStatus("ParamsError");
				return compulsoryQueryResultVo;
			}
			
			String format = "yyyy-MM-dd'T'HH:mm";
			ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
			SimpleDateFormat dateFormat = local.get();
	        if (dateFormat == null) {
	            dateFormat = new SimpleDateFormat(format);
	            local.set(dateFormat);
	        }
	        httpURL = httpURL + "?hit_time=" + dateFormat.format(queryVo.getHitTime()) + "&id_number=" + queryVo.getIdNumber() + "&id_number_type=" + queryVo.getIdNumberType(); 
	        System.out.println("httpURL = " + httpURL);
			url = new URL (httpURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty ("Authorization ", basicAuth);
			con.setRequestProperty("api_key", apiKey);
			con.setRequestProperty("user", token);
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println("response.toString() = " + response.toString());
				ObjectMapper objectMapper = new ObjectMapper();
				try{
					compulsoryQueryResultVo = objectMapper.readValue(response.toString(), CompulsoryQueryResultVo.class);
					return compulsoryQueryResultVo;
				}catch(Exception e){
					throw new Exception("查詢案件時發生錯誤：" + e.getMessage());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(con != null){
				con.disconnect();				
			}
			url = null;
			con = null;
		}
	}
}
