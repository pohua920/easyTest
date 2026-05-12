package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLpersonHospital;
import com.sinosoft.claim.schema.model.PrpLpersonHospitalId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonHospitalService;

public class PrpLpersonHospitalServiceSpringImpl extends GenericDaoHibernate<PrpLpersonHospital, PrpLpersonHospitalId> implements PrpLpersonHospitalService {


	/**
	 * 删除就诊医院
	 * @param prpLpersonHospitalId ：就诊医院信息主键
	 */
	public void delete(PrpLpersonHospitalId prpLpersonHospitalId) throws Exception {
		super.deleteByPK(prpLpersonHospitalId);
		
	}

	/**
	 * 根据主键查询就诊医院信息
	 * @param prpLpersonHospitalId ：就诊医院信息ID
	 * @return 就诊医院信息
	 */
	public PrpLpersonHospital findPrpLpersonHospital(PrpLpersonHospitalId prpLpersonHospitalId) throws Exception {
		return super.get(prpLpersonHospitalId);
	}

	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的就诊医院信息集合
	 */
	public Page findPrpLpersonHospital(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param queryRule 查询对象
	 * @return 包含的 就诊医院信息 的集合
	 */
	public List<PrpLpersonHospital> findPrpLpersonHospital(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据查询对象获取 就诊医院信息
	 * @param compensateNo 计算书号码
	 * @return 包含的 就诊医院信息 的集合
	 */
	public List<PrpLpersonHospital> findPrpLpersonHospital(String compensateNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.compensateNo", compensateNo).addAscOrder("id.serialNo");
		return this.findPrpLpersonHospital(queryRule);
	}

	/**
	 * 保存就诊医院信息
	 * @param prpLpersonHospital ：就诊医院信息
	 */
	public void save(PrpLpersonHospital prpLpersonHospital) throws Exception {
		super.save(prpLpersonHospital);
	}

	/**
	 * 保存就诊医院信息
	 * @param list  :就诊医院信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLpersonHospital> list) throws Exception {
		super.saveAll(list);
	}

}
