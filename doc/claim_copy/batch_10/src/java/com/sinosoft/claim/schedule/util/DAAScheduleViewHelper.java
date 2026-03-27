package com.sinosoft.claim.schedule.util;

import ins.framework.common.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.util.UIPowerInterface;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * <p>
 * Title: ScheduleViewHelper
 * </p>
 * <p>
 * Description:调度ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2013
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */
public class DAAScheduleViewHelper extends ScheduleViewHelper {
	/** 调度服务 */
	private ScheduleService scheduleService;
	/** 报案服务 */
	private RegistService registService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 代码服务 */
	private CodeService codeService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 调度任务/查勘任务信息服务 */
	private PrpLscheduleMainWFService prpLscheduleMainWFService;

	/**
	 * 默认构造方法
	 */
	public DAAScheduleViewHelper() {
	}

	/**
	 * 保存查勘时查勘页面数据整理. 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理。
	 * @param httpServletRequest
	 * @return scheduleDto 查勘数据传输数据结构
	 * @throws Exception
	 */
	public ScheduleDto viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		// 继承对schedule,scheduleText表的赋值
		ScheduleDto scheduleDto = super.viewToDto(httpServletRequest);

		/*---------------------调度标底prpLScheduleItem------------------------------------*/
		/*---------------------查勘标底prpLcheckItem------------------------------------*/

		ArrayList<PrpLscheduleItem> scheduleItemList = new ArrayList<PrpLscheduleItem>();
		PrpLscheduleItem prpLscheduleItem = null;
		// 从界面得到输入数组 後来shceudleid不需要自动加1了
		String[] prpLscheduleItemScheduleId = httpServletRequest.getParameterValues("prpLscheduleItemScheduleID");
		String prpLscheduleItemRegistNo = (String) httpServletRequest.getAttribute("registNo");
		String[] prpLscheduleItemItemNo = httpServletRequest.getParameterValues("prpLscheduleItemItemNo");
		String[] prpLscheduleItemInsureCarFlag = httpServletRequest.getParameterValues("prpLscheduleItemInsureCarFlag");

		String[] prpLscheduleItemSelectSend = httpServletRequest.getParameterValues("prpLscheduleItemSelectSend");
		// 未加双代前,查勘与定损项目的调度中心为同一个,但加上双代後,可能每个都不同,所以要单独区分
		String[] prpLscheduleItemClaimComCode = httpServletRequest.getParameterValues("prpLscheduleItemClaimComCode");
		String[] prpLscheduleItemSurveyType = httpServletRequest.getParameterValues("surveyType");
		String[] prpLscheduleItemCheckSite = httpServletRequest.getParameterValues("prpLscheduleItemCheckSite");
		String[] prpLscheduleItemLicenseNo = httpServletRequest.getParameterValues("prpLscheduleItemLicenseNo");
		String[] prpLscheduleItemScheduleObjectID = httpServletRequest.getParameterValues("prpLscheduleItemScheduleObjectID");
		String[] prpLscheduleItemScheduleObjectName = httpServletRequest.getParameterValues("prpLscheduleItemScheduleObjectName");

		String[] prpLscheduleItemCommendRepairFactoryName = httpServletRequest.getParameterValues("prpLscheduleItemCommendRepairFactoryName");

		String prpLscheduleMainWFInputDate = httpServletRequest.getParameter("prpLscheduleMainWFInputDate");

		// Reason:在定损调度页面中增加修理厂报损金额、修理厂联系电话、紧急位标志位
		String[] prpLscheduleItemFactoryEstimateLoss = httpServletRequest.getParameterValues("prpLscheduleItemFactoryEstimateLoss");
		String[] prpLscheduleItemFactoryPhone = httpServletRequest.getParameterValues("prpLscheduleItemFactoryPhone");
		String[] exigenceGree = httpServletRequest.getParameterValues("exigenceGree");

		String[] prpLscheduleItemResultInfo = httpServletRequest.getParameterValues("prpLscheduleItemResultInfo");
		String[] prpLscheduleItemBookFlag = httpServletRequest.getParameterValues("prpLscheduleItemBookFlag");
		String[] prpLscheduleItemScheduleType = httpServletRequest.getParameterValues("prpLscheduleItemScheduleType");
		String[] prpLscheduleItemFlag = httpServletRequest.getParameterValues("prpLscheduleItemFlag");
		String[] surveyTimes = httpServletRequest.getParameterValues("prpLscheduleItemSurveyTimes");// 是否为已经调度过的？

		String[] prpLscheduleItemInputDate = httpServletRequest.getParameterValues("prpLscheduleItemInputDate");
		String[] prpLscheduleItemOperatorCode = httpServletRequest.getParameterValues("prpLscheduleItemOperatorCode");

		int maxRow = Integer.parseInt((String) httpServletRequest.getParameter("maxrow"));

		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// reason:调度保存提交的下一个节点的人员和节点名称
		String[] nextHandlerCode = httpServletRequest.getParameterValues("nextHandlerCode"); // 指定下一个节点操作人代码
		String[] nextHandlerName = httpServletRequest.getParameterValues("nextHandlerName"); // 指定下一个节点操作人姓名
		String[] strNextNode = httpServletRequest.getParameterValues("nextNodeNo"); // 指定下一个节点名

		// 调度时往客户、调度人员发送短信
		// 初始化变量
		List<PrpLscheduleItem> sMCComCodeInfoDtoList = new ArrayList<PrpLscheduleItem>();
		List<?> sMSendSMListDtoList = new ArrayList<Object>();
		List<?> smcResultDtoList = new ArrayList<Object>();

