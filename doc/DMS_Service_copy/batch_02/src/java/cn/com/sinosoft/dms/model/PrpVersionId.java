package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpVersionId
 */
@Embeddable
public class PrpVersionId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性项目版本号 */
	private String projectVersion;

	/** 属性系统代码 */
	private String productId;

	/**
	 * 类PrpVersionId的默认构造方法
	 */
	public PrpVersionId() {
	}

	/**       
	 * 属性项目版本号的getter方法
	 */

	@Column(name = "projectversion")
	public String getProjectVersion() {
		return this.projectVersion;
	}

	/**       
	 * 属性项目版本号的setter方法
	 */
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	/**       
	 * 属性系统代码的getter方法
	 */

	@Column(name = "productid")
	public String getProductId() {
		return this.productId;
	}

	/**       
	 * 属性系统代码的setter方法
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpVersionId))
			return false;
		PrpVersionId castOther = (PrpVersionId) other;

		return ((this.getProjectVersion() == castOther.getProjectVersion()) || (this
				.getProjectVersion() != null
				&& castOther.getProjectVersion() != null && this
				.getProjectVersion().equals(castOther.getProjectVersion())))
				&& ((this.getProductId() == castOther.getProductId()) || (this
						.getProductId() != null
						&& castOther.getProductId() != null && this
						.getProductId().equals(castOther.getProductId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getProjectVersion() == null ? 0 : this.getProjectVersion()
						.hashCode());
		result = 37 * result
				+ (getProductId() == null ? 0 : this.getProductId().hashCode());
		return result;
	}

}
