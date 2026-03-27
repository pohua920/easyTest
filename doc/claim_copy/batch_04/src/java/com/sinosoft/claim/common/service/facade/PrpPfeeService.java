package com.sinosoft.claim.common.service.facade;

import java.util.ArrayList;

import com.sinosoft.claim.schema.model.PrpPfee;

public interface PrpPfeeService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public ArrayList<PrpPfee> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
}
