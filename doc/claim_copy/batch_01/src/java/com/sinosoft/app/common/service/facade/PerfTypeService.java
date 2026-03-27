package com.sinosoft.app.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.app.common.model.PerfType;

public interface PerfTypeService {
	
	/**
	 * 查询代码类型
	 * @param QueryRule queryRule, int pageNo, int pageSize
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public Page queryPerfType(QueryRule queryRule, int pageNo, int pageSize)throws Exception;
	
	/**
	 * 按主键查询代码类型
	 * @param String codeType
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public PerfType findByPK(String codeType)throws Exception;
	
	/**
	 * 删除代码类型
	 * @param String codeType
	 * @param operateCode 操作人
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void deletePerfType(String codeType)throws Exception;
	
	/**
	 * 保存代码类型
	 * @param PerfType
	 * @param operateCode
	 * @throws Exception
	 * @author 中科软
	 * @Data 2011-08-30
	 */
	public void savePerfType(PerfType perfType,String operateType)throws Exception;
}
