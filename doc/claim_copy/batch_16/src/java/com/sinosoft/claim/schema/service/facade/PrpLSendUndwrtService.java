package com.sinosoft.claim.schema.service.facade;

/**
 * 送审审核菜单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLSendUndwrt;
import com.sinosoft.claim.schema.model.PrpLSendUndwrtId;

public interface PrpLSendUndwrtService {

	/**
	 * 保存送审审核菜单信息
	 * @param prpLSendUndwrt ：传入的送审审核菜单
	 */
	public void save(PrpLSendUndwrt prpLSendUndwrt) throws Exception;

	/**
	 * 送审审核菜单信息
	 * @param list :传入的送审审核菜单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLSendUndwrt> list) throws Exception;

	/**
	 * 删除送审审核菜单信息
	 * @param prpLSendUndwrtId ：传入的送审审核菜单编号
	 */
	public void delete(PrpLSendUndwrtId prpLSendUndwrtId) throws Exception;

	/**
	 * 更新送审审核菜单信息
	 * @param prpLSendUndwrt :传入需要更新的送审审核菜单
	 */
	public void update(PrpLSendUndwrt prpLSendUndwrt) throws Exception;

	/**
	 * 根据送审审核菜单编号查询出送审审核菜单信息
	 * @param prpLSendUndwrtId ：传入的送审审核菜单编号
	 * @return 返回送审审核菜单
	 */
	public PrpLSendUndwrt findPrpLSendUndwrt(PrpLSendUndwrtId prpLSendUndwrtId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的送审审核菜单页面信息
	 */
	public Page findPrpLSendUndwrt(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取送审审核菜单信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 送审审核菜单信息的集合
	 */
	public List<PrpLSendUndwrt> findPrpLSendUndwrt(QueryRule queryRule) throws Exception;

	/**
	 * 根据送审审核菜单编号查询出送审审核菜单信息
	 * @param certiNo ：传入的送审审核菜单编号
	 * @return 返回送审审核菜单
	 */
	public PrpLSendUndwrt findPrpLSendUndwrt(String certiNo) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception;
}
