package cn.com.sinosoft.dms.vo;

// 锟斤拷锟矫癸拷锟斤拷 Hibernate Tools 3.2.4.GA (sinosoft version) 锟斤拷桑锟斤拷锟斤拷锟斤拷止锟斤拷薷摹锟�

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO锟斤拷prpdtaxauthorities
 */
@Entity
@Table(name = "prpdtaxauthorities")
public class PrpDtaxAuthorities implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 锟斤拷锟斤拷taxauthcode */
	private String taxAuthCode;

	/** 锟斤拷锟斤拷taxauthcname */
	private String taxAuthCName;

	/** 锟斤拷锟斤拷taxauthaliasname */
	private String taxAuthAliasName;

	/** 锟斤拷锟斤拷taxauthename */
	private String taxAuthEName;

	/** 锟斤拷锟皆碉拷址锟斤拷锟斤拷锟斤拷锟�*/
	private String addressCName;

	/** 锟斤拷锟皆碉拷址英锟斤拷锟斤拷锟�*/
	private String addressEName;

	/** 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 */
	private String postCode;

	/** 锟斤拷锟斤拷phonenumber */
	private String phoneNumber;

	/** 锟斤拷锟皆达拷锟斤拷锟斤拷锟�*/
	private String faxNumber;

	/** 锟斤拷锟斤拷upperauthcode */
	private String upperAuthCode;

	/** 锟斤拷锟斤拷锟斤拷嘶锟斤拷锟斤拷 */
	private String comCode;

	/** 锟斤拷锟斤拷manager */
	private String manager;

	/** 锟斤拷锟斤拷taxleader */
	private String taxLeader;

	/** 锟斤拷锟斤拷taxdeportment */
	private String taxDeportment;

	/** 锟斤拷锟斤拷authlevel */
	private String authLevel;

	/** 锟斤拷锟斤拷taxbusiness */
	private String taxBusiness;

	/** 锟斤拷锟皆憋拷注 */
	private String remark;

	/** 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 */
	private Date startDate;

	/** 锟斤拷锟斤拷锟秸憋拷锟斤拷锟斤拷 */
	private Date endDate;

	/** 锟斤拷锟斤拷锟斤拷锟矫伙拷锟斤拷锟�*/
	private String userCode;

	/** 锟斤拷锟斤拷锟斤拷效锟斤拷志 */
	private String validStatus;

	/** 锟斤拷锟皆憋拷志位 */
	private String flag;

	/**
	 * 锟斤拷prpdtaxauthorities锟斤拷默锟较癸拷锟届方锟斤拷
	 */
	public PrpDtaxAuthorities() {
	}

	/**       
	 * 锟斤拷锟斤拷taxauthcode锟斤拷getter锟斤拷锟斤拷
	 */
	@Id
	@Column(name = "taxauthcode")
	public String getTaxAuthCode() {
		return this.taxAuthCode;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthcode锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxAuthCode(String taxAuthCode) {
		this.taxAuthCode = taxAuthCode;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthcname锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxauthcname")
	public String getTaxAuthCName() {
		return this.taxAuthCName;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthcname锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxAuthCName(String taxAuthCName) {
		this.taxAuthCName = taxAuthCName;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthaliasname锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxauthaliasname")
	public String getTaxAuthAliasName() {
		return this.taxAuthAliasName;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthaliasname锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxAuthAliasName(String taxAuthAliasName) {
		this.taxAuthAliasName = taxAuthAliasName;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthename锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxauthename")
	public String getTaxAuthEName() {
		return this.taxAuthEName;
	}

	/**       
	 * 锟斤拷锟斤拷taxauthename锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxAuthEName(String taxAuthEName) {
		this.taxAuthEName = taxAuthEName;
	}

	/**       
	 * 锟斤拷锟皆碉拷址锟斤拷锟斤拷锟斤拷频锟絞etter锟斤拷锟斤拷
	 */

	@Column(name = "addresscname")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**       
	 * 锟斤拷锟皆碉拷址锟斤拷锟斤拷锟斤拷频锟絪etter锟斤拷锟斤拷
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**       
	 * 锟斤拷锟皆碉拷址英锟斤拷锟斤拷频锟絞etter锟斤拷锟斤拷
	 */

	@Column(name = "addressename")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**       
	 * 锟斤拷锟皆碉拷址英锟斤拷锟斤拷频锟絪etter锟斤拷锟斤拷
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絞etter锟斤拷锟斤拷
	 */

	@Column(name = "postcode")
	public String getPostCode() {
		return this.postCode;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絪etter锟斤拷锟斤拷
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**       
	 * 锟斤拷锟斤拷phonenumber锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "phonenumber")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * 锟斤拷锟斤拷phonenumber锟斤拷setter锟斤拷锟斤拷
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * 锟斤拷锟皆达拷锟斤拷锟斤拷锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "faxnumber")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * 锟斤拷锟皆达拷锟斤拷锟斤拷锟斤拷setter锟斤拷锟斤拷
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**       
	 * 锟斤拷锟斤拷upperauthcode锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "upperauthcode")
	public String getUpperAuthCode() {
		return this.upperAuthCode;
	}

	/**       
	 * 锟斤拷锟斤拷upperauthcode锟斤拷setter锟斤拷锟斤拷
	 */
	public void setUpperAuthCode(String upperAuthCode) {
		this.upperAuthCode = upperAuthCode;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷嘶锟斤拷锟斤拷锟絞etter锟斤拷锟斤拷
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷嘶锟斤拷锟斤拷锟絪etter锟斤拷锟斤拷
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * 锟斤拷锟斤拷manager锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "manager")
	public String getManager() {
		return this.manager;
	}

	/**       
	 * 锟斤拷锟斤拷manager锟斤拷setter锟斤拷锟斤拷
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**       
	 * 锟斤拷锟斤拷taxleader锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxleader")
	public String getTaxLeader() {
		return this.taxLeader;
	}

	/**       
	 * 锟斤拷锟斤拷taxleader锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxLeader(String taxLeader) {
		this.taxLeader = taxLeader;
	}

	/**       
	 * 锟斤拷锟斤拷taxdeportment锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxdeportment")
	public String getTaxDeportment() {
		return this.taxDeportment;
	}

	/**       
	 * 锟斤拷锟斤拷taxdeportment锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxDeportment(String taxDeportment) {
		this.taxDeportment = taxDeportment;
	}

	/**       
	 * 锟斤拷锟斤拷authlevel锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "authlevel")
	public String getAuthLevel() {
		return this.authLevel;
	}

	/**       
	 * 锟斤拷锟斤拷authlevel锟斤拷setter锟斤拷锟斤拷
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**       
	 * 锟斤拷锟斤拷taxbusiness锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "taxbusiness")
	public String getTaxBusiness() {
		return this.taxBusiness;
	}

	/**       
	 * 锟斤拷锟斤拷taxbusiness锟斤拷setter锟斤拷锟斤拷
	 */
	public void setTaxBusiness(String taxBusiness) {
		this.taxBusiness = taxBusiness;
	}

	/**       
	 * 锟斤拷锟皆憋拷注锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * 锟斤拷锟皆憋拷注锟斤拷setter锟斤拷锟斤拷
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟节碉拷getter锟斤拷锟斤拷
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	public Date getStartDate() {
		return this.startDate;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟节碉拷setter锟斤拷锟斤拷
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**       
	 * 锟斤拷锟斤拷锟秸憋拷锟斤拷锟节碉拷getter锟斤拷锟斤拷
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	public Date getEndDate() {
		return this.endDate;
	}

	/**       
	 * 锟斤拷锟斤拷锟秸憋拷锟斤拷锟节碉拷setter锟斤拷锟斤拷
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟矫伙拷锟斤拷诺锟絞etter锟斤拷锟斤拷
	 */

	@Column(name = "usercode")
	public String getUserCode() {
		return this.userCode;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷锟矫伙拷锟斤拷诺锟絪etter锟斤拷锟斤拷
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷效锟斤拷志锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * 锟斤拷锟斤拷锟斤拷效锟斤拷志锟斤拷setter锟斤拷锟斤拷
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * 锟斤拷锟皆憋拷志位锟斤拷getter锟斤拷锟斤拷
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 锟斤拷锟皆憋拷志位锟斤拷setter锟斤拷锟斤拷
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
