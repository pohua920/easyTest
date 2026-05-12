package com.sinosoft.claim.schema.service.spring;
/**
 * 赔案收费计划信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLplan;
import com.sinosoft.claim.schema.model.PrpLplanId;
import com.sinosoft.claim.schema.service.facade.PrpLplanService;

public class PrpLplanServiceSpringImpl extends
GenericDaoHibernate<PrpLplan, PrpLplanId> implements PrpLplanService{

	@Override
	public void save(PrpLplan prpLplan) throws Exception {
		logger.info("保存赔案收费计划信息");
		super.save(prpLplan);
		
	}

	@Override
	public void save(List<PrpLplan> list) throws Exception {
		logger.info("保存赔案收费计划信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLplanId prpLplanId) throws Exception {
		logger.info("删除赔案收费计划信息编号为" + prpLplanId + "的赔案收费计划信息");
		super.deleteByPK(PrpLplan.class, prpLplanId);
	}

	@Override
	public PrpLplan findPrpLplan(PrpLplanId prpLplanId) throws Exception {
		logger.info("查询赔案收费计划信息编号为" + prpLplanId + "的赔案收费计划信息");
		return super.get(PrpLplan.class, prpLplanId);
	}

	@Override
	public Page findPrpLplan(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取赔案收费计划信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLplan> findPrpLplan(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据赔案收费计划编号查询出赔案收费计划信息
	 * @param certiNo ：传入的赔案收费计划编号
	 * @return 返回赔案收费计划
	 */
	public PrpLplan findPrpLplan(String certiNo) throws Exception{
		PrpLplan prpLplan = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLplan> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLplan = resultList.get(0);
		}
		return prpLplan;
	}
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 更具sql条件查询信息
	 */
	public List<PrpLplan> findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	@Override	
	public int getCount(String conditions)throws Exception{
		if (!CommonUtils.isEmpty(conditions)) {
			String sql = "select count(*) from prplplan where 1=1 AND " + conditions;
			return (int) HibernateUtils.getCountbyCountSql(super.getSession(), sql);
		}
		return 0;
	}
}
