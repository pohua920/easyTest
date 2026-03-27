package com.sinosoft.claim.schema.service.facade;
/**
 * 人伤跟踪接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopenCoverBal;
import com.sinosoft.claim.schema.model.PrpCopenCoverBalId;

public interface PrpCopenCoverBalService {
	
	/**
	 * 保存人伤跟踪信息
	 * @param PrpCopenCoverBal ：传入的人伤跟踪
	 */
	public void save(PrpCopenCoverBal PrpCopenCoverBal) throws Exception;
	
	/**
	 * 人伤跟踪信息
	 * @param list  :传入的人伤跟踪信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopenCoverBal> list) throws Exception;
	
	/**
	 * 删除人伤跟踪信息
	 * @param PrpCopenCoverBalId ：传入的人伤跟踪编号
	 */
	public void delete(PrpCopenCoverBalId PrpCopenCoverBalId) throws Exception;

	/**
	 * 更新人伤跟踪信息
	 * @param PrpCopenCoverBal :传入需要更新的人伤跟踪
	 */
	public void update(PrpCopenCoverBal PrpCopenCoverBal) throws Exception;

	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param PrpCopenCoverBalId ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCopenCoverBal findPrpCopenCoverBal(PrpCopenCoverBalId PrpCopenCoverBalId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人伤跟踪页面信息
	 */
	public Page findPrpCopenCoverBal(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取人伤跟踪  的列表
	 * @param queryRule 查询对象
	 * @return 包含的人伤跟踪 的列表
	 */
	public List<PrpCopenCoverBal> findPrpCopenCoverBal(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCopenCoverBal findPrpCopenCoverBal(String certiNo) throws Exception;
}
