package com.sinosoft.claim.schema.service.facade;
/**
 * 地震基金实现接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLearthquakeFund;
import com.sinosoft.claim.schema.model.PrpLearthquakeFundId;

public interface PrpLearthquakeFundService {
	
	/**
	 * 保存地震基金
	 * @param PrpLearthquakeFund ：传入的地震基金
	 */
	public void save(PrpLearthquakeFund prpLearthquakeFund) throws Exception;
	
	/**
	 * 保存地震基金
	 * @param list  :传入的地震基金集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLearthquakeFund> list) throws Exception;
	
	/**
	 * 删除地震基金
	 * @param policyNo ：传入的地震基金编号
	 */
	public void delete(PrpLearthquakeFundId prpLearthquakeFundId) throws Exception;

	/**
	 * 更新地震基金
	 * @param PrpLearthquakeFund :传入需要更新的地震基金
	 */
	public void update(PrpLearthquakeFund prpLearthquakeFund) throws Exception;

	/**
	 * 根据地震基金编号查询出地震基金
	 * @param policyNo ：传入的地震基金编号
	 * @return 返回地震基金
	 */
	public PrpLearthquakeFund findPrpLearthquakeFund(PrpLearthquakeFundId prpLearthquakeFundId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单全貌页面信息
	 */
	public Page findPrpLearthquakeFund(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单全貌列表信息
	 */
	public List<PrpLearthquakeFund> findPrpLearthquakeFund(QueryRule queryRule) throws Exception;
}
