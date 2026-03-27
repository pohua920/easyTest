package com.sinosoft.claim.schema.service.facade;
/**
 * 通赔任务信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLgeneralClaimTask;

public interface PrpLgeneralClaimTaskService {
	
	/**
	 * 通赔任务信息
	 * @param PrpLgeneralClaimTask ：传入的通赔任务信息
	 */
	public void save(PrpLgeneralClaimTask prpLgeneralClaimTask) throws Exception;
	
	/**
	 * 保存通赔任务信息
	 * @param list  :传入的通赔任务信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLgeneralClaimTask> list) throws Exception;
	
	/**
	 * 删除通赔任务信息
	 * @param policyNo ：传入的通赔任务信息
	 */
	public void delete(String serialNo) throws Exception;

	/**
	 * 更新通赔任务信息信息
	 * @param PrpLgeneralClaimTask :传入需要更新的通赔任务信息
	 */
	public void update(PrpLgeneralClaimTask prpLgeneralClaimTask) throws Exception;

	/**
	 * 根据通赔任务信息编号查询出保单通赔任务信息
	 * @param policyNo ：传入的通赔任务信息编号
	 * @return 返回通赔任务信息
	 */
	public PrpLgeneralClaimTask findPrpLgeneralClaimTask(String serialNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的通赔任务信息页面信息
	 */
	public Page findPrpLgeneralClaimTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 通赔任务信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的通赔任务信息  的集合
	 */
	public List<PrpLgeneralClaimTask> findPrpLgeneralClaimTask(QueryRule queryRule) throws Exception;
	/**
     * 通过表名取得该表的sequence的nextval
     * @param tableName
     * @return
     * @throws Exception
     */
    public long getSeqNextVal(String tableName) throws Exception;
	/**
	 * 根据查询对象获取 通赔任务信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的通赔任务信息  的集合
	 */
    public Page findPrpLgeneralClaimTask(String conditions, int pageNo, int pageSize) throws Exception;
	/**
	 * 删除通赔任务信息
	 * @param registNo ：传入的业务号
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
}
