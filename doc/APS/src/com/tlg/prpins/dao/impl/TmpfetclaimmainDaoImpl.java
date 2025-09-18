package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// 加 log 查問題，處理人員：DP0714
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.TmpfetclaimmainDao;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.util.PageInfo;

public class TmpfetclaimmainDaoImpl extends IBatisBaseDaoImpl<Tmpfetclaimmain, BigDecimal> implements TmpfetclaimmainDao {

	// 加 log 查問題，處理人員：DP0714
	private static final Logger logger = Logger.getLogger(TmpfetclaimmainDaoImpl.class);

	@Override
	public String getNameSpace() {
		return "Tmpfetclaimmain";
	}

	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  start*/
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
	public void processInBatch(final List<Tmpfetclaimmain> listTmpfetclaimmain) {
		try {
			Integer count = (Integer) getSqlMapClientTemplate().execute( new SqlMapClientCallback() {
	            public Object doInSqlMapClient( SqlMapExecutor executor ) throws SQLException {
	            	int batch = 0;
	                executor.startBatch();
	                Iterator<Tmpfetclaimmain> iter = listTmpfetclaimmain.iterator();
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
			// 加 log 查問題，處理人員：DP0714
			logger.error(e.getMessage(), e);
			e.printStackTrace();
	    }
	}
	
	@Override
	public int countMultiClaim(String policyNo) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countMultiClaim", policyNo);
		return count;
	}

	/**
	 * mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認
	 */
	@Override
	public int countMultiClaim2(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countMultiClaim2", params);
		return count;
	}

	@Override
	public List<Map<String, String>> getPayoutData(String policyNo) {
		List<Map<String, String>> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".findPayoutData",policyNo);
		
		return queryForList;
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  end*/

	
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 start*/
	@Override
	public Aps046ResultVo selectSumWda35() {
		Aps046ResultVo aps046ResultVo = (Aps046ResultVo) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectSumWda35");
		return aps046ResultVo;
	}
	
	@Override
	public List<Aps046ResultVo> selectForMainData(PageInfo pageInfo) throws Exception {
		List<Aps046ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForMainData",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForMainData(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForMainData", params);
		return count;
	}
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 end*/
}