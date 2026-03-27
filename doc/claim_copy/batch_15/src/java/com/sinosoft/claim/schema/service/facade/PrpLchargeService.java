package com.sinosoft.claim.schema.service.facade;
/**
 * 赔款费用接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLchargeId;

public interface PrpLchargeService {
	
	/**
	 * 保存赔款费用信息
	 * @param prpLcharge ：传入的赔款费用
	 */
	public void save(PrpLcharge prpLcharge) throws Exception;
	
	/**
	 * 赔款费用信息
	 * @param list  :传入的赔款费用信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcharge> list) throws Exception;
	
	/**
	 * 删除赔款费用信息
	 * @param prpLchargeId ：传入的赔款费用编号
	 */
	public void delete(PrpLchargeId prpLchargeId) throws Exception;

	/**
	 * 更新赔款费用信息
	 * @param prpLcharge :传入需要更新的赔款费用
	 */
	public void update(PrpLcharge prpLcharge) throws Exception;

	/**
	 * 根据赔款费用编号查询出赔款费用信息
	 * @param prpLchargeId ：传入的赔款费用编号
	 * @return 返回赔款费用
	 */
	public PrpLcharge findPrpLcharge(PrpLchargeId prpLchargeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔款费用页面信息
	 */
	public Page findPrpLcharge(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 赔款费用页面信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 赔款费用页面信息 的列表
	 */
	public List<PrpLcharge> findPrpLcharge(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔款费用编号查询出赔款费用信息
	 * @param certiNo ：传入的赔款费用编号
	 * @return 返回赔款费用
	 */
	public PrpLcharge findPrpLcharge(String compensateNo) throws Exception;
	/**
	 * 
	 * 根据计算书号删除赔款费用信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
	/**
	 * 根据查询对象获取 赔款费用页面信息 的列表
	 * @param conditions 查询条件
	 * @return 包含的 赔款费用页面信息 的列表
	 */
	public List<PrpLcharge>findByConditions(String conditions)throws Exception;
	public List<PrpLcharge> findPrpLchargeList(String compensateNo) throws Exception;
}
