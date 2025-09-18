package com.tlg.aps.bs.reinsInwardService;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface ReinsInwardService {
	
	public Result createData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ArrayList<ReinsInwardInsData> reinsInwardInsDataList) throws SystemException,Exception;
	
	public Result updateData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ArrayList<ReinsInwardInsData> reinsInwardInsDataList) throws SystemException,Exception;
	
	public Result deleteData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException,Exception;
	
	public Result submitData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception;
	
	public Result auditData(String auditResult, UserInfo userInfo, ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception;
	
	public void convertNumberComma(ReinsInwardMainData reinsInwardMainData) throws Exception;
	
	public void convertNumberComma(ReinsInwardInsData reinsInwardInsData) throws Exception;
	
	public Result queryPolicyDataForEndorse(BigDecimal oid) throws SystemException, Exception;
	
}
