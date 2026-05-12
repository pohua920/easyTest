package com.sinosoft.claim.schema.service.facade;
/**
 * 免赔接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLdeductible;
import com.sinosoft.claim.schema.model.PrpLdeductibleId;

public interface PrpLdeductibleService {
	
	/**
	 * 保存免赔信息
	 * @param prpLdeductible ：传入的免赔
	 */
	public void save(PrpLdeductible prpLdeductible) throws Exception;
	
	/**
	 * 免赔信息
	 * @param list  :传入的免赔信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLdeductible> list) throws Exception;
	
	/**
	 * 删除免赔信息
	 * @param prpLdeductibleId ：传入的免赔编号
	 */
	public void delete(PrpLdeductibleId prpLdeductibleId) throws Exception;

	/**
	 * 更新免赔信息
	 * @param prpLdeductible :传入需要更新的免赔
	 */
	public void update(PrpLdeductible prpLdeductible) throws Exception;

	/**
	 * 根据免赔编号查询出免赔信息
	 * @param prpLdeductibleId ：传入的免赔编号
	 * @return 返回免赔
	 */
	public PrpLdeductible findPrpLdeductible(PrpLdeductibleId prpLdeductibleId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的免赔页面信息
	 */
	public Page findPrpLdeductible(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取免赔页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的免赔页面信息  的集合
	 */
	public List<PrpLdeductible> findPrpLdeductible(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据免赔编号查询出免赔信息
	 * @param certiNo ：传入的免赔编号
	 * @return 返回免赔
	 */
	public PrpLdeductible findPrpLdeductible(String certiNo) throws Exception;
	/**
	 * 
	 * 根据计算书号删除免赔信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
}
