package com.sinosoft.claim.ExternalAgency.util;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.PrpLAssessorScore;
import com.sinosoft.claim.schema.model.PrpLAssessorScoreId;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyorId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.service.facade.PrpLAssessorScoreService;
import com.sinosoft.claim.schema.service.facade.PrpLInsuranceSurveyorService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * <p>
 * Title: AssessorScoreViewHelper
 * </p>
 * <p>
 * Description:公估师评估ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2011
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class AssessorScoreViewHelper {
	/** 公估师基本信息服务 */
	private PrpLInsuranceSurveyorService prpLInsuranceSurveyorService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 公估师评估信息服务 */
	private PrpLAssessorScoreService prpLAssessorScoreService;

	public AssessorScoreViewHelper() {

	}

	public void assessorScoreDtoToView(HttpServletRequest httpServletRequest, String editType) throws Exception {
		/* 得到页面参数 */
		String comCode = httpServletRequest.getParameter("ComCode");// 公估师代码
		String comCode1 = httpServletRequest.getParameter("NewComCode");// 公估机构代码
		String claimNo = httpServletRequest.getParameter("claimNo");// 赔案号
		String comCName1 = httpServletRequest.getParameter("ComCName");// 公估师名称
		String comCName2 = httpServletRequest.getParameter("NewComCName");// 公估机构名称
		PrpLAssessorScore prpLAssessorScore = new PrpLAssessorScore();
		prpLAssessorScore.setEditType(editType);
		if ("show".equals(editType)) {
			PrpLAssessorScoreId prpLAssessorScoreId = new PrpLAssessorScoreId();
			prpLAssessorScoreId.setClaimNo(claimNo);
			prpLAssessorScoreId.setComCode(comCode);
			prpLAssessorScoreId.setComCode1(comCode1);
			prpLAssessorScore = this.prpLAssessorScoreService.findPrpLAssessorScore(prpLAssessorScoreId);
		} else if ("insert".equals(editType)) {
			prpLAssessorScore.getId().setClaimNo(claimNo);
			prpLAssessorScore.getId().setComCode(comCode);
			prpLAssessorScore.setComCName1(comCName1);
			prpLAssessorScore.getId().setComCode1(comCode1);
			prpLAssessorScore.setComCName2(comCName2);
			PrpLInsuranceSurveyorId prpLInsuranceSurveyorId = new PrpLInsuranceSurveyorId();
			prpLInsuranceSurveyorId.setComCode(comCode);
			prpLInsuranceSurveyorId.setNewcomcode(comCode1);
			PrpLInsuranceSurveyor prpLInsuranceSurveyor = this.prpLInsuranceSurveyorService.findPrpLInsuranceSurveyor(prpLInsuranceSurveyorId);
			prpLAssessorScore.setTelePhone(prpLInsuranceSurveyor.getTelephone());
		}

		PrpLclaim prpLclaimDto = this.prpLclaimService.findPrpLclaim(claimNo);
		httpServletRequest.setAttribute("prpLAssessorScoreDto", prpLAssessorScore);
		httpServletRequest.setAttribute("prpLclaimDto", prpLclaimDto);
	}

	public PrpLAssessorScore viewToDto(HttpServletRequest httpServletRequest) throws Exception {
		PrpLAssessorScore prpLAssessorScore = new PrpLAssessorScore();
		String[] score1s = httpServletRequest.getParameterValues("score1");// 配合度得分
		int score1 = 0;
		for (int i = 0; i < score1s.length; i++) {
			if (!"".equals(score1s[i].trim())) {
				score1 = Integer.parseInt(score1s[i]);
			}
		}

		String[] score2s = httpServletRequest.getParameterValues("score2");// 谈判技巧得分
		int score2 = 0;
		for (int i = 0; i < score2s.length; i++) {
			if (!"".equals(score2s[i].trim())) {
				score2 = Integer.parseInt(score2s[i]);
			}
		}

		String[] score3s = httpServletRequest.getParameterValues("score3");// 专业水平得分
		int score3 = 0;
		for (int i = 0; i < score3s.length; i++) {
			if (!"".equals(score3s[i].trim())) {
				score3 = Integer.parseInt(score3s[i]);
			}
		}

		String[] score4s = httpServletRequest.getParameterValues("score4");// 处理进度回报是否及时得分
		int score4 = 0;
		for (int i = 0; i < score4s.length; i++) {
			if (!"".equals(score4s[i].trim())) {
				score4 = Integer.parseInt(score4s[i]);
			}
		}

		String[] score5s = httpServletRequest.getParameterValues("score5");// 公估报告质量得分
		int score5 = 0;
		for (int i = 0; i < score5s.length; i++) {
			if (!"".equals(score5s[i].trim())) {
				score5 = Integer.parseInt(score5s[i]);
			}
		}

		String[] score6s = httpServletRequest.getParameterValues("score6");// 职业道德操守得分
		int score6 = 0;
		for (int i = 0; i < score6s.length; i++) {
			if (!"".equals(score6s[i].trim())) {
				score6 = Integer.parseInt(score6s[i]);
			}
		}

		String[] score7s = httpServletRequest.getParameterValues("score7");// 收费价格得分
		int score7 = 0;
		for (int i = 0; i < score7s.length; i++) {
			if (!"".equals(score7s[i].trim())) {
				score7 = Integer.parseInt(score7s[i]);
			}
		}

		double totalScore = Double.parseDouble(httpServletRequest.getParameter("totalScore"));// 总得分
		String remark = httpServletRequest.getParameter("remark");// 说明
		String company = httpServletRequest.getParameter("company");// 评估单位
		String comCode = httpServletRequest.getParameter("comCode");// 公估师代码
		String comCode1 = httpServletRequest.getParameter("comCode1");// 公估机构代码
		String claimNo = httpServletRequest.getParameter("claimNo");// 赔案号
		DateTime commitDate = new DateTime(httpServletRequest.getParameter("commitDate"));// 委托时间

		prpLAssessorScore.getId().setComCode(comCode);
		prpLAssessorScore.getId().setComCode1(comCode1);
		prpLAssessorScore.getId().setClaimNo(claimNo);
		prpLAssessorScore.setScore1(score1);
		prpLAssessorScore.setScore2(score2);
		prpLAssessorScore.setScore3(score3);
		prpLAssessorScore.setScore4(score4);
		prpLAssessorScore.setScore5(score5);
		prpLAssessorScore.setScore6(score6);
		prpLAssessorScore.setScore7(score7);
		prpLAssessorScore.setTotalScore(totalScore);
		prpLAssessorScore.setRemark(remark);
		prpLAssessorScore.setCompany(company);
		prpLAssessorScore.setCommitDate(commitDate);
		httpServletRequest.setAttribute("prpLAssessorScoreDto", prpLAssessorScore);
		return prpLAssessorScore;
	}

	public PrpLInsuranceSurveyorService getPrpLInsuranceSurveyorService() {
		return prpLInsuranceSurveyorService;
	}

	public void setPrpLInsuranceSurveyorService(PrpLInsuranceSurveyorService prpLInsuranceSurveyorService) {
		this.prpLInsuranceSurveyorService = prpLInsuranceSurveyorService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLAssessorScoreService getPrpLAssessorScoreService() {
		return prpLAssessorScoreService;
	}

	public void setPrpLAssessorScoreService(PrpLAssessorScoreService prpLAssessorScoreService) {
		this.prpLAssessorScoreService = prpLAssessorScoreService;
	}

}
