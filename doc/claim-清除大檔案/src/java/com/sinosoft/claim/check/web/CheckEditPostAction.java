package com.sinosoft.claim.check.web;

import ins.framework.web.Struts2Action;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certainLoss.util.DAACertainLossViewHelper;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.util.DAACheckViewHelper;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.generalClaim.util.GeneralClaimViewHelper;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 分发HTTP Post 车险理赔查勘编辑界面
 * <p>
 * Title: 车险理赔查勘编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔查勘编辑界面信息
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
public class CheckEditPostAction extends Struts2Action {

	/** serialVersionUID */
	private static final long serialVersionUID = 4233975002946039531L;
	/** 报案Service */
	private RegistService registService;
	/** 调度Service */
	private ScheduleService scheduleService;
	/** 查勘Service */
	private CheckService checkService;
	/** 车险定损ViewHelper */
	private DAACertainLossViewHelper daaCertainLossViewHelper;
	/** 车险查勘ViewHelper */
	private DAACheckViewHelper daaCheckViewHelper;
	/** 生成单号的service */
	private BillService billService;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流service */
	private WorkFlowService workFlowService;
	/** 工作流日志表接口service */
	private SwfLogService swfLogService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	/** 代码翻译service */
	private CodeService codeService;
	private GeneralClaimViewHelper generalClaimViewHelper;

