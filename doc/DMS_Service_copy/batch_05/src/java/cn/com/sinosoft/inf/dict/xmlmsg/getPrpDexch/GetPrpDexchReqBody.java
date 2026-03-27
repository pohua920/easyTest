package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class GetPrpDexchReqBody implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private String EXCHDATE = "";
	private String BASECURRENCY = "";
	private String EXCHCURRENCY = "";

	public String getEXCHDATE() {
		return EXCHDATE;
	}

	public void setEXCHDATE(String EXCHDATE) {
		this.EXCHDATE = EXCHDATE;
	}

	public String getBASECURRENCY() {
		return BASECURRENCY;
	}

	public void setBASECURRENCY(String BASECURRENCY) {
		this.BASECURRENCY = BASECURRENCY;
	}

	public String getEXCHCURRENCY() {
		return EXCHCURRENCY;
	}

	public void setEXCHCURRENCY(String EXCHCURRENCY) {
		this.EXCHCURRENCY = EXCHCURRENCY;
	}

}
