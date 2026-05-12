/*
 * @(#)PrepayBeforeEditAction.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import ins.framework.common.DateTime;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.PolicyDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.dto.domain.PrpClimitDto;
import com.sinosoft.claim.dto.domain.PrpCmainDto;
import com.sinosoft.claim.schema.model.PrpLSpecialCaseReason;
import com.sinosoft.claim.schema.model.PrpLSpecialCaseReasonId;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.service.facade.PrpLSpecialCaseReasonService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.specailCase.util.DAAPrepayViewHelper;
import com.sinosoft.claim.ui.control.action.UIPolicyAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.bl.facade.BLWfLogFacade;
import com.sinosoft.undwrt.dto.domain.WfLogDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrepayBeforeEditAction extends Struts2Action {

	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	/**预赔登记服务*/
	private PrpLprepayService prpLprepayService;
	/**立案服务*/
	private ClaimService claimService;
	/**特殊赔案申请原因服务*/
	private PrpLSpecialCaseReasonService prpLSpecialCaseReasonService;
	/**特殊赔案数据收集 */
	private DAAPrepayViewHelper daaPrepayViewHelper;
	private String editType = "";
	private String caseType = "";
	private String sysconst_PrepayPercent = "";
	private String time1 = "";
	private String time2 = "";
	/** 审核意见服务 */
	private UwNotionService uwNotionService;
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 处理工作流服务 */
	private WorkFlowService workFlowService;

	/**
	 * 处理特殊赔案
	 * @return
	 * @throws Exception
	 */
	public String prepayBeforeEdit() throws Exception {
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest httpServletRequest = this.getRequest();
		String policyNo = "";
		String registNo = "";
		String claimNo = "";
		String riskCode = httpServletRequest.getParameter("riskCode"); // 险种
		String forward = ""; // 向前
		String prepayNo = "";
		policyNo = httpServletRequest.getParameter("policyNo");
		if (policyNo == null || policyNo.equals("")) {
			policyNo = httpServletRequest.getParameter("prpLprepayPolicyNo");
		}
		UIPolicyAction uiPolicyAction = new UIPolicyAction();
		PolicyDto policyDto = new PolicyDto();
		policyDto = uiPolicyAction.findByPrimaryKey(policyNo);
		// 增加保费是否实收提示
		String conditions = "POLICYNO = '" + policyNo + "'";
		int payFlag = uiPolicyAction.checkPay(conditions);
		httpServletRequest.setAttribute("payFlag", String.valueOf(payFlag));

		List<PrpClimitDto> prpclimitDtoList = policyDto.getPrpClimitDtoList();
		// 增加共保标志位 begin
		PrpCmainDto prpLCmainDto = policyDto.getPrpCmainDto();
		httpServletRequest.setAttribute("coinsFlag", prpLCmainDto.getCoinsFlag());
		// add by zhangyurui 2009-05-26 增加共保标志位 end
		// add by liuwei at 2011-07-14 增加被保险人名称 start
		httpServletRequest.setAttribute("insuredName", prpLCmainDto.getInsuredName());
		// add by liuwei at 2011-07-14 增加被保险人名称 end

		double limitfeeHaveDuty = 0.00;
		double limitfeeNoneDuty = 0.00;

		if (prpclimitDtoList != null && prpclimitDtoList.size() > 0) {
			for (int i = 0; i < prpclimitDtoList.size(); i++) {
				PrpClimitDto prpclimitDto = (PrpClimitDto) prpclimitDtoList.get(i);
				if (prpclimitDto.getLimitType().equals("91")) {
					limitfeeHaveDuty = prpclimitDto.getLimitFee();
				}
				if (prpclimitDto.getLimitType().equals("94")) {
					limitfeeNoneDuty = prpclimitDto.getLimitFee();
				}
			}
		}
		httpServletRequest.setAttribute("limitfeeHaveDuty", String.valueOf(limitfeeHaveDuty));
		httpServletRequest.setAttribute("limitfeeNoneDuty", String.valueOf(limitfeeNoneDuty));
		// Reason:增加控制：历次垫付款不能超过无责医疗限额、支付款不能超过有责医疗限额
		String status = httpServletRequest.getParameter("status");
		String prpLprepayPrepayNo = httpServletRequest.getParameter("prpLprepayPrepayNo");
		claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		if (claimNo == null || "".equals(claimNo)) {
			PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(prpLprepayPrepayNo);
			claimNo = prpLprepay.getClaimNo();
		}
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		double sumBeforePrepaidzf = 0.00;
		double sumBeforePrepaiddf = 0.00;
		List<PrpLprepay> prpLprepayList = claimDto.getPrpLprepayList();
		if (prpLprepayList != null && prpLprepayList.size() > 0) {
			for (int i = 0; i < prpLprepayList.size(); i++) {
				PrpLprepay element = prpLprepayList.get(i);
				if ("7".equals(element.getCaseType())) {
					if (!("2".equals(status) && prpLprepayPrepayNo.equals(element.getPreCompensateNo()))) {
						sumBeforePrepaidzf += element.getSumPrePaid();
					}
				} else {
					if ("8".equals(element.getCaseType())) {
						if (!("2".equals(status) && prpLprepayPrepayNo.equals(element.getPreCompensateNo()))) {
							sumBeforePrepaiddf += element.getSumPrePaid();
						}
					}

				}
			}
		}
		httpServletRequest.setAttribute("sumBeforePrepaidzf", String.valueOf(sumBeforePrepaidzf));
		httpServletRequest.setAttribute("sumBeforePrepaiddf", String.valueOf(sumBeforePrepaiddf));
		// 获取核赔审批片语和审批意见
		List<UwNotion> uwNotionList = (List<UwNotion>) this.getUwNotionService().findByConditions(" claimNo='" + claimNo + "' order by businessno,logno,lineno");
		Map<String, UwNotion> map = new HashMap<String, UwNotion>();
		for (int i = 0; i < uwNotionList.size(); i++) {
			UwNotion uwNotionDto = uwNotionList.get(i);
			String keyString = uwNotionDto.getBusinessNo() + uwNotionDto.getId().getLogNo();
			if (map.containsKey(keyString)) {
				String handletext1 = uwNotionDto.getHandleText();
				String handletext2 = map.get(keyString).getHandleText();
				uwNotionDto.setHandleText(handletext2 + handletext1);
				map.put(keyString, uwNotionDto);
			} else {
				map.put(keyString, uwNotionDto);
			}
		}
		Set<String> set = map.keySet();
		List<UwNotion> uwNotionList2 = new ArrayList<UwNotion>();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			UwNotion uwNotionDto = map.get(iterator.next());
			uwNotionList2.add(uwNotionDto);
		}
		httpServletRequest.setAttribute("uwNotionList", uwNotionList2);
		// 获取核赔审批片语和审批意见fubon-470 end

		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD")) {
			registNo = claimDto.getPrpLclaim().getRegistNo();
			httpServletRequest.setAttribute("registNo", registNo);
			// 查询立案信息,整理输入，用於初始界面显示
			// ==================================================
			// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
			// 如果没有flowID和logno则不进行判断。
			String flowID = httpServletRequest.getParameter("swfLogFlowID");
			String logNo = httpServletRequest.getParameter("swfLogLogNo");

			// 查询特殊赔案申请原因
			PrpLSpecialCaseReason prpLSpecialCaseReason = null;
			long haveReason = prpLSpecialCaseReasonService.getCount("from PrpLSpecialCaseReason p where p.id.claimNo=? and p.id.logNo=?", claimNo, logNo);
			if (haveReason == 0) {
				// 由於可能核赔不通过退回生成新的logno，所以取一次特殊赔案的最小logno,以获取申请原因
				List<SwfLog> iterator = this.getSwfLogService().findByConditions("businessno in (select businessno from swflog where logNo = '" + logNo + "' and flowid = '" + flowID + "') and nodetype = 'speci' and flowid = '" + flowID + "'");
				int minLogNo = Integer.valueOf(logNo);
				for (int i = 0; i < iterator.size(); i++) {
					SwfLog swfLogDto = iterator.get(i);
					if (minLogNo > swfLogDto.getId().getLogNo()) {
						minLogNo = swfLogDto.getId().getLogNo();
					}
				}
				PrpLSpecialCaseReasonId prpLSpecialCaseReasonId = new PrpLSpecialCaseReasonId(claimNo, new Long(minLogNo));
				prpLSpecialCaseReason = prpLSpecialCaseReasonService.findPrpLSpecialCaseReason(prpLSpecialCaseReasonId);
			} else {
				PrpLSpecialCaseReasonId prpLSpecialCaseReasonId = new PrpLSpecialCaseReasonId(claimNo, Long.valueOf(logNo));
				prpLSpecialCaseReason = prpLSpecialCaseReasonService.findPrpLSpecialCaseReason(prpLSpecialCaseReasonId);
			}
			String reason = "";
			if (prpLSpecialCaseReason != null) {
				reason = prpLSpecialCaseReason.getReason();
			}
			httpServletRequest.setAttribute("reason", reason);
			if (flowID != null && logNo != null) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					String msg = "案件'" + claimNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所占用,請選擇其它案件進行處理!";
					throw new UserException(1, 3, "工作流", msg);
				}
			}
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(claimNo, "ClaimNo");
			}
			// 判断险种
			daaPrepayViewHelper.claimDtoToView(httpServletRequest, claimNo);
		}

		if (editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询预赔信息,整理输入，用於初始界面显示
			registNo = claimDto.getPrpLclaim().getRegistNo();
			httpServletRequest.setAttribute("registNo", registNo);
			prepayNo = httpServletRequest.getParameter("prpLprepayPrepayNo"); // 赔案号
			daaPrepayViewHelper.prepayDtoToView(httpServletRequest, prepayNo);
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(prepayNo, "PrepayNo");
			}
			// 查询特殊赔案申请原因
			String flowID = httpServletRequest.getParameter("swfLogFlowID");
			String logNo = httpServletRequest.getParameter("swfLogLogNo");
			PrpLSpecialCaseReason prpLSpecialCaseReason = null;
			long haveReason = prpLSpecialCaseReasonService.getCount("from PrpLSpecialCaseReason p where p.id.claimNo=? and p.id.logNo=?", claimNo, logNo);
			if (haveReason == 0) {
				// 由於可能核赔不通过退回生成新的logno，所以取一次特殊赔案的最小logno,以获取申请原因
				List<SwfLog> list = this.getSwfLogService().findByConditions("businessno in (select businessno from swflog where logNo = '" + logNo + "' and flowid = '" + flowID + "') and nodetype = 'speci' and flowid = '" + flowID + "'");
				int minLogNo = Integer.valueOf(logNo);
				Iterator<SwfLog> iterator = list.iterator();
				while (iterator.hasNext()) {
					SwfLog swfLogDto = iterator.next();
					if (minLogNo > swfLogDto.getId().getLogNo()) {
						minLogNo = swfLogDto.getId().getLogNo();
					}
				}
				prpLSpecialCaseReason = prpLSpecialCaseReasonService.findPrpLSpecialCaseReason(new PrpLSpecialCaseReasonId(claimNo, new Long(minLogNo)));
			} else {
				prpLSpecialCaseReason = prpLSpecialCaseReasonService.findPrpLSpecialCaseReason(new PrpLSpecialCaseReasonId(claimNo, Long.valueOf(logNo)));
			}
			String reason = "";
			if (prpLSpecialCaseReason != null) {
				reason = prpLSpecialCaseReason.getReason();
			}
			httpServletRequest.setAttribute("reason", reason);
			// add end 查询特殊赔案申请原因
			// add by begin 核赔退回的特殊赔案显示核赔审核意见
			if (("3").equals(status)) {
				BLWfLogFacade blWfLogFacade = new BLWfLogFacade();
				List<WfLogDto> wfLogList = (List<WfLogDto>) blWfLogFacade.findByConditions("businessno = '" + prepayNo + "' and businesstype = 'Y'");
				String flowId = "";
				if (wfLogList != null && wfLogList.size() > 0) {
					WfLogDto WfLogDto = wfLogList.get(0);
					flowId = WfLogDto.getFlowID();
				}
				if (!flowId.equals("")) {
					List<UwNotion> uwNotionDtoList = this.getUwNotionService().findByConditions("flowid = '" + flowId + "' order by logno desc");
					if (uwNotionDtoList != null && uwNotionDtoList.size() > 0) {
						UwNotion uwNotionDto = uwNotionDtoList.get(0);
						httpServletRequest.setAttribute("uwNotionHandleText", uwNotionDto.getHandleText());
					}
				}
			}
			// add by 核赔退回的特殊赔案显示核赔审核意见
		}
		if (editType.equals("Approve")) {
			// 查询预赔信息,整理输入，用於初始界面显示
			claimNo = httpServletRequest.getParameter("prpLprepayPrepayNo"); // 赔案号
			daaPrepayViewHelper.prepayDtoToView(httpServletRequest, claimNo);
			return forward;
		}
		//
		// reason:未处理预赔任务的放弃处理
		if (editType.equals("GIVUP")) {
			// add by 放弃未暂存和提交的预赔任务，删去结点操作人，使其他人可见可处理
			String FlowID = httpServletRequest.getParameter("swfLogFlowID");
			int LogNo = Integer.parseInt((String) httpServletRequest.getParameter("swfLogLogNo"));
			SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
			if (swfLogDto.getNodeType().equals("speci")) {
				swfLogDto.setHandlerCode("");
				swfLogDto.setHandlerName("");
				swfLogDto.setFlowStatus("1");
			}
			this.getWorkFlowService().updateFlow(swfLogDto);
			this.clearMessages();
			this.saveMessage(this.getText("prompt.compensate.giveup"));
			forward = "success";
			return forward;
		}
		// 取得forward
		forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "prepa", editType, 1);
		return forward;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLSpecialCaseReasonService getPrpLSpecialCaseReasonService() {
		return prpLSpecialCaseReasonService;
	}

	public void setPrpLSpecialCaseReasonService(PrpLSpecialCaseReasonService prpLSpecialCaseReasonService) {
		this.prpLSpecialCaseReasonService = prpLSpecialCaseReasonService;
	}

	public DAAPrepayViewHelper getDaaPrepayViewHelper() {
		return daaPrepayViewHelper;
	}

	public void setDaaPrepayViewHelper(DAAPrepayViewHelper daaPrepayViewHelper) {
		this.daaPrepayViewHelper = daaPrepayViewHelper;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getSysconst_PrepayPercent() {
		if (sysconst_PrepayPercent == null || "".equals(sysconst_PrepayPercent)) {
			try {
				sysconst_PrepayPercent = AppConfig.get("sysconst.PrepayPercent");
			} catch (Exception e) {
				sysconst_PrepayPercent = "";
			}
		}
		return sysconst_PrepayPercent;
	}

	public void setSysconst_PrepayPercent(String sysconst_PrepayPercent) {
		this.sysconst_PrepayPercent = sysconst_PrepayPercent;
	}

	public String getTime1() {
		if (time1 == null || "".equals(time1)) {
			time1 = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).toString();
		}
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		if (time2 == null || "".equals(time2)) {
			DateTime dateTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND);
			time2 = dateTime.getHour() + "时" + dateTime.getSecond() + "分";
		}
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
