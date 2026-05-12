package com.sinosoft.claim.regist.util;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 SATRT
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegist;
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegistTemp;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalSourceVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.Driver;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.PersonTrace;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ThirdParty;
//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalRiskSourceVo;
import com.sinosoft.claim.bl.facade.BLPrpLclaimFacade;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.SwfPathDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCcarDriver;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpCopyInsured;
import com.sinosoft.claim.schema.model.PrpCopyItemCar;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistLog;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLrelatePerson;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCcarDriverService;
import com.sinosoft.claim.schema.service.facade.PrpCengageService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCarGoSubService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCopyInsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCopyItemCarService;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistLogService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.ui.control.action.UIWorkFlowAction;
import com.sinosoft.claim.ui.control.viewHelper.WorkFlowViewHelper;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.claim.schema.model.PrpCopyItemCarId;

import com.sinosoft.prpall.interf.Visa;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <p>
 * Title: RegistViewHelper
 * </p>
 * <p>
 * Description:报案ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 *  policyListToView方法中增加出险时间
 */
public class DAARegistViewHelper extends RegistViewHelper {
	/** 报案文字信息每行最大显示的字符长度 */
	private int RULE_LENGTH = 70; // rule字段的长度
	/** 报案服务 */
	private RegistService registService;
	/** 报案数据传输对象 */
	private RegistDto registDto;
	/** 代码服务 */
	private CodeService codeService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 报案信息服务 */
	private PrpLregistService prpLregistService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 赔案保单关联信息服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 报案文字信息服务 */
	private PrpLregistTextService prpLregistTextService;
	/** 报案修改轨迹信息服务 */
	private PrpLregistLogService prpLregistLogService;
	/** 特别约定信息服务 */
	private PrpCengageService prpCengageService;
	/** 保单基本信息服务 */
	private PrpCmainService prpCmainService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 批单服务 */
	private EndorseService endorseService;
	/** 机构信息服务 */
	private PrpDcompanyService prpDcompanyService;
	/** 再保管理对象 */
	private ReinsServiceManager reinsServiceManager;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 通用代码数据服务 */
	private PrpDcodeService prpDcodeService;
	private ScheduleService scheduleService;
	private PrpPheadService prpPheadService;
	private PrpCitemCarService prpCitemCarService;
	private PowerService powerService;
	private PrpCaddressService prpCaddressService;
	private PrpCinsuredService prpCinsuredService;
	private PrpCplaneService prpCplaneService;
	private PrpCitemShipService prpCitemShipService;
	private PrpCmainLiabService prpCmainLiabService;
	private PrpCmainCargoService prpCmainCargoService;
	private PrpCmainCarGoSubService prpCmainCarGoSubService;
	private PrpCcarDriverService prpCcarDriverService;
	private PrpCplanService prpCplanService;
	private PrpCopyInsuredService prpCopyInsuredService;
	private PrpCopyItemCarService prpCopyItemCarService;
	/**
	 * 默认构造方法
	 */
	public DAARegistViewHelper() {

	}

	/**
	 * 保存报案时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return registDto 报案数据传输数据结构
	 * @throws Exception
	 */
	public RegistDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对regist,registText表的赋值
		RegistDto registDto = super.viewToDto(httpServletRequest);
		/* 强三 ---关联表的存储----------------- */
		// 目前只存储了强三的单号
		Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
		Prplregistrpolicy prpLRegistRPolicy_comp = new Prplregistrpolicy();
		String registNo = (String) httpServletRequest.getAttribute("registNo");
		String mainPolicyNo = (String) httpServletRequest.getParameter("mainPolicyNo");
		String quaryPolicyNo = (String) httpServletRequest.getParameter("quaryPolicyNo");
		String policyNo = (String) httpServletRequest.getParameter("prpLregistPolicyNo");
		String prpLregistRPPolicyRiskCode = (String) httpServletRequest.getParameter("prpLregistRiskCode");
		// 交强险迁移 报案类型 0 ：商业险单独报案 1：交强险单独报案 2：商业、交强险关联报案
		String registType = httpServletRequest.getParameter("registType");
		boolean isCompelRiskOnly = false; // 判断单独交强报案的情况，默认都是否。
		List<Prplregistrpolicy> prpLRegistRPolicyList = new ArrayList<Prplregistrpolicy>();
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		String flowId = prpLregistrpolicyService.findSwfLogId(registNo);
		if (DataUtils.emptyToNull(policyNo) != null && DataUtils.emptyToNull(mainPolicyNo) != null && "2".equals(DataUtils.emptyToNull(registType))) {
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(policyNo)) {
				prpLRegistRPolicy.setRegistFlag("1");
			} else {
				prpLRegistRPolicy.setRegistFlag("0");
			}
			prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
			prpLRegistRPolicy.getId().setRegistNo(registNo);
			prpLRegistRPolicy.getId().setPolicyNo(policyNo);
			prpLRegistRPolicy.setPolicyType("1");
			prpLRegistRPolicy.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(policyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(mainPolicyNo)) {
				prpLRegistRPolicy_comp.setRegistFlag("1");
			} else {
				prpLRegistRPolicy_comp.setRegistFlag("0");
			}
			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
			prpLRegistRPolicy_comp.setRiskCode(compelRiskCode);
			
			prpLRegistRPolicy_comp.getId().setRegistNo(registNo);
			prpLRegistRPolicy_comp.getId().setPolicyNo(mainPolicyNo);
			prpLRegistRPolicy_comp.setPolicyType("3");
			prpLRegistRPolicy_comp.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(mainPolicyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy_comp.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy_comp.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy_comp);
			// 以上为关联表增加两条的情况
		} else {
			// 交强险迁移 2013-03-15 chenjie
			if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
				String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
				prpLRegistRPolicy.getId().setPolicyNo(mainPolicyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				prpLRegistRPolicy.setPolicyType("3");
				prpLRegistRPolicy.setRiskCode(compelRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
				isCompelRiskOnly = true;
			} else {
				// 存储费关联;
				prpLRegistRPolicy.getId().setPolicyNo(policyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				String strConfigCode = codeService.translateRiskCodetoConfigCode(prpLregistRPPolicyRiskCode);
				if ("RISKCODE_DAZ".equals(strConfigCode)) {
					prpLRegistRPolicy.setPolicyType("3");
				} else {
					prpLRegistRPolicy.setPolicyType("1");
				}
				// reason:判断出单独交强的情况
				String strOnlyDAZ = codeService.translateRiskCodetoConfigCode(registDto.getPrpLregist().getRiskCode());
				if ("RISKCODE_DAZ".equals(strOnlyDAZ)) {
					// 这里可以判断出是单独交强险报的案
					isCompelRiskOnly = true;
				}
				// 单独交强
				prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
			}
			for(PrpLclaim temp : prpLclaimList){
				if(prpLRegistRPolicy.getId().getPolicyNo().equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
		}
		registDto.setPrpLRegistRPolicyList(prpLRegistRPolicyList);
		/* 强三 ---关联表的存储 */
		List<PrpLthirdParty> thirdPartyList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;

		// 从界面得到输入数组
		String prpLthirdPartyRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPartyRiskCode = httpServletRequest.getParameter("prpLregistRiskCode");
		// 交强险迁移 2013-03-15 chenjie
		if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
			prpLthirdPartyRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String prpLthirdPartyClaimNo = httpServletRequest.getParameter("prpLregistClaimNo");
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLregistClauseType");
		String[] prpLthirdPartySerialNo = httpServletRequest.getParameterValues("prpLthirdPartySerialNo");
		String[] prpLthirdPartyLicenseNo = httpServletRequest.getParameterValues("prpLthirdPartyLicenseNo");
		String[] prpLthirdPartyLicenseColorCode = httpServletRequest.getParameterValues("licenseColorCode");
		String[] prpLthirdPartyCarKindCode = httpServletRequest.getParameterValues("carKindCode");
		String[] prpLthirdPartyInsureCarFlag = httpServletRequest.getParameterValues("insureCarFlag");
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
		String[] prpLthirdPartySelectSend = httpServletRequest.getParameterValues("prpLthirdPartySelectSend");
		// 差异化begin---add by liuwei----2013-5-12----------
		String[] prpLthirdPartyGarageHeadName = httpServletRequest.getParameterValues("prpLthirdPartyGarageHeadName");
		String[] prpLthirdPartyRelationship = httpServletRequest.getParameterValues("prpLthirdPartyRelationship");
		String[] prpLthirdPartyDrivingAddress = httpServletRequest.getParameterValues("prpLthirdPartyDrivingAddress");
		String[] prpLthirdPartyCarryingUnit = httpServletRequest.getParameterValues("prpLthirdPartyCarryingUnit");
		String[] prpLthirdPartyInsuranceNo = httpServletRequest.getParameterValues("prpLthirdPartyInsuranceNo");
		String[] prpLthirdPartyIsInsurance = httpServletRequest.getParameterValues("prpLthirdPartyIsInsurance");
		String[] prpLthirdPartyCarryingNumber = httpServletRequest.getParameterValues("prpLthirdPartyCarryingNumber");
		String[] prpLthirdPartyCarsOwners = httpServletRequest.getParameterValues("prpLthirdPartyCarsOwners");
		String[] prpLthirdPartyInsuredIdentity = httpServletRequest.getParameterValues("prpLthirdPartyInsuredIdentity");
		// 差异化end---------------------
		// 取赔案类型的选择
		/*----------包括查勘调度，定损调度 PrpLscheduleMainWF ,PrpLscheduleItem --------------*/
		List<PrpLscheduleItem> scheduleItemDtoList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		int scheduleId = 1; // 调度号的id
		PrpLscheduleMainWF prpLscheduleMainWF = null;
		// 通过代码对照表转换riskcode
		String riskCode = registDto.getPrpLregist().getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		if ("D".equals(strRiskType)) {
			PrpLregist prpLregist = registDto.getPrpLregist();
			prpLscheduleMainWF = new PrpLscheduleMainWF();
			prpLscheduleMainWF.getId().setScheduleID(1);
			prpLscheduleMainWF.getId().setRegistNo(prpLregist.getRegistNo());
			prpLscheduleMainWF.setSurveyNo(0);
			prpLscheduleMainWF.setClaimComCode(prpLregist.getComCode());
			prpLscheduleMainWF.setRiskCode(prpLregist.getRiskCode());
			prpLscheduleMainWF.setPolicyNo(prpLregist.getPolicyNo());
			prpLscheduleMainWF.setOperatorCode("");
			prpLscheduleMainWF.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLscheduleMainWF.setInputHour(DateTime.current().getHour());
			prpLscheduleMainWF.setScheduleObjectID("_");
			prpLscheduleMainWF.setScheduleObjectName(" ");
			String scheduleType = httpServletRequest.getParameter("scheduleType");
			prpLscheduleMainWF.setScheduleType(scheduleType);
			prpLscheduleMainWF.setCheckFlag("0");
			prpLscheduleMainWF.setScheduleFlag("0"); // 查勘调度没有被派出去的
			prpLscheduleMainWF.setFlag("");
			prpLscheduleMainWF.setCheckSite(registDto.getPrpLregist().getDamageAddress());
		}
		// 加入调度主表
		registDto.setPrpLscheduleMainWF(prpLscheduleMainWF);
		// 对象赋值
		// 三者车辆部分开始
		if (prpLthirdPartySerialNo != null) {
			for (int index = 1; index < prpLthirdPartySerialNo.length; index++) {
				prpLthirdParty = new PrpLthirdParty();
				prpLthirdParty.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartySerialNo[index])));
				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.setClaimNo(prpLthirdPartyClaimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if("".equals(prpLthirdPartyLicenseNo[index])||prpLthirdPartyLicenseNo[index]==null){
					prpLthirdPartyLicenseNo[index]=" ";
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
				// 加入集合
				thirdPartyList.add(prpLthirdParty);
				// 整理调度情况
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLscheduleItem.getId().setItemNo(prpLthirdParty.getId().getSerialNo());
				prpLscheduleItem.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				prpLscheduleItem.setClaimComCode(prpLthirdParty.getInsureComCode());
				// 表示是否选中
				prpLscheduleItem.setSelectSend(prpLthirdPartySelectSend[index]);
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setNextNodeNo("certa");
				// 加入调度标的集合
				// reasion:如果单独保的交强险的标的车辆，不显示标的车调度信息
				// 首先判断不是（单交强和标的车）的数据，增加定损车辆调度。
				// 交强险赔标的车
				// 在单交强险的情况並且在配置过的机构下会执行该段代码
				PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "advance_case");
				PrpDriskConfig prpDriskConfig1 = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "dealFast_case");
				if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) || (prpDriskConfig1 != null && "1".equals(prpDriskConfig1.getConfigValue()))) {
					scheduleItemDtoList.add(prpLscheduleItem);
					// 设置调度的标签显示 shcheduleItemNote
					String strTemp = "";
					if (prpLscheduleItem.getId().getItemNo() != 1)
						strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
				} else {
					if (!((isCompelRiskOnly && prpLscheduleItem.getId().getItemNo() == 1)) || isCompelRiskOnly) {
						scheduleItemDtoList.add(prpLscheduleItem);
						// 设置调度的标签显示 shcheduleItemNote
						String strTemp = "";
						if (prpLscheduleItem.getId().getItemNo() != 1)
							strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
						registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
					}// 判断是否需要增加定损车辆的标的
				}
			}
			// 报案集合中加入三者车辆
			registDto.setPrpLthirdPartyList(thirdPartyList);
			
			// 这里直接先读财产定损了， 不再另写了。。如果选择了财产定损的话
			String prpLthirdPropSelectSend = httpServletRequest.getParameter("prpLthirdPropSelectSend");
			//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 START
			//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  START
			//強制證號儲存時去掉最前面的公司碼
			for(int i = 0 ;i<thirdPartyList.size();i++){
				if(i>0 && ((PrpLthirdParty)thirdPartyList.get(i)).getIsInsurance().equals("1")){
					if(null!=thirdPartyList.get(i).getInsuranceNo() && !"".equals(thirdPartyList.get(i).getInsuranceNo())
						&& thirdPartyList.get(i).getInsuranceNo().length()>2){
					thirdPartyList.get(i).setInsuranceNo(thirdPartyList.get(i).getInsuranceNo().trim().substring(2));
						}
				}
			}
			//mantis：CLM0228，處理人員：CE046，需求單編號：新核心-第三方強制證號規則類別98 99修正  END
			//mantis：CLM0204，處理人員：CE046，需求單編號：新核心-第三方強制證號規則調整 END
			
			if (prpLthirdPropSelectSend.equals("1")) {
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLscheduleItem.getId().setItemNo(1);
				// 表示是否选中
				prpLscheduleItem.setSelectSend(prpLthirdPropSelectSend);
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setLicenseNo("財產損失");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setNextNodeNo("propc");
				scheduleItemDtoList.add(prpLscheduleItem);
				registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
			}
			// 报案集合中加入调度标的
		}
		// Reason:损失部位模块中加进零件代码、零件名称,损失部位代码与零件(项目)代码都以列表框形式展现
		/*---------------------损失部位 PrpLthirdCarLoss begin------------------------------------*/
		List<PrpLthirdCarLoss> thirdCarLossList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdCarLossRiskCode = httpServletRequest.getParameter("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdCarLossRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
				// 加入集合
				thirdCarLossList.add(prpLthirdCarLoss);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLthirdCarLossList(thirdCarLossList);
		}
		/*---------------------损失部位 PrpLthirdCarLoss-----------------------------------*/
		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp-----------------------------------*/
		List<PrpLthirdProp> thirdPropList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;
		// 从界面得到输入数组
		String prpLthirdPropRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPropRiskCode = httpServletRequest.getParameter("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdPropRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
			registDto.setPrpLthirdPropList(thirdPropList);
		}
		/*---------------------其它损失部位 PrpLthirdProp ------------------------------------*/
		/*---------------------人员伤亡跟踪 PrpLpersonTrace ------------------------------------*/
		List<PrpLpersonTrace> personTraceList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
		String prpLpersonTraceRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLpersonTracePolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLpersonTracePolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
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
		String[] PrpLpersonTraceSelectSend = httpServletRequest.getParameterValues("prpLpersonTraceSelectSend");
		String[] prpLpersonTraceDoctor = null;
		String[] prpLpersonTraceHospitalCode = null;
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
		String[] PrpLpersonTraceIdNumber = httpServletRequest.getParameterValues("prpLpersonTraceIdNumber");
		String[] PrpLpersonTraceRideSituation = httpServletRequest.getParameterValues("rideSituation");
		String[] PrpLpersonTraceLicenseno = httpServletRequest.getParameterValues("prpLpersonTraceLicenseno");

//		String[] PrpLpersonTraceBklineQueryDate = httpServletRequest.getParameterValues("prpLpersonTraceBklineQueryDate");
		String[] PrpLpersonTraceIdNumberType = httpServletRequest.getParameterValues("prpLpersonTraceIdNumberType");
		String[] PrpLpersonTraceApplicantBirthday = httpServletRequest.getParameterValues("prpLpersonTraceApplicantBirthday");
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
		if ("E".equals(strRiskType)) {
			prpLpersonTraceDoctor = httpServletRequest.getParameterValues("prpLpersonTraceDoctor");// 就診醫師
			prpLpersonTraceHospitalCode = httpServletRequest.getParameterValues("prpLpersonTraceHospitalCode");// 就诊医院代碼
		}
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLpersonTracePersonNo != null) {
			for (int index = 1; index < prpLpersonTracePersonNo.length; index++) {
				prpLpersonTrace = new PrpLpersonTrace();
				prpLpersonTrace.getId().setRegistNo(prpLpersonTraceRegistNo);
				prpLpersonTrace.setClaimNo(""); // 改为存空值
				prpLpersonTrace.setPolicyNo(prpLpersonTracePolicyNo);
				prpLpersonTrace.getId().setPersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonNo[index])));
				prpLpersonTrace.setPersonName(prpLpersonTracePersonName[index]);
				prpLpersonTrace.setPersonSex(prpLpersonTracePersonSex[index]);
				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));
				prpLpersonTrace.setIdentifyNumber(prpLpersonTraceIdentifyNumber[index]);
				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				if (prpLpersonTraceReferKind == null) {
					prpLpersonTrace.setReferKind("");
				} else {
					prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				}
				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);
				prpLpersonTrace.setHospital(CommonUtils.getValue(prpLpersonTraceHospital,index));
				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);
				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);
				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);
				if ("E".equals(strRiskType)) {
					prpLpersonTrace.setDoctor(prpLpersonTraceDoctor[index]);
					prpLpersonTrace.setHospitalCode(prpLpersonTraceHospitalCode[index]);
				}
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
				prpLpersonTrace.setIdNumber(PrpLpersonTraceIdNumber[index]);
				prpLpersonTrace.setRideSituation(PrpLpersonTraceRideSituation[index]);
				prpLpersonTrace.setLicenseno(PrpLpersonTraceLicenseno[index]);
				
