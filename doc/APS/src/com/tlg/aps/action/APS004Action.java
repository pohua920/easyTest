package com.tlg.aps.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firBotRenewalFileService.FirBotReceiveFDFileService;
/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
import com.tlg.aps.bs.firBotRenewalFileService.FirBotRenewalFileService;
/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程*/
import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbRenewalFileService;
import com.tlg.aps.bs.firGenRenewListService.FirGenRenewListService;
import com.tlg.aps.bs.firIssueCheckService.FirIssueCheckService;
import com.tlg.aps.bs.firPanhsinBackFileService.FirPanhsinBackFileService;
import com.tlg.aps.bs.firProcFpolicyRerunService.FirProcFpolicyRerunService;
import com.tlg.aps.bs.firTiiDataServerce.FirTiiDataService;
import com.tlg.aps.bs.firUbNewPolicyService.FirUbBackFileService;
import com.tlg.aps.bs.firUbNewPolicyService.FirUbProposalFileImportService;
// mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
import com.tlg.aps.bs.firYcbBackFileService.FirYcbBackFileService;
import com.tlg.aps.bs.generatePolicyPassbookService.GeneratePolicyPassbookService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS004Action extends BaseAction {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	private FirIssueCheckService firIssueCheckService;
	private List<FirTmpDatacheck> devResults = new ArrayList<FirTmpDatacheck>();
	//mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格
	private GeneratePolicyPassbookService generatePolicyPassbookService;
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START */
	private FirPanhsinBackFileService firPanhsinBackFileService;
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END */
    // mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
	private FirYcbBackFileService firYcbBackFileService;
	//mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程
	private FirUbProposalFileImportService firUbProposalFileImportService;
	//mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程
	private FirUbBackFileService firUbBackFileService;
	
	//mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程
	private FirGenRenewListService firGenRenewListService;
	
	//mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格
	private FirTiiDataService firTiiDataService;
	
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
	private FirBotRenewalFileService firBotRenewalFileService;

	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
	private FirProcFpolicyRerunService firProcFpolicyRerunService;
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
	private FirBotReceiveFDFileService firBotReceiveFDFileService;
	
	/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程*/
	private FirYcbRenewalFileService firYcbRenewalFileService;
	
	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("btnQuery");
			Result result = firIssueCheckService.firIssueCheck(getUserInfo());
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			formLoad("execute");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	//mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 start
	/** APS004E0.jsp頁面按下手動執行鍵,開始執行 **/
	public String btnExecuteOthPatchPassbook() throws Exception {
		try{
			String batchNo = (String)getPageInfo().getFilter().get("batchNo");
			logger.info("batchNo :" + batchNo);
			if(StringUtil.isSpace(batchNo)) {
				Result result = generatePolicyPassbookService.runToGeneratePolicyPassbook(getUserInfo().getUserId().toUpperCase(), "OTH_PASSBOOK_01");
				setMessage(result.getMessage().toString());
			}else {
				String returnCode = generatePolicyPassbookService.generatePolicyPassbook(getUserInfo().getUserId().toUpperCase(), batchNo,"2");
				switch (returnCode) {
				case "0":
					setMessage("無資料");
					break;
				case "1":
					setMessage("完成");
					break;
				case "2":
					setMessage("失敗");
					break;
				}
			}
		} catch (SystemException se) {
			logger.error("btnExecuteOthPatchPassbook SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteOthPatchPassbook Exception :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	//mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 end
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START */
	public String btnExecuteFirPanhsinBackFile() throws Exception {
		formLoad("btnQuery");
		try{
			String batchNo = (String)getPageInfo().getFilter().get("backFileBatchNo");
			String backFileType = (String)getPageInfo().getFilter().get("backFileType");
			String userId = getUserInfo().getUserId().toUpperCase();
			logger.info("batchNo :" + batchNo + ",backFileType :" + backFileType);
			if(StringUtil.isSpace(batchNo)) {
				Result result = firPanhsinBackFileService.runToGenerateFile(backFileType, new Date(), userId, "FIR_AGT_BOP_FB");
				setMessage(result.getMessage().toString());
			}else {
				Map<String,String> returnData = firPanhsinBackFileService.generateFile(batchNo, userId, "FIR_AGT_BOP_FB", "2");
				if("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				}else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
		} catch (SystemException se) {
			logger.error("btnExecuteFirPanhsinBackFile SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirPanhsinBackFile Exception :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END */

	/**
	 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
	 */
	public String btnExecuteFirYcbBackFile() throws Exception {
		formLoad("btnQuery");
		try{
			String batchNo = (String)getPageInfo().getFilter().get("backFileBatchNo");
			String userId = getUserInfo().getUserId().toUpperCase();
			logger.info("batchNo :" + batchNo);
			if(StringUtil.isSpace(batchNo)) {
				Result result = firYcbBackFileService.runToGenerateFile(new Date(), userId, "FIR_AGT_YCB_FB");
				setMessage(result.getMessage().toString());
			}else {
				Map<String,String> returnData = firYcbBackFileService.generateFile(batchNo, userId, "FIR_AGT_YCB_FB", "2");
				if("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				}else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
		} catch (SystemException se) {
			logger.error("btnExecuteFirYcbBackFile SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirYcbBackFile Exception :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/**mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 
	 * APS004E0.jsp頁面按下14執行按鈕,開始執行聯邦要保進度檔接收作業 **/
	public String btnExecuteUbProposal() throws Exception {
		try{
			Result result = firUbProposalFileImportService.runToImportFile(getUserInfo().getUserId().toUpperCase(),
					new Date(), "FIR_AGT_UB_01");
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			logger.error("btnExecuteUbProposal SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteUbProposal SystemException :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程
	 * APS004E0.jsp頁面按下13執行按鈕,開始執行聯邦回饋檔產生作業作業 **/
	public String btnExecuteUbBackFile() throws Exception {
		try{
			Result result = new Result();
			String batchNo = (String)getPageInfo().getFilter().get("ubBackFileBatchNo");
			String type = "2";
			String userId = getUserInfo().getUserId().toUpperCase();
			String programId = "FIR_AGT_UB_02";
			if(StringUtil.isSpace(batchNo)) {
				type="1";
				result = firUbBackFileService.runToReceiveData(userId,
					new Date(), programId, type);
				setMessage(result.getMessage().toString());
			}else {
				Map<String,String> returnData = firUbBackFileService.generateFile(batchNo, userId, programId, type);
				if("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				}else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
			
		} catch (SystemException se) {
			logger.error("btnExecuteUbBackFile SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteUbBackFile SystemException :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程
	 * APS004E0.jsp頁面按下15執行按鈕,開始執行住火應續件清單作業 **/
	public String btnExecuteFirRenewList() throws Exception {
		try{
			
			String rnYymm = (String)getPageInfo().getFilter().get("rnYymm");
			String userId = getUserInfo().getUserId().toUpperCase();
			String programId = "FIR_RENEW_LIST";
			Result result = firGenRenewListService.runToGenerateList(rnYymm, new Date(), userId, programId);
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			logger.error("btnExecuteFirRenewList SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirRenewList SystemException :" + e.toString());
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格
	 * APS004E0.jsp頁面按下16執行按鈕,開始執行保發中心-住火保批資料產生(SP)作業 **/
	public String btnExecuteFirTiiSp() throws Exception {
		formLoad("btnExecuteFirTiiSp");
		try{
			String spType = (String)getPageInfo().getFilter().get("spType");
			String tiiBusinessNo = (String)getPageInfo().getFilter().get("tiiBusinessNo");
			String tiiUndate = (String)getPageInfo().getFilter().get("tiiUndate");
			//檢核簽單日必填且為合理日
			Date undate = null;
			if(!StringUtil.isSpace(tiiUndate)) {
				undate = parseDate(tiiUndate,"yyyy/MM/dd");
			}
			if(!"AS400".equals(spType) && null == undate) {
				setMessage("簽單日期為必填且須為合理日期!");
				return Action.SUCCESS;
			}
			logger.info("保發中心-住火保批資料產生(SP)-手動執行開始...");
			Result result = firTiiDataService.callSpToGenData(spType, tiiBusinessNo, undate,
					getUserInfo().getUserId().toUpperCase(), "FIR_BATCH_TII_01");
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteFirTiiSp SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirTiiSp Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("保發中心-住火保批資料產生(SP)-手動執行結束...");
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格
	 * APS004E0.jsp頁面按下17執行按鈕,開始執行保發中心-住火保批資料傳輸作業 **/
	public String btnExecuteGenTiiFile() throws Exception {
		formLoad("btnExecuteGenTiiFile");
		try{
			String batchNo = (String)getPageInfo().getFilter().get("firTiiBatchNo");
			String type = "1";
			if(!StringUtil.isSpace(batchNo)) {
				type = "2";
			}
			Result result = firTiiDataService.generateFileAndUpload(getUserInfo().getUserId().toUpperCase(), batchNo, type, "FIR_BATCH_TII_02");
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteGenTiiFile SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteGenTiiFile Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
	public String btnExecuteBotrn() throws Exception {
		formLoad("btnExecuteBotrn");
		
		try{
			String rnDate = (String)getPageInfo().getFilter().get("rnDate");
			String userId = getUserInfo().getUserId().toUpperCase();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
		
			//沒輸入續保日期 ，自動帶入系統時間加兩個月
			if(StringUtil.isSpace(rnDate)){
				Calendar c =Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.MONTH, 2);
				rnDate=sdf.format(c.getTime());
			}
			
				Result result =firBotRenewalFileService.runToReceiveData(userId, new Date(), "FIR_AGT_BOTRN", rnDate);
			 setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteFirTiiSp SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirTiiSp Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("台銀續保資料-手動執行結束...");
		
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
	public String btnExecuteFpolicyRerun() throws Exception {
		formLoad("btnExecuteFpolicyRerun");
		String userId = getUserInfo().getUserId().toUpperCase();
		String underwriteenddate=(String)getPageInfo().getFilter().get("rerunUndate");
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
		if(StringUtil.isSpace(underwriteenddate)){
			
			underwriteenddate=sdf.format(new Date());
		}
		underwriteenddate+=" 00:00:00";
		String programId="FIR_PROC_FPOLICY_RERUN";
		try{
			Result result = firProcFpolicyRerunService.rerunFpolicy(new Date(), underwriteenddate, userId, programId);
			 setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteFpolicyRerun SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFpolicyRerun Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("住火_新核心保單轉入中介檔異常處理-手動執行結束...");
		
		return Action.SUCCESS;
	}
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  start**/
	public String btnExecuteBotFdreceive() throws Exception {
		formLoad("btnExecuteFdreceive");
		String userId = getUserInfo().getUserId().toUpperCase();
		String programId="FIR_AGT_BOT_FD";
		try{
			Result result =firBotReceiveFDFileService.runToReceiveData(userId, new Date(), programId);
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteFdreceive SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFdreceive Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("臺銀FD簽署資料接收-手動執行結束...");
		
		return Action.SUCCESS;
	}
	
	public String btnExecuteBotFdCompare() throws Exception {
		formLoad("btnExecuteBotFdCompare");
		String userId = getUserInfo().getUserId().toUpperCase();
		String programId="FIR_AGT_BOTRN_FD";
		try{
			Result result =firBotRenewalFileService.compareFDIntoCore(userId, new Date(), programId);
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteBotFdCompare SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteBotFdCompare Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("臺銀續保資料比對簽署檔作業-手動執行結束...");
		
		return Action.SUCCESS;
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程  end**/
	
	/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 **/
	private Date parseDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date parseDate;
		try {
			parseDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return parseDate;
	}
	
	/**
	 * mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程
	 * @return
	 * @throws Exception
	 */
	public String btnExecuteYcbrn() throws Exception {
		formLoad("btnExecuteYcbrn");
		
		try{
			String rnDate = (String)getPageInfo().getFilter().get("ycbrnDate");
			String userId = getUserInfo().getUserId().toUpperCase();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
		
			//沒輸入續保日期 ，自動帶入系統時間加兩個月
			if(StringUtil.isSpace(rnDate)){
				Calendar c =Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.MONTH, 2);
				rnDate=sdf.format(c.getTime());
			}
			
				Result result = firYcbRenewalFileService.runToReceiveData(userId, new Date(), "FIR_AGT_YCBRN", rnDate);
			 setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			logger.error("btnExecuteFirTiiSp SystemException :" + se);
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteFirTiiSp Exception :" + e);
			getRequest().setAttribute("exception", e);
			throw e;
		}
		logger.info("元大續保資料-手動執行結束...");
		
		return Action.SUCCESS;
	}

	public List<FirTmpDatacheck> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirTmpDatacheck> devResults) {
		this.devResults = devResults;
	}

	public FirIssueCheckService getFirIssueCheckService() {
		return firIssueCheckService;
	}

	public void setFirIssueCheckService(FirIssueCheckService firIssueCheckService) {
		this.firIssueCheckService = firIssueCheckService;
	}
	//mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 start
	public GeneratePolicyPassbookService getGeneratePolicyPassbookService() {
		return generatePolicyPassbookService;
	}

	public void setGeneratePolicyPassbookService(GeneratePolicyPassbookService generatePolicyPassbookService) {
		this.generatePolicyPassbookService = generatePolicyPassbookService;
	}
	//mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 end
	
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START */
	public FirPanhsinBackFileService getFirPanhsinBackFileService() {
		return firPanhsinBackFileService;
	}

	public void setFirPanhsinBackFileService(FirPanhsinBackFileService firPanhsinBackFileService) {
		this.firPanhsinBackFileService = firPanhsinBackFileService;
	}
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END */

	// mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 -- start
	public FirYcbBackFileService getFirYcbBackFileService() {
		return firYcbBackFileService;
	}

	public void setFirYcbBackFileService(FirYcbBackFileService firYcbBackFileService) {
		this.firYcbBackFileService = firYcbBackFileService;
	}
	// mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 -- end

	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start*/
	public FirUbProposalFileImportService getFirUbProposalFileImportService() {
		return firUbProposalFileImportService;
	}
	
	public void setFirUbProposalFileImportService(FirUbProposalFileImportService firUbProposalFileImportService) {
		this.firUbProposalFileImportService = firUbProposalFileImportService;
	}
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end*/

	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start*/
	public FirUbBackFileService getFirUbBackFileService() {
		return firUbBackFileService;
	}
	
	public void setFirUbBackFileService(FirUbBackFileService firUbBackFileService) {
		this.firUbBackFileService = firUbBackFileService;
	}
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end*/

	/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start*/
	public FirGenRenewListService getFirGenRenewListService() {
		return firGenRenewListService;
	}

	public void setFirGenRenewListService(FirGenRenewListService firGenRenewListService) {
		this.firGenRenewListService = firGenRenewListService;
	}
	/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end*/

	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start */
	public FirTiiDataService getFirTiiDataService() {
		return firTiiDataService;
	}

	public void setFirTiiDataService(FirTiiDataService firTiiDataService) {
		this.firTiiDataService = firTiiDataService;
	}
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end */

	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業 start **/
	public FirBotRenewalFileService getFirBotRenewalFileService() {
		return firBotRenewalFileService;
	}

	public void setFirBotRenewalFileService(FirBotRenewalFileService firBotRenewalFileService) {
		this.firBotRenewalFileService = firBotRenewalFileService;
	}
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  end**/

	
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程   start**/
	public FirProcFpolicyRerunService getFirProcFpolicyRerunService() {
		return firProcFpolicyRerunService;
	}

	public void setFirProcFpolicyRerunService(FirProcFpolicyRerunService firProcFpolicyRerunService) {
		this.firProcFpolicyRerunService = firProcFpolicyRerunService;
	}
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  end**/
	
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 start**/
	public FirBotReceiveFDFileService getFirBotReceiveFDFileService() {
		return firBotReceiveFDFileService;
	}

	public void setFirBotReceiveFDFileService(FirBotReceiveFDFileService firBotReceiveFDFileService) {
		this.firBotReceiveFDFileService = firBotReceiveFDFileService;
	}
	/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 end**/

	/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程START  **/
	public FirYcbRenewalFileService getFirYcbRenewalFileService() {
		return firYcbRenewalFileService;
	}

	public void setFirYcbRenewalFileService(FirYcbRenewalFileService firYcbRenewalFileService) {
		this.firYcbRenewalFileService = firYcbRenewalFileService;
	}
	/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程 END **/
	
}
