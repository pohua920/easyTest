/*
 * Created on 2005-6-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.common.vo;
import java.io.Serializable;

/**
 * The Class PrpCurrencyTypeVo.
 * 
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrpCurrencyTypeVo implements Serializable{
    
    /**
	 * 獲取屬性the sinosoft currency type.
	 * 
	 * @return Returns the currencyType.
	 */
    public String getCurrencyType() {
        return currencyType;
    }
    
    /**
	 * 設置屬性the sinosoft currency type.
	 * 
	 * @param currencyType
	 *            The currencyType to set.
	 */
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
    
    /** 屬性The sinosoft currency type. */
    private String currencyType;
    

}
