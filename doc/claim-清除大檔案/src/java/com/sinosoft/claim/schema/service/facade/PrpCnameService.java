package com.sinosoft.claim.schema.service.facade;
/**
 * 雇员清单接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCname;
import com.sinosoft.claim.schema.model.PrpCnameId;

public interface PrpCnameService {
	
	/**
	 * 保存雇员清单信息
	 * @param PrpCname ：传入的雇员清单
	 */
	public void save(PrpCname PrpCname) throws Exception;
	
	/**
	 * 雇员清单信息
	 * @param list  :传入的雇员清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCname> list) throws Exception;
	
	/**
	 * 删除雇员清单信息
	 * @param PrpCnameId ：传入的雇员清单编号
	 */
	public void delete(PrpCnameId PrpCnameId) throws Exception;

	/**
	 * 更新雇员清单信息
	 * @param PrpCname :传入需要更新的雇员清单
	 */
	public void update(PrpCname PrpCname) throws Exception;

	/**
	 * 根据雇员清单编号查询出雇员清单信息
	 * @param PrpCnameId ：传入的雇员清单编号
	 * @return 返回雇员清单
	 */
	public PrpCname findPrpCname(PrpCnameId PrpCnameId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的雇员清单页面信息
	 */
	public Page findPrpCname(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  雇员清单的列表
	 * @param queryRule 查询对象
	 * @return 包含的 雇员清单 的列表
	 */
	public List<PrpCname> findPrpCname(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据雇员清单编号查询出雇员清单信息
	 * @param certiNo ：传入的雇员清单编号
	 * @return 返回雇员清单
	 */
	public PrpCname findPrpCname(String certiNo) throws Exception;
}
