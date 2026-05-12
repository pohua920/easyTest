package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLacciPerso意健险索赔申请人表
 */
@Entity
@Table(name = "PRPLACCIPERSON")
public class PrpLacciPerson implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLacciPersonId id;

	/** 属性保单号 */
	private String policyNo;

	/** 属性FamilyNo */
	private Integer familyNo;

	/** 属性AcciCode */
	private String acciCode;

	/** 属性AcciName */
	private String acciName;

	/** 属性Sex */
	private String sex;

	/** 属性Age */
	private Integer age;

	/** 属性IdentifyType */
	private String identifyType;

	/** 属性IdentifyNumber */
	private String identifyNumber;

	/** 属性备注 */
	private String remark;

	/** 属性状态字段 */
	private String flag;

	/** 属性索赔申请人与事故者关系名称 */
	private String relationName;

	/** 属性索赔申请人与事故者关系代码 */
	private String relationCode;

	/** 属性索赔申请人通信地址 */
	private String address;

	/** 属性索赔申请人联系电话 */
	private String phone;

	private List<PrpLacciPerson> prpLacciPersonList;

	/**
	 * 类PrpLacciPerson的默认构造方法
	 */
	public PrpLacciPerson() {
		id = new PrpLacciPersonId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")), @AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLacciPersonId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLacciPersonId id) {
		this.id = id;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性FamilyNo的getter方法
	 */

	@Column(name = "FAMILYNO")
	public Integer getFamilyNo() {
		if(this.familyNo==null){
			return 0;
		}
		return this.familyNo;
	}

	/**
	 * 属性FamilyNo的setter方法
	 */
	public void setFamilyNo(Integer familyNo) {
		this.familyNo = familyNo;
	}

	/**
	 * 属性AcciCode的getter方法
	 */

	@Column(name = "ACCICODE")
	public String getAcciCode() {
		return this.acciCode;
	}

	/**
	 * 属性AcciCode的setter方法
	 */
	public void setAcciCode(String acciCode) {
		this.acciCode = acciCode;
	}

	/**
	 * 属性AcciName的getter方法
	 */

	@Column(name = "ACCINAME")
	public String getAcciName() {
		return this.acciName;
	}

	/**
	 * 属性AcciName的setter方法
	 */
	public void setAcciName(String acciName) {
		this.acciName = acciName;
	}

	/**
	 * 属性Sex的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性Sex的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性Age的getter方法
	 */

	@Column(name = "AGE")
	public Integer getAge() {
		if(this.age == null){
			return 0;
		}
		return this.age;
	}

	/**
	 * 属性Age的setter方法
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 属性IdentifyType的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性IdentifyType的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性IdentifyNumber的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性IdentifyNumber的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性索赔申请人与事故者关系名称的getter方法
	 */

	@Column(name = "RELATIONNAME")
	public String getRelationName() {
		return this.relationName;
	}

	/**
	 * 属性索赔申请人与事故者关系名称的setter方法
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	/**
	 * 属性索赔申请人与事故者关系代码的getter方法
	 */

	@Column(name = "RELATIONCODE")
	public String getRelationCode() {
		return this.relationCode;
	}

	/**
	 * 属性索赔申请人与事故者关系代码的setter方法
	 */
	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	/**
	 * 属性索赔申请人通信地址的getter方法
	 */

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	/**
	 * 属性索赔申请人通信地址的setter方法
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 属性索赔申请人联系电话的getter方法
	 */

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 属性索赔申请人联系电话的setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Transient
	public List<PrpLacciPerson> getPrpLacciPersonList() {
		return prpLacciPersonList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setPrpLacciPersonList(List<PrpLacciPerson> prpLacciPersonList) {
		this.prpLacciPersonList = prpLacciPersonList;
	}

}
