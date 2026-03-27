package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔冲减保额接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLendor;
import com.sinosoft.claim.schema.model.PrpLendorId;

public interface PrpLendorService {
	
	/**
	 * 保存理赔冲减保额信息
	 * @param prpLendor ：传入的理赔冲减保额
	 */
	public void save(PrpLendor prpLendor) throws Exception;
	
	/**
	 * 理赔冲减保额信息
	 * @param list  :传入的理赔冲减保额信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLendor> list) throws Exception;
	
	/**
	 * 删除理赔冲减保额信息
	 * @param prpLendorId ：传入的理赔冲减保额编号
	 */
	public void delete(PrpLendorId prpLendorId) throws Exception;

	/**
	 * 更新理赔冲减保额信息
	 * @param prpLendor :传入需要更新的理赔冲减保额
	 */
	public void update(PrpLendor prpLendor) throws Exception;

	/**
	 * 根据理赔冲减保额编号查询出理赔冲减保额信息
	 * @param prpLendorId ：传入的理赔冲减保额编号
	 * @return 返回理赔冲减保额
	 */
	public PrpLendor findPrpLendor(PrpLendorId prpLendorId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔冲减保额页面信息
	 */
	public Page findPrpLendor(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  理赔冲减保额页面信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的 理赔冲减保额页面信息 的集合
	 */
	public List<PrpLendor> findPrpLendor(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据理赔冲减保额编号查询出理赔冲减保额信息
	 * @param certiNo ：传入的理赔冲减保额编号
	 * @return 返回理赔冲减保额
	 */
	public PrpLendor findPrpLendor(String certiNo) throws Exception;
	
	/**
	 * 
	 * 根据计算书号删除理赔冲减保额信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
}
