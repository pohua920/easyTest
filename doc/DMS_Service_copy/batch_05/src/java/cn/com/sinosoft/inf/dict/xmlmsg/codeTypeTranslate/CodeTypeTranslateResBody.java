package cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class CodeTypeTranslateResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPECNAME = "";

	public String getCODETYPECNAME() {
		return CODETYPECNAME;
	}

	public void setCODETYPECNAME(String CODETYPECNAME) {
		this.CODETYPECNAME = CODETYPECNAME;
	}

}
