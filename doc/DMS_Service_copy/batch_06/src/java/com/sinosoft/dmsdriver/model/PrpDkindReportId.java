package com.sinosoft.dmsdriver.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PrpDkindReportId implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private String riskCode;
    private String kindCode;
    private String userNature;
    
    public PrpDkindReportId() {
    }
    
    @Column(name = "riskCode")
    public String getRiskCode() {
        return riskCode;
    }
    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }
    
    @Column(name = "kindCode")
    public String getKindCode() {
        return kindCode;
    }
    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }
    
    @Column(name = "userNature")
    public String getUserNature() {
        return userNature;
    }
    public void setUserNature(String userNature) {
        this.userNature = userNature;
    }
    
    

}
