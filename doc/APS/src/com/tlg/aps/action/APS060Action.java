package com.tlg.aps.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firAgtrnYcbN1ImportService.FirAgtrnYcbN1ImportService;//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbGenFileService;
import com.tlg.aps.bs.firYcbRenewalFileService.YcbRenewalFileService; 
import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.Aps060DownloadlVo;
import com.tlg.aps.vo.FileListResponseVo;
import com.tlg.aps.vo.FileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnBatchMainService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FtsUtil;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業  */

@SuppressWarnings("serial")
public class APS060Action extends BaseAction {
	
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	private FirYcbGenFileService firYcbGenFileService;
	private YcbRenewalFileService ycbRenewalFileService;
	private FirBatchInfoService firBatchInfoService;
	private FirAgtrnYcbN1ImportService firAgtrnYcbN1ImportService;//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	private ConfigUtil configUtil;
	
	private List<Aps060YcbDetailVo> devResults;
	private List<Aps060YcbDetailVo> dtlDevResults;
	
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	private Aps060YcbDetailVo ycbDetailVo;
	private Aps060DownloadlVo ycbDownloadVo;
	private FirAgtTocoreInsured firAgtTocoreInsured;
	private String batchNo;
	private String batchSeq;
	private String rnYyyymm;
	
	private String fileType;
	private String goBack;
	private String bno;
	private String downloadFilename;
	private String insuredDataStr;
	
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	
	private File upload;//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	private String handleridentifynumber;//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位
	
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

	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(!"Y".equals(goBack)) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("deleteFlag", "N");
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
	
