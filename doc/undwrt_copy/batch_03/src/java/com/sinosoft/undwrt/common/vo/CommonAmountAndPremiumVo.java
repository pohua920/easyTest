/*
 * Created on 2005-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;

/**
 * The Class CommonAmountAndPremiumVo.
 * 
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonAmountAndPremiumVo implements Serializable{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = CommonAmountAndPremiumVo.class.getName().hashCode();
    
    /** 屬性The sinosoft bussiness no. */
    private String bussinessNo   ="";
    
    /** 屬性The sinosoft bussiness type. */
    private String bussinessType ="";
    
    /** 屬性The sinosoft Amount. */
    private double Amount        =0d;
    
    /** 屬性The sinosoft Premium. */
    private double Premium       =0d;
    

    /**
	 * 獲取屬性the sinosoft amount.
	 * 
	 * @return Returns the amount.
	 */
    public double getAmount() {
        return Amount;
    }
    
    /**
	 * 設置屬性the sinosoft amount.
	 * 
	 * @param amount
	 *            The amount to set.
	 */
    public void setAmount(double amount) {
        Amount = amount;
    }
    
    /**
	 * 獲取屬性the sinosoft bussiness no.
	 * 
	 * @return Returns the bussinessNo.
	 */
    public String getBussinessNo() {
        return bussinessNo;
    }
    
    /**
	 * 設置屬性the sinosoft bussiness no.
	 * 
	 * @param bussinessNo
	 *            The bussinessNo to set.
	 */
    public void setBussinessNo(String bussinessNo) {
        this.bussinessNo = bussinessNo;
    }
    
    /**
	 * 獲取屬性the sinosoft bussiness type.
	 * 
	 * @return Returns the bussinessType.
	 */
    public String getBussinessType() {
        return bussinessType;
    }
    
    /**
	 * 設置屬性the sinosoft bussiness type.
	 * 
	 * @param bussinessType
	 *            The bussinessType to set.
	 */
    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }
    
    /**
	 * 獲取屬性the sinosoft premium.
	 * 
	 * @return Returns the premium.
	 */
    public double getPremium() {
        return Premium;
    }
    
    /**
	 * 設置屬性the sinosoft premium.
	 * 
	 * @param premium
	 *            The premium to set.
	 */
    public void setPremium(double premium) {
        Premium = premium;
    }
}
