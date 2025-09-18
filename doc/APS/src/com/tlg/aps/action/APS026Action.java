package com.tlg.aps.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrpcinsuredContactChk0;
import com.tlg.prpins.service.PrpcinsuredContactChk0Service;
import com.tlg.sales.entity.PrpdCompany;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.PrpcinsuredContactChk0New;
import com.tlg.xchg.service.PrpcinsuredContactChk0NewService;

/**mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業*/
/**整頁修改mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG) */
@SuppressWarnings("serial")
public class APS026Action extends BaseAction {
	private PrpcinsuredContactChk0Service prpcinsuredContactChk0Service;
	private PrpcinsuredContactChk0NewService prpcinsuredContactChk0NewService;
	private PrpdNewCodeService prpdNewCodeService;
	private List<PrpcinsuredContactChk0> devResults;
	private PrpcinsuredContactChk0 prpcinsuredContactChk0;
	
	private PrpcinsuredContactChk0New prpcinsuredContactChk0New;
	private List<PrpcinsuredContactChk0New> devResults2;
	private String source = "";

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
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
	
	/** APS026E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().getFilter().put("sortBy", "INPUTDATE");
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

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		parameterHandler();
		Result result = prpcinsuredContactChk0Service.findPrpcinsuredContactChk0ByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<PrpcinsuredContactChk0>) result.getResObject();
			for(int i=0;i<devResults.size();i++) {
				devResults.get(i).setInsuredname(maskData(devResults.get(i).getInsuredname(),1,countEndIndex(devResults.get(i).getInsuredname())));
				devResults.get(i).setIdentifynumber(maskData(devResults.get(i).getIdentifynumber(),4,8));
			}
		} 
		
		PageInfo pageInfo2 = getPageInfo2();
		if(pageInfo2 != null){
			pageInfo2.setFilter(getPageInfo().getFilter());
			
		} else {
			setPageInfo2(new PageInfo());
			pageInfo2 = getPageInfo2();
			BeanUtils.copyProperties(getPageInfo(), pageInfo2);
			pageInfo2.setFilter(getPageInfo().getFilter());
			setPageInfo2(pageInfo2);
		}
		Result result2 = prpcinsuredContactChk0NewService.findPrpcinsuredContactChk0ByPageInfo(pageInfo2);
		if (null != result2.getResObject()) {
			devResults2 = (List<PrpcinsuredContactChk0New>) result2.getResObject();
			for(int i=0;i<devResults2.size();i++) {
				devResults2.get(i).setInsuredname(maskData(devResults2.get(i).getInsuredname(),1,countEndIndex(devResults2.get(i).getInsuredname())));
				devResults2.get(i).setIdentifynumber(maskData(devResults2.get(i).getIdentifynumber(),4,8));
				
				//查詢不同DB來源sales/dms 顯示欄位中文資訊
				Map params = new HashMap();
				params.put("codetype", "AppCode");
				params.put("codecode", devResults2.get(i).getAppcode());
				Result resultCode = prpdNewCodeService.findPrpdNewCodeByParams(params);
				
				if(resultCode != null && resultCode.getResObject() != null) {
					List<PrpdNewCode> searchCodeResult = (List<PrpdNewCode>)resultCode.getResObject();
					for(PrpdNewCode entity : searchCodeResult) {
						devResults2.get(i).setAppcode(entity.getCodecname());
					}
				}
				
				params = new HashMap();
				params.put("comcode", devResults2.get(i).getCom());
				params.put("validstatus", "1");
				Result resultCompany = prpcinsuredContactChk0Service.findPrpdCompany(params);
				if(resultCompany != null && resultCompany.getResObject() != null) {
					List<PrpdCompany> searchCompanyResult = (List<PrpdCompany>)resultCompany.getResObject();
					for(PrpdCompany entity : searchCompanyResult) {
						devResults2.get(i).setCom(entity.getComcode()+entity.getComcname());
					}
				}
			}
		} 
		if(devResults == null && devResults2 == null) {
			setMessage(result.getMessage().toString());
		}
		
	}
	
	/** APS026E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS026E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	public String ddlPageSizeChanged2() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo2();
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
	
	/** APS026E0.jsp， 查詢結果點選上下三角型排序 */
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
	
