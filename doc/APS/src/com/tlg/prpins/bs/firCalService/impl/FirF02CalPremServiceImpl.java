package com.tlg.prpins.bs.firCalService.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bsh.EvalError;
import bsh.Interpreter;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.firCalService.FirBaseUtilService;
import com.tlg.prpins.bs.firCalService.FirF02CalPremService;
import com.tlg.prpins.bs.premCalculate.CalPremBaseService;
import com.tlg.prpins.entity.FirPremiumRate2;
import com.tlg.prpins.entity.PrpdBaseRate;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.FirPremiumRate2Service;
import com.tlg.prpins.service.PrpdBaseRateService;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.prpins.util.FirF02Formula;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirF02CalPremServiceImpl implements FirF02CalPremService{

	private FirPremiumRate2Service firPremiumRate2Service;
	private FirBaseUtilService firBaseUtilService;
	private PrpdBaseRateService prpdBaseRateService; 
	private PrpdPropStructService prpdPropStructService;
	private CalPremBaseService calPremBaseService;

	
	@SuppressWarnings("unchecked")
	@Override
	public Map getFR2Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checktFR2Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		String selectTypeList[] = {"EarthquakePrem", "MaxAmount"};
		Map map = firBaseUtilService.getCalParamDataByAry(selectTypeList, calcDate);
		if(map == null){
			throw new SystemException("查無地震險費率資料，無法計算保費。");
		}
		String earthquakePrem = (String)map.get("EarthquakePrem");
		String maxAmount = (String)map.get("MaxAmount");
		
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("earthquakePrem", new BigDecimal(earthquakePrem));
		formulaParamMap.put("maxAmount", new BigDecimal(maxAmount));
		formulaParamMap.put("para01", new BigDecimal(para01));
		//WK_出單保費 - 未四捨五入
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF02Formula.FR2, formulaParamMap);
		//純保費(已四捨五入)
		BigDecimal purePrem = calPremBaseService.calculatePurePrem(prem, surchargeRate);
		//保費四捨五入
		prem = prem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//E.	費率回推處理
		//		WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//		WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getFR3Prem(String calcDate, String channelType, String riskcode, String kindcode, String paraType, 
			String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception {
		
		
		//檢查欄位是否輸入
		String msg = checktFR3Value(riskcode, kindcode, paraType, para01, para02, para03, para04, para05, para06);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//TODO B.	判斷建物等級，待科長確認。 	TMP_建物等級 = ???
		Map params = new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("wallno", para02);
		params.put("roofno", para03);
		params.put("calcDate", calcDate);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("查無建物等級，無法計算保費。");
		}
		ArrayList<PrpdPropStruct> prpdPropStructList = (ArrayList<PrpdPropStruct>)result.getResObject();
		PrpdPropStruct prpdPropStruct = (PrpdPropStruct)prpdPropStructList.get(0);
		String structureno = prpdPropStruct.getStructureno();
		String level1 = "01,02,03,27,28";
		if(level1.indexOf(para02) != -1 && Integer.parseInt(para04) >= 14){
			structureno = "1";
		}
		if(level1.indexOf(para02) != -1 && Integer.parseInt(para04) < 14){
			structureno = "2";
		}
		//C.	讀取火險費率資料-基本費率BASE
		
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		//TODO 建築等級???
		params.put("buildinglevel", structureno);
		params.put("validflag", "1");
		params.put("flag", "1");
		params.put("calcDate", calcDate);
		if("1".equals(paraType)){
			params.put("statisticsnum", "A0001A8");
		}
		if("2".equals(paraType)){
			params.put("statisticsnum", "A0002A5");
		}
		if("3".equals(paraType)){
			params.put("statisticsnum", "A0003A2");
		}
		if("RFB".equals(kindcode)){
			params.put("item", para06);
		}
		result = prpdBaseRateService.findPrpdBaseRateByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("住宅火險無法取得基本費率資料，無法計算保費。");
		}
		ArrayList<PrpdBaseRate> prpdBaseRateList = (ArrayList<PrpdBaseRate>)result.getResObject();
		PrpdBaseRate prpdBaseRate = (PrpdBaseRate)prpdBaseRateList.get(0);
		String baseRate = prpdBaseRate.getBaserate();
		if(StringUtil.isSpace(baseRate)){
			throw new SystemException("住宅火險無法取得基本費率資料，無法計算保費。");
		}
		
		//D.	讀取費率資料-高樓加費HIGH
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("paraType", "HIGHRISEFEE");
		params.put("para04", para04);
		result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("住宅火險無法取得高樓加費資料，無法計算保費。");
		}
		ArrayList<FirPremiumRate2> firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
		FirPremiumRate2 firPremiumRate2 = (FirPremiumRate2)firPremiumRate2List.get(0);
		BigDecimal highBuildFee = firPremiumRate2.getParaValue();
		if(highBuildFee == null){
			throw new SystemException("住宅火險無法取得高樓加費資料，無法計算保費。");
		}
		//讀取火險費率資料-營業加費
		//若接收參數.PARA_05 = ‘N’或無值，TMP_營業加費 = 0。 
		BigDecimal businessFee = BigDecimal.ZERO;
		if("Y".equalsIgnoreCase(para05)){
			params.clear();
			params.put("riskcode", riskcode);
			params.put("kindcode", kindcode);
			params.put("validFalg", "1");
			params.put("calcDate", calcDate);
			params.put("paraType", "OPERATINGFEE");
			params.put("para04", para04);
			result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
			if(result.getResObject() == null){
				throw new SystemException("住宅火險無法取得營業加費資料，無法計算保費。");
			}
			firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
			firPremiumRate2 = (FirPremiumRate2)firPremiumRate2List.get(0);
			businessFee = firPremiumRate2.getParaValue();
			if(businessFee == null){
				throw new SystemException("住宅火險無法取得營業加費資料，無法計算保費。");
			}
		}
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		Map formulaParamMap = new HashMap();
		
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("baseRate", new BigDecimal(baseRate));
		formulaParamMap.put("highBuildFee", highBuildFee);
		formulaParamMap.put("businessFee", businessFee);
		formulaParamMap.put("surchargeRate", surchargeRate);
		//出單保費(未四捨五入)
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF02Formula.FR3, formulaParamMap);
		//純保費 WK_純保費 = WK_出單保費 * (1 – WK_附加費用率) –四捨五入至整數位
		BigDecimal purePrem = prem.multiply(BigDecimal.ONE.subtract(surchargeRate)).setScale(0, BigDecimal.ROUND_HALF_UP);
		//保費四捨五入
		prem = prem.setScale(0, BigDecimal.ROUND_HALF_UP);
