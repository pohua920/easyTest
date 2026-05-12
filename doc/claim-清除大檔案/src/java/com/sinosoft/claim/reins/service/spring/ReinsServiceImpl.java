package com.sinosoft.claim.reins.service.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.reins.service.facade.ReinsService;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.reins.vo.ReinsClaimDetail;
import com.sinosoft.claim.reins.vo.ReinsClaimMain;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.reins.vo.ReinsDangerUnit;
import com.sinosoft.claim.reins.vo.ReinsLargeCase;
import com.sinosoft.claim.reins.vo.ReinsRepayCalResult;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.reins.interf.vo.ClaimDangerUnitVO;
import com.sinosoft.reins.interf.vo.ClaimDetailVO;
import com.sinosoft.reins.interf.vo.ClaimItemVO;
import com.sinosoft.reins.interf.vo.ClaimLargeLossVO;
import com.sinosoft.reins.interf.vo.ClaimRepayCalResultVO;
import com.sinosoft.reins.interf.vo.ClaimSummaryVO;
import com.sinosoft.reins.interf.web.ReinsClaimInterfAction;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class ReinsServiceImpl implements ReinsService {

	private ReinsClaimInterfAction reinsClaimInterfAction;
	private PrpCmainService prpCmainService;
	private CodeService codeService;

	public void changeCaseStatus(ReinsCaseStatus reinsCaseStatus) {
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
			reinsClaimInterfAction.reinsEndCase(claimNo, businessType, operateDate, operateComCode, operaterCode);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}

	}

	public Collection<?> getDangerUnit(String policyNo, DateTime damageDate) {
		Collection<ReinsDangerUnit> dangerUnitCollection = new ArrayList<ReinsDangerUnit>();
		try {
			PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);
			String riskType = this.codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode());
			Collection<?> dangerUnitDtoCollection = null;
			if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
				dangerUnitDtoCollection = reinsClaimInterfAction.getDangerUnitForAcci(policyNo, damageDate);
			} else {
				dangerUnitDtoCollection = reinsClaimInterfAction.getDangerUnit(policyNo, damageDate);
			}
			Iterator<?> it = dangerUnitDtoCollection.iterator();
			while (it.hasNext()) {
				ClaimDangerUnitVO claimDangerUnitDto = (ClaimDangerUnitVO)it.next();
				ReinsDangerUnit reinsDangerUnit = new ReinsDangerUnit();
				reinsDangerUnit.setDangerNo(new Integer(claimDangerUnitDto.getDangerNo()));
				//System.out.println("++++++++++claimDangerNo+++++++++++++++" + claimDangerUnitDto.getDangerNo());
				reinsDangerUnit.setPolicyNo(claimDangerUnitDto.getPolicyNo());
				reinsDangerUnit.setDangerDesc(claimDangerUnitDto.getDangerDesc());
				reinsDangerUnit.setAddressName(claimDangerUnitDto.getAddressName());
				reinsDangerUnit.setCurrency(claimDangerUnitDto.getCurrency());
				// modify by xukefeng 20070312 start 查看危险单位信息需要的一些参数
				reinsDangerUnit.setAmount(new Double(claimDangerUnitDto.getAmount()));
				reinsDangerUnit.setPremium(new Double(claimDangerUnitDto.getPremium()));
				reinsDangerUnit.setDangerShare(new Double(claimDangerUnitDto.getDangerShare()));
				reinsDangerUnit.setDangerItemList(claimDangerUnitDto.getClaimDangerItemList());
				//System.out.println("++++++++++ClaimDangerItemDtoList++size+++++++++++++++" + claimDangerUnitDto.getClaimDangerItemList().size());
				// modify by xukefeng 20070312 end
				dangerUnitCollection.add(reinsDangerUnit);
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return dangerUnitCollection;
	}

	public Collection<ReinsLargeCase> getLargeCashLoss(ReinsClaimSummary reinsClaimSummary) {
		Collection<ReinsLargeCase> reinsLargeCaseCollection = new ArrayList<ReinsLargeCase>();
		try {
			Collection<ClaimSummaryVO> claimSummaryDtoList = getClaimSummaryDtoList(reinsClaimSummary);
			Iterator<?> it = reinsClaimInterfAction.getLargeCashLoss(claimSummaryDtoList).iterator();
			while (it.hasNext()) {
				ClaimLargeLossVO claimLargeLossDto = (ClaimLargeLossVO)it.next();
				ReinsLargeCase reinsLargeCase = new ReinsLargeCase();
				reinsLargeCase.setDangerNo(new Integer(claimLargeLossDto.getDangerNo()));
				reinsLargeCase.setPolicyNo(claimLargeLossDto.getPolicyNo());
				reinsLargeCase.setTreatyName(claimLargeLossDto.getTreatyName());
				if ("1".equals(claimLargeLossDto.getLargeLoss())) {
					reinsLargeCase.setLargeLoss(Boolean.TRUE);
				} else {
					reinsLargeCase.setLargeLoss(Boolean.FALSE);
				}
				if ("1".equals(claimLargeLossDto.getCashLoss())) {
					reinsLargeCase.setCashLoss(Boolean.TRUE);
				} else {
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

	public double getSumFacShare(String policyNo, DateTime damageDate) {
		try {
			return (reinsClaimInterfAction.getSumFacShare(policyNo, damageDate));
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return 0.0;
	}

	public void repayCal(ReinsClaimMain reinsClaimMain) {
		try {
			Collection<ClaimDetailVO> claimDetailDtoList = getReinsClaimDetailList(reinsClaimMain);
			reinsClaimInterfAction.repayCal(claimDetailDtoList);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}

	}

	public Collection<ReinsRepayCalResult> repaySimulate(ReinsClaimSummary reinsClaimSummary) {
		Collection<ReinsRepayCalResult> reinsRepayCalResultCollection = new ArrayList<ReinsRepayCalResult>();
		try {
			Collection<ClaimSummaryVO> claimSummaryDtoList = getClaimSummaryDtoList(reinsClaimSummary);
			Iterator<?> it = reinsClaimInterfAction.repaySimulate(claimSummaryDtoList).iterator();
			while (it.hasNext()) {
				ClaimRepayCalResultVO claimRepayCalResultDto = (ClaimRepayCalResultVO)it.next();
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

	private Collection<ClaimSummaryVO> getClaimSummaryDtoList(ReinsClaimSummary reinsClaimSummary) {//此方法需要把claimSummaryDto加kindcode，且不能只传一个claimSummaryDto对象
		Collection<ClaimSummaryVO> claimSummaryDtoList = new ArrayList<ClaimSummaryVO>();
		try {
			Map<String,ClaimSummaryVO> claimSummaryDtoMap = new HashMap<String,ClaimSummaryVO>();
			Iterator<?> it = reinsClaimSummary.getReinsClaimDetailList().iterator();
			while (it.hasNext()) {
				ReinsClaimDetail reinsClaimDetail = (ReinsClaimDetail) it.next();

				int dangeNo = 1;
				if (reinsClaimDetail.getDangerNo() == null) {
					dangeNo = 1;
				} else {
					dangeNo = reinsClaimDetail.getDangerNo().intValue();
				}
				ClaimSummaryVO claimSummaryDto = (ClaimSummaryVO) claimSummaryDtoMap.get(reinsClaimDetail.getKindCode());
				if (claimSummaryDto == null) {
					claimSummaryDto = new ClaimSummaryVO();
					claimSummaryDto.setPolicyNo(reinsClaimSummary.getPolicyNo());
					claimSummaryDto.setDamageDate(reinsClaimSummary.getDamageDate());
					claimSummaryDto.setCurrency(ConstantCodes.LOCAL_CURRENCY);
					claimSummaryDto.setSumClaim(reinsClaimDetail.getSumPaid().doubleValue());
					claimSummaryDto.setDangerNo(dangeNo);
					claimSummaryDto.setKindCode(reinsClaimDetail.getKindCode());
					claimSummaryDtoMap.put(reinsClaimDetail.getKindCode(), claimSummaryDto);
				} else {
					claimSummaryDto.setSumClaim(claimSummaryDto.getSumClaim() + reinsClaimDetail.getSumPaid().doubleValue());
				}
			}
			ArrayList<String> resultList = new ArrayList<String>(claimSummaryDtoMap.keySet());
//			Collections.sort(dangerNoList);
			Iterator<?> it1 = resultList.iterator();
			while (it1.hasNext()) {
				claimSummaryDtoList.add(claimSummaryDtoMap.get(it1.next()));
			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return claimSummaryDtoList;
	}

	private Collection<ClaimDetailVO> getReinsClaimDetailList(ReinsClaimMain reinsClaimMain) {

		Map<String, ClaimDetailVO> claimDetailDtoMap = new HashMap<String, ClaimDetailVO>();
		try {
			Iterator<?> it = reinsClaimMain.getReinsClaimDetailList().iterator();
			while (it.hasNext()) {
				ReinsClaimDetail reinsClaimDetail = (ReinsClaimDetail) it.next();
				int dangeNo = 1;
				if (reinsClaimDetail.getDangerNo() == null) {
					dangeNo = 1;
				} else {
					dangeNo = reinsClaimDetail.getDangerNo().intValue();
				}
				ClaimDetailVO claimDetailDto = (ClaimDetailVO) claimDetailDtoMap.get(String.valueOf(dangeNo));

				if (claimDetailDto == null) {
					claimDetailDto = getClaimDetail(reinsClaimMain);
					claimDetailDto.setDangerNo(dangeNo);

					claimDetailDto.setSumClaim(reinsClaimDetail.getSumPaid().doubleValue());
					setClaimItem(claimDetailDto, reinsClaimDetail);
					// modify 20070124 start
					if (reinsClaimMain.getCertiType() == ReinsClaimMain.CertiType.PAY && reinsClaimMain.getSumClaim() != null) {
						claimDetailDto.setSumClaim(reinsClaimMain.getSumClaim().doubleValue());
					}
					// modify 20070124 end

					// SAP 改造 start
					claimDetailDto.setBusinessNature(reinsClaimMain.getBusinessNature());
					claimDetailDto.setChannelType(reinsClaimMain.getChannelType());
					claimDetailDto.setCartypeCode(reinsClaimMain.getCartypeCode());
					// SAP 改造 end
					claimDetailDtoMap.put(String.valueOf(dangeNo), claimDetailDto);

				} else {
					// modify 20070124 start
					if (reinsClaimMain.getCertiType() != ReinsClaimMain.CertiType.PAY) {
						claimDetailDto.setSumClaim(claimDetailDto.getSumClaim() + reinsClaimDetail.getSumPaid().doubleValue());
					}
					// modify 20070124 end

					// SAP 改造 start
					claimDetailDto.setBusinessNature(reinsClaimMain.getBusinessNature());
					claimDetailDto.setChannelType(reinsClaimMain.getChannelType());
					claimDetailDto.setCartypeCode(reinsClaimMain.getCartypeCode());
					// SAP 改造 end
					setClaimItem(claimDetailDto, reinsClaimDetail);

				}

			}
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
		return claimDetailDtoMap.values();
	}

	private ClaimDetailVO getClaimDetail(ReinsClaimMain reinsClaimMain) {
		ClaimDetailVO claimDetailDto = new ClaimDetailVO();
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
			if (reinsClaimMain.getEndCaseFlag().booleanValue()) {
				claimDetailDto.setEndCaseFlag("1");
			} else {
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

	private void setClaimItem(ClaimDetailVO claimDetailDto, ReinsClaimDetail reinsClaimDetail) {
		try {
			ClaimItemVO claimItemDto = new ClaimItemVO();
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
				claimDetailDto.setClaimItemDtoList(new ArrayList<Object>());
			}
			claimDetailDto.setCurrency(reinsClaimDetail.getCurrency());
			claimDetailDto.getClaimItemDtoList().add(claimItemDto);
		} catch (Exception e) {
			System.err.println("送再保数据出现异常。。。。");
			e.printStackTrace();
		}
	}

	public ReinsClaimInterfAction getReinsClaimInterfAction() {
		return reinsClaimInterfAction;
	}

	public void setReinsClaimInterfAction(ReinsClaimInterfAction reinsClaimInterfAction) {
		this.reinsClaimInterfAction = reinsClaimInterfAction;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
}
