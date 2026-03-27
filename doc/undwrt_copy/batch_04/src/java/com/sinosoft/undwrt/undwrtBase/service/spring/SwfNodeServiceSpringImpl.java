package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.SwfNodeId;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;

/**
 * 工作流節點定義實現類.
 */
public class SwfNodeServiceSpringImpl extends
		GenericDaoHibernate<SwfNode, SwfNodeId> implements SwfNodeService {

	/**
	 * 根據hql查詢結果集.
	 * 
	 * @param hql
	 *            查詢條件
	 * @param str1
	 *            查詢條件
	 * @return 滿足條件的結果集
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService#findByHqlList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<SwfNode> findByHqlList(String hql, String str1) {

		return super.findBySql(hql, str1);
	}

	/**
	 * 根據條件查詢結果集.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的結果集
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService#findByQureyRuleList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<SwfNode> findByQureyRuleList(QueryRule queryRule) {
		return super.find(queryRule);
	}

	/**
	 * 根據主鍵查詢節點.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	@Override
	public SwfNode findByPrimaryKey(QueryRule queryRule) {
		return super.findUnique(queryRule);
	}

	/**
	 * 檢查當前模版節點是否可以被置爲結束節點.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return true 是,false 否
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService#checkEndflag(ins.framework.common.QueryRule)
	 */
	@Override
	public boolean checkEndflag(QueryRule queryRule) throws SQLException,
			Exception {

		int intCount = 0;
		boolean blnReturn = true;
		try {
			intCount = this.getCount(queryRule);
			if (intCount > 0) {
				blnReturn = false;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return blnReturn;
	}

	/**
	 * 得到滿足條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄數
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService#getCount(ins.framework.common.QueryRule)
	 */
	@Override
	public int getCount(QueryRule queryRule) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule).size();
	}

}
