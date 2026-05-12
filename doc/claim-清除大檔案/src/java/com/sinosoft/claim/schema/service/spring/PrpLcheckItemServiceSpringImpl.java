package com.sinosoft.claim.schema.service.spring;

/**
 * 查勘任务处理信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLcheckItemId;
import com.sinosoft.claim.schema.service.facade.PrpLcheckItemService;

public class PrpLcheckItemServiceSpringImpl extends GenericDaoHibernate<PrpLcheckItem, PrpLcheckItemId> implements PrpLcheckItemService {

	@Override
	public void save(PrpLcheckItem prpLcheckItem) throws Exception {
		logger.info("保存查勘任务处理信息");
		super.save(prpLcheckItem);

	}

	@Override
	public void save(List<PrpLcheckItem> list) throws Exception {
		logger.info("保存查勘任务处理信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcheckItemId prpLcheckItemId) throws Exception {
		logger.info("删除查勘任务处理信息编号为" + prpLcheckItemId + "的查勘任务处理信息");
		super.deleteByPK(PrpLcheckItem.class, prpLcheckItemId);
	}

	@Override
	public PrpLcheckItem findPrpLcheckItem(PrpLcheckItemId prpLcheckItemId) throws Exception {
		logger.info("查询查勘任务处理信息编号为" + prpLcheckItemId + "的查勘任务处理信息");
		return super.get(PrpLcheckItem.class, prpLcheckItemId);
	}

	@Override
	public Page findPrpLcheckItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取查勘任务处理信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcheckItem> findPrpLcheckItem(QueryRule queryRule) {
		return super.find(queryRule);
	}

	/**
	 * 根据查勘任务处理编号查询出查勘任务处理信息
	 * @param certiNo ：传入的查勘任务处理编号
	 * @return 返回查勘任务处理
	 */
	public PrpLcheckItem findPrpLcheckItem(String certiNo) throws Exception {
		PrpLcheckItem prpLcheckItem = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcheckItem> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLcheckItem = resultList.get(0);
		}
		return prpLcheckItem;
	}

}
