package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.generateEpolicyService.GenerateEpolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS020Action extends BaseAction {
	
	private GenerateEpolicyService generateEpolicyService;
	
	private String type;
	private String policyNo;

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	/** APS020E0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			if("1".equals(this.type)){
				//批次
				Map<String, Object> resultMap = generateEpolicyService.queryToGeneratePAEpolicyService("APS", this.getUserInfo().getUserId());
				
				String total = (String)resultMap.get("total");
				ArrayList<String> successList = (ArrayList<String>)resultMap.get("0");
				ArrayList<String> failList = (ArrayList<String>)resultMap.get("-1");
				ArrayList<String> existList = (ArrayList<String>)resultMap.get("1");
				
				String msg = "總筆數：" + total + "筆。成功：" + successList.size() + "筆，失敗" + failList.size() + "筆，電子保單已產生過：" + existList.size() + "筆";
				
				this.setMessage("執行完成。\\r\\n" + msg);
			}
			if("2".equals(this.type)){
				if(StringUtil.isSpace(this.policyNo)){
					this.setMessage("請輸入保單號");
					return Action.SUCCESS;
				}
				//單筆
				String result = generateEpolicyService.generatePAEpolicyService(this.policyNo, "ASP", this.getUserInfo().getUserId(), "PA");
				if("0".equals(result)){
					this.setMessage("執行完成～");
				}
				if("1".equals(result)){
					this.setMessage("執行失敗(已產生過電子保單)～");
				}
				if("-1".equals(result)){
					this.setMessage("執行失敗(請確認保單號碼是否存在)～");
				}
			}
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}


	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public GenerateEpolicyService getGenerateEpolicyService() {
		return generateEpolicyService;
	}

	public void setGenerateEpolicyService(
			GenerateEpolicyService generateEpolicyService) {
		this.generateEpolicyService = generateEpolicyService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
