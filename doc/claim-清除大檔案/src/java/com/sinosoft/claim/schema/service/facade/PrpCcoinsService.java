package com.sinosoft.claim.schema.service.facade;
/**
 * 共保信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCcoinsId;

public interface PrpCcoinsService {
	
	/**
	 * 保存共保信息信息
	 * @param PrpCcoins ：传入的共保信息
	 */
	public void save(PrpCcoins PrpCcoins) throws Exception;
	
	/**
	 * 共保信息信息
	 * @param list  :传入的共保信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCcoins> list) throws Exception;
	
	/**
	 * 删除共保信息信息
	 * @param PrpCcoinsId ：传入的共保信息编号
	 */
	public void delete(PrpCcoinsId PrpCcoinsId) throws Exception;

	/**
	 * 更新共保信息信息
	 * @param PrpCcoins :传入需要更新的共保信息
	 */
	public void update(PrpCcoins PrpCcoins) throws Exception;

	/**
	 * 根据共保信息编号查询出共保信息信息
	 * @param PrpCcoinsId ：传入的共保信息编号
	 * @return 返回共保信息
	 */
	public PrpCcoins findPrpCcoins(PrpCcoinsId PrpCcoinsId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的共保信息页面信息
	 */
	public Page findPrpCcoins(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取共保信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的共保信息的列表
	 */
	public List<PrpCcoins> findPrpCcoins(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据共保信息编号查询出共保信息信息
	 * @param certiNo ：传入的共保信息编号
	 * @return 返回共保信息
	 */
	public PrpCcoins findPrpCcoins(String certiNo) throws Exception;
	/**
	 * 根据查询条件获取共保信息的列表
	 * @param conditions 查询条件
	 * @return 包含的共保信息的列表
	 */
	public List<PrpCcoins> findByConditionsChiefFlag(String conditions)throws Exception;
	/**
	 * 根据查询条件获取共保信息的列表
	 * @param conditions 查询条件
	 * @return 包含的共保信息的列表
	 */
	public List<PrpCcoins> findByConditions(String conditions)throws Exception;
}
