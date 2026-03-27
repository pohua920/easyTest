package cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetUpperCodeResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private UpperCodeResInfo UPPERCODERESINFO = new UpperCodeResInfo();

	public UpperCodeResInfo getUPPERCODERESINFO() {
		return UPPERCODERESINFO;
	}

	public void setUPPERCODERESINFO(UpperCodeResInfo UPPERCODERESINFO) {
		this.UPPERCODERESINFO = UPPERCODERESINFO;
	}

	

	

}
