package com.sinosoft.claim.common.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.util.BusinessRuleUtil;

import ins.framework.web.Struts2Action;

/**
 * @ClassName PerilInfoQueryAction
 * @Description 页面点击查看已出险次数 的出险信息
 * @author 中科软
 */
@SuppressWarnings("serial")
public class PerilInfoQueryAction extends Struts2Action {

	private ClaimService claimService;

	public String execute() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String policyNo = httpServletRequest.getParameter("policyNo"); // 保单号
		String curRegistNo = httpServletRequest.getParameter("curRegistNo"); // 报案号
		String riskCode = ""; // 险种
		String forward = ""; // 向前
		// 原因：要在界面上显示一些立案信息
		List<RegistClaimInfoDto> registClaimDtoList = this.claimService.findByPolicyNo(policyNo);
		httpServletRequest.setAttribute("registClaimDtoList", registClaimDtoList);
		httpServletRequest.setAttribute("curRegistNo", curRegistNo);
		riskCode = BusinessRuleUtil.getRiskCode(policyNo, "PolicyNo");
		// 取得forward
		UICodeAction uicodeAction = UICodeAction.getInstance();
		String strRiskType = uicodeAction.translateRiskCodetoRiskType(riskCode);
		if ("D".equals(strRiskType)) { // 如果为车险则跳转到target1页面。
			forward = "target1";
		} else { // 如果为非车险则跳转到target2页面。
			forward = "target2";
		}
		return forward;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}
}
