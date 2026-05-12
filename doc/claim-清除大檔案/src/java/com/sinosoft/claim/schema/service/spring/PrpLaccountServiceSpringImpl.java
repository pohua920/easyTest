package com.sinosoft.claim.schema.service.spring;
/**
 * 银行帐号与赔案信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLaccount;
import com.sinosoft.claim.schema.model.PrpLaccountId;
import com.sinosoft.claim.schema.service.facade.PrpLaccountService;
import com.sinosoft.sysframework.common.util.DataUtils;

public class PrpLaccountServiceSpringImpl extends
GenericDaoHibernate<PrpLaccount, PrpLaccountId> implements PrpLaccountService{

	@Override
	public void save(PrpLaccount prpLaccount) throws Exception {
		logger.info("保存银行帐号与赔案信息");
		super.save(prpLaccount);
		
	}

	@Override
	public void save(List<PrpLaccount> list) throws Exception {
		logger.info("保存银行帐号与赔案信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLaccountId prpLaccountId) throws Exception {
		logger.info("删除银行帐号与赔案信息编号为" + prpLaccountId + "的银行帐号与赔案信息");
		super.deleteByPK(PrpLaccount.class, prpLaccountId);
	}

	@Override
	public PrpLaccount findPrpLaccount(PrpLaccountId prpLaccountId) throws Exception {
		logger.info("查询银行帐号与赔案信息编号为" + prpLaccountId + "的银行帐号与赔案信息");
		return super.get(PrpLaccount.class, prpLaccountId);
	}

	@Override
	public Page findPrpLaccount(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取银行帐号与赔案信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLaccount> findPrpLaccount(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据银行帐号与赔案编号查询出银行帐号与赔案信息
	 * @param certiNo ：传入的银行帐号与赔案编号
	 * @return 返回银行帐号与赔案
	 */
	public PrpLaccount findPrpLaccount(String registNo) throws Exception{
		PrpLaccount prpLaccount = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		List<PrpLaccount> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLaccount = resultList.get(0);
		}
		return prpLaccount;
	}
	@Override
	public List<PrpLaccount> findByConditions(String conditions)throws Exception{
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	@Override
	 public int getCount(String conditions) 
     throws Exception{
     int count = -1;
     String sql = "SELECT count(*) FROM PrpLaccount WHERE " + conditions;
     List<?> result = HibernateUtils.findbySql(super.getSession(), sql);
     count = Integer.parseInt(result.get(0).toString());
     return count;
 }

}
