package com.sinosoft.claim.schema.service.facade;
/**
 * 计算书免赔条件接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLdeductCondId;

public interface PrpLdeductCondService {
	
	/**
	 * 保存计算书免赔条件信息
	 * @param prpLdeductCond ：传入的计算书免赔条件
	 */
	public void save(PrpLdeductCond prpLdeductCond) throws Exception;
	
	/**
	 * 计算书免赔条件信息
	 * @param list  :传入的计算书免赔条件信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLdeductCond> list) throws Exception;
	
	/**
	 * 删除计算书免赔条件信息
	 * @param prpLdeductCondId ：传入的计算书免赔条件编号
	 */
	public void delete(PrpLdeductCondId prpLdeductCondId) throws Exception;

	/**
	 * 更新计算书免赔条件信息
	 * @param prpLdeductCond :传入需要更新的计算书免赔条件
	 */
	public void update(PrpLdeductCond prpLdeductCond) throws Exception;

	/**
	 * 根据计算书免赔条件编号查询出计算书免赔条件信息
	 * @param prpLdeductCondId ：传入的计算书免赔条件编号
	 * @return 返回计算书免赔条件
	 */
	public PrpLdeductCond findPrpLdeductCond(PrpLdeductCondId prpLdeductCondId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的计算书免赔条件页面信息
	 */
	public Page findPrpLdeductCond(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  计算书免赔条件页面信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的  计算书免赔条件页面信息的集合
	 */
	public List<PrpLdeductCond> findPrpLdeductCond(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据计算书免赔条件编号查询出计算书免赔条件信息
	 * @param certiNo ：传入的计算书免赔条件编号
	 * @return 返回计算书免赔条件
	 */
	public PrpLdeductCond findPrpLdeductCond(String certiNo) throws Exception;
	/**
	 * 
	 * 根据计算书号删除免赔条件信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
}
