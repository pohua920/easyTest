package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeKind;

public class GetPrpDcodeKindReqBody {

	private static final long	serialVersionUID	= 1L;

	public void validate() throws Exception {
	}

	private String	RISKCODE;
	private String	CODETYPE;
	private String	KINDCODE;

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getCODETYPE() {
		return CODETYPE;
	}
	public void setCODETYPE(String codetype) {
		CODETYPE = codetype;
	}
	public String getKINDCODE() {
		return KINDCODE;
	}
	public void setKINDCODE(String kindcode) {
		KINDCODE = kindcode;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
