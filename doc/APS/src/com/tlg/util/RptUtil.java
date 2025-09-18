package com.tlg.util;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.tlg.aps.vo.RptFir00103ResultVo;

public class RptUtil {
	
	private String rptUrl;
	
	public RptUtil() {
		this.rptUrl = "http://192.168.112.6:8880/RPT/";
	}
	
	public RptUtil(String rptUrl) {
		this.rptUrl = rptUrl;
	}
	
	public boolean genFir00103Pdf(String filePath, String oid, String fileName){
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			String httpURL = rptUrl +"webService/pdf/fir00103/" + oid;
	        HttpGet getRequest = new HttpGet(httpURL);
	        getRequest.addHeader("accept", "application/json");
	        HttpResponse response = httpClient.execute(getRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("statusCode = " + statusCode);
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        HttpEntity httpEntity = response.getEntity();
	        String jsonString = EntityUtils.toString(httpEntity);
	        RptFir00103ResultVo vo = (RptFir00103ResultVo)JsonUtil.getDTO(jsonString, RptFir00103ResultVo.class);
	        
	        System.out.println("vo.getMsg() = " + vo.getMsg());
	        byte[] byteArray = Base64.decodeBase64(vo.getRptStr());
			FileUtils.writeByteArrayToFile(new File(filePath + fileName + ".pdf"), byteArray);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 
	 *新增建立PDF方法*/
	public boolean genPdf(String filePath, String param, String fileName, String reportName){
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			String httpURL = rptUrl +"webService/pdf/"+reportName+"/" + param;
	        HttpGet getRequest = new HttpGet(httpURL);
	        getRequest.addHeader("accept", "application/json");
	        HttpResponse response = httpClient.execute(getRequest);
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("statusCode = " + statusCode);
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	        HttpEntity httpEntity = response.getEntity();
	        String jsonString = EntityUtils.toString(httpEntity);
	        RptFir00103ResultVo vo = (RptFir00103ResultVo)JsonUtil.getDTO(jsonString, RptFir00103ResultVo.class);
	        
	        System.out.println("vo.getMsg() = " + vo.getMsg());
	        if(!"Y".equals(vo.getStatus())) {
	        	return false;
	        }
	        byte[] byteArray = Base64.decodeBase64(vo.getRptStr());
			FileUtils.writeByteArrayToFile(new File(filePath + fileName + ".pdf"), byteArray);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getRptUrl() {
		return rptUrl;
	}

	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}
}
