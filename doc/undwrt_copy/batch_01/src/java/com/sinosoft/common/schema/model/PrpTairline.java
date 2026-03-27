// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name="PRPTAIRLINE"
)
public class PrpTairline  implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


   
 /** 属性要保号码 */
    private PrpTairlineId id;
   
 /** 属性险类代码 */
     private String classCode;
   
 /** 属性险种代码 */
     private String riskCode;
   
 /** 属性计价方式 */
     private String priceMode;
   
 /** 属性保险费 */
     private String premium;
     
 /** 属性预留字段1*/   
     private String tcol1;
     
 /** 属性预留字段2*/ 
     private String tcol2;
	/** 屬性flag*/
     private String flag;
     /**
      * 属性prpTmain
      */
     private PrpTmain prpTmain;
     
     /**
 	 * 类PrpTairline的默认构造方法
 	 */
     public PrpTairline(){
    	 
     }
     
     /**
      * 属性prpTmain的getter方法
      */
     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="PROPOSALNO",nullable=false,insertable=false, updatable=false)
	public PrpTmain getPrpTmain() {
		return prpTmain;
	}
	/**
	 * 属性prpTmain的setter方法
	 */
	public void setPrpTmain(PrpTmain prpTmain) {
		this.prpTmain = prpTmain;
	}
	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="proposalNo", column=@Column(name="PROPOSALNO") ), 
        @AttributeOverride(name="seriesNo", column=@Column(name="SERIESNO") ) } )
	public PrpTairlineId getId() {
		return id;
	}
	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpTairlineId id) {
		this.id = id;
	}
	/**
	 * 属性classCode的getter方法
	 */
	@Column(name="CLASSCODE")
	public String getClassCode() {
		return classCode;
	}
	

	/**
	 * 属性classCode的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**
	 * 属性riskCode的getter方法
	 */
	@Column(name="RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}
	/**
	 * 属性riskCode的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	/**
	 * 属性priceMode的getter方法
	 */
	@Column(name="PRICEMODE")
	public String getPriceMode() {
		return priceMode;
	}
	/**
	 * 属性priceMode的setter方法
	 */
	public void setPriceMode(String priceMode) {
		this.priceMode = priceMode;
	}
	/**
	 * 属性premium的getter方法
	 */
	@Column(name="PREMIUM")
	public String getPremium() {
		return premium;
	}
	/**
	 * 属性premium的setter方法
	 */
	public void setPremium(String premium) {
		this.premium = premium;
	}
	/**
	 * 属性tcol1的getter方法
	 */
	@Column(name="TCOL1")
	public String getTcol1() {
		return tcol1;
	}
	/**
	 * 属性tcol1的setter方法
	 */
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	/**
	 * 属性tcol2的getter方法
	 */
	@Column(name="TCOL2")
	public String getTcol2() {
		return tcol2;
	}
	/**
	 * 属性tcol2的setter方法
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	/**属性flag的getter方法*/
	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}
	/**属性flag的setter方法*/
	public void setFlag(String flag) {
		this.flag = flag;
	}
}


