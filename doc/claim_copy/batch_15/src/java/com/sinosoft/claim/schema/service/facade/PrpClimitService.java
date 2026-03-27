package com.sinosoft.claim.schema.service.facade;
/**
 * PRPCLIMIT跟踪接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpClimitId;

public interface PrpClimitService {
	
	/**
	 * 保存限额/免赔跟踪信息
	 * @param prpClimit ：传入的限额/免赔跟踪
	 */
	public void save(PrpClimit prpClimit) throws Exception;
	
	/**
	 * 限额/免赔跟踪信息
	 * @param list  :传入的限额/免赔跟踪信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpClimit> list) throws Exception;
	
	/**
	 * 删除限额/免赔跟踪信息
	 * @param prpClimitId ：传入的限额/免赔跟踪编号
	 */
	public void delete(PrpClimitId prpClimitId) throws Exception;

	/**
	 * 更新限额/免赔跟踪信息
	 * @param prpClimit :传入需要更新的限额/免赔跟踪
	 */
	public void update(PrpClimit prpClimit) throws Exception;

	/**
	 * 根据限额/免赔跟踪编号查询出限额/免赔跟踪信息
	 * @param prpClimitId ：传入的限额/免赔跟踪编号
	 * @return 返回限额/免赔跟踪
	 */
	public PrpClimit findPrpClimit(PrpClimitId prpClimitId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的限额/免赔跟踪页面信息
	 */
	public Page findPrpClimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 限额/免赔的列表
	 * @param queryRule 查询对象
	 * @return 包含的 限额/免赔 的列表
	 */
	public List<PrpClimit> findPrpClimit(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据限额/免赔跟踪编号查询出限额/免赔跟踪信息
	 * @param certiNo ：传入的限额/免赔跟踪编号
	 * @return 返回限额/免赔跟踪
	 */
	public PrpClimit findPrpClimit(String certiNo) throws Exception;
	
	/**
	 * 根据查询对象获取 限额/免赔的列表
	 * @param conditions 查询条件
	 * @param damageDate 出险时间
	 * @param startDate 起保时间
	 * @return 包含的 限额/免赔 的列表
	 */
	public List<PrpClimit> findPrpClimit(String conditions,String damageDate,String startDate) throws Exception;
}
