package com.sinosoft.claim.schema.service.facade;
/**
 * 特殊赔案申请原因接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLSpecialCaseReason;
import com.sinosoft.claim.schema.model.PrpLSpecialCaseReasonId;

public interface PrpLSpecialCaseReasonService {
	
	/**
	 * 保存特殊赔案申请原因信息
	 * @param prpLSpecialCaseReason ：传入的特殊赔案申请原因
	 */
	public void save(PrpLSpecialCaseReason prpLSpecialCaseReason) throws Exception;
	
	/**
	 * 特殊赔案申请原因信息
	 * @param list  :传入的特殊赔案申请原因信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLSpecialCaseReason> list) throws Exception;
	
	/**
	 * 删除特殊赔案申请原因信息
	 * @param prpLSpecialCaseReasonId ：传入的特殊赔案申请原因编号
	 */
	public void delete(PrpLSpecialCaseReasonId prpLSpecialCaseReasonId) throws Exception;

	/**
	 * 更新特殊赔案申请原因信息
	 * @param prpLSpecialCaseReason :传入需要更新的特殊赔案申请原因
	 */
	public void update(PrpLSpecialCaseReason prpLSpecialCaseReason) throws Exception;

	/**
	 * 根据特殊赔案申请原因编号查询出特殊赔案申请原因信息
	 * @param prpLSpecialCaseReasonId ：传入的特殊赔案申请原因编号
	 * @return 返回特殊赔案申请原因
	 */
	public PrpLSpecialCaseReason findPrpLSpecialCaseReason(PrpLSpecialCaseReasonId prpLSpecialCaseReasonId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的特殊赔案申请原因页面信息
	 */
	public Page findPrpLSpecialCaseReason(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取特殊赔案申请原因信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  特殊赔案申请原因信息的集合
	 */
	public List<PrpLSpecialCaseReason> findPrpLSpecialCaseReason(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据特殊赔案申请原因编号查询出特殊赔案申请原因信息
	 * @param certiNo ：传入的特殊赔案申请原因编号
	 * @return 返回特殊赔案申请原因
	 */
	public PrpLSpecialCaseReason findPrpLSpecialCaseReason(String certiNo) throws Exception;
	/**
	 * 查询出特殊赔案申请原因信息的数量
	 * @param hql ：查询hql语句
	 * @param values ：参数数组
	 * @return 返回特殊赔案申请原因信息的数量
	 */
	public long getCount(String hql,Object...values);
	/**
	 * 申请特殊赔案，，保存带jbpm工作流信息
	 * @param prpLSpecialCaseReason
	 * @throws Exception
	 */
	public void saveBpm(String businessNo,PrpLSpecialCaseReason prpLSpecialCaseReason) throws Exception;
}
