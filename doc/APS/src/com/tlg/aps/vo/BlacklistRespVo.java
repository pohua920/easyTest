package com.tlg.aps.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="blacklistResponseVo")
public class BlacklistRespVo {
	
	private String result;
	private List<BlacklistTypeVo> noPassTypeList;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<BlacklistTypeVo> getNoPassTypeList() {
		return noPassTypeList;
	}
	public void setNoPassTypeList(List<BlacklistTypeVo> noPassTypeList) {
		this.noPassTypeList = noPassTypeList;
	}
}
