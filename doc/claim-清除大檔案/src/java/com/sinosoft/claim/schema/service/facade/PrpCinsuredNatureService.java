package com.sinosoft.claim.schema.service.facade;
/**
 * 被保险人详细信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCinsuredNatureId;

public interface PrpCinsuredNatureService {
	
	/**
	 * 保存被保险人详细信息信息
	 * @param prpCinsuredNature ：传入的被保险人详细信息
	 */
	public void save(PrpCinsuredNature prpCinsuredNature) throws Exception;
	
	/**
	 * 被保险人详细信息信息
	 * @param list  :传入的被保险人详细信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCinsuredNature> list) throws Exception;
	
	/**
	 * 删除被保险人详细信息信息
	 * @param prpCinsuredNatureId ：传入的被保险人详细信息编号
	 */
	public void delete(PrpCinsuredNatureId prpCinsuredNatureId) throws Exception;

	/**
	 * 更新被保险人详细信息信息
	 * @param prpCinsuredNature :传入需要更新的被保险人详细信息
	 */
	public void update(PrpCinsuredNature prpCinsuredNature) throws Exception;

	/**
	 * 根据被保险人详细信息编号查询出被保险人详细信息信息
	 * @param prpCinsuredNatureId ：传入的被保险人详细信息编号
	 * @return 返回被保险人详细信息
	 */
	public PrpCinsuredNature findPrpCinsuredNature(PrpCinsuredNatureId prpCinsuredNatureId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的被保险人详细信息页面信息
	 */
	public Page findPrpCinsuredNature(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<PrpCinsuredNature> findPrpCinsuredNature(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据保单号码，查询自然人信息
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNature(String policyNo) throws Exception;
	/**
	 * 查询自然人信息
	 * @param policyNo 保单号码
	 * @param serialNo 序号
	 * @return
	 * @throws Exception
	 */
	public PrpCinsuredNature findPrpCinsuredNature(String policyNo,int serialNo) throws Exception;
}
