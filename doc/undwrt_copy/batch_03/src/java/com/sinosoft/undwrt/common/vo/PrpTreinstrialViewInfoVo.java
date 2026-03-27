/*
 * Description: 分保试算数据公共显示Dto
 * Author : 阳光项目组
 * 
 */
package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;


/**
 * The Class PrpTreinstrialViewInfoVo.
 */
public class PrpTreinstrialViewInfoVo implements Serializable {
 
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = PrpTreinstrialViewInfoVo.class.getName().hashCode();
    
    /** 属性businessNo. */
    private String businessNo = "";
    
    /** 属性DangerNo. */
    private int dangerNo = 0;
    
    /** 属性SerialNo. */
    private int serialNo = 0;
    
    /** 属性DangerDesc. */
    private String dangerDesc = "";
    
    /** 屬性The sinosoft ref no. */
    private String refNo  = "";        //合约简称
    
    /** 属性ReinsMode. */
    private String reinsMode = "";
    
    /** 属性TreatyNo. */
    private String treatyNo = "";
    
    /** 属性SectionNo. */
    private String sectionNo = "";
    
    /** 属性ShareRate. */
    private double shareRate = 0D;
    
    /** 属性Currency. */
    private String currency = "";
    
    /** 属性Amount. */
    private double amount = 0D;
    
    /** 属性Premium. */
    private double premium = 0D;
    
    /** 属性Commission. */
    private double commission = 0D;
    
    /** 屬性The sinosoft chg amount. */
    private double chgAmount  = 0D;
    
    /** 屬性The sinosoft chg premium. */
    private double chgPremium = 0D;
    
    /** 屬性The sinosoft chg commission. */
    private double chgCommission = 0D;  
    
    /** 屬性The sinosoft exchratecny. */
    private double exchratecny = 0D;
    
    /** 属性Flag. */
    private String flag = "";
    
    
    /**
	 * 獲取屬性the sinosoft ref no.
	 * 
	 * @return Returns the refNo.
	 */
    public String getRefNo() {
        return refNo;
    }
    
