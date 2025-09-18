package com.tlg.db2.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.db2.dao.Com910waDao;
import com.tlg.db2.entity.Com910wa;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * @author bi086
 *
 */
public class Com910waDaoImpl extends IBatisBaseDaoImpl<Com910wa, String> implements Com910waDao {
	
	
	@Override
	public String getNameSpace() {
		return "Com910wa";
	}
	
	public List<Com910wa> selectByUnsend() throws Exception {
		List<Com910wa> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByUnsend");
		return queryForList;
	}
	
	public boolean updateForBatch(Com910wa com910wa) throws Exception {
		boolean result = false;
		String nameSpace = getNameSpace()+".updateForBatch";
		if(getSqlMapClientTemplate().update(nameSpace, com910wa) == 1) {
			result = true;
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void batchUpdate(final List<Com910wa> list)throws Exception{

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

}