package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.AmlQueryObjDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface AmlQueryObjDetailService {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	public int countAmlQueryObjDetail(Map params) throws SystemException, Exception;

	public Result findAmlQueryObjDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findAmlQueryObjDetailByParams(Map params) throws SystemException, Exception;

	public Result findAmlQueryObjDetailByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertAmlQueryObjDetail(AmlQueryObjDetail amlQueryObjDetail) throws SystemException, Exception;

}
