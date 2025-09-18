package com.tlg.aps.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.miClaimDownloadService.MiClaimTypeCmService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetclaimTypecm;
import com.tlg.msSqlMob.service.FetclaimTypecmService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS051Action extends BaseAction {

	private FetclaimTypecm fetclaimTypecm;
	private FetclaimTypecmService fetclaimTypecmService;
	private MiClaimTypeCmService miClaimTypeCmService;

	private ConfigUtil configUtil;
	private List<FetclaimTypecm> devResults;
	
	private void formLoad(String type) throws SystemException,Exception  {	

	}
	
	/** 按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "OID");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	public String btnQueryCancel() throws Exception{
		try{
			this.setPageInfo(new PageInfo());
		}catch(Exception e){
			this.getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = this.fetclaimTypecmService.findFetclaimTypecmByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FetclaimTypecm>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** 連結至查詢頁面 */
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
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 連結至轉入頁面 */
	public String lnkGoCreate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.fetclaimTypecm = new FetclaimTypecm();
		this.fetclaimTypecm.setNotifDate(simpleDateFormat.format(new Date()));
		
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		this.fetclaimTypecm.setEventDate(simpleDateFormat1.format(new Date()));
		this.fetclaimTypecm.setFinalAuth("N");
		return Action.SUCCESS;	
	}
	
	/** 參數處理 */
	private void parameterHandler() throws Exception {


	}
	
	/** 按下API發送鍵，做儲存及發送動作 */
	public String btnSend() throws Exception {
		try {
			send();
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 按下API發送鍵，做儲存及發送動作 */
	public String btnUpload() throws Exception {
		try {
			Result result = this.miClaimTypeCmService.upload(getUserInfo(), fetclaimTypecm);
			setMessage(result.getMessage().toString());
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
		return Action.SUCCESS;
	}
	
	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			create();
			return SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return INPUT;
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			
		}
	}
	
	/** 儲存後直接發送*/
	private void send() throws SystemException, Exception{
		Result result = this.miClaimTypeCmService.send(getUserInfo(), fetclaimTypecm);
		setMessage(result.getMessage().toString());
	}
	
	/** 新增*/
	private void create() throws SystemException, Exception{
		Result result = this.miClaimTypeCmService.createData(getUserInfo(), fetclaimTypecm);
		if(result.getResObject() != null){
			this.fetclaimTypecm = (FetclaimTypecm)result.getResObject();
		}
		setMessage(result.getMessage().toString());
	}
	
	public String lnkGoSubmit()throws Exception{
		try{
			if(null== this.fetclaimTypecm.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = this.fetclaimTypecmService.findFetclaimTypecmByOid(this.fetclaimTypecm.getOid());
				if(result.getResObject() == null){
					setMessage(result.getMessage().toString());
					return INPUT;
				}
				this.fetclaimTypecm = (FetclaimTypecm)result.getResObject();
				if("2".equals(this.fetclaimTypecm.getApiStatus())){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateStr = simpleDateFormat.format(new Date());
					if(StringUtil.isSpace(this.fetclaimTypecm.getErDate())){
						this.fetclaimTypecm.setErDate(dateStr);
					}
					if(StringUtil.isSpace(this.fetclaimTypecm.getFinishDate())){
						this.fetclaimTypecm.setFinishDate(dateStr);
					}
				}
				return SUCCESS;
			}
		}catch(SystemException se){
			setMessage(se.getMessage().toString());
		}catch(Exception e){
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			this.query();
		}
		return INPUT;
	}
	
	public String lnkGoView() throws Exception {
		try {
			if(null== this.fetclaimTypecm.getOid()) {// NO ID
				this.setMessage("請重新操作");
			}else {
				Result result = this.fetclaimTypecmService.findFetclaimTypecmByOid(this.fetclaimTypecm.getOid());
				if(result.getResObject() == null){
					setMessage(result.getMessage().toString());
					return INPUT;
				}
				this.fetclaimTypecm = (FetclaimTypecm)result.getResObject();
				return SUCCESS;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		} finally{
			formLoad("");
			
		}
		return INPUT;
	}

	public FetclaimTypecmService getFetclaimTypecmService() {
		return fetclaimTypecmService;
	}

	public void setFetclaimTypecmService(FetclaimTypecmService fetclaimTypecmService) {
		this.fetclaimTypecmService = fetclaimTypecmService;
	}

	public MiClaimTypeCmService getMiClaimTypeCmService() {
		return miClaimTypeCmService;
	}

	public void setMiClaimTypeCmService(MiClaimTypeCmService miClaimTypeCmService) {
		this.miClaimTypeCmService = miClaimTypeCmService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public List<FetclaimTypecm> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FetclaimTypecm> devResults) {
		this.devResults = devResults;
	}

	public FetclaimTypecm getFetclaimTypecm() {
		return fetclaimTypecm;
	}

	public void setFetclaimTypecm(FetclaimTypecm fetclaimTypecm) {
		this.fetclaimTypecm = fetclaimTypecm;
	}
}