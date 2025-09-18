package com.tlg.prpins.util;

public class FirF02Formula {
	
	/**
	 * WK_出單保費 =接收參數.PARA_01 / TMP_地震保額上限 * TMP_地震保費
	 */
	 public static final String FR2 = "para01.divide(maxAmount, 10, BigDecimal.ROUND_HALF_UP).multiply(earthquakePrem)"; 
	 
//	 /**
//	  *  危險費率 = 基本費率 * (1 + 高樓加費 + 營業加費 – 消防設備減費)
//		 TMP_危險費率 = BASE.PARA_VALUE * (1 + HIGH.PARA_VALUE + TMP_營業加費)
//	  */
//	 public static final String FR3 = "(para01.divide(new BigDecimal(1000), 10, BigDecimal.ROUND_HALF_UP)).multiply(baseRate.multiply(BigDecimal.ONE.add(highBuildFee).add(businessFee)))";
	 
	 /**
	  * TMP_出單費率 = BASE.PARA_VALUE * (1 + HIGH.PARA_VALUE + TMP_營業加費) / (1 – WK_附加費用率) –-四捨五入至小數第三位
	  * WK_出單保費 = (接收參數.PARA_01 / 1000) * TMP_出單費率 –-四捨五入至整數位
	  */
	 public static final String FR3 = "(para01.divide(new BigDecimal(1000), 10, BigDecimal.ROUND_HALF_UP)).multiply(baseRate.multiply(BigDecimal.ONE.add(highBuildFee).add(businessFee)).divide(BigDecimal.ONE.subtract(surchargeRate), 3, BigDecimal.ROUND_HALF_UP))";
	 
	 /**
	  * 家庭日常生活責任純保險費 = 賠償金基本純保險費 × (1 - 自負額扣減率) + 慰問金純保險費
		WK_純保費 = CPS.RETURN_VALUE * (1 – DCT.RETURN_VALUE) + CSL.RETURN_VALUE
	  */
	 public static final String RFE = "cpsPurePrem.multiply(BigDecimal.ONE.subtract(dctRate)).add(cslPrem)";
	 
	 /**
	  * 13.	RF01-擴大家庭災害費用補償
	  * 擴大家庭災害費用補償純保險費 = 基本事故純保險費 + 地震事故純保險費 + 颱風及洪水事故純保險費
		WK_純保費 = BASE.RETURN_VALUE + EQ.RETURN_VALUE + TB.RETURN_VALUE

	  */
	 public static final String RF01 = "basePrem.add(eqPrem).add(tbPrem)";
	 
	 /**
	  * WK_純保費 = 接收參數.PARA_01 / 10000 * HS.RETURN_VALUE + CL.RETURN_VALUE
		WK_出單保費 = WK_純保費 / (1 – WK_附加費用率)	--四捨五入至整數位。

	  */
	 public static final String RF04 = "para01.divide(new BigDecimal(10000), 10, BigDecimal.ROUND_HALF_UP).multiply(hsRate).add(clPrem)";

	 /**
	  *	租金補償保險金額/10,000 × 約定月數 × 房屋租金補償基本純費率 + 清理費用純保險費
		WK_純保費 = 接收參數.PARA_01 / 10000 * HS.RETURN_VALUE * MONTH.RETURN_VALUE + CL.RETURN_VALUE
	  */
	 public static final String RF05 = "(para01.divide(new BigDecimal(10000), 10, BigDecimal.ROUND_HALF_UP)).multiply(hsRate).multiply(month).add(clPrem)";
	 
	 /**
	  *	RF08-住宅輕損地震損失
		WK_純保費 = AMT.RETURN_VALUE * (1 – DCT.RETURN_VALUE)
	  */
	 public static final String RF08 = "amtPrem.multiply(BigDecimal.ONE.subtract(dctRate))";
	 
	 /**
	  *	RF09-住宅火險附加家庭成員傷害保險
			 WK_純保費 = (接收參數.PARA_01 / 10000 * DEAD.RETURN_VALUE)
			 + (接收參數.PARA_02 / 10000 * DISA.RETURN_VALUE)
			 + (接收參數.PARA_03 / 1000 * HOSP.RETURN_VALUE)
			 + (接收參數.PARA_04 / 1000 * ICU.RETURN_VALUE)
			 + (接收參數.PARA_05 / 1000 * HCSL.RETURN_VALUE)
	  */
	 public static final String RF09 = "para01.divide(new BigDecimal(10000), 10, BigDecimal.ROUND_HALF_UP).multiply(deadRate)." +
	 		"add(para02.divide(new BigDecimal(10000), 10, BigDecimal.ROUND_HALF_UP).multiply(disaRate))." +
	 		"add(para03.divide(new BigDecimal(1000), 10, BigDecimal.ROUND_HALF_UP).multiply(hospRate))." +
	 		"add(para04.divide(new BigDecimal(1000), 10, BigDecimal.ROUND_HALF_UP).multiply(icuRate))." +
	 		"add(para05.divide(new BigDecimal(1000), 10, BigDecimal.ROUND_HALF_UP).multiply(hcslRate))";
	 
	 /**
	  *	RF10-住家綠能升級附加條款
		WK_純保費 = TMP_主險純保費 * RETURN_VALUE
	  */
	 public static final String RF10 = "mainInsPrem.multiply(greenFactor)";

}
