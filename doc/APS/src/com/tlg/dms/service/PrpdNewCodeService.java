package com.tlg.dms.service;

import java.util.Map;

import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdNewCodeService {

	public int countPrpdNewCode(Map params) throws SystemException, Exception;

	public Result findPrpdNewCodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdNewCodeByParams(Map params) throws SystemException, Exception;
	
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
	public Result updatePrpdNewCode(PrpdNewCode prpdNewCode) throws SystemException, Exception;

	//mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳
	// mantis：FIR0651，處理人員：DP0714，商住火65歲業務員高齡教育訓練檢核及APS例外設定
	public Result insertPrpdnewcode(PrpdNewCode prpdNewCode) throws SystemException, Exception;
}
