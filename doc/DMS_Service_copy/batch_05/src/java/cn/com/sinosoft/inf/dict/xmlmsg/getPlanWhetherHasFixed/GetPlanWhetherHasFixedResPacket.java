package cn.com.sinosoft.inf.dict.xmlmsg.getPlanWhetherHasFixed;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

public class GetPlanWhetherHasFixedResPacket {
	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private GetPlanWhetherHasFixedResBody BODY = new GetPlanWhetherHasFixedResBody();

	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}

	public void setHEAD(ResponseHeadSchema HEAD) {
		this.HEAD = HEAD;
	}

	public GetPlanWhetherHasFixedResBody getBODY() {
		return BODY;
	}

	public void setBODY(GetPlanWhetherHasFixedResBody body) {
		BODY = body;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


}
