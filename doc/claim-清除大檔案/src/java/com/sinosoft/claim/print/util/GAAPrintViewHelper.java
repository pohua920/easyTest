/**
 * 2014-6-10
 */
package com.sinosoft.claim.print.util;

import ins.framework.common.QueryRule;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctbcins.util.print.PrintViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.print.vo.CompensateContextObject;
import com.sinosoft.claim.print.vo.CompensateObject;
import com.sinosoft.claim.print.vo.CompensateSubreportObject;
import com.sinosoft.claim.print.vo.GAAClaimApplicationObject;
import com.sinosoft.claim.print.vo.GAACommissionedObject;
import com.sinosoft.claim.print.vo.GAACompensateObject;
import com.sinosoft.claim.print.vo.GAAContractObject;
import com.sinosoft.claim.print.vo.GAAInvestigativeObject;
import com.sinosoft.claim.print.vo.GAANotificationObject;
import com.sinosoft.claim.print.vo.GAAReceiptObject;
import com.sinosoft.claim.print.vo.GAAReconciliationObject;
import com.sinosoft.claim.print.vo.GAAReinsCompensateObject;
import com.sinosoft.claim.print.vo.GAARemittanceObject;
import com.sinosoft.claim.print.vo.GAARemnantObject;
import com.sinosoft.claim.print.vo.GAAReplevyReportObject;
import com.sinosoft.claim.print.vo.GAARevocationObject;
import com.sinosoft.claim.remnant.service.facade.RemnantService;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainConstruct;
import com.sinosoft.claim.schema.model.PrpCmainLiab;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLremnant;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainConstructService;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpJPayRefRecHisService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 收集工程险列印所需数据
 * @author 中科軟
 */
//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書
public class GAAPrintViewHelper extends PrintViewHelper {
	private PrpLcompensateService prpLcompensateService;	
	private PrpLclaimService prpLclaimService;	
	private PrpDriskService prpDriskService;	
	private PrpCinsuredNatureService prpCinsuredNatureService;	
	private PrpCinsuredService prpCinsuredService;	
	private PrpDclassService prpDclassService;	
	private PrpCmainService prpCmainService;	
	private PrpLregistService prpLregistService;	
	private PrpLcheckService prpLcheckService;	
	private PrpCmainConstructService prpCmainConstructService;
	/**  残余物对象 */
	private RemnantService remnantService;
	/** 代码转换对象  */
	private CodeService codeService;
	private EndorseViewHelper endorseViewHelper;
	private PolicyService policyService;
	private PrpJPayRefRecHisService prpJPayRefRecHisService;
	private PrpPheadService prpPheadService;
	private CompensateService compensateService;
	private PrpLctextService prpLctextService;
	private PrpLchargeService prpLchargeService;
	private PrpCitemKindService prpCitemKindService;
	private PrpLexternalAgencyService prpLexternalAgencyService;
	private PrpCmainLiabService prpCmainLiabService;
	private PrpCaddressService prpCaddressService;
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
	/** 用户基本信息Service */
	private PrpDuserService prpDuserService;
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
	
