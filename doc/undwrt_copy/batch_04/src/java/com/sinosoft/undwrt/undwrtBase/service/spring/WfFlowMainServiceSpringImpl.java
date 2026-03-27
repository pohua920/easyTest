package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.WfFlowMain;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService;

/**
 * 工作流主表實現類.
 */
public class WfFlowMainServiceSpringImpl extends
		GenericDaoHibernate<WfFlowMain, String> implements WfFlowMainService {

	/**
	 * 插入一條記錄.
	 * 
	 * @param wfFlowMain
	 *            工作流主表類
	 * @throws Exception
	 *             the exception
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#insert(com.sinosoft.undwrt.undwrtBase.model.WfFlowMain)
	 */
	@Override
	public void insert(WfFlowMain wfFlowMain) throws Exception {
		// TODO Auto-generated method stub
		super.save(wfFlowMain);
	}

	/**
	 * 按主鍵刪除一條記錄.
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#delete(java.lang.String)
	 */
	@Override
	public void delete(String flowID) throws Exception {
		// TODO Auto-generated method stub
		super.deleteByPK(flowID);
	}

	/**
	 * 按條件刪除記錄.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#deleteByQueryRule(ins.framework.common.QueryRule)
	 */
	@Override
	public void deleteByQueryRule(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵更新一條數據(主鍵本身無法變更).
	 * 
	 * @param wfFlowMain
	 *            工作流主表類
	 * @throws Exception
	 *             異常
	 * @see ins.framework.dao.GenericDaoHibernate#update(java.io.Serializable)
	 */
	@Override
	public void update(WfFlowMain wfFlowMain) {

		WfFlowMain wfFlowMainNew = super.get(wfFlowMain.getFlowId());
		super.update(wfFlowMainNew);
	}

	/**
	 * 按主鍵查找一條數據.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return wfFlowMain 工作流主表類
	 * @throws Exception
	 *             異常
	 */
	@Override
	public WfFlowMain findByPrimaryKey(String flowID) throws Exception {
		// 声明
		WfFlowMain wfFlowMain = null;
		// 查询数据,赋值给DTO
		wfFlowMain = super.get(flowID);
		return wfFlowMain;
	}

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的行數
	 * @return PageRecord 查詢的一頁的結果
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#findByQueryRule(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public Page findByQueryRule(QueryRule queryRule, int pageNo, int rowsPerPage)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule, pageNo, rowsPerPage);
	}

	/**
	 * 按条件查询多条数据.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @return 滿足條件的工作流主表類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#findByQueryRule(ins.framework.common.QueryRule)
	 */
	@Override
	public List<WfFlowMain> findByQueryRule(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 查詢滿足模糊查詢條件的記錄數.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @return 滿足模糊查詢條件的記錄數
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService#getCount(ins.framework.common.QueryRule)
	 */
	@Override
	public int getCount(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule).size();
	}

}
