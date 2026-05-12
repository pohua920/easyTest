package com.sinosoft.claim.schema.service.facade;
/**
 * 简易赔案接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLquickCase;

public interface PrpLquickCaseService {
	
	/**
	 * 保存简易赔案信息
	 * @param prpLpersonTrace ：传入的简易赔案
	 */
	public void save(PrpLquickCase prpLpersonTrace) throws Exception;
	
	/**
	 * 保存简易赔案信息
	 * @param list:保存简易赔案信息
	 */
	public void save(List<PrpLquickCase> list) throws Exception;
	
	/**
	 * 删除简易赔案信息
	 * @param registNo ：传入的简易赔案编号
	 */
	public void delete(String registNo) throws Exception;

	/**
	 * 更新简易赔案信息
	 * @param prpLpersonTrace :传入需要更新的简易赔案
	 */
	public void update(PrpLquickCase prpLpersonTrace) throws Exception;

	/**
	 * 根据简易赔案编号查询出简易赔案信息
	 * @param registNo ：传入的简易赔案编号
	 * @return 返回简易赔案
	 */
	public PrpLquickCase findPrpLquickCase(String registNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的简易赔案页面信息
	 */
	public Page findPrpLquickCase(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取简易赔案信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 简易赔案信息 的集合
	 */
	public List<PrpLquickCase> findPrpLquickCase(QueryRule queryRule) throws Exception;
}
