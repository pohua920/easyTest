package com.tlg.aps.bs.reinsInwardService;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardClaimData;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface ReinsClaimInwardService {
	
	public Result createData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ReinsInwardClaimData reinsInwardClaimData, ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList) throws SystemException,Exception;
	
	public Result updateData(UserInfo userInfo, ReinsInwardMainData reinsInwardMainData, ReinsInwardClaimData reinsInwardClaimData, ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList) throws SystemException,Exception;
	
	public Result deleteData(UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException,Exception;
	
	public Result submitData(UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException, Exception;
	
	public Result auditData(String auditResult, UserInfo userInfo, ReinsInwardClaimData reinsInwardClaimData) throws SystemException, Exception;
	
	public void convertNumberComma(ReinsInwardClaimData reinsInwardClaimData) throws Exception;
	
	public void convertNumberComma(ReinsInwardClaimInsData reinsInwardClaimInsData) throws Exception;
	
	public Result queryPolicyDataForEndorse(BigDecimal oid) throws SystemException, Exception;
	
}
