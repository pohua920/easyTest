/*
 * @(#)WfLogQueryViewHelper.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.util.ArrayList;
import java.util.Collection;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.util.UIQueryAction;
import com.sinosoft.claim.schema.model.PrpDcompany;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
import org.apache.commons.lang3.SerializationUtils;
import com.sinosoft.claim.common.ConstantsCollection;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.drools.process.core.TypeObject;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcarInsuranceId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLdriverId;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLlossId;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfoId;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.MidResultConfigDto;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END
import com.sinosoft.platform.bl.facade.BLSwfPathNewFacade;
import com.sinosoft.platform.dto.domain.SwfPathNewDto;
import com.sinosoft.reins.common.service.facade.BLReinsLTrialService;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.bl.facade.BLWfLogFacade;
import com.sinosoft.undwrt.dto.domain.WfLogDto;
import com.sinosoft.undwrt.ui.control.action.UICommonDealSubmitAction;
import com.sinosoft.undwrt.ui.control.action.UIWflogQueryAction;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class WfLogQueryViewHelper {
	/** 机构信息服务 */
	private PrpDcompanyService prpDcompanyService;
	/** 再保审核服务 */
	private BLReinsLTrialService blReinsLTrialService;
	/** 核赔工作流服务 */
	private BLWfLogFacade blWfLogFacade = new BLWfLogFacade();
	
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
	private PrpLcompensateService prpLcompensateService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 驾驶员Service */
	private PrpLdriverService prpLdriverService;
	/** 车体险讯息接口 */
	private PrpLcarInsuranceService prpLcarInsuranceService;
	/**车辆财产赔付信息服务*/
	private PrpLlossService prpLlossService;
	/**赔付对象服务*/
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 理赔车辆信息service */
	private PrpLthirdPartyService prpLthirdPartyService;
	/** 保单服务 */
	private PolicyService policyService;
	/**核赔日志服务*/
	private WfLogService wfLogService; 
	/**核赔数据收集服务*/
	private CommonCheckTaskViewHelper commonCheckTaskViewHelper;
	/** 人傷損失 服務 */
	private PrpLpersonLossService prpLpersonLossService;
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END

	/**
	 * 根据页面输入条件拼写Where字句
	 * @param req HttpServletRequest
	 * @throws Exception
	 * @return String
	 */
	public String getWherePart(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		String handType = req.getParameter("HandType");
		String editType = req.getParameter("EditType");
		if (handType == null || handType.equals("")) {
			handType = (String) session.getAttribute("HandType");
			editType = (String) session.getAttribute("EditType");
		}

		// 初始条件
		String statement = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog " + " WHERE WfLog.ModelNo = UwGrade.ModelNo" + " AND UwGrade.GroupNo = UwGroup.GroupNo" + " AND Wflog.RiskCode = UwGroup.RiskCode"
				+ " AND Wflog.ComCode = UwGroup.ComCode" + " AND Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1";
		if (handType.equals("11")) {
			statement += " AND WfLog.BusinessType NOT IN('C','Y')";
		} else if (handType.equals("22")) {
			statement += " AND WfLog.BusinessType IN('C','Y')";
		}
		statement = statement + this.getQueryConditionStatement(req);
		return statement;
	}

	private String getQueryConditionStatement(HttpServletRequest req) {
		String riskCategoryTag = "=";
		String riskCategoryVal = StringUtils.trimToEmpty(req.getParameter("riskCategory"));
		String[] riskCodeVal = req.getParameterValues("riskCode");
		String businessNoTag = req.getParameter("businessNoTag");
		String businessNoVal = StringUtils.trimToEmpty(req.getParameter("businessNo"));
		String contractNoTag = req.getParameter("contractNoTag");
		String contractNoVal = StringUtils.trimToEmpty(req.getParameter("contractNo"));
		String comCodeTag = req.getParameter("comCodeTag");
		String comCodeVal = StringUtils.trimToEmpty(req.getParameter("comCode"));
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
		String[] relateContractNoYesNoVal = req.getParameterValues("relateContractNoYesNo");
		String relateContractNoTag = req.getParameter("relateContractNoTag");
		String relateContractNoVal = StringUtils.trimToEmpty(req.getParameter("relateContractNo"));
		String policyNoTag = StringUtils.trimToEmpty(req.getParameter("policyNoTag"));
		String policyNo = StringUtils.trimToEmpty(req.getParameter("policyNo"));
		String claimNoTag = StringUtils.trimToEmpty(req.getParameter("claimNoTag"));
		String claimNo = StringUtils.trimToEmpty(req.getParameter("claimNo"));

		String statement = "";
		UIQueryAction uiQueryAction = new UIQueryAction();
		statement += uiQueryAction.getCharConditions("Wflog.RiskCategory", riskCategoryTag, riskCategoryVal);
		statement += uiQueryAction.getCharInConditions("Wflog.RiskCode", riskCodeVal);
		statement += uiQueryAction.getCharConditions("Wflog.BusinessNo", businessNoTag, businessNoVal);
		statement += uiQueryAction.getCharConditions("Wflog.ContractNo", contractNoTag, contractNoVal);
		statement += uiQueryAction.getCharConditions("Wflog.ComCode", comCodeTag, comCodeVal);
		statement += uiQueryAction.getCharInConditions("Wflog.NodeStatus", nodeStatusVal);
		statement += uiQueryAction.getCharConditions("Wflog.FlowInTime", flowInTime1Tag, flowInTime1Val);
		statement += uiQueryAction.getCharConditions("Wflog.FlowInTime", flowInTime2Tag, flowInTime2Val);
		statement += uiQueryAction.getCharConditions("Wflog.PolicyNo", policyNoTag, policyNo);
		statement += uiQueryAction.getCharConditions("Wflog.ClaimNo", claimNoTag, claimNo);

		if (riskCategoryVal.equals("1"))// 车险
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
		return statement;
	}

	/**
	 * 设置撤销任务列表
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public void setUndoQueryTaskList(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		UIWflogQueryAction uiWflogQueryAction = new UIWflogQueryAction();
		Collection undoTaskList = new ArrayList<Object>();
		String sql = "";
		String strWherePart = "";
		String userCode = (String) req.getSession(false).getAttribute("myUserCode");
		strWherePart = " a.OperatorCode='" + userCode.trim() + "'" + " AND a.ModelNo=d.ModelNo" + " AND a.NodeNo=d.NodeNo" + " AND a.NodeNo<>1" + " AND a.RiskCode=c.RiskCode" + " AND a.ComCode=c.ComCode" + " AND c.GroupNo=d.GroupNo "
				+ " AND d.UserCode='" + userCode.trim() + "'" + " AND a.NodeStatus='4'" + " AND a.LogNo <> 1";
		sql = "SELECT DISTINCT a.* FROM WfLog a,UwGroup c,UwGrade d" + " WHERE " + strWherePart.trim();
		undoTaskList = uiWflogQueryAction.findByConditions(sql, true);
		// 传入撤销任务列表
		session.setAttribute("undoTaskList", undoTaskList);
	}

	/**
	 * 获取批量核保列表
	 * @param req HttpServletRequest
	 * @throws Exception
	 * @return Collection
	 */
	public Collection<WfLogDto> setBatchTaskViewToDto(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(false);
		Collection<WfLogDto> wfLogList = new ArrayList<WfLogDto>();
		WfLogDto wfLogDto = null;
		String[] flowID = req.getParameterValues("flowID");
		String[] businessNo = req.getParameterValues("businessNo");
		String[] businessType = req.getParameterValues("businessType");
		// 统一指定第一条记录节点
		String nodeNo = req.getParameter("selectNodeNo");
		String[] modelNo = req.getParameterValues("modelNo");
		String[] operatorCode = req.getParameterValues("operatorCode");
		String[] flowStatus = req.getParameterValues("flowStatus");
		String userCode = (String) session.getAttribute("myUserCode");
		for (int i = 0; i < flowID.length - 1; i++) {
			wfLogDto = new WfLogDto();
			wfLogDto.setFlowID(flowID[i]);
			wfLogDto.setModelNo(Integer.parseInt(modelNo[i]));
			wfLogDto.setNodeNo(Integer.parseInt(nodeNo));
			wfLogDto.setBusinessNo(businessNo[i]);
			wfLogDto.setBusinessType(businessType[i]);
			wfLogDto.setUserCode(userCode);
			wfLogDto.setOperatorCode(operatorCode[i]);
			wfLogDto.setFlowStatus(flowStatus[i]);
			wfLogDto.setFlag("1");
			wfLogList.add(wfLogDto);
		}
		return wfLogList;
	}

	/**
	 * 设置批量核保提交列表
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public void setBatchTaskListDtoToView(HttpServletRequest req) throws UserException, Exception {
		HttpSession session = req.getSession(true);
		String[] modelNo = req.getParameterValues("modelNo");
		String[] nodeNo = req.getParameterValues("nodeNo");
		String[] flowID = req.getParameterValues("flowID");
		String[] logNo = req.getParameterValues("logNo");
		String[] businessNo = req.getParameterValues("businessNo");
		String[] businessType = req.getParameterValues("businessType");
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
		Collection<?> colSubmitList = new ArrayList<Object>();
		Collection<WfLogDto> colBackList = new ArrayList<WfLogDto>();
		Collection<WfLogDto> colWfLogList = new ArrayList<WfLogDto>();
		WfLogDto wfLogDto = null;
		UICommonDealSubmitAction uiCommonDealSubmitAction = new UICommonDealSubmitAction();
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
							swfPathNewDto = blSwfPathNewFacade.findByPrimaryKey(riskCode[i], strComCode[1]);
							// 如果没有找到就查找上级
							if (swfPathNewDto != null) {
								isFind = true;
							} else {
								PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(strComCode[1]);
								// 查到总公司还没有数据就抛出
								if (!prpDcompany.getComCode().equals(prpDcompany.getPrpDcompany().getComCode())) {
									strComCode[1] = prpDcompany.getPrpDcompany().getComCode();
								} else {
									throw new UserException(-98, -9999, this.getClass().getName() + ".getNodeNo()", "沒有找到路徑，請聯系信息部！");
								}
							}
						}
						arrNodeNo = swfPathNewDto.getPath().split(",");
						arrNodeName = swfPathNewDto.getPathDesc().split(",");
						// 回退路径
						for (int j = (arrNodeNo.length - 1); j >= 0; j--) {
							if (Integer.parseInt(nodeNo[i]) > Integer.parseInt(arrNodeNo[j])) {
								WfLogDto wfPathDto = new WfLogDto();
								wfPathDto.setNodeNo(Integer.parseInt(arrNodeNo[j]));
								wfPathDto.setNodeName(arrNodeName[j]);
								if (wfPathDto == null || "".equals(wfPathDto)) {
									colBackList.add(wfPathDto);
								}
							}
						}
					} else {
						// 回退列表
						colBackList = uiCommonDealSubmitAction.getBackList(flowID[i], Integer.parseInt(logNo[i]), Integer.parseInt(nodeNo[i]));
					}

					// 提交记录列表
					wfLogDto = new WfLogDto();
					wfLogDto.setFlowID(flowID[i]);
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

	/**
	 * 设置提交指定人员列表
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public void setSubmitUserrList(HttpServletRequest req) throws Exception {
		try {
			HttpSession session = req.getSession(true);
			Collection<?> submitUserList = new ArrayList<Object>();
			String modelNo = req.getParameter("ModelNo");
			String nodeNo = req.getParameter("selectNodeNo");
			String businessType = req.getParameter("BusinessType");
			String businessNo = req.getParameter("BusinessNo");
			String flag = req.getParameter("Flag");
			submitUserList = new UICommonDealSubmitAction().getSubmitUserList(Integer.parseInt(modelNo), Integer.parseInt(nodeNo), businessType, businessNo, flag);
			session.setAttribute("submitUserList", submitUserList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 设置任务列表
	 * @param req HttpServletRequest
	 * @throws Exception
	 * @author 中科软
	 */
	public void setTaskMessage(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		String userCode = (String) session.getAttribute("myUserCode");
		String sql = " SELECT DISTINCT Wflog.* FROM UwGroup ,UwGrade,Wflog " + " WHERE WfLog.ModelNo = UwGrade.ModelNo " + " AND UwGrade.UserCode = '" + userCode + "'" + " AND WfLog.NodeNo = UwGrade.NodeNo" + " AND UwGrade.GroupNo = UwGroup.GroupNo"
				+ " AND Wflog.RiskCode = UwGroup.RiskCode" + " AND Wflog.ComCode = UwGroup.ComCode" + " AND Wflog.LogNo <> 1" + " AND Wflog.NodeNo <> 1" + " AND WfLog.NodeStatus NOT IN('4','0')";
		String wherePart = "";
		WfLogDto wfLogDto = new WfLogDto();
		UIWflogQueryAction uiWflogQueryAction = new UIWflogQueryAction();
		ArrayList<WfLogDto> hebaoList = new ArrayList<WfLogDto>();
		ArrayList<WfLogDto> hepeiList = new ArrayList<WfLogDto>();
		int size = 0;
		int i = 0;
		int requestNum = 0;
		int notSubmitNum = 0;
		wherePart = " AND WfLog.BusinessType NOT IN('C','Y') ";
		hebaoList = (ArrayList) uiWflogQueryAction.findByConditions(sql + wherePart, true);
		size = hebaoList.size();
		for (i = 0; i < size; i++) {
			// 查询待处理任务 1
			wfLogDto = (WfLogDto) hebaoList.get(i);
			if (wfLogDto.getNodeStatus().equals("1")) {
				requestNum++;
			}
			// 查询处理未提交任务 3
			else if (wfLogDto.getNodeStatus().equals("3") || (wfLogDto.getNodeStatus().equals("2") && wfLogDto.getOperatorCode().equals(userCode))/*
																																				 * &&
																																				 * wfLogDto
																																				 * .
																																				 * getOperatorCode
																																				 * (
																																				 * )
																																				 * .
																																				 * equals
																																				 * (
																																				 * userCode
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
		hepeiList = (ArrayList) uiWflogQueryAction.findByConditions(sql + wherePart, true);
		size = hepeiList.size();
		for (i = 0; i < size; i++) {
			wfLogDto = (WfLogDto) hepeiList.get(i);
			if (wfLogDto.getNodeStatus().equals("1")) {
				requestNum++;
			}
			// 查询处理未提交任务 3
			else if (wfLogDto.getNodeStatus().equals("3") || (wfLogDto.getNodeStatus().equals("2") && wfLogDto.getOperatorCode().equals(userCode))/*
																																				 * &&
																																				 * wfLogDto
																																				 * .
																																				 * getOperatorCode
																																				 * (
																																				 * )
																																				 * .
																																				 * equals
																																				 * (
																																				 * userCode
																																				 * )
																																				 */) {
				notSubmitNum++;
			}
		}
		session.setAttribute("hepeiRequestNum", String.valueOf(requestNum));
		session.setAttribute("hepeiNotSubmitNum", String.valueOf(notSubmitNum));
	}


	/**
	 * (批次)核賠案件查詢包含WFLOG
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public List<PrpLcompensate> queryHeapTask(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		String userCode = (String) session.getAttribute("myUserCode");
		String choseRiskCode = req.getParameter("riskCode");//險種
		req.setAttribute("riskCode",req.getParameter("riskCode"));
		String chosePayCodeType = req.getParameter("payCodeType");//賠付代號
		req.setAttribute("payCodeType",req.getParameter("payCodeType"));
		String choseUniformNo = req.getParameter("uniformNo");//賠付對象統一編號/身分證號
		req.setAttribute("uniformNo",req.getParameter("uniformNo"));
		String choseflowInTime1 = req.getParameter("flowInTime1");//提交時間(起)
		req.setAttribute("flowInTime1",req.getParameter("flowInTime1"));
		String choseflowInTime2 = req.getParameter("flowInTime2");//提交時間(迄)
		req.setAttribute("flowInTime2",req.getParameter("flowInTime2"));
		String chsoeNodeStatus = req.getParameter("choseNodeStatus");//狀態
		req.setAttribute("choseNodeStatus",req.getParameter("choseNodeStatus"));
		String chsoePaymentKind = req.getParameter("paymentKind");//費用類型
		req.setAttribute("paymentKind",req.getParameter("paymentKind"));
		String[] aryNodeStatus = chsoeNodeStatus.split(",");
		String sqlNodeStatus = "";
		List<PrpLcompensate> prpLcompensateList = new ArrayList<PrpLcompensate>();
		for(String str : aryNodeStatus){
			if(!str.equals("undefined") && StringUtils.isNotBlank(str)){
				sqlNodeStatus+=",'"+str+"'";
			}
		}
		String sql = 
			"   SELECT * "+
			   " FROM " +
			   " ( " +
			    	" SELECT  " +
					    " DISTINCT WFLOG.*  " +
					    " FROM  " +
					    " WFLOG  " +
					      " WHERE  " +
					      " ( " +
					        " ( " +
					          " WFLOG.NODENO <= 5  " +
					            " AND ( " +
					            " 1 = 1  " +
					            " AND ( " +
					              " WFLOG.CLASSCODE IN ('A', 'B') OR WFLOG.RISKCODE IN ('"+choseRiskCode+"') " +
					             " ) " +
					            " ) " +
					           " ) " +
					       " )  " +
					       " AND WFLOG.LOGNO <> 1  " +
					       " AND WFLOG.NODENO <> 1  " +
					       " AND ( " +
					        	"   WFLOG.BUSINESSTYPE = 'C' OR WFLOG.BUSINESSTYPE = 'Y' " +
					       " )  " +
					       " AND RISKCATEGORY = 'D' " +
					       " AND RISKCODE IN('"+choseRiskCode+"')  " +
					       (StringUtils.isNotBlank(sqlNodeStatus)?" AND NODESTATUS IN("+sqlNodeStatus.substring(1)+") ":"")+
					       " AND FLOWINTIME >= '"+choseflowInTime1+" 00:00:00'  " +
					       " AND FLOWINTIME <= '"+choseflowInTime2+" 23:59:59'  " +
			    " )  " +
			    " WHERE (OPERATORCODE IS NULL OR OPERATORCODE = '"+user.getUserCode()+"' ) " +//LOGNO = '2' AND DEPTCODE IS NULL AND DEPTNAME IS NULL AND 
			    " ORDER BY FLOWINTIME DESC ";
		String wherePart = "";
		WfLogDto wfLogDto = new WfLogDto();
		UIWflogQueryAction uiWflogQueryAction = new UIWflogQueryAction();
		ArrayList<WfLogDto> hebaoList = new ArrayList<WfLogDto>();
		int size = 0;
		int i = 0;
		int requestNum = 0;
		int notSubmitNum = 0;
//		wherePart = " AND WfLog.BusinessType NOT IN('C','Y') ";
		hebaoList = (ArrayList) uiWflogQueryAction.findByConditions(sql + wherePart, true);
		size = hebaoList.size();
		double sumThisPaidAmount=0.0;
		int sumThisPaidCount = 0;
		String wfLogFlowIdArray = "";
		String compNoAry = "";
		for (i = 0; i < size; i++) {
			wfLogDto = (WfLogDto) hebaoList.get(i);
			WfLog wfLog = this.getWfLogService().findByPrimaryKey(wfLogDto.getFlowID(), wfLogDto.getLogNo());
			commonCheckTaskViewHelper.getPassPath(req,wfLog);
			
			List<PrpLpayObjectInfo> prpLpayObjectInfoList;
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", wfLogDto.getBusinessNo());
			if(StringUtils.isNotBlank(choseUniformNo)){
				queryRule.addEqual("uniformNo", choseUniformNo);//UI有擋 必須輸入(賠付對象統一編號/身分證號)
			}
			if(StringUtils.isNotBlank(chsoePaymentKind)){
				queryRule.addEqual("paymentKind", chsoePaymentKind);//費用類型
			}
			queryRule.addEqual("paycodeType", chosePayCodeType);//賠付代號
			//queryRule.addSql(" ((kindcode is null and certiType = '01') or (kindcode is not null and certiType = '02'))");
			prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
			if(null!=prpLpayObjectInfoList && prpLpayObjectInfoList.size()>0){
				for(PrpLpayObjectInfo ploi:prpLpayObjectInfoList){
					//if(wfLogDto.getBusinessNo().equals("C180024AL0006302")){
					//	System.out.println("CLM0241 追蹤點1");
					//}
					if(wfLogDto.getBusinessNo().equals(ploi.getId().getCompensateNo())){
						if(wfLog.getNodeStatus().equals("1")){
							wfLog.setDeptCode(user.getComCode());
							wfLog.setDeptName(user.getComName());
							wfLog.setOperatorCode(user.getUserCode());
							wfLog.setOperatorName(user.getUserName());
							wfLog.setNodeStatus("2");
							this.getWfLogService().saveOrUpdate(wfLog);
						}
						wfLogFlowIdArray += ","+wfLog.getId().getFlowId()+"@"+wfLog.getId().getLogNo();
						//選中的去找paid
						//SELECT plc.SUMTHISPAID,plc.* FROM PRPLCOMPENSATE plc WHERE plc.COMPENSATENO = 'C188124AL0029101'
						QueryRule queryRule2 = QueryRule.getInstance();
						queryRule2.addEqual("compensateNo", wfLogDto.getBusinessNo());
						List<PrpLcompensate> prpLcompensateListTemp = prpLcompensateService.findPrpLcompensate(queryRule2);
						for(PrpLcompensate flc:prpLcompensateListTemp){
							
							compNoAry+=","+flc.getCompensateNo();
							sumThisPaidAmount+=flc.getSumThisPaid();
							sumThisPaidCount++;
		//					req.setAttribute("lastClaimNo",flc.getClaimNo());//抓最後一筆的就好 前一個func有查詢需要
							req.setAttribute("lastPolicyNo",flc.getPolicyNo());//抓最後一筆的就好 前一個func有查詢需要
							prpLcompensateList.add(flc);
						}
						
					}
				}
			}
			
		}
		req.setAttribute("compNoAry",compNoAry);//將計算書號放到前端但不顯示
		req.setAttribute("sumThisPaidAmount",sumThisPaidAmount);//總賠金額
		req.setAttribute("sumThisPaidCount",sumThisPaidCount);//總賠付件數
		req.setAttribute("wfLogFlowIdArray", wfLogFlowIdArray);//包括地sflog 的flowId and logNo
		return prpLcompensateList;
	}
	

	/**
	 * (批次)核賠案件 - 导出理算紧急案件清单至Excel
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增[EXCEL]
	 * @param httpServletRequest 返回给页面的request
	 * @throws Exception
	 */
	public void exportToExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		
		List<PrpLcompensate> prpLcompensateList = queryHeapTask(httpServletRequest);
		String choseRiskCode = httpServletRequest.getParameter("riskCode");//險種
		
		// 查询理赔节点状态信息
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String title = "";
		// 定义列名信息
		List<MidResultConfigDto> arrConfigs = new ArrayList<MidResultConfigDto>();
		List<Map<String, String>> heapHashMapAll = new ArrayList<Map<String, String>>();
		if(choseRiskCode.equals("A01")){
			title = "A01_";
			String[] columnS = { 
					"prpLcompensateCompensateNo", "replevyFlag", "prpLcompensateInsuredName", "prpLdriverDriverName","prpLthirdPartyRelationship", 
					"prpLdriverIdentifyNumber", "prpLdriverBirthday_show_format_rcDate","driverSex","prpLcompensateDamageName","prpLcompensateDamageCode",
					"prpLcompensateLicenseNo","prpCmain.govPurchaseFlag","prpLcompensateLossType","selectAccidentType","prpLcompensateIndemnityDutyRate",
					"prpLcompensateOppositeIndemnityDuty","prpLcompensateOtherIndemnityDuty","prpLcarInsuranceDeductibleInvoice","prpLlossDtoKindCode","prpLpayObjectInfoPaycodeType",
					"prpLpayObjectInfoPaymentKind","prpLcompensateSumPaid","prpLpayObjectInfoUniformNo","prpLpayObjectInfoOwnerName","prpLpayObjectInfoAMLFlag"};
			String[] columnNameS = { 
					"計算書號", "是否追償", "被保險人", "肇事駕駛人","與被保險人關係", 
					"證件號碼", "出生日期", "性別", "出險原因","出險代號", 
					"牌照號碼", "收費狀況", "本車損失", "車體險肇責","保車肇責", 
					"對造車肇責", "其他肇責", "自負額發票號碼", "保險種類代號","賠付代號（賠案）", 
					"費用類型", "賠付總額", "受款人ID", "受款人名稱","洗錢"};
			for (int i = 0; i < columnNameS.length; i++) {
				MidResultConfigDto midResultConfigDto = new MidResultConfigDto();
				midResultConfigDto.setItemColumn(columnS[i]);
				midResultConfigDto.setItemColumnName(columnNameS[i]);
				midResultConfigDto.setDataType("String");
				arrConfigs.add(midResultConfigDto);
			}
			if(prpLcompensateList != null && prpLcompensateList.size()>0){
				for(PrpLcompensate plc : prpLcompensateList){
					
					PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(plc.getClaimNo());
					if(null==prpLclaim){
						prpLclaim = new PrpLclaim();
					}
					
					QueryRule queryRule_prpLcarInsurance = QueryRule.getInstance();
					queryRule_prpLcarInsurance.addEqual("id.compensateNo", plc.getCompensateNo());
					List<PrpLcarInsurance> prpLcarInsuranceList = this.getPrpLcarInsuranceService().findPrpLcarInsurance(queryRule_prpLcarInsurance);
					PrpLcarInsurance prpLcarInsurance = null;
					for(PrpLcarInsurance pli:prpLcarInsuranceList){
						if(null!=prpLcarInsurance){
							continue;
						}
						prpLcarInsurance = pli;
					}
					if(null==prpLcarInsurance){
						prpLcarInsurance = new PrpLcarInsurance();
					}
					
					QueryRule queryRule_prpLdriver = QueryRule.getInstance();
					queryRule_prpLdriver.addEqual("policyNo", plc.getPolicyNo());
					List<PrpLdriver> prpLdriverList = prpLdriverService.findPrpLdriver(queryRule_prpLdriver);
					PrpLdriver prpLdriver = new PrpLdriver();
					if(null!=prpLdriverList && prpLdriverList.size()>0){
						prpLdriver = prpLdriverList.get(0);
					}

					QueryRule queryRule_prpLthirdParty = QueryRule.getInstance();
					//mantis： CLM0250，處理人員：DP0713，需求單編號：新核心-理賠整批處理Excel下載欄位對應調整 START
//					queryRule_prpLthirdParty.addEqual("claimNo", plc.getClaimNo());
					queryRule_prpLthirdParty.addEqual("id.registNo", prpLclaim.getRegistNo());
					//mantis： CLM0250，處理人員：DP0713，需求單編號：新核心-理賠整批處理Excel下載欄位對應調整 END
					queryRule_prpLthirdParty.addEqual("id.serialNo", new Integer("1"));
					List<PrpLthirdParty> prpLthirdPartyList = this.prpLthirdPartyService.findPrpLthirdParty(queryRule_prpLthirdParty);
					PrpLthirdParty prpLthirdParty = null;
					if(null!=prpLthirdPartyList && prpLthirdPartyList.size()>0){
						for(PrpLthirdParty plp :prpLthirdPartyList){
							if(null!=prpLthirdParty){
								continue;
							}
							if(StringUtils.isNotBlank(plp.getRelationship())){
								prpLthirdParty = plp;//有relationship寫入
							}
						}
						if(null==prpLthirdParty){
							for(PrpLthirdParty plp :prpLthirdPartyList){
								if(null!=prpLthirdParty){
									continue;
								}
								prpLthirdParty = plp;//如果還是空的就取第一筆
							}
						}
					}
					if(null==prpLthirdParty){
						prpLthirdParty = new PrpLthirdParty();
					}
					
					String palyName = "";
					String conditions = " policyno = '" + plc.getPolicyNo() + "'";
					// 获得缴费情况
					int intReturn = 0;
					intReturn = this.getPolicyService().checkPay(conditions);// -1为未缴费，0为未缴全，1为缴全
					if (intReturn == -1) {
						palyName = "未繳費";
					} else if (intReturn == 0) {
						palyName = "未繳全";
					} else if (intReturn == 1) {
						palyName = "繳全";
					}
					
					QueryRule queryRule_prpLpayObjectInfo = QueryRule.getInstance();
					queryRule_prpLpayObjectInfo.addEqual("id.compensateNo", plc.getCompensateNo());
					List<PrpLpayObjectInfo> prpLpayObjectInfoList=this.prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule_prpLpayObjectInfo);
					PrpLpayObjectInfo prpLpayObjectInfo = null;
					for(PrpLpayObjectInfo ppoi:prpLpayObjectInfoList){
						if(null!=prpLpayObjectInfo){
							continue;
						}
						prpLpayObjectInfo = ppoi;
					}
					if(null==prpLpayObjectInfo){
						prpLpayObjectInfo = new PrpLpayObjectInfo();
					}
					
					QueryRule queryRule_prpLloss = QueryRule.getInstance();
					queryRule_prpLloss.addEqual("id.compensateNo", plc.getCompensateNo());
					List<PrpLloss> prpLlossList = this.prpLlossService.findPrpLloss(queryRule_prpLloss);
					PrpLloss prpLloss = null;
					//第一筆先做 文件的prpLloss是用Left JOIN
					for(PrpLloss pll:prpLlossList){
						if(null!=prpLloss){
							continue;
						}
						prpLloss = pll;
					}
					if(null==prpLloss){
						prpLloss = new PrpLloss();
					}
					
					Map<String, String> heapHashMap = new HashMap<String, String>();
					heapHashMap.put(columnS[0], plc.getCompensateNo());//計算書號
					heapHashMap.put(columnS[1], this.trimToEmpty(prpLclaim.getReplevyFlag()));//是否追償
					heapHashMap.put(columnS[2], this.trimToEmpty(prpLclaim.getInsuredName()));//被保險人
					heapHashMap.put(columnS[3], this.trimToEmpty(prpLdriver.getDriverName()));//肇事駕駛人
					heapHashMap.put(columnS[4], this.trimToEmpty(prpLthirdParty.getRelationship()));//與被保險人關係
					
					heapHashMap.put(columnS[5], this.trimToEmpty(prpLdriver.getIdentifyNumber()));//證件號碼
					heapHashMap.put(columnS[6], this.trimToEmpty(prpLdriver.getBirthday()+""));//出生日期
					heapHashMap.put(columnS[7], this.trimToEmpty(prpLdriver.getDriverSex()));//性別
					heapHashMap.put(columnS[8], this.trimToEmpty(prpLclaim.getDamageName()));//出險原因
					heapHashMap.put(columnS[9], this.trimToEmpty(prpLclaim.getDamageCode()));//出險代號
	
					heapHashMap.put(columnS[10], this.trimToEmpty(prpLdriver.getLicenseNo()));//牌照號碼
					heapHashMap.put(columnS[11], palyName);//收費狀況
					heapHashMap.put(columnS[12], this.trimToEmpty(plc.getLossType()));//本車損失
					heapHashMap.put(columnS[13], this.trimToEmpty(plc.getAccidentType()));//車體險肇責
					heapHashMap.put(columnS[14], this.trimToEmpty(plc.getIndemnityDutyRate()+""));//保車肇責
	
					heapHashMap.put(columnS[15], this.trimToEmpty(plc.getOppositeIndemnityDuty()+""));//對造車肇責
					heapHashMap.put(columnS[16], this.trimToEmpty(plc.getOtherIndemnityDuty()+""));//其他肇責
					heapHashMap.put(columnS[17], this.trimToEmpty(prpLcarInsurance.getDeductibleInvoice()));//自負額發票號碼
					heapHashMap.put(columnS[18], prpLloss.getKindCode());//保險種類代號???????
					heapHashMap.put(columnS[19], trimToEmptyForObj(prpLpayObjectInfo.getPaycodeType(),ConstantsCollection.payCodeTypeList,null));//賠付代號（賠案）
					
					heapHashMap.put(columnS[20], prpLpayObjectInfo.getPaymentKind());//費用類型
					heapHashMap.put(columnS[21], this.trimToEmpty(plc.getSumPaid()+""));//賠付總額
					heapHashMap.put(columnS[22], prpLpayObjectInfo.getUniformNo());//受款人ID
					heapHashMap.put(columnS[23], prpLpayObjectInfo.getOwnerName());//受款人名稱
					heapHashMap.put(columnS[24], this.trimToEmpty(prpLpayObjectInfo.getAmlFlag()));//洗錢
					
					heapHashMapAll.add(heapHashMap);
					
					//prplLoss 有第二筆之後 再塞入其他
					if(null!=prpLlossList && prpLlossList.size()>=2){
						for(int i=0;i<prpLlossList.size();i++){
							if(i!=0){
								Map<String, String> heapHashMap2 = SerializationUtils.clone(new HashMap<>(heapHashMap));
								heapHashMap2.put(columnS[18], prpLlossList.get(i).getKindCode());//保險種類代號???????
								heapHashMapAll.add(heapHashMap2);
							}
						}
					}
				}
			}else {
				throw new UserException(0, 1, "批次導出數據", "沒有符合要求的數據，請重新選擇導出！");
			}
				user.setUserMessage("導出Excel文件成功！");
				httpServletRequest.setAttribute("user", user);
		
		
		}else if(choseRiskCode.equals("B01")){
				title = "B01_";
				String[] columnS = { 
						"prpLcompensatePaySituation","prpLcompensatePayCode","indemnityDuty","prpLcompensateIndemnityDutyRate","prpLcompensateAccidentType",
						"prpLcompensateSubrogation","replevyFlag","prpLpayObjectInfoPaycodeType","prpLpayObjectInfoPaymentKind","prpLpersonCommerceEndCaseAndRecoverFlag",
						"personNum","prpLpersonCommerceIdentifyNumber","prpLpersonCommerceChasingLossesStatus","prpLpersonMedicalDetailCode","totalPay",
						"prpLcompensateCompensateNo"};
				String[] columnNameS = { 
						"給付追償情況", "賠付代號", "本車肇事責任", "本車肇責百分比","肇責類型", 
						"是否涉及第29條代位情形", "是否追償", "賠付代號（賠案）", "費用類型","個別受害人醫療給付是否結案且待健保追償（返還）", 
						"受害人序號※會有同一案有兩位受害人的情況，所以需要有序號顯示複數受害人", "身份證號", "健保局追償狀況","費用代碼", "實賠金額總計"
						,"計算書號"};
				for (int i = 0; i < columnNameS.length; i++) {
					MidResultConfigDto midResultConfigDto = new MidResultConfigDto();
					midResultConfigDto.setItemColumn(columnS[i]);
					midResultConfigDto.setItemColumnName(columnNameS[i]);
					midResultConfigDto.setDataType("String");
					arrConfigs.add(midResultConfigDto);
				}
				
				if(prpLcompensateList != null && prpLcompensateList.size()>0){
					for(PrpLcompensate plc : prpLcompensateList){
						

						QueryRule queryRule_prpLclaim = QueryRule.getInstance();
						queryRule_prpLclaim.addEqual("policyNo", plc.getPolicyNo());
						List<PrpLclaim> prpLclaimList = this.getPrpLclaimService().findPrpLclaim(queryRule_prpLclaim);
						PrpLclaim prpLclaim=new PrpLclaim();
						if(null!=prpLclaimList && prpLclaimList.size()>0){
							prpLclaim = prpLclaimList.get(0);
						}

						QueryRule queryRule_prpLpayObjectInfo = QueryRule.getInstance();
						queryRule_prpLpayObjectInfo.addEqual("id.compensateNo", plc.getCompensateNo());
						List<PrpLpayObjectInfo> prpLpayObjectInfoList=this.prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule_prpLpayObjectInfo);
						PrpLpayObjectInfo prpLpayObjectInfo = null;

						
						QueryRule queryRule_prpLloss = QueryRule.getInstance();
						queryRule_prpLloss.addEqual("id.compensateNo", plc.getCompensateNo());
						List<PrpLloss> prpLlossList = this.prpLlossService.findPrpLloss(queryRule_prpLloss);
						PrpLloss prpLloss = null;
						for(PrpLloss pll:prpLlossList){
							if(null!=prpLloss){
								continue;
							}
							prpLloss = pll;
						}
						if(null==prpLloss){
							prpLloss = new PrpLloss();
						}
					
						for(PrpLpayObjectInfo ppoi:prpLpayObjectInfoList){
							if(null!=prpLpayObjectInfo){
								continue;
							}
							prpLpayObjectInfo = ppoi;
						}
						if(null==prpLpayObjectInfo){
							prpLpayObjectInfo = new PrpLpayObjectInfo();
						}


						
						QueryRule queryRule_prpLpersonLoss = QueryRule.getInstance();
						queryRule_prpLpersonLoss.addEqual("id.compensateNo", plc.getCompensateNo());
						List<PrpLpersonLoss> rpLpersonLossList = prpLpersonLossService.findPrpLpersonLoss(queryRule_prpLpersonLoss); //prpLdriverService.findPrpLdriver(queryRule_prpLdriver);

						if(null!=rpLpersonLossList && rpLpersonLossList.size()>0){
							for(PrpLpersonLoss prpLpersonLoss :rpLpersonLossList){
								Map<String, String> heapHashMap = new HashMap<String, String>();
								heapHashMap.put(columnS[0], trimToEmptyForObj(plc.getPaySituation(),ConstantsCollection.paySituationList,null));//給付追償情況
								heapHashMap.put(columnS[1], trimToEmptyForObj(plc.getPayCode(),ConstantsCollection.payCodeList,null));//賠付代號
								heapHashMap.put(columnS[2], trimToEmptyForObj(plc.getIndemnityDuty(),ConstantsCollection.indemnityDutyList,null));//本車肇事責任
								heapHashMap.put(columnS[3], this.trimToEmpty(plc.getIndemnityDutyRate()+""));//本車肇責百分比
								heapHashMap.put(columnS[4], trimToEmptyForObj(plc.getAccidentType(),ConstantsCollection.accidentTypeList,null));//肇責類型
								
								heapHashMap.put(columnS[5], this.trimToEmpty(plc.getSubrogation()));//是否涉及第29條代位情形
								heapHashMap.put(columnS[6], this.trimToEmpty(prpLclaim.getReplevyFlag()));//是否追償
								heapHashMap.put(columnS[7], trimToEmptyForObj(prpLpayObjectInfo.getPaycodeType(),ConstantsCollection.payCodeTypeList,null));//賠付代號（賠案）
								heapHashMap.put(columnS[8], trimToEmptyForObj(prpLpayObjectInfo.getPaymentKind(),ConstantsCollection.paymentKindList,null));//費用類型
								heapHashMap.put(columnS[9], this.trimToEmpty(prpLpersonLoss.getEndCaseAndRecoverFlag()));//個別受害人醫療給付是否結案且待健保追償（返還）
				
								heapHashMap.put(columnS[10], this.trimToEmpty(prpLpersonLoss.getPersonNo()+""));//受害人序號※會有同一案有兩位受害人的情況，所以需要有序號顯示複數受害人。
								heapHashMap.put(columnS[11], this.trimToEmpty(prpLpersonLoss.getIdentifyNumber()));//身份證號
								heapHashMap.put(columnS[12], trimToEmptyForObj(prpLpersonLoss.getChasingLossesStatus(),ConstantsCollection.chasingLossesStatusList,null));//健保局追償狀況
								heapHashMap.put(columnS[13], this.trimToEmpty(prpLpersonLoss.getMedicalCode()));//費用代碼
								heapHashMap.put(columnS[14], this.trimToEmpty(prpLpersonLoss.getSumRealPay()+""));//實賠金額總計
								
								heapHashMap.put(columnS[15], this.trimToEmpty(plc.getCompensateNo()));
								heapHashMapAll.add(heapHashMap);
							}
						}else{
							Map<String, String> heapHashMap = new HashMap<String, String>();
							heapHashMap.put(columnS[0], trimToEmptyForObj(plc.getPaySituation(),ConstantsCollection.paySituationList,null));//給付追償情況
							heapHashMap.put(columnS[1], trimToEmptyForObj(plc.getPayCode(),ConstantsCollection.payCodeList,null));//賠付代號
							heapHashMap.put(columnS[2], trimToEmptyForObj(plc.getIndemnityDuty(),ConstantsCollection.indemnityDutyList,null));//本車肇事責任
							heapHashMap.put(columnS[3], this.trimToEmpty(plc.getIndemnityDutyRate()+""));//本車肇責百分比
							heapHashMap.put(columnS[4], trimToEmptyForObj(plc.getAccidentType(),ConstantsCollection.accidentTypeList,null));//肇責類型
							
							heapHashMap.put(columnS[5], this.trimToEmpty(plc.getSubrogation()));//是否涉及第29條代位情形
							heapHashMap.put(columnS[6], this.trimToEmpty(prpLclaim.getReplevyFlag()));//是否追償
							heapHashMap.put(columnS[7], trimToEmptyForObj(prpLpayObjectInfo.getPaycodeType(),ConstantsCollection.payCodeTypeList,null));//賠付代號（賠案）
							heapHashMap.put(columnS[8], trimToEmptyForObj(prpLpayObjectInfo.getPaymentKind(),ConstantsCollection.paymentKindList,null));//費用類型
							//PERSON沒有的狀況下
							heapHashMap.put(columnS[9], "");//個別受害人醫療給付是否結案且待健保追償（返還）
							
							heapHashMap.put(columnS[10], "");//受害人序號※會有同一案有兩位受害人的情況，所以需要有序號顯示複數受害人。
							heapHashMap.put(columnS[11], "");//身份證號
							heapHashMap.put(columnS[12], "");//健保局追償狀況
							heapHashMap.put(columnS[13], "");//費用代碼
							heapHashMap.put(columnS[14], "");//實賠金額總計

							heapHashMap.put(columnS[15], this.trimToEmpty(plc.getCompensateNo()));
							heapHashMapAll.add(heapHashMap);
						}
					}
				}
		}
		this.exportExcel(heapHashMapAll, arrConfigs, httpServletResponse,title);
	}
	
	/**
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 */
	public String trimToEmpty(Object obj) {
		if(obj instanceof String){
			return obj.toString() == null || obj.toString().trim().equals("null")?"":obj.toString().trim();
		}
		return null;
	}
	
	/**
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 */
	public String trimToEmptyForObj(String strId,Map cc,Integer format) {
		if(null==format){
			//default
			if(null!=cc && null!=cc.get(strId)){
				return this.trimToEmpty(strId)+"-"+this.trimToEmpty(cc.get(strId));
			}
			return this.trimToEmpty(strId);
		}
		return null;
	}
	
	/**
	 * (批次)核賠案件 - 保存EXCEL
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @Parameter ArrayList
	 *            arrHashResult（arrHashResult是hashResult的集合，hashResult：Key
	 *            MidResultConfig.ItemColumn，Value 对应表的Dto对应字段的值）
	 * @Parameter ArrayList arrConfigs（页面配置项集合）
	 * @Parameter HttpServletResponse httpServletResponse
	 */
	public void exportExcel(List<Map<String, String>> arrHashResult, List<MidResultConfigDto> arrConfigs, HttpServletResponse response,String title) throws Exception {
		MidResultConfigDto midResultConfigDto = null;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); // 建立新的HSSFWorkbook对象
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("sheet1"); // 建立新的HSSFSheet对象
		hssfSheet.setDefaultColumnWidth(20);// 指定默认列宽
		hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, arrConfigs.size() - 1));// 指定合並区域,前二个参数为开始处X,Y坐标.後二个为结束的坐标.
		HSSFRow hssfRow = null;
		HSSFCell hssfCell = null;
		Map<String, String> hashResult = null;

		String strItemColumn = "";
		String strDataType = "";

		int i = 0;
		int j = 0;

		response.setContentType("application/ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((title+"undwrtClaim.xls").getBytes(), "iso-8859-1"));
		// 表名格式
		HSSFFont tableName_font = hssfWorkbook.createFont();
		tableName_font.setFontName(HSSFFont.FONT_ARIAL);
		tableName_font.setFontHeightInPoints((short) 15);

		HSSFCellStyle tableName_cellNumStyle = hssfWorkbook.createCellStyle();
		tableName_cellNumStyle.setFont(tableName_font);
		tableName_cellNumStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tableName_cellNumStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		tableName_cellNumStyle.setWrapText(true);// 文本区域随内容多少自动调整
		// 列名格式
		HSSFFont title_font = hssfWorkbook.createFont();
		title_font.setFontName(HSSFFont.FONT_ARIAL);
		title_font.setFontHeightInPoints((short) 8);

		HSSFCellStyle title_cellNumStyle = hssfWorkbook.createCellStyle();
		title_cellNumStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		title_cellNumStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		title_cellNumStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		title_cellNumStyle.setFont(title_font);
//		title_cellNumStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		title_cellNumStyle.setWrapText(true);// 文本区域随内容多少自动调整

		HSSFCellStyle cellNumStyle_red = hssfWorkbook.createCellStyle();
		cellNumStyle_red.setFillForegroundColor(HSSFColor.RED.index);
		cellNumStyle_red.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellNumStyle_red.setWrapText(true);
		HSSFCellStyle cellNumStyle_yellow = hssfWorkbook.createCellStyle();
		cellNumStyle_yellow.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellNumStyle_yellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellNumStyle_yellow.setWrapText(true);
		HSSFCellStyle cellNumStyle_normal = hssfWorkbook.createCellStyle();
		cellNumStyle_normal.setWrapText(true);

		// 表名
		hssfRow = hssfSheet.createRow(0);// 建立新行，行号从0开始
		hssfRow.setHeight((short) 400);
		hssfCell = hssfRow.createCell(0);
		hssfCell.setCellValue(title+"批次核賠清單");
		hssfCell.setCellStyle(tableName_cellNumStyle);
		// 列名
		hssfRow = hssfSheet.createRow((short) 1);// 建立新行，行号从1开始
		for (i = 0; i < arrConfigs.size(); i++) {
			midResultConfigDto = new MidResultConfigDto();
			midResultConfigDto = (MidResultConfigDto) arrConfigs.get(i);
			hssfCell = hssfRow.createCell(i);
			hssfCell.setCellValue(midResultConfigDto.getItemColumnName());
			hssfCell.setCellStyle(title_cellNumStyle);
		}

		// 数据
		midResultConfigDto = null;
		HSSFCellStyle row_cellNumStyle = hssfWorkbook.createCellStyle();
		for (i = 0; i < arrHashResult.size(); i++) {
			hashResult = arrHashResult.get(i);
			hssfRow = hssfSheet.createRow((short) (i + 2)); // 建立新行，行号从2开始
			int Dalydays = 0;
//			Dalydays = Integer.parseInt((String) hashResult.get("timeLimit").toString());
//			if (Dalydays >= 25) {
//				row_cellNumStyle = cellNumStyle_red;
//			} else if (Dalydays >= 15 && Dalydays < 25) {
//				row_cellNumStyle = cellNumStyle_yellow;
//			} else {
//				row_cellNumStyle = cellNumStyle_normal;
//			}
			for (j = 0; j < arrConfigs.size(); j++) {
				midResultConfigDto = new MidResultConfigDto();
				midResultConfigDto = (MidResultConfigDto) arrConfigs.get(j);
				hssfCell = hssfRow.createCell(j);

				hssfCell.setCellStyle(row_cellNumStyle);
				strItemColumn = midResultConfigDto.getItemColumn();
				strDataType = midResultConfigDto.getDataType();

				if (strDataType.equals("String")) {
//					if(null == hashResult.get(strItemColumn)){
//						System.out.println("CLM0241 WfLogQueryViewHelper.exportToExcel(WfLogQueryViewHelper.java from column="+strItemColumn);
//					}
					try{
						hssfCell.setCellValue(((String) hashResult.get(strItemColumn).toString()));
					}catch(Exception e){System.out.println("column="+strItemColumn+"error_String i="+i+"/j:"+j);}
				} else if (strDataType.equals("Double")){
					try{
						hssfCell.setCellValue(Double.valueOf(hashResult.get(strItemColumn)).doubleValue());
					}catch(Exception e){System.out.println("column="+strItemColumn+"error_Double i="+i+"/j:"+j);}
				}
				else if (strDataType.equals("Float")){
					try{
						hssfCell.setCellValue(Float.valueOf(hashResult.get(strItemColumn)).floatValue());
					}catch(Exception e){System.out.println("column="+strItemColumn+"error_Float i="+i+"/j:"+j);}
				}
				else if (strDataType.equals("Integer")){
					try{
						hssfCell.setCellValue(Integer.valueOf(hashResult.get(strItemColumn)).intValue());
					}catch(Exception e){System.out.println("column="+strItemColumn+"error_Integer i="+i+"/j:"+j);}
				}
			}
		}

		OutputStream out = response.getOutputStream();
		hssfWorkbook.write(out);
		out.close();
		hssfWorkbook = null;
		hashResult = null;
		arrConfigs = null;

	}
	
	/*
	 * 分保意向中取每个危险单位的相关信息
	 */

	public void setDangerInfoToViewByReins(String certiNo, String certiType, HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession(true);
		session.setAttribute("CertiNo", certiNo);
		session.setAttribute("CertiType", certiType);

	}

	public String simulateRepolicyByDangerNo(String certiNo, String classCode, String certiType) throws Exception {
		blReinsLTrialService.simulateRepolicyByDangerNo(certiNo, classCode, certiType);
		return "s";
	}

	// 按自定义SQL查询
	public Collection<?> findByConditions(String wherePart, boolean blnAll) throws Exception {
		return (Collection<?>) blWfLogFacade.findByConditions(wherePart, blnAll);
	}

	public PageRecord findByConditions(String wherePart, int pageNo, int rowsPerPage, boolean blnAll) throws Exception {
		return (PageRecord) blWfLogFacade.findByConditions(wherePart, pageNo, rowsPerPage, blnAll);
	}

	public PageRecord findByConditions(String wherePart, int pageNo, int rowsPerPage) throws Exception {
		return (PageRecord) blWfLogFacade.findByConditions(wherePart, pageNo, rowsPerPage);
	}

	public Collection<?> findByConditions(String wherePart) throws Exception {
		return (Collection<?>) blWfLogFacade.findByConditions(wherePart);
	}

	// 按主键查找一条记录
	public WfLogDto findByPrimaryKey(String flowID, int logNo) throws Exception {
		return blWfLogFacade.findByPrimaryKey(flowID, logNo);
	}

	/**
	 * 得到查询的条件
	 * @param conditions String
	 * @throws Exception
	 * @return Collection
	 */
	public Collection<?> getPackageId(String conditions) throws Exception {
		return (Collection<?>) blWfLogFacade.getPackageId(conditions);
	}

	public ArrayList<?> getWorkFlowQueryView(String sql) throws Exception {
		return (ArrayList<?>) blWfLogFacade.getWorkFlowQueryView(sql);
	}

	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}
	
	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	public WorkFlowService getWorkFlowService() {
		if (workFlowService == null) {
			return (WorkFlowService) ServiceFactory.getService("workFlowService");
		}
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLdriverService getPrpLdriverService() {
		return prpLdriverService;
	}

	public void setPrpLdriverService(PrpLdriverService prpLdriverService) {
		this.prpLdriverService = prpLdriverService;
	}

	public PrpLcarInsuranceService getPrpLcarInsuranceService() {
		return prpLcarInsuranceService;
	}

	public void setPrpLcarInsuranceService(
			PrpLcarInsuranceService prpLcarInsuranceService) {
		this.prpLcarInsuranceService = prpLcarInsuranceService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public CommonCheckTaskViewHelper getCommonCheckTaskViewHelper() {
		return commonCheckTaskViewHelper;
	}

	public void setCommonCheckTaskViewHelper(CommonCheckTaskViewHelper commonCheckTaskViewHelper) {
		this.commonCheckTaskViewHelper = commonCheckTaskViewHelper;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}
	
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END


}
