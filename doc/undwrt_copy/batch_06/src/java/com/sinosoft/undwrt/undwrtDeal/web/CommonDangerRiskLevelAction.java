package com.sinosoft.undwrt.undwrtDeal.web;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.vo.DangerRiskKindVo;

import ins.framework.web.Struts2Action;
import com.sinosoft.reins.product.code.service.facade.BLReinsService;

/**
 * 獲取危險單位處理類.
 */
public class CommonDangerRiskLevelAction extends Struts2Action {

	/** 屬性核保系統查詢接口. */
	private PrpallService prpallService;

	/** 屬性再保接口業務處理接口. */
	private BLReinsService blReinsService;

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/**
	 * 獲取指定業務號，危險單位序號的壹個危險單位主信息的風險等級，風險名稱，自留額，風險幣別信息.
	 * 
	 * @return 頁面跳轉結果
	 * @throws Exception
	 *             異常
	 */
	public String getDangerRiskLevel() throws UserException, Exception {
		HttpServletRequest req = this.getRequest();
		String forward = "";
		String uwYear = "";
		String riskCode = req.getParameter("riskCode");
		String businessNo = req.getParameter("businessNo");
		String businessType = req.getParameter("businessType");
		String classCode = req.getParameter("classCode");
		String riskClass = req.getParameter("riskClass");
		String kindCode = req.getParameter("itemKindCode");
		Collection riskKindInfo = null;
		Collection itemInfo = null;

		riskKindInfo = this.getDangerRiskKind(riskCode); // 风险类别信息
		if (riskKindInfo != null) {
			if (req.getParameter("operateType") != null
					&& req.getParameter("operateType").equals("view")) {
				forward = "view";
			} else {
				forward = "success";
			}
			req.setAttribute("riskKindInfo", riskKindInfo);
			if (businessType.equals("T")) {
				PrpTmain prpTmain = null;
				prpTmain = prpallService.getPrpTmain(businessNo, businessType);
				uwYear = prpTmain.getStartDate().toString().substring(0, 4);
			}
			if (businessType.equals("P")) {
				PrpCmain prpCmain = null;
				prpCmain = prpallService.getPrpCmain(businessNo);
				uwYear = prpCmain.getStartDate().toString().substring(0, 4);
			}
			if (businessType.equals("E")) {
				PrpPmain prpPmain = null;
				prpPmain = prpallService.getPrpPmain(businessNo);
				uwYear = prpPmain.getStartDate().toString().substring(0, 4);
			}
			String thisYear = new SimpleDateFormat("yyyy").format(new Date());
			if (Integer.parseInt(uwYear) > Integer.parseInt(thisYear)) {
				uwYear = thisYear;
			}
			if (riskClass.equals("")) {
				DangerRiskKindVo dangerRiskKindDto = null;
				dangerRiskKindDto = (DangerRiskKindVo) riskKindInfo.iterator()
						.next();
				riskClass = dangerRiskKindDto.getRiskKindCode();
			}
			String strConditon ="";
			if("A".equals(classCode) || "B".equals(classCode))
			{
				strConditon= " riskCode ='" + riskCode
					+ "' and riskClass ='" + riskClass + "' and uwYear ='"
					+ uwYear + "' and kindCode='" + kindCode + "'";
			}
			else
			{
				strConditon= " riskCode ='" + riskCode
						+ "' and riskClass ='" + riskClass + "' and uwYear ='"
						+ uwYear + "' and kindCode='00'";
			}
			Collection RetenValueColl = this.getRetenValue(strConditon);
			req.setAttribute("RetenValueSet", RetenValueColl);
		}
		if (riskKindInfo == null && itemInfo == null) {
			forward = "failure";
		}
		return forward;

	}

	/**
	 * 獲得風險類別.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return 風險類別訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerRiskKind(String riskCode) throws Exception {
		Collection collection;
		collection = null;
		try {
			collection = prpDcodeService.getDangerRiskKind(riskCode);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return collection;
	}

	/**
	 * 獲取屬性核保系統查詢接口.
	 * 
	 * @return 屬性核保系統查詢接口的值
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置屬性核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口的值
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取屬性再保接口業務處理接口.
	 * 
	 * @return 屬性再保接口業務處理接口的值
	 */
	public BLReinsService getBlReinsService() {
		return blReinsService;
	}

	/**
	 * 設置屬性再保接口業務處理接口.
	 * 
	 * @param blReinsService
	 *            待設置的再保接口業務處理接口的值
	 */
	public void setBlReinsService(BLReinsService blReinsService) {
		this.blReinsService = blReinsService;
	}

	/**
	 * 取自留額信息
	 * 
	 * @param strCondition
	 *            查詢條件
	 * @return 自留額信息
	 * @throws Exception
	 */
	public Collection getRetenValue(String strConditon) throws Exception {

		return blReinsService.getFhRetenValue(strConditon);

	}

	/**
	 * 獲取屬性基礎代碼表接口.
	 * 
	 * @return 屬性基礎代碼表接口的值
	 */
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	/**
	 * 設置屬性基礎代碼表接口.
	 * 
	 * @param prpDcodeService
	 *            待設置的基礎代碼表接口的值
	 */
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

}
