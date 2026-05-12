package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDclass;

/**
 * 收费计划接口
 * @author 中科软
 *
 */
public interface PrpDclassService {



	/**
	 * 保存优惠信息
	 * @param prpLcheck ：传入的优惠信息
	 */
	public void save(PrpDclass prpDclass) throws Exception;

	/**
	 * 优惠信息
	 * @param list :传入的优惠信息集合
	 * @throws Exception
	 */
	public void save(List<PrpDclass> list) throws Exception;

	/**
	 * 删除优惠信息
	 * @param prpDclassId ：传入的优惠信息编号
	 */
	public void delete(String classCode) throws Exception;

	/**
	 * 更新优惠信息
	 * @param prpDclass :传入需要更新的优惠信息
	 */
	public void update(PrpDclass prpDclass) throws Exception;

	/**
	 * 根据优惠信息编号查询出优惠信息
	 * @param prpDclassId ：传入的优惠信息编号
	 * @return 返回优惠信息
	 */
	public PrpDclass findPrpDclass(String classCode) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的优惠信息页面信息
	 */
	public Page findPrpDclass(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	public List<PrpDclass> findPrpDclass(QueryRule queryRule) throws Exception;
	 /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含prpDclassDto的集合
     * @throws Exception
     */
    public Collection<PrpDclass> findByConditions(String conditions)
        throws Exception;

}
