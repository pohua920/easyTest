package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.resendAnnounceService.ResendAnnounceService;
import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.aps.vo.liaJsonObj.lia07010au.request.Row;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.LiaRcvAnnounce;
import com.tlg.xchg.service.CwpRcvAnnounceService;
import com.tlg.xchg.service.CwpUndwrtAnnounceService;

@SuppressWarnings("serial")
public class APS013Action extends BaseAction {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	private CwpRcvAnnounceService cwpRcvAnnounceService;
	private CwpUndwrtAnnounceService cwpUndwrtAnnounceService;
	private ResendAnnounceService resendAnnounceService;
	
	private List<LiaRcvAnnounce> sendResults;
	private List<CwpAnnounceVo> devResults;
	private List<CwpAnnounceVo> dtlDevResults;
	private List<CwpAnnounceVo> moreDtlDevResults;
	private CwpAnnounceVo cwpAnnounceVo;
	private String announceCase;
	
	private Map<String,String> conMap = new LinkedHashMap<>();
	private Map<String,String> insitemMap = new LinkedHashMap<>();
	private Map<String,String> insclassMap = new LinkedHashMap<>();
	private Map<String,String> inskindMap = new LinkedHashMap<>();
	private Map<String,String> paytypeMap = new LinkedHashMap<>();
	private Map<String,String> cmptypeMap = new LinkedHashMap<>();
	private Map<String,String> asktypeMap = new LinkedHashMap<>();
	private Map<String,String> sexMap = new LinkedHashMap<>();
	private Map<String,String> broktypeMap = new LinkedHashMap<>();
	private Map<String,String> bamttypeMap = new LinkedHashMap<>();	
	private Map<String,String> channelMap = new LinkedHashMap<>();	
	private Map<String,String> originMap = new LinkedHashMap<>();	