	/**
	 * 追償計算書
	 * mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public GAAReplevyReportObject findGAAReplevyReportObjectByCompensateNo(String compensateNo) throws Exception{
		GAAReplevyReportObject GAAReplevyReportObject = new GAAReplevyReportObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		GAAReplevyReportObject.setPolicyNo(prpLcompensate.getPolicyNo());
		GAAReplevyReportObject.setClaimNo(prpLcompensate.getClaimNo());
		GAAReplevyReportObject.setCompensateNo(compensateNo);
		//多个批单号用 ，隔开
		String endorseNo = "";
		for(PrpPhead prpPhead : this.prpPheadService.findByPolicyNo(prpLcompensate.getPolicyNo())){
			endorseNo += prpPhead.getEndorseNo() + "，";
		}
		GAAReplevyReportObject.setEndorseNo(endorseNo.length()>0?endorseNo.substring(0, endorseNo.length() - 1):endorseNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		GAAReplevyReportObject.setInsuredName(prpLclaim.getInsuredName());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		GAAReplevyReportObject.setSumAmount(decimalFormat.format(prpCmain.getSumAmount()));
		GAAReplevyReportObject.setDamageDate(PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(), prpLclaim.getDamageStartHour()));
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		GAAReplevyReportObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		GAAReplevyReportObject.setReplevyAmount(decimalFormat.format(prpLcompensate.getSumDutyPaid()*-1));
//		追 償 核 定 那块数据未收集完全
		List<PrpLcharge> prpLchargeList = prpLchargeService.findPrpLchargeList(compensateNo);
		double legalFee = 0D;//律师费
		double notarialFee = 0D;//公证费
		double othersFee = 0D; //另外的费用
		for(PrpLcharge prpLcharge : prpLchargeList){
			if("S".equals(prpLcharge.getChargeCode())){
				legalFee += prpLcharge.getChargeAmount();
			}else if("5".equals(prpLcharge.getChargeCode())){
				notarialFee += prpLcharge.getChargeAmount();
			}else{
				othersFee += prpLcharge.getChargeAmount();
			}
		}
		GAAReplevyReportObject.setLegalFee(decimalFormat.format(legalFee));
		GAAReplevyReportObject.setNotarialFee(decimalFormat.format(notarialFee));
		GAAReplevyReportObject.setOthersFee(decimalFormat.format(othersFee));
		GAAReplevyReportObject.setSumFee(decimalFormat.format(prpLcompensate.getSumPaid()));
		GAAReplevyReportObject.setPaidFee(decimalFormat.format(prpLcompensate.getSumNoDutyFee()));
		GAAReplevyReportObject.setClaimSumPaid(decimalFormat.format(compensateService.getClaimSumPaidByClaimNo(prpLcompensate.getClaimNo())));
		GAAReplevyReportObject.setClaimSumFee(decimalFormat.format(compensateService.getClaimSumFeeByClaimNo(prpLcompensate.getClaimNo())));
		getReplevyHandler(GAAReplevyReportObject, compensateNo);
		getContextByCompensateNo(GAAReplevyReportObject, compensateNo);
		
		return GAAReplevyReportObject;
	}
	/**
	 * 理賠申請書  收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAClaimApplicationObject findGAAClaimApplicationObjectByClaimNo(String claimNo) throws Exception{
		GAAClaimApplicationObject gaaRemittanceObject = new GAAClaimApplicationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		gaaRemittanceObject.setClaimNo(claimNo);
		gaaRemittanceObject.setPolicyNo(prpLclaim.getPolicyNo());
		gaaRemittanceObject.setStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));
		gaaRemittanceObject.setEndDate(PrintUtils.getYearToDayMGName(prpLclaim.getEndDate()));
		gaaRemittanceObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		gaaRemittanceObject.setDamageAddress(prpLclaim.getDamageAddress());
		gaaRemittanceObject.setDamageName(prpLclaim.getDamageName());
		gaaRemittanceObject.setSumAmount(String.valueOf(prpLclaim.getSumAmount()));		
		gaaRemittanceObject.setInsuredName(prpLclaim.getInsuredName());
		
		PrpDclass prpDclass = prpDclassService.findPrpDclass(prpLclaim.getClassCode());
		gaaRemittanceObject.setClassName(prpDclass.getClassName());
		
		PrpLregist  prpLregist  = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		gaaRemittanceObject.setLinkman(prpLregist.getLinkerName());
		gaaRemittanceObject.setLinkPhone(prpLregist.getPhoneNumber());
		PrpCmainConstruct prpCmainConstruct = prpCmainConstructService.findPrpCmainConstruct(prpLclaim.getPolicyNo());
		if (prpCmainConstruct!=null) {
			gaaRemittanceObject.setProjectName(prpCmainConstruct.getConstructName());
		}
		
		return gaaRemittanceObject;
	}
	/**
	 * 匯款同意書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public GAARemittanceObject findGAARemittanceObjectByCompensateNo(String compensateNo) throws Exception {
		GAARemittanceObject gaaRemittanceObject = new GAARemittanceObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);	
		
		gaaRemittanceObject.setClaimNo(prpLcompensate.getClaimNo());
		gaaRemittanceObject.setPolicyNo(prpLcompensate.getPolicyNo());
		gaaRemittanceObject.setClaimAmount(String.valueOf(prpLcompensate.getSumPaid()));
		return gaaRemittanceObject;
	}
	/**
	 * 賠款同意書暨領款收據  收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAReceiptObject findGAAReceiptObjectByClaimNo(String claimNo) throws Exception{
		GAAReceiptObject gaaReceiptObject = new GAAReceiptObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);
		gaaReceiptObject.setClaimNo(claimNo);
		gaaReceiptObject.setAddress(prpCinsured.getRoomAddress());
		gaaReceiptObject.setAppliIdentifyNumber(prpCinsured.getIdentifyNumber());
		gaaReceiptObject.setInsuredName(prpCinsured.getInsuredName());
		gaaReceiptObject.setLinkPhone(prpCinsured.getPhoneNumber());
		gaaReceiptObject.setPolicyNo(prpLclaim.getPolicyNo());
		
		return gaaReceiptObject;
	}
	/**
	 * 委託公證申請單 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAACommissionedObject findGAACommissionedObjectByClaimNo(String claimNo,String comName,String userName) throws Exception{
		GAACommissionedObject gaaCommissionedObject = new GAACommissionedObject();
		
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		PrpLregist prpLregist  = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		PrpLcheck prpLcheck = prpLcheckService.findPrpLcheck(new PrpLcheckId(prpLclaim.getRegistNo(), 1));
		
//		String currentDate = DateUtil.getCurrentDate();
//		String currentYear = StringUtil.NullToStrinNull(currentDate.substring(0, 4));
//		String remainStr = currentDate.substring(4);
//		if(!"".equals(currentYear)){
//			gaaCommissionedObject.setCurrentTime((Integer.parseInt(currentYear) - 1911)+remainStr);
//		}
		gaaCommissionedObject.setCurrentTime(PrintUtils.getYearToDayMGName(new Date()));
		gaaCommissionedObject.setComName(comName);
		gaaCommissionedObject.setName(userName);
		gaaCommissionedObject.setClaimNo(claimNo);
		gaaCommissionedObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		
		gaaCommissionedObject.setFirstLinkMan(prpLregist.getLinkerName());
		if(prpLcheck!=null){
			gaaCommissionedObject.setRemark(prpLcheck.getDamageName());
			gaaCommissionedObject.setEstimateLoss(String.valueOf(prpLcheck.getEstimateLoss()));
		}
		gaaCommissionedObject.setDamageAddress(prpLclaim.getDamageAddress());
		//待定 ？？
		gaaCommissionedObject.setEscrowCompany("");
		gaaCommissionedObject.setLinkMan(""+"先生");
		gaaCommissionedObject.setCompanyAdress("");
		gaaCommissionedObject.setPhoneNumber("");
		gaaCommissionedObject.setFaxNumber("");
		
		return gaaCommissionedObject;
	}
	/**
	 * 債權讓與契約暨通知書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAContractObject findGAAGAAContractObjectByClaimNo(String claimNo) throws Exception{
		GAAContractObject gaaContractObject = new GAAContractObject();
		
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);
		PrpDclass prpDclass = prpDclassService.findPrpDclass(prpLclaim.getClassCode());
		gaaContractObject.setClassName(prpDclass.getClassName());
		gaaContractObject.setAddress(prpCinsured.getRoomAddress());
		gaaContractObject.setAppliIdentifyNumber(prpCinsured.getIdentifyNumber());
		gaaContractObject.setInsuredName(prpCinsured.getInsuredName());
		gaaContractObject.setPolicyNo(prpLclaim.getPolicyNo());
		gaaContractObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		
		return gaaContractObject;
	}
	
	/**
	 * 撤銷申請理賠同意書  收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAARevocationObject findGAARevocationObjectByClaimNo(String claimNo) throws Exception{
		GAARevocationObject gaaRevocationObject = new GAARevocationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		gaaRevocationObject.setClaimNo(claimNo);
		gaaRevocationObject.setPolicyNo(prpLclaim.getPolicyNo());
		System.out.println("DamageStartDate:"+prpLclaim.getDamageStartDate().toString());
		gaaRevocationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		
		gaaRevocationObject.setClaimNo(claimNo);
		
		return gaaRevocationObject;
	}
	/**
	 * 補件通知函  收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAANotificationObject findGAANotificationObjectByClaimNo(String claimNo) throws Exception{
		GAANotificationObject gaaNotificationObject = new GAANotificationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		gaaNotificationObject.setClaimNo(claimNo);
		gaaNotificationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		PrpDrisk prpDrisk = prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
		
		gaaNotificationObject.setRiskCname(prpDrisk.getRiskCName());
		
		return gaaNotificationObject;
	}
	/**
	 * 查案單 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAInvestigativeObject findGAAInvestigativeObjectByClaimNo(String claimNo) throws Exception{
		GAAInvestigativeObject gaaInvestigativeObject = new GAAInvestigativeObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		gaaInvestigativeObject.setClaimNo(claimNo);
		gaaInvestigativeObject.setInsuredName(prpLclaim.getInsuredName());
		gaaInvestigativeObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		
		return gaaInvestigativeObject;
	}
	/**
	 *  和解書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAReconciliationObject findGAAReconciliationObjectByClaimNo(String claimNo) throws Exception{
		GAAReconciliationObject gaaReconciliationObject = new GAAReconciliationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);	
		gaaReconciliationObject.setInsuredName(prpLclaim.getInsuredName());
		gaaReconciliationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));
		
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);
		gaaReconciliationObject.setIdentifyNumber(prpCinsured.getIdentifyNumber());
		
		QueryRule queryRule2 = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsuredNature prpCinsuredNature =prpCinsuredNatureService.findPrpCinsuredNature(queryRule2).get(0);
		gaaReconciliationObject.setRoomAddress(prpCinsuredNature.getRoomAddress());
		
		return gaaReconciliationObject;
	}
	/**
	 *  残余物理算书 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAARemnantObject findGAARemnantObjectByCompensateNo(String compensateNo) throws Exception{
		GAARemnantObject gaaRemnantObject = new GAARemnantObject();
		gaaRemnantObject = this.findRemnantObjectByCompensateNo(compensateNo,gaaRemnantObject);
		return gaaRemnantObject;
	}
	/**
	 *  残余物理算书 收集数据,收集工程险，责任险，水险公共的信息。
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public <T extends CompensateObject> T findRemnantObjectByCompensateNo(String compensateNo,T remnantObject) throws Exception{
		DecimalFormat df = new DecimalFormat("#,###");
		remnantObject = this.compensateObjectByCompensateNo(compensateNo,remnantObject);
		RemnantDto remnantDto = remnantService.findByPrimaryKey(compensateNo);
		PrpLcompensate prpLcompensate = remnantDto.getPrpLcompensate();
		String querySql = "select centerCode from prplplan where certino='" + prpLcompensate.getCompensateNo() + "'";
		List<?> tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
		String billingUnit = "";
		if (!CommonUtils.isEmpty(tempResult)) {// 开票单位
			billingUnit = tempResult.get(0).toString();
		}
		
		Map<String,PrpLpayObjectInfo> payObjectInfo = new HashMap<String,PrpLpayObjectInfo>(remnantDto.getPrpLpayObjectInfoList().size());
		for(PrpLpayObjectInfo prpLpayObjectInfo : remnantDto.getPrpLpayObjectInfoList()){
			payObjectInfo.put(prpLpayObjectInfo.getId().getCertiType()+prpLpayObjectInfo.getId().getSerialNo(), prpLpayObjectInfo);
		}
		//费用明细
		List<CompensateSubreportObject> gaaRemnantSubreport1Object = remnantObject.getCompensateSubreport1Object();
		CompensateSubreportObject subreportObject = null;
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		for(PrpLcharge prpLcharge : remnantDto.getPrpLchargeList()){
			subreportObject = new CompensateSubreportObject();
			prpLpayObjectInfo = payObjectInfo.get(PrpLpayObjectInfo.CERTITYPE_CHARGE+prpLcharge.getId().getSerialNo());
			subreportObject.setOwnerName(prpLpayObjectInfo.getOwnerName());
			subreportObject.setUniformNo(prpLpayObjectInfo.getUniformNo());
			subreportObject.setPayAmount(df.format(prpLpayObjectInfo.getPayAmount()));
			subreportObject.setCustomBankName(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getBankName()));
			subreportObject.setCurrency(prpLcharge.getCurrency());
			subreportObject.setCustomBankFullName(CommonUtils.getCustomBankFullName(prpLpayObjectInfo));
			subreportObject.setBillingUnit(billingUnit);
			gaaRemnantSubreport1Object.add(subreportObject);
		}
		//冲账明细
		List<CompensateSubreportObject> gaaRemnantSubreport0Object = remnantObject.getCompensateSubreport0Object();
		for(PrpLremnant prpLremnant : remnantDto.getPrpLremnantList()){
			subreportObject = new CompensateSubreportObject();
			subreportObject.setKindCode(prpLremnant.getKindCode());
			subreportObject.setKindName(prpLremnant.getKindName());
			subreportObject.setCurrency(prpLremnant.getCurrency());
			subreportObject.setRealPaid(df.format(prpLremnant.getRealPay()*prpLremnant.getExchRate()));
			gaaRemnantSubreport0Object.add(subreportObject);
		}
		
		return remnantObject;
	}
	/**
	 *  理賠計算書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAACompensateObject findGAACompensateObjectByCompensateNo(String compensateNo) throws Exception{
		GAACompensateObject gaaCompensateObject = new GAACompensateObject();
		gaaCompensateObject = this.findCompensateObjectByCompensateNo(compensateNo,gaaCompensateObject);
		return gaaCompensateObject;
	}
	/**
	 *  赔款理算书 收集数据,收集工程险，责任险，水险公共的信息。
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public <T extends CompensateObject> T findCompensateObjectByCompensateNo(String compensateNo,T compensateObject) throws Exception{
		DecimalFormat df = new DecimalFormat("#,###");
		compensateObject = this.compensateObjectByCompensateNo(compensateNo,compensateObject);
		CompensateDto compensateDto = compensateService.findByPrimaryKey(compensateNo);
		PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
		String querySql = "select centerCode from prplplan where certino='" + prpLcompensate.getCompensateNo() + "'";
		List<?> tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
		String billingUnit = "";
		if (!CommonUtils.isEmpty(tempResult)) {// 开票单位
			billingUnit = tempResult.get(0).toString();
		}
		
		Map<String,PrpLpayObjectInfo> payObjectInfo = new HashMap<String,PrpLpayObjectInfo>(compensateDto.getPrpLpayObjectInfoList().size());
		//公证公司对应费用序号
		Map<String,List<?>> chargeMap = PrintUtils.getExternalAgencySerialno(compensateDto.getPrpLchargeList());
		List<Integer> resultList = (List<Integer>) chargeMap.get(PrintUtils.serialNoList);
		List<PrpLcharge> chargeList = (List<PrpLcharge>) chargeMap.get(PrintUtils.chargeList);
		//费用明细
		List<CompensateSubreportObject> gaaRemnantSubreport1Object = compensateObject.getCompensateSubreport1Object();
		CompensateSubreportObject subreportObject = null;
		int serialNo = 1;
		for(PrpLpayObjectInfo prpLpayObjectInfo : compensateDto.getPrpLpayObjectInfoList()){
			payObjectInfo.put(prpLpayObjectInfo.getId().getCertiType() + prpLpayObjectInfo.getId().getSerialNo(), prpLpayObjectInfo);
			if (PrpLpayObjectInfo.CERTITYPE_CHARGE.equals(prpLpayObjectInfo.getId().getCertiType())&&resultList.contains(prpLpayObjectInfo.getId().getSerialNo())) {
				continue;//公证费支付对象不需在此列印
			}
			subreportObject = new CompensateSubreportObject();
			subreportObject.setSerialNo(String.valueOf(serialNo++));
			subreportObject.setOwnerName(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getOwnerName()));
			subreportObject.setUniformNo(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getUniformNo()));
			subreportObject.setPayAmount(df.format(prpLpayObjectInfo.getPayAmount()));
			subreportObject.setCustomBankName(DataUtils.dbNullToEmpty(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getBankName())));
			subreportObject.setCustomBankFullName(CommonUtils.getCustomBankFullName(prpLpayObjectInfo));
			subreportObject.setBillingUnit(billingUnit);
			gaaRemnantSubreport1Object.add(subreportObject);
		}
		List<CompensateSubreportObject> gaaRemnantSubreport2Object = compensateObject.getCompensateSubreport2Object();
		PrpLpayObjectInfo prpLpayObjectInfo = null;
		String agentNo = null;
		PrpLexternalAgency prpLexternalAgency = null;
		for(PrpLcharge prpLcharge : chargeList){
			subreportObject = new CompensateSubreportObject();
			agentNo = null;
			if(DataUtils.emptyToNull(prpLcharge.getPayObjectCode())!=null){
				prpLexternalAgency = prpLexternalAgencyService.findPrpLexternalAgency(prpLcharge.getPayObjectCode());
				if(prpLexternalAgency!=null){
					agentNo = prpLexternalAgency.getAgentNo();
				}
			}
			subreportObject.setSerialNo(DataUtils.dbNullToEmpty(agentNo));
			prpLpayObjectInfo = payObjectInfo.get(PrpLpayObjectInfo.CERTITYPE_CHARGE+prpLcharge.getId().getSerialNo());
			subreportObject.setOwnerName(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getOwnerName()));
			subreportObject.setUniformNo(DataUtils.dbNullToEmpty(prpLpayObjectInfo.getUniformNo()));
			subreportObject.setPayAmount(df.format(prpLpayObjectInfo.getPayAmount()));
			subreportObject.setCustomBankName(prpLpayObjectInfo.getBankName());
			subreportObject.setCurrency(DataUtils.dbNullToEmpty(prpLcharge.getCurrency()));
			subreportObject.setCustomBankFullName(CommonUtils.getCustomBankFullName(prpLpayObjectInfo));
			subreportObject.setBillingUnit(billingUnit);
			gaaRemnantSubreport2Object.add(subreportObject);
		}
		//财产损失
		List<PrpCitemKind> tempList = this.prpCitemKindService.findByConditions(" policyNo = '"+prpLcompensate.getPolicyNo()+"'");
		Map<String,String> kindMap = new HashMap<String,String>();
		for(PrpCitemKind p : tempList){
			kindMap.put(p.getKindCode(), p.getKindName());
		}
		List<CompensateSubreportObject> gaaRemnantSubreport0Object = compensateObject.getCompensateSubreport0Object();
		for(PrpLloss prpLloss : compensateDto.getPrpLlossList()){
			subreportObject = new CompensateSubreportObject();
			subreportObject.setKindCode(prpLloss.getKindCode());
			subreportObject.setKindName(DataUtils.dbNullToEmpty(kindMap.get(prpLloss.getKindCode())));
			subreportObject.setAmount(df.format(prpLloss.getAmount()));
			subreportObject.setDeductible(df.format(prpLloss.getDeductible()));
			subreportObject.setDeductibleRate(PrintUtils.getDoubleToStr(prpLloss.getDeductiblerate()));
			subreportObject.setCurrency(prpLloss.getCurrency());
			subreportObject.setItemCode(DataUtils.dbNullToEmpty(prpLloss.getItemCode()));
			subreportObject.setItemName(DataUtils.dbNullToEmpty(prpLloss.getLossName()));
			subreportObject.setPayAmount(df.format(prpLloss.getSumRealPay() * prpLloss.getExchRate()));
			gaaRemnantSubreport0Object.add(subreportObject);
		}
		//人伤损失。
		Map<String,PrpLpersonLoss> personLossMap = new HashMap<String,PrpLpersonLoss>();
		for(PrpLpersonLoss prpLpersonLoss : compensateDto.getPrpLpersonLossList()){
			subreportObject = new CompensateSubreportObject();
			if(!personLossMap.containsKey(compensateNo+prpLpersonLoss.getPersonNo())){
				subreportObject.setKindCode(prpLpersonLoss.getKindCode());
				subreportObject.setKindName(DataUtils.dbNullToEmpty(kindMap.get(prpLpersonLoss.getKindCode())));
				subreportObject.setAmount(df.format(prpLpersonLoss.getAmount()));
				subreportObject.setDeductible(df.format(prpLpersonLoss.getDeductible()));
				subreportObject.setDeductibleRate(PrintUtils.getDoubleToStr(prpLpersonLoss.getDeductiblerate()));
				subreportObject.setCurrency(prpLpersonLoss.getCurrency());
				gaaRemnantSubreport0Object.add(subreportObject);
				personLossMap.put(compensateNo+prpLpersonLoss.getPersonNo(), prpLpersonLoss);
			}
		}
		/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
		//經辦人信息查詢
		String handleCode = "";//總公司經辦人code
		String handleName = "";//總公司經辦人name
		String handleCode1 = "";//分公司經辦人code
		String handleName1 = "";//分公司經辦人name
		PrpLclaim prpLclaim  = compensateDto.getPrpLclaim();
		if(!CommonUtils.isEmpty(prpLclaim.getHandlerCode())){
			String userCode = prpLclaim.getHandlerCode();
			PrpDuser prpDuser = this.prpDuserService.findPrpDuser( userCode);
			if("00".equals(prpDuser.getComCode())){
				handleCode = userCode;
				handleName = prpDuser.getUserName();
			}else {
				handleCode1 = userCode;
				handleName1 = prpDuser.getUserName();
			}
		}
		//服務人員信息查詢
		String handler1Code2 = prpLregistService.findPrpLregist(prpLclaim.getRegistNo()).getHandler1Code();
		String handler1Name = this.getCodeService().translateUserCode(handler1Code2, true);
		//參數放入
		compensateObject.setHandleCode(handleCode);
		compensateObject.setHandleName(handleName);
		compensateObject.setHandleCode1(handleCode1);
		compensateObject.setHandleName1(handleName1);
		compensateObject.setHandler1Name(handler1Name);
		/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
		return compensateObject;
	}

	/**
	 *  计算书 收集数据,收集工程险，责任险，水险公共的信息。残余物和理算都共用信息
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public <T extends CompensateObject> T compensateObjectByCompensateNo(String compensateNo,T compensateObject) throws Exception{
		DecimalFormat df = new DecimalFormat("#,###");
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		compensateObject.setRiskCodeName(codeService.translateRiskCode(prpLcompensate.getRiskCode(), true));
		compensateObject.setCompensateNo(prpLcompensate.getCompensateNo());
		String times = String.valueOf(Integer.parseInt(prpLcompensate.getCompensateNo().substring(prpLcompensate.getCompensateNo().length()-2)));
		compensateObject.setTimes(times);
		compensateObject.setPolicyNo(prpLcompensate.getPolicyNo());
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
		compensateObject.setInsuredName(prpLclaim.getInsuredName());
		for(PrpCinsured prpCinsured : prpCinsuredList){
			// 被保险人资料
			if ("1".equals(prpCinsured.getInsuredFlag()) && (DataUtils.dbNullToEmpty(prpLclaim.getInsuredCode()).equals(DataUtils.dbNullToEmpty(prpCinsured.getInsuredCode()))||DataUtils.dbNullToEmpty(prpLclaim.getInsuredCode()).equals(DataUtils.dbNullToEmpty(prpCinsured.getIdentifyNumber())))) {
				compensateObject.setInsuredIdentifyNumber(prpCinsured.getIdentifyNumber());
			}
			// 要保人资料
			if ("2".equals(prpCinsured.getInsuredFlag())) {
				compensateObject.setAppliName(DataUtils.dbNullToEmpty(prpCinsured.getInsuredName()));
				compensateObject.setAppliIdentifyNumber(DataUtils.dbNullToEmpty(prpCinsured.getIdentifyNumber()));
			}
			if (DataUtils.emptyToNull(compensateObject.getInsuredIdentifyNumber()) != null && DataUtils.emptyToNull(compensateObject.getAppliIdentifyNumber()) != null) {
				break;
			}
		}
		int intReturn = policyService.checkPay(" policyNo = '"+prpLclaim.getPolicyNo()+"'");
		// 收费情形 -1为未缴费，0为未缴全，1为缴全
		if (intReturn < 0) {
			compensateObject.setIntReturn("未繳費");
		} else if (intReturn == 0) {
			compensateObject.setIntReturn("未繳全");
		} else if (intReturn == 1) {
			compensateObject.setIntReturn("繳全");
		}
		String querySql = "select payRefDate from prpJPayRefRecHis where policyno = '" + prpLclaim.getPolicyNo() + "' and realpayrefflag = '1' and certitype='P'";
		List<?> tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
		if (!CommonUtils.isEmpty(tempResult)) {// 收费日期
			compensateObject.setPayRefDate(PrintUtils.getYearToDayMGStr(new Date(((Timestamp) tempResult.get(0)).getTime())));
		}
		querySql = "select billEndDate From prpjfeebillsub Where businessno In (select businessNo From prpjpayinfo Where certino='" + prpLclaim.getPolicyNo() + "')";
		tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
		if (!CommonUtils.isEmpty(tempResult)) {// 票据到期日
			compensateObject.setBillEndDate(PrintUtils.getYearToDayMGStr(CommonUtils.toYearToDayDate(tempResult.get(0).toString())));
		}
		//	批改日期
		querySql = " policyno = '" + prpLcompensate.getPolicyNo() + "' and validdate < to_date('" + new DateTime(prpLclaim.getDamageStartDate(), DateTime.YEAR_TO_DAY) + "','yyyy-mm-dd') order by validdate desc";
		List<PrpPhead> prpPheadList = prpPheadService.findByQueryConditions(querySql);
		if (!CommonUtils.isEmpty(prpPheadList)) {
			PrpPhead prpPhead = prpPheadList.get(0);
			compensateObject.setValidDate(PrintUtils.getYearToDayMGStr(prpPhead.getValidDate()));
		}
//		querySql = "select centerCode from prplplan where certino='" + prpLcompensate.getCompensateNo() + "'";
//		tempResult = prpJPayRefRecHisService.findByQueryConditions(querySql);
//		String billingUnit = "";
//		if (!CommonUtils.isEmpty(tempResult)) {// 开票单位
//			billingUnit = tempResult.get(0).toString();
//		}
		compensateObject.setInputDate(PrintUtils.getYearToDayMGStr(prpCmain.getUnderwriteEndDate()));
		compensateObject.setDamageStartDate(PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));
		if(!CommonUtils.isEmpty(prpLclaim.getReceiptDate())){
			String dateY = null;
			String dateM = "";
			if(prpLclaim.getReceiptDate().length()>10){
				dateY = prpLclaim.getReceiptDate().substring(0, 10);
				dateM = prpLclaim.getReceiptDate().substring(10);
			}else{
				dateY = prpLclaim.getReceiptDate();
			}
			SimpleDateFormat yearToDayformatMG = new SimpleDateFormat("yyyy-MM-dd");
			//mantis：CLM0165，處理人員：DP0713，需求單編號：電子設備計算書欄位調整
			compensateObject.setReceiptDate(CommonUtils.getMGDateStr(yearToDayformatMG.parse(dateY),yearToDayformatMG));
		}
		compensateObject.setEndCaseDate(PrintUtils.getYearToDayMGStr(prpLcompensate.getInputDate()));
		String startDate = PrintUtils.getYearToDayMGStr(prpLclaim.getStartDate())+" "+prpLclaim.getStartHour()+" 時起   "+PrintUtils.getYearToDayMGStr(prpLclaim.getEndDate())+" "+prpLclaim.getEndHour()+" 時止";
		compensateObject.setStartDate(startDate);
		compensateObject.setSumPremium(df.format(prpCmain.getSumPremium()));
		
		if(compensateNo.startsWith("C")){
			compensateObject.setSumPaid(df.format(prpLcompensate.getSumPaid()));
			compensateObject.setSumClaim(df.format(prpLcompensate.getSumDutyPaid()));
		}else{
			compensateObject.setSumPaid(df.format(-prpLcompensate.getSumPaid()));
			compensateObject.setSumClaim(df.format(-prpLcompensate.getSumDutyPaid()));
		}
		
		//费用明细
		double assessmentFee = 0D;//公证费用
		double richardFee = 0D;//估理費
		double otherCosts = 0D;//其他费用
		List<PrpLcharge> prpLchargeList = prpLchargeService.findPrpLchargeList(compensateNo);
		for(PrpLcharge prpLcharge : prpLchargeList){
			if("5".equals(prpLcharge.getChargeCode())){
				assessmentFee += prpLcharge.getChargeAmount()*prpLcharge.getExchRate();
			}else{
				otherCosts += prpLcharge.getChargeAmount()*prpLcharge.getExchRate();
			}
		}
		compensateObject.setAssessmentFee(df.format(assessmentFee));
		compensateObject.setRichardFee(df.format(richardFee));
		compensateObject.setOtherCosts(df.format(otherCosts));
		
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo",compensateNo);
		//TA textType =1;MC textType=05;PA textType=1;F01 textType = 05;BP textType =05;
		//R = 26
		String riskType = codeService.translateRiskCodetoRiskType(prpLcompensate.getRiskCode());
		if(compensateNo.startsWith("R")){
			queryRule.addEqual("id.textType", "26");
		}if(compensateNo.startsWith("S")){
			queryRule.addEqual("id.textType", "1");
		}else if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			queryRule.addEqual("id.textType","1");
		}else{
			queryRule.addEqual("id.textType","05");
		}
		queryRule.addAscOrder("id.lineNo");
		List<PrpLctext> prpLctextList = prpLctextService.findPrpLctext(queryRule);
		//理算说明
		StringBuffer context = new StringBuffer("");
		for(PrpLctext prpLctext : prpLctextList){
			context.append(prpLctext.getContext());
		}
		compensateObject.setCtext(context.toString());
		CompensateContextObject contextObject = null;
		for(String line :context.toString().split("\r\n")){
			contextObject = new CompensateContextObject();
			contextObject.setContext(line);
			compensateObject.getContextList().add(contextObject);
		}
		return compensateObject;
	}
	/**
	 *  再保共保业务  理賠計算書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public GAAReinsCompensateObject findGAAReinsCompensateObjectByCompensateNo(String compensateNo) throws Exception{
		GAAReinsCompensateObject gaaCompensateObject = new GAAReinsCompensateObject();
		//mantis： CLM0066 ，處理人員：BK007 蘇哲，需求單編號：CLM0066.新核心-更名計畫
		gaaCompensateObject.setPartyInsure("中國信託產物保險股份有限公司");
		DecimalFormat df = new DecimalFormat("#,###");
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		gaaCompensateObject.setRiskCode(prpLcompensate.getRiskCode());
		gaaCompensateObject.setRiskCodeName(codeService.translateRiskCode(prpLcompensate.getRiskCode(), true));
		gaaCompensateObject.setCompensateNo(prpLcompensate.getCompensateNo());
		gaaCompensateObject.setClaimNo(prpLcompensate.getClaimNo());
		String policyName= prpLcompensate.getPolicyNo();
		gaaCompensateObject.setPolicyNo(policyName.substring(0,4)+"字第"+policyName.substring(4)+"號");
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), prpCmain.getPolicyType());
		SimpleDateFormat dateFrom = new SimpleDateFormat("yyyy/MM/dd");
		gaaCompensateObject.setStartDate(CommonUtils.getMGDateStr(prpCmain.getStartDate(),dateFrom)+"-"+CommonUtils.getMGDateStr(prpCmain.getEndDate(),dateFrom));
		gaaCompensateObject.setDamageCode(prpLclaim.getDamageCode());
		gaaCompensateObject.setDamageName(prpLclaim.getDamageName());
		SimpleDateFormat dateFrom1 = new SimpleDateFormat("yyyy.MM.dd");
		gaaCompensateObject.setDamageStartDate(CommonUtils.getMGDateStr(prpLclaim.getDamageStartDate(),dateFrom1));
		gaaCompensateObject.setInputDate(CommonUtils.getMGDateStr(new Date(),dateFrom1));
		gaaCompensateObject.setInsuredName(prpLclaim.getInsuredName());
//		List<PrpCinsured> prpCinsuredList = policyDto.getPrpCinsuredList();
//		for(PrpCinsured prpCinsured : prpCinsuredList){
//			// 被保险人资料
//			if ("1".equals(prpCinsured.getInsuredFlag()) && (DataUtils.dbNullToEmpty(prpLclaim.getInsuredCode()).equals(DataUtils.dbNullToEmpty(prpCinsured.getInsuredCode()))||DataUtils.dbNullToEmpty(prpLclaim.getInsuredCode()).equals(DataUtils.dbNullToEmpty(prpCinsured.getIdentifyNumber())))) {
//				gaaCompensateObject.setInsuredIdentifyNumber(prpCinsured.getIdentifyNumber());
//			}
//			// 要保人资料
//			if ("2".equals(prpCinsured.getInsuredFlag())) {
//				gaaCompensateObject.setAppliName(DataUtils.dbNullToEmpty(prpCinsured.getInsuredName()));
//				gaaCompensateObject.setAppliIdentifyNumber(DataUtils.dbNullToEmpty(prpCinsured.getIdentifyNumber()));
//			}
//			if (DataUtils.emptyToNull(gaaCompensateObject.getInsuredIdentifyNumber()) != null && DataUtils.emptyToNull(gaaCompensateObject.getAppliIdentifyNumber()) != null) {
//				break;
//			}
//		}
		PrpCmainLiab prpCmainLiab = this.prpCmainLiabService.findPrpCmainLiab(policyNo);
		if( prpCmainLiab != null ){
			gaaCompensateObject.setHirer(prpCmainLiab.getHirer());
//			gaaCompensateObject.setHirerCode(prpCmainLiabList.get(0).getOthPolicyNo());
		}
		//查勘说明
//		List<PrpLregistText> prpLregistTextList = prpLregistTextService.findByRegistNo(prpLclaim.getRegistNo(), "3");
//		StringBuffer context = new StringBuffer("");
//		for(PrpLregistText prpLregistText : prpLregistTextList){
//			context.append(prpLregistText.getContext());
//		}
//		gaaCompensateObject.setCtext(context.toString());
		//承保工程險標的述要
		PrpCmainConstruct prpCmainConstruct = this.prpCmainConstructService.findPrpCmainConstruct(policyNo);
		if(prpCmainConstruct != null){
			gaaCompensateObject.setItemRemark(prpCmainConstruct.getConstructName());
		}
		//设置同险代号
//		施工或保險標的物處所
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			gaaCompensateObject.setItemAddress(prpCaddressList.get(0).getAddressName());
		}
//		for(PrpCaddress prpCaddress : policyDto.getPrpCaddressList()){
//			if(!CommonUtils.isEmpty(prpCaddress.getSameAddressNo())){
//				gaaCompensateObject.setSameAddressNo(prpCaddress.getSameAddressNo());
//				break;
//			}
//		}
		//设置承保范围
		String kindCode = null;
		List<Double> sumLoss = null;
		for(PrpCitemKind prpCitemKind : prpCitemKindList){
			kindCode = prpCitemKind.getKindCode().substring(prpCitemKind.getKindCode().length()-2);
			sumLoss = prpLcompensateService.findKindSumLoss(compensateNo, prpCitemKind);
			if("01".equals(kindCode)){
				//保單第一條
				if("CA,EA".contains(prpCitemKind.getRiskCode())&&"1".equals(prpCitemKind.getItemCode())){
					gaaCompensateObject.putCompensateSubreport(sumLoss,"02sumLoss","02sumRest","02sumRealPay","02amount");
				}else{
					gaaCompensateObject.putCompensateSubreport(sumLoss,"01sumLoss","01sumRest","01sumRealPay","01amount");
				}
				gaaCompensateObject.putCompensateSubreport(sumLoss,"03sumLoss","03sumRest","03sumRealPay","03amount");
				gaaCompensateObject.putCompensateSubreport(sumLoss,"08sumLoss","08sumRest","08sumRealPay","08amount","09sumPaid","09chargeAmount");
			}else if("02".equals(kindCode)){
				//保單第二條
				if("EE".equals(prpCitemKind.getRiskCode())){
					gaaCompensateObject.putCompensateSubreport(sumLoss,"06sumLoss","06sumRest","06sumRealPay","06amount");
				}else{
					if("2".equals(prpCitemKind.getItemCode())&&"1".equals(prpCitemKind.getItemDetailName())){
						//倒塌責任,每一次事故
						gaaCompensateObject.putCompensateSubreport("05amount2", df.format(prpCitemKind.getAmount()/10000)+"萬");
					}else if("1".equals(prpCitemKind.getItemCode())&&"1".equals(prpCitemKind.getItemDetailName())){
						//龜裂責任,每一次事故
						gaaCompensateObject.putCompensateSubreport("05amount1", df.format(prpCitemKind.getAmount()/10000)+"萬");
					}
					gaaCompensateObject.putCompensateSubreport(sumLoss,"05sumLoss","05sumRest","05sumRealPay","05amount");
				}
				gaaCompensateObject.putCompensateSubreport(sumLoss,"08sumLoss","08sumRest","08sumRealPay","08amount","09sumPaid","09chargeAmount");
			}else if("03".equals(kindCode)){
				if("EE".equals(prpCitemKind.getRiskCode())){
					//保單第三條
					if("1".equals(prpCitemKind.getItemCode())){
						gaaCompensateObject.putCompensateSubreport("07itemName","1-租金");
					}else if("2".equals(prpCitemKind.getItemCode())){
						gaaCompensateObject.putCompensateSubreport("07itemName","2-人事費");
					}else{
						gaaCompensateObject.putCompensateSubreport("07itemName","3-材料運費");
					}
					gaaCompensateObject.putCompensateSubreport(sumLoss,"07sumLoss","07sumRest","07sumRealPay","07amount");
					gaaCompensateObject.putCompensateSubreport(sumLoss,"08sumLoss","08sumRest","08sumRealPay","08amount","09sumPaid","09chargeAmount");
				}else{
					//雇主意外責任險
					//每一個人體傷或死亡
					gaaCompensateObject.putCompensateSubreport("11perHumanInjury", df.format(prpCitemKind.getPerHumanInjury()/10000)+"萬");
					//每一事故體傷或死亡
					gaaCompensateObject.putCompensateSubreport("11perAccidentDeaths", df.format(prpCitemKind.getPerAccidentDeaths()/10000)+"萬");
					//每一事故體傷或死亡
					gaaCompensateObject.putCompensateSubreport("11perAccidentDamage", df.format(prpCitemKind.getPerAccidentDamage()/10000)+"萬");
					//保險期間內最高責任 
					gaaCompensateObject.putCompensateSubreport("11periodMaxAmount", df.format(prpCitemKind.getPeriodMaxAmount()/10000)+"萬");
					gaaCompensateObject.putCompensateSubreport(sumLoss,"11sumLoss","11sumRest","11sumRealPay","11amount","12sumPaid","12chargeAmount");
				}
			}else if("09".equals(kindCode)){
				//保單第二條
				//每一個人體傷或死亡
				gaaCompensateObject.putCompensateSubreport("04perHumanInjury", df.format(prpCitemKind.getPerHumanInjury()/10000)+"萬");
				//每一事故體傷或死亡
				gaaCompensateObject.putCompensateSubreport("04perAccidentDeaths", df.format(prpCitemKind.getPerAccidentDeaths()/10000)+"萬");
				//每一事故財產損失
				gaaCompensateObject.putCompensateSubreport("04perAccidentDamage", df.format(prpCitemKind.getPerAccidentDamage()/10000)+"萬");
				//保險期間內最高責任 
				gaaCompensateObject.putCompensateSubreport("04periodMaxAmount", df.format(prpCitemKind.getPeriodMaxAmount()/10000)+"萬");
				gaaCompensateObject.putCompensateSubreport(sumLoss,"04sumLoss","04sumRest","04sumRealPay","04amount");
				gaaCompensateObject.putCompensateSubreport(sumLoss,"08sumLoss","08sumRest","08sumRealPay","08amount","09sumPaid","09chargeAmount");
			}
		}
//		本會承受比例
		gaaCompensateObject.putCompensateSubreport("09proportion", "20%");
		//格式化数字
		Object value = null;
		for(String key : gaaCompensateObject.getCompensateSubreport().keySet()){
			value = gaaCompensateObject.getCompensateSubreport().get(key);
			if(value instanceof Double){
				gaaCompensateObject.getCompensateSubreport().put(key, df.format(value));
			}
		}
		return gaaCompensateObject;
	}
	
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}
	public PrpCinsuredNatureService getPrpCinsuredNatureService() {
		return prpCinsuredNatureService;
	}
	public void setPrpCinsuredNatureService(PrpCinsuredNatureService prpCinsuredNatureService) {
		this.prpCinsuredNatureService = prpCinsuredNatureService;
	}
	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}
	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}
	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}
	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}
	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}
	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}
	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}
	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}
	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}
	public PrpCmainConstructService getPrpCmainConstructService() {
		return prpCmainConstructService;
	}
	public void setPrpCmainConstructService(PrpCmainConstructService prpCmainConstructService) {
		this.prpCmainConstructService = prpCmainConstructService;
	}
	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}
	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}
	public RemnantService getRemnantService() {
		return remnantService;
	}
	public void setRemnantService(RemnantService remnantService) {
		this.remnantService = remnantService;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}
	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}
	public PolicyService getPolicyService() {
		return policyService;
	}
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}
	public PrpJPayRefRecHisService getPrpJPayRefRecHisService() {
		return prpJPayRefRecHisService;
	}
	public void setPrpJPayRefRecHisService(PrpJPayRefRecHisService prpJPayRefRecHisService) {
		this.prpJPayRefRecHisService = prpJPayRefRecHisService;
	}
	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}
	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}
	public CompensateService getCompensateService() {
		return compensateService;
	}
	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}
	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}
	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}
	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}
	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}
	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}
	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}
	public PrpLexternalAgencyService getPrpLexternalAgencyService() {
		return prpLexternalAgencyService;
	}
	public void setPrpLexternalAgencyService(PrpLexternalAgencyService prpLexternalAgencyService) {
		this.prpLexternalAgencyService = prpLexternalAgencyService;
	}
	public PrpCmainLiabService getPrpCmainLiabService() {
		return prpCmainLiabService;
	}
	public void setPrpCmainLiabService(PrpCmainLiabService prpCmainLiabService) {
		this.prpCmainLiabService = prpCmainLiabService;
	}
	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}
	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}
	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
}
