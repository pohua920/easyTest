package cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDcompanyResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private String COMCODE="";
	private String COMCNAME="";
	private String COMENAME="";
	private String ADDRESSCNAME="";
	private String ADDRESSENAME="";
	private String POSTCODE="";
	private String PHONENUMBER="";
	private String FAXNUMBER="";
	private String UPPERCOMCODE="";
	private String INSURERNAME="";
	private String COMTYPE="";
	private String MANAGER="";
	private String ACCOUNTANT="";
	private String REMARK="";
	private String NEWCOMCODE="";
//	private String PRINTCOMCNAME="";
//	private String PRINTCOMENAME="";
//	private String PRINTADDRESSCNAME="";
//	private String PRINTADDRESSENAME="";
//	private String PRINTPOSTCODE="";
	private String COMKIND=""; //新增加的字段start...
	private String UPDATEFLAG=""; 
	private String UPDATEDATE="";
	private String OPERATORCOMCODE="";//新增加的字段end...
	private String ACNTUNIT="";
	private String ARTICLECODE="";
	private String COMFLAG="";
	private String CENTERFLAG="";
	private String BRANCHTYPE="";
	private String COMLEVEL="";
	private String VALIDSTATUS="";
	private String FLAG="";
//	private String GRADETEMPLID = "";
	private String GRADE = "";
//	private String PRINTWSURL = "";
	
	

	public String getGRADE() {
		return GRADE;
	}
	public void setGRADE(String grade) {
		GRADE = grade;
	}
//	public String getPRINTWSURL() {
//		return PRINTWSURL;
//	}
//	public void setPRINTWSURL(String printwsurl) {
//		PRINTWSURL = printwsurl;
//	}
//	public String getGRADETEMPLID() {
//		return GRADETEMPLID;
//	}
//	public void setGRADETEMPLID(String gradetemplid) {
//		GRADETEMPLID = gradetemplid;
//	}
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
	public String getCOMFLAG() {
		return COMFLAG;
	}
	public void setCOMFLAG(String comflag) {
		COMFLAG = comflag;
	}
	public String getCENTERFLAG() {
		return CENTERFLAG;
	}
	public void setCENTERFLAG(String centerflag) {
		CENTERFLAG = centerflag;
	}
	public String getBRANCHTYPE() {
		return BRANCHTYPE;
	}
	public void setBRANCHTYPE(String branchtype) {
		BRANCHTYPE = branchtype;
	}
	public String getCOMLEVEL() {
		return COMLEVEL;
	}
	public void setCOMLEVEL(String comlevel) {
		COMLEVEL = comlevel;
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
	public String getCOMCNAME() {
		return COMCNAME;
	}
	public void setCOMCNAME(String COMCNAME) {
		this.COMCNAME = COMCNAME;
	}
	public String getCOMENAME() {
		return COMENAME;
	}
	public void setCOMENAME(String COMENAME) {
		this.COMENAME = COMENAME;
	}
	public String getADDRESSCNAME() {
		return ADDRESSCNAME;
	}
	public void setADDRESSCNAME(String ADDRESSCNAME) {
		this.ADDRESSCNAME = ADDRESSCNAME;
	}
	public String getADDRESSENAME() {
		return ADDRESSENAME;
	}
	public void setADDRESSENAME(String ADDRESSENAME) {
		this.ADDRESSENAME = ADDRESSENAME;
	}
	public String getPOSTCODE() {
		return POSTCODE;
	}
	public void setPOSTCODE(String POSTCODE) {
		this.POSTCODE = POSTCODE;
	}
	public String getPHONENUMBER() {
		return PHONENUMBER;
	}
	public void setPHONENUMBER(String PHONENUMBER) {
		this.PHONENUMBER = PHONENUMBER;
	}
	public String getUPPERCOMCODE() {
		return UPPERCOMCODE;
	}
	public void setUPPERCOMCODE(String UPPERCOMCODE) {
		this.UPPERCOMCODE = UPPERCOMCODE;
	}
	public String getINSURERNAME() {
		return INSURERNAME;
	}
	public void setINSURERNAME(String INSURERNAME) {
		this.INSURERNAME = INSURERNAME;
	}
	public String getCOMTYPE() {
		return COMTYPE;
	}
	public void setCOMTYPE(String COMTYPE) {
		this.COMTYPE = COMTYPE;
	}
	public String getMANAGER() {
		return MANAGER;
	}
	public void setMANAGER(String MANAGER) {
		this.MANAGER = MANAGER;
	}
	public String getACCOUNTANT() {
		return ACCOUNTANT;
	}
	public void setACCOUNTANT(String ACCOUNTANT) {
		this.ACCOUNTANT = ACCOUNTANT;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}
	public String getNEWCOMCODE() {
		return NEWCOMCODE;
	}
	public void setNEWCOMCODE(String NEWCOMCODE) {
		this.NEWCOMCODE = NEWCOMCODE;
	}
//	public String getPRINTCOMCNAME() {
//		return PRINTCOMCNAME;
//	}
//	public void setPRINTCOMCNAME(String PRINTCOMCNAME) {
//		this.PRINTCOMCNAME = PRINTCOMCNAME;
//	}
//	public String getPRINTCOMENAME() {
//		return PRINTCOMENAME;
//	}
//	public void setPRINTCOMENAME(String PRINTCOMENAME) {
//		this.PRINTCOMENAME = PRINTCOMENAME;
//	}
//	public String getPRINTADDRESSCNAME() {
//		return PRINTADDRESSCNAME;
//	}
//	public void setPRINTADDRESSCNAME(String PRINTADDRESSCNAME) {
//		this.PRINTADDRESSCNAME = PRINTADDRESSCNAME;
//	}
//	public String getPRINTADDRESSENAME() {
//		return PRINTADDRESSENAME;
//	}
//	public void setPRINTADDRESSENAME(String PRINTADDRESSENAME) {
//		this.PRINTADDRESSENAME = PRINTADDRESSENAME;
//	}
//	public String getPRINTPOSTCODE() {
//		return PRINTPOSTCODE;
//	}
//	public void setPRINTPOSTCODE(String PRINTPOSTCODE) {
//		this.PRINTPOSTCODE = PRINTPOSTCODE;
//	}
	public String getACNTUNIT() {
		return ACNTUNIT;
	}
	public void setACNTUNIT(String ACNTUNIT) {
		this.ACNTUNIT = ACNTUNIT;
	}
	public String getARTICLECODE() {
		return ARTICLECODE;
	}
	public void setARTICLECODE(String ARTICLECODE) {
		this.ARTICLECODE = ARTICLECODE;
	}
	public String getCOMKIND() {
		return COMKIND;
	}
	public void setCOMKIND(String comkind) {
		COMKIND = comkind;
	}
	public String getUPDATEFLAG() {
		return UPDATEFLAG;
	}
	public void setUPDATEFLAG(String updateflag) {
		UPDATEFLAG = updateflag;
	}
	public String getUPDATEDATE() {
		return UPDATEDATE;
	}
	public void setUPDATEDATE(String updatedate) {
		UPDATEDATE = updatedate;
	}
	public String getOPERATORCOMCODE() {
		return OPERATORCOMCODE;
	}
	public void setOPERATORCOMCODE(String operatorcomcode) {
		OPERATORCOMCODE = operatorcomcode;
	}
}
