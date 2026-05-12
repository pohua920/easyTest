/*
 * @(#)SpecialCaseViewHelper.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>:
 * @author 中科软
 * @description 特殊赔案申请的Viewhelper类
 */
public class SpecialCaseViewHelper {
	/** 报案服务 */
	private RegistService registService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 赔案保单关联信息服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	private PrpLacciCheckService prpLacciCheckService;
	public static final int RULE_LENGTH = 69; // rule字段的长度

	/**
	 * 默认构造方法
	 */
	public SpecialCaseViewHelper() {
	}

	// 整理提示赔案申请时所需要的内容
	public SwfLog viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		SwfLog swfLogDto = new SwfLog();
		swfLogDto.getId().setFlowID(httpServletRequest.getParameter("swfLogFlowID"));
		swfLogDto.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("swfLogLogNo"))));
		String content = httpServletRequest.getParameter("Context");
		String typeFlag = httpServletRequest.getParameter("specialCaseCaseType");

		swfLogDto.setTypeFlag(typeFlag);

		content = user.getUserName() + " " + new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString() + " " + content;
		String[] rules = StringUtils.split(content, RULE_LENGTH);
		List<SwfNotion> swfNotionList = new ArrayList<SwfNotion>();
		int maxLineNo = this.getWorkFlowService().getSwfNotionMaxLineNo(swfLogDto.getId().getFlowID(), swfLogDto.getId().getLogNo());
		for (int i = 0; i < rules.length; i++) {
			SwfNotion swfNotionDto = new SwfNotion();
			swfNotionDto.getId().setFlowID(swfLogDto.getId().getFlowID());
			swfNotionDto.getId().setLogNo(swfLogDto.getId().getLogNo());
			swfNotionDto.setHandleText(rules[i]);
			swfNotionDto.getId().setLineNo(i + maxLineNo);
			swfNotionList.add(swfNotionDto);
		}
		swfLogDto.setSwfNotionList(swfNotionList);
		return swfLogDto;
	}

	public void buessinessNoDtoToView(HttpServletRequest httpServletRequest) throws Exception {
		SwfLog swfLogDto = new SwfLog();
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 解释报案号码，立案号码
		PrpLclaim prpLclaim = queryRelateInfoToDto(httpServletRequest);
		swfLogDto.getId().setFlowID(httpServletRequest.getParameter("swfLogFlowID"));
		swfLogDto.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(httpServletRequest.getParameter("swfLogLogNo"))));
		swfLogDto.setHandlerCode(user.getUserCode());
		swfLogDto.setHandlerName(user.getUserName());
		swfLogDto.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// 查询已有的特殊赔案情况
		/**
		 * String conditions =" flowid='"+swfLogDto.getFlowID()+"' and
		 * nodeType='speci' and
		 * registNo='"+httpServletRequest.getParameter("businessNo") +"' ";
		 */
		String conditions = " handledept='" + user.getComCode() + "' and nodeType='speci'";
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		swfLogDto.setSwfLogList(swfLogList);
		String registNo = httpServletRequest.getParameter("keyIn");
		String riskCode = httpServletRequest.getParameter("riskCode"); // 险种

		RegistDto registDto = registService.findByPrimaryKey(registNo);
		swfLogDto.setCompeFlag("1");// 表示含强三
		if (registDto.getPrpLRegistRPolicyOfCompel() == null) {
			UICodeAction uiCodeAction = UICodeAction.getInstance();
			String configCode = uiCodeAction.translateRiskCodetoConfigCode(riskCode);
			if ("RISKCODE_DAZ".equals(configCode) == false) {
				swfLogDto.setCompeFlag("0");// 不含强三
			}

		}
		httpServletRequest.setAttribute("swfLogDto", swfLogDto);
	}

	// 申请页面初始化
	public void applyScheduleDtoToViewDtoToView(HttpServletRequest httpServletRequest) throws Exception {
		SwfLog swfLogDto = new SwfLog();
		SwfLog swfLogAcciDto = new SwfLog();
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		// 解释报案号码，立案号码
		String registNo = httpServletRequest.getParameter("appRegistNo");
		registNo = registNo.trim();

		String claimNo = "";
		String nodeType = "";
		String flowId = "";
		String nodeStatus = "";
		String condition = "";
		PrpLacciCheck prpLacciCheck = new PrpLacciCheck();
		if (registNo.equals("")) {
			throw new UserException(-100, -1003, "備案號碼錯誤", "請輸入備案號");
		}
		PrpLregist prpLregist = registService.findByPrimaryKeyForPrpLRegist(registNo);
		if (prpLregist == null) {
			throw new UserException(-100, -1003, "備案號碼錯誤", "請輸入備案號");
		}
		if (!"E".equals(ConstantCodes.carClassMap.get(prpLregist.getRiskCode()))) {
			throw new UserException(-100, -1003, "備案號碼錯誤", "此號非意健險報案號，請輸入意健險備案號");
		}

		flowId = this.getWorkFlowService().findFlowIDByRegistNo(registNo);

		List<SwfLog> swfLogDtolist = this.getWorkFlowService().findNodesByFlowID(flowId);

		if (swfLogDtolist != null && swfLogDtolist.size() > 0) {
			for (int k = 0; k < swfLogDtolist.size(); k++) {
				swfLogDto = swfLogDtolist.get(k);
				nodeType = swfLogDto.getNodeType();
				nodeStatus = swfLogDto.getNodeStatus();
				if (nodeType.equals("regis") && nodeStatus.equals("2")) {
					prpLacciCheck.setRegistNo(registNo);
					swfLogAcciDto.getId().setFlowID(swfLogDto.getId().getFlowID());
					swfLogAcciDto.getId().setLogNo(swfLogDto.getId().getLogNo());
					swfLogAcciDto.setNodeName(swfLogDto.getNodeName());
					swfLogAcciDto.setNodeStatus(nodeStatus);
					swfLogAcciDto.setNodeStatusName("正在處理");
					swfLogAcciDto.setNodeType(nodeType);
					swfLogAcciDto.setHandlerCode(user.getUserCode());
					swfLogAcciDto.setHandlerName(user.getUserName());
					httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);
					httpServletRequest.setAttribute("swfLogAcciDto", swfLogAcciDto);
				} else if (nodeType.equals("claim") && (nodeStatus.equals("2") || nodeStatus.equals("0"))) {
					if (nodeStatus.equals("2")) {
						claimNo = swfLogDto.getKeyOut();
					} else {
						claimNo = "";
					}
					prpLacciCheck.setRegistNo(registNo);
					prpLacciCheck.setClaimNo(claimNo);
					swfLogAcciDto.getId().setFlowID(swfLogDto.getId().getFlowID());
					swfLogAcciDto.getId().setLogNo(swfLogDto.getId().getLogNo());
					swfLogAcciDto.setNodeName(swfLogDto.getNodeName());
					swfLogAcciDto.setNodeStatus(nodeStatus);
					if (nodeStatus.equals("0")) {
						swfLogAcciDto.setNodeStatusName("待處理");
					} else if (nodeStatus.equals("2")) {
						swfLogAcciDto.setNodeStatusName("正在處理");
					}
					swfLogAcciDto.setNodeType(nodeType);
					swfLogAcciDto.setHandlerCode(user.getUserCode());
					swfLogAcciDto.setHandlerName(user.getUserName());
					httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);
					httpServletRequest.setAttribute("swfLogAcciDto", swfLogAcciDto);
				} else if (nodeType.equals("compe") && nodeStatus.equals("0")) {
					condition = "flowId = '" + flowId + "' and  nodeType = 'compp' ";
					List<SwfLog> swfLogCompplist = this.getWorkFlowService().findNodesByConditions(condition);
					if (swfLogCompplist != null && swfLogCompplist.size() > 0) { // 有计算书
						for (int m = 0; m < swfLogCompplist.size(); m++) {
							SwfLog swflogCompDto = swfLogCompplist.get(m);
							nodeStatus = swflogCompDto.getNodeStatus();
							if (nodeStatus.equals("2")) {
								prpLacciCheck.setRegistNo(registNo);
								prpLacciCheck.setClaimNo(swflogCompDto.getKeyIn());
								prpLacciCheck.setCompensateNo(swflogCompDto.getBusinessNo());
								swfLogAcciDto.getId().setFlowID(swflogCompDto.getId().getFlowID());
								swfLogAcciDto.getId().setLogNo(swflogCompDto.getId().getLogNo());
								swfLogAcciDto.setNodeName(swflogCompDto.getNodeName());
								swfLogAcciDto.setNodeStatus(swflogCompDto.getNodeStatus());
								swfLogAcciDto.setNodeStatusName("正在處理");
								swfLogAcciDto.setNodeType(swflogCompDto.getNodeType());
								swfLogAcciDto.setHandlerCode(user.getUserCode());
								swfLogAcciDto.setHandlerName(user.getUserName());
								httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);
								httpServletRequest.setAttribute("swfLogAcciDto", swfLogAcciDto);
							} else {
								throw new UserException(-100, -1003, "資料錯誤", "審核已提交，不能申請調查");
							}
						}
					} else { // 没有计算书
						prpLacciCheck.setRegistNo(registNo);
						prpLacciCheck.setClaimNo(swfLogDto.getKeyIn());
						prpLacciCheck.setCompensateNo("");
						swfLogAcciDto.getId().setFlowID(flowId);
						swfLogAcciDto.getId().setLogNo(swfLogDto.getId().getLogNo());
						swfLogAcciDto.setNodeName("審核");
						swfLogAcciDto.setNodeStatus(swfLogDto.getNodeStatus());
						swfLogAcciDto.setNodeStatusName("待處理");
						swfLogAcciDto.setNodeType(swfLogDto.getNodeType());
						swfLogAcciDto.setHandlerCode(user.getUserCode());
						swfLogAcciDto.setHandlerName(user.getUserName());
						httpServletRequest.setAttribute("prpLacciCheck", prpLacciCheck);
						httpServletRequest.setAttribute("swfLogAcciDto", swfLogAcciDto);
					}
				}
				// 如果不满足上面的if就继续循环，累加i
			}
			if (swfLogAcciDto.getId().getFlowID() == null || swfLogAcciDto.getId().getFlowID().equals("")) {
				throw new UserException(-100, -1003, "資料錯誤","審核已提交，不能申請調查");
			}
		} else {
			throw new UserException(-100, -1003, "資料錯誤", "資料庫中沒有這條數據，請確認報案號是否正確");
		}
		// --只允许上次提调结束後才能再次申请提调--------
		String fowid = swfLogAcciDto.getId().getFlowID();
		String checkNotOver = "0";
		String conditions2 = "flowid='" + fowid + "' and nodetype='check'";
		List<SwfLog> swfLogChecklist = this.getWorkFlowService().findNodesByConditions(conditions2);
		if (CommonUtils.isEmpty(swfLogChecklist)) {
			checkNotOver = "0";
		} else {
			for (int i = 0; i < swfLogChecklist.size(); i++) {
				swfLogDto = swfLogChecklist.get(i);
				nodeStatus = swfLogDto.getNodeStatus();
				if (!nodeStatus.equals("4")) {
					checkNotOver = "1";
				}
			}
		}
		httpServletRequest.setAttribute("checkNotOver", checkNotOver);
	}

	/**
	 * 得到原来的申请列表
	 * @param httpServletRequest HttpServletRequest
	 * @throws Exception
	 */
	public void specialCaseDtoToView(HttpServletRequest httpServletRequest) throws Exception {
		SwfLog swfLogDto = new SwfLog();
		httpServletRequest.setAttribute("swfLogDto", swfLogDto);
	}

	/**
	 * 留言保存页面查询相关信息
	 * @param httpServletRequest HttpServletRequest
	 * @throws Exception
	 * @return PrpLmessageDto
	 */
	public PrpLclaim queryRelateInfoToDto(HttpServletRequest httpServletRequest) throws Exception {
		String businessNo = httpServletRequest.getParameter("keyIn");
		String nodeType = httpServletRequest.getParameter("nodeType");
		PrpLclaim prpLclaim = new PrpLclaim();
		String registNo = "";
		String claimNo = "";
		UICodeAction uiCodeAction = UICodeAction.getInstance();

		// 查勘
		if (nodeType.equals("check")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}

		// 定损
		if (nodeType.equals("certa")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}
		// 核损
		if (nodeType.equals("verif")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}

		// 人伤定损
		if (nodeType.equals("wound")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}
		// 人伤核损
		if (nodeType.equals("veriw")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}
		// 单正
		if (nodeType.equals("certi")) {
			registNo = businessNo;
			claimNo = uiCodeAction.translateBusinessCode(registNo, true);
		}

		// 特殊赔案
		if (nodeType.equals("speci")) {
			claimNo = businessNo;
			registNo = uiCodeAction.translateBusinessCode(claimNo, false);
		}
		String conditions = "registNo in (Select registNo From Swflog Where registNo ='" + registNo + "' and (nodeStatus = '4' or nodeStatus='2'))";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<PrpLclaim> prpLclaimList = prpLclaimService.findPrpLclaim(queryRule);
		prpLclaim.setRegistNo(registNo);
		String policyNo = "";
		String policyRNo = "";
		RegistDto registDto = registService.findByPrimaryKey(registNo);
		if (prpLregistrpolicyService.findByRegistNo(registNo).size() > 1 && registDto.getPrpLRegistRPolicyOfCompel() != null) {
			policyRNo = registDto.getPrpLRegistRPolicyOfCompel().getId().getPolicyNo();
			policyNo = registDto.getPrpLregist().getPolicyNo();
		} else {
			policyNo = registDto.getPrpLregist().getPolicyNo();
		}
		String msg = "";
		if (prpLclaimList == null || prpLclaimList.size() == 0) {
			msg = "案件目前還沒有立案，請先立案後再申請！";
			throw new UserException(1, 3, "特殊賠案", msg);
		}
		httpServletRequest.setAttribute("prpLclaimList", prpLclaimList);
		httpServletRequest.setAttribute("prpLclaim", prpLclaim);
		httpServletRequest.setAttribute("policyNo", policyNo);
		httpServletRequest.setAttribute("policyRNo", policyRNo);
		return prpLclaim;
	}

	// 整理提示赔案申请时所需要的内容
	public PrpLacciCheck viewToCheckDto(HttpServletRequest httpServletRequest) throws Exception {
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		PrpLacciCheck prpLacciCheck = new PrpLacciCheck();
		prpLacciCheck.setCertiNo(httpServletRequest.getParameter("certiNo"));
		prpLacciCheck.setRegistNo(httpServletRequest.getParameter("registNo"));
		prpLacciCheck.setCertiType(httpServletRequest.getParameter("certiType"));
		prpLacciCheck.setCheckerCode(httpServletRequest.getParameter("checkerCode"));
		prpLacciCheck.setCheckContext(httpServletRequest.getParameter("context"));
		prpLacciCheck.setCurrency(httpServletRequest.getParameter("currency"));
		prpLacciCheck.setHandleDept(user.getComCode());
		prpLacciCheck.setCheckFee(0D);
		return prpLacciCheck;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
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

	public WorkFlowService getWorkFlowService() {
		if (workFlowService == null) {
			workFlowService = (WorkFlowService) ServiceFactory.getService("workFlowService");
		}
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		if (prpLacciCheckService == null) {
			prpLacciCheckService = (PrpLacciCheckService) ServiceFactory.getService("prpLacciCheckService");
		}
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

}
