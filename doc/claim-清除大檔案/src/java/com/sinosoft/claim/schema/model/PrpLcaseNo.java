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

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLcaseNo
 */
@Entity
@Table(name = "PRPLCASENO")
public class PrpLcaseNo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcaseNoId id;

	/** 属性标志字段 */
	private String flag;

	/** 属性赔案号 */
	private String claimNo;
	/** Dto传递时的编辑状态，如Eidt,Add等等 */
	private String editType = "";
	/** 此查勘的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** 属性保单号码 */
	private String policyNo = "";
	/** 属性总赔付金额 */
	private double sumPaid = 0d;
	/** 属性结案员代码 */
	private String endCaserCode = "";
	/** 属性结案时间 */
	private DateTime endCaseDate = new DateTime();
	/** 属性车牌号码 */
	private String licenseNo = "";// Modify By sunhao 2004-08-24
	/** 属性此案件的操作时间 */
	private DateTime operateDate = new DateTime();// Modify By Sunhao,2004-08-24
	/** 集合 **/
	List<?> caseList;
	/** 先别险别 */
	private String riskCode = "";
	/** 属性预赔计算书号 */
	private String preCompensateNo = "";
	/** 属性赔款计算书号码 */
	private String compensateNo = "";
	private String notBackCount = "";

	/**
	 * 类PrpLcaseNo的默认构造方法
	 */
	public PrpLcaseNo() {
		id = new PrpLcaseNoId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")), @AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")), @AttributeOverride(name = "caseNo", column = @Column(name = "CASENO")) })
	public PrpLcaseNoId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcaseNoId id) {
		this.id = id;
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
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	@Transient
	public String getNotBackCount() {
		return notBackCount;
	}

	public void setNotBackCount(String notBackCount) {
		this.notBackCount = notBackCount;
	}

	/**
	 * 设置属性保单号码
	 * @param policyNo 待设置的属性保单号码的值
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = StringUtils.rightTrim(policyNo);
	}

	/**
	 * 获取属性保单号码
	 * @return 属性保单号码的值
	 */
	@Transient
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * 设置属性总赔付金额
	 * @param sumPaid 待设置的属性总赔付金额的值
	 */
	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}

	/**
	 * 获取属性总赔付金额
	 * @return 属性总赔付金额的值
	 */
	@Transient
	public double getSumPaid() {
		return sumPaid;
	}

	/**
	 * 得到结案集合
	 * @return 结案集合信息
	 */
	@Transient
	public List<?> getCaseList() {
		return caseList;
	}

	/**
	 * 设置结案集合
	 * @param caseList 结案集合
	 */
	public void setCaseList(List<?> caseList) {
		this.caseList = caseList;
	}

	/**
	 * 得到结案的操作状态
	 * @return 结案的操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置结案的操作状态
	 * @param status 结案的操作状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 设置编辑类型
	 * @param editType 待设置的编辑类型的值
	 */
	public void setEditType(String editType) {
		this.editType = StringUtils.rightTrim(editType);
	}

	/**
	 * 获取编辑类型
	 * @return 属性编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	/**
	 * 设置属性结案员代码
	 * @param policyNo 待设置的属性结案员代码的值
	 */
	public void setEndCaserCode(String endCaserCode) {
		this.endCaserCode = StringUtils.rightTrim(endCaserCode);
	}

	/**
	 * 设置属性结案时间
	 * @param policyNo 待设置的属性结案时间的值
	 */
	public void setEndCaseDate(DateTime endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	/**
	 * 获取属性结案员代码
	 * @return 属性结案员代码的值
	 */
	@Transient
	public String getEndCaserCode() {
		return endCaserCode;
	}

	/**
	 * 获取属性结案时间
	 * @return 属性结案时间的值
	 */
	@Transient
	public DateTime getEndCaseDate() {
		return endCaseDate;
	}

	/**
	 * 设置属性车牌号码
	 * @param licenseNo 待设置的属性车牌号码的值 Modify By sunhao 2004-08-24
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = StringUtils.rightTrim(licenseNo);
	}

	/**
	 * 获取属性车牌号码
	 * @return 属性车牌号码的值 Modify By sunhao 2004-08-24
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 设置属性操作时间
	 * @param operateDate 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	public void setOperateDate(DateTime operateDate) {
		this.operateDate = operateDate;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 获取属性操作时间名称
	 * @param status 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public DateTime getOperateDate() {
		return operateDate;
	}

	@Transient
	public String getRiskCode() {
		return riskCode;
	}

	// modify by zhaozhuo add 20050331 start

	/**
	 * 设置属性预赔计算书号
	 * @param preCompensateNo 待设置的属性预赔计算书号的值
	 */
	public void setPreCompensateNo(String preCompensateNo) {
		this.preCompensateNo = StringUtils.rightTrim(preCompensateNo);
	}

	/**
	 * 获取属性预赔计算书号
	 * @return 属性预赔计算书号的值
	 */
	@Transient
	public String getPreCompensateNo() {
		return preCompensateNo;
	}

	/**
	 * 设置属性赔款计算书号码
	 * @param compensateNo 待设置的属性赔款计算书号码的值
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = StringUtils.rightTrim(compensateNo);
	}

	/**
	 * 获取属性赔款计算书号码
	 * @return 属性赔款计算书号码的值
	 */
	@Transient
	public String getCompensateNo() {
		return compensateNo;
	}

}
