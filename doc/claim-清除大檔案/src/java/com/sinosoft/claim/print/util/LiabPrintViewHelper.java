package com.sinosoft.claim.print.util;

import ins.framework.common.QueryRule;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.print.vo.LiabCardAppendObject;
import com.sinosoft.claim.print.vo.LiabCardComplexObject;
import com.sinosoft.claim.print.vo.LiabCardObject;
import com.sinosoft.claim.print.vo.LiabClaimApplicationObject;
import com.sinosoft.claim.print.vo.LiabCommissionedObject;
import com.sinosoft.claim.print.vo.LiabCompensateObject;
import com.sinosoft.claim.print.vo.LiabContractObject;
import com.sinosoft.claim.print.vo.LiabInvestigativeObject;
import com.sinosoft.claim.print.vo.LiabNotificationObject;
import com.sinosoft.claim.print.vo.LiabReceiptObject;
import com.sinosoft.claim.print.vo.LiabReconciliationObject;
import com.sinosoft.claim.print.vo.LiabRemittanceObject;
import com.sinosoft.claim.print.vo.LiabRemnantObject;
import com.sinosoft.claim.print.vo.LiabRevocationObject;
import com.sinosoft.claim.print.vo.LiabSingleNoteObject;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;

/**
 * 收集火險列印所需數據
 * @author 中科軟
 */
public class LiabPrintViewHelper {
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
	/** 险类服務 */
	private PrpDclassService prpDclassService;
	/** 险种服務 */
	private PrpDriskService prpDriskService;
	/** 外部机构服務 */
	private PrpLexternalAgencyService prpLexternalAgencyService;
	/**
	 * 工程险和
	 */
	private GAAPrintViewHelper gaaPrintViewHelper;

