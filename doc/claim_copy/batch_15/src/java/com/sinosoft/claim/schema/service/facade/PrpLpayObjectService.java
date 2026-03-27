package com.sinosoft.claim.schema.service.facade;

/**
 * 支付对象接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpayObject;

public interface PrpLpayObjectService {

	/**
	 * 保存支付对象信息
	 * @param prpLpayObject ：传入的支付对象
	 */
	public void save(PrpLpayObject prpLpayObject) throws Exception;

	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void save(List<PrpLpayObject> list) throws Exception;

	/**
	 * 删除支付对象信息
	 * @param payObjectCode ：传入的支付对象编号
	 */
	public void delete(String payObjectCode) throws Exception;

	/**
	 * 更新支付对象信息
	 * @param prpLpayObject :传入需要更新的支付对象
	 */
	public void update(PrpLpayObject prpLpayObject) throws Exception;

	/**
	 * 根据支付对象编号查询出支付对象信息
	 * @param payObjectCode ：传入的支付对象编号
	 * @return 返回支付对象
	 */
	public PrpLpayObject findPrpLpayObject(String payObjectCode) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的支付对象页面信息
	 * @deprecated
	 */
	public Page findPrpLpayObject(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取List<PrpLpayObject>对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的支付对象页面信息
	 */
	public List<PrpLpayObject> findPrpLpayObject(QueryRule queryRule) throws Exception;

	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void saveOrUpdate(List<PrpLpayObject> list) throws Exception;

	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void saveOrUpdate(PrpLpayObject prpLpayObject) throws Exception;
	/**
	 * :支付对象信息
	 * @param prpLpayObjectInfoList  :传入的:支付对象信息集合
	 * @throws Exceptionuan
	 */
	public void insertAll(List<PrpLpayObject> prpLpayObjectList);
	/**
	 * 根据查询条件获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的支付对象页面信息
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception;
}
