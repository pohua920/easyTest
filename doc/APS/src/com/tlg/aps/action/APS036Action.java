package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.reinsInwardService.ReinsInwardService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.ReinsInwardInsDataService;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS036Action extends BaseAction {

	private ReinsInwardMainData reinsInwardMainData;
	private ReinsInwardInsData reinsInwardInsData;
	private ArrayList<ReinsInwardInsData> reinsInwardInsDataList;
	private ReinsInwardMainDataService reinsInwardMainDataService;
	private ReinsInwardInsDataService reinsInwardInsDataService;
	private ReinsInwardService reinsInwardService;
	private ConfigUtil configUtil;
	private List<ReinsInwardMainData> devResults;
	private String reinsInwardInsDataStr = "";
	private String roleType = "";
	private String auditResult = "";
	
	
	/** 按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "CREATE_DATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
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
		} finally{
			getRoleList();
		}
		return Action.SUCCESS;
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
		} finally{
			getRoleList();
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
		} finally{
			getRoleList();
		}
		return Action.SUCCESS;
	}
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		getRoleList();
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<ReinsInwardMainData>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 連結至查詢頁面 */
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
		} finally{
			getRoleList();
		}
		return Action.SUCCESS;
	}
	
	/** 連結至轉入頁面 */
	public String lnkGoCreate(){
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.YEAR, 1);
	    
		this.reinsInwardMainData = new ReinsInwardMainData();
		this.reinsInwardMainData.setStartDate(DateUtils.getTaiwanDateString(date).replaceAll("/", ""));
		this.reinsInwardMainData.setEndDate(DateUtils.getTaiwanDateString(cal.getTime()).replaceAll("/", ""));
		getRoleList();
		return Action.SUCCESS;	
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
//		String strDate = (String)getPageInfo().getFilter().get("sDate");
//		if(!StringUtil.isSpace(strDate)) {
//			strDate += " 00:00:00";
//			getPageInfo().getFilter().put("startDate", strDate);
//		}else {
//			getPageInfo().getFilter().remove("startDate");
//		}
//		
//		strDate = (String)getPageInfo().getFilter().get("eDate");
//		if(!StringUtil.isSpace(strDate)) {
//			strDate += " 23:59:59";
//			getPageInfo().getFilter().put("endDate", strDate);
//		}else {
//			getPageInfo().getFilter().remove("endDate");
//		}
//		
//		String datastatus = (String)getPageInfo().getFilter().get("datastatus");
//		if(datastatus.equals("BLANK")) {
//			getPageInfo().getFilter().remove("datastatus");
//		}
		
		String status = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("status"));
		if(StringUtil.isSpace(status)) {
			getPageInfo().getFilter().remove("status");
		}
	}
	
	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			create();
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.YEAR, 1);
	    
		this.reinsInwardMainData = new ReinsInwardMainData();
		this.reinsInwardMainData.setStartDate(DateUtils.getTaiwanDateString(date).replaceAll("/", ""));
		this.reinsInwardMainData.setEndDate(DateUtils.getTaiwanDateString(cal.getTime()).replaceAll("/", ""));
		
		this.reinsInwardInsDataStr = "";
		this.reinsInwardInsDataList = new ArrayList<ReinsInwardInsData>();
		return Action.SUCCESS;
	}
	
	/** 新增*/
	private void create() throws SystemException, Exception{
		
		//將明細轉換
		if(!StringUtil.isSpace(this.reinsInwardInsDataStr)){
			ObjectMapper objectMapper = new ObjectMapper();
			reinsInwardInsDataList = objectMapper.readValue(this.reinsInwardInsDataStr, new TypeReference<ArrayList<ReinsInwardInsData>>(){});
			reinsInwardInsDataList.removeAll(Collections.singleton(null));
		}
		Result result = reinsInwardService.createData(getUserInfo(), reinsInwardMainData, reinsInwardInsDataList);
		setMessage(result.getMessage().toString());
		
	}
	
	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
					this.reinsInwardService.convertNumberComma(this.reinsInwardMainData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
					result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardInsData> dataList = (ArrayList<ReinsInwardInsData>)result.getResObject();
						for(ReinsInwardInsData data:dataList){
							this.reinsInwardService.convertNumberComma(data);
						}
						this.reinsInwardInsDataStr = JsonUtil.getJSONString(dataList);
					}
					if("P".equals(this.reinsInwardMainData.getType())){
						forward = Action.SUCCESS;
					}
					if("T".equals(this.reinsInwardMainData.getType())){
						forward = "endorse";
					}
					
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return forward;
	}
	
	public String lnkView() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
					this.reinsInwardService.convertNumberComma(this.reinsInwardMainData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
					result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardInsData> dataList = (ArrayList<ReinsInwardInsData>)result.getResObject();
						for(ReinsInwardInsData data:dataList){
							this.reinsInwardService.convertNumberComma(data);
						}
						this.reinsInwardInsDataStr = JsonUtil.getJSONString(dataList);
					}
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return forward;
	}
	
	public String lnkInsAudit() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
					
					//判斷狀態是否為1才可以由險部審核
					if(!"1".equals(this.reinsInwardMainData.getStatus())){
						setMessage("本單狀態不為1(目前狀態：" + this.reinsInwardMainData.getStatus() + ")");
						return forward;
					}
					
					this.reinsInwardService.convertNumberComma(this.reinsInwardMainData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
					result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardInsData> dataList = (ArrayList<ReinsInwardInsData>)result.getResObject();
						for(ReinsInwardInsData data:dataList){
							this.reinsInwardService.convertNumberComma(data);
						}
						this.reinsInwardInsDataStr = JsonUtil.getJSONString(dataList);
					}
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return forward;
	}
	
	public String btnInsAudit() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == this.reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(this.reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
					return forward;
				}
				ReinsInwardMainData tmp = (ReinsInwardMainData) result.getResObject();
				if(!tmp.getStatus().equals(this.reinsInwardMainData.getStatus())){
					setMessage("狀態已被改變，無法審核，請重新操作並確認狀態！");
					return forward;
				}
				this.reinsInwardService.auditData(this.auditResult, getUserInfo(), this.reinsInwardMainData);
				setMessage("審核完成");
				forward = Action.SUCCESS;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
			query();
		}
		return forward;
	}
	
	public String lnkReinsAudit() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardMainData = (ReinsInwardMainData) result.getResObject();
					
					//判斷狀態是否為1才可以由險部審核
					if(!"2".equals(this.reinsInwardMainData.getStatus())){
						setMessage("本單狀態不為2(目前狀態：" + this.reinsInwardMainData.getStatus() + ")");
						return forward;
					}
					
					this.reinsInwardService.convertNumberComma(this.reinsInwardMainData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardMainData", reinsInwardMainData.getOid());
					result = this.reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardInsData> dataList = (ArrayList<ReinsInwardInsData>)result.getResObject();
						for(ReinsInwardInsData data:dataList){
							this.reinsInwardService.convertNumberComma(data);
						}
						this.reinsInwardInsDataStr = JsonUtil.getJSONString(dataList);
					}
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return forward;
	}
	
	public String btnReinsAudit() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardMainDataService.findReinsInwardMainDataByOid(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
					return forward;
				}
				ReinsInwardMainData tmp = (ReinsInwardMainData) result.getResObject();
				if(!tmp.getStatus().equals(this.reinsInwardMainData.getStatus())){
					setMessage("狀態已被改變，無法審核，請重新操作並確認狀態！");
					return forward;
				}
				this.reinsInwardService.auditData(this.auditResult, getUserInfo(), this.reinsInwardMainData);
				setMessage("審核完成");
				forward = Action.SUCCESS;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
			query();
		}
		return forward;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return Action.SUCCESS;
	}
	
	public String lnkDelete()throws Exception{
		String forward="input" ;
		try{
			if(null== this.reinsInwardMainData.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = reinsInwardService.deleteData(getUserInfo(), reinsInwardMainData);
				setMessage(result.getMessage().toString());
			}
			
		}catch(SystemException se){
			setMessage(se.getMessage().toString());
		}catch(Exception e){
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
			this.query();
		}
		return forward;
	}
	
	public String lnkSubmit()throws Exception{
		String forward="input" ;
		try{
			if(null== this.reinsInwardMainData.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = reinsInwardService.submitData(getUserInfo(), reinsInwardMainData);
				setMessage(result.getMessage().toString());
			}
		}catch(SystemException se){
			setMessage(se.getMessage().toString());
		}catch(Exception e){
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
			this.query();
		}
		return forward;
	}
	
	/** 連結至修改頁面 */
	public String lnkGoEndorse() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardMainData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = reinsInwardService.queryPolicyDataForEndorse(reinsInwardMainData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					Map map = (HashMap) result.getResObject();
					this.reinsInwardMainData = (ReinsInwardMainData)map.get("main");
					this.reinsInwardInsDataList = (ArrayList<ReinsInwardInsData>)map.get("ins");
					if(this.reinsInwardInsDataList != null){
						this.reinsInwardInsDataStr = JsonUtil.getJSONString(this.reinsInwardInsDataList); 
					}
					
					//用保單號去找是否有其他批單
					Map params = new HashMap();
					params.put("type", "T");
					params.put("policyNo", this.reinsInwardMainData.getPolicyNo());
					result = this.reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
					if(result.getResObject() != null){
						ArrayList<ReinsInwardMainData> mainList = (ArrayList<ReinsInwardMainData>)result.getResObject();
						for(ReinsInwardMainData main:mainList){
							if(!"4".equals(main.getStatus())){
								setMessage("尚有批單正在處理中，因此無法新增批單");
								query();
								return forward;
							}
						}
					}
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		return forward;
	}
	
	/** 按下存檔鍵，做新增儲存動作 */
	public String btnEndorse() throws Exception {
		try {
			create();
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			getRoleList();
		}
		this.reinsInwardMainData = new ReinsInwardMainData();
		this.reinsInwardInsDataStr = "";
		this.reinsInwardInsDataList = new ArrayList<ReinsInwardInsData>();
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		
		//將明細轉換
		if(!StringUtil.isSpace(this.reinsInwardInsDataStr)){
			ObjectMapper objectMapper = new ObjectMapper();
			reinsInwardInsDataList = objectMapper.readValue(this.reinsInwardInsDataStr, new TypeReference<ArrayList<ReinsInwardInsData>>(){}); 
		}
		Result result = reinsInwardService.updateData(getUserInfo(), reinsInwardMainData, reinsInwardInsDataList);
		setMessage(result.getMessage().toString());
	}
	
	private void getRoleList(){
		List<String> roleList = this.getUserInfo().getRoleList();
		if(roleList.contains("RI003")){
			//再保覆核、檢視
			this.roleType = "RI003";
			return;
		}
		if(roleList.contains("RI002")){
			//險部覆核、檢視
			this.roleType = "RI002";
			return;
		}
		if(roleList.contains("RI001")){
			//新增、修改、刪除、提交及檢視
			this.roleType = "RI001";
			return;
		}
	}

	public List<ReinsInwardMainData> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<ReinsInwardMainData> devResults) {
		this.devResults = devResults;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ReinsInwardMainData getReinsInwardMainData() {
		return reinsInwardMainData;
	}

	public void setReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) {
		this.reinsInwardMainData = reinsInwardMainData;
	}

	public ReinsInwardMainDataService getReinsInwardMainDataService() {
		return reinsInwardMainDataService;
	}

	public void setReinsInwardMainDataService(
			ReinsInwardMainDataService reinsInwardMainDataService) {
		this.reinsInwardMainDataService = reinsInwardMainDataService;
	}

	public ArrayList<ReinsInwardInsData> getReinsInwardInsDataList() {
		return reinsInwardInsDataList;
	}

	public void setReinsInwardInsDataList(
			ArrayList<ReinsInwardInsData> reinsInwardInsDataList) {
		this.reinsInwardInsDataList = reinsInwardInsDataList;
	}

	public ReinsInwardInsData getReinsInwardInsData() {
		return reinsInwardInsData;
	}

	public void setReinsInwardInsData(ReinsInwardInsData reinsInwardInsData) {
		this.reinsInwardInsData = reinsInwardInsData;
	}

	public String getReinsInwardInsDataStr() {
		return reinsInwardInsDataStr;
	}

	public void setReinsInwardInsDataStr(String reinsInwardInsDataStr) {
		this.reinsInwardInsDataStr = reinsInwardInsDataStr;
	}

	public ReinsInwardInsDataService getReinsInwardInsDataService() {
		return reinsInwardInsDataService;
	}

	public void setReinsInwardInsDataService(
			ReinsInwardInsDataService reinsInwardInsDataService) {
		this.reinsInwardInsDataService = reinsInwardInsDataService;
	}

	public ReinsInwardService getReinsInwardService() {
		return reinsInwardService;
	}

	public void setReinsInwardService(ReinsInwardService reinsInwardService) {
		this.reinsInwardService = reinsInwardService;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
}