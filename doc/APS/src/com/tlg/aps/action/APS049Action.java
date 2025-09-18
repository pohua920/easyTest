package com.tlg.aps.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.CarReinsurancePlan;
import com.tlg.prpins.service.CarReinsurancePlanService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS049Action extends BaseAction {
	/* mantis：CAR0553，處理人員：DP0706，需求單編號：CAR0553.APS-車險再保註記設定維護功能 start */
	private List<CarReinsurancePlan> devResults = new ArrayList<>();
	private CarReinsurancePlanService carReinsurancePlanService;
	private CarReinsurancePlan carReinsurancePlan;
	private InputStream inputStream;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	private void getStatus() {
		if (getPageInfo().getFilter().get("deleteFlag")!=null 
				&& "BLANK".equals(getPageInfo().getFilter().get("deleteFlag"))) {
			getPageInfo().getFilter().remove("deleteFlag");
		}
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
			formLoad("execute");
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
	public String btnQuery() throws Exception {
		try{
			formLoad("query");
			getStatus();
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "REINSURANCETYPE, CONTRACTNAME");
			getPageInfo().getFilter().put("sortType", "ASC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結至查詢頁面 
	 * @throws Exception */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
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
	
	/** APS049E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** APS049E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	private void query() throws SystemException, Exception {
		Result result = carReinsurancePlanService.findCarReinsurancePlanByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<CarReinsurancePlan>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		this.formLoad("create");
		return Action.SUCCESS;	
	}
	
	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			formLoad("create");
			create();
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("新增失敗\\n"+e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}
	
	/** 新增*/
	private void create() throws SystemException, Exception{
		carReinsurancePlan.setDeleteFlag("Y");
		carReinsurancePlan.setIcreate(getUserInfo().getUserId().toUpperCase());
		carReinsurancePlan.setDcreate(new Date());
		Result result = carReinsurancePlanService.insertCarReinsurancePlan(carReinsurancePlan);
		setMessage(result.getMessage().toString());
	}
	
	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		try {
			formLoad("update");
			if (null == carReinsurancePlan.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
				return Action.INPUT;
			}
			Result result = carReinsurancePlanService.findCarReinsurancePlanByOid(carReinsurancePlan.getOid());
			if (null == result.getResObject()) {
				setMessage(result.getMessage().toString());
			} else {
				carReinsurancePlan = (CarReinsurancePlan) result.getResObject();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("儲存失敗\\n"+e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		carReinsurancePlan.setIupdate(getUserInfo().getUserId().toUpperCase());
		carReinsurancePlan.setDupdate(new Date());
		Result result = carReinsurancePlanService.updateCarReinsurancePlan(carReinsurancePlan);
		setMessage(result.getMessage().toString());
	}
	
	/** APS049E0.jsp 按下下載XLSX，下載XLSX檔案 */
	@SuppressWarnings({ "unchecked", "resource" })
	public String btnGenExcel() throws Exception {
		getPageInfo().getFilter().put("sortBy", "REINSURANCETYPE, CONTRACTNAME");
		getPageInfo().getFilter().put("sortType", "ASC");
		getPageInfo().setPageSize(0);//不分頁
		Result result = carReinsurancePlanService.findCarReinsurancePlanByPageInfo(getPageInfo());
		
		ServletOutputStream out = null;
		if(result!=null) {
			List<CarReinsurancePlan> results = new ArrayList<>();
			results = (List<CarReinsurancePlan>) result.getResObject();
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				XSSFSheet sheet = workbook.createSheet();
				String[] titleArr = { "再保類型","合約名稱","合約期限(起)","合約期限(迄)","業務來源代號","車種代號",
						"險種代號","保額(起)","保額(迄)","分出單號類別","分出單號","車牌","備註","效力註記"};
				XSSFRow rowTitle = sheet.createRow(0);
				for (int i = 0; i < titleArr.length; i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String bidnoTypeStr="";
				for (int i = 0; i < results.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue("1".equals(results.get(i).getReinsurancetype()) ? "合約" : "臨分");//再保類型:1合約2臨分
					row.createCell(1).setCellValue(results.get(i).getContractname());//合約名稱
					row.createCell(2).setCellValue(results.get(i).getPeriodstartdate() != null ? sdf.format(results.get(i).getPeriodstartdate()) : "");//合約期限(起)
					row.createCell(3).setCellValue(results.get(i).getPeriodend() != null ? sdf.format(results.get(i).getPeriodend()) : "");//合約期限(迄)
					row.createCell(4).setCellValue(results.get(i).getBcode());//業務來源代號
					row.createCell(5).setCellValue(results.get(i).getCarkindcode());//車種代號
					row.createCell(6).setCellValue(results.get(i).getKindcode());//險種代號
					row.createCell(7).setCellValue(results.get(i).getAmountS() != null ? results.get(i).getAmountS().toString() :"");//"保額(起)
					row.createCell(8).setCellValue(results.get(i).getAmountE() != null ? results.get(i).getAmountE().toString() :"");//保額(迄)
					bidnoTypeStr="";
					if("1".equals(results.get(i).getBidnotype())){
						bidnoTypeStr = "報價";
					} else if("2".equals(results.get(i).getBidnotype())){
						bidnoTypeStr = "要保";
					} else if("3".equals(results.get(i).getBidnotype())){
						bidnoTypeStr = "保單";
					}	
					row.createCell(9).setCellValue(bidnoTypeStr);//分出單號類別
					row.createCell(10).setCellValue(results.get(i).getBidno());//分出單號
					row.createCell(11).setCellValue(results.get(i).getLicenseno());//車牌
					row.createCell(12).setCellValue(results.get(i).getMark());//備註
					row.createCell(13).setCellValue("Y".equals(results.get(i).getDeleteFlag()) ? "有效" : "無效");//效力註記
				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				byte[] bookByteAry = bos.toByteArray();
				inputStream = new ByteArrayInputStream(bookByteAry);
				
				Date currentTime = new Date();
				SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmm", Locale.TAIWAN);
				String dateTime = timeFormatter.format(currentTime);
				String downFileName = new String(("CarReinsurancePlan_"+dateTime+".xlsx").getBytes("UTF-8"), "ISO8859-1");
		        this.getResponse().reset();
		        this.getResponse().setCharacterEncoding("UTF-8");
		        this.getResponse().setContentType("application/vnd.ms-excel;charset=UTF-8");
		        this.getResponse().addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(downFileName, "ISO8859-1"));
		        byte[] b = new byte[4096];
		        int len;
		        out = this.getResponse().getOutputStream();

		        while((len=inputStream.read(b)) >0){
		        	out.write(b,0,len);
		        }
			} catch (SystemException se) {
				setMessage(se.getMessage());
			} catch (Exception e) {
				getRequest().setAttribute("exception", e);
				throw e;
			} finally {
				out.flush();
		        out.close();
		        inputStream.close();
			}
		}
		return Action.SUCCESS;
	}
	
	public List<CarReinsurancePlan> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<CarReinsurancePlan> devResults) {
		this.devResults = devResults;
	}

	public CarReinsurancePlanService getCarReinsurancePlanService() {
		return carReinsurancePlanService;
	}

	public void setCarReinsurancePlanService(CarReinsurancePlanService carReinsurancePlanService) {
		this.carReinsurancePlanService = carReinsurancePlanService;
	}

	public CarReinsurancePlan getCarReinsurancePlan() {
		return carReinsurancePlan;
	}

	public void setCarReinsurancePlan(CarReinsurancePlan carReinsurancePlan) {
		this.carReinsurancePlan = carReinsurancePlan;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}



}


