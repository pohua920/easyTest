package com.sinosoft.claim.claim.util;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import org.apache.commons.lang.NumberUtils;

import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;

public class ClaimViewHelper {
	/** 立案文本表信息每行显示的最多字符长度 */
	private int RULE_LENGTH = 70;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 批单ViewHelper */
	private EndorseViewHelper endorseViewHelper;
	private PrpCplaneService prpCplaneService;
	private PrpCitemShipService prpCitemShipService;
	private CodeService codeService;
	/**
	 * 保存立案时页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return claimDto 查勘数据传输数据结构
	 * @throws Exception
	 */
	public ClaimDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		//String buttonSaveTypeForClaim = httpServletRequest.getParameter("buttonSaveType").trim();
		// 取得当前用户信息，写操作员信息到实赔中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		ClaimDto claimDto = new ClaimDto();
		String classCode = httpServletRequest.getParameter("prpLclaimClassCode");
		/*---------------------立案主表prpLclaim------------------------------------*/
		PrpLclaim prpLclaim = null;
		prpLclaim = (PrpLclaim) httpServletRequest.getAttribute("prpLclaim");
		if (prpLclaim == null) {
			prpLclaim = new PrpLclaim();
			prpLclaim.setPolicyNo(httpServletRequest.getParameter("prpLclaimPolicyNo"));
			String prpLclaimOperatorCode = httpServletRequest.getParameter("prpLclaimOperatorCode");
			prpLclaim.setOperatorCode(prpLclaimOperatorCode);
			prpLclaim.setReceiptDate(httpServletRequest.getParameter("prpLclaimReceiptDate"));
		}
		// 加到ArrayList中
		prpLclaim.setClaimNo((String) httpServletRequest.getAttribute("claimNo"));
		prpLclaim.setLflag(httpServletRequest.getParameter("lflag"));
		prpLclaim.setCaseNo(httpServletRequest.getParameter("prpLclaimCaseNo"));
		prpLclaim.setClassCode(classCode);
		prpLclaim.setRiskCode(httpServletRequest.getParameter("prpLclaimRiskCode"));
		prpLclaim.setRegistNo(httpServletRequest.getParameter("prpLclaimRegistNo"));
		prpLclaim.setPolicyNo(prpLclaim.getPolicyNo());
		prpLclaim.setBusinessNature(httpServletRequest.getParameter("prpLclaimBusinessNature"));
		prpLclaim.setLanguage(httpServletRequest.getParameter("prpLclaimLanguage"));
		prpLclaim.setPolicyType(httpServletRequest.getParameter("prpLclaimPolicyType"));
		prpLclaim.setInsuredCode(httpServletRequest.getParameter("prpLregistInsuredCode"));
		prpLclaim.setInsuredName(httpServletRequest.getParameter("prpLclaimInsuredName"));
		// 增加对是否涉及担保标志，涉诉标志 begin
		prpLclaim.setGuaranteeFlag(httpServletRequest.getParameter("guaranteeFlag"));
		prpLclaim.setReferLawFlag(httpServletRequest.getParameter("referLawFlag"));
		prpLclaim.setChargeType(httpServletRequest.getParameter("chargeType"));
		prpLclaim.setCoinsFlag(httpServletRequest.getParameter("prpLclaimCoinsFlag"));
		prpLclaim.setStartDate(new DateTime(httpServletRequest.getParameter("prpLclaimStartDate"), DateTime.YEAR_TO_DAY));
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(httpServletRequest.getParameter("prpLclaimDamageStartDate")).toString();
		String damageHour = httpServletRequest.getParameter("prpLclaimDamageStartHour");
		PrpCmain prpCmain = getEndorseViewHelper().findPrpCmain(policyNo, damageDate, damageHour);
		if (prpCmain != null) {
			prpLclaim.setStartHour(prpCmain.getStartHour());
			prpLclaim.setEndHour(prpCmain.getEndHour());
		} else {
			prpLclaim.setStartHour(0);
			prpLclaim.setEndHour(24);
		}
		String riskCode=prpLclaim.getRiskCode();
		prpLclaim.setPolicyInputDate(CommonUtils.getYearToDayStr(prpCmain.getInputDate()));
		if(ConstantCodes.RISKCODE_MC.equals(riskCode)){
			prpLclaim.setSailStartDate(CommonUtils.getYearToDayStr(prpCmain.getStartDate()));
		}
		if(ConstantCodes.RISKCODE_AV.equals(riskCode)){
			PrpCplane prpCplane = getPrpCplaneService().findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLclaim.setMakeDate(prpCplane.getBuildYear());
			}
		}else if(ConstantCodes.RISKCODE_OH.equals(riskCode)||ConstantCodes.RISKCODE_EV.equals(riskCode)||ConstantCodes.RISKCODE_FV.equals(riskCode)||ConstantCodes.RISKCODE_EW.equals(riskCode)||ConstantCodes.RISKCODE_FW.equals(riskCode)){
			PrpCitemShip prpCitemShip = getPrpCitemShipService().findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLclaim.setMakeDate(prpCitemShip.getMakeYearMonth());
			}
		}
		prpLclaim.setEndDate(new DateTime(httpServletRequest.getParameter("prpLclaimEndDate"), DateTime.YEAR_TO_DAY));
		prpLclaim.setCurrency(httpServletRequest.getParameter("prpLclaimCurrency"));
		prpLclaim.setSumAmount(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumAmount"))));
		prpLclaim.setSumPremium(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumPremium"))));
		prpLclaim.setSumQuantity(Long.parseLong(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumQuantity"))));
		// 兼容大地的小时，所以先注释掉
		prpLclaim.setDamageStartHour(StringConvert.newString(httpServletRequest.getParameter("prpLclaimDamageStartHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLclaimDamageStartMinute")) + ":00");
		// 兼容大地的小时，所以先注释掉
		prpLclaim.setDamageStartDate(new DateTime(httpServletRequest.getParameter("prpLclaimDamageStartDate"), DateTime.YEAR_TO_DAY));
		prpLclaim.setDamageEndDate(new DateTime(httpServletRequest.getParameter("prpLclaimDamageEndDate"), DateTime.YEAR_TO_DAY));
		// 兼容大地的小时，所以先注释掉
		prpLclaim.setDamageEndHour(StringConvert.newString(httpServletRequest.getParameter("prpLclaimDamageEndHour")) + ":" + StringConvert.newString(httpServletRequest.getParameter("prpLclaimDamageEndMinute")) + ":00");
		prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLclaimDamageCode")).trim());
		prpLclaim.setDamageName(httpServletRequest.getParameter("prpLclaimDamageName"));
		prpLclaim.setDamageTypeCode(httpServletRequest.getParameter("prpLclaimDamageTypeCode"));
		prpLclaim.setDamageTypeName(httpServletRequest.getParameter("prpLclaimDamageTypeName"));
		prpLclaim.setDamageAreaCode(httpServletRequest.getParameter("prpLclaimDamageAreaCode"));
		prpLclaim.setDamageAreaName(httpServletRequest.getParameter("prpLclaimDamageAreaName"));
		prpLclaim.setDamageAddressType(httpServletRequest.getParameter("prpLclaimDamageAddressType"));
		prpLclaim.setDamageAddress(httpServletRequest.getParameter("prpLclaimDamageAddress"));
		prpLclaim.setLossName(httpServletRequest.getParameter("prpLclaimLossName"));
		prpLclaim.setCargoNo(httpServletRequest.getParameter("prpLclaimCargoNo"));
		prpLclaim.setCargoName(httpServletRequest.getParameter("prpLclaimCargoName"));
		prpLclaim.setGeneralAverage(httpServletRequest.getParameter("generalAverage"));
		prpLclaim.setTransportType(httpServletRequest.getParameter("transportType"));
		prpLclaim.setBkWardStartDate(new DateTime(httpServletRequest.getParameter("prpLclaimBKWardStartDate"),DateTime.YEAR_TO_DAY));
		String riskType = getCodeService().translateRiskCodetoRiskType(riskCode);
		if(ConstantCodes.CLASSCODE_G.equals(riskType)){//工程险
			prpLclaim.setBusinessCareerCode(httpServletRequest.getParameter("prpLclaimBusinessCareerCode"));
			prpLclaim.setBusinessCareerName(httpServletRequest.getParameter("prpLclaimBusinessCareerName"));
			prpLclaim.setDangerousClassItem(httpServletRequest.getParameter("prpLclaimDangerousClassItem"));
			prpLclaim.setDangerousClassSubItem(httpServletRequest.getParameter("prpLclaimDangerousClassSubItem"));
			prpLclaim.setDangerousClassSubItemName(httpServletRequest.getParameter("prpLclaimDangerousClassSubItemName"));
			prpLclaim.setProjectCode(httpServletRequest.getParameter("prpLclaimProjectCode"));
		}
		prpLclaim.setLossQuantity(Long.parseLong(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimLossQuantity"))));
		prpLclaim.setDamageKind(httpServletRequest.getParameter("prpLclaimDamageKind"));
//		if ("4".equals(buttonSaveTypeForClaim)) {
			prpLclaim.setClaimDate(new DateTime(httpServletRequest.getParameter("prpLclaimClaimDate"), DateTime.YEAR_TO_SECOND));
//		}
		prpLclaim.setIndemnityDuty(httpServletRequest.getParameter("indemnityDuty"));
		prpLclaim.setIndemnityDutyRate(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimIndemnityDutyRate"))));
		prpLclaim.setDeductibleRate(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimDeductibleRate"))));
		prpLclaim.setSumClaim(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumClaim"))));
		prpLclaim.setSumDefLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumDefLoss"))));
		prpLclaim.setSumPaid(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumPaid"))));
		prpLclaim.setSumReplevy(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLclaimSumReplevy"))));
		prpLclaim.setRemark(httpServletRequest.getParameter("prpLclaimRemark"));
		String strCaseType = StringUtils.rightTrim(httpServletRequest.getParameter("prpLclaimCaseType"));
		prpLclaim.setCaseType(strCaseType);
		prpLclaim.setMakeCom(httpServletRequest.getParameter("prpLclaimMakeCom"));
		prpLclaim.setComCode(httpServletRequest.getParameter("prpLclaimComCode"));
		prpLclaim.setAgentCode(httpServletRequest.getParameter("prpLclaimAgentCode"));
		prpLclaim.setHandlerCode(httpServletRequest.getParameter("prpLclaimHandlerCode"));
		prpLclaim.setHandleDept(user.getComCode());
		prpLclaim.setHandler1Code(httpServletRequest.getParameter("prpLclaimHandler1Code"));
		prpLclaim.setEndorseNo(DataUtils.nullToEmpty(httpServletRequest.getParameter("prpLclaimEndorseNo")));
		prpLclaim.setStatisticsYM(new DateTime(httpServletRequest.getParameter("prpLclaimStatisticsYM"), DateTime.YEAR_TO_DAY));
		prpLclaim.setOperatorCode(prpLclaim.getOperatorCode());
		prpLclaim.setInputDate(new DateTime(httpServletRequest.getParameter("prpLclaimInputDate"), DateTime.YEAR_TO_DAY));
		String prpLclaimEndCaseDate = httpServletRequest.getParameter("prpLclaimEndCaseDate");
		if (prpLclaimEndCaseDate != null && !"".equals(prpLclaimEndCaseDate)) {
			// 如果不添加这个判断，prpLclaimCancelDate = null 的时候，保存的时候，会保存系统当前的时间
			prpLclaim.setEndCaseDate(new DateTime(prpLclaimEndCaseDate, DateTime.YEAR_TO_DAY));
		}
		prpLclaim.setEndCaserCode(httpServletRequest.getParameter("prpLclaimEndCaserCode"));
		String prpLclaimCancelDate = httpServletRequest.getParameter("prpLclaimCancelDate");
		if (null != prpLclaimCancelDate && !"".equals(prpLclaimCancelDate)) {
			// 如果不添加这个判断，prpLclaimCancelDate = null 的时候，保存的时候，会保存系统当前的时间
			prpLclaim.setCancelDate(new DateTime(prpLclaimCancelDate, DateTime.YEAR_TO_DAY));
		}
		prpLclaim.setCancelReason(httpServletRequest.getParameter("prpLclaimCancelReason"));
		prpLclaim.setDealerCode(httpServletRequest.getParameter("prpLclaimDealerCode"));
		// 原因：添加巨灾代码和出险地邮编
		prpLclaim.setDamageAreaPostCode(httpServletRequest.getParameter("prpDamageAreaPostCode"));
		prpLclaim.setCatastropheCode1(httpServletRequest.getParameter("prpCatastropheCode1"));
		prpLclaim.setCatastropheName1(httpServletRequest.getParameter("prpCatastropheName1"));
		prpLclaim.setCatastropheCode2(httpServletRequest.getParameter("prpCatastropheCode2"));
		prpLclaim.setCatastropheName2(httpServletRequest.getParameter("prpCatastropheName2"));
		// 是否团单免导标志 begin
		prpLclaim.setTermFlag(httpServletRequest.getParameter("termFlag"));
		prpLclaim.setClaimType(httpServletRequest.getParameter("prpLclaimClaimType"));
		String escapeFlag = httpServletRequest.getParameter("escapeFlag");
		if (escapeFlag == null) {
			escapeFlag = "";
		}
		String escapeFlag2 = "";
		escapeFlag2 = httpServletRequest.getParameter("escapeFlag2");
		if (escapeFlag2 == null) {
			escapeFlag2 = "";
		}
		prpLclaim.setEscapeFlag2(escapeFlag2);
		prpLclaim.setEscapeFlag(escapeFlag + escapeFlag2);
		prpLclaim.setFlag(httpServletRequest.getParameter("prpLclaimFlag"));
		prpLclaim.setReplevyFlag(httpServletRequest.getParameter("replevyFlag"));
		prpLclaim.setReplevyRemark(httpServletRequest.getParameter("prpLclaimReplevyRemark"));
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
		if(NumberUtils.isNumber(httpServletRequest.getParameter("prpLclaimHospitalizedDays"))){
			prpLclaim.setHospitalizedDays(new Integer(httpServletRequest.getParameter("prpLclaimHospitalizedDays")));//本次住院天數
		}
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
		prpLclaim.setReplevyLimitDate(new DateTime(httpServletRequest.getParameter("ReplevyLimitDate")));
		prpLclaim.setThirdComFlag(httpServletRequest.getParameter("thirdComFlag"));
		// 需要保存的状态,所以每个操作界面必须有一个"buttonSaveType"的操作域来保存要操作的状态！！
		prpLclaim.setAddressCode(httpServletRequest.getParameter("prpLclaimAddressCode"));
		if ((prpLclaim.getEscapeFlag().length() > 0) && (prpLclaim.getEscapeFlag2().length() > 0)) {
			String strTemp = prpLclaim.getEscapeFlag().substring(0, 1) + prpLclaim.getEscapeFlag2().substring(0, 1);
			prpLclaim.setEscapeFlag(strTemp);
		}
		prpLclaim.setComName(prpLclaim.getComCode());
		/***  add by chenjie 20150601 需求變更-095 begin ***/
		prpLclaim.setCarAccidentType(httpServletRequest.getParameter("prpLclaimCarAccidentType"));
		prpLclaim.setPropAccidentType(httpServletRequest.getParameter("prpLclaimPropAccidentType"));
		/***  add by chenjie 20150601 需求變更-095 end ***/
		claimDto.setPrpLclaim(prpLclaim);
		// 原因：添加出险人员信息
		if (httpServletRequest.getParameter("prpLacciPersonAcciCode") != null || httpServletRequest.getParameter("prpLacciPersonAcciName") != null || httpServletRequest.getParameter("prpLacciPersonAge") != null
				|| httpServletRequest.getParameter("prpLregistIdentifyNumber") != null) {
			PrpLacciPerson prpLacciPerson = new PrpLacciPerson();
			prpLacciPerson.getId().setCertiNo((String) httpServletRequest.getAttribute("claimNo"));
			prpLacciPerson.getId().setCertiType("03");
			prpLacciPerson.setPolicyNo(prpLclaim.getPolicyNo());
			// 分户序号从界面上取
			prpLacciPerson.setFamilyNo(DataUtils.getInteger(DataUtils.emptyToNull(httpServletRequest.getParameter("prpLacciPersonFamilyNo"))));
			prpLacciPerson.getId().setSerialNo(getPolicyService().findBySeriaNo("1=1") + 1);
			prpLacciPerson.setAcciCode(httpServletRequest.getParameter("prpLacciPersonAcciCode"));
			prpLacciPerson.setAcciName(httpServletRequest.getParameter("prpLacciPersonAcciName"));
			prpLacciPerson.setAge(DataUtils.getInteger(DataUtils.nullToZero(httpServletRequest.getParameter("prpLacciPersonAge"))));
			prpLacciPerson.setSex(httpServletRequest.getParameter("prpLacciPersonSex"));
			prpLacciPerson.setIdentifyNumber(httpServletRequest.getParameter("prpLacciPersonIdentifyNumber"));
			prpLacciPerson.setFlag("1"); // 标志是索赔人
			claimDto.setPrpLacciPerson(prpLacciPerson);
		}
		/*---------------------立案文本表prpLltext------------------------------------*/
		ArrayList<PrpLltext> prpLltextList = new ArrayList<PrpLltext>();
		String TextTemp = httpServletRequest.getParameter("prpLltextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH,"GBK");
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			PrpLltext prpLltext = new PrpLltext();
			prpLltext.getId().setClaimNo((String) httpServletRequest.getAttribute("claimNo"));
			prpLltext.setContext(rules[k]);
			prpLltext.getId().setLineNo(k + 1);
			prpLltext.getId().setTextType("09");
			prpLltextList.add(prpLltext);
		}
		claimDto.setPrpLltextList(prpLltextList);
		String[] prpLclaimLossKindCode = httpServletRequest.getParameterValues("prpLclaimLossKindCode");
		String[] prpLclaimLossItemCode = httpServletRequest.getParameterValues("prpLclaimLossItemCode");
		String[] prpLclaimLossCurrency = httpServletRequest.getParameterValues("prpLclaimLossCurrency");
		String[] prpLclaimLossInputDate = httpServletRequest.getParameterValues("prpLclaimLossInputDate");
		String[] prpLclaimLossRemarkFlag = httpServletRequest.getParameterValues("prpLclaimLossRemarkFlag");
		ArrayList<PrpLclaimLoss> prpLclaimLossList = new ArrayList<PrpLclaimLoss>(prpLclaimLossKindCode.length - 1);
		for (int i = 1; i < prpLclaimLossKindCode.length; i++) {
			PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
			prpLclaimLoss.getId().setClaimNo(prpLclaim.getClaimNo());
			prpLclaimLoss.getId().setSerialNo(i);
			prpLclaimLoss.setKindCode(prpLclaimLossKindCode[i]);
			prpLclaimLoss.setItemCode(prpLclaimLossItemCode[i]);
			prpLclaimLoss.setCurrency(prpLclaimLossCurrency[i]);
			if(CommonUtils.isEmpty(prpLclaimLossInputDate[i])){
				prpLclaimLoss.setInputDate(new Date());
			}else{
				prpLclaimLoss.setInputDate(new DateTime(prpLclaimLossInputDate[i], DateTime.YEAR_TO_DAY));
			}
			prpLclaimLoss.setRemarkFlag(prpLclaimLossRemarkFlag[i]);
			prpLclaimLossList.add(prpLclaimLoss);
		}
		claimDto.setPrpLclaimLossList(prpLclaimLossList);
		/*---------------------立案操作状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLclaim.getClaimNo());
		prpLclaimStatus.setPolicyNo(prpLclaim.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("claim");
		prpLclaimStatus.getId().setSerialNo(0);
		prpLclaimStatus.setRiskCode(prpLclaim.getRiskCode());
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLclaim.getInputDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		claimDto.setPrpLclaimStatus(prpLclaimStatus);
		return claimDto;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
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

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
