package com.sinosoft.app.common.web;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.app.common.vo.CodeCondition;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDcarModelService;
import com.sinosoft.claim.common.service.facade.PrpDclauseKindService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpLagentService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.util.UIDeductCondAction;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpDcarModel;
import com.sinosoft.claim.schema.model.PrpDclauseKind;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLagent;
import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyImgService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLdisabilityLimitService;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;
import com.sinosoft.claimciplatform.bl.facade.BLAdvanceFacade;
import com.sinosoft.claimciplatform.dto.custom.AdvanceConfirm;
import com.sinosoft.claimciplatform.dto.custom.AdvanceResponse;
import com.sinosoft.claimciplatform.dto.custom.EndCaseResponse;
import com.sinosoft.claimciplatform.dto.custom.Iconstants;
import com.sinosoft.claimciplatform.dto.custom.ReturnInfo;
import com.sinosoft.function.insutil.bl.facade.BLPubRateFacade;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * class comments write here...
 * @version created date: 2007-1-8 13:19:00
 */
public class DwrInvokeDataAction {
	public static final int MAX_RECORDS = 15;// 代码查询的最大记录数
	private CompanyService companyService;
	private static List<PrpLdeductCond> prpLdeductCondList = new ArrayList<PrpLdeductCond>();
	private RegistService registService;
	private PrpLclaimService prpLclaimService;
	private PrpLcertifyImgService prpLcertifyImgService;
	private EndorseViewHelper endorseViewHelper;
	private PrpClimitService prpClimitService;
	private PrpDclauseKindService prpDclauseKindService;
	private PrpCitemKindService prpCitemKindService;
	private PrpDcodeService prpDcodeService;
	private PrpCmainService prpCmainService;
	private PrpCcoinsService prpCcoinsService;
	private PrpLexternalAgencyService prpLexternalAgencyService;
	private PrpLagentService prpLagentService;
	private PrpDcarModelService prpDcarModelService;
	private PrpDriskService prpDriskService;
	private PolicyService policyService;
	private CodeService codeService;
	private PrpLdisabilityLimitService prpLdisabilityLimitService;
	private PrpCmainCargoService prpCmainCargoService;
	private PrpLclaimStatusService prpLclaimStatusService;
	/**
	 * @author 中科软
	 * @param cond
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findCompanySelect(CodeCondition cond) throws Exception {
		StringBuffer selectCompany = new StringBuffer(500);
		try {
			String query = cond.getQuery();
			String extraCond = cond.getExtraCond();
			if (query == null || "".equals(query.trim())) {
				query = "";
			}
			if (extraCond == null || "".equals(extraCond.trim())) {
				extraCond = "";
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("validStatus", "1");
			queryRule.addSql(" 1=1 and (comCode like '%" + query + "%' or comCName like '%" + query + "%') order by comCode");
			Page page = this.getCompanyService().findCompany(queryRule, 0, 50);
			List<SaaCompany> saaCompanyList = page.getResult();
			selectCompany.append("<select name='selectCompany' class='selct1' " + extraCond + "' multiple ondblclick='forbidIn();'>");
			for (SaaCompany company : saaCompanyList) {
				selectCompany.append("<option value=").append(company.getComCode()).append(" />");
				selectCompany.append(company.getComCode()).append("--").append(company.getComCName());
			}
			selectCompany.append("</select>");
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return selectCompany.toString();
	}

	/**
	 * @author 中科软
	 * @param cond
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String listTaskCompanySelect(CodeCondition cond) throws Exception {
		StringBuffer selectCompany = new StringBuffer(500);
		try {
			String query = cond.getQuery();
			if (query == null || "".equals(query.trim())) {
				query = "";
			}
			String type = cond.getType();
			if (type == null || "".equals(type.trim())) {
				type = "";
			}
			String gradeId = cond.getExtraCond();
			List<SaaCompany> saaCompanyList = this.getCompanyService().listGradePermitCompanyCodes(cond.getUserCode(), query, type, gradeId);
			selectCompany.append("<select name='selectCompany' class='selct1' style='width:450px;' multiple >");
			for (SaaCompany company : saaCompanyList) {
				selectCompany.append("<option value=").append(company.getComCode()).append(" />");
				selectCompany.append(company.getComCode()).append("--").append(company.getComCName());
			}
			selectCompany.append("</select>");
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return selectCompany.toString();
	}

	/**
	 * 判断立案估损金额是否超出限额
	 * @author 中科软
	 * @throws Exception 
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public String checkBeyondAmount(HashMap hashMap) throws Exception {
		try {
			double allKindLoss = Double.parseDouble((String) hashMap.get("allKindLoss"));
			String kindCode = (String) hashMap.get("kindCode");
			String kindName = (String) hashMap.get("kindName");
			String policyNo = (String) hashMap.get("policyNo");
			String riskCode = (String) hashMap.get("riskCode");
			String indemnityDuty = (String) hashMap.get("indemnityDuty");
			String kindLoss = (String) hashMap.get("kindLoss");
			String damageDate = (String) hashMap.get("damageDate");
			String startDate = (String) hashMap.get("startDate");
			String limitType = "";
			double deductible = 0.0;
			String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
			double amount = 0;
			double amountA = 0;
			// 险额校验应读取出险时保单信息
//			PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(policyNo, damageDate, "0");
			if ("RISKCODE_DAZ".equals(configCode)) {// 交强险
				limitType = (String) hashMap.get("limitType");
				if (limitType.equals("C")) {
					limitType = "G";
				}
				List<PrpClimit> limitList = this.getPrpClimitService().findPrpClimit(" policyNo='" + policyNo + "'", damageDate, startDate);
				if (limitList != null && limitList.size() > 0) {
					Iterator<PrpClimit> mapit = limitList.iterator();
					while (mapit.hasNext()) {
						PrpClimit prpClimit = mapit.next();
						// add by chenjie 2013-03-14 start 交强险迁移
						if ("O".equals(limitType) && "95".equals(prpClimit.getId().getLimitType())) {
							amount = prpClimit.getLimitFee();
							break;
						}
						// add by chenjie 2013-03-14 end 交强险迁移
						if ("4".equals(indemnityDuty)) {
							if ("D".equals(limitType) && "93".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}
							if ("M".equals(limitType) && "94".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}
							if ("G".equals(limitType) && "95".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}
						} else {
							if ("M".equals(limitType) && "91".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}
							if ("D".equals(limitType) && "90".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}
							if ("G".equals(limitType) && "92".equals(prpClimit.getId().getLimitType())) {
								amount = prpClimit.getLimitFee();
								break;
							}

						}
					}
				}
			} else {
				List<PrpCitemKind> limitList = this.getEndorseViewHelper().findPrpCitemKind(policyNo, damageDate, "0", riskCode, null);
				Iterator<PrpCitemKind> it = limitList.iterator();
				Map<String, Double> map = new HashMap<String, Double>();
				while (it.hasNext()) {
					PrpCitemKind prpCitemKind = it.next();
					if (ConstantCodes.KINDCODE_D_A.equals(prpCitemKind.getKindCode())) {
						amountA = prpCitemKind.getAmount();
					}
					map.put(prpCitemKind.getKindCode(), new Double(prpCitemKind.getAmount()));
					if (kindCode.equals(prpCitemKind.getKindCode())) {
						amount = prpCitemKind.getAmount();
						if ("M".equals(kindCode) || "Y".equals(kindCode)) {
							break;
						}
						if (new Double(amount).intValue() == 0) {
							String conditions = " RelateKindCode ='" + prpCitemKind.getKindCode() + "' and RiskCode='" + prpCitemKind.getRiskCode() + "' and (clausetype ='F44' OR clausetype ='F45'OR clausetype ='F46')";
							List<PrpDclauseKind> collection = this.getPrpDclauseKindService().findByConditions(QueryRule.getInstance().addSql(conditions));
							Iterator<PrpDclauseKind> it1 = collection.iterator();
							while (it1.hasNext()) {
								PrpDclauseKind prpDclauseKind = (PrpDclauseKind) it1.next();
								if (map.containsKey(prpDclauseKind.getId().getKindCode())) {
									if (amount < (map.get(prpDclauseKind.getId().getKindCode())).doubleValue()) {
										amount = (map.get(prpDclauseKind.getId().getKindCode())).doubleValue();
									}
								}
							}
						}
						break;
					}
				}
				if (ConstantCodes.KINDCODE_D_A.equals(kindCode)) {
					while (it.hasNext()) {
						PrpCitemKind itemKind = (PrpCitemKind) it.next();
						if ("M1".equals(itemKind.getKindCode())) {
							deductible = itemKind.getValue();
							break;
						}
					}
				}
			}
			if ("D".equals(this.getCodeService().translateRiskCodetoRiskType(riskCode)))
				allKindLoss = allKindLoss * (Integer.parseInt(indemnityDuty) / 100);
			if (!("M".equals(kindCode) || "Y".equals(kindCode) || "F".equals(kindCode))) {

//				if (allKindLoss > amount) {
//					return kindName + "估损金额之和超过限额(" + amount + ")元.";
//				} else if (Double.parseDouble(kindLoss) > amount) {
//					if ("RISKCODE_DAZ".equals(configCode) && "O".equals(limitType)) {
//
//					} else {
//						return kindName + "估损金额(" + Double.parseDouble(kindLoss) + ")超过限额(" + amount + ")元";
//					}
//				}
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return "true";
	}

	/**
	 * 立案时计算估损金额
	 * @author 中科软
	 * @throws Exception 
	 * @throws Exception
	 */
	public double getSumClaim(HashMap hashMap) throws Exception {
		double sumClaim = 0.00d;
		try {
			String feeType = (String) hashMap.get("feeType");
			String kindLoss = (String) hashMap.get("kindLoss");
			String riskCode = (String) hashMap.get("riskCode");
			String kindCode = (String) hashMap.get("kindCode");
			String indemnityDutyRate = (String) hashMap.get("indemnityDutyRate");
			String indemnityDuty = (String) hashMap.get("indemnityDuty");
			String registNo = (String) hashMap.get("registNo");
			if ("9".equals(indemnityDuty)) {
				if (Double.parseDouble(indemnityDutyRate) > 100) {
					indemnityDuty = "0";
				} else if (Double.parseDouble(indemnityDutyRate) > 50) {
					indemnityDuty = "1";
				} else if (Double.parseDouble(indemnityDutyRate) > 0) {
					indemnityDuty = "3";
				}
			}
			Double dutydeductRate = new Double(0);
			String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
			dutydeductRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, registNo);
			sumClaim = 0;
			if ("Z".equals(feeType) || "RISKCODE_DAZ".equals(configCode)) {
				sumClaim = Double.parseDouble(kindLoss);
			} else {
				sumClaim = Double.parseDouble(kindLoss) * Double.parseDouble(indemnityDutyRate) / 100 * (1 - Double.parseDouble(dutydeductRate.toString()) / 100);
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return sumClaim;
	}

	/**
	 * 取事故责任免赔率
	 * @author 中科软
	 * @param riskCode 险类
	 * @param kindCode 险别
	 * @param indemnityDuty 责任比例
	 * @param registNo 报案号
	 * @throws Exception
	 */
	private Double getDutydeductRate(String riskCode, String kindCode, String indemnityDuty, String registNo) throws Exception {
		Double dutydeductRate = new Double(0);
		try {
			UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
			Map<String, Double> map = new HashMap<String, Double>();
			PrpCitemCar prpCitemCar = new PrpCitemCar();
			RegistDto registDto = null;
			PolicyDto policyDto = new PolicyDto();
			String clauseType = "";
			String strKindCode = kindCode;
			String validDate = CommonUtils.getYearToDayStr(new Date());
			if ("".equals(registNo) == false) {
				registDto = this.getRegistService().findByPrimaryKey(registNo);
				if (registDto != null) {
					PrpLregist prpLregist = registDto.getPrpLregist();
//					policyDto = this.getEndorseViewHelper().findForEndorBefore(prpLregist.getPolicyNo(), CommonUtils.getYearToDayStr(prpLregist.getDamageStartDate()), prpLregist.getDamageStartHour());
					String policyNo = prpLregist.getPolicyNo();
					String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
					String damageHour = prpLregist.getDamageStartHour();
					PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
					validDate = CommonUtils.getYearToDayStr(prpCmain.getStartDate());
					List<PrpCitemCar> prpCitemCarLis = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
					if (!CommonUtils.isEmpty(prpCitemCarLis)) {
						prpCitemCar = (PrpCitemCar) prpCitemCarLis.get(0);
						if (prpCitemCar != null) {
							clauseType = prpCitemCar.getClauseType();
						}
					}
				}
			}
			map = uiDeductCondAction.getDeductibleRateOfAccident(riskCode, strKindCode, "", clauseType, validDate);
			if (map != null) {
				dutydeductRate = map.get(indemnityDuty);
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return dutydeductRate;
	}

	/**
	 * 立案汇总估损金额
	 * @author 中科软
	 * @param hashMap
	 * @throws Exception 
	 */
	public synchronized double collectClaimLoss(HashMap hashMap) throws Exception {
		double collectClaimLoss = 0.0d;
		try {
			double exchRate = 1; // 兑换率
			String exchCurrency = (String) hashMap.get("exchCurrency"); // 得到目标币别类型
			String nowAmount = (String) hashMap.get("nowAmout"); // 得到估损金额
			String baseCurrency = (String) hashMap.get("baseCurrency");// 原币别
			String[] baseCurrencyValue = baseCurrency.split(",");
			String[] nowAmountArray = nowAmount.split(",");
			for (int i = 1; i < baseCurrencyValue.length; i++) {
				// 调用兑换率的方法，得到当前兑换率
				try {
					exchRate = getExchangeRate(baseCurrencyValue[i], exchCurrency);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if ("".equals(nowAmountArray[i])) {
					nowAmountArray[i] = "0";
				}
				collectClaimLoss = collectClaimLoss + Double.parseDouble(nowAmountArray[i]) * exchRate;
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return collectClaimLoss;
	}

	/**
	 * 得到 币别兑换率
	 * @param baseCurrencyValue
	 * @param exchCurrency
	 * @return
	 * @throws Exception
	 */
	public double getExchangeRate(String baseCurrency, String exchCurrency) throws Exception {
		try {
			double exchangeRate = BLPubRateFacade.getExchangeRate(baseCurrency, exchCurrency, new com.sinosoft.sysframework.common.datatype.DateTime(new Date()));
			return exchangeRate;
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return 0.0d;
	}

	/**
	 * 判断是否购买不计免赔
	 * @author 中科软
	 * @param hashMap 含险别，选择号，保单号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public synchronized PrpCitemKind checkExceptDeductible(HashMap hashMap) throws SQLException, UserException, Exception {
		PrpCitemKind returnPrpCitemKind = new PrpCitemKind();
		try {
			String conditions = "";
			conditions = "policyno = '" + hashMap.get("policyno") + "'";
			List<PrpCitemKind> list = this.getPrpCitemKindService().findPrpCitemKind(QueryRule.getInstance().addSql(conditions));
			Iterator<PrpCitemKind> it = list.iterator();
			while (it.hasNext()) {
				PrpCitemKind prpCitemKind = (PrpCitemKind) it.next();
				if ((hashMap.get("kind")).equals(prpCitemKind.getKindCode()) && "1".equals((prpCitemKind.getFlag()).substring(4, 5))) {
					returnPrpCitemKind = prpCitemKind;
					if (hashMap.get("riskCode") != null && !"".equals((String) hashMap.get("riskCode"))) {
						String riskCode = (String) hashMap.get("riskCode");
						String kindCode = (String) hashMap.get("kind");
						String indemnityDutyRate = (String) hashMap.get("indemnityDutyRate");
						String indemnityDuty = (String) hashMap.get("indemnityDuty");
						String registNo = (String) hashMap.get("registNo");
						if ("9".equals(indemnityDuty)) {
							if (Double.parseDouble(indemnityDutyRate) > 100) {
								indemnityDuty = "0";
							} else if (Double.parseDouble(indemnityDutyRate) > 50) {
								indemnityDuty = "1";
							} else if (Double.parseDouble(indemnityDutyRate) > 0) {
								indemnityDuty = "3";
							}
						}
						Double dutydeductRate = new Double(0);
						dutydeductRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, registNo);
						returnPrpCitemKind.setDeductibleRate(dutydeductRate);
					}
					break;
				}
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return returnPrpCitemKind;
	}

	/**
	 * 立案时计算估损金额的免赔额
	 * @author 中科软
	 * @throws Exception
	 */
	public String getExceptDeductiblePay(HashMap hashMap) throws Exception {
		String kindLoss = (String) hashMap.get("kindLoss");
		String sumKindLoss = (String) hashMap.get("sumKindLoss");
		String riskCode = (String) hashMap.get("riskCode");
		String kindCode = (String) hashMap.get("kindCode");
		String indemnityDutyRate = (String) hashMap.get("indemnityDutyRate");
		String indemnityDuty = (String) hashMap.get("indemnityDuty");
		String registNo = (String) hashMap.get("registNo");
		double loss = 0;
		double sumLoss = 0;
		try {
			if ("9".equals(indemnityDuty)) {
				if (Double.parseDouble(indemnityDutyRate) > 100) {
					indemnityDuty = "0";
				} else if (Double.parseDouble(indemnityDutyRate) > 50) {
					indemnityDuty = "1";
				} else if (Double.parseDouble(indemnityDutyRate) > 0) {
					indemnityDuty = "3";
				}
			}
			Double dutydeductRate = new Double(0);
			dutydeductRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, registNo);
			loss = Double.parseDouble(kindLoss) * Double.parseDouble(indemnityDutyRate) / 100 * (Double.parseDouble(dutydeductRate.toString()) / 100);
			sumLoss = Double.parseDouble(sumKindLoss) * Double.parseDouble(indemnityDutyRate) / 100 * (Double.parseDouble(dutydeductRate.toString()) / 100);
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return sumLoss + "|" + loss;
	}

	/**
	 * 立案计算责任估损金额
	 * @author 中科软
	 */
	public String getDutySum(HashMap hashMap) {
		String indemnityDuty = (String) hashMap.get("indemnityDutyRate");
		String kindLoss = (String) hashMap.get("allKindLoss");
		if (Double.parseDouble(indemnityDuty) > 100 || Double.parseDouble(indemnityDuty) < 0)
			return "error";
		else {
			Double dutySum = new Double(Double.parseDouble(indemnityDuty) / 100 * Double.parseDouble(kindLoss));
			return dutySum.toString();
		}
	}

	public PrpDcode getPrpDcodeDto(HashMap hashMap) throws Exception {
		String codeCode = (String) hashMap.get("codeCode");
		String codeType = (String) hashMap.get("codeType");
		String conditions = " codeCode = '" + codeCode + "'" + "and " + " codeType = '" + codeType + "'";
		PrpDcode prpDcodeDto = new PrpDcode();
		try {
			List<PrpDcode> list = this.getPrpDcodeService().findPrpDcode(QueryRule.getInstance().addSql(conditions));
			if (list != null && list.size() > 0) {
				prpDcodeDto = (PrpDcode) list.get(0);
				return prpDcodeDto;
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpDcodeDto;
	}

	/**
	 * 报案时更新保单主信息
	 * @author 中科软
	 * @param hashMap
	 * @return
	 * @throws Exception
	 */
	public PrpLregist getPrpLregistDto(HashMap hashMap) throws Exception {
		String policyNo = (String) hashMap.get("policyNo");
		String damageDate = (String) hashMap.get("damageDate");
		String damageHour = (String) hashMap.get("damageHour");
		PrpLregist prpLregist = new PrpLregist();
		String strInsuredName = "";
		int insureQuantity = 0;
		int j = 0;
		try {
			// 查询保单信息
//			PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(policyNo, damageDate, damageHour);
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			insureQuantity = prpCmain.getSumQuantity();
			if (String.valueOf(insureQuantity) == null || String.valueOf(insureQuantity).equals("") || insureQuantity <= 1) {
				strInsuredName = prpCmain.getInsuredName();
			} else {
				strInsuredName = prpCmain.getInsuredName() + "等" + insureQuantity + "人";
			}
			// 检查缴费 -1为未缴费，0为未缴全，1为缴全
			String conditions = " policyno = '" + policyNo + "'";
			int intPayFee = this.getPolicyService().checkPay(conditions);
			prpLregist.setPayFlag(String.valueOf(intPayFee));
			prpLregist.setPolicyNo(policyNo);
			prpLregist.setClassCode(prpCmain.getClassCode());
			String riskCode = prpCmain.getRiskCode();
			prpLregist.setRiskCode(riskCode);
			prpLregist.setSumAmount(prpCmain.getSumAmount());
			prpLregist.setSignDate((com.sinosoft.sysframework.common.datatype.DateTime) prpCmain.getSignDate());
			prpLregist.setOthFlag(prpCmain.getOthFlag());
			prpLregist.setUnderWriteEndDate((com.sinosoft.sysframework.common.datatype.DateTime) prpCmain.getUnderwriteEndDate());
			String handler1Code = prpCmain.getHandler1Code();
			prpLregist.setHandler1Code(handler1Code);
			String comCode = prpCmain.getComCode();
			prpLregist.setComCode(comCode);
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			if ("Y".equals(strRiskType)) {
				PrpCmainCargo prpCmainCargo = this.prpCmainCargoService.findPrpCmainCargo(policyNo);
				if (prpCmainCargo != null) {
					prpLregist.setDamageAddress(prpCmainCargo.getEndSiteName());
				}
			}
			prpLregist.setStartDate(prpCmain.getStartDate().toString());
			prpLregist.setStartHour(prpCmain.getStartHour());
			prpLregist.setEndDate(prpCmain.getEndDate().toString());
			prpLregist.setEndHour(prpCmain.getEndHour());
			String estiCurrency = prpCmain.getCurrency();
			prpLregist.setEstiCurrency(estiCurrency);
			prpLregist.setEstiCurrencyName(this.getCodeService().translateCurrencyCode(prpLregist.getEstiCurrency(), true));
			prpLregist.setLanguage(prpCmain.getLanguage());
			prpLregist.setHandlerCode(prpCmain.getHandlerCode());
			prpLregist.setInsuredCode(prpCmain.getInsuredCode());
			prpLregist.setInsuredName(prpCmain.getInsuredName());
			prpLregist.setInsuredNameShow(strInsuredName);
			prpLregist.setInsuredAddress(prpCmain.getInsuredAddress());
			prpLregist.setInputDate(new DateTime(new Date()));
			// 初始化报案日期，出险日期
			prpLregist.setReportDate(new DateTime(new Date()));
			prpLregist.setReportHour(String.valueOf(DateTime.current().getHour()));
			prpLregist.setReportMinute(String.valueOf(DateTime.current().getMinute()));
			strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpCmain.getRiskCode());
			if ("Y".equals(strRiskType)) {
				prpLregist.setDamageStartDate(new Date());
			} else {
				prpLregist.setDamageStartDate(new DateTime(damageDate));
			}
			prpLregist.setDamageStartHour(String.valueOf(DateTime.current().getHour()));
			prpLregist.setDamageStartMinute(String.valueOf(DateTime.current().getMinute()));
			prpLregist.setDamageEndDate(new DateTime(new Date()));
			prpLregist.setDamageEndHour(String.valueOf(DateTime.current().getHour()));
			prpLregist.setLflag("L");
			// (1)归属业务员名称的转换
			String handler1Code2 = prpLregist.getHandler1Code();
			String handler1Name = this.getCodeService().translateUserCode(handler1Code2, true);
			prpLregist.setHandler1Name(handler1Name);
			// (2)归属业务机构的转换
			String comCode2 = prpLregist.getComCode();
			String comName = this.getCodeService().translateComCode(comCode2, true);
			String agentCode = "";
			if (prpCmain != null) {
				agentCode = prpCmain.getAgentCode(); // 代理人代码
			}
			prpLregist.setAgentCode(agentCode);
			prpLregist.setAgentName(this.getCodeService().translateAgentName(agentCode));// 得到代理人名称
			prpLregist.setAcceptFlag("Y");
			prpLregist.setRepeatInsureFlag("N");
			PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId();
			prpLclaimStatusId.setBusinessNo(policyNo);
			prpLclaimStatusId.setNodeType("polic");
			prpLclaimStatusId.setSerialNo(0);
			PrpLclaimStatus prpLclaimStatus = this.prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId);
			if (prpLclaimStatus == null) {
				prpLregist.setStatus("1");
			} else {
				prpLregist.setStatus(prpLclaimStatus.getStatus());
			}
			PrpLregist prpLregistTemp = new PrpLregist();
			PrpLregist prpLregistPre = new PrpLregist();
			// 计算出险次数
			List<PrpLregist> registList = this.getRegistService().findSamePolicyRegist(policyNo);
			int intPerilCount = 0;
			int intRecentCount = 0; // 最近几天的出险次数
			String priorDate = AppConfig.get("sysconst.RegistViewLimitDay");
			DateTime dateTime = new DateTime(new Date());
			int intervalDay = 0;
			String oldRegistNo = "";
			String nowRegistNo = "";
			int rowNo = 0;
			int rowCount = 0;
			if (registList != null) {
				rowCount = registList.size(); // 计算数据的数目
			}
			String curRegistNo = "";
			for (rowNo = 0; rowNo < rowCount; rowNo++) {
				oldRegistNo = nowRegistNo;
				prpLregistPre = prpLregistTemp;
				prpLregistTemp = registList.get(rowNo);
				nowRegistNo = prpLregistTemp.getRegistNo();
				intervalDay = DateTime.intervalDay(new DateTime(prpLregistTemp.getDamageStartDate()), 0, dateTime, 0);
				if (intervalDay <= Integer.parseInt(priorDate)) {
					intRecentCount++;
				}
				if (rowNo == 0 && rowCount != 1 || ((rowNo != (rowCount - 1)) && (rowNo > 0) && (oldRegistNo.equals(nowRegistNo)))) {
					if (oldRegistNo.equals(nowRegistNo)) {
						prpLregistTemp.setBrandName(prpLregistPre.getBrandName() + " " + prpLregistTemp.getBrandName());
					} else {
						intPerilCount++;
					}
					continue;
				}
				if ((rowNo == rowCount - 1) && !curRegistNo.equals(nowRegistNo) && (!oldRegistNo.equals(nowRegistNo))) {
					intPerilCount++;
				} else {
					if (rowCount != 1 && !curRegistNo.equals(nowRegistNo)) {
						if (intervalDay <= Integer.parseInt(priorDate)) {
							intRecentCount++;
						}
						intPerilCount++;
					}
				}
			}
			prpLregist.setPerilCount(intPerilCount);
			prpLregist.setRecentCount(intRecentCount);
			prpLregist.setComName(comName);
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpLregist;
	}

	/**
	 * 更新报案险别信息
	 * @author 中科软 
	 * @param HashMap
	 */
	public List getItemKindList(HashMap hashMap) throws Exception {
		String policyNo = (String) hashMap.get("policyNo");
		String damageDate = (String) hashMap.get("damageDate");
		String damageHour = (String) hashMap.get("damageHour");
		String qsFlag = (String) hashMap.get("qsFlag");
		List<PrpCitemKind> itemKindList = new ArrayList<PrpCitemKind>();
		try {
			itemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
			if (qsFlag != null && "Y".equals(qsFlag)) {
				String mainPolicyNo = (String) hashMap.get("mainPolicyNo");
				List<PrpCitemKind> itemKindList_qs = this.endorseViewHelper.findPrpCitemKind(mainPolicyNo, damageDate, damageHour, null, null);
				PrpCitemKind prpCitemKindDto_qs = (PrpCitemKind) itemKindList_qs.get(0);
				itemKindList.add(prpCitemKindDto_qs);
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return itemKindList;
	}

	/**
	 * 获得免赔条件(绝对免赔率，传参数为界面上选择的免赔条件)
	 * @author 中科软
	 * @param list 界面上选择的免赔条件数组
	 * @return
	 */
	public void getDeductCondList(List<PrpLdeductCond> list) {
//		// 清空原对象内容
//		prpLdeductCondList = new ArrayList<PrpLdeductCond>();
//		// 为什么i从2开始？？对为界面上选择的免赔条件数组进行循环判断,而且整理完了之後，这个还是空的呢。。
//		PrpLdeductCond prpLdeductCond = null;
//		for (int i = 0; i < list.size(); i++) {
//			prpLdeductCond = new PrpLdeductCond();
//			prpLdeductCond.getId().setDeductCondCode(list.get(i).getId().getDeductCondCode());
//			prpLdeductCond.setDeductCondName(list.get(i).getDeductCondName());
//			prpLdeductCond.setTimes(((PrpLdeductCond) list.get(i)).getTimes());
//			prpLdeductCondList.add(prpLdeductCond);
//		}
		// 整理完prpLdeductCondList变量
	}

	/**
	 * 非车理算计算赔款
	 * @param hashMap
	 * @return
	 * @throws Exception
	 */
	public PrpLcompensate getSumRealPay(HashMap hashMap) throws Exception {
		double dblSumDutyPaid = 0; // 责任赔款合计（=（赔款费用附加信息中）计入赔款金额+（赔付标的附加信息中）赔偿金额+（赔付人员附加信息中）赔付合计）
		double dblSumPaid = 0; // 赔款合计（=责任赔款合计+其它费用）
		double dblSumPrePaid = Double.parseDouble((String) hashMap.get("dblSumPrePaid")); // 预赔金额
		double dblSumNoDutyFee = 0; // 其它费用（（赔款费用附加信息中）费用金额 - 计入赔款金额）
		double dblSumThisPaid = 0; // 实赔金额（=责任赔款合计－已预付赔款）
		double personLossSumRealPay = 0;
		// 计算责任赔款合计
		double chargeRealPayValue = 0;
		double chargeAmountValue = 0;
		double exchRate = 1;
		String riskCode = (String) hashMap.get("riskCode");
		String policyNo = (String) hashMap.get("policyNo");
		PrpLcompensate prpLcompensate = new PrpLcompensate();

		try {
			String chargeRealPay = (String) hashMap.get("chargeRealPay");
			String chargeAmount = (String) hashMap.get("chargeAmount");
			String exchCurrency = (String) hashMap.get("exchCurrency"); // 得到目标币别类型
			String baseCurrency = (String) hashMap.get("baseCurrency");// 原币别
			String[] chargeRealPayArray = chargeRealPay.split(",");
			String[] chargeAmountArray = chargeAmount.split(",");
			String[] baseCurrencyArray = baseCurrency.split(",");
			String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			PrpCmain prpCmain = this.getPrpCmainService().findPrpCmain(policyNo);
			// 费用
			for (int i = 1; i < baseCurrencyArray.length; i++) {
				if ("2".equals(prpCmain.getCoinsFlag()) || "3".equals(prpCmain.getCoinsFlag())) {// 从（联、共）保輸入的费用为我司分摊的费用，这里要计算总费用
					List<PrpCcoins> list = this.getPrpCcoinsService().findPrpCcoins(QueryRule.getInstance().addSql("policyNo='" + policyNo + "' and coinstype='2'"));
					for (Iterator<PrpCcoins> iterator = list.iterator(); iterator.hasNext();) {
						PrpCcoins prpCcoins = iterator.next();
						BigDecimal bigCoinsRate = new BigDecimal(new DecimalFormat(".00").format(prpCcoins.getCoinsRate().doubleValue() / 100));
						BigDecimal bigChargeRealPayValue = new BigDecimal(new DecimalFormat(".00").format(Double.parseDouble(chargeRealPayArray[i])));
						BigDecimal bigChargeAmountValue = new BigDecimal(new DecimalFormat(".00").format(Double.parseDouble(chargeAmountArray[i])));
						chargeRealPayValue = bigChargeRealPayValue.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
						chargeAmountValue = bigChargeAmountValue.divide(bigCoinsRate, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				} else {
					chargeRealPayValue = Double.parseDouble(chargeRealPayArray[i]);
					chargeAmountValue = Double.parseDouble(chargeAmountArray[i]);
				}
				try {
					exchRate = getExchangeRate(baseCurrencyArray[i], exchCurrency);
					if (exchRate == -1) {
						exchRate = 1;
					}
				} catch (Exception e) {
					CommonUtils.process(e);
				}
				dblSumDutyPaid = dblSumDutyPaid + chargeRealPayValue * exchRate;
				dblSumNoDutyFee = dblSumNoDutyFee + chargeAmountValue * exchRate;
			}
			// 财产险赔付标的、责任险财产
			if ("Q".equals(riskType) || "Y".equals(riskType) || "Z".equals(riskType) || "C".equals(riskType) || "G".equals(riskType)) {
				String strBaseCurrencyLoss = "";
				String[] baseCurrencyLossArray = null;
				String strLossRealPay = (String) hashMap.get("lossRealPay");
				String strSumRest = (String) hashMap.get("dblSumRest");
				String[] lossRealPayArray = strLossRealPay.split(",");
				String[] sumRestArray = strSumRest.split(",");
				double lossRealPay = 0;
				double dblSumRest = 0;
				for (int n = 1; n < lossRealPayArray.length; n++) {
					if ("Q".equals(riskType) || "Z".equals(riskType)) {
						try {
							strBaseCurrencyLoss = (String) hashMap.get("baseCurrencyLoss");
							baseCurrencyLossArray = strBaseCurrencyLoss.split(",");
							exchRate = getExchangeRate(baseCurrencyLossArray[n], exchCurrency);
						} catch (Exception e) {
							CommonUtils.process(e);
						}
						lossRealPay = Double.parseDouble(lossRealPayArray[n]) * exchRate;

					} else
						lossRealPay = Double.parseDouble(lossRealPayArray[n]);
					dblSumDutyPaid = dblSumDutyPaid + lossRealPay;
					dblSumRest = dblSumRest + Double.parseDouble(sumRestArray[n]);

				}
				prpLcompensate.setSumRest(dblSumRest);// 残值
			}
			// 意外险赔付人员、责任险赔付人员
			if ("E".equals(riskType) || "Z".equals(riskType)) {

				String strPersonLossRealPay = (String) hashMap.get("personLossRealPay");
				String[] personLossRealPayArray = strPersonLossRealPay.split(",");
				for (int n = 1; n < personLossRealPayArray.length; n++) {
					personLossSumRealPay = Double.parseDouble(personLossRealPayArray[n]);
					dblSumDutyPaid = dblSumDutyPaid + personLossSumRealPay;
				}
			}
			if (dblSumNoDutyFee < 0) {
				dblSumNoDutyFee = 0;
			}
			dblSumPaid = dblSumDutyPaid + dblSumNoDutyFee;
			dblSumThisPaid = dblSumDutyPaid - dblSumPrePaid;
			prpLcompensate.setSumDutyPaid(dblSumDutyPaid);// 标的损失赔款
			prpLcompensate.setSumNoDutyFee(dblSumNoDutyFee);// 费用金额
			prpLcompensate.setSumPaid(dblSumPaid);// 赔款合计与费用之和
			prpLcompensate.setSumThisPaid(dblSumThisPaid);// 本次标的损失赔款=标的损失赔款减去已预付赔款
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpLcompensate;
	}

	/**
	 * @author 中科软
	 * @deprecated 非车理算计算赔偿金额 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发） 如果免赔高：（核定损失 -
	 *             残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * @throws Exception
	 */
	public PrpLloss compensateRealPay(HashMap hashMap) throws Exception {
		PrpLloss prpLloss = new PrpLloss();
		String riskCode = (String) hashMap.get("riskCode");
		double sumLoss = Double.parseDouble((String) hashMap.get("SumLoss"));// 核损金额
		double sumRest = Double.parseDouble((String) hashMap.get("SumRest")); // 残值
		double claimRate = Double.parseDouble((String) hashMap.get("ClaimRate")); // 赔偿比例
		double deductibleRate = Double.parseDouble((String) hashMap.get("DeductibleRate")); // 免赔率
		double deductible = Double.parseDouble((String) hashMap.get("Deductible")); // 免赔额
		String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		String exchCurrency = (String) hashMap.get("exchCurrency"); // 得到目标币别类型
		String baseCurrency = (String) hashMap.get("baseCurrency");// 原币别
		double deductibleFlag = 0;
		double deductibleRateFlag = 0;
		double exchRate = 1;
		double realpay = 0;
		// 如果不别不同获取兑换率
		if (exchCurrency != baseCurrency) {
			exchRate = getExchangeRate(baseCurrency, exchCurrency);
			if (exchRate == -1) {
				exchRate = 1;
			}
		}
		if ("E".equals(riskType)) {
			realpay = (sumLoss - sumRest - deductible) * claimRate / 100;
			realpay = realpay * exchRate;
		} else {
			deductibleFlag = deductible;
			deductibleRateFlag = deductibleRate;
			if (deductibleRateFlag == 0 && deductibleFlag == 0) {
				realpay = (sumLoss - sumRest) * exchRate * claimRate;
			}
			if (deductibleRateFlag > 0) {
				// 输入免赔率後计算公式为 (标的-残值)*赔偿比例*(1-免赔率 )
				realpay = (sumLoss - sumRest) * exchRate * claimRate * (1 - deductibleRate);
			}
			if (deductibleFlag > 0) {
				// 输入免赔额後计算公式为 (标的-残值)*赔偿比例*-免赔额
				realpay = (sumLoss - sumRest) * exchRate * claimRate - deductible;
			}
		}
		prpLloss.setSumRealPay(realpay);
		return prpLloss;
	}

	public HashMap getPrpDcarModelBrand(HashMap hashMap) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			PrpDcarModel prpDcarModel = new PrpDcarModel();
			List<PrpDcarModel> prpDcarModelBrandCollection = new ArrayList<PrpDcarModel>();
			StringBuffer conditions = new StringBuffer();
			String factory;
			String carBrand;
			String findType = (String) hashMap.get("findType");
			if ("factory".equals(findType)) {
				factory = (String) hashMap.get("factory");
				conditions.append("factory='" + factory + "'");
				prpDcarModelBrandCollection = this.getPrpDcarModelService().findByConditions(conditions.toString());
				Iterator<PrpDcarModel> it = prpDcarModelBrandCollection.iterator();
				while (it.hasNext()) {
					prpDcarModel = (PrpDcarModel) it.next();
					map.put(prpDcarModel.getCarBrand(), prpDcarModel.getCarBrand());
				}
			}
			if ("carBrand".equals(findType)) {
				carBrand = (String) hashMap.get("carBrand");
				conditions.append("carBrand='" + carBrand + "'");
				prpDcarModelBrandCollection = this.getPrpDcarModelService().findByConditions(conditions.toString());
				Iterator<PrpDcarModel> it = prpDcarModelBrandCollection.iterator();
				while (it.hasNext()) {
					prpDcarModel = (PrpDcarModel) it.next();
					map.put(prpDcarModel.getCarSeriesName(), prpDcarModel.getCarSeriesName());
				}
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return map;
	}

	public synchronized PrpLloss getPrpLlossDto(HashMap hashMap) throws Exception {
		PrpLloss prpLloss = new PrpLloss();
		double sumLoss = Double.parseDouble((String) hashMap.get("SumLoss"));
		double sumRest = Double.parseDouble((String) hashMap.get("SumRest"));
		double CompelPay = Double.parseDouble((String) hashMap.get("CompelPay"));
		double factValue = Double.parseDouble((String) hashMap.get("factValue"));
		double purchasePrice = Double.parseDouble((String) hashMap.get("purchasePrice"));
		double sumDefPay = Double.parseDouble((String) hashMap.get("SumDefPay"));
		double claimRate = Double.parseDouble((String) hashMap.get("ClaimRate"));
		double dutyRate = Double.parseDouble((String) hashMap.get("DutyRate"));
		double arrangeRate = Double.parseDouble((String) hashMap.get("ArrangeRate"));
		Double dutyDeductibleRate = new Double(Double.parseDouble((String) hashMap.get("DutyDeductibleRate1")));
		double deductibleRate = Double.parseDouble((String) hashMap.get("DeductibleRate"));
		double unitPrice = Double.parseDouble((String) hashMap.get("unitPrice"));
		double amount = Double.parseDouble((String) hashMap.get("Amount"));
		double Deductible = Double.parseDouble((String) hashMap.get("Deductible"));
		double sumRealPay = 0.0;
		double exceptDeductiblePay = 0.0;
		double exceptDeductibleRate = 0.0;
		String damageStartDate = (String) hashMap.get("damageStartDate");
		String damageStartHour = (String) hashMap.get("damageStartHour");
		if (damageStartHour != null && !damageStartHour.equals("") && damageStartHour.length() > 15) {
			damageStartHour = damageStartHour.substring(13, 15);
		} else {
			damageStartHour = "0";
		}
		String riskCode = (String) hashMap.get("riskCode1");
		String kindCode = (String) hashMap.get("kindCode");
		String registNo = (String) hashMap.get("registNo");
		String indemnityDuty = (String) hashMap.get("indemnityDuty0");
		String prpLlossDtoIsLossAll = (String) hashMap.get("prpLlossDtoIsLossAll");
		String policyno = (String) hashMap.get("policyno");
		String flag = (String) hashMap.get("flagn");
		String kindCodeTemp = kindCode;
		// 获取utiCodeTransfer对应险别的configCode值
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		if ("".equals(kindCode) || kindCode == null) {
			prpLloss.setDutyDeductibleRate(dutyDeductibleRate.doubleValue());
			prpLloss.setDeductiblerate(deductibleRate);
			prpLloss.setSumRealPay(sumRealPay);
			prpLloss.setClaimRate(claimRate);
			prpLloss.setIndemnityDutyRate(Double.parseDouble(indemnityDuty));
			prpLloss.setExceptDeductibleRate(exceptDeductibleRate);
			prpLloss.setExceptDeductiblePay(exceptDeductiblePay);
			prpLloss.setFlag(flag);
			return prpLloss;
		}
		// 随车行李物品和特种车设备走A险、新增加设备损失险
		if ("NZ".equals(kindCode) || "X".equals(kindCode)) {
			kindCode = ConstantCodes.KINDCODE_D_A;
		}
		if (!("F".equals(kindCode) || "L".equals(kindCode) || "NX".equals(kindCode) || "NY".equals(kindCode) || "Z".equals(kindCode) || "C5".equals(kindCode) || "X1".equals(kindCode))) {
			String kindCodeStr = kindCode;
			// 责任免赔率
			dutyDeductibleRate = getDutydeductRate(riskCode, kindCodeStr, indemnityDuty, registNo);
			// 必须做个临时变量，否则会变更之後内容
			List<PrpLdeductCond> prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
			boolean haveselect = false; // 是否选择了 单方，无法第三者和协商处理
			boolean deductFlag = false;// 免赔条件-单方肇事标志
			PrpLdeductCond prpLdeductCondTemp = new PrpLdeductCond(); // 若选择了
			for (int c = 0; c < prpLdeductCondList.size(); c++) {
				PrpLdeductCond prpLdeductCond = prpLdeductCondList.get(c);
				String deductCondCode = prpLdeductCond.getId().getDeductCondCode();
				int times = prpLdeductCond.getTimes();
				// 这三个是做为事故责任使用的，不是做为绝对免赔用的。。
				if (("110".equals(deductCondCode) && times == 1) || ("120".equals(deductCondCode) && times == 1) || ("121".equals(deductCondCode) && times == 1)) {
					haveselect = true;
					if ("110".equals(deductCondCode)) {
						deductFlag = true;
					}
					// 做一个临时的变量，为了替换後面的事故责任。
					prpLdeductCondTemp.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp.setDeductCondName(prpLdeductCond.getDeductCondName());
					// 由於静态变量的问题，没办法，先借用一下变量吧。
					prpLdeductCondTemp.setTimes(times);
					PrpLdeductCond prpLdeductCondTemp2 = new PrpLdeductCond();
					prpLdeductCondTemp2.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp2.setDeductCondName(prpLdeductCond.getDeductCondName());
					prpLdeductCondTemp2.setTimes(0);
					prpLdeductCondListTemp.add(prpLdeductCondTemp2);
				} else {
					prpLdeductCondListTemp.add(prpLdeductCond);
				}
				// 由於取绝对免赔的时候，需要先取掉这3个关於事故的。。然後在取这三个哪个大。。(或者只能三选1)放在责任里面，並且责任这时候是需要选其他的。
			}
			// 绝对免赔率：
			ExceptDeductibleRateDto exceptDeductibleRateDto = this.getDeductRate(kindCodeStr, registNo, prpLdeductCondListTemp);
			deductibleRate = exceptDeductibleRateDto.getDeductibleRate();
			// 判断事故的责任
			if (haveselect && (!ConstantCodes.KINDCODE_D_B.equals(kindCodeStr)) && (!"D11".equals(kindCodeStr)) && (!"D12".equals(kindCodeStr)) && (!"G".equals(kindCodeStr))) {
				prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
				prpLdeductCondListTemp.add(prpLdeductCondTemp);
				// 责任免赔率
				ExceptDeductibleRateDto exceptDeductibleRate1 = this.getDeductRate(kindCodeStr, registNo, prpLdeductCondListTemp);
				double rateTemp = exceptDeductibleRate1.getDeductibleRate();
				if (rateTemp > 0)
					dutyDeductibleRate = new Double(rateTemp);
			}
			if ("1".equals(flag)) {
				exceptDeductibleRate = dutyDeductibleRate.doubleValue() + (exceptDeductibleRateDto.getAfterDeductibleRate());
				if (haveselect && !deductFlag) {
					exceptDeductibleRate = (exceptDeductibleRateDto.getAfterDeductibleRate());
				}
			}
		}
		if ("NZ".equals(kindCodeTemp) || "X".equals(kindCodeTemp)) {
			kindCode = kindCodeTemp;
		}
		// 获取出险时保单信息
//		PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(policyno, damageStartDate, damageStartHour);
		// 获取出险时保单信息
		String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		// 获取提车保险车损险和三者险保险金额
		if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyno, damageStartDate, damageStartHour, null, null);
			for (int i = 0; i < prpCitemKindList.size(); i++) {
				PrpCitemKind prpCitemKindDto = (PrpCitemKind) prpCitemKindList.get(i);
				if (ConstantCodes.KINDCODE_D_A.equals(kindCode) && "AB".equalsIgnoreCase(prpCitemKindDto.getKindCode())) {
					amount = prpCitemKindDto.getAmount() - prpCitemKindDto.getValue();
				} else if (ConstantCodes.KINDCODE_D_B.equals(kindCode) && "AB".equalsIgnoreCase(prpCitemKindDto.getKindCode())) {
					prpCitemKindDto.getValue();
				}
			}
		}
		if ("D".equals(riskType)) {
			if (ConstantCodes.KINDCODE_D_A.equals(kindCode)) {// 车损险计算
				List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyno, damageStartDate, damageStartHour);
				PrpCitemCar prpCitemCarDto = (PrpCitemCar) prpCitemCarList.get(0);
				claimRate = 100;
				if ("Y".equals(prpLlossDtoIsLossAll)) {
					// 增加提车保险特殊处理
					if ("RISKCODE_DTC".equals(configCode) || "RISKCODE_DAS".equals(configCode)) {
						sumRealPay = (factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
						// 扣减可选免赔额後，赔款小於零，则实际赔款为零
						if (sumRealPay >= 0) {
							sumRealPay = sumRealPay - Deductible;
							if (sumRealPay < 0) {
								sumRealPay = 0;
							}
						} else {
							sumRealPay = sumRealPay - Deductible;
						}
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
						if ("1".equals(flag)) {
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
						}
					} else {
						if (factValue >= amount) {
							sumRealPay = (amount - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
							// 扣减可选免赔额後，赔款小於零，则实际赔款为零
							if (sumRealPay >= 0) {
								sumRealPay = sumRealPay - Deductible;
								if (sumRealPay < 0) {
									sumRealPay = 0;
								}
							} else {
								sumRealPay = sumRealPay - Deductible;
							}
							sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
							if ("1".equals(flag)) {
								exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((amount - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
							}
						} else {
							sumRealPay = (factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
							// 扣减可选免赔额後，赔款小於零，则实际赔款为零
							if (sumRealPay >= 0) {
								sumRealPay = sumRealPay - Deductible;
								if (sumRealPay < 0) {
									sumRealPay = 0;
								}
							} else {
								sumRealPay = sumRealPay - Deductible;
							}
							sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
							if ("1".equals(flag)) {
								exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
							}
						}
					}
				} else {
					if (amount >= prpCitemCarDto.getPurchasePrice().doubleValue()) {
						sumRealPay = (sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
						// 扣减可选免赔额後，赔款小於零，则实际赔款为零
						if (sumRealPay >= 0) {
							sumRealPay = sumRealPay - Deductible;
							if (sumRealPay < 0) {
								sumRealPay = 0;
							}
						} else {
							sumRealPay = sumRealPay - Deductible;
						}
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
						if ("1".equals(flag)) {
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
						}
					} else {
						claimRate = (amount / (prpCitemCarDto.getPurchasePrice().doubleValue())) * 100;
						sumRealPay = (sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
						// 扣减可选免赔额後，赔款小於零，则实际赔款为零
						if (sumRealPay >= 0) {
							sumRealPay = sumRealPay - Deductible;
							if (sumRealPay < 0) {
								sumRealPay = 0;
							}
						} else {
							sumRealPay = sumRealPay - Deductible;
						}
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
						if ("1".equals(flag)) {
							exceptDeductiblePay = (sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100);
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(exceptDeductiblePay));
						}
					}
					if (sumRealPay > factValue) {
						sumRealPay = (factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
						// 扣减可选免赔额後，赔款小於零，则实际赔款为零
						if (sumRealPay >= 0) {
							sumRealPay = sumRealPay - Deductible;
							if (sumRealPay < 0) {
								sumRealPay = 0;
							}
						} else {
							sumRealPay = sumRealPay - Deductible;
						}
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
						if ("1".equals(flag)) {
							exceptDeductiblePay = (factValue - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100);
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(exceptDeductiblePay));
						}
					}
					claimRate = Double.parseDouble(new DecimalFormat("#.##").format(claimRate));
				}
			} else if (ConstantCodes.KINDCODE_D_B.equals(kindCode)) {// 三者险计算
				claimRate = 100;
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
				if ("1".equals(flag)) {
					exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
				}
			} else if ("G".equals(kindCode)) {// 盗抢险计算
				dutyRate = 100;
				claimRate = 100;
				if ("Y".equals(prpLlossDtoIsLossAll)) {
					deductibleRate = deductibleRate + 20;
					if (amount > factValue)
						amount = factValue;
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductibleRate += 20;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
					}
				}
				if (sumRealPay > factValue) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((factValue) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((factValue) * claimRate / 100 * arrangeRate / 100 * dutyRate / 100 * (exceptDeductibleRate / 100)));
					}
				}
			} else if ("F".equals(kindCode)) {// 玻璃单独破碎险计算
				dutyRate = 100;
				// 该险别处理时，免赔率均为零
				dutyDeductibleRate = new Double(0);
				deductibleRate = 0;
				sumRealPay = sumDefPay;
			} else if ("L".equals(kindCode)) {// 车身划痕险计算
				deductibleRate = 15;
				exceptDeductibleRate = 15;
				if (sumDefPay <= amount) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * deductibleRate / 100));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * deductibleRate / 100));
					}
				}
			} else if ("NZ".equals(kindCode)) {// 随车行李物品计算
				if (sumDefPay <= amount) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * dutyRate / 100 * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * dutyRate / 100 * (exceptDeductibleRate / 100)));
					}
				}
			} else if ("NX".equals(kindCode)) {// 新车特约条款A
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(purchasePrice));
			} else if ("NY".equals(kindCode)) {// 新车特约条款B
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(purchasePrice));
			} else if ("Z".equals(kindCode)) {// 自燃损失险条款
				deductibleRate = 20;
				if (sumDefPay - sumRest - CompelPay <= amount) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((amount - sumRest - CompelPay) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((amount - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
				}
			} else if ("X".equals(kindCode)) {// 新增加设备损失险
				if (sumDefPay - sumRest - CompelPay <= amount) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((amount - sumRest - CompelPay) * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100))));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((amount - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
				}
			} else if ("X1".equals(kindCode)) {// 发动机特约条款
				deductibleRate = 20;
				if (sumDefPay <= factValue) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
					if (sumRealPay > amount * (1 - deductibleRate / 100)) {
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (1 - deductibleRate / 100)));
						if ("1".equals(flag)) {
							exceptDeductibleRate = deductibleRate;
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (exceptDeductibleRate / 100)));
						}
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((factValue - sumRest - CompelPay) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((factValue - sumRest - CompelPay) * (exceptDeductibleRate / 100)));
					}
					if (sumRealPay > amount * (1 - deductibleRate / 100)) {
						sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (1 - deductibleRate / 100)));
						if ("1".equals(flag)) {
							exceptDeductibleRate = deductibleRate;
							exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (exceptDeductibleRate / 100)));
						}
					}
				}
			} else if ("C6".equals(kindCode)) {// 法律服务特约
				if (sumDefPay > amount)
					sumDefPay = amount;
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay));
			} else if ("V1".equals(kindCode)) {// 附加油污污染
				deductibleRate = 20;
				if (sumDefPay > amount)
					sumDefPay = amount;
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * (1 - deductibleRate / 100)));
				if ("1".equals(flag)) {
					exceptDeductibleRate = deductibleRate;
					exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(sumDefPay * (exceptDeductibleRate / 100)));
				}
			} else if (ConstantCodes.KINDCODE_D_D2.equals(kindCode)) {// 车上货物责任险
				deductibleRate = 20;
				if (sumDefPay - CompelPay <= amount) {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay) * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format(amount * (exceptDeductibleRate / 100)));
					}
				}
			} else if ("E".equals(kindCode)) {// 火灾、爆炸、自燃损失险
				deductibleRate = 20; // 每次实行20%的免赔率
				dutyDeductibleRate = new Double(0d);
				if ("Y".equals(prpLlossDtoIsLossAll)) {// 全损
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((amount - CompelPay - sumRest) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((amount - CompelPay - sumRest) * (exceptDeductibleRate / 100)));
					}
				} else {
					sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * (1 - deductibleRate / 100)));
					if ("1".equals(flag)) {
						exceptDeductibleRate = deductibleRate;
						exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay - sumRest) * (exceptDeductibleRate / 100)));
					}
				}
			}
		}
		prpLloss.setDutyDeductibleRate(dutyDeductibleRate.doubleValue());
		prpLloss.setDeductiblerate(deductibleRate);
		prpLloss.setSumRealPay(sumRealPay);
		prpLloss.setClaimRate(claimRate);
		prpLloss.setIndemnityDutyRate(dutyRate);
		prpLloss.setExceptDeductibleRate(exceptDeductibleRate);
		prpLloss.setExceptDeductiblePay(exceptDeductiblePay);
		prpLloss.setFlag(flag);
		return prpLloss;
	}

	public synchronized PrpLpersonLoss getPrpLpersonLossDto(HashMap hashMap) throws Exception {
		PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
		Double dutyDeductibleRate = 0D;
		double deductibleRate = 0;
		double sumRealPay = 0;
		double exceptDeductiblePay = 0;
		double exceptDeductibleRate = 0;
		String flag = "";
		try {
			double CompelPay = Double.parseDouble((String) hashMap.get("CompelPay"));
			double sumLoss = Double.parseDouble((String) hashMap.get("SumLoss"));
			double sumRest = Double.parseDouble((String) hashMap.get("SumRest"));
			double sumDefPay = Double.parseDouble((String) hashMap.get("SumDefPay"));
			double dutyRate = Double.parseDouble((String) hashMap.get("DutyRate"));
			double arrangeRate = Double.parseDouble((String) hashMap.get("ArrangeRate"));
			dutyDeductibleRate = new Double(Double.parseDouble((String) hashMap.get("DutyDeductibleRate")));
			deductibleRate = Double.parseDouble((String) hashMap.get("DeductibleRate"));
			sumRealPay = 0.0;
			exceptDeductiblePay = 0.0;
			exceptDeductibleRate = 0.0;
			String riskCode = (String) hashMap.get("riskCode1");
			String kindCode = (String) hashMap.get("kindCode");
			String registNo = (String) hashMap.get("registNo");
			String indemnityDuty = (String) hashMap.get("indemnityDuty1");
			flag = (String) hashMap.get("flagn");
			if ("".equals(kindCode) || kindCode == null) {
				prpLpersonLoss.setDutyDeductibleRate(dutyDeductibleRate.doubleValue());
				prpLpersonLoss.setDeductiblerate(deductibleRate);
				prpLpersonLoss.setSumRealPay(sumRealPay);
				prpLpersonLoss.setExceptDeductiblePay(exceptDeductiblePay);
				prpLpersonLoss.setExceptDeductibleRate(exceptDeductibleRate);
				prpLpersonLoss.setFlag(flag);
				return prpLpersonLoss;
			}
			// 责任免赔率
			dutyDeductibleRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, registNo);
			// 绝对免赔率
			// 必须做个临时变量，否则会变更之後内容
			List<PrpLdeductCond> prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
			boolean haveselect = false; // 是否选择了 单方，无法第三者和协商处理
			boolean deductFlag = false;// 免赔条件-单方肇事标志
			PrpLdeductCond prpLdeductCondTemp = new PrpLdeductCond(); // 若选择了
			// 单方，无法第三者和协商处理其一，放这里。
			for (int c = 0; c < prpLdeductCondList.size(); c++) {
				PrpLdeductCond prpLdeductCond = prpLdeductCondList.get(c);
				String deductCondCode = prpLdeductCond.getId().getDeductCondCode();
				int times = prpLdeductCond.getTimes();
				// 这三个是做为事故责任使用的，不是做为绝对免赔用的。。
				if (("110".equals(deductCondCode) && times == 1) || ("120".equals(deductCondCode) && times == 1) || ("121".equals(deductCondCode) && times == 1)) {
					haveselect = true;
					if ("110".equals(deductCondCode)) {
						deductFlag = true;
					}
					// 做一个临时的变量，为了替换後面的事故责任。
					prpLdeductCondTemp.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp.setDeductCondName(prpLdeductCond.getDeductCondName());
					// 由於静态变量的问题，没办法，先借用一下变量吧。
					prpLdeductCondTemp.setTimes(times);
					PrpLdeductCond prpLdeductCondTemp2 = new PrpLdeductCond();
					prpLdeductCondTemp2.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp2.setDeductCondName(prpLdeductCond.getDeductCondName());
					prpLdeductCondTemp2.setTimes(0);
					prpLdeductCondListTemp.add(prpLdeductCondTemp2);
				} else {
					prpLdeductCondListTemp.add(prpLdeductCond);
				}
				// 由於取绝对免赔的时候，需要先取掉这3个关於事故的。。然後在取这三个哪个大。。(或者只能三选1)放在责任里面，並且责任这时候是需要选其他的。
			}
			// 绝对免赔率：
			ExceptDeductibleRateDto exceptDeductibleRateDto = this.getDeductRate(kindCode, registNo, prpLdeductCondListTemp);
			deductibleRate = exceptDeductibleRateDto.getDeductibleRate();
			// 判断事故的责任
			if (haveselect && (!ConstantCodes.KINDCODE_D_B.equals(kindCode)) && (!"D11".equals(kindCode)) && (!"D12".equals(kindCode)) && (!"G".equals(kindCode))) {
				prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
				prpLdeductCondListTemp.add(prpLdeductCondTemp);
				// 责任免赔率
				ExceptDeductibleRateDto exceptDeductibleRateDto1 = getDeductRate(kindCode, registNo, prpLdeductCondListTemp);
				double rateTemp = exceptDeductibleRateDto1.getDeductibleRate();
				if (rateTemp > 0)
					dutyDeductibleRate = new Double(rateTemp);
			}
			if ("D11".equals(kindCode) || "D12".equals(kindCode)) {
				sumRealPay = (sumDefPay - CompelPay) * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
			} else if (ConstantCodes.KINDCODE_D_B.equals(kindCode)) {
				sumRealPay = (sumDefPay - CompelPay) * arrangeRate / 100 * dutyRate / 100 * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
			} else if ("R".equals(kindCode)) {// 精神损害赔偿
				deductibleRate = 20;
				sumRealPay = (sumDefPay - CompelPay) * (1 - (dutyDeductibleRate.doubleValue() / 100 + deductibleRate / 100));
				sumRealPay = Double.parseDouble(new DecimalFormat("#.##").format(sumRealPay));
			}
			if ("1".equals(flag)) {
				exceptDeductibleRate = Double.parseDouble(new DecimalFormat("#.##").format(dutyDeductibleRate.doubleValue() + exceptDeductibleRateDto.getAfterDeductibleRate()));
				if (haveselect && !deductFlag) {
					exceptDeductibleRate = Double.parseDouble(new DecimalFormat("#.##").format(exceptDeductibleRateDto.getAfterDeductibleRate()));
				}
				exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay) * arrangeRate / 100 * dutyRate / 100 * exceptDeductibleRate / 100));
				if ("R".equals(kindCode)) {
					exceptDeductibleRate = deductibleRate;
					exceptDeductiblePay = Double.parseDouble(new DecimalFormat("#.##").format((sumDefPay - CompelPay) * exceptDeductibleRate / 100));
				}
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		prpLpersonLoss.setDutyDeductibleRate(dutyDeductibleRate.doubleValue());
		prpLpersonLoss.setDeductiblerate(deductibleRate);
		prpLpersonLoss.setSumRealPay(sumRealPay);
		prpLpersonLoss.setExceptDeductiblePay(exceptDeductiblePay);
		prpLpersonLoss.setExceptDeductibleRate(exceptDeductibleRate);
		prpLpersonLoss.setFlag(flag);
		return prpLpersonLoss;
	}

	/**
	 * 取绝对免赔率
	 * @author 中科软
	 * @param kindCode 险别
	 * @param registNo 报案号
	 * @param deductConditionList 免赔条件
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	private ExceptDeductibleRateDto getDeductRate(String kindCode, String registNo, List<PrpLdeductCond> deductConditionList) throws Exception {
		ExceptDeductibleRateDto exceptDeductibleRateDto = new ExceptDeductibleRateDto();
		try {
			double deductRate = 0.0;
			String riskCode = "";
			String clauseType = "";
			RegistDto registDto = this.getRegistService().findByPrimaryKey(registNo);
			String validDate = "";
			UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
			PrpCitemCar prpCitemCarDto = new PrpCitemCar();
			if (registDto != null) {
				PrpLregist prpLregist = registDto.getPrpLregist();
				riskCode = prpLregist.getRiskCode();
				String policyNo = prpLregist.getPolicyNo();
				String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
				String damageHour = prpLregist.getDamageStartHour();
				PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
				if (prpCmain != null) {
					validDate = CommonUtils.getYearToDayStr(prpCmain.getStartDate());
					List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
					if (!CommonUtils.isEmpty(prpCitemCarList)) {
						prpCitemCarDto = (PrpCitemCar) prpCitemCarList.get(0);
						if (prpCitemCarDto != null)
							clauseType = prpCitemCarDto.getClauseType(); // f44
					}
					exceptDeductibleRateDto = uiDeductCondAction.getDeductibleRateOfAbsolute(clauseType, kindCode, deductConditionList, riskCode, validDate);
				} else {
					throw new UserException(1, 3, "claim", "無此保單號!");
				}
			} else {
				throw new UserException(1, 3, "claim", "無此保單備案號!");
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return exceptDeductibleRateDto;
	}

	/**
	 * 判断是否超过限额
	 * @author 中科软
	 * @param hashMap 含险别，保单号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public String checkAmount(HashMap hashMap) throws Exception {
		String conditions = "";
		String policyno = (String) hashMap.get("policyno");
		String kindCode = (String) hashMap.get("kindCode");
		String returnString = "";
		try {
			conditions = " policyno = '" + policyno + "' and kindcode = '" + kindCode + "'";
			PrpCitemKind prpCitemKind = new PrpCitemKind();
			List<PrpCitemKind> collection = this.getPrpCitemKindService().findPrpCitemKind(QueryRule.getInstance().addSql(conditions));
			Iterator<PrpCitemKind> it = collection.iterator();
			if (it.hasNext()) {
				prpCitemKind = it.next();
			}
			returnString = new Double(prpCitemKind.getAmount()).toString();
			if (ConstantCodes.KINDCODE_D_A.equals(kindCode) || "G".equals(kindCode)) {
				PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim((String) hashMap.get("claimno"));
				returnString = returnString + "," + prpLclaim.getEscapeFlag().substring(1, 2);
			} else {
				returnString = returnString + ",0";
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return returnString;
	}

	/**
	 * 取出所有的免赔率
	 * @author 中科软
	 * @param hashMap 含险别，顺序号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public ExceptDeductibleRateDto getAllDeductibleRate(HashMap hashMap) throws Exception {
		String indemnityDuty = (String) hashMap.get("indemnityDuty");
		String level = (String) hashMap.get("level");
		String kindCode = (String) hashMap.get("kindCode");
		String riskCode = (String) hashMap.get("riskCode1");
		String registNo = (String) hashMap.get("registNo");
		double deductibleRate = 0.0;
		double exceptDeductibleRate = 0.0;
		ExceptDeductibleRateDto exceptDeductibleRateDto = null;
		try {
			Double dutyDeductibleRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, registNo);
			List<PrpLdeductCond> prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
			boolean haveselect = false; // 是否选择了 单方，无法第三者和协商处理
			boolean deductFlag = false;// 免赔条件-单方肇事标志
			PrpLdeductCond prpLdeductCondTemp = new PrpLdeductCond(); // 若选择了
			// 单方，无法第三者和协商处理其一，放这里。
			for (int c = 0; c < prpLdeductCondList.size(); c++) {
				PrpLdeductCond prpLdeductCond = prpLdeductCondList.get(c);
				String deductCondCode = prpLdeductCond.getId().getDeductCondCode();
				int times = prpLdeductCond.getTimes();
				// 这三个是做为事故责任使用的，不是做为绝对免赔用的。。
				if (("110".equals(deductCondCode) && times == 1) || ("120".equals(deductCondCode) && times == 1) || ("121".equals(deductCondCode) && times == 1)) {
					haveselect = true;
					if ("110".equals(deductCondCode)) {
						deductFlag = true;
					}
					// 做一个临时的变量，为了替换後面的事故责任。
					prpLdeductCondTemp.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp.setDeductCondName(prpLdeductCond.getDeductCondName());
					// 由於静态变量的问题，没办法，先借用一下变量吧。
					prpLdeductCondTemp.setTimes(times);
					PrpLdeductCond prpLdeductCondTemp2 = new PrpLdeductCond();
					prpLdeductCondTemp2.getId().setDeductCondCode(deductCondCode);
					prpLdeductCondTemp2.setDeductCondName(prpLdeductCond.getDeductCondName());
					prpLdeductCondTemp2.setTimes(0);
					prpLdeductCondListTemp.add(prpLdeductCondTemp2);
				} else {
					prpLdeductCondListTemp.add(prpLdeductCond);
				}
				// 由於取绝对免赔的时候，需要先取掉这3个关於事故的。。然後在取这三个哪个大。。(或者只能三选1)放在责任里面，並且责任这时候是需要选其他的。
			}
			exceptDeductibleRateDto = getDeductRate(kindCode, registNo, prpLdeductCondListTemp);
			deductibleRate = exceptDeductibleRateDto.getDeductibleRate();
			if (haveselect && (!ConstantCodes.KINDCODE_D_B.equals(kindCode)) && (!"D11".equals(kindCode)) && (!"D12".equals(kindCode)) && (!"G".equals(kindCode))) {
				prpLdeductCondListTemp = new ArrayList<PrpLdeductCond>();
				prpLdeductCondListTemp.add(prpLdeductCondTemp);
				// 责任免赔率
				ExceptDeductibleRateDto exceptDeductibleRateDto1 = this.getDeductRate(kindCode, registNo, prpLdeductCondListTemp);
				double rateTemp = exceptDeductibleRateDto1.getDeductibleRate();
				if (rateTemp > 0) {
					dutyDeductibleRate = new Double(rateTemp);
				}
			}
			exceptDeductibleRate = dutyDeductibleRate.doubleValue() + exceptDeductibleRateDto.getAfterDeductibleRate();
			if (haveselect && !deductFlag) {
				exceptDeductibleRate = exceptDeductibleRateDto.getAfterDeductibleRate();
			}
			exceptDeductibleRateDto.setExceptDeductibleRatePay(exceptDeductibleRate);
			exceptDeductibleRateDto.setAfterDeductibleRate(dutyDeductibleRate.doubleValue());
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return exceptDeductibleRateDto;
	}

	/**
	 * 全责方向平台传输数据
	 * @author 中科软
	 * @param hashMap 含险别，顺序号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public ReturnInfo getPrpLagentDto(HashMap hashMap) throws Exception {
		String uploadType = (String) hashMap.get("uploadType1");
		String registNo = (String) hashMap.get("registNo1");
		String comCode = (String) hashMap.get("comCode1");
		ReturnInfo returnInfo = new ReturnInfo();
		if ("D5".equals(uploadType)) {
			String conditions = " fullreportno='" + registNo + "' and claimtype='1'";

			List<PrpLagent> collection = this.getPrpLagentService().findByConditions(conditions);
			if (collection.size() > 0) {
				PrpLagent prpLagent = collection.iterator().next();
				String returnString = this.checkuploadInfo(prpLagent);
				if (!("".equals(returnString))) {// 说明数据没有輸入完整
					returnString += "請繼續輸入訊息！";
					returnInfo.setErrorMessage(returnString);

					return returnInfo;
				}
				if ("".equals(prpLagent.getAdvanceNo())) {
				} else {
					returnInfo.setErrorMessage("已經上傳成功，不能重複上傳！");
				}
			} else {
				returnInfo.setErrorMessage("還沒輸入上傳訊息，請繼續輸入訊息！");
			}
		} else if ("DA".equals(uploadType)) {
			returnInfo.setErrorMessage("平台不再支持上傳圖片，還要上傳嗎?");
			return returnInfo;
		}

		return returnInfo;
	}

	/**
	 * 无责方向平台确认垫付信息
	 * @author 中科软
	 * @param hashMap 含险别，顺序号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public ReturnInfo getAdvanceConfirm(HashMap hashMap) throws Exception {
		AdvanceConfirm advanceConfirmDto = new AdvanceConfirm();
		advanceConfirmDto.setComCode((String) hashMap.get("comCode1"));
		advanceConfirmDto.setNullReportNo((String) hashMap.get("nullReportNo1"));
		advanceConfirmDto.setNullComments("無訊息");
		advanceConfirmDto.setPassFlag("1");
		BLAdvanceFacade blAdvanceFacade = new BLAdvanceFacade();
		ReturnInfo returnInfo = blAdvanceFacade.sendAdvanceConfirmToPlatform(Iconstants.RequstType.AdvanceConfirm, advanceConfirmDto);
		return returnInfo;
	}

	/**
	 * 全责方向平台获取确认信息
	 * @author 中科软
	 * @param hashMap 含险别，顺序号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public ReturnInfo nullConfirmInfo(HashMap hashMap) throws Exception {
		AdvanceResponse advanceResponseDto = new AdvanceResponse();
		advanceResponseDto.setComCode((String) hashMap.get("comCode1"));
		advanceResponseDto.setFullResportNo((String) hashMap.get("registNo1"));
		BLAdvanceFacade blAdvanceFacade = new BLAdvanceFacade();
		ReturnInfo returnInfo = blAdvanceFacade.sendAdvanceResponseToPlatform(Iconstants.RequstType.AdvanceResponse, advanceResponseDto);
		return returnInfo;
	}

	/**
	 * 无责方向平台获取全责方上传的信息
	 * @author 中科软
	 * @param hashMap 含险别，顺序号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public ReturnInfo getEndCaseResponseReturn(HashMap hashMap) throws Exception {
		EndCaseResponse endCaseResponse = new EndCaseResponse();
		endCaseResponse.setComCode((String) hashMap.get("comCode1"));
		endCaseResponse.setNullReposrtNo((String) hashMap.get("nullReportNo1"));
		BLAdvanceFacade blAdvanceFacade = new BLAdvanceFacade();
		ReturnInfo returnInfo = blAdvanceFacade.sendEndCaseResponseToPlatform(Iconstants.RequstType.EndCaseResponse, endCaseResponse);
		return returnInfo;
	}

	private String checkuploadInfo(PrpLagent prpLagent) throws Exception {
		String message = "";
		if ("".equals(prpLagent.getId().getNullReportNo()) || "000000000000000000000".equals(prpLagent.getId().getNullReportNo()))
			message = message + "無責方備案號不能為空！\n";
		if ("".equals(prpLagent.getNullCarMark()))
			message = message + "無責方牌照號碼不能為空！\n";
		if ("".equals(prpLagent.getNullVihecleType()))
			message = message + "無責方號牌種類不能為空！\n";
		if ("".equals(prpLagent.getId().getFullReportNo()))
			message = message + "全責方備案號不能為空！\n";
		if ("".equals(prpLagent.getFullClaimCode()))
			message = message + "全責方賠案號不能為空！\n";
		if ("".equals(prpLagent.getNullCompany()))
			message = message + "無責方公司號碼不能為空！\n";
		if ("".equals(prpLagent.getNullComName()))
			message = message + "無責方公司名稱不能為空！\n";
		if (prpLagent.getSettleMentAmount().intValue() == 0)
			message = message + "無責方賠償金額不能為空！\n";
		if (prpLagent.getEstimatedAmount().intValue() == 0)
			message = message + "定損金額不能為空！\n";
		if ("".equals(prpLagent.getNullInsured()))
			message = message + "無責方被保險人不能為空！\n";
		if ("".equals(prpLagent.getId().getClaimType()))
			message = message + "賠案類型不能為空！\n";
		if ("".equals(prpLagent.getPayMode()))
			message = message + "支付方式不能為空！\n";
		return message;
	}

	/**
	 * 取出所有的免赔率
	 * @author 中科软
	 * @param hashMap 含险别，标的,保单号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	synchronized public PrpCitemKind getPrpcitemkind(HashMap hashMap) throws Exception {
		String kindCode = (String) hashMap.get("kindCode1");
		String itemCode = (String) hashMap.get("itemCode");
		String policyno = (String) hashMap.get("policyno");
		PrpCitemKind prpCitemKind = new PrpCitemKind();
		try {
			String conditions = "1 = 1 and policyno = '" + policyno + "' and kindCode='" + kindCode + "' and itemCode = '" + itemCode + "'";
			List<PrpCitemKind> collection = this.getPrpCitemKindService().findPrpCitemKind(QueryRule.getInstance().addSql(conditions));
			Iterator<PrpCitemKind> iterator = collection.iterator();
			while (iterator.hasNext()) {
				prpCitemKind = (PrpCitemKind) iterator.next();
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prpCitemKind;
	}

	/**
	 * 获得单证上传时节点和当前节点
	 * @author 中科软
	 * @param hashMap 保单号
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public PrpDcode getUpLoadNodeFlag(HashMap hashMap) throws Exception {
		String uploadNodeFlag = "";
		PrpLcertifyImg prpLcertifyImg = new PrpLcertifyImg();
		String registNo = (String) hashMap.get("registNo");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.businessNo", registNo);
		List<PrpLcertifyImg> collection = this.getPrpLcertifyImgService().findPrpLcertifyImg(queryRule);
		if (collection != null && !collection.isEmpty()) {
			prpLcertifyImg = collection.get(0);
		}
		uploadNodeFlag = prpLcertifyImg.getUploadNodeFlag();
		return this.getPrpDcodeService().findByPrimaryKey("ClaimNodeType", uploadNodeFlag);
	}

	/**
	 * 获得外部机构信息
	 * @author 中科软
	 * @param hashMap 外部机构组织机构代码
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public PrpLexternalAgency getExternAlagency(HashMap hashMap) throws Exception {
		String comCode = (String) hashMap.get("comCode");
		String conditions = "comcode ='" + comCode + "'";
		PrpLexternalAgency prplexternalagency = null;
		try {
			List<PrpLexternalAgency> collection = this.getPrpLexternalAgencyService().findPrpLexternalAgency(QueryRule.getInstance().addSql(conditions));
			if (collection != null && !collection.isEmpty()) {
				prplexternalagency = (PrpLexternalAgency) collection.iterator().next();
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prplexternalagency;
	}

	/**
	 * 获得外部机构信息
	 * @author 中科软
	 * @param String 外部机构组织机构代码
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public PrpLexternalAgency getExternAlagencyByStr(String comCode) throws Exception {
		String conditions = "comcode ='" + comCode + "'";
		PrpLexternalAgency prplexternalagency = null;
		try {
			List<PrpLexternalAgency> collection = this.getPrpLexternalAgencyService().findPrpLexternalAgency(QueryRule.getInstance().addSql(conditions));
			if (collection != null && !collection.isEmpty()) {
				prplexternalagency = (PrpLexternalAgency) collection.iterator().next();
			}
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return prplexternalagency;
	}

	/**
	 * 根据险类代码获得对应的险种信息
	 * @author 中科软
	 * @param String 险类代码
	 * @return List
	 * @throws Exception
	 */
	public String getRiskCodeAndNameByClassCode(String classCode) throws Exception {
		String conditions = "classCode = '" + classCode + "' and validstatus='1' order by riskCode";
		List<PrpDrisk> list = this.getPrpDriskService().findByConditions(conditions);
		String string = "";
		if(list != null && list.size() > 0){
		for (Iterator<PrpDrisk> iterator = list.iterator(); iterator.hasNext();) {
			PrpDrisk prpDrisk = iterator.next();
			string += prpDrisk.getRiskCode() + "|" + prpDrisk.getRiskCName() + ",";
		}
		return string.substring(0, string.lastIndexOf(","));
		}
		return string;
	}

	public double getPrpLdisabilityLimitFee(HashMap hashMap) throws Exception {
		double limitFee = 0;
		try {
			String claimNo = (String) hashMap.get("claimNo");
			String ratingCode = (String) hashMap.get("ratingCode");
			limitFee = prpLdisabilityLimitService.getPrpLdisabilityLimitFee(claimNo, ratingCode);
		} catch (Exception e) {
			CommonUtils.process(e);
		}
		return limitFee;
	}

	public CompanyService getCompanyService() {
		if (companyService == null) {
			return (CompanyService) ServiceFactory.getService("companyService");
		}
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public RegistService getRegistService() {
		if (registService == null) {
			return (RegistService) ServiceFactory.getService("registService");
		}
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			return (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcertifyImgService getPrpLcertifyImgService() {
		if (prpLcertifyImgService == null) {
			return (PrpLcertifyImgService) ServiceFactory.getService("prpLcertifyImgService");
		}
		return prpLcertifyImgService;
	}

	public void setPrpLcertifyImgService(PrpLcertifyImgService prpLcertifyImgService) {
		this.prpLcertifyImgService = prpLcertifyImgService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		if (endorseViewHelper == null) {
			return (EndorseViewHelper) ServiceFactory.getService("endorseViewHelper");
		}
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpClimitService getPrpClimitService() {
		if (prpClimitService == null) {
			return (PrpClimitService) ServiceFactory.getService("prpClimitService");
		}
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public PrpDclauseKindService getPrpDclauseKindService() {
		if (prpDclauseKindService == null) {
			return (PrpDclauseKindService) ServiceFactory.getService("prpDclauseKindService");
		}
		return prpDclauseKindService;
	}

	public void setPrpDclauseKindService(PrpDclauseKindService prpDclauseKindService) {
		this.prpDclauseKindService = prpDclauseKindService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		if (prpCitemKindService == null) {
			return (PrpCitemKindService) ServiceFactory.getService("prpCitemKindService");
		}
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public PrpDcodeService getPrpDcodeService() {
		if (prpDcodeService == null) {
			return (PrpDcodeService) ServiceFactory.getService("prpDcodeService");
		}
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpCmainService getPrpCmainService() {
		if (prpCmainService == null) {
			return (PrpCmainService) ServiceFactory.getService("prpCmainService");
		}
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		if (prpCcoinsService == null) {
			return (PrpCcoinsService) ServiceFactory.getService("prpCcoinsService");
		}
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpLexternalAgencyService getPrpLexternalAgencyService() {
		if (prpLexternalAgencyService == null) {
			return (PrpLexternalAgencyService) ServiceFactory.getService("prpLexternalAgencyService");
		}
		return prpLexternalAgencyService;
	}

	public void setPrpLexternalAgencyService(PrpLexternalAgencyService prpLexternalAgencyService) {
		this.prpLexternalAgencyService = prpLexternalAgencyService;
	}

	public PrpLagentService getPrpLagentService() {
		if (prpLagentService == null) {
			return (PrpLagentService) ServiceFactory.getService("prpLagentService");
		}
		return prpLagentService;
	}

	public void setPrpLagentService(PrpLagentService prpLagentService) {
		this.prpLagentService = prpLagentService;
	}

	public PrpDcarModelService getPrpDcarModelService() {
		if (prpDcarModelService == null) {
			return (PrpDcarModelService) ServiceFactory.getService("prpDcarModelService");
		}
		return prpDcarModelService;
	}

	public void setPrpDcarModelService(PrpDcarModelService prpDcarModelService) {
		this.prpDcarModelService = prpDcarModelService;
	}

	public PrpDriskService getPrpDriskService() {
		if (prpDriskService == null) {
			return (PrpDriskService) ServiceFactory.getService("prpDriskService");
		}
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PolicyService getPolicyService() {
		if (policyService == null) {
			return (PolicyService) ServiceFactory.getService("policyService");
		}
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public static List<PrpLdeductCond> getPrpLdeductCondList() {
		return prpLdeductCondList;
	}

	public static void setPrpLdeductCondList(List<PrpLdeductCond> prpLdeductCondList) {
		DwrInvokeDataAction.prpLdeductCondList = prpLdeductCondList;
	}

	public PrpLdisabilityLimitService getPrpLdisabilityLimitService() {
		return prpLdisabilityLimitService;
	}

	public void setPrpLdisabilityLimitService(PrpLdisabilityLimitService prpLdisabilityLimitService) {
		this.prpLdisabilityLimitService = prpLdisabilityLimitService;
	}

	public PrpCmainCargoService getPrpCmainCargoService() {
		return prpCmainCargoService;
	}

	public void setPrpCmainCargoService(PrpCmainCargoService prpCmainCargoService) {
		this.prpCmainCargoService = prpCmainCargoService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}
	
}
