package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDclauseKindService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDclauseKind;
import com.sinosoft.claim.schema.model.PrpDclauseKindId;

public class PrpDclauseKindServiceSpringImpl  extends GenericDaoHibernate<PrpDclauseKind, PrpDclauseKindId> implements PrpDclauseKindService{

	@Override
	public List<PrpDclauseKind> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception {
        String statement = "Select RiskCode," + 
        " ClauseType," + 
        " KindCode," + 
        " RelateKindCode," + 
        " Flag From PrpDclauseKind Where " + conditions;
        PrpDclauseKind prpDclauseKind = null;
		List<PrpDclauseKind> resultList = new ArrayList<PrpDclauseKind>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpDclauseKind = new PrpDclauseKind();
			prpDclauseKind.getId().setRiskCode(String.valueOf(object[0]));
			prpDclauseKind.getId().setClauseType(String.valueOf(object[1]));
			prpDclauseKind.getId().setKindCode(String.valueOf(object[2]));
			prpDclauseKind.getId().setRelateKindCode(String.valueOf(object[3]));
			prpDclauseKind.setFlag(String.valueOf(object[4]));
			resultList.add(prpDclauseKind);
		}
		return resultList;
	}

	@Override
	public List<PrpDclauseKind> findByConditions(QueryRule queryRule) {
		return super.find(queryRule);
	}

}
