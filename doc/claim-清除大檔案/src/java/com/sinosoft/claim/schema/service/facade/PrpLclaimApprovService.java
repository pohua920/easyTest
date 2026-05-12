package com.sinosoft.claim.schema.service.facade;
/**
 * 代赔数据转出确认接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimApprov;

public interface PrpLclaimApprovService {
	
	/**
	 * 代赔数据转出确认信息
	 * @param PrpLclaimApprov ：传入的代赔数据转出确认
	 */
	public void save(PrpLclaimApprov prpLclaimApprov) throws Exception;
	
	/**
	 * 保存代赔数据转出确认
	 * @param list  :传入的代赔数据转出确认集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimApprov> list) throws Exception;
	
	/**
	 * 删除代赔数据转出确认
	 * @param policyNo ：传入的代赔数据转出确认
	 */
	public void delete(String registno) throws Exception;

	/**
	 * 更新代赔数据转出确认信息
	 * @param PrpLclaimApprov :传入需要更新的代赔数据转出确认
	 */
	public void update(PrpLclaimApprov prpLclaimApprov) throws Exception;

	/**
	 * 根据代赔数据转出确认编号查询出保单代赔数据转出确认
	 * @param policyNo ：传入的代赔数据转出确认编号
	 * @return 返回代赔数据转出确认
	 */
	public PrpLclaimApprov findPrpLclaimApprov(String registno) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代赔数据转出确认页面信息
	 */
	public Page findPrpLclaimApprov(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 代赔数据转出确认信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的代赔数据转出确认信息  的列表
	 */
	public List<PrpLclaimApprov> findPrpLclaimApprov(QueryRule queryRule) throws Exception;
}
