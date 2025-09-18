package com.tlg.prpins.bs.pbCalService.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.bs.pbCalService.PbCalPremService;
import com.tlg.prpins.bs.premCalculate.CalPremBaseService;
import com.tlg.prpins.entity.PbPremcalcClause;
import com.tlg.prpins.entity.PbPremcalcTmp;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;
import com.tlg.prpins.entity.PbPremiumRate2;
import com.tlg.prpins.entity.PbPremiumRate5;
import com.tlg.prpins.service.PbPremcalcCklistService;
import com.tlg.prpins.service.PbPremiumRate2Service;
import com.tlg.prpins.service.PbPremiumRate5Service;
import com.tlg.prpins.service.PbQueryDetailService;
import com.tlg.prpins.util.PbFormula;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbCalPremServiceImpl implements PbCalPremService{

	private PbPremiumRate5Service pbPremiumRate5Service;
	private PbPremiumRate2Service pbPremiumRate2Service;
	private CalPremBaseService calPremBaseService;
	private PbPremcalcCklistService pbPremcalcCklistService;
	private PbQueryDetailService pbQueryDetailService;

	@SuppressWarnings("unchecked")
	@Override
	public Map getPbPlacePrem(PbPremcalcTmp pbPremcalcTmp, PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception {
		
		String calcDate =  DateUtils.getDateDay(pbPremcalcTmp.getCalcDate(), "", 1);
		String calcType = StringUtil.nullToSpace(pbPremcalcTmp.getCalcType());
		String riskcode = StringUtil.nullToSpace(pbPremcalcTmpdtl.getRiskcode());
		String kindcode = StringUtil.nullToSpace(pbPremcalcTmpdtl.getKindcode());
		String paraType = StringUtil.nullToSpace(pbPremcalcTmpdtl.getParaType());
		String para01 = pbPremcalcTmpdtl.getPara01() == null ? "":pbPremcalcTmpdtl.getPara01().toString();
		String para02 = pbPremcalcTmpdtl.getPara02() == null ? "":pbPremcalcTmpdtl.getPara02().toString();
		String para03 = pbPremcalcTmpdtl.getPara03() == null ? "":pbPremcalcTmpdtl.getPara03().toString();
		String para04 = pbPremcalcTmpdtl.getPara04() == null ? "":pbPremcalcTmpdtl.getPara04().toString();
		String para05 = pbPremcalcTmpdtl.getPara05() == null ? "":pbPremcalcTmpdtl.getPara05().toString();
		String para06 = pbPremcalcTmpdtl.getPara06() == null ? "":pbPremcalcTmpdtl.getPara06().toString();
		String para07 = pbPremcalcTmpdtl.getPara07() == null ? "":pbPremcalcTmpdtl.getPara07().toString();
		String para08 = pbPremcalcTmpdtl.getPara08() == null ? "":pbPremcalcTmpdtl.getPara08().toString();
		String para09 = pbPremcalcTmpdtl.getPara09() == null ? "":pbPremcalcTmpdtl.getPara09().toString();
		
		//檢查欄位是否輸入
		String msg = checkPbPlaceValue(riskcode, kindcode, para01, para02, para03, para04, para05, para06, para07, para08, para09);
		if(!StringUtil.isSpace(msg)){
			throw new SystemException(msg);
		}
		//B.	讀取費率資料-基本純保費  → basePurePrem
		String tableType = "";
		if("1".equals(calcType)){
			tableType = "BUS_BASE_PURE_PREMIUM";
		}
		if("2".equals(calcType)){
			tableType = "ACT_BASE_PURE_PREMIUM";
		}
		Map valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "0", calcType, calcDate, tableType, para01, "", "", "");
		BigDecimal basePurePrem = (BigDecimal)valueMap.get("PARA_01");
		
		//C.	讀取費率資料-規模係數 → scaleFactor
		BigDecimal scaleFactor = BigDecimal.ZERO;
		/**因偉大的SA調整規格以下先mark**/
//		//讀取費率資料-坪數規模係數 
//		//(1)	先計算坪數加乘倍數:
//		if("1".equals(calcType)){
//			tableType = "BUS_BASE_SCALE_COE";
//		}
//		if("2".equals(calcType)){
//			tableType = "ACT_BASE_SCALE_COE";
//		}
//		valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "0", calcType, calcDate, tableType, "", "", "", "");
//		BigDecimal tmpPingBase = (BigDecimal)valueMap.get("PARA_01");
//		BigDecimal tmpValue = new BigDecimal(pbPremcalcTmpdtl.getPara02()).divide(tmpPingBase).setScale(0, RoundingMode.FLOOR);
//		//(2)再計算坪數規模係數
//		Map params = new HashMap();
//		params.put("riskcode", riskcode);
//		params.put("kindcode", kindcode);
//		if("1".equals(calcType)){
//			params.put("paraType", "AREA_SCALE_RANGE");
//		}
//		if("2".equals(calcType)){
//			params.put("paraType", "PEOPLE_SCALE_RANGE");
//		}
//		params.put("validFalg", "1");
//		params.put("calcDate", calcDate);
//		params.put("calcType", calcType);
//		params.put("lePara01", tmpValue);
//		params.put("gPara02", tmpValue);
//		Result result = pbPremiumRate5Service.findPbPremiumRate5ByParams(params);
//		if(result.getResObject() == null){
//			throw new SystemException("無法取得公共意外責任險規模係數，無法計算保費。");
//		}
//		ArrayList<PbPremiumRate5> pbPremiumRate5List = (ArrayList<PbPremiumRate5>)result.getResObject();
//		PbPremiumRate5 pbPremiumRate5 = (PbPremiumRate5)pbPremiumRate5List.get(0);
//		BigDecimal scalePara01 = null;
//		BigDecimal scalePara03 = null;
//		BigDecimal scaleParaValue01 = pbPremiumRate5.getParaValue01();
//		if(!StringUtil.isSpace(pbPremiumRate5.getPara01())){
//			scalePara01 = new BigDecimal(pbPremiumRate5.getPara01());
//		}
//		if(!StringUtil.isSpace(pbPremiumRate5.getPara03())){
//			scalePara03 = new BigDecimal(pbPremiumRate5.getPara03());
//		}
//		
//		double scaleTmp = tmpValue.doubleValue();
//		//介於0 <= TMP_VALUE <0.1，直接取PARA_VALUE_01的值
//		if(scaleTmp < 0.1 && scaleTmp >= 0){
//			scaleFactor = scaleParaValue01;
//		}
//		//i介於0.1 =< TMP_VALUE < 1 	規模係數公式=PARA_VALUE_01+(TMP_VALUE -PARA_01)/PARA_01*PARA_03
//		if(scaleTmp < 1 && scaleTmp >= 0.1){
//			scaleFactor = scaleParaValue01.add((tmpValue.subtract(scalePara01)).divide(scalePara01, 10 ,RoundingMode.HALF_UP)).multiply(scalePara03);
//		}
//		//介於1 <= TMP_VALUE <2，直接取PARA_VALUE_01的值
//		if(scaleTmp < 2 && scaleTmp >= 1){
//			scaleFactor = scaleParaValue01;
//		}
//		if("1".equals(calcType)){
//			//ii 2=< TMP_VALUE 規模係數公式=PARA_VALUE_01+(TMP_VALUE -PARA_01)/1*PARA_03
//			if(scaleTmp < 501 && scaleTmp >= 2){
//				scaleFactor = scaleParaValue01.add((tmpValue.subtract(scalePara01)).divide(BigDecimal.ONE, 10 ,RoundingMode.HALF_UP)).multiply(scalePara03);
//			}
//		}
//		if("2".equals(calcType)){
//			//ii 2=< TMP_VALUE 規模係數公式=PARA_VALUE_01+(TMP_VALUE -PARA_01)/1*PARA_03
//			if(scaleTmp < 101 && scaleTmp >= 2){
//				scaleFactor = scaleParaValue01.add((tmpValue.subtract(scalePara01)).divide(BigDecimal.ONE, 10 ,RoundingMode.HALF_UP)).multiply(scalePara03);
//			}
//		}
		//C 讀取費率資料-規模係數 → scaleFactor
		Map params = new HashMap();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		if("1".equals(calcType)){
			params.put("paraType", "PB_SCALE");
		}
		if("2".equals(calcType)){
			params.put("paraType", "PB_PEOPLE_SCALE");
		}
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("calcType", calcType);
		params.put("lePara01", para02);
		params.put("gePara02", para02);
		Result result = pbPremiumRate2Service.findPbPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			if("1".equals(calcType)){
				throw new SystemException("無法取得公共意外責任險處所坪數規模係數，無法計算保費。");
			}
			if("2".equals(calcType)){
				throw new SystemException("無法取得公共意外責任險活動人數規模係數，無法計算保費。");	
			}
		}
		ArrayList<PbPremiumRate2> pbPremiumRate2List = (ArrayList<PbPremiumRate2>)result.getResObject();
		PbPremiumRate2 pbPremiumRate2 = (PbPremiumRate2)pbPremiumRate2List.get(0);
		scaleFactor = pbPremiumRate2.getParaValue();
		
		//D.	讀取費率資料-多處所係數 固定 = 1 → 處所 scaleTypeFactor
		BigDecimal scaleTypeFactor = BigDecimal.ZERO;
		if("1".equals(calcType)){
			scaleTypeFactor = BigDecimal.ONE;
		}
		//D.	計算期間規模係數  → 活動 scaleTypeFactor
		if("2".equals(calcType)){
			params.clear();
			params.put("riskcode", riskcode);
			params.put("kindcode", kindcode);
			params.put("paraType", "DURING_RANGE");
			params.put("validFalg", "1");
			params.put("calcDate", calcDate);
			params.put("calcType", calcType);
			params.put("lePara01", para03);
			params.put("gePara02", para03);
			result = pbPremiumRate5Service.findPbPremiumRate5ByParams(params);
			if(result.getResObject() == null){
				throw new SystemException("無法取得公共意外責任險規模係數，無法計算保費。");
			}
			ArrayList<PbPremiumRate5> pbPremiumRate5List = (ArrayList<PbPremiumRate5>)result.getResObject();
			PbPremiumRate5 pbPremiumRate5 = (PbPremiumRate5)pbPremiumRate5List.get(0);
			BigDecimal tmpPara01 = null;
			BigDecimal tmpPara03 = null;
			if(!StringUtil.isSpace(pbPremiumRate5.getPara01())){
				tmpPara01 = new BigDecimal(pbPremiumRate5.getPara01());
			}
			if(!StringUtil.isSpace(pbPremiumRate5.getPara03())){
				tmpPara03 = new BigDecimal(pbPremiumRate5.getPara03());
			}
			BigDecimal tmpParaValue01 = pbPremiumRate5.getParaValue01();
			BigDecimal tmpParaValue02 = pbPremiumRate5.getParaValue02();
			
			int days = Integer.parseInt(para03);
			if("1".equals(para03)){
				scaleTypeFactor = tmpParaValue01;
			}
			if(days >= 2 && days <= 19){
				scaleTypeFactor = tmpParaValue01.add((new BigDecimal(para03).subtract(tmpPara01)).multiply(tmpPara03));
			}
		}
		
		//E.	讀取費率資料-自負額調整係數 → deductFactor
		valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "2", calcType, calcDate, "DEDUCTIBLE_ADJ_COE", para04, "", "", "");
		BigDecimal deductFactor = (BigDecimal)valueMap.get("PARA_VALUE_02");
		
		//F.	讀取費率資料-高保額係數 → highAmtFactor
		valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "3", calcType, calcDate, "PHAMT", para06, para07, para08, para09);
		BigDecimal highAmtFactor = (BigDecimal)valueMap.get("PARA_VALUE");
		
		//G.	計算AGG加費係數 → aggFactor
		//先計算AGG基本單位倍數
		// TMP_AOA_AMT = 接收參數.PARA_07 + 接收參數.PARA_08 (每一意外事故體傷責任(AOA)+ 每一意外事故財損責任(AOA))
		BigDecimal tmpAoaAmt = new BigDecimal(para07).add(new BigDecimal(para08));
		// TMP_AGG_AMT = 接收參數.PARA_09 (保險期間內最高賠償金額(AGG))
		BigDecimal tmpAggAmt = new BigDecimal(para09);
		// TMP_VALUE = TMP_AGG_AMT / TMP_AOA_AMT (四捨五入至整數位)
		BigDecimal tmpAggMultiple = tmpAggAmt.divide(tmpAoaAmt, 0, RoundingMode.HALF_UP);
		
		//再計算AGG加費係數
		params.clear();
		params.put("riskcode", riskcode);
		params.put("kindcode", kindcode);
		params.put("paraType", "PB_AGG");
		params.put("validFalg", "1");
		params.put("calcDate", calcDate);
		params.put("lPara01", tmpAggMultiple);
		params.put("gePara02", tmpAggMultiple);
		result = pbPremiumRate2Service.findPbPremiumRate2ByParams(params);
		if(result.getResObject() == null){
			throw new SystemException("無法取得公共意外責任險AGG加費係數，無法計算保費。");
		}
		ArrayList<?> premiumRateList = (ArrayList<?>)result.getResObject();
		pbPremiumRate2 = (PbPremiumRate2)premiumRateList.get(0);
		BigDecimal aggFactor = pbPremiumRate2.getParaValue();
		
		//H.	取值-UW核保加減費係數 → uwRate
		BigDecimal uwRate = BigDecimal.ZERO;
		if(pbPremcalcTmp.getPbQueryDetailList() != null && pbPremcalcTmp.getPbQueryDetailList().size() > 0){
			valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "0", calcType, calcDate, "QUERY_MAX", "", "", "", "");
			BigDecimal tmpMaxLimit = (BigDecimal)valueMap.get("PARA_VALUE");
			params = new HashMap();
			params.put("oidPbPremcalcTmp", pbPremcalcTmp.getOid());
			BigDecimal score = pbQueryDetailService.findPbQueryDetailForPbResultScore(params);
			/**
			 * 	若FCKL.F_SCORE = 0
			 *  PARA_VALUE = 0
			 */
			if(score.compareTo(BigDecimal.ZERO) == 0){
				uwRate = BigDecimal.ZERO;
			}
			/**
			 * 若FCKL.F_SCORE > 0 AND FCKL.F_SCORE > TMP_MAX_LIMIT_UPPER
				PARA_VALUE = TMP_MAX_LIMIT_UPPER，否則PARA_VALUE = FCKL.F_SCORE
			 */
			if(score.compareTo(BigDecimal.ZERO) > 0){
				if(score.compareTo(tmpMaxLimit) > 0){
					uwRate = tmpMaxLimit;
				}else{
					uwRate = score;
				}
			}else{
				/**
				 * 若FCKL.F_SCORE < 0 AND FCKL.F_SCORE < (TMP_MAX_LIMIT_LOWER * -1)
					PARA_VALUE = TMP_MAX_LIMIT_LOWER * -1，否則PARA_VALUE = FCKL.F_SCORE
				 */
				if(score.compareTo(tmpMaxLimit.multiply(new BigDecimal("-1"))) < 0 ){
					uwRate = tmpMaxLimit.multiply(new BigDecimal("-1"));
				}else{
					uwRate = score;
				}
			}
		}
		
		//I.	處理附約加費 → addPrem，addProportion
		BigDecimal addPrem = BigDecimal.ZERO;
		BigDecimal addProportion = BigDecimal.ZERO;
		if(pbPremcalcTmp.getPbPremcalcClauseList() != null && pbPremcalcTmp.getPbPremcalcClauseList().size() > 0){
			for(PbPremcalcClause pbPremcalcClause : pbPremcalcTmp.getPbPremcalcClauseList()){
				valueMap = calPremBaseService.getPbInsRate(riskcode, pbPremcalcClause.getClausecode(), "7", calcType, calcDate, "", "", "", "", "");
				BigDecimal addProportionClause = (BigDecimal)valueMap.get("ADD_PROPORTION");
				BigDecimal addPremClause = (BigDecimal)valueMap.get("ADD_FEE");
				addPrem = addPrem.add(addPremClause);
				addProportion = addProportion.add(addProportionClause);
				pbPremcalcClause.setAddPrem(addPremClause);
				pbPremcalcClause.setAddProportion(addProportionClause);
			}
		}
		//J.	讀取附加費用率 → drate
		valueMap = calPremBaseService.getPbInsRate(riskcode, kindcode, "8", calcType, calcDate, "", "", "", "", "");
		BigDecimal drate = (BigDecimal)valueMap.get("DANGER_GRADE");
		
		Map formulaParamMap = new HashMap();
		formulaParamMap.put("basePurePrem", basePurePrem);
		formulaParamMap.put("scaleFactor", scaleFactor);
		formulaParamMap.put("scaleTypeFactor", scaleTypeFactor);
		formulaParamMap.put("deductFactor", deductFactor);
		formulaParamMap.put("highAmtFactor", highAmtFactor);
		formulaParamMap.put("uwRate", uwRate);
		formulaParamMap.put("aggFactor", aggFactor);
		formulaParamMap.put("addProportion", addProportion);
		formulaParamMap.put("addPrem", addPrem);
		
		//純保費 =基本純保費 *規模係數*期間係數*(1+自負額調整係數)*高保額係數* (1+AGG加費係數)*(1+附加條款加費係數)+附加條款加費
		BigDecimal purePrem= calPremBaseService.beanShellCalculate(PbFormula.PB, formulaParamMap);
		//總保費 = (純保費 / (1-附加費用率))  //四捨五入至整數位
		BigDecimal prem = calPremBaseService.calculatePrem(purePrem, drate).setScale(0, BigDecimal.ROUND_HALF_UP);		
		//純保費四捨五入
		purePrem = purePrem.setScale(0, BigDecimal.ROUND_HALF_UP);
		
		//調整後純保費 =基本純保費 *規模係數*多處所係數*(1+自負額調整係數)*高保額係數*[(1+核保加減費係數)/100]*(1+AGG加費係數)*[(1+附加條款加費係數)]+附加條款加費
		BigDecimal adjPurePremium= calPremBaseService.beanShellCalculate(PbFormula.PB_ADJ, formulaParamMap);
		//調整後總保費 = 純保費/(1-附加費用率) =>四捨五入至整數位
		BigDecimal adjPremium = calPremBaseService.calculatePrem(adjPurePremium, drate).setScale(0, BigDecimal.ROUND_HALF_UP);
		//純保費四捨五入
		adjPurePremium = adjPurePremium.setScale(0, BigDecimal.ROUND_HALF_UP);
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
		premMap.put("adjPurePremium", adjPurePremium);
		premMap.put("adjPremium", adjPremium);
		premMap.put("dangerGrade", drate);
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
	private String checkPbPlaceValue(String riskcode, String kindcode, String para01,
			String para02, String para03, String para04, String para05,
			String para06, String para07, String para08, String para09) throws Exception{
		String keyAry[] = {"險種代碼", "險別代碼", "營業類別", "坪數", "處所數", "自負額", "業務處所", "每一個人體傷責任AOP", "每一意外事故體傷責任AOA", "每一意外事故財損責任AOA", "保險期間內最高賠償金額AGG"};
		String valueAry[] =  {riskcode, kindcode, para01, para02, para03, para04, para05, para06, para07, para08, para09};
		
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


	public CalPremBaseService getCalPremBaseService() {
		return calPremBaseService;
	}

	public void setCalPremBaseService(CalPremBaseService calPremBaseService) {
		this.calPremBaseService = calPremBaseService;
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
	}



	public PbPremiumRate5Service getPbPremiumRate5Service() {
		return pbPremiumRate5Service;
	}



	public void setPbPremiumRate5Service(PbPremiumRate5Service pbPremiumRate5Service) {
		this.pbPremiumRate5Service = pbPremiumRate5Service;
	}



	public PbPremiumRate2Service getPbPremiumRate2Service() {
		return pbPremiumRate2Service;
	}



	public void setPbPremiumRate2Service(PbPremiumRate2Service pbPremiumRate2Service) {
		this.pbPremiumRate2Service = pbPremiumRate2Service;
	}



	public PbQueryDetailService getPbQueryDetailService() {
		return pbQueryDetailService;
	}



	public void setPbQueryDetailService(PbQueryDetailService pbQueryDetailService) {
		this.pbQueryDetailService = pbQueryDetailService;
	}
}
