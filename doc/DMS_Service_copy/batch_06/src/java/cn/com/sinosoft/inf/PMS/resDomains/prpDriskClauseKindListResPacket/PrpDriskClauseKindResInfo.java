package cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskClauseKindResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String RISKCODE="";
	private String CLAUSEKINDID="";
	private String CLAUSECODE="";
	private String KINDCODE="";
	private String CLAUSECLASSCODE="";
	private String KINDCLASSCODE="";
//	private String CLAUSEATTRIBUTE="";
	private String KINDATTRIBUTE="";
	private String TYPE="";
	private String LOWEROPERATOR="";
	private String UPPEROPERATOR="";
	private String UPPER="";
	private String LOWER="";
	private String VALUE="";
	private String CALCULATEFLAG="";
//	private String OFFSETFLAG="";
	private String CREATORCODE="";
	private String CREATETIME="";
	private String UPDATERCODE="";
	private String UPDATETIME="";
	private String VALIDDATE="";
	private String INVAIDDATE="";
	private String VALIDIND="";
//	private String DOCUMENTNUMBER="";
	private String CLAUSECONTENTNUMBER="";
	private String TCOL1="";
	private String TCOL2="";
	private String TCOL3="";
	private String REMARK="";
	private String FLAG="";
	private String RISKCNAME="";//新增加的字段，上边注释的为删除的字段start...
	private String CLACULATEFAG="";
	private String CLAIMTYPE="";
	private String ENDUPDATERCODE="";
	private String OPERATETIMEFORHIS="";//新增加的字段，上边注释的为删除的字段end...
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getCLAUSEKINDID() {
		return CLAUSEKINDID;
	}
	public void setCLAUSEKINDID(String clausekindid) {
		CLAUSEKINDID = clausekindid;
	}
	public String getCLAUSECODE() {
		return CLAUSECODE;
	}
	public void setCLAUSECODE(String clausecode) {
		CLAUSECODE = clausecode;
	}
	public String getKINDCODE() {
		return KINDCODE;
	}
	public void setKINDCODE(String kindcode) {
		KINDCODE = kindcode;
	}
	public String getCLAUSECLASSCODE() {
		return CLAUSECLASSCODE;
	}
	public void setCLAUSECLASSCODE(String clauseclasscode) {
		CLAUSECLASSCODE = clauseclasscode;
	}
	public String getKINDCLASSCODE() {
		return KINDCLASSCODE;
	}
	public void setKINDCLASSCODE(String kindclasscode) {
		KINDCLASSCODE = kindclasscode;
	}
//	public String getCLAUSEATTRIBUTE() {
//		return CLAUSEATTRIBUTE;
//	}
//	public void setCLAUSEATTRIBUTE(String clauseattribute) {
//		CLAUSEATTRIBUTE = clauseattribute;
//	}
	public String getKINDATTRIBUTE() {
		return KINDATTRIBUTE;
	}
	public void setKINDATTRIBUTE(String kindattribute) {
		KINDATTRIBUTE = kindattribute;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String type) {
		TYPE = type;
	}
	public String getLOWEROPERATOR() {
		return LOWEROPERATOR;
	}
	public void setLOWEROPERATOR(String loweroperator) {
		LOWEROPERATOR = loweroperator;
	}
	public String getUPPEROPERATOR() {
		return UPPEROPERATOR;
	}
	public void setUPPEROPERATOR(String upperoperator) {
		UPPEROPERATOR = upperoperator;
	}
	public String getUPPER() {
		return UPPER;
	}
	public void setUPPER(String upper) {
		UPPER = upper;
	}
	public String getLOWER() {
		return LOWER;
	}
	public void setLOWER(String lower) {
		LOWER = lower;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String value) {
		VALUE = value;
	}
	public String getCALCULATEFLAG() {
		return CALCULATEFLAG;
	}
	public void setCALCULATEFLAG(String calculateflag) {
		CALCULATEFLAG = calculateflag;
	}
//	public String getOFFSETFLAG() {
//		return OFFSETFLAG;
//	}
//	public void setOFFSETFLAG(String offsetflag) {
//		OFFSETFLAG = offsetflag;
//	}
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
//	public String getDOCUMENTNUMBER() {
//		return DOCUMENTNUMBER;
//	}
//	public void setDOCUMENTNUMBER(String documentnumber) {
//		DOCUMENTNUMBER = documentnumber;
//	}
	public String getCLAUSECONTENTNUMBER() {
		return CLAUSECONTENTNUMBER;
	}
	public void setCLAUSECONTENTNUMBER(String clausecontentnumber) {
		CLAUSECONTENTNUMBER = clausecontentnumber;
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
	public String getRISKCNAME() {
		return RISKCNAME;
	}
	public void setRISKCNAME(String riskcname) {
		RISKCNAME = riskcname;
	}
	public String getCLACULATEFAG() {
		return CLACULATEFAG;
	}
	public void setCLACULATEFAG(String claculatefag) {
		CLACULATEFAG = claculatefag;
	}
	public String getCLAIMTYPE() {
		return CLAIMTYPE;
	}
	public void setCLAIMTYPE(String claimtype) {
		CLAIMTYPE = claimtype;
	}
	public String getENDUPDATERCODE() {
		return ENDUPDATERCODE;
	}
	public void setENDUPDATERCODE(String endupdatercode) {
		ENDUPDATERCODE = endupdatercode;
	}
	public String getOPERATETIMEFORHIS() {
		return OPERATETIMEFORHIS;
	}
	public void setOPERATETIMEFORHIS(String operatetimeforhis) {
		OPERATETIMEFORHIS = operatetimeforhis;
	}
	
}
