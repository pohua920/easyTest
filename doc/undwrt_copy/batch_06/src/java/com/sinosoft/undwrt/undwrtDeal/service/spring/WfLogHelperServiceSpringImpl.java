package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.bl.facade.BLPrpLchargeFacade;
import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTaddress;
import com.sinosoft.common.schema.model.PrpTexpense;
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.platform.bl.facade.BLPrpDcompanyFacade;
import com.sinosoft.platform.bl.facade.BLSwfPathNewFacade;
import com.sinosoft.platform.dto.domain.SwfPathNewDto;
import com.sinosoft.prpall.blsvr.cb.BLPrpCmainCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmainCovernote;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.prpall.dto.domain.PrpCPexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCPgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCexpenseDto;
import com.sinosoft.prpall.dto.domain.PrpCgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpLcompensateDto;
import com.sinosoft.prpall.dto.domain.PrpLprepayDto;
import com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpTgradeDto;
import com.sinosoft.prpins.common.util.EvaluateUtil;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnitId;
import com.sinosoft.reins.common.service.facade.BLReinsCalService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.util.QueryAction;
import com.sinosoft.undwrt.common.vo.CommonAmountAndPremiumVo;
import com.sinosoft.undwrt.common.vo.WfLogVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.model.WfLogId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonCheckTaskService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;
import com.sinosoft.undwrt.undwrtDeal.vo.ClaimInfoVo;
import com.sinosoft.undwrt.undwrtDeal.vo.PolicyAbstractInfoVo;
import com.sinosoft.undwrt.undwrtDeal.web.CommonDealSubmitAction;

/**
 * 核保系統幫助服務實現類
 */

