/*
 * @(#)CommonCheckTaskViewHelper.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.SwfFlowMainService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.undwrt.service.facade.WfCheckAdvanceService;
import com.sinosoft.claim.undwrt.vo.PolicyAbstractInfoDto;
import com.sinosoft.prpall.ui.model.UtiPrintPageFindByConditionsCommand;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 在页面上的java代码移到这个类中
 */
public class CommonCheckTaskViewHelper {
	/** 保单基本信息服务 */
	private PrpCmainService prpCmainService;
	/** 代码服务 */
	private CodeService codeService;
	/** 流程主表信息服务 */
	private SwfFlowMainService swfFlowMainService;
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 预赔登记信息服务 */
	private PrpLprepayService prpLprepayService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 工作流路径服务 */
	private SwfPathService swfPathService;
	/** 工作流日志服务 */
	private WfLogService wfLogService; 
	/** 共保信息服务 */
	private PrpCcoinsService prpCcoinsService;
	/** 保单服务 */
	private PolicyService policyService;
	/** 报案数据收集 */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 检查下一节点的工作流服务 */
	private WfCheckAdvanceService wfCheckAdvanceService;
	
	public SwfFlowMainService getSwfFlowMainService() {
		return swfFlowMainService;
	}

	public void setSwfFlowMainService(SwfFlowMainService swfFlowMainService) {
		this.swfFlowMainService = swfFlowMainService;
	}

	public void commonDealContent(HttpServletRequest request,PrpCmain prpCmain,PrpLclaim prpLclaim,WfLog wfLog) throws Exception {
		String ProposalNo = prpCmain.getProposalNo();
		String condition = " businessNo=" + "'" + ProposalNo + "'";
		UtiPrintPageFindByConditionsCommand UtiPrintPageFindByConditionsCommand = new UtiPrintPageFindByConditionsCommand(condition);
		List<?> list = (List<?>) UtiPrintPageFindByConditionsCommand.executeCommand();
		String reportPrintDisabledFlag = "disabled";
		if (list.size() > 0) {
			reportPrintDisabledFlag = "";
		}
		request.setAttribute("speedFlagList", ConstantsCollection.speedFlagList);
		request.setAttribute("businessFlag", prpCmain.getBusinessFlag());
		request.setAttribute("reportPrintDisabledFlag", reportPrintDisabledFlag);
		request.setAttribute("list", list);
		
		String editType = request.getParameter("EditType");
		if(!"query".equals(editType)){
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			boolean submitPass = wfCheckAdvanceService.checkAdvanceCondition(wfLog.getModelNo(), wfLog.getNodeNo(), wfLog.getBusinessType(), wfLog.getBusinessNo(), "1", userDto.getUserCode());
			request.setAttribute("submitPass", submitPass);
		}
		
	}

