package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLagent;

public interface PrpLagentService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含prpLagentDto的集合
     * @throws Exception
     */
    public List<PrpLagent> findByConditions(String conditions);
    /**
     * 用於更改核赔标志位
     * @param prpLagentDto prpLagentDto
     * @throws Exception
     */
	public void updateUndwrt(String conditions) throws Exception;
}
