package com.sinosoft.claim.schema.service.facade;
/**
 * PRPCENGAGE接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCengageId;

public interface PrpCengageService {
	
	/**
	 * 保存特别约定信息
	 * @param prpCengage ：传入的特别约定信息
	 */
	public void save(PrpCengage prpCengage) throws Exception;
	
	/**
	 * PRPCENGAGE信息
	 * @param list  :传入的特别约定信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCengage> list) throws Exception;
	
	/**
	 * 删除PRPCENGAGE信息
	 * @param prpCengageId ：传入的特别约定编号
	 */
	public void delete(PrpCengageId prpCengageId) throws Exception;

	/**
	 * 更新PRPCENGAGE信息
	 * @param prpCengage :传入需要更新的特别约定信息
	 */
	public void update(PrpCengage prpCengage) throws Exception;

	/**
	 * 根据PRPCENGAGE编号查询出特别约定信息
	 * @param prpCengageId ：传入的特别约定编号
	 * @return 返回特别约定信息
	 */
	public PrpCengage findPrpCengage(PrpCengageId prpCengageId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的特别约定页面信息
	 */
	public Page findPrpCengage(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取特别约定的列表
	 * @param queryRule 查询对象
	 * @return 包含的特别约定的列表
	 */
	public List<PrpCengage> findPrpCengage(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据特别约定编号查询出特别约定信息
	 * @param certiNo ：传入的特别约定编号
	 * @return 返回特别约定信息
	 */
	public PrpCengage findPrpCengage(String certiNo) throws Exception;
}
