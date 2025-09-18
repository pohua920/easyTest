package com.tlg.aps.action;

import java.util.Date;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.carBrokenPolicyDataServerce.RunBrokenPolicyDataService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
@SuppressWarnings("serial")
public class APS032Action extends BaseAction {
	
	private RunBrokenPolicyDataService runBrokenPolicyDataService;
	
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

	/** APS030E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			Result result = runBrokenPolicyDataService.readFileAndImportData(new Date());
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public RunBrokenPolicyDataService getRunBrokenPolicyDataService() {
		return runBrokenPolicyDataService;
	}

	public void setRunBrokenPolicyDataService(RunBrokenPolicyDataService runBrokenPolicyDataService) {
		this.runBrokenPolicyDataService = runBrokenPolicyDataService;
	}

}
