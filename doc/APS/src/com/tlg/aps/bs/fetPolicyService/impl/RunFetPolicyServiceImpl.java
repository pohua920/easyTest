package com.tlg.aps.bs.fetPolicyService.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.net.ssl.SSLContext;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.bs.fetPolicyService.XchgTerminationNoticeService;
import com.tlg.aps.util.AESEcbUtil;
import com.tlg.aps.util.FetTransactionIdGenUtil;
import com.tlg.aps.util.FileUtil;
import com.tlg.aps.vo.AmlInsuredListVo;
import com.tlg.aps.vo.AmlInsuredVo;
import com.tlg.aps.vo.AmlResponseVo;
import com.tlg.aps.vo.BatchRepairVo;
import com.tlg.aps.vo.BlacklistReqVo;
import com.tlg.aps.vo.BlacklistRespVo;
import com.tlg.aps.vo.PrpinsAgentReqVo;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.vo.ReturnPolicyNoToG10Vo;
import com.tlg.aps.vo.SmsVo;
import com.tlg.aps.vo.StakeHolderVo;
import com.tlg.aps.vo.mob.fetPolicy.TerminationNoticeVo;
import com.tlg.aps.vo.mob.fetPolicy.request.AuthInfoVo;
import com.tlg.aps.vo.mob.fetPolicy.request.ConditionVo;
import com.tlg.aps.vo.mob.fetPolicy.request.FetPolicyRequestVo;
import com.tlg.aps.vo.mob.fetPolicy.request.PolicyDataRequestToG10CustomerVo;
import com.tlg.aps.vo.mob.fetPolicy.request.PolicyDataRequestToG10Vo;
import com.tlg.aps.vo.mob.fetPolicy.request.ReqContentVo;
import com.tlg.aps.vo.mob.fetPolicy.response.CustomerVo;
import com.tlg.aps.vo.mob.fetPolicy.response.FetPolicyResponseVo;
import com.tlg.aps.vo.mob.fetPolicy.response.G10ResponseVo;
import com.tlg.aps.vo.mob.fetPolicy.response.Head;
import com.tlg.aps.vo.mob.fetPolicy.response.MdiDailyReport;
import com.tlg.aps.vo.mob.fetPolicy.response.ResContentVo;
import com.tlg.aps.webService.blasklistQueryService.client.BlacklistQueryService;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.aps.webService.corePolicyService.client.BusinessSalesRequestVo;
import com.tlg.aps.webService.corePolicyService.client.BusinessSalesResultVo;
import com.tlg.aps.webService.corePolicyService.client.CheckPolicyService;
import com.tlg.aps.webService.metaAmlService.client.AmlService;
import com.tlg.aps.webService.prpinsAgentService.client.PrpinsAgentService;
import com.tlg.aps.webService.stakeHolderService.client.StakeHolderService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.AcFile;
import com.tlg.msSqlMob.entity.AccountFile;
import com.tlg.msSqlMob.entity.Applicant;
import com.tlg.msSqlMob.entity.ApplicantEndorse;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.entity.ChFile;
import com.tlg.msSqlMob.entity.ChubbApplicantEndorse;
import com.tlg.msSqlMob.entity.ChubbCustomerEndorse;
import com.tlg.msSqlMob.entity.ChubbInsuredEndorse;
import com.tlg.msSqlMob.entity.ChubbProductEndorse;
import com.tlg.msSqlMob.entity.ChubbReturnCustomer;
import com.tlg.msSqlMob.entity.ClFile;
import com.tlg.msSqlMob.entity.CmFile;
import com.tlg.msSqlMob.entity.Customer;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.msSqlMob.entity.FetAmlRecord;
import com.tlg.msSqlMob.entity.FetCancelNotification;
import com.tlg.msSqlMob.entity.FetMobileEpolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicySales;
import com.tlg.msSqlMob.entity.FetPaid;
import com.tlg.msSqlMob.entity.FetPayable;
import com.tlg.msSqlMob.entity.InsuredEndorse;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;
import com.tlg.msSqlMob.entity.ProductEndorse;
import com.tlg.msSqlMob.entity.ProposalInsufficientSms;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.msSqlMob.service.AcFileService;
import com.tlg.msSqlMob.service.ApplicantService;
import com.tlg.msSqlMob.service.ApplicationService;
import com.tlg.msSqlMob.service.ChFileService;
import com.tlg.msSqlMob.service.ChubbCustomerEndorseService;
import com.tlg.msSqlMob.service.ChubbReturnCustomerService;
import com.tlg.msSqlMob.service.ClFileService;
import com.tlg.msSqlMob.service.CmFileService;
import com.tlg.msSqlMob.service.CustomerEndorseService;
import com.tlg.msSqlMob.service.CustomerService;
import com.tlg.msSqlMob.service.FetAmlRecordService;
import com.tlg.msSqlMob.service.FetCancelNotificationService;
import com.tlg.msSqlMob.service.FetMobileEpolicyService;
import com.tlg.msSqlMob.service.FetMobilePolicySalesService;
import com.tlg.msSqlMob.service.FetPaidService;
import com.tlg.msSqlMob.service.FetPayableService;
import com.tlg.msSqlMob.service.MobileInsBatchInfoService;
import com.tlg.msSqlMob.service.ProposalFileService;
import com.tlg.msSqlMob.service.ProposalInsufficientSmsService;
import com.tlg.msSqlMob.service.TerminationNoticeService;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.entity.Prpcopycommission;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.PrpcopycommissionService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GIGO;
import com.tlg.util.HttpsUrlUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.SmsUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.WebserviceObjConvert;
import com.tlg.util.ZipUtil;
import com.tlg.xchg.entity.LiaMiNoticeCancle;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;
import com.tlg.xchg.service.LiaMiNoticeCancleService;
import com.tlg.xchg.service.LiaMiNoticeUnpaidService;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@Transactional(value="msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RunFetPolicyServiceImpl implements RunFetPolicyService {
	
	private static final Logger logger = Logger.getLogger(RunFetPolicyServiceImpl.class);
	private ConfigUtil configUtil;
	private FetPolicyService fetPolicyService;
	private CustomerService customerService;
	private MobileInsBatchInfoService mobileInsBatchInfoService;
	private ApplicantService applicantService;
	private AmlService clientAmlQueryService;
	private BlacklistQueryService clientBlacklistQueryService;
	private StakeHolderService clientStakeHolderService; 
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 start */
	private ChubbReturnCustomerService chubbReturnCustomerService;
	private ApplicationService applicationService;
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 end */
	/** mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
	private CustomerEndorseService customerEndorseService; 
	/** mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業新 end */
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	private AcFileService acFileService;
	private ChFileService chFileService;
	private ClFileService clFileService;
	private CmFileService cmFileService;
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	private FetMobileEpolicyService fetMobileEpolicyService;
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 START*/
	private TerminationNoticeService terminationNoticeService;
	private XchgTerminationNoticeService xchgTerminationNoticeService;
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 END*/
	
	private ProposalFileService proposalFileService;
	
	private PrpcmainService prpcmainService;
	private PrpcopycommissionService prpcopycommissionService;
	
	private LiaMiNoticeCancleService liaMiNoticeCancleService;
	private LiaMiNoticeUnpaidService liaMiNoticeUnpaidService;
	
	private FetPaidService fetPaidService;
	private FetPayableService fetPayableService;
	
	private ChubbCustomerEndorseService chubbCustomerEndorseService;
	
	private ProposalInsufficientSmsService proposalInsufficientSmsService;
	private FetCancelNotificationService fetCancelNotificationService;
	
	private CheckPolicyService clientCheckPolicyService;
	
	private FetMobilePolicySalesService fetMobilePolicySalesService;
	
	private FetAmlRecordService fetAmlRecordService;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 START */
	private PrpinsAgentService clientPrpinsAgentService;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END */
	
	@Override
	public Result getDailyInsuranceDataFromFet(String rptBatchNo) throws SystemException, Exception {
		Gson gson = new Gson();
		Date executeTime = new Date();
		int checkCount = 1;
		int totalCount = 0;
		int totalRecord = 0; //content內總比數
		int currentRecordCount = 0; //已寫入資料庫的資料筆數
		String msg = "";
		
		//儲存批次執行記錄檔[MOBILE_INS_BATCH_INFO]
		MobileInsBatchInfo mobileInsBatchInfo = fetPolicyService.insertMobileInsBatchInfo(executeTime);
		
		try {
			for(int count=1;count<=checkCount;count++) {
				//組成Request
				String requestJosn = genRequest(rptBatchNo, count);
				logger.info("request = "+requestJosn);
				
				//呼叫WebService
				HttpsUrlUtil httpsUrlUtil = new HttpsUrlUtil();
				String url = configUtil.getString("fetApiUrl"); 
				String resJson = httpsUrlUtil.doConnectionPost(url, requestJosn);
				
//				CloseableHttpClient httpClient = null;
//				try {
//					int timeout = 300; //秒
//					RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).build();
////					httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//					httpClient = this.createTlsV2HttpClient();
//					HttpPost httpPost = new HttpPost(url);
//					StringEntity stringEntity = new StringEntity(requestJosn, "UTF-8");
//					stringEntity.setContentEncoding("UTF-8");
//					httpPost.setEntity(stringEntity);
//					httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
//					httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON);
//					
//					HttpResponse httpResponse = httpClient.execute(httpPost);
//					logger.info("httpResponse.getStatusLine() = " + httpResponse.getStatusLine());
//					int statusCode = httpResponse.getStatusLine().getStatusCode(); 
//					if(statusCode != 200){
//						throw new Exception("連線至webservice發生問題，status code = " + statusCode);
//					}
//					HttpEntity entity = httpResponse.getEntity();
//					ContentType contentType = ContentType.getOrDefault(entity);
//					
//					if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
//						resJson = EntityUtils.toString(entity, "UTF-8");
//						logger.info("response = "+resJson);
//					}
//				} catch (Exception e) {
//					logger.error("Exceptions happen!", e);
//					throw e;
//				}finally{
//					if(httpClient != null){
//						httpClient.close();
//						httpClient = null;
//					}
//				}
				
				//response解析
				if(resJson.equals("")) {
					logger.error("response json為空");
					this.sendEmail(mobileInsBatchInfo, currentRecordCount, "response json為空","import");
					continue;
				}
				
				FetPolicyResponseVo res = gson.fromJson(resJson, FetPolicyResponseVo.class);
				logger.info(res.toString());
				MdiDailyReport report = res.getMdiDailyReport();
				if(report != null) {
					Head head = report.getHead();
					totalCount = Integer.valueOf(head.getTotalCount());
					if(checkCount < totalCount) checkCount = totalCount;
					totalRecord = Integer.valueOf(head.getTotalRecord());
					
					//取得content解析資料並存入DB
					String content = report.getContent();
					logger.info("content = "+content);
					//content AES解碼
					AESEcbUtil aesUtil = new AESEcbUtil();
					content = aesUtil.decrypt(content,"utf-8");
					logger.info("content decrypt= "+content);
					
					ResContentVo contentVo = gson.fromJson(content, ResContentVo.class);
					List<CustomerVo> customersVos = contentVo.getCustomer();
					/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題  START*/
					/**針對同一批的同個保單出現2筆cancel情況，更新如下：
						1筆application_id為99999999，即指透過遠傳系統自動執行非用戶主動提出之異動，不會產出批改申請書，此筆請忽略，不須處理 
						1筆application_id為非99999999，即指透過遠傳系統執行用戶主動提出之異動，帶有批改申請書，此筆請作後續異動處理 
					 * */
					List<CustomerVo> cancelList = new ArrayList<CustomerVo>();
					for(CustomerVo customer:customersVos) {
						if("CANCEL".equals(customer.getTransactionType()) && !"99999999".equals(customer.getApplication().getApplicationId())) {
							cancelList.add(customer);
						}
					}
					for(CustomerVo customersVo:customersVos) {
						try {
							fetPolicyService.insertData(customersVo, cancelList, EnumMobileDataSrc.FEPIA);
							/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題  END*/
							currentRecordCount++;
						} catch (Exception e) {
							logger.error(e.getMessage());
							fetPolicyService.insertFetPolicyImportError(customersVo, EnumMobileDataSrc.FEPIA);
						}
					}
				}
			}
			
			Boolean recordCheck = true;
			String batchStatus = "01";
			if(totalRecord == 0) {
				batchStatus = "0X";
			}
			if(currentRecordCount != totalRecord) {
				recordCheck = false;
				msg = "匯入資料筆數與遠傳提供的筆數不相同";
//				batchStatus = "00";// marked by ce035 MOB0026
			}
			
			//更新[MOBILE_INS_BATCH_INFO]
			mobileInsBatchInfo.setTotalCount(String.valueOf(totalCount));
			mobileInsBatchInfo.setTotalRecord(String.valueOf(totalRecord));
			mobileInsBatchInfo.setBatchStatus(batchStatus);
			mobileInsBatchInfo.setBatchCheckStatus(recordCheck?"Y":"N");
			mobileInsBatchInfo.setCurrentRecord(String.valueOf(currentRecordCount));
			mobileInsBatchInfo.setRptBatchNo(rptBatchNo);
			mobileInsBatchInfo.setDataSrc(EnumMobileDataSrc.FEPIA.getCode());
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
			fetPolicyService.updateMobileInsBatchInfo(mobileInsBatchInfo);
			
			if(!recordCheck) {
				this.sendEmail(mobileInsBatchInfo, currentRecordCount, msg,"import");
			}
			
			return this.getReturnResult("執行成功");
		
		} catch (SystemException se) {
			msg = se.getMessage();
			this.sendEmail(mobileInsBatchInfo, currentRecordCount, msg,"import");
			throw se;
		} catch (Exception e) {
			msg = e.getMessage();
			this.sendEmail(mobileInsBatchInfo, currentRecordCount, msg,"import");
			throw e;
		}
	}
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
	@SuppressWarnings("unchecked")
	@Override
	public Result checkFetInsuranceData() throws SystemException, Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchStatus", "01");
		params.put("batchCheckStatus", "Y");
		Result infoResult = mobileInsBatchInfoService.findMobileInsBatchInfoByParams(params);
		if(infoResult.getResObject() == null) {
			return this.getReturnResult("本次無需檢核之批次號資料");
		}
		List<MobileInsBatchInfo> list = (List<MobileInsBatchInfo>) infoResult.getResObject();
		for(MobileInsBatchInfo info:list) {
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 調整檢核寫法 START*/
			String blacklistCheck = "0";
			String stakeHolderCheck = "0";
			String amlCheck = "0";
			String businessSalesCheck = "0";
			params.clear();
			params.put("rptBatchNo", info.getRptBatchNo());
			params.put("batchStatus", "01");
			Result applicantResult = applicantService.selectForFetPolicyCheck(params);
			if(applicantResult.getResObject() == null) {
				logger.info("checkFetInsuranceData applicant.rptBatchNo = "+info.getRptBatchNo()+" 無資料");
				continue;
			}
			List<Applicant> applicantList = (List<Applicant>) applicantResult.getResObject();
			for(Applicant applicant:applicantList) {
				Result customerResult = customerService.findCustomerByUK(applicant.getTransactionId());
				Result applicationResult = applicationService.findApplicationByUK(applicant.getTransactionId());
				if(null != customerResult.getResObject()) {
					Customer customer = (Customer) customerResult.getResObject();
					/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
					String dataSrc = customer.getDataSrc();// 資料來源
					if(StringUtils.isBlank(applicant.getCustomerId())) {
						customer.setCtbcStockholderStatus("X");
						customer.setBatchStatus("X");
						customer.setCtbcAmlStatus("X");
					}else {
						try {
							//blacklist
							blacklistCheck = callBlacklist(applicant.getCustomerId()); /** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
							customer.setCtbcBlacklistStatus(blacklistCheck);
							logger.info("checkFetInsuranceData blacklistCheck ="+blacklistCheck);
							//stakeHolder
							stakeHolderCheck = callStakeHolder(applicant.getTransactionId(), applicant.getCustomerId()); /** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
							customer.setCtbcStockholderStatus(stakeHolderCheck);
							logger.info("checkFetInsuranceData stakeHolderCheck ="+stakeHolderCheck);
							//aml
							/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表  START*/
							Map<String, String> amlparams = new HashMap<String, String>();
							amlparams.put("contractId", applicant.getContractId());
							amlparams.put("name", applicant.getName());
							amlparams.put("customerId", applicant.getCustomerId());
							amlparams.put("orderByCreatedtimeDesc", "Y");
							Result amlResult = fetAmlRecordService.findFetAmlRecordByParams(amlparams);
							if(amlResult.getResObject() == null) {// 如果之前沒查詢過該筆保單則將此次查詢AML的條件存到FET_AML_RECORD資料表
								amlCheck = callAml(applicant.getContractId(), applicant.getCustomerId(), applicant.getName(), applicant.getBirthday()); /** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
								if("0".equals(amlCheck) || "1".equals(amlCheck)) {
									FetAmlRecord fetAmlRecord = new FetAmlRecord();
									fetAmlRecord.setTransactionId(applicant.getTransactionId());
									fetAmlRecord.setContractId(applicant.getContractId());
									fetAmlRecord.setName(applicant.getName());
									fetAmlRecord.setCustomerId(applicant.getCustomerId());
									fetAmlRecord.setBirthday(applicant.getBirthday());
									fetAmlRecord.setCtbcAmlStatus(amlCheck);
									fetAmlRecord.setDataSrc(dataSrc);// CE035:標明資料來源
									fetAmlRecordService.insertFetAmlRecord(fetAmlRecord);
								}
							} else {// 取之前進洗錢系統的檢核結果
								ArrayList<FetAmlRecord> fetAmlRecordList = (ArrayList<FetAmlRecord>)amlResult.getResObject();
								FetAmlRecord fetAmlRecord = fetAmlRecordList.get(0);
								amlCheck = fetAmlRecord.getCtbcAmlStatus();
							}
							/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表  END*/
							customer.setCtbcAmlStatus(amlCheck);
							logger.info("checkFetInsuranceData amlCheck ="+amlCheck);
							
							//20240515：BJ016：業務員檢核----START
							//業務員檢核結果要與黑名單檢核結果欄位共用欄位，目前0表未命中，1表命中黑名單，2表業務員檢核失敗，3表命中黑名單且業務員檢核失敗
							if (applicationResult != null && applicationResult.getResObject() != null
									&& EnumMobileDataSrc.FEPIA.getCode().equals(dataSrc)) {// 資料來源是遠傳保代才須業務員檢核 START
								/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
								Application application = (Application)applicationResult.getResObject();
								BusinessSalesResultVo businessSalesResultVo = this.callCheckBusinessSales(application.getSalesId());
								if(businessSalesResultVo != null) {
									//業務員檢核結果要與黑名單檢核結果欄位共用欄位，目前0表未命中，1表命中黑名單，2表業務員檢核失敗，3表命中黑名單且業務員檢核失敗
									if("0".equals(businessSalesResultVo.getReturnCode())) {
										 if("1".equals(blacklistCheck)) {
											 businessSalesCheck = "1";
										 } 
										 FetMobilePolicySales fetMobilePolicySales = this.mappingFetMobilePolicySales(application.getTransactionId(), businessSalesResultVo);
										 if(this.fetMobilePolicySalesService.isUnique(fetMobilePolicySales)) {
											 this.fetMobilePolicySalesService.insertFetMobilePolicySales(fetMobilePolicySales);
										 } else {
											 fetMobilePolicySales.setModifiedBy("system");
											 fetMobilePolicySales.setModifiedTime(new Date());
											 this.fetMobilePolicySalesService.updateFetMobilePolicySales(fetMobilePolicySales);
										 }
									} else if("1".equals(blacklistCheck)) {
										businessSalesCheck = "3";
									} else {
										businessSalesCheck = "2";
									}
								} else {
									businessSalesCheck = "X";
								}
								if(businessSalesCheck.trim().length() <= 0) businessSalesCheck = "X";
								customer.setCtbcBlacklistStatus(businessSalesCheck);
								//20240515：BJ016：業務員檢核----END
							}// 資料來源是遠傳保代才須業務員檢核 END
							/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 調整檢核寫法 END*/
							customer.setBatchStatus("02");
							if("X".equals(blacklistCheck) || "X".equals(stakeHolderCheck)) {/** mantis：MOB0025，處理人員：CE035，因AML系統針對公司戶資料回傳X會導致整批資料無法取得保單號，刪除amlCheck判斷條件 */
								customer.setBatchStatus("01");
							}
						} catch (Exception e) {
							logger.error(e.getMessage());
							customer.setBatchStatus("01");
						}
					}
					
					try {
						customer.setModifiedBy("system");
						customer.setModifiedTime(new Date());
						fetPolicyService.updateCustomer(customer);
					} catch (Exception e) {
						logger.error(e.getMessage());
						continue;
					}
				}else {
					logger.info("checkFetInsuranceData customer.TransactionId = "+applicant.getTransactionId()+" 無資料");
				}
			}
			
			Result result = customerService.findCustomerByParams(params);
			if(result.getResObject() == null) {
				info.setBatchStatus("02");
			}else {
				info.setBatchStatus("01");
				sendEmail(info, 0, null, "check");
			}
			info.setModifiedBy("system");
			info.setModifiedTime(new Date());
			mobileInsBatchInfoService.updateMobileInsBatchInfo(info);
		}
		return this.getReturnResult("遠傳要保及批單資料作洗錢、利關人、黑名單檢核作業成功");
	}
	
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 start */
	@Override
	public Result getProposalFileFromFet() throws SystemException, Exception {
		String msg = "";
		
		SftpUtil sftpUtil = this.getFetSftpUtil();
		String remotePath = configUtil.getString("mobFetSFTPUploadPath") + "/FET";
		List<String> sftpFileList = sftpUtil.getFileListFromSftp(remotePath);
		List<String> downloadFileList = new ArrayList<String>();
		if(sftpFileList == null || sftpFileList.size() <= 0) {
			logger.info("getProposalFileFromFet : 無任何要保書檔案");
			return this.getReturnResult("無任何檔案");
		} else {
			Map params = new HashMap();
			for(String filename : sftpFileList) {
				params.clear();
				if(!".".equals(filename) && !"..".equals(filename)) {
					params.put("zipFileName", filename);
					int zipFileCheckCount = proposalFileService.countProposalFile(params);
					if(zipFileCheckCount == 0) {
						downloadFileList.add(filename);
					}
				}
			}
		}
		
		if(downloadFileList == null || downloadFileList.size() <= 0) {
			logger.info("getProposalFileFromFet : 無任何要保書檔案");
			return this.getReturnResult("無任何檔案");
		}
		
		String strFolder = configUtil.getString("mobRootDirectory") + "/PROPOSAL";
		File folder = new File(strFolder);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		sftpUtil.getFileFromSftp(remotePath, strFolder, downloadFileList);
		
		//將解壓縮後的檔案資料儲存
		List<String> fileList = FileUtil.getAllFileName(strFolder);
		ZipUtil zipUtil = new ZipUtil();
		String pwd = "";
		List<String> zipFileContentList;
		String unzipDestination = "";
		String fileSource = "";
		for(String filename : fileList) {
			fileSource = strFolder + "/" + filename;
			try {
				
				File zipFile = new File(fileSource);
				zipFileContentList = null;
				pwd = "";
				unzipDestination = "";
				if(!zipFile.isDirectory()) {
					if(zipFile.exists() && zipFile.getName().contains(".zip")) {
						//上傳sftp
						boolean result = uploadZipFileToSftp(strFolder + "/" + filename);
						if(!result) {
							throw new Exception("上傳SFTP失敗");
						}
						//解壓縮檔案
						pwd = filename.replaceAll(".zip", "");
						unzipDestination = strFolder+"/unzip/" + pwd;
						zipUtil.unzip(fileSource, unzipDestination, pwd);
						zipFileContentList = FileUtil.getAllFileName(unzipDestination);
						
						//資料儲存
						if(zipFileContentList != null && zipFileContentList.size() > 0) {
							fetPolicyService.insertFileData(zipFileContentList,zipFile);
						}
						
						//將成功上傳的檔案搬到backup資料夾中
						FileUtil.move(fileSource, strFolder + "/backup/" + filename);
						
						//刪除SFTP上已經下載過的檔案
//						if("PDF_218_202310230000_001.zip".equals(filename)) {
//							sftpUtil.deleteFileToSftp(remotePath, filename);
//						}
						
					}
				}
				
			}catch (SystemException se) {
				msg = se.getMessage();
				this.sendEmail(null, 0, msg,"download");
				throw se;
			}catch (Exception e) {
				msg = e.getMessage();
				this.sendEmail(null, 0, msg,"download");
				throw e;
			}finally {
				//刪除解壓縮檔案
//				deleteFiles(workDir + File.separator + "proposal");
			}
		}
		return this.getReturnResult("從遠傳抓取要保書檔作業成功");
	}
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	@Override
	public Result downloadChFile() throws SystemException, Exception {
		try {
			List<ChFile> lists = new ArrayList<ChFile>();
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobChFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadChFile : 無任何CH檔案");
				return this.getReturnResult("無任何檔案");
			}
			String chFileFolder = configUtil.getString("mobRootDirectory") + remotePath;
			String chubbFolder = chFileFolder + configUtil.getString("mobChubbFileFolder");
			File folder = new File(chubbFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, chubbFolder, downloadFileList);
			File file = null;
			File ctbcFile = null;
			List<String> listFiles = FileUtil.getAllFileName(chubbFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			ChFile insertEntity = null;
			String line = "";
			for(String fileName : listFiles) {
				
				file = new File(chubbFolder + "\\" + fileName);
				
				if(file.exists()) {
					arrData = new String[60];
					arrTemp = null;
					try (FileInputStream fis = new FileInputStream(file);
						       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
						       BufferedReader reader = new BufferedReader(isr)) {

						String data = "";
						lists.clear();
						while ((data = reader.readLine()) != null) {
							data = data.replace("\uFEFF", "");//刪除UTF8-BOM
							if(data.length() > 0) {
								arrTemp = data.split("\t");
								for(int i = 0; i<arrTemp.length; i++) {
									if(i < arrData.length) {
										arrData[i] = arrTemp[i];
									}else {
										break;
									}
								}
								insertEntity = this.mappingChFile(fileName, arrData);
								lists.add(insertEntity);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					ctbcFile = new File(chFileFolder + "\\" + fileName);
	        		try (FileOutputStream fos = new FileOutputStream(ctbcFile);
	        			       OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
	        			       BufferedWriter writer = new BufferedWriter(osw)) {
	        			if(lists != null && lists.size() > 0) {
	        				fetPolicyService.batchInsertChFile(lists);
	        				for(ChFile chFile : lists) {
		        				line = chFile.getCh001() + "\t" + chFile.getCh002() + "\t" + chFile.getCh003() + "\t" + chFile.getCh004() + "\t" + chFile.getCh005() + "\t" + chFile.getCh006() + "\t" + chFile.getCh007() + "\t" + chFile.getCh008() + "\t" + chFile.getCh009() + "\t" + chFile.getCh010() + "\t" 
		        						+ chFile.getCh011() + "\t" + chFile.getCh012() + "\t" + chFile.getCh013() + "\t" + chFile.getCh014() + "\t" + chFile.getCh015() + "\t" + chFile.getCh016() + "\t" + chFile.getCh017() + "\t" + chFile.getCh018() + "\t" + chFile.getCh019() + "\t" + chFile.getCh020() + "\t" 
		        						+ chFile.getCh021() + "\t" + chFile.getCh022() + "\t" + chFile.getCh023() + "\t" + chFile.getCh024() + "\t" + chFile.getCh025() + "\t" + chFile.getCh026() + "\t" + chFile.getCh027() + "\t" + chFile.getCh028() + "\t" + chFile.getCh029() + "\t" + chFile.getCh030() + "\t" 
		        						+ chFile.getCh031() + "\t" + chFile.getCh032() + "\t" + chFile.getCh033() + "\t" + chFile.getCh034() + "\t" + chFile.getCh035() + "\t" + chFile.getCh036() + "\t" + chFile.getCh037() + "\t" + chFile.getCh038() + "\t" + chFile.getCh039() + "\t" + chFile.getCh040() + "\t" 
		        						+ chFile.getCh041() + "\t" + chFile.getCh042() + "\t" + chFile.getCh043() + "\t" + chFile.getCh044() + "\t" + chFile.getCh045() + "\t" + chFile.getCh046() + "\t" + chFile.getCh047() + "\t" + chFile.getCh048() + "\t" + chFile.getCh049() + "\t" + chFile.getCh050() + "\t" 
		        						+ chFile.getCh051() + "\t" + chFile.getCh052() + "\t" + chFile.getCh053() + "\t" + chFile.getCh054() + "\t" + chFile.getCh055() + "\t" + chFile.getCh056() + "\t" + chFile.getCh057() + "\t" + chFile.getCh058() + "\t" + chFile.getCh059() + "\t" + chFile.getCh060();
		        				writer.append(line + "\r\n");
		        			}
	        			} else {
	        				writer.append("");
	        			}
	        		}
	        		FileUtil.move(chubbFolder + "\\" + fileName, chubbFolder+ "\\backup\\" + fileName);
	        		sftpUtil.deleteFileToSftp(remotePath, fileName);
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadChFile error:", e);
		}
		return null;
	}
	
	@Override
	public Result downloadAcFile(String endorseDate) throws SystemException, Exception {
		try {
			List<AcFile> listsAcFile = new ArrayList<AcFile>();
			List<AccountFile> listsAccountFile = new ArrayList<AccountFile>();
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobAcFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadAcFile : 無任何AC檔案");
				return this.getReturnResult("無任何檔案");
			}
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
			Map<String, CustomerEndorse> ceMap = new HashMap<String, CustomerEndorse>();
			if(StringUtils.isNotBlank(endorseDate)) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("endorseDate", endorseDate);
				Result result = this.customerEndorseService.selectCeToCorrectEndorseNo(params);
				if(result != null && result.getResObject() != null) {
					List<CustomerEndorse> ceList = (List<CustomerEndorse>)result.getResObject();
					if(ceList != null && ceList.size() > 0) {
						for(CustomerEndorse ce : ceList) {
							if(StringUtils.isNotBlank(ce.getPolicyNo()) && StringUtils.isNotBlank(ce.getPayPeriodStartDate())) {
								ceMap.put(ce.getPolicyNo()+ce.getPayPeriodStartDate(), ce);
							}
						}
					}
				}
			}
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
			String acFileFolder = configUtil.getString("mobRootDirectory") + remotePath;
			String chubbFolder = acFileFolder + configUtil.getString("mobChubbFileFolder");
			File folder = new File(acFileFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, chubbFolder, downloadFileList);
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(chubbFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			AcFile insertEntityAcFile = null;
			AccountFile insertEntityAccountFile = null;
			for(String fileName : listFiles) {
				
				file = new File(chubbFolder + "\\" + fileName);
				
				if(file.exists()) {
					if(fileName.indexOf("txt") >= 0) {
						arrData = new String[29];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							listsAcFile.clear();
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split("\t");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityAcFile = this.mappingAcFile(fileName, arrData);
									/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  ac檔補批單號 START*/
									if (StringUtils.isBlank(insertEntityAcFile.getAc006()) && !ceMap.isEmpty() && StringUtils.isNotBlank(endorseDate)
											&& StringUtils.isNotBlank(insertEntityAcFile.getAc005()) && StringUtils.isNotBlank(insertEntityAcFile.getAc028())) {
										String policyNoPayPeriodStartDate = insertEntityAcFile.getAc005() + insertEntityAcFile.getAc028();
										CustomerEndorse ce = ceMap.get(policyNoPayPeriodStartDate);
										String contractId = insertEntityAcFile.getAc004();
										String policyNo = insertEntityAcFile.getAc005();
										String projectCodePremium = insertEntityAcFile.getAc016();
										String payPeriodStartDate = insertEntityAcFile.getAc028();
										String payPeriodEndDate = insertEntityAcFile.getAc029();
										if(StringUtils.isNotBlank(contractId) && StringUtils.isNotBlank(policyNo) && StringUtils.isNotBlank(projectCodePremium) 
												&& StringUtils.isNotBlank(payPeriodStartDate) && StringUtils.isNotBlank(payPeriodEndDate) 
												&& contractId.equals(ce.getContractId()) && policyNo.equals(ce.getPolicyNo()) 
												&& payPeriodStartDate.equals(ce.getPayPeriodStartDate()) && payPeriodEndDate.equals(ce.getPayPeriodEndDate())
												&& projectCodePremium.equals(ce.getProjectCodePremium())) {
											insertEntityAcFile.setAc006(ce.getEndorseNo());// 補上批單號
										}
									}
									/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
									listsAcFile.add(insertEntityAcFile);
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.error("RunFetPolicyService downloadAcFile error:", e);
						}
					    
						if(listsAcFile != null && listsAcFile.size() > 0) {
				        	boolean check = fetPolicyService.batchInsertAcFile(listsAcFile);
				        	//把Fet_Mobile_Policy資料表中status = HOLD的狀態改為WAIT---START
				        	if(check) {
				        		for(AcFile entity : listsAcFile) {
				        			try {
				        				fetPolicyService.updateFetMobilePolicyStatus(entity.getAc005(), entity.getAc006(), entity.getAc016(),entity.getAc027());
				        			} catch (Exception e) {
				        				continue;
				        			}
				        		}
				        	}
				        	//把Fet_Mobile_Policy資料表中status = HOLD的狀態改為WAIT---END
				        }
					} else if (fileName.indexOf("csv") >= 0) {
						arrData = new String[4];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							listsAccountFile.clear();
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split(",");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityAccountFile = this.mappingAccountFile(fileName, arrData);
									listsAccountFile.add(insertEntityAccountFile);
								}
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					    
						if(listsAccountFile != null && listsAccountFile.size() > 0) {
				        	boolean check = fetPolicyService.batchInsertAccountFile(listsAccountFile);
				        }
						FileUtil.move(chubbFolder + "\\" + fileName, chubbFolder+ "\\ACCOUNTING\\" + fileName);
					}
					sftpUtil.deleteFileToSftp(remotePath, fileName);
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadAcFile error:", e);
		}
		return null;
	}

	@Override
	public Result downloadClFile() throws SystemException, Exception {
		try {
			List<ClFile> lists = new ArrayList<ClFile>();
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobClFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadClFile : 無任何CL檔案");
				return this.getReturnResult("無任何檔案");
			}
			String strFolder = configUtil.getString("mobRootDirectory") + remotePath;
			File folder = new File(strFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, strFolder, downloadFileList);
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(strFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			ClFile insertEntity = null;
			for(String fileName : listFiles) {
				
				file = new File(strFolder + "\\" + fileName);
				
				if(file.exists()) {
					arrData = new String[19];
					arrTemp = null;
					try (FileInputStream fis = new FileInputStream(file);
						       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
						       BufferedReader reader = new BufferedReader(isr)) {

						String data = "";
						lists.clear();
						while ((data = reader.readLine()) != null) {
							data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
							if(data.length() > 0) {
								arrTemp = data.split("\t");
								for(int i = 0; i<arrTemp.length; i++) {
									if(i < arrData.length) {
										arrData[i] = arrTemp[i];
									}else {
										break;
									}
								}
								insertEntity = this.mappingClFile(fileName, arrData);
								lists.add(insertEntity);
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
						logger.error("RunFetPolicyService downloadClFile error:", e);
					}
				    
					if(lists != null && lists.size() > 0) {
			        	fetPolicyService.batchInsertClFile(lists);
			        }
					sftpUtil.deleteFileToSftp(remotePath, fileName);
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadClFile error:", e);
		}
		return null;
	}

	@Override
	public Result downloadCmFile() throws SystemException, Exception {
		try {
			List<CmFile> lists = new ArrayList<CmFile>();
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobCmFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadCmFile : 無任何CM檔案");
				return this.getReturnResult("無任何檔案");
			}
			String cmFileFolder = configUtil.getString("mobRootDirectory") + remotePath;
			String chubbFolder = cmFileFolder + configUtil.getString("mobChubbFileFolder");
			File folder = new File(chubbFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, chubbFolder, downloadFileList);
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(chubbFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			CmFile insertEntity = null;
			for(String fileName : listFiles) {
				
				file = new File(chubbFolder + "\\" + fileName);
				
				if(file.exists()) {
					arrData = new String[37];
					arrTemp = null;
					try (FileInputStream fis = new FileInputStream(file);
						       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
						       BufferedReader reader = new BufferedReader(isr)) {

						String data = "";
						lists.clear();
						while ((data = reader.readLine()) != null) {
							data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
							if(data.length() > 0) {
								arrTemp = data.split("\t");
								for(int i = 0; i<arrTemp.length; i++) {
									if(i < arrData.length) {
										arrData[i] = arrTemp[i];
									}else {
										break;
									}
								}
								insertEntity = this.mappingCmFile(fileName, arrData);
								lists.add(insertEntity);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				    
					if(lists != null && lists.size() > 0) {
			        	fetPolicyService.batchInsertCmFile(lists);
			        }
					sftpUtil.deleteFileToSftp(remotePath, fileName);
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadCmFile error:", e);
		}
		return null;
	}
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	/**mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表  start*/
	@Override
	public Result downloadAccountFile() throws SystemException, Exception {
		try {
			List<AccountFile> lists = new ArrayList<AccountFile>();
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobAcFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadAccountFile : 無任何ACCOUNT檔案");
				return this.getReturnResult("無任何檔案");
			}
			String strFolder = configUtil.getString("mobRootDirectory") + remotePath;
			File folder = new File(strFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, strFolder, downloadFileList);
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(strFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			AccountFile insertEntity = null;
			for(String fileName : listFiles) {
				
				file = new File(strFolder + "\\" + fileName);
				
				if(file.exists()) {
					arrData = new String[4];
					arrTemp = null;
					try (FileInputStream fis = new FileInputStream(file);
						       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
						       BufferedReader reader = new BufferedReader(isr)) {

						String data = "";
						while ((data = reader.readLine()) != null) {
							data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
							arrTemp = data.split("\t");
							for(int i = 0; i<arrTemp.length; i++) {
								if(i < arrData.length) {
									arrData[i] = arrTemp[i];
								}else {
									break;
								}
							}
							insertEntity = this.mappingAccountFile(fileName, arrData);
							lists.add(insertEntity);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				    
					if(lists != null && lists.size() > 0) {
			        	boolean check = fetPolicyService.batchInsertAccountFile(lists);
			        }
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadAccountFile error:", e);
		}
		return null;
	}
	/**mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表  end*/
	
	/**mantis：MOB0009，處理人員：BJ016，需求單編號：MOB0009 傳送回饋檔給遠傳  start*/
	@Override
	public Result offerDailyFeedbackFileToFet() throws SystemException, Exception {
		try {
			
			SftpUtil sftpUtil = this.getFetSftpUtil();
			String strFolder = configUtil.getString("mobRootDirectory");
			String acFilePath = configUtil.getString("mobAcFilePath");
			String chFilePath = configUtil.getString("mobChFilePath");
			String clFilePath = configUtil.getString("mobClFilePath");
			String cmFilePath = configUtil.getString("mobCmFilePath");
			String chubbFolder = configUtil.getString("mobChubbFileFolder");
			String[] arrCheckFolder = {acFilePath,chFilePath,clFilePath,cmFilePath};
			List<String> listFiles = null;
			int dataCount = 0;
			Map<String, Object> params = new HashMap<String, Object>();
			String remoteUploadPath = configUtil.getString("mobFetSFTPUploadPath");
			String uploadResult = "";
			String filePath = "";
			String backupFilePath = "";
			String realFilePath = "";
			Result result;
			for(String checkFolder : arrCheckFolder) {
				if(checkFolder.equals(acFilePath)) {
					this.offerFeedbackAcFileToFet();
				} else {
					if(checkFolder.equals(cmFilePath)) {
						realFilePath = strFolder + checkFolder + chubbFolder;
					} else {
						realFilePath = strFolder + checkFolder;
					}
					listFiles = FileUtil.getAllFileName(realFilePath);
					for(String fileName : listFiles) {
						result = null;
						params.clear();
						params.put("fileName", fileName);
						if(checkFolder.equals(chFilePath)) {
							dataCount = this.chFileService.countChFile(params);
						} else if(checkFolder.equals(clFilePath)) {
							dataCount = this.clFileService.countClFile(params);
						} else if(checkFolder.equals(cmFilePath)) {
							List<CmFile> listExportFile = new ArrayList<CmFile>();
							dataCount = this.cmFileService.countCmFile(params);
							if(dataCount > 0) {
								result = this.cmFileService.findCmFileByParams(params);
								if(result != null && result.getResObject() != null) {
									List<CmFile> searchResult = (List<CmFile>)result.getResObject();
									for(CmFile cmFile : searchResult) {
										Prpcmain prpcmain = this.findPrpcmain(cmFile.getCm005());
										if(prpcmain != null) {
											cmFile.setCm017(prpcmain.getHandlername());
										}
										
										Prpcopycommission prpcopycommission = this.findPrpcopycommission(cmFile.getCm005(),cmFile.getCm006());
										if(prpcopycommission != null) {
											if(prpcopycommission.getCostrate() != null) {
												cmFile.setCm019(prpcopycommission.getCostrate().intValue() + "");
											} else {
												cmFile.setCm019("0");
											}
											
											if(prpcopycommission.getChgcostfee() != null) {
												cmFile.setCm021(prpcopycommission.getChgcostfee().intValue() + "");
											} else {
												if(prpcopycommission.getCostfee() != null) {
													cmFile.setCm021(prpcopycommission.getCostfee().intValue() + "");
												} else {
													cmFile.setCm021("0");
												}
											}
										} else {
											cmFile.setCm019("0");
											cmFile.setCm021("0");
										}
										this.cmFileService.updateCmFile(cmFile);
										listExportFile.add(cmFile);
									}
									if(listExportFile != null && listExportFile.size() > 0 && listExportFile.size() == dataCount) {
										String ctbcFilePath = strFolder + checkFolder;
										File ctbcFile = new File(ctbcFilePath + "\\" + fileName);
										String line = "";
						        		try (FileOutputStream fos = new FileOutputStream(ctbcFile);
						        			       OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
						        			       BufferedWriter writer = new BufferedWriter(osw)) {
						        			for(CmFile cmFile : listExportFile) {
						        				line = StringUtil.nullToSpace(cmFile.getCm001()) + "\t" + StringUtil.nullToSpace(cmFile.getCm002()) + "\t" + StringUtil.nullToSpace(cmFile.getCm003()) + "\t" + StringUtil.nullToSpace(cmFile.getCm004()) + "\t" + StringUtil.nullToSpace(cmFile.getCm005()) + "\t" + StringUtil.nullToSpace(cmFile.getCm006()) + "\t" + StringUtil.nullToSpace(cmFile.getCm007()) + "\t" + StringUtil.nullToSpace(cmFile.getCm008()) + "\t" + StringUtil.nullToSpace(cmFile.getCm009()) + "\t" + StringUtil.nullToSpace(cmFile.getCm010()) + "\t" 
						        						+ StringUtil.nullToSpace(cmFile.getCm011()) + "\t" + StringUtil.nullToSpace(cmFile.getCm012()) + "\t" + StringUtil.nullToSpace(cmFile.getCm013()) + "\t" + StringUtil.nullToSpace(cmFile.getCm014()) + "\t" + StringUtil.nullToSpace(cmFile.getCm015()) + "\t" + StringUtil.nullToSpace(cmFile.getCm016()) + "\t" + StringUtil.nullToSpace(cmFile.getCm017()) + "\t" + StringUtil.nullToSpace(cmFile.getCm018()) + "\t" + StringUtil.nullToSpace(cmFile.getCm019()) + "\t" + StringUtil.nullToSpace(cmFile.getCm020()) + "\t" 
						        						+ StringUtil.nullToSpace(cmFile.getCm021()) + "\t" + StringUtil.nullToSpace(cmFile.getCm022()) + "\t" + StringUtil.nullToSpace(cmFile.getCm023()) + "\t" + StringUtil.nullToSpace(cmFile.getCm024()) + "\t" + StringUtil.nullToSpace(cmFile.getCm025()) + "\t" + StringUtil.nullToSpace(cmFile.getCm026()) + "\t" + StringUtil.nullToSpace(cmFile.getCm027()) + "\t" + StringUtil.nullToSpace(cmFile.getCm028()) + "\t" + StringUtil.nullToSpace(cmFile.getCm029()) + "\t" + StringUtil.nullToSpace(cmFile.getCm030()) + "\t" 
						        						+ StringUtil.nullToSpace(cmFile.getCm031()) + "\t" + StringUtil.nullToSpace(cmFile.getCm032()) + "\t" + StringUtil.nullToSpace(cmFile.getCm033()) + "\t" + StringUtil.nullToSpace(cmFile.getCm034()) + "\t" + StringUtil.nullToSpace(cmFile.getCm035()) + "\t" + StringUtil.nullToSpace(cmFile.getCm036()) + "\t" + StringUtil.nullToSpace(cmFile.getCm037());
						        				writer.append(line + "\r\n");
						        			}
						        		}
									}
								}
							}
							FileUtil.move(realFilePath+ "\\" + fileName, realFilePath+ "\\backup\\" + fileName);
						}
						
						filePath = strFolder + checkFolder + "\\" + fileName;
						backupFilePath = strFolder + checkFolder + "\\backup\\" + fileName;
						uploadResult = sftpUtil.putFileToSftp2(remoteUploadPath, filePath);
						if("success".equals(uploadResult)) {
							FileUtil.move(filePath, backupFilePath);
						}
					}
				}
			}

			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService offerDailyFeedbackFileToFet error:", e);
		}
		return null;
	}
	
	@Override
	public Result offerFeedbackAcFileToFet() throws SystemException, Exception {
		try {
			
			SftpUtil sftpUtil = this.getFetSftpUtil();
			String strFolder = configUtil.getString("mobRootDirectory");
			String acFilePath = configUtil.getString("mobAcFilePath");
			int dataCount = 0;
			String remoteUploadPath = configUtil.getString("mobFetSFTPUploadPath");
			String uploadResult = "";
			String filePath = "";
			String backupFilePath = "";
			Result result;
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "00");
			List<AcFile> listExportFile = new ArrayList<AcFile>();
			dataCount = this.acFileService.countAcFile(params);
			String ctbcFilePath = strFolder + acFilePath;
			if(dataCount > 0) {
				result = this.acFileService.findAcFileByParams(params);
				if(result != null && result.getResObject() != null) {
					List<AcFile> searchResult = (List<AcFile>)result.getResObject();
					for(AcFile acFile : searchResult) {
						Prpcopycommission prpcopycommission = this.findPrpcopycommission(acFile.getAc005(), acFile.getAc006());
						if(prpcopycommission != null) {
							if(prpcopycommission.getChgcostfee() != null) {
								acFile.setAc018(prpcopycommission.getChgcostfee().intValue() + "");
							} else {
								if(prpcopycommission.getCostfee() != null) {
									acFile.setAc018(prpcopycommission.getCostfee().intValue() + "");
								} else {
									acFile.setAc018("0");
								}
							}
						} else {
							acFile.setAc018("0");
						}
						acFile.setStatus("01");
						/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  紀錄回壓ac資料回壓時間 START*/
						acFile.setModifiedBy("system");
						acFile.setModifiedTime(new Date());
						/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  紀錄回壓ac資料回壓時間 END*/
						this.acFileService.updateAcFile(acFile);
						listExportFile.add(acFile);
					}
				}
			}
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int fileSerialNo = 1;
			String fileName1 = "218AC" + sdf.format(now);
			String finalFileName = fileName1 +  String.format("%03d", fileSerialNo) + ".txt";
			int fileNameCount = 0;
			while(true) {
				params.clear();
				params.put("fileName", finalFileName);
				fileNameCount = this.acFileService.countAcFile(params);
				if(fileNameCount > 0) {
					fileSerialNo += 1;
					finalFileName = fileName1 +  String.format("%03d", fileSerialNo) + ".txt";
				} else {
					break;
				}
			}
			
			File ctbcFile = new File(ctbcFilePath + "\\" + finalFileName);
			try (FileOutputStream fos = new FileOutputStream(ctbcFile);
    			       OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    			       BufferedWriter writer = new BufferedWriter(osw)) {
				if(listExportFile != null && listExportFile.size() > 0 && listExportFile.size() == dataCount) {
					String line = "";
					for(AcFile acFile : listExportFile) {
        				line = StringUtil.nullToSpace(acFile.getAc001()) + "\t" + StringUtil.nullToSpace(acFile.getAc002()) + "\t" + StringUtil.nullToSpace(acFile.getAc003()) + "\t" + StringUtil.nullToSpace(acFile.getAc004()) + "\t" + StringUtil.nullToSpace(acFile.getAc005()) + "\t" + StringUtil.nullToSpace(acFile.getAc006()) + "\t" + StringUtil.nullToSpace(acFile.getAc007()) + "\t" + StringUtil.nullToSpace(acFile.getAc008()) + "\t" + StringUtil.nullToSpace(acFile.getAc009()) + "\t" + StringUtil.nullToSpace(acFile.getAc010()) + "\t" 
        						+ StringUtil.nullToSpace(acFile.getAc011()) + "\t" + StringUtil.nullToSpace(acFile.getAc012()) + "\t" + StringUtil.nullToSpace(acFile.getAc013()) + "\t" + StringUtil.nullToSpace(acFile.getAc014()) + "\t" + StringUtil.nullToSpace(acFile.getAc015()) + "\t" + StringUtil.nullToSpace(acFile.getAc016()) + "\t" + StringUtil.nullToSpace(acFile.getAc017()) + "\t" + StringUtil.nullToSpace(acFile.getAc018()) + "\t" + StringUtil.nullToSpace(acFile.getAc019()) + "\t" + StringUtil.nullToSpace(acFile.getAc020()) + "\t" 
        						+ StringUtil.nullToSpace(acFile.getAc021()) + "\t" + StringUtil.nullToSpace(acFile.getAc022()) + "\t" + StringUtil.nullToSpace(acFile.getAc023()) + "\t" + StringUtil.nullToSpace(acFile.getAc024()) + "\t" + StringUtil.nullToSpace(acFile.getAc025()) + "\t" + StringUtil.nullToSpace(acFile.getAc026()) + "\t" + StringUtil.nullToSpace(acFile.getAc027()) + "\t" + StringUtil.nullToSpace(acFile.getAc028()) + "\t" + StringUtil.nullToSpace(acFile.getAc029());
        				writer.append(line + "\r\n");
        			}
				} else {
					writer.append("");
				}
    		}
			filePath = ctbcFilePath + "\\" + finalFileName;
			backupFilePath = ctbcFilePath + "\\backup\\" + finalFileName;
			uploadResult = sftpUtil.putFileToSftp2(remoteUploadPath, filePath);
			uploadResult = "success";
			if("success".equals(uploadResult)) {
				FileUtil.move(filePath, backupFilePath);
			}

			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService offerFeedbackAcFileToFet error:", e);
		}
		return null;
	}
	/**mantis：MOB0009，處理人員：BJ016，需求單編號：MOB0009 傳送回饋檔給遠傳  start*/
	
	private boolean uploadZipFileToSftp(String filePath) throws Exception{
		boolean result = false;
		String sftpHost = configUtil.getString("mobSFTP");
		String sftpUser = configUtil.getString("mobSftpUserGet");
		String sftpPwd = configUtil.getString("mobSftpPwdGet");
		String sftpPort = configUtil.getString("mobSftpPort");
		//String remoteDir = configUtil.getString("panhsinBackFileRemotePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2("/PROPOSAL", filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}
	
	private void deleteFiles(String path) {
		File deleteFile = new File(path);
		if(deleteFile.isDirectory()){
			File [] fs = deleteFile.listFiles();
			for (int i = 0; i < fs.length; i++) {
				File file = fs[i];
				file.delete();
			}
		}
	}
	
	private List<String> getFileFromWorkDir(String filesDir) throws IOException {
		File workPath = new File(filesDir);
		if (!workPath.exists()) {
			workPath.mkdirs();
		}
		List<String> fileList = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(filesDir))) {
			for (Path file : stream) {
				fileList.add(file.getFileName().toString());
			}
		}
		return fileList;
	}
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 end */

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private String genRequest(String rptBatchNo, int count) throws Exception{
		AuthInfoVo authInfoVo = new AuthInfoVo();
		authInfoVo.setChannelID(configUtil.getString("mobApiChannelID"));
		authInfoVo.setUserID(configUtil.getString("mobApiUserID"));
		authInfoVo.setPassword(configUtil.getString("mobApiPwd"));
		
		ReqContentVo reqContentVo = new ReqContentVo();
		reqContentVo.setRptBatchNo(rptBatchNo);
		reqContentVo.setRptId("DAILY_REPORT01"); //待確認
		reqContentVo.setCount(String.valueOf(count));
		reqContentVo.setUserId(configUtil.getString("mobApiUserID"));
		
		Gson gson = new Gson();
		String contentJson = gson.toJson(reqContentVo); //待確認
		//content AES加碼
		AESEcbUtil aesUtil = new AESEcbUtil();
		ConditionVo conditionVo = new ConditionVo();
		conditionVo.setContent(aesUtil.encrypt(contentJson));
//		conditionVo.setContent("aeoJF08mSqoErn2bcg1E6mBx6etqA6ndXweM9OoMGdl1WE11TTzaG0ChvVKGCdWcau2XjHYZwAbj2+P/etTQAh/M9AYCau4qaT+QGydmDUwnPsybrygll13uyHXPOgWv");
		
		FetPolicyRequestVo requestVo = new FetPolicyRequestVo();
		requestVo.setAuthInfo(authInfoVo);
		requestVo.setCondition(conditionVo);
		return new Gson().toJson(requestVo);
	}
	
	private String sendEmail(MobileInsBatchInfo mobileInsBatchInfo, int currentRecordCount, String msg, String type) {
		Mailer mailer = new Mailer();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			String subject = "";
			String mailTo = "ce035@ctbcins.com";

			StringBuilder sb = new StringBuilder();
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9'>");
			if("import".equals(type)) {
				subject = "行動裝置險-取得遠傳保批單資料發生錯誤";
				sb.append("<td>OID</td>");
				sb.append("<td>BATCH_DATE</td>");
				sb.append("<td>BATCH_STATUS</td>");
				sb.append("<td>BATCH_CHECK_STATUS</td>");
				sb.append("<td>RPT_BATCH_NO</td>");
				sb.append("<td>TOTAL_COUNT</td>");
				sb.append("<td>TOTAL_RECORD</td>");
				sb.append("<td>CURRENT_RECORD</td>");
				sb.append("<td>ERROR_MSG</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td>" + mobileInsBatchInfo.getOid() + "</td>");
				sb.append("<td>" + mobileInsBatchInfo.getBatchDate() + "</td>");
				sb.append("<td>" + mobileInsBatchInfo.getBatchStatus() + "</td>");
				sb.append("<td>N</td>");
				sb.append("<td>" + mobileInsBatchInfo.getRptBatchNo() + "</td>");
				sb.append("<td>" + mobileInsBatchInfo.getTotalCount() + "</td>");
				sb.append("<td>" + mobileInsBatchInfo.getTotalRecord() + "</td>");
				sb.append("<td>" + currentRecordCount + "</td>");
				sb.append("<td>" + msg + "</td>");
				sb.append("</tr>");
			}else if("check".equals(type)) {
				subject = "行動裝置險-行動裝置險核保檢核失敗";
				sb.append("<td>時間</td>");
				sb.append("<td>批次號</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td>" + date + "</td>");
				sb.append("<td>" + mobileInsBatchInfo.getRptBatchNo() + "</td>");
				sb.append("</tr>");
			}
			/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 start */
			else if("download".equals(type)) {
				subject = "行動裝置險-下載要保書檔作業失敗";
				sb.append("<td>時間</td>");
				sb.append("<td>錯誤訊息</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td>" + date + "</td>");
				sb.append("<td>" + msg + "</td>");
				sb.append("</tr>");
			}
			/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 end */
			sb.append("</table>");
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("passbookCallSp sendEmail error", e);
		}
		return "";
	}
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 start */
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
	public String callAml(String businessNo, String customerId, String name, String birthdayDate) throws Exception{
		//名字或生日為空則回傳X
		if(StringUtils.isBlank(name) || StringUtils.isBlank(birthdayDate)) {
			return "X";
		}
		
		AmlInsuredListVo amlInsuredListVo = new AmlInsuredListVo();
		ArrayList<AmlInsuredVo> amlInsuredVoList = new ArrayList<AmlInsuredVo>();
		
		amlInsuredListVo.setAppCode("FET_MOBILE");
		amlInsuredListVo.setBusinessNo(businessNo);
		amlInsuredListVo.setChannelType("20");
		amlInsuredListVo.setClassCode("C");
		amlInsuredListVo.setRiskCode("MI");
		amlInsuredListVo.setComCode("00");
		amlInsuredListVo.setPrem("1");
		amlInsuredListVo.setAmlType("2");
		amlInsuredListVo.setResend("1");
		amlInsuredListVo.setType("Q");
		
		AmlInsuredVo amlInsuredVo = new AmlInsuredVo();
		amlInsuredVo.setSerialNo("1");
		amlInsuredVo.setId(customerId);
		amlInsuredVo.setName(name);
		if(!StringUtil.isSpace(birthdayDate) && birthdayDate.matches("^\\d+$")){
			Date birthday = DateUtils.parseDate(birthdayDate, "yyyyMMdd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			amlInsuredVo.setBirthday(sdf.format(birthday));
		}
		String gender = "";
		if(!StringUtil.isSpace(customerId) && customerId.length() == 10){
			String code = (String) customerId.subSequence(1, 2);
			//性別
			if(code.matches("^[18]+$") || code.matches("^[AC]+$")){
				gender = "M";
			}
			if(code.matches("^[29]+$") || code.matches("^[BD]+$")){
				gender = "F";
			}
		}
		amlInsuredVo.setGender(gender);
		amlInsuredVo.setNationCode("TW");
		if(!StringUtil.isSpace(customerId) && customerId.length() == 8){
			amlInsuredVo.setInsuredType("2");
		}else{
			amlInsuredVo.setInsuredType("1");
		}
		amlInsuredVo.setInsuredFlag("2");
		amlInsuredVo.setDangerOccupation("N");
		amlInsuredVoList.add(amlInsuredVo);
		amlInsuredListVo.setAmlInsuredList(amlInsuredVoList);
		
		try {
			Client proxy = ClientProxy.getClient(this.clientAmlQueryService);
			if(proxy != null){
				HTTPConduit httpConduit = (HTTPConduit) proxy.getConduit();

			  	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			  	httpClientPolicy.setConnectionTimeout(120000); //設定TCP握手時間，若超出這個時間就認為連線超時，預設30秒改為60秒
			  	httpClientPolicy.setReceiveTimeout(120000); //示傳送WebService請求後所等待響應的時間，若超過設定的時間則認為超時，預設是60秒，改為120秒
			  	httpConduit.setClient(httpClientPolicy);
			}
			
			String soapxml = WebserviceObjConvert.convertObjToBase64Str(AmlInsuredListVo.class, amlInsuredListVo);
			String returnValue = clientAmlQueryService.amlQuery(soapxml);
			AmlResponseVo amlResponseVo = (AmlResponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, AmlResponseVo.class);
			String workStatus = amlResponseVo.getWorkStatus();
			if("00".equals(workStatus) || "04".equals(workStatus)) {
				return "0";
			}else {
				return "1";
			}
		} catch (Exception e) {
			logger.error("aml webService錯誤 :"+e.getMessage());
			return "X";
		}
	}
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 end */
	
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
	public String callBlacklist(String customerId) throws Exception{
		BlacklistReqVo reqVo = new BlacklistReqVo();
		reqVo.setIinstype("MI");
		reqVo.setIdentifyNumber(customerId);
		try {
			String soapxml = WebserviceObjConvert.convertObjToBase64Str(BlacklistReqVo.class, reqVo);
			String returnValue = clientBlacklistQueryService.blacklistQuery(soapxml);
			BlacklistRespVo blacklistRespVo = (BlacklistRespVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, BlacklistRespVo.class);
			if("Y".equals(blacklistRespVo.getResult())) {
				return "1";
			}else {
				return "0";
			}
		}catch (Exception e) {
			logger.error("blacklist webService錯誤 :"+e.getMessage());
			return "X";
		}
	}
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 end */
	
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
	public String callStakeHolder(String transactionId, String customerId) throws Exception {
		StakeHolderVo stakeHolderVo = new StakeHolderVo();
		stakeHolderVo.setListNo(transactionId);
		stakeHolderVo.setStakeId(customerId);
		stakeHolderVo.setUserCode("FET_MOBILE");
		try {
			String soapxml = WebserviceObjConvert.convertObjToBase64Str(StakeHolderVo.class, stakeHolderVo);
			String returnValue = clientStakeHolderService.stakeHolderQuery(soapxml);
			stakeHolderVo = (StakeHolderVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, StakeHolderVo.class);
			if("Y".equals(stakeHolderVo.getRelation()) || "9999".equals(stakeHolderVo.getResponseCode())) {
				return "1";
			}else {
				return "0";
			}
		}catch (Exception e) {
			logger.error("stakeHolder webService錯誤 :"+e.getMessage());
			return "X";
		}
	}
	/** mantis：mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 end */
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 end */
	
	/** 20240515：BJ016：業務員檢核 START
	 * */
	public BusinessSalesResultVo callCheckBusinessSales(String handlerIdentifyNumber) throws Exception {
		BusinessSalesRequestVo businessSalesRequestVo = new BusinessSalesRequestVo();
		businessSalesRequestVo.setRiskCode("MI");
		businessSalesRequestVo.setHandlerIdentifyNumber(handlerIdentifyNumber);
		try {
			BusinessSalesResultVo returnValue = clientCheckPolicyService.checkBusinessSales(businessSalesRequestVo);
			return returnValue;
		}catch (Exception e) {
			logger.error("checkBusinessSales webService錯誤 :"+e.getMessage());
		}
		return null;
	}
	/** 20240515：BJ016：業務員檢核 END*/
	
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 start */
	@SuppressWarnings("unchecked")
	@Override
	public Result updatePolicyDataByChubbReturnData() throws SystemException, Exception {
		Map<String,String> params = new HashMap<>();
		params.put("dataStatus", "00");
		Result result = chubbReturnCustomerService.findChubbReturnCustomerByParams(params);
		if(result.getResObject() != null) {
			List<ChubbReturnCustomer> resultList = (List<ChubbReturnCustomer>)result.getResObject();
			List<String> failList = new ArrayList<String>();
			List<String> epolicyFailList = new ArrayList<String>();
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 START*/
			int estoreCount = 0;
			Map<String,String> rptBatchNoEstoreMap = new HashMap<String,String>();
			for(ChubbReturnCustomer chubbReturnCustomer : resultList) {
				result = applicationService.findApplicationByUK(chubbReturnCustomer.getTransactionId());
				if(result.getResObject()!=null) {
					Application application = (Application) result.getResObject();
					if(EnumMobileDataSrc.ESTORE.getCode().equals(application.getDataSrc())) {
						estoreCount++;
						String rptBatchNo = application.getRptBatchNo();
						if(!rptBatchNoEstoreMap.containsKey(rptBatchNo)) {
							rptBatchNoEstoreMap.put(rptBatchNo, rptBatchNo);
						}
					}
					try{
						fetPolicyService.updatePolicyDataByChubbReturn(chubbReturnCustomer, application);
						if((chubbReturnCustomer.getTransactionId() != null && chubbReturnCustomer.getTransactionId().length() > 0) && 
								(chubbReturnCustomer.getPolicyNo() != null && chubbReturnCustomer.getPolicyNo().length() > 0) && 
								(chubbReturnCustomer.getEndorseNo() == null || chubbReturnCustomer.getEndorseNo().length() <= 0) && 
								"1".equals(chubbReturnCustomer.getReturnStatus())) {
							/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單(排除批單資料)*/
							try {
								fetPolicyService.insertFetMobileEpolicy(chubbReturnCustomer.getTransactionId(), chubbReturnCustomer.getPolicyNo(), "HOLD");
							} catch(Exception e) {
								logger.error("安達回傳保單及批單處理電子保單失敗，TRANSACTION_ID = "+ chubbReturnCustomer.getTransactionId()+":"+e.getMessage());
								e.printStackTrace();
							}
						}
						
					}catch(Exception e) {
						failList.add(application.getTransactionId());
						logger.error("安達回傳保單及批單處理更新失敗，TRANSACTION_ID = "+ application.getTransactionId()+":"+e.getMessage());
						e.printStackTrace();
					}
				}
			}// end for loop
			if(!failList.isEmpty()) {
				sendUpdateFailEmail(failList);
			}
			if(!rptBatchNoEstoreMap.isEmpty()) {
				Set<String> keySet = rptBatchNoEstoreMap.keySet();
				Iterator<String> it = keySet.iterator();
				while(it.hasNext()) {
					String rptBatchNo = it.next();
					logger.info("updatePolicyDataByChubbReturnData returnPolicyNoToG10 rptBatchNo="+rptBatchNo);
					this.returnPolicyNoToG10(rptBatchNo);
				}
			}
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 END*/
		}
		return this.getReturnResult("安達回傳保單及批單處理結果狀態更新作業成功");
	}
	
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 START */
	public PrpinsAgentRespVo prpinsAgentQuery(String identifyNumber) throws Exception{
		PrpinsAgentReqVo reqVo = new PrpinsAgentReqVo();
		reqVo.setType("1");// 查詢類別 ，必填，業務員查詢:1  服務人員查詢：2
		reqVo.setIdentifyNumber(identifyNumber);// 查詢身份編號 ，必填，業務員登錄證字號/服務人員員編
		logger.info("runFetPolicyServiceImpl prpinsAgentQuery identifyNumber="+identifyNumber);
		try {
			String soapxml = WebserviceObjConvert.convertObjToBase64Str(PrpinsAgentReqVo.class, reqVo);
			String returnValue = clientPrpinsAgentService.prpinsAgentQuery(soapxml);
			PrpinsAgentRespVo prpinsAgentRespVo = (PrpinsAgentRespVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, PrpinsAgentRespVo.class);
			logger.info("runFetPolicyServiceImpl prpinsAgentQuery prpinsAgentRespVo.toString():"+prpinsAgentRespVo.toString());
			return prpinsAgentRespVo;
		}catch (Exception e) {
			logger.error("prpinsAgentQuery webService錯誤 :"+e.getMessage());
			PrpinsAgentRespVo prpinsAgentRespVo = new PrpinsAgentRespVo();
			prpinsAgentRespVo.setpResult("N");
			prpinsAgentRespVo.setErrMsg(e.getMessage());
			return prpinsAgentRespVo;
		}
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END */
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 START*/
	@Override
	public Result returnPolicyNoToG10(String rptBatchNo) throws SystemException, Exception {
		logger.info("returnPolicyNoToG10 rptBatchNo::::" + rptBatchNo);
		Result result = customerService.returnPolicyNoToG10(rptBatchNo);
		List<ReturnPolicyNoToG10Vo> voList = (List<ReturnPolicyNoToG10Vo>) result.getResObject();
		List<String> apTransactionIdList = new ArrayList<String>();
		List<PolicyDataRequestToG10CustomerVo> customerList = new ArrayList<PolicyDataRequestToG10CustomerVo>();
		String dataSrc = EnumMobileDataSrc.ESTORE.getCode();
		if(CollectionUtils.isNotEmpty(voList)) {
		for (ReturnPolicyNoToG10Vo vo : voList) {
			vo.setReturnStatus(vo.getChubbReturnStatus());
			vo.setReturnMsg(vo.getChubbReturnMsg());
				PolicyDataRequestToG10CustomerVo req = new PolicyDataRequestToG10CustomerVo();
			BeanUtils.copyProperties(vo, req);
			customerList.add(req);
				apTransactionIdList.add(vo.getTransactionId().trim());
		}
		}
		// 向數開回傳保單號
		String updateStatus = "";
		String returnMsg = "";
		String url = configUtil.getString("returnPolicyNoToG10Url");
		logger.info("returnPolicyNoToG10 request url::" + url);
		Gson gson = new Gson();
		PolicyDataRequestToG10Vo req = new PolicyDataRequestToG10Vo();
		req.setCustomer(customerList);
		String requsetJson = gson.toJson(req);
		logger.info("returnPolicyNoToG10 request::" + requsetJson);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(requsetJson, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
			httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
			HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity respEntity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(respEntity);
            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
            	String respJsonStr = EntityUtils.toString(respEntity, "UTF-8");
            	logger.info("returnPolicyNoToG10 respJsonStr:"+respJsonStr);
            	G10ResponseVo respVo = gson.fromJson(respJsonStr,G10ResponseVo.class);
            	if(respVo != null) {
            		if("SUCCESS".equals(respVo.getResult())) {
            			updateStatus = "G10OK";
            			returnMsg = "回傳保單資料至數開成功";
            		}else {
            			updateStatus = "G10NG";
        				returnMsg = "回傳保單資料至數開失敗:"+respVo.getResultMsg();
            		}
            	}else {
    				returnMsg = "數開回傳response為空，請確認";
            	}
            }
		} catch (Exception e2) {
			e2.printStackTrace();
			logger.info(e2.getMessage());
		} finally {
			httpClient.close();
		}
		// 將回傳保單號給數開的結果，回壓至application.application_status
		List<Application> applicationList = new ArrayList<Application>();
		if(CollectionUtils.isNotEmpty(apTransactionIdList)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("dataSrc", dataSrc);
			params.put("rptBatchNo", rptBatchNo);
			Result apResult = this.applicationService.findApplicationByParams(params);
			if(apResult != null && apResult.getResObject() != null) {
				List<Application> applications = (List<Application>)apResult.getResObject();
				for(Application ap : applications) {
					if(apTransactionIdList.contains(ap.getTransactionId())) {
						applicationList.add(ap);
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(applicationList)) {
			for(Application app : applicationList) {
				app.setApplicationStatus(updateStatus);
				applicationService.updateApplication(app);
			}
			}
		if("G10NG".equals(updateStatus)) {
			ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
			Mailer mailer = new Mailer();
			String env = configUtil.getString("env");
			String smtpServer = configUtil.getString("smtp_host");
			String userName = configUtil.getString("smtp_username");
			String pwd = configUtil.getString("smtp_pwd");
			String contentType = "text/html; charset=utf-8";
			String auth = "smtp";
			String subject = env + "行動裝置險-回傳保單號至數開失敗通知";
			String from = configUtil.getString("mail_from_address");
			String to = configUtil.getString("uncheckApplicationNotifyCc");;
			StringBuffer mailBody = new StringBuffer();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());
			mailBody.append("時間：" + executeDate + "<BR>");
			mailBody.append("錯誤訊息：" + returnMsg);
			mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", "", "", "", "", mailBody.toString(), auth, userName, pwd);
		}
		return this.getReturnResult(returnMsg);
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 END*/
	/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單-----START*/
	@Override
	public Result sendMobileEpolicy() throws SystemException, Exception {
		Map<String,String> params = new HashMap<>();
		params.put("status", "WAIT");
		Result result = fetMobileEpolicyService.findFetMobileEpolicyByParams(params);
		List<String> failList = new ArrayList<String>();
		if(result.getResObject() != null) {
			List<FetMobileEpolicy> resultList = (List<FetMobileEpolicy>)result.getResObject();
			boolean success = false;
			for(FetMobileEpolicy entity : resultList) {
				success = false;
				try {
					success = fetPolicyService.genMobileEpolicyData(entity.getTransactionId());
					if(success) {
						entity.setStatus("FINISH");
						entity.setModifiedBy("system");
						entity.setModifiedTime(new Date());
						fetMobileEpolicyService.updateFetMobileEpolicy(entity);
					}
				} catch(Exception e) {
					failList.add(entity.getOid());
					logger.error("行動裝置險電子保單處理作業失敗，oid = "+ entity.getOid() +":"+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if(!failList.isEmpty()) {
			sendEpolicyFailEmail(failList);
		}
		return this.getReturnResult("行動裝置險電子保單處理作業成功");
	}
	/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單-----END*/
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 START*/
	@SuppressWarnings("unchecked")
	@Override
	public Result doTerminationNoticeCancel(String transactionId) throws SystemException, Exception {
		Map<String,String> params = new HashMap<String,String>();
		params.put("transactionId", transactionId);
		//查詢自取消件
		Result result = this.terminationNoticeService.findTerminationNoticeForCancel(params);
		List<TerminationNotice> totalList = new ArrayList<TerminationNotice>();
		TerminationNotice terminationNotice = null;
		if(result != null && result.getResObject() != null) {
			List<TerminationNoticeVo> cancelList = (List<TerminationNoticeVo>)result.getResObject();
			if(cancelList.size() > 0) {
				TerminationNoticeVo terminationNoticeVo = cancelList.get(0);
				terminationNotice = this.mappingTerminationNotice(terminationNoticeVo);
				totalList.add(terminationNotice);
			}
		}
		
		//將資料寫入Termination_Notice
		if(totalList != null && totalList.size() > 0) {
			boolean check = this.fetPolicyService.batchInsertTerminationNotice(totalList);
			if(!check) {
				logger.error("終止通知書作業寫入Termination_Notice記錄檔失敗");
				result = new Result();
				result.setResObject(new Boolean("false"));
				return result;
			}
		}
		
		boolean check = false;
		if(terminationNotice != null) {
			if("CANCEL".equals(terminationNotice.getType())) {
				check = this.liaMiNoticeCancleProcess(terminationNotice);
			}
		}
		
		result = new Result();
		result.setResObject(new Boolean(check));

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result doTerminationNoticeUnpaid(String transactionId) throws SystemException, Exception {
		Map<String,String> params = new HashMap<String,String>();
		params.put("transactionId", transactionId);
		List<TerminationNotice> totalList = new ArrayList<TerminationNotice>();
		TerminationNotice terminationNotice = null;
		
		//查詢未繳費(中途退保)
		Result result = this.terminationNoticeService.findTerminationNoticeForUnpaid1(params);
		if(result != null && result.getResObject() != null) {
			List<TerminationNoticeVo> unpaid1List = (List<TerminationNoticeVo>)result.getResObject();
			if(unpaid1List.size() > 0) {
				TerminationNoticeVo terminationNoticeVo = unpaid1List.get(0);
				terminationNotice = this.mappingTerminationNotice(terminationNoticeVo);
				totalList.add(terminationNotice);
			}
		} else {
			//查詢未繳費(註銷)
			result = this.terminationNoticeService.findTerminationNoticeForUnpaid2(params);
			if(result != null && result.getResObject() != null) {
				List<TerminationNoticeVo> unpaid2List = (List<TerminationNoticeVo>)result.getResObject();
				if(unpaid2List.size() > 0) {
					TerminationNoticeVo terminationNoticeVo = unpaid2List.get(0);
					terminationNotice = this.mappingTerminationNotice(terminationNoticeVo);
					totalList.add(terminationNotice);
				}
			}
		}
		
		//將資料寫入Termination_Notice
		if(totalList != null && totalList.size() > 0) {
			boolean check = this.fetPolicyService.batchInsertTerminationNotice(totalList);
			if(!check) {
				logger.error("終止通知書作業寫入Termination_Notice記錄檔失敗");
				result = new Result();
				result.setResObject(new Boolean("false"));
				return result;
			}
		}
		
		boolean check = false;
		if(terminationNotice != null) {
			if("UNPAID".equals(terminationNotice.getType())) {
				check = this.liaMiNoticeUnpaidProcess(terminationNotice);
			}
		}
		
		result = new Result();
		result.setResObject(new Boolean(check));

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result terminationNotice() throws SystemException, Exception {
		//查詢自取消件
		Map<String,String> noParams = new HashMap<String,String>();
		Result result = this.terminationNoticeService.findTerminationNoticeForCancel(noParams);
		List<TerminationNotice> totalList = new ArrayList<TerminationNotice>();
		if(result != null && result.getResObject() != null) {
			List<TerminationNoticeVo> cancelList = (List<TerminationNoticeVo>)result.getResObject();
			if(cancelList.size() > 0) {
				totalList = this.mappingListTerminationNotice(totalList, cancelList);
			}
		}
		
		//查詢未繳費(中途退保)
		result = this.terminationNoticeService.findTerminationNoticeForUnpaid1(noParams);
		if(result != null && result.getResObject() != null) {
			List<TerminationNoticeVo> unpaid1List = (List<TerminationNoticeVo>)result.getResObject();
			if(unpaid1List.size() > 0) {
				totalList = this.mappingListTerminationNotice(totalList, unpaid1List);
			}
		}
		
		//查詢未繳費(註銷)
		result = this.terminationNoticeService.findTerminationNoticeForUnpaid2(noParams);
		if(result != null && result.getResObject() != null) {
			List<TerminationNoticeVo> unpaid2List = (List<TerminationNoticeVo>)result.getResObject();
			if(unpaid2List.size() > 0) {
				totalList = this.mappingListTerminationNotice(totalList, unpaid2List);
			}
		}
		
		//將資料寫入Termination_Notice
		if(totalList != null && totalList.size() > 0) {
			boolean check = this.fetPolicyService.batchInsertTerminationNotice(totalList);
			if(!check) {
				logger.error("終止通知書作業寫入Termination_Notice記錄檔失敗");
			}
		}
		
		//來自遠傳電文的退保件(自取消)
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", "CANCEL");
		params.put("isSend", "N");
		result = this.terminationNoticeService.findTerminationNoticeByParams(params);

		if(result != null && result.getResObject() != null) {
			List<TerminationNotice> cancelList = (List<TerminationNotice>)result.getResObject();
			for(TerminationNotice terminationNotice : cancelList) {
				this.liaMiNoticeCancleProcess(terminationNotice);
			}
		}
		
		//來自安達因未收費而終止的保單號
		params.clear();
		params.put("type", "UNPAID");
		params.put("isSend", "N");
		result = this.terminationNoticeService.findTerminationNoticeByParams(params);
		if(result != null && result.getResObject() != null) {
			List<TerminationNotice> unpaidList = (List<TerminationNotice>)result.getResObject();
			for(TerminationNotice terminationNotice : unpaidList) {
				this.liaMiNoticeUnpaidProcess(terminationNotice);
			}
		}
		return null;
	}
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 END*/
	
	@Override
	public Result downloadRepairXmlFile(String strNowDate) throws SystemException, Exception {
		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			String strNowDate = sdf.format(new Date());
			if(strNowDate ==  null || strNowDate.length() <= 0) {
				return null;
			}
			SftpUtil sftpUtil = this.getArcoaSftpUtil();
			String remotePath = configUtil.getString("arcoaRemoteDirectory");
			remotePath += "/" + strNowDate;
			
			String strFolder = configUtil.getString("arcoaRootDirectory") + "/" + strNowDate;
			File folder = new File(strFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			String result = sftpUtil.downloadFolder(remotePath, strFolder);
			if(!"success".equals(result)) {
				return null;
			}
			
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(strFolder);
			for(String fileName : listFiles) {
				file = new File(strFolder + "\\" + fileName);
				if(file.exists() && file.isFile()) {
					//讀取XML內容----START
					String xmlContent = "";
					try (FileInputStream fis = new FileInputStream(file);
							BufferedReader br = new BufferedReader(new InputStreamReader(fis, "Big5"));) {
						String str;
			            while ((str = br.readLine()) != null) {
			                System.out.println(str);
			                xmlContent += str;
			            }
					} catch (IOException e) {
						e.printStackTrace();
					}
					//讀取XML內容----END
					
					if(xmlContent != null && xmlContent.length() > 0) {
						//將XML內容轉換成物件----START
						InputStream is = new ByteArrayInputStream(xmlContent.getBytes("BIG5"));
				        JAXBContext jaxbContext = JAXBContext.newInstance(BatchRepairVo.class);  
				   
				        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
				        BatchRepairVo vo= (BatchRepairVo) jaxbUnmarshaller.unmarshal(is);  
				        //將XML內容轉換成物件----END
			        	
				        //資料寫入資料庫
				        boolean check = this.fetPolicyService.batchInsertRepairXmlData(vo);
				        if(!check) {
				        	return this.getReturnResult("執行失敗");
				        } else {
				        	//將XML資料放到安達可以抓取的SFTP上----START
				        	String remoteUploadPath = configUtil.getString("mobClaimArcoaPath") + "/" + strNowDate;
				        	SftpUtil downloadSftpUtil = this.getDownloadSftpUtil();
							String uploadResult = downloadSftpUtil.uploadFolder(remoteUploadPath, strFolder);
							if("success".equals(uploadResult)) { 
//								String backupFilePath = strFolder + checkFolder + "\\backup\\" + fileName;
//								FileUtil.move(filePath, backupFilePath);
							}
							//將XML資料放到安達可以抓取的SFTP上----END
				        }
			        }
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadRepairXmlFile error:", e);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result downloadFetPaidFile() throws SystemException, Exception {
		try {
			List<FetPaid> lists = new ArrayList<FetPaid>();
			SftpUtil sftpUtil = this.getFetAccountSftpUtil();
			String remotePath = configUtil.getString("mobFetAccountSFTPRemotePath");
			List<String> sftpFileList = sftpUtil.getFileListFromSftp(remotePath);
			List<String> downloadFileList = new ArrayList<String>();
			if(sftpFileList == null || sftpFileList.size() <= 0) {
				logger.info("downloadFetPaidFile : 無任何FetPaid檔案");
				return this.getReturnResult("無任何檔案");
			} else {
				Map params = new HashMap();
				for(String filename : sftpFileList) {
					params.clear();
					if(!".".equals(filename) && !"..".equals(filename)) {
						if(filename.indexOf("paid") >= 0) {
							params.put("fileName", filename);
							int fileCheckCount = fetPaidService.countFetPaid(params);
							if(fileCheckCount == 0) {
								downloadFileList.add(filename);
							}
						}
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strNowDate = sdf.format(new Date());
			String fetPaidFileFolder = configUtil.getString("mobRootDirectory") + "\\FetPaid\\" + strNowDate;
			File folder = new File(fetPaidFileFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			sftpUtil.getFileFromSftp(remotePath, fetPaidFileFolder, downloadFileList);
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(fetPaidFileFolder);
			if(listFiles != null && listFiles.size() > 0) {
				Map params = new HashMap();
				Iterator<String> itr = listFiles.iterator(); 
				String filename = "";
				while (itr.hasNext()) {
					filename = itr.next();
					params.clear();
					if(!".".equals(filename) && !"..".equals(filename)) {
						params.put("fileName", filename);
						int fileCheckCount = fetPaidService.countFetPaid(params);
						if(fileCheckCount > 0) {
							itr.remove(); 
						}
					}
				}
			}
			String[] arrData = null;
			String[] arrTemp = null;
			FetPaid insertEntity = null;
			for(String fileName : listFiles) {
				
				file = new File(fetPaidFileFolder + "\\" + fileName);
				
				if(file.exists()) {
					arrData = new String[10];
					arrTemp = null;
					try (FileInputStream fis = new FileInputStream(file);
							BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "Big5"));) {

						String data = "";
						lists.clear();
						int dataCount = 0;
						while ((data = reader.readLine()) != null) {
							if(dataCount <= 0) {//標題列不儲存
								dataCount += 1;
								continue;
							} else {
								data = data.replace("\uFEFF", "");//刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split(",");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntity = this.mappingFetPaid(fileName, arrData);
									lists.add(insertEntity);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(lists != null && lists.size() > 0) {
        				if(fetPolicyService.batchInsertFetPaid(lists)) {
//        					FileUtil.move(fetPaidFileFolder + "\\" + fileName, fetPaidFileFolder + "\\backup\\" + fileName);
        				}
        			} else {
        				logger.info("RunFetPolicyService downloadFetPaidFile file content empty......");
        			}
				}
			}
			return this.getReturnResult("執行成功");
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadFetPaidFile error:", e);
		}
		return null;
	}

	@Override
	public Result downloadFetPayableFile() throws SystemException, Exception {
		try {
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadFetPayableFile error:", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result transferMiPolicyToCore(FetMobilePolicy entity, PrpinsAgentRespVo estorePrpinsAgentRespVo) throws SystemException, Exception {
		logger.info("transferMiPolicyToCore start......");
		logger.info("transferMiPolicyToCore entity PolicyNo : " + entity.getPolicyNo());
		Result result = new Result();
		ArrayList<String> errorList = new ArrayList<String>();  // 組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
		String errorMsg = "";//組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
		String policyNo = entity.getPolicyNo();
		//用保單號找出相關保批單資料
		Map<String, String> params = new HashMap<String, String>();
		/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  START*/
		params.put("status", "WAIT");
		/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  END*/
		params.put("policyNo", policyNo);
		params.put("orderByEndorseNo", "Y");
		Result result1 = this.fetPolicyService.findFetMobilePolicyByParams(params);
		if(result1.getResObject() == null){
			//查不到資料跳下一筆保單號
			errorMsg = "," + policyNo + ",," + "無法從FetMobilePolicy取得保批單資料";
			logger.info(errorMsg);
			Message message = new Message();
			message.setMessageString(errorMsg);
			result.setResObject(new Boolean("false"));
			result.setMessage(message);
			return result;
		}
		ArrayList<FetMobilePolicy> fetMobilePolicyList = (ArrayList<FetMobilePolicy>)result1.getResObject();
		for(FetMobilePolicy fetMobilePolicy:fetMobilePolicyList){
			if("WAIT".equalsIgnoreCase(fetMobilePolicy.getStatus())){
				errorMsg = this.fetPolicyService.importMiPolicyToCore(fetMobilePolicy, estorePrpinsAgentRespVo);
				if(errorMsg != null && errorMsg.length() > 0) {
					break;
				}
//			}else if("FINISH".equalsIgnoreCase(fetMobilePolicy.getStatus())){
//				continue;
			}else{
				continue;
			}
		}
		if(errorMsg != null && errorMsg.length() > 0) {
			Message message = new Message();
			message.setMessageString(errorMsg);
			result.setResObject(new Boolean("false"));
			result.setMessage(message);
		} else {
			result.setResObject(new Boolean("true"));
		}
		logger.info("transferMiPolicyToCore end......");
		return result;
	}
	
	@Override
	public Result downloadChubbEndorseFile() throws SystemException, Exception {
		try {
			SftpUtil sftpUtil = this.getSftpUtil();
			String remotePath = configUtil.getString("mobBatchEndorseFilePath");
			List<String> downloadFileList = sftpUtil.getFileListFromSftp(remotePath);
			//判斷SFTP上是否有檔案
			if(downloadFileList == null || downloadFileList.size() <= 0) {
				logger.info("downloadAcFile : SFTP無任何BatchEndorseFile檔案");
				return this.getReturnResult("無BatchEndorseFile檔案");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strNowDate = sdf.format(new Date());
			String batchEndorseFileFolder = configUtil.getString("mobRootDirectory") + remotePath + "/" + strNowDate;

			File folder = new File(batchEndorseFileFolder);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			//從SFTP下載檔案
			sftpUtil.getFileFromSftp(remotePath, batchEndorseFileFolder, downloadFileList);
			
			File file = null;
			List<String> listFiles = FileUtil.getAllFileName(batchEndorseFileFolder);
			String[] arrData = null;
			String[] arrTemp = null;
			List<ChubbCustomerEndorse> chubbCustomerEndorseList = new ArrayList<ChubbCustomerEndorse>();
			List<ChubbApplicantEndorse> chubbApplicantEndorseList = new ArrayList<ChubbApplicantEndorse>();
			List<ChubbInsuredEndorse> chubbInsuredEndorseList = new ArrayList<ChubbInsuredEndorse>();
			List<ChubbProductEndorse> chubbProductEndorseList = new ArrayList<ChubbProductEndorse>();
			ChubbCustomerEndorse insertEntityCce = null;
			ChubbApplicantEndorse insertEntityCae = null;
			ChubbInsuredEndorse insertEntityCie = null;
			ChubbProductEndorse insertEntityCpe = null;
			for(String fileName : listFiles) {
				
				file = new File(batchEndorseFileFolder + "\\" + fileName);
				
				if(file.exists()) {
					if(fileName.startsWith("CE")) {
						arrData = new String[13];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split("\\|\\|");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityCce = this.mappingChubbCustomerEndorse(arrData, fileName);
									if(insertEntityCce != null) {
										chubbCustomerEndorseList.add(insertEntityCce);
									}
								}
							}
							if(chubbCustomerEndorseList != null && chubbCustomerEndorseList.size() > 0) {
								this.fetPolicyService.batchInsertChubbCustomerEndorse(chubbCustomerEndorseList);
							} else {
								logger.info("RunFetPolicyService downloadChubbEndorseFile chubbCustomerEndorseList has no data........");
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.error("RunFetPolicyService downloadChubbEndorseFile download CustomerEndorse file error:", e);
						}
					} else if(fileName.startsWith("AE")) {
						arrData = new String[11];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split("\\|\\|");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityCae = this.mappingChubbApplicantEndorse(arrData, fileName);
									if(insertEntityCae != null) {
										chubbApplicantEndorseList.add(insertEntityCae);
									}
								}
							}
							if(chubbApplicantEndorseList != null && chubbApplicantEndorseList.size() > 0) {
								this.fetPolicyService.batchInsertChubbApplicantEndorse(chubbApplicantEndorseList);
							} else {
								logger.info("RunFetPolicyService downloadChubbEndorseFile chubbApplicantEndorseList has no data........");
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.error("RunFetPolicyService downloadChubbEndorseFile download ApplicantEndorse file error:", e);
						}
					} else if(fileName.startsWith("IE")) {
						arrData = new String[9];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split("\\|\\|");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityCie = this.mappingChubbInsuredEndorse(arrData, fileName);
									if(insertEntityCie != null) {
										chubbInsuredEndorseList.add(insertEntityCie);
									}
								}
							}
							if(chubbInsuredEndorseList != null && chubbInsuredEndorseList.size() > 0) {
								this.fetPolicyService.batchInsertChubbInsuredEndorse(chubbInsuredEndorseList);
							} else {
								logger.info("RunFetPolicyService downloadChubbEndorseFile chubbInsuredEndorseList has no data........");
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.error("RunFetPolicyService downloadChubbEndorseFile download InsuredEndorse file error:", e);
						}
					} else if(fileName.startsWith("PE")) {
						arrData = new String[9];
						arrTemp = null;
						try (FileInputStream fis = new FileInputStream(file);
							       InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							       BufferedReader reader = new BufferedReader(isr)) {

							String data = "";
							while ((data = reader.readLine()) != null) {
								data = data.replace("\uFEFF", "");//20230522刪除UTF8-BOM
								if(data.length() > 0) {
									arrTemp = data.split("\\|\\|");
									for(int i = 0; i<arrTemp.length; i++) {
										if(i < arrData.length) {
											arrData[i] = arrTemp[i];
										}else {
											break;
										}
									}
									insertEntityCpe = this.mappingChubbProductEndorse(arrData, fileName);
									if(insertEntityCpe != null) {
										chubbProductEndorseList.add(insertEntityCpe);
									}
								}
							}
							if(chubbProductEndorseList != null && chubbProductEndorseList.size() > 0) {
								this.fetPolicyService.batchInsertChubbProductEndorse(chubbProductEndorseList);
							} else {
								logger.info("RunFetPolicyService downloadChubbEndorseFile chubbProductEndorseList has no data........");
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.error("RunFetPolicyService downloadChubbEndorseFile download ProductEndorse file error:", e);
						}
					}
					sftpUtil.deleteFileToSftp(remotePath, fileName);
				}
			}
		} catch (Exception e) {
			logger.error("RunFetPolicyService downloadCubbEndorseFile error:", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result chubbEndorseFileDataTransfer() throws SystemException, Exception {
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("dataStatus", "00");
			Result result = this.chubbCustomerEndorseService.findChubbCustomerEndorseByParams(params);
			if(result == null || result.getResObject() == null) {
				return null;
			}
			List<ChubbCustomerEndorse> chubbCustomerEndorseList = (List<ChubbCustomerEndorse>)result.getResObject();
			if(chubbCustomerEndorseList != null && chubbCustomerEndorseList.size() > 0) {
				boolean check = false;
				for(ChubbCustomerEndorse chubbCustomerEndorse : chubbCustomerEndorseList) {
					try {
						check = fetPolicyService.batchInsertChubbEndorseData(chubbCustomerEndorse);
						if(check) {
							chubbCustomerEndorse.setDataStatus("01");
						} else {
							chubbCustomerEndorse.setDataStatus("02");
						}
					} catch (Exception e) {
						chubbCustomerEndorse.setDataStatus("02");
						logger.error("RunFetPolicyService chubbEndorseFileDataTransfer fetPolicyService.batchInsertChubbEndorseData error:", e);
					}
					chubbCustomerEndorse.setModifiedBy("system");
					chubbCustomerEndorse.setModifiedTime(new Date());
					this.chubbCustomerEndorseService.updateChubbCustomerEndorse(chubbCustomerEndorse);
				}
        	}
		} catch (Exception e) {
			logger.error("RunFetPolicyService chubbEndorseFileDataTransfer error:", e);
		}
		return null;
	}
	
	@Override
	public Result sendProposalInsufficientNotify() throws SystemException, Exception {
		Result methodResult = new Result();
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -21);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String checkDate = sdf.format(cal.getTime());
			Map<String,String> params = new HashMap<String,String>();
			params.put("startDate", checkDate);
			params.put("proposalFileCheck", "N");
			Result result = this.applicationService.findApplicationByParams(params);
			if(result == null || result.getResObject() == null) {
				return null;
			}
			List<Application> applicationList = (List<Application>)result.getResObject();
			ArrayList<String> errorList = new ArrayList<String>();
			String errorMsg = "";
			if(applicationList != null && applicationList.size() > 0) {
				boolean check = false;
				for(Application application : applicationList) {
					check = false;
					result = this.storeProposalInsufficientSms(application);
					if(result != null && result.getResObject() != null) {
						check = (Boolean)result.getResObject();
						if(!check) {
							errorMsg = application.getContractId() + "&&&" + result.getMessage().getMessageString();
							errorList.add(errorMsg);
						}
					}
				}
        	}
			
			if(errorList.size() > 0){
				ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
				 Mailer mailer = new Mailer();
				 String smtpServer = configUtil.getString("smtp_host");
				 String userName = configUtil.getString("smtp_username");
				 String password = configUtil.getString("smtp_pwd");
				 String contentType = "text/html; charset=utf-8";
				 String auth = "smtp";
				 String subject = "行動裝置險 -要保書不全簡訊記錄失敗件通知";
				 String from = configUtil.getString("mail_from_address");
				 String to = configUtil.getString("policyTransferPauseNotifyMail");
				 String cc = "";
				 StringBuffer mailBody = new StringBuffer();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String executeDate = dateFormat.format(new Date());
				 mailBody.append("時間：" + executeDate + "<BR>");
				 mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>合約編號</td><td>錯誤訊息</td></tr>");
				 int count = 0;
				 for(String str:errorList){
					 String s1 = str.split("&&&")[0];
					 String s2 = str.split("&&&")[1];
					 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td></tr>");
				 }
				 mailBody.append("</table>");
				 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			}
			methodResult.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService sendProposalInsufficientNotify error:", e);
		}
		return methodResult;
	}
	
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  START*/
	@Override
	public Result sendUncheckApplicationNotify() throws SystemException, Exception {
		logger.info("RunFetPolicyService sendUncheckApplicationNotify start...");
		Result result = new Result();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			Calendar alertStart = Calendar.getInstance();
			alertStart.add(Calendar.DAY_OF_MONTH, -20);// ex: 20250723 rpt_batch_no BETWEEN 20250703 AND 20250709
			Calendar alertEnd = Calendar.getInstance();
			alertEnd.add(Calendar.DAY_OF_MONTH, -14);
			Map<String,String> alertParams = new HashMap<String,String>();
			alertParams.put("alertStart", sdf.format(alertStart.getTime()));
			alertParams.put("alertEnd", sdf.format(alertEnd.getTime()));
			alertParams.put("orderByCondition", "Y");
			Result uncheckResult = this.applicationService.selectUncheckApplications(alertParams);
			if(uncheckResult != null && uncheckResult.getResObject() != null) {
				List<Application> alertUnchecks = (List<Application>)uncheckResult.getResObject();
				if(alertUnchecks != null && alertUnchecks.size() > 0) {
					ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
					Mailer mailer = new Mailer();
					String env = configUtil.getString("env");
					String smtpServer = configUtil.getString("smtp_host");
					String userName = configUtil.getString("smtp_username");
					String pwd = configUtil.getString("smtp_pwd");
					String contentType = "text/html; charset=utf-8";
					String auth = "smtp";
					String subject = env + "行動裝置險-未完成審核通過通知";
					String from = configUtil.getString("mail_from_address");
					String to = configUtil.getString("uncheckApplicationNotifyTo");
					String cc = configUtil.getString("uncheckApplicationNotifyCc");
					StringBuffer mailBody = new StringBuffer();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String executeDate = dateFormat.format(new Date());
					mailBody.append("時間：" + executeDate + "<BR>");
					mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>批次號</td><td>合約編號</td><td>保單號</td><td>批單號</td><td>批文</td><td>保險起日</td></tr>");
					int count = 0;
					for(Application uncheckAp : alertUnchecks) {
						mailBody.append("<tr><td>" + (++count) + "</td><td>" + uncheckAp.getRptBatchNo() + "</td><td>" + uncheckAp.getContractId() + "</td><td>" + uncheckAp.getPolicyNo() + "</td><td>" + uncheckAp.getEndorseNo() + "</td><td>" + uncheckAp.getEndorseContent() + "</td><td>" + uncheckAp.getStartDate() + "</td></tr>");
					}
					mailBody.append("</table>");
					mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody.toString(), auth, userName, pwd);
				}
			}
			result.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService sendUncheckApplicationNotify error:", e);
		}
		return result;
	}
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  END*/
	
	@Override
	public Result storeProposalInsufficientSms(Application application) throws SystemException, Exception {
		Result methodResult = new Result();
		Message message = new Message();
		String messageString = "";
		try {
			if(application == null) {
				methodResult.setResObject(new Boolean("false"));
				messageString = "Application不得為NULL";
				message.setMessageString(messageString);
				methodResult.setMessage(message);
				return methodResult;
			}
			
			if(application.getEndorseNo() != null && application.getEndorseNo().length() > 0) {
				methodResult.setResObject(new Boolean("false"));
				messageString = "此為批單資料(保單號：" + application.getPolicyNo() +",批單號：" + application.getEndorseNo() +")，不得發送要保書不全通知";
				message.setMessageString(messageString);
				methodResult.setMessage(message);
				return methodResult;
			}
			
			if("Y".equalsIgnoreCase(application.getProposalFileCheck())) {
				methodResult.setResObject(new Boolean("false"));
				messageString = "此保單(合約編號：" + application.getContractId() +")之要保書已審核通過，不得發送要保書不全通知";
				message.setMessageString(messageString);
				methodResult.setMessage(message);
				return methodResult;
			}
			
			Customer customer = null;
			Result result = this.customerService.findCustomerByUK(application.getTransactionId());
			if(result != null && result.getResObject() != null) {
				customer = (Customer)result.getResObject();
			} else {
				methodResult.setResObject(new Boolean("false"));
				messageString = "查無Customer資料(TransactionId:" + application.getTransactionId() + ")";
				message.setMessageString(messageString);
				methodResult.setMessage(message);
				return methodResult;
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			Calendar calEndDate = Calendar.getInstance();
			calEndDate.setTime(sdf.parse(application.getStartDate()));
			calEndDate.add(Calendar.DAY_OF_MONTH, 45);
			
			int rocYear = calEndDate.get(Calendar.YEAR) - 1911;
			int month = calEndDate.get(Calendar.MONTH) + 1;
			String rocDate = rocYear + "年" + month + "月" + calEndDate.get(Calendar.DAY_OF_MONTH) + "日";
			String smsContent = this.genProposalInsufficientSmsContent(rocDate, customer.getStoreName());
			
			ProposalInsufficientSms entity = new ProposalInsufficientSms();
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("PIS", 6));
			entity.setContractId(customer.getContractId());
			entity.setMobile(customer.getMsisdn());
			entity.setSmsContent(smsContent);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
			this.proposalInsufficientSmsService.insertProposalInsufficientSms(entity);
			
			methodResult.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService storeProposalInsufficientSms error:", e);
		}
		return methodResult;
	}
	
	@Override
	public Result sendProposalInsufficientSms() throws SystemException, Exception {
		Result methodResult = new Result();
		Message message = new Message();
		String messageString = "";
		try {
			Map params = new HashMap();
			params.put("smsDateIsNull", "Y");
			Result result = this.proposalInsufficientSmsService.findProposalInsufficientSmsByParams(params);
			ArrayList<String> errorList = new ArrayList<String>();
			String errorMsg = "";
			if(result != null && result.getResObject() != null) {
				List<ProposalInsufficientSms> searchResult = (List<ProposalInsufficientSms>)result.getResObject();
				String smsUrl = this.configUtil.getString("smsUrl");
				SmsVo sms = null;
				SmsUtil smsUtil = new SmsUtil();
				String msg = "";
				for(ProposalInsufficientSms entity : searchResult) {
					errorMsg = "";
					sms = new SmsVo();
					if(entity.getMobile() == null || entity.getMobile().trim().length() <= 0) {
						logger.info("RunFetPolicyService sendProposalInsufficientSms error(mobile is null or empty):contractId=" + entity.getContractId());
						errorMsg = entity.getContractId() + ",mobile is null or empty";
						errorList.add(errorMsg);
						entity.setErrorMsg(errorMsg);
						entity.setModifiedBy("system");
						entity.setModifiedTime(new Date());
						this.proposalInsufficientSmsService.updateProposalInsufficientSms(entity);
						continue;
					}
					if(entity.getSmsContent() == null || entity.getSmsContent().trim().length() <= 0) {
						logger.info("RunFetPolicyService sendProposalInsufficientSms error(SmsContent is null or empty):contractId=" + entity.getContractId());
						errorMsg = entity.getContractId() + ",SmsContent is null or empty";
						errorList.add(errorMsg);
						entity.setErrorMsg(errorMsg);
						entity.setModifiedBy("system");
						entity.setModifiedTime(new Date());
						this.proposalInsufficientSmsService.updateProposalInsufficientSms(entity);
						continue;
					}
					sms.setTarget(entity.getMobile());
					sms.setMessage(entity.getSmsContent());
					
					msg = smsUtil.sendSms(smsUrl, sms);
					if(!msg.startsWith("X0")){
						logger.info("RunFetPolicyService sendProposalInsufficientSms error(sendSms fail):contractId=" + entity.getContractId() + ",errMsg="+msg);
						errorMsg = entity.getContractId() + ",sendSms fail (errMsg:" + msg + ")";
						errorList.add(errorMsg);
						if(errorMsg.length() > 500) {
							errorMsg = errorMsg.substring(0, 500);
						}
						entity.setErrorMsg(errorMsg);
						entity.setModifiedBy("system");
						entity.setModifiedTime(new Date());
						this.proposalInsufficientSmsService.updateProposalInsufficientSms(entity);
					} else {
						entity.setSmsDate(new Date());
						entity.setModifiedBy("system");
						entity.setModifiedTime(new Date());
						this.proposalInsufficientSmsService.updateProposalInsufficientSms(entity);
					}
				}
			} else {
				methodResult.setResObject(new Boolean("false"));
				messageString = "查無ProposalInsufficientSms資料";
				message.setMessageString(messageString);
				methodResult.setMessage(message);
				return methodResult;
			}
			
			if(errorList.size() > 0){
				ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
				 Mailer mailer = new Mailer();
				 String smtpServer = configUtil.getString("smtp_host");
				 String userName = configUtil.getString("smtp_username");
				 String password = configUtil.getString("smtp_pwd");
				 String contentType = "text/html; charset=utf-8";
				 String auth = "smtp";
				 String subject = "行動裝置險 -要保書不全發送簡訊失敗件通知";
				 String from = configUtil.getString("mail_from_address");
				 String to = configUtil.getString("policyTransferPauseNotifyMail");
				 String cc = "";
				 StringBuffer mailBody = new StringBuffer();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String executeDate = dateFormat.format(new Date());
				 mailBody.append("時間：" + executeDate + "<BR>");
				 mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>合約編號</td><td>錯誤訊息</td></tr>");
				 int count = 0;
				 for(String str:errorList){
					 String s1 = str.split(",")[0];
					 String s2 = str.split(",")[1];
					 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td></tr>");
				 }
				 mailBody.append("</table>");
				 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			}
			
			methodResult.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService sendProposalInsufficientSms error:", e);
		}
		return methodResult;
	}
	
	@Override
	public Result genFetCancelNotificationData() throws SystemException, Exception {
		Result methodResult = new Result();
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -45);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String checkDate = sdf.format(cal.getTime());
			methodResult = this.genFetCancelNotificationData(checkDate);
		} catch (Exception e) {
			logger.error("RunFetPolicyService genFetCancelNotificationData error:", e);
		}
		return methodResult;
	}

	@Override
	public Result genFetCancelNotificationData(String date) throws SystemException, Exception {

		Result methodResult = new Result();
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("startDate", date);
			params.put("proposalFileCheckIsNull", "Y");
			Result result = this.applicationService.findApplicationByParams(params);
			if(result == null || result.getResObject() == null) {
				return null;
			}
			List<Application> applicationList = (List<Application>)result.getResObject();
			ArrayList<String> errorList = new ArrayList<String>();
			String errorMsg = "";
			if(applicationList != null && applicationList.size() > 0) {
				String resultMsg = "";
				for(Application application : applicationList) {
					resultMsg = "";
					resultMsg = this.storeFetCancelNotification(application);
					if(resultMsg != null && resultMsg.length() > 0) {
						errorMsg = application.getContractId() + "&&&" + resultMsg;
						errorList.add(errorMsg);
					}
				}
        	}
			
			if(errorList.size() > 0){
				ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
				 Mailer mailer = new Mailer();
				 String smtpServer = configUtil.getString("smtp_host");
				 String userName = configUtil.getString("smtp_username");
				 String password = configUtil.getString("smtp_pwd");
				 String contentType = "text/html; charset=utf-8";
				 String auth = "smtp";
				 String subject = "行動裝置險 -要保書不全取消保險名單資料產生失敗件通知";
				 String from = configUtil.getString("mail_from_address");
				 String to = configUtil.getString("policyTransferPauseNotifyMail");
				 String cc = "";
				 StringBuffer mailBody = new StringBuffer();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String executeDate = dateFormat.format(new Date());
				 mailBody.append("時間：" + executeDate + "<BR>");
				 mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>合約編號</td><td>錯誤訊息</td></tr>");
				 int count = 0;
				 String[] arrStr;
				 for(String str:errorList){
					 arrStr = str.split("&&&");
					 String s1 = arrStr[0];
					 String s2 = arrStr[1];
					 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td></tr>");
				 }
				 mailBody.append("</table>");
				 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);
			}
			methodResult.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService genFetCancelNotificationData error:", e);
		}
		return methodResult;
	
	}

	@Override
	public Result uploadFetCancelNotificationData() throws SystemException, Exception {
		Result methodResult = new Result();
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("uploadDateIsNull", "Y");
			Result result = this.fetCancelNotificationService.findFetCancelNotificationByParams(params);
			if(result == null || result.getResObject() == null) {
				logger.info("RunFetPolicyService uploadFetCancelNotificationData : Query FetCancelNotification no data.....");
				methodResult.setResObject(new Boolean("true"));
				return methodResult;
			}
			List<FetCancelNotification> searchList = (List<FetCancelNotification>)result.getResObject();
			String mobRootFolder = configUtil.getString("mobRootDirectory");
			String fetCancelNotificationPath = configUtil.getString("mobFetCancelNotificationFilePath");
			File folder = new File(mobRootFolder + fetCancelNotificationPath);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(searchList != null && searchList.size() > 0) {
				Date now = new Date();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String filename = "Cancel_" + sdf1.format(now);
				File ctbcFile = new File(mobRootFolder + fetCancelNotificationPath + "\\" + filename + ".txt");
				try (FileOutputStream fos = new FileOutputStream(ctbcFile);
     			       OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
     			       BufferedWriter writer = new BufferedWriter(osw)) {
					writer.append("INSURANCE_ID,MSISDN,OFFER_ID,SERVICE_START_DATE"+"\r\n");
					String line = "";
					for(FetCancelNotification entity : searchList) {
        				line = StringUtil.nullToSpace(entity.getInsuranceId()) + "," + StringUtil.nullToSpace(entity.getMsisdn()) + "," + StringUtil.nullToSpace(entity.getOfferId()) + "," + StringUtil.nullToSpace(entity.getServiceStartDate()) ;
        				writer.append(line + "\r\n");
        			}
				}
				
				File ctbcFileCtl = new File(mobRootFolder + fetCancelNotificationPath + "\\" + filename + ".ctl");
				try (FileOutputStream fos = new FileOutputStream(ctbcFileCtl);
	     			       OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
	     			       BufferedWriter writer = new BufferedWriter(osw)) {
					writer.append("RecCount|UpdateDate"+"\r\n");
					String line = searchList.size() + "|" +  sdf2.format(now);
					writer.append(line + "\r\n");
				}
				
//				SftpUtil sftpUtil = this.getFetAccountSftpUtil();
				String remoteUploadPath = configUtil.getString("mobFetAccountSFTPUploadRemotePath");
				String filePath = mobRootFolder + fetCancelNotificationPath + "\\" + ctbcFile.getName();
//				String uploadResult = sftpUtil.putFileToSftp2(remoteUploadPath, filePath);
				String uploadResult = "success";
				if("success".equalsIgnoreCase(uploadResult)) {
					String ctlFilePath = mobRootFolder + fetCancelNotificationPath + "\\" + ctbcFileCtl.getName();
//					uploadResult = sftpUtil.putFileToSftp2(remoteUploadPath, ctlFilePath);
					try {
						this.fetPolicyService.batchUpdateFetCancelNotification(searchList, now);
					} catch (Exception e) {
						logger.error("RunFetPolicyService uploadFetCancelNotificationData batchUpdateFetCancelNotification error:", e);
					}
				}
        	}
			
			methodResult.setResObject(new Boolean("true"));
		} catch (Exception e) {
			logger.error("RunFetPolicyService uploadFetCancelNotificationData error:", e);
		}
		return methodResult;
	}
	
	private String sendUpdateFailEmail(List<String> failList) {
		Mailer mailer = new Mailer();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			String subject =  "行動裝置險-安達回傳保單及批單處理問題件";
			String mailTo = "ce035@ctbcins.com";

			StringBuilder sb = new StringBuilder();
			sb.append("<p>時間:"+date+"</p>");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9'>");
			sb.append("<td>問題件TRANSACTION_ID</td>");
			sb.append("</tr>");
			for(String transactionId : failList) {
				sb.append("<tr>");
				sb.append("<td>" + transactionId + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("passbookCallSp sendEmail error", e);
		}
		return "";
	}
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 end */
	
	
	/**
	 * mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業
	 * 
	 * 線下批單資料問題件mail
	 * 
	 * @param errorList
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public void sendErrorMail(ArrayList<String> errorList, String executeDate, String groupNo, String title) throws AddressException, UnsupportedEncodingException, MessagingException{
		
		 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		 Mailer mailer = new Mailer();
		 String smtpServer = configUtil.getString("smtp_host");
		 String userName = configUtil.getString("smtp_username");
		 String password = configUtil.getString("smtp_pwd");
		 String contentType = "text/html; charset=utf-8";
		 String auth = "smtp";
		 String subject = title;
		 String from = configUtil.getString("mail_from_address");
		 String to = configUtil.getString("policyTransferPauseNotifyMail");
		 String cc = "";
		 StringBuffer mailBody = new StringBuffer();
		 mailBody.append("時間：" + executeDate + ", GroupNo：" + groupNo + "<BR>");
		 mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>TRANSACTION_ID</td><td>POLICY_NO</td><td>ENDORSE_NO</td><td>REMARK</td></tr>");
		 int count = 0;
		 for(String str:errorList){
			 String s1 = str.split(",")[0];
			 String s2 = str.split(",")[1];
			 String s3 = str.split(",")[2];
			 String s4 = str.split(",")[3];
			 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td><td>" + s3 + "</td><td>" + s4 + "</td></tr>");
		 }
		 mailBody.append("</table>");
		 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);

	}
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	private ChFile mappingChFile(String fileName,String[] arrData) {
		ChFile entity = new ChFile();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CH",6));
			entity.setFileName(fileName);
			
			entity.setCh001(arrData[0]);
			entity.setCh002(arrData[1]);
			entity.setCh003(arrData[2]);
			entity.setCh004(arrData[3]);
			entity.setCh005(arrData[4]);
			entity.setCh006(arrData[5]);
			entity.setCh007(arrData[6]);
			entity.setCh008(arrData[7]);
			entity.setCh009(arrData[8]);
			entity.setCh010(arrData[9]);
			
			entity.setCh011(arrData[10]);
			entity.setCh012(arrData[11]);
			entity.setCh013(arrData[12]);
			entity.setCh014(arrData[13]);
			entity.setCh015(arrData[14]);
			entity.setCh016(arrData[15].trim());
			entity.setCh017(arrData[16].trim());
			entity.setCh018(arrData[17].trim());
			entity.setCh018("0");//佣金直接給0
			entity.setCh019(arrData[18].trim());
			entity.setCh020(arrData[19]);
			
			entity.setCh021(arrData[20]);
			entity.setCh022(arrData[21]);
			entity.setCh023(arrData[22].trim());
			entity.setCh024(arrData[23]);
			entity.setCh025(arrData[24]);
			entity.setCh026(arrData[25]);
			entity.setCh027(arrData[26]);
			entity.setCh028(arrData[27]);
			entity.setCh029(arrData[28]);
			entity.setCh030(arrData[29]);
			
			entity.setCh031(arrData[30]);
			entity.setCh032(arrData[31]);
			entity.setCh033(arrData[32]);
			entity.setCh034(arrData[33]);
			entity.setCh035(arrData[34]);
			entity.setCh036(arrData[35]);
			entity.setCh037(arrData[36]);
			entity.setCh038(arrData[37]);
			entity.setCh039(arrData[38]);
			entity.setCh040(arrData[39]);

			entity.setCh041(arrData[40]);
			entity.setCh042(arrData[41]);
			entity.setCh043(arrData[42]);
			entity.setCh044(arrData[43]);
			entity.setCh045(arrData[44]);
			entity.setCh046(arrData[45]);
			entity.setCh047(arrData[46]);
			entity.setCh048(arrData[47]);
			entity.setCh049(arrData[48]);
			entity.setCh050(arrData[49]);

			entity.setCh051(arrData[50]);
			entity.setCh052(arrData[51]);
			entity.setCh053(arrData[52]);
			entity.setCh054(arrData[53]);
			entity.setCh055(arrData[54]);
			entity.setCh056(arrData[55]);
			entity.setCh057(arrData[56]);
			entity.setCh058(arrData[57]);
			entity.setCh059(arrData[58]);
			entity.setCh060(arrData[59]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingChFile error", e);
		}
		return entity;
	}
	
	private AcFile mappingAcFile(String fileName,String[] arrData) {
		AcFile entity = new AcFile();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("AC", 6));
			entity.setFileName(fileName);
			
			entity.setAc001(arrData[0]);
			entity.setAc002(arrData[1]);
			entity.setAc003(arrData[2]);
			entity.setAc004(arrData[3]);
			entity.setAc005(arrData[4]);
			entity.setAc006(arrData[5]);
			entity.setAc007(arrData[6]);
			entity.setAc008(arrData[7]);
			entity.setAc009(arrData[8]);
			entity.setAc010(arrData[9]);
			
			entity.setAc011(arrData[10]);
			entity.setAc012(arrData[11]);
			entity.setAc013(arrData[12]);
			entity.setAc014(arrData[13]);
			entity.setAc015(arrData[14]);
			entity.setAc016(arrData[15]);
			entity.setAc017(arrData[16]);
			entity.setAc018(arrData[17]);
			entity.setAc019(arrData[18]);
			entity.setAc020(arrData[19]);
			
			entity.setAc021(arrData[20]);
			entity.setAc022(arrData[21]);
			entity.setAc023(arrData[22]);
			entity.setAc024(arrData[23]);
			entity.setAc025(arrData[24]);
			entity.setAc026(arrData[25]);
			entity.setAc027(arrData[26]);
			entity.setAc028(arrData[27]);
			entity.setAc029(arrData[28]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingAcFile error", e);
		}
		return entity;
	}
	
	private ClFile mappingClFile(String fileName,String[] arrData) {
		ClFile entity = new ClFile();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CL", 6));
			entity.setFileName(fileName);
			
			entity.setCl001(arrData[0]);
			entity.setCl002(arrData[1]);
			entity.setCl003(arrData[2]);
			entity.setCl004(arrData[3]);
			entity.setCl005(arrData[4]);
			entity.setCl006(arrData[5]);
			entity.setCl007(arrData[6]);
			entity.setCl008(arrData[7].trim());
			entity.setCl009(arrData[8]);
			entity.setCl010(arrData[9]);
			
			entity.setCl011(arrData[10]);
			entity.setCl012(arrData[11]);
			entity.setCl013(arrData[12]);
			entity.setCl014(arrData[13]);
			entity.setCl015(arrData[14]);
			entity.setCl016(arrData[15]);
			entity.setCl017(arrData[16]);
			entity.setCl018(arrData[17]);
			entity.setCl019(arrData[18]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingClFile error", e);
		}
		return entity;
	}
	
	private CmFile mappingCmFile(String fileName,String[] arrData) {
		CmFile entity = new CmFile();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CM", 6));
			entity.setFileName(fileName);
			
			entity.setCm001(arrData[0]);
			entity.setCm002(arrData[1]);
			entity.setCm003(arrData[2]);
			entity.setCm004(arrData[3]);
			entity.setCm005(arrData[4]);
			entity.setCm006(arrData[5]);
			entity.setCm007(arrData[6]);
			entity.setCm008(arrData[7]);
			entity.setCm009(arrData[8]);
			entity.setCm010(arrData[9]);
			
			entity.setCm011(arrData[10]);
			entity.setCm012(arrData[11]);
			entity.setCm013(arrData[12]);
			entity.setCm014(arrData[13]);
			entity.setCm015(arrData[14]);
			entity.setCm016(arrData[15]);
			entity.setCm017(arrData[16]);
			entity.setCm018(arrData[17]);
			entity.setCm019(arrData[18]);
			entity.setCm020(arrData[19]);
			
			entity.setCm021(arrData[20]);
			entity.setCm022(arrData[21]);
			entity.setCm023(arrData[22]);
			entity.setCm024(arrData[23]);
			entity.setCm025(arrData[24]);
			entity.setCm026(arrData[25]);
			entity.setCm027(arrData[26]);
			entity.setCm028(arrData[27]);
			entity.setCm029(arrData[28]);
			entity.setCm030(arrData[29]);
			
			entity.setCm031(arrData[30]);
			entity.setCm032(arrData[31]);
			entity.setCm033(arrData[32]);
			entity.setCm034(arrData[33]);
			entity.setCm035(arrData[34]);
			entity.setCm036(arrData[35]);
			entity.setCm037(arrData[36]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingCmFile error", e);
		}
		return entity;
	}
	
	private AccountFile mappingAccountFile(String fileName,String[] arrData) {
		AccountFile entity = new AccountFile();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("ACC", 6));
			entity.setFileName(fileName);
			
			entity.setAcc001(arrData[0]);
			entity.setAcc002(arrData[1]);
			entity.setAcc003(arrData[2]);
			entity.setAcc004(arrData[3]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingAccountFile error", e);
		}
		return entity;
	}
	
	private TerminationNotice mappingTerminationNotice(TerminationNoticeVo terminationNoticeVo) {
		TerminationNotice entity = new TerminationNotice();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmm");
		Date now = new Date();
		try {
			GIGO.fill(entity, terminationNoticeVo);
			
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("TN", 6));
			entity.setSerialNo(sdf2.format(now) + FetTransactionIdGenUtil.getRandom(8));
			entity.setCreatedBy("system");
			entity.setCreatedTime(now);
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingCmFile error", e);
		}
		return entity;
	}
	
	private FetPaid mappingFetPaid(String fileName, String[] arrData) {
		FetPaid entity = new FetPaid();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("PD", 6));
			entity.setFileName(fileName);

			entity.setPd001(arrData[0]);
			entity.setPd002(arrData[1]);
			entity.setPd003(arrData[2]);
			entity.setPd004(arrData[3]);
			entity.setPd005(arrData[4]);
			entity.setPd006(arrData[5]);
			entity.setPd007(arrData[6]);
			entity.setPd008(arrData[7]);
			entity.setPd009(arrData[8]);
			entity.setPd010(arrData[9]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingFetPaid error", e);
		}
		return entity;
	}
	
	private FetPayable mappingFetPayable(String fileName, String[] arrData) {
		FetPayable entity = new FetPayable();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("PB", 6));
			entity.setFileName(fileName);
			
			entity.setPb001(arrData[0]);
			entity.setPb002(arrData[1]);
			entity.setPb003(arrData[2]);
			entity.setPb004(arrData[3]);
			entity.setPb005(arrData[4]);
			entity.setPb006(arrData[5]);
			entity.setPb007(arrData[6]);
			entity.setPb008(arrData[7]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingFetPaid error", e);
		}
		return entity;
	}
	
	private CustomerEndorse mappingCustomerEndorse(String[] arrData) {
		CustomerEndorse entity = new CustomerEndorse();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CE", 6));
			entity.setEndorseNo(arrData[0]);
			entity.setContractId(arrData[1]);
			entity.setPolicyNo(arrData[2]);
			entity.setEndorseType(arrData[3]);
			entity.setEndorseDate(arrData[4]);
			entity.setEndorseContent(arrData[5]);
			entity.setStartDate(arrData[6]);
			entity.setEndDate(arrData[7]);
			entity.setMsisdn(arrData[8]);
			entity.setPayPeriod(arrData[9]);
			entity.setPayPeriodStartDate(arrData[10]);
			entity.setPayPeriodEndDate(arrData[11]);
			entity.setProjectCodePremium(arrData[12]);
			entity.setDataStatus("00");
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingCustomerEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ApplicantEndorse mappingApplicantEndorse(String transactionId, String[] arrData) {
		ApplicantEndorse entity = new ApplicantEndorse();
		try {
			entity.setTransactionId(transactionId);
			entity.setCustomerId(arrData[1]);
			entity.setName(arrData[2]);
			entity.setResponsiblePerson(arrData[3]);
			entity.setBirthday(arrData[4]);
			entity.setTelNo(arrData[5]);
			entity.setZipCode(arrData[6]);
			entity.setAddress(arrData[7]);
			entity.setReleation(arrData[8]);
			entity.setCountry(arrData[9]);
			entity.setEmailAddress(arrData[10]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingApplicantEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private InsuredEndorse mappingInsuredEndorse(String transactionId, String[] arrData) {
		InsuredEndorse entity = new InsuredEndorse();
		try {
			entity.setTransactionId(transactionId);
			entity.setCustomerId(arrData[1]);
			entity.setName(arrData[2]);
			entity.setResponsiblePerson(arrData[3]);
			entity.setBirthday(arrData[4]);
			entity.setTelNo(arrData[5]);
			entity.setZipCode(arrData[6]);
			entity.setAddress(arrData[7]);
			entity.setCountry(arrData[8]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingInsuredEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ProductEndorse mappingProductEndorse(String transactionId, String[] arrData) {
		ProductEndorse entity = new ProductEndorse();
		try {
			entity.setTransactionId(transactionId);
			entity.setProdno(arrData[1]);
			entity.setProdname(arrData[2]);
			entity.setType(arrData[3]);
			entity.setBrand(arrData[4]);
			entity.setModel(arrData[5]);
			entity.setImei(arrData[6]);
			entity.setRrp(arrData[7]);
			entity.setPurchaseDate(arrData[8]);
			
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingProductEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ChubbCustomerEndorse mappingChubbCustomerEndorse(String[] arrData, String fileName) {
		ChubbCustomerEndorse entity = new ChubbCustomerEndorse();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CCE", 6));
			entity.setEndorseNo(arrData[0]);
			entity.setContractId(arrData[1]);
			entity.setPolicyNo(arrData[2]);
			entity.setEndorseType(arrData[3]);
			entity.setEndorseDate(arrData[4]);
			entity.setEndorseContent(arrData[5]);
			entity.setStartDate(arrData[6]);
			entity.setEndDate(arrData[7]);
			entity.setMsisdn(arrData[8]);
			entity.setPayPeriod(arrData[9]);
			entity.setPayPeriodStartDate(arrData[10]);
			entity.setPayPeriodEndDate(arrData[11]);
			entity.setProjectCodePremium(arrData[12]);
			entity.setDataStatus("00");
			entity.setFileName(fileName);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingCustomerEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ChubbApplicantEndorse mappingChubbApplicantEndorse(String[] arrData, String fileName) {
		ChubbApplicantEndorse entity = new ChubbApplicantEndorse();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CAE", 6));
			entity.setEndorseNo(arrData[0]);
			entity.setCustomerId(arrData[1]);
			entity.setName(arrData[2]);
			entity.setResponsiblePerson(arrData[3]);
			entity.setBirthday(arrData[4]);
			entity.setTelNo(arrData[5]);
			entity.setZipCode(arrData[6]);
			entity.setAddress(arrData[7]);
			entity.setReleation(arrData[8]);
			entity.setCountry(arrData[9]);
			entity.setEmailAddress(arrData[10]);
			entity.setFileName(fileName);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingApplicantEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ChubbInsuredEndorse mappingChubbInsuredEndorse(String[] arrData, String fileName) {
		ChubbInsuredEndorse entity = new ChubbInsuredEndorse();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CIE", 6));
			entity.setEndorseNo(arrData[0]);
			entity.setCustomerId(arrData[1]);
			entity.setName(arrData[2]);
			entity.setResponsiblePerson(arrData[3]);
			entity.setBirthday(arrData[4]);
			entity.setTelNo(arrData[5]);
			entity.setZipCode(arrData[6]);
			entity.setAddress(arrData[7]);
			entity.setCountry(arrData[8]);
			entity.setFileName(fileName);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingInsuredEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ChubbProductEndorse mappingChubbProductEndorse(String[] arrData, String fileName) {
		ChubbProductEndorse entity = new ChubbProductEndorse();
		try {
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CPE", 6));
			entity.setEndorseNo(arrData[0]);
			entity.setProdno(arrData[1]);
			entity.setProdname(arrData[2]);
			entity.setType(arrData[3]);
			entity.setBrand(arrData[4]);
			entity.setModel(arrData[5]);
			entity.setImei(arrData[6]);
			entity.setRrp(arrData[7]);
			entity.setPurchaseDate(arrData[8]);
			entity.setFileName(fileName);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingProductEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private FetMobilePolicySales mappingFetMobilePolicySales(String transactionId, BusinessSalesResultVo businessSalesResultVo) {
		FetMobilePolicySales entity = new FetMobilePolicySales();
		try {
			entity.setTransactionId(transactionId);
			entity.setHandlerCode(businessSalesResultVo.getHandlerCode());
			entity.setHandlerName(businessSalesResultVo.getHandlerName());
			entity.setExtraComCode(businessSalesResultVo.getExtraComCode());
			entity.setExtraComName(businessSalesResultVo.getExtraComName());
			entity.setChannelType(businessSalesResultVo.getChannelType());
			entity.setComCode(businessSalesResultVo.getComCode());
			entity.setBusinessNature(businessSalesResultVo.getBusinessNature());
			entity.setAgentCode(businessSalesResultVo.getAgentCode());
			entity.setAgentName(businessSalesResultVo.getAgentName());
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingFetMobilePolicySales error", e);
			entity = null;
		}
		return entity;
	}
	
	private SftpUtil getSftpUtil() throws SystemException, Exception {
		String ip = configUtil.getString("mobSFTP");
		String user = configUtil.getString("mobSftpUserPut");
		String mima = configUtil.getString("mobSftpPwdPut");
		SftpUtil sftpUtil = new SftpUtil(ip,22,user,mima);
		return sftpUtil;
	}
	
	private SftpUtil getDownloadSftpUtil() throws SystemException, Exception {
		String ip = configUtil.getString("mobSFTP");
		String user = configUtil.getString("mobSftpUserGet");
		String mima = configUtil.getString("mobSftpPwdGet");
		SftpUtil sftpUtil = new SftpUtil(ip,22,user,mima);
		return sftpUtil;
	}
	
	private SftpUtil getFetSftpUtil() throws SystemException, Exception {
		String ip = configUtil.getString("mobFetSFTP");
		String user = configUtil.getString("mobFetSFTPAccount");
		String mima = configUtil.getString("mobFetSFTPPwd");
		SftpUtil sftpUtil = new SftpUtil(ip,22,user,mima);
		return sftpUtil;
	}
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	private SftpUtil getArcoaSftpUtil() throws SystemException, Exception {
		String ip = configUtil.getString("arcoaSFTP");
		String user = configUtil.getString("arcoaSftpUser");
		String mima = configUtil.getString("arcoaSftpPwd");
		SftpUtil sftpUtil = new SftpUtil(ip,22,user,mima);
		return sftpUtil;
	}
	
	private SftpUtil getFetAccountSftpUtil() throws SystemException, Exception {
		String ip = configUtil.getString("mobFetAccountSFTP");
		String user = configUtil.getString("mobFetAccountSFTPAccount");
		String mima = configUtil.getString("mobFetAccountSFTPPwd");
		SftpUtil sftpUtil = new SftpUtil(ip,22,user,mima);
		return sftpUtil;
	}
	
	private String sendEpolicyFailEmail(List<String> failList) {
		Mailer mailer = new Mailer();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			String subject =  "行動裝置險-電子保單處理問題件";
			String mailTo = "ce035@ctbcins.com";

			StringBuilder sb = new StringBuilder();
			sb.append("<p>時間:"+date+"</p>");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9'>");
			sb.append("<td>問題件OID</td>");
			sb.append("</tr>");
			for(String oid : failList) {
				sb.append("<tr>");
				sb.append("<td>" + oid + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", "", "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("passbookCallSp sendEmail error", e);
		}
		return "";
	}
	
	public CloseableHttpClient createTlsV2HttpClient() throws KeyManagementException, 
    	UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
		//方法1-----START
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                SSLContext.getDefault(),
                new String[] {"SSLv2Hello","SSLv3","TLSv1","TLSv1.1","TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", socketFactory)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
		//方法1-----END
//		//方法2-----START
//        SSLConnectionSocketFactory sslsf = null;
//		try {
//			SSLContext sslContext = new SSLContextBuilder()
//					.useProtocol("TLSv1.2")
//					.loadTrustMaterial(null, new TrustStrategy() {
//
//						public boolean isTrusted(X509Certificate[] chain,
//								String authType) throws CertificateException {
//							return true;
//						}
//					}).build();
//			sslsf = new SSLConnectionSocketFactory(sslContext,
//					new X509HostnameVerifier() {
//
//						public boolean verify(String arg0, SSLSession arg1) {
//							return true;
//						}
//
//						public void verify(String host, SSLSocket ssl)
//								throws IOException {
//						}
//
//						public void verify(String host, X509Certificate cert)
//								throws SSLException {
//						}
//
//						public void verify(String host, String[] cns,
//								String[] subjectAlts) throws SSLException {
//						}
//					});
//		} catch (GeneralSecurityException e) {
//			e.printStackTrace();
//		}
//		
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setSocketTimeout(60000).setConnectTimeout(60000)
//				.setConnectionRequestTimeout(60000).build();
//        
//        CloseableHttpClient client = HttpClients.custom()
//				.setSSLSocketFactory(sslsf)
//				.setDefaultRequestConfig(requestConfig).build();
//      //方法2-----END
        return client;
	}
	
	private Prpcmain findPrpcmain(String policyno) {
		if(policyno == null || policyno.length() <= 0)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		Prpcmain prpcmain = null;
		try {
			params.put("policyno", policyno);
			Result result = this.prpcmainService.findPrpcmainByParams(params);
			if(result != null && result.getResObject() != null) {
				List<Prpcmain> listPrpcmain = (List<Prpcmain>)result.getResObject();
				if(listPrpcmain.size() > 0) {
					prpcmain = listPrpcmain.get(0);
				}
			}
		} catch (Exception e) {
			logger.error("findPrpcmain error", e);
		}
		return prpcmain;
	}
	
	private List<TerminationNotice> mappingListTerminationNotice(List<TerminationNotice> terminationNoticeList, List<TerminationNoticeVo> terminationNoticeVoList) {
		if(terminationNoticeList == null) {
			terminationNoticeList = new ArrayList<TerminationNotice>();
		}

		if(terminationNoticeVoList == null || terminationNoticeVoList.size() <= 0) {
			return terminationNoticeList;
		}

		try {
			TerminationNotice entity;
			for(TerminationNoticeVo vo : terminationNoticeVoList) {
				entity = this.mappingTerminationNotice(vo);
				terminationNoticeList.add(entity);
			}
		} catch (Exception e) {
			logger.error("mappingListTerminationNotice error", e);
		}
		return terminationNoticeList;
	}
	
	private boolean liaMiNoticeCancleProcess(TerminationNotice terminationNotice) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strPdfGenUrl = configUtil.getString("lia00102PdfGenUrl");
			String pdfFolder = configUtil.getString("lia00102PdfFolder");
			Result result = this.xchgTerminationNoticeService.batchtLiaMiNoticeCancle(terminationNotice);
			if(result == null || result.getResObject() == null) {
				logger.error("終止通知書作業產生LiaMiNoticeCancle PDF失敗");
				return false;
			} else {
				LiaMiNoticeCancle liaMiNoticeCancle = (LiaMiNoticeCancle)result.getResObject();
				Date createTime = new Date();
				String pdfFilePath = this.xchgTerminationNoticeService.genLia001Pdf(liaMiNoticeCancle.getSerialno(), liaMiNoticeCancle.getPolicyno(), createTime, strPdfGenUrl, pdfFolder);
				if(pdfFilePath != null && pdfFilePath.length() > 0) {
					result = xchgTerminationNoticeService.batchGenMobileInsSmsCancle(liaMiNoticeCancle, terminationNotice, pdfFilePath, createTime);
					if(result != null && !"success".equals(result.getMessage().getMessageString())) {
						logger.error("終止通知書作業 Update LiaMiNoticeCancle fail：Serialno = " + liaMiNoticeCancle.getSerialno());
						return false;
					} else {
						terminationNotice.setIsSend("Y");
						terminationNotice.setInputDate(sdf.format(liaMiNoticeCancle.getInputdate()));
						terminationNotice.setModifiedBy("system");
						terminationNotice.setModifiedTime(new Date());
						result = this.terminationNoticeService.updateTerminationNotice(terminationNotice);
						if(result != null && !"PUB120".equals(result.getMessage().getOpCode())) {
							logger.error("終止通知書作業更新TerminationNotice失敗 fail：Serialno = " + terminationNotice.getPolicyNo());
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("終止通知書作業產生LiaMiNoticeCancle PDF失敗 fail：Serialno = " + terminationNotice.getPolicyNo(), e);
		}
		return true;
	}
	
	private boolean liaMiNoticeUnpaidProcess(TerminationNotice terminationNotice) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strPdfGenUrl = configUtil.getString("lia00101PdfGenUrl");
			String pdfFolder = configUtil.getString("lia00101PdfFolder");
			Result result = this.xchgTerminationNoticeService.batchLiaMiNoticeUnpaid(terminationNotice);
			if(result == null || result.getResObject() == null) {
				logger.error("終止通知書作業產生LiaMiNoticeUnpaid PDF失敗");
			} else {
				LiaMiNoticeUnpaid liaMiNoticeUnpaid = (LiaMiNoticeUnpaid)result.getResObject();
				Date createTime = new Date();
				String pdfFilePath = this.xchgTerminationNoticeService.genLia001Pdf(liaMiNoticeUnpaid.getSerialno(), liaMiNoticeUnpaid.getPolicyno(), createTime, strPdfGenUrl, pdfFolder);
				if(pdfFilePath != null && pdfFilePath.length() > 0) {
					result = xchgTerminationNoticeService.batchGenMobileInsSmsUnpaid(liaMiNoticeUnpaid, terminationNotice, pdfFilePath, createTime);
					if(result != null && !"success".equals(result.getMessage().getMessageString())) {
						logger.error("終止通知書作業 Update LiaMiNoticeUnpaid fail：Serialno = " + liaMiNoticeUnpaid.getSerialno());
					} else {
						terminationNotice.setIsSend("Y");
						terminationNotice.setInputDate(sdf.format(liaMiNoticeUnpaid.getInputdate()));
						terminationNotice.setModifiedBy("system");
						terminationNotice.setModifiedTime(new Date());
						result = this.terminationNoticeService.updateTerminationNotice(terminationNotice);
						if(result != null && !"PUB120".equals(result.getMessage().getOpCode())) {
							logger.error("終止通知書作業更新TerminationNotice失敗 fail：Serialno = " + terminationNotice.getPolicyNo());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("終止通知書作業產生LiaMiNoticeUnpaid PDF失敗 fail：Serialno = " + terminationNotice.getPolicyNo(), e);
		}
		return true;
	}
	
	private Prpcopycommission findPrpcopycommission(String policyno, String endorseNo) {
		if(policyno == null || policyno.length() <= 0)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		Prpcopycommission prpcopycommission = null;
		try {
			params.put("policyno", policyno);
			if(endorseNo == null || endorseNo.trim().length() <= 0) {
				params.put("endorseno", policyno);
			} else {
				params.put("endorseno", endorseNo);
			}
			Result result = this.prpcopycommissionService.findPrpcopycommissionByParams(params);
			if(result != null && result.getResObject() != null) {
				List<Prpcopycommission> listPrpcopycommission = (List<Prpcopycommission>)result.getResObject();
				if(listPrpcopycommission.size() > 0) {
					prpcopycommission = listPrpcopycommission.get(0);
				}
			}
		} catch (Exception e) {
			logger.error("findPrpcopycommission error", e);
		}
		return prpcopycommission;
	}
	
	private String genProposalInsufficientSmsContent(String date, String storeName) {
		String smsContent = "";
		try {
			if(date == null || date.length() <= 0) {
				return smsContent;
			}
			String sendStoreName = "";
			if(storeName != null && storeName.length() > 0) {
				sendStoreName = "(" + storeName + ")";
			}
			/** mantis：MOB0027，處理人員：CE035，需求單編號：MOB0027 簡訊內容調整  START */
//			smsContent = "[行動裝置保險申請補件通知] 感謝您透過遠傳申請中國信託產險行動裝置保險，"
//					+ "由於要保文件不齊全導致無法進行完成審核作業，" 
//					+ "為保障您的權益，敬請於" + date + "前至原投保遠傳門市" + sendStoreName +"進行補件，" 
//					+ "如未能於期限內完成補件，將取消本保險契約之申請且不再另行通知，" 
//					+ "若您已完成補件請忽略此提醒! 如有相關問題，" 
//					+ "請撥打中國信託產險0800-226-988由專人為您說明(服務時間週一至週五08:50~17:30)謝謝。";
			smsContent = "您於中信產險投保的行動裝置險文件未齊全，敬請於"+date+"前至原投保遠傳門市"+sendStoreName+"進行補件，如未於期限內完成補件，將取消本保險契約之申請，若有任何疑問，歡迎致電0800-226-988";
			/** mantis：MOB0027，處理人員：CE035，需求單編號：MOB0027 簡訊內容調整  END */
		} catch (Exception e) {
			logger.error("genProposalInsufficientSmsContent error", e);
		}
		return smsContent;
	}
	
	private String storeFetCancelNotification(Application application) throws SystemException, Exception {
		logger.info("RunFetPolicyService storeFetCancelNotification start...");
		String messageString = "";
		try {
			if(application == null) {
				messageString = "Application不得為NULL";
				logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
				return messageString;
			}
			
			logger.info("RunFetPolicyService storeFetCancelNotification Application transactionId=" +application.getTransactionId());
			
			if(application.getEndorseNo() != null && application.getEndorseNo().length() > 0) {
				messageString = "此為批單資料(保單號：" + application.getPolicyNo() +",批單號：" + application.getEndorseNo() +")，不得產生要保書不全取消保險名單資料";
				logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
				return messageString;
			}
			
			if("Y".equalsIgnoreCase(application.getProposalFileCheck())) {
				messageString = "此保單(合約編號：" + application.getContractId() +")之要保書已審核通過，不得產生要保書不全取消保險名單資料";
				logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
				return messageString;
			}
			
			Customer customer = null;
			Result result = this.customerService.findCustomerByUK(application.getTransactionId());
			if(result != null && result.getResObject() != null) {
				customer = (Customer)result.getResObject();
			} else {
				messageString = "查無Customer資料(TransactionId:" + application.getTransactionId() + ")";
				logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
				return messageString;
			}
			
			if(customer != null) {
				if(customer.getContractId() != null && customer.getContractId().length() > 0) {
					Map<String,String> params = new HashMap<String,String>();
					params.put("insuranceId", customer.getContractId());
					int count = this.fetCancelNotificationService.countFetCancelNotification(params);
					if(count > 0) {
						messageString = "合約編號：" + customer.getContractId() + "資料已存在於要保書不全取消保險名單資料表(Fet_Cancel_Notification)中";
						logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
						return messageString;
					}
				} else {
					messageString = "查無Customer合約編號(TransactionId:" + application.getTransactionId() + ")";
					logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
					return messageString;
				}
			} else {
				messageString = "查無Customer資料(TransactionId:" + application.getTransactionId() + ")";
				logger.info("RunFetPolicyService storeFetCancelNotification msg=" +messageString);
				return messageString;
			}

			FetCancelNotification entity = new FetCancelNotification();
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("FCN", 6));
			entity.setInsuranceId(customer.getContractId());
			entity.setMsisdn(customer.getMsisdn());
			entity.setOfferId(customer.getOfferInstanceId());
			String startDate = customer.getServiceEffectiveDate();
			String serviceStartDate = "";
			if(startDate != null && startDate.length() >= 8) {
				serviceStartDate = startDate.substring(0, 4) + "/" + startDate.substring(4, 6) + "/" + startDate.substring(6, 8);
			}
			entity.setServiceStartDate(serviceStartDate);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
			this.fetCancelNotificationService.insertFetCancelNotification(entity);

		} catch (Exception e) {
			logger.error("RunFetPolicyService storeFetCancelNotification error:", e);
			messageString = e.getMessage();
		} finally {
			logger.info("RunFetPolicyService storeFetCancelNotification end...");
		}
		return messageString;
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FetPolicyService getFetPolicyService() {
		return fetPolicyService;
	}

	public void setFetPolicyService(FetPolicyService fetPolicyService) {
		this.fetPolicyService = fetPolicyService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public MobileInsBatchInfoService getMobileInsBatchInfoService() {
		return mobileInsBatchInfoService;
	}

	public void setMobileInsBatchInfoService(MobileInsBatchInfoService mobileInsBatchInfoService) {
		this.mobileInsBatchInfoService = mobileInsBatchInfoService;
	}

	public ApplicantService getApplicantService() {
		return applicantService;
	}

	public void setApplicantService(ApplicantService applicantService) {
		this.applicantService = applicantService;
	}

	public AmlService getClientAmlQueryService() {
		return clientAmlQueryService;
	}

	public void setClientAmlQueryService(AmlService clientAmlQueryService) {
		this.clientAmlQueryService = clientAmlQueryService;
	}

	public BlacklistQueryService getClientBlacklistQueryService() {
		return clientBlacklistQueryService;
	}

	public void setClientBlacklistQueryService(BlacklistQueryService clientBlacklistQueryService) {
		this.clientBlacklistQueryService = clientBlacklistQueryService;
	}

	public StakeHolderService getClientStakeHolderService() {
		return clientStakeHolderService;
	}

	public void setClientStakeHolderService(StakeHolderService clientStakeHolderService) {
		this.clientStakeHolderService = clientStakeHolderService;
	}

	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 start */
	public ChubbReturnCustomerService getChubbReturnCustomerService() {
		return chubbReturnCustomerService;
	}

	public void setChubbReturnCustomerService(ChubbReturnCustomerService chubbReturnCustomerService) {
		this.chubbReturnCustomerService = chubbReturnCustomerService;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 end */

	public CustomerEndorseService getCustomerEndorseService() {
		return customerEndorseService;
	}

	public void setCustomerEndorseService(
			CustomerEndorseService customerEndorseService) {
		this.customerEndorseService = customerEndorseService;
	}

	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	public AcFileService getAcFileService() {
		return acFileService;
	}

	public void setAcFileService(AcFileService acFileService) {
		this.acFileService = acFileService;
	}

	public ChFileService getChFileService() {
		return chFileService;
	}

	public void setChFileService(ChFileService chFileService) {
		this.chFileService = chFileService;
	}

	public ClFileService getClFileService() {
		return clFileService;
	}

	public void setClFileService(ClFileService clFileService) {
		this.clFileService = clFileService;
	}

	public CmFileService getCmFileService() {
		return cmFileService;
	}

	public void setCmFileService(CmFileService cmFileService) {
		this.cmFileService = cmFileService;
	}
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/

	public FetMobileEpolicyService getFetMobileEpolicyService() {
		return fetMobileEpolicyService;
	}

	public void setFetMobileEpolicyService(FetMobileEpolicyService fetMobileEpolicyService) {
		this.fetMobileEpolicyService = fetMobileEpolicyService;
	}

	public TerminationNoticeService getTerminationNoticeService() {
		return terminationNoticeService;
	}

	public void setTerminationNoticeService(TerminationNoticeService terminationNoticeService) {
		this.terminationNoticeService = terminationNoticeService;
	}

	public XchgTerminationNoticeService getXchgTerminationNoticeService() {
		return xchgTerminationNoticeService;
	}

	public void setXchgTerminationNoticeService(XchgTerminationNoticeService xchgTerminationNoticeService) {
		this.xchgTerminationNoticeService = xchgTerminationNoticeService;
	}

	public ProposalFileService getProposalFileService() {
		return proposalFileService;
	}

	public void setProposalFileService(ProposalFileService proposalFileService) {
		this.proposalFileService = proposalFileService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public PrpcopycommissionService getPrpcopycommissionService() {
		return prpcopycommissionService;
	}

	public void setPrpcopycommissionService(PrpcopycommissionService prpcopycommissionService) {
		this.prpcopycommissionService = prpcopycommissionService;
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

	public FetPaidService getFetPaidService() {
		return fetPaidService;
	}

	public void setFetPaidService(FetPaidService fetPaidService) {
		this.fetPaidService = fetPaidService;
	}

	public FetPayableService getFetPayableService() {
		return fetPayableService;
	}

	public void setFetPayableService(FetPayableService fetPayableService) {
		this.fetPayableService = fetPayableService;
	}

	public ChubbCustomerEndorseService getChubbCustomerEndorseService() {
		return chubbCustomerEndorseService;
	}

	public void setChubbCustomerEndorseService(ChubbCustomerEndorseService chubbCustomerEndorseService) {
		this.chubbCustomerEndorseService = chubbCustomerEndorseService;
	}

	public ProposalInsufficientSmsService getProposalInsufficientSmsService() {
		return proposalInsufficientSmsService;
	}

	public void setProposalInsufficientSmsService(ProposalInsufficientSmsService proposalInsufficientSmsService) {
		this.proposalInsufficientSmsService = proposalInsufficientSmsService;
	}

	public FetCancelNotificationService getFetCancelNotificationService() {
		return fetCancelNotificationService;
	}

	public void setFetCancelNotificationService(FetCancelNotificationService fetCancelNotificationService) {
		this.fetCancelNotificationService = fetCancelNotificationService;
	}

	public CheckPolicyService getClientCheckPolicyService() {
		return clientCheckPolicyService;
	}

	public void setClientCheckPolicyService(CheckPolicyService clientCheckPolicyService) {
		this.clientCheckPolicyService = clientCheckPolicyService;
	}

	public FetMobilePolicySalesService getFetMobilePolicySalesService() {
		return fetMobilePolicySalesService;
	}

	public void setFetMobilePolicySalesService(FetMobilePolicySalesService fetMobilePolicySalesService) {
		this.fetMobilePolicySalesService = fetMobilePolicySalesService;
	}

	public FetAmlRecordService getFetAmlRecordService() {
		return fetAmlRecordService;
	}

	public void setFetAmlRecordService(FetAmlRecordService fetAmlRecordService) {
		this.fetAmlRecordService = fetAmlRecordService;
	}

	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 START */
	public PrpinsAgentService getClientPrpinsAgentService() {
		return clientPrpinsAgentService;
	}

	public void setClientPrpinsAgentService(PrpinsAgentService clientPrpinsAgentService) {
		this.clientPrpinsAgentService = clientPrpinsAgentService;
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END */
}