		// 对象赋值
		// 调度标的部分开始
		// 整理定损调度部分的标的内容
		for (int index = 0; index < maxRow; index++) {
			prpLscheduleItem = new PrpLscheduleItem();
			prpLscheduleItem.getId().setScheduleID(Integer.parseInt(prpLscheduleItemScheduleId[index]));
			prpLscheduleItem.getId().setRegistNo(prpLscheduleItemRegistNo);
			prpLscheduleItem.getId().setItemNo(Integer.parseInt(prpLscheduleItemItemNo[index]));
			prpLscheduleItem.setInsureCarFlag(prpLscheduleItemInsureCarFlag[index]);

			prpLscheduleItem.setClaimComCode(prpLscheduleItemClaimComCode[index]);
			// 表示是否选中
			prpLscheduleItem.setSelectSend(prpLscheduleItemSelectSend[index]);
			// 如果选中的话，surveyTimes=1
			prpLscheduleItem.setSurveyTimes(0);

			// 往定损人员、客户发送短信 begin
			// String[] strScheduleSmcSend =
			// httpServletRequest.getParameterValues("prpLCheckSelectSend");
			// 往定损人员发送短信 end

			// 判断是不是进行了新的调度选择判断
			// reason:调度处理的人是不一样的，需要保留原来的人
			prpLscheduleItem.setInputDate(new DateTime(prpLscheduleMainWFInputDate, DateTime.YEAR_TO_DAY));
			// 已经调度过的日期处理
			if (prpLscheduleItem.getSelectSend().equals("1") && surveyTimes[index].equals("1")) {
				prpLscheduleItem.setOperatorCode(user.getUserCode());
				if (prpLscheduleItemInputDate[index] != null && prpLscheduleItemInputDate[index].length() == 10) {
					prpLscheduleItem.setInputDate(new DateTime(prpLscheduleItemInputDate[index], DateTime.YEAR_TO_DAY));

				}
				prpLscheduleItem.setOperatorCode(prpLscheduleItemOperatorCode[index]);
			}
			if (prpLscheduleItem.getSelectSend().equals("1") && surveyTimes[index].equals("0")) {
				prpLscheduleItem.setOperatorCode(user.getUserCode());
				prpLscheduleItem.setInputDate(new DateTime(prpLscheduleMainWFInputDate, DateTime.YEAR_TO_DAY));

			}
			if (prpLscheduleItem.getSelectSend().equals("1")) {
				prpLscheduleItem.setSurveyTimes(1);
			}

			prpLscheduleItem.setSurveyType(prpLscheduleItemSurveyType[index]);
			prpLscheduleItem.setCheckSite(prpLscheduleItemCheckSite[index]);
			// Reason:在定损调度页面中增加修理厂报损金额、修理厂联系电话、紧急位标志位
			prpLscheduleItem.setFactoryEstimateLoss(Double.parseDouble(DataUtils.nullToZero(prpLscheduleItemFactoryEstimateLoss[index])));
			prpLscheduleItem.setFactoryPhone(prpLscheduleItemFactoryPhone[index]);
			prpLscheduleItem.setExigenceGree(exigenceGree[index]);
			prpLscheduleItem.setLicenseNo(prpLscheduleItemLicenseNo[index]);
			prpLscheduleItem.setCommendRepairFactoryName(prpLscheduleItemCommendRepairFactoryName[index]);

			if (prpLscheduleItemScheduleObjectID[index].trim().length() < 1){
				prpLscheduleItemScheduleObjectID[index] = "_";
			}
			prpLscheduleItem.setScheduleObjectID(prpLscheduleItemScheduleObjectID[index]);
			prpLscheduleItem.setScheduleObjectName(prpLscheduleItemScheduleObjectName[index]);

			prpLscheduleItem.setResultInfo(prpLscheduleItemResultInfo[index]);
			prpLscheduleItem.setBookFlag(prpLscheduleItemBookFlag[index]);
			prpLscheduleItem.setScheduleType(prpLscheduleItemScheduleType[index]);

			prpLscheduleItem.setFlag(prpLscheduleItemFlag[index]);
			// reason:调度保存提交的下一个节点的人员和节点名称,目前情况
			prpLscheduleItem.setNextNodeNo(strNextNode[index]);
			prpLscheduleItem.setNextHandlerCode(nextHandlerCode[index]);
			prpLscheduleItem.setNextHandlerName(nextHandlerName[index]);
			// 加入调度标的集合
			scheduleItemList.add(prpLscheduleItem);
		}
		// 调度集合中加调度标的
		scheduleDto.setPrpLscheduleItemList(scheduleItemList);
		// 调度集合中加查勘标的

		// 发送短信时查勘或者定损人员没有手机号码时，给出提示

		scheduleDto.setSmSendSMListList(sMSendSMListDtoList);
		scheduleDto.setSmcComCodeInfoList(sMCComCodeInfoDtoList);

		httpServletRequest.setAttribute("smcResultDtoList", smcResultDtoList);
		// 发送短信时查勘或者定损人员没有手机号码时，给出提示 end

		/*---------------------报案信息补充说明 PrpLregistExt ------------------------------------*/
		ArrayList<PrpLregistExt> prpLregistExtDtoList = new ArrayList<PrpLregistExt>();
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
		// 报案扩展信息 部分开始
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
				prpLregistExtDtoList.add(prpLregistExt);
			}
			// 报案集合中加入损失部位
			scheduleDto.setPrpLregistExtList(prpLregistExtDtoList);
		}

		return scheduleDto;
	}

	/**
	 * 取初始化信息需要的数据的整理. 填写查勘单时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等。取这些信息需要一些入参，
	 * 考虑到接口的一致性，将这些入参作为Dto方式传入，Dto利用聚合而非继承的方式。 整理采用继承的方式分层处理，具体的逻辑放在险种险类子类中整理.
	 * @param httpServletRequest
	 * @return RequestDto 取初始化信息需要的数据
	 * @throws Exception
	 */
	public ScheduleDto iniViewToDto(HttpServletRequest httpServletRequest) throws Exception {
		ScheduleDto scheduleDto = new ScheduleDto();
		return scheduleDto;

	}

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception {
		// 得到request的PrpLsheduleForm用於显示
		PrpLscheduleMainWF prpLscheduleMainWFDto = scheduleDto.getPrpLscheduleMainWF();

		httpServletRequest.setAttribute("prpLscheduleMainWFDto", prpLscheduleMainWFDto);
		// 得到request的prpscheduleItemForm 用於显示
		PrpLscheduleItem prpLscheduleItemDto = new PrpLscheduleItem();
		prpLscheduleItemDto.setScheduleItemList(scheduleDto.getPrpLscheduleItemList());
		httpServletRequest.setAttribute("prpLscheduleItemDto", prpLscheduleItemDto);
	}

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo
	 * @param editType
	 * @param scheduleID
	 * @throws Exception
	 */
	public void scheduleDtoToView(HttpServletRequest httpServletRequest, String registNo, String editType, String scheduleID) throws Exception {

		// 取得当前用户信息，写操作员信息到查勘中
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		int intscheduleID = Integer.parseInt(scheduleID);

		// Reason：调度正处理需要根据不同的条件显示一个回退按钮(把案件由正在处理改为待处理)。
		// String handlerCode = httpServletRequest.getParameter("handlerCode");
		// String flagBit = "false";
		// if (!user.getUserCode().equals(handlerCode) &&
		// "EDIT".equals(editType)) {
		// flagBit = "false";
		// } else {
		// flagBit = "true";
		// }
		ScheduleDto scheduleDto = scheduleService.findByRegistNo(intscheduleID, registNo);

		// 根据查询出来的数据内容，给PrpLscheduleDto赋值
		PrpLscheduleMainWF prpLscheduleMainWF = scheduleDto.getPrpLscheduleMainWF();
		RegistDto registDto = registService.findByPrimaryKey(registNo);

		// reason 强制保单关联信息写到调度中
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());

		// 获得标的车驾驶员的信息
		List<PrpLdriver> prpLDriverList = (ArrayList<PrpLdriver>) registDto.getPrpLdriverList();
		String prpLdriverName = "";
		if (prpLDriverList != null) {
			for (Iterator<PrpLdriver> driverIterator = prpLDriverList.iterator(); driverIterator.hasNext();) {
				PrpLdriver prpLdriver = (PrpLdriver) driverIterator.next();
				if (prpLdriver.getId().getSerialNo() == 1) {
					prpLdriverName = prpLdriver.getDriverName();
				}
			}
		}
		httpServletRequest.setAttribute("prpLdriverName", prpLdriverName);
		// 保存代理人代码及名称
		String policyNo = httpServletRequest.getParameter("policyNo");
