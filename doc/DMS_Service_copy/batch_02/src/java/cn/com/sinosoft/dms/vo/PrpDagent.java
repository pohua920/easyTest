package cn.com.sinosoft.dms.vo;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpDagent
 */
@Entity
@Table(name = "prpdagent")
public class PrpDagent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性渠道代码 */
	private String agentCode;

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
	
	/** 属性标志字段 *///通路
	private String channelType;
	//and by xuli begin 20130523
	//登录日期
	private Date loginDate;
	//登录到期日
	private Date loginEndDate;
	//停止招攬期間始期
	private Date startJoinDate;

	//停止招攬終止期
	private Date endJoinDate;
	//登錄代碼(1,产险2,产险+健康险3,车险)
	private String loginCode;
	//'是否限制出單(1,是 0,否)';
	private String distanceFlag;
	//'代理人、经纪人終止日期';
	private Date validEndDate;
	//单位代码
	private String unitCode;
	//单位名称
	private String unitName;

	//and by xuli end 20130523
	//and by xuli end 20130617
	private String identifyNumber;
	private String userCode; 
	private String uniteCod;
	//涓氬姟鏉ユ簮浠ｇ爜
	private String businessSource;
	@Column(name = "identifyNumber")
	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	@Column(name = "userCode")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	//and by xuli end 20130617
	@Column(name = "loginDate")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	@Column(name = "loginEndDate")
	public Date getLoginEndDate() {
		return loginEndDate;
	}

	public void setLoginEndDate(Date loginEndDate) {
		this.loginEndDate = loginEndDate;
	}
	@Column(name = "startJoinDate")
	public Date getStartJoinDate() {
		return startJoinDate;
	}

	public void setStartJoinDate(Date startJoinDate) {
		this.startJoinDate = startJoinDate;
	}
	@Column(name = "endJoinDate")
	public Date getEndJoinDate() {
		return endJoinDate;
	}

	public void setEndJoinDate(Date endJoinDate) {
		this.endJoinDate = endJoinDate;
	}
	@Column(name = "loginCode")
	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	@Column(name = "distanceFlag")
	public String getDistanceFlag() {
		return distanceFlag;
	}

	public void setDistanceFlag(String distanceFlag) {
		this.distanceFlag = distanceFlag;
	}
	@Column(name = "validEndDate")
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}
	@Column(name = "unitCode")
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	@Column(name = "unitName")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * 类PrpDagent的默认构造方法
	 */
	public PrpDagent() {
	}

	/**       
	 * 属性渠道代码的getter方法
	 */
	@Id
	@Column(name = "agentcode")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**       
	 * 属性渠道代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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

	@Column(name = "channelType")
	public String getChannelType() {
		return this.channelType;
	}

	/**       
	 * 属性标志字段的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
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
	@Column(name = "uniteCod")
	public String getUniteCod() {
		return uniteCod;
	}

	public void setUniteCod(String uniteCod) {
		this.uniteCod = uniteCod;
	}
	@Column(name = "BUSINESSSOURCE")
	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	
}
