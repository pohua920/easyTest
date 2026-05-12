package com.sinosoft.app.webservice.server.schema.model.workflow;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="respWorkFlowResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespWorkFlowResult {
    
    private String returnCode;
    private String returnMsg;
    @XmlElementWrapper(name="claimStatusList")
    @XmlElement(name="claimStatus")
    private List<ClaimStatus> claimStatusList;
    
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
    public List<ClaimStatus> getClaimStatusList() {
        return claimStatusList;
    }
    public void setClaimStatusList(List<ClaimStatus> claimStatusList) {
        this.claimStatusList = claimStatusList;
    }

    
    
}
