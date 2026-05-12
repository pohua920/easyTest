package com.sinosoft.claim.common.service.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PersonLossService;
import com.sinosoft.claim.dto.custom.PersonLossDto;
import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRisk;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpDpersonFeeCodeRiskService;

public class PersonLossServiceSpringImpl implements PersonLossService {
	private PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService;
	@Override
	public void initPersonLoss(String configCode, String riskCode, List<PrpLpersonLoss> personLossList, double medicalLimit, double deathLimit) throws Exception {
		// 首先得到按优先级排列好的医疗费用类型代码和死亡伤残费用类型代码
		List<String> compleMedicalCodeList = this.getCompelMedicalCodeList();
		List<String> compleDeathCodeList = this.getCompelDeathCodeList();
		double realPay = 0;
		if ("RISKCODE_DAZ".equals(configCode)) { // 强制得核定赔偿 按费用优先级别赋值
			// 1.首先对所有费用明细的强制保险各医疗费用赋值
			for (String medicalCode : compleMedicalCodeList) {
				for (PrpLpersonLoss prpLpersonLoss : personLossList) {
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLpersonLoss.getKindCode())) {
						if (medicalCode.equals(prpLpersonLoss.getLiabDetailCode())) {
							prpLpersonLoss.setFeeCategory("M");
							if (medicalLimit >= (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest())) {
								// 设置强制赔付金额
								prpLpersonLoss.setSumDefPay(prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
								// 去除限额
								medicalLimit -= (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
							} else if (medicalLimit > 0) {
								// 设置强制赔付金额
								prpLpersonLoss.setSumDefPay(medicalLimit);
								// 去除限额
								medicalLimit = 0;
							} else {
								prpLpersonLoss.setSumDefPay(0);
							}
							prpLpersonLoss.setSumRealPay(prpLpersonLoss.getSumDefPay()); // add
						}
					}
				}
			}
			// 2.其次对所有明细的强制保险死亡伤残费用赋值
			for (String deathCode : compleDeathCodeList) {
				for (PrpLpersonLoss prpLpersonLoss : personLossList) {
					if (ConstantCodes.KINDCODE_D_BZ.equals(prpLpersonLoss.getKindCode())) {
						if (deathCode.equals(prpLpersonLoss.getLiabDetailCode())) {
							prpLpersonLoss.setFeeCategory("D");
							if (deathLimit >= (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest())) {
								// 设置强制赔付金额
								prpLpersonLoss.setSumDefPay(prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
								// 去除限额
								deathLimit -= (prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
							} else if (deathLimit > 0) {
								// 设置强制赔付金额
								prpLpersonLoss.setSumDefPay(deathLimit);
								// 去除限额
								deathLimit = 0;
							} else {
								prpLpersonLoss.setSumDefPay(0);
							}
							prpLpersonLoss.setSumRealPay(prpLpersonLoss.getSumDefPay());
						}
					}
				}
			}
		} else if ("RISKCODE_DAY".equals(configCode)) { // 0505核定赔偿设为零
			if(personLossList.size()>0){
			for (PrpLpersonLoss prpLpersonLoss : personLossList) {
				if (ConstantCodes.KINDCODE_D_B.equals(prpLpersonLoss.getKindCode())) {
					prpLpersonLoss.setSumDefPay(0);
				} else {
					prpLpersonLoss.setSumDefPay(prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
				}
				prpLpersonLoss.setFeeCategory(this.getBusinessFeeCategory(riskCode, prpLpersonLoss.getLiabDetailCode()));
				realPay = (prpLpersonLoss.getSumDefPay()) * (prpLpersonLoss.getClaimRate() * 0.01) * (prpLpersonLoss.getIndemnityDutyRate() * 0.01) * (1 - (prpLpersonLoss.getDutyDeductibleRate() * 0.01))
						* (1 - (prpLpersonLoss.getDeductiblerate() * 0.01));
				prpLpersonLoss.setSumRealPay(realPay);

			}
		} else { // 其他的车险 核定赔偿设为 核定损失-残值
			if(personLossList.size()>0){
			for (PrpLpersonLoss prpLpersonLoss : personLossList) {
				prpLpersonLoss.setSumDefPay(prpLpersonLoss.getSumLoss() - prpLpersonLoss.getSumRest());
				prpLpersonLoss.setFeeCategory(this.getBusinessFeeCategory(riskCode, prpLpersonLoss.getLiabDetailCode()));
				realPay = (prpLpersonLoss.getSumDefPay()) * (prpLpersonLoss.getClaimRate() * 0.01) * (prpLpersonLoss.getIndemnityDutyRate() * 0.01)
						* (1 - (prpLpersonLoss.getDutyDeductibleRate() * 0.01 + prpLpersonLoss.getDeductiblerate() * 0.01));
				prpLpersonLoss.setSumRealPay(realPay);
			}
			}
		}
		}
	}

	private String getBusinessFeeCategory(String riskCode, String feeCode) throws Exception {
		String[] businessMedicalCodeArray = this.getBusinessMedicalCodeArray(riskCode);
		String[] businessDeathCodeArray = this.getBusinessDeathCodeArray(riskCode);
		if (ArrayUtils.contains(businessMedicalCodeArray, feeCode)) {
			return PersonLossDto.FeeCategory.MEDICAL;
		} else if (ArrayUtils.contains(businessDeathCodeArray, feeCode)) {
			return PersonLossDto.FeeCategory.DEATH;
		} else {
			return null;
		}
	}

	/**
	 * 人伤费用险种对照
	 * @return
	 */
	private List<String> getCompelMedicalCodeList() throws Exception {
//		BLPrpDpersonFeeCodeRiskFacade facade = new BLPrpDpersonFeeCodeRiskFacade();
//		List<PrpDpersonFeeCodeRiskDto> personFeeCodeList = facade.findCompelMedicalCodeList();
		List<PrpDpersonFeeCodeRisk> personFeeCodeList = prpDpersonFeeCodeRiskService.findCompelMedicalCodeList();
		
		List<String> compelMedicalCodeList = new ArrayList<String>();
		for (Iterator<PrpDpersonFeeCodeRisk> iter = personFeeCodeList.iterator(); iter.hasNext();) {
			PrpDpersonFeeCodeRisk personFeeCode = (PrpDpersonFeeCodeRisk) iter.next();//??
			compelMedicalCodeList.add(personFeeCode.getId().getFeeCode());
		}
		return compelMedicalCodeList;
	}
	/**
	 * 获得强制保险的死亡伤残费用类型
	 * @Description:
	 * @author 中科软
	 */
	private List<String> getCompelDeathCodeList() throws Exception {
//		BLPrpDpersonFeeCodeRiskFacade facade = new BLPrpDpersonFeeCodeRiskFacade();
//		List<PrpDpersonFeeCodeRiskDto> personFeeCodeList  = facade.findCompelDeathCodeList();
		List<PrpDpersonFeeCodeRisk> personFeeCodeList = prpDpersonFeeCodeRiskService.findCompelDeathCodeList();
		List<String> compelDeathCodeList = new ArrayList<String>();
		for (Iterator<PrpDpersonFeeCodeRisk> iter = personFeeCodeList.iterator(); iter.hasNext();) {
			PrpDpersonFeeCodeRisk personFeeCode = (PrpDpersonFeeCodeRisk) iter.next();
			compelDeathCodeList.add(personFeeCode.getId().getFeeCode());
		}
		return compelDeathCodeList;
	}

	private String[] getBusinessMedicalCodeArray(String riskCode) throws Exception {
//		BLPrpDpersonFeeCodeRiskFacade facade = new BLPrpDpersonFeeCodeRiskFacade();
//		List<PrpDpersonFeeCodeRiskDto> personFeeCodeList = facade.findMedicalCodeList(riskCode);
		List<PrpDpersonFeeCodeRisk> personFeeCodeList = prpDpersonFeeCodeRiskService.findMedicalCodeList(riskCode);
		String[] businessMedicalCodeArray = new String[personFeeCodeList.size()];
		int i = 0;
		for (Iterator<PrpDpersonFeeCodeRisk> iter = personFeeCodeList.iterator(); iter.hasNext();) {
			PrpDpersonFeeCodeRisk personFeeCode = (PrpDpersonFeeCodeRisk) iter.next();
			businessMedicalCodeArray[i++] = personFeeCode.getId().getFeeCode();
		}
		return businessMedicalCodeArray;
	}

	private String[] getBusinessDeathCodeArray(String riskCode) throws Exception {
//		BLPrpDpersonFeeCodeRiskFacade facade = new BLPrpDpersonFeeCodeRiskFacade();
//		List<PrpDpersonFeeCodeRiskDto> personFeeCodeList = facade.findDeathCodeList(riskCode);
		List<PrpDpersonFeeCodeRisk> personFeeCodeList = prpDpersonFeeCodeRiskService.findDeathCodeList(riskCode);
		String[] businessDeathCodeArray = new String[personFeeCodeList.size()];
		int i = 0;
		for (Iterator<PrpDpersonFeeCodeRisk> iter = personFeeCodeList.iterator(); iter.hasNext();) {
			PrpDpersonFeeCodeRisk personFeeCode = (PrpDpersonFeeCodeRisk) iter.next();
			businessDeathCodeArray[i++] = personFeeCode.getId().getFeeCode();
		}
		return businessDeathCodeArray;
	}

	public PrpDpersonFeeCodeRiskService getPrpDpersonFeeCodeRiskService() {
		return prpDpersonFeeCodeRiskService;
	}

	public void setPrpDpersonFeeCodeRiskService(
			PrpDpersonFeeCodeRiskService prpDpersonFeeCodeRiskService) {
		this.prpDpersonFeeCodeRiskService = prpDpersonFeeCodeRiskService;
	}

}