//				prpLpersonTrace.setBklineQueryDate(CommonUtils.toYearToSercondDate(PrpLpersonTraceBklineQueryDate[index]));
				prpLpersonTrace.setBklineQueryDate(new Date());//存檔就會查 所以存檔寫當下時間既可
				prpLpersonTrace.setIdNumberType(PrpLpersonTraceIdNumberType[index]);
				prpLpersonTrace.setApplicantBirthday(CommonUtils.toYearToDayDate(PrpLpersonTraceApplicantBirthday[index]));
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
				// 加入集合
				personTraceList.add(prpLpersonTrace);
				// 这里直接先读人伤跟踪了， 不再另写了。。如果选择了人伤的话
				// if (PrpLpersonTraceSelectSend.equals("1")) {
				if("D".equals(strRiskType)) {//非车不生成调度标的表信息。
					prpLscheduleItem = new PrpLscheduleItem();
					prpLscheduleItem.getId().setScheduleID(scheduleId++);
					prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
					prpLscheduleItem.getId().setItemNo(index);
					// 表示是否选中
					prpLscheduleItem.setSelectSend(PrpLpersonTraceSelectSend[index]);
					// 表示没有调度成定损过
					prpLscheduleItem.setSurveyTimes(0);
					prpLscheduleItem.setSurveyType("1");
					prpLscheduleItem.setCheckSite(prpLpersonTraceHospital[index]);// 人伤的就诊医院
					prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
					prpLscheduleItem.setScheduleType("schel");
					prpLscheduleItem.setLicenseNo(prpLpersonTracePersonName[index]);
					prpLscheduleItem.setScheduleObjectID("_");
					prpLscheduleItem.setScheduleObjectName(" ");
					prpLscheduleItem.setNextNodeNo("wound");
					scheduleItemDtoList.add(prpLscheduleItem);
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
				}
				// }
			}
			// 报案集合中加入损失部位
			registDto.setPrpLpersonTraceList(personTraceList);
		}
		// 整理数据，整理定损调度的数据，如果当提交的时候。。将新的数据放入prplscheduleItem中，並保留已经调度过的数据
		if ("4".equals(registDto.getPrpLclaimStatus().getStatus())) {
			prpLscheduleMainWF = registDto.getPrpLscheduleMainWF();
			if (prpLscheduleMainWF != null) {
				PrpLscheduleMainWF prpLscheduleMainWFOld = scheduleService.findScheduleMainByConditions(" registno ='" + registDto.getPrpLregist().getRegistNo() + "' and scheduleFlag='1'");
				if (prpLscheduleMainWFOld != null) {
					registDto.setPrpLscheduleMainWF(null);
					// prpLscheduleMainWF.setScheduleFlag(prpLscheduleMainWFOld.getScheduleFlag());
					// prpLscheduleMainWF.setOperatorCode(prpLscheduleMainWFOld.getOperatorCode());
					// prpLscheduleMainWF.setScheduleObjectID(prpLscheduleMainWFOld.getScheduleObjectID());
					// prpLscheduleMainWF.setScheduleObjectName(prpLscheduleMainWFOld.getScheduleObjectName());
				}
			}
			if (scheduleItemDtoList.size() > 0) { // 本次查勘查找到有新的调度任务
				// 检查定损调度的情况，如果存在定损调度，检查是否已经调度过，如果没有调度过，按照没有调度过处理
				// 查询调度过的
				String strSql = " registno ='" + registDto.getPrpLregist().getRegistNo() + "' and surveyTimes='1'";
				// 查询数据
				List<PrpLscheduleItem> prpLscheduleItemList = (List<PrpLscheduleItem>) scheduleService.findItemByConditions(strSql);
				PrpLscheduleItem prpLscheduleItemold = null;
				List<PrpLscheduleItem> scheduleItemLastList = new ArrayList<PrpLscheduleItem>();
				if (prpLscheduleItemList == null || prpLscheduleItemList.size() < 1) {
					registDto.setPrpLscheduleItemList(scheduleItemDtoList);
				} else {
					// 检查整理好的数据中，是否已经有已经调度过的数据
					// scheduleItemDtoList 是指原来从调度已经调度过的数据，无论怎么样，都是不能被删除的。
					// 只要检查 scheduleItemDtoList中存在
					// prpLscheduleItemList中没有的，就增加prpLscheduleItemList好了。
					boolean blnotFind = true;
					for (int i = 0; i < scheduleItemDtoList.size(); i++) {
						prpLscheduleItem = (PrpLscheduleItem) scheduleItemDtoList.get(i);
						// 原则，相同的，以原来的数据为准，没有的已後来的为准
						blnotFind = true;
						for (int j = 0; j < prpLscheduleItemList.size(); j++) {
							prpLscheduleItemold = prpLscheduleItemList.get(j);
							if (prpLscheduleItemold.getNextNodeNo().equals(prpLscheduleItem.getNextNodeNo())) {
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
					registDto.setPrpLscheduleItemList(scheduleItemLastList);
				}
			}
		}
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLregistExtRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null) {
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLregistExtList(prpLregistExtList);
		}
		/*---------------------驾驶员prpLdriver------------------------------------*/
		List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = null;
		// 从界面得到输入数组
		String prpLdriverRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLdriverClaimNo = httpServletRequest.getParameter("prpLregistClaimNo");
		String prpLdriverRiskCode = httpServletRequest.getParameter("prpLregistRiskCode");
		String prpLdriverPolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLdriverRiskCode = ConstantCodes.RISKCODE_DAZ;
			prpLdriverPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
		String[] prpLdriverSerialNo = httpServletRequest.getParameterValues("prpLdriverSerialNo");
		String[] prpLdriverLicenseNo = httpServletRequest.getParameterValues("prpLdriverLicenseNo");
		// 差异化begin--------------------add by liuwei-----2013-5-13------
		String[] prpLdriverIsMarried = httpServletRequest.getParameterValues("prpLdriverIsMarried");
		String[] prpLdriverBirthday = httpServletRequest.getParameterValues("prpLdriverBirthday");
		String[] prpLdriverIdentifyNumber = httpServletRequest.getParameterValues("prpLdriverIdentifyNumber");// 身份证号码
		String[] prpLdriverMobilePhone = httpServletRequest.getParameterValues("prpLdriverMobilePhone");
		String[] prpLdriverDriverIdentity = httpServletRequest.getParameterValues("prpLdriverDriverIdentity");
		String[] prpLdriverDriverDistrict = httpServletRequest.getParameterValues("prpLdriverDriverDistrict");
		// 差异化end--------------------
		String[] prpLdriverDrivingLicenseNo = httpServletRequest.getParameterValues("prpLdriverDrivingLicenseNo");// 驾照号码
		String[] prpLdriverDriverName = httpServletRequest.getParameterValues("prpLdriverDriverName");
		String[] prpLdriverDriverSex = httpServletRequest.getParameterValues("driverSex");
		// 根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息
		String[] prpLdriverDriverPhone = httpServletRequest.getParameterValues("prpLdriverDriverPhone");
		String[] prpLdriverDrivingCarType = httpServletRequest.getParameterValues("drivingCarType");
		String[] prpLprpLdriverApanageCode = httpServletRequest.getParameterValues("prpLdriverApanageCode");
		String[] prpLprpLdriverApanage = httpServletRequest.getParameterValues("prpLdriverApanage");
		// 对象赋值
		// 驾驶员部分开始
		if (prpLdriverSerialNo != null) {
			for (int index = 1; index < prpLdriverSerialNo.length; index++) {
				prpLdriver = new PrpLdriver();
				prpLdriver.getId().setRegistNo(prpLdriverRegistNo);
				prpLdriver.setClaimNo(prpLdriverClaimNo);
				prpLdriver.setRiskCode(prpLdriverRiskCode);
				prpLdriver.setPolicyNo(prpLdriverPolicyNo);
				prpLdriver.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLdriverSerialNo[index])));
				prpLdriver.setLicenseNo(prpLdriverLicenseNo[index]);
				// 差异化begin--------------add by liuwei-----------
				prpLdriver.setIsMarried(prpLdriverIsMarried[index]);
				prpLdriver.setBirthday(CommonUtils.toYearToDayDate(prpLdriverBirthday[index]));
				prpLdriver.setIdentifyNumber(prpLdriverIdentifyNumber[index]);
				prpLdriver.setMobilePhone(prpLdriverMobilePhone[index]);
				prpLdriver.setDriverIdentity(prpLdriverDriverIdentity[index]);
				prpLdriver.setDriverDistrict(prpLdriverDriverDistrict[index]);
				// 差异化end----------------------------------------
				prpLdriver.setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);
				prpLdriver.setDriverName(prpLdriverDriverName[index]);
				prpLdriver.setDriverSex(prpLdriverDriverSex[index]);
				prpLdriver.setDrivingCarType(prpLdriverDrivingCarType[index]);
				prpLdriver.setDriverApanageCode(prpLprpLdriverApanageCode[index]);
				prpLdriver.setDriverApanage(prpLprpLdriverApanage[index]);
				// /***根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息****
				prpLdriver.setDriverSeaRoute(prpLdriverDriverPhone[index]);
				// 加入集合
				driverList.add(prpLdriver);
			}
			// 报案集合中加入驾驭员
			registDto.setPrpLdriverList(driverList);

		}
		// 原因：增加联系人信息
		List<PrpLrelatePerson> relatePersonList = new ArrayList<PrpLrelatePerson>();
		String personRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String[] prpSeriaNo = httpServletRequest.getParameterValues("prpLrelatePersonSeriaNo");
		String strPolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			strPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
		String[] prpPersonName = httpServletRequest.getParameterValues("prpLrelatePersonPersonName");
		String[] prpPhoneNumber = httpServletRequest.getParameterValues("prpLrelatePersonPhoneNumber");
		String[] prpMobile = httpServletRequest.getParameterValues("prpLrelatePersonMobile");
		String[] prpRemark = httpServletRequest.getParameterValues("prpLrelatePersonRemark");
		PrpLrelatePerson prpLrelatePerson = null;
		if (prpSeriaNo != null) {
			for (int i = 1; i < prpSeriaNo.length; i++) {
				prpLrelatePerson = new PrpLrelatePerson();
				prpLrelatePerson.getId().setRegistNo(personRegistNo);
				prpLrelatePerson.getId().setSerialNo(new BigDecimal(prpSeriaNo[i]));
				prpLrelatePerson.getId().setPersonType("Link");
				prpLrelatePerson.setPersonName(prpPersonName[i]);
				prpLrelatePerson.setPhoneNumber(prpPhoneNumber[i]);
				prpLrelatePerson.setMobile(prpMobile[i]);
				prpLrelatePerson.setRemark(prpRemark[i]);
				prpLrelatePerson.setPolicyNo(strPolicyNo);
				prpLrelatePerson.setPersonCode("");
				prpLrelatePerson.setFlag("");
				// 加入集合中
				relatePersonList.add(prpLrelatePerson);
			}
			// 报案集合中联系人信息
			registDto.setPrpLrelatePersonList(relatePersonList);
		}
		// 处理报案登记,当"第三者亡人数" "第三者伤人数" "车上人员亡人数"
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
		String lregistValue1 = httpServletRequest.getParameter("prpLregistValue1");
		String lregistValue2 = httpServletRequest.getParameter("prpLregistValue2");
			if ("D".equals(strRiskType)){
			PrpLext prpLext = new PrpLext();
			String certiNo = (String) httpServletRequest.getAttribute("registNo");
			prpLext.getId().setCertiNo(certiNo);
			prpLext.getId().setCertiType("01");
			prpLext.setPersonDeathB(Integer.parseInt(personDeathB));
			prpLext.setPersonInjureB(Integer.parseInt(personInjureB));
			prpLext.setPersonDeathD1(Integer.parseInt(personDeathD1));
			prpLext.setPersonInjureD1(Integer.parseInt(personInjureD1));
			prpLext.setValue1(lregistValue1);
			prpLext.setValue2(lregistValue2);
			registDto.setPrpLext(prpLext);
		}
		// 整理调度用的标的信息,去掉最後的一个"/"
		if (registDto.getPrpLregist().getScheduleItemNote().length() > 1) {
			String strTemp = registDto.getPrpLregist().getScheduleItemNote();
			strTemp = strTemp.substring(0, strTemp.length() - 1);
			registDto.getPrpLregist().setScheduleItemNote(strTemp);
		}
		// 收集callcenter补充信息
		String callCenterInfo = httpServletRequest.getParameter("callCenterInfo");
		ArrayList<PrpLregistText> prpLregistTextList2 = new ArrayList<PrpLregistText>();
		String[] rules2 = StringUtils.split(callCenterInfo, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules2.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules2[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("5");// 5表示95519补充报案信息
			prpLregistTextList2.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList2(prpLregistTextList2);
		return registDto;

	}

	/**
	 * --CLM0259、CLM9001 externalToDto
	 * (外部存入)保存报案时报案页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param httpServletRequest
	 * @return registDto 报案数据传输数据结构
	 * @throws Exception
	 */
	public RegistDto externalToDto(HttpServletRequest httpServletRequest,ClaimExternalSourceVo claimExternalSourceVo,PrpCmain prpCmainIn,PrpLregist prpLregistIn) throws Exception {
		//多元收件資料
		prpLregistIn.setMultiRecepNo(claimExternalSourceVo.getMultiRecepNo());
		prpLregistIn.setChannelSource(claimExternalSourceVo.getChannelSource());
		prpLregistIn.setMemo(claimExternalSourceVo.getMemo());
		// 继承对regist,registText表的赋值
		RegistDto registDto = super.externalToDto(httpServletRequest,claimExternalSourceVo,prpCmainIn,prpLregistIn);
		/* 强三 ---关联表的存储----------------- */
		// 目前只存储了强三的单号
		Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
		Prplregistrpolicy prpLRegistRPolicy_comp = new Prplregistrpolicy();
		String registNo = (String) httpServletRequest.getAttribute("registNo");
		String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
		String quaryPolicyNo = (String) httpServletRequest.getAttribute("quaryPolicyNo");
		String policyNo = (String) httpServletRequest.getAttribute("prpLregistPolicyNo");
		httpServletRequest.setAttribute("prpLregistRiskCode",prpLregistIn.getRiskCode());//下面會用到，為盡量保持與原程式同貌，用塞入attribute方式
		String prpLregistRPPolicyRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		// 交强险迁移 报案类型 0 ：商业险单独报案 1：交强险单独报案 2：商业、交强险关联报案
		String registType = (String)httpServletRequest.getAttribute("registType");
		boolean isCompelRiskOnly = false; // 判断单独交强报案的情况，默认都是否。
		List<Prplregistrpolicy> prpLRegistRPolicyList = new ArrayList<Prplregistrpolicy>();
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		String flowId = prpLregistrpolicyService.findSwfLogId(registNo);
		if (DataUtils.emptyToNull(policyNo) != null && DataUtils.emptyToNull(mainPolicyNo) != null && "2".equals(DataUtils.emptyToNull(registType))) {
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(policyNo)) {
				prpLRegistRPolicy.setRegistFlag("1");
			} else {
				prpLRegistRPolicy.setRegistFlag("0");
			}
			prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
			prpLRegistRPolicy.getId().setRegistNo(registNo);
			prpLRegistRPolicy.getId().setPolicyNo(policyNo);
			prpLRegistRPolicy.setPolicyType("1");
			prpLRegistRPolicy.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(policyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(mainPolicyNo)) {
				prpLRegistRPolicy_comp.setRegistFlag("1");
			} else {
				prpLRegistRPolicy_comp.setRegistFlag("0");
			}
			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
			prpLRegistRPolicy_comp.setRiskCode(compelRiskCode);
			
			prpLRegistRPolicy_comp.getId().setRegistNo(registNo);
			prpLRegistRPolicy_comp.getId().setPolicyNo(mainPolicyNo);
			prpLRegistRPolicy_comp.setPolicyType("3");
			prpLRegistRPolicy_comp.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(mainPolicyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy_comp.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy_comp.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy_comp);
			// 以上为关联表增加两条的情况
		} else {
			// 交强险迁移 2013-03-15 chenjie
			if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
				String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
				prpLRegistRPolicy.getId().setPolicyNo(org.apache.commons.lang.StringUtils.isNotEmpty(mainPolicyNo)?mainPolicyNo:policyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				prpLRegistRPolicy.setPolicyType("3");
				prpLRegistRPolicy.setRiskCode(compelRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
				isCompelRiskOnly = true;
			} else {
				// 存储费关联;
				prpLRegistRPolicy.getId().setPolicyNo(policyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				String strConfigCode = codeService.translateRiskCodetoConfigCode(prpLregistRPPolicyRiskCode);
				if ("RISKCODE_DAZ".equals(strConfigCode)) {
					prpLRegistRPolicy.setPolicyType("3");
				} else {
					prpLRegistRPolicy.setPolicyType("1");
				}
				// reason:判断出单独交强的情况
				String strOnlyDAZ = codeService.translateRiskCodetoConfigCode(registDto.getPrpLregist().getRiskCode());
				if ("RISKCODE_DAZ".equals(strOnlyDAZ)) {
					// 这里可以判断出是单独交强险报的案
					isCompelRiskOnly = true;
				}
				// 单独交强
				prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
			}
			for(PrpLclaim temp : prpLclaimList){
				if(prpLRegistRPolicy.getId().getPolicyNo().equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
		}
		registDto.setPrpLRegistRPolicyList(prpLRegistRPolicyList);
		/* 强三 ---关联表的存储 */
		List<PrpLthirdParty> thirdPartyList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;

		// 从界面得到输入数组
		String prpLthirdPartyRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPartyRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		// 交强险迁移 2013-03-15 chenjie
		if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
			prpLthirdPartyRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String prpLthirdPartyClaimNo = httpServletRequest.getParameter("prpLregistClaimNo");
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLregistClauseType");
//		String[] prpLthirdPartySerialNo = httpServletRequest.getParameterValues("prpLthirdPartySerialNo");
//		String[] prpLthirdPartyLicenseNo = httpServletRequest.getParameterValues("prpLthirdPartyLicenseNo");
//		String[] prpLthirdPartyLicenseColorCode = httpServletRequest.getParameterValues("licenseColorCode");
//		String[] prpLthirdPartyCarKindCode = httpServletRequest.getParameterValues("carKindCode");
//		String[] prpLthirdPartyInsureCarFlag = httpServletRequest.getParameterValues("insureCarFlag");
//		String[] prpLthirdPartyEngineNo = httpServletRequest.getParameterValues("prpLthirdPartyEngineNo");
//		String[] prpLthirdPartyFrameNo = httpServletRequest.getParameterValues("prpLthirdPartyFrameNo");
//		String[] prpLthirdPartyModelCode = httpServletRequest.getParameterValues("prpLthirdPartyModelCode");
//		String[] prpLthirdPartyBrandName = httpServletRequest.getParameterValues("prpLthirdPartyBrandName");
//		String[] prpLthirdPartyRunDistance = httpServletRequest.getParameterValues("prpLthirdPartyRunDistance");
//		String[] prpLthirdPartyUseYears = httpServletRequest.getParameterValues("prpLthirdPartyUseYears");
//		String[] prpLthirdPartyDutyPercent = httpServletRequest.getParameterValues("prpLthirdPartyDutyPercent");
//		String[] prpLthirdPartyInsuredFlag = httpServletRequest.getParameterValues("insuredFlag");
//		String[] prpLthirdPartyInsureComCode = httpServletRequest.getParameterValues("prpLthirdPartyInsureComCode");
//		String[] prpLthirdPartyInsureComName = httpServletRequest.getParameterValues("prpLthirdPartyInsureComName");
//		String[] prpLthirdPartyVINNo = httpServletRequest.getParameterValues("prpLthirdPartyVINNo");
//		String[] prpLthirdPartySelectSend = httpServletRequest.getParameterValues("prpLthirdPartySelectSend");
		// 差异化begin---add by liuwei----2013-5-12----------
//		String[] prpLthirdPartyGarageHeadName = httpServletRequest.getParameterValues("prpLthirdPartyGarageHeadName");
//		String[] prpLthirdPartyRelationship = httpServletRequest.getParameterValues("prpLthirdPartyRelationship");
//		String[] prpLthirdPartyDrivingAddress = httpServletRequest.getParameterValues("prpLthirdPartyDrivingAddress");
//		String[] prpLthirdPartyCarryingUnit = httpServletRequest.getParameterValues("prpLthirdPartyCarryingUnit");
//		String[] prpLthirdPartyInsuranceNo = httpServletRequest.getParameterValues("prpLthirdPartyInsuranceNo");
//		String[] prpLthirdPartyIsInsurance = httpServletRequest.getParameterValues("prpLthirdPartyIsInsurance");
//		String[] prpLthirdPartyCarryingNumber = httpServletRequest.getParameterValues("prpLthirdPartyCarryingNumber");
//		String[] prpLthirdPartyCarsOwners = httpServletRequest.getParameterValues("prpLthirdPartyCarsOwners");
//		String[] prpLthirdPartyInsuredIdentity = httpServletRequest.getParameterValues("prpLthirdPartyInsuredIdentity");
		// 差异化end---------------------
		// 取赔案类型的选择
		/*----------包括查勘调度，定损调度 PrpLscheduleMainWF ,PrpLscheduleItem --------------*/
		List<PrpLscheduleItem> scheduleItemDtoList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		int scheduleId = 1; // 调度号的id
		PrpLscheduleMainWF prpLscheduleMainWF = null;
		// 通过代码对照表转换riskcode
		String riskCode = registDto.getPrpLregist().getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		if ("D".equals(strRiskType)) {
			PrpLregist prpLregist = registDto.getPrpLregist();
			prpLscheduleMainWF = new PrpLscheduleMainWF();
			prpLscheduleMainWF.getId().setScheduleID(1);
			prpLscheduleMainWF.getId().setRegistNo(prpLregist.getRegistNo());
			prpLscheduleMainWF.setSurveyNo(0);
			prpLscheduleMainWF.setClaimComCode(prpLregist.getComCode());
			prpLscheduleMainWF.setRiskCode(prpLregist.getRiskCode());
			prpLscheduleMainWF.setPolicyNo(prpLregist.getPolicyNo());
			prpLscheduleMainWF.setOperatorCode("");
			prpLscheduleMainWF.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLscheduleMainWF.setInputHour(DateTime.current().getHour());
			prpLscheduleMainWF.setScheduleObjectID("_");
			prpLscheduleMainWF.setScheduleObjectName(" ");
			String scheduleType = (String)httpServletRequest.getAttribute("scheduleType");
			prpLscheduleMainWF.setScheduleType(scheduleType);
			prpLscheduleMainWF.setCheckFlag("0");
			prpLscheduleMainWF.setScheduleFlag("0"); // 查勘调度没有被派出去的
			prpLscheduleMainWF.setFlag("");
			prpLscheduleMainWF.setCheckSite(registDto.getPrpLregist().getDamageAddress());
		}
		// 加入调度主表
		registDto.setPrpLscheduleMainWF(prpLscheduleMainWF);
		
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
		//另外加工塞回去
		//標的車
		prpLthirdParty = (PrpLthirdParty) httpServletRequest.getAttribute("prpLthirdParty");
		
		if(null!=claimExternalSourceVo  && 
				(null==claimExternalSourceVo.getThirdPartyList() || claimExternalSourceVo.getThirdPartyList().size()==0)){
			
			List<ThirdParty> thirdPartyList_ = new ArrayList<ThirdParty>();
			for(int i = 1;i<=prpLthirdParty.getThirdPartyList().size();i++){
				ThirdParty thirdParty = new ThirdParty();
				
				PrpLthirdParty prpLthirdParty_ = prpLthirdParty.getThirdPartyList().get(i-1);
				
				thirdParty.setLicenseNo(prpLthirdParty_.getLicenseNo());
				thirdParty.setCarKindCode(prpLthirdParty_.getCarKindCode());
				thirdParty.setInsureComCode(prpLthirdParty_.getInsureComCode());
				thirdPartyList_.add(thirdParty);
				
			}
			claimExternalSourceVo.setThirdPartyList(thirdPartyList_);
			
		}
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
		
		// 对象赋值
		// 三者车辆部分开始
		if(null!=claimExternalSourceVo && null!=claimExternalSourceVo.getThirdPartyList()
				&& claimExternalSourceVo.getThirdPartyList().size()>0
//				&& 1==2 //暫時擋住
				){
			for (int index = 1; index <= claimExternalSourceVo.getThirdPartyList().size(); index++) {
				prpLthirdParty = new PrpLthirdParty();
				ThirdParty thirdParty = claimExternalSourceVo.getThirdPartyList().get(index-1);
				prpLthirdParty.getId().setSerialNo(index);//prpLthirdPartySerialNo[index]
				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.setClaimNo(prpLthirdPartyClaimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if("".equals(thirdParty.getLicenseNo())||thirdParty.getLicenseNo()==null){
					thirdParty.setLicenseNo(" ");
				}
				prpLthirdParty.setLicenseNo(thirdParty.getLicenseNo());//牌照號碼(D)
//				prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode[index]);//號牌底色
				prpLthirdParty.setCarKindCode(thirdParty.getCarKindCode());//車輛種類(D)
				prpLthirdParty.setInsureCarFlag(index==1?"1":"0");//標的車||第三方車輛||??(D)
//				prpLthirdParty.setEngineNo(thirdParty.getprpLthirdPartyEngineNo[index]);//引擎號碼
//				prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo[index]);//車身號碼
//				prpLthirdParty.setBrandName(prpLthirdPartyBrandName[index]);//廠牌型號
//				prpLthirdParty.setModelCode(prpLthirdPartyModelCode[index]);//廠牌型號 代碼
//				prpLthirdParty.setRunDistance(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyRunDistance[index])));//里程數
//				prpLthirdParty.setUseYears(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartyUseYears[index])));//使用年限
				prpLthirdParty.setDutyPercent(Double.parseDouble(DataUtils.nullToZero("0.0")));//責任比例:
//				prpLthirdParty.setInsuredFlag("1");//畫面上抓不到 判定是第一筆標的車 第二筆以後都是"+"出來的三方車
				
				prpLthirdParty.setInsureComCode(thirdParty.getInsureComCode());//承保公司代號(D)
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START 
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(thirdParty.getInsureComCode());
				prpLthirdParty.setInsureComName(prpDcompany.getComCName());//承保公司名稱
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
				
//				prpLthirdParty.setVINNo(prpLthirdPartyVINNo[index]);//????這啥
//				prpLthirdParty.setGarageHeadName(prpLthirdPartyGarageHeadName[index]);//修車廠負責人姓名
				// 由於标的车和三者车的属性现在不完全一样了，故作如下判断
//				if (prpLthirdParty.getInsuredFlag().equals("1")) {
				if(index == 1){///畫面上抓不到 判定是第一筆標的車 第二筆以後都是"+"出來的三方車 ，參考畫面似乎也是這樣的邏輯
//					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);//財車駕駛地址
					prpLthirdParty.setRelationship(claimExternalSourceVo.getRelationship());//(D)
				} else {
					prpLthirdParty.setCarryingUnit(thirdParty.getCarryingUnit());//承載單位(D)
					prpLthirdParty.setInsuranceNo(thirdParty.getInsuranceNo());//強制保險證編號(D)
					prpLthirdParty.setIsInsurance(thirdParty.getIsInsurance());//是否有保強制險(D)
					prpLthirdParty.setCarryingNumber(Long.parseLong(DataUtils.nullToZero(thirdParty.getCarringNumber())));//乘載數量(D)
					prpLthirdParty.setInsuredIdentity(thirdParty.getInsuredIdentity());//被保險人身分(D)
//					prpLthirdParty.setCarsOwners(prpLthirdPartyCarsOwners[index]);//財車車主
//					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);//財車駕駛地址
				}
				// 加入集合
				thirdPartyList.add(prpLthirdParty);
				// 整理调度情况
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLscheduleItem.getId().setItemNo(prpLthirdParty.getId().getSerialNo());
				prpLscheduleItem.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				prpLscheduleItem.setClaimComCode(prpLthirdParty.getInsureComCode());
				// 表示是否选中
				prpLscheduleItem.setSelectSend(index==1?"0":"");//看畫面判斷的
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setNextNodeNo("certa");
				// 加入调度标的集合
				// reasion:如果单独保的交强险的标的车辆，不显示标的车调度信息
				// 首先判断不是（单交强和标的车）的数据，增加定损车辆调度。
				// 交强险赔标的车
				// 在单交强险的情况並且在配置过的机构下会执行该段代码
				PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "advance_case");
				PrpDriskConfig prpDriskConfig1 = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "dealFast_case");
				if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) || (prpDriskConfig1 != null && "1".equals(prpDriskConfig1.getConfigValue()))) {
					scheduleItemDtoList.add(prpLscheduleItem);
					// 设置调度的标签显示 shcheduleItemNote
					String strTemp = "";
					if (prpLscheduleItem.getId().getItemNo() != 1)
						strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
				} else {
					if (!((isCompelRiskOnly && prpLscheduleItem.getId().getItemNo() == 1)) || isCompelRiskOnly) {
						scheduleItemDtoList.add(prpLscheduleItem);
						// 设置调度的标签显示 shcheduleItemNote
						String strTemp = "";
						if (prpLscheduleItem.getId().getItemNo() != 1)
							strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
						registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
					}// 判断是否需要增加定损车辆的标的
				}
			}
			// 报案集合中加入三者车辆
			registDto.setPrpLthirdPartyList(thirdPartyList);
			
			// 这里直接先读财产定损了， 不再另写了。。如果选择了财产定损的话
			String prpLthirdPropSelectSend = prpLscheduleItem.getSelectSend();//httpServletRequest.getParameter("prpLthirdPropSelectSend");
			//強制證號儲存時去掉最前面的公司碼
			for(int i = 0 ;i<thirdPartyList.size();i++){
				if(i>0 && ((PrpLthirdParty)thirdPartyList.get(i)).getIsInsurance().equals("1")){
					if(null!=thirdPartyList.get(i).getInsuranceNo() && !"".equals(thirdPartyList.get(i).getInsuranceNo())
						&& thirdPartyList.get(i).getInsuranceNo().length()>2){
					thirdPartyList.get(i).setInsuranceNo(thirdPartyList.get(i).getInsuranceNo().trim().substring(2));
						}
				}
			}
			
			if (prpLthirdPropSelectSend.equals("1")) {
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLscheduleItem.getId().setItemNo(1);
				// 表示是否选中
				prpLscheduleItem.setSelectSend(prpLthirdPropSelectSend);
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setLicenseNo("財產損失");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setNextNodeNo("propc");
				scheduleItemDtoList.add(prpLscheduleItem);
				registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
			}
			// 报案集合中加入调度标的
		}
		// Reason:损失部位模块中加进零件代码、零件名称,损失部位代码与零件(项目)代码都以列表框形式展现
		/*---------------------损失部位 PrpLthirdCarLoss begin------------------------------------*/
		List<PrpLthirdCarLoss> thirdCarLossList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdCarLossRiskCode = (String)httpServletRequest.getAttribute("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdCarLossRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
				// 加入集合
				thirdCarLossList.add(prpLthirdCarLoss);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLthirdCarLossList(thirdCarLossList);
		}
		/*---------------------损失部位 PrpLthirdCarLoss-----------------------------------*/
		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp-----------------------------------*/
		List<PrpLthirdProp> thirdPropList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;
		// 从界面得到输入数组
		String prpLthirdPropRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPropRiskCode = (String)httpServletRequest.getAttribute("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdPropRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
			registDto.setPrpLthirdPropList(thirdPropList);
		}
		/*---------------------其它损失部位 PrpLthirdProp ------------------------------------*/
		/*---------------------人员伤亡跟踪 PrpLpersonTrace ------------------------------------*/
		List<PrpLpersonTrace> personTraceList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
		String prpLpersonTraceRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLpersonTracePolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLpersonTracePolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
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
		String[] PrpLpersonTraceSelectSend = httpServletRequest.getParameterValues("prpLpersonTraceSelectSend");////觀察jsp都是0
		String[] prpLpersonTraceDoctor = null;
		String[] prpLpersonTraceHospitalCode = null;
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
		String[] PrpLpersonTraceIdNumber = httpServletRequest.getParameterValues("prpLpersonTraceIdNumber");
		String[] PrpLpersonTraceRideSituation = httpServletRequest.getParameterValues("rideSituation");
		String[] PrpLpersonTraceLicenseno = httpServletRequest.getParameterValues("prpLpersonTraceLicenseno");

//		String[] PrpLpersonTraceBklineQueryDate = httpServletRequest.getParameterValues("prpLpersonTraceBklineQueryDate");
		String[] PrpLpersonTraceIdNumberType = httpServletRequest.getParameterValues("prpLpersonTraceIdNumberType");
		String[] PrpLpersonTraceApplicantBirthday = httpServletRequest.getParameterValues("prpLpersonTraceApplicantBirthday");
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
		if ("E".equals(strRiskType)) {
			prpLpersonTraceDoctor = httpServletRequest.getParameterValues("prpLpersonTraceDoctor");// 就診醫師
			prpLpersonTraceHospitalCode = httpServletRequest.getParameterValues("prpLpersonTraceHospitalCode");// 就诊医院代碼
		}
		String driverLicenseno="";
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (null!=claimExternalSourceVo && null!=claimExternalSourceVo.getPersonTraceList()
				&& claimExternalSourceVo.getPersonTraceList().size()>0) {
			for (int index = 1; index <= claimExternalSourceVo.getPersonTraceList().size(); index++) {
				PersonTrace personTrace = claimExternalSourceVo.getPersonTraceList().get(index-1);
				
//				if(B01必田一組){
//					
//				}
				//人傷跟蹤訊息 
				prpLpersonTrace = new PrpLpersonTrace();
				prpLpersonTrace.getId().setRegistNo(prpLpersonTraceRegistNo);
				prpLpersonTrace.setClaimNo(""); // 改为存空值
				prpLpersonTrace.setPolicyNo(prpLpersonTracePolicyNo);
				
				prpLpersonTrace.getId().setPersonNo(index);//1開始
				prpLpersonTrace.setPersonName(personTrace.getPersonName());//受害人姓名
				prpLpersonTrace.setPersonSex(personTrace.getPersonSex());//受害人性別
//				prpLpersonTrace.setIdentifyNumber(personTrace.getIdentifyNumber());
//				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
//				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
//				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				if (prpLpersonTraceReferKind == null) {//涉及險種(似乎沒配到)
					prpLpersonTrace.setReferKind("");
				} else {
					prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				}
//				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);//受傷部位
//				prpLpersonTrace.setHospital(CommonUtils.getValue(prpLpersonTraceHospital,index));//就診醫院
//				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);//是否自行就醫
//				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);//傷情描述
//				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
//				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);
//				if ("E".equals(strRiskType)) {
//					prpLpersonTrace.setDoctor(prpLpersonTraceDoctor[index]);
//					prpLpersonTrace.setHospitalCode(prpLpersonTraceHospitalCode[index]);
//				}
				prpLpersonTrace.setIdNumber(personTrace.getIdentifyNumber());//受害人身分證號
				prpLpersonTrace.setRideSituation(personTrace.getRideSituation());//受害人乘坐狀況：
				prpLpersonTrace.setLicenseno(personTrace.getLicenseNo());//受害人乘坐車輛牌照號碼
				if(index==1){
					driverLicenseno = personTrace.getLicenseNo();
				}
//				prpLpersonTrace.setBklineQueryDate(CommonUtils.toYearToSercondDate(PrpLpersonTraceBklineQueryDate[index]));
				prpLpersonTrace.setBklineQueryDate(new Date());//存檔就會查 所以存檔寫當下時間既可
				prpLpersonTrace.setIdNumberType(personTrace.getIdNumberType());//受害人身分證號類別
				prpLpersonTrace.setApplicantBirthday(CommonUtils.toYearToDayDate(personTrace.getApplicantBirthday()));
				prpLpersonTrace.setPersonAge(com.sinosoft.claim.common.util.DataUtils.calculateAgeWithDateUtils(personTrace.getApplicantBirthday()));
//				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));需用ApplicantBirthday算年齡
				
				// 加入集合
				personTraceList.add(prpLpersonTrace);
				
				// 这里直接先读人伤跟踪了， 不再另写了。。如果选择了人伤的话
				// if (PrpLpersonTraceSelectSend.equals("1")) {
				if("D".equals(strRiskType)) {//非车不生成调度标的表信息。
					prpLscheduleItem = new PrpLscheduleItem();
					prpLscheduleItem.getId().setScheduleID(scheduleId++);
					prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
					prpLscheduleItem.getId().setItemNo(index);
					// 表示是否选中
					prpLscheduleItem.setSelectSend("0");//PrpLpersonTraceSelectSend[index]);//觀察JSP都是0
					// 表示没有调度成定损过
					prpLscheduleItem.setSurveyTimes(0);
					prpLscheduleItem.setSurveyType("1");
					prpLscheduleItem.setCheckSite("");//prpLpersonTraceHospital[index]);//WS先給空// 人伤的就诊医院
					prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
					prpLscheduleItem.setScheduleType("schel");
					prpLscheduleItem.setLicenseNo(prpLpersonTrace.getPersonName());//不懂為何原本給name?? prpLpersonTracePersonName[index]);
					prpLscheduleItem.setScheduleObjectID("_");
					prpLscheduleItem.setScheduleObjectName(" ");
					prpLscheduleItem.setNextNodeNo("wound");
					scheduleItemDtoList.add(prpLscheduleItem);
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
				}
				// }
			}
			// 报案集合中加入损失部位
			registDto.setPrpLpersonTraceList(personTraceList);
		}
		// 整理数据，整理定损调度的数据，如果当提交的时候。。将新的数据放入prplscheduleItem中，並保留已经调度过的数据
		if ("4".equals(registDto.getPrpLclaimStatus().getStatus())) {
			prpLscheduleMainWF = registDto.getPrpLscheduleMainWF();
			if (prpLscheduleMainWF != null) {
				PrpLscheduleMainWF prpLscheduleMainWFOld = scheduleService.findScheduleMainByConditions(" registno ='" + registDto.getPrpLregist().getRegistNo() + "' and scheduleFlag='1'");
				if (prpLscheduleMainWFOld != null) {
					registDto.setPrpLscheduleMainWF(null);
					// prpLscheduleMainWF.setScheduleFlag(prpLscheduleMainWFOld.getScheduleFlag());
					// prpLscheduleMainWF.setOperatorCode(prpLscheduleMainWFOld.getOperatorCode());
					// prpLscheduleMainWF.setScheduleObjectID(prpLscheduleMainWFOld.getScheduleObjectID());
					// prpLscheduleMainWF.setScheduleObjectName(prpLscheduleMainWFOld.getScheduleObjectName());
				}
			}
			if (scheduleItemDtoList.size() > 0) { // 本次查勘查找到有新的调度任务
				// 检查定损调度的情况，如果存在定损调度，检查是否已经调度过，如果没有调度过，按照没有调度过处理
				// 查询调度过的
				String strSql = " registno ='" + registDto.getPrpLregist().getRegistNo() + "' and surveyTimes='1'";
				// 查询数据
				List<PrpLscheduleItem> prpLscheduleItemList = (List<PrpLscheduleItem>) scheduleService.findItemByConditions(strSql);
				PrpLscheduleItem prpLscheduleItemold = null;
				List<PrpLscheduleItem> scheduleItemLastList = new ArrayList<PrpLscheduleItem>();
				if (prpLscheduleItemList == null || prpLscheduleItemList.size() < 1) {
					registDto.setPrpLscheduleItemList(scheduleItemDtoList);
				} else {
					// 检查整理好的数据中，是否已经有已经调度过的数据
					// scheduleItemDtoList 是指原来从调度已经调度过的数据，无论怎么样，都是不能被删除的。
					// 只要检查 scheduleItemDtoList中存在
					// prpLscheduleItemList中没有的，就增加prpLscheduleItemList好了。
					boolean blnotFind = true;
					for (int i = 0; i < scheduleItemDtoList.size(); i++) {
						prpLscheduleItem = (PrpLscheduleItem) scheduleItemDtoList.get(i);
						// 原则，相同的，以原来的数据为准，没有的已後来的为准
						blnotFind = true;
						for (int j = 0; j < prpLscheduleItemList.size(); j++) {
							prpLscheduleItemold = prpLscheduleItemList.get(j);
							if (prpLscheduleItemold.getNextNodeNo().equals(prpLscheduleItem.getNextNodeNo())) {
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
					registDto.setPrpLscheduleItemList(scheduleItemLastList);
				}
			}
		}
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLregistExtRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null) {
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLregistExtList(prpLregistExtList);
		}
		/*---------------------驾驶员prpLdriver------------------------------------*/
		List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = null;
		// 从界面得到输入数组
		String prpLdriverRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLdriverClaimNo = (String) httpServletRequest.getAttribute("prpLregistClaimNo");
		String prpLdriverRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		String prpLdriverPolicyNo = (String) httpServletRequest.getAttribute("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLdriverRiskCode = ConstantCodes.RISKCODE_DAZ;
			prpLdriverPolicyNo = (String)httpServletRequest.getAttribute("mainPolicyNo");
		}
//		String[] prpLdriverSerialNo = httpServletRequest.getParameterValues("prpLdriverSerialNo");
//		String[] prpLdriverLicenseNo = httpServletRequest.getParameterValues("prpLdriverLicenseNo");
		// 差异化begin--------------------add by liuwei-----2013-5-13------
//		String[] prpLdriverIsMarried = httpServletRequest.getParameterValues("prpLdriverIsMarried");
//		String[] prpLdriverBirthday = httpServletRequest.getParameterValues("prpLdriverBirthday");
//		String[] prpLdriverIdentifyNumber = httpServletRequest.getParameterValues("prpLdriverIdentifyNumber");// 身份证号码
//		String[] prpLdriverMobilePhone = httpServletRequest.getParameterValues("prpLdriverMobilePhone");
//		String[] prpLdriverDriverIdentity = httpServletRequest.getParameterValues("prpLdriverDriverIdentity");
//		String[] prpLdriverDriverDistrict = httpServletRequest.getParameterValues("prpLdriverDriverDistrict");
		// 差异化end--------------------
//		String[] prpLdriverDrivingLicenseNo = httpServletRequest.getParameterValues("prpLdriverDrivingLicenseNo");// 驾照号码
//		String[] prpLdriverDriverName = httpServletRequest.getParameterValues("prpLdriverDriverName");
//		String[] prpLdriverDriverSex = httpServletRequest.getParameterValues("driverSex");
		// 根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息
//		String[] prpLdriverDriverPhone = httpServletRequest.getParameterValues("prpLdriverDriverPhone");
//		String[] prpLdriverDrivingCarType = httpServletRequest.getParameterValues("drivingCarType");
//		String[] prpLprpLdriverApanageCode = httpServletRequest.getParameterValues("prpLdriverApanageCode");
//		String[] prpLprpLdriverApanage = httpServletRequest.getParameterValues("prpLdriverApanage");
		// 对象赋值
		// 驾驶员部分开始
		if (null!=claimExternalSourceVo && null!=claimExternalSourceVo.getDriverList()
				&& claimExternalSourceVo.getDriverList().size()>0) {
			for (int index = 1; index <= claimExternalSourceVo.getDriverList().size(); index++) {
				Driver driver = claimExternalSourceVo.getDriverList().get(index-1);
				
				prpLdriver = new PrpLdriver();
				prpLdriver.getId().setRegistNo(prpLdriverRegistNo);
				prpLdriver.setClaimNo(prpLdriverClaimNo);
				prpLdriver.setRiskCode(prpLdriverRiskCode);
				prpLdriver.setPolicyNo(prpLdriverPolicyNo);
				prpLdriver.getId().setSerialNo(index);
				prpLdriver.setLicenseNo(driverLicenseno);//牌照號碼//json沒有 由 "標的車"取得
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
				prpLdriver.setDrivingLicenseNo(driver.getDriverLicenseNo());//駕照號碼
				prpLdriver.setIdentifyNumber(driver.getDriverIdentityNumber());
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
				// 差异化begin--------------add by liuwei-----------
				prpLdriver.setIsMarried(driver.getDriverIsMarried());//婚姻別
				prpLdriver.setBirthday(CommonUtils.toYearToDayDate(driver.getDriverBirthday()));//出生年份
//				prpLdriver.setIdentifyNumber(prpLdriverIdentifyNumber[index]);////身份證號碼
				prpLdriver.setDriverPhone(driver.getDriverPhone());//駕駛人電話
				prpLdriver.setMobilePhone(driver.getDriverMobilePhone());//駕駛人手機
				prpLdriver.setDriverIdentity(driver.getDriverIdentity());//駕駛人身份
				prpLdriver.setDriverDistrict(driver.getDriverDistrict());//駕駛人區別：
				// 差异化end----------------------------------------
//				prpLdriver.setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);//駕照號碼
				prpLdriver.setDriverName(driver.getDriverName());//駕駛員姓名
				prpLdriver.setDriverSex(driver.getDriverSex());//性別
//				prpLdriver.setDrivingCarType(prpLdriverDrivingCarType[index]);//???
//				prpLdriver.setDriverApanageCode(prpLprpLdriverApanageCode[index]);//駕駛員屬地代碼
//				prpLdriver.setDriverApanage(prpLprpLdriverApanage[index]);//駕駛員屬地
				// /***根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息****
//				prpLdriver.setDriverSeaRoute(prpLdriverDriverPhone[index]);
				// 加入集合
				driverList.add(prpLdriver);
			}
			// 报案集合中加入驾驭员
			registDto.setPrpLdriverList(driverList);

		}
		// 原因：增加联系人信息
		List<PrpLrelatePerson> relatePersonList = new ArrayList<PrpLrelatePerson>();
		String personRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String[] prpSeriaNo = httpServletRequest.getParameterValues("prpLrelatePersonSeriaNo");
		String strPolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			strPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
		String[] prpPersonName = httpServletRequest.getParameterValues("prpLrelatePersonPersonName");
		String[] prpPhoneNumber = httpServletRequest.getParameterValues("prpLrelatePersonPhoneNumber");
		String[] prpMobile = httpServletRequest.getParameterValues("prpLrelatePersonMobile");
		String[] prpRemark = httpServletRequest.getParameterValues("prpLrelatePersonRemark");
		PrpLrelatePerson prpLrelatePerson = null;
		if (prpSeriaNo != null) {
			for (int i = 1; i < prpSeriaNo.length; i++) {
				prpLrelatePerson = new PrpLrelatePerson();
				prpLrelatePerson.getId().setRegistNo(personRegistNo);
				prpLrelatePerson.getId().setSerialNo(new BigDecimal(prpSeriaNo[i]));
				prpLrelatePerson.getId().setPersonType("Link");
				prpLrelatePerson.setPersonName(prpPersonName[i]);
				prpLrelatePerson.setPhoneNumber(prpPhoneNumber[i]);
				prpLrelatePerson.setMobile(prpMobile[i]);
				prpLrelatePerson.setRemark(prpRemark[i]);
				prpLrelatePerson.setPolicyNo(strPolicyNo);
				prpLrelatePerson.setPersonCode("");
				prpLrelatePerson.setFlag("");
				// 加入集合中
				relatePersonList.add(prpLrelatePerson);
			}
			// 报案集合中联系人信息
			registDto.setPrpLrelatePersonList(relatePersonList);
		}
		// 处理报案登记,当"第三者亡人数" "第三者伤人数" "车上人员亡人数"
		// "车上人员伤人数"四个字段其中任意一个没有填值,包括0也不填的情况下,点击"提交"报错
		String personDeathB = httpServletRequest.getParameter("prpLregistPersonDeathB");//JSON沒有[人傷跟蹤-第三者亡人數]
		if (personDeathB == null || personDeathB.equals("")) {
			personDeathB = "0";
		}
		String personInjureB = httpServletRequest.getParameter("prpLregistPersonInjureB");//JSON沒有[人傷跟蹤-第三者傷人數]
		if (personInjureB == null || personInjureB.equals("")) {
			personInjureB = "0";
		}
		String personDeathD1 = httpServletRequest.getParameter("prpLregistPersonDeathD1");//JSON沒有[人傷跟蹤-車上人員亡人數]
		if (personDeathD1 == null || personDeathD1.equals("")) {
			personDeathD1 = "0";
		}
		String personInjureD1 = httpServletRequest.getParameter("prpLregistPersonInjureD1");//JSON沒有[人傷跟蹤-車上人員傷人數]
		if (personInjureD1 == null || personInjureD1.equals("")) {
			personInjureD1 = "0";
		}
		String lregistValue1 = (String) httpServletRequest.getAttribute("prpLregistValue1");
		String lregistValue2 = (String) httpServletRequest.getAttribute("prpLregistValue2");
		if ("D".equals(strRiskType)){
			PrpLext prpLext = new PrpLext();
			String certiNo = (String) httpServletRequest.getAttribute("registNo");
			prpLext.getId().setCertiNo(certiNo);
			prpLext.getId().setCertiType("01");
			prpLext.setPersonDeathB(Integer.parseInt(personDeathB));
			prpLext.setPersonInjureB(Integer.parseInt(personInjureB));
			prpLext.setPersonDeathD1(Integer.parseInt(personDeathD1));
			prpLext.setPersonInjureD1(Integer.parseInt(personInjureD1));
			prpLext.setValue1(lregistValue1);
			prpLext.setValue2(lregistValue2);
			registDto.setPrpLext(prpLext);
		}
		// 整理调度用的标的信息,去掉最後的一个"/"
		if (registDto.getPrpLregist().getScheduleItemNote().length() > 1) {
			String strTemp = registDto.getPrpLregist().getScheduleItemNote();
			strTemp = strTemp.substring(0, strTemp.length() - 1);
			registDto.getPrpLregist().setScheduleItemNote(strTemp);
		}
		// 收集callcenter补充信息
		String callCenterInfo = (String) httpServletRequest.getAttribute("callCenterInfo");
		ArrayList<PrpLregistText> prpLregistTextList2 = new ArrayList<PrpLregistText>();
		String[] rules2 = StringUtils.split(callCenterInfo, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules2.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules2[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("5");// 5表示95519补充报案信息
			prpLregistTextList2.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList2(prpLregistTextList2);
		return registDto;

	}
	
	/**
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 */
	public RegistDto externalRiskToDto(HttpServletRequest httpServletRequest,ClaimExternalRiskSourceVo claimExternalRiskSourceVo,PrpCmain prpCmainIn,PrpLregist prpLregistIn) throws Exception {
		//多元收件資料
		prpLregistIn.setMultiRecepNo(claimExternalRiskSourceVo.getMultiRecepNo());
		prpLregistIn.setChannelSource(claimExternalRiskSourceVo.getChannelSource());
		prpLregistIn.setMemo(claimExternalRiskSourceVo.getMemo());
		// 继承对regist,registText表的赋值
		RegistDto registDto = null;//super.externalToDto(httpServletRequest,claimExternalSourceVo,prpCmainIn,prpLregistIn);
		String riskCodeTmp = prpLregistIn.getRiskCode();
		
		//*9009m claimExternalRiskSourceVo補充寫入物件
		registDto = super.externalRiskToDto(httpServletRequest,claimExternalRiskSourceVo,prpCmainIn,prpLregistIn);
		
		
		/* 强三 ---关联表的存储----------------- */
		// 目前只存储了强三的单号
		Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
		Prplregistrpolicy prpLRegistRPolicy_comp = new Prplregistrpolicy();
		String registNo = (String) httpServletRequest.getAttribute("registNo");
		String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
		String quaryPolicyNo = (String) httpServletRequest.getAttribute("quaryPolicyNo");
		String policyNo = (String) httpServletRequest.getAttribute("prpLregistPolicyNo");
		httpServletRequest.setAttribute("prpLregistRiskCode",prpLregistIn.getRiskCode());//下面會用到，為盡量保持與原程式同貌，用塞入attribute方式
		String prpLregistRPPolicyRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		// 交强险迁移 报案类型 0 ：商业险单独报案 1：交强险单独报案 2：商业、交强险关联报案
		String registType = (String)httpServletRequest.getAttribute("registType");
		boolean isCompelRiskOnly = false; // 判断单独交强报案的情况，默认都是否。
		List<Prplregistrpolicy> prpLRegistRPolicyList = new ArrayList<Prplregistrpolicy>();
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		String flowId = prpLregistrpolicyService.findSwfLogId(registNo);
		if (DataUtils.emptyToNull(policyNo) != null && DataUtils.emptyToNull(mainPolicyNo) != null && "2".equals(DataUtils.emptyToNull(registType))) {
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(policyNo)) {
				prpLRegistRPolicy.setRegistFlag("1");
			} else {
				prpLRegistRPolicy.setRegistFlag("0");
			}
			prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
			prpLRegistRPolicy.getId().setRegistNo(registNo);
			prpLRegistRPolicy.getId().setPolicyNo(policyNo);
			prpLRegistRPolicy.setPolicyType("1");
			prpLRegistRPolicy.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(policyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
			if (quaryPolicyNo != null && !quaryPolicyNo.equals("") && quaryPolicyNo.equals(mainPolicyNo)) {
				prpLRegistRPolicy_comp.setRegistFlag("1");
			} else {
				prpLRegistRPolicy_comp.setRegistFlag("0");
			}
			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
			prpLRegistRPolicy_comp.setRiskCode(compelRiskCode);
			
			prpLRegistRPolicy_comp.getId().setRegistNo(registNo);
			prpLRegistRPolicy_comp.getId().setPolicyNo(mainPolicyNo);
			prpLRegistRPolicy_comp.setPolicyType("3");
			prpLRegistRPolicy_comp.setValidStatus("1");
			for(PrpLclaim temp : prpLclaimList){
				if(mainPolicyNo.equals(temp.getPolicyNo())){
					prpLRegistRPolicy_comp.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy_comp.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy_comp);
			// 以上为关联表增加两条的情况
		} else {
			// 交强险迁移 2013-03-15 chenjie
			if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
				String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
				prpLRegistRPolicy.getId().setPolicyNo(org.apache.commons.lang.StringUtils.isNotEmpty(mainPolicyNo)?mainPolicyNo:policyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				prpLRegistRPolicy.setPolicyType("3");
				prpLRegistRPolicy.setRiskCode(compelRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
				isCompelRiskOnly = true;
			} else {
				// 存储费关联;
				prpLRegistRPolicy.getId().setPolicyNo(policyNo);
				prpLRegistRPolicy.getId().setRegistNo(registNo);
				String strConfigCode = codeService.translateRiskCodetoConfigCode(prpLregistRPPolicyRiskCode);
				if ("RISKCODE_DAZ".equals(strConfigCode)) {
					prpLRegistRPolicy.setPolicyType("3");
				} else {
					prpLRegistRPolicy.setPolicyType("1");
				}
				// reason:判断出单独交强的情况
				String strOnlyDAZ = codeService.translateRiskCodetoConfigCode(registDto.getPrpLregist().getRiskCode());
				if ("RISKCODE_DAZ".equals(strOnlyDAZ)) {
					// 这里可以判断出是单独交强险报的案
					isCompelRiskOnly = true;
				}
				// 单独交强
				prpLRegistRPolicy.setRiskCode(prpLregistRPPolicyRiskCode);
				prpLRegistRPolicy.setRegistFlag("1");
				prpLRegistRPolicy.setValidStatus("1");
			}
			for(PrpLclaim temp : prpLclaimList){
				if(prpLRegistRPolicy.getId().getPolicyNo().equals(temp.getPolicyNo())){
					prpLRegistRPolicy.setClaimNo(temp.getClaimNo());
				}
			}
			prpLRegistRPolicy.setFlowID(flowId);
			prpLRegistRPolicyList.add(prpLRegistRPolicy);
		}
		registDto.setPrpLRegistRPolicyList(prpLRegistRPolicyList);
		/* 强三 ---关联表的存储 */
		List<PrpLthirdParty> thirdPartyList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = null;

		// 从界面得到输入数组
		String prpLthirdPartyRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPartyRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		// 交强险迁移 2013-03-15 chenjie
		if ("1".equals(DataUtils.emptyToNull(registType))) {// 交强险单独报案
			prpLthirdPartyRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String prpLthirdPartyClaimNo = httpServletRequest.getParameter("prpLregistClaimNo");
		String prpLthirdPartyClauseType = httpServletRequest.getParameter("prpLregistClauseType");
		String[] prpLthirdPartySerialNo = httpServletRequest.getParameterValues("prpLthirdPartySerialNo");
		String[] prpLthirdPartyLicenseNo = httpServletRequest.getParameterValues("prpLthirdPartyLicenseNo");
		String[] prpLthirdPartyLicenseColorCode = httpServletRequest.getParameterValues("licenseColorCode");
		String[] prpLthirdPartyCarKindCode = httpServletRequest.getParameterValues("carKindCode");
		String[] prpLthirdPartyInsureCarFlag = httpServletRequest.getParameterValues("insureCarFlag");
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
		String[] prpLthirdPartySelectSend = httpServletRequest.getParameterValues("prpLthirdPartySelectSend");
		// 差异化begin---add by liuwei----2013-5-12----------
		String[] prpLthirdPartyGarageHeadName = httpServletRequest.getParameterValues("prpLthirdPartyGarageHeadName");
		String[] prpLthirdPartyRelationship = httpServletRequest.getParameterValues("prpLthirdPartyRelationship");
		String[] prpLthirdPartyDrivingAddress = httpServletRequest.getParameterValues("prpLthirdPartyDrivingAddress");
		String[] prpLthirdPartyCarryingUnit = httpServletRequest.getParameterValues("prpLthirdPartyCarryingUnit");
		String[] prpLthirdPartyInsuranceNo = httpServletRequest.getParameterValues("prpLthirdPartyInsuranceNo");
		String[] prpLthirdPartyIsInsurance = httpServletRequest.getParameterValues("prpLthirdPartyIsInsurance");
		String[] prpLthirdPartyCarryingNumber = httpServletRequest.getParameterValues("prpLthirdPartyCarryingNumber");
		String[] prpLthirdPartyCarsOwners = httpServletRequest.getParameterValues("prpLthirdPartyCarsOwners");
		String[] prpLthirdPartyInsuredIdentity = httpServletRequest.getParameterValues("prpLthirdPartyInsuredIdentity");
		// 差异化end---------------------
		// 取赔案类型的选择
		/*----------包括查勘调度，定损调度 PrpLscheduleMainWF ,PrpLscheduleItem --------------*/
		List<PrpLscheduleItem> scheduleItemDtoList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		int scheduleId = 1; // 调度号的id
		PrpLscheduleMainWF prpLscheduleMainWF = null;
		// 通过代码对照表转换riskcode
		String riskCode = registDto.getPrpLregist().getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		if ("D".equals(strRiskType)) {
			PrpLregist prpLregist = registDto.getPrpLregist();
			prpLscheduleMainWF = new PrpLscheduleMainWF();
			prpLscheduleMainWF.getId().setScheduleID(1);
			prpLscheduleMainWF.getId().setRegistNo(prpLregist.getRegistNo());
			prpLscheduleMainWF.setSurveyNo(0);
			prpLscheduleMainWF.setClaimComCode(prpLregist.getComCode());
			prpLscheduleMainWF.setRiskCode(prpLregist.getRiskCode());
			prpLscheduleMainWF.setPolicyNo(prpLregist.getPolicyNo());
			prpLscheduleMainWF.setOperatorCode("");
			prpLscheduleMainWF.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLscheduleMainWF.setInputHour(DateTime.current().getHour());
			prpLscheduleMainWF.setScheduleObjectID("_");
			prpLscheduleMainWF.setScheduleObjectName(" ");
			String scheduleType = (String)httpServletRequest.getAttribute("scheduleType");
			prpLscheduleMainWF.setScheduleType(scheduleType);
			prpLscheduleMainWF.setCheckFlag("0");
			prpLscheduleMainWF.setScheduleFlag("0"); // 查勘调度没有被派出去的
			prpLscheduleMainWF.setFlag("");
			prpLscheduleMainWF.setCheckSite(registDto.getPrpLregist().getDamageAddress());
		}
		// 加入调度主表
		registDto.setPrpLscheduleMainWF(prpLscheduleMainWF);

		
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
		//另外加工塞回去
		//標的車
		prpLthirdParty = (PrpLthirdParty) httpServletRequest.getAttribute("prpLthirdParty");
		
		if(null!=claimExternalRiskSourceVo  && 
				(null==claimExternalRiskSourceVo.getThirdPartyList() || claimExternalRiskSourceVo.getThirdPartyList().size()==0)){
			
			List<ThirdParty> thirdPartyList_ = new ArrayList<ThirdParty>();
			for(int i = 1;i<=prpLthirdParty.getThirdPartyList().size();i++){
				ThirdParty thirdParty = new ThirdParty();
				
				PrpLthirdParty prpLthirdParty_ = prpLthirdParty.getThirdPartyList().get(i-1);
				
				thirdParty.setLicenseNo(prpLthirdParty_.getLicenseNo());
				thirdParty.setCarKindCode(prpLthirdParty_.getCarKindCode());
				thirdParty.setInsureComCode(prpLthirdParty_.getInsureComCode());
				thirdPartyList_.add(thirdParty);
				
			}
			claimExternalRiskSourceVo.setThirdPartyList(thirdPartyList_);
			
		}
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
		
		// 对象赋值
		// 三者车辆部分开始
		if(null!=claimExternalRiskSourceVo && null!=claimExternalRiskSourceVo.getThirdPartyList()
				&& claimExternalRiskSourceVo.getThirdPartyList().size()>0
//				&& 1==2 //暫時擋住
				){
			for (int index = 1; index <= claimExternalRiskSourceVo.getThirdPartyList().size(); index++) {
				prpLthirdParty = new PrpLthirdParty();
				ThirdParty thirdParty = claimExternalRiskSourceVo.getThirdPartyList().get(index-1);
				prpLthirdParty.getId().setSerialNo(index);//prpLthirdPartySerialNo[index]
				prpLthirdParty.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLthirdParty.setRiskCode(prpLthirdPartyRiskCode);
				prpLthirdParty.setClaimNo(prpLthirdPartyClaimNo);
				prpLthirdParty.setClauseType(prpLthirdPartyClauseType);
				if("".equals(thirdParty.getLicenseNo())||thirdParty.getLicenseNo()==null){
					thirdParty.setLicenseNo(" ");
				}
				prpLthirdParty.setLicenseNo(thirdParty.getLicenseNo());//牌照號碼(D)
//				prpLthirdParty.setLicenseColorCode(prpLthirdPartyLicenseColorCode[index]);//號牌底色
				prpLthirdParty.setCarKindCode(thirdParty.getCarKindCode());//車輛種類(D)
				prpLthirdParty.setInsureCarFlag(index==1?"1":"0");//標的車||第三方車輛||??(D)
//				prpLthirdParty.setEngineNo(thirdParty.getprpLthirdPartyEngineNo[index]);//引擎號碼
//				prpLthirdParty.setFrameNo(prpLthirdPartyFrameNo[index]);//車身號碼
//				prpLthirdParty.setBrandName(prpLthirdPartyBrandName[index]);//廠牌型號
//				prpLthirdParty.setModelCode(prpLthirdPartyModelCode[index]);//廠牌型號 代碼
//				prpLthirdParty.setRunDistance(Double.parseDouble(DataUtils.nullToZero(prpLthirdPartyRunDistance[index])));//里程數
//				prpLthirdParty.setUseYears(Integer.parseInt(DataUtils.nullToZero(prpLthirdPartyUseYears[index])));//使用年限
				prpLthirdParty.setDutyPercent(Double.parseDouble(DataUtils.nullToZero("0.0")));//責任比例:
//				prpLthirdParty.setInsuredFlag("1");//畫面上抓不到 判定是第一筆標的車 第二筆以後都是"+"出來的三方車
				
				prpLthirdParty.setInsureComCode(thirdParty.getInsureComCode());//承保公司代號(D)
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START 
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(thirdParty.getInsureComCode());
				prpLthirdParty.setInsureComName(prpDcompany.getComCName());//承保公司名稱
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
				
//				prpLthirdParty.setVINNo(prpLthirdPartyVINNo[index]);//????這啥
//				prpLthirdParty.setGarageHeadName(prpLthirdPartyGarageHeadName[index]);//修車廠負責人姓名
				// 由於标的车和三者车的属性现在不完全一样了，故作如下判断
//				if (prpLthirdParty.getInsuredFlag().equals("1")) {
				if(index == 1){///畫面上抓不到 判定是第一筆標的車 第二筆以後都是"+"出來的三方車 ，參考畫面似乎也是這樣的邏輯
//					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);//財車駕駛地址
					prpLthirdParty.setRelationship(claimExternalRiskSourceVo.getRelationship());//(D)
				} else {
					prpLthirdParty.setCarryingUnit(thirdParty.getCarryingUnit());//承載單位(D)
					prpLthirdParty.setInsuranceNo(thirdParty.getInsuranceNo());//強制保險證編號(D)
					prpLthirdParty.setIsInsurance(thirdParty.getIsInsurance());//是否有保強制險(D)
					prpLthirdParty.setCarryingNumber(Long.parseLong(DataUtils.nullToZero(thirdParty.getCarringNumber())));//乘載數量(D)
					prpLthirdParty.setInsuredIdentity(thirdParty.getInsuredIdentity());//被保險人身分(D)
//					prpLthirdParty.setCarsOwners(prpLthirdPartyCarsOwners[index]);//財車車主
//					prpLthirdParty.setDrivingAddress(prpLthirdPartyDrivingAddress[index]);//財車駕駛地址
				}
				// 加入集合
				thirdPartyList.add(prpLthirdParty);
				// 整理调度情况
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(prpLthirdPartyRegistNo);
				prpLscheduleItem.getId().setItemNo(prpLthirdParty.getId().getSerialNo());
				prpLscheduleItem.setInsureCarFlag(prpLthirdParty.getInsureCarFlag());
				prpLscheduleItem.setClaimComCode(prpLthirdParty.getInsureComCode());
				// 表示是否选中
				prpLscheduleItem.setSelectSend(index==1?"0":"");//看畫面判斷的
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setLicenseNo(prpLthirdParty.getLicenseNo());
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setNextNodeNo("certa");
				// 加入调度标的集合
				// reasion:如果单独保的交强险的标的车辆，不显示标的车调度信息
				// 首先判断不是（单交强和标的车）的数据，增加定损车辆调度。
				// 交强险赔标的车
				// 在单交强险的情况並且在配置过的机构下会执行该段代码
				PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "advance_case");
				PrpDriskConfig prpDriskConfig1 = prpDriskConfigService.findByPrimaryKey(registDto.getPrpLregist().getComCode(), registDto.getPrpLregist().getRiskCode(), "dealFast_case");
				if ((prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) || (prpDriskConfig1 != null && "1".equals(prpDriskConfig1.getConfigValue()))) {
					scheduleItemDtoList.add(prpLscheduleItem);
					// 设置调度的标签显示 shcheduleItemNote
					String strTemp = "";
					if (prpLscheduleItem.getId().getItemNo() != 1)
						strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
				} else {
					if (!((isCompelRiskOnly && prpLscheduleItem.getId().getItemNo() == 1)) || isCompelRiskOnly) {
						scheduleItemDtoList.add(prpLscheduleItem);
						// 设置调度的标签显示 shcheduleItemNote
						String strTemp = "";
						if (prpLscheduleItem.getId().getItemNo() != 1)
							strTemp = "三者:"; // 如果不是标的车的话，在标签中增加三者几个字。
						registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + strTemp + prpLscheduleItem.getLicenseNo() + "/");
					}// 判断是否需要增加定损车辆的标的
				}
			}
			// 报案集合中加入三者车辆
			registDto.setPrpLthirdPartyList(thirdPartyList);
			
			// 这里直接先读财产定损了， 不再另写了。。如果选择了财产定损的话
			String prpLthirdPropSelectSend = prpLscheduleItem.getSelectSend();//httpServletRequest.getParameter("prpLthirdPropSelectSend");
			//強制證號儲存時去掉最前面的公司碼
			for(int i = 0 ;i<thirdPartyList.size();i++){
				if(i>0 && ((PrpLthirdParty)thirdPartyList.get(i)).getIsInsurance().equals("1")){
					if(null!=thirdPartyList.get(i).getInsuranceNo() && !"".equals(thirdPartyList.get(i).getInsuranceNo())
						&& thirdPartyList.get(i).getInsuranceNo().length()>2){
					thirdPartyList.get(i).setInsuranceNo(thirdPartyList.get(i).getInsuranceNo().trim().substring(2));
						}
				}
			}
			
			if (prpLthirdPropSelectSend.equals("1")) {
				prpLscheduleItem = new PrpLscheduleItem();
				prpLscheduleItem.getId().setScheduleID(scheduleId++);
				prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLscheduleItem.getId().setItemNo(1);
				// 表示是否选中
				prpLscheduleItem.setSelectSend(prpLthirdPropSelectSend);
				// 表示没有调度成定损过
				prpLscheduleItem.setSurveyTimes(0);
				prpLscheduleItem.setSurveyType("1");
				prpLscheduleItem.setCheckSite(registDto.getPrpLregist().getDamageAddress());
				prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
				prpLscheduleItem.setScheduleType("schel");
				prpLscheduleItem.setLicenseNo("財產損失");
				prpLscheduleItem.setScheduleObjectID("_");
				prpLscheduleItem.setScheduleObjectName(" ");
				prpLscheduleItem.setNextNodeNo("propc");
				scheduleItemDtoList.add(prpLscheduleItem);
				registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
			}
			// 报案集合中加入调度标的
		}
		// Reason:损失部位模块中加进零件代码、零件名称,损失部位代码与零件(项目)代码都以列表框形式展现
		/*---------------------损失部位 PrpLthirdCarLoss begin------------------------------------*/
		List<PrpLthirdCarLoss> thirdCarLossList = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = null;
		// 从界面得到输入数组
		String prpLthirdCarLossRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdCarLossRiskCode = (String)httpServletRequest.getAttribute("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdCarLossRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
				// 加入集合
				thirdCarLossList.add(prpLthirdCarLoss);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLthirdCarLossList(thirdCarLossList);
		}
		/*---------------------损失部位 PrpLthirdCarLoss-----------------------------------*/
		// Reason:页面中增加其它损失模块
		/*---------------------其它损失部位 PrpLthirdProp-----------------------------------*/
		List<PrpLthirdProp> thirdPropList = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = null;
		// 从界面得到输入数组
		String prpLthirdPropRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLthirdPropRiskCode = (String)httpServletRequest.getAttribute("prpLregistRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLthirdPropRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
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
			registDto.setPrpLthirdPropList(thirdPropList);
		}
		/*---------------------其它损失部位 PrpLthirdProp ------------------------------------*/
		/*---------------------人员伤亡跟踪 PrpLpersonTrace ------------------------------------*/
		List<PrpLpersonTrace> personTraceList = new ArrayList<PrpLpersonTrace>();
		PrpLpersonTrace prpLpersonTrace = null;
		// 从界面得到输入数组
		String prpLpersonTraceRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLpersonTracePolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLpersonTracePolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
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
		String[] PrpLpersonTraceSelectSend = httpServletRequest.getParameterValues("prpLpersonTraceSelectSend");////觀察jsp都是0
		String[] prpLpersonTraceDoctor = null;
		String[] prpLpersonTraceHospitalCode = null;
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
		String[] PrpLpersonTraceIdNumber = httpServletRequest.getParameterValues("prpLpersonTraceIdNumber");
		String[] PrpLpersonTraceRideSituation = httpServletRequest.getParameterValues("rideSituation");
		String[] PrpLpersonTraceLicenseno = httpServletRequest.getParameterValues("prpLpersonTraceLicenseno");

//		String[] PrpLpersonTraceBklineQueryDate = httpServletRequest.getParameterValues("prpLpersonTraceBklineQueryDate");
		String[] PrpLpersonTraceIdNumberType = httpServletRequest.getParameterValues("prpLpersonTraceIdNumberType");
		String[] PrpLpersonTraceApplicantBirthday = httpServletRequest.getParameterValues("prpLpersonTraceApplicantBirthday");
		//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
		if ("E".equals(strRiskType)) {
			prpLpersonTraceDoctor = httpServletRequest.getParameterValues("prpLpersonTraceDoctor");// 就診醫師
			prpLpersonTraceHospitalCode = httpServletRequest.getParameterValues("prpLpersonTraceHospitalCode");// 就诊医院代碼
		}
		String driverLicenseno="";
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (null!=claimExternalRiskSourceVo && null!=claimExternalRiskSourceVo.getPersonTraceList()
				&& claimExternalRiskSourceVo.getPersonTraceList().size()>0) {
			for (int index = 1; index <= claimExternalRiskSourceVo.getPersonTraceList().size(); index++) {
				PersonTrace personTrace = claimExternalRiskSourceVo.getPersonTraceList().get(index-1);
				
//				if(B01必田一組){
//					
//				}
				//人傷跟蹤訊息 
				prpLpersonTrace = new PrpLpersonTrace();
				prpLpersonTrace.getId().setRegistNo(prpLpersonTraceRegistNo);
				prpLpersonTrace.setClaimNo(""); // 改为存空值
				prpLpersonTrace.setPolicyNo(prpLpersonTracePolicyNo);
				
				prpLpersonTrace.getId().setPersonNo(index);//1開始
				prpLpersonTrace.setPersonName(personTrace.getPersonName());//受害人姓名
				prpLpersonTrace.setPersonSex(personTrace.getPersonSex());//受害人性別
//				prpLpersonTrace.setIdentifyNumber(personTrace.getIdentifyNumber());
//				prpLpersonTrace.setRelatePersonNo(Integer.parseInt(DataUtils.nullToZero(prpLpersonTraceRelatePersonNo[index])));
//				prpLpersonTrace.setJobCode(prpLpersonTraceJobCode[index]);
//				prpLpersonTrace.setJobName(prpLpersonTraceJobName[index]);
				if (prpLpersonTraceReferKind == null) {//涉及險種(似乎沒配到)
					prpLpersonTrace.setReferKind("");
				} else {
					prpLpersonTrace.setReferKind(prpLpersonTraceReferKind[index]);
				}
//				prpLpersonTrace.setPartDesc(prpLpersonTracePartDesc[index]);//受傷部位
//				prpLpersonTrace.setHospital(CommonUtils.getValue(prpLpersonTraceHospital,index));//就診醫院
//				prpLpersonTrace.setMotionFlag(prpLpersonTraceMotionFlag[index]);//是否自行就醫
//				prpLpersonTrace.setWoundRemark(prpLpersonTraceWoundRemark[index]);//傷情描述
//				prpLpersonTrace.setRemark(prpLpersonTraceRemark[index]);
//				prpLpersonTrace.setFlag(prpLpersonTraceFlag[index]);
//				if ("E".equals(strRiskType)) {
//					prpLpersonTrace.setDoctor(prpLpersonTraceDoctor[index]);
//					prpLpersonTrace.setHospitalCode(prpLpersonTraceHospitalCode[index]);
//				}
				prpLpersonTrace.setIdNumber(personTrace.getIdentifyNumber());//受害人身分證號
				prpLpersonTrace.setRideSituation(personTrace.getRideSituation());//受害人乘坐狀況：
				prpLpersonTrace.setLicenseno(personTrace.getLicenseNo());//受害人乘坐車輛牌照號碼
				if(index==1){
					driverLicenseno = personTrace.getLicenseNo();
				}
//				prpLpersonTrace.setBklineQueryDate(CommonUtils.toYearToSercondDate(PrpLpersonTraceBklineQueryDate[index]));
				prpLpersonTrace.setBklineQueryDate(new Date());//存檔就會查 所以存檔寫當下時間既可
				prpLpersonTrace.setIdNumberType(personTrace.getIdNumberType());//受害人身分證號類別
				prpLpersonTrace.setApplicantBirthday(CommonUtils.toYearToDayDate(personTrace.getApplicantBirthday()));
				prpLpersonTrace.setPersonAge(com.sinosoft.claim.common.util.DataUtils.calculateAgeWithDateUtils(personTrace.getApplicantBirthday()));
//				prpLpersonTrace.setPersonAge(Integer.parseInt(DataUtils.nullToZero(prpLpersonTracePersonAge[index])));需用ApplicantBirthday算年齡
				
				// 加入集合
				personTraceList.add(prpLpersonTrace);
				
				// 这里直接先读人伤跟踪了， 不再另写了。。如果选择了人伤的话
				// if (PrpLpersonTraceSelectSend.equals("1")) {
				if("D".equals(strRiskType)) {//非车不生成调度标的表信息。
					prpLscheduleItem = new PrpLscheduleItem();
					prpLscheduleItem.getId().setScheduleID(scheduleId++);
					prpLscheduleItem.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
					prpLscheduleItem.getId().setItemNo(index);
					// 表示是否选中
					prpLscheduleItem.setSelectSend("0");//PrpLpersonTraceSelectSend[index]);//觀察JSP都是0
					// 表示没有调度成定损过
					prpLscheduleItem.setSurveyTimes(0);
					prpLscheduleItem.setSurveyType("1");
					prpLscheduleItem.setCheckSite("");//prpLpersonTraceHospital[index]);//WS先給空// 人伤的就诊医院
					prpLscheduleItem.setInputDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
					prpLscheduleItem.setScheduleType("schel");
					prpLscheduleItem.setLicenseNo(prpLpersonTrace.getPersonName());//不懂為何原本給name?? prpLpersonTracePersonName[index]);
					prpLscheduleItem.setScheduleObjectID("_");
					prpLscheduleItem.setScheduleObjectName(" ");
					prpLscheduleItem.setNextNodeNo("wound");
					scheduleItemDtoList.add(prpLscheduleItem);
					registDto.getPrpLregist().setScheduleItemNote(registDto.getPrpLregist().getScheduleItemNote() + prpLscheduleItem.getLicenseNo() + "/");
				}
				// }
			}
			// 报案集合中加入损失部位
			registDto.setPrpLpersonTraceList(personTraceList);
		}
		// 整理数据，整理定损调度的数据，如果当提交的时候。。将新的数据放入prplscheduleItem中，並保留已经调度过的数据
		if ("4".equals(registDto.getPrpLclaimStatus().getStatus())) {
			prpLscheduleMainWF = registDto.getPrpLscheduleMainWF();
			if (prpLscheduleMainWF != null) {
				PrpLscheduleMainWF prpLscheduleMainWFOld = scheduleService.findScheduleMainByConditions(" registno ='" + registDto.getPrpLregist().getRegistNo() + "' and scheduleFlag='1'");
				if (prpLscheduleMainWFOld != null) {
					registDto.setPrpLscheduleMainWF(null);
					// prpLscheduleMainWF.setScheduleFlag(prpLscheduleMainWFOld.getScheduleFlag());
					// prpLscheduleMainWF.setOperatorCode(prpLscheduleMainWFOld.getOperatorCode());
					// prpLscheduleMainWF.setScheduleObjectID(prpLscheduleMainWFOld.getScheduleObjectID());
					// prpLscheduleMainWF.setScheduleObjectName(prpLscheduleMainWFOld.getScheduleObjectName());
				}
			}
			if (scheduleItemDtoList.size() > 0) { // 本次查勘查找到有新的调度任务
				// 检查定损调度的情况，如果存在定损调度，检查是否已经调度过，如果没有调度过，按照没有调度过处理
				// 查询调度过的
				String strSql = " registno ='" + registDto.getPrpLregist().getRegistNo() + "' and surveyTimes='1'";
				// 查询数据
				List<PrpLscheduleItem> prpLscheduleItemList = (List<PrpLscheduleItem>) scheduleService.findItemByConditions(strSql);
				PrpLscheduleItem prpLscheduleItemold = null;
				List<PrpLscheduleItem> scheduleItemLastList = new ArrayList<PrpLscheduleItem>();
				if (prpLscheduleItemList == null || prpLscheduleItemList.size() < 1) {
					registDto.setPrpLscheduleItemList(scheduleItemDtoList);
				} else {
					// 检查整理好的数据中，是否已经有已经调度过的数据
					// scheduleItemDtoList 是指原来从调度已经调度过的数据，无论怎么样，都是不能被删除的。
					// 只要检查 scheduleItemDtoList中存在
					// prpLscheduleItemList中没有的，就增加prpLscheduleItemList好了。
					boolean blnotFind = true;
					for (int i = 0; i < scheduleItemDtoList.size(); i++) {
						prpLscheduleItem = (PrpLscheduleItem) scheduleItemDtoList.get(i);
						// 原则，相同的，以原来的数据为准，没有的已後来的为准
						blnotFind = true;
						for (int j = 0; j < prpLscheduleItemList.size(); j++) {
							prpLscheduleItemold = prpLscheduleItemList.get(j);
							if (prpLscheduleItemold.getNextNodeNo().equals(prpLscheduleItem.getNextNodeNo())) {
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
					registDto.setPrpLscheduleItemList(scheduleItemLastList);
				}
			}
		}
		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		List<PrpLregistExt> prpLregistExtList = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = null;
		// 从界面得到输入数组
		String prpLregistExtRiskCode = httpServletRequest.getParameter("prpLregistExtRiskCode");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLregistExtRiskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String[] prpLregistExtSerialNo = httpServletRequest.getParameterValues("prpLregistExtSerialNo");
		String[] prpLregistExtInputDate = httpServletRequest.getParameterValues("prpLregistExtInputDate");
		String[] prpLregistExtInputHour = httpServletRequest.getParameterValues("prpLregistExtInputHour");
		String[] prpLregistExtOperatorCode = httpServletRequest.getParameterValues("prpLregistExtOperatorCode");
		String[] prpLregistExtContext = httpServletRequest.getParameterValues("prpLregistExtContext");
		// 对象赋值
		// 人员伤亡跟踪 部分开始
		if (prpLregistExtSerialNo != null) {
			for (int index = 1; index < prpLregistExtSerialNo.length; index++) {
				prpLregistExt = new PrpLregistExt();
				prpLregistExt.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
				prpLregistExt.setRiskCode(prpLregistExtRiskCode);
				prpLregistExt.getId().setSerialNo(Integer.parseInt(DataUtils.nullToZero(prpLregistExtSerialNo[index])));
				prpLregistExt.setInputDate(new DateTime(prpLregistExtInputDate[index], DateTime.YEAR_TO_DAY));
				prpLregistExt.setInputHour(prpLregistExtInputHour[index]);
				prpLregistExt.setOperatorCode(prpLregistExtOperatorCode[index]);
				prpLregistExt.setContext(prpLregistExtContext[index]);
				// 加入集合
				prpLregistExtList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			registDto.setPrpLregistExtList(prpLregistExtList);
		}
		/*---------------------驾驶员prpLdriver------------------------------------*/
		List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = null;
		// 从界面得到输入数组
		String prpLdriverRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String prpLdriverClaimNo = (String) httpServletRequest.getAttribute("prpLregistClaimNo");
		String prpLdriverRiskCode = (String) httpServletRequest.getAttribute("prpLregistRiskCode");
		String prpLdriverPolicyNo = (String) httpServletRequest.getAttribute("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			prpLdriverRiskCode = ConstantCodes.RISKCODE_DAZ;
			prpLdriverPolicyNo = (String)httpServletRequest.getAttribute("mainPolicyNo");
		}
//		String[] prpLdriverSerialNo = httpServletRequest.getParameterValues("prpLdriverSerialNo");
//		String[] prpLdriverLicenseNo = httpServletRequest.getParameterValues("prpLdriverLicenseNo");
		// 差异化begin--------------------add by liuwei-----2013-5-13------
//		String[] prpLdriverIsMarried = httpServletRequest.getParameterValues("prpLdriverIsMarried");
//		String[] prpLdriverBirthday = httpServletRequest.getParameterValues("prpLdriverBirthday");
//		String[] prpLdriverIdentifyNumber = httpServletRequest.getParameterValues("prpLdriverIdentifyNumber");// 身份证号码
//		String[] prpLdriverMobilePhone = httpServletRequest.getParameterValues("prpLdriverMobilePhone");
//		String[] prpLdriverDriverIdentity = httpServletRequest.getParameterValues("prpLdriverDriverIdentity");
//		String[] prpLdriverDriverDistrict = httpServletRequest.getParameterValues("prpLdriverDriverDistrict");
		// 差异化end--------------------
//		String[] prpLdriverDrivingLicenseNo = httpServletRequest.getParameterValues("prpLdriverDrivingLicenseNo");// 驾照号码
//		String[] prpLdriverDriverName = httpServletRequest.getParameterValues("prpLdriverDriverName");
//		String[] prpLdriverDriverSex = httpServletRequest.getParameterValues("driverSex");
		// 根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息
//		String[] prpLdriverDriverPhone = httpServletRequest.getParameterValues("prpLdriverDriverPhone");
//		String[] prpLdriverDrivingCarType = httpServletRequest.getParameterValues("drivingCarType");
//		String[] prpLprpLdriverApanageCode = httpServletRequest.getParameterValues("prpLdriverApanageCode");
//		String[] prpLprpLdriverApanage = httpServletRequest.getParameterValues("prpLdriverApanage");
		// 对象赋值
		// 驾驶员部分开始
		if (null!=claimExternalRiskSourceVo && null!=claimExternalRiskSourceVo.getDriverList()
				&& claimExternalRiskSourceVo.getDriverList().size()>0) {
			for (int index = 1; index <= claimExternalRiskSourceVo.getDriverList().size(); index++) {
				Driver driver = claimExternalRiskSourceVo.getDriverList().get(index-1);
				
				prpLdriver = new PrpLdriver();
				prpLdriver.getId().setRegistNo(prpLdriverRegistNo);
				prpLdriver.setClaimNo(prpLdriverClaimNo);
				prpLdriver.setRiskCode(prpLdriverRiskCode);
				prpLdriver.setPolicyNo(prpLdriverPolicyNo);
				prpLdriver.getId().setSerialNo(index);
				prpLdriver.setLicenseNo(driverLicenseno);//牌照號碼//json沒有 由 "標的車"取得
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 START
				prpLdriver.setDrivingLicenseNo(driver.getDriverLicenseNo());//駕照號碼
				prpLdriver.setIdentifyNumber(driver.getDriverIdentityNumber());
				//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 END
				// 差异化begin--------------add by liuwei-----------
				prpLdriver.setIsMarried(driver.getDriverIsMarried());//婚姻別
				prpLdriver.setBirthday(CommonUtils.toYearToDayDate(driver.getDriverBirthday()));//出生年份
//				prpLdriver.setIdentifyNumber(prpLdriverIdentifyNumber[index]);////身份證號碼
				prpLdriver.setDriverPhone(driver.getDriverPhone());//駕駛人電話
				prpLdriver.setMobilePhone(driver.getDriverMobilePhone());//駕駛人手機
				prpLdriver.setDriverIdentity(driver.getDriverIdentity());//駕駛人身份
				prpLdriver.setDriverDistrict(driver.getDriverDistrict());//駕駛人區別：
				// 差异化end----------------------------------------
//				prpLdriver.setDrivingLicenseNo(prpLdriverDrivingLicenseNo[index]);//駕照號碼
				prpLdriver.setDriverName(driver.getDriverName());//駕駛員姓名
				prpLdriver.setDriverSex(driver.getDriverSex());//性別
//				prpLdriver.setDrivingCarType(prpLdriverDrivingCarType[index]);//???
//				prpLdriver.setDriverApanageCode(prpLprpLdriverApanageCode[index]);//駕駛員屬地代碼
//				prpLdriver.setDriverApanage(prpLprpLdriverApanage[index]);//駕駛員屬地
				// /***根据驾驶员航线字段含义变更申请文档，将驾驶员航线字段改存驾驶员电话信息****
//				prpLdriver.setDriverSeaRoute(prpLdriverDriverPhone[index]);
				// 加入集合
				driverList.add(prpLdriver);
			}
			// 报案集合中加入驾驭员
			registDto.setPrpLdriverList(driverList);

		}
		// 原因：增加联系人信息
		List<PrpLrelatePerson> relatePersonList = new ArrayList<PrpLrelatePerson>();
		String personRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String[] prpSeriaNo = httpServletRequest.getParameterValues("prpLrelatePersonSeriaNo");
		String strPolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		if (registType != null && registType.equals("1")) {// 交强险单独报案
			strPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		}
		String[] prpPersonName = httpServletRequest.getParameterValues("prpLrelatePersonPersonName");
		String[] prpPhoneNumber = httpServletRequest.getParameterValues("prpLrelatePersonPhoneNumber");
		String[] prpMobile = httpServletRequest.getParameterValues("prpLrelatePersonMobile");
		String[] prpRemark = httpServletRequest.getParameterValues("prpLrelatePersonRemark");
		PrpLrelatePerson prpLrelatePerson = null;
		if (prpSeriaNo != null) {
			for (int i = 1; i < prpSeriaNo.length; i++) {
				prpLrelatePerson = new PrpLrelatePerson();
				prpLrelatePerson.getId().setRegistNo(personRegistNo);
				prpLrelatePerson.getId().setSerialNo(new BigDecimal(prpSeriaNo[i]));
				prpLrelatePerson.getId().setPersonType("Link");
				prpLrelatePerson.setPersonName(prpPersonName[i]);
				prpLrelatePerson.setPhoneNumber(prpPhoneNumber[i]);
				prpLrelatePerson.setMobile(prpMobile[i]);
				prpLrelatePerson.setRemark(prpRemark[i]);
				prpLrelatePerson.setPolicyNo(strPolicyNo);
				prpLrelatePerson.setPersonCode("");
				prpLrelatePerson.setFlag("");
				// 加入集合中
				relatePersonList.add(prpLrelatePerson);
			}
			// 报案集合中联系人信息
			registDto.setPrpLrelatePersonList(relatePersonList);
		}
		// 处理报案登记,当"第三者亡人数" "第三者伤人数" "车上人员亡人数"
		// "车上人员伤人数"四个字段其中任意一个没有填值,包括0也不填的情况下,点击"提交"报错
		String personDeathB = httpServletRequest.getParameter("prpLregistPersonDeathB");//JSON沒有[人傷跟蹤-第三者亡人數]
		if (personDeathB == null || personDeathB.equals("")) {
			personDeathB = "0";
		}
		String personInjureB = httpServletRequest.getParameter("prpLregistPersonInjureB");//JSON沒有[人傷跟蹤-第三者傷人數]
		if (personInjureB == null || personInjureB.equals("")) {
			personInjureB = "0";
		}
		String personDeathD1 = httpServletRequest.getParameter("prpLregistPersonDeathD1");//JSON沒有[人傷跟蹤-車上人員亡人數]
		if (personDeathD1 == null || personDeathD1.equals("")) {
			personDeathD1 = "0";
		}
		String personInjureD1 = httpServletRequest.getParameter("prpLregistPersonInjureD1");//JSON沒有[人傷跟蹤-車上人員傷人數]
		if (personInjureD1 == null || personInjureD1.equals("")) {
			personInjureD1 = "0";
		}
		String lregistValue1 = (String) httpServletRequest.getAttribute("prpLregistValue1");
		String lregistValue2 = (String) httpServletRequest.getAttribute("prpLregistValue2");
		if ("D".equals(strRiskType)){
			PrpLext prpLext = new PrpLext();
			String certiNo = (String) httpServletRequest.getAttribute("registNo");
			prpLext.getId().setCertiNo(certiNo);
			prpLext.getId().setCertiType("01");
			prpLext.setPersonDeathB(Integer.parseInt(personDeathB));
			prpLext.setPersonInjureB(Integer.parseInt(personInjureB));
			prpLext.setPersonDeathD1(Integer.parseInt(personDeathD1));
			prpLext.setPersonInjureD1(Integer.parseInt(personInjureD1));
			prpLext.setValue1(lregistValue1);
			prpLext.setValue2(lregistValue2);
			registDto.setPrpLext(prpLext);
		}
		// 整理调度用的标的信息,去掉最後的一个"/"
		if (registDto.getPrpLregist().getScheduleItemNote().length() > 1) {
			String strTemp = registDto.getPrpLregist().getScheduleItemNote();
			strTemp = strTemp.substring(0, strTemp.length() - 1);
			registDto.getPrpLregist().setScheduleItemNote(strTemp);
		}
		// 收集callcenter补充信息
		String callCenterInfo = (String) httpServletRequest.getAttribute("callCenterInfo");
		ArrayList<PrpLregistText> prpLregistTextList2 = new ArrayList<PrpLregistText>();
		String[] rules2 = StringUtils.split(callCenterInfo, RULE_LENGTH);
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules2.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getAttribute("registNo"));
			prpLregistText.setContext(rules2[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("5");// 5表示95519补充报案信息
			prpLregistTextList2.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList2(prpLregistTextList2);
		return registDto;

	}
	
	/**
	 * 取初始化信息需要的数据的整理. 填写报案单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public RegistDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		RegistDto registDto = new RegistDto();
		return registDto;
	}

	/**
	 * 填写报案页面及查询报案request的生成.
	 * 填写报案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, RegistDto registDto) throws Exception {
		// 得到request的PrpLregistForm用於显示
		PrpLregist prpLregist = registDto.getPrpLregist();
		// 设置客户类型
		if (!prpLregist.getInsuredCode().equals("")) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		// 得到request的prpLthirdPartyForm用於显示
		List<PrpLthirdParty> prpLthirdPartyList = registDto.getPrpLthirdPartyList();
		httpServletRequest.setAttribute("prpLthirdPartyList", prpLthirdPartyList);
		// 得到request的prpLdriverForm用於显示
		List<PrpLdriver> prpLdriverList = registDto.getPrpLdriverList();
		httpServletRequest.setAttribute("prpLdriverList", prpLdriverList);
		// 得到request的prpLregistTextForm用於显示
		List<PrpLregistText> prpLregistTextList = registDto.getPrpLregistTextList();
		httpServletRequest.setAttribute("prpLregistTextList", prpLregistTextList);
	}
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 根据备案号获取报案数据
	 * @param httpServletRequest
	 * @param registNo
	 * @throws Exception
	 */
	public void setRegistDtoView4Ws(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		RegistDto registDto = null;
		String flushflag = (String)httpServletRequest.getAttribute("flushflag");
		if (flushflag != null) {//修改出险时间,保留修改部分前的数据。
			registDto = this.viewToDto(httpServletRequest);
		} else {
			registDto = this.getRegistService().findByPrimaryKey(registNo);
		}
		this.setRegistDtoView(httpServletRequest, registNo, registDto);
	}
	
	/**
	 * 
	 * 根据备案号获取报案数据
	 * @param httpServletRequest
	 * @param registNo
	 * @throws Exception
	 */
	public void setRegistDtoView(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		RegistDto registDto = null;
		if (httpServletRequest.getParameter("flushflag") != null) {//修改出险时间,保留修改部分前的数据。
			registDto = this.viewToDto(httpServletRequest);
		} else {
			registDto = this.getRegistService().findByPrimaryKey(registNo);
		}
		this.setRegistDtoView(httpServletRequest, registNo, registDto);
	}

	private void setRegistDtoView(HttpServletRequest httpServletRequest, String registNo, RegistDto registDto) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		// 给registForm赋值
		DateTime.setDateDelimiter("-");
		PrpLregist prpLregist = registDto.getPrpLregist();
		String policyNo = prpLregist.getPolicyNo();
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		// 缴费标志
		prpLregist.setPayFlag(String.valueOf(intPayFee));
		prpLregist.setReportHour(StringConvert.toStandardTime(prpLregist.getReportHour()));
		prpLregist.setReportMinute(prpLregist.getReportHour().substring(3, 5));
		prpLregist.setReportHour(prpLregist.getReportHour().substring(0, 2));
		prpLregist.setDamageStartHour(StringConvert.toStandardTime(prpLregist.getDamageStartHour()));
		prpLregist.setDamageStartMinute(prpLregist.getDamageStartHour().substring(3, 5));
		prpLregist.setDamageStartHour(prpLregist.getDamageStartHour().substring(0, 2));
		DateTime systemFlowInTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		prpLregist.setFlowInTime(systemFlowInTime);
		// 根据保单号查询保单信息
		// 当缴费不足时,要显示相应的缴费情况
		String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
		String damageHour = prpLregist.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intPayFee == 0 && prpCmain.getPayTimes() > 1) {
			delinquentfeeCase = getDelinquentfeeCase(prpCmain);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
		String riskCode = prpCmain.getRiskCode();
		String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
		String insuredCode;
		String insuredName;
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			insuredCode = prpLregist.getInsuredCode();
			insuredName = prpLregist.getInsuredName();
		}  else {
			insuredCode = prpCmain.getInsuredCode();
			insuredName = prpCmain.getInsuredName();
		}
		List<PrpCinsured> prpCinsuredList = null;
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
		}
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
		if (prpCinsured != null) {
			prpLregist.setPolicyInsuredPhoneNumber(prpCinsured.getPhoneNumber());
			prpLregist.setPolicyInsuredMobile(prpCinsured.getMobile());
			insuredCode = prpCinsured.getInsuredCode() == null ? prpCinsured.getIdentifyNumber() : prpCinsured.getInsuredCode();
			insuredName = prpCinsured.getInsuredName();
			httpServletRequest.setAttribute("prpCinsured", prpCinsured);
		}
		prpLregist.setInsuredCode(insuredCode);
		prpLregist.setInsuredName(insuredName);
		List<PrpPhead> prpPheadList = this.endorseViewHelper.findPrpPhead(policyNo, damageDate, damageHour);
		List<PrpCitemKind> prpCitemKindList = null;
		if(ConstantCodes.CLASSCODE_E.equals(riskType) && prpCinsured != null){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(prpPheadList , policyNo, riskCode, null);
		}
		// 设置驾照号码
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCcarDriver> prpCcarDriverList = this.prpCcarDriverService.findPrpCcarDriver(queryRule);
		for (PrpCcarDriver temp : prpCcarDriverList) {
			if (DataUtils.emptyToNull(insuredName) != null && insuredName.equals(temp.getDriverName())) {
				prpLregist.setPolicyInsuredLicenseNumber(temp.getDrivingLicenseNo());// 固定驾驶员驾照
				break;
			}
		}
		policyNo = prpLregist.getPolicyNo();
		/* 20151029 DELETE BY 中科軟  TA優化處理  BEGIN **/
		// 将insured,itemkind信息放到session中,意外险用
		session.setAttribute("prpcinsuredList", prpCinsuredList);
		session.setAttribute("prpcitemkindList", prpCitemKindList);
		/* 20151029 DELETE BY 中科軟  TA優化處理  END **/
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(prpPheadList, policyNo);
		registDto.setPrpCengageList(prpCengageList);
		prpLregist.setPolicyNo(policyNo);
		prpLregist.setHandler1Code(prpCmain.getHandler1Code());
		prpLregist.setStartDate(new DateTime(prpCmain.getStartDate()).toString());
		prpLregist.setStartHour(prpCmain.getStartHour());
		prpLregist.setEndDate(prpCmain.getEndDate().toString());
		prpLregist.setEndHour(prpCmain.getEndHour());
		// 设置签单日期
		prpLregist.setSignDate(prpCmain.getSignDate());
		prpLregist.setOthFlag(prpCmain.getOthFlag());
		prpLregist.setUnderWriteEndDate(prpCmain.getUnderwriteEndDate());
		// 增加共保字段
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		prpLregist.setComCode(prpCmain.getComCode());
		prpLregist.setInsuredCode(prpLregist.getInsuredCode());
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			String sameAddressNo = prpCaddressList.get(0).getSameAddressNo();
			prpLregist.setSameAddressNo(sameAddressNo);
		}
		// 被保险人显示****等**人
		String strInsuredName = prpLregist.getInsuredName();
		prpLregist.setInsuredName(strInsuredName);
		prpLregist.setInsuredNameShow(strInsuredName);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setSumAmount(prpCmain.getSumAmount());
		prpLregist.setEstiCurrencyName(codeService.translateCurrencyCode(prpLregist.getEstiCurrency(), true));
		String agentCode = "";
		if (prpCmain != null) {
			agentCode = prpCmain.getAgentCode(); // 代理人代码
		}
		prpLregist.setAgentCode(agentCode);
		prpLregist.setAgentName(codeService.translateAgentName(agentCode));// 得到代理人名称
		// 设置报案操作的状态为 案件已提交
		if (registDto.getPrpLclaimStatus() != null) {
			if (registDto.getPrpLclaimStatus().getStatus().equals("7"))
				registDto.getPrpLclaimStatus().setStatus("3");
			prpLregist.setStatus(registDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLregist.setStatus("4");
		}
		// 查询保单信息
		String strTemp = "";
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (PrpCitemKind prpCitemKind : prpCitemKindList) {
				strTemp = strTemp + "," + prpCitemKind.getKindCode();
			}
		}
		prpLregist.setReferKind(strTemp);
		// reason:加入保险标的信息的内容，界面上可以直接显示承保险别
		registDto.setPrpCitemKindList(prpCitemKindList);
		// Reason:人伤跟踪信息模块中涉及险种以列表框多选形式显示
		// 将险别名称改成D1-车上人员责任险的方式
		String qsFlag = (String) httpServletRequest.getAttribute("qsFlag");

		if (qsFlag != null && "Y".equals(qsFlag)) {
			String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
			List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, null, null);
			PrpCitemKind prpCitemKind_qs = itemKindList_qs.get(0);
			prpCitemKindList.add(prpCitemKind_qs);
		}
		httpServletRequest.setAttribute("itemKindList", prpCitemKindList);
		// 筛选出可对人伤进行赔付的险别，且去重
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = null;
		for (PrpCitemKind temp : prpCitemKindList) {
			if ("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode())) && !ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())) {
				continue;
			}
			prpCitemKind = new PrpCitemKind();
			BeanUtils.copyProperties(prpCitemKind, temp);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			referKindList.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		httpServletRequest.setAttribute("prpCmain", prpCmain);
		// 出险原因、事故原因按照优先级别排序
		// 分别黙认显示为碰撞、疏忽大意、措施不当
		prpLregist.setPrpLregistDamageCode(prpLregist.getDamageCode());
		prpLregist.setPrpLregistDamageTypeCode(prpLregist.getDamageTypeCode());
		httpServletRequest.setAttribute("damageCodeList", ICollections.getDamageCodeList());
		httpServletRequest.setAttribute("damageTypeCodeList", ICollections.getDamageTypeCodeList());
		httpServletRequest.setAttribute("indemnityDutyList", ICollections.getIndemnityDutyList());
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		PrpLext prpLext = registDto.getPrpLext();
		if (prpLext != null) {
			prpLregist.setCertiNo(prpLext.getId().getCertiNo());
			prpLregist.setCertiType(prpLext.getId().getCertiType());
			prpLregist.setPersonDeathB(prpLext.getPersonDeathB());
			prpLregist.setPersonInjureB(prpLext.getPersonInjureB());
			prpLregist.setPersonDeathD1(prpLext.getPersonDeathD1());
			prpLregist.setPersonInjureD1(prpLext.getPersonInjureD1());
			prpLregist.setLextValue1(prpLext.getValue1());
			prpLregist.setLextValue2(prpLext.getValue2());
		}
		httpServletRequest.setAttribute("prpLrelatePersonList", registDto.getPrpLrelatePersonList());
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "regis";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);
		// 设置客户类型
		if (!CommonUtils.isEmpty(prpLregist.getInsuredCode())) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		// 在界面上显示险种名称
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLregist.getRiskCode(), true));
		// 给三者车辆多行列表准备数据
		List<PrpLthirdParty> thirdPartyList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		thirdPartyList = registDto.getPrpLthirdPartyList();
		// 整理调度信息的
		ScheduleItemDtoToView(thirdPartyList, registDto.getPrpLscheduleItemList());
		prpLthirdParty.setThirdPartyList(thirdPartyList);
		prpLthirdParty.setNodeType("regis");
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		// 给驾驶员多行多行列表准备数据
		List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
		PrpLdriver prpLdriver = new PrpLdriver();
		arrayListDriver = registDto.getPrpLdriverList();
		prpLdriver.setDriverList(arrayListDriver);
		httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {
			// 原因：增加货运险保单信息
			PrpCmainCargo prpCmain_cargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			// reason:增加进行运输工具的转换
			if (prpCmain_cargo != null) {
				prpCmain_cargo.setConveyance(codeService.translateCodeCode("TransMode", prpCmain_cargo.getConveyance(), true));
			}
			httpServletRequest.setAttribute("prpCmain_cargo", prpCmain_cargo);
		}
		// 计算出险次数
		getSamePolicyRegistInfo(httpServletRequest, policyNo, registNo);
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimList", registClaimList);
		// 转换名称代码
		changeCodeToName(httpServletRequest, prpLregist);
		// 设置各个列表和下拉框的选择信息的
		setSelectionList(httpServletRequest, prpLregist);
		// 设置各个子表的信息
		setSubInfo(httpServletRequest, registDto);
		prpLregist.setOthFlag(prpCmain.getOthFlag());
		prpLregist.setSignDate(prpCmain.getSignDate());
		httpServletRequest.setAttribute("prpLacciPerson", registDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : registDto.getPrpLacciPerson());
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest, prpLregist.getRiskCode(), prpLregist.getComCode());
		// 意健险在提交前，所有的申请调查应该已经提交
		String strRiskType = codeService.translateRiskCodetoRiskType(prpLregist.getRiskCode());
		if ("E".equals(strRiskType)) {
			UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
			int intCount = 0; // 没有提交的申请调查数目
			String strFlowID = httpServletRequest.getParameter("swfLogFlowID");
			String strSql = " FLOWID='" + strFlowID + "' and NODETYPE='check' and NODESTATUS<>'4'";
			intCount = uiWorkFlowAction.findNodesByConditions(strSql).size();
			httpServletRequest.setAttribute("com_sinosoft_acciFlag", intCount > 0 ? "N" : "Y"); // 设置一个标志位：N表示不能提交，Y表示可以提交。
		}
		// 增加注销/拒赔原因
		List<PrpLregistText> prpLregistTextList = registDto.getPrpLregistTextList();
		StringBuffer context = new StringBuffer();
		StringBuffer callCenterInfo = new StringBuffer();
		if (prpLregistTextList != null) {
			Iterator<PrpLregistText> iterator = prpLregistTextList.iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistText = (PrpLregistText) iterator.next();
				if ("2".equals(prpLregistText.getId().getTextType())) {
					context.append("  ");
					context.append(prpLregistText.getContext());
					context.append("\t");
				}
				if ("5".equals(prpLregistText.getId().getTextType())) {
					callCenterInfo.append(prpLregistText.getContext());
				}
			}
		}
		httpServletRequest.setAttribute("strContext", context.toString());
		httpServletRequest.setAttribute("callCenterInfo", callCenterInfo.toString());
		httpServletRequest.setAttribute("bLNo", prpLregist.getBrandName());
		// 增加历史报案修改人相关信息
		List<PrpLregist> modifyInfoList = new ArrayList<PrpLregist>();
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", prpLregist.getRegistNo());
		queryRule.addIsNotNull("altername");
		queryRule.addAscOrder("altername");
		List<PrpLregistLog> prpLregistLogList = prpLregistLogService.findByQuery(queryRule);
		PrpLregist prpLregisttemp = null;
		if (prpLregistLogList != null && prpLregistLogList.size() > 0) {
			for (int i = 0; i < prpLregistLogList.size(); i++) {
				prpLregisttemp = new PrpLregist();
				PrpLregistLog prpLregistLog = new PrpLregistLog();
				prpLregistLog = prpLregistLogList.get(i);
				prpLregisttemp.setAlterName(prpLregistLog.getAltername());
				prpLregisttemp.setAlterPhoneNumber(prpLregistLog.getAlterphonenumber());
				prpLregisttemp.setAlterRelationType(prpLregistLog.getAlterRelationType());
				prpLregisttemp.setAlterTime(new DateTime(prpLregistLog.getAlterTime(), DateTime.YEAR_TO_SECOND));
				prpLregisttemp.setAlterLocus(prpLregistLog.getAlterLocus().replaceAll("；", "\n"));// 格式美化
				modifyInfoList.add(prpLregisttemp);
			}
		}
		if ((!"".equals(prpLregist.getAlterName()) && (prpLregist.getAlterName() != null))) {
			modifyInfoList.add(prpLregist);
		}
		httpServletRequest.setAttribute("modifyInfoList", modifyInfoList);
		// 非见费出单的分期缴费业务判断是否已做过保单停效批改 start
		String endorType = "";
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(queryRule);
		if (prpCplanList != null && prpCplanList.size() > 1) {
			Collection<?> prpPheadList2 = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "' and ValidDate<=to_date('" + prpLregist.getDamageStartDate() + "','yyyy-MM-dd') and ValidHour<='" + prpLregist.getDamageStartHour()
					+ "' and UnderWriteFlag in ('1','3')");
			if (prpPheadList2 != null && prpPheadList2.size() != 0) {
				Iterator<?> iterator = prpPheadList2.iterator();
				while (iterator.hasNext()) {
					PrpPhead prpPhead = (PrpPhead) iterator.next();
					if (prpPhead.getEndorType().equals(ConstantCodes.EndorseType_54)) {// 保单停效
						endorType = ConstantCodes.EndorseType_54;
					}
				}
			}
		}
		httpServletRequest.setAttribute("endorType", endorType);
		// 非见费出单的分期缴费业务判断是否已做过保单停效批改 end
	}

	/**
	 * 填写报案页面及查询报案request的生成.
	 * 填写报案时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void policyDtoToView(HttpServletRequest httpServletRequest, String policyNo, String damageDate, String damageHour) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 查询保单信息
//		// 根据出险时间找到保单
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		String prpInsuredPhoneNumber = httpServletRequest.getParameter("prpInsuredPhoneNumber");
		String riskCode = prpCmain.getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		String flushflag = "0";// 刷新标记【修改出险时间，会触发刷新页面事件，重新获取出险时保单讯息，需要做特殊处理的均以此标记为依据】
		if (!CommonUtils.isEmpty(prpInsuredPhoneNumber)) {// 通过页面获取的"被保險人電話"判断是否是刷新的页面
			flushflag = "1";
			httpServletRequest.setAttribute("flushflag", flushflag);
		}
		String strInsuredCode = httpServletRequest.getParameter("insuredCode");
//		strInsuredCode = null ;// add by 中科軟  被保險人重新從保單獲取
		String strInsuredName = httpServletRequest.getParameter("insuredName");
		if(CommonUtils.isEmpty(strInsuredCode)){
			strInsuredCode = prpCmain.getInsuredCode();
			strInsuredName = prpCmain.getInsuredName();
		}
		List<PrpCinsured> prpCinsuredList = null;
		String endorseNo = this.endorseViewHelper.getEndorseNo(policyNo, damageDate, damageHour);
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType)){
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo, strInsuredCode, strInsuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo);
		}
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, strInsuredCode, strInsuredName);
		int[] serialnos = this.endorseViewHelper.getPrpCinsuredSerialNos(prpCinsuredList);
		List<PrpCinsuredNature> prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(endorseNo, policyNo, serialnos);
		PrpCinsuredNature prpCinsuredNature = this.endorseViewHelper.getPrpCinsuredNature(prpCinsuredNatureList, prpCinsured.getId().getSerialNo());
		if(prpCinsured != null){
			strInsuredCode = prpCinsured.getInsuredCode() == null ? prpCinsured.getIdentifyNumber() : prpCinsured.getInsuredCode();
			strInsuredName = prpCinsured.getInsuredName();
		}
		List<PrpCitemKind> prpCitemKindList = null;
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType) && prpCinsured != null){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, riskCode, null);
		}
		/* 20151029 DELETE BY 中科軟  TA優化處理  BEGIN **/
		// 将insured,itemkind信息放到session中,意外险用
		session.setAttribute("prpcinsuredList", prpCinsuredList);
		session.setAttribute("prpcitemkindList", prpCitemKindList);
		/* 20151029 DELETE BY 中科軟   TA優化處理  END **/
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		// 当缴费不足时,要显示相应的缴费情况
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intPayFee == 0 && prpCmain.getPayTimes() > 1) {
			delinquentfeeCase = getDelinquentfeeCase(prpCmain);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
		// 当缴费不足时,要显示相应的缴费情况
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimList", registClaimList);
		Collection<?> prpPheadList = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "'");
		httpServletRequest.setAttribute("prpPheadList", prpPheadList);
		// 非见费出单的分期缴费业务判断是否已做过保单停效批改
		String endorType = "";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(queryRule);
		if (prpCplanList != null && prpCplanList.size() > 0 ) {
			Collection<?> prpPheadList2 = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "' and ValidDate<=to_date('" + damageDate + "','yyyy-MM-dd') and ValidHour<='" + damageHour + "' and UnderWriteFlag in ('1','3')");
			if (prpPheadList2 != null && prpPheadList2.size() != 0) {
				Iterator<?> iterator = prpPheadList2.iterator();
				while (iterator.hasNext()) {
					PrpPhead prpPhead = (PrpPhead) iterator.next();
					if (prpPhead.getEndorType().equals(ConstantCodes.EndorseType_54)) {// 保单停效
						endorType = ConstantCodes.EndorseType_54;
					}
				}
			}
		}
		httpServletRequest.setAttribute("endorType", endorType);
		httpServletRequest.setAttribute("prpCmain", prpCmain);
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType)) {
			httpServletRequest.setAttribute("liabStartDate", this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo).toString());
		}
		// 增加共保字段
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		// 原因：要在界面上判断追溯期
		// 保存共保、是否股东信息 、临分信息
		DateTime damageDate1 = new DateTime(damageDate, DateTime.YEAR_TO_DAY);
		String tempReinsFlag = "0";
		try {
			tempReinsFlag = reinsServiceManager.getReinsService().getSumFacShare(policyNo, damageDate1) > 0 ? "1" : "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("tempReinsFlag", tempReinsFlag);
		// 给registForm赋值
		DateTime.setDateDelimiter("-");
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setPayFlag(String.valueOf(intPayFee));
		prpLregist.setPolicyNo(policyNo);
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			PrpCaddress prpCaddress = prpCaddressList.get(0);
			String sameAddressNo = prpCaddress.getSameAddressNo();
			prpLregist.setSameAddressNo(sameAddressNo);
			if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) && prpCaddress.getAddressDetailInfo() != null) {
				String damageAddress = prpCaddress.getAddressDetailInfo();
				prpLregist.setAddressCode(prpCaddress.getAddressCode());
				prpLregist.setAddressName(prpCaddress.getAddressName());
				prpLregist.setDamageAddress(damageAddress);
				prpLregist.setLinkerAddress(damageAddress);
			}
		}
		prpLregist.setClassCode(prpCmain.getClassCode());
		prpLregist.setRiskCode(riskCode);
		if (ConstantCodes.RISKCODE_MC.equals(riskCode)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			PrpCmainCarGoSub prpCmainCarGoSub = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(policyNo, 1);
			if(prpCmainCarGoSub != null){
				prpLregist.setShipCName(prpCmainCarGoSub.getSiteName());
			}
			if (prpCmainCargo != null) {
				prpLregist.setClaimAgent(prpCmainCargo.getCheckAgentCode());
			}
		} else if (ConstantCodes.RISKCODE_OH.equals(riskCode) || ConstantCodes.RISKCODE_EV.equals(riskCode) || ConstantCodes.RISKCODE_FV.equals(riskCode) || ConstantCodes.RISKCODE_EW.equals(riskCode) || ConstantCodes.RISKCODE_FW.equals(riskCode)) {
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLregist.setShipCName(prpCitemShip.getShipCName());
			}
		}
		if (ConstantCodes.RISKCODE_AV.equals(riskCode) ) {
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLregist.setShipModel(prpCplane.getPlaneType());
			}
		}
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType) || ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程、责任险
			prpLregist.setDamageAddress(prpCmain.getInsuredAddress());
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			prpLregist.setLinkerAddress(prpCmain.getInsuredAddress());
		}
		prpLregist.setReceiverCode(user.getUserCode());
		prpLregist.setReceiverName(user.getUserName());
		prpLregist.setSumAmount(prpCmain.getSumAmount());
		// 设置签单日期
		prpLregist.setSignDate(prpCmain.getSignDate());
		prpLregist.setOthFlag(prpCmain.getOthFlag());
		prpLregist.setUnderWriteEndDate(prpCmain.getUnderwriteEndDate());
		// 设置默认现场为非第一现场
		prpLregist.setFirstSiteFlag("0");
		// 得到归属业务员
		String handler1Code = prpCmain.getHandler1Code();
		prpLregist.setHandler1Code(handler1Code);
		// 设置报案操作的状态为 新案件登记 (未处理任务)
		prpLregist.setStatus("1");
		// 设置归属业务机构
		String comCode = prpCmain.getComCode();
		prpLregist.setComCode(comCode);
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				prpLregist.setDamageAddress(prpCmainCargo.getEndSiteName());
			}
		}
		prpLregist.setStartDate(new DateTime(prpCmain.getStartDate()).toString());
		prpLregist.setStartHour(prpCmain.getStartHour());
		prpLregist.setEndDate(prpCmain.getEndDate().toString());
		prpLregist.setEndHour(prpCmain.getEndHour());
		// 设置币别
		String estiCurrency = prpCmain.getCurrency();
		prpLregist.setEstiCurrency(estiCurrency);
		prpLregist.setEstiCurrencyName(codeService.translateCurrencyCode(prpLregist.getEstiCurrency(), true));
		// 原来忘记的 必须非空的
		prpLregist.setLanguage(prpCmain.getLanguage());
		prpLregist.setMakeCom(user.getComCode());
		prpLregist.setHandlerCode(user.getUserCode());
		prpLregist.setOperatorCode(user.getUserCode());
		prpLregist.setMakeComName(user.getComName());
		prpLregist.setOperatorName(user.getUserName());
		DateTime systemFlowInTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		prpLregist.setFlowInTime(systemFlowInTime);
		// 获取报案出险延期天数
