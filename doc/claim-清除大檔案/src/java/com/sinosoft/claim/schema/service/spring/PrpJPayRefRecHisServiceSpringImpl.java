package com.sinosoft.claim.schema.service.spring;
/**
 * 实收实付记录转储表接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.util.PrintUtils;
import com.sinosoft.claim.schema.model.PrpJPayRefRecHis;
import com.sinosoft.claim.schema.model.PrpJPayRefRecHisId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.service.facade.PrpJPayRefRecHisService;

public class PrpJPayRefRecHisServiceSpringImpl extends GenericDaoHibernate<PrpJPayRefRecHis, PrpJPayRefRecHisId> implements PrpJPayRefRecHisService {

	@Override
	public void delete(String certiType, String certiNo, Integer serialNo, String payRefReason, Integer payRefTimes) {
		PrpJPayRefRecHisId prpJPayRefRecHisId = new PrpJPayRefRecHisId(certiType, certiNo, serialNo, payRefReason, payRefTimes);
		super.deleteByPK(prpJPayRefRecHisId);
	}

	@Override
	public void deleteByPayRefNo(String payRefNo) throws Exception {
		String sql = "Delete From PrpJpayRefRecHis Where payRefNo = '" + payRefNo + "'";
		HibernateUtils.executeSql(super.getSession(), sql);
	}

	@Override
	public PrpJPayRefRecHis findPrpJPayRefRecHis(String certiType, String certiNo, Integer serialNo, String payRefReason) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiType", certiType);
		queryRule.addEqual("id.certiNo", certiNo);
		queryRule.addEqual("id.serialNo", serialNo);
		queryRule.addEqual("id.payRefReason", payRefReason);
		List<PrpJPayRefRecHis> result = super.find(queryRule);
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	public List<?> findByQueryConditions(String conditions) throws Exception {
		List<?> tempResult = HibernateUtils.findbySql(getSession(), conditions);
		return tempResult;
	}

	@Override
	public List<PrpJPayRefRecHis> findPrpJPayRefRecHis(String conditions) {
		return super.find(QueryRule.getInstance().addSql(conditions));
	}

	@Override
	public void save(PrpJPayRefRecHis prpJPayRefRecHis) {
		super.save(prpJPayRefRecHis);
	}

}
