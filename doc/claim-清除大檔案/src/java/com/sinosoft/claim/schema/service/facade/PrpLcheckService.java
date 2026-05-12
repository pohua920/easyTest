package com.sinosoft.claim.schema.service.facade;
/**
 * 查勘/代查勘接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;

public interface PrpLcheckService {
	
	/**
	 * 保存查勘/代查勘信息
	 * @param prpLcheck ：传入的查勘/代查勘
	 */
	public void save(PrpLcheck prpLcheck) throws Exception;
	
	/**
	 * 查勘/代查勘信息
	 * @param list  :传入的查勘/代查勘信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcheck> list) throws Exception;
	
	/**
	 * 删除查勘/代查勘信息
	 * @param prpLcheckId ：传入的查勘/代查勘编号
	 */
	public void delete(PrpLcheckId prpLcheckId) throws Exception;

	/**
	 * 更新查勘/代查勘信息
	 * @param prpLcheck :传入需要更新的查勘/代查勘
	 */
	public void update(PrpLcheck prpLcheck) throws Exception;

	/**
	 * 根据查勘/代查勘编号查询出查勘/代查勘信息
	 * @param prpLcheckId ：传入的查勘/代查勘编号
	 * @return 返回查勘/代查勘
	 */
	public PrpLcheck findPrpLcheck(PrpLcheckId prpLcheckId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的查勘/代查勘页面信息
	 */
	public Page findPrpLcheck(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取查勘/代查勘页面信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的  查勘/代查勘页面信息的列表
	 */
	public List<PrpLcheck> findPrpLcheck(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据查勘/代查勘编号查询出查勘/代查勘信息
	 * @param certiNo ：传入的查勘/代查勘编号
	 * @return 返回查勘/代查勘
	 */
	public PrpLcheck findPrpLcheck(String certiNo) throws Exception;
	/**
	 * 判断是否查勘
	 * @param registNo
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String registNo)throws Exception;
}
