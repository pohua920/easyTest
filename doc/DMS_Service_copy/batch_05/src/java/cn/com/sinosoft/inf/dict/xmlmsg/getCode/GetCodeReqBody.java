package cn.com.sinosoft.inf.dict.xmlmsg.getCode;

import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetCodeReqBody  implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String codeType = "";
	private String codeFlag = "";
	private DictPage dictPage = new DictPage();
	
	public void validate() throws Exception {
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public DictPage getDictPage() {
		return dictPage;
	}
	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}
	public String getCodeFlag() {
		return codeFlag;
	}
	public void setCodeFlag(String codeFlag) {
		this.codeFlag = codeFlag;
	}
	
}
