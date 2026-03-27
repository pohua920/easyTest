package cn.com.sinosoft.dms.model;

// default package
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��prpDcurrency
 */
@Entity
@Table(name = "PRPDCURRENCY")
public class PrpDcurrency implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Աұ���� */
	private String currencyCode;

	/** ���Աұ�������� */
	private String currencyCName;

	/** ���Աұ�Ӣ����� */
	private String currencyEName;

	/** �����ʲ����� */
	private String accBookCode;

	/** �������±ұ���� */
	private String newCurrencyCode;

	/** ����Чf״̬(0ʧЧ/1��Ч) */
	private String validStatus;

	/** ���Ա�־ */
	private String flag;

	/**
	 * ��prpDcurrency��Ĭ�Ϲ��췽��
	 */
	public PrpDcurrency() {
	}

	/**       
	 * ���Աұ�����getter����
	 */
	@Id
	@Column(name = "CURRENCYCODE")
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	/**       
	 * ���Աұ�����setter����
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**       
	 * ���Աұ�������Ƶ�getter����
	 */

	@Column(name = "CURRENCYCNAME")
	public String getCurrencyCName() {
		return this.currencyCName;
	}

	/**       
	 * ���Աұ�������Ƶ�setter����
	 */
	public void setCurrencyCName(String currencyCName) {
		this.currencyCName = currencyCName;
	}

	/**       
	 * ���Աұ�Ӣ����Ƶ�getter����
	 */

	@Column(name = "CURRENCYENAME")
	public String getCurrencyEName() {
		return this.currencyEName;
	}

	/**       
	 * ���Աұ�Ӣ����Ƶ�setter����
	 */
	public void setCurrencyEName(String currencyEName) {
		this.currencyEName = currencyEName;
	}

	/**       
	 * �����ʲ������getter����
	 */

	@Column(name = "ACCBOOKCODE")
	public String getAccBookCode() {
		return this.accBookCode;
	}

	/**       
	 * �����ʲ������setter����
	 */
	public void setAccBookCode(String accBookCode) {
		this.accBookCode = accBookCode;
	}

	/**       
	 * �������±ұ�����getter����
	 */

	@Column(name = "NEWCURRENCYCODE")
	public String getNewCurrencyCode() {
		return this.newCurrencyCode;
	}

	/**       
	 * �������±ұ�����setter����
	 */
	public void setNewCurrencyCode(String newCurrencyCode) {
		this.newCurrencyCode = newCurrencyCode;
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
	 * ���Ա�־��getter����
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־��setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