	private String dPageInfoName = this.getClass().getSimpleName() + "dPageInfo";
	private PageInfo dPageInfo;
	private Map<String, String> dFilter;

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			formLoad("execute");
			resetDPageInfo();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

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
		if(StringUtil.isSpace((String)getPageInfo().getFilter().get("rtncode"))) {
			getPageInfo().getFilter().remove("rtncode");
		}
	}

	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("btnQuery");
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "SENDTIME");
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

	/** 分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("txtChangePageIndex");		
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("ddlPageSizeChanged");			
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

	/** 查詢結果點選上下三角型排序 */
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

	/** (明細)導頁至掃描結果明細檔查詢結果頁面 */
	@SuppressWarnings("unchecked")
	public String lnkGoDetail() throws Exception {
		try {
			formLoad("detail");
			if (null == cwpAnnounceVo.getIdno() && null == cwpAnnounceVo.getCheckno()
					&& null == cwpAnnounceVo.getInsno()) {
				setMessage("請重新操作");
				return "input";
			}
			getDPageInfo().getFilter().put("sendTimeNotNull", "Y");
			getDPageInfo().getFilter().put("insno",cwpAnnounceVo.getInsno());
			getDPageInfo().getFilter().put("idno",cwpAnnounceVo.getIdno());
			getDPageInfo().getFilter().put("checkno",cwpAnnounceVo.getCheckno());
			getDPageInfo().getFilter().put("sortBy","OID");
			getDPageInfo().getFilter().put("sortType","ASC");
			dtlQuery();
			mainQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** (報送資料明細)APS013E1.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtDtlChangePageIndex() throws Exception {
		try {
			formLoad("detail");
			dtlQuery();
			mainQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exection", e);
		}
		return Action.SUCCESS;
	}

	/** (報送資料明細)APS013E1.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlDtlPageSizeChanged() throws Exception {
		try {
			formLoad("detail");
			PageInfo epageInfo = getDPageInfo();
			epageInfo.setCurrentPage(1);
			dtlQuery();
			mainQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exection", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 連結至查詢頁面 */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("lnkGoQuery");
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

	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = new Result();
		if(getPageInfo().getFilter().get("case").equals("RCV")) {
			result = this.cwpRcvAnnounceService.findDistinctCwpRcvAnnounceData(getPageInfo());
		}else if(getPageInfo().getFilter().get("case").equals("UNDWRT")){
			result = this.cwpUndwrtAnnounceService.findDistinctCwpUndwrtAnnounceData(getPageInfo());
		}
		if(result != null && result.getResObject() != null) {
			devResults = (List<CwpAnnounceVo>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}

	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		Result result = new Result();
		//判斷是收件通報或是承保通報
		if(getPageInfo().getFilter().get("case").equals("RCV")) {
			result = this.cwpRcvAnnounceService.findCwpRcvAnnounceByPageInfo(getDPageInfo());
			//給頁面傳值用
			announceCase = "RCV";
		}else if(getPageInfo().getFilter().get("case").equals("UNDWRT")){
			result = this.cwpUndwrtAnnounceService.findCwpUndwrtAnnounceByPageInfo(getDPageInfo());
			announceCase = "UNDWRT";
		}		
		if (null != result.getResObject()) {
			dtlDevResults = (List<CwpAnnounceVo>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}

	//原始查詢結果呈現在明細頁面上，但原有多筆distinct，原始結果也需顯示一筆(可用原條件再select table)
	private void mainQuery() throws SystemException, Exception {
		Map<String,String> params = new HashMap<>();
		params.put("insno", (String) getDPageInfo().getFilter().get("insno"));
		params.put("idno", (String) getDPageInfo().getFilter().get("idno"));
		params.put("checkno", (String) getDPageInfo().getFilter().get("checkno"));
		Result result = new Result();
		if(getPageInfo().getFilter().get("case").equals("RCV")) {
			result = this.cwpRcvAnnounceService.findDistinctCwpRcvAnnounceByParams(params);
		}else if(getPageInfo().getFilter().get("case").equals("UNDWRT")){
			result = this.cwpUndwrtAnnounceService.findDistinctCwpUndwrtAnnounceByParams(params);
		}		
		if (null != result.getResObject()) {
			cwpAnnounceVo = (CwpAnnounceVo) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}

	//轉換民國年為西元年
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	/**(報送資料明細)APS013E1.jsp，點選重送按鈕，修改cwp重送時間、重送人員、重送原因，且新增一筆資料在lia(並加上此筆資料在cwp中的oid)*/
	public String btnResend() throws Exception {
		try {
			formLoad("detail");
			Result result = resendAnnounceService.resendAnnounce(getPageInfo().getFilter().get("case").toString(),
					getUserInfo().getUserId().toUpperCase(), cwpAnnounceVo);
			setMessage(result.getMessage().toString());
			dtlQuery();
			mainQuery();
		} catch (SystemException se) {
			dtlQuery();
			mainQuery();
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 載入愈補送的預設資料
	 *
	 */
	@SuppressWarnings("unchecked")
	public String lnkQueryDefaultData() throws Exception {
		try {
			
			if (null == cwpAnnounceVo.getIdno() && null == cwpAnnounceVo.getCheckno()
					&& null == cwpAnnounceVo.getInsno()) {
				setMessage("請重新操作");
				return "input";
			}
			getDPageInfo().getFilter().put("sendTimeNotNull", "Y");
			getDPageInfo().getFilter().put("insno",cwpAnnounceVo.getInsno());
			getDPageInfo().getFilter().put("idno",cwpAnnounceVo.getIdno());
			getDPageInfo().getFilter().put("checkno",cwpAnnounceVo.getCheckno());
			getDPageInfo().getFilter().put("sortBy","OID");
			getDPageInfo().getFilter().put("sortType","ASC");
			dtlQuery();
		} catch (SystemException se) {
			query();
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
			formLoad("detail");
		}
		return Action.SUCCESS;
	}
	
	/**新增一筆資料在lia*/
	public String btnReSendLia() throws Exception {
		ArrayList<CwpAnnounceVo> cwpAnnounceVoList = new ArrayList<CwpAnnounceVo>();
		for(CwpAnnounceVo cwpAnnounceVo:dtlDevResults){
			CwpAnnounceVo row = new CwpAnnounceVo();
			BeanUtils.copyProperties(cwpAnnounceVo, row);
			cwpAnnounceVoList.add(row);
		}
		String type = this.announceCase;
		try {
			Result result = resendAnnounceService.resendAnnounce(type,
					getUserInfo().getUserId().toUpperCase(), cwpAnnounceVoList);
			if(result.getResObject() != null){
				this.sendResults = (ArrayList<LiaRcvAnnounce>)result.getResObject();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
//			this.devResults = cwpAnnounceVoList;
//			this.announceCase = type;
			formLoad("detail");
		}
		return Action.SUCCESS;
	}

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws Exception {
		if(type.equals("detail")) {
			conMap.put("", "");
			if("RCV".equals(this.announceCase)){
				conMap.put("01", "01-有效");
				conMap.put("06", "06-未承保取消件");
				conMap.put("07", "07-契約註銷");
				conMap.put("11", "11-滿期(契約到期)");
				conMap.put("12", "12-鍵值欄位通報錯誤終止");
				conMap.put("15", "15-通報更正");
			}else if("UNDWRT".equals(this.announceCase)){
				conMap.put("01", "01-有效");
				conMap.put("02", "02-增額");
				conMap.put("03", "03-減額");
				conMap.put("04", "04-展期");
				conMap.put("05", "05-繳清");
				conMap.put("06", "06-契約撤銷");
				conMap.put("07", "07-停效");
				conMap.put("10", "10-解除契約");
				conMap.put("11", "11-滿期(契約到期)");
				conMap.put("12", "12-鍵值欄位通報錯誤終止");
				conMap.put("20", "20-終止1");
				conMap.put("21", "21-終止2");
				conMap.put("30", "30-被保險人因自然死身故");
				conMap.put("31", "31-被保險人因意外身故");
				conMap.put("32", "32-被保險人因其他原因身故");
				conMap.put("50", "50-一O七條/一O七條之一承保資料(精神障礙或其他心智缺陷/受監護宣告尚未撤銷)");
				//conMap.put("51", "51-一O七條理賠資料(未滿14足歲之未成年人)");
				//conMap.put("52", "52-一O七條理賠資料(心神喪失或精神耗弱之人)");
			}else{
				//保單狀況
				conMap.put("01", "01-有效");
				conMap.put("02", "02-增額");
				conMap.put("03", "03-減額");
				conMap.put("04", "04-展期");
				conMap.put("05", "05-繳清");
				conMap.put("06", "06-契約撤銷/未承保取消件");
				conMap.put("07", "07-停效/契約註銷");
				conMap.put("10", "10-解除契約");
				conMap.put("11", "11-滿期(契約到期)");
				conMap.put("12", "12-鍵值欄位通報錯誤終止");
				conMap.put("15", "15-通報更正");
				conMap.put("20", "20-終止1");
				conMap.put("21", "21-終止2");
				conMap.put("30", "30-被保險人因自然死身故");
				conMap.put("31", "31-被保險人因意外身故");
				conMap.put("32", "32-被保險人因其他原因身故");
				conMap.put("50", "50-一O七條/一O七條之一承保資料(精神障礙或其他心智缺陷/受監護宣告尚未撤銷)");
				conMap.put("51", "51-一O七條理賠資料(未滿14足歲之未成年人)");
				conMap.put("52", "52-一O七條理賠資料(心神喪失或精神耗弱之人)");
			}

			//險種
			insitemMap.put("", "");
			insitemMap.put("01", "01-一般");
			insitemMap.put("02", "02-特定");
			insitemMap.put("03", "03-投資型");
			insitemMap.put("04", "04-日額型");
			insitemMap.put("05", "05-實支實付型");
			insitemMap.put("06", "06-日額或實支實付擇一");
			insitemMap.put("07", "07-手術型");
			insitemMap.put("08", "08-重大疾病");
			insitemMap.put("09", "09-帳戶型");
			insitemMap.put("10", "10-長期看護型");
			insitemMap.put("11", "11-喪失工作能力");
			insitemMap.put("12", "12-防癌");
			insitemMap.put("13", "13-旅行平安");
			insitemMap.put("14", "14-微型");
			insitemMap.put("15", "15-微型實支實付型");
			insitemMap.put("16", "16-小額終老保險");
			insitemMap.put("17", "17-失能扶助保險");
			insitemMap.put("18", "18-登山綜合保險");
			insitemMap.put("19", "19-定期壽險");
			//2024/08/02 傷害險通報新增21險種
			insitemMap.put("21", "21-一年期");
			insitemMap.put("99", "99-意外身故");
			//保單分類
			insclassMap.put("", "");
			insclassMap.put("1", "1-個人");
			insclassMap.put("2", "2-團體");
			//險種分類
			inskindMap.put("", "");
			inskindMap.put("1", "1-人壽保險");
			inskindMap.put("2", "2-傷害保險");
			inskindMap.put("3", "3-健康保險");
			inskindMap.put("4", "4-年金保險");
			//公、自費件
			paytypeMap.put("", "");
			paytypeMap.put("0", "0-無");
			paytypeMap.put("1", "1-公費");
			paytypeMap.put("2", "2-自費");
			//產壽險別
			cmptypeMap.put("", "");
			cmptypeMap.put("r", "r-產險業之收件通報資料");
			cmptypeMap.put("R", "R-壽險業之收件通報資料");
			cmptypeMap.put("L", "L-壽險業之承保通報資料");
			cmptypeMap.put("N", "N-產險業之承保通報資料");
			//要保人與被保人關係
			asktypeMap.put("", "");
			asktypeMap.put("00", "00-無");	
			asktypeMap.put("01", "01-本人");	
			asktypeMap.put("02", "02-配偶");	
			asktypeMap.put("03", "03-父母");	
			asktypeMap.put("04", "04-子女");	
			asktypeMap.put("05", "05-其他");	
			//被保險人性別
			sexMap.put("", "");
			sexMap.put("1", "1-男");
			sexMap.put("2", "2-女");
			//保經代類別
			broktypeMap.put("", "");
			broktypeMap.put("00", "00-無");
			broktypeMap.put("01", "01-保經代傳真件");
			broktypeMap.put("02", "02-保經代健康險及傷害醫療險");
			broktypeMap.put("03", "03-保經代壽險與傷害險");
			//保險繳別
			bamttypeMap.put("", "");
			bamttypeMap.put("0", "0-無");
			bamttypeMap.put("1", "1-躉繳");
			bamttypeMap.put("2", "2-年繳");
			bamttypeMap.put("3", "3-半年繳");
			bamttypeMap.put("4", "4-季繳");
			bamttypeMap.put("5", "5-月繳");
			bamttypeMap.put("6", "6-彈性繳");
			bamttypeMap.put("9", "9-繳費期滿");
			//銷售通路別
			channelMap.put("", "");
			channelMap.put("1", "1-網路投保");
			channelMap.put("2", "2-業務員");
			channelMap.put("3", "3-保經、保代");
			channelMap.put("4", "4-電話行銷");
			channelMap.put("5", "5-機場櫃檯");
			//來源別
			originMap.put("", "");
			originMap.put("0", "0-無");
			originMap.put("1", "1-OIU保單");
		}
	}

	public CwpRcvAnnounceService getCwpRcvAnnounceService() {
		return cwpRcvAnnounceService;
	}

	public void setCwpRcvAnnounceService(CwpRcvAnnounceService cwpRcvAnnounceService) {
		this.cwpRcvAnnounceService = cwpRcvAnnounceService;
	}

	public CwpUndwrtAnnounceService getCwpUndwrtAnnounceService() {
		return cwpUndwrtAnnounceService;
	}

	public void setCwpUndwrtAnnounceService(CwpUndwrtAnnounceService cwpUndwrtAnnounceService) {
		this.cwpUndwrtAnnounceService = cwpUndwrtAnnounceService;
	}

	public ResendAnnounceService getResendAnnounceService() {
		return resendAnnounceService;
	}

	public void setResendAnnounceService(ResendAnnounceService resendAnnounceService) {
		this.resendAnnounceService = resendAnnounceService;
	}

	public CwpAnnounceVo getCwpAnnounceVo() {
		return cwpAnnounceVo;
	}

	public void setCwpAnnounceVo(CwpAnnounceVo cwpAnnounceVo) {
		this.cwpAnnounceVo = cwpAnnounceVo;
	}

	public List<CwpAnnounceVo> getDevResults() {
		return devResults;
	}

	public List<CwpAnnounceVo> getDtlDevResults() {
		return dtlDevResults;
	}

	public void setDtlDevResults(List<CwpAnnounceVo> dtlDevResults) {
		this.dtlDevResults = dtlDevResults;
	}

	public void setDevResults(List<CwpAnnounceVo> devResults) {
		this.devResults = devResults;
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
		this.dPageInfo.setFilter(this.dFilter);         //將dFilter設定進Filter 這樣getFilter時就會取得eFilter
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

	public List<CwpAnnounceVo> getMoreDtlDevResults() {
		return moreDtlDevResults;
	}

	public void setMoreDtlDevResults(List<CwpAnnounceVo> moreDtlDevResults) {
		this.moreDtlDevResults = moreDtlDevResults;
	}

	public String getAnnounceCase() {
		return announceCase;
	}

	public void setAnnounceCase(String announceCase) {
		this.announceCase = announceCase;
	}

	public Map<String, String> getConMap() {
		return conMap;
	}

	public void setConMap(Map<String, String> conMap) {
		this.conMap = conMap;
	}

	public Map<String, String> getInsitemMap() {
		return insitemMap;
	}

	public void setInsitemMap(Map<String, String> insitemMap) {
		this.insitemMap = insitemMap;
	}

	public Map<String, String> getInsclassMap() {
		return insclassMap;
	}

	public void setInsclassMap(Map<String, String> insclassMap) {
		this.insclassMap = insclassMap;
	}

	public Map<String, String> getInskindMap() {
		return inskindMap;
	}

	public void setInskindMap(Map<String, String> inskindMap) {
		this.inskindMap = inskindMap;
	}

	public Map<String, String> getPaytypeMap() {
		return paytypeMap;
	}

	public void setPaytypeMap(Map<String, String> paytypeMap) {
		this.paytypeMap = paytypeMap;
	}

	public Map<String, String> getCmptypeMap() {
		return cmptypeMap;
	}

	public void setCmptypeMap(Map<String, String> cmptypeMap) {
		this.cmptypeMap = cmptypeMap;
	}

	public Map<String, String> getAsktypeMap() {
		return asktypeMap;
	}

	public void setAsktypeMap(Map<String, String> asktypeMap) {
		this.asktypeMap = asktypeMap;
	}

	public Map<String, String> getSexMap() {
		return sexMap;
	}

	public void setSexMap(Map<String, String> sexMap) {
		this.sexMap = sexMap;
	}

	public Map<String, String> getBroktypeMap() {
		return broktypeMap;
	}

	public void setBroktypeMap(Map<String, String> broktypeMap) {
		this.broktypeMap = broktypeMap;
	}

	public Map<String, String> getBamttypeMap() {
		return bamttypeMap;
	}

	public void setBamttypeMap(Map<String, String> bamttypeMap) {
		this.bamttypeMap = bamttypeMap;
	}

	public Map<String, String> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public Map<String, String> getOriginMap() {
		return originMap;
	}

	public void setOriginMap(Map<String, String> originMap) {
		this.originMap = originMap;
	}

	public List<LiaRcvAnnounce> getSendResults() {
		return sendResults;
	}

	public void setSendResults(List<LiaRcvAnnounce> sendResults) {
		this.sendResults = sendResults;
	}
}
