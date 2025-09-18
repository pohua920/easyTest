package com.tlg.prpins.util;


public class PbFormula {
	
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
	 * 純保費 =基本純保費 *規模係數*期間係數*(1+自負額調整係數)*高保額係數* (1+AGG加費係數)*[(1+附加條款加費係數)]+附加條款加費

	 * WK_純保費 = (PPREM.RETURN_VALUE * PSCALE.PARA_VALUE * PPLACE.PARA_VALUE * (1 –PDCT.RETURN_VALUE) * PHAMT.RETURN_VALUE * (1 + UW.PARA_VALUE) * (1 + CLAUSE.ADD_PROPORTION)) + CLAUSE.ADD_PURE_PREM
	 */
	 public static final String PB = "basePurePrem.multiply(scaleFactor).multiply(scaleTypeFactor).multiply(BigDecimal.ONE.add(deductFactor)).multiply(highAmtFactor).multiply(BigDecimal.ONE.add(aggFactor)).multiply(BigDecimal.ONE.add(addProportion)).add(addPrem)";
	 
	 public static final String PB_ADJ = "basePurePrem.multiply(scaleFactor).multiply(scaleTypeFactor).multiply(BigDecimal.ONE.add(deductFactor)).multiply(highAmtFactor).multiply((BigDecimal.ONE.add(uwRate.divide(new BigDecimal(100))))).multiply(BigDecimal.ONE.add(aggFactor)).multiply(BigDecimal.ONE.add(addProportion)).add(addPrem)";
	 
}
