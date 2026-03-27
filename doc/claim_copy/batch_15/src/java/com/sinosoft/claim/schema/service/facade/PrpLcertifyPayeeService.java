/*
 * @(#)PrpLcertifyPayeeService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;
/**
 * 领款人信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLcertifyPayeeId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @Date    <Jan 23, 2013>
 * @description 
 */
public interface PrpLcertifyPayeeService {
	/**
	 * 保存领款人信息信息
	 * @param prpLcaseNo ：传入的领款人信息
	 */
	public void save(PrpLcertifyPayee prpLcertifyPayee) throws Exception;

	
	/**
	 * 领款人信息信息
	 * @param list  :传入的领款人信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcertifyPayee> list) throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyPayee> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLcertifyPayee prpLcertifyPayee)throws Exception;
	/**
	 * 删除领款人信息信息
	 * @param prpLcaseNoId ：传入的领款人信息编号
	 */
	public void delete(PrpLcertifyPayeeId prpLcertifyPayeeId) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 根据领款人信息编号查询出领款人信息信息
	 * @param prpLcaseNoId ：传入的领款人信息编号
	 * @return 返回领款人信息
	 */
	public PrpLcertifyPayee findByPrpLcertifyPayeeId(PrpLcertifyPayeeId prpLcertifyPayeeId)throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的领款人信息页面信息
	 */
	public Page findPrpLcertifyPayee(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception;
	/**
	 * 根据查询对象获取 领款人信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  领款人信息的集合
	 */
	public List<PrpLcertifyPayee> findPrpLcertifyPayee(QueryRule queryRule) throws Exception;
	
	/**
	 * @param registNo 
	 * @return
	 * @throws Exception
	 * 根据保单号查询信息
	 */
	public List<PrpLcertifyPayee> findPrpLcertifyPayee(String registNo) throws Exception;


}
