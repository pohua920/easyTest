package cn.com.sinosoft.inf.dict.xmlmsg.exchange;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class ExchangeResBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	private String EXCHEDAMOUNT = "";

	public void validate() throws Exception {
	}

	public String getEXCHEDAMOUNT() {
		return EXCHEDAMOUNT;
	}

	public void setEXCHEDAMOUNT(String EXCHEDAMOUNT) {
		this.EXCHEDAMOUNT = EXCHEDAMOUNT;
	}

}
