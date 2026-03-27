package cn.com.sinosoft.dms.vo;
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��prpDcompany
 */
@Entity
@Table(name = "PRPDCOMPANY")
public class PrpDcompany implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������˻���� */
	private String comCode;

	/** ���Ի�������� */
	private String comCName;

	/** ���Ի�Ӣ����� */
	private String comEName;

	/** ���Ե�ַ������� */
	private String addressCName;

	/** ���Ե�ַӢ����� */
	private String addressEName;

	/** ������������ */
	private String postCode;

	/** ���Ե绰 */
	private String phoneNumber;

	/** ���Դ��� */
	private String faxNumber;

	/** �����ϼ������ */
	private String upperComCode;

	/** ���Թ����չ�˾��� */
	private String insurerName;

	/** ���Ի�����(��/����/�ո�) */
	private String comType;

	/** ���Ծ��� */
	private String manager;

	/** ���Ի�� */
	private String accountant;

	/** ���Ա�ע */
	private String remark;

	/** �������»���� */
	private String newComCode;

	/** ���Դ�ӡ�û�������� */
	private String printComCName;

	/** ���Դ�ӡ�û�Ӣ����� */
	private String printComEName;

	/** ���Դ�ӡ�û����ĵ�ַ */
	private String printAddressCName;

	/** ���Դ�ӡ�û�Ӣ�ĵ�ַ */
	private String printAddressEName;

	/** ���Դ�ӡ���ʱ� */
	private String printPostCode;

	/** ����Чf״̬(0ʧЧ/1��Ч) */
	private String validStatus;

	/** �����˻��������� */
	private String acntUnit;

	/** ����ר�����(��Ӧ��ƿ�Ŀ) */
	private String articleCode;

	/** ���Ա�־λ */
	private String flag;

	/** ����comflag */
	private String comFlag;

	/** ����centerflag */
	private String centerFlag;

	/** ����branchtype */
	private String branchType;

	/** ����comlevel */
	private BigDecimal comLevel;

	private Long gradeTemplId;
	
	private String upperPath;
	
	private String printWsUrl;
	/**
	 * ��prpDcompany��Ĭ�Ϲ��췽��
	 */
	public PrpDcompany() {
	}

	/**       
	 * ������˻�����getter����
	 */
	@Id
	@Column(name = "COMCODE")
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
	 * ���Ի�������Ƶ�getter����
	 */

	@Column(name = "COMCNAME")
	public String getComCName() {
		return this.comCName;
	}

	/**       
	 * ���Ի�������Ƶ�setter����
	 */
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	/**       
	 * ���Ի�Ӣ����Ƶ�getter����
	 */

	@Column(name = "COMENAME")
	public String getComEName() {
		return this.comEName;
	}

	/**       
	 * ���Ի�Ӣ����Ƶ�setter����
	 */
	public void setComEName(String comEName) {
		this.comEName = comEName;
	}

	/**       
	 * ���Ե�ַ������Ƶ�getter����
	 */

	@Column(name = "ADDRESSCNAME")
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

	@Column(name = "ADDRESSENAME")
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

	@Column(name = "POSTCODE")
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
	 * ���Ե绰��getter����
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * ���Ե绰��setter����
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * ���Դ����getter����
	 */

	@Column(name = "FAXNUMBER")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * ���Դ����setter����
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**       
	 * �����ϼ�������getter����
	 */

	@Column(name = "UPPERCOMCODE")
	public String getUpperComCode() {
		return this.upperComCode;
	}

	/**       
	 * �����ϼ�������setter����
	 */
	public void setUpperComCode(String upperComCode) {
		this.upperComCode = upperComCode;
	}

	/**       
	 * ���Թ����չ�˾��Ƶ�getter����
	 */

	@Column(name = "INSURERNAME")
	public String getInsurerName() {
		return this.insurerName;
	}

	/**       
	 * ���Թ����չ�˾��Ƶ�setter����
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**       
	 * ���Ի�����(��/����/�ո�)��getter����
	 */

	@Column(name = "COMTYPE")
	public String getComType() {
		return this.comType;
	}

	/**       
	 * ���Ի�����(��/����/�ո�)��setter����
	 */
	public void setComType(String comType) {
		this.comType = comType;
	}

	/**       
	 * ���Ծ����getter����
	 */

	@Column(name = "MANAGER")
	public String getManager() {
		return this.manager;
	}

	/**       
	 * ���Ծ����setter����
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**       
	 * ���Ի�Ƶ�getter����
	 */

	@Column(name = "ACCOUNTANT")
	public String getAccountant() {
		return this.accountant;
	}

	/**       
	 * ���Ի�Ƶ�setter����
	 */
	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "REMARK")
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
	 * �������»�����getter����
	 */

	@Column(name = "NEWCOMCODE")
	public String getNewComCode() {
		return this.newComCode;
	}

	/**       
	 * �������»�����setter����
	 */
	public void setNewComCode(String newComCode) {
		this.newComCode = newComCode;
	}

	/**       
	 * ���Դ�ӡ�û�������Ƶ�getter����
	 */

	@Column(name = "PRINTCOMCNAME")
	public String getPrintComCName() {
		return this.printComCName;
	}

	/**       
	 * ���Դ�ӡ�û�������Ƶ�setter����
	 */
	public void setPrintComCName(String printComCName) {
		this.printComCName = printComCName;
	}

	/**       
	 * ���Դ�ӡ�û�Ӣ����Ƶ�getter����
	 */

	@Column(name = "PRINTCOMENAME")
	public String getPrintComEName() {
		return this.printComEName;
	}

	/**       
	 * ���Դ�ӡ�û�Ӣ����Ƶ�setter����
	 */
	public void setPrintComEName(String printComEName) {
		this.printComEName = printComEName;
	}

	/**       
	 * ���Դ�ӡ�û����ĵ�ַ��getter����
	 */

	@Column(name = "PRINTADDRESSCNAME")
	public String getPrintAddressCName() {
		return this.printAddressCName;
	}

	/**       
	 * ���Դ�ӡ�û����ĵ�ַ��setter����
	 */
	public void setPrintAddressCName(String printAddressCName) {
		this.printAddressCName = printAddressCName;
	}

	/**       
	 * ���Դ�ӡ�û�Ӣ�ĵ�ַ��getter����
	 */

	@Column(name = "PRINTADDRESSENAME")
	public String getPrintAddressEName() {
		return this.printAddressEName;
	}

	/**       
	 * ���Դ�ӡ�û�Ӣ�ĵ�ַ��setter����
	 */
	public void setPrintAddressEName(String printAddressEName) {
		this.printAddressEName = printAddressEName;
	}

	/**       
	 * ���Դ�ӡ���ʱ��getter����
	 */

	@Column(name = "PRINTPOSTCODE")
	public String getPrintPostCode() {
		return this.printPostCode;
	}

	/**       
	 * ���Դ�ӡ���ʱ��setter����
	 */
	public void setPrintPostCode(String printPostCode) {
		this.printPostCode = printPostCode;
	}

	/**       
	 * ����Чf״̬(0ʧЧ/1��Ч)��getter����
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ����Чf״̬(0ʧЧ/1��Ч)��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * �����˻����������getter����
	 */

	@Column(name = "ACNTUNIT")
	public String getAcntUnit() {
		return this.acntUnit;
	}

	/**       
	 * �����˻����������setter����
	 */
	public void setAcntUnit(String acntUnit) {
		this.acntUnit = acntUnit;
	}

	/**       
	 * ����ר�����(��Ӧ��ƿ�Ŀ)��getter����
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**       
	 * ����ר�����(��Ӧ��ƿ�Ŀ)��setter����
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**       
	 * ���Ա�־λ��getter����
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Column(name = "COMFLAG")
	public String getComFlag() {
		return comFlag;
	}

	public void setComFlag(String comFlag) {
		this.comFlag = comFlag;
	}
	
	@Column(name = "CENTERFLAG")
	public String getCenterFlag() {
		return centerFlag;
	}

	public void setCenterFlag(String centerFlag) {
		this.centerFlag = centerFlag;
	}
	
	@Column(name = "BRANCHTYPE")
	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	
	@Column(name = "COMLEVEL")
	public BigDecimal getComLevel() {
		return comLevel;
	}

	public void setComLevel(BigDecimal comLevel) {
		this.comLevel = comLevel;
	}
	
	@Column(name = "GRADETEMPLID")
	public Long getGradeTemplId() {
		return gradeTemplId;
	}

	public void setGradeTemplId(Long gradeTemplId) {
		this.gradeTemplId = gradeTemplId;
	}
	
	@Column(name = "UPPERPATH")
	public String getUpperPath() {
		return upperPath;
	}

	public void setUpperPath(String upperPath) {
		this.upperPath = upperPath;
	}
	
	@Column(name = "PRINTWSURL")
	public String getPrintWsUrl() {
		return printWsUrl;
	}

	public void setPrintWsUrl(String printWsUrl) {
		this.printWsUrl = printWsUrl;
	}

}
