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
@Table(name = "PRPTRISKASST")
public class PrpTriskasst implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpTriskasstId id;

	/** 属性险类代码 */
	private String riskCode;
	
	/** 属性PRPCMAIN */
	private PrpTmain prpTmain;
	
    private String selectFlag1;
	
	private String selectFlag2;
	
	private String checkFlag1;
	
	private String checkFlag2;
	
	private String radioFlag1;
	
	private String radioFlag2;
	
	private String note1;
	
	private String note2;
	
	private String note3;
	
	private String tcol1;
	
	private String flag;
	
	public PrpTriskasst() {
	}
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpTriskasstId getId() {
		return this.id;
	}

	public void setId(PrpTriskasstId id) {
		this.id = id;
	}
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpTmain getPrpTmain() {
		return this.prpTmain;
	}

	public void setPrpTmain(PrpTmain prpTmain) {
		this.prpTmain = prpTmain;
	}
	

	@Column(name = "SELECTFLAG1")
	public String getSelectFlag1() {
		return selectFlag1;
	}

	public void setSelectFlag1(String selectFlag1) {
		this.selectFlag1 = selectFlag1;
	}
	@Column(name = "SELECTFLAG2")
	public String getSelectFlag2() {
		return selectFlag2;
	}

	public void setSelectFlag2(String selectFlag2) {
		this.selectFlag2 = selectFlag2;
	}
	@Column(name = "CHECKFLAG1")
	public String getCheckFlag1() {
		return checkFlag1;
	}

	public void setCheckFlag1(String checkFlag1) {
		this.checkFlag1 = checkFlag1;
	}
	@Column(name = "CHECKFLAG2")
	public String getCheckFlag2() {
		return checkFlag2;
	}

	public void setCheckFlag2(String checkFlag2) {
		this.checkFlag2 = checkFlag2;
	}
	@Column(name = "RADIOFLAG1")
	public String getRadioFlag1() {
		return radioFlag1;
	}

	public void setRadioFlag1(String radioFlag1) {
		this.radioFlag1 = radioFlag1;
	}
	@Column(name = "RADIOFLAG2")
	public String getRadioFlag2() {
		return radioFlag2;
	}

	public void setRadioFlag2(String radioFlag2) {
		this.radioFlag2 = radioFlag2;
	}
	
	@Column(name = "NOTE1")
	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}
	@Column(name = "NOTE2")
	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}
	@Column(name = "NOTE3")
	public String getNote3() {
		return note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}
	@Column(name = "TCOL1")
	public String getTcol1() {
		return tcol1;
	}

	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
