package com.sinosoft.claim.schema.service.facade;
/**
 * PrpCopyLimit跟踪接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpCopyLimit;
import com.sinosoft.claim.schema.model.PrpCopyLimitId;

public interface PrpCopyLimitService {
	
	/**
	 * 保存限额/免赔跟踪信息
	 * @param prpCopyLimit ：传入的限额/免赔跟踪
	 */
	public void save(PrpCopyLimit prpCopyLimit) throws Exception;
	
	/**
	 * 限额/免赔跟踪信息
	 * @param list  :传入的限额/免赔跟踪信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyLimit> list) throws Exception;
	
	/**
	 * 删除限额/免赔跟踪信息
	 * @param prpCopyLimitId ：传入的限额/免赔跟踪编号
	 */
	public void delete(PrpCopyLimitId prpCopyLimitId) throws Exception;

	/**
	 * 更新限额/免赔跟踪信息
	 * @param prpCopyLimit :传入需要更新的限额/免赔跟踪
	 */
	public void update(PrpCopyLimit prpCopyLimit) throws Exception;

	/**
	 * 根据限额/免赔跟踪编号查询出限额/免赔跟踪信息
	 * @param prpCopyLimitId ：传入的限额/免赔跟踪编号
	 * @return 返回限额/免赔跟踪
	 */
	public PrpCopyLimit findPrpCopyLimit(PrpCopyLimitId prpCopyLimitId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的限额/免赔跟踪页面信息
	 */
	public Page findPrpCopyLimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 限额/免赔的列表
	 * @param queryRule 查询对象
	 * @return 包含的 限额/免赔 的列表
	 */
	public List<PrpCopyLimit> findPrpCopyLimit(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据限额/免赔跟踪编号查询出限额/免赔跟踪信息
	 * @param certiNo ：传入的限额/免赔跟踪编号
	 * @return 返回限额/免赔跟踪
	 */
	public PrpCopyLimit findPrpCopyLimit(String certiNo) throws Exception;
	
	/**
	 * 根据查询对象获取 限额/免赔的列表
	 * @param conditions 查询条件
	 * @param damageDate 出险时间
	 * @param startDate 起保时间
	 * @return 包含的 限额/免赔 的列表
	 */
	public List<PrpCopyLimit> findPrpCopyLimit(String conditions,String damageDate,String startDate) throws Exception;
}
