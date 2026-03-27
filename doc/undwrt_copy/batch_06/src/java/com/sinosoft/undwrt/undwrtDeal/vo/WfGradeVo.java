package com.sinosoft.undwrt.undwrtDeal.vo;

import java.io.Serializable;

import com.sinosoft.undwrt.undwrtBase.model.WfGrade;

/**
 * 这是wfgrade的数据传输对象类<br>
 * 创建于 JToolpad(1.5.1) Vendor:zhouxianli1978@msn.com
 */
public class WfGradeVo extends WfGrade implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** 属性riskCode. */
    private String riskCode = "";
    
    /** 属性classCode. */
    private String classCode = "";
    
    /** 属性HistoryBusiness. */
    private String HistoryBusiness = "";
    
    /**
	 * 默认构造方法,构造一个默认的WfgradeDto对象.
	 */
    public WfGradeVo(){
    }
	
	/**
	 * Gets the 属性classCode.
	 * 
	 * @return the 属性classCode
	 */
	public String getClassCode() {
		return classCode;
	}
	
	/**
	 * Sets the 属性classCode.
	 * 
	 * @param classCode
	 *            the new 属性classCode
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
	/**
	 * Gets the 属性riskCode.
	 * 
	 * @return the 属性riskCode
	 */
	public String getRiskCode() {
		return riskCode;
	}
	
	/**
	 * Sets the 属性riskCode.
	 * 
	 * @param riskCode
	 *            the new 属性riskCode
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	/**
	 * 獲取屬性the sinosoft history business.
	 * 
	 * @return 屬性the sinosoft history business的值
	 */
	public String getHistoryBusiness() {
		return HistoryBusiness;
	}
	
	/**
	 * 設置屬性the sinosoft history business.
	 * 
	 * @param historyBusiness
	 *            待設置的the sinosoft history business的值
	 */
	public void setHistoryBusiness(String historyBusiness) {
		HistoryBusiness = historyBusiness;
	}
}
