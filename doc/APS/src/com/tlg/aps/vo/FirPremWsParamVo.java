package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 	SOURCE_TYPE 來源，必填。 
 * 	SOURCE_USER 使用者，必填。
 *  CALC_TYPE 試算類型，必填。
 *  CALC_DATE 費率基準日，必填。 YYYYMMDD 
 *  CHANNEL_TYPE 通路別，必填。
 * 
 * @author bi086
 * 
 */
@XmlRootElement
public class FirPremWsParamVo {

	private String sourceType;
	private String sourceUser;
	private String calcType;
	private String calcDate;
	private String channelType;
	private ArrayList<FirInsPremVo> insPremList;
	private ArrayList<PbCkListVo> pbCkList;
	private ArrayList<ClauseVo> clauseList;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public String getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(String calcDate) {
		this.calcDate = calcDate;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public ArrayList<FirInsPremVo> getInsPremList() {
		return insPremList;
	}

	public void setInsPremList(ArrayList<FirInsPremVo> insPremList) {
		this.insPremList = insPremList;
	}

	public ArrayList<PbCkListVo> getPbCkList() {
		return pbCkList;
	}

	public void setPbCkList(ArrayList<PbCkListVo> pbCkList) {
		this.pbCkList = pbCkList;
	}

	public ArrayList<ClauseVo> getClauseList() {
		return clauseList;
	}

	public void setClauseList(ArrayList<ClauseVo> clauseList) {
		this.clauseList = clauseList;
	}

}
