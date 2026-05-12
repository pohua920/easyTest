package com.sinosoft.claim.ExternalAgency.service.facade;

import ins.framework.common.Page;

import com.sinosoft.claim.schema.model.PrpLexternalAgency;

public interface ExternalagencyService {
	public void insert (PrpLexternalAgency prpLexternalAgency) throws Exception;
    public void delete(String comcode,String comtype) throws Exception;
    public void deleteByConditions(String strComCode, String strComType) throws Exception;
    public void update(PrpLexternalAgency prpLexternalAgency) throws Exception;
    public PrpLexternalAgency findByPrimaryKey(String comcode,String comtype, int pageNo, int pageSize) throws Exception;
    public Page findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
}
	
