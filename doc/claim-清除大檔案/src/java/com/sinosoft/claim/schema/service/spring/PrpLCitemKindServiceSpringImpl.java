package com.sinosoft.claim.schema.service.spring;
/**
 * 代赔保单投保险别信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸START
import org.hibernate.Session;
import com.sinosoft.claim.common.util.HibernateUtils;
//mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸 END
import com.sinosoft.claim.schema.model.PrpLCitemKind;
import com.sinosoft.claim.schema.model.PrpLCitemKindId;
import com.sinosoft.claim.schema.service.facade.PrpLCitemKindService;

public class PrpLCitemKindServiceSpringImpl extends
GenericDaoHibernate<PrpLCitemKind, PrpLCitemKindId> implements PrpLCitemKindService{

	@Override
	public void save(PrpLCitemKind prpLCitemKind) throws Exception {
		logger.info("保存代赔保单投保险别信息");
		super.save(prpLCitemKind);
		
	}

	@Override
	public void save(List<PrpLCitemKind> list) throws Exception {
		logger.info("保存代赔保单投保险别信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLCitemKindId prpLCitemKindId) throws Exception {
		logger.info("删除代赔保单投保险别信息编号为" + prpLCitemKindId + "的代赔保单投保险别信息");
		super.deleteByPK(PrpLCitemKind.class, prpLCitemKindId);
	}

	@Override
	public PrpLCitemKind findPrpLCitemKind(PrpLCitemKindId prpLCitemKindId) throws Exception {
		logger.info("查询代赔保单投保险别信息编号为" + prpLCitemKindId + "的代赔保单投保险别信息");
		return super.get(PrpLCitemKind.class, prpLCitemKindId);
	}

	@Override
	public Page findPrpLCitemKind(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代赔保单投保险别信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLCitemKind> findPrpLCitemKind(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据代赔保单投保险别编号查询出代赔保单投保险别信息
	 * @param certiNo ：传入的代赔保单投保险别编号
	 * @return 返回代赔保单投保险别
	 */
	public PrpLCitemKind findPrpLCitemKind(String certiNo) throws Exception{
		PrpLCitemKind prpLCitemKind = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLCitemKind> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLCitemKind = resultList.get(0);
		}
		return prpLCitemKind;
	}
	
	/**
	 * mantis：CLM0155，處理人員：DP0713，車體險自負額有責任時卡控自負額發票號碼必輸
	 */
	public String findDeductibleTypeByConditions(String conditions)throws Exception{
		String sql = "SELECT KIND.DEDUCTIBLETYPE FROM BUSINESS.PRPLCLAIM CL "
				+" INNER JOIN BUSINESS.PRPCOPYITEMKIND KIND ON KIND.POLICYNO = CL.POLICYNO "
				+" WHERE "
				//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
				+" KIND.ENDORSENO = BUSINESS.GETENDORSENO(CL.POLICYNO,TO_DATE(CL.DAMAGESTARTDATE,'yyyy-mm-dd'),SUBSTR(CL.DAMAGESTARTHOUR,1,2)) AND " 
				+" KIND.DEDUCTIBLETYPE IS NOT NULL "
				+" AND "+conditions;
//				conditions = " CLAIMNO = AND KIND.KINDCODE= ";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, sql, 0,0);
		return tempList.size()>0?(String) tempList.get(0):"";
	}

	/**
	 * mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核
	 * 檢查 理算書號、牌照號碼、出險日期、出險小時 是否存在
	 */
	public String checkLicenceNoAndDamageStartDate(String licenseNo, String damageDate,
			String damageHour, String compensateNo) throws Exception {

		int sum = 0;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CLM.CLAIMNO ");
		sql.append("  FROM PRPLCOMPENSATE COM ");
		sql.append(" INNER JOIN PRPLCLAIM CLM ON CLM.POLICYNO = COM.POLICYNO "); 
		sql.append(" INNER JOIN SWFLOG SW ON SW.POLICYNO = CLM.POLICYNO ");
		sql.append(" WHERE SW.LOSSITEMNAME = '" + licenseNo + "' ");
		sql.append("   AND TO_CHAR(CLM.DAMAGESTARTDATE, 'YYYY-MM-DD') = '" + damageDate + "' ");
		sql.append("   AND SUBSTR(CLM.DAMAGESTARTHOUR, 0, 2) = '" + damageHour + "' ");
		sql.append(" GROUP BY CLM.CLAIMNO ");
		logger.info(">>> sql: " + sql.toString());

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, sql.toString(), 0, 0);

		if (tempList!=null && tempList.size()>0) {
			for (int i=0; i<tempList.size(); i++) {
				String claimNo = (String) tempList.get(i);
				StringBuffer sql2 = new StringBuffer();
				sql2.append(" SELECT NVL(SUM(chargeamount), 0) FROM prpLcharge ");
				sql2.append("  WHERE chargecode = 'Y' ");
				if (claimNo.indexOf("AL")!=-1) {
					sql2.append("   AND riskcode = 'A01' ");
				} else if (claimNo.indexOf("BL")!=-1) {
					sql2.append("   AND riskcode = 'B01' ");
				}
				sql2.append("   AND compensateno like '%" + claimNo + "%' ");
				// mantis：CLM0229，處理人員：DP0714，新核心-醫詢費用強制任意累積費用調整
				if (org.apache.commons.lang3.StringUtils.isNotBlank(compensateNo)) {
					sql2.append("   AND compensateno <> '" + compensateNo + "' ");
				}
				logger.info(">>> sql2: " + sql2.toString());

				List<?> tempList2 = HibernateUtils.findbySql(session, sql2.toString(), 0, 0);
				if (tempList2!=null && tempList2.size()>0) {
					sum += ((java.math.BigDecimal) tempList2.get(0)).intValue(); 
				}
			}
		}
		logger.info(">>> sum: " + sum);

		return "" + sum;
	}
}
