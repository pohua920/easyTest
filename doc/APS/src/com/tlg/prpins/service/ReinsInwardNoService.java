package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardNo;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReinsInwardNoService {

	public int countReinsInwardNo(Map params) throws SystemException, Exception;

	public Result findReinsInwardNoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReinsInwardNoByParams(Map params) throws SystemException, Exception;

	public Result findReinsInwardNoByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateReinsInwardNo(ReinsInwardNo reinsInwardNo) throws SystemException, Exception;

	public Result insertReinsInwardNo(ReinsInwardNo reinsInwardNo) throws SystemException, Exception;

	public Result removeReinsInwardNo(BigDecimal oid) throws SystemException, Exception;
	

}
