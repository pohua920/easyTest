package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.TiiTvmcq;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public interface TiiTvmcqService {

	public int countTiiTvmcq(Map params) throws SystemException, Exception;

	public Result findTiiTvmcqByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findTiiTvmcqByParams(Map params) throws SystemException, Exception;

	public Result findTiiTvmcqByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertTiiTvmcq(TiiTvmcq tiiTvmcq) throws SystemException, Exception;

	public Result updateTiiTvmcq(TiiTvmcq tiiTvmcq) throws SystemException, Exception;
	
	public int findMaxID() throws Exception;

	// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	public Result selectTiiTvmcq2() throws SystemException, Exception;
}
