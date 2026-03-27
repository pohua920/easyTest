package com.sinosoft.claim.schema.service.facade;
/**
 * 支付信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcarInsurance;
import com.sinosoft.claim.schema.model.PrpLcarInsuranceId;


public interface PrpLcarInsuranceService {
	/**
	 * 保存车体险讯息信息
	 * @param prpLthirdCarLoss ：传入的车体险讯息
	 */
	public void save(PrpLcarInsurance prpLpayObjectInfo) throws Exception;
	
	/**
	 * 保存车体险讯息信息
	 * @param list:保存车体险讯息信息
	 */
	public void save(List<PrpLcarInsurance> list) throws Exception;
	
	/**
	 * 删除车体险讯息信息
	 * @param PrpLcarInsuranceId ：传入的车体险讯息编号
	 */
	public void delete(PrpLcarInsuranceId prpLcarInsuranceId) throws Exception;
	
	/**
	 * @param compensateNo
	 * @throws Exception
	 * 根据计算书号删除信息
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;

	/**
	 * 更新车体险讯息信息
	 * @param PrpLcarInsurance :传入需要更新的车体险讯息
	 */
	public void update(PrpLcarInsurance prpLcarInsurance) throws Exception;

	/**
	 * 根据车体险讯息编号查询出车体险讯息信息
	 * @param PrpLcarInsuranceId ：传入的车体险讯息编号
	 * @return 返回车体险讯息
	 */
	public PrpLcarInsurance findPrpLcarInsurance(PrpLcarInsuranceId prpLcarInsuranceId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的车体险讯息页面信息
	 */
	public Page findPrpLcarInsurance(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取车体险讯息页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的车体险讯息页面信息  的集合
	 */
	public List<PrpLcarInsurance> findPrpLcarInsurance(QueryRule queryRule) throws Exception;

	/**
	 * :车体险讯息信息
	 * @param prpLcarInsuranceList  :传入的:车体险讯息信息集合
	 * @throws Exceptionuan
	 */
	public void insertAll(List<PrpLcarInsurance> prpLcarInsuranceList);
	
	/**
	 * 保存车体险讯息信息
	 * @param list:保存车体险讯息信息
	 */
	public void saveOrUpdate(List<PrpLcarInsurance> list) throws Exception;
	/**
	 * 保存车体险讯息信息
	 * @param list:保存车体险讯息信息
	 */
	public void saveOrUpdate(PrpLcarInsurance prpLcarInsurance) throws Exception;
}
