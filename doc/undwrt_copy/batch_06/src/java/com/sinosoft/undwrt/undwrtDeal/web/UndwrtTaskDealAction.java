package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.common.schema.model.PrpDprint;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.web.view.AbstractForm;
import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.common.util.CommonSession;
import com.sinosoft.undwrt.common.util.TaskDealViewHelper;
import com.sinosoft.undwrt.common.vo.NodeListVo;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService;
import com.sinosoft.sysframework.reference.DBManager;
/**
 * 核保任務處理控制.
 * 
 */
public class UndwrtTaskDealAction extends Struts2Action {

	/** 屬性動作類型. */
	private String actionType;

	/** 屬性處理類型. */
	private String handType;

	/** 屬性編輯類型. */
	private String editType;

	/** 屬性工作流日誌. */
	private WfLog wfLog;

	/** 屬性起始日期. */
	private String startDate;

	/** 屬性當前日期. */
	private String todayDate;

	/** 屬性終止日期. */
	private String endDate;

	/** 民國年起始日期. */
	private Date startDateRc;

	/** 民國年當前日期. */
	private Date todayDateRc;

	/** 屬性核保處理任務服務接口. */
	private TaskDealService taskDealService;

	/** 屬性險種大類列表. */
	List riskCategoryList;
	
	/** 屬性險種大類分類列表. */
	List<PrpDcode> prpDcode_riskCategoryList;

	/** 屬性證件類型. */
	List identifyTypeList;

	/** 屬性節點列表. */
	List<NodeListVo> nodeList;

	/** 屬性險種大類. */
	private String riskCategory;

	/** 屬性列名. */
	private String showColumnName;// 预约协议号/车牌号/证件号

	/** 屬性核保任務列表. */
	List displayUndwrtTaskList;

	/** 屬性核保任務列表. */
	List undwrtTaskList;

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/** 屬性審核意見. */
	List notionList;

	/** 屬性提交任務列表. */
	List yesToSubmitList;

	/** 屬性提交任務列表. */
	List noToSubmitList;

	/** 屬性日期. */
	private Date[] date;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 任務處理幫助類 */
	private TaskDealViewHelper taskDealViewHelper;
	
	private String operateType;

	/** 要保書查詢接口 */
	private PolicyService policyService;
	
	/** 批單查詢接口*/
	private EndorseService endorseService;
    //調用承保的AML系統后 add by xuhuiling 20160827
	private PrpFeedBackService prpFeedBackService;
	
	/** mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整*/
	private CommonService commonService;
	
	public PrpFeedBackService getPrpFeedBackService() {
		return prpFeedBackService;
	}

	public void setPrpFeedBackService(PrpFeedBackService prpFeedBackService) {
		this.prpFeedBackService = prpFeedBackService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}
	
	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	//add by xuhuiling end
	/**
	 * 處理核保任務.
	 * 
	 * @return 頁面跳轉結果
	 * @throws Exception
	 *             異常
	 */
	public String hebaoTaskDeal() throws UserException, Exception {
		HttpServletRequest request = this.getRequest();
		String forward = "";
		if (actionType.equals("prepareQueryAuthorizeControl"))// 授权查询任务
		{
			this.prepareQueryAuthorizeControl(request);
			forward = actionType;
		} else if (actionType.equals("queryAuthorizeControl")) {
			this.queryAuthorizeControl(request);
			forward = actionType;
		} else if (actionType.equals("prepareQuery")) {
			this.prepareQuery(request);
			forward = actionType;
		} else if (actionType.equals("prepareQueryQta") || actionType.equals("prepareQueryQtaStats")) {
			this.prepareQueryQta(request);
			forward = actionType;
		//mantis： CAR0123，處理人員： David ，需求單編號： CAR0123 關聯強制險查詢
		} else if (actionType.equals("relevanceQueryQta")) {
			this.relevanceQueryQta(request);
			forward = "prepareQueryQta";
		} else if (actionType.equals("prepareQueryShow")) {
			this.prepareQuery(request);
			forward = actionType;
		} else if (actionType.equals("query") || actionType.equals("queryQta") || actionType.equals("queryQtaStats")) {
			this.query(request);
			forward = actionType;
		}
		// 对已处理核保任务的查询（WflogStore表）
		else if (actionType.equals("prepareQueryShowWflogStore")) {
			// uiAction.prepareQueryWflogStore();
			forward = actionType;
		} else if (actionType.equals("queryContinue")) {
			this.queryContinue(request);
			if (handType.equals("11"))// 核保
			{
				forward = "hebaoQueryContinue";
			} else if (handType.equals("22"))// 核赔
			{
				forward = "hepeiQueryContinue";
			} else if (handType.equals("12"))// 报价
			{
				forward = "qtaQueryContinue";
			}
		} else if (actionType.equals("prepareBatchSubmitSuperior")) {
			this.prepareBatchSubmitSuperior(request);
			forward = actionType;
		} else if (actionType.equals("batchSubmitSuperior")) {
			// uiAction.batchSubmitSuperior();
			forward = actionType;
		} else if (actionType.equals("prepareBatchSubmitJunior")) {
			// uiAction.prepareBatchSubmitJunior();
			forward = actionType;
		} else if (actionType.equals("batchSubmitJunior")) {
			// uiAction.batchSubmitJunior();
			forward = actionType;
		} else if (actionType.equals("prepareBatchUndo")) {
			// uiAction.prepareBatchUndo();
			forward = actionType;
		} else if (actionType.equals("batchUndo")) {
			// uiAction.batchUndo();
			forward = actionType;
		} else if (actionType.equals("retract")) {
			// uiAction.retract();
			forward = actionType;
		} else {
			forward = "failure";
			content = getText("undwrt.action.undwrtTaskDeal.undefinedTaskType");
		}
		return forward;
	}

