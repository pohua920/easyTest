package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReinsInwardPolicyInsVo {

	private String poname;
	private String uwins;
	private String poins;
	private String inscode;
	private String actins;
	private String oriCurrAmount;
	private String oriCurrInwardAmount;
	private String undertakingRate;

	public String getPoname() {
		return poname;
	}

	public void setPoname(String poname) {
		this.poname = poname;
	}

	public String getUwins() {
		return uwins;
	}

	public void setUwins(String uwins) {
		this.uwins = uwins;
	}

	public String getPoins() {
		return poins;
	}

	public void setPoins(String poins) {
		this.poins = poins;
	}

	public String getInscode() {
		return inscode;
	}

	public void setInscode(String inscode) {
		this.inscode = inscode;
	}

	public String getActins() {
		return actins;
	}

	public void setActins(String actins) {
		this.actins = actins;
	}

	public String getOriCurrAmount() {
		return oriCurrAmount;
	}

	public void setOriCurrAmount(String oriCurrAmount) {
		this.oriCurrAmount = oriCurrAmount;
	}

	public String getOriCurrInwardAmount() {
		return oriCurrInwardAmount;
	}

	public void setOriCurrInwardAmount(String oriCurrInwardAmount) {
		this.oriCurrInwardAmount = oriCurrInwardAmount;
	}

	public String getUndertakingRate() {
		return undertakingRate;
	}

	public void setUndertakingRate(String undertakingRate) {
		this.undertakingRate = undertakingRate;
	}

}
