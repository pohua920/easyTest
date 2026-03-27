/*
 * @(#)PrpLcertifyImgServiceSpringImpl.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import org.hibernate.Session;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLcertifyImgId;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyImgService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class PrpLcertifyImgServiceSpringImpl extends GenericDaoHibernate<PrpLcertifyImg, PrpLcertifyImgId> implements PrpLcertifyImgService{
	
	/* （非 Javadoc）保存表prpLcertifyCollect信息
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(com.sinosoft.claim.schema.model.PrpLcertifyCollect)
	 * 
	 */
	public void save(PrpLcertifyImg prpLcertifyImg) throws Exception {
		logger.info("保存立案基本信息");
		super.save(prpLcertifyImg);
	}

	
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#save(java.util.List)
	 * 保存所有的对象
	 */
	public void save(List<PrpLcertifyImg> list) throws Exception {
		logger.info("保存立案基本信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * @param prpLcertifyDirect
	 * @throws Exception
	 * 更新或者保存对象，根据主键判断
	 */
	public void saveOrUpdate(List<PrpLcertifyImg> list)throws Exception{
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
	public void saveOrUpdate(PrpLcertifyImg prpLcertifyImg)throws Exception{
		if(prpLcertifyImg!=null){
			super.getSession().saveOrUpdate(prpLcertifyImg);
		}
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#delete(java.lang.String)
	 * 根据主键删除条件
	 */
	public void delete(PrpLcertifyImgId prpLcertifyImgId) throws Exception {
		logger.info("删除立案基本信息编号为" + prpLcertifyImgId + "的立案基本信息");
		super.deleteByPK(PrpLcertifyImg.class, prpLcertifyImgId);
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		String sql = "delete from PrpLcertifyImg where businessNo='"+registNo+"'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findByPrpLcertifyCollectId(com.sinosoft.claim.schema.model.PrpLcertifyCollectId)
	 * 根据主键查询出对象
	 */
	public PrpLcertifyImg findByPrpLcertifyImgId(PrpLcertifyImgId prpLcertifyImgId)throws Exception{
		return super.get(PrpLcertifyImg.class, prpLcertifyImgId);
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule, int, int)
	 *查询【page对象，页面分页
	 */
	public Page findPrpLcertifyImg(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}
	/* （非 Javadoc）
	 * @see com.sinosoft.claim.certify.service.facade.PrpLcertifyCollectService#findPrpLcertifyCollect(ins.framework.common.QueryRule)
	 * 查询出所有的值
	 */
	public List<PrpLcertifyImg> findPrpLcertifyImg(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
