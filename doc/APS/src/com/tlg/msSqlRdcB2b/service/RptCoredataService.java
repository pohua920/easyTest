package com.tlg.msSqlRdcB2b.service;

import java.util.List;

import com.tlg.exception.SystemException;
import com.tlg.msSqlRdcB2b.entity.RptCoredata;
import com.tlg.util.Result;

//mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
public interface RptCoredataService{
	
	public Result insertRptCoredata(List<RptCoredata> rptCoredatas) throws SystemException, Exception;
	
}
