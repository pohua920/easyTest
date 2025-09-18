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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import com.tlg.aps.bs.cibPolicyDataImportService.CibPolicyDataImportService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps002DetailVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileUploadRequestVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.prpins.entity.FirRptCtbcCtf;
import com.tlg.prpins.entity.Prpduser;
import com.tlg.prpins.service.FirCtbcBatchDtlService;
import com.tlg.prpins.service.FirCtbcBatchMainService;
import com.tlg.prpins.service.FirCtbcStlService;
import com.tlg.prpins.service.FirRptCtbcCtfService;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.RptUtil;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS002Action extends BaseAction {
	private ConfigUtil configUtil;
	// PageInfoName、PageInfo及Filter
	private String pageInfoName = this.getClass().getSimpleName() + "PageInfo";
	private PageInfo pageInfo;
	private Map<String, String> filter;
	// scope
	private Map<String, Object> session;
	// 下拉
	private Map<String, String> fileStatusMap = new LinkedHashMap<String, String>();// 類別下拉
	private Map<String, String> orderSeqStatusMap = new LinkedHashMap<String, String>();// 受理編號狀態下拉
	private Map<String, String> fkStatusMap = new LinkedHashMap<String, String>();// 下單狀態下拉
	private Map<String, String> sendTypeMap = new LinkedHashMap<String, String>();// 送件類型
	// 查詢結果
	private List<FirCtbcBatchMain> devResults = new ArrayList<FirCtbcBatchMain>();
	
	//mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	private List<Aps002DetailVo> devResults2 = new ArrayList<Aps002DetailVo>();
	//Service
	private FirCtbcBatchMainService  firCtbcBatchMainService;
	private FirCtbcBatchDtlService  firCtbcBatchDtlService;
	private FirRptCtbcCtfService firRptCtbcCtfService;
	
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	 *調整角色權限控管功能，後USER決定取消此功能
	private FirApsCtbcHandlerService firApsCtbcHandlerService;*/
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start*/
	private FirCtbcStlService  firCtbcStlService;
	private PrpduserService  prpduserService;
	private CibPolicyDataImportService cibPolicyDataImportService;
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/
	
	private Aps002DetailVo aps002DetailVo;
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start*/
	//private boolean roleF0201;
	private String url;
	private String goBack;
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		fileStatusMap.put("", "");
		fileStatusMap.put("S", "正常");
		fileStatusMap.put("L", "缺檔");
		fileStatusMap.put("E", "ZIP檔案異常");
		fileStatusMap.put("A", "新增錯誤");
		fileStatusMap.put("Z", "檔案無資料");
		
		orderSeqStatusMap.put("", "");
		orderSeqStatusMap.put("0", "未處理");
		orderSeqStatusMap.put("1", "資料驗證失敗");
		orderSeqStatusMap.put("2", "寫入中信下單檔成功");
		orderSeqStatusMap.put("3", "轉核心暫存檔成功");
		orderSeqStatusMap.put("4", "轉核心暫存檔失敗");
		orderSeqStatusMap.put("5", "轉核心要保檔成功");
		orderSeqStatusMap.put("6", "轉核心要保檔失敗");
		orderSeqStatusMap.put("A", "出單完成產生投保證明");
		orderSeqStatusMap.put("B", "產生回饋檔成功");
		orderSeqStatusMap.put("C", "產生回饋檔失敗");
		orderSeqStatusMap.put("D", "中信暫存件不處理");
		orderSeqStatusMap.put("E", "中信取消件不處理");
		orderSeqStatusMap.put("F", "未簽署件不處理");
		orderSeqStatusMap.put("G", "內部程式異常");
		orderSeqStatusMap.put("H", "產生投保證明失敗-SP");
		orderSeqStatusMap.put("I", "產生投保證明失敗-PDF");
		orderSeqStatusMap.put("J", "續保叫單保單號回寫完成");
		
		fkStatusMap.put("", "");
		fkStatusMap.put("01", "下單完成");
		fkStatusMap.put("02", "暫存");
		fkStatusMap.put("09", "取消");
		//mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱
		fkStatusMap.put("XX", "中國信託產險檢核異常");
		
		sendTypeMap.put("", "");
		sendTypeMap.put("01", "正本送件");
		sendTypeMap.put("02", "傳真送件");
		sendTypeMap.put("03", "續保叫單");

	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		strDate = rocToAd(strDate, "/");
		if(strDate != null && strDate.length() > 0) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		strDate = rocToAd(strDate, "/");
		if(strDate != null && strDate.length() > 0) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "BATCH_NO,BATCH_SEQ");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().getFilter().put("queryType", "1");
			query("1");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery2() throws Exception {
		try{
			formLoad("execute");
			//mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
			//點選上一頁需回到上次查詢的頁面，重新查詢才需從第一頁開始
			if(!"Y".equals(goBack)) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("sortBy", "DTL.BATCH_NO,DTL.BATCH_SEQ,DTL.FK_ORDER_SEQ");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().getFilter().put("queryType", "2");
			query("2");
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


	/** CMN130E0.jsp頁面按下清除鍵,清除所有 pageInfo的資料 */
	public String btnQueryCancel() throws Exception {
		try {

			
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public void resetPageInfo() {
		pageInfo = new PageInfo();
		filter = new HashMap<String, String>();
		pageInfo.setPageSize(10);
		pageInfo.setFilter(filter);
		session.put(pageInfoName, pageInfo);
	}
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String lnkGoDetail() throws Exception {
		try {
			/* mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start
			 * 調整角色權限控管功能，後USER決定取消此功能 
			
			boolean goEdit = false;
			Result result;
			Map params = new HashMap();
			
			String coreComcode = "";
			if(aps002DetailVo.getCoreComcode() == null || aps002DetailVo.getCoreComcode().length() <= 0) {
				goEdit = true;
			}else {
				coreComcode = aps002DetailVo.getCoreComcode();
			}
			
			Vector<String> roleList = getUserInfo().getRoleList();
			if(roleList.contains("F0201") || roleList.contains("F0202") || roleList.contains("00000")) {
				goEdit = true;
			}
			
			if(roleList.contains("F0201") || roleList.contains("00000")) {
				roleF0201 = true;
			}
			
			if(!goEdit) {
				params.put("usercode", getUserInfo().getUserId());
				result = this.firApsCtbcHandlerService.findFirApsCtbcHandlerByParams(params);
				if(result != null && result.getResObject() != null) {
					List<FirApsCtbcHandler> firApsCtbcHandlerList = (List<FirApsCtbcHandler>)result.getResObject();
					if(roleList.contains("F0203")) {
						for(FirApsCtbcHandler firApsCtbcHandler : firApsCtbcHandlerList) {
							if(coreComcode.equalsIgnoreCase(firApsCtbcHandler.getComcode())) {
								goEdit = true;
							}
						}
						if(!goEdit) {
							formLoad("execute");
							setMessage("登入者無權查詢其他單位之資料");
							return Action.INPUT;
						}
					}else if(roleList.contains("F0204")) {
						String coreComcodes = "";
						for(FirApsCtbcHandler firApsCtbcHandler : firApsCtbcHandlerList) {
							coreComcodes = this.firApsCtbcHandlerService.findCoreComcodeByUpperComcode(firApsCtbcHandler.getUppercomcode());
							if(coreComcodes.indexOf(coreComcode) >= 0) {
								goEdit = true;
							}
						}
						if(!goEdit) {
							formLoad("execute");
							setMessage("登入者無權查詢其他單位之資料(區域)");
							return Action.INPUT;
						}
					}else {
						formLoad("execute");
						setMessage("登入者之角色不適用於本作業，請洽系統服務人員。");
						return Action.INPUT;
					}
				}else {
					formLoad("execute");
					setMessage("目前登入者權限為單位或區域查詢角色，但未設定對應資料(FIR_APS_CBC_HANDLER)，請洽系統服務人員。");
					return Action.INPUT;
				}
			}
			mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end */
			Map params = new HashMap();
			params.put("batchNo", aps002DetailVo.getBatchNo());
			params.put("batchSeq", aps002DetailVo.getBatchSeq());
			params.put("fkOrderSeq", aps002DetailVo.getFkOrderSeq());
			Result result = this.firCtbcBatchDtlService.findForAps002Detail(params);
			if(result != null && result.getResObject() != null) {
				aps002DetailVo = (Aps002DetailVo)result.getResObject();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String lnkDownloadPDF() throws Exception {
		
		String businessNo = aps002DetailVo.getBatchNo() + aps002DetailVo.getBatchSeq() + aps002DetailVo.getFkOrderSeq();
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
            File file = new File(configUtil.getString("tempFolder") + aps002DetailVo.getFilenameTiff());
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
//			try {
//                if(fileInputStream != null){
//                	fileInputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            
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
	
@SuppressWarnings({ "rawtypes", "unchecked" })
public String lnkDownloadProve() throws Exception {
		
		String orderSeq = aps002DetailVo.getFkOrderSeq();

        try{
        	
        	Map params = new HashMap<String,Object>();
    		params.put("orderseq", orderSeq);
    		Result result = this.firRptCtbcCtfService.findFirRptCtbcCtfByParams(params);
    		if(result != null && result.getResObject() != null) {
    			List<FirRptCtbcCtf> resultList = (List<FirRptCtbcCtf>)result.getResObject();
    			FirRptCtbcCtf firRptCtbcCtf = resultList.get(0);
    			RptUtil rptUtil = new RptUtil(configUtil.getString("rptUrl"));
    			String rptFilePath = configUtil.getString("tempFolder");
    			File fileFolder = new File(rptFilePath);
    			if(!fileFolder.exists()) {
    				fileFolder.mkdirs();
    			}
    			boolean check = rptUtil.genFir00103Pdf(rptFilePath, firRptCtbcCtf.getOid().toString(), orderSeq);
    			if(check) {
    				File pdfFile = new File(rptFilePath + orderSeq + ".pdf");
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
		}finally{

		}
		return Action.SUCCESS;
	}

	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start
	 *轉檔異常資料修正*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String lnkGoEditUnusualData() throws Exception {
		
		String batchNo = aps002DetailVo.getBatchNo();
		String batchSeq = aps002DetailVo.getBatchSeq();
		String orderSeq = aps002DetailVo.getFkOrderSeq();
	
	    try{
	    	Vector<String> roleList = getUserInfo().getRoleList();
			if(!roleList.contains("F0201") && !roleList.contains("00000")) {
				setMessage("本功能僅限案件轉核心要保檔失敗時由核保人員角色進行修正。");
				HashMap params = new HashMap();
				params.put("batchNo", aps002DetailVo.getBatchNo());
				params.put("batchSeq", aps002DetailVo.getBatchSeq());
				params.put("fkOrderSeq", aps002DetailVo.getFkOrderSeq());
				Result result = this.firCtbcBatchDtlService.findForAps002Detail(params);
				if(result != null && result.getResObject() != null) {
					aps002DetailVo = (Aps002DetailVo)result.getResObject();
				}
				return Action.INPUT;
			}
	    	
	    	Map params = new HashMap<String,Object>();
	    	params.put("batchNo", batchNo);
	    	params.put("batchSeq", batchSeq);
			params.put("orderseq", orderSeq);
			Result result = this.firCtbcStlService.findFirCtbcStlByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcStl> resultList = (List<FirCtbcStl>)result.getResObject();
				FirCtbcStl firCtbcStl = resultList.get(0);
				aps002DetailVo.setCommCenterCode(firCtbcStl.getCommCenterCode());
				aps002DetailVo.setHandler1code(firCtbcStl.getCoreHandler1code());
			}else {
//				this.url = "/aps/002/lnkGoDetail.action?" 
//						+ "aps002DetailVo.batchNo=" + batchNo
//						+ "&aps002DetailVo.batchSeq=" + batchSeq
//						+ "&aps002DetailVo.fkOrderSeq=" + orderSeq
//						+ "&aps002DetailVo.coreComcode=" + aps002DetailVo.getCoreComcode();
//				setMessage("查無檔案");
//				return "redirect";
			}
		}catch (IOException e) {
		            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
	
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String btnEditUnusualData() throws Exception {
		try{
			System.out.println("btnEditUnusualData......");
			Map params = new HashMap<String,Object>();
	    	params.put("usercode", aps002DetailVo.getHandler1code());
	    	params.put("validstatus", "1");
	    	Result result = this.prpduserService.findPrpduserByParams(params);
	    	if(result == null || result.getResObject() == null) {
	    		setMessage("核心系統查無此服務人員或服務人員已失效");
	    		return Action.INPUT;
	    	}
	    	
	    	List<Prpduser> resultList = (List<Prpduser>)result.getResObject();
	    	if(resultList.size() <= 0) {
	    		setMessage("核心系統查無此服務人員或服務人員已失效");
	    		return Action.INPUT;
	    	}
	    	
	    	Prpduser prpduser = resultList.get(0);
	    	String comcode = StringUtil.nullToSpace(prpduser.getComcode());
	    	
	    	FirCtbcStl firCtbcStl = null;
	    	FirCtbcBatchDtl firCtbcBatchDtl = null;
	    	
	    	params = new HashMap<String,Object>();
	    	params.put("batchNo", aps002DetailVo.getBatchNo());
	    	params.put("batchSeq", aps002DetailVo.getBatchSeq());
			params.put("orderSeq", aps002DetailVo.getFkOrderSeq());
			result = this.firCtbcStlService.findFirCtbcStlByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcStl> resultList2 = (List<FirCtbcStl>)result.getResObject();
				firCtbcStl = resultList2.get(0);
				firCtbcStl.setCommCenterCode(aps002DetailVo.getCommCenterCode());
				firCtbcStl.setCoreHandler1code(aps002DetailVo.getHandler1code());
				firCtbcStl.setCoreComcode(comcode);
			}
			
			params = new HashMap<String,Object>();
	    	params.put("batchNo", aps002DetailVo.getBatchNo());
	    	params.put("batchSeq", aps002DetailVo.getBatchSeq());
			params.put("orderSeq", aps002DetailVo.getFkOrderSeq());
			result = this.firCtbcBatchDtlService.findFirCtbcBatchDtlByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcBatchDtl> resultList2 = (List<FirCtbcBatchDtl>)result.getResObject();
				firCtbcBatchDtl = resultList2.get(0);
				firCtbcBatchDtl.setOrderSeqStatus("2");
				firCtbcBatchDtl.setPrpinsBatchStatus("0");
				firCtbcBatchDtl.setIupdate(getUserInfo().getUserId());
				firCtbcBatchDtl.setDupdate(new Date());
			}
			
			this.cibPolicyDataImportService.updateUnusualData(firCtbcStl, firCtbcBatchDtl, getUserInfo());
			setMessage("修改成功");
			this.url = "/aps/002/default.action";
			return "redirect";
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	private void query(String queryType) throws SystemException, Exception {

		parameterHandler();
		if("1".equals(queryType)) {
			// 檔案清單查詢
			String orderSeq = (String)getPageInfo().getFilter().get("orderSeq");
			String policyno =  (String)getPageInfo().getFilter().get("policyno");
			if((orderSeq != null && orderSeq.length() > 0) ||
					(policyno != null && policyno.length() > 0)) {
				getPageInfo().getFilter().put("joinDtl", "Y");
			}else {
				getPageInfo().getFilter().remove("joinDtl");
			}
			
			Result result = this.firCtbcBatchMainService.findFirCtbcBatchMainByPageInfo(getPageInfo());
			
			if(result != null && result.getResObject() != null) {
				devResults = (List<FirCtbcBatchMain>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		}else {
			// 檔案清單查詢
			Result result = this.firCtbcBatchDtlService.findFirCtbcBatchDtlByPageInfoJoinStl(getPageInfo());
			if(result != null && result.getResObject() != null) {
				//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
				devResults2 = (List<Aps002DetailVo>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		}
	}
	
	private String getFtsFileOid(String businessNo) {
		
		String fileOid = "";
		String riskCode = "F";
		String httpURL = configUtil.getString("downloadListServiceUrl");
		StringBuilder  stringBuilder = new StringBuilder();
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
            if(list != null && list.size() > 0) {
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
        finally {
        }
        return fileOid;
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public String getPageInfoName() {
		return pageInfoName;
	}

	public void setPageInfoName(String pageInfoName) {
		this.pageInfoName = pageInfoName;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> getFilter() {
		if (pageInfo.getFilter() == null) {
			pageInfo.setFilter(new HashMap());
			session.put(pageInfoName, pageInfo);
			filter = new HashMap<String, String>();
		} else {
			filter = pageInfo.getFilter();
		}
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		this.pageInfo = (PageInfo) session.get(pageInfoName);
		if (this.pageInfo == null) {
			this.resetPageInfo();
		}
	}

	public Map<String, String> getFileStatusMap() {
		return fileStatusMap;
	}

	public void setFileStatusMap(Map<String, String> fileStatusMap) {
		this.fileStatusMap = fileStatusMap;
	}

	public FirCtbcBatchMainService getFirCtbcBatchMainService() {
		return firCtbcBatchMainService;
	}

	public void setFirCtbcBatchMainService(FirCtbcBatchMainService firCtbcBatchMainService) {
		this.firCtbcBatchMainService = firCtbcBatchMainService;
	}

	public List<FirCtbcBatchMain> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcBatchMain> devResults) {
		this.devResults = devResults;
	}

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start*/
	public List<Aps002DetailVo> getDevResults2() {
		return devResults2;
	}

	public void setDevResults2(List<Aps002DetailVo> devResults2) {
		this.devResults2 = devResults2;
	}
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/

	public FirCtbcBatchDtlService getFirCtbcBatchDtlService() {
		return firCtbcBatchDtlService;
	}

	public void setFirCtbcBatchDtlService(FirCtbcBatchDtlService firCtbcBatchDtlService) {
		this.firCtbcBatchDtlService = firCtbcBatchDtlService;
	}

	public Aps002DetailVo getAps002DetailVo() {
		return aps002DetailVo;
	}

	public void setAps002DetailVo(Aps002DetailVo aps002DetailVo) {
		this.aps002DetailVo = aps002DetailVo;
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

	public Map<String, String> getOrderSeqStatusMap() {
		return orderSeqStatusMap;
	}

	public void setOrderSeqStatusMap(Map<String, String> orderSeqStatusMap) {
		this.orderSeqStatusMap = orderSeqStatusMap;
	}

	public Map<String, String> getFkStatusMap() {
		return fkStatusMap;
	}

	public void setFkStatusMap(Map<String, String> fkStatusMap) {
		this.fkStatusMap = fkStatusMap;
	}

	public Map<String, String> getSendTypeMap() {
		return sendTypeMap;
	}

	public void setSendTypeMap(Map<String, String> sendTypeMap) {
		this.sendTypeMap = sendTypeMap;
	}

	public FirRptCtbcCtfService getFirRptCtbcCtfService() {
		return firRptCtbcCtfService;
	}

	public void setFirRptCtbcCtfService(FirRptCtbcCtfService firRptCtbcCtfService) {
		this.firRptCtbcCtfService = firRptCtbcCtfService;
	}
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	調整角色權限控管功能，後USER決定取消此功能
	public FirApsCtbcHandlerService getFirApsCtbcHandlerService() {
		return firApsCtbcHandlerService;
	}

	public void setFirApsCtbcHandlerService(FirApsCtbcHandlerService firApsCtbcHandlerService) {
		this.firApsCtbcHandlerService = firApsCtbcHandlerService;
	}

	public boolean isRoleF0201() {
		return roleF0201;
	}

	public void setRoleF0201(boolean roleF0201) {
		this.roleF0201 = roleF0201;
	}*/

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start*/
	public FirCtbcStlService getFirCtbcStlService() {
		return firCtbcStlService;
	}

	public void setFirCtbcStlService(FirCtbcStlService firCtbcStlService) {
		this.firCtbcStlService = firCtbcStlService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PrpduserService getPrpduserService() {
		return prpduserService;
	}

	public void setPrpduserService(PrpduserService prpduserService) {
		this.prpduserService = prpduserService;
	}

	public CibPolicyDataImportService getCibPolicyDataImportService() {
		return cibPolicyDataImportService;
	}

	public void setCibPolicyDataImportService(CibPolicyDataImportService cibPolicyDataImportService) {
		this.cibPolicyDataImportService = cibPolicyDataImportService;
	}
	
	public String getGoBack() {
		return goBack;
	}

	public void setGoBack(String goBack) {
		this.goBack = goBack;
	}
	/*mantis：FIR0181，中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/
}
