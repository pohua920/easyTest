package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

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
 * POJO��prpdagentcontract
 */
@Entity
@Table(name = "prpdagentcontract")
public class PrpDagentContract implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������ˮ��ID */
	private PrpDagentContractId id;

	/** ����contractstartdate */
	private Date contractStartDate;

	/** ����contractenddate */
	private Date contractEndDate;

	/** ����payaccount */
	private String payAccount;

	/** ���Կ������� */
	private String bank;

	/** ����payrate */
	private String payRate;

	/** ����copyway */
	private String copyWay;

	/** ����Чf״̬(0ʧЧ/1��Ч) */
	private String validStatus;

	/** ���Ա�־�ֶ� */
	private String flag;

	/**
	 * ��prpdagentcontract��Ĭ�Ϲ��췽��
	 */
	public PrpDagentContract() {
	}

	/**       
	 * ������ˮ��ID��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "agentCode", column = @Column(name = "agentcode")),
			@AttributeOverride(name = "contractno", column = @Column(name = "contractno")) })
	public PrpDagentContractId getId() {
		return this.id;
	}

	/**       
	 * ������ˮ��ID��setter����
	 */
	public void setId(PrpDagentContractId id) {
		this.id = id;
	}

	/**       
	 * ����contractstartdate��getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "contractstartdate")
	public Date getContractStartDate() {
		return this.contractStartDate;
	}

	/**       
	 * ����contractstartdate��setter����
	 */
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	/**       
	 * ����contractenddate��getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "contractenddate")
	public Date getContractEndDate() {
		return this.contractEndDate;
	}

	/**       
	 * ����contractenddate��setter����
	 */
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**       
	 * ����payaccount��getter����
	 */

	@Column(name = "payaccount")
	public String getPayAccount() {
		return this.payAccount;
	}

	/**       
	 * ����payaccount��setter����
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	/**       
	 * ���Կ������е�getter����
	 */

	@Column(name = "bank")
	public String getBank() {
		return this.bank;
	}

	/**       
	 * ���Կ������е�setter����
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**       
	 * ����payrate��getter����
	 */

	@Column(name = "payrate")
	public String getPayRate() {
		return this.payRate;
	}

	/**       
	 * ����payrate��setter����
	 */
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	/**       
	 * ����copyway��getter����
	 */

	@Column(name = "copyway")
	public String getCopyWay() {
		return this.copyWay;
	}

	/**       
	 * ����copyway��setter����
	 */
	public void setCopyWay(String copyWay) {
		this.copyWay = copyWay;
	}

	/**       
	 * ����Чf״̬(0ʧЧ/1��Ч)��getter����
	 */

	@Column(name = "validstatus")
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

}
