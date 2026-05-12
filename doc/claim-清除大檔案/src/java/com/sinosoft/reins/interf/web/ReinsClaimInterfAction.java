package com.sinosoft.reins.interf.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.reins.base.model.FhSection;
import com.sinosoft.reins.base.model.FhSectionId;
import com.sinosoft.reins.base.model.FhTreaty;
import com.sinosoft.reins.base.service.facade.BLFcoRecoinsService;
import com.sinosoft.reins.base.service.facade.BLFdConfigService;
import com.sinosoft.reins.base.service.facade.FhSectionService;
import com.sinosoft.reins.base.service.facade.FhTreatyService;
import com.sinosoft.reins.common.model.PrpCDangerItem;
import com.sinosoft.reins.common.model.PrpCDangerUnit;
import com.sinosoft.reins.common.model.PrpCReinsShare;
import com.sinosoft.reins.common.model.PrpCReinsTrial;
import com.sinosoft.reins.common.service.facade.PubRateService;
import com.sinosoft.reins.interf.model.CDanger;
import com.sinosoft.reins.interf.service.facade.BLCTrialService;
import com.sinosoft.reins.interf.service.facade.BLDangerService;
import com.sinosoft.reins.interf.service.facade.BLLDangerService;
import com.sinosoft.reins.interf.vo.CReinsTrialVO;
import com.sinosoft.reins.interf.vo.ClaimDangerItemVO;
import com.sinosoft.reins.interf.vo.ClaimDangerUnitVO;
import com.sinosoft.reins.interf.vo.ClaimDetailVO;
import com.sinosoft.reins.interf.vo.ClaimLargeLossVO;
import com.sinosoft.reins.interf.vo.ClaimRepayCalResultVO;
import com.sinosoft.reins.interf.vo.ClaimSummaryVO;
import com.sinosoft.reins.interf.vo.LDangerVO;
import com.sinosoft.reins.othSystemInterf.prpall.service.facade.PrpallReinsInterfService;
import com.sinosoft.reins.othSystemInterf.prpall.vo.PrpCmainVO;
import com.sinosoft.reins.out.facultative.enquiry.model.FeoEnquiry;
import com.sinosoft.reins.out.facultative.enquiry.service.facade.FeoEnquiryService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;

