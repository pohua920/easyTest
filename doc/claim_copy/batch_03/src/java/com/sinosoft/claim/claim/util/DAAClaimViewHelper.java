package com.sinosoft.claim.claim.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.util.CompensateKindLimitViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpClimitDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.schema.model.PrpCCargoItem;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRisk;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimCredit;
import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclause;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLltextId;
import com.sinosoft.claim.schema.model.PrpLperson;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLpersonLossId;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrplregistrpolicyId;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpDpersonFeeCodeRiskService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclauseService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.ui.control.action.UIExchAction;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.ui.control.action.UIPrpClimitAction;
import com.sinosoft.claim.ui.control.viewHelper.SendUndwrtViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.function.insutil.bl.facade.BLPubRateFacade;
import com.sinosoft.function.insutil.dto.domain.PrpDexchDto;
import com.sinosoft.platform.bl.facade.BLUtiUserGradeFacade;
import com.sinosoft.platform.dto.domain.UtiUserGradeDto;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <p>
 * Title: ClaimViewHelper
 * </p>
 * <p>
 * Description:立案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class DAAClaimViewHelper extends ClaimViewHelper {
	/** 立案注销文字每行最大能显示的 */
	private int RULE_LENGTH = 70; // rule字段的长度
	/** 赔案保单关联信息服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 代码服务 */
	private CodeService codeService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 立案数据传输对象 */
//	private ClaimDto claimDto;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 查勘服务 */
	private CheckService checkService;
	/** 报案服务 */
	private RegistService registService;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	/** 报案文字信息服务 */
	private PrpLregistTextService prpLregistTextService;
	/** 立案文字信息服务 */
	private PrpLltextService prpLltextService;
	/** 报案ViewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 查勘信息服务 */
	private PrpLcheckService prpLcheckService;
	/** 定损服务 */
	private CertainLossService certainLossService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 预赔登记信息服务 */
	private PrpLprepayService prpLprepayService;
	/** 限额/免赔跟踪信息服务 */
	private PrpClimitService prpClimitService;
	/** 代理人代码信息服务 */
	private PrpDagentService prpDagentService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 批单ViewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 标的子险信息服务 */
	private PrpCitemKindService prpCitemKindService;
	/** 批单数据传输对象服务 */
	private EndorseService endorseService;
	/** 单号取号服务 */
	private BillService billService;
	/** 再保管理对象 */
	private ReinsServiceManager reinsServiceManager;
	/** 共保信息服务 */
	private PrpCcoinsService prpCcoinsService;
	/** 通用代码数据服务 */
	private PrpDcodeService prpDcodeService;
	/** 人伤费用险种对照信息服务 */
	private PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 水险标的信息流服务 */
	private PrpCCargoItemService prpCCargoItemService;
	private PrpLclauseService prpLclauseService;
	private PrpCaddressService prpCaddressService;
	private PrpCplaneService prpCplaneService;
	private PrpCitemShipService prpCitemShipService;
	private PrpCmainLiabService prpCmainLiabService;
	private PrpCmainCargoService prpCmainCargoService;
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
	private PrpLpersonLossService prpLpersonLossService;
	// mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
	private org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	/**
	 * 默认构造方法
	 */
	public DAAClaimViewHelper() {
	}

	/**
	 * 保存立案时立案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return claimDto 立案数据传输数据结构
	 * @throws Exception
	 */
	public ClaimDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对claim,claimsText表的赋值
		ClaimDto claimDto = super.viewToDto(httpServletRequest);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String buttonSaveType = httpServletRequest.getParameter("buttonSaveType").trim();
		// 页面取不到时才给它赋值
		if ("".equals(prpLclaim.getClaimType()) || prpLclaim.getClaimType() == null) {
			prpLclaim.setClaimType(httpServletRequest.getParameter("escapeFlag"));
		}
		String riskType = this.getCodeService().translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		String simpleFlag = httpServletRequest.getParameter("prpLclaimSimpleFlag");
		if ("D".equals(riskType) && DataUtils.emptyToNull(simpleFlag) != null) {// 簡易賠案處理
			prpLclaim.setSimpleFlag(simpleFlag);
		}
		// reason权限判断
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerAction.checkPower(user.convertToPlatFromPrpDuserDto(), "claim.claim.insert");
		String policyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");
		String registNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLRegistRPolicyClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String flowID = httpServletRequest.getParameter("swfLogFlowID");
		Prplregistrpolicy prplregistrpolicy = prpLregistrpolicyService.findPrplregistrpolicy(new PrplregistrpolicyId(registNo, policyNo));
		if (prplregistrpolicy != null) {
			prplregistrpolicy.getId().setRegistNo(prplregistrpolicy.getId().getRegistNo());
			prplregistrpolicy.getId().setPolicyNo(prplregistrpolicy.getId().getPolicyNo());
			prplregistrpolicy.setClaimNo(prpLRegistRPolicyClaimNo);
			prplregistrpolicy.setFlowID(flowID);
			prplregistrpolicy.setPolicyType(prplregistrpolicy.getPolicyType());
			prplregistrpolicy.setRegistFlag(prplregistrpolicy.getRegistFlag());
			prplregistrpolicy.setValidStatus(prplregistrpolicy.getValidStatus());
			prplregistrpolicy.setFlag(prplregistrpolicy.getFlag());
			prplregistrpolicy.setRemark(prplregistrpolicy.getRemark());
		}
		claimDto.setPrplregistrpolicy(prplregistrpolicy);
		/*---------------------标的车,三者车辆prpLthirdParty------------------------------------*/
		List<PrpLthirdParty> thirdPartyList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;
		// 从界面得到输入数组
		String prpLthirdPartyClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLthirdPartyRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		String prpLthirdPartyRegistNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLclaimClauseType");
		String[] prpLthirdPartySerialNo = httpServletRequest.getParameterValues("prpLthirdPartySerialNo");
		String[] prpLthirdPartyLicenseNo = httpServletRequest.getParameterValues("prpLthirdPartyLicenseNo");
		String[] prpLthirdPartyLicenseColorCode = httpServletRequest.getParameterValues("licenseColorCode");
		String[] prpLthirdPartyCarKindCode = httpServletRequest.getParameterValues("carKindCode");
		String[] prpLthirdPartyInsureCarFlag = httpServletRequest.getParameterValues("insureCarFlag");
		// String[] prpLthirdPartyCarOwner =
		// httpServletRequest.getParameterValues("prpLthirdPartyCarOwner");
		String[] prpLthirdPartyEngineNo = httpServletRequest.getParameterValues("prpLthirdPartyEngineNo");
		String[] prpLthirdPartyFrameNo = httpServletRequest.getParameterValues("prpLthirdPartyFrameNo");
		String[] prpLthirdPartyModelCode = httpServletRequest.getParameterValues("prpLthirdPartyModelCode");
		String[] prpLthirdPartyBrandName = httpServletRequest.getParameterValues("prpLthirdPartyBrandName");
		String[] prpLthirdPartyRunDistance = httpServletRequest.getParameterValues("prpLthirdPartyRunDistance");
		String[] prpLthirdPartyUseYears = httpServletRequest.getParameterValues("prpLthirdPartyUseYears");
		String[] prpLthirdPartyDutyPercent = httpServletRequest.getParameterValues("prpLthirdPartyDutyPercent");
		String[] prpLthirdPartyInsuredFlag = httpServletRequest.getParameterValues("insuredFlag");
		String[] prpLthirdPartyInsureComCode = httpServletRequest.getParameterValues("prpLthirdPartyInsureComCode");
		String[] prpLthirdPartyInsureComName = httpServletRequest.getParameterValues("prpLthirdPartyInsureComName");
		String[] prpLthirdPartyVINNo = httpServletRequest.getParameterValues("prpLthirdPartyVINNo");

		String[] prpLthirdPartyGarageHeadName = httpServletRequest.getParameterValues("prpLthirdPartyGarageHeadName");
		String[] prpLthirdPartyRelationship = httpServletRequest.getParameterValues("prpLthirdPartyRelationship");
		String[] prpLthirdPartyDrivingAddress = httpServletRequest.getParameterValues("prpLthirdPartyDrivingAddress");
		String[] prpLthirdPartyCarryingUnit = httpServletRequest.getParameterValues("prpLthirdPartyCarryingUnit");
		String[] prpLthirdPartyInsuranceNo = httpServletRequest.getParameterValues("prpLthirdPartyInsuranceNo");
		String[] prpLthirdPartyIsInsurance = httpServletRequest.getParameterValues("prpLthirdPartyIsInsurance");
		String[] prpLthirdPartyCarryingNumber = httpServletRequest.getParameterValues("prpLthirdPartyCarryingNumber");
		String[] prpLthirdPartyCarsOwners = httpServletRequest.getParameterValues("prpLthirdPartyCarsOwners");
		String[] prpLthirdPartyInsuredIdentity = httpServletRequest.getParameterValues("prpLthirdPartyInsuredIdentity");
		// 对象赋值
		// 三者车辆部分开始
		if (prpLthirdPartySerialNo != null) {
			for (int index = 1; index < prpLthirdPartySerialNo.length; index++) {
				prpLthirdParty = new PrpLthirdParty();
				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartySerialNo[index])));
				prpLthirdParty.setClaimNo(prpLthirdPartyClaimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if ("".equals(prpLthirdPartyLicenseNo[index]) || prpLthirdPartyLicenseNo[index] == null) {
					prpLthirdPartyLicenseNo[index] = " ";
				}
				prpLthirdParty.setLicenseNo(prpLthirdPartyLicenseNo[index]);
				prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode[index]);
				prpLthirdParty.setCarKindCode(prpLthirdPartyCarKindCode[index]);
				prpLthirdParty.setInsureCarFlag(prpLthirdPartyInsureCarFlag[index]);
				prpLthirdParty.setEngineNo(prpLthirdPartyEngineNo[index]);
				prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo[index]);
				prpLthirdParty.setBrandName(prpLthirdPartyBrandName[index]);
				prpLthirdParty.setModelCode(prpLthirdPartyModelCode[index]); // add
				prpLthirdParty.setRunDistance(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyRunDistance[index])));
				prpLthirdParty.setUseYears(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartyUseYears[index])));
				prpLthirdParty.setDutyPercent(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyDutyPercent[index])));
				prpLthirdParty.setInsuredFlag(prpLthirdPartyInsuredFlag[index]);
				prpLthirdParty.setInsureComCode(prpLthirdPartyInsureComCode[index]);
				prpLthirdParty.setInsureComName(prpLthirdPartyInsureComName[index]);
				prpLthirdParty.setVINNo(prpLthirdPartyVINNo[index]);
				prpLthirdParty.setGarageHeadName(prpLthirdPartyGarageHeadName[index]);
				// 由於标的车和三者车的属性现在不完全一样了，故作如下判断
				if (prpLthirdPartyInsureCarFlag[index].equals("1")) {
					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);
					prpLthirdParty.setRelationship(prpLthirdPartyRelationship[index]);
				} else {
					prpLthirdParty.setCarryingUnit(prpLthirdPartyCarryingUnit[index]);
					prpLthirdParty.setInsuranceNo(prpLthirdPartyInsuranceNo[index]);
					prpLthirdParty.setIsInsurance(prpLthirdPartyIsInsurance[index]);
					prpLthirdParty.setCarryingNumber(Long.parseLong(DataUtils.nullToZero(prpLthirdPartyCarryingNumber[index])));
					prpLthirdParty.setInsuredIdentity(prpLthirdPartyInsuredIdentity[index]);
					prpLthirdParty.setCarsOwners(prpLthirdPartyCarsOwners[index]);
					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);
				}
				// 差异化end---------------------------------
				// 加入集合
				thirdPartyList.add(prpLthirdParty);
			}
			// 立案集合中加入三者车辆
			claimDto.setPrpLthirdPartyList(thirdPartyList);
		}
		//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 START
		//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正 START
		//強制證號儲存時去掉最前面的公司碼
		for(int i = 0 ;i<thirdPartyList.size();i++){
			if(i>0 && ((PrpLthirdParty)thirdPartyList.get(i)).getIsInsurance().equals("1")){
				thirdPartyList.get(i).setInsuranceNo(thirdPartyList.get(i).getInsuranceNo().trim().substring(2));
			}
		}
		//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正 END
		//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END
		/*-------------------索赔申请人信息-------start------------------------------------*/
		List<PrpLacciPerson> prpLacciPersonList = new ArrayList<PrpLacciPerson>();
		PrpLacciPerson prpLacciPersonProposer = null;
		// 从界面得到输入数组
		String proposerClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String proposerPolicyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");
		String[] proposerName = httpServletRequest.getParameterValues("proposerName");
		String[] proposerSerialNo = httpServletRequest.getParameterValues("prpLacciPersonSerialNo");
		String[] proposerIdentifyNumber = httpServletRequest.getParameterValues("proposerIdentifyNumber");
		String[] proposerRelation = httpServletRequest.getParameterValues("relationCode");
		String[] proposerPhone = httpServletRequest.getParameterValues("proposerPhone");
		String[] proposerAddress = httpServletRequest.getParameterValues("proposerAddress");
		String prpLacciPersonFamilyNo = httpServletRequest.getParameter("prpLacciPersonFamilyNo");
		int familyNo = 1;// 家庭序号
		if(DataUtils.emptyToNull(prpLacciPersonFamilyNo)!=null){
			familyNo = Integer.parseInt(prpLacciPersonFamilyNo);
		}
		// 对象赋值
		if (proposerSerialNo != null) {
			for (int index = 1; index < proposerSerialNo.length; index++) {
				prpLacciPersonProposer = new PrpLacciPerson();
				prpLacciPersonProposer.setAcciName(proposerName[index]);
				prpLacciPersonProposer.getId().setCertiNo(proposerClaimNo);
				prpLacciPersonProposer.getId().setCertiType("03");
				prpLacciPersonProposer.setPolicyNo(proposerPolicyNo);
				prpLacciPersonProposer.setFlag("1"); // 标志是索赔人
				prpLacciPersonProposer.setIdentifyNumber(proposerIdentifyNumber[index]);
				prpLacciPersonProposer.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(proposerSerialNo[index])));
				prpLacciPersonProposer.setAddress(proposerAddress[index]);
				prpLacciPersonProposer.setPhone(proposerPhone[index]);
				prpLacciPersonProposer.setRelationCode(proposerRelation[index]);
				String relationName = "";
				if (proposerRelation[index].equals("1")) {
					relationName = "被保險人本人";
				} else if (proposerRelation[index].equals("2")) {
					relationName = "指定受益人";
				} else if (proposerRelation[index].equals("3")) {
					relationName = "被保險人之繼承人";
				} else if (proposerRelation[index].equals("4")) {
					relationName = "被保險人之監護人";
				}
				prpLacciPersonProposer.setRelationName(relationName);
				prpLacciPersonProposer.setFamilyNo(familyNo);// 家庭序号
				// 加入集合
				prpLacciPersonList.add(prpLacciPersonProposer);
			}
			// 意健险立案集合中加入索赔申请人
			claimDto.setPrpLacciPersonList(prpLacciPersonList);
		}

		/*-------------------驾驶员信息-------start------------------------------------*/
		List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = null;
		// 从界面得到输入数组
		String prpLdriverRegistNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLdriverClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLdriverRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		String prpLdriverPolicyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");
		String[] prpLdriverSerialNo = httpServletRequest.getParameterValues("prpLdriverSerialNo");
		String[] prpLdriverDriverName = httpServletRequest.getParameterValues("prpLdriverDriverName");
		String[] prpLdriverDriverPhone = httpServletRequest.getParameterValues("prpLdriverDriverPhone");
		String[] prpLdriverLicenseNo = httpServletRequest.getParameterValues("prpLdriverLicenseNo");
		String[] prpLdriverDrivingLicenseNo = httpServletRequest.getParameterValues("prpLdriverDrivingLicenseNo");
		String[] prpLdriverDriverSex = httpServletRequest.getParameterValues("driverSex");
		String[] prpLprpLdriverApanageCode = httpServletRequest.getParameterValues("prpLdriverApanageCode");
		String[] prpLprpLdriverApanage = httpServletRequest.getParameterValues("prpLdriverApanage");
		String[] prpLdriverDrivingCarType = httpServletRequest.getParameterValues("drivingCarType");
		// 差异化begin-------------add by liuwei-----------------
		String[] prpLdriverIsMarried = httpServletRequest.getParameterValues("prpLdriverIsMarried");
		String[] prpLdriverBirthday = httpServletRequest.getParameterValues("prpLdriverBirthday");
		String[] prpLdriverIdentifyNumber = httpServletRequest.getParameterValues("prpLdriverIdentifyNumber");
		String[] prpLdriverMobilePhone = httpServletRequest.getParameterValues("prpLdriverMobilePhone");
		String[] prpLdriverDriverIdentity = httpServletRequest.getParameterValues("prpLdriverDriverIdentity");
		String[] prpLdriverDriverDistrict = httpServletRequest.getParameterValues("prpLdriverDriverDistrict");
		// 差异化end--------------------------------------
		// 对象赋值
		if (prpLdriverSerialNo != null) {
			// 三者车辆部分开始
			for (int index = 1; index < prpLdriverSerialNo.length; index++) {
				prpLdriver = new PrpLdriver();
				prpLdriver.getId().setRegistNo(prpLdriverRegistNo);
				prpLdriver.setClaimNo(prpLdriverClaimNo);
				prpLdriver.setRiskCode(prpLdriverRiskCode);
				prpLdriver.setPolicyNo(prpLdriverPolicyNo);
				prpLdriver.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLdriverSerialNo[index])));
				prpLdriver.setDriverName(prpLdriverDriverName[index]);
				prpLdriver.setDriverSeaRoute(prpLdriverDriverPhone[index]);
				prpLdriver.setLicenseNo(prpLdriverLicenseNo[index]);
				prpLdriver.setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);
				prpLdriver.setDriverApanageCode(prpLprpLdriverApanageCode[index]);
				prpLdriver.setDriverApanage(prpLprpLdriverApanage[index]);
				prpLdriver.setDrivingCarType(prpLdriverDrivingCarType[index]);
				prpLdriver.setDriverSex(prpLdriverDriverSex[index]);
				// 差异化begin--------------add by liuwei-----------
				prpLdriver.setIsMarried(prpLdriverIsMarried[index]);
				prpLdriver.setBirthday(CommonUtils.toYearToDayDate(prpLdriverBirthday[index]));
				prpLdriver.setIdentifyNumber(prpLdriverIdentifyNumber[index]); 
				prpLdriver.setMobilePhone(prpLdriverMobilePhone[index]);
				prpLdriver.setDriverIdentity(prpLdriverDriverIdentity[index]);
				prpLdriver.setDriverDistrict(prpLdriverDriverDistrict[index]);
				// 差异化end--------------------
				// 加入集合
				driverList.add(prpLdriver);
			}
			// 立案集合中加入驾驭员
			claimDto.setPrpLdriverList(driverList);
		}
		// /*---------------------估损金额PrpLclaimfee------------------------------------*/
		List<PrpLclaimFee> claimFeeList = new ArrayList<PrpLclaimFee>();
		PrpLclaimFee prpLclaimFee = null;
		// 从界面得到输入数组
		String prpLclaimFeeClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLclaimFeeRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		String[] prpLclaimFeeCurrency = httpServletRequest.getParameterValues("prpLclaimFeeCurrency");
		// String[] prpLclaimFeeCurrencyName =
		// httpServletRequest.getParameterValues("prpLclaimFeeCurrencyName");
		String[] prpLclaimFeeSumClaim = httpServletRequest.getParameterValues("prpLclaimFeeSumClaim");
		String[] prpLclaimFeeFlag = httpServletRequest.getParameterValues("prpLclaimFeeFlag");
		// 对象赋值
		// -估损金额部分开始
		if (prpLclaimFeeCurrency != null) {
			for (int index = 1; index < prpLclaimFeeCurrency.length; index++) {
				prpLclaimFee = new PrpLclaimFee();
				prpLclaimFee.getId().setClaimNo(prpLclaimFeeClaimNo);
				prpLclaimFee.setRiskCode(prpLclaimFeeRiskCode);
				prpLclaimFee.getId().setCurrency(prpLclaimFeeCurrency[index]);
				// prpLclaimFee.setCurrencyName(prpLclaimFeeCurrencyName[index]);
				prpLclaimFee.setSumClaim(Double.parseDouble(prpLclaimFeeSumClaim[index]));
				prpLclaimFee.setFlag(prpLclaimFeeFlag[index]);
				// 加入集合
				claimFeeList.add(prpLclaimFee);
			}
			claimDto.setPrpLclaimFeeList(claimFeeList);
		}
		// /*---------------------险别估损金额PrpLclaimloss------------------------------------*/
		List<PrpLclaimLoss> claimLossList = new ArrayList<PrpLclaimLoss>();
		PrpLclaimLoss prpLclaimLoss = null;
		// 从界面得到输入数组
		String prpLclaimLossClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLclaimLossRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		// String[] prpLclaimLossSerialNo =
		// httpServletRequest.getParameterValues("prpLclaimLossSerialNo");
		// 理赔拆分危险单位
		String[] prpLclaimLossDangerNo = httpServletRequest.getParameterValues("prpLclaimLossDangerNo");
		String[] prpLclaimLossItemKindNo = httpServletRequest.getParameterValues("prpLclaimLossItemKindNo");
		String[] prpLclaimLossFeeCategory = httpServletRequest.getParameterValues("prpLclaimLossFeeCategory");
		String[] prpLclaimLossKindCode = httpServletRequest.getParameterValues("prpLclaimLossKindCode");
		String[] prpLclaimLossItemCode = httpServletRequest.getParameterValues("prpLclaimLossItemCode");
		String[] prpLclaimLossItemDetailName = httpServletRequest.getParameterValues("prpLclaimLossItemDetailName");
		String[] prpLclaimLossCurrency = httpServletRequest.getParameterValues("prpLclaimLossCurrency");
		String[] prpLclaimLossSumClaim = httpServletRequest.getParameterValues("prpLclaimLossSumClaim");
		// String[] prpLclaimLossInputDate =
		// httpServletRequest.getParameterValues("prpLclaimLossInputDate");
		String[] prpLclaimLossRemarkFlag = httpServletRequest.getParameterValues("prpLclaimLossRemarkFlag");
		String[] prpLclaimLossFlag = httpServletRequest.getParameterValues("prpLclaimLossFlag");
		String[] prpLclaimLossKindLoss = httpServletRequest.getParameterValues("prpLclaimLossKindLoss");
		// 加入残值
		String[] prpLclaimLossKindRest = httpServletRequest.getParameterValues("prpLclaimLossKindRest");
		// 责任免赔额
		String[] prpLclaimLossAcciDeductiblePay = httpServletRequest.getParameterValues("prpLclaimLossAcciDeductiblePay");
		// 责任免赔率
		String[] prpLclaimLossAcciDeductibleRate = httpServletRequest.getParameterValues("prpLclaimLossAcciDeductibleRate");
		// 责任免赔额
		String[] prpLclaimLossDeductible = httpServletRequest.getParameterValues("prpLclaimLossDeductible");
		// 车险不计免赔额特约(M)对应的险别
		String[] exceptDeductibleKindCode = httpServletRequest.getParameterValues("exceptDeductibleKindCode");
		// 不计免赔额
		String[] exceptDeductiblePay = httpServletRequest.getParameterValues("exceptDeductiblePay");
		// 不计免赔率
		String[] exceptDeductibleRate = httpServletRequest.getParameterValues("exceptDeductibleRate");
		String[] prpLclaimLossAmount = httpServletRequest.getParameterValues("prpLclaimLossAmount");
		String[] prpLclaimLossAccidentType = httpServletRequest.getParameterValues("prpLclaimLossAccidentType");
		
		// 立案估损金额表中存入的itemkindNo
		// 应为prpcitemKind的险别序号---[1]--------------------
		if(prpLclaimLossItemKindNo == null ){
			prpLclaimLossItemKindNo = new String[prpLclaimLossKindCode.length];
		}
		if(prpLclaimLossAmount==null){
			prpLclaimLossAmount = new String[prpLclaimLossKindCode.length];
		}
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		List<PrpCitemKind> prpCitemKindList = null;
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
			httpServletRequest.setAttribute("prpCitemKindForE", prpCitemKindList);
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
		Map<String, PrpCitemKind> itemKindMap = new HashMap<String, PrpCitemKind>();// 險別序號映射
		Map<String, PrpCitemKind> virtualKindMap = new HashMap<String, PrpCitemKind>();
		// mantis： CLM0117 ，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -start
		PrpCitemKind tempPrpCitemKind = null;
		for (PrpCitemKind p : prpCitemKindList) {
			tempPrpCitemKind = new PrpCitemKind();
			PropertyUtils.copyProperties(tempPrpCitemKind, p);
			//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題 START
			if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
				tempPrpCitemKind.setItemCode(tempPrpCitemKind.getItemDetailName());
			}
			//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題 END
			itemKindNoMap.put(String.valueOf(tempPrpCitemKind.getId().getItemKindNo()), tempPrpCitemKind);
			itemKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);
			if (ConstantCodes.CLASSCODE_Z.equals(riskType)||ConstantCodes.CLASSCODE_G.equals(riskType)||ConstantCodes.CLASSCODE_Q.equals(riskType)) {
				// 虛擬標的情況處理
				List<PrpCitemKind> virtualKindList = prpCitemKindService.generateVirtualKind(tempPrpCitemKind);
				if (!CommonUtils.isEmpty(virtualKindList)) {
					virtualKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					double sumAmount = 0d;
					for (PrpCitemKind sp : virtualKindList) {
						virtualKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						sumAmount += sp.getAmount();
						tempPrpCitemKind.setAmount(sumAmount);
					}
				}
			}
		}
		// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -end
		String prpLclaimLossPolicyNo = prpLclaim.getPolicyNo();
		// mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
		if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
			logger.info("CLM0133 +++ prpLclaimLossPolicyNo: " + prpLclaimLossPolicyNo);
			logger.info("CLM0133 --- virtualKindMap: " + virtualKindMap.keySet());
			logger.info("CLM0133 --- itemKindMap: " + itemKindMap.keySet());
			logger.info("CLM0133 --- itemKindNoMap: " + itemKindNoMap.keySet());
		}

