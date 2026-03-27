package cn.com.sinosoft.inf.dict.xmlmsg.getTaxAuthorities;

import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetTaxAuthoritiesReqBody  implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private String userCode="";
	private String comCode="";
	private DictPage dictPage = new DictPage();
	public void validate() throws Exception {
	}
	

	public String getUserCode() {
		return userCode;
	}


	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getComCode() {
		return comCode;
	}


	public void setComCode(String comCode) {
		this.comCode = comCode;
	}


	public DictPage getDictPage() {
		return dictPage;
	}
	public void setDictPage(DictPage dictPage) {
		this.dictPage = dictPage;
	}
	
}
