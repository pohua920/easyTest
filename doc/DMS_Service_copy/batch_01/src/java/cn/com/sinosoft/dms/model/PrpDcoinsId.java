package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类prpDcoinsId
 */
@Embeddable
public class PrpDcoinsId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性审核机构代码 */
	private String comCode;

	/** 属性归属险种代码 */
	private String riskCode;

	/** 属性批次 */
	private Integer period;

	/** 属性共保体单位代码 */
	private String coinsComCode;

	/**
	 * 类prpDcoinsId的默认构造方法
	 */
	public PrpDcoinsId() {
	}

	/**       
	 * 属性审核机构代码的getter方法
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * 属性审核机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * 属性归属险种代码的getter方法
	 */

	@Column(name = "riskcode")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**       
	 * 属性归属险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**       
	 * 属性批次的getter方法
	 */

	@Column(name = "period")
	public Integer getPeriod() {
		return this.period;
	}

	/**       
	 * 属性批次的setter方法
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}

	/**       
	 * 属性共保体单位代码的getter方法
	 */

	@Column(name = "coinscomcode")
	public String getCoinsComCode() {
		return this.coinsComCode;
	}

	/**       
	 * 属性共保体单位代码的setter方法
	 */
	public void setCoinsComCode(String coinsComCode) {
		this.coinsComCode = coinsComCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDcoinsId))
			return false;
		PrpDcoinsId castOther = (PrpDcoinsId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this
				.getComCode() != null
				&& castOther.getComCode() != null && this.getComCode().equals(
				castOther.getComCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this
						.getRiskCode() != null
						&& castOther.getRiskCode() != null && this
						.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getPeriod() == castOther.getPeriod()) || (this
						.getPeriod() != null
						&& castOther.getPeriod() != null && this.getPeriod()
						.equals(castOther.getPeriod())))
				&& ((this.getCoinsComCode() == castOther.getCoinsComCode()) || (this
						.getCoinsComCode() != null
						&& castOther.getCoinsComCode() != null && this
						.getCoinsComCode().equals(castOther.getCoinsComCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result
				+ (getPeriod() == null ? 0 : this.getPeriod().hashCode());
		result = 37
				* result
				+ (getCoinsComCode() == null ? 0 : this.getCoinsComCode()
						.hashCode());
		return result;
	}

}
