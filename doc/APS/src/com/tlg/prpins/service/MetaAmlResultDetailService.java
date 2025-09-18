package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.MetaAmlResultDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface MetaAmlResultDetailService {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	public int countMetaAmlResultDetail(Map params) throws SystemException, Exception;

	public Result findMetaAmlResultDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findMetaAmlResultDetailByParams(Map params) throws SystemException, Exception;

	public Result findMetaAmlResultDetailByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateMetaAmlResultDetail(MetaAmlResultDetail metaAmlResultDetail) throws SystemException, Exception;

	public Result insertMetaAmlResultDetail(MetaAmlResultDetail metaAmlResultDetail) throws SystemException, Exception;

	public Result removeMetaAmlResultDetail(BigDecimal oid) throws SystemException, Exception;
	

}
