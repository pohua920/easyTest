package com.sinosoft.claim.verifyLoss.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.util.ClaimProgressViewHelper;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.verifyLoss.service.facade.VerifyLossService;
import com.sinosoft.claim.verifyLoss.util.DAAVerifyLossViewHelper;
import com.sinosoft.claim.verifyLoss.vo.VerifyLossDto;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;

import ins.framework.web.Struts2Action;

/**
 * @ClassName VerifyLossEditAction
 * @Description 车险理赔核损编辑界面
 * @author 中科软
 */
@SuppressWarnings("serial")
public class VerifyLossEditPostAction extends Struts2Action {
	/**操作执行後，显示的信息*/
	private String message;
	/**核损service*/
	private VerifyLossService verifyLossService;
	/**核损viewHelper*/
	private DAAVerifyLossViewHelper daaVerifyLossViewHelper;
	/**工作流viewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private WorkFlowService workFlowService;
	private ClaimProgressViewHelper claimProgressViewHelper;
	private BusinessViewHelper businessViewHelper;
	/**
	 * 核损提交、暂存
	 * @return 页面类型
	 * @throws Exception
	 */
	public String verifyLossEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = super.getRequest();
		String registNo = httpServletRequest.getParameter("prpLverifyLossRegistNo"); // 报案号
		String claimNo = httpServletRequest.getParameter("prpLverifyLossClaimNo"); // 赔案号
		httpServletRequest.setAttribute("registNo", registNo);
		httpServletRequest.setAttribute("claimNo", claimNo);
//		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		VerifyLossDto verifyLossDto = this.daaVerifyLossViewHelper.viewToDto(httpServletRequest);
		/** 需求變更085 理賠進度收集 begin */
		PrpLverifyLoss prpLverifyLoss = verifyLossDto.getPrpLverifyLoss();
		String nodeType = httpServletRequest.getParameter("nodeType");
		String lossItemCode = prpLverifyLoss.getId().getLossItemCode();;
		String lossItemName = prpLverifyLoss.getLossItemName();
		String policyNo = prpLverifyLoss.getPolicyNo();
		String taskCode = "";
		String taskObject = "";
		//01立案;02查勘;03定損;04核損;05人傷定損;06人傷核損;07財產定損;08財產核損;09理算
		if("propv".equals(nodeType)){
			taskCode = "08";
			taskObject = "財產核損";
		} else if("veriw".equals(nodeType)){
			taskCode = "06";
			taskObject = "人傷核損 - " + lossItemName;
		} else if("verif".equals(nodeType)){
			taskCode = "04";
			taskObject = ("1".equals(prpLverifyLoss.getInsureCarFlag()) ? "標的車" : "三者車" ) + "核損 - " + lossItemName;
		}
		verifyLossDto.setClaimProgressList(this.claimProgressViewHelper.getClaimProgressData(httpServletRequest, registNo, registNo, policyNo, taskCode, taskObject , Integer.parseInt(lossItemCode)));
		/** 需求變更085 理賠進度收集 end */
		WorkFlowDto workFlowDto = null;
		String actorId = httpServletRequest.getParameter("swfLogActorId");
		String businessNo = DataUtils.dbNullToEmpty(claimNo).length() > 0 ? claimNo : registNo;
		if(WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId))!=null){
			workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, false, null, businessNo, null, registNo, null,null);
		} else {
			//workFlowDto = this.getWorkFlowDto(verifyLossDto);
	          workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, false, null, businessNo, null, registNo, null,null);

		}
		String userMessage = "";
		// 如果提交的话，那么自己的状态还是2
		if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
			this.verifyLossService.save(verifyLossDto, workFlowDto);
