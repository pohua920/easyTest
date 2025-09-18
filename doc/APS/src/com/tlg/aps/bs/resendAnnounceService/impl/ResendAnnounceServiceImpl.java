package com.tlg.aps.bs.resendAnnounceService.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.resendAnnounceService.ResendAnnounceService;
import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Datum;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Head;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Lia07010auRequestVo;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Row;
import com.tlg.aps.vo.liaJsonObj.lia07010au.response.Lia07010auResponseVo;
import com.tlg.exception.SystemException;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.DateUtils;
import com.tlg.util.GUID;
import com.tlg.util.JsonUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpRcvAnnounce;
import com.tlg.xchg.entity.CwpUndwrtAnnounce;
import com.tlg.xchg.entity.LiaRcvAnnounce;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;
import com.tlg.xchg.service.CwpRcvAnnounceService;
import com.tlg.xchg.service.CwpUndwrtAnnounceService;
import com.tlg.xchg.service.LiaRcvAnnounceService;
import com.tlg.xchg.service.LiaUndwrtAnnounceService;

@Transactional(value="xchgTransactionManager", propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
public class ResendAnnounceServiceImpl implements ResendAnnounceService {
	
	private static final Logger logger = Logger.getLogger(ResendAnnounceServiceImpl.class);
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	private LiaRcvAnnounceService liaRcvAnnounceService;
	private CwpRcvAnnounceService cwpRcvAnnounceService;
	private LiaUndwrtAnnounceService liaUndwrtAnnounceService;
	private CwpUndwrtAnnounceService cwpUndwrtAnnounceService;
	private ConfigUtil configUtil;

	@Override
	public Result resendAnnounce(String announceCase,String userId,CwpAnnounceVo cwpAnnounceVo) throws SystemException,Exception {
		Result result = new Result();
		try {
			if(announceCase.equals("RCV")) {
				LiaRcvAnnounce liaRcvAnnounce = new LiaRcvAnnounce();
				BeanUtils.copyProperties(cwpAnnounceVo,liaRcvAnnounce);
				liaRcvAnnounce.setOidCwpRcvAnnounce(cwpAnnounceVo.getResendSourceOid());
				result = liaRcvAnnounceService.insertLiaRcvAnnounce(liaRcvAnnounce);
				CwpRcvAnnounce cwpRcvAnnounce = new CwpRcvAnnounce();
				cwpRcvAnnounce.setOid(cwpAnnounceVo.getResendSourceOid());
				cwpRcvAnnounce.setResendModifytime(new Date());
				cwpRcvAnnounce.setResendUserId(userId);
				cwpRcvAnnounce.setResendReason(cwpAnnounceVo.getResendReason());
				result = cwpRcvAnnounceService.updateCwpRcvAnnounce(cwpRcvAnnounce);
			}else if(announceCase.equals("UNDWRT")){
				LiaUndwrtAnnounce liaUndwrtAnnounce = new LiaUndwrtAnnounce();
				BeanUtils.copyProperties(cwpAnnounceVo,liaUndwrtAnnounce);
				liaUndwrtAnnounce.setOidCwpUndwrtAnnounce(cwpAnnounceVo.getResendSourceOid());
				result = liaUndwrtAnnounceService.insertLiaUndwrtAnnounce(liaUndwrtAnnounce);
				CwpUndwrtAnnounce cwpUndwrtAnnounce = new CwpUndwrtAnnounce();
				cwpUndwrtAnnounce.setOid(cwpAnnounceVo.getResendSourceOid());
				cwpUndwrtAnnounce.setResendModifytime(new Date());
				cwpUndwrtAnnounce.setResendUserId(userId);
				cwpUndwrtAnnounce.setResendReason(cwpAnnounceVo.getResendReason());
				result = cwpUndwrtAnnounceService.updateCwpUndwrtAnnounce(cwpUndwrtAnnounce);
			}
		}catch(SystemException se){
			throw se;
		}catch(Exception e){
			result.setMessage(Message.getMessage(Constants.UPDATE_DATA_FAIL));
			throw e;
		}
		return result;
	}
	
	@Override
	public Result resendAnnounce(String announceCase,String userId, List<CwpAnnounceVo> cwpAnnounceVoList) throws SystemException,Exception {
		Result result = new Result();
		try {
			if(cwpAnnounceVoList == null || cwpAnnounceVoList.size() < 0){
				throw new SystemException("無法取得重送資料");
			}
			
			String checkNo = announceCase + "_U_" + userId.toUpperCase() + DateUtils.format(new Date(),"yyyyMMddHHmmss");
			checkNo += "_" + new GUID().toString(29 - checkNo.length());
			Lia07010auRequestVo vo = new Lia07010auRequestVo();
			List<Datum> dataList = new ArrayList<Datum>();
			Head head = new Head();
			head.setCheckno(checkNo);	
			
			String idno = "";
			ArrayList<Row> rowList = new ArrayList<Row>();
			
			for(CwpAnnounceVo cwpAnnounceVo:cwpAnnounceVoList){
				if("".equals(idno)){
					idno = cwpAnnounceVo.getIdno();
				}else{
					if(!idno.equals(cwpAnnounceVo.getIdno())){
						throw new SystemException("被保險人ID不一致，請再確認資料～");
					}
				}
				
				Row row = new Row();
				BeanUtils.copyProperties(cwpAnnounceVo, row);
				rowList.add(row);
			}
			Datum datum  = new Datum();
			datum.setKeyidno(idno);
			datum.setRow(rowList);
			dataList.add(datum);
			head.setTotal(String.valueOf(cwpAnnounceVoList.size()));
			head.setAppCode("USER");
			vo.setHead(head);
			vo.setData(dataList);
			String json = JsonUtil.getJSONString(vo);
			vo = null; //轉成字串已無用
			
			//呼叫web service
			Date sendTime = new Date();
			Lia07010auResponseVo responseVo = null;
			try{
				responseVo = connectCwpWebService(announceCase.toLowerCase(), json);
			}catch (Exception e) {
				throw new SystemException("連線至公會通報時發生問題，" + e.getMessage());
			}
			ArrayList<LiaRcvAnnounce> resultList = new ArrayList<LiaRcvAnnounce>();
			
			List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum> datumList = responseVo.getData();
			for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum datum1 : datumList){
				String id = datum1.getKeyidno();
				List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row> rowList1 =  datum1.getRow();
				//各ID下的資料
				for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row row : rowList1){
					String dataserno = row.getDataserno();
					LiaRcvAnnounce liaRcvAnnounce = new LiaRcvAnnounce();
					liaRcvAnnounce.setDataserno(dataserno);
					liaRcvAnnounce.setSendtime(sendTime);
					liaRcvAnnounce.setRtncode(row.getRtncode());
					liaRcvAnnounce.setRtnmsg(row.getRtnmsg());
					resultList.add(liaRcvAnnounce);
				}
			}
			result.setResObject(resultList);
			
		}catch(SystemException se){
			throw se;
		}catch(Exception e){
			result.setMessage(Message.getMessage(Constants.UPDATE_DATA_FAIL));
			throw e;
		}
		return result;
	}
	
	/**
	 * 連線至CWP 傷害險通報 web service
	 * @param type rcv或是undwrt
	 * @throws Exception 
	 */
	private Lia07010auResponseVo connectCwpWebService(String type, String json) throws Exception {
		Lia07010auResponseVo responseVo = new Lia07010auResponseVo();
		String rcvurl = this.configUtil.getString("cwpLiaUrl") + "CWP/webService/lia/api/" + type;
		CloseableHttpClient httpClient = null;
        try {
        	int timeout = 600; //秒
        	RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).build();
        	httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        	HttpPost httpPost = new HttpPost(rcvurl);  
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
            
            HttpEntity entity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);
            
            //不管是收件通報或是承保通報都是用Lia07010auResponseVo
            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
            	String jsonStr = EntityUtils.toString(entity, "UTF-8");
            	System.out.println(jsonStr);
            	
            	ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        		responseVo = objectMapper.readValue(jsonStr, Lia07010auResponseVo.class);
        		System.out.println(responseVo.getHead().getCheckno());
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
        return responseVo;
    } 

	public LiaUndwrtAnnounceService getLiaUndwrtAnnounceService() {
		return liaUndwrtAnnounceService;
	}

	public void setLiaUndwrtAnnounceService(LiaUndwrtAnnounceService liaUndwrtAnnounceService) {
		this.liaUndwrtAnnounceService = liaUndwrtAnnounceService;
	}

	public CwpUndwrtAnnounceService getCwpUndwrtAnnounceService() {
		return cwpUndwrtAnnounceService;
	}

	public void setCwpUndwrtAnnounceService(CwpUndwrtAnnounceService cwpUndwrtAnnounceService) {
		this.cwpUndwrtAnnounceService = cwpUndwrtAnnounceService;
	}

	public LiaRcvAnnounceService getLiaRcvAnnounceService() {
		return liaRcvAnnounceService;
	}

	public void setLiaRcvAnnounceService(LiaRcvAnnounceService liaRcvAnnounceService) {
		this.liaRcvAnnounceService = liaRcvAnnounceService;
	}

	public CwpRcvAnnounceService getCwpRcvAnnounceService() {
		return cwpRcvAnnounceService;
	}

	public void setCwpRcvAnnounceService(CwpRcvAnnounceService cwpRcvAnnounceService) {
		this.cwpRcvAnnounceService = cwpRcvAnnounceService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

}
