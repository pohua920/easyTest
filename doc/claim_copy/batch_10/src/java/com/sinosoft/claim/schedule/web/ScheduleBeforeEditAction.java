package com.sinosoft.claim.schedule.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.UIPowerInterface;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.util.DAAScheduleViewHelper;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 分发HTTP GET 理赔调度节点的新的需要调度的任务
 * <p>
 * Title: 理赔调度节点的新的需要调度的任务
 * </p>
 * <p>
 * Description: 理赔调度节点的新的需要调度的任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0u
 */

@SuppressWarnings("serial")
public class ScheduleBeforeEditAction extends Struts2Action {

	
	/**
	 * 编辑类型的定义
	 */
	public static String SCHEDULEADD = "ADD";
	public static String SCHEDULEEDIT = "EDIT";
	public static String SCHEDULEQUERYCHECK = "QUERYCHECK";

	/**
	 * 跳转类型的定义
	 */
	public static String FORWARDSUCCESS = "success"; // 跳转条件成功
	public static String FORWARDERROR = "error"; // 跳转条件失败
	public static String FORWARDLISTDAA = "LISTDAA"; // 跳转条件成功到LISTDAA
	public static String FORWARDEDITDAA = "EDITDAA"; // 跳转条件成功到EDITDAA

	/**分案服务*/
	private ScheduleService scheduleService;
	/**报案号码*/
	private String registNo;
	/**车牌号码*/
	private String prpLscheduleItemLicenseNo;
	/**被保险人*/
	private String InsuredName;
	private String registNoSign;
	private String prpLscheduleItemLicenseNoSign;
	private String InsuredNameSign;
	/**分案处理单位*/
	private String scheduleObjectID;
	/**处理人*/
	private String handlerCode;
	/**调度类型*/
	private String scheduleType;
	/**流入开始时间*/
	private String startDate;
	/**流入结束时间*/
	private String endDate;
	private String checkFlag0;
	private String checkFlag4;
	/**编辑类型*/
	private String editType;
	/**分案数据收集*/
	private DAAScheduleViewHelper daaScheduleViewHelper;
	/**工作流数据收集*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流处理服务*/
	private WorkFlowService workFlowService;
	
	private CommonService commonService;
	private CodeService codeService;
	

	/**
	 * 处理调度信息
	 * @return
	 * @throws Exception
	 */
	public String scheduleBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		HttpServletResponse httpServletResponse = getResponse();
		HttpSession session = httpServletRequest.getSession();
		/**
		 * 包含如下功能 1。调度录入的界面展现 2。已提交调度任务列表中的调度信息详细查看 3。已查勘情况查询 4。查询调度取回任务
		 * 5。查询调度撤消任务 6。查询调度的所有信息
		 **/

		String forward = "";

