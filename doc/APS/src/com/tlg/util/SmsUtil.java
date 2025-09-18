package com.tlg.util;

import java.io.IOException;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.vo.SmsVo;
import com.tlg.aps.vo.SmsVoList;

public class SmsUtil {
	
	private static final Logger logger = Logger.getLogger(SmsUtil.class);

	public String sendSms(String smsUrl, SmsVo sms) throws IOException {		
		if(smsUrl == null || smsUrl.length() <= 0) {
			return "X6,smsUrl不得為null或空值";
		}
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
            logger.error(e1);
            return "X2," + e1.getMessage();
        } catch (Exception e2) {
            e2.printStackTrace();
            logger.error(e2);
            return "X3," + e2.getMessage();
        }finally{
        	httpClient.close();
        }
		return "X9,";
	}	
}
