package cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;

public class GetPlanInfoNewReqPacket
{
  private RequestHeadPacket HEAD = new RequestHeadPacket();
  private GetPlanInfoNewReqBody BODY = new GetPlanInfoNewReqBody();

  public RequestHeadPacket getHEAD()
  {
    return this.HEAD; }

  public void setHEAD(RequestHeadPacket head) {
    this.HEAD = head; }

public GetPlanInfoNewReqBody getBODY() {
	return BODY;
}

public void setBODY(GetPlanInfoNewReqBody bODY) {
	BODY = bODY;
}

  
}