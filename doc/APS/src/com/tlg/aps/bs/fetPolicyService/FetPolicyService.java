package com.tlg.aps.bs.fetPolicyService;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.BatchRepairVo;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.vo.mob.fetPolicy.response.CustomerVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.entity.ChubbReturnCustomer;
import com.tlg.msSqlMob.entity.Customer;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.msSqlMob.entity.FetCancelNotification;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.msSqlMob.entity.FetPaid;
import com.tlg.msSqlMob.entity.FetPayable;
import com.tlg.msSqlMob.entity.AcFile;
import com.tlg.msSqlMob.entity.AccountFile;
import com.tlg.msSqlMob.entity.ChFile;
import com.tlg.msSqlMob.entity.ChubbApplicantEndorse;
import com.tlg.msSqlMob.entity.ChubbCustomerEndorse;
import com.tlg.msSqlMob.entity.ChubbInsuredEndorse;
import com.tlg.msSqlMob.entity.ChubbProductEndorse;
import com.tlg.msSqlMob.entity.ClFile;
import com.tlg.msSqlMob.entity.CmFile;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface FetPolicyService {
	
	public MobileInsBatchInfo insertMobileInsBatchInfo(Date executeTime) throws SystemException,Exception;
	
	public Result updateMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException,Exception;
	
	/** mantis：MOB0028，處理人員：CE035，需求單編號：MOB0028 修正同批遠傳資料中途退保重複的問題  */
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
	public void insertData(CustomerVo data, List<CustomerVo> cancelList, EnumMobileDataSrc enumDataSrc) throws SystemException,Exception;
	
	public Result insertFetPolicyImportError(CustomerVo data, EnumMobileDataSrc enumDataSrc) throws SystemException,Exception;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
	public Result updateCustomer(Customer data) throws SystemException,Exception;
	
	/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 */
	public void insertFileData(List<String> filename, File zipFile) throws SystemException,Exception;
	
	/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
	public void updatePolicyDataByChubbReturn(ChubbReturnCustomer chubbReturnCustomer, Application application) throws SystemException,Exception;
	
	/** mantis：MOB0011，處理人員：BI086，需求單編號：MOB0011 呼叫web service後結果狀態更新 */
	public Result updateFetMobilePolicy(FetMobilePolicy fetMobilePolicy) throws SystemException, Exception;
	public Result findFetMobilePolicyByParams(Map params) throws SystemException, Exception;
	public Result findFetMobilePolicyDeviceByUK(String transactionId) throws SystemException, Exception;
	public Result findFetMobilePolicyInsurantInfoByUK(String transactionId) throws SystemException, Exception;
	
	/** mantis：MOB0012，mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業 */
	public int countFetMobilePolicy(Map params) throws SystemException, Exception;
	public Result updateCustomerEndorse(CustomerEndorse customerEndorse) throws SystemException, Exception;
	public Result findCustomerEndorseByUK(String transactionId) throws SystemException, Exception;

	/** mantis：MOB0013，處理人員：BI086，需求單編號：MOB0013線下批單資料處理作業 */
	public boolean insertEndorseData(FetMobilePolicy fetMobilePolicy, 
		FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo, 
		FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException,Exception;
	/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 start*/
	public void insertDataToFetMobilePolicy(FetMobilePolicy fetMobilePolicy,
			FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo, FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException, Exception;
	public Result updateApplication(Application application) throws SystemException, Exception;
	/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 end*/
	
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  start*/
	public boolean batchInsertAcFile(List<AcFile> lists) throws SystemException,Exception;	
	
	public boolean batchInsertChFile(List<ChFile> lists) throws SystemException,Exception;	
	
	public boolean batchInsertClFile(List<ClFile> lists) throws SystemException,Exception;
	
	public boolean batchInsertCmFile(List<CmFile> lists) throws SystemException,Exception;
	
	public boolean updateFetMobilePolicyStatus(String policyNo, String endorseNo, String premium, String installments) throws SystemException,Exception;
	/**mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的回饋檔記錄到相對應資料表  end*/
	
	public boolean insertFetMobileEpolicy(String transactionId, String policyNo, String status) throws SystemException,Exception;
	public boolean genMobileEpolicyData(String transactionId) throws SystemException,Exception;
	
	/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
	public boolean batchInsertAccountFile(List<AccountFile> lists) throws SystemException,Exception;	
	
	/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
	public boolean batchInsertTerminationNotice(List<TerminationNotice> lists) throws SystemException,Exception;
	
	public boolean batchInsertRepairXmlData(BatchRepairVo batchRepairVo) throws SystemException,Exception;
	
	public boolean proposalFileCheck(String transactionId, String userId) throws SystemException,Exception;
	
	public boolean batchInsertFetPaid(List<FetPaid> lists) throws SystemException,Exception;	
	public boolean batchInsertFetPayable(List<FetPayable> lists) throws SystemException,Exception;	
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
	public String importMiPolicyToCore(FetMobilePolicy fetMobilePolicy, PrpinsAgentRespVo estorePrpinsAgentRespVo) throws SystemException,Exception;
	
	public boolean batchInsertChubbEndorseData(ChubbCustomerEndorse chubbCustomerEndorse) throws SystemException,Exception;
	
	public boolean batchInsertChubbApplicantEndorse(List<ChubbApplicantEndorse> lists) throws SystemException,Exception;
	public boolean batchInsertChubbCustomerEndorse(List<ChubbCustomerEndorse> lists) throws SystemException,Exception;
	public boolean batchInsertChubbInsuredEndorse(List<ChubbInsuredEndorse> lists) throws SystemException,Exception;
	public boolean batchInsertChubbProductEndorse(List<ChubbProductEndorse> lists) throws SystemException,Exception;
	
	public boolean batchUpdateFetCancelNotification(List<FetCancelNotification> lists, Date uploadDate) throws SystemException,Exception;
}
