package com.sinosoft.claim.schema.service.facade;
/**
 * 货运险标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemCargo;
import com.sinosoft.claim.schema.model.PrpCitemCargoId;

public interface PrpCitemCargoService {
	
	/**
	 * 保存货运险标的信息信息
	 * @param PrpCitemCargo ：传入的货运险标的信息
	 */
	public void save(PrpCitemCargo PrpCitemCargo) throws Exception;
	
	/**
	 * 货运险标的信息信息
	 * @param list  :传入的货运险标的信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemCargo> list) throws Exception;
	
	/**
	 * 删除货运险标的信息信息
	 * @param PrpCitemCargoId ：传入的货运险标的信息编号
	 */
	public void delete(PrpCitemCargoId PrpCitemCargoId) throws Exception;

	/**
	 * 更新货运险标的信息信息
	 * @param PrpCitemCargo :传入需要更新的货运险标的信息
	 */
	public void update(PrpCitemCargo PrpCitemCargo) throws Exception;

	/**
	 * 根据货运险标的信息编号查询出货运险标的信息信息
	 * @param PrpCitemCargoId ：传入的货运险标的信息编号
	 * @return 返回货运险标的信息
	 */
	public PrpCitemCargo findPrpCitemCargo(PrpCitemCargoId PrpCitemCargoId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险标的信息页面信息
	 */
	public Page findPrpCitemCargo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取货运险标的信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险标的信息  的列表
	 */
	public List<PrpCitemCargo> findPrpCitemCargo(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据货运险标的信息编号查询出货运险标的信息信息
	 * @param certiNo ：传入的货运险标的信息编号
	 * @return 返回货运险标的信息
	 */
	public PrpCitemCargo findPrpCitemCargo(String certiNo) throws Exception;
}
