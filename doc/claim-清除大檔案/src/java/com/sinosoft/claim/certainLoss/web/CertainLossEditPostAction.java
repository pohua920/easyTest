package com.sinosoft.claim.certainLoss.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.util.DAACertainLossViewHelper;
import com.sinosoft.claim.certainLoss.vo.CertainLossDto;
import com.sinosoft.claim.claim.util.ClaimProgressViewHelper;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 车险理赔定损编辑界面
 * <p>
 * Title: 车险理赔定损编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔定损编辑界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * <p>
 * author: 中科软
 * </p>
 */
public class CertainLossEditPostAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 定损viewHelper */
	private DAACertainLossViewHelper daaCertainLossViewHelper;
	/** 定损业务处理service */
	private CertainLossService certainLossService;
	/** 报案接口service */
	private PrpLregistService prpLregistService;
	/** 基础配置service */
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流整理viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	private WorkFlowService workFlowService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private ClaimProgressViewHelper claimProgressViewHelper;
	private BusinessViewHelper businessViewHelper;
	/**
	 * <b>function: 定损暂存，提交</b>
	 * @Description:
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */
	public String certainLossEditPost() throws Exception {
		HttpServletRequest request = super.getRequest();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String riskCodeTemp = request.getParameter("prpLverifyLossRiskCode");
		String checkInput = request.getParameter("checkInput"); // 工作流logno
		if (riskCodeTemp != null && riskCodeTemp.trim().length() > 0) {
			user.setRiskCode(riskCodeTemp);
		}
		// 以下是业务使用
		String registNo = request.getParameter("prpLverifyLossRegistNo"); // 报案号
		String claimNo = request.getParameter("prpLverifyLossClaimNo"); // 赔案号
//		String lossItemCode = request.getParameter("prpLverifyLossLossItemCode"); // 损失代码
		String prpLverifyLossLossNodeType = request.getParameter("prpLverifyLossLossNodeType"); // 损失代码
		// 是否需要核价标志
		// 防止重复提交--------------------------------------------------------------------
		String strLastAccessedTime = "" + request.getSession().getLastAccessedTime() / 1000;
		String oldLastAccessedTime = (String) request.getSession().getAttribute("oldCertainLossLastAccessedTime");
		this.clearErrorsAndMessages();
		if (oldLastAccessedTime == null || oldLastAccessedTime.trim().equals("")) {
			try {
				request.getSession().setAttribute("oldCertainLossLastAccessedTime", strLastAccessedTime);
				request.setAttribute("registNo", registNo);
				request.setAttribute("claimNo", claimNo);
				CertainLossDto certainLossDto = this.daaCertainLossViewHelper.viewToDto(request);
				/** 需求變更085 理賠進度收集 begin */
				PrpLverifyLoss prpLverifyLoss = certainLossDto.getPrpLverifyLoss();
				String nodeType = request.getParameter("nodeType");
				String lossItemCode = prpLverifyLoss.getId().getLossItemCode();;
				String lossItemName = prpLverifyLoss.getLossItemName();
				String policyNo = prpLverifyLoss.getPolicyNo();
				String taskCode = "";
				String taskObject = "";
				//01立案;02查勘;03定損;04核損;05人傷定損;06人傷核損;07財產定損;08財產核損;09理算
				if("propc".equals(nodeType)){
					taskCode = "07";
					taskObject = "財產定損";
				} else if("wound".equals(nodeType)){
					taskCode = "05";
					taskObject = "人傷定損 - " + lossItemName;
				} else if("certa".equals(nodeType)){
					taskCode = "03";
					taskObject = ("1".equals(prpLverifyLoss.getInsureCarFlag()) ? "標的車" : "三者車" ) + "定損 - " + lossItemName;
				}
				certainLossDto.setClaimProgressList(this.claimProgressViewHelper.getClaimProgressData(request, registNo, registNo, policyNo, taskCode, taskObject , Integer.parseInt(lossItemCode)));
				/** 需求變更085 理賠進度收集 end */
				if (checkInput != null && checkInput.equals("true")) {
					if ("certa".equals(prpLverifyLossLossNodeType)) {
						this.certainLossService.save(certainLossDto);
					} else {
						this.certainLossService.save(certainLossDto);
					}
					// 只保存定损信息，不用管工作的情况
				} else {
					WorkFlowDto workFlowDto = null;
					String actorId = request.getParameter("swfLogActorId");
					if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
						workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, false, null, null, null, registNo, null, null);
					} else {
						//workFlowDto = this.getWorkFlowDto(certainLossDto);
					    workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, false, null, null, null, registNo, null, null);
					}
					// 保存定损信息
					String userMessage = "";
					if (workFlowViewHelper.checkDealDto(workFlowDto)) {
						certainLossService.save(certainLossDto, workFlowDto);
//						this.jbpmBusinessViewHelper.saveBusiness(certainLossService,"save", workFlowDto, certainLossDto);
						// 报案主表回写
						PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
						String comCode = prpLregist.getComCode().substring(0, 2);
						PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
						if (prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) {
							String advanceType = request.getParameter("prplregistAdvance");
							prpLregist.setAdvanceType(advanceType);
							this.prpLregistService.save(prpLregist);
						}
					} else {
						if ("certa".equals(prpLverifyLossLossNodeType)) {
							this.certainLossService.save(certainLossDto);
						} else {
							this.certainLossService.save(certainLossDto);
						}
						// 报案主表回写
						PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
						String comCode = prpLregist.getComCode().substring(0, 2);
						PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(comCode, prpLregist.getRiskCode(), "advance_case");
						if (prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) {
							String advanceType = request.getParameter("prplregistAdvance");
							prpLregist.setAdvanceType(advanceType);
							this.prpLregistService.save(prpLregist);
						}
						userMessage = "注意:沒有發現與工作流流程相關任何數據！！";
					}
					this.clearErrorsAndMessages();
					if ("4".equals(request.getParameter("buttonSaveType"))) {
						this.addActionMessage(super.getText("prompt.certainLoss.submit"));
					} else {
						this.addActionMessage(super.getText("prompt.certainLoss.save"));
					}
					if (DataUtils.dbNullToEmpty(claimNo).length() > 0) {
						this.addActionMessage(super.getText("db.prpLclaim.claimNo"));
						this.addActionMessage(claimNo);
					}
					if (DataUtils.dbNullToEmpty(registNo).length() > 0) {
						this.addActionMessage(super.getText("db.prpLclaim.registNo"));
						this.addActionMessage(registNo);
					}
					if (!CommonUtils.isEmpty(userMessage)) {
						this.addActionMessage(userMessage);
					}
				}
			} catch (ProcessTokenException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionMessage("數據錯誤，錯誤原因:" + e.getMessage());
			}
		} else {
			throw new UserException(1, 3, "工作流", "請不要重複提交");
		}
		return "success";
	}

	/***
	 * 旧工作流引擎处理定损任务
	 * @param certainLossDto
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(CertainLossDto certainLossDto) throws Exception {
		// 进行与工作流有关的操作
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
		// 判断是否是直接从0待处理到提交，如果是则先进行业务保存操作 则从0->3
		// ,从3->4这样的状态变更,由於後来变成人到人的方式，所以去掉以下的代码
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("prpLverifyLossRegistNo"); // 报案号
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setSwfNotionList((ArrayList<SwfNotion>) certainLossDto.getSwfNotionList());
		// 定损的工作流设置，比较特殊
		SwfLog swfLogDtoDealNode = new SwfLog();
		if (!"".equals(DataUtils.dbNullToEmpty(swfLogFlowID)) && !"".equals(DataUtils.dbNullToEmpty(swfLogLogNo))) {
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		}
		swfLogDtoDealNode.setBusinessNo(registNo);
		swfLogDtoDealNode.setNextBusinessNo(registNo);
		String statusTemp = request.getParameter("buttonSaveType");
		swfLogDtoDealNode.setNodeStatus(statusTemp);
		swfLogDtoDealNode.setKeyIn(registNo);
		swfLogDtoDealNode.setKeyOut(registNo);
		swfLogDtoDealNode.setConditionBusinessNo(registNo);
		// 判断如果是理算退回的定损，並且该定损没有新增加数据，那么可以直接提交回理算的。
		// 相应的问题，如果理算处，以上信息都没完成，是不可以进行出理算书的。
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		// 理算退回的定损，並且是没有增加任何记录的标记，如果为1，说明，可以直接提交到理算去。
		String NextComeBackCompensate = request.getParameter("NextComeBackCompensate");
		workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
		// reason:增加判断如果是提交操作的话，到理算的时候，要换成是立案号的
		if ("1".equals(NextComeBackCompensate) && workFlowDto.getSubmitSwfLogList() != null && workFlowDto.getSubmitSwfLogList().size() > 0) {
			// 设置businessNo,KeyIn的值
			workFlowDto.getSubmitSwfLogList().get(0).setKeyIn(registNo);
			workFlowDto.getSubmitSwfLogList().get(0).setBusinessNo(registNo);
		}
		return workFlowDto;
	}
	
	public DAACertainLossViewHelper getDaaCertainLossViewHelper() {
		return daaCertainLossViewHelper;
	}

	public void setDaaCertainLossViewHelper(DAACertainLossViewHelper daaCertainLossViewHelper) {
		this.daaCertainLossViewHelper = daaCertainLossViewHelper;
	}

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public ClaimProgressViewHelper getClaimProgressViewHelper() {
		return claimProgressViewHelper;
	}

	public void setClaimProgressViewHelper(ClaimProgressViewHelper claimProgressViewHelper) {
		this.claimProgressViewHelper = claimProgressViewHelper;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
