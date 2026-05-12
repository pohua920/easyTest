package com.sinosoft.claim.schedule.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * <p>
 * Title: ThirdPartyViewHelper
 * </p>
 * <p>
 * Description:新增定损调度ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2005
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public class ThirdPartyViewHelper {
	/** 查勘服务 */
	private CheckService checkService;
	/** 代码服务 */
	private CodeService codeService;
	/** 调度服务 */
	private ScheduleService scheduleService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 报案服务 */
	private RegistService registService;
	private PrpDcodeService prpDcodeService;

	public ThirdPartyViewHelper() {

	}

	/**
	 * 保存查勘时查勘页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return checkDto 查勘数据传输数据结构
	 * @throws Exception
	 */

	public CheckDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");

		// 继承对check,checkText表的赋值
		CheckDto checkDto = new CheckDto();
		int intCheckLossIndex = 1;// 序号
		List<PrpLcheckLoss> prpLcheckLossDtoList = null;
		boolean newScheduleItem = false;

		httpServletRequest.setAttribute("prpLnodeType", "check");
		String prplregistDamageAddress = httpServletRequest.getParameter("prplregistDamageAddress");

		ArrayList<PrpLthirdParty> thirdPartyDtoList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;
		// 从界面得到输入数组
		String prpLthirdPartyClaimNo = httpServletRequest.getParameter("prpLcheckClaimNo");
		String prpLthirdPartyRiskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String prpLthirdPartyRegistNo = (String) httpServletRequest.getParameter("businessNo");
		String prpLcheckLossPolicyNo = httpServletRequest.getParameter("policyNo");
		// 注意此处保存到checkDto里
		PrpLcheck prpLcheck1 = new PrpLcheck();
		prpLcheck1.getId().setRegistNo(prpLthirdPartyRegistNo);
		checkDto.setPrpLcheck(prpLcheck1);

		// 先取立案号码，很重要，不要从页面上取得。。。
		String claimNo = prpLthirdPartyClaimNo;
		String registNo = prpLthirdPartyRegistNo;
//		boolean isCompelRiskOnly = false;
//		String strOnlyDAZ = this.codeService.translateRiskCodetoConfigCode(prpLthirdPartyRiskCode);
//		if ("RISKCODE_DAZ".equals(strOnlyDAZ)) {
			// 这里可以判断出是单独交强险报的案
//			isCompelRiskOnly = true;
//		}
		// reason: 因为考虑到录入的时候，可能没有立案，但是在提交的时候，做了立案，导致立案号没写入。
		if (claimNo == null || claimNo.length() < 2) {
			claimNo = this.codeService.translateBusinessCode(registNo, true);
		}
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLcheckClauseType");
		String[] prpLthirdPartySerialNo = httpServletRequest.getParameterValues("prpLthirdPartySerialNo");
		String[] prpLthirdPartyLicenseNo = httpServletRequest.getParameterValues("prpLthirdPartyLicenseNo");
		String[] prpLthirdPartyLicenseColorCode = httpServletRequest.getParameterValues("licenseColorCode");
		String[] prpLthirdPartyCarKindCode = httpServletRequest.getParameterValues("carKindCode");
		String[] prpLthirdPartyInsureCarFlag = httpServletRequest.getParameterValues("insureCarFlag");
		String[] prpLthirdPartyEngineNo = httpServletRequest.getParameterValues("prpLthirdPartyEngineNo");
		String[] prpLthirdPartyFrameNo = httpServletRequest.getParameterValues("prpLthirdPartyFrameNo");
		String[] prpLthirdPartyBrandName = httpServletRequest.getParameterValues("prpLthirdPartyBrandName");
		String[] prpLthirdPartyRunDistance = httpServletRequest.getParameterValues("prpLthirdPartyRunDistance");
		String[] prpLthirdPartyUseYears = httpServletRequest.getParameterValues("prpLthirdPartyUseYears");
		String[] prpLthirdPartyDutyPercent = httpServletRequest.getParameterValues("prpLthirdPartyDutyPercent");
		String[] prpLthirdPartyInsuredFlag = httpServletRequest.getParameterValues("insuredFlag");
		String[] prpLthirdPartyInsureComCode = httpServletRequest.getParameterValues("prpLthirdPartyInsureComCode");
		String[] prpLthirdPartyInsureComName = httpServletRequest.getParameterValues("prpLthirdPartyInsureComName");
		String[] prpLthirdPartyVINNo = httpServletRequest.getParameterValues("prpLthirdPartyVINNo");
		// 得到隐藏的标志：为new 需要保存到库中 其它为不需要
//		String[] prpLthirdPartyNewAddFlag = httpServletRequest.getParameterValues("prpLthirdPartyNewAddFlag");

		// 调度标底用的
		List<PrpLscheduleItem> scheduleItemDtoList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		int scheduleId = 1;
		// 对象赋值
		if (prpLthirdPartySerialNo != null) {
			// 三者车辆部分开始
			for (int index = 1; index < prpLthirdPartySerialNo.length; index++) {
				// 只增加新添的 以前的不增加 在此处判断
				prpLthirdParty = new PrpLthirdParty();
				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartySerialNo[index])));
				prpLthirdParty.setClaimNo(claimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if(prpLthirdPartyLicenseNo[index]==null||"".equals(prpLthirdPartyLicenseNo[index])){
					prpLthirdPartyLicenseNo[index]=" ";
				}
				prpLthirdParty.setLicenseNo(prpLthirdPartyLicenseNo[index].trim());
				prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode[index]);
				prpLthirdParty.setCarKindCode(prpLthirdPartyCarKindCode[index]);
				prpLthirdParty.setInsureCarFlag(prpLthirdPartyInsureCarFlag[index]);
				prpLthirdParty.setEngineNo(prpLthirdPartyEngineNo[index]);
				prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo[index]);
				prpLthirdParty.setBrandName(prpLthirdPartyBrandName[index]);
				prpLthirdParty.setRunDistance(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyRunDistance[index])));
				prpLthirdParty.setUseYears(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartyUseYears[index])));
				prpLthirdParty.setDutyPercent(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyDutyPercent[index])));
				prpLthirdParty.setInsuredFlag(prpLthirdPartyInsuredFlag[index]);
				prpLthirdParty.setInsureComCode(prpLthirdPartyInsureComCode[index]);
				prpLthirdParty.setInsureComName(prpLthirdPartyInsureComName[index]);
				prpLthirdParty.setVINNo(prpLthirdPartyVINNo[index]);

				// 加入集合
				thirdPartyDtoList.add(prpLthirdParty);

				// 整理调度情况
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLscheduleItem.getId().setItemNo(prpLthirdParty.getId().getSerialNo());
				prpLscheduleItem.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				prpLscheduleItem.setClaimComCode(user.getComCode());

				// 表示是否选中
				prpLscheduleItem.setSelectSend("1");
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setNextNodeNo("certa");
				prpLscheduleItem.setCheckSite(prplregistDamageAddress);
				// 如果是新增的定损,保存 add if条件 by liyanjie
				// 如果单独交强的话，标的车不增加。。

				// 加入调度标的集合
