package com.tlg.aps.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */

@XmlSeeAlso({ChubbClaimXmlHeaderVo.class,ChubbClaimXmlItem.class})
@XmlRootElement(name = "ROOT")
public class ChubbClaimXmlVo {

	private ChubbClaimXmlHeaderVo chubbClaimXmlHeaderVo;
	private List<ChubbClaimXmlItem> item = new ArrayList<ChubbClaimXmlItem>();

	@XmlElement(name = "Header")
	public ChubbClaimXmlHeaderVo getChubbClaimXmlHeaderVo() {
		return chubbClaimXmlHeaderVo;
	}

	public void setChubbClaimXmlHeaderVo(
			ChubbClaimXmlHeaderVo chubbClaimXmlHeaderVo) {
		this.chubbClaimXmlHeaderVo = chubbClaimXmlHeaderVo;
	}

	@XmlElement(name = "Item")
	public List<ChubbClaimXmlItem> getItem() {
		return item;
	}

	public void setItem(List<ChubbClaimXmlItem> item) {
		this.item = item;
	}

}
