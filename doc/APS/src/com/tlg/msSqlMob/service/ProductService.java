package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Product;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ProductService {

	public int countProduct(Map params) throws SystemException, Exception;
	
	public Result findProductByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findProductByParams(Map params) throws SystemException, Exception;

	public Result findProductByUK(String transactionId) throws SystemException, Exception;

	public Result updateProduct(Product product) throws SystemException, Exception;

	public Result insertProduct(Product product) throws SystemException, Exception;

	public Result removeProduct(String transactionId) throws SystemException, Exception;
	
}
