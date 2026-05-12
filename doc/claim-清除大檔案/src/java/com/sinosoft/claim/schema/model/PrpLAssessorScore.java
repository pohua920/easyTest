package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLAssessorScore 公估师评估信息表
 */
@Entity
@Table(name = "PRPLASSESSORSCORE")
public class PrpLAssessorScore implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLAssessorScoreId id;

	/** 属性配合度得分 */
	private Integer score1;

	/** 属性谈判技巧得分 */
	private Integer score2;

	/** 属性专业水平得分 */
	private Integer score3;

	/** 属性处理进度回报是否及时得分 */
	private Integer score4;

	/** 属性公估报告质量得分 */
	private Integer score5;

	/** 属性职业道德操守得分 */
	private Integer score6;

	/** 属性收费价格得分 */
	private Integer score7;

	/** 属性总得分 */
	private Double totalScore;

	/** 属性说明 */
	private String remark;

	/** 属性评估单位 */
	private String company;

	/** 属性委托时间 */
	private Date commitDate;

	private String editType = "";

	/** 属性公估师名称 */
	private String comCName1 = "";

	/** 属性公估机构名称 */
	private String comCName2 = "";

	/** 属性联系电话 */
	private String telePhone = "";

	/**
	 * 类PrpLAssessorScore的默认构造方法
	 */
	public PrpLAssessorScore() {
		id = new PrpLAssessorScoreId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "comCode1", column = @Column(name = "COMCODE1")), @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")) })
	public PrpLAssessorScoreId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLAssessorScoreId id) {
		this.id = id;
	}

	/**
	 * 属性配合度得分的getter方法
	 */

	@Column(name = "SCORE1")
	public Integer getScore1() {
		return this.score1;
	}

	/**
	 * 属性配合度得分的setter方法
	 */
	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	/**
	 * 属性谈判技巧得分的getter方法
	 */

	@Column(name = "SCORE2")
	public Integer getScore2() {
		return this.score2;
	}

	/**
	 * 属性谈判技巧得分的setter方法
	 */
	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	/**
	 * 属性专业水平得分的getter方法
	 */

	@Column(name = "SCORE3")
	public Integer getScore3() {
		return this.score3;
	}

	/**
	 * 属性专业水平得分的setter方法
	 */
	public void setScore3(Integer score3) {
		this.score3 = score3;
	}

	/**
	 * 属性处理进度回报是否及时得分的getter方法
	 */

	@Column(name = "SCORE4")
	public Integer getScore4() {
		return this.score4;
	}

	/**
	 * 属性处理进度回报是否及时得分的setter方法
	 */
	public void setScore4(Integer score4) {
		this.score4 = score4;
	}

	/**
	 * 属性公估报告质量得分的getter方法
	 */

	@Column(name = "SCORE5")
	public Integer getScore5() {
		return this.score5;
	}

	/**
	 * 属性公估报告质量得分的setter方法
	 */
	public void setScore5(Integer score5) {
		this.score5 = score5;
	}

	/**
	 * 属性职业道德操守得分的getter方法
	 */

	@Column(name = "SCORE6")
	public Integer getScore6() {
		return this.score6;
	}

	/**
	 * 属性职业道德操守得分的setter方法
	 */
	public void setScore6(Integer score6) {
		this.score6 = score6;
	}

	/**
	 * 属性收费价格得分的getter方法
	 */

	@Column(name = "SCORE7")
	public Integer getScore7() {
		return this.score7;
	}

	/**
	 * 属性收费价格得分的setter方法
	 */
	public void setScore7(Integer score7) {
		this.score7 = score7;
	}

	/**
	 * 属性总得分的getter方法
	 */

	@Column(name = "TOTALSCORE")
	public double getTotalScore() {
		return this.totalScore;
	}

	/**
	 * 属性总得分的setter方法
	 */
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * 属性说明的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性说明的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性评估单位的getter方法
	 */

	@Column(name = "COMPANY")
	public String getCompany() {
		return this.company;
	}

	/**
	 * 属性评估单位的setter方法
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 属性委托时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "COMMITDATE")
	public Date getCommitDate() {
		return this.commitDate;
	}

	/**
	 * 属性委托时间的setter方法
	 */
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 获取属性公估师名称
	 * @return 属性公估师名称的值
	 */
	@Transient
	public String getComCName1() {
		return comCName1;
	}

	/**
	 * 设置属性公估师名称
	 * @param comCName1 待设置的属性公估师名称的值
	 */
	public void setComCName1(String comCName1) {
		this.comCName1 = StringUtils.rightTrim(comCName1);
	}

	/**
	 * 获取属性公估机构名称
	 * @return 属性公估机构名称的值
	 */
	@Transient
	public String getComCName2() {
		return comCName2;
	}

	/**
	 * 设置属性公估机构名称
	 * @param comCName2 待设置的属性公估机构名称的值
	 */
	public void setComCName2(String comCName2) {
		this.comCName2 = StringUtils.rightTrim(comCName2);
	}

	/**
	 * 获取属性联系电话
	 * @return 属性联系电话的值
	 */
	@Transient
	public String getTelePhone() {
		return telePhone;
	}

	/**
	 * 设置属性联系电话
	 * @param telePhone 待设置的属性联系电话的值
	 */
	public void setTelePhone(String telePhone) {
		this.telePhone = StringUtils.rightTrim(telePhone);
	}

}