//		String configValue = prpDriskConfigService.getConfigValue("REPORT_DEFER_DAYS", prpLregist.getRiskCode());
//		if (configValue == null || configValue.equals("")) {
//			throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpLregist.getRiskCode() + "'報案出險延期天數'的初始化！");
//		}
//		httpServletRequest.setAttribute("configValue", configValue);
		// 取得理赔登记机构信息
		prpLregist.setInsuredCode(strInsuredCode);
		prpLregist.setInsuredName(strInsuredName);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 初始化报案日期，出险日期
		prpLregist.setReportDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setReportHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setReportMinute(String.valueOf(DateTime.current().getMinute()));
		// 货运险出险时间缺省为启运日
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			prpLregist.setDamageStartDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
		} else {
			if (!ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程险無需預設當前時間
				prpLregist.setDamageStartDate(new DateTime(damageDate, DateTime.YEAR_TO_DAY));
				prpLregist.setDamageStartHour(damageHour);
			} else {
				prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
			}
		}
		prpLregist.setDamageStartMinute(String.valueOf(DateTime.current().getMinute()));
		prpLregist.setDamageEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setDamageEndHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setLflag("L");
		// (1)归属业务员名称的转换
		String handler1Code2 = prpLregist.getHandler1Code();
		String handler1Name = codeService.translateUserCode(handler1Code2, true);
		prpLregist.setHandler1Name(handler1Name);
		// (2)归属业务机构的转换
		String comCode2 = prpLregist.getComCode();
		String comName = codeService.translateComCode(comCode2, true);
		prpLregist.setComName(comName);
		String agentCode = "";
		if (prpCmain != null) {
			agentCode = prpCmain.getAgentCode(); // 代理人代码
		}
		prpLregist.setAgentCode(agentCode);
		prpLregist.setAgentName(codeService.translateAgentName(agentCode));// 得到代理人名称
		// 默认的是否受理是Y,投其他公司的是N
		prpLregist.setAcceptFlag("Y");
		prpLregist.setRepeatInsureFlag("N");
		// 设置立案操作的状态为 案件修改 (正处理任务)
		prpLregist.setStatus("1");
		// 给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		PrpLthirdParty prpLthirdPartyList = new PrpLthirdParty();
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				PrpCitemCar PrpCitemCar = (PrpCitemCar) prpCitemCarList.get(0);
				prpLregist.setClauseType(PrpCitemCar.getClauseType());
				prpLregist.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLregist.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLregist.setCarKindCode(PrpCitemCar.getCarKindCode());
				prpLregist.setEngineNo(PrpCitemCar.getEngineNo());
				prpLregist.setFrameNo(PrpCitemCar.getFrameNo());
				prpLregist.setRunDistance(PrpCitemCar.getRunMiles());
				prpLregist.setUseYears(PrpCitemCar.getUseYears());
				prpLregist.setBrandName(PrpCitemCar.getBrandName());
				prpLregist.setModelCode(PrpCitemCar.getModelCode());
				// 三者车辆
				prpLthirdPartyList.getId().setSerialNo(1);
				prpLthirdPartyList.setClauseType(prpLregist.getClauseType());
				prpLthirdPartyList.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLthirdPartyList.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLthirdPartyList.setEngineNo(PrpCitemCar.getEngineNo());
				prpLthirdPartyList.setFrameNo(PrpCitemCar.getFrameNo());
				prpLthirdPartyList.setRunDistance(PrpCitemCar.getRunMiles());
				prpLthirdPartyList.setUseYears(PrpCitemCar.getUseYears());
				prpLthirdPartyList.setBrandName(PrpCitemCar.getBrandName());
				prpLthirdPartyList.setModelCode(PrpCitemCar.getModelCode());
				prpLthirdPartyList.setInsureCarFlag("1");
				// 添加VIN号
				prpLthirdPartyList.setVINNo(PrpCitemCar.getVinNo());
				prpLthirdPartyList.setInsureComCode(prpCmain.getMakeCom());
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpCmain.getMakeCom());
				if (prpDcompany != null) {
					prpLthirdPartyList.setInsureComName(prpDcompany.getComCName());
				} else {
					prpLthirdPartyList.setInsureComName("");
				}
				prpLthirdPartyList.setCarKindCode(PrpCitemCar.getCarKindCode());
				arrayList.add(prpLthirdPartyList);
			}
		}
		String strTemp = "";
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				PrpCitemKind prpCitemKindDto = prpCitemKindList.get(i);
				strTemp = strTemp + "," + prpCitemKindDto.getKindCode();
			}
		}
		prpLthirdParty.setThirdPartyList(arrayList);
		prpLregist.setReferKind(strTemp);
		// 计算出险次数
		getSamePolicyRegistInfo(httpServletRequest, policyNo, prpLregist.getRegistNo());
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 给驾驶员多行多行列表准备数据
			List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
			PrpLdriver prpLdriver = new PrpLdriver();
			PrpLdriver prpLdriverList = new PrpLdriver();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCcarDriver> prpCcarDriverList = this.prpCcarDriverService.findPrpCcarDriver(queryRule);
			if (!CommonUtils.isEmpty(prpCcarDriverList)) {
				for (PrpCcarDriver prpCcarDriver : prpCcarDriverList) {
					if ("1".equals(prpCcarDriver.getChangelessFlag())) {
						prpLdriverList.getId().setSerialNo(1);
						prpLdriverList.setDrivingLicenseNo(prpCcarDriver.getDrivingLicenseNo());
						prpLdriverList.setDriverName(prpCcarDriver.getDriverName());
						prpLdriverList.setDriverSex(prpCcarDriver.getSex());
						prpLdriverList.setIdentifyNumber(prpCcarDriver.getIdentifynumber());
						prpLdriverList.setDriverAge(new BigDecimal(prpCcarDriver.getAge()));
						prpLdriverList.setEducation("");
						prpLdriverList.setAwardLicenseOrgan(prpCcarDriver.getAwardLicenseOrgan());
						prpLdriverList.setDrivingCarType(prpCcarDriver.getDrivingCarType());
						prpLdriverList.setDriverApanage(prpCcarDriver.getDriverAddress());
						prpLdriverList.setDriverOccupation(prpCcarDriver.getBusinessSource());
						// 添加驾驶员初次领证日期
						prpLdriverList.setReceiveLicenseDate(prpCcarDriver.getAcceptLicenseDate());
						arrayListDriver.add(prpLdriverList);
					}
					if (DataUtils.emptyToNull(prpCmain.getInsuredName()) != null && prpCmain.getInsuredName().equals(prpCcarDriver.getDriverName())) {
						prpLregist.setPolicyInsuredLicenseNumber(prpCcarDriver.getDrivingLicenseNo());// 固定駕駛員駕照
					}
				}
			}
			if (CommonUtils.isEmpty(arrayListDriver)) {
				prpLdriverList = new PrpLdriver();
				prpLdriverList.getId().setSerialNo(1);
				prpLdriverList.setLicenseNo(prpLregist.getLicenseNo());
				if ("01".equals(prpCinsured.getIdentifytype())) {//设置身份证
					prpLdriverList.setIdentifyNumber(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDrivingLicenseNo(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDriverName(prpLregist.getInsuredName());
					prpLdriverList.setDriverPhone(prpCinsured.getPhoneNumber());
					prpLdriverList.setMobilePhone(prpCinsured.getMobile());
					if(prpCinsuredNature != null){
						prpLdriverList.setBirthday(prpCinsuredNature.getBirthday());
						if (prpCinsuredNature.getAge() != null) {
							prpLdriverList.setDriverAge(new BigDecimal(prpCinsuredNature.getAge()));
						}
						prpLdriverList.setDriverSex(prpCinsuredNature.getSex());
					}
				}
				arrayListDriver.add(prpLdriverList);
			}
			prpLdriver.setDriverList(arrayListDriver);
			httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		}
		if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_Y.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_G.equals(strRiskType)) {
			String itemDetiaName = "";
			PrpCitemKind prpCitemKind;
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				prpCitemKind = prpCitemKindList.get(i);
				if (!CommonUtils.isEmpty(prpCitemKind.getItemDetailName())) {
					itemDetiaName = itemDetiaName + prpCitemKind.getItemDetailName();
				}
			}
			prpLregist.setLossName(itemDetiaName.trim());
		}
		// 设置各个列表和下拉框的选择信息的
		setSelectionList(httpServletRequest, prpLregist);
		// 设置各个子表中的信息
		RegistDto registDto = new RegistDto();
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(policyNo, damageDate, damageHour);
		registDto.setPrpCengageList(prpCengageList);
		// reason:加入保险标的信息的内容，界面上可以直接显示承保险别
		registDto.setPrpCitemKindList(prpCitemKindList);
		registDto.setPrpLregist((PrpLregist) CommonUtils.convertObj(new PrpLregist(), prpLregist));
		// Reason:人伤跟踪信息模块中涉及险种以列表框多选形式显示
		String qsFlag = (String) httpServletRequest.getAttribute("qsFlag");
		if (qsFlag != null && "Y".equals(qsFlag)) {
			String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
			PrpCmain mainPrpCmain = this.endorseViewHelper.findPrpCmain(mainPolicyNo, damageDate, damageHour);
			List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, mainPrpCmain.getRiskCode(), CommonUtils.nullToEmpty(mainPrpCmain.getPolicyType()));
			PrpCitemKind prpCitemKind_qs = (PrpCitemKind) itemKindList_qs.get(0);
			prpCitemKindList.add(prpCitemKind_qs);
		}
		// 筛选出可对人伤进行赔付的险别，且去重
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = null;
		for (PrpCitemKind temp : prpCitemKindList) {
			if ("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode())) && !ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())) {
				continue;
			}
			prpCitemKind = new PrpCitemKind();
			BeanUtils.copyProperties(prpCitemKind, temp);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			referKindList.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		registDto.setPrpLscheduleMainWF(new PrpLscheduleMainWF());
		setSubInfo(httpServletRequest, registDto);
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest, prpLregist.getRiskCode(), user.getComCode());
		// 出险原因、事故原因按照优先级别排序
		// 分别黙认显示为暴雨、疏忽大意、措施不当
		prpLregist.setPrpLregistDamageCode("001");
		prpLregist.setPrpLregistDamageTypeCode("009");
		prpLregist.setDamageTypeCode("009");
		prpLregist.setDamageTypeName("疏忽大意，措施不當");
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 车险出险原因代码：默认碰撞
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
		} else if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			// 不带入默认项（需求）
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
			prpLregist.setDamageTypeCode("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
			prpLregist.setDamageTypeName("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
		}
		// mantis： CLM0097 ，處理人員：BK007 蘇哲，需求單編號：CLM0097 新核心-TA險種在備案時輔助帶入被保人資料 -start
		if (ConstantCodes.RISKCODE_ETA.equals(riskCode)) {
			//補預設
			prpLregist.setDamageCode("219"); //出險原因 219 交通工具延誤
			prpLregist.setDamageName("交通工具延誤");
			prpLregist.setDamageTypeCode("999"); //出險類型 999 其他
			prpLregist.setDamageTypeName("其他");
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			if(prpCinsured != null){
				prpLregist.setPhoneNumber(org.apache.commons.lang.StringUtils.isNotEmpty(prpCinsured.getMobile())? prpCinsured.getMobile():prpCinsured.getPhoneNumber());
			}
			prpLregist.setSendMesFlag("1");
			prpLregist.setDamageAddress("全球地區");
		}
		// mantis： CLM0097 ，處理人員：BK007 蘇哲，需求單編號：CLM0097 新核心-TA險種在備案時輔助帶入被保人資料 -end
		httpServletRequest.setAttribute("damageCodeList", ICollections.getDamageCodeList());
		httpServletRequest.setAttribute("damageTypeCodeList", ICollections.getDamageTypeCodeList());
		httpServletRequest.setAttribute("indemnityDutyList", ICollections.getIndemnityDutyList());
		// Reason:损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "regis";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		// 节点设置成报案，三者车辆
		prpLthirdParty.setNodeType("regis");
		// 设置客户类型
		if (!CommonUtils.isEmpty(prpLregist.getInsuredCode())) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		// e保通
//		List<PrpCinsured> prpCinsuredList = policyDto.getPrpCinsuredList();
		if (prpCinsuredList != null && prpCinsuredList.size() > 0) {
			for (PrpCinsured tempPrpCinsured : prpCinsuredList) {
				if (tempPrpCinsured != null && "1".equalsIgnoreCase(tempPrpCinsured.getInsuredFlag())) {
					if ("1".equals(flushflag)) {// 页面刷新时取当前页面电话号码
						prpLregist.setInsuredPhoneNumber(prpInsuredPhoneNumber);
						prpLregist.setPolicyInsuredPhoneNumber(prpInsuredPhoneNumber);
					} else {
						prpLregist.setInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
						prpLregist.setPolicyInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
					}
					prpLregist.setPolicyInsuredMobile(tempPrpCinsured.getMobile());
					if (prpCinsuredList != null) {
						prpLregist.setInsuredNameShow(strInsuredName);
					}
					if(strInsuredCode!=null){
						if(strInsuredCode.equals(tempPrpCinsured.getInsuredCode())||(tempPrpCinsured.getInsuredCode()==null&&strInsuredCode.equals(prpCinsured.getIdentifyNumber()))){
							httpServletRequest.setAttribute("prpCinsured", prpCinsured);
							break;
						}
					}else{
						httpServletRequest.setAttribute("prpCinsured", tempPrpCinsured);
						break;
					}
					
				}
			}
		}
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		httpServletRequest.setAttribute("flushflag", "1");
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLregist.getRiskCode(), true));
		
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			// 原因：增加货运险保单信息
			PrpCmainCargo prpCmain_cargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			// reason:增加进行运输工具的转换
			if (prpCmain_cargo != null) {
				httpServletRequest.setAttribute("bLNo", prpCmain_cargo.getBlNo());
				prpCmain_cargo.setConveyance(codeService.translateCodeCode("TransMode", prpCmain_cargo.getConveyance(), true));
				prpLregist.setAddressCode(prpCmain_cargo.getEndSiteCode());
				prpLregist.setDamageAddress(prpCmain_cargo.getEndSiteName());
			}
			httpServletRequest.setAttribute("prpCmain_cargo", prpCmain_cargo);
		}
		httpServletRequest.setAttribute("prpLacciPerson", registDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : registDto.getPrpLacciPerson());
	}

	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param httpServletRequest
	 * @param request
	 * @param claimExternalSourceVo
	 * @param prpCmain
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @throws Exception
	 */
	public void policyDtoToView4Ws(HttpServletRequest httpServletRequest ,ReqRegistTemp request ,ClaimExternalSourceVo claimExternalSourceVo ,PrpCmain prpCmain) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String policyNo = request.getPolicyNo();
		String damageDate = request.getDamageDate();
		String damageHour = request.getDamageHour();
		// 查询保单信息
