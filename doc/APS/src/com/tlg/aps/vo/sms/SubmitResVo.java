package com.tlg.aps.vo.sms;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SubmitRes")
public class SubmitResVo {

	
	private String ResultCode;
	private String ResultText;
	private String MessageId;

	@XmlElement(name="ResultCode")
	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	@XmlElement(name="ResultText")
	public String getResultText() {
		return ResultText;
	}

	public void setResultText(String resultText) {
		ResultText = resultText;
	}

	@XmlElement(name="MessageId")
	public String getMessageId() {
		return MessageId;
	}

	public void setMessageId(String messageId) {
		MessageId = messageId;
	}

}
