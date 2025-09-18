package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBatchMainDao;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.util.PageInfo;

public class FirAgtBatchMainDaoImpl extends IBatisBaseDaoImpl<FirAgtBatchMain, BigDecimal> implements FirAgtBatchMainDao {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
	@Override
	public String getNameSpace() {
		return "FirAgtBatchMain";
	}

	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
	@Override
	public String findBotMaxFilenameByParams(Map params) throws Exception {
		String searchResult =  (String) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForBotMaxFilename",params);
		return searchResult;
	}
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	@Override
	public List<FirAgtBatchMain> selectForAps041(PageInfo pageInfo) throws Exception {
		List<FirAgtBatchMain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps041",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps041(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps041", params);
		return count;
	}
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */
}