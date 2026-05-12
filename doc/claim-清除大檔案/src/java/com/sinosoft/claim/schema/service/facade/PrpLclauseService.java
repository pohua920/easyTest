package com.sinosoft.claim.schema.service.facade;
/**
 * 条款接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclause;

public interface PrpLclauseService {
	
	/**
	 * 保存条款信息
	 * @param prpLclause ：传入的条款
	 */
	public void save(PrpLclause prpLclause) throws Exception;
	
	/**
	 * 保存条款信息
	 * @param list:保存条款信息
	 */
	public void save(List<PrpLclause> list) throws Exception;
	
	/**
	 * 删除条款信息
	 * @param clauseCode ：传入的条款编号
	 */
	public void delete(String clauseCode) throws Exception;

	/**
	 * 更新条款信息
	 * @param prpLclause :传入需要更新的条款
	 */
	public void update(PrpLclause prpLclause) throws Exception;

	/**
	 * 根据条款编号查询出条款信息
	 * @param clauseCode ：传入的条款编号
	 * @return 返回条款
	 */
	public PrpLclause findPrpLclause(String clauseCode) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的条款页面信息
	 */
	public Page findPrpLclause(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取条款信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  条款信息的集合
	 */
	public List<PrpLclause> findPrpLclause(QueryRule queryRule) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 根据sql语句条件查询
	 */
	public List<PrpLclause> findByConditions(String conditions)throws Exception;

	/**
	 * @param prpLclause
	 * @throws Exception
	 * 保存或修改，
	 */
	public void saveOrUpdate(PrpLclause prpLclause)throws Exception;
	
	/**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(String conditions) throws Exception;


	Page findPrpLclause(String conditions, int pageNo, int pageSize) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 * conditions如果conditions後面有有别名，会出错，prpLclause.clauseCode='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	public Page findByConditions(String conditions,int pageNo,int pageSize) throws Exception;
}
