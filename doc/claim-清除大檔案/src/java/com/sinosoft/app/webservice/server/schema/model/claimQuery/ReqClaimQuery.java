package com.sinosoft.app.webservice.server.schema.model.claimQuery;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sinosoft.app.webservice.util.JaxbDateAdapter;

@XmlRootElement(name="reqClaimQuery")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqClaimQuery {
    
    /** 身分證字號*/
    private String identifyNumber;
	/** 車牌號碼 */
	private String licenseNo;
	/** 賠案狀態*/
	private String status;
	/** 查詢的險類  险种 */
	private String code;
	
	
    public String getIdentifyNumber() {
        return identifyNumber;
    }
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }
    public String getLicenseNo() {
        return licenseNo;
    }
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
	
	
}
