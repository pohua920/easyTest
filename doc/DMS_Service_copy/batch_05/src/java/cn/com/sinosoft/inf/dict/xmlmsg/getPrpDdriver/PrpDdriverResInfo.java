package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDdriverResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String   DRIVINGLICENSENO="";

	private String   DRIVERNAME="";

 	private String   DRIVERSEX="";

	private String   BIRTHDAY="";

	private String   DRIVERADDRESS="";

	private String   IDENTIFYNUMBER="";

	private String   RECEIVELICENSEDATE="";

	private String   AWARDLICENSEORGAN="";

	private String   DRIVINGCARTYPE="";

	public String getDRIVINGLICENSENO() {
		return DRIVINGLICENSENO;
	}

	public void setDRIVINGLICENSENO(String DRIVINGLICENSENO) {
		this.DRIVINGLICENSENO = DRIVINGLICENSENO;
	}

	public String getDRIVERNAME() {
		return DRIVERNAME;
	}

	public void setDRIVERNAME(String DRIVERNAME) {
		this.DRIVERNAME = DRIVERNAME;
	}

	public String getDRIVERSEX() {
		return DRIVERSEX;
	}

	public void setDRIVERSEX(String DRIVERSEX) {
		this.DRIVERSEX = DRIVERSEX;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}

	public String getDRIVERADDRESS() {
		return DRIVERADDRESS;
	}

	public void setDRIVERADDRESS(String DRIVERADDRESS) {
		this.DRIVERADDRESS = DRIVERADDRESS;
	}

	public String getIDENTIFYNUMBER() {
		return IDENTIFYNUMBER;
	}

	public void setIDENTIFYNUMBER(String IDENTIFYNUMBER) {
		this.IDENTIFYNUMBER = IDENTIFYNUMBER;
	}

	public String getRECEIVELICENSEDATE() {
		return RECEIVELICENSEDATE;
	}

	public void setRECEIVELICENSEDATE(String RECEIVELICENSEDATE) {
		this.RECEIVELICENSEDATE = RECEIVELICENSEDATE;
	}

	public String getAWARDLICENSEORGAN() {
		return AWARDLICENSEORGAN;
	}

	public void setAWARDLICENSEORGAN(String AWARDLICENSEORGAN) {
		this.AWARDLICENSEORGAN = AWARDLICENSEORGAN;
	}

	public String getDRIVINGCARTYPE() {
		return DRIVINGCARTYPE;
	}

	public void setDRIVINGCARTYPE(String DRIVINGCARTYPE) {
		this.DRIVINGCARTYPE = DRIVINGCARTYPE;
	}

	
	
	
}
