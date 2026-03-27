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
 * POJO类prpDcoins
 */
@Entity
@Table(name = "prpdcoins")
public class PrpDcoins implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程动作标识 */
	private PrpDcoinsId id;

	/** 属性共保体单位名称 */
	private String coinsComName;

	/** 属性共保身份 */
	private String coinsType;

	/** 属性生效日期 */
	private Date validDate;

	/** 属性共保比例 */
	private BigDecimal coinsRate;

	/** 属性有效标志 */
	private String validStatus;

	/** 属性标志位 */
	private String flag;

	/**
	 * 类prpDcoins的默认构造方法
	 */
	public PrpDcoins() {
	}

	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "comCode", column = @Column(name = "comcode")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "period", column = @Column(name = "period")),
			@AttributeOverride(name = "coinsComCode", column = @Column(name = "coinscomcode")) })
	public PrpDcoinsId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpDcoinsId id) {
		this.id = id;
	}

	/**       
	 * 属性共保体单位名称的getter方法
	 */

	@Column(name = "coinscomname")
	public String getCoinsComName() {
		return this.coinsComName;
	}

	/**       
	 * 属性共保体单位名称的setter方法
	 */
	public void setCoinsComName(String coinsComName) {
		this.coinsComName = coinsComName;
	}

	/**       
	 * 属性共保身份的getter方法
	 */

	@Column(name = "coinstype")
	public String getCoinsType() {
		return this.coinsType;
	}

	/**       
	 * 属性共保身份的setter方法
	 */
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}

	/**       
	 * 属性生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * 属性生效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * 属性共保比例的getter方法
	 */

	@Column(name = "coinsrate")
	public BigDecimal getCoinsRate() {
		return this.coinsRate;
	}

	/**       
	 * 属性共保比例的setter方法
	 */
	public void setCoinsRate(BigDecimal coinsRate) {
		this.coinsRate = coinsRate;
	}

	/**       
	 * 属性有效标志的getter方法
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * 属性有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * 属性标志位的getter方法
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 属性标志位的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
