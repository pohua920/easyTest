package com.sinosoft.claim.ExternalAgency.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sinosoft.claim.ExternalAgency.service.facade.ExternalagencyService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLexternalAgencyId;
import com.sinosoft.sysframework.log.Logger;

public class ExternalagencyServiceSpringImpl extends GenericDaoHibernate<PrpLexternalAgency, PrpLexternalAgencyId> implements ExternalagencyService {
	private static Logger logger = Logger.getLogger(ExternalagencyServiceSpringImpl.class);

	@Override
	public void delete(String comcode, String comtype) throws Exception {

	}

	@Override
	public void deleteByConditions(String strComCode, String strComType) throws Exception {
		PrpLexternalAgencyId prpLexternalAgencyId = new PrpLexternalAgencyId();
		prpLexternalAgencyId.setComCode(strComCode);
		prpLexternalAgencyId.setComtype(strComType);
		super.deleteByPK(prpLexternalAgencyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PrpLexternalAgency findByPrimaryKey(String comcode, String comtype, int pageNo, int pageSize) throws Exception {
		PrpLexternalAgency prpLexternalAgency = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.comCode", comcode);
		queryRule.addEqual("id.comtype", comtype);
		List<PrpLexternalAgency> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLexternalAgency = resultList.get(0);
		}
		return prpLexternalAgency;
	}

	@Override
	public void insert(PrpLexternalAgency prpLexternalAgency) throws Exception {
		super.save(prpLexternalAgency);
	}

	@Override
	public void update(PrpLexternalAgency prpLexternalAgency) {
		String statement = "";
		if (prpLexternalAgency != null) {
			String condition = "Comcode = '" + prpLexternalAgency.getId().getComtype() + "' Comtype = '" + prpLexternalAgency.getId().getComtype() + "'";
			statement = " DELETE FROM Prplexternalagency Where " + condition;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			try {
				HibernateUtils.executeSql(session, statement);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				insert(prpLexternalAgency);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public Page findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule, pageNo, rowsPerPage);
	}

}
