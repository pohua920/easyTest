package com.tlg.aps.action;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.tlg.db2.entity.As400Inrcfil;
import com.tlg.db2.service.As400InrcfilService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Constants;
import com.tlg.util.Result;

/** mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400  */

@SuppressWarnings("serial")
public class APS061Action extends BaseAction {
	
	
	private ConfigUtil configUtil;
	private As400InrcfilService as400InrcfilService;
	
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
		
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String btnExecute() throws Exception {
		try{
			String pass = (String) getPageInfo().getFilter().get("pass");
			if(!configUtil.getString("inrcfilPwd").equals(pass)){
				setMessage("密碼錯誤");
			} else {
				Result result = as400InrcfilService.inrcfilRdmToAs400();
				if(result.getMessage() != null){
					//無資料顯示查無資料，有錯誤則顯示錯誤訊息
					setMessage(result.getMessage().toString());
				}
				if(result.getResObject() != null){
					List<As400Inrcfil> as400Inrcfils  = (List<As400Inrcfil>) result.getResObject();
					setMessage("處理作業執行成功，共執行"+as400Inrcfils.size()+"筆。");
				} else {
					
				}
			}
			
		} catch (SystemException se) {
//			setMessage(Constants.SAVE_DATA_FAIL);
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			setMessage(Constants.SAVE_DATA_FAIL);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}
	

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public As400InrcfilService getAs400InrcfilService() {
		return as400InrcfilService;
	}

	public void setAs400InrcfilService(As400InrcfilService as400InrcfilService) {
		this.as400InrcfilService = as400InrcfilService;
	}

	
	
	
}
