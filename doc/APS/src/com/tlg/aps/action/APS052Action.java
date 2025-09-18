package com.tlg.aps.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.hasFetMatchFileService.HasFetMatchFileService;
import com.tlg.aps.bs.hasLionBackFileService.HasLawBackFileService;
import com.tlg.aps.bs.hasLionBackFileService.HasLionBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
/** mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 (全檔案覆蓋) */
/** mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 (全檔案覆蓋) */
public class APS052Action extends BaseAction {

	private HasLionBackFileService hasLionBackFileService;
	private HasLawBackFileService hasLawBackFileService;
	private HasFetMatchFileService hasFetMatchFileService;

	private void formLoad(String type) throws SystemException, Exception {
	}

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String btnExecuteHasLionBackFile() throws Exception {
		formLoad("btnQuery");
		try{
			String batchNo = (String)getPageInfo().getFilter().get("backFileBatchNo");
			String backFileType = (String)getPageInfo().getFilter().get("backFileType");
			String inputDataDate = (String)getPageInfo().getFilter().get("dataDate");
			String userId = getUserInfo().getUserId().toUpperCase();
			logger.info("batchNo :" + batchNo + ",backFileType :" + backFileType);
			if(StringUtil.isSpace(batchNo)) {
				if (!StringUtil.isSpace(inputDataDate)) {
					Date dataDate = new SimpleDateFormat("yyyyMMdd").parse((inputDataDate));
					Result result = hasLionBackFileService.runToGenerateFile(backFileType, new Date(), userId,
							"HAS_AGT_LION", dataDate);
					setMessage(result.getMessage().toString());
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String sysDate = sdf.format(new Date());
					Date dataDate = sdf.parse(sysDate);
					Result result = hasLionBackFileService.runToGenerateFile(backFileType, new Date(), userId,
							"HAS_AGT_LION", dataDate);
					setMessage(result.getMessage().toString());
				}
			} else {
				Map<String, String> returnData = hasLionBackFileService.generateFile(batchNo, userId, "HAS_AGT_LION",
						"2");
				if (!StringUtil.isSpace(returnData.get("outputFile"))) {
					File file = new File(returnData.get("outputFile"));
					if (file.exists()) {
						hasLionBackFileService.deleteFile(returnData.get("outputFile"),
								returnData.get("outputFile") + ".zip");
					}
				}
				if("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				}else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
		} catch (SystemException se) {
			logger.error("btnExecuteHasLionBackFile SystemException :" + se.toString());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error("btnExecuteHasLionBackFile Exception :" + e.toString());
			e.printStackTrace();
			throw e;
		}
		return Action.SUCCESS;
	}

	public String btnExecuteHasLawBackFile() throws Exception {
		formLoad("btnQuery");
		try {
			String batchNo = (String) getPageInfo().getFilter().get("backFileBatchNo2");
			String inputDataDate = (String) getPageInfo().getFilter().get("dataDate2");
			String userId = getUserInfo().getUserId().toUpperCase();
			logger.info(">>> batchNo:" + batchNo + ", inputDataDate:" + inputDataDate + ", userId: " + userId);

			if (StringUtils.isBlank(batchNo)) {
				if (!StringUtils.isBlank(inputDataDate)) {
					Date dataDate = new SimpleDateFormat("yyyyMMdd").parse((inputDataDate));
					Result result = hasLawBackFileService.runToGenerateFile(new Date(), userId, dataDate);
					setMessage(result.getMessage().toString());
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String sysDate = sdf.format(new Date());
					Date dataDate = sdf.parse(sysDate);
					Result result = hasLawBackFileService.runToGenerateFile(new Date(), userId, dataDate);
					setMessage(result.getMessage().toString());
				}
			} else {
				Map<String, String> returnData = hasLawBackFileService.generateFile(batchNo, userId, "2");
				if (!StringUtil.isSpace(returnData.get("outputFile"))) {
					File file = new File(returnData.get("outputFile"));
					if (file.exists()) {
						hasLawBackFileService.deleteFile(returnData.get("outputFile"));
					}
				}
				if ("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				} else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
		} catch (Exception e) {
			logger.error("btnExecuteHasLawBackFile Exception :" + e.getMessage());
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
	 * @return
	 * @throws Exception
	 */
	public String btnExecuteHasFetInsuredFile() throws Exception {
		formLoad("btnQuery");
		try {
			String batchNo = (String) getPageInfo().getFilter().get("backFileBatchNo3");
			String inputDataDate = (String) getPageInfo().getFilter().get("dataDate3");
			String backFileType = (String)getPageInfo().getFilter().get("backFileType2");
			String userId = getUserInfo().getUserId().toUpperCase();
			logger.info(">>> batchNo:" + batchNo + ", inputDataDate:" + inputDataDate + ", userId: " + userId);

			if (StringUtils.isBlank(batchNo)) {// 無批次號，從頭開始
				if (!StringUtils.isBlank(inputDataDate)) {
					Date dataDate = new SimpleDateFormat("yyyyMMdd").parse(inputDataDate);
					Result result = hasFetMatchFileService.runToGenerateFile(backFileType, new Date(), userId, dataDate);
					setMessage(result.getMessage().toString());
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String sysDate = sdf.format(new Date());
					Date dataDate = sdf.parse(sysDate);
					Result result = hasFetMatchFileService.runToGenerateFile(backFileType, new Date(), userId, dataDate);
					setMessage(result.getMessage().toString());
				}
			} else {//已有批次號，直接執行作業
				Map<String, String> returnData = hasFetMatchFileService.generateFile(batchNo, userId, "2");
				if (!StringUtil.isSpace(returnData.get("outputFile"))) {
					File file = new File(returnData.get("outputFile"));
					if (file.exists()) {
						hasLawBackFileService.deleteFile(returnData.get("outputFile"));
					}
				}
				if ("S".equals(returnData.get("status"))) {
					setMessage("執行完成");
				} else {
					setMessage("執行失敗, " + returnData.get("msg"));
				}
			}
		} catch (Exception e) {
			logger.error("btnExecuteHasFetInsuredFile Exception :" + e.getMessage());
			throw e;
		}
		return Action.SUCCESS;
	}

	public HasLionBackFileService getHasLionBackFileService() {
		return hasLionBackFileService;
	}

	public void setHasLionBackFileService(HasLionBackFileService hasLionBackFileService) {
		this.hasLionBackFileService = hasLionBackFileService;
	}

	public HasLawBackFileService getHasLawBackFileService() {
		return hasLawBackFileService;
	}

	public void setHasLawBackFileService(HasLawBackFileService hasLawBackFileService) {
		this.hasLawBackFileService = hasLawBackFileService;
	}

	public HasFetMatchFileService getHasFetMatchFileService() {
		return hasFetMatchFileService;
	}

	public void setHasFetMatchFileService(HasFetMatchFileService hasFetMatchFileService) {
		this.hasFetMatchFileService = hasFetMatchFileService;
	}
}