//		// 根据出险时间找到保单
//		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		String prpInsuredPhoneNumber = (String)httpServletRequest.getAttribute("prpInsuredPhoneNumber");
		String riskCode = prpCmain.getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		String flushflag = "0";// 刷新标记【修改出险时间，会触发刷新页面事件，重新获取出险时保单讯息，需要做特殊处理的均以此标记为依据】
		if (!CommonUtils.isEmpty(prpInsuredPhoneNumber)) {// 通过页面获取的"被保險人電話"判断是否是刷新的页面
			flushflag = "1";
			httpServletRequest.setAttribute("flushflag", flushflag);
		}
		String strInsuredCode = (String)httpServletRequest.getAttribute("insuredCode");
//		strInsuredCode = null ;// add by 中科軟  被保險人重新從保單獲取
		String strInsuredName = (String)httpServletRequest.getAttribute("insuredName");
		if(CommonUtils.isEmpty(strInsuredCode)){
			strInsuredCode = prpCmain.getInsuredCode();
			strInsuredName = prpCmain.getInsuredName();
		}
		List<PrpCinsured> prpCinsuredList = null;
		String endorseNo = this.endorseViewHelper.getEndorseNo(policyNo, damageDate, damageHour);
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType)){
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo, strInsuredCode, strInsuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo);
		}
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, strInsuredCode, strInsuredName);
		int[] serialnos = this.endorseViewHelper.getPrpCinsuredSerialNos(prpCinsuredList);
		List<PrpCinsuredNature> prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(endorseNo, policyNo, serialnos);
		PrpCinsuredNature prpCinsuredNature = this.endorseViewHelper.getPrpCinsuredNature(prpCinsuredNatureList, prpCinsured.getId().getSerialNo());
		if(prpCinsured != null){
			strInsuredCode = prpCinsured.getInsuredCode() == null ? prpCinsured.getIdentifyNumber() : prpCinsured.getInsuredCode();
			strInsuredName = prpCinsured.getInsuredName();
		}
		List<PrpCitemKind> prpCitemKindList = null;
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType) && prpCinsured != null){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, riskCode, null);
		}
		/* 20151029 DELETE BY 中科軟  TA優化處理  BEGIN **/
		// 将insured,itemkind信息放到session中,意外险用
		session.setAttribute("prpcinsuredList", prpCinsuredList);
		session.setAttribute("prpcitemkindList", prpCitemKindList);
		/* 20151029 DELETE BY 中科軟   TA優化處理  END **/
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		// 当缴费不足时,要显示相应的缴费情况
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intPayFee == 0 && prpCmain.getPayTimes() > 1) {
			delinquentfeeCase = getDelinquentfeeCase(prpCmain);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
		// 当缴费不足时,要显示相应的缴费情况
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimList", registClaimList);
		Collection<?> prpPheadList = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "'");
		httpServletRequest.setAttribute("prpPheadList", prpPheadList);
		// 非见费出单的分期缴费业务判断是否已做过保单停效批改
		String endorType = "";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(queryRule);
		if (prpCplanList != null && prpCplanList.size() > 0 ) {
			Collection<?> prpPheadList2 = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "' and ValidDate<=to_date('" + damageDate + "','yyyy-MM-dd') and ValidHour<='" + damageHour + "' and UnderWriteFlag in ('1','3')");
			if (prpPheadList2 != null && prpPheadList2.size() != 0) {
				Iterator<?> iterator = prpPheadList2.iterator();
				while (iterator.hasNext()) {
					PrpPhead prpPhead = (PrpPhead) iterator.next();
					if (prpPhead.getEndorType().equals(ConstantCodes.EndorseType_54)) {// 保单停效
						endorType = ConstantCodes.EndorseType_54;
					}
				}
			}
		}
		httpServletRequest.setAttribute("endorType", endorType);
		httpServletRequest.setAttribute("prpCmain", prpCmain);
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType)) {
			httpServletRequest.setAttribute("liabStartDate", this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo).toString());
		}
		// 增加共保字段
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		// 原因：要在界面上判断追溯期
		// 保存共保、是否股东信息 、临分信息
		DateTime damageDate1 = new DateTime(damageDate, DateTime.YEAR_TO_DAY);
		String tempReinsFlag = "0";
		try {
			tempReinsFlag = reinsServiceManager.getReinsService().getSumFacShare(policyNo, damageDate1) > 0 ? "1" : "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("tempReinsFlag", tempReinsFlag);
		// 给registForm赋值
		DateTime.setDateDelimiter("-");
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setPayFlag(String.valueOf(intPayFee));
		prpLregist.setPolicyNo(policyNo);
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			PrpCaddress prpCaddress = prpCaddressList.get(0);
			String sameAddressNo = prpCaddress.getSameAddressNo();
			prpLregist.setSameAddressNo(sameAddressNo);
			if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) && prpCaddress.getAddressDetailInfo() != null) {
				String damageAddress = prpCaddress.getAddressDetailInfo();
				prpLregist.setAddressCode(prpCaddress.getAddressCode());
				prpLregist.setAddressName(prpCaddress.getAddressName());
				prpLregist.setDamageAddress(damageAddress);
				prpLregist.setLinkerAddress(damageAddress);
			}
		}
		prpLregist.setClassCode(prpCmain.getClassCode());
		prpLregist.setRiskCode(riskCode);
		if (ConstantCodes.RISKCODE_MC.equals(riskCode)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			PrpCmainCarGoSub prpCmainCarGoSub = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(policyNo, 1);
			if(prpCmainCarGoSub != null){
				prpLregist.setShipCName(prpCmainCarGoSub.getSiteName());
			}
			if (prpCmainCargo != null) {
				prpLregist.setClaimAgent(prpCmainCargo.getCheckAgentCode());
			}
		} else if (ConstantCodes.RISKCODE_OH.equals(riskCode) || ConstantCodes.RISKCODE_EV.equals(riskCode) || ConstantCodes.RISKCODE_FV.equals(riskCode) || ConstantCodes.RISKCODE_EW.equals(riskCode) || ConstantCodes.RISKCODE_FW.equals(riskCode)) {
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLregist.setShipCName(prpCitemShip.getShipCName());
			}
		}
		if (ConstantCodes.RISKCODE_AV.equals(riskCode) ) {
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLregist.setShipModel(prpCplane.getPlaneType());
			}
		}
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType) || ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程、责任险
			prpLregist.setDamageAddress(prpCmain.getInsuredAddress());
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			prpLregist.setLinkerAddress(prpCmain.getInsuredAddress());
		}
		prpLregist.setReceiverCode(user.getUserCode());
		prpLregist.setReceiverName(user.getUserName());
		prpLregist.setSumAmount(prpCmain.getSumAmount());
		// 设置签单日期
		prpLregist.setSignDate(prpCmain.getSignDate());
		prpLregist.setOthFlag(prpCmain.getOthFlag());
		prpLregist.setUnderWriteEndDate(prpCmain.getUnderwriteEndDate());
		// 设置默认现场为非第一现场
		prpLregist.setFirstSiteFlag("0");
		// 得到归属业务员
		String handler1Code = prpCmain.getHandler1Code();
		prpLregist.setHandler1Code(handler1Code);
		// 设置报案操作的状态为 新案件登记 (未处理任务)
		prpLregist.setStatus("1");
		// 设置归属业务机构
		String comCode = prpCmain.getComCode();
		prpLregist.setComCode(comCode);
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				prpLregist.setDamageAddress(prpCmainCargo.getEndSiteName());
			}
		}
		prpLregist.setStartDate(new DateTime(prpCmain.getStartDate()).toString());
		prpLregist.setStartHour(prpCmain.getStartHour());
		prpLregist.setEndDate(prpCmain.getEndDate().toString());
		prpLregist.setEndHour(prpCmain.getEndHour());
		// 设置币别
		String estiCurrency = prpCmain.getCurrency();
		prpLregist.setEstiCurrency(estiCurrency);
		prpLregist.setEstiCurrencyName(codeService.translateCurrencyCode(prpLregist.getEstiCurrency(), true));
		// 原来忘记的 必须非空的
		prpLregist.setLanguage(prpCmain.getLanguage());
		prpLregist.setMakeCom(user.getComCode());
		prpLregist.setHandlerCode(user.getUserCode());
		prpLregist.setOperatorCode(user.getUserCode());
		prpLregist.setMakeComName(user.getComName());
		prpLregist.setOperatorName(user.getUserName());
		DateTime systemFlowInTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		prpLregist.setFlowInTime(systemFlowInTime);
		// 获取报案出险延期天数
