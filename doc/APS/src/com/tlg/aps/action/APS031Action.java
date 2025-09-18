package com.tlg.aps.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.tlg.aps.bs.firPolicyCommunicationDataService.RunCommunicationDataService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.prpins.service.FirBatchPins01Service;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/*mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業
  mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@SuppressWarnings("serial")
public class APS031Action extends BaseAction {

	private FirBatchPins01Service firBatchPins01Service;
	private RunCommunicationDataService runCommunicationDataService;
	private ConfigUtil configUtil;
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	
	private List<FirBatchPins01> devResults;
	private String paramstartdate;
	private String paramenddate;
	private String parambasedate;
	private String paramriskcode;
	private String bno;
	private String token;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	/** 按下執行按鈕，開始轉入資料 */
	public String btnExecute() throws Exception{
		try{
			Map<String,String> params = new HashMap<>();
			params.put("isvoid", "N");
			params.put("datastatusEqual", "6");
			Result result = firBatchPins01Service.findFirBatchPins01ByParams(params);
			if(result.getResObject() != null) {
				setMessage("仍有未完成的批次，請至查詢介面查詢");
				return Action.SUCCESS;
			}
			FirBatchPins01 firBatchPins01 = new FirBatchPins01();
			firBatchPins01.setBno("FIR"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			firBatchPins01.setParamstartdate(dateFormat.parse(paramstartdate));
			firBatchPins01.setParamenddate(dateFormat.parse(paramenddate));
			firBatchPins01.setParambasedate(dateFormat.parse(parambasedate));
			firBatchPins01.setParamriskcode(paramriskcode.replaceAll("[\\s]*", ""));
			firBatchPins01.setDatastatus("0");
			firBatchPins01.setDatacount(new BigDecimal(0));
			firBatchPins01.setIcreate(getUserInfo().getUserId().toUpperCase());
			firBatchPins01.setDcreate(new Date());
			firBatchPins01.setIsvoid("N");
			result = firBatchPins01Service.insertFirBatchPins01(firBatchPins01);
			setMessage("新增批次完成，待凌晨執行批次");
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DCREATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下作廢按鈕，作廢此筆資料*/
	public String btnVoidData() throws Exception{
		try{
			Map<String,String> params = new HashMap<>();
			params.put("bno", bno);
			Result result = firBatchPins01Service.findFirBatchPins01ByUK(params);
			if(result.getResObject() == null) {
				setMessage("作廢失敗，查無此批次號");
				return "input";
			}
			FirBatchPins01 firBatchPins01 = (FirBatchPins01) result.getResObject();
			firBatchPins01.setDupdate(new Date());
			firBatchPins01.setIupdate(getUserInfo().getUserId().toUpperCase());
			firBatchPins01.setIsvoid("Y");
			firBatchPins01Service.updateFirBatchPins01(firBatchPins01);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下下載按鈕，開始下載檔案*/
	public String btnDownloadFile() throws Exception {
		String oid = this.getFtsFileOid(bno);
		String url = configUtil.getString("downloadFileUrl") + bno + "/" + oid;
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
			File file = new File(configUtil.getString("tempFolder") + bno + ".xlsx");
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
		
		//下載完成後更新下載時間、下載人員
		Map<String,String> params = new HashMap<>();
		params.put("bno",bno);
		Result result = firBatchPins01Service.findFirBatchPins01ByUK(params);
		if(result.getResObject()!=null) {
			FirBatchPins01 firBatchPins01 = (FirBatchPins01)result.getResObject();
			firBatchPins01.setDdownload(new Date());
			firBatchPins01.setIdownload(getUserInfo().getUserId().toUpperCase());
			firBatchPins01Service.updateFirBatchPins01(firBatchPins01);
		}
		
		Cookie cookie = new Cookie("aps031Download",this.token);
		cookie.setPath("/");
		this.getResponse().addCookie(cookie);
		
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private String getFtsFileOid(String businessNo) {
		String fileOid = "";
		String riskCode = "0";
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
			FileListResponseVo fileListResponseVo = (FileListResponseVo) JsonUtil.getDTO(stringBuilder.toString(),
					FileListResponseVo.class, classMap);
			ArrayList<FileVo> list = fileListResponseVo.getFileList();
			if (list != null && !list.isEmpty()) {
				FileVo fileVo = list.get(0);
				fileOid = fileVo.getOid();
			}

		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileOid;
	}
	
	/** 測試介面上執行批次功能 */
//	public String btnExcuteBatch() throws Exception {
//		try {
//			Result result = runCommunicationDataService.generatedata("FIR_APS_PINS01");
//			setMessage(result.getMessage().toString());
//		} catch (SystemException se) {
//			setMessage(se.getMessage());
//		} catch (Exception e) {
//			getRequest().setAttribute("exception", e);
//			throw e;
//		}
//		return Action.SUCCESS;
//	}
	
	/** 分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
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
	
	/** 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = this.firBatchPins01Service.findFirBatchPins01ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirBatchPins01>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 連結至查詢頁面 */
	public String lnkGoQuery() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
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
	
	/** 連結至轉入頁面 */
	public String lnkGoCreate(){
		return Action.SUCCESS;	
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
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
		
		String datastatus = (String)getPageInfo().getFilter().get("datastatus");
		if(datastatus.equals("BLANK")) {
			getPageInfo().getFilter().remove("datastatus");
		}
	}

	public List<FirBatchPins01> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirBatchPins01> devResults) {
		this.devResults = devResults;
	}

	public FirBatchPins01Service getFirBatchPins01Service() {
		return firBatchPins01Service;
	}

	public void setFirBatchPins01Service(FirBatchPins01Service firBatchPins01Service) {
		this.firBatchPins01Service = firBatchPins01Service;
	}

	public String getParamstartdate() {
		return paramstartdate;
	}

	public void setParamstartdate(String paramstartdate) {
		this.paramstartdate = paramstartdate;
	}

	public String getParamenddate() {
		return paramenddate;
	}

	public void setParamenddate(String paramenddate) {
		this.paramenddate = paramenddate;
	}

	public String getParambasedate() {
		return parambasedate;
	}

	public void setParambasedate(String parambasedate) {
		this.parambasedate = parambasedate;
	}

	public String getParamriskcode() {
		return paramriskcode;
	}

	public void setParamriskcode(String paramriskcode) {
		this.paramriskcode = paramriskcode;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public RunCommunicationDataService getRunCommunicationDataService() {
		return runCommunicationDataService;
	}

	public void setRunCommunicationDataService(RunCommunicationDataService runCommunicationDataService) {
		this.runCommunicationDataService = runCommunicationDataService;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