	/**
	 * 授權任務查詢準備.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void prepareQueryAuthorizeControl(HttpServletRequest request) throws Exception {
		identifyTypeList = taskDealService.findIdentifyTypeList();
		riskCategoryList = taskDealService.findRiskCodeByRiskCategory();

		HttpSession session = request.getSession();
		String strUserCode = (String) session.getAttribute("myUserCode");
		String strComCode = (String) session.getAttribute("myComCode");
		nodeList = taskDealService.findNodeList(strUserCode, strComCode);

		wfLog = new WfLog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 查询起始日期处理，暂时处理为上月一号
		Calendar cal = Calendar.getInstance();
		int month = 12;
		if (Calendar.MONTH != 1) {
			month = cal.get(Calendar.MONTH) - 1;
		} else {
			cal.set(Calendar.YEAR, Calendar.YEAR - 1);
		}
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);

		if (handType.equals("11")) // 核保
		{
			startDate = sdf.format(cal.getTime());
			todayDate = sdf.format(new Date());

			session.setAttribute("handTitle", getText("undwrt.action.commonCheckTask.underWrite"));
		} else if (handType.equals("22")) // 核赔
		{
			startDate = sdf.format(cal.getTime());
			endDate = sdf.format(new Date());

			session.setAttribute("handTitle", getText("undwrt.action.commonCheckTask.checkCompensate"));
		}
		session.setAttribute("handType", handType);
		session.setAttribute("editType", editType);
	}

	/**
	 * 授權任務查詢.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void queryAuthorizeControl(HttpServletRequest request) throws Exception {

		HttpSession session = this.getSession();
		ParamUtils paramUtils = new ParamUtils(request);
		CommonSession.setHandleSession(request);
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 15);

		boolean nodeStatusView = false; // 是否需要从视图中查数据
		String[] nodeStatusVal = { "0" };
		for (int a = 0; a < nodeStatusVal.length; a++) {
			if (nodeStatusVal[a].equals("0")) {
				nodeStatusView = true;
			}
		}
		session.setAttribute("nodeStatusView", nodeStatusView);
		String statement = null;
		statement = taskDealViewHelper.getHebaoAuthorizeTaskQueryStatement(request, false);
		session.setAttribute("UndwrtQueryStatement", statement);
		PageRecord pageRecord = null;
		PageRecord allPageRecord = null;
		pageRecord = taskDealService.findByStatementPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
		String riskCategory = StringUtils.trimToEmpty(paramUtils.getParameter("riskCategory"));
		showColumnName = taskDealViewHelper.getShowColumnNameByRiskCategory(riskCategory);
		String[] nodeStatus = { "0" };
		if (nodeStatus == null) {
			nodeStatus = new String[] { "" };
		}
		String[] enabled = taskDealViewHelper.getBatchButtonEnabledByNodeStatus(StringUtils.trimToEmpty(nodeStatus[0]));
		request.setAttribute("riskCategory", riskCategory);
		request.setAttribute("showColumnName", showColumnName);
		session.setAttribute("nodeStatusList", nodeStatusVal);
		session.setAttribute("nodeStatus", nodeStatus[0]);
		request.setAttribute("batchSuperiorButton", enabled[0]);
		request.setAttribute("batchJuniorButton", enabled[1]);
		request.setAttribute("batchUndoButton", enabled[2]);
		request.setAttribute("fm", new AbstractForm(pageRecord));
		request.setAttribute("UndwrtTaskList", pageRecord.getResult());
	}

	/**
	 * 準備查詢核保處理任務.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void prepareQuery(HttpServletRequest request) throws Exception {
		identifyTypeList = taskDealService.findIdentifyTypeList();
		riskCategoryList = taskDealService.findRiskCodeByRiskCategory();
		
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.codeType", "RiskCategory");
		queryRule.addIn("id.validStatus", "1","2");//1有效 2顯示
		queryRule.addDescOrder("flag");
		prpDcode_riskCategoryList = prpDcodeService.findPrpDcodeList(queryRule);
		
		wfLog = new WfLog();

		HttpSession session = this.getSession();
		String strUserCode = (String) session.getAttribute("myUserCode");
		String strComCode = (String) session.getAttribute("myComCode");
		nodeList = taskDealService.findNodeList(strUserCode, strComCode);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 查询起始日期处理，暂时处理为上月一号
		Calendar cal = Calendar.getInstance();
		int month = 12;
		if (Calendar.MONTH != 1) {
			month = cal.get(Calendar.MONTH) - 1;
		} else {
			cal.set(Calendar.YEAR, Calendar.YEAR - 1);
		}
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		if (handType.equals("11")) // 核保
		{
			startDate = sdf.format(cal.getTime());
			todayDate = sdf.format(new Date());
			startDateRc = sdf.parse(startDate);
			todayDateRc = sdf.parse(todayDate);
			session.setAttribute("handTitle", getText("undwrt.action.commonCheckTask.underWrite"));
		} else if (handType.equals("22")) // 核赔
		{
			startDate = sdf.format(cal.getTime());
			endDate = sdf.format(new Date());
			session.setAttribute("handTitle", getText("undwrt.action.commonCheckTask.checkCompensate"));
		}
		session.setAttribute("handType", handType);
		session.setAttribute("editType", editType);
	}

	/**
	 * 準備查詢報價單處理任務.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void prepareQueryQta(HttpServletRequest request) throws Exception {
		riskCategoryList = taskDealService.findRiskCodeByRiskCategory();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.codeType", "RiskCategory");
		queryRule.addIn("id.validStatus", "1","2");//1有效 2顯示
		queryRule.addDescOrder("flag");
		prpDcode_riskCategoryList = prpDcodeService.findPrpDcodeList(queryRule);
		wfLog = new WfLog();
		HttpSession session = this.getSession();
		String strUserCode = (String) session.getAttribute("myUserCode");
		String strComCode = (String) session.getAttribute("myComCode");
		nodeList = taskDealService.findNodeList(strUserCode, strComCode);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 查询起始日期处理，暂时处理为上月一号
		Calendar cal = Calendar.getInstance();
		int month = 12;
		if (Calendar.MONTH != 1) {
			month = cal.get(Calendar.MONTH) - 1;
		} else {
			cal.set(Calendar.YEAR, Calendar.YEAR - 1);
		}
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		if (handType.equals("12")) // 报价单审核
		{
			startDate = sdf.format(cal.getTime());
			todayDate = sdf.format(new Date());
			startDateRc = sdf.parse(startDate);
			todayDateRc = sdf.parse(todayDate);
			session.setAttribute("handTitle", getText("undwrt.action.undwrtTaskDeal.check"));
		}
		session.setAttribute("handType", handType);
		session.setAttribute("editType", editType);
	}
	
	/**
	 * mantis： CAR0123，處理人員： David ，需求單編號： CAR0123 關聯強制險查
	 * 準備查詢關聯報價單處理任務.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void relevanceQueryQta(HttpServletRequest request) throws Exception {
		riskCategoryList = taskDealService.findRiskCodeByRiskCategory();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.codeType", "RiskCategory");
		queryRule.addIn("id.validStatus", "1","2");//1有效 2顯示
		queryRule.addDescOrder("flag");
		prpDcode_riskCategoryList = prpDcodeService.findPrpDcodeList(queryRule);
		wfLog = new WfLog();
		HttpSession session = this.getSession();
		String strUserCode = (String) session.getAttribute("myUserCode");
		String strComCode = (String) session.getAttribute("myComCode");
		nodeList = taskDealService.findNodeList(strUserCode, strComCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = sdf.format(session.getAttribute("relevFlowInTime1"));
		todayDate = sdf.format(session.getAttribute("relevFlowInTime2"));
		startDateRc = sdf.parse(startDate);
		todayDateRc = sdf.parse(todayDate);
		session.setAttribute("handTitle", getText("undwrt.action.undwrtTaskDeal.check"));
		session.setAttribute("handType", handType);
		session.setAttribute("editType", editType);
	}

	/**
	 * 執行任務查詢.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void query(HttpServletRequest request) throws UserException, Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		HttpSession session = request.getSession();

		CommonSession.setHandleSession(request, handType, editType);
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 10);
		boolean nodeStatusView = false; // 是否需要从视图中查数据
		String[] nodeStatusVal = request.getParameterValues("nodeStatus");
		for (int a = 0; a < nodeStatusVal.length; a++) {
			if (nodeStatusVal[a].equals("0")) {
				nodeStatusView = true;
			}
		}

		//add by xuhuiling 2016年8月21日 begin
		//查询当前人工开关的状态
		String valueType=taskDealService.getRenGongKaiGuanStatu();
		System.out.printf("valueType:"+valueType);
		session.setAttribute("valueType",valueType);
		//add by xuhuiling 2016年8月21日 end

		// 没有对wFlog表中数据进行转储，不使用视图查询
		nodeStatusView = false;

		session.setAttribute("nodeStatusView", nodeStatusView);

		String statement = null;
		if (handType.equals("11"))// 核保
		{
			statement = taskDealViewHelper.getHebaoTaskQueryStatement(request, nodeStatusView);
		} else if (handType.equals("12"))// 报价单审核
		{
			if(actionType.equals("queryQtaStats"))
        	{
        		statement = taskDealViewHelper.getHebaoTaskQueryQtaStatement(request,false);
        	}
        	else
        	{
        	statement = taskDealViewHelper.getHebaoTaskQueryStatement(request,nodeStatusView);
        	}
		} else if (handType.equals("22"))// 核赔
		{
			statement = taskDealViewHelper.getHepeiTaskQueryStatement(request, false);
		}
		session.setAttribute("UndwrtQueryStatement", statement);

		Page page = null;
		Page allPage = null;
		PageRecord pageRecord = null;

		if (handType.equals("12")) {
			//需求变更，报价单审核通过走工作流20130117,查询按原规则
        	if(actionType.equals("queryQtaStats"))
        	{
        		page = taskDealService.findByStatementQta(statement, pageNo, rowsPerPage, nodeStatusView);
        		allPage = taskDealService.findAllByStatementQta(statement, pageNo, rowsPerPage ,nodeStatusView);
        		pageRecord = taskDealService.findByStatementQtaPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
        		displayUndwrtTaskList = allPage.getResult();
        	}
        	else
        	{
        		page = taskDealService.findByStatement(statement, pageNo, rowsPerPage, nodeStatusView);
        		pageRecord = taskDealService.findByStatementPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
        	}
		} else {
			page = taskDealService.findByStatement(statement, pageNo, rowsPerPage, nodeStatusView);
			pageRecord = taskDealService.findByStatementPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
		}
		undwrtTaskList = page.getResult();
		showColumnName = taskDealViewHelper.getShowColumnNameByRiskCategory(riskCategory);
		String[] nodeStatus = paramUtils.getParameterValues("nodeStatus");
		if (nodeStatus == null) {
			nodeStatus = new String[] { "" };
		}
		String[] enabled = taskDealViewHelper.getBatchButtonEnabledByNodeStatus(nodeStatus[0]);
		request.setAttribute("riskCategory", riskCategory);
		request.setAttribute("showColumnName", showColumnName);
		session.setAttribute("nodeStatusList", nodeStatusVal);
		session.setAttribute("nodeStatus", nodeStatus[0]);
		request.setAttribute("batchSuperiorButton", enabled[0]);
		request.setAttribute("batchJuniorButton", enabled[1]);
		request.setAttribute("batchUndoButton", enabled[2]);
		// 系统分页导航使用
		request.setAttribute("fm", new AbstractForm(pageRecord));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		session.setAttribute("relevFlowInTime1", sdf.parse(request.getParameter("flowInTime1")));
		session.setAttribute("relevFlowInTime2", sdf.parse(request.getParameter("flowInTime2")));
		session.removeAttribute("relevUndwrtBusiNo");
	}

	/**
	 * 繼續查詢任務.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void queryContinue(HttpServletRequest request) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		HttpSession session = request.getSession();
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 10);
		String statement = (String) session.getAttribute("UndwrtQueryStatement");
		boolean nodeStatusView = (Boolean) session.getAttribute("nodeStatusView");

		Page page = null;
		PageRecord pageRecord = null;

		if ("12".equals(handType)) {
			page = taskDealService.findByStatementQta(statement, pageNo, rowsPerPage, false);// false为不走视图写死的
			pageRecord = taskDealService.findByStatementQtaPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
		} else {
			page = taskDealService.findByStatement(statement, pageNo, rowsPerPage, nodeStatusView);
			pageRecord = taskDealService.findByStatementPageRecord(statement, pageNo, rowsPerPage, nodeStatusView);
		}
		undwrtTaskList = page.getResult();

		if (riskCategory == null) {
			riskCategory = "";
		}

		showColumnName = taskDealViewHelper.getShowColumnNameByRiskCategory(riskCategory);
		String nodeStatus = (String) session.getAttribute("nodeStatus");
		String[] enabled = taskDealViewHelper.getBatchButtonEnabledByNodeStatus(nodeStatus);
		request.setAttribute("riskCategory", riskCategory);
		request.setAttribute("showColumnName", showColumnName);
		request.setAttribute("batchSuperiorButton", enabled[0]);
		request.setAttribute("batchJuniorButton", enabled[1]);
		request.setAttribute("batchUndoButton", enabled[2]);
		// 系统分页导航使用
		request.setAttribute("fm", new AbstractForm(pageRecord));
	}

	/**
	 * 準備批量提交上級查詢.
	 * 
	 * @param request
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void prepareBatchSubmitSuperior(HttpServletRequest request) throws Exception {
		List checkboxSelectCollection = taskDealViewHelper.getCheckboxSelectTaskCollection(request);
		List[] submitList = taskDealService.prepareBatchSubmitSuperior(checkboxSelectCollection);
		QueryRule queryRule = QueryRule.getInstance();// 获取QueryRule对象的Instance

		if (handType.equals("11"))// 核保
		{
			queryRule.addEqual("id.codeType", "HbNotionCode");
			notionList = prpDcodeService.findPrpDcodeList(queryRule);
		} else if (handType.equals("22"))// 核赔
		{
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.codeType", "HpNotionCode");
			notionList = prpDcodeService.findPrpDcodeList(queryRule);
		}
		if (submitList[0].size() > 0) {
			yesToSubmitList = submitList[0];
		}
		if (submitList[1].size() > 0) {
			noToSubmitList = submitList[1];
		}
	}

	/**
	 * 獲取屬性動作類型.
	 * 
	 * @return 屬性動作類型的值
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * 設置屬性動作類型.
	 * 
	 * @param actionType
	 *            待設置的動作類型的值
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getHandType() {
		return handType;
	}

	/**
	 * 設置屬性處理類型.
	 * 
	 * @param handType
	 *            待設置的處理類型的值
	 */
	public void setHandType(String handType) {
		this.handType = handType;
	}

