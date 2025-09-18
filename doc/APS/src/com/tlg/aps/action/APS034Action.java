package com.tlg.aps.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firFubonRenewalService.FirFubonGenFileService;
import com.tlg.aps.bs.firFubonRenewalService.FirFubonRenewalFileService;
import com.tlg.aps.bs.firFubonRenewalService.FubonRenewalFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps034DownloadlVo;
import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirAgtrnBatchMain;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FtsUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 
	mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  */
@SuppressWarnings("serial")
public class APS034Action extends BaseAction {
	
	private FirFubonRenewalFileService firFubonRenewalFileService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	private FirFubonGenFileService firFubonGenFileService;
	private FubonRenewalFileService fubonRenewalFileService;
	private FirBatchInfoService firBatchInfoService;
	private ConfigUtil configUtil;
	
	private List<FirAgtrnBatchMain> devResults;
	private List<Aps034FbDetailVo> dtlDevResults;
	
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	private FirAgtrnBatchMain firAgtrnBatchMain;
	private Aps034FbDetailVo fbDetailVo;
	private Aps034DownloadlVo fbDownloadVo;
	private String fileType;
	private String goBack;
	private String bno;
	private String downloadFilename;
	
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			resetDPageInfo();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS034E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecuteFbrn() throws Exception {
		try{
			Result result = firFubonRenewalFileService.runToReceiveData(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_FBRN");
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(!"Y".equals(goBack)) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("deleteFlag", "N");
			getPageInfo().getFilter().put("businessnature", "I00107");
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
	
	/** APS034E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS034E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS034E0.jsp頁面按下主檔批次號碼資料，查詢明細資料 */
	@SuppressWarnings("unchecked")
	public String lnkGoDetailQuery() throws Exception {
		String forward = "input";
		try {
			if("Y".equals(goBack)) {
				getDPageInfo().getFilter().put("batchNo",fbDetailVo.getBatchNo());
			}else {
				getDPageInfo().getFilter().put("batchNo",firAgtrnBatchMain.getBatchNo());
				getDPageInfo().setCurrentPage(1);
			}
			getDPageInfo().getFilter().put("sortBy","BATCH_SEQ");
			getDPageInfo().getFilter().put("sortType","ASC");
			Result result = dtlQuery();
			if(result.getMessage()!=null) {
				setMessage(result.getMessage().toString());
				query();
				return forward;
			}
			forward = Action.SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	/** APS034E1.jsp(明細)分頁資料中，重新輸入要顯示的頁數 */
	public String txtDtlChangePageIndex() throws Exception {
		try {
			getStatus();
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS034E1.jsp(明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlDtlPageSizeChanged() throws Exception {
		try {
			getStatus();
			dPageInfo = getDPageInfo();
			dPageInfo.setCurrentPage(1);
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS034E1.jsp(明細)分頁資料中， 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
		try {
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS034E1.jsp(明細)分頁資料中， 點選序號進入資料修改頁面*/
	public String lnkGoUpdate() throws Exception {
		try {
			if (null == fbDetailVo.getBatchNo() || null == fbDetailVo.getBatchSeq()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", fbDetailVo.getBatchNo());
				params.put("batchSeq", fbDetailVo.getBatchSeq());
				Result result = updateQuery(params);
				if(result.getMessage()!=null) {
					dtlQuery();
					return "input";
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS034U0.jsp按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			Map<String,Object> params = new HashMap<>();
			params.put("batchNo", fbDetailVo.getBatchNo());
			params.put("batchSeq", fbDetailVo.getBatchSeq());
			updateQuery(params);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws Exception {
		fubonRenewalFileService.updateFirAgtTocoreAndAgtrnBatchDtl(fbDetailVo, getUserInfo().getUserId().toUpperCase());
		setMessage("儲存成功");
	}
	
	/** APS034E0.jsp頁面按下進入，進入檔案產生、下載頁面 */
	public String lnkGoDownload() throws Exception {
		try {
			if (null == firAgtrnBatchMain.getBatchNo()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", firAgtrnBatchMain.getBatchNo());
				Result result = downloadQuery(params);
				if(result.getMessage()!=null) {
					query();
					return "input";
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnGenFile() throws Exception {
		Result result = new Result();
		String message = "";
		//取得檔案加密密碼
		Map<String,Object> params = new HashMap<>();
		params.put("prgId", "FIR_AGT_FBRN_PSWD");
		result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()!=null) {
			FirBatchInfo batchInfo = (FirBatchInfo) result.getResObject();
			String filePwd = batchInfo.getMailTo();
			
			String batchNo = fbDownloadVo.getBatchNo();
			String userId = getUserInfo().getUserId().toUpperCase();
			
			if("diffFile".equals(fileType)) {
				result = firFubonGenFileService.genDiffFile(batchNo, userId, filePwd);
				message = result.getMessage().toString();
			}
			if("rejectFile".equals(fileType)) {
				result = firFubonGenFileService.genRejectFile(batchNo, userId, filePwd);
				message = result.getMessage().toString();
			}
			if("bigPolicy".equals(fileType)) {
				result = firFubonGenFileService.genBigPolicy(batchNo, userId, filePwd);
				message = result.getMessage().toString();
			}
			if("renewListFile".equals(fileType)) {
				result = firFubonGenFileService.genRenewFile(batchNo, userId, filePwd);
				message = result.getMessage().toString();
			}
			if("fixData".equals(fileType)) {
				params = new HashMap<>();
				params.put("batchNo", batchNo);
				params.put("fixUserNull", "Y");
				result = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
				try {
					if(result.getResObject()!=null) {
						List<FirAgtrnBatchDtl> fixList = (List<FirAgtrnBatchDtl>) result.getResObject();
						Date fixDate = new Date();
						for(FirAgtrnBatchDtl firAgtrnBatchDtl : fixList) {
							firAgtrnBatchDtl.setFixUser(userId);
							firAgtrnBatchDtl.setFixDate(fixDate);
							firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(firAgtrnBatchDtl);
						}
						message = "整批鎖定成功。";
					}
				}catch (Exception e) {
					logger.error("鎖定資料失敗", e);
					message = "鎖定資料失敗。";
				}
			}
			if("debitNotice".equals(fileType)) {
				result = firFubonGenFileService.genDebitNotice(batchNo, userId);
				message = result.getMessage().toString();
			}
		}else {
			message = "未設定資料交換檔案密碼，請通知資訊窗口處理。";
		}
		
		setMessage(message);
		params = new HashMap<>();
		params.put("batchNo", fbDownloadVo.getBatchNo());
		downloadQuery(params);
		return Action.SUCCESS;
	}
	
	/** 按下下載按鈕，開始下載檔案*/
	public String btnDownloadFile() throws Exception {
		FileVo fileVo = this.getFtsFileOid(bno);
		String url = configUtil.getString("downloadFileUrl") + bno + "/" + fileVo.getOid();
		logger.info("lnkDownloadPDF url : " + url);
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);

			if (MediaType.APPLICATION_JSON.equals(contentType.getMimeType())) {
				String entityStr = EntityUtils.toString(entity, "UTF-8");
				System.out.println(entityStr);
				return Action.INPUT;
			}

			this.fileInputStream = httpResponse.getEntity().getContent();
			File fileFolder = new File(configUtil.getString("tempFolder"));
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			downloadFilename = fileVo.getName();
			File file = new File(configUtil.getString("tempFolder") + downloadFilename);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			int readLength = 0;
			while ((readLength = fileInputStream.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				fileOutputStream.write(bytes);
			}

			fileOutputStream.flush();
			this.fileInputStream = new DeleteAfterDownloadFileInputStream(file);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return Action.SUCCESS;
	}
	
	/** APS034E2檔案產生頁面，產生大保單作業按下上傳大保單按鈕，將檔案自FTS上下載後上傳至富邦SFTP
	 *  富邦作業流程，大保單會產生big5、UTF_8兩種編碼檔案，分別要在N+1月25號前上傳即N月(續保當月)20號前上傳，所以增加功能按鈕將兩個檔案上傳功能拆開。
	 */
	public String btnUploadBigPolicyFile() throws Exception {
		try {
			Result result = firFubonGenFileService.uploadBigPolicyFile(bno,fbDownloadVo.getBatchNo(),fileType, getUserInfo().getUserId().toUpperCase());
			Map<String, Object> params = new HashMap<>();
			params.put("batchNo", fbDownloadVo.getBatchNo());
			downloadQuery(params);
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	//取得上傳FTS的檔案清單OID
	private FileVo getFtsFileOid(String businessNo) {
		String riskCode = "F";
		String httpURL = configUtil.getString("ftsUrl");
		
		FtsUtil ftsUtil = new FtsUtil(httpURL);
		FileListResponseVo fileListResponseVo = ftsUtil.getFtsFileList(riskCode, businessNo);
        ArrayList<FileVo> list = fileListResponseVo.getFileList();
        FileVo fileVo = new FileVo();
		if (list != null && !list.isEmpty()) {
			fileVo = list.get(0);
		}
		return fileVo;
	}
	
	public Result updateQuery(Map<String,Object> params) throws Exception {
		Result result = firAgtrnBatchDtlService.findFbrnInsuredData(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			fbDetailVo = (Aps034FbDetailVo) result.getResObject();
		}
		params.put("sortBy", "INSUREDFLAG");
		params.put("sortType", "DESC");
		result = firAgtTocoreInsuredService.findFirAgtTocoreInsuredByParams(params);
		if(result.getResObject()!=null) {
			List<FirAgtTocoreInsured> insuredList = (List<FirAgtTocoreInsured>) result.getResObject();
			for(int i=0;i<insuredList.size();i++) {
				FirAgtTocoreInsured insured = insuredList.get(i);
				insured.setStrBirthday(DateUtils.getTaiwanDateString(insured.getBirthday()));
				insuredList.set(i, insured);
			}
			fbDetailVo.setInsuredList(insuredList);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = firAgtrnBatchMainService.findFirAgtrnBatchMainByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirAgtrnBatchMain>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	//明細資料查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		Result result = new Result();	
		result = firAgtrnBatchDtlService.findForFbrnDetail(getDPageInfo());
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps034FbDetailVo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	public Result downloadQuery(Map<String, Object> params) throws Exception {
		Result result = firAgtrnBatchMainService.findFbrnDownloadData(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			fbDownloadVo = (Aps034DownloadlVo) result.getResObject();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}
	
	// 取下拉選單的值，若為空白則移除filter
	private void getStatus() {
		if (getPageInfo().getFilter().get("fileStatus")!=null && "BLANK".equals(getPageInfo().getFilter().get("fileStatus"))) {
			getPageInfo().getFilter().remove("fileStatus");
		}
		if (getPageInfo().getFilter().get("transStatus")!=null && "BLANK".equals(getPageInfo().getFilter().get("transStatus"))) {
			getPageInfo().getFilter().remove("transStatus");
		}
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public FirFubonRenewalFileService getFirFubonRenewalFileService() {
		return firFubonRenewalFileService;
	}

	public void setFirFubonRenewalFileService(FirFubonRenewalFileService firFubonRenewalFileService) {
		this.firFubonRenewalFileService = firFubonRenewalFileService;
	}

	public FirAgtrnBatchMainService getFirAgtrnBatchMainService() {
		return firAgtrnBatchMainService;
	}

	public void setFirAgtrnBatchMainService(FirAgtrnBatchMainService firAgtrnBatchMainService) {
		this.firAgtrnBatchMainService = firAgtrnBatchMainService;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirAgtTocoreInsuredService getFirAgtTocoreInsuredService() {
		return firAgtTocoreInsuredService;
	}

	public void setFirAgtTocoreInsuredService(FirAgtTocoreInsuredService firAgtTocoreInsuredService) {
		this.firAgtTocoreInsuredService = firAgtTocoreInsuredService;
	}

	public FirFubonGenFileService getFirFubonGenFileService() {
		return firFubonGenFileService;
	}

	public void setFirFubonGenFileService(FirFubonGenFileService firFubonGenFileService) {
		this.firFubonGenFileService = firFubonGenFileService;
	}

	public FubonRenewalFileService getFubonRenewalFileService() {
		return fubonRenewalFileService;
	}

	public void setFubonRenewalFileService(FubonRenewalFileService fubonRenewalFileService) {
		this.fubonRenewalFileService = fubonRenewalFileService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public List<FirAgtrnBatchMain> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirAgtrnBatchMain> devResults) {
		this.devResults = devResults;
	}
	
	public List<Aps034FbDetailVo> getDtlDevResults() {
		return dtlDevResults;
	}

	public void setDtlDevResults(List<Aps034FbDetailVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}

	public FirAgtrnBatchMain getFirAgtrnBatchMain() {
		return firAgtrnBatchMain;
	}

	public void setFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) {
		this.firAgtrnBatchMain = firAgtrnBatchMain;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public Aps034FbDetailVo getFbDetailVo() {
		return fbDetailVo;
	}

	public void setFbDetailVo(Aps034FbDetailVo fbDetailVo) {
		this.fbDetailVo = fbDetailVo;
	}

	public Aps034DownloadlVo getFbDownloadVo() {
		return fbDownloadVo;
	}

	public void setFbDownloadVo(Aps034DownloadlVo fbDownloadVo) {
		this.fbDownloadVo = fbDownloadVo;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getGoBack() {
		return goBack;
	}

	public void setGoBack(String goBack) {
		this.goBack = goBack;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getDownloadFilename() {
		return downloadFilename;
	}

	public void setDownloadFilename(String downloadFilename) {
		this.downloadFilename = downloadFilename;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.dPageInfo=(PageInfo)session.get(this.dPageInfoName);
		super.setSession(session);
	}
	public PageInfo getDPageInfo() {
		if(super.getSession().containsKey(dPageInfoName)){
			this.dPageInfo = (PageInfo)super.getSession().get(dPageInfoName);
		}
		return dPageInfo;
	}
	public void setDPageInfo(PageInfo dPageInfo) {
		this.dPageInfo = dPageInfo;
		super.getSession().put(dPageInfoName, this.dPageInfo);
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getDFilter() {
		if (dPageInfo.getFilter() == null) {             //沒有值 則設定一個新的MAP  並且傳進session裡
			this.dFilter = new HashMap<>();//此Filter如為空 則也要new 一個新的MAP進去 否則會nullpointException
			this.dPageInfo.setFilter(this.dFilter);
			super.getSession().put(dPageInfoName, this.dPageInfo);
		} else {
			this.dFilter = this.dPageInfo.getFilter();   //有值 則沿用此dPageInfo.getFilter
		}
		return dFilter;
	}
	public void setDFilter(Map<String, String> dFilter) {
		this.dFilter = dFilter;
		this.dPageInfo.setFilter(this.dFilter);         //將dFilter設定進Filter 這樣getFilter時就會取得dFilter
	}
	public String getDPageInfoName() {
		return dPageInfoName;
	}
	public void setDPageInfoName(String dPageInfoName) {
		this.dPageInfoName = dPageInfoName;
	}
	/**重設DPageInfo*/
	private void resetDPageInfo() {
		this.dPageInfo = new PageInfo();
		this.dFilter = new HashMap<>();
		this.dFilter.put("sortType", "ASC");
		this.dPageInfo.setPageSize(10);
		this.dPageInfo.setFilter(this.dFilter);
		super.getSession().put(dPageInfoName, this.dPageInfo);
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public OutputStream getFileOutputStream() {
		return fileOutputStream;
	}

	public void setFileOutputStream(OutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}
}
