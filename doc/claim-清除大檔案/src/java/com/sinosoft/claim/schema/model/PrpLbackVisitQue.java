package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLbackVisitQue
 */
@Entity
@Table(name = "PRPLBACKVISITQUE")
public class PrpLbackVisitQue implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLbackVisitQueId id;

	/** 属性回访项目名称 */
	private String questionName;

	/** 属性回访结果 */
	private String questionResult;

	/** 属性问题备注 */
	private String questionRemark;

	/** 属性问题类型 */
	private String questionType;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLbackVisitQue的默认构造方法
	 */
	public PrpLbackVisitQue() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "backVisitID", column = @Column(name = "BACKVISITID")), @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "backVisitType", column = @Column(name = "BACKVISITTYPE")), @AttributeOverride(name = "questionCode", column = @Column(name = "QUESTIONCODE")) })
	public PrpLbackVisitQueId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLbackVisitQueId id) {
		this.id = id;
	}

	/**
	 * 属性回访项目名称的getter方法
	 */

	@Column(name = "QUESTIONNAME")
	public String getQuestionName() {
		return this.questionName;
	}

	/**
	 * 属性回访项目名称的setter方法
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * 属性回访结果的getter方法
	 */

	@Column(name = "QUESTIONRESULT")
	public String getQuestionResult() {
		return this.questionResult;
	}

	/**
	 * 属性回访结果的setter方法
	 */
	public void setQuestionResult(String questionResult) {
		this.questionResult = questionResult;
	}

	/**
	 * 属性问题备注的getter方法
	 */

	@Column(name = "QUESTIONREMARK")
	public String getQuestionRemark() {
		return this.questionRemark;
	}

	/**
	 * 属性问题备注的setter方法
	 */
	public void setQuestionRemark(String questionRemark) {
		this.questionRemark = questionRemark;
	}

	/**
	 * 属性问题类型的getter方法
	 */

	@Column(name = "QUESTIONTYPE")
	public String getQuestionType() {
		return this.questionType;
	}

	/**
	 * 属性问题类型的setter方法
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
