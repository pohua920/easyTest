package com.sinosoft.claim.schema.service.spring;
/**
 * 人员核保核赔条件表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UtiUwUserCondition;
import com.sinosoft.claim.schema.model.UtiUwUserConditionId;
import com.sinosoft.claim.schema.service.facade.UtiUwUserConditionService;

public class UtiUwUserConditionServiceSpringImpl extends GenericDaoHibernate<UtiUwUserCondition, UtiUwUserConditionId> implements UtiUwUserConditionService {

	@Override
	public void deleteByConditions(String conditions) throws Exception {
		StringBuffer buffer1 = new StringBuffer(100);
		buffer1.append("DELETE FROM UtiUwUserCondition WHERE ");
		buffer1.append(conditions);
		super.getSession().createSQLQuery(buffer1.toString()).executeUpdate();
	}

	@Override
	public List<UtiUwUserCondition> findGroupByConditions(String conditions) throws Exception {
		StringBuffer str1 = new StringBuffer(200);
		str1.append("SELECT ");
		str1.append("ComCode,ModelNo,NodeNo,RiskCategoryCode,UwType,ClassCode,FactorCode,FactorValueNo,FactorValue,Remark,CreateTime,ValidStatus,UserCode ");
		str1.append("FROM UtiUwUserCondition WHERE ");
		StringBuffer str2 = new StringBuffer(200);
		str2.append(" GROUP BY ");
		str2.append("ComCode,ModelNo,NodeNo,RiskCategoryCode,UwType,ClassCode,FactorCode,FactorValueNo,FactorValue,Remark,CreateTime,ValidStatus,UserCode");
		StringBuffer str = new StringBuffer(200);
		str.append(str1);
		str.append(conditions);
		str.append(str2);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList =  HibernateUtils.findbySql(session, str.toString(), 0, 0);
		List<UtiUwUserCondition> resultList = new ArrayList<UtiUwUserCondition>();
		UtiUwUserCondition utiUwUserCondition=null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			utiUwUserCondition=new UtiUwUserCondition();
			UtiUwUserConditionId utiUwUserConditionId=new UtiUwUserConditionId();
			utiUwUserConditionId.setComCode((String) object[0]);
			utiUwUserConditionId.setModelNo(new Integer((String) object[1]));
			utiUwUserConditionId.setNodeNo(new Integer((String) object[2]));
			utiUwUserCondition.setRiskCategoryCode((String) object[3]);
			utiUwUserConditionId.setUwType((String) object[4]);
			utiUwUserCondition.setClassCode((String) object[5]);
			utiUwUserConditionId.setFactorCode((String) object[6]);
			utiUwUserConditionId.setFactorValueNo(new Integer((String) object[7]));
			utiUwUserCondition.setFactorValue((String) object[8]);
			utiUwUserCondition.setRemark((String) object[9]);
			utiUwUserCondition.setCreateTime((String) object[10]);
			utiUwUserCondition.setValidStatus((String) object[11]);
			utiUwUserConditionId.setUserCode((String) object[12]);
			utiUwUserCondition.setId(utiUwUserConditionId);
			resultList.add(utiUwUserCondition);
			}
		return resultList;
	}

	@Override
	public void insertAll(List<?> list) throws Exception {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

	@Override
	public List<UtiUwUserCondition> findFactorValueByConditions(String conditions) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT UserCode,FactorCode,FactorValue,FactorValueNo FROM UtiUwUserCondition WHERE ");
		statement.append(conditions);
		statement.append(" GROUP BY UserCode,FactorCode,FactorValue,FactorValueNo Order By FactorValueNo");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), 0, 0);
		UtiUwUserCondition utiUwUserCondition=null;
		ArrayList<UtiUwUserCondition> resultList = new ArrayList<UtiUwUserCondition>();
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			utiUwUserCondition = new UtiUwUserCondition();
			utiUwUserCondition.getId().setUserCode(String.valueOf(object[0]));
			utiUwUserCondition.getId().setFactorCode(String.valueOf(object[1]));
			utiUwUserCondition.setFactorValue(String.valueOf(object[2]));
			utiUwUserCondition.getId().setFactorValueNo(new Integer(String.valueOf(object[2])));
			resultList.add(utiUwUserCondition);
		}
		return resultList;
	}
	public List<UtiUwUserCondition> findByConditions(String sql)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sql);
		return super.find(queryRule);
	}

}
