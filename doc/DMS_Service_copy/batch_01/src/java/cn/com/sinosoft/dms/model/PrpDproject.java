package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类prpDproject
 */
@Entity
@Table(name = "prpdproject")
public class PrpDproject implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;

	/** 属性项目代码 */
	private String				projectCode;

	/** 属性项目简体中文名称 */
	private String				projectCName;

	/** 属性项目繁体中文名称 */
	private String				projectTName;

	/** 属性项目英文名称 */
	private String				projectEName;

	/** 属性创建人 */
	private String				creatorCode;

	/** 属性创建时间 */
	private Date				createTime;

	/** 属性最后修改人 */
	private String				updaterCode;

	/** 属性最后修改时间 */
	private Date				updateTime;

	/** 属性生效日期 */
	private Date				validDate;

	/** 属性失效日期 */
	private Date				invalidDate;

	/** 属性审核标志 */
	private String				auditFlag;
	
	/** 属性归属机构*/
	private String				comCode;

	/** 属性有效标志 */
	private String				validInd;

	/** 属性预留字段1 */
	private String				tcol1;

	/** 属性预留字段2 */
	private String				tcol2;

	/** 属性预留字段3 */
	private String				tcol3;

	/** 属性备注 */
	private String				remark;

	/** 属性标志字段 */
	private String				flag;

	/**
	 * 类prpDproject的默认构造方法
	 */
	public PrpDproject() {
	}

	/**       
	 * 属性项目代码的getter方法
	 */
	@Id
	@Column(name = "projectcode")
	public String getProjectCode() {
		return this.projectCode;
	}

	/**       
	 * 属性项目代码的setter方法
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**       
	 * 属性项目简体中文名称的getter方法
	 */

	@Column(name = "projectcname")
	public String getProjectCName() {
		return this.projectCName;
	}

	/**       
	 * 属性项目简体中文名称的setter方法
	 */
	public void setProjectCName(String projectCName) {
		this.projectCName = projectCName;
	}

	/**       
	 * 属性项目繁体中文名称的getter方法
	 */

	@Column(name = "projecttname")
	public String getProjectTName() {
		return this.projectTName;
	}

	/**       
	 * 属性项目繁体中文名称的setter方法
	 */
	public void setProjectTName(String projectTName) {
		this.projectTName = projectTName;
	}

	/**       
	 * 属性项目英文名称的getter方法
	 */

	@Column(name = "projectename")
	public String getProjectEName() {
		return this.projectEName;
	}

	/**       
	 * 属性项目英文名称的setter方法
	 */
	public void setProjectEName(String projectEName) {
		this.projectEName = projectEName;
	}

	/**       
	 * 属性创建人的getter方法
	 */

	@Column(name = "creatorcode")
	public String getCreatorCode() {
		return this.creatorCode;
	}

	/**       
	 * 属性创建人的setter方法
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	/**       
	 * 属性创建时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "createtime")
	public Date getCreateTime() {
		return this.createTime;
	}

	/**       
	 * 属性创建时间的setter方法
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**       
	 * 属性最后修改人的getter方法
	 */

	@Column(name = "updatercode")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**       
	 * 属性最后修改人的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**       
	 * 属性最后修改时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "updatetime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**       
	 * 属性最后修改时间的setter方法
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**       
	 * 属性生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * 属性生效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * 属性失效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "invaliddate")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**       
	 * 属性失效日期的setter方法
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**       
	 * 属性审核标志的getter方法
	 */

	@Column(name = "auditflag")
	public String getAuditFlag() {
		return this.auditFlag;
	}

	/**       
	 * 属性审核标志的setter方法
	 */
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	/**       
	 * 属性归属机构的getter方法
	 */
	
	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}
	
	/**       
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * 属性有效标志的getter方法
	 */

	@Column(name = "validind")
	public String getValidInd() {
		return this.validInd;
	}

	/**       
	 * 属性有效标志的setter方法
	 */
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}

	/**       
	 * 属性预留字段1的getter方法
	 */

	@Column(name = "tcol1")
	public String getTcol1() {
		return this.tcol1;
	}

	/**       
	 * 属性预留字段1的setter方法
	 */
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	/**       
	 * 属性预留字段2的getter方法
	 */

	@Column(name = "tcol2")
	public String getTcol2() {
		return this.tcol2;
	}

	/**       
	 * 属性预留字段2的setter方法
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/**       
	 * 属性预留字段3的getter方法
	 */

	@Column(name = "tcol3")
	public String getTcol3() {
		return this.tcol3;
	}

	/**       
	 * 属性预留字段3的setter方法
	 */
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

	/**       
	 * 属性备注的getter方法
	 */

	@Column(name = "remark")
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
	 * 属性标志字段的getter方法
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