//		PrpCitemKind tempPrpCitemKind = null;
		for (int m = 0; m < prpLclaimLossKindCode.length; m++) {
			String kindCode = prpLclaimLossKindCode[m];
			// mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
			if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
				logger.info("CLM0133 +++ kindCode: " + kindCode);
			}
			if (!CommonUtils.isEmpty(kindCode)) {
				String intemKindNo = CommonUtils.getValue(prpLclaimLossItemKindNo, m);
				String itemCode = CommonUtils.getValue(prpLclaimLossItemCode, m);
				// mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
				if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
					logger.info("CLM0133 +++ intemKindNo: " + intemKindNo + ", itemCode: " + itemCode);
				}
				if (virtualKindMap.containsKey(kindCode)) {//虛擬標的
					if(CommonUtils.isEmpty(intemKindNo)){
						if (CommonUtils.isEmpty(itemCode)) {
						    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
							if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
								logger.info("CLM0133 1 >>> key: " + kindCode);
							}
							tempPrpCitemKind = virtualKindMap.get(kindCode);
						} else {
						    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
							if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
								logger.info("CLM0133 2 >>> key: " + kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
							}
							tempPrpCitemKind = virtualKindMap.get(kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
						}
					} else {
						if (CommonUtils.isEmpty(itemCode)) {
						    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
							if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
								logger.info("CLM0133 3 >>> key: " + intemKindNo + "_" + kindCode);
							}
							tempPrpCitemKind = virtualKindMap.get(intemKindNo + "_" + kindCode);
						} else {
						    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
							if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
								logger.info("CLM0133 4 >>> key: " + intemKindNo + "_" + kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
							}
							tempPrpCitemKind = virtualKindMap.get(intemKindNo + "_" + kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
						}
					}
				} else {
					if (CommonUtils.isEmpty(intemKindNo)) {
					    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
						if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
							logger.info("CLM0133 5 >>> key: " + kindCode);
						}
						tempPrpCitemKind = itemKindMap.get(kindCode);
					} else {
					    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
						if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
							logger.info("CLM0133 6 >>> key: " + intemKindNo);
						}
						tempPrpCitemKind = itemKindNoMap.get(intemKindNo);
						if (tempPrpCitemKind == null || !tempPrpCitemKind.getKindCode().equals(kindCode)) {
						    // mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
							if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
								logger.info("CLM0133 7 >>> key: " + kindCode);
							}
							tempPrpCitemKind = itemKindMap.get(kindCode);
						}
					}
				}
				// mantis：CLM0133，處理人員：DP0714，新核心-藝術品AR立案NullException
				if(ConstantCodes.RISKCODE_AR.equals(prpLclaimLossRiskCode)){
					if (tempPrpCitemKind==null) {
						logger.info("CLM0133 +++ tempPrpCitemKind is null");
					}
				}
				
//				//mantis：CLM0141，處理人員：DP0713，需求單編號：理賠拖吊險案件立案失敗問題查詢 START
//				prpLclaimLossItemKindNo[m] = null!=tempPrpCitemKind?tempPrpCitemKind.getId().getItemKindNo() + "" :"";
//				prpLclaimLossAmount[m] = null!=tempPrpCitemKind?String.valueOf(tempPrpCitemKind.getAmount()):"0";
//				//mantis：CLM0141，處理人員：DP0713，需求單編號：理賠拖吊險案件立案失敗問題查詢 END
			}
		}
		// 原因：添加标志字段，用於区别赔款和费用。
		String[] prpLregsitLossFeeType = httpServletRequest.getParameterValues("prpLclaimLossLossFeeType");
		// 对象赋值
		// -估损金额部分开始
		if (prpLclaimLossCurrency != null) {
			for (int index = 1; index < prpLclaimLossCurrency.length; index++) {
				prpLclaimLoss = new PrpLclaimLoss();
				prpLclaimLoss.getId().setClaimNo(prpLclaimLossClaimNo);
				prpLclaimLoss.setRiskCode(prpLclaimLossRiskCode);
				prpLclaimLoss.getId().setSerialNo(index);
				prpLclaimLoss.setRegistNo(registNo);
				prpLclaimLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(prpLclaimLossItemKindNo[index])));
				prpLclaimLoss.setKindCode(prpLclaimLossKindCode[index]);
				// 理赔拆分危险单位
				prpLclaimLoss.setDangerNo(Integer.parseInt(prpLclaimLossDangerNo[index]));
				prpLclaimLoss.setFeeCategory(prpLclaimLossFeeCategory[index]);
				prpLclaimLoss.setKindRest(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossKindRest[index])));
				if (prpLclaimLossItemCode != null && prpLclaimLossItemCode.length > index) {
					prpLclaimLoss.setItemCode(prpLclaimLossItemCode[index]);
				}
				if (prpLclaimLossItemDetailName != null && prpLclaimLossItemDetailName.length > index) {
					prpLclaimLoss.setItemDetailName(prpLclaimLossItemDetailName[index]);
				}
				prpLclaimLoss.setCurrency(prpLclaimLossCurrency[index]);
				prpLclaimLoss.setSumClaim(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossSumClaim[index])));
				// //非车没有上报估损这一项------------------------------------------------
				if (prpLclaimLossKindLoss != null && prpLclaimLossKindLoss.length > 0) { // 非车没有上报估损这一项
					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossKindLoss[index])));
				} else {
					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossSumClaim[index])));
				}
				// //非车没有上报估损这一项----------------------------------------
				// reason:修改估损金额的调整
				if ("4".equals(buttonSaveType)) {
					prpLclaimLoss.setInputDate(new DateTime(new Date()));
				} else {
					// inputdate不能为空，但又不能参与准备金增量统计
					prpLclaimLoss.setInputDate(new DateTime(new Date(8099, 11, 31)));
				}
				prpLclaimLoss.setRemarkFlag(prpLclaimLossRemarkFlag[index]);
				if (prpLclaimLossFlag != null) {
					prpLclaimLoss.setFlag(prpLclaimLossFlag[index]);
				}
				if (prpLregsitLossFeeType != null) {
					prpLclaimLoss.setLossFeeType(prpLregsitLossFeeType[index]);
				}

				if (prpLclaimLossAcciDeductiblePay != null && prpLclaimLossAcciDeductiblePay.length > 0) {
					prpLclaimLoss.setAcciDeductiblePay(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossAcciDeductiblePay[index])));
				}

				if (prpLclaimLossAcciDeductibleRate != null && prpLclaimLossAcciDeductibleRate.length > 0) {
					prpLclaimLoss.setAcciDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossAcciDeductibleRate[index])));
				}
				prpLclaimLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossDeductible,index))));
				prpLclaimLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossAmount,index))));
				prpLclaimLoss.setHandlerCode(prpLclaim.getHandlerCode());
//				prpLclaimLoss.setAccidentType(CommonUtils.getValue(prpLclaimLossAccidentType,index));// delete by chenjie 20150601 需求變更-095 
				// 加入集合
				claimLossList.add(prpLclaimLoss);
			}

			if (exceptDeductibleKindCode != null) {
				PrpCitemKind prpCitemKind = null;
				for (int index = 1; index < exceptDeductibleKindCode.length; index++) {
					prpLclaimLoss = new PrpLclaimLoss();
					prpLclaimLoss.getId().setClaimNo(prpLclaimLossClaimNo);
					prpLclaimLoss.setRiskCode(prpLclaimLossRiskCode);
					prpLclaimLoss.getId().setSerialNo(index - 1 + prpLclaimLossCurrency.length);
					for (Iterator<PrpCitemKind> iterator = prpCitemKindList.iterator(); iterator.hasNext();) {
						prpCitemKind = iterator.next();
						if ("M".equals(prpCitemKind.getKindCode().trim())) {
							prpLclaimLoss.setItemKindNo(prpCitemKind.getId().getItemKindNo());
							prpLclaimLoss.setAmount(prpCitemKind.getAmount());
							break;
						}
					}
					prpLclaimLoss.setKindCode("M");
					prpLclaimLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
					prpLclaimLoss.setSumClaim(Double.parseDouble(DataUtils.nullToZero(exceptDeductiblePay[index])));
					if ("4".equals(buttonSaveType)) {
						prpLclaimLoss.setInputDate(new DateTime(new Date()));
					} else {
						// inputdate不能为空，但又不能参与准备金增量统计
						prpLclaimLoss.setInputDate(new DateTime(new Date(8099, 11, 31)));
					}
					prpLclaimLoss.setDangerNo(1);
					prpLclaimLoss.setKindLoss(Double.parseDouble(DataUtils.nullToZero(exceptDeductiblePay[index])));
					prpLclaimLoss.setAcciDeductibleRate(Double.parseDouble(DataUtils.nullToZero(exceptDeductibleRate[index])));
					prpLclaimLoss.setKindCodeSub(exceptDeductibleKindCode[index]);
					prpLclaimLoss.setLossFeeType("P");
					prpLclaimLoss.setFeeCategory("C");
					// 为了送再保数据的准确性
					for (int j = 0; j < prpLclaimLossKindCode.length; j++) {
						if (prpLclaimLoss.getKindCodeSub().equals(prpLclaimLossKindCode[j])) {
							prpLclaimLoss.setDangerNo(Integer.parseInt(prpLclaimLossDangerNo[j]));
							break;
						}
					}
					// 加入集合
					claimLossList.add(prpLclaimLoss);
				}
			}
			claimDto.setPrpLclaimLossList(claimLossList);
		}
		// /*---------------------索赔单证PrpLclaimDoc------------------------------------*/
		List<PrpLdoc> docList = new ArrayList<PrpLdoc>();
		PrpLdoc prpLdoc = null;
		// 从界面得到输入数组
		String prpLdocClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String[] prpLdocDocCode = httpServletRequest.getParameterValues("prpLdocDocCode");
		String[] prpLdocDocName = httpServletRequest.getParameterValues("prpLdocDocName");
		String[] prpLdocDocCount = httpServletRequest.getParameterValues("prpLdocDocCount");
		String[] prpLdocSignInDate = httpServletRequest.getParameterValues("prpLdocSignInDate");
		String[] prpLdocFlag = httpServletRequest.getParameterValues("prpLdocFlag");
		// 对象赋值
		// -索赔单证部分开始
		if (prpLdocDocCode != null) {
			for (int index = 1; index < prpLdocDocCode.length; index++) {
				prpLdoc = new PrpLdoc();
				prpLdoc.getId().setClaimNo(prpLdocClaimNo);
				prpLdoc.getId().setDocCode(prpLdocDocCode[index]);
				prpLdoc.setDocName(prpLdocDocName[index]);
				prpLdoc.setDocCount(Integer.parseInt(prpLdocDocCount[index]));
				prpLdoc.setSignInDate(new DateTime(prpLdocSignInDate[index]));
				prpLdoc.setFlag(prpLdocFlag[index]);
				// 加入集合
				docList.add(prpLdoc);
			}
			// 立案集合中加入索赔单证
			claimDto.setPrpLdocList(docList);
		}
		// Reason:损失部位模块加入到涉案车辆信息中後，相应模块做调整
		/*---------------------损失部位 PrpLthirdCarLoss 开始------------------------------------*/
		List<PrpLthirdCarLoss> thirdCarLossList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLthirdCarLossRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		String[] prpLthirdCarLossSerialNo = httpServletRequest.getParameterValues("RelateSerialNo");
		String[] prpLthirdCarLossItemNo = httpServletRequest.getParameterValues("prpLthirdCarLossItemNo");
		String[] prpLthirdCarLossLicenseNo = httpServletRequest.getParameterValues("prpLthirdCarLossLicenseNo");
		String[] prpLthirdCarLossPartCode = httpServletRequest.getParameterValues("partCode"); // 部件代码
		String[] prpLthirdCarLossPartName = httpServletRequest.getParameterValues("partName"); // 部件名称
		String[] prpLthirdCarLossCompCode = httpServletRequest.getParameterValues("compCode");// 零件代码
		String[] prpLthirdCarLossCompName = httpServletRequest.getParameterValues("compName");// 零件名称
		String[] prpLthirdCarLossLossGrade = httpServletRequest.getParameterValues("prpLthirdCarLossLossGrade");
		String[] prpLthirdCarLossLossDesc = httpServletRequest.getParameterValues("prpLthirdCarLossLossDesc");
		String[] prpLthirdCarLossFlag = httpServletRequest.getParameterValues("prpLthirdCarLossFlag");
		String[] prpLthirdCarLossKindCode = httpServletRequest.getParameterValues("prpLthirdCarLossKindCode");// 险别代码
		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdCarLossSerialNo != null) {
			for (int index = 1; index < prpLthirdCarLossSerialNo.length; index++) {
				prpLthirdCarLoss = new PrpLthirdCarLoss();
				prpLthirdCarLoss.getId().setRegistNo(prpLthirdCarLossRegistNo);
				prpLthirdCarLoss.setRiskCode(prpLthirdCarLossRiskCode);
				prpLthirdCarLoss.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdCarLossSerialNo[index])));
				prpLthirdCarLoss.getId().setItemNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdCarLossItemNo[index])));
				prpLthirdCarLoss.setLicenseNo(prpLthirdCarLossLicenseNo[index]);
				prpLthirdCarLoss.setCompCode(prpLthirdCarLossCompCode[index]);
				prpLthirdCarLoss.setCompName(prpLthirdCarLossCompName[index]);
				prpLthirdCarLoss.setLossGrade(prpLthirdCarLossLossGrade[index]);
				prpLthirdCarLoss.setLossDesc(prpLthirdCarLossLossDesc[index]);
				prpLthirdCarLoss.setFlag(prpLthirdCarLossFlag[index]);
				prpLthirdCarLoss.setPartCode(prpLthirdCarLossPartCode[index]);
				prpLthirdCarLoss.setPartName(prpLthirdCarLossPartName[index]);
				prpLthirdCarLoss.setKindCode(CommonUtils.getValue(prpLthirdCarLossKindCode,index));
				// 加入集合
				thirdCarLossList.add(prpLthirdCarLoss);
			}
			// 立案集合中加入损失部位
			claimDto.setPrpLthirdCarLossList(thirdCarLossList);
		}
		/*---------------------损失部位 PrpLthirdCarLoss 结束------------------------------------*/
		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp begin------------------------------------*/
		List<PrpLthirdProp> thirdPropList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;
		// 从界面得到输入数组
		String prpLthirdPropRegistNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLthirdPropRiskCode = httpServletRequest.getParameter("prpLclaimRiskCode");
		String[] prpLthirdPropItemNo = httpServletRequest.getParameterValues("prpLthirdPropItemNo");
		String[] prpLthirdPropLicenseNo = httpServletRequest.getParameterValues("prpLthirdPropLicenseNo");
		String[] lossItemCode = httpServletRequest.getParameterValues("prpLthirdLossItemCode");
		String[] LossItemName = httpServletRequest.getParameterValues("prpLthirdLossItemName");
		String[] prpLthirdPropLossDesc = httpServletRequest.getParameterValues("prpLthirdPropLossDesc");
		String[] prpLthirdPropFlag = httpServletRequest.getParameterValues("prpLthirdPropFlag");
		String[] prpLthirdPropGoodsCarLicenseNo = httpServletRequest.getParameterValues("prpLthirdPropGoodsCarLicenseNo");
		
		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdPropItemNo != null) {
			for (int index = 1; index < prpLthirdPropItemNo.length; index++) {
				prpLthirdProp = new PrpLthirdProp();
				prpLthirdProp.getId().setRegistNo(prpLthirdPropRegistNo);
				prpLthirdProp.setRiskCode(prpLthirdPropRiskCode);
				prpLthirdProp.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPropItemNo[index])));
				prpLthirdProp.setLicenseNo(prpLthirdPropLicenseNo[index]);
				prpLthirdProp.setLossItemCode(lossItemCode[index]);
				prpLthirdProp.setLossItemName(LossItemName[index]);
				prpLthirdProp.setLossItemDesc(prpLthirdPropLossDesc[index]);
				prpLthirdProp.setFlag(prpLthirdPropFlag[index]);
				prpLthirdProp.setGoodsCarLicenseNo(CommonUtils.getValue(prpLthirdPropGoodsCarLicenseNo, index));
				// 加入集合
				thirdPropList.add(prpLthirdProp);
			}
			// 报案集合中加入损失部位
			claimDto.setPrpLthirdPropList(thirdPropList);
		}
		/*---------------------其它损失部位 PrpLthirdProp end------------------------------------*/
		/*---------------------增加理赔联系记录 PrpLregistExt------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRegistNo = (String) httpServletRequest.getParameter("prpLregistExtRegistNo");
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 理赔联系记录
		if (prpLregistExtSerialNo != null) {
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(prpLregistExtRegistNo);
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 立案集合中加入理赔联系记录
			claimDto.setPrpLregistExtList(prpLregistExtList);
		}
		/*---------------------添加人员伤亡跟踪 PrpLpersonTrace------------------------------------*/
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
		String[] PrpLpersonTraceIdNumber = httpServletRequest.getParameterValues("prpLpersonTraceIdNumber");
		String[] PrpLpersonTraceRideSituation = httpServletRequest.getParameterValues("rideSituation");
		String[] PrpLpersonTraceLicenseno = httpServletRequest.getParameterValues("prpLpersonTraceLicenseno");
		
		String[] PrpLpersonTraceIdNumberType  = httpServletRequest.getParameterValues("prpLpersonTraceIdNumberType");
		String[] PrpLpersonTraceApplicantBirthday  = httpServletRequest.getParameterValues("prpLpersonTraceApplicantBirthday");
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
		List<PrpLpersonTrace> personTraceList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
		String prpLpersonTraceRegistNo = (String) httpServletRequest.getParameter("prpLclaimRegistNo");
		String prpLpersonTraceClaimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLpersonTracePolicyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");
		String[] prpLpersonTracePersonNo = httpServletRequest.getParameterValues("prpLpersonTracePersonNo");
		String[] prpLpersonTracePersonName = httpServletRequest.getParameterValues("prpLpersonTracePersonName");
		String[] prpLpersonTracePersonSex = httpServletRequest.getParameterValues("personSex");
		String[] prpLpersonTracePersonAge = httpServletRequest.getParameterValues("prpLpersonTracePersonAge");
		String[] prpLpersonTraceIdentifyNumber = httpServletRequest.getParameterValues("prpLpersonTraceIdentifyNumber");
		String[] prpLpersonTraceRelatePersonNo = httpServletRequest.getParameterValues("prpLpersonTraceRelatePersonNo");
		String[] prpLpersonTraceJobCode = httpServletRequest.getParameterValues("prpLpersonTraceJobCode");
		String[] prpLpersonTraceJobName = httpServletRequest.getParameterValues("prpLpersonTraceJobName");
		String[] prpLpersonTraceReferKind = httpServletRequest.getParameterValues("prpLpersonTraceReferKind");
		String[] prpLpersonTracePartDesc = httpServletRequest.getParameterValues("prpLpersonTracePartDesc");
		String[] prpLpersonTraceHospital = httpServletRequest.getParameterValues("prpLpersonTraceHospital");
		String[] prpLpersonTraceHospitalCode = httpServletRequest.getParameterValues("prpLpersonTraceHospitalCode");
		
		String[] prpLpersonTraceMotionFlag = httpServletRequest.getParameterValues("motionFlag");
		String[] prpLpersonTraceWoundRemark = httpServletRequest.getParameterValues("prpLpersonTraceWoundRemark");
		String[] prpLpersonTraceRemark = httpServletRequest.getParameterValues("prpLpersonTraceRemark");
		String[] prpLpersonTraceFlag = httpServletRequest.getParameterValues("prpLpersonTraceFlag");
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLpersonTracePersonNo != null) {
			for (int index = 1; index < prpLpersonTracePersonNo.length; index++) {
				prpLpersonTrace = new PrpLpersonTrace();
				prpLpersonTrace.getId().setRegistNo(prpLpersonTraceRegistNo);
				prpLpersonTrace.setClaimNo(prpLpersonTraceClaimNo);
				prpLpersonTrace.setPolicyNo(prpLpersonTracePolicyNo);
				prpLpersonTrace.getId().setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonNo[index])));
				prpLpersonTrace.setPersonName(prpLpersonTracePersonName[index]);
				prpLpersonTrace.setPersonSex(prpLpersonTracePersonSex[index]);
				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));
				prpLpersonTrace.setIdentifyNumber(prpLpersonTraceIdentifyNumber[index]);
				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				// prpLpersonTrace.setReferKind (prpLpersonTraceReferKind
				// [index] );
				if (prpLpersonTraceReferKind == null) {
					prpLpersonTrace.setReferKind("");
				} else {
					prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				}
				// prpLpersonTrace.setReferKind ("B");
				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);
				prpLpersonTrace.setHospital(CommonUtils.getValue(prpLpersonTraceHospital, index));
				prpLpersonTrace.setHospitalCode(CommonUtils.getValue(prpLpersonTraceHospitalCode, index));
				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);
				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);
				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);

				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
				prpLpersonTrace.setIdNumber(PrpLpersonTraceIdNumber[index]);
				prpLpersonTrace.setRideSituation(PrpLpersonTraceRideSituation[index]);
				prpLpersonTrace.setLicenseno(PrpLpersonTraceLicenseno[index]);
				
				prpLpersonTrace.setIdNumberType(PrpLpersonTraceIdNumberType[index]);
				prpLpersonTrace.setApplicantBirthday(CommonUtils.toYearToDayDate(PrpLpersonTraceApplicantBirthday[index]));
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
				// 加入集合
				personTraceList.add(prpLpersonTrace);
			}
			// 报案集合中加入损失部位
			claimDto.setPrpLpersonTraceList(personTraceList);
		}
		// String strCoinsFlag = prpCmain.getCoinsFlag();
		// ArrayList dangerUnitList = new ArrayList();
		// 对於货运险增加信息处理
		String claimNo = (String) httpServletRequest.getAttribute("claimNo");
		String prpLextSalvor = (String) httpServletRequest.getParameter("prpLextSalvor");
		DateTime uploadDate = new DateTime(httpServletRequest.getParameter("prpLextUnloadDate"), DateTime.YEAR_TO_DAY);
		// if(prpLclaimLossRiskCode.substring(0,2).equals("09")||prpLclaimLossRiskCode.substring(0,2).equals("10")){
		String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLclaimLossRiskCode);
		if ("Y".equals(strRiskType)) {
			PrpLext prplext = new PrpLext();
			prplext.getId().setCertiNo(claimNo);
			prplext.getId().setCertiType("03");
			prplext.setUnloadDate(uploadDate);
			prplext.setSalvor(prpLextSalvor);
			claimDto.setPrpLext(prplext);
		}
		//信用卡信息
		String prpLclaimCreditBusinessNo           =  (String) httpServletRequest.getAttribute("claimNo");
		String prpLclaimCreditNodeType             =  httpServletRequest.getParameter("prpLclaimCreditNodeType");
