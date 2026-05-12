package com.sinosoft.claim.schema.service.facade;
/**
 * 损余回收信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLreclaim;
import com.sinosoft.claim.schema.model.PrpLreclaimId;

public interface PrpLreclaimService {
	
	/**
	 * 保存损余回收信息
	 * @param prpLreclaim ：传入的损余回收信息
	 */
	public void save(PrpLreclaim prpLreclaim) throws Exception;
	
	/**
	 * 损余回收信息
	 * @param list  :传入的损余回收信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLreclaim> list) throws Exception;
	
	/**
	 * 删除损余回收信息
	 * @param prpLreclaimId ：传入的损余回收信息编号
	 */
	public void delete(PrpLreclaimId prpLreclaimId) throws Exception;

	/**
	 * 更新损余回收信息
	 * @param prpLreclaim :传入需要更新的损余回收信息
	 */
	public void update(PrpLreclaim prpLreclaim) throws Exception;

	/**
	 * 根据损余回收信息编号查询出损余回收信息
	 * @param prpLreclaimId ：传入的损余回收信息编号
	 * @return 返回损余回收信息
	 */
	public PrpLreclaim findPrpLreclaim(PrpLreclaimId prpLreclaimId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的损余回收信息页面信息
	 */
	public Page findPrpLreclaim(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取损余回收信息信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  损余回收信息信息的集合
	 */
	public List<PrpLreclaim> findPrpLreclaim(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据损余回收信息编号查询出损余回收信息
	 * @param certiNo ：传入的损余回收信息编号
	 * @return 返回损余回收信息
	 */
	public PrpLreclaim findPrpLreclaim(String certiNo) throws Exception;
}
