package com.tlg.aps.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.reinsInwardService.ReinsClaimInwardService;
import com.tlg.aps.vo.ReinsInwardPolicyInsVo;
import com.tlg.aps.vo.ReinsInwardPolicyMainVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpdcode;
import com.tlg.prpins.entity.ReinsInwardClaimData;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.prpins.service.PrpdcodeService;
import com.tlg.prpins.service.ReinsInwardClaimDataService;
import com.tlg.prpins.service.ReinsInwardClaimInsDataService;
import com.tlg.prpins.service.ReinsInwardInsDataService;
import com.tlg.prpins.service.ReinsInwardMainDataService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.GIGO;
import com.tlg.util.JsonUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS048Action extends BaseAction {

	private PrpdcodeService prpdcodeService;
	private ReinsInwardClaimDataService reinsInwardClaimDataService;
	private ReinsInwardClaimInsDataService reinsInwardClaimInsDataService;
	private ReinsInwardMainDataService reinsInwardMainDataService;
	private ReinsInwardInsDataService reinsInwardInsDataService;
	private ReinsInwardMainData reinsInwardMainData;
	private ReinsInwardClaimData reinsInwardClaimData;
	private ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList;
	private ReinsClaimInwardService reinsClaimInwardService;
	private ConfigUtil configUtil;
	private List<ReinsInwardClaimData> devResults;
	private String roleType = "";
	private String auditResult = "";
	private String reinsInwardDataStr = "";
	private String reinsInwardClaimInsDataStr = "";
	private Map<String, String> damageCodeMap = new LinkedHashMap<String, String>(); 

	private void formLoad(String type) throws SystemException,Exception  {	
		
		
		if(!StringUtil.isSpace(reinsInwardClaimData.getClasscode())){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codetype", "DamageCode");
			params.put("damageCode", "Y");
			String classCode = reinsInwardClaimData.getClasscode();
			if("F01".equals(classCode) || "F02".equals(classCode)){
				classCode = "F";
			}
			params.put("classcode", classCode);
			Result result = this.prpdcodeService.findPrpdcodeByParams(params);
			if(result.getResObject() != null){
				ArrayList<Prpdcode> list = (ArrayList<Prpdcode>)result.getResObject();
				for(Prpdcode prpdcode:list){
					damageCodeMap.put(prpdcode.getCodecode(), prpdcode.getCodecname());
				}
			}
		}

		
	}
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
	
	public String btnQueryCancel() throws Exception{
		try{
			this.setPageInfo(new PageInfo());
		}catch(Exception e){
			this.getRequest().setAttribute("exception", e);
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
		Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<ReinsInwardClaimData>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		
		//還原值
		String applicantName = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("applicantName"));
		if(!StringUtil.isSpace(applicantName)) {
			getPageInfo().getFilter().put("applicantName", applicantName.substring(0, applicantName.length() - 1));
		}
		String insuredName = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("insuredName"));
		if(!StringUtil.isSpace(insuredName)) {
			getPageInfo().getFilter().put("insuredName", insuredName.substring(0, insuredName.length() - 1));
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
	    
		this.reinsInwardClaimData = new ReinsInwardClaimData();
		this.reinsInwardClaimData.setStartDate(DateUtils.getTaiwanDateString(date).replaceAll("/", ""));
		this.reinsInwardClaimData.setEndDate(DateUtils.getTaiwanDateString(cal.getTime()).replaceAll("/", ""));
		getRoleList();
		return Action.SUCCESS;	
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {

		String applicantName = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("applicantName"));
		if(!StringUtil.isSpace(applicantName)) {
			getPageInfo().getFilter().put("applicantName", applicantName + "%");
		}
		String insuredName = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("insuredName"));
		if(!StringUtil.isSpace(insuredName)) {
			getPageInfo().getFilter().put("insuredName", insuredName + "%");
		}
		String applicantId = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("applicantId"));
		String insuredId = StringUtil.nullToSpace((String)getPageInfo().getFilter().get("insuredId"));
		if(!StringUtil.isSpace(applicantName) || !StringUtil.isSpace(insuredName) || !StringUtil.isSpace(applicantId) || !StringUtil.isSpace(insuredId)){
			getPageInfo().getFilter().put("queryPolicy", "Y");
		}
		
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
		
		this.reinsInwardClaimData = new ReinsInwardClaimData();
		this.reinsInwardClaimInsDataList = new ArrayList<ReinsInwardClaimInsData>();
		this.reinsInwardMainData = new ReinsInwardMainData();
		return Action.SUCCESS;
	}
	
	/** 新增*/
	private void create() throws SystemException, Exception{
		Result result = reinsClaimInwardService.createData(getUserInfo(), reinsInwardMainData, reinsInwardClaimData, reinsInwardClaimInsDataList);
		setMessage(result.getMessage().toString());
	}
	
	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if (null == reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();
					this.reinsClaimInwardService.convertNumberComma(this.reinsInwardClaimData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
					result = this.reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardClaimInsData> dataList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
						for(ReinsInwardClaimInsData data:dataList){
							this.reinsClaimInwardService.convertNumberComma(data);
						}
						this.reinsInwardClaimInsDataStr = JsonUtil.getJSONString(dataList);
					}
					
					//撈主檔資料
					params.clear();
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getPolicyNo())){
						params.put("policyNo", this.reinsInwardClaimData.getPolicyNo());
					}
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getEndorseNo())){
						params.put("endorseNo", this.reinsInwardClaimData.getEndorseNo());
					}
				
					Result queryResult = reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
					if(queryResult.getResObject() == null){
						setMessage("無法取得保單主檔資料");
						return forward;
					}
					ArrayList<ReinsInwardMainData> list = (ArrayList<ReinsInwardMainData>)queryResult.getResObject();
					ReinsInwardMainData mainData = list.get(0);
					
					ReinsInwardPolicyMainVo mainVo = new ReinsInwardPolicyMainVo();
					GIGO.fill(mainVo, mainData);
					ArrayList<ReinsInwardPolicyInsVo> insVoList = new ArrayList<ReinsInwardPolicyInsVo>();
					mainVo.setInsList(insVoList);
					//找明細資料
					params.clear();
					params.put("oidReinsInwardMainData", mainData.getOid().toString());
					
					queryResult = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(queryResult.getResObject() != null){
						DecimalFormat formatterDot = new DecimalFormat("#,###.##");
						ArrayList<ReinsInwardInsData> inslist = (ArrayList<ReinsInwardInsData>)queryResult.getResObject();
						for(ReinsInwardInsData insData : inslist){
							ReinsInwardPolicyInsVo insVo = new ReinsInwardPolicyInsVo();
							GIGO.fill(insVo, insData);
							if(!StringUtil.isSpace(insVo.getOriCurrAmount())){
								insVo.setOriCurrAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrAmount())));
							}
							if(!StringUtil.isSpace(insVo.getOriCurrInwardAmount())){
								insVo.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrInwardAmount())));
							}
							if(!StringUtil.isSpace(insVo.getUndertakingRate())){
								insVo.setUndertakingRate(mainData.getUndertakingRate().toString());								
							}
							insVoList.add(insVo);
						}
					}
					this.reinsInwardDataStr = JsonUtil.getJSONString(mainVo);
					
					if("C".equals(this.reinsInwardClaimData.getType())){
						forward = Action.SUCCESS;
					}
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			formLoad("");
			getRoleList();
		}
		return forward;
	}
	
	public String lnkView() throws Exception {
		String forward = "input";
		try {
			if (null == reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();
					this.reinsClaimInwardService.convertNumberComma(this.reinsInwardClaimData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
					result = this.reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardClaimInsData> dataList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
						for(ReinsInwardClaimInsData data:dataList){
							this.reinsClaimInwardService.convertNumberComma(data);
						}
						this.reinsInwardClaimInsDataStr = JsonUtil.getJSONString(dataList);
					}
					
					//撈主檔資料
					params.clear();
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getPolicyNo())){
						params.put("policyNo", this.reinsInwardClaimData.getPolicyNo());
					}
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getEndorseNo())){
						params.put("endorseNo", this.reinsInwardClaimData.getEndorseNo());
					}
				
					Result queryResult = reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
					if(queryResult.getResObject() == null){
						setMessage("無法取得保單主檔資料");
						return forward;
					}
					ArrayList<ReinsInwardMainData> list = (ArrayList<ReinsInwardMainData>)queryResult.getResObject();
					ReinsInwardMainData mainData = list.get(0);
					
					ReinsInwardPolicyMainVo mainVo = new ReinsInwardPolicyMainVo();
					GIGO.fill(mainVo, mainData);
					ArrayList<ReinsInwardPolicyInsVo> insVoList = new ArrayList<ReinsInwardPolicyInsVo>();
					mainVo.setInsList(insVoList);
					//找明細資料
					params.clear();
					params.put("oidReinsInwardMainData", mainData.getOid().toString());
					
					queryResult = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(queryResult.getResObject() != null){
						DecimalFormat formatterDot = new DecimalFormat("#,###.##");
						ArrayList<ReinsInwardInsData> inslist = (ArrayList<ReinsInwardInsData>)queryResult.getResObject();
						for(ReinsInwardInsData insData : inslist){
							ReinsInwardPolicyInsVo insVo = new ReinsInwardPolicyInsVo();
							GIGO.fill(insVo, insData);
							if(!StringUtil.isSpace(insVo.getOriCurrAmount())){
								insVo.setOriCurrAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrAmount())));
							}
							if(!StringUtil.isSpace(insVo.getOriCurrInwardAmount())){
								insVo.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrInwardAmount())));
							}
							if(!StringUtil.isSpace(insVo.getUndertakingRate())){
								insVo.setUndertakingRate(mainData.getUndertakingRate().toString());								
							}
							insVoList.add(insVo);
						}
					}
					this.reinsInwardDataStr = JsonUtil.getJSONString(mainVo);
					
					if("C".equals(this.reinsInwardClaimData.getType())){
						forward = Action.SUCCESS;
					}
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			formLoad("");
			getRoleList();
		}
		return forward;
	}
	
	public String lnkInsAudit() throws Exception {
		String forward = "input";
		try {
			if (null == reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();
					this.reinsClaimInwardService.convertNumberComma(this.reinsInwardClaimData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
					result = this.reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardClaimInsData> dataList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
						for(ReinsInwardClaimInsData data:dataList){
							this.reinsClaimInwardService.convertNumberComma(data);
						}
						this.reinsInwardClaimInsDataStr = JsonUtil.getJSONString(dataList);
					}
					
					//撈主檔資料
					params.clear();
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getPolicyNo())){
						params.put("policyNo", this.reinsInwardClaimData.getPolicyNo());
					}
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getEndorseNo())){
						params.put("endorseNo", this.reinsInwardClaimData.getEndorseNo());
					}
				
					Result queryResult = reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
					if(queryResult.getResObject() == null){
						setMessage("無法取得保單主檔資料");
						return forward;
					}
					ArrayList<ReinsInwardMainData> list = (ArrayList<ReinsInwardMainData>)queryResult.getResObject();
					ReinsInwardMainData mainData = list.get(0);
					
					ReinsInwardPolicyMainVo mainVo = new ReinsInwardPolicyMainVo();
					GIGO.fill(mainVo, mainData);
					ArrayList<ReinsInwardPolicyInsVo> insVoList = new ArrayList<ReinsInwardPolicyInsVo>();
					mainVo.setInsList(insVoList);
					//找明細資料
					params.clear();
					params.put("oidReinsInwardMainData", mainData.getOid().toString());
					
					queryResult = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(queryResult.getResObject() != null){
						DecimalFormat formatterDot = new DecimalFormat("#,###.##");
						ArrayList<ReinsInwardInsData> inslist = (ArrayList<ReinsInwardInsData>)queryResult.getResObject();
						for(ReinsInwardInsData insData : inslist){
							ReinsInwardPolicyInsVo insVo = new ReinsInwardPolicyInsVo();
							GIGO.fill(insVo, insData);
							if(!StringUtil.isSpace(insVo.getOriCurrAmount())){
								insVo.setOriCurrAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrAmount())));
							}
							if(!StringUtil.isSpace(insVo.getOriCurrInwardAmount())){
								insVo.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrInwardAmount())));
							}
							if(!StringUtil.isSpace(insVo.getUndertakingRate())){
								insVo.setUndertakingRate(mainData.getUndertakingRate().toString());								
							}
							insVoList.add(insVo);
						}
					}
					this.reinsInwardDataStr = JsonUtil.getJSONString(mainVo);
					
					if("C".equals(this.reinsInwardClaimData.getType())){
						forward = Action.SUCCESS;
					}
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			formLoad("");
			getRoleList();
		}
		return forward;
	}
	
	public String btnInsAudit() throws Exception {
		String forward = "input";
		try {
			if (null == this.reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(this.reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
					return forward;
				}
				ReinsInwardClaimData tmp = (ReinsInwardClaimData) result.getResObject();
				if(!tmp.getStatus().equals(this.reinsInwardClaimData.getStatus())){
					setMessage("狀態已被改變，無法審核，請重新操作並確認狀態！");
					return forward;
				}
				this.reinsClaimInwardService.auditData(this.auditResult, getUserInfo(), this.reinsInwardClaimData);
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
			if (null == reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					this.reinsInwardClaimData = (ReinsInwardClaimData) result.getResObject();
					this.reinsClaimInwardService.convertNumberComma(this.reinsInwardClaimData);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("oidReinsInwardClaimData", reinsInwardClaimData.getOid());
					result = this.reinsInwardClaimInsDataService.findReinsInwardClaimInsDataByParams(params);
					if(result.getResObject() != null){ 
						ArrayList<ReinsInwardClaimInsData> dataList = (ArrayList<ReinsInwardClaimInsData>)result.getResObject();
						for(ReinsInwardClaimInsData data:dataList){
							this.reinsClaimInwardService.convertNumberComma(data);
						}
						this.reinsInwardClaimInsDataStr = JsonUtil.getJSONString(dataList);
					}
					
					//撈主檔資料
					params.clear();
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getPolicyNo())){
						params.put("policyNo", this.reinsInwardClaimData.getPolicyNo());
					}
					if(!StringUtil.isSpace(this.reinsInwardClaimData.getEndorseNo())){
						params.put("endorseNo", this.reinsInwardClaimData.getEndorseNo());
					}
				
					Result queryResult = reinsInwardMainDataService.findReinsInwardMainDataByParams(params);
					if(queryResult.getResObject() == null){
						setMessage("無法取得保單主檔資料");
						return forward;
					}
					ArrayList<ReinsInwardMainData> list = (ArrayList<ReinsInwardMainData>)queryResult.getResObject();
					ReinsInwardMainData mainData = list.get(0);
					
					ReinsInwardPolicyMainVo mainVo = new ReinsInwardPolicyMainVo();
					GIGO.fill(mainVo, mainData);
					ArrayList<ReinsInwardPolicyInsVo> insVoList = new ArrayList<ReinsInwardPolicyInsVo>();
					mainVo.setInsList(insVoList);
					//找明細資料
					params.clear();
					params.put("oidReinsInwardMainData", mainData.getOid().toString());
					
					queryResult = reinsInwardInsDataService.findReinsInwardInsDataByParams(params);
					if(queryResult.getResObject() != null){
						DecimalFormat formatterDot = new DecimalFormat("#,###.##");
						ArrayList<ReinsInwardInsData> inslist = (ArrayList<ReinsInwardInsData>)queryResult.getResObject();
						for(ReinsInwardInsData insData : inslist){
							ReinsInwardPolicyInsVo insVo = new ReinsInwardPolicyInsVo();
							GIGO.fill(insVo, insData);
							insVo.setOriCurrAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrAmount())));
							insVo.setOriCurrInwardAmount(formatterDot.format(new BigDecimal(insVo.getOriCurrInwardAmount())));
							insVo.setUndertakingRate(mainData.getUndertakingRate().toString());
							insVoList.add(insVo);
						}
					}
					this.reinsInwardDataStr = JsonUtil.getJSONString(mainVo);
					
					if("C".equals(this.reinsInwardClaimData.getType())){
						forward = Action.SUCCESS;
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
	
	public String btnReinsAudit() throws Exception {
		String forward = "input";
		try {
//			formLoad("update");
			if (null == reinsInwardClaimData.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
			} else {
				Result result = this.reinsInwardClaimDataService.findReinsInwardClaimDataByOid(reinsInwardClaimData.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
					return forward;
				}
				ReinsInwardClaimData tmp = (ReinsInwardClaimData) result.getResObject();
				if(!tmp.getStatus().equals(this.reinsInwardClaimData.getStatus())){
					setMessage("狀態已被改變，無法審核，請重新操作並確認狀態！");
					return forward;
				}
				this.reinsClaimInwardService.auditData(this.auditResult, getUserInfo(), this.reinsInwardClaimData);
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
			if(null== this.reinsInwardClaimData.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = reinsClaimInwardService.deleteData(getUserInfo(), reinsInwardClaimData);
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
			if(null== this.reinsInwardClaimData.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = reinsClaimInwardService.submitData(getUserInfo(), reinsInwardClaimData);
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
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {

		Result result = reinsClaimInwardService.updateData(getUserInfo(), reinsInwardMainData, reinsInwardClaimData, reinsInwardClaimInsDataList);
		setMessage(result.getMessage().toString());
	}
	
	private void getRoleList(){
		List<String> roleList = this.getUserInfo().getRoleList();
		if(roleList.contains("CR003")){
			//再保覆核、檢視
			this.roleType = "CR003";
			return;
		}
		if(roleList.contains("CR002")){
			//險部覆核、檢視
			this.roleType = "CR002";
			return;
		}
		if(roleList.contains("CR001")){
			//新增、修改、刪除、提交及檢視
			this.roleType = "CR001";
			return;
		}
	}

	public List<ReinsInwardClaimData> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<ReinsInwardClaimData> devResults) {
		this.devResults = devResults;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ReinsInwardClaimData getReinsInwardClaimData() {
		return reinsInwardClaimData;
	}

	public void setReinsInwardClaimData(ReinsInwardClaimData reinsInwardClaimData) {
		this.reinsInwardClaimData = reinsInwardClaimData;
	}

	public ReinsInwardClaimDataService getReinsInwardClaimDataService() {
		return reinsInwardClaimDataService;
	}

	public void setReinsInwardClaimDataService(
			ReinsInwardClaimDataService reinsInwardClaimDataService) {
		this.reinsInwardClaimDataService = reinsInwardClaimDataService;
	}

	public ReinsInwardClaimInsDataService getReinsInwardClaimInsDataService() {
		return reinsInwardClaimInsDataService;
	}

	public void setReinsInwardClaimInsDataService(
			ReinsInwardClaimInsDataService reinsInwardClaimInsDataService) {
		this.reinsInwardClaimInsDataService = reinsInwardClaimInsDataService;
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

	public ReinsInwardMainDataService getReinsInwardMainDataService() {
		return reinsInwardMainDataService;
	}

	public void setReinsInwardMainDataService(
			ReinsInwardMainDataService reinsInwardMainDataService) {
		this.reinsInwardMainDataService = reinsInwardMainDataService;
	}

	public ReinsInwardInsDataService getReinsInwardInsDataService() {
		return reinsInwardInsDataService;
	}

	public void setReinsInwardInsDataService(
			ReinsInwardInsDataService reinsInwardInsDataService) {
		this.reinsInwardInsDataService = reinsInwardInsDataService;
	}

	public ArrayList<ReinsInwardClaimInsData> getReinsInwardClaimInsDataList() {
		return reinsInwardClaimInsDataList;
	}

	public void setReinsInwardClaimInsDataList(
			ArrayList<ReinsInwardClaimInsData> reinsInwardClaimInsDataList) {
		this.reinsInwardClaimInsDataList = reinsInwardClaimInsDataList;
	}

	public ReinsInwardMainData getReinsInwardMainData() {
		return reinsInwardMainData;
	}

	public void setReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) {
		this.reinsInwardMainData = reinsInwardMainData;
	}

	public ReinsClaimInwardService getReinsClaimInwardService() {
		return reinsClaimInwardService;
	}

	public void setReinsClaimInwardService(
			ReinsClaimInwardService reinsClaimInwardService) {
		this.reinsClaimInwardService = reinsClaimInwardService;
	}

	public String getReinsInwardDataStr() {
		return reinsInwardDataStr;
	}

	public void setReinsInwardDataStr(String reinsInwardDataStr) {
		this.reinsInwardDataStr = reinsInwardDataStr;
	}

	public String getReinsInwardClaimInsDataStr() {
		return reinsInwardClaimInsDataStr;
	}

	public void setReinsInwardClaimInsDataStr(String reinsInwardClaimInsDataStr) {
		this.reinsInwardClaimInsDataStr = reinsInwardClaimInsDataStr;
	}

	public Map<String, String> getDamageCodeMap() {
		return damageCodeMap;
	}

	public void setDamageCodeMap(Map<String, String> damageCodeMap) {
		this.damageCodeMap = damageCodeMap;
	}
	public PrpdcodeService getPrpdcodeService() {
		return prpdcodeService;
	}
	public void setPrpdcodeService(PrpdcodeService prpdcodeService) {
		this.prpdcodeService = prpdcodeService;
	}
}