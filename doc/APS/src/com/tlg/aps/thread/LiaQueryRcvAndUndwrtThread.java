package com.tlg.aps.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Lia07061aqRequestVo;
import com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Lia07072aqRequestVo;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GUID;
import com.tlg.util.JsonUtil;

public class LiaQueryRcvAndUndwrtThread implements Runnable {

	protected Logger logger = Logger.getLogger(LiaQueryRcvAndUndwrtThread.class);
	private List<String> idlist;
	private String appCode;
	private String groupNo;
	private ConfigUtil configUtil;
	
	public LiaQueryRcvAndUndwrtThread(String appCode, String groupNo, List<String> idlist){
		this.idlist = idlist;
		this.groupNo = groupNo;
		this.appCode = appCode;
		this.configUtil = (ConfigUtil)AppContext.getBean("configUtil");
	}

	public void run() {
		try {
			logger.debug("LiaQueryRcvAndUndwrtThread START： groupNo - " + this.groupNo + "," +new Date() + ", this.idlist.size() = " + this.idlist.size());
			
			if(idlist != null && idlist.size() > 0){
				//先產生收件
				Lia07061aqRequestVo rcvIdListVo =  getLia07061aqRequestVo(appCode, idlist);
				String rcvJson = JsonUtil.getJSONString(rcvIdListVo);
				connectCwpWebService("lia07062aq", rcvJson);
				
				
				Lia07072aqRequestVo undwrtIdListVo =  getLia07072aqRequestVo(appCode, idlist);
				String undwrtJson = JsonUtil.getJSONString(undwrtIdListVo);
				connectCwpWebService("lia07072aq", undwrtJson);
				
			}else{
				logger.debug("this.idlist is null ，呼叫查詢收件及承保通報執行結束");
			}
			
			logger.debug("LiaQueryRcvAndUndwrtThread END： groupNo - " + this.groupNo + "," +new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 連線至CWP 傷害險通報 web service
	 * @param type rcv或是undwrt
	 * @throws Exception 
	 */
	private void connectCwpWebService(String type, String json) throws Exception {
		String url = this.configUtil.getString("cwpLiaUrl") + "CWP/webService/lia/api/" + type;
		CloseableHttpClient httpClient = null;
        try {
        	int timeout = 600; //秒
        	RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        	httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        	HttpPost httpPost = new HttpPost(url);  
        	StringEntity stringEntity = new StringEntity(json, "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
        	httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println( "httpResponse.getStatusLine() = " + httpResponse.getStatusLine());
            int statusCode = httpResponse.getStatusLine().getStatusCode(); 
            if(statusCode != 200){
            	throw new Exception("連線至CWP webservice發生問題，status code = " + statusCode);
            }
            
        } catch (IOException ioe) {
        	logger.error("Exceptions happen!", ioe);
            ioe.printStackTrace();
            throw ioe;
        } catch (Exception e) {
        	logger.error("Exceptions happen!", e);
            e.printStackTrace();
            throw e;
        }finally{
        	if(httpClient != null){
        		httpClient.close();
        		httpClient = null;
        	}
        }
    } 

	/**
	 * Lia07062和Lia07061內容一樣，因此共用物件
	 * 
	 * @param appCode
	 * @param idList
	 * @return
	 * @throws Exception 
	 */
	private static Lia07061aqRequestVo getLia07061aqRequestVo(String appCode, List<String> idList) throws Exception {
		if(idList != null && idList.size() > 0){
			Lia07061aqRequestVo vo  = new Lia07061aqRequestVo();
			com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Head head = new com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Head();
			head.setAppCode(appCode);
			head.setCheckno(appCode + "_" + new GUID().toString(5));
			head.setTotal(String.valueOf(idList.size()));
			ArrayList<com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Datum> datumList = new ArrayList<com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Datum>();
			for(String id:idList){
				com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Datum datum = new com.tlg.aps.vo.liaJsonObj.lia07061aq.request.Datum();
				datum.setKeyidno(id);
				datumList.add(datum);
			}
			vo.setHead(head);
			vo.setData(datumList);
			return vo;
		}
		return null;
    }
	
	private static Lia07072aqRequestVo getLia07072aqRequestVo(String appCode, List<String> idList) throws Exception {
		if(idList != null && idList.size() > 0){
			Lia07072aqRequestVo vo  = new Lia07072aqRequestVo();
			com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Head head = new com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Head();
			head.setAppCode(appCode);
			head.setCheckno(appCode + "_" + new GUID().toString(5));
			head.setTotal(String.valueOf(idList.size()));
			head.setDatastatus("1,2");
			ArrayList<com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Datum> datumList = new ArrayList<com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Datum>();
			for(String id:idList){
				com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Datum datum = new com.tlg.aps.vo.liaJsonObj.lia07072aq.request.Datum();
				datum.setKeyidno(id);
				datumList.add(datum);
			}
			vo.setHead(head);
			vo.setData(datumList);
			return vo;
		}
		return null;
    }

}

