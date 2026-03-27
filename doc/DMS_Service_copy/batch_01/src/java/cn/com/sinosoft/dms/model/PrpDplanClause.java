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
 * POJO类prpDplanClause
 */
@Entity
@Table(name = "prpdplanclause")
public class PrpDplanClause implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流水号ID */
	private PrpDplanClauseId id;

	/** 属性险种代码 */
	private String classCode;

	/** 属性条款简体中文名称 */
	private String clauseCName;

	/** 属性条款繁体中文名称 */
	private String clauseTName;

	/** 属性条款英文名称 */
	private String clauseEName;

	/** 属性条款中文简称 */
	private String clauseSCName;

	/** 属性条款英文简称 */
	private String clauseSEName;

	/** 属性条款版本 */
	private String clauseVersion;

	/** 属性条款属性(主险、附加险) */
	private String clauseAttribute;

	/** 属性适用区域层级(统总公司省公司市公司) */
	private String areaLevel;

	/** 属性地区编码 */
	private String areaCode;

	/** 属性适用区域名称(用逗号分隔) */
	private String areaName;

	/** 属性报备、报批号 */
	private String reportNo;

	/** 属性审批部门 */
	private String approvalDepart;

	/** 属性短期费率代码 */
	private String shortRateCode;

	/** 属性文件系统对应序号 */
	private String documentNumber;

	/** 属性文本内容 */
	private String contentNumber;

	/** 属性生效日期 */
	private Date validDate;

	/** 属性失效日期 */
	private Date invalidDate;

	/** 属性有效标志 */
	private String validInd;

	/** 属性预留字段1 */
	private String tcol1;

	/** 属性预留字段2 */
	private String tcol2;

	/** 属性预留字段3 */
	private String tcol3;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类prpDplanClause的默认构造方法
	 */
	public PrpDplanClause() {
	}

	/**       
	 * 属性流水号ID的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "planCode", column = @Column(name = "plancode")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")) })
	public PrpDplanClauseId getId() {
		return this.id;
	}

	/**       
	 * 属性流水号ID的setter方法
	 */
	public void setId(PrpDplanClauseId id) {
		this.id = id;
	}

	/**       
	 * 属性险种代码的getter方法
	 */

	@Column(name = "classcode")
	public String getClassCode() {
		return this.classCode;
	}

	/**       
	 * 属性险种代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**       
	 * 属性条款简体中文名称的getter方法
	 */

	@Column(name = "clausecname")
	public String getClauseCName() {
		return this.clauseCName;
	}

	/**       
	 * 属性条款简体中文名称的setter方法
	 */
	public void setClauseCName(String clauseCName) {
		this.clauseCName = clauseCName;
	}

	/**       
	 * 属性条款繁体中文名称的getter方法
	 */

	@Column(name = "clausetname")
	public String getClauseTName() {
		return this.clauseTName;
	}

	/**       
	 * 属性条款繁体中文名称的setter方法
	 */
	public void setClauseTName(String clauseTName) {
		this.clauseTName = clauseTName;
	}

	/**       
	 * 属性条款英文名称的getter方法
	 */

	@Column(name = "clauseename")
	public String getClauseEName() {
		return this.clauseEName;
	}

	/**       
	 * 属性条款英文名称的setter方法
	 */
	public void setClauseEName(String clauseEName) {
		this.clauseEName = clauseEName;
	}

	/**       
	 * 属性条款中文简称的getter方法
	 */

	@Column(name = "clausescname")
	public String getClauseSCName() {
		return this.clauseSCName;
	}

	/**       
	 * 属性条款中文简称的setter方法
	 */
	public void setClauseSCName(String clauseSCName) {
		this.clauseSCName = clauseSCName;
	}

	/**       
	 * 属性条款英文简称的getter方法
	 */

	@Column(name = "clausesename")
	public String getClauseSEName() {
		return this.clauseSEName;
	}

	/**       
	 * 属性条款英文简称的setter方法
	 */
	public void setClauseSEName(String clauseSEName) {
		this.clauseSEName = clauseSEName;
	}

	/**       
	 * 属性条款版本的getter方法
	 */

	@Column(name = "clauseversion")
	public String getClauseVersion() {
		return this.clauseVersion;
	}

	/**       
	 * 属性条款版本的setter方法
	 */
	public void setClauseVersion(String clauseVersion) {
		this.clauseVersion = clauseVersion;
	}

	/**       
	 * 属性条款属性(主险、附加险)的getter方法
	 */

	@Column(name = "clauseattribute")
	public String getClauseAttribute() {
		return this.clauseAttribute;
	}

	/**       
	 * 属性条款属性(主险、附加险)的setter方法
	 */
	public void setClauseAttribute(String clauseAttribute) {
		this.clauseAttribute = clauseAttribute;
	}

	/**       
	 * 属性适用区域层级(统总公司省公司市公司)的getter方法
	 */

	@Column(name = "arealevel")
	public String getAreaLevel() {
		return this.areaLevel;
	}

	/**       
	 * 属性适用区域层级(统总公司省公司市公司)的setter方法
	 */
	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	/**       
	 * 属性地区编码的getter方法
	 */

	@Column(name = "areacode")
	public String getAreaCode() {
		return this.areaCode;
	}

	/**       
	 * 属性地区编码的setter方法
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**       
	 * 属性适用区域名称(用逗号分隔)的getter方法
	 */

	@Column(name = "areaname")
	public String getAreaName() {
		return this.areaName;
	}

	/**       
	 * 属性适用区域名称(用逗号分隔)的setter方法
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**       
	 * 属性报备、报批号的getter方法
	 */

	@Column(name = "reportno")
	public String getReportNo() {
		return this.reportNo;
	}

	/**       
	 * 属性报备、报批号的setter方法
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**       
	 * 属性审批部门的getter方法
	 */

	@Column(name = "approvaldepart")
	public String getApprovalDepart() {
		return this.approvalDepart;
	}

	/**       
	 * 属性审批部门的setter方法
	 */
	public void setApprovalDepart(String approvalDepart) {
		this.approvalDepart = approvalDepart;
	}

	/**       
	 * 属性短期费率代码的getter方法
	 */

	@Column(name = "shortratecode")
	public String getShortRateCode() {
		return this.shortRateCode;
	}

	/**       
	 * 属性短期费率代码的setter方法
	 */
	public void setShortRateCode(String shortRateCode) {
		this.shortRateCode = shortRateCode;
	}

	/**       
	 * 属性文件系统对应序号的getter方法
	 */

	@Column(name = "documentnumber")
	public String getDocumentNumber() {
		return this.documentNumber;
	}

	/**       
	 * 属性文件系统对应序号的setter方法
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**       
	 * 属性文本内容的getter方法
	 */

	@Column(name = "contentnumber")
	public String getContentNumber() {
		return this.contentNumber;
	}

	/**       
	 * 属性文本内容的setter方法
	 */
	public void setContentNumber(String contentNumber) {
		this.contentNumber = contentNumber;
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
