package com.sinosoft.claim.schema.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLemailLog;
import com.sinosoft.claim.schema.service.facade.PrpLemailLogService;

public class PrpLemailLogServiceSpringImpl extends GenericDaoHibernate<PrpLemailLog, String>  implements PrpLemailLogService{

	@Override
	public void save(PrpLemailLog prpLemailLog) {
		super.save(prpLemailLog);
	}

	@Override
	public void delete(PrpLemailLog prpLemailLog) {
		super.delete(prpLemailLog);
	}

	@Override
	public void update(PrpLemailLog prpLemailLog) {
		super.update(prpLemailLog);
	}

	@Override
	public PrpLemailLog findPrpLemailByPK(String prpLemailLogId) {
		return super.get(PrpLemailLog.class, prpLemailLogId);
	}

	@Override
	public void logForSendEmail(PrpLemailLog prpLemailLog) {
		super.save(prpLemailLog);
	}

}
