package com.sinosoft.claim.schema.service.spring;

/**
 * 人员级别设置表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.model.UtiUwLevelId;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;

public class UtiUwLevelServiceSpringImpl extends GenericDaoHibernate<UtiUwLevel, UtiUwLevelId> implements UtiUwLevelService {

	@Override
	public void deleteByConditions(String conditions2) throws Exception {
		StringBuffer buffer2 = new StringBuffer(100);
		buffer2.append("DELETE FROM UtiUwLevel WHERE ");
		buffer2.append(conditions2);
		super.getSession().createSQLQuery(buffer2.toString()).executeUpdate();
	}

	@Override
	public List<UtiUwLevel> findGroupByConditions(String conditions) throws Exception {
		StringBuffer str1 = new StringBuffer(200);
		str1.append("SELECT UserCode,ComCode,ModelNo,NodeNo,StartDate,EndDate,ValidStatus,Flag,UwType FROM UtiUwLevel WHERE ");
		StringBuffer str2 = new StringBuffer(200);
		str2.append(" GROUP BY UserCode,ComCode,ModelNo,NodeNo,StartDate,EndDate,ValidStatus,Flag,UwType Order By UserCode");
		StringBuffer str = new StringBuffer(200);
		str.append(str1);
		str.append(conditions);
		str.append(str2);
		Session session = super.getSession();
		List<UtiUwLevel> tempList = new ArrayList<UtiUwLevel>();
		List<?> list = HibernateUtils.findbySql(session, str.toString(), 0, 0);
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			UtiUwLevel utiUwLevel = (UtiUwLevel) iterator.next();
			tempList.add(utiUwLevel);
		}
		return tempList;
	}

	@Override
	public void insertAll(List<UtiUwLevel> list) throws Exception {
		if (list != null && list.size() > 0) {
			Session session = super.getSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public List<UtiUwLevel> findByConditions(String conditionsLevel) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditionsLevel);
		return super.find(queryRule);
	}

	@Override
	public int getCount(String conditions) throws Exception {
		String sql = "select * from UtiUwLevel where " + conditions;
		Long count = HibernateUtils.getCountbySql(super.getSession(), sql);
		return count.intValue();
	}

	@Override
	public UtiUwLevel findByPrimaryKeyAndValidStatus(String userCode, String calComCode, String riskCode, int modelNo, int nodeNo, String uwType) throws Exception {
		// 拼SQL语句
		String sql = "UserCode= '" + userCode + "' AND ComCode= '" + calComCode + "' AND ModelNo= " + modelNo + " AND riskCode= '" + riskCode + "' AND NodeNo =" + nodeNo + " AND UwType= '" + uwType + "'";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.findUnique(queryRule);
	}
}
