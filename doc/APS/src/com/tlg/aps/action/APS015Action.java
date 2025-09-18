/**
 * 20210222
 * mantis：FIR0220，處理人員：BJ016，需求單編號：FIR0220 中信代理投保通知處理
 * */
package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firDifficultWordImportServerce.FirDifficultWordImportService;
import com.tlg.aps.vo.Aps015DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS015Action extends BaseAction {
	
	private List<FirCtbcRewNoshowword> devResults = new ArrayList<FirCtbcRewNoshowword>();
	
	private FirCtbcRewNoshowwordService firCtbcRewNoshowwordService;

	private Aps015DetailVo aps015DetailVo;
	
	private FirCtbcRewNoshowword firCtbcRewNoshowword;
	//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
	private String oriOwnerid;
	
	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 start*/
	private FirDifficultWordImportService firDifficultWordImportService;
	private File upload;
	private String downloadFileName;
	private InputStream inputStream;
	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 end*/
	
	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {

	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String year = (String)getPageInfo().getFilter().get("year");
		String month = (String)getPageInfo().getFilter().get("month");
		String ctbcno = "";
		if(year != null && year.length() > 0) {
			ctbcno += year;
			if(month != null && month.length() > 0) {
				ctbcno += month;
			}
			getPageInfo().getFilter().put("likeCtbcno", ctbcno);
		}else {
			if(month != null && month.length() > 0) {
				//沒輸入民國年只輸入月份的話，故意讓人查不到
				getPageInfo().getFilter().put("likeCtbcno", "yyymm");
			}else {
				getPageInfo().getFilter().remove("likeCtbcno");
			}
		}
		
		String invalidflag = (String)getPageInfo().getFilter().get("invalidflag");
		if(invalidflag != null && "*".equals(invalidflag)) {
			getPageInfo().getFilter().remove("invalidflag");
		}
		
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
		String id = (String)getPageInfo().getFilter().get("id");
		if(!com.tlg.util.StringUtil.isSpace(id)) {
			getPageInfo().getFilter().put("ownerid", id);
			getPageInfo().getFilter().put("datatype", "1");
		}
		String policyno = (String)getPageInfo().getFilter().get("policyno");
		if(!com.tlg.util.StringUtil.isSpace(policyno)) {
			getPageInfo().getFilter().put("ownerid", policyno);
			getPageInfo().getFilter().put("datatype", "2");
		}
		if(com.tlg.util.StringUtil.isSpace(id) && com.tlg.util.StringUtil.isSpace(policyno)) {
			getPageInfo().getFilter().remove("ownerid");
			getPageInfo().getFilter().remove("datatype");
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
	}

	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			
			formLoad("execute");

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoCreate() throws Exception {
		try {

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoEdit() throws Exception {
		try {
			System.out.println("lnkGoExecute.....");
			Map params = new HashMap();
			if(this.firCtbcRewNoshowword == null || this.firCtbcRewNoshowword.getOid() == null) {
				setMessage("查無修改資料");
				return Action.ERROR;
			}
			params.put("oid", this.firCtbcRewNoshowword.getOid());
			Result result = this.firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByParams(params);
			if(result == null || result.getResObject() == null) {
				setMessage("查無修改資料");
				return Action.ERROR;
			}
			
			List<FirCtbcRewNoshowword> searchResult = (List<FirCtbcRewNoshowword>)result.getResObject();
			if(searchResult.size() > 0) {
				this.firCtbcRewNoshowword = searchResult.get(0);
			}else {
				setMessage("查無修改資料");
				return Action.ERROR;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("execute");
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String btnUpdate() throws Exception {
		try{
//			FirCtbcRewNoshowword firCtbcRewNoshowword = null;
//			Map params = new HashMap();
//			if(aps015DetailVo == null || aps015DetailVo.getOid() == null || aps015DetailVo.getOid().length() <= 0) {
//				setMessage("查無修改資料");
//				return Action.ERROR;
//			}
//			params.put("oid", aps015DetailVo.getOid());
//			Result result = this.firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByParams(params);
//			if(result == null || result.getResObject() == null) {
//				setMessage("查無修改資料");
//				return Action.ERROR;
//			}
//			
//			List<FirCtbcRewNoshowword> searchResult = (List<FirCtbcRewNoshowword>)result.getResObject();
//			if(searchResult.size() > 0)
//				firCtbcRewNoshowword = searchResult.get(0);
//			
//			String datatype = "";
//			String ownerid = "";
//			if(aps015DetailVo != null) {
//				if(aps015DetailVo.getId() != null && aps015DetailVo.getId().length() > 0) {
//					datatype = "1";
//					ownerid = aps015DetailVo.getId();
//				}else if(aps015DetailVo.getPolicyno() != null && aps015DetailVo.getPolicyno().length() > 0){
//					datatype = "2";
//					ownerid = aps015DetailVo.getPolicyno();
//				}else {
//					setMessage("頁面修改資料有誤(aps015DetailVo has no data)");
//					return Action.ERROR;
//				}
//				
//				if(!datatype.equals(firCtbcRewNoshowword.getDatatype())) {
//					setMessage("頁面修改資料有誤(修改的資料型態不同)");
//					return Action.ERROR;
//				}
//				firCtbcRewNoshowword.setOwnerid(ownerid);
//				firCtbcRewNoshowword.setIupdate(getUserInfo().getUserId());
//				firCtbcRewNoshowword.setDupdate(new Date());
//				this.firCtbcRewNoshowwordService.updateFirCtbcRewNoshowword(firCtbcRewNoshowword);
//				setMessage("修改成功");
//			}else {
//				setMessage("頁面修改資料有誤(aps015DetailVo is NULL)");
//				return Action.ERROR;
//			}
			
			Map params = new HashMap();
			if(this.firCtbcRewNoshowword == null || firCtbcRewNoshowword.getOid() == null) {
				//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
				setMessage("頁面修改資料有誤(firCtbcRewNoshowword is NULL)");
				return Action.ERROR;
			}
			params.put("oid", this.firCtbcRewNoshowword.getOid());
			Result result = this.firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByParams(params);
			if(result == null || result.getResObject() == null) {
				setMessage("查無修改資料");
				return Action.ERROR;
			}	
			/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
			//判斷輸入資料是否跟原始資料相同，若不同才判斷資料是否重複
			if(!firCtbcRewNoshowword.getOwnerid().equals(oriOwnerid)) {
				params = new HashMap<>();
				params.put("ownerid", firCtbcRewNoshowword.getOwnerid());
				params.put("datatype", firCtbcRewNoshowword.getDatatype());
				result = firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByUK(params);
				if(result.getResObject()!=null) {
					setMessage("資料已經存在");
					return Action.ERROR;
				}
			}
			
			this.firCtbcRewNoshowword.setIupdate(getUserInfo().getUserId());
			this.firCtbcRewNoshowword.setDupdate(new Date());
			this.firCtbcRewNoshowwordService.updateFirCtbcRewNoshowword(this.firCtbcRewNoshowword);
			setMessage("修改成功");
			/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnCancel() throws Exception {
		try{
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public String btnCreate() throws Exception {
		try {
//			FirCtbcRewNoshowword firCtbcRewNoshowword = new FirCtbcRewNoshowword();
//			boolean hasData = false;
//			if(aps015DetailVo.getId() != null && aps015DetailVo.getId().length() > 0) {
//				firCtbcRewNoshowword.setDatatype("1");
//				firCtbcRewNoshowword.setOwnerid(aps015DetailVo.getId());
//				firCtbcRewNoshowword.setIcreate(getUserInfo().getUserId());
//				firCtbcRewNoshowword.setDcreate(new Date());
//				hasData = true;
//			}else if(aps015DetailVo.getPolicyno() != null && aps015DetailVo.getPolicyno().length() > 0) {
//				firCtbcRewNoshowword.setDatatype("2");
//				firCtbcRewNoshowword.setOwnerid(aps015DetailVo.getPolicyno());
//				firCtbcRewNoshowword.setIcreate(getUserInfo().getUserId());
//				firCtbcRewNoshowword.setDcreate(new Date());
//				hasData = true;
//			}
//			
//			if(hasData) {
//				this.firCtbcRewNoshowwordService.insertFirCtbcRewNoshowword(firCtbcRewNoshowword);
//				setMessage("新增成功");
//			}else {
//				setMessage("新增失敗");
//			}
			/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理-新增時檢查是否有重複資料存在。start*/
			Map<String,String> params = new HashMap<>();
			params.put("ownerid", firCtbcRewNoshowword.getOwnerid());
			params.put("datatype", firCtbcRewNoshowword.getDatatype());
			Result result = firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByUK(params);
			if(result.getResObject()!=null) {
				setMessage("資料已經存在");
				return "input";
			}
			/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
			this.firCtbcRewNoshowword.setIcreate(getUserInfo().getUserId());
			this.firCtbcRewNoshowword.setDcreate(new Date());
			this.firCtbcRewNoshowword.setIupdate(getUserInfo().getUserId());
			this.firCtbcRewNoshowword.setDupdate(new Date());
			this.firCtbcRewNoshowwordService.insertFirCtbcRewNoshowword(this.firCtbcRewNoshowword);
			setMessage("新增成功");
			this.firCtbcRewNoshowword = new FirCtbcRewNoshowword();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkDelete() throws Exception {
		try{
			if(this.firCtbcRewNoshowword == null || this.firCtbcRewNoshowword.getOid() == null) {
				setMessage("查無刪除資料");
				return Action.ERROR;
			}

			this.firCtbcRewNoshowwordService.removeFirCtbcRewNoshowword(this.firCtbcRewNoshowword.getOid());
			setMessage("刪除成功");

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			this.query();
		}
		return Action.SUCCESS;
	}
	
	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 start*/
	//轉入作業確定轉入按鈕，做資料匯入動作
	public String btnDataImport() throws Exception {
		try {
			if (upload != null) {
				Result result = firDifficultWordImportService.importDifficultWord(upload, getUserInfo());
				setMessage(result.getMessage().toString());
			} else {
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
	public String btnDowloadSample() throws Exception {
		try {
			downloadFileName = "SAMPLE.xlsx";
			inputStream = APS015Action.class.getClassLoader().getResourceAsStream("xlsx/difficultWordImportSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoImport(){
		return Action.SUCCESS;
	}
	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 end*/
	
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
	
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結至查詢頁面
	 * mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 
	 * @throws Exception */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
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
	
	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {

		parameterHandler();
		
		// 檔案清單查詢
		Result result = this.firCtbcRewNoshowwordService.findFirCtbcRewNoshowwordByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirCtbcRewNoshowword>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public List<FirCtbcRewNoshowword> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcRewNoshowword> devResults) {
		this.devResults = devResults;
	}

	public FirCtbcRewNoshowwordService getFirCtbcRewNoshowwordService() {
		return firCtbcRewNoshowwordService;
	}

	public void setFirCtbcRewNoshowwordService(FirCtbcRewNoshowwordService firCtbcRewNoshowwordService) {
		this.firCtbcRewNoshowwordService = firCtbcRewNoshowwordService;
	}

	public Aps015DetailVo getAps015DetailVo() {
		return aps015DetailVo;
	}

	public void setAps015DetailVo(Aps015DetailVo aps015DetailVo) {
		this.aps015DetailVo = aps015DetailVo;
	}

	public FirCtbcRewNoshowword getFirCtbcRewNoshowword() {
		return firCtbcRewNoshowword;
	}

	public void setFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) {
		this.firCtbcRewNoshowword = firCtbcRewNoshowword;
	}

	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	public String getOriOwnerid() {
		return oriOwnerid;
	}

	public void setOriOwnerid(String oriOwnerid) {
		this.oriOwnerid = oriOwnerid;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/

	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 start*/
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public FirDifficultWordImportService getFirDifficultWordImportService() {
		return firDifficultWordImportService;
	}

	public void setFirDifficultWordImportService(FirDifficultWordImportService firDifficultWordImportService) {
		this.firDifficultWordImportService = firDifficultWordImportService;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getFileInputStream() {
		return inputStream;
	}
	/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 end*/
}
