package com.sinosoft.claim.schema.service.spring;

/**
 * 呼叫中心信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLcallCenterId;
import com.sinosoft.claim.schema.service.facade.PrpLcallCenterService;

public class PrpLcallCenterServiceSpringImpl extends GenericDaoHibernate<PrpLcallCenter, PrpLcallCenterId> implements PrpLcallCenterService {

	@Override
	public void save(PrpLcallCenter prpLcallCenter) throws Exception {
		logger.info("保存呼叫中心信息");
		super.save(prpLcallCenter);

	}

	@Override
	public void save(List<PrpLcallCenter> list) throws Exception {
		logger.info("保存呼叫中心信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void saveOrUpdate(PrpLcallCenter prpLcallCenter) throws Exception {
		logger.info("保存呼叫中心信息");
		super.getSession().saveOrUpdate(prpLcallCenter);

	}

	public void saveOrUpdate(List<PrpLcallCenter> list) throws Exception {
		logger.info("保存呼叫中心信息");
		for (int i = 0; i < list.size(); i++) {
			this.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcallCenterId prpLcallCenterId) throws Exception {
		logger.info("删除呼叫中心信息编号为" + prpLcallCenterId + "的呼叫中心信息");
		super.deleteByPK(PrpLcallCenter.class, prpLcallCenterId);
	}

	@Override
	public PrpLcallCenter findPrpLcallCenter(PrpLcallCenterId prpLcallCenterId) throws Exception {
		logger.info("查询呼叫中心信息编号为" + prpLcallCenterId + "的呼叫中心信息");
		return super.get(PrpLcallCenter.class, prpLcallCenterId);
	}

	@Override
	public Page findPrpLcallCenter(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取呼叫中心信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcallCenter> findPrpLcallCenter(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	public int getMaxSerialNo(String registNo) throws SQLException, Exception {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String condition = " registNo = '" + registNo + "'";
		String sql = "";
		sql = " SELECT MAX(serialNo) FROM PrpLcallCenter Where " + condition;
		List<?> resultList = HibernateUtils.findbySql(session, sql);
		int serialNo = 0;
		if (resultList.size() > 0) {
			BigDecimal object = (BigDecimal) resultList.get(0);
			serialNo = object.intValue();
		}
		return serialNo;
	}
}
