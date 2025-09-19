package com.tlg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
public class HttpsUrlUtil {

    private Logger log = LoggerFactory.getLogger(HttpsUrlUtil.class);

    public String doConnectionPost(String strUrl, String jsonParams){
    	log.info("doConnectionPost strUrl : " + strUrl + ", jsonParams : "+ jsonParams);

	    try{
	    	String urlParameters = jsonParams;
	    	byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
	    	URL url = new URL(strUrl);
	    	
	    	//取得SSLContext方法1，查JDK憑證庫是否有憑證----START
		    SSLContext sc = SSLContext.getInstance("TLSv1.2", "SunJSSE");
			sc.init(null, null, null);
			//取得SSLContext方法1，查JDK憑證庫是否有憑證----END
	    	
//			//取得SSLContext方法2，信任所有網站----START
//			SSLContext sc = SSLContext.getInstance("TLSv1.2", "SunJSSE");
//	    	sc.init(null, new X509TrustManager[] { new X509TrustManager() {
//	            @Override
//	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//	            }
//
//	            @Override
//	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//	            }
//
//	            @Override
//	            public X509Certificate[] getAcceptedIssuers() {
//	                return new X509Certificate[0];
//	            }
//	        } }, new SecureRandom());
//			//取得SSLContext方法2，信任所有網站----END

	        
	    	HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();    
//	    	//呼略所有憑證----START
//	        SSLSocketFactory ssf = sc.getSocketFactory();
//	    	HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
//	            public boolean verify(String s, SSLSession sslsession) {
//	                System.out.println(
//	                    "WARNING: Hostname is not matched for cert."
//	                );
//	                return true;
//	            }
//	        };
//	    	
//	        conn.setDefaultHostnameVerifier(ignoreHostnameVerifier);
//	        conn.setDefaultSSLSocketFactory(ssf);
//	    	//呼略所有憑證----END
	        conn.setSSLSocketFactory(sc.getSocketFactory());
	    	
	    	conn.setRequestMethod( "POST" );
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	conn.setRequestProperty("Accept", "application/json");
	    	conn.setDoOutput( true );
	    	try(OutputStream os = conn.getOutputStream()) {
	    	    os.write(postData, 0, postData.length);			
	    	}
			
			int responseCode = conn.getResponseCode();
			log.info("call Url Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				try(BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					log.info(response.toString());
					return response.toString();
				} catch(Exception e) {
					log.error("doConnectionPost exception :: ", e);
				}
			} else {
				log.info("call URL request did not work.");
			}
	    	
	    }catch(Exception e){
	    	log.error("doConnectionPost exception :: ", e);
	    }

		return "";
	}
    
    public String doConnectionGet(String strUrl){
		log.info("doConnectionGet strUrl : " + strUrl);

	    try{
	    	URL url = new URL(strUrl);
	    	HttpURLConnection conn= (HttpURLConnection) url.openConnection();    
	    	conn.setRequestMethod( "GET" );
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	conn.setRequestProperty("Accept", "application/json");

			int responseCode = conn.getResponseCode();
			log.info("call Url Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				try(BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					log.info(response.toString());
					return response.toString();
				} catch(Exception e) {
					log.error("doConnectionPost exception :: ", e);
				}
			} else {
				log.info("call URL request did not work.");
			}
	    	
	    }catch(Exception e){
	    	log.error("doConnectionGet exception :: ", e);
	    }

		return "";
	}
}
