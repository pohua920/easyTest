/*
 * @(#)PrpLqualityCheckService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;

/**
 * 质量评审内容表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLqualityCheckId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @Date    <Jan 23, 2013>
 * @description 
 */
public interface PrpLqualityCheckService {
	/**
	 * 保存质量评审内容信息
	 * @param prpLptext ：传入的质量评审内容
	 */
	public void save(PrpLqualityCheck prpLqualityCheck) throws Exception;

	
	/**
	 * 质量评审内容信息
	 * @param list  :传入的质量评审内容信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLqualityCheck> list) throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLqualityCheck> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLqualityCheck prpLqualityCheck)throws Exception;
	/**
	 * 删除质量评审内容信息
	 * @param prpLptextId ：传入的质量评审内容编号
	 */
	public void delete(PrpLqualityCheckId prpLqualityCheckId) throws Exception ;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 根据质量评审内容编号查询出质量评审内容信息
	 * @param prpLptextId ：传入的质量评审内容编号
	 * @return 返回质量评审内容
	 */
	public PrpLqualityCheck findByPrpLqualityCheckId(PrpLqualityCheckId prpLqualityCheckId)throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的质量评审内容页面信息
	 */
	public Page findPrpLqualityCheck(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception ;
	/**
	 * 根据查询对象获取 质量评审内容信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  质量评审内容信息的集合
	 */
	public List<PrpLqualityCheck> findPrpLqualityCheck(QueryRule queryRule) throws Exception;
	
	/**
	 * 删除质量评审内容信息
	 * @param registNo 报案号码
	 * @param qualityCheckType 业务类型
	 */
	public void delete(String registNo,String qualityCheckType) throws Exception;

}
