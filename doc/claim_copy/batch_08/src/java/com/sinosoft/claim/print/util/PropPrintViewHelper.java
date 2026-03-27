package com.sinosoft.claim.print.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ctbcins.util.print.PrintViewHelper;
import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.print.vo.PropClaimApplicationFormObject;
import com.sinosoft.claim.print.vo.PropClaimDisposeReportObject;
import com.sinosoft.claim.print.vo.PropCoinsCompesateObject;
import com.sinosoft.claim.print.vo.PropCoinsCompesateSubObject;
import com.sinosoft.claim.print.vo.PropCompensateObject;
import com.sinosoft.claim.print.vo.PropGeneralClaimObject;
import com.sinosoft.claim.print.vo.PropLossListObject;
import com.sinosoft.claim.print.vo.PropPaymentAcceptanceObject;
import com.sinosoft.claim.print.vo.PropPaymentAcceptanceSubObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationSubFiveObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationSubFourObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationSubOneObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationSubThreeObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationSubTwoObject;
import com.sinosoft.claim.print.vo.PropRegistReportObject;
import com.sinosoft.claim.print.vo.PropRemittanceFormObject;
import com.sinosoft.claim.print.vo.PropRemnantObject;
import com.sinosoft.claim.print.vo.PropReplevyReportObject;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;

/**
 * 收集火險列印所需數據
 * @author 中科軟
 *
 */
