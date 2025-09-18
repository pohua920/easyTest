package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.mobclaimService.MobSubmitAndPrintService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.aps.vo.RptFir00103ResultVo;
import com.tlg.aps.vo.rpt.Clm00100RequestVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FetclaimmainService;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.util.AppContext;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.encoders.AesGcm256;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@SuppressWarnings("serial")
public class APS046Action extends BaseAction {
	
	private List<Aps046ResultVo> devResults;
	private TmpfetclaimmainService tmpfetclaimmainService;
	private FetclaimmainService fetclaimmainService;
	private MobSubmitAndPrintService mobSubmitAndPrintService;
	private ConfigUtil configUtil;
	private Integer sumWda35;
	private String wda00;
	private String wda02;
	private String wda03;
	
	private InputStream inputStream;
	private String filename;
	private String token;
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			sumWda35();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS046E0.jsp頁面按下查詢鍵,開始查詢主檔資料 */
	public String btnQuery() throws Exception {
		try{
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS046U0.jsp頁面按下提交按鈕,將TMP檔資料寫入正式資料檔 */
	public String btnSubmit() throws Exception {
		try {
			Result result = mobSubmitAndPrintService.submitClaimData(getUserInfo().getUserId().toUpperCase(), wda00);
			if(null == sumWda35) {
				sumWda35();
			}
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS046P0.jsp頁面按下列印計算書按鈕,產生理賠計算書PDF檔案 */
	public String btnPrint() throws Exception {
		this.configUtil = (ConfigUtil)AppContext.getBean("configUtil");
		
		if(StringUtil.isSpace(wda00)) {
			setMessage("未輸入資料年月，無法列印計算書!");
			return "input";
		}
		
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("wda00", wda00);
		if(!StringUtil.isSpace(wda02) && !StringUtil.isSpace(wda03)) {
			map.put("wda02", wda02);
			map.put("wda03", wda03);
		}
		String jsonStr = JsonUtil.getJSONString(map);
		
		//將傳入參數加密後再以post方式取得報表
		String key = configUtil.getString("claimEncode1");
		String iv = configUtil.getString("claimEncode2");
		String enStr = AesGcm256.encrypt(jsonStr, key, iv);
		
		String tempFolder = configUtil.getString("tempFolder") + File.separator;
		File fileFolder = new File(tempFolder);
		if(!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		
		filename = wda00 +"_CLMCALCULATE.pdf";
		String filepath = configUtil.getString("tempFolder") + filename;
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String rcvurl = configUtil.getString("rptUrl") +"webService/pdf/clm00100/";
			
			Clm00100RequestVo vo  = new Clm00100RequestVo();
			vo.setEncodeStr(enStr);
			
			HttpPost httpPost = new HttpPost(rcvurl);  
			StringEntity stringEntity = new StringEntity(JsonUtil.getJSONString(vo), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
			httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			System.out.println( "httpResponse.getStatusLine() = " + httpResponse.getStatusLine());
			HttpEntity entity = httpResponse.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);
			
			if(MediaType.APPLICATION_JSON.equals(contentType.getMimeType())){
				String returnJsonStr = EntityUtils.toString(entity, "UTF-8");
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				RptFir00103ResultVo rptResultBaseVo = objectMapper.readValue(returnJsonStr, RptFir00103ResultVo.class);
				
				byte[] byteArray = Base64.decodeBase64(rptResultBaseVo.getRptStr());
				FileUtils.writeByteArrayToFile(new File(filepath), byteArray);
			}
			this.inputStream = new DeleteAfterDownloadFileInputStream(new File(filepath));
		}catch (Exception e) {
			e.printStackTrace();
			setMessage("列印計算書失敗，請通知資訊人員!!");
		}
		//連動cookie，讓頁面上的blockUI解除block狀態----START
		Cookie cookie = new Cookie("aps046Print",this.token);
		cookie.setPath("/");
		this.getResponse().addCookie(cookie);
		//連動cookie，讓頁面上的blockUI解除block狀態----END
		
		return Action.SUCCESS;
	}
	
	/** APS046P0.jsp頁面按下列印清單按鈕,產生理賠清單EXCEL檔案 */
	public String btnGenListFile() throws Exception {
		Map<String,String> params = new HashMap<>();
		if(!StringUtil.isSpace(wda00)) {
			params.put("wda00",wda00);
			params.put("wda61","4");
			Result result = mobSubmitAndPrintService.genClaimListFile(params);
			if(null != result.getResObject()) {
				File file = (File)result.getResObject();
				filename = file.getName();
				this.inputStream = new DeleteAfterDownloadFileInputStream(file);
				//連動cookie，讓頁面上的blockUI解除block狀態----START
	            Cookie cookie = new Cookie("aps046GenFile",this.token);
	            cookie.setPath("/");
	            this.getResponse().addCookie(cookie);
	            //連動cookie，讓頁面上的blockUI解除block狀態----END
			}else {
				setMessage(result.getMessage().toString());
				return "input";
			}
		}
		return Action.SUCCESS;
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
	
	/**連結至轉檔頁面*/
	public String lnkGoUpload() throws Exception {
		try {
			if(sumWda35 == null) {
				sumWda35();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/**連結至列印頁面*/
	public String lnkGoPrint() throws Exception {
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
	private void query() throws Exception {
		parameterHandler();
		getPageInfo().getFilter().put("sortBy", "MAIN.OID");
		getPageInfo().getFilter().put("sortType", "ASC");
		getPageInfo().getFilter().put("wda61", "4");
		if("N".equals(getPageInfo().getFilter().get("isSubmit"))) {
			Result result = tmpfetclaimmainService.findForMainDataByPageInfo(getPageInfo());
			if (null != result.getResObject()) {
				devResults = (List<Aps046ResultVo>) result.getResObject();
			} else {
				setMessage(result.getMessage().toString());
			}
		}else if("Y".equals(getPageInfo().getFilter().get("isSubmit"))){
			Result result = fetclaimmainService.findForMainDataByPageInfo(getPageInfo());
			if (null != result.getResObject()) {
				devResults = (List<Aps046ResultVo>) result.getResObject();
			} else {
				setMessage(result.getMessage().toString());
			}
		}
	}
	
	//計算TMP理賠總金額，取WDA61理賠狀態為4的賠款總計相加，並顯示TMP檔資料日期
	private void sumWda35() throws SystemException, Exception {
		Result result = tmpfetclaimmainService.sumWda35();
		if(null == result.getResObject()) {
			sumWda35 = null;
			wda00 = "";
		}else {
			Aps046ResultVo resultVo = (Aps046ResultVo) result.getResObject();
			sumWda35 = resultVo.getCount();
			wda00 = resultVo.getWda00();
		}
	}
	
	/** APS044E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS044E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS044E0.jsp，(主檔)查詢結果點選上下三角型排序 */
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
	
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String wde06 = (String)getPageInfo().getFilter().get("wde06");
		if(StringUtil.isSpace(wde06)) {
			getPageInfo().getFilter().remove("wde06Select");
			getPageInfo().getFilter().remove("wde06Else");
		}else if("else".equals(wde06)){
			getPageInfo().getFilter().remove("wde06Select");
			getPageInfo().getFilter().put("wde06Else","Y");
		}else {
			getPageInfo().getFilter().put("wde06Select",wde06);
			getPageInfo().getFilter().remove("wde06Else");
		}
	}
	
	public List<Aps046ResultVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Aps046ResultVo> devResults) {
		this.devResults = devResults;
	}

	public TmpfetclaimmainService getTmpfetclaimmainService() {
		return tmpfetclaimmainService;
	}

	public void setTmpfetclaimmainService(TmpfetclaimmainService tmpfetclaimmainService) {
		this.tmpfetclaimmainService = tmpfetclaimmainService;
	}

	public FetclaimmainService getFetclaimmainService() {
		return fetclaimmainService;
	}

	public void setFetclaimmainService(FetclaimmainService fetclaimmainService) {
		this.fetclaimmainService = fetclaimmainService;
	}

	public MobSubmitAndPrintService getMobSubmitAndPrintService() {
		return mobSubmitAndPrintService;
	}

	public void setMobSubmitAndPrintService(MobSubmitAndPrintService mobSubmitAndPrintService) {
		this.mobSubmitAndPrintService = mobSubmitAndPrintService;
	}

	public Integer getSumWda35() {
		return sumWda35;
	}

	public void setSumWda35(Integer sumWda35) {
		this.sumWda35 = sumWda35;
	}
	
	public InputStream getFileInputStream() {
		return inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWda00() {
		return wda00;
	}

	public void setWda00(String wda00) {
		this.wda00 = wda00;
	}

	public String getWda02() {
		return wda02;
	}

	public void setWda02(String wda02) {
		this.wda02 = wda02;
	}

	public String getWda03() {
		return wda03;
	}

	public void setWda03(String wda03) {
		this.wda03 = wda03;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
