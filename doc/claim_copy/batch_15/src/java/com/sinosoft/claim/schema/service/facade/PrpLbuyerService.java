package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLbuyer;
import com.sinosoft.claim.schema.model.PrpLbuyerId;

public interface PrpLbuyerService {
	/**
	 * 保存买受人信息
	 * @param prpLbuyer ：传入的买受人
	 */
	public void save(PrpLbuyer prpLbuyer) throws Exception;
	
	/**
	 * 保存买受人信息
	 * @param list:保存买受人信息
	 */
	public void save(List<PrpLbuyer> list) throws Exception;
	
	/**
	 * 删除买受人信息
	 * @param prpLbuyerId ：传入的买受人编号
	 */
	public void delete(PrpLbuyerId prpLbuyerId) throws Exception;

	/**
	 * 更新买受人信息
	 * @param prpLbuyer :传入需要更新的买受人
	 */
	public void update(PrpLbuyer prpLbuyer) throws Exception;

	/**
	 * 根据买受人编号查询出买受人信息
	 * @param prpLbuyerId ：传入的买受人编号
	 * @return 返回买受人
	 */
	public PrpLbuyer findPrpLbuyer(PrpLbuyerId prpLbuyerId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的买受人页面信息
	 */
	public Page findPrpLbuyer(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取买受人信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  买受人信息的集合
	 */
	public List<PrpLbuyer> findPrpLbuyer(QueryRule queryRule) throws Exception;
	/**
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询所有信息
	 */
	public List<PrpLbuyer> findByCompensateNo(String compensateNo)throws Exception;
	/**
	 * @param compensateNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
	/**
	 * 保存买受人信息
	 * @param list:保存买受人信息
	 */
	public void saveOrUpdate(List<PrpLbuyer> list) throws Exception;
	/**
	 * 保存买受人信息
	 * @param list:保存买受人信息
	 */
	public void saveOrUpdate(PrpLbuyer prpLbuyer) throws Exception;

	public void insertAll(List<PrpLbuyer> prpLbuyerList);
}
