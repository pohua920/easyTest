/*
 * @(#)PrpLcertifyCollectServiceSpringImpl.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyCollectId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyCollectService;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class PrpLcertifyCollectServiceSpringImpl extends GenericDaoHibernate<PrpLcertifyCollect, PrpLcertifyCollectId> implements PrpLcertifyCollectService{
	
	/* （非 Javadoc）保存表prpLcertifyCollect信息
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(com.sinosoft.claim.schema.model.PrpLcertifyCollect)
	 * 
	 */
	public void save(PrpLcertifyCollect prpLcertifyCollect) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prpLcertifyCollect);
	}

	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(List<PrpLcertifyCollect> list)throws Exception{
		logger.info("保存或者修改基本信息");
		if(list==null||list.size()==0){
			return;
		}
		Session session = super.getSession();
		for(int i=0;i<list.size();i++){
			session.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(PrpLcertifyCollect prpLcertifyCollect)throws Exception{
		if(prpLcertifyCollect!=null){
			super.getSession().saveOrUpdate(prpLcertifyCollect);
		}
	}
	
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(java.util.List)
	 * 保存所有的对象
	 */
	public void save(List<PrpLcertifyCollect> list) throws Exception {
		logger.info("保存立案基本信息");
		if(list==null||list.size()==0){
			return;
		}
		super.saveAll(list);
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#delete(java.lang.String)
	 * 根据主键删除条件
	 */
	public void delete(PrpLcertifyCollectId prpLcertifyCollectId) throws Exception {
		logger.info("删除立案基本信息编号为" + prpLcertifyCollectId + "的立案基本信息");
		super.deleteByPK(PrpLcertifyCollect.class, prpLcertifyCollectId);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLcertifyCollect where businessNo='"+registNo+"'";
		Session session = super.getSession();
		session.createSQLQuery(sql).executeUpdate();
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findByPrpLcertifyCollectId(com.sinosoft.claim.schema.model.PrpLcertifyCollectId)
	 * 根据主键查询出对象
	 */
	public PrpLcertifyCollect findByPrpLcertifyCollectId(PrpLcertifyCollectId prpLcertifyCollectId)throws Exception{
		return super.get(PrpLcertifyCollect.class, prpLcertifyCollectId);
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule, int, int)
	 *查询【page对象，页面分页
	 */
	public Page findPrpLcertifyCollect(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule)
	 * 查询出所有的值
	 */
	public List<PrpLcertifyCollect> findPrpLcertifyCollect(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	public List<PrpLcertifyCollect> findByQueryConditions(String conditions)throws Exception{
		List<?> list = this.findByQueryConditions(conditions,0,20).getResult();
		List<PrpLcertifyCollect> prpLcertifyCollectList = new ArrayList<PrpLcertifyCollect>();
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			PrpLcertifyCollect prpLcertifyCollect = (PrpLcertifyCollect)it.next();
			prpLcertifyCollectList.add(prpLcertifyCollect);
		}
		return prpLcertifyCollectList;
	}
	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 * 根据sql语句查询分页方法
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select DISTINCT a.BusinessNo," + "a.StartDate, a.OperatorCode, u.userName, a.CollectFlag, b.OperateDate, b.Status, b.RiskCode, "
				+ " c.LicenseNo From (select * from PrpLClaimStatus where NodeType='certi') b Right JOIN PrpLcertifyCollect a ON a.BusinessNo = b.BusinessNo"
				+ " LEFT JOIN prplregist c ON b.BusinessNo = c.registNo LEFT JOIN prpDuser u on u.usercode=a.OperatorCode where " + conditions;
		String countSql = "select count(*) from (" + statement+")";
		List<PrpLcertifyCollect> collection = new ArrayList<PrpLcertifyCollect>();
		List<?> countList = super.getSession().createSQLQuery(countSql).list();
		long count = 0;
		if (countList != null && countList.get(0) != null) {
			count = ((Number) countList.get(0)).longValue();
		}
//		Page page = null;
		if (count < 1) {
			return new Page(rowsPerPage * (pageNo - 1), count, rowsPerPage, collection);
		}
		StringBuffer buffer = new StringBuffer(200);
		buffer.append(statement);
		if (pageNo > 0) {
			buffer.insert(0, "SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM (");
			buffer.append(") row_ WHERE rownum <= " + rowsPerPage * pageNo + ") WHERE rownum_ > " + rowsPerPage * (pageNo - 1));
		}
		// if(supportPaging==false && pageNo>1){
		// dbManager.locate(resultSet,rowsPerPage * (pageNo - 1));
		// }
		// add by caozhigang 20090319 增加分页处理 end
		PrpLcertifyCollect prpLcertifyCollect = null;
		List<?> resuleSet = super.getSession().createSQLQuery(buffer.toString()).list();
		Object[] resule = null;
		Iterator<?> it = resuleSet.iterator();
		while (it.hasNext()) {
			resule = (Object[])it.next();
			prpLcertifyCollect = new PrpLcertifyCollect();
			prpLcertifyCollect.getId().setBusinessNo(resule[0]==null?"":resule[0].toString());
			prpLcertifyCollect.setStartDate(resule[1]==null?null:new DateTime(resule[1].toString()));
			prpLcertifyCollect.setOperatorCode(resule[2]==null?"":resule[2].toString());
			prpLcertifyCollect.setOperatorName(resule[3]==null?"":resule[3].toString());
			prpLcertifyCollect.setCollectFlag(resule[4]==null?"":resule[4].toString());
			prpLcertifyCollect.setOperateDate(resule[5]==null?null:new DateTime(resule[5].toString()));
			prpLcertifyCollect.setStatus(resule[6]==null?"":resule[6].toString());
			prpLcertifyCollect.setRiskCode(resule[7]==null?"":resule[7].toString());
			collection.add(prpLcertifyCollect);
		}
		return new Page(rowsPerPage * (pageNo - 1), count, rowsPerPage, collection);
	}
}
