package cn.com.sinosoft.saa.vo;

import java.io.Serializable;

public class Code implements Serializable {
	
	private String code;
	private String name;
	private String upperCode;
	private String factoryType;
	private String factoryTypeName;
	private String validFalg;
	private String newCodeCode;
	
	public String getNewCodeCode() {
		return newCodeCode;
	}

	public void setNewCodeCode(String newCodeCode) {
		this.newCodeCode = newCodeCode;
	}

	public String getUpperCode() {
	    return upperCode;
	}

	public void setUpperCode(String upperCode) {
	    this.upperCode = upperCode;
	}

	public String getValidFalg() {
		return validFalg;
	}

	public void setValidFalg(String validFalg) {
		this.validFalg = validFalg;
	}

	public Code() {
	}
	
	public Code(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getFactoryType() {
		return factoryType;
	}

	public void setFactoryType(String factoryType) {
		this.factoryType = factoryType;
	}

	public String getFactoryTypeName() {
		return factoryTypeName;
	}

	public void setFactoryTypeName(String factoryTypeName) {
		this.factoryTypeName = factoryTypeName;
	}
	
}

