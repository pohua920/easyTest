package com.sinosoft.claim.schema.service.spring;

/**
 * 贷款保险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainLoan;
import com.sinosoft.claim.schema.model.PrpCmainLoanId;
import com.sinosoft.claim.schema.service.facade.PrpCmainLoanService;

public class PrpCmainLoanServiceSpringImpl extends GenericDaoHibernate<PrpCmainLoan, PrpCmainLoanId> implements PrpCmainLoanService {

	public void save(PrpCmainLoan prpCmainLoan) throws Exception {
		logger.info("贷款保险保单信息信息");
		super.save(prpCmainLoan);
	}

	public void save(List<PrpCmainLoan> list) throws Exception {
		logger.info("贷款保险保单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCmainLoanId prpCmainLoanId) throws Exception {
		logger.info("删除贷款保险保单信息编号为" + prpCmainLoanId + "的贷款保险保单信息");
		super.deleteByPK(PrpCmainLoan.class, prpCmainLoanId);
	}

	public PrpCmainLoan findPrpCmainLoan(PrpCmainLoanId prpCmainLoanId) throws Exception {
		logger.info("查询贷款保险保单信息编号为" + prpCmainLoanId + "的贷款保险保单信息");
		return super.get(PrpCmainLoan.class, prpCmainLoanId);
	}

	public Page findPrpCmainLoan(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取贷款保险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCmainLoan> findPrpCmainLoan(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
