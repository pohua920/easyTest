package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ProductEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ProductEndorseService {

	public int countProductEndorse(Map params) throws SystemException, Exception;
	
	public Result findProductEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findProductEndorseByParams(Map params) throws SystemException, Exception;

	public Result findProductEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateProductEndorse(ProductEndorse productEndorse) throws SystemException, Exception;

	public Result insertProductEndorse(ProductEndorse productEndorse) throws SystemException, Exception;

	public Result removeProductEndorse(String transactionId) throws SystemException, Exception;
	
}
