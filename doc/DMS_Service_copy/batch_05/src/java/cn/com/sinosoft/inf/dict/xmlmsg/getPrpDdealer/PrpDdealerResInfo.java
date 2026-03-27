package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDdealerResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
 	private String   DEALERCODE="";

	private String   DEALERNAME="";

	private String   CUSTOMERCODE="";

	private String   ADDRESSNAME="";

	private String   POSTCODE="";

	private String   DEALERTYPE="";

	private String   CAPITAL="";

	private String  DEALERGRADE="";
 
	private String   CARTYPE="";

	private String   LINKERNAME="";

	private String   PHONENUMBER="";

	private String   FAXNUMBER="";

	private String   ARREARAGERATE="";

	private String   ARREARAGECOFF="";

	private String   COMCODE="";

	public String getDEALERCODE() {
		return DEALERCODE;
	}

	public void setDEALERCODE(String DEALERCODE) {
		this.DEALERCODE = DEALERCODE;
	}

	public String getDEALERNAME() {
		return DEALERNAME;
	}

	public void setDEALERNAME(String DEALERNAME) {
		this.DEALERNAME = DEALERNAME;
	}

	public String getCUSTOMERCODE() {
		return CUSTOMERCODE;
	}

	public void setCUSTOMERCODE(String CUSTOMERCODE) {
		this.CUSTOMERCODE = CUSTOMERCODE;
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

	public String getDEALERTYPE() {
		return DEALERTYPE;
	}

	public void setDEALERTYPE(String DEALERTYPE) {
		this.DEALERTYPE = DEALERTYPE;
	}

	public String getCAPITAL() {
		return CAPITAL;
	}

	public void setCAPITAL(String CAPITAL) {
		this.CAPITAL = CAPITAL;
	}

	public String getDEALERGRADE() {
		return DEALERGRADE;
	}

	public void setDEALERGRADE(String DEALERGRADE) {
		this.DEALERGRADE = DEALERGRADE;
	}

	public String getCARTYPE() {
		return CARTYPE;
	}

	public void setCARTYPE(String CARTYPE) {
		this.CARTYPE = CARTYPE;
	}

	public String getLINKERNAME() {
		return LINKERNAME;
	}

	public void setLINKERNAME(String LINKERNAME) {
		this.LINKERNAME = LINKERNAME;
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

	public String getARREARAGERATE() {
		return ARREARAGERATE;
	}

	public void setARREARAGERATE(String ARREARAGERATE) {
		this.ARREARAGERATE = ARREARAGERATE;
	}

	public String getARREARAGECOFF() {
		return ARREARAGECOFF;
	}

	public void setARREARAGECOFF(String ARREARAGECOFF) {
		this.ARREARAGECOFF = ARREARAGECOFF;
	}

	public String getCOMCODE() {
		return COMCODE;
	}

	public void setCOMCODE(String COMCODE) {
		this.COMCODE = COMCODE;
	}

	
}
