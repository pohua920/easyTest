package com.sinosoft.undwrt.undwrtDeal.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCDangerUnitId;
import com.sinosoft.reins.common.model.PrpPDangerUnit;
import com.sinosoft.reins.common.model.PrpPDangerUnitId;
import com.sinosoft.reins.common.model.PrpTDangerRisk;
import com.sinosoft.reins.common.model.PrpTDangerUnit;
import com.sinosoft.reins.common.model.PrpTDangerUnitId;
import com.sinosoft.reins.common.service.facade.PrpCDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpCDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpPDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpPDangerUnitService;
import com.sinosoft.reins.common.service.facade.PrpTDangerRiskService;
import com.sinosoft.reins.common.service.facade.PrpTdangerUnitService;
import com.sinosoft.sysframework.exceptionlog.UserException;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * 危險單位信息處理類.
 */
public class CommonDangerInfoViewAction extends Struts2Action {

	/** 屬性風險類別描述. */
	private String riskClassDesc;

	/** 屬性保單危險單位交費計畫接口. */
	private PrpCDangerRiskService prpCDangerRiskService;

	/** 屬性保單危險單位臨分接口. */
	private PrpCDangerUnitService prpCDangerUnitService;

	/** 屬性批單危險單位風險評估接口. */
	private PrpPDangerRiskService prpPDangerRiskService;

	/** 屬性批單的危險單位劃分接口. */
	private PrpPDangerUnitService prpPDangerUnitService;

	/** 屬性要保書危險單位風險評估接口. */
	private PrpTDangerRiskService prpTDangerRiskService;

	/** 屬性投保單的危險單位劃分接口. */
	private PrpTdangerUnitService prpTdangerUnitService;

