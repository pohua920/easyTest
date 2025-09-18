/**
 * 20210222
 * mantis：FIR0220，處理人員：BJ016，需求單編號：FIR0220 中信代理投保通知處理
 * */
package com.tlg.aps.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.dms.entity.Prpdexch;
import com.tlg.dms.service.PrpdexchService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpdnewcode;
import com.tlg.prpins.service.PrpdnewcodeService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS019Action extends BaseAction {

	private List<Prpdexch> devResults = new ArrayList<Prpdexch>();
	
	private PrpdexchService prpdexchService;
	private PrpdnewcodeService prpdnewcodeService;
	
	// 下拉
	private Map<String, String> currencyMap = new LinkedHashMap<String, String>();// 類別下拉
	
	private Prpdexch prpdexch;
	
	private File upload;//上傳的檔案
	private String uploadContentType;//檔案型態
	private String uploadFileName; //檔案名稱
	//mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正
	private String batchExchdate; //批次匯率日期
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
	
	/** 載入畫面下拉資料 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void formLoad(String type, String currency) throws SystemException, Exception {
		try {
			Map params = new HashMap();
			params.put("codetype", "CurrencyM");
			params.put("validstatus", "1");
			Result result = null;
			if(type.equals("create") || type.equals("query")) {
				result = prpdnewcodeService.findPrpdnewcodeByParams(params);
				
			}else if(type.equals("update")) {
				params.put("codecode", currency);
				result = prpdnewcodeService.findPrpdnewcodeByParams(params);
			}
			
			if(result != null && result.getResObject() != null) {
				if(type.equals("query")) {
					currencyMap.put("*", "全選");
				}
				List<Prpdnewcode> searchResult = (List<Prpdnewcode>)result.getResObject();
				for(Prpdnewcode entity : searchResult) {
					currencyMap.put(entity.getCodecode(), entity.getCodecode() + "-" + entity.getCodecname());
				}
			}else {
				currencyMap.put("*****", "查無資料");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/** 參數處理 */
	private void parameterHandler() throws SystemException, Exception {
		String value = (String)getPageInfo().getFilter().get("basecurrency");
		if("*".equals(value)) {
			getPageInfo().getFilter().remove("basecurrency");
		}
		
//		value = (String)getPageInfo().getFilter().get("strExchdate");
//		if(value.length() > 0) {
//			try {
//				Date exchdate = new SimpleDateFormat("yyyy/MM/dd").parse(value);
//				getPageInfo().getFilter().put("exchdate", exchdate);
//			}catch(Exception e) {
//				getPageInfo().getFilter().put("exchdate", new Date());
//			}
//		}else {
//			getPageInfo().getFilter().remove("exchdate");
//		}
	}

	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			
			formLoad("query","");

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoCreate() throws Exception {
		try {
			System.out.println("lnkGoExecute.....");
			formLoad("create","");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String lnkGoEdit() throws Exception {
		try {
			Map params = new HashMap();
			if(prpdexch.getExchdate() == null) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("exchdate", SDF.format(prpdexch.getExchdate()));
			}
			
			if(prpdexch.getBasecurrency() == null || prpdexch.getBasecurrency().length() <= 0) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("basecurrency", prpdexch.getBasecurrency());
			}
			
			if(prpdexch.getExchcurrency() == null || prpdexch.getExchcurrency().length() <= 0) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("exchcurrency", prpdexch.getExchcurrency());
			}
			
			Result result = this.prpdexchService.findPrpdexchByUK(params);
			if(result != null && result.getResObject() != null) {
				this.prpdexch = (Prpdexch)result.getResObject();
				
				this.formLoad("update", this.prpdexch.getBasecurrency());
			}else {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
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
	public String btnQuery() throws Exception {
		try{
			formLoad("query","");
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "exchdate ");
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
	
	public String btnCreate() throws Exception {
		try{
			prpdexch.setExchcurrency("NTD");
			prpdexch.setCreateuser(getUserInfo().getUserId().toUpperCase());
			prpdexch.setCreatedate(new Date());
			this.prpdexchService.insertPrpdexch(prpdexch);
			
			setMessage("新增成功");
			
			prpdexch = new Prpdexch();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			formLoad("create","");
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public String btnBatchUpload() throws Exception {
		try{
			if (upload != null) {
//				File destFile  = new File("D:\\temp\\testRfr\\", uploadFileName);
//				FileUtils.copyFile(upload, destFile);
				/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 start*/
				/*尋找匯率日期是否已經作過批次匯入，如果有將資料刪除----START*/
				Map params = new HashMap();
				params.put("strExchdate", batchExchdate);
				/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 end*/
				Result result = this.prpdexchService.findPrpdexchByParams(params);
				if(result != null && result.getResObject() != null) {
					List<Prpdexch> list = (List<Prpdexch>)result.getResObject();
					for(Prpdexch entity : list) {
						this.prpdexchService.removePrpdexch(entity);
					}
				}
				/*尋找今天是否已經作過批次匯入，如果有將資料刪除----END*/
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(upload), "UTF-8"));
				String str;
				String[] arrData;
				BigDecimal bdBuy = null;
				BigDecimal bdSold = null;
				BigDecimal bdExchrate = null;
				
				int lineNo = 1;
				Prpdexch prpdexch = null;
				while ((str = reader.readLine()) != null) {
					if(lineNo == 1) {
						lineNo++;
						continue;//第一行是標題
					}
					prpdexch = new Prpdexch();
					prpdexch.setExchcurrency("NTD");
					//mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正
					prpdexch.setExchdate(SDF.parse(batchExchdate));
					bdBuy = BigDecimal.ZERO;
					bdSold = BigDecimal.ZERO;
					arrData = str.split(",", 21); //設定欄位數量
					if(arrData[0].length() > 0) {
						prpdexch.setBasecurrency(arrData[0]);
					}
					if(arrData[3].length() > 0) {
						prpdexch.setSpotbuyrate(arrData[3]);
						bdBuy = new BigDecimal(arrData[3]);
					}
					if(arrData[13].length() > 0) {
						prpdexch.setSpotsoldrate(arrData[13]);
						bdSold = new BigDecimal(arrData[13]);
					}
					bdExchrate = bdBuy.add(bdSold).divide(new BigDecimal("2")).setScale(4, BigDecimal.ROUND_HALF_UP);
					prpdexch.setExchrate(bdExchrate);
					prpdexch.setCreateuser(getUserInfo().getUserId().toUpperCase());
					prpdexch.setCreatedate(new Date());
					this.prpdexchService.insertPrpdexch(prpdexch);
				}
				setMessage("執行成功");
			}else {
				setMessage("無上傳檔案");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			formLoad("create","");
		}
		return Action.SUCCESS;
	}
	
	public String btnUpdate() throws Exception {
		try{
			Map params = new HashMap();
			if(prpdexch.getExchdate() == null) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("exchdate", SDF.format(prpdexch.getExchdate()));
			}
			
			if(prpdexch.getBasecurrency() == null || prpdexch.getBasecurrency().length() <= 0) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("basecurrency", prpdexch.getBasecurrency());
			}
			
			if(prpdexch.getExchcurrency() == null || prpdexch.getExchcurrency().length() <= 0) {
				setMessage("查無資料");
				formLoad("query", "");
				query();
				return "returnQuery";
			}else {
				params.put("exchcurrency", prpdexch.getExchcurrency());
			}
			Result result = this.prpdexchService.findPrpdexchByUK(params);
			if(result != null && result.getResObject() != null) {
				Prpdexch edit = (Prpdexch)result.getResObject();
				edit.setSpotbuyrate(this.prpdexch.getSpotbuyrate());
				edit.setSpotsoldrate(this.prpdexch.getSpotsoldrate());
				edit.setExchrate(this.prpdexch.getExchrate());
				edit.setUpdateuser(getUserInfo().getUserId().toUpperCase());
				edit.setUpdatedate(new Date());
				
				this.prpdexchService.updatePrpdexch(edit);
				setMessage("修改成功");
			}else {
				setMessage("修改失敗：查無資料");
			}
			formLoad("update", prpdexch.getBasecurrency());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {

		}
		return Action.SUCCESS;
	}

	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query","");
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
	
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("query","");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/**mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 
	 * 查詢結果點選上下三角型排序*/
	public String lnkSortQuery() throws Exception {
		try {
			formLoad("query","");
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
		
		// 檔案清單查詢
		Result result = this.prpdexchService.findPrpdexchByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devResults = (List<Prpdexch>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public PrpdnewcodeService getPrpdnewcodeService() {
		return prpdnewcodeService;
	}

	public void setPrpdnewcodeService(PrpdnewcodeService prpdnewcodeService) {
		this.prpdnewcodeService = prpdnewcodeService;
	}

	public PrpdexchService getPrpdexchService() {
		return prpdexchService;
	}

	public void setPrpdexchService(PrpdexchService prpdexchService) {
		this.prpdexchService = prpdexchService;
	}

	public Map<String, String> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(Map<String, String> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public Prpdexch getPrpdexch() {
		return prpdexch;
	}

	public void setPrpdexch(Prpdexch prpdexch) {
		this.prpdexch = prpdexch;
	}

	public void setDevResults(List<Prpdexch> devResults) {
		this.devResults = devResults;
	}

	public List<Prpdexch> getDevResults() {
		return devResults;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 start*/
	public String getBatchExchdate() {
		return batchExchdate;
	}

	public void setBatchExchdate(String batchExchdate) {
		this.batchExchdate = batchExchdate;
	}
	/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 end*/
	
}
