package cn.com.sinosoft.inf.dict.xmlmsg.getSubCode;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

public class GetSubCodeResPacket {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetSubCodeResBody BODY = new GetSubCodeResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetSubCodeResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetSubCodeResBody BODY) {
		this.BODY = BODY;
	}

}
