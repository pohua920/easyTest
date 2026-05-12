package com.sinosoft.claim.schema.service.facade;
/**
 * 人员险种代码表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLPersonLimit;
import com.sinosoft.claim.schema.model.PrpLPersonLimitId;

public interface PrpLPersonLimitService {
	
	/**
	 * 保存人员险种代码信息
	 * @param prpLPersonLimit ：传入的人员险种代码
	 */
	public void save(PrpLPersonLimit prpLPersonLimit) throws Exception;
	
	/**
	 * 人员险种代码信息
	 * @param list  :传入的人员险种代码信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLPersonLimit> list) throws Exception;
	
	/**
	 * 删除人员险种代码信息
	 * @param prpLPersonLimitId ：传入的人员险种代码编号
	 */
	public void delete(PrpLPersonLimitId prpLPersonLimitId) throws Exception;

	/**
	 * 更新人员险种代码信息
	 * @param prpLPersonLimit :传入需要更新的人员险种代码
	 */
	public void update(PrpLPersonLimit prpLPersonLimit) throws Exception;

	/**
	 * 根据人员险种代码编号查询出人员险种代码信息
	 * @param prpLPersonLimitId ：传入的人员险种代码编号
	 * @return 返回人员险种代码
	 */
	public PrpLPersonLimit findPrpLPersonLimit(PrpLPersonLimitId prpLPersonLimitId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人员险种代码页面信息
	 */
	public Page findPrpLPersonLimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 人员险种代码页面信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 人员险种代码页面信息 的集合
	 */
	public List<PrpLPersonLimit> findPrpLPersonLimit(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据人员险种代码编号查询出人员险种代码信息
	 * @param certiNo ：传入的人员险种代码编号
	 * @return 返回人员险种代码
	 */
	public PrpLPersonLimit findPrpLPersonLimit(String certiNo) throws Exception;
}
