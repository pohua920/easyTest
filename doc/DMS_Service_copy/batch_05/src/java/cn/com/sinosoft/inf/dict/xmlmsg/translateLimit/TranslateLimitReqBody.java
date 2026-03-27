package cn.com.sinosoft.inf.dict.xmlmsg.translateLimit;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class TranslateLimitReqBody implements SchemaNode {
	
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String RISKCODE = "";
	
	private String LIMITCODE = "";
	public String getRISKCODE() {
		return RISKCODE;
	}

	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}

	public String getLIMITCODE() {
		return LIMITCODE;
	}

	public void setLIMITCODE(String limitcode) {
		LIMITCODE = limitcode;
	}

	
}
