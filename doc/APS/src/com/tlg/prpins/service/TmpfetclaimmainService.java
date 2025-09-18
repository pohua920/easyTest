package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface TmpfetclaimmainService {

	public int countTmpfetclaimmain(Map params) throws SystemException, Exception;

	public Result findTmpfetclaimmainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findTmpfetclaimmainByParams(Map params) throws SystemException, Exception;

	public Result insertTmpfetclaimmain(Tmpfetclaimmain tmpfetclaimmain) throws SystemException, Exception;
	
	public Result updateTmpfetclaimmain(Tmpfetclaimmain tmpfetclaimmain) throws SystemException, Exception;

	public Result removeTmpfetclaimmain(BigDecimal oid) throws SystemException, Exception;
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 start */
	public Result removeTmpfetclaimmainAll() throws SystemException, Exception;
	public void batchInsertTmpfetclaimmain(List<Tmpfetclaimmain> listTmpfetclaimmain) throws SystemException,Exception;
	public int countMultiClaim(String policyNo) throws SystemException, Exception;

	// mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認
	public int countMultiClaim2(Map params) throws SystemException, Exception;

	public Result findPayoutDataByParams(String policyNo) throws SystemException, Exception;
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */

	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業  start*/
	public Result sumWda35() throws SystemException, Exception;
	
	public Result findForMainDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業  end*/
}
