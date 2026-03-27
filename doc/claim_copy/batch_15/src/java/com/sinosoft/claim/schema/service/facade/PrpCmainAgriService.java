package com.sinosoft.claim.schema.service.facade;
/**
 * 农业险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainAgri;

public interface PrpCmainAgriService {
	
	/**
	 * 农业险保单信息信息
	 * @param PrpCmainAgri ：传入的农业险保单信息
	 */
	public void save(PrpCmainAgri PrpCmainAgri) throws Exception;
	
	/**
	 * 保存农业险保单信息
	 * @param list  :传入的农业险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainAgri> list) throws Exception;
	
	/**
	 * 删除农业险保单信息信息
	 * @param policyNo ：传入的农业险保单信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新农业险保单信息信息
	 * @param PrpCmainAgri :传入需要更新的农业险保单信息
	 */
	public void update(PrpCmainAgri PrpCmainAgri) throws Exception;

	/**
	 * 根据农业险保单信息编号查询出农业险保单信息信息
	 * @param policyNo ：传入的农业险保单信息编号
	 * @return 返回农业险保单信息
	 */
	public PrpCmainAgri findPrpCmainAgri(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的农业险保单信息页面信息
	 */
	public Page findPrpCmainAgri(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  农业险保单信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的 农业险保单信息 的列表
	 */
	public List<PrpCmainAgri> findPrpCmainAgri(QueryRule queryRule) throws Exception;
}
