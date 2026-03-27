package cn.com.sinosoft.inf.dict.xmlmsg.updateprpdstatistics;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;

public class UpdatePrpDstatisticsReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private UpdatePrpDstatisticsReqBody BODY = new UpdatePrpDstatisticsReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public UpdatePrpDstatisticsReqBody getBODY() {
		return BODY;
	}
	public void setBODY(UpdatePrpDstatisticsReqBody bODY) {
		BODY = bODY;
	}
	
}
