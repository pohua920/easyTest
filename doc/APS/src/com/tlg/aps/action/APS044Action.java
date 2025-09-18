package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firGenRenewListService.FirRenewListGenFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps044DetailVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.prpins.service.FirRenewListDtlService;
import com.tlg.prpins.service.FirRenewListService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業*/
@SuppressWarnings("serial")
public class APS044Action extends BaseAction {
	
	private FirRenewListService firRenewListService;
	private FirRenewListDtlService firRenewListDtlService;
	private FirSpService firSpService;
	private ConfigUtil configUtil;
	
	private List<FirRenewList> devResults;
	private List<Aps044DetailVo> dtlDevResults;
	
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	private FirRenewList firRenewList;
	private FirRenewListDtl firRenewListDtl;
	private String batchNo;
	private String rnYymm;
	private String token;
	private String downloadFileName;
	private InputStream inputStream;
	private static final Integer DATACOUNT = 1000;
	
	/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	private String fileType;
	private String bno;
	private String downloadFilename;
	private OutputStream fileOutputStream;
	private FirRenewListGenFileService firRenewListGenFileService;
	private String goBack;
	/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
	
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

	/** APS044E0.jsp頁面按下查詢鍵,開始查詢主檔資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
			if(!"Y".equals(goBack)) {
				getPageInfo().setCurrentPage(1);				
			}
			/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
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
	
	/** APS044E0.jsp頁面按下主檔批次號碼資料，進入明細查詢頁面 */
	public String lnkGoDetailQuery() throws Exception {
		
		return  Action.SUCCESS;
	}
	
