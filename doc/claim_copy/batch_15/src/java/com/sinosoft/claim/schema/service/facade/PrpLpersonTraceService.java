package com.sinosoft.claim.schema.service.facade;
/**
 * 人伤跟踪接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLpersonTraceId;

public interface PrpLpersonTraceService {
	
	/**
	 * 保存人伤跟踪信息
	 * @param prpLpersonTrace ：传入的人伤跟踪
	 */
	public void save(PrpLpersonTrace prpLpersonTrace) throws Exception;
	
	/**
	 * 保存人伤跟踪信息
	 * @param list:保存人伤跟踪信息
	 */
	public void save(List<PrpLpersonTrace> list) throws Exception;
	
	/**
	 * 删除人伤跟踪信息
	 * @param prpLpersonTraceId ：传入的人伤跟踪编号
	 */
	public void delete(PrpLpersonTraceId prpLpersonTraceId) throws Exception;

	/**
	 * 更新人伤跟踪信息
	 * @param prpLpersonTrace :传入需要更新的人伤跟踪
	 */
	public void update(PrpLpersonTrace prpLpersonTrace) throws Exception;

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param prpLpersonTraceId ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLpersonTrace findPrpLpersonTrace(PrpLpersonTraceId prpLpersonTraceId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人伤跟踪页面信息
	 */
	public Page findPrpLpersonTrace(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<PrpLpersonTrace> findPrpLpersonTrace(QueryRule queryRule) throws Exception;
	/**
	 * 保存人伤跟踪信息
	 * @param list:保存人伤跟踪信息
	 */
	public void saveOrUpdate(List<PrpLpersonTrace> list) throws Exception;
	/**
	 * 保存人伤跟踪信息
	 * @param list:保存人伤跟踪信息
	 */
	public void saveOrUpdate(PrpLpersonTrace prpLpersonTrace) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 人伤跟踪信息
	 * @param list  :传入的人伤跟踪信息集合
	 * @throws Exceptionuan
	 */
	public void insertAll(List<PrpLpersonTrace> prpLpersonTraceList);
}
