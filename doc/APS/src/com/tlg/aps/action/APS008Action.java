package com.tlg.aps.action;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.metaAmlService.AmlAS400Service;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;

@SuppressWarnings("serial")
public class APS008Action extends BaseAction {
	/* mantis：OTH0075，處理人員：BJ085，需求單編號：OTH0075 400轉AML手動執行作業 start */
	private AmlAS400Service amlAS400Service;

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		
		return Action.SUCCESS;
	}

	/** APS008E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			amlAS400Service.as400toMetaAmlWebService();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public AmlAS400Service getAmlAS400Service() {
		return amlAS400Service;
	}

	public void setAmlAS400Service(AmlAS400Service amlAS400Service) {
		this.amlAS400Service = amlAS400Service;
	}

}
