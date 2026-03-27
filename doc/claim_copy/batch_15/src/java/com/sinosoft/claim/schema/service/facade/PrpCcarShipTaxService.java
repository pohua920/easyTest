package com.sinosoft.claim.schema.service.facade;
/**
 * 车船税接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcarShipTax;
import com.sinosoft.claim.schema.model.PrpCcarShipTaxId;

public interface PrpCcarShipTaxService {
	
	/**
	 * 保存车船税信息
	 * @param PrpCcarShipTax ：传入的车船税
	 */
	public void save(PrpCcarShipTax PrpCcarShipTax) throws Exception;
	
	/**
	 * 车船税信息
	 * @param list  :传入的车船税信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCcarShipTax> list) throws Exception;
	
	/**
	 * 删除车船税信息
	 * @param PrpCcarShipTaxId ：传入的车船税编号
	 */
	public void delete(PrpCcarShipTaxId PrpCcarShipTaxId) throws Exception;

	/**
	 * 更新车船税信息
	 * @param PrpCcarShipTax :传入需要更新的车船税
	 */
	public void update(PrpCcarShipTax PrpCcarShipTax) throws Exception;

	/**
	 * 根据车船税编号查询出车船税信息
	 * @param PrpCcarShipTaxId ：传入的车船税编号
	 * @return 返回车船税
	 */
	public PrpCcarShipTax findPrpCcarShipTax(PrpCcarShipTaxId PrpCcarShipTaxId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车船税页面信息
	 */
	public Page findPrpCcarShipTax(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取车船税页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的车船税页面信息的列表
	 */
	public List<PrpCcarShipTax> findPrpCcarShipTax(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据车船税编号查询出车船税信息
	 * @param certiNo ：传入的车船税编号
	 * @return 返回车船税
	 */
	public PrpCcarShipTax findPrpCcarShipTax(String certiNo) throws Exception;
}
