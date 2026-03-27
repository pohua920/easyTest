package com.sinosoft.claim.schema.service.facade;
/**
 * 投保车辆扩展信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCitemCarExtId;

public interface PrpCitemCarExtService {
	
	/**
	 * 保存投保车辆扩展信息信息
	 * @param PrpCitemCarExt ：传入的投保车辆扩展信息
	 */
	public void save(PrpCitemCarExt PrpCitemCarExt) throws Exception;
	
	/**
	 * 投保车辆扩展信息信息
	 * @param list  :传入的投保车辆扩展信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemCarExt> list) throws Exception;
	
	/**
	 * 删除投保车辆扩展信息信息
	 * @param PrpCitemCarExtId ：传入的投保车辆扩展信息编号
	 */
	public void delete(PrpCitemCarExtId PrpCitemCarExtId) throws Exception;

	/**
	 * 更新投保车辆扩展信息信息
	 * @param PrpCitemCarExt :传入需要更新的投保车辆扩展信息
	 */
	public void update(PrpCitemCarExt PrpCitemCarExt) throws Exception;

	/**
	 * 根据投保车辆扩展信息编号查询出投保车辆扩展信息信息
	 * @param PrpCitemCarExtId ：传入的投保车辆扩展信息编号
	 * @return 返回投保车辆扩展信息
	 */
	public PrpCitemCarExt findPrpCitemCarExt(PrpCitemCarExtId PrpCitemCarExtId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的投保车辆扩展信息页面信息
	 */
	public Page findPrpCitemCarExt(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  投保车辆扩展信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的 投保车辆扩展信息 的列表
	 */
	public List<PrpCitemCarExt> findPrpCitemCarExt(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据投保车辆扩展信息编号查询出投保车辆扩展信息信息
	 * @param certiNo ：传入的投保车辆扩展信息编号
	 * @return 返回投保车辆扩展信息
	 */
	public PrpCitemCarExt findPrpCitemCarExt(String certiNo) throws Exception;
	/**
	 * 根据保单号获取  投保车辆扩展信息的列表
	 * @param policyNo 保单号
	 * @return 包含的 投保车辆扩展信息 的列表
	 */
	public List<PrpCitemCarExt> findByPolicyNo(String policyNo)throws Exception;
}
