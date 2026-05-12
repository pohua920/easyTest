package com.sinosoft.app.webservice.server.schema.model.workflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reqWorkFlow")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqWorkFlow {

    /** Éí·Ö×C×ÖÌ–*/
    private String identifyNumber;

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    

    
}
