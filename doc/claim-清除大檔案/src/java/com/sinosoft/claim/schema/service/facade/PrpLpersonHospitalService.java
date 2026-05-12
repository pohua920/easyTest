package com.sinosoft.claim.schema.service.facade;
/**
 * 就诊医院信息
 * @author zhangxingwei
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonHospitalId;

public interface PrpLpersonHospitalService {
	
	/**
	 * 保存就诊医院信息
	 * @param prpLpersonHospital ：就诊医院信息
	 */
	public void save(PrpLpersonHospital prpLpersonHospital) throws Exception;
	
	/**
	 * 保存就诊医院信息
	 * @param list  :就诊医院信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLpersonHospital> list) throws Exception;
	
	/**
	 * 删除就诊医院
	 * @param prpLpersonHospitalId ：就诊医院信息主键
	 */
	public void delete(PrpLpersonHospitalId prpLpersonHospitalId) throws Exception;

	/**
	 * 更新就诊医院信息
	 * @param prpLpersonHospital :就诊医院信息
	 */
	public void update(PrpLpersonHospital prpLpersonHospital) throws Exception;

	/**
	 * 根据主键查询就诊医院信息
	 * @param prpLpersonHospitalId ：就诊医院信息ID
	 * @return 就诊医院信息
	 */
	public PrpLpersonHospital findPrpLpersonHospital(PrpLpersonHospitalId prpLpersonHospitalId) throws Exception;
	
	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的就诊医院信息集合
	 */
	public Page findPrpLpersonHospital(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param queryRule 查询对象
	 * @return 包含的 就诊医院信息 的集合
	 */
	public List<PrpLpersonHospital> findPrpLpersonHospital(QueryRule queryRule) throws Exception;
	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param compensateNo 计算书号码
	 * @return 包含的 就诊医院信息 的集合
	 */
	public List<PrpLpersonHospital> findPrpLpersonHospital(String compensateNo) throws Exception;
	
}
