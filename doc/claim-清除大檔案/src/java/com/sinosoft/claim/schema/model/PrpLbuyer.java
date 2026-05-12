package com.sinosoft.claim.schema.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRPLBUYER")
public class PrpLbuyer implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLbuyerId id;

	/** 买受人 */
	private String buyerCode = "";
	/** 买受人名称 */
	private String buyerName = "";
	/** 統一編號 */
	private String uniformNo = "";

	/** 属性住址 */
	private String address = "";
	/** 属性说明 （支援1000個文字輸入） */
	private String explanation = "";

	/** 属性描述符 */
	private String remark = "";

	/** 属性联系电话 */
	private String linkPhone = "";

	/** 属性标志位 */
	private String flag = "";

	public PrpLbuyer() {
		id = new PrpLbuyerId();
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLbuyerId getId() {
		return id;
	}

	public void setId(PrpLbuyerId id) {
		this.id = id;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "LINKPHONE")
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Column(name = "BUYERCODE")
	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	@Column(name = "BUYERNAME")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	@Column(name = "UNIFORMNO")
	public String getUniformNo() {
		return uniformNo;
	}

	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}

	@Column(name = "EXPLANATION")
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
