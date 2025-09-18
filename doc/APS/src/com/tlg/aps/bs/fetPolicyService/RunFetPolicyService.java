package com.tlg.aps.bs.fetPolicyService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface RunFetPolicyService {

	public Result getDailyInsuranceDataFromFet(String rptBatchNo) throws SystemException,Exception;
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
	public Result checkFetInsuranceData() throws SystemException,Exception;
	
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 */
	public Result getProposalFileFromFet() throws SystemException,Exception;
	
	/**mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
	public Result updatePolicyDataByChubbReturnData() throws SystemException,Exception;
	
	/**mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 start */
	public String callAml(String transactionId, String customerId, String name, String birthdayDate) throws Exception;
	
	public String callBlacklist(String customerId) throws Exception;
	
	public String callStakeHolder(String transactionId, String customerId) throws Exception;
	
	public void sendErrorMail(ArrayList<String> errorList, String executeDate, String groupNo, String title) throws AddressException, UnsupportedEncodingException, MessagingException;
	/**mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 end */
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到各資料表 start */
	public Result downloadChFile() throws SystemException,Exception;
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
	public Result downloadAcFile(String textValue) throws SystemException,Exception;
	
	public Result downloadClFile() throws SystemException,Exception;
	
	public Result downloadCmFile() throws SystemException,Exception;
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到各資料表 end */
	
	/**mantis：MOB0009，處理人員：BJ016，需求單編號：MOB0009 傳送回饋檔給遠傳 start */
	public Result offerDailyFeedbackFileToFet() throws SystemException,Exception;
	public Result offerFeedbackAcFileToFet() throws SystemException,Exception;
	/**mantis：MOB0009，處理人員：BJ016，需求單編號：MOB0009 傳送回饋檔給遠傳 end */
	
	/**mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020 發送電子保單 start */
	public Result sendMobileEpolicy() throws SystemException,Exception;
	/**mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020 發送電子保單 end */
	
	/**mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表  start*/
	public Result downloadAccountFile() throws SystemException, Exception;
	/**mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表  end*/
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 START*/
	public Result terminationNotice() throws SystemException, Exception;
	public Result doTerminationNoticeCancel(String transactionId) throws SystemException, Exception;
	public Result doTerminationNoticeUnpaid(String transactionId) throws SystemException, Exception;
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 END*/
	
	public Result downloadRepairXmlFile(String strNowDate) throws SystemException, Exception;
	
	public Result downloadFetPaidFile() throws SystemException,Exception;
	public Result downloadFetPayableFile() throws SystemException,Exception;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
	public Result transferMiPolicyToCore(FetMobilePolicy fetMobilePolicy, PrpinsAgentRespVo estorePrpinsAgentRespVo) throws SystemException,Exception;
	
	public Result downloadChubbEndorseFile() throws SystemException,Exception;
	public Result chubbEndorseFileDataTransfer() throws SystemException,Exception;
	
	public Result sendProposalInsufficientNotify() throws SystemException,Exception;
	/**mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員 */
	public Result sendUncheckApplicationNotify() throws SystemException,Exception;
	public Result storeProposalInsufficientSms(Application application) throws SystemException,Exception;
	public Result sendProposalInsufficientSms() throws SystemException,Exception;
	
	public Result genFetCancelNotificationData() throws SystemException,Exception;
	public Result genFetCancelNotificationData(String date) throws SystemException,Exception;
	public Result uploadFetCancelNotificationData() throws SystemException,Exception;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 START*/
	public Result returnPolicyNoToG10(String rptBatchNo) throws SystemException,Exception;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
	public PrpinsAgentRespVo prpinsAgentQuery(String identifyNumber) throws Exception;
}
