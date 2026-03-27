package com.sinosoft.claim.common.service.facade;

import com.sinosoft.claim.schema.model.PrpDtype;

public interface PrpDtypeService {
	public PrpDtype findByPrimaryKey(String codeType) throws Exception;
}
