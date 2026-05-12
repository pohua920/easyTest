package com.sinosoft.claim.schema.service.spring;
/**
 * 资料归档调阅日志表接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveLogService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpLDocArchiveLogServiceSpringImpl extends
GenericDaoHibernate<PrpLDocArchiveLog, PrpLDocArchiveLogId> implements PrpLDocArchiveLogService {

	@Override
	public void delete(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception {
		logger.info("保存资料归档调阅日志表的数据传输对象类");
		super.deleteByPK(prpLDocArchiveLogId);
	}

	@Override
	public PrpLDocArchiveLog findPrpLDocArchiveLog(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception {
		logger.info("查询资料归档调阅日志表编号为" + prpLDocArchiveLogId + "的资料归档调阅日志表信息");
		return super.get(PrpLDocArchiveLog.class,prpLDocArchiveLogId);
	}

	@Override
	public Page findPrpLDocArchiveLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取资料归档调阅日志表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLDocArchiveLog> findPrpLDocArchiveLog(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 根据条件查询PrpLDocArchiveLog表的信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public List<PrpLDocArchiveLog> findByconditions(String conditions) throws Exception{
		String sql = "select * from PrpLDocArchiveLog where "+conditions;
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql, PrpLDocArchiveLog.class);
		List<PrpLDocArchiveLog> prpLDocArchiveLogList = new ArrayList<PrpLDocArchiveLog>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			PrpLDocArchiveLog prpLDocArchiveLog = (PrpLDocArchiveLog) iterator.next();
			prpLDocArchiveLogList.add(prpLDocArchiveLog);
		}
		return prpLDocArchiveLogList;
	}

	@Override
	public void save(PrpLDocArchiveLog prpLDocArchiveLog) throws Exception{
		logger.info("保存资料归档调阅日志表信息");
		super.getSession().saveOrUpdate(prpLDocArchiveLog);
	}

	@Override
	public void save(List<PrpLDocArchiveLog> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(List<PrpLDocArchiveLog> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	@Override
	public void saveOrUpdate(PrpLDocArchiveLog prpLDocArchiveLog) throws Exception {
		logger.info("保存资料归档调阅日志表信息");
		super.getSession().saveOrUpdate(prpLDocArchiveLog);
	}

	@Override
	public void update(PrpLDocArchiveLog prpLDocArchiveLog) {
		logger.info("修改资料归档调阅日志表信息开始");
		super.update(prpLDocArchiveLog);
		logger.info("修改资料归档调阅日志表信息结束");
	}
	@Override
	public Page findPrpLDocArchiveLog(String sql, int pageNo, int pageSize) throws Exception {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<PrpLDocArchiveLog> resultList = new ArrayList<PrpLDocArchiveLog>();
		List<?> tempList = HibernateUtils.findbySql(session, sql, pageNo, pageSize);
		PrpLDocArchiveLog prpLDocArchiveLog = null;
		if(tempList != null && tempList.size()>0){
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			prpLDocArchiveLog = new PrpLDocArchiveLog();
		    prpLDocArchiveLog.getId().setClaimNo((String) object[0]);
			prpLDocArchiveLog.getId().setSerialNo(((BigDecimal)object[1]).intValue());
			prpLDocArchiveLog.setRegistNo((String) object[2]);
			prpLDocArchiveLog.setPolicyNo((String) object[3]);
			prpLDocArchiveLog.setComcode((String) object[4]);
			prpLDocArchiveLog.setInsuredCode((String) object[5]);
			prpLDocArchiveLog.setInsuredName((String) object[6]);
			prpLDocArchiveLog.setEndCaseDate(object[7] == null ? null:new DateTime(new Date(((Timestamp) object[7]).getTime())));
			prpLDocArchiveLog.setSumDutyPaid(((BigDecimal) object[8]).doubleValue());
			prpLDocArchiveLog.setStatus((String) object[9]);
			prpLDocArchiveLog.setApplyReason((String) object[10]);
			prpLDocArchiveLog.setEstimatePeriod((String) object[11]);
			prpLDocArchiveLog.setApplyDeferno(object[12]==null?0:((BigDecimal) object[12]).intValue());
			prpLDocArchiveLog.setApplyDeferPeriod((String) object[13]);
			prpLDocArchiveLog.setEstimateReturnDate(object[14] == null ? null:new DateTime(new Date(((Timestamp) object[14]).getTime())));
			prpLDocArchiveLog.setReturnDate(object[15] == null ? null:new DateTime(new Date(((Timestamp) object[15]).getTime())));
			prpLDocArchiveLog.setRemark((String) object[16]);
			prpLDocArchiveLog.setUndwrtFlag((String) object[17]);
			prpLDocArchiveLog.setOperatorCode((String) object[18]);
			prpLDocArchiveLog.setOperatorName((String) object[19]);
			prpLDocArchiveLog.setOperatorDate(object[20] == null ? null:new DateTime(new Date(((Timestamp) object[20]).getTime())));
			prpLDocArchiveLog.setModelNo(object[21]==null?0:((BigDecimal) object[21]).intValue());
			prpLDocArchiveLog.setNodeNo(((BigDecimal) object[22]).intValue());
			prpLDocArchiveLog.setNodeName((String) object[23]);
			resultList.add(prpLDocArchiveLog);
			}}
		return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(session, sql), pageSize, resultList);
	}

}
