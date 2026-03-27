package com.sinosoft.claim.compensate.util;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclause;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpLclauseService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonHospitalService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;

/***
 * 非車理算自動帶出估損 非車二次理算自動帶出保留預估項目
 * @author 中科軟
 */
public class CompensateGenerateLossViewHelper {

	private CodeService codeService;
	private CompensateService compensateService;
	private EndorseViewHelper endorseViewHelper;

	private PrpLlossService prpLlossService;

	private PrpLpersonLossService prpLpersonLossService;

	private PrpLpersonHospitalService prpLpersonHospitalService;

	private PrpCitemKindService prpCitemKindService;
	private PrpLclauseService prpLclauseService;
	
	public void generateLoss(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String riskCode = prpLclaim.getRiskCode();
		String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {// 傷害險
			this.generateAcci(request, compensateDto, claimDto);
		} else if (ConstantCodes.CLASSCODE_Y.equals(riskType)) {// 水險
			this.generateShip(request, compensateDto, claimDto);
		} else if (ConstantCodes.CLASSCODE_Q.equals(riskType)) {// 火險
			this.generateProp(request, compensateDto, claimDto);
		} else if (ConstantCodes.CLASSCODE_G.equals(riskType)) {// 工程險
			this.generateGaa(request, compensateDto, claimDto);
		} else if (ConstantCodes.CLASSCODE_Z.equals(riskType)) {// 責任險
			this.generateLiab(request, compensateDto, claimDto);
		}
	}

	/***
	 * 責任險理算帶出
	 * @param request
	 * @param compensateDto
	 * @param claimDto
	 * @throws Exception
	 */
	private void generateLiab(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String conditions = "  ClaimNo = '" + prpLclaim.getClaimNo() + "' and compensateNo like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		//
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for (PrpLcompensate prpLcompensate : compensateList) {
				compeNo = prpLcompensate.getCompensateNo();
				if (conditions.length() > 0) {
					conditions += " or ";
				}
				conditions += "compensateNo = '" + compeNo + "'";
			}
		}
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		String tempKey = null;
		//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
//		PrpCitemKind tempPrpCitemKind = null;
		Map<String, Double> kindLossMap = new HashMap<String, Double>();// 險別 金額
		Map<String, PrpLclaimLoss> propLossMap = new HashMap<String, PrpLclaimLoss>();// 賠付財產的估損
		Map<String, PrpLclaimLoss> personLossMap = new HashMap<String, PrpLclaimLoss>();// 賠付人傷的估損
		String kindCode = null;
		if (claimLoss != null && !claimLoss.isEmpty()) {// 存在估損
			String feeCategory = null;
			for (PrpLclaimLoss loss : claimLoss) {
				if ("P".equals(loss.getLossFeeType())) {
					kindCode = loss.getKindCode();
					feeCategory = loss.getFeeCategory();
					if (kindLossMap.containsKey(kindCode)) {// 險別估損合併
						kindLossMap.put(kindCode, loss.getSumClaim() + kindLossMap.get(kindCode));
					} else {
						kindLossMap.put(kindCode, loss.getSumClaim());
					}
					tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(loss.getItemCode());
					if ("G".equals(feeCategory)) {// 財損賠付
						if (propLossMap.containsKey(tempKey)) {// 險別估損合併
							PrpLclaimLoss temp = propLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							propLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
					} else if ("M".equals(feeCategory) || "H".equals(feeCategory) || "D".equals(feeCategory)) {
						if (personLossMap.containsKey(tempKey)) {// 險別估損合併
							PrpLclaimLoss temp = personLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							personLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
					}
				}
			}
			List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
			List<PrpLpersonLoss> prpLpersonLossList = new ArrayList<PrpLpersonLoss>();
			int serialNo = 0;
			if (CommonUtils.isEmpty(conditions)) {// 初次理算
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
				List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
				Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
				Map<String, PrpCitemKind> itemKindMap = new HashMap<String, PrpCitemKind>();// 險別序號映射
				Map<String, PrpCitemKind> KindCodeMap = new HashMap<String, PrpCitemKind>();// 險別映射
				Map<String, PrpCitemKind> virtualKindMap = new HashMap<String, PrpCitemKind>();
				// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -start
				if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
					PrpCitemKind tempPrpCitemKind = null;
					for (PrpCitemKind p : prpCitemKindList) {
						tempPrpCitemKind = new PrpCitemKind();
						PropertyUtils.copyProperties(tempPrpCitemKind, p);
						itemKindNoMap.put(String.valueOf(tempPrpCitemKind.getId().getItemKindNo()), tempPrpCitemKind);
						KindCodeMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);
						// 虛擬標的情況處理
						List<PrpCitemKind> virtualKindList = prpCitemKindService.generateVirtualKind(tempPrpCitemKind);
						if (!CommonUtils.isEmpty(virtualKindList)) {
							virtualKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
							virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
							double sumAmount = 0d;
							for (PrpCitemKind sp : virtualKindList) {
								virtualKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
								virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
								sumAmount += sp.getAmount();
								tempPrpCitemKind.setAmount(sumAmount);
							}
						} else {
							itemKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode()), tempPrpCitemKind);
						}
					}
				}
				// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -end
				String currency = null;
				if (!propLossMap.isEmpty()) {
					serialNo = 0;
					PrpLloss prpLloss = null;
					PrpLclaimLoss tempPrpLclaimLoss = null;
					for (Map.Entry<String, PrpLclaimLoss> entry : propLossMap.entrySet()) {
						tempPrpLclaimLoss = entry.getValue();
						kindCode = tempPrpLclaimLoss.getKindCode();
						// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
						PrpCitemKind tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLclaimLoss.getItemCode(), tempPrpLclaimLoss.getItemKindNo());
						if (tempPrpCitemKind != null) {
							prpLloss = new PrpLloss();
							prpLloss.getId().setSerialNo(++serialNo);
							prpLloss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
							prpLloss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
							prpLloss.setAmount(tempPrpCitemKind.getAmount());
							prpLloss.setPolicyNo(tempPrpCitemKind.getId().getPolicyNo());
							prpLloss.setRiskCode(tempPrpCitemKind.getRiskCode());
							prpLloss.setKindCode(tempPrpCitemKind.getKindCode());
							prpLloss.setKindName(tempPrpCitemKind.getKindName());
							String itemCode = codeService.getItemCode(tempPrpCitemKind);
							String itemName = codeService.getItemName(tempPrpCitemKind);
							prpLloss.setItemCode(itemCode);
							prpLloss.setLossName(itemName);
							prpLloss.setUnitPrice(tempPrpCitemKind.getUnitAmount() == null ? 0 : tempPrpCitemKind.getUnitAmount());
							prpLloss.setLossQuantity(tempPrpCitemKind.getQuantity() == null ? 0 : tempPrpCitemKind.getQuantity());
							prpLloss.setItemValue(tempPrpCitemKind.getValue());
							prpLloss.setDeductiblerate(tempPrpCitemKind.getDeductibleRate());
							prpLloss.setDeductible(tempPrpCitemKind.getDeductible());
							currency = tempPrpLclaimLoss.getCurrency();
							prpLloss.setCurrency(currency);
							prpLloss.setCurrency1(currency);
							prpLloss.setCurrency2(currency);
							prpLloss.setCurrency3(currency);
							prpLloss.setCurrency4(currency);
							prpLloss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
							prpLloss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
							prpLloss.setSumRest(0);
							prpLloss.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
							prpLloss.setArrangeRate(100);
							prpLloss.setClaimRate(100);
							prpLloss.setDutyDeductibleRate(0d);// 事故责任免赔率
							if (tempPrpCitemKind.getFlag() != null && tempPrpCitemKind.getFlag().length() > 4) {
								prpLloss.setFlag(tempPrpCitemKind.getFlag().substring(4, 5).trim());
							} else {
								prpLloss.setFlag("0");
							}
							prpLloss.setExceptDeductiblePay(0);
							prpLloss.setExceptDeductibleRate(0);
							double sum = 0d;
							if (prpLloss.getDeductible() != 0) {
								sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
							} else {
								sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
							}
							prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
							prpLlossList.add(prpLloss);
						}
					}
				}
				// 估損但沒有賠付的險別，先行帶出
				if (!personLossMap.isEmpty()) {
					serialNo = 0;
					PrpLpersonLoss tempPrpLpersonLoss = null;
					// 受害人从人傷跟蹤里带出
					List<PrpLpersonTrace> prpLpersonTraceList = claimDto.getPrpLpersonTraceList();
					if (!CommonUtils.isEmpty(prpLpersonTraceList)) {
						for (PrpLpersonTrace tempPrpLpersonTrace : prpLpersonTraceList) {
							for (Entry<String, PrpLclaimLoss> entry : personLossMap.entrySet()) {
								PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
								kindCode = tempPrpLclaimLoss.getKindCode();
								// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
								PrpCitemKind tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLclaimLoss.getItemCode(), tempPrpLclaimLoss.getItemKindNo());
								// 不為0，且每一個人體傷或死亡 \每一事故體傷或死亡之保額不為0，則該險別可以賠付人傷
								if (tempPrpCitemKind != null && (tempPrpCitemKind.getPerHumanInjury() > 0 || tempPrpCitemKind.getPerHumanDeath() > 0)) {
									tempPrpLpersonLoss = new PrpLpersonLoss();
									tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
									tempPrpLpersonLoss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
									tempPrpLpersonLoss.setAmount(0);
									tempPrpLpersonLoss.setKindCode(kindCode);
									tempPrpLpersonLoss.setKindName(tempPrpCitemKind.getKindName());
									tempPrpLpersonLoss.setPersonNo(tempPrpLpersonTrace.getId().getPersonNo());
									tempPrpLpersonLoss.setPersonName(tempPrpLpersonTrace.getPersonName());
									tempPrpLpersonLoss.setSex(tempPrpLpersonTrace.getPersonSex());
									tempPrpLpersonLoss.setAge(tempPrpLpersonTrace.getPersonAge() != null ? tempPrpLpersonTrace.getPersonAge().intValue() : 0);
									tempPrpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									// 协商赔偿比例默认为100%
									tempPrpLpersonLoss.setClaimRate(100);
									tempPrpLpersonLoss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
									// 赔付合计
									tempPrpLpersonLoss.setDeductible(tempPrpCitemKind.getDeductible());
									tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
									tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
									double sumRealPay = tempPrpLpersonLoss.getSumDefPay() - tempPrpLpersonLoss.getDeductible();
									tempPrpLpersonLoss.setSumRealPay(sumRealPay);
									prpLpersonLossList.add(tempPrpLpersonLoss);
								}
							}
						}
					}
				}
			} else {// 二次理算 , 帶出保留預估
				Map<String, Double> payMap = new HashMap<String, Double>();// 各險別已賠付
				// 統計標的賠付
				Map<String, PrpLloss> mapPrpLloss = new LinkedHashMap<String, PrpLloss>();
				Map<String, PrpLpersonLoss> mapPrpLpersonLoss = new LinkedHashMap<String, PrpLpersonLoss>();
				double realPay = 0d;
				conditions = " ( " + conditions + " ) order by compensateNo asc , serialno asc ";
				// 標的賠付
				List<PrpLloss> tempPrpLlosslist = this.prpLlossService.findByConditions(conditions);
				for (PrpLloss tempPrpLloss : tempPrpLlosslist) {
					kindCode = tempPrpLloss.getKindCode();
					propLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
					tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpLloss.getItemCode());
					if (tempPrpLloss.getId().getCompensateNo().charAt(0) == 'C') {
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠付
						realPay = CommonUtils.round(tempPrpLloss.getSumLoss() * tempPrpLloss.getExchRate(), 0);
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLloss.put(tempKey, tempPrpLloss);
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							payMap.put(kindCode, realPay);
						}
					}
				}
				// 受害人賠付
				List<PrpLpersonLoss> tempPrpLpersonLosslist = this.prpLpersonLossService.findByConditions(conditions);
				for (PrpLpersonLoss tempPrpLpersonLoss : tempPrpLpersonLosslist) {
					kindCode = tempPrpLpersonLoss.getKindCode();
					propLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
					if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C') {
						tempKey = tempPrpLpersonLoss.getPersonName() + "_" + tempPrpLpersonLoss.getIdentifyNumber() + "_" + kindCode + "_" + tempPrpLpersonLoss.getLiabDetailCode();
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠償
						realPay = CommonUtils.round(tempPrpLpersonLoss.getSumDefPay() * tempPrpLpersonLoss.getExchRate(), 0);
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							kindLossMap.put(kindCode, realPay);
						}
					}
				}
				if (!mapPrpLloss.isEmpty()) {
					serialNo = 0;
					PrpLloss prpLloss = null;
					for (Map.Entry<String, PrpLloss> entry : mapPrpLloss.entrySet()) {
						prpLloss = entry.getValue();
						if ("Y".equals(prpLloss.getReservedEstimate())) {// 該筆賠付有保留預估
							kindCode = prpLloss.getKindCode();
							Double claimLossValue = kindLossMap.get(kindCode);// （險別）之預估金額
							Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
							if (claimLossValue != null) {// 有預估
								if (hasPayValue == null) {
									hasPayValue = 0d;
								}
								Double sumLoss = claimLossValue - hasPayValue;
								if (sumLoss >= 0) {
									prpLloss.getId().setSerialNo(++serialNo);
									prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setExchRate(1D);
									prpLloss.setSumLoss(sumLoss);
									prpLloss.setSumDefPay(sumLoss);
									prpLloss.setExchRate(1D);
									// 保留預估二次賠付，不需要再扣除殘值、自負額
									prpLloss.setSumRest(0d);
									prpLloss.setDeductible(0d);
									prpLloss.setDeductiblerate(0d);
									double sum = 0d;
									if (prpLloss.getDeductible() != 0) {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
									} else {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
									}
									prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
									prpLloss.setPayObjectSerialNo("");
									prpLlossList.add(prpLloss);
								}
							}
						}
					}
				}
				Map<String, List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String, List<PrpLpersonLoss>>();
				List<PrpLpersonLoss> allPersonLoss = null;
				Map<String, String> tempNo = new HashMap<String, String>();// 存新序人傷序號
				String personNo = null;
				// 保留處理預估的人傷
				if (!mapPrpLpersonLoss.isEmpty()) {
					String uniqueIdentify = null;// 受害人唯一身份標識
					PrpLpersonLoss prpLpersonLoss = null;
					for (Map.Entry<String, PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()) {
						prpLpersonLoss = entry.getValue();
						if ("Y".equals(prpLpersonLoss.getReservedEstimate())) {// 該筆賠付有保留預估
							kindCode = prpLpersonLoss.getKindCode();
							Double claimLossValue = kindLossMap.get(kindCode);// （險別）之預估金額
							Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
							if (claimLossValue != null) {// 有預估
								if (hasPayValue == null) {
									hasPayValue = 0d;
								}
								Double sumLoss = claimLossValue - hasPayValue;
								if (sumLoss >= 0) {// 有預估，有保留且，保留預估 大於 0
									prpLpersonLoss.setSumLoss(sumLoss);
									prpLpersonLoss.setSumDefPay(sumLoss);
									// 保留預估帶出的幣別設置為本位幣，NTD
									prpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setExchRate(1D);
									prpLpersonLoss.setDeductible(0d);// 保留預估賠付不需要再次扣除自負額，除非自行錄入
									prpLpersonLoss.setSumRealPay(sumLoss);
									prpLpersonLoss.setPayObjectSerialNo("");
									prpLpersonLoss.getId().setCompensateNo("");
									uniqueIdentify = prpLpersonLoss.getPersonName() + "_" + DataUtils.dbNullToEmpty(prpLpersonLoss.getIdentifyNumber());
									if (tempNo.containsKey(uniqueIdentify)) {// 身份證號碼代表同一個受害人
										allPersonLoss = allPerson.get(tempNo.get(uniqueIdentify));
										personNo = tempNo.get(uniqueIdentify);
									} else {
										allPersonLoss = new ArrayList<PrpLpersonLoss>();
										personNo = String.valueOf(allPerson.size() + 1);
										tempNo.put(uniqueIdentify, personNo);
									}
									allPersonLoss.add(prpLpersonLoss);
									allPerson.put(personNo, allPersonLoss);
								}
							}
						}
					}
				}
				serialNo = 0;
				for (Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()) {
					personNo = entry.getKey();
					allPersonLoss = entry.getValue();
					// 整理每個受害人序號
					for (PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss) {
						tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
						tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
						prpLpersonLossList.add(tempPrpLpersonLoss);
					}
				}
			}
			compensateDto.setPrpLlossList(prpLlossList);
			compensateDto.setPrpLpersonLossList(prpLpersonLossList);
		}
	}

	/***
	 * 工程險理算帶出
	 * @param request
	 * @param compensateDto
	 * @param claimDto
	 * @throws Exception
	 */
	private void generateGaa(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String conditions = "  ClaimNo = '" + prpLclaim.getClaimNo() + "' and compensateNo like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		//
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for (PrpLcompensate prpLcompensate : compensateList) {
				compeNo = prpLcompensate.getCompensateNo();
				if (conditions.length() > 0) {
					conditions += " or ";
				}
				conditions += "compensateNo = '" + compeNo + "'";
			}
		}
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		String tempKey = null;
		//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
