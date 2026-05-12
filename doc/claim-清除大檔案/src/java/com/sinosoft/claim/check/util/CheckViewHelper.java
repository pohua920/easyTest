package com.sinosoft.claim.check.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLacciCheckText;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * <p>
 * Title: CheckViewHelper
 * </p>
 * <p>
 * Description:查勘ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2004
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public abstract class CheckViewHelper {
	/** 意健险调查文字信息每行最大显示字符长度 */
	private int RULE_LENGTH = 70; // rule字段的长度
	/** 代码服务 */
	private CodeService codeService;

	/**
	 * 默认构造方法
	 */
	public CheckViewHelper() {
	}

	/**
	 * 保存查勘时查勘页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return checkDto 查勘数据传输数据结构
	 * @throws Exception
	 */
	public CheckDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CheckDto checkDto = new CheckDto();
		/*---------------------查勘主表prpLcheck------------------------------------*/
		PrpLcheck prpLcheck = new PrpLcheck();
		PrpLregist prpLregist = new PrpLregist();
		prpLcheck.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
		prpLcheck.setClaimNo((String) httpServletRequest.getParameter("prpLcheckClaimNo"));
		prpLcheck.setRiskCode((String) httpServletRequest.getParameter("prpLcheckRiskCode"));
		prpLcheck.setPolicyNo((String) httpServletRequest.getParameter("prpLcheckPolicyNo"));
		prpLcheck.setCheckType((String) httpServletRequest.getParameter("checkType"));
		prpLcheck.setCheckNature((String) httpServletRequest.getParameter("checkNature"));
		prpLcheck.setCoinsFlag(httpServletRequest.getParameter("prpLcheckCoinsFlag"));
		// 車險時間精確到分秒
		if ("D".equals(ConstantCodes.carClassMap.get(prpLcheck.getRiskCode()))) {
			prpLcheck.setCheckDate(new DateTime(httpServletRequest.getParameter("prpLcheckCheckDate"), DateTime.YEAR_TO_SECOND));
		} else {
			prpLcheck.setCheckDate(new DateTime(httpServletRequest.getParameter("prpLcheckCheckDate")));
		}
		prpLcheck.setCheckSite((String) httpServletRequest.getParameter("prpLcheckCheckSite"));
		prpLcheck.setFirstSiteFlag((String) httpServletRequest.getParameter("firstSiteFlag"));
		prpLcheck.setClaimType((String) httpServletRequest.getParameter("claimType"));
		prpLcheck.setDamageCode((String) httpServletRequest.getParameter("prpLcheckDamageCode"));
		prpLcheck.setDamageName((String) httpServletRequest.getParameter("prpLcheckDamageName"));
		prpLregist.setDamageCode(DataUtils.dbNullToEmpty(httpServletRequest.getParameter("prpLcheckDamageCode")).trim());
		prpLregist.setDamageName((String) httpServletRequest.getParameter("prpLcheckDamageName"));
		prpLcheck.setDamageTypeCode((String) httpServletRequest.getParameter("prpLcheckDamageTypeCode"));
		prpLcheck.setDamageTypeName((String) httpServletRequest.getParameter("prpLcheckDamageTypeName"));
		prpLcheck.setReferKind((String) httpServletRequest.getParameter("referKind"));
		prpLcheck.setDamageAreaCode((String) httpServletRequest.getParameter("prpLcheckDamageAreaCode"));
		prpLcheck.setDamageAddressType((String) httpServletRequest.getParameter("damageAddressType"));
		prpLcheck.setIndemnityDuty((String) httpServletRequest.getParameter("indemnityDuty"));
		prpLcheck.setClaimFlag((String) httpServletRequest.getParameter("claimFlag"));
		prpLcheck.setChecker1((String) httpServletRequest.getParameter("prpLcheckChecker1"));
		prpLcheck.setChecker2((String) httpServletRequest.getParameter("prpLcheckChecker2"));
		prpLcheck.setCheckUnitName((String) httpServletRequest.getParameter("prpLcheckCheckUnitName"));
		prpLcheck.setHandleUnit((String) httpServletRequest.getParameter("prpLcheckHandleUnitName"));
		prpLcheck.setRemark((String) httpServletRequest.getParameter("prpLcheckRemark"));
		prpLcheck.setFlag((String) httpServletRequest.getParameter("prpLcheckFlag"));
		prpLcheck.setDamageAddress((String) httpServletRequest.getParameter("prpLcheckDamageAddress"));
		prpLcheck.setAddressCode((String) httpServletRequest.getParameter("prpLcheckAddressCode"));
		prpLcheck.setAddressName((String) httpServletRequest.getParameter("prpLcheckAddressName"));
		prpLcheck.setAcciAddressCode((String) httpServletRequest.getParameter("prpLcheckAcciAddressCode"));
		prpLcheck.setAcciAddressName((String) httpServletRequest.getParameter("prpLcheckAcciAddressName"));
		// 增加联系人电话
		prpLcheck.setInsuredPhoneNumber((String) httpServletRequest.getParameter("prpLregistPhoneNumber"));
		// 被保险人手机
		prpLcheck.setInsuredMobile((String) httpServletRequest.getParameter("prpLregistMobile"));
		// 强制出险原因代码
		prpLcheck.setDamageCodeBZ((String) httpServletRequest.getParameter("prpLcheckDamageCodeBZ"));
		// 强制出险原因名称
		prpLcheck.setDamageNameBZ((String) httpServletRequest.getParameter("prpLcheckDamageNameBZ"));
		// 增加處理類型代碼和名稱
		prpLcheck.setManageType((String) httpServletRequest.getParameter("prpLcheckManageType"));
		prpLcheck.setManageTypeName((String) httpServletRequest.getParameter("prpLcheckManageTypeName"));
		// 增加處理類型代碼和名稱
		// 增加被保險人代碼、被保險人、車牌號、報案人、報案人電話、駕駛人、駕駛人電話、驾驶人手机
		prpLcheck.setInsuredCode((String) httpServletRequest.getParameter("insuredCode"));
		prpLcheck.setInsuredName((String) httpServletRequest.getParameter("prpLregistLinkerName"));
		prpLcheck.setLicenseNo((String) httpServletRequest.getParameter("prpLcheckLiceseNo"));
		prpLcheck.setReportorName((String) httpServletRequest.getParameter("reportorName"));
		prpLcheck.setReportorPhoneNumber((String) httpServletRequest.getParameter("reportorPhoneNumber"));
		prpLcheck.setLinkerName((String) httpServletRequest.getParameter("linkerName"));
		prpLcheck.setPhoneNumber((String) httpServletRequest.getParameter("phoneNumber"));
		prpLcheck.setDriverMobile((String) httpServletRequest.getParameter("driverMobile"));

		prpLcheck.setPayselfFlag((String) httpServletRequest.getParameter("payselfFlag"));// 互碰自赔

		// 原因：从页面中或的预估金额和预估费用
		prpLcheck.setEstimateLoss(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLcheckEstimateLoss"))));
		prpLcheck.setEstimateFee(Double.parseDouble(DataUtils.nullToZero(httpServletRequest.getParameter("prpLcheckEstimateFee"))));

		// 加入 系统表示 和 查勘处理单位代码字段
		prpLcheck.setUnitType((String) httpServletRequest.getParameter("unitType"));
		prpLcheck.setHandleUnitCode((String) httpServletRequest.getParameter("prpLcheckHandleUnitCode"));
		//需求變更19 增加 警方單位和警員姓名
		prpLcheck.setPoliceUnit((String)httpServletRequest.getParameter("prpLcheckPoliceUnit"));
		prpLcheck.setPoliceName((String)httpServletRequest.getParameter("prpLcheckPoliceName"));

		// 加到ArrayList中
		prpLcheck.getId().setReferSerialNo(1);
		prpLcheck.setInsureCarFlag("1");

		if (httpServletRequest.getParameter("repeatInsureFlag") == null)
			prpLcheck.setRepeatInsureFlag("-");
		else
			prpLcheck.setRepeatInsureFlag(httpServletRequest.getParameter("repeatInsur eFlag"));

		checkDto.setPrpLcheck(prpLcheck);
		String riskCode = prpLcheck.getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 原因：添加意健险的调查信息
		if ("E".equals(strRiskType)) {
			PrpLacciCheck prpLacciCheck = new PrpLacciCheck();
			// //暂时用来显示是什么节点提交过来的调查,需调整
			prpLacciCheck.setRegistNo(httpServletRequest.getParameter("prpLacciCheckRegistNo"));
			prpLacciCheck.setTimes(Integer.parseInt(httpServletRequest.getParameter("prpLacciCheckTimes")));
			prpLacciCheck.setCheckNo(httpServletRequest.getParameter("prpLacciCheckCheckNo"));
			prpLacciCheck.setCertiType(httpServletRequest.getParameter("prpLacciCheckCertiType"));
			prpLacciCheck.setCertiNo(httpServletRequest.getParameter("prpLacciCheckCertiNo"));
			prpLacciCheck.setRiskCode(httpServletRequest.getParameter("prpLacciCheckRiskCode"));
			prpLacciCheck.setPolicyNo(httpServletRequest.getParameter("prpLacciCheckPolicyNo"));
			prpLacciCheck.setCheckContext(httpServletRequest.getParameter("prpLacciCheckCheckContext"));
			prpLacciCheck.setCheckObject(httpServletRequest.getParameter("prpLacciCheckCheckObject"));
			prpLacciCheck.setCheckNature(httpServletRequest.getParameter("checkNature"));
			prpLacciCheck.setCheckDate(new DateTime(httpServletRequest.getParameter("prpLacciCheckCheckDate")));
			prpLacciCheck.setCheckHour(httpServletRequest.getParameter("prpLacciCheckCheckHour") + ":" + httpServletRequest.getParameter("prpLaccecheckCheckMinute"));
			prpLacciCheck.setCheckEndDate(new DateTime(httpServletRequest.getParameter("prpLacciCheckCheckEndDate")));
			prpLacciCheck.setCheckEndHour(httpServletRequest.getParameter("prpLacciCheckCheckEndHour") + ":" + httpServletRequest.getParameter("prpLacciCheckCheckEndMinute"));
			prpLacciCheck.setCheckSite(httpServletRequest.getParameter("prpLacciCheckCheckSite"));
			prpLacciCheck.setDamageCode(httpServletRequest.getParameter("prpLacciCheckDamageCode"));
			prpLacciCheck.setDamageName(httpServletRequest.getParameter("prpLacciCheckDamageName"));
			prpLacciCheck.setDamageTypeCode(httpServletRequest.getParameter("prpLacciCheckDamageTypeCode"));
			prpLacciCheck.setDamageTypeName(httpServletRequest.getParameter("prpLacciCheckDamageTypeName"));
			prpLacciCheck.setCheckerCode(httpServletRequest.getParameter("prpLacciCheckCode"));
			prpLacciCheck.setRemark(httpServletRequest.getParameter("prpLacciCheckRemark"));
			prpLacciCheck.setCheckFee((Double.parseDouble(DataUtils.nullToZero((httpServletRequest.getParameter("prpLacciCheckCheckFee"))))));
			prpLacciCheck.setCurrency(httpServletRequest.getParameter("prpLacciCheckCurrencyCode"));
			prpLacciCheck.setHandleDept(user.getComCode());

			List<PrpLacciCheckText> prpLacciCheckTextList = new ArrayList<PrpLacciCheckText>();
			// 得到连接串,下面将其切分到数组
			String TextTemp = httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
			String[] rulesAcci = StringUtils.split(TextTemp, RULE_LENGTH);
			for (int k = 0; k < rulesAcci.length; k++) {
				PrpLacciCheckText prpLacciCheckText = new PrpLacciCheckText();
				prpLacciCheckText.getId().setCheckNo((String) httpServletRequest.getParameter("prpLacciCheckCheckNo"));
				prpLacciCheckText.setContext(rulesAcci[k]);
				prpLacciCheckText.getId().setLineNo((long) k + 1);
				prpLacciCheckText.getId().setTextType("3");
				prpLacciCheckTextList.add(prpLacciCheckText);
			}
			AcciCheckDto acciCheckDto = new AcciCheckDto();
			acciCheckDto.setPrpLacciCheck(prpLacciCheck);
			acciCheckDto.setPrpLacciCheckTextList(prpLacciCheckTextList);
			checkDto.setAcciCheckDto(acciCheckDto);
		}
		/*---------------------文本表PrpLregistTextDto--------------------*/
		ArrayList<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
		// 得到连接串,下面将其切分到数组
		String TextTemp = httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);
		for (int k = 0; k < rules.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("3");
			prpLregistTextList.add(prpLregistText);
		}

		// 装入checkDto
		checkDto.setPrpLregistTextList(prpLregistTextList);

		/*---------------------状态内容prpLclaimStatus------------------------------------*/
		PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
		prpLclaimStatus.setStatus(httpServletRequest.getParameter("buttonSaveType"));
		prpLclaimStatus.getId().setBusinessNo(prpLcheck.getId().getRegistNo());
		prpLclaimStatus.setPolicyNo(prpLcheck.getPolicyNo());
		prpLclaimStatus.getId().setNodeType("check");
		prpLclaimStatus.getId().setSerialNo(0);
		prpLclaimStatus.setRiskCode(prpLcheck.getRiskCode());
		// 取得当前用户信息，写操作员信息到查勘中
		//HttpSession session = httpServletRequest.getSession();
		//UserDto user = (UserDto) session.getAttribute("user");
		prpLclaimStatus.setHandlerCode(user.getUserCode());
		prpLclaimStatus.setInputDate(prpLcheck.getCheckDate());
		prpLclaimStatus.setOperateDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));

		checkDto.setPrpLclaimStatus(prpLclaimStatus);
		return checkDto;

	}

	/**
	 * 取初始化信息需要的数据的整理. 填写查勘单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public abstract CheckDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param checkDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public abstract void dtoToView(HttpServletRequest httpServletRequest, CheckDto checkDto) throws Exception;

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
