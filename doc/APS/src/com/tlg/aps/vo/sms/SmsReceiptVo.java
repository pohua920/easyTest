package com.tlg.aps.vo.sms;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Receipt")
public class SmsReceiptVo {

	private String messageId;
	private String destAddress;
	private String deliveryStatus;
	private String errorCode;
	private String submitDate;
	private String doneDate;
	private String seq;

	@XmlElement(name = "MessageId")
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@XmlElement(name = "DestAddress")
	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	@XmlElement(name = "DeliveryStatus")
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@XmlElement(name = "ErrorCode")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@XmlElement(name = "SubmitDate")
	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	@XmlElement(name = "DoneDate")
	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	@XmlElement(name = "Seq")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}
