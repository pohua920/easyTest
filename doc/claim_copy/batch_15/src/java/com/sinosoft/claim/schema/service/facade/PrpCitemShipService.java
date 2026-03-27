package com.sinosoft.claim.schema.service.facade;
/**
 * 船舶险标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;

public interface PrpCitemShipService {
	
	/**
	 * 保存船舶险标的信息信息
	 * @param PrpCitemShip ：传入的船舶险标的信息
	 */
	public void save(PrpCitemShip PrpCitemShip) throws Exception;
	
	/**
	 * 船舶险标的信息信息
	 * @param list  :传入的船舶险标的信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemShip> list) throws Exception;
	
	/**
	 * 删除船舶险标的信息信息
	 * @param PrpCitemShipId ：传入的船舶险标的信息编号
	 */
	public void delete(PrpCitemShipId PrpCitemShipId) throws Exception;

	/**
	 * 更新船舶险标的信息信息
	 * @param PrpCitemShip :传入需要更新的船舶险标的信息
	 */
	public void update(PrpCitemShip PrpCitemShip) throws Exception;

	/**
	 * 根据船舶险标的信息编号查询出船舶险标的信息信息
	 * @param PrpCitemShipId ：传入的船舶险标的信息编号
	 * @return 返回船舶险标的信息
	 */
	public PrpCitemShip findPrpCitemShip(PrpCitemShipId PrpCitemShipId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的船舶险标的信息页面信息
	 */
	public Page findPrpCitemShip(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  船舶险标的的列表
	 * @param queryRule 查询对象
	 * @return 包含的船舶险标的  的列表
	 */
	public List<PrpCitemShip> findPrpCitemShip(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据船舶险标的信息编号查询出船舶险标的信息信息
	 * @param certiNo ：传入的船舶险标的信息编号
	 * @return 返回船舶险标的信息
	 */
	public PrpCitemShip findPrpCitemShip(String certiNo) throws Exception;
}
