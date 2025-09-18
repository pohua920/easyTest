package com.tlg.aps.bs.liaAnnounceService.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.ws.rs.core.MediaType;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlg.aps.bs.liaAnnounceService.AnnounceService;
import com.tlg.aps.thread.LiaQueryRcvAndUndwrtThread;
import com.tlg.aps.vo.liaJsonObj.ipb902i.request.Ipb902iRequestVo;
import com.tlg.aps.vo.liaJsonObj.ipb902i.response.Data;
import com.tlg.aps.vo.liaJsonObj.ipb902i.response.Ipb902iResponseVo;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Datum;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Head;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Lia07010auRequestVo;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Row;
import com.tlg.aps.vo.liaJsonObj.lia07010au.response.Lia07010auResponseVo;
import com.tlg.db2.entity.Com051wa;
import com.tlg.db2.entity.Com051wb;
import com.tlg.db2.entity.Com051we;
import com.tlg.db2.service.Com051waService;
import com.tlg.db2.service.Com051wbService;
import com.tlg.db2.service.Com051weService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.service.PrpcinsuredService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.util.CommonFunc;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.GUID;
import com.tlg.util.JsonUtil;
import com.tlg.util.Mailer;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;
import com.tlg.xchg.entity.AssocAnnAssuw;
import com.tlg.xchg.entity.AssocRcvAncmt;
import com.tlg.xchg.entity.CwpLiaLia07061aqResult;
import com.tlg.xchg.entity.LiaRcvAnnounce;
import com.tlg.xchg.entity.LiaRcvAnnounceResult;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;
import com.tlg.xchg.entity.LiaUndwrtAnnounceResult;
import com.tlg.xchg.entity.UndwrtIpb902i;
import com.tlg.xchg.service.AssocAnnAssuwService;
import com.tlg.xchg.service.AssocRcvAncmtService;
import com.tlg.xchg.service.CwpLiaLia07061aqResultService;
import com.tlg.xchg.service.CwpRcvAnnounceService;
import com.tlg.xchg.service.CwpUndwrtAnnounceService;
import com.tlg.xchg.service.LiaRcvAnnounceResultService;
import com.tlg.xchg.service.LiaRcvAnnounceService;
import com.tlg.xchg.service.LiaUndwrtAnnounceResultService;
import com.tlg.xchg.service.LiaUndwrtAnnounceService;
import com.tlg.xchg.service.UndwrtIpb902iService;

