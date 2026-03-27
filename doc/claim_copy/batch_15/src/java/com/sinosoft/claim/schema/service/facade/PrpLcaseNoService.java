package com.sinosoft.claim.schema.service.facade;
/**
 * 赔案号接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLcaseNoId;

public interface PrpLcaseNoService {
	
	/**
	 * 保存赔案号信息
	 * @param prpLcaseNo ：传入的赔案号
	 */
	public void save(PrpLcaseNo prpLcaseNo) throws Exception;
	
	/**
	 * 赔案号信息
	 * @param list  :传入的赔案号信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcaseNo> list) throws Exception;
	
	/**
	 * 删除赔案号信息
	 * @param prpLcaseNoId ：传入的赔案号编号
	 */
	public void delete(PrpLcaseNoId prpLcaseNoId) throws Exception;

	/**
	 * 更新赔案号信息
	 * @param prpLcaseNo :传入需要更新的赔案号
	 */
	public void update(PrpLcaseNo prpLcaseNo) throws Exception;

	/**
	 * 根据赔案号编号查询出赔案号信息
	 * @param prpLcaseNoId ：传入的赔案号编号
	 * @return 返回赔案号
	 */
	public PrpLcaseNo findPrpLcaseNo(PrpLcaseNoId prpLcaseNoId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔案号页面信息
	 */
	public Page findPrpLcaseNo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 赔案号 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  赔案号的集合
	 */
	public List<PrpLcaseNo> findPrpLcaseNo(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔案号编号查询出赔案号信息
	 * @param certiNo ：传入的赔案号编号
	 * @return 返回赔案号
	 */
	public PrpLcaseNo findPrpLcaseNo(String certiNo) throws Exception;
}
