package com.sinosoft.claim.schema.service.spring;

/**
 * 立案基本信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;

public class PrpLclaimServiceSpringImpl extends GenericDaoHibernate<PrpLclaim, String> implements PrpLclaimService {

	@Override
	public void save(PrpLclaim prpLclaim) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prpLclaim);

	}

	@Override
	public void save(List<PrpLclaim> list) throws Exception {
		logger.info("保存立案基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpLclaim prpLclaim) throws Exception {
		logger.info("保存立案基本信息");
		super.getSession().merge(prpLclaim);

	}

	@Override
	public void saveOrUpdate(List<PrpLclaim> list) throws Exception {
		logger.info("保存立案基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除立案基本信息编号为" + claimNo + "的立案基本信息");
		super.deleteByPK(PrpLclaim.class, claimNo);
	}

	@Override
	public PrpLclaim findPrpLclaim(String claimNo) throws Exception {
		logger.info("查询立案基本信息编号为" + claimNo + "的立案基本信息");
		return super.get(PrpLclaim.class, claimNo);
	}

	@Override
	public Page findPrpLclaim(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<PrpLclaim> findPrpLclaim(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据报案号判断是否立案注销
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public boolean isClaim(String registNo) throws Exception {
		boolean flag = false;
		String hql = " from PrpLclaim where caseNo is not null and registNo ='" + registNo + "'";
		long count = super.getCount(hql);
		if (count > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据报案号查询立案信息
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public List<PrpLclaim> findByRegistNo(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		return this.find(queryRule);
	}

	/**
	 * 根据报案号查询立案信息,立案没有注销的信息
	 * @param registNo
	 * @return
	 * @throws Exception 
	 */
	public List<PrpLclaim> findByRegistNoCancel(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		queryRule.addIsNull("cancelDate");
		return this.find(queryRule);
	}

	/**
	 * 根据sql语句查询有多少条立案信息
	 * @param conditions
	 * @return
	 * @throws Exception 
	 */
	public long getCount(String conditions) throws Exception {
		long count = 0;
		if (!CommonUtils.isEmpty(conditions)) {
			String countSql = "select count(*) from prpLclaim where " + conditions;
			count = HibernateUtils.getCountbyCountSql(getSession(), countSql);
		}
		return count;
	}

	/**
	 * 判断是否已经立案，如果没立案返回null，立案就返回立案号
	 * @param registNo
	 * @param policyNo
	 * @return
	 * @throws Exception 
	 */
	public String isClaim(String registNo, String policyNo) throws Exception {
		String claimNo = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		queryRule.addEqual("policyNo", policyNo);
		List<PrpLclaim> list = super.find(queryRule);
		if (list.size() > 0) {
			claimNo = list.get(0).getClaimNo();
		}
		return claimNo;
	}
	
	/**
	 * 根据sql语句查询page信息
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByConditions(String conditions,int pageNo,int pageSize)throws Exception{
		String hql = "select * from PrpLclaim where "+conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), hql, pageNo, pageSize, PrpLclaim.class);
		return page;
	}

	@Override
	public String translateCode(String businessCode, boolean isSearchClaimNo) throws Exception {
		if (businessCode == null || businessCode.trim().equals("")) {
			return "";
		}
		String statement = null;
		if (isSearchClaimNo) {
			statement = "Select claimno from prplclaim Where registno='" + businessCode + "'";
		} else {
			statement = "Select registno from prplclaim Where claimno='" + businessCode + "'";
		}
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			return list.get(0).toString();
		}
		return "";
	}
	/**
	 * 预赔提交时回写立案的预赔金额
	 * @param prpLclaim
	 * @return
	 * @throws Exception
	 */
	public void updatePrepayPaid(PrpLclaim prpLclaim) throws Exception {
		String statement = " update PrpLclaim set SumPaid='"+ prpLclaim.getSumPaid() +"' Where " +
        " ClaimNo ='"+prpLclaim.getClaimNo()+"'";
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public String[] translateCodes(String businessCode, boolean isSearchClaimNo) throws Exception {
		String[] buinessNo = null;
		if (businessCode == null) {
			return new String[0];
		} else {
			if (businessCode.trim().equals("")) {
				return new String[0];
			}
		}
		String statement = null;
		if (isSearchClaimNo) {
			statement = "Select claimno from prplclaim Where registno='" + businessCode + "'";
		} else {
			statement = "Select registno from prplclaim Where claimno='" + businessCode + "'";
		}
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement);
		if (list != null && !list.isEmpty()) {
			buinessNo = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				buinessNo[i] = (String) list.get(i);
			}
		}
		return buinessNo;
	}

	@Override
	public Page findReplevyCase(String conditions, int pageNo, int pageSize) throws Exception {
		String sql = "select c.* from prplclaim c,prplregist r where r.registno = c.registno and c.endcasedate is not null and c.canceldate is null ";
		if(DataUtils.emptyToNull(conditions)!=null){
			sql += conditions;
		}
		Page page = HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLclaim.class);
		return page;
	}
}
