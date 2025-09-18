package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BlacklistTypeVo {
	
	private String blacklistType;
	private String blacklistDesc;
	
	public String getBlacklistType() {
		return blacklistType;
	}
	public void setBlacklistType(String blacklistType) {
		this.blacklistType = blacklistType;
	}
	public String getBlacklistDesc() {
		return blacklistDesc;
	}
	public void setBlacklistDesc(String blacklistDesc) {
		this.blacklistDesc = blacklistDesc;
	}
}
