package com.sinosoft.claim.schema.service.spring;

/**
 * 重开赔案信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLrecaseId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;

public class PrpLrecaseServiceSpringImpl extends GenericDaoHibernate<PrpLrecase, PrpLrecaseId> implements PrpLrecaseService {

	private PrpLclaimService prpLclaimService;

	@Override
	public void save(PrpLrecase prpLrecase) throws Exception {
		logger.info("保存重开赔案信息");
		super.save(prpLrecase);

	}

	@Override
	public void save(List<PrpLrecase> list) throws Exception {
		logger.info("保存重开赔案信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLrecaseId prpLrecaseId) throws Exception {
		logger.info("删除重开赔案信息编号为" + prpLrecaseId + "的重开赔案信息");
		super.deleteByPK(PrpLrecase.class, prpLrecaseId);
	}

	@Override
	public PrpLrecase findPrpLrecase(PrpLrecaseId prpLrecaseId) throws Exception {
		logger.info("查询重开赔案信息编号为" + prpLrecaseId + "的重开赔案信息");
		return super.get(PrpLrecase.class, prpLrecaseId);
	}

	@Override
	public Page findPrpLrecase(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取重开赔案信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLrecase> findPrpLrecase(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据重开赔案编号查询出重开赔案信息
	 * @param certiNo ：传入的重开赔案编号
	 * @return 返回重开赔案
	 */
	public PrpLrecase findPrpLrecase(String claimNo) throws Exception {
		PrpLrecase prpLrecase = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		List<PrpLrecase> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLrecase = resultList.get(0);
		}
		return prpLrecase;
	}

	public int getCount(String claimNo) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String statement = "Select count(1) from PrpLrecase Where claimNo ='" + claimNo + "'";
		int count = ((Number) HibernateUtils.getCountbyCountSql(session, statement)).intValue();
		return count;
	}

	/**
	 * 根据立案号查找业务号,如果没有重开过赔案,正常的流程是用registNo流转,如果是重开的赔案,用claimNo+serialNo最为业务流转
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public String findJbpmBusinessNo(String claimNo, boolean isRecase) throws Exception {
		String sql = "select count(1) from PrpLrecase where claimNo='" + claimNo + "'";
		Long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
		String recaseNo = "";
		if (isRecase) {
			recaseNo = claimNo + "_" + (count + 1);
		} else {
			if (count > 0) {
				PrpLrecaseId prpLrecaseId = new PrpLrecaseId();
				prpLrecaseId.setClaimNo(claimNo);
				prpLrecaseId.setSerialNo(((Number) count).intValue());
				PrpLrecase prpLrecase = this.findPrpLrecase(prpLrecaseId);
				if (CommonUtils.isEmpty(prpLrecase.getCloseCaseUserCode()) && prpLrecase.getCloseCaseDate() == null) {
					recaseNo = claimNo + "_" + count;
				}
			}
		}
		if ("".equals(recaseNo)) {
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			recaseNo = prpLclaim.getRegistNo();
		}
		return recaseNo;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

}