//		String prpLclaimCreditSerialNo             =  httpServletRequest.getParameter("prpLclaimCreditSerialNo");
		String prpLclaimCreditPolicyNo             =  httpServletRequest.getParameter("prpLclaimCreditPolicyNo");
		String prpLclaimCreditRiskCode             =  httpServletRequest.getParameter("prpLclaimCreditRiskCode");
		String prpLclaimCreditBankCode             =  httpServletRequest.getParameter("prpLclaimCreditBankCode");
//		String prpLclaimCreditBankName             =  httpServletRequest.getParameter("prpLclaimCreditBankName");
		String prpLclaimCreditCardType             =  httpServletRequest.getParameter("prpLclaimCreditCardType");
		String prpLclaimCreditCardOtherType        =  httpServletRequest.getParameter("prpLclaimCreditCardOtherType");
		String prpLclaimCreditCardName             =  httpServletRequest.getParameter("prpLclaimCreditCardName");
		String prpLclaimCreditCardNo               =  httpServletRequest.getParameter("prpLclaimCreditCardNo");
		String prpLclaimCreditValidDateYear        =  httpServletRequest.getParameter("prpLclaimCreditValidDateYear");
		String prpLclaimCreditValidDateMonth       =  httpServletRequest.getParameter("prpLclaimCreditValidDateMonth");
		String prpLclaimCreditHolderName           =  httpServletRequest.getParameter("prpLclaimCreditHolderName");
		String prpLclaimCreditHolderIdentifyNumber =  httpServletRequest.getParameter("prpLclaimCreditHolderIdentifyNumber");
		String prpLclaimCreditHolderTel            =  httpServletRequest.getParameter("prpLclaimCreditHolderTel");
		String prpLclaimCreditHolderPhone          =  httpServletRequest.getParameter("prpLclaimCreditHolderPhone");
		String prpLclaimCreditHolderRelationShip   =  httpServletRequest.getParameter("prpLclaimCreditHolderRelationShip");
		String prpLclaimCreditHolderAddress        =  httpServletRequest.getParameter("prpLclaimCreditHolderAddress");
//		String prpLclaimCreditCurrency             =  httpServletRequest.getParameter("prpLclaimCreditCurrency");
		String prpLclaimCreditUseArea              =  httpServletRequest.getParameter("prpLclaimCreditUseArea");
		String prpLclaimCreditRemark               =  httpServletRequest.getParameter("prpLclaimCreditRemark");
		String prpLclaimCreditFlag                 =  httpServletRequest.getParameter("prpLclaimCreditFlag");
		String prpLclaimCreditCardCode             =  httpServletRequest.getParameter("prpLclaimCreditCardCode");
		String prpLclaimCreditValidDate             =  httpServletRequest.getParameter("prpLclaimCreditValidDate");
		if(!CommonUtils.isEmpty(prpLclaimCreditValidDate)){
			Pattern p = Pattern.compile("(\\d+)年(\\d+)月");
			Matcher m = p.matcher(prpLclaimCreditValidDate);
			if(m.find()){
				prpLclaimCreditValidDateYear = m.group(1);
				prpLclaimCreditValidDateMonth = m.group(2);
			}
		}
		//判断是否有信用卡信息，不用去判断等于"";
		if(prpLclaimCreditCardNo != null && prpLclaimCreditNodeType != null){
			PrpLclaimCredit prpLclaimCredit = new PrpLclaimCredit();
			prpLclaimCredit.getId().setBusinessNo(prpLclaimCreditBusinessNo);
			prpLclaimCredit.getId().setNodeType(prpLclaimCreditNodeType);
			prpLclaimCredit.getId().setSerialNo(1);
			prpLclaimCredit.setPolicyNo(prpLclaimCreditPolicyNo);
			prpLclaimCredit.setRiskCode(prpLclaimCreditRiskCode);
			prpLclaimCredit.setBankCode(prpLclaimCreditBankCode);
			if(!CommonUtils.isEmpty(prpLclaimCreditBankCode)){
				prpLclaimCredit.setBankName(this.codeService.translateCode("CreditType", prpLclaimCreditBankCode, "C"));
			}
			prpLclaimCredit.setCardType(prpLclaimCreditCardType);
			prpLclaimCredit.setCardOtherType(prpLclaimCreditCardOtherType);
			prpLclaimCredit.setCardName(prpLclaimCreditCardName);
			prpLclaimCredit.setCardNo(prpLclaimCreditCardNo);
			prpLclaimCredit.setValidDateYear(prpLclaimCreditValidDateYear);
			prpLclaimCredit.setValidDateMonth(prpLclaimCreditValidDateMonth);
			prpLclaimCredit.setHolderName(prpLclaimCreditHolderName);
			prpLclaimCredit.setHolderIdentifyNumber(prpLclaimCreditHolderIdentifyNumber);
			prpLclaimCredit.setHolderTel(prpLclaimCreditHolderTel);
			prpLclaimCredit.setHolderPhone(prpLclaimCreditHolderPhone);
			prpLclaimCredit.setHolderRelationShip(prpLclaimCreditHolderRelationShip);
			prpLclaimCredit.setHolderAddress(prpLclaimCreditHolderAddress);
			prpLclaimCredit.setCurrency(ConstantCodes.LOCAL_CURRENCY);
			prpLclaimCredit.setUseArea(prpLclaimCreditUseArea);
			prpLclaimCredit.setRemark(prpLclaimCreditRemark);
			prpLclaimCredit.setFlag(prpLclaimCreditFlag);
			prpLclaimCredit.setCardCode(prpLclaimCreditCardCode);
			if(!CommonUtils.isEmpty(prpLclaimCreditCardCode)){
				prpLclaimCredit.setCardName(this.codeService.translateCode("CreditType", prpLclaimCreditCardCode, "C"));
			}
			claimDto.setPrpLclaimCredit(prpLclaimCredit);
		}
		//信用卡信息 结束
		
		// 增加主子表金额校验
		PrpLclaim prpLclaimTemp = new PrpLclaim();
		List<PrpLclaimLoss> prplclaimlossListTemp = new ArrayList<PrpLclaimLoss>();
		prpLclaimTemp = claimDto.getPrpLclaim();
		prplclaimlossListTemp = claimDto.getPrpLclaimLossList();
		PrpLclaimLoss prpLclaimLossTemp = new PrpLclaimLoss();
		double prplclaimSumclaim = 0.00;
		double prplclaimlossSumclaim = 0.00;
		if (prpLclaimTemp != null) {
			prplclaimSumclaim = prpLclaimTemp.getSumClaim();
		}
		// 目标币别，保險損失金額、責任估損金額的币别
		String exchCurrency = httpServletRequest.getParameter("prpLclaimCurrency");
		double exchRate = 1; // 兑换率
		double sumClaim = 0d;
		if (prplclaimlossListTemp != null && prplclaimlossListTemp.size() > 0) {
			for (int i = 0; i < prplclaimlossListTemp.size(); i++) {
				prpLclaimLossTemp = (PrpLclaimLoss) prplclaimlossListTemp.get(i);
				sumClaim = prpLclaimLossTemp.getSumClaim();
				if (!prpLclaimLossTemp.getCurrency().equals(exchCurrency)) {
					exchRate = BLPubRateFacade.getExchangeRate(prpLclaimLossTemp.getCurrency(), exchCurrency, new com.sinosoft.sysframework.common.datatype.DateTime(new Date()));
					sumClaim = sumClaim * exchRate;
				}
				prplclaimlossSumclaim += sumClaim;
			}
		}
		BigDecimal BigPrplclaimSumclaim = new BigDecimal(new DecimalFormat("#.##").format(prplclaimSumclaim));
		BigDecimal BigPrplclaimlossSumclaim = new BigDecimal(new DecimalFormat("#.##").format(prplclaimlossSumclaim));
		if (BigPrplclaimSumclaim.compareTo(BigPrplclaimlossSumclaim) != 0) {
			throw new UserException(-1, 0, "立案", "預計賠付總金額與各分項損失匯總金額不相等，請檢查各項金額,重新輸入！<br>" + "預計賠付總金額：" + prplclaimSumclaim + "<br>" + "險別估損匯總金額：" + prplclaimlossSumclaim + "<br>" + "預計賠付總金額與險別估損匯總金額相差 " + (prplclaimSumclaim - prplclaimlossSumclaim));
		}
		return claimDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写立案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public ClaimDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		ClaimDto claimDto = new ClaimDto();
		return claimDto;
	}

	/**
	 * 填写立案页面及查询立案request的生成.
	 * 填写立案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public PrpLclaim registDtoToView(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		// 取得当前用户信息，写操作员信息到立案中
		prpLcheckService.isExist(registNo);
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String policyNo = httpServletRequest.getParameter("policyNo");
		String riskCode = httpServletRequest.getParameter("riskCode");
		String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		ClaimDto claimDto = new ClaimDto();
		// 将查勘信息带入到立案中
		PrpLcheck prpLcheck = new PrpLcheck();
		CheckDto checkDto = checkService.findByPrimaryKey(registNo);
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		List<PrpLltext> list = new ArrayList<PrpLltext>();
		if (checkDto.getPrpLext() == null) {
			httpServletRequest.setAttribute("prpLext", new PrpLext());
		} else {
			httpServletRequest.setAttribute("prpLext", checkDto.getPrpLext());
		}
		// 根据查询出来的数据内容，给PrpLclaimDto赋值
		PrpLclaim prpLclaim = new PrpLclaim();
		//理赔三期需求 begin
		prpLclaim.setReceiptDate(new DateTime(new Date(),DateTime.YEAR_TO_MINUTE).toString());
		//理赔三期需求 end
		prpLcheck = checkDto.getPrpLcheck();
		String indemnityDuty = "0";
		if (prpLcheck == null) { // 要提示，立案之前必须做查看的！！！
			// 没查勘时从报案带出责任比例
			prpLclaim.setIndemnityDuty(registDto.getPrpLregist().getIndemnityDuty());
			indemnityDuty = registDto.getPrpLregist().getIndemnityDuty();
			prpLclaim.setEscapeFlag(registDto.getPrpLregist().getClaimType());
			// 没有做查勘，但是可以立案
			// String msg = "立案之前，请先做查勘！";
			// throw new UserException(1,3,"立案",msg);
			// 原因：在立案时从报案信息中带出出险摘要
			/*
			 * if (checkDto.getPrpLregistTextDtoList() != null) { Iterator
			 * iterator = checkDto.getPrpLregistTextDtoList().iterator(); while
			 * (iterator.hasNext()) { PrpLregistTextDto prpLregistTextDto =
			 * (PrpLregistTextDto) iterator.next(); PrpLltextDto prpLltextDto =
			 * new PrpLltextDto();
			 * prpLltextDto.setContext(prpLregistTextDto.getContext());
			 * list.add(prpLltextDto); } }
			 */
		} else {
			prpLclaim.setIndemnityDuty(prpLcheck.getIndemnityDuty());
			// reason: 立案需要从查勘带出责任比例
			indemnityDuty = prpLcheck.getIndemnityDuty();
			prpLclaim.setEscapeFlag(prpLcheck.getClaimType());
		}
		if (registDto.getPrpLregistTextList() != null) {
			Iterator<PrpLregistText> iterator = registDto.getPrpLregistTextList().iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistText = (PrpLregistText) iterator.next();
				if("1".equals(prpLregistText.getId().getTextType())){
					PrpLltext prpLltext = new PrpLltext();
					prpLltext.setContext(prpLregistText.getContext());
					prpLltext.getId().setTextType(prpLregistText.getId().getTextType());
					list.add(prpLltext);
				}
			}
		}
		double indemnityDutyRate = 100;
		if (indemnityDuty == null) {
			indemnityDuty = "0";
		}
		// 转换赔偿责任
		if (indemnityDuty.trim().equals("0")) {
			indemnityDutyRate = 100;
		} else if (indemnityDuty.trim().equals("1")) {
			indemnityDutyRate = 70;
		} else if (indemnityDuty.trim().equals("2")) {
			indemnityDutyRate = 50;
		} else if (indemnityDuty.trim().equals("3")) {
			indemnityDutyRate = 30;
		} else if (indemnityDuty.trim().equals("4")) {
			indemnityDutyRate = 0.0;
		} else if (indemnityDuty.trim().equals("9")) {
			indemnityDutyRate = 0.0;
		}
		prpLclaim.setIndemnityDutyRate(indemnityDutyRate);
		//肇责10以上，追偿默认带Y
		if(ConstantCodes.CLASSCODE_D.equals(riskType)&&prpLclaim.getIndemnityDutyRate()>10){
			prpLclaim.setReplevyFlag("1");
		}
		// 查询报案信息，並取适当的信息到新登记的立案中
		// reason 强制保单关联信息写到立案中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimDtoList", registClaimList);
		// 如果未查勘就立案，此时，从报案带入出险摘要默认作为出险摘要
		// 在这里进行转化将得到的prpLregistTextDto数据传递给prpLltextDto
		prpLclaim.setRegistNo(registDto.getPrpLregist().getRegistNo());
		prpLclaim.setPolicyNo(policyNo); // 强三
		// 立案时，出险地点取查勘的，如果没有就取报案的出险地点
		if (prpLcheck == null || DataUtils.emptyToNull(prpLcheck.getDamageAddress()) == null) {
			prpLclaim.setDamageAddress(registDto.getPrpLregist().getDamageAddress());
			prpLclaim.setDamageAddressType(registDto.getPrpLregist().getDamageAddressType());
		} else {
			prpLclaim.setDamageAddress(prpLcheck.getDamageAddress());
			prpLclaim.setDamageAddressType(prpLcheck.getDamageAddressType());
		}
		// 立案时，事故类型取查勘的，如果没有就取报案的事故类型
		if (prpLcheck == null || DataUtils.emptyToNull(prpLcheck.getDamageTypeCode()) == null) {
			prpLclaim.setDamageTypeCode(registDto.getPrpLregist().getDamageTypeCode());
			prpLclaim.setDamageTypeName(registDto.getPrpLregist().getDamageTypeName());
		} else {
			prpLclaim.setDamageTypeCode(prpLcheck.getDamageTypeCode());
			prpLclaim.setDamageTypeName(prpLcheck.getDamageTypeName());
		}
		// 立案时，出险区域取查勘的，如果没有就取报案的出险区域
		if (prpLcheck == null || DataUtils.emptyToNull(prpLcheck.getDamageAreaCode()) == null) {
			prpLclaim.setDamageAreaCode(registDto.getPrpLregist().getDamageAreaCode());
			prpLclaim.setDamageAreaName(registDto.getPrpLregist().getDamageAreaName());
		} else {
			prpLclaim.setDamageAreaCode(prpLcheck.getDamageAreaCode());
			prpLclaim.setDamageAreaName(prpLcheck.getDamageAreaName());
		}
		// 立案时，出险原因取查勘的，如果没有就取报案的出险原因，强制险显示强制险出险原因，任意险显示任意险出险原因
		if (prpLcheck == null || (DataUtils.emptyToNull(prpLcheck.getDamageCode()) == null && DataUtils.emptyToNull(prpLcheck.getDamageCodeBZ()) == null)) {
			if (ConstantCodes.RISKCODE_DAZ.equals(riskCode)) {
				prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(registDto.getPrpLregist().getDamageCodeBZ()).trim());
				prpLclaim.setDamageName(registDto.getPrpLregist().getDamageNameBZ());
			} else {
				prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(registDto.getPrpLregist().getDamageCode()).trim());
				prpLclaim.setDamageName(registDto.getPrpLregist().getDamageName());
			}
		} else {
			if (ConstantCodes.RISKCODE_DAZ.equals(riskCode)) {
				prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(prpLcheck.getDamageCodeBZ()).trim());
				prpLclaim.setDamageName(prpLcheck.getDamageNameBZ());
			} else {
				prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(prpLcheck.getDamageCode()).trim());
				prpLclaim.setDamageName(prpLcheck.getDamageName());
			}
		}
		prpLclaim.setRiskCode(riskCode);
		String timeTemp = "";
		timeTemp = StringConvert.toStandardTime(registDto.getPrpLregist().getDamageStartHour());
		prpLclaim.setDamageStartDate(registDto.getPrpLregist().getDamageStartDate());
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageStartMinute(timeTemp.substring(3, 5));
		timeTemp = StringConvert.toStandardTime(registDto.getPrpLregist().getDamageEndHour());
		prpLclaim.setDamageEndDate(registDto.getPrpLregist().getDamageEndDate());
		prpLclaim.setDamageEndHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageEndMinute(timeTemp.substring(3, 5));
		prpLclaim.setClaimDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
		prpLclaim.setInsuredCode(registDto.getPrpLregist().getInsuredCode());
		prpLclaim.setInsuredName(registDto.getPrpLregist().getInsuredName());
		if (!CommonUtils.isEmpty(registDto.getPrpLregist().getInsuredCode())) {
			prpLclaim.setCustomerType(this.codeService.getCustomerType(registDto.getPrpLregist().getInsuredCode()));
		}
		prpLclaim.setComCode(registDto.getPrpLregist().getComCode());
		prpLclaim.setAddressCode(registDto.getPrpLregist().getAddressCode());
		prpLclaim.setClaimType((registDto.getPrpLregist().getClaimType()));
		prpLclaim.setCoinsFlag(registDto.getPrpLregist().getCoinsFlag());
		// 缺省带出报案的币别
		// 货运险如果进行过查勘则取查勘币别
		
		if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {
			if (prpLcheckService.isExist(registNo)) {
				PrpLext prpLext = checkDto.getPrpLext();
				if (prpLext != null) {
					prpLclaim.setEstiCurrency(prpLext.getCurrency());
				} else {
					// reason:货运险立案的币别为空，结果是查勘有数据，但是prplextDto为空,处理方式，修改增加else.
					prpLclaim.setEstiCurrency(registDto.getPrpLregist().getEstiCurrency());
				}
			} else {
				prpLclaim.setEstiCurrency(registDto.getPrpLregist().getEstiCurrency());
			}
		} else {
			prpLclaim.setEstiCurrency(registDto.getPrpLregist().getEstiCurrency());
		}
		// 添加案件性质
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			//伤害险默认我一般赔案
			prpLclaim.setClaimType("0");
			prpLclaim.setClaimTypeName("一般賠案");
		}else{
			prpLclaim.setClaimType("1");
			prpLclaim.setClaimTypeName("速決賠案");
		}
		// 进行币别转化，得到中文名称
		String strCurrencyName = this.codeService.translateCurrencyCode(prpLclaim.getEstiCurrency(), true);
		httpServletRequest.setAttribute("strCurrencyName", strCurrencyName);
		// 原因：添加出险人员信息
		if (registDto.getPrpLacciPerson() != null) {
			prpLclaim.setAcciCode(registDto.getPrpLacciPerson().getAcciCode());
			prpLclaim.setAcciName(registDto.getPrpLacciPerson().getAcciName());
			prpLclaim.setSex(registDto.getPrpLacciPerson().getSex());
			prpLclaim.setAge(registDto.getPrpLacciPerson().getAge());
			prpLclaim.setIdentifyNumber(registDto.getPrpLacciPerson().getIdentifyNumber());
			prpLclaim.setFamilyNo(registDto.getPrpLacciPerson().getFamilyNo());
		}
		// 对照数据结构後增加
		prpLclaim.setLanguage(registDto.getPrpLregist().getLanguage());
		prpLclaim.setLossName(registDto.getPrpLregist().getLossName());
		prpLclaim.setMakeCom(registDto.getPrpLregist().getMakeCom());
		prpLclaim.setHandler1Code(registDto.getPrpLregist().getHandler1Code());
		prpLclaim.setOperatorCode(user.getUserCode());
		// 估损金额的获取，如果是查勘，定损，核损都可以立案，
		if(ConstantCodes.CLASSCODE_Q.equals(riskType)&&prpLcheck != null){
			prpLclaim.setSumClaim(prpLcheck.getEstimateLoss());
			claimDto.setPrpLclaimLossList(checkDto.getPrpLclaimLossList());
		}else{
			prpLclaim.setSumClaim(registDto.getPrpLregist().getEstimateLoss());
		}
		// 设置默认的经办人
		prpLclaim.setHandlerCode(user.getUserCode());
		// 处理机构
		prpLclaim.setHandleDept(user.getComCode());
		// 对车型,条款等信息的支持
		prpLclaim.setClauseType(registDto.getPrpLregist().getClauseType());
		prpLclaim.setClauseName(registDto.getPrpLregist().getClauseName());
		prpLclaim.setLicenseNo(registDto.getPrpLregist().getLicenseNo());
		prpLclaim.setLicenseColorCode(registDto.getPrpLregist().getLicenseColorCode());
		prpLclaim.setLicenseColor(prpLclaim.getLicenseColorCode());
		prpLclaim.setBrandName(registDto.getPrpLregist().getBrandName());
		prpLclaim.setCarKindCode(registDto.getPrpLregist().getCarKindCode());
		prpLclaim.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 对是否团单免导初始化
		prpLclaim.setTermFlag(registDto.getPrpLregist().getTermFlag());
		// 设置立案操作的状态为 新案件登记 (未处理任务)
		prpLclaim.setStatus("1");
		double dAmount = 0;
		// 从保单中获得信息
		// 查询保单信息
		// 根据出险日期获得出险时的保单信息
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		
		//mantis： CLM0003，處理人員：David，需求單編號：CLM0003 原程式使用insuredName查詢造成錯誤，所以調整為用identifyNumber
		String identifyNumber = prpLclaim.getIdentifyNumber();
		
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
		prpLclaim.setClassCode(prpCmain.getClassCode());
		List<PrpCitemKind> prpCitemKindList = null;
		
		/*
		 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 ---start
		  原因 原程式使用insuredName查詢造成編碼錯誤查不到資料，所以調整為用identifyNumber
		*/
