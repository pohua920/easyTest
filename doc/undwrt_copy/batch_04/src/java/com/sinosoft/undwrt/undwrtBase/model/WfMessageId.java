package com.sinosoft.undwrt.undwrtBase.model;

import javax.persistence.Embeddable;

/**
 * The Class WfMessageId.
 */
@Embeddable
public class WfMessageId implements java.io.Serializable{
	
    /** 属性保單號. */
    private String messageId = "";
    
    /** 属性行號. */
    private int lineNo = 0;
    
    /** 属性序號. */
    private int serialNo = 0;
	
	/**
	 * Gets the 属性保单号.
	 * 
	 * @return the 属性保单号
	 */
	public String getMessageId() {
		return messageId;
	}
	
	/**
	 * Sets the 属性保单号.
	 * 
	 * @param messageId
	 *            the new 属性保单号
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	/**
	 * Gets the 属性行号.
	 * 
	 * @return the 属性行号
	 */
	public int getLineNo() {
		return lineNo;
	}
	
	/**
	 * Sets the 属性行号.
	 * 
	 * @param lineNo
	 *            the new 属性行号
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	
	/**
	 * Gets the 属性序号.
	 * 
	 * @return the 属性序号
	 */
	public int getSerialNo() {
		return serialNo;
	}
	
	/**
	 * Sets the 属性序号.
	 * 
	 * @param serialNo
	 *            the new 属性序号
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
    

}
