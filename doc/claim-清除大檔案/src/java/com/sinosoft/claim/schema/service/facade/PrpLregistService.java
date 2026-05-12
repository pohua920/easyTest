package com.sinosoft.claim.schema.service.facade;
/**
 * 报案接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregist;

public interface PrpLregistService {
	
	/**
	 * 保存报案信息
	 * @param prpLregist ：传入的报案
	 */
	public void save(PrpLregist prpLregist) throws Exception;
	
	/**
	 * 保存报案信息
	 * @param list:保存报案信息
	 */
	public void save(List<PrpLregist> list) throws Exception;
	
	/**
	 * 删除报案信息
	 * @param registNo ：传入的报案编号
	 */
	public void delete(String registNo) throws Exception;

	/**
	 * 更新报案信息
	 * @param prpLregist :传入需要更新的报案
	 */
	public void update(PrpLregist prpLregist) throws Exception;

	/**
	 * 根据报案编号查询出报案信息
	 * @param registNo ：传入的报案编号
	 * @return 返回报案
	 */
	public PrpLregist findPrpLregist(String registNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案页面信息
	 */
	public Page findPrpLregist(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取报案信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  报案信息的集合
	 */
	public List<PrpLregist> findPrpLregist(QueryRule queryRule) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 根据sql语句条件查询
	 */
	public List<PrpLregist> findByConditions(String conditions)throws Exception;
	/**
	 * @param registNo
	 * @return
	 */
	public boolean isExist(String registNo)throws Exception;
	/**
	 * @param prpLregist
	 * @throws Exception
	 * 保存或修改，
	 */
	public void saveOrUpdate(PrpLregist prpLregist)throws Exception;
	
	/**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(String conditions) throws Exception;
	/**
	 * @param policyNo
	 * @return
	 * @throws Exception
	 * 查找少数的字段
	 */
	public List<PrpLregist> findSamePolicyRegist(String policyNo) throws Exception;

	Page findPrpLregist(String conditions, int pageNo, int pageSize) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 * conditions如果conditions後面有有别名，会出错，prplregist.registno='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	public Page findByConditions(String conditions,int pageNo,int pageSize) throws Exception;
	
	/**
	 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLregist> findSameLicensenoRegist(String policyNo) throws Exception ;
}
