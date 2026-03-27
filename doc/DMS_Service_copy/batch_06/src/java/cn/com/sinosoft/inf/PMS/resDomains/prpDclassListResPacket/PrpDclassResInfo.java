package cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDclassResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String CLASSCODE="";
	private String CLASSCNAME="";
	private String CLASSSCNAME="";
	private String CLASSTNAME="";
	private String CLASSENAME="";
	private String CLASSSENAME="";
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

	public String getCLASSCODE() {
		return CLASSCODE;
	}
	public void setCLASSCODE(String classcode) {
		CLASSCODE = classcode;
	}
	public String getCLASSCNAME() {
		return CLASSCNAME;
	}
	public void setCLASSCNAME(String classcname) {
		CLASSCNAME = classcname;
	}
	public String getCLASSSCNAME() {
		return CLASSSCNAME;
	}
	public void setCLASSSCNAME(String classscname) {
		CLASSSCNAME = classscname;
	}
	public String getCLASSTNAME() {
		return CLASSTNAME;
	}
	public void setCLASSTNAME(String classtname) {
		CLASSTNAME = classtname;
	}
	public String getCLASSENAME() {
		return CLASSENAME;
	}
	public void setCLASSENAME(String classename) {
		CLASSENAME = classename;
	}
	public String getCLASSSENAME() {
		return CLASSSENAME;
	}
	public void setCLASSSENAME(String classsename) {
		CLASSSENAME = classsename;
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
