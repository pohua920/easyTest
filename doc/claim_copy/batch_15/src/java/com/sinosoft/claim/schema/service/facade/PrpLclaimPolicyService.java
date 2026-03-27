package com.sinosoft.claim.schema.service.facade;
/**
 * 立案保单清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimPolicy;
import com.sinosoft.claim.schema.model.PrpLclaimPolicyId;

public interface PrpLclaimPolicyService {
	
	/**
	 * 保存立案保单清单信息
	 * @param prpLclaimPolicy ：传入的立案保单清单
	 */
	public void save(PrpLclaimPolicy prpLclaimPolicy) throws Exception;
	
	/**
	 * 立案保单清单信息
	 * @param list  :传入的立案保单清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimPolicy> list) throws Exception;
	
	/**
	 * 删除立案保单清单信息
	 * @param prpLclaimPolicyId ：传入的立案保单清单编号
	 */
	public void delete(PrpLclaimPolicyId prpLclaimPolicyId) throws Exception;

	/**
	 * 更新立案保单清单信息
	 * @param prpLclaimPolicy :传入需要更新的立案保单清单
	 */
	public void update(PrpLclaimPolicy prpLclaimPolicy) throws Exception;

	/**
	 * 根据立案保单清单编号查询出立案保单清单信息
	 * @param prpLclaimPolicyId ：传入的立案保单清单编号
	 * @return 返回立案保单清单
	 */
	public PrpLclaimPolicy findPrpLclaimPolicy(PrpLclaimPolicyId prpLclaimPolicyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案保单清单页面信息
	 */
	public Page findPrpLclaimPolicy(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取立案保单清单信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的立案保单清单信息   的列表
	 */
	public List<PrpLclaimPolicy> findPrpLclaimPolicy(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据立案保单清单编号查询出立案保单清单信息
	 * @param certiNo ：传入的立案保单清单编号
	 * @return 返回立案保单清单
	 */
	public PrpLclaimPolicy findPrpLclaimPolicy(String certiNo) throws Exception;
}
