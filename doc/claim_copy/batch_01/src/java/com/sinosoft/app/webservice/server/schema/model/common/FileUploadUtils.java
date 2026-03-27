package com.sinosoft.app.webservice.server.schema.model.common;

import ins.framework.utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

import com.sinosoft.claim.common.util.CommonUtils;

public class FileUploadUtils {
	private static final Log logger = LogFactory.getLog(FileUploadUtils.class);
	private static HttpClient httpClient = new HttpClient();
	static {
		// 榛樿璁剧疆璁剧疆杩炴帴瓒呮椂鏃堕棿30绉掞紝璇绘暟鎹秴鏃舵椂闂?20绉?
		setConnectionTimeout(30000);
		setSoTimeout(120000);
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
	}

	public static String uploadFile(final String fileTransServiceUrl,
			final String bussNo, String appendPath, String originalFileName,
			byte[] fileContent, String keyStr, long fileSize, String isZip,
			String isDecry, String isPack) {
		
		PostMethod postMethod = new PostMethod(fileTransServiceUrl);
		// 灏嗚姹傚弬鏁癤ML鐨勫?鏀惧叆postMethod涓?
		try {

			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("methodName", "uploadFile"),
					new NameValuePair("bussNo", bussNo),
					new NameValuePair("appendPath", appendPath),
					new NameValuePair("keyStr", keyStr),
					new NameValuePair("isZip", isZip),
					new NameValuePair("isDecry", isDecry),
					new NameValuePair("isPack", isPack),
					new NameValuePair("fileSize", Long.toString(fileSize)),
					new NameValuePair("originalFileName", originalFileName) });
			Part[] parts = { new FilePart("fileContent", new ByteArrayPartSource(
					"fileContent", fileContent)) };
			MultipartRequestEntity entity = new MultipartRequestEntity(parts,
					new HttpMethodParams());
			postMethod.setRequestEntity(entity);
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			return postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			// 閲婃斁杩炴帴
			postMethod.releaseConnection();
		}
	}
	public static String saveIndex(String bussNo, String typePath,
			String appendPath, String fileName, long fileSize,
			String originalFileName, String userCode, String comCode,
			String orignalImageFlag, String reason, String fileTransServiceUrl,
			String operatorCode, String remark, String property1,
			String property2, String property3, String property4,
			//modfiy by xianglijun 20130129 begin 增加备注字段
			//modify by xianglijun 20130104 begin 增加说明字段
			String property5, String fileIndexServiceUrl, String file_date,String flowId,String batchFlag,String explain,String remarks) {
		    //modify by xianglijun 20130104 end 增加说明字段
		    //modfiy by xianglijun 20130129 end 增加备注字段
		
		PostMethod postMethod = new PostMethod("http://192.168.100.64:7005/filemanager/fileIndex");
		// 将请求参数XML的值放入postMethod中
		try {

			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("methodName", "saveIndex"),
					new NameValuePair("bussNo", bussNo),
					new NameValuePair("appendPath", appendPath),
					new NameValuePair("typePath", typePath),
					new NameValuePair("originalFileName", originalFileName),
					new NameValuePair("fileName", fileName),
					new NameValuePair("fileSize", Long.toString(fileSize)),
					new NameValuePair("userCode", userCode),
					new NameValuePair("comCode", comCode),
					new NameValuePair("orignalImageFlag", orignalImageFlag),
					new NameValuePair("reason", reason),
					new NameValuePair("fileTransServiceUrl",
							fileTransServiceUrl),
					new NameValuePair("operatorCode", operatorCode),
					new NameValuePair("remark", remark),
					new NameValuePair("property1", property1),
					new NameValuePair("property2", property2),
					new NameValuePair("property3", property3),
					new NameValuePair("property4", property4),
					new NameValuePair("property5", property5),
					new NameValuePair("file_date", file_date),
					new NameValuePair("flowId", flowId),
					//add by xianglijun 20120908 begin 增加批量标识
					new NameValuePair("batchFlag",batchFlag)});
					//add by xianglijun 20120908 end 增加批量标识
					//add by xianglijun 20130104 begin 增加说明字段
					new NameValuePair("explain",explain);
					//add by xianglijun 20130104 end 增加说明字段	
					//add by xianglijun 20130129 begin 增加备注字段
					new NameValuePair("remarks",remarks);
				   //add by xianglijun 20130129 end 增加备注字段
					NameValuePair NameValuePair[]  = 
							postMethod.getParameters();
					for(int i=0;i<NameValuePair.length;i++){
						logger.info("========="+NameValuePair[i]);
					}
					
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			return postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}

	}
	/**
	 * String型轉成圖片
	 * @param img
	 * @param imgPath
	 */
	public static void stringConvertImg(String img,String imgPath){
		if(img.length()>0&&!CommonUtils.isEmpty(img)){
			BASE64Decoder decoder = new BASE64Decoder();  
			try {
				byte[] content = decoder.decodeBuffer(img);
				for(int i=0;i<content.length;++i)  
	            {  
	                if(content[i]<0)  
	                {//调整异常数据  
	                	content[i]+=256;  
	                }  
	            }  
	            //生成jpeg图片  
	            OutputStream out = new FileOutputStream(imgPath);      
	            out.write(content);  
	            out.flush();  
	            out.close();  
				
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
	/**
	 * 设置连接超时时间
	 * 
	 * @param connectionTimeout
	 *          连接超时时间(单位毫秒)
	 */
	public static void setConnectionTimeout(int connectionTimeout) {
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		managerParams.setConnectionTimeout(connectionTimeout);
	}
	/**
	 * 设置读数据超时时间
	 * 
	 * @param soTimeout
	 *          读数据超时时间(单位毫秒)
	 */
	public static void setSoTimeout(int soTimeout) {
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		managerParams.setSoTimeout(soTimeout);
	}

}
