package com.sinosoft.claim.schema.service.spring;
/**
 * 代赔保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLCMain;
import com.sinosoft.claim.schema.model.PrpLCMainId;
import com.sinosoft.claim.schema.service.facade.PrpLCMainService;

public class PrpLCMainServiceSpringImpl extends
GenericDaoHibernate<PrpLCMain, PrpLCMainId> implements PrpLCMainService{

	@Override
	public void save(PrpLCMain prpLCMain) throws Exception {
		logger.info("保存代赔保单信息");
		super.save(prpLCMain);
		
	}

	@Override
	public void save(List<PrpLCMain> list) throws Exception {
		logger.info("保存代赔保单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLCMainId prpLCMainId) throws Exception {
		logger.info("删除代赔保单信息编号为" + prpLCMainId + "的代赔保单信息");
		super.deleteByPK(PrpLCMain.class, prpLCMainId);
	}

	@Override
	public PrpLCMain findPrpLCMain(PrpLCMainId prpLCMainId) throws Exception {
		logger.info("查询代赔保单信息编号为" + prpLCMainId + "的代赔保单信息");
		return super.get(PrpLCMain.class, prpLCMainId);
	}

	@Override
	public Page findPrpLCMain(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代赔保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLCMain> findPrpLCMain(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据代赔保单编号查询出代赔保单信息
	 * @param certiNo ：传入的代赔保单编号
	 * @return 返回代赔保单
	 */
	public PrpLCMain findPrpLCMain(String certiNo) throws Exception{
		PrpLCMain prpLCMain = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLCMain> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLCMain = resultList.get(0);
		}
		return prpLCMain;
	}

}
