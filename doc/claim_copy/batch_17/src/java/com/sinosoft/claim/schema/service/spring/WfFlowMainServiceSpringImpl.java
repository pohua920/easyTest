package com.sinosoft.claim.schema.service.spring;
/**
 * 工作流主表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.WfFlowMain;
import com.sinosoft.claim.schema.service.facade.WfFlowMainService;


public class WfFlowMainServiceSpringImpl extends
		GenericDaoHibernate<WfFlowMain, String> implements WfFlowMainService {

	@Override
	public void insert(WfFlowMain wfFlowMain) throws Exception {
        super.save(wfFlowMain);
	}

	@Override
	public void delete(String flowID) throws Exception {
        super.deleteByPK(flowID);
	}

	@Override
	public void deleteByQueryRule(QueryRule queryRule) throws Exception {
        
	}

	@Override
	public void update(WfFlowMain wfFlowMain) {
		
		WfFlowMain wfFlowMainNew = super.get(wfFlowMain.getFlowId());
		super.update(wfFlowMainNew);
	}

    /**
     * 按主键查找一条数据
     * @param flowID 工作流号
     * @return WfFlowMainDto
     * @throws Exception
     */
	@Override
	public WfFlowMain findByPrimaryKey(String flowID) throws Exception {
        //声明
        WfFlowMain wfFlowMain = null;
        //查询数据,赋值给DTO
        wfFlowMain = super.get(flowID);
        return wfFlowMain;
	}

	@Override
	public Page findByQueryRule(QueryRule queryRule, int pageNo, int rowsPerPage)
			throws Exception {
		return super.find(queryRule, pageNo, rowsPerPage);
	}

	@Override
	public List<WfFlowMain> findByQueryRule(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

	@Override
	public int getCount(QueryRule queryRule) throws Exception {
		return super.find(queryRule).size();
	}

}
