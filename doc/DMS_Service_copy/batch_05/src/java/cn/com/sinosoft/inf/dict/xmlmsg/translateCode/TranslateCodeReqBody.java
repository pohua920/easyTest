package cn.com.sinosoft.inf.dict.xmlmsg.translateCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class TranslateCodeReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String CODETYPE="";//代码类型
	private String CODECODE="";//代码
	private String LANGUAGE="";//要翻译的语言
	private String CODEFLAG="";
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
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	public void setLANGUAGE(String LANGUAGE) {
		this.LANGUAGE = LANGUAGE;
	}
	public String getCODEFLAG() {
		return CODEFLAG;
	}
	public void setCODEFLAG(String cODEFLAG) {
		CODEFLAG = cODEFLAG;
	}
	
}
