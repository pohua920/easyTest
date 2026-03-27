package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.sinosoft.dms.model.PrpDareaId;

/**
 * POJO��prpDarea
 */
@Entity
@Table(name = "prpdarea")
public class PrpDarea implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDareaId id;

	/** ����������� */
	private String areaCName;

	/** ����Ԥ���ֶ�2 */
	private String tcol2;

	/** ����Ԥ���ֶ�3 */
	private String tcol3;

	/**
	 * ��prpDarea��Ĭ�Ϲ��췽��
	 */
	public PrpDarea() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")),
			@AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "areaCode", column = @Column(name = "areacode")),
			@AttributeOverride(name = "tcol1", column = @Column(name = "tcol1")) })
	public PrpDareaId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDareaId id) {
		this.id = id;
	}

	/**       
	 * ����������Ƶ�getter����
	 */

	@Column(name = "areacname")
	public String getAreaCName() {
		return this.areaCName;
	}

	/**       
	 * ����������Ƶ�setter����
	 */
	public void setAreaCName(String areaCName) {
		this.areaCName = areaCName;
	}

	/**       
	 * ����Ԥ���ֶ�2��getter����
	 */

	@Column(name = "tcol2")
	public String getTcol2() {
		return this.tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�2��setter����
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�3��getter����
	 */

	@Column(name = "tcol3")
	public String getTcol3() {
		return this.tcol3;
	}

	/**       
	 * ����Ԥ���ֶ�3��setter����
	 */
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

}
