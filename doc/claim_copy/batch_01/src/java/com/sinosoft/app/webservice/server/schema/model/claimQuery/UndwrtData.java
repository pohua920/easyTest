package com.sinosoft.app.webservice.server.schema.model.claimQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *自定义核賠资料对象
 * 
  */
@XmlRootElement(name="undwrtData")
@XmlAccessorType(XmlAccessType.FIELD)
public class UndwrtData {
    
    /** 核賠险别  */
    private String kindType = "";
    /** 核賠金额  */
    private double sumRealPay = 0d;
    
    public String getKindType() {
        return kindType;
    }
    public void setKindType(String kindType) {
        this.kindType = kindType;
    }
    public double getSumRealPay() {
        return sumRealPay;
    }
    public void setSumRealPay(double sumRealPay) {
        this.sumRealPay = sumRealPay;
    }
    
    

}
