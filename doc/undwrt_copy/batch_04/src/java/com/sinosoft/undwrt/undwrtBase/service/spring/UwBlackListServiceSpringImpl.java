package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackList;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackListId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwBlackListService;

/**
 * 核保黑名單接口實現類
 * 
 * @author sinoSoft
 * 
 * @created 2013-12-29
 */
public class UwBlackListServiceSpringImpl extends GenericDaoHibernate<UwBlackList,UwBlackListId> implements UwBlackListService{

	/**
	 * 根據條件查詢核保黑名單列表.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 核保黑名單列表
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwBlackListService#getUwBlackList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<UwBlackList> getUwBlackList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

	@Override
	public void saveBlackMaintenance(UwBlackList uwBlackList) {
		// TODO Auto-generated method stub
		this.save(uwBlackList);
	}

	@Override
	public String getStatement(String identifyNumber, String riskCode,
			boolean nodeStatusView) {
		// TODO Auto-generated method stub
       StringBuffer  stateMent =new StringBuffer();
       stateMent.append("select *  from UwBlackList where 1=1 " );
       stateMent.append(" and blackListType ='E' ");
       if(identifyNumber!=null&&!"".equals(identifyNumber))
       stateMent.append(" and blackListCode = '"+identifyNumber+"' ");
/*       if(riskCode!=null&&!"".equals(riskCode))
       stateMent.append(" and riskCode = '"+riskCode+"' ");*/
            
		return stateMent.toString();
	}

	@Override
	public Page findByStatement(String statement, int pageNo, int rowsPerPage,
			boolean nodeStatusView) {
		// TODO Auto-generated method stub
		Session session = super.getSession();
		SQLQuery query = session.createSQLQuery(statement).addEntity(UwBlackList.class);
		List<UwBlackList> list = query.list();
		List collection = new ArrayList();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= list.size()) {
				break;
			}
			collection.add(list.get(i));
		}
		Page page = new Page(0, collection.size(), 15, collection);
		return page;
	}

	@Override
	public PageRecord findByStatementPageRecord(String statement, int pageNo,
			int rowsPerPage, boolean nodeStatusView) {
		// TODO Auto-generated method stub
		Session session = super.getSession();
		SQLQuery query = session.createSQLQuery(statement).addEntity(UwBlackList.class);
		List<UwBlackList> list = query.list();
		List collection = new ArrayList();
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= list.size()) {
				break;
			}
			collection.add(list.get(i));
		}
		PageRecord pageRecord = new PageRecord(list.size(), pageNo, 1, rowsPerPage, collection);
		return pageRecord;
	}

	@Override
	public void updateBlackList(UwBlackList uwBlackList) {
		// TODO Auto-generated method stub
		this.update(uwBlackList);
	}

	@Override
	public void deleteBlackList(List<UwBlackList> list) {
		// TODO Auto-generated method stub
		this.deleteAll(list);
	}

	@Override
	public PrpDBankInfo queryBankInfo(String bankCode) {
		// TODO Auto-generated method stub
		PrpDBankInfo  prpDBankInfo  =new PrpDBankInfo();
		Session session = super.getSession();
		String  sql ="select  *  from PrpDBankInfo where bankCode='"+bankCode+"'";
		SQLQuery query = session.createSQLQuery(sql).addEntity(PrpDBankInfo.class);
		List<PrpDBankInfo> list = query.list();
		if(list!=null&&list.size()>0){
			prpDBankInfo=list.get(0);
		}
		return prpDBankInfo;
	}


}
