/*
* Description: 分保试算结果
* Author     : 阳光项目组
* CreateDate:  2005-6-4 14:37
* UpdateLog：  Name       Date            Reason/Contents
* ------------------------------------------------------
*/

package com.sinosoft.undwrt.undwrtDeal.vo;
import java.io.Serializable;
import java.util.Collection;

/**
 * The Class ReinsTrialDangerInfoVo.
 */
public class ReinsTrialDangerInfoVo implements Serializable{
     
    /** 屬性危險單位號. */
    private int dangerNo = 0;
    
    /** 屬性The sinosoft collection. */
    private Collection collection = null;

    /**
	 * 獲取屬性the sinosoft collection.
	 * 
	 * @return Returns the collection.
	 */
    public Collection getCollection() {
        return collection;
    }
    
    /**
	 * 設置屬性the sinosoft collection.
	 * 
	 * @param collection
	 *            The collection to set.
	 */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    
    /**
	 * 獲取屬性危險單位號.
	 * 
	 * @return Returns the dangerNo.
	 */
    public int getDangerNo() {
        return dangerNo;
    }
    
    /**
	 * 設置屬性危險單位號.
	 * 
	 * @param dangerNo
	 *            The dangerNo to set.
	 */
    public void setDangerNo(int dangerNo) {
        this.dangerNo = dangerNo;
    }
}
