package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.MetaAmlResultMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface MetaAmlResultMainService {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	public int countMetaAmlResultMain(Map params) throws SystemException, Exception;

	public Result findMetaAmlResultMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findMetaAmlResultMainByParams(Map params) throws SystemException, Exception;

	public Result findMetaAmlResultMainByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) throws SystemException, Exception;

	public Result insertMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) throws SystemException, Exception;

	public Result removeMetaAmlResultMain(BigDecimal oid) throws SystemException, Exception;
	

}
