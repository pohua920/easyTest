package com.sinosoft.claim.schema.service.spring;

/**
 * 责任险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainLiab;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpCmainLiabServiceSpringImpl extends GenericDaoHibernate<PrpCmainLiab, String> implements PrpCmainLiabService {

	public void save(PrpCmainLiab prpCmainLiab) throws Exception {
		logger.info("责任险保单信息信息");
		super.save(prpCmainLiab);
	}

	public void save(List<PrpCmainLiab> list) throws Exception {
		logger.info("责任险保单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(String proposalNo) throws Exception {
		logger.info("删除责任险保单信息编号为" + proposalNo + "的责任险保单信息");
		super.deleteByPK(PrpCmainLiab.class, proposalNo);
	}

	public PrpCmainLiab findPrpCmainLiab(String proposalNo) throws Exception {
		logger.info("查询责任险保单信息编号为" + proposalNo + "的责任险保单信息");
		return super.get(PrpCmainLiab.class, proposalNo);
	}

	public Page findPrpCmainLiab(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取责任险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCmainLiab> findPrpCmainLiab(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	public DateTime findByPrimaryKeyStartDate(String policyNo) throws Exception {
		PrpCmainLiab prpCmainLiab = this.findPrpCmainLiab(policyNo);
		DateTime bkWardStartDate = new DateTime();
		if (prpCmainLiab != null) {
			bkWardStartDate = new DateTime(prpCmainLiab.getBkWardStartDate());
		}
		return bkWardStartDate;
	}
}
