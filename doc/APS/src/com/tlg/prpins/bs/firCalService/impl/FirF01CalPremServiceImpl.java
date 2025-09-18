package com.tlg.prpins.bs.firCalService.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.firCalService.FirF01CalPremService;
import com.tlg.prpins.bs.premCalculate.CalPremBaseService;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.prpins.entity.FirPremiumRate2;
import com.tlg.prpins.entity.FirPremiumRate3;
import com.tlg.prpins.entity.PbPremcalcClause;
import com.tlg.prpins.entity.PbPremiumRate2;
import com.tlg.prpins.entity.PrpdPropStruct;
import com.tlg.prpins.service.FirPremiumRate2Service;
import com.tlg.prpins.service.FirPremiumRate3Service;
import com.tlg.prpins.service.PbPremcalcCklistService;
import com.tlg.prpins.service.PbPremiumRate2Service;
import com.tlg.prpins.service.PrpdPropStructService;
import com.tlg.prpins.util.FirF01Formula;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirF01CalPremServiceImpl implements FirF01CalPremService{

	private FirPremiumRate3Service firPremiumRate3Service;
	private PbPremiumRate2Service pbPremiumRate2Service;
	private CalPremBaseService calPremBaseService;
	private PbPremcalcCklistService pbPremcalcCklistService;
	private PrpdPropStructService prpdPropStructService;
	private FirPremiumRate2Service firPremiumRate2Service;

	@SuppressWarnings("unchecked")
	@Override
	public Map getQfb1Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, String para05,
			String para06) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkQfb1Value(riskcode, kindcode, para01, para02, para03, para04, para05, para06);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-PRATE出單費率
		Map params = new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "Q_FB1_RATE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("lSubstrPara02", para02.substring(0, 1));
		params.put("compareFloor", para04);
		Result result = firPremiumRate3Service.findFirPremiumRate3ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("無法取得商火出單費率，無法計算保費。");
		}
		ArrayList<FirPremiumRate3> firPremiumRate3List = (ArrayList<FirPremiumRate3>)result.getResObject();
		FirPremiumRate3 firPremiumRate3 = (FirPremiumRate3)firPremiumRate3List.get(0);
		BigDecimal prate = firPremiumRate3.getParaValue();
		//C.	讀取附加費用率資料-DRATE：
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "Q_FB1_DANGER_GRADE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("para01", para02);
		params.put("compareFloor", para04);
		result = firPremiumRate3Service.findFirPremiumRate3ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("無法取得商火附加費用率，無法計算保費。");
		}
		firPremiumRate3List = (ArrayList<FirPremiumRate3>)result.getResObject();
		firPremiumRate3 = (FirPremiumRate3)firPremiumRate3List.get(0);
		BigDecimal drate = firPremiumRate3.getParaValue();
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("prate", prate);
		//出單保費
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF01Formula.QFB1, formulaParamMap).setScale(0, RoundingMode.HALF_UP);
		//純保費 = WK_出單保費 * (1 – DRATE.RETURN_VALUE) //四捨五入至整數位
		BigDecimal purePrem = calPremBaseService.calculatePurePrem(prem, drate);
		/**
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / TMP_主險保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_主險保額* 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getQb1Prem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkQb1Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-PRATE
		BigDecimal prate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "PRATE", "", "", "", "");
		if(prate == null){
			throw new SystemException("無法取得費率資料-PRATE，無法計算保費。");
		}
		//C.	讀取附加費用率資料-DRATE
		BigDecimal drate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "DRATE", "", "", "", "");
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料-DRATE，無法計算保費。");
		}
		
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("prate", prate);
		//出單保費
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF01Formula.QB1, formulaParamMap).setScale(0, RoundingMode.HALF_UP);
		//WK_純保費 = WK_出單保費 * (1 – DRATE.RETURN_VALUE) //四捨五入至整數位
		BigDecimal purePrem = calPremBaseService.calculatePurePrem(prem, drate);
		/**
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / TMP_主險保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_主險保額* 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getQbbPrem(String calcDate, String channelType, String riskcode, String kindcode, String para01) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkQbbValue(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-PRATE
		BigDecimal prate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "PRATE", "", "", "", "");
		if(prate == null){
			throw new SystemException("無法取得費率資料-PRATE，無法計算保費。");
		}
		//C.	讀取附加費用率資料-DRATE
		BigDecimal drate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "DRATE", "", "", "", "");
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料-DRATE，無法計算保費。");
		}
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("para01", new BigDecimal(para01));
		formulaParamMap.put("prate", prate);
		//出單保費
		BigDecimal prem = calPremBaseService.beanShellCalculate(FirF01Formula.QBB, formulaParamMap).setScale(0, RoundingMode.HALF_UP);
		//WK_純保費 = WK_出單保費 * (1 – DRATE.RETURN_VALUE) //四捨五入至整數位
		BigDecimal purePrem = calPremBaseService.calculatePurePrem(prem, drate);
		/**
		 * 費率回推處理
		 * WK_出單費率 = WK_出單保費 / TMP_主險保額 * 1000  --四捨五入至小數第四位
		 * WK_純保費率 = WK_純保費 / TMP_主險保額* 1000 –四捨五入至小數第四位
		 */
		BigDecimal premRate = prem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
		BigDecimal purePremRate = purePrem.divide(new BigDecimal(para01), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getQpbPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, String para05,
			String para06) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkQpbValue(riskcode, kindcode, para01, para02, para03, para04, para05, para06);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-PPREM基本純保費
		BigDecimal pprem = calPremBaseService.getFirInsRate(riskcode, kindcode, "5", calcDate, "BASE-PREM", para01, "", "", "");
		if(pprem == null){
			throw new SystemException("無法取得費率資料-PPREM基本純保費，無法計算保費。");
		}
		
		//C.	讀取費率資料-PSCALE規模係數
		Map params = new HashMap();
		String para02Str = new BigDecimal(para02).setScale(0, RoundingMode.DOWN).toString();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "PB_SCALE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("lePara01", para02Str);
		params.put("gePara02", para02Str);
		
		Result result = pbPremiumRate2Service.findPbPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("無法取得公共意外責任險規模係數，無法計算保費。");
		}
		ArrayList<PbPremiumRate2> pbPremiumRate2List = (ArrayList<PbPremiumRate2>)result.getResObject();
		PbPremiumRate2 pbPremiumRate2 = (PbPremiumRate2)pbPremiumRate2List.get(0);
		BigDecimal pscale = pbPremiumRate2.getParaValue();
		
		//D.	讀取費率資料-PPLACE多處所係數
		BigDecimal pplace = BigDecimal.ONE;
		
		//E.	讀取費率資料-PDCT自負額調整係數
		BigDecimal pdct = calPremBaseService.getFirInsRate(riskcode, kindcode, "5", calcDate, "DEDUCTION", para04, "", "", "");
		if(pdct == null){
			throw new SystemException("無法取得費率資料-PPREM基本純保費，無法計算保費。");
		}
		
		//F.	讀取費率資料-PHAMT高保額係數
		BigDecimal phamt = calPremBaseService.getFirInsRate(riskcode, kindcode, "7", calcDate, "PHAMT", para05.split(";")[0], para06.split(";")[0], para06.split(";")[1], para05.split(";")[1]);
		if(phamt == null){
			throw new SystemException("無法取得費率資料-PHAMT高保額係數，無法計算保費。");
		}
		
		//G.	讀取費率資料-UW核保加減費係數
		BigDecimal uwRate = BigDecimal.ZERO;
		if(firPremcalcTmp.getPbPremCklist() != null && firPremcalcTmp.getPbPremCklist().size() > 0){
			params.clear();
			params.put("oidFtrPremcalcTmp", firPremcalcTmp.getOid());
			BigDecimal score = pbPremcalcCklistService.findPbPremcalcCklistForResultScore(params);
			if(score == null){
				throw new SystemException("計算公共意外責任險核保加減費係數發生問題，無法計算保費。");
			}
			BigDecimal tmpMaxLimit = calPremBaseService.getFirInsRate(riskcode, kindcode, "4", calcDate, "CKLIST-MAX", "", "", "", "");
			if(tmpMaxLimit == null){	
				throw new SystemException("無法取得費率資料-PPREM基本純保費，無法計算保費。");
			}
			/**
			 * 	若FCKL.F_SCORE = 0
			 *  PARA_VALUE = 0
			 */
			if(score.compareTo(BigDecimal.ZERO) == 0){
				uwRate = BigDecimal.ZERO;
			}
			/**
				若FCKL.F_SCORE > 0 AND FCKL.F_SCORE > TMP_MAX_LIMIT
  				PARA_VALUE = TMP_MAX_LIMIT
			 */
			if(score.compareTo(BigDecimal.ZERO) > 0){
				if(score.compareTo(tmpMaxLimit) > 0){
					uwRate = tmpMaxLimit;
				}else{
					uwRate = score;
				}
			}else{
				if(score.compareTo(tmpMaxLimit.multiply(new BigDecimal("-1"))) < 0 ){
					uwRate = tmpMaxLimit.multiply(new BigDecimal("-1"));
				}else{
					uwRate = score;
				}
			}
		}
		//H.	處理附約加費比率或加費純保費或加費出單保費-CLAUSE
		BigDecimal addPrem = BigDecimal.ZERO;
		BigDecimal addProportion = BigDecimal.ZERO;
		BigDecimal addPurePrem = BigDecimal.ZERO;
		if(firPremcalcTmp.getPbPremcalcClauseList() != null && firPremcalcTmp.getPbPremcalcClauseList().size() > 0){
			for(PbPremcalcClause pbPremcalcClause : firPremcalcTmp.getPbPremcalcClauseList()){
				if("PL01".equals(pbPremcalcClause.getClausecode())){
					BigDecimal pl01 = calPremBaseService.getFirInsRate(riskcode, "PL01", "4", calcDate, "PRATE", "", "", "", "");
					if(addProportion == null){	
						throw new SystemException("無法取得費率資料-食物中毒，無法計算保費。");
					}
					pbPremcalcClause.setAddProportion(pl01);
					addProportion = addProportion.add(pl01);
				}
				if("PL10".equals(pbPremcalcClause.getClausecode())){
					BigDecimal pl10 = calPremBaseService.getFirInsRate(riskcode, "PL10", "4", calcDate, "PRATE", "", "", "", "");
					if(addProportion == null){	
						throw new SystemException("無法取得費率資料-廣告招牌，無法計算保費。");
					}
					pbPremcalcClause.setAddProportion(pl10);
					addProportion = addProportion.add(pl10);
				}
				if("PL05".equals(pbPremcalcClause.getClausecode())){
					BigDecimal pl05 = calPremBaseService.getFirInsRate(riskcode, "PL05", "5", calcDate, "AMOUNT", pbPremcalcClause.getAmount01(), "", "", "");
					if(addProportion == null){	
						throw new SystemException("無法取得費率資料-建築物承租人火災責任，無法計算保費。");
					}
					pbPremcalcClause.setAddPrem(pl05);
					addPrem = addPrem.add(pl05);
				}
			}
		}
		//I.	AGG加乘倍數處理-AGG
		/*
		 * 	TMP_AOA_AMT = 取分號前的數字(接收參數.PARA_06) + 取分號後的數字(接收參數.PARA_06)
			TMP_AGG_AMT = 取分號後的數字(接收參數.PARA_05)
			TMP_VALUE = TMP_AGG_AMT / TMP_AOA_AMT
		 */
		BigDecimal tmpAoaAmt = new BigDecimal(para06.split(";")[0]).add(new BigDecimal(para06.split(";")[1]));
		BigDecimal tmpAggAmt = new BigDecimal(para05.split(";")[1]);
		BigDecimal tmpValue = tmpAggAmt.divide(tmpAoaAmt, 10, RoundingMode.HALF_UP);
		
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "PB_AGG");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("lPara01", tmpValue.toString());
		params.put("gePara02", tmpValue.toString());
		
		result = pbPremiumRate2Service.findPbPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("無法取得公共意外責任險AGG加乘倍數係數，無法計算保費。");
		}
		pbPremiumRate2List = (ArrayList<PbPremiumRate2>)result.getResObject();
		pbPremiumRate2 = (PbPremiumRate2)pbPremiumRate2List.get(0);
		BigDecimal aggFactor = pbPremiumRate2.getParaValue();
		
		//J.	讀取附加費用率資料-DRATE 
		BigDecimal drate = calPremBaseService.getFirInsRate(riskcode, kindcode, "4", calcDate, "DRATE", "", "", "", "");
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("pprem", pprem);
		formulaParamMap.put("pscale", pscale);
		formulaParamMap.put("pplace", pplace);
		formulaParamMap.put("pdct", pdct);
		formulaParamMap.put("phamt", phamt);
		formulaParamMap.put("uwRate", uwRate);
		formulaParamMap.put("addProportion", addProportion);
		formulaParamMap.put("addPurePrem", addPurePrem);
		formulaParamMap.put("aggFactor", aggFactor);
		
		//純保費 = (基本純保險費 * 規模係數 * 多處所係數 * (1+自負額調整係數) * 高保額係數 * (1+(核保加減費係數/100)) * (1 + 附約加費比率)* (1 + AGG倍數)) + 附約加費純保費
		BigDecimal purePrem= calPremBaseService.beanShellCalculate(FirF01Formula.QPB, formulaParamMap);
		//總保費 = (純保費 / (1-附加費用率)) + 附約加費出單保費 //四捨五入至整數位
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, drate).add(addPrem).setScale(0, BigDecimal.ROUND_HALF_UP);
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		/**
		 * 	費率回推處理
			WK_出單費率 = 0
			WK_純保費率 = 0

		 */
		BigDecimal premRate = BigDecimal.ZERO;
		BigDecimal purePremRate = BigDecimal.ZERO;

		Map premMap = new HashMap();
		premMap.put("purePremium", purePrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", purePremRate);
//		premMap.put("addPrem", addPrem);
//		premMap.put("addProportion", addProportion);
//		premMap.put("addPurePrem", addPurePrem);
		
		return premMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getFb1Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String paraType, String para01,
			String para02, String para03, String para04, String para05,
			String para06) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkFb1Value(riskcode, kindcode, paraType, para01, para02, para03, para04);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	判斷建物等級
		Map params = new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("wallno", para03.substring(0,2));
		params.put("roofno", para03.substring(2));
		params.put("calcDate", calcDate);
		Result result = prpdPropStructService.findPrpdPropStructByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("查無建物等級，無法計算保費。");
		}
		ArrayList<PrpdPropStruct> prpdPropStructList = (ArrayList<PrpdPropStruct>)result.getResObject();
		PrpdPropStruct prpdPropStruct = (PrpdPropStruct)prpdPropStructList.get(0);
		String structureno = prpdPropStruct.getStructureno();
		String level1 = "01,02,03,27,28";
		if(level1.indexOf(para03.substring(0,2)) != -1 && Integer.parseInt(para04) >= 14){
			structureno = "1";
		}
		if(level1.indexOf(para03.substring(0,2)) != -1 && Integer.parseInt(para04) < 14){
			structureno = "2";
		}
				
		//C.	讀取商火費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "2", calcDate, "BASERATE", para02, structureno, "", "");
		
		//D.	讀取商火費率資料-高樓加費HIGH
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "HIGHRISEFEE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("ltPara01", para04);
		params.put("gtPara02", para04);
		
		result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("商業火災保險無法取得高樓加費資料，無法計算保費。");
		}
		ArrayList<FirPremiumRate2> firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
		FirPremiumRate2 firPremiumRate2 = (FirPremiumRate2)firPremiumRate2List.get(0);
		BigDecimal highFee = firPremiumRate2.getParaValue();
		
		//E.	讀取商火費率資料-營業加費
		BigDecimal businessFee = BigDecimal.ZERO;
		//計算營業加費之表標準為行業別代號B類且同棟或同層有營業行為者
		if("Y".equals(paraType) && "B".equals(para02.subSequence(0, 1))){
			params.clear();
			params.put("riskcode", riskcode);
			params.put("kindcode", kindcode);
			params.put("paraType", "OPERATINGFEE");
			params.put("validFalg", "1");
			params.put("calcDate", calcDate);
			params.put("ltPara01", para04);
			params.put("gtPara02", para04);
			result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
			if(result.getResObject() == null){
				throw new SystemException("商業火災保險無法取得營業加費資料，無法計算保費。");
			}
			firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
			firPremiumRate2 = (FirPremiumRate2)firPremiumRate2List.get(0);
			businessFee = firPremiumRate2.getParaValue();
		}
		
		//F.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para05, para06);
		
		//G.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		//核保技術調整係數
		BigDecimal[] undwrtAry = calPremBaseService.getFb1PremcalcCkList(firPremcalcTmp.getOid(), calcDate, riskcode, kindcode);
		BigDecimal undwrtFactor = undwrtAry[0];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("highFee", highFee);
		formulaParamMap.put("businessFee", businessFee);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.FB1, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位;
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		premMap.put("structureno", structureno);
		premMap.put("businessFee", businessFee);
		premMap.put("fb1UndwrtAry", undwrtAry);
		premMap.put("baseRate", baseRate);
		premMap.put("highFee", highFee);
		
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getB1Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkB1Value(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "BASERATE", para02, "", "", "");
		
		//C.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para03, para04);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.B1, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getB4Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkB4Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "BASERATE", "", "", "", "");
		
		//C.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para02, para03);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.B4, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getB5Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkB5Value(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "BASERATE", "", "", "", "");
		
		//C.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para02, para03);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		//危險費率
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.B5, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getB6Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkB6Value(para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		
		//B.	讀取費率資料-建物基本危險費率
		if(StringUtil.isSpace(para01) || Integer.parseInt(para01) < 0){
			para01 = "0";
		}
		BigDecimal buildRiskRate = BigDecimal.ZERO;
		if(Integer.parseInt(para01) > 0){
			buildRiskRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "BASERATE", "1", "", "", "");
		}
		
		//C.	讀取費率資料-非建物基本危險費率
		if(StringUtil.isSpace(para02) || Integer.parseInt(para02) < 0){
			para02 = "0";
		}
		BigDecimal nonBuildRiskRate = BigDecimal.ZERO;
		if(Integer.parseInt(para02) > 0){
			nonBuildRiskRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "BASERATE", "2", "", "", "");
		}
				
		//D.	處理自負額扣減率DT
		BigDecimal amount = new BigDecimal(para01).add(new BigDecimal(para02));
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, amount.toString(), para03, para04);

		//E.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		Map formulaParamMap = new HashMap();
		
		formulaParamMap.put("para01",  new BigDecimal(para01));
		formulaParamMap.put("para02",  new BigDecimal(para02));
		formulaParamMap.put("buildRiskRate", buildRiskRate);
		formulaParamMap.put("nonBuildRiskRate", nonBuildRiskRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		BigDecimal riskRate = BigDecimal.ZERO;
		BigDecimal premRate = BigDecimal.ZERO;
		BigDecimal riskPrem = BigDecimal.ZERO;
		BigDecimal prem = BigDecimal.ZERO;
		
		BigDecimal riskRate1 = BigDecimal.ZERO;
		BigDecimal premRate1 = BigDecimal.ZERO;
		BigDecimal riskPrem1 = BigDecimal.ZERO;
		BigDecimal prem1 = BigDecimal.ZERO;
		
		BigDecimal riskRate2 = BigDecimal.ZERO;
		BigDecimal premRate2 = BigDecimal.ZERO;
		BigDecimal riskPrem2 = BigDecimal.ZERO;
		BigDecimal prem2 = BigDecimal.ZERO;
		
		if(Integer.parseInt(para01) > 0){
			//危險費率
			riskRate1 = calPremBaseService.beanShellCalculate(FirF01Formula.B6_1, formulaParamMap).setScale(3, RoundingMode.HALF_UP);
			//WK_出單費率_1 = WK_危險費率_1 / (1 – WK_附加費用率)	--無條件進位至小數第三位
			premRate1 = riskRate1.divide(BigDecimal.ONE.subtract(drate), 10, BigDecimal.ROUND_HALF_UP).setScale(3, RoundingMode.CEILING);
			//WK_危險保費_1 = (接收參數.PARA_01 / 1,000) * WK_危險費率_1
			riskPrem1 = new BigDecimal(para01).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP).multiply(riskRate1).setScale(0, RoundingMode.HALF_UP);
			// WK_出單保費_1 = ((接收參數.PARA_01 / 1,000) * WK_出單費率_1
			prem1 = new BigDecimal(para01).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP).multiply(premRate1).setScale(0, RoundingMode.HALF_UP);
		}

		if(Integer.parseInt(para02) > 0){
			//危險費率
			riskRate2 = calPremBaseService.beanShellCalculate(FirF01Formula.B6_2, formulaParamMap).setScale(3, RoundingMode.HALF_UP);
			//WK_出單費率_2 = WK_危險費率_2 / (1 – WK_附加費用率)	--無條件進位至小數第三位
			premRate2 = riskRate2.divide(BigDecimal.ONE.subtract(drate), 10, BigDecimal.ROUND_HALF_UP).setScale(3, RoundingMode.CEILING);;
			//WK_危險保費_2 = (接收參數.PARA_02 / 1,000) * WK_危險費率_1
			riskPrem2 = new BigDecimal(para02).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP).multiply(riskRate2).setScale(0, RoundingMode.HALF_UP);
			// WK_出單保費_2 = ((接收參數.PARA_02 / 1,000) * WK_出單費率_1
			prem2 = new BigDecimal(para02).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP).multiply(premRate2).setScale(0, RoundingMode.HALF_UP);
		}
		/**
			WK_危險保費 = WK_危險保費_1 + WK_危險保費_2
			WK_出單保費 = WK_出單保費_1 + WK_出單保費_2
		
			費率回推如下（四捨五入至小數第三位）：
			WK_危險費率 = (WK_危險保費 / (接收參數.PARA_01 + 接收參數.PARA_02)) * 1,000
			WK_出單費率 = (WK_出單保費 / (接收參數.PARA_01 + 接收參數.PARA_02)) * 1,000
		 */
		riskPrem = riskPrem1.add(riskPrem2);
		prem = prem1.add(prem2);
		riskRate = (riskPrem).divide(amount, 10, RoundingMode.HALF_UP).multiply(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);
		premRate = (prem).divide(amount, 10, RoundingMode.HALF_UP).multiply(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);
	
		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getB7Prem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkB7Value(riskcode, kindcode);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		if(StringUtil.isSpace(para01)){
			para01 = "0";
		}
		if(StringUtil.isSpace(para04)){
			para04 = "0";
		}
		if( Integer.parseInt(para01) + Integer.parseInt(para04) <= 0){
			throw new SystemException("竊盜險未傳入保額，無法計算保費。");
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "BASEPREM", "", "", "", "");
		
		//C.	讀取各級距危險費率-LRATE
		BigDecimal classIntervalPrem = BigDecimal.ZERO;
		Map params = new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "LEVEL_RATE");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("orderBy", "ORDER BY　TO_NUMBER(PARA_01) DESC");
		Result result = firPremiumRate2Service.findFirPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("商火竊盜險查無保額級距危險費率，無法計算保費。");
		}
		ArrayList<FirPremiumRate2> firPremiumRate2List = (ArrayList<FirPremiumRate2>)result.getResObject();
		BigDecimal para01Tmp = new BigDecimal(para01).add(new BigDecimal(para04));
		for(int i = 0 ; i < firPremiumRate2List.size() ; i++){
			FirPremiumRate2 firPremiumRate2 = firPremiumRate2List.get(i);
			BigDecimal para01Value = new BigDecimal(firPremiumRate2.getPara01());
			if(para01Tmp.compareTo(para01Value) > 0){
				//保額減para01Value看餘數多少，用餘數乘上費率，再用原本的保額減掉餘數作為下次比較的值
				BigDecimal tmpNum = para01Tmp.subtract(para01Value.subtract(BigDecimal.ONE));
				classIntervalPrem = classIntervalPrem.add(tmpNum.multiply(firPremiumRate2.getParaValue()).divide(new BigDecimal(1000), 10, RoundingMode.HALF_UP));
				para01Tmp = para01Tmp.subtract(tmpNum);
			}
		}
		if(para01Tmp.compareTo(BigDecimal.ZERO) > 0){
			throw new SystemException("商火竊盜險計算級距保費發生錯誤，無法計算保費。");
		}
		//D.	讀取費率資料-實損實賠係數
		BigDecimal realLossFactor = BigDecimal.ONE;
		
		//E.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para02, para03);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		

		BigDecimal amount = new BigDecimal(para01).add(new BigDecimal(para04));
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("classIntervalPrem", classIntervalPrem);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		formulaParamMap.put("realLossFactor", realLossFactor);
		formulaParamMap.put("amount", amount);
		
		//WK_基本危險費率  = ((BASE.RETURN_VALUE + WK_級距保費加總) / (接收參數.PARA_01 + 接收參數.PARA_04)) * 1,000  	--四捨五入至小數第三位
		BigDecimal baseRiskRate = calPremBaseService.beanShellCalculate(FirF01Formula.B7, formulaParamMap).setScale(3, RoundingMode.HALF_UP);

		//WK_危險費率 = WK_基本危險費率 * (1 - .DT.RETURN_VALUE) * (1 + TMP_核保調整係數_非天災附加險)	--四捨五入至小數第三位
		BigDecimal riskRate = baseRiskRate.multiply(BigDecimal.ONE.subtract(new BigDecimal(deductionRate))).multiply(BigDecimal.ONE.add(undwrtFactor)).setScale(3, RoundingMode.HALF_UP);

		//WK_出單費率 = WK_危險費率 / (1 – WK_附加費用率)	--無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 10, BigDecimal.ROUND_HALF_UP).setScale(3, RoundingMode.CEILING);
		
		BigDecimal riskPrem = BigDecimal.ZERO;
		BigDecimal prem = BigDecimal.ZERO;
		BigDecimal riskPrem1 = BigDecimal.ZERO;
		BigDecimal prem1 = BigDecimal.ZERO;
		BigDecimal riskPrem2 = BigDecimal.ZERO;
		BigDecimal prem2 = BigDecimal.ZERO;
		if(Integer.parseInt(para01) > 0){
			riskPrem1 = riskRate.multiply(new BigDecimal(para01).divide(new BigDecimal(1000), 10, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP);
			prem1 = premRate.multiply(new BigDecimal(para01).divide(new BigDecimal(1000), 10, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP);
		}
		
		if(Integer.parseInt(para04) > 0){
			riskPrem2 = riskRate.multiply(new BigDecimal(para04).divide(new BigDecimal(1000), 10, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP);
			prem2 = premRate.multiply(new BigDecimal(para04).divide(new BigDecimal(1000), 10, RoundingMode.HALF_UP)).setScale(0, RoundingMode.HALF_UP);
		}
		riskPrem = riskPrem1.add(riskPrem2);
		prem = prem1.add(prem2);
				
		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getBBPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkBBValue(riskcode, kindcode, para01);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "0", calcDate, "BASERATE", "", "", "", "");
		
		//C.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para02, para03);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.B5, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
		return premMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map getBDPrem(FirPremcalcTmp firPremcalcTmp, String calcDate, String channelType, String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, BigDecimal[] fb1UndwrtAry) throws SystemException, Exception {
		
		//檢查欄位是否輸入
		String msg = checkBDValue(riskcode, kindcode, para01, para02);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本危險費率BASE
		BigDecimal baseRate = calPremBaseService.getFirInsRate(riskcode, kindcode, "1", calcDate, "BASERATE", para02.substring(0, 1), "", "", "");
		
		//C.	處理自負額扣減率DT
		double deductionRate = calPremBaseService.getDeductionRate(riskcode, kindcode, calcDate, para01, para03, para04);
		
		//D.	讀取附加費用率資料
		BigDecimal drate =  calPremBaseService.getSurchargeRate(riskcode, kindcode, channelType, calcDate);
		if(drate == null){
			throw new SystemException("無法取得附加費用率資料，無法計算保費。");
		}
		
		//承保多種附加險優待率
		BigDecimal discountRate = calPremBaseService.getFb1DiscountRate(firPremcalcTmp, riskcode, kindcode, firPremcalcTmp.getOid(), calcDate);
		
		//營業狀況詢問表結果
		BigDecimal undwrtFactor = fb1UndwrtAry[1];
		
		/**
		 * 	危險保費 ＝ 保險金額 × 危險費率
			危險費率 ＝ 基本危險費率 ×（1－自負額扣減率）×（1+高樓加費+）×（1＋核保技術調整係數）
		 */
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("baseRate", baseRate);
		formulaParamMap.put("discountRate", discountRate);
		formulaParamMap.put("deductionRate", new BigDecimal(deductionRate));
		formulaParamMap.put("undwrtFactor", undwrtFactor);
		
		
		BigDecimal riskRate = calPremBaseService.beanShellCalculate(FirF01Formula.BD, formulaParamMap).setScale(3, RoundingMode.HALF_UP); //四捨五入至小數第三位
		//危險保費 ＝ 保險金額 × 危險費率--四捨五入至整數位
		BigDecimal riskPrem = new BigDecimal(para01).divide(new BigDecimal("1000"), 10, RoundingMode.HALF_UP).multiply(riskRate).setScale(0, RoundingMode.HALF_UP);
		//出單費率 = 危險費率/(1-附加費用率) //無條件進位至小數第三位
		BigDecimal premRate = riskRate.divide(BigDecimal.ONE.subtract(drate), 3, RoundingMode.CEILING);
		//出單保費 ＝ 保險金額 × 危險費率 --四捨五入至整數位
		BigDecimal prem = (new BigDecimal(para01).divide(new BigDecimal("1000")).setScale(10, RoundingMode.HALF_UP)).multiply(premRate).setScale(0, RoundingMode.HALF_UP);

		Map premMap = new HashMap();
		premMap.put("purePremium", riskPrem);
		premMap.put("premium", prem);
		premMap.put("dangerGrade", drate);
		premMap.put("premRate", premRate);
		premMap.put("purePremRate", riskRate);
		
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
	private String checkQfb1Value(String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, String para05,
			String para06) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額", "使用性質", "自負額", "樓層", "坪數", "郵遞區號"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03, para04, para05, para06};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "Q_FB1商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkQb1Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "Q_B1商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkQbbValue(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}
		return "Q_BB商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkQpbValue(String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, String para05,
			String para06) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "使用性質", "坪數", "處所數", "自負額", "AOP保額及AGG保額", "AOA體傷保額及AOA財損保額"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03, para04, para05, para06};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "PB商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkFb1Value(String riskcode, String kindcode, String paraType, String para01,
			String para02, String para03, String para04) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "是否營業加費", "保險金額", "使用性質", "建物結構及屋頂別", "總樓層數"};
		String valueAry[] =  {riskcode, kindcode, paraType, para01, para02, para03, para04};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "FB1商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkB1Value(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額", "爆炸等級"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "B1商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkB4Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "B4商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkB5Value(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "B5商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkB6Value(String para01, String para02) throws Exception{
		if(StringUtil.isSpace(para01) && StringUtil.isSpace(para02)){
			return "B6消防裝置滲漏險未傳入保險金額資料，無法計算保費。";			
		}
		return "";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkB7Value(String riskcode, String kindcode) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼"};
		String valueAry[] =  {riskcode, kindcode};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "B7商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkBBValue(String riskcode, String kindcode, String para01) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額"};
		String valueAry[] =  {riskcode, kindcode, para01};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "BB商品未傳入" + msg + "資料，無法計算保費。";
	}
	
	/**
	 * 檢查欄位必輸
	 * @param riskcode
	 * @param kindcode
	 * @param para01
	 * @return
	 * @throws Exception
	 */
	private String checkBDValue(String riskcode, String kindcode, String para01, String para02) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "保險金額", "使用性質"};
		String valueAry[] =  {riskcode, kindcode, para01, para02};
		
		String msg = checkValue(keyAry, valueAry);
		if(StringUtil.isSpace(msg)){
			return "";
		}

		return "BD商品未傳入" + msg + "資料，無法計算保費。";
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

	public FirPremiumRate3Service getFirPremiumRate3Service() {
		return firPremiumRate3Service;
	}

	public void setFirPremiumRate3Service(
			FirPremiumRate3Service firPremiumRate3Service) {
		this.firPremiumRate3Service = firPremiumRate3Service;
	}

	public CalPremBaseService getCalPremBaseService() {
		return calPremBaseService;
	}

	public void setCalPremBaseService(CalPremBaseService calPremBaseService) {
		this.calPremBaseService = calPremBaseService;
	}

	public PbPremiumRate2Service getPbPremiumRate2Service() {
		return pbPremiumRate2Service;
	}

	public void setPbPremiumRate2Service(PbPremiumRate2Service pbPremiumRate2Service) {
		this.pbPremiumRate2Service = pbPremiumRate2Service;
	}

	public PbPremcalcCklistService getPbPremcalcCklistService() {
		return pbPremcalcCklistService;
	}

	public void setPbPremcalcCklistService(
			PbPremcalcCklistService pbPremcalcCklistService) {
		this.pbPremcalcCklistService = pbPremcalcCklistService;
	}

	
	public static void main(String args[]){
		String s = "80.88";
		System.out.println(new BigDecimal(s).setScale(0, RoundingMode.DOWN).toString());
		String s1 = "MIN1234";
		System.out.println(s1.substring(0,3));
	}
	public PrpdPropStructService getPrpdPropStructService() {
		return prpdPropStructService;
	}
	public void setPrpdPropStructService(PrpdPropStructService prpdPropStructService) {
		this.prpdPropStructService = prpdPropStructService;
	}
	public FirPremiumRate2Service getFirPremiumRate2Service() {
		return firPremiumRate2Service;
	}
	public void setFirPremiumRate2Service(
			FirPremiumRate2Service firPremiumRate2Service) {
		this.firPremiumRate2Service = firPremiumRate2Service;
	}
}
