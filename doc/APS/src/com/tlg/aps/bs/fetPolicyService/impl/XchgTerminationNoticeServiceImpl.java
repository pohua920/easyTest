package com.tlg.aps.bs.fetPolicyService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.tlg.aps.bs.fetPolicyService.XchgTerminationNoticeService;
import com.tlg.aps.util.FetTransactionIdGenUtil;
import com.tlg.aps.vo.RptResultBaseVo;
import com.tlg.aps.vo.ShortUrlVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiNoticeCancle;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;
import com.tlg.xchg.entity.MobileInsSms;
import com.tlg.xchg.service.LiaMiNoticeCancleService;
import com.tlg.xchg.service.LiaMiNoticeUnpaidService;
import com.tlg.xchg.service.MobileInsSmsService;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class XchgTerminationNoticeServiceImpl implements XchgTerminationNoticeService {
	
	private static final Logger logger = Logger.getLogger(XchgTerminationNoticeServiceImpl.class);
	
	private ConfigUtil configUtil;
	
	private LiaMiNoticeCancleService liaMiNoticeCancleService;
	private LiaMiNoticeUnpaidService liaMiNoticeUnpaidService;
	private MobileInsSmsService mobileInsSmsService;
//	private String epolicySmsDownloadUrl = "http://epftst.ctbcins.com/FTS/rf/sms/cr/";
//	private String epolicyFtsGenUrl = "http://192.168.112.6:8880/CWP/webService/surl/api/addUrl";
	
	@Override
	public Result batchtLiaMiNoticeCancle(TerminationNotice terminationNotice) throws SystemException, Exception {
		Result result = new Result();
		if(terminationNotice != null) {
			Result temp = null;
			LiaMiNoticeCancle liaMiNoticeCancle = this.mappingLiaMiNoticeCancle(terminationNotice);
			temp = liaMiNoticeCancleService.insertLiaMiNoticeCancle(liaMiNoticeCancle);
			if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					logger.error("batchInsertLiaMiNoticeCancle Insert Termination_Notice fail：Policyno = " + liaMiNoticeCancle.getPolicyno());
					throw new Exception("batchInsertLiaMiNoticeCancle Insert Termination_Notice fail");
			}
			result.setResObject(liaMiNoticeCancle);
			Message message = new Message();
			message.setMessageString("success");
			result.setMessage(message);
		}
		return result;
	}

	@Override
	public Result batchLiaMiNoticeUnpaid(TerminationNotice terminationNotice) throws SystemException, Exception {
		Result result = new Result();
		if(terminationNotice != null) {
			Result temp = null;
			LiaMiNoticeUnpaid liaMiNoticeUnpaid = this.mappingLiaMiNoticeUnpaid(terminationNotice);
			temp = this.liaMiNoticeUnpaidService.insertLiaMiNoticeUnpaid(liaMiNoticeUnpaid);
			if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					logger.error("batchLiaMiNoticeUnpaid Insert Termination_Notice fail：Policyno = " + liaMiNoticeUnpaid.getPolicyno());
					throw new Exception("batchLiaMiNoticeUnpaid Insert Termination_Notice fail");
			}
			result.setResObject(liaMiNoticeUnpaid);
			Message message = new Message();
			message.setMessageString("success");
			result.setMessage(message);
		}
		return result;
	}
	
	@Override
	public Result batchGenMobileInsSmsCancle(LiaMiNoticeCancle liaMiNoticeCancle, TerminationNotice terminationNotice, String pdfFilePath, Date createTime) throws SystemException, Exception {
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		if(pdfFilePath != null && pdfFilePath.length() > 0) {
			liaMiNoticeCancle.setPdftime(sdf.format(createTime));
			result = this.liaMiNoticeCancleService.updateLiaMiNoticeCancle(liaMiNoticeCancle);
			if(result != null && !"PUB120".equals(result.getMessage().getOpCode())) {
				logger.error("batchGenMobileInsSmsCancle Update LiaMiNoticeCancle fail：Serialno = " + liaMiNoticeCancle.getSerialno());
				throw new Exception("batchGenMobileInsSmsCancle Update LiaMiNoticeCancle fail");
			}
			
			MobileInsSms mobileInsSms = this.mappingMobileInsSms(liaMiNoticeCancle.getSerialno(), terminationNotice, pdfFilePath);
			Result temp = this.mobileInsSmsService.insertMobileInsSms(mobileInsSms);
			if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
				logger.error("batchGenMobileInsSmsCancle Insert MobileInsSms fail：" + liaMiNoticeCancle.getSerialno());
				throw new Exception("batchGenMobileInsSmsCancle Insert MobileInsSms fail");
			}

			result.setResObject(liaMiNoticeCancle);
			Message message = new Message();
			message.setMessageString("success");
			result.setMessage(message);
		} else {
			logger.error("batchGenMobileInsSmsCancle create PDF fail：" + liaMiNoticeCancle.getSerialno());
			throw new Exception("batchGenMobileInsSmsCancle create PDF fail");
		}
		return result;
	}
	
	@Override
	public Result batchGenMobileInsSmsUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid, TerminationNotice terminationNotice, String pdfFilePath, Date createTime) throws SystemException, Exception {
		Result result = new Result();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		if(pdfFilePath != null && pdfFilePath.length() > 0) {
			liaMiNoticeUnpaid.setPdftime(sdf.format(createTime));
			result = this.liaMiNoticeUnpaidService.updateLiaMiNoticeUnpaid(liaMiNoticeUnpaid);
			if(result != null && !"PUB120".equals(result.getMessage().getOpCode())) {
				logger.error("batchGenMobileInsSmsUnpaid Update LiaMiNoticeUnpaid fail：Serialno = " + liaMiNoticeUnpaid.getSerialno());
				throw new Exception("batchGenMobileInsSmsUnpaid Update LiaMiNoticeUnpaid fail");
			}
			
			MobileInsSms mobileInsSms = this.mappingMobileInsSms(liaMiNoticeUnpaid.getSerialno(), terminationNotice, pdfFilePath);
			Result temp = this.mobileInsSmsService.insertMobileInsSms(mobileInsSms);
			if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
				logger.error("batchGenMobileInsSmsUnpaid Insert MobileInsSms fail：" + liaMiNoticeUnpaid.getSerialno());
				throw new Exception("batchGenMobileInsSmsUnpaid Insert MobileInsSms fail");
			}

			result.setResObject(liaMiNoticeUnpaid);
			Message message = new Message();
			message.setMessageString("success");
			result.setMessage(message);
		} else {
			logger.error("batchGenMobileInsSmsUnpaid create PDF fail：" + liaMiNoticeUnpaid.getSerialno());
			throw new Exception("batchGenMobileInsSmsUnpaid create PDF fail");
		}
		return result;
	}
	
	private LiaMiNoticeUnpaid mappingLiaMiNoticeUnpaid(TerminationNotice terminationNotice) {
		LiaMiNoticeUnpaid entity = new LiaMiNoticeUnpaid();
		try {
			entity.setSerialno(FetTransactionIdGenUtil.getRandom(20));
			entity.setPolicyno(terminationNotice.getPolicyNo());
			entity.setInsuredname(terminationNotice.getInsuredName());
			entity.setPhonenumber(terminationNotice.getPhoneNumber());
			entity.setUnpaidDate(terminationNotice.getServiceTerminationDate());
			entity.setPdftime(null);
			entity.setInputdate(new Date());
			
		} catch (Exception e) {
			logger.error("XchgTerminationNoticeService mappingLiaMiNoticeCancle error", e);
		}
		return entity;
	}
	
	private LiaMiNoticeCancle mappingLiaMiNoticeCancle(TerminationNotice terminationNotice) {
		LiaMiNoticeCancle entity = new LiaMiNoticeCancle();
		try {
			entity.setSerialno(terminationNotice.getSerialNo());
			entity.setPolicyno(terminationNotice.getPolicyNo());
			entity.setInsuredname(terminationNotice.getInsuredName());
			entity.setPhonenumber(terminationNotice.getPhoneNumber());
			entity.setServiceTerminationDate(terminationNotice.getServiceTerminationDate());
			entity.setPdftime(null);
			entity.setInputdate(new Date());
			
		} catch (Exception e) {
			logger.error("XchgTerminationNoticeService mappingLiaMiNoticeCancle error", e);
		}
		return entity;
	}
	
	private MobileInsSms mappingMobileInsSms(String oid, TerminationNotice terminationNotice, String pdfFilePath) {
		MobileInsSms entity = new MobileInsSms();
		try {
			entity.setOid(oid);
			entity.setRiskcname("行動裝置險");
			entity.setMobile(terminationNotice.getPhoneNumber());
			entity.setEmail(null);
			entity.setClosedate(null);
			entity.setPdfUrl(pdfFilePath);
			entity.setSmsdate(null);
			entity.setEmailDate(null);
			String guid = FetTransactionIdGenUtil.getRandom(7);
			String guid64 = Base64.encodeBase64String(guid.getBytes());
			entity.setGuid(guid);
			entity.setGuid64(guid64);
			String shortPath = this.genShortUrl(guid64);
			entity.setShortpath(shortPath);
			entity.setIcreate("system");
			entity.setDcreate(new Date());
			
		} catch (Exception e) {
			logger.error("XchgTerminationNoticeService mappingMobileInsSms(LiaMiNoticeCancle) error", e);
		}
		return entity;
	}
	
	public String genLia001Pdf(String serialno, String policyNo, Date createTime, String strPdfGenUrl, String pdfFolder){
		String filePath = "";
		if(serialno == null || serialno.length() <= 0) {
			return "";
		}
//		String pdfGenUrl = "http://192.168.112.6:8880/RPT/webService/pdf/lia00102/" + serialno;
		String pdfGenUrl = strPdfGenUrl + serialno;
		logger.info("genPdf serialno : " + serialno);

	    try{
	    	URL url = new URL(pdfGenUrl);
	    	HttpURLConnection conn= (HttpURLConnection) url.openConnection();    
	    	conn.setRequestMethod( "GET" );
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	conn.setRequestProperty("Accept", "application/json");

			int responseCode = conn.getResponseCode();
			logger.info("call genShortUrl Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				try(BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					logger.info(response.toString());
					Gson g = new Gson();  
					RptResultBaseVo vo = g.fromJson(response.toString(), RptResultBaseVo.class);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//					String folder = "E:/mobile_ins/LIA_MI_NOTICE_CANCLE/" + sdf.format(createTime);
					String folder = pdfFolder + sdf.format(createTime);
					File folderFile = new File(folder);
					if(!folderFile.exists()) {
						folderFile.mkdirs();
					}
					if(vo != null){
						logger.info("vo.getMsg() = " + vo.getMsg());
						byte[] byteArray = Base64.decodeBase64(vo.getRptStr());
						filePath = folder+"/"+policyNo + ".pdf";
						FileUtils.writeByteArrayToFile(new File(filePath), byteArray);
						logger.info("create pdf file successfully : " + filePath);
					}
					
				}
			} else {
				logger.info("genPdf request did not work.");
				return "";
			}
	    	
	    }catch(Exception e){
	    	logger.error("genPdf exception :: ", e);
	    	return "";
	    }

		return filePath;
	}
	
	private String genShortUrl(String guidBase64){
		String shortUrl = "";
		String epolicySmsDownloadUrl = configUtil.getString("epolicySmsDownloadUrl");
		String epolicyFtsGenUrl = configUtil.getString("epolicyFtsGenUrl");
		String pdfDownloadUrl = epolicySmsDownloadUrl + guidBase64;
		logger.info("genShortUrl guidBase64 : " + guidBase64 + ", pdfDownloadUrl : "+ pdfDownloadUrl);

	    try{
	    	String urlParameters = "{\"url\": \""+ pdfDownloadUrl +"\"}";
	    	byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
	    	URL url = new URL(epolicyFtsGenUrl);
	    	HttpURLConnection conn= (HttpURLConnection) url.openConnection();    
	    	conn.setRequestMethod( "POST" );
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	conn.setRequestProperty("Accept", "application/json");
	    	conn.setDoOutput( true );
	    	try(OutputStream os = conn.getOutputStream()) {
	    	    os.write(postData, 0, postData.length);			
	    	}
			
			int responseCode = conn.getResponseCode();
			logger.info("call genShortUrl Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				try(BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					logger.info(response.toString());
					Gson g = new Gson();  
					ShortUrlVo s = g.fromJson(response.toString(), ShortUrlVo.class);
					if(s != null){
						shortUrl = "https://ctbcins.com/" + s.getCode();
					}
					logger.info("shortUrl : " + shortUrl);
				}
			} else {
				logger.info("genShortUrl request did not work.");
			}
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

		return shortUrl;
	}

	public LiaMiNoticeCancleService getLiaMiNoticeCancleService() {
		return liaMiNoticeCancleService;
	}

	public void setLiaMiNoticeCancleService(LiaMiNoticeCancleService liaMiNoticeCancleService) {
		this.liaMiNoticeCancleService = liaMiNoticeCancleService;
	}

	public LiaMiNoticeUnpaidService getLiaMiNoticeUnpaidService() {
		return liaMiNoticeUnpaidService;
	}

	public void setLiaMiNoticeUnpaidService(LiaMiNoticeUnpaidService liaMiNoticeUnpaidService) {
		this.liaMiNoticeUnpaidService = liaMiNoticeUnpaidService;
	}

	public MobileInsSmsService getMobileInsSmsService() {
		return mobileInsSmsService;
	}

	public void setMobileInsSmsService(MobileInsSmsService mobileInsSmsService) {
		this.mobileInsSmsService = mobileInsSmsService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

}
