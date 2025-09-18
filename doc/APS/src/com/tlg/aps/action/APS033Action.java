package com.tlg.aps.action;

import java.util.Date;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.carFourthPostcardServerce.RunFourthPostcardService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
@SuppressWarnings("serial")
public class APS033Action extends BaseAction {
	
	private RunFourthPostcardService runFourthPostcardService;
	
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

	/** APS033E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			Result result = runFourthPostcardService.readFileAndImportData(new Date());
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public RunFourthPostcardService getRunFourthPostcardService() {
		return runFourthPostcardService;
	}

	public void setRunFourthPostcardService(RunFourthPostcardService runFourthPostcardService) {
		this.runFourthPostcardService = runFourthPostcardService;
	}
}
