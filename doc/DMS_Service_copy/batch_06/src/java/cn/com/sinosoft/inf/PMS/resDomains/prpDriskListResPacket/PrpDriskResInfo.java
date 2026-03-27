package cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskResInfo implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String RISKCODE="";
	private String RISKCNAME="";
	private String RISKTNAME="";
	private String RISKENAME="";
	private String RISKATTRIBUTE="";
	private String SALEAREALEVEL="";
	private String SALEAREACODE="";
	private String MATERIALCONTXT="";
	private String CLASSCODE="";
	private String FRAMECODE="";
//	private String PROJECT="";
//	private String DOCUMENTIND="";
//	private String RENEWIND="";
//	private String AUTORENEWIND="";
//	private String COUNTERACTIND="";
//	private String REINSININD="";
//	private String GROUPIND="";
//	private String HESITATEBACKDAYS="";
//	private String DECLARATIONIND="";
	private String PLANIND="";
//	private String LOWESTPREMIUMIND="";
//	private String LOWESTPREMCURRENCY="";
//	private String LOWESTPREMIUM	="";
//	private String AUTORELATEDCLAUSE="";
//	private String OPTIONALCLAUSE="";
//	private String ESTIMATELOSSINDLEVEL="";
//	private String COMPENSATELEVEL="";
	private String ACCOUNTLEVEL="";
	private String REINSLEVEL="";
	private String MANAGEMENTLEVEL	="";
	private String STATLEVEL="";
//	private String DOCUMENTNUMBER="";
//	private String CONTENTNUMBER="";
//	private String DYNAMICIND="";
//	private String TEMPLATERISKCODE="";
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
	private String RISKSCNAME=""; //新增加的字段，上面注释的为删除的字段start...
	private String RISKSENAME="";
	private String POLICYPROCESSFLAG="";
	private String REQUIREDFLAG="";
	private String RATEUNIT="";
	private String SHORTRATEFLAG="";
	private String CLASSFLAG="";
	private String RISKFLAG="";
	private String ENDUPDATERCODE="";
	private String PROJECTCODE="";
	private String OPERATETIMEFORHIS="";//新增加的字段，上面注释的为删除的字段end...
	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getRISKCNAME() {
		return RISKCNAME;
	}
	public void setRISKCNAME(String riskcname) {
		RISKCNAME = riskcname;
	}
	public String getRISKTNAME() {
		return RISKTNAME;
	}
	public void setRISKTNAME(String risktname) {
		RISKTNAME = risktname;
	}
	public String getRISKENAME() {
		return RISKENAME;
	}
	public void setRISKENAME(String riskename) {
		RISKENAME = riskename;
	}
	public String getRISKATTRIBUTE() {
		return RISKATTRIBUTE;
	}
	public void setRISKATTRIBUTE(String riskattribute) {
		RISKATTRIBUTE = riskattribute;
	}
	public String getSALEAREALEVEL() {
		return SALEAREALEVEL;
	}
	public void setSALEAREALEVEL(String salearealevel) {
		SALEAREALEVEL = salearealevel;
	}
	public String getSALEAREACODE() {
		return SALEAREACODE;
	}
	public void setSALEAREACODE(String saleareacode) {
		SALEAREACODE = saleareacode;
	}
	public String getMATERIALCONTXT() {
		return MATERIALCONTXT;
	}
	public void setMATERIALCONTXT(String materialcontxt) {
		MATERIALCONTXT = materialcontxt;
	}
	public String getCLASSCODE() {
		return CLASSCODE;
	}
	public void setCLASSCODE(String classcode) {
		CLASSCODE = classcode;
	}
	public String getFRAMECODE() {
		return FRAMECODE;
	}
	public void setFRAMECODE(String framecode) {
		FRAMECODE = framecode;
	}
//	public String getPROJECT() {
//		return PROJECT;
//	}
//	public void setPROJECT(String project) {
//		PROJECT = project;
//	}
//	public String getDOCUMENTIND() {
//		return DOCUMENTIND;
//	}
//	public void setDOCUMENTIND(String documentind) {
//		DOCUMENTIND = documentind;
//	}
//	public String getRENEWIND() {
//		return RENEWIND;
//	}
//	public void setRENEWIND(String renewind) {
//		RENEWIND = renewind;
//	}
//	public String getAUTORENEWIND() {
//		return AUTORENEWIND;
//	}
//	public void setAUTORENEWIND(String autorenewind) {
//		AUTORENEWIND = autorenewind;
//	}
//	public String getCOUNTERACTIND() {
//		return COUNTERACTIND;
//	}
//	public void setCOUNTERACTIND(String counteractind) {
//		COUNTERACTIND = counteractind;
//	}
//	public String getREINSININD() {
//		return REINSININD;
//	}
//	public void setREINSININD(String reinsinind) {
//		REINSININD = reinsinind;
//	}
//	public String getGROUPIND() {
//		return GROUPIND;
//	}
//	public void setGROUPIND(String groupind) {
//		GROUPIND = groupind;
//	}
//	public String getHESITATEBACKDAYS() {
//		return HESITATEBACKDAYS;
//	}
//	public void setHESITATEBACKDAYS(String hesitatebackdays) {
//		HESITATEBACKDAYS = hesitatebackdays;
//	}
//	public String getDECLARATIONIND() {
//		return DECLARATIONIND;
//	}
//	public void setDECLARATIONIND(String declarationind) {
//		DECLARATIONIND = declarationind;
//	}
	public String getPLANIND() {
		return PLANIND;
	}
	public void setPLANIND(String planind) {
		PLANIND = planind;
	}
