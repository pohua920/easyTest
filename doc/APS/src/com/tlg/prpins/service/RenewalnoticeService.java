package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
public interface RenewalnoticeService {

	public int countRenewalnotice(Map params) throws SystemException, Exception;

	public Result findRenewalnoticeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findRenewalnoticeByParams(Map params) throws SystemException, Exception;

	public Result findRenewalnoticeByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertRenewalnotice(Renewalnotice renewalnotice) throws SystemException, Exception;

}