	/**
	 * 獲取危險信息.
	 * 
	 * @return 頁面跳轉結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String getDangerInfo() throws UserException, Exception {
		HttpServletRequest req = this.getRequest();
		String forward = "";
		String riskCode = req.getParameter("riskCode");
		String classCode = req.getParameter("classCode");
		String businessNo = req.getParameter("businessNo");
		String dangerNo = req.getParameter("dangerNo");
		String businessType = req.getParameter("businessType");

		Collection PrpDangerRiskAll = null;
		Collection PrpDangerUnit = null;
		PrpDangerRiskAll = this.getDangerRiskInfo(req);
		QueryRule queryRule;
		if (PrpDangerRiskAll != null && PrpDangerRiskAll.size() > 0) {
			req.setAttribute("PrpDangerRiskAll", PrpDangerRiskAll);
			req.setAttribute("PrpDangerRiskFirst", PrpDangerRiskAll.iterator()
					.next());
		} else {
			PrpDangerRiskAll = new ArrayList();
			req.setAttribute("PrpDangerRiskAll", PrpDangerRiskAll);
			req.setAttribute("PrpDangerRiskFirst", new PrpTDangerRisk());
		}
		PrpDangerUnit = new ArrayList();
		if (businessType.equals("T")) {
			PrpTDangerUnit prpTdangerUnitDto = null;
			PrpTDangerUnitId id = new PrpTDangerUnitId();
			id.setDangerNo(Integer.parseInt(dangerNo));
			id.setProposalNo(businessNo);
			prpTdangerUnitDto = prpTdangerUnitService.findByConditions(id);
			riskClassDesc = prpTdangerUnitDto.getRiskClassDesc();
			PrpDangerUnit.add(prpTdangerUnitDto);
		}
		if (businessType.equals("P")) {
			PrpCDangerUnit prpCdangerUnitDto = null;
			PrpCDangerUnitId id = new PrpCDangerUnitId();
			id.setDangerNo(Integer.parseInt(dangerNo));
			id.setPolicyNo(businessNo);
			prpCdangerUnitDto = prpCDangerUnitService.findByConditions(id);
			riskClassDesc = prpCdangerUnitDto.getRiskClassDesc();
			PrpDangerUnit.add(prpCdangerUnitDto);
		}
		if (businessType.equals("E")) {
			PrpPDangerUnit prpPdangerUnitDto = null;
			PrpPDangerUnitId id = new PrpPDangerUnitId();
			id.setDangerNo(Integer.parseInt(dangerNo));
			id.setEndorseNo(businessNo);
			prpPdangerUnitDto = prpPDangerUnitService.findByPrimaryKey(id);
			riskClassDesc = prpPdangerUnitDto.getRiskClassDesc();
			PrpDangerUnit.add(prpPdangerUnitDto);
		}
		req.setAttribute("PrpDangerUnit", PrpDangerUnit);
		req.setAttribute("riskClassDesc", riskClassDesc);
		forward = "success";

		return forward;

	}

	/**
	 * 獲取危險單位風險評估訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 滿足條件的風險評估訊息集合
	 * @throws Exception
	 *            異常
	 */
	public Collection getDangerRiskInfo(HttpServletRequest req)
			throws Exception {
		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String dangerNo = req.getParameter("dangerNo");
		// String riskClass = req.getParameter("riskKind");
		Collection dangerRiskInfoList = new ArrayList();
		QueryRule queryRule = QueryRule.getInstance();

		if (businessType.equals("T")) {

			queryRule.addEqual("id.proposalNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.parseInt(dangerNo));
			dangerRiskInfoList = prpTDangerRiskService
					.findByConditions(queryRule);
		} else if (businessType.equals("P")) {
			queryRule.addEqual("id.policyNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.parseInt(dangerNo));
			dangerRiskInfoList = prpCDangerRiskService
					.findByConditions(queryRule);

		} else if (businessType.equals("E")) {
			queryRule.addEqual("id.endorseNo", businessNo);
			queryRule.addEqual("id.dangerNo", Integer.parseInt(dangerNo));
			dangerRiskInfoList = prpPDangerRiskService
					.findByConditions(queryRule);

		}
		return dangerRiskInfoList;
	}

	/**
	 * 獲取屬性風險類別描述.
	 * 
	 * @return 屬性風險類別描述的值
	 */
	public String getRiskClassDesc() {
		return riskClassDesc;
	}

	/**
	 * 設置屬性風險類別描述.
	 * 
	 * @param riskClassDesc
	 *            待設置的風險類別描述的值
	 */
	public void setRiskClassDesc(String riskClassDesc) {
		this.riskClassDesc = riskClassDesc;
	}

	/**
	 * 獲取屬性保單危險單位交費計畫接口.
	 * 
	 * @return 屬性保單危險單位交費計畫接口的值
	 */
	public PrpCDangerRiskService getPrpCDangerRiskService() {
		return prpCDangerRiskService;
	}

	/**
	 * 設置屬性保單危險單位交費計畫接口.
	 * 
	 * @param prpCDangerRiskService
	 *            待設置的保單危險單位交費計畫接口的值
	 */
	public void setPrpCDangerRiskService(
			PrpCDangerRiskService prpCDangerRiskService) {
		this.prpCDangerRiskService = prpCDangerRiskService;
	}

	/**
	 * 獲取屬性保單危險單位臨分接口.
	 * 
	 * @return 屬性保單危險單位臨分接口的值
	 */
	public PrpCDangerUnitService getPrpCDangerUnitService() {
		return prpCDangerUnitService;
	}

	/**
	 * 設置屬性保單危險單位臨分接口.
	 * 
	 * @param prpCDangerUnitService
	 *            待設置的保單危險單位臨分接口的值
	 */
	public void setPrpCDangerUnitService(
			PrpCDangerUnitService prpCDangerUnitService) {
		this.prpCDangerUnitService = prpCDangerUnitService;
	}

	/**
	 * 獲取屬性批單危險單位風險評估接口.
	 * 
	 * @return 屬性批單危險單位風險評估接口的值
	 */
	public PrpPDangerRiskService getPrpPDangerRiskService() {
		return prpPDangerRiskService;
	}

	/**
	 * 設置屬性批單危險單位風險評估接口.
	 * 
	 * @param prpPDangerRiskService
	 *            待設置的批單危險單位風險評估接口的值
	 */
	public void setPrpPDangerRiskService(
			PrpPDangerRiskService prpPDangerRiskService) {
		this.prpPDangerRiskService = prpPDangerRiskService;
	}

	/**
	 * 獲取屬性批單的危險單位劃分接口.
	 * 
	 * @return 屬性批單的危險單位劃分接口的值
	 */
	public PrpPDangerUnitService getPrpPDangerUnitService() {
		return prpPDangerUnitService;
	}

	/**
	 * 設置屬性批單的危險單位劃分接口.
	 * 
	 * @param prpPDangerUnitService
	 *            待設置的批單的危險單位劃分接口的值
	 */
	public void setPrpPDangerUnitService(
			PrpPDangerUnitService prpPDangerUnitService) {
		this.prpPDangerUnitService = prpPDangerUnitService;
	}

	/**
	 * 獲取屬性要保書危險單位風險評估接口.
	 * 
	 * @return 屬性要保書危險單位風險評估接口的值
	 */
	public PrpTDangerRiskService getPrpTDangerRiskService() {
		return prpTDangerRiskService;
	}

	/**
	 * 設置屬性要保書危險單位風險評估接口.
	 * 
	 * @param prpTDangerRiskService
	 *            待設置的要保書危險單位風險評估接口的值
	 */
	public void setPrpTDangerRiskService(
			PrpTDangerRiskService prpTDangerRiskService) {
		this.prpTDangerRiskService = prpTDangerRiskService;
	}

	/**
	 * 獲取屬性投保單的危險單位劃分接口.
	 * 
	 * @return 屬性投保單的危險單位劃分接口的值
	 */
	public PrpTdangerUnitService getPrpTdangerUnitService() {
		return prpTdangerUnitService;
	}

	/**
	 * 設置屬性投保單的危險單位劃分接口.
	 * 
	 * @param prpTdangerUnitService
	 *            待設置的投保單的危險單位劃分接口的值
	 */
	public void setPrpTdangerUnitService(
			PrpTdangerUnitService prpTdangerUnitService) {
		this.prpTdangerUnitService = prpTdangerUnitService;
	}

}
