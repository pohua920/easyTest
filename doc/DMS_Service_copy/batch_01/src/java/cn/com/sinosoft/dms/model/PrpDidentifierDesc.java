package cn.com.sinosoft.dms.model;

// default package
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO��prpDidentifierDesc
 */
@Entity
@Table(name = "PRPDIDENTIFIERDESC")
public class PrpDidentifierDesc implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����Id */
	private PrpDidentifierDescId id;

	/** ���Լ�������Ϣ */
	private String mark;

	/** ���Ա�־ */
	private String flag;

	/**
	 * ��prpDidentifierDesc��Ĭ�Ϲ��췽��
	 */
	public PrpDidentifierDesc() {
	}

	/**       
	 * ����Id��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "identifierCode", column = @Column(name = "IDENTIFIERCODE")),
			@AttributeOverride(name = "lineNo", column = @Column(name = "LINENO")) })
	public PrpDidentifierDescId getId() {
		return this.id;
	}

	/**       
	 * ����Id��setter����
	 */
	public void setId(PrpDidentifierDescId id) {
		this.id = id;
	}

	/**       
	 * ���Լ�������Ϣ��getter����
	 */

	@Column(name = "MARK")
	public String getMark() {
		return this.mark;
	}

	/**       
	 * ���Լ�������Ϣ��setter����
	 */
	public void setMark(String mark) {
		this.mark = mark;
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
