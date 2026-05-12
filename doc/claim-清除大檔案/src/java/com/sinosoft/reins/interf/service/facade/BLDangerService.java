package com.sinosoft.reins.interf.service.facade;

import com.sinosoft.reins.interf.model.CDanger;
import com.sinosoft.reins.interf.vo.CDangerVO;
import com.sinosoft.reins.interf.vo.PDangerVO;
import com.sinosoft.reins.interf.vo.TDangerVO;
import com.sinosoft.sysframework.common.datatype.DateTime;
import java.util.Collection;

public abstract interface BLDangerService {
	public abstract Collection getCDangerList(String paramString, DateTime paramDateTime) throws Exception;

	public abstract Collection getCDangerListForClaim(String policyNo, DateTime damageDate) throws Exception;

	public abstract String getRecentlyEndorseNo(String paramString, int paramInt, DateTime paramDateTime) throws Exception;

	public abstract Collection getPDangerList(String paramString) throws Exception;

	public abstract PDangerVO getPDangerInfo(String paramString, int paramInt) throws Exception;

	public abstract Collection getCDangerList(String paramString) throws Exception;

	public abstract CDanger getCDangerInfo(String paramString, int paramInt) throws Exception;

	public abstract void saveCDangerUnit(CDangerVO paramCDangerVO) throws Exception;

	public abstract Collection getDangerRiskList(String paramString1, String paramString2, String paramString3) throws Exception;

	public abstract void saveTDangerUnit(TDangerVO paramTDangerVO) throws Exception;

	public abstract void savePDangerUnit(PDangerVO paramPDangerVO) throws Exception;
}

/*
 * Location: C:\Users\Jerry\Desktop\reinsNew.jar Qualified Name:
 * com.sinosoft.reins.interf.service.facade.BLDangerService JD-Core Version:
 * 0.5.3
 */