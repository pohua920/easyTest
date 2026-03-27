package cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class UpperCodeResInfo implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPE="";
	private String CODECODE="";
	private String CODECNAME="";
	private String CODEENAME="";
	private String CODECODE1="";
	private String CODECODE2="";
	private String CODECODE3="";
	private String CODECODE4="";
	private String CODECODE5="";
	private String NEWCODECODE="";

	public String getCODETYPE() {
		return CODETYPE;
	}

	public void setCODETYPE(String CODETYPE) {
		this.CODETYPE = CODETYPE;
	}

	public String getCODECODE() {
		return CODECODE;
	}

	public void setCODECODE(String CODECODE) {
		this.CODECODE = CODECODE;
	}

	public String getCODECNAME() {
		return CODECNAME;
	}

	public void setCODECNAME(String CODECNAME) {
		this.CODECNAME = CODECNAME;
	}

	public String getCODEENAME() {
		return CODEENAME;
	}

	public void setCODEENAME(String CODEENAME) {
		this.CODEENAME = CODEENAME;
	}

	public String getCODECODE1() {
		return CODECODE1;
	}

	public void setCODECODE1(String CODECODE1) {
		this.CODECODE1 = CODECODE1;
	}

	public String getCODECODE2() {
		return CODECODE2;
	}

	public void setCODECODE2(String CODECODE2) {
		this.CODECODE2 = CODECODE2;
	}

	public String getCODECODE3() {
		return CODECODE3;
	}

	public void setCODECODE3(String CODECODE3) {
		this.CODECODE3 = CODECODE3;
	}

	public String getCODECODE4() {
		return CODECODE4;
	}

	public void setCODECODE4(String CODECODE4) {
		this.CODECODE4 = CODECODE4;
	}

	public String getCODECODE5() {
		return CODECODE5;
	}

	public void setCODECODE5(String CODECODE5) {
		this.CODECODE5 = CODECODE5;
	}

	public String getNEWCODECODE() {
		return NEWCODECODE;
	}

	public void setNEWCODECODE(String NEWCODECODE) {
		this.NEWCODECODE = NEWCODECODE;
	}

}
