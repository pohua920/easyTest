package com.sinosoft.reins.interf.bl.action.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.claim.bl.action.domain.BLPrpCmainAction;
import com.sinosoft.claim.dto.domain.PrpCmainDto;
import com.sinosoft.reins.common.dto.domain.FhSectionDto;
import com.sinosoft.reins.common.dto.domain.FhTreatyDto;
import com.sinosoft.reins.common.resource.dtofactory.domain.DBFhSection;
import com.sinosoft.reins.common.resource.dtofactory.domain.DBFhTreaty;
import com.sinosoft.reins.interf.dto.custom.ClaimDangerItemDto;
import com.sinosoft.reins.interf.dto.custom.ClaimDangerUnitDto;
import com.sinosoft.reins.interf.dto.custom.ClaimDetailDto;
import com.sinosoft.reins.interf.dto.custom.ClaimLargeLossDto;
import com.sinosoft.reins.interf.dto.custom.ClaimRepayCalResultDto;
import com.sinosoft.reins.interf.dto.custom.ClaimSummaryDto;
import com.sinosoft.reins.out.bl.action.custom.BLCTrialAction;
import com.sinosoft.reins.out.bl.action.custom.BLDangerAction;
import com.sinosoft.reins.out.bl.action.custom.BLExchGetAction;
import com.sinosoft.reins.out.bl.action.custom.BLLDangerAction;
import com.sinosoft.reins.out.bl.action.domain.BLFcoRecoinsAction;
import com.sinosoft.reins.out.dto.custom.CDangerDto;
import com.sinosoft.reins.out.dto.custom.CReinsTrialDto;
import com.sinosoft.reins.out.dto.custom.LDangerDto;
import com.sinosoft.reins.out.dto.domain.FeoEnquiryDto;
import com.sinosoft.reins.out.dto.domain.PrpCdangerItemDto;
import com.sinosoft.reins.out.dto.domain.PrpCdangerUnitDto;
import com.sinosoft.reins.out.dto.domain.PrpCreinsShareDto;
import com.sinosoft.reins.out.dto.domain.PrpCreinsTrialDto;
import com.sinosoft.reins.out.resource.dtofactory.domain.DBFeoEnquiry;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.reference.DBManager;

public class BLReinsClaimInterfAction {
	public Collection getDangerUnit(String policyNo, DateTime damageDate, DBManager dbManager) throws Exception {
		Collection claimDangerUnitDtoList = new ArrayList();
		Collection claimDangerItemDtoList = null;
		Collection prpCdangerItemDtoList = null;
		PrpCdangerUnitDto prpCdangerUnitDto = null;

		Collection cDangerDtoList = new BLDangerAction().getCDangerList(policyNo, damageDate, dbManager);
		if (cDangerDtoList.size() > 0) {
			Iterator itCdanger = cDangerDtoList.iterator();
			while (itCdanger.hasNext()) {
				CDangerDto cDangerDto = (CDangerDto) itCdanger.next();
				prpCdangerUnitDto = cDangerDto.getPrpCdangerUnitDto();

				ClaimDangerUnitDto claimDangerUnitDto = new ClaimDangerUnitDto();

				claimDangerUnitDto.setPolicyNo(policyNo);
				claimDangerUnitDto.setDangerNo(prpCdangerUnitDto.getDangerNo());
				claimDangerUnitDto.setDangerDesc(prpCdangerUnitDto.getDangerDesc());
				claimDangerUnitDto.setAddressName(prpCdangerUnitDto.getAddressName());
				claimDangerUnitDto.setAmount(prpCdangerUnitDto.getAmount());
				claimDangerUnitDto.setPremium(prpCdangerUnitDto.getPremium());
				claimDangerUnitDto.setDangerShare(prpCdangerUnitDto.getDangerShare());

				prpCdangerItemDtoList = cDangerDto.getPrpCdangerItemDtoList();

				claimDangerItemDtoList = new ArrayList();
				if (prpCdangerItemDtoList.size() != 0) {
					Iterator itItem = prpCdangerItemDtoList.iterator();
					while (itItem.hasNext()) {
						PrpCdangerItemDto prpCdangerItemDto = (PrpCdangerItemDto) itItem.next();
						ClaimDangerItemDto claimDangerItemDto = new ClaimDangerItemDto();

						claimDangerItemDto.setKindCode(prpCdangerItemDto.getKindCode());
						claimDangerItemDto.setKindName(prpCdangerItemDto.getKindName());
						claimDangerItemDto.setItemCode(prpCdangerItemDto.getItemCode());
						claimDangerItemDto.setItemDetailName(prpCdangerItemDto.getItemDetailName());
						claimDangerItemDto.setCurrency(prpCdangerItemDto.getCurrency());
						claimDangerItemDto.setAmount(prpCdangerItemDto.getAmount());
						claimDangerItemDto.setPremium(prpCdangerItemDto.getPremium());
						claimDangerItemDto.setCalculateFlag(prpCdangerItemDto.getCalculateFlag());

						claimDangerItemDtoList.add(claimDangerItemDto);
					}
				}
				claimDangerUnitDto.setClaimDangerItemDtoList(claimDangerItemDtoList);

				claimDangerUnitDtoList.add(claimDangerUnitDto);
			}
		}
		return claimDangerUnitDtoList;
	}

