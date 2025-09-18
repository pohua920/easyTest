package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.agentBranchBatchUploadService.AgentBranchBatchUploadService;
import com.tlg.aps.vo.Aps028ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.sales.service.PrpdagentService;
import com.tlg.sales.service.PrpdagentsubService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求*/
@SuppressWarnings("serial")
public class APS028Action extends BaseAction {
	private PrpdagentsubService prpdagentsubService;
	private PrpdagentService prpdagentService;
	private List<Aps028ResultVo> devResults;
	private Aps028ResultVo aps028ResultVo;
	private Prpdagentsub prpdagentsub;
	private AgentBranchBatchUploadService agentBranchBatchUploadService;
	
	private File upload = null;
	private String downloadFileName;
	private InputStream inputStream;
	private String checkKind;
	private List<String> kindList;
	private List<String> selectedKindList = new ArrayList<>();

	private void formLoad(String type) throws SystemException, Exception {
		if(type.equals("query")){
			kindList = new ArrayList<>();
			kindList.add("台壽");
			kindList.add("全國經代");
			kindList.add("地區經代");
			kindList.add("銀行");
		}
	}
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		formLoad("query");
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
	
	/** APS028E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		formLoad("query");
		try{
			getPageInfo().getFilter().put("sortBy", "A.AGENTCODE");
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

	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = prpdagentsubService.findPrpdagentsubForAps028ByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Aps028ResultVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS028E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("query");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS028E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query");
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
	
	/** APS028E0.jsp， 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
		try {
			formLoad("query");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS028E0.jsp，連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		try {
			System.out.println("lnkGoCreate.....");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下儲存鍵，做資料新增的動作 */
	public String btnCreate() throws Exception {
		try{
			formLoad("query");
			int maxAgentsubcode = prpdagentsubService.countMaxAgentsubcode();
			prpdagentsub.setAgentsubcode(String.valueOf(maxAgentsubcode+1));
			prpdagentsub.setIcreate(getUserInfo().getUserId().toUpperCase());
			prpdagentsub.setDcreate(new Date());
			prpdagentsub.setIupdate(getUserInfo().getUserId().toUpperCase());
			prpdagentsub.setDupdate(new Date());
			this.prpdagentsubService.insertPrpdagentsub(prpdagentsub);
			
			setMessage("新增成功");
			prpdagentsub = new Prpdagentsub();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("新增失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** APS028E0.jsp 連結至修改頁面 */
	@SuppressWarnings("unchecked")
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if (null == aps028ResultVo.getAgentcode() || null == aps028ResultVo.getAgentsubcode()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,String> params = new HashMap<>();
				params.put("agentcode", aps028ResultVo.getAgentcode());
				params.put("agentsubcode", aps028ResultVo.getAgentsubcode());
				Result result = prpdagentsubService.findPrpdagentsubByUk(params);
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					prpdagentsub = (Prpdagentsub) result.getResObject();
					params.clear();
					params.put("orgicode", aps028ResultVo.getOrgicode());
					params.put("agenttypeInAgent", "Y");
					params.put("validstatus", "1");
					result = prpdagentService.findPrpdagentByParams(params);
					List<Prpdagent> resultList =  (List<Prpdagent>) result.getResObject();
					if(!resultList.isEmpty()) {
						Prpdagent prpdagent = resultList.get(0);
						aps028ResultVo.setAgentname(prpdagent.getAgentname());
					}
					
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			formLoad("query");
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("修改失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			query();
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws Exception {
		if(prpdagentsub.getKind()==null) {
			prpdagentsub.setKind("");
		}
		prpdagentsub.setIupdate(getUserInfo().getUserId().toUpperCase());
		prpdagentsub.setDupdate(new Date());
		Result result = prpdagentsubService.updatePrpdagentsub(prpdagentsub);
		if(result.getResObject()!=null) {
			setMessage("修改完成");
		}else {
			setMessage("修改失敗");
		}
	}
	
	/** 按下刪除鍵，做資料刪除的動作 */
	public String btnRemove() throws Exception {
		try {
			formLoad("query");
			delete();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("刪除失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			query();
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理delete動作  */
	private void delete() throws Exception {
		if (null == aps028ResultVo.getAgentcode() || null == aps028ResultVo.getAgentsubcode()) {
			setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
		} else {
			Map<String,String> params = new HashMap<>();
			params.put("agentcode", aps028ResultVo.getAgentcode());
			params.put("agentsubcode", aps028ResultVo.getAgentsubcode());
			boolean result = prpdagentsubService.removePrpdagentsub(params);
			if(result) {
				setMessage("刪除成功");
			}else {
				setMessage("刪除失敗");
			}
		}
	}
	
	public String lnkGobatchUpload() throws Exception {
		try {
			formLoad("query");
			System.out.println("lnkGobatchUpload.....");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception{
		try {
			String kind = (String) getPageInfo().getFilter().get("kindList");
			if(kind!=null && kind.contains(",")) {
				String[] kindArr = kind.split(",");
				for(int i=0;i<kindArr.length;i++) {
					kindArr[i] = kindArr[i].trim();
				}
				getPageInfo().getFilter().put("kindList", kindArr);
				selectedKindList = Arrays.asList(kindArr);
				getPageInfo().getFilter().put("selectedKindList", selectedKindList);
			}else if(kind!=null && !StringUtil.isSpace(kind)){
				String[] kindArr = {kind};
				getPageInfo().getFilter().put("kindList", kindArr);
				selectedKindList = Arrays.asList(kindArr);
				getPageInfo().getFilter().put("selectedKindList", selectedKindList);
			}else {
				getPageInfo().getFilter().remove("kindList");
			}
		}catch(Exception e) {
			String[] kindArr = (String[]) getPageInfo().getFilter().get("kindList");
			selectedKindList = Arrays.asList(kindArr);
		}
	}

	public String btnBatchUpload() throws Exception {
		try{
			if(upload!=null) {
				Result result = agentBranchBatchUploadService.uploadBatchData(getUserInfo().getUserId().toUpperCase(), 
						upload);
				setMessage(result.getMessage().toString());
			}else {
				throw new SystemException("檔案讀取失敗");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/*下載sample檔案*/
	public String btnDowloadSample() {
		try {
			downloadFileName = "SAMPLE.xlsx";
			inputStream = APS028Action.class.getClassLoader().getResourceAsStream("AgentsubBatchSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public PrpdagentsubService getPrpdagentsubService() {
		return prpdagentsubService;
	}

	public void setPrpdagentsubService(PrpdagentsubService prpdagentsubService) {
		this.prpdagentsubService = prpdagentsubService;
	}

	public List<Aps028ResultVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Aps028ResultVo> devResults) {
		this.devResults = devResults;
	}

	public Prpdagentsub getPrpdagentsub() {
		return prpdagentsub;
	}

	public void setPrpdagentsub(Prpdagentsub prpdagentsub) {
		this.prpdagentsub = prpdagentsub;
	}

	public PrpdagentService getPrpdagentService() {
		return prpdagentService;
	}

	public void setPrpdagentService(PrpdagentService prpdagentService) {
		this.prpdagentService = prpdagentService;
	}

	public Aps028ResultVo getAps028ResultVo() {
		return aps028ResultVo;
	}

	public void setAps028ResultVo(Aps028ResultVo aps028ResultVo) {
		this.aps028ResultVo = aps028ResultVo;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public AgentBranchBatchUploadService getAgentBranchBatchUploadService() {
		return agentBranchBatchUploadService;
	}

	public void setAgentBranchBatchUploadService(AgentBranchBatchUploadService agentBranchBatchUploadService) {
		this.agentBranchBatchUploadService = agentBranchBatchUploadService;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getCheckKind() {
		return checkKind;
	}

	public void setCheckKind(String checkKind) {
		this.checkKind = checkKind;
	}

	public List<String> getKindList() {
		return kindList;
	}

	public void setKindList(List<String> kindList) {
		this.kindList = kindList;
	}

	public List<String> getSelectedKindList() {
		return selectedKindList;
	}

	public void setSelectedKindList(List<String> selectedKindList) {
		this.selectedKindList = selectedKindList;
	}
}