	/**
	 * 理賠申請書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabClaimApplicationObject findLiabClaimApplicationObjectByClaimNo(String claimNo) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###");
		LiabClaimApplicationObject liabClaimApplicationObject = new LiabClaimApplicationObject();
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		PrpDclass prpDclass = prpDclassService.findPrpDclass(prpLclaim.getClassCode());
		liabClaimApplicationObject.setClassName(prpDclass.getClassName());
		liabClaimApplicationObject.setClaimNo(claimNo);
		liabClaimApplicationObject.setPolicyNo(prpLclaim.getPolicyNo());
		liabClaimApplicationObject.setStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));
		liabClaimApplicationObject.setEndDate(PrintUtils.getYearToDayMGName(prpLclaim.getEndDate()));
		liabClaimApplicationObject.setInsuredName(prpLclaim.getInsuredName());
		PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		liabClaimApplicationObject.setLinkman(prpLregist.getLinkerName());
		liabClaimApplicationObject.setLinkPhone(prpLregist.getPhoneNumber());
		liabClaimApplicationObject.setDamageStartDate(PrintUtils.getDamageDate(prpLclaim.getDamageStartDate(), prpLclaim.getDamageStartHour()));
		liabClaimApplicationObject.setDamageAddress(prpLclaim.getDamageAddress());
		liabClaimApplicationObject.setAmount(df.format(prpLclaim.getSumAmount()));
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
		liabClaimApplicationObject.setAddress(prpCmain.getInsuredAddress());
		String tempContext = "";
		if (claimDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = claimDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}
		PrpLcheck prpLcheck = prpLcheckService.findPrpLcheck(prpLclaim.getRegistNo());
		liabClaimApplicationObject.setDamageContent(tempContext.trim());
		if (prpLcheck != null) {
			liabClaimApplicationObject.setPoliceUnit(DataUtils.dbNullToEmpty(prpLcheck.getPoliceUnit()));
		}
		return liabClaimApplicationObject;
	}

	/***
	 * 根据賠案号查询 信用卡不便險理賠申請書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabCardObject findLiabCardObjectByClaimNo(String claimNo) throws Exception {
		LiabCardObject liabCardObject = new LiabCardObject();
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		String tempContext = "";
		if (claimDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = claimDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}

		liabCardObject.setClaimNo(claimNo);
		liabCardObject.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		liabCardObject.setName("");
		liabCardObject.setId("");
		liabCardObject.setAdress("");
		liabCardObject.setPhone("");
		liabCardObject.setMobile("");
		liabCardObject.setBank("");
		liabCardObject.setCardType("");
		liabCardObject.setCardCode("");
		liabCardObject.setEndDate("");
		liabCardObject.setDamageContent(tempContext);
		liabCardObject.setAmount(String.valueOf(claimDto.getPrpLclaim().getSumAmount()));

		return liabCardObject;
	}

	/***
	 * 根据賠案号查询 信用卡附加旅平險理賠申請書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabCardAppendObject findLiabCardAppendObjectByClaimNo(String claimNo) throws Exception {
		LiabCardAppendObject liabCardAppendObject = new LiabCardAppendObject();
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		String tempContext = "";
		if (claimDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = claimDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}
		PrpCmain prpCmain = policyService.findPrpCmainDtoByPrimaryKey(claimDto.getPrpLclaim().getPolicyNo());
		String appliName = "";// 要保人
		if (prpCmain != null) {
			appliName = prpCmain.getAppliName();
		}

		liabCardAppendObject.setClaimNo(claimNo);
		liabCardAppendObject.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		liabCardAppendObject.setAppliName(appliName);
		liabCardAppendObject.setCardType("");
		liabCardAppendObject.setCardCode("");
		liabCardAppendObject.setName("");
		liabCardAppendObject.setId("");
		liabCardAppendObject.setAdress("");
		liabCardAppendObject.setPhone("");
		liabCardAppendObject.setDamageAdress(claimDto.getPrpLclaim().getDamageAddress());
		liabCardAppendObject.setDamageDate(PrintUtils.getDamageDate(claimDto.getPrpLclaim().getDamageStartDate(), claimDto.getPrpLclaim().getDamageStartHour()));
		liabCardAppendObject.setDamageContent(tempContext);
		// liabCardAppendObject.setHospital(claimDto.getPrpLpersonTraceList().get(0).getHospital());
		return liabCardAppendObject;
	}

	/***
	 * 根据賠案号查询 信用卡綜合保險全球購物理賠申請書 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabCardComplexObject findLiabCardComplexObjectByClaimNo(String claimNo) throws Exception {
		LiabCardComplexObject liabCardComplexObject = new LiabCardComplexObject();
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		String tempContext = "";
		if (claimDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = claimDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}

		liabCardComplexObject.setClaimNo(claimNo);
		liabCardComplexObject.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		liabCardComplexObject.setName("");
		liabCardComplexObject.setAdress("");
		liabCardComplexObject.setPhone("");
		liabCardComplexObject.setCardCode("");
		liabCardComplexObject.setEndDate("");
		liabCardComplexObject.setDamageDate(PrintUtils.getDamageDate(claimDto.getPrpLclaim().getDamageStartDate(), claimDto.getPrpLclaim().getDamageStartHour()));
		liabCardComplexObject.setDamageContent(tempContext);
		liabCardComplexObject.setAmount(String.valueOf(claimDto.getPrpLclaim().getSumAmount()));

		return liabCardComplexObject;
	}

	/**
	 * 匯款同意書 收集数据
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public LiabRemittanceObject findLiabRemittanceObjectByCompensateNo(String compensateNo) throws Exception {
		LiabRemittanceObject liabRemittanceObject = new LiabRemittanceObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		ClaimDto claimDto = claimService.findByPrimaryKey(prpLcompensate.getClaimNo());
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredCode", prpLclaim.getInsuredCode());
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);

		liabRemittanceObject.setClaimNo(prpLcompensate.getClaimNo());
		liabRemittanceObject.setPolicyNo(prpLcompensate.getPolicyNo());
		liabRemittanceObject.setInsuredName(prpLclaim.getInsuredName());
		liabRemittanceObject.setAddress(prpCmain.getInsuredAddress());
		liabRemittanceObject.setIdentifyNumber(prpCinsured.getIdentifyNumber());

		return liabRemittanceObject;
	}

	/**
	 * 賠款同意書暨領款收據 收集数据
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public LiabReceiptObject findLiabReceiptObjectByClaimNo(String compensateNo) throws Exception {
		LiabReceiptObject liabReceiptObject = new LiabReceiptObject();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		ClaimDto claimDto = claimService.findByPrimaryKey(prpLcompensate.getClaimNo());
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredCode", prpLclaim.getInsuredCode());
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);

		liabReceiptObject.setClaimNo(prpLcompensate.getClaimNo());
		liabReceiptObject.setPolicyNo(prpLcompensate.getPolicyNo());
		liabReceiptObject.setInsuredName(prpLclaim.getInsuredName());
		liabReceiptObject.setAddress(prpCmain.getInsuredAddress());
		liabReceiptObject.setIdentifyNumber(prpCinsured.getIdentifyNumber());
		liabReceiptObject.setPhone(prpCinsured.getPhoneNumber());

		return liabReceiptObject;
	}

	/**
	 * 委託公證申請單 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabCommissionedObject findLiabCommissionedObjectByClaimNo(String claimNo, String comName, String userName) throws Exception {
		LiabCommissionedObject liabCommissionedObject = new LiabCommissionedObject();

		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		PrpLcheck prpLcheck = prpLcheckService.findPrpLcheck(new PrpLcheckId(prpLclaim.getRegistNo(), 1));

		liabCommissionedObject.setCurrentTime((PrintUtils.getYearToDayMGName(new Date())));
		liabCommissionedObject.setComName(comName);
		liabCommissionedObject.setName(userName);
		liabCommissionedObject.setClaimNo(claimNo);
		liabCommissionedObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));

		liabCommissionedObject.setFirstLinkMan(prpLregist.getLinkerName());
		if (prpLcheck != null) {
			liabCommissionedObject.setRemark(prpLcheck.getRemark());
			liabCommissionedObject.setEstimateLoss(String.valueOf(prpLcheck.getEstimateLoss()));
			liabCommissionedObject.setDamageAddress(prpLcheck.getDamageAddress());
			if(prpLcheck.getUnitType()=="0"){
				String handleUnitCode = prpLcheck.getHandleUnitCode();
				QueryRule queryRule = QueryRule.getInstance().addEqual("id.comCode", handleUnitCode);
				PrpLexternalAgency prpLexternalAgency = prpLexternalAgencyService.findPrpLexternalAgency(queryRule).get(0);
				liabCommissionedObject.setEscrowCompany(prpLexternalAgency.getComcname());
				liabCommissionedObject.setLinkMan(prpLexternalAgency.getLinkerName());
				liabCommissionedObject.setCompanyAdress(prpLexternalAgency.getAddress());
				liabCommissionedObject.setPhoneNumber(prpLexternalAgency.getLinkernametel());
				liabCommissionedObject.setFaxNumber(prpLexternalAgency.getFaxno());
			}
		}

		return liabCommissionedObject;
	}

	/**
	 * 債權讓與契約暨通知書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabContractObject findLiabContractObjectByClaimNo(String claimNo) throws Exception {
		LiabContractObject liabContractObject = new LiabContractObject();

		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);
		PrpDclass prpDclass = prpDclassService.findPrpDclass(prpLclaim.getClassCode());
		liabContractObject.setClassName(prpDclass.getClassName());
		liabContractObject.setAddress(prpCinsured.getInsuredAddress());
		liabContractObject.setAppliIdentifyNumber(prpCinsured.getIdentifyNumber());
		liabContractObject.setInsuredName(prpCinsured.getInsuredName());
		liabContractObject.setPolicyNo(prpLclaim.getPolicyNo());
		liabContractObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));

		return liabContractObject;
	}

	/**
	 * 撤銷申請理賠同意書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabRevocationObject findLiabRevocationObjectByClaimNo(String claimNo) throws Exception {
		LiabRevocationObject liabRevocationObject = new LiabRevocationObject();

		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		liabRevocationObject.setClaimNo(claimNo);
		liabRevocationObject.setPolicyNo(prpLclaim.getPolicyNo());
		liabRevocationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));

		return liabRevocationObject;
	}

	/**
	 * 補件通知函 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabNotificationObject findLiabNotificationObjectByClaimNo(String claimNo) throws Exception {
		LiabNotificationObject liabNotificationObject = new LiabNotificationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		liabNotificationObject.setClaimNo(claimNo);
		liabNotificationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));
		PrpDrisk prpDrisk = prpDriskService.findPrpDrisk(prpLclaim.getRiskCode());
		liabNotificationObject.setRiskCname(prpDrisk.getRiskCName());

		return liabNotificationObject;
	}

	/**
	 * 查案單 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabInvestigativeObject findLiabInvestigativeObjectByClaimNo(String claimNo) throws Exception {
		LiabInvestigativeObject liabInvestigativeObject = new LiabInvestigativeObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		liabInvestigativeObject.setClaimNo(claimNo);
		liabInvestigativeObject.setInsuredName(prpLclaim.getInsuredName());
		liabInvestigativeObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getStartDate()));

		return liabInvestigativeObject;
	}

	/**
	 * 和解書 收集数据
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabReconciliationObject findLiabReconciliationObjectByClaimNo(String claimNo) throws Exception {
		LiabReconciliationObject liabReconciliationObject = new LiabReconciliationObject();
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		liabReconciliationObject.setInsuredName(prpLclaim.getInsuredName());
		liabReconciliationObject.setDamageStartDate(PrintUtils.getYearToDayMGName(prpLclaim.getDamageStartDate()));

		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", prpLclaim.getPolicyNo()).addEqual("insuredFlag", "1");
		PrpCinsured prpCinsured = this.prpCinsuredService.findPrpCinsured(queryRule).get(0);
		liabReconciliationObject.setIdentifyNumber(prpCinsured.getIdentifyNumber());
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
		liabReconciliationObject.setRoomAddress(prpCmain.getInsuredAddress());

		return liabReconciliationObject;
	}

	/***
	 * 根据賠案号查询 責任險旅行業責任保險理賠照會單 数据对象
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public LiabSingleNoteObject findLiabSingleNoteObjectByClaimNo(String claimNo) throws Exception {
		LiabSingleNoteObject liabSingleNoteObject = new LiabSingleNoteObject();
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		liabSingleNoteObject.setClaimNo(claimNo);
		liabSingleNoteObject.setPolicyNo(prpLclaim.getPolicyNo());
		liabSingleNoteObject.setInsuredName(prpLclaim.getInsuredName());
		liabSingleNoteObject.setLinkerName(prpLregist.getLinkerName());
		liabSingleNoteObject.setOperatorName(this.prpDuserService.getUserName(prpLclaim.getOperatorCode()));
		liabSingleNoteObject.setSystemDate(PrintUtils.getYearToDayMGStr(new Date()));
		return liabSingleNoteObject;
	}

	/**
	 * 残余物理算书 收集数据
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public LiabRemnantObject findLiabRemnantObjectByCompensateNo(String compensateNo) throws Exception {
		LiabRemnantObject liabRemnantObject = new LiabRemnantObject();
		liabRemnantObject = gaaPrintViewHelper.findRemnantObjectByCompensateNo(compensateNo,liabRemnantObject);
		return liabRemnantObject;
	}

	/**
	 * 理賠計算書 收集数据
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public LiabCompensateObject findLiabCompensateObjectByCompensateNo(String compensateNo) throws Exception {
		LiabCompensateObject liabCompensateObject = new LiabCompensateObject();
		liabCompensateObject = gaaPrintViewHelper.findCompensateObjectByCompensateNo(compensateNo, liabCompensateObject);
		return liabCompensateObject;
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

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpLexternalAgencyService getPrpLexternalAgencyService() {
		return prpLexternalAgencyService;
	}

	public void setPrpLexternalAgencyService(PrpLexternalAgencyService prpLexternalAgencyService) {
		this.prpLexternalAgencyService = prpLexternalAgencyService;
	}

	public GAAPrintViewHelper getGaaPrintViewHelper() {
		return gaaPrintViewHelper;
	}

	public void setGaaPrintViewHelper(GAAPrintViewHelper gaaPrintViewHelper) {
		this.gaaPrintViewHelper = gaaPrintViewHelper;
	}

}
