package com.sinosoft.claim.schema.service.facade;
/**
 * 银行帐号与赔案接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLaccount;
import com.sinosoft.claim.schema.model.PrpLaccountId;

public interface PrpLaccountService {
	
	/**
	 * 保存银行帐号与赔案信息
	 * @param prpLaccount ：传入的银行帐号与赔案
	 */
	public void save(PrpLaccount prpLaccount) throws Exception;
	
	/**
	 * 银行帐号与赔案信息
	 * @param list  :传入的银行帐号与赔案信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLaccount> list) throws Exception;
	
	/**
	 * 删除银行帐号与赔案信息
	 * @param prpLaccountId ：传入的银行帐号与赔案编号
	 */
	public void delete(PrpLaccountId prpLaccountId) throws Exception;

	/**
	 * 更新银行帐号与赔案信息
	 * @param prpLaccount :传入需要更新的银行帐号与赔案
	 */
	public void update(PrpLaccount prpLaccount) throws Exception;

	/**
	 * 根据银行帐号与赔案编号查询出银行帐号与赔案信息
	 * @param prpLaccountId ：传入的银行帐号与赔案编号
	 * @return 返回银行帐号与赔案
	 */
	public PrpLaccount findPrpLaccount(PrpLaccountId prpLaccountId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的银行帐号与赔案页面信息
	 */
	public Page findPrpLaccount(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<PrpLaccount> findPrpLaccount(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据银行帐号与赔案编号查询出银行帐号与赔案信息
	 * @param certiNo ：传入的银行帐号与赔案编号
	 * @return 返回银行帐号与赔案
	 */
	public PrpLaccount findPrpLaccount(String registNo) throws Exception;
	/**
	 * 根据查询对象获取  银行帐号与赔案的列表
	 * @param queryRule 查询对象
	 * @return 包含的 银行帐号与赔案 的列表
	 */
	public List<PrpLaccount> findByConditions(String conditions)throws Exception;
	/**
	 * 根据查询条件获取  银行帐号与赔案的列表
	 * @param string 查询条件
	 * @return 满足条件的银行帐号与赔案 的数量
	 */
	public int getCount(String string)throws Exception;
}
