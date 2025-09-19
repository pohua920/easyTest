package com.tlg.msSqlSms.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

public class Receipt extends IBatisBaseEntity<BigDecimal>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal oid;
	private String messageId;
	private String destAddress;
	private String deliveryStatus;
	private String errorCode;
	private String submitDate;
	private String doneDate;
	private String retrieveDate;
	private String seq;

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(String retrieveDate) {
		this.retrieveDate = retrieveDate;
	}

}
