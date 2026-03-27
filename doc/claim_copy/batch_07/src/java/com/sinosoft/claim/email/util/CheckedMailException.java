package com.sinosoft.claim.email.util;

/**
 * 定义了邮件发送过程中使用的异常类，在该异常类中定义了邮件发送失败的原因。
 */
public class CheckedMailException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errMsg = "";

	public CheckedMailException(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
