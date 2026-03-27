package com.sinosoft.dmsdriver.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="PrpDstartPlace")
public class PrpDstartPlace {
	/**属性代码*/
	private String codeCode;
	/**属性港口名称*/
	private String portName;
	/**属性国家名称*/
	private String countries;
	/**属性标志*/
	private String flag;
	/**属性有效状态*/
	private String validStatus;
	
	@Id
	@Column(name = "CODECODE")
	public String getCodeCode() {
		return codeCode;
	}
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}
	
	@Column(name = "PORTNAME")
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	
	@Column(name = "COUNTRIES")
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
}
