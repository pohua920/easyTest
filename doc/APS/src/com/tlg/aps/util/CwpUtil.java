///**
// * 
// */
//package com.tlg.aps.util;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import com.tlg.aps.vo.VerifyIdVo;
//import com.tlg.util.JsonUtil;
//
///**
// * @author bj016
// *
// */
//public class CwpUtil {
//	
//	private String httpUrl;
//	
//	public CwpUtil(){
//		//正式區的CWP
//		this.httpUrl = "http://192.168.2.112:8880/CWP/webService/verify/id/";
//	}
//	
//	public CwpUtil(String httpUrl){
//		this.httpUrl = httpUrl;
//	}
//	
//public boolean verifyID(String id){
//		
//		if(this.httpUrl == null || this.httpUrl.length() <= 0){
//			return false;
//		}
//		
//		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//			/**
//			 * 20200620
//			 * BJ016
//			 * 中信傳過來的資料會有中間空白的問題，所以這邊要對url作url encode，將特殊字作轉換，免得程式出exception
//			 * */
//			// 進行 URL 百分比編碼
//			String encodedURLID = URLEncoder.encode(id, "UTF-8");
//			String httpURL = this.httpUrl + encodedURLID;
//	        HttpGet getRequest = new HttpGet(httpURL);
//	        getRequest.addHeader("accept", "application/json");
//	        HttpResponse response = httpClient.execute(getRequest);
//	        int statusCode = response.getStatusLine().getStatusCode();
//	        System.out.println("statusCode = " + statusCode);
//	        if (statusCode != 200) 
//	        {
//	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
//	        }
//	        HttpEntity httpEntity = response.getEntity();
//	        String jsonString = EntityUtils.toString(httpEntity);
//	        VerifyIdVo vo = (VerifyIdVo)JsonUtil.getDTO(jsonString, VerifyIdVo.class);
//	        if("S0000".equals(vo.getCode())) {
//	        	return true;
//	        }
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		return false;
//	}
//	
//	public boolean verifyID(String id, String identifyType){
//		
//		if(this.httpUrl == null || this.httpUrl.length() <= 0){
//			return false;
//		}
//		
//		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//			/**
//			 * 20200620
//			 * BJ016
//			 * 中信傳過來的資料會有中間空白的問題，所以這邊要對url作url encode，將特殊字作轉換，免得程式出exception
//			 * */
//			// 進行 URL 百分比編碼
//			String encodedURLID = URLEncoder.encode(id, "UTF-8");
//			String httpURL = this.httpUrl + encodedURLID;
//	        HttpGet getRequest = new HttpGet(httpURL);
//	        getRequest.addHeader("accept", "application/json");
//	        HttpResponse response = httpClient.execute(getRequest);
//	        int statusCode = response.getStatusLine().getStatusCode();
//	        System.out.println("statusCode = " + statusCode);
//	        if (statusCode != 200) 
//	        {
//	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
//	        }
//	        HttpEntity httpEntity = response.getEntity();
//	        String jsonString = EntityUtils.toString(httpEntity);
//	        VerifyIdVo vo = (VerifyIdVo)JsonUtil.getDTO(jsonString, VerifyIdVo.class);
//	        if("S0000".equals(vo.getCode())) {
//	        	if("05".equals(identifyType)) {
//	        		/**
//	        		 * bj016
//	        		 * 外國人id這個欄位可能放得是居留證號或稅籍編號
//	        		 * 所以當IdentifyType為05時表示為居留證號
//	        		 *             IdentifyType為T1時表示為外國人稅籍編號
//	        		 *             IdentifyType為T2時表示為中國人稅籍編號
//	        		 * */
//	        		if("05".equals(vo.getIdentifyType()) || "T1".equals(vo.getIdentifyType()) || "T2".equals(vo.getIdentifyType())) {
//	        			return true;
//	        		}
//	        	}else if(identifyType != null && identifyType.equals(vo.getIdentifyType())) {
//	        		return true;
//	        	}
//	        	return false;
//	        }
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		return false;
//	}
//	
//	public VerifyIdVo getIDInfo(String id){
//		
//		if(this.httpUrl == null || this.httpUrl.length() <= 0){
//			return null;
//		}
//		VerifyIdVo vo = null;
//		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//			/**
//			 * 20200620
//			 * BJ016
//			 * 中信傳過來的資料會有中間空白的問題，所以這邊要對url作url encode，將特殊字作轉換，免得程式出exception
//			 * */
//			// 進行 URL 百分比編碼
//			String encodedURLID = URLEncoder.encode(id, "UTF-8");
//			String httpURL = this.httpUrl + encodedURLID;
//	        HttpGet getRequest = new HttpGet(httpURL);
//	        getRequest.addHeader("accept", "application/json");
//	        HttpResponse response = httpClient.execute(getRequest);
//	        int statusCode = response.getStatusLine().getStatusCode();
//	        System.out.println("statusCode = " + statusCode);
//	        if (statusCode != 200) 
//	        {
//	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
//	        }
//	        HttpEntity httpEntity = response.getEntity();
//	        String jsonString = EntityUtils.toString(httpEntity);
//	        vo = (VerifyIdVo)JsonUtil.getDTO(jsonString, VerifyIdVo.class);
//	        if(!"S0000".equals(vo.getCode())) {
//	        	return null;
//	        }
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		return vo;
//	}
//
//	public String getHttpUrl() {
//		return httpUrl;
//	}
//
//	public void setHttpUrl(String httpUrl) {
//		this.httpUrl = httpUrl;
//	}
//
//}
