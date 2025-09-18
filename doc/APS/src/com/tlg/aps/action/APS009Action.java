package com.tlg.aps.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firPanhsinFeedbackFile.FirProcessPanhsinFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps009Detail01Vo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchGenfileService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.JsonUtil;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS009Action extends BaseAction {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	   mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	//Service
	private FirProcessPanhsinFileService firProcessPanhsinFileService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtBatchGenfileService firAgtBatchGenfileService;
	private FirBatchInfoService firBatchInfoService;
	//Entity
	private FirAgtBatchMain firAgtBatchMain;
	private FirAgtBatchGenfile firAgtBatchGenfile;
	//pagInfo
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	private PageInfo gPageInfo;
	private Map<String, String> gFilter;
	private String gPageInfoName = this.getClass().getSimpleName() + "gPageInfo";
	
	private List<FirAgtBatchMain> devResults;
	private List<Aps009Detail01Vo> dtlDevResults;
	private List<FirAgtBatchGenfile> gefDevResults;	
	private String type;
	private ConfigUtil configUtil;
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;

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
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	/** 取下拉選單的值，若為空白則移除filter */
	private void getStatus() {
		if (getPageInfo().getFilter().get("batchType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("batchType"))) {
			getPageInfo().getFilter().remove("batchType");
		}
		if (getPageInfo().getFilter().get("fileStatus")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("fileStatus"))) {
			getPageInfo().getFilter().remove("fileStatus");
		}
	}
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		resetDPageInfo();
		resetGPageInfo();
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
	
	/** APS009E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(type == null) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("businessnature", "I99065");
			/** mantis：FIR0502，處理人員：CC009，需求單編號：FIR0502_板信資料交換處理作業-排除BOP03、BOP04執行結果 start */
			getPageInfo().getFilter().put("batchTypeFlag", "Y");
			getPageInfo().getFilter().put("oldBackFileFlag", "Y");
			/** mantis：FIR0502，處理人員：CC009，需求單編號：FIR0502_板信資料交換處理作業-排除BOP03、BOP04執行結果 end */
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
		Result result = firAgtBatchMainService.findFirAgtBatchMainByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirAgtBatchMain>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}

	/*mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start */
	/** APS004E0.jsp頁面按下手動執行鍵,開始執行 **/
	public String btnExecuteBop01() throws Exception {
		try{
			firProcessPanhsinFileService.RunToGenerateFiles(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_BOP_01");
			setMessage("執行完成");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS004E0.jsp頁面按下手動執行鍵,開始執行 **/
	public String btnExecuteBop02() throws Exception {
		try{
			firProcessPanhsinFileService.RunToGenerateFiles(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_BOP_02");
			setMessage("執行完成");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/*mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 end */
	
	/** APS009E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS009E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	/** APS009E0.jsp頁面按下主檔批次號碼資料，查詢明細資料 */
	@SuppressWarnings("unchecked")
	public String lnkGoDetailQuery() throws Exception {
		String forward = "input";
		try {
			if (null == firAgtBatchMain.getBatchNo()||null == firAgtBatchMain.getBatchType()) {
				setMessage("請重新操作");
				return forward;
			} 
			getDPageInfo().getFilter().put("batchNo",firAgtBatchMain.getBatchNo());
			getDPageInfo().getFilter().put("batchType",firAgtBatchMain.getBatchType());
			getDPageInfo().getFilter().put("sortBy","DTL.RISKCODE,PM.STARTDATE");
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
	
	//明細資料查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		Result result = new Result();
		if(getDPageInfo().getFilter().get("batchType").equals("01")) {//批次類型 =01 要保書受理檔
			result = firAgtBatchDtlService.findAPS009Detail01ByPageInfo(getDPageInfo());
		}else if(getDPageInfo().getFilter().get("batchType").equals("03")) {//批次類型 =03保單檔
			result = firAgtBatchDtlService.findAPS009Detail03ByPageInfo(getDPageInfo());
		}else {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps009Detail01Vo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	/** APS009E1.jsp(明細)分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** APS009E1.jsp(明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	/** APS009E0.jsp頁面主檔資料按下執行，產生檔案 */
	public String btnGenerateFile() throws Exception {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("batchNo", firAgtBatchMain.getBatchNo());
			//mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業
			Result result = firProcessPanhsinFileService.generateFile(params,getUserInfo().getUserId().toUpperCase());
			setMessage(result.getMessage().toString());
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return SUCCESS;
	}

	/** APS009E2.jsp檔案清單資料中，按下檔案名稱，下載檔案 */
	public String downloadBopFile() throws Exception {
		String businessNo = firAgtBatchGenfile.getBatchNo()+firAgtBatchGenfile.getFileName();
		String oid = this.getFtsFileOid(businessNo);
		String url = configUtil.getString("downloadFileUrl") + businessNo + "/" + oid;
		logger.info("lnkDownloadPDF url : " + url);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);

            if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
            	String entityStr = EntityUtils.toString(entity, "UTF-8");
            	System.out.println(entityStr);
            	return Action.INPUT;
            }
            
            this.fileInputStream = httpResponse.getEntity().getContent();
            File fileFolder = new File(configUtil.getString("tempFolder"));
			if(!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
            File file = new File(configUtil.getString("tempFolder") + firAgtBatchGenfile.getFileName());
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);  
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=fileInputStream.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                fileOutputStream.write(bytes);
            }
            
            fileOutputStream.flush();
            this.fileInputStream = new DeleteAfterDownloadFileInputStream(file);

		}catch (IOException e) {
		            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
            try {
                if(fileOutputStream != null){
                	fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

		}
		return Action.SUCCESS;
	}
	
@SuppressWarnings("unchecked")
private String getFtsFileOid(String businessNo) {
		
		String fileOid = "";
		String riskCode = "F";
		String httpURL = configUtil.getString("downloadListServiceUrl");
		StringBuilder stringBuilder = new StringBuilder();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        	FileUploadRequestVo fileUploadRequestVo = new FileUploadRequestVo();
        	fileUploadRequestVo.setRiskCode(riskCode);
        	fileUploadRequestVo.setBusinessNo(businessNo);
              
        	HttpPost httpPost = new HttpPost(httpURL);  
        	StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(fileUploadRequestVo), "UTF-8");
        	stringEntity.setContentEncoding("UTF-8");
        	httpPost.setEntity(stringEntity);
        	httpPost.setHeader("Accept", "application/json");
        	httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();
            // get the response content
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            
            String strReadLine = bufferedReader.readLine();
 
            // iterate to get the data and append in StringBuilder
            while (strReadLine != null) {
                stringBuilder.append(strReadLine);
                strReadLine = bufferedReader.readLine();
                if (strReadLine != null) {
                    stringBuilder.append("\n");
                }
            }
            
            Map classMap = new HashMap();
            classMap.put("fileList", FileVo.class);
            FileListResponseVo fileListResponseVo = (FileListResponseVo)JsonUtil.getDTO(stringBuilder.toString(), FileListResponseVo.class, classMap);
            ArrayList<FileVo> list = fileListResponseVo.getFileList();
            if(list != null && !list.isEmpty()) {
            	FileVo fileVo = list.get(0);
            	fileOid = fileVo.getOid();
            }
            
        }
        catch (UnsupportedEncodingException usee) {
            usee.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileOid;
	}
	
	/** APS009E0.主檔資料中，按下主檔檔名，查詢檔案清單 */
	@SuppressWarnings("unchecked")
	public String lnkGoFilelistQuery() throws Exception {
		String forward = "input";
		try {
			if (null == firAgtBatchMain.getBatchNo()) {
				setMessage("請重新操作");
				return forward;
			} 
			getGPageInfo().getFilter().put("batchNo",firAgtBatchMain.getBatchNo());
			getGPageInfo().getFilter().put("sortBy","DCREATE");
			getGPageInfo().getFilter().put("sortType","DESC");
			fileListQuery();
			forward = Action.SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	//檔案清單查詢
	@SuppressWarnings("unchecked")
	private Result fileListQuery() throws Exception {
		Result result = firAgtBatchGenfileService.findFirAgtBatchGenfileByPageInfo(getGPageInfo());
		if (null != result.getResObject()) {
			gefDevResults = (List<FirAgtBatchGenfile>) result.getResObject();
		}else {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}
		return result;
	}

	/** (檔案清單)分頁資料中，重新輸入要顯示的頁數 */
	public String txtGefChangePageIndex() throws Exception {
		try {
			getStatus();
			fileListQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS009E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlGefPageSizeChanged() throws Exception {
		try {
			getStatus();
			gPageInfo = getGPageInfo();
			gPageInfo.setCurrentPage(1);
			fileListQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/*mantis：FIR0312，處理人員：BJ016，需求單編號：FIR0312_APS 呼叫核心外銀批次匯入 start */
	public String btnCallBankToCoreService() throws Exception {
		try {
			//取得要呼叫的URL----START
			String url = "";
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("prgId", "FIR_BANKTOCORE");
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result != null && result.getResObject() != null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
				url = StringUtil.nullToSpace(firBatchInfo.getMailTo());
			}
			//取得要呼叫的URL----END
			
			//呼叫URL----START
			if(url.length() <= 0) {
				setMessage("無法取得核心外銀批次匯入URL");
				return "SUCCESS";
			}

			URL U = new URL(url);
			URLConnection connection = U.openConnection(); 
			connection.connect(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GB2312")); 
			String line; 
			String strResult = "";
			while ((line = in.readLine())!= null) { 
				strResult += line; 
			} 
			in.close();
			//呼叫URL----END
			setMessage("批次匯入執行中，請稍後再查詢匯入結果。");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/*mantis：FIR0312，處理人員：BJ016，需求單編號：FIR0312_APS 呼叫核心外銀批次匯入 end */
	
	
	/*mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start */
	public FirProcessPanhsinFileService getFirProcessPanhsinFileService() {
		return firProcessPanhsinFileService;
	}
	public void setFirProcessPanhsinFileService(FirProcessPanhsinFileService firProcessPanhsinFileService) {
		this.firProcessPanhsinFileService = firProcessPanhsinFileService;
	}
	/*mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 end */
	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}
	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}
	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}
	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}
	public FirAgtBatchGenfileService getFirAgtBatchGenfileService() {
		return firAgtBatchGenfileService;
	}
	public void setFirAgtBatchGenfileService(FirAgtBatchGenfileService firAgtBatchGenfileService) {
		this.firAgtBatchGenfileService = firAgtBatchGenfileService;
	}
	public List<FirAgtBatchMain> getDevResults() {
		return devResults;
	}
	public void setDevResults(List<FirAgtBatchMain> devResults) {
		this.devResults = devResults;
	}
	public List<Aps009Detail01Vo> getDtlDevResults() {
		return dtlDevResults;
	}
	public void setDtlDevResults(List<Aps009Detail01Vo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}
	public List<FirAgtBatchGenfile> getGefDevResults() {
		return gefDevResults;
	}
	public void setGefDevResults(List<FirAgtBatchGenfile> gefDevResults) {
		this.gefDevResults = gefDevResults;
	}
	public FirAgtBatchMain getFirAgtBatchMain() {
		return firAgtBatchMain;
	}
	public void setFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) {
		this.firAgtBatchMain = firAgtBatchMain;
	}
	public FirAgtBatchGenfile getFirAgtBatchGenfile() {
		return firAgtBatchGenfile;
	}
	public void setFirAgtBatchGenfile(FirAgtBatchGenfile firAgtBatchGenfile) {
		this.firAgtBatchGenfile = firAgtBatchGenfile;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.dPageInfo=(PageInfo)session.get(this.dPageInfoName);
		this.gPageInfo=(PageInfo)session.get(this.gPageInfoName);
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
	public PageInfo getGPageInfo() {
		if(super.getSession().containsKey(gPageInfoName)){
			this.gPageInfo = (PageInfo)super.getSession().get(gPageInfoName);
		}
		return gPageInfo;
	}
	public void setGPageInfo(PageInfo gPageInfo) {
		this.gPageInfo = gPageInfo;
		super.getSession().put(gPageInfoName, this.gPageInfo);
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getGFilter() {
		if (gPageInfo.getFilter() == null) {     
			this.gFilter = new HashMap<>();
			this.gPageInfo.setFilter(this.gFilter);
			super.getSession().put(gPageInfoName, this.gPageInfo);
		} else {
			this.gFilter = this.gPageInfo.getFilter(); 
		}
		return gFilter;
	}
	public void setGFilter(Map<String, String> gFilter) {
		this.gFilter = gFilter;
		this.gPageInfo.setFilter(this.gFilter);
	}
	public String getGPageInfoName() {
		return gPageInfoName;
	}
	public void setGPageInfoName(String gPageInfoName) {
		this.gPageInfoName = gPageInfoName;
	}
	/**重設GPageInfo*/
	private void resetGPageInfo() {
		this.gPageInfo = new PageInfo();
		this.gFilter = new HashMap<>();
		this.gFilter.put("sortType", "ASC");
		this.gPageInfo.setPageSize(10);
		this.gPageInfo.setFilter(this.gFilter);
		super.getSession().put(gPageInfoName, this.gPageInfo);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
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

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}
}
