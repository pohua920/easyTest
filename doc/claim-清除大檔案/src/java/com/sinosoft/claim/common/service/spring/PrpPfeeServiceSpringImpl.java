package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpPfeeService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPfee;
import com.sinosoft.claim.schema.model.PrpPfeeId;

public class PrpPfeeServiceSpringImpl extends GenericDaoHibernate<PrpPfee,PrpPfeeId> implements PrpPfeeService {

	@Override
	public ArrayList<PrpPfee> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select EndorseNo," +
        " PolicyNo," +
        " RiskCode," +
        " Currency," +
        " ChgAmount," +
        " ChgPremium," +
        " Flag From PrpPfee Where " + conditions;
		PrpPfee prpPfee = null;
		ArrayList<PrpPfee> resultList = new ArrayList<PrpPfee>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpPfee=new PrpPfee();
            prpPfee.getId().setEndorseNo(String.valueOf(object[0]));
            prpPfee.setPolicyNo(String.valueOf(object[1]));
            prpPfee.setRiskCode(String.valueOf(object[2]));
            prpPfee.getId().setCurrency(String.valueOf(object[3]));
            prpPfee.setChgAmount(Double.parseDouble(String.valueOf(object[4])));
            prpPfee.setChgPremium(Double.parseDouble(String.valueOf(object[5])));
            prpPfee.setFlag(String.valueOf(object[6]));
            resultList.add(prpPfee);
		}
		return resultList;
	}

}
