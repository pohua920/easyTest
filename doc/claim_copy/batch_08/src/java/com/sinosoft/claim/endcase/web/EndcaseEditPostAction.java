package com.sinosoft.claim.endcase.web;

import ins.framework.web.Struts2Action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.bl.facade.BLMailSenderFacade;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.util.DAAEndcaseViewHelper;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.payment.payment.model.PrpJpayRefRec;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 车险理赔结案编辑界面
 * <p>
 * Title: 车险理赔结案编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔结案编辑界面信息
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
public class EndcaseEditPostAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/**结案处理数据收集*/
	private DAAEndcaseViewHelper daaEndcaseViewHelper;
	/**结案服务*/
	private EndcaseService endcaseService;
	/**立案主表服务*/
	private PrpLclaimService prpLclaimService;
	/**赔案保单关联服务*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/**重开赔案服务*/
	private PrpLrecaseService prpLrecaseService;
	/**立案服务*/
	private ClaimService claimService;
	/**结案处理数据收集*/
	private PrpDriskConfigService prpDriskConfigService;
	/**结案处理数据收集*/
	private WorkFlowViewHelper workFlowViewHelper;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private WorkFlowService workFlowService;
	private BusinessViewHelper businessViewHelper;
	/**
	 * 结案提交处理
	 * @return
	 * @throws Exception
	 */
	public String endcaseEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		String forward = ""; // 向前流转
		String strStep = request.getParameter("step"); // 页面标志
		String dfFlag = request.getParameter("dfFlag"); // 页面标志（特殊赔案：垫支付）
		String claimNo = request.getParameter("prpLendcaseClaimNo1"); // 赔案号
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		// （特殊赔案：垫支付）
		if (dfFlag != null && !dfFlag.equals("") && dfFlag.equals("Y")) {
		} else {
			// （特殊赔案：垫支付）
			int intCompensateFlag = this.daaEndcaseViewHelper.checkCompensate(request, claimNo);
			request.setAttribute("intCompensateFlag", Integer.toString(intCompensateFlag));
			if (intCompensateFlag == 0) { // 有问题，如果是垫付，不应该有计算书的，这个是怎么考虑的。
				throw new UserException(1, 3, "endcase", "該立案" + claimNo + "不存在賠款計算書，無法結案！");
			}
			if (intCompensateFlag < 0) { // 有计算书，但没通过核赔。
				throw new UserException(1, 3, "endcase", "該賠案尚有未核賠通過的計算書，無法結案！");
			}
		}
		// 判断是否存在未摊回的赔款 begin
		List<PrpJpayRefRec> ve = this.daaEndcaseViewHelper.checkCoinsFlag(request, claimNo);
		if (ve != null && !ve.isEmpty()) {
			Iterator<PrpJpayRefRec> iter = ve.iterator();
			while (iter.hasNext()) {
//				PrpJpayRefRecSchema prpJpayRefRecSchema = (PrpJpayRefRecSchema) iter.next();
				PrpJpayRefRec prpJpayRefRec = iter.next();
				throw new UserException(1, 3, "endcase", "該案件存在未攤回的賠款不能結案，立案號：" + claimNo + " " + "理算书号:" + prpJpayRefRec.getId().getCertiNo() + "");
			}
		}
		if (claimNo != null) {
			claimNo = claimNo.trim();
			// 对赔案号的存在性进行判断
			boolean isExist = true;
			isExist = claimService.isExist(claimNo);
			if (!isExist) {
				throw new UserException(1, 3, "產生賠案號", "抱歉，您輸入的賠案號不存在，請檢查！");
			}
		}
		String flowID = request.getParameter("swfLogFlowID");
		String logNo = request.getParameter("swfLogLogNo");
		if (DataUtils.emptyToNull(flowID) != null && DataUtils.emptyToNull(logNo) != null) {
			SwfLog swflog = this.getWorkFlowService().findByPrimaryKey(flowID, Integer.parseInt(logNo));
			if(swflog!=null){
				String condition = " flowid = '"+flowID+"' and riskcode = '"+swflog.getRiskCode()+"' and nodetype in ('certa','verif','wound','veriw','propc','propv') and nodestatus < 4 order by nodetype asc , lossitemcode ";
				List<SwfLog> list = this.getWorkFlowService().findByConditions(condition);
				if (list != null && !list.isEmpty()) {
					String tempStr = "";
					for(SwfLog s : list){
						if("certa".equals(s.getNodeType())){
							tempStr += "、定損("+s.getLossItemCode()+"-"+ s.getLossItemName() +")";
						} else if("verif".equals(s.getNodeType())){
							tempStr += "、核損("+s.getLossItemCode()+"-"+ s.getLossItemName() +")";
						} else if("wound".equals(s.getNodeType())){
							tempStr += "、人傷定損("+s.getLossItemCode()+"-"+ s.getLossItemName() +")";
						} else if("veriw".equals(s.getNodeType())){
							tempStr += "、人傷核損("+s.getLossItemCode()+"-"+ s.getLossItemName() +")";
						} else if("propc".equals(s.getNodeType())){
							tempStr += "、財損定損";
						} else if("propv".equals(s.getNodeType())){
							tempStr += "、財產核損";
						}
					}
					throw new UserException(1, 3, "結案" , "該案件存在未處理完畢的任務：" + tempStr.substring(1) + " ！");
				}
			}
		}
		// 把赔案号码设置到页面
		request.setAttribute("claimNo", claimNo);
		// 结案分步骤进行
		String userMessage = "";
		if (strStep != null) {
			if (strStep.trim().equals("step1")) {
				// 向立案表写入结案日期和结案员代码
				// 用viewHelper整理界面输入
				// 以下是 的业务需求:要求结案和归档要在一步完成,以避免只结案不归档带来的问题)
				boolean isRecase = this.daaEndcaseViewHelper.isRecase(claimNo);
				boolean isGenrateCaseNo = this.daaEndcaseViewHelper.isGenrateCaseNo(claimNo);
				if (isGenrateCaseNo == true && isRecase == false) {
					// 无重开赔案 或 无未结案的重开赔案
					this.clearErrorsAndMessages();
					super.addActionMessage(super.getText("title.endcaseEidt.generateCaseNo"));
					return SUCCESS;
				}
				String caseNo = "";
				if (isRecase == true) { // 有未结的重开赔案
					PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
					caseNo = prpLclaim.getCaseNo();
				} else {
					caseNo = this.daaEndcaseViewHelper.getCaseNo(claimNo, request.getParameter("prpLendcaseComCode"));
				}
				request.setAttribute("caseNo", caseNo);
				EndcaseDto endcaseDto = this.daaEndcaseViewHelper.viewToDto(request, true);
				// 工作流处理过程
				WorkFlowDto workFlowDto = null;
				String actorId = request.getParameter("swfLogActorId");
				if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
					// 新工作流引擎处理入口
					workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, true, "4", null, null, caseNo, null, null);
				} else {
					//workFlowDto = this.getWorkFlowDto(claimNo, caseNo);
				    workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, true, "4", null, null, caseNo, null, null);
                    workFlowDto.setClose(true);//結束工作流
				}
				// 保存结案信息
				if (workFlowViewHelper.checkDealDto(workFlowDto)) { // 判断有可以保存的工作流
					if (isRecase == true) { // 重开赔案只保存流的东西,回写 prplrecase表
//						this.jbpmBusinessViewHelper.saveBusiness(endcaseService,"reCaseSave",workFlowDto, endcaseDto);
						this.endcaseService.reCaseSave(endcaseDto, workFlowDto);
					} else { // 一般案件结案
						this.endcaseService.save(endcaseDto, workFlowDto);
//						this.jbpmBusinessViewHelper.saveBusiness(endcaseService,"save",workFlowDto, endcaseDto);
					}
					// 结案提交发邮件通知经办
					BLMailSenderFacade blMailSenderFacade = new BLMailSenderFacade();
					blMailSenderFacade.MailSend("endca", claimNo, user);
				} else {
					this.endcaseService.save(endcaseDto);
					userMessage = "注意:沒有發現與工作流流程相關任何數據！";
//					user.setUserMessage(caseNo + ";注意:沒有發現與工作流流程相關任何數據！！");
//					request.getSession().setAttribute("user", user);
				}
				this.clearErrorsAndMessages();
				super.addActionMessage(super.getText("prompt.endcase.save"));
				super.addActionMessage(super.getText("db.prpLcompensate.caseNo"));
				super.addActionMessage(caseNo);
				if (!CommonUtils.isEmpty(userMessage)) {
					super.addActionMessage(userMessage);
				}
				return SUCCESS;
			} else if (strStep.trim().equals("step2")) {// 为处理遗留的历史数据,暂时保留
				// 是否生成过赔案号码
				boolean isGenrateCaseNo = this.daaEndcaseViewHelper.isGenrateCaseNo(claimNo);
				if (isGenrateCaseNo == true) {
					this.clearErrorsAndMessages();
					super.addActionMessage(super.getText("title.endcaseEidt.generateCaseNo"));
					return SUCCESS;
				}
				this.daaEndcaseViewHelper.compensateToView(request, claimNo);
				forward = "step2";
			} else if (strStep.trim().equals("step3")) {
				String caseNo = request.getParameter("prplCaseNoCaseNo1"); // 陪案号
				request.setAttribute("caseNo", caseNo);
				// 用viewHelper整理界面输入
				EndcaseDto endcaseDto = this.daaEndcaseViewHelper.viewToDto(request, true);
				// 工作流处理过程
				// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以后节点的业务号码,6本节点的业务流入号码,7以后节点的业务流出号码????
				SwfLog swfLogDtoDealNode = new SwfLog();
				swfLogDtoDealNode.setNodeType("endca");
				swfLogDtoDealNode.setNodeStatus("4"); // 默认都是提交
				swfLogDtoDealNode.setBusinessNo(claimNo);
				swfLogDtoDealNode.setNextBusinessNo(claimNo);
				swfLogDtoDealNode.setKeyIn(claimNo);
				swfLogDtoDealNode.setKeyOut(caseNo);
				WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
				// 保存结案信息
				if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
					this.endcaseService.save(endcaseDto, workFlowDto);
					user.setUserMessage(caseNo);
				} else {
					this.endcaseService.save(endcaseDto);
					userMessage = "注意:沒有發現與工作流流程相關任何數據！";
//					user.setUserMessage(caseNo + ";注意:沒有發現與工作流流程相關任何數據！！");
//					request.getSession().setAttribute("user", user);
				}
				// 添加提示信息
				this.clearErrorsAndMessages();
				if ("4".equals(request.getParameter("buttonSaveType"))) {
					super.addActionMessage(super.getText("prompt.endcase.submit"));
				} else {
					super.addActionMessage(super.getText("prompt.endcase.save"));
				}
				super.addActionMessage(super.getText("db.prpLcompensate.caseNo"));
				if (!CommonUtils.isEmpty(userMessage)) {
					super.addActionMessage(userMessage);
				}
				return SUCCESS;
			}
		}
		return forward;
	}

	/***
	 * 旧工作流处理结案任务
	 * @param claimNo
	 * @param caseNo
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(String claimNo, String caseNo) throws Exception {
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.setNodeType("endca");
		swfLogDtoDealNode.setNodeStatus("4"); // 默认都是提交
		swfLogDtoDealNode.setBusinessNo(claimNo);
		swfLogDtoDealNode.setNextBusinessNo(claimNo);
		swfLogDtoDealNode.setKeyIn(claimNo);
		swfLogDtoDealNode.setKeyOut(caseNo);
		HttpServletRequest request = super.getRequest();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
	}

	public DAAEndcaseViewHelper getDaaEndcaseViewHelper() {
		return daaEndcaseViewHelper;
	}

	public void setDaaEndcaseViewHelper(DAAEndcaseViewHelper daaEndcaseViewHelper) {
		this.daaEndcaseViewHelper = daaEndcaseViewHelper;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
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

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
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

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
