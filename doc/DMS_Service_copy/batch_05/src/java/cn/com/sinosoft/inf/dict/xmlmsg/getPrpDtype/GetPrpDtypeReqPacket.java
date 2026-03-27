package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class GetPrpDtypeReqPacket implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private GetPrpDtypeReqBody BODY = new GetPrpDtypeReqBody();



	public GetPrpDtypeReqBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPrpDtypeReqBody BODY) {
		this.BODY = BODY;
	}

	public RequestHeadPacket getHEAD() {
		return HEAD;
	}

	public void setHEAD(RequestHeadPacket head) {
		HEAD = head;
	}

}
