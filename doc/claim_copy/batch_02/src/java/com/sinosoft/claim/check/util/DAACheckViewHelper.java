package com.sinosoft.claim.check.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.certainLoss.util.DAACertainLossViewHelper;
import com.sinosoft.claim.check.service.facade.AcciCheckService;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.SendUndwrtViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLacciCheckCharge;
import com.sinosoft.claim.schema.model.PrpLacciCheckChargeId;
import com.sinosoft.claim.schema.model.PrpLacciCheckText;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckExt;
import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sun.org.apache.commons.beanutils.BeanUtils;

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
public class DAACheckViewHelper extends CheckViewHelper {
	/** 批单ViewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	/** 报案服务 */
	private RegistService registService;
	/** 查勘服务 */
	private CheckService checkService;
	/** 意键险报案对象服务 */
	private AcciCheckService acciCheckService;
	/** 查勘信息服务 */
	private PrpLcheckService prpLcheckService;
	/** 调度服务 */
	private ScheduleService scheduleService;
	/** 定损viewHelper */
	private DAACertainLossViewHelper daaCertainLossViewHelper;
	/** 报案文字信息服务 */
	private PrpLregistTextService prpLregistTextService;
	/** 报案viewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 通用代码数据服务 */
	private PrpDcodeService prpDcodeService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 赔案保单关联服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 代码服务 */
	private CodeService codeService;
	/** 节点送审信息viewHelper */
	private SendUndwrtViewHelper sendUndwrtViewHelper;
	/** 保险关系人信息服务 */
	private PrpCinsuredService prpCinsuredService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	private PrpCitemKindService prpCitemKindService;
	private PrpCaddressService prpCaddressService;
	private PrpCmainCargoService prpCmainCargoService;
	/**
	 * 默认构造方法
	 */
	public DAACheckViewHelper() {
	}

