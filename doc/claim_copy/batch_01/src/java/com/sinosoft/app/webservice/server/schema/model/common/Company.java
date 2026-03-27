package com.sinosoft.app.webservice.server.schema.model.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="company")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {
	private String comCode;
	private String comCName;
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComCName() {
		return comCName;
	}
	public void setComCName(String comCName) {
		this.comCName = comCName;
	}
	
}
