package cn.com.sinosoft.dms.model;
// default package
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
 * POJO类PrpDagentAll
 */
@Entity
@Table(name = "prpdagentall")
public class PrpDagentAll implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDagentAllId id;

	/** 属性渠道名称 */
	private String agentName;

	/** 属性渠道地址 */
	private String addressName;

	/** 属性邮政编码 */
	private String postCode;

	/** 属性渠道类型 */
	private String agentType;

	/** 属性许可证号 */
	private String permitNo;

	/** 属性联系人 */
	private String linkerName;

	/** 属性合同期 */
	private Date bargainDate;

	/** 属性电话 */
	private String phoneNumber;

	/** 属性传真 */
	private String faxNumber;

	/** 属性归属机构代码 */
	private String comCode;

	/** 属性上级代理人代码 */
	private String upperAgentCode;

	/** 属性新的代理人代码 */
	private String newAgentCode;

	/** 属性是否允许归属机构的下级机构使用 */
	private String agentNature;

	/** 属性有效状态(0无效1有效) */
	private String validStatus;

	/** 属性专项代码(对应会计科目) */
	private String articleCode;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDagentAll的默认构造方法
	 */
	public PrpDagentAll() {
	}

	/**       
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "agentCode", column = @Column(name = "agentcode")),
			@AttributeOverride(name = "locatecomcode", column = @Column(name = "locatecomcode")) })
	public PrpDagentAllId getId() {
		return this.id;
	}

	/**       
	 * 属性id的setter方法
	 */
	public void setId(PrpDagentAllId id) {
		this.id = id;
	}

	/**       
	 * 属性渠道名称的getter方法
	 */

	@Column(name = "agentname")
	public String getAgentName() {
		return this.agentName;
	}

	/**       
	 * 属性渠道名称的setter方法
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**       
	 * 属性渠道地址的getter方法
	 */

	@Column(name = "addressname")
	public String getAddressName() {
		return this.addressName;
	}

	/**       
	 * 属性渠道地址的setter方法
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**       
	 * 属性邮政编码的getter方法
	 */

	@Column(name = "postcode")
	public String getPostCode() {
		return this.postCode;
	}

	/**       
	 * 属性邮政编码的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**       
	 * 属性渠道类型的getter方法
	 */

	@Column(name = "agenttype")
	public String getAgentType() {
		return this.agentType;
	}

	/**       
	 * 属性渠道类型的setter方法
	 */
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	/**       
	 * 属性许可证号的getter方法
	 */

	@Column(name = "permitno")
	public String getPermitNo() {
		return this.permitNo;
	}

	/**       
	 * 属性许可证号的setter方法
	 */
	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	/**       
	 * 属性联系人的getter方法
	 */

	@Column(name = "linkername")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**       
	 * 属性联系人的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**       
	 * 属性合同期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "bargaindate")
	public Date getBargainDate() {
		return this.bargainDate;
	}

	/**       
	 * 属性合同期的setter方法
	 */
	public void setBargainDate(Date bargainDate) {
		this.bargainDate = bargainDate;
	}

	/**       
	 * 属性电话的getter方法
	 */

	@Column(name = "phonenumber")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * 属性电话的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * 属性传真的getter方法
	 */

	@Column(name = "faxnumber")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * 属性传真的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**       
	 * 属性归属机构代码的getter方法
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * 属性归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * 属性上级代理人代码的getter方法
	 */

	@Column(name = "upperagentcode")
	public String getUpperAgentCode() {
		return this.upperAgentCode;
	}

	/**       
	 * 属性上级代理人代码的setter方法
	 */
	public void setUpperAgentCode(String upperAgentCode) {
		this.upperAgentCode = upperAgentCode;
	}

	/**       
	 * 属性新的代理人代码的getter方法
	 */

	@Column(name = "newagentcode")
	public String getNewAgentCode() {
		return this.newAgentCode;
	}

	/**       
	 * 属性新的代理人代码的setter方法
	 */
	public void setNewAgentCode(String newAgentCode) {
		this.newAgentCode = newAgentCode;
	}

	/**       
	 * 属性是否允许归属机构的下级机构使用的getter方法
	 */

	@Column(name = "agentnature")
	public String getAgentNature() {
		return this.agentNature;
	}

	/**       
	 * 属性是否允许归属机构的下级机构使用的setter方法
	 */
	public void setAgentNature(String agentNature) {
		this.agentNature = agentNature;
	}

	/**       
	 * 属性有效状态(0无效1有效)的getter方法
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * 属性有效状态(0无效1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * 属性专项代码(对应会计科目)的getter方法
	 */

	@Column(name = "articlecode")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**       
	 * 属性专项代码(对应会计科目)的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
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
