package com.tlg.aps.bs.fetPolicyService;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiNoticeCancle;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
public interface XchgTerminationNoticeService {	

	public Result batchtLiaMiNoticeCancle(TerminationNotice terminationNotice) throws SystemException,Exception;
	
	public Result batchLiaMiNoticeUnpaid(TerminationNotice terminationNotice) throws SystemException,Exception;
	
	public String genLia001Pdf(String serialno, String policyNo, Date createTime, String strPdfGenUrl, String pdfFolder) throws SystemException,Exception;
	
	public Result batchGenMobileInsSmsCancle(LiaMiNoticeCancle liaMiNoticeCancle, TerminationNotice terminationNotice, String pdfFilePath, Date createTime) throws SystemException, Exception;
	
	public Result batchGenMobileInsSmsUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid, TerminationNotice terminationNotice, String pdfFilePath, Date createTime) throws SystemException, Exception;
}
