package com.tlg.prpins.bs.premCalculate;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;

public interface CalPremBaseService {
	
	
	/**
	 * 讀取火險附加費用率
	 * 
	 * @param riskcode 險種代碼,例如：F02
	 * @param kindcode 險別代碼,例如：RFC
	 * @param channelType 通路別,例如：11
	 * @param calcDate 費率基準日,例如：20190601
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal getSurchargeRate(String riskcode, String kindcode, String channelType, String calcDate)throws SystemException,Exception;
	
	/**
	 * 取得火險費用率
	 * 
	 * @param riskcode 險種代碼,例如：F02
	 * @param kindcode 險別代碼,例如：RFC
	 * @param tableType 參數類型,例如：0 = FIR_PREMIUM_RATE火險費率檔_0、1 = FIR_PREMIUM_RATE_1火險費率檔_1、2 = FIR_PREMIUM_RATE_2火險費率檔_2、3 = FIR_PREMIUM_RATE_2火險費率檔_3、
	 * @param calcDate 費率基準日,例如：20190601
	 * @param paraType 參數類型，非必填。
	 * @param para01 參數1，非必填。
	 * @param para02 參數2，非必填。
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal getFirInsRate(String riskcode, String kindcode, String tableType, String calcDate, String paraType, String para01, String para02, String para03, String para04)throws SystemException,Exception;
	
	/**
	 * 取得公共意外責任險費用率
	 * 
	 * @param riskcode 險種代碼,例如：PB
	 * @param kindcode 險別代碼,例如：PB
	 * @param tableType 參數類型,例如：0 = PB_PREMIUM_RATE_0公共意外責任費率檔_0、1 = PB_PREMIUM_RATE_5公共意外責任費率檔_5、2 = PB_PREMIUM_RATE_6公共意外責任費率檔_6、3 = PB_PREMIUM_RATE_7公共意外責任費率檔_7、4 = PB_PREMIUM_RATE_8公共意外責任費率檔_8、5 = PB_PREMIUM_RATE_9公共意外責任費率檔_9、6 = PB_PREMIUM_RATE_10公共意外責任費率檔_10、7 = PB_PREMCALC_ADDITION_TERM公共意外責任保險附約加費檔、8 = PB_DANGER_GRADE 公共意外責任保險附加費用檔
	 * @param calcDate 費率基準日,例如：20190601
	 * @param paraType 參數類型，非必填。
	 * @param para01 參數1，非必填。
	 * @param para02 參數2，非必填。
	 * @param para03 參數3，非必填。
	 * @param para04 參數4，非必填。
	 * @throws SystemException
	 * @throws Exception
	 */
	public Map getPbInsRate(String riskcode, String kindcode, String tableType, String calcType, String calcDate, String paraType, String para01, String para02, String para03, String para04)throws SystemException,Exception;

	
	public BigDecimal beanShellCalculate(String formula, Map<String,BigDecimal> paramsMap) throws SystemException, Exception;
	/**
	 * 由出單保費回推純保費
	 * 
	 * @param prem
	 * @param surchargeRate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal calculatePurePrem(BigDecimal prem, BigDecimal surchargeRate) throws SystemException, Exception;
	
	/**
	 * 由純保費推出單保費
	 * 
	 * @param purePrem
	 * @param surchargeRate
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal calculatePrem(BigDecimal purePrem, BigDecimal surchargeRate) throws SystemException, Exception;

	/**
	 * 取得自負額扣檢率
	 * 
	 * @param riskcode 險種代碼，必填。	例如：F01
	 * @param kindcode 險別代碼，必填。	例如：FB1
	 * @param calcDate 費率基準日，必填。	例如：20190601
	 * @param para01 總保額，例如：50000000
	 * @param para02 自負額比例，例如10，代表10%。
	 * @param para03 自負額金額，例如MIN30000
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public double getDeductionRate(String riskcode, String kindcode, String calcDate, String para01, String para02, String para03) throws SystemException, Exception;
	
	/**
	 * 商火速算-營業狀況詢問表處理(用於計算核保技術調整係數)
	 * 
	 * @param oidFirPremcalcTmp 試算的主檔OID
	 * @param calcDate 主檔資料.CALC_DATE 
	 * @param riskcode 險種代碼
	 * @param kindcode 險別代碼
	 * @return BigDecimal Array {核保調整係數_火險主險 , 核保調整係數_非天災附加險 , 核保調整係數_天災附加險 }
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal[] getFb1PremcalcCkList(BigDecimal oidFirPremcalcTmp, String calcDate, String riskcode, String kindcode) throws SystemException, Exception;
	
	/**
	 * 
	 * 取得承保多種附加險優待率
	 * 
	 * @param oidFirPremcalcTmp 試算的主檔OID
	 * @param calcDate 主檔資料.CALC_DATE 
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	public BigDecimal getFb1DiscountRate(FirPremcalcTmp firPremcalcTmp, String riskcode, String kindcode, BigDecimal oidFirPremcalcTmp, String calcDate) throws SystemException, Exception;
}
