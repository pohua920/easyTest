package com.sinosoft.dmsdriver.domain.getPlanInfo;

import com.sinosoft.dmsdriver.model.PrpDration;

public class GetPlanInfoReqBody
{
  private PrpDration prpDration;
  private static final long serialVersionUID = 1L;

  public PrpDration getPrpDration()
  {
    return this.prpDration;
  }

  public void setPrpDration(PrpDration prpDration) {
    this.prpDration = prpDration;
  }
}
