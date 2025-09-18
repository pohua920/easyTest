package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.TiiTvbcm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
public interface TiiTvbcmService {

	public int countTiiTvbcm(Map params) throws SystemException, Exception;

	public Result findTiiTvbcmByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findTiiTvbcmByParams(Map params) throws SystemException, Exception;

	public Result findTiiTvbcmByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertTiiTvbcm(TiiTvbcm tiiTvbcm) throws SystemException, Exception;

	public Result updateTiiTvbcm(TiiTvbcm tiiTvbcm) throws SystemException, Exception;
	
	public int findMaxID() throws Exception;

	// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	public Result selectTiiTvbcm2() throws SystemException, Exception;
}
