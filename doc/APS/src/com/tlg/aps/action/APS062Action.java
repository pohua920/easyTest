package com.tlg.aps.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps038DetailVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.JsonUtil;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**
 * mantis：FIR0681，處理人員：DP0714，住火-APS元大回饋檔-排程查詢作業
 */
@SuppressWarnings("serial")
public class APS062Action extends BaseAction {
	//Service
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	//Entity
	private FirAgtBatchMain firAgtBatchMain;
	private FirAgtBatchGenfile firAgtBatchGenfile;
	//pagInfo
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	private List<FirAgtBatchMain> devResults;
	private List<Aps038DetailVo> dtlDevResults;
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
		
		getPageInfo().getFilter().put("businessnature", "I00006");
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
	@SuppressWarnings("unchecked")
	private void getStatus() {
		if (getPageInfo().getFilter().get("batchType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("batchType"))) {
			getPageInfo().getFilter().remove("batchType");
			getPageInfo().getFilter().put("batchTypeFlag", "Y");
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
	
	/** 按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(type == null) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("businessnature", "I00006");
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

	/** (主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** ，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	/** 頁面按下主檔批次號碼資料，查詢明細資料 */
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
		if(getDPageInfo().getFilter().get("batchType").equals("03")) {//批次類型 =03保單檔
			result = firAgtBatchDtlService.findAPS038Detail03ByPageInfo(getDPageInfo());
		}else {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps038DetailVo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	/** (明細)分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** (明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	/** 檔案清單資料中，按下檔案名稱，下載檔案 */
	public String downloadBopFile() throws Exception {
		String businessNo = firAgtBatchGenfile.getBatchNo();
		String fileName = firAgtBatchGenfile.getFileName();
		//String businessNo = firAgtBatchGenfile.getBatchNo()+firAgtBatchGenfile.getFileName();
		String oid = this.getFtsFileOid(businessNo,fileName);
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
            File file = new File(configUtil.getString("tempFolder") + firAgtBatchGenfile.getFileName() + ".zip");
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getFtsFileOid(String businessNo, String fileName) {
		
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
            for(FileVo fv : list) {
            	if(fv.getName().equals(fileName + ".zip"))
            		fileOid = fv.getOid();
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
	public List<FirAgtBatchMain> getDevResults() {
		return devResults;
	}
	public void setDevResults(List<FirAgtBatchMain> devResults) {
		this.devResults = devResults;
	}
	public List<Aps038DetailVo> getDtlDevResults() {
		return dtlDevResults;
	}
	public void setDtlDevResults(List<Aps038DetailVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
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
}
