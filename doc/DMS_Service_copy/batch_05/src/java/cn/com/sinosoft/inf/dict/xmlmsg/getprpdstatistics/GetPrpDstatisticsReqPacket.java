package cn.com.sinosoft.inf.dict.xmlmsg.getprpdstatistics;

import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;

public class GetPrpDstatisticsReqPacket {

	private RequestHeadPacket HEAD = new RequestHeadPacket();
	private GetPrpDstatisticsReqBody BODY = new GetPrpDstatisticsReqBody();
	public RequestHeadPacket getHEAD() {
		return HEAD;
	}
	public void setHEAD(RequestHeadPacket hEAD) {
		HEAD = hEAD;
	}
	public GetPrpDstatisticsReqBody getBODY() {
		return BODY;
	}
	public void setBODY(GetPrpDstatisticsReqBody bODY) {
		BODY = bODY;
	}
	
}
