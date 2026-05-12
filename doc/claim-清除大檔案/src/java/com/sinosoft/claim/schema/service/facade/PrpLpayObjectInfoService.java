package com.sinosoft.claim.schema.service.facade;
/**
 * 支付信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfoId;


public interface PrpLpayObjectInfoService {
	/**
	 * 保存支付对象信息
	 * @param prpLthirdCarLoss ：传入的支付对象
	 */
	public void save(PrpLpayObjectInfo prpLpayObjectInfo) throws Exception;
	
	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void save(List<PrpLpayObjectInfo> list) throws Exception;
	
	/**
	 * 删除支付对象信息
	 * @param PrpLpayObjectInfoId ：传入的支付对象编号
	 */
	public void delete(PrpLpayObjectInfoId prpLpayObjectInfoId) throws Exception;
	
	/**
	 * @param compensateNo
	 * @throws Exception
	 * 根据计算书号删除信息
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;

	/**
	 * 更新支付对象信息
	 * @param PrpLpayObjectInfo :传入需要更新的支付对象
	 */
	public void update(PrpLpayObjectInfo prpLpayObjectInfo) throws Exception;

	/**
	 * 根据支付对象编号查询出支付对象信息
	 * @param PrpLpayObjectInfoId ：传入的支付对象编号
	 * @return 返回支付对象
	 */
	public PrpLpayObjectInfo findPrpLpayObjectInfo(PrpLpayObjectInfoId prpLpayObjectInfoId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的支付对象页面信息
	 */
	public Page findPrpLpayObjectInfo(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取支付对象页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的支付对象页面信息  的集合
	 */
	public List<PrpLpayObjectInfo> findPrpLpayObjectInfo(QueryRule queryRule) throws Exception;

	/**
	 * :支付对象信息
	 * @param prpLpayObjectInfoList  :传入的:支付对象信息集合
	 * @throws Exceptionuan
	 */
	public void insertAll(List<PrpLpayObjectInfo> prpLpayObjectInfoList);
	
	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void saveOrUpdate(List<PrpLpayObjectInfo> list) throws Exception;
	/**
	 * 保存支付对象信息
	 * @param list:保存支付对象信息
	 */
	public void saveOrUpdate(PrpLpayObjectInfo prpLpayObjectInfo) throws Exception;
}
