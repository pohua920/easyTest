package com.sinosoft.claim.schema.service.facade;
/**
 * 担保信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCguaranty;
import com.sinosoft.claim.schema.model.PrpCguarantyId;

public interface PrpCguarantyService {
	
	/**
	 * 保存担保信息信息
	 * @param PrpCguaranty ：传入的担保信息
	 */
	public void save(PrpCguaranty PrpCguaranty) throws Exception;
	
	/**
	 * 担保信息信息
	 * @param list  :传入的担保信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCguaranty> list) throws Exception;
	
	/**
	 * 删除担保信息信息
	 * @param PrpCguarantyId ：传入的担保信息编号
	 */
	public void delete(PrpCguarantyId PrpCguarantyId) throws Exception;

	/**
	 * 更新担保信息信息
	 * @param PrpCguaranty :传入需要更新的担保信息
	 */
	public void update(PrpCguaranty PrpCguaranty) throws Exception;

	/**
	 * 根据担保信息编号查询出担保信息信息
	 * @param PrpCguarantyId ：传入的担保信息编号
	 * @return 返回担保信息
	 */
	public PrpCguaranty findPrpCguaranty(PrpCguarantyId PrpCguarantyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的担保信息页面信息
	 */
	public Page findPrpCguaranty(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取担保信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的担保信息的列表
	 */
	public List<PrpCguaranty> findPrpCguaranty(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据担保信息编号查询出担保信息信息
	 * @param certiNo ：传入的担保信息编号
	 * @return 返回担保信息
	 */
	public PrpCguaranty findPrpCguaranty(String certiNo) throws Exception;
}
