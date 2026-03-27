package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdriverReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private String DRIVERLICENSENO;
	
	public String getDRIVERLICENSENO() {
		return DRIVERLICENSENO;
	}
	public void setDRIVERLICENSENO(String DRIVERLICENSENO) {
		this.DRIVERLICENSENO = DRIVERLICENSENO;
	}
	
	
}
