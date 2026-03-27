package com.sinosoft.undwrt.common.model;

// Generated 2012-12-28 11:12:02 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 基礎代碼配置類.
 */
@Entity(name = "PRPDCODE_UNDWRT")
@Table(name = "PRPDCODE")
public class PrpDcode implements java.io.Serializable {

	/** 屬性id. */
	private PrpDcodeId id;

	/** 屬性代碼英文名稱. */
	private String codeEName;

	/** 屬性標志. */
	private String flag;

	/**
	 * 默認構造方法.
	 */
	public PrpDcode() {
	}

	/**
	 * 構造方法.
	 * 
	 * @param id
	 *            the id
	 */
	public PrpDcode(PrpDcodeId id) {
		this.id = id;
	}

	/**
	 * 獲取屬性id.
	 * 
	 * @return 屬性id的值
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE", nullable = false, length = 20)),
			@AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE", nullable = false, length = 40)),
			@AttributeOverride(name = "codeCName", column = @Column(name = "CODECNAME", length = 500)),
			@AttributeOverride(name = "newCodeCode", column = @Column(name = "NEWCODECODE", nullable = false, length = 40)),
			@AttributeOverride(name = "validStatus", column = @Column(name = "VALIDSTATUS", nullable = false, length = 1)) })
	public PrpDcodeId getId() {
		return this.id;
	}

	/**
	 * 設置屬性id.
	 * 
	 * @param id
	 *            待設置的id的值
	 */
	public void setId(PrpDcodeId id) {
		this.id = id;
	}

	/**
	 * 獲取屬性代碼英文名稱.
	 * 
	 * @return 屬性代碼英文名稱的值
	 */
	@Column(name = "CODEENAME", length = 100)
	public String getCodeEName() {
		return this.codeEName;
	}

	/**
	 * 設置屬性代碼英文名稱.
	 * 
	 * @param codeEName
	 *            待設置的代碼英文名稱的值
	 */
	public void setCodeEName(String codeEName) {
		this.codeEName = codeEName;
	}

	/**
	 * 獲取屬性標志.
	 * 
	 * @return 屬性標志的值
	 */
	@Column(name = "FLAG", length = 20)
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 設置屬性標志.
	 * 
	 * @param flag
	 *            待設置的標志的值
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
