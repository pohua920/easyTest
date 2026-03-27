package cn.com.sinosoft.inf.dict.xmlmsg.risktransform;

import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;



public class RiskTransformResPacket {

	private ResponseHeadSchema HEAD = new ResponseHeadSchema();
	private RiskTransformResBody BODY = new RiskTransformResBody();
	public ResponseHeadSchema getHEAD() {
		return HEAD;
	}
	public void setHEAD(ResponseHeadSchema hEAD) {
		HEAD = hEAD;
	}
	public RiskTransformResBody getBODY() {
		return BODY;
	}
	public void setBODY(RiskTransformResBody bODY) {
		BODY = bODY;
	}
	
}
