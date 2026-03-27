/*
 * Created on 2005-7-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.undwrt.undwrtDeal.vo;
import java.io.Serializable;

/**
 * The Class CommonDangerUnitSerialNoVo.
 */
public class CommonDangerUnitSerialNoVo implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = CommonDangerUnitSerialNoVo.class.getName().hashCode();
    
    /** 屬性業務號. */
    private String businessNo ="";
    
    /** 屬性危險單位號. */
    private String dangerNo = "" ;

    /**
	 * 獲取屬性業務號.
	 * 
	 * @return Returns the businessNo.
	 */
    public String getBusinessNo() {
        return businessNo;
    }
    
    /**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            The businessNo to set.
	 */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    /**
	 * 獲取屬性危險單位號.
	 * 
	 * @return Returns the dangerNo.
	 */
    public String getDangerNo() {
        return dangerNo;
    }
    
    /**
	 * 設置屬性危險單位號.
	 * 
	 * @param dangerNo
	 *            The dangerNo to set.
	 */
    public void setDangerNo(String dangerNo) {
        this.dangerNo = dangerNo;
    }
}
