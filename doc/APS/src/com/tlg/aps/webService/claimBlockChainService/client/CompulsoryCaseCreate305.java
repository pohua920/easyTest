package com.tlg.aps.webService.claimBlockChainService.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.webService.claimBlockChainService.client.util.ConfigUtil;
import com.tlg.aps.webService.claimBlockChainService.client.util.TokenUtil;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryCreateVo;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class CompulsoryCaseCreate305 {
	
	private static final Logger log = LoggerFactory.getLogger(CompulsoryCaseCreate305.class);
	
	/**
	 * 
	 * /compulsory/cases [v1.0.13] 3.5 Create a compulsory cases 建置案件
	 * 
	 * @param compulsoryCreateVo 案件物件
	 * @param userCode 操作的使用者帳號
	 * @return
	 * @throws Exception
	 */
	public CompulsoryCreateResultVo compulsoryCreate(CompulsoryCreateVo compulsoryCreateVo, String userCode) throws Exception{
		CompulsoryCreateResultVo compulsoryCreateResultVo = new CompulsoryCreateResultVo();

		final String ip = ConfigUtil.getIp();
		String httpURL = "http://" + ip + "/api/v1/compulsory/cases";
		final String apiKey = ConfigUtil.getApiKey();
		String token = TokenUtil.generateToken(userCode);
		
		String basicAuth = "Bearer";
		URL url = null;
		HttpURLConnection con = null;
		try{

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(compulsoryCreateVo);
//			String jsonInputString = "{\"applicant\":{\"applicant_id_number_type\":\"ID_NUMBER\",\"applicant_id_number\":\"A123456789\",\"applicant_birthday\":\"099-01-01\",\"applicant_name\":\"受害者\"},\"apportion\":{\"amount\":15986,\"health_insurance_apportion\":false,\"responsibility_type\":\"RT2\",\"loss_reason\":\"LR13\",\"loss_city\":\"LC11\",\"state_prices\":[{\"type\":\"MEDICAL\",\"amount\":120000,\"code\":\"C13\"}],\"vehicle_payload_capacity_unit\":\"PERSON\",\"vehicle_payload_capacity\":5,\"vehicle_type\":\"VT03\",\"recovery_item\":\"RI02\",\"driver_name\":\"駕駛人\",\"health_insurance\":false,\"other_responsibility_rate\":0,\"applicant_responsibility_rate\":0,\"responsibility_rate\":0,\"insurance_number\":\"180024AB123546789\",\"insurance_car_number\":\"TESE-123\"},\"case\":{\"applicant_role\":\"AR1\",\"police_name\":\"測試警察\",\"police_unit\":\"測試警察局\",\"hit_road\":\"測試路\",\"in_car_number\":\"TEST-123\",\"hit_time\":\"2024-01-01T21:41:31.000Z\",\"applicant_type\":\"PASSENGER\",\"case_number\":\"TEST123456789\"}}";
			System.out.println("jsonInputString = " + jsonInputString);
			url = new URL (httpURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty ("Authorization ", basicAuth);
			con.setRequestProperty("api_key", apiKey);
			con.setRequestProperty("user", token);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

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
				System.out.println("response.toString() = " + response.toString());
				try{
					compulsoryCreateResultVo = objectMapper.readValue(response.toString(), CompulsoryCreateResultVo.class);
					return compulsoryCreateResultVo;
				}catch(Exception e){
					throw new Exception("新增案件時發生錯誤：" + e.getMessage());
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
	
	public static void main(String args[]){
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.YEAR, 2024);
		cal.set(cal.MONTH, 0);
		cal.set(cal.DAY_OF_MONTH, 1);
		cal.set(cal.HOUR_OF_DAY, 21);
		cal.set(cal.MINUTE, 41);
		cal.set(cal.SECOND, 31);
		
		String format = "yyyy-MM-dd'T'kk:mm:ss.000'Z'";
		ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
		SimpleDateFormat dateFormat = local.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            local.set(dateFormat);
        }
//		ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
//		SimpleDateFormat dateFormat = local.get();
        System.out.println(dateFormat.format(cal.getTime()));
		
	}
}