//				if (!(isCompelRiskOnly && prpLscheduleItem.getId().getItemNo() == 1) && prpLthirdPartyNewAddFlag[index].equals("new")) {
				scheduleItemDtoList.add(prpLscheduleItem);
				// 设置调度的标签显示 shcheduleItemNote
				String strTemp = "標的:";
				if (prpLscheduleItem.getId().getItemNo() != 1){
					strTemp = "三者:";
				}
				checkDto.setScheduleItemNote(checkDto.getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
//				}
			}

			// 查勘集合中加入三者车辆
			checkDto.setPrpLthirdPartyList(thirdPartyDtoList);
			// 查勘集合中加入调度任务标的
			checkDto.setPrpLscheduleItemList(scheduleItemDtoList);
		}

		// Reason:损失部位模块信息调整到涉案车辆信息中，相应模块做调整
		// ---------------------损失部位 PrpLthirdCarLoss
		// begin------------------------------------
		List<PrpLthirdCarLoss> thirdCarLossDtoList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = (String) httpServletRequest.getParameter("businessNo");
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

		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdCarLossSerialNo != null) {
			for (int index2 = 1; index2 < prpLthirdCarLossSerialNo.length; index2++) {

				prpLthirdCarLoss = new PrpLthirdCarLoss();
				prpLthirdCarLoss.getId().setRegistNo(prpLthirdCarLossRegistNo);
				prpLthirdCarLoss.setRiskCode(prpLthirdCarLossRiskCode);
				prpLthirdCarLoss.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdCarLossSerialNo[index2])));
				prpLthirdCarLoss.getId().setItemNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdCarLossItemNo[index2])));
				prpLthirdCarLoss.setLicenseNo(prpLthirdCarLossLicenseNo[index2]);
				prpLthirdCarLoss.setCompCode(prpLthirdCarLossCompCode[index2]);
				prpLthirdCarLoss.setCompName(prpLthirdCarLossCompName[index2]);
				prpLthirdCarLoss.setLossGrade(prpLthirdCarLossLossGrade[index2]);
				prpLthirdCarLoss.setLossDesc(prpLthirdCarLossLossDesc[index2]);
				prpLthirdCarLoss.setFlag(prpLthirdCarLossFlag[index2]);
				prpLthirdCarLoss.setPartCode(prpLthirdCarLossPartCode[index2]);
				prpLthirdCarLoss.setPartName(prpLthirdCarLossPartName[index2]);

				// 加入集合
				thirdCarLossDtoList.add(prpLthirdCarLoss);
			}
			// 查勘集合中加入损失部位
			checkDto.setPrpLthirdCarLossList(thirdCarLossDtoList);
		}

		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp begin------------------------------------*/
		ArrayList<PrpLthirdProp> thirdPropDtoList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;

		// 从界面得到输入数组
		String[] prpLthirdPropItemNo = httpServletRequest.getParameterValues("prpLthirdPropItemNo");
		String[] prpLthirdPropLicenseNo = httpServletRequest.getParameterValues("prpLthirdPropLicenseNo");
		String[] lossItemCode = httpServletRequest.getParameterValues("prpLthirdLossItemCode");
		String[] LossItemName = httpServletRequest.getParameterValues("prpLthirdLossItemName");
		String[] prpLthirdPropLossDesc = httpServletRequest.getParameterValues("prpLthirdPropLossDesc");
		String[] prpLthirdPropFlag = httpServletRequest.getParameterValues("prpLthirdPropFlag");

		// Reason:损失模块信息合到涉案车辆、人伤、财产损失信息中
		String[] prpLthirdPropKindCode = httpServletRequest.getParameterValues("prpLthirdPropKindCode");
		String[] prpLthirdPropLossFee = httpServletRequest.getParameterValues("prpLthirdPropLossFee");

		// 对象赋值
		// 损失部位部分开始
		if (prpLthirdPropItemNo != null) {
			prpLcheckLossDtoList = new ArrayList<PrpLcheckLoss>();
			for (int index = 1; index < prpLthirdPropItemNo.length; index++) {

				prpLthirdProp = new PrpLthirdProp();

				// Reason:预估损失模块合到涉案车辆信息中
				PrpLcheckLoss prpLcheckLoss = new PrpLcheckLoss();
				prpLcheckLoss.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLcheckLoss.setClaimNo(claimNo);
				prpLcheckLoss.setRiskCode(prpLthirdPartyRiskCode);
				prpLcheckLoss.setPolicyNo(prpLcheckLossPolicyNo);
				prpLcheckLoss.getId().setSerialNo(intCheckLossIndex);
				prpLcheckLoss.setReferSerialNo(intCheckLossIndex);
				prpLcheckLoss.setKindCode(DataUtils.nullToEmpty(prpLthirdPropKindCode[index]));
				prpLcheckLoss.setLossFeeType("3");
				prpLcheckLoss.setLossFee(Double.parseDouble(DataUtils.nullToZero(prpLthirdPropLossFee[index])));
				prpLcheckLoss.setFlag("");
				intCheckLossIndex++;
				prpLcheckLossDtoList.add(prpLcheckLoss);

				prpLthirdProp.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdProp.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdProp.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPropItemNo[index])));
				prpLthirdProp.setLicenseNo(prpLthirdPropLicenseNo[index]);
				prpLthirdProp.setLossItemCode(lossItemCode[index]);
				prpLthirdProp.setLossItemName(LossItemName[index]);
				prpLthirdProp.setLossItemDesc(prpLthirdPropLossDesc[index]);

				prpLthirdProp.setFlag(prpLthirdPropFlag[index]);

				// 加入集合
				thirdPropDtoList.add(prpLthirdProp);
			}

			// 报案集合中加入三者损失
			checkDto.setPrpLthirdPropList(thirdPropDtoList);
			checkDto.setPrpLcheckLossList(prpLcheckLossDtoList);

			// 增加人伤定损调度信息，如果有人，就进行调度

			if (thirdPropDtoList != null && thirdPropDtoList.size() > 0) {
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
				prpLscheduleItem.setNextNodeNo("propc");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setClaimComCode(user.getComCode());

				scheduleItemDtoList.add(prpLscheduleItem);

				// 显示标的
				checkDto.setScheduleItemNote(checkDto.getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
			}

		}

		/*---------------------人员伤亡跟踪 PrpLpersonTrace --------begin----------------------------*/

		List<PrpLpersonTrace> personTraceDtoList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
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

		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLpersonTracePersonNo != null) {
			for (int index = 1; index < prpLpersonTracePersonNo.length; index++) {
				prpLpersonTrace = new PrpLpersonTrace();
				prpLpersonTrace.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLpersonTrace.setClaimNo(claimNo);
				prpLpersonTrace.setPolicyNo(prpLcheckLossPolicyNo);
				prpLpersonTrace.getId().setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonNo[index])));
				prpLpersonTrace.setPersonName(prpLpersonTracePersonName[index]);
				prpLpersonTrace.setPersonSex(prpLpersonTracePersonSex[index]);
				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));
				prpLpersonTrace.setIdentifyNumber(prpLpersonTraceIdentifyNumber[index]);
				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				prpLpersonTrace.setReferKind(DataUtils.nullToEmpty(prpLpersonTraceReferKind[index]));
				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);
				prpLpersonTrace.setHospital(prpLpersonTraceHospital[index]);
				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);
				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);
				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);
				// 加入集合
				personTraceDtoList.add(prpLpersonTrace);
				
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
				prpLscheduleItem.setNextNodeNo("wound");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				scheduleItemDtoList.add(prpLscheduleItem);
				// 显示标的
				checkDto.setScheduleItemNote(checkDto.getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");

			}
		}
		// 报案集合中加入损失部位
		checkDto.setPrpLpersonTraceList(personTraceDtoList);
		// 整理调度用的标的信息,去掉最後的一个"/"
		if (checkDto.getScheduleItemNote().length() > 1) {
			String strTemp = checkDto.getScheduleItemNote();
			strTemp = strTemp.substring(0, strTemp.length() - 1);
			checkDto.setScheduleItemNote(strTemp);
		}

		// 整理数据，整理定损调度的数据，如果当提交的时候。。将新的数据放入prplscheduleItem中，並保留已经调度过的数据
		// 检查定损调度的情况，如果存在定损调度，检查是否已经调度过，如果没有调度过，按照没有调度过处理
		// 查询调度过的
		String strSql = " registno ='" + checkDto.getPrpLcheck().getId().getRegistNo() + "'";
		// 查询数据
		List<PrpLscheduleItem> prpLscheduleItemList = (List<PrpLscheduleItem>) this.scheduleService.findItemByConditions(strSql);
		PrpLscheduleItem prpLscheduleItemold = null;
		List<PrpLscheduleItem> scheduleItemLastList = new ArrayList<PrpLscheduleItem>();
		if (prpLscheduleItemList == null || prpLscheduleItemList.size() < 1) {
			// 不用检查scheduleITem的。。
			checkDto.setPrpLscheduleItemList(scheduleItemDtoList);
		} else { // 检查整理好的数据中，是否已经有已经调度过的数据

			boolean findit = false;
			for (int i = 0; i < scheduleItemDtoList.size(); i++) {
				prpLscheduleItem = (PrpLscheduleItem) scheduleItemDtoList.get(i);
				// 原则，相同的，以原来的数据为准，没有的已後来的为准
				findit = false;
				for (int j = 0; j < prpLscheduleItemList.size(); j++) {
					prpLscheduleItemold = prpLscheduleItemList.get(j);
					if(prpLscheduleItemold.getNextNodeNo().equals(prpLscheduleItem.getNextNodeNo())){
						if (prpLscheduleItem.getId().getItemNo().equals(prpLscheduleItemold.getId().getItemNo())) { // 如果存在旧的数据，就要用旧的数据，不要用新的数据
							prpLscheduleItem = prpLscheduleItemold;
							findit = true;
							break;
						}
					}
					// 原则，相同的，以原来的数据为准，没有的已後来的为准

				}
				// 有新增的标的
				if (findit == false){
					newScheduleItem = true;
					if("certa".equals(prpLscheduleItem.getNextNodeNo())&&DataUtils.emptyToNull(prpLscheduleItem.getCheckSite()) == null){
						for(int k = 0 ; k<prpLscheduleItemList.size();k++ ){
							if("certa".equals(prpLscheduleItemList.get(k).getNextNodeNo())){
								prpLscheduleItem.setCheckSite(prpLscheduleItemList.get(k).getCheckSite());
								break;
							}
						}
					}
				}
				scheduleItemLastList.add(prpLscheduleItem);
			}
			checkDto.setPrpLscheduleItemList(scheduleItemLastList);
		}
		checkDto.setNewScheduleItem(newScheduleItem);
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
	public void registDtoToView(HttpServletRequest httpServletRequest, String registNo, String editType) throws Exception {
		// 取得当前用户信息，写操作员信息到查勘中
		RegistDto registDto = this.registService.findByPrimaryKey(registNo);
		CheckDto checkDtoTemp = this.checkService.findByPrimaryKey(registNo);
		// 如果有数值的话，重新给界面复制

		// 根据查询出来的数据内容，给PrpLcheckDto赋值
		PrpLcheck prpLcheck = new PrpLcheck();
		// 设置数值
		prpLcheck.getId().setReferSerialNo(1);
		prpLcheck.getId().setRegistNo(registNo);
		prpLcheck.setDamageStartDate(registDto.getPrpLregist().getDamageStartDate());
		prpLcheck.setDamageStartHour(registDto.getPrpLregist().getDamageStartHour());
		prpLcheck.setRiskCode(registDto.getPrpLregist().getRiskCode());
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, prpLcheck);

		// 设置各个子表信息项到窗体表单
		CheckDto checkDto = new CheckDto();
		checkDto.setPrpLcheckLossList(checkDtoTemp.getPrpLcheckLossList());
		checkDto.setPrpLthirdPartyList(registDto.getPrpLthirdPartyList());
		checkDto.setPrpLthirdCarLossList(registDto.getPrpLthirdCarLossList());
		checkDto.setPrpLpersonTraceList(checkDtoTemp.getPrpLpersonTraceList());
		checkDto.setPrpLthirdPropList(registDto.getPrpLthirdPropList());
		checkDto.setPrpLcheck(prpLcheck);

		// 设置各个子表中的信息和显示
		setSubInfo(httpServletRequest, checkDto);
		// 保存报案号到页面
		httpServletRequest.setAttribute("businessNo", registNo);
		httpServletRequest.setAttribute("prpLregist", registDto.getPrpLregist());
	}

	/**
	 * 根据PrpCheckDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param checkDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, CheckDto checkDto) throws Exception {

		// [涉案车辆]给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		arrayList = checkDto.getPrpLthirdPartyList();
		prpLthirdParty.setThirdPartyList(arrayList);
		prpLthirdParty.setNodeType("check");
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		String strRiskType = codeService.translateRiskCodetoRiskType(checkDto.getPrpLcheck().getRiskCode());

		// Reason:损失部位模块合到涉到车辆信息中後，其它相应模块做调整
		// 给损失部位多行多行列表准备数据

		List<PrpLthirdCarLoss> arrayListThirdCarLoss = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = new PrpLthirdCarLoss();
		arrayListThirdCarLoss = checkDto.getPrpLthirdCarLossList();
		prpLthirdCarLoss.setThirdCarLossList(arrayListThirdCarLoss);
		httpServletRequest.setAttribute("prpLthirdCarLoss", prpLthirdCarLoss);

		// Reason:损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());

		// 事故估损金额多行列表准备数据
		List<PrpLcheckLoss> arrayList2 = new ArrayList<PrpLcheckLoss>();
		PrpLcheckLoss prpLcheckLoss = new PrpLcheckLoss();
		arrayList2 = (ArrayList<PrpLcheckLoss>) checkDto.getPrpLcheckLossList();
		if (arrayList2 != null) {
			for (int indexCheck = 0; indexCheck < arrayList2.size(); indexCheck++) {
				PrpLcheckLoss prpLcheckLoss1 = new PrpLcheckLoss();
				prpLcheckLoss1 = (PrpLcheckLoss) arrayList2.get(indexCheck);

				// 对险别进行转换
				String kindCode = prpLcheckLoss1.getKindCode();
				String kindName = this.codeService.translateKindCode(checkDto.getPrpLcheck().getRiskCode(), kindCode, true);
				prpLcheckLoss1.setKindName(kindName);
			}
		}
		prpLcheckLoss.setPrpLcheckLossList(arrayList2);
		httpServletRequest.setAttribute("prpLcheckLoss", prpLcheckLoss);

		// 给人员伤亡跟踪多行多行列表准备数据
		PrpLpersonTrace prpLpersonTrace = new PrpLpersonTrace();
		List<PrpLpersonTrace> arrayListPersonTrace = checkDto.getPrpLpersonTraceList();
		prpLpersonTrace.setPersonTraceList(arrayListPersonTrace);

		if (checkDto.getPrpLpersonTraceList() != null) {
			String jobCode = null;
			String jobCodes = null;
			String conditions = null;
			PrpDcode prpDcode = null;
			List<PrpDcode> prpDcodeList = null;
			PrpLpersonTrace prplpersonTrace = null;
			for(int i=0;i<arrayListPersonTrace.size();i++){
				prplpersonTrace = arrayListPersonTrace.get(i);
				prplpersonTrace.setPrpLpersonTraceReferKind(prplpersonTrace.getReferKind());
				// 获取一级行业和二级行业信息 start
				jobCode = prplpersonTrace.getJobCode();// 三级行业代码
				if (!"".equals(jobCode) && jobCode != null) {
					jobCodes = jobCode.substring(0, jobCode.length() - 2);// 一级行业代码
					conditions = "codecode='" + jobCodes + "' and flag='1' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					prpDcodeList = prpDcodeService.findByConditions(conditions);
					if(prpDcodeList!=null&&prpDcodeList.size()>0){
						prpDcode = prpDcodeList.get(0);
						prplpersonTrace.setJobCode1(prpDcode.getId().getCodeCode());
						prplpersonTrace.setJobName1(prpDcode.getCodeCName());
					}
					jobCodes = jobCode.substring(0, jobCode.length() - 1);// 二级行业代码
					conditions = "codecode='" + jobCodes + "' and flag='2' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%,"+strRiskType+",%' ";
					prpDcodeList = prpDcodeService.findByConditions(conditions);
					if(prpDcodeList!=null&&prpDcodeList.size()>0){
						prpDcode = prpDcodeList.get(0);
						prplpersonTrace.setJobCode2(prpDcode.getId().getCodeCode());
						prplpersonTrace.setJobName2(prpDcode.getCodeCName());
					}
				}
			}
		}

		httpServletRequest.setAttribute("prpLpersonTrace", prpLpersonTrace);

		// Reason:人伤跟踪信息模块中涉及险种以列表框多选形式显示
		// 将险别名称改成D1-车上人员责任险的方式,只包括三者险与车上人员责任险
		List<PrpCitemKind> prpLcheckItemKindNew = new ArrayList<PrpCitemKind>();
		List<PrpCitemKind> itemKindList = new ArrayList<PrpCitemKind>();
		List<PrpCitemKind> itemKindListNew = new ArrayList<PrpCitemKind>();

		String policyNo = httpServletRequest.getParameter("policyNo");
		if (!policyNo.equals("") && policyNo != null) {
			PrpLcheck prpLcheck = checkDto.getPrpLcheck();
			String damageDate = new DateTime(prpLcheck.getDamageStartDate()).toString();
			String damageHour = prpLcheck.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			itemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		for (int i = 0; i < itemKindList.size(); i++) {
			PrpCitemKind prpCitemKind = (PrpCitemKind) itemKindList.get(i);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			//增加判断prpCitemKind的险别是否属于可赔人伤
			if (ConstantsCollection.KindCodeForPerson.contains(prpCitemKind.getKindCode())){ 
				itemKindListNew.add(prpCitemKind);
			}
			prpLcheckItemKindNew.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", itemKindListNew);
		httpServletRequest.setAttribute("prpLcheckItemKindList", prpLcheckItemKindNew);

		// Reason:在查勘页面中加上其它损失模块
		List<PrpLthirdProp> arrayListThirdProp = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = new PrpLthirdProp();
		arrayListThirdProp = checkDto.getPrpLthirdPropList();
		prpLthirdProp.setThirdPropList(arrayListThirdProp);
		httpServletRequest.setAttribute("prpLthirdProp", prpLthirdProp);

	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcheck 查勘的数据类
	 * @throws Exception
	 */

	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLcheck prpLcheck) throws Exception {
		// 傷亡類型
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		// 查勘性质列表
		ArrayList<PrpDcode> checkNatures = (ArrayList<PrpDcode>) this.codeService.getCodeType("CheckNature", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("checkNatures", checkNatures);
		// 赔案类别
		List<PrpDcode> caseCodes = this.codeService.getCodeType("CaseCode", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("caseCodes", caseCodes);
		// 出险地点分类
		List<PrpDcode> damageAddresss = this.codeService.getCodeType("DamageAddress", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("damageAddresss", damageAddresss);
		// 事故赔偿责任
		List<PrpDcode> indemnityDutys = this.codeService.getCodeType("IndemnityDuty", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDutys);

		// 得到实赔类型列表
		List<PrpDcode> reportTypes = this.codeService.getCodeType("ReportType", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// 得到案件种类列表列表
		List<PrpDcode> claimTypes = this.codeService.getCodeType("CaseCode", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// 得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = this.codeService.getCodeType("DamageAddress", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// 得到车辆种类列表
		List<PrpDcode> carKindCodes = this.codeService.getCodeTypeCarKind("CarKind", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 得到车牌底色列表
		List<PrpDcode> licenseColorCode = this.codeService.getCodeType("LicenseColor", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// 得到赔偿责任列表
		List<PrpDcode> indemnityDuty = this.codeService.getCodeType("IndemnityDuty", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("indemnityDutys", indemnityDuty);
		// 得到赔案类别列表
		List<PrpDcode> escapeFlags = this.codeService.getCodeType("CaseCode", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("escapeFlags", escapeFlags);
		// 得到得到性别
		List<PrpDcode> driverSex = this.codeService.getCodeType("SexCode", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// 得到职业分类
		List<PrpDcode> driverOccupation = this.codeService.getCodeType("Occupation", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// 得到文化程度
		List<PrpDcode> education = this.codeService.getCodeType("Education", prpLcheck.getRiskCode());
		httpServletRequest.setAttribute("educations", education);
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
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

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

}
