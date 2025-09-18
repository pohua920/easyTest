package com.tlg.aps.action;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.cibPolicyDataImportService.RunCibPolicyDataImportService;
import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.bs.petFileDownloadService.PetFileDownloadService;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.aps.webService.corePolicyService.client.GeneratePolicyService;
import com.tlg.aps.webService.corePolicyService.client.GeneratePolicyWebServiceService;
import com.tlg.aps.webService.corePolicyService.client.InsurantInfoVoByMobile;
import com.tlg.aps.webService.corePolicyService.client.MobileVo;
import com.tlg.aps.webService.corePolicyService.client.PolicyEndorseInfoListRequest;
import com.tlg.aps.webService.corePolicyService.client.PolicyEndorseInfoRequest;
import com.tlg.aps.webService.corePolicyService.client.PolicyInfoResultVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.service.FetMobilePolicyService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

@SuppressWarnings("serial")
public class APS999Action extends BaseAction {

	private RunCibPolicyDataImportService runCibPolicyDataImportService;
	private PetFileDownloadService petFileDownloadService;
	
	private RunFetPolicyService runFetPolicyService;
	private FetMobilePolicyService fetMobilePolicyService;
	private FetPolicyService fetPolicyService;
	private ConfigUtil configUtil;
	private String textValue;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	/** 負責處理查詢結果Grid */
	public String btnQuery() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
			Result result = this.runCibPolicyDataImportService.policyDataImport(userInfo);
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery2() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
			//上傳檔案
//			String responseString = this.runCibPolicyDataImportService.uploadFile("E:\\aptoap\\insco\\180\\RECASPO\\FRIFIN202007081300.180\\FRISIGIT2020070800002.tiff", "IT2020070800002");
//			System.out.println("responseString : " + responseString);
//		    FileUploadResponseVo vo = (FileUploadResponseVo)JsonUtil.getDTO(responseString, FileUploadResponseVo.class);
//		    System.out.println("status:" + vo.getStatus());
//		    System.out.println("message:" + vo.getMessage());
//		    System.out.println("uploadOid:" + vo.getUploadOid());
		    
