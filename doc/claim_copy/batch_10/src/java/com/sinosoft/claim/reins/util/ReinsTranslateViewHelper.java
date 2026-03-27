package com.sinosoft.claim.reins.util;

import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.reins.vo.ReinsClaimDetail;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 收集数据的类
 * @author 中科软
 *
 */
public class ReinsTranslateViewHelper {

	/**
	 *  获取分赔需要的主信息
	 * @param claimDto
	 * @param workFlowDto
	 * @return
	 */
	public static ReinsClaimMain getClaimMainCollection(ClaimDto claimDto, WorkFlowDto workFlowDto) {

		ReinsClaimMain reinsClaimMain = new ReinsClaimMain();
		reinsClaimMain.setClaimNo(claimDto.getPrpLclaim().getClaimNo());
		reinsClaimMain.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		reinsClaimMain.setCertiType(ReinsClaimMain.CertiType.CLAIM);
		reinsClaimMain.setCertiNo(claimDto.getPrpLclaim().getClaimNo());

		reinsClaimMain.setDamageDate(new DateTime(claimDto.getPrpLclaim().getDamageStartDate()));
		reinsClaimMain.setDamageCode(claimDto.getPrpLclaim().getDamageCode());
		reinsClaimMain.setDamageReason(claimDto.getPrpLclaim().getDamageName());
		reinsClaimMain.setPostCode(claimDto.getPrpLclaim().getAddressCode());
		reinsClaimMain.setAddressName(claimDto.getPrpLclaim().getAgentName());
		if (claimDto.getPrpLclaim().getEndCaseFlag() == null || claimDto.getPrpLclaim().getEndCaseFlag().equals("0") || claimDto.getPrpLclaim().getEndCaseFlag().equals("")) {
			reinsClaimMain.setEndCaseFlag(Boolean.FALSE);
		} else {
			reinsClaimMain.setEndCaseFlag(Boolean.TRUE);
		}
		reinsClaimMain.setMakeComCode(claimDto.getPrpLclaim().getMakeCom());
		reinsClaimMain.setCreaterCode(claimDto.getPrpLclaim().getHandlerCode());
		reinsClaimMain.setCreateDate(new DateTime(claimDto.getPrpLclaim().getInputDate()));
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		for (int i = 0; i < claimDto.getPrpLclaimLossList().size(); i++) {
			PrpLclaimLoss prpLclaimLoss = claimDto.getPrpLclaimLossList().get(i);
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			// modify by xukefeng 20060306 start 拆分多条危险单位
			reinsClaimDetail.setDangerNo(new Integer(prpLclaimLoss.getDangerNo()));
			// modify by xukefeng 20060306 end
			reinsClaimDetail.setKindCode(prpLclaimLoss.getKindCode());
			reinsClaimDetail.setKindName(prpLclaimLoss.getKindName());
			reinsClaimDetail.setItemName(prpLclaimLoss.getItemDetailName());
			if (prpLclaimLoss.getLossFeeType().equals("P")) {
				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);
			} else {
				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.CHARGE);
			}
			reinsClaimDetail.setCurrency(prpLclaimLoss.getCurrency());
			reinsClaimDetail.setSumPaid(new Double(prpLclaimLoss.getSumClaim()));
			reinsClaimDetailList.add(reinsClaimDetail);
		}
		reinsClaimMain.setReinsClaimDetailList(reinsClaimDetailList);
		return reinsClaimMain;
	}

	public static ReinsClaimSummary getReinsClaimSummary(ClaimDto claimDto) {
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		reinsClaimSummary.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		reinsClaimSummary.setDamageDate(new DateTime(claimDto.getPrpLclaim().getDamageStartDate()));
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		for (int i = 0; i < claimDto.getPrpLclaimLossList().size(); i++) {
			PrpLclaimLoss prpLclaimLoss = claimDto.getPrpLclaimLossList().get(i);
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLclaimLoss.getKindCode());
			reinsClaimDetail.setKindName(prpLclaimLoss.getKindName());
			reinsClaimDetail.setItemName(prpLclaimLoss.getItemDetailName());
			if (prpLclaimLoss.getLossFeeType().equals("P")) {
				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);
			} else {
				reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.CHARGE);
			}
			reinsClaimDetail.setCurrency(prpLclaimLoss.getCurrency());
			reinsClaimDetail.setSumPaid(new Double(prpLclaimLoss.getSumClaim()));
			reinsClaimDetailList.add(reinsClaimDetail);
		}
		reinsClaimSummary.setReinsClaimDetailList(reinsClaimDetailList);
		return reinsClaimSummary;
	}

	public static ReinsClaimSummary getReinsClaimSummary(String businessNo) throws Exception {
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		PrpLcompensateService prpLcompensateService = (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		// 静态方法，不是要方法注入，自动获取
		PrpLclaimService prpLclaimService = (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		// 得到出险摘要

		// 得到是否结案 true已结/false未结
//		boolean isEndCase = false;
		if (prpLclaim.getEndCaseDate() != null) {
//			isEndCase = true;
		}

		reinsClaimSummary.setPolicyNo(prpLcompensate.getPolicyNo());
		reinsClaimSummary.setCurrency(prpLcompensate.getCurrency());
		String damageStartHourTemp = prpLclaim.getDamageStartHour()==null?"00:00:00":prpLclaim.getDamageStartHour().trim();
		if(damageStartHourTemp.length()<=2){
			damageStartHourTemp = damageStartHourTemp+":00:00";
		}
		reinsClaimSummary.setDamageDate(new DateTime(prpLclaim.getDamageStartDate().toString() + " " + damageStartHourTemp, DateTime.YEAR_TO_SECOND));
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", businessNo);
		PrpLlossService prpLlossService = (PrpLlossService) ServiceFactory.getService("prpLlossService");
		List<PrpLloss> prpLlossList = prpLlossService.findPrpLloss(queryRule);
		// Loss表明细
		double sumPaid = 0.0;
		for (int i = 0; i < prpLlossList.size(); i++) {
			PrpLloss prpLloss = prpLlossList.get(i);
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLloss.getKindCode());
			reinsClaimDetail.setKindName(prpLloss.getKindName());
			reinsClaimDetail.setItemName(prpLloss.getItemCode());
			reinsClaimDetail.setCurrency(prpLloss.getCurrency4());
			sumPaid = prpLloss.getSumRealPay() + prpLloss.getExceptDeductiblePay();
			reinsClaimDetail.setSumPaid(sumPaid);

			reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);

			reinsClaimDetail.setDangerNo(prpLloss.getDangerNo());// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}
		PrpLpersonLossService prpLpersonLossService = (PrpLpersonLossService) ServiceFactory.getService("prpLpersonLossService");
		// PersonLoss表明细
		List<PrpLpersonLoss> prpLpersonLossList = prpLpersonLossService.findPrpLpersonLoss(queryRule);
		for (int i = 0; i < prpLpersonLossList.size(); i++) {
			PrpLpersonLoss prpLpersonLoss = prpLpersonLossList.get(i);
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLpersonLoss.getKindCode());
			reinsClaimDetail.setKindName(prpLpersonLoss.getKindName());
			reinsClaimDetail.setCurrency(prpLpersonLoss.getCurrency4());
			sumPaid = prpLpersonLoss.getSumRealPay() + prpLpersonLoss.getExceptDeductiblePay();
			reinsClaimDetail.setSumPaid(sumPaid);
			reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.PAY);

			reinsClaimDetail.setDangerNo(prpLpersonLoss.getDangerNo());// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}

		// Charge表明细
		PrpLchargeService prpLchargeService = (PrpLchargeService) ServiceFactory.getService("prpLchargeService");
		List<PrpLcharge> prpLchargeList = prpLchargeService.findPrpLcharge(queryRule);
		for (int i = 0; i < prpLchargeList.size(); i++) {
			PrpLcharge prpLcharge = prpLchargeList.get(i);
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setKindCode(prpLcharge.getKindCode());
			reinsClaimDetail.setKindName(prpLcharge.getKindName());

			reinsClaimDetail.setCurrency(prpLcharge.getCurrency());
			// modify by liping 2008-04-03 start
			sumPaid = prpLcharge.getSumRealPay() + prpLcharge.getExceptDeductiblePay();
			reinsClaimDetail.setSumPaid(sumPaid);
			// modify by liping 2008-04-03 end
			reinsClaimDetail.setPayType(ReinsClaimDetail.PayType.CHARGE);

			reinsClaimDetail.setDangerNo(prpLcharge.getDangerNo());// 危险单位序号
			reinsClaimDetailList.add(reinsClaimDetail);
		}
		reinsClaimSummary.setReinsClaimDetailList(reinsClaimDetailList);
		return reinsClaimSummary;
	}

	// 注销/拒赔任务中送再保结案标志
	public static ReinsCaseStatus getReinsCaseStatus(ClaimDto claimDto) {
		ReinsCaseStatus reinsCaseStatus = new ReinsCaseStatus();
		reinsCaseStatus.setClaimNo(claimDto.getPrpLclaim().getClaimNo());
		if ("1".equals(claimDto.getPrpLclaim().getCaseType())) {
			reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.REFUSE);
		} else {
			reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.CANCLE);
		}
		reinsCaseStatus.setOperaterCode(claimDto.getPrpLclaim().getEndCaserCode());
		reinsCaseStatus.setOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		return reinsCaseStatus;
	}

	public static ReinsCaseStatus getReinsCaseStatus(EndcaseDto endcaseDto) throws Exception {

		ReinsCaseStatus reinsCaseStatus = new ReinsCaseStatus();
		reinsCaseStatus.setClaimNo(endcaseDto.getPrpLclaim().getClaimNo());
		reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.ENDCASE);
		reinsCaseStatus.setOperateComCode(endcaseDto.getPrpLclaimStatus().getComCode());
		reinsCaseStatus.setOperaterCode(endcaseDto.getPrpLclaimStatus().getHandlerCode());
		reinsCaseStatus.setOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		return reinsCaseStatus;

	}

	public static ReinsCaseStatus getReinsCaseStatus(ReCaseDto recaseDto) throws SQLException, Exception {
		ReinsCaseStatus reinsCaseStatus = new ReinsCaseStatus();
		reinsCaseStatus.setClaimNo(recaseDto.getPrpLrecase().getId().getClaimNo());
		reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.REOPEN);
		reinsCaseStatus.setOperateComCode(recaseDto.getPrpLrecase().getOpenCaseComCode());
		reinsCaseStatus.setOperateDate(new DateTime(recaseDto.getPrpLrecase().getOpenCaseDate()));
		reinsCaseStatus.setOperaterCode(recaseDto.getPrpLrecase().getOpenCaseUserCode());
		return reinsCaseStatus;
	}

	/**
	 * 增加核赔通过再保临分业务提示的理算数据组织 add by liping 080410
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	public static ReinsClaimSummary getCompensateReinsClaimSummary(String businessNo) throws Exception {
		ReinsClaimSummary reinsClaimSummary = new ReinsClaimSummary();
		List<ReinsClaimDetail> reinsClaimDetailList = new ArrayList<ReinsClaimDetail>();
		PrpLcompensateService prpLcompensateService = (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		if (prpLcompensate != null) {
			reinsClaimSummary.setPolicyNo(prpLcompensate.getPolicyNo());
			reinsClaimSummary.setDamageDate(new DateTime(prpLcompensate.getDamageStartDate()));
			ReinsClaimDetail reinsClaimDetail = new ReinsClaimDetail();
			reinsClaimDetail.setCurrency(prpLcompensate.getCurrency());
			reinsClaimDetail.setSumPaid(prpLcompensate.getSumPaid());
			// 与再保确定，危险单位可固定为“1”
			String dangerNo = "1";
			reinsClaimDetail.setDangerNo(new Integer(dangerNo));
			reinsClaimDetailList.add(reinsClaimDetail);
			reinsClaimSummary.setReinsClaimDetailList(reinsClaimDetailList);
		}
		return reinsClaimSummary;
	}
}