//		// 查询保单信息
//		PolicyDto policyDto = this.policyService.findByPrimaryKey(policyNo);
		// 如果查不到怎么办？ 写程序要注意的
		String agentCode = "";
		PrpCmain prpCmain = this.policyService.findPrpCmainDtoByPrimaryKey(policyNo);
		if (prpCmain != null) {
			agentCode = prpCmain.getAgentCode(); // 代理人代码
		}

		if (agentCode == null){
			agentCode = "";
		}

		prpLscheduleMainWF.setAgentCode(agentCode);
		prpLscheduleMainWF.setAgentName(this.codeService.translateAgentName(agentCode));// 得到代理人名称

		PrpLregist prpLregist = registDto.getPrpLregist();
		prpLregist.setReportHour(StringConvert.toStandardTime(prpLregist.getReportHour()));
		prpLregist.setReportMinute(prpLregist.getReportHour().substring(3, 5));
		prpLregist.setReportHour(prpLregist.getReportHour().substring(0, 2));
		prpLregist.setDamageStartHour(StringConvert.toStandardTime(prpLregist.getDamageStartHour()));
		prpLregist.setDamageStartMinute(prpLregist.getDamageStartHour().substring(3, 5));
		prpLregist.setDamageStartHour(prpLregist.getDamageStartHour().substring(0, 2));
		httpServletRequest.setAttribute("prpLregist", prpLregist);
		// 设置扩展属性
		prpLscheduleMainWF.setLinkerName(registDto.getPrpLregist().getLinkerName());
		prpLscheduleMainWF.setPhoneNumber(registDto.getPrpLregist().getPhoneNumber());
		prpLscheduleMainWF.setOperatorName(user.getUserName());
		prpLscheduleMainWF.setLicenseNo(registDto.getPrpLregist().getLicenseNo());
		// 增加调度报损金额

		prpLscheduleMainWF.setEstimateLoss(registDto.getPrpLregist().getEstimateLoss());
		if ("_".equals(prpLscheduleMainWF.getScheduleObjectID())) {
			prpLscheduleMainWF.setScheduleObjectID("");
		}
		// 设置到底是什么类型的保存,可能是取回类型的

		prpLscheduleMainWF.setSaveType(editType);

		// 给报案文件多行列表准备数据
		if (registDto.getPrpLregistTextList() != null) {
			Iterator<PrpLregistText> iterator = registDto.getPrpLregistTextList().iterator();
			while (iterator.hasNext()) {
				PrpLregistText prpLregistTextTemp = (PrpLregistText) iterator.next();
				if (StringUtils.rightTrim(prpLregistTextTemp.getId().getTextType()).equals("1")) {
					prpLscheduleMainWF.setRegistText(prpLscheduleMainWF.getRegistText() + prpLregistTextTemp.getContext());
				}
			}
		}

		// 设置查勘操作的状态为 案件修改 (正处理任务)
		if (scheduleDto.getPrpLclaimStatus() != null) {
			if (scheduleDto.getPrpLclaimStatus().getStatus().equals("7")) {
				scheduleDto.getPrpLclaimStatus().setStatus("3");
			}
			prpLscheduleMainWF.setStatus(scheduleDto.getPrpLclaimStatus().getStatus());
		} else {
			// 已提交，已经处理完毕的状态
			prpLscheduleMainWF.setStatus("4");
		}

		// 还要判断ClaimComCode为空的情况
		String nodeType = (String) httpServletRequest.getParameter("nodeType");
