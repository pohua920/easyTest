package cn.com.sinosoft.inf.dict.xmlmsg.translateLimit;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class TranslateLimitResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODECNAME = "";

	public String getCODECNAME() {
		return CODECNAME;
	}

	public void setCODECNAME(String codecname) {
		CODECNAME = codecname;
	}


}
