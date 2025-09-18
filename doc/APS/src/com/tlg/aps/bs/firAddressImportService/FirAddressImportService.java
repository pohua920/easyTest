package com.tlg.aps.bs.firAddressImportService;

import java.util.Map;

import com.tlg.aps.vo.AddressFormatDataVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.prpins.entity.FirAddrImporterr;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface FirAddressImportService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;
	
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;
	
	public Result updateFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception;
	
	public void truncateFirAddrCkdata() throws SystemException, Exception;

	public Result insertFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception;

	public Result insertFirAddrImporterr(FirAddrImporterr firAddrImporterr,UserInfo userInfo) throws SystemException, Exception;
	
	public int countFirAddrCkdata(Map params) throws SystemException, Exception;
	
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
	public Result findFirAddrCkdataByPolicyno(String policyno) throws SystemException, Exception;
	
	public Result updateFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception;
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/
	
	//mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整
	public AddressFormatDataVo addressFormat(String address) throws SystemException, Exception;
}
