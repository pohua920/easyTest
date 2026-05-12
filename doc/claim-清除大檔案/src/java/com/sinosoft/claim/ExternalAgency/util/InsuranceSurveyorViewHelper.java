package com.sinosoft.claim.ExternalAgency.util;

import ins.framework.common.Page;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.ExternalAgency.service.facade.InsuranceSurveyorService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * <p>
 * Title: InsuranceSurveyorViewHelper
 * </p>
 * <p>
 * Description:公估师ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2011
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class InsuranceSurveyorViewHelper {
	/** 公估师信息服务 */
	private InsuranceSurveyorService insuranceSurveyorService;

	public InsuranceSurveyorService getInsuranceSurveyorService() {
		return insuranceSurveyorService;
	}

	public void setInsuranceSurveyorService(InsuranceSurveyorService insuranceSurveyorService) {
		this.insuranceSurveyorService = insuranceSurveyorService;
	}

	public InsuranceSurveyorViewHelper() {

	}

	public PrpLInsuranceSurveyor viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		PrpLInsuranceSurveyor prpLInsuranceSurveyor = new PrpLInsuranceSurveyor();
		String comCode = StringUtils.rightTrim(httpServletRequest.getParameter("ComCode"));// 公估师代码
		String newComCode = StringUtils.rightTrim(httpServletRequest.getParameter("NewComCode"));// 公估机构代码
		String comCName = StringUtils.rightTrim(httpServletRequest.getParameter("ComCName"));// 中文名称
		String comEName = StringUtils.rightTrim(httpServletRequest.getParameter("ComEName"));// 英文名称
		String telePhone = StringUtils.rightTrim(httpServletRequest.getParameter("Telephone"));// 联系电话
		String eMail = StringUtils.rightTrim(httpServletRequest.getParameter("EMail"));// EMail
		String validStatus = StringUtils.rightTrim(httpServletRequest.getParameter("Validstatus"));// 是否有效
		String editType = httpServletRequest.getParameter("editType");
		if ("insertSave".equals(editType)) {// 如果是新增
			int count = insuranceSurveyorService.getCount("NewComCode='" + newComCode + "'");
			count = count + 1;
			if (count < 10) {
				comCode = newComCode + "-" + "000" + count;
			} else if (count >= 10 && count < 100) {
				comCode = newComCode + "-" + "00" + count;
			} else if (count >= 100 && count < 1000) {
				comCode = newComCode + "-" + "0" + count;
			} else {
				comCode = newComCode + "-" + count;
			}
		}

		prpLInsuranceSurveyor.getId().setComCode(comCode);
		prpLInsuranceSurveyor.getId().setNewcomcode(newComCode);
		prpLInsuranceSurveyor.setComcname(comCName);
		prpLInsuranceSurveyor.setComename(comEName);
		prpLInsuranceSurveyor.setTelephone(telePhone);
		prpLInsuranceSurveyor.setEmail(eMail);
		prpLInsuranceSurveyor.setValidStatus(validStatus);
		httpServletRequest.setAttribute("prpLInsuranceSurveyor", prpLInsuranceSurveyor);
		return prpLInsuranceSurveyor;
	}

	public Page insuranceSurveyorDtoToView(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) {
		/* 得到页面参数 */
		try {
			String newComCode = httpServletRequest.getParameter("NewComCode");// 公估机构代码
			String comCode = httpServletRequest.getParameter("ComCode");// 公估师代码
			String comCName = com.sinosoft.claim.common.util.StringConvert.getParam(httpServletRequest,"ComCName",ConstantCodes.YUI_CHARSET);// 中文名称
			String comType = httpServletRequest.getParameter("ComType"); // 公估类型
			String validStatus = httpServletRequest.getParameter("Validstatus");// 是否有效
			/* 得到页面选择查询情况 */
			String comCodeSign = httpServletRequest.getParameter("ComCodeSign");
			String comCNameSign = httpServletRequest.getParameter("ComCNameSign");

			StringBuilder conditions = new StringBuilder();
			conditions.append(" 1=1 ");
			conditions.append(StringConvert.convertString("a.newComCode", newComCode, null));
			conditions.append(StringConvert.convertString("a.comCode", comCode, comCodeSign));
			conditions.append(StringConvert.convertString("a.comCName", comCName, comCNameSign));
			conditions.append(StringConvert.convertString("b.comType", comType, null));
			conditions.append(StringConvert.convertString("a.validStatus", validStatus, null));
			Page page = this.insuranceSurveyorService.findByQueryConditions(conditions, pageNo, recordPerPage);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insuranceSurveyorUpdate(HttpServletRequest httpServletRequest) throws Exception {
		String comCodeString = httpServletRequest.getParameter("comCode");
		String newcomcodeString = httpServletRequest.getParameter("newcomcode");
		PrpLInsuranceSurveyor prpLInsuranceSurveyor = new PrpLInsuranceSurveyor();
		prpLInsuranceSurveyor = insuranceSurveyorService.findByPrimaryKey(comCodeString, newcomcodeString);
		httpServletRequest.setAttribute("prpLInsuranceSurveyor", prpLInsuranceSurveyor);
	}

	public void insuranceSurveyorShow(HttpServletRequest httpServletRequest) throws Exception {
		String comCode = httpServletRequest.getParameter("comCode");
		String newComCode = httpServletRequest.getParameter("newcomcode");
		PrpLInsuranceSurveyor prpLInsuranceSurveyor = new PrpLInsuranceSurveyor();
		prpLInsuranceSurveyor = insuranceSurveyorService.findByPrimaryKey(comCode, newComCode);
		httpServletRequest.setAttribute("prpLInsuranceSurveyor", prpLInsuranceSurveyor);
	}

}
