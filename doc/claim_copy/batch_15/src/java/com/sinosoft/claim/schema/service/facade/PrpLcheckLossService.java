package com.sinosoft.claim.schema.service.facade;
/**
 * 查勘事故估损金额接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLcheckLossId;

public interface PrpLcheckLossService {
	
	/**
	 * 保存查勘事故估损金额信息
	 * @param prpLcheckLoss ：传入的查勘事故估损金额
	 */
	public void save(PrpLcheckLoss prpLcheckLoss) throws Exception;
	
	/**
	 * 查勘事故估损金额信息
	 * @param list  :传入的查勘事故估损金额信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcheckLoss> list) throws Exception;
	
	/**
	 * 删除查勘事故估损金额信息
	 * @param prpLcheckLossId ：传入的查勘事故估损金额编号
	 */
	public void delete(PrpLcheckLossId prpLcheckLossId) throws Exception;

	/**
	 * 更新查勘事故估损金额信息
	 * @param prpLcheckLoss :传入需要更新的查勘事故估损金额
	 */
	public void update(PrpLcheckLoss prpLcheckLoss) throws Exception;

	/**
	 * 根据查勘事故估损金额编号查询出查勘事故估损金额信息
	 * @param prpLcheckLossId ：传入的查勘事故估损金额编号
	 * @return 返回查勘事故估损金额
	 */
	public PrpLcheckLoss findPrpLcheckLoss(PrpLcheckLossId prpLcheckLossId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的查勘事故估损金额页面信息
	 */
	public Page findPrpLcheckLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取查勘事故估损金额页面信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的查勘事故估损金额页面信息  的列表
	 */
	public List<PrpLcheckLoss> findPrpLcheckLoss(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据查勘事故估损金额编号查询出查勘事故估损金额信息
	 * @param certiNo ：传入的查勘事故估损金额编号
	 * @return 返回查勘事故估损金额
	 */
	public PrpLcheckLoss findPrpLcheckLoss(String certiNo) throws Exception;
	/**
	 * 根据报案号删除查勘事故
	 * @param certiNo ：传入的查勘事故报案号
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 插入查勘事故信息
	 */
	public void insertAll(List<PrpLcheckLoss> prpLcheckLossList);
}