	/**
	 * 獲取屬性編輯類型.
	 * 
	 * @return 屬性編輯類型的值
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 設置屬性編輯類型.
	 * 
	 * @param editType
	 *            待設置的編輯類型的值
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 獲取屬性工作流日誌.
	 * 
	 * @return 屬性工作流日誌的值
	 */
	public WfLog getWfLog() {
		return wfLog;
	}

	/**
	 * 設置屬性工作流日誌.
	 * 
	 * @param wfLog
	 *            待設置的工作流日誌的值
	 */
	public void setWfLog(WfLog wfLog) {
		this.wfLog = wfLog;
	}

	/**
	 * 獲取屬性起始日期.
	 * 
	 * @return 屬性起始日期的值
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 設置屬性起始日期.
	 * 
	 * @param startDate
	 *            待設置的起始日期的值
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 獲取屬性當前日期.
	 * 
	 * @return 屬性當前日期的值
	 */
	public String getTodayDate() {
		return todayDate;
	}

	/**
	 * 設置屬性當前日期.
	 * 
	 * @param todayDate
	 *            待設置的當前日期的值
	 */
	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	/**
	 * 獲取屬性終止日期.
	 * 
	 * @return 屬性終止日期的值
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 設置屬性終止日期.
	 * 
	 * @param endDate
	 *            待設置的終止日期的值
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 獲取屬性核保處理任務服務接口.
	 * 
	 * @return 屬性核保處理任務服務接口的值
	 */
	public TaskDealService getTaskDealService() {
		return taskDealService;
	}

