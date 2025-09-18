/**
 * 20210222
 * mantis：FIR0220，處理人員：BJ016，需求單編號：FIR0220 中信代理投保通知處理
 * */
package com.tlg.aps.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firBatchSendmailService.BatchSendmailService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.prpins.service.FirBatchSendmailDetailService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS018Action extends BaseAction {

	private List<FirBatchSendmailDetail> devResults = new ArrayList<FirBatchSendmailDetail>();
	
	private FirBatchSendmailDetailService firBatchSendmailDetailService;
//	private PrpdnewcodeService prpdnewcodeService;
	//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
	private BatchSendmailService batchSendmailService;
	
	// 下拉
	private Map<String, String> businessnatureMap = new LinkedHashMap<String, String>();// 類別下拉
	
	private FirBatchSendmailDetail firBatchSendmailDetail;
	
	/** 載入畫面下拉資料 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void formLoad(String type) throws SystemException, Exception {
//		try {
//			Map params = new HashMap();
//			params.put("codetype", "BusinessChannel");
//			params.put("codecode", "I99065");
//			Result result = prpdnewcodeService.findPrpdnewcodeByParams(params);
//			if(result != null && result.getResObject() != null) {
//				List<Prpdnewcode> searchResult = (List<Prpdnewcode>)result.getResObject();
//				for(Prpdnewcode entity : searchResult) {
//					businessnatureMap.put(entity.getCodecode(), entity.getCodecode() + "-" + entity.getCodecname());
//				}
//			}else {
//				businessnatureMap.put("*****", "查無資料");
//			}
//		}catch(Exception ex) {
//			ex.printStackTrace();
//		}
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		//mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件
		String flag = (String)getPageInfo().getFilter().get("emailstatus");
		getPageInfo().getFilter().remove("emaildate");
		if(getPageInfo().getFilter().containsKey("emaildateNotNull")) {
			getPageInfo().getFilter().remove("emaildateNotNull");
		}
		if(getPageInfo().getFilter().containsKey("emaildateNull")) {
			getPageInfo().getFilter().remove("emaildateNull");
		}
		if("Y".equals(flag)) {
			getPageInfo().getFilter().put("emaildateNotNull","Y");
		/*mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
		}else if("N".equals(flag)){
			getPageInfo().getFilter().put("emaildateNull","Y");
		}
		
		String startDate = (String)getPageInfo().getFilter().get("sDate");
		startDate = checkDate(startDate);
		if(!StringUtil.isSpace(startDate)) {
			getPageInfo().getFilter().put("startUnderwriteenddate",startDate);
		}else {
			getPageInfo().getFilter().remove("startUnderwriteenddate");
		}
		
		String endDate = (String)getPageInfo().getFilter().get("eDate");
		endDate = checkDate(endDate);
		if(!StringUtil.isSpace(endDate)) {
			getPageInfo().getFilter().put("endUnderwriteenddate",endDate);
		}else {
			getPageInfo().getFilter().remove("endUnderwriteenddate");
		}
		/*mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
	}
	
	/*mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件*/
	private String checkDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			return "";
		}
		return date;
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
	
	public String lnkGoCreate() throws Exception {
		try {
			System.out.println("lnkGoExecute.....");
			formLoad("query");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoEdit() throws Exception {
		try {
//			if(firBatchSendmailDetail.getPolicyno() == null) {
//				setMessage("查無資料");
//				query();
//				return Action.ERROR;
//			}
//			
//			Result result = this.firBatchSendmailDetailService.selectByOidForAps017(firBatchSendmailDetail.getOid());
//			if(result != null && result.getResObject() != null) {
//				this.aps017EditVo = (Aps017EditVo)result.getResObject();
//			}else {
//				setMessage("查無資料");
//				query();
//				return Action.ERROR;
//			}

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
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
			getPageInfo().getFilter().put("sortBy", "UNDERWRITEENDDATE");
			getPageInfo().getFilter().put("sortBy2", "POLICYNO");
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
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
	
	public String btnRunJob() throws Exception {
		try{
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			Result result = this.batchSendmailService.batchSendmail(getUserInfo());
			boolean check = false;
			if(result != null && result.getResObject() != null) {
				check = (Boolean)result.getResObject();
			}
			
			if(check) {
				setMessage("執行成功");
			}else {
				setMessage(result.getMessage().getMessageString());
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			formLoad("execute");
		}
		return Action.SUCCESS;
	}
	
	public String btnCreate() throws Exception {
		try{
//			firBatchSendmailDetail.setDeleteFlag("N");
//			firBatchSendmailDetail.setIcreate(getUserInfo().getUserId());
//			firBatchSendmailDetail.setDcreate(new Date());
//			this.firBatchSendmailDetailService.insertFirBatchSendmailDetail(firBatchSendmailDetail);
//			
//			setMessage("新增成功");
//			
//			firBatchSendmailDetail = new FirBatchSendmailDetail();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			formLoad("execute");
		}
		return Action.SUCCESS;
	}
	
	public String btnUpdate() throws Exception {
		try{
//			Result result = this.firBatchSendmailDetailService.findFirBatchSendmailDetailByOid(this.aps017EditVo.getOid());
//			if(result != null && result.getResObject() != null) {
//				this.firBatchSendmailDetail = (FirBatchSendmailDetail)result.getResObject();
//				
//				this.firBatchSendmailDetail.setBranchName(this.aps017EditVo.getBranchName());
//				this.firBatchSendmailDetail.setHandler1code(this.aps017EditVo.getHandler1code());
//				this.firBatchSendmailDetail.setRemark(this.aps017EditVo.getRemark());
//				this.firBatchSendmailDetail.setDeleteFlag(this.aps017EditVo.getDeleteFlag());
//				this.firBatchSendmailDetail.setIupdate(getUserInfo().getUserId());
//				this.firBatchSendmailDetail.setDupdate(new Date());
//				
//				this.firBatchSendmailDetailService.updateFirBatchSendmailDetail(this.firBatchSendmailDetail);
//				setMessage("修改成功");
//			}else {
//				setMessage("修改失敗：查無資料");
//			}

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
//			Result result = this.firBatchSendmailDetailService.selectByOidForAps017(firBatchSendmailDetail.getOid());
//			if(result != null && result.getResObject() != null) {
//				this.aps017EditVo = (Aps017EditVo)result.getResObject();
//			}
		}
		return Action.SUCCESS;
	}

	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query");
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
			formLoad("query");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 查詢結果點選上下三角型排序
	 * mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件
	 */
	public String lnkSortQuery() throws Exception {
		try {
			formLoad("lnkSortQuery");
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
		Result result = this.firBatchSendmailDetailService.findFirBatchSendmailDetailByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirBatchSendmailDetail>)result.getResObject();
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

	public List<FirBatchSendmailDetail> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirBatchSendmailDetail> devResults) {
		this.devResults = devResults;
	}

	public FirBatchSendmailDetailService getFirBatchSendmailDetailService() {
		return firBatchSendmailDetailService;
	}

	public void setFirBatchSendmailDetailService(FirBatchSendmailDetailService firBatchSendmailDetailService) {
		this.firBatchSendmailDetailService = firBatchSendmailDetailService;
	}

	public FirBatchSendmailDetail getFirBatchSendmailDetail() {
		return firBatchSendmailDetail;
	}

	public void setFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) {
		this.firBatchSendmailDetail = firBatchSendmailDetail;
	}

//	public PrpdnewcodeService getPrpdnewcodeService() {
//		return prpdnewcodeService;
//	}
//
//	public void setPrpdnewcodeService(PrpdnewcodeService prpdnewcodeService) {
//		this.prpdnewcodeService = prpdnewcodeService;
//	}

	public Map<String, String> getBusinessnatureMap() {
		return businessnatureMap;
	}

	public void setBusinessnatureMap(Map<String, String> businessnatureMap) {
		this.businessnatureMap = businessnatureMap;
	}

	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
	public BatchSendmailService getBatchSendmailService() {
		return batchSendmailService;
	}

	public void setBatchSendmailService(BatchSendmailService batchSendmailService) {
		this.batchSendmailService = batchSendmailService;
	}
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/
}
