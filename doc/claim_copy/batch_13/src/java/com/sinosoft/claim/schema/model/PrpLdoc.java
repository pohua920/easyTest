package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLdoc索赔单证信息表对象
 */
@Entity
@Table(name = "PRPLDOC")
public class PrpLdoc implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLdocId id;

	/** 属性立案基本信息表 */
	private PrpLclaim prpLclaim;

	/** 属性单证名称 */
	private String docName;

	/** 属性单证份数 */
	private Integer docCount;

	/** 属性签收日期 */
	private Date signInDate;

	/** 属性状态字段 */
	private String flag;
	private Collection<PrpLdoc> docList;

	/**
	 * 类PrpLdoc的默认构造方法
	 */
	public PrpLdoc() {
		id = new PrpLdocId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "docCode", column = @Column(name = "DOCCODE")) })
	public PrpLdocId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLdocId id) {
		this.id = id;
	}

	/**
	 * 属性立案基本信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMNO", nullable = false, insertable = false, updatable = false)
	public PrpLclaim getPrpLclaim() {
		return this.prpLclaim;
	}

	/**
	 * 属性立案基本信息表的setter方法
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	/**
	 * 属性单证名称的getter方法
	 */

	@Column(name = "DOCNAME")
	public String getDocName() {
		return this.docName;
	}

	/**
	 * 属性单证名称的setter方法
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * 属性单证份数的getter方法
	 */

	@Column(name = "DOCCOUNT")
	public Integer getDocCount() {
		return this.docCount;
	}

	/**
	 * 属性单证份数的setter方法
	 */
	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}

	/**
	 * 属性签收日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNINDATE")
	public Date getSignInDate() {
		return this.signInDate;
	}

	/**
	 * 属性签收日期的setter方法
	 */
	public void setSignInDate(Date signInDate) {
		this.signInDate = signInDate;
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
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLdoc> getDocList() {
		return docList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setDocList(Collection<PrpLdoc> docList) {
		this.docList = docList;
	}

}
