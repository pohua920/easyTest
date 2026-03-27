package com.sinosoft.claim.schema.service.facade;
/**
 * 公估师基本信息表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyor;
import com.sinosoft.claim.schema.model.PrpLInsuranceSurveyorId;

public interface PrpLInsuranceSurveyorService {
	
	/**
	 * 保存公估师基本信息信息
	 * @param prpLInsuranceSurveyor ：传入的公估师基本信息
	 */
	public void save(PrpLInsuranceSurveyor prpLInsuranceSurveyor) throws Exception;
	
	/**
	 * 公估师基本信息信息
	 * @param list  :传入的公估师基本信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLInsuranceSurveyor> list) throws Exception;
	
	/**
	 * 删除公估师基本信息信息
	 * @param prpLInsuranceSurveyorId ：传入的公估师基本信息编号
	 */
	public void delete(PrpLInsuranceSurveyorId prpLInsuranceSurveyorId) throws Exception;

	/**
	 * 更新公估师基本信息信息
	 * @param prpLInsuranceSurveyor :传入需要更新的公估师基本信息
	 */
	public void update(PrpLInsuranceSurveyor prpLInsuranceSurveyor) throws Exception;

	/**
	 * 根据公估师基本信息编号查询出公估师基本信息信息
	 * @param prpLInsuranceSurveyorId ：传入的公估师基本信息编号
	 * @return 返回公估师基本信息
	 */
	public PrpLInsuranceSurveyor findPrpLInsuranceSurveyor(PrpLInsuranceSurveyorId prpLInsuranceSurveyorId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的公估师基本信息页面信息
	 */
	public Page findPrpLInsuranceSurveyor(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取公估师基本信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 公估师基本信息 的集合
	 */
	public List<PrpLInsuranceSurveyor> findPrpLInsuranceSurveyor(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据公估师基本信息编号查询出公估师基本信息信息
	 * @param certiNo ：传入的公估师基本信息编号
	 * @return 返回公估师基本信息
	 */
	public PrpLInsuranceSurveyor findPrpLInsuranceSurveyor(String certiNo) throws Exception;
	
	/**
	 * 按条件查询多条数据,要联合Prplexternalagency
	 * @author 中科软
	 * @date Mar 25, 2013 2:36:18 PM
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception;
}