	/**
	 * 設置屬性核保處理任務服務接口.
	 * 
	 * @param taskDealService
	 *            待設置的核保處理任務服務接口的值
	 */
	public void setTaskDealService(TaskDealService taskDealService) {
		this.taskDealService = taskDealService;
	}

	/**
	 * 獲取屬性證件類型.
	 * 
	 * @return 屬性證件類型的值
	 */
	public List getIdentifyTypeList() {
		return identifyTypeList;
	}

	/**
	 * 設置屬性證件類型.
	 * 
	 * @param identifyTypeList
	 *            待設置的證件類型的值
	 */
	public void setIdentifyTypeList(List identifyTypeList) {
		this.identifyTypeList = identifyTypeList;
	}

	/**
	 * 獲取屬性險種大類列表.
	 * 
	 * @return 屬性險種大類列表的值
	 */
	public List getRiskCategoryList() {
		return riskCategoryList;
	}

	/**
	 * 設置屬性險種大類列表.
	 * 
	 * @param riskCategoryList
	 *            待設置的險種大類列表的值
	 */
	public void setRiskCategoryList(List riskCategoryList) {
		this.riskCategoryList = riskCategoryList;
	}

	/**
	 * 獲取屬性節點列表.
	 * 
	 * @return 屬性節點列表的值
	 */
	public List getNodeList() {
		return nodeList;
	}

