package com.tlg.db2.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.db2.dao.Com880wkDao;
import com.tlg.db2.entity.Com880wk;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * @author bi086
 *
 */
public class Com880wkDaoImpl extends IBatisBaseDaoImpl<Com880wk, BigDecimal> implements Com880wkDao {
	
	@Override
	public String getNameSpace() {
		return "Com880wk";
	}
	
	public List<Com880wk> selectByUnsend() throws Exception {
		List<Com880wk> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByUnsend");
		return queryForList;
	}
	
	public boolean updateForBatch(Com880wk com880wk) throws Exception {
		boolean result = false;
		String nameSpace = getNameSpace()+".updateForBatch";
		if(getSqlMapClientTemplate().update(nameSpace, com880wk) == 1) {
			result = true;
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void batchUpdate(final List<Com880wk> list)throws Exception{

		try {
			if (list != null) {
				this.getSqlMapClientTemplate().execute(
						new SqlMapClientCallback() {
							String nameSpace = getNameSpace()+".updateForBatch";
							public Object doInSqlMapClient(SqlMapExecutor executor)	throws SQLException {
								executor.startBatch();
								int batch = 0;
								for (int i = 0, n = list.size(); i < n; i++) {
									executor.update(nameSpace, list.get(i));
									batch++;
									//每500條批量提交一次。
									if(batch==500){
										executor.executeBatch();
										batch = 0;
									}
								}
								executor.executeBatch(); //將最後的數據執行，最後不夠500條的數據
								return null;
							}
						});
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	

//	public void batchInsert(final String statementName, final List list)throws Exception{
//		try {
//			if (list != null) {
//				this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
//							public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
//								executor.startBatch();
//								for (int i = 0, n = list.size(); i < n; i++) {
//									executor.insert(statementName, list.get(i));
//								}
//								executor.executeBatch();
//								return null;
//							}
//						});
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
}