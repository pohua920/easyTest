package com.sinosoft.claim.schema.service.facade;
/**
 * PRPCLIMIT跟踪接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDriskRate;
import com.sinosoft.claim.schema.model.PrpDriskRateId;

public interface PrpDriskRateService {
	
	/**
	 * 保存险别配置信息
	 * @param prpDriskRate ：传入的险别配置信息
	 */
	public void save(PrpDriskRate prpDriskRate) throws Exception;
	
	/**
	 * 险别配置信息
	 * @param list  :传入的险别配置信息
	 * @throws Exceptionuan
	 */
	public void save(List<PrpDriskRate> list) throws Exception;
	
	/**
	 * 删除险别配置信息
	 * @param prpDriskRateId ：传入的险别配置信息
	 */
	public void delete(PrpDriskRateId prpDriskRateId) throws Exception;

	/**
	 * 更新险别配置信息
	 * @param prpDriskRate :传入需要更新的险别配置信息
	 */
	public void update(PrpDriskRate prpDriskRate) throws Exception;

	/**
	 * 险别配置信息
	 * @param prpDriskRateId ：传入的险别配置信息
	 * @return 险别配置信息
	 */
	public PrpDriskRate findPrpDriskRate(PrpDriskRateId prpDriskRateId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 险别配置信息
	 */
	public Page findPrpDriskRate(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 险别配置信息
	 * @param queryRule 查询对象
	 * @return 包含的 限额/免赔 的列表
	 */
	public List<PrpDriskRate> findPrpDriskRate(QueryRule queryRule) throws Exception;
	
	/**
	 * 查询险别的日额
	 * @return
	 * @throws Exception
	 */
	public double findDayAmount(PrpCmain prpCmain,PrpCitemKind prpCitemKind)throws Exception;
	
}
