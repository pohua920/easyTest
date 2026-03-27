package com.sinosoft.claim.schema.service.facade;
/**
 * 组合因子表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwComboFactor;
import com.sinosoft.claim.schema.model.UtiUwComboFactorId;

public interface UtiUwComboFactorService {
	/**
	 * 根据查询对象获取组合因子的集合
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的组合因子页面信息
	 */
	public List<?> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;
	
	/**
	 * 保存组合因子信息
	 * @param utiUwComboFactor ：传入的组合因子
	 */
	public void save(UtiUwComboFactor utiUwComboFactor) throws Exception;
	
	/**
	 * 保存组合因子信息
	 * @param list:保存组合因子信息
	 */
	public void save(List<UtiUwComboFactor> list) throws Exception;
	
	/**
	 * 删除组合因子信息
	 * @param utiUwComboFactorId ：传入的组合因子编号
	 */
	public void delete(UtiUwComboFactorId utiUwComboFactorId) throws Exception;

	/**
	 * 更新组合因子信息
	 * @param utiUwComboFactor :传入需要更新的组合因子
	 */
	public void update(UtiUwComboFactor utiUwComboFactor) throws Exception;

	/**
	 * 根据组合因子编号查询出组合因子信息
	 * @param utiUwComboFactorId ：传入的组合因子编号
	 * @return 返回组合因子
	 */
	public UtiUwComboFactor findUtiUwComboFactor(UtiUwComboFactorId utiUwComboFactorId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的组合因子页面信息
	 */
	public Page findUtiUwComboFactor(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取组合因子信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  组合因子信息的集合
	 */
	public List<UtiUwComboFactor> findUtiUwComboFactor(QueryRule queryRule) throws Exception;
}
