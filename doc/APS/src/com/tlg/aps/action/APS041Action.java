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

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps041DetailVo1;
import com.tlg.aps.vo.Aps041DetailVo2;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtUb01Service;
import com.tlg.prpins.service.FirAgtUb02Service;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
@SuppressWarnings("serial")
public class APS041Action extends BaseAction {
	private static final Logger logger = Logger.getLogger(APS041Action.class);
	private InputStream inputStream;
	private OutputStream outputStream;
	private String downloadFileName;
	private String queryType;
	private String sortBy;
	private String sortType;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtUb01Service firAgtUb01Service;
	private FirAgtUb02Service firAgtUb02Service;
	private List<FirAgtBatchMain> devResults1;
	private List<Aps041DetailVo1> devResults2;
	private List<Aps041DetailVo2> devResults3;
	private FirAgtBatchMain firAgtBatchMain;
	private FirAgtBatchGenfile firAgtBatchGenfile;
	private ConfigUtil configUtil;
	
	//pagInfo
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DCREATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			if("query1".equals(queryType)) {
				getPageInfo().setId("query1");
				mainQuery1();
				return "query1";
			}else if("query2".equals(queryType)) {
				getPageInfo().setId("query2");
				mainQuery2();
				return "query2";
			}else if("query3".equals(queryType)) {
				getPageInfo().setId("query3");
				mainQuery3();
				return "query3";
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
	public String lnkGoDtlQuery() throws Exception {
		try{
			String batchNo = firAgtBatchMain.getBatchNo();
			String batchType = firAgtBatchMain.getBatchType();
			if(StringUtils.isBlank(batchType) || StringUtils.isBlank(batchNo)) {
				setMessage("此筆資料異常");
				return Action.INPUT;
			}
			getDPageInfo().setSortBy("1");
			getDPageInfo().setSortType("DESC");
			getDPageInfo().setCurrentPage(1);
			getDPageInfo().getFilter().put("batchNo",batchNo);
			dtlQuery(batchType);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex1() throws Exception {
		try {
			if("query1".equals(getPageInfo().getId())) {
				mainQuery1();
				return "query1";
			}else if("query2".equals(getPageInfo().getId())) {
				mainQuery2();
				return "query2";
			}else if("query3".equals(getPageInfo().getId())) {
				mainQuery3();
				return "query3";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged1() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			if("query1".equals(getPageInfo().getId())) {
				mainQuery1();
				return "query1";
			}else if("query2".equals(getPageInfo().getId())) {
				mainQuery2();
				return "query2";
			}else if("query3".equals(getPageInfo().getId())) {
				mainQuery3();
				return "query3";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String txtChangePageIndex2() throws Exception {
		try {
			dtlQuery(firAgtBatchMain.getBatchType());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	public String ddlPageSizeChanged2() throws Exception {
		try {
			dPageInfo = getDPageInfo();
			dPageInfo.setCurrentPage(1);
			dtlQuery(firAgtBatchMain.getBatchType());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		resetDPageInfo();
		try {
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void mainQuery1() throws Exception {
		parameterHandler();
		Result result = firAgtBatchMainService.findAPS041ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults1 = (List<FirAgtBatchMain>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void mainQuery2() throws Exception {
		parameterHandler();
		Result result = firAgtUb01Service.findAPS041Main2ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults1 = (List<FirAgtBatchMain>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void mainQuery3() throws Exception {
		parameterHandler();
		Result result = firAgtUb02Service.findAPS041Main3ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults1 = (List<FirAgtBatchMain>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void dtlQuery(String batchType) throws Exception {
		Result result = null;
		if("01".equals(batchType)) { //要保受理檔
			result = firAgtBatchDtlService.findAPS041Dtl1ByPageInfo(getDPageInfo());
			if(result != null && result.getResObject() != null) {
				devResults2 = (List<Aps041DetailVo1>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		} else if ("31".equals(batchType)) { //保批檔
			result = firAgtBatchDtlService.findAPS041Dtl2ByPageInfo(getDPageInfo());
			if(result != null && result.getResObject() != null) {
				devResults3 = (List<Aps041DetailVo2>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String lnkSortQuery() throws Exception {
		try {
			if("query1".equals(queryType)) {
				mainQuery1();
				return "query1";
			}else if("dtlQuery".equals(queryType)) {
				String batchNo = firAgtBatchMain.getBatchNo();
				String batchType = firAgtBatchMain.getBatchType();
				getDPageInfo().getFilter().put("batchNo",batchNo);
				getDPageInfo().getFilter().put("sortBy",sortBy);
				getDPageInfo().getFilter().put("sortType",sortType);
				dtlQuery(batchType);
				return "dtlQuery";
			}else if("query2".equals(queryType)) {
				mainQuery2();
				return "query2";
			}else if("query3".equals(queryType)) {
				mainQuery3();
				return "query3";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoQuery() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				mainQuery1();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String goQuery1() throws Exception {
		try {
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().setFilter(new HashMap<>());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String goQuery2() throws Exception {
		try {
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().setFilter(new HashMap<>());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String goQuery3() throws Exception {
		try {
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().setFilter(new HashMap<>());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		if("query3".equals(queryType)) {
			//要保建檔日期
			String signStrDate = (String)getPageInfo().getFilter().get("signSDate");
			if(!StringUtil.isSpace(signStrDate)) {
				signStrDate += " 00:00:00";
				getPageInfo().getFilter().put("ssDate", signStrDate);
			}else {
				getPageInfo().getFilter().remove("ssDate");
			}
			
			signStrDate = (String)getPageInfo().getFilter().get("signEDate");
			if(!StringUtil.isSpace(signStrDate)) {
				signStrDate += " 23:59:59";
				getPageInfo().getFilter().put("seDate", signStrDate);
			}else {
				getPageInfo().getFilter().remove("seDate");
			}
			//簽單日期
			String unStrDate = (String)getPageInfo().getFilter().get("unSDate");
			if(!StringUtil.isSpace(unStrDate)) {
				unStrDate += " 00:00:00";
				getPageInfo().getFilter().put("unsDate", unStrDate);
			}else {
				getPageInfo().getFilter().remove("unsDate");
			}
			
			unStrDate = (String)getPageInfo().getFilter().get("unEDate");
			if(!StringUtil.isSpace(unStrDate)) {
				unStrDate += " 23:59:59";
				getPageInfo().getFilter().put("uneDate", unStrDate);
			}else {
				getPageInfo().getFilter().remove("uneDate");
			}
		}
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endDate");
		}
	}
	
	public String downloadMainFile() throws Exception {
		String businessNo = firAgtBatchGenfile.getBatchNo();
		String fileName = firAgtBatchGenfile.getFileName();
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
            
            this.inputStream = httpResponse.getEntity().getContent();
            File fileFolder = new File(configUtil.getString("tempFolder"));
			if(!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
            File file = new File(configUtil.getString("tempFolder") + firAgtBatchGenfile.getFileName());
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);  
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=inputStream.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                outputStream.write(bytes);
            }
            downloadFileName = firAgtBatchGenfile.getFileName();
            outputStream.flush();
            this.inputStream = new DeleteAfterDownloadFileInputStream(file);

		}catch (IOException e) {
		            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
            try {
                if(outputStream != null){
                	outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

		}
		return Action.SUCCESS;
	}
	
	public String downloadDtlFile() throws Exception {
		String businessNo = firAgtBatchGenfile.getBatchNo();
		String fileName = firAgtBatchGenfile.getFileName();
		String oid = this.getFtsFileOid(businessNo,fileName+".pdf");
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
            
            this.inputStream = httpResponse.getEntity().getContent();
            File fileFolder = new File(configUtil.getString("tempFolder"));
			if(!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
            File file = new File(configUtil.getString("tempFolder") + firAgtBatchGenfile.getFileName()+".pdf");
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);  
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=inputStream.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                outputStream.write(bytes);
            }
            downloadFileName = firAgtBatchGenfile.getFileName()+".pdf";
            outputStream.flush();
            this.inputStream = new DeleteAfterDownloadFileInputStream(file);

		}catch (IOException e) {
		            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
            try {
                if(outputStream != null){
                	outputStream.close();
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
            if(fileListResponseVo.getStatus().equals("N")) {
            	fileOid = "";
            }else {
            	ArrayList<FileVo> list = fileListResponseVo.getFileList();
            	for(FileVo fv : list) {
            		if(fv.getName().equals(fileName))
            			fileOid = fv.getOid();
            	}
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<FirAgtBatchMain> getDevResults1() {
		return devResults1;
	}

	public void setDevResults1(List<FirAgtBatchMain> devResults1) {
		this.devResults1 = devResults1;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtBatchMain getFirAgtBatchMain() {
		return firAgtBatchMain;
	}
	
	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}
	
	public FirAgtUb01Service getFirAgtUb01Service() {
		return firAgtUb01Service;
	}

	public void setFirAgtUb01Service(FirAgtUb01Service firAgtUb01Service) {
		this.firAgtUb01Service = firAgtUb01Service;
	}

	public FirAgtUb02Service getFirAgtUb02Service() {
		return firAgtUb02Service;
	}

	public void setFirAgtUb02Service(FirAgtUb02Service firAgtUb02Service) {
		this.firAgtUb02Service = firAgtUb02Service;
	}

	public void setFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) {
		this.firAgtBatchMain = firAgtBatchMain;
	}

	public List<Aps041DetailVo1> getDevResults2() {
		return devResults2;
	}

	public void setDevResults2(List<Aps041DetailVo1> devResults2) {
		this.devResults2 = devResults2;
	}

	public List<Aps041DetailVo2> getDevResults3() {
		return devResults3;
	}

	public void setDevResults3(List<Aps041DetailVo2> devResults3) {
		this.devResults3 = devResults3;
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
		this.dPageInfo.setPageSize(10);
		this.dPageInfo.setFilter(this.dFilter);
		super.getSession().put(dPageInfoName, this.dPageInfo);
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public FirAgtBatchGenfile getFirAgtBatchGenfile() {
		return firAgtBatchGenfile;
	}

	public void setFirAgtBatchGenfile(FirAgtBatchGenfile firAgtBatchGenfile) {
		this.firAgtBatchGenfile = firAgtBatchGenfile;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
}
