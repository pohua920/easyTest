package com.sinosoft.claim.schema.model;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prpcrescue")
public class PrpCrescue implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** SerialNo */
	private PrpCrescueId id;
	/** 投保单号码 */
	private PrpCmain prpCmain;
	/** 救援公司名称 */
	private String rescueName;
	/** 救援公司代码 */
	private String rescueCode;
	/** 救援模式 */
	private String rescueModel;
	/** 插入时间 */
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;
	/** 标志字段 */
	private String flag;
	/** 救援电话 */
	private String rescuePhone;

	/**
	 * SerialNo
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "policyNo", nullable = false)), @AttributeOverride(name = "serialNo", column = @Column(name = "serialno", nullable = false)) })
	public PrpCrescueId getId() {
		return id;
	}

	public void setId(PrpCrescueId id) {
		this.id = id;
	}

	/**
	 * 投保单号码
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policyNo", nullable = false, insertable = false, updatable = false)
	public PrpCmain getPrpCmain() {
		return this.prpCmain;
	}

	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	/**
	 * 救援公司名称
	 */

	@Column(name = "rescuename")
	public String getRescueName() {
		return rescueName;
	}

	public void setRescueName(String rescueName) {
		this.rescueName = rescueName;
	}

	/**
	 * 救援公司代码
	 */

	@Column(name = "rescuecode")
	public String getRescueCode() {
		return rescueCode;
	}

	public void setRescueCode(String rescueCode) {
		this.rescueCode = rescueCode;
	}

	/**
	 * 救援模式
	 */

	@Column(name = "rescuemodel")
	public String getRescueModel() {
		return rescueModel;
	}

	public void setRescueModel(String rescueModel) {
		this.rescueModel = rescueModel;
	}

	/**
	 * 插入时间
	 */

	@Column(name = "inserttimeforhis", insertable = false, updatable = false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}

	/**
	 * 更新时间
	 */

	@Column(name = "operatetimeforhis", insertable = false)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}

	/**
	 * 标志字段
	 */

	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 救援电话
	 */
	@Column(name = "rescuephone")
	public String getRescuePhone() {
		return rescuePhone;
	}

	public void setRescuePhone(String rescuePhone) {
		this.rescuePhone = rescuePhone;
	}

}
