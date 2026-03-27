package com.sinosoft.claim.common.util;

import ins.framework.common.QueryRule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.ui.control.action.UIWorkFlowAction;
import com.sinosoft.sysframework.log.Logger;

/**
 * 关联页面相关数据整理
 * <p>
 * Title: 关联页面相关数据整理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class RelateBusinessNoViewHelper {
	/** 批单数据传输对象服务 */
	private EndorseService endorseService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 赔案保单关联信息服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 报案服务 */
	private RegistService registService;
	/** 日志Log对象 */
	Logger log = Logger.getLogger(RelateBusinessNoViewHelper.class);

	public RelateBusinessNoViewHelper() {
	}

	/**
	 * 关联页面查询相关信息
	 * @param httpServletRequest request
	 * @throws Exception
	 */
	public void queryRelateInfoToDto(HttpServletRequest request) throws Exception {
		UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();

		// 获得页面传入的保单号码
		String policyNo = request.getParameter("policyNo");
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
		String claimNo = request.getParameter("claimNo");

		log.debug("进入关联Action:RelateBusinessNoViewHelper,获得的保单号是:" + policyNo);
		// 1.获得保单信息(单条)

//		PolicyDto policyDto = this.policyService.findByPrimaryKey(policyNo);
		// 只需要保单主信息即可
		PrpCmain prpCmain = this.policyService.findPrpCmainDtoByPrimaryKey(policyNo);

		// 2.获得批单信息(多条)
		List<PrpPhead> headList = this.endorseService.findByConditions(policyNo).getPrpPheadList();
		// 传到前台是空长度的集合而不要传空指针
		if (headList == null)
			headList = new ArrayList<PrpPhead>();

		// 3.获得理赔信息(多条)
		List<PrpLregist> registList = this.registService.findRegistsByPolicyno(policyNo);
		if (registList == null) {
			registList = new ArrayList<PrpLregist>();
		}
		if (registList.size() == 0) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", policyNo);
			List<?> registListTemp = this.prpLregistrpolicyService.findPrplregistrpolicy(queryRule);
			for (Iterator<?> iterator = registListTemp.iterator(); iterator.hasNext();) {
				Prplregistrpolicy prpLRegistRPolicyDto = (Prplregistrpolicy) iterator.next();
				PrpLregist prpLregist = this.registService.findByPrimaryKey(prpLRegistRPolicyDto.getId().getRegistNo()).getPrpLregist();
				prpLregist.setCompensateFeeDto(this.registService.getCompensateFeeByRegistNo(prpLRegistRPolicyDto.getId().getRegistNo()));
				prpLregist.setWorkFlowId(uiWorkFlowAction.findFlowIDByRegistNo(prpLRegistRPolicyDto.getId().getRegistNo()));
				registList.add(prpLregist);
			}
		} else {
			// 下面分别计算每个报案的赔款金额
			for (Iterator<PrpLregist> iter = registList.iterator(); iter.hasNext();) {
				PrpLregist prpLregistDto = (PrpLregist) iter.next();
				// 设置报案信息的赔款金额
				prpLregistDto.setCompensateFeeDto(this.registService.getCompensateFeeByRegistNo(prpLregistDto.getRegistNo()));
				prpLregistDto.setWorkFlowId(uiWorkFlowAction.findFlowIDByRegistNo(prpLregistDto.getRegistNo()));
			}
		}
		Date nowTime = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String strDamageDate = sdf.format(nowTime); 
			
		// 设置保单主信息
		request.setAttribute("prpCmain", prpCmain);
		// 设置批单信息(List中保存的是PrpPheadDto对象)
		request.setAttribute("headList", headList);
		// 设置报案信息(List中保存的是:prpLregistDto对象)
		request.setAttribute("registList", registList);
		//调用承保保单查询时需要damageDate参数
		request.setAttribute("strDamageDate", strDamageDate);
		
		//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
		request.setAttribute("claimNo", claimNo);
	}

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

}
