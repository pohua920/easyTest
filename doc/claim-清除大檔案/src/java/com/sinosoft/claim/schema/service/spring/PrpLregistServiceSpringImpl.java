package com.sinosoft.claim.schema.service.spring;

/**
 * 报案接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpLregistServiceSpringImpl extends GenericDaoHibernate<PrpLregist, String> implements PrpLregistService {
	/** 立案 service */
	private PrpLclaimService prpLclaimService;

	/**
	 * 保存报案信息
	 * @param prpLregist ：传入的报案
	 */
	@Override
	public void save(PrpLregist prpLregist) throws Exception {
		logger.info("保存报案信息");
		super.save(prpLregist);
	}

	/**
	 * @param prpLregist
	 * @throws Exception 保存或修改，
	 */
	public void saveOrUpdate(PrpLregist prpLregist) throws Exception {
		super.getSession().merge(prpLregist);
	}

	/**
	 * 删除报案信息
	 * @param registNo ：传入的报案编号
	 */
	@Override
	public void delete(String registNo) throws Exception {
		super.deleteByPK(PrpLregist.class, registNo);
		logger.info("删除报案编号为" + registNo + "的报案信息");
	}

	/**
	 * 保存报案信息
	 * @param list:保存报案信息
	 */
	@Override
	public void save(List<PrpLregist> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 报案修改
	 * @param PrpLregist prpLregist
	 * @throws Exception
	 */
	@Override
	public void update(PrpLregist prpLregist) {
		logger.info("修改报案信息开始");
		super.update(prpLregist);
		logger.info("修改报案信息结束");
	}

	/**
	 * 根据报案编号查询出报案信息
	 * @param registNo ：传入的报案编号
	 * @return 返回报案
	 */
	@Override
	public PrpLregist findPrpLregist(String registNo) throws Exception {
		logger.info("查询报案编号为" + registNo + "的报案信息");
		return super.get(PrpLregist.class, registNo);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案页面信息
	 */
	@Override
	public Page findPrpLregist(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取报案列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案页面信息
	 */
	@Override
	public Page findPrpLregist(String conditions, int pageNo, int pageSize) throws Exception {
		logger.info("获取报案列表信息");
		String sql = "select * from PrpLregist where " + conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpLregist.class);
		return page;
	}

	@Override
	public List<PrpLregist> findPrpLregist(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 *             conditions如果conditions後面有有别名，会出错，prplregist.
	 *             registno='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	@SuppressWarnings("unchecked")
	public List<PrpLregist> findByConditions(String conditions) throws Exception {
		String sql = "select * from prplregist where " + conditions;
		return (List<PrpLregist>) HibernateUtils.findbySql(super.getSession(), sql, PrpLregist.class);
	}

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 *             conditions如果conditions後面有有别名，会出错，prplregist.
	 *             registno='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String sql = "select * from prplregist where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLregist.class);
	}

	/**
	 * @param registNo
	 * @return
	 */
	@Override
	public boolean isExist(String registNo) throws Exception {
		String hql = "from PrpLregist where registNo=?";
		long count = super.getCount(hql, registNo);
		if (count < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getCount(String conditions) throws Exception {
		int count = -1;
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM (SELECT * FROM PrpLregist WHERE ");
		buffer.append(conditions);
		buffer.append(")");
		Session session = super.getSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
		return count;
	}

	/**
	 * @param policyNo
	 * @return
	 * @throws Exception 查找少数的字段
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpLregist> findSamePolicyRegist(String policyNo) throws Exception {
		String statement = " Select registNo,damageStartDate " + " From Prplregist where policyNo='" + policyNo + "'  order by registNo";
		List<PrpLregist> list = new ArrayList<PrpLregist>();
		PrpLregist prpLregist = null;
		List<String[]> resultSet = super.getSession().createSQLQuery(statement).list();
		for (int i = 0; i < resultSet.size(); i++) {
			prpLregist = new PrpLregist();
			prpLregist.setRegistNo(resultSet.get(i)[0]);
			if (resultSet.get(i)[1] != null) {
				prpLregist.setDamageStartDate(new DateTime(resultSet.get(i)[1]));
			} else {
				prpLregist.setDamageStartDate(null);
			}
			list.add(prpLregist);
		}
		return list;
	}
	
	/**
	 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	 * @param policyNo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpLregist> findSameLicensenoRegist(String policyNo) throws Exception {
		//licenseNo 
		String statement = 
		" SELECT LICENSENO||','||TO_CHAR(REPAIRSTARTDATE, 'YYYY-MM-DD')||','||TO_CHAR(REPAIRENDDATE, 'YYYY-MM-DD') FROM (  "+
	    " SELECT REPAIRFACTORYNAME,REPAIRSTARTDATE, REPAIRENDDATE,POLICYNO ,LICENSENO,CLAIMNO "+
	    " FROM BUSINESS.PRPLCOMPONENT PONENT "+
	    " GROUP BY REPAIRFACTORYNAME,REPAIRSTARTDATE, REPAIRENDDATE,POLICYNO ,LICENSENO,CLAIMNO "+
	    " UNION "+
	    " SELECT REPAIRFACTORYNAME,REPAIRSTARTDATE, REPAIRENDDATE,POLICYNO ,LICENSENO,CLAIMNO "+
	    " FROM BUSINESS.PRPLREPAIRFEE REPAIRF "+
	    " GROUP BY REPAIRFACTORYNAME,REPAIRSTARTDATE, REPAIRENDDATE,POLICYNO ,LICENSENO,CLAIMNO "+
	    " ) WHERE LICENSENO IN (SELECT DISTINCT p.LICENSENO  FROM BUSINESS.PRPLREGIST p WHERE p.POLICYNO = '" + policyNo + "')";
		List<PrpLregist> list = new ArrayList<PrpLregist>();
		PrpLregist prpLregist = null;
		List<String> resultSet = super.getSession().createSQLQuery(statement).list();
		for (int i = 0; i < resultSet.size(); i++) {
			prpLregist = new PrpLregist();
			String[] rtn = resultSet.get(i).split(",");
			prpLregist.setLicenseNo(rtn[0]);
			String repairStartDate = rtn[1];
			if (repairStartDate != null) {
				prpLregist.setDamageStartDate(new DateTime(repairStartDate));
			} else {
				prpLregist.setDamageStartDate(null);
			}
			String repairEndDate = rtn[2];
			if (repairEndDate != null) {
				prpLregist.setDamageEndDate(new DateTime(repairEndDate));
			} else {
				prpLregist.setDamageEndDate(null);
			}
			list.add(prpLregist);
		}
		return list;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

}