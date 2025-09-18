package com.tlg.prpins.util;


public class FirF01Formula {
	
	/**
	 * WK_出單保費 = 接收參數.PARA_01 * PRATE.RETURN_VALUE / 1000 //四捨五入至整數位
	 */
	 public static final String QFB1 = "para01.multiply(prate).divide(new BigDecimal(1000), 0, BigDecimal.ROUND_HALF_UP)"; 
	 
	/**
	 * WK_出單保費 = 接收參數.PARA_01 * PRATE.RETURN_VALUE / 1000 //四捨五入至整數位
	 */
	 public static final String QB1 = "para01.multiply(prate).divide(new BigDecimal(1000), 0, BigDecimal.ROUND_HALF_UP)"; 

	/**
	 * WK_出單保費 = 接收參數.PARA_01 * PRATE.RETURN_VALUE / 1000 //四捨五入至整數位
	 */
	 public static final String QBB = "para01.multiply(prate).divide(new BigDecimal(1000), 0, BigDecimal.ROUND_HALF_UP)"; 	 
	 
	/**
	 * 純保費 = (基本純保險費 * 規模係數 * 多處所係數 * (1+自負額調整係數) * 高保額係數 * (1+(核保加減費係數/100)) * (1 + 附約加費比率)) + 附約加費純保費
	 * WK_純保費 = (PPREM.RETURN_VALUE * PSCALE.PARA_VALUE * PPLACE.PARA_VALUE * (1 –PDCT.RETURN_VALUE) * PHAMT.RETURN_VALUE * (1 + UW.PARA_VALUE) * (1 + CLAUSE.ADD_PROPORTION)) + CLAUSE.ADD_PURE_PREM

	 */
	 public static final String QPB = "pprem.multiply(pscale).multiply(pplace).multiply(BigDecimal.ONE.add(pdct)).multiply(phamt).multiply(BigDecimal.ONE.add(uwRate.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP))).multiply(BigDecimal.ONE.add(addProportion)).multiply(BigDecimal.ONE.add(aggFactor)).add(addPurePrem)";
	 
	 /**
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–自負額加減費) * (1 + 高樓加費 + 營業加費) * (1 ＋ TMP_核保調整係數_火險主險)	–-四捨五入至小數第三位
	  */
	 public static final String FB1 = "baseRate.multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(highFee).add(businessFee)).multiply(BigDecimal.ONE.add(undwrtFactor))";
	 
	 /**
	  * 危險費率＝基本危險費率×（1－承保多種附加險優待率）×（1－自負額扣減率）×（1＋核保技術調整係數）
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE) * (1+ TMP_核保調整係數_非天災附加險) 
	  */
	 public static final String B1 = "baseRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";

	 /**
	  * 危險費率＝基本危險費率×（1－承保多種附加險優待率）×（1－自負額扣減率）×（1＋核保技術調整係數）
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE) * (1+ TMP_核保調整係數_非天災附加險) 
	  */
	 public static final String B4 = "baseRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";
	 
	 /**
	  * 危險費率＝基本危險費率×（1－承保多種附加險優待率）×（1－自負額扣減率）×（1＋核保技術調整係數）
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE) * (1+ TMP_核保調整係數_非天災附加險) 
	  */
	 public static final String B5 = "baseRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";

	 /**
	  *  WK_危險保費_1 = ((接收參數.PARA_01 / 1,000) * TMP_BRATE_1 +  (接收參數.PARA_02 / 1,000) * TMP_BRATE_2) * (1 - TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE)* (1 + TMP_核保調整係數_非天災附加險)
	  */
	 public static final String B6_1 = "buildRiskRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";

	 /**
	  *  WK_危險保費_2 = ((接收參數.PARA_01 / 1,000) * TMP_BRATE_1 +  (接收參數.PARA_02 / 1,000) * TMP_BRATE_2) * (1 - TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE)* (1 + TMP_核保調整係數_非天災附加險)
	  */
	 public static final String B6_2 = "nonBuildRiskRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";
	 
	 /**
	  * 	WK_基本危險費率  = ((BASE.RETURN_VALUE + WK_級距保費加總) / (接收參數.PARA_01 + 接收參數.PARA_04)) * 1,000  	--四捨五入至小數第三位
	  */
	 public static final String B7 = "(baseRate.add(classIntervalPrem)).divide(amount, 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000))";
	 
	 /**
	  * 危險費率＝基本危險費率×（1－承保多種附加險優待率）×（1－自負額扣減率）×（1＋核保技術調整係數）
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE) * (1+ TMP_核保調整係數_非天災附加險) 
	  */
	 public static final String BB = "baseRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";

	 /**
	  * 危險費率＝基本危險費率×（1－承保多種附加險優待率）×（1－自負額扣減率）×（1＋核保技術調整係數）
	  * WK_危險費率 = BASE.RETURN_VALUE * (1–TMP_承保多種附加險優待率) * (1–DT.RETURN_VALUE) * (1+ TMP_核保調整係數_非天災附加險) 
	  */
	 public static final String BD = "baseRate.multiply(BigDecimal.ONE.subtract(discountRate)).multiply(BigDecimal.ONE.subtract(deductionRate)).multiply(BigDecimal.ONE.add(undwrtFactor))";

	 

}
