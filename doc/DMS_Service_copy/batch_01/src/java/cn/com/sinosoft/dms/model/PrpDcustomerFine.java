package cn.com.sinosoft.dms.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类Prpdcustomerfine
 */
@Entity
@Table(name = "PRPDCUSTOMERFINE")
public class PrpDcustomerFine implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDcustomerFineId id;

	/** 属性BRANDNAME */
	private String brandName;

	/** 属性INSUREDNAME */
	private String insuredName;

	/** 属性VALIDSTARTDATE */
	private Date validStartDate;

	/** 属性VALIDENDDATE */
	private Date validEndDate;

	/** 属性VALIDSTATUS */
	private String validStatus;

	/** 属性REMARK */
	private String remark;

	/** 属性FLAG */
	private String flag;

	/**
	 * 类Prpdcustomerfine的默认构造方法
	 */
	public PrpDcustomerFine() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
			@AttributeOverride(name = "batchNo", column = @Column(name = "BATCHNO")),
			@AttributeOverride(name = "licenseNo", column = @Column(name = "LICENSENO")) })
	public PrpDcustomerFineId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDcustomerFineId id) {
		this.id = id;
	}

	/**
	 * 属性BRANDNAME的getter方法
	 */

	@Column(name = "BRANDNAME")
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 属性BRANDNAME的setter方法
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 属性INSUREDNAME的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性INSUREDNAME的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性VALIDSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDSTARTDATE")
	public Date getValidStartDate() {
		return this.validStartDate;
	}

	/**
	 * 属性VALIDSTARTDATE的setter方法
	 */
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	/**
	 * 属性VALIDENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDENDDATE")
	public Date getValidEndDate() {
		return this.validEndDate;
	}

	/**
	 * 属性VALIDENDDATE的setter方法
	 */
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	/**
	 * 属性VALIDSTATUS的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性VALIDSTATUS的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
