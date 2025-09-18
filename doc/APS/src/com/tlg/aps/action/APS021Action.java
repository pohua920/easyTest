package com.tlg.aps.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrpdhighareaMc;
import com.tlg.prpins.service.PrpdhighareaMcService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS021Action extends BaseAction {
	/* mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start */

	private List<PrpdhighareaMc> devResults = new ArrayList<>();
	
	private PrpdhighareaMcService prpdhighareaMcService;
	
	private PrpdhighareaMc prpdhighareaMc;
	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
	
	
	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		
		return Action.SUCCESS;
	}
	
	/** 按下查詢鍵，做資料查詢的動作 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "countycode");
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
	
	/** 按下新增鍵，做資料新增的動作 */
	public String btnCreate() throws Exception {
		try{
			prpdhighareaMc.setCreateuser(getUserInfo().getUserId().toUpperCase());
			prpdhighareaMc.setCreatedate(new Date());
			prpdhighareaMc.setUpdateuser(getUserInfo().getUserId().toUpperCase());
			prpdhighareaMc.setUpdatedate(new Date());
			this.prpdhighareaMcService.insertPrpdhighareaMc(prpdhighareaMc);
			
			setMessage("新增成功");
			prpdhighareaMc = new PrpdhighareaMc();
		} catch (SystemException se) {
			setMessage("新增失敗:"+se.getMessage().replace("\"", "'"));
		} catch (Exception e) {
			setMessage("新增失敗:"+e.getMessage().replace("\"", "'"));
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下修改鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			query();
		} catch (SystemException se) {
			setMessage("修改失敗:"+se.getMessage().replace("\"", "'"));
			return "returnQuery";
		} catch (Exception e) {
			setMessage("修改失敗:"+e.getMessage().replace("\"", "'"));
			e.printStackTrace();
			return "returnQuery";
		}
		return Action.SUCCESS;
	}
	
	/** 連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		
		return Action.SUCCESS;
	}
	
	/** 連結至修改頁面 */
	public String lnkGoEdit() throws Exception {
		try {
			Map<String,String> params = new HashMap<>();
			if(StringUtil.isSpace(prpdhighareaMc.getCountycode()) || StringUtil.isSpace(prpdhighareaMc.getShortcode())) {
				setMessage("查無資料");
				query();
				return "returnQuery";
			}else {
				params.put("countycode", prpdhighareaMc.getCountycode());
				params.put("shortcode", prpdhighareaMc.getShortcode());
			}
			
			Result result = this.prpdhighareaMcService.findPrpdhighareaMcByUK(params);
			if(result != null && result.getResObject() != null) {
				this.prpdhighareaMc = (PrpdhighareaMc)result.getResObject();
				prpdhighareaMc.setStrStartdate(SDF.format(prpdhighareaMc.getStartdate()));
				prpdhighareaMc.setStrEnddate(SDF.format(prpdhighareaMc.getEnddate()));
			}else {
				setMessage("查無資料");
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
	
	/** 負責處理查詢動作  */
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		Result result = this.prpdhighareaMcService.findPrpdhighareaMcByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<PrpdhighareaMc>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 負責處理update動作  */
	private void update() throws Exception {
		prpdhighareaMc.setUpdateuser(getUserInfo().getUserId().toUpperCase());
		prpdhighareaMc.setUpdatedate(new Date());
		prpdhighareaMc.setStartdate(SDF.parse(prpdhighareaMc.getStrStartdate()));
		prpdhighareaMc.setEnddate(SDF.parse(prpdhighareaMc.getStrEnddate()));
		Result result = prpdhighareaMcService.updatePrpdhighareaMc(prpdhighareaMc);
		setMessage(result.getMessage().toString());
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

	public PrpdhighareaMcService getPrpdhighareaMcService() {
		return prpdhighareaMcService;
	}

	public void setPrpdhighareaMcService(PrpdhighareaMcService prpdhighareaMcService) {
		this.prpdhighareaMcService = prpdhighareaMcService;
	}

	public List<PrpdhighareaMc> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<PrpdhighareaMc> devResults) {
		this.devResults = devResults;
	}

	public PrpdhighareaMc getPrpdhighareaMc() {
		return prpdhighareaMc;
	}

	public void setPrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) {
		this.prpdhighareaMc = prpdhighareaMc;
	}
}
