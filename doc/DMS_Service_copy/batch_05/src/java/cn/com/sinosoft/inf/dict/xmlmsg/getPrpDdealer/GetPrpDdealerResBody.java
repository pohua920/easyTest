package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDdealerResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private PrpDdealerResInfo PRPDDEALER = new PrpDdealerResInfo();
	public PrpDdealerResInfo getPRPDDEALER() {
		return PRPDDEALER;
	}
	public void setPRPDDEALER(PrpDdealerResInfo PRPDDEALER) {
		this.PRPDDEALER = PRPDDEALER;
	}
	
}

