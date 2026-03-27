package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDcodeResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPE="";
	private String CODECODE="";
	private String CODECNAME="";
	private String CODEENAME="";
	private String OLDCODETYPE="";
	private String OLDCODECODE="";
	private String NEWCODECODE="";
	private String FLAG="";
	private String VALIDSTATUS="";

	public String getCODETYPE() {
		return CODETYPE;
	}

	public void setCODETYPE(String CODETYPE) {
		this.CODETYPE = CODETYPE;
	}

	public String getCODECODE() {
		return CODECODE;
	}

	public void setCODECODE(String CODECODE) {
		this.CODECODE = CODECODE;
	}

	public String getCODECNAME() {
		return CODECNAME;
	}

	public void setCODECNAME(String CODECNAME) {
		this.CODECNAME = CODECNAME;
	}

	public String getCODEENAME() {
		return CODEENAME;
	}

	public void setCODEENAME(String CODEENAME) {
		this.CODEENAME = CODEENAME;
	}

	public String getNEWCODECODE() {
		return NEWCODECODE;
	}

	public void setNEWCODECODE(String NEWCODECODE) {
		this.NEWCODECODE = NEWCODECODE;
	}

	public String getFLAG() {
		return FLAG;
	}

	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}

	public String getVALIDSTATUS() {
		return VALIDSTATUS;
	}

	public void setVALIDSTATUS(String vALIDSTATUS) {
		VALIDSTATUS = vALIDSTATUS;
	}

	public String getOLDCODETYPE() {
		return OLDCODETYPE;
	}

	public void setOLDCODETYPE(String oLDCODETYPE) {
		OLDCODETYPE = oLDCODETYPE;
	}

	public String getOLDCODECODE() {
		return OLDCODECODE;
	}

	public void setOLDCODECODE(String oLDCODECODE) {
		OLDCODECODE = oLDCODECODE;
	}

}