//		String configValue = prpDriskConfigService.getConfigValue("REPORT_DEFER_DAYS", prpLregist.getRiskCode());
//		if (configValue == null || configValue.equals("")) {
//			throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpLregist.getRiskCode() + "'報案出險延期天數'的初始化！");
//		}
//		httpServletRequest.setAttribute("configValue", configValue);
		// 取得理赔登记机构信息
		prpLregist.setInsuredCode(strInsuredCode);
		prpLregist.setInsuredName(strInsuredName);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 初始化报案日期，出险日期
		prpLregist.setReportDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setReportHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setReportMinute(String.valueOf(DateTime.current().getMinute()));
		// 货运险出险时间缺省为启运日
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			prpLregist.setDamageStartDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
		} else {
			if (!ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程险無需預設當前時間
				prpLregist.setDamageStartDate(new DateTime(damageDate, DateTime.YEAR_TO_DAY));
				prpLregist.setDamageStartHour(damageHour);
			} else {
				prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
			}
		}
		prpLregist.setDamageStartMinute(String.valueOf(DateTime.current().getMinute()));
		prpLregist.setDamageEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setDamageEndHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setLflag("L");
		// (1)归属业务员名称的转换
		String handler1Code2 = prpLregist.getHandler1Code();
		String handler1Name = codeService.translateUserCode(handler1Code2, true);
		prpLregist.setHandler1Name(handler1Name);
		// (2)归属业务机构的转换
		String comCode2 = prpLregist.getComCode();
		String comName = codeService.translateComCode(comCode2, true);
		prpLregist.setComName(comName);
		String agentCode = "";
		if (prpCmain != null) {
			agentCode = prpCmain.getAgentCode(); // 代理人代码
		}
		prpLregist.setAgentCode(agentCode);
		prpLregist.setAgentName(codeService.translateAgentName(agentCode));// 得到代理人名称
		// 默认的是否受理是Y,投其他公司的是N
		prpLregist.setAcceptFlag("Y");
		prpLregist.setRepeatInsureFlag("N");
		// 设置立案操作的状态为 案件修改 (正处理任务)
		prpLregist.setStatus("1");
		// 给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		PrpLthirdParty prpLthirdPartyList = new PrpLthirdParty();
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				PrpCitemCar PrpCitemCar = (PrpCitemCar) prpCitemCarList.get(0);
				prpLregist.setClauseType(PrpCitemCar.getClauseType());
				prpLregist.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLregist.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLregist.setCarKindCode(PrpCitemCar.getCarKindCode());
				prpLregist.setEngineNo(PrpCitemCar.getEngineNo());
				prpLregist.setFrameNo(PrpCitemCar.getFrameNo());
				prpLregist.setRunDistance(PrpCitemCar.getRunMiles());
				prpLregist.setUseYears(PrpCitemCar.getUseYears());
				prpLregist.setBrandName(PrpCitemCar.getBrandName());
				prpLregist.setModelCode(PrpCitemCar.getModelCode());
				// 三者车辆
				prpLthirdPartyList.getId().setSerialNo(1);
				prpLthirdPartyList.setClauseType(prpLregist.getClauseType());
				prpLthirdPartyList.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLthirdPartyList.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLthirdPartyList.setEngineNo(PrpCitemCar.getEngineNo());
				prpLthirdPartyList.setFrameNo(PrpCitemCar.getFrameNo());
				prpLthirdPartyList.setRunDistance(PrpCitemCar.getRunMiles());
				prpLthirdPartyList.setUseYears(PrpCitemCar.getUseYears());
				prpLthirdPartyList.setBrandName(PrpCitemCar.getBrandName());
				prpLthirdPartyList.setModelCode(PrpCitemCar.getModelCode());
				prpLthirdPartyList.setInsureCarFlag("1");
				// 添加VIN号
				prpLthirdPartyList.setVINNo(PrpCitemCar.getVinNo());
				prpLthirdPartyList.setInsureComCode(prpCmain.getMakeCom());
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpCmain.getMakeCom());
				if (prpDcompany != null) {
					prpLthirdPartyList.setInsureComName(prpDcompany.getComCName());
				} else {
					prpLthirdPartyList.setInsureComName("");
				}
				prpLthirdPartyList.setCarKindCode(PrpCitemCar.getCarKindCode());
				arrayList.add(prpLthirdPartyList);
			}
		}
		String strTemp = "";
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				PrpCitemKind prpCitemKindDto = prpCitemKindList.get(i);
				strTemp = strTemp + "," + prpCitemKindDto.getKindCode();
			}
		}
		prpLthirdParty.setThirdPartyList(arrayList);
		prpLregist.setReferKind(strTemp);
		// 计算出险次数
		getSamePolicyRegistInfo(httpServletRequest, policyNo, prpLregist.getRegistNo());
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 给驾驶员多行多行列表准备数据
			List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
			PrpLdriver prpLdriver = new PrpLdriver();
			PrpLdriver prpLdriverList = new PrpLdriver();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCcarDriver> prpCcarDriverList = this.prpCcarDriverService.findPrpCcarDriver(queryRule);
			if (!CommonUtils.isEmpty(prpCcarDriverList)) {
				for (PrpCcarDriver prpCcarDriver : prpCcarDriverList) {
					if ("1".equals(prpCcarDriver.getChangelessFlag())) {
						prpLdriverList.getId().setSerialNo(1);
						prpLdriverList.setDrivingLicenseNo(prpCcarDriver.getDrivingLicenseNo());
						prpLdriverList.setDriverName(prpCcarDriver.getDriverName());
						prpLdriverList.setDriverSex(prpCcarDriver.getSex());
						prpLdriverList.setIdentifyNumber(prpCcarDriver.getIdentifynumber());
						prpLdriverList.setDriverAge(new BigDecimal(prpCcarDriver.getAge()));
						prpLdriverList.setEducation("");
						prpLdriverList.setAwardLicenseOrgan(prpCcarDriver.getAwardLicenseOrgan());
						prpLdriverList.setDrivingCarType(prpCcarDriver.getDrivingCarType());
						prpLdriverList.setDriverApanage(prpCcarDriver.getDriverAddress());
						prpLdriverList.setDriverOccupation(prpCcarDriver.getBusinessSource());
						// 添加驾驶员初次领证日期
						prpLdriverList.setReceiveLicenseDate(prpCcarDriver.getAcceptLicenseDate());
						arrayListDriver.add(prpLdriverList);
					}
					if (DataUtils.emptyToNull(prpCmain.getInsuredName()) != null && prpCmain.getInsuredName().equals(prpCcarDriver.getDriverName())) {
						prpLregist.setPolicyInsuredLicenseNumber(prpCcarDriver.getDrivingLicenseNo());// 固定駕駛員駕照
					}
				}
			}
			if (CommonUtils.isEmpty(arrayListDriver)) {
				prpLdriverList = new PrpLdriver();
				prpLdriverList.getId().setSerialNo(1);
				prpLdriverList.setLicenseNo(prpLregist.getLicenseNo());
				if ("01".equals(prpCinsured.getIdentifytype())) {//设置身份证
					prpLdriverList.setIdentifyNumber(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDrivingLicenseNo(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDriverName(prpLregist.getInsuredName());
					prpLdriverList.setDriverPhone(prpCinsured.getPhoneNumber());
					prpLdriverList.setMobilePhone(prpCinsured.getMobile());
					if(prpCinsuredNature != null){
						prpLdriverList.setBirthday(prpCinsuredNature.getBirthday());
						if (prpCinsuredNature.getAge() != null) {
							prpLdriverList.setDriverAge(new BigDecimal(prpCinsuredNature.getAge()));
						}
						prpLdriverList.setDriverSex(prpCinsuredNature.getSex());
					}
				}
				arrayListDriver.add(prpLdriverList);
			}
			prpLdriver.setDriverList(arrayListDriver);
			httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		}
		if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_Y.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_G.equals(strRiskType)) {
			String itemDetiaName = "";
			PrpCitemKind prpCitemKind;
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				prpCitemKind = prpCitemKindList.get(i);
				if (!CommonUtils.isEmpty(prpCitemKind.getItemDetailName())) {
					itemDetiaName = itemDetiaName + prpCitemKind.getItemDetailName();
				}
			}
			prpLregist.setLossName(itemDetiaName.trim());
		}
		// 设置各个列表和下拉框的选择信息的
		setSelectionList(httpServletRequest, prpLregist);
		// 设置各个子表中的信息
		RegistDto registDto = new RegistDto();
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(policyNo, damageDate, damageHour);
		registDto.setPrpCengageList(prpCengageList);
		// reason:加入保险标的信息的内容，界面上可以直接显示承保险别
		registDto.setPrpCitemKindList(prpCitemKindList);
		registDto.setPrpLregist((PrpLregist) CommonUtils.convertObj(new PrpLregist(), prpLregist));
		// Reason:人伤跟踪信息模块中涉及险种以列表框多选形式显示
		String qsFlag = (String) httpServletRequest.getAttribute("qsFlag");
		if (qsFlag != null && "Y".equals(qsFlag)) {
			String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
			PrpCmain mainPrpCmain = this.endorseViewHelper.findPrpCmain(mainPolicyNo, damageDate, damageHour);
			List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, mainPrpCmain.getRiskCode(), CommonUtils.nullToEmpty(mainPrpCmain.getPolicyType()));
			PrpCitemKind prpCitemKind_qs = (PrpCitemKind) itemKindList_qs.get(0);
			prpCitemKindList.add(prpCitemKind_qs);
		}
		// 筛选出可对人伤进行赔付的险别，且去重
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = null;
		for (PrpCitemKind temp : prpCitemKindList) {
			if ("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode())) && !ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())) {
				continue;
			}
			prpCitemKind = new PrpCitemKind();
			BeanUtils.copyProperties(prpCitemKind, temp);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			referKindList.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		registDto.setPrpLscheduleMainWF(new PrpLscheduleMainWF());
		setSubInfo(httpServletRequest, registDto);
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest, prpLregist.getRiskCode(), user.getComCode());
		// 出险原因、事故原因按照优先级别排序
		// 分别黙认显示为暴雨、疏忽大意、措施不当
		prpLregist.setPrpLregistDamageCode("001");
		prpLregist.setPrpLregistDamageTypeCode("009");
		prpLregist.setDamageTypeCode("009");
		prpLregist.setDamageTypeName("疏忽大意，措施不當");
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 车险出险原因代码：默认碰撞
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
		} else if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			// 不带入默认项（需求）
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
			prpLregist.setDamageTypeCode("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
			prpLregist.setDamageTypeName("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
		}
		// mantis： CLM0097 ，處理人員：BK007 蘇哲，需求單編號：CLM0097 新核心-TA險種在備案時輔助帶入被保人資料 -start
		if (ConstantCodes.RISKCODE_ETA.equals(riskCode)) {
			//補預設
			prpLregist.setDamageCode("219"); //出險原因 219 交通工具延誤
			prpLregist.setDamageName("交通工具延誤");
			prpLregist.setDamageTypeCode("999"); //出險類型 999 其他
			prpLregist.setDamageTypeName("其他");
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			if(prpCinsured != null){
				prpLregist.setPhoneNumber(org.apache.commons.lang.StringUtils.isNotEmpty(prpCinsured.getMobile())? prpCinsured.getMobile():prpCinsured.getPhoneNumber());
			}
			prpLregist.setSendMesFlag("1");
			//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 START
			prpLregist.setDamageAddress(registDto.getPrpLregist().getDamageAddress());
			prpLregist.setAddressCode(registDto.getPrpLregist().getAddressCode());
			//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 END
		}
		// mantis： CLM0097 ，處理人員：BK007 蘇哲，需求單編號：CLM0097 新核心-TA險種在備案時輔助帶入被保人資料 -end
		httpServletRequest.setAttribute("damageCodeList", ICollections.getDamageCodeList());
		httpServletRequest.setAttribute("damageTypeCodeList", ICollections.getDamageTypeCodeList());
		httpServletRequest.setAttribute("indemnityDutyList", ICollections.getIndemnityDutyList());
		// Reason:损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "regis";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		// 节点设置成报案，三者车辆
		prpLthirdParty.setNodeType("regis");
		// 设置客户类型
		if (!CommonUtils.isEmpty(prpLregist.getInsuredCode())) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		// e保通
//		List<PrpCinsured> prpCinsuredList = policyDto.getPrpCinsuredList();
		if (prpCinsuredList != null && prpCinsuredList.size() > 0) {
			for (PrpCinsured tempPrpCinsured : prpCinsuredList) {
				if (tempPrpCinsured != null && "1".equalsIgnoreCase(tempPrpCinsured.getInsuredFlag())) {
					if ("1".equals(flushflag)) {// 页面刷新时取当前页面电话号码
						prpLregist.setInsuredPhoneNumber(prpInsuredPhoneNumber);
						prpLregist.setPolicyInsuredPhoneNumber(prpInsuredPhoneNumber);
					} else {
						prpLregist.setInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
						prpLregist.setPolicyInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
					}
					prpLregist.setPolicyInsuredMobile(tempPrpCinsured.getMobile());
					if (prpCinsuredList != null) {
						prpLregist.setInsuredNameShow(strInsuredName);
					}
					if(strInsuredCode!=null){
						if(strInsuredCode.equals(tempPrpCinsured.getInsuredCode())||(tempPrpCinsured.getInsuredCode()==null&&strInsuredCode.equals(prpCinsured.getIdentifyNumber()))){
							httpServletRequest.setAttribute("prpCinsured", prpCinsured);
							break;
						}
					}else{
						httpServletRequest.setAttribute("prpCinsured", tempPrpCinsured);
						break;
					}
					
				}
			}
		}
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		httpServletRequest.setAttribute("flushflag", "1");
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLregist.getRiskCode(), true));

		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			// 原因：增加货运险保单信息
			PrpCmainCargo prpCmain_cargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			// reason:增加进行运输工具的转换
			if (prpCmain_cargo != null) {
				httpServletRequest.setAttribute("bLNo", prpCmain_cargo.getBlNo());
				prpCmain_cargo.setConveyance(codeService.translateCodeCode("TransMode", prpCmain_cargo.getConveyance(), true));
				prpLregist.setAddressCode(prpCmain_cargo.getEndSiteCode());
				prpLregist.setDamageAddress(prpCmain_cargo.getEndSiteName());
			}
			httpServletRequest.setAttribute("prpCmain_cargo", prpCmain_cargo);
		}
		httpServletRequest.setAttribute("prpLacciPerson", registDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : registDto.getPrpLacciPerson());
	}
	
	
	/**
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 * @param httpServletRequest
	 * @param request
	 * @param claimExternalSourceVo
	 * @param prpCmain
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @throws Exception
	 */
	public void policyDtoRiskToView4Ws(HttpServletRequest httpServletRequest ,ReqRegistTemp request ,ClaimExternalRiskSourceVo claimExternalSourceVo ,PrpCmain prpCmain) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String policyNo = request.getPolicyNo();
		String damageDate = request.getDamageDate();
		String damageHour = request.getDamageHour();
		// 查询保单信息
