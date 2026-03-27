package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLDOCARCHIVE接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;


import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpLDocArchiveServiceSpringImpl extends
GenericDaoHibernate<PrpLDocArchive, String> implements PrpLDocArchiveService{

	@Override
	public void save(PrpLDocArchive prpLDocArchive) throws Exception {
		logger.info("保存PRPLDOCARCHIVE信息");
		super.save(prpLDocArchive);
		
	}

	@Override
	public void save(List<PrpLDocArchive> list) throws Exception {
		logger.info("保存PRPLDOCARCHIVE");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除PRPLDOCARCHIVE编号为" + claimNo + "的PRPLDOCARCHIVE");
		super.deleteByPK(PrpLDocArchive.class, claimNo);
	}

	@Override
	public PrpLDocArchive findPrpLDocArchive(String claimNo) throws Exception {
		logger.info("查询PRPLDOCARCHIVE编号为" + claimNo + "的PRPLDOCARCHIVE");
		return super.get(PrpLDocArchive.class,claimNo);
	}
    
	@Override
	public Page findPrpLDocArchive(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLDOCARCHIVE列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}
	/**
	 * 更具sql语句条件，查询page对象信息
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize)throws Exception {
		logger.info("获取PRPLDOCARCHIVE列表信息");
		String sql = "select * from PrpLDocArchive where "+conditions;
		if(pageSize<=0){
			List<?> resultList = HibernateUtils.findbySql(super.getSession(), sql,PrpLDocArchive.class);
			return new Page((pageNo - 1) * pageSize, HibernateUtils.getCountbySql(super.getSession(), sql), pageSize, resultList);
		}else{
			return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLDocArchive.class);
		}
	}
	@Override
	public List<PrpLDocArchive> findPrpLDocArchive(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	@Override
	public void update(PrpLDocArchive prpLDocArchive){
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("UPDATE PrpLDocArchive SET ");
		buffer.append("SumDutyPaid = '"+prpLDocArchive.getSumDutyPaid()+"', ");
		buffer.append("Status = '"+prpLDocArchive.getStatus()+"', ");
		buffer.append("ApplicantCode = '"+prpLDocArchive.getApplicantCode()+"', ");
		buffer.append("ApplicantName = '"+prpLDocArchive.getApplicantName()+"', ");
		DateTime ApplyDate = new DateTime(prpLDocArchive.getApplyDate(), DateTime.YEAR_TO_DAY);
		buffer.append("ApplyDate = to_date('"+ApplyDate+"','YYYY-mm-dd'), ");
		DateTime StartReviewDate = new DateTime(prpLDocArchive.getStartReviewDate(), DateTime.YEAR_TO_DAY);
		buffer.append("StartReviewDate = to_date('"+StartReviewDate+"','YYYY-mm-dd'), ");
		buffer.append("EstimatePeriod = '"+(prpLDocArchive.getEstimatePeriod() == null ? "" : prpLDocArchive.getEstimatePeriod() )+"', ");
		buffer.append("ApplyDeferno = '"+(prpLDocArchive.getApplyDeferno() == null ? "" : prpLDocArchive.getApplyDeferno())+"', ");
		buffer.append("ApplyDeferPeriod = '"+(prpLDocArchive.getApplyDeferPeriod() == null ? "" : prpLDocArchive.getApplyDeferPeriod() )+"', ");
		DateTime EstimateReturnDate = new DateTime(prpLDocArchive.getEstimateReturnDate(), DateTime.YEAR_TO_DAY);
		buffer.append("EstimateReturnDate = to_date('"+EstimateReturnDate+"','YYYY-mm-dd'), ");
		DateTime ReturnDate = new DateTime(prpLDocArchive.getReturnDate(), DateTime.YEAR_TO_DAY);
		buffer.append("ReturnDate = to_date('"+ReturnDate+"','YYYY-mm-dd')");
		buffer.append("WHERE ");
        buffer.append("ClaimNo = '"+prpLDocArchive.getClaimNo()+"'");
		super.getSession().createSQLQuery(buffer.toString()).executeUpdate();
	}
}
