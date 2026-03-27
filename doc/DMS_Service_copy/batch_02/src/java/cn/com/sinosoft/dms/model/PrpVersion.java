package cn.com.sinosoft.dms.model;

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

/**
 * POJO类PrpVersion
 */
@Entity
@Table(name = "prpversion")
public class PrpVersion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程动作标识 */
	private PrpVersionId id;

	/** 属性项目名称 */
	private String projectName;

	/** 属性变更次数 */
	private Integer times;

	/** 属性新用户名称 */
	private String userName;

	/** 属性客户名称 */
	private String company;

	/** 属性最后修改时间 */
	private Date updateDate;

	/** 属性升级前版本号 */
	private String primaryVersion;

	/** 属性扩展字段1 */
	private String flag1;

	/** 属性扩展字段2 */
	private String flag2;

	/** 属性扩展字段3 */
	private String flag3;

	/** 属性扩展字段4 */
	private String flag4;

	/** 属性扩展字段5 */
	private String flag5;

	/**
	 * 类PrpVersion的默认构造方法
	 */
	public PrpVersion() {
	}

	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectversion", column = @Column(name = "projectversion")),
			@AttributeOverride(name = "productid", column = @Column(name = "productid")) })
	public PrpVersionId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpVersionId id) {
		this.id = id;
	}

	/**       
	 * 属性项目名称的getter方法
	 */

	@Column(name = "projectname")
	public String getProjectName() {
		return this.projectName;
	}

	/**       
	 * 属性项目名称的setter方法
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**       
	 * 属性变更次数的getter方法
	 */

	@Column(name = "times")
	public Integer getTimes() {
		return this.times;
	}

	/**       
	 * 属性变更次数的setter方法
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}

	/**       
	 * 属性新用户名称的getter方法
	 */

	@Column(name = "username")
	public String getUserName() {
		return this.userName;
	}

	/**       
	 * 属性新用户名称的setter方法
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**       
	 * 属性客户名称的getter方法
	 */

	@Column(name = "company")
	public String getCompany() {
		return this.company;
	}

	/**       
	 * 属性客户名称的setter方法
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**       
	 * 属性最后修改时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "updatedate")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**       
	 * 属性最后修改时间的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**       
	 * 属性升级前版本号的getter方法
	 */

	@Column(name = "primaryversion")
	public String getPrimaryVersion() {
		return this.primaryVersion;
	}

	/**       
	 * 属性升级前版本号的setter方法
	 */
	public void setPrimaryVersion(String primaryVersion) {
		this.primaryVersion = primaryVersion;
	}

	/**       
	 * 属性扩展字段1的getter方法
	 */

	@Column(name = "flag1")
	public String getFlag1() {
		return this.flag1;
	}

	/**       
	 * 属性扩展字段1的setter方法
	 */
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	/**       
	 * 属性扩展字段2的getter方法
	 */

	@Column(name = "flag2")
	public String getFlag2() {
		return this.flag2;
	}

	/**       
	 * 属性扩展字段2的setter方法
	 */
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	/**       
	 * 属性扩展字段3的getter方法
	 */

	@Column(name = "flag3")
	public String getFlag3() {
		return this.flag3;
	}

	/**       
	 * 属性扩展字段3的setter方法
	 */
	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	/**       
	 * 属性扩展字段4的getter方法
	 */

	@Column(name = "flag4")
	public String getFlag4() {
		return this.flag4;
	}

	/**       
	 * 属性扩展字段4的setter方法
	 */
	public void setFlag4(String flag4) {
		this.flag4 = flag4;
	}

	/**       
	 * 属性扩展字段5的getter方法
	 */

	@Column(name = "flag5")
	public String getFlag5() {
		return this.flag5;
	}

	/**       
	 * 属性扩展字段5的setter方法
	 */
	public void setFlag5(String flag5) {
		this.flag5 = flag5;
	}

}
