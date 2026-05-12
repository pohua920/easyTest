package com.sinosoft.claim.schema.service.facade;

import com.sinosoft.claim.schema.model.PrpLemailLog;

public interface PrpLemailLogService {
	
	public void save(PrpLemailLog prpLemailLog);
	
	public void delete(PrpLemailLog prpLemailLog);
	
	public void update(PrpLemailLog prpLemailLog);
	
	public PrpLemailLog findPrpLemailByPK(String Id );
	public void logForSendEmail(PrpLemailLog prpLemailLog);
}
