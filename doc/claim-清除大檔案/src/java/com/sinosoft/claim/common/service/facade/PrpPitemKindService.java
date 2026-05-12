package com.sinosoft.claim.common.service.facade;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpPitemKind;

public interface PrpPitemKindService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public ArrayList<PrpPitemKind> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
    
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
    public List<PrpPitemKind> findByConditions(String conditions) throws Exception;
}
