package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔节点状态接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;

public interface PrpLclaimStatusService {
	
	/**
	 * 理赔节点状态信息
	 * @param PrpLclaimStatus ：传入的理赔节点状态
	 */
	public void save(PrpLclaimStatus prpLclaimStatus) throws Exception;
	
	/**
	 * 保存理赔节点状态信息
	 * @param list  :传入的理赔节点状态信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimStatus> list) throws Exception;
	
	/**
	 * 删除理赔节点状态信息
	 * @param policyNo ：传入的理赔节点状态编号
	 */
	public void delete(PrpLclaimStatusId prpLclaimStatusId) throws Exception;
	/**
	 * @param registNo
	 * @param nodeType
	 * @throws Exception
	 * 根据报案号和节点信息，删除所有的状态
	 */
	public void deleteByRegistNo(String registNo,String nodeType) throws Exception;

	/**
	 * 更新理赔节点状态信息
	 * @param PrpLclaimStatus :传入需要更新的理赔节点状态
	 */
	public void update(PrpLclaimStatus prpLclaimStatus) throws Exception;

	/**
	 * 根据理赔节点状态编号查询出理赔节点状态信息
	 * @param policyNo ：传入的理赔节点状态编号
	 * @return 返回理赔节点状态
	 */
	public PrpLclaimStatus findPrpLclaimStatus(PrpLclaimStatusId prpLclaimStatusId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔节点状态页面信息
	 */
	public Page findPrpLclaimStatus(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取理赔节点状态  的列表
	 * @param queryRule 查询对象
	 * @return 包含的理赔节点状态  的列表
	 */
	public List<PrpLclaimStatus> findPrpLclaimStatus(QueryRule queryRule) throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLclaimStatus> list)throws Exception;
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLclaimStatus prpLclaimStatus)throws Exception;
	/**保存对象，不同步到session中
	 * @param prpLclaimStatus
	 * @throws Exception
	 */
	public void saveOrMerge(PrpLclaimStatus prpLclaimStatus)throws Exception;
}
