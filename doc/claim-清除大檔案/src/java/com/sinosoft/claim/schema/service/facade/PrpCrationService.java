package com.sinosoft.claim.schema.service.facade;
/**
 * 承保险种定额份数接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCration;
import com.sinosoft.claim.schema.model.PrpCrationId;

public interface PrpCrationService {
	
	/**
	 * 保存承保险种定额份数信息
	 * @param PrpCration ：传入的承保险种定额份数
	 */
	public void save(PrpCration PrpCration) throws Exception;
	
	/**
	 * 承保险种定额份数信息
	 * @param list  :传入的承保险种定额份数信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCration> list) throws Exception;
	
	/**
	 * 删除承保险种定额份数信息
	 * @param PrpCrationId ：传入的承保险种定额份数编号
	 */
	public void delete(PrpCrationId PrpCrationId) throws Exception;

	/**
	 * 更新承保险种定额份数信息
	 * @param PrpCration :传入需要更新的承保险种定额份数
	 */
	public void update(PrpCration PrpCration) throws Exception;

	/**
	 * 根据承保险种定额份数编号查询出承保险种定额份数信息
	 * @param PrpCrationId ：传入的承保险种定额份数编号
	 * @return 返回承保险种定额份数
	 */
	public PrpCration findPrpCration(PrpCrationId PrpCrationId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的承保险种定额份数页面信息
	 */
	public Page findPrpCration(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取承保险种定额份数  的列表
	 * @param queryRule 查询对象
	 * @return 包含的承保险种定额份数  的列表
	 */
	public List<PrpCration> findPrpCration(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据承保险种定额份数编号查询出承保险种定额份数信息
	 * @param certiNo ：传入的承保险种定额份数编号
	 * @return 返回承保险种定额份数
	 */
	public PrpCration findPrpCration(String certiNo) throws Exception;
}