    /**
	 * 設置屬性the sinosoft ref no.
	 * 
	 * @param refNo
	 *            The refNo to set.
	 */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }
    
    /**
	 * Gets the 属性Amount.
	 * 
	 * @return Returns the amount.
	 */
    public double getAmount() {
        return amount;
    }
    
    /**
	 * Sets the 属性Amount.
	 * 
	 * @param amount
	 *            The amount to set.
	 */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    /**
	 * Gets the 属性businessNo.
	 * 
	 * @return Returns the businessNo.
	 */
    public String getBusinessNo() {
        return businessNo;
    }
    
    /**
	 * Sets the 属性businessNo.
	 * 
	 * @param businessNo
	 *            The businessNo to set.
	 */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    /**
	 * Gets the 属性Commission.
	 * 
	 * @return Returns the commission.
	 */
    public double getCommission() {
        return commission;
    }
    
    /**
	 * Sets the 属性Commission.
	 * 
	 * @param commission
	 *            The commission to set.
	 */
    public void setCommission(double commission) {
        this.commission = commission;
    }
    
    /**
	 * Gets the 属性Currency.
	 * 
	 * @return Returns the currency.
	 */
    public String getCurrency() {
        return currency;
    }
    
    /**
	 * Sets the 属性Currency.
	 * 
	 * @param currency
	 *            The currency to set.
	 */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /**
	 * Gets the 属性DangerDesc.
	 * 
	 * @return Returns the dangerDesc.
	 */
    public String getDangerDesc() {
        return dangerDesc;
    }
    
    /**
	 * Sets the 属性DangerDesc.
	 * 
	 * @param dangerDesc
	 *            The dangerDesc to set.
	 */
    public void setDangerDesc(String dangerDesc) {
        this.dangerDesc = dangerDesc;
    }
    
    /**
	 * Gets the 属性DangerNo.
	 * 
	 * @return Returns the dangerNo.
	 */
    public int getDangerNo() {
        return dangerNo;
    }
    
    /**
	 * Sets the 属性DangerNo.
	 * 
	 * @param dangerNo
	 *            The dangerNo to set.
	 */
    public void setDangerNo(int dangerNo) {
        this.dangerNo = dangerNo;
    }
    
    /**
	 * Gets the 属性Flag.
	 * 
	 * @return Returns the flag.
	 */
    public String getFlag() {
        return flag;
    }
    
    /**
	 * Sets the 属性Flag.
	 * 
	 * @param flag
	 *            The flag to set.
	 */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    /**
	 * Gets the 属性Premium.
	 * 
	 * @return Returns the premium.
	 */
    public double getPremium() {
        return premium;
    }
    
    /**
	 * Sets the 属性Premium.
	 * 
	 * @param premium
	 *            The premium to set.
	 */
    public void setPremium(double premium) {
        this.premium = premium;
    }
    
    /**
	 * Gets the 属性ReinsMode.
	 * 
	 * @return Returns the reinsMode.
	 */
    public String getReinsMode() {
        return reinsMode;
    }
    
    /**
	 * Sets the 属性ReinsMode.
	 * 
	 * @param reinsMode
	 *            The reinsMode to set.
	 */
    public void setReinsMode(String reinsMode) {
        this.reinsMode = reinsMode;
    }
    
    /**
	 * Gets the 属性SectionNo.
	 * 
	 * @return Returns the sectionNo.
	 */
    public String getSectionNo() {
        return sectionNo;
    }
    
    /**
	 * Sets the 属性SectionNo.
	 * 
	 * @param sectionNo
	 *            The sectionNo to set.
	 */
    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }
    
    /**
	 * Gets the 属性SerialNo.
	 * 
	 * @return Returns the serialNo.
	 */
    public int getSerialNo() {
        return serialNo;
    }
    
    /**
	 * Sets the 属性SerialNo.
	 * 
	 * @param serialNo
	 *            The serialNo to set.
	 */
    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }
    
    /**
	 * Gets the 属性ShareRate.
	 * 
	 * @return Returns the shareRate.
	 */
    public double getShareRate() {
        return shareRate;
    }
    
    /**
	 * Sets the 属性ShareRate.
	 * 
	 * @param shareRate
	 *            The shareRate to set.
	 */
    public void setShareRate(double shareRate) {
        this.shareRate = shareRate;
    }
    
    /**
	 * Gets the 属性TreatyNo.
	 * 
	 * @return Returns the treatyNo.
	 */
    public String getTreatyNo() {
        return treatyNo;
    }
    
    /**
	 * Sets the 属性TreatyNo.
	 * 
	 * @param treatyNo
	 *            The treatyNo to set.
	 */
    public void setTreatyNo(String treatyNo) {
        this.treatyNo = treatyNo;
    }
    
    /**
	 * 獲取屬性the sinosoft chg amount.
	 * 
	 * @return 屬性the sinosoft chg amount的值
	 */
    public double getChgAmount() {
        return chgAmount;
    }
    
    /**
	 * 設置屬性the sinosoft chg amount.
	 * 
	 * @param chgAmount
	 *            待設置的the sinosoft chg amount的值
	 */
    public void setChgAmount(double chgAmount) {
        this.chgAmount = chgAmount;
    }
    
    /**
	 * 獲取屬性the sinosoft chg commission.
	 * 
	 * @return 屬性the sinosoft chg commission的值
	 */
    public double getChgCommission() {
        return chgCommission;
    }
    
    /**
	 * 設置屬性the sinosoft chg commission.
	 * 
	 * @param chgCommission
	 *            待設置的the sinosoft chg commission的值
	 */
    public void setChgCommission(double chgCommission) {
        this.chgCommission = chgCommission;
    }
    
    /**
	 * 獲取屬性the sinosoft chg premium.
	 * 
	 * @return 屬性the sinosoft chg premium的值
	 */
    public double getChgPremium() {
        return chgPremium;
    }
    
    /**
	 * 設置屬性the sinosoft chg premium.
	 * 
	 * @param chgPremium
	 *            待設置的the sinosoft chg premium的值
	 */
    public void setChgPremium(double chgPremium) {
        this.chgPremium = chgPremium;
    }
	
	/**
	 * 獲取屬性the sinosoft exchratecny.
	 * 
	 * @return 屬性the sinosoft exchratecny的值
	 */
	public double getExchratecny() {
		return exchratecny;
	}
	
	/**
	 * 設置屬性the sinosoft exchratecny.
	 * 
	 * @param exchratecny
	 *            待設置的the sinosoft exchratecny的值
	 */
	public void setExchratecny(double exchratecny) {
		this.exchratecny = exchratecny;
	}
    
}
