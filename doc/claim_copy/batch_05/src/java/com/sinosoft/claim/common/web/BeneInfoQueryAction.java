package com.sinosoft.claim.common.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;

/**
 * 分发HTTP GET 根据被保险人查找相关受益人
 * <p>
 * 查看事故者相關受益人訊息，查看險種保益訊息
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class BeneInfoQueryAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private PrpCitemKindService prpCitemKindService;
	private PrpCinsuredService prpCinsuredService;
	
	/***
	 * 
	 * @return
	 * @throws Exception
	 */
	public String beneInfoQuery() throws Exception {
		HttpServletRequest request = super.getRequest();
		String serialNo = request.getParameter("serialNo");
		String bizType = request.getParameter("bizType");
		String policyno = request.getParameter("policyno");
		String prpLacciPersonAcciCode = request.getParameter("prpLacciPersonAcciCode");
		int i = 0;
		// 根据保单号和序号查到当前受益人信息,insuredFlag='9'(受益人)
		if ("beneInfoShow".equals(bizType)) {
			QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", policyno);
			List<PrpCinsured> prpcinsuredDtoList = prpCinsuredService.findPrpCinsured(queryRule);
			List<PrpCinsured> beneInsuredList = new ArrayList<PrpCinsured>();
			PrpCinsured prpCinsured = null;
			for (i = 0; i < prpcinsuredDtoList.size(); i++) {
				prpCinsured = (PrpCinsured) prpcinsuredDtoList.get(i);
				if (prpCinsured.getId().getPolicyNo().equalsIgnoreCase(policyno) && prpCinsured.getInsuredCode().equals(prpLacciPersonAcciCode.trim()) && "9".equals(prpCinsured.getInsuredFlag())) {
					beneInsuredList.add(prpCinsured);
				}
			}
			request.setAttribute("beneInsuredList", beneInsuredList);
			return bizType;
		} else if ("riskInfoShow".equals(bizType)) {
			List<PrpCitemKind> prpcitemkindList = prpCitemKindService.findByConditions("policyno = '" + policyno + "'");
			List<PrpCitemKind> beneKindList = new ArrayList<PrpCitemKind>();
			PrpCitemKind prpCitemKind = null;
			for (i = 0; i < prpcitemkindList.size(); i++) {
				prpCitemKind = (PrpCitemKind) prpcitemkindList.get(i);
				// reason:必须区分是哪个人的保额信息
				if ((prpCitemKind.getId().getPolicyNo().equalsIgnoreCase(policyno)) && (prpCitemKind.getFamilyNo().intValue() == Integer.parseInt(serialNo))) {
					beneKindList.add(prpCitemKind);
				}
			}
			request.setAttribute("beneKindList", beneKindList);
			return bizType;
		}
		return NONE;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

}