	/** APS060E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS060E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	@SuppressWarnings("unchecked")
	public String btnDtlQuery() throws Exception {
		try{
			getDPageInfo().getFilter().put("batchNo", batchNo);
			getDPageInfo().getFilter().put("sortBy", "DTL.BATCH_SEQ");
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
	
	/** APS060E0.jsp頁面按下主檔批次號碼資料，查詢明細資料 */
	@SuppressWarnings("unchecked")
	public String lnkGoDetailQuery() throws Exception {
		String forward = "input";
		try {
			if("Y".equals(goBack)) {
				getDPageInfo().getFilter().put("batchNo",batchNo);
			}else {
				getDPageInfo().getFilter().put("batchNo",batchNo);
				getDPageInfo().getFilter().put("rnYyyymm",rnYyyymm);//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
				getDPageInfo().setCurrentPage(1);
			}
			getDPageInfo().getFilter().put("sortBy","DTL.BATCH_SEQ");
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
	
	/** APS060E1.jsp(明細)分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** APS060E1.jsp(明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS060E1.jsp(明細)分頁資料中， 查詢結果點選上下三角型排序 */
	public String lnkSortDtlQuery() throws Exception {
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
	
	/** APS044E1.jsp(明細)頁面按下「產生xls」按鈕，下載EXCEL檔案 */
	@SuppressWarnings("unchecked")
	public String btnDownloadXls() throws Exception {
		try {
			getDPageInfo().getFilter().put("batchNo", batchNo);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
				// 建立總表
				SXSSFSheet sheet = workbook.createSheet();
				String[] titleArr = { "批次號碼", "批次序號", "資料狀態", "續保與否", "出單單位", "員編", "服務人員姓名", "保經系統扣款帳號", "行員登錄證號",
						"行員姓名", "分行別", "分行名稱", "火險保單號碼", "借款人", "借款人ID", "起期日", "到期日", "標的物郵遞區號", "標的物地址", "主要建材",
						"屋頂材料", "建築等級", "坪數", "建築完成年", "總樓層數", "要保人郵遞區號", "要保人地址", "所有權人", "所有權人ID", "前一年火險保額",
						"前一年地震保額", "前一年火險保費", "前一年地震保費", "火險保額", "地震險保額", "火險保費", "地震險保費", "異常訊息", "提醒訊息" };
				SXSSFRow rowTitle = sheet.createRow(0);
				// 建立excel欄位
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}

				Result result = firAgtrnBatchDtlService.findYcbRnDataForXlsByBatchNo(getDPageInfo());

				if (result.getResObject() != null) {
					List<Aps060YcbGenFileVo> excelList = (List<Aps060YcbGenFileVo>) result.getResObject();
					for (int i = 0; i < excelList.size(); i++) {
						SXSSFRow row = sheet.createRow(i + 1); // 建立儲存格
						Aps060YcbGenFileVo xlsData = excelList.get(i);
						row.createCell(0).setCellValue(xlsData.getBatchNo());
						row.createCell(1).setCellValue(xlsData.getBatchSeq());
						row.createCell(2).setCellValue(xlsData.getDataStatus());
						row.createCell(3).setCellValue(xlsData.getSfFlag());
						row.createCell(4).setCellValue(xlsData.getComcode());
						row.createCell(5).setCellValue(xlsData.getHandler1code());
						row.createCell(6).setCellValue(xlsData.getUsername());
						row.createCell(7).setCellValue(xlsData.getAccountno());
						row.createCell(8).setCellValue(xlsData.getHandleridentifynumber());
						row.createCell(9).setCellValue(xlsData.getAgentname());
						row.createCell(10).setCellValue(xlsData.getExtracomcode());
						row.createCell(11).setCellValue(xlsData.getExtracomname());
						row.createCell(12).setCellValue(xlsData.getOldpolicyno());
						row.createCell(13).setCellValue(xlsData.getNameA());
						row.createCell(14).setCellValue(xlsData.getIdA());
						row.createCell(15).setCellValue(sdf.format(xlsData.getStartdate()));
						row.createCell(16).setCellValue(sdf.format(xlsData.getEnddate()));
						row.createCell(17).setCellValue(xlsData.getAddresscode());
						row.createCell(18).setCellValue(xlsData.getAddressdetailinfo());
						row.createCell(19).setCellValue(xlsData.getWallmaterial());
						row.createCell(20).setCellValue(xlsData.getRoofmaterial());
						row.createCell(21).setCellValue(xlsData.getStructure());
						row.createCell(22).setCellValue(xlsData.getBuildarea());
						row.createCell(23).setCellValue(xlsData.getBuildyears());
						row.createCell(24).setCellValue(xlsData.getSumfloors());
						row.createCell(25).setCellValue(xlsData.getPostcode());
						row.createCell(26).setCellValue(xlsData.getPostaddress());
						row.createCell(27).setCellValue(xlsData.getNameI());
						row.createCell(28).setCellValue(xlsData.getIdI());
						row.createCell(29).setCellValue(xlsData.getAmountFLast());
						row.createCell(30).setCellValue(xlsData.getAmountQLast());
						row.createCell(31).setCellValue(xlsData.getPremiumFLast());
						row.createCell(32).setCellValue(xlsData.getPremiumQLast());
						row.createCell(33).setCellValue(xlsData.getAmountF());
						row.createCell(34).setCellValue(xlsData.getAmountQ());
						row.createCell(35).setCellValue(xlsData.getPremiumF());
						row.createCell(36).setCellValue(xlsData.getPremiumQ());
						row.createCell(37).setCellValue(xlsData.getCheckErrMsg());
						row.createCell(38).setCellValue(xlsData.getCheckWarnMsg());
					}
				}

				File fileFolder = new File(configUtil.getString("tempFolder"));
				if (!fileFolder.exists()) {
					fileFolder.mkdirs();
				}

				sdf = new SimpleDateFormat("mmss");
				downloadFilename = batchNo + sdf.format(new Date()) + ".xlsx";
				String filepath = configUtil.getString("tempFolder") + downloadFilename;
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close();

				// 檔案加密
				//取得加密密碼
				Map<String, String> params = new HashMap<>();
				params.put("prgId", "FIR_AGT_YCBRN_XLSPWD");
				Result resultPwd = firBatchInfoService.findFirBatchInfoByUK(params);
				if(result.getResObject()==null) {
					setMessage("未設定資料交換檔案密碼，請通知資訊窗口處理。");
					return Action.SUCCESS;
				}
				FirBatchInfo batchInfo = (FirBatchInfo) resultPwd.getResObject();
				String filePwd = batchInfo.getMailTo();
				
				POIFSFileSystem fs = new POIFSFileSystem();
				EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
				Encryptor enc = info.getEncryptor();
				enc.confirmPassword(filePwd);
				OPCPackage opc = OPCPackage.open(new File(filepath), PackageAccess.READ_WRITE);
				OutputStream os = enc.getDataStream(fs);
				opc.save(os);
				opc.close();
				FileOutputStream fos = new FileOutputStream(filepath);
				fs.writeFilesystem(fos);
				fos.flush();
				fos.close();

				this.fileInputStream = new DeleteAfterDownloadFileInputStream(new File(filepath));
			}

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS060E1.jsp(明細)分頁資料中， 點選序號進入資料修改頁面*/
	public String lnkGoUpdate() throws Exception {
		try {
			if (null == batchNo || null == batchSeq) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				dtlQuery();
				return "input";
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", batchNo);
				params.put("batchSeq", batchSeq);
				Result result = updateQuery(params);
				if(result.getMessage()!=null) {
					dtlQuery();
					return "input";
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS060U0.jsp按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			Map<String,Object> params = new HashMap<>();
			params.put("batchNo", ycbDetailVo.getBatchNo());
			params.put("batchSeq", ycbDetailVo.getBatchSeq());
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
		
		List<FirAgtTocoreInsured> insuredList = new ArrayList<>();
		if(!StringUtil.isSpace(this.insuredDataStr)){
			ObjectMapper objectMapper = new ObjectMapper();
			insuredList = objectMapper.readValue(this.insuredDataStr, new TypeReference<ArrayList<FirAgtTocoreInsured>>(){}); 
		}
		ycbDetailVo.setInsuredList(insuredList);
		
		ycbRenewalFileService.updateFirAgtTocoreAndAgtrnBatchDtl(ycbDetailVo, getUserInfo().getUserId().toUpperCase());
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
		if(ycbDetailVo.getLocking().equals("N")) {
			setMessage("儲存成功");
		}else {
			setMessage("儲存及鎖定成功");
		}
		/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	}
	
	/** APS060E0.jsp頁面按下進入，進入檔案產生、下載頁面 */
	public String lnkGoDownload() throws Exception {
		try {
			if (null == batchNo) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
//				int fixCount = fixQuery(batchNo);
//				if (fixCount > 0) {
//					setMessage("尚有"+fixCount+"筆資料未修改完成，無法進入產檔作業。");
//					query();
//					return "input";
//				}
				
				Map<String, Object> params = new HashMap<>();
				params.put("batchNo", batchNo);
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
			
		String userId = getUserInfo().getUserId().toUpperCase();
		
		//FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
		switch (fileType) {
		case "rnFile":	//元大續保明細檔
			result = firYcbGenFileService.genRnFile(batchNo, userId);
			message = result.getMessage().toString();
			break;
		case "enFile":	//元大到期通知
			result = firYcbGenFileService.genEnFile(batchNo, userId);
			message = result.getMessage().toString();
			break;
		case "poFile":	//元大出單明細檔 
			result = firYcbGenFileService.genXlsFile(EnumYCBFile.POLICY, batchNo, userId);
			message = result.getMessage().toString();
			break;
		case "coFile":	//元大保單副本
			result = firYcbGenFileService.genXlsFile(EnumYCBFile.POLICYCOPY, batchNo, userId);
			message = result.getMessage().toString();
			break;
		default :
			message ="檔案類型(" + fileType + ")無產生任何檔案。";
			//do nothing
			break;
		}
		
		setMessage(message);
		Map<String,Object> params = new HashMap<>();
		params = new HashMap<>();
		params.put("batchNo", batchNo);
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
	
	public String btnFixData() throws Exception {
		
		int fixCount = fixQuery(batchNo);
		if (fixCount > 0) {
			setMessage("尚有"+fixCount+"筆資料未修改完成，無法進行整批鎖定。");
			query();
			return Action.SUCCESS;
		}
		
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("fixUserNull", "Y");
		params.put("sfFlag", "N");
		Result result = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
		if(result.getResObject()!=null) {
			List<FirAgtrnBatchDtl > fixList = (List<FirAgtrnBatchDtl>) result.getResObject();
			Date fixDate = new Date();
			for(FirAgtrnBatchDtl dtl : fixList) {
				dtl.setFixUser(getUserInfo().getUserId().toUpperCase());
				dtl.setFixDate(fixDate);
				dtl.setDataStatus("B");
				firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(dtl);
			}
		}
		setMessage("整批鎖定成功");
		
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
		Result result = firAgtrnBatchDtlService.findYcbrnInsuredData(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			ycbDetailVo = (Aps060YcbDetailVo) result.getResObject();
		}
		params.put("sortBy", "INSUREDFLAG,INSURED_SEQ");
		params.put("sortType", "ASC");
		result = firAgtTocoreInsuredService.findFirAgtTocoreInsuredByParams(params);
		
		if(result.getResObject() != null){ 
			List<FirAgtTocoreInsured> insuredList = (List<FirAgtTocoreInsured>) result.getResObject();
			for(FirAgtTocoreInsured insured:insuredList){
				insured.setStrBirthday(DateUtils.getTaiwanDateString(insured.getBirthday()));
			}
			this.insuredDataStr = JsonUtil.getJSONString(insuredList);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = firAgtrnBatchMainService.findBatchMainForYcbrnByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Aps060YcbDetailVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	//明細資料查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		Result result = new Result();	
		result = firAgtrnBatchDtlService.findForYcbrnDetail(getDPageInfo());
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps060YcbDetailVo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	public Result downloadQuery(Map<String, Object> params) throws Exception {
		Result result = firAgtrnBatchMainService.findYcbrnDownloadData(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			ycbDownloadVo = (Aps060DownloadlVo) result.getResObject();
		}
		return result;
	}
	
	//鎖定、進入產檔前先查詢是否已修改過所有資料
	public int fixQuery(String batchNo) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		params.put("fixUserNull", "Y");
		params.put("sfFlag", "N");
		params.put("iupdateNull", "Y");
		int fixCount = firAgtrnBatchDtlService.countFirAgtrnBatchDtl(params);
		
		return fixCount;
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
	
	public List<Aps060YcbDetailVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Aps060YcbDetailVo> devResults) {
		this.devResults = devResults;
	}

	public List<Aps060YcbDetailVo> getDtlDevResults() {
		return dtlDevResults;
	}

	public void setDtlDevResults(List<Aps060YcbDetailVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
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
	
	//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	public String lnkGoN1Upload() throws Exception {
		String forward = "input";
		try {
			getDPageInfo().getFilter().put("batchNo",batchNo);
			getDPageInfo().getFilter().put("rnYyyymm",rnYyyymm);
			forward = Action.SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	//轉入作業確定轉入按鈕，做資料匯入動作
	public String btnDataImport() throws Exception {
		System.out.println(">>>>>>btnDataImport");
		try {
			if (upload != null) {
				//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位START
				if(StringUtils.isNotBlank(handleridentifynumber)) {
					String[] handleridentifynumberArr = handleridentifynumber.split(",");
					//新增FIR_AGTRN_YCB_UPLOAD元大授信檔案資訊
					Result result = firAgtrnYcbN1ImportService.importFirAgtrnYcbN1(batchNo, upload, getUserInfo(), handleridentifynumberArr);
					//更新FIR_AGT_TOCORE_MAIN & FIR_AGTRN_BATCH_DTL
					if("S".equals(result.getMessage().getMessageString())) {
						result = firAgtrnYcbN1ImportService.updateFirAgtrnTocoreMainAndDtl(batchNo, getUserInfo());
					}
					if("S".equals(result.getMessage().getMessageString())) {
						setMessage("元大續保資料處理作業成功");
					} else {
						setMessage(result.getMessage().getMessageString());
					}
				} else {
					throw new SystemException("請輸入業務員登錄證字號");
				}
				//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位END
			} else {
				throw new SystemException("檔案讀取失敗");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/*下載sample檔案*/
	public String btnDowloadSample() throws Exception {
		try {
			downloadFilename = "SAMPLE.xlsx";
			fileInputStream = APS015Action.class.getClassLoader().getResourceAsStream("xlsx/FirAgtrnYcbUploadSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchSeq() {
		return batchSeq;
	}

	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}

	public String getInsuredDataStr() {
		return insuredDataStr;
	}

	public void setInsuredDataStr(String insuredDataStr) {
		this.insuredDataStr = insuredDataStr;
	}

	public FirAgtTocoreInsured getFirAgtTocoreInsured() {
		return firAgtTocoreInsured;
	}

	public void setFirAgtTocoreInsured(FirAgtTocoreInsured firAgtTocoreInsured) {
		this.firAgtTocoreInsured = firAgtTocoreInsured;
	}

	public String getRnYyyymm() {
		return rnYyyymm;
	}

	public void setRnYyyymm(String rnYyyymm) {
		this.rnYyyymm = rnYyyymm;
	}

	public Aps060YcbDetailVo getYcbDetailVo() {
		return ycbDetailVo;
	}

	public void setYcbDetailVo(Aps060YcbDetailVo ycbDetailVo) {
		this.ycbDetailVo = ycbDetailVo;
	}

	public Aps060DownloadlVo getYcbDownloadVo() {
		return ycbDownloadVo;
	}

	public void setYcbDownloadVo(Aps060DownloadlVo ycbDownloadVo) {
		this.ycbDownloadVo = ycbDownloadVo;
	}

	public YcbRenewalFileService getYcbRenewalFileService() {
		return ycbRenewalFileService;
	}

	public void setYcbRenewalFileService(YcbRenewalFileService ycbRenewalFileService) {
		this.ycbRenewalFileService = ycbRenewalFileService;
	}

	public FirYcbGenFileService getFirYcbGenFileService() {
		return firYcbGenFileService;
	}

	public void setFirYcbGenFileService(FirYcbGenFileService firYcbGenFileService) {
		this.firYcbGenFileService = firYcbGenFileService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案START
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public FirAgtrnYcbN1ImportService getFirAgtrnYcbN1ImportService() {
		return firAgtrnYcbN1ImportService;
	}

	public void setFirAgtrnYcbN1ImportService(FirAgtrnYcbN1ImportService firAgtrnYcbN1ImportService) {
		this.firAgtrnYcbN1ImportService = firAgtrnYcbN1ImportService;
	}
	//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案END

	//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位START
	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}

	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
	}
	//mantis：FIR0684，處理人員：DP0706，需求單編號：FIR0684_住火_APS元大續保作業_N+1轉檔新增填寫出單業務員欄位END 
}
