package com.tlg.prpins.bs.premCalculate.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bsh.Interpreter;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.premCalculate.CalPremBaseService;
import com.tlg.prpins.entity.FirDangerGrade;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremiumRate;
import com.tlg.prpins.entity.FirPremiumRate1;
import com.tlg.prpins.entity.FirPremiumRate2;
import com.tlg.prpins.entity.FirPremiumRate3;
import com.tlg.prpins.entity.PbDangerGrade;
import com.tlg.prpins.entity.PbPremcalcAdditionTerm;
import com.tlg.prpins.entity.PbPremiumRate;
import com.tlg.prpins.entity.PbPremiumRate0;
import com.tlg.prpins.entity.PbPremiumRate1;
import com.tlg.prpins.entity.PbPremiumRate2;
import com.tlg.prpins.entity.PbPremiumRate4;
import com.tlg.prpins.entity.PbPremiumRate5;
import com.tlg.prpins.entity.PbPremiumRate6;
import com.tlg.prpins.service.FirDangerGradeService;
import com.tlg.prpins.service.FirPremcalcTmpdtlService;
import com.tlg.prpins.service.FirPremiumRate1Service;
import com.tlg.prpins.service.FirPremiumRate2Service;
import com.tlg.prpins.service.FirPremiumRate3Service;
import com.tlg.prpins.service.FirPremiumRateService;
import com.tlg.prpins.service.PbDangerGradeService;
import com.tlg.prpins.service.PbPremcalcAdditionTermService;
import com.tlg.prpins.service.PbPremcalcCklistService;
import com.tlg.prpins.service.PbPremiumRate0Service;
import com.tlg.prpins.service.PbPremiumRate10Service;
import com.tlg.prpins.service.PbPremiumRate1Service;
import com.tlg.prpins.service.PbPremiumRate2Service;
import com.tlg.prpins.service.PbPremiumRate4Service;
import com.tlg.prpins.service.PbPremiumRate5Service;
import com.tlg.prpins.service.PbPremiumRate6Service;
import com.tlg.prpins.service.PbPremiumRate7Service;
import com.tlg.prpins.service.PbPremiumRate8Service;
import com.tlg.prpins.service.PbPremiumRate9Service;
import com.tlg.prpins.service.PbPremiumRateService;
import com.tlg.prpins.util.FirF01KindCodeUtil;
import com.tlg.prpins.util.FirF02KindCodeUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CalPremBaseServiceImpl implements CalPremBaseService{

	private FirDangerGradeService firDangerGradeService;
	private FirPremiumRateService firPremiumRateService;
	private FirPremiumRate1Service firPremiumRate1Service;
	private FirPremiumRate2Service firPremiumRate2Service;
	private FirPremiumRate3Service firPremiumRate3Service;
	private PbPremiumRateService pbPremiumRateService;
	private PbPremiumRate0Service pbPremiumRate0Service;
	private PbPremiumRate1Service pbPremiumRate1Service;
	private PbPremiumRate2Service pbPremiumRate2Service;
	private PbPremiumRate4Service pbPremiumRate4Service;
	private PbPremiumRate5Service pbPremiumRate5Service;
	private PbPremiumRate6Service pbPremiumRate6Service;
	private PbPremiumRate7Service pbPremiumRate7Service;
	private PbPremiumRate8Service pbPremiumRate8Service;
	private PbPremiumRate9Service pbPremiumRate9Service;
	private PbPremiumRate10Service pbPremiumRate10Service;
	private PbPremcalcAdditionTermService pbPremcalcAdditionTermService;
	private PbDangerGradeService pbDangerGradeService;
	private PbPremcalcCklistService pbPremcalcCklistService;
	private FirPremcalcTmpdtlService firPremcalcTmpdtlService; 

	
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getSurchargeRate(String riskcode, String kindcode,
			String channelType, String calcDate) throws SystemException,
			Exception {

		if(StringUtil.isSpace(riskcode)){
			throw new SystemException("無法取得險種代碼，無法查詢附加費用率資料。");
		}
		if(StringUtil.isSpace(kindcode)){
			throw new SystemException("無法取得險別代碼，無法查詢附加費用率資料。");
		}
		if(StringUtil.isSpace(channelType)){
			throw new SystemException("無法取得通路別，無法查詢附加費用率資料。");
		}
		if(StringUtil.isSpace(calcDate)){
			throw new SystemException("無法取得費率基準日，無法查詢附加費用率資料。");
		}
		
		Map params= new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("channelType", channelType);
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		Result result = firDangerGradeService.findFirDangerGradeByParams(params);
		if(result.getResObject() == null){
			String str = "查無" + FirF02KindCodeUtil.getName(kindcode) + "附加費用率資料，無法計算保費。";
			throw new SystemException(str);
		}
		ArrayList<FirDangerGrade> firDangerGradeList = (ArrayList<FirDangerGrade>)result.getResObject();
		FirDangerGrade firDangerGrade = firDangerGradeList.get(0);
		return firDangerGrade.getDangerGrade();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getFirInsRate(String riskcode, String kindcode,
			String tableType, String calcDate, String paraType, String para01,
			String para02, String para03, String para04) throws SystemException, Exception {
		if(StringUtil.isSpace(riskcode)){
			throw new SystemException("費率讀取錯誤，必填欄位-險種代碼無值。");
		}
		if(StringUtil.isSpace(kindcode)){
			throw new SystemException("費率讀取錯誤，必填欄位-險別代碼無值。");
		}
		if(StringUtil.isSpace(tableType)){
			throw new SystemException("費率讀取錯誤，必填欄位-參數類型無值。");
		}
		if(StringUtil.isSpace(calcDate)){
			throw new SystemException("費率讀取錯誤，必填欄位-費率基準日無值。");
		}
		if(StringUtil.isSpace(paraType) && StringUtil.isSpace(para01) && StringUtil.isSpace(para02) && StringUtil.isSpace(para03)){
			throw new SystemException(FirF02KindCodeUtil.getName(kindcode) + "費率讀取錯誤，非必填欄位最少要一個有值。");
		}
		
		Map params= new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		if(!StringUtil.isSpace(paraType)){
			params.put("paraType", paraType);
		}
		if(!StringUtil.isSpace(para01)){
			params.put("para01", para01);
		}
		if(!StringUtil.isSpace(para02)){
			params.put("para02", para02);
		}
		if(!StringUtil.isSpace(para03)){
			params.put("para03", para03);
		}
		if(!StringUtil.isSpace(para04)){
			params.put("para04", para04);
		}
		
		Result result = null;
		BigDecimal rate = null;
		if("0".equals(tableType)){
			result = firPremiumRateService.findFirPremiumRateByParams(params);
		}
		if("1".equals(tableType)){
			result = firPremiumRate1Service.findFirPremiumRate1ByParams(params);
		}
		if("2".equals(tableType)){
			result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
		}
		if("3".equals(tableType)){
			result = firPremiumRate3Service.findFirPremiumRate3ByParams(params);
		}
		if("4".equals(tableType)){
			result = pbPremiumRateService.findPbPremiumRateByParams(params);
		}
		if("5".equals(tableType)){
			result = pbPremiumRate1Service.findPbPremiumRate1ByParams(params);
		}
		if("6".equals(tableType)){
			result = pbPremiumRate2Service.findPbPremiumRate2ByParams(params);
		}
		if("7".equals(tableType)){
			result = pbPremiumRate4Service.findPbPremiumRate4ByParams(params);
		}
		if(result.getResObject() == null){
			
			String str1 = "";
			if("F02".equals(riskcode)){
				str1 = FirF02KindCodeUtil.getName(kindcode);
			}
			if("F01".equals(riskcode) || "F01_PB".equals(riskcode)){
				str1 = FirF01KindCodeUtil.getName(kindcode);
			}
			String str = "查無" + str1 + "費用率資料，無法計算保費。";
			throw new SystemException(str);
		}
		
		ArrayList<?> premiumRateList = (ArrayList<?>)result.getResObject();
		if(premiumRateList.size() > 1){
			String str = "費率資料異常(多筆)，無法計算保費。";
			throw new SystemException(str);
		}
		if("0".equals(tableType)){
			FirPremiumRate firPremiumRate = (FirPremiumRate)premiumRateList.get(0);
			rate = firPremiumRate.getParaValue();
			return rate;
		}
		if("1".equals(tableType)){
			FirPremiumRate1 firPremiumRate1 = (FirPremiumRate1)premiumRateList.get(0);
			rate = firPremiumRate1.getParaValue();
			return rate;
		}
		if("2".equals(tableType)){
			FirPremiumRate2 firPremiumRate2 = (FirPremiumRate2)premiumRateList.get(0);
			rate = firPremiumRate2.getParaValue();
			return rate;
		}
		if("3".equals(tableType)){
			FirPremiumRate3 firPremiumRate3 = (FirPremiumRate3)premiumRateList.get(0);
			rate = firPremiumRate3.getParaValue();
			return rate;
		}
		if("4".equals(tableType)){
			PbPremiumRate pbPremiumRate = (PbPremiumRate)premiumRateList.get(0);
			rate = pbPremiumRate.getParaValue();
			return rate;
		}
		if("5".equals(tableType)){
			PbPremiumRate1 pbPremiumRate1 = (PbPremiumRate1)premiumRateList.get(0);
			rate = pbPremiumRate1.getParaValue();
			return rate;
		}
		if("6".equals(tableType)){
			PbPremiumRate2 pbPremiumRate2 = (PbPremiumRate2)premiumRateList.get(0);
			rate = pbPremiumRate2.getParaValue();
			return rate;
		}
		if("7".equals(tableType)){
			PbPremiumRate4 pbPremiumRate4 = (PbPremiumRate4)premiumRateList.get(0);
			rate = pbPremiumRate4.getParaValue();
			return rate;
		}
		return rate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getPbInsRate(String riskcode, String kindcode,
			String tableType, String calcType, String calcDate, String paraType, String para01,
			String para02, String para03, String para04) throws SystemException, Exception {
		if(StringUtil.isSpace(riskcode)){
			throw new SystemException("費率讀取錯誤，必填欄位-險種代碼無值。");
		}
		if(StringUtil.isSpace(kindcode)){
			throw new SystemException("費率讀取錯誤，必填欄位-險別代碼無值。");
		}
		if(StringUtil.isSpace(tableType)){
			throw new SystemException("費率讀取錯誤，必填欄位-參數類型無值。");
		}
		if(StringUtil.isSpace(calcDate)){
			throw new SystemException("費率讀取錯誤，必填欄位-費率基準日無值。");
		}
//		if(StringUtil.isSpace(paraType) && StringUtil.isSpace(para01) && StringUtil.isSpace(para02) 
//				&& StringUtil.isSpace(para03) && StringUtil.isSpace(para04)){
//			throw new SystemException(PbKindCodeUtil.getName(kindcode) + "費率讀取錯誤，非必填欄位最少要一個有值。");
//		}
		
		Map params= new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("calcType", calcType);
		if(!StringUtil.isSpace(paraType)){
			params.put("paraType", paraType);
		}
		if(!StringUtil.isSpace(para01)){
			params.put("para01", para01);
		}
		if(!StringUtil.isSpace(para02)){
			params.put("para02", para02);
		}
		if(!StringUtil.isSpace(para03)){
			params.put("para03", para03);
		}
		if(!StringUtil.isSpace(para04)){
			params.put("para04", para04);
		}
		
		Result result = null;
		if("0".equals(tableType)){
			result = pbPremiumRate0Service.findPbPremiumRate0ByParams(params);
		}
		if("1".equals(tableType)){
			result = pbPremiumRate5Service.findPbPremiumRate5ByParams(params);
		}
		if("2".equals(tableType)){
			result = pbPremiumRate6Service.findPbPremiumRate6ByParams(params);
		}
		if("3".equals(tableType)){
			result = pbPremiumRate4Service.findPbPremiumRate4ByParams(params);
		}
		if("7".equals(tableType)){
			result = pbPremcalcAdditionTermService.findPbPremcalcAdditionTermByParams(params);
		}
		if("8".equals(tableType)){
			result = pbDangerGradeService.findPbDangerGradeByParams(params);
		}
		if(result.getResObject() == null){
			String str = "";
			if("0".equals(tableType)){
				if("QUERY_MAX".equals(paraType)){
					str = "核保最高加減費%";
				}
				if("ACT_BASE_PURE_PREMIUM".equals(paraType) || "BUS_BASE_PURE_PREMIUM".equals(paraType)){
					str = "基本純保費";
				}
				
				if("BUS_BASE_SCALE_COE".equals(paraType)){
					str = "營業處所，基本規模係數";
				}
				if("ACT_BASE_SCALE_COE".equals(paraType)){
					str = "活動事件_基本規模係數";
				}
			}
			if("2".equals(tableType)){
				str = "自負額調整係數";
			}
			if("3".equals(tableType)){
				str = "公共意外責任險高保額係數錯誤";
			}
			if("7".equals(tableType)){
				str = "附加條款加費及加費係數";
			}
			if("8".equals(tableType)){
				str = "附加費用率";
			}
			str = "無法取得" + str + "，無法計算保費。";
			throw new SystemException(str);
		}
		
		ArrayList<?> premiumRateList = (ArrayList<?>)result.getResObject();
		if(premiumRateList.size() > 1){
			String str = "費率資料異常(多筆)，無法計算保費。";
			throw new SystemException(str);
		}
		Map map = new HashMap();
		if("0".equals(tableType)){
			PbPremiumRate0 pbPremiumRate0 = (PbPremiumRate0)premiumRateList.get(0);
			if("BUS_BASE_SCALE_COE".equals(paraType) || "ACT_BASE_SCALE_COE".equals(paraType)){
				map.put("PARA_01", new BigDecimal(pbPremiumRate0.getPara01()));
			}
			if("QUERY_MAX".equals(paraType)){
				map.put("PARA_VALUE", pbPremiumRate0.getParaValue());
			}
			if("BUS_BASE_PURE_PREMIUM".equals(paraType) || "ACT_BASE_PURE_PREMIUM".equals(paraType)){
				map.put("PARA_01", pbPremiumRate0.getParaValue());
			}
			if("BASE_AMOUNT".equals(paraType)){
				map.put("PARA_01", new BigDecimal(pbPremiumRate0.getPara01()));
				map.put("PARA_02", new BigDecimal(pbPremiumRate0.getPara02()));
				map.put("PARA_03", new BigDecimal(pbPremiumRate0.getPara03()));
			}
			return map;
		}
		if("1".equals(tableType)){
			PbPremiumRate5 pbPremiumRate5 = (PbPremiumRate5)premiumRateList.get(0);
			map.put("PARA_01", new BigDecimal(pbPremiumRate5.getPara01()));
			map.put("PARA_03", new BigDecimal(pbPremiumRate5.getPara03()));
			map.put("PARA_VALUE_01", pbPremiumRate5.getParaValue01());
			map.put("PARA_VALUE_02", pbPremiumRate5.getParaValue02());
			return map;
		}
		if("2".equals(tableType)){
			PbPremiumRate6 pbPremiumRate6 = (PbPremiumRate6)premiumRateList.get(0);
			map.put("PARA_VALUE_02", pbPremiumRate6.getParaValue02());
			return map;
		}
		if("3".equals(tableType)){
			PbPremiumRate4 pbPremiumRate4 = (PbPremiumRate4)premiumRateList.get(0);
			map.put("PARA_VALUE", pbPremiumRate4.getParaValue());
			return map;
		}
		if("7".equals(tableType)){
			PbPremcalcAdditionTerm pbPremcalcAdditionTerm = (PbPremcalcAdditionTerm)premiumRateList.get(0);
			map.put("ADD_PROPORTION", pbPremcalcAdditionTerm.getAddProportion());
			map.put("ADD_FEE", pbPremcalcAdditionTerm.getAddFee());
			return map;
		}
		if("8".equals(tableType)){
			PbDangerGrade pbDangerGrade = (PbDangerGrade)premiumRateList.get(0);
			map.put("DANGER_GRADE", pbDangerGrade.getDangerGrade());
			return map;
		}
		return map;
	}
	
	public BigDecimal beanShellCalculate(String formula, Map<String,BigDecimal> paramsMap) throws SystemException, Exception{
		
		//beanShell.set("one",BigDecimal.ONE);
		//beanShell.set("hundred",new BigDecimal(100));
		//beanShell.set("BigDecimal.ROUND_HALF_UP",BigDecimal.ROUND_HALF_UP);
		System.out.println("formula : " + formula);
		Interpreter interpreter = new Interpreter();
		for (String key : paramsMap.keySet()) {
            BigDecimal b = paramsMap.get(key);
            System.out.println(key + " : " + b.toString());
            interpreter.set(key, b);
        }
		interpreter.eval("import java.math.BigDecimal;");
		
		Object amount = (Object)interpreter.eval(formula);
		
		if(amount == null){
			System.out.println("prem : null");
			throw new SystemException("保費計算發生錯誤");
		}else{
			System.out.println("prem : " + amount.toString());
		}
//		return new BigDecimal(amount.toString()).setScale(0, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(amount.toString());
	}
	
	/**
	 * 由出單保費回推純保費
	 * @param prem
	 * @param surchargeRate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal calculatePurePrem(BigDecimal prem, BigDecimal surchargeRate) throws SystemException, Exception{
		
		BigDecimal purePrem = prem.multiply(BigDecimal.ONE.subtract(surchargeRate)).setScale(0, BigDecimal.ROUND_HALF_UP);
			
		return purePrem;
	}
	
	/**
	 * 由純保費推出單保費
	 * @param prem
	 * @param surchargeRate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal calculatePrem(BigDecimal purePrem, BigDecimal surchargeRate) throws SystemException, Exception{
		
		BigDecimal prem = purePrem.divide(BigDecimal.ONE.subtract(surchargeRate), 10, BigDecimal.ROUND_HALF_UP);
			
		return prem.setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal[] getFb1PremcalcCkList(BigDecimal oidFirPremcalcTmp, String calcDate, String riskcode, String kindcode) throws SystemException, Exception {
		/**
		 * TMP_核保調整係數_火險主險 = 0
		 * TMP_核保調整係數_非天災附加險 = 0
		 * TMP_核保調整係數_天災附加險 = 0
		 */
		BigDecimal[] resultAry = { BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		Map params = new HashMap();
		params.put("cklistResult", "1");
		params.put("oidFirPremcalcTmp", oidFirPremcalcTmp);
		int clrec = pbPremcalcCklistService.countPbPremcalcCklist(params);
		if(clrec == 0){
			return resultAry;
		}
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "F01_CKLIST_SCORE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("numPara02", clrec);
		Result result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			return resultAry;
		}
		ArrayList<FirPremiumRate2> firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
		for(int i = 0 ; i < firPremiumRate2List.size() ; i++){
			FirPremiumRate2 firPremiumRate2 = firPremiumRate2List.get(i);
			BigDecimal paraValue = firPremiumRate2.getParaValue();
			if("1".equals(firPremiumRate2.getPara01())){
				resultAry[0] = paraValue;
			}
			if("2".equals(firPremiumRate2.getPara01())){
				resultAry[1] = paraValue;
			}
			if("3".equals(firPremiumRate2.getPara01())){
				resultAry[2] = paraValue;
			}
		}
		return resultAry;
	}
	
	@Override
	public BigDecimal getFb1DiscountRate(FirPremcalcTmp firPremcalcTmp, String riskcode, String kindcode, BigDecimal oidFirPremcalcTmp,
			String calcDate) throws SystemException, Exception {
		BigDecimal rate = BigDecimal.ZERO;
		if("WEB_CALC03".equals(firPremcalcTmp.getSourceUser())){
			int count = firPremcalcTmpdtlService.countDiscountNum(oidFirPremcalcTmp, calcDate, null);
			if(count == 0){
				return rate;
			}
			if(count >= 2){
				count = firPremcalcTmpdtlService.countDiscountNum(oidFirPremcalcTmp, calcDate, "Y");
				if(count == 0){
					return rate;
				}
				rate = this.getFirInsRate("F01", "FB1", "1", calcDate, "F01_EXT_DISCOUNT", String.valueOf(count), "", "", "");
			}
		}
		return rate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public double getDeductionRate(String riskcode, String kindcode,
			String calcDate, String para01, String para02, String para03)
			throws SystemException, Exception {
		
		double deductionRate = -1;
		
		String msg = checkDeductionValue(riskcode, kindcode, calcDate, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		if(StringUtil.isSpace(para02)){
			para02 = "0";
		}
		if(StringUtil.isSpace(para03)){
			para03 = "0";
		}
		
		//情境一：處理無自負額
		if("0".equals((para02)) && "0".equals(para03)){
			return 0;
		}
		//情境二：處理只有賠償比率
		if(Integer.parseInt(para02) > 0 && "0".equals(para03)){
			return Double.parseDouble(para02) / 100;
		}
		//先處理自負額金額，再判斷情境三、四、五。
		if(!"0".equals(para03)){
			double dctRate = 0;
			double deductionAmt = Double.parseDouble(para03.substring(3));
			double deductionRateA = deductionAmt / Double.parseDouble(para01);
			
			Map params = new HashMap();
			params.put("riskcode", "F01");
			params.put("kindcode", "FB1");
			params.put("validFalg", "1");
			params.put("calcDate", calcDate);
			params.put("paraType", "F01_DEDUCTION");
			
			Double deduction = firPremiumRate3Service.findFirPremiumRate3ForDeduction(params);
			if(deduction == null){
				throw new SystemException("商火自負額扣減率處理錯誤，查無自負額起算點。");
			}
			//自負額金額 >= 自負額起算點，至費率表取得對應扣減率。
			if(deductionAmt >= deduction.doubleValue()){
				params = new HashMap();
				params.put("riskcode", "F01");
				params.put("kindcode", "FB1");
				params.put("validFalg", "1");
				params.put("calcDate", calcDate);
				params.put("paraType", "F01_DEDUCTION");
				params.put("para01", deductionAmt);
				params.put("lePara02", deductionRateA);
				params.put("gtPara03", deductionRateA);
				Result result = firPremiumRate3Service.findFirPremiumRate3ByParams(params);
				if(result.getResObject() == null){
					throw new SystemException("商火自負額扣減率處理錯誤，查無自負額設定資料。");
				}
				ArrayList<FirPremiumRate3> firPremiumRate3List = (ArrayList<FirPremiumRate3>)result.getResObject();
				if(firPremiumRate3List.size() > 1){
					throw new SystemException("商火自負額扣減率處理錯誤，資料異常(多筆)。");
				}
				FirPremiumRate3 firPremiumRate3 = firPremiumRate3List.get(0);
				dctRate = firPremiumRate3.getParaValue().doubleValue();
			}
			//情境三：只有自負額金額，無自負額比例。
			if("0".equals(para02)){
				return dctRate;
			}
			//情境四：有自負額比例，且有最低賠償金額。
			if(Double.parseDouble(para02) > 0 && "MIN".equals(para03.substring(0,3))){
				if(Double.parseDouble(para02) / 100 > dctRate){
					deductionRate = Double.parseDouble(para02) / 100;
				}else{
					deductionRate = dctRate;
				}
				return deductionRate;	
			}
			//情境五：有自負額比例，且有最高賠償金額。
			if(Double.parseDouble(para02) > 0 && "MAX".equals(para03.substring(0,3))){
				if(Double.parseDouble(para02) / 100 < dctRate){
					deductionRate = Double.parseDouble(para02) / 100;
				}else{
					deductionRate = dctRate;
				}
				return deductionRate;
			}
			if(deductionRate == -1){
				throw new SystemException("商火自負額扣減率處理錯誤，前端專案險種自負額設定異常，無法找到對應處理。");
			}
		}
		return deductionRate;
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkDeductionValue(String riskcode, String kindcode,
			String calcDate, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "費率基準日", "總保額"};
		String valueAry[] =  {riskcode, kindcode, calcDate, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "FB1商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	private String checkValue(String[] keyAry, String[] valueAry) throws Exception{
		String msg = "";
		for(int i = 0 ; i < keyAry.length ; i++){
			if(StringUtil.isSpace(keyAry[i]))
				continue;
			
			if(StringUtil.isSpace(valueAry[i])){
				if(!StringUtil.isSpace(msg)){
					msg += "、" + keyAry[i];
				}else{
					msg = keyAry[i];
				}
			}
		}
		return msg;
	}

	public FirDangerGradeService getFirDangerGradeService() {
		return firDangerGradeService;
	}

	public void setFirDangerGradeService(FirDangerGradeService firDangerGradeService) {
		this.firDangerGradeService = firDangerGradeService;
	}

	public FirPremiumRateService getFirPremiumRateService() {
		return firPremiumRateService;
	}

	public void setFirPremiumRateService(FirPremiumRateService firPremiumRateService) {
		this.firPremiumRateService = firPremiumRateService;
	}

	public FirPremiumRate1Service getFirPremiumRate1Service() {
		return firPremiumRate1Service;
	}

	public void setFirPremiumRate1Service(
			FirPremiumRate1Service firPremiumRate1Service) {
		this.firPremiumRate1Service = firPremiumRate1Service;
	}

	public FirPremiumRate2Service getFirPremiumRate2Service() {
		return firPremiumRate2Service;
	}

	public void setFirPremiumRate2Service(
			FirPremiumRate2Service firPremiumRate2Service) {
		this.firPremiumRate2Service = firPremiumRate2Service;
	}

	public FirPremiumRate3Service getFirPremiumRate3Service() {
		return firPremiumRate3Service;
	}

	public void setFirPremiumRate3Service(
			FirPremiumRate3Service firPremiumRate3Service) {
		this.firPremiumRate3Service = firPremiumRate3Service;
	}

	public PbPremiumRateService getPbPremiumRateService() {
		return pbPremiumRateService;
	}

	public void setPbPremiumRateService(PbPremiumRateService pbPremiumRateService) {
		this.pbPremiumRateService = pbPremiumRateService;
	}

	public PbPremiumRate1Service getPbPremiumRate1Service() {
		return pbPremiumRate1Service;
	}

	public void setPbPremiumRate1Service(PbPremiumRate1Service pbPremiumRate1Service) {
		this.pbPremiumRate1Service = pbPremiumRate1Service;
	}

	public PbPremiumRate2Service getPbPremiumRate2Service() {
		return pbPremiumRate2Service;
	}

	public void setPbPremiumRate2Service(PbPremiumRate2Service pbPremiumRate2Service) {
		this.pbPremiumRate2Service = pbPremiumRate2Service;
	}

	public PbPremiumRate4Service getPbPremiumRate4Service() {
		return pbPremiumRate4Service;
	}

	public void setPbPremiumRate4Service(PbPremiumRate4Service pbPremiumRate4Service) {
		this.pbPremiumRate4Service = pbPremiumRate4Service;
	}

	public PbPremiumRate0Service getPbPremiumRate0Service() {
		return pbPremiumRate0Service;
	}

	public void setPbPremiumRate0Service(PbPremiumRate0Service pbPremiumRate0Service) {
		this.pbPremiumRate0Service = pbPremiumRate0Service;
	}

	public PbPremiumRate5Service getPbPremiumRate5Service() {
		return pbPremiumRate5Service;
	}

	public void setPbPremiumRate5Service(PbPremiumRate5Service pbPremiumRate5Service) {
		this.pbPremiumRate5Service = pbPremiumRate5Service;
	}

	public PbPremiumRate6Service getPbPremiumRate6Service() {
		return pbPremiumRate6Service;
	}

	public void setPbPremiumRate6Service(PbPremiumRate6Service pbPremiumRate6Service) {
		this.pbPremiumRate6Service = pbPremiumRate6Service;
	}

	public PbPremiumRate7Service getPbPremiumRate7Service() {
		return pbPremiumRate7Service;
	}

	public void setPbPremiumRate7Service(PbPremiumRate7Service pbPremiumRate7Service) {
		this.pbPremiumRate7Service = pbPremiumRate7Service;
	}

	public PbPremiumRate8Service getPbPremiumRate8Service() {
		return pbPremiumRate8Service;
	}

	public void setPbPremiumRate8Service(PbPremiumRate8Service pbPremiumRate8Service) {
		this.pbPremiumRate8Service = pbPremiumRate8Service;
	}

	public PbPremiumRate9Service getPbPremiumRate9Service() {
		return pbPremiumRate9Service;
	}

	public void setPbPremiumRate9Service(PbPremiumRate9Service pbPremiumRate9Service) {
		this.pbPremiumRate9Service = pbPremiumRate9Service;
	}

	public PbPremiumRate10Service getPbPremiumRate10Service() {
		return pbPremiumRate10Service;
	}

	public void setPbPremiumRate10Service(
			PbPremiumRate10Service pbPremiumRate10Service) {
		this.pbPremiumRate10Service = pbPremiumRate10Service;
	}

	public PbPremcalcAdditionTermService getPbPremcalcAdditionTermService() {
		return pbPremcalcAdditionTermService;
	}

	public void setPbPremcalcAdditionTermService(
			PbPremcalcAdditionTermService pbPremcalcAdditionTermService) {
		this.pbPremcalcAdditionTermService = pbPremcalcAdditionTermService;
	}

	public PbDangerGradeService getPbDangerGradeService() {
		return pbDangerGradeService;
	}

	public void setPbDangerGradeService(PbDangerGradeService pbDangerGradeService) {
		this.pbDangerGradeService = pbDangerGradeService;
	}

	public PbPremcalcCklistService getPbPremcalcCklistService() {
		return pbPremcalcCklistService;
	}

	public void setPbPremcalcCklistService(
			PbPremcalcCklistService pbPremcalcCklistService) {
		this.pbPremcalcCklistService = pbPremcalcCklistService;
	}

	public FirPremcalcTmpdtlService getFirPremcalcTmpdtlService() {
		return firPremcalcTmpdtlService;
	}

	public void setFirPremcalcTmpdtlService(
			FirPremcalcTmpdtlService firPremcalcTmpdtlService) {
		this.firPremcalcTmpdtlService = firPremcalcTmpdtlService;
	}

}