	/**
	 * 設置屬性節點列表.
	 * 
	 * @param nodeList
	 *            待設置的節點列表的值
	 */
	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}

	/**
	 * 獲取屬性險種大類.
	 * 
	 * @return 屬性險種大類的值
	 */
	public String getRiskCategory() {
		return riskCategory;
	}

	/**
	 * 設置屬性險種大類.
	 * 
	 * @param riskCategory
	 *            待設置的險種大類的值
	 */
	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}

	/**
	 * 獲取屬性列名.
	 * 
	 * @return 屬性列名的值
	 */
	public String getShowColumnName() {
		return showColumnName;
	}

	/**
	 * 設置屬性列名.
	 * 
	 * @param showColumnName
	 *            待設置的列名的值
	 */
	public void setShowColumnName(String showColumnName) {
		this.showColumnName = showColumnName;
	}

	/**
	 * 獲取屬性基礎代碼表接口.
	 * 
	 * @return 屬性基礎代碼表接口的值
	 */
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	/**
	 * 設置屬性基礎代碼表接口.
	 * 
	 * @param prpDcodeService
	 *            待設置的基礎代碼表接口的值
	 */
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	/**
	 * 獲取屬性提交任務列表.
	 * 
	 * @return 屬性提交任務列表的值
	 */
	public List getYesToSubmitList() {
		return yesToSubmitList;
	}

	/**
	 * 設置屬性提交任務列表.
	 * 
	 * @param yesToSubmitList
	 *            待設置的提交任務列表的值
	 */
	public void setYesToSubmitList(List yesToSubmitList) {
		this.yesToSubmitList = yesToSubmitList;
	}

	/**
	 * 獲取屬性提交任務列表.
	 * 
	 * @return 屬性提交任務列表的值
	 */
	public List getNoToSubmitList() {
		return noToSubmitList;
	}

	/**
	 * 設置屬性提交任務列表.
	 * 
	 * @param noToSubmitList
	 *            待設置的提交任務列表的值
	 */
	public void setNoToSubmitList(List noToSubmitList) {
		this.noToSubmitList = noToSubmitList;
	}

	/**
	 * 獲取屬性核保任務列表.
	 * 
	 * @return 屬性核保任務列表的值
	 */
	public List getUndwrtTaskList() {
		return undwrtTaskList;
	}

	/**
	 * 設置屬性核保任務列表.
	 * 
	 * @param undwrtTaskList
	 *            待設置的核保任務列表的值
	 */
	public void setUndwrtTaskList(List undwrtTaskList) {
		this.undwrtTaskList = undwrtTaskList;
	}

	/**
	 * 獲取屬性核保任務列表.
	 * 
	 * @return 屬性核保任務列表的值
	 */
	public List getDisplayUndwrtTaskList() {
		return displayUndwrtTaskList;
	}

	/**
	 * 設置屬性核保任務列表.
	 * 
	 * @param displayUndwrtTaskList
	 *            待設置的核保任務列表的值
	 */
	public void setDisplayUndwrtTaskList(List displayUndwrtTaskList) {
		this.displayUndwrtTaskList = displayUndwrtTaskList;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性民國年起始日期.
	 * 
	 * @return民國年起始日期的值
	 */
	public Date getStartDateRc() {
		return startDateRc;
	}

	/**
	 * 設置屬性民國年起始日期.
	 * 
	 * @param startDateRc
	 *            待設置的起始日期 rc的值
	 */
	public void setStartDateRc(Date startDateRc) {
		this.startDateRc = startDateRc;
	}

	/**
	 * 獲取屬性民國年當前日期.
	 * 
	 * @return民國年當前日期的值
	 */
	public Date getTodayDateRc() {
		return todayDateRc;
	}

	/**
	 * 設置屬性民國年當前日期.
	 * 
	 * @param todayDateRc
	 *            待設置的當前日期 rc的值
	 */
	public void setTodayDateRc(Date todayDateRc) {
		this.todayDateRc = todayDateRc;
	}

	/**
	 * 獲取屬性日期.
	 * 
	 * @return 屬性日期的值
	 */
	public Date[] getDate() {
		return date;
	}

	/**
	 * 設置屬性日期.
	 * 
	 * @param date
	 *            待設置的日期的值
	 */
	public void setDate(Date[] date) {
		this.date = date;
	}
	
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * 獲取任務處理幫助類.
	 * 
	 * @return the 任務處理幫助類
	 */
	public TaskDealViewHelper getTaskDealViewHelper() {
		return taskDealViewHelper;
	}

	/**
	 * 設置任務處理幫助類.
	 * 
	 * @param taskDealViewHelper
	 *            待設置任務處理幫助類
	 */
	public void setTaskDealViewHelper(TaskDealViewHelper taskDealViewHelper) {
		this.taskDealViewHelper = taskDealViewHelper;
	}
	
	/**
	 * 獲取屬性險種大類分類列表.
	 * 
	 * @return the 屬性險種大類分類列表
	 */
	public List<PrpDcode> getPrpDcode_riskCategoryList() {
		return prpDcode_riskCategoryList;
	}

	/**
	 * 設置屬性險種大類分類列表.
	 * 
	 * @param prpDcode_riskCategoryList
	 *            the new 屬性險種大類分類列表
	 */
	public void setPrpDcode_riskCategoryList(List<PrpDcode> prpDcode_riskCategoryList) {
		this.prpDcode_riskCategoryList = prpDcode_riskCategoryList;
	}
	//add by xuhuiling 需求150 begin
	/**
	 * 獲取當的單號作業狀態
	 * @throws Exception
	 */
	public void getWorkStatus()throws Exception{
		String busiNo = this.getRequest().getParameter("busiNo");//獲取保單號
		String busiType = this.getRequest().getParameter("busiType");//獲取保單類型
		String workStatus = taskDealService.getWorkStatusForBusiNo(busiNo, busiType);
		System.out.println("workStatus:"+workStatus);
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter writer = response.getWriter();
		writer.print(workStatus);
		writer.flush();
		writer.close();

	}
	/**
	 * 獲取當前單號的人工審核開關
	 * @throws Exception
	 */
	public void getValueType()throws Exception{
		String renGongKaiGuanStatu = taskDealService.getRenGongKaiGuanStatu();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter writer = response.getWriter();
		writer.print(renGongKaiGuanStatu);
		writer.flush();
		writer.close();
	}
	
	/**
	 *  調用AML系統
	 * @throws Exception 
	 */
	public void amlSystem() throws Exception{
		String busiNo = this.getRequest().getParameter("busiNo");//獲取保單號
		String businessType = this.getRequest().getParameter("busiType");//獲取保單類型
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter writer = response.getWriter();
		String message = "";
		String strUserCode =(String) super.getSession().getAttribute("userCode");
		try {
			//调用AML系统
			boolean callPrpinsAml = prpFeedBackService.callPrpinsAml(businessType, busiNo,strUserCode);
			if(callPrpinsAml){
				message="調用AML系統成功！";
			}else {
				message="調用AML系統失敗！！！";
			}
			//amlService.callAmlMethod(prpQmain, prpTmain, prpPhead, prpCPmain,userInfo, businessType, busiNo);
		} catch (Exception e) {
			e.printStackTrace();
			message = " AML系統異常！";
		}
		String workStatus = taskDealService.getWorkStatusForBusiNo(busiNo, businessType);
		if(workStatus!=null && "06".equals(workStatus)){
			message = "調用AML系統超時！";
		}
		if(message==null || "".equals(message)){
			message = "調用AML系統成功！";
		}
		writer.print(message);
		writer.flush();
		writer.close();

	}
	
	//add by xuhuiling 需求150 同時獲取人工開關和作業狀態 begin
	
	public void getWorkStatusAndValueType() throws Exception{
		String busiNo = this.getRequest().getParameter("busiNo");//獲取保單號
		String busiType = this.getRequest().getParameter("busiType");//獲取保單類型
		String workStatus = taskDealService.getWorkStatusForBusiNo(busiNo, busiType);
		String valueType = taskDealService.getRenGongKaiGuanStatu();
		Map<String,String> map = new HashMap<String,String>();
		map.put("workStatus", workStatus);
		map.put("valueType", valueType);
		JSONObject jsonObject = JSONObject.fromBean(map);
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObject.toString());
		writer.flush();
		writer.close();
	}
	
	/**
	 * songxin
	 * 獲取核保狀態和收費信息
	 * @throws Exception
	 */
	public void getPayRef()throws Exception{
		String busiNo = this.getRequest().getParameter("busiNo");//獲取保單號
		String busiType = this.getRequest().getParameter("busiType");//獲取保單類型
		String flag = taskDealService.getPayrefAndUnd(busiNo);
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		PrintWriter writer = response.getWriter();
		writer.print(flag);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 更新收付信息
	 * @throws Exception
	 */
	public String updatePayRef() throws Exception{
		String busiType = this.getRequest().getParameter("busiType");//獲取保單類型
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化
		String[] busiNo = new String[14];
		busiNo[0] = this.getRequest().getParameter("proposalNo");//獲取保單號
		busiNo[1] = this.getRequest().getParameter("virtualNo");//繳款單號/虛擬編碼
		busiNo[2] = this.getRequest().getParameter("appliName");//要保人
		busiNo[3] = this.getRequest().getParameter("mainPolicyNo");//強制證號
		busiNo[4] = this.getRequest().getParameter("payDate");//繳費日期
		busiNo[5] = this.getRequest().getParameter("payWay");//繳費方式
		busiNo[6] = this.getRequest().getParameter("checkAccount");//支票號碼
		busiNo[7] = this.getRequest().getParameter("payAmount");//收費金額
		busiNo[8] = this.getRequest().getParameter("expireDate");//支票日期
		busiNo[9] = this.getRequest().getParameter("creditAmount");//信用卡金額
		busiNo[10] = this.getRequest().getParameter("checkAmount");//支票金額
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
		HttpSession session = this.getRequest().getSession();
		busiNo[11] = this.getRequest().getParameter("issuerName");//開票人
		busiNo[12] = this.getRequest().getParameter("approvalCode");//授權碼
		busiNo[13] = (String) session.getAttribute("myUserCode"); //登入者
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END
		taskDealService.saveIntfPrpjpayrefrec(busiNo, busiType);

		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
		String mainPolicyNo = this.getRequest().getParameter("mainPolicyNo"); // 強制證號 (關聯單號)
		String plusMsg = "";
		if (StringUtils.isNotBlank(mainPolicyNo)) {
			plusMsg = "<br>關聯單" + mainPolicyNo + "繳費成功！";
		}
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
		
		if("T".equals(busiType)){
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
			super.getRequest().setAttribute("message", "要保書"+busiNo[0]+"繳費成功！" + plusMsg);
		}else if("E".equals(busiType)){
			super.getRequest().setAttribute("message", "批單"+busiNo[0]+"繳費成功！");
		}
		
		return SUCCESS;
	}
	/**
	 * 查詢銀行信息
	 * @return
	 * @throws Exception
	 */
	public String queryBankInfo() throws Exception {
		String bankCode = this.getRequest().getParameter("LastBankCode");
		// 定义list对象向页面返回银行名称
		PrpDBankInfo prpDBankInfo = taskDealService.queryBankInfo(bankCode);
		List<PrpDBankInfo> prpDBankInfolist = new ArrayList<PrpDBankInfo>();
		if (prpDBankInfo != null) {
			prpDBankInfolist.add(prpDBankInfo);
		}
		Page page = new Page(1, 1, 1, prpDBankInfolist);
		if (prpDBankInfolist.size() == 0) {
			// 为0时表示没有查出名称
			page.setMessage("0");
		} else {
			// 为1时表示查出数据
			page.setMessage("1");
		}
		this.writeJSONData(page, "bankName");
		return NONE;
	}
	/**
	 * 更新收付信息
	 * @throws Exception
	 */
	public String getPayFeeInfo() throws Exception{
		HttpServletRequest request = this.getRequest();
		String busiNo = this.getRequest().getParameter("busiNo");//獲取保單號
		String busiType = this.getRequest().getParameter("busiType");//獲取保單類型
		String appliName = "";;
		String mainPolicyNo = "";
		BigDecimal sumpremium = BigDecimal.ZERO;
		BigDecimal chgpremium = BigDecimal.ZERO;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		todayDate = sdf.format(new Date());
		todayDateRc = sdf.parse(todayDate);
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
		Date refStartDate = null;
		Date refEndDate = null;
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化
		String validDate = "";
		if("T".equals(busiType)){
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(busiNo);
			if(null!=prpTmain) {
				appliName = prpTmain.getAppliName();
				if("A01".equals(prpTmain.getRiskCode())) {
					request.setAttribute("proposalNo", busiNo);
					sumpremium = prpTmain.getSumPremium();
					List<PrpTmainSub> prpTmainsubs = prpTmain.getPrpTmainSubs();
					if(null!=prpTmainsubs && prpTmainsubs.size()>0) {
						// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
						//mainPolicyNo = prpTmainsubs.get(0).getId().getMainPolicyNo();
						if("111".equals(prpTmainsubs.get(0).getFlag())) {
							// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
							mainPolicyNo = prpTmainsubs.get(0).getId().getMainPolicyNo();
							PrpTmain prpTmainCI = policyService.getPrpTmainByProposalNo(mainPolicyNo);
							if(null!=prpTmainCI) {
								sumpremium = sumpremium.add(prpTmainCI.getSumPremium());
								// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
								refStartDate = prpTmainCI.getStartDate();
								refEndDate = prpTmainCI.getEndDate();
								// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
							}
						}else{
							// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化
							//request.setAttribute("mainPolicyNo", "");
						}
					}
					request.setAttribute("mainPolicyNo", mainPolicyNo);
				} else {
					sumpremium = prpTmain.getSumPremium();
					List<PrpTmainSub> prpTmainsubs = policyService.getPrpTmainSubByMainPolicyNo(busiNo);
					if(null!=prpTmainsubs && prpTmainsubs.size()>0) {
						String proposalnoBI = prpTmainsubs.get(0).getId().getProposalNo();
						if("111".equals(prpTmainsubs.get(0).getFlag())) {
							PrpTmain prpTmainBI = policyService.getPrpTmainByProposalNo(proposalnoBI);
							if(null!=prpTmainBI) {
								request.setAttribute("proposalNo", proposalnoBI);
								request.setAttribute("mainPolicyNo", busiNo);
								sumpremium = sumpremium.add(prpTmainBI.getSumPremium());
								// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
								refStartDate = prpTmainBI.getStartDate();
								refEndDate = prpTmainBI.getEndDate();
								// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
							} 
						} else {
							request.setAttribute("proposalNo", busiNo);
							request.setAttribute("mainPolicyNo", "");
						}
					} else {
						request.setAttribute("proposalNo", busiNo);
						request.setAttribute("mainPolicyNo", "");
					}
				}
			}
			request.setAttribute("payAmount", sumpremium+"");
			request.setAttribute("appliName", appliName);
			//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
			startDate = sdf.format(prpTmain.getStartDate());
			endDate = sdf.format(prpTmain.getEndDate());
			validDate = startDate;
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
			if (refStartDate!=null) {
				if (refStartDate.getTime() < prpTmain.getStartDate().getTime()) {
					validDate = sdf.format(refStartDate);
					logger.info(">>> validDate: " + validDate);
				}
			}
			// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end
			request.setAttribute("startDate", sdf.parse(startDate));
			request.setAttribute("endDate", sdf.parse(endDate));
			//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END
		}else if("E".equals(busiType)){
			PrpPhead prpphead = endorseService.getPrpPheadByEndorseNo(busiNo);
			List<PrpPmain> prppmainList = prpphead.getPrpPmains();
			if(null != prpphead && null != prppmainList && prppmainList.size()>0){
				request.setAttribute("proposalNo", busiNo);
				request.setAttribute("payAmount", prppmainList.get(0).getChgPremium());
				request.setAttribute("appliName", prppmainList.get(0).getAppliName());
				request.setAttribute("mainPolicyNo", "");
				//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
				validDate = sdf.format(prpphead.getValidDate());
				startDate = sdf.format(prppmainList.get(0).getStartDate());
				endDate = sdf.format(prppmainList.get(0).getEndDate());
				request.setAttribute("startDate", sdf.parse(startDate));
				request.setAttribute("endDate", sdf.parse(endDate));
				//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END
			}
		}
		List list = taskDealService.queryPrpDprint(busiNo);
		if(null!=list && list.size()>0) {
			PrpDprint prpDprintq = (PrpDprint) list.get(0);
			request.setAttribute("virtualNo", prpDprintq.getId().getPrintvirtualCode()); //虛擬編碼
		}	
		request.setAttribute("busiType", busiType);
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 START
		String userCode = (String) request.getSession().getAttribute("myUserCode");
		request.setAttribute("validDate", sdf.parse(validDate));
		request.setAttribute("userCode", userCode);
		//mantis： CAR0491，處理人員：CC009，需求單編號：車險核保系統，收費註記功能優化 END

		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- start
		request.setAttribute("refStartDate", refStartDate);
		request.setAttribute("refEndDate", refEndDate);
		// mantis：CAR0520，處理人員：DP0714，車險繳費註記功能優化 -- end

		//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 START
		String checkPay = "";
		try{
			checkPay = commonService.queryCheckPay(busiNo);
		}catch(Exception e){}
		request.setAttribute("checkPay", checkPay);
		//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 END
		
		return SUCCESS;
		
	}

	//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 START
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	//mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整 END
}
