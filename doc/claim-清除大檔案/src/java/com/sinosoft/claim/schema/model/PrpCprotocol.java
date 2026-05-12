package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prpcprotocol")
public class PrpCprotocol implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** SerialNo */
	private String policyNo;
	/** 投保单号码 */
	private PrpCmain prpCmain;
	/** 协议文本内容 */
	private String content;

	/**
	 * 投保单号码
	 */
	@Id
	@Column(name = "policyNo", unique = true, nullable = false)
	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 投保单号码
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policyNo", unique = true, nullable = false, insertable = false, updatable = false)
	public PrpCmain getPrpCmain() {
		return this.prpCmain;
	}

	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	/**
	 * 协议文本内容
	 */
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}