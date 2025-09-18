package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.util.PageInfo;


public interface TmpfetclaimmainDao extends IBatisBaseDao<Tmpfetclaimmain, BigDecimal> {
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  start*/
	public boolean removeAll();
	public void processInBatch(final List<Tmpfetclaimmain> listTmpfetclaimmain);
	/**
	 * 計算資料筆數
	 * @param params
	 * @return
	 */
	public int countMultiClaim(String policyNo);

	// mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認
	public int countMultiClaim2(Map<String, String> params) throws Exception;
	
	public List<Map<String, String>> getPayoutData(String policyNo);
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫  end*/
	
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 start*/
	public Aps046ResultVo selectSumWda35();
	
	public List<Aps046ResultVo> selectForMainData(PageInfo pageInfo) throws Exception;
	
	public int countForMainData(Map<String, String> params) throws Exception;
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 end*/
}