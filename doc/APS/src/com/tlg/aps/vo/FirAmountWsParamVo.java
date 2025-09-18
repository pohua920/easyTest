package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 	SOURCE_TYPE	來源，必填。
	SOURCE_USER	使用者，必填。
	CALC_TYPE	試算類別，必填。
	CALC_DATE	費率基準日，必填。YYYYMMDD
	CHANNEL_TYPE	通路別，必填。
	POSTCODE	郵遞區號3碼，必填。
	WALLNO	建物結構2碼，必填。
	ROOFNO	屋頂別，2碼，必填。
	SUMFLOORS	總樓層數，必填。
	BUILDAREA	坪數，必填。
	DÉCOR_FEE	每坪裝潢費，必填。

 * @author bi086
 *
 */
@XmlRootElement
public class FirAmountWsParamVo {

	private String sourceType;
	private String sourceUser;
	private String calcType;
	private String calcDate;
	private String channelType;
	private String postcode;
	private String wallno;
	private String roofno;
	private String sumfloors;
	private String buildarea;
	private String decorFee;
	
	//mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程
	private String amountFlag;

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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWallno() {
		return wallno;
	}

	public void setWallno(String wallno) {
		this.wallno = wallno;
	}

	public String getRoofno() {
		return roofno;
	}

	public void setRoofno(String roofno) {
		this.roofno = roofno;
	}

	public String getSumfloors() {
		return sumfloors;
	}

	public void setSumfloors(String sumfloors) {
		this.sumfloors = sumfloors;
	}

	public String getBuildarea() {
		return buildarea;
	}

	public void setBuildarea(String buildarea) {
		this.buildarea = buildarea;
	}

	public String getDecorFee() {
		return decorFee;
	}

	public void setDecorFee(String decorFee) {
		this.decorFee = decorFee;
	}

	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start*/
	public String getAmountFlag() {
		return amountFlag;
	}

	public void setAmountFlag(String amountFlag) {
		this.amountFlag = amountFlag;
	}
	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end*/

}
