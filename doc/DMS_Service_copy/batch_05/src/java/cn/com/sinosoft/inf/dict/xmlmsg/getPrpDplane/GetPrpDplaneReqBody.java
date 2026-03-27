package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDplaneReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String LICENSENO = "";
	public String getLICENSENO() {
		return LICENSENO;
	}
	public void setLICENSENO(String LICENSENO) {
		this.LICENSENO = LICENSENO;
	}
	
}
