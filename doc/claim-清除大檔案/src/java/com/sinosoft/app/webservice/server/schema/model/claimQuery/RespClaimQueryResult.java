package com.sinosoft.app.webservice.server.schema.model.claimQuery;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="respClaimQueryResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespClaimQueryResult {
	
	private String returnCode;
	private String returnMsg;
	
	@XmlElementWrapper(name="claimList")
	@XmlElement(name="claimData")
	private List<ClaimData> claimList;
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

    public List<ClaimData> getClaimList() {
        return claimList;
    }

    public void setClaimList(List<ClaimData> claimList) {
        this.claimList = claimList;
    }
	
}
