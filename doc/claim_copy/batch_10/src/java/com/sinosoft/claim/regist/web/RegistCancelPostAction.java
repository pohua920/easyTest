package com.sinosoft.claim.regist.web;

import ins.framework.web.Struts2Action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrplregistrpolicyId;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 报案注销界面信息
 * <p>
 * Title: 车险报案注销界面信息
 * </p>
 * <p>
 * Description: 车险报案注销界面信息保存
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

public class RegistCancelPostAction extends Struts2Action {
	
	private static final long serialVersionUID = 1L;
	/**備案的viewHelper*/
	private DAARegistViewHelper daaRegistViewHelper;
	/** 備案基礎類的服務*/
	private RegistService registService;
	/** 立案基礎類的服務*/
	private ClaimService claimService;
	/** 關聯備案的服務*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 保單基礎類的服務*/
	private PolicyService policyService;
	/** 險種配置的服務*/
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流的viewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流的服務*/
	private WorkFlowService workFlowService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;

    /**
     * 备案注销
     * @return  页面类型
     * @throws Exception
     */
	public String registCancel() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String forward = ""; // 向前流转
		// 取得当前用户信息
		// 得到是否为全部注销的标记
		String allCancleFlag = StringUtils.rightTrim(httpServletRequest.getParameter("txtAllCancle"));
		// 检查界面结果，如果选择的保单的报案已经立过案的话，则不能进行注销。
		// 用viewHelper整理界面输入
		RegistDto registDto = daaRegistViewHelper.cancelViewToDto(httpServletRequest);
		WorkFlowDto workFlowDto = new WorkFlowDto();
		// 保存立案拒赔注销信息
		// 以下为工作流使用中的
		// 得到流程编号
		String swfLogFlowID = this.getWorkFlowService().findFlowIDByRegistNo(registDto.getPrpLregist().getRegistNo());
		int swfLogLogNo = 1; // 工作流logno黙认为1
		if (allCancleFlag.equals("1")) {
			// 需要检查是否有已经立案的，但是是正常的立案的情况。。。
			String strSql = "registNo='" + registDto.getPrpLregist().getRegistNo() + "' and canceldate is null";
			int llcount = claimService.getCount(strSql);
			if (llcount > 0) {
				String msg = getText("regist.have") + llcount + getText("prompt.registCancel.alreadyClaimedCanNotCancel");//保單已經立案，請做完此保單的立案註銷後，再進行報案的全部註銷！
				throw new UserException(1, 3, getText("prompt.registCancel.registCancel"), msg);//備案註銷
			}
			SwfLog regisSwfLog = this.getWorkFlowService().findByPrimaryKey(swfLogFlowID, swfLogLogNo);
			if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(regisSwfLog.getActorId()) != null) {
				workFlowDto = this.getJbpmWorkFlowDto(regisSwfLog);
			} else {
				workFlowDto = this.getWorkFlowDto(swfLogFlowID, swfLogLogNo);
//				if (workFlowDto.getUpdateSwfLog() != null) {
//					workFlowDto.getUpdateSwfLog().setNodeStatus("6"); // 撤消的流程
//				}
			}
		}
		// 如果只是简单注销一部分，的操作
		if (allCancleFlag.equals("0")) {
			// 根据保单号进行注销
			String[] policyNoList = httpServletRequest.getParameterValues("prpLclaimPolicyNo"); // 保单列表
			String[] selectToCancleList = httpServletRequest.getParameterValues("selectToCancle"); // 选择进行注销的开关
			registDto.getPrpLregist().setCancelDate(null);
			registDto.getPrpLregist().setDealerCode("");
			for (int index = 0; index < policyNoList.length; index++) {
				if (selectToCancleList[index].equals("1")) {
					// 暂时先注销一个保单吧。。以後再说。。。目前支持一个一个的注销。。暂时！！
					// 查询这个保单的
					String policyNo = policyNoList[index];
					PrplregistrpolicyId prplregistrpolicyId = new PrplregistrpolicyId();
					prplregistrpolicyId.setPolicyNo(policyNo);
					prplregistrpolicyId.setRegistNo(registDto.getPrpLregist().getRegistNo());
					Prplregistrpolicy prpLRegistRPolicy = prpLregistrpolicyService.findPrplregistrpolicy(prplregistrpolicyId);
					if (prpLRegistRPolicy != null) {
						prpLRegistRPolicy.setValidStatus("0");
					}
					registDto.setPrpLRegistRPolicy(prpLRegistRPolicy);
					// 工作流注销
					if (DataUtils.emptyToNull(swfLogFlowID) != null) {
						String strSql = "flowid='" + swfLogFlowID + "' and nodeType='claim' and policyNo='" + policyNo + "'";
						List<SwfLog> claimNodeList = this.getWorkFlowService().findNodesByConditions(strSql);
						if (claimNodeList != null && claimNodeList.size() > 0) {
							workFlowDto.setUpdate(true);
							workFlowDto.setUpdateSwfLog(claimNodeList.get(0));
							workFlowDto.getUpdateSwfLog().setNodeStatus("6");
						}
					}
					break;
				}
			}
		}
		// 保存报案注销信息並查找工作流程
		if (workFlowViewHelper.checkDealDto(workFlowDto)) {
			registService.saveRegistCancel(registDto, workFlowDto);
//			this.jbpmBusinessViewHelper.saveBusiness(registService, "saveRegistCancel", workFlowDto, registDto);
		} else {
			registService.saveRegistCancel(registDto, workFlowDto);
//			this.jbpmBusinessViewHelper.saveBusiness(registService, "saveRegistCancel", workFlowDto, registDto);
		}
		this.clearErrorsAndMessages();
		this.addActionMessage(getText("prompt.registcancel.save"));
		forward = "success";
		return forward;
	}
	/***
	 * 新工作流引擎处理备案注销任务
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getJbpmWorkFlowDto(SwfLog swfLog) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);
		workFlowDto.setUpdate(true);
		workFlowDto.setSubmit(true);
		workFlowDto.setCurrSwfLog(swfLog);
		Map<String,Object> paramMap = workFlowDto.getParamMap();
		paramMap.put("endFlag", "1");
		paramMap.put("nodeStatus", "6");
		workFlowDto.setParamMap(paramMap);
		JbpmDto jbpmDto = new JbpmDto();
		jbpmDto.setActorId("regis_cancel");
		jbpmDto.setBusinessId(swfLog.getBusinessId());
		jbpmDto.setProcessId(swfLog.getProcessId());
		jbpmDto.putParamsMap("endFlag", true);
		workFlowDto.setJbpmDto(jbpmDto);
		return workFlowDto;
	}
	/***
	 * 旧工作流引擎处理备案注销
	 * @param flowID
	 * @param logNo
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(String flowID,int logNo) throws Exception{
//		SwfLog swfLogDtoDealNode = new SwfLog();
//		if (DataUtils.emptyToNull(flowID)!=null) {
//			swfLogDtoDealNode.getId().setFlowID(flowID);
//			swfLogDtoDealNode.getId().setLogNo(logNo);
//		}
//		swfLogDtoDealNode.setNodeStatus("4");
//		swfLogDtoDealNode.setEndFlag("1");
//		UserDto user = (UserDto) super.getRequest().getSession().getAttribute("user");
//		return workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
	    WorkFlowDto workFlowDto = new WorkFlowDto();
        workFlowDto.setNewWorkFlow(false);
        workFlowDto.setUpdate(true);
        workFlowDto.setClose(true);
        SwfLog swfLog = new SwfLog(flowID , logNo);
        workFlowDto.setCurrSwfLog(swfLog);
        Map<String,Object> paramMap = workFlowDto.getParamMap();
        paramMap.put("nodeStatus", "6");
        workFlowDto.setParamMap(paramMap);
        workFlowDto.getFlowParamMap().put("endFlag", true);
        return workFlowDto;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
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
}