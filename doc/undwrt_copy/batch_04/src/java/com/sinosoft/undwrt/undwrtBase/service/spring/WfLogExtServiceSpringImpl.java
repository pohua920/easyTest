package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.WfLogExt;
import com.sinosoft.undwrt.undwrtBase.model.WfLogExtId;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogExtService;

/**
 * 工作流日誌附屬實現類.
 */
public class WfLogExtServiceSpringImpl extends
		GenericDaoHibernate<WfLogExt, WfLogExtId> implements WfLogExtService {

	/**
	 * 按條件刪除數據.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogExtService#deleteByQueryRule(ins.framework.common.QueryRule)
	 */
	@Override
	public void deleteByQueryRule(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		super.deleteAll(super.find(queryRule));
	}

	/**
	 * 全部保存工作流日誌附屬信息
	 * 
	 * @param collection
	 *            工作流日誌附屬信息集合
	 */
	public void saveAll(Collection<WfLogExt> collection) {
		// TODO Auto-generated method stub
		super.saveAll((List<WfLogExt>) collection);
	}

	/**
	 * 批量插入多條數據.
	 * 
	 * @param collection
	 *            工作流日誌附屬接口類集合
	 */
	public void insertAll(Collection<WfLogExt> wfLogExtList) {
		WfLogExt wfLogExtDto = null;
		QueryRule queryRule = QueryRule.getInstance();

		if (wfLogExtList.iterator().hasNext()) {
			wfLogExtDto = (WfLogExt) wfLogExtList.iterator().next();
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", wfLogExtDto.getId().getFlowId());
			queryRule.addEqual("id.logNo", wfLogExtDto.getId().getLogNo());
			try {
				this.deleteByQueryRule(queryRule);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.insertAll(wfLogExtList);
	}

	/**
	 * 根據條件查詢工作流日誌附屬接口類集合.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的工作流日誌附屬接口類集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfLogExtService#getWfLogExtList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<WfLogExt> getWfLogExtList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}
}
