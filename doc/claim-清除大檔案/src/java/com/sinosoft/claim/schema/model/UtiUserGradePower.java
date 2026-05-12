package com.sinosoft.claim.schema.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类機構員工崗位差異功能權限表
 */
@Entity
@Table(name="UtiUserGradePower")
public class UtiUserGradePower  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
   
    /** 属性id */
     private UtiUserGradePowerId id;
     /** 業務權限機構代碼 */
     private String permitComCode;
     /** 業務權限除外機構 */
     private String exceptComCode;
     /** 員工范圍  */
     private String permitUserCode;
     /** 除外員工范圍 */
     private String exceptUserCode;
     /** 險種范*/
     private String permitRiskCode;
     /** 代碼權限機構代碼 */
     private String codePermitComCode;
     /** 代碼權限除外機構 */
     private String codeExceptComCode;
     /** 客戶代碼權限機構代碼 */
     private String customerExceptComCode;
     /** 客戶權限除外機構 */
     private String customerPermitComCode;
    
    /**
     * 属性id的getter方法
     */
    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="comcode", column=@Column(name="COMCODE") ), 
        @AttributeOverride(name="usercode", column=@Column(name="USERCODE") ), 
        @AttributeOverride(name="gradecode", column=@Column(name="GRADECODE") ), 
        @AttributeOverride(name="serialNo", column=@Column(name="serialNo") ) } )
    public UtiUserGradePowerId getId() {
        return this.id;
    }
 	public void setId(UtiUserGradePowerId id) {
 		this.id = id;
 	}
 	@Column(name = "permitComCode")
	public String getPermitComCode() {
		return permitComCode;
	}

	public void setPermitComCode(String permitComCode) {
		this.permitComCode = permitComCode;
	}
	@Column(name = "exceptComCode")
	public String getExceptComCode() {
		return exceptComCode;
	}

	public void setExceptComCode(String exceptComCode) {
		this.exceptComCode = exceptComCode;
	}
	@Column(name = "permitUserCode")
	public String getPermitUserCode() {
		return permitUserCode;
	}

	public void setPermitUserCode(String permitUserCode) {
		this.permitUserCode = permitUserCode;
	}
	@Column(name = "exceptUserCode")
	public String getExceptUserCode() {
		return exceptUserCode;
	}

	public void setExceptUserCode(String exceptUserCode) {
		this.exceptUserCode = exceptUserCode;
	}
	@Column(name = "permitRiskCode")
	public String getPermitRiskCode() {
		return permitRiskCode;
	}

	public void setPermitRiskCode(String permitRiskCode) {
		this.permitRiskCode = permitRiskCode;
	}
	@Column(name = "codePermitComCode")
	public String getCodePermitComCode() {
		return codePermitComCode;
	}

	public void setCodePermitComCode(String codePermitComCode) {
		this.codePermitComCode = codePermitComCode;
	}
	@Column(name = "codeExceptComCode")
	public String getCodeExceptComCode() {
		return codeExceptComCode;
	}

	public void setCodeExceptComCode(String codeExceptComCode) {
		this.codeExceptComCode = codeExceptComCode;
	}
	@Column(name = "customerExceptComCode")
	public String getCustomerExceptComCode() {
		return customerExceptComCode;
	}

	public void setCustomerExceptComCode(String customerExceptComCode) {
		this.customerExceptComCode = customerExceptComCode;
	}
	@Column(name = "customerPermitComCode")
	public String getCustomerPermitComCode() {
		return customerPermitComCode;
	}

	public void setCustomerPermitComCode(String customerPermitComCode) {
		this.customerPermitComCode = customerPermitComCode;
	}
}


