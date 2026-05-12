package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCname;
import com.sinosoft.claim.schema.model.PrpCnameId;
import com.sinosoft.claim.schema.service.facade.PrpCnameService;

public class PrpCnameServiceSpringImpl extends
GenericDaoHibernate<PrpCname, PrpCnameId> implements PrpCnameService{

	@Override
	public void save(PrpCname PrpCname) throws Exception {
		logger.info("保存雇员清单信息信息");
		super.save(PrpCname);
		
	}

	@Override
	public void save(List<PrpCname> list) throws Exception {
		logger.info("保存雇员清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCnameId PrpCnameId) throws Exception {
		logger.info("删除雇员清单信息编号为" + PrpCnameId + "的雇员清单信息");
		super.deleteByPK(PrpCname.class, PrpCnameId);
	}

	@Override
	public PrpCname findPrpCname(PrpCnameId PrpCnameId) throws Exception {
		logger.info("查询雇员清单信息编号为" + PrpCnameId + "的雇员清单信息");
		return super.get(PrpCname.class, PrpCnameId);
	}

	@Override
	public Page findPrpCname(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取雇员清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCname> findPrpCname(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据雇员清单编号查询出雇员清单信息
	 * @param certiNo ：传入的雇员清单编号
	 * @return 返回雇员清单
	 */
	public PrpCname findPrpCname(String certiNo) throws Exception{
		PrpCname PrpCname = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCname> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCname = resultList.get(0);
		}
		return PrpCname;
	}

}