//mantis： CLM0093 ，處理人員：BK007 蘇哲，需求單編號：CLM0093 新核心-火險追償理算書加上追償說明
public class PropPrintViewHelper extends PrintViewHelper{
	/** 賠付對象服務*/
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 理算信息服務*/
	private PrpLcompensateService prpLcompensateService;
	/** 立案服務*/
	private PrpLclaimService prpLclaimService;
	/** 保單信息服务*/
	private PrpCmainService prpCmainService;
	/** 備案信息服務*/
	private PrpLregistService prpLregistService;
	/** 批单viewHelper*/
	private EndorseViewHelper endorseViewHelper;
	/** 批单服务*/
	private PrpPheadService prpPheadService;
	/** 报案文字服务*/
	private PrpLregistTextService prpLregistTextService;
	/** 查勘信息服務*/
	private PrpLcheckService prpLcheckService;
	/** 代碼轉換服務*/
	private CodeService codeService;
	/** 机构信息服务*/
	private PrpDcompanyService prpDcompanyService;
	/** 被保險人信息服務*/
	private PrpCinsuredService prpCinsuredService;
	/** 聯共保信息服務*/
	private PrpCcoinsService prpCcoinsService;
	/** 聯共保信息服務*/
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 賠款計算方式服務*/
	private PrpLctextService prpLctextService;
	/** 財產損失 服務*/
	private PrpLlossService prpLlossService;
	/** 人傷損失 服務*/
	private PrpLpersonLossService prpLpersonLossService;
	/** 費用信息 服務*/
	private PrpLchargeService prpLchargeService;
	/**收費狀況 服務*/
	private PrpCplanService prpCplanService;
	/** 操作人員信息服務*/
	private PrpDuserService prpDuserService;
	private PrpDcurrencyService prpDcurrencyService;
	private PrpCaddressService prpCaddressService;
	private GAAPrintViewHelper gaaPrintViewHelper;
	/**
	 * 通过计算书号 收集 火險追償計算書列印对象
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PropReplevyReportObject findPropReplevyReportObjectByCompensateNo(String compensateNo) throws Exception{
		PropReplevyReportObject propReplevyReportObject = new PropReplevyReportObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		propReplevyReportObject.setPolicyNo(prpLcompensate.getPolicyNo());
		propReplevyReportObject.setClaimNo(prpLcompensate.getClaimNo());
		propReplevyReportObject.setCompensateNo(compensateNo);
		//多个批单号用 ，隔开
		String endorseNo = "";
		for(PrpPhead prpPhead : this.prpPheadService.findByPolicyNo(prpLcompensate.getPolicyNo())){
			endorseNo += prpPhead.getEndorseNo() + "，";
		}
		propReplevyReportObject.setEndorseNo(endorseNo.length()>0?endorseNo.substring(0, endorseNo.length() - 1):endorseNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		propReplevyReportObject.setDamageAddress(prpLclaim.getDamageAddress());
		propReplevyReportObject.setInsuredName(prpLclaim.getInsuredName());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		String itemKind = "";
		String itemKindAddress = "";
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		for(PrpCitemKind prpCitemKind : prpCitemKindList){
			if(itemKind.indexOf(prpCitemKind.getItemCode())<0){
				itemKind += prpCitemKind.getItemCode()+",";
				PrpCaddress prpCaddress = prpCaddressService.findPrpCaddress(prpCitemKind.getId().getPolicyNo(), prpCitemKind);
				if(prpCaddress!=null){
					itemKindAddress += prpCaddress.getAddressName()+",";
				}
			}
		}
		if(itemKind.length()>0){
			propReplevyReportObject.setItemKind(itemKind.substring(0,itemKind.length()-1));
		}
		if(itemKindAddress.length()>0){
			propReplevyReportObject.setItemKindAddress(itemKindAddress.substring(0,itemKindAddress.length()-1));
		}
		propReplevyReportObject.setSumAmount(decimalFormat.format(prpCmain.getSumAmount()));
		propReplevyReportObject.setDamageDate(PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(), prpLclaim.getDamageStartHour()));
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propReplevyReportObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		propReplevyReportObject.setReplevyAmount(decimalFormat.format(prpLcompensate.getSumDutyPaid()*-1));
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
		propReplevyReportObject.setLegalFee(decimalFormat.format(legalFee));
		propReplevyReportObject.setNotarialFee(decimalFormat.format(notarialFee));
		propReplevyReportObject.setOthersFee(decimalFormat.format(othersFee));
		propReplevyReportObject.setSumFee(decimalFormat.format(prpLcompensate.getSumPaid()));
		propReplevyReportObject.setPaidFee(decimalFormat.format(prpLcompensate.getSumNoDutyFee()));
		//mantis： CLM0093 ，處理人員：BK007 蘇哲，需求單編號：CLM0093 新核心-火險追償理算書加上追償說明
		getContextByCompensateNo(propReplevyReportObject, compensateNo);
		return propReplevyReportObject;
	}
	
	/***
	 * 通过compensateNo 收集 火災保險賠款接受書 内容(已不用)
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
//	public List<PropPaymentAcceptanceObject> findPropPaymentAcceptanceObjectListByCompensateNo(String compensateNo) throws Exception{
//		List<PropPaymentAcceptanceObject> propPaymentAcceptanceObjectList = new ArrayList<PropPaymentAcceptanceObject>();
//		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
//		
//		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
//		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
//		String damageDate = PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(),prpLclaim.getDamageStartHour());
//		List<PrpLloss> prpLlossList = this.prpLlossService.findPrpLloss(QueryRule.getInstance().addEqual("id.compensateNo", compensateNo));
//		List<PrpLpayObjectInfo> prpLpayObjectInfoList = this.prpLpayObjectInfoService.findPrpLpayObjectInfo(QueryRule.getInstance().addEqual("id.compensateNo", compensateNo));
//		PropPaymentAcceptanceObject propPaymentAcceptanceObject = null;
//		//查询 數據對象
//		for(PrpLpayObjectInfo prpLpayObjectInfo : prpLpayObjectInfoList){
//			propPaymentAcceptanceObject = new PropPaymentAcceptanceObject();
//			List<PropPaymentAcceptanceSubObject> propPaymentAcceptanceSubObjectList = new ArrayList<PropPaymentAcceptanceSubObject>();
//			PropPaymentAcceptanceSubObject propPaymentAcceptanceSubObject = null;
//			//propPaymentAcceptanceObject.setOwnerName(prpLpayObjectInfo.getOwnerName());
//			propPaymentAcceptanceObject.setPayAmount(PrintUtils.digitUppercase(Double.parseDouble(new DecimalFormat("#").format(prpLpayObjectInfo.getPayAmount()))));
//			propPaymentAcceptanceObject.setDamageDate(damageDate);
//			//查询子报表 数据
//			for(PrpLloss prpLloss : prpLlossList){
//				propPaymentAcceptanceSubObject = new PropPaymentAcceptanceSubObject();
//				propPaymentAcceptanceSubObject.setPolicyno(prpLloss.getPolicyNo());
//				propPaymentAcceptanceSubObject.setItemKind(prpLloss.getLossName());
//				//保险金额 此处 使用 赔偿金额
//				propPaymentAcceptanceSubObject.setAmount(decimalFormat.format(prpLloss.getSumRealPay()));
//				propPaymentAcceptanceSubObjectList.add(propPaymentAcceptanceSubObject);
//			}
//			propPaymentAcceptanceObject.setPropPaymentAcceptanceSubObject(propPaymentAcceptanceSubObjectList);
//			//標的物地址， 此处暂取 PrpCaddress 的第一条数据
//			//this.endorseViewHelper.findForEndorBefore 方法并没有回滚 PrpCaddress，是否需要修改？
//			PolicyDto policyDto = this.endorseViewHelper.findForEndorBefore(prpLclaim.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
//			PrpCaddress prpCaddress = policyDto.getPrpCaddressList().get(0);
//			//propPaymentAcceptanceObject.setItemKindAddress(prpCaddress.getAddressName());
//			propPaymentAcceptanceObjectList.add(propPaymentAcceptanceObject);
//		}
//		return propPaymentAcceptanceObjectList;
//	}
	/**
	 * 通过compensateNo 收集 火災保險賠款接受書 内容
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PropPaymentAcceptanceObject findPropPaymentAcceptanceObjectByCompensateNo(String compensateNo) throws Exception{
		PropPaymentAcceptanceObject propPaymentAcceptanceObject = new PropPaymentAcceptanceObject();
		List<PropPaymentAcceptanceSubObject> propPaymentAcceptanceSubObjectList = new ArrayList<PropPaymentAcceptanceSubObject>();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		propPaymentAcceptanceObject.setInsuredName(prpCmain.getInsuredName());
		propPaymentAcceptanceObject.setPayAmount(PrintUtils.digitUppercase(prpLcompensate.getSumDutyPaid()));
		propPaymentAcceptanceObject.setDamageDate(PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(), prpLclaim.getDamageStartHour()));
		List<PrpLloss> prpLlossList = this.prpLlossService.findPrpLloss(QueryRule.getInstance().addEqual("id.compensateNo", compensateNo));
		PropPaymentAcceptanceSubObject propPaymentAcceptanceSubObject = null;
		PrpCaddress prpCaddress = null;
		if(!CommonUtils.isEmpty(prpLlossList)){
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			for(PrpLloss prpLloss : prpLlossList){
				propPaymentAcceptanceSubObject = new PropPaymentAcceptanceSubObject();
				propPaymentAcceptanceSubObject.setPolicyno(prpCmain.getPolicyNo());
				propPaymentAcceptanceSubObject.setItemKind(prpLloss.getItemCode());
				propPaymentAcceptanceSubObject.setAmount(decimalFormat.format(prpLloss.getSumRealPay()));
				for(PrpCitemKind prpCitemKind : prpCitemKindList){
					if(prpLloss.getKindCode().equals(prpCitemKind.getKindCode())&&(prpLloss.getItemCode()!=null&&prpLloss.getItemCode().equals(prpCitemKind.getItemCode()))){
						prpCaddress = prpCaddressService.findPrpCaddress(prpLloss.getPolicyNo(),prpCitemKind);
						if(prpCaddress!=null){
							propPaymentAcceptanceSubObject.setAddressName(prpCaddress.getAddressName());
							break;
						}
					}
				}
				propPaymentAcceptanceSubObjectList.add(propPaymentAcceptanceSubObject);
			}
		}
		propPaymentAcceptanceObject.setPropPaymentAcceptanceSubObjectList(propPaymentAcceptanceSubObjectList);
		return propPaymentAcceptanceObject;
	}
	
	/***
	 * 根据备案号查询 火險出險報告 数据对象
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public PropRegistReportObject findPropRegistReportObjectByRegistNo(String registNo) throws Exception{
		PropRegistReportObject propRegistReportObject = new PropRegistReportObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		//出險標的 未收集
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findByRegistNo(registNo).get(0);
		propRegistReportObject.setClaimNo(prpLclaim.getClaimNo());
		propRegistReportObject.setPolicyNo(prpLregist.getPolicyNo());
		//多个批单号用 ，隔开
		String endorseNo = "";
		for(PrpPhead prpPhead : this.prpPheadService.findByPolicyNo(prpLregist.getPolicyNo())){
			endorseNo += prpPhead.getEndorseNo() + "，";
		}
		propRegistReportObject.setEndorseNo(endorseNo.length()>0?endorseNo.substring(0, endorseNo.length() - 1):endorseNo);
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propRegistReportObject.setInsuredName(prpLclaim.getInsuredName());
		propRegistReportObject.setInsurancePeriod(startDate + "—" + endDate);
		propRegistReportObject.setDamageAddress(prpLclaim.getDamageAddress());
		propRegistReportObject.setDamageReason(prpLclaim.getDamageName());
		propRegistReportObject.setItemKind(prpLregist.getLossName());
		
		propRegistReportObject.setClaimAmount(decimalFormat.format(prpLregist.getEstimateLoss()));
		propRegistReportObject.setRegistDate(PrintUtils.getDamageDate(prpLregist.getReportDate(),prpLregist.getReportHour()));
		propRegistReportObject.setDamageDate(PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(), prpLclaim.getDamageStartHour()));
		propRegistReportObject.setContactName(prpLregist.getLinkerName());
		propRegistReportObject.setContact(prpLregist.getPhoneNumber());
		//收集本案出險原因、經過以及損失處理情況；估計全案損失並填寫查勘人意見（查勘报告）
		List<PrpLregistText> prpLregistTextList = this.prpLregistTextService.findByRegistNo(registNo, "3");
		String registProcess = "";
		for(PrpLregistText prpLregistText : prpLregistTextList){
			registProcess = registProcess + prpLregistText.getContext();
		}
		propRegistReportObject.setRegistProcess(registProcess);
		PrpLcheck prpLcheck = this.prpLcheckService.findPrpLcheck(QueryRule.getInstance().addEqual("id.registNo", registNo)).get(0);
		propRegistReportObject.setCheckDate(PrintUtils.getYearToDayMGName(prpLcheck.getCheckDate()));
		propRegistReportObject.setCheckAddress(prpLcheck.getCheckSite());
		propRegistReportObject.setChecker(prpLcheck.getChecker1());
		propRegistReportObject.setPrintTime(PrintUtils.getYearToHourMGStr(new Date()));
		
		return propRegistReportObject;
	}
	
	/**
	 * 根据备案号查询 非水代查勘委託書 数据对象
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public PropGeneralClaimObject findPropGeneralClaimObjectByRegistNo(String registNo) throws Exception{
		PropGeneralClaimObject propGeneralClaimObject = new PropGeneralClaimObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		PrpLclaim prpLclaim = new PrpLclaim();
		if(!CommonUtils.isEmpty(this.prpLclaimService.findByRegistNo(registNo))){
			prpLclaim = this.prpLclaimService.findByRegistNo(registNo).get(0);
		}
		propGeneralClaimObject.setClaimNo(prpLclaim.getClaimNo());
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
		//handleUnitName 事故处理部门  数据有待验证
		String handleUnitName = CommonUtils.isEmpty(prpLregist.getHandleUnit())?"":this.codeService.translateComCode(prpLregist.getHandleUnit(), true);
		propGeneralClaimObject.setHandleUnitName(handleUnitName);
		propGeneralClaimObject.setRiskName(this.codeService.translateRiskCode(prpLregist.getRiskCode(), true));
		propGeneralClaimObject.setInsuredName(prpLregist.getInsuredName());
		propGeneralClaimObject.setPolicyNo(prpLregist.getPolicyNo());
		propGeneralClaimObject.setDamageAddress(prpLregist.getDamageAddress());
		//受损标的 未收集
		propGeneralClaimObject.setDamageDate(PrintUtils.getDamageDate(prpLregist.getDamageStartDate(),prpLregist.getDamageStartHour()));
		propGeneralClaimObject.setContactName(prpLregist.getLinkerName());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propGeneralClaimObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		propGeneralClaimObject.setContact(prpLregist.getPhoneNumber());
		if(prpCmain.getSumAmount()!=null){
			propGeneralClaimObject.setSumAmount(decimalFormat.format(prpCmain.getSumAmount()));
		}
		//得到UserDto对象
		ActionContext act = ActionContext.getContext();
		Map<String,Object> session = act.getSession();
		UserDto user = (UserDto)session.get("user");
		propGeneralClaimObject.setInsuranceLinkMan(user.getUserName());
		PrpDcompany prpDcompany = this.prpDcompanyService.findPrpDcompany(user.getComCode());
		propGeneralClaimObject.setPhoneNum(prpDcompany.getPhoneNumber());
		propGeneralClaimObject.setFaxNumber(prpDcompany.getFaxNumber());
		propGeneralClaimObject.setPrintTime(PrintUtils.getYearToHourMGStr(new Date()));
		
		return propGeneralClaimObject;
	}
	
	/***
	 * 根據 計算書號 查詢 匯款同意書 數據對象
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PropRemittanceFormObject findPropRemittanceFormObjectByCompensateNo(String compensateNo) throws Exception{
		PropRemittanceFormObject propRemittanceFormObject = new PropRemittanceFormObject();
		
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		propRemittanceFormObject.setClaimNo(prpLcompensate.getClaimNo());
		propRemittanceFormObject.setPolicyNo(prpLcompensate.getPolicyNo());
		propRemittanceFormObject.setClaimAmount(PrintUtils.digitUppercase(prpLcompensate.getSumDutyPaid()) + "，");
		
		return propRemittanceFormObject;
	}
	
	/***
	 * 根據賠案號 查詢 理賠申請書 數據對象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public PropClaimApplicationFormObject findPropClaimApplicationFormObjectByClaimNo (String claimNo) throws Exception{
		PropClaimApplicationFormObject propClaimApplicationFormObject = new PropClaimApplicationFormObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		//得到UserDto对象
		ActionContext act = ActionContext.getContext();
		Map<String,Object> session = act.getSession();
		UserDto user = (UserDto)session.get("user");
		PrpDcompany prpDcompany = this.prpDcompanyService.findPrpDcompany(user.getComCode());
		propClaimApplicationFormObject.setCompanyAddress(prpDcompany.getAddressCName());
		propClaimApplicationFormObject.setCompanyFaxNumber(prpDcompany.getFaxNumber());
		propClaimApplicationFormObject.setCompanyPhoneNumber(prpDcompany.getPhoneNumber());
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		propClaimApplicationFormObject.setRiskName(this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		propClaimApplicationFormObject.setClaimNo(claimNo);
		propClaimApplicationFormObject.setInsuredName(prpLclaim.getInsuredName());
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		propClaimApplicationFormObject.setInsuredAddress(prpLregist.getInsuredAddress());
		//从 PrpCinsured 查询 被保險人電話
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredCode", prpLclaim.getInsuredCode());
		List<PrpCinsured> prpCinsuredList = prpCinsuredService.findPrpCinsured(queryRule);
		if(prpCinsuredList.size() > 0){
			PrpCinsured prpCinsured = prpCinsuredList.get(0);
			propClaimApplicationFormObject.setInsuredPhone(prpCinsured.getPhoneNumber());
		}
		propClaimApplicationFormObject.setPolicyNo(prpLclaim.getPolicyNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propClaimApplicationFormObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		propClaimApplicationFormObject.setContactName(prpLregist.getLinkerName());
		propClaimApplicationFormObject.setContactAddress(prpLregist.getLinkerAddress());
		propClaimApplicationFormObject.setContactPhone(prpLregist.getPhoneNumber());
		propClaimApplicationFormObject.setDamageDate(PrintUtils.getDamageDate(prpLregist.getDamageStartDate(),prpLregist.getDamageStartHour()));
		propClaimApplicationFormObject.setDamageReason(prpLclaim.getDamageName());
		propClaimApplicationFormObject.setDamageAddress(prpLclaim.getDamageAddress());
		propClaimApplicationFormObject.setClaimAmount(ConstantCodes.LOCAL_CURRENCY+" "+decimalFormat.format(prpLregist.getEstimateLoss()));
		
		return propClaimApplicationFormObject;
	}
	
	/***
	 * 根據 賠案號 查詢 理賠處理報告 數據對象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public PropClaimDisposeReportObject findPropClaimDisposeReportObjectByClaimNo(String claimNo) throws Exception{
		PropClaimDisposeReportObject propClaimDisposeReportObject = new PropClaimDisposeReportObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		
		propClaimDisposeReportObject.setClaimNo(claimNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		propClaimDisposeReportObject.setRegistNo(prpLclaim.getRegistNo());
		propClaimDisposeReportObject.setRiskName(this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		propClaimDisposeReportObject.setSumAmount(decimalFormat.format(prpCmain.getSumAmount()));
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propClaimDisposeReportObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		// 查询 承保比例，有待验证正确，暂根据老代码查询方式
		List<PrpCcoins> prpCcoinsList = this.prpCcoinsService.findPrpCcoins(QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()));
		double coins = 100.0;
		for(PrpCcoins prpCcoins : prpCcoinsList){
			if("2".equals(prpCcoins.getCoinsType()) || "3".equals(prpCcoins.getCoinsType())){
				coins -= prpCcoins.getCoinsRate();
			}
		}
		propClaimDisposeReportObject.setCoins(String.valueOf(coins).toString() + "%");
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		propClaimDisposeReportObject.setInsuredNameAndAddress(prpLregist.getInsuredName() + "（" + prpLregist.getInsuredAddress() + "）");
		propClaimDisposeReportObject.setDamageAddress(prpLregist.getDamageAddress());
		propClaimDisposeReportObject.setDamageDate(PrintUtils.getDamageDate(prpLregist.getDamageStartDate(),prpLregist.getDamageStartHour()));
		propClaimDisposeReportObject.setIndemnityDuty(decimalFormat.format(prpLclaim.getDeductibleRate()*prpLclaim.getSumClaim()));
		propClaimDisposeReportObject.setSumClaim(decimalFormat.format(prpLclaim.getSumClaim()));
		propClaimDisposeReportObject.setSumDefLoss(decimalFormat.format(prpLclaim.getSumDefLoss()));
		propClaimDisposeReportObject.setSumDefLoss(decimalFormat.format(prpLclaim.getSumPaid()));
		propClaimDisposeReportObject.setSumPaid(decimalFormat.format(prpLclaim.getSumPaid()));
		
		return propClaimDisposeReportObject;
	}
	
	/***
	 * 根據 計算書號 查詢 聯共保計算書 數據對象
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PropCoinsCompesateObject findPropCoinsCompesateObjectByCompensateNo(String compensateNo) throws Exception{
		PropCoinsCompesateObject propCoinsCompesateObject = new PropCoinsCompesateObject();
		List<PropCoinsCompesateSubObject> propCoinsCompesateSubObjectList = new ArrayList<PropCoinsCompesateSubObject>();
		PropCoinsCompesateSubObject propCoinsCompesateSubObject = null;
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		propCoinsCompesateObject.setCoinsFlag(ConstantsCollection.COINSFLAG.get(prpCmain.getCoinsFlag()));
		//根据老代码查询子对象数据
		List<PrpCcoins> prpCcoinsList = this.prpCcoinsService.findPrpCcoins(QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()));
		PrpCcoins prpCcoins = null;
		List<PrpLcfeecoins> prpLcfeecoinsList = this.prpLcfeecoinsService.findPrpLcfeecoins(QueryRule.getInstance().addEqual("id.businessNo", prpLcompensate.getCompensateNo()));
		double sumCoinsRate = 0.0;//份額合計
		double sumCoinsClaimPaid = 0.0;//應支付賠款金額合計
		double sumCoinsChargePaid = 0.0;//應支付費用金額合計
		for(int index = 0; index < prpCcoinsList.size(); index ++){
			prpCcoins = prpCcoinsList.get(index);
			propCoinsCompesateSubObject = new PropCoinsCompesateSubObject();
			propCoinsCompesateSubObject.setIndex(String.valueOf(index + 1));
			propCoinsCompesateSubObject.setCoinsName(DataUtils.dbNullToEmpty(prpCcoins.getCoinsName()));
			propCoinsCompesateSubObject.setCoinsRate(decimalFormat.format(prpCcoins.getCoinsRate()) + "%");
			sumCoinsRate += prpCcoins.getCoinsRate();
			//币种 先统一设置成NTD，之后看要不要更改
			propCoinsCompesateSubObject.setClaimPaidCurrency(ConstantCodes.LOCAL_CURRENCY);
			propCoinsCompesateSubObject.setChargePaidCurrency(ConstantCodes.LOCAL_CURRENCY);
			propCoinsCompesateSubObject.setCoinsCurrency(ConstantCodes.LOCAL_CURRENCY);
			//取 賠款金額 和 費用金額
			double claimPaid = 0.0;//賠款金額
			double chargePaid = 0.0;//費用金額
			for(PrpLcfeecoins prpLcfeecoins : prpLcfeecoinsList){
				if(prpCcoins.getCoinsType().equals(prpLcfeecoins.getCoinsType()) && Integer.parseInt(prpLcfeecoins.getLossFeeType()) == 0){
					claimPaid += prpLcfeecoins.getCoinsSumPaid();
				}else if(prpCcoins.getCoinsType().equals(prpLcfeecoins.getCoinsType()) && Integer.parseInt(prpLcfeecoins.getLossFeeType()) == 1){
					chargePaid += prpLcfeecoins.getCoinsSumPaid();
				}
			}
			sumCoinsClaimPaid += claimPaid;
			sumCoinsChargePaid += chargePaid;
			propCoinsCompesateSubObject.setClaimPaid(decimalFormat.format(claimPaid));
			propCoinsCompesateSubObject.setChargePaid(decimalFormat.format(chargePaid));
			propCoinsCompesateSubObject.setCoinsSumPaid(decimalFormat.format(claimPaid + chargePaid));
			propCoinsCompesateSubObject.setPolicyNo(prpCcoins.getId().getPolicyNo());
			propCoinsCompesateSubObject.setClaimNo(prpLclaim.getClaimNo());
			
			propCoinsCompesateSubObjectList.add(propCoinsCompesateSubObject);
		}
		propCoinsCompesateObject.setSumCoinsRate(decimalFormat.format(sumCoinsRate) + "%");
		propCoinsCompesateObject.setSumCoinsClaimPaid(decimalFormat.format(sumCoinsClaimPaid));
		propCoinsCompesateObject.setSumCoinsChargePaid(decimalFormat.format(sumCoinsChargePaid));
		propCoinsCompesateObject.setSumAllPaid(decimalFormat.format(sumCoinsClaimPaid + sumCoinsChargePaid));
		propCoinsCompesateObject.setPropCoinsCompesateSubObjectList(propCoinsCompesateSubObjectList);
		propCoinsCompesateObject.setCompanyName(this.codeService.translateComCode(prpLcompensate.getComCode(), true));
		propCoinsCompesateObject.setPrintTime(PrintUtils.getYearToDayMGName(new Date()));
		
		return propCoinsCompesateObject;
	}
	
	/***
	 * 根據計算書號查詢  火險賠款計算書 數據對象
	 * @param compensateNo
	 * @return
	 * @throws Exception 
	 */
	public PropCompensateObject findPropClaimCompensateReportObjectByCompensateNo(String compensateNo) throws Exception{
		PropCompensateObject propCompensateObject = new PropCompensateObject();
		propCompensateObject = this.getGaaPrintViewHelper().findCompensateObjectByCompensateNo(compensateNo, propCompensateObject);;
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		propCompensateObject.setDamageCode(prpLclaim.getDamageCode());
		propCompensateObject.setDamageName(prpLclaim.getDamageName());
		return propCompensateObject;
	}
	
