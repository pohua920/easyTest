package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
 * POJO类PrpDautoDpLog
 */
@Embeddable
public class PrpDautoDpLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 軌跡ID **/
	private String logId;
	/** 修改的欄位(放入資料庫欄位名) **/
	private String columnName;

	/**
	 * 类PrpDautoDpLog的默认构造方法
	 */
	public PrpDautoDpLogId() {
	}
	
	@Column(name = "LOGID")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "COLUMNNAME")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDautoDpLogId)) {
			return false;
		}
		PrpDautoDpLogId castOther = (PrpDautoDpLogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null && castOther.getLogId() != null && this.getLogId().equals(castOther.getLogId())))
				&& ((this.getColumnName() == castOther.getColumnName()) || (this.getColumnName() != null && castOther.getColumnName() != null && this.getColumnName().equals(castOther.getColumnName())))
				;
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result + (getColumnName() == null ? 0 : this.getColumnName().hashCode());
		return result;
	}
}