	private BusinessViewHelper businessViewHelper;
	/**
	 * 提交查勘信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String checkEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = getRequest();
		String forward = ""; // 向前流转
		// 查勘号
		String checkNo = httpServletRequest.getParameter("prpLcheckRegistNo");// 查勘号
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String riskCode = httpServletRequest.getParameter("prpLcheckRiskCode");
		String insureCarFlag = httpServletRequest.getParameter("prpLcheckInsureCarFlag"); // 是否为本保单车辆
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String checkGuideUser = httpServletRequest.getParameter("checkGuideUser"); // 是否代查勘
		// 是否是三着车，如果是三者车，不保存任何查勘的信息，只是把工作流送到定损就可以了
		try {
			if (checkNo == null || checkNo.length() < 1 || checkNo.trim().equals("")) {
				String tableName = "prplcheck";
				String prpLcheckLossPolicyNo = httpServletRequest.getParameter("prpLcheckPolicyNo");
				String prpLcheckDamageCode = httpServletRequest.getParameter("prpLcheckDamageCode");
				Map<String,Object> infoMap = new HashMap<String,Object>();
				infoMap.put("damageCode", prpLcheckDamageCode);
				infoMap.put("policyNo", prpLcheckLossPolicyNo);
				checkNo = billService.getNoByPolciyYear(tableName, riskCode,infoMap);
			}
			httpServletRequest.setAttribute("checkNo", checkNo);
			httpServletRequest.setAttribute("registNo", checkNo);
			// 用viewHelper整理界面输入
			// 区分是三者，或者是主车的内容进行分别的数据整理
			CheckDto checkDto = daaCheckViewHelper.viewToDto(httpServletRequest);
			CertainLossDto certainLossDto = daaCertainLossViewHelper.viewToDto(httpServletRequest);
			WorkFlowDto workFlowDto = null;
			String actorId = httpServletRequest.getParameter("swfLogActorId");
			if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
				workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, false, null, null, null, checkNo, null, null);
			} else {
				//workFlowDto = this.getWorkFlowDto(checkDto, checkNo);
	             workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, false, null, null, null, checkNo, null, null);
			}
			String userMessage = checkNo;
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				checkService.save(checkDto, certainLossDto, workFlowDto);
				if("true".equals(checkGuideUser)){
					generalClaimViewHelper.saveGuideUser(httpServletRequest, checkNo, null);
				}
//				this.getJbpmBusinessViewHelper().saveBusiness(checkService,"save", workFlowDto, checkDto, certainLossDto);
				//user.setUserMessage(checkNo);
				// 查勘後直接立案
				boolean checkPower = false;
				PrpDuserDto prpDuserDto = new PrpDuserDto();
				prpDuserDto.setUserCode(user.getUserCode());
				prpDuserDto.setComCode(user.getComCode());
				prpDuserDto.setValidStatus("1");
				prpDuserDto.setLoginComCode(user.getComCode());
				prpDuserDto.setCurrentRiskCode(user.getRiskCode());
				checkPower = UIPowerAction.checkPowerReturn(prpDuserDto, ICollections.TASK_CLAIM_CLAIM_INSERT);
				if (checkPower) {
					httpServletRequest.setAttribute("RegistNo", checkNo);
					httpServletRequest.setAttribute("swfLogFlowID", swfLogFlowID);
					String conditions = "FLOWID = '" + swfLogFlowID + "' AND NODETYPE = 'claim'";
					List<SwfLog> swfLogList = this.getWorkFlowService().findByConditions(conditions);
					SwfLog swfLogDto = null;
					if (swfLogList != null && swfLogList.size() > 0) {
						swfLogDto = swfLogList.get(0);
					}
					httpServletRequest.setAttribute("riskCode", riskCode);
					String policyNo = checkDto.getPrpLcheck().getPolicyNo();
					httpServletRequest.setAttribute("policyNo", policyNo);
					if (swfLogDto != null) {
						httpServletRequest.setAttribute("modelNo", String.valueOf(swfLogDto.getModelNo()));
						httpServletRequest.setAttribute("logno", String.valueOf(swfLogDto.getId().getLogNo()));
						httpServletRequest.setAttribute("nodeNo", String.valueOf(swfLogDto.getNodeNo()));
					}
				}
			} else {
				if (insureCarFlag.equals("1")){
					checkService.save(checkDto, certainLossDto);
				}
				userMessage += " 注意:沒有發現與工作流流程相關任何數據！";
			}
			httpServletRequest.setAttribute("prpLcheckDto", checkDto.getPrpLcheck());
			// 意健险和非意健险在查勘环节提示不同的信息
			this.clearErrorsAndMessages();
			String riskType = codeService.translateRiskCodetoRiskType(riskCode);
			if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
				if (httpServletRequest.getParameter("buttonSaveType").trim().equals("4")) {
					this.addActionMessage(getText("prompt.accicheck.submit"));
				} else {
					this.addActionMessage(getText("prompt.accicheck.save"));
				}
			} else {
				if (httpServletRequest.getParameter("buttonSaveType").trim().equals("4")) {
					this.addActionMessage(getText("prompt.check.submit"));
				} else {
					this.addActionMessage(getText("prompt.check.save"));
				}
			}
			this.addActionMessage(getText("db.prpLregist.registNo"));
			if (!CommonUtils.isEmpty(userMessage)) {
				this.addActionMessage(userMessage);
			}
			// 更新报案主表事故管界
			PrpLregist prpLregist = registService.findByPrimaryKeyForPrpLRegist(checkNo);
			prpLregist.setSection(httpServletRequest.getParameter("prpLregistSection"));
			prpLregist.setSectionName(httpServletRequest.getParameter("prpLregistSectionName"));
			registService.updatePrpLRegist(prpLregist);
			forward = "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}
	
	/***
	 * 旧工作流引擎处理查勘任务
	 * @param checkDto
	 * @param checkNo
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(CheckDto checkDto,String checkNo) throws Exception{
		HttpServletRequest request = super.getRequest();
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String activeSchedule = request.getParameter("messageToScheduleCheck"); // 通知调度，使调度工作流变成待处理状态
		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
		// 工作流处理过程
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
		SwfLog swfLogDtoDealNode = new SwfLog();
		if (swfLogFlowID != null && swfLogLogNo != null) {
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		} else {
			swfLogDtoDealNode.setNodeType("check");
			swfLogDtoDealNode.setBusinessNo(checkDto.getPrpLcheck().getId().getRegistNo());
		}
		swfLogDtoDealNode.setNodeStatus(checkDto.getPrpLclaimStatus().getStatus());
		swfLogDtoDealNode.setNextBusinessNo(checkDto.getPrpLcheck().getId().getRegistNo());
		swfLogDtoDealNode.setKeyOut(checkNo);
		// 回访是查勘提交过来的回访,没有typeflag的值,1表示查勘回访，2表示定损回访
		swfLogDtoDealNode.setTypeFlag("1");
		swfLogDtoDealNode.setKeyIn(checkDto.getPrpLcheck().getId().getRegistNo());
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
		// 不通知调度
		if ("4".equals(swfLogDtoDealNode.getNodeStatus()) && "0".equals(activeSchedule) && workFlowDto.getUpdateSwfLog() != null) {
			// 如果为查勘提交，则判断是不是需要通知调度的选项, activeSchedule.equals(
			// "0")说明不需要通知调度的
			// 虽然不需要通知，但是需要判断是否所有的调度都已经做完了，如果做完了，则需要结束调度任务
			String flowID = workFlowDto.getUpdateSwfLog().getId().getFlowID();
			// 查找没有完成的调度，比如正在处理的调度
			String conditonss = "flowId='" + flowID + "' and nodeType='sched' and nodestatus<4";
			List<SwfLog> schedList = this.getWorkFlowService().findNodesByConditions(conditonss);
			if (schedList != null && schedList.size() > 0) {
				String strSql = " registno='" + checkDto.getPrpLcheck().getId().getRegistNo() + "' and surveyTimes<1";
				Collection<PrpLscheduleItem> scheduleItemList = scheduleService.findItemByConditions(strSql);
				if (scheduleItemList != null && scheduleItemList.size() > 0) {
					// 没有完成调度呢
					workFlowDto.setUpdateSwfLog2(null);
				} else {
					// 如果查勘新增加了调度的数据呢？ 那也不能结束的
					// 如果调度增加标的，还会引起调度提交操作
					if (checkDto.getPrpLscheduleItemList() == null || checkDto.getPrpLscheduleItemList().size() == 0) {
						SwfLog swfLogDto2 = new SwfLog();
						swfLogDto2 = schedList.iterator().next();
						swfLogDto2.setNodeStatus("4");
						workFlowDto.setUpdateSwfLog2(swfLogDto2);
					}
				}
			}
		}
		return workFlowDto;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public DAACertainLossViewHelper getDaaCertainLossViewHelper() {
		return daaCertainLossViewHelper;
	}

	public void setDaaCertainLossViewHelper(DAACertainLossViewHelper daaCertainLossViewHelper) {
		this.daaCertainLossViewHelper = daaCertainLossViewHelper;
	}

	public DAACheckViewHelper getDaaCheckViewHelper() {
		return daaCheckViewHelper;
	}

	public void setDaaCheckViewHelper(DAACheckViewHelper daaCheckViewHelper) {
		this.daaCheckViewHelper = daaCheckViewHelper;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public GeneralClaimViewHelper getGeneralClaimViewHelper() {
		return generalClaimViewHelper;
	}

	public void setGeneralClaimViewHelper(
			GeneralClaimViewHelper generalClaimViewHelper) {
		this.generalClaimViewHelper = generalClaimViewHelper;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
