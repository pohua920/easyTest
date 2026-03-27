package com.sinosoft.claim.schema.service.facade;
/**
 * 车险驾驶员接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLdriverId;

public interface PrpLdriverService {
	
	/**
	 * 车险驾驶员信息
	 * @param PrpLdriver ：传入的车险驾驶员
	 */
	public void save(PrpLdriver prpLdriver) throws Exception;
	
	/**
	 * 保存车险驾驶员信息
	 * @param list  :传入的车险驾驶员信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLdriver> list) throws Exception;
	
	/**
	 * 删除车险驾驶员信息
	 * @param policyNo ：传入的车险驾驶员编号
	 */
	public void delete(PrpLdriverId prpLdriverId) throws Exception;

	/**
	 * 更新车险驾驶员信息
	 * @param PrpLdriver :传入需要更新的车险驾驶员
	 */
	public void update(PrpLdriver prpLdriver) throws Exception;

	/**
	 * 根据车险驾驶员编号查询出车险驾驶员信息
	 * @param policyNo ：传入的车险驾驶员编号
	 * @return 返回车险驾驶员
	 */
	public PrpLdriver findPrpLdriver(PrpLdriverId prpLdriverId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车险驾驶员页面信息
	 */
	public Page findPrpLdriver(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取车险驾驶员页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的车险驾驶员页面信息  的集合
	 */
	public List<PrpLdriver> findPrpLdriver(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改
	 */
	public void saveOrUpdate(List<PrpLdriver> list) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改
	 */
	public void saveOrUpdate(PrpLdriver prpLdriver) throws Exception;
}