	/** APS026E0.jsp 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if("prpins".equals(source)) { //原始來源
				if (null == prpcinsuredContactChk0.getNo()) {
					setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				} else {
					
					Map<String,BigDecimal> params = new HashMap<>();
					params.put("no", prpcinsuredContactChk0.getNo());
					Result result = prpcinsuredContactChk0Service.findPrpcinsuredContactChk0ByUk(params);
					if (null == result.getResObject()) {
						setMessage(result.getMessage().toString());
					} else {
						prpcinsuredContactChk0 = (PrpcinsuredContactChk0) result.getResObject();
						prpcinsuredContactChk0.setInsuredname(maskData(prpcinsuredContactChk0.getInsuredname(),1,countEndIndex(prpcinsuredContactChk0.getInsuredname())));
						prpcinsuredContactChk0.setIdentifynumber(maskData(prpcinsuredContactChk0.getIdentifynumber(),4,8));
						forward = Action.SUCCESS;
					}
				}
			} else {//XCHG分流來源
				if (null == prpcinsuredContactChk0New.getNo()) {
					setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				} else {
					
					Map<String,BigDecimal> params = new HashMap<>();
					params.put("no", prpcinsuredContactChk0New.getNo());
					Result result = prpcinsuredContactChk0NewService.findPrpcinsuredContactChk0ByUk(params);
					if (null == result.getResObject()) {
						setMessage(result.getMessage().toString());
					} else {
						prpcinsuredContactChk0New = (PrpcinsuredContactChk0New) result.getResObject();
						prpcinsuredContactChk0New.setInsuredname(maskData(prpcinsuredContactChk0New.getInsuredname(),1,countEndIndex(prpcinsuredContactChk0New.getInsuredname())));
						prpcinsuredContactChk0New.setIdentifynumber(maskData(prpcinsuredContactChk0New.getIdentifynumber(),4,8));
						forward = Action.SUCCESS;
					}
				}
			}
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
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
			return "input";
		} catch (Exception e) {
			setMessage("存檔失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		
		if("prpins".equals(source)) {
			prpcinsuredContactChk0.setIupdate(getUserInfo().getUserId().toUpperCase());
			prpcinsuredContactChk0.setDupdate(new Date());
			Result result = prpcinsuredContactChk0Service.updatePrpcinsuredContactChk0(prpcinsuredContactChk0);
			if(result.getResObject()!=null) {
				setMessage("存檔完成");
			}else {
				setMessage("存檔失敗");
			}
		} else {
			prpcinsuredContactChk0New.setIupdate(getUserInfo().getUserId().toUpperCase());
			prpcinsuredContactChk0New.setDupdate(new Date());
			Result result = prpcinsuredContactChk0NewService.updatePrpcinsuredContactChk0(prpcinsuredContactChk0New);
			if(result.getResObject()!=null) {
				setMessage("存檔完成");
			}else {
				setMessage("存檔失敗");
			}
		}
		
		
	}
	
	/** 參數處理 */
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

	private String maskData(String sourceStr, int startIndex, int endIndex) {
		StringBuilder orgString;
		StringBuilder destStr = null;
        char symbol = '*';
        if (sourceStr == null || sourceStr.isEmpty()) {
        	return "";
        }else {
        	orgString = new StringBuilder(sourceStr.trim());
        }
        
        if(startIndex > sourceStr.length()){
            return sourceStr;
        }
        
        if(endIndex == 0 || startIndex > endIndex){
            return sourceStr;
        }
        
        if(endIndex > orgString.length()){
            destStr = orgString.replace(startIndex,orgString.length(),repeat(symbol,orgString.length() - startIndex));
        } else{
            destStr = orgString.replace(startIndex,endIndex,repeat(symbol,endIndex - startIndex));
        }
		return destStr.toString();
	}
	
	private String repeat(char ch, int repeat) {
		char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}
	
	private int countEndIndex(String sourceStr) throws Exception {
		int endIndex = 0;
		if(!StringUtil.isSpace(sourceStr)) {
			if(sourceStr.length()==2) {
				endIndex = 2;
			}else {
				endIndex = sourceStr.length() -1;
			}
		}
		return endIndex;
	}

	public PrpcinsuredContactChk0Service getPrpcinsuredContactChk0Service() {
		return prpcinsuredContactChk0Service;
	}

	public void setPrpcinsuredContactChk0Service(PrpcinsuredContactChk0Service prpcinsuredContactChk0Service) {
		this.prpcinsuredContactChk0Service = prpcinsuredContactChk0Service;
	}

	public List<PrpcinsuredContactChk0> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<PrpcinsuredContactChk0> devResults) {
		this.devResults = devResults;
	}

	public PrpcinsuredContactChk0 getPrpcinsuredContactChk0() {
		return prpcinsuredContactChk0;
	}

	public void setPrpcinsuredContactChk0(PrpcinsuredContactChk0 prpcinsuredContactChk0) {
		this.prpcinsuredContactChk0 = prpcinsuredContactChk0;
	}
	
	public PrpcinsuredContactChk0NewService getPrpcinsuredContactChk0NewService() {
		return prpcinsuredContactChk0NewService;
	}

	public void setPrpcinsuredContactChk0NewService(PrpcinsuredContactChk0NewService prpcinsuredContactChk0NewService) {
		this.prpcinsuredContactChk0NewService = prpcinsuredContactChk0NewService;
	}

	public List<PrpcinsuredContactChk0New> getDevResults2() {
		return devResults2;
	}

	public void setDevResults2(List<PrpcinsuredContactChk0New> devResults2) {
		this.devResults2 = devResults2;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public PrpcinsuredContactChk0New getPrpcinsuredContactChk0New() {
		return prpcinsuredContactChk0New;
	}

	public void setPrpcinsuredContactChk0New(PrpcinsuredContactChk0New prpcinsuredContactChk0New) {
		this.prpcinsuredContactChk0New = prpcinsuredContactChk0New;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}


}
