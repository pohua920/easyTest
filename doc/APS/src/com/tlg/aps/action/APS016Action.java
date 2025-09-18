package com.tlg.aps.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firPanhsinFeedbackFile.FirPanhsinRenewalFileService;
import com.tlg.aps.bs.firPanhsinFeedbackFile.ProcessPanhsinFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.FirPahsinRenewalVo;
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
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS016Action extends BaseAction {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程
	 * mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start */
	
	private ConfigUtil configUtil;
	private FirPanhsinRenewalFileService firPanhsinRenewalFileService;
	private FirAgtrnBatchMainService firAgtrnBatchMainService;
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtTocoreInsuredService firAgtTocoreInsuredService;
	private ProcessPanhsinFileService processPanhsinFileService;
	private FirBatchInfoService firBatchInfoService;
	
	private FirAgtrnBatchMain firAgtrnBatchMain;
	private FirAgtrnBatchDtl firAgtrnBatchDtl;
	private Aps016DetailVo aps016DetailVo;
	
	//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	private List<FirPahsinRenewalVo> devResults;
	private List<Aps016DetailVo> dtlDevResults;
	
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;
	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	
	private String type;
	private String batchNo;
	private InputStream inputStream;
	private String fileName;
	
	
	
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
	
	/** APS016E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			if(!"N".equals(type)) {
				getPageInfo().setCurrentPage(1);				
			}
			getPageInfo().getFilter().put("deleteFlag", "N");
			getPageInfo().getFilter().put("businessnature", "I99065");
			//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
			getPageInfo().getFilter().put("sortBy", "MAIN.DCREATE");
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
	
	/** APS016E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS016E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS016E0.jsp頁面按下主檔批次號碼資料，查詢明細資料 */
	@SuppressWarnings("unchecked")
	public String lnkGoDetailQuery() throws Exception {
		String forward = "input";
		try {
			if("N".equals(type)) {
				getDPageInfo().getFilter().put("batchNo",batchNo);
			}else {
				getDPageInfo().getFilter().put("batchNo",firAgtrnBatchMain.getBatchNo());
				getDPageInfo().setCurrentPage(1);
			}
			getDPageInfo().getFilter().put("sortBy","BATCH_SEQ");
			getDPageInfo().getFilter().put("sortType","ASC");
			Result result = dtlQuery();
			if(result.getMessage()!=null) {
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
	
	/** APS016E1.jsp(明細)分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** APS016E1.jsp(明細)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS016E1.jsp(明細)分頁資料中， 查詢結果點選上下三角型排序 */
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

	/** APS016E1.jsp 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		try {
			if (null == aps016DetailVo.getBatchNo() || null == aps016DetailVo.getBatchSeq()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,Object> params = new HashMap<>();
				params.put("batchNo", aps016DetailVo.getBatchNo());
				params.put("batchSeq", aps016DetailVo.getBatchSeq());
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
	
	/** APS016U0.jsp按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			Map<String,Object> params = new HashMap<>();
			params.put("batchNo", aps016DetailVo.getBatchNo());
			params.put("batchSeq", aps016DetailVo.getBatchSeq());
			updateQuery(params);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS016E0.jsp 按下下載XLS，下載XLS檔案 */
	@SuppressWarnings({ "unchecked", "resource" })
	public String btnGenExcel() throws Exception {
		try{
			String excelBatchNo = firAgtrnBatchMain.getBatchNo();
			Result result = firAgtrnBatchDtlService.findFirAgtrnBatchDtlForExcel(excelBatchNo);
			if(result.getResObject()!=null) {
				List<Aps016DetailVo> dataList =  (List<Aps016DetailVo>) result.getResObject();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
				String[] titleArr = {"批次號碼","批次序號","資料狀態","續保單號","受理編號","保單生效日","要保人ID","要保人姓名","被保險人ID",
						"被保險人姓名","標的物郵遞區號","標的物地址","外牆","屋頂","坪數","總樓層數","建築年份","建物等級","火險保額","火險保費","地震保額",
						"地震保費","板信總保費","歸屬單位","服務人員","服務人員姓名","業務員登錄證字號","分行代號","異常訊息","提醒訊息"};
				XSSFRow rowTitle = sheet.createRow(0);
				for(int i=0;i<titleArr.length;i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				for (int i = 0; i < dataList.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(dataList.get(i).getBatchNo());
					row.createCell(1).setCellValue(dataList.get(i).getBatchSeq());
					row.createCell(2).setCellValue(dataList.get(i).getDataStatus());
					row.createCell(3).setCellValue(dataList.get(i).getOldpolicyno());
					row.createCell(4).setCellValue(dataList.get(i).getOrderseq());
					row.createCell(5).setCellValue(adToRoc(sdf.format(dataList.get(i).getStartdate())));
					row.createCell(6).setCellValue(dataList.get(i).getIdentifynumber());
					row.createCell(7).setCellValue(dataList.get(i).getInsuredname());
					row.createCell(8).setCellValue(dataList.get(i).getIdentifynumber1());
					row.createCell(9).setCellValue(dataList.get(i).getInsuredname1());
					row.createCell(10).setCellValue(dataList.get(i).getAddresscode());
					row.createCell(11).setCellValue(dataList.get(i).getAddressdetailinfo());
					row.createCell(12).setCellValue(dataList.get(i).getWallmaterial());
					row.createCell(13).setCellValue(dataList.get(i).getRoofmaterial());
					row.createCell(14).setCellValue(dataList.get(i).getBuildarea());
					row.createCell(15).setCellValue(dataList.get(i).getSumfloors());
					row.createCell(16).setCellValue(dataList.get(i).getBuildyears());
					row.createCell(17).setCellValue(dataList.get(i).getStructure());
					row.createCell(18).setCellValue(dataList.get(i).getAmountF());
					row.createCell(19).setCellValue(dataList.get(i).getPremiumF());
					row.createCell(20).setCellValue(dataList.get(i).getAmountQ());
					row.createCell(21).setCellValue(dataList.get(i).getPremiumQ());
					row.createCell(22).setCellValue(dataList.get(i).getPremiumT());
					row.createCell(23).setCellValue(dataList.get(i).getComcode());
					row.createCell(24).setCellValue(dataList.get(i).getHandler1code());
					row.createCell(25).setCellValue(dataList.get(i).getUsername());
					row.createCell(26).setCellValue(dataList.get(i).getHandleridentifynumber());
					row.createCell(27).setCellValue(dataList.get(i).getExtracomcode());
					/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
					row.createCell(28).setCellValue(dataList.get(i).getCheckErrMsg());
					row.createCell(29).setCellValue(dataList.get(i).getCheckWarnMsg());
					/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
				}
				
				Map<String,String> params = new HashMap<>();
				params.put("prgId", "FIR_AGT_BOPRN_XLSPD");
				result = firBatchInfoService.findFirBatchInfoByUK(params);
				if(result.getResObject() == null) {
					setMessage("未設定檔案密碼(FIR_BATCH_INFO)，請聯繫系統管理員。");
					query();
					return "input";
				}
				
				File fileFolder = new File(configUtil.getString("tempFolder"));
				if(!fileFolder.exists()) {
					fileFolder.mkdirs();
				}
				
				sdf = new SimpleDateFormat("yyyyMM");
				fileName = excelBatchNo+sdf.format(new Date())+".xlsx";
				String filepath = configUtil.getString("tempFolder") + fileName;
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close();

				//檔案加密
				FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
				POIFSFileSystem fs = new POIFSFileSystem();
				EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
				Encryptor enc = info.getEncryptor();
				enc.confirmPassword(firBatchInfo.getMailTo());
				OPCPackage opc = OPCPackage.open(new File(filepath), PackageAccess.READ_WRITE);
				OutputStream os = enc.getDataStream(fs);
				opc.save(os);
				opc.close();
				FileOutputStream fos = new FileOutputStream(filepath);
				fs.writeFilesystem(fos);
				fos.flush();
				fos.close();
				
				this.inputStream = new DeleteAfterDownloadFileInputStream(new File(filepath));
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	

	/** APS004E0.jsp頁面按下手動執行鍵,開始執行 **/
	public String btnExecuteBoprn() throws Exception {
		try{
			Result result = firPanhsinRenewalFileService.runToReceiveData(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_BOPRN");
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
	private void query() throws SystemException, Exception {
		parameterHandler();
		//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
		Result result = firAgtrnBatchMainService.findBatchMainForBoprnByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
			devResults = (List<FirPahsinRenewalVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	//明細資料查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		Result result = new Result();
		result = firAgtrnBatchDtlService.findFirAgtrnBatchDtlForDetail(getDPageInfo());
		if (null != result.getResObject()) {
			dtlDevResults = (List<Aps016DetailVo>) result.getResObject();
		}else {
			Map<String,String> params = new HashMap<>();
			params.put("batchNo", firAgtrnBatchMain.getBatchNo());
			Result result2 = firAgtrnBatchMainService.findFirAgtrnBatchMainByUk(params);
			
			firAgtrnBatchMain = (FirAgtrnBatchMain) result2.getResObject();
			String remark = firAgtrnBatchMain.getRemark();
			if(!StringUtil.isSpace(remark)) {
				setMessage("資料檢核異常，查無符合資料:"+remark);
			}else {
				setMessage(result.getMessage().toString());
			}
		}
		return result;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		processPanhsinFileService.updateFirAgtTocoreAndAgtrnBatchDtl(aps016DetailVo, getUserInfo().getUserId().toUpperCase());
		if(aps016DetailVo.getLocking().equals("N")) {
			setMessage("儲存成功");
		}else {
			setMessage("儲存及鎖定成功");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result updateQuery(Map<String,Object> params) throws SystemException, Exception {
		
		Result result = firAgtrnBatchDtlService.findInsuredDataJoinTocoreMain(params);
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
			return result;
		} else {
			aps016DetailVo = (Aps016DetailVo) result.getResObject();
		}
		params.put("sortBy", "INSUREDFLAG, INSURED_SEQ");
		result = firAgtTocoreInsuredService.findFirAgtTocoreInsuredByParams(params);
		if(result.getResObject()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			List<FirAgtTocoreInsured> insuredList = (List<FirAgtTocoreInsured>) result.getResObject();
			for(int i=0;i<insuredList.size();i++) {
				if(i==0) {
					aps016DetailVo.setInsuredname1(insuredList.get(i).getInsuredname());
					aps016DetailVo.setIdentifynumber1(insuredList.get(i).getIdentifynumber());
					aps016DetailVo.setInsurednature1(insuredList.get(i).getInsurednature());
					aps016DetailVo.setIdentifytype1(insuredList.get(i).getIdentifytype());
					aps016DetailVo.setPhonenumber1(insuredList.get(i).getPhonenumber());
					aps016DetailVo.setMobile1(insuredList.get(i).getMobile());
					aps016DetailVo.setPostcode1(insuredList.get(i).getPostcode());
					aps016DetailVo.setPostaddress1(insuredList.get(i).getPostaddress());
					aps016DetailVo.setDomicile1(insuredList.get(i).getDomicile());
					aps016DetailVo.setCountryename1(insuredList.get(i).getCountryename());
					aps016DetailVo.setIshighdengeroccupation1(insuredList.get(i).getIshighdengeroccupation());
					aps016DetailVo.setBirthday1Check(insuredList.get(i).getBirthday()==null?"":adToRoc(sdf.format(insuredList.get(i).getBirthday())));
					aps016DetailVo.setListedcabinetcompany1(insuredList.get(i).getListedcabinetcompany());
					aps016DetailVo.setHeadname1(insuredList.get(i).getHeadname());
				}else if(i==1){
					aps016DetailVo.setInsuredname2(insuredList.get(i).getInsuredname());
					aps016DetailVo.setIdentifynumber2(insuredList.get(i).getIdentifynumber());
					aps016DetailVo.setInsurednature2(insuredList.get(i).getInsurednature());
					aps016DetailVo.setIdentifytype2(insuredList.get(i).getIdentifytype());
					aps016DetailVo.setPhonenumber2(insuredList.get(i).getPhonenumber());
					aps016DetailVo.setMobile2(insuredList.get(i).getMobile());
					aps016DetailVo.setPostcode2(insuredList.get(i).getPostcode());
					aps016DetailVo.setPostaddress2(insuredList.get(i).getPostaddress());
					aps016DetailVo.setDomicile2(insuredList.get(i).getDomicile());
					aps016DetailVo.setCountryename2(insuredList.get(i).getCountryename());
					aps016DetailVo.setIshighdengeroccupation2(insuredList.get(i).getIshighdengeroccupation());
					aps016DetailVo.setBirthday2Check(insuredList.get(i).getBirthday()==null?"":adToRoc(sdf.format(insuredList.get(i).getBirthday())));
					aps016DetailVo.setListedcabinetcompany2(insuredList.get(i).getListedcabinetcompany());
					aps016DetailVo.setHeadname2(insuredList.get(i).getHeadname());
				}
			}
		}
		return result;
	}
	
	public String adToRoc(String date) throws Exception {
		if(!StringUtil.isSpace(date)) {
			date = String.valueOf((Integer.parseInt(date.substring(0,4))-1911))
					+date.substring(4,7)+date.substring(7);
			if(date.length()<9) {
				date = "0" + date;
			}
			return date;
		}
		return date;
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

	// 轉換民國年為西元年 
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public String btnFixData() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", firAgtrnBatchMain.getBatchNo());
		params.put("fixUserNull", "Y");
		params.put("sfFlag", "N");
		params.put("iupdateNull", "Y");
		int fixCount = firAgtrnBatchDtlService.countFirAgtrnBatchDtl(params);

		if (fixCount > 0) {
			setMessage("尚有"+fixCount+"筆資料未修改完成，無法進行整批鎖定。");
			query();
			return Action.SUCCESS;
		}
		
		params.remove("iupdateNull");
		Result result = firAgtrnBatchDtlService.findFirAgtrnBatchDtlByParams(params);
		if(result.getResObject()!=null) {
			List<FirAgtrnBatchDtl> fixList = (List<FirAgtrnBatchDtl>) result.getResObject();
			Date fixDate = new Date();
			for(FirAgtrnBatchDtl dtl : fixList) {
				dtl.setFixUser(getUserInfo().getUserId().toUpperCase());
				dtl.setFixDate(fixDate);
				firAgtrnBatchDtlService.updateFirAgtrnBatchDtl(dtl);
			}
		}
		setMessage("整批鎖定成功");
		
		return Action.SUCCESS;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/

	public FirPanhsinRenewalFileService getFirPanhsinRenewalFileService() {
		return firPanhsinRenewalFileService;
	}

	public void setFirPanhsinRenewalFileService(FirPanhsinRenewalFileService firPanhsinRenewalFileService) {
		this.firPanhsinRenewalFileService = firPanhsinRenewalFileService;
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

	public ProcessPanhsinFileService getProcessPanhsinFileService() {
		return processPanhsinFileService;
	}

	public void setProcessPanhsinFileService(ProcessPanhsinFileService processPanhsinFileService) {
		this.processPanhsinFileService = processPanhsinFileService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	public List<FirPahsinRenewalVo> getDevResults() {
		return devResults;
	}

	//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	public void setDevResults(List<FirPahsinRenewalVo> devResults) {
		this.devResults = devResults;
	}

	public List<Aps016DetailVo> getDtlDevResults() {
		return dtlDevResults;
	}

	public void setDtlDevResults(List<Aps016DetailVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}

	public FirAgtrnBatchMain getFirAgtrnBatchMain() {
		return firAgtrnBatchMain;
	}

	public void setFirAgtrnBatchMain(FirAgtrnBatchMain firAgtrnBatchMain) {
		this.firAgtrnBatchMain = firAgtrnBatchMain;
	}
	
	public FirAgtrnBatchDtl getFirAgtrnBatchDtl() {
		return firAgtrnBatchDtl;
	}

	public void setFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl) {
		this.firAgtrnBatchDtl = firAgtrnBatchDtl;
	}

	public Aps016DetailVo getAps016DetailVo() {
		return aps016DetailVo;
	}

	public void setAps016DetailVo(Aps016DetailVo aps016DetailVo) {
		this.aps016DetailVo = aps016DetailVo;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return inputStream;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
}
