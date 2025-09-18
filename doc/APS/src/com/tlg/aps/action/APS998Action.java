package com.tlg.aps.action;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;

@SuppressWarnings("serial")
public class APS998Action extends BaseAction {

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	/** 進入查詢頁面前會進來做的事情 */
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

}
