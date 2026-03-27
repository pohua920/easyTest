package com.sinosoft.common.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Embeddable
public class PrpQpeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 属性投保单号 */
	private String proposalNo;

	/** 属性序列号 */
	private Integer serialNo;
	
	public PrpQpeId() {
		
	}

	@Column(name = "PROPOSALNO")
	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
}
