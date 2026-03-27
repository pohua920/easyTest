package cn.com.sinosoft.inf.dict.xmlmsg.getCodeWithRiskOrCom;

import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;



public class GetCodeWithReqBody  implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String codeType="";
	private String codeCode="";
	private String codeCName="";
	private String withCode="";//riskCode 或者 comCode
	private String codeFlag="";
	private String ignoreCode="";
	private String extraCodeCode="";
	private String upperCode="";
	private DictPage dictPage = new DictPage();
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeCode() {
		return codeCode;
	}
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}
	public String getCodeCName() {
		return codeCName;
	}
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}
	
	public String getWithCode() {
		return withCode;
	}
	public void setWithCode(String withCode) {
		this.withCode = withCode;
	}
	public DictPage getDictPage() {
		return dictPage;
	}
	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}
	public void validate() throws Exception {
	}
	public String getCodeFlag() {
		return codeFlag;
	}
	public void setCodeFlag(String codeFlag) {
		this.codeFlag = codeFlag;
	}
	public String getIgnoreCode() {
		return ignoreCode;
	}
	public void setIgnoreCode(String ignoreCode) {
		this.ignoreCode = ignoreCode;
	}
	public String getExtraCodeCode() {
		return extraCodeCode;
	}
	public void setExtraCodeCode(String extraCodeCode) {
		this.extraCodeCode = extraCodeCode;
	}
	public String getUpperCode() {
		return upperCode;
	}
	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}
	
}