	/**
	 * 保存查勘时查勘页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return checkDto 查勘数据传输数据结构
	 * @throws Exception
	 */
	public CheckDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CheckDto checkDto = super.viewToDto(httpServletRequest);
		checkDto.getPrpLcheck().setDealFastFlag(DataUtils.nullToZero((String) httpServletRequest.getParameter("dealFastFlag")));
		// 损失模块信息合到涉案车辆、人伤、财产损失信息中
		ArrayList<PrpLcheckLoss> prpLcheckLossList = new ArrayList<PrpLcheckLoss>();
		PrpLcheckLoss prpLcheckLoss = null;
		int intCheckLossIndex = 1;// 序号
		String prpLcheckLossRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLcheckLossPolicyNo = httpServletRequest.getParameter("prpLcheckPolicyNo");
		String prpLcheckLossClaimNo = httpServletRequest.getParameter("prpLcheckClaimNo");
		String prpLcheckLossRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		// 因为考虑到录入的时候，可能没有立案，但是在提交的时候，做了立案，导致立案号没写入。
		// 先取立案号码，很重要，不要从页面上取得
		String claimNo = prpLcheckLossClaimNo;
		if (claimNo == null || claimNo.length() < 2) {
			claimNo = this.codeService.translateBusinessCode(prpLcheckLossRegistNo, true);
		}
		/*---------------------三者车辆prpLthirdParty------------------------------------*/
		ArrayList<PrpLthirdParty> thirdPartyDtoList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;
		// 从界面得到输入数组
		// String prpLthirdPartyClaimNo = httpServletRequest
		// .getParameter("prpLcheckClaimNo");
		String prpLthirdPartyRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String prpLthirdPartyRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLcheckClauseType");
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
		// String[] prpLthirdPartyFlag =
		// httpServletRequest.getParameterValues("prpLthirdPartyFlag");
		String[] prpLthirdPartyCarryingUnit = httpServletRequest.getParameterValues("prpLthirdPartyCarryingUnit");
		String[] prpLthirdPartyInsuranceNo = httpServletRequest.getParameterValues("prpLthirdPartyInsuranceNo");
		String[] prpLthirdPartyCarryingNumber = httpServletRequest.getParameterValues("prpLthirdPartyCarryingNumber");
		String[] prpLthirdPartyIsInsurance = httpServletRequest.getParameterValues("prpLthirdPartyIsInsurance");
		String[] prpLthirdPartyInsuredIdentity = httpServletRequest.getParameterValues("prpLthirdPartyInsuredIdentity");
		String[] prpLthirdPartyCarsOwners = httpServletRequest.getParameterValues("prpLthirdPartyCarsOwners");
		String[] prpLthirdPartyRelationship = httpServletRequest.getParameterValues("prpLthirdPartyRelationship");
		String[] prpLthirdPartyGarageHeadName = httpServletRequest.getParameterValues("prpLthirdPartyGarageHeadName");
		String[] prpLthirdPartyDrivingAddress = httpServletRequest.getParameterValues("prpLthirdPartyDrivingAddress");
		String[] prpLthirdPartyVINNo = httpServletRequest.getParameterValues("prpLthirdPartyVINNo");
		String[] prpLthirdPartyLossFlag = httpServletRequest.getParameterValues("prpLthirdPartyLossFlag");
		// Reason:损失模块信息合到涉案车辆、人伤、财产损失信息中
		// String[] prpLthirdPartyKindCode =
		// httpServletRequest.getParameterValues("prpLthirdPartyKindCode");
		String[] prpLthirdPartyLossFee = httpServletRequest.getParameterValues("prpLthirdPartyLossFee");
		// 界面上车辆对应的是否为新增车辆的标志
		// String[] prpLthirdPartyNewAddFlag =
		// httpServletRequest.getParameterValues("prpLthirdPartyNewAddFlag");
		// 界面上车辆对应的是否为新增车辆的标志
		// 调度标底用的
		List<PrpLscheduleItem> scheduleItemList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		int scheduleId = 1; //调度号的id
		// 对象赋值
		if (prpLthirdPartySerialNo != null && prpLthirdPartyLicenseColorCode != null) {
			// 三者车辆部分开始
			for (int index = 1; index < prpLthirdPartySerialNo.length; index++) {
				prpLthirdParty = new PrpLthirdParty();
				// 预估损失模块合到涉案车辆信息中
				prpLcheckLoss = new PrpLcheckLoss();
				prpLcheckLoss.getId().setRegistNo(prpLcheckLossRegistNo);
				prpLcheckLoss.setClaimNo(claimNo);
				prpLcheckLoss.setRiskCode(prpLcheckLossRiskCode);
				prpLcheckLoss.setPolicyNo(prpLcheckLossPolicyNo);
				prpLcheckLoss.getId().setSerialNo(intCheckLossIndex);
				prpLcheckLoss.setReferSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartySerialNo[index])));
				// 车险查勘时要求提供多险别的输入，为此将险别存入到carloss里，但由於checkloss表kindcode字段不允许为空，所以此填入一个师直为默认值
				// prpLcheckLossDto.setKindCode(prpLthirdPartyKindCode[index]);
				prpLcheckLoss.setKindCode(ConstantCodes.KINDCODE_A01_01);
				prpLcheckLoss.setLossFeeType("1");
				prpLcheckLoss.setLossFee(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyLossFee[index])));
				prpLcheckLoss.setFlag("");
				intCheckLossIndex++;
				prpLcheckLossList.add(prpLcheckLoss);

				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartySerialNo[index])));
				prpLthirdParty.setClaimNo(claimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if (prpLthirdPartyLicenseNo[index] == null || "".equals(prpLthirdPartyLicenseNo[index])) {
					prpLthirdPartyLicenseNo[index] = " ";
				}
				prpLthirdParty.setLicenseNo(prpLthirdPartyLicenseNo[index]);
				prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode[index]);
				prpLthirdParty.setCarKindCode(prpLthirdPartyCarKindCode[index]);
				prpLthirdParty.setInsureCarFlag(prpLthirdPartyInsureCarFlag[index]);
				prpLthirdParty.setEngineNo(prpLthirdPartyEngineNo[index]);
				prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo[index]);
				prpLthirdParty.setBrandName(prpLthirdPartyBrandName[index]);
				prpLthirdParty.setModelCode(prpLthirdPartyModelCode[index]);

				prpLthirdParty.setRunDistance(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyRunDistance[index])));
				prpLthirdParty.setUseYears(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartyUseYears[index])));
				prpLthirdParty.setDutyPercent(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyDutyPercent[index])));
				prpLthirdParty.setInsuredFlag(prpLthirdPartyInsuredFlag[index]);
				prpLthirdParty.setInsureComCode(prpLthirdPartyInsureComCode[index]);
				prpLthirdParty.setInsureComName(prpLthirdPartyInsureComName[index]);
				prpLthirdParty.setVINNo(prpLthirdPartyVINNo[index]);
				prpLthirdParty.setGarageHeadName(prpLthirdPartyGarageHeadName[index]);
				prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);
				prpLthirdParty.setCarryingUnit(prpLthirdPartyCarryingUnit[index]);
				prpLthirdParty.setInsuranceNo(prpLthirdPartyInsuranceNo[index]);
				prpLthirdParty.setCarryingNumber(Long.parseLong(DataUtils.nullToZero(prpLthirdPartyCarryingNumber[index])));
				prpLthirdParty.setCarsOwners(prpLthirdPartyCarsOwners[index]);
				prpLthirdParty.setIsInsurance(prpLthirdPartyIsInsurance[index]);
				prpLthirdParty.setInsuredIdentity(prpLthirdPartyInsuredIdentity[index]);
				prpLthirdParty.setRelationship(prpLthirdPartyRelationship[index]);

				prpLthirdParty.setLossFlag(prpLthirdPartyLossFlag[index]);
				// 加入集合
				thirdPartyDtoList.add(prpLthirdParty);
				// 整理调度情况
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLscheduleItem.getId().setItemNo(prpLthirdParty.getId().getSerialNo());
				prpLscheduleItem.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				// 表示是否选中
				prpLscheduleItem.setSelectSend("1");
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(checkDto.getPrpLcheck().getCheckSite());
				prpLscheduleItem.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setNextNodeNo("certa");
				// 加入调度标的集合
				// 由於单独的强制险不增加标的车的定损。
				// 定损任务主表及报案主表的回写
				PrpLregist prpLregist = prpLregistService.findPrpLregist(checkDto.getPrpLcheck().getId().getRegistNo());
				String comCode = prpLregist.getComCode().substring(0, 2);
				PrpDriskConfig prpDriskConfigDto = this.prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
				PrpDriskConfig prpDriskConfigDto1 = this.prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "dealFast_case");
				if ((prpDriskConfigDto != null && "1".equals(prpDriskConfigDto.getConfigValue())) || (prpDriskConfigDto1 != null && "1".equals(prpDriskConfigDto1.getConfigValue())))// 是否配置
				{
					if (prpDriskConfigDto != null && "1".equals(prpDriskConfigDto.getConfigValue()))// 无责垫付
					{
						prpLregist.setAdvanceType(httpServletRequest.getParameter("prplregistAdvance"));
						prpLregistService.update(prpLregist);// 回写报案主表
					}
				}
				// 无责代赔 要求交强险处理须存在标的车定损
				scheduleItemList.add(prpLscheduleItem);
			}
			// 查勘集合中加入三者车辆
			checkDto.setPrpLthirdPartyList(thirdPartyDtoList);
		}
		/*---------------------驾驶员prpLdriver------------------------------------*/
		List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = null;

		// 从界面得到输入数组
		String prpLdriverRegistNo = (String) httpServletRequest.getAttribute("registNo");
		// String prpLdriverClaimNo =
		// httpServletRequest.getParameter("prpLcheckClaimNo");
		String prpLdriverRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String prpLdriverPolicyNo = httpServletRequest.getParameter("prpLcheckPolicyNo");
		String[] prpLdriverSerialNo = httpServletRequest.getParameterValues("prpLdriverSerialNo");
		/*
		 * modify by liuyanmei delete 20051116 reason : 驾驶员信息只需要姓名电话 String[]
		 * prpLdriverLicenseColorCode = httpServletRequest
		 * .getParameterValues("prpLdriverLicenseColorCode"); String[]
		 * prpLdriverDrivingLicenseNo = httpServletRequest
		 * .getParameterValues("prpLdriverDrivingLicenseNo");
		 */
		String[] prpLdriverDrivingLicenseNo = httpServletRequest.getParameterValues("prpLdriverDrivingLicenseNo");
		String[] prpLdriverDriverName = httpServletRequest.getParameterValues("prpLdriverDriverName");
		String[] prpLdriverDriverPhone = httpServletRequest.getParameterValues("prpLdriverDriverPhone");
		String[] prpLdriverMobilePhone = httpServletRequest.getParameterValues("prpLdriverMobilePhone");
		String[] prpLdriverBirthday = httpServletRequest.getParameterValues("prpLdriverBirthday");
		String[] prpLdriverIdentifyNumber = httpServletRequest.getParameterValues("prpLdriverIdentifyNumber");
		// 添加驾驶员证件号
		String[] prpLdriverLicenseNo = httpServletRequest.getParameterValues("prpLdriverLicenseNo");
		String[] prpLdriverDriverSex = httpServletRequest.getParameterValues("driverSex");
		String[] prpLdriverDrivingCarType = httpServletRequest.getParameterValues("drivingCarType");
		String[] prpLdriverApanageCode = httpServletRequest.getParameterValues("prpLdriverApanageCode");
		String[] prpLdriverApanage = httpServletRequest.getParameterValues("prpLdriverApanage");
		String[] prpLdriverIdentity = httpServletRequest.getParameterValues("prpLdriverDriverIdentity");
		String[] prpLdriverDistrict = httpServletRequest.getParameterValues("prpLdriverDriverDistrict");
		String[] prpLdriverIsMarried = httpServletRequest.getParameterValues("prpLdriverIsMarried");
		/*
		 * 驾驶员信息只需要姓名电话 String[] prpLdriverDriverSex = httpServletRequest
		 * .getParameterValues("driverSex"); String[] prpLdriverDriverAge =
		 * httpServletRequest .getParameterValues("prpLdriverDriverAge");
		 * String[] prpLdriverDriverOccupation = httpServletRequest
		 * .getParameterValues("prpLdriverDriverOccupation"); String[]
		 * prpLdriverEducation = httpServletRequest
		 * .getParameterValues("education"); String[] prpLdriverUnitAddress =
		 * httpServletRequest .getParameterValues("prpLdriverUnitAddress");
		 * String[] prpLdriverIdentifyNumber = httpServletRequest
		 * .getParameterValues("prpLdriverIdentifyNumber"); String[]
		 * prpLdriverDriverGrade = httpServletRequest
		 * .getParameterValues("prpLdriverDriverGrade"); String[]
		 * prpLdriverDriverSeaRoute = httpServletRequest
		 * .getParameterValues("prpLdriverDriverSeaRoute"); String[]
		 * prpLdriverReceiveLicenseDate = httpServletRequest
		 * .getParameterValues("prpLdriverReceiveLicenseDate"); String[]
		 * prpLdriverDrivingCarType = httpServletRequest
		 * .getParameterValues("drivingCarType"); String[] prpLdriverDrivingYear
		 * = httpServletRequest .getParameterValues("prpLdriverDrivingYear");
		 * String[] prpLdriverAwardLicenseOrgan = httpServletRequest
		 * .getParameterValues("prpLdriverAwardLicenseOrgan"); String[]
		 * prpLdriverSpecialCertificate = httpServletRequest
		 * .getParameterValues("prpLdriverSpecialCertificate"); String[]
		 * prpLdriverFlag = httpServletRequest
		 * .getParameterValues("prpLdriverFlag"); // modify by liuyanmei delete
		 * 20051116
		 */

		// 对象赋值
		if (prpLdriverSerialNo != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// 驾驶员部分开始
			for (int index = 1; index < prpLdriverSerialNo.length; index++) {
				prpLdriver = new PrpLdriver();
				prpLdriver.getId().setRegistNo(prpLdriverRegistNo);
				prpLdriver.setClaimNo(claimNo);
				prpLdriver.setRiskCode(prpLdriverRiskCode);
				prpLdriver.setPolicyNo(prpLdriverPolicyNo);
				prpLdriver.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLdriverSerialNo[index])));
				prpLdriver.setDriverName(prpLdriverDriverName[index]);
				prpLdriver.setDriverSex(prpLdriverDriverSex[index]);
				prpLdriver.setDriverSeaRoute(prpLdriverDriverPhone[index]);
				prpLdriver.setMobilePhone(prpLdriverMobilePhone[index]);
				if (!"".equals(prpLdriverBirthday[index])) {
					prpLdriver.setBirthday(simpleDateFormat.parse(prpLdriverBirthday[index]));
				}
				prpLdriver.setIdentifyNumber(prpLdriverIdentifyNumber[index]);
				prpLdriver.setDrivingCarType(prpLdriverDrivingCarType[index]);
				// 添加驾驶员证件号
				prpLdriver.setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);
				prpLdriver.setLicenseNo(prpLdriverLicenseNo[index]);
				prpLdriver.setDriverApanage(prpLdriverApanage[index]);
				prpLdriver.setDriverApanageCode(prpLdriverApanageCode[index]);
				prpLdriver.setDriverIdentity(prpLdriverIdentity[index]);
				prpLdriver.setDriverDistrict(prpLdriverDistrict[index]);
				prpLdriver.setIsMarried(prpLdriverIsMarried[index]);
				/*
				 * modify by liuyanmei delete 20051116 reason : 驾驶员信息只需要姓名电话
				 * prpLdriverDto.setLicenseNo(prpLdriverLicenseNo[index]);
				 * prpLdriverDto
				 * .setLicenseColorCode(prpLdriverLicenseColorCode[index]);
				 * prpLdriverDto
				 * .setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);
				 * prpLdriverDto.setDriverName(prpLdriverDriverName[index]);
				 * prpLdriverDto.setDriverSex(prpLdriverDriverSex[index]);
				 * prpLdriverDto.setDriverAge(Integer.parseInt(DataUtils
				 * .nullToZero(prpLdriverDriverAge[index]))); prpLdriverDto
				 * .setDriverOccupation(prpLdriverDriverOccupation[index]);
				 * prpLdriverDto.setEducation(prpLdriverEducation[index]);
				 * prpLdriverDto.setUnitAddress(prpLdriverUnitAddress[index]);
				 * prpLdriverDto
				 * .setIdentifyNumber(prpLdriverIdentifyNumber[index]);
				 * prpLdriverDto.setDriverGrade(Integer.parseInt(DataUtils
				 * .nullToZero(prpLdriverDriverGrade[index]))); prpLdriverDto
				 * .setDriverSeaRoute(prpLdriverDriverSeaRoute[index]);
				 * prpLdriverDto.setReceiveLicenseDate(new DateTime(
				 * prpLdriverReceiveLicenseDate[index], DateTime.YEAR_TO_DAY));
				 * prpLdriverDto
				 * .setDrivingCarType(prpLdriverDrivingCarType[index]);
				 * prpLdriverDto.setDrivingYear(Integer.parseInt(DataUtils
				 * .nullToZero(prpLdriverDrivingYear[index]))); prpLdriverDto
				 * .setAwardLicenseOrgan(prpLdriverAwardLicenseOrgan[index]);
				 * prpLdriverDto
				 * .setSpecialCertificate(prpLdriverSpecialCertificate[index]);
				 * prpLdriverDto.setFlag(prpLdriverFlag[index]); //end
				 */
				// 加入集合
				driverList.add(prpLdriver);
			}
			// 查勘集合中加入驾驶员
			checkDto.setPrpLdriverList(driverList);
		}
		/*---------------------查勘扩展信息PrpLcheckExt------------------------------------*/
		List<PrpLcheckExt> prpLcheckExtList = new ArrayList<PrpLcheckExt>();
		PrpLcheckExt prpLcheckExt = null;

		// 从界面得到输入数组
		String prpLcheckExtRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLcheckExtPolicyNo = httpServletRequest.getParameter("prpLcheckPolicyNo");
		// String prpLcheckExtClaimNo =
		// httpServletRequest.getParameter("prpLcheckClaimNo");
		String prpLcheckExtRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String prpLcheckExtColumnValue01 = httpServletRequest.getParameter("CheckExt01");
		String prpLcheckExtColumnValue02 = httpServletRequest.getParameter("CheckExt02");
		String prpLcheckExtColumnValue03 = httpServletRequest.getParameter("CheckExt03");
		String[] prpLcheckExtColumnValue04 = httpServletRequest.getParameterValues("CheckExt04");
		String prpLcheckExtColumnValue05 = httpServletRequest.getParameter("CheckExt05");
		String prpLcheckExtColumnValue06 = httpServletRequest.getParameter("CheckExt06");
		String prpLcheckExtColumnValue07 = httpServletRequest.getParameter("CheckExt07");
		String prpLcheckExtColumnValue08 = httpServletRequest.getParameter("CheckExt08");
		String prpLcheckExtColumnValue09 = httpServletRequest.getParameter("CheckExt09");
		String prpLcheckExtColumnValue10 = httpServletRequest.getParameter("CheckExt10");
		String prpLcheckExtColumnValue11 = httpServletRequest.getParameter("CheckExt11");
		String prpLcheckExtColumnValue12 = httpServletRequest.getParameter("CheckExt12");
		String prpLcheckExtColumnValue13 = httpServletRequest.getParameter("CheckExt13");
		String prpLcheckExtColumnValue14 = httpServletRequest.getParameter("CheckExt14");
		String prpLcheckExtColumnValue15 = httpServletRequest.getParameter("CheckExt15");
		String prpLcheckExtColumnValue16 = httpServletRequest.getParameter("CheckExt16");
		String prpLcheckExtColumnValue17 = httpServletRequest.getParameter("CheckExt17");
		String[] prpLcheckExtColumnValue18 = httpServletRequest.getParameterValues("CheckExt18");
		String prpLcheckExtColumnValue19 = httpServletRequest.getParameter("CheckExt19");
		String prpLcheckExtColumnValue20 = httpServletRequest.getParameter("CheckExt20");
		String prpLcheckExtColumnValue21 = httpServletRequest.getParameter("CheckExt21");
		String prpLcheckExtColumnValue22 = httpServletRequest.getParameter("CheckExt22");
		String prpLcheckExtColumnValue23 = httpServletRequest.getParameter("CheckExtText23");
		String prpLcheckExtColumnValue24 = httpServletRequest.getParameter("CheckExt24");
		String prpLcheckExtColumnValue25 = httpServletRequest.getParameter("CheckExt25");
		String prpLcheckExtColumnValue26 = httpServletRequest.getParameter("CheckExt26");

		String prpLcheckExtColumnValue27 = httpServletRequest.getParameter("CheckExtText27");

		String prpLcheckExtColumnValue091 = httpServletRequest.getParameter("CheckExt091");
		String prpLcheckExtColumnValue191 = httpServletRequest.getParameter("CheckExt191");

		// 第一条记录
		boolean hasExtColumn = false; // 表示有记录的

		if (prpLcheckExtColumnValue01 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo((Integer.parseInt(httpServletRequest.getParameter("CheckExt01Serial"))));
			prpLcheckExt.getId().setColumnName("CheckExt01");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt01Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue01);
			prpLcheckExt.setFlag("0");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二条记录
		if (prpLcheckExtColumnValue02 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt02Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt02");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt02Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue02);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第三条记录
		if (prpLcheckExtColumnValue03 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt03Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt03");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt03Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue03);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第四条记录
		if (prpLcheckExtColumnValue04 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt04Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt04");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt04Dis"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < prpLcheckExtColumnValue04.length; i++) {
				if (i > 0) {
					sb.append("," + prpLcheckExtColumnValue04[i]);
				} else {
					sb.append(prpLcheckExtColumnValue04[i]);
				}
			}
			prpLcheckExt.setColumnValue(sb.toString());
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第五条记录
		if (prpLcheckExtColumnValue05 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt05Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt05");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt05Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue05);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第六条记录
		if (prpLcheckExtColumnValue06 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt06Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt06");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt06Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue06);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第七条记录
		if (prpLcheckExtColumnValue07 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt07Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt07");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt07Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue07);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第八条记录
		if (prpLcheckExtColumnValue08 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt08Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt08");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt08Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue08);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第九条记录
		if (prpLcheckExtColumnValue09 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt09Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt09");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt09Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue09);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十条记录
		if (prpLcheckExtColumnValue10 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt10Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt10");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt10Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue10);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十一条记录
		if (prpLcheckExtColumnValue11 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt11Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt11");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt11Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue11);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十二条记录
		if (prpLcheckExtColumnValue12 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt12Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt12");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt12Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue12);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十三条记录
		if (prpLcheckExtColumnValue13 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt13Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt13");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt13Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue13);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十四条记录
		if (prpLcheckExtColumnValue14 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt14Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt14");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt14Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue14);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十五条记录
		if (prpLcheckExtColumnValue15 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt15Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt15");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt15Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue15);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十六条记录
		if (prpLcheckExtColumnValue16 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt16Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt16");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt16Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue16);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十七条记录
		if (prpLcheckExtColumnValue17 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt17Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt17");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt17Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue17);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十八条记录
		if (prpLcheckExtColumnValue18 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt18Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt18");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt18Dis"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < prpLcheckExtColumnValue18.length; i++) {
				if (i > 0) {
					sb.append("," + prpLcheckExtColumnValue18[i]);
				} else {
					sb.append(prpLcheckExtColumnValue18[i]);
				}
			}
			prpLcheckExt.setColumnValue(sb.toString());
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十九条记录
		if (prpLcheckExtColumnValue19 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt19Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt19");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt19Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue19);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二十条记录
		if (prpLcheckExtColumnValue20 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt20Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt20");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt20Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue20);
			if ("0".equals(prpLcheckExtColumnValue20)) {
				String prpLcheckExtColumnValue201 = httpServletRequest.getParameter("CheckExtText201");
				String prpLcheckExtColumnValue202 = httpServletRequest.getParameter("CheckExtText202");
				prpLcheckExt.setRemark(prpLcheckExtColumnValue201 + "," + prpLcheckExtColumnValue202);
			}
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二一条记录
		if (prpLcheckExtColumnValue21 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt21Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt21");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt21Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue21);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二二条记录
		if (prpLcheckExtColumnValue22 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt22Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt22");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt22Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue22);
			if ("0".equals(prpLcheckExtColumnValue22)) {
				String prpLcheckExtColumnValue221 = httpServletRequest.getParameter("CheckExtText221");
				String prpLcheckExtColumnValue222 = httpServletRequest.getParameter("CheckExtText222");
				prpLcheckExt.setRemark(prpLcheckExtColumnValue221 + "," + prpLcheckExtColumnValue222);
			}
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二三条记录
		if (prpLcheckExtColumnValue23 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt23Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt23");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt23Dis"));
			if (prpLcheckExtColumnValue23 != null) {
				prpLcheckExt.setRemark(prpLcheckExtColumnValue23);
			}
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二四条记录
		if (prpLcheckExtColumnValue24 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt24Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt24");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt24Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue24);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二五条记录
		if (prpLcheckExtColumnValue25 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt25Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt25");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt25Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue25);
			prpLcheckExt.setFlag("");
			prpLcheckExt.setRemark(httpServletRequest.getParameter("CheckExtText25"));
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			httpServletRequest.setAttribute("prpLcheckExt25", prpLcheckExt);
			hasExtColumn = true;
		}
		// 第二六条记录
		if (prpLcheckExtColumnValue26 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt26Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt26");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt26Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue26);
			prpLcheckExt.setFlag("");
			prpLcheckExt.setRemark(httpServletRequest.getParameter("CheckExtText26"));
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			httpServletRequest.setAttribute("prpLcheckExt26", prpLcheckExt);
			hasExtColumn = true;
		}
		if (prpLcheckExtColumnValue27 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt27Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt27");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt27Dis"));
			prpLcheckExt.setFlag("");
			if (prpLcheckExtColumnValue27 != null) {
				prpLcheckExt.setRemark(prpLcheckExtColumnValue27);
			}
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			httpServletRequest.setAttribute("prpLcheckExt27", prpLcheckExt);
			hasExtColumn = true;
		}

		// 第九条（2）记录
		if (prpLcheckExtColumnValue091 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt091Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt091");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt091Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue091);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 第十九条（1）记录
		if (prpLcheckExtColumnValue191 != null) {
			prpLcheckExt = new PrpLcheckExt();
			prpLcheckExt.setPolicyNo(prpLcheckExtPolicyNo);
			prpLcheckExt.setRiskCode(prpLcheckExtRiskCode);
			prpLcheckExt.getId().setRegistNo(prpLcheckExtRegistNo);
			prpLcheckExt.setClaimNo(claimNo);
			prpLcheckExt.getId().setSerialNo(Integer.parseInt(httpServletRequest.getParameter("CheckExt191Serial")));
			prpLcheckExt.getId().setColumnName("CheckExt191");
			prpLcheckExt.setDisplayName(httpServletRequest.getParameter("CheckExt191Dis"));
			prpLcheckExt.setColumnValue(prpLcheckExtColumnValue191);
			prpLcheckExt.setFlag("");
			// 加入集合
			prpLcheckExtList.add(prpLcheckExt);
			hasExtColumn = true;
		}
		// 查勘扩展信息
		if (hasExtColumn) {
			checkDto.setPrpLcheckExtList(prpLcheckExtList);
		}
		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp begin------------------------------------*/
		List<PrpLthirdProp> thirdPropList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;

		// 从界面得到输入数组
		String prpLthirdPropRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPropRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String[] prpLthirdPropItemNo = httpServletRequest.getParameterValues("prpLthirdPropItemNo");
		String[] prpLthirdPropLicenseNo = httpServletRequest.getParameterValues("prpLthirdPropLicenseNo");
		String[] lossItemCode = httpServletRequest.getParameterValues("prpLthirdLossItemCode");
		String[] LossItemName = httpServletRequest.getParameterValues("prpLthirdLossItemName");
		String[] prpLthirdPropLossDesc = httpServletRequest.getParameterValues("prpLthirdPropLossDesc");
		String[] prpLthirdPropFlag = httpServletRequest.getParameterValues("prpLthirdPropFlag");

		// 损失模块信息合到涉案车辆、人伤、财产损失信息中
		String[] prpLthirdPropKindCode = httpServletRequest.getParameterValues("prpLthirdPropKindCode");
		String[] prpLthirdPropLossFee = httpServletRequest.getParameterValues("prpLthirdPropLossFee");
		String[] prpLthirdPropGoodsCarLicenseNo = httpServletRequest.getParameterValues("prpLthirdPropGoodsCarLicenseNo");
		
		// String[] prpLthirdPropNewAddFlag =
		// httpServletRequest.getParameterValues("prpLthirdPropNewAddFlag");//
		// 是否新增项目的标志

		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdPropItemNo != null) {
			for (int index = 1; index < prpLthirdPropItemNo.length; index++) {
				prpLthirdProp = new PrpLthirdProp();
				// 预估损失模块合到涉案车辆信息中
				prpLcheckLoss = new PrpLcheckLoss();
				prpLcheckLoss.getId().setRegistNo(prpLcheckLossRegistNo);
				prpLcheckLoss.setClaimNo(claimNo);
				prpLcheckLoss.setRiskCode(prpLcheckLossRiskCode);
				prpLcheckLoss.setPolicyNo(prpLcheckLossPolicyNo);
				prpLcheckLoss.getId().setSerialNo(intCheckLossIndex);
				prpLcheckLoss.setReferSerialNo(intCheckLossIndex);
				prpLcheckLoss.setKindCode(CommonUtils.getValue(prpLthirdPropKindCode,index));
				prpLcheckLoss.setLossFeeType("3");
				prpLcheckLoss.setLossFee(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLthirdPropLossFee,index))));
				prpLcheckLoss.setFlag("");
				intCheckLossIndex++;
				prpLcheckLossList.add(prpLcheckLoss);

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
			// 增加财产损失定损调度信息，如果有人，就进行调度
			if (thirdPropList != null && thirdPropList.size() > 0) {
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(checkDto.getPrpLcheck().getId().getRegistNo());
				prpLscheduleItem.getId().setItemNo(1);
				// 表示是否选中
				prpLscheduleItem.setSelectSend("1");
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(checkDto.getPrpLcheck().getCheckSite());
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setLicenseNo("財產損失");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setNextNodeNo("propc");
				scheduleItemList.add(prpLscheduleItem);
			}
			// 报案集合中加入损失部位
			checkDto.setPrpLthirdPropList(thirdPropList);
		}
		/*---------------------其它损失部位 PrpLthirdProp end------------------------------------*/
		/*---------------------人员伤亡跟踪 PrpLpersonTrace ------------------------------------*/
		ArrayList<PrpLpersonTrace> personTraceList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
		String prpLpersonTraceRegistNo = (String) httpServletRequest.getAttribute("registNo");
		// String prpLpersonTraceClaimNo =
		// httpServletRequest.getParameter("prpLregistRiskCode");
		String prpLpersonTracePolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
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
		String[] prpLpersonTraceMotionFlag = httpServletRequest.getParameterValues("motionFlag");
		String[] prpLpersonTraceWoundRemark = httpServletRequest.getParameterValues("prpLpersonTraceWoundRemark");
		String[] prpLpersonTraceRemark = httpServletRequest.getParameterValues("prpLpersonTraceRemark");
		String[] prpLpersonTraceFlag = httpServletRequest.getParameterValues("prpLpersonTraceFlag");

		// String[] prpLpersonTraceNewAddFlag =
		// httpServletRequest.getParameterValues("prpLpersonTraceNewAddFlag");//
		// 是否新增项目的标志

		// 损失模块信息合到涉案车辆、人伤、财产损失信息中
		// String[] prpLpersonTraceLossFee =
		// httpServletRequest.getParameterValues("prpLpersonTraceLossFee" );

		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLpersonTracePersonNo != null) {
			for (int index = 1; index < prpLpersonTracePersonNo.length; index++) {
				prpLpersonTrace = new PrpLpersonTrace();

				prpLpersonTrace.getId().setRegistNo(prpLpersonTraceRegistNo);
				prpLpersonTrace.setClaimNo(claimNo);
				prpLpersonTrace.setPolicyNo(prpLpersonTracePolicyNo);
				prpLpersonTrace.getId().setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonNo[index])));
				prpLpersonTrace.setPersonName(prpLpersonTracePersonName[index]);
				prpLpersonTrace.setPersonSex(prpLpersonTracePersonSex[index]);
				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));
				prpLpersonTrace.setIdentifyNumber(prpLpersonTraceIdentifyNumber[index]);
				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				// modify by wangshujiao at 20080225 start
				// prpLpersonTrace
				// .setReferKind(prpLpersonTraceReferKind[index]);
				if (prpLpersonTraceReferKind == null) {
					prpLpersonTrace.setReferKind("");
				} else {
					prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				}
				// prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				// modify by wangshujiao at 20080225 end
				// prpLpersonTrace.setReferKind (ConstantCodes.KINDCODE_D_B);
				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);
				prpLpersonTrace.setHospital(CommonUtils.getValue(prpLpersonTraceHospital,index));
				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);
				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);
				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);
				// 加入集合
				personTraceList.add(prpLpersonTrace);
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(checkDto.getPrpLcheck().getId().getRegistNo());
				prpLscheduleItem.getId().setItemNo(index);
				// 表示是否选中
				prpLscheduleItem.setSelectSend("1");
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(checkDto.getPrpLcheck().getCheckSite());
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setLicenseNo(prpLpersonTracePersonName[index]);
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setNextNodeNo("wound");
				// if(prpLpersonTraceNewAddFlag[prpLpersonTrace.getPersonNo()].equals("new")){
				scheduleItemList.add(prpLscheduleItem);
					// }
			}
			// 报案集合中加入损失部位
			checkDto.setPrpLpersonTraceList(personTraceList);
		}
		
		// 整理数据，整理定损调度的数据，如果当提交的时候。。将新的数据放入prplscheduleItem中，並保留已经调度过的数据
		if ("4".equals(checkDto.getPrpLclaimStatus().getStatus())&&scheduleItemList.size() > 0) {
			// 本次查勘查找到有新的调度任务
			// 检查定损调度的情况，如果存在定损调度，检查是否已经调度过，如果没有调度过，按照没有调度过处理
			// 查询调度过的
			String strSql = " registno ='" + checkDto.getPrpLcheck().getId().getRegistNo() + "' and surveyTimes='1'";
			// 查询数据
			List<PrpLscheduleItem> prpLscheduleItemList = (List<PrpLscheduleItem>) scheduleService.findItemByConditions(strSql);
			PrpLscheduleItem prpLscheduleItemold = null;
			List<PrpLscheduleItem> scheduleItemLastList = new ArrayList<PrpLscheduleItem>();
			if (prpLscheduleItemList == null || prpLscheduleItemList.size() < 1) {
				// 不用检查scheduleITem的。。
				checkDto.setPrpLscheduleItemList(scheduleItemList);
			} else { // 检查整理好的数据中，是否已经有已经调度过的数据
				// scheduleItemDtoList 是指原来从调度已经调度过的数据，无论怎么样，都是不能被删除的。
				// 只要检查 scheduleItemDtoList中存在
				// prpLscheduleItemList中没有的，就增加prpLscheduleItemList好了。
				boolean blnotFind = true;
				// scheduleItemDtoList，查勘这里收集的调度数据
				for (int i = 0; i < scheduleItemList.size(); i++) {
					prpLscheduleItem = (PrpLscheduleItem) scheduleItemList.get(i);
					// 原则，相同的，以原来的数据为准，没有的已後来的为准
					blnotFind = true;
					for (int j = 0; j < prpLscheduleItemList.size(); j++) {
						prpLscheduleItemold = prpLscheduleItemList.get(j);
						if(prpLscheduleItemold.getNextNodeNo().equals(prpLscheduleItem.getNextNodeNo())){
							if (prpLscheduleItem.getId().getItemNo().intValue() == prpLscheduleItemold.getId().getItemNo().intValue()) { // 如果存在旧的数据，就要用旧的数据，不要用新的数据
								blnotFind = false;
								break;
							}
						}
						// 原则，相同的，以原来的数据为准，没有的已後来的为准
					}
					if (blnotFind) {
						scheduleItemLastList.add(prpLscheduleItem);
					}
				}
				// 最後把原来已经调度过的数据再增加回去
				scheduleItemLastList.addAll(prpLscheduleItemList);
				checkDto.setPrpLscheduleItemList(scheduleItemLastList);
			}
		}
		// 损失部位模块信息调整到涉案车辆信息中，相应模块做调整
		/*---------------------损失部位 PrpLthirdCarLoss begin------------------------------------*/
		List<PrpLthirdCarLoss> thirdCarLossDtoList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdCarLossRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
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
		String[] prpLthirdCarLossKindCode = httpServletRequest.getParameterValues("kindCode"); // 险别代码
		// 车险查勘要求可以多险同时出
		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdCarLossSerialNo == null) {
		} else {
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
				prpLthirdCarLoss.setKindCode(prpLthirdCarLossKindCode[index]);
				// 加入集合
				thirdCarLossDtoList.add(prpLthirdCarLoss);
			}
			// 查勘集合中加入损失部位
			checkDto.setPrpLthirdCarLossList(thirdCarLossDtoList);
		}
		/*---------------------损失部位 PrpLthirdCarLoss end------------------------------------*/
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtDtoList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRegistNo = (String) httpServletRequest.getParameter("prpLregistExtRegistNo");
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");

		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo == null) {
		} else {
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
				prpLregistExtDtoList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			checkDto.setPrpLregistExtList(prpLregistExtDtoList);
		}
		// 损失模块信息合到涉案车辆、人伤、财产损失信息中;最後损失信息放入checkDto对象中
		checkDto.setPrpLcheckLossList(prpLcheckLossList);
		// 加入货运险查勘信息
		// 货运险险种包括02%的 在if条件中加入02的
		if (prpLcheckLossRiskCode != null && prpLcheckLossRiskCode.length() > 0) {
			String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLcheckLossRiskCode);

			if ("C".equals(strRiskType)) {
				PrpLext prpLext = new PrpLext();

				String prpLextRegistNo = (String) httpServletRequest.getAttribute("registNo");
				String prpLextAppliCheckDate = httpServletRequest.getParameter("prpLextAppliCheckDate");
				String prpLextAppliPhone = httpServletRequest.getParameter("prpLextAppliPhone");
				String prpLextInsuredPhone = httpServletRequest.getParameter("prpLextInsuredPhone");
				String prplextSailStartDate = httpServletRequest.getParameter("prplextSailStartDate");
				String prpLextSumValue = httpServletRequest.getParameter("prpLextSumValue");
				String prpLextLoadCheckCompany = httpServletRequest.getParameter("prpLextLoadCheckCompany");
				String prpLextUnloadDate = httpServletRequest.getParameter("prpLextUnloadDate");
				String prpLextCurrency = httpServletRequest.getParameter("prpLextCurrency");
				String prpLextValue2 = httpServletRequest.getParameter("prpLextValue2");
				String prpLextValue1 = httpServletRequest.getParameter("prpLextValue1");
				String prpLextRestQuantity = httpServletRequest.getParameter("prpLextRestQuantity");
				String prpLextLoadingNo = httpServletRequest.getParameter("prpLextLoadingNo");
				String prpLextInvoiceNo = httpServletRequest.getParameter("prpLextInvoiceNo");
				String prpLextSalvor = httpServletRequest.getParameter("prpLextSalvor");

				// 给DTO赋值，将界面得到得数值保存在DTO对象中
				prpLext.getId().setCertiNo(prpLextRegistNo);
				prpLext.getId().setCertiType("02");
				prpLext.setAppliCheckDate(new DateTime(prpLextAppliCheckDate, DateTime.YEAR_TO_DAY));
				prpLext.setAppliPhone(prpLextAppliPhone);
				prpLext.setInsuredPhone(prpLextInsuredPhone);
				prpLext.setSailStartDate(new DateTime(prplextSailStartDate, DateTime.YEAR_TO_DAY));
				prpLext.setCargoValue(Double.parseDouble(prpLextSumValue));
				prpLext.setCargoLossCheckCom(prpLextLoadCheckCompany);
				prpLext.setUnloadDate(new DateTime(prpLextUnloadDate, DateTime.YEAR_TO_DAY));
				prpLext.setCurrency(prpLextCurrency);
				prpLext.setSalvor(prpLextSalvor);
				prpLext.setValue2(prpLextValue2);
				prpLext.setValue1(prpLextValue1);
				prpLext.setRestQuantity(Integer.parseInt(prpLextRestQuantity));
				prpLext.setRemark(prpLextLoadingNo);// 用备注存储，提单运单号码
				prpLext.setValue3(prpLextInvoiceNo);// value3存储发票号码
				checkDto.setPrpLext(prpLext);
			}
			// 若是意健险则还要保存相应的调查费用信息
			//伤害险
			if ("E".equals(strRiskType)) {
				// 获得相应的调查号
				String prpLacciCheckCheckNo = httpServletRequest.getParameter("prpLacciCheckCheckNo");
				// 获得费用类别代码
				String[] prpLAcciCheckChargeCode = httpServletRequest.getParameterValues("prpLAcciCheckChargeCode");
				// 获得费用类别名称
				String[] prpLAcciCheckChargeName = httpServletRequest.getParameterValues("prpLAcciCheckChargeName");
				// 获得币别代码
				String[] prpLAcciCheckChargeCurrency = httpServletRequest.getParameterValues("prpLAcciCheckChargeCurrency");
				// //获得币别名称
				// String[] prpLAcciCheckChargeCurrencyName =
				// httpServletRequest.getParameterValues("prpLAcciCheckChargeCurrencyName");
				// 获得费用金额
				String[] prpLAcciCheckChargeAmount = httpServletRequest.getParameterValues("prpLAcciCheckChargeAmount");
				// 定义调查费用List
				List<PrpLacciCheckCharge> PrpLacciCheckChargeList = new ArrayList<PrpLacciCheckCharge>();
				double chargeAmount = 0;
				for (int i = 1; i < prpLAcciCheckChargeCode.length; i++) {
					PrpLacciCheckCharge prpLacciCheckCharge = new PrpLacciCheckCharge();
					prpLacciCheckCharge.setId(new PrpLacciCheckChargeId());
					prpLacciCheckCharge.getId().setCheckNo(prpLacciCheckCheckNo);
					prpLacciCheckCharge.getId().setSerialNo(i);
					prpLacciCheckCharge.setChargeCode(prpLAcciCheckChargeCode[i]);
					prpLacciCheckCharge.setChargeName(prpLAcciCheckChargeName[i]);
					prpLacciCheckCharge.setCurrency(prpLAcciCheckChargeCurrency[i]);
					chargeAmount = Double.valueOf(DataUtils.nullToZero(prpLAcciCheckChargeAmount[i]));
					prpLacciCheckCharge.setChargeAmount(chargeAmount);
					PrpLacciCheckChargeList.add(prpLacciCheckCharge);
				}
				checkDto.getAcciCheckDto().setPrpLacciCheckChargeList(PrpLacciCheckChargeList);
			}
			//车险
			if ("D".equals(strRiskType)) {
				// "车上人员伤人数"四个字段其中任意一个没有填值,包括0也不填的情况下,点击"提交"报错
				String personDeathB = httpServletRequest.getParameter("prpLregistPersonDeathB");
				if (personDeathB == null || personDeathB.equals("")) {
					personDeathB = "0";
				}
				String personInjureB = httpServletRequest.getParameter("prpLregistPersonInjureB");
				if (personInjureB == null || personInjureB.equals("")) {
					personInjureB = "0";
				}
				String personDeathD1 = httpServletRequest.getParameter("prpLregistPersonDeathD1");
				if (personDeathD1 == null || personDeathD1.equals("")) {
					personDeathD1 = "0";
				}
				String personInjureD1 = httpServletRequest.getParameter("prpLregistPersonInjureD1");
				if (personInjureD1 == null || personInjureD1.equals("")) {
					personInjureD1 = "0";
				}
				PrpLext prpLext = new PrpLext();
				String certiNo = (String) httpServletRequest.getAttribute("registNo");
				prpLext.getId().setCertiNo(certiNo);
				prpLext.getId().setCertiType("01");
				prpLext.setPersonDeathB(Integer.parseInt(personDeathB));
				prpLext.setPersonInjureB(Integer.parseInt(personInjureB));
				prpLext.setPersonDeathD1(Integer.parseInt(personDeathD1));
				prpLext.setPersonInjureD1(Integer.parseInt(personInjureD1));
				checkDto.setPrpLext(prpLext);
			}
			//火险
			if("Q".equals(strRiskType)){
				/*---------------------险别估损金额PrpLclaimloss */
				List<PrpLclaimLoss> claimLossList = new ArrayList<PrpLclaimLoss>();
				PrpLclaimLoss prpLclaimLoss = null;
				// 理赔拆分危险单位
				String[] prpLclaimLossDangerNo = httpServletRequest.getParameterValues("prpLclaimLossDangerNo");
				String[] prpLclaimLossItemKindNo = httpServletRequest.getParameterValues("prpLclaimLossItemKindNo");
//				int[] prpLclaimLossItemKindNo;
				String[] prpLclaimLossFeeCategory = httpServletRequest.getParameterValues("prpLclaimLossFeeCategory");
				String[] prpLclaimLossKindCode = httpServletRequest.getParameterValues("prpLclaimLossKindCode");
				String[] prpLclaimLossItemCode = httpServletRequest.getParameterValues("prpLclaimLossItemCode");
				String[] prpLclaimLossItemDetailName = httpServletRequest.getParameterValues("prpLclaimLossItemDetailName");
				String[] prpLclaimLossCurrency = httpServletRequest.getParameterValues("prpLclaimLossCurrency");
				String[] prpLclaimLossSumClaim = httpServletRequest.getParameterValues("prpLclaimLossSumClaim");
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
				String[] prpLclaimLossAmount = httpServletRequest.getParameterValues("prpLclaimLossAmount");
				if(prpLclaimLossAmount==null){
					prpLclaimLossAmount = new String[prpLclaimLossKindCode.length];
				}
				String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
				// 原因：添加标志字段，用於区别赔款和费用。
				String[] prpLregsitLossFeeType = httpServletRequest.getParameterValues("prpLclaimLossLossFeeType");
				// 对象赋值
				// -估损金额部分开始
				if (prpLclaimLossCurrency != null) {
					for (int index = 1; index < prpLclaimLossCurrency.length; index++) {
						prpLclaimLoss = new PrpLclaimLoss();
						prpLclaimLoss.getId().setClaimNo(claimNo);
						if(CommonUtils.isEmpty(claimNo)){
							prpLclaimLoss.getId().setClaimNo(prpLcheckLossRegistNo);
						}
						prpLclaimLoss.setRegistNo(prpLcheckLossRegistNo);
						prpLclaimLoss.setRiskCode(prpLcheckLossRiskCode);
						prpLclaimLoss.getId().setSerialNo(index);
						prpLclaimLoss.setItemKindNo(Integer.parseInt(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossItemKindNo,index))));
						prpLclaimLoss.setKindCode(prpLclaimLossKindCode[index]);
						// 理赔拆分危险单位
						prpLclaimLoss.setDangerNo(Integer.parseInt(prpLclaimLossDangerNo[index]));
						prpLclaimLoss.setFeeCategory(prpLclaimLossFeeCategory[index]);
						prpLclaimLoss.setKindRest(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossKindRest[index])));
						prpLclaimLoss.setItemCode(CommonUtils.getValue(prpLclaimLossItemCode,index));
						prpLclaimLoss.setItemDetailName(CommonUtils.getValue(prpLclaimLossItemDetailName,index));
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
						prpLclaimLoss.setFlag(CommonUtils.getValue(prpLclaimLossFlag,index));
						prpLclaimLoss.setLossFeeType(CommonUtils.getValue(prpLregsitLossFeeType,index));
						prpLclaimLoss.setAcciDeductiblePay(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossAcciDeductiblePay,index))));
						if (prpLclaimLossAcciDeductibleRate != null && prpLclaimLossAcciDeductibleRate.length > 0) {
							prpLclaimLoss.setAcciDeductibleRate(Double.parseDouble(DataUtils.nullToZero(prpLclaimLossAcciDeductibleRate[index])));
						}
						prpLclaimLoss.setDeductible(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossDeductible,index))));
						prpLclaimLoss.setAmount(Double.parseDouble(DataUtils.nullToZero(CommonUtils.getValue(prpLclaimLossAmount,index))));
						// 加入集合
						claimLossList.add(prpLclaimLoss);
						checkDto.setPrpLclaimLossList(claimLossList);
					}
				}
			}
		}
		// 存储巨灾代码信息
		String strCatastropheCode1 = httpServletRequest.getParameter("prpCatastropheCode1");
		String strCatastropheName1 = httpServletRequest.getParameter("prpCatastropheName1");
		String strCatastropheCode2 = httpServletRequest.getParameter("prpCatastropheCode2");
		String strCatastropheName2 = httpServletRequest.getParameter("prpCatastropheName2");
		PrpLregist prpLregist = new PrpLregist();
		prpLregist = prpLregistService.findPrpLregist(checkDto.getPrpLcheck().getId().getRegistNo());
		prpLregist.setCatastropheCode1(strCatastropheCode1);
		prpLregist.setCatastropheName1(strCatastropheName1);
		prpLregist.setCatastropheCode2(strCatastropheCode2);
		prpLregist.setCatastropheName2(strCatastropheName2);
		checkDto.setPrpLregist(prpLregist);
		return checkDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写查勘单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public CheckDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		CheckDto checkDto = new CheckDto();
		return checkDto;
	}

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, CheckDto checkDto) throws Exception {
		// 得到request的PrpLcheckForm用於显示
		PrpLcheck prpLcheck = checkDto.getPrpLcheck();
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);
	}

	public void checkDtoView(HttpServletRequest httpServletRequest, String checkNo) throws Exception {
		// 意健险在这个环节的checktNo为调查号,长度大於21位,需要截取为21长.
		String checkNoTemp = checkNo;
		if (checkNo.length() > 21) {
			checkNo = checkNo.substring(0, 21);
		}

		// 取得当前用户信息，写操作员信息到查勘中
		// HttpSession session = httpServletRequest.getSession();
		// UserDto user = (UserDto) session.getAttribute("user");
		// String lossItemName = "";
		// httpServletRequest.getParameter("lossItemName");
		// String insureCarFlag
		// =httpServletRequest.getParameter("insureCarFlag");
		CheckDto checkDto = checkService.findByPrimaryKey(checkNo);

		// 原因：区分意健和非意键险
		RegistDto registDto = registService.findByPrimaryKey(checkNo);
		PrpLregist prpLregist = registDto.getPrpLregist();
		String strRiskType = this.codeService.translateRiskCodetoRiskType(prpLregist.getRiskCode());

		// 定损核损查看查勘信息时，如还未进行查勘需提示
		if (checkDto.getPrpLcheck() == null && !"E".equals(strRiskType)) {
			throw new UserException(1, 3, "", "該賠案還未進行查勘輸入操作！");
		}
		// 定损核损查看查勘信息时，如还未进行查勘需提示
		PrpLcheck prpLcheck = checkDto.getPrpLcheck();

		if ("E".equals(strRiskType)) { // 如果是意外健康险的情况
			// 按调查号进行查询
			if (httpServletRequest.getParameter("keyIn") != null && !("".equals(httpServletRequest.getParameter("keyIn"))) && !"null".equals(httpServletRequest.getParameter("keyIn"))) {
				checkNoTemp = httpServletRequest.getParameter("keyIn");
			} else { // 如果是在调查查询的状态下，则可以进行调查号查询的。
				if (!CommonUtils.isEmpty(httpServletRequest.getParameter("accicheckNo"))) {
					checkNoTemp = httpServletRequest.getParameter("accicheckNo");
				}
			}
			AcciCheckDto acciCheckDto = acciCheckService.findByPrimaryKey(checkNoTemp);
			PrpLacciCheck prpLacciCheck = acciCheckDto.getPrpLacciCheck();
			httpServletRequest.setAttribute("acciCheckDto", acciCheckDto);
			UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
			if (prpLacciCheck != null) {
				// 得到报案时间
				prpLacciCheck.setDamageStartDate(new DateTime(acciCheckDto.getPrpLregist().getDamageStartDate()));
				String timeTemp = StringConvert.toStandardTime(acciCheckDto.getPrpLregist().getDamageStartHour());
				prpLacciCheck.setDamageStartMinute(timeTemp.substring(3, 5));
				prpLacciCheck.setDamageStartHour(timeTemp.substring(0, 2));
				prpLacciCheck.setDamageAddress(acciCheckDto.getPrpLregist().getDamageAddress());
				prpLacciCheck.setPolicyNo(acciCheckDto.getPrpLregist().getPolicyNo());
				prpLacciCheck.setHandleDept(user.getComCode());
				if(prpLacciCheck.getCheckDate()==null){
					// 得到当前的时间
					prpLacciCheck.setCheckDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				}
				String hour = prpLacciCheck.getCheckHour();
				if(CommonUtils.isEmpty(hour)){
					prpLacciCheck.setCheckHour(String.valueOf(DateTime.current().getHour()));
					prpLacciCheck.setDamageStartMinute2(String.valueOf(DateTime.current().getMinute()));
				}else if(hour.indexOf(":")>-1){
					prpLacciCheck.setCheckHour(hour.substring(0,hour.indexOf(":")));
					prpLacciCheck.setDamageStartMinute2(hour.substring(hour.indexOf(":")+1));
					
				}
				
				if(prpLacciCheck.getCheckEndDate()==null){
					prpLacciCheck.setCheckEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				}
				hour = prpLacciCheck.getCheckEndHour();
				if(CommonUtils.isEmpty(hour)){
					prpLacciCheck.setCheckEndHour(String.valueOf(DateTime.current().getHour()));
					prpLacciCheck.setDamageStartMinute3(String.valueOf(DateTime.current().getMinute()));
				}else if(hour.indexOf(":")>-1){
					prpLacciCheck.setCheckEndHour(hour.substring(0,hour.indexOf(":")));
					prpLacciCheck.setDamageStartMinute3(hour.substring(hour.indexOf(":")+1));
				}

				// 增加调查费用币别
				String currencyName = this.codeService.translateCurrencyCode(prpLacciCheck.getCurrency(), true);
				prpLacciCheck.setCurrencyName(currencyName);

				// 添加备注信息
				// prpLacciCheckDto.setRemark(acciCheckDto.getPrpLregist().getRemark());
				httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);

				// [调查描述]给调查描述多行列表准备数据
				PrpLacciCheckText prpLacciCheckText = new PrpLacciCheckText();
				String tempContext = "";
				if (checkDto.getAcciCheckDto().getPrpLacciCheckTextList() != null) {
					Iterator<PrpLacciCheckText> iterator = checkDto.getAcciCheckDto().getPrpLacciCheckTextList().iterator();
					while (iterator.hasNext()) {
						PrpLacciCheckText prpLacciCheckTextDtoTemp2 = (PrpLacciCheckText) iterator.next();
						tempContext = tempContext + prpLacciCheckTextDtoTemp2.getContext();
					}
				}
				prpLacciCheckText.setContext(tempContext);
				prpLacciCheckText.getId().setTextType("3");
				httpServletRequest.setAttribute("prpLacciCheckTextDto", prpLacciCheckText);

				if (prpLcheck == null) {
					prpLcheck = new PrpLcheck();
				}

				if (checkDto.getPrpLclaimStatus() != null) {
					prpLcheck.setStatus(checkDto.getPrpLclaimStatus().getStatus());
				} else {
					// 已提交，已经处理完毕的状态
					prpLcheck.setStatus("4");
				}
				String nodeStatus = httpServletRequest.getParameter("status");
				if (nodeStatus != null) {
					prpLcheck.setStatus(nodeStatus);
				}
				prpLcheck.setRiskCode(acciCheckDto.getPrpLacciCheck().getRiskCode());

				changeCodeToName(httpServletRequest, prpLcheck);

				this.daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, acciCheckDto.getPrpLacciCheck().getPolicyNo(), acciCheckDto.getPrpLacciCheck().getRegistNo());
				// 三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
				String strPrpLnodeType = "check";
				httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);
				// 设置各个子表信息项到窗体表单
				// setSubInfo(httpServletRequest,checkDto);
				// 设置工作流下一个节点提交的配置信息
				getSubmitNodes(httpServletRequest);
				// 设置主查勘信息内容到窗体表单
				httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLregist.getRiskCode(), true));
				httpServletRequest.setAttribute("prpLcheck", prpLcheck);
				httpServletRequest.setAttribute("prpLregist", prpLregist);
				// 设置窗体表单中各个多选框中列表信息的内容
				setSelectionList(httpServletRequest, prpLcheck);
			} else {
				throw new Exception("資料庫中沒有這條數據！");
			}
			// 送审初复核初始化
			// 待翻译
			this.sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, prpLcheck.getId().getRegistNo(), "check");
			// 送审初复核初始化
			return; // 结束所有意外健康险调查的调用过程。
		}

		// 非意外健康险的查勘都以下部分的代码
		// 不清楚含义prpLcheckDto.getReferSerialNo()
		prpLcheck.setLossItemCode(prpLcheck.getId().getReferSerialNo() + "");
		// 设置查勘操作的状态为 案件修改 (正处理任务)
		if (checkDto.getPrpLclaimStatus() != null) {
			prpLcheck.setStatus(checkDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLcheck.setStatus("4");
		}
		String nodeStatus = httpServletRequest.getParameter("status");
		if (nodeStatus != null) {
			prpLcheck.setStatus(nodeStatus);
		}

		// UIRegistAction uiRegistAction = registService;
		// RegistDto registDto = registService.findByPrimaryKey(checkNo);
		// [没有车损险时，页面上不显示选择标的车是否受损的标志]
		String policyNo = prpLregist.getPolicyNo();
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();

		String kindCode = "";
		String kindAFlag = "0"; // 出险时有无保车损险的标志 1：有 0：无
		// String kindBFlag = "0"; // 出险时有无保三者险的标志 1：有 0：无
		// 根据出险时间找到保单
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList= this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			String sameAddressNo = prpCaddressList.get(0).getSameAddressNo();
			prpLcheck.setSameAddressNo(sameAddressNo);
		}
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpLregist.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (int k = 0; k < prpCitemKindList.size(); k++) {
				PrpCitemKind prpCitemKind = (PrpCitemKind) prpCitemKindList.get(k);
				kindCode = prpCitemKind.getKindCode();
				if (kindCode.equals(ConstantCodes.KINDCODE_A01_01)
						|| kindCode.equals(ConstantCodes.KINDCODE_A01_05)
						||kindCode.equals(ConstantCodes.KINDCODE_A01_0J)) {
					kindAFlag = "1";
					break;
				}
			}
		}
		httpServletRequest.setAttribute("kindAFlag", kindAFlag);

		// [没有车损险时，页面上不显示选择标的车是否受损的标志]
		prpLcheck.setDamageStartDate(new DateTime(prpLregist.getDamageStartDate()));
		prpLcheck.setDamageEndDate(new DateTime(prpLregist.getDamageEndDate()));
		prpLcheck.setRegistEstimateFee(prpLregist.getEstimateFee());
		prpLcheck.setRegistEstimateLoss(prpLregist.getEstimateLoss());
		// prpLregist.setDamageStartHour(StringConvert.toStandardTime(prpLregist.getDamageStartHour()));
		String timeTemp = StringConvert.toStandardTime(prpLregist.getDamageStartHour());
		prpLcheck.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLcheck.setDamageStartHour(timeTemp.substring(0, 2));
		timeTemp = StringConvert.toStandardTime(prpLregist.getDamageEndHour());
		prpLcheck.setDamageEndMinute(timeTemp.substring(3, 5));
		prpLcheck.setDamageEndHour(timeTemp.substring(0, 2));
		prpLcheck.setInsuredName(prpLregist.getInsuredName());
		prpLcheck.setSumAmount(prpCmain.getSumAmount());
		prpLcheck.setCurrency(prpLregist.getEstiCurrency());
		String strCurrencyName = this.codeService.translateCurrencyCode(prpLcheck.getCurrency(), true);
		httpServletRequest.setAttribute("strCurrencyName", strCurrencyName);
		// prpLcheckDto.setDamageAddress(prpLregist
		// .getDamageAddress());
		// 为了保证立案後三者车的顺利保存
		// 如果立案取出赔案号，否则会出现问题
		String claimNo = this.codeService.translateBusinessCode(checkNo, true);
		prpLcheck.setClaimNo(claimNo);
		if (registDto != null && prpLregist != null) {
			prpLcheck.setLossItemName(prpLregist.getLicenseNo());
			// prpLcheckDto.setInsuredName(prpLregist.getInsuredName());
		}
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLcheck);

		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcheck);
		// 查询相同保单号的出险次数
		daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLcheck.getPolicyNo(), prpLcheck.getId().getRegistNo());

		// 三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "check";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, checkDto);

		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);
		// 设置主查勘信息内容到窗体表单
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);
		httpServletRequest.setAttribute("registContext", getRegistContext(prpLcheck));

		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLcheck.getRiskCode(), true));
		if ("D".equals(strRiskType)) {
			PrpLext prpLext = (PrpLext) checkDto.getPrpLext();

			if (prpLext != null) {
				prpLregist.setPersonDeathB(prpLext.getPersonDeathB());
				prpLregist.setPersonInjureB(prpLext.getPersonInjureB());
				prpLregist.setPersonDeathD1(prpLext.getPersonDeathD1());
				prpLregist.setPersonInjureD1(prpLext.getPersonInjureD1());
			}
			registDto.setPrpLext(prpLext);
		}
		// 应从查勘对象中取赔案类型
		prpLregist.setClaimType(prpLcheck.getClaimType());

		httpServletRequest.setAttribute("prpLregist", prpLregist);
		// 原因：转换代理人
		httpServletRequest.setAttribute("com.sinosoft.agentCode", registDto.getIdentifierName());
		//PolicyDto policyDto = this.endorseViewHelper.findForEndorBefore(policyNo, prpLregist.getDamageStartDate().toString(), prpLregist.getDamageStartHour());
		// 增加货运险扩展信息
		// 货运险险种包括02%的 在if条件中加入02的
		if ("C".equals(strRiskType)) {
			PrpLext prpLext = (PrpLext) checkDto.getPrpLext();
			String currency = prpLext.getCurrency();
			String currencyName = this.codeService.translateCurrencyCode(currency, true);
			prpLext.setCurrencyCname(currencyName);
			prpLext.setSumAmount(String.valueOf(prpCmain.getSumAmount())); // 保额，从保单带入
			prpLext.setLimitAmount("0"); // 免赔额 暂时未赋值
			String rootComName = AppConfig.get("sysconst.ROOTCOMCNAME");
			prpLext.setPrpCompanyName(rootComName);
			String itemAll = "";
			if (prpCitemKindList != null && prpCitemKindList.size() > 0) {
				Iterator<PrpCitemKind> item = prpCitemKindList.iterator();
				while (item.hasNext()) {
					PrpCitemKind itemKindDto = item.next();
					itemAll = itemAll + itemKindDto.getItemDetailName();
					double account = itemKindDto.getQuantity();
					String model = itemKindDto.getModel();
					if (account > 0) {
						itemAll = itemAll + "; 共" + account + model;
					}
					prpLext.setLimitAmount(String.valueOf(itemKindDto.getDeductible())); // 免赔额
					break;
				}
			}
			httpServletRequest.setAttribute("prpLcarGo", this.prpCmainCargoService.findPrpCmainCargo(policyNo));
			if (prpLext == null) {
				prpLext = new PrpLext();
			}
			httpServletRequest.setAttribute("prpLext", prpLext);
		}
		// 展现事故管界
		//PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLcheck.getId().getRegistNo());
		String section = prpLregist.getSection();
		String sectionName = prpLregist.getSectionName();
		if (section == null) {
			section = "";
			sectionName = "";
		}
		List<PrpCinsured> list = null;
		if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			//意健險被保險人多，只針對被保險回滾
			String insuredCode = prpLregist.getInsuredCode();
			String insuredName = prpLregist.getInsuredName();
			list = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		} else {
			list = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
		}
		for (int i = 0; i < list.size(); i++) {
			PrpCinsured prpCinsure =  list.get(i);
			if ("1".equalsIgnoreCase(prpCinsure.getInsuredFlag())) {
				httpServletRequest.setAttribute("prpCinsured", prpCinsure);
			}
		}

		httpServletRequest.setAttribute("section", section);
		httpServletRequest.setAttribute("sectionName", sectionName);

		// 送审初复核初始化
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, prpLcheck.getId().getRegistNo(), "check");

	}

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void registDtoToView(HttpServletRequest httpServletRequest, String registNo, String editType) throws Exception {
		// 意健险在这个环节的registNo为调查号,长度大於21位,需要截取为21长.
		if (registNo.length() > 21) {
			registNo = registNo.substring(0, 21);
		}
		// 取得当前用户信息，写操作员信息到查勘中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		if (user == null) {
			user = new UserDto();
		}
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		PrpLregist prpLregist = registDto.getPrpLregist();
		PrpLcheck prpLcheck = new PrpLcheck();
		String claimNo = this.codeService.translateBusinessCode(registNo, true);

		// [没有车损险时，页面上不显示选择标的车是否受损的标志]-20060426-start--------------------------

		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		// String policyNo = prpLregist.getPolicyNo();
		// 获取事故管界代码
		String section = prpLregist.getSection();
		String sectionName = prpLregist.getSectionName();
		if (section == null) {
			section = "";
			sectionName = "";
		}
		httpServletRequest.setAttribute("section", section);
		httpServletRequest.setAttribute("sectionName", sectionName);

		String kindCode = "";
		String kindAFlag = "0"; // 出险时有无保车损险的标志 1：有 0：无
		// String kindBFlag = "0"; // 出险时有无保三者险的标志 1：有 0：无
		double prpLDeductible = 0.0; // 可选免赔额
		// 根据出险时间找到保单
		String riskCode = prpLregist.getRiskCode();
		String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		String policyNo = prpLregist.getPolicyNo();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			List<PrpCitemKind> prpCitemKindDtoList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, riskCode, null);
			if (!CommonUtils.isEmpty(prpCitemKindDtoList)) {
				for (int k = 0; k < prpCitemKindDtoList.size(); k++) {
					PrpCitemKind prpCitemKindDto = prpCitemKindDtoList.get(k);
					kindCode = prpCitemKindDto.getKindCode();
					if (kindCode.equals(ConstantCodes.KINDCODE_A01_01)
							||kindCode.equals(ConstantCodes.KINDCODE_A01_05)
							||kindCode.equals(ConstantCodes.KINDCODE_A01_0J)) {
						kindAFlag = "1";
					}
					if ("M1".equals(kindCode)) {
						prpLDeductible = prpCitemKindDto.getValue();
					}
				}
			}
		}
		httpServletRequest.setAttribute("kindAFlag", kindAFlag);
		httpServletRequest.setAttribute("prpLDeductible", new Double(prpLDeductible).toString());

		// [没有车损险时，页面上不显示选择标的车是否受损的标志]-20060426-end--------------------------

		// 原因：区分意健和非意键险
		if ("E".equals(strRiskType)) {
			// 按调查号进行查询 2005-08-16
			AcciCheckDto acciCheckDto = acciCheckService.findByPrimaryKey(httpServletRequest.getParameter("keyIn"));
			PrpLacciCheck prpLacciCheck = acciCheckDto.getPrpLacciCheck();
			httpServletRequest.setAttribute("acciCheckDto", acciCheckDto);

			if (prpLacciCheck != null) {
				// 得到报案时间
				prpLacciCheck.setDamageStartDate(new DateTime(acciCheckDto.getPrpLregist().getDamageStartDate()));
				String timeTemp = StringConvert.toStandardTime(acciCheckDto.getPrpLregist().getDamageStartHour());
				prpLacciCheck.setDamageStartMinute(timeTemp.substring(3, 5));
				prpLacciCheck.setDamageStartHour(timeTemp.substring(0, 2));
				prpLacciCheck.setDamageAddress(acciCheckDto.getPrpLregist().getDamageAddress());
				prpLacciCheck.setPolicyNo(acciCheckDto.getPrpLregist().getPolicyNo());
				prpLacciCheck.setHandleDept(user.getComCode());

				// 从报案表中得到出险代码和出险原因 2005-09-02
				prpLacciCheck.setDamageCode(prpLregist.getDamageCode());
				prpLacciCheck.setDamageName(prpLregist.getDamageName());

				// 从报案表中得到事故类型 205-09-06
				prpLacciCheck.setDamageTypeCode(prpLregist.getDamageTypeCode());
				prpLacciCheck.setDamageTypeName(prpLregist.getDamageTypeName());

				// 得到当前的时间
				prpLacciCheck.setCheckDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				prpLacciCheck.setCheckHour(String.valueOf(DateTime.current().getHour()));
				prpLacciCheck.setDamageStartMinute2(String.valueOf(DateTime.current().getMinute()));

				prpLacciCheck.setCheckEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				prpLacciCheck.setCheckEndHour(String.valueOf(DateTime.current().getHour()));
				prpLacciCheck.setDamageStartMinute3(String.valueOf(DateTime.current().getMinute()));

				// 添加备注信息
				prpLacciCheck.setRemark(acciCheckDto.getPrpLregist().getRemark());
				// 设置默认币别为"人民币"
				prpLacciCheck.setCurrency(ConstantCodes.LOCAL_CURRENCY);
				String currencyName = this.codeService.translateCurrencyCode(prpLacciCheck.getCurrency(), true);
				prpLacciCheck.setCurrencyName(currencyName);
				httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);
				// 赔款费用数据-----------------------------------------------------
				// [调查描述]给调查描述多行列表准备数据
				PrpLacciCheckText prpLacciCheckText = new PrpLacciCheckText();
				String tempContext = "";
				if (registDto.getAcciCheckDto().getPrpLacciCheckTextList() != null) {
					Iterator<PrpLacciCheckText> iterator = registDto.getAcciCheckDto().getPrpLacciCheckTextList().iterator();
					while (iterator.hasNext()) {
						PrpLacciCheckText prpLacciCheckTextDtoTemp2 = (PrpLacciCheckText) iterator.next();
						tempContext = tempContext + prpLacciCheckTextDtoTemp2.getContext();
					}
				}
				prpLacciCheckText.setContext(tempContext);
				prpLacciCheckText.getId().setTextType("3");
				httpServletRequest.setAttribute("prpLacciCheckText", prpLacciCheckText);
				// 得到共保信息
				if (prpCmain != null) {
					httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
					httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
				}
			} else {
				throw new Exception("資料庫中没有这条数据！");
			}
		}

		// 原因：要在界面上显示一些立案信息
		List<?> registClaimDtoList = this.claimService.findByPolicyNo(prpLregist.getPolicyNo());
		httpServletRequest.setAttribute("registClaimDtoList", registClaimDtoList);
		httpServletRequest.setAttribute("com.sinosoft.agentCode", registDto.getIdentifierName());
		// 查询是否已经录入过查勘信息。
		if (checkService.isExist(registNo)) {
			checkDtoView(httpServletRequest, registNo);
			return;
		}
		// 如果有数值的话，重新给界面复制
		// 根据查询出来的数据内容，给PrpLcheckDto赋值
		// 设置数值
		// prpLcheckDto.setInsureCarFlag(insureCarFlag);
		prpLcheck.getId().setReferSerialNo(1);
		// prpLcheckDto.setLossItemCode(lossItemCode);
		// prpLcheckDto.setLossItemName(lossItemName);
		prpLcheck.getId().setRegistNo(prpLregist.getRegistNo());
		// 如果在查勘前已经立案了，那么如果不把赔案号放入的话会出问题
		prpLcheck.setClaimNo(claimNo);
		prpLcheck.setRiskCode(prpLregist.getRiskCode());
		prpLcheck.setPolicyNo(prpLregist.getPolicyNo());
		// prpLcheckDto.setCheckType("L");
		// //查勘界面的查勘类型由调度表带出
		if (registDto.getPrpLscheduleMainWF() != null) {
			prpLcheck.setCheckType(registDto.getPrpLscheduleMainWF().getFlag());
		}
		// 原因：增加报损金额和报损费用
		prpLcheck.setRegistEstimateLoss(prpLregist.getEstimateLoss());
		prpLcheck.setRegistEstimateFee(prpLregist.getEstimateFee());
		// reason:查勘界面出险地点由报案带出，查勘地址默认为出险地点

		// 查勘界面的查勘地址由调度表带出
		if (registDto.getPrpLscheduleMainWF() != null) {
			prpLcheck.setCheckSite(registDto.getPrpLscheduleMainWF().getCheckSite());
		}else{
			//如果没有调度，就从备案的出险地点带出。
			prpLcheck.setCheckSite(prpLregist.getDamageAddress());
		}
		prpLcheck.setFirstSiteFlag(prpLregist.getFirstSiteFlag());
		prpLcheck.setClaimType(prpLregist.getClaimType());
		prpLcheck.setDamageCode(prpLregist.getDamageCode());
		prpLcheck.setDamageName(prpLregist.getDamageName());
		// 客制化 带出强制险的
		prpLcheck.setDamageCodeBZ(prpLregist.getDamageCodeBZ());
		prpLcheck.setDamageNameBZ(prpLregist.getDamageNameBZ());
		prpLcheck.setCoinsFlag(prpLregist.getCoinsFlag());
		// 设置事故责任
		for (PrpLthirdParty prpLthirdParty : registDto.getPrpLthirdPartyList()) {
			if ("1".equals(prpLthirdParty.getInsureCarFlag())) {
				prpLcheck.setIndemnityDuty(this.getIndemnityDuty(prpLthirdParty.getDutyPercent()));
				prpLcheck.setLicenseNo(prpLthirdParty.getLicenseNo());
				break;
			}
		}
		prpLcheck.setDamageTypeCode(prpLregist.getDamageTypeCode());
		prpLcheck.setDamageTypeName(prpLregist.getDamageTypeName());
		// prpLcheckDto.setReferKind(prpLregist.getReferKind());
		prpLcheck.setReferKind("A,M,L");
		prpLcheck.setDamageAreaCode(prpLregist.getDamageAreaCode());
		prpLcheck.setDamageAddressType(prpLregist.getDamageAddressType());
		// prpLcheck.setIndemnityDuty(prpLregist.getIndemnityDuty());
		// prpLcheck.setClaimFlag(prpLregist.getClaimFlag());

		// 查勘人1默认为操作人远的名称

		prpLcheck.setChecker1(user.getUserName());
		// prpLcheck.setChecker2(prpLregist.getChecker2());
		// prpLcheck.setCheckUnitName(prpLregist.getCheckUnitName());
		prpLcheck.setHandleUnit(prpLregist.getHandleUnit());
		prpLcheck.setRemark(prpLregist.getRemark());
		prpLcheck.setFlag(prpLregist.getFlag());

		// 用户显示报案时的币别
		prpLcheck.setCurrency(prpLregist.getEstiCurrency());
		String strCurrencyName = this.codeService.translateCurrencyCode(prpLcheck.getCurrency(), true);
		httpServletRequest.setAttribute("strCurrencyName", strCurrencyName);
		prpLcheck.setDamageAddress(prpLregist.getDamageAddress());
		prpLcheck.setStatus(prpLregist.getStatus());
		prpLcheck.setEditType(prpLregist.getEditType());
		prpLcheck.setClauseType(prpLregist.getClauseType());
		prpLcheck.setClauseName(prpLregist.getClauseName());// code
		prpLcheck.setDamageStartDate(new DateTime(prpLregist.getDamageStartDate()));
		// prpLcheck.setDamageStartHour(prpLregist.getDamageStartHour());

		prpLcheck.setDamageEndDate(new DateTime(prpLregist.getDamageEndDate()));

		// 向查勘页面添加被保险人信息
		prpLcheck.setInsuredName(prpLregist.getInsuredName());

		// prpLcheck.setDamageEndHour(prpLregist.getDamageEndHour());
		String timeTemp = StringConvert.toStandardTime(prpLregist.getDamageStartHour());
		prpLcheck.setDamageStartMinute(timeTemp.substring(3, 5));
		prpLcheck.setDamageStartHour(timeTemp.substring(0, 2));
		timeTemp = StringConvert.toStandardTime(prpLregist.getDamageEndHour());
		prpLcheck.setDamageEndMinute(timeTemp.substring(3, 5));
		prpLcheck.setDamageEndHour(timeTemp.substring(0, 2));

		prpLcheck.setDamageAreaName(prpLregist.getDamageAreaName());
		if("Y".equals(strRiskType) || "Z".equals(strRiskType) || "G".equals(strRiskType)){
			prpLcheck.setAddressCode(prpLregist.getAddressCode());
		}
		// prpLcheck.setHandleUnitName(prpLregist.getHandleUnitName());//未知

		// 默认查勘日期
		prpLcheck.setCheckDate((new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND)));
		// 设置查勘操作的状态为 新案件登记 (未处理任务)
		prpLcheck.setStatus("1");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList= this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			String sameAddressNo = prpCaddressList.get(0).getSameAddressNo();
			prpLcheck.setSameAddressNo(sameAddressNo);
		}
		prpLcheck.setSumAmount(prpCmain.getSumAmount());
		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, prpLcheck);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcheck);
		// 查询相同保单号的出险次数
		this.daaRegistViewHelper.getSamePolicyRegistInfo(httpServletRequest, prpLcheck.getPolicyNo(), prpLcheck.getId().getRegistNo());
		// 设置主查勘信息内容到窗体表单
		// 如果是三者车辆，那么因为不保存，又要通过校验，所以必须在特定域中放如初始化的数值
		/*
		 * if((prpLcheck.getRiskCode().equals(BusinessRuleUtil.getOuterCode(
		 * httpServletRequest,"RISKCODE_DAA")))&&!insureCarFlag.equals("1")) {
		 * prpLcheck.setCheckSite("0 "); prpLcheck.setChecker1("0");
		 * prpLcheck.setChecker2("0 "); }else {//默认值
		 * prpLcheck.setInsureCarFlag("1"); prpLcheck.setReferSerialNo(1); }
		 */
		// 设置各个子表信息项到窗体表单
		CheckDto checkDto = new CheckDto();
		checkDto.setPrpLthirdPartyList(registDto.getPrpLthirdPartyList());
		// 是否有标的车、人伤、财产
		String hasThirdParty = "0";
		if (registDto.getPrpLthirdPartyList().size() > 1 || registDto.getPrpLpersonTraceList().size() > 0 || registDto.getPrpLthirdPropList().size() > 0) {
			hasThirdParty = "1";
		}
		httpServletRequest.setAttribute("hasThirdParty", hasThirdParty);
		checkDto.setPrpLdriverList(registDto.getPrpLdriverList());
		checkDto.setPrpLpersonTraceList(registDto.getPrpLpersonTraceList());
		checkDto.setPrpLthirdCarLossList(registDto.getPrpLthirdCarLossList());
		checkDto.setPrpLthirdPropList(registDto.getPrpLthirdPropList());
		checkDto.setPrpLregistExtList(registDto.getPrpLregistExtList());
		checkDto.setPrpLregistTextList(registDto.getPrpLregistTextList());
		// 保存特别约定信息
		checkDto.setPrpLcheck(prpLcheck);
		// 三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "check";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		CheckDto checkDtoTemp = checkService.findByPrimaryKey(registNo);
		checkDto.setPrpLclaimLossList(checkDtoTemp.getPrpLclaimLossList());
		checkDto.setPrpLcheckLossList(checkDtoTemp.getPrpLcheckLossList());
		// 设置各个子表中的信息和显示
		setSubInfo(httpServletRequest, checkDto);

		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);

		// 获取定损信息
		this.getDaaCertainLossViewHelper().registDtoToView(httpServletRequest, registNo, BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);
		httpServletRequest.setAttribute("registContext", getRegistContext(prpLcheck));

		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", this.codeService.translateRiskCode(prpLcheck.getRiskCode(), true));
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		httpServletRequest.setAttribute("shareHolderFlag", prpCmain.getShareHolderFlag());
		String insuredCode = prpLregist.getInsuredCode();
		String insuredName = prpLregist.getInsuredName();
		List<PrpCinsured> prpCinsuredList = null;
		if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
		}
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		httpServletRequest.setAttribute("prpCinsured", prpCinsured);
		// 增加货运险扩展信息
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			PrpLext prpLext = new PrpLext();
			String itemAll = "";
			// 查询联系方式 1.被保险人2.投保人
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				if (!CommonUtils.isEmpty(prpCinsuredList)) {
					for (PrpCinsured prpCinsuredDto : prpCinsuredList) {
						if (prpCinsuredDto.getInsuredFlag().equals("1") && !CommonUtils.isEmpty(prpCinsuredDto.getPhoneNumber())) {// 被保险人
							prpLext.setInsuredPhone(prpCinsuredDto.getPhoneNumber());
						} else if (prpCinsuredDto.getInsuredFlag().equals("2") && !CommonUtils.isEmpty(prpCinsuredDto.getPhoneNumber())) {// 投保人
							prpLext.setAppliPhone(prpCinsuredDto.getPhoneNumber());
						}
					}
				}
				prpLext.setSailStartDate(prpCmain.getStartDate());// 起运日期，取起保日期
				prpLext.setSumAmount(String.valueOf(prpCmain.getSumAmount())); // 保额，从保单带入

				prpLext.setCargoValue(prpCmain.getSumValue()); // 货价
				String rootComName = AppConfig.get("sysconst.ROOTCOMCNAME");
				prpLext.setPrpCompanyName(rootComName);
				List<PrpCitemKind> itemKind = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
				if (!CommonUtils.isEmpty(itemKind)) {
					Iterator<PrpCitemKind> item = itemKind.iterator();
					while (item.hasNext()) {
						PrpCitemKind itemKindDto = item.next();
						itemAll = itemAll + itemKindDto.getItemDetailName();
						double account = itemKindDto.getQuantity();
						String model = itemKindDto.getModel();
						if (account > 0) {
							itemAll = itemAll + "; 共" + account + model;
						}
						prpLext.setLimitAmount(String.valueOf(itemKindDto.getDeductible())); // 免赔额
						break;
					}
					if (itemAll.length() > 29) {
						itemAll = itemAll.substring(0, 28);
					}
				}
				prpLext.setValue1(itemAll); // 保险标的
				// 由於业务对运输方式及工具的存储字段不唯一，所以在此处要做一个处理
				prpLext.setValue3(prpCmainCargo.getInvoiceNo()); // 发票号码
				prpLext.setRemark(CommonUtils.nullToEmpty(prpCmainCargo.getLadingNo()) + CommonUtils.nullToEmpty(prpCmainCargo.getCarryBillNo()));
				if (prpCmainCargo != null) {
					prpCmainCargo.setConveyance(this.codeService.translateCodeCode("TransMode", prpCmainCargo.getConveyance(), true));
				}
				prpLext.setValue2(String.valueOf(prpLregist.getEstimateLoss())); // 估损金额
				prpLext.setCurrency(prpLregist.getEstiCurrency());
				prpLext.setCurrencyCname(this.codeService.translateCurrencyCode(prpLregist.getEstiCurrency(), true));
				httpServletRequest.setAttribute("prpLcarGo", prpCmainCargo);
			}
			httpServletRequest.setAttribute("prpLext", prpLext);
		}
		PrpLregist prplregist = prpLregist;
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			PrpLext prpLext = (PrpLext) registDto.getPrpLext();
			if (prpLext != null) {
				prplregist.setPersonDeathB(prpLext.getPersonDeathB());
				prplregist.setPersonInjureB(prpLext.getPersonInjureB());
				prplregist.setPersonDeathD1(prpLext.getPersonDeathD1());
				prplregist.setPersonInjureD1(prpLext.getPersonInjureD1());
			}
		}
		sendUndwrtViewHelper.LoadingSendUndwrt(httpServletRequest, registNo, "check");
		httpServletRequest.setAttribute("prpLregist", prplregist);
	}

	/**
	 * 根据肇责比获取事故责任值 0：全责 1：主责 2：同责 3：次责 4：无责 9：其他
	 * @author 中科软
	 * @date May 17, 2013 5:54:44 PM
	 * @param dutyPercent
	 * @return
	 */
	private String getIndemnityDuty(double dutyPercent) {
		if (dutyPercent == 100) {
			return "0";
		} else if (dutyPercent > 50) {
			return "1";
		} else if (dutyPercent == 50) {
			return "2";
		} else if (dutyPercent > 25) {
			return "3";
		} else if (dutyPercent == 0) {
			return "4";
		} else {
			return "9";
		}
	}

	/**
	 * 根据赔案号和报案号查询查勘信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 赔案号
	 * @param claimNo 报案号
	 * @throws Exception
	 */
	public void setPrpLcheckToView(HttpServletRequest httpServletRequest, String registNo, String policyNo) throws Exception {
		// 根据输入的保单号，查勘号生成SQL where 子句
		QueryRule queryRule = QueryRule.getInstance();
		policyNo = StringUtils.rightTrim(policyNo);
		registNo = StringUtils.rightTrim(registNo);
		queryRule.addLike("id.registNo", "%'" + registNo + "'%");
		queryRule.addLike("policyNo", "%'" + policyNo + "'%");
		// 查询预赔信息
		// 得到多行查勘主表信息
		List<PrpLcheck> checkList = new ArrayList<PrpLcheck>();
		checkList = prpLcheckService.findPrpLcheck(queryRule);
		PrpLcheck prpLcheck = new PrpLcheck();
		prpLcheck.setCheckList(checkList);

		prpLcheck.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);
		// 展现事故管界
		PrpLregist prpLregistDto = prpLregistService.findPrpLregist(registNo);
		String section = prpLregistDto.getSection();
		String sectionName = prpLregistDto.getSectionName();
		if (section == null) {
			section = "";
			sectionName = "";
		}
		httpServletRequest.setAttribute("section", section);
		httpServletRequest.setAttribute("sectionName", sectionName);
	}

	/***
	 * 查勘查询
	 * @param request
	 * @param workFlowQueryDto
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception
	 */
	public Page setPrpLcheckToView(HttpServletRequest request, WorkFlowQueryDto workFlowQueryDto, int pageNo, int pageSize) throws Exception {
		// 根据输入的保单号，查勘号生成SQL where 子句
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		//区分意健险，非意健险
		String type = request.getParameter("type");
		//事故日期
		String strDamageStartDate = request.getParameter("damageStartDate");
		String strDamageEndDate = request.getParameter("damageEndDate");
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" 1=1 ");
		conditions.append(StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign()));
		if(!CommonUtils.isEmpty(claimNo)){
			if("acci".equals(type)){
				//意见险的查询
				conditions.append(" and a.registNo in ( select registNo from prpLclaim prpLclaim where 1=1 ");
				conditions.append(StringConvert.convertString("prpLclaim.claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
				conditions.append(")");
			}else{
				conditions.append(StringConvert.convertString("a.claimNo", claimNo, workFlowQueryDto.getClaimNoSign()));
			}
		}
		// 强三查询
		conditions.append(StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign()));
		conditions.append(StringConvert.convertString("c.LicenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		conditions.append(StringConvert.convertString("c.InsuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		conditions.append(StringConvert.convertDate("c.damageStartDate",strDamageStartDate,">="));
		conditions.append(StringConvert.convertDate("c.damageStartDate",strDamageEndDate,"<="));
		if (status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ")");
		}
		if (DataUtils.emptyToNull(operateDate) != null) {
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		
		conditions.append(" and ");
		if(!"acci".equals(type)){
			conditions.append(" not ");
		}
		conditions.append(" exists (select 0 from uticodetransfer where b.riskcode = uticodetransfer.outercode and uticodetransfer.risktype= 'E' and uticodetransfer.validstatus = '1') ");
		//被保險人ID
		String strInsuredIdentifyNumber = request.getParameter("InsuredIdentifyNumber");
		String strInsuredIdentifyNumberSign = request.getParameter("InsuredIdentifyNumberSign");
		if (DataUtils.emptyToNull(strInsuredIdentifyNumber) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcinsured where a.policyno = prpcinsured.policyno ");
			conditions.append(" and prpcinsured.insuredflag = '1' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
			conditions.append(" ) ");
		}
		Page page = null;
		// 原因：意健险和非意健险查询不同的表
		if ("acci".equals(type)) {
			page = checkService.findByQueryConditionsAcci(conditions.toString(), pageNo, pageSize);
		} else {
			page = checkService.findByQueryConditions(conditions.toString(), pageNo, pageSize);
		}
		return page;
	}

	/**
	 * 根据PrpCheckDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param checkDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, CheckDto checkDto) throws Exception {
		// 给报案信息补充说明多行列表准备数据
		String riskCode = checkDto.getPrpLcheck().getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		List<String> delete = new ArrayList<String>();
		SwfLog swfLog = null;
		List<SwfLog> certaList = new ArrayList<SwfLog>();
		String condition = " NODETYPE='certa' AND BUSINESSNO= '" + checkDto.getPrpLcheck().getId().getRegistNo() + "'  ORDER BY LOSSITEMCODE";
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(checkDto.getPrpLcheck().getId().getRegistNo());
		prpLregistExt.setRiskCode(checkDto.getPrpLcheck().getRiskCode());
		List<PrpLregistExt> arrayListRegistExt = checkDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);

		// [查勘报告]给报案文件多行列表准备数据
		PrpLregistText prpLregistText = new PrpLregistText();
		String tempContext = "";
		if (checkDto.getPrpLregistTextList() != null) {
			Iterator<?> iterator = checkDto.getPrpLregistTextList().iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistTextTemp = (PrpLregistText) iterator.next();
				tempContext = tempContext + prpLregistTextTemp.getContext();
			}
		}
		prpLregistText.setContext(tempContext);
		prpLregistText.getId().setTextType("3");
		httpServletRequest.setAttribute("prpLregistText", prpLregistText);

		// [涉案车辆]给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		arrayList = checkDto.getPrpLthirdPartyList();
		certaList = this.getSwfLogService().findByConditions(condition);
		int number = 1;
		String lossItemCode = "";
		boolean add = false;
		while (number <= arrayList.size()) {
			Iterator<SwfLog> iteratorOfcerta = certaList.iterator();
			while (iteratorOfcerta.hasNext()) {
				swfLog = iteratorOfcerta.next();
				lossItemCode = swfLog.getLossItemCode();
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
		prpLthirdParty.setThirdPartyList(arrayList);
		prpLthirdParty.setNodeType("check");
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);

		// [驾驶员]给驾驶员多行多行列表准备数据
		List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = new PrpLdriver();
		arrayListDriver = checkDto.getPrpLdriverList();
		prpLdriver.setDriverList(arrayListDriver);
		httpServletRequest.setAttribute("prpLdriver", prpLdriver);

		// [查勘信息]查勘报告多行列表准备数据
		/*
		 * Collection arrayLTextList = new ArrayList<Object>();
		 * PrpLregistTextDto prpLregistTextDto = new PrpLregistTextDto();
		 * prpLregistTextDto.setTextType("3") ; arrayLTextList =
		 * checkDto.getPrpLregistTextDtoList() ;
		 * prpLregistTextDto.setRegistTextList(arrayLTextList) ;
		 * httpServletRequest.setAttribute("prpLregistText", prpLregistTextDto);
		 */
		// 查勘扩展信息多行列表准备数据
		Collection<PrpLcheckExt> arrayList1 = new ArrayList<PrpLcheckExt>();
		PrpLcheckExt prpLcheckExt = new PrpLcheckExt();
		arrayList1 = checkDto.getPrpLcheckExtList();
		prpLcheckExt.setPrpLcheckExtList(arrayList1);
		httpServletRequest.setAttribute("prpLcheckExt", prpLcheckExt);

		// 事故估损金额多行列表准备数据
		List<PrpLcheckLoss> arrayList2 = new ArrayList<PrpLcheckLoss>();
		PrpLcheckLoss prpLcheckLoss = new PrpLcheckLoss();
		arrayList2 = checkDto.getPrpLcheckLossList();
		if (arrayList2 != null) {
			for (int indexCheck = 0; indexCheck < arrayList2.size(); indexCheck++) {
				PrpLcheckLoss prpLcheckLoss1 = new PrpLcheckLoss();
				prpLcheckLoss1 = (PrpLcheckLoss) arrayList2.get(indexCheck);
				// 对险别进行转换
				String kindCode = prpLcheckLoss1.getKindCode();
				String kindName = this.codeService.translateKindCode(BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"), kindCode, true);
				prpLcheckLoss1.setKindName(kindName);
			}
		}
		prpLcheckLoss.setPrpLcheckLossList(arrayList2);
		httpServletRequest.setAttribute("prpLcheckLoss", prpLcheckLoss);

		// 给人员伤亡跟踪多行多行列表准备数据
		PrpLpersonTrace prpLpersonTrace = new PrpLpersonTrace();
		List<PrpLpersonTrace> arrayListPersonTrace = checkDto.getPrpLpersonTraceList();
		prpLpersonTrace.setPersonTraceList(arrayListPersonTrace);
		prpLpersonTrace.setNodeType("check");

		if (checkDto.getPrpLpersonTraceList() != null) {
			PrpDcode[] prpDcodes = null;
			PrpLpersonTrace prplpersonTrace = null;
			for(int i=0;i<arrayListPersonTrace.size();i++){
				prplpersonTrace = arrayListPersonTrace.get(i);
				prplpersonTrace.setPrpLpersonTraceReferKind(prplpersonTrace.getReferKind());
				// 获取一级行业和二级行业信息 start
				prpDcodes = codeService.translateJobCode(prplpersonTrace.getJobCode(),riskCode);
				prplpersonTrace.setJobCode1(prpDcodes[0].getId().getCodeCode());
				prplpersonTrace.setJobName1(prpDcodes[0].getCodeCName());
				prplpersonTrace.setJobCode2(prpDcodes[1].getId().getCodeCode());
				prplpersonTrace.setJobName2(prpDcodes[1].getCodeCName());
			}
		}
		httpServletRequest.setAttribute("prpLpersonTrace", prpLpersonTrace);

		String registNo = checkDto.getPrpLcheck().getId().getRegistNo();
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		PrpLregist prpLregist = registDto.getPrpLregist();
		String policyNo = prpLregist.getPolicyNo();
		// 判断是否关联
		boolean compelFlag = this.prpLregistrpolicyService.isCompelFlag(registNo);
		if (compelFlag) {
			httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		}
		//PolicyDto policyDto = this.endorseViewHelper.findForEndorBefore(policyNo, new DateTime(prpLregist.getDamageStartDate()).toString(), prpLregist.getDamageStartHour());
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		List<PrpCitemKind> itemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, riskCode, null);
//		List<PrpCengage> cengageList = this.endorseViewHelper.findPrpCengage(policyNo, damageDate, damageHour);
//		checkDto.setPrpCengageList(cengageList);
////		 特别约定信息多行列表准备数据
//		PrpCengage prpCengage = new PrpCengage();
//		cengageList = checkDto.getPrpCengageList();
//		List<PrpCengage> cengageListTemp = new ArrayList<PrpCengage>();
//		if (cengageList != null) {
//			Iterator<PrpCengage> iteratorCengage = cengageList.iterator();
//			while (iteratorCengage.hasNext()) {
//				PrpCengage prpCengageDtoTemp = (PrpCengage) iteratorCengage.next();
//				if (prpCengageDtoTemp.getClauseCode() != null && prpCengageDtoTemp.getClauseCode().length() > 0 && prpCengageDtoTemp.getClauseCode().charAt(0) == 'T') {
//					cengageListTemp.add(prpCengageDtoTemp);
//				}
//			}
//			cengageList = new ArrayList<PrpCengage>();
//			cengageList.addAll(cengageListTemp);
//			cengageListTemp = new ArrayList<PrpCengage>();
//			iteratorCengage = cengageList.iterator();
//			PrpCengage prpCengageDtoTemp1 = new PrpCengage();
//			while (iteratorCengage.hasNext()) {
//				PrpCengage prpCengageDtoTemp = (PrpCengage) iteratorCengage.next();
//				if (prpCengageDtoTemp.getTitleFlag().equals("0")) {
//					cengageListTemp.add(prpCengageDtoTemp1);
//					prpCengageDtoTemp1 = new PrpCengage();
//					PropertyUtils.copyProperties(prpCengageDtoTemp1, prpCengageDtoTemp);
//				} else {
//					prpCengageDtoTemp1.setContext(prpCengageDtoTemp1.getContext() + prpCengageDtoTemp.getClauses() + "<br>");
//				}
//			}
//			cengageListTemp.add(prpCengageDtoTemp1);
//			if (cengageListTemp.size() > 0) {
//				cengageListTemp.remove(0);
//			}
//		}
//		prpCengage.setPrpCengageList(cengageListTemp);
//		httpServletRequest.setAttribute("prpCengage", prpCengage);

		if (compelFlag) {// 如果关联
			String mainPolicyNo = "";
			Prplregistrpolicy prpLregistRPolicy = (Prplregistrpolicy) httpServletRequest.getAttribute("prpLregistRPolicyNo");
			if (prpLregistRPolicy != null) {
				mainPolicyNo = prpLregistRPolicy.getId().getPolicyNo();
			}
			if (!CommonUtils.isEmpty(mainPolicyNo)) { // 暂时这样，
				List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, riskCode, null);
				if (!CommonUtils.isEmpty(itemKindList_qs)) {
					PrpCitemKind prpCitemKindDto_qs = (PrpCitemKind) itemKindList_qs.get(0);
					itemKindList.add(prpCitemKindDto_qs);
				}
			}
		}
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();// 赔人伤的险别
		List<PrpCitemKind> referKindListForProp = new ArrayList<PrpCitemKind>();// 赔财产的险别
		List<PrpCitemKind> referKindListForCar = new ArrayList<PrpCitemKind>();// 赔主车\三者车的险别
		PrpCitemKind prpCitemKindTemp = null;
		for (PrpCitemKind temp : itemKindList) {
			if("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode()))){
				if (ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())) {
					prpCitemKindTemp = new PrpCitemKind();
					BeanUtils.copyProperties(prpCitemKindTemp, temp);
					prpCitemKindTemp.setKindName(prpCitemKindTemp.getKindCode() + "-" + prpCitemKindTemp.getKindName());
					referKindList.add(prpCitemKindTemp);
				}
				if (ConstantsCollection.KindCodeForProp.contains(temp.getKindCode())) {
					referKindListForProp.add(temp);
				}
				if (ConstantsCollection.MainCarLoss.contains(temp.getKindCode()) || ConstantsCollection.ThirdCarLoss.contains(temp.getKindCode())) {
					referKindListForCar.add(temp);
				}
			}else{
				prpCitemKindTemp = new PrpCitemKind();
				BeanUtils.copyProperties(prpCitemKindTemp, temp);
				prpCitemKindTemp.setKindName(prpCitemKindTemp.getKindCode() + "-" + prpCitemKindTemp.getKindName());
				referKindList.add(prpCitemKindTemp);
				referKindListForProp.add(temp);
			}
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		httpServletRequest.setAttribute("prpLcheckPropItemKindList", referKindListForProp);
		httpServletRequest.setAttribute("prpLcheckItemKindList", referKindListForCar);
		// httpServletRequest.setAttribute("prpLcheckMainCarItemKindList",
		// referKindListForMainCar);
		// httpServletRequest.setAttribute("prpLcheckThirdCarItemKindList",
		// referKindListForThirdCar);
		// 损失部位模块合到涉到车辆信息中後，其它相应模块做调整
		// 给损失部位多行多行列表准备数据
		Collection<PrpLthirdCarLoss> arrayListThirdCarLoss = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = new PrpLthirdCarLoss();
		arrayListThirdCarLoss = checkDto.getPrpLthirdCarLossList();
		prpLthirdCarLoss.setThirdCarLossList(arrayListThirdCarLoss);
		httpServletRequest.setAttribute("prpLthirdCarLoss", prpLthirdCarLoss);

		// 损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		// 在查勘页面中加上其它损失模块
		Collection<PrpLthirdProp> arrayListThirdProp = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = new PrpLthirdProp();
		arrayListThirdProp = checkDto.getPrpLthirdPropList();
		prpLthirdProp.setThirdPropList(arrayListThirdProp);
		httpServletRequest.setAttribute("prpLthirdProp", prpLthirdProp);

		List<PrpLprop> prpLpropList = new ArrayList<PrpLprop>();
		List<PrpLcheckLoss> tempPrpLcheckLossList = (List<PrpLcheckLoss>) prpLcheckLoss.getPrpLcheckLossList();
		if (prpLthirdProp.getThirdPropList() != null && prpLthirdProp.getThirdPropList().size() > 0) {
			for (int i = 0; i < tempPrpLcheckLossList.size(); i++) {
				if (tempPrpLcheckLossList.get(i) != null && "3".equals(tempPrpLcheckLossList.get(i).getLossFeeType())) {
					PrpLprop prpLprop = new PrpLprop();
					prpLprop.setKindCode(tempPrpLcheckLossList.get(i).getKindCode());
					prpLprop.setSumLoss(tempPrpLcheckLossList.get(i).getLossFee());
					prpLpropList.add(prpLprop);
				}
			}
			httpServletRequest.setAttribute("prpLpropList", prpLpropList);
		}

		// 垫付赔案所走代码，保证按钮显示状态正确
		String comCode = prpLregist.getComCode().substring(0, 2);
		PrpDriskConfig prpDriskConfigDto = this.prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
		if (prpDriskConfigDto != null && "1".equals(prpDriskConfigDto.getConfigValue())) {
			//PrpLregist prpLregist = prpLregistService.findPrpLregist(checkDto.getPrpLcheck().getId().getRegistNo());
			if ("1".equals(prpLregist.getAdvanceType())) {
				httpServletRequest.setAttribute("display1", "display:");
			} else if ("2".equals(prpLregist.getAdvanceType())) {
				httpServletRequest.setAttribute("display1", "display:none");
			} else {
				httpServletRequest.setAttribute("display1", "display:none");
			}
			httpServletRequest.setAttribute("isSpecial", "1");
			httpServletRequest.setAttribute("advance", "1");
			httpServletRequest.setAttribute("advanceType", prpLregist.getAdvanceType());
		}
		prpDriskConfigDto = this.prpDriskConfigService.findByPrimaryKey(prpLregist.getComCode(), prpLregist.getRiskCode(), "dealFast_case");
		if (prpDriskConfigDto != null && "1".equals(prpDriskConfigDto.getConfigValue())) {
			httpServletRequest.setAttribute("dealFast", "1");
		}
		PrpLclaimLoss prpLclaimLoss = new PrpLclaimLoss();
		List<PrpLclaimLoss> claimLossList = checkDto.getPrpLclaimLossList();
		if ("Q".equals(strRiskType) && claimLossList != null && claimLossList.size()>0) {
			double estimateFee = 0;//费用
			double estimateLoss = 0;//赔款
			for (int i = 0; i < claimLossList.size(); i++) {
				PrpLclaimLoss prpLclaimLoss1 = (PrpLclaimLoss) claimLossList.get(i);
				if (prpLclaimLoss1.getInputDate() == null) {
					prpLclaimLoss1.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
				}
				prpLclaimLoss1.setKindName(this.codeService.translateKindCode(prpLclaimLoss1.getRiskCode(), prpLclaimLoss1.getKindCode(), true));
				if (prpLclaimLoss1.getKindCodeSub() != null && !"".equals(prpLclaimLoss1.getKindCodeSub())) {
					prpLclaimLoss1.setKindNameSub(this.codeService.translateKindCode(prpLclaimLoss1.getRiskCode(), prpLclaimLoss1.getKindCodeSub(), true));
				}
				for(PrpCitemKind prpCitemKind : itemKindList){
					if(prpLclaimLoss1.getKindCode()!=null&&prpLclaimLoss1.getItemCode()!=null&&prpLclaimLoss1.getKindCode().equals(prpCitemKind.getKindCode())&&prpLclaimLoss1.getItemCode().equals(prpCitemKind.getItemCode())){
						prpLclaimLoss1.setItemKindName(prpCitemKind.getItemDetailName());
						break;
					}
				}
				if("Z".equals(prpLclaimLoss1.getLossFeeType())){
					estimateFee += prpLclaimLoss1.getSumClaim();
				}else{
					estimateLoss += prpLclaimLoss1.getSumClaim();
				}
				prpLclaimLoss.setLossFeeType(prpLclaimLoss1.getLossFeeType());
				prpLclaimLoss1.setCurrencyName(this.codeService.translateCurrencyCode(prpLclaimLoss1.getCurrency(), true));
			}
			checkDto.getPrpLcheck().setEstimateFee(estimateFee);
			checkDto.getPrpLcheck().setEstimateLoss(estimateLoss);
		}
		prpLclaimLoss.setClaimLossList(claimLossList);
		httpServletRequest.setAttribute("prpLclaimLoss", prpLclaimLoss);

	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcheck 查勘的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLcheck prpLcheck) throws Exception {

		// 查勘性质列表
		List<PrpDcode> checkNatures = this.codeService.getCodeType("CheckNature", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("checkNatures", checkNatures);
		httpServletRequest.setAttribute("checkNatureList", ConstantsCollection.checkNatureList);//火险的调查方式
		// 赔案类别
		List<PrpDcode> caseCodes = this.codeService.getCodeType("CaseCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("caseCodes", caseCodes);
		// 保险事故类型
		List<PrpDcode> accidentTypes = this.codeService.getCodeType("AccidentTypeCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("accidentTypes", accidentTypes);
		// 出险地点分类
		List<PrpDcode> damageAddresss = this.codeService.getCodeType("DamageAddress", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("damageAddresss", damageAddresss);
		// 事故赔偿责任
		List<PrpDcode> indemnityDutys = this.codeService.getCodeType("IndemnityDuty", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("indemnityDutys", indemnityDutys);

		// 得到实赔类型列表
		List<PrpDcode> reportTypes = this.codeService.getCodeType("ReportType", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// 得到案件种类列表列表
		List<PrpDcode> claimTypes = this.codeService.getCodeType("CaseCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// 得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = this.codeService.getCodeType("DamageAddress", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// 得到车辆种类列表
		List<PrpDcode> carKindCodes = this.codeService.getCodeTypeCarKind("CarKind", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 得到车牌底色列表
		List<PrpDcode> licenseColorCode = this.codeService.getCodeType("LicenseColor", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// 得到赔偿责任列表
		List<PrpDcode> indemnityDuty = this.codeService.getCodeType("IndemnityDuty", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// 得到赔案类别列表
		List<PrpDcode> escapeFlags = this.codeService.getCodeType("CaseCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// 得到得到性别
		List<PrpDcode> driverSex = this.codeService.getCodeType("SexCode", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// 得到职业分类
		List<PrpDcode> driverOccupation = this.codeService.getCodeType("Occupation", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// 得到文化程度
		List<PrpDcode> education = this.codeService.getCodeType("Education", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("educations", education);
		// 查勘類型
		httpServletRequest.setAttribute("checkTypeList", ConstantsCollection.checkTypeList);
		// 水险查勘類型
		httpServletRequest.setAttribute("shipCheckTypeList", ConstantsCollection.shipCheckTypeList);
		// 互碰自賠標志
		httpServletRequest.setAttribute("payselfFlagList", ConstantsCollection.payselfFlagList);
		// 本車是否受損
		httpServletRequest.setAttribute("lossFlagList", ConstantsCollection.lossFlagList);
		// 傷亡類型
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		// 是否自行就醫
		httpServletRequest.setAttribute("motionFlagList", ConstantsCollection.motionFlagList);
		// 證件類型
		httpServletRequest.setAttribute("drivingCarTypeList", ConstantsCollection.drivingCarTypeList);
		// 駕駛人區別
		httpServletRequest.setAttribute("driverDistrictList", ConstantsCollection.driverDistrictList);
		// 估损金额调整
		httpServletRequest.setAttribute("lossLossFeeTypeList", ConstantsCollection.lossLossFeeTypeList);
		// 範圍
		httpServletRequest.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryList);
		// 本車駕駛人與被保險人關係
		httpServletRequest.setAttribute("thirdPartyRelationshipList", ConstantsCollection.thirdPartyRelationshipList);
		// 被保險人身分 駕駛人身份
		httpServletRequest.setAttribute("identityList", ConstantsCollection.identityList);
		httpServletRequest.setAttribute("partyCarryingUnitList", ConstantsCollection.partyCarryingUnitList);
		// 承載單位
	}

	/**
	 * 根据PrpCheckDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcheck 查勘的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLcheck prpLcheck) throws Exception {

		// (1)条款名称的转换
		String clauseType = prpLcheck.getClauseType();
		String clauseName = this.codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLcheck.setClauseName(clauseName);
		prpLcheck.setDamageAreaName(this.codeService.translateCodeCode("DamageAreaCode", prpLcheck.getDamageAreaCode(), true));

		// (2)处理部门的中文转换
		String strHandleUnit = prpLcheck.getHandleUnit();
		String strHandleUnitName = this.codeService.translateComCode(strHandleUnit, true);
		prpLcheck.setHandleUnitName(strHandleUnitName);
		
		// 转换邮编地址
		String strAddressName = this.codeService.translateCodeCode("PostCode", prpLcheck.getAddressCode(), true);
		prpLcheck.setAddressName(strAddressName);
	}

	/**
	 * 获取没有处理完毕的调度任务
	 * @param httpServletRequest
	 * @throws Exception
	 */
	public void getCheckScheduleDtoToView(HttpServletRequest httpServletRequest) throws Exception {
		// 目的是取得没有被处理完毕的调度任务，然后list出来。
		List<PrpLcheckItem> scheduleDtoList = new ArrayList<PrpLcheckItem>();
		// 查找符合条件的调度任务
		String conditions = "";
		conditions = " selectSend='1' and insurecarflag='1' order by registNo,scheduleid";
		scheduleDtoList = checkService.findNewScheduleTaskList(conditions);
		PrpLcheckItem prpLcheckItemDto = new PrpLcheckItem();

		scheduleDtoList = changeOperatorCodeToName(scheduleDtoList);
		prpLcheckItemDto.setCheckItemList(scheduleDtoList);
		// 将查询出来的 任务列表放到界面上显示
		httpServletRequest.setAttribute("prpLcheckItem", prpLcheckItemDto);
	}

	/**
	 * 分案列表
	 * @param scheduleList
	 * @return
	 * @throws Exception
	 */
	private List<PrpLcheckItem> changeOperatorCodeToName(List<PrpLcheckItem> scheduleList) throws Exception {
		List<PrpLcheckItem> scheduleListChange = new ArrayList<PrpLcheckItem>();
		Iterator<PrpLcheckItem> it = scheduleList.iterator();
		String operatorCode = "";
		String operatorName = "";
		while (it.hasNext()) {
			PrpLcheckItem prpLcheckItem = new PrpLcheckItem();
			prpLcheckItem = (PrpLcheckItem) it.next();
			operatorCode = prpLcheckItem.getOperatorCode();
			operatorName = this.codeService.translateUserCode(operatorCode, true);
			prpLcheckItem.setOperatorName(operatorName);
			scheduleListChange.add(prpLcheckItem);
		}
		return scheduleListChange;
	}

	/**
	 * 分案查勘
	 * @param httpServletRequest
	 * @param registNo
	 * @param scheduleID
	 * @throws Exception
	 */
	public void getCheckScheduleDealDtoToView(HttpServletRequest httpServletRequest, String registNo, String scheduleID) throws Exception {

		int intscheduleID = Integer.parseInt(scheduleID);
		ScheduleDto scheduleDto = scheduleService.findByRegistNo(intscheduleID, registNo);

		// 根据查询出来的数据内容，给PrpLscheduleDto赋值
		PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();
		prpLscheduleMainWF = scheduleDto.getPrpLscheduleMainWF();

		RegistDto registDto = registService.findByPrimaryKey(registNo);

		// 设置扩展属性
		prpLscheduleMainWF.setLinkerName(registDto.getPrpLregist().getLinkerName());
		prpLscheduleMainWF.setPhoneNumber(registDto.getPrpLregist().getPhoneNumber());
		// prpLscheduleMainWFDto.setOperatorName(user.getUserName() );
		if (registDto.getPrpLregistTextList() != null) {
			PrpLregistText prpLregistTextDto = new PrpLregistText();
			prpLregistTextDto = registDto.getPrpLregistTextList().iterator().next();
			prpLscheduleMainWF.setRegistText(prpLregistTextDto.getContext());
		}

		// 设置查勘操作的状态为 案件修改 (正处理任务)
		if (scheduleDto.getPrpLclaimStatus() != null) {
			prpLscheduleMainWF.setStatus(scheduleDto.getPrpLscheduleMainWF().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLscheduleMainWF.setStatus("4");
		}

		scheduleDto.setPrpLscheduleMainWF(prpLscheduleMainWF);

		// 设置相关代码的中文转换
		// 设置窗体表单中各个多选框中列表信息的内容
		// 设置主查勘信息内容到窗体表单
		httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWF);
		// 设置各个子表信息项到窗体表单
		setCheckItemInfo(httpServletRequest, scheduleDto);
	}

	/**
	 * 根据Dto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param scheduleDto 查勘的数据类
	 * @throws Exception
	 */
	private void setCheckItemInfo(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception {
		List<PrpLcheckItem> checkItemList = new ArrayList<PrpLcheckItem>();
		checkItemList = scheduleDto.getPrpLcheckItemList();
		scheduleDto.setPrpLcheckItemList(checkItemList);
		PrpLcheckItem prpLcheckItemDto = new PrpLcheckItem();
		prpLcheckItemDto.setCheckItemList(checkItemList);
		httpServletRequest.setAttribute("prpLcheckItem", prpLcheckItemDto);
	}

	/**
	 * 保存查勘时查勘页面数据整理.
	 * @param httpServletRequest
	 * @return scheduleDto 查勘数据传输数据结构
	 * @throws Exception
	 */
	public ScheduleDto checkViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 取得当前用户信息，写操作员信息到查勘中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
		String scheduleID = httpServletRequest.getParameter("prpLscheduleMainWFScheduleID"); // 调度号
		String prpLscheduleMainWFCheckFlag = httpServletRequest.getParameter("prpLscheduleMainWFCheckFlag"); // 查勘状态
		String prpLscheduleMainWFCheckInfo = httpServletRequest.getParameter("prpLscheduleMainWFCheckInfo"); // 查勘情况内容
		String prpLscheduleMainWFCheckOperatorCode = user.getUserCode();
		int intscheduleID = Integer.parseInt(scheduleID);

		// 首先从数据库中取得调度的所有信息
		ScheduleDto scheduleDto = scheduleService.findByRegistNo(intscheduleID, registNo);

		// 根据查询出来的数据内容，给PrpLscheduleDto赋值
		PrpLscheduleMainWF prpLscheduleMainWFDto = new PrpLscheduleMainWF();
		prpLscheduleMainWFDto = scheduleDto.getPrpLscheduleMainWF();
		prpLscheduleMainWFDto.setCheckFlag(prpLscheduleMainWFCheckFlag);
		prpLscheduleMainWFDto.setCheckInfo(prpLscheduleMainWFCheckInfo);
		prpLscheduleMainWFDto.setCheckInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLscheduleMainWFDto.setCheckOperatorCode(prpLscheduleMainWFCheckOperatorCode);
		scheduleDto.setPrpLscheduleMainWF(prpLscheduleMainWFDto);
		// -------------完成主表控制-------------------------------
		List<PrpLcheckItem> checkItemList = new ArrayList<PrpLcheckItem>();
		List<PrpLcheckItem> checkItemListTemp = new ArrayList<PrpLcheckItem>();
		checkItemListTemp = scheduleDto.getPrpLcheckItemList();
		Iterator<PrpLcheckItem> it = checkItemListTemp.iterator();
		while (it.hasNext()) {
			PrpLcheckItem prpLcheckItemDto = new PrpLcheckItem();
			prpLcheckItemDto = (PrpLcheckItem) it.next();
			prpLcheckItemDto.setCheckflag(prpLscheduleMainWFCheckFlag);
			prpLcheckItemDto.setCheckinfo(prpLscheduleMainWFCheckInfo);
			prpLcheckItemDto.setCheckoperatorcode(prpLscheduleMainWFCheckOperatorCode);
			// 加入查勘标的集合
			checkItemList.add(prpLcheckItemDto);
		}
		// 调度集合中修改查勘标的
		scheduleDto.setPrpLcheckItemList(checkItemList);
		return scheduleDto;
	}

	/**
	 * 根据赔案号,报案号,案件状态，车牌号码，操作时间查询查勘信息
	 * @param httpServletRequest 返回给页面的request
	 * @param claimNo 赔案号
	 * @param registNo 报案号
	 * @param licenseNo 车牌号
	 * @throws Exception 增加车牌号，案件状态，操作时间查询条件
	 */
	public void setPrpLcheckToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// 根据输入的保单号，查勘号生成SQL where 子句
		String claimNo = StringUtils.rightTrim(workFlowQueryDto.getClaimNo());
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("a.registNo", registNo, workFlowQueryDto.getRegistNoSign());
		conditions = conditions + StringConvert.convertString("a.claimNo", claimNo, workFlowQueryDto.getClaimNoSign());
		// 强三查询
		conditions = conditions + StringConvert.convertString("d.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign());
		conditions = conditions + StringConvert.convertString("c.LicenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign());
		// 添加被保险人查询条件
		conditions = conditions + StringConvert.convertString("c.InsuredName", insuredName, workFlowQueryDto.getInsuredNameSign());
		if (status.trim().length() > 0) {
			conditions = conditions + " AND b.status in (" + status + ")";
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions = conditions + StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign());
		}
		// 拼权限
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 原因：需要根据不同的条件查询意键险和非意健险
		if (httpServletRequest.getParameter("type") != null && httpServletRequest.getParameter("type").equals("acci")) {
			conditions = conditions + " and (b.riskcode like '07%' or b.riskcode like '26%')";
		} else {
			conditions = conditions + " and b.riskcode not like '07%' and b.riskcode not like '26%'";
		}
		conditions = conditions + uiPowerInterface.addPower(userDto, "c", "", "ComCode");

		// 得到多行查勘主表信息
		List<PrpLcheck> checkList = new ArrayList<PrpLcheck>();
		// 原因：意健险和非意健险查询不同的表
		if (httpServletRequest.getParameter("type") != null && httpServletRequest.getParameter("type").equals("acci")) {
			checkList = checkService.findByQueryConditionsAcci(conditions);
		} else {
			checkList = checkService.findByQueryConditions(conditions);
		}
		PrpLcheck prpLcheck = new PrpLcheck();
		prpLcheck.setCheckList(checkList);
		prpLcheck.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);
	}

	/**
	 * 查询工作流可以用来选择的节点内容
	 * @param modelNo String
	 * @param nodeNo String
	 * @throws Exception
	 */
	private void getSubmitNodes(HttpServletRequest httpServletRequest) throws Exception {
		String modelNo = httpServletRequest.getParameter("modelNo"); // 模板号
		String nodeNo = httpServletRequest.getParameter("nodeNo"); // 节点号
		int nextNodeNo = 0;
		List<SwfPath> pathList = new ArrayList<SwfPath>();
		SwfPath swfPathDto = new SwfPath();
		if (modelNo != null && nodeNo != null) {
			pathList = this.getWorkFlowViewHelper().getNextSumbitNodes(modelNo, nodeNo);
			Iterator<SwfPath> it = pathList.iterator();
			if (it.hasNext()) {
				SwfPath swfPathDtoTemp = it.next();
				nextNodeNo = swfPathDtoTemp.getEndNodeNo();
				swfPathDto.setNextNodeNo(nextNodeNo);
			}
		}
		swfPathDto.setPathList(pathList);
		httpServletRequest.setAttribute("pathList", pathList);
		httpServletRequest.setAttribute("swfPath", swfPathDto);
	}

	/**
	 * 获取报案内容
	 * @param prpLcheck
	 * @return
	 * @throws Exception
	 */
	private String getRegistContext(PrpLcheck prpLcheck) throws Exception {
		String context = "";
		StringBuffer strRegistContext = new StringBuffer();
		String queryCondition = " registNo='" + prpLcheck.getId().getRegistNo() + "' and TextType='" + 1 + "' order by lineNo ASC";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(queryCondition);
		List<PrpLregistText> resultList = prpLregistTextService.findPrpLregistText(queryRule);
		PrpLregistText prpLregistText = null;
		for (int i = 0; i < resultList.size(); i++) {
			prpLregistText = resultList.get(i);
			strRegistContext.append(prpLregistText.getContext());
		}
		if (!"".equals(strRegistContext.toString())) {
			context = strRegistContext.toString();
		}
		return context;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public AcciCheckService getAcciCheckService() {
		return acciCheckService;
	}

	public void setAcciCheckService(AcciCheckService acciCheckService) {
		this.acciCheckService = acciCheckService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public DAACertainLossViewHelper getDaaCertainLossViewHelper() {
		return daaCertainLossViewHelper;
	}

	public void setDaaCertainLossViewHelper(DAACertainLossViewHelper daaCertainLossViewHelper) {
		this.daaCertainLossViewHelper = daaCertainLossViewHelper;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		super.setCodeService(codeService);
		this.codeService = codeService;
	}

	public SendUndwrtViewHelper getSendUndwrtViewHelper() {
		return sendUndwrtViewHelper;
	}

	public void setSendUndwrtViewHelper(SendUndwrtViewHelper sendUndwrtViewHelper) {
		this.sendUndwrtViewHelper = sendUndwrtViewHelper;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCmainCargoService getPrpCmainCargoService() {
		return prpCmainCargoService;
	}

	public void setPrpCmainCargoService(PrpCmainCargoService prpCmainCargoService) {
		this.prpCmainCargoService = prpCmainCargoService;
	}

}