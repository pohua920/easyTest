package cn.com.sinosoft.inf.dict.xmlmsg.getIdentityDesc;

import com.sinosoft.dmsdriver.domain.common.SchemaNode;

public class GetIdentityDescReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String IDENTIFIERCODE = "";

	public String getIDENTIFIERCODE() {
		return IDENTIFIERCODE;
	}

	public void setIDENTIFIERCODE(String identifiercode) {
		IDENTIFIERCODE = identifiercode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


}
