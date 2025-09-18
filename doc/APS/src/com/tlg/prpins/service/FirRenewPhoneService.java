package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRenewPhone;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
public interface FirRenewPhoneService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirRenewPhoneByParams(Map params) throws SystemException, Exception;

	public Result findFirRenewPhoneByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findFirRenewPhoneByUk(Map params) throws SystemException, Exception;

	public Result insertFirRenewPhone(FirRenewPhone firRenewPhone) throws SystemException, Exception;

	public Result updateFirRenewPhone(FirRenewPhone firRenewPhone) throws SystemException, Exception;
	
	public Result FindPhoneByHandler1code(Map params) throws SystemException, Exception;
}
