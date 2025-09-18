package com.tlg.aps.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.msSqlMob.entity.ApplicantEndorse;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.entity.InsuredEndorse;
import com.tlg.msSqlMob.entity.ProductEndorse;
import com.tlg.msSqlMob.service.ApplicantEndorseService;
import com.tlg.msSqlMob.service.FetMobilePolicyDeviceService;
import com.tlg.msSqlMob.service.FetMobilePolicyInsurantInfoService;
import com.tlg.msSqlMob.service.FetMobilePolicyService;
import com.tlg.msSqlMob.service.InsuredEndorseService;
import com.tlg.msSqlMob.service.ProductEndorseService;
import com.tlg.util.AppContext;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

public class ChubbEndorseDataTransferToFMPThread implements Runnable {

	protected Logger logger = Logger.getLogger(ChubbEndorseDataTransferToFMPThread.class);
	private RunFetPolicyService runFetPolicyService;
	private FetPolicyService fetPolicyService;
	private List<CustomerEndorse> list;
	private String groupNo;
	private FetMobilePolicyService fetMobilePolicyService;
	private FetMobilePolicyDeviceService fetMobilePolicyDeviceService;
	private FetMobilePolicyInsurantInfoService fetMobilePolicyInsurantInfoService;
	private ApplicantEndorseService applicantEndorseService;
	private InsuredEndorseService insuredEndorseService;
	private ProductEndorseService productEndorseService;
	