	public double getSumFacShare(String policyNo, DateTime damageDate, DBManager dbManager) throws Exception {
		double sumFacShare = 0.0D;
		String endorseNo = "";
		FeoEnquiryDto feoEnquiryDto = null;
		Collection feoEnquiryDtoList = null;
		String conditions = "";

		endorseNo = new BLDangerAction().getRecentlyEndorseNo(policyNo, 1, damageDate, dbManager);

		if (!(endorseNo.equals(""))) {
			conditions = " endorseNo = '" + endorseNo + "' ";
		} else {
			conditions = " policyNo = '" + policyNo + "' and endorseNo is null ";
		}
		feoEnquiryDtoList = new DBFeoEnquiry(dbManager).findByConditions(conditions);
		if (feoEnquiryDtoList != null) {
			Iterator itFac = feoEnquiryDtoList.iterator();
			if (itFac.hasNext()) {
				feoEnquiryDto = (FeoEnquiryDto) itFac.next();
				sumFacShare = feoEnquiryDto.getFacShare();
			}
		}

		return sumFacShare;
	}

	public Collection getLargeCashLoss(Collection claimSummaryDtoList, DBManager dbManager) throws Exception {
		String policyNo = "";
		int dangerNo = 0;
		DateTime damageDate = null;
		String currency = "";
		double sumClaim = 0.0D;
		BLCTrialAction blCTrialAction = new BLCTrialAction();
		PrpCreinsShareDto prpCreinsShareDto = null;
		Collection prpCreinsShareDtoList = null;
		String treatyNo = "";
		String sectionNo = "";
		DBFhTreaty dbFhTreaty = new DBFhTreaty(dbManager);
		DBFhSection dbFhSection = new DBFhSection(dbManager);
		FhTreatyDto fhTreatyDto = null;
		FhSectionDto fhSectionDto = null;
		double exchRate = 0.0D;
		String largeLoss = "0";
		String cashLoss = "0";
		Collection claimLargeLossDtoList = new ArrayList();

		double baseRate = 0.0D;
		double treatySumClaim = 0.0D;
		if ((claimSummaryDtoList != null) && (claimSummaryDtoList.size() > 0)) {
			Iterator itLargeLoss = claimSummaryDtoList.iterator();
			while (itLargeLoss.hasNext()) {
				ClaimSummaryDto claimSummaryDto = (ClaimSummaryDto) itLargeLoss.next();
				policyNo = claimSummaryDto.getPolicyNo();
				dangerNo = claimSummaryDto.getDangerNo();
				damageDate = claimSummaryDto.getDamageDate();
				currency = claimSummaryDto.getCurrency();
				sumClaim = claimSummaryDto.getSumClaim();

				baseRate = new BLFcoRecoinsAction().getBaseRate(policyNo, dbManager);

				sumClaim = sumClaim * baseRate / 100.0D;

				CReinsTrialDto cReinsTrialDto = blCTrialAction.getCTrialInfo(policyNo, dangerNo, damageDate, dbManager);
				prpCreinsShareDtoList = cReinsTrialDto.getPrpCreinsShareDtoList();
				if (prpCreinsShareDtoList == null)
					continue;
				Iterator itShare = prpCreinsShareDtoList.iterator();
				while (itShare.hasNext()) {
					prpCreinsShareDto = (PrpCreinsShareDto) itShare.next();

					if (!(prpCreinsShareDto.getReinsMode().substring(0, 1).equals("2"))) {
						continue;
					}

					treatyNo = prpCreinsShareDto.getTreatyNo();
					sectionNo = prpCreinsShareDto.getSectionNo();
					fhTreatyDto = dbFhTreaty.findByPrimaryKey(treatyNo);
					fhSectionDto = dbFhSection.findByPrimaryKey(treatyNo, sectionNo);

					new BLExchGetAction();
					exchRate = BLExchGetAction.getExchRate(currency, fhTreatyDto.getCurrency(), damageDate, dbManager);

					sumClaim *= exchRate;

					treatySumClaim = sumClaim * prpCreinsShareDto.getShareRate() / 100.0D;
					if (fhSectionDto.getCashLossFlag().equals("0")) {
						if (sumClaim >= fhSectionDto.getLargeLossValue()) {
							cashLoss = "1";
						}
					} else if (treatySumClaim >= fhSectionDto.getLargeLossValue()) {
						largeLoss = "1";
					}

					if (fhSectionDto.getCashLossFlag().equals("0")) {
						if (sumClaim >= fhSectionDto.getCashLossValue()) {
							cashLoss = "1";
						}
					} else if (treatySumClaim >= fhSectionDto.getCashLossValue()) {
						largeLoss = "1";
					}

					ClaimLargeLossDto claimLargeLossDto = new ClaimLargeLossDto();
					claimLargeLossDto.setPolicyNo(policyNo);
					claimLargeLossDto.setDangerNo(dangerNo);
					claimLargeLossDto.setTreatyName(fhTreatyDto.getTreatyName());
					claimLargeLossDto.setLargeLoss(largeLoss);
					claimLargeLossDto.setCashLoss(cashLoss);
					claimLargeLossDtoList.add(claimLargeLossDto);
				}
			}
		}

		return claimLargeLossDtoList;
	}

