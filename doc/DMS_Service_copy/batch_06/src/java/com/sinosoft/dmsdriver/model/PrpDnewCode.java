package com.sinosoft.dmsdriver.model;
// default package
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��PrpDnewCode
 */
@Entity
@Table(name = "prpdnewcode")
public class PrpDnewCode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** �������̶�����ʶ */
	private PrpDnewCodeId id;

	/** ����PrpDtype - ͨ�ô������ */
	private PrpDtype prpDtype;

	/** ����ҵ��������ĺ��� */
	private String codeCName;

	/** ����ҵ�����Ӣ�ĺ��� */
	private String codeEName;

	/** ����uppercode */
	private String upperCode;

	/** ����oldcodetype */
	private String oldCodeType;

	/** ���Ծ�ҵ����� */
	private String oldCodeCode;

	/** �����µ�ҵ����� */
	private String newCodeCode;

	/** �����Ƿ��ô��� */
	private String commonFlag;

	/** ������Ч���� */
	private Date validDate;

	/** ����ʧЧ���� */
	private Date invalidDate;

	/** ������Ч״̬(0��Ч1��Ч) */
	private String validStatus;

	/** ���Ա�־�ֶ� */
	private String flag;
	
	//added by yuyqiang 20130222 begin �����ݿ��ṹ����
	/** ���뼶�� */
	private String codeLevel;
	  
	/** ������������ */
    private String codeCdesc;
	  
	/** ����Ӣ������ */
	private String codeEdesc;
	  
	/** ���״̬(0-��ʼ״̬��1-���ͨ��2-��˲�ͨ��) */
	private String auditFlag;
    //add by mjx 20150303 新增字段  职业类别需要
	/**
	 * 属性职业大类代码
	 */
	private String codeBigCode;
	/**
	 * 属性职业中类代码
	 */
	private String codeMiddleCode;
	/**
	 * 属性职业大类名称
	 */
	private String codeBigName;
	/**
	 * 属性职业中类名称
	 */
	private String codeMiddleName;
	/**
	 * 属性修改人员
	 */
	private String updateUser;
	/**
	 * 属性建档人员
	 */
	private String createUser;
	/**
	 * 属性修改日期
	 */
	private Date updateDate;
	/**
	 * 属性建档日期
	 */
	private Date createDate;
	
	//add by  mjx  20150303 end 
	
	//added by yuyqiang 20130222 end
	/**
	 * ��PrpDnewCode��Ĭ�Ϲ��췽��
	 */
	public PrpDnewCode() {
	}

	/**       
	 * �������̶�����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")) })
	public PrpDnewCodeId getId() {
		return this.id;
	}

	/**       
	 * �������̶�����ʶ��setter����
	 */
	public void setId(PrpDnewCodeId id) {
		this.id = id;
	}

	/**       
	 * ����PrpDtype - ͨ�ô�������getter����
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codetype", nullable = false, insertable = false, updatable = false)
	public PrpDtype getPrpDtype() {
		return this.prpDtype;
	}

	/**       
	 * ����PrpDtype - ͨ�ô�������setter����
	 */
	public void setPrpDtype(PrpDtype prpDtype) {
		this.prpDtype = prpDtype;
	}

	/**       
	 * ����ҵ��������ĺ����getter����
	 */

	@Column(name = "codecname")
	public String getCodeCName() {
		return this.codeCName;
	}

	/**       
	 * ����ҵ��������ĺ����setter����
	 */
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}

	/**       
	 * ����ҵ�����Ӣ�ĺ����getter����
	 */

	@Column(name = "codeename")
	public String getCodeEName() {
		return this.codeEName;
	}

	/**       
	 * ����ҵ�����Ӣ�ĺ����setter����
	 */
	public void setCodeEName(String codeEName) {
		this.codeEName = codeEName;
	}

	/**       
	 * ����uppercode��getter����
	 */

	@Column(name = "uppercode")
	public String getUpperCode() {
		return this.upperCode;
	}

	/**       
	 * ����uppercode��setter����
	 */
	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}

	/**       
	 * ����oldcodetype��getter����
	 */

	@Column(name = "oldcodetype")
	public String getOldCodeType() {
		return this.oldCodeType;
	}

	/**       
	 * ����oldcodetype��setter����
	 */
	public void setOldCodeType(String oldCodeType) {
		this.oldCodeType = oldCodeType;
	}

	/**       
	 * ���Ծ�ҵ������getter����
	 */

	@Column(name = "oldcodecode")
	public String getOldCodeCode() {
		return this.oldCodeCode;
	}

	/**       
	 * ���Ծ�ҵ������setter����
	 */
	public void setOldCodeCode(String oldCodeCode) {
		this.oldCodeCode = oldCodeCode;
	}

	/**       
	 * �����µ�ҵ������getter����
	 */

	@Column(name = "newcodecode")
	public String getNewCodeCode() {
		return this.newCodeCode;
	}

	/**       
	 * �����µ�ҵ������setter����
	 */
	public void setNewCodeCode(String newCodeCode) {
		this.newCodeCode = newCodeCode;
	}

	/**       
	 * �����Ƿ��ô����getter����
	 */

	@Column(name = "commonflag")
	public String getCommonFlag() {
		return this.commonFlag;
	}

	/**       
	 * �����Ƿ��ô����setter����
	 */
	public void setCommonFlag(String commonFlag) {
		this.commonFlag = commonFlag;
	}

	/**       
	 * ������Ч���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * ������Ч���ڵ�setter����
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * ����ʧЧ���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "invaliddate")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**       
	 * ����ʧЧ���ڵ�setter����
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**       
	 * ������Ч״̬(0��Ч1��Ч)��getter����
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ������Ч״̬(0��Ч1��Ч)��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * ���Ա�־�ֶε�getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־�ֶε�setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "codelevel")
	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	@Column(name = "codecdesc")
	public String getCodeCdesc() {
		return codeCdesc;
	}

	public void setCodeCdesc(String codeCdesc) {
		this.codeCdesc = codeCdesc;
	}

	@Column(name = "codeedesc")
	public String getCodeEdesc() {
		return codeEdesc;
	}

	public void setCodeEdesc(String codeEdesc) {
		this.codeEdesc = codeEdesc;
	}

	@Column(name = "auditflag")
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	
	@Column(name = "codebigcode")
	public String getCodeBigCode() {
		return codeBigCode;
	}

	public void setCodeBigCode(String codeBigCode) {
		this.codeBigCode = codeBigCode;
	}
	
	@Column(name = "codemiddlecode")
	public String getCodeMiddleCode() {
		return codeMiddleCode;
	}

	public void setCodeMiddleCode(String codeMiddleCode) {
		this.codeMiddleCode = codeMiddleCode;
	}
	@Column(name = "codebigname")
	public String getCodeBigName() {
		return codeBigName;
	}

	public void setCodeBigName(String codeBigName) {
		this.codeBigName = codeBigName;
	}
	
	@Column(name = "codemiddlename")
	public String getCodeMiddleName() {
		return codeMiddleName;
	}

	public void setCodeMiddleName(String codeMiddleName) {
		this.codeMiddleName = codeMiddleName;
	}
	
	@Column(name = "updateuser")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@Column(name = "createuser")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name = "updatedate")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "createdate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
