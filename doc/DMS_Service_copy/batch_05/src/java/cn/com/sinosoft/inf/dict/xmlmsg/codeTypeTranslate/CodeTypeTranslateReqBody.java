package cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class CodeTypeTranslateReqBody implements SchemaNode {
	
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String CODETYPE = "";
	public String getCODETYPE() {
		return CODETYPE;
	}
	public void setCODETYPE(String CODETYPE) {
		this.CODETYPE = CODETYPE;
	}
	
}
