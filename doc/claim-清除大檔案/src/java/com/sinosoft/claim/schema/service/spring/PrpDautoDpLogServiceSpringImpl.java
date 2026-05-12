package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDautoDpLog;
import com.sinosoft.claim.schema.service.facade.PrpDautoDpLogService;

/**
 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
 */
public class PrpDautoDpLogServiceSpringImpl extends GenericDaoHibernate<PrpDautoDpLog, String> implements PrpDautoDpLogService {

	@Override
	public void save(PrpDautoDpLog prpDautoDpLog) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prpDautoDpLog);

	}

	@Override
	public void save(List<PrpDautoDpLog> list) throws Exception {
		logger.info("保存立案基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpDautoDpLog prpDautoDpLog) throws Exception {
		logger.info("保存立案基本信息");
		super.getSession().merge(prpDautoDpLog);

	}

	@Override
	public void saveOrUpdate(List<PrpDautoDpLog> list) throws Exception {
		logger.info("保存立案基本信息");
		for (int i = 0; i < list.size(); i++) {
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

//	@Override
//	public void delete(String logId) throws Exception {
//		logger.info("删除立案基本信息编号为" + logId + "的立案基本信息");
//		super.deleteByPK(PrpDautoDpLog.class, logId);
//	}

	@Override
	public List<PrpDautoDpLog> findPrpDautoDpLog(String logId) throws Exception {
		logger.info("查询立案基本信息编号为" + logId + "的立案基本信息");

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.logId", logId);
		return this.find(queryRule);
	}
	
	/**
	 * 
	 */
	@Override
	public List<PrpDautoDpLog> findPrpDautoDpLogStatus(PrpDautoDpLog prpDautoDpLog) throws Exception {
		logger.info("查询立案基本信息编号为" + prpDautoDpLog + "的立案基本信息");
		
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("compensateNo", prpDautoDpLog.getCompensateNo());
		if(null!=prpDautoDpLog.getInputStatus() && ""!=prpDautoDpLog.getInputStatus().trim()){
			queryRule.addEqual("inputStatus", prpDautoDpLog.getInputStatus());
		}
		queryRule.addDescOrder("inputDate");
		
		List<PrpDautoDpLog> rtn = this.find(queryRule);
		return rtn;
	}
	
	@Override
	public void update(List<PrpDautoDpLog> prpDautoDpLogList) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