public class ReinsClaimInterfAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private BLDangerService blDangerService;
	private FeoEnquiryService feoEnquiryService;
	private BLFcoRecoinsService blFcoRecoinsService;
	private BLCTrialService blCTrialService;
	private FhTreatyService fhTreatyService;
	private FhSectionService fhSectionService;
	private PubRateService pubRateService;
	private PrpallReinsInterfService prpallReinsInterfService;
	private BLLDangerService blLDangerService;
	private BLFdConfigService blFdConfigService;

	public Collection getDangerUnitForAcci(String policyNo, DateTime damageDate) throws Exception {
		Collection claimDangerUnitList = new ArrayList();
		Collection claimDangerItemList = null;
		Collection prpCdangerItemDtoList = null;
		PrpCDangerUnit prpCdangerUnit = null;
		Collection cDangerList = this.blDangerService.getCDangerList(policyNo, damageDate);
		if (cDangerList.size() > 0) {
			Iterator itCdanger = cDangerList.iterator();
			while (itCdanger.hasNext()) {
				CDanger cDanger = (CDanger) itCdanger.next();
				prpCdangerUnit = cDanger.getPrpCDangerUnit();
				ClaimDangerUnitVO claimDangerUnit = new ClaimDangerUnitVO();
				claimDangerUnit.setPolicyNo(policyNo);
				claimDangerUnit.setDangerNo(prpCdangerUnit.getId().getDangerNo().intValue());
				claimDangerUnit.setDangerDesc(prpCdangerUnit.getDangerDesc());
				claimDangerUnit.setAddressName(prpCdangerUnit.getAddressName());
				claimDangerUnit.setCurrency(prpCdangerUnit.getCurrency());
				claimDangerUnit.setAmount(prpCdangerUnit.getAmount().doubleValue());
				claimDangerUnit.setPremium(prpCdangerUnit.getPremium().doubleValue());
				claimDangerUnit.setDangerShare(prpCdangerUnit.getDangerShare().doubleValue());
				// 考慮效率問題，這個就暫時不查了。
//				claimDangerUnit.setClaimDangerItemList(claimDangerItemList);
				claimDangerUnitList.add(claimDangerUnit);
			}
		}
		return claimDangerUnitList;
	}
	
	public Collection getDangerUnit(String policyNo, DateTime damageDate) throws Exception {
		Collection claimDangerUnitList = new ArrayList();
		Collection claimDangerItemList = null;
		Collection prpCdangerItemDtoList = null;
		PrpCDangerUnit prpCdangerUnit = null;

		Collection cDangerList = this.blDangerService.getCDangerList(policyNo, damageDate);
		if (cDangerList.size() > 0) {
			Iterator itCdanger = cDangerList.iterator();
			while (itCdanger.hasNext()) {
				CDanger cDanger = (CDanger) itCdanger.next();
				prpCdangerUnit = cDanger.getPrpCDangerUnit();

				ClaimDangerUnitVO claimDangerUnit = new ClaimDangerUnitVO();

				claimDangerUnit.setPolicyNo(policyNo);
				claimDangerUnit.setDangerNo(prpCdangerUnit.getId().getDangerNo().intValue());
				claimDangerUnit.setDangerDesc(prpCdangerUnit.getDangerDesc());
				claimDangerUnit.setAddressName(prpCdangerUnit.getAddressName());
				claimDangerUnit.setAmount(prpCdangerUnit.getAmount().doubleValue());
				claimDangerUnit.setPremium(prpCdangerUnit.getPremium().doubleValue());
				claimDangerUnit.setDangerShare(prpCdangerUnit.getDangerShare().doubleValue());

				prpCdangerItemDtoList = cDanger.getPrpCDangerItemList();

				claimDangerItemList = new ArrayList();
				if (prpCdangerItemDtoList !=null && prpCdangerItemDtoList.size() != 0) {
					Iterator itItem = prpCdangerItemDtoList.iterator();
					while (itItem.hasNext()) {
						PrpCDangerItem prpCdangerItem = (PrpCDangerItem) itItem.next();
						ClaimDangerItemVO claimDangerItem = new ClaimDangerItemVO();

						claimDangerItem.setKindCode(prpCdangerItem.getKindCode());
						claimDangerItem.setKindName(prpCdangerItem.getKindName());
						claimDangerItem.setItemCode(prpCdangerItem.getItemCode());
						claimDangerItem.setItemDetailName(prpCdangerItem.getItemDetailName());
						claimDangerItem.setCurrency(prpCdangerItem.getCurrency());
						claimDangerItem.setAmount(prpCdangerItem.getAmount().doubleValue());
						claimDangerItem.setPremium(prpCdangerItem.getPremium().doubleValue());
						claimDangerItem.setCalculateFlag(prpCdangerItem.getCalculateFlag());

						if (this.blFdConfigService.isCarRiskCode(prpCdangerItem.getRiskCode())) {
							claimDangerItem.setRiskLevel(prpCdangerItem.getRiskLevel());
							claimDangerItem.setRiskLevelDesc(prpCdangerItem.getRiskLevelDesc());
							claimDangerItem.setRiskClass(prpCdangerItem.getRiskClass());
							claimDangerItem.setRiskClassDesc(prpCdangerItem.getRiskClassDesc());
							claimDangerItem.setRetentionValue(prpCdangerItem.getRetentionValue().doubleValue());
						}

						claimDangerItemList.add(claimDangerItem);
					}
				}
				claimDangerUnit.setClaimDangerItemList(claimDangerItemList);

				claimDangerUnitList.add(claimDangerUnit);
			}
		}
		return claimDangerUnitList;
	}

	public double getSumFacShare(String policyNo, DateTime damageDate) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		double sumFacShare = 0.0D;
		String endorseNo = "";
		FeoEnquiry feoEnquiry = null;
		Collection feoEnquiryList = null;
		String conditions = "";

		endorseNo = this.blDangerService.getRecentlyEndorseNo(policyNo, 1, damageDate);

		if (!(endorseNo.equals(""))) {
			conditions = " endorseNo = '" + endorseNo + "' ";
		} else {
			conditions = " policyNo = '" + policyNo + "' and endorseNo is null ";
		}
		queryRule.addSql(conditions);
		feoEnquiryList = this.feoEnquiryService.findByConditions(queryRule);
		if (feoEnquiryList != null) {
			Iterator itFac = feoEnquiryList.iterator();
			if (itFac.hasNext()) {
				feoEnquiry = (FeoEnquiry) itFac.next();
				sumFacShare = feoEnquiry.getFacShare().doubleValue();
			}
		}

		return sumFacShare;
	}

	public Collection getLargeCashLoss(Collection claimSummaryList) throws Exception {
		PrpCReinsShare prpCReinsShare = null;
		String policyNo = "";
		int dangerNo = 0;
		DateTime damageDate = null;
		String currency = "";
		double sumClaim = 0.0D;
		String kindCode = "";
		Collection prpCreinsShareList = null;
		String treatyNo = "";
		String sectionNo = "";
		FhTreaty fhTreaty = null;
		FhSection fhSection = null;
		double exchRate = 0.0D;
		String largeLoss = "0";
		String cashLoss = "0";
		Collection claimLargeLossList = new ArrayList();

		double baseRate = 0.0D;
		double treatySumClaim = 0.0D;
		if ((claimSummaryList != null) && (claimSummaryList.size() > 0)) {
			Iterator itLargeLoss = claimSummaryList.iterator();
			while (itLargeLoss.hasNext()) {
				ClaimSummaryVO claimSummary = (ClaimSummaryVO) itLargeLoss.next();
				policyNo = claimSummary.getPolicyNo();
				dangerNo = claimSummary.getDangerNo();
				damageDate = claimSummary.getDamageDate();
				currency = claimSummary.getCurrency();
				sumClaim = claimSummary.getSumClaim();
				kindCode = claimSummary.getKindCode();

				baseRate = this.blFcoRecoinsService.getBaseRate(policyNo);

				sumClaim = sumClaim * baseRate / 100.0D;

				CReinsTrialVO cReinsTrial = this.blCTrialService.getCTrialInfo(policyNo, dangerNo, damageDate);
				prpCreinsShareList = cReinsTrial.getPrpCreinsShareList();
				if (prpCreinsShareList == null)
					continue;
				Iterator itShare = prpCreinsShareList.iterator();
				while (itShare.hasNext()) {
					prpCReinsShare = (PrpCReinsShare) itShare.next();

					if (!(kindCode.equals(prpCReinsShare.getId().getKindCode()))) {
						continue;
					}
					if (!(prpCReinsShare.getReinsMode().substring(0, 1).equals("2"))) {
						continue;
					}

					treatyNo = prpCReinsShare.getTreatyNo();
					sectionNo = prpCReinsShare.getSectionNo();
					fhTreaty = this.fhTreatyService.findByConditions(treatyNo);
					FhSectionId fhSectionId = new FhSectionId();
					fhSectionId.setTreatyNo(treatyNo);
					fhSectionId.setSectionNo(sectionNo);
					fhSection = this.fhSectionService.findByConditions(fhSectionId);

					exchRate = this.pubRateService.getStraightExchangeRate(currency, fhTreaty.getCurrency(), damageDate);

					sumClaim *= exchRate;

					treatySumClaim = sumClaim * prpCReinsShare.getShareRate().doubleValue() / 100.0D;
					if (fhSection.getCashLossFlag().equals("0")) {
						if (sumClaim >= fhSection.getLargeLossValue().doubleValue()) {
							cashLoss = "1";
						}
					} else if (treatySumClaim >= fhSection.getLargeLossValue().doubleValue()) {
						largeLoss = "1";
					}

					if (fhSection.getCashLossFlag().equals("0")) {
						if (sumClaim >= fhSection.getCashLossValue().doubleValue()) {
							cashLoss = "1";
						}
					} else if (treatySumClaim >= fhSection.getCashLossValue().doubleValue()) {
						largeLoss = "1";
					}

					ClaimLargeLossVO claimLargeLoss = new ClaimLargeLossVO();
					claimLargeLoss.setPolicyNo(policyNo);
					claimLargeLoss.setDangerNo(dangerNo);
					claimLargeLoss.setTreatyName(fhTreaty.getTreatyName());
					claimLargeLoss.setLargeLoss(largeLoss);
					claimLargeLoss.setCashLoss(cashLoss);
					claimLargeLossList.add(claimLargeLoss);
				}

			}

		}

		return claimLargeLossList;
	}

	public Collection repaySimulate(Collection claimSummaryList) throws Exception {
		Collection prpCreinsTrialList = null;
		PrpCReinsTrial prpCreinsTrial = null;
		String policyNo = "";
		int dangerNo = 0;
		DateTime damageDate = null;
		String currency = "";
		double sumClaim = 0.0D;
		double totalPaid = 0.0D;
		String reinsMode = "";
		Collection claimRepayCalResultList = new ArrayList();
		FhTreaty fhTreaty = null;
		double baseRate = 0.0D;
		if ((claimSummaryList != null) && (claimSummaryList.size() > 0)) {
			Iterator itRepayCal = claimSummaryList.iterator();
			while (itRepayCal.hasNext()) {
				ClaimSummaryVO claimSummary = (ClaimSummaryVO) itRepayCal.next();
				policyNo = claimSummary.getPolicyNo();
				dangerNo = claimSummary.getDangerNo();
				damageDate = claimSummary.getDamageDate();
				currency = claimSummary.getCurrency();
				sumClaim = claimSummary.getSumClaim();

				baseRate = this.blFcoRecoinsService.getBaseRate(policyNo);

				sumClaim = sumClaim * baseRate / 100.0D;

				totalPaid = 0.0D;

				CReinsTrialVO cReinsTrial = this.blCTrialService.getCTrialInfo(policyNo, dangerNo, damageDate);
				prpCreinsTrialList = cReinsTrial.getPrpCreinsTrialList();
				if ((prpCreinsTrialList == null) || (prpCreinsTrialList.size() <= 0))
					continue;
				Iterator itTrial = prpCreinsTrialList.iterator();
				ClaimRepayCalResultVO claimRepayCalResult;
				while (itTrial.hasNext()) {
					prpCreinsTrial = (PrpCReinsTrial) itTrial.next();
					if ("181".equals(prpCreinsTrial.getReinsMode()))
						continue;
					claimRepayCalResult = new ClaimRepayCalResultVO();
					claimRepayCalResult.setPolicyNo(policyNo);
					claimRepayCalResult.setDangerNo(dangerNo);
					if (prpCreinsTrial.getReinsMode().startsWith("3")) {
						reinsMode = getText("prompt.java44");
					} else if (prpCreinsTrial.getReinsMode().startsWith("2")) {
						reinsMode = getText("prompt.java31");
					} else if (prpCreinsTrial.getReinsMode().startsWith("0")) {
						reinsMode = getText("prompt.java65");
					} else if (prpCreinsTrial.getReinsMode().startsWith("1")) {
						reinsMode = getText("prompt.java66");
					}

					claimRepayCalResult.setReinsMode(reinsMode);
					claimRepayCalResult.setTreatyNo(prpCreinsTrial.getTreatyNo());
					fhTreaty = this.fhTreatyService.findByConditions(claimRepayCalResult.getTreatyNo());
					claimRepayCalResult.setTreatyName(fhTreaty.getTreatyName());
					claimRepayCalResult.setShareRate(prpCreinsTrial.getShareRate().doubleValue());
					claimRepayCalResult.setCurrency(currency);

					claimRepayCalResult.setSumPaid(DataUtils.round(sumClaim * prpCreinsTrial.getShareRate().doubleValue() / 100.0D, 2));
					claimRepayCalResultList.add(claimRepayCalResult);
					totalPaid += DataUtils.round(claimRepayCalResult.getSumPaid(), 2);
				}

				itTrial = prpCreinsTrialList.iterator();
				while (itTrial.hasNext()) {
					prpCreinsTrial = (PrpCReinsTrial) itTrial.next();
					if (!("181".equals(prpCreinsTrial.getReinsMode())))
						continue;
					claimRepayCalResult = new ClaimRepayCalResultVO();
					claimRepayCalResult.setPolicyNo(policyNo);
					claimRepayCalResult.setDangerNo(dangerNo);
					claimRepayCalResult.setReinsMode(getText("prompt.java67"));
					claimRepayCalResult.setTreatyNo(prpCreinsTrial.getTreatyNo());
					fhTreaty = this.fhTreatyService.findByConditions(claimRepayCalResult.getTreatyNo());
					claimRepayCalResult.setTreatyName(fhTreaty.getTreatyName());
					claimRepayCalResult.setShareRate(prpCreinsTrial.getShareRate().doubleValue());
					claimRepayCalResult.setCurrency(currency);

					claimRepayCalResult.setSumPaid(DataUtils.round(sumClaim - totalPaid, 2));

					claimRepayCalResultList.add(claimRepayCalResult);
					break;
				}
			}

		}

		return claimRepayCalResultList;
	}

	public void reinsEndCase(String claimNo, String businessType, DateTime operateDate, String operateComCode, String operaterCode) throws Exception {
		Collection lDangerList = null;
		lDangerList = this.blLDangerService.genLDangerInfo(claimNo, businessType, operateDate, operateComCode, operaterCode);
		if (lDangerList == null)
			return;
		this.blLDangerService.reinsRepayCal(lDangerList);
	}

	public void repayCal(Collection claimDetailList) throws Exception {
		ClaimDetailVO claimDetail = null;
		LDangerVO lDanger = null;
		String certiNo = "";
		if ((claimDetailList == null) || (claimDetailList.size() <= 0))
			return;
		Iterator itDetail = claimDetailList.iterator();
		while (itDetail.hasNext()) {
			claimDetail = (ClaimDetailVO) itDetail.next();

			PrpCmainVO prpCmainVO = this.prpallReinsInterfService.DBPrpCmain_findByPrimaryKey(claimDetail.getPolicyNo());
			String coinsFlag = prpCmainVO.getCoinsFlag();
			if ("4".equals(coinsFlag)) {
				continue;
			}

			lDanger = this.blLDangerService.genLDangerInfo(claimDetail, certiNo);

			if (certiNo.equals("")) {
				certiNo = lDanger.getPrpLDangerUnit().getId().getCertiNo();
			}

			if (lDanger == null)
				continue;
			this.blLDangerService.reinsRepayCal(lDanger);
		}
	}

	public BLDangerService getBlDangerService() {
		return this.blDangerService;
	}

	public void setBlDangerService(BLDangerService blDangerService) {
		this.blDangerService = blDangerService;
	}

	public FeoEnquiryService getFeoEnquiryService() {
		return this.feoEnquiryService;
	}

	public void setFeoEnquiryService(FeoEnquiryService feoEnquiryService) {
		this.feoEnquiryService = feoEnquiryService;
	}

	public BLFcoRecoinsService getBlFcoRecoinsService() {
		return this.blFcoRecoinsService;
	}

	public void setBlFcoRecoinsService(BLFcoRecoinsService blFcoRecoinsService) {
		this.blFcoRecoinsService = blFcoRecoinsService;
	}

	public BLCTrialService getBlCTrialService() {
		return this.blCTrialService;
	}

	public void setBlCTrialService(BLCTrialService blCTrialService) {
		this.blCTrialService = blCTrialService;
	}

	public FhTreatyService getFhTreatyService() {
		return this.fhTreatyService;
	}

	public void setFhTreatyService(FhTreatyService fhTreatyService) {
		this.fhTreatyService = fhTreatyService;
	}

	public FhSectionService getFhSectionService() {
		return this.fhSectionService;
	}

	public void setFhSectionService(FhSectionService fhSectionService) {
		this.fhSectionService = fhSectionService;
	}

	public PubRateService getPubRateService() {
		return this.pubRateService;
	}

	public void setPubRateService(PubRateService pubRateService) {
		this.pubRateService = pubRateService;
	}

	public PrpallReinsInterfService getPrpallReinsInterfService() {
		return this.prpallReinsInterfService;
	}

	public void setPrpallReinsInterfService(PrpallReinsInterfService prpallReinsInterfService) {
		this.prpallReinsInterfService = prpallReinsInterfService;
	}

	public BLLDangerService getBlLDangerService() {
		return this.blLDangerService;
	}

	public void setBlLDangerService(BLLDangerService blLDangerService) {
		this.blLDangerService = blLDangerService;
	}

	public BLFdConfigService getBlFdConfigService() {
		return this.blFdConfigService;
	}

	public void setBlFdConfigService(BLFdConfigService blFdConfigService) {
		this.blFdConfigService = blFdConfigService;
	}
}