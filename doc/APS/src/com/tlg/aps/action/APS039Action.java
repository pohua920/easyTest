package com.tlg.aps.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps039DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.entity.FirRptCtbcCtf;
import com.tlg.prpins.service.FirCtbcB2b2cService;
import com.tlg.prpins.service.FirCtbcRstService;
import com.tlg.prpins.service.FirRptCtbcCtfService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.RptUtil;
import com.tlg.util.StringUtil;

/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 */
@SuppressWarnings("serial")
public class APS039Action extends BaseAction {
	//Service
	private FirCtbcB2b2cService firCtbcB2b2cService;
	private FirRptCtbcCtfService firRptCtbcCtfService;
	private ConfigUtil configUtil;
	//mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔
	private FirCtbcRstService firCtbcRstService;
	
	//Entity
	private FirCtbcB2b2c firCtbcB2b2c;
	private Aps039DetailVo aps039DetailVo;
	
	private List<FirCtbcB2b2c> devResults;
	private String type;
	private InputStream fileInputStream;
	private String orderseq;

	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS039E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(type == null) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("sortBy", "DCREATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			getStatus();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		parameterHandler();
		Result result = firCtbcB2b2cService.findFirCtbcB2b2cByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirCtbcB2b2c>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}

	/** APS039E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			getStatus();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}

	/** APS039E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			getStatus();
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS039E0.jsp頁面按下主檔批次號碼資料，查詢明細資料 */
	public String lnkGoDetailQuery() throws Exception {
		try {
			if (null == firCtbcB2b2c.getBatchNo() || null == firCtbcB2b2c.getBatchSeq() || null == firCtbcB2b2c.getFkOrderSeq()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", firCtbcB2b2c.getBatchNo());
				params.put("batchSeq", firCtbcB2b2c.getBatchSeq());
				params.put("fkOrderSeq", firCtbcB2b2c.getFkOrderSeq());
				dtlQuery(params);
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS039E1.jsp按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			Map<String,Object> params = new HashMap<>();
			params.put("batchNo", firCtbcB2b2c.getBatchNo());
			params.put("batchSeq", firCtbcB2b2c.getBatchSeq());
			params.put("fkOrderSeq", firCtbcB2b2c.getFkOrderSeq());
			dtlQuery(params);
			setMessage("存檔成功");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public String lnkDownloadProve() throws Exception {
		BigDecimal oid = aps039DetailVo.getOid();

        try{
        	Map<String, BigDecimal> params = new HashMap<>();
    		params.put("oid", oid);
    		Result result = this.firRptCtbcCtfService.findFirRptCtbcCtfByOid(oid);
    		if(result != null && result.getResObject() != null) {
    			FirRptCtbcCtf firRptCtbcCtf = (FirRptCtbcCtf)result.getResObject();
    			RptUtil rptUtil = new RptUtil(configUtil.getString("rptUrl"));
    			String rptFilePath = configUtil.getString("tempFolder");
    			File fileFolder = new File(rptFilePath);
    			if(!fileFolder.exists()) {
    				fileFolder.mkdirs();
    			}
    			orderseq = firRptCtbcCtf.getOrderseq();
    			boolean check = rptUtil.genFir00103Pdf(rptFilePath, oid.toString(), orderseq);
    			if(check) {
    				File pdfFile = new File(rptFilePath + orderseq + ".pdf");
    				if(pdfFile.exists()) {
    					this.fileInputStream = new DeleteAfterDownloadFileInputStream(pdfFile);
    				}else {
    					setMessage("查無檔案");
    					return Action.INPUT;
    				}
    			}else {
    				setMessage("查無檔案");
    				return Action.INPUT;
    			}
    		}
		}catch (IOException e) {
	            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	//明細資料查詢
	private Result dtlQuery(Map params) throws Exception {
		Result	result = firCtbcB2b2cService.findAPS039DetailByParams(params);
		
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			aps039DetailVo = (Aps039DetailVo) result.getResObject();
			/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 start*/
			Map<String,String> params2= new HashMap<>(); 
			params2.put("oBatchNo", aps039DetailVo.getBatchNo());
			params2.put("oBatchSeq", aps039DetailVo.getBatchSeq());
			result = firCtbcRstService.findFirCtbcRstByParams(params2);
			if(null != result.getResObject()) {
				List<FirCtbcRst> firCtbcRstList = (List<FirCtbcRst>) result.getResObject();
				aps039DetailVo.setInscoFeedback(firCtbcRstList.get(0).getInscoFeedback());
			}
			/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 end*/
		}
		return result;
	}

	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", aps039DetailVo.getBatchNo());
		params.put("batchSeq", aps039DetailVo.getBatchSeq());
		params.put("fkOrderSeq", aps039DetailVo.getFkOrderSeq());
		Result result = firCtbcB2b2cService.findFirCtbcB2b2cByUk(params);
		
		firCtbcB2b2c = (FirCtbcB2b2c) result.getResObject();
		
		firCtbcB2b2c.setIupdate(getUserInfo().getUserId().toUpperCase());
		firCtbcB2b2c.setDupdate(new Date());
		firCtbcB2b2c.setRemark(aps039DetailVo.getRemark());
		firCtbcB2b2cService.updateFirCtbcB2b2c(firCtbcB2b2c);
	}

	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		strDate = rocToAd(strDate, "/");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		strDate = rocToAd(strDate, "/");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}

	/** 轉換民國年為西元年 */
	private String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	/** 取下拉選單的值，若為空白則移除filter */
	private void getStatus() {
		if (getPageInfo().getFilter().get("orderSeqStatus")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("orderSeqStatus"))) {
			getPageInfo().getFilter().remove("orderSeqStatus");
		}
		if (getPageInfo().getFilter().get("debitType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("debitType"))) {
			getPageInfo().getFilter().remove("debitType");
		}
		if (getPageInfo().getFilter().get("sendType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("sendType"))) {
			getPageInfo().getFilter().remove("sendType");
		}
		if (getPageInfo().getFilter().get("sendToBank")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("sendToBank"))) {
			getPageInfo().getFilter().remove("sendToBank");
		}
		/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 start*/
		if (getPageInfo().getFilter().get("bkType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("bkType"))) {
			getPageInfo().getFilter().remove("bkType");
		}
		/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 end*/
	}

	public FirCtbcB2b2cService getFirCtbcB2b2cService() {
		return firCtbcB2b2cService;
	}

	public void setFirCtbcB2b2cService(FirCtbcB2b2cService firCtbcB2b2cService) {
		this.firCtbcB2b2cService = firCtbcB2b2cService;
	}

	public FirRptCtbcCtfService getFirRptCtbcCtfService() {
		return firRptCtbcCtfService;
	}

	public void setFirRptCtbcCtfService(FirRptCtbcCtfService firRptCtbcCtfService) {
		this.firRptCtbcCtfService = firRptCtbcCtfService;
	}

	public List<FirCtbcB2b2c> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcB2b2c> devResults) {
		this.devResults = devResults;
	}

	public FirCtbcB2b2c getFirCtbcB2b2c() {
		return firCtbcB2b2c;
	}

	public void setFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) {
		this.firCtbcB2b2c = firCtbcB2b2c;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Aps039DetailVo getAps039DetailVo() {
		return aps039DetailVo;
	}

	public void setAps039DetailVo(Aps039DetailVo aps039DetailVo) {
		this.aps039DetailVo = aps039DetailVo;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public String getOrderseq() {
		return orderseq;
	}

	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}

	/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 start*/
	public FirCtbcRstService getFirCtbcRstService() {
		return firCtbcRstService;
	}

	public void setFirCtbcRstService(FirCtbcRstService firCtbcRstService) {
		this.firCtbcRstService = firCtbcRstService;
	}
	/*mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_付款失敗產生回饋檔 end*/
}
