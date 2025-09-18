package com.tlg.aps.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;/** mantis：MOB0025，處理人員：CE035，公司戶紙本件線下批單資料，洗錢檢核X視為檢核成功*/
import org.apache.log4j.Logger;

import com.tlg.aps.bs.fetPolicyService.FetPolicyService;
import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.vo.ApplicantForEndorseCheckVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.msSqlMob.entity.FetAmlRecord;
import com.tlg.msSqlMob.service.FetAmlRecordService;
import com.tlg.util.AppContext;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

public class ChubbEndorseDataCheckThread implements Runnable {

	protected Logger logger = Logger.getLogger(ChubbEndorseDataCheckThread.class);
	private RunFetPolicyService runFetPolicyService;
	private FetPolicyService fetPolicyService;
	private List<ApplicantForEndorseCheckVo> list;
	private String groupNo;
	private FetAmlRecordService fetAmlRecordService;
	
	public ChubbEndorseDataCheckThread(String groupNo, List<ApplicantForEndorseCheckVo> list){
		this.list = list;
		this.groupNo = groupNo;
		this.runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
		this.fetPolicyService = (FetPolicyService)AppContext.getBean("fetPolicyService");
		this.fetAmlRecordService = (FetAmlRecordService)AppContext.getBean("fetAmlRecordService");
	}

