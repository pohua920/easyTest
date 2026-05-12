package com.sinosoft.claim.common.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDclauseKind;


public interface PrpDclauseKindService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含prpDclausekindDto的集合
     * @throws Exception
     */
    public List<PrpDclauseKind> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
    
    
    public List<PrpDclauseKind> findByConditions(QueryRule queryRule);
}
