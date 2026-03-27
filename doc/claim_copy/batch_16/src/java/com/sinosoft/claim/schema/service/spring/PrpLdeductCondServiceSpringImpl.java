package com.sinosoft.claim.schema.service.spring;
/**
 * 计算书免赔条件信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLdeductCondId;
import com.sinosoft.claim.schema.service.facade.PrpLdeductCondService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLdeductCondServiceSpringImpl extends
GenericDaoHibernate<PrpLdeductCond, PrpLdeductCondId> implements PrpLdeductCondService{

	@Override
	public void save(PrpLdeductCond prpLdeductCond) throws Exception {
		logger.info("保存计算书免赔条件信息");
		super.save(prpLdeductCond);
		
	}

	@Override
	public void save(List<PrpLdeductCond> list) throws Exception {
		logger.info("保存计算书免赔条件信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLdeductCondId prpLdeductCondId) throws Exception {
		logger.info("删除计算书免赔条件信息编号为" + prpLdeductCondId + "的计算书免赔条件信息");
		super.deleteByPK(PrpLdeductCond.class, prpLdeductCondId);
	}

	@Override
	public PrpLdeductCond findPrpLdeductCond(PrpLdeductCondId prpLdeductCondId) throws Exception {
		logger.info("查询计算书免赔条件信息编号为" + prpLdeductCondId + "的计算书免赔条件信息");
		return super.get(PrpLdeductCond.class, prpLdeductCondId);
	}

	@Override
	public Page findPrpLdeductCond(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取计算书免赔条件信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLdeductCond> findPrpLdeductCond(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据计算书免赔条件编号查询出计算书免赔条件信息
	 * @param certiNo ：传入的计算书免赔条件编号
	 * @return 返回计算书免赔条件
	 */
	public PrpLdeductCond findPrpLdeductCond(String certiNo) throws Exception{
		PrpLdeductCond prpLdeductCond = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLdeductCond> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLdeductCond = resultList.get(0);
		}
		return prpLdeductCond;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpldeductcond Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
