package com.tlg.aps.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.FirInsPremVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlSh.service.Rs000ApService;
import com.tlg.prpins.bs.firCalService.FirCalAmountService;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.util.BaseAction;
import com.tlg.util.DateUtils;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS001Action extends BaseAction {
	
	private FirInsPremVo firInsPremVo;
	private FirPremcalcTmp firPremcalcTmp;
	private FirCalAmountService firCalAmountService;
	private String calcDate;
	private Rs000ApService rs000ApService;
	

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	/** 負責處理查詢結果Grid */
	public String btnQuery() throws Exception {
		try{
//			Rs000Ap rs000Ap = new Rs000Ap();
//			rs000Ap.setAp00("1");
//			rs000Ap.setAp01("SH00A");
//			rs000Ap.setAp02("測試資料");
//			rs000Ap.setAp03("9");
//			rs000Ap.setAp04("N");
//			rs000Ap.setAp05(20200410);
//			rs000Ap.setAp06(0);
//			rs000Ap.setAp07("BI086");
//			rs000Ap.setAp08("");
//			Result result = rs000ApService.insertRs000Ap(rs000Ap);
//			if(result.getResObject() != null){
//				System.out.println(result.getMessage().toString());
//			}
			
//			Result result = rs000ApService.removeRs000Ap("1", "SH00A");
//			if(result.getResObject() != null){
//				System.out.println(result.getMessage().toString());
//			}
			Map params = new HashMap();
			rs000ApService.findRs000ApByParams(params);
			if(this.firPremcalcTmp != null){
				firPremcalcTmp.setReturnType("");
				firPremcalcTmp.setReturnMsg("");
				if(!StringUtil.isSpace(calcDate)){
					firPremcalcTmp.setCalcDate(DateUtils.getWestDateObj(calcDate + "0000"));
				}
				firCalAmountService.getFirAmountCal(this.firPremcalcTmp);
			}
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			this.firPremcalcTmp = new FirPremcalcTmp();
			formLoad("execute");

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}


	/** CMN130E0.jsp頁面按下清除鍵,清除所有 pageInfo的資料 */
	public String btnQueryCancel() throws Exception {
		try {

			
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public FirInsPremVo getFirInsPremVo() {
		return firInsPremVo;
	}

	public void setFirInsPremVo(FirInsPremVo firInsPremVo) {
		this.firInsPremVo = firInsPremVo;
	}

	public FirPremcalcTmp getFirPremcalcTmp() {
		return firPremcalcTmp;
	}

	public void setFirPremcalcTmp(FirPremcalcTmp firPremcalcTmp) {
		this.firPremcalcTmp = firPremcalcTmp;
	}

	public FirCalAmountService getFirCalAmountService() {
		return firCalAmountService;
	}

	public void setFirCalAmountService(FirCalAmountService firCalAmountService) {
		this.firCalAmountService = firCalAmountService;
	}

	public String getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(String calcDate) {
		this.calcDate = calcDate;
	}

	public Rs000ApService getRs000ApService() {
		return rs000ApService;
	}

	public void setRs000ApService(Rs000ApService rs000ApService) {
		this.rs000ApService = rs000ApService;
	}

	
}
