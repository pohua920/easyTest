package com.sinosoft.claim.schema.service.spring;
/**
 * 个人费用险种接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRisk;
import com.sinosoft.claim.schema.model.PrpDpersonFeeCodeRiskId;
import com.sinosoft.claim.schema.service.facade.PrpDpersonFeeCodeRiskService;

public class PrpDpersonFeeCodeRiskServiceSpringImpl extends
GenericDaoHibernate<PrpDpersonFeeCodeRisk, PrpDpersonFeeCodeRiskId> implements
		PrpDpersonFeeCodeRiskService {
	
	private UtiCodeTransferService utiCodeTransferService;

	@Override
	public List<PrpDpersonFeeCodeRisk> findAllCodeList(String riskCode)
			throws Exception {
		String sql = "riskcode ='"+riskCode+"' and validstatus='1' order by feeCategory desc,priority";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
//		queryRule.addEqual("riskcode", riskCode);
//		queryRule.addAscOrder(propertyName)
//		sql = "select * from PrpDpersonFeeCodeRisk where "+sql;
//		List list = HibernateUtils.findbySql(super.getSession(), sql, PrpDpersonFeeCodeRisk.class);
		
		return super.find(queryRule);
	}

	@Override
	public List<PrpDpersonFeeCodeRisk> findCompelMedicalCodeList() throws Exception {
		System.out.println("1");
		System.out.println(utiCodeTransferService);
		String riskCode = utiCodeTransferService.findByPrimaryKey("RISKCODE_DAZ").getOuterCode();
		System.out.println("2"+riskCode);
		String sql = "riskcode ='"+riskCode+"' and feeCategory = 'M' and validstatus='1' order by priority";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.find(queryRule);
	}
	@Override
	public List<PrpDpersonFeeCodeRisk> findCompelDeathCodeList()
			throws Exception {
		String riskCode = utiCodeTransferService.findByPrimaryKey("RISKCODE_DAZ").getOuterCode();
		String sql = "riskcode ='"+riskCode+"' and feeCategory = 'D' and validstatus='1' order by priority";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.find(queryRule);
	}
	
	@Override
	public List<PrpDpersonFeeCodeRisk> findMedicalCodeList(String riskCode)
			throws Exception {
		String sql = "riskcode ='"+riskCode+"' and feeCategory = 'M' and validstatus='1' order by priority";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.find(queryRule);
	}
	
	@Override
	public List<PrpDpersonFeeCodeRisk> findDeathCodeList(String riskCode)
			throws Exception {
		String sql = "riskcode ='"+riskCode+"' and feeCategory = 'D' and validstatus='1' order by priority";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.find(queryRule);
	}
	
	@Override
	public PrpDpersonFeeCodeRisk findByPrimaryKey(String riskCode,
			String feeCode) throws Exception {
		PrpDpersonFeeCodeRiskId id = new PrpDpersonFeeCodeRiskId();
		id.setFeeCode(feeCode);
		id.setRiskCode(riskCode);
		return super.get(id);
	}
	
	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(
			UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	
}
