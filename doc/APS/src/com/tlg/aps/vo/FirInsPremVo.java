package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 	RISKCODE	險種代碼，必填。
	KINDCODE	險別代碼，必填。
	PARA_TYPE	參數類型，依險別各自定義。
	PARA_01	參數1，依險別各自定義。
	PARA_02	參數2，依險別各自定義。
	PARA_03	參數3，依險別各自定義。
	PARA_04	參數4，依險別各自定義。
	PARA_05	參數5，依險別各自定義。
	PARA_06	參數6，依險別各自定義。

 * 
 * @author bi086
 *
 */
@XmlRootElement
public class FirInsPremVo {

	private String riskcode;
	private String kindcode;
	private String paraType;
	private String para01;
	private String para02;
	private String para03;
	private String para04;
	private String para05;
	private String para06;

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getKindcode() {
		return kindcode;
	}

	public void setKindcode(String kindcode) {
		this.kindcode = kindcode;
	}

	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

	public String getPara01() {
		return para01;
	}

	public void setPara01(String para01) {
		this.para01 = para01;
	}

	public String getPara02() {
		return para02;
	}

	public void setPara02(String para02) {
		this.para02 = para02;
	}

	public String getPara03() {
		return para03;
	}

	public void setPara03(String para03) {
		this.para03 = para03;
	}

	public String getPara04() {
		return para04;
	}

	public void setPara04(String para04) {
		this.para04 = para04;
	}

	public String getPara05() {
		return para05;
	}

	public void setPara05(String para05) {
		this.para05 = para05;
	}

	public String getPara06() {
		return para06;
	}

	public void setPara06(String para06) {
		this.para06 = para06;
	}

}
