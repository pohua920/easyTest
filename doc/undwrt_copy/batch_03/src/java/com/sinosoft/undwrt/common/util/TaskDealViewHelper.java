package com.sinosoft.undwrt.common.util;

import ins.framework.common.QueryRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.platform.bl.facade.BLUtiUwLevelFacade;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.UtiUwLevelDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.vo.WfLogVo;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;

/**
 * 任務處理幫助類.
 */
public class TaskDealViewHelper {

	/** 核保級別設定接口類 */
	private UtiUwLevelService utiUwLevelService;

	/**
	 * 構造方法.
	 */
	public TaskDealViewHelper() {
	}

	/**
	 * 組織查詢條件.
	 * 
	 * @param userCode
	 *            用戶代碼
	 * @param nodeStatusView
	 *            節點狀態
	 * @return 查詢條件
	 */
	private String getPermissionControlStatement(String userCode, boolean nodeStatusView) {
		String statement = null;
		if (nodeStatusView) {

			statement = " SELECT DISTINCT view_wflogall.* FROM UwGroup ,UwGrade,view_wflogall " + " WHERE view_wflogall.ModelNo = UwGrade.ModelNo"
					+ " AND UwGrade.UserCode = '" + userCode + "'" + " AND UwGrade.GroupNo = UwGroup.GroupNo"
					+ " AND view_wflogall.RiskCode = UwGroup.RiskCode" + " AND view_wflogall.ComCode = UwGroup.ComCode"
					+ " AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 ";

		} else {

			statement = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog " + " WHERE WfLog.ModelNo = UwGrade.ModelNo" + " AND UwGrade.UserCode = '"
					+ userCode + "'" + " AND UwGrade.GroupNo = UwGroup.GroupNo" + " AND Wflog.RiskCode = UwGroup.RiskCode"
					+ " AND Wflog.ComCode = UwGroup.ComCode" + " AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 ";
		}
		/*
		 * <<<新权限的核保任务查询>>>
		 * //根据当前人员UserCode从UtiUwLevel表查询所能操作的部门ComCode、险种RiskCode
		 * 、级别(ModelNo+NodeNo). //LogNo/NodeNo等于1的节点是出单员节点，不允许被查出。 statement =
		 * "SELECT DISTINCT WfLog.* FROM UtiUwLevel,WfLog WHERE " +
		 * "UtiUwLevel.UserCode='" + userCode + "' AND " +
		 * "WfLog.ComCode=UtiUwLevel.ComCode AND " +
		 * "WfLog.RiskCode=UtiUwLevel.RiskCode AND " +
		 * "WfLog.ModelNo=UtiUwLevel.ModelNo AND " +
		 * "WfLog.NodeNo=UtiUwLevel.NodeNo AND " +
		 * "WfLog.LogNo<>1 AND WfLog.NodeNo<>1 ";
		 */
		return statement;
	}

	/**
	 * 組織查詢條件.
	 * 
	 * @param prpDuser
	 *            用戶訊息
	 * @param nodeStatusView
	 *            節點狀態
	 * @return 查詢條件
	 * @throws Exception
	 *             異常
	 */
	private String getControlStatement(PrpDuserDto prpDuser, boolean nodeStatusView) throws Exception {
		String statement = null;
		if (nodeStatusView) {

			statement = " SELECT DISTINCT view_wflogall.* FROM UwGroup ,UwGrade,view_wflogall " + " WHERE view_wflogall.ModelNo = UwGrade.ModelNo"
					+ " AND UwGrade.UserCode = '" + prpDuser.getUserCode() + "'" + " AND UwGrade.GroupNo = UwGroup.GroupNo"
					+ " AND view_wflogall.RiskCode = UwGroup.RiskCode" + " AND view_wflogall.ComCode = UwGroup.ComCode"
					+ " AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 ";

		} else {

			statement = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog " + " WHERE WfLog.ModelNo = UwGrade.ModelNo" + " AND UwGrade.UserCode = '"
					+ prpDuser.getUserCode() + "'" + " AND UwGrade.GroupNo = UwGroup.GroupNo" + " AND Wflog.RiskCode = UwGroup.RiskCode"
					+ " AND Wflog.ComCode = UwGroup.ComCode" + " AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 ";
		}
		/*
		 * <<<新权限的核保任务查询>>>
		 * //根据当前人员UserCode从UtiUwLevel表查询所能操作的部门ComCode、险种RiskCode
		 * 、级别(ModelNo+NodeNo). //LogNo/NodeNo等于1的节点是出单员节点，不允许被查出。 statement =
		 * "SELECT DISTINCT WfLog.* FROM UtiUwLevel,WfLog WHERE " +
		 * "UtiUwLevel.UserCode='" + userCode + "' AND " +
		 * "WfLog.ComCode=UtiUwLevel.ComCode AND " +
		 * "WfLog.RiskCode=UtiUwLevel.RiskCode AND " +
		 * "WfLog.ModelNo=UtiUwLevel.ModelNo AND " +
		 * "WfLog.NodeNo=UtiUwLevel.NodeNo AND " +
		 * "WfLog.LogNo<>1 AND WfLog.NodeNo<>1 ";
		 */
		return statement;
	}

