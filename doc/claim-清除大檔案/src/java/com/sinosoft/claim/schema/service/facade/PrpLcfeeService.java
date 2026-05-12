package com.sinosoft.claim.schema.service.facade;
/**
 * 赔款计算金额接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcfee;
import com.sinosoft.claim.schema.model.PrpLcfeeId;

public interface PrpLcfeeService {
	
	/**
	 * 保存赔款计算金额信息
	 * @param prpLcfee ：传入的赔款计算金额
	 */
	public void save(PrpLcfee prpLcfee) throws Exception;
	
	/**
	 * 赔款计算金额信息
	 * @param list  :传入的赔款计算金额信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLcfee> list) throws Exception;
	
	/**
	 * 删除赔款计算金额信息
	 * @param prpLcfeeId ：传入的赔款计算金额编号
	 */
	public void delete(PrpLcfeeId prpLcfeeId) throws Exception;

	/**
	 * 更新赔款计算金额信息
	 * @param prpLcfee :传入需要更新的赔款计算金额
	 */
	public void update(PrpLcfee prpLcfee) throws Exception;

	/**
	 * 根据赔款计算金额编号查询出赔款计算金额信息
	 * @param prpLcfeeId ：传入的赔款计算金额编号
	 * @return 返回赔款计算金额
	 */
	public PrpLcfee findPrpLcfee(PrpLcfeeId prpLcfeeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔款计算金额页面信息
	 */
	public Page findPrpLcfee(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 赔款计算金额 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 赔款计算金额 的列表
	 */
	public List<PrpLcfee> findPrpLcfee(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔款计算金额编号查询出赔款计算金额信息
	 * @param certiNo ：传入的赔款计算金额编号
	 * @return 返回赔款计算金额
	 */
	public PrpLcfee findPrpLcfee(String certiNo) throws Exception;
	
	/**
	 * 
	 * 根据计算书号删除赔款计算金额信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
}
