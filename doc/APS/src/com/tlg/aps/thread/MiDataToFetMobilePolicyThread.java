package com.tlg.aps.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.msSqlMob.entity.Applicant;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.entity.Customer;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.entity.Insured;
import com.tlg.msSqlMob.entity.Product;
import com.tlg.msSqlMob.service.ApplicantService;
import com.tlg.msSqlMob.service.CustomerService;
import com.tlg.msSqlMob.service.InsuredService;
import com.tlg.msSqlMob.service.ProductService;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 */
public class MiDataToFetMobilePolicyThread implements Runnable {

	protected Logger logger = Logger.getLogger(MiDataToFetMobilePolicyThread.class);
	private List<Application> applicationList;
	private String groupNo;
	private CustomerService customerService;
	private ApplicantService applicantService;
	private InsuredService insuredService;
	private ProductService productService;
	private FetPolicyService fetPolicyService;
	private RunFetPolicyService runFetPolicyService;
	
	public MiDataToFetMobilePolicyThread(List<Application> applicationList, String groupNo){
		this.applicationList = applicationList;
		this.groupNo = groupNo;
		this.customerService = (CustomerService)AppContext.getBean("customerService");
		this.applicantService = (ApplicantService)AppContext.getBean("applicantService");
		this.insuredService = (InsuredService)AppContext.getBean("insuredService");
		this.productService = (ProductService)AppContext.getBean("productService");
		this.fetPolicyService = (FetPolicyService)AppContext.getBean("fetPolicyService");
		this.runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}