@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class AnnounceServiceImpl implements AnnounceService {
	
	private static final Logger logger = Logger.getLogger(AnnounceServiceImpl.class);
	private ConfigUtil configUtil;
	private LiaRcvAnnounceService liaRcvAnnounceService;
	private LiaRcvAnnounceResultService liaRcvAnnounceResultService;
	private LiaUndwrtAnnounceService liaUndwrtAnnounceService;
	private LiaUndwrtAnnounceResultService liaUndwrtAnnounceResultService;
	private Com051waService com051waService;
	private Com051wbService com051wbService;
	private Com051weService com051weService;
	private AssocRcvAncmtService assocRcvAncmtService;
	private AssocAnnAssuwService assocAnnAssuwService;
	private PrpcinsuredService prpcinsuredService;
	private PrpcmainService prpcmainService;
	private CwpRcvAnnounceService cwpRcvAnnounceService;
	private CwpUndwrtAnnounceService cwpUndwrtAnnounceService;
	private UndwrtIpb902iService undwrtIpb902iService;
	private CwpLiaLia07061aqResultService cwpLiaLia07061aqResultService;
	
	/**
	 * 收件通報排程 LIA → CWP
	 */
	@Override
	public void rcvAnnounceService() throws SystemException, Exception {

		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("toCWP_mail_to_address");
		String cc = "";
		StringBuffer mailBody = new StringBuffer();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		String startTime = fmt.format(new Date());
		try{
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)
			Result result = liaRcvAnnounceService.findUnsendLiaRcvAnnounceData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險收件通報轉檔系統作業-傳送至公會結果( LIA → CWP 無資料)" ;
//				Mailer mailer = new Mailer();
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
				return;
			}
			int totalSum = 0;
			int totalFail = 0;
			//有待處理資料時
			ArrayList<LiaRcvAnnounce> unsendIdList = (ArrayList<LiaRcvAnnounce>)result.getResObject();
			
			for(LiaRcvAnnounce tmpLiaRcvAnnounce : unsendIdList){
				int currentCount = 0;//這一次筆數
				Map<String, LiaRcvAnnounce> dataMap = null;
				try{
					String idno = tmpLiaRcvAnnounce.getIdno();
					Map<String, String> params = new HashMap<String, String>();
					params.put("idno", idno);
					params.put("sendtimeIsNull", "Y");
					params.put("orderByCon", "Y");
					result = liaRcvAnnounceService.findLiaRcvAnnounceByParams(params);
					if(result.getResObject() == null){
						continue;
					}
					
					dataMap = new HashMap<String, LiaRcvAnnounce>();
					//設定checkno → SHEDULE_ + 西元年月日時分秒毫秒 20210114133955888
					String checkNo = "RCV_SCHE_" + DateUtils.format(new Date(),"yyyyMMddHHmmssSSS");
					checkNo += "_" + new GUID().toString(29 - checkNo.length());
					Lia07010auRequestVo vo = new Lia07010auRequestVo();
					List<Datum> dataList = new ArrayList<Datum>();
					Head head = new Head();
					head.setCheckno(checkNo);	
					
					String sourceRemark = "";
					ArrayList<Row> rowList = new ArrayList<Row>();//目前此Id下的資料
					ArrayList<LiaRcvAnnounce> findByIdList = (ArrayList<LiaRcvAnnounce>)result.getResObject();
					for(LiaRcvAnnounce liaRcvAnnounce : findByIdList){
						//如果已有傳送紀錄則下一筆
						if(liaRcvAnnounce.getSendtime() != null){
							continue;
						}
						totalSum++; //迴圈所有的筆數
						currentCount++; //目前筆數+1
						if(StringUtil.isSpace(sourceRemark)){
							sourceRemark = liaRcvAnnounce.getSourceRemark();
						}
						liaRcvAnnounce.setCheckno(checkNo);
						liaRcvAnnounce.setKeyidno(idno);
						liaRcvAnnounce.setDataserno(String.valueOf(currentCount));
						Row row = new Row();
						BeanUtils.copyProperties(row, liaRcvAnnounce);
						agjustRowValue("rcv", row);
						rowList.add(row);
//						//更新回LiaRcvAnnounce
//						result = liaRcvAnnounceService.updateLiaRcvAnnounce(liaRcvAnnounce);
						dataMap.put(checkNo + "-" + currentCount, liaRcvAnnounce);
					}
					Datum datum  = new Datum();
					datum.setKeyidno(idno);
					datum.setRow(rowList);
					dataList.add(datum);
					head.setTotal(String.valueOf(findByIdList.size()));
					head.setAppCode(sourceRemark);
					vo.setHead(head);
					vo.setData(dataList);
					String json = JsonUtil.getJSONString(vo);
					writeStringToFile(json, checkNo);
					vo = null; //轉成字串已無用
//					System.out.println("json = " + json);
					
					//呼叫web service
					Date sendTime = new Date();
					Lia07010auResponseVo responseVo = null;
					try{
						responseVo = connectCwpWebService("rcv", json);
					}catch (Exception e) {
						//扣掉這一次的筆數
						totalSum = totalSum - currentCount;
						
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logger.error(e);
						
						String subjectStr = env + " - 傷害險收件通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常  checkNo：" + checkNo + ")" ;
						StringBuffer errorStrBuf = new StringBuffer();
						errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
						
						Mailer mailer = new Mailer();
						mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
						continue;
					}
					
					//新增結果檔
					com.tlg.aps.vo.liaJsonObj.lia07010au.response.Head responseHead = responseVo.getHead();
					LiaRcvAnnounceResult liaRcvAnnounceResult = new LiaRcvAnnounceResult();
					BeanUtils.copyProperties(liaRcvAnnounceResult, responseHead);
					result = liaRcvAnnounceResultService.insertLiaRcvAnnounceResult(liaRcvAnnounceResult);
					totalFail = totalFail + Integer.parseInt(responseHead.getFail());
					
					//更新LiaRcvAnnounce!
					List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum> datumList = responseVo.getData();
					for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum datum1 : datumList){
						String id = datum1.getKeyidno();
						List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row> rowList1 =  datum1.getRow();
						//各ID下的資料
						for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row row : rowList1){
							String dataserno = row.getDataserno();
							
							LiaRcvAnnounce liaRcvAnnounce = dataMap.get(responseHead.getCheckno() + "-" + dataserno);
							liaRcvAnnounce.setSendtime(sendTime);
							liaRcvAnnounce.setRtncode(row.getRtncode());
							liaRcvAnnounce.setRtnmsg(row.getRtnmsg());
							result = liaRcvAnnounceService.updateLiaRcvAnnounce(liaRcvAnnounce);
						}
					}
					//有錯才紀錄
					if(!StringUtil.isSpace(responseHead.getFail()) && Integer.parseInt(responseHead.getFail()) > 0){
						mailBody.append(appendNotifyMail(responseVo).toString());
					}
					responseVo = null;
					responseHead = null;
					
				}catch (Exception e) {
					logger.error(e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					
					String subjectStr = env + " - 傷害險收件通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常)" ;
					StringBuffer errorStrBuf = new StringBuffer();
					errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
					
					Mailer mailer = new Mailer();
					mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
					continue;
				}finally{
					dataMap = null;
				}
			}
			String endTime = fmt.format(new Date());
			//發送mail
			sendNotifyMail("收件",mailBody.toString(), totalSum, totalFail, startTime, endTime);
		}catch (Exception e) {
			mailBody.setLength(0);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險收件通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常)" ;
			mailBody.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			throw e;
		}
	}
	
	/**
	 *  承保通報排程 LIA → CWP
	 */
	@Override
	public void undwrtAnnounceService() throws SystemException, Exception {

		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("toCWP_mail_to_address");
		String cc = "";
		StringBuffer mailBody = new StringBuffer();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		String startTime = fmt.format(new Date());
		
		try{
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)
			Result result = liaUndwrtAnnounceService.findUnsendLiaUndwrtAnnounceData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險承保通報轉檔系統作業-傳送至公會結果( LIA → CWP 無資料)" ;
//				Mailer mailer = new Mailer();
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
				return;
			}
			
			int totalSum = 0;
			int totalFail = 0;
			//有待處理資料時
			ArrayList<LiaUndwrtAnnounce> unsendIdList = (ArrayList<LiaUndwrtAnnounce>)result.getResObject();
			
			for(LiaUndwrtAnnounce tmpLiaUndwrtAnnounce : unsendIdList){
				int currentCount = 0;//這一次筆數
				Map<String, LiaUndwrtAnnounce> dataMap = null;
				try{
					String idno = tmpLiaUndwrtAnnounce.getIdno();
					Map<String, String> params = new HashMap<String, String>();
					params.put("idno", idno);
					params.put("sendtimeIsNull", "Y");
//					params.put("checknoIsNull", "Y");
					result = liaUndwrtAnnounceService.findLiaUndwrtAnnounceByParams(params);
					if(result.getResObject() == null){
						//跳下一個ID
						continue;
					}
					
					dataMap = new HashMap<String, LiaUndwrtAnnounce>();
					//設定checkno → SHEDULE_ + 西元年月日時分秒毫秒 20210114133955888
					String checkNo = "UND_SCHE_" + DateUtils.format(new Date(),"yyyyMMddHHmmssSSS");
					checkNo += "_" + new GUID().toString(29 - checkNo.length());
					Lia07010auRequestVo vo = new Lia07010auRequestVo();
					List<Datum> dataList = new ArrayList<Datum>();
					Head head = new Head();
					head.setCheckno(checkNo);
					
					String sourceRemark = "";
					ArrayList<Row> rowList = new ArrayList<Row>();//目前此Id下的資料
					
					ArrayList<LiaUndwrtAnnounce> findByIdList = (ArrayList<LiaUndwrtAnnounce>)result.getResObject();
					for(LiaUndwrtAnnounce liaUndwrtAnnounce : findByIdList){
						//如果已有傳送紀錄則下一筆
						if(liaUndwrtAnnounce.getSendtime() != null){
							continue;
						}
						
						totalSum++; //迴圈所有的筆數
						currentCount++; //目前筆數+1
						if(StringUtil.isSpace(sourceRemark)){
							sourceRemark = liaUndwrtAnnounce.getSourceRemark();
						}
						liaUndwrtAnnounce.setCheckno(checkNo);
						liaUndwrtAnnounce.setKeyidno(idno);
						liaUndwrtAnnounce.setDataserno(String.valueOf(currentCount));
						Row row = new Row();
						BeanUtils.copyProperties(row, liaUndwrtAnnounce);
						agjustRowValue("undwrt", row);
						rowList.add(row);
						//更新回LiaRcvAnnounce
//						result = liaUndwrtAnnounceService.updateLiaUndwrtAnnounce(liaUndwrtAnnounce);
						dataMap.put(checkNo + "-" + currentCount, liaUndwrtAnnounce);
					}
					Datum datum  = new Datum();
					datum.setKeyidno(idno);
					datum.setRow(rowList);
					dataList.add(datum);
					head.setTotal(String.valueOf(findByIdList.size()));
					head.setAppCode(sourceRemark);
					vo.setHead(head);
					vo.setData(dataList);
					String json = JsonUtil.getJSONString(vo);
					writeStringToFile(json, checkNo);
					vo = null; //轉成字串已無用
//					System.out.println("json = " + json);
					
					//呼叫web service
					Date sendTime = new Date();
					Lia07010auResponseVo responseVo = null;
					try{
						responseVo = connectCwpWebService("undwrt", json);
					}catch (Exception e) {
						//扣掉這一次的筆數
						totalSum = totalSum - currentCount;
						
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logger.error(e);
						
						String subjectStr = env + " - 傷害險承保通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常  checkNo：" + checkNo + ")" ;
						StringBuffer errorStrBuf = new StringBuffer();
						errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
						
						Mailer mailer = new Mailer();
						mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
						continue;
					}
					
					//新增結果檔
					com.tlg.aps.vo.liaJsonObj.lia07010au.response.Head responseHead = responseVo.getHead();
					LiaUndwrtAnnounceResult liaUndwrtAnnounceResult = new LiaUndwrtAnnounceResult();
					BeanUtils.copyProperties(liaUndwrtAnnounceResult, responseHead);
					result = liaUndwrtAnnounceResultService.insertLiaUndwrtAnnounceResult(liaUndwrtAnnounceResult);
					totalFail = totalFail + Integer.parseInt(responseHead.getFail());
					
					//更新LiaUndwrtAnnounce
					List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum> datumList = responseVo.getData();
					for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum datum1 : datumList){
						String id = datum1.getKeyidno();
						List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row> rowList1 =  datum1.getRow();
						//各ID下的資料
						for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row row : rowList1){
							String dataserno = row.getDataserno();
							LiaUndwrtAnnounce liaUndwrtAnnounce = dataMap.get(responseHead.getCheckno() + "-" + dataserno);
							liaUndwrtAnnounce.setSendtime(sendTime);
							liaUndwrtAnnounce.setRtncode(row.getRtncode());
							liaUndwrtAnnounce.setRtnmsg(row.getRtnmsg());
							result = liaUndwrtAnnounceService.updateLiaUndwrtAnnounce(liaUndwrtAnnounce);
						}
					}
					//有錯才紀錄
					if(!StringUtil.isSpace(responseHead.getFail()) && Integer.parseInt(responseHead.getFail()) > 0){
						mailBody.append(appendNotifyMail(responseVo).toString());
					}
					responseVo = null;
					responseHead = null;
					
				}catch (Exception e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logger.error(e);
					
					String subjectStr = env + " - 傷害險承保通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常)" ;
					StringBuffer errorStrBuf = new StringBuffer();
					errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
					
					Mailer mailer = new Mailer();
					mailer.sendmail(smtpServer, contentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
					continue;
				}finally{
					dataMap = null;
				}
			}
			String endTime = fmt.format(new Date());
			//發送mail
			sendNotifyMail("承保",mailBody.toString(), totalSum, totalFail, startTime, endTime);
		}catch (Exception e) {
			mailBody.setLength(0);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險承保通報轉檔系統作業-傳送至公會結果( LIA → CWP 發生異常)" ;
			mailBody.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			throw e;
		}
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
//        		System.out.println(responseVo.getHead().getCheckno());
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
	
	private StringBuffer appendNotifyMail(Lia07010auResponseVo responseVo) {
		StringBuffer sb = new StringBuffer();
		com.tlg.aps.vo.liaJsonObj.lia07010au.response.Head head = responseVo.getHead();
		
		List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum> datumList = responseVo.getData();
		for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum datum : datumList){
			String id = datum.getKeyidno();
			sb.append("<li>--------------------------------------------------------------------</li>");
			sb.append("<li>資料檢查碼：" + head.getCheckno() + "</li>");
			sb.append("<li>傳送時間" + head.getStarttime() + "～" + head.getEndtime() + "</li>");
			sb.append("<li>身分證字號：" + id + "</li>");
			
			List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row> rowList =  datum.getRow();
			for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row row:rowList){
				if(!"00".equals(row.getRtncode())){
					sb.append("<li>資料序號： " + row.getDataserno() + "</li>");
					sb.append("<li>回傳代碼： " + row.getRtncode() + "</li>");
					sb.append("<li>回傳訊息： " + row.getRtnmsg() + "</li>");
				}
			}
		}
		sb.append("</ul>");
		return sb;
	}
	
	private void sendNotifyMail(String typeDesc, String mailBody, int totalSum, int totalFail, String startTime, String endTime) {

//		com.tlg.aps.vo.liaJsonObj.lia07010au.response.Head head = responseVo.getHead();
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = env + " - 傷害險" + typeDesc + "通報轉檔系統作業-傳送至公會結果" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("toCWP_mail_to_address");
		String cc = "";
		String body = "<h2>傷害險" + typeDesc + "通報轉檔系統 (" + env + ")</h2><br/>";
//		mailBody += "<ul>";
//		mailBody += "<li>資料檢查碼：" + head.getCheckno() + "</li>";
		body += "<li>傳送時間" + startTime + "～" + endTime + "</li>";
		body += "<li>總筆數" + totalSum + "成功：" + (totalSum - totalFail) + "筆。失敗：" + totalFail + "筆。</li>";
		body += "<li>明細資料如下：</li>";
		body += mailBody;;
//		
//		
//		List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum> datumList = responseVo.getData();
//		for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Datum datum : datumList){
//			String id = datum.getKeyidno();
//			mailBody += "<li>--------------------------------------------------------------------</li>";
//			mailBody += "<li>身分證字號：" + id + "</li>";
//			
//			List<com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row> rowList =  datum.getRow();
//			for(com.tlg.aps.vo.liaJsonObj.lia07010au.response.Row row:rowList){
//		
//				mailBody += "<li>資料序號： " + row.getDataserno() + "</li>";
//				mailBody += "<li>回傳代碼： " + row.getRtncode() + "</li>";
//				mailBody += "<li>回傳訊息： " + row.getRtnmsg() + "</li>";
//			}
//		}
		body += "</ul>";

		try {
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", body, auth, userName, password);
			
		} catch (Exception e1) {
			logger.error("Exception occurred while sending error notify mail:\n" + mailBody, e1);
		}
	}
	
	
	/**
	 * AS400 COM051WB → LiaRcvAnnounce
	 */
	@Override
	public void rcvComm051WBToAnnounceService() throws SystemException,
			Exception {
		
		Mailer mailer = new Mailer();
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("com051ToLia_mail_to_address");
		String cc = "";
		String mailBody = "";
		
		try{
			//清除註記 開始
			Date date = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("mm");
			SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd");
			String hh = dateFormat1.format(date);
			String mm = dateFormat2.format(date);
			String yyyyMMdd = dateFormat3.format(date);
			logger.info("hhmm = " + hh + ":" + mm);
			//清除註記 
			String h1 = "";
			String h2 = "";
			String m1 = "";
			String m2 = "";
			if("15".equals(mm)){
				h1 = String.valueOf(Integer.parseInt(hh) - 1);
				h2 = hh;
				m1 = "45";
				m2 = "15";
			}
			if("45".equals(mm)){
				h1 = hh;
				h2 = hh;
				m1 = "15";
				m2 = "45";
			}
			
			if(!StringUtil.isSpace(h1) && !StringUtil.isSpace(h2)){
				Map params1 = new HashMap();
				params1.put("yyyyMMdd", yyyyMMdd);
				params1.put("hhmm1", h1 + "" + m1);
				params1.put("hhmm2", h2 + "" + m2);
				com051wbService.updateCom051wbForWb60(params1);
			}

			//清除註記 結束
			
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)
			Result result = com051wbService.findUnsendCom051wbData();
			if(result.getResObject() == null){
//				subject =  env + " - 傷害險收件通報轉檔系統作業-轉檔結果(COM051WB → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<Com051wb> unsendIdList = (ArrayList<Com051wb>)result.getResObject();
			for(Com051wb tmpCom051wb : unsendIdList){
				//依據ID逐筆找出要轉換的資料
				String idno = tmpCom051wb.getWb04();
				Map<String, String> params = new HashMap<String, String>();
				params.put("wb04", idno);
				/*
				 * 因為會有WB60被押Y的情況發生，因此在200210714開始，改成400在20210713之後產生的資料，判斷WB60非X的情況
				 */
				params.put("wb60NotEqualX", "X");
				params.put("wb61GreatThanSomeday", "20210713");
				result = com051wbService.findCom051wbByParams(params);
				if(result.getResObject() != null){
					ArrayList<Com051wb> findByIdList = (ArrayList<Com051wb>)result.getResObject();
					for(Com051wb com051wb : findByIdList){
						count++;
						LiaRcvAnnounce liaRcvAnnounce = new LiaRcvAnnounce();
						liaRcvAnnounce.setCmptype(com051wb.getWb01()); //產壽險別
						liaRcvAnnounce.setCmpno(com051wb.getWb02()); //公司代號
						liaRcvAnnounce.setName(com051wb.getWb03()); //被保險人姓名
						liaRcvAnnounce.setIdno(com051wb.getWb04()); //被保險人ID
						liaRcvAnnounce.setBirdate(com051wb.getWb05()); //被保險人生日
						liaRcvAnnounce.setSex(com051wb.getWb06()); //被保險人性別
						liaRcvAnnounce.setInsnom(com051wb.getWb07()); //主約保單號碼
						liaRcvAnnounce.setInsno(com051wb.getWb08()); //保單號碼
						liaRcvAnnounce.setOrigin(com051wb.getWb09()); //來源別
						liaRcvAnnounce.setChannel(com051wb.getWb10()); //銷售通路別
						liaRcvAnnounce.setPrdcode(com051wb.getWb11()); //商品代碼
						liaRcvAnnounce.setInsclass(com051wb.getWb12()); //保單分類
						liaRcvAnnounce.setInskind(com051wb.getWb13()); //險種分類
						liaRcvAnnounce.setInsitem(com051wb.getWb14()); //險種
						liaRcvAnnounce.setPaytype(com051wb.getWb15()); //公自費件
						liaRcvAnnounce.setItema(new BigDecimal(com051wb.getWb16()).toString()); //給付身故
						liaRcvAnnounce.setItemb(new BigDecimal(com051wb.getWb17()).toString()); //給付全殘或最高失能
						liaRcvAnnounce.setItemc(new BigDecimal(com051wb.getWb18()).toString()); //給付失能扶助金
						liaRcvAnnounce.setItemd(new BigDecimal(com051wb.getWb19()).toString()); //給付特定事故保險金
						liaRcvAnnounce.setIteme(new BigDecimal(com051wb.getWb20()).toString()); //給付初次罹患
						liaRcvAnnounce.setItemf(new BigDecimal(com051wb.getWb21()).toString()); //給付醫療限額
						liaRcvAnnounce.setItemg(new BigDecimal(com051wb.getWb22()).toString()); //給付醫療限額自負額
						liaRcvAnnounce.setItemh(new BigDecimal(com051wb.getWb23()).toString()); //給付日額
						liaRcvAnnounce.setItemi(new BigDecimal(com051wb.getWb24()).toString()); //給付住院手術
						liaRcvAnnounce.setItemj(new BigDecimal(com051wb.getWb25()).toString()); //給付門診手術
						liaRcvAnnounce.setItemk(new BigDecimal(com051wb.getWb26()).toString()); //給付門診
						liaRcvAnnounce.setIteml(new BigDecimal(com051wb.getWb27()).toString()); //給付重大疾病含特定
						liaRcvAnnounce.setItemm(new BigDecimal(com051wb.getWb28()).toString()); //給付重大燒燙傷
						liaRcvAnnounce.setItemn(new BigDecimal(com051wb.getWb29()).toString()); //給付癌症療傷
						liaRcvAnnounce.setItemo(new BigDecimal(com051wb.getWb30()).toString()); //給付出院療養
						liaRcvAnnounce.setItemp(new BigDecimal(com051wb.getWb31()).toString()); //給付失能
						liaRcvAnnounce.setItemq(new BigDecimal(com051wb.getWb32()).toString()); //給付喪葬費用
						liaRcvAnnounce.setItemr(new BigDecimal(com051wb.getWb33()).toString()); //給付項目自負額
						liaRcvAnnounce.setItems(new BigDecimal(com051wb.getWb34()).toString()); //給付分期
						liaRcvAnnounce.setValdate(com051wb.getWb35()); //契約生效日期
						liaRcvAnnounce.setValtime(com051wb.getWb36()); //契約生效時間
						liaRcvAnnounce.setOvrdate(com051wb.getWb37()); //契約滿期日期
						liaRcvAnnounce.setOvrtime(com051wb.getWb38());//契約滿期時間
						liaRcvAnnounce.setPrm(new BigDecimal(com051wb.getWb39()).toString());//保費
						liaRcvAnnounce.setBamttype(com051wb.getWb40());//保費繳別
						liaRcvAnnounce.setPrmyears(com051wb.getWb41().toString());//繳費年期
						liaRcvAnnounce.setCon(com051wb.getWb42());//保單狀況
						liaRcvAnnounce.setCondate(com051wb.getWb43());//保單狀況生效日期
						liaRcvAnnounce.setContime(com051wb.getWb44());//保單狀況生效時間
						liaRcvAnnounce.setAskname(com051wb.getWb45());//要保人姓名
						liaRcvAnnounce.setAskidno(com051wb.getWb46());//要保人身份證號
						if(!"00000000".equals(com051wb.getWb47())){
							liaRcvAnnounce.setAskbirdate(com051wb.getWb47());//要保人出生日期
						}
						liaRcvAnnounce.setAsktype(com051wb.getWb48());//要保與被保險人關係
						liaRcvAnnounce.setFilldate(com051wb.getWb49());//要保書日期
						liaRcvAnnounce.setBroktype(com051wb.getWb50());//保經代分類
						liaRcvAnnounce.setDcreate(new Date());
						liaRcvAnnounce.setSourceRemark("AS400");
						/*
						WB60	傳送註記
						WB61	建檔日　
						WB62	建檔時　
						*/
						result = this.liaRcvAnnounceService.insertLiaRcvAnnounce(liaRcvAnnounce);
						if(result.getResObject() == null){
							mailBody += "新增發生錯誤[被保險人ID = " + com051wb.getWb04() + "，被保險人生日 = " + com051wb.getWb05() + 
									"，保單號碼 = " + com051wb.getWb08() + "，保單分類 = " + com051wb.getWb12() + "，險種分類 = " + com051wb.getWb13() + 
									"，險種 = " + com051wb.getWb14() + "，保單狀況 = " + com051wb.getWb42() + "] <br>";
							failCount++;
						}else{
							//更新400傳送註記
							com051wb.setWb60("Y");
							result = this.com051wbService.updateCom051wbForWb60(com051wb);
						}
					}
				}
				params = null;
			}
			subject = env + " - 傷害險收件通報轉檔系統作業(COM051WB → LIA)-轉檔結果(新增成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險收件通報轉檔系統作業(COM051WB → LIA)-轉檔結果(發生異常)" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}
	}

	/**
	 * AS400 COM051WA → LiaUndwrtAnnounce
	 */
	@Override
	public void undwrtComm051WAToAnnounceService() throws SystemException,
			Exception {
		
		Mailer mailer = new Mailer();
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("com051ToLia_mail_to_address");
		String cc = "";
		String mailBody = "";
		
		try{
			
			//清除註記 開始
			Date date = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("mm");
			SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd");
			String hh = dateFormat1.format(date);
			String mm = dateFormat2.format(date);
			String yyyyMMdd = dateFormat3.format(date);
			logger.info("yyyyMMdd hhmm = " + yyyyMMdd + "  " + hh + ":" + mm);
			//清除註記 
			String h1 = "";
			String h2 = "";
			String m1 = "";
			String m2 = "";
			if("15".equals(mm)){
				h1 = String.valueOf(Integer.parseInt(hh) - 1);
				h2 = hh;
				m1 = "45";
				m2 = "15";
			}
			if("45".equals(mm)){
				h1 = hh;
				h2 = hh;
				m1 = "15";
				m2 = "45";
			}
			if(!StringUtil.isSpace(h1) && !StringUtil.isSpace(h2)){
				Map params1 = new HashMap();
				params1.put("yyyyMMdd", yyyyMMdd);
				params1.put("hhmm1", h1 + "" + m1);
				params1.put("hhmm2", h2 + "" + m2);
				com051waService.updateCom051waForWa60(params1);
			}
			//清除註記 結束
			
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)
			Result result = com051waService.findUnsendCom051waData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險承保通報轉檔系統作業-轉檔結果(COM051WA → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<Com051wa> unsendIdList = (ArrayList<Com051wa>)result.getResObject();
			for(Com051wa tmpCom051wa : unsendIdList){
				//依據ID逐筆找出要轉換的資料
				String idno = tmpCom051wa.getWa04();
				Map<String, String> params = new HashMap<String, String>();
				params.put("wa04", idno);
				/*
				 * 因為會有WA60被押Y的情況發生，因此在200210714開始，改成400在20210713之後產生的資料，判斷WA60非X的情況
				 */
				params.put("wa60NotEqualX", "X");
				params.put("wa61GreatThanSomeday", "20210713");
				result = com051waService.findCom051waByParams(params);
				if(result.getResObject() != null){
					ArrayList<Com051wa> findByIdList = (ArrayList<Com051wa>)result.getResObject();
					for(Com051wa com051wa : findByIdList){
						count++;
						LiaUndwrtAnnounce liaUndwrtAnnounce = new LiaUndwrtAnnounce();
						liaUndwrtAnnounce.setCmptype(com051wa.getWa01()); //產壽險別
						liaUndwrtAnnounce.setCmpno(com051wa.getWa02()); //公司代號
						liaUndwrtAnnounce.setName(com051wa.getWa03()); //被保險人姓名
						liaUndwrtAnnounce.setIdno(com051wa.getWa04()); //被保險人ID
						liaUndwrtAnnounce.setBirdate(com051wa.getWa05()); //被保險人生日
						liaUndwrtAnnounce.setSex(com051wa.getWa06()); //被保險人性別
						liaUndwrtAnnounce.setInsnom(com051wa.getWa07()); //主約保單號碼
						liaUndwrtAnnounce.setInsno(com051wa.getWa08()); //保單號碼
						liaUndwrtAnnounce.setOrigin(com051wa.getWa09()); //來源別
						liaUndwrtAnnounce.setChannel(com051wa.getWa10()); //銷售通路別
						liaUndwrtAnnounce.setPrdcode(com051wa.getWa11()); //商品代碼
						liaUndwrtAnnounce.setInsclass(com051wa.getWa12()); //保單分類
						liaUndwrtAnnounce.setInskind(com051wa.getWa13()); //險種分類
						liaUndwrtAnnounce.setInsitem(com051wa.getWa14()); //險種
						liaUndwrtAnnounce.setPaytype(com051wa.getWa15()); //公自費件
						liaUndwrtAnnounce.setItema(new BigDecimal(com051wa.getWa16()).toString()); //給付身故
						liaUndwrtAnnounce.setItemb(new BigDecimal(com051wa.getWa17()).toString()); //給付全殘或最高失能
						liaUndwrtAnnounce.setItemc(new BigDecimal(com051wa.getWa18()).toString()); //給付失能扶助金
						liaUndwrtAnnounce.setItemd(new BigDecimal(com051wa.getWa19()).toString()); //給付特定事故保險金
						liaUndwrtAnnounce.setIteme(new BigDecimal(com051wa.getWa20()).toString()); //給付初次罹患
						liaUndwrtAnnounce.setItemf(new BigDecimal(com051wa.getWa21()).toString()); //給付醫療限額
						liaUndwrtAnnounce.setItemg(new BigDecimal(com051wa.getWa22()).toString()); //給付醫療限額自負額
						liaUndwrtAnnounce.setItemh(new BigDecimal(com051wa.getWa23()).toString()); //給付日額
						liaUndwrtAnnounce.setItemi(new BigDecimal(com051wa.getWa24()).toString()); //給付住院手術
						liaUndwrtAnnounce.setItemj(new BigDecimal(com051wa.getWa25()).toString()); //給付門診手術
						liaUndwrtAnnounce.setItemk(new BigDecimal(com051wa.getWa26()).toString()); //給付門診
						liaUndwrtAnnounce.setIteml(new BigDecimal(com051wa.getWa27()).toString()); //給付重大疾病含特定
						liaUndwrtAnnounce.setItemm(new BigDecimal(com051wa.getWa28()).toString()); //給付重大燒燙傷
						liaUndwrtAnnounce.setItemn(new BigDecimal(com051wa.getWa29()).toString()); //給付癌症療傷
						liaUndwrtAnnounce.setItemo(new BigDecimal(com051wa.getWa30()).toString()); //給付出院療養
						liaUndwrtAnnounce.setItemp(new BigDecimal(com051wa.getWa31()).toString()); //給付失能
						liaUndwrtAnnounce.setItemq(new BigDecimal(com051wa.getWa32()).toString()); //給付喪葬費用
						liaUndwrtAnnounce.setItemr(new BigDecimal(com051wa.getWa33()).toString()); //給付項目自負額
						liaUndwrtAnnounce.setItems(new BigDecimal(com051wa.getWa34()).toString()); //給付分期
						liaUndwrtAnnounce.setValdate(com051wa.getWa35()); //契約生效日期
						liaUndwrtAnnounce.setValtime(com051wa.getWa36()); //契約生效時間
						liaUndwrtAnnounce.setOvrdate(com051wa.getWa37()); //契約滿期日期
						liaUndwrtAnnounce.setOvrtime(com051wa.getWa38());//契約滿期時間
						liaUndwrtAnnounce.setPrm(new BigDecimal(com051wa.getWa39()).toString());//保費
						liaUndwrtAnnounce.setBamttype(com051wa.getWa40());//保費繳別
						liaUndwrtAnnounce.setPrmyears(com051wa.getWa41().toString());//繳費年期
						liaUndwrtAnnounce.setCon(com051wa.getWa42());//保單狀況
						liaUndwrtAnnounce.setCondate(com051wa.getWa43());//保單狀況生效日期
						liaUndwrtAnnounce.setContime(com051wa.getWa44());//保單狀況生效時間
						liaUndwrtAnnounce.setAskname(com051wa.getWa45());//要保人姓名
						liaUndwrtAnnounce.setAskidno(com051wa.getWa46());//要保人身份證號
						if(!"00000000".equals(com051wa.getWa47())){
							liaUndwrtAnnounce.setAskbirdate(com051wa.getWa47());//要保人出生日期
						}
						
						liaUndwrtAnnounce.setAsktype(com051wa.getWa48());//要保與被保險人關係
						liaUndwrtAnnounce.setDcreate(new Date());
						liaUndwrtAnnounce.setSourceRemark("AS400");
						/*
						WB60	傳送註記
						WB61	建檔日　
						WB62	建檔時　
						*/
						result = this.liaUndwrtAnnounceService.insertLiaUndwrtAnnounce(liaUndwrtAnnounce);
						if(result.getResObject() == null){
							mailBody += "新增發生錯誤[被保險人ID = " + com051wa.getWa04() + "，被保險人生日 = " + com051wa.getWa05() + 
									"，保單號碼 = " + com051wa.getWa08() + "，保單分類 = " + com051wa.getWa12() + "，險種分類 = " + com051wa.getWa13() + 
									"，險種 = " + com051wa.getWa14() + "，保單狀況 = " + com051wa.getWa42() + "] <br>";
							failCount++;
						}else{
							//更新400傳送註記
							com051wa.setWa60("Y");
							result = this.com051waService.updateCom051waForWa60(com051wa);
						}
					}
				}
				params = null;
			}
			subject = env + " - 傷害險承保通報轉檔系統作業(COM051WA → LIA)-轉檔結果(新增成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險承保通報轉檔系統作業(COM051WA → LIA)-轉檔結果(發生異常)" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}
		
	}
	
	/**
	 * AssocRcvAncmt → LiaRcvAnnounce
	 */
	@Override
	public void rcvAssocToAnnounceService() throws SystemException, Exception {
		
		
		Mailer mailer = new Mailer();
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("toAssoc_mail_to_address");
		String cc = "";
		String mailBody = "";
		
		try{
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)，要排除400件，否則會重複送
			Result result = assocRcvAncmtService.findUnsendAssocRcvAncmtData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險收件通報轉檔系統作業-轉檔結果(ASSOC → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<AssocRcvAncmt> unsendIdList = (ArrayList<AssocRcvAncmt>)result.getResObject();
			for(AssocRcvAncmt tmpAssocRcvAncmt : unsendIdList){
				//依據ID逐筆找出要轉換的資料
				String idno = tmpAssocRcvAncmt.getIdno();
				Map<String, String> params = new HashMap<String, String>();
				params.put("idno", idno);
				params.put("toLiaFlagIsNull", "Y");
				params.put("condateGt", "01100101"); //抓110/01/01以後的
				result = assocRcvAncmtService.findAssocRcvAncmtByParams(params);
				if(result.getResObject() != null){
					ArrayList<AssocRcvAncmt> findByIdList = (ArrayList<AssocRcvAncmt>)result.getResObject();
					for(AssocRcvAncmt assocRcvAncmt : findByIdList){
						count++;
						LiaRcvAnnounce liaRcvAnnounce = new LiaRcvAnnounce();
						BeanUtils.copyProperties(liaRcvAnnounce, assocRcvAncmt);
						result = this.liaRcvAnnounceService.insertLiaRcvAnnounce(liaRcvAnnounce);
						if(result.getResObject() == null){
							mailBody += "新增發生錯誤[被保險人ID = " + assocRcvAncmt.getIdno() + "，被保險人生日 = " + assocRcvAncmt.getBirdate() + 
									"，保單號碼 = " + assocRcvAncmt.getInsno() + "，保單分類 = " + assocRcvAncmt.getInsclass() + "，險種分類 = " + assocRcvAncmt.getInskind() + 
									"，險種 = " + assocRcvAncmt.getInsitem() + "，保單狀況 = " + assocRcvAncmt.getCon() + "] <br>";
							failCount++;
						}else{
							//更新400傳送註記
							assocRcvAncmt.setToLiaFlag("Y");
							result = this.assocRcvAncmtService.updateAssocRcvAncmt(assocRcvAncmt);
						}
					}
				}
				params = null;
			}
			subject = env + " - 傷害險收件通報轉檔系統作業(ASSOC → LIA)-轉檔結果(新增成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險收件通報轉檔系統作業(ASSOC → LIA)-轉檔結果(發生異常)" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}
	}

	/**
	 *  AssocAnnAssuw → LiaUndwrtAnnounce
	 */
	@Override
	public void undwrtAssocToAnnounceService() throws SystemException,Exception {
		
		Mailer mailer = new Mailer();
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("toAssoc_mail_to_address");
		String cc = "";
		String mailBody = "";
		
		try{
			//找出需要傳送的承保通報(每次固定抓1000筆id，再依據ID找需要送出的資料)，要排除400件，否則會重複送
			Result result = assocAnnAssuwService.findUnsendAssocAnnAssuwData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險承保通報轉檔系統作業-轉檔結果(ASSOC → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<AssocAnnAssuw> unsendIdList = (ArrayList<AssocAnnAssuw>)result.getResObject();
			for(AssocAnnAssuw tmpAssocAnnAssuw : unsendIdList){
				//依據ID逐筆找出要轉換的資料
				String idno = tmpAssocAnnAssuw.getIdno();
				Map<String, String> params = new HashMap<String, String>();
				params.put("idno", idno);
				params.put("toLiaFlagIsNull", "Y");
				params.put("condateGt", "01100101"); //抓110/01/01以後的
				result = assocAnnAssuwService.findAssocAnnAssuwByParams(params);
				if(result.getResObject() != null){
					ArrayList<AssocAnnAssuw> findByIdList = (ArrayList<AssocAnnAssuw>)result.getResObject();
					for(AssocAnnAssuw assocAnnAssuw : findByIdList){
						count++;
						LiaUndwrtAnnounce liaUndwrtAnnounce = new LiaUndwrtAnnounce();
						BeanUtils.copyProperties(liaUndwrtAnnounce, assocAnnAssuw);
						result = this.liaUndwrtAnnounceService.insertLiaUndwrtAnnounce(liaUndwrtAnnounce);
						if(result.getResObject() == null){
							mailBody += "新增發生錯誤[被保險人ID = " + assocAnnAssuw.getIdno() + "，被保險人生日 = " + assocAnnAssuw.getBirdate() + 
									"，保單號碼 = " + assocAnnAssuw.getInsno() + "，保單分類 = " + assocAnnAssuw.getInsclass() + "，險種分類 = " + assocAnnAssuw.getInskind() + 
									"，險種 = " + assocAnnAssuw.getInsitem() + "，保單狀況 = " + assocAnnAssuw.getCon() + "] <br>";
							failCount++;
						}else{
							//更新400傳送註記
							assocAnnAssuw.setToLiaFlag("Y");
							result = this.assocAnnAssuwService.updateAssocAnnAssuw(assocAnnAssuw);
						}
					}
				}
				params = null;
			}
			subject = env + " - 傷害險承保通報轉檔系統作業(ASSOC → LIA)-轉檔結果(新增成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 傷害險承保通報轉檔系統作業(ASSOC → LIA)-轉檔結果(發生異常)" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}
		
	}
	
	@Override
	public void ipb902iService() throws SystemException, Exception {
		Mailer mailer = new Mailer();
		String url = this.configUtil.getString("cwpLiaUrl") + "CWP/webService/lia/api/ipb902i";
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String mailContentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("com051ToLia_mail_to_address");
		String cc = "";
		String mailBody = "";
		CloseableHttpClient httpClient = null;
		int timeout = 600; //秒
		Ipb902iResponseVo ipb902iResponseVo = null;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			
			//清除註記 開始
			/*
			 * 原本是因為佩君程式有問題(又查不出原因)，因此需要清除她程式誤上的註記
			 * 但因為保險存摺和承保通報一樣一天一次，暫時不作清除註記
			 */
			
			//清除註記 結束
			
			
			//找出需要傳送的收件通報(每次固定抓3000筆id，再依據ID找需要送出的資料)
			Result result = com051weService.findUnsendCom051weData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險承保通報轉檔系統作業-轉檔結果(COM051WA → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<Com051we> unsendIdList = (ArrayList<Com051we>)result.getResObject();
			for(Com051we tmpCom051we : unsendIdList){
				
				ArrayList<Ipb902iRequestVo> ipb902iRequestVoList = new ArrayList<Ipb902iRequestVo>();
				
				//依據ID逐筆找出要轉換的資料
				String idno = tmpCom051we.getWe13();
				Map<String, String> params = new HashMap<String, String>();
				params.put("we13", idno);
				params.put("we20IsNull", "Y");
				params.put("fetchFirst20", "Y");
				
				
				result = com051weService.findCom051weByParams(params);
				if(result.getResObject() != null){
					
					try{
						ArrayList<Com051we> findByIdList = (ArrayList<Com051we>)result.getResObject();
						for(Com051we com051we : findByIdList){
							count++;
							Ipb902iRequestVo vo = new Ipb902iRequestVo();
							vo.setReqMode(com051we.getWe01());//操作模式
							vo.setConvenantType(com051we.getWe02()); //主附約號
							vo.setCprodName(com051we.getWe03()); //保險公司的產品名稱
							vo.setCinsKindName(com051we.getWe04()); //保險險種名稱
							
							String policyno = "";
							if(com051we.getWe15().indexOf("-") != -1){
								policyno = com051we.getWe15().split("-")[0].trim();	
							}else{
								policyno = com051we.getWe15().trim();	
							}
							Map<String, String> params1 = new HashMap<String, String>();
							params1.put("policyno", policyno);
							// 確認該單是否為電子保單 - 目前只有核新件有寄送
							result = prpcmainService.findPrpcmainByParams(params1);
							if(result.getResObject() != null){
								ArrayList<Prpcmain> list = (ArrayList<Prpcmain>)result.getResObject();
								Prpcmain prpcmain = list.get(0);
								if("Y".equals(prpcmain.getEpolicy())){
									vo.setCelectronicType("2"); //電子保單
								}else{
									vo.setCelectronicType("1"); //紙本保單
								}
							}else{
								//查不到就用原本400設定的
								vo.setCelectronicType(com051we.getWe05()); //電子/紙本保單
							}

							vo.setCmptype("N"); //產壽險別1 //TODO 先寫死N
							vo.setCmpno(com051we.getWe12()); //公司代號
							vo.setIdno(com051we.getWe13()); //被保險人ＩＤ
							vo.setBirdate(com051we.getWe14()); //被保險人出生日期
							vo.setInsno(com051we.getWe15()); //保單號碼
							vo.setInsclass(com051we.getWe16()); //保單分類
							vo.setInskind(com051we.getWe17()); //險種分類
							vo.setInsitem(com051we.getWe18()); //險種
							
							ipb902iRequestVoList.add(vo);
						}
						
						String json = JsonUtil.getJSONString(ipb902iRequestVoList);
						
						//呼叫web service
			        	RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).build();
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
			            	failCount = failCount + ipb902iRequestVoList.size();
			            	System.out.println("連線至CWP webservice發生問題，status code = " + statusCode);
			            	continue;
			            }
			            
			            HttpEntity entity = httpResponse.getEntity();
			            ContentType contentType = ContentType.getOrDefault(entity);
			            
			            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
			            	String jsonStr = EntityUtils.toString(entity, "UTF-8");
			            	System.out.println(jsonStr);
			            	
			            	ObjectMapper objectMapper = new ObjectMapper();
			        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			        		ipb902iResponseVo = objectMapper.readValue(jsonStr, Ipb902iResponseVo.class);
			        		System.out.println(ipb902iResponseVo.getCode() + "-" + ipb902iResponseVo.getMsg());
			        		if(ipb902iResponseVo != null){
			        			//傳送成功的更新時間註記
			        			List<Data> datas = ipb902iResponseVo.getDatas();
			        			for(Data data : datas){
			        				///成功回傳0
			        				if("0".equals(data.getCode())){
			        					Com051we com051we = new Com051we();
			        					com051we.setWe11(data.getCmptype());
			        					com051we.setWe12(data.getCmpno());
			        					com051we.setWe13(data.getIdno());
			        					com051we.setWe14(data.getBirdate());
			        					com051we.setWe15(data.getInsno());
			        					com051we.setWe16(data.getInsclass());
			        					com051we.setWe17(data.getInskind());
			        					com051we.setWe18(data.getInsitem());
			        					com051we.setWe20(sdFormat.format(new Date()));
			        					result = com051weService.updateCom051weForWe20(com051we);
			        				}else{
			        					failCount++;
			        				}
			        			}
			        		}
			            }
			            ipb902iRequestVoList = null; //轉成字串已無用
						params = null;
					}catch (Exception e) {
						e.printStackTrace();
						failCount++;
						
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logger.debug(e.getMessage(), e);
						
						String subjectStr = env + " - AS400傷害險IPB902I傳送至公會結果(發生異常)" ;
						StringBuffer errorStrBuf = new StringBuffer();
						errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
						
						mailer = new Mailer();
						mailer.sendmail(smtpServer, mailContentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
						continue;
					}
				}
			}
			subject = env + " - AS400傷害險IPB902I傳送至公會結果：成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, mailContentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.debug(e.getMessage(), e);
			subject = env + " - AS400傷害險IPB902I傳送至公會結果-轉檔發生異常" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, mailContentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}finally{
        	if(httpClient != null){
        		httpClient.close();
        		httpClient = null;
        	}
        }
		
	}
	
	@Override
	public void coreIpb902iService() throws SystemException, Exception {
		Mailer mailer = new Mailer();
		String url = this.configUtil.getString("cwpLiaUrl") + "CWP/webService/lia/api/ipb902i";
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String mailContentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("com051ToLia_mail_to_address");
		String cc = "";
		String mailBody = "";
		
		int timeout = 600; //秒
		Ipb902iResponseVo ipb902iResponseVo = null;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			
			
			//找出需要傳送的收件通報(每次固定抓1000筆id，再依據ID找需要送出的資料)
			Result result = undwrtIpb902iService.findUnsendUndwrtIpb902iData();
			if(result.getResObject() == null){
//				subject = env + " - 傷害險承保通報轉檔系統作業-轉檔結果(COM051WA → LIA 無資料)" ;
//				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
				return;
			}
			int count = 0;
			int failCount = 0;
			//有待處理資料時
			ArrayList<UndwrtIpb902i> unsendIdList = (ArrayList<UndwrtIpb902i>)result.getResObject();
			for(UndwrtIpb902i undwrtIpb902i : unsendIdList){
				
				//依據ID逐筆找出要轉換的資料
				String idno = undwrtIpb902i.getIdno();
				Map<String, String> params = new HashMap<String, String>();
				params.put("idno", idno);
				params.put("sendtimeIsNull", "Y");
				params.put("sortBy", "OID");
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCurrentPage(1);
				pageInfo.setPageSize(20);
				pageInfo.setFilter(params);
				
				result = undwrtIpb902iService.findUndwrtIpb902iByPageInfo(pageInfo);
				if(result.getResObject() != null){
					CloseableHttpClient httpClient = null;
					try{
						ArrayList<UndwrtIpb902i> findByIdList = (ArrayList<UndwrtIpb902i>)result.getResObject();
						for(UndwrtIpb902i ipb902i : findByIdList){
							ArrayList<Ipb902iRequestVo> ipb902iRequestVoList = new ArrayList<Ipb902iRequestVo>();
							count++;
							Ipb902iRequestVo vo = new Ipb902iRequestVo();
							vo.setReqMode(ipb902i.getReqMode());//操作模式
							vo.setConvenantType(ipb902i.getConvenantType()); //主附約號
							vo.setCprodName(ipb902i.getCprodName()); //保險公司的產品名稱
							vo.setCinsKindName(ipb902i.getCinsKindName()); //保險險種名稱
							vo.setCelectronicType(ipb902i.getCelectronicType()); //電子/紙本保單
							vo.setCmptype(ipb902i.getCmptype()); //產壽險別1 //TODO 先寫死N
							vo.setCmpno(ipb902i.getCmpno()); //公司代號
							vo.setIdno(ipb902i.getIdno()); //被保險人ＩＤ
							vo.setBirdate(ipb902i.getBirdate()); //被保險人出生日期
							vo.setInsno(ipb902i.getInsno()); //保單號碼
							vo.setInsclass(ipb902i.getInsclass()); //保單分類
							vo.setInskind(ipb902i.getInskind()); //險種分類
							vo.setInsitem(ipb902i.getInsitem()); //險種
							
							ipb902iRequestVoList.add(vo);
							
							String json = JsonUtil.getJSONString(ipb902iRequestVoList);
							
							//呼叫web service
				        	RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).build();
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
				            	failCount = failCount + ipb902iRequestVoList.size();
				            	System.out.println("連線至CWP webservice發生問題，status code = " + statusCode);
				            	continue;
				            }
				            
				            HttpEntity entity = httpResponse.getEntity();
				            ContentType contentType = ContentType.getOrDefault(entity);
				            
				            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
				            	String jsonStr = EntityUtils.toString(entity, "UTF-8");
				            	System.out.println(jsonStr);
				            	
				            	ObjectMapper objectMapper = new ObjectMapper();
				        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				        		ipb902iResponseVo = objectMapper.readValue(jsonStr, Ipb902iResponseVo.class);
				        		System.out.println(ipb902iResponseVo.getCode() + "-" + ipb902iResponseVo.getMsg());
				        		if(ipb902iResponseVo != null){
				        			//傳送成功的更新時間註記
				        			List<Data> datas = ipb902iResponseVo.getDatas();
				        			for(Data data : datas){
				        				///成功回傳0
				        				if("0".equals(data.getCode())){
				        					ipb902i.setSendtime(new Date());
				        					ipb902i.setDupdate(new Date());
				        					result = undwrtIpb902iService.updateUndwrtIpb902i(ipb902i);
				        				}else{
				        					failCount++;
				        				}
				        			}
				        		}
				            }
				            ipb902iRequestVoList = null; //轉成字串已無用
						}
						params = null;
					}catch (Exception e) {
						e.printStackTrace();
						failCount++;
						
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logger.debug(e.getMessage(), e);
						
						String subjectStr = env + " - 新核心傷害險IPB902I傳送至公會結果(發生異常)" ;
						StringBuffer errorStrBuf = new StringBuffer();
						errorStrBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
						
						mailer = new Mailer();
						mailer.sendmail(smtpServer, mailContentType, subjectStr, from, "", to, "", cc, "", "", "", errorStrBuf.toString(), auth, userName, password);
						continue;
					}finally{
			        	if(httpClient != null){
			        		httpClient.close();
			        		httpClient = null;
			        	}
			        }
				}
			}
			subject = env + " - 新核心傷害險IPB902I傳送至公會結果：成功： " + (count - failCount) + "筆，失敗：" + failCount + "筆)" ;
			mailer.sendmail(smtpServer, mailContentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.debug(e.getMessage(), e);
			subject = env + " - 新核心傷害險IPB902I傳送至公會結果-轉檔發生異常" ;
			mailBody = "發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]";
			mailer.sendmail(smtpServer, mailContentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
			throw e;
		}finally{
        
        }
		
	}
	
	private static void writeStringToFile(String json, String checkNo) throws IOException {
		FileOutputStream writerStream = null;
		BufferedWriter writer = null;
		try {
			String date = DateUtils.getDateString(new Date());
			String fileDir = "D:" + File.separator + "notify_json_Bak" + File.separator + date.split("/")[0] + File.separator + date.split("/")[1] + File.separator;
			String jsonfile = checkNo + ".json";
			String zipfile = checkNo + ".zip";
			File file = new File( fileDir + jsonfile);
			try {
	            FileUtils.touch(file);
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
			
			int bufferSize = 8 * 1024;
			
			writerStream = new FileOutputStream(file);    
			writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"), bufferSize); 
			writer.write(json);
			
			ArrayList<File> fileList = new ArrayList();
			fileList.add(file);
			writer.flush();
			writer.close();
			writer = null;
			//壓縮
			writeZip(fileDir + zipfile, fileList, "27938888");
			//刪檔
			FileUtils.forceDelete(file);
//			FileUtils.forceDelete(file);
//			FileUtils.forceDeleteOnExit(file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer != null){
				writer.close();
				writer = null;
			}
			if(writerStream != null){
				writerStream.close();
				writerStream = null;
			}
		}
	}
	
