package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class PrpDplaneResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String  LICENCENO="";
	private String  PLANETYPE="";
	private String  MODEL="";
	private String  FACTORYNO="";
	private String  FACTORYDATE="";
	private String  MAKEYEAR="";
	private String  AIRLINECNAME="";
	private String  AIRLINEENAME="";
	private String  RANGE="";
	private String  SEATCOUNT="";
	private String  LOANSTAUS="";
	private String  PLANEUSAGE="";
	private String  USDAMOUNT="";
	private String  JPYAMOUNT="";
	private String  REMARK="";

	public String getLICENCENO() {
		return LICENCENO;
	}
	public void setLICENCENO(String LICENCENO) {
		this.LICENCENO = LICENCENO;
	}
	public String getPLANETYPE() {
		return PLANETYPE;
	}
	public void setPLANETYPE(String PLANETYPE) {
		this.PLANETYPE = PLANETYPE;
	}
	public String getMODEL() {
		return MODEL;
	}
	public void setMODEL(String MODEL) {
		this.MODEL = MODEL;
	}
	public String getFACTORYNO() {
		return FACTORYNO;
	}
	public void setFACTORYNO(String FACTORYNO) {
		this.FACTORYNO = FACTORYNO;
	}
	public String getFACTORYDATE() {
		return FACTORYDATE;
	}
	public void setFACTORYDATE(String FACTORYDATE) {
		this.FACTORYDATE = FACTORYDATE;
	}
	public String getMAKEYEAR() {
		return MAKEYEAR;
	}
	public void setMAKEYEAR(String MAKEYEAR) {
		this.MAKEYEAR = MAKEYEAR;
	}
	public String getAIRLINECNAME() {
		return AIRLINECNAME;
	}
	public void setAIRLINECNAME(String AIRLINECNAME) {
		this.AIRLINECNAME = AIRLINECNAME;
	}
	public String getAIRLINEENAME() {
		return AIRLINEENAME;
	}
	public void setAIRLINEENAME(String AIRLINEENAME) {
		this.AIRLINEENAME = AIRLINEENAME;
	}
	public String getRANGE() {
		return RANGE;
	}
	public void setRANGE(String RANGE) {
		this.RANGE = RANGE;
	}
	public String getSEATCOUNT() {
		return SEATCOUNT;
	}
	public void setSEATCOUNT(String SEATCOUNT) {
		this.SEATCOUNT = SEATCOUNT;
	}
	public String getLOANSTAUS() {
		return LOANSTAUS;
	}
	public void setLOANSTAUS(String LOANSTAUS) {
		this.LOANSTAUS = LOANSTAUS;
	}
	public String getPLANEUSAGE() {
		return PLANEUSAGE;
	}
	public void setPLANEUSAGE(String PLANEUSAGE) {
		this.PLANEUSAGE = PLANEUSAGE;
	}
	public String getUSDAMOUNT() {
		return USDAMOUNT;
	}
	public void setUSDAMOUNT(String USDAMOUNT) {
		this.USDAMOUNT = USDAMOUNT;
	}
	public String getJPYAMOUNT() {
		return JPYAMOUNT;
	}
	public void setJPYAMOUNT(String JPYAMOUNT) {
		this.JPYAMOUNT = JPYAMOUNT;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}
	

}
