package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDagentInfo implements SchemaNode {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String AGENTCODE = "";
	private String AGENTNAME = "";
	private String ADDRESSNAME = "";
	private String POSTCODE = "";
	private String AGENTTYPE = "";
	private String PERMITNO = "";
	private String LINKERNAME = "";
	private String BARGAINDATE = "";
	private String PHONENUMBER = "";
	private String FAXNUMBER = "";
	private String COMCODE = "";
	private String UPPERAGENTCODE = "";
	private String NEWAGENTCODE = "";
	private String AGENTNATURE = "";
	private String ARTICLECODE = "";

	public String getAGENTCODE() {
		return AGENTCODE;
	}

	public void setAGENTCODE(String AGENTCODE) {
		this.AGENTCODE = AGENTCODE;
	}

	public String getAGENTNAME() {
		return AGENTNAME;
	}

	public void setAGENTNAME(String AGENTNAME) {
		this.AGENTNAME = AGENTNAME;
	}

	public String getADDRESSNAME() {
		return ADDRESSNAME;
	}

	public void setADDRESSNAME(String ADDRESSNAME) {
		this.ADDRESSNAME = ADDRESSNAME;
	}

	public String getPOSTCODE() {
		return POSTCODE;
	}

	public void setPOSTCODE(String POSTCODE) {
		this.POSTCODE = POSTCODE;
	}

	public String getAGENTTYPE() {
		return AGENTTYPE;
	}

	public void setAGENTTYPE(String AGENTTYPE) {
		this.AGENTTYPE = AGENTTYPE;
	}

	public String getPERMITNO() {
		return PERMITNO;
	}

	public void setPERMITNO(String PERMITNO) {
		this.PERMITNO = PERMITNO;
	}

	public String getLINKERNAME() {
		return LINKERNAME;
	}

	public void setLINKERNAME(String LINKERNAME) {
		this.LINKERNAME = LINKERNAME;
	}

	public String getBARGAINDATE() {
		return BARGAINDATE;
	}

	public void setBARGAINDATE(String BARGAINDATE) {
		this.BARGAINDATE = BARGAINDATE;
	}

	public String getPHONENUMBER() {
		return PHONENUMBER;
	}

	public void setPHONENUMBER(String PHONENUMBER) {
		this.PHONENUMBER = PHONENUMBER;
	}

	public String getFAXNUMBER() {
		return FAXNUMBER;
	}

	public void setFAXNUMBER(String FAXNUMBER) {
		this.FAXNUMBER = FAXNUMBER;
	}

	public String getCOMCODE() {
		return COMCODE;
	}

	public void setCOMCODE(String COMCODE) {
		this.COMCODE = COMCODE;
	}

	public String getUPPERAGENTCODE() {
		return UPPERAGENTCODE;
	}

	public void setUPPERAGENTCODE(String UPPERAGENTCODE) {
		this.UPPERAGENTCODE = UPPERAGENTCODE;
	}

	public String getNEWAGENTCODE() {
		return NEWAGENTCODE;
	}

	public void setNEWAGENTCODE(String NEWAGENTCODE) {
		this.NEWAGENTCODE = NEWAGENTCODE;
	}

	public String getAGENTNATURE() {
		return AGENTNATURE;
	}

	public void setAGENTNATURE(String AGENTNATURE) {
		this.AGENTNATURE = AGENTNATURE;
	}

	public String getARTICLECODE() {
		return ARTICLECODE;
	}

	public void setARTICLECODE(String ARTICLECODE) {
		this.ARTICLECODE = ARTICLECODE;
	}

}