	/**
	 * 組織核保審核處理查詢條件.
	 * 
	 * @param prpDuser
	 *            用戶訊息
	 * @param nodeStatusView
	 *            節點狀態
	 * @param lowFlag
	 *            是否包含下級
	 * @return 核保審核處理查詢條件
	 * @throws Exception
	 *             異常
	 */
	private String getControlStatementHebao(PrpDuserDto prpDuser, boolean nodeStatusView, boolean lowFlag) throws Exception {
		String statement = null;
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		String strCondition = null;
		if (nodeStatusView) {

			strCondition = blUtiUwLevelFacade.addPower("", prpDuser.getUserCode(), "view_wflogall", "COMCODE", "T", lowFlag);
			statement = " SELECT DISTINCT view_wflogall.* FROM view_wflogall WHERE (" + strCondition
					+ ") AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 ";
			/*
			 * statement =
			 * " SELECT DISTINCT view_wflogall.* FROM UwGroup ,UwGrade,view_wflogall "
			 * + " WHERE view_wflogall.ModelNo = UwGrade.ModelNo" +
			 * " AND UwGrade.UserCode = '" + prpDuserDto.getUserCode() + "'" +
			 * " AND UwGrade.GroupNo = UwGroup.GroupNo" +
			 * " AND view_wflogall.RiskCode = UwGroup.RiskCode" +
			 * " AND view_wflogall.ComCode = UwGroup.ComCode" +
			 * " AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 ";
			 */

		} else {
			strCondition = blUtiUwLevelFacade.addPower("", prpDuser.getUserCode(), "Wflog", "COMCODE", "T", lowFlag);
			statement = " SELECT DISTINCT Wflog.* FROM Wflog WHERE (" + strCondition + ") AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 ";
		}
		/*
		 * <<<新权限的核保任务查询>>>
		 * //根据当前人员UserCode从UtiUwLevel表查询所能操作的部门ComCode、险种RiskCode
		 * 、级别(ModelNo+NodeNo). //LogNo/NodeNo等于1的节点是出单员节点，不允许被查出。 statement =
		 * "SELECT DISTINCT WfLog.* FROM UtiUwLevel,WfLog WHERE " +
		 * "UtiUwLevel.UserCode='" + userCode + "' AND " +
		 * "WfLog.ComCode=UtiUwLevel.ComCode AND " +
		 * "WfLog.RiskCode=UtiUwLevel.RiskCode AND " +
		 * "WfLog.ModelNo=UtiUwLevel.ModelNo AND " +
		 * "WfLog.NodeNo=UtiUwLevel.NodeNo AND " +
		 * "WfLog.LogNo<>1 AND WfLog.NodeNo<>1 ";
		 */
		return statement;
	}

