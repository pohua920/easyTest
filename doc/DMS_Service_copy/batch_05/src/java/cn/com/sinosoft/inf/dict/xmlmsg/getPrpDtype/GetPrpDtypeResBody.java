package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDtypeResBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String CODETYPE = "";
	private String CODETYPECNAME = "";
	private String CODETYPEDESC = "";
	private String NEWCODETYPE = "";

	public String getCODETYPE() {
		return CODETYPE;
	}

	public void setCODETYPE(String CODETYPE) {
		this.CODETYPE = CODETYPE;
	}

	public String getCODETYPECNAME() {
		return CODETYPECNAME;
	}

	public void setCODETYPECNAME(String CODETYPECNAME) {
		this.CODETYPECNAME = CODETYPECNAME;
	}

	public String getCODETYPEDESC() {
		return CODETYPEDESC;
	}

	public void setCODETYPEDESC(String CODETYPEDESC) {
		this.CODETYPEDESC = CODETYPEDESC;
	}

	public String getNEWCODETYPE() {
		return NEWCODETYPE;
	}

	public void setNEWCODETYPE(String NEWCODETYPE) {
		this.NEWCODETYPE = NEWCODETYPE;
	}

}