//		//純保費(未四捨五入)
//		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.FR3, formulaParamMap);
//		//出單保費(已四捨五入)
//		BigDecimal prem = calculatePrem(purePrem, surchargeRate);
		
		//TMP_出單費率 = BASE.PARA_VALUE * (1 + HIGH.PARA_VALUE + TMP_營業加費) / (1 – WK_附加費用率) –-四捨五入至小數第三位
		
		
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//E.	費率回推處理
		//		WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//		WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = (prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = (purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
	
		Map premMap = new HashMap();
		premMap.put("firBaseRate", new BigDecimal(baseRate));
		premMap.put("firHigh", highBuildFee);
		premMap.put("firOperating", businessFee);
		//TODO 建物等級
		premMap.put("firStructure", structureno);
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		//存入火險保額
		premMap.put("amount", new BigDecimal(para01));
		
		return premMap;
	}
	
	
	@Override
	public Map getRFAPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception {
		
		return this.getFR2Prem(calcDate, channelType, riskcode, "RFA", para01);
	}
	
	@Override
	public Map getRFBPrem(String calcDate, String channelType, String riskcode, String kindcode, String paraType, 
			String para01, String para02, String para03, String para04, String para05, String para06) throws SystemException, Exception {
		
		return this.getFR3Prem(calcDate, channelType, riskcode, "RFB", paraType, 
				para01, para02, para03, para04, para05, para06);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRFCPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checktRFCValue(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//純保費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para01, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//費率回推處理
		//WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRFDPrem(String calcDate, String channelType, String riskcode,
			String kindcode, String para01, String para02, String para03)
			throws SystemException, Exception {

		//檢查欄位是否輸入
		String msg = checktRFDValue(riskcode, kindcode, para01, para02, para03);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//附加費用率
		BigDecimal surchargeRate = calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//純保費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "3", calcDate, "", para01, para02, para03, "");
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//費率回推處理
		//WK_出單費率 = WK_出單保費 / 接收參數.PARA_03 * 1000  --四捨五入至小數第四位
		//WK_純保費率 = WK_純保費 / 接收參數.PARA_03 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para03), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para03), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRFEPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05) throws SystemException, Exception {

		//檢查欄位是否輸入
		String msg = checktRFEValue(riskcode, kindcode, para01, para02, para03, para04, para05);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//CPS賠償金基本純保險費
		BigDecimal cpsPurePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "CPS-AMOUNT", para01, "", "", "");
		//CPS賠償金基本純保險費
		BigDecimal dctRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "DEDUCTION", para02, "", "", "");
		//CSL慰問金純保險費
		BigDecimal cslPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "3", calcDate, "CSL-AMOUNT", para03, para04, para05, "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("cpsPurePrem", cpsPurePrem);
		formulaParamMap.put("dctRate", dctRate);
		formulaParamMap.put("cslPrem", cslPrem);
		//純保費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RFE, formulaParamMap);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//費率回推處理
		//WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		return premMap;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF01Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02,
			String para03) throws SystemException, Exception {

		//檢查欄位是否輸入
		String msg = checktRF01Value(riskcode, kindcode);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		if(StringUtil.isSpace(para01) && StringUtil.isSpace(para02) && StringUtil.isSpace(para03)){
			throw new SystemException("RF01商品最少要有一個保額。");
		}
		
		//BASE基本事故純保險費
		BigDecimal basePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "BASE-AMOUNT", para01, "", "", "");
		//EQ地震事故純保險費
		BigDecimal eqPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "EQ-AMOUNT", para02, "", "", "");
		//TB颱風及洪水事故純保險費
		BigDecimal tbPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "TB-AMOUNT", para03, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("basePrem", basePrem);
		formulaParamMap.put("eqPrem", eqPrem);
		formulaParamMap.put("tbPrem", tbPrem);
		//純保費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RF01, formulaParamMap);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 * 費率回推處理
		 * TMP_總保額 = 接收參數.PARA_01 + 接收參數.PARA_02 + 接收參數.PARA_03
		 * WK_出單費率 = WK_出單保費 / TMP_總保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_總保額 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal totalAmount = new BigDecimal(para01).add(new BigDecimal(para02)).add(new BigDecimal(para03));
		BigDecimal premRate = prem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF02Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01)
			throws SystemException, Exception {
		//檢查欄位是否輸入
		String msg = checktRF02Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//純保險費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para01, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//費率回推處理
		//WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem.setScale(0, BigDecimal.ROUND_HALF_UP));
		premMap.put("premium", prem.setScale(0, BigDecimal.ROUND_HALF_UP));
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF03Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01)
			throws SystemException, Exception {
		//檢查欄位是否輸入
		String msg = checktRF03Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//純保險費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para01, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		//費率回推處理
		//WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		//WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF04Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02)
			throws SystemException, Exception {

		//檢查欄位是否輸入
		String msg = checktRF04Value(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//HS房屋跌價補償基本純費率
		BigDecimal hsRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "PRATE", "", "", "", "");
		//-CL清理費用純保險費
		BigDecimal clPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para02, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
				
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("hsRate", hsRate);
		formulaParamMap.put("clPrem", clPrem);
		//純保險費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RF04, formulaParamMap);		
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 * 費率回推處理
		 * TMP_總保額 = 接收參數.PARA_01 + 接收參數.PARA_02
		 * WK_出單費率 = WK_出單保費 / TMP_總保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_總保額 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal totalAmount = new BigDecimal(para01).add(new BigDecimal(para02));
		BigDecimal premRate = prem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		
		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF05Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02)
			throws SystemException, Exception {
		//檢查欄位是否輸入
		String msg = checktRF05Value(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//HS房屋租金補償基本純費率
		BigDecimal hsRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "PRATE", "", "", "", "");
		//CL清理費用純保險費
		BigDecimal clPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para02, "", "", "");
		//MONTH約定月數
		BigDecimal month = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "MONTH", "", "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
				
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("hsRate", hsRate);
		formulaParamMap.put("clPrem", clPrem);
		formulaParamMap.put("month", month);
		//純保險費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RF05, formulaParamMap);		
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 * 費率回推處理
		 * TMP_總保額 = 接收參數.PARA_01 + 接收參數.PARA_02
		 * WK_出單費率 = WK_出單保費 / TMP_總保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_總保額 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal totalAmount = new BigDecimal(para01).add(new BigDecimal(para02));
		BigDecimal premRate = prem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		
		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF06Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02)
			throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checktRF06Value(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//純保險費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "2", calcDate, "", para01, para02, "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 *費率回推處理
		 *WK_出單費率 = WK_出單保費 / 接收參數.PARA_02 * 1000  --四捨五入至小數第四位
		 *WK_純保費率 = WK_純保費 / 接收參數.PARA_02 * 1000 –四捨五入至小數第四位 
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para02), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para02), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF07Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01)
			throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checktRF07Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//純保險費
		BigDecimal purePrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "", para01, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF08Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02)
			throws SystemException, Exception {
		//檢查欄位是否輸入
		String msg = checktRF08Value(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//AMT
		BigDecimal amtPrem = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "AMOUNT", para01, "", "", "");
		//DCT
		BigDecimal dctRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "DEDUCTION", para02, "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("amtPrem", amtPrem);
		formulaParamMap.put("dctRate", dctRate);
		//純保險費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RF08, formulaParamMap);		
		//出單保費
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 *費率回推處理
		 *WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		 *WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF09Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, String para02,
			String para03, String para04, String para05)
			throws SystemException, Exception {

		//檢查欄位是否輸入
		String msg = checktRF09Value(riskcode, kindcode, para01, para02, para03, para04, para05);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//DEAD身故或喪葬費用基本純費率
		BigDecimal deadRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "DEAD-PRATE", "", "", "", "");
		//DISA失能基本純費率
		BigDecimal disaRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "DISABLED-PRATE", "", "", "", "");
		//HOSP住院基本純費率
		BigDecimal hospRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "HOSPITAL-PRATE", "", "", "", "");
		//ICU加護病房基本純費率
		BigDecimal icuRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "ICU-PRATE", "", "", "", "");
		//HCSL住院慰問基本純費率
		BigDecimal hcslRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "HCSL-PRATE", "", "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("para02", new BigDecimal(para02));
		formulaParamMap.put("para03", new BigDecimal(para03));
		formulaParamMap.put("para04", new BigDecimal(para04));
		formulaParamMap.put("para05", new BigDecimal(para05));
		formulaParamMap.put("deadRate", deadRate);
		formulaParamMap.put("disaRate", disaRate);
		formulaParamMap.put("hospRate", hospRate);
		formulaParamMap.put("icuRate", icuRate);
		formulaParamMap.put("hcslRate", hcslRate);
		//純保險費(未四捨五入)
		BigDecimal purePrem = calPremBaseService.beanShellCalculate(FirF02Formula.RF09, formulaParamMap);		
		//出單保費(已四捨五入)
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, surchargeRate);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/*
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / 接收參數.PARA_01 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / 接收參數.PARA_01 * 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getRF10Prem(String calcDate, String channelType,
			String riskcode, String kindcode, String para01, Map fr3Map)
			throws SystemException, Exception {
		//檢查欄位是否輸入
		String msg = checktRF10Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		if("1".equals(para01)){
			throw new SystemException("目前無法計算家庭綜合甲式-綠能保費。");
		}
		if(fr3Map == null){
			throw new SystemException("沒有傳入主險資料，無法計算綠能保費。");
		}
		//主險出單保費
		BigDecimal mainInsPrem = (BigDecimal)fr3Map.get("premium");
		//主險保額
		BigDecimal mainAmount = (BigDecimal)fr3Map.get("amount");
		//綠能升級調整係數
		String paraType = "";
		if("1".equals(para01)){
			paraType = "RATE-1";
		}
		if("2".equals(para01)){
			paraType = "RATE-2";
		}
		if("3".equals(para01)){
			paraType = "RATE-3";
		}
		BigDecimal greenFactor = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, paraType, "", "", "", "");
		//附加費用率
		BigDecimal surchargeRate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("greenFactor", greenFactor);
		formulaParamMap.put("mainInsPrem", mainInsPrem);
		//純保險費(未四捨五入)
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF02Formula.RF10, formulaParamMap);		
		//純保費四捨五入(已四捨五入)
		BigDecimal purePrem = calPremBaseService.calculatePurePrem(prem, surchargeRate);
		//保費四捨五入
		prem = prem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/**
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / TMP_主險保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_主險保額* 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(mainAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(mainAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", surchargeRate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktFR2Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼","險別代碼","地震保額"};
		String valueAry[] =  {riskcode,kindcode,para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "地震險未傳入" + msg + "資料，無法計算保費。";
	}
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param paraType
	 * @param para01
	 * @param para02
	 * @param para03
	 * @param para04
	 * @param para05
	 * @param para06
	 * @return
	 * @throws Exception
	 */
	private String checktFR3Value(String riskcode, String kindcode, String paraType, 
			String para01, String para02, String para03, String para04, String para05, String para06) throws Exception{
		
		
		String keyAry[] = {"險種代碼", "險別代碼", "參數類型", "火險保額", "建築結構", "屋頂別", "總樓層", "同棟樓是否有營業", ""};
		String valueAry[] =  {riskcode, kindcode, paraType, para01, para02, para03, para04, para05, ""};
		
		if("RFB".equals(kindcode)){
			keyAry[8] = "標的物";
			valueAry[8] = para06;
		}
		
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "住宅火險未傳入" + msg + "資料，無法計算保費。";
	}
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktRFCValue(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼","險別代碼","機車火災保額"};
		String valueAry[] =  {riskcode,kindcode,para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RFC商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @param para03
	 * @return
	 * @throws Exception
	 */
	private String checktRFDValue(String riskcode, String kindcode, String para01, String para02, String para03) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "每件特定物品保額", "特定物品保額", "財務被竊保額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RFD商品未傳入" + msg + "資料，無法計算保費。";
	}
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @param para03
	 * @param para04
	 * @param para05
	 * @return
	 * @throws Exception
	 */
	private String checktRFEValue(String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "生活責任保額", "自負額", "慰問金住院保額", "慰問金死亡保額", "慰問金保期內保額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03, para04, para05};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RFE商品未傳入" + msg + "資料，無法計算保費。";
	}
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @return
	 * @throws Exception
	 */
	private String checktRF01Value(String riskcode, String kindcode) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼"};
		String valueAry[] =  {riskcode, kindcode};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF01商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktRF02Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "家事代勞保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF02商品未傳入" + msg + "資料，無法計算保費。";
	}
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktRF03Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "地震災害保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF03商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @return
	 * @throws Exception
	 */
	private String checktRF04Value(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "房屋跌價補償保險金額", "清理費用保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF04商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @return
	 * @throws Exception
	 */
	private String checktRF05Value(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "房屋租金補償保險金額", "清理費用保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF05商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @return
	 * @throws Exception
	 */
	private String checktRF06Value(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "每一事故保額", "保險期間累計保額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF06商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktRF07Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "寵物意外費用保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF07商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * 
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @return
	 * @throws Exception
	 */
	private String checktRF08Value(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "輕損地震保險金額", "自負額比率"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF08商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @param para02
	 * @param para03
	 * @param para04
	 * @param para05
	 * @return
	 * @throws Exception
	 */
	private String checktRF09Value(String riskcode, String kindcode, String para01, String para02, String para03, String para04, String para05) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "身故或喪葬費用保險金額", "失能保險金額","住院保險金額","加護病房保險金額","住院慰問保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03, para04, para05};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF09商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checktRF10Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "主險類別"};
		String valueAry[] =  {riskcode, kindcode, para01};

		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "RF10商品未傳入" + msg + "資料，無法計算保費。";
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
	

	
	public static void main(String args[]) throws EvalError, IOException{
		
		Interpreter interpreter = new Interpreter();
		interpreter.set("i", 1);
		interpreter.set("j", 2);
		
		System.out.println(interpreter.getIn().toString());
		System.out.println(interpreter.eval("i+j"));
		
		 BufferedReader bufReader = new BufferedReader(interpreter.getIn());
	        String input = null;
	        while((input = bufReader.readLine()) != null) {
	        	System.out.println(input);

	        }
	        bufReader.close();
				
		BigDecimal purePrem = new BigDecimal("10.22");
		BigDecimal surchargeRate = new BigDecimal("0.445");
		BigDecimal prem = purePrem.divide(BigDecimal.ONE.subtract(surchargeRate), 2, BigDecimal.ROUND_HALF_UP);
		
		ArrayList a = new ArrayList();
		ArrayList b = new ArrayList();
		
		a.add("7");
		a.add("9");
		a.add("4");
		System.out.println("1 - " + a.size());
		b.add("3");
		b.add("2");
		b.add("1");
		System.out.println("2 - " + a.size());
		a.addAll(b);
		for(int i = 0 ; i < a.size(); i++){
			System.out.println(a.get(i));
		}
		
	}

	public FirPremiumRate2Service getFirPremiumRate2Service() {
		return firPremiumRate2Service;
	}

	public void setFirPremiumRate2Service(
			FirPremiumRate2Service firPremiumRate2Service) {
		this.firPremiumRate2Service = firPremiumRate2Service;
	}

	public FirBaseUtilService getFirBaseUtilService() {
		return firBaseUtilService;
	}

	public void setFirBaseUtilService(FirBaseUtilService firBaseUtilService) {
		this.firBaseUtilService = firBaseUtilService;
	}

	public PrpdBaseRateService getPrpdBaseRateService() {
		return prpdBaseRateService;
	}

	public void setPrpdBaseRateService(PrpdBaseRateService prpdBaseRateService) {
		this.prpdBaseRateService = prpdBaseRateService;
	}

	public PrpdPropStructService getPrpdPropStructService() {
		return prpdPropStructService;
	}

	public void setPrpdPropStructService(PrpdPropStructService prpdPropStructService) {
		this.prpdPropStructService = prpdPropStructService;
	}

	public CalPremBaseService getCalPremBaseService() {
		return calPremBaseService;
	}

	public void setCalPremBaseService(CalPremBaseService calPremBaseService) {
		this.calPremBaseService = calPremBaseService;
	}
	
	

}