	/** APS044E1.jsp頁面按下查詢，查詢明細資料 */
	@SuppressWarnings("unchecked")
	public String btnDtlQuery() throws Exception {
		try{
			getDPageInfo().getFilter().put("batchNo", batchNo);
			getDPageInfo().getFilter().put("rnYymm", rnYymm);
			getDPageInfo().getFilter().put("sortBy", "OLD_POLICYNO");
			getDPageInfo().getFilter().put("sortType", "ASC");
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS044E1.jsp頁面，連結至查詢頁面 */
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
	
	/** APS044E1.jsp(明細)分頁資料中， 查詢結果點選刪除/還原更新刪除註記  */
	public String btnUpdateFlag() throws Exception {
		try {
			update();
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS044E1.jsp(明細)分頁資料中， 點選「核心續件資料更新」按鈕，呼叫SP更新最新核心續件情況 */
	public String btnUpdateCoreRenewData() throws Exception{
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("inBatchNo", batchNo);
			params.put("inUser", getUserInfo().getUserId().toUpperCase());
			int returnValue = firSpService.runSpFirRenewListUpdate(params);
			if (returnValue != 0) {
				setMessage("核心續件資料更新失敗!");
			}else {
				setMessage("核心續件資料更新成功!");
			}
			//連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("btnUpdateCoreRenewData",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) {
			setMessage("核心續件資料更新失敗:"+se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS044E1.jsp(明細)頁面按下「產生xls」按鈕，下載EXCEL檔案 */
	@SuppressWarnings("unchecked")
	public String btnDownloadFile() throws Exception {
		try {
			getDPageInfo().getFilter().put("batchNo", batchNo);
			getDPageInfo().getFilter().put("rnYymm", rnYymm);
			getDPageInfo().getFilter().put("sortBy", "OID");
			
			// 分次讀取資料寫入excel
			int count = firRenewListDtlService.countForAps044Detail(getDPageInfo().getFilter());

			int cycleTimes = count / DATACOUNT;
			if (count % DATACOUNT > 0) {
				cycleTimes = cycleTimes + 1;
			}
			int startRow = 1;
			int endRow = DATACOUNT;

			List<Aps044DetailVo> excelList = new ArrayList<>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
				// 建立總表
				SXSSFSheet sheet = workbook.createSheet();
				//mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格
				String[] titleArr = { "續保單號", "保單到期日", "續保類型", "業務來源", "續保要保書號", "新年度保單號", "是否完成續保篩選", "是否有附加險", "附加險重算完成",
						"是否自動續約", "核心件續期繳費", "核心件繳費資訊", "前一年火險建物保額", "前一年火險非建物保額", "前一年地震保額", "前一年火險建物保費", "前一年火險非建物保費",
						"前一年地震保費", "前一年附加險保費", "火險建物保額", "火險非建物保額", "地震保額", "火險建物保費", "火險非建物保費", "地震保費", "附加險保費",
						"總保險費", "主被保險人", "主要保人", "主要保人郵遞區號", "主要保人通訊地址", "服務人員", "核心虛擬編號", "處理狀態", "備註", "刪除註記", "刪除人員",
						"刪除時間", "抵押權人", "是否批改", "前一年總保費" };
				SXSSFRow rowTitle = sheet.createRow(0);
				// 建立excel欄位
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}

				int totalRowNum = 0;
				for (int c = 0; c < cycleTimes; c++) {
					getDPageInfo().setStartRow(startRow);
					getDPageInfo().setEndRow(endRow);
					getDPageInfo().getFilter().put("doPage", false);
					Result result = firRenewListDtlService.findForAps044DetailByPageInfo(getDPageInfo()); // 一次只查詢一千筆
					if (result.getResObject() != null) {
						excelList = (List<Aps044DetailVo>) result.getResObject();
						for (Aps044DetailVo dtl : excelList) {
							totalRowNum++;
							SXSSFRow row = sheet.createRow(totalRowNum); // 建立列 必須為每次迴圈筆數往上加
							row.createCell(0).setCellValue(dtl.getOldPolicyno());
							row.createCell(1).setCellValue(sdf.format(dtl.getOldEnddate()));
							String rnType = dtl.getRnType();
							if("1".equals(rnType)) {
								rnType = "核心續件";
							}else if("2".equals(rnType)) {
								rnType = "中信件";
							}else if("3".equals(rnType)) {
								rnType = "外銀續件";
							}
							row.createCell(2).setCellValue(rnType);
							row.createCell(3).setCellValue(dtl.getBusinessnature());
							row.createCell(4).setCellValue(dtl.getProposalno());
							row.createCell(5).setCellValue(dtl.getPolicyno());
							row.createCell(6).setCellValue(dtl.getIsRenewal());
							row.createCell(7).setCellValue(dtl.getIsAddIns());
							row.createCell(8).setCellValue(dtl.getIsAddCalc());
							row.createCell(9).setCellValue(dtl.getIsAutoRenew());
							row.createCell(10).setCellValue("2".equals(dtl.getRnPayway()) ? "信用卡" : "");
							row.createCell(11).setCellValue(dtl.getRnPaydata());
							row.createCell(12).setCellValue(dtl.getOldAmtF1() == null ? "" : dtl.getOldAmtF1().toString());
							row.createCell(13).setCellValue(dtl.getOldAmtF2() == null ? "" : dtl.getOldAmtF2().toString());
							row.createCell(14).setCellValue(dtl.getOldAmtQ() == null ? "" : dtl.getOldAmtQ().toString());
							row.createCell(15).setCellValue(dtl.getOldPremF1() == null ? "" : dtl.getOldPremF1().toString());
							row.createCell(16).setCellValue(dtl.getOldPremF2() == null ? "" : dtl.getOldPremF2().toString());
							row.createCell(17).setCellValue(dtl.getOldPremQ() == null ? "" : dtl.getOldPremQ().toString());
							row.createCell(18).setCellValue(dtl.getOldPremA() == null ? "" : dtl.getOldPremA().toString());
							row.createCell(19).setCellValue(dtl.getAmtF1() == null ? "" : dtl.getAmtF1().toString());
							row.createCell(20).setCellValue(dtl.getAmtF2() == null ? "" : dtl.getAmtF2().toString());
							row.createCell(21).setCellValue(dtl.getAmtQ() == null ? "" : dtl.getAmtQ().toString());
							BigDecimal premF1 = dtl.getPremF1() != null ? dtl.getPremF1() : new BigDecimal(0);
							BigDecimal premF2 = dtl.getPremF2() != null ? dtl.getPremF2() : new BigDecimal(0);
							BigDecimal premQ = dtl.getPremQ() != null ? dtl.getPremQ() : new BigDecimal(0);
							BigDecimal premA = dtl.getPremA() != null ? dtl.getPremA() : new BigDecimal(0);
							row.createCell(22).setCellValue(dtl.getPremF1() == null ? "" : dtl.getPremF1().toString());
							row.createCell(23).setCellValue(dtl.getPremF2()== null ? "" : premF2.toString());
							row.createCell(24).setCellValue(dtl.getPremQ()== null ? "" : dtl.getPremQ().toString());
							row.createCell(25).setCellValue(dtl.getPremA()== null ? "" : dtl.getPremA().toString());
							row.createCell(26).setCellValue(premF1.add(premF2).add(premQ).add(premA).toString());
							row.createCell(27).setCellValue(dtl.getInsuredname());
							row.createCell(28).setCellValue(dtl.getAppliname());
							row.createCell(29).setCellValue(dtl.getPostcode());
							row.createCell(30).setCellValue(dtl.getPostaddress());
							row.createCell(31).setCellValue(dtl.getHandler1code());
							row.createCell(32).setCellValue(dtl.getPrintvirtualcode());
							row.createCell(33).setCellValue(dtl.getDataStatus());
							row.createCell(34).setCellValue(dtl.getRemark());
							row.createCell(35).setCellValue(dtl.getDeleteFlag());
							row.createCell(36).setCellValue(dtl.getDeleteUser());
							row.createCell(37).setCellValue(dtl.getDeleteDate() == null? "" : sdf2.format(dtl.getDeleteDate()));
							/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
							row.createCell(38).setCellValue(dtl.getMortgagee());
							row.createCell(39).setCellValue(dtl.getHasEndorse());
							BigDecimal oldPremF1 = dtl.getOldPremF1() != null ? new BigDecimal(dtl.getOldPremF1()) : new BigDecimal(0);
							BigDecimal oldPremF2 = dtl.getOldPremF2() != null ? new BigDecimal(dtl.getOldPremF2()) : new BigDecimal(0);
							BigDecimal oldPremQ = dtl.getOldPremQ() != null ? new BigDecimal(dtl.getOldPremQ()) : new BigDecimal(0);
							BigDecimal oldPremA = dtl.getOldPremA() != null ? new BigDecimal(dtl.getOldPremA()) : new BigDecimal(0);
							row.createCell(40).setCellValue((oldPremF1.add(oldPremF2).add(oldPremQ).add(oldPremA)).toString());
							/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
						}
					}

					startRow += DATACOUNT;
					endRow += DATACOUNT;
				}

				downloadFileName = "RENEW_LIST_" + rnYymm + ".xlsx";
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte[] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);
			}
			//連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("btnDownloadFile",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	/** APS044E0.jsp頁面按下進入，進入檔案產生、下載頁面 */
	public String lnkGoDownload() throws Exception {
		try {
			if (null == firRenewList.getBatchNo()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", firRenewList.getBatchNo());
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
	
	public String btnGenFile() throws Exception {
		Result result = new Result();
		String message = "";
			
		String fileBatchNo = firRenewList.getBatchNo();
		String userId = getUserInfo().getUserId().toUpperCase();
		try {
			if("ctbc".equals(fileType)) {
				result = firRenewListGenFileService.genCtbcFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			if("ncore".equals(fileType)) {
				result = firRenewListGenFileService.genNcoreFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式  start
			if("tsca".equals(fileType)) {
				result = firRenewListGenFileService.genNcoreFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式  end
			if("ncorea".equals(fileType)) {
				result = firRenewListGenFileService.genNcoreaFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			if("bop".equals(fileType)) {
				result = firRenewListGenFileService.genBopFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			if("fb".equals(fileType)) {
				result = firRenewListGenFileService.genFbFile(fileBatchNo, userId, fileType);
				message = result.getMessage().toString();
			}
			// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
			if("frnpro".equals(fileType)) {
				String rnYymm = firRenewList.getRnYymm();
				result = firRenewListGenFileService.genPpFile(fileBatchNo, userId, rnYymm);
				message = result.getMessage().toString();
			}
			// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
		}catch (Exception e) {
			e.printStackTrace();
			message = "產生檔案時發生異常:"+e.getMessage();
		}
		
		setMessage(message);
		Map<String, Object> params = new HashMap<>();
		params.put("batchNo", fileBatchNo);
		downloadQuery(params);
		return Action.SUCCESS;
	}
	
	/**APS044E2.jsp 產生檔案頁面按下下載按鈕，依各檔案類型開始下載檔案*/
	public String btnDownloadFile2() throws Exception {
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

			this.inputStream = httpResponse.getEntity().getContent();
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
			while ((readLength = inputStream.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				fileOutputStream.write(bytes);
			}

			fileOutputStream.flush();
			this.inputStream = new DeleteAfterDownloadFileInputStream(file);

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
	
	public Result downloadQuery(Map<String, Object> params) throws Exception {
		Result result = firRenewListService.findFirRenewListByUk(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			firRenewList = (FirRenewList) result.getResObject();
		}
		return result;
	}
	/* mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = firRenewListService.findFirRenewListByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirRenewList>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	//明細資料查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		if(null == batchNo || null == rnYymm) {
			batchNo = (String) getDPageInfo().getFilter().get("batchNo");
			rnYymm = (String) getDPageInfo().getFilter().get("rnYymm");
		}
		getDPageInfo().getFilter().put("doPage", true);
		Result result = new Result();	
		result = firRenewListDtlService.findForAps044DetailByPageInfo(getDPageInfo());
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps044DetailVo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	/** 負責處理update動作  */
	private void update() throws Exception {
		if("Y".equals(firRenewListDtl.getDeleteFlag())){
			firRenewListDtl.setDeleteUser(getUserInfo().getUserId().toUpperCase());
			firRenewListDtl.setDeleteDate(new Date());
		}
		if("N".equals(firRenewListDtl.getDeleteFlag())) {
			firRenewListDtl.setDeleteUser(null);
			firRenewListDtl.setDeleteDate(null);
		}
		firRenewListDtlService.updateFirRenewListDtl(firRenewListDtl);
		setMessage("儲存成功");
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
	
	/** APS044E1.jsp(明細)分頁資料中，重新輸入要顯示的頁數 */
	public String txtDtlChangePageIndex() throws Exception {
		try {
			dtlQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS044E1.jsp(明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlDtlPageSizeChanged() throws Exception {
		try {
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
	
	/** APS044E1.jsp(明細)分頁資料中， 查詢結果點選上下三角型排序 */
	public String lnkDtlSortQuery() throws Exception {
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
	
	public FirRenewListService getFirRenewListService() {
		return firRenewListService;
	}

	public void setFirRenewListService(FirRenewListService firRenewListService) {
		this.firRenewListService = firRenewListService;
	}

	public FirRenewListDtlService getFirRenewListDtlService() {
		return firRenewListDtlService;
	}

	public void setFirRenewListDtlService(FirRenewListDtlService firRenewListDtlService) {
		this.firRenewListDtlService = firRenewListDtlService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public List<FirRenewList> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirRenewList> devResults) {
		this.devResults = devResults;
	}
	
	public List<Aps044DetailVo> getDtlDevResults() {
		return dtlDevResults;
	}

	public void setDtlDevResults(List<Aps044DetailVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}

	public FirRenewList getFirRenewList() {
		return firRenewList;
	}

	public void setFirRenewList(FirRenewList firRenewList) {
		this.firRenewList = firRenewList;
	}

	public FirRenewListDtl getFirRenewListDtl() {
		return firRenewListDtl;
	}

	public void setFirRenewListDtl(FirRenewListDtl firRenewListDtl) {
		this.firRenewListDtl = firRenewListDtl;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getRnYymm() {
		return rnYymm;
	}

	public void setRnYymm(String rnYymm) {
		this.rnYymm = rnYymm;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}
	
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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

	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	public FirRenewListGenFileService getFirRenewListGenFileService() {
		return firRenewListGenFileService;
	}

	public void setFirRenewListGenFileService(FirRenewListGenFileService firRenewListGenFileService) {
		this.firRenewListGenFileService = firRenewListGenFileService;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	
	public OutputStream getFileOutputStream() {
		return fileOutputStream;
	}

	public void setFileOutputStream(OutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}

	public String getGoBack() {
		return goBack;
	}

	public void setGoBack(String goBack) {
		this.goBack = goBack;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}
