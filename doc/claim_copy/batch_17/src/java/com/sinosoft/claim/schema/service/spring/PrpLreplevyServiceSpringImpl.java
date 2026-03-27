package com.sinosoft.claim.schema.service.spring;
/**
 * 权益转让及追偿登记接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLreplevy;
import com.sinosoft.claim.schema.service.facade.PrpLreplevyService;

public class PrpLreplevyServiceSpringImpl extends
GenericDaoHibernate<PrpLreplevy, String> implements PrpLreplevyService{

	@Override
	public void save(PrpLreplevy prpLreplevy) throws Exception {
		logger.info("保存权益转让及追偿登记信息");
		super.save(prpLreplevy);
		
	}

	@Override
	public void save(List<PrpLreplevy> list) throws Exception {
		logger.info("保存权益转让及追偿登记");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String policyNo) throws Exception {
		logger.info("删除权益转让及追偿登记编号为" + policyNo + "的权益转让及追偿登记");
		super.deleteByPK(PrpLreplevy.class, policyNo);
	}

	@Override
	public PrpLreplevy findPrpLreplevy(String policyNo) throws Exception {
		logger.info("查询权益转让及追偿登记编号为" + policyNo + "的权益转让及追偿登记");
		return super.get(PrpLreplevy.class,policyNo);
	}
    
	@Override
	public Page findPrpLreplevy(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取权益转让及追偿登记列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLreplevy> findPrpLreplevy(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
