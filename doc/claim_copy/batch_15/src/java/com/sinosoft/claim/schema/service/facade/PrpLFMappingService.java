package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔费用与收付原因对照接口
 * @author 中科软
 */
import java.util.List;
import java.util.Map;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLFMapping;
import com.sinosoft.claim.schema.model.PrpLFMappingId;

public interface PrpLFMappingService {
	
	/**
	 * 保存理赔费用与收付原因对照信息
	 * @param prpLFMapping ：传入的理赔费用与收付原因对照
	 */
	public void save(PrpLFMapping prpLFMapping) throws Exception;
	
	/**
	 * 理赔费用与收付原因对照信息
	 * @param list  :传入的理赔费用与收付原因对照信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLFMapping> list) throws Exception;
	
	/**
	 * 删除理赔费用与收付原因对照信息
	 * @param prpLFMappingId ：传入的理赔费用与收付原因对照编号
	 */
	public void delete(PrpLFMappingId prpLFMappingId) throws Exception;

	/**
	 * 更新理赔费用与收付原因对照信息
	 * @param prpLFMapping :传入需要更新的理赔费用与收付原因对照
	 */
	public void update(PrpLFMapping prpLFMapping) throws Exception;

	/**
	 * 根据理赔费用与收付原因对照编号查询出理赔费用与收付原因对照信息
	 * @param prpLFMappingId ：传入的理赔费用与收付原因对照编号
	 * @return 返回理赔费用与收付原因对照
	 */
	public PrpLFMapping findPrpLFMapping(PrpLFMappingId prpLFMappingId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔费用与收付原因对照页面信息
	 */
	public Page findPrpLFMapping(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 理赔费用与收付原因对照信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 理赔费用与收付原因对照信息 的集合
	 */
	public List<PrpLFMapping> findPrpLFMapping(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据理赔费用与收付原因对照编号查询出理赔费用与收付原因对照信息
	 * @param certiNo ：传入的理赔费用与收付原因对照编号
	 * @return 返回理赔费用与收付原因对照
	 */
	public PrpLFMapping findPrpLFMapping(String certiNo) throws Exception;
	     
		/**根据查询对象获取理赔费用与收付原因对照信息集合
		 * @param conditions
		 * @return 理赔费用与收付原因对照信息集合
		 * @throws Exception
		 */
	public List<PrpLFMapping> findByConditions(String conditions)throws Exception;
	/** (non-Javadoc)
	 * @see com.sinosoft.claim.schema.service.facade.PrpLFMappingService#findByConditions(java.lang.String)
	 * 根据条件查询所有的
	 */
	public Map<String, String> findMapByConditions(String conditions)throws Exception;
}
