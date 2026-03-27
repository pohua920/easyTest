package com.sinosoft.undwrt.undwrtDeal.vo;

/**
 * The Class TaskDealVo.
 * 
 * @author xumingjie created on 2005-8-23
 */
public class TaskDealVo
{
    
    /** 屬性工作流號. */
    private String flowId = null;
    
    /** 屬性業務號. */
    private String businessNo = null;
    
    /** 屬性The sinosoft submitted. */
    private boolean submitted = false;
    
    /** 屬性要請求的ip地址. */
    private String submitTip = null;
    
    /**
	 * Instantiates a new task deal vo.
	 */
    public TaskDealVo()
    {}
    
    /**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
    public String getFlowId()
    {
        return flowId;
    }
    
    /**
	 * 設置屬性工作流號.
	 * 
	 * @param flowId
	 *            待設置的工作流號的值
	 */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }
    
    /**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
    public String getBusinessNo()
    {
        return businessNo;
    }
    
    /**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            待設置的業務號的值
	 */
    public void setBusinessNo(String businessNo)
    {
        this.businessNo = businessNo;
    }
    
    /**
	 * Checks if is submitted.
	 * 
	 * @return true, if is submitted
	 */
    public boolean isSubmitted()
    {
        return submitted;
    }
    
    /**
	 * 設置屬性the sinosoft submitted.
	 * 
	 * @param submitted
	 *            待設置的the sinosoft submitted的值
	 */
    public void setSubmitted(boolean submitted)
    {
        this.submitted = submitted;
    }
    
    /**
	 * 獲取屬性要請求的ip地址.
	 * 
	 * @return 屬性要請求的ip地址的值
	 */
    public String getSubmitTip()
    {
        return submitTip;
    }
    
    /**
	 * 設置屬性要請求的ip地址.
	 * 
	 * @param submitTip
	 *            待設置的要請求的ip地址的值
	 */
    public void setSubmitTip(String submitTip)
    {
        this.submitTip = submitTip;
    }
}