//	private static void writeStringToFile(String json, String checkNo) throws IOException {
//		InputStream inputStream = null;
//		try {
//			String date = DateUtils.getDateString(new Date());
//			String fileDir = "D:" + File.separator + "notify_json_Bak" + File.separator + date.split("/")[0] + File.separator + date.split("/")[1] + File.separator;
//			String jsonfile = checkNo + ".json";
//			String zipfile = fileDir + checkNo + ".zip";
//			inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
//			writeZip(zipfile, inputStream, "27938888");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			if(inputStream != null){
//				inputStream.close();
//				inputStream = null;
//			}
//
//		}
//	}
	
	private static void writeZip(String fileName, ArrayList<File> filesToAdd, String pwd) throws Exception {			
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(fileName);
		} catch (ZipException e) {
			e.printStackTrace();
		}		

		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if(!StringUtil.isSpace(pwd)){
			parameters.setEncryptFiles(true);
			parameters.setPassword(pwd);
		}
		try {
			zipFile.addFiles(filesToAdd, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		} finally{
		}
	}
	
//	private static void writeZip(String zipFilePath, InputStream inputStream, String pwd) throws Exception {			
//		ZipFile zipFile = null;
//		
//		try {
//			zipFile = new ZipFile(new File(zipFilePath));
//		} catch (ZipException e) {
//			e.printStackTrace();
//		}		
//
//		ZipParameters parameters = new ZipParameters();
//		parameters.setSourceExternalStream(true);
//		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
//		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
//		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
//		if(!StringUtil.isSpace(pwd)){
//			parameters.setEncryptFiles(true);
//			parameters.setPassword(pwd);
//		}
//		try {
//			zipFile.addStream(inputStream, parameters);
//		} catch (ZipException e) {
//			e.printStackTrace();
//		} finally{
//		}
//	}
	
	private void agjustRowValue(String type, Row row) throws Exception{
		
		
		//姓名長度
		if(row.getAskname() != null){
			if(row.getAskname().length() > 10){
				row.setAskname(substring(row.getAskname(), 20, ""));
			}
		}
		if(row.getName() != null){
			if(row.getName().length() > 10){
				row.setName(substring(row.getName(), 20, ""));
			}
		}
		//condate設為系統日
		row.setCondate(getNowDate());
		
		//收件的要保書填寫日設為系統日
		if("rcv".equals(type)){

	    	BigDecimal b1 = new BigDecimal(row.getValdate());
	    	BigDecimal b2 = new BigDecimal(row.getCondate());
			if(b1.compareTo(b2) > 0){
				row.setFilldate(getNowDate());
			}else{
				row.setFilldate(row.getValdate());
			}
			
		}

		//保額null 轉為 0 
		if(StringUtil.isSpace(row.getItema())){
			row.setItema("0");
		}
		if(StringUtil.isSpace(row.getItemb())){
			row.setItemb("0");
		}
		if(StringUtil.isSpace(row.getItemc())){
			row.setItemc("0");
		}
		if(StringUtil.isSpace(row.getItemd())){
			row.setItemd("0");
		}
		if(StringUtil.isSpace(row.getIteme())){
			row.setIteme("0");
		}
		if(StringUtil.isSpace(row.getItemf())){
			row.setItemf("0");
		}
		if(StringUtil.isSpace(row.getItemg())){
			row.setItemg("0");
		}
		if(StringUtil.isSpace(row.getItemh())){
			row.setItemh("0");
		}
		if(StringUtil.isSpace(row.getItemi())){
			row.setItemi("0");
		}
		if(StringUtil.isSpace(row.getItemj())){
			row.setItemj("0");
		}
		if(StringUtil.isSpace(row.getItemk())){
			row.setItemk("0");
		}
		if(StringUtil.isSpace(row.getIteml())){
			row.setIteml("0");
		}
		if(StringUtil.isSpace(row.getItemm())){
			row.setItemm("0");
		}
		if(StringUtil.isSpace(row.getItemn())){
			row.setItemn("0");
		}
		if(StringUtil.isSpace(row.getItemo())){
			row.setItemo("0");
		}
		if(StringUtil.isSpace(row.getItemp())){
			row.setItemp("0");
		}
		if(StringUtil.isSpace(row.getItemq())){
			row.setItemq("0");
		}
		if(StringUtil.isSpace(row.getItemr())){
			row.setItemr("0");
		}
		if(StringUtil.isSpace(row.getItems())){
			row.setItems("0");
		}
	}
	
	//回壓日期
		public String getNowDate() {
			   /* 時：分：秒  HH:mm:ss  HH : 23小時制 (0-23)
			                            kk : 24小時制 (1-24)
			                            hh : 12小時制 (1-12)
			                            KK : 11小時制 (0-11)*/
			StringBuffer condate = new StringBuffer("");
			Calendar c=Calendar.getInstance();
			int year = c.get(Calendar.YEAR)-1911;
			int month=c.get(Calendar.MONTH)+1;
		    int day=c.get(Calendar.DAY_OF_MONTH);
			
			if(year<1000) {
				condate.append("0"+Integer.toString(year));
			} else {
				condate.append(Integer.toString(year));
			}
			if(month<10) {
				condate.append("0"+Integer.toString(month));
			} else {
				condate.append(Integer.toString(month));
			}
			if(day<10) {
				condate.append("0"+Integer.toString(day));
			} else {
				condate.append(Integer.toString(day));
			}
			//System.out.print(Condate);
			return condate.toString();
		}
	
    /*
    * 按位元組長度擷取字串
    * @param str 將要擷取的字串引數
    * @param toCount 擷取的位元組長度
    * @param more 字串末尾補上的字串
    * @return 返回擷取後的字串
    */
	public static String substring(String str, int toCount, String more){
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++){
	       String s1 = str.valueOf(tempChar[kk]);
	       byte[] b = s1.getBytes();
	       reInt += b.length;
	       reStr += tempChar[kk];
		}
	    if (toCount == reInt || (toCount == reInt - 1))
	    	reStr += more;
	    return reStr;
	}
	
	public static void main(String args[]) throws Exception{
		
		//設定checkno → SHEDULE_ + 西元年月日時分秒毫秒 20210114133955888
		String checkNo = "SCHEDULE_" + DateUtils.format(new Date(),"yyyyMMddHHmmssSSS");
    	checkNo += "_" + new GUID().toString(29 - checkNo.length());
    	
    	System.out.println(checkNo + "," + checkNo.length());
    	
    	writeStringToFile("1111122221111", new GUID().toString(10));
    	
    	
    	BigDecimal b1 = new BigDecimal("01100625");
    	BigDecimal b2 = new BigDecimal("01100625");
    	if(b1.compareTo(b2) > 0){
    		System.out.println("11111");
    		
    	}else{
    		System.out.println("22222222");
    	}
    	
		Date date = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("mm");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd");
		String hh = dateFormat1.format(date);
		String mm = dateFormat2.format(date);
		String yyyyMMdd = dateFormat3.format(date);
		System.out.println("hh = " + hh);
		System.out.println("mm = " + mm);
		System.out.println("yyyyMMdd = " + yyyyMMdd);
		
		String a = "(ytfguhijk)".replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
		System.out.println("a = " + a);
		
	}
	
	@Override
	public Result mailUnsendRecord() throws SystemException, Exception {

		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String mailContentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("notifyUnsend_to_address");
		String cc = "";
		String subjectStr = env + " - 已簽單但未發送收件通報及承保通報通知" ;
		StringBuffer strBuf = new StringBuffer();

		
		ArrayList<Map<String, String>> unsendRcvList = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> unsendUndwrtList = new ArrayList<Map<String, String>>();
		
		try{
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, -1);
			dt = c.getTime();
			String rocDate = DateUtils.getTaiwanDateString(dt).replaceAll("/", "");
			
			ArrayList<String> uwdateList = new ArrayList<String>();
			uwdateList.add(rocDate);
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uwdateList", uwdateList);
			Result result = this.cwpRcvAnnounceService.findUnsendRcvData(params);
			if(result.getResObject() != null){
				unsendRcvList = (ArrayList<Map<String, String>>)result.getResObject();
			}
			
			result = this.cwpUndwrtAnnounceService.findUnsendUndwrtData(params);
			if(result.getResObject() != null){
				unsendUndwrtList = (ArrayList<Map<String, String>>)result.getResObject();
			}
			
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			strBuf.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
		}finally{
			if(strBuf.length() == 0){
				if(unsendRcvList != null){
					if(unsendRcvList.size() > 0){
						strBuf.append("<table border=1 style='border-collapse: collapse;'>");
						strBuf.append("<tr><td colspan='5' >已簽單但未送收件通報：</td></tr>");
						strBuf.append("<tr bgcolor='#70bbd9'>");
						strBuf.append("<td></td>");
						strBuf.append("<td>保單號</td>");
						strBuf.append("<td>批單號</td>");
						strBuf.append("<td>生效日</td>");
						strBuf.append("<td>ID</td>");
						strBuf.append("</tr>");
						int i = 1;
						for(Map<String, String> map: unsendRcvList){
							String plyno = StringUtil.nullToSpace((String)map.get("plyno"));
							String pecno = StringUtil.nullToSpace((String)map.get("pecno"));
							String startdate = StringUtil.nullToSpace((String)map.get("startdate"));
							String identifynumber = StringUtil.nullToSpace((String)map.get("identifynumber"));

							strBuf.append("<tr>");
							strBuf.append("<td>" + (i++) + "</td>");
							strBuf.append("<td>" + plyno + "</td>");
							strBuf.append("<td>" + pecno + "</td>");
							strBuf.append("<td>" + startdate + "</td>");
							strBuf.append("<td>" + identifynumber + "</td>");
							strBuf.append("</tr>");
						}
						strBuf.append("</table>");
					}else{
						strBuf.append("<table border=1 style='border-collapse: collapse;'>");
						strBuf.append("<tr><td>已簽單但未送收件通報： 無資料</td></tr>");
						strBuf.append("</table>");
					}
				}
				strBuf.append("</br>");
				if(unsendUndwrtList != null){
					if(unsendUndwrtList.size() > 0){
						strBuf.append("<table border=1 style='border-collapse: collapse;'>");
						strBuf.append("<tr><td colspan='5' >已簽單但未送承保通報：</td></tr>");
						strBuf.append("<tr bgcolor='#70bbd9'>");
						strBuf.append("<td></td>");
						strBuf.append("<td>保單號</td>");
						strBuf.append("<td>批單號</td>");
						strBuf.append("<td>生效日</td>");
						strBuf.append("<td>ID</td>");
						strBuf.append("</tr>");
						int i = 1;
						for(Map<String, String> map: unsendUndwrtList){
							String plyno = StringUtil.nullToSpace((String)map.get("plyno"));
							String pecno = StringUtil.nullToSpace((String)map.get("pecno"));
							String startdate = StringUtil.nullToSpace((String)map.get("startdate"));
							String identifynumber = StringUtil.nullToSpace((String)map.get("identifynumber"));
							strBuf.append("<tr>");
							strBuf.append("<td>" + (i++) + "</td>");
							strBuf.append("<td>" + plyno + "</td>");
							strBuf.append("<td>" + pecno + "</td>");
							strBuf.append("<td>" + startdate + "</td>");
							strBuf.append("<td>" + identifynumber + "</td>");
							strBuf.append("</tr>");
						}
						strBuf.append("</table>");
					}else{
						strBuf.append("<table border=1 style='border-collapse: collapse;'>");
						strBuf.append("<tr><td>已簽單但未送承保通報： 無資料</td></tr>");
						strBuf.append("</table>");
					}
				}
			}
			
			Mailer	mailer = new Mailer();
			mailer.sendmail(smtpServer, mailContentType, subjectStr, from, "", to, "", cc, "", "", "", strBuf.toString(), auth, userName, password);
		}
		
		
		
		
		return null;
	}
	
	@Override
	public void queryRcvAndUndwrtByFileService(File file, String mail) throws SystemException, Exception {
		ExecutorService executor = null;
		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = "" ;
		String from = configUtil.getString("mail_from_address");
		String to = mail;
		String cc = "";
		StringBuffer mailBody = new StringBuffer();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		try {

			if (file == null) {
				throw new Exception("檔案為null");
			}
			if (StringUtil.isSpace(mail)) {
				throw new Exception("請輸入Email");
			}
			ArrayList<String> allIdList = new ArrayList<String>();
			try (FileInputStream fis = new FileInputStream(file); 
					InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
					BufferedReader reader = new BufferedReader(isr)) {
				String str;
				while ((str = reader.readLine()) != null) {
					if(StringUtil.isSpace(str)){
						continue;
					}
					allIdList.add(str);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(allIdList.size() == 0){
				throw new Exception("檔案內容為空檔");
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			executor = Executors.newCachedThreadPool();
			String appCode = "BAT_APS_" + dateFormat.format(new Date());
			int sizeCount = allIdList.size() / 50;
			if(allIdList.size() % 50 > 0){
				sizeCount++;
			}
			
			// Cast the object to its class type
			ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
			if (allIdList != null) {
				List<List<String>> tempList = CommonFunc.averageAssign(allIdList, sizeCount);
				for (int i = 1; i <= tempList.size(); i++) {
					List<String> inList = tempList.get(i - 1);
					executor.submit(new LiaQueryRcvAndUndwrtThread(appCode, String.valueOf(i), inList));
				}
			}
			// 判斷pool中的執行緒是否都執行完成，執行完成才能shutdown結束排程，目前設定30秒檢查一次
			while (true) {
				int executeCount = pool.getActiveCount();
				logger.info(">>> 檔案批次查詢收件及承保通報目前執行中的執行緒 ：" + executeCount);
				if (executeCount == 0) {
					executor.shutdown();
					break;
				}
				Thread.sleep(30000); // 30秒
			}
			logger.info(">>> 檔案批次查詢收件及承保通報目前執行中的執行緒 ：" + dateFormat.format(new Date()));
			//查詢結果
			Map<String, String> params = new HashMap<String, String>();
			params.put("appCode", appCode);
			//查收件通報
			params.put("queryType", "LIA07062AQ");
			Result result = cwpLiaLia07061aqResultService.findCwpLiaLia07061aqResultByParams(params);
			if(result.getResObject() != null){
				ArrayList<CwpLiaLia07061aqResult> list = (ArrayList<CwpLiaLia07061aqResult>)result.getResObject();
				File rcvZipFile = generateXls(list);
				sendQueryResultMail("收件通報", rcvZipFile, mail);
			}
			//查承保通報
			params.put("queryType", "LIA07072AQ");
			result = cwpLiaLia07061aqResultService.findCwpLiaLia07061aqResultByParams(params);
			if(result.getResObject() != null){
				ArrayList<CwpLiaLia07061aqResult> list = (ArrayList<CwpLiaLia07061aqResult>)result.getResObject();
				File rcvZipFile = generateXls(list);
				sendQueryResultMail("承保通報", rcvZipFile, mail);
			}

		}catch (Exception e) {
			mailBody.setLength(0);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(e);
			subject = env + " - 檔案批次查詢收件及承保通報發生異常。麻煩請重新執行查詢！" ;
			mailBody.append("發生未知錯誤[" + e.getClass().getName() + ":" + errors + "]");
			Mailer mailer = new Mailer();
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			throw e;
		}
	}
	
	private File generateXls(ArrayList<CwpLiaLia07061aqResult> list) {
		FileOutputStream fout = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		Cell cell =  null;
		try {
			
			logger.info("generateXls start ....");
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateStr = sdf.format(calendar.getTime());
			Path tempDirWithPrefix = Files.createTempDirectory("QUERY_LIA_");
			File tempDir = tempDirWithPrefix.toFile();
			String outputFile = tempDir + File.separator + dateStr + ".xls";
			// 創建新的Excel 工作簿
			workbook = new HSSFWorkbook();
			// 在Excel工作簿中建一工作表，其名為缺省值
			// 如要新建一名為"效益指標"的工作表，其語句為：
			// HSSFSheet sheet = workbook.createSheet("效益指標");
			sheet = workbook.createSheet();
			// 在索引0的位置創建行（最頂端的行）
			row = sheet.createRow(0);
			//在索引0的位置創建單格（左上端）
			String title[] = {"OID", "CHECKNO", "KEYIDNO", "DATASERNO", "CMPTYPE", "CMPNO", "NAME" , "IDNO" , "BIRDATE", "SEX", "INSNOM",
					"INSNO", "ORIGIN", "CHANNEL", "PRDCODE", "INSCLASS", "INSKIND", "INSITEM", "PAYTYPE", "ITEMA", "ITEMB", "ITEMC", 
					"ITEMD", "ITEME", "ITEMF", "ITEMG", "ITEMH", "ITEMI", "ITEMJ", "ITEMK", "ITEML", "ITEMM", "ITEMN", "ITEMO", 
					"ITEMP", "ITEMQ", "ITEMR", "ITEMS", "VALDATE" , "VALTIME" , "OVRDATE" , "OVRTIME" , "PRM", "BAMTTYPE", 
					"PRMYEARS" , "CON", "CONDATE", "CONTIME", "ASKNAME" , "ASKIDNO" , "ASKBIRDATE", "ASKTYPE", "FILLDATE", "BROKTYPE"};
			cell =  null;
			for(int j = 0 ; j < title.length ; j++){
				cell = row.createCell(j);
				cell.setCellValue(title[j]);
			}
			
			for(int i = 0 ; i < list.size() ; i++) {
				CwpLiaLia07061aqResult queryResult = list.get(i);
				
				row = sheet.createRow(i + 1);
				cell = row.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getOid().toString());
				cell = row.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getCheckno());
				cell = row.createCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getKeyidno());
				cell = row.createCell(3);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getDataserno());
				cell = row.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getCmptype());
				cell = row.createCell(5);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getCmpno());				
				cell = row.createCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(StringUtil.nullToSpace(queryResult.getName()));
				cell = row.createCell(7);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getIdno());
				cell = row.createCell(8);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getBirdate());
				cell = row.createCell(9);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getSex());
				cell = row.createCell(10);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getInsnom());
				cell = row.createCell(11);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getInsno());
				cell = row.createCell(12);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getOrigin());
				cell = row.createCell(13);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getChannel());
				cell = row.createCell(14);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getPrdcode());
				cell = row.createCell(15);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getInsclass());
				cell = row.createCell(16);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getInskind());
				cell = row.createCell(17);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getInsitem());
				cell = row.createCell(18);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getPaytype());
				cell = row.createCell(19);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItema());
				cell = row.createCell(20);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemb());
				cell = row.createCell(21);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemc());
				cell = row.createCell(22);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemd());
				cell = row.createCell(23);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getIteme());
				cell = row.createCell(24);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemf());
				cell = row.createCell(25);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemg());
				cell = row.createCell(26);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemh());
				cell = row.createCell(27);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemi());
				cell = row.createCell(28);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemj());
				cell = row.createCell(29);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemk());
				cell = row.createCell(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getIteml());
				cell = row.createCell(31);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemm());
				cell = row.createCell(32);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemn());
				cell = row.createCell(33);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemo());
				cell = row.createCell(34);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemp());
				cell = row.createCell(35);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemq());
				cell = row.createCell(36);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItemr());
				cell = row.createCell(37);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getItems());
				cell = row.createCell(38);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getValdate());
				cell = row.createCell(39);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getValtime());
				cell = row.createCell(40);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getOvrdate());
				cell = row.createCell(41);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getOvrtime());
				cell = row.createCell(42);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getPrm());
				cell = row.createCell(43);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getBamttype());
				cell = row.createCell(44);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getPrmyears());
				cell = row.createCell(45);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getCon());
				cell = row.createCell(46);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getCondate());
				cell = row.createCell(47);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getContime());
				cell = row.createCell(48);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getAskname());
				cell = row.createCell(49);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getAskidno());
				cell = row.createCell(50);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getAskbirdate());
				cell = row.createCell(51);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getAsktype());
				cell = row.createCell(52);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getFilldate());
				cell = row.createCell(53);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(queryResult.getBroktype());
