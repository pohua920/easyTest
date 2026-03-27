package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
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
 * POJO类PrpDSimpleTreaty
 */
@Entity
@Table(name = "prpdsimpletreaty")
public class PrpDSimpleTreaty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程动作标识 */
	private PrpDSimpleTreatyId id;

	/** 属性合约账单币种 */
	private String ttyCurrency;

	/** 属性合约快速分出比例 */
	private BigDecimal ttyShareRate;

	/** 属性开始日期 */
	private Date startDate;

	/** 属性终止日期 */
	private Date endDate;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDSimpleTreaty的默认构造方法
	 */
	public PrpDSimpleTreaty() {
	}

	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "treatyNo", column = @Column(name = "treatyno")),
			@AttributeOverride(name = "sectionNo", column = @Column(name = "sectionno")),
			@AttributeOverride(name = "classCode", column = @Column(name = "classcode")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "othCondition", column = @Column(name = "othcondition")) })
	public PrpDSimpleTreatyId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpDSimpleTreatyId id) {
		this.id = id;
	}

	/**       
	 * 属性合约账单币种的getter方法
	 */

	@Column(name = "ttycurrency")
	public String getTtyCurrency() {
		return this.ttyCurrency;
	}

	/**       
	 * 属性合约账单币种的setter方法
	 */
	public void setTtyCurrency(String ttyCurrency) {
		this.ttyCurrency = ttyCurrency;
	}

	/**       
	 * 属性合约快速分出比例的getter方法
	 */

	@Column(name = "ttysharerate")
	public BigDecimal getTtyShareRate() {
		return this.ttyShareRate;
	}

	/**       
	 * 属性合约快速分出比例的setter方法
	 */
	public void setTtyShareRate(BigDecimal ttyShareRate) {
		this.ttyShareRate = ttyShareRate;
	}

	/**       
	 * 属性开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	public Date getStartDate() {
		return this.startDate;
	}

	/**       
	 * 属性开始日期的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**       
	 * 属性终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	public Date getEndDate() {
		return this.endDate;
	}

	/**       
	 * 属性终止日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**       
	 * 属性标志字段的getter方法
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
