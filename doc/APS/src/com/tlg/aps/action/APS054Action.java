package com.tlg.aps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Result;
import com.tlg.xchg.entity.Newepolicyresult;
import com.tlg.xchg.service.NewepolicyresultService;

/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS) **/
@SuppressWarnings("serial")
public class APS054Action extends BaseAction {
	private List<Newepolicyresult> devResults = new ArrayList<>();
	private NewepolicyresultService newepolicyresultService;
	private NewepolicyVo newepolicyVo;
	private ConfigUtil configUtil;

	private InputStream fileInputStream;
	// 下拉
	private Map<String, String> riskcodeMap = new HashMap<>();// 類別下拉

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {

	}

	private void getStatus() {

	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				getPageInfo().getFilter().put("sortBy", "DCREATE");
				getPageInfo().getFilter().put("sortType", "DESC");
				// query();
			}
			formLoad("execute");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try {
			formLoad("query");
			getStatus();
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DCREATE");
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

	public String lnkGoDownload() throws Exception {

		String filename = "";
		try {
			if (newepolicyVo.getPolicyno() == null || newepolicyVo.getPolicyno().length() <= 0
					|| newepolicyVo.getLogfilename() == null || newepolicyVo.getPolicyno().length() <= 0) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				return Action.INPUT;
			}

			filename = newepolicyVo.getLogfilename();
			filename = filename.substring(0, filename.lastIndexOf("_")) + ".zip";
			// 根據不同階段檢核失敗，zip檔案位置會不同
			ArrayList<String> roots = new ArrayList<String>();
			roots.add(configUtil.getString("zipfilePath1"));
			roots.add(configUtil.getString("zipfilePath2"));
			roots.add(configUtil.getString("zipfilePath3"));
			boolean flag = false;
			for (String root : roots) {
				String path = root + filename;
				File resource = new File(path);
				if (resource.exists()) {
					flag =true;
					this.fileInputStream = new FileInputStream(resource);
					break;
				}
			}

			if (!flag) {
				setMessage("查無檔案");
				return Action.INPUT;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally {

		}
		newepolicyVo.setFilename(filename);
		setMessage("你好");
		return Action.SUCCESS;

	}

	/** APS054E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			getStatus();
			getPageInfo().getFilter().put("sortBy", "DCREATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}

	/** APS054E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			getStatus();
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DCREATE");
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
	private void query() throws SystemException, Exception {
		parameterHandler();
		Result result = newepolicyresultService.findNewepolicyVo(getPageInfo());
		// Result result = newepolicyresultService.findYdayNewepolicyVo();
		if (null != result.getResObject()) {
			devResults = (List<Newepolicyresult>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}

	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String strDate = (String) getPageInfo().getFilter().get("startDate");
		String classCode = (String) getPageInfo().getFilter().get("classcode");
		if (strDate != null && strDate.length() > 0) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		} else {
			getPageInfo().getFilter().remove("startCreateDate");
		}

		strDate = (String) getPageInfo().getFilter().get("endDate");
		if (strDate != null && strDate.length() > 0) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		} else {
			getPageInfo().getFilter().remove("endCreateDate");
		}

		if ("全部".equals(classCode)) {
			getPageInfo().getFilter().put("riskcode", "");
		}
	}

	public NewepolicyresultService getNewepolicyresultService() {
		return newepolicyresultService;
	}

	public void setNewepolicyresultService(NewepolicyresultService newepolicyresultService) {
		this.newepolicyresultService = newepolicyresultService;
	}

	public List<Newepolicyresult> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Newepolicyresult> devResults) {
		this.devResults = devResults;
	}

	public NewepolicyVo getNewepolicyVo() {
		return newepolicyVo;
	}

	public void setNewepolicyVo(NewepolicyVo newepolicyVo) {
		this.newepolicyVo = newepolicyVo;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public Map<String, String> getRiskcodeMap() {
		return riskcodeMap;
	}

	public void setRiskcodeMap(Map<String, String> riskcodeMap) {
		this.riskcodeMap = riskcodeMap;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

}
