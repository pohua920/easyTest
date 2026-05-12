package com.sinosoft.claim.ExternalAgency.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.ExternalAgency.service.facade.InsuranceSurveyorService;
import com.sinosoft.claim.ExternalAgency.util.InsuranceSurveyorViewHelper;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.sysframework.reference.AppConfig;

@SuppressWarnings("serial")
public class InsuranceSurveyorAction extends Struts2Action {
	/** 外部机构服务 */
	private InsuranceSurveyorService insuranceSurveyorService;
	/** 外部机构数据收集*/
	private InsuranceSurveyorViewHelper insuranceSurveyorViewHelper;

	public InsuranceSurveyorViewHelper getInsuranceSurveyorViewHelper() {
		return insuranceSurveyorViewHelper;
	}

	public void setInsuranceSurveyorViewHelper(InsuranceSurveyorViewHelper insuranceSurveyorViewHelper) {
		this.insuranceSurveyorViewHelper = insuranceSurveyorViewHelper;
	}

	public InsuranceSurveyorService getInsuranceSurveyorService() {
		return insuranceSurveyorService;
	}

	public void setInsuranceSurveyorService(InsuranceSurveyorService insuranceSurveyorService) {
		this.insuranceSurveyorService = insuranceSurveyorService;
	}

	public String insuranceSurveyor() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String editType = httpServletRequest.getParameter("editType");
		Page page = null;
		if ("insertSave".equals(editType)) {
			PrpLInsuranceSurveyor prpLInsuranceSurveyor = insuranceSurveyorViewHelper.viewToDto(httpServletRequest);
			insuranceSurveyorService.insert(prpLInsuranceSurveyor);
			return "result";
		} else if ("queryResult".equals(editType)) {
			String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
			String pageno = httpServletRequest.getParameter("pageNo");
			if (pageno == null) {
				pageno = "1";
			}
			int intRecordPerPage = Integer.parseInt(recordPerPage);
			int intPageNo = Integer.parseInt(pageno);
			page = this.insuranceSurveyorViewHelper.insuranceSurveyorDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			this.writeJSONData(page, "id", "comcname", "newComCName", "comType", "validStatus");
			return NONE;
		} else if ("update".equals(editType)) {
			insuranceSurveyorViewHelper.insuranceSurveyorUpdate(httpServletRequest);
			return editType;
		} else if ("updateSave".equals(editType)) {
			PrpLInsuranceSurveyor prpLInsuranceSurveyor = insuranceSurveyorViewHelper.viewToDto(httpServletRequest);
			insuranceSurveyorService.update(prpLInsuranceSurveyor);
			return "result";
		} else if ("show".equals(editType)) {
			insuranceSurveyorViewHelper.insuranceSurveyorShow(httpServletRequest);
			return editType;
		}
		return null;
	}

}
