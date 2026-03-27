package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpDcode
 */
@Entity
@Table(name = "PRPDCODE")
public class PrpDcode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDcodeId id;

	/** 属性名称 */
	private String codeCName;

	/** 属性英文名称 */
	private String codeEName;

	/** 属性NEWCODECODE */
	private String newCodeCode;
	
	/** 上级代码  */
	private String upperCode;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性标志 */
	private String flag;
	
	/** 等级  */
	private String codeLevel ;
	
	//mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額
	private String codeCDesc;/** 參數**/
	/**
	 * 类PrpDcode的默认构造方法
	 */
	public PrpDcode() {
		id = new PrpDcodeId();
	}

	public PrpDcode(PrpDcode prpDcode) {
		this.id = new PrpDcodeId(prpDcode.id);
		this.codeCName = prpDcode.codeCName;
		this.codeEName = prpDcode.codeEName;
		this.newCodeCode = prpDcode.newCodeCode;
		this.validStatus = prpDcode.validStatus;
		this.flag = prpDcode.flag;
	}

	public PrpDcode(PrpDcodeId id, String codeCName, String codeEName, String newCodeCode, String validStatus, String flag) {
		this.id = id;
		this.codeCName = codeCName;
		this.codeEName = codeEName;
		this.newCodeCode = newCodeCode;
		this.validStatus = validStatus;
		this.flag = flag;
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codeType", column = @Column(name = "CODETYPE")), @AttributeOverride(name = "codeCode", column = @Column(name = "CODECODE")) })
	public PrpDcodeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDcodeId id) {
		this.id = id;
	}

	/**
	 * 属性名称的getter方法
	 */

	@Column(name = "CODECNAME")
	public String getCodeCName() {
		return this.codeCName;
	}

	/**
	 * 属性名称的setter方法
	 */
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}

	/**
	 * 属性英文名称的getter方法
	 */

	@Column(name = "CODEENAME")
	public String getCodeEName() {
		return this.codeEName;
	}

	/**
	 * 属性英文名称的setter方法
	 */
	public void setCodeEName(String codeEName) {
		this.codeEName = codeEName;
	}

	/**
	 * 属性NEWCODECODE的getter方法
	 */

	@Column(name = "NEWCODECODE")
	public String getNewCodeCode() {
		return this.newCodeCode;
	}

	/**
	 * 属性NEWCODECODE的setter方法
	 */
	public void setNewCodeCode(String newCodeCode) {
		this.newCodeCode = newCodeCode;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Column(name = "upperCode")
	public String getUpperCode() {
		return upperCode;
	}

	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}
	
	@Column(name = "CODELEVEL")
	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	
	//mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額 START
	@Column(name = "CODECDESC")
	public String getCodeCDesc() {
		return codeCDesc;
	}

	public void setCodeCDesc(String codeCDesc) {
		this.codeCDesc = codeCDesc;
	}
	//mantis：CLM0296 ，處理人員：DP0713，需求單編號：新核心-調整醫療給付費用明細費用放寬卡控限額 END	
}