//			this.getJbpmBusinessViewHelper().saveBusiness(verifyLossService,"save",workFlowDto, verifyLossDto);
//			user.setUserMessage(registNo);
		} else {
			this.verifyLossService.save(verifyLossDto);
//			user.setUserMessage(registNo + ";注意:沒有發現與工作流流程相關任何數據！！");
			userMessage = "注意:沒有發現與工作流流程相關任何數據！";
		}
		this.clearErrorsAndMessages();
		if ("4".equals(httpServletRequest.getParameter("buttonSaveType").trim())) {
			this.addActionMessage(super.getText("prompt.verifyLoss.submit"));
		} else if ("5".equals(httpServletRequest.getParameter("buttonSaveType").trim())) {
			this.addActionMessage(super.getText("prompt.verifyLoss.reject"));
		}else {
			this.addActionMessage(super.getText("prompt.verifyLoss.save"));
		}
		if (DataUtils.dbNullToEmpty(claimNo).length() > 0) {
			this.addActionMessage(super.getText("db.prpLclaim.claimNo"));
			this.addActionMessage(claimNo);
		}
		if (DataUtils.dbNullToEmpty(registNo).length() > 0) {
			this.addActionMessage(super.getText("db.prpLclaim.registNo"));
			this.addActionMessage(registNo);
		}
		if(!CommonUtils.isEmpty(userMessage)){
			this.addActionMessage(userMessage);
		}
		return SUCCESS;
	}

	/***
	 * 旧工作流引擎处理核损任务
	 * @param verifyLossDto
	 * @return
	 * @throws Exception 
	 */
	private WorkFlowDto getWorkFlowDto(VerifyLossDto verifyLossDto) throws Exception{
		HttpServletRequest request = super.getRequest();
		String nextHandlerCode = request.getParameter("nextHandlerCode");
		String nextHandlerName = request.getParameter("nextHandlerName");
		String flowID = request.getParameter("swfLogFlowID");
		String LogNo = request.getParameter("swfLogLogNo");
		String nodeType = request.getParameter("nodeType");
		String registNo = request.getParameter("prpLverifyLossRegistNo"); // 报案号
		String claimNo = request.getParameter("prpLverifyLossClaimNo"); // 赔案号
		int nextNodeNo = 0;
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.setLossItemCode(verifyLossDto.getPrpLverifyLoss().getId().getLossItemCode());
		swfLogDtoDealNode.setTypeFlag(verifyLossDto.getPrpLclaimStatus().getTypeFlag());
		swfLogDtoDealNode.setLossItemName(verifyLossDto.getPrpLverifyLoss().getLossItemName());
		// 保存是否保单车辆的标志
		swfLogDtoDealNode.setInsureCarFlag(verifyLossDto.getPrpLverifyLoss().getInsureCarFlag());
		swfLogDtoDealNode.setNodeType(nodeType);
		String statusTemp = request.getParameter("buttonSaveType");
		swfLogDtoDealNode.setNodeStatus(statusTemp);
		if (DataUtils.dbNullToEmpty(claimNo).length() > 0) {
			swfLogDtoDealNode.setBusinessNo(claimNo);
			swfLogDtoDealNode.setNextBusinessNo(claimNo);
		} else {
			swfLogDtoDealNode.setBusinessNo(registNo);
			swfLogDtoDealNode.setNextBusinessNo(registNo);
		}
		swfLogDtoDealNode.setKeyIn(registNo);
		swfLogDtoDealNode.setKeyOut(registNo);
		swfLogDtoDealNode.getId().setFlowID(flowID);
		swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(LogNo)));
		// 因为有条件限制，所以有条件的业务主键传入
		swfLogDtoDealNode.setConditionBusinessNo(registNo);
		// 因为人到人的原因
		if (swfLogDtoDealNode.getNodeStatus().equals("4")) {
			nextNodeNo = Integer.parseInt(request.getParameter("nextNodeNo"));
			// 可增加回勘的下一个节点
			List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
			SwfLog swfLogNextNode = new SwfLog();
			if (verifyLossDto.getPrpLcarLossList() != null && verifyLossDto.getPrpLcarLossList().size() > 0 && (!nodeType.equals("backc"))) {
				PrpLcarLoss prpLcarLossDto = (PrpLcarLoss) verifyLossDto.getPrpLcarLossList().get(0);
				if (prpLcarLossDto.getBackCheckFlag().equals("1")) {
					// 需要增加修复验车
					swfLogNextNode = new SwfLog();
					swfLogNextNode.setNodeNo(0);
					swfLogNextNode.setNodeType("backc");
					nextNodeList.add(swfLogNextNode);
				}
			}
			if (nextNodeList.size() > 0) {
				swfLogDtoDealNode.setSwfLogList(nextNodeList);
				swfLogDtoDealNode.setNextNodeListType("1");
			}
			swfLogDtoDealNode.setNodeNo(nextNodeNo);
			swfLogDtoDealNode.setHandlerCode(nextHandlerCode);
			swfLogDtoDealNode.setHandlerName(nextHandlerName);
		}
		// 退回，提交定损，並且设置为定损的回退标志
		if (swfLogDtoDealNode.getNodeStatus().equals("5")) {
			if (nodeType != null && nodeType.trim().equals("verif")) {
				// 设置要退回的节点
				swfLogDtoDealNode.setNodeType("certa");
				swfLogDtoDealNode.setBusinessType("verif");
				swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定要回退的节点，如果不是，就是swflog表中寻找回退的节点
			}else if(nodeType != null && nodeType.trim().equals("veriw")){
				// 设置要退回的节点
				swfLogDtoDealNode.setNodeType("wound");
				swfLogDtoDealNode.setBusinessType("veriw");
				swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定要回退的节点，如果不是，就是swflog表中寻找回退的节点
			}else if(nodeType != null && nodeType.trim().equals("propv")){
				// 设置要退回的节点
				swfLogDtoDealNode.setNodeType("propc");
				swfLogDtoDealNode.setBusinessType("propv");
				swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定要回退的节点，如果不是，就是swflog表中寻找回退的节点
			}
		}
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VerifyLossService getVerifyLossService() {
		return verifyLossService;
	}

	public void setVerifyLossService(VerifyLossService verifyLossService) {
		this.verifyLossService = verifyLossService;
	}

	public DAAVerifyLossViewHelper getDaaVerifyLossViewHelper() {
		return daaVerifyLossViewHelper;
	}

	public void setDaaVerifyLossViewHelper(DAAVerifyLossViewHelper daaVerifyLossViewHelper) {
		this.daaVerifyLossViewHelper = daaVerifyLossViewHelper;
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
