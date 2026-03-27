// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.undwrt.undwrtBase.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinosoft.common.schema.model.PrpTmain;

/**
 * POJO类PrpTnote
 */
@Entity(name = "PRPTNOTE_UNDWRT")
@Table(name = "PRPTNOTE")
public class PrpTnote implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpTnoteId id;

	/** 属性PRPTMAIN */
	private PrpTmain prpTmain;

	/** 属性险类代码 */
	private String riskCode;

	/** 属性照会代号 */
	private String noteCode;

	/** 属性照会内容 */
	private String noteName;

	/** 属性照会日期 */
	private String noteDate;

	/** 属性照会回复日期 */
	private String replyDate;

	/** 属性处理状态 */
	private String flag;

	/** 属性照会期次 */
	private String noteTime;

	/** 属性跌二次照会回复日期 */
	private String SecondReplyDate;

	/** 属性第三次照会回复日期 */
	private String thirdReplyDate;

	/** 属性是否材料已补齐 */
	private String filledFlag;

	/** 属性实际回复日期 */
	private String actualReplyDate;

	/** 属性被保险人 */
	private String insuredName;

	/**
	 * 类PrpCengage的默认构造方法
	 */
	public PrpTnote() {
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "proposalNo", column = @Column(name = "PROPOSALNO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO"))})
	public PrpTnoteId getId() {
		return this.id;
	}

	public void setId(PrpTnoteId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROPOSALNO", nullable = false, insertable = false, updatable = false)
	public PrpTmain getPrpTmain() {
		return this.prpTmain;
	}

	public void setPrpTmain(PrpTmain prpTmain) {
		this.prpTmain = prpTmain;
	}

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "NOTECODE")
	public String getNoteCode() {
		return noteCode;
	}

	public void setNoteCode(String noteCode) {
		this.noteCode = noteCode;
	}

	@Column(name = "NOTENAME")
	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	@Column(name = "NOTEDATE")
	public String getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}

	@Column(name = "REPLYDATE")
	public String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	@Column(name = "NOTETIME")
	public String getNoteTime() {
		return noteTime;
	}

	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}

	@Column(name = "SECONDREPLYDATE")
	public String getSecondReplyDate() {
		return SecondReplyDate;
	}

	public void setSecondReplyDate(String secondReplyDate) {
		SecondReplyDate = secondReplyDate;
	}

	@Column(name = "THIRDREPLYDATE")
	public String getThirdReplyDate() {
		return thirdReplyDate;
	}

	public void setThirdReplyDate(String thirdReplyDate) {
		this.thirdReplyDate = thirdReplyDate;
	}

	@Column(name = "FILLEDFLAG")
	public String getFilledFlag() {
		return filledFlag;
	}

	public void setFilledFlag(String filledFlag) {
		this.filledFlag = filledFlag;
	}

	@Column(name = "ACTUALREPLYDATE")
	public String getActualReplyDate() {
		return actualReplyDate;
	}

	public void setActualReplyDate(String actualReplyDate) {
		this.actualReplyDate = actualReplyDate;
	}

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

}
