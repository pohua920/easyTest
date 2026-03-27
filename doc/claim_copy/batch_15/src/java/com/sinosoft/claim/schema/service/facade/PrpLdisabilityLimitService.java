package com.sinosoft.claim.schema.service.facade;
/**
 * 伤残等级信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLdisabilityLimit;
import com.sinosoft.claim.schema.model.PrpLdisabilityLimitId;

public interface PrpLdisabilityLimitService {

	
	/**保存伤残等级信息
	 * @param prpLdisabilityLimit
	 * @throws Exception
	 */
	public void save(PrpLdisabilityLimit prpLdisabilityLimit) throws Exception;
	
	/**保存伤残等级信息
	 * @param list
	 * @throws Exception
	 */
	public void save(List<PrpLdisabilityLimit> list) throws Exception;
	
	/**删除伤残等级信息
	 * @param prpLdisabilityLimitId
	 * @throws Exception
	 */
	public void delete(PrpLdisabilityLimitId prpLdisabilityLimitId) throws Exception;

	/**更新伤残等级信息
	 * @param prpLdisabilityLimit
	 * @throws Exception
	 */
	public void update(PrpLdisabilityLimit prpLdisabilityLimit) throws Exception;

	/**根据伤残等级编号查询出伤残等级信息
	 * @param prpLdisabilityLimitId
	 * @return
	 * @throws Exception
	 */
	public PrpLdisabilityLimit findPrpLdisabilityLimit(PrpLdisabilityLimitId prpLdisabilityLimitId) throws Exception;
	
	/**根据查询对象获取Page对象的列表
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findPrpLdisabilityLimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**根据查询对象获取伤残等级信息  的集合
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLdisabilityLimit> findPrpLdisabilityLimit(QueryRule queryRule) throws Exception;
	/**
	 * @param claimNo
	 * @param ratingCode
	 * @return
	 * @throws Exception
	 * 根据立案号和伤残等级查询赔付限额
	 */
	public double getPrpLdisabilityLimitFee(String claimNo,String ratingCode)throws Exception;


}
