package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * ★★★★★警告：本文件不允许手工修改！！！请使用JToolpad生成！<br>
 * 这是PrpTexpense-投保兑换率表的数据传输对象基类<br>
 * 创建于 JToolpad(1.4.0) Vendor:zhouxianli1978@msn.com
 */
public class PrpTexpenseVo implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = PrpTexpenseVo.class.getName().hashCode();
    //proposalno,riskcode,managefeerate,maxmanagefeerate,flag
    /** 属性投保单号. */
    private String proposalNo = "";
    
    /** 属性险种. */
    private String riskCode = "";
    
    /** 属性费用比例. */
    private double manageFeeRate = 0D;
    
    /** 属性最大费用比例. */
    private double maxManageFeeRate = 0D;
    
    /** 属性标志位. */
    private String flag = "";

    /**
	 * 默认构造方法,构造一个默认的PrpTexpenseDtoBase对象.
	 */
    public PrpTexpenseVo(){
    }

    /**
	 * 设置属性投保单号.
	 * 
	 * @param proposalNo
	 *            待设置的属性投保单号的值
	 */
    public void setProposalNo(String proposalNo){
        this.proposalNo = StringUtils.rightTrim(proposalNo);
    }

    /**
	 * 获取属性投保单号.
	 * 
	 * @return 属性投保单号的值
	 */
    public String getProposalNo(){
        return proposalNo;
    }

    /**
	 * 设置属性原币币种.
	 * 
	 * @param riskCode
	 *            待设置的属性原币币种的值
	 */
    public void setRiskCode(String riskCode){
        this.riskCode = StringUtils.rightTrim(riskCode);
    }

    /**
	 * 获取属性原币币种.
	 * 
	 * @return 属性原币币种的值
	 */
    public String getRiskCode(){
        return riskCode;
    }

    /**
	 * 设置属性兑换率.
	 * 
	 * @param manageFeeRate
	 *            the new 属性费用比例
	 */
    public void setManageFeeRate(double manageFeeRate){
        this.manageFeeRate = manageFeeRate;
    }

    /**
	 * 获取属性兑换率.
	 * 
	 * @return 属性兑换率的值
	 */
    public double getManageFeeRate(){
        return manageFeeRate;
    }

    /**
	 * 设置属性保额.
	 * 
	 * @param maxManageFeeRate
	 *            the new 属性最大费用比例
	 */
    public void setMaxManageFeeRate(double maxManageFeeRate){
        this.maxManageFeeRate = maxManageFeeRate;
    }

    /**
	 * 获取属性保额.
	 * 
	 * @return 属性保额的值
	 */
    public double getMaxManageFeeRate(){
        return maxManageFeeRate;
    }

    /**
	 * 设置属性预留.
	 * 
	 * @param flag
	 *            待设置的属性预留的值
	 */
    public void setFlag(String flag){
        this.flag = StringUtils.rightTrim(flag);
    }

    /**
	 * 获取属性预留.
	 * 
	 * @return 属性预留的值
	 */
    public String getFlag(){
        return flag;
    }
}
