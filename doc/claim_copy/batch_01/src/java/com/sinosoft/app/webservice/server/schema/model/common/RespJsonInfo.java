package com.sinosoft.app.webservice.server.schema.model.common;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement(name="jsonInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespJsonInfo {

	private String operateDate;
	
	private String stackTrace;

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	
}
