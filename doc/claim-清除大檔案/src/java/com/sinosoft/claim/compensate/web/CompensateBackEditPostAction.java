package com.sinosoft.claim.compensate.web;

import ins.framework.common.DateTime;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 车险理赔实赔退回定损保存
 * <p>
 * Title: 车险理赔实赔编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔实赔编辑界面信息
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class CompensateBackEditPostAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private WorkFlowService workFlowService;
	private CompensateService compensateService;
	private WorkFlowViewHelper workFlowViewHelper;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private BusinessViewHelper businessViewHelper;

	public String compensateBackEditPost() throws Exception {
		// 程序思路:
		// ---------------------------------------------------
		// 如果是第一次保存，只要能将状态变成正在处理就行了。。
		// 其他就是在正在处理的状态栏里进行处理了。
		// ---------------------------------------------------
		// 取赔款计算书号
		HttpServletRequest httpServletRequest = super.getRequest();
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logNo
		String claimNo = httpServletRequest.getParameter("prpLverifyLossClaimNoShow"); // claimNo
		String compensateOpinion = httpServletRequest.getParameter("compensateOpinion");
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		if (swfLog == null) {
			//工作流		未查詢到需要處理的工作流編碼
			throw new UserException(1, 3, getText("prompt.certify.workFlow"), getText("prompt.compensate.notFoundWorkFlowCode"));
		}
		if (Integer.parseInt(DataUtils.nullToZero(swfLog.getNodeStatus())) > 3) {//
			//工作流		請不要重複提交
			throw new UserException(1, 3, getText("prompt.certify.workFlow"), getText("prompt.regist.multipleSubmit"));
		}
		WorkFlowDto workFlowDto = null;
		if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(swfLog.getActorId()) != null) {
			workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowBack(getRequest(), swfLog);
			workFlowDto.getJbpmDto().putParamsMap("certaFlag", true);
		} else {
			//workFlowDto = this.getWorkFlowDto();
			workFlowDto = this.businessViewHelper.getWorkFlowBack(getRequest(), swfLog);
            if(CommonUtils.isEmpty(workFlowDto.getBackSwfLogList())){
                throw new UserException(1, 3, getText("prompt.certify.workFlow"), "未找到可退回的定損任務！");
            }
		}
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setCompensateApproverCode(user.getUserCode());
		prpLverifyLoss.setCompensateBackDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
		prpLverifyLoss.setCompensateOpinion(compensateOpinion);
		prpLverifyLoss.setFlowID(swfLog.getId().getFlowID());
		this.getCompensateService().backToCerta(claimNo, prpLverifyLoss, workFlowDto);
//		this.jbpmBusinessViewHelper.saveWorkFlow(compensateService, "backToCerta", workFlowDto,claimNo,prpLverifyLoss);
		this.clearErrorsAndMessages();
		this.addActionMessage(getText("prompt.compensate.backSuccess"));//理算回退任務訊息儲存成功
		this.addActionMessage(super.getText("db.prpLclaim.claimNo"));
		this.addActionMessage(claimNo);
		return SUCCESS;
	}

	/***
	 * 旧工作流处理理算退回定损任务
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto() throws Exception {
		HttpServletRequest request = super.getRequest();
		String flowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String logNo = request.getParameter("swfLogLogNo"); // 工作流logNo
		String lossItemCode[] = request.getParameterValues("lossitemCode"); // lossitemCode
		String nodeType[] = request.getParameterValues("nodeType"); // nodeType
		String checked[] = request.getParameterValues("selectCerta"); // lossitemCode
		String policyNo = request.getParameter("prpLverifyLossPolicyNoShow");
		// 将计算书删除和工作流操作放在一个workFlow事务操作中
		// 模拟操作的提交，提交到不同的定损，但是最後将查勘内容替换成理算的内容
		// 並设置当前需要到达的节点为所要退回的定损
		SwfLog swfLogDealNode = new SwfLog();
		swfLogDealNode.getId().setFlowID(flowID);
		swfLogDealNode.getId().setLogNo(Integer.parseInt(logNo));
		swfLogDealNode.setNodeStatus("5");
		// 计算列表
		List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
		String conditions = "";
		String nowNodeType = "";
		String nowLossItemCode = "";
		for (int i = 1; i < checked.length; i++) {
			nowNodeType = DataUtils.nullToEmpty(nodeType[i]);
			nowLossItemCode = DataUtils.nullToEmpty(lossItemCode[i]);
			if (checked[i].equals("1")) {
				conditions = " flowId='" + flowID + "' and nodeType='" + nowNodeType + "' and lossItemCode='" + nowLossItemCode + "' order by logNo desc";
				// 取得定损的最後一次的操作工作流节点
				List<SwfLog> swfLogTempList = this.getWorkFlowService().findNodesByConditions(conditions);
				if (swfLogTempList == null || swfLogTempList.size() < 1)
					continue;
				SwfLog swfLogNextNode = swfLogTempList.iterator().next();
				// 关联报案，单证提交後，交强险退回到唯一的标的定损，在定损环节申请注销，却把商业险注销了
				swfLogNextNode.setPolicyNo(policyNo);
				swfLogNextNode.setRiskCode(swfLogTempList.get(0).getRiskCode());
				nextNodeList.add(swfLogNextNode);
			}
		}
		if (nextNodeList.size() > 0) {
			swfLogDealNode.setNextNodeListType("1");
			swfLogDealNode.setSwfLogList(nextNodeList);
		}
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDealNode);
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
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

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