	public ChubbEndorseDataTransferToFMPThread(String groupNo, List<CustomerEndorse> list){
		this.list = list;
		this.groupNo = groupNo;
		this.runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
		this.fetPolicyService = (FetPolicyService)AppContext.getBean("fetPolicyService");
		this.fetMobilePolicyService = (FetMobilePolicyService)AppContext.getBean("fetMobilePolicyService");
		this.fetMobilePolicyDeviceService = (FetMobilePolicyDeviceService)AppContext.getBean("fetMobilePolicyDeviceService");
		this.fetMobilePolicyInsurantInfoService = (FetMobilePolicyInsurantInfoService)AppContext.getBean("fetMobilePolicyInsurantInfoService");
		this.applicantEndorseService = (ApplicantEndorseService)AppContext.getBean("applicantEndorseService");
		this.insuredEndorseService = (InsuredEndorseService)AppContext.getBean("insuredEndorseService");
		this.productEndorseService = (ProductEndorseService)AppContext.getBean("productEndorseService");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			logger.debug("ChubbEndorseDataTransferToFMPThread START： groupNo - " + this.groupNo + "," +new Date() + ", this.policyNolist.size() = " + this.list.size());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());
			String title = "行動裝置險 - 線下批單資料處理作業";
			ArrayList<String> errorList = null;
			try{
				if(list != null && list.size() > 0){
					errorList = new ArrayList<String>();  // 組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
					for(CustomerEndorse vo : list){
						String policyNo = vo.getPolicyNo();
						Map<String, String>  params = new HashMap<String, String>();
						params.put("policyNo", policyNo);
						params.put("orderByCreatedtimeDesc", "Y");
						Result result = this.fetMobilePolicyService.findFetMobilePolicyByParams(params);
						if(result.getResObject() == null){
							//缺少FetMobilePolicy資料
							errorList.add("" + "," +policyNo + "," + vo.getEndorseNo() + "," + "缺少FetMobilePolicy資料");
							continue;
						}
						ArrayList<FetMobilePolicy> fetMobilePolicyList = (ArrayList<FetMobilePolicy>)result.getResObject();
						
						FetMobilePolicy fetMobilePolicy = fetMobilePolicyList.get(0); //取第一筆
						String transactionId = fetMobilePolicy.getTransactionId();
						result = this.fetMobilePolicyInsurantInfoService.findFetMobilePolicyInsurantInfoByUK(transactionId);
						if(result.getResObject() == null){
							//缺少FetMobilePolicyInsurantInfo資料
							errorList.add(transactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "缺少FetMobilePolicyInsurantInfo資料");
							continue;
						}
						FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo = (FetMobilePolicyInsurantInfo)result.getResObject();
						
						result = this.fetMobilePolicyDeviceService.findFetMobilePolicyDeviceByUK(transactionId);
						if(result.getResObject() == null){
							//缺少FetMobilePolicyDevice資料
							errorList.add(transactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "缺少FetMobilePolicyDevice資料");
							continue;
						}
						FetMobilePolicyDevice fetMobilePolicyDevice = (FetMobilePolicyDevice)result.getResObject();
						/** B.	取得線下批單資料*/
						String endorseTransactionId = vo.getTransactionId();
						result = this.applicantEndorseService.findApplicantEndorseByUK(endorseTransactionId);
						if(result.getResObject() == null){
							//缺少ApplicantEndorse資料
							errorList.add(endorseTransactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "缺少ApplicantEndorse資料");
							continue;
						}
						ApplicantEndorse applicantEndorse = (ApplicantEndorse)result.getResObject();
						
						result = this.insuredEndorseService.findInsuredEndorseByUK(endorseTransactionId);
						if(result.getResObject() == null){
							//缺少InsuredEndorse資料
							errorList.add(endorseTransactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "缺少InsuredEndorse資料");
							continue;
						}
						InsuredEndorse insuredEndorse = (InsuredEndorse)result.getResObject();
								
						result = this.productEndorseService.findProductEndorseByUK(endorseTransactionId);
						if(result.getResObject() == null){
							//缺少ProductEndorse資料
							errorList.add(endorseTransactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "缺少ProductEndorse資料");
							continue;
						}
						ProductEndorse productEndorse = (ProductEndorse)result.getResObject();
						//處理FET_MOBILE_POLICY
						FetMobilePolicy newFetMobilePolicy = new FetMobilePolicy();
						newFetMobilePolicy.setTransactionId(endorseTransactionId);
						newFetMobilePolicy.setContractId(fetMobilePolicy.getContractId());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
						newFetMobilePolicy.setTxId(fetMobilePolicy.getTxId());
						newFetMobilePolicy.setDataSrc(fetMobilePolicy.getDataSrc());
						newFetMobilePolicy.setCtbcRcvNo(fetMobilePolicy.getCtbcRcvNo());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
						newFetMobilePolicy.setApplicationId("");
						newFetMobilePolicy.setDataType("2");
						newFetMobilePolicy.setStatus("WAIT");
						//如果金額大於0，表示為分期付款件，狀態需為READ_WAIT
						if(vo.getProjectCodePremium() != null && vo.getProjectCodePremium().trim().length() > 0) {
							int projectCodePremuim = StringUtil.stringToInteger(vo.getProjectCodePremium());
							if(projectCodePremuim > 0) {
								newFetMobilePolicy.setStatus("READY_WAIT");
							}
						}
						newFetMobilePolicy.setPolicyNo(fetMobilePolicy.getPolicyNo());
						newFetMobilePolicy.setEndorseNo(vo.getEndorseNo());
						newFetMobilePolicy.setEndorseType(vo.getEndorseType());
						newFetMobilePolicy.setEndorseText(vo.getEndorseContent());
						newFetMobilePolicy.setStartDate(vo.getStartDate());
						newFetMobilePolicy.setEndDate(vo.getEndDate());
						newFetMobilePolicy.setEndorseDate(vo.getEndorseDate());
						newFetMobilePolicy.setHandlerIdentifyNumber(fetMobilePolicy.getHandlerIdentifyNumber());
						newFetMobilePolicy.setHandlerName(fetMobilePolicy.getHandlerName());
						newFetMobilePolicy.setInsuredCode(applicantEndorse.getCustomerId());
						newFetMobilePolicy.setInsuredName(applicantEndorse.getName());
						newFetMobilePolicy.setHeadName(applicantEndorse.getResponsiblePerson());
						newFetMobilePolicy.setBirthday(applicantEndorse.getBirthday());
						newFetMobilePolicy.setPhoneTeleNumber(fetMobilePolicy.getPhoneTeleNumber());
						newFetMobilePolicy.setMobile(applicantEndorse.getTelNo());
						newFetMobilePolicy.setPostCode(applicantEndorse.getZipCode());
						newFetMobilePolicy.setPostAddress(applicantEndorse.getAddress());
						newFetMobilePolicy.setInsuredIdentity("01");//01表示與被保人關係為本人
						
						if("9".equals(applicantEndorse.getCountry())) {
							newFetMobilePolicy.setCountryEname("TW");
						} else if("2".equals(applicantEndorse.getCountry())) {
							newFetMobilePolicy.setCountryEname("CN");
						}
						
						newFetMobilePolicy.setDomicile(fetMobilePolicy.getDomicile());
						newFetMobilePolicy.setEmail(applicantEndorse.getEmailAddress());
						newFetMobilePolicy.setBranchCode(fetMobilePolicy.getBranchCode());
						newFetMobilePolicy.setBranchName(fetMobilePolicy.getBranchName());
						newFetMobilePolicy.setPayNo(vo.getPayPeriod());
						newFetMobilePolicy.setPlanStartDate(vo.getPayPeriodStartDate());
						newFetMobilePolicy.setPlanEndDate(vo.getPayPeriodEndDate());
						newFetMobilePolicy.setCreatedBy("SYSTEM");
						newFetMobilePolicy.setCreatedTime(new Date());
						
						//處理FET_MOBILE_POLICY_INSURANT_INFO
						FetMobilePolicyInsurantInfo newFetMobilePolicyInsurantInfo = new FetMobilePolicyInsurantInfo();
						newFetMobilePolicyInsurantInfo.setTransactionId(endorseTransactionId);
						newFetMobilePolicyInsurantInfo.setInsuredCode(insuredEndorse.getCustomerId());
						newFetMobilePolicyInsurantInfo.setInsuredName(insuredEndorse.getName());
						newFetMobilePolicyInsurantInfo.setBirthday(insuredEndorse.getBirthday());
						newFetMobilePolicyInsurantInfo.setPhoneTeleNumber("");
						newFetMobilePolicyInsurantInfo.setMobile(insuredEndorse.getTelNo());
						newFetMobilePolicyInsurantInfo.setPostCode(insuredEndorse.getZipCode());
						newFetMobilePolicyInsurantInfo.setPostAddress(insuredEndorse.getAddress());
						if("9".equals(insuredEndorse.getCountry())) {
							newFetMobilePolicyInsurantInfo.setCountryEname("TW");
						} else if("2".equals(insuredEndorse.getCountry())) {
							newFetMobilePolicyInsurantInfo.setCountryEname("CN");
						}
						
						newFetMobilePolicyInsurantInfo.setDomicile(fetMobilePolicyInsurantInfo.getDomicile());
						newFetMobilePolicyInsurantInfo.setEmail(applicantEndorse.getEmailAddress());
						newFetMobilePolicyInsurantInfo.setProjectCode(fetMobilePolicyInsurantInfo.getProjectCode());
						newFetMobilePolicyInsurantInfo.setProjectCodePremium(vo.getProjectCodePremium());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
						// TODO 這邊要再確認一下退費金額這樣寫有沒有問題
						if(vo.getRefundAmount() > 0) {
							newFetMobilePolicyInsurantInfo.setProjectCodePremium(String.valueOf(0-vo.getRefundAmount()));
						}
						newFetMobilePolicyInsurantInfo.setDataSrc(fetMobilePolicyInsurantInfo.getDataSrc());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
						newFetMobilePolicyInsurantInfo.setHeadName(insuredEndorse.getResponsiblePerson());
						newFetMobilePolicyInsurantInfo.setCreatedBy("SYSTEM");
						newFetMobilePolicyInsurantInfo.setCreatedTime(new Date());
						
						//處理FET_MOBILE_POLICY_DEVICE
						FetMobilePolicyDevice newFetMobilePolicyDevice = new FetMobilePolicyDevice();
						newFetMobilePolicyDevice.setTransactionId(endorseTransactionId);
						newFetMobilePolicyDevice.setProductNumber(productEndorse.getProdno());
						newFetMobilePolicyDevice.setProductName(productEndorse.getProdname());
						newFetMobilePolicyDevice.setType(productEndorse.getType());
						newFetMobilePolicyDevice.setBrand(productEndorse.getBrand());
						newFetMobilePolicyDevice.setModel(productEndorse.getModel());
						newFetMobilePolicyDevice.setProductId(productEndorse.getImei());
						newFetMobilePolicyDevice.setInitPrice(productEndorse.getRrp());
						newFetMobilePolicyDevice.setFileName(fetMobilePolicyDevice.getFileName());
						newFetMobilePolicyDevice.setPhoneNumber(fetMobilePolicyDevice.getPhoneNumber());
						newFetMobilePolicyDevice.setPurchaseDate(productEndorse.getPurchaseDate());
						newFetMobilePolicyDevice.setIsFetSupply(fetMobilePolicyDevice.getIsFetSupply());
						newFetMobilePolicyDevice.setCreatedBy("SYSTEM");
						newFetMobilePolicyDevice.setCreatedTime(new Date());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
						newFetMobilePolicyDevice.setDataSrc(fetMobilePolicyDevice.getDataSrc());
						newFetMobilePolicyDevice.setTxId(fetMobilePolicyDevice.getTxId());
						newFetMobilePolicyDevice.setProductSrc(fetMobilePolicyDevice.getProductSrc());
						newFetMobilePolicyDevice.setProductOrderNo(fetMobilePolicyDevice.getProductOrderNo());
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
						
						try{
							boolean boo = this.fetPolicyService.insertEndorseData(newFetMobilePolicy, newFetMobilePolicyInsurantInfo, newFetMobilePolicyDevice);
							if(!boo){
								vo.setDataStatus("03"); //失敗
							}else{
								vo.setDataStatus("02"); //完成
							}

							vo.setModifiedBy("SYSTEM9");
							vo.setModifiedTime(new Date());
							result = this.fetPolicyService.updateCustomerEndorse(vo);
						}catch (Exception e) {
							e.printStackTrace();
							
							vo.setDataStatus("03"); //失敗
							vo.setModifiedBy("SYSTEM9");
							vo.setModifiedTime(new Date());
							result = this.fetPolicyService.updateCustomerEndorse(vo);
							
							//發生異常
							errorList.add(endorseTransactionId + "," + policyNo + "," + vo.getEndorseNo() + "," + "新增或修改資料時發生異常" + e.getMessage());
							continue;
						}
					}
				}else{
					logger.debug("this.list is null ，線下批單資料處理作業 執行結束");
				}
			}catch(Exception e){
				e.printStackTrace();
				
			}finally{
				if(errorList != null && errorList.size() > 0){
					this.runFetPolicyService.sendErrorMail(errorList, executeDate, groupNo, title);
				}
			}
			logger.debug("ChubbEndorseDataTransferToFMPThread END： groupNo - " + this.groupNo + "," +new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

