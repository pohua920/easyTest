package cn.com.sinosoft.dms.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDkindCarId implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private String riskCode;
    private String kindCode;
    
    public PrpDkindCarId(){
        
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

    
}
