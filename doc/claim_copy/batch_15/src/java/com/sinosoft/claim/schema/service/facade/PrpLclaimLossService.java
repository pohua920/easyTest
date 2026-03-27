package com.sinosoft.claim.schema.service.facade;

/**
 * 立案险别估损金额接口
 * @author 中科软
 */

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimLossId;

public interface PrpLclaimLossService {

	/**
	 * 立案险别估损金额信息
	 * @param PrpLclaimLoss ：传入的立案险别估损金额
	 */
	public void save(PrpLclaimLoss prpLclaimLoss) throws Exception;

	/**
	 * 保存立案险别估损金额信息
	 * @param list :传入的立案险别估损金额信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimLoss> list) throws Exception;

	/**
	 * 删除立案险别估损金额信息
	 * @param policyNo ：传入的立案险别估损金额编号
	 */
	public void delete(PrpLclaimLossId prpLclaimLossId) throws Exception;

	/**
	 * 更新立案险别估损金额信息
	 * @param PrpLclaimLoss :传入需要更新的立案险别估损金额
	 */
	public void update(PrpLclaimLoss prpLclaimLoss) throws Exception;

	/**
	 * 根据立案险别估损金额编号查询出立案险别估损金额信息
	 * @param policyNo ：传入的立案险别估损金额编号
	 * @return 返回立案险别估损金额
	 */
	public PrpLclaimLoss findPrpLclaimLoss(PrpLclaimLossId prpLclaimLossId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案险别估损金额页面信息
	 */
	public Page findPrpLclaimLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 立案险别估损金额 的列表
	 * @param queryRule 查询对象
	 * @return 包含的立案险别估损金额  的列表
	 */
	public List<PrpLclaimLoss> findPrpLclaimLoss(QueryRule queryRule) throws Exception;

	/**
	 * @param claimNo
	 * @throws Exception 根据立案号删除车损信息
	 */
	public void deleteByClaimNo(String claimNo) throws Exception;

	/**
	 * @param list
	 * @throws Exception 修改或者保存的方法
	 */
	public void saveOrUpdate(List<PrpLclaimLoss> list) throws Exception;

	/**
	 * @param list
	 * @throws Exception 修改或者保存的方法
	 */
	public void saveOrUpdate(PrpLclaimLoss prpLclaimLoss) throws Exception;

	/**
	 * @param claimNo
	 * @return
	 * @throws Exception 获取赔款金额
	 */
	public PrpLclaimLoss getClaimLoss(String claimNo) throws Exception;

	/**
	 * 修改数据
	 * @param condition
	 * @param claimLossList
	 */
	public void updateClaimLoss(String condition, List<PrpLclaimLoss> claimLossList) throws Exception;

	/**
	 * 修改数据
	 * @param condition 查询条件
	 * @param claimLossList
	 */
	public void updateDAAClaimLoss(String condition, List<PrpLclaimLoss> claimLossList) throws Exception;
	/**
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * 根据立案号，查询多条估损信息
	 */
	public List<PrpLclaimLoss> findPrpLclaimLoss(String claimNo) throws Exception;
	
	/***
	 * 获取调整估损金额的险别及其金额
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLclaimLoss> getClaimLossList(String claimNo)  throws Exception;

}
