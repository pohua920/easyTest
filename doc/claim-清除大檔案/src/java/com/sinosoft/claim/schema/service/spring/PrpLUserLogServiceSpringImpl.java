package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.List;


import com.sinosoft.claim.schema.model.PrpLUserLog;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.service.facade.PrpLUserLogService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
 */
public class PrpLUserLogServiceSpringImpl extends GenericDaoHibernate<PrpLUserLog, String> implements PrpLUserLogService {

	@Override
	public void save(PrpLUserLog prpLUserLog) throws Exception {
		logger.info("保存PrpLUserLog信息");
		super.save(prpLUserLog);
		
	}

	@Override
	public void save(List<PrpLUserLog> list) throws Exception {
		logger.info("保存PrpLUserLog信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除PrpLUserLog信息编号为" + Id + "的PrpLUserLog信息");
		super.deleteByPK(PrpLUserLog.class, Id);
	}

	@Override
	public PrpLUserLog findPrpLUserLog(String Id) throws Exception {
		logger.info("查询PrpLUserLog信息编号为" + Id + "的PrpLUserLog信息");
		return super.get(PrpLUserLog.class, Id);
	}

	@Override
	public Page findPrpLUserLog(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PrpLUserLog信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLUserLog> findPrpLUserLog(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	public Double getMax() throws UserException, Exception {
		
		String strConfig = "SELECT max(OID) FROM BUSINESS.prpluserlog ";
			
		BigDecimal rtn = (BigDecimal)super.getSession().createSQLQuery(strConfig).uniqueResult();
		
		if(rtn!=null){
			return rtn.doubleValue();
		}else {
			return new Double(0);
		}
	}
}
