package com.sinosoft.claim.print.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.print.vo.CargoClaimApplicationObject;
import com.sinosoft.claim.print.vo.CargoCommissionedObject;
import com.sinosoft.claim.print.vo.CargoSubrogationObject;
import com.sinosoft.claim.print.vo.CargoTransferObject;
import com.sinosoft.claim.print.vo.CompensateContextObject;
import com.sinosoft.claim.print.vo.CompensateKindInfoObject;
import com.sinosoft.claim.print.vo.CompensatePayInfoObject;
import com.sinosoft.claim.print.vo.CompensateSubreportObject;
import com.sinosoft.claim.print.vo.ShipClaimApplicationObject;
import com.sinosoft.claim.print.vo.ShipCommissionedObject;
import com.sinosoft.claim.print.vo.ShipCompensateObject;
import com.sinosoft.claim.print.vo.ShipContractObject;
import com.sinosoft.claim.print.vo.ShipReceiptObject;
import com.sinosoft.claim.print.vo.ShipReconciliationObject;
import com.sinosoft.claim.print.vo.ShipRemittanceObject;
import com.sinosoft.claim.print.vo.ShipRemnantObject;
import com.sinosoft.claim.print.vo.ShipRevocationObject;
import com.sinosoft.claim.remnant.service.facade.RemnantService;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.schema.model.PrpCCargoItem;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCarGoSubService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;

/**
 * 收集水险列印所需數據
 * @author 中科軟
 */
