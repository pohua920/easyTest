package com.tlg.db2.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.db2.dao.Com890waDao;
import com.tlg.db2.entity.Com890wa;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * @author bi086
 *
 */
public class Com890waDaoImpl extends IBatisBaseDaoImpl<Com890wa, BigDecimal> implements Com890waDao {
	
	@Override
	public String getNameSpace() {
		return "Com890wa";
	}
	
	public List<Com890wa> selectByUnsend() throws Exception {
		List<Com890wa> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByUnsend");
		return queryForList;
	}
	
	public boolean updateForBatch(Com890wa com890wa) throws Exception {
		boolean result = false;
		String nameSpace = getNameSpace()+".updateForBatch";
		if(getSqlMapClientTemplate().update(nameSpace, com890wa) == 1) {
			result = true;
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void batchUpdate(final List<Com890wa> list)throws Exception{

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