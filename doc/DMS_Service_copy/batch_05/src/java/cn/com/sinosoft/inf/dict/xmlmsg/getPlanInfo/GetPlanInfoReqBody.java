package cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo;

import cn.com.sinosoft.dms.model.PrpDration;


public class GetPlanInfoReqBody {

	private PrpDration prpDration;

	private static final long serialVersionUID = 1L;

	public PrpDration getPrpDration() {
		return prpDration;
	}

	public void setPrpDration(PrpDration prpDration) {
		this.prpDration = prpDration;
	}

}
