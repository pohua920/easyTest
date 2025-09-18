package com.tlg.aps.action;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firBotFileService.GenerateBotInsuredFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.Result;

/** mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程*/
@SuppressWarnings("serial")
public class APS022Action extends BaseAction {
	private GenerateBotInsuredFileService generateBotInsuredFileService;

	public String btnExcuteGenBotApfile() throws Exception {
		try {
			Result result = generateBotInsuredFileService.RunToGenerateFiles(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_BOT_AP");
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
		
	}
	
	public String btnExcuteGenBotFhfile() throws Exception {
		try {
			Result result = generateBotInsuredFileService.RunToGenerateFiles(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_AGT_BOT_FH");
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
		
	}

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

	public GenerateBotInsuredFileService getGenerateBotInsuredFileService() {
		return generateBotInsuredFileService;
	}

	public void setGenerateBotInsuredFileService(GenerateBotInsuredFileService generateBotInsuredFileService) {
		this.generateBotInsuredFileService = generateBotInsuredFileService;
	}

}
