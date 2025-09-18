package com.tlg.aps.bs.mobclaimService.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.mobclaimService.MobSubmitAndPrintService;
import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Fetclaimcomm;
import com.tlg.prpins.entity.Fetclaimkind;
import com.tlg.prpins.entity.Fetclaimmain;
import com.tlg.prpins.entity.Fetclaimpay;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.prpins.entity.Tmpfetclaimkind;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.prpins.entity.Tmpfetclaimpay;
import com.tlg.prpins.service.FetclaimcommService;
import com.tlg.prpins.service.FetclaimkindService;
import com.tlg.prpins.service.FetclaimmainService;
import com.tlg.prpins.service.FetclaimpayService;
import com.tlg.prpins.service.TmpfetclaimcommService;
import com.tlg.prpins.service.TmpfetclaimkindService;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.prpins.service.TmpfetclaimpayService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MobSubmitAndPrintServiceImpl implements MobSubmitAndPrintService {

	private static final Logger logger = Logger.getLogger(MobSubmitAndPrintServiceImpl.class);
	private FetclaimmainService fetclaimmainService;
	private FetclaimpayService fetclaimpayService;
	private FetclaimcommService fetclaimcommService;
	private FetclaimkindService fetclaimkindService;
	private TmpfetclaimmainService tmpfetclaimmainService;
	private TmpfetclaimpayService tmpfetclaimpayService;
	private TmpfetclaimcommService tmpfetclaimcommService;
	private TmpfetclaimkindService tmpfetclaimkindService;
	private ConfigUtil configUtil;
	
	@Override
	public Result submitClaimData(String userId, String dataDate) throws Exception {
		//先檢查此資料年月資料是否已存在正式資料表中，無資料可直接提交，若有資料則先判斷資料是否已轉核心，若已轉核心不允許重複提交。
		Map params = new HashMap<>();
		params.put("wda00", dataDate);
		params.put("nclaimNotNull", "Y");
		int nclaimCount = fetclaimmainService.countFetclaimmain(params);
		if (nclaimCount != 0) {// 若查詢有資料表示此資料年月已轉進核心，不能再重新轉檔。
			return this.getReturnResult("資料年月" + dataDate + "資料已轉入核心，不能重新提交!");
		}
		Result result = new Result();
		
		//查詢此資料年月是否已存在，若已存在正式資料，則將上次提交資料刪除重新轉入TMP資料
		try {
			result = fetclaimmainService.findFetclaimmainByParams(params);
			if(result.getResObject() != null) {
				List<Fetclaimmain> mainList =  (List<Fetclaimmain>) result.getResObject();
				for(Fetclaimmain main : mainList) {
					fetclaimmainService.removeFetclaimmain(main.getOid());
				}
			}
			params.clear();
			params.put("wde00", dataDate);
			result = fetclaimpayService.findFetclaimpayByParams(params);
			if(result.getResObject() != null) {
				List<Fetclaimpay> payList =  (List<Fetclaimpay>) result.getResObject();
				for(Fetclaimpay pay : payList) {
					fetclaimpayService.removeFetclaimpay(pay.getOid());
				}
			}
			params.clear();
			params.put("wdf00", dataDate);
			result = fetclaimcommService.findFetclaimcommByParams(params);
			if(result.getResObject() != null) {
				List<Fetclaimcomm> commList =  (List<Fetclaimcomm>) result.getResObject();
				for(Fetclaimcomm comm : commList) {
					fetclaimcommService.removeFetclaimcomm(comm.getOid());
				}
			}
			params.clear();
			params.put("wdc00", dataDate);
			result = fetclaimkindService.findFetclaimkindByParams(params);
			if(result.getResObject() != null) {
				List<Fetclaimkind> kindList =  (List<Fetclaimkind>) result.getResObject();
				for(Fetclaimkind kind : kindList) {
					fetclaimkindService.removeFetclaimkind(kind.getOid());
				}
			}
		}catch(Exception e) {
			logger.error("行動裝置險理賠資料提交作業:刪除資料發生錯誤:"+e.toString());
			e.printStackTrace();
			return this.getReturnResult("資料年月" + dataDate + ":刪除已有的正式資料發生錯誤!");
		}
		
		
		//將資料從TMP寫入正式資料TABLE
		try {
			params.clear();
			params.put("wda00", dataDate);
			result = tmpfetclaimmainService.findTmpfetclaimmainByParams(params);
			if(result.getResObject() == null) {
				return this.getReturnResult("資料年月" + dataDate + "不存在TMPFETCLAIMMAIN資料，無法提交!");
			}
			List<Tmpfetclaimmain> tmainList = (List<Tmpfetclaimmain>) result.getResObject();
			for(Tmpfetclaimmain tmain : tmainList) {
				Fetclaimmain main = new Fetclaimmain();
				BeanUtils.copyProperties(tmain, main);
				main.setWda54(userId);
				fetclaimmainService.insertFetclaimmain(main);
			}
			
			params.clear();
			params.put("wde00", dataDate);
			result = tmpfetclaimpayService.findTmpfetclaimpayByParams(params);
			if(result.getResObject() == null) {
				return this.getReturnResult("資料年月" + dataDate + "不存在TMPFETCLAIMPAY資料，無法提交!");
			}
			List<Tmpfetclaimpay> tpayList = (List<Tmpfetclaimpay>) result.getResObject();
			for(Tmpfetclaimpay tpay : tpayList) {
				Fetclaimpay pay = new Fetclaimpay();
				BeanUtils.copyProperties(tpay, pay);
				fetclaimpayService.insertFetclaimpay(pay);
			}

			params.clear();
			params.put("wdf00", dataDate);
			result = tmpfetclaimcommService.findTmpfetclaimcommByParams(params);
			if(result.getResObject() == null) {
				return this.getReturnResult("資料年月" + dataDate + "不存在TMPFETCLAIMCOMM資料，無法提交!");
			}
			List<Tmpfetclaimcomm> tcommList = (List<Tmpfetclaimcomm>) result.getResObject();
			for(Tmpfetclaimcomm tcomm : tcommList) {
				Fetclaimcomm comm = new Fetclaimcomm();
				BeanUtils.copyProperties(tcomm, comm);
				fetclaimcommService.insertFetclaimcomm(comm);
			}

			params.clear();
			params.put("wdc00", dataDate);
			result = tmpfetclaimkindService.findTmpfetclaimkindByParams(params);
			if(result.getResObject() == null) {
				return this.getReturnResult("資料年月" + dataDate + "不存在TMPFETCLAIMKIND資料，無法提交!");
			}
			List<Tmpfetclaimkind> tkindList = (List<Tmpfetclaimkind>) result.getResObject();
			for(Tmpfetclaimkind tkind : tkindList) {
				Fetclaimkind kind = new Fetclaimkind();
				BeanUtils.copyProperties(tkind, kind);
				fetclaimkindService.insertFetclaimkind(kind);
			}
		}catch(Exception e) {
			logger.error("行動裝置險理賠資料提交作業:新增資料發生錯誤:"+e.toString());
			e.printStackTrace();
			return this.getReturnResult("資料年月" + dataDate + ":新增正式資料發生錯誤!");
		}
		return this.getReturnResult("資料年月" + dataDate + ":資料提交成功!");
	}
	
	@Override
	public Result genClaimListFile(Map params) throws SystemException, Exception {
		Result result = fetclaimmainService.findForClaimListByParams(params);
		if(null == result.getResObject()) {
			return getReturnResult("查無此資料年月資料，無法產生理賠清單檔案!");
		}
		List<Aps046ResultVo> claimList = (List<Aps046ResultVo>) result.getResObject();
		
		String tempFolder = configUtil.getString("tempFolder");
		File folderPath = new File(tempFolder);
		if (!folderPath.exists()) {
			folderPath.mkdirs();
		}
		
		String filename = "CLAIMLIST.XLSX";
		String filepath = tempFolder + filename;
		File file = new File(filepath);
		FileOutputStream fileOut;
		
		//清單資料欄位標題
		String[] title = { "序號", "賠案號碼", "維修單號", "保單號碼", "賠款金額", "賠付對象", "總行代碼", "分行名稱", "銀行帳號", "理賠經辦" };
		try {
			// 建立workbook格式
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				
				//設定欄位邊框
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下邊框
				cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左邊框
				cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上邊框
				cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右邊框
				cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平置中
				
				XSSFSheet sheet = workbook.createSheet("理賠計算書清單");
				createTitle(sheet, title, cellStyle, 0, 0);
				int rownumT = 1;
				
				int wde22Tot = 0;
				int dataCount = claimList.size();
				
				for (int i = 0; i < claimList.size(); i++) {
					Aps046ResultVo claimData = claimList.get(i);
					
					//計算總筆數、賠付總金額
					String wde22 = claimData.getWde22() == null?"0":claimData.getWde22();
					wde22Tot = wde22Tot + Integer.parseInt(wde22);
					rownumT = createListCell(claimData, sheet, rownumT, cellStyle);
				}

				// 最後放統計資料及簽核欄位
				sheet.addMergedRegion(new CellRangeAddress(rownumT, rownumT, 0, title.length - 1));
				XSSFRow rowDataCount = sheet.createRow(rownumT);
				rowDataCount.createCell(0).setCellValue("賠付案件總筆數     " + dataCount + "筆");
				sheet.addMergedRegion(new CellRangeAddress(rownumT+1, rownumT+1, 0, title.length - 1));
				XSSFRow rowWde22Tot = sheet.createRow(rownumT + 1);
				rowWde22Tot.createCell(0).setCellValue("賠付案件總金額     " + wde22Tot + "元");
				
				String[] jobTitle = { "總經理", "處長", "部長", "科長", "經辦"};
				createTitle(sheet, jobTitle, cellStyle, rownumT + 4, 5);
				
				//最後簽核欄位合併
				sheet.addMergedRegion(new CellRangeAddress(rownumT+5, rownumT+7, 5, 5));
				sheet.addMergedRegion(new CellRangeAddress(rownumT+5, rownumT+7, 6, 6));
				sheet.addMergedRegion(new CellRangeAddress(rownumT+5, rownumT+7, 7, 7));
				sheet.addMergedRegion(new CellRangeAddress(rownumT+5, rownumT+7, 8, 8));
				sheet.addMergedRegion(new CellRangeAddress(rownumT+5, rownumT+7, 9, 9));
				String[] signTitle = { "", "", "", "", ""};
				createTitle(sheet, signTitle, cellStyle, rownumT + 5, 5);
				createTitle(sheet, signTitle, cellStyle, rownumT + 6, 5);
				createTitle(sheet, signTitle, cellStyle, rownumT + 7, 5);
				
				fileOut = new FileOutputStream(file);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			}
		} catch (Exception e) {
			logger.error("genRenewListFile error:", e);
			return getReturnResult("產生理賠清單EXCEL檔案發生錯誤:"+e.toString()) ;
		}
		result.setMessage(null);
		result.setResObject(file);
		return result;
	}
	
	private Integer createListCell(Aps046ResultVo claimData, XSSFSheet sheet, int rownum, XSSFCellStyle cellStyle) throws Exception {
		//續保明細表內容
		XSSFRow row = sheet.createRow(rownum); //建立列
		setStyle(cellStyle,row,0).setCellValue(rownum);
		setStyle(cellStyle,row,1).setCellValue(claimData.getWda02());
		setStyle(cellStyle,row,2).setCellValue(claimData.getReporder());
		setStyle(cellStyle,row,3).setCellValue(claimData.getWda04());
		setStyle(cellStyle,row,4).setCellValue(claimData.getWde22());
		setStyle(cellStyle,row,5).setCellValue(claimData.getWde07());
		setStyle(cellStyle,row,6).setCellValue(claimData.getWde08());
		setStyle(cellStyle,row,7).setCellValue(claimData.getWde09());
		setStyle(cellStyle,row,8).setCellValue(claimData.getWde10());
		setStyle(cellStyle,row,9).setCellValue(claimData.getWda54());
		rownum++;
		return rownum;
	}
	
	private XSSFCell setStyle(XSSFCellStyle setBorder, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellStyle(setBorder);
		return cell;
	}
	
	private XSSFSheet createTitle(XSSFSheet sheet, String[] title, XSSFCellStyle cellStyle, int rowNum, int startCell) {
		XSSFRow rowTitle = sheet.createRow(rowNum);
		for (int i = 0; i < title.length; i++) {
			setStyle(cellStyle,rowTitle,i+startCell).setCellValue(title[i]);
		}
		return sheet;
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FetclaimmainService getFetclaimmainService() {
		return fetclaimmainService;
	}

	public void setFetclaimmainService(FetclaimmainService fetclaimmainService) {
		this.fetclaimmainService = fetclaimmainService;
	}

	public FetclaimpayService getFetclaimpayService() {
		return fetclaimpayService;
	}

	public void setFetclaimpayService(FetclaimpayService fetclaimpayService) {
		this.fetclaimpayService = fetclaimpayService;
	}

	public FetclaimcommService getFetclaimcommService() {
		return fetclaimcommService;
	}

	public void setFetclaimcommService(FetclaimcommService fetclaimcommService) {
		this.fetclaimcommService = fetclaimcommService;
	}

	public FetclaimkindService getFetclaimkindService() {
		return fetclaimkindService;
	}

	public void setFetclaimkindService(FetclaimkindService fetclaimkindService) {
		this.fetclaimkindService = fetclaimkindService;
	}

	public TmpfetclaimmainService getTmpfetclaimmainService() {
		return tmpfetclaimmainService;
	}

	public void setTmpfetclaimmainService(TmpfetclaimmainService tmpfetclaimmainService) {
		this.tmpfetclaimmainService = tmpfetclaimmainService;
	}

	public TmpfetclaimpayService getTmpfetclaimpayService() {
		return tmpfetclaimpayService;
	}

	public void setTmpfetclaimpayService(TmpfetclaimpayService tmpfetclaimpayService) {
		this.tmpfetclaimpayService = tmpfetclaimpayService;
	}

	public TmpfetclaimcommService getTmpfetclaimcommService() {
		return tmpfetclaimcommService;
	}

	public void setTmpfetclaimcommService(TmpfetclaimcommService tmpfetclaimcommService) {
		this.tmpfetclaimcommService = tmpfetclaimcommService;
	}

	public TmpfetclaimkindService getTmpfetclaimkindService() {
		return tmpfetclaimkindService;
	}

	public void setTmpfetclaimkindService(TmpfetclaimkindService tmpfetclaimkindService) {
		this.tmpfetclaimkindService = tmpfetclaimkindService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
}
