package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護  START
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
import com.sinosoft.sysframework.exceptionlog.UserException;
//mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護  END

/**
 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
 */
public class PrpLuserServiceSpringImpl extends GenericDaoHibernate<PrpLuser, String> implements PrpLuserService {

	@Override
	public void save(PrpLuser prpLuser) throws Exception {
		logger.info("保存PrpLuser信息");
		super.save(prpLuser);
		
	}

	@Override
	public void save(List<PrpLuser> list) throws Exception {
		logger.info("保存PrpLuser信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除PrpLuser信息编号为" + Id + "的PrpLuser信息");
		super.deleteByPK(PrpLuser.class, Id);
	}

	@Override
	public PrpLuser findPrpLuser(String Id) throws Exception {
		logger.info("查询PrpLuser信息编号为" + Id + "的PrpLuser信息");
		return super.get(PrpLuser.class, Id);
	}

	@Override
	public Page findPrpLuser(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PrpLuser信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLuser> findPrpLuser(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

	public PrpLuser findPrpLuserByUserCode(String userCode) throws Exception{
		PrpLuser prpLuser = null;
		if(null==userCode){
			return prpLuser;
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("userCode", userCode);
		List<PrpLuser> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLuser = resultList.get(0);
		}
		return prpLuser;
	}

	public PrpLuser findPrpLuserById(String id) throws Exception{
		PrpLuser prpLuser = null;
		if(null==id){
			return prpLuser;
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id", id);
		List<PrpLuser> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLuser = resultList.get(0);
		}
		return prpLuser;
	}
	
	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護
	 * @param statements
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Page findPrpLuser(String statements, Object[] params, int pageNo, int pageSize) {
		Session session = super.getSession();
		long count = 0L;
		Query query;
		List<?> resultList;
		if (pageNo > 0 && pageSize > 0) {
			// 統計數量
			String countSQL = "select count(0) from PrpLuser where " + statements ;
			query = session.createSQLQuery(countSQL);
			if (!CommonUtils.isEmpty(params)) {
				for (int i = 0, l = params.length; i < l; i++) {
					query.setParameter(i, params[i]);
				}
			}
			resultList = query.list();
			if (resultList.size() > 0) {
				BigDecimal object = (BigDecimal) resultList.get(0);
				count = object.longValue();
			}
			if (count == 0) {
				return new Page((pageNo - 1) * pageSize, 0L, pageSize, new ArrayList());
			}
		}
		query = session.createSQLQuery("select * from PrpLuser where " + statements ).addEntity(PrpLuser.class) ;
		if (!CommonUtils.isEmpty(params)) {
			for (int i = 0, l = params.length; i < l; i++) {
				query.setParameter(i, params[i]);
			}
		}
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return new Page((pageNo - 1) * pageSize, count, pageSize, resultList);
	}
	
	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	 */
	public Date getRecordDateByUserCode(String userCode) throws UserException, Exception {
		
		String strConfig = "SELECT CREATTIME FROM BUSINESS.prpluser where userCode='"+userCode+"'";
			
		Date rtn = (Date)super.getSession().createSQLQuery(strConfig).uniqueResult();
		
		return rtn;
	}
	
	/**
	 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
	 */
	public int countPrpLuserByUserCode(String userCode) throws Exception{
		String strConfig = "SELECT count(CREATTIME) FROM BUSINESS.prpluser where userCode='"+userCode+"'";
		int rtn = new Integer(super.getSession().createSQLQuery(strConfig).uniqueResult().toString());
		return rtn;
	}
}
