package com.sinosoft.claim.schema.service.facade;
/**
 * 赔案收费计划接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLplan;
import com.sinosoft.claim.schema.model.PrpLplanId;

public interface PrpLplanService {
	
	/**
	 * 保存赔案收费计划信息
	 * @param prpLplan ：传入的赔案收费计划
	 */
	public void save(PrpLplan prpLplan) throws Exception;
	
	/**
	 * 赔案收费计划信息
	 * @param list  :传入的赔案收费计划信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLplan> list) throws Exception;
	
	/**
	 * 删除赔案收费计划信息
	 * @param prpLplanId ：传入的赔案收费计划编号
	 */
	public void delete(PrpLplanId prpLplanId) throws Exception;

	/**
	 * 更新赔案收费计划信息
	 * @param prpLplan :传入需要更新的赔案收费计划
	 */
	public void update(PrpLplan prpLplan) throws Exception;

	/**
	 * 根据赔案收费计划编号查询出赔案收费计划信息
	 * @param prpLplanId ：传入的赔案收费计划编号
	 * @return 返回赔案收费计划
	 */
	public PrpLplan findPrpLplan(PrpLplanId prpLplanId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔案收费计划页面信息
	 */
	public Page findPrpLplan(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 赔案收费计划信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  赔案收费计划信息 的集合
	 */
	public List<PrpLplan> findPrpLplan(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔案收费计划编号查询出赔案收费计划信息
	 * @param certiNo ：传入的赔案收费计划编号
	 * @return 返回赔案收费计划
	 */
	public PrpLplan findPrpLplan(String certiNo) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 更具sql条件查询信息
	 */
	public List<PrpLplan> findByConditions(String conditions)throws Exception;
	int getCount(String conditions) throws Exception;
}
