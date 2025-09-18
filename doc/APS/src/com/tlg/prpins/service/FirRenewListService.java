package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface FirRenewListService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirRenewListByParams(Map params) throws SystemException, Exception;

	public Result findFirRenewListByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findFirRenewListByUk(Map params) throws SystemException, Exception;

	public Result insertFirRenewList(FirRenewList firRenewList) throws SystemException, Exception;

	public Result updateFirRenewList(FirRenewList firRenewList) throws SystemException, Exception;

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@SuppressWarnings("rawtypes")
	public Result selectMainData(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result selectInsuredData(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result selectAddressData(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result selectPropData(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result selectMortgageeData(Map params) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result selectItemkindData(Map params) throws SystemException, Exception;
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
}
