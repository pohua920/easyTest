package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.util.PageInfo;

/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
public interface FirAgtBatchMainDao extends IBatisBaseDao<FirAgtBatchMain, BigDecimal> {
	
	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
	public String findBotMaxFilenameByParams(Map params)throws Exception;
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 start */
	public List<FirAgtBatchMain> selectForAps041(PageInfo pageInfo)throws Exception;
	
	public int countForAps041(Map<String,String> params)throws Exception;
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 end */
}