	/***
	 * 根據 備案號 查詢 火 險 損 失 清 單 數據對象
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public PropLossListObject findPropLossListObjectByRegistNo(String registNo) throws Exception{
		PropLossListObject propLossListObject = new PropLossListObject();
		
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
		propLossListObject.setPolicyNo(prpLregist.getPolicyNo());
		
		return propLossListObject;
	}
	
	/***
	 * 根據 保單號查詢 火 險 損 失 清 單 數據對象
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public PropLossListObject findPropLossListObjectByPolicyNo(String policyNo) throws Exception{
		PropLossListObject propLossListObject = new PropLossListObject();
		
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);
		propLossListObject.setPolicyNo(prpCmain.getPolicyNo());
		
		return propLossListObject;
	}
	
	/***
	 * 根據 備案 號查詢 火險承保理賠信息數據對象
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public PropPrpinsClaimInformationObject findpropPrpinsClaimInformationObjectByRegistNo(String registNo) throws Exception{
		PropPrpinsClaimInformationObject propPrpinsClaimInformationObject = new PropPrpinsClaimInformationObject();
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
		propPrpinsClaimInformationObject.setRegistNo(registNo);
		propPrpinsClaimInformationObject.setPolicyNo(prpLregist.getPolicyNo());
		propPrpinsClaimInformationObject.setInsuranceName(DataUtils.dbNullToEmpty(prpLregist.getInsuredName()));
		String policyNo = prpLregist.getPolicyNo();
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		//保險標的物地址    取老报表的 保险地址
		String itemKindAddress = "";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);;
		if(!CommonUtils.isEmpty(prpCaddressList)){
			for(PrpCaddress prpCaddress : prpCaddressList){
				itemKindAddress += prpCaddress.getId().getAddressNo() + "、" +prpCaddress.getAddressName() + "\r\n";
			}
		}
		propPrpinsClaimInformationObject.setItemKindAddress(DataUtils.dbNullToEmpty(itemKindAddress));
		propPrpinsClaimInformationObject.setOperateDate(PrintUtils.getYearToDayMGStr(prpCmain.getOperateDate()));
		propPrpinsClaimInformationObject.setUnderWriteEndDate(PrintUtils.getYearToDayMGStr(prpCmain.getUnderwriteEndDate()));
		propPrpinsClaimInformationObject.setSignDate(PrintUtils.getYearToDayMGStr(prpCmain.getSignDate()));
		propPrpinsClaimInformationObject.setInputDate(PrintUtils.getYearToDayMGStr(prpCmain.getInputDate()));
		String startDate = PrintUtils.getDamageDate(prpCmain.getStartDate(), String.valueOf(prpCmain.getStartHour()));
		String endDate = PrintUtils.getDamageDate(prpCmain.getEndDate(), String.valueOf(prpCmain.getEndHour()));
		propPrpinsClaimInformationObject.setInsurancePeriod("自" + startDate + "起至" + endDate + "止");
		propPrpinsClaimInformationObject.setIsCoinsFlag(ConstantsCollection.ISCOINSFLAG.get(prpCmain.getCoinsFlag()));
		String specialAgreement = "";
		List<PrpPhead> prpPheadList = this.endorseViewHelper.findPrpPhead(policyNo, damageDate, damageHour);
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(prpPheadList, policyNo);
		if(!CommonUtils.isEmpty(prpCengageList)){
			for(PrpCengage prpCengage : prpCengageList){
				if (DataUtils.emptyToNull(prpCengage.getClauses()) != null) {
					specialAgreement += prpCengage.getClauses() + "\r\n";
				}
			}
		}
		propPrpinsClaimInformationObject.setSpecialAgreement(DataUtils.dbNullToEmpty(specialAgreement));
		//查询子报表  主险 数据
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(prpPheadList, policyNo, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		String flag = null;
		if(!CommonUtils.isEmpty(prpCitemKindList)){
			List<PropPrpinsClaimInformationSubOneObject> propPrpinsClaimInformationSubOneObjectList = new ArrayList<PropPrpinsClaimInformationSubOneObject>();
			PropPrpinsClaimInformationSubOneObject propPrpinsClaimInformationSubOneObject = null;
			for(PrpCitemKind prpCitemKind : prpCitemKindList){
				flag = prpCitemKind.getFlag();
				if(flag!=null&&flag.length()>1&&"1".equals(flag.substring(1,2))){
					propPrpinsClaimInformationSubOneObject = new PropPrpinsClaimInformationSubOneObject();
					propPrpinsClaimInformationSubOneObject.setKindName(prpCitemKind.getKindName());
					propPrpinsClaimInformationSubOneObject.setItemCode(prpCitemKind.getItemCode());
					propPrpinsClaimInformationSubOneObject.setItemDetailName(DataUtils.dbNullToEmpty(prpCitemKind.getItemName()));
					propPrpinsClaimInformationSubOneObject.setSumAmount(decimalFormat.format(prpCitemKind.getAmount()));
					propPrpinsClaimInformationSubOneObjectList.add(propPrpinsClaimInformationSubOneObject);
				}
			}
			propPrpinsClaimInformationObject.setPropPrpinsClaimInformationSubOneObjectList(propPrpinsClaimInformationSubOneObjectList);
		}
		//查询子报表 附加險 数据
		if(!CommonUtils.isEmpty(prpCitemKindList)){
			List<PropPrpinsClaimInformationSubTwoObject> propPrpinsClaimInformationSubTwoObjectList = new ArrayList<PropPrpinsClaimInformationSubTwoObject>();
			PropPrpinsClaimInformationSubTwoObject propPrpinsClaimInformationSubTwoObject = null;
			for(PrpCitemKind prpCitemKind : prpCitemKindList){
				flag = prpCitemKind.getFlag();
				if(flag!=null&&flag.length()>1&&"2".equals(flag.substring(1,2))){
					propPrpinsClaimInformationSubTwoObject = new PropPrpinsClaimInformationSubTwoObject();
					propPrpinsClaimInformationSubTwoObject.setKindName(prpCitemKind.getKindName());
					propPrpinsClaimInformationSubTwoObject.setItemCode(prpCitemKind.getItemCode());
					propPrpinsClaimInformationSubTwoObject.setItemDetailName(prpCitemKind.getItemName());
					propPrpinsClaimInformationSubTwoObject.setSumAmount(decimalFormat.format(prpCitemKind.getAmount()));
					propPrpinsClaimInformationSubTwoObjectList.add(propPrpinsClaimInformationSubTwoObject);
				}
			}
			propPrpinsClaimInformationObject.setPropPrpinsClaimInformationSubTwoObjectList(propPrpinsClaimInformationSubTwoObjectList);
		}
		//查询子报表 批改情況 数据
		List<PrpPhead> pheadList = this.prpPheadService.findByPolicyNo(prpLregist.getPolicyNo());
		if(!CommonUtils.isEmpty(pheadList)){
			List<PropPrpinsClaimInformationSubThreeObject> propPrpinsClaimInformationSubThreeObjectList = new ArrayList<PropPrpinsClaimInformationSubThreeObject>();
			PropPrpinsClaimInformationSubThreeObject propPrpinsClaimInformationSubThreeObject = null;
			for(PrpPhead prpPhead : pheadList){
				propPrpinsClaimInformationSubThreeObject = new PropPrpinsClaimInformationSubThreeObject();
				propPrpinsClaimInformationSubThreeObject.setEndorseNo(prpPhead.getEndorseNo());
				propPrpinsClaimInformationSubThreeObject.setEndorReason(this.codeService.translateCodeCode("EndorType", prpPhead.getEndorType(), true));
				propPrpinsClaimInformationSubThreeObject.setEndorDate(PrintUtils.getYearToDayMGStr(prpPhead.getEndorDate()));
				propPrpinsClaimInformationSubThreeObject.setUnderWriteName(prpPhead.getUnderWriteName());
				propPrpinsClaimInformationSubThreeObjectList.add(propPrpinsClaimInformationSubThreeObject);
			}
			propPrpinsClaimInformationObject.setPropPrpinsClaimInformationSubThreeObjectList(propPrpinsClaimInformationSubThreeObjectList);
		}
		//查询子报表 收費情況数据
		List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(QueryRule.getInstance().addEqual("id.policyNo", prpLregist.getPolicyNo()));
		if(!CommonUtils.isEmpty(prpCplanList)){
			List<PropPrpinsClaimInformationSubFourObject> propPrpinsClaimInformationSubFourObjectList = new ArrayList<PropPrpinsClaimInformationSubFourObject>();
			PropPrpinsClaimInformationSubFourObject propPrpinsClaimInformationSubFourObject = null;
			for(PrpCplan prpCplan : prpCplanList){
				propPrpinsClaimInformationSubFourObject = new PropPrpinsClaimInformationSubFourObject();
				propPrpinsClaimInformationSubFourObject.setPaidTimes(prpCplan.getPayNo()==0?"1":prpCplan.getPayNo().toString());
				propPrpinsClaimInformationSubFourObject.setEndorseNo(prpCplan.getEndorseNo());
				propPrpinsClaimInformationSubFourObject.setSumShouldPaid(decimalFormat.format(prpCplan.getPlanFee()));
				if("0".equals(decimalFormat.format(prpCplan.getDelinquentFee()))){
					propPrpinsClaimInformationSubFourObject.setSumRealpaid(decimalFormat.format(prpCplan.getPlanFee()));
					propPrpinsClaimInformationSubFourObject.setArriveDate(PrintUtils.getYearToDayMGStr(prpCplan.getPlanDate()));
				}else {
					propPrpinsClaimInformationSubFourObject.setSumRealpaid("0");
					propPrpinsClaimInformationSubFourObject.setArriveDate(PrintUtils.getYearToDayMGStr(new Date()));
				}
				propPrpinsClaimInformationSubFourObjectList.add(propPrpinsClaimInformationSubFourObject);
			}
			propPrpinsClaimInformationObject.setPropPrpinsClaimInformationSubFourObjectList(propPrpinsClaimInformationSubFourObjectList);
		}
		//查询子报表 賠付記錄数据 
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("policyNo", prpLregist.getPolicyNo());
		queryRule.addAscOrder("damageStartDate");
		queryRule.addAscOrder("claimNo");
		List<PrpLclaim> prpLclaimList = this.prpLclaimService.findPrpLclaim(queryRule);
		if(!CommonUtils.isEmpty(prpLclaimList)){
			List<PropPrpinsClaimInformationSubFiveObject> propPrpinsClaimInformationSubFiveObjectList = new ArrayList<PropPrpinsClaimInformationSubFiveObject>();
			PropPrpinsClaimInformationSubFiveObject subFiveObject = null;
			for(PrpLclaim prpLclaim : prpLclaimList){
				subFiveObject = new PropPrpinsClaimInformationSubFiveObject();
				subFiveObject.setClaimNo(prpLclaim.getClaimNo());
				subFiveObject.setDamageDate(PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));
				subFiveObject.setUndecidedAmount(decimalFormat.format(prpLclaim.getSumClaim()));
				subFiveObject.setClaimPaid(decimalFormat.format(prpLclaim.getSumPaid()));
				subFiveObject.setCloseDate(PrintUtils.getYearToDayMGStr(prpLclaim.getEndCaseDate()));
				//一个赔案号对应多个计算书号，每个计算书号各自对应理算人 、核賠人。但是根据老报表，就取第一条prpLcompensate数据
				List<PrpLcompensate> list = this.prpLcompensateService.findByClaimNo(prpLclaim.getClaimNo());
				if(!CommonUtils.isEmpty(list)){
					PrpLcompensate prpLcompensate = list.get(0);
					subFiveObject.setHandleName(this.prpDuserService.getUserName(prpLcompensate.getHandlerCode()));
					subFiveObject.setUnderWriteName(prpLcompensate.getUnderWriteName());
				}
				propPrpinsClaimInformationSubFiveObjectList.add(subFiveObject);
			}
			propPrpinsClaimInformationObject.setPropPrpinsClaimInformationSubFiveObjectList(propPrpinsClaimInformationSubFiveObjectList);
		}
		//得到UserDto对象
		ActionContext act = ActionContext.getContext();
		Map<String,Object> session = act.getSession();
		UserDto user = (UserDto)session.get("user");
		propPrpinsClaimInformationObject.setHandleName(user.getUserName());
		propPrpinsClaimInformationObject.setPrintTime(PrintUtils.getYearToDayMGStr(new Date()));
		return propPrpinsClaimInformationObject;
		
	}
	/**
	 * 残余物理算书 收集数据
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public PropRemnantObject findPropRemnantObjectByCompensateNo(String compensateNo) throws Exception {
		PropRemnantObject propRemnantObject = new PropRemnantObject();
		propRemnantObject = gaaPrintViewHelper.findRemnantObjectByCompensateNo(compensateNo,propRemnantObject);
		return propRemnantObject;
	}
	
	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
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

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}
	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
	}

	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}

	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}

	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}

	public PrpCplanService getPrpCplanService() {
		return prpCplanService;
	}

	public void setPrpCplanService(PrpCplanService prpCplanService) {
		this.prpCplanService = prpCplanService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcurrencyService getPrpDcurrencyService() {
		return prpDcurrencyService;
	}

	public void setPrpDcurrencyService(PrpDcurrencyService prpDcurrencyService) {
		this.prpDcurrencyService = prpDcurrencyService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public GAAPrintViewHelper getGaaPrintViewHelper() {
		return gaaPrintViewHelper;
	}

	public void setGaaPrintViewHelper(GAAPrintViewHelper gaaPrintViewHelper) {
		this.gaaPrintViewHelper = gaaPrintViewHelper;
	}
	
}
