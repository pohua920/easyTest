package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.model.PrpDcompanyTrace;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyTraceService;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDcompanyTraceServiceSpringImpl extends
		GenericDaoHibernate<PrpDcompanyTrace, Integer> implements
		PrpDcompanyTraceService {

	private static Log logger = LogFactory
			.getLog(PrpDcompanyTraceServiceSpringImpl.class);

	/**
	 * 根据机构代码得到实体
	 */
	public PrpDcompanyTrace findByPrimaryKey(Integer serialNo) {
		PrpDcompanyTrace prpDcompanyTrace = super.get(serialNo);
		return prpDcompanyTrace;
	}
	/**
	 * 分页查询(查询的是当前申请状态为1的记录)
	 */
	public Page getPrpDcompanyTraceNotAuditList(PrpDcompanyTrace prpDcompanyTrace,int pageNo,int pageSize){
		String hql = "";
		hql = " from PrpDcompanyTrace prpDcompanyTrace where prpDcompanyTrace.currentStatus = 1 ";
		Page page = super.findByHql(hql, pageNo, pageSize);
		return page;
	}
	/**
	 * 查看所有申请记录
	 */
	public List<PrpDcompanyTrace> getPrpDcompanyTraceList() {
		String hql = " from PrpDcompanyTrace prpDcompanyTrace where 1=1 ";
		List<PrpDcompanyTrace> prpDcompanyTraceList = super
				.findByHql(hql, null);
		return prpDcompanyTraceList;
	}

	/**
	 * 分页查询(PrpDcompanyTrace表的所有记录)
	 */
	public Page getPrpDcompanyTraceList(PrpDcompanyTrace prpDcompanyTrace,
			int pageNo, int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDcompanyTrace prpDcompanyTrace where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("prpDcompanyTrace.comCode", prpDcompanyTrace
				.getComCode());
		hqlRules.addLike("prpDcompanyTrace.comCName", prpDcompanyTrace
				.getComCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {// 如果用户在查询框输入条件时
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("!!!!!!!!!!!!" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	/**
	 * 向PrpDcompanyTrace表中添加数据
	 */
	public void insertPrpDcompanyTrace(PrpDcompanyTrace prpDcompanyTrace) {
		/**
		 * 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* start 2009-10-28
		 */
		String comCode = prpDcompanyTrace.getComCode();		
		String upperComCode = prpDcompanyTrace.getUpperComCode();
		String upperPath = generateupperPath(comCode,upperComCode);
		prpDcompanyTrace.setUpperPath(upperPath);
		/** 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* end */
		String[] level = upperPath.split(",");
		int comLevel = level.length;
		prpDcompanyTrace.setComLevel(new BigDecimal(comLevel));		
		int maxId = findByMaxId("PrpDcompanyTrace","serialNo");
		prpDcompanyTrace.setSerialNo(maxId);
		super.save(prpDcompanyTrace);
	}
	/**
	 * 生成prpDcompanyTrace表中的级别upperPath字段数据
	 * 
	 * @param upperComCode
	 *            当前机构的上级机构代码字段值 uppergrade 当前机构的上级机构的upperPath字段值
	 * @return 当前机构的upperPath字段值
	 */
	private String generateupperPath(String comCode, String upperComCode) {
		String uppergrade = "";
		StringBuffer hql = new StringBuffer();
		StringBuffer grade = new StringBuffer();
		hql.append("select upperPath from PrpDcompany where comCode = '");
		hql.append(upperComCode);
		hql.append("'");
		List<String> upperPaths = super.findByHql(hql.toString());
		if (upperPaths.size() > 0) {
			uppergrade = upperPaths.get(0);
		}
		grade.append(uppergrade);
		// TODO 判断代码是不是总公司代码
		//modify by duanfa start 20110726 总公司改为31000000
		//if (!"00000000".equals(comCode)) {
		if (!"31000000".equals(comCode)) {
		//modify by duanfa end 20110726
			grade.append(",");
			grade.append(comCode);
		}
		return grade.toString();
	}

	/**
	 * 更改审批数据
	 */
	public void updatePrpDcompanyTrace(PrpDcompanyTrace prpDcompanyTrace) {
		/**
		 * 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* start 2009-10-28
		 */
		String comCode = prpDcompanyTrace.getComCode();		
		String upperComCode = prpDcompanyTrace.getUpperComCode();
		String upperPath = generateupperPath(comCode,upperComCode);
		prpDcompanyTrace.setUpperPath(upperPath);
		/** 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* end */
		String[] level = upperPath.split(",");
		int comLevel = level.length;
		prpDcompanyTrace.setComLevel(new BigDecimal(comLevel));		
		super.update(prpDcompanyTrace);
	}
	
	/**
	 *  查找PrpDcompanyTrace表中当前存在的最大ID值
	 */
	public Integer findByMaxId(String className,String key){
		String sql = "select max(" + key + ") from " + className + " temp " ;
		int maxId  = 0;
		List result = super.findByHql(sql);
		if( result != null && result.size() > 0){			
			Object o = result.get(0);
			if (null != o) {
				maxId = (Integer)o;
				maxId++;
			}else {
				maxId = 1;
			}		
		}
		return maxId;
	}
}
