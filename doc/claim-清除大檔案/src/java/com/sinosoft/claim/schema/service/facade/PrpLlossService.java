package com.sinosoft.claim.schema.service.facade;
/**
 * 赔付标的信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLlossId;

public interface PrpLlossService {
	
	/**
	 * 保存赔付标的信息
	 * @param prpLloss ：传入的赔付标的信息
	 */
	public void save(PrpLloss prpLloss) throws Exception;
	
	/**
	 * 赔付标的信息
	 * @param list  :传入的赔付标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLloss> list) throws Exception;
	
	/**
	 * 删除赔付标的信息
	 * @param prpLlossId ：传入的赔付标的信息编号
	 */
	public void delete(PrpLlossId prpLlossId) throws Exception;

	/**
	 * 更新赔付标的信息
	 * @param prpLloss :传入需要更新的赔付标的信息
	 */
	public void update(PrpLloss prpLloss) throws Exception;

	/**
	 * 根据赔付标的信息编号查询出赔付标的信息
	 * @param prpLlossId ：传入的赔付标的信息编号
	 * @return 返回赔付标的信息
	 */
	public PrpLloss findPrpLloss(PrpLlossId prpLlossId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔付标的信息页面信息
	 */
	public Page findPrpLloss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取赔付标的信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的赔付标的信息  的集合
	 */
	public List<PrpLloss> findPrpLloss(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔付标的信息编号查询出赔付标的信息
	 * @param certiNo ：传入的赔付标的信息编号
	 * @return 返回赔付标的信息
	 */
	public PrpLloss findPrpLloss(String certiNo) throws Exception;
	
	/**
	 * 
	 * 根据计算书号删除赔付标的信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
	/**
	 * 根据查询条件获取赔付标的信息  的集合
	 * @param conditions 查询条件
	 * @return 包含的赔付标的信息  的集合
	 */
	public List<PrpLloss>findByConditions(String conditions)throws Exception;
	/**
	 * 查询保单的最大赔付额
	 * @param policyNo
	 * @param kindCode
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public Double findLossHisPaid(PrpLloss prpLloss)throws Exception;
}
