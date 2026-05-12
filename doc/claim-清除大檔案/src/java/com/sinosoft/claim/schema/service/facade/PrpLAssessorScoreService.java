package com.sinosoft.claim.schema.service.facade;
/**
 * 公估师评估信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLAssessorScore;
import com.sinosoft.claim.schema.model.PrpLAssessorScoreId;

public interface PrpLAssessorScoreService {
	
	/**
	 * 保存公估师评估信息
	 * @param prpLAssessorScore ：传入的公估师评估信息
	 */
	public void save(PrpLAssessorScore prpLAssessorScore) throws Exception;
	
	/**
	 * 公估师评估信息
	 * @param list  :传入的公估师评估信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLAssessorScore> list) throws Exception;
	
	/**
	 * 删除公估师评估信息
	 * @param prpLAssessorScoreId ：传入的公估师评估信息编号
	 */
	public void delete(PrpLAssessorScoreId prpLAssessorScoreId) throws Exception;

	/**
	 * 更新公估师评估信息
	 * @param prpLAssessorScore :传入需要更新的公估师评估信息
	 */
	public void update(PrpLAssessorScore prpLAssessorScore) throws Exception;

	/**
	 * 根据公估师评估信息编号查询出公估师评估信息
	 * @param prpLAssessorScoreId ：传入的公估师评估信息编号
	 * @return 返回PRPLASSESSORSCORE
	 */
	public PrpLAssessorScore findPrpLAssessorScore(PrpLAssessorScoreId prpLAssessorScoreId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的公估师评估信息页面信息
	 */
	public Page findPrpLAssessorScore(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取公估师评估信息页面信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的公估师评估信息页面信息  的列表
	 */
	public List<PrpLAssessorScore> findPrpLAssessorScore(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据公估师评估信息编号查询出公估师评估信息
	 * @param certiNo ：传入的公估师评估信息编号
	 * @return 返回公估师评估信息
	 */
	public PrpLAssessorScore findPrpLAssessorScore(String certiNo) throws Exception;
	/**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(String conditions) throws Exception;
}
