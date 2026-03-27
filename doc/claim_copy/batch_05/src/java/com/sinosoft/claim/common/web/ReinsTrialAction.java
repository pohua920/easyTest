package com.sinosoft.claim.common.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsClaimDetail;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsClaimSummary;
import com.sinosoft.claiminterface.reins.service.ReinsServiceManager;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import ins.framework.web.Struts2Action;

/**
 * 分摊试算处理并返回试算结果
 * @Description 
 * @author 中科软
 */
public class ReinsTrialAction extends Struts2Action {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public String reinsTrial() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String reinsTrial = "";
		Collection<Object> reinsRepayCalResultCollection = new ArrayList<Object>();
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		String prpLcompensateSumPaid = httpServletRequest.getParameter("prpLcompensateSumPaid");
		// 生成分摊试算结果 分为两种情况 立案和理算两个环节
		if (prpLcompensateSumPaid == null || prpLcompensateSumPaid.equals("")) {// 立案
			reinsTrial = "估損";
			reinsClaimSummary = this.getClaimReinsClaimSummary(httpServletRequest);
			reinsRepayCalResultCollection = ReinsServiceManager.getReinsService().repaySimulate(reinsClaimSummary);

		} else if (prpLcompensateSumPaid != null && !prpLcompensateSumPaid.equals("")) {// 理算
			reinsTrial = "實賠";
			reinsClaimSummary = this.getCompensateReinsClaimSummary(httpServletRequest);
			reinsRepayCalResultCollection = ReinsServiceManager.getReinsService().repaySimulate(reinsClaimSummary);
		}
		// 返回分摊试算结果
		if (reinsRepayCalResultCollection.size() > 0) {
			httpServletRequest.setAttribute("reinsTrial", reinsTrial);
			httpServletRequest.setAttribute("reinsRepayCalResultCollection", reinsRepayCalResultCollection);
		} else {
			httpServletRequest.setAttribute("reinsTrial", "");
		}
		return SUCCESS;
	}

	private ReinsClaimSummary getClaimReinsClaimSummary(HttpServletRequest httpServletRequest) {
		String prpLdangerDangerNoAry[] = StringUtils.split(httpServletRequest.getParameter("prpLdangerDangerNo"), ",");
		String prpLdangerCurrency = httpServletRequest.getParameter("prpLclaimCurrency");
		String prpLdangerPolicyNo = httpServletRequest.getParameter("prpLdangerPolicyNo");
		DateTime prpLclaimDamageStartDate = new DateTime(httpServletRequest.getParameter("prpLclaimDamageStartDate"));
		// 传入的估损信息分危险单位拆分
		String prpLsumPay[] = StringUtils.split(httpServletRequest.getParameter("prpLclaimSumClaim"), ",");
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		reinsClaimSummary.setPolicyNo(prpLdangerPolicyNo);
		reinsClaimSummary.setDamageDate(prpLclaimDamageStartDate);
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		if (prpLdangerDangerNoAry!=null && prpLdangerDangerNoAry.length > 0) {
			ReinsClaimDetail reinsClaimDetail = null;
			for (int i = 0; i < prpLdangerDangerNoAry.length; i++) {
				reinsClaimDetail = new ReinsClaimDetail();
				reinsClaimDetail.setCurrency(prpLdangerCurrency);
				reinsClaimDetail.setSumPaid(new Double(Double.parseDouble(prpLsumPay[i])));
				String dangerNo = prpLdangerDangerNoAry[i];
				reinsClaimDetail.setDangerNo(new Integer(dangerNo));
				reinsClaimDetailList.add(reinsClaimDetail);
			}
		}
		reinsClaimSummary.setReinsClaimDetailList(reinsClaimDetailList);
		return reinsClaimSummary;
	}

	private ReinsClaimSummary getCompensateReinsClaimSummary(HttpServletRequest httpServletRequest) {
		String prpLdangerDangerNoAry[] = StringUtils.split(httpServletRequest.getParameter("prpLdangerDangerNo"), ",");
		String prpLdangerCurrency = httpServletRequest.getParameter("prpLcompensateCurrency");
		String prpLdangerPolicyNo = httpServletRequest.getParameter("prpLdangerPolicyNo");
		DateTime prpLclaimDamageStartDate = new DateTime(httpServletRequest.getParameter("prpLdamageStartDate"));
		String prpLsumClaim[] = StringUtils.split(httpServletRequest.getParameter("prpLcompensateSumPaid"), ",");
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		reinsClaimSummary.setPolicyNo(prpLdangerPolicyNo);
		reinsClaimSummary.setDamageDate(prpLclaimDamageStartDate);
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		if (prpLdangerDangerNoAry!=null && prpLdangerDangerNoAry.length > 0) {
			for (int i = 0; i < prpLdangerDangerNoAry.length; i++) {
				ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
				reinsClaimDetail.setCurrency(prpLdangerCurrency);
				reinsClaimDetail.setSumPaid(new Double(Double.parseDouble(prpLsumClaim[i])));
				String dangerNo = prpLdangerDangerNoAry[i];
				reinsClaimDetail.setDangerNo(new Integer(dangerNo));
				reinsClaimDetailList.add(reinsClaimDetail);
			}
		}
		reinsClaimSummary.setReinsClaimDetailList(reinsClaimDetailList);
		return reinsClaimSummary;
	}
}