		    //產生回饋檔
			Result result = this.runCibPolicyDataImportService.policyDataReturn(userInfo);
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery3() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //寵物險附件檔下載
			Result result = this.petFileDownloadService.petFileDownload(userInfo);
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery4() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //遠傳保批檔下載
			Result result = this.runFetPolicyService.downloadChFile();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery5() throws Exception {
		try{
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
			if(this.textValue == null || this.textValue.length() <= 0) {
				setMessage("請輸endorse_date(yyyyMMdd)");
				return Action.SUCCESS;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    String endorseDate = StringUtils.isNotBlank(this.textValue) ? StringUtils.trim(this.textValue): "";
		    //遠傳銷帳檔下載
			Result result = this.runFetPolicyService.downloadAcFile(endorseDate);
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery6() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //遠傳理賠檔下載
			Result result = this.runFetPolicyService.downloadClFile();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery7() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //遠傳佣金檔下載
			Result result = this.runFetPolicyService.downloadCmFile();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery8() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //行動裝置險回饋檔上傳 MOB0022 CE035
			Result result = this.runFetPolicyService.offerDailyFeedbackFileToFet();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery9() throws Exception {
		try{
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
			if(this.textValue == null || this.textValue.length() <= 0) {
				setMessage("請輸批次號rptBatchNo ex:202508020600");
				return Action.SUCCESS;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");

		    //遠傳保批單檔API呼叫  MOB0022 CE035
			Result result = this.runFetPolicyService.getDailyInsuranceDataFromFet(StringUtils.trim(this.textValue));
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
//			Result result = this.runFetPolicyService.getDailyInsuranceDataFromFet("202305311200");
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery10() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //遠傳要保及批單資料作洗錢、利關人、黑名單檢核  MOB0022 CE035
			Result result = this.runFetPolicyService.checkFetInsuranceData();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery11() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //全虹完修出件維修資料回饋-XML下載
			Result result = this.runFetPolicyService.downloadRepairXmlFile(this.textValue);
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery12() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //安達回傳保單及批單處理結果狀態更新
			Result result = this.runFetPolicyService.updatePolicyDataByChubbReturnData();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery13() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //安達回傳保單及批單處理結果狀態更新
			Result result = this.runFetPolicyService.sendMobileEpolicy();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery14() throws Exception {
		try{
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
		    
		    //安達回傳保單及批單處理結果狀態更新
			Result result = this.runFetPolicyService.getProposalFileFromFet();
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnQuery15() throws Exception {
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("System");
//			Map<String, String> params1 = new HashMap<String, String>();
//			params1.put("transactionId", this.textValue);
//			Result result = fetMobilePolicyService.findFetMobilePolicyByParams(params1);
			Result result = fetMobilePolicyService.findPolicyNoByWait();
			List<FetMobilePolicy> policyNolist =(List<FetMobilePolicy>)result.getResObject();
			if(policyNolist != null && policyNolist.size() > 0){
				ArrayList<String> errorList = new ArrayList<String>();  // 組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
				Result transferResult;
				/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 START*/
				PrpinsAgentRespVo estorePrpinsAgentRespVo = null;
				String handlerIdentifyNumber = "";
				for(FetMobilePolicy fmp : policyNolist) {
					if(EnumMobileDataSrc.ESTORE.getCode().equals(fmp.getDataSrc())) {
						handlerIdentifyNumber = StringUtils.isNotBlank(fmp.getHandlerIdentifyNumber()) ? fmp.getHandlerIdentifyNumber() : "AA1C000002";
						break;
					}
				}
				if(StringUtils.isNotBlank(handlerIdentifyNumber)) {
					estorePrpinsAgentRespVo = this.runFetPolicyService.prpinsAgentQuery(handlerIdentifyNumber);
				}
				for(FetMobilePolicy tmp : policyNolist){
					transferResult = this.runFetPolicyService.transferMiPolicyToCore(tmp, estorePrpinsAgentRespVo);
					/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END*/
					if(transferResult != null && transferResult.getResObject() != null) {
						boolean check = (Boolean)transferResult.getResObject();
						if(!check) {
							errorList.add(transferResult.getMessage().getMessageString());
						}
					}
				}
				if(errorList.size() > 0){
					this.runFetPolicyService.sendErrorMail(errorList, executeDate, "hand", "行動裝置險 - 資料匯入核心系統失敗件通知");
				}
			}
			
			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery16() throws Exception {
		try{
			//安達回傳保單及批單處理結果狀態更新
			Result result = this.runFetPolicyService.terminationNotice();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery17() throws Exception {
		try{
			//下載遠傳拆帳檔
			Result result = this.runFetPolicyService.downloadFetPaidFile();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery18() throws Exception {
		try{
			//下載安達批單檔
			Result result = this.runFetPolicyService.downloadChubbEndorseFile();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery19() throws Exception {
		try{
			//安達批單檔資料匯入線下批單資料表
			this.runFetPolicyService.chubbEndorseFileDataTransfer();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery20() throws Exception {
		try{
			//發送申請補件通知
			this.runFetPolicyService.sendProposalInsufficientSms();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery21() throws Exception {
		try{
			if(this.textValue == null || this.textValue.length() <= 0) {
				setMessage("請輸入日期(yyyymmdd)");
				return Action.SUCCESS;
			}
			//產生要保書不全取消保險名單資料產生
			this.runFetPolicyService.genFetCancelNotificationData(this.textValue);

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery22() throws Exception {
		try{
			//上傳要保書不全取消保險名單資料
			this.runFetPolicyService.uploadFetCancelNotificationData();

			setMessage("執行成功");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 START*/
	public String btnQuery23() throws Exception {
		try{
			if(this.textValue == null || this.textValue.length() <= 0) {
				setMessage("請輸批次號rptBatchNo ex:202509300500");
				return Action.SUCCESS;
			}
			// 回傳保單號給數開
			this.runFetPolicyService.returnPolicyNoToG10(StringUtils.trim(this.textValue));
//			this.runFetPolicyService.prpinsAgentQuery(StringUtils.trim(this.textValue));
			setMessage("執行成功");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 END*/
	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			UserInfo userInfo = this.getUserInfo();
			if(!"CE035".equalsIgnoreCase(userInfo.getUserId())) {
				return "redirect";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}


	/** 頁面按下清除鍵,清除所有 pageInfo的資料 */
	public String btnQueryCancel() throws Exception {
		try {

			
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
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
	private PolicyEndorseInfoRequest getPolicyEndorseInfoRequest(FetMobilePolicy fetMobilePolicy, InsurantInfoVoByMobile insurant) throws NumberFormatException, Exception{
		PolicyEndorseInfoRequest main = new PolicyEndorseInfoRequest();
		
		//主檔資料
		main.getInsurantInfoVoByMobiles().add(insurant);
		main.setFlowNo("1"); //送金單號/流水編號
		main.setOperateSite("FET"); //傳入來源
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
		main.setBusinessNature("J00123"); //業務來源
		main.setExtraComCode("00"); //單位代號
		main.setExtraComName("總公司"); //單位名稱
		main.setHandlerIdentifyNumber(fetMobilePolicy.getHandlerIdentifyNumber()); //單招攬人身分證字號
		main.setHandlerName(fetMobilePolicy.getHandlerName()); //招攬人名稱
		main.setAgentCode("349353"); //經辦代號
		main.setHandler1Code("AA002"); //服務人員
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
		
		return main;
	}

	public RunCibPolicyDataImportService getRunCibPolicyDataImportService() {
		return runCibPolicyDataImportService;
	}

	public void setRunCibPolicyDataImportService(RunCibPolicyDataImportService runCibPolicyDataImportService) {
		this.runCibPolicyDataImportService = runCibPolicyDataImportService;
	}

	public PetFileDownloadService getPetFileDownloadService() {
		return petFileDownloadService;
	}

	public void setPetFileDownloadService(PetFileDownloadService petFileDownloadService) {
		this.petFileDownloadService = petFileDownloadService;
	}

	public RunFetPolicyService getRunFetPolicyService() {
		return runFetPolicyService;
	}

	public void setRunFetPolicyService(RunFetPolicyService runFetPolicyService) {
		this.runFetPolicyService = runFetPolicyService;
	}

	public FetMobilePolicyService getFetMobilePolicyService() {
		return fetMobilePolicyService;
	}

	public void setFetMobilePolicyService(FetMobilePolicyService fetMobilePolicyService) {
		this.fetMobilePolicyService = fetMobilePolicyService;
	}

	public FetPolicyService getFetPolicyService() {
		return fetPolicyService;
	}

	public void setFetPolicyService(FetPolicyService fetPolicyService) {
		this.fetPolicyService = fetPolicyService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

}
