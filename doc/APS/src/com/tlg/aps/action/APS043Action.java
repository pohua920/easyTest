package com.tlg.aps.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.salesAgentDateAlertService.SalesAgentDateAlertService;
import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
@SuppressWarnings("serial")
public class APS043Action extends BaseAction {
	private static final Logger logger = Logger.getLogger(APS043Action.class);
	private SalesAgentDateAlertService salesAgentDateAlertService;
	private PrpdNewCodeService prpdNewCodeService;
	private PrpdagentService prpdagentService;
	private List<SalesAgentDateAlertVo> devResults;
	
	public String mailExcute() throws Exception {
		try {
			String eYyyymm = (String) getPageInfo().getFilter().get("eYyyymm");
			if(StringUtils.isNotBlank(eYyyymm)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date sysdate = sdf.parse(eYyyymm);
				List<SalesAgentDateAlertVo> mailVo = salesAgentDateAlertService.sendMailToAgent(sysdate);
				String msg = salesAgentDateAlertService.sendEmail(null, mailVo);
				if(!msg.equals("")) {
					setMessage(msg);
				}else {
					setMessage("指定日期發送E-Mail發送完成");
				}
			}else {
				throw new SystemException("執行日期不能為空");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			setMessage(e.getMessage().replaceAll("\"", ""));
		}
		return Action.SUCCESS;
	}
	
	public String phoneExcute() throws Exception {
		try {
			String mYyyymm = (String) getPageInfo().getFilter().get("mYyyymm");
			if(StringUtils.isNotBlank(mYyyymm)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date sysdate = sdf.parse(mYyyymm);
				File file = salesAgentDateAlertService.genTxtFile(sysdate);
				String msg = salesAgentDateAlertService.sendEmail(file, new ArrayList<SalesAgentDateAlertVo>());
				if(!msg.equals("")) {
					setMessage(msg);
				}else {
					setMessage("指定日期產簡訊發送檔完成");
				}
			}else {
				throw new SystemException("執行日期不能為空");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			setMessage(e.getMessage().replaceAll("\"", ""));
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String mailQuery() throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("codetype", "Salemailjob");
			params.put("codecode", "user");
			params.put("validstatus", "1");
			Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
			if (result.getResObject() == null) {
				setMessage(result.getMessage().toString());
			}else {
				List<PrpdNewCode> list = (List<PrpdNewCode>) result.getResObject();
				PrpdNewCode prpdNewCode = list.get(0);
				getPageInfo().getFilter().put("email", prpdNewCode.getCodecname());
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			setMessage(e.getMessage().replaceAll("\"", ""));
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String mailUpdate() throws Exception {
		try {
			String email = (String) getPageInfo().getFilter().get("email");
			if(StringUtils.isBlank(email)) {
				throw new SystemException("UserMail不能為空");
			}
			Map<String, Object> params = new HashMap<>();
			params.put("codetype", "Salemailjob");
			params.put("codecode", "user");
			params.put("validstatus", "1");
			Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
			if (result.getResObject() == null) {
				setMessage(result.getMessage().toString());
			}else {
				List<PrpdNewCode> list = (List<PrpdNewCode>) result.getResObject();
				PrpdNewCode prpdNewCode = list.get(0);
				prpdNewCode.setCodecname(email);
				result = prpdNewCodeService.updatePrpdNewCode(prpdNewCode);
				if(result.getResObject() != null) {
					setMessage(result.getMessage().toString());
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			setMessage(e.getMessage().replaceAll("\"", ""));
		}
		return Action.SUCCESS;
	}
	
	
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "AGENTCODE");
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
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
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
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** 進入查詢頁面前會進來做的事情 */
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
	private void query() throws Exception {
		parameterHandler();
		Result result = prpdagentService.findAPS043ByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<SalesAgentDateAlertVo>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String lnkGoQuery() throws Exception {
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
	
	public String lnkGoJob(){
		return Action.SUCCESS;
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		//Email發送日期
		String esDate = (String)getPageInfo().getFilter().get("esDate");
		if(!StringUtil.isSpace(esDate)) {
			esDate += " 00:00:00";
			getPageInfo().getFilter().put("sMaillog", esDate);
		}else {
			getPageInfo().getFilter().remove("sMaillog");
		}
		String eeDate = (String)getPageInfo().getFilter().get("eeDate");
		if(!StringUtil.isSpace(eeDate)) {
			eeDate += " 23:59:59";
			getPageInfo().getFilter().put("eMaillog", eeDate);
		}else {
			getPageInfo().getFilter().remove("eMaillog");
		}
		//登錄證到期日
		String lsDate = (String)getPageInfo().getFilter().get("lsDate");
		if(!StringUtil.isSpace(lsDate)) {
			lsDate += " 00:00:00";
			getPageInfo().getFilter().put("sLoginenddate", lsDate);
		}else {
			getPageInfo().getFilter().remove("sLoginenddate");
		}
		String leDate = (String)getPageInfo().getFilter().get("leDate");
		if(!StringUtil.isSpace(leDate)) {
			leDate += " 23:59:59";
			getPageInfo().getFilter().put("eLoginenddate", leDate);
		}else {
			getPageInfo().getFilter().remove("eLoginenddate");
		}
	}

	public SalesAgentDateAlertService getSalesAgentDateAlertService() {
		return salesAgentDateAlertService;
	}

	public void setSalesAgentDateAlertService(SalesAgentDateAlertService salesAgentDateAlertService) {
		this.salesAgentDateAlertService = salesAgentDateAlertService;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public List<SalesAgentDateAlertVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<SalesAgentDateAlertVo> devResults) {
		this.devResults = devResults;
	}

}
