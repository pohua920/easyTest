package com.sinosoft.claim.common.util;

/***
 * 異常：工作流任務校驗是否已處理，已處理則拋出此異常訊息。
 * @author 中科軟
 *
 */
public class ProcessTokenException extends Exception {
	
	private String detailMessage;
	
	private static final long serialVersionUID = 1L;

	public ProcessTokenException() {		
	}

	public ProcessTokenException(String message) {
		super(message);
		this.detailMessage = message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
	
}
