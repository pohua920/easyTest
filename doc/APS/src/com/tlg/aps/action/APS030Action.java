package com.tlg.aps.action;

import java.util.Date;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.stockholderSynchronizeServerce.RunStockholderSynchronizeService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程  */
@SuppressWarnings("serial")
public class APS030Action extends BaseAction {
	
	private RunStockholderSynchronizeService runStockholderSynchronizeService;
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	/** APS030E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			Result result = runStockholderSynchronizeService.stockholderDataSynchronize(new Date());
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public RunStockholderSynchronizeService getRunStockholderSynchronizeService() {
		return runStockholderSynchronizeService;
	}

	public void setRunStockholderSynchronizeService(RunStockholderSynchronizeService runStockholderSynchronizeService) {
		this.runStockholderSynchronizeService = runStockholderSynchronizeService;
	}


}
