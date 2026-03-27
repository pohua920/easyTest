package com.sinosoft.app.webservice.server.schema.model.common;

//import java.util.List;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLthirdParty;

//import com.sinosoft.app.webservice.server.schema.model.claimQuery.ClaimData;

/**
 *  mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@XmlRootElement(name="claimData")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespClaimRiskData {

	private String code;
	
	private String msg;
	
	private String registNo;
	
	private String type;
	
	private String claimNo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
