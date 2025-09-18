package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.CwpLiaLia07061aqResultDao;
import com.tlg.xchg.entity.CwpLiaLia07061aqResult;

public class CwpLiaLia07061aqResultDaoImpl extends IBatisBaseDaoImpl<CwpLiaLia07061aqResult, BigDecimal> implements CwpLiaLia07061aqResultDao {
	
	@Override
	public String getNameSpace() {
		return "CwpLiaLia07061aqResult";
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public void processInBatch(final List<CwpLiaLia07061aqResult> list) {
		try {
			Integer count = (Integer) getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
	            public Object doInSqlMapClient( SqlMapExecutor executor ) throws SQLException {
	            	int batch = 0;
	                executor.startBatch();
	                Iterator<CwpLiaLia07061aqResult> iter = list.iterator();
	                while(iter.hasNext()) {
	                	batch++;
	                	executor.insert(getNameSpace()+".insert",iter.next());
	                	if (batch%300 == 0) {
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
	
}