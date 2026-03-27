package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpPtextService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPtext;
import com.sinosoft.claim.schema.model.PrpPtextId;

public class PrpPtextServiceSpringImpl extends GenericDaoHibernate<PrpPtext, PrpPtextId> implements PrpPtextService {

	@Override
	public ArrayList<PrpPtext> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
        String statement = "Select EndorseNo," +
        " PolicyNo," +
        " LineNo," +
        " EndorseText," +
        " Flag From PrpPtext Where " + conditions;
		PrpPtext prpPtext = null;
		ArrayList<PrpPtext> resultList = new ArrayList<PrpPtext>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpPtext=new PrpPtext();
            prpPtext.getId().setEndorseNo(String.valueOf(object[0]));
            prpPtext.getId().setPolicyNo(String.valueOf(object[1]));
            prpPtext.getId().setLineNo(Integer.parseInt(String.valueOf(object[2])));
            prpPtext.setEndorseText(String.valueOf(object[3]));
            prpPtext.setFlag(String.valueOf(object[4]));
            resultList.add(prpPtext);
		}
		return resultList;
	}

}