public class ShipPrintViewHelper {
	/** 賠付對象服務 */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 理算信息服務 */
	private PrpLcompensateService prpLcompensateService;
	/** 立案服務 */
	private PrpLclaimService prpLclaimService;
	/** 保單信息服务 */
	private PrpCmainService prpCmainService;
	/** 備案信息服務 */
	private PrpLregistService prpLregistService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 批单服务 */
	private PrpPheadService prpPheadService;
	/** 报案文字服务 */
	private PrpLregistTextService prpLregistTextService;
	/** 查勘信息服務 */
	private PrpLcheckService prpLcheckService;
	/** 代碼轉換服務 */
	private CodeService codeService;
	/** 机构信息服务 */
	private PrpDcompanyService prpDcompanyService;
	/** 被保險人信息服務 */
	private PrpCinsuredService prpCinsuredService;
	/** 聯共保信息服務 */
	private PrpCcoinsService prpCcoinsService;
	/** 聯共保信息服務 */
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/** 賠款計算方式服務 */
	private PrpLctextService prpLctextService;
	/** 財產損失 服務 */
	private PrpLlossService prpLlossService;
	/** 人傷損失 服務 */
	private PrpLpersonLossService prpLpersonLossService;
	/** 費用信息 服務 */
	private PrpLchargeService prpLchargeService;
	/** 收費狀況 服務 */
	private PrpCplanService prpCplanService;
	/** 操作人員信息服務 */
	private PrpDuserService prpDuserService;
	private PrpDcurrencyService prpDcurrencyService;
	private PrpCaddressService prpCaddressService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 保單相關信息服務 */
	private PolicyService policyService;
	/** 货运险标的信息服務 */
	private PrpCCargoItemService prpCCargoItemService;
	private GAAPrintViewHelper gaaPrintViewHelper;
	private RemnantService remnantService;
	private CommonService commonService;
	private PrpCmainCarGoSubService prpCmainCarGoSubService;
	private PrpCitemKindService prpCitemKindService;
	private PrpLltextService prpLltextService;
	private CompensateService compensateService;
	private EndorseService endorseService;
	/***
	 * 根据保单号查询 貨物運輸險索賠函 数据对象
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public CargoClaimApplicationObject findCargoClaimApplicationObjectByPolicyNo(String policyNo) throws Exception {
		CargoClaimApplicationObject cargoClaimApplicationObject = new CargoClaimApplicationObject();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCCargoItem> prpCCargoItemList = prpCCargoItemService.findPrpCCargoItem(queryRule);
		PrpCCargoItem prpCCargoItem = new PrpCCargoItem();
		if(prpCCargoItemList.size() > 0){
			prpCCargoItem = prpCCargoItemList.get(0);
			cargoClaimApplicationObject.setGoodsName(prpCCargoItem.getCargoName());
		}else{
			cargoClaimApplicationObject.setGoodsName("");
		}
		cargoClaimApplicationObject.setPolicyNo(policyNo);
		return cargoClaimApplicationObject;
	}

	/***
	 * 根据备案号查询委托公证申请书 数据对象
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public CargoCommissionedObject findCargoCommissionedObjectByRegistNo(String registNo) throws Exception {
		CargoCommissionedObject CargoCommissionedObject = new CargoCommissionedObject();
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		if(prpLregist != null){
			PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpLregist.getComCode());
			
			CargoCommissionedObject.setComName(DataUtils.dbNullToEmpty(prpDcompany.getComCName()));
			CargoCommissionedObject.setRegistNo(registNo);
			CargoCommissionedObject.setPolicyNo(prpLregist.getPolicyNo());
			CargoCommissionedObject.setDamageTime(PrintUtils.getYearToDayMGName(prpLregist.getDamageStartDate()));
			CargoCommissionedObject.setInsuredName(DataUtils.dbNullToEmpty(prpLregist.getInsuredName()));
			CargoCommissionedObject.setLinkerName(DataUtils.dbNullToEmpty(prpLregist.getLinkerName()));
			CargoCommissionedObject.setPhoneNumber(DataUtils.dbNullToEmpty(prpLregist.getPhoneNumber()));
			CargoCommissionedObject.setDamageAddress(DataUtils.dbNullToEmpty(prpLregist.getDamageAddress()));
			CargoCommissionedObject.setDamageName(DataUtils.dbNullToEmpty(prpLregist.getDamageName()));
			CargoCommissionedObject.setLossName(DataUtils.dbNullToEmpty(prpLregist.getLossName()));
			CargoCommissionedObject.setEstimateLoss(new DecimalFormat("#,###").format(prpLregist.getEstimateLoss()));
		}
		return CargoCommissionedObject;
	}

	/***
	 * 根据赔案号查询 貨物運輸險代位追償權利書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public CargoSubrogationObject findCargoSubrogationObjectByClaimNo(String claimNo) throws Exception {
		CargoSubrogationObject cargoSubrogationObject = new CargoSubrogationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		if(prpLclaim != null){
			cargoSubrogationObject.setPolicyNo(prpLclaim.getPolicyNo());
			cargoSubrogationObject.setClaimNo(claimNo);
			cargoSubrogationObject.setInsuredName(prpLclaim.getInsuredName());
			
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
			List<PrpCCargoItem> prpCCargoItemList = prpCCargoItemService.findPrpCCargoItem(queryRule);
			PrpCCargoItem prpCCargoItem = new PrpCCargoItem();
			if(prpCCargoItemList.size() > 0){
				prpCCargoItem = prpCCargoItemList.get(0);
				cargoSubrogationObject.setGoodsName(prpCCargoItem.getCargoName());
			}
		}
		return cargoSubrogationObject;
	}

	/***
	 * 根据保单号查询 貨物運輸險權利轉讓書 数据对象
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public CargoTransferObject findCargoTransferObjectByPolicyNo(String policyNo) throws Exception {
		CargoTransferObject cargoTransferObject = new CargoTransferObject();
		cargoTransferObject.setPolicyNo(policyNo);
		
		PrpCmain prpCmain = prpCmainService.findByPrimaryKey(policyNo);
		cargoTransferObject.setInsuredName(prpCmain.getInsuredName());
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCCargoItem> prpCCargoItemList = prpCCargoItemService.findPrpCCargoItem(queryRule);
		PrpCCargoItem prpCCargoItem = new PrpCCargoItem();
		if(prpCCargoItemList.size() > 0){
			prpCCargoItem = prpCCargoItemList.get(0);
			cargoTransferObject.setGoodsName(prpCCargoItem.getCargoName());
		}
		return cargoTransferObject;
	}
	
	/***
	 * 根据备案号查询理賠申請書 数据对象
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public ShipClaimApplicationObject findShipClaimApplicationObjectByRegistNo(String registNo) throws Exception {
		ShipClaimApplicationObject shipClaimApplicationObject = new ShipClaimApplicationObject();
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		if(prpLregist != null){
			String riskName = codeService.translateRiskCode(prpLregist.getRiskCode(), true);
			shipClaimApplicationObject.setRiskName(riskName);
			shipClaimApplicationObject.setRegistNo(registNo);
			shipClaimApplicationObject.setInsuredName(prpLregist.getInsuredName());
			shipClaimApplicationObject.setInsuredAddress(prpLregist.getInsuredAddress());
			shipClaimApplicationObject.setPolicyNo(prpLregist.getPolicyNo());
//			PolicyDto policyDto = policyService.findByPrimaryKey(prpLregist.getPolicyNo());
			String policyNo = prpLregist.getPolicyNo();
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			shipClaimApplicationObject.setStartDate(PrintUtils.getYearToDayMGName(prpCmain.getStartDate()));
			shipClaimApplicationObject.setEndDate(PrintUtils.getYearToDayMGName(prpCmain.getEndDate()));
			String conditions = " policyno = '" + prpLregist.getPolicyNo() + "'";
			int payFlag = policyService.checkPay(conditions);
			if(payFlag == 1){
				shipClaimApplicationObject.setPayFlag("已收");
			}else{
				shipClaimApplicationObject.setPayFlag("未收");
			}
			shipClaimApplicationObject.setCoverage("");//目前承包範圍暫時無法取值
			List<PrpLregistText> prpLregistTextList = prpLregistTextService.findByRegistNo(registNo, "1");
			String damageContent = "";
			for(PrpLregistText prpLregistText:prpLregistTextList){
				damageContent = damageContent + prpLregistText.getContext();
			}
			shipClaimApplicationObject.setDamageContent(damageContent);
			shipClaimApplicationObject.setEstimateLoss(prpLregist.getEstimateLoss().toString());
		}
		return shipClaimApplicationObject;
	}
	
	/***
	 * 根据赔案号查询 匯款同意書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public ShipRemittanceObject findShipRemittanceObjectByClaimNo(String claimNo) throws Exception {
		ShipRemittanceObject shipRemittanceObject = new ShipRemittanceObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
		queryRule.addEqual("insuredFlag", "1");
		List<PrpCinsured> prpCinsuredList = prpCinsuredService.findPrpCinsured(queryRule);
		if(prpCinsuredList.size() > 0){
			PrpCinsured prpCinsured = prpCinsuredList.get(0);
			shipRemittanceObject.setClaimNo(claimNo);
			shipRemittanceObject.setPolicyNo(prpLclaim.getPolicyNo());
			shipRemittanceObject.setInsuredName(prpCinsured.getInsuredName());
			shipRemittanceObject.setInsuredAddress(prpCinsured.getPostAddress());
			shipRemittanceObject.setInsuredIdentifyNumber(prpCinsured.getIdentifyNumber());
		}
		return shipRemittanceObject;
	}
	
	/***
	 * 根据赔案号查询 賠款同意書暨領款收據 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public ShipReceiptObject findShipReceiptObjectByClaimNo(String claimNo) throws Exception {
		ShipReceiptObject shipReceiptObject = new ShipReceiptObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
		queryRule.addEqual("insuredFlag", "1");
		List<PrpCinsured> prpCinsuredList = prpCinsuredService.findPrpCinsured(queryRule);
		if(prpCinsuredList.size() > 0){
			PrpCinsured prpCinsured = prpCinsuredList.get(0);
			shipReceiptObject.setClaimNo(claimNo);
			shipReceiptObject.setPolicyNo(prpLclaim.getPolicyNo());
			shipReceiptObject.setInsuredName(prpCinsured.getInsuredName());
			shipReceiptObject.setInsuredAddress(prpCinsured.getPostAddress());
			shipReceiptObject.setInsuredIdentifyNumber(prpCinsured.getIdentifyNumber());
		}
		return shipReceiptObject;
	}
	
	/***
	 * 根据备案号查询委託公證申請單 数据对象
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public ShipCommissionedObject findShipCommissionedObjectByRegistNo(String registNo) throws Exception {
		ShipCommissionedObject shipCommissionedObject = new ShipCommissionedObject();
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		if(prpLregist != null){
			PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpLregist.getComCode());
			shipCommissionedObject.setComName(DataUtils.dbNullToEmpty(prpDcompany.getComCName()));
			shipCommissionedObject.setRegistNo(registNo);
			shipCommissionedObject.setPolicyNo(prpLregist.getPolicyNo());
			shipCommissionedObject.setDamageTime(PrintUtils.getYearToDayMGName(prpLregist.getDamageStartDate()));
			shipCommissionedObject.setInsuredName(DataUtils.dbNullToEmpty(prpLregist.getInsuredName()));
			shipCommissionedObject.setLinkerName(DataUtils.dbNullToEmpty(prpLregist.getLinkerName()));
			shipCommissionedObject.setPhoneNumber(DataUtils.dbNullToEmpty(prpLregist.getPhoneNumber()));
			shipCommissionedObject.setDamageAddress(DataUtils.dbNullToEmpty(prpLregist.getDamageAddress()));
			shipCommissionedObject.setDamageName(DataUtils.dbNullToEmpty(prpLregist.getDamageName()));
			shipCommissionedObject.setLossName(DataUtils.dbNullToEmpty(prpLregist.getLossName()));
			shipCommissionedObject.setEstimateLoss(new DecimalFormat("#,###").format(prpLregist.getEstimateLoss()));
		}
		return shipCommissionedObject;
	}
	
	/***
	 * 根据保单号查询 債權讓與契約暨通知書 数据对象
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public ShipContractObject findShipContractObjectByPolicyNo(String policyNo) throws Exception {
		ShipContractObject shipContractObject = new ShipContractObject();
		PrpCmain prpCmain = policyService.findPrpCmainDtoByPrimaryKey(policyNo);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		queryRule.addEqual("insuredFlag", "1");
		List<PrpCinsured> prpCinsuredList = prpCinsuredService.findPrpCinsured(queryRule);
		if(prpCinsuredList.size() > 0){
			PrpCinsured prpCinsured = prpCinsuredList.get(0);
			shipContractObject.setInsuredName(prpCinsured.getInsuredName());
			shipContractObject.setInsuredAddress(prpCinsured.getRoomAddress());
			shipContractObject.setInsuredIdentifyNumber(prpCinsured.getIdentifyNumber());
		}
		queryRule.getQueryRuleList().clear();
		queryRule.getRuleList().clear();
		queryRule.addEqual("policyNo", policyNo);
		queryRule.addDescOrder("damageStartDate");
		List<PrpLregist> prpLregistList = prpLregistService.findPrpLregist(queryRule);
		if(prpLregistList.size() > 0){
			PrpLregist prpLregist = prpLregistList.get(0);
			shipContractObject.setDamageTime(PrintUtils.getYearToDayMGName(prpLregist.getDamageStartDate()));
		}
		shipContractObject.setPolicyNo(policyNo);
		String riskName = codeService.translateRiskCode(prpCmain.getRiskCode(), true);
		shipContractObject.setRiskName(riskName);
		return shipContractObject;
	}
	
	/***
	 * 根据保单号查询 撤銷申請理賠同意書 数据对象
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public ShipRevocationObject findShipRevocationObjectByPolicyNo(String policyNo) throws Exception {
		ShipRevocationObject shipRevocationObject = new ShipRevocationObject();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("policyNo", policyNo);
		queryRule.addDescOrder("damageStartDate");
		List<PrpLregist> prpLregistList = prpLregistService.findPrpLregist(queryRule);
		if(prpLregistList.size() > 0){
			PrpLregist prpLregist = prpLregistList.get(0);
			shipRevocationObject.setDamageTime(PrintUtils.getYearToDayMGStr(prpLregist.getDamageStartDate()));
		}
		queryRule.getQueryRuleList().clear();
		queryRule.getRuleList().clear();
		queryRule.addEqual("policyNo", policyNo);
		queryRule.addDescOrder("claimDate");
		List<PrpLclaim> prpLclaimList = prpLclaimService.findPrpLclaim(queryRule);
		if(prpLclaimList.size() > 0){
			PrpLclaim prpLclaim = prpLclaimList.get(0);
			shipRevocationObject.setClaimNo(prpLclaim.getClaimNo());
		}
		shipRevocationObject.setPolicyNo(policyNo);
		return shipRevocationObject;
	}
	
	/***
	 * 根据赔案号查询 和解書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public ShipReconciliationObject findShipReconciliationObjectByCompensateNo(String claimNo) throws Exception {
		ShipReconciliationObject shipReconciliationObject = new ShipReconciliationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		shipReconciliationObject.setDamageTime(PrintUtils.getYearToDayMGName(prpLregist.getDamageStartDate()));
		if(!CommonUtils.isEmpty(prpLregist.getDamageStartHour())){
			shipReconciliationObject.setDamageHour(prpLregist.getDamageStartHour().substring(0,2));
			shipReconciliationObject.setDamageMinute(prpLregist.getDamageStartHour().substring(3, 5));
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", prpLclaim.getPolicyNo());
		queryRule.addEqual("insuredFlag", "1");
		List<PrpCinsured> prpCinsuredList = prpCinsuredService.findPrpCinsured(queryRule);
		if(prpCinsuredList.size() > 0){
			PrpCinsured prpCinsured = prpCinsuredList.get(0);
			shipReconciliationObject.setInsuredName(DataUtils.dbNullToEmpty(prpCinsured.getInsuredName()));
			shipReconciliationObject.setInsuredAddress(DataUtils.dbNullToEmpty(prpCinsured.getRoomAddress()));
			shipReconciliationObject.setInsuredIdentifyNumber(DataUtils.dbNullToEmpty(prpCinsured.getIdentifyNumber()));
		}
		return shipReconciliationObject;
	}
	/**
	 *  残余物理算书 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception 
	 */
	public ShipRemnantObject findShipRemnantObjectByCompensateNo(String compensateNo) throws Exception{
		DecimalFormat df = new DecimalFormat("#,###");
		ShipRemnantObject shipRemnantObject = new ShipRemnantObject();
		shipRemnantObject = gaaPrintViewHelper.findRemnantObjectByCompensateNo(compensateNo,shipRemnantObject);
		RemnantDto remnantDto = remnantService.findByPrimaryKey(compensateNo);
		PrpLclaim prpLclaim = remnantDto.getPrpLclaim();
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);//
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		DateTime dateTime = new DateTime(prpCmain.getInputDate());
		shipRemnantObject.setPolicyYear(String.valueOf(dateTime.getYear()));
		shipRemnantObject.setSumAmount(df.format(prpCmain.getSumAmount()));
		shipRemnantObject.setDamageName(prpLclaim.getDamageName());
		PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		/**運輸方式  ,1-海運、2-空運、3-陸運、4-郵寄*/
		if("1".equals(prpLclaim.getTransportType())){
			shipRemnantObject.setTransportType("海運");
		}else if("2".equals(prpLclaim.getTransportType())){
			shipRemnantObject.setTransportType("空運");
		}else if("3".equals(prpLclaim.getTransportType())){
			shipRemnantObject.setTransportType("陸運");
		}else if("4".equals(prpLclaim.getTransportType())){
			shipRemnantObject.setTransportType("郵寄");
		}
		//开航日期
		shipRemnantObject.setEndorseNo(this.findEndorseNo(prpLclaim));//批单号码
		shipRemnantObject.setShipName(DataUtils.dbNullToEmpty(prpLregist.getShipCName()));//从备案环节取
		shipRemnantObject.setRegistDate(PrintUtils.getYearToDayMGStr(prpLregist.getReportDate()));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("claimNo", prpLclaim.getClaimNo());
		queryRule.addLike("compensateNo", "C%");
		queryRule.addDescOrder("compensateNo");
		List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
		String startSitePort = "";
		String endSitePort = "";
		if(prpLcompensateList.size()>0){
			PrpLcompensate prpLcompensate = prpLcompensateList.get(0);
			startSitePort = "             ";
			if(DataUtils.emptyToNull(prpLcompensate.getStartSitePort())!=null){
				startSitePort = DataUtils.dbNullToEmpty(prpLcompensate.getStartSitePort()) + "  ";
			}
			if(DataUtils.emptyToNull(prpLcompensate.getStartSiteCountry())!=null){
				startSitePort += DataUtils.dbNullToEmpty(prpLcompensate.getStartSiteCountry());
			}else{
				startSitePort += "             ";
			}
			endSitePort = "             ";
			if(DataUtils.emptyToNull(prpLcompensate.getEndSitePort())!=null){
				endSitePort = DataUtils.dbNullToEmpty(prpLcompensate.getEndSitePort()) + "  ";
			}
			if(DataUtils.emptyToNull(prpLcompensate.getEndSiteCountry())!=null){
				endSitePort += DataUtils.dbNullToEmpty(prpLcompensate.getEndSiteCountry());
			}else{
				endSitePort += "             ";
			}
		}
		shipRemnantObject.setStartSitePort(startSitePort);
		shipRemnantObject.setEndSitePort(endSitePort);
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		if ("RISKCODE_YMC".equals(configCode)) {// 貨物類別代號
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCCargoItem> prpCCargoItemList = this.prpCCargoItemService.findPrpCCargoItem(queryRule);
			if (!CommonUtils.isEmpty(prpCCargoItemList)) {
				shipRemnantObject.setCargoType(((PrpCCargoItem) prpCCargoItemList.get(0)).getCargoBigTypeCode());
			}
			shipRemnantObject.setPortDate(PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()));
		} else if ("RISKCODE_YAV".equals(configCode)) {
			String policyCondition = "";
			for(PrpCitemKind prpCitemKind : prpCitemKindList){
				policyCondition+= ","+prpCitemKind.getKindCode();
			}
			if(policyCondition.length()>0){
				shipRemnantObject.setPolicyCondition(policyCondition.substring(1));
			}
		}
		for(CompensateSubreportObject remnantSubreportObject : shipRemnantObject.getCompensateSubreport0Object()){
			for(PrpCitemKind prpCitemKind : prpCitemKindList){
				if(remnantSubreportObject.getKindCode().equals(prpCitemKind.getKindCode())){
					remnantSubreportObject.setAmount(df.format(prpCitemKind.getAmount()));
					break;
				}
			}
		}
		return shipRemnantObject;
	}
	/***
	 * 貨運險追償列印
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	public ShipCompensateObject findCargoRecovery(PrpLcompensate prpLcompensate) throws Exception {
		List<?> tempList = null;
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		ShipCompensateObject reportObject = new ShipCompensateObject();
		this.initShipCompensate(prpLcompensate, reportObject);
		String conditions = null ;
		conditions = " compensateNo = '"+prpLcompensate.getCompensateNo()+"' and textType = '26' order by lineNo asc ";
		tempList = this.prpLctextService.findPrpLctext(QueryRule.getInstance().addSql(conditions));
		if(!CommonUtils.isEmpty(tempList)){
			StringBuffer contextAll = new StringBuffer("");//追償說明
			CompensateContextObject contextObject = null;
			for (Iterator<?> it = tempList.iterator(); it.hasNext();) {
				contextAll.append(((PrpLctext)it.next()).getContext());
			}
			for(String line :contextAll.toString().split("\r\n")){
				contextObject = new CompensateContextObject();
				contextObject.setContext(line);
				reportObject.getContextList().add(contextObject);
			}
		}
		DecimalFormat df = new DecimalFormat("#,###");
		reportObject.setSumPaid(df.format(prpLclaim.getSumPaid()));// 总赔付金额
		return reportObject;
	}

	/***
	 * 理算書列印
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	public ShipCompensateObject printShipCompensate(PrpLcompensate prpLcompensate) throws Exception {
		List<?> tempList = null;
		String conditions = "";
		String compensateNo = prpLcompensate.getCompensateNo();
		ShipCompensateObject reportObject = new ShipCompensateObject();
		this.initShipCompensate(prpLcompensate, reportObject);
		//and certiType = '" + PrpLpayObjectInfo.CERTITYPE_PAYOBJECT + "' 费用赔付对象也需要列印出来
		conditions = " compensateNo = '" + compensateNo + "'  order by certiType,serialNo asc ";
		tempList = this.prpLpayObjectInfoService.findPrpLpayObjectInfo(QueryRule.getInstance().addSql(conditions));
		if (!CommonUtils.isEmpty(tempList)) {
			CompensatePayInfoObject payObject = null;
			for (Iterator<?> it = tempList.iterator(); it.hasNext();) {
				payObject = new CompensatePayInfoObject();
				payObject.init((PrpLpayObjectInfo)it.next());
				reportObject.getPayInfoList().add(payObject);
			}
		}
		conditions = " TextType = '05' and CompensateNo = '"+ prpLcompensate.getCompensateNo() +"' order by lineNo asc ";
		tempList = this.prpLctextService.findPrpLctext(QueryRule.getInstance().addSql(conditions));
		if (!CommonUtils.isEmpty(tempList)) {
			StringBuffer contextAll = new StringBuffer("");
			CompensateContextObject contextObject = null;
			for (Iterator<?> it = tempList.iterator(); it.hasNext();) {
				contextAll.append(((PrpLctext)it.next()).getContext());
			}
			for(String line :contextAll.toString().split("\r\n")){
				contextObject = new CompensateContextObject();
				contextObject.setContext(line);
				reportObject.getContextList().add(contextObject);
			}
		}
		return reportObject;
	}
	
	private void initShipCompensate(PrpLcompensate prpLcompensate,ShipCompensateObject reportObject) throws Exception{
		List<?> tempList = null;
		DecimalFormat df = new DecimalFormat("#,###");
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		String policyNo = prpLcompensate.getPolicyNo();
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
		reportObject.setCompensateNo(prpLcompensate.getCompensateNo());// 追償號碼
		reportObject.setTimes(String.valueOf(prpLcompensate.getTimes()));
		reportObject.setPolicyNo(prpLcompensate.getPolicyNo());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(prpCmain.getInputDate());
		reportObject.setRiskCName(this.codeService.translateRiskCode(prpLcompensate.getRiskCode(), true));
		reportObject.setPolicyYear(String.valueOf(calendar.get(Calendar.YEAR)));
		reportObject.setInsuredName(prpLclaim.getInsuredName());
		reportObject.setAppliName(prpCmain.getAppliName());
		reportObject.setCurrency(prpLcompensate.getCurrency());
		reportObject.setSumThisPaid(df.format(Math.abs(prpLcompensate.getSumThisPaid())));
		StringBuffer duration = new StringBuffer("");//保險期間
		duration.append(PrintUtils.getYearToDayMGStr(prpCmain.getStartDate())).append(" ");
		duration.append(String.valueOf(prpCmain.getStartHour())).append("時").append(" 至");
		duration.append(" ").append(PrintUtils.getYearToDayMGStr(prpCmain.getEndDate())).append(" ");
		duration.append(String.valueOf(prpCmain.getEndHour())).append("時");
		reportObject.setDuration(duration.toString());
		String transportType = prpLclaim.getTransportType();
		/** 運輸方式 立案運輸 1-海運、2-空運、3-陸運、4-郵寄 */
		if ("1".equals(transportType)) {
			reportObject.setTransportTypeStr("海運");
		} else if ("2".equals(transportType)) {
			reportObject.setTransportTypeStr("空運");
		} else if ("3".equals(transportType)) {
			reportObject.setTransportTypeStr("陸運");
		} else if ("4".equals(transportType)) {
			reportObject.setTransportTypeStr("郵寄");
		}
		String conditions = " policyno = '" + prpLcompensate.getPolicyNo() + "'";
		String statements = "select payRefDate from prpJPayRefRecHis where policyno = '" + prpLclaim.getPolicyNo() + "' and realpayrefflag = '1' and certitype='P'";
		List<?> tempResult = this.commonService.findByStatements(statements);
		reportObject.setPayStatus("未收");
		if (!CommonUtils.isEmpty(tempResult)) {// 收费日期
			reportObject.setPayStatus("已收");
			reportObject.setPayDateStr(PrintUtils.getYearToDayMGStr(new Date(((Timestamp) tempResult.get(0)).getTime())));
		}
		if ("RISKCODE_YMC".equals(configCode)) {// 貨物類別代號
			if (!CommonUtils.isEmpty(prpLclaim.getEndorseNo())) {
				PrpLclaim tempPrpLclaim = claimService.generateCargoInfo(null, prpLclaim.getEndorseNo());
				reportObject.setShipCName(tempPrpLclaim.getShipCName());
				reportObject.setCargoType(tempPrpLclaim.getCargoNo());
				reportObject.setSailStartDateStr(tempPrpLclaim.getSailStartDate());
				
				prpLcompensate.setAreaCode(tempPrpLclaim.getAreaCode());
			}else{
				tempList = prpCCargoItemService.findPrpCCargoItem(QueryRule.getInstance().addEqual("id.policyNo", policyNo));
				if (!CommonUtils.isEmpty(tempList)) {
					reportObject.setCargoType(((PrpCCargoItem) tempList.get(0)).getCargoBigTypeCode());
				}
				reportObject.setSailStartDateStr(PrintUtils.getYearToDayMGStr(prpCmain.getStartDate()));// 開航日期
				// 船名 MC prpCmainCarGoSubs[0].siteName
				conditions = " policyNo = '" + prpLcompensate.getPolicyNo() + "' order by serialNo asc ";
				tempList = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(conditions);
				if (!CommonUtils.isEmpty(tempList)) {// 设置船名
					reportObject.setShipCName(DataUtils.dbNullToEmpty(((PrpCmainCarGoSub) tempList.get(0)).getSiteName()));
				}
			}
		} else if ("RISKCODE_YAV".equals(configCode)) {
			conditions = " policyNo = '" + prpLcompensate.getPolicyNo() + "' order by itemKindNo asc ";
			tempList = this.prpCitemKindService.findByConditions(conditions);
			if (!CommonUtils.isEmpty(tempList)) {// 保险条件
				PrpCitemKind p = (PrpCitemKind) tempList.get(0);
				reportObject.setPolicyCondition(p.getKindCode() + " " + p.getKindName());
			}
		}
		reportObject.setDamageStartDateStr(PrintUtils.getYearToDayMGStr(prpLclaim.getDamageStartDate()));// 出險日期
		reportObject.setReportDateStr(PrintUtils.getYearToDayMGStr(prpLregist.getReportDate()));// 受理日期(備案日期)
		reportObject.setEndCaseDateStr(PrintUtils.getYearToDayMGStr(prpLclaim.getEndCaseDate()));// 結案日期
		reportObject.setCaseNo(DataUtils.dbNullToEmpty(prpLclaim.getCaseNo()));// 結案號碼
		reportObject.setDamageName(prpLclaim.getDamageName());// 出險原因
		reportObject.setSumAmount(df.format(prpLclaim.getSumAmount()));// 总保险金额
		reportObject.setSumPremium(df.format(prpCmain.getSumPremium()));// 总保险费
		reportObject.setSumPaid(df.format(prpLcompensate.getSumPaid()));// 总赔付金额
		String startSite = "             ";
		if(DataUtils.emptyToNull(prpLcompensate.getStartSitePort())!=null){
			startSite = DataUtils.dbNullToEmpty(prpLcompensate.getStartSitePort()) + "  ";
		}
		if(DataUtils.emptyToNull(prpLcompensate.getStartSiteCountry())!=null){
			startSite += DataUtils.dbNullToEmpty(prpLcompensate.getStartSiteCountry());
		}else{
			startSite += "             ";
		} 
		String endSite = "             ";
		if(DataUtils.emptyToNull(prpLcompensate.getEndSitePort())!=null){
			endSite = DataUtils.dbNullToEmpty(prpLcompensate.getEndSitePort()) + "  ";
		}
		if(DataUtils.emptyToNull(prpLcompensate.getEndSiteCountry())!=null){
			endSite += DataUtils.dbNullToEmpty(prpLcompensate.getEndSiteCountry());
		}else{
			endSite += "             ";
		}
		reportObject.setStartSite(startSite);
		reportObject.setEndSite(endSite);
		reportObject.setEndorseNo(this.findEndorseNo(prpLclaim));
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			List<String> list = this.compensateService.getPayRiskCode(prpLcompensate.getCompensateNo());
			for (PrpCitemKind prpCitemKind : prpCitemKindList) {
				CompensateKindInfoObject kindInfoObject = null;
				if (list.contains(prpCitemKind.getKindCode())) {
					kindInfoObject = new CompensateKindInfoObject();
					kindInfoObject.setKindCode(prpCitemKind.getKindCode());
					kindInfoObject.setKindName(prpCitemKind.getKindName());
					kindInfoObject.setAmount(df.format(prpCitemKind.getAmount()));
					reportObject.getKindInfoList().add(kindInfoObject);
				}
			}
		}
	}
	/**
	 * 查询批单号码
	 * @param prpLclaim
	 * @param polciyNo
	 * @return
	 * @throws Exception
	 */
	public String findEndorseNo(PrpLclaim prpLclaim)throws Exception{
		String endorseNo = "";
		if(ConstantCodes.CARGO_RISKCODE.contains(prpLclaim.getRiskCode())){
			endorseNo = prpLclaim.getEndorseNo();
		}else{
			// 将字符串转换成整型
			int theDamageHour = 0;
			String strDamageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String strDamageHour = prpLclaim.getDamageStartHour();
			if (strDamageHour != null && strDamageHour.length() > 1) {
				theDamageHour = Integer.parseInt(strDamageHour.substring(0, 2));
			}
			if (strDamageDate != null && strDamageDate.length() > 9) {
				strDamageDate = strDamageDate.substring(0, 10);
			}
			// 取得批改信息表信息
			String iWherePart = "PolicyNo = '" + prpLclaim.getPolicyNo() + "'" + " AND (ValidDate >to_date('" + strDamageDate + "','yyyy-MM-dd') OR (ValidDate=to_date('" + strDamageDate + "','yyyy-MM-dd') AND ValidHour>" + theDamageHour + "))"
					+ " AND UnderWriteFlag in ('1', '3') " + " ORDER BY InputDate DESC,EndorseTimes DESC ";
			List<PrpPhead> listTemp = this.endorseService.findByPrpPheadConditions(iWherePart);
			if(listTemp.size()>0){
				endorseNo = listTemp.get(0).getEndorseNo();
			}
		}
		return endorseNo;
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

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpCCargoItemService getPrpCCargoItemService() {
		return prpCCargoItemService;
	}

	public void setPrpCCargoItemService(PrpCCargoItemService prpCCargoItemService) {
		this.prpCCargoItemService = prpCCargoItemService;
	}
	public GAAPrintViewHelper getGaaPrintViewHelper() {
		return gaaPrintViewHelper;
	}

	public void setGaaPrintViewHelper(GAAPrintViewHelper gaaPrintViewHelper) {
		this.gaaPrintViewHelper = gaaPrintViewHelper;
	}

	public RemnantService getRemnantService() {
		return remnantService;
	}

	public void setRemnantService(RemnantService remnantService) {
		this.remnantService = remnantService;
	}

	
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public PrpCmainCarGoSubService getPrpCmainCarGoSubService() {
		return prpCmainCarGoSubService;
	}

	public void setPrpCmainCarGoSubService(PrpCmainCarGoSubService prpCmainCarGoSubService) {
		this.prpCmainCarGoSubService = prpCmainCarGoSubService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpLltextService getPrpLltextService() {
		return prpLltextService;
	}

	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}
}
