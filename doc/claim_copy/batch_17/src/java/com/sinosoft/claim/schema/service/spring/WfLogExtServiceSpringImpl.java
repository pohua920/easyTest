package com.sinosoft.claim.schema.service.spring;
/**
 * WfLog表的附属表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.schema.model.WfLogExt;
import com.sinosoft.claim.schema.model.WfLogExtId;
import com.sinosoft.claim.schema.service.facade.WfLogExtService;


public class WfLogExtServiceSpringImpl extends GenericDaoHibernate<WfLogExt,WfLogExtId> implements WfLogExtService {

    @Override
    public void deleteByQueryRule(QueryRule queryRule) throws Exception {
        super.deleteAll(super.find(queryRule));
    }
    
    public void saveAll(Collection<WfLogExt> collection) {
        super.saveAll((List<WfLogExt>)collection);
    }

    /**
     * 先删除後插入
     * @param dbManager
     * @param wfLogExtList
     * @throws Exception
     */
	public void insertAll(List<WfLogExt> wfLogExtList)throws Exception{
		WfLogExt wfLogExtDto = null;
		QueryRule queryRule = null;
		Iterator<?> it = wfLogExtList.iterator();
		if (it.hasNext()) {
            wfLogExtDto = (WfLogExt)it.next();
            queryRule = QueryRule.getInstance();
            queryRule.getQueryRuleList().clear();
            queryRule.addEqual("id.flowId", wfLogExtDto.getId().getFlowId());
            queryRule.addEqual("id.logNo", wfLogExtDto.getId().getLogNo());
            this.deleteByQueryRule(queryRule);
        }
		super.saveAll(wfLogExtList);
	}
    
	@Override
	public List<WfLogExt> getWfLogExtList(QueryRule queryRule) {
		return super.find(queryRule);
	}

	@Override
	public void delete(WfLogExtId wfLogExtId) throws Exception {
		super.deleteByPK(wfLogExtId);
		logger.info("删除WfLog表的附属表编号为" + wfLogExtId + "的WfLog表的附属表信息");
	}

	@Override
	public WfLogExt findWfLogExt(WfLogExtId wfLogExtId) throws Exception {
		logger.info("查询WfLog表的附属表编号为" + wfLogExtId + "的WfLog表的附属表信息");
		return super.get(WfLogExt.class,wfLogExtId);
	}

	@Override
	public void save(WfLogExt wfLogExt) throws Exception {
		logger.info("保存WfLog表的附属表信息");
		super.save(wfLogExt);
	}

	@Override
	public void save(List<WfLogExt> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}



	
}
