package com.sinosoft.claim.compensate.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.vo.ICollections;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 车险理赔实赔退回定损前查询保单信息
 * @Description 车险理赔实赔退回定损前查询保单信息系统样本程序
 * @author 中科软
 */
public class CompensateBackBeforeEditAction extends Struts2Action {

	/** 工作流处理接口 */
	private WorkFlowService workFlowService;
	/** 工作流数据整理工具类 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 查勘主表接口 */
	private PrpLcheckService prpLcheckService;
	/** 理算处理接口 */
	private PrpLcompensateService prpLcompensateService;
	/** 定核损处理接口 */
	private PrpLverifyLossService prpLverifyLossService;

	private static final long serialVersionUID = 1L;

	public String compensateBackBeforeEdit() throws Exception {
		// 业务类型：查询当前的理赔处理情况
		HttpServletRequest httpServletRequest = super.getRequest();
		String claimNo = httpServletRequest.getParameter("claimNo"); // 赔案号
		String registNo = httpServletRequest.getParameter("registNo"); // 报案号
		String policyNo = httpServletRequest.getParameter("policyNo"); // 保单号
		String flowId = httpServletRequest.getParameter("swfLogFlowID");// 当前工作流号码
		String logNo = httpServletRequest.getParameter("swfLogLogNo");// 当前工作流号码
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowId, Integer.parseInt(DataUtils.nullToZero(logNo)));
		// 检查是不是前面的流程都已经结束了，可以操作计算书了。
		this.getWorkFlowViewHelper().checkNodeSubmit(swfLog);
		// 增被保险人联系电话 start
		List<PrpLcheck> checkList = this.getPrpLcheckService().findPrpLcheck(QueryRule.getInstance().addEqual("id.registNo", registNo));
		PrpLcheck prpLcheck = new PrpLcheck();
		prpLcheck.setCheckList(checkList);
		// 首先查询计算书情况：
		List<PrpLcompensate> compensateList = this.getPrpLcompensateService().findByClaimNo(claimNo);
		PrpLcompensate prpLcompensate = new PrpLcompensate();
		prpLcompensate.setCompensateList(compensateList);
		// 再次查询定损的情况
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addAscOrder("id.nodeType");
		queryRule.addAscOrder("id.lossItemCode");
		List<PrpLverifyLoss> verifyLossList = this.getPrpLverifyLossService().findPrpLverifyLoss(queryRule);
		PrpLverifyLoss prpLverifyLoss = new PrpLverifyLoss();
		prpLverifyLoss.setVerifyLossList(verifyLossList);
		prpLverifyLoss.setClaimNo(claimNo);
		prpLverifyLoss.getId().setRegistNo(registNo);
		prpLverifyLoss.setPolicyNo(policyNo);
		prpLverifyLoss.setCompensateApproverCode(user.getUserCode());
		prpLverifyLoss.setCompensateApproverName(user.getUserName());
		prpLverifyLoss.setCompensateBackDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
		prpLverifyLoss.setFlag("1");// 1是可以回退的
		// 判断核赔情况
		if (compensateList != null) {
			String conditions = "claimNo='" + claimNo + "' and underwriteflag in('1','3','9')";
			List<PrpLcompensate> compensateListTemp = this.getPrpLcompensateService().findByConditions(conditions);
			if (compensateListTemp != null && compensateListTemp.size() > 0) {
				// 不可以进行理算的会退·！因为已经有提交核赔的数据了
				prpLverifyLoss.setFlag("0"); // 不可以回退
			}
		}
		// 理算退回意见
		httpServletRequest.setAttribute("compensateBackOptionsList", ICollections.getCompensateBackList());
		httpServletRequest.setAttribute("prpLverifyLoss", prpLverifyLoss);
		httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
		httpServletRequest.setAttribute("prpLcheck", prpLcheck);//

		return SUCCESS;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

}
