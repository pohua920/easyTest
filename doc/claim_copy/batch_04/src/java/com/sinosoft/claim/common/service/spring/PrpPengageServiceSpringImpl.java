package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpPengageService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPengage;
import com.sinosoft.claim.schema.model.PrpPengageId;

public class PrpPengageServiceSpringImpl extends GenericDaoHibernate<PrpPengage, PrpPengageId> implements PrpPengageService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
	@Override
	public ArrayList<PrpPengage> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
        String statement = "Select EndorseNo," +
        " PolicyNo," +
        " RiskCode," +
        " SerialNo," +
        " LineNo," +
        " ClauseCode," +
        " Clauses," +
        " Flag From PrpPengage Where " + conditions;
        PrpPengage prpPengage = null;
		ArrayList<PrpPengage> resultList = new ArrayList<PrpPengage>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpPengage = new PrpPengage();
            prpPengage.getId().setEndorseNo(String.valueOf(object[0]));
            prpPengage.setPolicyNo(String.valueOf(object[1]));
            prpPengage.setRiskCode(String.valueOf(object[2]));
            prpPengage.getId().setSerialNo(Integer.parseInt(String.valueOf(object[3])));
            prpPengage.getId().setLineNo(Integer.parseInt(String.valueOf(object[4])));
            prpPengage.setClauseCode(String.valueOf(object[5]));
            prpPengage.setClauses(String.valueOf(object[6]));
            prpPengage.setFlag(String.valueOf(object[7]));
            resultList.add(prpPengage);
		}
		return resultList;
	}

}
