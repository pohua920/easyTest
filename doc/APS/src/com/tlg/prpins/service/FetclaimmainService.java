package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Fetclaimmain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface FetclaimmainService {

	public int countFetclaimmain(Map params) throws SystemException, Exception;

	public Result findFetclaimmainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetclaimmainByParams(Map params) throws SystemException, Exception;

	public Result insertFetclaimmain(Fetclaimmain fetclaimmain) throws SystemException, Exception;

	public Result updateFetclaimmain(Fetclaimmain fetclaimmain) throws SystemException, Exception;

	public Result removeFetclaimmain(BigDecimal oid) throws SystemException, Exception;
	
	public Result findForMainDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result findForClaimListByParams(Map params) throws SystemException, Exception;
	
	//mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業
	public Result findForReviewNumByParams(Map params) throws SystemException, Exception;
}
