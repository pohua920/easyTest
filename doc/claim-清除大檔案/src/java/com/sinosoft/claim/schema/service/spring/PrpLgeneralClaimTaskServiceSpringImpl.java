package com.sinosoft.claim.schema.service.spring;

/**
 * 通赔任务信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTask;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskService;

public class PrpLgeneralClaimTaskServiceSpringImpl extends GenericDaoHibernate<PrpLgeneralClaimTask, String> implements PrpLgeneralClaimTaskService {

	@Override
	public void save(PrpLgeneralClaimTask prpLgeneralClaimTask) throws Exception {
		logger.info("保存通赔任务信息");
		super.save(prpLgeneralClaimTask);
	}

	@Override
	public void save(List<PrpLgeneralClaimTask> list) throws Exception {
		logger.info("保存通赔任务信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String serialNo) throws Exception {
		logger.info("删除通赔任务信息编号为" + serialNo + "的通赔任务信息");
		super.deleteByPK(PrpLgeneralClaimTask.class, serialNo);
	}

	@Override
	public PrpLgeneralClaimTask findPrpLgeneralClaimTask(String serialNo) throws Exception {
		logger.info("查询通赔任务信息编号为" + serialNo + "的通赔任务信息");
		return super.get(PrpLgeneralClaimTask.class, serialNo);
	}

	@Override
	public Page findPrpLgeneralClaimTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取通赔任务信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<PrpLgeneralClaimTask> findPrpLgeneralClaimTask(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 通过表名取得该表的sequence的nextval
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public long getSeqNextVal(String tableName) throws Exception {
		long value = 0;
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("SELECT ");
		buffer.append(tableName.toUpperCase());
		buffer.append("_SEQ.NEXTVAL FROM DUAL");
		Number num = (Number) super.getSession().createSQLQuery(buffer.toString()).uniqueResult();
		if (num != null) {
			value = num.longValue();
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page findPrpLgeneralClaimTask(String conditions, int pageNo, int pageSize) throws Exception {
		logger.info("获取通赔任务信息列表信息");
		String statement = "";
		statement = "Select distinct registno,policyno,givecomname,receivecomname,giveoperatorname,givetime,currentnode From prpLgeneralClaimTask where " + conditions;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Page page = HibernateUtils.findPagebySql(session, statement, pageNo, pageSize);
		List<PrpLgeneralClaimTask> resultList = new ArrayList<PrpLgeneralClaimTask>();
		List<PrpLgeneralClaimTask> tempList = page.getResult();
		PrpLgeneralClaimTask prpLgeneralClaimTask = null;
		Object[] object = null;
		for (Iterator<?> it = tempList.iterator(); it.hasNext(); resultList.add(prpLgeneralClaimTask)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLgeneralClaimTask = new PrpLgeneralClaimTask();
			prpLgeneralClaimTask.setRegistNo((String) object[0]);
			prpLgeneralClaimTask.setPolicyNo((String) object[1]);
			prpLgeneralClaimTask.setGiveComName((String) object[2]);
			prpLgeneralClaimTask.setReceiveComName((String) object[3]);
			prpLgeneralClaimTask.setGiveOperatorName((String) object[4]);
			if (object[5] != null) {
				prpLgeneralClaimTask.setGiveTime(new Date(((Timestamp) object[5]).getTime()));
			} else {
				prpLgeneralClaimTask.setGiveTime(null);
			}
			prpLgeneralClaimTask.setCurrentNode((String) object[6]);
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);

	}
	
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception {
		logger.info("删除通赔任务信息报案号为" + registNo + "的通赔任务信息");
		String sql = "delete from PrpLgeneralClaimTask where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}

}
