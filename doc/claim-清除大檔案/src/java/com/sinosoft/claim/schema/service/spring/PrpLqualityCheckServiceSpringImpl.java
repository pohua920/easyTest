package com.sinosoft.claim.schema.service.spring;
/**
 * 质量评审内容信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLqualityCheckId;
import com.sinosoft.claim.schema.service.facade.PrpLqualityCheckService;

public class PrpLqualityCheckServiceSpringImpl extends
GenericDaoHibernate<PrpLqualityCheck, PrpLqualityCheckId> implements PrpLqualityCheckService{

	@Override
	public void save(PrpLqualityCheck prpLqualityCheck) throws Exception {
		logger.info("保存质量评审内容信息");
		super.save(prpLqualityCheck);
		
	}

	@Override
	public void save(List<PrpLqualityCheck> list) throws Exception {
		logger.info("保存质量评审内容信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLqualityCheck> list)throws Exception{
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(PrpLqualityCheck prpLqualityCheck)throws Exception{
		if(prpLqualityCheck!=null){
			super.getSession().saveOrUpdate(prpLqualityCheck);
		}
	}
	@Override
	public void delete(PrpLqualityCheckId prpLqualityCheckId) throws Exception {
		logger.info("删除质量评审内容信息编号为" + prpLqualityCheckId + "的质量评审内容信息");
		super.deleteByPK(PrpLqualityCheck.class, prpLqualityCheckId);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLqualityCheck where registNo='"+registNo+"'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
//	@Override
//	public PrpLqualityCheck findPrpLqualityCheck(PrpLqualityCheckId prpLqualityCheckId) throws Exception {
//		logger.info("查询质量评审内容信息编号为" + prpLqualityCheckId + "的质量评审内容信息");
//		return super.get(PrpLqualityCheck.class, prpLqualityCheckId);
//	}

	@Override
	public Page findPrpLqualityCheck(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取质量评审内容信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLqualityCheck> findPrpLqualityCheck(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据质量评审内容编号查询出质量评审内容信息
	 * @param certiNo ：传入的质量评审内容编号
	 * @return 返回质量评审内容
	 */
	public PrpLqualityCheck findPrpLqualityCheck(String certiNo) throws Exception{
		PrpLqualityCheck prpLqualityCheck = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLqualityCheck> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLqualityCheck = resultList.get(0);
		}
		return prpLqualityCheck;
	}

	@Override
	public PrpLqualityCheck findByPrpLqualityCheckId(PrpLqualityCheckId prpLqualityCheckId) throws Exception {
		return super.get(PrpLqualityCheck.class, prpLqualityCheckId);
	}

	@Override
	public void delete(String registNo, String qualityCheckType) throws Exception {
		String sql = " DELETE FROM PrpLqualityCheck Where registNo = '" + registNo + "' and QualityCheckType='"+qualityCheckType+"'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
