package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
//DP0713 手動增加
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DP0713仿製來源: POJO类PrpLDocArchiveLog
 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
 */
@Embeddable
public class PrpLUserLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔案号 */
	private String userCode;

	/** 属性序号 */
	private Double oid;

	/**
	 * 类PrpLUserLogId的默认构造方法
	 */
	public PrpLUserLogId() {
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "OID")
	public Double getOid() {
		return this.oid;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setOid(Double oid) {
		this.oid = oid;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLUserLogId)) {
			return false;
		}
		PrpLUserLogId castOther = (PrpLUserLogId) other;

		return ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(castOther.getUserCode())))
				&& ((this.getOid() == castOther.getOid()) || (this.getOid() != null && castOther.getOid() != null && this.getOid().equals(castOther.getOid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		result = 37 * result + (getOid() == null ? 0 : this.getOid().hashCode());
		return result;
	}

}
