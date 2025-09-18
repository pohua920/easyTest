package com.tlg.prpins.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

public class ReinsInwardNo extends IBatisBaseEntity<BigDecimal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal oid;
	private String type;
	private String year;
	private String poins;
	private String no;

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPoins() {
		return poins;
	}

	public void setPoins(String poins) {
		this.poins = poins;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}