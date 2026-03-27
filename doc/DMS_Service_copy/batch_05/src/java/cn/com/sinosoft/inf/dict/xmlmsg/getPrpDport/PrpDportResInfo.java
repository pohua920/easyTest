package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDportResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private String PORTCODE="";

	private String PORTCNAME="";

	private String PORTENAME="";

	private String COUNTRYCODE="";

	private String COUNTRYCNAME="";

	private String COUNTRYENAME="";

	private String NEWPORTCODE="";

	public String getPORTCODE() {
		return PORTCODE;
	}

	public void setPORTCODE(String PORTCODE) {
		this.PORTCODE = PORTCODE;
	}

	public String getPORTCNAME() {
		return PORTCNAME;
	}

	public void setPORTCNAME(String PORTCNAME) {
		this.PORTCNAME = PORTCNAME;
	}

	public String getPORTENAME() {
		return PORTENAME;
	}

	public void setPORTENAME(String PORTENAME) {
		this.PORTENAME = PORTENAME;
	}

	public String getCOUNTRYCODE() {
		return COUNTRYCODE;
	}

	public void setCOUNTRYCODE(String COUNTRYCODE) {
		this.COUNTRYCODE = COUNTRYCODE;
	}

	public String getCOUNTRYCNAME() {
		return COUNTRYCNAME;
	}

	public void setCOUNTRYCNAME(String COUNTRYCNAME) {
		this.COUNTRYCNAME = COUNTRYCNAME;
	}

	public String getCOUNTRYENAME() {
		return COUNTRYENAME;
	}

	public void setCOUNTRYENAME(String COUNTRYENAME) {
		this.COUNTRYENAME = COUNTRYENAME;
	}

	public String getNEWPORTCODE() {
		return NEWPORTCODE;
	}

	public void setNEWPORTCODE(String NEWPORTCODE) {
		this.NEWPORTCODE = NEWPORTCODE;
	}

}