//		PrpCitemKind tempPrpCitemKind = null;
		Map<String, Double> kindLossMap = new HashMap<String, Double>();// 險別 金額
		Map<String, PrpLclaimLoss> propLossMap = new HashMap<String, PrpLclaimLoss>();// 賠付財產的估損
		Map<String, PrpLclaimLoss> personLossMap = new HashMap<String, PrpLclaimLoss>();// 賠付人傷的估損
		String kindCode = null;
		if (claimLoss != null && !claimLoss.isEmpty()) {// 存在估損
			String feeCategory = null;
			for (PrpLclaimLoss loss : claimLoss) {
				if ("P".equals(loss.getLossFeeType())) {
					kindCode = loss.getKindCode();
					feeCategory = loss.getFeeCategory();
					if (kindLossMap.containsKey(kindCode)) {// 險別估損合併
						kindLossMap.put(kindCode, loss.getSumClaim() + kindLossMap.get(kindCode));
					} else {
						kindLossMap.put(kindCode, loss.getSumClaim());
					}
					tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(loss.getItemCode());
					if ("G".equals(feeCategory)) {// 財損賠付
						if (propLossMap.containsKey(tempKey)) {// 險別估損合併
							PrpLclaimLoss temp = propLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							propLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
					} else if ("M".equals(feeCategory) || "H".equals(feeCategory) || "D".equals(feeCategory)) {
						if (personLossMap.containsKey(tempKey)) {// 險別估損合併
							PrpLclaimLoss temp = personLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							personLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
					}
				}
			}
			List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
			List<PrpLpersonLoss> prpLpersonLossList = new ArrayList<PrpLpersonLoss>();
			int serialNo = 0;
			if (CommonUtils.isEmpty(conditions)) {// 初次理算
				//PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(prpLclaim.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
				List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
				Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
				Map<String, PrpCitemKind> itemKindMap = new HashMap<String, PrpCitemKind>();// 險別序號映射
				Map<String, PrpCitemKind> KindCodeMap = new HashMap<String, PrpCitemKind>();// 險別映射
				Map<String, PrpCitemKind> virtualKindMap = new HashMap<String, PrpCitemKind>();
				// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -start
				if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
					PrpCitemKind tempPrpCitemKind = null;
					for (PrpCitemKind p : prpCitemKindList) {
						tempPrpCitemKind = new PrpCitemKind();
						PropertyUtils.copyProperties(tempPrpCitemKind, p);
						itemKindNoMap.put(String.valueOf(tempPrpCitemKind.getId().getItemKindNo()), tempPrpCitemKind);
						KindCodeMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);
						// 虛擬標的情況處理
						List<PrpCitemKind> virtualKindList = prpCitemKindService.generateVirtualKind(tempPrpCitemKind);
						if (!CommonUtils.isEmpty(virtualKindList)) {
							virtualKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
							virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
							double sumAmount = 0d;
							for (PrpCitemKind sp : virtualKindList) {
								virtualKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
								virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
								sumAmount += sp.getAmount();
								tempPrpCitemKind.setAmount(sumAmount);
							}
						} else {
							itemKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode()), tempPrpCitemKind);
						}
					}
				}
				// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -end
				String currency = null;
				if (!propLossMap.isEmpty()) {
					serialNo = 0;
					PrpLloss prpLloss = null;
					PrpLclaimLoss tempPrpLclaimLoss = null;
					for (Map.Entry<String, PrpLclaimLoss> entry : propLossMap.entrySet()) {
						tempPrpLclaimLoss = entry.getValue();
						kindCode = tempPrpLclaimLoss.getKindCode();
						//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
						PrpCitemKind tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLclaimLoss.getItemCode(), tempPrpLclaimLoss.getItemKindNo());
						if (tempPrpCitemKind != null) {
							prpLloss = new PrpLloss();
							prpLloss.getId().setSerialNo(++serialNo);
							prpLloss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
							prpLloss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
							prpLloss.setAmount(tempPrpCitemKind.getAmount());
							prpLloss.setPolicyNo(tempPrpCitemKind.getId().getPolicyNo());
							prpLloss.setRiskCode(tempPrpCitemKind.getRiskCode());
							prpLloss.setKindCode(tempPrpCitemKind.getKindCode());
							prpLloss.setKindName(tempPrpCitemKind.getKindName());
							String itemCode = codeService.getItemCode(tempPrpCitemKind);
							String itemName = codeService.getItemName(tempPrpCitemKind);
							prpLloss.setItemCode(itemCode);
							prpLloss.setLossName(itemName);
							prpLloss.setUnitPrice(tempPrpCitemKind.getUnitAmount() == null ? 0 : tempPrpCitemKind.getUnitAmount());
							prpLloss.setLossQuantity(tempPrpCitemKind.getQuantity() == null ? 0 : tempPrpCitemKind.getQuantity());
							prpLloss.setItemValue(tempPrpCitemKind.getValue());
							prpLloss.setDeductiblerate(tempPrpCitemKind.getDeductibleRate());
							prpLloss.setDeductible(tempPrpCitemKind.getDeductible());
							currency = tempPrpLclaimLoss.getCurrency();
							prpLloss.setCurrency(currency);
							prpLloss.setCurrency1(currency);
							prpLloss.setCurrency2(currency);
							prpLloss.setCurrency3(currency);
							prpLloss.setCurrency4(currency);
							prpLloss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
							prpLloss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
							prpLloss.setSumRest(0);
							prpLloss.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
							prpLloss.setArrangeRate(100);
							prpLloss.setClaimRate(100);
							prpLloss.setDutyDeductibleRate(0d);// 事故责任免赔率
							if (tempPrpCitemKind.getFlag() != null && tempPrpCitemKind.getFlag().length() > 4) {
								prpLloss.setFlag(tempPrpCitemKind.getFlag().substring(4, 5).trim());
							} else {
								prpLloss.setFlag("0");
							}
							prpLloss.setExceptDeductiblePay(0);
							prpLloss.setExceptDeductibleRate(0);
							double sum = 0d;
							if (prpLloss.getDeductible() != 0) {
								sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
							} else {
								sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
							}
							prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
							prpLlossList.add(prpLloss);
						}
					}
				}
				// 估損但沒有賠付的險別，先行帶出
				if (!personLossMap.isEmpty()) {
					serialNo = 0;
					PrpLpersonLoss tempPrpLpersonLoss = null;
					// 受害人从人傷跟蹤里带出
					List<PrpLpersonTrace> prpLpersonTraceList = claimDto.getPrpLpersonTraceList();
					if (!CommonUtils.isEmpty(prpLpersonTraceList)) {
						for (PrpLpersonTrace tempPrpLpersonTrace : prpLpersonTraceList) {
							for (Entry<String, PrpLclaimLoss> entry : personLossMap.entrySet()) {
								PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
								kindCode = tempPrpLclaimLoss.getKindCode();
								//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
								PrpCitemKind tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLclaimLoss.getItemCode(), tempPrpLclaimLoss.getItemKindNo());
								// 不為0，且每一個人體傷或死亡 \每一事故體傷或死亡之保額不為0，則該險別可以賠付人傷
								if (tempPrpCitemKind != null && (tempPrpCitemKind.getPerHumanInjury() > 0 || tempPrpCitemKind.getPerHumanDeath() > 0)) {
									tempPrpLpersonLoss = new PrpLpersonLoss();
									tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
									tempPrpLpersonLoss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
									tempPrpLpersonLoss.setAmount(0);
									tempPrpLpersonLoss.setKindCode(kindCode);
									tempPrpLpersonLoss.setKindName(tempPrpCitemKind.getKindName());
									tempPrpLpersonLoss.setPersonNo(tempPrpLpersonTrace.getId().getPersonNo());
									tempPrpLpersonLoss.setPersonName(tempPrpLpersonTrace.getPersonName());
									tempPrpLpersonLoss.setSex(tempPrpLpersonTrace.getPersonSex());
									tempPrpLpersonLoss.setAge(tempPrpLpersonTrace.getPersonAge() != null ? tempPrpLpersonTrace.getPersonAge().intValue() : 0);
									tempPrpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									// 协商赔偿比例默认为100%
									tempPrpLpersonLoss.setClaimRate(100);
									tempPrpLpersonLoss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
									// 赔付合计
									tempPrpLpersonLoss.setDeductible(tempPrpCitemKind.getDeductible());
									tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
									tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
									double sumRealPay = tempPrpLpersonLoss.getSumDefPay() - tempPrpLpersonLoss.getDeductible();
									tempPrpLpersonLoss.setSumRealPay(sumRealPay);
									prpLpersonLossList.add(tempPrpLpersonLoss);
								}
							}
						}
					}
				}
			} else {// 二次理算 , 帶出保留預估
				Map<String, Double> payMap = new HashMap<String, Double>();// 各險別已賠付
				// 統計標的賠付
				Map<String, PrpLloss> mapPrpLloss = new LinkedHashMap<String, PrpLloss>();
				Map<String, PrpLpersonLoss> mapPrpLpersonLoss = new LinkedHashMap<String, PrpLpersonLoss>();
				double realPay = 0d;
				conditions = " ( " + conditions + " ) order by compensateNo asc , serialno asc ";
				// 標的賠付
				List<PrpLloss> tempPrpLlosslist = this.prpLlossService.findByConditions(conditions);
				for (PrpLloss tempPrpLloss : tempPrpLlosslist) {
					kindCode = tempPrpLloss.getKindCode();
					propLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
					tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpLloss.getItemCode());
					if (tempPrpLloss.getId().getCompensateNo().charAt(0) == 'C') {
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠付
						realPay = CommonUtils.round(tempPrpLloss.getSumLoss() * tempPrpLloss.getExchRate(), 0);
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLloss.put(tempKey, tempPrpLloss);
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							payMap.put(kindCode, realPay);
						}
					}
				}
				// 受害人賠付
				List<PrpLpersonLoss> tempPrpLpersonLosslist = this.prpLpersonLossService.findByConditions(conditions);
				for (PrpLpersonLoss tempPrpLpersonLoss : tempPrpLpersonLosslist) {
					kindCode = tempPrpLpersonLoss.getKindCode();
					propLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
					if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C') {
						tempKey = tempPrpLpersonLoss.getPersonName() + "_" + tempPrpLpersonLoss.getIdentifyNumber() + "_" + kindCode + "_" + tempPrpLpersonLoss.getLiabDetailCode();
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠償
						realPay = CommonUtils.round(tempPrpLpersonLoss.getSumDefPay() * tempPrpLpersonLoss.getExchRate(), 0);
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							kindLossMap.put(kindCode, realPay);
						}
					}
				}
				if (!mapPrpLloss.isEmpty()) {
					serialNo = 0;
					PrpLloss prpLloss = null;
					for (Map.Entry<String, PrpLloss> entry : mapPrpLloss.entrySet()) {
						prpLloss = entry.getValue();
						if ("Y".equals(prpLloss.getReservedEstimate())) {// 該筆賠付有保留預估
							kindCode = prpLloss.getKindCode();
							Double claimLossValue = kindLossMap.get(kindCode);// （險別）之預估金額
							Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
							if (claimLossValue != null) {// 有預估
								if (hasPayValue == null) {
									hasPayValue = 0d;
								}
								Double sumLoss = claimLossValue - hasPayValue;
								if (sumLoss >= 0) {
									prpLloss.getId().setSerialNo(++serialNo);
									prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setExchRate(1D);
									prpLloss.setSumLoss(sumLoss);
									prpLloss.setSumDefPay(sumLoss);
									prpLloss.setExchRate(1D);
									// 保留預估二次賠付，不需要再扣除殘值、自負額
									prpLloss.setSumRest(0d);
									prpLloss.setDeductible(0d);
									prpLloss.setDeductiblerate(0d);
									double sum = 0d;
									if (prpLloss.getDeductible() != 0) {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
									} else {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
									}
									prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
									prpLloss.setPayObjectSerialNo("");
									prpLlossList.add(prpLloss);
								}
							}
						}
					}
				}
				Map<String, List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String, List<PrpLpersonLoss>>();
				List<PrpLpersonLoss> allPersonLoss = null;
				Map<String, String> tempNo = new HashMap<String, String>();// 存新序人傷序號
				String personNo = null;
				// 保留處理預估的人傷
				if (!mapPrpLpersonLoss.isEmpty()) {
					String uniqueIdentify = null;// 受害人唯一身份標識
					PrpLpersonLoss prpLpersonLoss = null;
					for (Map.Entry<String, PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()) {
						prpLpersonLoss = entry.getValue();
						if ("Y".equals(prpLpersonLoss.getReservedEstimate())) {// 該筆賠付有保留預估
							kindCode = prpLpersonLoss.getKindCode();
							Double claimLossValue = kindLossMap.get(kindCode);// （險別）之預估金額
							Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
							if (claimLossValue != null) {// 有預估
								if (hasPayValue == null) {
									hasPayValue = 0d;
								}
								Double sumLoss = claimLossValue - hasPayValue;
								if (sumLoss >= 0) {// 有預估，有保留且，保留預估 大於 0
									prpLpersonLoss.setSumLoss(sumLoss);
									prpLpersonLoss.setSumDefPay(sumLoss);
									// 保留預估帶出的幣別設置為本位幣，NTD
									prpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									prpLpersonLoss.setExchRate(1D);
									prpLpersonLoss.setDeductible(0d);// 保留預估賠付不需要再次扣除自負額，除非自行錄入
									prpLpersonLoss.setSumRealPay(sumLoss);
									prpLpersonLoss.setPayObjectSerialNo("");
									prpLpersonLoss.getId().setCompensateNo("");
									uniqueIdentify = prpLpersonLoss.getPersonName() + "_" + DataUtils.dbNullToEmpty(prpLpersonLoss.getIdentifyNumber());
									if (tempNo.containsKey(uniqueIdentify)) {// 身份證號碼代表同一個受害人
										allPersonLoss = allPerson.get(tempNo.get(uniqueIdentify));
										personNo = tempNo.get(uniqueIdentify);
									} else {
										allPersonLoss = new ArrayList<PrpLpersonLoss>();
										personNo = String.valueOf(allPerson.size() + 1);
										tempNo.put(uniqueIdentify, personNo);
									}
									allPersonLoss.add(prpLpersonLoss);
									allPerson.put(personNo, allPersonLoss);
								}
							}
						}
					}
				}
				serialNo = 0;
				for (Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()) {
					personNo = entry.getKey();
					allPersonLoss = entry.getValue();
					// 整理每個受害人序號
					for (PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss) {
						tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
						tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
						prpLpersonLossList.add(tempPrpLpersonLoss);
					}
				}
			}
			compensateDto.setPrpLlossList(prpLlossList);
			compensateDto.setPrpLpersonLossList(prpLpersonLossList);
		}
	}

	private PrpCitemKind getPrpCitemKind(Map<String, PrpCitemKind> virtualKindMap, Map<String, PrpCitemKind> itemKindNoMap, Map<String, PrpCitemKind> KindCodeMap, String kindCode, String itemCode, int itemKindNo) {
		PrpCitemKind tempPrpCitemKind = null;
		if (virtualKindMap.containsKey(kindCode)) {// 虛擬標的
			if (itemKindNo == 0) {
				if (CommonUtils.isEmpty(itemCode)) {
					tempPrpCitemKind = virtualKindMap.get(kindCode);
				} else {
					tempPrpCitemKind = virtualKindMap.get(kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
				}
			} else {
				if (CommonUtils.isEmpty(itemCode)) {
					tempPrpCitemKind = virtualKindMap.get(itemKindNo + "_" + kindCode);
				} else {
					tempPrpCitemKind = virtualKindMap.get(itemKindNo + "_" + kindCode + "_" + DataUtils.dbNullToEmpty(itemCode));
				}
			}
		} else {
			if (itemKindNo == 0) {
				tempPrpCitemKind = KindCodeMap.get(kindCode);
			} else {
				tempPrpCitemKind = itemKindNoMap.get(String.valueOf(itemKindNo));
				if (tempPrpCitemKind == null || !tempPrpCitemKind.getKindCode().equals(kindCode)) {
					tempPrpCitemKind = KindCodeMap.get(kindCode);
				}
			}
		}
		return tempPrpCitemKind;
	}

	/***
	 * 火險理算帶出
	 * @param request
	 * @param compensateDto
	 * @param claimDto
	 * @throws Exception
	 */
	private void generateProp(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String conditions = "  ClaimNo = '" + prpLclaim.getClaimNo() + "' and compensateNo like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		//
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for (PrpLcompensate prpLcompensate : compensateList) {
				compeNo = prpLcompensate.getCompensateNo();
				if (conditions.length() > 0) {
					conditions += " or ";
				}
				conditions += "compensateNo = '" + compeNo + "'";
			}
		}
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		//mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題
		List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
		Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
		Map<String, PrpCitemKind> itemKindMap = new HashMap<String, PrpCitemKind>();// 險別序號映射
		Map<String, PrpCitemKind> KindCodeMap = new HashMap<String, PrpCitemKind>();// 險別映射
		Map<String, PrpCitemKind> virtualKindMap = new HashMap<String, PrpCitemKind>();
		// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -start
		if (prpCitemKindList != null && !prpCitemKindList.isEmpty()) {
			PrpCitemKind tempPrpCitemKind = null;
			for (PrpCitemKind p : prpCitemKindList) {
				tempPrpCitemKind = new PrpCitemKind();
				PropertyUtils.copyProperties(tempPrpCitemKind, p);
				itemKindNoMap.put(String.valueOf(tempPrpCitemKind.getId().getItemKindNo()), tempPrpCitemKind);
				KindCodeMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);
				// 虛擬標的情況處理
				List<PrpCitemKind> virtualKindList = prpCitemKindService.generateVirtualKind(tempPrpCitemKind);
				if (!CommonUtils.isEmpty(virtualKindList)) {
					virtualKindMap.put(tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode(), tempPrpCitemKind);// 虛擬標的
					double sumAmount = 0d;
					for (PrpCitemKind sp : virtualKindList) {
						virtualKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						virtualKindMap.put(tempPrpCitemKind.getId().getItemKindNo() + "_" + tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(sp.getItemCode()), sp);
						sumAmount += sp.getAmount();
						tempPrpCitemKind.setAmount(sumAmount);
					}
				} else {
					itemKindMap.put(tempPrpCitemKind.getKindCode() + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode()), tempPrpCitemKind);
				}
			}
		}
		// mantis： CLM0117，處理人員：BK007 蘇哲，需求單編號：CLM0117.新核心-PB異常問題 -end
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		String tempKey = null;
		PrpCitemKind tempPrpCitemKind = null;
		Map<String, Double> lossMap = new HashMap<String, Double>();// 險別 + 标的,
																	// 估損金額
		Map<String, PrpLclaimLoss> claimLossMap = new HashMap<String, PrpLclaimLoss>();
		String kindCode = null;
		if (claimLoss != null && !claimLoss.isEmpty()) {// 存在估損
			for (PrpLclaimLoss loss : claimLoss) {
				if ("P".equals(loss.getLossFeeType())) {
					kindCode = loss.getKindCode();
					tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, loss.getItemCode(), loss.getItemKindNo());
					if (tempPrpCitemKind != null && tempPrpCitemKind.getKindCode().equals(loss.getKindCode())) {
						tempKey = tempPrpCitemKind.getKindCode();
						if (lossMap.containsKey(tempKey)) {// 險別估損合併
							lossMap.put(tempKey, loss.getSumClaim() + lossMap.get(tempKey));
						} else {
							lossMap.put(tempKey, loss.getSumClaim());
						}
						if (virtualKindMap.containsKey(loss.getKindCode())) {
							// 如果險別存在虛擬標的險別，虛擬標的無法確定標的，則以選擇的虛擬標的為主
							tempKey += "_" + DataUtils.dbNullToEmpty(loss.getItemCode());
							//
						} else {
							tempKey += "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode());
						}
						if (lossMap.containsKey(tempKey)) {// 險別估損合併
							lossMap.put(tempKey, loss.getSumClaim() + lossMap.get(tempKey));
							// 加總同險別同賠付類型的預估金額
							PrpLclaimLoss temp = claimLossMap.get(tempKey);
							temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
						} else {
							lossMap.put(tempKey, loss.getSumClaim());
							claimLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
						}
					}
				}
			}
		}
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		PrpLloss prpLlossDto = null;
		int serialNo = 0;
		Map<String, List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String, List<PrpLpersonLoss>>();
		List<PrpLpersonLoss> allPersonLoss = null;
		Map<String, String> tempNo = new HashMap<String, String>();// 存新序人傷序號
		String personNo = null;
		if (CommonUtils.isEmpty(conditions)) {
			String currency = null;
			if (!claimLossMap.isEmpty()) {
				PrpLclaimLoss tempPrpLclaimLoss = null;
				for (Map.Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()) {
					tempPrpLclaimLoss = entry.getValue();
					kindCode = tempPrpLclaimLoss.getKindCode();
					tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLclaimLoss.getItemCode(), tempPrpLclaimLoss.getItemKindNo());
					if (virtualKindMap.containsKey(kindCode)) {// 如果是虛擬標的，且賠付人傷的。
						String itemCode = tempPrpLclaimLoss.getItemCode();
						if (!CommonUtils.isEmpty(itemCode) && ("64".equals(itemCode) || "68".equals(itemCode))) {
							continue;
						}
					}
					prpLlossDto = new PrpLloss();
					prpLlossDto.getId().setSerialNo(++serialNo);
					prpLlossDto.setDangerNo(tempPrpLclaimLoss.getDangerNo());
					prpLlossDto.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
					prpLlossDto.setAmount(tempPrpCitemKind.getAmount());
					prpLlossDto.setPolicyNo(tempPrpCitemKind.getId().getPolicyNo());
					prpLlossDto.setRiskCode(tempPrpCitemKind.getRiskCode());
					prpLlossDto.setKindCode(tempPrpCitemKind.getKindCode());
					prpLlossDto.setKindName(tempPrpCitemKind.getKindName());
					prpLlossDto.setItemCode(tempPrpCitemKind.getItemCode());
					prpLlossDto.setLossName(tempPrpCitemKind.getItemName());
					prpLlossDto.setUnitPrice(tempPrpCitemKind.getUnitAmount() == null ? 0 : tempPrpCitemKind.getUnitAmount());
					prpLlossDto.setLossQuantity(tempPrpCitemKind.getQuantity() == null ? 0 : tempPrpCitemKind.getQuantity());
					prpLlossDto.setItemValue(tempPrpCitemKind.getValue());
					prpLlossDto.setDeductiblerate(tempPrpCitemKind.getDeductibleRate());
					prpLlossDto.setDeductible(tempPrpCitemKind.getDeductible());
					currency = tempPrpLclaimLoss.getCurrency();
					prpLlossDto.setCurrency(currency);
					prpLlossDto.setCurrency1(currency);
					prpLlossDto.setCurrency2(currency);
					prpLlossDto.setCurrency3(currency);
					prpLlossDto.setCurrency4(currency);
					prpLlossDto.setSumLoss(tempPrpLclaimLoss.getSumClaim());
					prpLlossDto.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
					prpLlossDto.setSumRest(0);
					prpLlossDto.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
					prpLlossDto.setArrangeRate(100);
					prpLlossDto.setClaimRate(100);
					prpLlossDto.setDutyDeductibleRate(0d);// 事故责任免赔率
					if (tempPrpCitemKind.getFlag() != null && tempPrpCitemKind.getFlag().length() > 4) {
						prpLlossDto.setFlag(tempPrpCitemKind.getFlag().substring(4, 5).trim());
					} else {
						prpLlossDto.setFlag("0");
					}
					prpLlossDto.setExceptDeductiblePay(0);
					prpLlossDto.setExceptDeductibleRate(0);
					double sum = 0d;
					if (prpLlossDto.getDeductible() != 0) {
						sum = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) - prpLlossDto.getDeductible();
					} else {
						sum = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) * (1 - prpLlossDto.getDeductiblerate() / 100);
					}
					prpLlossDto.setSumRealPay(sum >= 0 ? sum : 0);
					prpLlossList.add(prpLlossDto);
				}
			}
			// 受害人从人傷跟蹤里带出
			List<PrpLpersonTrace> prpLpersonTraceList = claimDto.getPrpLpersonTraceList();
			if (!CommonUtils.isEmpty(prpLpersonTraceList)) {
				allPersonLoss = new ArrayList<PrpLpersonLoss>();
				PrpLpersonLoss tempPrpLpersonLoss = null;
				for (PrpLpersonTrace tempPrpLpersonTrace : prpLpersonTraceList) {
					for (Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()) {
						PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
						kindCode = tempPrpLclaimLoss.getKindCode();
						tempPrpCitemKind = itemKindNoMap.get(String.valueOf(tempPrpLclaimLoss.getItemKindNo()));
						if (tempPrpCitemKind == null) {
							tempPrpCitemKind = KindCodeMap.get(kindCode);
						}
						if (!virtualKindMap.containsKey(kindCode) || "66".equals(tempPrpLclaimLoss.getItemCode())) {
							// com.sinosoft.claim.common.ConstantsCollection.limitTypeNameList
							// 有實物標的，或者虛擬標的中指明財產的
							continue;
						}
						tempPrpLpersonLoss = new PrpLpersonLoss();
						tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
						tempPrpLpersonLoss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
						tempPrpLpersonLoss.setAmount(0);
						tempPrpLpersonLoss.setKindCode(kindCode);
						tempPrpLpersonLoss.setKindName(tempPrpCitemKind.getKindName());
						tempPrpLpersonLoss.setPersonName(tempPrpLpersonTrace.getPersonName());
						tempPrpLpersonLoss.setSex(tempPrpLpersonTrace.getPersonSex());
						tempPrpLpersonLoss.setAge(tempPrpLpersonTrace.getPersonAge() != null ? tempPrpLpersonTrace.getPersonAge().intValue() : 0);
						tempPrpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
						tempPrpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
						tempPrpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
						tempPrpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
						tempPrpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
						// 协商赔偿比例默认为100%
						tempPrpLpersonLoss.setClaimRate(100);
						tempPrpLpersonLoss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
						// 赔付合计
						tempPrpLpersonLoss.setDeductible(tempPrpCitemKind.getDeductible());
						tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
						tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
						double sumRealPay = tempPrpLpersonLoss.getSumDefPay() - tempPrpLpersonLoss.getDeductible();
						tempPrpLpersonLoss.setSumRealPay(sumRealPay);
						// identifyNumber =
						// tempPrpLpersonLoss.getPersonName() +"_" +
						// DataUtils.dbNullToEmpty(tempPrpLpersonLoss.getIdentifyNumber());
						personNo = String.valueOf(tempPrpLpersonTrace.getId().getPersonNo());
						if (allPerson.containsKey(personNo)) {
							allPersonLoss = allPerson.get(personNo);
						} else {
							allPersonLoss = new ArrayList<PrpLpersonLoss>();
						}
						allPersonLoss.add(tempPrpLpersonLoss);
						allPerson.put(personNo, allPersonLoss);
					}
				}
			}
		} else {// 有過賠付，屬二次賠付
			Map<String, Double> payMap = new HashMap<String, Double>();// 各險別已賠付
			Map<String, PrpLloss> mapPrpLloss = new HashMap<String, PrpLloss>();
			Map<String, PrpLpersonLoss> mapPrpLpersonLoss = new HashMap<String, PrpLpersonLoss>();
			double realPay = 0d;
			conditions = " ( " + conditions + " ) order by compensateNo asc , serialno asc ";
			// 標的賠付
			List<PrpLloss> tempPrpLlosslist = this.prpLlossService.findByConditions(conditions);
			for (PrpLloss tempPrpLloss : tempPrpLlosslist) {
				kindCode = tempPrpLloss.getKindCode();
				tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, tempPrpLloss.getItemCode(), tempPrpLloss.getItemKindNo());
				if (tempPrpCitemKind != null && tempPrpCitemKind.getKindCode().equals(kindCode)) {
					tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode());
					if (tempPrpLloss.getId().getCompensateNo().charAt(0) == 'C') {
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠付
						realPay = CommonUtils.round(tempPrpLloss.getSumLoss() * tempPrpLloss.getExchRate(), 0);
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLloss.put(tempKey, tempPrpLloss);
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							payMap.put(kindCode, realPay);
						}
						if (payMap.containsKey(tempKey)) {// （ 險別 + 标的 ）已赔付合併
							payMap.put(tempKey, realPay + payMap.get(tempKey));
						} else {
							payMap.put(tempKey, realPay);
						}
					}
				}
			}
			// 受害人賠付
			List<PrpLpersonLoss> tempPrpLpersonLosslist = this.prpLpersonLossService.findByConditions(conditions);
			for (PrpLpersonLoss tempPrpLpersonLoss : tempPrpLpersonLosslist) {
				kindCode = tempPrpLpersonLoss.getKindCode();
				tempPrpCitemKind = itemKindNoMap.get(String.valueOf(tempPrpLpersonLoss.getItemKindNo()));
				if (tempPrpCitemKind == null) {
					tempPrpCitemKind = KindCodeMap.get(kindCode);
				}
				if (tempPrpCitemKind != null && tempPrpCitemKind.getKindCode().equals(kindCode)) {
					if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C') {
						tempKey = tempPrpLpersonLoss.getPersonName() + "_" + tempPrpLpersonLoss.getIdentifyNumber() + "_" + kindCode + "_" + tempPrpLpersonLoss.getLiabDetailCode();
						// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
						mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);
						// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠償
						realPay = CommonUtils.round(tempPrpLpersonLoss.getSumDefPay() * tempPrpLpersonLoss.getExchRate(), 0);
						tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode());
						if (payMap.containsKey(kindCode)) {// 險別已赔付合併
							payMap.put(kindCode, realPay + payMap.get(kindCode));
						} else {
							payMap.put(kindCode, realPay);
						}
						if (payMap.containsKey(tempKey)) {// （ 險別 + 标的 ）已赔付合併
							payMap.put(tempKey, realPay + payMap.get(tempKey));
						} else {
							payMap.put(tempKey, realPay);
						}
					}
				}
			}
			serialNo = 0;
			// 處理保留預估的
			if (!mapPrpLloss.isEmpty()) {
				PrpLloss prpLloss = null;
				for (Map.Entry<String, PrpLloss> entry : mapPrpLloss.entrySet()) {
					prpLloss = entry.getValue();
					if ("Y".equals(prpLloss.getReservedEstimate())) {// 該筆賠付有保留預估
						kindCode = prpLloss.getKindCode();
						tempPrpCitemKind = this.getPrpCitemKind(virtualKindMap, itemKindNoMap, KindCodeMap, kindCode, prpLloss.getItemCode(), prpLloss.getItemKindNo());
						if (tempPrpCitemKind != null && tempPrpCitemKind.getKindCode().equals(kindCode)) {
							tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode());
							Double claimLossValue = 0d;
							Double hasPayValue = 0d;
							if (lossMap.containsKey(tempKey)) {// （險別+标的）有估損
								claimLossValue = lossMap.get(tempKey);// （險別+标的）之預估金額
								hasPayValue = payMap.get(tempKey);// （險別+标的）已賠付金額
							} else {
								claimLossValue = lossMap.get(kindCode);// （險別+标的）之預估金額
								hasPayValue = payMap.get(kindCode);// （險別+标的）已賠付金額
							}
							if (claimLossValue != null) {// 有預估
								if (hasPayValue == null) {
									hasPayValue = 0d;
								}
								Double sumLoss = claimLossValue - hasPayValue;
								if (sumLoss >= 0) {
									prpLloss.getId().setSerialNo(++serialNo);
									prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									prpLloss.setExchRate(1D);
									prpLloss.setSumLoss(sumLoss);
									prpLloss.setSumDefPay(sumLoss);
									prpLloss.setExchRate(1D);
									// 保留預估二次賠付，不需要再扣除殘值、自負額
									prpLloss.setSumRest(0d);
									prpLloss.setDeductible(0d);
									prpLloss.setDeductiblerate(0d);
									double sum = 0d;
									if (prpLloss.getDeductible() != 0) {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
									} else {
										sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
									}
									prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
									prpLloss.setPayObjectSerialNo("");
									prpLlossList.add(prpLloss);
								}
							}
						}
					}
				}
			}
			// 保留處理預估的人傷
			if (!mapPrpLpersonLoss.isEmpty()) {
				PrpLpersonLoss personLoss = null;
				String identifyNumber = null;
				for (Map.Entry<String, PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()) {
					personLoss = entry.getValue();
					if ("Y".equals(personLoss.getReservedEstimate())) {// 該筆賠付有保留預估
						kindCode = personLoss.getKindCode();
						tempPrpCitemKind = itemKindNoMap.get(String.valueOf(personLoss.getItemKindNo()));
						if (tempPrpCitemKind == null) {
							tempPrpCitemKind = KindCodeMap.get(kindCode);
						}
						tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpCitemKind.getItemCode());
						Double claimLossValue = 0d;
						Double hasPayValue = 0d;
						if (lossMap.containsKey(tempKey)) {// （險別+标的）有估損
							claimLossValue = lossMap.get(tempKey);// （險別+标的）之預估金額
							hasPayValue = payMap.get(tempKey);// （險別+标的）已賠付金額
						} else {
							claimLossValue = lossMap.get(kindCode);// （險別+标的）之預估金額
							hasPayValue = payMap.get(kindCode);// （險別+标的）已賠付金額
						}
						if (claimLossValue != null) {// 有預估
							if (hasPayValue == null) {
								hasPayValue = 0d;
							}
							Double sumLoss = claimLossValue - hasPayValue;
							if (sumLoss >= 0) {// 有預估，有保留且，保留預估 大於 0
								personLoss.setSumLoss(sumLoss);
								personLoss.setSumDefPay(sumLoss);
								// 保留預估帶出的幣別設置為本位幣，NTD
								personLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setExchRate(1D);
								personLoss.setDeductible(0d);// 保留預估賠付不需要再次扣除自負額，除非自行錄入
								personLoss.setSumRealPay(sumLoss);
								personLoss.setPayObjectSerialNo("");
								personLoss.getId().setCompensateNo("");
								identifyNumber = personLoss.getPersonName() + "_" + DataUtils.dbNullToEmpty(personLoss.getIdentifyNumber());
								if (tempNo.containsKey(identifyNumber)) {// 身份證號碼代表同一個受害人
									allPersonLoss = allPerson.get(tempNo.get(identifyNumber));
									personNo = tempNo.get(identifyNumber);
								} else {
									allPersonLoss = new ArrayList<PrpLpersonLoss>();
									personNo = String.valueOf(allPerson.size() + 1);
									tempNo.put(identifyNumber, personNo);
								}
								allPersonLoss.add(personLoss);
								allPerson.put(personNo, allPersonLoss);
							}
						}
					}
				}
			}
		}
		compensateDto.setPrpLlossList(prpLlossList);
		List<PrpLpersonLoss> last = new ArrayList<PrpLpersonLoss>();
		serialNo = 0;
		for (Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()) {
			personNo = entry.getKey();
			allPersonLoss = entry.getValue();
			// 整理每個受害人序號
			for (PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss) {
				tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
				tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
				last.add(tempPrpLpersonLoss);
			}
		}
		compensateDto.setPrpLpersonLossList(last);
	}

	/***
	 * 水險理算帶出
	 * @param request
	 * @param compensateDto
	 * @param claimDto
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void generateShip(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String conditions = "  ClaimNo = '" + prpLclaim.getClaimNo() + "' and compensateNo like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		//
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for (PrpLcompensate prpLcompensate : compensateList) {
				compeNo = prpLcompensate.getCompensateNo();
				if (conditions.length() > 0) {
					conditions += " or ";
				}
				conditions += "compensateNo = '" + compeNo + "'";
			}
		}
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		List<PrpCitemKind> tempPrpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null , null);
		Map<String, PrpCitemKind> itemKindNoMap = new HashMap<String, PrpCitemKind>();
		if (tempPrpCitemKindList != null && !tempPrpCitemKindList.isEmpty()) {
			for (PrpCitemKind p : tempPrpCitemKindList) {
				itemKindNoMap.put(String.valueOf(p.getId().getItemKindNo()), p);
			}
		}
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		String tempKey = null;
		PrpCitemKind tempPrpCitemKind = null;
		Map<String, Double> lossMap = new HashMap<String, Double>();// 險別 + 标的
																	// 估損金額
		Map<String, PrpLclaimLoss> claimLossMap = new HashMap<String, PrpLclaimLoss>();
		String kindCode = null;
		if (claimLoss != null && !claimLoss.isEmpty()) {// 存在估損
			for (PrpLclaimLoss loss : claimLoss) {
				if ("P".equals(loss.getLossFeeType())) {
					kindCode = loss.getKindCode();
					if (lossMap.containsKey(kindCode)) {// 險別估損合併
						lossMap.put(kindCode, loss.getSumClaim() + lossMap.get(kindCode));
						// 加總同險別同賠付類型的預估金額
						PrpLclaimLoss temp = claimLossMap.get(kindCode);
						temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
					} else {
						lossMap.put(kindCode, loss.getSumClaim());
						claimLossMap.put(kindCode, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
					}
				}
			}
		}
		List<PrpLloss> prpLlossList = new ArrayList<PrpLloss>();
		PrpLloss prpLlossDto = null;
		int serialNo = 0;
		Map<String, List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String, List<PrpLpersonLoss>>();
		List<PrpLpersonLoss> allPersonLoss = null;
		Map<String, String> tempNo = new HashMap<String, String>();// 存新序人傷序號
		String personNo = null;
		if (CommonUtils.isEmpty(conditions)) {// 有過賠付，屬二次賠付
			String currency = null;
			if (!claimLossMap.isEmpty()) {
				PrpLclaimLoss tempPrpLclaimLoss = null;
				for (Map.Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()) {
					tempPrpLclaimLoss = entry.getValue();
					kindCode = tempPrpLclaimLoss.getKindCode();
					tempPrpCitemKind = itemKindNoMap.get(String.valueOf(tempPrpLclaimLoss.getItemKindNo()));
					if (tempPrpCitemKind != null) {
						prpLlossDto = new PrpLloss();
						prpLlossDto.getId().setSerialNo(++serialNo);
						prpLlossDto.setDangerNo(tempPrpLclaimLoss.getDangerNo());
						prpLlossDto.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
						prpLlossDto.setAmount(tempPrpCitemKind.getAmount());
						prpLlossDto.setPolicyNo(tempPrpCitemKind.getId().getPolicyNo());
						prpLlossDto.setRiskCode(tempPrpCitemKind.getRiskCode());
						prpLlossDto.setKindCode(tempPrpCitemKind.getKindCode());
						prpLlossDto.setKindName(tempPrpCitemKind.getKindName());
						String itemCode = codeService.getItemCode(tempPrpCitemKind);
						String itemName = codeService.getItemName(tempPrpCitemKind);
						prpLlossDto.setItemCode(itemCode);
						prpLlossDto.setLossName(itemName);
						prpLlossDto.setUnitPrice(tempPrpCitemKind.getUnitAmount() == null ? 0 : tempPrpCitemKind.getUnitAmount());
						prpLlossDto.setLossQuantity(tempPrpCitemKind.getQuantity() == null ? 0 : tempPrpCitemKind.getQuantity());
						prpLlossDto.setItemValue(tempPrpCitemKind.getValue());
						prpLlossDto.setDeductiblerate(tempPrpCitemKind.getDeductibleRate());
						prpLlossDto.setDeductible(tempPrpCitemKind.getDeductible());
						currency = tempPrpLclaimLoss.getCurrency();
						prpLlossDto.setCurrency(currency);
						prpLlossDto.setCurrency1(currency);
						prpLlossDto.setCurrency2(currency);
						prpLlossDto.setCurrency3(currency);
						prpLlossDto.setCurrency4(currency);
						prpLlossDto.setSumLoss(tempPrpLclaimLoss.getSumClaim());
						prpLlossDto.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
						prpLlossDto.setSumRest(0);
						prpLlossDto.setIndemnityDutyRate(prpLclaim.getIndemnityDutyRate());
						prpLlossDto.setArrangeRate(100);
						prpLlossDto.setClaimRate(100);
						prpLlossDto.setDutyDeductibleRate(0d);// 事故责任免赔率
						if (tempPrpCitemKind.getFlag() != null && tempPrpCitemKind.getFlag().length() > 4) {
							prpLlossDto.setFlag(tempPrpCitemKind.getFlag().substring(4, 5).trim());
						} else {
							prpLlossDto.setFlag("0");
						}
						prpLlossDto.setExceptDeductiblePay(0);
						prpLlossDto.setExceptDeductibleRate(0);
						double sum = 0d;
						if (prpLlossDto.getDeductible() != 0) {
							sum = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) - prpLlossDto.getDeductible();
						} else {
							sum = (prpLlossDto.getSumLoss() - prpLlossDto.getSumRest()) * (prpLlossDto.getClaimRate() / 100) * (1 - prpLlossDto.getDeductiblerate() / 100);
						}
						prpLlossDto.setSumRealPay(sum >= 0 ? sum : 0);
						prpLlossList.add(prpLlossDto);
					}
				}
			}
			// 估損但沒有賠付的險別，先行帶出
			if (!claimLossMap.isEmpty()) {
				allPersonLoss = new ArrayList<PrpLpersonLoss>();
				PrpLpersonLoss tempPrpLpersonLoss = null;
				if (allPerson.isEmpty()) {
					// 受害人从人傷跟蹤里带出
					List<PrpLpersonTrace> prpLpersonTraceList = claimDto.getPrpLpersonTraceList();
					if (!CommonUtils.isEmpty(prpLpersonTraceList)) {
						for (PrpLpersonTrace tempPrpLpersonTrace : prpLpersonTraceList) {
							for (Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()) {
								PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
								kindCode = tempPrpLclaimLoss.getKindCode();
								tempPrpCitemKind = itemKindNoMap.get(String.valueOf(tempPrpLclaimLoss.getItemKindNo()));
								// 不為0，且每一個人體傷或死亡 \每一事故體傷或死亡之保額不為0，則該險別可以賠付人傷
								if (tempPrpCitemKind != null && (tempPrpCitemKind.getPerHumanInjury() > 0 || tempPrpCitemKind.getPerHumanDeath() > 0)) {
									tempPrpLpersonLoss = new PrpLpersonLoss();
									tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
									tempPrpLpersonLoss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
									tempPrpLpersonLoss.setAmount(0);
									tempPrpLpersonLoss.setKindCode(kindCode);
									tempPrpLpersonLoss.setKindName(tempPrpCitemKind.getKindName());
									tempPrpLpersonLoss.setPersonName(tempPrpLpersonTrace.getPersonName());
									tempPrpLpersonLoss.setSex(tempPrpLpersonTrace.getPersonSex());
									tempPrpLpersonLoss.setAge(tempPrpLpersonTrace.getPersonAge() != null ? tempPrpLpersonTrace.getPersonAge().intValue() : 0);
									tempPrpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
									tempPrpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
									// 协商赔偿比例默认为100%
									tempPrpLpersonLoss.setClaimRate(100);
									tempPrpLpersonLoss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
									// 赔付合计
									tempPrpLpersonLoss.setDeductible(tempPrpCitemKind.getDeductible());
									tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
									tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
									double sumRealPay = tempPrpLpersonLoss.getSumDefPay() - tempPrpLpersonLoss.getDeductible();
									tempPrpLpersonLoss.setSumRealPay(sumRealPay);
									personNo = String.valueOf(tempPrpLpersonTrace.getId().getPersonNo());
									if (allPerson.containsKey(personNo)) {
										allPersonLoss = allPerson.get(personNo);
									} else {
										allPersonLoss = new ArrayList<PrpLpersonLoss>();
									}
									allPersonLoss.add(tempPrpLpersonLoss);
									allPerson.put(personNo, allPersonLoss);
								}
							}
						}
					}
				}
			}
		} else {
			Map<String, Double> payMap = new HashMap<String, Double>();// 各險別已賠付
			// 統計標的賠付
			Map<String, PrpLloss> mapPrpLloss = new HashMap<String, PrpLloss>();
			Map<String, PrpLpersonLoss> mapPrpLpersonLoss = new HashMap<String, PrpLpersonLoss>();
			double realPay = 0d;
			conditions = " ( " + conditions + " ) order by compensateNo asc , serialno asc ";
			// 標的賠付
			List<PrpLloss> tempPrpLlosslist = this.prpLlossService.findByConditions(conditions);
			for (PrpLloss tempPrpLloss : tempPrpLlosslist) {
				kindCode = tempPrpLloss.getKindCode();
				claimLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
				tempKey = kindCode + "_" + DataUtils.dbNullToEmpty(tempPrpLloss.getItemCode());
				if (tempPrpLloss.getId().getCompensateNo().charAt(0) == 'C') {
					// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠付
					realPay = CommonUtils.round(tempPrpLloss.getSumLoss() * tempPrpLloss.getExchRate(), 0);
					// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
					mapPrpLloss.put(tempKey, tempPrpLloss);
					if (payMap.containsKey(kindCode)) {// 險別已赔付合併
						payMap.put(kindCode, realPay + payMap.get(kindCode));
					} else {
						payMap.put(kindCode, realPay);
					}
				}
			}
			// 受害人賠付
			List<PrpLpersonLoss> tempPrpLpersonLosslist = this.prpLpersonLossService.findByConditions(conditions);
			for (PrpLpersonLoss tempPrpLpersonLoss : tempPrpLpersonLosslist) {
				kindCode = tempPrpLpersonLoss.getKindCode();
				claimLossMap.remove(kindCode);// 險別已有過賠付了，不再帶出估損
				if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C') {
					tempKey = tempPrpLpersonLoss.getPersonName() + "_" + tempPrpLpersonLoss.getIdentifyNumber() + "_" + kindCode + "_" + tempPrpLpersonLoss.getLiabDetailCode();
					// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
					mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);
					// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠償
					realPay = CommonUtils.round(tempPrpLpersonLoss.getSumDefPay() * tempPrpLpersonLoss.getExchRate(), 0);
					if (payMap.containsKey(kindCode)) {// 險別已赔付合併
						payMap.put(kindCode, realPay + payMap.get(kindCode));
					} else {
						payMap.put(kindCode, realPay);
					}
				}
			}
			serialNo = 0;
			// 處理保留預估的物損賠付
			if (!mapPrpLloss.isEmpty()) {
				PrpLloss prpLloss = null;
				for (Map.Entry<String, PrpLloss> entry : mapPrpLloss.entrySet()) {
					prpLloss = entry.getValue();
					if ("Y".equals(prpLloss.getReservedEstimate())) {// 該筆賠付有保留預估
						kindCode = prpLloss.getKindCode();
						Double claimLossValue = lossMap.get(kindCode);// （險別）之預估金額
						Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
						if (claimLossValue != null) {// 有預估
							if (hasPayValue == null) {
								hasPayValue = 0d;
							}
							Double sumLoss = claimLossValue - hasPayValue;
							if (sumLoss >= 0) {
								prpLloss.getId().setSerialNo(++serialNo);
								prpLloss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
								prpLloss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
								prpLloss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
								prpLloss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
								prpLloss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
								prpLloss.setExchRate(1D);
								prpLloss.setSumLoss(sumLoss);
								prpLloss.setSumDefPay(sumLoss);
								prpLloss.setExchRate(1D);
								// 保留預估二次賠付，不需要再扣除殘值、自負額
								prpLloss.setSumRest(0d);
								prpLloss.setDeductible(0d);
								prpLloss.setDeductiblerate(0d);
								double sum = 0d;
								if (prpLloss.getDeductible() != 0) {
									sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) - prpLloss.getDeductible();
								} else {
									sum = (prpLloss.getSumLoss() - prpLloss.getSumRest()) * (prpLloss.getClaimRate() / 100) * (1 - prpLloss.getDeductiblerate() / 100);
								}
								prpLloss.setSumRealPay(sum >= 0 ? sum : 0);
								prpLloss.setPayObjectSerialNo("");
								prpLlossList.add(prpLloss);
							}
						}
					}
				}
			}
			// 保留處理預估的人傷
			if (!mapPrpLpersonLoss.isEmpty()) {
				PrpLpersonLoss personLoss = null;
				String identifyNumber = null;
				for (Map.Entry<String, PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()) {
					personLoss = entry.getValue();
					if ("Y".equals(personLoss.getReservedEstimate())) {// 該筆賠付有保留預估
						kindCode = personLoss.getKindCode();
						Double claimLossValue = lossMap.get(kindCode);// （險別）之預估金額
						Double hasPayValue = payMap.get(kindCode);// （險別）已賠付金額
						if (claimLossValue != null) {// 有預估
							if (hasPayValue == null) {
								hasPayValue = 0d;
							}
							Double sumLoss = claimLossValue - hasPayValue;
							if (sumLoss >= 0) {// 有預估，有保留且，保留預估 大於 0
								personLoss.setSumLoss(sumLoss);
								personLoss.setSumDefPay(sumLoss);
								// 保留預估帶出的幣別設置為本位幣，NTD
								personLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setExchRate(1D);
								personLoss.setDeductible(0d);// 保留預估賠付不需要再次扣除自負額，除非自行錄入
								personLoss.setSumRealPay(sumLoss);
								personLoss.setPayObjectSerialNo("");
								personLoss.getId().setCompensateNo("");
								identifyNumber = personLoss.getPersonName() + "_" + DataUtils.dbNullToEmpty(personLoss.getIdentifyNumber());
								if (tempNo.containsKey(identifyNumber)) {// 身份證號碼代表同一個受害人
									allPersonLoss = allPerson.get(tempNo.get(identifyNumber));
									personNo = tempNo.get(identifyNumber);
								} else {
									allPersonLoss = new ArrayList<PrpLpersonLoss>();
									personNo = String.valueOf(allPerson.size() + 1);
									tempNo.put(identifyNumber, personNo);
								}
								allPersonLoss.add(personLoss);
								allPerson.put(personNo, allPersonLoss);
							}
						}
					}
				}
			}
		}
		compensateDto.setPrpLlossList(prpLlossList);
		List<PrpLpersonLoss> last = new ArrayList<PrpLpersonLoss>();
		serialNo = 0;
		for (Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()) {
			personNo = entry.getKey();
			allPersonLoss = entry.getValue();
			// 整理每個受害人序號
			for (PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss) {
				tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
				tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
				last.add(tempPrpLpersonLoss);
			}
		}
		compensateDto.setPrpLpersonLossList(last);
	}

	/***
	 * 傷害險理算帶出
	 * @param request
	 * @param compensateDto
	 * @param claimDto
	 * @throws Exception
	 */
	private void generateAcci(HttpServletRequest request, CompensateDto compensateDto, ClaimDto claimDto) throws Exception {
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String conditions = "  ClaimNo = '" + prpLclaim.getClaimNo() + "' and compensateNo like 'C%' and (UnderWriteFlag = '1' or UnderWriteFlag = '3') and mutualCompensateNo is null order by inputDate desc";
		//
		List<PrpLcompensate> compensateList = this.getCompensateService().findByConditions(conditions);
		conditions = "";
		if (!CommonUtils.isEmpty(compensateList)) {
			String compeNo = null;
			for (PrpLcompensate prpLcompensate : compensateList) {
				compeNo = prpLcompensate.getCompensateNo();
				if (conditions.length() > 0) {
					conditions += " or ";
				}
				conditions += "compensateNo = '" + compeNo + "'";
			}
		}
		String kindCode = null;
		List<PrpLclaimLoss> claimLoss = claimDto.getPrpLclaimLossList();
		String tempKey = null;
		Map<String, Double> lossMap = new HashMap<String, Double>();// 險別估損金額
		Map<String, PrpLclaimLoss> claimLossMap = new HashMap<String, PrpLclaimLoss>();
		if (claimLoss != null && !claimLoss.isEmpty()) {// 存在估損
			for (PrpLclaimLoss loss : claimLoss) {
				if ("P".equals(loss.getLossFeeType())) {
					tempKey = loss.getKindCode();
					if (lossMap.containsKey(tempKey)) {// 險別估損合併
						lossMap.put(tempKey, loss.getSumClaim() + lossMap.get(tempKey));
						// 加總同險別同賠付類型的預估金額
						PrpLclaimLoss temp = claimLossMap.get(tempKey);
						temp.setSumClaim(temp.getSumClaim() + loss.getSumClaim());
					} else {
						lossMap.put(tempKey, loss.getSumClaim());
						claimLossMap.put(tempKey, (PrpLclaimLoss) BeanUtils.cloneBean(loss));
					}
				}
			}
		}
		Map<String, List<PrpLpersonLoss>> allPerson = new LinkedHashMap<String, List<PrpLpersonLoss>>();
		List<PrpLpersonLoss> allPersonLoss = null;
		Map<String, String> tempNo = new HashMap<String, String>();// 存新序人傷序號
		String personNo = null;
		int serialNo = 0;
		Map<String, Double> payMap = new HashMap<String, Double>();// 已賠付人傷賠付各險別已賠付
		if (CommonUtils.isEmpty(conditions)) {
			// 估損但沒有賠付的險別，先行帶出，受害人為本次備案之被保險人
			if (!claimLossMap.isEmpty()) {
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				String insuredCode = prpLclaim.getInsuredCode();
				String insuredName = prpLclaim.getInsuredName();
				List<PrpCinsured> tempPrpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(tempPrpCinsuredList, insuredCode, insuredName);
				int familyNo = prpCinsured.getId().getSerialNo();
				List<PrpCitemKind> tempPrpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, familyNo);
				Map<String, PrpCitemKind> kindMap = new HashMap<String, PrpCitemKind>();
				Map<String, PrpCitemKind> kindCodeMap = new HashMap<String, PrpCitemKind>();
				if (tempPrpCitemKindList != null && !tempPrpCitemKindList.isEmpty()) {
					for (PrpCitemKind p : tempPrpCitemKindList) {
						kindMap.put(String.valueOf(p.getId().getItemKindNo()), p);
						kindCodeMap.put(p.getKindCode(), p);
					}
				}
				int[] serialNos = { familyNo };
				List<PrpCinsuredNature> prpCinsuredNatureList = this.endorseViewHelper.findPrpCinsuredNatureFromCopy(policyNo, damageDate, damageHour, serialNos);
				PrpCinsuredNature prpCinsuredNature = this.endorseViewHelper.getPrpCinsuredNature(prpCinsuredNatureList, familyNo);
				allPersonLoss = new ArrayList<PrpLpersonLoss>();
				PrpLpersonLoss tempPrpLpersonLoss = null;
				PrpCitemKind tempPrpCitemKind = null;
				String identifyNumber = null;
				for (Entry<String, PrpLclaimLoss> entry : claimLossMap.entrySet()) {
					PrpLclaimLoss tempPrpLclaimLoss = entry.getValue();
					kindCode = tempPrpLclaimLoss.getKindCode();
					tempPrpCitemKind = kindMap.get(String.valueOf(tempPrpLclaimLoss.getItemKindNo()));
					if(tempPrpCitemKind == null ){
						tempPrpCitemKind = kindCodeMap.get(kindCode);
					}
					// 获得事故责任免赔率
					tempPrpLpersonLoss = new PrpLpersonLoss();
					tempPrpLpersonLoss.setDangerNo(tempPrpLclaimLoss.getDangerNo());
					tempPrpLpersonLoss.setRiskCode(tempPrpLclaimLoss.getRiskCode());
					tempPrpLpersonLoss.setItemKindNo(tempPrpCitemKind.getId().getItemKindNo());
					tempPrpLpersonLoss.setAmount(tempPrpCitemKind.getAmount());
					tempPrpLpersonLoss.setFamilyNo(tempPrpCitemKind.getFamilyNo());
					tempPrpLpersonLoss.setFamilyName(tempPrpCitemKind.getFamilyName());
					tempPrpLpersonLoss.setKindCode(kindCode);
					tempPrpLpersonLoss.setKindName(tempPrpCitemKind.getKindName());
					PrpLclause prpLclause = prpLclauseService.findPrpLclause(kindCode);
					if (prpLclause != null && !CommonUtils.isEmpty(prpLclause.getRange2())) {
						tempPrpLpersonLoss.setContractingScope(prpLclause.getRange2());// 承保范围设定
					}
					if (prpCinsured != null) {
						tempPrpLpersonLoss.setPersonName(prpCinsured.getInsuredName());
						tempPrpLpersonLoss.setIdentifyNumber(prpCinsured.getIdentifyNumber());
					}
					if (prpCinsuredNature != null) {
						tempPrpLpersonLoss.setSex(prpCinsuredNature.getSex());
						tempPrpLpersonLoss.setAge(prpCinsuredNature.getAge() != null ? prpCinsuredNature.getAge().intValue() : 0);
						tempPrpLpersonLoss.setBirthday(prpCinsuredNature.getBirthday());
					}
					tempPrpLpersonLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
					tempPrpLpersonLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
					tempPrpLpersonLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
					tempPrpLpersonLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
					tempPrpLpersonLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
					// 协商赔偿比例默认为100%
					tempPrpLpersonLoss.setDutyDeductibleRate(0);
					// 赔付合计
					tempPrpLpersonLoss.setSumRealPay1(0);
					tempPrpLpersonLoss.setUnitAmount(tempPrpCitemKind.getUnitAmount() == null ? 0d : tempPrpCitemKind.getUnitAmount());
					tempPrpLpersonLoss.setLossQuantity(0);
					tempPrpLpersonLoss.setSumLoss(tempPrpLclaimLoss.getSumClaim());
					tempPrpLpersonLoss.setSumDefPay(tempPrpLclaimLoss.getSumClaim());
					tempPrpLpersonLoss.setClaimRate(0);
					tempPrpLpersonLoss.setSumRealPay(tempPrpLclaimLoss.getSumClaim());
					identifyNumber = DataUtils.dbNullToEmpty(tempPrpLpersonLoss.getIdentifyNumber());
					if (tempNo.containsKey(identifyNumber)) {// 身份證號碼代表同一個受害人
						allPersonLoss = allPerson.get(tempNo.get(identifyNumber));
						personNo = tempNo.get(identifyNumber);
					} else {
						allPersonLoss = new ArrayList<PrpLpersonLoss>();
						personNo = String.valueOf(allPerson.size() + 1);
						tempNo.put(identifyNumber, personNo);
						List<PrpLpersonHospital> tempPrpLpersonHospitalList = new ArrayList<PrpLpersonHospital>();
						PrpLpersonHospital prpLpersonHospital = new PrpLpersonHospital();
						prpLpersonHospital.getId().setSerialNo(1);
						prpLpersonHospital.setPersonNo(allPerson.size() + 1);
						tempPrpLpersonHospitalList.add(prpLpersonHospital);
						tempPrpLpersonLoss.setPrpLpersonHospitalList(tempPrpLpersonHospitalList);
					}
					allPersonLoss.add(tempPrpLpersonLoss);
					allPerson.put(personNo, allPersonLoss);
				}
			}
		} else {// 有過賠付，屬二次賠付
			// 統計人傷賠付
			Map<String, PrpLpersonLoss> mapPrpLpersonLoss = new HashMap<String, PrpLpersonLoss>();
			double realPay = 0d;
			conditions = " ( " + conditions + " ) order by compensateNo asc , serialno asc ";
			// 已賠付的被保險人訊息
			List<PrpLpersonLoss> tempPrpLpersonLosslist = this.prpLpersonLossService.findByConditions(conditions);
			tempKey = null;
			for (PrpLpersonLoss tempPrpLpersonLoss : tempPrpLpersonLosslist) {
				kindCode = tempPrpLpersonLoss.getKindCode();
				if (tempPrpLpersonLoss.getId().getCompensateNo().charAt(0) == 'C') {
					tempKey = tempPrpLpersonLoss.getIdentifyNumber() + "_" + tempPrpLpersonLoss.getKindCode() + "_" + tempPrpLpersonLoss.getPaymentType() + "_" + tempPrpLpersonLoss.getPaymentType1() + "_" + tempPrpLpersonLoss.getPaymentType2();
					// 統計賠款，同险别、同给付类别、給付內容最后的赔付结果，篩選最後設置保留預估的賠付訊息
					mapPrpLpersonLoss.put(tempKey, tempPrpLpersonLoss);
				}
				// 賠付是在估損基礎上進行的，所以這裡要計多少估損參與賠償
				realPay = CommonUtils.round(tempPrpLpersonLoss.getSumDefPay() * tempPrpLpersonLoss.getExchRate(), 0);
				if (payMap.containsKey(kindCode)) {// 險別已赔付合併
					payMap.put(kindCode, realPay + payMap.get(kindCode));
				} else {
					payMap.put(kindCode, realPay);
				}
			}
			// 保留處理預估的人傷
			serialNo = 0;
			if (!mapPrpLpersonLoss.isEmpty()) {
				PrpLpersonLoss personLoss = null;
				String identifyNumber = null;
				for (Map.Entry<String, PrpLpersonLoss> entry : mapPrpLpersonLoss.entrySet()) {
					personLoss = entry.getValue();
					if ("Y".equals(personLoss.getReservedEstimate())) {// 該筆賠付有保留預估
						personLoss.setSumLoss(0d);
						Double claimLossValue = lossMap.get(personLoss.getKindCode());// 預估金額
						Double hasPayValue = payMap.get(personLoss.getKindCode());
						if (claimLossValue != null) {// 有預估
							if (hasPayValue == null) {
								hasPayValue = 0d;
							}
							Double sumLoss = claimLossValue - hasPayValue;
							if (sumLoss >= 0) {// 有預估，有保留且，保留預估 大於 0
								personLoss.setSumLoss(sumLoss);
								personLoss.setSumDefPay(sumLoss);
								// 保留預估帶出的幣別設置為本位幣，NTD
								personLoss.setCurrency(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency1(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency2(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency3(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setCurrency4(ConstantCodes.LOCAL_CURRENCY);
								personLoss.setExchRate(1D);
								personLoss.setSumRealPay(sumLoss);
								personLoss.setPayObjectSerialNo("");
								personNo = null;
								identifyNumber = DataUtils.dbNullToEmpty(personLoss.getIdentifyNumber());
								if (tempNo.containsKey(identifyNumber)) {// 身份證號碼代表同一個受害人
									allPersonLoss = allPerson.get(tempNo.get(identifyNumber));
									personNo = tempNo.get(identifyNumber);
								} else {
									allPersonLoss = new ArrayList<PrpLpersonLoss>();
									personNo = String.valueOf(allPerson.size() + 1);
									tempNo.put(identifyNumber, personNo);
									String sql = " compensateNo = '" + personLoss.getId().getCompensateNo() + "' and personNo = " + personLoss.getPersonNo() + " order by serialNo asc ";
									personLoss.setPrpLpersonHospitalList(this.prpLpersonHospitalService.findPrpLpersonHospital(QueryRule.getInstance().addSql(sql)));
								}
								allPersonLoss.add(personLoss);
								allPerson.put(personNo, allPersonLoss);
							}
						}
					}
				}
			}
		}
		List<PrpLpersonLoss> last = new ArrayList<PrpLpersonLoss>();
		serialNo = 0;
		double maxPaid = 0d;
		for (Double d : lossMap.values()) {
			maxPaid += d;
		}
		double hisPaid = 0d;
		for (Double d : payMap.values()) {
			hisPaid += d;
		}
		for (Entry<String, List<PrpLpersonLoss>> entry : allPerson.entrySet()) {
			personNo = entry.getKey();
			allPersonLoss = entry.getValue();
			// 整理每個受害人序號
			for (PrpLpersonLoss tempPrpLpersonLoss : allPersonLoss) {
				tempPrpLpersonLoss.setPersonNo(Integer.parseInt(personNo));
				tempPrpLpersonLoss.getId().setSerialNo(++serialNo);
				tempPrpLpersonLoss.setMaxpaid(maxPaid);
				tempPrpLpersonLoss.setHispaid(hisPaid);
				last.add(tempPrpLpersonLoss);
			}
		}
		compensateDto.setPrpLpersonLossList(last);
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public PrpLpersonHospitalService getPrpLpersonHospitalService() {
		return prpLpersonHospitalService;
	}

	public void setPrpLpersonHospitalService(PrpLpersonHospitalService prpLpersonHospitalService) {
		this.prpLpersonHospitalService = prpLpersonHospitalService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpLclauseService getPrpLclauseService() {
		return prpLclauseService;
	}

	public void setPrpLclauseService(PrpLclauseService prpLclauseService) {
		this.prpLclauseService = prpLclauseService;
	}

}
