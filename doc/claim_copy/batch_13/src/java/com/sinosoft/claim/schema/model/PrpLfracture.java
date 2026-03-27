package com.sinosoft.claim.schema.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 骨折程度和骨折部位配置表
 * @author 中科软
 *
 */
@Entity
@Table(name = "PrpLfracture")
public class PrpLfracture implements java.io.Serializable{
	
	/** 序号*/
	private static final long serialVersionUID = 1L;
	/** 属性id */
	private PrpLfractureId id;
	/** 骨折说明*/
	private String fractureName;
	/** 骨折天数，或者比例 */
	private Double fractureRate;
	/**  有效标志  */
	private String validStatus;
	/** 属性标志 */
	private String flag;
	/** 属性标志 */
	private String remark;
	
	public PrpLfracture(){
		id = new PrpLfractureId();
	}
	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "fractureCode", column = @Column(name = "fractureCode")), @AttributeOverride(name = "fractureType", column = @Column(name = "fractureType")) })
	public PrpLfractureId getId() {
		return this.id;
	}

	public void setId(PrpLfractureId id) {
		this.id = id;
	}
	@Column(name = "fractureName")
	public String getFractureName() {
		return fractureName;
	}
	public void setFractureName(String fractureName) {
		this.fractureName = fractureName;
	}
	@Column(name = "fractureRate")
	public Double getFractureRate() {
		return fractureRate;
	}
	public void setFractureRate(Double fractureRate) {
		this.fractureRate = fractureRate;
	}
	@Column(name = "validStatus")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
