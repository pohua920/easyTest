package com.sinosoft.claim.claim.web;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDclauseKindService;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.util.UIDeductCondAction;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDclauseKind;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.function.insutil.bl.facade.BLPubRateFacade;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 立案估损金额计算，
 * @Description 该类是将原来DAAClaimEditDWR.js函数calculateSumClaim改造的函数
 * @author 中科软
 */
public class ClaimLossAmountCalculateAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 代码翻译service */
	private CodeService codeService;
	/** 备案service */
	private RegistService registService;
	/** 理赔节点状态viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 查询PrpDclauseKind的service */
	private PrpDclauseKindService prpDclauseKindService;
	/** PRPCLIMIT跟踪接口service */
	private PrpClimitService prpClimitService;
	/** 备案service */
	private PrpLregistService prpLregistService;

	/**
	 * 获取汇率
	 * @author 中科软
	 * @date May 21, 2013 7:54:57 PM
	 * @return
	 * @throws Exception
	 */
	public String getExchangeRate() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String baseCurrency = request.getParameter("baseCurrency");
			String exchCurrency = request.getParameter("exchCurrency");
			double exchangeRate = BLPubRateFacade.getExchangeRate(baseCurrency, exchCurrency, new com.sinosoft.sysframework.common.datatype.DateTime(new Date()));
			jsonMap.put("exchangeRate", exchangeRate);
			jsonMap.put("resultFlag", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("resultFlag", false);
			jsonMap.put("exchangeRate", 1.0d);
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/**
	 * 计算估损金额
	 * @author 中科软
	 * @date May 21, 2013 8:37:11 PM
	 * @return
	 * @throws Exception
	 */
	public String getSumClaim() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		double sumClaim = 0.00d;
		try {
			String registNo = request.getParameter("registNo");
			String policyNo = request.getParameter("policyNo");
			PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
			String damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();
			String damageHour = prpLregist.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
//			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, null, null);
			List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
			PolicyDto policyDto = new PolicyDto();
			policyDto.setPrpCmain(prpCmain);
			policyDto.setPrpCitemCarList(prpCitemCarList);
//			policyDto.setPrpCitemKindList(prpCitemKindList);
			if (checkBeyondAmount(request, response, policyDto)) {// 校验
				String feeType = request.getParameter("feeType");
				String kindLoss = request.getParameter("kindLoss");
				String riskCode = request.getParameter("riskCode");
				String kindCode = request.getParameter("kindCode");
				String indemnityDutyRate = request.getParameter("indemnityDutyRate");
				String indemnityDuty = request.getParameter("indemnityDuty");
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
				dutydeductRate = getDutydeductRate(riskCode, kindCode, indemnityDuty, policyDto);
				sumClaim = 0;
				if ("Z".equals(feeType) || "RISKCODE_DAZ".equals(configCode)) {
					sumClaim = Double.parseDouble(kindLoss);
				} else {
					sumClaim = Double.parseDouble(kindLoss) * Double.parseDouble("100") / 100 * (1 - Double.parseDouble(dutydeductRate.toString()) / 100);
				}
				jsonMap.put("sumClaim", sumClaim);
				jsonMap.put("resultFlag", true);
			}
		} catch (Exception e) {
			if (e instanceof UserException) {
				jsonMap.put("errorMessage", ((UserException) e).getErrorMessage());
			} else {
				jsonMap.put("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			jsonMap.put("sumClaim", sumClaim);
			jsonMap.put("resultFlag", false);
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	private PolicyDto getPolicyDto(String policyNo, String registNo) throws Exception {
		SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
		if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(registNo)) != null) {
			PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
			if (prpLregist != null) {
				return this.getEndorseViewHelper().findForEndorBefore(policyNo, formatter10.format(prpLregist.getDamageStartDate()), prpLregist.getDamageStartHour());
			}
		}
		return null;
	}

	/**
	 * 取事故责任免赔率
	 * @param riskCode 险类
	 * @param kindCode 险别
	 * @param indemnityDuty 责任比例
	 * @param registNo 报案号
	 * @throws Exception
	 */
	private Double getDutydeductRate(String riskCode, String kindCode, String indemnityDuty, PolicyDto policyDto) throws Exception {
		Double dutydeductRate = new Double(0);
		SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
		UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
		Map<String, Double> map = new HashMap<String, Double>();
		PrpCitemCar prpCitemCar = new PrpCitemCar();
		String clauseType = "";
		String validDate = formatter10.format(policyDto.getPrpCmain().getStartDate());
		if (policyDto.getPrpCitemCarList().size() > 0) {
			prpCitemCar = (PrpCitemCar) policyDto.getPrpCitemCarList().get(0);
			if (prpCitemCar != null)
				clauseType = prpCitemCar.getClauseType();
		}
		map = uiDeductCondAction.getDeductibleRateOfAccident(riskCode, kindCode, "", clauseType, validDate);
		if (map != null) {
			dutydeductRate = map.get(indemnityDuty);
		}
		return dutydeductRate;
	}

	/**
	 * 判断立案估损金额是否超出限额
	 * @throws Exception
	 * @throws Exception
	 * @throws UserException
	 * @throws SQLException
	 */
	public boolean checkBeyondAmount(HttpServletRequest request, HttpServletResponse response, PolicyDto policyDto) throws Exception {
		double allKindLoss = Double.parseDouble(request.getParameter("allKindLoss"));
		String kindCode = request.getParameter("kindCode");
//		String kindName = URLDecoder.decode(request.getParameter("kindName"), "UTF-8");
		String policyNo = request.getParameter("policyNo");
		String riskCode = request.getParameter("riskCode");
		String indemnityDuty = request.getParameter("indemnityDuty");
		String indemnityDutyRate = DataUtils.nullToZero(request.getParameter("indemnityDutyRate"));
//		String kindLossSum = request.getParameter("kindLossSum");
		String damageDate = request.getParameter("damageDate");
		String startDate = request.getParameter("startDate");
		String limitType = "";
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		double amount = 0;
		double OMaxAmount = 0;
		// 险额校验应读取出险时保单信息
		if ("RISKCODE_DAZ".equals(configCode)) {// 交强险
			limitType = request.getParameter("limitType");
			if (limitType.equals("C")) {
				limitType = "G";
			}
			List<PrpClimit> limitList = this.getPrpClimitService().findPrpClimit(" policyNo='" + policyNo + "'", damageDate, startDate);
			if (limitList != null && limitList.size() > 0) {
				Iterator<PrpClimit> mapit = limitList.iterator();
				while (mapit.hasNext()) {
					PrpClimit prpClimit = mapit.next();
					if ("O".equals(limitType)) {
						if(prpClimit.getLimitFee()>OMaxAmount){
							OMaxAmount = prpClimit.getLimitFee();
						}
					}
					if ("O".equals(limitType) && "95".equals(prpClimit.getId().getLimitType())) {
						amount = prpClimit.getLimitFee();
						break;
					}
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
			List<PrpCitemKind> limitList = new ArrayList<PrpCitemKind>();
			if (policyDto != null && policyDto.getPrpCitemKindList() != null) {
				limitList = policyDto.getPrpCitemKindList();
			}
			Iterator<PrpCitemKind> it = limitList.iterator();
			Map<String, Double> map = new HashMap<String, Double>();
			while (it.hasNext()) {
				PrpCitemKind prpCitemKind = it.next();
				if (ConstantCodes.KINDCODE_D_A.equals(prpCitemKind.getKindCode())) {
//					double amountA = prpCitemKind.getAmount();
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
//						double deductible = itemKind.getValue();
						break;
					}
				}
			}
		}
		if("O".equals(limitType)){
			amount = OMaxAmount;
		}
//		String prpLclaimCurrencyName = URLDecoder.decode(request.getParameter("prpLclaimCurrencyName"), "UTF-8");
		if ("D".equals(this.getCodeService().translateRiskCodetoRiskType(riskCode)))
			allKindLoss = allKindLoss * (Double.valueOf(indemnityDutyRate) / 100);
		if (!("M".equals(kindCode) || "Y".equals(kindCode) || "F".equals(kindCode))) {
//			if (allKindLoss > amount) {
//				throw new UserException(0, -1, "估損金額計算", kindName + "估損金額之和超過限額(" + amount + ")" + prpLclaimCurrencyName);
//			} else if (Double.parseDouble(kindLossSum) > amount) {
//				if (!("RISKCODE_DAZ".equals(configCode) && "O".equals(limitType))) {
//					throw new UserException(0, -1, "估損金額計算", kindName + "估損金額(" + Double.parseDouble(kindLossSum) + ")超過限額(" + amount + ")" + prpLclaimCurrencyName);
//				}
//			}
		}
		return true;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
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

	public PrpDclauseKindService getPrpDclauseKindService() {
		return prpDclauseKindService;
	}

	public void setPrpDclauseKindService(PrpDclauseKindService prpDclauseKindService) {
		this.prpDclauseKindService = prpDclauseKindService;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}
}
