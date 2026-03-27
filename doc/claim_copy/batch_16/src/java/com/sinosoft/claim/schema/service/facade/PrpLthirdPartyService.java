package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔车辆接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdPartyId;

public interface PrpLthirdPartyService {
	
	/**
	 * 保存理赔车辆信息
	 * @param prpLthirdParty ：传入的理赔车辆
	 */
	public void save(PrpLthirdParty prpLthirdParty) throws Exception;
	
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	public void save(List<PrpLthirdParty> list) throws Exception;
	
	/**
	 * 删除理赔车辆信息
	 * @param prpLthirdPartyId ：传入的理赔车辆编号
	 */
	public void delete(PrpLthirdPartyId prpLthirdPartyId) throws Exception;

	/**
	 * 更新理赔车辆信息
	 * @param prpLthirdParty :传入需要更新的理赔车辆
	 */
	public void update(PrpLthirdParty prpLthirdParty) throws Exception;

	/**
	 * 根据理赔车辆编号查询出理赔车辆信息
	 * @param prpLthirdPartyId ：传入的理赔车辆编号
	 * @return 返回理赔车辆
	 */
	public PrpLthirdParty findPrpLthirdParty(PrpLthirdPartyId prpLthirdPartyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔车辆页面信息
	 */
	public Page findPrpLthirdParty(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取理赔车辆信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  理赔车辆信息的集合
	 */
	public List<PrpLthirdParty> findPrpLthirdParty(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	public void saveOrUpdate(List<PrpLthirdParty> list) throws Exception;
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息
	 */
	public void saveOrUpdate(PrpLthirdParty prpLthirdParty) throws Exception;
	/**
	 * 保存理赔车辆信息
	 * @param list:保存理赔车辆信息集合
	 */
	public void insertAll(List<PrpLthirdParty> prpLthirdPartyList);
}
