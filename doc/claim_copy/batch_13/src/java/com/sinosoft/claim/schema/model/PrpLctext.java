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
 * POJO类PrpLctext赔款计算文字表
 */
@Entity
@Table(name = "PRPLCTEXT")
public class PrpLctext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLctextId id;

	/** 属性文字说明 */
	private String context;

	/** 属性标志字段 */
	private String flag;
	/** 属性显示列表 */
	private List<PrpLctext> prpLctextList;

	/**
	 * 类PrpLctext的默认构造方法
	 */
	public PrpLctext() {
		id = new PrpLctextId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "textType", column = @Column(name = "TEXTTYPE")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpLctextId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLctextId id) {
		this.id = id;
	}

	/**
	 * 属性文字说明的getter方法
	 */

	@Column(name = "CONTEXT")
	public String getContext() {
		return this.context;
	}

	/**
	 * 属性文字说明的setter方法
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setPrpLctextList(List<PrpLctext> prpLctextList) {
		this.prpLctextList = prpLctextList;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public List<PrpLctext> getPrpLctextList() {
		return prpLctextList;
	}

}