	public Collection repaySimulate(Collection claimSummaryDtoList, DBManager dbManager) throws Exception {
		Collection prpCreinsTrialDtoList = null;
		PrpCreinsTrialDto prpCreinsTrialDto = null;
		BLCTrialAction blCTrialAction = new BLCTrialAction();
		String policyNo = "";
		int dangerNo = 0;
		DateTime damageDate = null;
		String currency = "";
		double sumClaim = 0.0D;
		double totalPaid = 0.0D;
		String reinsMode = "";
		Collection claimRepayCalResultDtoList = new ArrayList();
		DBFhTreaty dbFhTreaty = new DBFhTreaty(dbManager);
		FhTreatyDto fhTreatyDto = null;
		double baseRate = 0.0D;
		if ((claimSummaryDtoList != null) && (claimSummaryDtoList.size() > 0)) {
			Iterator itRepayCal = claimSummaryDtoList.iterator();
			while (itRepayCal.hasNext()) {
				ClaimSummaryDto claimSummaryDto = (ClaimSummaryDto) itRepayCal.next();
				policyNo = claimSummaryDto.getPolicyNo();
				dangerNo = claimSummaryDto.getDangerNo();
				damageDate = claimSummaryDto.getDamageDate();
				currency = claimSummaryDto.getCurrency();
				sumClaim = claimSummaryDto.getSumClaim();
				baseRate = new BLFcoRecoinsAction().getBaseRate(policyNo, dbManager);
				sumClaim = sumClaim * baseRate / 100.0D;
				totalPaid = 0.0D;
				CReinsTrialDto cReinsTrialDto = blCTrialAction.getCTrialInfo(policyNo, dangerNo, damageDate, dbManager);
				prpCreinsTrialDtoList = cReinsTrialDto.getPrpCreinsTrialDtoList();
				if ((prpCreinsTrialDtoList == null) || (prpCreinsTrialDtoList.size() <= 0))
					continue;
				Iterator itTrial = prpCreinsTrialDtoList.iterator();
				ClaimRepayCalResultDto claimRepayCalResultDto;
				while (itTrial.hasNext()) {
					prpCreinsTrialDto = (PrpCreinsTrialDto) itTrial.next();
					if ("181".equals(prpCreinsTrialDto.getReinsMode()))
						continue;
					claimRepayCalResultDto = new ClaimRepayCalResultDto();
					claimRepayCalResultDto.setPolicyNo(policyNo);
					claimRepayCalResultDto.setDangerNo(dangerNo);
					if (prpCreinsTrialDto.getReinsMode().startsWith("3")) {
						reinsMode = "臨分";
					} else if (prpCreinsTrialDto.getReinsMode().startsWith("2")) {
						reinsMode = "合約";
					} else if (prpCreinsTrialDto.getReinsMode().startsWith("0")) {
						reinsMode = "法定";
					} else if (prpCreinsTrialDto.getReinsMode().startsWith("1")) {
						reinsMode = "附加自留";
					}

					claimRepayCalResultDto.setReinsMode(reinsMode);
					claimRepayCalResultDto.setTreatyNo(prpCreinsTrialDto.getTreatyNo());
					fhTreatyDto = dbFhTreaty.findByPrimaryKey(claimRepayCalResultDto.getTreatyNo());
					claimRepayCalResultDto.setTreatyName(fhTreatyDto.getTreatyName());
					claimRepayCalResultDto.setShareRate(prpCreinsTrialDto.getShareRate());
					claimRepayCalResultDto.setCurrency(currency);

					claimRepayCalResultDto.setSumPaid(DataUtils.round(sumClaim * prpCreinsTrialDto.getShareRate() / 100.0D, 0));
					claimRepayCalResultDtoList.add(claimRepayCalResultDto);
					totalPaid += DataUtils.round(claimRepayCalResultDto.getSumPaid(), 2);
				}

				itTrial = prpCreinsTrialDtoList.iterator();
				while (itTrial.hasNext()) {
					prpCreinsTrialDto = (PrpCreinsTrialDto) itTrial.next();
					if (!("181".equals(prpCreinsTrialDto.getReinsMode())))
						continue;
					claimRepayCalResultDto = new ClaimRepayCalResultDto();
					claimRepayCalResultDto.setPolicyNo(policyNo);
					claimRepayCalResultDto.setDangerNo(dangerNo);
					claimRepayCalResultDto.setReinsMode("毛自留");
					claimRepayCalResultDto.setTreatyNo(prpCreinsTrialDto.getTreatyNo());
					fhTreatyDto = dbFhTreaty.findByPrimaryKey(claimRepayCalResultDto.getTreatyNo());
					claimRepayCalResultDto.setTreatyName(fhTreatyDto.getTreatyName());
					claimRepayCalResultDto.setShareRate(prpCreinsTrialDto.getShareRate());
					claimRepayCalResultDto.setCurrency(currency);

					claimRepayCalResultDto.setSumPaid(DataUtils.round(sumClaim - totalPaid, 0));

					claimRepayCalResultDtoList.add(claimRepayCalResultDto);
					break;
				}
			}

		}

		return claimRepayCalResultDtoList;
	}

