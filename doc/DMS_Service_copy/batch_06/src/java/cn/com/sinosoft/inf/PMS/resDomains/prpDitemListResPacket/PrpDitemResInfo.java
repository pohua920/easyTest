package cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String ITEMCODE="";
	private String ITEMTYPE="";
	private String ITEMCNAME="";
	private String ITEMTNAME="";
	private String ITEMENAME="";
	private String CREATORCODE="";
	private String CREATETIME="";
	private String UPDATERCODE="";
	private String UPDATETIME="";
	private String VALIDDATE="";
	private String INVALIDDATE="";
	private String VALIDIND="";
	private String TCOL1="";
	private String TCOL2="";
	private String TCOL3="";
	private String REMARK="";
	private String FLAG="";
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	public String getITEMCODE() {
		return ITEMCODE;
	}
	public void setITEMCODE(String itemcode) {
		ITEMCODE = itemcode;
	}
	public String getITEMTYPE() {
		return ITEMTYPE;
	}
	public void setITEMTYPE(String itemtype) {
		ITEMTYPE = itemtype;
	}
	public String getITEMCNAME() {
		return ITEMCNAME;
	}
	public void setITEMCNAME(String itemcname) {
		ITEMCNAME = itemcname;
	}
	public String getITEMTNAME() {
		return ITEMTNAME;
	}
	public void setITEMTNAME(String itemtname) {
		ITEMTNAME = itemtname;
	}
	public String getITEMENAME() {
		return ITEMENAME;
	}
	public void setITEMENAME(String itemename) {
		ITEMENAME = itemename;
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
	public String getINVALIDDATE() {
		return INVALIDDATE;
	}
	public void setINVALIDDATE(String invaliddate) {
		INVALIDDATE = invaliddate;
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
