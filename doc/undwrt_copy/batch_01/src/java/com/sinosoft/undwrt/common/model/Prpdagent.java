package com.sinosoft.undwrt.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author David
 *
 */
@Entity
@Table(name="PRPDAGENT")
public class Prpdagent implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  Ù–‘agentCode */
	private String agentCode;

	@Id
	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	
	
	
}
