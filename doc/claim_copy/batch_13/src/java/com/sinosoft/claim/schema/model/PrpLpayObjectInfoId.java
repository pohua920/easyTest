package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpLpayObjectInfoId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 赔款计算书号 */
	private String compensateNo;

	/** 序号 */
	private Integer serialNo;

	/** 业务类型01赔款，02费用 */
	private String certiType;

	/**
	 * 类PrpLpayObjectInfoId的默认构造方法
	 */
	public PrpLpayObjectInfoId() {
	}

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return certiType;
	}

	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}
}
