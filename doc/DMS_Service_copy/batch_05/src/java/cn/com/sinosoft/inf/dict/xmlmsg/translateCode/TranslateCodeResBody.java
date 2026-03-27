package cn.com.sinosoft.inf.dict.xmlmsg.translateCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class TranslateCodeResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	private String CODENAME="";

	public String getCODENAME() {
		return CODENAME;
	}
	public void setCODENAME(String CODENAME) {
		this.CODENAME = CODENAME;
	}
	
}