//		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName, identifyNumber);
		/*
		 mantis： CLM0003，處理人員：David，需求單編號：CLM0003 ---end
		*/
		
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
			httpServletRequest.setAttribute("prpCitemKindForE", prpCitemKindList);
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		// 将险别信息压到页面上
		httpServletRequest.setAttribute("damageKindList", prpCitemKindList);
		// 得到共保和股东业务信息临分信息
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		Integer familyno = 0;
		//CLM0277 但不送 START
		httpServletRequest.setAttribute("familyno",1);
		if(null!=prpCinsured && prpCinsured.getId()!=null)
		httpServletRequest.setAttribute("familyno",DataUtils.nullToEmpty(String.valueOf(prpCinsured.getId().getSerialNo())));
		//CLM0277 但不送 END
		// 初始化该被保险人的相应险别 end
		com.sinosoft.sysframework.common.datatype.DateTime damageDate1 = new com.sinosoft.sysframework.common.datatype.DateTime(registDto.getPrpLregist().getDamageStartDate());
		httpServletRequest.setAttribute("tempReinsFlag", reinsServiceManager.getReinsService().getSumFacShare(policyNo, damageDate1) > 0 ? "1" : "0");
		if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {
			// 添加保单信息 由於业务对运输方式及工具的存储字段不唯一，所以在此处要做一个处理
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				prpCmainCargo.setConveyance(this.codeService.translateCodeCode("TransMode", prpCmainCargo.getConveyance(), true));
			}
			httpServletRequest.setAttribute("prpCmainCargo", prpCmainCargo);
		}
		String defaultKindCode = getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		// 根据业务部门需求，总保额取主险保额+附加险保额 begin
		if(!ConstantCodes.CARGO_RISKCODE.contains(riskCode)){
			Iterator<PrpCitemKind> it = prpCitemKindList.iterator();
			PrpCitemKind prpCitemKind = null;
			while (it.hasNext()) {
				prpCitemKind = (PrpCitemKind) it.next();
				dAmount = dAmount + prpCitemKind.getAmount();
			}
			prpCmain.setSumAmount(dAmount);
		}
		if (ConstantCodes.CLASSCODE_Z.equals(riskType)) {
			// 根据业务部门需求，总保额取主险保额+附加险保额 end
			//add by zhuyongwei 20140516 reason:理赔三期需求 begin
			//责任险 增加 追溯日 栏位
			prpLclaim.setBkWardStartDate(this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo));
		}
		//add by zhuyongwei 20140516 reason:理赔三期需求 end
		//add by ZhaoXianyang 设置货物编号、名称
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCCargoItem> prpCCargoItemList = prpCCargoItemService.findPrpCCargoItem(queryRule);
		PrpCCargoItem prpCCargoItem = new PrpCCargoItem();
		if(prpCCargoItemList.size() > 0){
			prpCCargoItem = prpCCargoItemList.get(0);
			prpLclaim.setCargoNo(prpCCargoItem.getCargoBigTypeCode());
			if(prpCCargoItem.getCargoName()!=null){
				prpLclaim.setCargoName(prpCCargoItem.getCargoName().trim());
			}
		}
		double deductible = 0.0;
		Iterator<PrpCitemKind> it = prpCitemKindList.iterator();
		while (it.hasNext()) {
			PrpCitemKind prpCitemKind = (PrpCitemKind) it.next();
			String kindCode = prpCitemKind.getKindCode();
			if (ConstantCodes.KINDCODE_D_A.equals(kindCode)) {
				while (it.hasNext()) {
					PrpCitemKind itemKind = (PrpCitemKind) it.next();
					if ("M1".equals(itemKind.getKindCode())) {
						deductible = itemKind.getValue();
						break;
					}
				}
			}
		}
		httpServletRequest.setAttribute("deductible", deductible);
		prpLclaim.setPolicyNo(prpCmain.getPolicyNo());
		prpLclaim.setHandler1Code(prpCmain.getHandler1Code());
		prpLclaim.setComCode(prpCmain.getComCode());
		// 从共、从联保额显示总保额
		if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
			List<PrpCcoins> list2 = (ArrayList<PrpCcoins>) prpCcoinsService.findByConditionsChiefFlag("policyno='" + policyNo + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"'");
			for (Iterator<PrpCcoins> iterator = list2.iterator(); iterator.hasNext();) {
				PrpCcoins prpCcoins = iterator.next();
				double sumAmount = 0;
				BigDecimal bigSumAmount = new BigDecimal(new DecimalFormat(".00").format(prpCmain.getSumAmount()));
				BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(prpCcoins.getCoinsRate() / 100));
				sumAmount = bigSumAmount.divide(bigCoinsRate, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
				prpLclaim.setSumAmount(sumAmount);
			}
		} else {
			prpLclaim.setSumAmount(prpCmain.getSumAmount());
		}
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if (!CommonUtils.isEmpty(prpCaddressList)) {
			String sameAddressNo = prpCaddressList.get(0).getSameAddressNo();
			prpLclaim.setSameAddressNo(sameAddressNo);
		}
		prpLclaim.setSumPremium(prpCmain.getSumPremium());
		prpLclaim.setBusinessNature(prpCmain.getBusinessNature());
		prpLclaim.setPolicyType(prpCmain.getPolicyType());
		prpLclaim.setCurrency(prpCmain.getCurrency());
		prpLclaim.setRiskCode(prpCmain.getRiskCode());
		prpLclaim.setStartDate(prpCmain.getStartDate());
		prpLclaim.setEndDate(prpCmain.getEndDate());
		prpLclaim.setStartHour(prpCmain.getStartHour());
		prpLclaim.setEndHour(prpCmain.getEndHour());
		prpLclaim.setAgentCode(prpCmain.getAgentCode());
		prpLclaim.setPolicyInputDate(CommonUtils.getYearToDayStr(prpCmain.getInputDate()));
		if(ConstantCodes.CARGO_RISKCODE.contains(riskCode)){
			prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCmain.getStartDate()));
		}
		if(ConstantCodes.RISKCODE_AV.equals(riskCode)){
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLclaim.setMakeDate(prpCplane.getBuildYear());
			}
		}else if(ConstantCodes.RISKCODE_OH.equals(riskCode)||ConstantCodes.RISKCODE_EV.equals(riskCode)||ConstantCodes.RISKCODE_FV.equals(riskCode)||ConstantCodes.RISKCODE_EW.equals(riskCode)||ConstantCodes.RISKCODE_FW.equals(riskCode)){
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLclaim.setMakeDate(prpCitemShip.getMakeYearMonth());
			}
		}
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(policyNo, damageDate, damageHour);
		claimDto.setPrpCengageList(prpCengageList);
		prpLclaim.setOthFlag(prpCmain.getOthFlag());
		prpLclaim.setUnderWriteEndDate(new DateTime(prpCmain.getUnderwriteEndDate(), DateTime.YEAR_TO_DAY));
		if(ConstantCodes.CLASSCODE_Z.equals(riskType)){
			httpServletRequest.setAttribute("liabStartDate", this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo));
		}
		// 获取系统设置信息：立案天数
		String standardDays = prpDriskConfigService.getConfigValue("CLAIM_DAYS", prpLclaim.getRiskCode());
		if (standardDays == null || standardDays.equals("")) {
			throw new UserException(1, 3, "platform", "請聯繫系統管理員，在平台配置系統中進行險種" + prpLclaim.getRiskCode() + "'立案天數'的初始化！");
		}
		// 当前时间减去报案时间
		DateTime currentDate = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
		DateTime registDate = new DateTime(registDto.getPrpLregist().getReportDate());
		httpServletRequest.setAttribute("registDate", registDate);
		httpServletRequest.setAttribute("standardDays", standardDays);
		long current_regist = (currentDate.getTime() - registDate.getTime()) / (1000 * 60 * 60 * 24);
		if (current_regist >= Integer.parseInt(standardDays)) {
			httpServletRequest.setAttribute("claim_days", "0");// 0表示立案天数大於系统规定时间，进行提示
		} else {
			httpServletRequest.setAttribute("claim_days", "1");// 1表示立案天数小於系统规定时间，不进行提示
		}
		// 设置相关代码的中文转换
		changeCodeToName(prpLclaim);
		// 设置窗体表单中各个多选框中的内容
		setSelectionList(httpServletRequest, prpLclaim);
		// 查询相同保单号的出险次数
		daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLclaim.getPolicyNo(), prpLclaim.getRegistNo());
		// 设置巨灾代码信息
		prpLclaim.setCatastropheCode1(registDto.getPrpLregist().getCatastropheCode1());
		prpLclaim.setCatastropheName1(registDto.getPrpLregist().getCatastropheName1());
		prpLclaim.setCatastropheCode2(registDto.getPrpLregist().getCatastropheCode2());
		prpLclaim.setCatastropheName2(registDto.getPrpLregist().getCatastropheName2());
		// 设置主立案信息内容到窗体表单
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		// Reason:损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());// 
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "claim";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);
		com.sinosoft.sysframework.common.datatype.DateTime dateTime = new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate());
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLclaim.getPolicyNo(), dateTime);
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);// 
		// 设置各个子表信息项到窗体表单
		// 给报案文件多行列表准备数据
		claimDto.setPrpLdriverList(registDto.getPrpLdriverList());
		claimDto.setPrpLthirdCarLossList(registDto.getPrpLthirdCarLossList());
		claimDto.setPrpLthirdPropList(registDto.getPrpLthirdPropList());
		claimDto.setPrpLthirdPartyList(registDto.getPrpLthirdPartyList());
		// 添加人伤跟踪信息
		claimDto.setPrpLpersonTraceList(registDto.getPrpLpersonTraceList());
		// 因为是新录入，所以都是空的。
		claimDto.setPrpLclaimFeeList(new ArrayList<PrpLclaimFee>());
		claimDto.setPrpLdocList(new ArrayList<PrpLdoc>());
		claimDto.setPrpLltextList(list);
		// 给立案信息理赔联系记录多行列表准备数据
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
		prpLregistExt.setRiskCode(riskCode); // 强三
		arrayListRegistExt = checkDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		// 准备自表信息
		claimDto.setPrpLclaim(prpLclaim);
		setSubInfo(httpServletRequest, claimDto , prpCitemKindList);
		// 设置默认的估损金额的子表金额信息
		setFirstClaimFeeLoss(httpServletRequest, prpLclaim, riskType);
		httpServletRequest.setAttribute("prpLacciPerson", claimDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : claimDto.getPrpLacciPerson());
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		//立案|正在處理立案任務
		settingPAF4567(httpServletRequest,prpLclaim);
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		// 索赔申请人
		if (claimDto.getPrpLclaimLossList() == null) {
			claimDto.setPrpLclaimLossList(new ArrayList<PrpLclaimLoss>());
		}
		// 意健险在提交前，所有的申请调查应该已经提交。
		if ("E".equals(riskType)) {
			// 险类27修改为07
			int intCount = 0; // 没有提交的申请调查数目
			String strFlowID = httpServletRequest.getParameter("swfLogFlowID");
			String strSql = " FLOWID='" + strFlowID + "' and NODETYPE='check' and NODESTATUS<>'4'";
			intCount = this.getWorkFlowService().findNodesByConditions(strSql).size();
			httpServletRequest.setAttribute("com_sinosoft_acciFlag", intCount > 0 ? "N" : "Y"); // 设置一个标志位：N表示不能提交，Y表示可以提交。
		}
		// 获取兑换率信息
		UIExchAction uiExchAction = new UIExchAction();
		List<PrpDexchDto> prpDexchList = (List<PrpDexchDto>) uiExchAction.getExchOfMaxDate(DateTime.current().toString().substring(0, 10));

		// reason:签单币别不是CNY时，给出提示，並提供当前兑换率
		PrpDexchDto prpDexch = null;
		String currency = claimDto.getPrpLclaim().getCurrency();
		for(int i=0;i<prpDexchList.size();i++){
			if(prpDexchList.get(i).getBaseCurrency().equals(currency)){
				prpDexch = prpDexchList.get(i);
			}
		}
		httpServletRequest.setAttribute("prpDexch", prpDexch);
		httpServletRequest.setAttribute("prpDexchList", prpDexchList);
		httpServletRequest.setAttribute("claimDto", claimDto);
		// 送审初复核初始化
		return prpLclaim;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		super.setPolicyService(policyService);
		this.policyService = policyService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		super.setEndorseViewHelper(endorseViewHelper);
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	/**
	 * 填写立案页面及查询立案request的生成.
	 * 填写立案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIni 取出的初始化信息
	 * @throws Exception
	 */

	public void claimDtoToView(HttpServletRequest httpServletRequest, ClaimDto claimDto) throws Exception {
		// 查询立案信息
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		// 给prpLclaim赋值
		if (prpLclaim == null) {
			String msg = "案件無法查詢到！";
			throw new UserException(1, 3, "查詢", msg);
		}
		String claimNo = prpLclaim.getClaimNo();
		// CheckDto checkDto =
		// checkService.findByPrimaryKey(claimDto.getPrpLclaim().getRegistNo());
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageStartMinute(timeTemp.substring(3, 5));
		timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageEndHour());
		prpLclaim.setDamageEndHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageEndMinute(timeTemp.substring(3, 5));
		// 区分逃逸和全损
		String strTemp = prpLclaim.getEscapeFlag();
		if ((strTemp.length() > 0) && (strTemp.substring(0, 1) != null)) {
			prpLclaim.setEscapeFlag(strTemp.substring(0, 1));
		}
		if ((strTemp.length() > 1) && (strTemp.substring(1, 2) != null)) {
			prpLclaim.setEscapeFlag2(strTemp.substring(1, 2));
		}
		// 设置立案操作的状态为 案件修改 (正处理任务)
		if (claimDto.getPrpLclaimStatus() != null) {
			if (claimDto.getPrpLclaimStatus().getStatus().equals("7"))
				claimDto.getPrpLclaimStatus().setStatus("3");
			prpLclaim.setStatus(claimDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLclaim.setStatus("4");
		}
		if (prpLclaim.getClaimDate() == null || "".equals(prpLclaim.getClaimDate())) {
			prpLclaim.setClaimDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		}
		/*
		 * 从保单中获得信息 原因：因为这些保单信息都已经保存到立案表信息中了，故注掉，但是因为需要转换界面上的
		 * 车辆信息，所以仍需要去取保单和下面的车辆信息这一个过程。
		 */
		double dAmount = 0;
		String riskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		prpLclaim.setClassCode(prpCmain.getClassCode());
		List<PrpCitemKind> prpCitemKindList = null;
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
			httpServletRequest.setAttribute("prpCitemKindForE", prpCitemKindList);
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		httpServletRequest.setAttribute("damageKindList", prpCitemKindList);
		String defaultKindCode = getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		if (ConstantCodes.CLASSCODE_D.equals(riskType)) {
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				// 对车型等信息的支持
				PrpCitemCar prpCitemCar = (PrpCitemCar) prpCitemCarList.get(0);
				prpLclaim.setClauseType(prpCitemCar.getClauseType());
				prpLclaim.setLicenseNo(prpCitemCar.getLicenseNo());
				prpLclaim.setLicenseColorCode(prpCitemCar.getLicenseColorCode());
				prpLclaim.setLicenseColor(prpCitemCar.getLicenseColorCode());
				prpLclaim.setBrandName(prpCitemCar.getBrandName());
				prpLclaim.setCarKindCode(prpCitemCar.getCarKindCode());
			}
		}
		if(ConstantCodes.CLASSCODE_Z.equals(riskType)){
			httpServletRequest.setAttribute("liabStartDate", this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo));
		}
		Iterator<?> it = prpCitemKindList.iterator();
		double deductible = 0.0;
		while (it.hasNext()) {
			PrpCitemKind prpCitemKind = (PrpCitemKind) it.next();
			String kindCode = prpCitemKind.getKindCode();
			if (ConstantCodes.KINDCODE_D_A.equals(kindCode)) {
				while (it.hasNext()) {
					PrpCitemKind itemKind = (PrpCitemKind) it.next();
					if ("M1".equals(itemKind.getKindCode())) {
						deductible = itemKind.getValue();
						break;
					}
				}
			}
		}
		httpServletRequest.setAttribute("deductible", deductible);
		// 添加保单信息
		String riskCode=claimDto.getPrpLclaim().getRiskCode();
		prpLclaim.setPolicyInputDate(CommonUtils.getYearToDayStr(prpCmain.getInputDate()));
		if(ConstantCodes.RISKCODE_MC.equals(riskCode)){
			prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCmain.getStartDate()));
		}
		if(ConstantCodes.RISKCODE_AV.equals(riskCode)){
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLclaim.setMakeDate(prpCplane.getBuildYear());
			}
		}else if(ConstantCodes.RISKCODE_OH.equals(riskCode)||ConstantCodes.RISKCODE_EV.equals(riskCode)||ConstantCodes.RISKCODE_FV.equals(riskCode)||ConstantCodes.RISKCODE_EW.equals(riskCode)||ConstantCodes.RISKCODE_FW.equals(riskCode)){
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLclaim.setMakeDate(prpCitemShip.getMakeYearMonth());
			}
		}
		// 由於业务对运输方式及工具的存储字段不唯一，所以在此处要做一个处理
		if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				if (CommonUtils.isEmpty(prpCmainCargo.getBlNo())) {
					prpCmainCargo.setBlNo(this.codeService.translateCodeCode("ConveyanceType", prpCmainCargo.getConveyance(), true));
				}
			}
			httpServletRequest.setAttribute("prpCmainCargo", prpCmainCargo);
		}
		// 根据业务部门需求，总保额取主险保额+附加险保额
		if(!ConstantCodes.CARGO_RISKCODE.contains(riskCode)){
			Iterator<PrpCitemKind> itera = prpCitemKindList.iterator();
			PrpCitemKind prpCitemKind = null;
			while (itera.hasNext()) {
				prpCitemKind = (PrpCitemKind) itera.next();
				dAmount = dAmount + prpCitemKind.getAmount();
			}
			prpCmain.setSumAmount(dAmount);
			// 根据业务部门需求，总保额取主险保额+附加险保额
			// 从共、从联保额显示总保额
			if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
				List<PrpCcoins> list2 = (ArrayList<PrpCcoins>) prpCcoinsService.findByConditionsChiefFlag("policyno='" + claimDto.getPrpLclaim().getPolicyNo() + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"'");
				for (Iterator<PrpCcoins> iterator = list2.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoins = iterator.next();
					double sumAmount = 0;
					BigDecimal bigSumAmount = new BigDecimal(new DecimalFormat(".00").format(prpCmain.getSumAmount()));
					BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(prpCcoins.getCoinsRate() / 100));
					sumAmount = bigSumAmount.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
					prpLclaim.setSumAmount(sumAmount);
				}
			} else {
				prpLclaim.setSumAmount(prpCmain.getSumAmount());
			}
		}
		// 设置相关代码的中文转换
		changeCodeToName(prpLclaim);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLclaim);
		// 查询相同保单号的出险次数
		// reason:获取危险单位信息
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "claim";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);
		// 设置各个子表信息项到窗体表单
		claimDto.setPrpLpersonTraceList(claimDto.getPrpLpersonTraceList());
		setSubInfo(httpServletRequest, claimDto , prpCitemKindList);
		httpServletRequest.setAttribute("prpLacciPerson", claimDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : claimDto.getPrpLacciPerson());
		// 显示立案日期和现在日期之间的工作日天数
		RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
		DateTime registDate = new DateTime(registDto.getPrpLregist().getReportDate());
		// reason 强制保单关联信息写到立案中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		httpServletRequest.setAttribute("registDate", registDate);
		// 立案环节增加理赔联系记录
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(claimDto.getPrpLclaim().getRegistNo());
		prpLregistExt.setRiskCode(claimDto.getPrpLclaim().getRiskCode());
		arrayListRegistExt = claimDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		// 原因：添加出险人员信息
		if (registDto.getPrpLacciPerson() != null) {
			prpLclaim.setAcciCode(registDto.getPrpLacciPerson().getAcciCode());
			prpLclaim.setAcciName(registDto.getPrpLacciPerson().getAcciName());
			prpLclaim.setSex(registDto.getPrpLacciPerson().getSex());
			prpLclaim.setAge(registDto.getPrpLacciPerson().getAge());
			prpLclaim.setIdentifyNumber(registDto.getPrpLacciPerson().getIdentifyNumber());
			prpLclaim.setFamilyNo(registDto.getPrpLacciPerson().getFamilyNo());
		}
		// 意健险在提交前，所有的申请调查应该已经提交
		String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if ("E".equals(strRiskType)) {
			int intCount = 0; // 没有提交的申请调查数目
			String strFlowID = httpServletRequest.getParameter("swfLogFlowID");
			String strSql = " FLOWID='" + strFlowID + "' and NODETYPE='check' and NODESTATUS<>'4'";
			intCount = this.getWorkFlowService().findNodesByConditions(strSql).size();
			httpServletRequest.setAttribute("com_sinosoft_acciFlag", intCount > 0 ? "N" : "Y"); // 设置一个标志位：N表示不能提交，Y表示可以提交。
		}
		httpServletRequest.setAttribute("claimDto", claimDto);
		// 缺省带出报案的币别
		prpLclaim.setEstiCurrency(prpLclaim.getCurrency());
		// 进行币别转化，得到中文名称
		String strCurrencyName = this.codeService.translateCurrencyCode(prpLclaim.getEstiCurrency(), true);
		httpServletRequest.setAttribute("strCurrencyName", strCurrencyName);
		// 获取兑换率信息
		UIExchAction uiExchAction = new UIExchAction();
		List<PrpDexchDto> prpDexchList = (List<PrpDexchDto>) uiExchAction.getExchOfMaxDate(DateTime.current().toString().substring(0, 10));

		// reason:签单币别不是CNY时，给出提示，並提供当前兑换率
		PrpDexchDto prpDexch = null;
		String currency = claimDto.getPrpLclaim().getCurrency();
		for(int i=0;i<prpDexchList.size();i++){
			if(prpDexchList.get(i).getBaseCurrency().equals(currency)){
				prpDexch = prpDexchList.get(i);
			}
		}
		httpServletRequest.setAttribute("prpDexch", prpDexch);
		httpServletRequest.setAttribute("prpDexchList", prpDexchList);
		// 设置主立案信息内容到窗体表单
		if (claimDto.getPrpLext() == null) {
			httpServletRequest.setAttribute("prpLext", new PrpLext());
		} else {
			httpServletRequest.setAttribute("prpLext", claimDto.getPrpLext());
		}
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));
		// 索赔申请人
		List<PrpLacciPerson> prpLacciPersonList = claimDto.getPrpLacciPersonList();
		PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
		prpLacciPerson.setPrpLacciPersonList(prpLacciPersonList);
		httpServletRequest.setAttribute("prpLacciPerson", prpLacciPerson);

	}

	/**
	 * 根据PrpClaimDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param prpClaimDto 立案的数据类
	 * @throws Exception
	 */

	private void setSubInfo(HttpServletRequest httpServletRequest, ClaimDto claimDto , List<PrpCitemKind> prpCitemKindList) throws Exception {
		String strCurrency = "";
		String strCurrencyName = "";
		// (1).[涉案车辆]给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		arrayList = claimDto.getPrpLthirdPartyList();
		prpLthirdParty.setThirdPartyList(arrayList);
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		// (2).[驾驶员]给驾驶员多行多行列表准备数据
		List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = new PrpLdriver();
		arrayListDriver = claimDto.getPrpLdriverList();
		prpLdriver.setDriverList(arrayListDriver);
		httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		// (3).[查勘信息]给报案文件多行列表准备数据
		PrpLltext prpLltext = new PrpLltext();
		PrpLltextId prpLltextId = new PrpLltextId();
		String tempContext = "";
		String strRiskType = this.codeService.translateRiskCodetoRiskType(claimDto.getPrpLclaim().getRiskCode());
		int number = 1;
		String lossItemCode = "";
		// String nodeStatus = "";
		boolean add = false;
		List<String> delete = new ArrayList<String>();
		String condition = " NODETYPE='certa' AND BUSINESSNO= '" + claimDto.getPrpLclaim().getRegistNo() + "'  ORDER BY LOSSITEMCODE";
		List<SwfLog> certaList = this.getSwfLogService().findByConditions(condition);
		while (number <= arrayList.size()) {
			Iterator<SwfLog> iteratorOfcerta = certaList.iterator();
			while (iteratorOfcerta.hasNext()) {
				SwfLog SwfLog = iteratorOfcerta.next();
				lossItemCode = SwfLog.getLossItemCode();
				// nodeStatus = SwfLog.getNodeStatus();
				if (Integer.parseInt(lossItemCode) == number) {
					delete.add("disabled");
					add = true;
				}
			}
			number++;
			if (add == false) {
				delete.add("");
			}
			add = false;
		}
		httpServletRequest.setAttribute("delete", delete);
		if (claimDto.getPrpLltextList() != null) {
			Iterator<PrpLltext> iterator = claimDto.getPrpLltextList().iterator();
			while (iterator.hasNext()) {
				PrpLltext prpLltextTemp = iterator.next();
				tempContext = tempContext + prpLltextTemp.getContext();
			}
		}
		prpLltext.setContext(tempContext);
		prpLltextId.setTextType("09");
		prpLltext.setId(prpLltextId);
		httpServletRequest.setAttribute("prpLltext", prpLltext);
		// (4).[估损金额]给估损金额文件多行列表准备数据
		List<PrpLclaimFee> claimFeeList = new ArrayList<PrpLclaimFee>();
		List<PrpLclaimFee> claimFeeList1 = new ArrayList<PrpLclaimFee>();
		PrpLclaimFee prpLclaimFee = new PrpLclaimFee();
		claimFeeList = claimDto.getPrpLclaimFeeList();
		Iterator<PrpLclaimFee> it = claimFeeList.iterator();
		while (it.hasNext()) {
			prpLclaimFee = (PrpLclaimFee) it.next();
			strCurrency = prpLclaimFee.getId().getCurrency();
			strCurrencyName = this.codeService.translateCurrencyCode(strCurrency, true);
			prpLclaimFee.setCurrencyName(strCurrencyName);
			claimFeeList1.add(prpLclaimFee);
		}
		prpLclaimFee.setClaimFeeList(claimFeeList1);
		httpServletRequest.setAttribute("prpLclaimFee", prpLclaimFee);
		// (5).给险别估损金额文件多行列表准备数据
		List<PrpLclaimLoss> claimLossList = new ArrayList<PrpLclaimLoss>();
		PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
		claimLossList = claimDto.getPrpLclaimLossList();
		if (claimLossList != null) {
			for (int i = 0; i < claimLossList.size(); i++) {
				PrpLclaimLoss prpLclaimLoss1 = (PrpLclaimLoss) claimLossList.get(i);
				if (prpLclaimLoss1.getInputDate() == null) {
					prpLclaimLoss1.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				}
				prpLclaimLoss1.setKindName(this.codeService.translateKindCode(prpLclaimLoss1.getRiskCode(), prpLclaimLoss1.getKindCode(), true));
				if (prpLclaimLoss1.getKindCodeSub() != null && !"".equals(prpLclaimLoss1.getKindCodeSub())) {
					prpLclaimLoss1.setKindNameSub(this.codeService.translateKindCode(prpLclaimLoss1.getRiskCode(), prpLclaimLoss1.getKindCodeSub(), true));
				}
				for(PrpCitemKind p : prpCitemKindList){
					if(p.getKindCode().equals(prpLclaimLoss1.getKindCode())
							&& !CommonUtils.isEmpty(p.getItemCode())
							&& p.getItemCode().equals(prpLclaimLoss1.getItemCode())){
						prpLclaimLoss1.setItemKindName(p.getItemDetailName());
					}
				}
				prpLclaimLoss.setLossFeeType(prpLclaimLoss1.getLossFeeType());
				prpLclaimLoss1.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaimLoss1.getCurrency(), true));
			}
		}
		prpLclaimLoss.setClaimLossList(claimLossList);
		httpServletRequest.setAttribute("prpLclaimLoss", prpLclaimLoss);
		// (5).[单证信息]给索赔单证文件多行列表准备数据
		Collection<PrpLdoc> docList = new ArrayList<PrpLdoc>();
		PrpLdoc prpLdoc = new PrpLdoc();
		docList = claimDto.getPrpLdocList();
		prpLdoc.setDocList(docList);
		httpServletRequest.setAttribute("prpLdoc", prpLdoc);
		// 特别约定信息多行列表准备数据
		List<PrpCengage> arrayListCengageTemp = new ArrayList<PrpCengage>();
		PrpCengage prpCengage = new PrpCengage();
		arrayListCengageTemp = claimDto.getPrpCengageList();
		List<PrpCengage> cengageListTemp = new ArrayList<PrpCengage>();
		if (arrayListCengageTemp != null) {
			Iterator<PrpCengage> iteratorCengage = arrayListCengageTemp.iterator();
			while (iteratorCengage.hasNext()) {
				PrpCengage prpCengageTemp = (PrpCengage) iteratorCengage.next();
				if (prpCengageTemp.getClauseCode() != null && prpCengageTemp.getClauseCode().length() > 0 && prpCengageTemp.getClauseCode().charAt(0) == 'T') {
					cengageListTemp.add(prpCengageTemp);
				}
			}
			// boolean cFlag = false;
			arrayListCengageTemp = new ArrayList<PrpCengage>();
			arrayListCengageTemp.addAll(cengageListTemp);
			cengageListTemp = new ArrayList<PrpCengage>();
			iteratorCengage = arrayListCengageTemp.iterator();
			PrpCengage prpCengageTemp1 = new PrpCengage();
			while (iteratorCengage.hasNext()) {
				PrpCengage prpCengageTemp = (PrpCengage) iteratorCengage.next();
				if (prpCengageTemp.getTitleFlag().equals("0")) {
					// cFlag = true;
					cengageListTemp.add(prpCengageTemp1);
					prpCengageTemp1 = new PrpCengage();
					PropertyUtils.copyProperties(prpCengageTemp1, prpCengageTemp);
				} else {
					prpCengageTemp1.setContext(prpCengageTemp1.getContext() + prpCengageTemp.getClauses() + "<br>");
				}
			}
			cengageListTemp.add(prpCengageTemp1);
			if (cengageListTemp.size() > 0) {
				cengageListTemp.remove(0);
			}
		}
		prpCengage.setPrpCengageList(cengageListTemp);
		httpServletRequest.setAttribute("prpCengage", prpCengage);
		// 损失部位模块加进涉案车辆信息中後，相应模块做调整
		List<PrpLthirdCarLoss> arrayListThirdCarLoss = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = new PrpLthirdCarLoss();
		arrayListThirdCarLoss = claimDto.getPrpLthirdCarLossList();
		// prpLthirdCarLoss.setThirdCarLossList((Collection)
		// CommonUtils.convertObjs(PrpLthirdCarLoss.class,
		// arrayListThirdCarLoss));
		prpLthirdCarLoss.setThirdCarLossList(arrayListThirdCarLoss);
		httpServletRequest.setAttribute("prpLthirdCarLoss", prpLthirdCarLoss);
		// 在报案页面中加上其它损失模块
		List<PrpLthirdProp> arrayListThirdProp = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = new PrpLthirdProp();
		arrayListThirdProp = claimDto.getPrpLthirdPropList();
		prpLthirdProp.setThirdPropList(arrayListThirdProp);
		httpServletRequest.setAttribute("prpLthirdProp", prpLthirdProp);

		// 查询保单信息 显示特别约定列表
		String registNo = claimDto.getPrpLclaim().getRegistNo();
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		String policyNo  = prpLregist.getPolicyNo();
		List<PrpCitemKind> itemKindList = new ArrayList<PrpCitemKind>();
		itemKindList.addAll(prpCitemKindList);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<Prplregistrpolicy> registRPolicyList = prpLregistrpolicyService.findByRegistNo(claimDto.getPrpLclaim().getRegistNo());
		String mainPolicyNo = "";
		for (int i = 0; i < registRPolicyList.size(); i++) {
			Prplregistrpolicy prpLRegistRPolicy = registRPolicyList.get(i);
			if ("3".equals(prpLRegistRPolicy.getPolicyType())) {
				mainPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
				break;
			}
		}
		if (!CommonUtils.isEmpty(mainPolicyNo) && !policyNo.equals(mainPolicyNo)) {
			List<PrpCitemKind> mainPrpCitemKindList = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, null , null );
			itemKindList.addAll(mainPrpCitemKindList);
		}
		// 筛选出可对人伤进行赔付的险别，且去重
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = null;
		for (PrpCitemKind temp : itemKindList) {
			if("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode()))&&!ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())){
				continue;
			}
			prpCitemKind = new PrpCitemKind();
			BeanUtils.copyProperties(prpCitemKind, temp);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			referKindList.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		// 给人员伤亡跟踪多行多行列表准备数据
		PrpLpersonTrace prpLpersonTrace = new PrpLpersonTrace();
		List<PrpLpersonTrace> arrayListPersonTrace = claimDto.getPrpLpersonTraceList();
		prpLpersonTrace.setPersonTraceList(arrayListPersonTrace);
		prpLpersonTrace.setNodeType("check");
		if (claimDto.getPrpLpersonTraceList() != null) {
			PrpDcode[] prpDcodes = null;
			PrpLpersonTrace prplpersonTrace = null;
			for(int i=0;i<arrayListPersonTrace.size();i++){
				prplpersonTrace = arrayListPersonTrace.get(i);
				prplpersonTrace.setPrpLpersonTraceReferKind(prplpersonTrace.getReferKind());
				// 获取一级行业和二级行业信息 start
				prpDcodes = codeService.translateJobCode(prplpersonTrace.getJobCode(), claimDto.getPrpLclaim().getRiskCode());
				prplpersonTrace.setJobCode1(prpDcodes[0].getId().getCodeCode());
				prplpersonTrace.setJobName1(prpDcodes[0].getCodeCName());
				prplpersonTrace.setJobCode2(prpDcodes[1].getId().getCodeCode());
				prplpersonTrace.setJobName2(prpDcodes[1].getCodeCName());
			}
		}
		httpServletRequest.setAttribute("prpLpersonTrace", prpLpersonTrace);
		//伤害险
		if("E".equals(ConstantCodes.carClassMap.get(claimDto.getPrpLclaim().getRiskCode()))){
			List<PrpCitemKind> damageKindList = (List<PrpCitemKind>) httpServletRequest.getAttribute("damageKindList");
			PrpLclause prpLclause = null;
			for(PrpCitemKind temp : damageKindList){
				prpLclause = prpLclauseService.findPrpLclause(temp.getKindCode());
				if(prpLclause!=null&&!CommonUtils.isEmpty(prpLclause.getCoverageratio())){
					temp.setCoverageratio(prpLclause.getCoverageratio());
				}
			}
		}
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 本案对各险别限额的控制
			String configCode = this.getCodeService().translateRiskCodetoConfigCode(claimDto.getPrpLclaim().getRiskCode());
			if ("RISKCODE_DAZ".equals(configCode)) {// 强制险限制到每个个人
				CompensateKindLimitViewHelper.setLimitInfo(prpLclaim, httpServletRequest);
			} else {
				PolicyDto policyDto = new PolicyDto();
				policyDto.setPrpCmain(this.endorseViewHelper.findPrpCmain(prpLclaim.getPolicyNo(), damageDate, damageHour));
				policyDto.setPrpCitemCarList(this.endorseViewHelper.findPrpCitemCar(prpLclaim.getPolicyNo(), damageDate, damageHour));
				policyDto.setPrpCitemKindList(prpCitemKindList);
				CompensateKindLimitViewHelper.setLimitInfo(policyDto, claimDto.getPrpLclaim(), httpServletRequest);
			}
		}
	}

	/**
	 * 根据PrpClaimDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpClaimDto 立案的数据类
	 * @param ClaimDto 查询出的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(PrpLclaim prpLclaim) throws Exception {
		// (1)条款名称的转换
		String clauseType = prpLclaim.getClauseType();
		String clauseName = this.codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLclaim.setClauseName(clauseName);
		// (2)号牌颜色转换
		String licenseColorCodeCode = prpLclaim.getLicenseColorCode();
		String licenseColor = this.codeService.translateCodeCode("LicenseColor", licenseColorCodeCode, true);
		prpLclaim.setLicenseColor(licenseColor);
		// (3)车辆类型转换
		String carKindCode = prpLclaim.getCarKindCode();
		String carKind = this.codeService.translateCodeCode("CarKind", carKindCode, true);
		prpLclaim.setCarKind(carKind);
		// (4)对业务归属结构进行转换
		String comCode = prpLclaim.getComCode();
		String comName = this.codeService.translateComCode(comCode, true);
		prpLclaim.setComName(comName);
		// (5)对归属业务员进行转换
		String handler1Code = prpLclaim.getHandler1Code();
		String handler1Name = this.codeService.translateUserCode(handler1Code, true);
		prpLclaim.setHandler1Name(handler1Name);
		// (6)对代理人进行转换
		String agentCode = prpLclaim.getAgentCode();
		String agentName = this.codeService.translateUserCode(agentCode, true);
		prpLclaim.setAgentName(agentName);
		// (7)对经办人进行转换
		String handlerCode = prpLclaim.getHandlerCode();
		String handlerName = this.codeService.translateUserCode(handlerCode, true);
		prpLclaim.setHandlerName(handlerName);
		// (8)对案件性质进行转换
		String strClaimType = prpLclaim.getClaimType();
		String strClaimTypeName = this.codeService.translateCodeCode("CaseCode", strClaimType, true);
		prpLclaim.setClaimTypeName(strClaimTypeName);
		// (9)对业务类型进行转换
		String strBusinessNature = prpLclaim.getBusinessNature();
		String strBusinessNatureName = this.codeService.translateCodeCode("BusinessNature", strBusinessNature, true);
		prpLclaim.setBusinessNatureName(strBusinessNatureName);
		// 10)对语种转换
		if (prpLclaim.getLanguage().equals("C")) {
			prpLclaim.setLanguageName("中文");
		}
		if (prpLclaim.getLanguage().equals("E")) {
			prpLclaim.setLanguageName("英文");
		}
		// (11)对makeCom进行转换
		// 立案任务查询，理赔登记部门显示的只有代码，没有名称。
		String makeCom = prpLclaim.getMakeCom();
		String makeComName = this.codeService.translateComCode(makeCom, true);
		prpLclaim.setMakeComName(makeComName);
		if (DataUtils.emptyToNull(prpLclaim.getDamageAreaCode()) != null) {
			prpLclaim.setDamageAreaName(this.codeService.translateCodeCode("DamageAreaCode", prpLclaim.getDamageAreaCode(), true));
		}
		// (12)对出现地址进行转换
		String strAddressCode = prpLclaim.getAddressCode();
		String strAddressName = this.codeService.translateCodeCode("PostCode", strAddressCode, true);
		prpLclaim.setAddressName(strAddressName);
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpClaimDto 立案的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLclaim prpLclaim) throws Exception {
		// (1)得到立案类型列表
		List<PrpDcode> reportTypes = this.codeService.getCodeType("ReportType", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// (2)得到案件种类列表列表
		List<PrpDcode> claimTypes = this.codeService.getCodeType("CaseCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// (3)得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = this.codeService.getCodeType("DamageAddress", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// (4)得到车辆种类列表
		List<PrpDcode> carKindCodes = this.codeService.getCodeTypeCarKind("CarKind", prpLclaim.getClassCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// (5)得到车牌底色列表
		List<PrpDcode> licenseColorCode = this.codeService.getCodeType("LicenseColor", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// (6)得到赔偿责任列表
		List<PrpDcode> indemnityDuty = this.codeService.getCodeType("IndemnityDuty", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// (7)得到赔案类别列表
		List<PrpDcode> escapeFlags = this.codeService.getCodeType("CaseCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// (8)得到性别
		List<PrpDcode> driverSex = this.codeService.getCodeType("SexCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// (9)得到职业分类
		List<PrpDcode> driverOccupation = this.codeService.getCodeType("Occupation", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// (10)得到文化程度
		List<PrpDcode> education = this.codeService.getCodeType("Education", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("educations", education);
		// (11)得到理赔类型的列表 --- 目前无法得到
		httpServletRequest.setAttribute("claimFlagList", ConstantsCollection.claimFlagList);
		// 證件類型
		httpServletRequest.setAttribute("drivingCarTypeList", ConstantsCollection.drivingCarTypeList);
		// 駕駛人區別
		httpServletRequest.setAttribute("driverDistrictList", ConstantsCollection.driverDistrictList);
		// 估损金额调整
		httpServletRequest.setAttribute("lossLossFeeTypeList", ConstantsCollection.lossLossFeeTypeList);
		// 範圍
		if (ConstantCodes.CLASSCODE_D_B.equals(prpLclaim.getClassCode())) {//强制险单独处理。
			httpServletRequest.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryListBZ);
		} else {
			httpServletRequest.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryList);
		}
		// 傷亡類型
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		// 是否自行就醫
		httpServletRequest.setAttribute("motionFlagList", ConstantsCollection.motionFlagList);
		// 本車駕駛人與被保險人關係
		httpServletRequest.setAttribute("thirdPartyRelationshipList", ConstantsCollection.thirdPartyRelationshipList);
		// 被保險人身分 ,駕駛人身份
		httpServletRequest.setAttribute("identityList", ConstantsCollection.identityList);
		// 承載單位
		httpServletRequest.setAttribute("partyCarryingUnitList", ConstantsCollection.partyCarryingUnitList);
		httpServletRequest.setAttribute("LOCAL_CURRENCY", ConstantCodes.LOCAL_CURRENCY);
		httpServletRequest.setAttribute("LOCAL_CURRENCYNAME", ConstantCodes.LOCAL_CURRENCYNAME);
		// 運輸方式
		httpServletRequest.setAttribute("transportTypeList", ConstantsCollection.transportTypeList);
		// 進出口別代號 
		httpServletRequest.setAttribute("importTypeList", ConstantsCollection.importTypeList);
		
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		// 例如 09 10 11 12 13 14 15 16 17 18 19   
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = (thisYear - 5); i < (thisYear + 6); i++) {
			yearList.add(i);
		}
		httpServletRequest.setAttribute("yearList", yearList);
		List<PrpPhead> endorseList = endorseService.findByPrpPheadConditions("policyNo='"+prpLclaim.getPolicyNo()+"' and underwriteflag in ('1','3') order by endorsetimes");
		httpServletRequest.setAttribute("endorseList", endorseList);
		//危險分類總項 
		List<PrpDcode> dangerousClassItemList = this.codeService.findPrpDcodeByConditions("codeType='DangerousClassItem' and (upperCode is null or upperCode = '') and validstatus = '1' order by codeCode");
		httpServletRequest.setAttribute("dangerousClassItemList",dangerousClassItemList);
		// 肇事类型
		httpServletRequest.setAttribute("accidentTypeList", ConstantsCollection.accidentTypeList);
		if(ConstantCodes.RISKCODE_CC.equals(prpLclaim.getRiskCode())){
			httpServletRequest.setAttribute("creditBankList", this.codeService.findPrpDcodeByConditions(" codeType='CreditType' and validStatus = '1' and codeLevel = '1' order by codeCode "));
		}
	}

	/**
	 * 根据PolicyDto获得该保单投保的主要险别
	 * @param policyDto
	 * @return
	 */
	public String getDefaultKindCodeByPolicyDto(PolicyDto policyDto) {
		String kindCode = "";
		for (Iterator<PrpCitemKind> iter = policyDto.getPrpCitemKindList().iterator(); iter.hasNext();) {
			PrpCitemKind itemKind = (PrpCitemKind) iter.next();
			// ItemKindNo为1的为主要险别
			if (itemKind.getId().getItemKindNo() == 1) {
				return itemKind.getKindCode();
			}
		}
		return kindCode;
	}
	/**
	 * 根据prpCitemKindList获得该保单投保的主要险别
	 * @param prpCitemKindList
	 * @return
	 */
	public String getDefaultKindCodeByPolicyDto(List<PrpCitemKind> prpCitemKindList) {
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (PrpCitemKind itemKind : prpCitemKindList) {
				if (itemKind.getId().getItemKindNo() != null && itemKind.getId().getItemKindNo().intValue() == 1) {
					return itemKind.getKindCode();
				}
			}
		}
		return "";
	}
	/**
	 * 填写拒赔页面及查询立案request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIni 取出的初始化信息
	 * @throws Exception
	 */

	public void cancelDtoToView(HttpServletRequest httpServletRequest, UserDto user) throws Exception {
		String claimNo = httpServletRequest.getParameter("businessNo"); // 赔案号
		String nodeType = httpServletRequest.getParameter("nodeType");
		String registNo = httpServletRequest.getParameter("registNo");// 报案号
		String strSql = ""; // 查询条件
		String msg = ""; // 抛出错误使用的消息传递信息
		String cancelReason = httpServletRequest.getParameter("prpLclaimContext");
		PrpLclaim prpLclaim = null; // 立案对象
		ClaimDto claimDto = null;
		// 由於强三的原因，只要立案超过1个的情况的，注销掉部分，则不进行整个流程的关闭，需要选择是申请哪个注销的，我想，一次只能申请一个吧。
		// 所以只能依靠registNo来进行判断了。。。
		if (registNo == null || registNo.equals("")) {
			msg = "不合法的報案號碼！";
			throw new UserException(1, 3, "拒賠", msg);
		}
		// 2.将立案信息取出
		// modify by zhangmaoyu begin reason:判断该立案号不能已经申请注销/拒赔
		// 申请注销拒赔，点击备案号码进入页面的立案信息的查询sql。此处如果立案信息中有已经申请注销拒赔的立案号则不显示。
		strSql = "registNo='" + registNo + "' and endcasedate is null and claimno not in (select keyin from swflog where registNo='" + registNo + "' and nodetype = 'cance' and nodestatus != 3)";
		if (nodeType.equals("compe")) {
			strSql = " claimNo='" + claimNo + "' and endcasedate is null";
		}
		QueryRule queryRule = QueryRule.getInstance();// 获取QueryRule对象的Instance
		queryRule.addSql(strSql);
		List<PrpLclaim> claimList = prpLclaimService.findPrpLclaim(queryRule);
		// 如果没有立案，先提示，立案後在做拒赔以後要自动写信息到立案表里去'
		// 整理立案数据
		// 简易赔案增加注销拒赔申请功能，wuping和李伏强要求没有立案的报案，必须自动立案後，才提注销申请，我不同意这么做，却不得不按諑们规定的任务和想法去编写代码。
		// 1。取得没有立案的报案号，进行自动立案操作 ,因为考虑交强，所以需要在这里用列表过程
		QueryRule queryRuleNo = QueryRule.getInstance();
		queryRuleNo.addEqual("id.registNo", registNo);
		queryRuleNo.addEqual("validStatus", "1");
		queryRuleNo.addEqual("claimNo", "");
		List<Prplregistrpolicy> noClaimList = prpLregistrpolicyService.findPrplregistrpolicy(queryRuleNo);
		// 判断在简易赔案进入的任务中，有没有立案的个数
		if ("quickCase".equals(nodeType) && noClaimList != null && noClaimList.size() > 0) {
			// 逐个立案
			for (int i = 0; i < noClaimList.size(); i++) {
				Prplregistrpolicy prpLregistRpolicy = new Prplregistrpolicy();
				prpLregistRpolicy = (Prplregistrpolicy) ((ArrayList<Prplregistrpolicy>) noClaimList).get(i);
				// 取号，保存
				// 本想借用赵辉的立案保存，发现那边依赖查勘，定损等，直接调用时候，会出现抛数据空等错误，所以暂时用老方法。
				// 产生新的立案数据
				prpLclaim = registDtoToClaimDto(registNo, user);
				// 产生新立案
				String riskCode = prpLregistRpolicy.getRiskCode();
				// String comCode = prpLclaim.getComCode();
				// int year = DateTime.current().getYear();

				// 取号过程还需要进一步完善
				String tableName = "prplclaim";
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("damageCode", prpLclaim.getDamageCode());
				infoMap.put("policyNo", prpLclaim.getPolicyNo());
				claimNo = billService.getNoByPolciyYear(tableName, riskCode, infoMap);
				prpLclaim.setClaimNo(claimNo);
				prpLclaim.setFlag("1"); // 需要插入新的立案数据。
				prpLclaim.setSumClaim(0.0);
				prpLclaim.setRemark("申請簡易賠案註銷引起的自動立案！");
				// 需要很多基础数据的。。。。
				prpLregistRpolicy.setClaimNo(claimNo);
				claimDto = new ClaimDto();
				claimDto.setAutoClaim(true);
				claimDto.setPrpLclaim(prpLclaim);
				claimDto.setPrplregistrpolicy(prpLregistRpolicy);
				PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
				prpLclaimStatus.setStatus("2");
				prpLclaimStatus.getId().setBusinessNo(prpLclaim.getClaimNo());
				prpLclaimStatus.setPolicyNo(prpLclaim.getPolicyNo());
				prpLclaimStatus.getId().setNodeType("claim");
				prpLclaimStatus.getId().setSerialNo(0);
				prpLclaimStatus.setRiskCode(prpLclaim.getRiskCode());
				// 取得当前用户信息，写操作员信息到实赔中
				prpLclaimStatus.setHandlerCode(user.getUserCode());
				prpLclaimStatus.setInputDate(prpLclaim.getInputDate());
				prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				claimDto.setPrpLclaimStatus(prpLclaimStatus);
				// 由於考虑如果申请注销之後，还是要按照简易赔案或者一般赔案来继续走的情况下，是需要处理未立案的工作流数据的，
				// 否则会出现流程在立案处无法进行下去，因为会提示，已经立案，不能再继续立案的情况而导致流程无法继续。
				// 为了解决这个问题，所以要对立案的数据进行补充
				strSql = "registNo='" + registNo + "' and policyNo='" + prpLclaim.getPolicyNo() + "' and nodetype='claim' and nodestatus<4";

				// 检查工作流上是否有这么样的一个节点，如果有的话，那么需要更新和补充这个点的数据.
				List<SwfLog> claimworkFLowList = this.getWorkFlowService().findByConditions(strSql);
				WorkFlowDto workFlowDto = null;
				if (claimworkFLowList != null && claimworkFLowList.size() > 0) {
					SwfLog swfClaimLogDto = claimworkFLowList.get(0);
					workFlowDto = new WorkFlowDto();
					swfClaimLogDto.setBusinessNo(prpLclaim.getClaimNo());
					swfClaimLogDto.setNodeStatus("2");
					// 先把简易赔案的操作人，认为是立案的人，这里不合理，但没办法,原因如上。
					swfClaimLogDto.setHandlerCode(user.getUserCode());
					swfClaimLogDto.setHandleDept(user.getComCode());
					swfClaimLogDto.setHandlerName(user.getUserName() + " 註銷拒賠自動立案");
					workFlowDto.setUpdate(true);
					workFlowDto.setUpdateSwfLog(swfClaimLogDto);
				}
				claimService.save(claimDto, workFlowDto);
			}
			strSql = "registNo='" + registNo + "' and endcasedate is null";
			QueryRule queryRule1 = QueryRule.getInstance();
			queryRule1.addSql(strSql);
			claimList = prpLclaimService.findPrpLclaim(queryRule);
		}
		if ((claimList == null || claimList.size() < 1) && !"quickCase".equals(nodeType)) {
			msg = "案件目前還沒有立案，請先立案後再做註銷和拒賠！";
			throw new UserException(1, 3, "拒賠", msg);
		}
		Iterator<PrpLclaim> it = claimList.iterator();
		if (it.hasNext()) {
			prpLclaim = (PrpLclaim) it.next();
			prpLclaim.setClaimList(claimList);
		}
		// 非车险如果存在实赔或者预赔，案件不允许注销！
		if (!"D".equals(ConstantCodes.carClassMap.get(prpLclaim.getClassCode()))) {
			QueryRule queryRule_c = QueryRule.getInstance();
			queryRule_c.addEqual("claimNo", prpLclaim.getClaimNo()).addEqual("underWriteFlag", "1");
			List<PrpLcompensate> compensateList = prpLcompensateService.findPrpLcompensate(queryRule_c);
			List<PrpLprepay> prepayList = prpLprepayService.findPrpLprepay(queryRule_c);
			if (compensateList.size() > 0 || prepayList.size() > 0) {
				msg = "當前案件已有實賠或者預賠，案件不允許註銷！";
				throw new UserException(1, 3, "拒賠", msg);
			}
		}
		if (prpLclaim == null) {
			msg = "沒有查詢到相關立案！";
			throw new UserException(1, 3, "拒賠", msg);
		}

		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		// 放信息到request里面
		// 设置申请时间
		prpLclaim.setCancelDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
		// 设置申请人
		prpLclaim.setDealerCode(user.getUserCode());
		prpLclaim.setDealerName(user.getUserName());
		prpLclaim.setCancelReason(cancelReason);
		// 设置操作状态为ADD,申请登记，很重要的
		prpLclaim.setEditType("ADD");
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 输入报案号码，默认出立案的必要信息
	 * @param registNo 报案号码
	 * @throws Exception
	 */
	public PrpLclaim registDtoToClaimDto(String registNo, UserDto user) throws Exception {
		// 取得当前用户信息，写操作员信息到立案中
		// 将查勘信息带入到立案中
		CheckDto checkDto = checkService.findByPrimaryKey(registNo);
		PrpLcheck prpLcheck = checkDto.getPrpLcheck();
		List<PrpLltext> list = new ArrayList<PrpLltext>();
		// 根据查询出来的数据内容，给prpLclaim赋值
		PrpLclaim prpLclaim = new PrpLclaim();
		if (prpLcheck == null) // 要提示，立案之前必须做查看的！！！
		{
			// 没有做查勘，但是可以立案
		} else {
			if (checkDto.getPrpLregistTextList() != null) {
				Iterator<PrpLregistText> iterator = checkDto.getPrpLregistTextList().iterator();
				while (iterator.hasNext()) {
					PrpLregistText prpLregistText = (PrpLregistText) iterator.next();
					PrpLltext prpLltext = new PrpLltext();
					prpLltext.setContext(prpLregistText.getContext());
					list.add(prpLltext);
				}
			}
			prpLclaim.setIndemnityDuty(prpLcheck.getIndemnityDuty());
			prpLclaim.setEscapeFlag(prpLcheck.getClaimType());
		}
		// 查询报案信息，並取适当的信息到新登记的立案中
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		PrpLregist prpLregist = registDto.getPrpLregist();
		prpLclaim.setRegistNo(prpLregist.getRegistNo());
		prpLclaim.setPolicyNo(prpLregist.getPolicyNo());
		prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(prpLregist.getDamageCode()).trim());
		prpLclaim.setDamageName(prpLregist.getDamageName());
		prpLclaim.setDamageTypeCode(prpLregist.getDamageTypeCode());
		prpLclaim.setDamageTypeName(prpLregist.getDamageTypeName());
		prpLclaim.setDamageAreaCode(prpLregist.getDamageAreaCode());
		prpLclaim.setDamageAreaName(prpLregist.getDamageAreaName());
		prpLclaim.setDamageAddressType(prpLregist.getDamageAddressType());
		prpLclaim.setDamageAddress(prpLregist.getDamageAddress());
		prpLclaim.setRiskCode(prpLregist.getRiskCode());
		String timeTemp = "";
		timeTemp = StringConvert.toStandardTime(prpLregist.getDamageStartHour());
		prpLclaim.setDamageStartDate(prpLregist.getDamageStartDate());
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageStartMinute(timeTemp.substring(3, 5));
		timeTemp = StringConvert.toStandardTime(prpLregist.getDamageEndHour());
		prpLclaim.setDamageEndDate(prpLregist.getDamageEndDate());
		prpLclaim.setDamageEndHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageEndMinute(timeTemp.substring(3, 5));
		prpLclaim.setClaimDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaim.setInsuredCode(prpLregist.getInsuredCode());
		prpLclaim.setInsuredName(prpLregist.getInsuredName());
		prpLclaim.setClassCode(prpLregist.getClassCode());
		prpLclaim.setComCode(prpLregist.getComCode());
		prpLclaim.setAddressCode(prpLregist.getAddressCode());
		prpLclaim.setLanguage(prpLregist.getLanguage());
		prpLclaim.setLossName(prpLregist.getLossName());
		prpLclaim.setMakeCom(prpLregist.getMakeCom());
		prpLclaim.setHandler1Code(prpLregist.getHandler1Code());
		prpLclaim.setOperatorCode(user.getUserCode());
		// 估损金额的获取，如果是查勘，定损，核损都可以立案，
		prpLclaim.setSumClaim(prpLregist.getEstimateLoss());
		// 设置默认的经办人
		prpLclaim.setHandlerCode(user.getUserCode());
		// 处理机构
		prpLclaim.setHandleDept(user.getComCode());
		// 对车型,条款等信息的支持
		prpLclaim.setClauseType(prpLregist.getClauseType());
		prpLclaim.setClauseName(prpLregist.getClauseName());
		prpLclaim.setLicenseNo(prpLregist.getLicenseNo());
		prpLclaim.setLicenseColorCode(prpLregist.getLicenseColorCode());
		prpLclaim.setLicenseColor(prpLclaim.getLicenseColorCode());
		prpLclaim.setBrandName(prpLregist.getBrandName());
		prpLclaim.setCarKindCode(prpLregist.getCarKindCode());
		prpLclaim.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 设置立案操作的状态为 新案件登记 (未处理任务)
		prpLclaim.setStatus("1");
		// 从保单中获得信息
		if (!prpLregist.getPolicyNo().equals("")) {
			// 查询保单信息
			String policyNo = prpLregist.getPolicyNo();
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			prpLclaim.setPolicyNo(prpCmain.getPolicyNo());
			prpLclaim.setHandler1Code(prpCmain.getHandler1Code());
			prpLclaim.setStartDate(new DateTime(prpCmain.getStartDate().toString(), DateTime.YEAR_TO_DAY));
			prpLclaim.setEndDate(new DateTime(prpCmain.getEndDate().toString(), DateTime.YEAR_TO_DAY));
			prpLclaim.setComCode(prpCmain.getComCode());
			prpLclaim.setInsuredCode(prpCmain.getInsuredCode());
			prpLclaim.setInsuredName(prpCmain.getInsuredName());
			prpLclaim.setSumAmount(prpCmain.getSumAmount());
			prpLclaim.setSumPremium(prpCmain.getSumPremium());
			prpLclaim.setBusinessNature(prpCmain.getBusinessNature());
			prpLclaim.setPolicyType(prpCmain.getPolicyType());
			prpLclaim.setCurrency(prpCmain.getCurrency());
			prpLclaim.setRiskCode(prpCmain.getRiskCode());
			prpLclaim.setAgentCode(prpCmain.getAgentCode());
			String riskCode=prpCmain.getRiskCode();
			prpLclaim.setPolicyInputDate(CommonUtils.getYearToDayStr(prpCmain.getInputDate()));
			if (ConstantCodes.RISKCODE_MC.equals(riskCode)) {
				prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCmain.getStartDate()));
			}
			if(ConstantCodes.RISKCODE_AV.equals(riskCode)){
				PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo , 1));
				if (prpCplane != null) {
					prpLclaim.setMakeDate(prpCplane.getBuildYear());
				}
			}else if(ConstantCodes.RISKCODE_OH.equals(riskCode)||ConstantCodes.RISKCODE_EV.equals(riskCode)||ConstantCodes.RISKCODE_FV.equals(riskCode)||ConstantCodes.RISKCODE_EW.equals(riskCode)||ConstantCodes.RISKCODE_FW.equals(riskCode)){
				PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
				if (prpCitemShip != null) {
					prpLclaim.setMakeDate(prpCitemShip.getMakeYearMonth());
				}
			}
		}
		// 增加的其他信息
		prpLclaim.setLflag("L");
		return prpLclaim;
	}

	/**
	 * 展现拒赔申请的页面信息.就是查询
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIni 取出的初始化信息
	 * @throws Exception
	 */
	public void cancelDtoToCancelView(HttpServletRequest httpServletRequest, UserDto user) throws Exception {
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		String registNo = httpServletRequest.getParameter("RegistNo"); // 赔案号
		String strSql = ""; // 查询条件
		String msg = ""; // 抛出错误使用的消息传递信息
		Collection<PrpLclaim> claimList = new ArrayList<PrpLclaim>();
		PrpLregist prpLregist = null; // 立案对象
		PrpLclaim prpLclaim = null; // 立案对象
		ClaimDto claimDto = null;
		if ((claimNo == null || claimNo.equals(""))) {
			// 产生新的立案数据
			prpLclaim = registDtoToClaimDto(registNo, user);
		} else {
			// 将立案信息取出
			strSql = " claimno='" + claimNo + "'";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strSql);
			claimList = prpLclaimService.findPrpLclaim(queryRule);
			claimDto = claimService.findByPrimaryKey(claimNo);
			Iterator<PrpLclaim> it = claimList.iterator();
			if (it.hasNext())
				prpLclaim = (PrpLclaim) it.next();
		}
		if (prpLclaim == null) {
			msg = "沒有查詢到相關立案！";
			throw new UserException(1, 3, "拒賠", msg);
		}
		QueryRule queryRule_c = QueryRule.getInstance();
		queryRule_c.addEqual("claimNo", claimNo).addIn("underWriteFlag", "1", "3");
		List<PrpLcompensate> compensateList = prpLcompensateService.findPrpLcompensate(queryRule_c);
		QueryRule queryRule_p = QueryRule.getInstance();
		queryRule_p.addEqual("claimNo", claimNo).addEqual("underWriteFlag", "1");
		List<PrpLprepay> prepayList = prpLprepayService.findPrpLprepay(queryRule_p);
		if (compensateList.size() > 0 || prepayList.size() > 0) {
			msg = "當前案件已有實賠或者預賠，案件不允許註銷！";
			throw new UserException(1, 3, "拒賠", msg);
		}
		strSql = "registNo='" + prpLclaim.getRegistNo() + "'";
		prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		// 注销/拒赔/不予立案文件多行列表准备数据
		String tempContext = "";
		// 修改获取立案注销拒赔描述信息
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		queryRule.addEqual("id.textType", "10");
		queryRule.addAscOrder("id.lineNo");
		List<PrpLltext> prpLregistTextList = prpLltextService.findPrpLltext(queryRule);
		for (Iterator<PrpLltext> iter = prpLregistTextList.iterator(); iter.hasNext();) {
			PrpLltext prpLltext = (PrpLltext) iter.next();
			tempContext += prpLltext.getContext();
		}
		// 修改获取立案注销拒赔描述信息
		prpLclaim.setCancelReason(tempContext);
		// 放信息到request里面。。。
		// 转换申请人的姓名
		prpLclaim.setDealerCode(user.getUserCode());
		String dealerCode = prpLclaim.getDealerCode();
		String dealerName = this.codeService.translateUserCode(dealerCode, true);
		prpLclaim.setDealerName(dealerName);
		// 设置操作状态为ADD,申请登记，很重要的
		String typeFlag = httpServletRequest.getParameter("typeFlag");
		String flowInTime = httpServletRequest.getParameter("flowInTime"); // 申请日期

		if (typeFlag != null) {
			prpLclaim.setCaseType(typeFlag);
		}
		if (flowInTime != null) {
			prpLclaim.setCancelDate(new DateTime(flowInTime, DateTime.YEAR_TO_DAY));
		}

		// 如果typeflag,flowinTime有数据，则设置 caseType和canceldate
		prpLclaim.setEditType("EDIT");
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("prpLregist", prpLregist);

	}

	/**
	 * 填写立案页面及查询立案request的生成.
	 * 填写立案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */

	/**
	 * 填写立案页面及查询立案request的生成.
	 * 填写立案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public void claimDtoToView(HttpServletRequest httpServletRequest, String claimNo) throws Exception {
		// 查询立案信息

		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);

		// 给prpLclaim赋值
		if (claimDto.getPrpLclaim() == null) {
			String msg = "案件'" + claimNo + "'無法查詢到！";
			throw new UserException(1, 3, "查詢", msg);
		}
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
		prpLclaim.setDamageStartHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageStartMinute(timeTemp.substring(3, 5));
		timeTemp = StringConvert.toStandardTime(prpLclaim.getDamageEndHour());
		prpLclaim.setDamageEndHour(timeTemp.substring(0, 2));
		prpLclaim.setDamageEndMinute(timeTemp.substring(3, 5));

		// 区分逃逸和全损
		String strTemp = prpLclaim.getEscapeFlag();
		if (strTemp != null && strTemp.length() > 0 && strTemp.substring(0, 1) != null) {
			prpLclaim.setEscapeFlag(strTemp.substring(0, 1));
		} else {
			prpLclaim.setEscapeFlag("");
		}
		if (strTemp != null && strTemp.length() > 1 && strTemp.substring(1, 2) != null) {
			prpLclaim.setEscapeFlag2(strTemp.substring(1, 2));
		} else {
			prpLclaim.setEscapeFlag2("");
		}

		// 设置立案操作的状态为 案件修改 (正处理任务)
		if (claimDto.getPrpLclaimStatus() != null) {
			if (claimDto.getPrpLclaimStatus().getStatus().equals("7"))
				claimDto.getPrpLclaimStatus().setStatus("3");
			prpLclaim.setStatus(claimDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLclaim.setStatus("4");
		}
		if (prpLclaim.getClaimDate() == null || "".equals(prpLclaim.getClaimDate())) {
			prpLclaim.setClaimDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		}
		/*
		 * 从保单中获得信息 原因：因为这些保单信息都已经保存到立案表信息中了，故注掉，但是因为需要转换界面上的
		 * 车辆信息，所以仍需要去取保单和下面的车辆信息这一个过程。
		 */
		double dAmount = 0;
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		// 查询保单信息
		// 根据出险时间还原保单信息
		//PolicyDto policyDto = endorseViewHelper.findForEndorBefore(claimDto.getPrpLclaim().getPolicyNo(), new DateTime(claimDto.getPrpLclaim().getDamageStartDate()).toString(), claimDto.getPrpLclaim().getDamageStartHour());
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		prpLclaim.setPolicyInputDate(CommonUtils.getYearToDayStr(prpCmain.getInputDate()));
		String riskCode= prpLclaim.getRiskCode();
		String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			String sameAddressNo = prpCaddressList.get(0).getSameAddressNo();
			prpLclaim.setSameAddressNo(sameAddressNo);
		}
		if(ConstantCodes.RISKCODE_MC.equals(riskCode)){
			prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCmain.getStartDate()));
		}
		if(ConstantCodes.RISKCODE_AV.equals(riskCode)){
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo , 1));
			if (prpCplane != null) {
				prpLclaim.setMakeDate(prpCplane.getBuildYear());
			}
		}else if(ConstantCodes.RISKCODE_OH.equals(riskCode)||ConstantCodes.RISKCODE_EV.equals(riskCode)||ConstantCodes.RISKCODE_FV.equals(riskCode)||ConstantCodes.RISKCODE_EW.equals(riskCode)||ConstantCodes.RISKCODE_FW.equals(riskCode)){
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo , 1));
			if (prpCitemShip != null) {
				prpLclaim.setMakeDate(prpCitemShip.getMakeYearMonth());
			}
		}
		// 将险别信息压到页面上
		String insuredCode = prpLclaim.getInsuredCode();
		String insuredName = prpLclaim.getInsuredName();
		prpLclaim.setClassCode(prpCmain.getClassCode());
		List<PrpCitemKind> prpCitemKindList = null;
		List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
			httpServletRequest.setAttribute("prpCitemKindForE", prpCitemKindList);
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		httpServletRequest.setAttribute("damageKindList", prpCitemKindList);
		PrpCitemCar PrpCitemCarDto = new PrpCitemCar();
		String defaultKindCode = this.getDefaultKindCodeByPolicyDto(prpCitemKindList);
		httpServletRequest.setAttribute("defaultKindCode", defaultKindCode);
		if(ConstantCodes.CLASSCODE_D.equals(riskType)){
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				// 对车型等信息的支持
				PrpCitemCarDto = (PrpCitemCar) prpCitemCarList.get(0);
				prpLclaim.setClauseType(PrpCitemCarDto.getClauseType());
				prpLclaim.setLicenseNo(PrpCitemCarDto.getLicenseNo());
				prpLclaim.setLicenseColorCode(PrpCitemCarDto.getLicenseColorCode());
				prpLclaim.setLicenseColor(PrpCitemCarDto.getLicenseColorCode());
				prpLclaim.setBrandName(PrpCitemCarDto.getBrandName());
				prpLclaim.setCarKindCode(PrpCitemCarDto.getCarKindCode());
			}
		}
		if (ConstantCodes.CLASSCODE_Z.equals(riskType)) {
			Date liabStartDate = this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo);
			httpServletRequest.setAttribute("liabStartDate", liabStartDate);
		}
		Iterator<PrpCitemKind> it = prpCitemKindList.iterator();
		double deductible = 0.0;
		while (it.hasNext()) {
			PrpCitemKind prpCitemKindDto = (PrpCitemKind) it.next();
			String kindCode = prpCitemKindDto.getKindCode();
			if (ConstantCodes.KINDCODE_D_A.equals(kindCode)) {
				while (it.hasNext()) {
					PrpCitemKind itemKindDto = (PrpCitemKind) it.next();
					if ("M1".equals(itemKindDto.getKindCode())) {
						deductible = itemKindDto.getValue();
						break;
					}
				}
			}
		}
		httpServletRequest.setAttribute("deductible", deductible);
		if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {
			// 添加保单信息 2005-9-26
			// 由於业务对运输方式及工具的存储字段不唯一，所以在此处要做一个处理
			PrpCmainCargo prpCmainCargoDto = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargoDto != null) {
				if (CommonUtils.isEmpty(prpCmainCargoDto.getBlNo())) {
					prpCmainCargoDto.setBlNo(this.codeService.translateCodeCode("ConveyanceType", prpCmainCargoDto.getConveyance(), true));
				}
			}
			httpServletRequest.setAttribute("prpCmainCargoDto", prpCmainCargoDto);
		}
		// 根据业务部门需求，总保额取主险保额+附加险保额 begin
		if(!ConstantCodes.CARGO_RISKCODE.contains(riskCode)){
			Iterator<PrpCitemKind> itera = prpCitemKindList.iterator();
			PrpCitemKind prpCitemKind = null;
			while (itera.hasNext()) {
				prpCitemKind = (PrpCitemKind) itera.next();
				dAmount = dAmount + prpCitemKind.getAmount();
			}
			prpCmain.setSumAmount(dAmount);
		
		// 根据业务部门需求，总保额取主险保额+附加险保额 end
		// 从共、从联保额显示总保额
			if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
				List<PrpCcoins> list2 = (ArrayList<PrpCcoins>) prpCcoinsService.findByConditionsChiefFlag("policyno='" + claimDto.getPrpLclaim().getPolicyNo() + "' and coinsCode='"+ConstantCodes.COMPANYCODE+"'");
				for (Iterator<PrpCcoins> iterator = list2.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoins = iterator.next();
					double sumAmount = 0;
					BigDecimal bigSumAmount = new BigDecimal(new DecimalFormat(".00").format(prpCmain.getSumAmount()));
					BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(prpCcoins.getCoinsRate() / 100));
					sumAmount = bigSumAmount.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
					prpLclaim.setSumAmount(sumAmount);
				}
			} else {
				prpLclaim.setSumAmount(prpCmain.getSumAmount());
			}
		}

		// 设置相关代码的中文转换
		changeCodeToName(prpLclaim);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLclaim);
		// 查询相同保单号的出险次数
		daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLclaim.getPolicyNo(), prpLclaim.getRegistNo());

		// 获取危险单位信息
		com.sinosoft.sysframework.common.datatype.DateTime dateTime = new com.sinosoft.sysframework.common.datatype.DateTime(prpLclaim.getDamageStartDate());
		Collection<?> reinsDangerUnitCollection = reinsServiceManager.getReinsService().getDangerUnit(prpLclaim.getPolicyNo(), dateTime);
		httpServletRequest.setAttribute("ReinsDangerUnitCollection", reinsDangerUnitCollection);
		// 三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "claim";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		// 设置各个子表信息项到窗体表单
		claimDto.setPrpLpersonTraceList(claimDto.getPrpLpersonTraceList());
		setSubInfo(httpServletRequest, claimDto , prpCitemKindList);
		httpServletRequest.setAttribute("prpLacciPerson", claimDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : claimDto.getPrpLacciPerson());
		// 显示立案日期和现在日期之间的工作日天数
		RegistDto registDto = registService.findByPrimaryKey(prpLclaim.getRegistNo());
		PrpLregist prpLregist = registDto.getPrpLregist();
		DateTime registDate = new DateTime(prpLregist.getReportDate());
		// add by zhaolu start at 2006-06-09
		// reason 强制保单关联信息写到立案中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		// add by zhaolu end at 2006-06-09
		httpServletRequest.setAttribute("registDate", registDate);
		// 立案环节增加理赔联系记录
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(claimDto.getPrpLclaim().getRegistNo());
		prpLregistExt.setRiskCode(claimDto.getPrpLclaim().getRiskCode());
		arrayListRegistExt = claimDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);

		// 原因：添加出险人员信息
		if (registDto.getPrpLacciPerson() != null) {
			prpLclaim.setAcciCode(registDto.getPrpLacciPerson().getAcciCode());
			prpLclaim.setAcciName(registDto.getPrpLacciPerson().getAcciName());
			prpLclaim.setSex(registDto.getPrpLacciPerson().getSex());
			prpLclaim.setAge(registDto.getPrpLacciPerson().getAge());
			prpLclaim.setIdentifyNumber(registDto.getPrpLacciPerson().getIdentifyNumber());
			prpLclaim.setFamilyNo(registDto.getPrpLacciPerson().getFamilyNo());
		}

		// 意健险在提交前，所有的申请调查应该已经提交。 2005-08-04
		String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if ("E".equals(strRiskType)) {
			int intCount = 0; // 没有提交的申请调查数目
			String strFlowID = httpServletRequest.getParameter("swfLogFlowID");
			String strSql = " FLOWID='" + strFlowID + "' and NODETYPE='check' and NODESTATUS<>'4'";
			intCount = this.getWorkFlowService().findNodesByConditions(strSql).size();
			httpServletRequest.setAttribute("com_sinosoft_acciFlag", intCount > 0 ? "N" : "Y"); // 设置一个标志位：N表示不能提交，Y表示可以提交。
		}

		httpServletRequest.setAttribute("claimDto", claimDto);

		// 缺省带出报案的币别
		prpLclaim.setEstiCurrency(prpLclaim.getCurrency());
		
		//设置进出口类别
		if(!CommonUtils.isEmpty(prpLclaim.getEndorseNo())) {
			PrpLclaim tempPrpLclaim = claimService.generateCargoInfo(null, prpLclaim.getEndorseNo());
			prpLclaim.setImportType(tempPrpLclaim.getImportType());
		}

		// 进行币别转化，得到中文名称
		String strCurrencyName = this.codeService.translateCurrencyCode(prpLclaim.getEstiCurrency(), true);
		httpServletRequest.setAttribute("strCurrencyName", strCurrencyName);
		// 获取兑换率信息
		UIExchAction uiExchAction = new UIExchAction();
		List<PrpDexchDto> prpDexchList = (List<PrpDexchDto>) uiExchAction.getExchOfMaxDate(DateTime.current().toString().substring(0, 10));

		// reason:签单币别不是CNY时，给出提示，並提供当前兑换率
		PrpDexchDto prpDexch = null;
		String currency = claimDto.getPrpLclaim().getCurrency();
		for(int i=0;i<prpDexchList.size();i++){
			if(prpDexchList.get(i).getBaseCurrency().equals(currency)){
				prpDexch = prpDexchList.get(i);
			}
		}
		httpServletRequest.setAttribute("prpDexch", prpDexch);
		httpServletRequest.setAttribute("prpDexchList", prpDexchList);

		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		//立案|未處理立案任務
		settingPAF4567(httpServletRequest,prpLclaim);
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		
		// 设置主立案信息内容到窗体表单
		if (claimDto.getPrpLext() == null) {
			httpServletRequest.setAttribute("prpLext", new PrpLext());
		} else {
			httpServletRequest.setAttribute("prpLext", claimDto.getPrpLext());
		}
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());

		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLclaim.getRiskCode(), true));

		List<PrpLacciPerson> prpLacciPersonList = claimDto.getPrpLacciPersonList();
		PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
		prpLacciPerson.setPrpLacciPersonList(prpLacciPersonList);
		httpServletRequest.setAttribute("prpLacciPerson", prpLacciPerson);
		
		// 送审初复核初始化
		SendUndwrtViewHelper sendUndwrtViewHelper = new SendUndwrtViewHelper();
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, prpLclaim.getRegistNo(), "claim");
		// 送审初复核初始化
		
		if (claimDto.getPrpLclaimCredit() != null) {
			String bankCode = claimDto.getPrpLclaimCredit().getBankCode();
			if(!CommonUtils.isEmpty(bankCode)){
				httpServletRequest.setAttribute("creditTypeList", this.codeService.findPrpDcodeByConditions(" codeType='CreditType' and validStatus = '1' and codeLevel = '2' and upperCode = '"+ bankCode +"' order by codeCode "));
			}
		}
	}

	/***************************************************************************
	 * 立案查询分页
	 * @param request
	 * @param workFlowQueryDto
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 * @throws Exception
	 */
	public Page setPrpLclaimDtoToView(HttpServletRequest request, WorkFlowQueryDto workFlowQueryDto, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，赔案号生成SQL where 子句
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		// 在查询立案任务中增加"报案号"查询条件
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());

		//身份證字號/統一編碼
		String insuredIdentifyNumberSign = StringUtils.rightTrim(request.getParameter("insuredIdentifyNumberSign"));
		String insuredIdentifyNumber = StringUtils.rightTrim(request.getParameter("insuredIdentifyNumber"));
		//事故日期
		String damageStartDate = StringUtils.rightTrim(request.getParameter("damageStartDate"));
		String damageEndDate = StringUtils.rightTrim(request.getParameter("damageEndDate"));
		
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("prplclaim.claimNo", claimNo, workFlowQueryDto.getClaimNoSign());
		conditions = conditions + StringConvert.convertString("prplclaim.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());
		conditions = conditions + StringConvert.convertString("prplclaim.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		conditions = conditions + StringConvert.convertString("prplclaim.identifyNumber", insuredIdentifyNumber, insuredIdentifyNumberSign);
		conditions = conditions + StringConvert.convertDate("prplclaim.damageStartDate", damageStartDate, "=");
		conditions = conditions + StringConvert.convertDate("prplclaim.damageEndDate", damageEndDate, "=");
		conditions = conditions + StringConvert.convertString("prplregist.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		conditions = conditions + StringConvert.convertString("prplregist.InsuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		// 当查询注销，拒赔数据的时候，没有查询到结果
		// 想法是从status中查询6和7的状态 6-注销， 7-拒赔
		String strTempStatus = "";
		String caseType = "";
		if (CommonUtils.isEmpty(status)) {
			status = "";
		}
		String statusTemp = status;

		if (statusTemp.indexOf("2") >= 0) {
			strTempStatus = strTempStatus + "2,";
		}
		if (statusTemp.indexOf("4") >= 0) {
			strTempStatus = strTempStatus + "4,";
		}
		if (statusTemp.indexOf("6") >= 0) {
			caseType = caseType + "0,";
		}
		if (statusTemp.indexOf("7") >= 0) {
			caseType = caseType + "1,";
		}
		if (statusTemp.indexOf("8") >= 0) {
			caseType = caseType + "2,";
		}
		if (!caseType.equals("")) {
			caseType = caseType.substring(0, caseType.length() - 1);
			conditions = conditions + " AND (prplclaim.caseType in (" + caseType + ")) ";
		}

		if (!strTempStatus.equals("")) {
			strTempStatus = strTempStatus.substring(0, strTempStatus.length() - 1);
			conditions = conditions + " AND (b.status in (" + strTempStatus + ")) ";
		}

		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		// 根据sql字句形成记录
		return claimService.findByQueryConditions(conditions, pageNo, recordPerPage);
	}

	/**
	 * 根据报案号和保单号查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */
	public Page registTimeOut(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，赔案号生成SQL where 子句
//		DateTime.setDateDelimiter("-");
		// 已超时的报案：报案时间-出险时间>＝48小时
//		String classCode = codeService.translateClassCodeByRiskCode(riskCode);
//		if(classCode.equals("M") || classCode.equals("E") || classCode.equals("C")){
//		}else{
//		}
		//'M','E','C' 的30天
		String conditions = "(( classCode in('M','E','C') and ((ReportDate-DamageStartDate)*24+(substr('00' || ReportHour,instr(ReportHour,':'),instr(ReportHour,':')-1 )-substr('00' || DamageStartHour,instr(DamageStartHour,':'),instr(DamageStartHour,':')-1 ))) >= (30*24) )"
		+" or ( classCode not in('M','E','C') and ((ReportDate-DamageStartDate)*24+(substr('00' || ReportHour,instr(ReportHour,':'),instr(ReportHour,':')-1 )-substr('00' || DamageStartHour,instr(DamageStartHour,':'),instr(DamageStartHour,':')-1 ))) >= 24 ))";//其餘的1天
		// 增加查询条件
		String registNo = httpServletRequest.getParameter("RegistNo");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNo = httpServletRequest.getParameter("PolicyNo");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String insuredName = httpServletRequest.getParameter("InsuredName");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		
		String riskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
		String riskCode = httpServletRequest.getParameter("RiskCode");
		// 整理查询条件
		if (registNo != null && !registNo.equals("") || policyNo != null && !policyNo.equals("") || insuredName != null && !insuredName.equals("") || riskCode != null && !riskCode.equals("")) {
			conditions = conditions + " and ( 1=1 ";
			conditions = conditions + StringConvert.convertString("prpLregist.registNo", registNo, registNoSign);
			conditions = conditions + StringConvert.convertString("prpLregist.policyNo", policyNo, policyNoSign);
			conditions = conditions + StringConvert.convertString("prpLregist.insuredName", insuredName, insuredNameSign);
			conditions = conditions + StringConvert.convertString("prpLregist.riskCode", riskCode, riskCodeSign);
			conditions = conditions + ")";
		}

		// 得到多行报案主表信息
		return registService.findByQueryConditions(conditions, pageNo, recordPerPage);
	}

	/**
	 * 立案超时查询
	 * @param httpServletRequest
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 * @throws Exception
	 */
	public Page claimTimeOut(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，赔案号生成SQL where 子句
		String conditions = "";
		DateTime.setDateDelimiter("-");
		// 已超时的报案：立案时间-报案时间>＝168小时(一周)
		conditions = " ((prplclaim.inputDate-prplregist.reportDate)*24+(substr(prplclaim.inputDate,0,2 )-substr(prplregist.ReportHour,0,2 )))>=168";
		// 增加查询条件
		String claimNo = httpServletRequest.getParameter("ClaimNo");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String policyNo = httpServletRequest.getParameter("PolicyNo");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String insuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET);
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String riskCode = httpServletRequest.getParameter("RiskCode");
		String riskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
		String registNo = httpServletRequest.getParameter("RegistNo");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		//事故日期
		String damageStartDate = StringUtils.rightTrim(httpServletRequest.getParameter("damageStartDate"));
		String damageEndDate = StringUtils.rightTrim(httpServletRequest.getParameter("damageEndDate"));
		// 整理查询条件
		if (claimNo != null && !claimNo.equals("") || policyNo != null && !policyNo.equals("") || insuredName != null && !insuredName.equals("") || riskCode != null && !riskCode.equals("") || registNo != null && !registNo.equals("") || damageStartDate != null && !damageStartDate.equals("") || damageEndDate != null && !damageEndDate.equals("")) {
			conditions = conditions + " and ( 1=1 ";
			conditions = conditions + StringConvert.convertString("prplclaim.claimNo", claimNo, claimNoSign);
			conditions = conditions + StringConvert.convertString("prplclaim.policyNo", policyNo, policyNoSign);
			conditions = conditions + StringConvert.convertString("prplclaim.insuredName", insuredName, insuredNameSign);
			conditions = conditions + StringConvert.convertString("prplclaim.riskCode", riskCode, riskCodeSign);
			conditions = conditions + StringConvert.convertString("prplclaim.registNo", registNo, registNoSign);
			conditions = conditions + StringConvert.convertDate("prplclaim.damageStartDate", damageStartDate, "=");
			conditions = conditions + StringConvert.convertDate("prplclaim.damageEndDate", damageEndDate, "=");
			conditions = conditions + ")";
		}

		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();

		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		if (userDto == null) {
		}

		conditions = conditions + uiPowerInterface.addPower(userDto, "prplregist", "", "ComCode");

		conditions = conditions + uiPowerInterface.addPower(userDto, "prplclaim", "", "ComCode");

		// 得到多行报案主表信息
		return claimService.findByQueryConditions(conditions, pageNo, recordPerPage);
	}

	/**
	 * 获取已超时赔付 
	 */
	public Page compeTimeOut(HttpServletRequest httpServletRequest, int pageNo, int pageSize) throws Exception {
		String conditions = "";
		// 已超时的赔案：单证提交n天後没有核赔的案件
		// String timeout = AppConfig.get("sysconst.TIMEOUT_PAID");
		// if ("".equals(DataUtils.nullToEmpty(timeout))) {
		// timeout = "5";
		// }
		String timeoutB = "14";// 强制险单证提交天数,加上周末是14天
		String timeoutA = "15";// 任意险单证提交天数
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// int week = calendar.get(Calendar.DAY_OF_WEEK);//得到今天是星期几
		// if((week-1)==5){//如果是周五，单证提交天数多加一个周末
		// timeoutB = "12";
		// }else{//如果不是周五，单证提交天数多加两个周末
		// timeoutB = "14";
		// }
		DateTime dateTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		StringBuffer sql = new StringBuffer();
		sql.append(" swflog a  ");
		sql.append("where nodetype = 'compe'");
		sql.append("AND (TO_DATE('" + dateTime + "','yyyy-mm-dd HH24:MI:SS')-TO_DATE(a.flowintime, 'yyyy-mm-dd HH24:MI:SS')>= case when a.riskcode = 'A01' then'" + timeoutA + "' when a.riskcode ='B01'then '" + timeoutB + "' end)");
		sql.append("and not exists (select 0 from swflog c  ");
		sql.append("where c.flowid = a.flowid and c.nodetype = 'veric' and c.nodestatus = '4') ");
		conditions = sql.toString();
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		BLUtiUserGradeFacade utiUserGradeFacade = new BLUtiUserGradeFacade();
		String conditions2 = "comCode='" + userDto.getComCode() + "' AND userCode='" + userDto.getUserCode() + "' AND VALIDSTATUS='1' AND gradeCode ='122'";
		// List<UtiUserGradeDto> list = (ArrayList<UtiUserGradeDto>)
		// utiUserGradeFacade.findByConditions(conditions2);
		List<UtiUserGradeDto> list = new ArrayList<UtiUserGradeDto>();
		Collection<?> listTemp = utiUserGradeFacade.findByConditions(conditions2);
		Iterator<?> it = listTemp.iterator();
		while (it.hasNext()) {
			UtiUserGradeDto utiUserGradeDto = (UtiUserGradeDto) it.next();
			list.add(utiUserGradeDto);
		}
		if (list != null && !list.isEmpty()) {// 核赔员
			conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} else {
			conditions = conditions + " AND EXISTS (SELECT 1 FROM swflog s WHERE s.flowid=a.flowid AND s.handlercode='" + userDto.getUserCode() + "')";
		}
		// 查询已超时赔付信息
		return this.getSwfLogService().findTimeOutByConditions(conditions, pageNo, pageSize);
		// PageRecord pageRecord =
		// uiSwfLogAction.findTimeOutByConditions(conditions,
		// String.valueOf(pageNo), String.valueOf(pageSize));
		// return new Page((pageNo - 1) * pageSize, pageRecord.getCount(),
		// pageRecord.getRowsPerPage(), (List) pageRecord.getResult());
	}

	/**
	 * 根据报案号和保单号查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */
	public Page getSpecialList(HttpServletRequest httpServletRequest, String claimNo, String policyNo) throws Exception {
		// 根据输入的保单号，赔案号生成SQL where 子句
		claimNo = StringUtils.rightTrim(claimNo);
		policyNo = StringUtils.rightTrim(policyNo);

		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("BusinessNo", claimNo, httpServletRequest.getParameter("ClaimNoSign"));
		conditions = conditions + StringConvert.convertString("PolicyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		conditions = conditions + "  and SwfLog.nodeType='claim' and SwfLog.NodeStatus='4' and (SwfLog.FlowStatus='1' or SwfLog.FlowStatus='2')";

		// 拼权限
		// modify by zhaolu 20060816 start
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");

		// 查询立案信息
		// 得到多行报案主表信息
		List<SwfLog> swfList = this.getWorkFlowService().findNodesByConditions(conditions);
		SwfLog swfLogDto = new SwfLog();
		swfLogDto.setSwfLogList(swfList);
		httpServletRequest.setAttribute("swfLogDto", swfLogDto);
		return new Page();
	}

	/**
	 * 填写不予立案页面及查询报案request的生成.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIni 取出的初始化信息
	 * @throws Exception
	 */

	public void notGrandClaimDtoToView(HttpServletRequest httpServletRequest, UserDto user) throws Exception {
		String registNo = httpServletRequest.getParameter("RegistNo"); // 报案号

		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo); // 报案对象

		prpLregist.setDealerCode(user.getUserCode());
		prpLregist.setComName(user.getUserName()); // 用部门属性存放操作员名字
		prpLregist.setCancelDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));

		// 设置操作状态为ADD,申请登记，很重要的
		prpLregist.setEditType("ADD");

		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 设置在第一次报案录入立案的时候，将设置默认的估损金额，由於DAA与其它险种的保存内容表不同，所以值也不同
	 * @param httpServletRequest HttpServletRequest
	 * @param claimDto ClaimDto
	 * @throws Exception
	 */

	private void setFirstClaimFeeLoss(HttpServletRequest httpServletRequest, PrpLclaim prpLclaim, String riskType) throws Exception {
		String strCurrencyName = "";
		String strCurrency = "";
		String registNo = prpLclaim.getRegistNo();
		String riskCode = prpLclaim.getRiskCode();
		// 车险
		String configCode = this.codeService.translateRiskCodetoConfigCode(riskCode);
		// 强制险或关联的 强制限额
		double quota_qsP = 0; // 财产
		double quota_qsM = 0; // 医疗
		double quota_qsD = 0; // 死亡
		boolean compelFlag = false;
		String mainPolicyNo = "";
		compelFlag = this.prpLregistrpolicyService.isCompelFlag(registNo); // 是否关联
		if (compelFlag) { // 取强制保单号
			List<Prplregistrpolicy> list = this.prpLregistrpolicyService.findByRegistNo(registNo);
			for (Prplregistrpolicy prpLRegistRPolicy : list) {
				if ("3".equals(prpLRegistRPolicy.getPolicyType())) {
					mainPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
				}
			}
		}
		// 商业保单是否有保三者险
		boolean kindBFlag = false;
//		PolicyDto policyDto = endorseViewHelper.findForEndorBefore(prpLclaim.getPolicyNo());
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
		if (ConstantCodes.CLASSCODE_D.equals(riskType)) {
			List<PrpCitemKind> kindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
			for (Iterator<PrpCitemKind> iter = kindList.iterator(); iter.hasNext();) {
				PrpCitemKind prpCitemKindDto = (PrpCitemKind) iter.next();
				if (ConstantCodes.KINDCODE_D_B.equals(prpCitemKindDto.getKindCode())) {
					kindBFlag = true;
					break;
				}
			}
		}
		// 取强制保单限额
		if ("RISKCODE_DAZ".equals(configCode)) {
			mainPolicyNo = prpLclaim.getPolicyNo();
		}
		if (mainPolicyNo != null && !mainPolicyNo.equals("")) {
			String startDate = new DateTime(prpCmain.getStartDate()).toString();
			UIPrpClimitAction uiPrpClimitAction = new UIPrpClimitAction();
			Collection<PrpClimitDto> limitList = uiPrpClimitAction.findByConditions(" policyNo='" + mainPolicyNo + "'", damageDate, startDate);
			if (limitList != null && limitList.size() > 0) {
				for (Iterator<PrpClimitDto> it = limitList.iterator(); it.hasNext();) {
					PrpClimitDto prpClimitDto = (PrpClimitDto) it.next();
					if (!"4".equals(prpLclaim.getIndemnityDuty())) {
						if ("90".equals(prpClimitDto.getLimitType())) {
							quota_qsD = prpClimitDto.getLimitFee();
						} else if ("91".equals(prpClimitDto.getLimitType())) {
							quota_qsM = prpClimitDto.getLimitFee();
						} else if ("92".equals(prpClimitDto.getLimitType())) {
							quota_qsP = prpClimitDto.getLimitFee();
						}
					} else {
						if ("93".equals(prpClimitDto.getLimitType())) {
							quota_qsD = prpClimitDto.getLimitFee();
						} else if ("94".equals(prpClimitDto.getLimitType())) {
							quota_qsM = prpClimitDto.getLimitFee();
						} else if ("95".equals(prpClimitDto.getLimitType())) {
							quota_qsP = prpClimitDto.getLimitFee();
						}
					}
				}
			}
		} else { // 非关联保单 非强制保单 ,暂时写死
			quota_qsD = 50000;
			quota_qsM = 8000;
			quota_qsP = 2000;
		}
		// 取人伤费用列表
		ArrayList<PrpDpersonFeeCodeRisk> personFeeCodeList = (ArrayList<PrpDpersonFeeCodeRisk>) prpDpersonFeeCodeRiskService.findAllCodeList(prpLclaim.getRiskCode());
		// 车险，应该是险类的判断，不应该是按照具体险种进行判断，所以需要要修改。
		if ("D".equals(riskType) && !prpLclaim.getRiskCode().equals(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ"))) {
			// 估损金额列表
			List<PrpLclaimLoss> claimLossList = new ArrayList<PrpLclaimLoss>();
			// 从报案带过来
			PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
			prpLclaimLoss.setRiskCode(prpLclaim.getRiskCode());
			prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
			strCurrency = prpLclaimLoss.getCurrency();
			strCurrencyName = this.codeService.translateCurrencyCode(strCurrency, true);
			prpLclaimLoss.setCurrencyName(strCurrencyName);
			prpLclaimLoss.setKindLoss(prpLclaim.getSumClaim());
			prpLclaimLoss.setSumClaim(prpLclaim.getSumClaim());
			prpLclaimLoss.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			claimLossList.add(prpLclaimLoss);
			HashMap<String, PrpLclaimLoss> hashMapTemp = new LinkedHashMap<String, PrpLclaimLoss>();
			// 如果已经查勘了，从查勘带估损金额
			CheckDto checkDto = this.checkService.findByPrimaryKey(registNo);
			if (checkDto.getPrpLcheck() != null) {
				List<PrpLcheckLoss> prpLcheckLossList = checkDto.getPrpLcheckLossList();
				if (prpLcheckLossList != null && prpLcheckLossList.size() > 0) {
					List<PrpCitemKind> prpCitemKindList = prpCitemKindService.findByConditions("policyNo = '" + checkDto.getPrpLcheck().getPolicyNo() + "'");
					claimLossList = new ArrayList<PrpLclaimLoss>();
					String[] kindCodeFeeCategory = null;
					hashMapTemp = new HashMap<String, PrpLclaimLoss>();
					for (PrpLcheckLoss prpLcheckLoss : prpLcheckLossList) {
						if (hashMapTemp.containsKey(prpLcheckLoss.getKindCode())) {
							prpLclaimLoss = (PrpLclaimLoss) hashMapTemp.get(prpLcheckLoss.getKindCode());
							prpLclaimLoss.setKindLoss(prpLclaimLoss.getSumClaim() + prpLcheckLoss.getLossFee());
							prpLclaimLoss.setSumClaim(prpLclaimLoss.getSumClaim() + prpLcheckLoss.getLossFee());
						} else {
							prpLclaimLoss = new PrpLclaimLoss();
							kindCodeFeeCategory = this.getFeeCategory(prpCitemKindList, prpLcheckLoss.getKindCode());
							prpLclaimLoss.setKindCode(kindCodeFeeCategory[0]);
							prpLclaimLoss.setFeeCategory(kindCodeFeeCategory[1]);
							prpLclaimLoss.setKindName(this.codeService.translateKindCode(prpLcheckLoss.getRiskCode(), prpLclaimLoss.getKindCode(), true));
							prpLclaimLoss.setKindLoss(prpLcheckLoss.getLossFee());
							prpLclaimLoss.setSumClaim(prpLcheckLoss.getLossFee());
							prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
							prpLclaimLoss.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaim.getCurrency(), true));
						}
						hashMapTemp.put(prpLcheckLoss.getKindCode(), prpLclaimLoss);
					}
				}
			}
			// 立案条件为报案後48小时可立案。立案的估损金额取值方法：当报案後未查勘的案件，估损金额需要立案人员根据报案情况输入；已查勘未定损案件取查勘估损金额；已定损案件取定损金额；已核损通过取核损金额；立案後，查勘/定损/核损/金额/
			CertainLossDto certainLossDto = this.certainLossService.findByPrimaryKey(registNo);
			String feeCategory = "";
			// 如果已经定损了，从定损带估损金额
			List<PrpLverifyLoss> prpLverifyLossList = certainLossDto.getPrpLverifyLossList();
			if (prpLverifyLossList != null && prpLverifyLossList.size() > 0) {
				hashMapTemp = new HashMap<String, PrpLclaimLoss>();
				// 修理
				List<PrpLrepairFee> prpLrepairFeeList = certainLossDto.getPrpLrepairFeeList();
				if (prpLrepairFeeList != null && prpLrepairFeeList.size() > 0) {
					for (PrpLrepairFee prpLrepairFee : prpLrepairFeeList) {
						// BZ不带入到估损里面
						PrpLrepairFee tempPrpLrepairFee = prpLrepairFee;
						if (prpLrepairFee.getKindCode().equals(ConstantCodes.KINDCODE_D_B) || prpLrepairFee.getKindCode().equals(ConstantCodes.KINDCODE_D_BZ)) {
							tempPrpLrepairFee.setKindCode("BKIND");
							tempPrpLrepairFee.setKindName("三者");
						} else {
							tempPrpLrepairFee.setKindCode(prpLrepairFee.getKindCode());
							tempPrpLrepairFee.setKindName(this.codeService.translateKindCode(prpLrepairFee.getRiskCode(), tempPrpLrepairFee.getKindCode(), true));
						}
						feeCategory = "C";
						if (hashMapTemp.containsKey(feeCategory + tempPrpLrepairFee.getKindCode())) {
							prpLclaimLoss = hashMapTemp.get(feeCategory + tempPrpLrepairFee.getKindCode());
							prpLclaimLoss.setKindLoss(prpLclaimLoss.getSumClaim() + tempPrpLrepairFee.getSumDefLoss());
							prpLclaimLoss.setSumClaim(prpLclaimLoss.getSumClaim() + tempPrpLrepairFee.getSumDefLoss());
						} else {
							prpLclaimLoss = new PrpLclaimLoss();
							prpLclaimLoss.setKindCode(tempPrpLrepairFee.getKindCode());
							prpLclaimLoss.setKindName(tempPrpLrepairFee.getKindName());
							prpLclaimLoss.setFeeCategory(feeCategory);
							prpLclaimLoss.setKindLoss(tempPrpLrepairFee.getSumDefLoss());
							prpLclaimLoss.setSumClaim(tempPrpLrepairFee.getSumDefLoss());
							prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
							prpLclaimLoss.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaim.getCurrency(), true));
						}
						hashMapTemp.put(feeCategory + tempPrpLrepairFee.getKindCode(), prpLclaimLoss);
					}
				}
				// 换件
				List<PrpLcomponent> prpLcomponentList = certainLossDto.getPrpLcomponentList();
				if (prpLcomponentList != null && prpLcomponentList.size() > 0) {
					for (PrpLcomponent prpLcomponent : prpLcomponentList) {
						PrpLcomponent tempPrpLcomponent = prpLcomponent;
						if (prpLcomponent.getKindCode().equals(ConstantCodes.KINDCODE_D_B) || prpLcomponent.getKindCode().equals(ConstantCodes.KINDCODE_D_BZ)) {
							tempPrpLcomponent.setKindCode("BKIND");
							tempPrpLcomponent.setKindName("三者");
						} else {
							tempPrpLcomponent.setKindCode(prpLcomponent.getKindCode());
							tempPrpLcomponent.setKindName(this.codeService.translateKindCode(prpLcomponent.getRiskCode(), tempPrpLcomponent.getKindCode(), true));

						}
						feeCategory = "C";
						if (hashMapTemp.containsKey(feeCategory + tempPrpLcomponent.getKindCode())) {
							prpLclaimLoss = hashMapTemp.get(feeCategory + tempPrpLcomponent.getKindCode());
							prpLclaimLoss.setKindLoss(prpLclaimLoss.getSumClaim() + tempPrpLcomponent.getSumDefLoss());
							prpLclaimLoss.setSumClaim(prpLclaimLoss.getSumClaim() + tempPrpLcomponent.getSumDefLoss());
						} else {
							prpLclaimLoss = new PrpLclaimLoss();
							prpLclaimLoss.setKindCode(tempPrpLcomponent.getKindCode());
							prpLclaimLoss.setKindName(tempPrpLcomponent.getKindName());
							prpLclaimLoss.setFeeCategory(feeCategory);
							prpLclaimLoss.setKindLoss(tempPrpLcomponent.getSumDefLoss());
							prpLclaimLoss.setSumClaim(tempPrpLcomponent.getSumDefLoss());
							prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
							prpLclaimLoss.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaim.getCurrency(), true));
						}
						hashMapTemp.put(feeCategory + tempPrpLcomponent.getKindCode(), prpLclaimLoss);
					}
				}
				// 人员
				List<PrpLperson> prpLpersonList = certainLossDto.getPrpLpersonList();
				if (prpLpersonList != null && prpLpersonList.size() > 0) {
					for (PrpLperson prpLperson : prpLpersonList) {
						PrpLperson tempPrpLperson = prpLperson;
						if (ConstantCodes.KINDCODE_D_B.equals(prpLperson.getKindCode()) || ConstantCodes.KINDCODE_D_BZ.equals(prpLperson.getKindCode())) {
							tempPrpLperson.setKindCode("BKIND");
							tempPrpLperson.setKindName("三者");
						} else {
							tempPrpLperson.setKindCode(prpLperson.getKindCode());
							tempPrpLperson.setKindName(this.codeService.translateKindCode(prpLperson.getRiskCode(), tempPrpLperson.getKindCode(), true));
						}
						for (Iterator<PrpDpersonFeeCodeRisk> iter = personFeeCodeList.iterator(); iter.hasNext();) {
							PrpDpersonFeeCodeRisk personFeeCode = (PrpDpersonFeeCodeRisk) iter.next();
							if (personFeeCode.getId().getFeeCode().equals(prpLperson.getFeeTypeCode())) {
								feeCategory = personFeeCode.getFeeCategory();
								break;
							}
						}
						if (hashMapTemp.containsKey(feeCategory + tempPrpLperson.getKindCode())) {
							prpLclaimLoss = hashMapTemp.get(feeCategory + tempPrpLperson.getKindCode());
							prpLclaimLoss.setKindLoss(prpLclaimLoss.getSumClaim() + tempPrpLperson.getSumDefLoss());
							prpLclaimLoss.setSumClaim(prpLclaimLoss.getSumClaim() + tempPrpLperson.getSumDefLoss());
						} else {
							prpLclaimLoss = new PrpLclaimLoss();
							prpLclaimLoss.setKindCode(tempPrpLperson.getKindCode());
							prpLclaimLoss.setKindName(tempPrpLperson.getKindName());
							prpLclaimLoss.setFeeCategory(feeCategory);
							prpLclaimLoss.setKindLoss(tempPrpLperson.getSumDefLoss());
							prpLclaimLoss.setSumClaim(tempPrpLperson.getSumDefLoss());
							prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
							prpLclaimLoss.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaim.getCurrency(), true));
						}
						hashMapTemp.put(feeCategory + tempPrpLperson.getKindCode(), prpLclaimLoss);
					}
				}
				// 财产
				List<PrpLprop> prpLpropList = certainLossDto.getPrpLpropList();
				if (prpLpropList != null && prpLpropList.size() > 0) {
					for (PrpLprop prpLprop : prpLpropList) {
						PrpLprop tempPrpLprop = prpLprop;
						if (ConstantCodes.KINDCODE_D_B.equals(prpLprop.getKindCode()) || ConstantCodes.KINDCODE_D_BZ.equals(prpLprop.getKindCode())) {
							tempPrpLprop.setKindCode("BKIND");
							tempPrpLprop.setKindName("三者");
						} else {
							tempPrpLprop.setKindCode(prpLprop.getKindCode());
							tempPrpLprop.setKindName(this.codeService.translateKindCode(prpLprop.getRiskCode(), tempPrpLprop.getKindCode(), true));
						}
						feeCategory = "G";
						if (hashMapTemp.containsKey(feeCategory + tempPrpLprop.getKindCode())) {
							prpLclaimLoss = hashMapTemp.get(feeCategory + tempPrpLprop.getKindCode());
							prpLclaimLoss.setKindLoss(prpLclaimLoss.getSumClaim() + tempPrpLprop.getSumDefLoss());
							prpLclaimLoss.setSumClaim(prpLclaimLoss.getSumClaim() + tempPrpLprop.getSumDefLoss());
						} else {
							prpLclaimLoss = new PrpLclaimLoss();
							prpLclaimLoss.setKindCode(tempPrpLprop.getKindCode());
							prpLclaimLoss.setKindName(tempPrpLprop.getKindName());
							prpLclaimLoss.setFeeCategory(feeCategory);
							prpLclaimLoss.setKindLoss(tempPrpLprop.getSumDefLoss());
							prpLclaimLoss.setSumClaim(tempPrpLprop.getSumDefLoss());
							prpLclaimLoss.setCurrency(prpLclaim.getCurrency());
							prpLclaimLoss.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaim.getCurrency(), true));
						}
						hashMapTemp.put(feeCategory + tempPrpLprop.getKindCode(), prpLclaimLoss);
					}
				}
			}
			String kindCode = "";
			String feeCategoryB = "";
			double kindLoss = 0.00;
			double kindLossP = 0;
			// 把hashmap里面的值取出来
			for (PrpLclaimLoss prpLclaimLossTemp : hashMapTemp.values()) {
				if ("BKIND".equals(kindCode) && "G".equals(feeCategoryB) || "C".equals(feeCategoryB)) {
					kindLossP += prpLclaimLossTemp.getKindLoss();
				}
				kindCode = prpLclaimLossTemp.getKindCode();
				String strConfigCode = this.codeService.translateRiskCodetoConfigCode(riskCode);
				if ("RISKCODE_DAZ".equals(strConfigCode)) { // 强制的估损只取强三险别的，並根据强三限额计算上报估损金额
					if (kindCode.equals("BKIND")) {
						prpLclaimLossTemp.setKindCode(ConstantCodes.KINDCODE_D_BZ);
						prpLclaimLossTemp.setKindName(this.codeService.translateKindCode(prpLclaim.getRiskCode(), ConstantCodes.KINDCODE_D_BZ, true));
						kindLoss = prpLclaimLossTemp.getKindLoss();
						feeCategoryB = prpLclaimLossTemp.getFeeCategory();
						if (feeCategoryB.equals("G")) {
							if (kindLoss > quota_qsP) {
								prpLclaimLossTemp.setKindLoss(quota_qsP);
								quota_qsP = 0;
							} else {
								prpLclaimLossTemp.setKindLoss(kindLoss);
								quota_qsP -= kindLoss;
							}
						} else if (feeCategoryB.equals("C")) {
							if (kindLoss > quota_qsP) {
								prpLclaimLossTemp.setKindLoss(quota_qsP);
								quota_qsP = 0;
							} else {
								prpLclaimLossTemp.setKindLoss(kindLoss);
								quota_qsP -= kindLoss;
							}
						} else if (feeCategoryB.equals("M")) {
							if (kindLoss > quota_qsM) {
								prpLclaimLossTemp.setKindLoss(quota_qsM);
							}
						} else if (feeCategoryB.equals("D")) {
							if (kindLoss > quota_qsD) {
								prpLclaimLossTemp.setKindLoss(quota_qsD);
							}
						}
						claimLossList.add(prpLclaimLossTemp);
					} else {
						continue;
					}
				} else { // 商业的除三者之外其他不变
					if ("BKIND".equals(kindCode)) {
						if (kindBFlag) {
							prpLclaimLossTemp.setKindCode(ConstantCodes.KINDCODE_D_B);
							prpLclaimLossTemp.setKindName(this.codeService.translateKindCode(prpLclaim.getRiskCode(), ConstantCodes.KINDCODE_D_B, true));
							kindLoss = prpLclaimLossTemp.getKindLoss();
							feeCategoryB = prpLclaimLossTemp.getFeeCategory();
							if (feeCategoryB.equals("G")) {
								if (kindLoss > quota_qsP) {
									prpLclaimLossTemp.setKindLoss(kindLoss - quota_qsP);
									quota_qsP = 0;
								} else {
									prpLclaimLossTemp.setKindLoss(0.00);
								}
							} else if (feeCategoryB.equals("C")) {
								if (kindLoss > quota_qsP) {
									prpLclaimLossTemp.setKindLoss(kindLoss - quota_qsP);
									quota_qsP = 0;
								} else {
									prpLclaimLossTemp.setKindLoss(0.00);
								}
							} else if (feeCategoryB.equals("M")) {
								if (kindLoss > quota_qsM) {
									prpLclaimLossTemp.setKindLoss(kindLoss - quota_qsM);
								} else {
									prpLclaimLossTemp.setKindLoss(0.00);
								}
							} else if (feeCategoryB.equals("D")) {
								if (kindLoss > quota_qsD) {
									prpLclaimLossTemp.setKindLoss(kindLoss - quota_qsD);
								} else {
									prpLclaimLossTemp.setKindLoss(0.00);
								}
							}
						} else { // 商业没保三者
							continue;
						}
					}
					claimLossList.add(prpLclaimLossTemp);
				}
			}
			prpLclaimLoss.setClaimLossList(claimLossList);
			httpServletRequest.setAttribute("prpLclaimLoss", prpLclaimLoss);
		} else {
			List<PrpLclaimFee> claimFeeList = new ArrayList<PrpLclaimFee>();
			PrpLclaimFee prpLclaimFee = new PrpLclaimFee();
			prpLclaimFee.setRiskCode(prpLclaim.getRiskCode());
			prpLclaimFee.setSumClaim(prpLclaim.getSumClaim());
			prpLclaimFee.getId().setCurrency(prpLclaim.getCurrency());
			strCurrency = prpLclaimFee.getId().getCurrency();
			strCurrencyName = this.codeService.translateCurrencyCode(strCurrency, true);
			prpLclaimFee.setCurrencyName(strCurrencyName);
			claimFeeList.add(prpLclaimFee);
			prpLclaimFee.setClaimFeeList(claimFeeList);
			httpServletRequest.setAttribute("prpLclaimFee", prpLclaimFee);
		}
	}

	public String getLossClaimNo(String registNo) throws Exception {
		String claimNo = "";
		claimNo = this.codeService.translateBusinessCode(registNo, true);
		if (claimNo == null)
			claimNo = "";
		return claimNo;
	}

	/**
	 * 保存立案注销时立案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return claimDto 立案数据传输数据结构
	 * @throws Exception
	 */
	public ClaimDto cancelViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对claim,claimsText表的赋值
		ClaimDto claimDto = new ClaimDto();
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String claimNo = "";
		String caseType = "";
		claimNo = (String) httpServletRequest.getParameter("prpLclaimClaimNo");
		caseType = httpServletRequest.getParameter("caseType");
		/*---------------------立案主表prpLclaim------------------------------------*/
		PrpLclaim prpLclaim = null;
		if ((claimNo == null) || claimNo.equals("")) {
			String msg = " 此案件沒有立案，可以通知報案人員進行報案註銷操作！";
			if ("1".equals(caseType)) {
				msg = " 此案件沒有立案，需要通知立案人員先進行立案後，再申請！";
			}
			throw new UserException(1, 3, "註銷/拒賠", msg);
		}

		claimDtoToView(httpServletRequest, claimNo);
		prpLclaim = (PrpLclaim) httpServletRequest.getAttribute("prpLclaim");
		if (prpLclaim == null) {
			String msg = "沒有查詢到相關立案！";
			throw new UserException(1, 3, "拒賠", msg);
		}
		prpLclaim.setFlag("0"); // 只update就可以了。。
		// 设置第一次进入时的默认信息
		prpLclaim.setDealerCode(user.getUserCode());
		prpLclaim.setDealerName(user.getUserName());
		prpLclaim.setCancelReason("");
		// 用工作流的
		claimDto.setPrpLclaim(prpLclaim);
		/*---------------------立案注销文本表prpLltext------------------------------------*/
		ArrayList<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		PrpLltext prpLltext = null;
		String TextTemp = httpServletRequest.getParameter("prpLclaimContext");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			prpLltext = new PrpLltext();
			prpLltext.getId().setClaimNo(claimNo);
			prpLltext.setContext(rules[k]);
			prpLltext.getId().setLineNo(k + 1);
			prpLltext.getId().setTextType("10");
			prpLltextList.add(prpLltext);
		}
		// 装入ClaimDto
		claimDto.setPrpLltextList(prpLltextList);
		/*---------------------立案操作状态内容prpLclaimStatus默认为完成------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus("4");
		prpLclaimStatus.getId().setBusinessNo(prpLclaim.getClaimNo());
		prpLclaimStatus.setPolicyNo(prpLclaim.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("claim");
		prpLclaimStatus.getId().setSerialNo(0);// 默认值为0--liudaoping
		// 取得当前用户信息，写操作员信息到实际claimstatus中
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLclaim.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaimStatus.setRiskCode(prpLclaim.getRiskCode());
		claimDto.setPrpLclaimStatus(prpLclaimStatus);
		return claimDto;
	}
	/**
	 * 判断是否承保了这个险别
	 * @param prpCitemKindList 保单承保的险别，
	 * @param kindCode 传人的险别，
	 * @return 传出的范围，车损，物损，医疗,不存在这个险别，为""
	 * @throws Exception
	 */
	public String[] getFeeCategory(List<PrpCitemKind> prpCitemKindList, String kindCode) throws Exception {
		String feeCategory = "";
		boolean flag = false;
		String c_kindCode = "";
		if (prpCitemKindList != null && prpCitemKindList.size() > 0) {
			PrpCitemKind prpCitemKind = null;
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				prpCitemKind = prpCitemKindList.get(i);
				if (prpCitemKind.getKindCode().equals(kindCode)) {
					flag = true;
					break;
				}
				if ("".equals(c_kindCode)) {
					if (ConstantsCollection.MainCarLoss.contains(prpCitemKind.getKindCode())) {
						c_kindCode = prpCitemKind.getKindCode();
					} else if (ConstantsCollection.MainPersonLoss.contains(prpCitemKind.getKindCode()) || ConstantsCollection.InsAnddriver.contains(prpCitemKind.getKindCode()) || ConstantsCollection.MainPropLoss.contains(prpCitemKind.getKindCode())) {
						// :(包括人车物)，
						c_kindCode = prpCitemKind.getKindCode();
					} else if (ConstantsCollection.ThirdCarLoss.contains(prpCitemKind.getKindCode())) {
						// :三者车
						c_kindCode = prpCitemKind.getKindCode();
					}
				}
			}
			if (!flag) {
				if (!"".equals(c_kindCode)) {
					kindCode = c_kindCode;
				} else {
					kindCode = "";
				}
			}
			if (ConstantsCollection.MainCarLoss.contains(kindCode)) {
				feeCategory = "C";
			} else if (ConstantsCollection.MainPersonLoss.contains(prpCitemKind.getKindCode()) || ConstantsCollection.InsAnddriver.contains(prpCitemKind.getKindCode()) || ConstantsCollection.MainPropLoss.contains(prpCitemKind.getKindCode())) {// :(包括人车物)，
				feeCategory = "C";
			} else if (ConstantsCollection.ThirdCarLoss.contains(kindCode)) {
				// :三者车
				feeCategory = "C";
			} else if (ConstantsCollection.ThirdPersonLoss.contains(kindCode)) {
				// :三者物，
				feeCategory = "G";
			} else if (ConstantsCollection.ThirdPersonLoss.contains(kindCode) || ConstantsCollection.InsAnddriver.contains(kindCode)) {// :三者人，
				feeCategory = "M";
			} else {
				feeCategory = "O";
			}
		} else {
			kindCode = "";
		}
		return new String[] { kindCode, feeCategory };
	}
	
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
	public Page claimEditCase(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
			// where 子句
			String conditions = "";
			// 增加查询条件
			String claimNo = httpServletRequest.getParameter("ClaimNo");
			String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
			String policyNo = httpServletRequest.getParameter("PolicyNo");
			String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
			String registNo = httpServletRequest.getParameter("RegistNo");
			String registNoSign = httpServletRequest.getParameter("RegistNoSign");
			String licenseNo = httpServletRequest.getParameter("licenseNo");
			String licenseNoSign = httpServletRequest.getParameter("licenseNoSign");
			// 整理查询条件
			if (claimNo != null && !claimNo.equals("") || policyNo != null && !policyNo.equals("") || registNo != null && !registNo.equals("") || licenseNo != null && !licenseNo.equals("")) {
				conditions = conditions + " where ( 1=1 ";
				conditions = conditions + StringConvert.convertString("prplclaim.claimNo", claimNo, claimNoSign);
				conditions = conditions + StringConvert.convertString("prplclaim.policyNo", policyNo, policyNoSign);
				conditions = conditions + StringConvert.convertString("prplclaim.registNo", registNo, registNoSign);
				conditions = conditions + StringConvert.convertString("prplclaim.riskCode", licenseNo, licenseNoSign);
				conditions = conditions + ")";
			}

			return claimService.findBySpecialEditConditions(conditions, pageNo, recordPerPage);
	}
	// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End

	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	public void settingPAF4567(HttpServletRequest httpServletRequest,PrpLclaim prpLclaim) throws Exception{
		//立案任務
		Double PAF7_AMOUNT = 0.0;
		Double PAF456_SUMLOSS=0.0;
		if(prpLclaim.getRiskCode().equals("PA")){
			System.out.println("CLM0231 立案--------START");
			String conditions_forClaimHis = "policyNo = '"+prpLclaim.getPolicyNo()+"' ";
			Page prpLclaimHis =  this.prpLclaimService.findByConditions(conditions_forClaimHis, 0, 99);
			List<PrpLclaim> claimHitList = prpLclaimHis.getResult();
			List<PrpCitemKind> prpCitemKindListPaf7 = this.endorseViewHelper.findPrpCitemKind(prpLclaim.getPolicyNo(), "PAF7");
			for(PrpCitemKind paf7:prpCitemKindListPaf7){
				PAF7_AMOUNT = paf7.getAmount();//PAF7 來源為保單
			}
			for(PrpLclaim claimHit:claimHitList){
				String conditions_forCompensateHis = "compensateNo like 'C"+claimHit.getClaimNo()+"%' AND (underWriteFlag =1 OR underWriteFlag =3) order by compensateNo ";
				List<PrpLcompensate> PrpLcompensateHisList = this.prpLcompensateService.findByConditions(conditions_forCompensateHis);
				for(PrpLcompensate prpLcompensateHit:PrpLcompensateHisList){
					System.out.println(prpLcompensateHit.getCompensateNo());

					QueryRule queryRulePerson = QueryRule.getInstance();
					queryRulePerson.addEqual("id.compensateNo", prpLcompensateHit.getCompensateNo());//立案
//					queryRulePerson.addEqual("policyNo", prpLcompensateHit.getPolicyNo());
					queryRulePerson.addAscOrder("personNo");
					queryRulePerson.addAscOrder("id.serialNo");
					List<PrpLpersonLoss> prplPersonLossList = this.prpLpersonLossService.findPrpLpersonLoss(queryRulePerson);
					for(PrpLpersonLoss plpl:prplPersonLossList){
						if(plpl.getKindCode().equals("PAF4") || plpl.getKindCode().equals("PAF5") || plpl.getKindCode().equals("PAF6")){
							PAF456_SUMLOSS+=plpl.getSumLoss();
							System.out.println(plpl.getKindCode()+"_SUMLOSS:"+plpl.getSumLoss()+"/sum:"+PAF456_SUMLOSS);
						}
					}
				}
			}
			System.out.println(prpLclaim.getPolicyNo()+"_PAF7_AMOUNT:"+PAF7_AMOUNT);
			httpServletRequest.setAttribute("PAF456_SUMLOSS", PAF456_SUMLOSS);
			httpServletRequest.setAttribute("PAF7_AMOUNT", PAF7_AMOUNT);
			System.out.println("CLM0231 立案--------END");
		}
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prplregistrpolicyService) {
		this.prpLregistrpolicyService = prplregistrpolicyService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

//	public ClaimDto getClaimDto() {
//		return claimDto;
//	}
//
//	public void setClaimDto(ClaimDto claimDto) {
//		this.claimDto = claimDto;
//	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public PrpLltextService getPrpLltextService() {
		return prpLltextService;
	}

	public void setPrpLltextService(PrpLltextService prpLltextService) {
		this.prpLltextService = prpLltextService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDpersonFeeCodeRiskService getPrpDpersonFeeCodeRiskService() {
		return prpDpersonFeeCodeRiskService;
	}

	public void setPrpDpersonFeeCodeRiskService(PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService) {
		this.prpDpersonFeeCodeRiskService = prpDpersonFeeCodeRiskService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpCCargoItemService getPrpCCargoItemService() {
		return prpCCargoItemService;
	}

	public void setPrpCCargoItemService(PrpCCargoItemService prpCCargoItemService) {
		this.prpCCargoItemService = prpCCargoItemService;
	}

	public PrpLclauseService getPrpLclauseService() {
		return prpLclauseService;
	}

	public void setPrpLclauseService(PrpLclauseService prpLclauseService) {
		this.prpLclauseService = prpLclauseService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCplaneService getPrpCplaneService() {
		return prpCplaneService;
	}

	public void setPrpCplaneService(PrpCplaneService prpCplaneService) {
		this.prpCplaneService = prpCplaneService;
	}

	public PrpCitemShipService getPrpCitemShipService() {
		return prpCitemShipService;
	}

	public void setPrpCitemShipService(PrpCitemShipService prpCitemShipService) {
		this.prpCitemShipService = prpCitemShipService;
	}

	public PrpCmainLiabService getPrpCmainLiabService() {
		return prpCmainLiabService;
	}

	public void setPrpCmainLiabService(PrpCmainLiabService prpCmainLiabService) {
		this.prpCmainLiabService = prpCmainLiabService;
	}

	public PrpCmainCargoService getPrpCmainCargoService() {
		return prpCmainCargoService;
	}

	public void setPrpCmainCargoService(PrpCmainCargoService prpCmainCargoService) {
		this.prpCmainCargoService = prpCmainCargoService;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
}