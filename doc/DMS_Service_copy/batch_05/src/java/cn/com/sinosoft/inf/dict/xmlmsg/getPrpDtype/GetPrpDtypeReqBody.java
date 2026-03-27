package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDtypeReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPE = "";
    
	private String CODETYPENAME="";

	public String getCODETYPENAME() {
		return CODETYPENAME;
	}

	public void setCODETYPENAME(String codetypename) {
		CODETYPENAME = codetypename;
	}

	public String getCODETYPE() {
		return CODETYPE;
	}

	public void setCODETYPE(String CODETYPE) {
		this.CODETYPE = CODETYPE;
	}

}