//		// 根据出险时间找到保单
//		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
		String prpInsuredPhoneNumber = (String)httpServletRequest.getAttribute("prpInsuredPhoneNumber");
		String riskCode = prpCmain.getRiskCode();
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		String flushflag = "0";// 刷新标记【修改出险时间，会触发刷新页面事件，重新获取出险时保单讯息，需要做特殊处理的均以此标记为依据】
		if (!CommonUtils.isEmpty(prpInsuredPhoneNumber)) {// 通过页面获取的"被保險人電話"判断是否是刷新的页面
			flushflag = "1";
			httpServletRequest.setAttribute("flushflag", flushflag);
		}
		String strInsuredCode = (String)httpServletRequest.getAttribute("insuredCode");
//		strInsuredCode = null ;// add by 中科軟  被保險人重新從保單獲取
		String strInsuredName = (String)httpServletRequest.getAttribute("insuredName");
		if(CommonUtils.isEmpty(strInsuredCode)){
			strInsuredCode = prpCmain.getInsuredCode();
			strInsuredName = prpCmain.getInsuredName();
		}
		List<PrpCinsured> prpCinsuredList = null;
		String endorseNo = this.endorseViewHelper.getEndorseNo(policyNo, damageDate, damageHour);
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType)){
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo, strInsuredCode, strInsuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(endorseNo, policyNo);
		}
		PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, strInsuredCode, strInsuredName);
		int[] serialnos = this.endorseViewHelper.getPrpCinsuredSerialNos(prpCinsuredList);
		List<PrpCinsuredNature> prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(endorseNo, policyNo, serialnos);
		PrpCinsuredNature prpCinsuredNature = this.endorseViewHelper.getPrpCinsuredNature(prpCinsuredNatureList, prpCinsured.getId().getSerialNo());
		if(prpCinsured != null){
			strInsuredCode = prpCinsured.getInsuredCode() == null ? prpCinsured.getIdentifyNumber() : prpCinsured.getInsuredCode();
			strInsuredName = prpCinsured.getInsuredName();
		}
		List<PrpCitemKind> prpCitemKindList = null;
		if(ConstantCodes.CLASSCODE_E.equals(strRiskType) && prpCinsured != null){
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, riskCode, null);
		}
		/* 20151029 DELETE BY 中科軟  TA優化處理  BEGIN **/
		// 将insured,itemkind信息放到session中,意外险用
		session.setAttribute("prpcinsuredList", prpCinsuredList);
		session.setAttribute("prpcitemkindList", prpCitemKindList);
		/* 20151029 DELETE BY 中科軟   TA優化處理  END **/
		int intPayFee = this.checkPay(httpServletRequest, policyNo);
		// 当缴费不足时,要显示相应的缴费情况
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intPayFee == 0 && prpCmain.getPayTimes() > 1) {
			delinquentfeeCase = getDelinquentfeeCase(prpCmain);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
		// 当缴费不足时,要显示相应的缴费情况
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimList = claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimList", registClaimList);
		Collection<?> prpPheadList = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "'");
		httpServletRequest.setAttribute("prpPheadList", prpPheadList);
		// 非见费出单的分期缴费业务判断是否已做过保单停效批改
		String endorType = "";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCplan> prpCplanList = this.prpCplanService.findPrpCplan(queryRule);
		if (prpCplanList != null && prpCplanList.size() > 0 ) {
			Collection<?> prpPheadList2 = endorseService.findByPrpPheadConditions(" policyNo='" + policyNo + "' and ValidDate<=to_date('" + damageDate + "','yyyy-MM-dd') and ValidHour<='" + damageHour + "' and UnderWriteFlag in ('1','3')");
			if (prpPheadList2 != null && prpPheadList2.size() != 0) {
				Iterator<?> iterator = prpPheadList2.iterator();
				while (iterator.hasNext()) {
					PrpPhead prpPhead = (PrpPhead) iterator.next();
					if (prpPhead.getEndorType().equals(ConstantCodes.EndorseType_54)) {// 保单停效
						endorType = ConstantCodes.EndorseType_54;
					}
				}
			}
		}
		httpServletRequest.setAttribute("endorType", endorType);
		httpServletRequest.setAttribute("prpCmain", prpCmain);
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType)) {
			httpServletRequest.setAttribute("liabStartDate", this.prpCmainLiabService.findByPrimaryKeyStartDate(policyNo).toString());
		}
		// 增加共保字段
		httpServletRequest.setAttribute("coinsFlag", prpCmain.getCoinsFlag());
		// 原因：要在界面上判断追溯期
		// 保存共保、是否股东信息 、临分信息
		DateTime damageDate1 = new DateTime(damageDate, DateTime.YEAR_TO_DAY);
		String tempReinsFlag = "0";
		try {
			tempReinsFlag = reinsServiceManager.getReinsService().getSumFacShare(policyNo, damageDate1) > 0 ? "1" : "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("tempReinsFlag", tempReinsFlag);
		// 给registForm赋值
		DateTime.setDateDelimiter("-");
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setPayFlag(String.valueOf(intPayFee));
		prpLregist.setPolicyNo(policyNo);
		queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCaddress> prpCaddressList = this.prpCaddressService.findPrpCaddress(queryRule);
		if(!CommonUtils.isEmpty(prpCaddressList)){
			PrpCaddress prpCaddress = prpCaddressList.get(0);
			String sameAddressNo = prpCaddress.getSameAddressNo();
			prpLregist.setSameAddressNo(sameAddressNo);
			if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) && prpCaddress.getAddressDetailInfo() != null) {
				String damageAddress = prpCaddress.getAddressDetailInfo();
				prpLregist.setAddressCode(prpCaddress.getAddressCode());
				prpLregist.setAddressName(prpCaddress.getAddressName());
				prpLregist.setDamageAddress(damageAddress);
				prpLregist.setLinkerAddress(damageAddress);
			}
		}
		prpLregist.setClassCode(prpCmain.getClassCode());
		prpLregist.setRiskCode(riskCode);
		if (ConstantCodes.RISKCODE_MC.equals(riskCode)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			PrpCmainCarGoSub prpCmainCarGoSub = this.prpCmainCarGoSubService.findPrpCmainCarGoSub(policyNo, 1);
			if(prpCmainCarGoSub != null){
				prpLregist.setShipCName(prpCmainCarGoSub.getSiteName());
			}
			if (prpCmainCargo != null) {
				prpLregist.setClaimAgent(prpCmainCargo.getCheckAgentCode());
			}
		} else if (ConstantCodes.RISKCODE_OH.equals(riskCode) || ConstantCodes.RISKCODE_EV.equals(riskCode) || ConstantCodes.RISKCODE_FV.equals(riskCode) || ConstantCodes.RISKCODE_EW.equals(riskCode) || ConstantCodes.RISKCODE_FW.equals(riskCode)) {
			PrpCitemShip prpCitemShip = this.prpCitemShipService.findPrpCitemShip(new PrpCitemShipId(policyNo, 1));
			if (prpCitemShip != null) {
				prpLregist.setShipCName(prpCitemShip.getShipCName());
			}
		}
		if (ConstantCodes.RISKCODE_AV.equals(riskCode) ) {
			PrpCplane prpCplane = this.prpCplaneService.findPrpCplane(new PrpCplaneId(policyNo, 1));
			if (prpCplane != null) {
				prpLregist.setShipModel(prpCplane.getPlaneType());
			}
		}
		if (ConstantCodes.CLASSCODE_Z.equals(strRiskType) || ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程、责任险
			prpLregist.setDamageAddress(prpCmain.getInsuredAddress());
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			prpLregist.setLinkerAddress(prpCmain.getInsuredAddress());
		}
		prpLregist.setReceiverCode(user.getUserCode());
		prpLregist.setReceiverName(user.getUserName());
		prpLregist.setSumAmount(prpCmain.getSumAmount());
		// 设置签单日期
		prpLregist.setSignDate(prpCmain.getSignDate());
		prpLregist.setOthFlag(prpCmain.getOthFlag());
		prpLregist.setUnderWriteEndDate(prpCmain.getUnderwriteEndDate());
		// 设置默认现场为非第一现场
		prpLregist.setFirstSiteFlag("0");
		// 得到归属业务员
		String handler1Code = prpCmain.getHandler1Code();
		prpLregist.setHandler1Code(handler1Code);
		// 设置报案操作的状态为 新案件登记 (未处理任务)
		prpLregist.setStatus("1");
		// 设置归属业务机构
		String comCode = prpCmain.getComCode();
		prpLregist.setComCode(comCode);
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			if (prpCmainCargo != null) {
				prpLregist.setDamageAddress(prpCmainCargo.getEndSiteName());
			}
		}
		prpLregist.setStartDate(new DateTime(prpCmain.getStartDate()).toString());
		prpLregist.setStartHour(prpCmain.getStartHour());
		prpLregist.setEndDate(prpCmain.getEndDate().toString());
		prpLregist.setEndHour(prpCmain.getEndHour());
		// 设置币别
		String estiCurrency = prpCmain.getCurrency();
		prpLregist.setEstiCurrency(estiCurrency);
		prpLregist.setEstiCurrencyName(codeService.translateCurrencyCode(prpLregist.getEstiCurrency(), true));
		// 原来忘记的 必须非空的
		prpLregist.setLanguage(prpCmain.getLanguage());
		prpLregist.setMakeCom(user.getComCode());
		prpLregist.setHandlerCode(user.getUserCode());
		prpLregist.setOperatorCode(user.getUserCode());
		prpLregist.setMakeComName(user.getComName());
		prpLregist.setOperatorName(user.getUserName());
		DateTime systemFlowInTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		prpLregist.setFlowInTime(systemFlowInTime);
		// 获取报案出险延期天数
