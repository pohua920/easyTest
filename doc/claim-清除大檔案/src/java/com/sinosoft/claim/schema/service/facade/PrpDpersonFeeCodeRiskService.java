package com.sinosoft.claim.schema.service.facade;

/**
 * 个人费用险种接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRisk;

public interface PrpDpersonFeeCodeRiskService {
	 /**
     * 获得指定险种的所有费用类别
     * @param riskCode
     * @return
     * @throws Exception
     */
	 public List<PrpDpersonFeeCodeRisk> findAllCodeList(String riskCode) throws Exception;
	 /**
	     * 获得强制保险的医疗费用类型
	     * @return
	     * @throws Exception
	     */
	public List<PrpDpersonFeeCodeRisk> findCompelMedicalCodeList()throws Exception;
	/**
     * 获得指定险种的死亡伤残费用类别
     * @param riskCode
     * @return
     * @throws Exception
     */
	public List<PrpDpersonFeeCodeRisk> findCompelDeathCodeList()throws Exception;
	/**
     * 获得指定险种的医疗费用类型
     * @param riskCode
     * @return
     * @throws Exception
     */
	public List<PrpDpersonFeeCodeRisk> findMedicalCodeList(String riskCode) throws Exception;
	 /**
     * 获得指定险种的死亡伤残费用类别
     * @param riskCode
     * @return
     * @throws Exception
     */
	public List<PrpDpersonFeeCodeRisk> findDeathCodeList(String riskCode) throws Exception;
	 /**
     * 按主键查找一条数据
     * @param riskCode 险种代码
     * @param feeCode 费用代码
     * @return prpDpersonFeeCodeRiskDto prpDpersonFeeCodeRiskDto
     * @throws Exception
     */
	public PrpDpersonFeeCodeRisk findByPrimaryKey(String riskCode,String feeCode) throws Exception;
}
