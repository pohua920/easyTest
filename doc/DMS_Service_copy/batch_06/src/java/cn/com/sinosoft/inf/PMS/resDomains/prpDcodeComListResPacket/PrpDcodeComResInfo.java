package cn.com.sinosoft.inf.PMS.resDomains.prpDcodeComListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDcodeComResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String COMCODE ="";
	private String CODETYPE ="";
	private String CODECODE ="";
	private String CODECNAME ="";
	private String CODEENAME ="";
	private String NEWCODECODE ="";
	private String VALIDSTATUS ="";
	private String FLAG ="";
	private String CODEVALUE ="";
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	public String getCOMCODE() {
		return COMCODE;
	}
	public void setCOMCODE(String comcode) {
		COMCODE = comcode;
	}
	public String getCODETYPE() {
		return CODETYPE;
	}
	public void setCODETYPE(String codetype) {
		CODETYPE = codetype;
	}
	public String getCODECODE() {
		return CODECODE;
	}
	public void setCODECODE(String codecode) {
		CODECODE = codecode;
	}
	public String getCODECNAME() {
		return CODECNAME;
	}
	public void setCODECNAME(String codecname) {
		CODECNAME = codecname;
	}
	public String getCODEENAME() {
		return CODEENAME;
	}
	public void setCODEENAME(String codeename) {
		CODEENAME = codeename;
	}
	public String getNEWCODECODE() {
		return NEWCODECODE;
	}
	public void setNEWCODECODE(String newcodecode) {
		NEWCODECODE = newcodecode;
	}
	public String getVALIDSTATUS() {
		return VALIDSTATUS;
	}
	public void setVALIDSTATUS(String validstatus) {
		VALIDSTATUS = validstatus;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String flag) {
		FLAG = flag;
	}
	public String getCODEVALUE() {
		return CODEVALUE;
	}
	public void setCODEVALUE(String codevalue) {
		CODEVALUE = codevalue;
	}
	
}
