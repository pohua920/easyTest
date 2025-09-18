package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.TmpfetclaimkindDao;
import com.tlg.prpins.entity.Tmpfetclaimkind;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public class TmpfetclaimkindDaoImpl extends IBatisBaseDaoImpl<Tmpfetclaimkind, BigDecimal> implements TmpfetclaimkindDao {
	
	@Override
	public String getNameSpace() {
		return "Tmpfetclaimkind";
	}
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 start */
	@Override
	public boolean removeAll() {
		int i = getSqlMapClientTemplate().delete(getNameSpace() + ".deleteAll");
		if(i > 0){
			return true;
		}
		return false;
	}
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public void processInBatch(final List<Tmpfetclaimkind> listTmpfetclaimkind) {
		try {
			Integer count = (Integer) getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
	            public Object doInSqlMapClient( SqlMapExecutor executor ) throws SQLException {
	            	int batch = 0;
	                executor.startBatch();
	                Iterator<Tmpfetclaimkind> iter = listTmpfetclaimkind.iterator();
	                while(iter.hasNext()) {
	                	batch++;
	                	executor.insert(getNameSpace()+".insert",iter.next());
	                	if (batch%100 == 0) {
                            executor.executeBatch();
                            executor.startBatch();
                        }
	                }
	                executor.executeBatch();
	                return null;
	            }
	        });
		}catch (Exception e ) {
			e.printStackTrace();
	    }
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  end*/

}