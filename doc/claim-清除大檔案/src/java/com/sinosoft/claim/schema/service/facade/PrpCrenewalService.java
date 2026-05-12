package com.sinosoft.claim.schema.service.facade;
/**
 * 续保信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCrenewal;

public interface PrpCrenewalService {
	
	/**
	 * 续保信息
	 * @param PrpCrenewal ：传入的续保信息
	 */
	public void save(PrpCrenewal PrpCrenewal) throws Exception;
	
	/**
	 * 保存续保信息
	 * @param list  :传入的续保信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCrenewal> list) throws Exception;
	
	/**
	 * 删除续保信息信息
	 * @param policyNo ：传入的续保信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新续保信息信息
	 * @param PrpCrenewal :传入需要更新的续保信息
	 */
	public void update(PrpCrenewal PrpCrenewal) throws Exception;

	/**
	 * 根据续保信息编号查询出续保信息信息
	 * @param policyNo ：传入的续保信息编号
	 * @return 返回续保信息
	 */
	public PrpCrenewal findPrpCrenewal(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的续保信息页面信息
	 */
	public Page findPrpCrenewal(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 续保信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的续保信息  的列表
	 */
	public List<PrpCrenewal> findPrpCrenewal(QueryRule queryRule) throws Exception;
}
