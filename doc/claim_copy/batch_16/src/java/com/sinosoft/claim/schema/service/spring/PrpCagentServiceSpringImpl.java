package com.sinosoft.claim.schema.service.spring;

/**
 * 代理信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCagent;
import com.sinosoft.claim.schema.model.PrpCagentId;
import com.sinosoft.claim.schema.service.facade.PrpCagentService;

public class PrpCagentServiceSpringImpl extends
		GenericDaoHibernate<PrpCagent, PrpCagentId> implements PrpCagentService {

	@Override
	public void save(PrpCagent prpCagent) throws Exception {
		logger.info("保存代理信息信息");
		super.save(prpCagent);

	}

	@Override
	public void save(List<PrpCagent> list) throws Exception {
		logger.info("保存代理信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCagentId prpCagentId) throws Exception {
		logger.info("删除代理信息编号为" + prpCagentId + "的代理信息");
		super.deleteByPK(PrpCagent.class, prpCagentId);
	}

	@Override
	public PrpCagent findPrpCagent(PrpCagentId prpCagentId) throws Exception {
		logger.info("查询代理信息编号为" + prpCagentId + "的代理信息");
		return super.get(PrpCagent.class, prpCagentId);
	}

	@Override
	public Page findPrpCagent(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代理信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCagent> findPrpCagent(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据代理编号查询出代理信息
	 * 
	 * @param certiNo
	 *            ：传入的代理编号
	 * @return 返回代理
	 */
	public PrpCagent findPrpCagent(String certiNo) throws Exception {
		PrpCagent prpCagent = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCagent> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCagent = resultList.get(0);
		}
		return prpCagent;
	}

}
