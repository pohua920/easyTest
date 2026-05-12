package com.sinosoft.claim.check.web;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.util.DAACheckViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.generalClaim.util.GeneralClaimViewHelper;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
/**
 * 分发HTTP Post 车险理赔查勘编辑界面
 * <p>
 * Title: 车险理赔查勘前编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔查勘前界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class CheckBeforeEditAction extends Struts2Action {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 查勘Service */
	private CheckService checkService;
	/** 查勘主表Service */
	private PrpLcheckService prpLcheckService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 车险查勘ViewHelper */
	private DAACheckViewHelper daaCheckViewHelper;
	/** 编辑类型 */
	private String editType;
	/** 工作流日志表接口service */
	private SwfLogService swfLogService;
	/** 工作流service */
	private WorkFlowService workFlowService;
	/** 核心地址 */
	private String core_URL;
	private GeneralClaimViewHelper generalClaimViewHelper;

	/**
	 * 查勘处理
	 * @return 返回页面
	 * @throws Exception
	 */
	public String checkBeforeEdit() throws Exception {
		core_URL = AppConfig.get("sysconst.Core_URL");
		HttpServletRequest httpServletRequest = getRequest();
		HttpServletResponse httpServletResponse = getResponse();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		editType = httpServletRequest.getParameter("editType");
		String registNo = "";
		String checkNo = "";
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String riskCode = httpServletRequest.getParameter("riskCode");// 险种
		String scheduleCheckFlag = "true";
		String forward = "";// 向前
		String flowID = httpServletRequest.getParameter("swfLogFlowID");
		String logNo = httpServletRequest.getParameter("swfLogLogNo");
		UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();

		try {
			// 增被保险人联系电话
			registNo = httpServletRequest.getParameter("businessNo"); // 报案号
			if (registNo != null && registNo != "") {
				PrpLcheck prpLcheck = new PrpLcheck();
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.registNo", registNo);
				List<PrpLcheck> checkList = prpLcheckService.findPrpLcheck(queryRule);
				if (checkList.size() > 0) {
					prpLcheck.setCheckList(checkList);
					httpServletRequest.setAttribute("prpLcheck", prpLcheck);
				}
			}
			if ("EDIT".equals(editType)) {
				List<SwfLog> collection = this.getSwfLogService().findByConditions(" businessno='" + registNo + "'");
				if (collection != null && collection.size() > 0) {
					String flowid = collection.get(0).getId().getFlowID();
					List<SwfLog> collection2 = this.getSwfLogService().findByConditions(" flowid='" + flowid + "' and nodetype='compp' and nodestatus='4' order by logno desc");
					if (collection2 != null && collection2.size() > 0) {
						List<SwfLog> collection3 = this.getSwfLogService().findByConditions(" flowid='" + flowid + "' order by logno desc");
						if (collection3 != null && collection3.size() > 0) {
							for (SwfLog swfLogDto: collection3) {
								if ("compe".equals(swfLogDto.getNodeType()) && "4".equals(swfLogDto.getNodeStatus())) {
									throw new UserException(1, 3, "查勘修改", "該案件已理算提交不能再修改查勘信息");
								}
							}
						}
					}
				}
			}
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			if (editType.equals("ADD")) {
				// 查询立案信息,整理输入，用於初始界面显示
				registNo = httpServletRequest.getParameter("RegistNo"); // 报案号
				boolean checkGuideUser = generalClaimViewHelper.checkGuideUser(httpServletRequest, registNo, editType);
				PrpLclaim prpLclaim = null;
				List<PrpLclaim> tempClaimList = prpLclaimService.findByRegistNo(registNo);
				for (int temp = 0; temp < tempClaimList.size(); temp++) {
					prpLclaim = (PrpLclaim) tempClaimList.get(temp);
				}
				if (prpLclaim != null) {
					httpServletRequest.setAttribute("prpLclaim", prpLclaim);
				}
				// 增加对简易赔案的判断
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(registNo, httpServletResponse);
				if (blFwd) {
					return "";
				}
				if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
					riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
				}
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
				// ==================================================
				// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
				// 如果没有flowID和logno则不进行判断。
				
				if (flowID != null && logNo != null&&!checkGuideUser) {
					SwfLog swfLog = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
					if (swfLog.getHoldNode() == false) {
						String msg = "案件'" + registNo + "'已經被代碼:'" + swfLog.getHandlerCode() + "',名稱:'" + swfLog.getHandlerName() + "'的用戶所占用,請選擇其它案件進行處理!";
						throw new UserException(1, 3, "工作流", msg);
					}
					if ("check".equals(swfLog.getNodeType())) {
						if ("15".equals(swfLog.getTypeFlag()) || "certa".equals(swfLog.getNodeType()))
							scheduleCheckFlag = "false";
					}
				}
				httpServletRequest.setAttribute("scheduleCheckFlag", scheduleCheckFlag);
				daaCheckViewHelper.registDtoToView(httpServletRequest, registNo, editType);
			}
			if (editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("DELETE")) {
				// 查询查勘信息,整理输入，用於初始界面显示
				// reason:增加对简易赔案的判断
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(registNo, httpServletResponse);
				if (blFwd) {
					return "";
				}
				httpServletRequest.setAttribute("scheduleCheckFlag", scheduleCheckFlag);
				checkNo = httpServletRequest.getParameter("prpLcheckCheckNo"); // 查勘号
				if(editType.equals("EDIT")){
					boolean checkGuideUser = generalClaimViewHelper.checkGuideUser(httpServletRequest, checkNo, editType);
					if(!checkGuideUser){
						SwfLog swfLog = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
						if (swfLog.getHoldNode() == false) {
							String msg = "案件'" + registNo + "'已經被代碼:'" + swfLog.getHandlerCode() + "',名稱:'" + swfLog.getHandlerName() + "'的用戶所占用,請選擇其它案件進行處理!";
							throw new UserException(1, 3, "工作流", msg);
						}
					}
				}
				daaCheckViewHelper.checkDtoView(httpServletRequest, checkNo);
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
			}
			// 未处理查勘任务的放弃处理
			if (editType.equals("GIVUP")) {
				// 放弃未暂存和提交的查勘任务，删去结点操作人，使其他人可见可处理
				String FlowID = httpServletRequest.getParameter("swfLogFlowID");
				int LogNo = Integer.parseInt((String) httpServletRequest.getParameter("swfLogLogNo"));
				SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
				if (swfLogDto.getNodeType().equals("check")) {
					swfLogDto.setHandlerCode("");
					swfLogDto.setHandlerName("");
					swfLogDto.setFlowStatus("1");
				}
				this.getWorkFlowService().updateFlow(swfLogDto);
				this.clearErrorsAndMessages();
				this.addActionMessage(this.getText("button.giveUpTask.value"));
				this.addActionMessage(this.getText("prompt.queRegist.RegistNo"));
				this.addActionMessage(swfLogDto.getRegistNo());
				forward = "success";
				return forward;
			}
			// 已暂存查勘任务的放弃处理
			if (editType.equals("giveupTemporarySave")) {
				String FlowID = httpServletRequest.getParameter("swfLogFlowID");
				int LogNo = Integer.parseInt((String) httpServletRequest.getParameter("swfLogLogNo"));
				SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
				if (swfLogDto.getNodeType().equals("check")) {
					swfLogDto.setHandlerCode("");
					swfLogDto.setHandlerName("");
					swfLogDto.setNodeStatus("0");
					swfLogDto.setFlowStatus("1");
				}
				this.getWorkFlowService().updateFlow(swfLogDto);
				this.clearErrorsAndMessages();
				this.addActionMessage(this.getText("button.giveUpTask.value"));
				this.addActionMessage(this.getText("prompt.queRegist.RegistNo"));
				this.addActionMessage(swfLogDto.getRegistNo());
				forward = "success";
				return forward;
			}
			// 取得forward
			forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "check", editType, 1);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public DAACheckViewHelper getDaaCheckViewHelper() {
		return daaCheckViewHelper;
	}

	public void setDaaCheckViewHelper(DAACheckViewHelper daaCheckViewHelper) {
		this.daaCheckViewHelper = daaCheckViewHelper;
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

	public String getCore_URL() {
		return core_URL;
	}

	public void setCore_URL(String core_URL) {
		this.core_URL = core_URL;
	}

	public GeneralClaimViewHelper getGeneralClaimViewHelper() {
		return generalClaimViewHelper;
	}

	public void setGeneralClaimViewHelper(GeneralClaimViewHelper generalClaimViewHelper) {
		this.generalClaimViewHelper = generalClaimViewHelper;
	}
	
}
