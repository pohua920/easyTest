package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDcodeRisk;

public interface PrpDcodeRiskService {
	/**
	 * 更具条件查询prpdcoderisk
	 * @param conditions 查询条件
	 * @return 返回数据集
	 * @throws Exception
	 */
	public List<PrpDcodeRisk> findByConditions(String conditions)throws Exception;
	/**
	 * 更具条件查询prpdcoderisk
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示的条数
	 * @return
	 * @throws Exception
	 */
	public List<PrpDcodeRisk> findByConditions(String conditions, int pageNo, int rowsPerPage)throws Exception;
}
