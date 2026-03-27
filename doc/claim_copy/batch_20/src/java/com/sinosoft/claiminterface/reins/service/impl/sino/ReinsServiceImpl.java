package com.sinosoft.claiminterface.reins.service.impl.sino;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sinosoft.claiminterface.reins.dto.custom.ReinsCaseStatus;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsClaimDetail;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsClaimMain;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsClaimSummary;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsDangerUnit;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsLargeCase;
import com.sinosoft.claiminterface.reins.dto.custom.ReinsRepayCalResult;
import com.sinosoft.claiminterface.reins.service.ReinsService;
import com.sinosoft.reins.interf.bl.action.custom.BLReinsClaimInterfAction;
import com.sinosoft.reins.interf.dto.custom.ClaimDangerUnitDto;
import com.sinosoft.reins.interf.dto.custom.ClaimDetailDto;
import com.sinosoft.reins.interf.dto.custom.ClaimItemDto;
import com.sinosoft.reins.interf.dto.custom.ClaimLargeLossDto;
import com.sinosoft.reins.interf.dto.custom.ClaimRepayCalResultDto;
import com.sinosoft.reins.interf.dto.custom.ClaimSummaryDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;

public class ReinsServiceImpl implements ReinsService {
	public void changeCaseStatus(DBManager dbManager, ReinsCaseStatus reinsCaseStatus) throws Exception {
		try {
			String claimNo = reinsCaseStatus.getClaimNo();
			String businessType = "";
			if (reinsCaseStatus.getBusinessType() == ReinsCaseStatus.BusinessType.ENDCASE) {
				businessType = "0";
			}
			if (reinsCaseStatus.getBusinessType() == ReinsCaseStatus.BusinessType.CANCLE) {
				businessType = "0";
			}
			if (reinsCaseStatus.getBusinessType() == ReinsCaseStatus.BusinessType.REFUSE) {
				businessType = "0";
			}
			if (reinsCaseStatus.getBusinessType() == ReinsCaseStatus.BusinessType.REOPEN) {
				businessType = "1";
			}

			DateTime operateDate = reinsCaseStatus.getOperateDate();
			String operateComCode = reinsCaseStatus.getOperateComCode();
			String operaterCode = reinsCaseStatus.getOperaterCode();
			new BLReinsClaimInterfAction().reinsEndCase(claimNo, businessType, operateDate, operateComCode, operaterCode, dbManager);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
	}

	public Collection getDangerUnit(DBManager dbManager, String policyNo, DateTime damageDate) throws Exception {
		Collection dangerUnitCollection = new ArrayList();
		try {
			Collection dangerUnitDtoCollection = new BLReinsClaimInterfAction().getDangerUnit(policyNo, damageDate, dbManager);
			for (Iterator iter = dangerUnitDtoCollection.iterator(); iter.hasNext();) {
				ClaimDangerUnitDto claimDangerUnitDto = (ClaimDangerUnitDto) iter.next();
				ReinsDangerUnit reinsDangerUnit = new ReinsDangerUnit();
				reinsDangerUnit.setDangerNo(new Integer(claimDangerUnitDto.getDangerNo()));
				System.out.println("++++++++++claimDangerNo+++++++++++++++" + claimDangerUnitDto.getDangerNo());
				reinsDangerUnit.setPolicyNo(claimDangerUnitDto.getPolicyNo());
				reinsDangerUnit.setDangerDesc(claimDangerUnitDto.getDangerDesc());
				reinsDangerUnit.setAddressName(claimDangerUnitDto.getAddressName());
				reinsDangerUnit.setCurrency(claimDangerUnitDto.getCurrency());

				reinsDangerUnit.setAmount(new Double(claimDangerUnitDto.getAmount()));
				reinsDangerUnit.setPremium(new Double(claimDangerUnitDto.getPremium()));
				reinsDangerUnit.setDangerShare(new Double(claimDangerUnitDto.getDangerShare()));
				reinsDangerUnit.setDangerItemList(claimDangerUnitDto.getClaimDangerItemDtoList());
				System.out.println("++++++++++ClaimDangerItemDtoList++size+++++++++++++++" + claimDangerUnitDto.getClaimDangerItemDtoList().size());

				dangerUnitCollection.add(reinsDangerUnit);
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return dangerUnitCollection;
	}

	public Collection getLargeCashLoss(DBManager dbManager, ReinsClaimSummary reinsClaimSummary) throws Exception {
		Collection reinsLargeCaseCollection = new ArrayList();
		try {
			Collection claimSummaryDtoList = getClaimSummaryDtoList(reinsClaimSummary);
			Collection claimLargeLossDtoList = new BLReinsClaimInterfAction().getLargeCashLoss(claimSummaryDtoList, dbManager);
			for (Iterator iter = claimLargeLossDtoList.iterator(); iter.hasNext();) {
				ClaimLargeLossDto claimLargeLossDto = (ClaimLargeLossDto) iter.next();
				ReinsLargeCase reinsLargeCase = new ReinsLargeCase();
				reinsLargeCase.setDangerNo(new Integer(claimLargeLossDto.getDangerNo()));
				reinsLargeCase.setPolicyNo(claimLargeLossDto.getPolicyNo());
				reinsLargeCase.setTreatyName(claimLargeLossDto.getTreatyName());
				if ("1".equals(claimLargeLossDto.getLargeLoss()))
					reinsLargeCase.setLargeLoss(Boolean.TRUE);
				else {
					reinsLargeCase.setLargeLoss(Boolean.FALSE);
				}
				if ("1".equals(reinsLargeCase.getCashLoss()))
					reinsLargeCase.setCashLoss(Boolean.TRUE);
				else {
					reinsLargeCase.setCashLoss(Boolean.FALSE);
				}
				reinsLargeCaseCollection.add(reinsLargeCase);
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return reinsLargeCaseCollection;
	}

	public double getSumFacShare(DBManager dbManager, String policyNo, DateTime damageDate) throws Exception {
		try {
			return new BLReinsClaimInterfAction().getSumFacShare(policyNo, damageDate, dbManager);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return 0.0D;
	}

	public void repayCal(DBManager dbManager, ReinsClaimMain reinsClaimMain) throws Exception {
		try {
			Collection claimDetailDtoList = getReinsClaimDetailList(reinsClaimMain);
			new BLReinsClaimInterfAction().repayCal(claimDetailDtoList, dbManager);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
	}

	public Collection repaySimulate(DBManager dbManager, ReinsClaimSummary reinsClaimSummary) throws Exception {
		Collection reinsRepayCalResultCollection = new ArrayList();
		try {
			Collection claimSummaryDtoList = getClaimSummaryDtoList(reinsClaimSummary);
			Collection claimRepayCalResultDtoArrayList = new BLReinsClaimInterfAction().repaySimulate(claimSummaryDtoList, dbManager);
			for (Iterator iter = claimRepayCalResultDtoArrayList.iterator(); iter.hasNext();) {
				ClaimRepayCalResultDto claimRepayCalResultDto = (ClaimRepayCalResultDto) iter.next();
				ReinsRepayCalResult reinsRepayCalResult = new ReinsRepayCalResult();
				reinsRepayCalResult.setPolicyNo(claimRepayCalResultDto.getPolicyNo());
				reinsRepayCalResult.setDangerNo(new Integer(claimRepayCalResultDto.getDangerNo()));
				reinsRepayCalResult.setCurrency(claimRepayCalResultDto.getCurrency());
				reinsRepayCalResult.setShareRate(new Double(claimRepayCalResultDto.getShareRate()));
				reinsRepayCalResult.setSumPaid(new Double(claimRepayCalResultDto.getSumPaid()));
				reinsRepayCalResult.setTreatyName(claimRepayCalResultDto.getTreatyName());
				reinsRepayCalResult.setTreatyNo(claimRepayCalResultDto.getTreatyNo());
				reinsRepayCalResult.setReinsModeName(claimRepayCalResultDto.getReinsMode());
				reinsRepayCalResultCollection.add(reinsRepayCalResult);
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return reinsRepayCalResultCollection;
	}

	public double getSumFacShare(String policyNo, DateTime damageDate) throws Exception {
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			return getSumFacShare(dbManager, policyNo, damageDate);
		} catch (Exception exception) {
			System.err.println("送再保数据出现异常。。。。");
			exception.printStackTrace();
		} finally {
			dbManager.close();
		}
		return 0.0D;
	}

	public Collection getLargeCashLoss(ReinsClaimSummary reinsClaimSummary) throws Exception {
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			return getLargeCashLoss(dbManager, reinsClaimSummary);
		} catch (Exception exception) {
			System.err.println("送再保数据出现异常。。。。");
			exception.printStackTrace();
		} finally {
			dbManager.close();
		}
		return new ArrayList();
	}

	public Collection repaySimulate(ReinsClaimSummary reinsClaimSummary) throws Exception {
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			return repaySimulate(dbManager, reinsClaimSummary);
		} catch (Exception exception) {
			throw exception;
		} finally {
			dbManager.close();
		}
	}

	public Collection getDangerUnit(String policyNo, DateTime damageDate) throws Exception {
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			return getDangerUnit(dbManager, policyNo, damageDate);
		} catch (Exception exception) {
			System.err.println("送再保数据出现异常。。。。");
			exception.printStackTrace();
		} finally {
			dbManager.close();
		}
		return new ArrayList();
	}

	private Collection getClaimSummaryDtoList(ReinsClaimSummary reinsClaimSummary) {
		Collection claimSummaryDtoList = new ArrayList();
		try {
			Map claimSummaryDtoMap = new HashMap();
			for (Iterator iter = reinsClaimSummary.getReinsClaimDetailList().iterator(); iter.hasNext();) {
				ReinsClaimDetail reinsClaimDetail = (ReinsClaimDetail) iter.next();

				int dangeNo = 1;
				if (reinsClaimDetail.getDangerNo() == null)
					dangeNo = 1;
				else {
					dangeNo = reinsClaimDetail.getDangerNo().intValue();
				}
				ClaimSummaryDto claimSummaryDto = (ClaimSummaryDto) claimSummaryDtoMap.get(String.valueOf(dangeNo));
				if (claimSummaryDto == null) {
					claimSummaryDto = new ClaimSummaryDto();
					claimSummaryDto.setPolicyNo(reinsClaimSummary.getPolicyNo());
					claimSummaryDto.setDamageDate(reinsClaimSummary.getDamageDate());
					claimSummaryDto.setCurrency("NTD");
					claimSummaryDto.setSumClaim(reinsClaimDetail.getSumPaid().doubleValue());
					claimSummaryDto.setDangerNo(dangeNo);
					claimSummaryDtoMap.put(String.valueOf(dangeNo), claimSummaryDto);
				} else {
					claimSummaryDto.setSumClaim(claimSummaryDto.getSumClaim() + reinsClaimDetail.getSumPaid().doubleValue());
				}
			}
			ArrayList dangerNoList = new ArrayList(claimSummaryDtoMap.keySet());
			Collections.sort(dangerNoList);
			claimSummaryDtoList = new ArrayList();
			Iterator dangerNoListIterator = dangerNoList.iterator();
			while (dangerNoListIterator.hasNext())
				claimSummaryDtoList.add(claimSummaryDtoMap.get(dangerNoListIterator.next()));
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return claimSummaryDtoList;
	}

	private Collection getReinsClaimDetailList(ReinsClaimMain reinsClaimMain) {
		Map claimDetailDtoMap = new HashMap();
		try {
			for (Iterator iter = reinsClaimMain.getReinsClaimDetailList().iterator(); iter.hasNext();) {
				ReinsClaimDetail reinsClaimDetail = (ReinsClaimDetail) iter.next();
				int dangeNo = 1;
				if (reinsClaimDetail.getDangerNo() == null)
					dangeNo = 1;
				else {
					dangeNo = reinsClaimDetail.getDangerNo().intValue();
				}
				ClaimDetailDto claimDetailDto = (ClaimDetailDto) claimDetailDtoMap.get(String.valueOf(dangeNo));

				if (claimDetailDto == null) {
					claimDetailDto = getClaimDetail(reinsClaimMain);
					claimDetailDto.setDangerNo(dangeNo);

					claimDetailDto.setSumClaim(reinsClaimDetail.getSumPaid().doubleValue());
					setClaimItem(claimDetailDto, reinsClaimDetail);

					if ((reinsClaimMain.getCertiType() == ReinsClaimMain.CertiType.PAY) && (reinsClaimMain.getSumClaim() != null)) {
						claimDetailDto.setSumClaim(reinsClaimMain.getSumClaim().doubleValue());
					}

					claimDetailDto.setBusinessNature(reinsClaimMain.getBusinessNature());
					claimDetailDto.setChannelType(reinsClaimMain.getChannelType());
					claimDetailDto.setCartypeCode(reinsClaimMain.getCartypeCode());

					claimDetailDtoMap.put(String.valueOf(dangeNo), claimDetailDto);
				} else {
					if (reinsClaimMain.getCertiType() != ReinsClaimMain.CertiType.PAY) {
						claimDetailDto.setSumClaim(claimDetailDto.getSumClaim() + reinsClaimDetail.getSumPaid().doubleValue());
					}

					claimDetailDto.setBusinessNature(reinsClaimMain.getBusinessNature());
					claimDetailDto.setChannelType(reinsClaimMain.getChannelType());
					claimDetailDto.setCartypeCode(reinsClaimMain.getCartypeCode());

					setClaimItem(claimDetailDto, reinsClaimDetail);
				}
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return claimDetailDtoMap.values();
	}

	private ClaimDetailDto getClaimDetail(ReinsClaimMain reinsClaimMain) {
		ClaimDetailDto claimDetailDto = new ClaimDetailDto();
		try {
			claimDetailDto.setClaimNo(reinsClaimMain.getClaimNo());
			claimDetailDto.setPolicyNo(reinsClaimMain.getPolicyNo());
			claimDetailDto.setDamageDate(reinsClaimMain.getDamageDate());

			String certiType = null;
			if (reinsClaimMain.getCertiType() == ReinsClaimMain.CertiType.CLAIM) {
				certiType = "1";
			}
			if (reinsClaimMain.getCertiType() == ReinsClaimMain.CertiType.PREPAY) {
				certiType = "2";
			}
			if (reinsClaimMain.getCertiType() == ReinsClaimMain.CertiType.PAY) {
				certiType = "3";
			}
			claimDetailDto.setCertiType(certiType);
			if (reinsClaimMain.getEndCaseFlag().booleanValue())
				claimDetailDto.setEndCaseFlag("1");
			else {
				claimDetailDto.setEndCaseFlag("0");
			}
			claimDetailDto.setDamageCode(reinsClaimMain.getDamageCode());
			claimDetailDto.setDamageReason(reinsClaimMain.getDamageReason());
			claimDetailDto.setCertiNo(reinsClaimMain.getCertiNo());

			claimDetailDto.setMakeComCode(reinsClaimMain.getMakeComCode());
			claimDetailDto.setCreaterCode(reinsClaimMain.getCreaterCode());
			claimDetailDto.setCreateDate(reinsClaimMain.getCreateDate());
			claimDetailDto.setCreateDate(reinsClaimMain.getCreateDate());
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return claimDetailDto;
	}

	private void setClaimItem(ClaimDetailDto claimDetailDto, ReinsClaimDetail reinsClaimDetail) {
		try {
			ClaimItemDto claimItemDto = new ClaimItemDto();
			claimItemDto.setKindCode(reinsClaimDetail.getKindCode());
			claimItemDto.setKindName(reinsClaimDetail.getKindName());
			claimItemDto.setCurrency(reinsClaimDetail.getCurrency());
			if (reinsClaimDetail.getPayType() == ReinsClaimDetail.PayType.PAY) {
				claimItemDto.setPayType("1");
			}
			if (reinsClaimDetail.getPayType() == ReinsClaimDetail.PayType.CHARGE) {
				claimItemDto.setPayType("2");
			}
			claimItemDto.setSumPaid(reinsClaimDetail.getSumPaid().doubleValue());
			if (claimDetailDto.getClaimItemDtoList() == null) {
				claimDetailDto.setClaimItemDtoList(new ArrayList());
			}
			claimDetailDto.setCurrency(reinsClaimDetail.getCurrency());
			claimDetailDto.getClaimItemDtoList().add(claimItemDto);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
	}
}