package cn.com.sinosoft.dms.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRPDRATIONRELATION")
public class PrpDrationRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrpDrationRelationId id;
	// 辅助方案名称
	private String rationAssistName;
	// 有效标记
	private String validInd;

	public PrpDrationRelation() {
	}

	public PrpDrationRelation(PrpDrationRelationId id, String rationAssistName,
			String validInd) {
		this.id = id;
		this.rationAssistName = rationAssistName;
		this.validInd = validInd;
	}

	/**
	 * 方案关系类主键
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "rationCodeMain", column = @Column(name = "RATIONCODEMAIN")),
			@AttributeOverride(name = "rationCodeAssist", column = @Column(name = "RATIONCODEASSIST")) })
	public PrpDrationRelationId getId() {
		return id;
	}

	public void setId(PrpDrationRelationId id) {
		this.id = id;
	}

	@Column(name = "RATIONASSISTNAME")
	public String getRationAssistName() {
		return rationAssistName;
	}

	public void setRationAssistName(String rationAssistName) {
		this.rationAssistName = rationAssistName;
	}

	@Column(name = "validInd")
	public String getValidInd() {
		return validInd;
	}

	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}

}
