package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��prpdtaxauthorities
 */
@Entity
@Table(name = "prpdtaxauthorities")
public class PrpDtaxAuthorities implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����taxauthcode */
	private String taxAuthCode;

	/** ����taxauthcname */
	private String taxAuthCName;

	/** ����taxauthaliasname */
	private String taxAuthAliasName;

	/** ����taxauthename */
	private String taxAuthEName;

	/** ���Ե�ַ������� */
	private String addressCName;

	/** ���Ե�ַӢ����� */
	private String addressEName;

	/** ������������ */
	private String postCode;

	/** ����phonenumber */
	private String phoneNumber;

	/** ���Դ������ */
	private String faxNumber;

	/** ����upperauthcode */
	private String upperAuthCode;

	/** ������˻���� */
	private String comCode;

	/** ����manager */
	private String manager;

	/** ����taxleader */
	private String taxLeader;

	/** ����taxdeportment */
	private String taxDeportment;

	/** ����authlevel */
	private String authLevel;

	/** ����taxbusiness */
	private String taxBusiness;

	/** ���Ա�ע */
	private String remark;

	/** ���������� */
	private Date startDate;

	/** �����ձ����� */
	private Date endDate;

	/** �������û���� */
	private String userCode;

	/** ������Ч��־ */
	private String validStatus;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��prpdtaxauthorities��Ĭ�Ϲ��췽��
	 */
	public PrpDtaxAuthorities() {
	}

	/**       
	 * ����taxauthcode��getter����
	 */
	@Id
	@Column(name = "taxauthcode")
	public String getTaxAuthCode() {
		return this.taxAuthCode;
	}

	/**       
	 * ����taxauthcode��setter����
	 */
	public void setTaxAuthCode(String taxAuthCode) {
		this.taxAuthCode = taxAuthCode;
	}

	/**       
	 * ����taxauthcname��getter����
	 */

	@Column(name = "taxauthcname")
	public String getTaxAuthCName() {
		return this.taxAuthCName;
	}

	/**       
	 * ����taxauthcname��setter����
	 */
	public void setTaxAuthCName(String taxAuthCName) {
		this.taxAuthCName = taxAuthCName;
	}

	/**       
	 * ����taxauthaliasname��getter����
	 */

	@Column(name = "taxauthaliasname")
	public String getTaxAuthAliasName() {
		return this.taxAuthAliasName;
	}

	/**       
	 * ����taxauthaliasname��setter����
	 */
	public void setTaxAuthAliasName(String taxAuthAliasName) {
		this.taxAuthAliasName = taxAuthAliasName;
	}

	/**       
	 * ����taxauthename��getter����
	 */

	@Column(name = "taxauthename")
	public String getTaxAuthEName() {
		return this.taxAuthEName;
	}

	/**       
	 * ����taxauthename��setter����
	 */
	public void setTaxAuthEName(String taxAuthEName) {
		this.taxAuthEName = taxAuthEName;
	}

	/**       
	 * ���Ե�ַ������Ƶ�getter����
	 */

	@Column(name = "addresscname")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**       
	 * ���Ե�ַ������Ƶ�setter����
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**       
	 * ���Ե�ַӢ����Ƶ�getter����
	 */

	@Column(name = "addressename")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**       
	 * ���Ե�ַӢ����Ƶ�setter����
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**       
	 * �������������getter����
	 */

	@Column(name = "postcode")
	public String getPostCode() {
		return this.postCode;
	}

	/**       
	 * �������������setter����
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**       
	 * ����phonenumber��getter����
	 */

	@Column(name = "phonenumber")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * ����phonenumber��setter����
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * ���Դ�������getter����
	 */

	@Column(name = "faxnumber")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * ���Դ�������setter����
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**       
	 * ����upperauthcode��getter����
	 */

	@Column(name = "upperauthcode")
	public String getUpperAuthCode() {
		return this.upperAuthCode;
	}

	/**       
	 * ����upperauthcode��setter����
	 */
	public void setUpperAuthCode(String upperAuthCode) {
		this.upperAuthCode = upperAuthCode;
	}

	/**       
	 * ������˻�����getter����
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * ������˻�����setter����
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * ����manager��getter����
	 */

	@Column(name = "manager")
	public String getManager() {
		return this.manager;
	}

	/**       
	 * ����manager��setter����
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**       
	 * ����taxleader��getter����
	 */

	@Column(name = "taxleader")
	public String getTaxLeader() {
		return this.taxLeader;
	}

	/**       
	 * ����taxleader��setter����
	 */
	public void setTaxLeader(String taxLeader) {
		this.taxLeader = taxLeader;
	}

	/**       
	 * ����taxdeportment��getter����
	 */

	@Column(name = "taxdeportment")
	public String getTaxDeportment() {
		return this.taxDeportment;
	}

	/**       
	 * ����taxdeportment��setter����
	 */
	public void setTaxDeportment(String taxDeportment) {
		this.taxDeportment = taxDeportment;
	}

	/**       
	 * ����authlevel��getter����
	 */

	@Column(name = "authlevel")
	public String getAuthLevel() {
		return this.authLevel;
	}

	/**       
	 * ����authlevel��setter����
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**       
	 * ����taxbusiness��getter����
	 */

	@Column(name = "taxbusiness")
	public String getTaxBusiness() {
		return this.taxBusiness;
	}

	/**       
	 * ����taxbusiness��setter����
	 */
	public void setTaxBusiness(String taxBusiness) {
		this.taxBusiness = taxBusiness;
	}

	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * ���Ա�ע��setter����
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**       
	 * ���������ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	public Date getStartDate() {
		return this.startDate;
	}

	/**       
	 * ���������ڵ�setter����
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**       
	 * �����ձ����ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	public Date getEndDate() {
		return this.endDate;
	}

	/**       
	 * �����ձ����ڵ�setter����
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**       
	 * �������û���ŵ�getter����
	 */

	@Column(name = "usercode")
	public String getUserCode() {
		return this.userCode;
	}

	/**       
	 * �������û���ŵ�setter����
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**       
	 * ������Ч��־��getter����
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ������Ч��־��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * ���Ա�־λ��getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־λ��setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
 