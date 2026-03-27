package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpPprofit;

public interface PrpPprofitService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public List<PrpPprofit> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
}
