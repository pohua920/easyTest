package cn.com.sinosoft.inf.PMS.resDomains.prpDengageListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDengageResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String ENGAGECODE="";
	private String ENGAGECNAME="";
	private String ENGAGEENAME="";
	private String LANGUAGE="";
	private String ENGAGEDESC="";
	private String CREATORCODE="";
	private String CREATETIME="";
	private String UPDATERCODE="";
	private String UPDATETIME="";
	private String VALIDDATE="";
	private String INVAIDDATE="";
	private String VALIDIND="";
	private String TCOL1="";
	private String TCOL2="";
	private String TCOL3="";
	private String REMARK="";
	private String FLAG="";
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	public String getENGAGECODE() {
		return ENGAGECODE;
	}
	public void setENGAGECODE(String engagecode) {
		ENGAGECODE = engagecode;
	}
	public String getENGAGECNAME() {
		return ENGAGECNAME;
	}
	public void setENGAGECNAME(String engagecname) {
		ENGAGECNAME = engagecname;
	}
	public String getENGAGEENAME() {
		return ENGAGEENAME;
	}
	public void setENGAGEENAME(String engageename) {
		ENGAGEENAME = engageename;
	}
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	public void setLANGUAGE(String language) {
		LANGUAGE = language;
	}
	public String getENGAGEDESC() {
		return ENGAGEDESC;
	}
	public void setENGAGEDESC(String engagedesc) {
		ENGAGEDESC = engagedesc;
	}
	public String getCREATORCODE() {
		return CREATORCODE;
	}
	public void setCREATORCODE(String creatorcode) {
		CREATORCODE = creatorcode;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String createtime) {
		CREATETIME = createtime;
	}
	public String getUPDATERCODE() {
		return UPDATERCODE;
	}
	public void setUPDATERCODE(String updatercode) {
		UPDATERCODE = updatercode;
	}
	public String getUPDATETIME() {
		return UPDATETIME;
	}
	public void setUPDATETIME(String updatetime) {
		UPDATETIME = updatetime;
	}
	public String getVALIDDATE() {
		return VALIDDATE;
	}
	public void setVALIDDATE(String validdate) {
		VALIDDATE = validdate;
	}
	public String getINVAIDDATE() {
		return INVAIDDATE;
	}
	public void setINVAIDDATE(String invaiddate) {
		INVAIDDATE = invaiddate;
	}
	public String getVALIDIND() {
		return VALIDIND;
	}
	public void setVALIDIND(String validind) {
		VALIDIND = validind;
	}
	public String getTCOL1() {
		return TCOL1;
	}
	public void setTCOL1(String tcol1) {
		TCOL1 = tcol1;
	}
	public String getTCOL2() {
		return TCOL2;
	}
	public void setTCOL2(String tcol2) {
		TCOL2 = tcol2;
	}
	public String getTCOL3() {
		return TCOL3;
	}
	public void setTCOL3(String tcol3) {
		TCOL3 = tcol3;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String remark) {
		REMARK = remark;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String flag) {
		FLAG = flag;
	}
	
}
