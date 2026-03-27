package com.sinosoft.claim.schema.service.facade;
/**
 * 人员赔付信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLpersonLossId;

public interface PrpLpersonLossService {
	
	/**
	 * 保存人员赔付信息
	 * @param prpLpersonLoss ：传入的人员赔付信息
	 */
	public void save(PrpLpersonLoss prpLpersonLoss) throws Exception;
	
	/**
	 * 人员赔付信息
	 * @param list  :传入的人员赔付信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLpersonLoss> list) throws Exception;
	
	/**
	 * 删除人员赔付信息
	 * @param prpLpersonLossId ：传入的人员赔付信息编号
	 */
	public void delete(PrpLpersonLossId prpLpersonLossId) throws Exception;

	/**
	 * 更新人员赔付信息
	 * @param prpLpersonLoss :传入需要更新的人员赔付信息
	 */
	public void update(PrpLpersonLoss prpLpersonLoss) throws Exception;

	/**
	 * 根据人员赔付信息编号查询出人员赔付信息
	 * @param prpLpersonLossId ：传入的人员赔付信息编号
	 * @return 返回人员赔付信息
	 */
	public PrpLpersonLoss findPrpLpersonLoss(PrpLpersonLossId prpLpersonLossId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人员赔付信息页面信息
	 */
	public Page findPrpLpersonLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 人员赔付信息页面信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 人员赔付信息页面信息 的集合
	 */
	public List<PrpLpersonLoss> findPrpLpersonLoss(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据人员赔付信息编号查询出人员赔付信息
	 * @param certiNo ：传入的人员赔付信息编号
	 * @return 返回人员赔付信息
	 */
	public PrpLpersonLoss findPrpLpersonLoss(String certiNo) throws Exception;
	
	/**
	 * 
	 * 根据计算书号删除人员赔付信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
	/**
	 * 根据查询条件获取 人员赔付信息页面信息 的集合
	 * @param conditions 查询条件
	 * @return 包含的 人员赔付信息页面信息 的集合
	 */
	public List<PrpLpersonLoss>findByConditions(String conditions)throws Exception;
	/**查询历史赔付人员信息
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpersonLoss>findPersonHistory(String claimNo)throws Exception;
	/**
	 * 查询人员的历史赔付信息
	 * @param policyNo
	 * @param identifyNumber
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpersonLoss>findPersonHisPaid(String policyNo,String identifyNumber)throws Exception;
}