	public void run() {
		try {
			logger.debug("MiDataToFetMobilePolicyThread START: groupNo=" + this.groupNo + "; applicationList.size()="
					+ this.applicationList.size());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());

			Map<String, String> params = new HashMap<>();
			ArrayList<String> errorList = new ArrayList<>();
			for (Application application : applicationList) {
				Date sysdate = new Date();
				String transactionId = application.getTransactionId();
				logger.debug("groupNo=" + groupNo + "; transactionId=" + transactionId);
				Customer customer = new Customer();
				Applicant applicant = new Applicant();
				Insured insured = new Insured();
				Product product = new Product();
				params.put("transactionId", transactionId);
				Result result = customerService.findCustomerByUK(transactionId);
				if (result.getResObject() == null) {// 若查無結果列為問題件發信送出
					errorList.add(transactionId + ",,, customer 查無 transactionId 資料!");
					continue;
				}
				customer = (Customer) result.getResObject();

				result = applicantService.findApplicantByUK(transactionId);
				if (result.getResObject() == null) {
					errorList.add(transactionId + ",,,applicant 查無 transactionId 資料!");
					continue;
				}
				applicant = (Applicant) result.getResObject();

				result = insuredService.findInsuredByUK(transactionId);
				if (result.getResObject() == null) {
					errorList.add(transactionId + ",,,insured 查無 transactionId 資料!");
					continue;
				}
				insured = (Insured) result.getResObject();

				result = productService.findProductByUK(transactionId);
				if (result.getResObject() == null) {
					errorList.add(transactionId + ",,,product 查無 transactionId 資料!");
					continue;
				}
				product = (Product) result.getResObject();

				try {
					FetMobilePolicy fetMobilePolicy = getFetMobilePolicy(application, applicant, customer, sysdate);
					FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo = getFetMobilePolicyInsurantInfo(
							application, insured, sysdate);
					FetMobilePolicyDevice fetMobilePolicyDevice = getFetMobilePolicyDevice(application, product,
							customer, sysdate);

					fetPolicyService.insertDataToFetMobilePolicy(fetMobilePolicy, fetMobilePolicyInsurantInfo,
							fetMobilePolicyDevice);
					application.setDataStatus("FINISH");
					result = fetPolicyService.updateApplication(application);
					if(result.getResObject() == null) {
						errorList.add(transactionId + ",,,寫入中介檔成功，但更新Application.data_status發生錯誤!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					application.setDataStatus("FAIL");
					result = fetPolicyService.updateApplication(application);
					if(result.getResObject() == null) {
						errorList.add(transactionId + ",,,寫入中介檔失敗，且更新Application.data_status發生錯誤!");
					}
					errorList.add(transactionId + ",,,資料寫入中介檔時發生錯誤!");
				}
			}
			if (!errorList.isEmpty()) {
				this.runFetPolicyService.sendErrorMail(errorList, executeDate, groupNo, "行動裝置險-保批單資料寫入匯入核心中介資料表問題件");
			}
			logger.debug("MiDataToFetMobilePolicyThread END: groupNo=" + this.groupNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	private FetMobilePolicy getFetMobilePolicy(Application application, Applicant applicant, Customer customer, Date sysdate) {
		FetMobilePolicy fetMobilePolicy = new FetMobilePolicy();
		fetMobilePolicy.setTransactionId(application.getTransactionId());
		fetMobilePolicy.setContractId(application.getContractId());
		fetMobilePolicy.setApplicationId(application.getApplicationId());
		if(null == application.getEndorseNo() || application.getEndorseNo().length() <= 0) {
			fetMobilePolicy.setDataType("1");
			fetMobilePolicy.setStatus("WAIT");
		}else {
			fetMobilePolicy.setDataType("2");
			fetMobilePolicy.setStatus("HOLD");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if("85".equals(application.getEndorseType())) {
				fetMobilePolicy.setEndorseDate(sdf.format(customer.getCreatedTime()));
				if(application.getPremium() != null && application.getPremium().trim().length() > 0 
						&& Integer.parseInt(application.getPremium()) <= 0) {
					fetMobilePolicy.setStatus("WAIT");
				}
			} else if("21".equals(application.getEndorseType())) {
				if(customer.getServiceTerminationDate() != null && customer.getServiceTerminationDate().trim().length() >= 8) {
					fetMobilePolicy.setEndorseDate(customer.getServiceTerminationDate().substring(0, 8));
				} else {
					fetMobilePolicy.setEndorseDate(sdf.format(customer.getCreatedTime()));
				}
				fetMobilePolicy.setStatus("WAIT");
			} else if("19".equals(application.getEndorseType())) {
				fetMobilePolicy.setStatus("CANCEL");//如果是註銷的批單就不要匯入核心
			}
		}
		fetMobilePolicy.setPolicyNo(application.getPolicyNo());
		fetMobilePolicy.setEndorseNo(application.getEndorseNo());
		fetMobilePolicy.setEndorseType(application.getEndorseType());
		fetMobilePolicy.setEndorseText(application.getEndorseContent());
		fetMobilePolicy.setStartDate(application.getStartDate());
		fetMobilePolicy.setEndDate(application.getEndDate());
		fetMobilePolicy.setHandlerIdentifyNumber(application.getSalesId());
		fetMobilePolicy.setHandlerName("");//呼叫WS反查業務員姓名(待開發先給空值)
		fetMobilePolicy.setInsuredCode(applicant.getCustomerId());
		fetMobilePolicy.setInsuredName(applicant.getName());
		fetMobilePolicy.setHeadName(applicant.getResponsiblePerson());
		fetMobilePolicy.setBirthday(applicant.getBirthday());
		fetMobilePolicy.setMobile(applicant.getTelNo());
		fetMobilePolicy.setPostCode(applicant.getZipCode());
		fetMobilePolicy.setPostAddress(applicant.getAddress());
		fetMobilePolicy.setInsuredIdentity("01");//01表示與被保人關係為本人
		if("9".equals(applicant.getCountry())) {
			fetMobilePolicy.setCountryEname("TW");
		}else if("2".equals(applicant.getCountry())) {
			fetMobilePolicy.setCountryEname("CN");
		}
		fetMobilePolicy.setDomicile("TW");
		fetMobilePolicy.setEmail(applicant.getEmailAddress());
		fetMobilePolicy.setBranchCode(customer.getStoreId());
		fetMobilePolicy.setBranchName(customer.getStoreName());
		fetMobilePolicy.setPayNo(application.getPayPeriod());
		fetMobilePolicy.setPlanStartDate(application.getPayPeriodStartDate());
		fetMobilePolicy.setPlanEndDate(application.getPayPeriodEndDate());
		fetMobilePolicy.setCreatedBy("system");
		fetMobilePolicy.setCreatedTime(sysdate);
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
		if (EnumMobileDataSrc.ESTORE.getCode().equals(customer.getDataSrc())) {
			String handlerIdentifyNumber = StringUtils.isNotBlank(application.getSalesId()) ? application.getSalesId() : "AA1C000002";
			fetMobilePolicy.setHandlerIdentifyNumber(handlerIdentifyNumber);
		}
		fetMobilePolicy.setDataSrc(customer.getDataSrc());
		fetMobilePolicy.setTxId(customer.getTxId());
		fetMobilePolicy.setCtbcRcvNo(customer.getCtbcRcvNo());
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
		return fetMobilePolicy;
	}
	
	private FetMobilePolicyInsurantInfo getFetMobilePolicyInsurantInfo(Application application, Insured insured, Date sysdate) {
		FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo = new FetMobilePolicyInsurantInfo();
		fetMobilePolicyInsurantInfo.setTransactionId(application.getTransactionId());
		fetMobilePolicyInsurantInfo.setInsuredCode(insured.getCustomerId());
		fetMobilePolicyInsurantInfo.setInsuredName(insured.getName());
		fetMobilePolicyInsurantInfo.setBirthday(insured.getBirthday());
		fetMobilePolicyInsurantInfo.setMobile(insured.getTelNo());
		fetMobilePolicyInsurantInfo.setPostCode(insured.getZipCode());
		fetMobilePolicyInsurantInfo.setPostAddress(insured.getAddress());
		if("9".equals(insured.getCountry())) {
			fetMobilePolicyInsurantInfo.setCountryEname("TW");
		}else if("2".equals(insured.getCountry())) {
			fetMobilePolicyInsurantInfo.setCountryEname("CN");
		}
		fetMobilePolicyInsurantInfo.setDomicile("TW");
		fetMobilePolicyInsurantInfo.setProjectCode(application.getProjectCode());
		/**
		 * 20240515：BJ016：預約式保單要用0元保費方式匯入核心----START
		 * 判斷方式：沒有批單號就是保單資料，將保費設為0
		 * */
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程  只有遠傳保代的資料要0元保單入核心  START*/
		String dataSrc = application.getDataSrc();
		fetMobilePolicyInsurantInfo.setDataSrc(dataSrc);
		if((application.getEndorseNo() == null || application.getEndorseNo().trim().length() <= 0)
				&& EnumMobileDataSrc.FEPIA.getCode().equals(dataSrc)) {
			/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程  只有遠傳保代的資料要0元保單入核心  END*/
			fetMobilePolicyInsurantInfo.setProjectCodePremium("0");
		} else {
			fetMobilePolicyInsurantInfo.setProjectCodePremium(application.getPremium());
		}
		/**
		 * 20240515：BJ016：預約式保單要用0元保費方式匯入核心----END
		 * */
		fetMobilePolicyInsurantInfo.setHeadName(insured.getResponsiblePerson());
		fetMobilePolicyInsurantInfo.setCreatedBy("system");
		fetMobilePolicyInsurantInfo.setCreatedTime(sysdate);
		
		return fetMobilePolicyInsurantInfo;
	}
	
	private FetMobilePolicyDevice getFetMobilePolicyDevice(Application application, Product product, Customer customer, Date sysdate) {
		FetMobilePolicyDevice fetMobilePolicyDevice = new FetMobilePolicyDevice();
		fetMobilePolicyDevice.setTransactionId(application.getTransactionId());
		fetMobilePolicyDevice.setProductNumber(product.getProdno());
		fetMobilePolicyDevice.setProductName(product.getProdname());
		fetMobilePolicyDevice.setType(product.getType());
		fetMobilePolicyDevice.setBrand(product.getBrand());
		fetMobilePolicyDevice.setModel(product.getModel());
		fetMobilePolicyDevice.setProductId(product.getImei());
		fetMobilePolicyDevice.setInitPrice(product.getRrp());
		fetMobilePolicyDevice.setFileName(application.getProposalFileName());
		fetMobilePolicyDevice.setPhoneNumber(customer.getMsisdn());
		fetMobilePolicyDevice.setPurchaseDate(product.getPurchaseDate());
		fetMobilePolicyDevice.setIsFetSupply(customer.getIsFetSupply());
		fetMobilePolicyDevice.setCreatedBy("system");
		fetMobilePolicyDevice.setCreatedTime(sysdate);
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程  只有遠傳保代的資料要0元保單入核心  START*/
		fetMobilePolicyDevice.setDataSrc(product.getDataSrc());
		fetMobilePolicyDevice.setTxId(product.getTxId());
		fetMobilePolicyDevice.setProductOrderNo(product.getProductOrderNo());
		fetMobilePolicyDevice.setProductSrc(product.getProductSrc());
		/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程  只有遠傳保代的資料要0元保單入核心  END*/
		return fetMobilePolicyDevice;
	}
}
