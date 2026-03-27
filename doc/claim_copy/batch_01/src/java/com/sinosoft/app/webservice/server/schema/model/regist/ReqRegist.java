package com.sinosoft.app.webservice.server.schema.model.regist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimPrpLregistVo;

/**
 *  mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 *  接外部-無須產出
 */
@XmlRootElement(name="reqRegistQuery")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqRegist {
	private String sendJson;
	public String getSendJson() {
		return sendJson;
	}
	public void setSendJson(String sendJson) {
		this.sendJson = sendJson;
	}
}
