package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDbankInfo implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String BANKCODE = "";
	private String BANKNAME = "";
	private String CUSTOMERCODE = "";
	private String ADDRESSNAME = "";
	private String POSTCODE = "";
	private String BANKTYPE = "";
	private String LINKERNAME = "";
	private String PHONENUMBER = "";
	private String FAXNUMBER = "";
	private String ARREARAGERATE = "";
	private String ARREARAGECOFF = "";
	private String COMCODE = "";

	public String getBANKCODE() {
		return BANKCODE;
	}

	public void setBANKCODE(String BANKCODE) {
		this.BANKCODE = BANKCODE;
	}

	public String getBANKNAME() {
		return BANKNAME;
	}

	public void setBANKNAME(String BANKNAME) {
		this.BANKNAME = BANKNAME;
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

	public String getBANKTYPE() {
		return BANKTYPE;
	}

	public void setBANKTYPE(String BANKTYPE) {
		this.BANKTYPE = BANKTYPE;
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
