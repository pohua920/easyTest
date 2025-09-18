package com.tlg.aps.webService.claimBlockChainService.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.webService.claimBlockChainService.client.util.ConfigUtil;
import com.tlg.aps.webService.claimBlockChainService.client.util.TokenUtil;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateResultVo;
import com.tlg.aps.webService.claimBlockChainService.client.vo.CompulsoryUpdateVo;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class CompulsoryCaseUpdate307 {
	
	private static final Logger log = LoggerFactory.getLogger(CompulsoryCaseUpdate307.class);
	
	/**
	 * 
	 * /compulsory/cases/update/{id} [v1.0.13] 3.7 Update compulsory cases by id 更新案件
	 * 
	 * @param compulsoryUpdateVo 案件物件
	 * @param userCode 操作的使用者帳號
	 * @return
	 * @throws Exception
	 */
	public CompulsoryUpdateResultVo compulsoryUpdate(CompulsoryUpdateVo compulsoryUpdateVo, String userCode) throws Exception{
		CompulsoryUpdateResultVo compulsoryUpdateResultVo = new CompulsoryUpdateResultVo();

		final String ip = ConfigUtil.getIp();
		String httpURL = "http://" + ip + "/api/v1/compulsory/cases/update/" + compulsoryUpdateVo.getCaseId();
		final String apiKey = ConfigUtil.getApiKey();
		String token = TokenUtil.generateToken(userCode);
		
		String basicAuth = "Bearer";
		URL url = null;
		HttpURLConnection con = null;
		try{

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(compulsoryUpdateVo);
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
					compulsoryUpdateResultVo = objectMapper.readValue(response.toString(), CompulsoryUpdateResultVo.class);
					return compulsoryUpdateResultVo;
				}catch(Exception e){
					throw new Exception("修改案件時發生錯誤：" + e.getMessage());
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
	
	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException{
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(cal.YEAR, 2024);
//		cal.set(cal.MONTH, 0);
//		cal.set(cal.DAY_OF_MONTH, 1);
//		cal.set(cal.HOUR_OF_DAY, 21);
//		cal.set(cal.MINUTE, 41);
//		cal.set(cal.SECOND, 31);
//		
//		String format = "yyyy-MM-dd'T'kk:mm:ss.000'Z'";
//		ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
//		SimpleDateFormat dateFormat = local.get();
//        if (dateFormat == null) {
//            dateFormat = new SimpleDateFormat(format);
//            local.set(dateFormat);
//        }
////		ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
////		SimpleDateFormat dateFormat = local.get();
//        System.out.println(dateFormat.format(cal.getTime()));
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String responseStr = "{\"status\":\"Success\",\"case\":{\"id\":176,\"apportion\":{\"apportion_actions\":[\"UPDATE\",\"DELETE\",\"NOTICE\"],\"id\":178,\"created_at\":\"2024-02-22T12:06:59.131Z\",\"updated_at\":\"2024-02-27T08:41:20.613Z\",\"deleted_at\":null,\"compulsoryCaseId\":176,\"status\":\"CREATED\",\"amount\":15986,\"noticed_at\":null,\"completed_at\":null,\"approved_at\":null,\"rejected_at\":null,\"apportion_key\":\"apportionKey_74ef4912c751fa36f61d516db41882b1e8e856981fd98882758b2c801e900ef1\",\"apportion_index\":1,\"responsibility_type\":\"RT2\",\"insurance_number\":\"180024AB123546789\",\"responsibility_rate\":0,\"applicant_responsibility_rate\":0,\"other_responsibility_rate\":100,\"driver_name\":\"駕駛人\",\"vehicle_type\":\"VT03\",\"vehicle_payload_capacity\":5,\"vehicle_payload_capacity_unit\":\"PERSON\",\"loss_reason\":\"LR13\",\"loss_city\":\"LC11\",\"recovery_item\":\"RI02\",\"health_insurance\":false,\"insurance_car_number\":\"TESE-123\",\"apportion_type\":\"ByResponsibilityRate\",\"health_insurance_apportion\":false,\"charges\":[{\"id\":891,\"created_at\":\"2024-02-22T12:06:52.243Z\",\"updated_at\":\"2024-02-22T12:06:52.243Z\",\"deleted_at\":null,\"apportionsId\":178,\"status\":\"CREATED\",\"amount\":15000,\"msp_id\":\"MSP06\",\"insurance_car_number\":\"TEST-123\",\"insurance_number\":\"181234567980\",\"approved_at\":null,\"rejected_at\":null,\"responsibility_rate\":0,\"driver_name\":\"駕駛人\",\"charge_index\":0,\"vehicle_type\":\"VT03\",\"vehicle_payload_capacity\":5,\"vehicle_payload_capacity_unit\":\"PERSON\"}],\"files\":[],\"state_prices\":[{\"id\":290,\"created_at\":\"2024-02-22T12:06:52.246Z\",\"updated_at\":\"2024-02-22T12:06:52.246Z\",\"deleted_at\":null,\"amount\":120000,\"code\":\"C13\",\"type\":\"DISABILITY\",\"apportionId\":178}]}}}";
		CompulsoryUpdateResultVo compulsoryUpdateResultVo = objectMapper.readValue(responseStr, CompulsoryUpdateResultVo.class);
		
	}
}
