package com.tlg.aps.bs.fetPolicyService.impl;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.util.FetTransactionIdGenUtil;
import com.tlg.aps.vo.BatchRepairHeaderVo;
import com.tlg.aps.vo.BatchRepairItemVo;
import com.tlg.aps.vo.BatchRepairVo;
import com.tlg.aps.vo.EpolicyPrpdrationclausekindVo;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.vo.mob.fetPolicy.response.ApplicantVo;
import com.tlg.aps.vo.mob.fetPolicy.response.ApplicationVo;
import com.tlg.aps.vo.mob.fetPolicy.response.CustomerVo;
import com.tlg.aps.vo.mob.fetPolicy.response.ExtColVo;
import com.tlg.aps.vo.mob.fetPolicy.response.ExtColsVo;
import com.tlg.aps.vo.mob.fetPolicy.response.InsuredVo;
import com.tlg.aps.vo.mob.fetPolicy.response.ProductVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.aps.webService.corePolicyService.client.GeneratePolicyService;
import com.tlg.aps.webService.corePolicyService.client.GeneratePolicyWebServiceService;
import com.tlg.aps.webService.corePolicyService.client.InsurantInfoVoByMobile;
import com.tlg.aps.webService.corePolicyService.client.MobileVo;
import com.tlg.aps.webService.corePolicyService.client.PolicyEndorseInfoListRequest;
import com.tlg.aps.webService.corePolicyService.client.PolicyEndorseInfoRequest;
import com.tlg.aps.webService.corePolicyService.client.PolicyInfoResultVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.AcFile;
import com.tlg.msSqlMob.entity.AccountFile;
import com.tlg.msSqlMob.entity.Applicant;
import com.tlg.msSqlMob.entity.ApplicantEndorse;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.entity.BatchRepairDetail;
import com.tlg.msSqlMob.entity.BatchRepairMain;
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
import com.tlg.msSqlMob.entity.ExtCol;
import com.tlg.msSqlMob.entity.FetAmlRecord;
import com.tlg.msSqlMob.entity.FetCancelNotification;
import com.tlg.msSqlMob.entity.FetMobileEpolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.entity.FetMobilePolicySales;
import com.tlg.msSqlMob.entity.FetPaid;
import com.tlg.msSqlMob.entity.FetPayable;
import com.tlg.msSqlMob.entity.FetPolicyImportError;
import com.tlg.msSqlMob.entity.Insured;
import com.tlg.msSqlMob.entity.InsuredEndorse;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;
import com.tlg.msSqlMob.entity.Product;
import com.tlg.msSqlMob.entity.ProductEndorse;
import com.tlg.msSqlMob.entity.ProposalFile;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.msSqlMob.service.AcFileService;
import com.tlg.msSqlMob.service.AccountFileService;
import com.tlg.msSqlMob.service.ApplicantEndorseService;
import com.tlg.msSqlMob.service.ApplicantService;
import com.tlg.msSqlMob.service.ApplicationService;
import com.tlg.msSqlMob.service.BatchRepairDetailService;
import com.tlg.msSqlMob.service.BatchRepairMainService;
import com.tlg.msSqlMob.service.ChFileService;
import com.tlg.msSqlMob.service.ChubbApplicantEndorseService;
import com.tlg.msSqlMob.service.ChubbCustomerEndorseService;
import com.tlg.msSqlMob.service.ChubbInsuredEndorseService;
import com.tlg.msSqlMob.service.ChubbProductEndorseService;
import com.tlg.msSqlMob.service.ChubbReturnCustomerService;
import com.tlg.msSqlMob.service.ClFileService;
import com.tlg.msSqlMob.service.CmFileService;
import com.tlg.msSqlMob.service.CustomerEndorseService;
import com.tlg.msSqlMob.service.CustomerService;
import com.tlg.msSqlMob.service.ExtColService;
import com.tlg.msSqlMob.service.FetAmlRecordService;
import com.tlg.msSqlMob.service.FetCancelNotificationService;
import com.tlg.msSqlMob.service.FetMobileEpolicyService;
import com.tlg.msSqlMob.service.FetMobilePolicyDeviceService;
import com.tlg.msSqlMob.service.FetMobilePolicyInsurantInfoService;
import com.tlg.msSqlMob.service.FetMobilePolicySalesService;
import com.tlg.msSqlMob.service.FetMobilePolicyService;
import com.tlg.msSqlMob.service.FetPaidService;
import com.tlg.msSqlMob.service.FetPayableService;
import com.tlg.msSqlMob.service.FetPolicyImportErrorService;
import com.tlg.msSqlMob.service.InsuredEndorseService;
import com.tlg.msSqlMob.service.InsuredService;
import com.tlg.msSqlMob.service.MobileInsBatchInfoService;
import com.tlg.msSqlMob.service.ProductEndorseService;
import com.tlg.msSqlMob.service.ProductService;
import com.tlg.msSqlMob.service.ProposalFileService;
import com.tlg.msSqlMob.service.TerminationNoticeService;
import com.tlg.prpins.service.PrpdclausereportService;
import com.tlg.prpins.service.PrpdrationclausekindService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GIGO;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.LiaMiEpolicy;
import com.tlg.xchg.entity.MiEpolicysms;
import com.tlg.xchg.entity.Newepolicymain;
import com.tlg.xchg.service.LiaMiEpolicyService;
import com.tlg.xchg.service.MiEpolicysmsService;
import com.tlg.xchg.service.NewepolicymainService;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@Transactional(value="msSqlMobTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FetPolicyServiceImpl implements FetPolicyService {
	
	private static final Logger logger = Logger.getLogger(FetPolicyServiceImpl.class);
	private MobileInsBatchInfoService mobileInsBatchInfoService;
	private CustomerService customerService;
	private ApplicationService applicationService;
	private ApplicantService applicantService;
	private InsuredService insuredService;
	private ExtColService extColService;
	private ProductService productService;
	private FetPolicyImportErrorService fetPolicyImportErrorService;
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 */
	private ProposalFileService proposalFileService;
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
	private ChubbReturnCustomerService chubbReturnCustomerService;
	/** mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新 */
	private FetMobilePolicyService fetMobilePolicyService;
	private FetMobilePolicyInsurantInfoService fetMobilePolicyInsurantInfoService;
	private FetMobilePolicyDeviceService fetMobilePolicyDeviceService;
	
	/** mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 */
	private CustomerEndorseService customerEndorseService;
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	private AcFileService acFileService;
	private ChFileService chFileService;
	private ClFileService clFileService;
	private CmFileService cmFileService;
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	private LiaMiEpolicyService liaMiEpolicyService;
	private NewepolicymainService newepolicymainService;
	private FetMobileEpolicyService fetMobileEpolicyService;
	private MiEpolicysmsService miEpolicysmsService;
	
	/**mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表*/
	private AccountFileService accountFileService;
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書*/
	private TerminationNoticeService terminationNoticeService;
	
	private BatchRepairMainService batchRepairMainService;
	private BatchRepairDetailService batchRepairDetailService;
	
	private PrpdrationclausekindService prpdrationclausekindService;
	private PrpdclausereportService prpdclausereportService;
	
	private FetPaidService fetPaidService;
	private FetPayableService fetPayableService;
	
	private ConfigUtil configUtil;
	
	private ApplicantEndorseService applicantEndorseService;
	private InsuredEndorseService insuredEndorseService;
	private ProductEndorseService productEndorseService;
	
	private ChubbApplicantEndorseService chubbApplicantEndorseService;
	private ChubbCustomerEndorseService chubbCustomerEndorseService;
	private ChubbInsuredEndorseService chubbInsuredEndorseService;
	private ChubbProductEndorseService chubbProductEndorseService;
	
	private FetCancelNotificationService fetCancelNotificationService;
	
	private FetMobilePolicySalesService fetMobilePolicySalesService;
	
	private FetAmlRecordService fetAmlRecordService;
	
	@Override
	public MobileInsBatchInfo insertMobileInsBatchInfo(Date executeTime) throws SystemException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		MobileInsBatchInfo entity = new MobileInsBatchInfo();
		entity.setBatchDate(sdf.format(executeTime));
		entity.setBatchStatus("00");
		entity.setBatchCheckStatus("N");
		entity.setCurrentRecord("0");
		entity.setCreatedBy("system");
		entity.setCreatedTime(executeTime);
		Result result = mobileInsBatchInfoService.insertMobileInsBatchInfo(entity);
		entity = (MobileInsBatchInfo) result.getResObject();
		return entity;
	}
	
	@Override
	public Result updateMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException, Exception {
		mobileInsBatchInfo.setModifiedBy("system");
		mobileInsBatchInfo.setModifiedTime(new Date());
		return mobileInsBatchInfoService.updateMobileInsBatchInfo(mobileInsBatchInfo);
	}
	/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題  START*/
	@Override
	public void insertData(CustomerVo customerVo, List<CustomerVo> cancelList, EnumMobileDataSrc enumDataSrc) throws SystemException, Exception {
		Date now = new Date();
		String transactionId = FetTransactionIdGenUtil.getTransactionId("", 6);
		try {
			// insert Customer
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerVo, customer);
			customer.setTransactionId(transactionId);
			customer.setBatchStatus("01");
			customer.setBatchDate(new SimpleDateFormat("yyyyMMdd").format(now));
			customer.setCreatedBy("system");
			customer.setCreatedTime(now);
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程  START*/
			String dataSrc = enumDataSrc.getCode();
			customer.setDataSrc(dataSrc);
			
			// insert Application
			ApplicationVo applicationVo = customerVo.getApplication();
			Application application = new Application();
			BeanUtils.copyProperties(applicationVo, application);
			application.setDataStatus("INIT");
			application.setTransactionId(transactionId);
			application.setContractId(customerVo.getContractId());
			application.setProposalFileCheck("N");
			boolean dirtyCancel = "CANCEL".equals(customer.getTransactionType()) && "99999999".equals(application.getApplicationId());
			if (dirtyCancel) {
				for (CustomerVo c : cancelList) {
					if(application.getContractId().equals(c.getContractId())) {// 如果是重複的中途退保資料，RptBatchNo上註記X 例: 202507160600X
						String dirtyMark = customerVo.getRptBatchNo() + "X";
						customerVo.setRptBatchNo(dirtyMark);
						customer.setRptBatchNo(dirtyMark);
						customer.setBatchStatus("XX");// 註記為髒資料，不處理
						application.setDataStatus("DEPRECATE");
						application.setProposalFileCheck("X");
					}
				}
			}
			application.setRptBatchNo(customerVo.getRptBatchNo());
			application.setCreatedBy("system");
			application.setCreatedTime(now);
			application.setDataSrc(dataSrc);
			logger.info(customer.toString());
			customerService.insertCustomer(customer);
			logger.info(application.toString());
			applicationService.insertApplication(application);
			/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題 END*/

			// insert Applicant
			ApplicantVo applicantVo = applicationVo.getApplicant();
			Applicant applicant = new Applicant();
			BeanUtils.copyProperties(applicantVo, applicant);
			applicant.setTransactionId(transactionId);
			applicant.setRptBatchNo(customerVo.getRptBatchNo());
			applicant.setContractId(customerVo.getContractId());
			applicant.setApplicationId(applicationVo.getApplicationId());
			applicant.setCreatedBy("system");
			applicant.setCreatedTime(now);
			applicant.setDataSrc(dataSrc);
			logger.info(applicant.toString());
			applicantService.insertApplicant(applicant);

			// insert Insured
			InsuredVo insuredVo = applicationVo.getInsured();
			Insured insured = new Insured();
			BeanUtils.copyProperties(insuredVo, insured);
			insured.setTransactionId(transactionId);
			insured.setRptBatchNo(customerVo.getRptBatchNo());
			insured.setContractId(customerVo.getContractId());
			insured.setApplicationId(applicationVo.getApplicationId());
			insured.setCreatedBy("system");
			insured.setCreatedTime(now);
			insured.setDataSrc(dataSrc);
			logger.info(insured.toString());
			insuredService.insertInsured(insured);

			//insert ExtCol
			ExtColsVo extColsVo = applicationVo.getExtCols();
			List<ExtColVo> extColVoList = extColsVo.getExtCol();
			for (ExtColVo extColVo : extColVoList) {
				ExtCol extCol = new ExtCol();
				BeanUtils.copyProperties(extColVo, extCol);
				extCol.setTransactionId(transactionId);
				extCol.setRptBatchNo(customerVo.getRptBatchNo());
				extCol.setContractId(customerVo.getContractId());
				extCol.setApplicationId(applicationVo.getApplicationId());
				extCol.setObjectKey(extColVo.getKey());
				extCol.setCreatedBy("system");
				extCol.setCreatedTime(now);
				logger.info(extCol.toString());
				extColService.insertExtCol(extCol);
			}
			
			//insert Product
			ProductVo productVo = customerVo.getProduct();
			Product product = new Product();
			BeanUtils.copyProperties(productVo, product);
			product.setTransactionId(transactionId);
			product.setRptBatchNo(customerVo.getRptBatchNo());
			product.setContractId(customerVo.getContractId());
			product.setApplicationId(applicationVo.getApplicationId());
			product.setCreatedBy("system");
			product.setCreatedTime(now);
			product.setDataSrc(dataSrc);
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
			logger.info(product.toString());
			productService.insertProduct(product);
			
		}catch (SystemException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Result insertFetPolicyImportError(CustomerVo data, EnumMobileDataSrc enumDataSrc) throws SystemException, Exception {
		Date now = new Date();
		String transactionId = FetTransactionIdGenUtil.getTransactionId("", 6);
		FetPolicyImportError entity = new FetPolicyImportError();
		entity.setTransactionId(transactionId);
		entity.setBatchDate(new SimpleDateFormat("yyyyMMdd").format(now));
		entity.setRptBatchNo(data.getRptBatchNo());
		entity.setContractId(data.getContractId());
		entity.setDataSrc(enumDataSrc.getCode());
		ApplicationVo applicationVo = data.getApplication();
		entity.setApplicationId(applicationVo.getApplicationId());
		entity.setCreatedBy("system");
		entity.setCreatedTime(now);
		logger.info(entity.toString());
		return fetPolicyImportErrorService.insertFetPolicyImportError(entity);
	}
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 start */	
	@Override
	public Result updateCustomer(Customer data) throws SystemException, Exception {
		return customerService.updateCustomer(data);
	}
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 end */
	
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 start */
	@Override
	public void insertFileData(List<String> filenames, File zipFile) throws SystemException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String zipFilename = zipFile.getName();
		String[] zipfilenameArray = zipFilename.split("_");
		String rptBatchNo = zipfilenameArray[2];
		for(String filename:filenames) {
			String transactionId = FetTransactionIdGenUtil.getTransactionId("PF", 6);
			ProposalFile entity = new ProposalFile();
			entity.setTransactionId(transactionId);
			entity.setZipFileName(zipFilename);
			entity.setRptBatchNo(rptBatchNo);
			entity.setFileName(filename);
			entity.setDownloadDate(sdf.format(now));
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			// TODO entity.setApplicationId(filename.split("_")[2].replace(".pdf", ""));// ex: 20240413_218_203356668687353393.pdf
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  將 PROPOSAL_FILE 的 application_id 補上 START*/
			if (StringUtils.isNotBlank(filename)) {
			    String[] parts = filename.split("_");
			    if (parts.length > 2 && StringUtils.isNotBlank(parts[2])) {
			        String applicationId = parts[2];
			        if (applicationId.endsWith(".pdf")) {
			            applicationId = applicationId.substring(0, applicationId.length() - 4);
			        }
			        entity.setApplicationId(applicationId);
			    } 
			} 
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程  將 PROPOSAL_FILE 的 application_id 補上 END*/
			proposalFileService.insertProposalFile(entity);
		}
	}
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 end */
	
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 start */
	@Override
	public void updatePolicyDataByChubbReturn(ChubbReturnCustomer chubbReturnCustomer, Application application) throws SystemException, Exception {
			application.setPolicyNo(chubbReturnCustomer.getPolicyNo());
			application.setEndorseNo(chubbReturnCustomer.getEndorseNo());
			application.setEndorseType(chubbReturnCustomer.getEndorseType());
			application.setEndorseContent(chubbReturnCustomer.getEndorseContent());
			application.setStartDate(chubbReturnCustomer.getStartDate());
			application.setEndDate(chubbReturnCustomer.getEndDate());
			application.setProjectCode(chubbReturnCustomer.getProjectCode());
			application.setPremium(chubbReturnCustomer.getPremium());
			application.setPayPeriod(chubbReturnCustomer.getPayPeriod());
			application.setPayPeriodStartDate(chubbReturnCustomer.getPayPeriodStartDate());
			application.setPayPeriodEndDate(chubbReturnCustomer.getPayPeriodEndDate());
			application.setChubbReturnStatus(chubbReturnCustomer.getReturnStatus());
			application.setProposalFileName(chubbReturnCustomer.getProposalFileName());
			application.setChubbReturnMsg(chubbReturnCustomer.getReturnMsg());
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
			if (EnumMobileDataSrc.ESTORE.getCode().equals(application.getDataSrc()) 
					&& StringUtils.isBlank(application.getApplicationStatus())) {
				application.setApplicationStatus("G10");// G10:初始狀態，待回傳保單號給數開
			}
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
			String returnStatus = chubbReturnCustomer.getReturnStatus();
			if("1".equals(returnStatus)) {
				application.setDataStatus("READY");
			}else {
				application.setDataStatus("INIT");
			}
			Result result = applicationService.updateApplication(application);
			if(result.getResObject()!=null) {
				chubbReturnCustomer.setDataStatus("01");
				chubbReturnCustomerService.updateChubbReturnCustomer(chubbReturnCustomer);
			}
			/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 START*/
			Map<String, String> amlparams = new HashMap<String, String>();
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
			if(StringUtils.isNotBlank(chubbReturnCustomer.getContractId())) {
				amlparams.put("contractId", chubbReturnCustomer.getContractId());
			}else if(StringUtils.isNotBlank(chubbReturnCustomer.getTxId())) {
				amlparams.put("txId", chubbReturnCustomer.getTxId());
			}
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
			//取得尚無保單號的fetAmlRecord
			amlparams.put("policyNoIsNull", "Y");
			amlparams.put("orderByCreatedtimeDesc", "Y");
			Result amlResult = fetAmlRecordService.findFetAmlRecordByParams(amlparams);
			if(amlResult.getResObject() != null) {
				ArrayList<FetAmlRecord> fetAmlRecordList = (ArrayList<FetAmlRecord>)amlResult.getResObject();
				FetAmlRecord fetAmlRecord = fetAmlRecordList.get(0);
				fetAmlRecord.setPolicyNo(chubbReturnCustomer.getPolicyNo());// 補上保單號
				/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 匯入核心排程發信告知結果、修正手機險排程時間、修正保批單號資料型態、洗錢條件檢核補上批單號、遠傳資料批次下載使用者改ce035 */
				fetAmlRecord.setEndorseNo(chubbReturnCustomer.getEndorseNo());// 補上批單號
				fetAmlRecordService.updateFetAmlRecord(fetAmlRecord);
			}
			/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 END*/
	}
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 end */
	
	/** mantis：MOB0011，處理人員：BI086，需求單編號：OB0011 呼叫web service後結果狀態更新  start */
	@Override
	public Result updateFetMobilePolicy(FetMobilePolicy fetMobilePolicy)
			throws SystemException, Exception {
		return this.fetMobilePolicyService.updateFetMobilePolicy(fetMobilePolicy);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFetMobilePolicyByParams(Map params)
			throws SystemException, Exception {
		return this.fetMobilePolicyService.findFetMobilePolicyByParams(params);
	}
	
	@Override
	public Result findFetMobilePolicyDeviceByUK(String transactionId)
			throws SystemException, Exception {
		return this.fetMobilePolicyDeviceService.findFetMobilePolicyDeviceByUK(transactionId);
	}

	@Override
	public Result findFetMobilePolicyInsurantInfoByUK(String transactionId)
			throws SystemException, Exception {
		return this.fetMobilePolicyInsurantInfoService.findFetMobilePolicyInsurantInfoByUK(transactionId);
	}
	
	/** mantis：MOB0011，處理人員：BI086，需求單編號：OB0011 呼叫web service後結果狀態更新 end */
	
	
	/**mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業  start */
	@Override
	public Result updateCustomerEndorse(CustomerEndorse customerEndorse)
			throws SystemException, Exception {
		return this.customerEndorseService.updateCustomerEndorse(customerEndorse);
	}
	
	@Override
	public Result findCustomerEndorseByUK(String transactionId)
			throws SystemException, Exception {
		return this.customerEndorseService.findCustomerEndorseByUK(transactionId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFetMobilePolicy(Map params) throws SystemException,
			Exception {
		return this.fetMobilePolicyService.countFetMobilePolicy(params);
	}
	/**mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業  end*/
	
	/**mantis：MOB0013，處理人員：BI086，需求單編號：MOB0013線下批單資料處理作業  start */

	/**  
	 *	mantis：MOB0013，處理人員：BI086，需求單編號：MOB0013線下批單資料處理作業
	 */
	@Override
	public boolean insertEndorseData(FetMobilePolicy fetMobilePolicy,
			FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo,
			FetMobilePolicyDevice fetMobilePolicyDevice)
			throws SystemException, Exception {
		try{
			if(fetMobilePolicy == null || fetMobilePolicyInsurantInfo == null || fetMobilePolicyDevice == null){
				String error = "";
				if(fetMobilePolicy == null){
					error = StringUtil.appendStr(error, "FetMobilePolicy", "，");
				}
				if(fetMobilePolicyInsurantInfo == null){
					error = StringUtil.appendStr(error, "FetMobilePolicyInsurantInfo", "，");
				}
				if(fetMobilePolicyDevice == null){
					error = StringUtil.appendStr(error, "FetMobilePolicyDevice", "，");
				}
				error = "缺少" + error;
				throw new SystemException(error);
			}
			
			Result result = this.fetMobilePolicyService.insertFetMobilePolicy(fetMobilePolicy);
			if(result.getResObject() == null){
				throw new SystemException("新增FetMobilePolicy失敗");
			}
			result = this.fetMobilePolicyInsurantInfoService.insertFetMobilePolicyInsurantInfo(fetMobilePolicyInsurantInfo);
			if(result.getResObject() == null){
				throw new SystemException("新增FetMobilePolicyInsurantInfo失敗");
			}
			result = this.fetMobilePolicyDeviceService.insertFetMobilePolicyDevice(fetMobilePolicyDevice);
			if(result.getResObject() == null){
				throw new SystemException("新增FetMobilePolicyDevice失敗");
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**mantis：MOB0013，處理人員：BI086，需求單編號：MOB0013線下批單資料處理作業  end */
	
	/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 start*/
	public void insertDataToFetMobilePolicy(FetMobilePolicy fetMobilePolicy,
			FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo, FetMobilePolicyDevice fetMobilePolicyDevice)
			throws SystemException, Exception {
		
		fetMobilePolicyService.insertFetMobilePolicy(fetMobilePolicy);
		fetMobilePolicyInsurantInfoService.insertFetMobilePolicyInsurantInfo(fetMobilePolicyInsurantInfo);
		fetMobilePolicyDeviceService.insertFetMobilePolicyDevice(fetMobilePolicyDevice);
	}
	
	public Result updateApplication(Application application) throws SystemException, Exception{
		return this.applicationService.updateApplication(application);
	}
	/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 end*/
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	@Override
	public boolean batchInsertAcFile(List<AcFile> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(AcFile entity : lists) {
				temp = acFileService.insertAcFile(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert AC_FILE fail");
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean batchInsertChFile(List<ChFile> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ChFile entity : lists) {
				temp = chFileService.insertChFile(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert CH_FILE fail");
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean batchInsertClFile(List<ClFile> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ClFile entity : lists) {
				temp = clFileService.insertClFile(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert CL_FILE fail");
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean batchInsertCmFile(List<CmFile> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(CmFile entity : lists) {
				temp = cmFileService.insertCmFile(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert CM_FILE fail");
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateFetMobilePolicyStatus(String policyNo, String endorseNo, String premium, String installments) throws SystemException, Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", "HOLD");
		boolean checkEndorse = false;
		if(null == policyNo || policyNo.length() <= 0) {
			return false;
		} else {
			params.put("policyNo", policyNo);
		}
		
		if(null != endorseNo && endorseNo.length() > 0) {
			params.put("endorseNo", endorseNo);
			checkEndorse = true;
		}
		
		if(checkEndorse) {
			params.put("dataType", "2");
		} else {
			params.put("dataType", "1");
		}
		
		Result temp = fetMobilePolicyService.findFetMobilePolicyByParams(params);
		if(temp != null && temp.getResObject() != null) {
			List<FetMobilePolicy> searchResult = (List<FetMobilePolicy>)temp.getResObject();
			if(searchResult.size() > 1) {
				return false;
			}
			FetMobilePolicy entity = searchResult.get(0);
			/**
			 * 20231124:BJ016:依據AC檔上面的保費為主，更新至FetMobilePolicyInsurantInfo-----START
			 * */
			Result tempFMPII = this.fetMobilePolicyInsurantInfoService.findFetMobilePolicyInsurantInfoByUK(entity.getTransactionId());
			if(tempFMPII == null || tempFMPII.getResObject() == null) {
				return false;
			}
			FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo = (FetMobilePolicyInsurantInfo)tempFMPII.getResObject();
			fetMobilePolicyInsurantInfo.setProjectCodePremium(premium);
			tempFMPII = this.fetMobilePolicyInsurantInfoService.updateFetMobilePolicyInsurantInfo(fetMobilePolicyInsurantInfo);
			if(tempFMPII != null && !"PUB120".equals(tempFMPII.getMessage().getOpCode())) {
				throw new Exception("update  Fet_Mobile_Policy_InsurantInfo fail......");
			}
			/**
			 * 20231124:BJ016:依據AC檔上面的保費為主，更新至FetMobilePolicyInsurantInfo-----END
			 * */
			
			/**
			 * 20231225:BJ016:依據遠傳拆帳檔上的繳費開始及繳費結束日更新至FetMobilePolicy的PlanStartDate及PlanEndDate-----START
			 * */
			Map<String,Object> fpParams = new HashMap<String,Object>();
			fpParams.put("pd001", entity.getContractId());
			fpParams.put("pd005", installments);
			fpParams.put("checkAccountTimeIsNull", "Y");
			Result fpResult = fetPaidService.findFetPaidByParams(fpParams);
			if(fpResult == null || fpResult.getResObject() == null) {
				throw new Exception("there is no FetPaid data.....");
			}
			List<FetPaid> fpSearchResult = (List<FetPaid>)fpResult.getResObject();
//			if(fpSearchResult.size() > 1) {
//				throw new Exception("there are more then one FetPaid data.....");
//			}
			FetPaid fetPaid = fpSearchResult.get(0);
			String planStartDate = "";
			if(fetPaid != null && fetPaid.getPd003() != null && fetPaid.getPd003().trim().length() >= 0) {
				planStartDate = fetPaid.getPd003().trim().replaceAll("\"", "");
				planStartDate = planStartDate.replaceAll("/", "");
				if(planStartDate.length() != 8) {//如果解析出來的日期不是8碼，yyyyMMdd表示日期可能有誤，因此將日期清空
					planStartDate = "";
				}
			}
			String planEndDate = "";
			if(fetPaid != null && fetPaid.getPd004() != null && fetPaid.getPd004().trim().length() >= 0) {
				planEndDate = fetPaid.getPd004().trim().replaceAll("\"", "");
				planEndDate = planEndDate.replaceAll("/", "");
				if(planEndDate.length() != 8) {//如果解析出來的日期不是8碼，yyyyMMdd表示日期可能有誤，因此將日期清空
					planEndDate = "";
				}
			}
			if(planStartDate.length() <= 0 || planEndDate.length() <= 0) {
				throw new Exception("FetPaid pd001=" + fetPaid.getPd001() + ", pd003 or pd004 value error......");
			}
			entity.setPlanStartDate(planStartDate);
			entity.setPlanEndDate(planEndDate);
			
			//將處理過的資料的CheckAccountTime欄位壓上當下時間表示資料已處理過
			fetPaid.setCheckAccountTime(new Date());
			fetPaid.setModifiedBy("system");
			fetPaid.setModifiedTime(new Date());
			temp = fetPaidService.updateFetPaid(fetPaid);
			if(temp != null && !"PUB120".equals(temp.getMessage().getOpCode())) {
				throw new Exception("update Fet_Paid fail......");
			}
			/**
			 * 20231225:BJ016:依據遠傳拆帳檔上的繳費開始及繳費結束日更新至FetMobilePolicy的PlanStartDate及PlanEndDate-----END
			 * */
			
			entity.setStatus("READY_WAIT");
			entity.setModifiedBy("system");
			entity.setModifiedTime(new Date());
			temp = fetMobilePolicyService.updateFetMobilePolicy(entity);
			if(temp != null && !"PUB120".equals(temp.getMessage().getOpCode())) {
				throw new Exception("update  Fet_Mobile_Policy fail......");
			}
			
		}
		
		return true;
	}
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單-----START*/
	@Override
	public boolean insertFetMobileEpolicy(String transactionId, String policyNo, String status) throws SystemException, Exception {
		Date now = new Date();
		String oid = FetTransactionIdGenUtil.getTransactionId("FME", 6);
		FetMobileEpolicy entity = new FetMobileEpolicy();
		entity.setOid(oid);;
		entity.setTransactionId(transactionId);
		entity.setPolicyNo(policyNo);
		entity.setStatus(status);
		entity.setCreatedBy("system");
		entity.setCreatedTime(now);
		logger.info(entity.toString());
		Result result = fetMobileEpolicyService.insertFetMobileEpolicy(entity);
		if(result == null || result.getResObject() == null) {
			throw new SystemException("新增FetMobileEpolicy失敗");
		}
		return true;
	}
	/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單-----END*/
	
	@Override
	public boolean genMobileEpolicyData(String transactionId) throws SystemException, Exception {
		Customer customer = null;
		Application application = null;
		Applicant applicant = null;
		Insured insured = null;
		Product product = null;
		List<EpolicyPrpdrationclausekindVo> epolicyPrpdrationclausekindList = null;
		String reportNo = "";
		Result result = customerService.findCustomerByUK(transactionId);
		if (result.getResObject() == null) {
			return false;
		}
		customer = (Customer) result.getResObject();
		
		result = applicationService.findApplicationByUK(transactionId);
		if (result.getResObject() == null) {
			return false;
		}
		application = (Application) result.getResObject();

		result = applicantService.findApplicantByUK(transactionId);
		if (result.getResObject() == null) {
			return false;
		}
		applicant = (Applicant) result.getResObject();

		result = insuredService.findInsuredByUK(transactionId);
		if (result.getResObject() == null) {
			return false;
		}
		insured = (Insured) result.getResObject();

		result = productService.findProductByUK(transactionId);
		if (result.getResObject() == null) {
			return false;
		}
		product = (Product) result.getResObject();
		
		Map params = new HashMap();
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  START*/
		String projectCode = application.getProjectCode();
		params.put("rationcode", projectCode);
		params.put("riskcode", "MI");
		result = prpdrationclausekindService.selectForEpolicy(params);
		if (result.getResObject() == null) {
			return false;
		}
		epolicyPrpdrationclausekindList = (List<EpolicyPrpdrationclausekindVo>)result.getResObject();
		
		params.clear();
//		params.put("clausecode", "MI");
		params.put("clausecode", projectCode.substring(0, 2));
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  END*/
		result = prpdclausereportService.selectForEpolicy(params);
		if (result.getResObject() == null) {
			return false;
		}
		reportNo = (String)result.getResObject();
		
		LiaMiEpolicy liaMiEpolicy = this.mappingLiaMiEpolicy(customer, application, applicant, product, epolicyPrpdrationclausekindList, reportNo);
		liaMiEpolicyService.insertLiaMiEpolicy(liaMiEpolicy);
		Newepolicymain newepolicymain = this.mappingNewepolicymain(application, applicant, insured, epolicyPrpdrationclausekindList, reportNo);
		newepolicymainService.insertNewepolicymain(newepolicymain);
		MiEpolicysms miEpolicysms = this.mappingMiEpolicysms(application, applicant);
		miEpolicysmsService.insertMiEpolicysms(miEpolicysms);
		return true;
	}
	
	/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
	@Override
	public boolean batchInsertAccountFile(List<AccountFile> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(AccountFile entity : lists) {
				temp = accountFileService.insertAccountFile(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert ACCOUNT_FILE fail");
				}
			}
		}
		return true;
	}
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
	@Override
	public boolean batchInsertTerminationNotice(List<TerminationNotice> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			String transactionId = "";
			for(TerminationNotice entity : lists) {
				transactionId = FetTransactionIdGenUtil.getTransactionId("TN", 6);
				entity.setTransactionId(transactionId);
				entity.setIsSend("N");
				entity.setCreatedBy("system");
				entity.setCreatedTime(new Date());
				temp = terminationNoticeService.insertTerminationNotice(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert Termination_Notice fail");
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean batchInsertRepairXmlData(BatchRepairVo batchRepairVo) throws SystemException, Exception {
		if(batchRepairVo != null) {
			BatchRepairHeaderVo header = batchRepairVo.getHeader();
			if(header == null) {
				return false;
			}
			List<BatchRepairItemVo> listItem = batchRepairVo.getItems();
			if(listItem == null || listItem.size() <= 0) {
				return false;
			}
			BatchRepairMain batchRepairMain = new BatchRepairMain();
			BeanUtils.copyProperties(header, batchRepairMain);
			Date now = new Date();
			String batchNo = new SimpleDateFormat("yyyyMMddHHmm").format(now);
			batchRepairMain.setBatchNo(batchNo);
			batchRepairMain.setCreatedBy("system");
			batchRepairMain.setCreatedTime(new Date());
			Result result = this.batchRepairMainService.insertBatchRepairMain(batchRepairMain);
			if(result != null && !"PUB100".equals(result.getMessage().getOpCode())) {
				throw new Exception("Insert BatchRepairMain fail");
			}
			
			BatchRepairDetail batchRepairDetail;
			for(BatchRepairItemVo batchRepairItemVo : listItem) {
				batchRepairDetail = new BatchRepairDetail();
				BeanUtils.copyProperties(batchRepairItemVo, batchRepairDetail);
				batchRepairDetail.setBatchNo(batchNo);
				batchRepairDetail.setTransactionId(FetTransactionIdGenUtil.getTransactionId("BRD", 6));
				batchRepairDetail.setCreatedBy("system");
				batchRepairDetail.setCreatedTime(new Date());
				result = this.batchRepairDetailService.insertBatchRepairDetail(batchRepairDetail);
				if(result != null && !"PUB100".equals(result.getMessage().getOpCode())) {
					throw new Exception("Insert BatchRepairDetail fail");
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean proposalFileCheck(String transactionId, String userId) throws SystemException, Exception {
		logger.info("Function proposalFileCheck start : transactionId = " + transactionId + "----------");
		Result result = applicationService.findApplicationByUK(transactionId);
		Application application = null;
		if(result != null && result.getResObject() != null) {
			application = (Application)result.getResObject();
			if(application != null) {
				if(!"1".equals(application.getChubbReturnStatus())) {
					logger.info("Function proposalFileCheck : transactionId = " + transactionId + ", ChubbReturnStatus is not 1");
					return false;
				}
				if("Y".equalsIgnoreCase(application.getProposalFileCheck())) {
					logger.info("Function proposalFileCheck : transactionId = " + transactionId + ", ProposalFileCheck is Y");
				} else {
					application.setProposalFileCheck("Y");
					application.setProposalFileCheckUser(userId);
					application.setModifiedBy(userId);
					application.setModifiedTime(new Date());
					applicationService.updateApplication(application);
				}

				Map<String,String> params = new HashMap<String,String>();
				params.put("status", "HOLD");
				params.put("transactionId", transactionId);
				result = fetMobileEpolicyService.findFetMobileEpolicyByParams(params);
				if(result != null && result.getResObject() != null) {
					List<FetMobileEpolicy> searchResult = (List<FetMobileEpolicy>)result.getResObject(); 
					FetMobileEpolicy fetMobileEpolicy = searchResult.get(0);
					fetMobileEpolicy.setStatus("WAIT");
					fetMobileEpolicy.setModifiedBy(userId);
					fetMobileEpolicy.setModifiedTime(new Date());
					fetMobileEpolicyService.updateFetMobileEpolicy(fetMobileEpolicy);
				} else {
					logger.info("Function proposalFileCheck : transactionId = " + transactionId + ", FetMobileEpolicy status=HOLD not found..........");
				}
			}
		}
		logger.info("Function proposalFileCheck end : transactionId = " + transactionId + "----------");
		return true;
	}
	
	@Override
	public boolean batchInsertFetPaid(List<FetPaid> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(FetPaid entity : lists) {
				temp = fetPaidService.insertFetPaid(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert FetPaid fail");
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchInsertFetPayable(List<FetPayable> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(FetPayable entity : lists) {
				temp = fetPayableService.insertFetPayable(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert FetPayable fail");
				}
			}
		}
		return true;
	}
	
	@Override/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
	public String importMiPolicyToCore(FetMobilePolicy fetMobilePolicy, PrpinsAgentRespVo estorePrpinsAgentRespVo) throws SystemException, Exception {
		logger.info("importMiPolicyToCore start......");
		logger.info("importMiPolicyToCore entity TransactionId : " + fetMobilePolicy.getTransactionId());
		String result = "";//組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註

		PolicyEndorseInfoListRequest webServiceRequestObj = new PolicyEndorseInfoListRequest();
		String transactionId = fetMobilePolicy.getTransactionId();
		String policyNo = fetMobilePolicy.getPolicyNo();
		String endorseNo = fetMobilePolicy.getEndorseNo();

		FetMobilePolicyDevice fetMobilePolicyDevice = null;
		FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo = null;
		FetMobilePolicySales fetMobilePolicySales = null;
		//找FetMobilePolicyDevice
		Result result1 = this.findFetMobilePolicyDeviceByUK(fetMobilePolicy.getTransactionId());
		if(result1.getResObject() == null){
			result = transactionId + "," + policyNo + "," + endorseNo + "," + "缺少FindFetMobilePolicyDevice資料";
			logger.info(result);
			return result;
		}
		fetMobilePolicyDevice = (FetMobilePolicyDevice)result1.getResObject();
		//找FetMobilePolicyInsurantInfoService
		result1 = this.findFetMobilePolicyInsurantInfoByUK(transactionId);
		if(result1.getResObject() == null){
			result = transactionId + "," + policyNo + "," + endorseNo + "," + "缺少FetMobilePolicyInsurantInfo資料";
			logger.info(result);
			return result;
		}
		fetMobilePolicyInsurantInfo = (FetMobilePolicyInsurantInfo)result1.getResObject();
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
		String dataSrc = fetMobilePolicy.getDataSrc();
		if(EnumMobileDataSrc.FEPIA.getCode().equals(dataSrc)) {// 當資料來自遠傳保代才需抓業務員
			result1 = this.fetMobilePolicySalesService.findFetMobilePolicySalesByUK(transactionId);
			if(result1.getResObject() == null){
				if(fetMobilePolicy != null && "2".equals(fetMobilePolicy.getDataType())) {
					//如果是遠傳送的批單件，就有跟保批單資料檢核是就有作業務員的資料了
					//如果是分期付款的批單件，是沒有作業務員檢核的，所以這邊要去抓當初保單進件時作的業務員資料來用
					Map<String, String> params = new HashMap<String, String>();
					params.put("policyNo", policyNo);
					params.put("dataType", "1");
					Result tempResult = this.fetMobilePolicyService.findFetMobilePolicyByParams(params);
					if(tempResult.getResObject() == null){
						result = transactionId + "," + policyNo + "," + endorseNo + "," + "缺少FetMobilePolicy原始保單進件資料";
						logger.info(result);
						return result;
					}
					ArrayList<FetMobilePolicy> fetMobilePolicyList = (ArrayList<FetMobilePolicy>)tempResult.getResObject();
					FetMobilePolicy originPolicy = fetMobilePolicyList.get(0);
					result1 = this.fetMobilePolicySalesService.findFetMobilePolicySalesByUK(originPolicy.getTransactionId());
					if(result1.getResObject() == null){
						result = transactionId + "," + policyNo + "," + endorseNo + "," + "缺少原始保單進件FetMobilePolicySales資料";
						logger.info(result);
						return result;
					}
				} else {
					result = transactionId + "," + policyNo + "," + endorseNo + "," + "缺少FetMobilePolicySales資料";
					logger.info(result);
					return result;
				}
			}
			fetMobilePolicySales = (FetMobilePolicySales)result1.getResObject();
		}
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
		try{
			//組web service所需資料
			//行動裝置資料
			MobileVo mobileVo = this.getMobileVo(fetMobilePolicyDevice);
			//被保人清單物件
			InsurantInfoVoByMobile insurant = this.getInsurantInfoVoByMobile(fetMobilePolicyInsurantInfo, mobileVo);
			//主檔資料
			PolicyEndorseInfoRequest main = this.getPolicyEndorseInfoRequest(fetMobilePolicy, insurant, fetMobilePolicySales, estorePrpinsAgentRespVo);
			
			webServiceRequestObj.getPolicyEndorseInfoRequests().add(main);
		}catch (Exception e) {
			logger.error("importMiPolicyToCore 產生核心資料時發生錯誤 : ", e);
			result = transactionId + "," + policyNo + "," + endorseNo + "," + "產生資料時發生錯誤";
			return result;
		}
		String returnContent = "";
		try{
			//呼叫web Service
			returnContent = this.callWebService(webServiceRequestObj);
		}catch(Exception e){
			logger.error("importMiPolicyToCore 呼叫 web service 發生錯誤 : ", e);
			result = transactionId + "," + policyNo + "," + endorseNo + "," + "呼叫 web service 發生錯誤";
			return result;
		}
		try{
			String returnCode =  returnContent.split(",")[0];
			String returnMsg =  returnContent.split(",")[1];
			if("0".equals(returnCode)){
				fetMobilePolicy.setStatus("FINISH");
			}else{
				fetMobilePolicy.setStatus("CORE_FAIL");
				fetMobilePolicy.setCoreFailMsg(""+returnMsg);
			}
			//更新資料
			fetMobilePolicy.setModifiedBy("SYSTEM10");
			fetMobilePolicy.setModifiedTime(new Date());
			result1 = this.updateFetMobilePolicy(fetMobilePolicy);
			if(result1.getResObject() == null){
				result = transactionId + "," + policyNo + "," + endorseNo + "," + "更新FetMobilePolicy.Status時 發生錯誤(1)";
				return result;
			}
		}catch (Exception e) {
			logger.error("importMiPolicyToCore 更新FetMobilePolicy.Status時 發生錯誤(2) : ", e);
			result = transactionId + "," + policyNo + "," + endorseNo + "," + "更新FetMobilePolicy.Status時 發生錯誤(2)";
			return result;
		}
		logger.info("importMiPolicyToCore end......");
		return result;
	}
	
	@Override
	public boolean batchInsertChubbEndorseData(ChubbCustomerEndorse chubbCustomerEndorse) throws SystemException, Exception {
		
		if(chubbCustomerEndorse == null) return false;
		CustomerEndorse customerEndorse = this.mappingCustomerEndorse(chubbCustomerEndorse);
		if(customerEndorse == null) return false;
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("endorseNo", chubbCustomerEndorse.getEndorseNo());
		
		Result result = this.chubbApplicantEndorseService.findChubbApplicantEndorseByParams(params);
		if(result == null || result.getResObject() == null) {
			return false;
		}
		List<ChubbApplicantEndorse> chubbApplicantEndorseList = (List<ChubbApplicantEndorse>)result.getResObject();
		//查詢的資料如果查無資料或資料數量不等於1，視為資料異常，故不執行匯入資料
		if(chubbApplicantEndorseList == null || chubbApplicantEndorseList.size() != 1) {
			return false;
		}
		ChubbApplicantEndorse chubbApplicantEndorse = chubbApplicantEndorseList.get(0);
		ApplicantEndorse applicantEndorse = this.mappingApplicantEndorse(customerEndorse.getTransactionId(), chubbApplicantEndorse);
		if(applicantEndorse == null) return false;
		
		result = this.chubbInsuredEndorseService.findChubbInsuredEndorseByParams(params);
		if(result == null || result.getResObject() == null) {
			return false;
		}
		List<ChubbInsuredEndorse> chubbInsuredEndorseList = (List<ChubbInsuredEndorse>)result.getResObject();
		//查詢的資料如果查無資料或資料數量不等於1，視為資料異常，故不執行匯入資料
		if(chubbInsuredEndorseList == null || chubbInsuredEndorseList.size() != 1) {
			return false;
		}
		ChubbInsuredEndorse chubbInsuredEndorse = chubbInsuredEndorseList.get(0);
		InsuredEndorse insuredEndorse = this.mappingInsuredEndorse(customerEndorse.getTransactionId(), chubbInsuredEndorse);
		if(insuredEndorse == null) return false;
		
		result = this.chubbProductEndorseService.findChubbProductEndorseByParams(params);
		if(result == null || result.getResObject() == null) {
			return false;
		}
		List<ChubbProductEndorse> chubbProductEndorseList = (List<ChubbProductEndorse>)result.getResObject();
		//查詢的資料如果查無資料或資料數量不等於1，視為資料異常，故不執行匯入資料
		if(chubbProductEndorseList == null || chubbProductEndorseList.size() != 1) {
			return false;
		}
		ChubbProductEndorse chubbProductEndorse = chubbProductEndorseList.get(0);
		ProductEndorse productEndorse = this.mappingProductEndorse(customerEndorse.getTransactionId(), chubbProductEndorse);
		if(productEndorse == null) return false;
		
		//拿批單號查詢CustomerEndorse是否有資料，如果有就不執行匯入資料
		int dataCount = this.customerEndorseService.countCustomerEndorse(params);
		if(dataCount > 0) return false;
		
		this.customerEndorseService.insertCustomerEndorse(customerEndorse);
		this.applicantEndorseService.insertApplicantEndorse(applicantEndorse);
		this.insuredEndorseService.insertInsuredEndorse(insuredEndorse);
		this.productEndorseService.insertProductEndorse(productEndorse);
		
		return true;
	}
	
	@Override
	public boolean batchInsertChubbApplicantEndorse(List<ChubbApplicantEndorse> lists)
			throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ChubbApplicantEndorse entity : lists) {
				temp = chubbApplicantEndorseService.insertChubbApplicantEndorse(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert ChubbApplicantEndorse fail");
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchInsertChubbCustomerEndorse(List<ChubbCustomerEndorse> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ChubbCustomerEndorse entity : lists) {
				temp = chubbCustomerEndorseService.insertChubbCustomerEndorse(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert ChubbCustomerEndorse fail");
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchInsertChubbInsuredEndorse(List<ChubbInsuredEndorse> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ChubbInsuredEndorse entity : lists) {
				temp = chubbInsuredEndorseService.insertChubbInsuredEndorse(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert ChubbInsuredEndorse fail");
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchInsertChubbProductEndorse(List<ChubbProductEndorse> lists) throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(ChubbProductEndorse entity : lists) {
				temp = chubbProductEndorseService.insertChubbProductEndorse(entity);
				if(temp != null && !"PUB100".equals(temp.getMessage().getOpCode())) {
					throw new Exception("Insert ChubbProductEndorse fail");
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean batchUpdateFetCancelNotification(List<FetCancelNotification> lists, Date uploadDate)
			throws SystemException, Exception {
		if(lists != null) {
			Result temp = null;
			for(FetCancelNotification entity : lists) {
				entity.setUploadDate(uploadDate);
				entity.setModifiedBy("system");
				entity.setModifiedTime(new Date());
				temp = this.fetCancelNotificationService.updateFetCancelNotification(entity);
				if(temp != null && !"PUB120".equals(temp.getMessage().getOpCode())) {
					throw new Exception("update FetCancelNotification fail");
				}
			}
		}
		return true;
	}
	
	private LiaMiEpolicy mappingLiaMiEpolicy(Customer customer, Application application, Applicant applicant, 
			Product product,  List<EpolicyPrpdrationclausekindVo> epolicyPrpdrationclausekindList, String reportNo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		LiaMiEpolicy entity = new LiaMiEpolicy();
		try {
			entity.setSerialno(FetTransactionIdGenUtil.getTransactionId("LME", 6));
			entity.setPolicyno(application.getPolicyNo());
			entity.setInsuredname(applicant.getName());
			entity.setPostcode(applicant.getZipCode());
			entity.setPostaddress(applicant.getAddress());
			entity.setStartdate(df.parse(application.getStartDate()));
			entity.setEnddate(df.parse(application.getEndDate()));
			entity.setModel(product.getModel());
			entity.setProductid(product.getImei());
			entity.setInitprice(Long.valueOf(product.getRrp()));
			entity.setPhonenumber(customer.getMsisdn());

			String projectCode = application.getProjectCode();
			entity.setProjectcode(projectCode);
			String pdfFileName = "";
			if(projectCode.length() >= 5) {
				if("MI101".equals(projectCode.subSequence(0, 5)) 
						||  "MI201".equals(projectCode.subSequence(0, 5)) ) {
					pdfFileName = projectCode.substring(0, 5) + ".pdf";
				} else if("MI3006".equals(projectCode)) {
					pdfFileName = "MI3_6.pdf";
				} else {
					pdfFileName = projectCode.substring(0, 3) + ".pdf";
				}
				/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  START*/
				if ("MM100".equals(projectCode.subSequence(0, 5))) {
					pdfFileName = "MM100.pdf";
				} else if ("MY100".equals(projectCode.subSequence(0, 5))) {
					pdfFileName = "MY100.pdf";
				} else if ("MM200".equals(projectCode.subSequence(0, 5))) {
					pdfFileName = "MM200.pdf";
				}
			}
			entity.setPdffilename(pdfFileName);
			
			if(epolicyPrpdrationclausekindList != null && epolicyPrpdrationclausekindList.size() > 0) {
				BigDecimal maxindemnify = null;
				BigDecimal deductibleinstruction = null;
				BigDecimal unitpremium = null;
				for(EpolicyPrpdrationclausekindVo vo : epolicyPrpdrationclausekindList) {
					
					if(vo.getMaxIndemnify() != null && vo.getMaxIndemnify().length() > 0) {
						maxindemnify = StringUtil.stringToBigDecimal(vo.getMaxIndemnify());
					} else {
						maxindemnify = null;
					}
					
					if(vo.getDeductible() != null && vo.getDeductible().length() > 0) {
						deductibleinstruction = StringUtil.stringToBigDecimal(vo.getDeductible());
					} else {
						deductibleinstruction = null;
					}
					
					if("MI01".equals(vo.getKindcode())) {
						unitpremium = StringUtil.stringToBigDecimal(vo.getUnitPremium());
						entity.setMaxindemnify1(maxindemnify);
						entity.setDeductibleinstruction1(deductibleinstruction);
						entity.setUnitpremium(unitpremium);
					} else if("MI02".equals(vo.getKindcode())) {
						entity.setMaxindemnify2(maxindemnify);
						entity.setDeductibleinstruction2(deductibleinstruction);
					} else if("MI03".equals(vo.getKindcode())) {
						entity.setMaxindemnify3(maxindemnify);
						entity.setDeductibleinstruction3(deductibleinstruction);
					} else if("MI04".equals(vo.getKindcode())) {
						entity.setMaxindemnify4(maxindemnify);
						entity.setDeductibleinstruction4(deductibleinstruction);
					} else if("MI05".equals(vo.getKindcode())) {
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					}  else if("MI06".equals(vo.getKindcode())) {
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					} 
					
					if("MM01".equals(vo.getKindcode())) {// 意外損害、第二年起故障，同MI01欄位
						unitpremium = StringUtil.stringToBigDecimal(vo.getUnitPremium());
						entity.setMaxindemnify1(maxindemnify);
						entity.setDeductibleinstruction1(deductibleinstruction);
						entity.setUnitpremium(unitpremium);
					} else if("MM02".equals(vo.getKindcode())) {// SIM卡盜用，同MI05欄位
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					} else if("MM03".equals(vo.getKindcode())) {// 盜用損失，同MI03欄位
						entity.setMaxindemnify3(maxindemnify);
						entity.setDeductibleinstruction3(deductibleinstruction);
					} else if("MM04".equals(vo.getKindcode())) {// 電池維修置換，同MI04欄位
						entity.setMaxindemnify4(maxindemnify);
						entity.setDeductibleinstruction4(deductibleinstruction);
					} else if("MM05".equals(vo.getKindcode())) {// 竊盜損失，同MI02欄位
						entity.setMaxindemnify2(maxindemnify);
						entity.setDeductibleinstruction2(deductibleinstruction);
					}  else if("MM06".equals(vo.getKindcode())) {
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					} 
					if("MY01".equals(vo.getKindcode())) {// 意外損害、第二年起故障，同MI01欄位
						unitpremium = StringUtil.stringToBigDecimal(vo.getBasePremium());// 年繳直接抓 BasePremium 做為每期保費 
						entity.setMaxindemnify1(maxindemnify);
						entity.setDeductibleinstruction1(deductibleinstruction);
						entity.setUnitpremium(unitpremium);
					} else if("MY02".equals(vo.getKindcode())) {// SIM卡盜用，同MI05欄位
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					} else if("MY03".equals(vo.getKindcode())) {// 盜用損失，同MI03欄位
						entity.setMaxindemnify3(maxindemnify);
						entity.setDeductibleinstruction3(deductibleinstruction);
					} else if("MY04".equals(vo.getKindcode())) {// 竊盜損失，同MI02欄位
						entity.setMaxindemnify2(maxindemnify);
						entity.setDeductibleinstruction2(deductibleinstruction);
					} else if("MY05".equals(vo.getKindcode())) {
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					}  else if("MY06".equals(vo.getKindcode())) {
						entity.setMaxindemnify5(maxindemnify);
						entity.setDeductibleinstruction5(deductibleinstruction);
					} 
					/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  END*/
				}
			}
			
			entity.setReplyno(reportNo);
			entity.setInputdate(new Date());
		} catch(Exception e) {
			return null;
		}
		return entity;
	}
	
	private Newepolicymain mappingNewepolicymain(Application application, Applicant applicant, 
			Insured insured, List<EpolicyPrpdrationclausekindVo> epolicyPrpdrationclausekindList, String reportNo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Newepolicymain entity = new Newepolicymain();
		try {
			DecimalFormat dFormat = new DecimalFormat("###,###");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String policyYear = sdf.format(new Date());
			if(application.getStartDate() != null && application.getStartDate().length() >= 4){
				policyYear = application.getStartDate().substring(0, 4);
			}
			String type = "P";
			String filename = "P18COM_" + application.getPolicyNo() + "_" + policyYear;
			String checkPolicyno = application.getPolicyNo();
			entity.setFilename(filename);
			entity.setPolicyno(application.getPolicyNo());
			entity.setEndorseno(null);
			entity.setEndorsecontent(null);
			entity.setApplicantname(applicant.getName());
			entity.setApplicantid(applicant.getCustomerId());
			entity.setApplicantemail(applicant.getEmailAddress());
			entity.setInsuredid(insured.getCustomerId());
			entity.setInsuredname(insured.getName());
			entity.setStartdate(df.parse(application.getStartDate()));
			entity.setEnddate(df.parse(application.getEndDate()));
			entity.setRiskcode("MI");
			entity.setEpstatus("0");
			entity.setType(type);
			entity.setRemark("A");
			entity.setZipfilename(null);
			entity.setRecordate(new Date());
			entity.setUnderwriteenddate(df.parse(application.getStartDate()));
			entity.setEmaildate(null);
			entity.setPrintno(null);
			if(checkPolicyno != null && checkPolicyno.length() > 4) {
				checkPolicyno = checkPolicyno.substring(4);
			}
			String strEpolicymain = checkPolicyno + "|" + applicant.getName() + "|" + application.getPremium();
			entity.setEpolicymain(strEpolicymain);
			String deductible = "";
			if(epolicyPrpdrationclausekindList != null && epolicyPrpdrationclausekindList.size() > 0) {
				for(EpolicyPrpdrationclausekindVo vo : epolicyPrpdrationclausekindList) {
					if("MI01".equals(vo.getKindcode())) {
						deductible = vo.getDeductible();
						break;
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  START*/
					} else if("MM01".equals(vo.getKindcode())) {
						deductible = vo.getDeductible();
						break;
					} else if("MY01".equals(vo.getKindcode())) {
						deductible = vo.getDeductible();
						break;
					}
				}
			}
			String deductibleString = dFormat.format(StringUtil.stringToBigDecimal(deductible));
			String strEpolicymainkind = "行動裝置保險(分期交付型)" + "|" + deductibleString;
			String strEpolicyterms = reportNo + "|" + "中國信託產物行動裝置保險(分期交付型)";
			if(EnumMobileDataSrc.ESTORE.getCode().equals(application.getDataSrc())) {
				String pjCode = application.getProjectCode();
				if ("MM100".equals(pjCode.subSequence(0, 5)) || "MM200".equals(pjCode.subSequence(0, 5))) {
					strEpolicymainkind = "行動裝置保險(分期交付甲型)" + "|" + deductibleString;
					strEpolicyterms = reportNo + "|" + "中國信託產物行動裝置保險(分期交付甲型)";
				} else if ("MY100".equals(pjCode.subSequence(0, 5))) {
					strEpolicymainkind = "行動裝置保險(一次交付甲型)" + "|" + deductibleString;
					strEpolicyterms = reportNo + "|" + "中國信託產物行動裝置保險(一次交付甲型)";
				} 
			}
			entity.setEpolicykind(strEpolicymainkind);
			entity.setEpolicyterms(strEpolicyterms);
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發  電子保單  END*/
			entity.setPremium(application.getPremium());
		} catch(Exception e) {
			return null;
		}
		return entity;
	}
	
	private MiEpolicysms mappingMiEpolicysms(Application application, Applicant applicant) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		MiEpolicysms entity = new MiEpolicysms();
		try {
			String oid = FetTransactionIdGenUtil.getTransactionId("MES", 6);
			entity.setOid(oid);
			entity.setPolicyno(application.getPolicyNo());
			entity.setMobile(applicant.getTelNo());
			entity.setHandleridentifynumber(application.getSalesId());
			entity.setComcode("00");
			entity.setOperatedate(df.parse(application.getStartDate()));
			entity.setClosedate(df.parse(application.getEndDate()));
			entity.setIcreate("system");
			entity.setDcreate(new Date());
		} catch(Exception e) {
			return null;
		}
		return entity;
	}
	
	/**
	 * mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新
	 * 
	 * 產生web service 所需MobileVo物件
	 * 
	 * @param fetMobilePolicyDevice 
	 * @return MobileVo 
	 * @throws Exception
	 */
	private MobileVo getMobileVo(FetMobilePolicyDevice fetMobilePolicyDevice) throws Exception{
		MobileVo mobileVo = new MobileVo();
		//行動裝置資料
		mobileVo.setSerialNo("1"); //行動裝置順序
		mobileVo.setProductNumber(fetMobilePolicyDevice.getProductNumber()); //商品料號
		mobileVo.setProductName(fetMobilePolicyDevice.getProductName()); //商品名稱
		mobileVo.setType(fetMobilePolicyDevice.getType()); //種類
		mobileVo.setLabel(fetMobilePolicyDevice.getBrand()); //廠牌
		mobileVo.setModel(fetMobilePolicyDevice.getModel()); //型號
		mobileVo.setProductId(fetMobilePolicyDevice.getProductId()); //產品識別碼
		if(!StringUtil.isSpace(fetMobilePolicyDevice.getInitPrice())){
			mobileVo.setInitPrice(new BigDecimal(fetMobilePolicyDevice.getInitPrice())); //產品空機價
		}
		mobileVo.setFileName(fetMobilePolicyDevice.getFileName()); //檔名
		mobileVo.setPhoneNumber(fetMobilePolicyDevice.getPhoneNumber()); //手機門號
		mobileVo.setPurchaseDate(fetMobilePolicyDevice.getPurchaseDate()); //設備購買日
		mobileVo.setIsFetsupply(fetMobilePolicyDevice.getIsFetSupply()); //是否為遠傳出貨
		
		return mobileVo;
	}
	
	/**
	 * mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新
	 * 
	 * 產生web service 所需InsurantInfoVoByMobile物件
	 * 
	 * @param fetMobilePolicyInsurantInfo
	 * @param mobileVo
	 * @return
	 * @throws Exception
	 */
	private InsurantInfoVoByMobile getInsurantInfoVoByMobile(FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo, MobileVo mobileVo) throws Exception{
		InsurantInfoVoByMobile insurant = new InsurantInfoVoByMobile();
		//被保人清單物件
		insurant.getMobileVos().add(mobileVo); //行動裝置資料
		insurant.setSerialno("1"); //被保人順序
		insurant.setInsuredCode(fetMobilePolicyInsurantInfo.getInsuredCode()); //被保人身份證字號或統編或居留證號
		insurant.setInsuredName(fetMobilePolicyInsurantInfo.getInsuredName()); //被保人姓名或公司名稱
		insurant.setBirthday(fetMobilePolicyInsurantInfo.getBirthday()); //被保人生日
		insurant.setPhoneTeleNumber(""); //被保人住家電話
		insurant.setMobile(fetMobilePolicyInsurantInfo.getMobile()); //被保人手機
		insurant.setPostCode(fetMobilePolicyInsurantInfo.getPostCode()); //郵遞區號(3碼)
		insurant.setPostAddress(fetMobilePolicyInsurantInfo.getPostAddress()); //住址
		insurant.setCountryEName(fetMobilePolicyInsurantInfo.getCountryEname()); //被保人國籍英文縮寫
		insurant.setIsHighDengerOccupation("1"); //是否爲高危職業/行業 
		insurant.setDomicile(fetMobilePolicyInsurantInfo.getDomicile()); //居住地/註冊地英文縮寫
		insurant.setProjectCode(fetMobilePolicyInsurantInfo.getProjectCode()); //專案代號
		if(!StringUtil.isSpace(fetMobilePolicyInsurantInfo.getProjectCodePremium())){
			insurant.setProjectCodePremium(new BigDecimal(fetMobilePolicyInsurantInfo.getProjectCodePremium())); //專案保費
		}
		insurant.setEmail(fetMobilePolicyInsurantInfo.getEmail()); //電子信箱
		if(!StringUtil.isSpace(insurant.getInsuredCode()) && insurant.getInsuredCode().length() == 8){
			insurant.setHeadName(fetMobilePolicyInsurantInfo.getHeadName()); //負責人姓名
			insurant.setListedCabinetCompany("1"); //上市櫃公司
		}
		return insurant;
	}
	/**
	 * mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新
	 * 
	 * 產生web service 所需PolicyEndorseInfoRequest物件
	 * 
	 * @param fetMobilePolicy
	 * @param insurant
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private PolicyEndorseInfoRequest getPolicyEndorseInfoRequest(FetMobilePolicy fetMobilePolicy, 
			InsurantInfoVoByMobile insurant, FetMobilePolicySales fetMobilePolicySales, PrpinsAgentRespVo estorePrpinsAgentRespVo) throws NumberFormatException, Exception{
		PolicyEndorseInfoRequest main = new PolicyEndorseInfoRequest();
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
		String dataSrc = fetMobilePolicy.getDataSrc();
		//主檔資料
		main.getInsurantInfoVoByMobiles().add(insurant);
		main.setFlowNo("1"); //送金單號/流水編號
//		main.setOperateSite("FET"); //傳入來源
		main.setIsPremiumCal("1"); //是否計算保費
		main.setPolicyNo(fetMobilePolicy.getPolicyNo()); //保單號碼
		main.setEndorseNo(fetMobilePolicy.getEndorseNo()); //批單號碼
		main.setEndorseType(fetMobilePolicy.getEndorseType()); //批單類別
		main.setEndorseDate(fetMobilePolicy.getEndorseDate()); //批改生效日
		main.setStartDate(fetMobilePolicy.getStartDate()); //保險起日 
		main.setStartHour(0); //保險起時
		main.setEndDate(fetMobilePolicy.getEndDate()); //保險迄日
		main.setEndHour(0); //保險迄時
		main.setDirectBusiness("0");  //是否直接業務 0：否  1：是，行動裝置險固定為0
//		main.setBusinessNature("J00123"); //業務來源
//		main.setExtraComCode("00"); //單位代號
//		main.setExtraComName("總公司"); //單位名稱
//		main.setHandlerIdentifyNumber(fetMobilePolicy.getHandlerIdentifyNumber()); //單招攬人身分證字號
//		main.setHandlerName(fetMobilePolicy.getHandlerName()); //招攬人名稱
//		main.setAgentCode("349353"); //經辦代號
//		main.setHandler1Code("AA002"); //服務人員
		main.setRemark(""); //備註
		main.setInsuredCode(fetMobilePolicy.getInsuredCode()); //要保人ID
		main.setInsuredName(fetMobilePolicy.getInsuredName()); //要保人姓名
		main.setBirthday(fetMobilePolicy.getBirthday()); //要保人生日
		if(!StringUtil.isSpace(fetMobilePolicy.getInsuredCode()) && fetMobilePolicy.getInsuredCode().length() == 8){
			main.setHeadName(fetMobilePolicy.getHeadName()); //負責人
			main.setListedCabinetCompany("1"); //是否為上次櫃公司
		}
		main.setPhoneTeleNumber(fetMobilePolicy.getPhoneTeleNumber()); //電話
		main.setMobile(fetMobilePolicy.getMobile()); //手機
		main.setPostCode(fetMobilePolicy.getPostCode()); //通訊郵編、戶籍郵編
		main.setPostAddress(fetMobilePolicy.getPostAddress()); //通訊地址、戶籍地址
		main.setInsuredIdentity(fetMobilePolicy.getInsuredIdentity()); //要保人-與被保險人關係
		main.setCountryEName(fetMobilePolicy.getCountryEname()); //國籍英文名
		main.setIsHighDengerOccupation("1"); //是否爲高危職業/行業
		main.setDomicile(fetMobilePolicy.getDomicile()); //居住地/註冊地
		main.setEmail(fetMobilePolicy.getEmail()); //要保人電子信箱
		main.setEpolicy("Y"); //是否為電子保單
		main.setEndorseText(fetMobilePolicy.getEndorseText()); //批文
		main.setAutotransrenewflag("N"); //是否可自動續約
		main.setApplicationId(fetMobilePolicy.getApplicationId()); //申請書編號
		main.setOrderSeq(fetMobilePolicy.getContractId()); //合約編號
		main.setBranchcode(fetMobilePolicy.getBranchCode()); //門市編號
		main.setBranchName(fetMobilePolicy.getBranchName()); //門市名稱
		main.setBranchName(fetMobilePolicy.getBranchName()); //門市名稱
		main.setRecommenderId(""); //推薦人ID
		main.setDistributorId(""); //盤商代號
		main.setPromotionId(""); //促銷代號
		if(!StringUtil.isSpace(fetMobilePolicy.getPayNo())){
			main.setPayNo(new Integer(fetMobilePolicy.getPayNo())); //促銷代號
		}
		main.setPlanStartDate(fetMobilePolicy.getPlanStartDate()); //繳費起期
		main.setPlanEndDate(fetMobilePolicy.getPlanEndDate()); //繳費止期
		logger.info("getPolicyEndorseInfoRequest dataSrc::"+dataSrc);
		if(EnumMobileDataSrc.FEPIA.getCode().equals(dataSrc)) {
			main.setOperateSite("FET"); //傳入來源
			main.setBusinessNature("J00123"); //業務來源
			main.setExtraComCode("00"); //單位代號
			main.setExtraComName("總公司"); //單位名稱
			main.setHandlerIdentifyNumber(fetMobilePolicy.getHandlerIdentifyNumber()); //單招攬人身分證字號
			main.setHandlerName(fetMobilePolicy.getHandlerName()); //招攬人名稱
			main.setAgentCode("349353"); //經辦代號
			main.setHandler1Code("AA002"); //服務人員
			if(fetMobilePolicySales != null) {
				/**20240516：BJ016：業務員檢核----START*/
				main.setHandlerCode(fetMobilePolicySales.getHandlerCode());
				main.setHandlerName(fetMobilePolicySales.getHandlerName());
				main.setExtraComCode(fetMobilePolicySales.getExtraComCode());
				main.setExtraComName(fetMobilePolicySales.getExtraComName());
				main.setChannelType(fetMobilePolicySales.getChannelType());
				main.setBusinessNature(fetMobilePolicySales.getBusinessNature());
				main.setAgentCode(fetMobilePolicySales.getAgentCode());
				main.setAgentName(fetMobilePolicySales.getAgentName());
				/**20240516：BJ016：業務員檢核----END*/
			}
		}else if(EnumMobileDataSrc.ESTORE.getCode().equals(dataSrc)) {
			main.setOrderSeq(fetMobilePolicy.getTxId());// estore用tx_id
			main.setOperateSite(EnumMobileDataSrc.ESTORE.getCode()); //傳入來源
			main.setChannelType("91");
			main.setBusinessNature("Y00015"); //業務來源
			main.setExtraComCode("00"); //單位代號
			main.setExtraComName("行動裝置險"); //單位名稱
			main.setHandlerIdentifyNumber("AA1C000002"); //單招攬人身分證字號
			main.setHandlerName("遠傳電信"); //招攬人名稱
			main.setAgentCode("349367"); //經辦代號
			main.setAgentName("遠傳電信");
			main.setHandler1Code("CC024"); //服務人員
			if(estorePrpinsAgentRespVo != null) {
				main.setChannelType(estorePrpinsAgentRespVo.getChannelType());
				main.setBusinessNature(estorePrpinsAgentRespVo.getBusinessNature());
				main.setExtraComCode(estorePrpinsAgentRespVo.getExtraComCode());
				main.setExtraComName(estorePrpinsAgentRespVo.getExtraComName());
				main.setHandlerIdentifyNumber(fetMobilePolicy.getHandlerIdentifyNumber());
				main.setHandlerName(estorePrpinsAgentRespVo.getHandlerName());
				main.setAgentCode(estorePrpinsAgentRespVo.getAgentCode());
				main.setAgentName(estorePrpinsAgentRespVo.getAgentName());
				main.setHandler1Code(estorePrpinsAgentRespVo.getUserCode());
			}
		}
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
		return main;
	}
	
	/**
	 * mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新
	 * 
	 * @param policyEndorseInfoListRequest webservice所需物件
	 * @return
	 * @throws MalformedURLException
	 */
	private String callWebService(PolicyEndorseInfoListRequest policyEndorseInfoListRequest) throws MalformedURLException{
		
    	URL wsdlURL = new URL(configUtil.getString("miCoreWsdl"));
    	GeneratePolicyService generatePolicyWebServiceService = new GeneratePolicyWebServiceService(wsdlURL).getGeneratePolicyWebServicePort();
    	PolicyInfoResultVo vo = generatePolicyWebServiceService.generateMIPolicyInfos(policyEndorseInfoListRequest);
    	
    	String returnCode = vo.getReturnCode();
    	String message = vo.getReturnMsg();
    	logger.debug("returnCode = " + returnCode + " , message = " + message);
		return returnCode + "," + message;
	}
	
	private CustomerEndorse mappingCustomerEndorse(ChubbCustomerEndorse chubbCustomerEndorse) {
		CustomerEndorse entity = new CustomerEndorse();
		try {
			GIGO.fill(entity, chubbCustomerEndorse);
			entity.setTransactionId(FetTransactionIdGenUtil.getTransactionId("CE", 6));
			entity.setDataStatus("00");
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingCustomerEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ApplicantEndorse mappingApplicantEndorse(String transactionId, ChubbApplicantEndorse chubbApplicantEndorse) {
		ApplicantEndorse entity = new ApplicantEndorse();
		try {
			GIGO.fill(entity, chubbApplicantEndorse);
			entity.setTransactionId(transactionId);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingApplicantEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private InsuredEndorse mappingInsuredEndorse(String transactionId, ChubbInsuredEndorse chubbInsuredEndorse) {
		InsuredEndorse entity = new InsuredEndorse();
		try {
			GIGO.fill(entity, chubbInsuredEndorse);
			entity.setTransactionId(transactionId);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingInsuredEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
	private ProductEndorse mappingProductEndorse(String transactionId, ChubbProductEndorse chubbProductEndorse) {
		ProductEndorse entity = new ProductEndorse();
		try {
			GIGO.fill(entity, chubbProductEndorse);
			entity.setTransactionId(transactionId);
			entity.setCreatedBy("system");
			entity.setCreatedTime(new Date());
			
		} catch (Exception e) {
			logger.error("RunFetPolicyService mappingProductEndorse error", e);
			entity = null;
		}
		return entity;
	}
	
//	private String getTransactionId(Date now) throws SystemException, Exception {
//		Random r = new Random();
//		int upCase1 = r.nextInt(26)+65;
//		int upCase2 = r.nextInt(26)+65;
//		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
//		String transactionId = date + String.valueOf((char) upCase1) + String.valueOf((char) upCase2);
//		return transactionId;
//	}

	public MobileInsBatchInfoService getMobileInsBatchInfoService() {
		return mobileInsBatchInfoService;
	}

	public void setMobileInsBatchInfoService(MobileInsBatchInfoService mobileInsBatchInfoService) {
		this.mobileInsBatchInfoService = mobileInsBatchInfoService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public ApplicantService getApplicantService() {
		return applicantService;
	}

	public void setApplicantService(ApplicantService applicantService) {
		this.applicantService = applicantService;
	}

	public InsuredService getInsuredService() {
		return insuredService;
	}

	public void setInsuredService(InsuredService insuredService) {
		this.insuredService = insuredService;
	}

	public ExtColService getExtColService() {
		return extColService;
	}

	public void setExtColService(ExtColService extColService) {
		this.extColService = extColService;
	}

	public FetPolicyImportErrorService getFetPolicyImportErrorService() {
		return fetPolicyImportErrorService;
	}

	public void setFetPolicyImportErrorService(FetPolicyImportErrorService fetPolicyImportErrorService) {
		this.fetPolicyImportErrorService = fetPolicyImportErrorService;
	}
	
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 start */
	public ProposalFileService getProposalFileService() {
		return proposalFileService;
	}

	public void setProposalFileService(ProposalFileService proposalFileService) {
		this.proposalFileService = proposalFileService;
	}
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 end */

	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 start */
	public ChubbReturnCustomerService getChubbReturnCustomerService() {
		return chubbReturnCustomerService;
	}

	public void setChubbReturnCustomerService(ChubbReturnCustomerService chubbReturnCustomerService) {
		this.chubbReturnCustomerService = chubbReturnCustomerService;
	}
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 end */

	public FetMobilePolicyService getFetMobilePolicyService() {
		return fetMobilePolicyService;
	}

	public void setFetMobilePolicyService(
			FetMobilePolicyService fetMobilePolicyService) {
		this.fetMobilePolicyService = fetMobilePolicyService;
	}
	/** mantis：MOB0011，處理人員：BI086，需求單編號：OB0011 呼叫web service後結果狀態更新  start */
	public CustomerEndorseService getCustomerEndorseService() {
		return customerEndorseService;
	}

	public void setCustomerEndorseService(
			CustomerEndorseService customerEndorseService) {
		this.customerEndorseService = customerEndorseService;
	}

	public FetMobilePolicyInsurantInfoService getFetMobilePolicyInsurantInfoService() {
		return fetMobilePolicyInsurantInfoService;
	}

	public void setFetMobilePolicyInsurantInfoService(
			FetMobilePolicyInsurantInfoService fetMobilePolicyInsurantInfoService) {
		this.fetMobilePolicyInsurantInfoService = fetMobilePolicyInsurantInfoService;
	}

	public FetMobilePolicyDeviceService getFetMobilePolicyDeviceService() {
		return fetMobilePolicyDeviceService;
	}

	public void setFetMobilePolicyDeviceService(
			FetMobilePolicyDeviceService fetMobilePolicyDeviceService) {
		this.fetMobilePolicyDeviceService = fetMobilePolicyDeviceService;
	}
	/** mantis：MOB0011，處理人員：BI086，需求單編號：OB0011 呼叫web service後結果狀態更新  end */

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

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/

	public LiaMiEpolicyService getLiaMiEpolicyService() {
		return liaMiEpolicyService;
	}

	public void setLiaMiEpolicyService(LiaMiEpolicyService liaMiEpolicyService) {
		this.liaMiEpolicyService = liaMiEpolicyService;
	}

	public NewepolicymainService getNewepolicymainService() {
		return newepolicymainService;
	}

	public void setNewepolicymainService(NewepolicymainService newepolicymainService) {
		this.newepolicymainService = newepolicymainService;
	}

	public FetMobileEpolicyService getFetMobileEpolicyService() {
		return fetMobileEpolicyService;
	}

	public void setFetMobileEpolicyService(FetMobileEpolicyService fetMobileEpolicyService) {
		this.fetMobileEpolicyService = fetMobileEpolicyService;
	}

	public AccountFileService getAccountFileService() {
		return accountFileService;
	}

	public void setAccountFileService(AccountFileService accountFileService) {
		this.accountFileService = accountFileService;
	}

	public TerminationNoticeService getTerminationNoticeService() {
		return terminationNoticeService;
	}

	public void setTerminationNoticeService(TerminationNoticeService terminationNoticeService) {
		this.terminationNoticeService = terminationNoticeService;
	}

	public BatchRepairMainService getBatchRepairMainService() {
		return batchRepairMainService;
	}

	public void setBatchRepairMainService(BatchRepairMainService batchRepairMainService) {
		this.batchRepairMainService = batchRepairMainService;
	}

	public BatchRepairDetailService getBatchRepairDetailService() {
		return batchRepairDetailService;
	}

	public void setBatchRepairDetailService(BatchRepairDetailService batchRepairDetailService) {
		this.batchRepairDetailService = batchRepairDetailService;
	}

	public PrpdrationclausekindService getPrpdrationclausekindService() {
		return prpdrationclausekindService;
	}

	public void setPrpdrationclausekindService(PrpdrationclausekindService prpdrationclausekindService) {
		this.prpdrationclausekindService = prpdrationclausekindService;
	}

	public PrpdclausereportService getPrpdclausereportService() {
		return prpdclausereportService;
	}

	public void setPrpdclausereportService(PrpdclausereportService prpdclausereportService) {
		this.prpdclausereportService = prpdclausereportService;
	}

	public MiEpolicysmsService getMiEpolicysmsService() {
		return miEpolicysmsService;
	}

	public void setMiEpolicysmsService(MiEpolicysmsService miEpolicysmsService) {
		this.miEpolicysmsService = miEpolicysmsService;
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

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ApplicantEndorseService getApplicantEndorseService() {
		return applicantEndorseService;
	}

	public void setApplicantEndorseService(ApplicantEndorseService applicantEndorseService) {
		this.applicantEndorseService = applicantEndorseService;
	}

	public InsuredEndorseService getInsuredEndorseService() {
		return insuredEndorseService;
	}

	public void setInsuredEndorseService(InsuredEndorseService insuredEndorseService) {
		this.insuredEndorseService = insuredEndorseService;
	}

	public ProductEndorseService getProductEndorseService() {
		return productEndorseService;
	}

	public void setProductEndorseService(ProductEndorseService productEndorseService) {
		this.productEndorseService = productEndorseService;
	}

	public ChubbApplicantEndorseService getChubbApplicantEndorseService() {
		return chubbApplicantEndorseService;
	}

	public void setChubbApplicantEndorseService(ChubbApplicantEndorseService chubbApplicantEndorseService) {
		this.chubbApplicantEndorseService = chubbApplicantEndorseService;
	}

	public ChubbCustomerEndorseService getChubbCustomerEndorseService() {
		return chubbCustomerEndorseService;
	}

	public void setChubbCustomerEndorseService(ChubbCustomerEndorseService chubbCustomerEndorseService) {
		this.chubbCustomerEndorseService = chubbCustomerEndorseService;
	}

	public ChubbInsuredEndorseService getChubbInsuredEndorseService() {
		return chubbInsuredEndorseService;
	}

	public void setChubbInsuredEndorseService(ChubbInsuredEndorseService chubbInsuredEndorseService) {
		this.chubbInsuredEndorseService = chubbInsuredEndorseService;
	}

	public ChubbProductEndorseService getChubbProductEndorseService() {
		return chubbProductEndorseService;
	}

	public void setChubbProductEndorseService(ChubbProductEndorseService chubbProductEndorseService) {
		this.chubbProductEndorseService = chubbProductEndorseService;
	}

	public FetCancelNotificationService getFetCancelNotificationService() {
		return fetCancelNotificationService;
	}

	public void setFetCancelNotificationService(FetCancelNotificationService fetCancelNotificationService) {
		this.fetCancelNotificationService = fetCancelNotificationService;
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

}
