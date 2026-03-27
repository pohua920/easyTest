package com.sinosoft.undwrt.undwrtRule.service;

/**
 * 險別保額存儲類.
 */
public class UndwrtRuleRiskKind {

    /**
	 * 獲取屬性險別代碼.
	 * 
	 * @return 屬性險別代碼的值
	 */
    public String getKindCode() {
        return kindCode;
    }

    /**
	 * 設置屬性險別代碼.
	 * 
	 * @param kindCode
	 *            待設置的險別代碼的值
	 */
    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    /** 屬性險別代碼. */
    private String kindCode;
	

	/**
	 * 獲取屬性險別保額.
	 * 
	 * @return 屬性險別保額的值
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 設置屬性險別保額.
	 * 
	 * @param amount
	 *            待設置的險別保額的值
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	

	public double getKindCostRate() {
		return kindCostRate;
	}

	public void setKindCostRate(double kindCostRate) {
		this.kindCostRate = kindCostRate;
	}

	/** 屬性險別保額. */
	private double amount;
	
	private double kindCostRate;
}