	public void run() {
		try {
			logger.debug("ChubbEndorseDataCheckThread START： groupNo - " + this.groupNo + "," +new Date() + ", this.policyNolist.size() = " + this.list.size());
			
//			runFetPolicyService.chubbEndorseDataCheck(this.list, this.groupNo);
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());
			String title = "行動裝置險 - 線下批單資料檢核作業";
			ArrayList<String> errorList = null;
			try{
				if(list != null && list.size() > 0){
					errorList = new ArrayList<String>();  // 組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
					for(ApplicantForEndorseCheckVo vo : list){
						String transactionId = vo.getTransactionId();
						Map<String, String>  params = new HashMap<String, String>();
						params.put("policyNo", vo.getPolicyNo());
						int count = this.fetPolicyService.countFetMobilePolicy(params);
						if(count == 0){
							//缺少保單資料
							updateCustomerEndorse(transactionId, "X", "X", "X");
							errorList.add(transactionId + "," + vo.getPolicyNo() + "," + vo.getEndorseNo() + "," + "缺少保單資料");
							continue;
						}
						
						if(StringUtil.isSpace(vo.getEndorseNo()) || StringUtil.isSpace(vo.getCustomerId())){
							//缺少CUSTOMER_ENDORSE.ENDORSE_NO或APPLICANT_ENDORSE.CUSTOMER_ID
							updateCustomerEndorse(transactionId, "X", "X", "X");
							errorList.add(transactionId + "," + vo.getPolicyNo() + "," + vo.getEndorseNo() + "," + "批單號或是ID為空值");
							continue;
						}
						//黑名單
						String blacklistReturnCode = this.runFetPolicyService.callBlacklist(vo.getCustomerId());
						//AML
						/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表   START*/
						/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 僅日常文批資料須進洗錢系統，後續期繳不需再進洗錢系統審查   START*/
						String amlReturnCode = "0";
						if(vo.getProjectCodePremium() != null && vo.getProjectCodePremium().trim().length() > 0) {
							int projectCodePremuim = StringUtil.stringToInteger(vo.getProjectCodePremium());
							if(projectCodePremuim == 0) {// 僅日常文批資料須進洗錢系統，後續期繳不需再進洗錢系統審查
								Map<String, String> amlparams = new HashMap<String, String>();
								amlparams.put("policyNo", vo.getPolicyNo());
								amlparams.put("name", vo.getName());
								amlparams.put("customerId", vo.getCustomerId());
								amlparams.put("orderByCreatedtimeDesc", "Y");
								Result amlResult = fetAmlRecordService.findFetAmlRecordByParams(amlparams);// 以保單號 姓名 身分證字號查詢之前有無進過洗錢系統
								if(amlResult.getResObject() == null) {// 如果之前沒查詢過該筆保單則將此次查詢AML的條件存到FET_AML_RECORD資料表
									amlReturnCode = this.runFetPolicyService.callAml(vo.getPolicyNo(), vo.getCustomerId(), vo.getName(), vo.getBirthday());
									if("0".equals(amlReturnCode) || "1".equals(amlReturnCode)) {
										FetAmlRecord fetAmlRecord = new FetAmlRecord();
										fetAmlRecord.setTransactionId(transactionId);
										fetAmlRecord.setContractId(vo.getContractId());
										/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 匯入核心排程發信告知結果、修正手機險排程時間、修正批單號資料型態、洗錢條件檢核補上保批單號、遠傳資料批次下載使用者改ce035 */
										fetAmlRecord.setPolicyNo(vo.getPolicyNo());
										fetAmlRecord.setEndorseNo(vo.getEndorseNo());
										fetAmlRecord.setName(vo.getName());
										fetAmlRecord.setCustomerId(vo.getCustomerId());
										fetAmlRecord.setBirthday(vo.getBirthday());
										fetAmlRecord.setCtbcAmlStatus(amlReturnCode);
										/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 START*/
										fetAmlRecord.setTxId(vo.getTxId());
										fetAmlRecord.setDataSrc(vo.getDataSrc());
										/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 出單流程 END*/
										fetAmlRecordService.insertFetAmlRecord(fetAmlRecord);
									}
								} else {// 取之前進洗錢系統的檢核結果
									ArrayList<FetAmlRecord> fetAmlRecordList = (ArrayList<FetAmlRecord>)amlResult.getResObject();
									FetAmlRecord fetAmlRecord = fetAmlRecordList.get(0);
									amlReturnCode = fetAmlRecord.getCtbcAmlStatus();
								}
							}
						}
						/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 僅日常文批資料須進洗錢系統，後續期繳不需再進洗錢系統審查   END*/
						/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表  END*/
						//利關人
						String stokeHolderReturnCode = this.runFetPolicyService.callStakeHolder(transactionId, vo.getCustomerId());
						
						String errMsg = "";
						if("X".equals(blacklistReturnCode)){
							errMsg = StringUtil.appendStr(errMsg, "觀察名單檢核失敗", "&");
						}
						/** mantis：MOB0025，處理人員：CE035，公司戶紙本件線下批單資料，洗錢檢核X視為檢核成功*/
						if("X".equals(amlReturnCode) && StringUtils.isNotBlank(vo.getCustomerId()) && !vo.getCustomerId().matches("^\\d{8}$")){
							errMsg = StringUtil.appendStr(errMsg, "洗錢檢核失敗", "&");
						}
						if("X".equals(stokeHolderReturnCode)){
							errMsg = StringUtil.appendStr(errMsg, "利關人檢核失敗", "&");
						}
						
						if(!"".equals(errMsg)){
							errorList.add(transactionId + "," + vo.getPolicyNo() + "," + vo.getEndorseNo() + "," + errMsg);
							continue;
						}
						
						updateCustomerEndorse(transactionId, blacklistReturnCode, amlReturnCode, stokeHolderReturnCode);
						continue;
					}
				}else{
					logger.debug("this.list is null ，線下批單資料檢核作業 執行結束");
				}
			}catch(Exception e){
				e.printStackTrace();
				
			}finally{
				if(errorList != null && errorList.size() > 0){
					this.runFetPolicyService.sendErrorMail(errorList, executeDate, groupNo, title);
				}
			}
			logger.debug("ChubbEndorseDataCheckThread END： groupNo - " + this.groupNo + "," +new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * mantis：MOB0012，處理人員：BI086，需求單編號：MOB0012 線下批單資料檢核作業
	 * 
	 * @param transactionId
	 * @param blackStatus
	 * @param amlStatus
	 * @param stockHolderStatus
	 * @return
	 * @throws SystemException
	 * @throws Exception
	 */
	private Result updateCustomerEndorse(String transactionId, String blackStatus, String amlStatus, String stockHolderStatus) throws SystemException, Exception{
		
		Result result = this.fetPolicyService.findCustomerEndorseByUK(transactionId);
		if(result.getResObject() == null){
			throw new SystemException("無法取得CustomerEndorse資料");
		}
		CustomerEndorse customerEndorse = (CustomerEndorse)result.getResObject();
		customerEndorse.setCtbcAmlStatus(amlStatus);
		customerEndorse.setCtbcBlacklistStatus(blackStatus);
		customerEndorse.setCtbcStockholderStatus(stockHolderStatus);
		customerEndorse.setModifiedBy("SYSTEM8");
		customerEndorse.setModifiedTime(new Date());
		
		if(!"X".equalsIgnoreCase(blackStatus) && !"X".equalsIgnoreCase(amlStatus) && !"X".equalsIgnoreCase(stockHolderStatus)){
			customerEndorse.setDataStatus("01");
		}		
		result = this.fetPolicyService.updateCustomerEndorse(customerEndorse);
		//TODO 更新失敗？
		
		return result;
	}

}

