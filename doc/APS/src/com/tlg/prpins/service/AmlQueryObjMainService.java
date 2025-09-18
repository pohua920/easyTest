package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.AmlQueryObjMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface AmlQueryObjMainService {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	public int countAmlQueryObjMain(Map params) throws SystemException, Exception;

	public Result findAmlQueryObjMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findAmlQueryObjMainByParams(Map params) throws SystemException, Exception;

	public Result findAmlQueryObjMainByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertAmlQueryObjMain(AmlQueryObjMain amlQueryObjMain) throws SystemException, Exception;
	
	public Result findAmlQueryObjMainMaxOid(PageInfo pageInfo) throws SystemException, Exception;

}