	public void reinsEndCase(String claimNo, String businessType, DateTime operateDate, String operateComCode, String operaterCode, DBManager dbManager) throws Exception {
		Collection lDangerDtoList = null;
		BLLDangerAction blLDangerAction = new BLLDangerAction();
		lDangerDtoList = blLDangerAction.genLDangerInfo(claimNo, businessType, operateDate, operateComCode, operaterCode, dbManager);
		if (lDangerDtoList == null)
			return;
		blLDangerAction.reinsRepayCal(lDangerDtoList, dbManager);
	}

	public void repayCal(Collection claimDetailDtoList, DBManager dbManager) throws Exception {
		ClaimDetailDto claimDetailDto = null;
		BLLDangerAction blLDangerAction = new BLLDangerAction();
		LDangerDto lDangerDto = null;
		String certiNo = "";
		if ((claimDetailDtoList == null) || (claimDetailDtoList.size() <= 0))
			return;
		Iterator itDetail = claimDetailDtoList.iterator();
		while (itDetail.hasNext()) {
			claimDetailDto = (ClaimDetailDto) itDetail.next();

			BLPrpCmainAction blPrpCmainAction = new BLPrpCmainAction();
			PrpCmainDto prpCmainDto = blPrpCmainAction.findByPrimaryKey(dbManager, claimDetailDto.getPolicyNo());
			String coinsFlag = prpCmainDto.getCoinsFlag();
			if ("4".equals(coinsFlag)) {
				continue;
			}

			lDangerDto = blLDangerAction.genLDangerInfo(claimDetailDto, certiNo, dbManager);

			if (certiNo.equals("")) {
				certiNo = lDangerDto.getPrpLdangerUnitDto().getCertiNo();
			}

			if (lDangerDto == null)
				continue;
			blLDangerAction.reinsRepayCal(lDangerDto, dbManager);
		}
	}
}