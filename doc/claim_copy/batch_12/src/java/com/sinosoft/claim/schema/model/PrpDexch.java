package com.sinosoft.claim.schema.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * 每日汇率
 * @author 中科软
 */
@Entity
@Table(name = "PRPDEXCH")
public class PrpDexch implements Serializable {
	private static final long serialVersionUID = 1L;
	private PrpDexchId id;
	private Integer base;
	private Double exchRate;
	private String validStatus;
	private String flag;

	public PrpDexch() {
		
	}
	
	public PrpDexch(Date exchDate,String baseCurrency,String exchCurrency) {
		this.id = new PrpDexchId(exchDate,baseCurrency,exchCurrency);
	}
	@EmbeddedId
	@AttributeOverrides({ @javax.persistence.AttributeOverride(name = "exchDate", column = @Column(name = "EXCHDATE")), @javax.persistence.AttributeOverride(name = "baseCurrency", column = @Column(name = "BASECURRENCY")),
			@javax.persistence.AttributeOverride(name = "exchCurrency", column = @Column(name = "EXCHCURRENCY")) })
	public PrpDexchId getId() {
		return this.id;
	}

	public void setId(PrpDexchId id) {
		this.id = id;
	}

	@Column(name = "BASE")
	public Integer getBase() {
		return this.base;
	}

	public void setBase(Integer base) {
		this.base = base;
	}

	@Column(name = "EXCHRATE")
	public Double getExchRate() {
		return this.exchRate;
	}

	public void setExchRate(Double exchRate) {
		this.exchRate = exchRate;
	}

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
