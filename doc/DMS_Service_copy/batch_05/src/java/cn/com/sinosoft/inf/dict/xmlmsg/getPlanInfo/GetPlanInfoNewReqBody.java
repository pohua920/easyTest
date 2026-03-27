package cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo;

import cn.com.sinosoft.dms.model.PrpDration;



public class GetPlanInfoNewReqBody
{
  private PrpDration prpDration;
  private static final long serialVersionUID = 1L;
  private String comCode;
  private String  startDate;
  private String endDate;
  private String startHour;
  private String endHour;
  private String agentCode;
  private String policyWayCode;
  
  public PrpDration getPrpDration()
  {
    return this.prpDration;
  }

  public void setPrpDration(PrpDration prpDration) {
    this.prpDration = prpDration;
  }

public String getComCode() {
	return comCode;
}

public void setComCode(String comCode) {
	this.comCode = comCode;
}

public String getStartDate() {
	return startDate;
}

public void setStartDate(String startDate) {
	this.startDate = startDate;
}

public String getEndDate() {
	return endDate;
}

public void setEndDate(String endDate) {
	this.endDate = endDate;
}

public String getStartHour() {
	return startHour;
}

public void setStartHour(String startHour) {
	this.startHour = startHour;
}

public String getEndHour() {
	return endHour;
}

public void setEndHour(String endHour) {
	this.endHour = endHour;
}

public String getAgentCode() {
	return agentCode;
}

public void setAgentCode(String agentCode) {
	this.agentCode = agentCode;
}

public String getPolicyWayCode() {
	return policyWayCode;
}

public void setPolicyWayCode(String policyWayCode) {
	this.policyWayCode = policyWayCode;
}


}