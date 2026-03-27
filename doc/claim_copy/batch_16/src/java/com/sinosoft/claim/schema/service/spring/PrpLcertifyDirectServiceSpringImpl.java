/*
 * @(#)PrpLcertifyDirectServiceSpringImpl.java	Jan 24, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import org.hibernate.Session;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLcertifyDirectId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyDirectService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class PrpLcertifyDirectServiceSpringImpl extends GenericDaoHibernate<PrpLcertifyDirect, PrpLcertifyDirectId> implements PrpLcertifyDirectService{
	/* （非 Javadoc）保存表prpLcertifyCollect信息
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(com.sinosoft.claim.schema.model.PrpLcertifyCollect)
	 * 
	 */
	public void save(PrpLcertifyDirect prplcertifydirect) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prplcertifydirect);
	}

	
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(java.util.List)
	 * 保存所有的对象
	 */
	public void save(List<PrpLcertifyDirect> list) throws Exception {
		logger.info("保存立案基本信息");
		if(list!=null&&list.size()>0){
			super.saveAll(list);
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyDirect> list)throws Exception{
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
	public void saveOrUpdate(PrpLcertifyDirect prpLcertifyDirect)throws Exception{
		if(prpLcertifyDirect!=null){
			super.getSession().saveOrUpdate(prpLcertifyDirect);
		}
	}
	
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#delete(java.lang.String)
	 * 根据主键删除条件
	 */
	public void delete(PrpLcertifyDirectId prplcertifyDirectId) throws Exception {
		logger.info("删除立案基本信息编号为" + prplcertifyDirectId + "的立案基本信息");
		super.deleteByPK(PrpLcertifyDirectId.class, prplcertifyDirectId);
	}
	@Override
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from PrpLcertifyDirect where registNo='"+registNo+"'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findByPrpLcertifyCollectId(com.sinosoft.claim.schema.model.PrpLcertifyCollectId)
	 * 根据主键查询出对象
	 */
	public PrpLcertifyDirect findByPrpLcertifyDirectId(PrpLcertifyDirectId prpLcertifyDirectId)throws Exception{
		return super.get(PrpLcertifyDirect.class, prpLcertifyDirectId);
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule, int, int)
	 *查询【page对象，页面分页
	 */
	public Page findPrpLcertifyDirect(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule)
	 * 查询出所有的值
	 */
	public List<PrpLcertifyDirect> findPrpLcertifyDirect(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 联合查询
	 */
	public List<PrpLcertifyDirect> findPrpLcertifyDirect(String registNo) throws Exception{
		String sql = "(registNo,typecode) in ( select registNo,typecode from prplcertifydirect where"
			+" registno = '"
			+ registNo
			+ "'and not exists "
			+ "(select * from SFM_FILEINDEX "
			+ "where (prplcertifydirect.typecode = SFM_FILEINDEX.typepath4 or prplcertifydirect.typecode = SFM_FILEINDEX.typepath5) and prplcertifydirect.registno = SFM_FILEINDEX.bussNo)"
			+")";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule = queryRule.addSql(sql);
		return super.find(queryRule);
	}

}
