/*
 * @(#)PrpLcertifyPayeeServiceSpringImpl.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLcertifyPayeeId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyPayeeService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrpLcertifyPayeeServiceSpringImpl extends GenericDaoHibernate<PrpLcertifyPayee, PrpLcertifyPayeeId> implements PrpLcertifyPayeeService {

	/*
	 * （非 Javadoc）保存表prpLcertifyCollect信息
	 * @see
	 * com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save
	 * (com.sinosoft.claim.schema.model.PrpLcertifyCollect)
	 */
	public void save(PrpLcertifyPayee prpLcertifyPayee) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prpLcertifyPayee);
	}

	/*
	 * （非 Javadoc）
	 * @see
	 * com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save
	 * (java.util.List) 保存所有的对象
	 */
	public void save(List<PrpLcertifyPayee> list) throws Exception {
		logger.info("保存立案基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @param prpLcertifyDirect
	 * @throws Exception 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyPayee> list) throws Exception {
		if (list != null && list.size() > 0) {
			Session session = super.getSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	/**
	 * @param prpLcertifyDirect
	 * @throws Exception 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLcertifyPayee prpLcertifyPayee) throws Exception {
		if (prpLcertifyPayee != null) {
			super.getSession().saveOrUpdate(prpLcertifyPayee);
		}
	}

	/*
	 * （非 Javadoc）
	 * @see
	 * com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#delete
	 * (java.lang.String) 根据主键删除条件
	 */
	public void delete(PrpLcertifyPayeeId prpLcertifyPayeeId) throws Exception {
		logger.info("删除立案基本信息编号为" + prpLcertifyPayeeId + "的立案基本信息");
		super.deleteByPK(PrpLcertifyPayee.class, prpLcertifyPayeeId);
	}

	/**
	 * @param registNo
	 * @throws Exception 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from PrpLcertifyPayee where registNo='" + registNo + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

	/*
	 * （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#
	 * findByPrpLcertifyCollectId
	 * (com.sinosoft.claim.schema.model.PrpLcertifyCollectId) 根据主键查询出对象
	 */
	public PrpLcertifyPayee findByPrpLcertifyPayeeId(PrpLcertifyPayeeId prpLcertifyPayeeId) throws Exception {
		return super.get(PrpLcertifyPayee.class, prpLcertifyPayeeId);
	}

	/*
	 * （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#
	 * findPrpLcertifyCollect(ins.framework.common.QueryRule, int, int)
	 * 查询【page对象，页面分页
	 */
	public Page findPrpLcertifyPayee(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	/*
	 * （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#
	 * findPrpLcertifyCollect(ins.framework.common.QueryRule) 查询出所有的值
	 */
	public List<PrpLcertifyPayee> findPrpLcertifyPayee(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param registNo
	 * @return
	 * @throws Exception 根据保单号查询信息
	 */
	public List<PrpLcertifyPayee> findPrpLcertifyPayee(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		return super.find(queryRule);
	}

}
