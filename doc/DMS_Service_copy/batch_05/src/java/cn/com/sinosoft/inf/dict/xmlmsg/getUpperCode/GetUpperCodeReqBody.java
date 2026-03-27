package cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUpperCodeReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPE = "";
	private String CODECODE = "";

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

}
