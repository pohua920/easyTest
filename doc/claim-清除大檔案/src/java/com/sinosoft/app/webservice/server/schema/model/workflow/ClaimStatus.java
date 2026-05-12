package com.sinosoft.app.webservice.server.schema.model.workflow;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sinosoft.app.webservice.util.JaxbDateAdapter;

/**
 *自定义理赔查询对象
 * 
  */
@XmlRootElement(name="claimStatus")
@XmlAccessorType(XmlAccessType.FIELD)

public class ClaimStatus {
	/** 賠案號碼  */
	private String claimNo;
	/** 備案號碼 */
	private String registNo;
	/** 核心節點狀態 */
	private String stauts;

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getRegistNo() {
        return registNo;
    }

    public void setRegistNo(String registNo) {
        this.registNo = registNo;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
   
	
}
