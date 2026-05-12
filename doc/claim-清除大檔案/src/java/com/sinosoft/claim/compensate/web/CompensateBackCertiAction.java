package com.sinosoft.claim.compensate.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 理算退回单证
 * @Description 
 * @author 中科软
 */
public class CompensateBackCertiAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 理算处理接口 */
	private CompensateService compensateService;
	/** 工作流处理接口 */
	private SwfLogService swfLogService;
	/** 工作流数据整理工具类 */
	private WorkFlowViewHelper workFlowViewHelper;
	private WorkFlowService workFlowService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private PrpCmainService prpCmainService;
	private CodeService codeService;
	private BusinessViewHelper businessViewHelper;
	/**
	 * 理算退回单证处理
	 * @return
	 * @throws Exception
	 */
	public String compensateBackCerti() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		try {
			String flowId = request.getParameter("swfLogFlowID");
			String logNo = request.getParameter("swfLogLogNo");
			SwfLog swfLog = this.getSwfLogService().findSwfLog(flowId, Integer.parseInt(logNo));
			PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(swfLog.getPolicyNo());
			String strRiskType = codeService.translateRiskCodetoRiskType(swfLog.getRiskCode());
			if(prpCmain!=null && "Q".equals(strRiskType) && "3".equals(prpCmain.getCoinsFlag())){
				//理算退回	保單的業務來源是22-分進業務，簡易流程理算不可退回單證。
				throw new UserException(-1, 0, getText("prompt.compensate.back"), getText("prompt.compensate.policyBusiness22"));
			}
			WorkFlowDto workFlowDto = null;
			if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(swfLog.getActorId()) != null) {
				workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowBack(getRequest(), swfLog);
				workFlowDto.getJbpmDto().putParamsMap("certiFlag", true);
			} else {
				//workFlowDto = this.getWorkFlowDto();
			    workFlowDto = this.businessViewHelper.getWorkFlowBack(request, swfLog);
			}
			this.getWorkFlowService().deal(workFlowDto);
//			this.jbpmBusinessViewHelper.saveWorkFlow(workFlowDto);
			this.addActionMessage(super.getText("compe.back.success"));
		} catch (UserException e) {
			e.printStackTrace();
			throw e;
		} catch (ProcessTokenException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, getText("prompt.compensate.back"), e.getMessage());//理算退回
		}
		return SUCCESS;
	}
	/***
	 * 旧工作流处理理算退回单证任务
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto() throws Exception{
		HttpServletRequest request = super.getRequest();
		String flowId = request.getParameter("swfLogFlowID");
		String logNo = request.getParameter("swfLogLogNo");
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.getId().setFlowID(flowId);
		swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(logNo)));
		swfLogDtoDealNode.setNodeStatus("5");
		swfLogDtoDealNode.setNodeType("certi");// 设置要退回的节点
		swfLogDtoDealNode.setBusinessType("compe");
		swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定要回退的节点，如果不是，就是swflog表中寻找回退的节点
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
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

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}
	
	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}
	
	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}
	
	public CodeService getCodeService() {
		return codeService;
	}
	
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }
    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
