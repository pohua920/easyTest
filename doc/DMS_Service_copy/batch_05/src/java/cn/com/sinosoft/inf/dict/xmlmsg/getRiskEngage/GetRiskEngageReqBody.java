package cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage;

import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetRiskEngageReqBody  implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String riskCode="";
	private String language="";
	private String clauseCode="";
	private String engageCode="";
	private String extraEngageCode="";
	private String extraCondition="";////add by guyanqing 2012-02-06 reason:?????????
	private String initFlag;
	

	private DictPage dictPage = new DictPage();
	public void validate() throws Exception {
	}
	
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getClauseCode() {
		return clauseCode;
	}

	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	public DictPage getDictPage() {
		return dictPage;
	}
	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}

	public String getEngageCode() {
		return engageCode;
	}

	public void setEngageCode(String engageCode) {
		this.engageCode = engageCode;
	}

	public String getExtraEngageCode() {
		return extraEngageCode;
	}

	public void setExtraEngageCode(String extraEngageCode) {
		this.extraEngageCode = extraEngageCode;
	}

	public String getExtraCondition() {
		return extraCondition;
	}

	public void setExtraCondition(String extraCondition) {
		this.extraCondition = extraCondition;
	}
	public String getInitFlag() {
		return initFlag;
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}
	
}
