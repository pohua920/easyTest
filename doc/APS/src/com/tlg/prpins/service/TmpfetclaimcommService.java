package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface TmpfetclaimcommService {

	public int countTmpfetclaimcomm(Map params) throws SystemException, Exception;

	public Result findTmpfetclaimcommByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findTmpfetclaimcommByParams(Map params) throws SystemException, Exception;

	public Result insertTmpfetclaimcomm(Tmpfetclaimcomm tmpfebclaimlcomm) throws SystemException, Exception;

	public Result updateTmpfetclaimcomm(Tmpfetclaimcomm tmpfebclaimlcomm) throws SystemException, Exception;

	public Result removeTmpfetclaimcomm(BigDecimal oid) throws SystemException, Exception;
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 */
	public Result removeTmpfetclaimcommAll() throws SystemException, Exception;
	public void batchInsertTmpfetclaimcomm(List<Tmpfetclaimcomm> tmpfebclaimlcomm) throws SystemException,Exception;
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */
}
