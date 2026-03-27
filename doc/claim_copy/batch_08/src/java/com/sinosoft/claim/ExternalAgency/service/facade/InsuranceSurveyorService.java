package com.sinosoft.claim.ExternalAgency.service.facade;

import ins.framework.common.Page;

import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;


public interface InsuranceSurveyorService {
	public PrpLInsuranceSurveyor findByPrimaryKey(String comcode, String newcomcode) throws Exception;
	public void insert(PrpLInsuranceSurveyor prpLInsuranceSurveyor)  throws Exception;
	public void update(PrpLInsuranceSurveyor prpLInsuranceSurveyor) throws Exception;
	public int getCount(String string);
	public Page findByQueryConditions(StringBuilder conditions, int pageNo, int recordPerPage);
}
