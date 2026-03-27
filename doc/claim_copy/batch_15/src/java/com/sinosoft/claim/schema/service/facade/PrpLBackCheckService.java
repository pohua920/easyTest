package com.sinosoft.claim.schema.service.facade;
/**
 * 回勘表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLBackCheck;

public interface PrpLBackCheckService {
	
	/**
	 * 回勘表信息
	 * @param PrpLBackCheck ：传入的回勘表信息
	 */
	public void save(PrpLBackCheck prpLBackCheck) throws Exception;
	
	/**
	 * 保存回勘表信息
	 * @param list  :传入的回勘表信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLBackCheck> list) throws Exception;
	
	/**
	 * 删除回勘表信息
	 * @param policyNo ：传入的回勘表信息
	 */
	public void delete(String registno) throws Exception;

	/**
	 * 更新回勘表信息
	 * @param PrpLBackCheck :传入需要更新的回勘表信息
	 */
	public void update(PrpLBackCheck prpLBackCheck) throws Exception;

	/**
	 * 根据回勘表编号查询出保单回勘表信息
	 * @param registno ：传入的报案号
	 * @return 返回回勘表
	 */
	public PrpLBackCheck findPrpLBackCheck(String registno) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的回勘表页面信息
	 */
	public Page findPrpLBackCheck(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  的列表
	 * @param queryRule 查询对象
	 * @return 包含的  的列表
	 */
	public List<PrpLBackCheck> findPrpLBackCheck(QueryRule queryRule) throws Exception;
}
