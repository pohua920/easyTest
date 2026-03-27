package cn.com.sinosoft.inf.dict.xmlmsg.getRisk;

import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetRiskReqBody  implements SchemaNode{
	private static final long serialVersionUID = 1L;
	
	private String classCode="";
	private String riskCode="";
	private String reverseType="";
	
	private DictPage dictPage = new DictPage();
	public void validate() throws Exception {
	}
	
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}


	public DictPage getDictPage() {
		return dictPage;
	}
	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}

	public String getReverseType() {
		return reverseType;
	}

	public void setReverseType(String reverseType) {
		this.reverseType = reverseType;
	}
	
}
