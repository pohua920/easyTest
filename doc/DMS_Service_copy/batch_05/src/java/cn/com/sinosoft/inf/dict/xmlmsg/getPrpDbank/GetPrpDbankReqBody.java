package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDbankReqBody implements SchemaNode {
	
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String BANKCODE = "";
	private String BANKNAME = "";

	public String getBANKCODE() {
		return BANKCODE;
	}

	public void setBANKCODE(String BANKCODE) {
		this.BANKCODE = BANKCODE;
	}

	public String getBANKNAME() {
		return BANKNAME;
	}

	public void setBANKNAME(String bANKNAME) {
		BANKNAME = bANKNAME;
	}

}
