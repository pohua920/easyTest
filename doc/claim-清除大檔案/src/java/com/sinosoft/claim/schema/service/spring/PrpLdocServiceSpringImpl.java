package com.sinosoft.claim.schema.service.spring;
/**
 * 索赔单证信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdocId;
import com.sinosoft.claim.schema.service.facade.PrpLdocService;

public class PrpLdocServiceSpringImpl extends
GenericDaoHibernate<PrpLdoc, PrpLdocId> implements PrpLdocService{

	@Override
	public void save(PrpLdoc prpLdoc) throws Exception {
		logger.info("保存索赔单证信息");
		super.save(prpLdoc);
		
	}

	@Override
	public void save(List<PrpLdoc> list) throws Exception {
		logger.info("保存索赔单证信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLdocId prpLdocId) throws Exception {
		logger.info("删除索赔单证信息编号为" + prpLdocId + "的索赔单证信息");
		super.deleteByPK(PrpLdoc.class, prpLdocId);
	}

	@Override
	public PrpLdoc findPrpLdoc(PrpLdocId prpLdocId) throws Exception {
		logger.info("查询索赔单证信息编号为" + prpLdocId + "的索赔单证信息");
		return super.get(PrpLdoc.class, prpLdocId);  
	}
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除
	 */
	public void deleteByClaimNo(String claimNo)throws Exception{
		String sql = "delete from PrpLdoc where claimNo=?";
		super.getSession().createSQLQuery(sql).setString(0, claimNo).executeUpdate();
	}
	/**
	 * @param prpLdoc
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(List<PrpLdoc> list)throws Exception{
		for(int i=0;i<list.size();i++){
			super.getSession().saveOrUpdate(list.get(i));
		}
	}
	/**
	 * @param prpLdoc
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(PrpLdoc prpLdoc)throws Exception{
		super.getSession().saveOrUpdate(prpLdoc);
	}
	@Override
	public Page findPrpLdoc(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取索赔单证信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLdoc> findPrpLdoc(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
