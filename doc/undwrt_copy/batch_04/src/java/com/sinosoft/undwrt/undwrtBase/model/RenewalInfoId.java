package com.sinosoft.undwrt.undwrtBase.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RenewalInfoId implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String renewalNo;
    private String policyNo;

    public RenewalInfoId() {
    }

    @Column(name="RENEWALNO")
    public String getRenewalNo() {
        return renewalNo;
    }

    public void setRenewalNo(String renewalNo) {
        this.renewalNo = renewalNo;
    }

    @Column(name="POLICYNO")
    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

}
