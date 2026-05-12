/*
 * @(#)PrplcertifydirectService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;

/**
 * 索赔单证指引接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLcertifyDirectId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @Date    <Jan 23, 2013>
 * @description 
 */
public interface PrpLcertifyDirectService {
	
	/**
	 * 保存索赔单证指引信息
	 * @param prpLcaseNo ：传入的索赔单证指引
	 */
	public void save(PrpLcertifyDirect prplcertifydirect) throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyDirect> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLcertifyDirect prplcertifydirect)throws Exception;
	
	/**
	 * 索赔单证指引信息
	 * @param list  :传入的索赔单证指引信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcertifyDirect> list) throws Exception;
	/**
	 * 删除索赔单证指引信息
	 * @param prpLcaseNoId ：传入的索赔单证指引编号
	 */
	public void delete(PrpLcertifyDirectId prplcertifyDirectId) throws Exception ;
	/**
	 * @param registNo
	 * @throws Exception
	 * 更加报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception ;
	/**
	 * 根据索赔单证指引编号查询出索赔单证指引信息
	 * @param prpLcaseNoId ：传入的索赔单证指引编号
	 * @return 返回索赔单证指引
	 */
	public PrpLcertifyDirect findByPrpLcertifyDirectId(PrpLcertifyDirectId prpLcertifyDirectId)throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的索赔单证指引页面信息
	 */
	public Page findPrpLcertifyDirect(QueryRule queryRule, int pageNo, int pageSize)throws Exception ;
	/**
	 * 根据查询对象获取 索赔单证指引 的列表
	 * @param queryRule 查询对象
	 * @return 包含的  索赔单证指引的列表
	 */
	public List<PrpLcertifyDirect> findPrpLcertifyDirect(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 联合查询
	 */
	public List<PrpLcertifyDirect> findPrpLcertifyDirect(String registNo) throws Exception;

}
