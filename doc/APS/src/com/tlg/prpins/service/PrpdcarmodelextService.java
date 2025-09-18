package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpdcarmodelext;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/**mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業 */
public interface PrpdcarmodelextService {
	
	public Result findprpdcarmodelextByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdcarmodelextByParams(Map params) throws SystemException, Exception;

	public Result findPrpdcarmodelextByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertPrpdcarmodelext(Prpdcarmodelext prpdcarmodelext) throws SystemException, Exception;

	public Result updateFirCtbcDeptinfo(Prpdcarmodelext prpdcarmodelext) throws SystemException, Exception;
}
