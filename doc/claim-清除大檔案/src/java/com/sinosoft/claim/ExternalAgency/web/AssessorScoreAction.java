package com.sinosoft.claim.ExternalAgency.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.ExternalAgency.util.AssessorScoreViewHelper;
import com.sinosoft.claim.schema.model.PrpLAssessorScore;
import com.sinosoft.claim.schema.service.facade.PrpLAssessorScoreService;

public class AssessorScoreAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**公估师收集*/
	private AssessorScoreViewHelper assessorScoreViewHelper;
	/**公估师评估收集*/
	private PrpLAssessorScoreService prpLAssessorScoreService;

	public String assessorScore() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String editType = httpServletRequest.getParameter("editType");

		if ("insertSave".equals(editType)) {
			PrpLAssessorScore prpLAssessorScore = this.assessorScoreViewHelper.viewToDto(httpServletRequest);
			prpLAssessorScoreService.save(prpLAssessorScore);
			return "result";
		} else if ("queryResult".equals(editType)) {
			String comCode = httpServletRequest.getParameter("ComCode");// 公估师代码
			String comCode1 = httpServletRequest.getParameter("NewComCode");// 公估机构代码
			String claimNo = httpServletRequest.getParameter("claimNo");// 赔案号
			String conditions = " a.comCode='" + comCode + "' AND a.comCode1='" + comCode1 + "' AND a.claimNo='" + claimNo + "'";
			int count = this.prpLAssessorScoreService.getCount(conditions);
			if (count == 0) {
				editType = "insert";
			} else {
				editType = "show";
			}

			assessorScoreViewHelper.assessorScoreDtoToView(httpServletRequest, editType);
			return editType;
		}
		return null;
	}

	public AssessorScoreViewHelper getAssessorScoreViewHelper() {
		return assessorScoreViewHelper;
	}

	public void setAssessorScoreViewHelper(AssessorScoreViewHelper assessorScoreViewHelper) {
		this.assessorScoreViewHelper = assessorScoreViewHelper;
	}

	public PrpLAssessorScoreService getPrpLAssessorScoreService() {
		return prpLAssessorScoreService;
	}

	public void setPrpLAssessorScoreService(PrpLAssessorScoreService prpLAssessorScoreService) {
		this.prpLAssessorScoreService = prpLAssessorScoreService;
	}

}