public class WfLogHelperServiceSpringImpl extends GenericDaoHibernate implements
		WfLogHelperService {

	/** 屬性默認每頁顯示記錄條數. */
	private int defaultPageSize = 50;

	/** 屬性總記錄條數. */
	private int totalCount;

	/** 屬性總頁數. */
	private int totalPage;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/** 屬性核保服務接口. */
	private CommonCheckTaskService commonCheckTaskService;

	/** 屬性危險單位信息服務接口. */
	private CommonDangerInfoService commonDangerInfoService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性危險單位信息數組. */
	static ArrayList dangerArrayList = null;

	/** 屬性再保分保試算接口. */
	private BLReinsCalService blReinsCalService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/**
	 * 根據頁面輸入條件拼寫Where字句.
	 * 
	 * @param req
	 *            請求對象
	 * @return 返回完整的查詢條件
	 * @throws Exception
	 *             異常
	 */
	public String getWherePart(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		QueryAction uiQueryAction = new QueryAction();
		String handType = req.getParameter("HandType");
		String editType = req.getParameter("EditType");
		if (handType == null || handType.equals("")) {
			handType = (String) session.getAttribute("HandType");
			editType = (String) session.getAttribute("EditType");
		}

		// 初始条件
		String statement = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog "
				+ " WHERE WfLog.ModelNo = UwGrade.ModelNo"
				+ " AND UwGrade.GroupNo = UwGroup.GroupNo"
				+ " AND Wflog.RiskCode = UwGroup.RiskCode"
				+ " AND Wflog.ComCode = UwGroup.ComCode"
				+ " AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1";
		if (handType.equals("11")) {
			statement += " AND WfLog.BusinessType NOT IN('C','Y')";
		} else if (handType.equals("22")) {
			statement += " AND WfLog.BusinessType IN('C','Y')";
		}
		statement = statement + this.getQueryConditionStatement(req);
		return statement;
	}

	/**
	 * 獲取查詢的聲明sql.
	 * 
	 * @param req
	 *            請求對象
	 * @return 查詢的聲明
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#getQueryConditionStatement(javax.servlet.http.HttpServletRequest)
	 */
	public String getQueryConditionStatement(HttpServletRequest req) {
		String riskCategoryTag = "=";
		String riskCategoryVal = StringUtils.trimToEmpty(req
				.getParameter("riskCategory"));
		String riskCodeTag = "=";
		String[] riskCodeVal = req.getParameterValues("riskCode");
		String businessNoTag = req.getParameter("businessNoTag");
		String businessNoVal = StringUtils.trimToEmpty(req
				.getParameter("businessNo"));
		String contractNoTag = req.getParameter("contractNoTag");
		String contractNoVal = StringUtils.trimToEmpty(req
				.getParameter("contractNo"));
		String comCodeTag = req.getParameter("comCodeTag");
		String comCodeVal = StringUtils
				.trimToEmpty(req.getParameter("comCode"));
		String nodeStatusTag = "=";
		String[] nodeStatusVal = req.getParameterValues("nodeStatus");
		String flowInTime1Tag = ">=";
		String flowInTime1Val = StringUtils.trimToEmpty(req
				.getParameter("flowInTime1"));
		String flowInTime2Tag = "<=";
		String flowInTime2Val = StringUtils.trimToEmpty(req
				.getParameter("flowInTime2"));//
		if (flowInTime1Val.length() > 0) {
			flowInTime1Val = flowInTime1Val + " 00:00:00";
		}
		if (flowInTime2Val.length() > 0) {
			flowInTime2Val = flowInTime2Val + " 23:59:59";
		}
		String licenseNoTag = req.getParameter("licenseNoTag");
		String licenseNoVal = StringUtils.trimToEmpty(req
				.getParameter("licenseNo"));
		String identifyTypeTag = "=";
		String identifyTypeVal = req.getParameter("identifyType");
		String identifyNumberTag = req.getParameter("identifyNumberTag");
		String identifyNumberVal = StringUtils.trimToEmpty(req
				.getParameter("identifyNumber"));
		String relateContractNoYesNoTag = "=";
		String[] relateContractNoYesNoVal = req
				.getParameterValues("relateContractNoYesNo");
		String relateContractNoTag = req.getParameter("relateContractNoTag");
		String relateContractNoVal = StringUtils.trimToEmpty(req
				.getParameter("relateContractNo"));
		String policyNoTag = StringUtils.trimToEmpty(req
				.getParameter("policyNoTag"));
		String policyNo = StringUtils.trimToEmpty(req.getParameter("policyNo"));
		String claimNoTag = StringUtils.trimToEmpty(req
				.getParameter("claimNoTag"));
		String claimNo = StringUtils.trimToEmpty(req.getParameter("claimNo"));

		String statement = "";
		QueryAction uiQueryAction = new QueryAction();
		statement += uiQueryAction.getCharConditions("Wflog.RiskCategory",
				riskCategoryTag, riskCategoryVal);
		statement += uiQueryAction.getCharInConditions("Wflog.RiskCode",
				riskCodeVal);
		statement += uiQueryAction.getCharConditions("Wflog.BusinessNo",
				businessNoTag, businessNoVal);
		statement += uiQueryAction.getCharConditions("Wflog.ContractNo",
				contractNoTag, contractNoVal);
		statement += uiQueryAction.getCharConditions("Wflog.ComCode",
				comCodeTag, comCodeVal);
		statement += uiQueryAction.getCharInConditions("Wflog.NodeStatus",
				nodeStatusVal);
		statement += uiQueryAction.getCharConditions("Wflog.FlowInTime",
				flowInTime1Tag, flowInTime1Val);
		statement += uiQueryAction.getCharConditions("Wflog.FlowInTime",
				flowInTime2Tag, flowInTime2Val);
		statement += uiQueryAction.getCharConditions("Wflog.PolicyNo",
				policyNoTag, policyNo);
		statement += uiQueryAction.getCharConditions("Wflog.ClaimNo",
				claimNoTag, claimNo);

		if (riskCategoryVal.equals("1"))// 车险
		{
			statement += uiQueryAction.getCharConditions("Wflog.LicenseNo",
					licenseNoTag, licenseNoVal);
		} else if (riskCategoryVal.equals("4"))// 意健
		{
			statement += uiQueryAction.getCharConditions("Wflog.IdentifyType",
					identifyTypeTag, identifyTypeVal);
			statement += uiQueryAction.getCharConditions(
					"Wflog.IdentifyNumber", identifyNumberTag,
					identifyNumberVal);
		} else if (riskCategoryVal.equals("2"))// 水险（货运险）
		{
			if (relateContractNoYesNoVal != null
					&& relateContractNoYesNoVal.length == 1) {
				if (relateContractNoYesNoVal[0].equals("Yes")) {
					if (relateContractNoVal.length() > 0) {
						statement += uiQueryAction.getCharConditions(
								"Wflog.RelateContractNo", relateContractNoTag,
								relateContractNoVal);
					} else {
						statement += " AND Wflog.RelateContractNo is not null";
					}
				} else {
					statement += " AND Wflog.RelateContractNo is null";
				}
			} else if (relateContractNoYesNoVal != null
					&& relateContractNoYesNoVal.length == 2) {
				if (relateContractNoVal.length() > 0) {
					statement += uiQueryAction.getCharConditions(
							"Wflog.RelateContractNo", relateContractNoTag,
							relateContractNoVal);
				}
			}
		}
		return statement;
	}

	/**
	 * 設置撤銷任務列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setUndoQueryTaskList(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);

		Collection undoTaskList = new ArrayList();
		String sql = "";
		String strWherePart = "";
		String userCode = (String) req.getSession(false).getAttribute(
				"myUserCode");
		strWherePart = " a.OperatorCode='" + userCode.trim() + "'"
				+ " AND a.ModelNo=d.ModelNo" + " AND a.NodeNo=d.NodeNo"
				+ " AND a.NodeNo<>1" + " AND a.RiskCode=c.RiskCode"
				+ " AND a.ComCode=c.ComCode" + " AND c.GroupNo=d.GroupNo "
				+ " AND d.UserCode='" + userCode.trim() + "'"
				+ " AND a.NodeStatus='4'" + " AND a.LogNo <> 1";
		sql = "SELECT DISTINCT a.* FROM WfLog a,UwGroup c,UwGrade d"
				+ " WHERE " + strWherePart.trim();
		undoTaskList = wfLogService.findByConditions(sql);
		// 传入撤销任务列表
		session.setAttribute("undoTaskList", undoTaskList);
	}

	/**
	 * 獲取批量核保列表.
	 * 
	 * @param req
	 *            請求對象
	 * @param flowID
	 *            工作流號
	 * @param logNo
	 *            序號
	 * @return 批量核保列表
	 * @throws Exception
	 *             異常
	 */
	public Collection setBatchTaskViewToDto(HttpServletRequest req,
			String[] flowID, String[] logNo) throws Exception {
		HttpSession session = req.getSession(false);
		Collection wfLogList = new ArrayList();
		WfLog wfLog = null;
		String[] businessNo = req.getParameterValues("businessNo");
		String[] businessType = req.getParameterValues("businessType");
		String[] riskCode = req.getParameterValues("riskCode");
		// 统一指定第一条记录节点
		String nodeNo = req.getParameter("selectNodeNo");
		String[] modelNo = req.getParameterValues("modelNo");
		String[] operatorCode = req.getParameterValues("operatorCode");
		String[] flowStatus = req.getParameterValues("flowStatus");
		String userCode = (String) session.getAttribute("myUserCode");
		for (int i = 0; i < flowID.length; i++) {
			// 处理批量下发修改20130925
			boolean isAssociate = prpallService.isAssociation(riskCode[i],
					businessNo[i]);
			if (isAssociate) {
				if ("B01".equals(riskCode[i])) {
					PrpTmainSub prpTmainSub = prpallService.getPrpTmainSub(
							riskCode[i], businessNo[i]);
					String proposalno = prpTmainSub.getId().getProposalNo();
					boolean isExist = prpallService.isInArray(proposalno,
							businessNo);
					if (true == isExist) {
						continue;
					}
				}

			}
			wfLog = new WfLog();
			WfLogId wfLogId = new WfLogId();
			wfLogId.setFlowId(flowID[i]);
			wfLogId.setLogNo(Integer.parseInt(logNo[i]));
			wfLog.setId(wfLogId);
			wfLog.setModelNo(Integer.parseInt(modelNo[i]));
			wfLog.setNodeNo(Integer.parseInt(nodeNo));
			wfLog.setBusinessNo(businessNo[i]);
			wfLog.setBusinessType(businessType[i]);
			wfLog.setUserCode(userCode);
			wfLog.setOperatorCode(operatorCode[i]);
			wfLog.setFlowStatus(flowStatus[i]);
			wfLog.setFlag("1");
			wfLogList.add(wfLog);
		}
		return wfLogList;
	}

	/**
	 * 設置批量核保提交列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void setBatchTaskListDtoToView(HttpServletRequest req)
			throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		HttpSession session = req.getSession(true);
		String[] modelNo = req.getParameterValues("modelNo");
		String[] nodeNo = req.getParameterValues("nodeNo");
		String[] flowID = req.getParameterValues("flowID");
		String[] logNo = req.getParameterValues("logNo");
		String[] businessNo = req.getParameterValues("businessNo");
		String[] businessType = req.getParameterValues("businessType");
		String comCode = (String) req.getSession(false).getAttribute(
				"myComCode");
		String[] operateFlag = req.getParameterValues("operateFlag");
		String[] contractNo = req.getParameterValues("contractNo");
		String[] insuredName = req.getParameterValues("insuredName");
		String[] flowInTime = req.getParameterValues("flowInTime");
		String[] nodeName = req.getParameterValues("nodeName");
		String[] timeLimit = req.getParameterValues("timeLimit");
		String[] nodeStatusName = req.getParameterValues("nodeStatusName");
		String[] flowStatusName = req.getParameterValues("flowStatusName");
		String userCode = (String) session.getAttribute("myUserCode");
		String[] operatorCode = req.getParameterValues("operatorCode");
		String[] flowStatus = req.getParameterValues("flowStatus");
		String[] riskCode = req.getParameterValues("riskCode");
		String[] strComCode = req.getParameterValues("comCode");
		// 获取提交列表
		Collection colSubmitList = new ArrayList();
		Collection colBackList = new ArrayList();
		Collection colWfLogList = new ArrayList();
		WfLogVo wfLogDto = null;
		CommonDealSubmitAction uiCommonDealSubmitAction = new CommonDealSubmitAction();
		// UICommonCheckTaskAction uiCommonCheckTaskAction = new
		// UICommonCheckTaskAction();
		try {
			for (int i = 0; i < businessNo.length; i++) {
				if (operateFlag[i].equals("Y")) {

					if ("99".equals(modelNo[i])) {
						// 走规则引擎时，提交上级和下发修改的路径
						String[] arrNodeNo;
						String[] arrNodeName;
						BLSwfPathNewFacade blSwfPathNewFacade = new BLSwfPathNewFacade();
						SwfPathNewDto swfPathNewDto = new SwfPathNewDto();

						boolean isFind = false;
						while (!isFind) {
							swfPathNewDto = blSwfPathNewFacade
									.findByPrimaryKey(riskCode[i],
											strComCode[1]);
							// 如果没有找到就查找上级
							if (swfPathNewDto != null) {
								isFind = true;
							} else {
								BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
								com.sinosoft.platform.dto.domain.PrpDcompanyDto prpDcompanyDto = new com.sinosoft.platform.dto.domain.PrpDcompanyDto();
								prpDcompanyDto = blPrpDcompanyFacade
										.findByPrimaryKey(strComCode[1]);
								// 查到总公司还没有数据就抛出
								if (!prpDcompanyDto.getComCode().equals(
										prpDcompanyDto.getUpperComCode())) {
									strComCode[1] = prpDcompanyDto
											.getUpperComCode();
								} else {
									throw new UserException(
											-98,
											-9999,
											this.getClass().getName()
													+ ".getNodeNo()",
											internal.getText("undwrt.action.batchTask.noRoute"));
								}
							}
						}
						arrNodeNo = swfPathNewDto.getPath().split(",");
						arrNodeName = swfPathNewDto.getPathDesc().split(",");
						// 回退路径
						for (int j = (arrNodeNo.length - 1); j >= 0; j--) {
							if (Integer.parseInt(nodeNo[i]) > Integer
									.parseInt(arrNodeNo[j])) {
								WfLogVo wfPathDto = new WfLogVo();
								wfPathDto.setNodeNo(Integer
										.parseInt(arrNodeNo[j]));
								wfPathDto.setNodeName(arrNodeName[j]);
								if (wfPathDto == null || "".equals(wfPathDto)) {
									colBackList.add(wfPathDto);
								}
							}
						}
					} else {
						// colSubmitList =
						// uiCommonCheckTaskAction.getPassPath(Integer.parseInt(modelNo[i]),
						// Integer.parseInt(nodeNo[i]));
						// 回退列表
						// colBackList =
						// uiCommonDealSubmitAction.getBackList(flowID[i],
						// Integer.parseInt(logNo[i]),
						// Integer.parseInt(nodeNo[i]));
					}

					// 提交记录列表
					wfLogDto = new WfLogVo();
					wfLogDto.getId().setFlowId(flowID[i]);
					wfLogDto.setModelNo(Integer.parseInt(modelNo[i]));
					wfLogDto.setNodeNo(Integer.parseInt(nodeNo[i]));
					wfLogDto.setBusinessType(businessType[i]);
					wfLogDto.setOperatorCode(operatorCode[i]);
					wfLogDto.setUserCode(userCode);
					wfLogDto.setFlowStatus(flowStatus[i]);
					wfLogDto.setFlag("1");

					wfLogDto.setContractNo(contractNo[i]);
					wfLogDto.setBusinessNo(businessNo[i]);
					wfLogDto.setInsuredName(insuredName[i]);
					wfLogDto.setFlowInTime(flowInTime[i]);
					wfLogDto.setNodeName(nodeName[i]);
					wfLogDto.setTimeLimit(Integer.parseInt(timeLimit[i]));
					wfLogDto.setNodeStatusName(nodeStatusName[i]);
					wfLogDto.setFlowStatusName(flowStatusName[i]);
					colWfLogList.add(wfLogDto);
				}
			}
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
		session.setAttribute("submitList", colSubmitList);
		session.setAttribute("submitBackList", colBackList);
		session.setAttribute("wfLogList", colWfLogList);
	}

	// add by yanglib reason :批量下发路径查询方法
	/**
	 * 獲取 批量下發路徑.
	 * 
	 * @param req
	 *            請求對象
	 * @param iWfLogList
	 *            工作流日誌集合
	 * @return 批量下發路徑
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#getBackPathList(javax.servlet.http.HttpServletRequest,
	 *      java.util.Collection)
	 */
	public List getBackPathList(HttpServletRequest req, Collection iWfLogList)
			throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		List colBackList = new ArrayList();
		WfLog wfPathDto = new WfLog();
		try {
			Iterator it = iWfLogList.iterator();
			while (it.hasNext()) {
				WfLog wfLog = new WfLog();
				wfLog = (WfLog) it.next();
				if ("2".equals(wfLog.getResultCode())) {
					// 走规则引擎时，提交上级和下发修改的路径
					String[] arrNodeNo;
					String[] arrNodeName;
					String riskCode = wfLog.getRiskCode();
					String comcode = wfLog.getComCode();
					int nodeno = (int) wfLog.getNodeNo();
					BLSwfPathNewFacade blSwfPathNewFacade = new BLSwfPathNewFacade();
					SwfPathNewDto swfPathNewDto = new SwfPathNewDto();

					if ("".equals(wfPathDto.getNodeName())) {
						boolean isFind = false;
						while (!isFind) {
							swfPathNewDto = blSwfPathNewFacade
									.findByPrimaryKey(riskCode, comcode);
							// 如果没有找到就查找上级
							if (swfPathNewDto != null) {
								isFind = true;
							} else {
								BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
								com.sinosoft.platform.dto.domain.PrpDcompanyDto prpDcompanyDto = new com.sinosoft.platform.dto.domain.PrpDcompanyDto();
								prpDcompanyDto = blPrpDcompanyFacade
										.findByPrimaryKey(comcode);
								// 查到总公司还没有数据就抛出
								if (!prpDcompanyDto.getComCode().equals(
										prpDcompanyDto.getUpperComCode())) {
									comcode = prpDcompanyDto.getUpperComCode();
								} else {
									throw new UserException(
											-98,
											-9999,
											this.getClass().getName()
													+ ".getNodeNo()",
											internal.getText("undwrt.action.batchTask.noRoute"));
								}
							}
						}
						arrNodeNo = swfPathNewDto.getPath().split(",");
						arrNodeName = swfPathNewDto.getPathDesc().split(",");
						// 回退路径
						for (int j = 0; j < arrNodeNo.length - 1; j++) {
							if (nodeno > Integer.parseInt(arrNodeNo[j])) {
								System.out
										.println("=======arrNodeNo[j]====================="
												+ Integer
														.parseInt(arrNodeNo[j])
												+ "==============="
												+ arrNodeNo[j]);
								WfLogVo wfPathDto1 = new WfLogVo();
								wfPathDto1.setNodeNo(Integer
										.parseInt(arrNodeNo[j]));
								wfPathDto1.setNodeName(arrNodeName[j]);
								wfPathDto.setNodeName(arrNodeName[j]);
								colBackList.add(wfPathDto1);
							}
						}
					}
				} else {
					// colSubmitList =
					// uiCommonCheckTaskAction.getPassPath(Integer.parseInt(modelNo[i]),
					// Integer.parseInt(nodeNo[i]));
					// 回退列表
					colBackList = (List) wfLogService.getBackList(wfLog.getId()
							.getFlowId(), wfLog.getId().getLogNo(), wfLog
							.getNodeNo());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return colBackList;

	}

	/**
	 * 設置提交指定人員列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setSubmitUserrList(HttpServletRequest req) throws Exception {
		try {
			HttpSession session = req.getSession(true);
			Collection submitUserList = new ArrayList();
			String modelNo = req.getParameter("ModelNo");
			String nodeNo = req.getParameter("selectNodeNo");
			String businessType = req.getParameter("BusinessType");
			String businessNo = req.getParameter("BusinessNo");
			String flag = req.getParameter("Flag");
			// submitUserList = new
			// CommonDealSubmitAction().getSubmitUserList(Integer.parseInt(modelNo),
			// Integer.parseInt(nodeNo),
			// businessType, businessNo, flag);
			session.setAttribute("submitUserList", submitUserList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 設置任務列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setTaskMessage(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		String userCode = (String) session.getAttribute("myUserCode");
		String sql = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog "
				+ " WHERE WfLog.ModelNo = UwGrade.ModelNo "
				+ " AND UwGrade.UserCode = '"
				+ userCode
				+ "'"
				+ " AND WfLog.NodeNo = UwGrade.NodeNo"
				+ " AND UwGrade.GroupNo = UwGroup.GroupNo"
				+ " AND Wflog.RiskCode = UwGroup.RiskCode"
				+ " AND Wflog.ComCode = UwGroup.ComCode"
				// +
				// " AND ((WfLog.OperatorCode is null) OR (WfLog.OperatorCode='"
				// + userCode + "')) "
				+ " AND Wflog.LogNo <> 1" + " AND Wflog.NodeNo <> 1"
				+ " AND WfLog.NodeStatus NOT IN('4','0')";
		String wherePart = "";
		WfLogVo wfLogDto = new WfLogVo();

		ArrayList hebaoList = new ArrayList();
		ArrayList hepeiList = new ArrayList();
		int size = 0;
		int i = 0;
		int requestNum = 0;
		int notSubmitNum = 0;
		wherePart = " AND WfLog.BusinessType NOT IN('C','Y') ";
		hebaoList = (ArrayList) wfLogService.findByConditions(sql + wherePart);
		size = hebaoList.size();
		for (i = 0; i < size; i++) {
			// 查询待处理任务 1
			wfLogDto = (WfLogVo) hebaoList.get(i);
			if (wfLogDto.getNodeStatus().equals("1")) {
				requestNum++;
			}
			// 查询处理未提交任务 3
			else if (wfLogDto.getNodeStatus().equals("3")
					|| (wfLogDto.getNodeStatus().equals("2") && wfLogDto
							.getOperatorCode().equals(userCode))/*
																 * && wfLogDto.
																 * getOperatorCode
																 * (
																 * ).equals(userCode
																 * )
																 */) {
				notSubmitNum++;
			}
		}
		session.setAttribute("hebaoRequestNum", String.valueOf(requestNum));
		session.setAttribute("hebaoNotSubmitNum", String.valueOf(notSubmitNum));

		requestNum = 0;
		notSubmitNum = 0;
		wherePart = " AND WfLog.BusinessType IN('C','Y') ";
		hepeiList = (ArrayList) wfLogService.findByConditions(sql + wherePart);
		size = hepeiList.size();
		for (i = 0; i < size; i++) {
			wfLogDto = (WfLogVo) hepeiList.get(i);
			if (wfLogDto.getNodeStatus().equals("1")) {
				requestNum++;
			}
			// 查询处理未提交任务 3
			else if (wfLogDto.getNodeStatus().equals("3")
					|| (wfLogDto.getNodeStatus().equals("2") && wfLogDto
							.getOperatorCode().equals(userCode))/*
																 * && wfLogDto.
																 * getOperatorCode
																 * (
																 * ).equals(userCode
																 * )
																 */) {
				notSubmitNum++;
			}
		}
		session.setAttribute("hepeiRequestNum", String.valueOf(requestNum));
		session.setAttribute("hepeiNotSubmitNum", String.valueOf(notSubmitNum));
	}

	/**
	 * 檢查被保人曆史信息（曆史投保、曆史賠付）.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void checkHistoryInfo(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		String businessNo = req.getParameter("iBusinessNo");
		String businessType = req.getParameter("iBusinessType");
		String flag = "";
		Vector vcReturn = new Vector();

		if (businessType.equals("T")) {
			vcReturn = wfLogService.checkHistoryInfo(businessNo);
		}
		for (int i = 0; i < vcReturn.size(); i++) {
			flag = (String) vcReturn.get(i);
			if (flag.equals("historyProposal")) {
				session.setAttribute("historyProposal", "true");
			}
			if (flag.equals("historyLoss")) {
				session.setAttribute("historyLoss", "true");
			}
		}
	}

	/**
	 * 單獨獲取標的信息(拆分危險單位時調用，非拆分危險單位部分禁止調用).
	 * 
	 * @param req
	 *            請求對象
	 * @return 標的信息
	 * @throws Exception
	 *             異常
	 */
	public void getItemInfoToView(HttpServletRequest req) throws Exception {
		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String dangerNo = req.getParameter("dangerNo");
		String riskCode = req.getParameter("riskCode");// 意健险的特殊处理

		ArrayList itemKindList = new ArrayList();
		ArrayList dangerUnitList = new ArrayList();
		// 标的信息
		if (businessType.equals("T")) {
			PrpTDangerUnit prpTdangerUnitDto = new PrpTDangerUnit();
			PrpTDangerUnitId id = new PrpTDangerUnitId();
			id.setDangerNo(Integer.parseInt(dangerNo));
			id.setProposalNo(businessNo);
			prpTdangerUnitDto.setId(id);
			prpTdangerUnitDto.setRiskCode(riskCode);
			dangerUnitList.add(prpTdangerUnitDto);
			prpallService.saveDangerUnit(dangerUnitList, businessType);
			itemKindList = (ArrayList) wfLogService.getCustomPrpTitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("P")) // 保单
		{
			PrpCDangerUnit prpCdangerUnitDto = new PrpCDangerUnit();
			PrpCDangerUnitId id = new PrpCDangerUnitId();
			id.setDangerNo(Integer.parseInt(dangerNo));
			id.setPolicyNo(businessNo);
			prpCdangerUnitDto.setId(id);
			prpCdangerUnitDto.setRiskCode(riskCode);
			dangerUnitList.add(prpCdangerUnitDto);
			prpallService.saveDangerUnit(dangerUnitList, businessType);

			itemKindList = (ArrayList) wfLogService.getCustomPrpCitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("C")) // 赔单
		{
			itemKindList = (ArrayList) wfLogService.getCustomPrpCitemKindList(
					businessNo, riskCode);
		}
		if (businessType.equals("E")) {
			itemKindList = (ArrayList) wfLogService.getCustomPrpPitemKindList(
					businessNo, riskCode);
		}
		req.removeAttribute("ItemKind");
		req.setAttribute("ItemKind", itemKindList);
	}

	/**
	 * 根據業務類型，業務號，危險單位序號來獲取指定序號危險單位的信息.
	 * 
	 * @param req
	 *            待設置的危險單位主信息 to view by danger no的值
	 * @throws Exception
	 *             異常
	 */
	public void setDangerDetailToViewByDangerNo(HttpServletRequest req)
			throws Exception {

		InternationalizationUtil internal = new InternationalizationUtil();
		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String dangerNo = req.getParameter("dangerNo");
		String riskCode = req.getParameter("riskCode");
		// modify begin by lihua 20060420
		// 为了区分查看危险单位/操作危险单位（查看危险单位时，设置enterFlag＝1，操作危险单位enterFlag是空）
		String enterFlag = req.getParameter("enterFlag");
		// modify end by lihua 20060420
		// 为了区分查看危险单位/操作危险单位（查看危险单位时，设置enterFlag＝1，操作危险单位enterFlag是空）

		Collection dangerDetail = null;
		Collection planCurrencyType = null;
		Collection dangerExItemKind = null;

		// add begin by zhaijq 20060316 核保通过不允许再进行风险评估

		String underwriteFlag = "9";
		if (businessType.equals("T")) {
			PrpTmain prpTmain = policyService
					.getPrpTmainByProposalNo(businessNo);
			underwriteFlag = prpTmain.getUnderWriteFlag();
		} else if (businessType.equals("E")) {
			PrpPhead prpPhead = endorseService
					.getPrpPheadByEndorseNo(businessNo);
			underwriteFlag = prpPhead.getUnderWriteFlag();
		} else if (businessType.equals("P")) {
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(businessNo);
			underwriteFlag = prpCmain.getUnderWriteFlag();
		}
		// modify begin by lihua 20060420
		// 为了区分查看危险单位/操作危险单位（查看危险单位时，设置enterFlag＝1，操作危险单位enterFlag是空）
		if (enterFlag == null || enterFlag.equals("") || !enterFlag.equals("1")) {
			if (underwriteFlag.equals("1") || underwriteFlag.equals("2")
					|| underwriteFlag.equals("3")) {
				throw new UserException(
						-98,
						-3001,
						"WfLogQueryViewHelper.setDangerDetailToViewByDangerNo()",
						internal.getText("undwrt.service.wfLogHelper.donotPermitUpdate"));
			}
		}
		// modify end by lihua 20060420
		// 为了区分查看危险单位/操作危险单位（查看危险单位时，设置enterFlag＝1，操作危险单位enterFlag是空）
		// add end by zhaijq 20060316

		if (businessType.equals("E")) // 对批单特殊处理
		{
			dangerNo = req.getParameter("hiDangerNo");
		}
		// 危险单位主信息
		dangerDetail = wfLogService.getDangerDetail(businessType, businessNo,
				dangerNo);
		if (dangerDetail.size() == 0)// 没有在危险单位主信息表中找到数据，说明是新增的危险单位信息则获取原始标的信息
		{
			this.getItemInfoToView(req);
		} else {
			req.removeAttribute("DangerDetail");
			req.setAttribute("DangerDetail", dangerDetail);
			// 获取存在危险单位的子信息数据到页面
			this.getDangerItemToView(businessNo, dangerNo, businessType, req);
		}
		// 交费计划中的币种信息
		planCurrencyType = wfLogService.getPlanCurrencyType(businessNo,
				businessType);
		if (planCurrencyType != null) {
			req.setAttribute("planCurrencyType", planCurrencyType);
		}
		dangerExItemKind = commonDangerInfoService
				.getDangerExItemKind(riskCode);
		req.setAttribute("dangerExItemKind", dangerExItemKind);
		req.setAttribute("dangerExItemKind2", dangerExItemKind);

	}

	/**
	 * 獲取危險單位的子信息數據到頁面.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @return 危險單位的子信息
	 * @throws Exception
	 *             異常
	 */
	public void getDangerItemToView(String businessNo, String dangerNo,
			String businessType, HttpServletRequest req) throws Exception {

		Collection dangerItemList = new ArrayList();
		// 危险单位子信息
		dangerItemList = wfLogService.getDangerItemList(businessNo, dangerNo,
				businessType);
		req.removeAttribute("ItemKind");
		req.setAttribute("ItemKind", dangerItemList); // 注意和新增危险单位时的Collection同名
	}

	/**
	 * 分保意向中取每個危險單位的相關信息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param certiType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#setDangerInfoToViewByReins(java.lang.String,
	 *      java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	public void setDangerInfoToViewByReins(String certiNo, String certiType,
			HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		session.setAttribute("CertiNo", certiNo);
		session.setAttribute("CertiType", certiType);

	}

	/*
	 * //投保单信息 if(certiType.equals("T")) { PrpTmainDto prpTmainDto =
	 * uiWflogQueryAction.getPrpTmain(certiNo, certiType);
	 * session.setAttribute("PrpTmainDto", prpTmainDto); } //保单信息
	 * if(certiType.equals("P")) { PrpCmainDto prpCmainDto =
	 * uiWflogQueryAction.getPrpCmain(certiNo);
	 * session.setAttribute("PrpCmainDto", prpCmainDto); }
	 * 
	 * /*
	 * 
	 * //标的信息 if(certiType.equals("T")) {
	 * //System.out.println("--分保试算中正在获取标的信息---"); itemKindList =
	 * (ArrayList)uiWflogQueryAction.getPrpTitemKindList(certiNo);
	 * //System.out.println("---标的信息的条数"+itemKindList.size()); }
	 * if(certiType.equals("P")) { itemKindList =
	 * (ArrayList)uiWflogQueryAction.getPrpCitemKindList(certiNo); }
	 * if(certiType.equals("E")) { itemKindList =
	 * (ArrayList)uiWflogQueryAction.getPrpPitemKindList(certiNo); }
	 * session.setAttribute("ItemKind",itemKindList);
	 * session.setAttribute("ItemKind1",itemKindList);
	 * 
	 * String dangerNo = (String)req.getParameter("dangerNo"); //危险单位信息
	 * 
	 * if(dangerNo == null) //首次点击分保试算页面 { dangerList =
	 * uiWflogQueryAction.getDangerDetailList(certiNo,certiType);
	 * dangerArrayList = new ArrayList(dangerList);
	 * //System.out.println("---危险单位主信息条数为" + dangerArrayList.size());
	 * if(dangerArrayList.size() > 0) { if(certiType.equals("T")) {
	 * PrpTdangerUnitDto prpTdangerUnitDto =
	 * (PrpTdangerUnitDto)dangerArrayList.get(0);
	 * session.setAttribute("DangerDetail3",prpTdangerUnitDto); //默认显示第一个危险单位信息
	 * session.setAttribute("DangerDetail",dangerList); }
	 * if(certiType.equals("E")) { PrpPdangerUnitDto prpPdangerUnitDto =
	 * (PrpPdangerUnitDto)dangerArrayList.get(0);
	 * session.setAttribute("DangerDetail3",prpPdangerUnitDto); //默认显示第一个危险单位信息
	 * session.setAttribute("DangerDetail",dangerList); } } else {
	 * session.removeAttribute("DangerDetail3");//当查询为空时 清除上次session里的数据
	 * session.removeAttribute("DangerDetail"); }
	 * 
	 * } else { //System.out.println("正在查询第" + dangerNo + "条危险单位主信息信息");
	 * PrpTdangerUnitDto prpTdangerUnitDto =
	 * (PrpTdangerUnitDto)dangerArrayList.get(Integer.parseInt(dangerNo));
	 * session.setAttribute("DangerDetail3",prpTdangerUnitDto); } }
	 */

	/**
	 * 根據危險單位號生成分保訊息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param classCode
	 *            險類代碼
	 * @param certiType
	 *            業務類型
	 * @return "s"
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#simulateRepolicyByDangerNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String simulateRepolicyByDangerNo(String certiNo, String classCode,
			String certiType) throws Exception {
		blReinsCalService.reinsCalculate(certiNo, certiType);
		return "s";
	}

	// public String simulateRepolicyByDangerNo(String certiNo,String certiType)
	// throws Exception
	// {
	// new ReinsSimulateByDangerNoCommand(certiNo,certiType).execute();
	// return "s";
	// }

	/**
	 * 獲取放棄任務列表到頁面.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param logNo
	 *            序號
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#setCancelTaskListDtoToView(java.lang.String[],
	 *      java.lang.String[])
	 */
	public void setCancelTaskListDtoToView(String[] flowID, String[] logNo)
			throws UserException, Exception {

		// 获取提交列表
		String nodeStatus = "";
		WfLog wfLogDto = null;
		for (int i = 0; i < flowID.length; i++) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID[i].trim());
			queryRule.addEqual("id.logNo", Integer.parseInt((logNo[i]).trim()));
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			nodeStatus = wfLogDto.getNodeStatus();
			if ((nodeStatus.equals("2") || nodeStatus.equals("3"))) {

				wfLogDto.setOperatorCode("");
				wfLogDto.setOperatorName("");
				wfLogDto.setDeptCode("");
				wfLogDto.setDeptName("");
				wfLogDto.setNodeStatus("1");
				wfLogService.update(wfLogDto);
			}
		}
	}

	/**
	 * 獲取危險單位信息到頁面.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#setDangerInfoToView(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setDangerInfoToView(HttpServletRequest req) throws Exception {

		HttpSession session = req.getSession();
		String policyNo = ""; // 0623刘军加
		String strBusinessNoCI = "";// 用于取关联的交强险投保单号
		String businessNo = req.getParameter("iBusinessNo");
		String businessType = req.getParameter("iBusinessType");
		String flowID = req.getParameter("iFlowID");
		ArrayList itemKindList = new ArrayList();
		Collection dangerList = new ArrayList();
		Collection dangerExItemKind = null;
		BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
		PrpTmain prpTmainSub = new PrpTmain();
		String riskCode = "";
		String manualGradeCode = "";

		riskCode = (String) session.getAttribute("riskCode");
		if (riskCode == null) {
			riskCode = (String) req.getParameter("riskCode");
		}

		// 投保单信息
		String comCode = "";
		String comCName = ""; // 修正获取归属机构名称 add by luyang 2005-11-24

		// add by zhulei 20060326 净费比例
		String outLayRate = "";
		String rabateRate = "";
		if (businessType.equals("T")) {
			PrpTmain prpTmain = policyService
					.getPrpTmainByProposalNo(businessNo);
			// add by zhaoning20090421 begin Reason:增加分级信息的获取

			String strRelBusinessFlag = "0";// 关联业务标志
			String strPreHandleText = "";// 前次核保人意见
			String strPreGradeCode = "";// 前次分级

			strPreHandleText = uwNotionService.getPreHandleText(flowID);
			strPreGradeCode = wfGradeService.getPreGradeCode(flowID);

			PrpTgradeDto prpTgradeDto = prpallService.getPrpTgrade(businessNo,
					businessType);
			Collection colGradeGroupDetail = prpallService
					.getPrpGradeGroupDetailByBusinessNo(businessNo);

			if (colGradeGroupDetail != null) {
				if (colGradeGroupDetail.iterator().hasNext()) {
					strRelBusinessFlag = "1";
				}
			}
			// 车险联合出单时当审核商业险投保单时显示交强险信息
			if ("A".equals(prpTmain.getClassCode())
					|| "B".equals(prpTmain.getClassCode())
					&& (!"B01".equals(prpTmain.getRiskCode()))) {
				// 当是车险的业务且不是交强险时走这里，为了取关联的交强险的投保单信息
				blPrpTmainSub.getData(businessNo);
				if (blPrpTmainSub.getSize() > 0
						&& "111".equals(blPrpTmainSub.getArr(0).getFlag())) {
					// 当查询结果不为0且标志是联合出单时取出关联的交强险投保单信息
					strBusinessNoCI = blPrpTmainSub.getArr(0).getMainPolicyNo();
					prpTmainSub = policyService
							.getPrpTmainByProposalNo(strBusinessNoCI);
				}
			}
			req.setAttribute("PrpTmainSubDto", prpTmainSub);
			// add by zhangruifneg end 20071217
			req.removeAttribute("PrpCmainDto");

			req.setAttribute("PrpTmainDto", prpTmain);
			req.setAttribute("PrpTgradeDto", prpTgradeDto);
			req.setAttribute("RelBusinessFlag", strRelBusinessFlag);
			req.setAttribute("PreGradeCode", strPreGradeCode);
			req.setAttribute("PreHandleText", strPreHandleText);
			comCode = prpTmain.getComCode();
			req.setAttribute("outLayRate", outLayRate);
			// add by zhulei 20060422 费用比例 begin
			PrpTexpense prpTexpense = prpallService.getPrpTexpense(businessNo,
					businessType);
			req.setAttribute("PrpTexpenseDto", prpTexpense);
			// add by zhulei 20060422 费用比例 end

		}
		// 保单信息
		if (businessType.equals("P")) {
			BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
			blPrpCmainCovernote.getData(businessNo);
			if (blPrpCmainCovernote.getSize() > 0) {
				PrpCmainCovernoteDto prpCmainCovernoteDto = new PrpCmainCovernoteDto();
				comCode = blPrpCmainCovernote.getArr(0).getComCode();

				prpCmainCovernoteDto = prpallService
						.getPrpCmainCovernote(businessNo);
				// add by zhaoning20090421 begin Reason:增加分级信息的获取

				String strRelBusinessFlag = "0";// 关联业务标志
				String strPreHandleText = "";// 前次核保人意见
				String strPreGradeCode = "";// 前次分级

				strPreHandleText = uwNotionService.getPreHandleText(flowID);
				strPreGradeCode = wfGradeService.getPreGradeCode(flowID);

				PrpCgradeDto prpCgradeDto = prpallService
						.getPrpCgrade(businessNo);
				Collection colGradeGroupDetail = prpallService
						.getPrpGradeGroupDetailByBusinessNo(businessNo);
				if (colGradeGroupDetail != null) {
					if (colGradeGroupDetail.iterator().hasNext()) {
						strRelBusinessFlag = "1";
					}
				}
				// add by zhaoning20090421 end
				req.removeAttribute("PrpCmainCovernoteDto");
				req.removeAttribute("PrpCgradeDto");
				req.setAttribute("PrpCmainCovernoteDto", prpCmainCovernoteDto);
				req.setAttribute("PrpCgradeDto", prpCgradeDto);
				req.setAttribute("RelBusinessFlag", strRelBusinessFlag);
				req.setAttribute("PreGradeCode", strPreGradeCode);
				req.setAttribute("PreHandleText", strPreHandleText);
			} else {
				PrpCmain prpCmain = policyService
						.getPrpCmainByPolicyNo(businessNo);
				// add by zhaoning20090421 begin Reason:增加分级信息的获取
				String strRelBusinessFlag = "0";// 关联业务标志
				String strPreHandleText = "";// 前次核保人意见
				String strPreGradeCode = "";// 前次分级

				strPreHandleText = uwNotionService.getPreHandleText(flowID);
				strPreGradeCode = wfGradeService.getPreGradeCode(flowID);

				PrpCgradeDto prpCgradeDto = prpallService
						.getPrpCgrade(businessNo);
				Collection colGradeGroupDetail = prpallService
						.getPrpGradeGroupDetailByBusinessNo(businessNo);
				if (colGradeGroupDetail != null) {
					if (colGradeGroupDetail.iterator().hasNext()) {
						strRelBusinessFlag = "1";
					}
				}
				// add by zhaoning20090421 end
				req.removeAttribute("PrpTmainDto");
				req.removeAttribute("PrpTgradeDto");
				req.setAttribute("PrpCmainDto", prpCmain);
				req.setAttribute("PrpCgradeDto", prpCgradeDto);
				req.setAttribute("RelBusinessFlag", strRelBusinessFlag);
				req.setAttribute("PreGradeCode", strPreGradeCode);
				req.setAttribute("PreHandleText", strPreHandleText);
				comCode = prpCmain.getComCode();
				// add by zhulei 20060422 费用比例 begin
				PrpCexpenseDto prpCexpenseDto = prpallService.getPrpCexpense(
						businessNo, businessType);
				req.setAttribute("PrpCexpenseDto", prpCexpenseDto);
			}
			// add by zhulei 20060422 费用比例 end
		}
		// 对批单信息的处理
		if (businessType.equals("E")) {
			BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
			blPrpPmainCovernote.getData(businessNo);
			if (blPrpPmainCovernote.getSize() > 0) {
				PrpPmainCovernoteDto prpPmainCovernoteDto = prpallService
						.getPrpPmainCovernote(businessNo);
				policyNo = prpPmainCovernoteDto.getPolicyNo(); // 获取到对应的保单号

				PrpCmainCovernoteDto prpCmainCovernoteDto = prpallService
						.getPrpCmainCovernote(policyNo);
				req.setAttribute("PrpCmainCovernoteDto", prpCmainCovernoteDto);
				// add by zhaoning20090421 begin Reason:增加分级信息的获取

				String strRelBusinessFlag = "0";// 关联业务标志
				String strPreHandleText = "";// 前次核保人意见
				String strPreGradeCode = "";// 前次分级

				strPreHandleText = uwNotionService.getPreHandleText(flowID);
				strPreGradeCode = wfGradeService.getPreGradeCode(flowID);

				PrpCPmainCovernoteDto prpCPmainCovernoteDto = prpallService
						.getPrpCPmainCovernote(policyNo);
				PrpCPgradeDto prpCPgradeDto = prpallService
						.getPrpCPgrade(policyNo);
				Collection colGradeGroupDetail = prpallService
						.getPrpGradeGroupDetailByBusinessNo(businessNo);

				if (colGradeGroupDetail != null) {
					if (colGradeGroupDetail.iterator().hasNext()) {
						strRelBusinessFlag = "1";
					}
				}
				req.setAttribute("PreGradeCode", strPreGradeCode);
				req.setAttribute("PreHandleText", strPreHandleText);
				req.setAttribute("RelBusinessFlag", strRelBusinessFlag);
				req.setAttribute("PrpCPmainCovernoteDto", prpCPmainCovernoteDto);
				req.setAttribute("PrpCPgradeDto", prpCPgradeDto);
				// add by zhaoning20090421 end
				comCode = prpCmainCovernoteDto.getComCode();
				PrpPheadCovernoteDto prpPheadCovernoteDto = prpallService
						.getPrpPheadCovernote(businessNo);
				req.setAttribute("PrpPheadCovernoteDto", prpPheadCovernoteDto);
				// add by yanglibo 20100113 begin reason: TASK-2770
				PrpCgradeDto prpCgradeDto = prpallService
						.getPrpCgrade(policyNo);
				if (!(prpCgradeDto == null || prpCgradeDto.equals(""))) {
					manualGradeCode = prpCgradeDto.getManualGradeCode();
				}
				req.setAttribute("ManualGradeCode", manualGradeCode);
				// add by yanglibo 20100113 end reason:TASK-2770
			} else {
				PrpPmain prpPmain = prpallService.getPrpPmain(businessNo);
				policyNo = prpPmain.getPolicyNo(); // 获取到对应的保单号
				// add by zhaoning20090421 begin Reason:增加分级信息的获取
				String strProposalNo = "";// 投保单号

				String strRelBusinessFlag = "0";// 关联业务标志
				String strPreHandleText = "";// 前次核保人意见
				String strPreGradeCode = "";// 前次分级

				strPreHandleText = uwNotionService.getPreHandleText(flowID);
				strPreGradeCode = wfGradeService.getPreGradeCode(flowID);

				strProposalNo = prpPmain.getProposalNo();
				PrpCPmain prpCPmain = prpallService.getPrpCPmain(policyNo);
				PrpCPgradeDto prpCPgradeDto = prpallService
						.getPrpCPgrade(policyNo);
				Collection colGradeGroupDetail = prpallService
						.getPrpGradeGroupDetailByBusinessNo(strProposalNo);

				if (colGradeGroupDetail != null) {
					if (colGradeGroupDetail.iterator().hasNext()) {
						strRelBusinessFlag = "1";
					}
				}
				req.setAttribute("PreGradeCode", strPreGradeCode);
				req.setAttribute("PreHandleText", strPreHandleText);
				req.setAttribute("RelBusinessFlag", strRelBusinessFlag);
				req.setAttribute("PrpCPmainDto", prpCPmain);
				req.setAttribute("PrpCPgradeDto", prpCPgradeDto);
				// add by zhaoning20090421 end

				PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
				   //modify by wangjun 责任险批单显示最新信息 20150320 begin
				if("C".equals(prpCmain.getClassCode()))
					{
						PrpCmain prpCmain_new = new PrpCmain();
						EvaluateUtil.simpleCopyFromCPToPolicy(prpCPmain,prpCmain_new);
						req.setAttribute("PrpCmainDto", prpCmain_new);
					}
				else
					{
						req.setAttribute("PrpCmainDto", prpCmain);
					}
				//modify by wangjun 责任险批单显示最新信息 20150320 end
				comCode = prpCmain.getComCode();

				// add by yanglibo 20100113 begin reason: TASK-2770
				PrpCgradeDto prpCgradeDto = prpallService
						.getPrpCgrade(policyNo);
				if (!(prpCgradeDto == null || prpCgradeDto.equals(""))) {
					manualGradeCode = prpCgradeDto.getManualGradeCode();
				}
				req.setAttribute("ManualGradeCode", manualGradeCode);
				// add by yanglibo 20100113 end reason:TASK-2770

				// add by zhulei 20060422 费用比例 begin
				PrpCPexpenseDto prpCPexpenseDto = prpallService
						.getPrpCPexpense(prpCmain.getPolicyNo(), businessType);
				req.setAttribute("PrpCPexpenseDto", prpCPexpenseDto);
				PrpPhead prpPhead = endorseService
						.getPrpPheadByEndorseNo(businessNo);
				req.setAttribute("PrpPheadDto", prpPhead);
				// add by zhulei 20060422 费用比例 end
			}
		}
		if (businessType.equals("C"))// 0623刘军加
		{
			PrpLcompensateDto prpLcompensateDto = prpallService
					.getPrpLcompensate(businessNo);
			policyNo = prpLcompensateDto.getPolicyNo();
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
			req.setAttribute("PrpCmainDto", prpCmain);
			prpCmain.getComCode();
		}
		if (businessType.equals("Y"))// 20060824--modify by xuning 预赔
		{
			// System.out.println("--1-businessType==="+businessType);
			PrpLprepayDto prpLprepayDto = prpallService
					.getPrpLprepay(businessNo);
			policyNo = prpLprepayDto.getPolicyNo();
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(policyNo);
			req.setAttribute("PrpCmainDto", prpCmain);
			prpCmain.getComCode();
			// System.out.println("--2-businessType==="+businessType);
		}
		String handType = (String) session.getAttribute("handType");
		if (handType != null && handType.equals("22")) {
			PolicyAbstractInfoVo policyAbstractInfoDto = commonCheckTaskService
					.getPolicyAbstractInfo(businessNo);
			req.setAttribute("PolicyAbstractInfoDto", policyAbstractInfoDto);
			// modify by qinyongli 查询所有费用信息
			ArrayList prplchargeList = new ArrayList();
			BLPrpLchargeFacade blPrpLchargeFacade = new BLPrpLchargeFacade();
			String conditions = "compensateno= +'" + businessNo + "' ";
			prplchargeList = (ArrayList) blPrpLchargeFacade
					.findByConditions(conditions);
			req.setAttribute("prplchargeList", prplchargeList);
		}
		PrpDcompany prpDcompanyDto = prpDcompanyService
				.findByPrimaryKey(comCode);
		// 修正获取归属机构名称 add by luyang 2005-11-24
		if (prpDcompanyDto != null) {
			comCName = prpDcompanyDto.getComCName();
			req.setAttribute("comCName", comCName);
		}

		// 标的信息
		String bizNo = "";
		bizNo = businessNo;
		if (businessType.equals("C"))
			bizNo = policyNo;
		itemKindList = (ArrayList) prpallService.getCustomItemKindList(businessType, bizNo, riskCode);
		req.setAttribute("ItemKind", itemKindList);
		req.setAttribute("ItemKind1", itemKindList);

		// 危险单位信息
		dangerList = prpallService
				.getDangerDetailList(businessNo, businessType);

		req.setAttribute("DangerDetail", dangerList);

		BLPrpPmainCovernote blPrpPmainCovernote = new BLPrpPmainCovernote();
		blPrpPmainCovernote.getData(businessNo);
		if (blPrpPmainCovernote.getSize() == 0) {
			if ((!businessType.equals("C")) && (!businessType.equals("Y"))) // 核赔/预赔的危险单位暂时不处理
			{
				CommonAmountAndPremiumVo commonAmountAndPremiumDto = prpallService
						.getAmountAndPremium(businessNo, businessType);
				req.setAttribute("AmountAndPremiumDto",
						commonAmountAndPremiumDto);
			}
		}
		// 该险种的除外标的类型
		dangerExItemKind = commonDangerInfoService
				.getDangerExItemKind(riskCode);// 临时注掉的，报异常
		req.setAttribute("dangerExItemKind", dangerExItemKind);
		req.setAttribute("dangerExItemKind2", dangerExItemKind);
	}

	/**
	 * 查詢該保單是續保業務則查詢續保保單是否存在 存在立案.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 存在返回true,不存在返回false
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService#isExistPrplregis(java.lang.String)
	 */
	public boolean isExistPrplregis(String policyNo) throws Exception {
		String getCount_sql = " select * from prplregist r, prplclaim c "
				+ " where r.registno = c.registno(+) "
				+ " and (r.canceldate = '' or r.canceldate is null) "
				+ "  and r.policyno = '" + policyNo + "' ";
		boolean isExist = false;
		List list = null;
		try {
			list = super.getSession().createSQLQuery(getCount_sql).list();
			if (list != null && list.size() > 0) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return isExist;
	}
	/**
	 *查看與與該要保書要保人、被保險人、被保險財產坐落地址之一相同的資料庫中近五年保單的已決與未決的理賠記錄.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return Page 對象
	 * @throws Exception
	 *             異常
	 */
	public List<ClaimInfoVo> similarClaimsInfo(String businessNo, int pageNo, int pageSize) throws Exception{
		
		PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
		
		DateTime startDate = new DateTime(prpTmain.getStartDate(),DateTime.YEAR_TO_DAY);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int year = startDate.getYear()-5;
		calendar.set(Calendar.YEAR,year);
		DateTime tempDate = new DateTime(calendar.getTime());
		
		String strSql = //"SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM ( "+
				"select r.policyno," +
				"r.registno," +
				"c.claimno," +
				"to_char(r.damagestartdate, 'yyyy-mm-dd') damagestartdate," +
				"r.damagename," +
				"c.endcasedate," +
				"u.username," +
				"sum(decode(c.endcasedate,null,c.sumclaim - decode(s.underwriteflag, '1', s.sumpaid, '3', s.sumpaid, 0),0)) as outstanding," +
				"sum(decode(s.underwriteflag, '1', s.sumpaid, '3', s.sumpaid, 0)) as sumpaid " +
				"from prplregist r, prplclaim c, prplcompensate s, prpduser u " +
				"where r.registno = c.registno(+)                                        " +
				"and (r.canceldate = '' or r.canceldate is null)                         " +
				"and c.claimno = s.claimno(+)                                            " +
				"and c.policyno = s.policyno(+)                                          " +
				"and c.handlercode = u.usercode(+)                                       " +
				"and r.policyno in                                                       " +
			    "   (select *                                                            " +
			    "      from (SELECT distinct cm.policyno                                 " +
			    "              FROM prpcinsured ci, prpcmain cm                          " +
			    "             where ci.policyno = cm.policyno                            " +
			    "               and cm.classCode = '" + prpTmain.getClassCode() + "'                                  " +
			    "               and ci.identifytype in (select distinct identifytype from prptinsured where proposalno='" + businessNo + "')                               " +
			    "               and ci.identifynumber in (select distinct identifynumber from prptinsured where proposalno='" + businessNo + "')                     " +
			    "               and cm.startdate > '" + tempDate + "')                           " +
			    "    union                                                               " +
			    "    select *                                                            " +
			    "      from (SELECT distinct cm.policyno                                 " +
			    "              FROM prpcaddress ca, prpcmain cm                          " +
			    "             where ca.policyno = cm.policyno                            " +
			    "               and cm.classCode = '" + prpTmain.getClassCode() + "'                                  " +
			    "               and ca.addresscode in (select distinct addresscode from prptaddress where proposalno='" + businessNo + "')                              " +
			    "               and ca.addressdetailinfo in (select distinct addressdetailinfo from prptaddress where proposalno='" + businessNo + "')           " +
			    "               and cm.startdate > '" + tempDate + "'))                          " +
			 	"group by r.policyno,                                                    " +
			    "      r.registno,                                                       " +
			    "      c.claimno,                                                        " +
			    "      r.damagestartdate,                                                " +
			    "      u.username,                                                       " +
			    "      r.damagename,                                                     " +
			    "      c.endcasedate                                                     " +
			    "order by damagestartdate desc                                           ";
			    //") row_ WHERE rownum <="+pageSize*pageNo+") WHERE rownum_ >"+pageSize*(pageNo-1);
		
		
		List<ClaimInfoVo> claimInfoVoList = new ArrayList<ClaimInfoVo>();
		List list = this.getSession().createSQLQuery(strSql).list();
		Iterator<Object[]> it = list.iterator();
		while (it.hasNext()) {
			String insuredName1 = "",insuredName2 = "",itemAddress = "",kindName = "";
			ClaimInfoVo claimInfoVo = new ClaimInfoVo();
			Object[] row = it.next();
			claimInfoVo.setPolicyNo(String.valueOf(row[0]));
			PrpCmain prpCmain = policyService.getPrpCmainByPolicyNo(String.valueOf(row[0]));
			for(int i=0;i<prpCmain.getPrpCinsureds().size();i++){
				if("1".equals(prpCmain.getPrpCinsureds().get(i).getInsuredFlag())){
					insuredName1 += prpCmain.getPrpCinsureds().get(i).getInsuredName() + "	";
				}else if("2".equals(prpCmain.getPrpCinsureds().get(i).getInsuredFlag())){
					insuredName2 += prpCmain.getPrpCinsureds().get(i).getInsuredName() + "	";
				}
			}
			claimInfoVo.setInsuredName1(insuredName1);
			claimInfoVo.setInsuredName2(insuredName2);
			
			for(int i=0;i<prpCmain.getPrpCaddresses().size();i++){
				itemAddress += prpCmain.getPrpCaddresses().get(i).getAddressDetailInfo() + "	";
			}
			claimInfoVo.setItemAddress(itemAddress);
			
			for(int i=0;i<prpCmain.getPrpCitemKinds().size();i++){
				kindName += prpCmain.getPrpCitemKinds().get(i).getKindName() + "	";
			}
			claimInfoVo.setRiskName(kindName);
			claimInfoVo.setDamagestartdate((String) row[3]);
			claimInfoVo.setDamagename((String) row[4]);
			claimInfoVo.setOutstanding(String.valueOf(row[7]));
			claimInfoVo.setSumpaid(String.valueOf(row[8]));
			claimInfoVoList.add(claimInfoVo);
		}
		return claimInfoVoList; 
	}
	/**
	 * 查詢被保險人近三年是否有賠案.
	 * 
	 * @param insuredCode
	 *            被保險人代碼
	 * @return 存在返回true,不存在返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean isExistClaims(String insuredCode) throws Exception{
		String getCount_sql = " select * from prplcompensate p, prplclaim c "
				+ " where p.claimno = c.claimno(+) "
				+ " and (p.underwriteflag != '3') "
				+ "  and c.insuredcode = '" + insuredCode + "' ";
		boolean isExist = false;
		List list = null;
		try {
			list = super.getSession().createSQLQuery(getCount_sql).list();
			if (list != null && list.size() > 0) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return isExist;
	}
	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	/**
	 * 獲取屬性危險單位信息服務接口.
	 * 
	 * @return 屬性危險單位信息服務接口的值
	 */
	public CommonDangerInfoService getCommonDangerInfoService() {
		return commonDangerInfoService;
	}

	/**
	 * 設置屬性危險單位信息服務接口.
	 * 
	 * @param commonDangerInfoService
	 *            待設置的危險單位信息服務接口的值
	 */
	public void setCommonDangerInfoService(
			CommonDangerInfoService commonDangerInfoService) {
		this.commonDangerInfoService = commonDangerInfoService;
	}

	/**
	 * 獲取屬性核保系統查詢接口.
	 * 
	 * @return 屬性核保系統查詢接口的值
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置屬性核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口的值
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取屬性核保服務接口.
	 * 
	 * @return 屬性核保服務接口的值
	 */
	public CommonCheckTaskService getCommonCheckTaskService() {
		return commonCheckTaskService;
	}

	/**
	 * 設置屬性核保服務接口.
	 * 
	 * @param commonCheckTaskService
	 *            待設置的核保服務接口的值
	 */
	public void setCommonCheckTaskService(
			CommonCheckTaskService commonCheckTaskService) {
		this.commonCheckTaskService = commonCheckTaskService;
	}

	/**
	 * 獲取屬性定級信息接口.
	 * 
	 * @return 屬性定級信息接口的值
	 */
	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	/**
	 * 設置屬性定級信息接口.
	 * 
	 * @param wfGradeService
	 *            待設置的定級信息接口的值
	 */
	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	/**
	 * 獲取屬性核保處理意見接口.
	 * 
	 * @return 屬性核保處理意見接口的值
	 */
	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	/**
	 * 設置屬性核保處理意見接口.
	 * 
	 * @param uwNotionService
	 *            待設置的核保處理意見接口的值
	 */
	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
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
	 * 獲取屬性再保分保試算接口.
	 * 
	 * @return 屬性再保分保試算接口的值
	 */
	public BLReinsCalService getBlReinsCalService() {
		return blReinsCalService;
	}

	/**
	 * 設置屬性再保分保試算接口.
	 * 
	 * @param blReinsCalService
	 *            待設置的再保分保試算接口的值
	 */
	public void setBlReinsCalService(BLReinsCalService blReinsCalService) {
		this.blReinsCalService = blReinsCalService;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

}