//		if (!"SHOW".equals(editType)) {
//			if (prpLscheduleMainWF.getClaimComCode().trim().length() > 0) {
//
//			}
//		}

		// 调度任务双代标识: 0 or null:非双代案件; 1:双代代调度案件(出险方) 2:双代部分委托他方调度案件(承保方) add
		String commiFlag = httpServletRequest.getParameter("commiFlag");

		// 因为双代处理任务时,显示信息也借用此函数,故要区分一下,双代任务是没有双代标志的.
		if (!(nodeType == null)) {
			if ("commi".equals(nodeType)) {
			} else {
				prpLscheduleMainWF.setCommiFlag(commiFlag);
			}
		} else {
			prpLscheduleMainWF.setCommiFlag(commiFlag);
		}

		scheduleDto.setPrpLscheduleMainWF(prpLscheduleMainWF);

		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, scheduleDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, scheduleDto);
		// 设置主查勘信息内容到窗体表单
		httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWF);
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, scheduleDto, editType);
		// 设置工作流下一个节点提交的配置信息
		if (!prpLscheduleMainWF.getStatus().equals("4")) {
			getSubmitNodes(httpServletRequest);
		} else {
			// 已经是展现了
			httpServletRequest.setAttribute("finishSubmit", "");
		}
		setProvinceCode(httpServletRequest, registDto.getPrpLregist().getComCode());

		// 给报案信息补充说明多行列表准备数据
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo());
		prpLregistExt.setRiskCode(scheduleDto.getPrpLscheduleMainWF().getRiskCode());
		arrayListRegistExt = scheduleDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExt", prpLregistExt);
		setProvinceCode(httpServletRequest, registDto.getPrpLregist().getComCode());
		// 看看查勘没
		String isChecked = "false";
		SwfLog swfLogDtoTemp = new SwfLog();
		List<SwfLog> swfLogDtoList = this.getWorkFlowService().findNodesByConditions("businessno = '" + registNo + "'");
		for (Iterator<SwfLog> iterator = swfLogDtoList.iterator(); iterator.hasNext();) {
			swfLogDtoTemp = iterator.next();
			if ("check".equals(swfLogDtoTemp.getNodeType())) {
				isChecked = "true";
				break;
			}
		}
		httpServletRequest.setAttribute("isChecked", isChecked);
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
		scheduleDtoToView(httpServletRequest, registNo, editType, "1");
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
			pathList = workFlowViewHelper.getNextSumbitNodes(modelNo, nodeNo);
			Iterator<?> it = pathList.iterator();
			if (it.hasNext()) {
				SwfPath swfPathDtoTemp = (SwfPath) it.next();
				nextNodeNo = swfPathDtoTemp.getEndNodeNo();
				swfPathDto.setNextNodeNo(nextNodeNo);
			}
		}
		swfPathDto.setPathList(pathList);
		httpServletRequest.setAttribute("pathList", pathList);
		httpServletRequest.setAttribute("swfPath", swfPathDto);
	}

	/**
	 * 根据报案号查询查勘信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 立案号
	 * @param claimNo 报案号
	 * @throws Exception
	 */

	/**
	 * 根据Dto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param scheduleDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto, String editType) throws Exception {

		List<PrpLscheduleItem> scheduleItemListTemp = new ArrayList<PrpLscheduleItem>();
		List<PrpLscheduleItem> scheduleItemList = scheduleDto.getPrpLscheduleItemList();
		scheduleDto.setPrpLscheduleItemList(scheduleItemList);
		// 要过滤掉不同的scheduleType的内容,只有查勘定损才过滤的。。报案保存的是_,变成""
		for (int i = 0; i < scheduleItemList.size(); i++) {
			PrpLscheduleItem prpLscheduleItemTemp = new PrpLscheduleItem();
			prpLscheduleItemTemp = scheduleItemList.get(i);
			if (prpLscheduleItemTemp.getScheduleObjectID().equals("_")) {
				prpLscheduleItemTemp.setScheduleObjectID("");
				prpLscheduleItemTemp.setScheduleObjectName("");
			}
			/*
			 * //如果主车是到查勘去了的话，在定损调度里不能选择，除非查勘已经提交了。。 }
			 */
			// 如果没有调度过，默认进去为0,就是没有被选中
			if (prpLscheduleItemTemp.getSurveyTimes() == 0) {
				prpLscheduleItemTemp.setSelectSend("0");
			}
			if ((!"SHOW".equals(editType)) && prpLscheduleItemTemp.getClaimComCode() != null && prpLscheduleItemTemp.getClaimComCode().length() > 0) {
				// reason:由於这边目前不做双代，所以先不考虑这个问题。不做任何过滤数据
				scheduleItemListTemp.add(prpLscheduleItemTemp);
			} else {
				scheduleItemListTemp.add(prpLscheduleItemTemp);
			}

		}

		PrpLscheduleItem prpLscheduleItem = new PrpLscheduleItem();
		prpLscheduleItem.setScheduleItemList(scheduleItemListTemp);
		httpServletRequest.setAttribute("prpLscheduleItem", prpLscheduleItem);
	}

	/**
	 * 根据PrpPrepayDto中的已经设置的代码内容，对代码进行名称转换
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLscheduleDto 查勘的数据类
	 * @throws Exception
	 */
	private void changeCodeToName(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception {
		// (1)对业务归属结构进行转换
		String claimComCode = scheduleDto.getPrpLscheduleMainWF().getClaimComCode();
		String claimComName = this.codeService.translateComCode(claimComCode, true);
		scheduleDto.getPrpLscheduleMainWF().setClaimComName(claimComName);
		// (2)对操作员进行处理
		String operatorCode = scheduleDto.getPrpLscheduleMainWF().getOperatorCode();
		String operatorName = this.codeService.translateUserCode(operatorCode, true);
		scheduleDto.getPrpLscheduleMainWF().setOperatorName(operatorName);
	}

	/**
	 *获取调度取回任务列表
	 * @param httpServletRequest
	 * @param conditions
	 * @param scheduleType
	 * @throws Exception
	 */
	public void getScheuleGetBackQueryList(HttpServletRequest httpServletRequest, String conditions, String scheduleType) throws Exception {
		// 获得调度取回的任务的列表
		getScheuleCheckList(httpServletRequest, conditions, "schel");

	}

	/**
	 * 查询查勘调度的信息
	 * @param httpServletRequest HttpServletRequest
	 * @param conditions String
	 * @throws Exception
	 */
	public void getScheuleCheckList(HttpServletRequest httpServletRequest, String conditions, String scheduleType) throws Exception {
		PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();
		PrpLscheduleItem prpLscheduleItem = new PrpLscheduleItem();
		ArrayList<PrpLscheduleMainWF> scheduleList = new ArrayList<PrpLscheduleMainWF>();
		List<PrpLscheduleItem> list = new ArrayList<PrpLscheduleItem>();
		Collection<?> scheduleListTemp = new ArrayList<PrpLscheduleMainWF>();

		// 每页显示的行数
		String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
		String pageNo = httpServletRequest.getParameter("pageNo");
		//
		if (pageNo == null || pageNo.trim().equals(""))
			pageNo = "1";

		int intRecordPerPage = Integer.parseInt(recordPerPage);
		int intPageNo = Integer.parseInt(pageNo);

		int maxQueryCount = Integer.parseInt(DataUtils.nullToZero(AppConfig.get("sysconst.MaxQueryCount")));
		int count = 0;

		if (scheduleType.equals("sched")) {
			// 限制条件!!!
			// 判断条件限制
			count = this.scheduleService.findScheduleMainWFCountByConditon(conditions);
			if (maxQueryCount != 0 && count > maxQueryCount) {
				throw new UserException(1, 3, "0000", "查詢結果個數超過系統限制");
			}
			Page page = this.scheduleService.findByQueryConditions(conditions, intPageNo, intRecordPerPage);
			scheduleListTemp = page.getResult();
			// 1.转换操作员的姓名,和调度员
			Iterator<?> it = scheduleListTemp.iterator();
			while (it.hasNext()) {
				PrpLscheduleMainWF prpLscheduleMainWF1 = (PrpLscheduleMainWF) it.next();
				prpLscheduleMainWF1.setOperatorName(this.codeService.translateUserCode(prpLscheduleMainWF1.getOperatorCode(), true));
				scheduleList.add(prpLscheduleMainWF1);
			}
			prpLscheduleMainWF.setScheduleList(scheduleList);
			httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWF);
		} else {
			// 限制条件
			// 判断条件限制
			count = this.scheduleService.findScheduleItemCountByConditon(conditions);
			if (maxQueryCount != 0 && count > maxQueryCount) {
				throw new UserException(1, 3, "0000", "查詢結果個數超過系統限制");
			}
			Page page = this.scheduleService.findScheduleItemCountByConditon(conditions, intPageNo, intRecordPerPage);
			scheduleListTemp = page.getResult();
			// 1.转换操作员的姓名,和调度员
			Iterator<?> it = scheduleListTemp.iterator();
			while (it.hasNext()) {
				PrpLscheduleItem prpLscheduleItem1 = new PrpLscheduleItem();
				prpLscheduleItem1 = (PrpLscheduleItem) it.next();
				prpLscheduleItem1.setOperatorName(this.codeService.translateUserCode(prpLscheduleItem1.getOperatorCode(), true));
				list.add(prpLscheduleItem1);
			}
			prpLscheduleItem.setScheduleItemList(list);
			httpServletRequest.setAttribute("prpLscheduleItemDto", prpLscheduleItem);
		}

	}

	/**
	 * 查询定损调度的信息
	 * @param httpServletRequest HttpServletRequest
	 * @param conditions String
	 * @throws Exception
	 */
	public void getScheuleCertainLossList(HttpServletRequest httpServletRequest, String conditions) throws Exception {
		PrpLscheduleItem prpLscheduleItem = new PrpLscheduleItem();
		Collection<PrpLscheduleItem> scheduleList = new ArrayList<PrpLscheduleItem>();
		Collection<PrpLscheduleItem> scheduleListTemp = new ArrayList<PrpLscheduleItem>();
		scheduleListTemp = this.scheduleService.findItemByConditions(conditions);
		// 1.转换操作员的姓名,和调度员
		Iterator<PrpLscheduleItem> it = scheduleListTemp.iterator();

		while (it.hasNext()) {
			PrpLscheduleItem prpLscheduleItem1 = new PrpLscheduleItem();
			prpLscheduleItem1 = (PrpLscheduleItem) it.next();
			prpLscheduleItem1.setOperatorName(this.codeService.translateUserCode(prpLscheduleItem1.getOperatorCode(), true));

			scheduleList.add(prpLscheduleItem1);
		}

		prpLscheduleItem.setScheduleItemList(scheduleList);
		httpServletRequest.setAttribute("prpLscheduleItem", prpLscheduleItem);
		httpServletRequest.setAttribute("scheduleType", "schel");

	}

	/**
	 * 查询已经调度出去的，可以进行处理的信息（打算从工作流上获得了）
	 * @param httpServletRequest HttpServletRequest
	 * @param conditions String
	 * @throws Exception
	 */
	public Page getNextTaskList(String conditions, int pageNo, int pageSize) throws Exception {
		Page page = this.workFlowService.findNodesByConditions(conditions, pageNo, pageSize);
		return page;

	}

	/**
	 * 填写查勘页面及查询查勘request的生成.
	 * 填写查勘时页面需要一定的初始化信息，如地区代码、定额标的信息、车型种类等，将这些信息取出並放入request。
	 * 整理采用继承的方式分层处理，险种险类特有数据放在险种险类子类中整理.
	 * @param httpServletRequest 返回给页面的request
	 * @param proposalIniDto 取出的初始化信息Dto
	 * @throws Exception
	 */

	public void registDtoToView(HttpServletRequest httpServletRequest, String scheduleType, String registNo, String editType) throws Exception {
		// 按照类型进行节点的查询，因为所有数据都已经在报案中保存了。
		// 取得当前用户信息，写操作员信息到查勘中

		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		ScheduleDto scheduleDto = scheduleService.findByRegistNo(1, registNo);
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		String riskCode = httpServletRequest.getParameter("riskCode"); // 工作流logno

		// reason: 为非车险加入查勘调度节点
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		PrpLregist prpLregist = registDto.getPrpLregist();
		httpServletRequest.setAttribute("prpLregistRPolicyNo", registDto.getPrpLRegistRPolicyOfCompel());

		String policyNo = httpServletRequest.getParameter("policyNo");

		// 根据查询出来的数据内容，给PrpLscheduleDto赋值
		PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();
		if (scheduleDto.getPrpLscheduleMainWF() == null) {
			prpLscheduleMainWF.getId().setRegistNo(registNo);
			prpLscheduleMainWF.setPolicyNo(policyNo);
			prpLscheduleMainWF.setClaimComCode(prpLregist.getComCode());
			prpLscheduleMainWF.setClaimComName(prpLregist.getComName());

		} else {
			prpLscheduleMainWF = scheduleDto.getPrpLscheduleMainWF();
		}

		// 设置扩展属性
		prpLscheduleMainWF.setRiskCode(riskCode);
		prpLscheduleMainWF.setLinkerName(DataUtils.nullToEmpty(prpLregist.getLinkerName()));
		prpLscheduleMainWF.setPhoneNumber(DataUtils.nullToEmpty(prpLregist.getPhoneNumber()));
		;
		prpLscheduleMainWF.setOperatorCode(user.getUserCode());
		prpLscheduleMainWF.setOperatorName(user.getUserName());
		prpLscheduleMainWF.setInputDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLscheduleMainWF.setInputHour(DateTime.current().getHour());
		prpLscheduleMainWF.setLicenseNo(prpLregist.getLicenseNo());
		prpLscheduleMainWF.setScheduleObjectID("");
		prpLscheduleMainWF.setEstimateLoss(prpLregist.getEstimateLoss());

		// 得到部门列表
		PrpDcompany prpDcompany = new PrpDcompany();
		if (prpDcompany != null) {
			prpLscheduleMainWF.setScheduleObjectID(prpDcompany.getComCode());
			prpLscheduleMainWF.setScheduleObjectName(prpDcompany.getComCName());
		}
		// 查询保单信息

		if (policyNo != null && policyNo.length() > 0) {
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			String agentCode = "";
			if (prpCmain != null) {
				agentCode = prpCmain.getAgentCode(); // 代理人代码
			}
			prpLscheduleMainWF.setAgentCode(agentCode);
			prpLscheduleMainWF.setAgentName(this.codeService.translateAgentName(agentCode));// 得到代理人名称
		}
		// 默认调度地址为出险地址
		prpLscheduleMainWF.setCheckSite(prpLregist.getDamageAddress());
		// 设置到底是什么类型的保存
		prpLscheduleMainWF.setSaveType(editType);
		if (registDto.getPrpLregistTextList().size() > 0) {
			for (int i = 0; i < registDto.getPrpLregistTextList().size(); i++) {
				PrpLregistText prpLregistTextDto = new PrpLregistText();
				prpLregistTextDto = (PrpLregistText) registDto.getPrpLregistTextList().get(i);
				prpLscheduleMainWF.setRegistText(prpLscheduleMainWF.getRegistText() + prpLregistTextDto.getContext());
			}
		}

		// 已经处理完毕的状态
		prpLscheduleMainWF.setStatus("2");

		scheduleDto.setPrpLscheduleMainWF(prpLscheduleMainWF);

		// 设置相关代码的中文转换
		changeCodeToName(httpServletRequest, scheduleDto);
		// 设置窗体表单中各个多选框中列表信息的内容
		setSelectionList(httpServletRequest, scheduleDto);
		// 设置主查勘信息内容到窗体表单
		httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWF);
		// 只有定损调度才和标的有关系，查勘调度，只要保存主表信息，就可以了。
		// 设置各个子表信息项到窗体表单
		setSubInfo(httpServletRequest, scheduleDto);
		// 检查节点是不是可以提交
		WorkFlowViewHelper workFlowViewHelper = new WorkFlowViewHelper();
		String msg = workFlowViewHelper.checkNodeSubmit(swfLogFlowID, swfLogLogNo);
		httpServletRequest.setAttribute("finishSubmit", msg);

		// 根据类型整理scheduleItem
		// 设置工作流下一个节点提交的配置信息
		getSubmitNodes(httpServletRequest);

		// 给报案信息补充说明多行列表准备数据
		List<PrpLregistExt> arrayListRegistExt = new ArrayList<PrpLregistExt>();
		PrpLregistExt prpLregistExt = new PrpLregistExt();
		prpLregistExt.getId().setRegistNo(scheduleDto.getPrpLscheduleMainWF().getId().getRegistNo());
		prpLregistExt.setRiskCode(scheduleDto.getPrpLscheduleMainWF().getRiskCode());
		arrayListRegistExt = scheduleDto.getPrpLregistExtList();
		prpLregistExt.setRegistExtList(arrayListRegistExt);
		httpServletRequest.setAttribute("prpLregistExtDto", prpLregistExt);

		setProvinceCode(httpServletRequest, registDto.getPrpLregist().getComCode());

	}

	/**
	 * 根据Dto中的各子表内的信息填充界面
	 * @param httpServletRequest 返回给页面的request
	 * @param scheduleDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSubInfo(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception {

		List<PrpLscheduleItem> scheduleItemListTemp = new ArrayList<PrpLscheduleItem>();
		List<PrpLscheduleItem> scheduleItemList = scheduleDto.getPrpLscheduleItemList();
		scheduleDto.setPrpLscheduleItemList(scheduleItemList);
		// 要过滤掉不同的scheduleType的内容,只有查勘定损才过滤的。。报案保存的是_,变成""
		for (int i = 0; i < scheduleItemList.size(); i++) {
			PrpLscheduleItem prpLscheduleItemTemp = new PrpLscheduleItem();
			prpLscheduleItemTemp = (PrpLscheduleItem) ((ArrayList<PrpLscheduleItem>) scheduleItemList).get(i);
			if (prpLscheduleItemTemp.getScheduleObjectID().equals("_")) {
				prpLscheduleItemTemp.setScheduleObjectID("");
				prpLscheduleItemTemp.setScheduleObjectName("");

			}
			if (prpLscheduleItemTemp.getScheduleType().equals("sched")) {
				prpLscheduleItemTemp.setSelectSend("0");
				prpLscheduleItemTemp.setSurveyTimes(0);
				// 如果主车是到查勘去了的话，在定损调度里不能选择，除非查勘已经提交了。。
			}
			// 如果没有调度过，默认进去为0,就是没有被选中
			if (prpLscheduleItemTemp.getSurveyTimes() == 0) {
				prpLscheduleItemTemp.setSelectSend("0");
			}
			// 加入集合
			scheduleItemListTemp.add(prpLscheduleItemTemp);
		}

		PrpLscheduleItem prpLscheduleItem = new PrpLscheduleItem();
		prpLscheduleItem.setScheduleItemList(scheduleItemListTemp);
		httpServletRequest.setAttribute("prpLscheduleItem", prpLscheduleItem);

	}

	/**
	 * 获取选择框和列表框中的所有内容
	 * @param httpServletRequest 返回给页面的request
	 * @param prpLscheduleDto 查勘的数据类
	 * @throws Exception
	 */
	private void setSelectionList(HttpServletRequest httpServletRequest, ScheduleDto scheduleDto) throws Exception {
		// 得到车辆种类列表
		Collection<PrpDcode> carKindCodes = this.codeService.getCodeType("CarKind", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("carKindCodes", carKindCodes);
		// 得到车牌底色列表
		Collection<PrpDcode> licenseColorCode = this.codeService.getCodeType("LicenseColor", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("licenseColorCodes", licenseColorCode);
		// 案件狀態
		httpServletRequest.setAttribute("exigenceGreeList", ConstantsCollection.exigenceGreeList);
	}

	// reason:查询调度信息查询分页
	public void getScheuleCheckList(HttpServletRequest httpServletRequest, String conditions, String scheduleType, int pageNo, int recordPerPage) throws Exception {

		Page page = null;

		String condition = httpServletRequest.getParameter("condition");
		if (condition != null && condition.trim().length() > 0) {
			conditions = condition;
		}
		PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();
		PrpLscheduleItem prpLscheduleItem = new PrpLscheduleItem();
		List<PrpLscheduleMainWF> scheduleList = new ArrayList<PrpLscheduleMainWF>();
		List<?> scheduleListTemp = new ArrayList<PrpLscheduleMainWF>();
		List<PrpLscheduleItem> list = new ArrayList<PrpLscheduleItem>();
		if (scheduleType.equals("sched")) {
			page = (Page) this.scheduleService.findForRegistConditions(conditions, pageNo, recordPerPage);
			scheduleListTemp = page.getResult();
			PrpLscheduleMainWF prpLscheduleMainWFTemp = new PrpLscheduleMainWF();
			httpServletRequest.setAttribute("prpLscheduleMainWFDto", prpLscheduleMainWFTemp);
			// 1.转换操作员的姓名,和调度员
			Iterator<?> it = scheduleListTemp.iterator();
			while (it.hasNext()) {
				PrpLscheduleMainWF prpLscheduleMainWF1 = (PrpLscheduleMainWF) it.next();
				prpLscheduleMainWF1.setOperatorName(this.codeService.translateUserCode(prpLscheduleMainWF1.getOperatorCode(), true));
				scheduleList.add(prpLscheduleMainWF1);
			}
			prpLscheduleMainWF.setScheduleList(scheduleList);
			httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWF);
		} else {

			page = (Page) this.scheduleService.findScheduleItemCountByConditon(conditions, pageNo, recordPerPage);
			scheduleListTemp = page.getResult();

			PrpLscheduleMainWF prpLscheduleMainWFTemp = new PrpLscheduleMainWF();

			httpServletRequest.setAttribute("prpLscheduleMainWF", prpLscheduleMainWFTemp);
			// 1.转换操作员的姓名,和调度员
			Iterator<?> it = scheduleListTemp.iterator();
			while (it.hasNext()) {
				PrpLscheduleItem prpLscheduleItem1 = new PrpLscheduleItem();
				prpLscheduleItem1 = (PrpLscheduleItem)it.next();
				prpLscheduleItem1.setOperatorName(this.codeService.translateUserCode(prpLscheduleItem1.getOperatorCode(), true));
				list.add(prpLscheduleItem1);
			}
			prpLscheduleItem.setScheduleItemList(list);
			httpServletRequest.setAttribute("prpLscheduleItem", prpLscheduleItem);
		}

	}

	/**
	 * 获得保单归属机构的相应省份代码,用以 的业务需求。
	 * @param httpServletRequest
	 * @param comCode
	 */
	private void setProvinceCode(HttpServletRequest httpServletRequest, String comCode) {
		/**
		 * 获得保单归属机构的相应省份代码,用以 的业务需求。（要求在调度时只能选择该分公司相关的所有公司进行调度）
		 * ComCode的第2、3两位是相应的省份代码，如"2330000128"中的"33"就是省份代码
		 */
		String provinceCode = comCode;
		httpServletRequest.setAttribute("provinceCode", provinceCode);
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	/**
	 * 调用位置：调度任务处理->调度查询
	 */
	public Page setSchduleToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage, String scheduleType) throws Exception {
		// 放viewhelper
		String registNo = httpServletRequest.getParameter("registNo");
		String startDate = httpServletRequest.getParameter("startDate");
		String endDate = httpServletRequest.getParameter("endDate");
		String checkFlag0 = httpServletRequest.getParameter("checkFlag0");
		String checkFlag4 = httpServletRequest.getParameter("checkFlag4");
		String operatorCode = httpServletRequest.getParameter("handlerCode");
		String scheduleObjectID = httpServletRequest.getParameter("scheduleObjectID");
		String registNoSign = httpServletRequest.getParameter("registNoSign");
		String InsuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String InsuredName = StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET);
		String prpLscheduleItemLicenseNo = StringConvert.getParam(httpServletRequest, "prpLscheduleItemLicenseNo", ConstantCodes.YUI_CHARSET);
		String prpLscheduleItemLicenseNoSign = httpServletRequest.getParameter("prpLscheduleItemLicenseNoSign");
		String conditions = " 1=1 ";
		conditions = conditions + StringConvert.convertString("registNo", registNo, registNoSign);
		conditions = conditions + StringConvert.convertString("operatorCode", operatorCode, "=");
		conditions = conditions + StringConvert.convertString("scheduleObjectID", scheduleObjectID, "=");
		String strTemp1 = "";
		String strTemp = "";
		String tableName = "";
		if (scheduleType.equals("schel")) {// 只有分案类型为定损时，才考虑车牌号的查询条件。
			conditions = conditions + StringConvert.convertString("licenseNo", prpLscheduleItemLicenseNo, prpLscheduleItemLicenseNoSign);
		}
		if (checkFlag0 != null || checkFlag4 != null) {

			if (checkFlag0 != null) {
				strTemp = strTemp + "'0',";
				strTemp1 = strTemp1 + "'0',";
			}
			if (checkFlag4 != null) {
				strTemp = strTemp + "'4',";
				strTemp1 = strTemp1 + "'1',";
			}
			// 去掉最後的一个","
			strTemp = strTemp.substring(0, strTemp.length() - 1);
			strTemp1 = strTemp1.substring(0, strTemp1.length() - 1);
			if (scheduleType.equals("schel")) {
				conditions = conditions + " AND (surveyTimes in(" + strTemp1 + "))";
			} else {
				conditions = conditions + " AND (checkFlag in(" + strTemp + "))";
			}
		}
		if (startDate != null && startDate.trim().length() > 0) {
			conditions = conditions + StringConvert.convertDate("inputdate", startDate, ">=");
		}
		if (endDate != null && endDate.trim().length() > 0) {
			conditions = conditions + StringConvert.convertDate("inputdate", endDate, "<=");
		}
		if (scheduleType.equals("schel")) {
			tableName = "prplscheduleitem";
		} else {
			tableName = "prplschedulemainwf";
		}
		conditions += " AND exists(select 0 from prplregist a where DealerCode is null and a.registNo=" + tableName + ".registno" + StringConvert.convertString("InsuredName", InsuredName, InsuredNameSign) + ")";
		//conditions = conditions + StringConvert.convertString("InsuredName", InsuredName, InsuredNameSign);
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, tableName, "", "ClaimComCode");
		conditions = conditions + " order by inputdate desc,registNo";
		// 从翻页取数据
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if ("true".equals(searchFlag)) {
		} else {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		Page page = this.scheduleService.findByQueryConditions(conditions, pageNo, recordPerPage, scheduleType);
		// 如果是定损查询，查询的是PrpLscheduleItem表，把查询出来的表内容重新装在Prplschedulemainwf对象中，放到前台页面中展示
		if (scheduleType.equals("schel") && page.getResult().size() > 0) {
			List<?> tempList = page.getResult();
			String operatorName = null;
			PrpLscheduleMainWF temp = null;
			List<PrpLscheduleMainWF> resultList = new ArrayList<PrpLscheduleMainWF>();
			Iterator<?> it = tempList.iterator();
			while(it.hasNext()){
				PrpLscheduleItem prpLscheduleItem = (PrpLscheduleItem)it.next();
				PrpLscheduleMainWF prpLscheduleMainWF = new PrpLscheduleMainWF();
				prpLscheduleMainWF.getId().setRegistNo(prpLscheduleItem.getId().getRegistNo());
				prpLscheduleMainWF.getId().setScheduleID(1);//调度ID默认写为1
				if (prpLscheduleItem.getSurveyTimes() != null) {
					if (prpLscheduleItem.getSurveyTimes() == 1) {
						prpLscheduleMainWF.setCheckFlag("4");
					} else {
						prpLscheduleMainWF.setCheckFlag(prpLscheduleItem.getSurveyTimes().toString());
					}
				} else {
					prpLscheduleMainWF.setCheckFlag("0");
				}
				prpLscheduleMainWF.setCheckInfo(prpLscheduleItem.getResultInfo());
				prpLscheduleMainWF.setOperatorCode(prpLscheduleItem.getOperatorCode());
				operatorName = codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, prpLscheduleItem.getOperatorCode(), ConstantCodes.Language.CHINESE);
				prpLscheduleMainWF.setOperatorName(operatorName);
				prpLscheduleMainWF.setNextHandlerName(prpLscheduleItem.getNextHandlerName());
				prpLscheduleMainWF.setNextHandlerCode(prpLscheduleItem.getNextHandlerCode());
				prpLscheduleMainWF.setScheduleType(prpLscheduleItem.getScheduleType());
				prpLscheduleMainWF.setInputDate(prpLscheduleItem.getInputDate());
				temp = prpLscheduleMainWFService.findPrpLscheduleMainWF(prpLscheduleMainWF.getId());
				prpLscheduleMainWF.setRiskCode(temp.getRiskCode());
				resultList.add(prpLscheduleMainWF);
			}
			page = new Page((pageNo - 1) * recordPerPage, page.getTotalCount(), recordPerPage, resultList);
		}
		return page;
	}

	/*
	 * 调用位置:调度任务->查勘处理情况查询 ，查询条件拼接
	 */

	public Page getNextTaskList(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) {
		String registNo = httpServletRequest.getParameter("registNo");
		String beforeHandlerCode = httpServletRequest.getParameter("handlerCode");
		String handlerCode = httpServletRequest.getParameter("NhandlerCode");
		String checkFlag0 = httpServletRequest.getParameter("checkFlag0");
		String checkFlag2 = httpServletRequest.getParameter("checkFlag2");
		String checkFlag4 = httpServletRequest.getParameter("checkFlag4");
		String startDate = httpServletRequest.getParameter("startDate");
		String endDate = httpServletRequest.getParameter("endDate");
		String licenseNo = httpServletRequest.getParameter("licenseNo");
		String editType = httpServletRequest.getParameter("editType");

		String conditions = "";

		if (registNo != null && registNo.trim().length() > 0) {

			conditions = "1=1";
			conditions = conditions + StringConvert.convertString("keyin", registNo, httpServletRequest.getParameter("RegistNoSign")) + "AND ";

		}
		if (beforeHandlerCode != null && beforeHandlerCode.trim().length() > 0) {
			conditions = conditions + " (beforeHandlerCode=" + beforeHandlerCode + ") AND ";
		}
		if (handlerCode != null && handlerCode.trim().length() > 0) {
			conditions = conditions + " (handlerCode=" + handlerCode + ") AND ";
		}
		if (startDate != null && startDate.trim().length() > 0) {
			conditions = conditions + " (flowIntime>='" + startDate + "') AND ";
		}
		if (endDate != null && endDate.trim().length() > 0) {
			conditions = conditions + " (flowIntime<='" + new DateTime(endDate, DateTime.YEAR_TO_DAY).addDay(1).toString() + "') AND ";
		}
		String strTemp = "";
		if (checkFlag0 != null || checkFlag2 != null || checkFlag4 != null) {
			if (checkFlag0 != null)
				strTemp = strTemp + "'0',";
			if (checkFlag2 != null)
				strTemp = strTemp + "'2',";
			if (checkFlag4 != null)
				strTemp = strTemp + "'4',";

			strTemp = strTemp.substring(0, strTemp.length() - 1);
			conditions = conditions + " (nodeStatus in(" + strTemp + ")) AND ";
		}
		if (licenseNo != null && licenseNo.trim().length() > 0) {
			conditions = conditions + " (lossItemCode like '%" + licenseNo + "%') AND ";
		}

		@SuppressWarnings("unused")
		String nodeType = "";
		if (editType.equals("QUERYCHECK")) {
			nodeType = "check";
			conditions = conditions + " nodeType='check'";
		} else {
			conditions = conditions + " nodeType='certa'";
			nodeType = "certa";
		}

		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		try {
			conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		conditions = conditions + " order by flowintime";

		// 从翻页取数据
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if ("true".equals(searchFlag)) {

		} else {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		Page page = this.scheduleService.findByQueryConditions(conditions, pageNo, recordPerPage);
		return page;
	}

	/*
	 * 调度改派查询条件拼接
	 */
	public Page getNextBackTaskList(HttpServletRequest httpServletRequest, int pageNo, int pageSize) {
		String nodeType = httpServletRequest.getParameter("nodeType");
		String registNo = httpServletRequest.getParameter("registNo");
		String handlerCode = httpServletRequest.getParameter("handlerCode");// 调度员
		String NhandlerCode = httpServletRequest.getParameter("NhandlerCode");
		String licenseNo = httpServletRequest.getParameter("licenseNo");
		String startDate = httpServletRequest.getParameter("startDate");
		String endDate = httpServletRequest.getParameter("endDate");
		String conditions = "";
		conditions = " (nodeType='" + nodeType + "' and nodestatus<4) and (riskcode in (select outercode from uticodetransfer where risktype='D')) ";

		conditions = conditions + StringConvert.convertString("registNo", registNo, httpServletRequest.getParameter("registNoSign"));
		conditions = conditions + StringConvert.convertString("BeforeHandlerCode", handlerCode, "=");
		conditions = conditions + StringConvert.convertString("handlerCode", NhandlerCode, "=");
		conditions = conditions + StringConvert.convertString("lossItemName", licenseNo, httpServletRequest.getParameter("licenseNoSign"));

		if (startDate != null && startDate.trim().length() > 0) {
			conditions = conditions + " and (flowintime>='" + startDate + "') ";
		}
		if (endDate != null && endDate.trim().length() > 0) {
			conditions = conditions + "  and (flowintime<='" + new DateTime(endDate, DateTime.YEAR_TO_DAY).addDay(1).toString() + "') ";
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		try {
			conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		conditions = conditions + " order by flowintime desc";
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if (condition != null && condition.trim().length() > 0 && searchFlag.equals("")) {
			conditions = condition;
		}
		Page page = this.scheduleService.findByQueryConditions(conditions, pageNo, pageSize);
		return page;
	}

	/**
	 * 调用位置:调度任务处理->定损任务注销。
	 */
	public Page getCancelBeforeList(HttpServletRequest httpServletRequest, int pageNo, int pageSize) {
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 查询新调度提示表信息,整理输入，用於初始界面显示
		String registNo = httpServletRequest.getParameter("prpLcertainLossRegistNo"); // 报案号
		String nodeType = httpServletRequest.getParameter("nodeType");// 调度类型
		String conditions = "";
		if ("all".equals(nodeType)) {
			conditions = "nodestatus=0 and nodeType in ('certa','wound','propc')";
		} else {
			conditions = " (nodeType='" + nodeType + "' and nodestatus=0)";
		}
		conditions = conditions + StringConvert.convertString("registNo", registNo, "=");

		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		try {
			conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		conditions = conditions + " order by nodeType, flowintime desc";
		// 从翻页取数据
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if (!"true".equals(searchFlag) && condition != null && condition.trim().length() > 0) {
			conditions = condition;
		}
		Page page = this.scheduleService.findByQueryConditions(conditions, pageNo, pageSize);
		return page;
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

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

}
