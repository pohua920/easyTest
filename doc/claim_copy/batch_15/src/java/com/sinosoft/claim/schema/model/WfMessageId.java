package com.sinosoft.claim.schema.model;

import javax.persistence.Embeddable;

@Embeddable
public class WfMessageId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 属性保单号 */
	private String messageId = "";
	/** 属性行号 */
	private Integer lineNo = 0;
	/** 属性序号 */
	private Integer serialNo = 0;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

}