	public PolicyAbstractInfoDto getPolicyAbstractInfo(WfLog wfLog,PrpCmain prpCmain) throws Exception {
		String businessNo = wfLog.getBusinessNo();
		
		PolicyAbstractInfoDto policyAbstractInfoDto = new PolicyAbstractInfoDto();
		if ("C".equals(wfLog.getBusinessType())) {
			PrpLcompensate prpLcompensateDto = this.getPrpLcompensateService().findPrpLcompensate(businessNo);
			//按照客户需求，保品金额显示本次赔付总和
			// 从（联、共）保显示我司金额 start
			if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
				List<PrpCcoins> prpCcoinsList = (ArrayList<PrpCcoins>)this.getPrpCcoinsService().findByConditions("policyNo='"+prpLcompensateDto.getPolicyNo()+"' and coinsType='2'");
				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoinsDto = iterator.next();
					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
					BigDecimal bigSumLoss = new BigDecimal(Double.toString(prpLcompensateDto.getSumDutyPaid()));
					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLcompensateDto.getSumPaid()));
					policyAbstractInfoDto.setSumLoss(bigSumLoss.multiply(bigCoinsRate).doubleValue());
					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).doubleValue());
				}
			} else {
				policyAbstractInfoDto.setSumLoss(prpLcompensateDto.getSumDutyPaid());
				policyAbstractInfoDto.setSumPaid(prpLcompensateDto.getSumPaid());
			}
			//从（联、共）保显示我司金额 end
			policyAbstractInfoDto.setOther(prpLcompensateDto.getSumNoDutyFee());
		} else if ("Y".equals(wfLog.getBusinessType())) {
			PrpLprepay prpLprepayDto = this.getPrpLprepayService().findPrpLprepay(businessNo);
			//从（联、共）保显示我司金额 start
			if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {
				List<PrpCcoins> prpCcoinsList = (ArrayList<PrpCcoins>)this.getPrpCcoinsService().findByConditions("policyNo='"+prpLprepayDto.getPolicyNo()+"' and coinsType='2'");
				for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
					PrpCcoins prpCcoinsDto = iterator.next();
					BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoinsDto.getCoinsRate()/100));
					BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLprepayDto.getSumPrePaid()));
					policyAbstractInfoDto.setSumPaid(bigSumPaid.multiply(bigCoinsRate).doubleValue());
				}
			} else {
				policyAbstractInfoDto.setSumPaid(prpLprepayDto.getSumPrePaid());
			}
		}
		return policyAbstractInfoDto;
	}
	
	/**
	 * 设置缴费情况(是否缴费以及缴费情况)
	 * @param httpServletRequest
	 * @param policyNo
	 * @throws Exception
	 */
	public void setPayCase(HttpServletRequest httpServletRequest, PrpCmain prpCmain) throws Exception {
		String conditions = " policyno = '" + prpCmain.getPolicyNo() + "'";
		// 获得缴费情况
		int intReturn = 0;
		intReturn = this.getPolicyService().checkPay(conditions);// -1为未缴费，0为未缴全，1为缴全
		String strPayFlag = String.valueOf(intReturn);
		httpServletRequest.setAttribute("payFlag", strPayFlag);
		// 当缴费不足时,要显示相应的缴费情况		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intReturn == 0 && prpCmain.getPayTimes() > 1) {
			delinquentfeeCase = this.daaRegistViewHelper.getDelinquentfeeCase(prpCmain);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
	}

	/**
	 * 设置报案号和立案号
	 * @param request
	 * @throws Exception
	 */
	public void getRegistClaimNo(HttpServletRequest request) throws Exception {
		String businessType = StringUtils.trimToEmpty(request.getParameter("iBusinessType"));
		String businessNo = StringUtils.trimToEmpty(request.getParameter("iBusinessNo"));
		String claimNo = null;
		String registNo = null;
		
		if("C".equals(businessType)){//计算书号
			PrpLcompensate prpLcompensate = this.getPrpLcompensateService().findPrpLcompensate(businessNo);
			claimNo = prpLcompensate.getClaimNo();
		}else if ("Y".equals(businessType)){//预赔号
			PrpLprepay prpLprepay = this.getPrpLprepayService().findPrpLprepay(businessNo);
			claimNo = prpLprepay.getClaimNo();
		}
		registNo = this.getPrpLclaimService().findPrpLclaim(claimNo).getRegistNo();
		
		request.setAttribute("ClaimNo", claimNo);
		request.setAttribute("RegistNo", registNo);
		if(!"".equals(registNo)){
			List<SwfLog> swfLogList = swfLogService.findViewSwfLogAll(" registno = '"+registNo+"' and logno = '1'");
			String swfLogFlowID = swfLogList.get(0).getId().getFlowID();
			request.setAttribute("swfLogFlowID", swfLogFlowID);
		}
	}

	public void getPassPath(HttpServletRequest request,WfLog wfLog) throws Exception {
		SwfPath swfPath = this.getSwfPathService().getPassPath(wfLog);
		request.setAttribute("swfPath", swfPath);
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

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public WfCheckAdvanceService getWfCheckAdvanceService() {
		return wfCheckAdvanceService;
	}

	public void setWfCheckAdvanceService(WfCheckAdvanceService wfCheckAdvanceService) {
		this.wfCheckAdvanceService = wfCheckAdvanceService;
	}

}
