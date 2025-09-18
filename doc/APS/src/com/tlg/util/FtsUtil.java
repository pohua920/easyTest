package com.tlg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;

public class FtsUtil {
	
	private static final Logger logger = Logger.getLogger(FtsUtil.class);
	private String httpURL;
	
	public FtsUtil(String httpURL) {
		this.httpURL = httpURL;
	}

	public FileUploadResponseVo uploadFile(String filePath, String source, String riskCode, String businessNo) {
		FileUploadResponseVo fileUploadResponseVo = null;
	    if(this.httpURL == null || this.httpURL.length() <= 0) {
	    	return null;
	    }
	    String uploadUrl = this.httpURL + "upload";
	    File file = new File(filePath);
	    if(!file.exists()) {
	    	return null;
	    }
	    StringBuilder stringBuilder = null;
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
	            HttpPost httpPost = new HttpPost(uploadUrl);
	            FileBody fileBody = new FileBody(file);
	            MultipartEntityBuilder  multipartEntityBuilder = MultipartEntityBuilder.create();
	            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	            multipartEntityBuilder.addTextBody("source", source, ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addTextBody("riskCode", riskCode, ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addTextBody("businessNo", businessNo, ContentType.TEXT_PLAIN);
	            multipartEntityBuilder.addPart("file", fileBody);
	            HttpEntity httpEntity = multipartEntityBuilder.build();
	            httpPost.setEntity(httpEntity);
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            System.out.println("Response code/message: " + httpResponse.getStatusLine());
	            httpEntity = httpResponse.getEntity();

	            InputStream inputStream = httpEntity.getContent();
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	            stringBuilder = new StringBuilder();
	            String strReadLine = bufferedReader.readLine();
	            // iterate to get the data and append in StringBuilder
	            while (strReadLine != null) {
	                stringBuilder.append(strReadLine);
	                strReadLine = bufferedReader.readLine();
	                if (strReadLine != null) {
	                    stringBuilder.append("\n");
	                }
	            }
	            
	            String responseString = stringBuilder.toString();
	            logger.info("responseString : " + responseString);
		        fileUploadResponseVo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
		        logger.info("status:" + fileUploadResponseVo.getStatus());
		        logger.info("message:" + fileUploadResponseVo.getMessage());
		        logger.info("uploadOid:" + fileUploadResponseVo.getUploadOid());
	        }catch (UnsupportedEncodingException usee) {
	            usee.printStackTrace();
	        }catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    return fileUploadResponseVo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FileListResponseVo getFtsFileList(String riskCode, String businessNo) {
		if(this.httpURL == null || this.httpURL.length() <= 0) {
	    	return null;
	    }
		String getFileListUrl = this.httpURL + "fileList";
		FileListResponseVo fileListResponseVo = null;
		StringBuilder  stringBuilder = new StringBuilder();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        	FileUploadRequestVo fileUploadRequestVo = new FileUploadRequestVo();
        	fileUploadRequestVo.setRiskCode(riskCode);
        	fileUploadRequestVo.setBusinessNo(businessNo);
              
        	HttpPost httpPost = new HttpPost(getFileListUrl);  
        	StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(fileUploadRequestVo), "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", "application/json");
        	httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);  
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
            
            Map classMap = new HashMap();
            classMap.put("fileList", FileVo.class);
            fileListResponseVo = (FileListResponseVo)JsonUtil.getDTO(stringBuilder.toString(), FileListResponseVo.class, classMap);
        }
        catch (UnsupportedEncodingException usee) {
            usee.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
        return fileListResponseVo;
	}
	
	public void downloadFile(String businessNo, String oid, String downloadPath, String fileName) throws Exception {
		if(this.httpURL == null || this.httpURL.length() <= 0) {
	    	return;
	    }
		InputStream fileInputStream = null;
		OutputStream fileOutputStream = null;
		String downloadFileUrl = this.httpURL + "download/" + businessNo + "/" + oid;
		logger.info("lnkDownloadPDF url : " + downloadFileUrl);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpGet httpGet = new HttpGet(downloadFileUrl);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);

            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
            	String entityStr = EntityUtils.toString(entity, "UTF-8");
            	System.out.println(entityStr);
            	return;
            }
            
            fileInputStream = httpResponse.getEntity().getContent();
            File fileFolder = new File(downloadPath);
			if(!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
            File file = new File(downloadPath + fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);  
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=fileInputStream.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                fileOutputStream.write(bytes);
            }
            
            fileOutputStream.flush();
//            this.fileInputStream = new DeleteAfterDownloadFileInputStream(file);
            if(file.length() <= 0) {
            	file.deleteOnExit();
            }

		}catch (IOException e) {
			e.printStackTrace();
		} catch (SystemException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
                if(fileInputStream != null){
                	fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if(fileOutputStream != null){
                	fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

		}
	}
	
}