		// 向前
		// 去掉try，struts2会捕获userException异常，进行处理
		UserDto user = (UserDto) session.getAttribute("user");

		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 查询新调度提示表信息,整理输入，用於初始界面显示
		String scheduleID = "";
//		String surveyNo = "";
		String checkFlag0 = "";
		String checkFlag2 = "";
		String checkFlag4 = "";
		String handlerCode = "";
		String operatorCode = "";
		String scheduleObjectID = "";
		String startDate = "";
		String endDate = "";
		String conditions = "";
		String strTemp = "";
		String licenseNo = "";
		String scheduleType = "schedule"; // 调度传入参数
		String beforeHandlerCode = "";
		String flowID = httpServletRequest.getParameter("swfLogFlowID");
		String logNo = httpServletRequest.getParameter("swfLogLogNo");
		if ("ajaxQuery".equals(editType)) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			String scheduleComcode = httpServletRequest.getParameter("ScheduleObjectID");
			String scheduleUserCode = httpServletRequest.getParameter("nextHandlerCode");
			String nextNodeNo = httpServletRequest.getParameter("nextNodeNo");
			String taskCode = "";
			if("check".equals(nextNodeNo)){
				taskCode = "claim.check.insert";
			} else if("certa".equals(nextNodeNo)){
				taskCode = "claim.certaincarloss.insert";
			} else if("wound".equals(nextNodeNo)){
				taskCode = "claim.certainpersonloss.insert";
			} else if("propc".equals(nextNodeNo)){
				taskCode = "claim.certainloss.insert";
			}
			StringBuffer statements = new StringBuffer("");
			statements.append(" SELECT DISTINCT comcode FROM utiusergrade g, utigradetask t ");
			statements.append(" WHERE g.gradecode = t.gradecode AND t.taskcode = '"+taskCode+"' ");
			statements.append(" AND g.userCode = '").append(scheduleUserCode).append("' ");
			if(!CommonUtils.isEmpty(scheduleComcode)){
				statements.append(" AND g.comcode = '").append(scheduleComcode).append("' ");
				jsonMap.put("scheduleComcode", scheduleComcode);
				jsonMap.put("scheduleComCName", this.codeService.translateComCode(scheduleComcode, true));
			} else {
				jsonMap.put("scheduleComcode", "");
				jsonMap.put("scheduleComCName", "");
			}
			List<?> list = this.commonService.findByStatements(statements.toString());
			if(!CommonUtils.isEmpty(list)){
				if(CommonUtils.isEmpty(scheduleComcode)){
					scheduleComcode = list.get(0).toString();
					jsonMap.put("scheduleComcode", scheduleComcode);
					jsonMap.put("scheduleComCName", this.codeService.translateComCode(scheduleComcode, true));
				}
				jsonMap.put("scheduleUserCode", scheduleUserCode);
				jsonMap.put("scheduleUserName", this.codeService.translateUserCode(scheduleUserCode, true));
			} else {
				jsonMap.put("scheduleUserCode", "");
				jsonMap.put("scheduleUserName", "");
			}
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
			return NONE;
		}
		httpServletRequest.setAttribute("editType", editType);
		// 1。调度录入的界面展现
		// 调用位置:调度任务登记 ->新案件提示任务列表中选中一个报案->显示调度任务信息
		if (editType.equals("ADD")) {
			registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
//			surveyNo = httpServletRequest.getParameter("prpLscheduleMainWFSurveyNo"); // 次数号码
			// 占号怎么处理？？
			// 考虑先占号，大家都能看得到。。。。。找不到人就可以随便问了。。。
			// 可以做立案了
			// ==================================================
			// 如果没有flowID和logno则不进行判断。
			String msg = "";
			if (flowID != null && logNo != null) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					msg = "案件'" + registNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所佔用,請選擇其它案件進行處理!";
					throw new UserException(1, 3, "工作流", msg);
				}
			}
			this.daaScheduleViewHelper.registDtoToView(httpServletRequest, registNo, editType);
			forward = "success";
		}
		// 2。已提交调度任务列表中的调度信息详细查看
		// 调用位置:已提交调度任务 ->任务列表中选中一个报案->显示保存过的调度任务信息
		if (editType.equals("EDIT")) {
			registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
			scheduleID = httpServletRequest.getParameter("prpLscheduleMainWFScheduleID"); // 调度号
			daaScheduleViewHelper.scheduleDtoToView(httpServletRequest, registNo, editType, scheduleID);
			forward = "EDITDAA";
		}
		// 3。已查勘情况查询
		// 调用位置:调度任务->查勘处理情况查询 ->输入查询条件後按下按钮->显示查询结果
		if (editType.equals("QUERYCHECK") || editType.equals("QUERYCERTAINLOSS")) {
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			registNo = httpServletRequest.getParameter("registNo");
			beforeHandlerCode = httpServletRequest.getParameter("handlerCode");
			handlerCode = httpServletRequest.getParameter("NhandlerCode");
			checkFlag0 = httpServletRequest.getParameter("checkFlag0");
			checkFlag2 = httpServletRequest.getParameter("checkFlag2");
			checkFlag4 = httpServletRequest.getParameter("checkFlag4");
			startDate = httpServletRequest.getParameter("startDate");
			endDate = httpServletRequest.getParameter("endDate");
			licenseNo = httpServletRequest.getParameter("licenseNo");

			conditions = "";

			if (registNo != null && registNo.trim().length() > 0) {
				conditions = "1=1";
				conditions = conditions + StringConvert.convertString("keyin", registNo, httpServletRequest.getParameter("RegistNoSign")) + "AND ";

			}
			if (beforeHandlerCode != null && beforeHandlerCode.trim().length() > 0) {
				conditions = conditions + " (beforeHandlerCode='" + beforeHandlerCode + "') AND ";
			}
			if (handlerCode != null && handlerCode.trim().length() > 0) {
				conditions = conditions + " (handlerCode='" + handlerCode + "') AND ";
			}
			if (startDate != null && startDate.trim().length() > 0) {
				conditions = conditions + " (flowIntime>='" + startDate + "') AND ";
			}
			if (endDate != null && endDate.trim().length() > 0) {
				conditions = conditions + " (flowIntime<='" + new DateTime(endDate, DateTime.YEAR_TO_DAY).addDay(1).toString() + "') AND ";
			}
			if (checkFlag0 != null || checkFlag2 != null || checkFlag4 != null) {
				if (checkFlag0 != null)
					strTemp = strTemp + "'0',";
				if (checkFlag2 != null)
					strTemp = strTemp + "'2',";
				if (checkFlag4 != null)
					strTemp = strTemp + "'4',";
				// 去掉最後的一个","
				strTemp = strTemp.substring(0, strTemp.length() - 1);
				conditions = conditions + " (nodeStatus in(" + strTemp + ")) AND ";
			}
			if (licenseNo != null && licenseNo.trim().length() > 0) {
				conditions = conditions + " (lossItemCode like '%" + licenseNo + "%') AND ";
			}

//			String nodeType = "";
			if (editType.equals("QUERYCHECK")) {
//				nodeType = "check";
				conditions = conditions + " nodeType='check'";
			} else {
				conditions = conditions + " nodeType='certa'";
//				nodeType = "certa";
			}

			UIPowerInterface uiPowerInterface = new UIPowerInterface();
			UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
			conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
			conditions = conditions + " order by flowintime";

			// 从翻页取数据
			String condition = httpServletRequest.getParameter("condition");
			// reason 查询标志
			String searchFlag = httpServletRequest.getParameter("searchFlag");
			if ("true".equals(searchFlag)) {

			} else {
				if (condition != null && condition.trim().length() > 0) {
					conditions = condition;
				}
			}

			try {
				page = this.daaScheduleViewHelper.getNextTaskList(conditions, pageNo, pageSize);
				//System.err.println(JSONArray.fromObject(page.getResult()));
				this.writeJSONData(page, "id", "nodeStatus", "registNo", "flowInTime", "handlerName", "beforeHandlerName", "lossItemName", "riskCode", "nodeType", "businessNo", "policyNo", "modelNo", "nodeNo", "keyIn", "lossItemCode",
						"insureCarFlag", "typeFlag", "nodeName");
				return NONE;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 5。查询调度撤消任务
		// 调用位置:调度任务->已查勘情况查询 ->输入查询条件後按下按钮->显示查询结果
		if (editType.equals("CANCELQUERY")) {
			conditions = "1=1";
			daaScheduleViewHelper.getScheuleCheckList(httpServletRequest, conditions, scheduleType);
			forward = "CANCELLISTDAA";

		}
		if (editType.equals("QUERY")) {
			registNo = httpServletRequest.getParameter("registNo");
			startDate = httpServletRequest.getParameter("startDate");
			endDate = httpServletRequest.getParameter("endDate");
			checkFlag0 = httpServletRequest.getParameter("checkFlag0");
			checkFlag4 = httpServletRequest.getParameter("checkFlag4");
			checkFlag2 = httpServletRequest.getParameter("checkFlag2");
			operatorCode = httpServletRequest.getParameter("handlerCode");
			licenseNo = httpServletRequest.getParameter("licenseNo");
			scheduleObjectID = httpServletRequest.getParameter("scheduleObjectID");
			scheduleType = httpServletRequest.getParameter("scheduleType");
			conditions = " 1=1 ";
			conditions = conditions + StringConvert.convertString("registNo", registNo, httpServletRequest.getParameter("registNoSign"));
			conditions = conditions + StringConvert.convertString("operatorCode", operatorCode, "=");
			conditions = conditions + StringConvert.convertString("scheduleObjectID", scheduleObjectID, "=");
			String strTemp1 = "";
			String tableName = "";
			if (scheduleType.equals("schel")) {
				conditions = conditions + StringConvert.convertString("licenseNo", httpServletRequest.getParameter("prpLscheduleItemLicenseNo"), httpServletRequest.getParameter("prpLscheduleItemLicenseNoSign"));
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
				conditions = conditions + " AND (inputdate>='" + startDate + "') ";
			}
			if (endDate != null && endDate.trim().length() > 0) {
				conditions = conditions + " AND (inputdate<='" + new DateTime(endDate, DateTime.YEAR_TO_DAY).addDay(1).toString() + "') ";
			}
			if (scheduleType.equals("schel")) {
				tableName = "prplscheduleitem";
			} else {
				tableName = "prplschedulemainwf";
			}
			conditions += " AND exists(select 0 from prplregist a where DealerCode is null and a.registNo=" + tableName + ".registno)";
			conditions = conditions + StringConvert.convertString("InsuredName", httpServletRequest.getParameter("InsuredName"), httpServletRequest.getParameter("InsuredNameSign"));
			/***业务表查询不再限制机构  delete by chenjie 20130614 start*/
//			UIPowerInterface uiPowerInterface = new UIPowerInterface();
//			UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
//			conditions = conditions + uiPowerInterface.addPower(userDto, tableName, "", "ClaimComCode");
			/***业务表查询不再限制机构  delete by chenjie 20130614 end*/
			conditions = conditions + " order by inputdate desc";
			// 从翻页取数据
			String condition = httpServletRequest.getParameter("condition");
			// reason 查询标志
			String searchFlag = httpServletRequest.getParameter("searchFlag");
			if ("true".equals(searchFlag)) {
			} else {
				if (condition != null && condition.trim().length() > 0) {
					conditions = condition;
				}
			}
			this.daaScheduleViewHelper.getScheuleCheckList(httpServletRequest, conditions, scheduleType);
			forward = "COMMONLISTDAA" + scheduleType;
		}
		// 7。显示调度信息详细查看 只读的
		if (editType.equals("SHOW")) {
			registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
			scheduleID = httpServletRequest.getParameter("prpLscheduleMainWFScheduleID"); // 调度号
			this.daaScheduleViewHelper.scheduleDtoToView(httpServletRequest, registNo, editType, scheduleID);
			forward = "SHOWDAA";
		}
		// 8。调度信息取回详细显示
		// 调用位置:调度取回任务 ->任务列表中选中一个报案->显示保存过的调度任务信息
		if (editType.equals("GETBACKEDIT")) {
			scheduleType = httpServletRequest.getParameter("scheduleType");
			registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
			scheduleID = "1"; // 调度号
			this.daaScheduleViewHelper.scheduleDtoToView(httpServletRequest, registNo, editType, scheduleID);
			forward = "EDITDAA" + scheduleType;
		}
		if (editType.equals("ADDQUERY")) {
			// 说明:能够取回的任务必须是还没有被查勘处理过的调度任务
			logger.debug("查询满足条件的 公告信息");
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			page = this.workFlowViewHelper.getScheduleAddCertainLossSwfLogList(getRequest(), pageNo, pageSize);
			this.writeJSONData(page, "nodeStatus", "businessNo", "lossItemName", "handlerName", "flowInTime", "id", "policyNo", "nodeStatus", "riskCode", "keyIn");
			return NONE;

		}
		return forward;
	}

	/**
	 * 调用位置：调度任务处理->调度查询
	 */
	public String query() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		logger.debug("查询满足条件的 公告信息");
		Page page = null;
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		try {
			if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("QUERY")) {
				page = this.daaScheduleViewHelper.setSchduleToView(getRequest(), pageNo, pageSize, scheduleType);
				if (editType.equals("SHOW")) {
					httpServletRequest.setAttribute("type", "SHOW");
				}
			}
			this.writeJSONData(page, "checkFlag", "id", "checkInfo", "inputDate", "operatorName", "checkOperatorName", "nextHandlerName", "scheduleType", "riskCode");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeJSONMsg(ex.getMessage());
		}
		return NONE;
	}

	public String getCheckFlag0() {
		return checkFlag0;
	}

	public void setCheckFlag0(String checkFlag0) {
		this.checkFlag0 = checkFlag0;
	}

	public String getCheckFlag4() {
		return checkFlag4;
	}

	public void setCheckFlag4(String checkFlag4) {
		this.checkFlag4 = checkFlag4;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getScheduleObjectID() {
		return scheduleObjectID;
	}

	public void setScheduleObjectID(String scheduleObjectID) {
		this.scheduleObjectID = scheduleObjectID;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getRegistNoSign() {
		return registNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		this.registNoSign = registNoSign;
	}

	public String getPrpLscheduleItemLicenseNoSign() {
		return prpLscheduleItemLicenseNoSign;
	}

	public void setPrpLscheduleItemLicenseNoSign(String prpLscheduleItemLicenseNoSign) {
		this.prpLscheduleItemLicenseNoSign = prpLscheduleItemLicenseNoSign;
	}

	public String getInsuredNameSign() {
		return InsuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		InsuredNameSign = insuredNameSign;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getPrpLscheduleItemLicenseNo() {
		return prpLscheduleItemLicenseNo;
	}

	public void setPrpLscheduleItemLicenseNo(String prpLscheduleItemLicenseNo) {
		this.prpLscheduleItemLicenseNo = prpLscheduleItemLicenseNo;
	}

	public String getInsuredName() {
		return InsuredName;
	}

	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public DAAScheduleViewHelper getDaaScheduleViewHelper() {
		return daaScheduleViewHelper;
	}

	public void setDaaScheduleViewHelper(DAAScheduleViewHelper daaScheduleViewHelper) {
		this.daaScheduleViewHelper = daaScheduleViewHelper;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
