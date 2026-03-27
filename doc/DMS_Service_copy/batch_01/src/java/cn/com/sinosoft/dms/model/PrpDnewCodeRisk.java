package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类prpdnewcoderisk
 */
@Entity
@Table(name = "prpdnewcoderisk")
public class PrpDnewCodeRisk implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDnewCodeRiskId id;
    private String disPlayNo;
    private String validstatus;
	/**
	 * 类prpdnewcoderisk的默认构造方法
	 */
	public PrpDnewCodeRisk() {
	}

	/**       
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")) })
	public PrpDnewCodeRiskId getId() {
		return this.id;
	}

	/**       
	 * 属性id的setter方法
	 */
	public void setId(PrpDnewCodeRiskId id) {
		this.id = id;
	}
   @Column(name="disPlayNo")
	public String getDisPlayNo() {
		return disPlayNo;
	}

	public void setDisPlayNo(String disPlayNo) {
		this.disPlayNo = disPlayNo;
	}
	@Column(name="VALIDSTATUS")
	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}

}