	/**
	 * 組織查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 */
	private String getQueryConditionStatement(HttpServletRequest req, boolean nodeStatusView) {
		String riskCategoryTag = "=";
		String riskCategoryVal = StringUtils.trimToEmpty(req.getParameter("riskCategory"));
		if("ALL".equals(riskCategoryVal)){
			riskCategoryVal = "";
		}
		String riskCodeTag = "=";
		String[] riskCodeVal = req.getParameterValues("riskCode");
		String businessNoTag = req.getParameter("businessNoTag");
		String businessNoVal = StringUtils.trimToEmpty(req.getParameter("businessNo"));
//		String singleMemberTag = StringUtils.trimToEmpty(req.getParameter("singleMemberTag"));//add by wangcan 2015/11/26
//		String singleMemberVal = StringUtils.trimToEmpty(req.getParameter("singleMember"));//add by wangcan 2015/11/26
		String singleCodeTag = StringUtils.trimToEmpty(req.getParameter("singleCodeTag"));//add by wangcan 2015/12/16
		String singleCodeVal = StringUtils.trimToEmpty(req.getParameter("singleCode"));//add by wangcan 2015/12/16
		String contractNoTag = req.getParameter("contractNoTag");
		String contractNoVal = StringUtils.trimToEmpty(req.getParameter("contractNo"));
		String comCodeTag = req.getParameter("comCodeTag");
		String comCodeVal = StringUtils.trimToEmpty(req.getParameter("comCode"));
		String nodeStatusTag = "=";
		String[] nodeStatusVal = req.getParameterValues("nodeStatus");
		String flowInTime1Tag = ">=";
		String flowInTime1Val = StringUtils.trimToEmpty(req.getParameter("flowInTime1"));
		String flowInTime2Tag = "<=";
		String flowInTime2Val = StringUtils.trimToEmpty(req.getParameter("flowInTime2"));//
		if (flowInTime1Val.length() > 0) {
			flowInTime1Val = flowInTime1Val + " 00:00:00";
		}
		if (flowInTime2Val.length() > 0) {
			flowInTime2Val = flowInTime2Val + " 23:59:59";
		}
		String licenseNoTag = req.getParameter("licenseNoTag");
		String licenseNoVal = StringUtils.trimToEmpty(req.getParameter("licenseNo"));
		String identifyTypeTag = "=";
		String identifyTypeVal = req.getParameter("identifyType");
		String identifyNumberTag = req.getParameter("identifyNumberTag");
		String identifyNumberVal = StringUtils.trimToEmpty(req.getParameter("identifyNumber"));
		String relateContractNoYesNoTag = "=";
		String[] relateContractNoYesNoVal = req.getParameterValues("relateContractNoYesNo");
		String relateContractNoTag = req.getParameter("relateContractNoTag");
		String relateContractNoVal = StringUtils.trimToEmpty(req.getParameter("relateContractNo"));
		String policyNoTag = StringUtils.trimToEmpty(req.getParameter("policyNoTag"));
		String policyNo = StringUtils.trimToEmpty(req.getParameter("policyNo"));

		// add by fushixing 20110729 start
		String policynoTag = StringUtils.trimToEmpty(req.getParameter("policynoTag"));
		String policyno = StringUtils.trimToEmpty(req.getParameter("policyno"));
		// add by fushixing 20110729 end

		String claimNoTag = StringUtils.trimToEmpty(req.getParameter("claimNoTag"));
		String claimNo = StringUtils.trimToEmpty(req.getParameter("claimNo"));

		String statement = "";
		if (nodeStatusView) {

			/*
			 * if(underling.equals("Y")) { statement +=
			 * " AND view_wflogall.NodeNo <= UwGrade.NodeNo "; } else
			 * if(underling.equals("N")) { statement +=
			 * " AND view_wflogall.NodeNo = UwGrade.NodeNo "; }
			 */
			QueryAction uiQueryAction = new QueryAction();

			statement += uiQueryAction.getCharConditions("view_wflogall.RiskCategory", riskCategoryTag, riskCategoryVal);
			statement += uiQueryAction.getCharInConditions("view_wflogall.RiskCode", riskCodeVal);
			statement += uiQueryAction.getCharConditions("view_wflogall.BusinessNo", businessNoTag, businessNoVal);
			statement += uiQueryAction.getCharConditions("view_wflogall.singleCode", singleCodeTag, singleCodeVal);

			// 2011-07-28 add by shixing.fu start
			if (!policyno.equals("")) {
				if (businessNoVal.equals("")) {
					if (policynoTag.equals("=")) {
						statement += "AND (view_wflogall.BusinessNo in (select proposalno from prpcmain where policyno = '" + policyno + "')"
								+ "OR view_wflogall.BusinessNo in (select endorseno from prpphead where policyno = '" + policyno + "'))";
					} else {
						statement += "AND (view_wflogall.BusinessNo in (select proposalno from prpcmain where policyno like '%" + policyno + "%')"
								+ "OR view_wflogall.BusinessNo in (select endorseno from prpphead where policyno like '%" + policyno + "%'))";
					}
				}
			}
			// 2011-07-28 add by shixing.fu end

			statement += uiQueryAction.getCharConditions("view_wflogall.ContractNo", contractNoTag, contractNoVal);
			statement += uiQueryAction.getCharConditions("view_wflogall.ComCode", comCodeTag, comCodeVal);
			statement += uiQueryAction.getCharInConditions("view_wflogall.NodeStatus", nodeStatusVal);
			statement += uiQueryAction.getCharConditions("view_wflogall.FlowInTime", flowInTime1Tag, flowInTime1Val);
			statement += uiQueryAction.getCharConditions("view_wflogall.FlowInTime", flowInTime2Tag, flowInTime2Val);
			statement += uiQueryAction.getCharConditions("view_wflogall.PolicyNo", policyNoTag, policyNo);
			statement += uiQueryAction.getCharConditions("view_wflogall.ClaimNo", claimNoTag, claimNo);
			if (riskCategoryVal.equals("D"))// 车险
			{
				statement += uiQueryAction.getCharConditions("view_wflogall.LicenseNo", licenseNoTag, licenseNoVal);
			} else if (riskCategoryVal.equals("4"))// 意健
			{
				statement += uiQueryAction.getCharConditions("view_wflogall.IdentifyType", identifyTypeTag, identifyTypeVal);
				statement += uiQueryAction.getCharConditions("view_wflogall.IdentifyNumber", identifyNumberTag, identifyNumberVal);
			} else if (riskCategoryVal.equals("2"))// 水险（货运险）
			{
				if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 1) {
					if (relateContractNoYesNoVal[0].equals("Yes")) {
						if (relateContractNoVal.length() > 0) {
							statement += uiQueryAction.getCharConditions("view_wflogall.RelateContractNo", relateContractNoTag, relateContractNoVal);
						} else {
							statement += " AND view_wflogall.RelateContractNo is not null";
						}
					} else {
						statement += " AND view_wflogall.RelateContractNo is null";
					}
				} else if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 2) {
					if (relateContractNoVal.length() > 0) {
						statement += uiQueryAction.getCharConditions("view_wflogall.RelateContractNo", relateContractNoTag, relateContractNoVal);
					}
				}
			}

		} else {
			/*
			 * if(underling.equals("Y")) { statement +=
			 * " AND WfLog.NodeNo <= UwGrade.NodeNo "; } else
			 * if(underling.equals("N")) { statement +=
			 * " AND WfLog.NodeNo = UwGrade.NodeNo "; }
			 */
			QueryAction uiQueryAction = new QueryAction();

			statement += uiQueryAction.getCharConditions("Wflog.RiskCategory", riskCategoryTag, riskCategoryVal);
			statement += uiQueryAction.getCharInConditions("Wflog.RiskCode", riskCodeVal);
			statement += uiQueryAction.getCharConditions("Wflog.BusinessNo", businessNoTag, businessNoVal);
			statement += uiQueryAction.getCharConditions("Wflog.singleCode", singleCodeTag, singleCodeVal);//add by wangcan 2015/11/26
			// 2011-08-02 add by shixing.fu start
			if (!policyno.equals("")) {
				if (businessNoVal.equals("")) {
					if (policynoTag.equals("=")) {
						statement += "AND (Wflog.BusinessNo in (select proposalno from prpcmain where policyno = '" + policyno + "')"
								+ "OR Wflog.BusinessNo in (select endorseno from prpphead where policyno = '" + policyno + "'))";
					} else {
						statement += "AND (Wflog.BusinessNo in (select proposalno from prpcmain where policyno like '%" + policyno + "%')"
								+ "OR Wflog.BusinessNo in (select endorseno from prpphead where policyno like '%" + policyno + "%'))";
					}
				}
			}
			// 2011-08-02 add by shixing.fu end
			statement += uiQueryAction.getCharConditions("Wflog.ContractNo", contractNoTag, contractNoVal);
			statement += uiQueryAction.getCharConditions("Wflog.ComCode", comCodeTag, comCodeVal);
			statement += uiQueryAction.getCharInConditions("Wflog.NodeStatus", nodeStatusVal);
			statement += uiQueryAction.getCharConditions("Wflog.FlowInTime", flowInTime1Tag, flowInTime1Val);
			statement += uiQueryAction.getCharConditions("Wflog.FlowInTime", flowInTime2Tag, flowInTime2Val);
			statement += uiQueryAction.getCharConditions("Wflog.PolicyNo", policyNoTag, policyNo);
			statement += uiQueryAction.getCharConditions("Wflog.ClaimNo", claimNoTag, claimNo);

			if (riskCategoryVal.equals("D"))// 车险
			{
				statement += uiQueryAction.getCharConditions("Wflog.LicenseNo", licenseNoTag, licenseNoVal);
			} else if (riskCategoryVal.equals("4"))// 意健
			{
				statement += uiQueryAction.getCharConditions("Wflog.IdentifyType", identifyTypeTag, identifyTypeVal);
				statement += uiQueryAction.getCharConditions("Wflog.IdentifyNumber", identifyNumberTag, identifyNumberVal);
			} else if (riskCategoryVal.equals("2"))// 水险（货运险）
			{
				if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 1) {
					if (relateContractNoYesNoVal[0].equals("Yes")) {
						if (relateContractNoVal.length() > 0) {
							statement += uiQueryAction.getCharConditions("Wflog.RelateContractNo", relateContractNoTag, relateContractNoVal);
						} else {
							statement += " AND Wflog.RelateContractNo is not null";
						}
					} else {
						statement += " AND Wflog.RelateContractNo is null";
					}
				} else if (relateContractNoYesNoVal != null && relateContractNoYesNoVal.length == 2) {
					if (relateContractNoVal.length() > 0) {
						statement += uiQueryAction.getCharConditions("Wflog.RelateContractNo", relateContractNoTag, relateContractNoVal);
					}
				}
			}
		}
		return statement;
	}

	/**
	 * 組織查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 */
	private String getQueryQtaConditionStatement(HttpServletRequest req, boolean nodeStatusView) {
		String selectUnderling = StringUtils.trimToEmpty(req.getParameter("selectUnderling"));
		String strEditType = StringUtils.trimToEmpty(req.getParameter("EditType"));
		String riskCategoryTag = "=";
		String riskCategoryVal = StringUtils.trimToEmpty(req.getParameter("riskCategory"));
		String riskCodeTag = "=";
		String[] riskCodeVal = req.getParameterValues("riskCode");
		String businessNoTag = req.getParameter("businessNoTag");
		String businessNoVal = StringUtils.trimToEmpty(req.getParameter("businessNo"));
		String contractNoTag = req.getParameter("contractNoTag");
		String contractNoVal = StringUtils.trimToEmpty(req.getParameter("contractNo"));
		String comCodeTag = req.getParameter("comCodeTag");
		String comCodeVal = StringUtils.trimToEmpty(req.getParameter("comCode"));
		String nodeStatusTag = "=";
		String[] nodeStatusVal = req.getParameterValues("nodeStatus");
		String flowInTime1Tag = ">=";
		String flowInTime1Val = StringUtils.trimToEmpty(req.getParameter("flowInTime1"));
		String flowInTime2Tag = "<=";
		String flowInTime2Val = StringUtils.trimToEmpty(req.getParameter("flowInTime2"));
		// if(flowInTime1Val.length() > 0)
		// {
		// flowInTime1Val = flowInTime1Val + " 00:00:00";
		// }
		// if(flowInTime2Val.length() > 0)
		// {
		// flowInTime2Val = flowInTime2Val + " 23:59:59";
		// }
		String licenseNoTag = req.getParameter("licenseNoTag");
		String licenseNoVal = StringUtils.trimToEmpty(req.getParameter("licenseNo"));
		String identifyTypeTag = "=";
		String identifyTypeVal = req.getParameter("identifyType");
		String identifyNumberTag = req.getParameter("identifyNumberTag");
		String identifyNumberVal = StringUtils.trimToEmpty(req.getParameter("identifyNumber"));
		String relateContractNoYesNoTag = "=";
		String[] relateContractNoYesNoVal = req.getParameterValues("relateContractNoYesNo");
		String relateContractNoTag = req.getParameter("relateContractNoTag");
		String relateContractNoVal = StringUtils.trimToEmpty(req.getParameter("relateContractNo"));
		String policyNoTag = StringUtils.trimToEmpty(req.getParameter("policyNoTag"));
		String policyNo = StringUtils.trimToEmpty(req.getParameter("policyNo"));
		String claimNoTag = StringUtils.trimToEmpty(req.getParameter("claimNoTag"));
		String claimNo = StringUtils.trimToEmpty(req.getParameter("claimNo"));

		String statement = "";
		if (nodeStatusView) {

		} else {
			QueryAction uiQueryAction = new QueryAction();
			DateTime dt = new DateTime(flowInTime2Val, 13);

			statement += uiQueryAction.getCharInConditions("PrpQmain.RiskCode", riskCodeVal);
			statement += uiQueryAction.getCharConditions("PrpQmain.ProposalNo", businessNoTag, businessNoVal);
			statement += uiQueryAction.getCharConditions("PrpQmain.ContractNo", contractNoTag, contractNoVal);
			statement += uiQueryAction.getCharInConditions("PrpQmain.UnderwriteFlag", nodeStatusVal);
			statement += uiQueryAction.getCharConditions("PrpQmain.InputDate", flowInTime1Tag, flowInTime1Val);
			statement += uiQueryAction.getCharConditions("PrpQmain.InputDate", flowInTime2Tag, dt.addDay(1).toString());

			if ("queryStats".equals(strEditType) && "B".equals(selectUnderling)) {// 状态统计,包含下级

			} else {
				statement += uiQueryAction.getCharConditions("PrpQmain.ComCode", comCodeTag, comCodeVal);
			}

			if (riskCategoryVal.equals("D"))// 车险
			{
				statement += uiQueryAction.getCharConditions("PrpQitemcar.LicenseNo", licenseNoTag, licenseNoVal);
			}
		}
		return statement;
	}

	/**
	 * 組織核保審核查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 * @throws Exception
	 *             異常
	 */
	public String getHebaoTaskQueryStatement(HttpServletRequest request, boolean nodeStatusView) throws UserException, Exception {
		HttpSession session = request.getSession();
		PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
		// 是否包含下級
		String selectUnderling = StringUtils.trimToEmpty(request.getParameter("selectUnderling"));
		String statement = this.getControlStatementHebaoByNode(prpDuser, nodeStatusView, selectUnderling, request);
		//modify by wangJun 20150324 matis0003987问题修改，按时间降序排列 begin
		//statement = "SELECT * FROM (" + statement + ") order by flowintime ASC";
		statement = "SELECT * FROM (" + statement + ") order by flowintime DESC";
		//modify by wangJun 20150324 matis0003987问题修改，按时间降序排列 end
		System.out.println("-- 最后的sql==" + statement);
		return statement;
	}

	/**
	 * 組織授權任務查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 * @throws Exception
	 *             異常
	 */
	public String getHebaoAuthorizeTaskQueryStatement(HttpServletRequest request, boolean nodeStatusView) throws Exception {
		HttpSession session = request.getSession();
		PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
		String underling = StringUtils.trimToEmpty(request.getParameter("underling"));
		String selectUnderling = StringUtils.trimToEmpty(request.getParameter("selectUnderling"));
		String statement = this.getControlStatementHebaoByNode(prpDuser, nodeStatusView, selectUnderling, request);
		statement = "SELECT DISTINCT wflogall_temp.* FROM ("
				+ statement
				+ ") wflogall_temp, prptmain tmain,prpphead phead where (tmain.UNDERWRITEFLAG='7' and  wflogall_temp.businessno=tmain.proposalno) or (phead.UNDERWRITEFLAG='7' and  wflogall_temp.businessno=phead.endorseno)  order by wflogall_temp.flowintime ASC ";
		System.out.println("-- 最后的sql==" + statement);
		return statement;
	}

	/**
	 * 組織核保審核查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 * @throws Exception
	 *             異常
	 */
	public String getHebaoTaskQueryQtaStatement(HttpServletRequest request, boolean nodeStatusView) throws Exception {
		HttpSession session = request.getSession();
		PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
		// String underling =
		// StringUtils.trimToEmpty(request.getParameter("underling"));
		// modified by LanNing begin 20080407 核保修改查看下级
		String selectUnderling = StringUtils.trimToEmpty(request.getParameter("selectUnderling"));

		String statement = this.getControlStatementHebaoQta(prpDuser, nodeStatusView, selectUnderling, request);
		//modify by wangJun 20150324 matis0003987问题修改，按时间降序排列 begin
		//statement = "SELECT * FROM (" + statement + ") order by InputDate ASC";
		statement = "SELECT * FROM (" + statement + ") order by InputDate DESC";
		//modify by wangJun 20150324 matis0003987问题修改，按时间降序排列 end
		System.out.println("-- 最后的sql==" + statement);
		return statement;
	}

	/**
	 * 組織核賠審核查詢條件.
	 * 
	 * @param req
	 *            HttpServletRequest請求
	 * @param nodeStatusView
	 *            記得狀態
	 * @return 查詢條件
	 */
	public String getHepeiTaskQueryStatement(HttpServletRequest request, boolean nodeStatusView) {
		HttpSession session = request.getSession();
		String userCode = StringUtils.trimToEmpty((String) session.getAttribute("myUserCode"));
		String statement = this.getPermissionControlStatement(userCode, false);
		statement = statement + " AND WfLog.BusinessType IN('C','Y')";
		statement = statement + this.getQueryConditionStatement(request, false);
		return statement;
	}

	/**
	 * 展示險種大類名稱.
	 * 
	 * @param riskCategory
	 *            險種大類代碼
	 * @return 險種大類名稱
	 */
	public String getShowColumnNameByRiskCategory(String riskCategory) {
		InternationalizationUtil internal = new InternationalizationUtil();
		String columnName = null;
		if (riskCategory.equals("D"))// 车险
		{
			columnName = internal.getText("undwrt.carNo");
		} else if (riskCategory.equals("E"))// 意健险
		{
			columnName = internal.getText("undwrt.identifyNo");
		} else if (riskCategory.equals("Y"))// 水险
		{
			columnName = internal.getText("undwrt.preAgreementNo");
		} else {
			columnName = internal.getText("undwrt.otherNo");
		}
		return columnName;
	}

	/**
	 * 獲取批次處理節點狀態.
	 * 
	 * @param nodeStatus
	 *            節點狀態
	 * @return 批次處理節點狀態
	 */
	public String[] getBatchButtonEnabledByNodeStatus(String nodeStatus) {
		String[] enabled = new String[3];
		if (nodeStatus.equals("0")) {
			enabled[0] = enabled[1] = enabled[2] = "disabled";
		} else if (nodeStatus.equals("4")) {
			enabled[0] = enabled[1] = "disabled";
			enabled[2] = "";
		} else {
			enabled[0] = enabled[1] = "";
			enabled[2] = "disabled";
		}
		return enabled;
	}

	/**
	 * 獲取選擇的處理任務集合.
	 * 
	 * @param request
	 *            HttpServletRequest請求
	 * @return 選擇的處理任務集合
	 */
	public List getCheckboxSelectTaskCollection(HttpServletRequest request) {
		List collection = new ArrayList();
		String[] checkbox = request.getParameterValues("checkboxSelect");
		String[] businessNo = new String[checkbox.length];
		String[] comCode = new String[checkbox.length];
		String[] riskCode = new String[checkbox.length];
		String[] modelNo = new String[checkbox.length];
		String[] nodeNo = new String[checkbox.length];
		String[] flowId = new String[checkbox.length];
		String[] logNo = new String[checkbox.length];
		String[] nodeStatus = new String[checkbox.length];
		WfLogVo wfLogVo = null;
		for (int i = 1; i < checkbox.length; i++) {
			businessNo[i] = request.getParameterValues("BusinessNo")[Integer.parseInt(checkbox[i])];
			comCode[i] = request.getParameterValues("ComCode")[Integer.parseInt(checkbox[i])];
			riskCode[i] = request.getParameterValues("RiskCode")[Integer.parseInt(checkbox[i])];
			modelNo[i] = request.getParameterValues("ModelNo")[Integer.parseInt(checkbox[i])];
			nodeNo[i] = request.getParameterValues("NodeNo")[Integer.parseInt(checkbox[i])];
			flowId[i] = request.getParameterValues("FlowID")[Integer.parseInt(checkbox[i])];
			logNo[i] = request.getParameterValues("LogNo")[Integer.parseInt(checkbox[i])];
			nodeStatus[i] = request.getParameterValues("NodeStatus")[Integer.parseInt(checkbox[i])];
			wfLogVo = new WfLogVo();
			wfLogVo.setBusinessNo(businessNo[i]);
			wfLogVo.setComCode(comCode[i]);
			wfLogVo.setRiskCode(riskCode[i]);
			wfLogVo.setModelNo(Integer.parseInt(modelNo[i]));
			wfLogVo.setNodeNo(Integer.parseInt(nodeNo[i]));
			wfLogVo.getId().setFlowId(flowId[i]);
			wfLogVo.getId().setLogNo(Integer.parseInt(logNo[i]));
			wfLogVo.setNodeStatus(nodeStatus[i]);
			collection.add(wfLogVo);
		}
		return collection;
	}

	/**
	 * 獲取批次審核意見.
	 * 
	 * @param request
	 *            HttpServletRequest請求
	 * @return 批次審核意見
	 */
	public List getBatchTaskCollection(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userCode = (String) session.getAttribute("myUserCode");
		String userName = (String) session.getAttribute("myUserName");
		String deptCode = (String) session.getAttribute("myComCode");
		String deptName = (String) session.getAttribute("myComCName");
		List collection = new ArrayList();
		String[] businessNo = request.getParameterValues("businessNo");
		String[] comCode = request.getParameterValues("comCode");
		String[] modelNo = request.getParameterValues("modelNo");
		String[] nodeNo = request.getParameterValues("nodeNo");
		String[] flowId = request.getParameterValues("flowId");
		String[] logNo = request.getParameterValues("logNo");
		String[] nodeStatus = request.getParameterValues("nodeStatus");
		String[] nextNodeNo = request.getParameterValues("nextNodeNo");
		String[] nextNodeName = request.getParameterValues("nextNodeName");
		WfLogVo wfLogVo = null;
		for (int i = 0; i < businessNo.length; i++) {
			wfLogVo = new WfLogVo();
			wfLogVo.setBusinessNo(businessNo[i]);
			wfLogVo.setComCode(comCode[i]);
			wfLogVo.setModelNo(Integer.parseInt(modelNo[i]));
			wfLogVo.setNodeNo(Integer.parseInt(nodeNo[i]));
			wfLogVo.getId().setFlowId(flowId[i]);
			wfLogVo.getId().setLogNo(Integer.parseInt(logNo[i]));
			wfLogVo.setNodeStatus(nodeStatus[i]);
			wfLogVo.setNextNodeNo(Integer.parseInt(nextNodeNo[i]));
			wfLogVo.setNextNodeName(nextNodeName[i]);
			wfLogVo.setOperatorCode(userCode);
			wfLogVo.setOperatorName(userName);
			wfLogVo.setDeptCode(deptCode);
			wfLogVo.setDeptName(deptName);
			collection.add(wfLogVo);
		}
		return collection;
	}

	/**
	 * 獲取批次審核意見.
	 * 
	 * @param request
	 *            HttpServletRequest請求
	 * @return 批次審核意見
	 */
	public List getBatchNotionCollection(HttpServletRequest request) {
		String[] flowId = request.getParameterValues("flowId");
		String[] logNo = request.getParameterValues("logNo");
		String handleText = StringUtils.trimToEmpty(request.getParameter("HandleText"));
		handleText = StringUtils.replace(handleText, "'", "''");
		List notionCollection = new ArrayList();
		UwNotion uwNotionDto = null;
		for (int i = 0; i < flowId.length; i++) {
			uwNotionDto = new UwNotion();
			uwNotionDto.getId().setFlowId(flowId[i]);
			uwNotionDto.getId().setLogNo(Integer.parseInt(logNo[i]));
			uwNotionDto.setHandleText(handleText);
			notionCollection.add(uwNotionDto);
		}
		return notionCollection;
	}

	/**
	 * 獲取查看下級的查詢條件.
	 * 
	 * @param prpDuser
	 *            用戶訊息
	 * @param nodeStatusView
	 *            節點狀態
	 * @param lowFlag
	 *            是否包含下級
	 * @return 查看下級的查詢條件
	 * @throws Exception
	 *             異常
	 */
	private String getControlStatementHebaoByNode(PrpDuserDto prpDuser, boolean nodeStatusView, String lowFlag) throws Exception {
		String statement = null;
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		String strCondition = null;
		if (nodeStatusView) {
			strCondition = blUtiUwLevelFacade.addPowerString("", prpDuser.getUserCode(), "view_wflogall", "COMCODE", "T", lowFlag);
			statement = " SELECT DISTINCT view_wflogall.* FROM view_wflogall WHERE (" + strCondition
					+ ") AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 ";
		} else {
			strCondition = blUtiUwLevelFacade.addPowerString("", prpDuser.getUserCode(), "Wflog", "COMCODE", "T", lowFlag);
			statement = " SELECT DISTINCT Wflog.* FROM Wflog WHERE (" + strCondition + ") AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 ";
		}
		return statement;
	}

	/**
	 * 獲取查看下級的查詢條件.
	 * 
	 * @param prpDuser
	 *            用戶訊息
	 * @param nodeStatusView
	 *            節點狀態
	 * @param lowFlag
	 *            是否包含下級
	 * @param request
	 *            HttpServletRequest請求
	 * @return 查看下級的查詢條件
	 * @throws Exception
	 *             異常
	 */
	private String getControlStatementHebaoByNode(PrpDuserDto prpDuser, boolean nodeStatusView, String lowFlag, HttpServletRequest request)
			throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String statement = "1=0";
		String statementtemp = "";
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		UtiUwLevel utiUwLevel = new UtiUwLevel();
		String userCode = prpDuser.getUserCode();
		String comCode = prpDuser.getLoginComCode();

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.uwType", Constants.UWTYPE_T);
		queryRule.addEqual("id.validStatus", "1");
		queryRule.addEqual("id.userCode", userCode);
		queryRule.addEqual("id.comCode", comCode);
		Collection<UtiUwLevel> utiUwLevelCollection = utiUwLevelService.getUtiUwLevelList(queryRule);

		String strConditionAll = "";
		String strConditionCom = "";
		String strConditionClassRisk = "";
		String strConditionNode = "";
		String classCode = "";
		String riskCode = "";
		String comCodeTemp = "";
		int modelNo = 0;
		String handType = request.getParameter("handType");
		int nodeNo = 0;
		int passNodeNo = Constants.UWTYPE_PASSNODE;
		boolean flag = false;
		if (nodeStatusView) {
			String statementcommon = ") AND view_wflogall.LogNo <> 1 AND view_wflogall.NodeNo <> 1 "
					+ " AND ( view_wflogall.BusinessType = 'T' OR view_wflogall.BusinessType = 'P' OR view_wflogall.BusinessType = 'E')"
					+ this.getQueryConditionStatement(request, nodeStatusView);

			if (utiUwLevelCollection.size() == 0) {
				throw new Exception("您尚未配置核保權限，不能進行查詢。如有需要請聯繫相關人員進行核保權限申請及配置。");
			}
			for (Iterator<UtiUwLevel> iter = utiUwLevelCollection.iterator(); iter.hasNext();) {
				flag = true;
				utiUwLevel = iter.next();
				if (iter.hasNext()) {
					comCodeTemp = utiUwLevel.getId().getComCode();
					classCode = utiUwLevel.getClassCode();
					riskCode = utiUwLevel.getRiskCode();
					nodeNo = utiUwLevel.getId().getNodeNo();
					if (("B").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO <=" + nodeNo;
					} else if (("A").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO =" + nodeNo;
					} else if (("P").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO =" + passNodeNo;
					} else {
						strConditionNode = "view_wflogall.NODENO ='" + lowFlag + "'";
					}
					strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "view_wflogall", "COMCODE");

					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "view_wflogall");

					strConditionAll = "(" + strConditionNode + " AND (" + strConditionCom + " AND " + strConditionClassRisk + "))";

					statementtemp = statementtemp + " SELECT DISTINCT view_wflogall.* FROM view_wflogall WHERE (" + strConditionAll + statementcommon + "UNION";
					System.out.println("--if==" + statementtemp);
				} else {
					comCodeTemp = utiUwLevel.getId().getComCode();
					classCode = utiUwLevel.getClassCode();
					riskCode = utiUwLevel.getRiskCode();
					nodeNo = utiUwLevel.getId().getNodeNo();
					if (("B").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO <=" + nodeNo;
					} else if (("A").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO =" + nodeNo;
					} else if (("P").equals(lowFlag)) {
						strConditionNode = "view_wflogall.NODENO =" + passNodeNo;
					} else {
						strConditionNode = "view_wflogall.NODENO ='" + nodeNo + "'";
					}
					strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "view_wflogall", "COMCODE");

					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "view_wflogall");

					strConditionAll = "(" + strConditionNode + " AND (" + strConditionCom + " AND " + strConditionClassRisk + "))";

					statementtemp = statementtemp + " SELECT DISTINCT view_wflogall.* FROM view_wflogall WHERE (" + strConditionAll + statementcommon;
					System.out.println("--else==" + statementtemp);

				}
			}
		} else {
			// 双核人员只有登陆权限，没有查询权限时的提示语。
			if (utiUwLevelCollection.size() == 0) {
				// i18n-您尚未配置核保權限，不能進行查詢。如有需要請聯繫相關人員進行核保權限申請及配置。
				throw new UserException(-98, -9999, internal.getText("undwrt.java.TaskDealViewHelper.message1"), "");
			}

			//需求变更，报价单也走核保工作流20140117
			String statementcommon;
			if("12".equals(handType))
			{
				statementcommon = ") AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 "
						+ " AND ( WfLog.BusinessType = 'B')"
						+ this.getQueryConditionStatement(request, nodeStatusView);
			}
			else
			{
				statementcommon = ") AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1 "
					+ " AND ( WfLog.BusinessType = 'T' OR WfLog.BusinessType = 'P' OR WfLog.BusinessType = 'E')"
					+ this.getQueryConditionStatement(request, nodeStatusView);
			}

			for (Iterator<UtiUwLevel> iter = utiUwLevelCollection.iterator(); iter.hasNext();) {
				flag = true;
				utiUwLevel = iter.next();
				if (iter.hasNext()) {
					comCodeTemp = utiUwLevel.getId().getComCode();
					classCode = utiUwLevel.getClassCode();
					riskCode = utiUwLevel.getRiskCode();
					modelNo = utiUwLevel.getId().getModelNo();
					nodeNo = utiUwLevel.getId().getNodeNo();
					if (("B").equals(lowFlag)) {
						//mantis： CAR0231，處理人員：Sam，需求單編號：CAR0231，核保件查詢問題(因model24 是查自動核保通過案件 當單筆權限時有加 但多筆權限卻沒加到)
						strConditionNode = "Wflog.MODELNO in ('" + modelNo +"','24"+ "') AND Wflog.NODENO <=" + nodeNo;
					} else if (("A").equals(lowFlag)) {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO =" + nodeNo;
					} else if (("P").equals(lowFlag)) {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO =" + passNodeNo;
					} else {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO ='" + lowFlag + "'";
					}
					if("103".equals(modelNo) && "00".equals(comCodeTemp)){//火险
						strConditionCom = "Wflog.COMCODE = '" + comCodeTemp + "'";
					}else{
						strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "Wflog", "COMCODE");
					}
					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "Wflog");

					strConditionAll = "(" + strConditionNode + " AND (" + strConditionCom + " AND " + strConditionClassRisk + "))";

					statementtemp = statementtemp + " SELECT DISTINCT Wflog.* FROM Wflog WHERE (" + strConditionAll + statementcommon + "UNION";
					System.out.println("--if==" + statementtemp);
				} else {
					comCodeTemp = utiUwLevel.getId().getComCode();
					classCode = utiUwLevel.getClassCode();
					riskCode = utiUwLevel.getRiskCode();
					modelNo = utiUwLevel.getId().getModelNo();
					nodeNo = utiUwLevel.getId().getNodeNo();
					if (("B").equals(lowFlag)) {
						strConditionNode = "Wflog.MODELNO in ('" + modelNo +"','24"+ "') AND Wflog.NODENO <=" + nodeNo;
					} else if (("A").equals(lowFlag)) {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO =" + nodeNo;
					} else if (("P").equals(lowFlag)) {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO =" + passNodeNo;
					} else {
						strConditionNode = "Wflog.MODELNO = '" + modelNo + "' AND Wflog.NODENO ='" + lowFlag + "'";
					}
					if("103".equals(modelNo) && "00".equals(comCodeTemp)){//火险
						strConditionCom = "Wflog.COMCODE = '" + comCodeTemp + "'";
					}else{
						strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "Wflog", "COMCODE");
					}

					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "Wflog");

					strConditionAll = "(" + strConditionNode + " AND (" + strConditionCom + " AND " + strConditionClassRisk + "))";

					statementtemp = statementtemp + " SELECT DISTINCT Wflog.* FROM Wflog WHERE (" + strConditionAll + statementcommon;
					System.out.println("--else==" + statementtemp);
				}
			}
		}
		if (flag) {
			statement = statementtemp;
		}

		return statement;
	}

	/**
	 * 獲取報價單審核的查詢條件.
	 * 
	 * @param prpDuser
	 *            用戶訊息
	 * @param nodeStatusView
	 *            節點狀態
	 * @param lowFlag
	 *            是否包含下級
	 * @param request
	 *            HttpServletRequest請求
	 * @return 報價單審核的查詢條件
	 * @throws Exception
	 *             異常
	 */
	private String getControlStatementHebaoQta(PrpDuserDto prpDuser, boolean nodeStatusView, String lowFlag, HttpServletRequest request) throws Exception {
		String strEditType = StringUtils.trimToEmpty(request.getParameter("EditType"));
		String strComCode = StringUtils.trimToEmpty(request.getParameter("comCode"));
		String statement = "1=0";
		String statementtemp = "";
		BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
		UtiUwLevelDto utiUwLevelDto = new UtiUwLevelDto();
		String userCode = prpDuser.getUserCode();
		Map session = ActionContext.getContext().getSession();  
		String comCode =(String) session.get("myComCode");
		String conditionslevel = "UWTYPE = 'T' AND VALIDSTATUS  = '1' AND USERCODE = '" + userCode + "' AND COMCODE = '" + comCode + "'";
		Collection UtiUwLevel = blUtiUwLevelFacade.findByConditions(conditionslevel);
		String strConditionAll = "";
		String strConditionCom = "";
		String strConditionCom2 = "1=1";
		String strConditionClassRisk = "";
		String classCode = "";
		String riskCode = "";
		String comCodeTemp = "";
		String mainfieldList = "PrpQmain.ProposalNo,PrpQmain.RiskCode,PrpQmain.ClassCode,PrpQmain.ContractNo,"
				+ "PrpQmain.InsuredName,PrpQmain.ComCode,PrpQmain.UnderwriteFlag,PrpQmain.InputDate," + "PrpQmain.OperatorCode";
		String itemCarfieldList = "PrpQitemcar.LicenseNo";

		boolean flag = false;
		if (nodeStatusView) {

		} else {

			if (UtiUwLevel.size() == 0) {
				throw new Exception("您尚未配置双核权限，不能进行查询。如有需要请联系相关人员进行双核权限申请及配置。");
			}

			String statementcommon = this.getQueryQtaConditionStatement(request, nodeStatusView);
			if ("queryStats".equals(strEditType) && strComCode.length() > 0 && "B".equals(lowFlag)) {// 状态统计
				strConditionCom2 = blUtiUwLevelFacade.addPowerCom(strComCode, "PrpQmain", "COMCODE");
			}

			for (Iterator iter = UtiUwLevel.iterator(); iter.hasNext();) {
				flag = true;
				utiUwLevelDto = (UtiUwLevelDto) iter.next();
				if (iter.hasNext()) {
					comCodeTemp = utiUwLevelDto.getComCode();
					classCode = utiUwLevelDto.getClassCode();
					riskCode = utiUwLevelDto.getRiskCode();

					strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "PrpQmain", "COMCODE");

					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "PrpQmain");

					strConditionAll = " AND (" + strConditionCom + " AND " + strConditionCom2 + " AND " + strConditionClassRisk + ")";

					statementtemp = statementtemp + " SELECT " + mainfieldList + "," + itemCarfieldList
							+ " FROM PrpQmain,PrpQitemcar WHERE PrpQmain.ProposalNo=PrpQitemcar.proposalno" + strConditionAll + statementcommon + "UNION";
					System.out.println("--if==" + statementtemp);
				} else {
					comCodeTemp = utiUwLevelDto.getComCode();
					classCode = utiUwLevelDto.getClassCode();
					riskCode = utiUwLevelDto.getRiskCode();

					strConditionCom = blUtiUwLevelFacade.addPowerCom(comCodeTemp, "PrpQmain", "COMCODE");

					strConditionClassRisk = blUtiUwLevelFacade.addPowerClassRisk(classCode, riskCode, "PrpQmain");

					strConditionAll = " AND (" + strConditionCom + " AND " + strConditionCom2 + " AND " + strConditionClassRisk + ")";

					statementtemp = statementtemp + " SELECT " + mainfieldList + "," + itemCarfieldList
							+ " FROM PrpQmain,PrpQitemcar WHERE PrpQmain.ProposalNo=PrpQitemcar.proposalno" + strConditionAll + statementcommon;
					System.out.println("--else==" + statementtemp);

				}
			}
		}
		if (flag) {
			statement = statementtemp;
		}

		return statement;
	}

	/**
	 * 獲取核保級別設定接口.
	 * 
	 * @return the 核保級別設定接口
	 */
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	/**
	 * 設置核保級別設定接口.
	 * 
	 * @param utiUwLevelService
	 *            待設置核保級別設定接口
	 */
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}
}