//				cell = row.createCell(54);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(queryResult.getDcreate().toString());
			}
			// 新建一輸出檔流
			fout = new FileOutputStream(outputFile);
			// 把相應的Excel 工作簿存檔
			workbook.write(fout);
			fout.flush();
			// 操作結束，關閉檔
			fout.close();
			File file = new File(outputFile);
			File zipFile = new File(tempDir, file.getName().trim().replaceAll(".xls", "") + ".zip");
			ZipUtil zu = new ZipUtil();
			zu.zipAndEncryptFile(file, zipFile, "27938888", true);

			return zipFile;
		} catch (Exception e) {
//			System.out.println("Exception at super.postExecute");
			e.printStackTrace();
		}finally{
			if(fout != null) {
				try {
					fout.close();
					fout = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			workbook = null;
			sheet = null;
			row = null;
			cell =  null;
		}
		return null;
	}
	
	private void sendQueryResultMail(String typeDesc, File zipFile, String mail) {

		String env = configUtil.getString("env");
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String subject = env + " - 傷害險通報" + typeDesc + "查詢結果" ;
		String from = configUtil.getString("mail_from_address");
		String to = mail;
		String cc = "";
		String body = "";

		try {
			Mailer mailer = new Mailer();
			if (zipFile != null) {
				String[] filePath = { zipFile.getPath() };
				String[] fileName = { zipFile.getName() };
				mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", body, auth, userName, password, filePath, fileName);
			}
			
		} catch (Exception e1) {
			logger.error("Exception occurred while sending error notify mail:\n" , e1);
		}
	}
	
	public LiaRcvAnnounceService getLiaRcvAnnounceService() {
		return liaRcvAnnounceService;
	}

	public void setLiaRcvAnnounceService(
			LiaRcvAnnounceService liaRcvAnnounceService) {
		this.liaRcvAnnounceService = liaRcvAnnounceService;
	}

	public LiaUndwrtAnnounceService getLiaUndwrtAnnounceService() {
		return liaUndwrtAnnounceService;
	}

	public void setLiaUndwrtAnnounceService(
			LiaUndwrtAnnounceService liaUndwrtAnnounceService) {
		this.liaUndwrtAnnounceService = liaUndwrtAnnounceService;
	}

	public LiaRcvAnnounceResultService getLiaRcvAnnounceResultService() {
		return liaRcvAnnounceResultService;
	}

	public void setLiaRcvAnnounceResultService(
			LiaRcvAnnounceResultService liaRcvAnnounceResultService) {
		this.liaRcvAnnounceResultService = liaRcvAnnounceResultService;
	}

	public LiaUndwrtAnnounceResultService getLiaUndwrtAnnounceResultService() {
		return liaUndwrtAnnounceResultService;
	}

	public void setLiaUndwrtAnnounceResultService(
			LiaUndwrtAnnounceResultService liaUndwrtAnnounceResultService) {
		this.liaUndwrtAnnounceResultService = liaUndwrtAnnounceResultService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public Com051waService getCom051waService() {
		return com051waService;
	}


	public void setCom051waService(Com051waService com051waService) {
		this.com051waService = com051waService;
	}


	public Com051wbService getCom051wbService() {
		return com051wbService;
	}


	public void setCom051wbService(Com051wbService com051wbService) {
		this.com051wbService = com051wbService;
	}


	public AssocRcvAncmtService getAssocRcvAncmtService() {
		return assocRcvAncmtService;
	}


	public void setAssocRcvAncmtService(AssocRcvAncmtService assocRcvAncmtService) {
		this.assocRcvAncmtService = assocRcvAncmtService;
	}


	public AssocAnnAssuwService getAssocAnnAssuwService() {
		return assocAnnAssuwService;
	}


	public void setAssocAnnAssuwService(AssocAnnAssuwService assocAnnAssuwService) {
		this.assocAnnAssuwService = assocAnnAssuwService;
	}

	public Com051weService getCom051weService() {
		return com051weService;
	}

	public void setCom051weService(Com051weService com051weService) {
		this.com051weService = com051weService;
	}

	public PrpcinsuredService getPrpcinsuredService() {
		return prpcinsuredService;
	}

	public void setPrpcinsuredService(PrpcinsuredService prpcinsuredService) {
		this.prpcinsuredService = prpcinsuredService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public CwpRcvAnnounceService getCwpRcvAnnounceService() {
		return cwpRcvAnnounceService;
	}

	public void setCwpRcvAnnounceService(CwpRcvAnnounceService cwpRcvAnnounceService) {
		this.cwpRcvAnnounceService = cwpRcvAnnounceService;
	}

	public CwpUndwrtAnnounceService getCwpUndwrtAnnounceService() {
		return cwpUndwrtAnnounceService;
	}

	public void setCwpUndwrtAnnounceService(
			CwpUndwrtAnnounceService cwpUndwrtAnnounceService) {
		this.cwpUndwrtAnnounceService = cwpUndwrtAnnounceService;
	}

	public UndwrtIpb902iService getUndwrtIpb902iService() {
		return undwrtIpb902iService;
	}

	public void setUndwrtIpb902iService(UndwrtIpb902iService undwrtIpb902iService) {
		this.undwrtIpb902iService = undwrtIpb902iService;
	}

	public CwpLiaLia07061aqResultService getCwpLiaLia07061aqResultService() {
		return cwpLiaLia07061aqResultService;
	}

	public void setCwpLiaLia07061aqResultService(
			CwpLiaLia07061aqResultService cwpLiaLia07061aqResultService) {
		this.cwpLiaLia07061aqResultService = cwpLiaLia07061aqResultService;
	}
}
