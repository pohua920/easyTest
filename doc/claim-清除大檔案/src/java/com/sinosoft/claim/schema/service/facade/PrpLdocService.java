package com.sinosoft.claim.schema.service.facade;
/**
 * 索赔单证信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdocId;

public interface PrpLdocService {
	
	/**
	 * 索赔单证信息信息
	 * @param PrpLdoc ：传入的索赔单证信息
	 */
	public void save(PrpLdoc prpLdoc) throws Exception;
	
	/**
	 * 保存索赔单证信息
	 * @param list  :传入的索赔单证信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLdoc> list) throws Exception;
	
	/**
	 * 删除索赔单证信息
	 * @param policyNo ：传入的索赔单证信息编号
	 */
	public void delete(PrpLdocId prpLdocId) throws Exception;

	/**
	 * 更新索赔单证信息
	 * @param PrpLdoc :传入需要更新的索赔单证信息
	 */
	public void update(PrpLdoc prpLdoc) throws Exception;

	/**
	 * 根据索赔单证信息编号查询出索赔单证信息
	 * @param policyNo ：传入的索赔单证信息编号
	 * @return 返回索赔单证信息
	 */
	public PrpLdoc findPrpLdoc(PrpLdocId prpLdocId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的索赔单证信息页面信息
	 */
	public Page findPrpLdoc(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 索赔单证信息页面信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的索赔单证信息页面信息  的集合
	 */
	public List<PrpLdoc> findPrpLdoc(QueryRule queryRule) throws Exception;
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除
	 */
	public void deleteByClaimNo(String claimNo)throws Exception;
	/**
	 * @param prpLdoc
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(PrpLdoc prpLdoc)throws Exception;
	/**
	 * @param prpLdoc
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(List<PrpLdoc> list)throws Exception;
	
}
