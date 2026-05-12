package com.sinosoft.claim.schema.service.facade;
/**
 * 船舶险船员信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCshipDriver;
import com.sinosoft.claim.schema.model.PrpCshipDriverId;

public interface PrpCshipDriverService {
	
	/**
	 * 保存船舶险船员信息信息
	 * @param PrpCshipDriver ：传入的船舶险船员信息
	 */
	public void save(PrpCshipDriver PrpCshipDriver) throws Exception;
	
	/**
	 * 船舶险船员信息信息
	 * @param list  :传入的船舶险船员信息信息集合
	 * @throws Exception
	 */
	public void save(List<PrpCshipDriver> list) throws Exception;
	
	/**
	 * 删除船舶险船员信息信息
	 * @param PrpCshipDriverId ：传入的船舶险船员信息编号
	 */
	public void delete(PrpCshipDriverId PrpCshipDriverId) throws Exception;

	/**
	 * 更新船舶险船员信息信息
	 * @param PrpCshipDriver :传入需要更新的船舶险船员信息
	 */
	public void update(PrpCshipDriver PrpCshipDriver) throws Exception;

	/**
	 * 根据船舶险船员信息编号查询出船舶险船员信息信息
	 * @param PrpCshipDriverId ：传入的船舶险船员信息编号
	 * @return 返回船舶险船员信息
	 */
	public PrpCshipDriver findPrpCshipDriver(PrpCshipDriverId PrpCshipDriverId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的船舶险船员信息页面信息
	 */
	public Page findPrpCshipDriver(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  船舶险船员的列表
	 * @param queryRule 查询对象
	 * @return 包含的  船舶险船员的列表
	 */
	public List<PrpCshipDriver> findPrpCshipDriver(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据船舶险船员信息编号查询出船舶险船员信息信息
	 * @param certiNo ：传入的船舶险船员信息编号
	 * @return 返回船舶险船员信息
	 */
	public PrpCshipDriver findPrpCshipDriver(String certiNo) throws Exception;
}