//	public String getLOWESTPREMIUMIND() {
//		return LOWESTPREMIUMIND;
//	}
//	public void setLOWESTPREMIUMIND(String lowestpremiumind) {
//		LOWESTPREMIUMIND = lowestpremiumind;
//	}
//	public String getLOWESTPREMCURRENCY() {
//		return LOWESTPREMCURRENCY;
//	}
//	public void setLOWESTPREMCURRENCY(String lowestpremcurrency) {
//		LOWESTPREMCURRENCY = lowestpremcurrency;
//	}
//	public String getLOWESTPREMIUM() {
//		return LOWESTPREMIUM;
//	}
//	public void setLOWESTPREMIUM(String lowestpremium) {
//		LOWESTPREMIUM = lowestpremium;
//	}
//	public String getAUTORELATEDCLAUSE() {
//		return AUTORELATEDCLAUSE;
//	}
//	public void setAUTORELATEDCLAUSE(String autorelatedclause) {
//		AUTORELATEDCLAUSE = autorelatedclause;
//	}
//	public String getOPTIONALCLAUSE() {
//		return OPTIONALCLAUSE;
//	}
//	public void setOPTIONALCLAUSE(String optionalclause) {
//		OPTIONALCLAUSE = optionalclause;
//	}
//	public String getESTIMATELOSSINDLEVEL() {
//		return ESTIMATELOSSINDLEVEL;
//	}
//	public void setESTIMATELOSSINDLEVEL(String estimatelossindlevel) {
//		ESTIMATELOSSINDLEVEL = estimatelossindlevel;
//	}
//	public String getCOMPENSATELEVEL() {
//		return COMPENSATELEVEL;
//	}
//	public void setCOMPENSATELEVEL(String compensatelevel) {
//		COMPENSATELEVEL = compensatelevel;
//	}
	public String getACCOUNTLEVEL() {
		return ACCOUNTLEVEL;
	}
	public void setACCOUNTLEVEL(String accountlevel) {
		ACCOUNTLEVEL = accountlevel;
	}
	public String getREINSLEVEL() {
		return REINSLEVEL;
	}
	public void setREINSLEVEL(String reinslevel) {
		REINSLEVEL = reinslevel;
	}
	public String getMANAGEMENTLEVEL() {
		return MANAGEMENTLEVEL;
	}
	public void setMANAGEMENTLEVEL(String managementlevel) {
		MANAGEMENTLEVEL = managementlevel;
	}
	public String getSTATLEVEL() {
		return STATLEVEL;
	}
	public void setSTATLEVEL(String statlevel) {
		STATLEVEL = statlevel;
	}
//	public String getDOCUMENTNUMBER() {
//		return DOCUMENTNUMBER;
//	}
//	public void setDOCUMENTNUMBER(String documentnumber) {
//		DOCUMENTNUMBER = documentnumber;
//	}
//	public String getCONTENTNUMBER() {
//		return CONTENTNUMBER;
//	}
//	public void setCONTENTNUMBER(String contentnumber) {
//		CONTENTNUMBER = contentnumber;
//	}
//	public String getDYNAMICIND() {
//		return DYNAMICIND;
//	}
//	public void setDYNAMICIND(String dynamicind) {
//		DYNAMICIND = dynamicind;
//	}
//	public String getTEMPLATERISKCODE() {
//		return TEMPLATERISKCODE;
//	}
//	public void setTEMPLATERISKCODE(String templateriskcode) {
//		TEMPLATERISKCODE = templateriskcode;
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
	public String getRISKSCNAME() {
		return RISKSCNAME;
	}
	public void setRISKSCNAME(String riskscname) {
		RISKSCNAME = riskscname;
	}
	public String getRISKSENAME() {
		return RISKSENAME;
	}
	public void setRISKSENAME(String risksename) {
		RISKSENAME = risksename;
	}
	public String getPOLICYPROCESSFLAG() {
		return POLICYPROCESSFLAG;
	}
	public void setPOLICYPROCESSFLAG(String policyprocessflag) {
		POLICYPROCESSFLAG = policyprocessflag;
	}
	public String getREQUIREDFLAG() {
		return REQUIREDFLAG;
	}
	public void setREQUIREDFLAG(String requiredflag) {
		REQUIREDFLAG = requiredflag;
	}
	public String getRATEUNIT() {
		return RATEUNIT;
	}
	public void setRATEUNIT(String rateunit) {
		RATEUNIT = rateunit;
	}
	public String getSHORTRATEFLAG() {
		return SHORTRATEFLAG;
	}
	public void setSHORTRATEFLAG(String shortrateflag) {
		SHORTRATEFLAG = shortrateflag;
	}
	public String getCLASSFLAG() {
		return CLASSFLAG;
	}
	public void setCLASSFLAG(String classflag) {
		CLASSFLAG = classflag;
	}
	public String getRISKFLAG() {
		return RISKFLAG;
	}
	public void setRISKFLAG(String riskflag) {
		RISKFLAG = riskflag;
	}
	public String getENDUPDATERCODE() {
		return ENDUPDATERCODE;
	}
	public void setENDUPDATERCODE(String endupdatercode) {
		ENDUPDATERCODE = endupdatercode;
	}
	public String getPROJECTCODE() {
		return PROJECTCODE;
	}
	public void setPROJECTCODE(String projectcode) {
		PROJECTCODE = projectcode;
	}
	public String getOPERATETIMEFORHIS() {
		return OPERATETIMEFORHIS;
	}
	public void setOPERATETIMEFORHIS(String operatetimeforhis) {
		OPERATETIMEFORHIS = operatetimeforhis;
	}
	
}
