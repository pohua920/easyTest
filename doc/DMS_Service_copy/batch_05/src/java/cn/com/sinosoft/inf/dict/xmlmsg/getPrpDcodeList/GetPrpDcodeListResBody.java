package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetPrpDcodeListResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private PrpDcodeList CODELIST = new PrpDcodeList();

	public PrpDcodeList getCODELIST() {
		return CODELIST;
	}

	public void seCODELIST(PrpDcodeList CODELIST) {
		this.CODELIST = CODELIST;
	}
}
