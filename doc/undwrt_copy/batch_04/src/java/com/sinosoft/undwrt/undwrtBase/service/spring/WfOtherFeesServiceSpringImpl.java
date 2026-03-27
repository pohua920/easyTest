package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.WfOtherFees;
import com.sinosoft.undwrt.undwrtBase.model.WfOtherFeesId;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService;

/**
 * The Class WfOtherFeesServiceSpringImpl.
 */
public class WfOtherFeesServiceSpringImpl extends
		GenericDaoHibernate<WfOtherFees, WfOtherFeesId> implements WfOtherFeesService {

	/**
	 * Insert.
	 * 
	 * @param wfotherfees
	 *            the wfotherfees
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#insert(com.sinosoft.undwrt.undwrtBase.model.WfOtherFees)
	 */
	@Override
	public void insert(WfOtherFees wfotherfees) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Delete.
	 * 
	 * @param businessno
	 *            the businessno
	 * @param serialno
	 *            the serialno
	 * @param lineno
	 *            the lineno
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#delete(java.lang.String,
	 *      int, int)
	 */
	@Override
	public void delete(String businessno, int serialno, int lineno)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Delete by conditions.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#deleteByConditions(java.lang.String)
	 */
	@Override
	public void deleteByConditions(String conditions) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Update.
	 * 
	 * @param wfotherfees
	 *            the wfotherfees
	 * @see ins.framework.dao.GenericDaoHibernate#update(java.io.Serializable)
	 */
	@Override
	public void update(WfOtherFees wfotherfees) {
		// TODO Auto-generated method stub

	}

	/**
	 * Find by primary key.
	 * 
	 * @param businessno
	 *            the businessno
	 * @param serialno
	 *            the serialno
	 * @param lineno
	 *            the lineno
	 * @return the wf other fees
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#findByPrimaryKey(java.lang.String,
	 *      int, int)
	 */
	@Override
	public WfOtherFees findByPrimaryKey(String businessno, int serialno,
			int lineno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find by conditions.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @param pageNo
	 *            the page no
	 * @param rowsPerPage
	 *            the rows per page
	 * @return the page record
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#findByConditions(java.lang.String,
	 *      int, int)
	 */
	@Override
	public PageRecord findByConditions(String conditions, int pageNo,
			int rowsPerPage) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find by conditions.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return the collection
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#findByConditions(java.lang.String)
	 */
	@Override
	public Collection findByConditions(String conditions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 獲取屬性記錄條數.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 屬性記錄條數的值
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfOtherFeesService#getCount(java.lang.String)
	 */
	@Override
	public int getCount(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule).size();
	}

}