//		String configValue = prpDriskConfigService.getConfigValue("REPORT_DEFER_DAYS", prpLregist.getRiskCode());
//		if (configValue == null || configValue.equals("")) {
//			throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpLregist.getRiskCode() + "'報案出險延期天數'的初始化！");
//		}
//		httpServletRequest.setAttribute("configValue", configValue);
		// 取得理赔登记机构信息
		prpLregist.setInsuredCode(strInsuredCode);
		prpLregist.setInsuredName(strInsuredName);
		prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
		prpLregist.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		// 初始化报案日期，出险日期
		prpLregist.setReportDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setReportHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setReportMinute(String.valueOf(DateTime.current().getMinute()));
		// 货运险出险时间缺省为启运日
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			prpLregist.setDamageStartDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
		} else {
//			if (!ConstantCodes.CLASSCODE_G.equals(strRiskType)) {// 工程险無需預設當前時間//多元工程(E)還是把參數帶入
				prpLregist.setDamageStartDate(new DateTime(damageDate, DateTime.YEAR_TO_DAY));
				prpLregist.setDamageStartHour(damageHour);
//			} else {
//				prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
//			}
		}
		prpLregist.setDamageStartMinute(String.valueOf(DateTime.current().getMinute()));
		prpLregist.setDamageEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setDamageEndHour(String.valueOf(DateTime.current().getHour()));
		prpLregist.setLflag("L");
		// (1)归属业务员名称的转换
		String handler1Code2 = prpLregist.getHandler1Code();
		String handler1Name = codeService.translateUserCode(handler1Code2, true);
		prpLregist.setHandler1Name(handler1Name);
		// (2)归属业务机构的转换
		String comCode2 = prpLregist.getComCode();
		String comName = codeService.translateComCode(comCode2, true);
		prpLregist.setComName(comName);
		String agentCode = "";
		if (prpCmain != null) {
			agentCode = prpCmain.getAgentCode(); // 代理人代码
		}
		prpLregist.setAgentCode(agentCode);
		prpLregist.setAgentName(codeService.translateAgentName(agentCode));// 得到代理人名称
		// 默认的是否受理是Y,投其他公司的是N
		prpLregist.setAcceptFlag("Y");
		prpLregist.setRepeatInsureFlag("N");
		// 设置立案操作的状态为 案件修改 (正处理任务)
		prpLregist.setStatus("1");
		// 给三者车辆多行列表准备数据
		List<PrpLthirdParty> arrayList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdParty = new PrpLthirdParty();
		PrpLthirdParty prpLthirdPartyList = new PrpLthirdParty();
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			if (!CommonUtils.isEmpty(prpCitemCarList)) {
				PrpCitemCar PrpCitemCar = (PrpCitemCar) prpCitemCarList.get(0);
				prpLregist.setClauseType(PrpCitemCar.getClauseType());
				prpLregist.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLregist.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLregist.setCarKindCode(PrpCitemCar.getCarKindCode());
				prpLregist.setEngineNo(PrpCitemCar.getEngineNo());
				prpLregist.setFrameNo(PrpCitemCar.getFrameNo());
				prpLregist.setRunDistance(PrpCitemCar.getRunMiles());
				prpLregist.setUseYears(PrpCitemCar.getUseYears());
				prpLregist.setBrandName(PrpCitemCar.getBrandName());
				prpLregist.setModelCode(PrpCitemCar.getModelCode());
				// 三者车辆
				prpLthirdPartyList.getId().setSerialNo(1);
				prpLthirdPartyList.setClauseType(prpLregist.getClauseType());
				prpLthirdPartyList.setLicenseNo(PrpCitemCar.getLicenseNo());
				prpLthirdPartyList.setLicenseColorCode(PrpCitemCar.getLicenseColorCode());
				prpLthirdPartyList.setEngineNo(PrpCitemCar.getEngineNo());
				prpLthirdPartyList.setFrameNo(PrpCitemCar.getFrameNo());
				prpLthirdPartyList.setRunDistance(PrpCitemCar.getRunMiles());
				prpLthirdPartyList.setUseYears(PrpCitemCar.getUseYears());
				prpLthirdPartyList.setBrandName(PrpCitemCar.getBrandName());
				prpLthirdPartyList.setModelCode(PrpCitemCar.getModelCode());
				prpLthirdPartyList.setInsureCarFlag("1");
				// 添加VIN号
				prpLthirdPartyList.setVINNo(PrpCitemCar.getVinNo());
				prpLthirdPartyList.setInsureComCode(prpCmain.getMakeCom());
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(prpCmain.getMakeCom());
				if (prpDcompany != null) {
					prpLthirdPartyList.setInsureComName(prpDcompany.getComCName());
				} else {
					prpLthirdPartyList.setInsureComName("");
				}
				prpLthirdPartyList.setCarKindCode(PrpCitemCar.getCarKindCode());
				arrayList.add(prpLthirdPartyList);
			}
		}
		String strTemp = "";
		if (!CommonUtils.isEmpty(prpCitemKindList)) {
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				PrpCitemKind prpCitemKindDto = prpCitemKindList.get(i);
				strTemp = strTemp + "," + prpCitemKindDto.getKindCode();
			}
		}
		prpLthirdParty.setThirdPartyList(arrayList);
		prpLregist.setReferKind(strTemp);
		// 计算出险次数
		getSamePolicyRegistInfo(httpServletRequest, policyNo, prpLregist.getRegistNo());
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 给驾驶员多行多行列表准备数据
			List<PrpLdriver> arrayListDriver = new ArrayList<PrpLdriver>();
			PrpLdriver prpLdriver = new PrpLdriver();
			PrpLdriver prpLdriverList = new PrpLdriver();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<PrpCcarDriver> prpCcarDriverList = this.prpCcarDriverService.findPrpCcarDriver(queryRule);
			if (!CommonUtils.isEmpty(prpCcarDriverList)) {
				for (PrpCcarDriver prpCcarDriver : prpCcarDriverList) {
					if ("1".equals(prpCcarDriver.getChangelessFlag())) {
						prpLdriverList.getId().setSerialNo(1);
						prpLdriverList.setDrivingLicenseNo(prpCcarDriver.getDrivingLicenseNo());
						prpLdriverList.setDriverName(prpCcarDriver.getDriverName());
						prpLdriverList.setDriverSex(prpCcarDriver.getSex());
						prpLdriverList.setIdentifyNumber(prpCcarDriver.getIdentifynumber());
						prpLdriverList.setDriverAge(new BigDecimal(prpCcarDriver.getAge()));
						prpLdriverList.setEducation("");
						prpLdriverList.setAwardLicenseOrgan(prpCcarDriver.getAwardLicenseOrgan());
						prpLdriverList.setDrivingCarType(prpCcarDriver.getDrivingCarType());
						prpLdriverList.setDriverApanage(prpCcarDriver.getDriverAddress());
						prpLdriverList.setDriverOccupation(prpCcarDriver.getBusinessSource());
						// 添加驾驶员初次领证日期
						prpLdriverList.setReceiveLicenseDate(prpCcarDriver.getAcceptLicenseDate());
						arrayListDriver.add(prpLdriverList);
					}
					if (DataUtils.emptyToNull(prpCmain.getInsuredName()) != null && prpCmain.getInsuredName().equals(prpCcarDriver.getDriverName())) {
						prpLregist.setPolicyInsuredLicenseNumber(prpCcarDriver.getDrivingLicenseNo());// 固定駕駛員駕照
					}
				}
			}
			if (CommonUtils.isEmpty(arrayListDriver)) {
				prpLdriverList = new PrpLdriver();
				prpLdriverList.getId().setSerialNo(1);
				prpLdriverList.setLicenseNo(prpLregist.getLicenseNo());
				if ("01".equals(prpCinsured.getIdentifytype())) {//设置身份证
					prpLdriverList.setIdentifyNumber(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDrivingLicenseNo(prpCinsured.getIdentifyNumber());
					prpLdriverList.setDriverName(prpLregist.getInsuredName());
					prpLdriverList.setDriverPhone(prpCinsured.getPhoneNumber());
					prpLdriverList.setMobilePhone(prpCinsured.getMobile());
					if(prpCinsuredNature != null){
						prpLdriverList.setBirthday(prpCinsuredNature.getBirthday());
						if (prpCinsuredNature.getAge() != null) {
							prpLdriverList.setDriverAge(new BigDecimal(prpCinsuredNature.getAge()));
						}
						prpLdriverList.setDriverSex(prpCinsuredNature.getSex());
					}
				}
				arrayListDriver.add(prpLdriverList);
			}
			prpLdriver.setDriverList(arrayListDriver);
			httpServletRequest.setAttribute("prpLdriver", prpLdriver);
		}
		if (ConstantCodes.CLASSCODE_Q.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_Y.equals(strRiskType) 
				|| ConstantCodes.CLASSCODE_G.equals(strRiskType)) {
			String itemDetiaName = "";
			PrpCitemKind prpCitemKind;
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				prpCitemKind = prpCitemKindList.get(i);
				if (!CommonUtils.isEmpty(prpCitemKind.getItemDetailName())) {
					itemDetiaName = itemDetiaName + prpCitemKind.getItemDetailName();
				}
			}
			prpLregist.setLossName(itemDetiaName.trim());
		}
		// 设置各个列表和下拉框的选择信息的
		setSelectionList(httpServletRequest, prpLregist);
		// 设置各个子表中的信息
		RegistDto registDto = new RegistDto();
		List<PrpCengage> prpCengageList = this.endorseViewHelper.findPrpCengage(policyNo, damageDate, damageHour);
		registDto.setPrpCengageList(prpCengageList);
		// reason:加入保险标的信息的内容，界面上可以直接显示承保险别
		registDto.setPrpCitemKindList(prpCitemKindList);
		registDto.setPrpLregist((PrpLregist) CommonUtils.convertObj(new PrpLregist(), prpLregist));
		// Reason:人伤跟踪信息模块中涉及险种以列表框多选形式显示
		String qsFlag = (String) httpServletRequest.getAttribute("qsFlag");
		if (qsFlag != null && "Y".equals(qsFlag)) {
			String mainPolicyNo = (String) httpServletRequest.getAttribute("mainPolicyNo");
			PrpCmain mainPrpCmain = this.endorseViewHelper.findPrpCmain(mainPolicyNo, damageDate, damageHour);
			List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, mainPrpCmain.getRiskCode(), CommonUtils.nullToEmpty(mainPrpCmain.getPolicyType()));
			PrpCitemKind prpCitemKind_qs = (PrpCitemKind) itemKindList_qs.get(0);
			prpCitemKindList.add(prpCitemKind_qs);
		}
		// 筛选出可对人伤进行赔付的险别，且去重
		List<PrpCitemKind> referKindList = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = null;
		for (PrpCitemKind temp : prpCitemKindList) {
			if ("D".equals(ConstantCodes.carClassMap.get(temp.getRiskCode())) && !ConstantsCollection.KindCodeForPerson.contains(temp.getKindCode())) {
				continue;
			}
			prpCitemKind = new PrpCitemKind();
			BeanUtils.copyProperties(prpCitemKind, temp);
			prpCitemKind.setKindName(prpCitemKind.getKindCode() + "-" + prpCitemKind.getKindName());
			referKindList.add(prpCitemKind);
		}
		httpServletRequest.setAttribute("referKindList", referKindList);
		registDto.setPrpLscheduleMainWF(new PrpLscheduleMainWF());
		setSubInfo(httpServletRequest, registDto);
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest, prpLregist.getRiskCode(), user.getComCode());
		// 出险原因、事故原因按照优先级别排序
		// 分别黙认显示为暴雨、疏忽大意、措施不当
		prpLregist.setPrpLregistDamageCode("001");
		prpLregist.setPrpLregistDamageTypeCode("009");
		prpLregist.setDamageTypeCode("009");
		prpLregist.setDamageTypeName("疏忽大意，措施不當");
		if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
			// 车险出险原因代码：默认碰撞
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
		} else if (ConstantCodes.CLASSCODE_E.equals(strRiskType)) {
			// 不带入默认项（需求）
			prpLregist.setDamageCode("");
			prpLregist.setDamageName("");
			prpLregist.setDamageTypeCode("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
			prpLregist.setDamageTypeName("");
			// reason:事故类型：必录项
			// 不带入默认项（需求）
		}
		if (ConstantCodes.RISKCODE_ETA.equals(riskCode)) {
			//補預設
			prpLregist.setDamageCode("219"); //出險原因 219 交通工具延誤
			prpLregist.setDamageName("交通工具延誤");
			prpLregist.setDamageTypeCode("999"); //出險類型 999 其他
			prpLregist.setDamageTypeName("其他");
			prpLregist.setLinkerName(prpCmain.getInsuredName());
			if(prpCinsured != null){
				prpLregist.setPhoneNumber(org.apache.commons.lang.StringUtils.isNotEmpty(prpCinsured.getMobile())? prpCinsured.getMobile():prpCinsured.getPhoneNumber());
			}
			prpLregist.setSendMesFlag("1");
			prpLregist.setDamageAddress(registDto.getPrpLregist().getDamageAddress());
			prpLregist.setAddressCode(registDto.getPrpLregist().getAddressCode());
		}
		httpServletRequest.setAttribute("damageCodeList", ICollections.getDamageCodeList());
		httpServletRequest.setAttribute("damageTypeCodeList", ICollections.getDamageTypeCodeList());
		httpServletRequest.setAttribute("indemnityDutyList", ICollections.getIndemnityDutyList());
		// Reason:损失部位显示改为列表框方式
		httpServletRequest.setAttribute("partCodeList", ICollections.getPartCodeList());
		// Reason:三个不同节点共用几个jsp文件时，客户端程序需要区分请求来自哪个节点
		String strPrpLnodeType = "regis";
		httpServletRequest.setAttribute("prpLnodeType", strPrpLnodeType);

		// 节点设置成报案，三者车辆
		prpLthirdParty.setNodeType("regis");
		// 设置客户类型
		if (!CommonUtils.isEmpty(prpLregist.getInsuredCode())) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		// e保通
//		List<PrpCinsured> prpCinsuredList = policyDto.getPrpCinsuredList();
		if (prpCinsuredList != null && prpCinsuredList.size() > 0) {
			for (PrpCinsured tempPrpCinsured : prpCinsuredList) {
				if (tempPrpCinsured != null && "1".equalsIgnoreCase(tempPrpCinsured.getInsuredFlag())) {
					if ("1".equals(flushflag)) {// 页面刷新时取当前页面电话号码
						prpLregist.setInsuredPhoneNumber(prpInsuredPhoneNumber);
						prpLregist.setPolicyInsuredPhoneNumber(prpInsuredPhoneNumber);
					} else {
						prpLregist.setInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
						prpLregist.setPolicyInsuredPhoneNumber(tempPrpCinsured.getPhoneNumber());
					}
					prpLregist.setPolicyInsuredMobile(tempPrpCinsured.getMobile());
					if (prpCinsuredList != null) {
						prpLregist.setInsuredNameShow(strInsuredName);
					}
					if(strInsuredCode!=null){
						if(strInsuredCode.equals(tempPrpCinsured.getInsuredCode())||(tempPrpCinsured.getInsuredCode()==null&&strInsuredCode.equals(prpCinsured.getIdentifyNumber()))){
							httpServletRequest.setAttribute("prpCinsured", prpCinsured);
							break;
						}
					}else{
						httpServletRequest.setAttribute("prpCinsured", tempPrpCinsured);
						break;
					}
					
				}
			}
		}
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		httpServletRequest.setAttribute("flushflag", "1");
		httpServletRequest.setAttribute("riskCName", codeService.translateRiskCode(prpLregist.getRiskCode(), true));
		
		//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種 
		httpServletRequest.setAttribute("prpLthirdParty", prpLthirdParty);
		
		if (ConstantCodes.CLASSCODE_Y.equals(strRiskType)) {
			// 原因：增加货运险保单信息
			PrpCmainCargo prpCmain_cargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
			// reason:增加进行运输工具的转换
			if (prpCmain_cargo != null) {
				httpServletRequest.setAttribute("bLNo", prpCmain_cargo.getBlNo());
				prpCmain_cargo.setConveyance(codeService.translateCodeCode("TransMode", prpCmain_cargo.getConveyance(), true));
				prpLregist.setAddressCode(prpCmain_cargo.getEndSiteCode());
				prpLregist.setDamageAddress(prpCmain_cargo.getEndSiteName());
			}
			httpServletRequest.setAttribute("prpCmain_cargo", prpCmain_cargo);
		}
		httpServletRequest.setAttribute("prpLacciPerson", registDto.getPrpLacciPerson() == null ? new PrpLacciPerson() : registDto.getPrpLacciPerson());
	}
	
	/**
	 * 根据报案号和保单号查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */
	public void registDtoListToView(HttpServletRequest httpServletRequest, String registNo, String policyNo) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		String conditions = "";
		conditions = " registNo like '%" + registNo + "%' AND policyNo like '%" + policyNo + "%'";
		conditions = conditions + "  And registno in (select BusinessNo from prpLclaimStatus where nodeType='regis') ";

		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// modify by zhaolu 20060817 start
		/**
		 * conditions = conditions +
		 * uiPowerInterface.addPower(userDto.getUserCode(), "lpba", "a",
		 * BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		 */
		conditions = conditions + uiPowerInterface.addPower(userDto, "PrpLregist", "", "ComCode");
		// 查询报案信息
		// 得到多行报案主表信息
		List<PrpLregist> registList = prpLregistService.findByConditions(conditions);
		List<PrpLregist> registLastList = new ArrayList<PrpLregist>();
		PrpLregist prpLregist = new PrpLregist();
		String operatorName = "";
		String operatorCode = "";
		PrpLregist prpLregistTemp = null;
		for (int i = 0; i < registList.size(); i++) {
			prpLregistTemp = new PrpLregist();
			prpLregistTemp = registList.get(i);
			operatorCode = prpLregistTemp.getOperatorCode();
			operatorName = codeService.translateUserCode(operatorCode, true);
			prpLregistTemp.setOperatorName(operatorName);
			registLastList.add(prpLregistTemp);
		}
		prpLregist.setRegistList(registLastList);
		// 设置客户类型
		if (!prpLregist.getInsuredCode().equals("")) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		prpLregist.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */
	public void setPrpLregistDtoToView(HttpServletRequest httpServletRequest, String registNo, String policyNo, String licenseNo, String status, String operateDate, String riskCode, String insuredName, String strPageNo, String recordPerPage)
			throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		status = StringUtils.rightTrim(status);
		operateDate = StringUtils.rightTrim(operateDate);
		riskCode = StringUtils.rightTrim(riskCode);
		insuredName = StringUtils.rightTrim(insuredName);
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		if (registNo.length() > 0) {
			String registNoSign = httpServletRequest.getParameter("RegistNoSign");
			conditions.append(StringConvert.convertString(" PrpLregist.registNo", registNo, registNoSign));
		}
		// reason:强三查询
		if (policyNo.length() > 0) {
			conditions.append(StringConvert.convertString(" c.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign")));
		}
		if (riskCode.length() > 0) {
			String riskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
			conditions.append(StringConvert.convertString(" PrpLregist.riskCode", riskCode, riskCodeSign));
		}
		if (insuredName.length() > 0) {
			String insuredNameSign = (String) httpServletRequest.getParameter("InsuredNameSign");
			conditions.append(StringConvert.convertString(" PrpLregist.insuredName", insuredName, insuredNameSign));
		}
		if (status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ") ");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			String operateDateSign = (String) httpServletRequest.getParameter("OperateDateSign");
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, operateDateSign));
		}
		// 加入权限
		// reason添加新权限
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(powerService.addRiskPower(userDto, "PrpLregist","claim") + uiPowerInterface.addCustomerPower(userDto, "PrpLregist", "", "ComCode"));
		// 从翻页取数据
		String condition = httpServletRequest.getParameter("condition");
		if (condition != null && condition.trim().length() > 0) {
			conditions = new StringBuffer(condition);
		}
		// 查询报案信息
		// 得到多行报案主表信息
		Page page = registService.findByQueryConditions(conditions.toString(), strPageNo, recordPerPage);
		List<?> registList = (ArrayList<?>) page.getResult();
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setRegistList(registList);
		prpLregist.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 */
	public void setPrpLregistDtoToView(HttpServletRequest httpServletRequest, WorkFlowQueryDto workFlowQueryDto) throws Exception {
		// 报案查询
		// 根据输入的保单号，报案号生成SQL where 子句
		String registNo = StringUtils.rightTrim(workFlowQueryDto.getRegistNo());
		String policyNo = StringUtils.rightTrim(workFlowQueryDto.getPolicyNo());
		String licenseNo = StringUtils.rightTrim(workFlowQueryDto.getLicenseNo());
		String status = StringUtils.rightTrim(workFlowQueryDto.getStatus());
		String operateDate = StringUtils.rightTrim(workFlowQueryDto.getOperateDate());
		String riskCode = StringUtils.rightTrim(workFlowQueryDto.getRiskCode());
		String cancelFlag = StringUtils.rightTrim(workFlowQueryDto.getCancelFlag());
		String insuredName = StringUtils.rightTrim(workFlowQueryDto.getInsuredName());
		String registStartCancelDate = StringUtils.rightTrim(workFlowQueryDto.getRegistStartCancelDate());
		String registEndCancelDate = StringUtils.rightTrim(workFlowQueryDto.getRegistEndCancelDate());
		PrpLregist prpLregist = new PrpLregist();
		// reason 保留输入域字段
		PrpLregist prpLregist1 = new PrpLregist();
		prpLregist1.setRegistNo(httpServletRequest.getParameter("RegistNo"));
		prpLregist1.setPolicyNo(httpServletRequest.getParameter("PolicyNo"));
		prpLregist1.setLicenseNo(httpServletRequest.getParameter("LicenseNo"));
		prpLregist1.setRiskCode(httpServletRequest.getParameter("RiskCode"));
		prpLregist1.setInsuredName(httpServletRequest.getParameter("InsuredName"));
		httpServletRequest.setAttribute("prpLregist1", prpLregist1);
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		conditions.append(StringConvert.convertString("PrpLregist.registNo", registNo, workFlowQueryDto.getRegistNoSign()));
		// reason:强三查询
		if (policyNo.length() > 0) {
			conditions.append(StringConvert.convertString(" c.policyNo", policyNo, workFlowQueryDto.getPolicyNoSign()));
		}
		conditions.append(StringConvert.convertString("PrpLregist.riskCode", riskCode, workFlowQueryDto.getRiskCodeSign()));
		conditions.append(StringConvert.convertString("PrpLregist.licenseNo", licenseNo, workFlowQueryDto.getLicenseNoSign()));
		// 添加被保险人查询条件 2005-07-12
		conditions.append(StringConvert.convertString("PrpLregist.InsuredName", insuredName, workFlowQueryDto.getInsuredNameSign()));
		if (status != null && status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ") ");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, workFlowQueryDto.getOperateDateSign()));
		}
		// 判断是否报案注销
		if (cancelFlag.equals("1")) {
			// 需要检查此处代码是否被执行到
			if (registStartCancelDate != null && registStartCancelDate.length() > 0) {
				conditions.append(" AND PrpLregist.cancelDate >= '" + registStartCancelDate + "'");
			}
			if (registEndCancelDate != null && registEndCancelDate.length() > 0) {
				conditions.append(" AND PrpLregist.cancelDate <= '" + registEndCancelDate + "'");
			}
			if ("".equals(registStartCancelDate) && "".equals(registEndCancelDate)) {
				conditions.append(" AND (PrpLregist.cancelDate is not null)");
			}
		} else if (cancelFlag.equals("0")) {
			conditions.append(" AND (PrpLregist.cancelDate is null)");
			// 拼权限
			UIPowerInterface uiPowerInterface = new UIPowerInterface();
			UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
			conditions.append(uiPowerInterface.addPower(userDto, "PrpLregist", "", "ComCode"));
			// 查询报案信息
			String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
			String pageNo = httpServletRequest.getParameter("pageNo");
			if (pageNo == null || pageNo.trim().equals(""))
				pageNo = "1";
			String condition = httpServletRequest.getParameter("condition");
			// reason 查询标志
			String searchFlag = httpServletRequest.getParameter("searchFlag");
			if ("true".equals(searchFlag)) {
			} else {
				if (condition != null && condition.trim().length() > 0) {
					conditions = new StringBuffer(condition);
				}
			}
			// 得到多行报案主表信息
			Page page = registService.findByQueryConditions(conditions.toString(), pageNo, recordPerPage);
			// 设置客户类型
			if (!prpLregist.getInsuredCode().equals("")) {
				prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
			}
			List<?> registList = (ArrayList<?>) page.getResult();
			prpLregist.setRegistList(registList);
			prpLregist.setEditType(httpServletRequest.getParameter("editType"));
			httpServletRequest.setAttribute("prpLregist", prpLregist);
		}
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest
	 * @param registNo
	 * @param policyNo
	 * @param insuredName
	 * @param licenseNo
	 * @throws Exception
	 */
	public void setPrpLregistDtoToPrint(HttpServletRequest httpServletRequest, String registNo, String policyNo, String insuredName, String licenseNo) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.leftTrim(StringUtils.rightTrim(registNo));
		policyNo = StringUtils.leftTrim(StringUtils.rightTrim(policyNo));
		insuredName = StringUtils.leftTrim(StringUtils.rightTrim(insuredName));
		licenseNo = StringUtils.leftTrim(StringUtils.rightTrim(licenseNo));
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		if (!registNo.equals(""))
			conditions.append(" and registNo ='" + registNo + "'");
		if (!policyNo.equals(""))
			conditions.append("and  policyNo ='" + policyNo + "'");
		if (!insuredName.equals(""))
			conditions.append(" and insuredName ='" + insuredName + "'");
		if (!licenseNo.equals(""))
			conditions.append(" and licenseNo ='" + licenseNo + "'");
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(uiPowerInterface.addPower(userDto, "prplregist", "", "ComCode"));
		// 查询报案信息
		// 得到多行报案主表信息
		List<PrpLregist> registList = prpLregistService.findByConditions(conditions.toString());
		if (registList == null || registList.size() == 0) {
			throw new UserException(-98, -1000, "");
		}
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setRegistList(registList);
		// 设置客户类型
		if (!prpLregist.getInsuredCode().equals("")) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		prpLregist.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */

	public void setPrpLregistDtoToView(HttpServletRequest httpServletRequest, String registNo, String policyNo, String licenseNo, String status, String operateDate, String riskCode, String insuredName) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		status = StringUtils.rightTrim(status);
		operateDate = StringUtils.rightTrim(operateDate);
		riskCode = StringUtils.rightTrim(riskCode);
		insuredName = StringUtils.rightTrim(insuredName);
		StringBuffer conditions = new StringBuffer(" 1=1 ");
		if (registNo.length() > 0) {
			conditions.append(StringConvert.convertString(" PrpLregist.registNo", registNo, httpServletRequest.getParameter("RegistNoSign")));
		}
		if (policyNo.length() > 0) {
			conditions.append(StringConvert.convertString(" PrpLregist.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign")));
		}
		if (riskCode.length() > 0) {
			conditions.append(StringConvert.convertString(" PrpLregist.riskCode", riskCode, httpServletRequest.getParameter("RiskCodeSign")));
		}
		if (insuredName.length() > 0) {
			conditions.append(StringConvert.convertString(" PrpLregist.insuredName", insuredName, httpServletRequest.getParameter("InsuredNameSign")));
		}
		if (status.trim().length() > 0) {
			conditions.append(" AND b.status in (" + status + ") ");
		}
		if (operateDate != null && !operateDate.trim().equals("")) {
			conditions.append(StringConvert.convertDate("b.operateDate", operateDate, httpServletRequest.getParameter("OperateDateSign")));
		}
		// 得到多行报案主表信息
		Page page = registService.findByQueryConditions(conditions.toString());
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setRegistList(page.getResult());
		// 设置客户类型
		if (!prpLregist.getInsuredCode().equals("")) {
			prpLregist.setCustomerType(codeService.getCustomerType(prpLregist.getInsuredCode()));
		}
		prpLregist.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	// 强三
	/* 计算强三出险次数 */
	public void getQsRegistInfo(HttpServletRequest httpServletRequest, String policyNo) throws Exception {
		int intPerilCount = 0; // 出险次数
		int intRecentCount = 0; // 最近出险次数
		int intervalDay = 0;
		String priorDate = AppConfig.get("sysconst.RegistViewLimitDay");
		DateTime dateTime = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
		String registNo = "";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<Prplregistrpolicy> prpLRegistRPolicyList = prpLregistrpolicyService.findPrplregistrpolicy(queryRule);
//		Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
//		PolicyDto policyDto = policyService.findByPrimaryKey(policyNo);
//		List<Prplregistrpolicy> prpLRegistRPolicyList = policyDto.getPrpLRegistRPolicyList();
		if (prpLRegistRPolicyList != null && prpLRegistRPolicyList.size() > 0) {
			intPerilCount = prpLRegistRPolicyList.size(); // 出险次数
			Prplregistrpolicy prpLRegistRPolicy = null;
			for (int i = 0; i < prpLRegistRPolicyList.size(); i++) { // 求最近五天内出险次数
				prpLRegistRPolicy = (Prplregistrpolicy) prpLRegistRPolicyList.get(i);
				registNo = prpLRegistRPolicy.getId().getRegistNo();
//				RegistDto registDto = registService.findByPrimaryKey(registNo);
				PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
				intervalDay = DateTime.intervalDay(new DateTime(prpLregist.getDamageStartDate()), 0, dateTime, 0);
				if (intervalDay <= Integer.parseInt(priorDate)) {
					intRecentCount++;
				}
			}
		}
		httpServletRequest.setAttribute("intPerilCount", String.valueOf(intPerilCount));
		httpServletRequest.setAttribute("intRecentCount", String.valueOf(intRecentCount));
	}

	/**
	 * 根据保单号核对出险日期
	 * @param httpServletRequest
	 * @param policyNo
	 * @param damageDate
	 * @param damageHour
	 * @throws Exception
	 */
	public boolean checkDate(HttpServletRequest httpServletRequest, String policyNo, String damageDate, int damageHour) throws Exception {
		boolean valid = false;
//		PolicyDto policyDto = policyService.findByPrimaryKey(policyNo);
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);;
		if (prpCmain == null)
			return false;
		Date startDate = prpCmain.getStartDate();
		int startHour = prpCmain.getStartHour();
		Date endDate = prpCmain.getEndDate();
		int endHour = prpCmain.getEndHour();
		if (startDate.toString().compareTo(new DateTime(damageDate).toString()) > 0) { // 起保日期>出险日期
			valid = false;
		} else if (startDate.toString().compareTo(new DateTime(damageDate).toString()) < 0) { // 起保日期<出险日期
			if (endDate.toString().compareTo(new DateTime(damageDate).toString()) > 0) {// 终保日期>出险日期
				valid = true;
			} else if (endDate.toString().compareTo(new DateTime(damageDate).toString()) < 0) { // 终保日期<出险日期
				valid = false;
			} else { // //终保日期 = 出险日期
				if (endHour > damageHour) {
					valid = true;
				} else {
					valid = false;
				}
			}
		} else { // 起保日期 = 出险日期
			if (startHour > damageHour) {
				valid = false;
			} else {
				if (endDate.toString().compareTo(new DateTime(damageDate).toString()) > 0) {
					valid = true;
				} else if (endDate.toString().compareTo(new DateTime(damageDate).toString()) < 0) {
					valid = false;
				} else {
					if (endHour > damageHour) {
						valid = true;
					} else {
						valid = false;
					}
				}
			}
		}
		// 注销或全单退保保单不自动关联
		String relateFlag = policyService.isWithdraw(policyNo, damageDate, damageHour + "");
		System.out.println("relateFlag==" + relateFlag);
		if (relateFlag != null && (ConstantCodes.EndorseType_19.equals(relateFlag) || ConstantCodes.EndorseType_21.equals(relateFlag))) {// 注销或全单退保
			valid = false;
		}
		return valid;
	}

	/**
	 * 查询已经报案的数据，计算出现次数来进行显示
	 * @param registDto RegistDto
	 * @throws Exception
	 */
	public void getSamePolicyRegistInfo(HttpServletRequest httpServletRequest, String policyNo, String curRegistNo) throws Exception {
		
		// 修改计算当前出险次数和以往出险次数的计算。
		// 不用考虑部門等因素了。。
		String conditions = " policyno='" + policyNo + "' and canceldate is null ";
		String conditions1 = " policyno='" + policyNo+"'";
		QueryRule queryRule = QueryRule.getInstance();
		if(prpLregistService==null){
			prpLregistService = (PrpLregistService) ServiceFactory.getService("prpLregistService");
		}
		PrpCmain prpcmain = this.prpCmainService.findByPrimaryKey(policyNo);
		List<PrpLregist> registList = null;
		List<PrpLclaim> claimList = null;
		String riskType = codeService.translateRiskCodetoRiskType(prpcmain.getRiskCode());
		if ("E".equals(riskType)) {
			conditions = conditions + " and registNo not in (select distinct a.registno FROM prplclaim a inner join prplcompensate b on a.claimno = b.claimno where b.policyno = '" + policyNo + "' and b.nopaidclaim = '1')";
			registList = prpLregistService.findByConditions(conditions);
		} else if ("Y".equals(riskType)) {
			claimList = prpLclaimService.findPrpLclaim(queryRule.addSql(conditions1));
		} else {
			registList = prpLregistService.findPrpLregist(queryRule.addSql(conditions));
		}
		int intPerilCount = 0;
		int intRecentCount = 0; // 最近几天的出险次数
		int priorDate = Integer.parseInt(AppConfig.get("sysconst.RegistViewLimitDay"));
		DateTime dateTime = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
		int intervalDay = 0;
		if ("Y".equals(riskType)) {
			PrpLclaim prpLclaimTemp = null;
			intPerilCount = claimList.size(); // 通过立案计算已出险次数
			for (int i =0;i<claimList.size();i++) {
				prpLclaimTemp = claimList.get(i);
				intervalDay = DateTime.intervalDay(new DateTime(prpLclaimTemp.getDamageStartDate()), 0, dateTime, 0);
				if (intervalDay <= priorDate) {
					intRecentCount++; // 计算最近几天的出险次数。
				}
			}
		} else {
			PrpLregist prpLregistTemp = null;
			intPerilCount = registList.size();
			for (int i = 0; i < registList.size(); i++) {
				prpLregistTemp = registList.get(i);
				intervalDay = DateTime.intervalDay(new DateTime(prpLregistTemp.getDamageStartDate()), 0, dateTime, 0);
				if (intervalDay <= priorDate) {
					intRecentCount++; // 计算最近几天的出险次数。
				}
			}
		}
		if (curRegistNo == null){
			curRegistNo = "";
		}
		PrpLregist prpLregist = new PrpLregist();
		// 将查询出来的同个保单的数据放入PrpLregistDto的list
		prpLregist.setRegistList(registList);
		// 计算出险的次数
		prpLregist.setPerilCount(intPerilCount);
		prpLregist.setRecentCount(intRecentCount);
		httpServletRequest.setAttribute("policyNo", policyNo);
		httpServletRequest.setAttribute("curRegistNo", curRegistNo);
		httpServletRequest.setAttribute("prpLregistDto1", prpLregist);
	}

	/**
	 * 处理备案登记 报案前的保单查询 根据车牌号，操作时间，案件状态查询保单信息(此部分就是根据保单信息查询出保单的列表信息 )
	 * @param httpServletRequest 返回给页面的request
	 * @param pageNo 页码
	 * @param recordPerPage 每页显示的行数
	 * @throws Exception
	 */
	public void policyListToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，保单时间，被保险人，车牌，车驾号，发动机号生成SQL where 子句
		// 保单号码
		String strPolicyNo = httpServletRequest.getParameter("PolicyNo");
		String strPolicyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		// 车牌号码
		String strLicenseNo = httpServletRequest.getParameter("LicenseNo");
		String strLicenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		// 被保险人
		String strInsuredName = httpServletRequest.getParameter("InsuredName");
		String strInsuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		// 被保险人ID （身份证字号 or 统一编号）
		String strInsuredIdentifyNumber = httpServletRequest.getParameter("InsuredIdentifyNumber");
		String strInsuredIdentifyNumberSign = httpServletRequest.getParameter("InsuredIdentifyNumberSign");
		// 要保人名称
		String strAppliName = httpServletRequest.getParameter("AppliName");
		String strAppliNameSign = httpServletRequest.getParameter("AppliNameSign");
		// 定作人
		String strHirer = httpServletRequest.getParameter("Hirer");
		String strHirerSign = httpServletRequest.getParameter("HirerSign");
		// 施工处所
		String strConstructAddress = httpServletRequest.getParameter("ConstructAddress");
		String strConstructAddressSign = httpServletRequest.getParameter("ConstructAddressSign");
		// 要保人ID
		String strAppliIdentifyNumber = httpServletRequest.getParameter("AppliIdentifyNumber");
		String strAppliIdentifyNumberSign = httpServletRequest.getParameter("AppliIdentifyNumberSign");
		// 保险起期
		String strStartDate = httpServletRequest.getParameter("StartDate");
		String strStartDateSign = httpServletRequest.getParameter("StartDateSign");
		// 保险止期
		String strEndDate = httpServletRequest.getParameter("EndDate");
		String strEndDateSign = httpServletRequest.getParameter("EndDateSign");
		// 出险日期
		String damageDate = httpServletRequest.getParameter("DamageDate");
		// 出险小时
		String damageHour = httpServletRequest.getParameter("DamageHour");
		// 流水號（台帳保單）
		String sequenceNo = (String) httpServletRequest.getParameter("sequenceNo");
		// 增加险种查询
		String strRiskCode = httpServletRequest.getParameter("RiskCode");
		String strRiskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
		String strClassCode = httpServletRequest.getParameter("ClassCode");
		String strRiskCategory = httpServletRequest.getParameter("RiskCategory");
		//任意保險卡號
		String visaCodeBI = httpServletRequest.getParameter("visaCodeBI");
		String visaCodeBISign = httpServletRequest.getParameter("visaCodeBISign");
		
		//标的物地址--火险
		String addressDetailInfo = httpServletRequest.getParameter("addressDetailInfo");
		String addressDetailInfoSign = httpServletRequest.getParameter("addressDetailInfoSign");
		
		
		StringBuffer conditions = new StringBuffer(" and t.endorseno = getEndorseNo(t.PolicyNo, '"+ damageDate +"', '"+ damageHour +"') ");
		conditions.append(StringConvert.convertString("t.PolicyNo", strPolicyNo, strPolicyNoSign));
		conditions.append(StringConvert.convertString("t.Riskcode", strRiskCode, strRiskCodeSign));
		conditions.append(StringConvert.convertString("t.ClassCode", strClassCode, "="));
		conditions.append(StringConvert.convertString("t.AppliName", strAppliName, strAppliNameSign));
		conditions.append(StringConvert.convertDate("t.StartDate", strStartDate, strStartDateSign));
		conditions.append(StringConvert.convertDate("t.EndDate", strEndDate, strEndDateSign));
		conditions.append(StringConvert.convertString("t.visaCodeBI", visaCodeBI, visaCodeBISign));
		
		// 被保险人名称、被保险人ID、要保人ID 参与检索
		if (DataUtils.emptyToNull(strAppliIdentifyNumber) != null) {// 检索了要保人
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcopyinsured prpcinsured where t.endorseno = prpcinsured.endorseno ");
			conditions.append(" and prpcinsured.insuredflag = '2' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strAppliIdentifyNumber, strAppliIdentifyNumberSign));
			conditions.append(" ) ");
		}
		// 检索了被保险人、或其身份证字号、统一编号
		if (DataUtils.emptyToNull(strInsuredName) != null || DataUtils.emptyToNull(strInsuredIdentifyNumber) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcopyinsured prpcinsured where t.endorseno = prpcinsured.endorseno ");
			conditions.append(" and prpcinsured.insuredflag = '1' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
			conditions.append(StringConvert.convertString("prpcinsured.insuredname", strInsuredName, strInsuredNameSign));
			conditions.append(" ) ");
		}
		// 定作人检索
		if (DataUtils.emptyToNull(strHirer) != null) {// 检索了定作人
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcopyinsured prpcinsured where t.endorseno = prpcinsured.endorseno ");
			conditions.append(" and prpcinsured.insuredflag = '7' ");
			conditions.append(" and prpcinsured.riskCode in ('CA','EA',' CP') ");//CA、EA CP险种有 定作人
			conditions.append(StringConvert.convertString("prpcinsured.insuredname", strHirer, strHirerSign));
			conditions.append(" ) ");
		}
		// 施工处所检索
		if (DataUtils.emptyToNull(strConstructAddress) != null) {// 检索了施工处所
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpCaddress where t.policyno = prpCaddress.policyno ");
			conditions.append(" and prpCaddress.riskCode = 'CP' ");//只有CP的保險標的介面有 施工处所
			conditions.append(StringConvert.convertString("prpCaddress.addressName", strConstructAddress, strConstructAddressSign));
			conditions.append(" ) ");
		}
		// 标的物地址
		if (DataUtils.emptyToNull(addressDetailInfo) != null) {// 标的物地址
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpCaddress where t.policyno = prpCaddress.policyno ");
			conditions.append(StringConvert.convertString("prpCaddress.addressDetailInfo", addressDetailInfo, addressDetailInfoSign));
			conditions.append(" ) ");
		}
		// 增加承运人保险车牌号查询功能
		if (DataUtils.emptyToNull(strLicenseNo) != null) {
			// 车险才使用车牌这个条件
			if (DataUtils.emptyToNull(strRiskCategory) == null || "D".equals(strRiskCategory)) {
				conditions.append(" and exists (select 0 from prpcopyitemcar prpcitemcar where t.endorseno = prpcitemcar.endorseno ");
				conditions.append(StringConvert.convertString("prpcitemcar.licenseno", strLicenseNo, strLicenseNoSign));
				conditions.append(" ) ");
			}
		}
		// 增加台保单的查询条件
		if (DataUtils.emptyToNull(sequenceNo) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcbatch where t.policyno = prpcbatch.policyno ");
			conditions.append(" and prpcbatch.billstartno <= '" + sequenceNo + "'　and prpcbatch.billendno >= '" + sequenceNo + "'");
			conditions.append(" ) ");
			if (DataUtils.emptyToNull(strRiskCode) != null) {
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				String visaCode = new Visa().getOptionCode(user.getComCode(), strRiskCode, "P");
				if (DataUtils.emptyToNull(visaCode) != null) {
					conditions.append("  and t.VisaCode='" + visaCode + "' ");
				}
			}
		}
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if (!"true".equals(searchFlag)) {// 翻页的以上次查询为条件
			Object condition = httpServletRequest.getSession().getAttribute("registBeforeQueryConditions");
			if (condition != null) {
				conditions = new StringBuffer(String.valueOf(condition));
			}
		}
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(powerService.addRiskPower(user, "t","claim"));
		// 得到多行报案主表信息
		Page page = policyService.findForRegistConditions(conditions.toString(), pageNo, recordPerPage);
		List<?> prpCopyMainList = page.getResult();
		List<PrpCmain> prpCmainList = new ArrayList<PrpCmain>();
		if (prpCopyMainList != null && !prpCopyMainList.isEmpty()) {
			PrpCmain prpCmain = null;
			PrpCopyMain prpCopyMain = null;
			String endorseNo = null;
			String endorType = null;
			for (Iterator<?> it = prpCopyMainList.iterator(); it.hasNext();) {
				prpCmain = new PrpCmain();
				prpCopyMain = (PrpCopyMain) it.next();
				PropertyUtils.copyProperties(prpCmain, prpCopyMain);
				prpCmain.setDamageDate(damageDate);
				prpCmain.setDamageHour(damageHour);
				endorseNo = prpCopyMain.getEndorseNo();
				if (!prpCopyMain.getPolicyNo().equals(prpCopyMain.getEndorseNo())) {// 有批單
					PrpPhead prpPhead = this.prpPheadService.findByPrimaryKey(endorseNo);
					endorType = prpPhead.getEndorType();
					if ("21".equals(endorType) || "98".equals(endorType) || "19".equals(endorType) ) {
						// 全單退保、全額退保、保單註銷案件
						prpCmain.setEndorType(endorType);
						prpCmain.setValidDate(new DateTime(prpPhead.getValidDate()).toString());
						prpCmain.setValidHour(prpPhead.getValidHour());
					}
				}
				String othFlag = prpCmain.getOthFlag();// 存在其他标识字段 , // 已不適用
				if (othFlag.length() > 3 && "1".equals(othFlag.substring(3, 4))) {// 注销标识
					prpCmain.setColorFlag("1");
				}
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(prpCmain.getEndDate());
				endDate.set(Calendar.HOUR_OF_DAY, prpCmain.getEndHour());
				if (endDate.before(Calendar.getInstance())) {
					prpCmain.setColorFlag("1");// 过期保单显示为红色，过期保单的处理
				}
				// 险种名称转换
				prpCmain.setRiskCName(codeService.translateRiskCode(prpCmain.getRiskCode(), true));
				if ("D".equals(strRiskCategory) || "D".equals(codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode()))) {
					PrpCopyItemCar car = this.prpCopyItemCarService.findPrpCopyItemCar(new PrpCopyItemCarId(endorseNo , 1));
					if (car != null) {
						prpCmain.setLicenseNo(car.getLicenseNo());// 设置车牌号码
						prpCmain.setBrandName(car.getBrandName());// 设置厂牌型号
					}
				}
				PrpCopyInsured prpCopyInsured = null;
				String sql = " endorseNo = '"+ prpCopyMain.getEndorseNo() +"' and insuredflag = '1' ";
				QueryRule queryRule = null;
				if(!CommonUtils.isEmpty(strInsuredIdentifyNumber)){
					queryRule =  QueryRule.getInstance().addSql(sql + StringConvert.convertString("identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
					List<PrpCopyInsured> prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
					if(!CommonUtils.isEmpty(prpCinsuredList)){
						prpCopyInsured = prpCinsuredList.get(0);
						prpCmain.setInsuredCode(CommonUtils.isEmpty(prpCopyMain.getInsuredCode())? prpCopyInsured.getIdentifyNumber():prpCopyInsured.getInsuredCode());
						prpCmain.setInsuredName(prpCopyInsured.getInsuredName());
					}
				}
				if(CommonUtils.isEmpty(prpCmain.getInsuredCode())){
					String queryname = "";
					if(!CommonUtils.isEmpty(prpCmain.getInsuredName())){
						queryname = " and insuredName = '"+ prpCmain.getInsuredName() +"'";
					}
					queryRule =  QueryRule.getInstance().addSql(sql + queryname + " and rownum = 1 order by serialno ");
					List<PrpCopyInsured> prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
					if(CommonUtils.isEmpty(prpCinsuredList)){
						queryRule =  QueryRule.getInstance().addSql(sql + " and rownum = 1 order by serialno ");
						prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
					}
					if(!CommonUtils.isEmpty(prpCinsuredList)){
						prpCopyInsured = prpCinsuredList.get(0);
						prpCmain.setInsuredCode(CommonUtils.isEmpty(prpCopyMain.getInsuredCode())? prpCopyInsured.getIdentifyNumber():prpCopyInsured.getInsuredCode());
						prpCmain.setInsuredName(prpCopyInsured.getInsuredName());
					}
				}
				prpCmainList.add(prpCmain);
			}
		}
		httpServletRequest.setAttribute("prpCmainList", prpCmainList);
		httpServletRequest.setAttribute("page", page);
		httpServletRequest.getSession().setAttribute("registBeforeQueryConditions", conditions.toString());
	}

	
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 处理备案登记 报案前的保单查询 根据车牌号，操作时间，案件状态查询保单信息(此部分就是根据保单信息查询出保单的列表信息 )
	 * @param httpServletRequest 返回给页面的request
	 * @param pageNo 页码
	 * @param recordPerPage 每页显示的行数
	 * @throws Exception
	 */
	public List<PrpCmain> policyListToView4Ws(HttpServletRequest httpServletRequest,ReqRegistTemp request, int pageNo, int recordPerPage) throws Exception {
		// 根据输入的保单号，保单时间，被保险人，车牌，车驾号，发动机号生成SQL where 子句
		// 保单号码
		String strPolicyNo = request.getPolicyNo();
		String strPolicyNoSign = "=";
		// 出险日期
		String damageDate = request.getDamageDate();
		// 出险小时
		String damageHour = request.getDamageHour();
		// 增加险种查询
		//del
//		String strRiskCodeSign = "=";
		
		//标的物地址--火险
		//del
		
		StringBuffer conditions = new StringBuffer(" and t.endorseno = getEndorseNo(t.PolicyNo, '"+ damageDate +"', '"+ damageHour +"') ");
		conditions.append(StringConvert.convertString("t.PolicyNo", strPolicyNo, strPolicyNoSign));
//		conditions.append(StringConvert.convertString("t.Riskcode", strRiskCode, strRiskCodeSign));
		
		// 被保险人名称、被保险人ID、要保人ID 参与检索
		//DEL
		
		// 检索了被保险人、或其身份证字号、统一编号
		//DEL
		
		// 定作人检索
		//DEL
		
		// 施工处所检索
		//DEL
		
		// 标的物地址
		//DEL
		
		// 增加承运人保险车牌号查询功能
		//DEL
		
		// 增加台保单的查询条件
		//DEL
		
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions.append(powerService.addRiskPower(user, "t","claim"));
		if(conditions.indexOf("(1 = 0)")!=-1){
			throw new Exception("無效的理賠人員("+user.getComCode()+"/"+user.getUserCode()+")");
		}
		// 得到多行报案主表信息
		Page page = policyService.findForRegistConditions(conditions.toString(), pageNo, recordPerPage);
		List<?> prpCopyMainList = page.getResult();
		List<PrpCmain> prpCmainList = new ArrayList<PrpCmain>();
		if (prpCopyMainList != null && !prpCopyMainList.isEmpty()) {
			PrpCmain prpCmain = null;
			PrpCopyMain prpCopyMain = null;
			String endorseNo = null;
			String endorType = null;
			for (Iterator<?> it = prpCopyMainList.iterator(); it.hasNext();) {
				prpCmain = new PrpCmain();
				prpCopyMain = (PrpCopyMain) it.next();
				PropertyUtils.copyProperties(prpCmain, prpCopyMain);
				prpCmain.setDamageDate(damageDate);
				prpCmain.setDamageHour(damageHour);
				endorseNo = prpCopyMain.getEndorseNo();
				if (!prpCopyMain.getPolicyNo().equals(prpCopyMain.getEndorseNo())) {// 有批單
					PrpPhead prpPhead = this.prpPheadService.findByPrimaryKey(endorseNo);
					endorType = prpPhead.getEndorType();
					if ("21".equals(endorType) || "98".equals(endorType) || "19".equals(endorType) ) {
						// 全單退保、全額退保、保單註銷案件
						prpCmain.setEndorType(endorType);
						prpCmain.setValidDate(new DateTime(prpPhead.getValidDate()).toString());
						prpCmain.setValidHour(prpPhead.getValidHour());
					}
				}
				String othFlag = prpCmain.getOthFlag();// 存在其他标识字段 , // 已不適用
				if (othFlag.length() > 3 && "1".equals(othFlag.substring(3, 4))) {// 注销标识
					prpCmain.setColorFlag("1");
				}
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(prpCmain.getEndDate());
				endDate.set(Calendar.HOUR_OF_DAY, prpCmain.getEndHour());
				if (endDate.before(Calendar.getInstance())) {
					prpCmain.setColorFlag("1");// 过期保单显示为红色，过期保单的处理
				}
				// 险种名称转换
				prpCmain.setRiskCName(codeService.translateRiskCode(prpCmain.getRiskCode(), true));
//				if ("D".equals(strRiskCategory) || "D".equals(codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode()))) {
//					PrpCopyItemCar car = this.prpCopyItemCarService.findPrpCopyItemCar(new PrpCopyItemCarId(endorseNo , 1));
//					if (car != null) {
//						prpCmain.setLicenseNo(car.getLicenseNo());// 设置车牌号码
//						prpCmain.setBrandName(car.getBrandName());// 设置厂牌型号
//					}
//				}
				PrpCopyInsured prpCopyInsured = null;
				String sql = " endorseNo = '"+ prpCopyMain.getEndorseNo() +"' and insuredflag = '1' ";
				QueryRule queryRule = null;
//				if(!CommonUtils.isEmpty(strInsuredIdentifyNumber)){
//					queryRule =  QueryRule.getInstance().addSql(sql + StringConvert.convertString("identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
//					List<PrpCopyInsured> prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
//					if(!CommonUtils.isEmpty(prpCinsuredList)){
//						prpCopyInsured = prpCinsuredList.get(0);
//						prpCmain.setInsuredCode(CommonUtils.isEmpty(prpCopyMain.getInsuredCode())? prpCopyInsured.getIdentifyNumber():prpCopyInsured.getInsuredCode());
//						prpCmain.setInsuredName(prpCopyInsured.getInsuredName());
//					}
//				}
				if(CommonUtils.isEmpty(prpCmain.getInsuredCode())){
					String queryname = "";
					if(!CommonUtils.isEmpty(prpCmain.getInsuredName())){
						queryname = " and insuredName = '"+ prpCmain.getInsuredName() +"'";
					}
					queryRule =  QueryRule.getInstance().addSql(sql + queryname + " and rownum = 1 order by serialno ");
					List<PrpCopyInsured> prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
					if(CommonUtils.isEmpty(prpCinsuredList)){
						queryRule =  QueryRule.getInstance().addSql(sql + " and rownum = 1 order by serialno ");
						prpCinsuredList = this.getPrpCopyInsuredService().findPrpCopyInsured(queryRule);
					}
					if(!CommonUtils.isEmpty(prpCinsuredList)){
						prpCopyInsured = prpCinsuredList.get(0);
						prpCmain.setInsuredCode(CommonUtils.isEmpty(prpCopyMain.getInsuredCode())? prpCopyInsured.getIdentifyNumber():prpCopyInsured.getInsuredCode());
						prpCmain.setInsuredName(prpCopyInsured.getInsuredName());
					}
				}
				prpCmainList.add(prpCmain);
			}
		}
		httpServletRequest.setAttribute("prpCmainList", prpCmainList);
//		httpServletRequest.setAttribute("page", page);
//		httpServletRequest.getSession().setAttribute("registBeforeQueryConditions", conditions.toString());
		
		return prpCmainList;
	}
	
	/**
	 * 检查缴费标志 返回值 int -1为未缴费，0为未缴全，1为缴全
	 * @param httpServletRequest 返回给页面的request
	 * @param policyNo 立案号
	 * @throws Exception
	 */
	public int checkPay(HttpServletRequest httpServletRequest, String policyNo) throws Exception {
		String conditions = " policyno = '" + policyNo + "'";
		int intReturn = 0;
		intReturn = policyService.checkPay(conditions);
		return intReturn;
	}

	/**
	 * 根据保单号查询欠费时间信息
	 * @param policyNo
	 * @throws Exception
	 */
	public int[] getDelinquentfeeTime(String policyNo) throws Exception {
		String conditions = " policyno = '" + policyNo + "'";
		return policyService.getDelinquentfeeTime(conditions);
	}

	/**
	 * 根据prpcmian查询欠情况信息
	 * @param prpcmain
	 * @return delinquentfeeCase
	 * @throws Exception
	 */
	public String getDelinquentfeeCase(PrpCmain prpcmain) throws Exception {
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (prpcmain.getPayTimes() < 2) {
			delinquentfeeCase = "繳費計劃為" + prpcmain.getPayTimes() + "期";
		} else {
			int[] delinquentfeeTime = getDelinquentfeeTime(prpcmain.getPolicyNo());
			for (int i = 0; i < delinquentfeeTime.length; i++) {
				if (i == 0) {
					delinquentfeeCase = "繳費計劃為 " + prpcmain.getPayTimes() + " 期";
				}
				delinquentfeeCase += "\n";
				delinquentfeeCase += "第 " + delinquentfeeTime[i] + " 期末繳費";
			}
		}
		return delinquentfeeCase;
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @param licenseNo 车牌号码
	 * @param riskCode 险别
	 * @param insuredName 被保险人名称
	 * @throws Exception
	 */
	public void getWorkFlowList(HttpServletRequest httpServletRequest, String registNo, String policyNo, String licenseNo, String riskCode, String insuredName) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		insuredName = StringUtils.rightTrim(insuredName);
		riskCode = StringUtils.rightTrim(riskCode);
		PrpLregist prpLregist = new PrpLregist();
		riskCode = StringUtils.rightTrim(riskCode);
		insuredName = StringUtils.rightTrim(insuredName);
		String conditions = " 1=1 ";
		if (registNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" PrpLregist.registNo", registNo, httpServletRequest.getParameter("RegistNoSign"));
		}
		if (policyNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" PrpLregist.policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		}
		if (riskCode.length() > 0) {
			conditions = conditions + StringConvert.convertString(" PrpLregist.riskCode", riskCode, httpServletRequest.getParameter("RiskCodeSign"));
		}
		if (insuredName.length() > 0) {
			conditions = conditions + StringConvert.convertString(" PrpLregist.insuredName", insuredName, httpServletRequest.getParameter("InsuredNameSign"));
		}
		if (licenseNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" PrpLregist.licenseNo", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		}
		// 拼权限
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, httpServletRequest.getParameter("taskCodeC"), "PrpLregist", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		// 查询报案信息
		// 得到多行报案主表信息
		List<?> registList = registService.getWorkFlowList(conditions);
		prpLregist.setRegistList(registList);
		prpLregist.setEditType(httpServletRequest.getParameter("editType"));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 根据PrpCheckDto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param checkDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, RegistDto registDto) throws Exception {
		// 给损失部位多行多行列表准备数据
		String riskCode = registDto.getPrpLregist().getRiskCode();
		PrpLregist prpLregist = registDto.getPrpLregist();
		List<PrpLthirdCarLoss> arrayListThirdCarLoss = new ArrayList<PrpLthirdCarLoss>();
		PrpLthirdCarLoss prpLthirdCarLoss = new PrpLthirdCarLoss();
		arrayListThirdCarLoss = registDto.getPrpLthirdCarLossList();
		prpLthirdCarLoss.setThirdCarLossList(arrayListThirdCarLoss);
		prpLthirdCarLoss.setPartName("前部");
		prpLthirdCarLoss.setPartCode("1");
		httpServletRequest.setAttribute("prpLthirdCarLoss", prpLthirdCarLoss);
		// Reason:在报案页面中加上其它损失模块
		List<PrpLthirdProp> arrayListThirdProp = new ArrayList<PrpLthirdProp>();
		PrpLthirdProp prpLthirdProp = new PrpLthirdProp();
		arrayListThirdProp = registDto.getPrpLthirdPropList();
		prpLthirdProp.setThirdPropList(arrayListThirdProp);
		httpServletRequest.setAttribute("prpLthirdProp", prpLthirdProp);
		// 给人员伤亡跟踪多行多行列表准备数据
		PrpLpersonTrace prpLpersonTrace = new PrpLpersonTrace();
		prpLpersonTrace = ScheduleItemDtoToView(prpLpersonTrace, registDto.getPrpLscheduleItemList());
		List<PrpLpersonTrace> arrayListPersonTrace = registDto.getPrpLpersonTraceList();
		if (registDto.getPrpLpersonTraceList() != null) {
			PrpLpersonTrace prplpersonTrace = null;
			PrpDcode[] prpDcodes = null;
			for (int i = 0; i < arrayListPersonTrace.size(); i++) {
				prplpersonTrace = arrayListPersonTrace.get(i);
				prplpersonTrace.setPrpLpersonTraceReferKind(prplpersonTrace.getReferKind());
				// 获取一级行业和二级行业信息 start
				prpDcodes = codeService.translateJobCode(prplpersonTrace.getJobCode(), riskCode);
				prplpersonTrace.setJobCode1(prpDcodes[0].getId().getCodeCode());
				prplpersonTrace.setJobName1(prpDcodes[0].getCodeCName());
				prplpersonTrace.setJobCode2(prpDcodes[1].getId().getCodeCode());
				prplpersonTrace.setJobName2(prpDcodes[1].getCodeCName());
			}
		}
		prpLpersonTrace.setPersonTraceList(arrayListPersonTrace);
		httpServletRequest.setAttribute("personTraceList", arrayListPersonTrace);
		httpServletRequest.setAttribute("prpLpersonTrace", prpLpersonTrace);
		// 给报案信息补充说明多行列表准备数据
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(registDto.getPrpLregist().getRegistNo());
		prpLregistExt.setRiskCode(registDto.getPrpLregist().getRiskCode());
		arrayListRegistExt = registDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		// 给事故经过及其事故者现状准备数据
		PrpLregistText prpLregistText = new PrpLregistText();
		String tempContext = "";
		if (registDto.getPrpLregistTextList() != null) {
			Iterator<PrpLregistText> iterator = registDto.getPrpLregistTextList().iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistTextTemp = (PrpLregistText) iterator.next();
				if (prpLregistTextTemp.getId().getTextType().equals("1")) {
					tempContext = tempContext + prpLregistTextTemp.getContext();
				}
			}
		}
		prpLregistText.setContext(tempContext);
		prpLregistText.getId().setTextType("1");
		httpServletRequest.setAttribute("prpLregistText", prpLregistText);
		// 给呈报信息准备数据
		PrpLregistText prpLregistText4 = new PrpLregistText();
		tempContext = "";
		if (registDto.getPrpLregistTextList() != null) {
			Iterator<PrpLregistText> iterator = registDto.getPrpLregistTextList().iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistTextTemp = (PrpLregistText) iterator.next();
				if (prpLregistTextTemp.getId().getTextType().equals("4")) {
					tempContext = tempContext + prpLregistTextTemp.getContext();
				}
			}
		}
		prpLregistText4.setContext(tempContext);
		prpLregistText4.getId().setTextType("4");
		httpServletRequest.setAttribute("prpLregistText4", prpLregistText4);
		// reason:加入保险标的信息的内容，界面上可以直接显示承保险别
		// 给保单投保标的准备数据
		List<PrpCitemKind> arrayPrpCitemKind = new ArrayList<PrpCitemKind>();
		PrpCitemKind prpCitemKind = new PrpCitemKind();
		arrayPrpCitemKind = registDto.getPrpCitemKindList();
		for(PrpCitemKind temp : arrayPrpCitemKind){
			String itemCode = codeService.getItemCode(temp);
			temp.setItemCode(itemCode);
			String itemName = codeService.getItemName(temp);
			temp.setItemName(itemName);
		}
		prpCitemKind.setPrpCitemKindList(arrayPrpCitemKind);
		httpServletRequest.setAttribute("prpCitemKind", prpCitemKind);
		
		// 特别约定信息多行列表准备数据
		List<PrpCengage> cengageList = new ArrayList<PrpCengage>();
		PrpCengage prpCengage = new PrpCengage();
		cengageList = registDto.getPrpCengageList();
		List<PrpCengage> cengageListTemp = new ArrayList<PrpCengage>();
		if (cengageList != null) {
			Iterator<PrpCengage> iteratorCengage = cengageList.iterator();
			while (iteratorCengage.hasNext()) {
				PrpCengage prpCengageTemp = (PrpCengage) iteratorCengage.next();
				if (prpCengageTemp.getClauseCode() != null && prpCengageTemp.getClauseCode().length() > 0 && prpCengageTemp.getClauseCode().charAt(0) == 'T') {
					cengageListTemp.add(prpCengageTemp);
				}
			}
			cengageList = new ArrayList<PrpCengage>();
			cengageList.addAll(cengageListTemp);
			cengageListTemp = new ArrayList<PrpCengage>();
			iteratorCengage = cengageList.iterator();
			PrpCengage prpCengageTemp1 = new PrpCengage();
			while (iteratorCengage.hasNext()) {
				PrpCengage prpCengageTemp = (PrpCengage) iteratorCengage.next();
				if (prpCengageTemp.getTitleFlag().equals("0")) {
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
		httpServletRequest.setAttribute("prpLscheduleMainWF", registDto.getPrpLscheduleMainWF());
		
		String policyCancelFlag = "0";//退保标记
		String damageStartDate = CommonUtils.getYearToDayStr(prpLregist.getDamageStartDate());
		String damageStartHour = prpLregist.getDamageStartHour();
		if(!CommonUtils.isEmpty(policyService.isWithdraw(prpLregist.getPolicyNo(), damageStartDate, damageStartHour))) {
			policyCancelFlag = "1";
		}
		httpServletRequest.setAttribute("policyCancelFlag", policyCancelFlag);
		// 获取报案出险延期天数
		String configValue = prpDriskConfigService.getConfigValue("REPORT_DEFER_DAYS", prpLregist.getRiskCode());
		if (configValue == null || configValue.equals("")) {
			throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpLregist.getRiskCode() + "'報案出險延期天數'的初始化！");
		}
		httpServletRequest.setAttribute("configValue", configValue);
		
	}

	/**
	 * 根据PrpRegistDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLcheckDto 查勘的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, PrpLregist prpLregist) throws Exception {
		// (1)归属业务员名称的转换
		String handler1Code = prpLregist.getHandler1Code();
		String handler1Name = codeService.translateUserCode(handler1Code, true);
		prpLregist.setHandler1Name(handler1Name);
		// (2)归属业务机构的转换
		String comCode = prpLregist.getComCode();
		String comName = codeService.translateComCode(comCode, true);
		prpLregist.setComName(comName);
		// (3)币别名称的转换
		String estiCurrency = prpLregist.getEstiCurrency();
		String estiCurrencyName = codeService.translateCurrencyCode(estiCurrency, true);
		prpLregist.setEstiCurrencyName(estiCurrencyName);
		// (4)理赔登记机构的转换
		String makeComCode = prpLregist.getMakeCom();
		String makeComName = codeService.translateComCode(makeComCode, true);
		prpLregist.setMakeComName(makeComName);
		// (5)条款名称的转换
		String clauseType = prpLregist.getClauseType();
		String clauseName = codeService.translateCodeCode("ClauseType", clauseType, true);
		prpLregist.setClauseName(clauseName);
		// (6)操作用户的转换
		String operatorCode = prpLregist.getOperatorCode();
		String operatorName = codeService.translateUserCode(operatorCode, true);
		prpLregist.setOperatorName(operatorName);
		// (7)处理部门的转换
		String handleUnit = prpLregist.getHandleUnit();
		String handleUnitName = "";
		if (!"".equals(handleUnit) || handleUnit.length() > 0 || handleUnit != null) {
			handleUnitName = codeService.translateCodeCode("HandleUnit", handleUnit, true);
			prpLregist.setHandleUnitName(handleUnitName);
		}
		// (8)显示邮编地名
		prpLregist.setAddressName(codeService.translateCodeCode("PostCode", prpLregist.getAddressCode(), true));
	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLregist 查勘的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, PrpLregist prpLregist) throws Exception {
		String riskCode = prpLregist.getRiskCode();
		String classCode = prpLregist.getClassCode();
		// 得到报案类型列表
		List<PrpDcode> reportTypes = codeService.getCodeType("ReportType", riskCode);
		httpServletRequest.setAttribute("reportTypes", reportTypes);
		// 得到案件种类列表列表
		List<PrpDcode> claimTypes = codeService.getCodeType("CaseCode", riskCode);
		httpServletRequest.setAttribute("claimTypes", claimTypes);
		// 得到出险地址类型列表
		List<PrpDcode> damageAddressTypes = codeService.getCodeType("DamageAddress", riskCode);
		httpServletRequest.setAttribute("damageAddressTypes", damageAddressTypes);
		// 得到车辆种类列表
		List<PrpDcode> carKindCodes = codeService.getCodeTypeCarKind("CarKind", classCode);
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 得到车牌底色列表
		List<PrpDcode> licenseColorCode = codeService.getCodeType("LicenseColor", riskCode);
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// 得到得到性别
		List<PrpDcode> driverSex = codeService.getCodeType("SexCode", riskCode);
		httpServletRequest.setAttribute("driverSexs", driverSex);
		// 得到职业分类
		List<PrpDcode> driverOccupation = codeService.getCodeType("Occupation", riskCode);
		httpServletRequest.setAttribute("driverOccupations", driverOccupation);
		// 得到文化程度
		List<PrpDcode> education = codeService.getCodeType("Education", riskCode);
		httpServletRequest.setAttribute("educations", education);
		// 備案人與被保險人關係
		httpServletRequest.setAttribute("relationTypeList", ConstantsCollection.relationTypeList);
		// 互碰自賠標志
		httpServletRequest.setAttribute("payselfFlagList", ConstantsCollection.payselfFlagList);
		// 是否需要現場處理
		httpServletRequest.setAttribute("scheduleTypeList", ConstantsCollection.scheduleTypeList);
		// 證件類型
		httpServletRequest.setAttribute("drivingCarTypeList", ConstantsCollection.drivingCarTypeList);
		// 駕駛人區別
		httpServletRequest.setAttribute("driverDistrictList", ConstantsCollection.driverDistrictList);
		// 估损金额调整
		httpServletRequest.setAttribute("lossLossFeeTypeList", ConstantsCollection.lossLossFeeTypeList);
		// 範圍
		httpServletRequest.setAttribute("lossFeeCategoryList", ConstantsCollection.lossFeeCategoryList);
		// 傷亡類型
		httpServletRequest.setAttribute("casualtiesList", ConstantsCollection.casualtiesList);
		// 是否自行就醫
		httpServletRequest.setAttribute("motionFlagList", ConstantsCollection.motionFlagList);
		// 本車駕駛人與被保險人關係
		httpServletRequest.setAttribute("thirdPartyRelationshipList", ConstantsCollection.thirdPartyRelationshipList);
		// 被保險人身分 駕駛人身份
		httpServletRequest.setAttribute("identityList", ConstantsCollection.identityList);
		// 承載單位
		httpServletRequest.setAttribute("partyCarryingUnitList", ConstantsCollection.partyCarryingUnitList);
	}

	/**
	 * 查询工作流可以用来选择的节点内容,在这里有点不同，因为如果查询出来的话，立案是一定有的，
	 * 但是报案下面的定损和查勘调度，结果有两个selection,一个是包括全部的节点，另一个只有可供选择的节点list
	 * @param modelNo String
	 * @param nodeNo String
	 * @throws Exception
	 */
	private void getSubmitNodes(HttpServletRequest httpServletRequest, String riskCode, String comCode) throws Exception {
		// 报案节点特殊，无法从前面得到当前的modelNo号码，必须从数据库中获得
		int modelNo = 0; // 模板号,需要根据险种，操作员部门选择
		String nodeNo = "1"; // 节点号
		int nextNodeNo = 0;
		Collection<?> pathList = new ArrayList<SwfPathDto>(); // 可以选择的所有的下一个节点
		Collection<SwfPathDto> userSelectList = new ArrayList<SwfPathDto>(); // 需要用户来指定的下一个节点
		SwfPathDto swfPathDto = new SwfPathDto();
		WorkFlowViewHelper workFlowViewHelper = new WorkFlowViewHelper();
		com.sinosoft.claim.workflow.util.WorkFlowViewHelper workFlowViewHelperSpring = (com.sinosoft.claim.workflow.util.WorkFlowViewHelper) ServiceFactory.getService("workFlowViewHelper");
		modelNo = workFlowViewHelperSpring.getModelNoByRiskComCode(riskCode, comCode);
		// reason:查找不到分配的模板时候，取上级机构模版
//		String conditions = "";
//		Collection<?> collection = null;
//		UIWorkFlowModelAction uiWorkFlowModelAction = new UIWorkFlowModelAction();
//		if (modelNo < 0) {
//			conditions = "riskcode ='" + riskCode + "' And comcode='00' And modeltype='01' And modelstatus='1'";
//			collection = uiWorkFlowModelAction.findByModelUseConditions(conditions);
//			// System.out.println("查找不到分配的模板时候，取00000000级机构模版" + conditions);
//			if (collection.size() > 0) {
//				Iterator<?> it = collection.iterator();
//				SwfModelUseDto swfModelUseDto = (SwfModelUseDto) it.next();
//				swfModelUseDto.setComCode(comCode);
//				modelNo = swfModelUseDto.getModelNo();
//				System.out.println("查找取00000000级机构模版——————" + modelNo);
//				uiWorkFlowModelAction.saveWfModelUseDto(swfModelUseDto);
//			}
//
//		}
		int[] selectNodeList_int = null;
		if (modelNo > 0 && nodeNo != null) {
			pathList = workFlowViewHelper.getNextSumbitNodes(modelNo + "", nodeNo);
			String[] selectNodeList = new String[pathList.size()];
			selectNodeList_int = new int[pathList.size()];
			for (int i = 0; i < pathList.size(); i++) {
				SwfPathDto swfPathDtoTemp = new SwfPathDto();
				swfPathDtoTemp = (SwfPathDto) ((ArrayList<?>) pathList).get(i);
				nextNodeNo = swfPathDtoTemp.getEndNodeNo();
				swfPathDto.setNextNodeNo(nextNodeNo);
				if (!swfPathDtoTemp.getDefaultFlag().equals("3")) {
					// 判断是可供选择的节点
					userSelectList.add(swfPathDtoTemp);
				}
				selectNodeList[i] = nextNodeNo + "";
				selectNodeList_int[i] = nextNodeNo;
			}
			swfPathDto.setNextNodeNoList(selectNodeList);
		}
		swfPathDto.setPathList(pathList);
		httpServletRequest.setAttribute("pathList", pathList);
		httpServletRequest.setAttribute("userSelectList", userSelectList);
		httpServletRequest.setAttribute("selectNodeList_int", selectNodeList_int);
		httpServletRequest.setAttribute("swfPathDto", swfPathDto);
	}

	/**
	 * 整理调度在车辆列表中的数据显示
	 * @param itemType String
	 * @param itemList Collection
	 * @param scheduleItemList Collection
	 * @throws exception
	 * @return Collection
	 */
	private List<PrpLthirdParty> ScheduleItemDtoToView(List<PrpLthirdParty> itemList, List<PrpLscheduleItem> scheduleItemList) throws Exception {
		if (scheduleItemList == null)
			return itemList;
		List<PrpLthirdParty> itemToViewList = new ArrayList<PrpLthirdParty>();
		PrpLthirdParty prpLthirdparty = null;
		if (itemList != null) {
			for (int j = 0; j < itemList.size(); j++) {
				prpLthirdparty = (PrpLthirdParty) (itemList).get(j);
				for (int i = 0; i < scheduleItemList.size(); i++) {
					PrpLscheduleItem prpLscheduleItem = (PrpLscheduleItem) (scheduleItemList).get(i);
					if (prpLthirdparty.getId().getSerialNo().equals(prpLscheduleItem.getId().getItemNo())) {
						prpLthirdparty.setSelectSend(prpLscheduleItem.getSelectSend());
						prpLthirdparty.setScheduleType(prpLscheduleItem.getScheduleType());
						break;
					}
				}
				itemToViewList.add(prpLthirdparty);
			}
		}
		return itemToViewList;
	}

	/**
	 * 整理调度在人员列表中的数据显示
	 * @param prpLpersonTrace PrpLpersonTrace
	 * @param scheduleItemList Collection
	 * @throws exception
	 * @return Collection
	 */
	private PrpLpersonTrace ScheduleItemDtoToView(PrpLpersonTrace prpLpersonTrace, List<PrpLscheduleItem> scheduleItemList) throws Exception {
		if (scheduleItemList == null)
			return prpLpersonTrace;
		for (int i = 0; i < scheduleItemList.size(); i++) {
			PrpLscheduleItem prpLscheduleItem = (PrpLscheduleItem) (scheduleItemList).get(i);
			if (prpLscheduleItem.getId().getItemNo() == 0) {
				prpLpersonTrace.setSelectSend(prpLscheduleItem.getSelectSend());
				prpLpersonTrace.setScheduleType(prpLscheduleItem.getScheduleType());
				break;
			}
		}
		return prpLpersonTrace;
	}

	/**
	 * 根据报案号得到该次报案的 已决 和 未决金额
	 * @param registNo
	 * @author 中科软
	 * @return
	 */
	public CompensateFeeDto getCompensateFeeByRegistNo(String registNo) throws Exception {
		double sumClaim = 0d;
		double sumPaid = 0d;
		double sumNoPaid = 0d;
		CompensateFeeDto compensateFeeDto = new CompensateFeeDto();
		// 得立案号
		String claimNo = codeService.translateBusinessCode(registNo, true);
		if (claimNo != null && claimNo.length() > 0) {
			PrpLclaim prpLclaim = new PrpLclaim();
			prpLclaim = claimService.findByPrimaryKey(claimNo).getPrpLclaim();
			if (prpLclaim != null) {
				// 保险损失金额
				sumClaim = prpLclaim.getSumClaim();
			}
			compensateFeeDto = compensateService.findCompensateFeeByClaimNo(claimNo);
			// 已决金额
			sumPaid = compensateFeeDto.getSumPaid();
			// 未决金额 ＝ 保险损失金额 － 已决金额
			sumNoPaid = sumClaim - sumPaid;
		}
		compensateFeeDto.setSumPaid(sumPaid);
		compensateFeeDto.setSumNoPaid(sumNoPaid);
		compensateFeeDto.setSumClaim(sumClaim);
		compensateFeeDto.setRegistNo(registNo);
		return compensateFeeDto;
	}

	/**
	 * 不予立案处理方法
	 * @param httpServletRequest 请求对象
	 * @throws Exception
	 * @return RegistDto
	 */
	public RegistDto getNotGrandClaim(HttpServletRequest httpServletRequest) throws Exception {
		PrpLregistText prpLregistText = null;
		String registNo = httpServletRequest.getParameter("prpLclaimRegistNo");
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		// 回写CancelDate不予立案日期和DealerCode处理不予立案的操作员代码
		PrpLregist prpLregist = registDto.getPrpLregist();
		prpLregist.setCancelDate(new DateTime(httpServletRequest.getParameter("prpLclaimCancelDate")));
		prpLregist.setDealerCode(httpServletRequest.getParameter("prpLclaimDealerCode"));
		registDto.setPrpLregist(prpLregist);
		/*---------------------向报案文本表prpLregistText加入不予立案文本信息------------------------------------*/
		List<PrpLregistText> prpLregistTextList = registDto.getPrpLregistTextList();
		if (prpLregistTextList == null || prpLregistTextList.size() < 1) {
			prpLregistTextList = new ArrayList<PrpLregistText>();
		}
		String TextTemp = httpServletRequest.getParameter("prpLclaimContext");
		String[] rules = StringUtils.split(TextTemp, RULE_LENGTH);

		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo((String) httpServletRequest.getParameter("prpLclaimRegistNo"));
			prpLregistText.setContext(rules[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("2"); // 类型2为不予立案类型保存在prplregistText表中
			prpLregistTextList.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList(prpLregistTextList);
		return registDto;
	}

	/**
	 * 根据报案号和保单号查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @throws Exception
	 */
	public void registCancelDtoToView(HttpServletRequest httpServletRequest, String registNo) throws Exception {
		// 初始化变量
		String conditions = "";// 查询条件语句
		String msg = ""; // 错误信息
		String strCancel = "";
		String strConditions = "";
		// 判断报案号是否合法
		if (registNo == null || registNo.equals("")) {
			Locale locale = ActionContext.getContext().getLocale();
			msg = LocalizedTextUtil.findDefaultText("claim.notReceiveReportNumber", locale);
			// msg = "没有接收到合法的报案号码！";
			throw new UserException(1, 3, "備案註銷", msg);
		}
		// 报案注销不提供模糊查询
		registNo = StringUtils.rightTrim(registNo);
		conditions = " RegistNo='" + registNo + "' ";
		strConditions = conditions;
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "prplregist", "", "ComCode");
		// 查询报案信息
		// 判断报案号码是否存在
		if (!prpLregistService.isExist(registNo)) {
			Locale locale = ActionContext.getContext().getLocale();
			msg = LocalizedTextUtil.findDefaultText("claim.notReceiveReportNumber", locale);
			// msg = "没有接收到合法的报案号！";
			throw new UserException(1, 3, "備案註銷", msg);
		}
		// 判断是否已经立案
		BLPrpLclaimFacade blPrpLclaimFacade = new BLPrpLclaimFacade();
		int intClaimSize = blPrpLclaimFacade.getCount(strConditions);
		List<Prplregistrpolicy> registPolicyList = prpLregistrpolicyService.findByRegistNo(registNo);
		if (intClaimSize > 0) {
			// 需要判断是否所有的都已经立案，如果没有全部立案的话，还是可以进行报案注销的。
			if (registPolicyList.size() == intClaimSize) {
				Locale locale = ActionContext.getContext().getLocale();
				msg = LocalizedTextUtil.findDefaultText("claim.hasBeenFiled", locale);
				// msg = "该报案已经立案，请在立案中拒赔案件！";
				throw new UserException(1, 3, "備案註銷", msg);
			}
		}
		// 得到多行报案主表信息
		List<PrpLregist> registList = prpLregistService.findByConditions(conditions);
		PrpLregist prpLregist = new PrpLregist();
		PrpLregistText prpLregistText = new PrpLregistText();
		Iterator<PrpLregist> it = registList.iterator();
		if (it.hasNext()) {
			prpLregist = (PrpLregist) it.next();
		} else {
			Locale locale = ActionContext.getContext().getLocale();
			msg = LocalizedTextUtil.findDefaultText("claim.notRead", locale);
			// msg = "您无权读取报案信息！";
			throw new UserException(1, 3, "備案註銷", msg);
		}
		// 增加强三多保单等的判断
		RegistDto registDto = new RegistDto();
		registDto.setPrpLregist(prpLregist);
		registDto.setPrpLRegistRPolicyList(registPolicyList);
		httpServletRequest.setAttribute("registDto", registDto);
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());
		// 该报案是否已拒赔
		if (!(prpLregist.getCancelDate() == null || prpLregist.getDealerCode() == null || prpLregist.getDealerCode().trim().equals(""))) {
			strCancel = "1";
		}
		// 如已经注销，则取得注销原因
		conditions = " RegistNo='" + registNo + "'  and texttype = '2'";
		// 得到備案注銷原因
		List<PrpLregistText> registTextList = prpLregistTextService.findByRegistNo(registNo, "2");
		String tempContext = "";
		if (registTextList != null) {
			Iterator<PrpLregistText> iterator = registTextList.iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistTextTemp = (PrpLregistText) iterator.next();
				String tmp = StringConvert.encode(DataUtils.dbNullToEmpty(prpLregistTextTemp.getContext()));
				tempContext = tempContext + StringUtils.replace(tmp, "\r\n", "\\r\\n") + "\\r\\n";
			}
		}
		prpLregistText.setContext(tempContext);
		prpLregistText.getId().setTextType("2");
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		httpServletRequest.setAttribute("prpLregistText", prpLregistText);
		httpServletRequest.setAttribute("strCancel", strCancel);
	}

	/**
	 * 报案注销时数据整理
	 * @param httpServletRequest
	 * @return RegistDto 报案数据传输数据结构
	 * @throws Exception
	 */
	public RegistDto cancelViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 初始化变量
		String msg = "";
		// 取得当前用户对象
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 得到报案号
		String strRegistNo = StringUtils.rightTrim(httpServletRequest.getParameter("prpLclaimRegistNo"));
		// 判断报案号码是否存在
		if (!prpLregistService.isExist(strRegistNo)) {
			Locale locale = ActionContext.getContext().getLocale();
			msg = LocalizedTextUtil.findDefaultText("claim.notReceiveReportNumber", locale);
			// msg = "没有接收到合法的报案号！";
			throw new UserException(1, 3, "備案註銷", msg);
		}
		// 得到报案对象
		registDto = registService.findByPrimaryKey(strRegistNo);
		// 收集报案注销原因
		List<PrpLregistText> prpLregistTextList = new ArrayList<PrpLregistText>();
		String TextTemp = httpServletRequest.getParameter("prpLregistTextContextInnerHTML");
		String LINECR = "\r\n";
		String[] rules = StringUtils.split(TextTemp, LINECR);
		// prpLregistTextList 中已经包含所有其它的数据
		// 得到连接串,下面将其切分到数组
		for (int k = 0; k < rules.length; k++) {
			PrpLregistText prpLregistText = new PrpLregistText();
			prpLregistText.getId().setRegistNo(strRegistNo);
			prpLregistText.setContext(rules[k]);
			prpLregistText.getId().setLineNo(k + 1);
			prpLregistText.getId().setTextType("2");
			prpLregistTextList.add(prpLregistText);
		}
		// 装入RegistDto
		registDto.setPrpLregistTextList(prpLregistTextList);
		// 设置报案信息
		PrpLregist prpLregist = registDto.getPrpLregist();
		prpLregist.setCancelDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLregist.setDealerCode(user.getUserCode());
		registDto.setPrpLregist(prpLregist);
		return registDto;
	}
	/**
	 * 根据同险编号查询保单对象
	 * @param sameAddressNo
	 * @param damagerStartDate
	 * @param damageHour
	 * @return
	 * @throws Exception
	 */
	public void findSameAddressPolicy(HttpServletRequest request) throws Exception {
		String sameAddressNo = request.getParameter("prpCaddressSameaddressNo");
		String damageDate = request.getParameter("prpLregistDamageStartDate");
		String damageHour = request.getParameter("prpLregistDamageStartHour");
		List<String> list = prpCaddressService.findPolicyBySameAddressNo(sameAddressNo);
		List<PolicyDto> policyDtoList = new ArrayList<PolicyDto>();
		PolicyDto policyDto = null;
		String othFlag = null;
		PrpCmain prpCmain = null;
		for (String policyNo : list) {
			policyDto = new PolicyDto();
			prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
			othFlag = prpCmain.getOthFlag();
			if (othFlag.length() > 3 && "1".equals(othFlag.substring(3, 4))) {// 注销标识
				prpCmain.setColorFlag("1");
			}
			Calendar endDate = Calendar.getInstance();
			endDate.setTime(prpCmain.getEndDate());
			endDate.set(Calendar.HOUR_OF_DAY, prpCmain.getEndHour());
			if (endDate.after(Calendar.getInstance().getTime())) {
				prpCmain.setColorFlag("1");// 过期保单显示为红色，过期保单的处理
			}
			policyDto.setPrpCmain(prpCmain);
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			policyDto.setPrpCaddressList(this.prpCaddressService.findPrpCaddress(queryRule));
			policyDtoList.add(policyDto);
		}
		request.setAttribute("policyDtoList", policyDtoList);
	}
	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public RegistDto getRegistDto() {
		return registDto;
	}

	public void setRegistDto(RegistDto registDto) {
		this.registDto = registDto;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public PrpLregistLogService getPrpLregistLogService() {
		return prpLregistLogService;
	}

	public void setPrpLregistLogService(PrpLregistLogService prpLregistLogService) {
		this.prpLregistLogService = prpLregistLogService;
	}

	public PrpCengageService getPrpCengageService() {
		return prpCengageService;
	}

	public void setPrpCengageService(PrpCengageService prpCengageService) {
		this.prpCengageService = prpCengageService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		super.setPrpCmainService(prpCmainService);
		this.prpCmainService = prpCmainService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		super.setPolicyService(policyService);
		this.policyService = policyService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		super.setCodeService(codeService);
		this.codeService = codeService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public PrpPheadService getPrpPheadService() {
		return prpPheadService;
	}

	public void setPrpPheadService(PrpPheadService prpPheadService) {
		this.prpPheadService = prpPheadService;
	}

	public PrpCitemCarService getPrpCitemCarService() {
		return prpCitemCarService;
	}

	public void setPrpCitemCarService(PrpCitemCarService prpCitemCarService) {
		this.prpCitemCarService = prpCitemCarService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
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

	public PrpCmainCarGoSubService getPrpCmainCarGoSubService() {
		return prpCmainCarGoSubService;
	}

	public void setPrpCmainCarGoSubService(PrpCmainCarGoSubService prpCmainCarGoSubService) {
		this.prpCmainCarGoSubService = prpCmainCarGoSubService;
	}

	public PrpCcarDriverService getPrpCcarDriverService() {
		return prpCcarDriverService;
	}

	public void setPrpCcarDriverService(PrpCcarDriverService prpCcarDriverService) {
		this.prpCcarDriverService = prpCcarDriverService;
	}

	public PrpCplanService getPrpCplanService() {
		return prpCplanService;
	}

	public void setPrpCplanService(PrpCplanService prpCplanService) {
		this.prpCplanService = prpCplanService;
	}

	public PrpCopyInsuredService getPrpCopyInsuredService() {
		return prpCopyInsuredService;
	}

	public void setPrpCopyInsuredService(PrpCopyInsuredService prpCopyInsuredService) {
		this.prpCopyInsuredService = prpCopyInsuredService;
	}

	public PrpCopyItemCarService getPrpCopyItemCarService() {
		return prpCopyItemCarService;
	}

	public void setPrpCopyItemCarService(PrpCopyItemCarService prpCopyItemCarService) {
		this.prpCopyItemCarService = prpCopyItemCarService;
	}
	
}
