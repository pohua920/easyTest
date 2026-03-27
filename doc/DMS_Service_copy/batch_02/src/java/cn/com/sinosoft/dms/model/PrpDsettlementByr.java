package cn.com.sinosoft.dms.model;


// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��prpDsettlementByr
 */
@Entity
@Table(name = "prpdsettlementbyr")
public class PrpDsettlementByr implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����Ԥ�㵥λ���� */
	private String buyerUnitCode;

	/** ����Ԥ�㵥λ��� */
	private String buyerUnitName;

	/** ����Ԥ�㵥λ��ַ */
	private String buyerUnitAddress;

	/** ������Ч��־ */
	private String validStatus;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��prpDsettlementByr��Ĭ�Ϲ��췽��
	 */
	public PrpDsettlementByr() {
	}

	/**       
	 * ����Ԥ�㵥λ�����getter����
	 */
	@Id
	@Column(name = "buyerunitcode")
	public String getBuyerUnitCode() {
		return this.buyerUnitCode;
	}

	/**       
	 * ����Ԥ�㵥λ�����setter����
	 */
	public void setBuyerUnitCode(String buyerUnitCode) {
		this.buyerUnitCode = buyerUnitCode;
	}

	/**       
	 * ����Ԥ�㵥λ��Ƶ�getter����
	 */

	@Column(name = "buyerunitname")
	public String getBuyerUnitName() {
		return this.buyerUnitName;
	}

	/**       
	 * ����Ԥ�㵥λ��Ƶ�setter����
	 */
	public void setBuyerUnitName(String buyerUnitName) {
		this.buyerUnitName = buyerUnitName;
	}

	/**       
	 * ����Ԥ�㵥λ��ַ��getter����
	 */

	@Column(name = "buyerunitaddress")
	public String getBuyerUnitAddress() {
		return this.buyerUnitAddress;
	}

	/**       
	 * ����Ԥ�㵥λ��ַ��setter����
	 */
	public void setBuyerUnitAddress(String buyerUnitAddress) {
		this.buyerUnitAddress = buyerUnitAddress;
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
