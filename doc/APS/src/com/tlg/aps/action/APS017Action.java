/**
 * 20210222
 * mantis：FIR0220，處理人員：BJ016，需求單編號：FIR0220 中信代理投保通知處理
 * */
package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Aps017EditVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.prpins.entity.Prpdnewcode;
import com.tlg.prpins.service.FirAgtSalesMappingService;
import com.tlg.prpins.service.PrpdnewcodeService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS017Action extends BaseAction {

	private List<FirAgtSalesMapping> devResults = new ArrayList<FirAgtSalesMapping>();
	
	private FirAgtSalesMappingService firAgtSalesMappingService;
	private PrpdnewcodeService prpdnewcodeService;
	
	// 下拉
	private Map<String, String> businessnatureMap = new LinkedHashMap<String, String>();// 類別下拉
	
	private FirAgtSalesMapping firAgtSalesMapping;
	private Aps017EditVo aps017EditVo;
	
	/** 載入畫面下拉資料 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void formLoad(String type) throws SystemException, Exception {
		try {
			Map params = new HashMap();
			params.put("codetype", "BusinessChannel");
			params.put("codecode", "I99065");
			Result result = prpdnewcodeService.findPrpdnewcodeByParams(params);
			if(result != null && result.getResObject() != null) {
				List<Prpdnewcode> searchResult = (List<Prpdnewcode>)result.getResObject();
				for(Prpdnewcode entity : searchResult) {
					businessnatureMap.put(entity.getCodecode(), entity.getCodecode() + "-" + entity.getCodecname());
				}
			}else {
				businessnatureMap.put("*****", "查無資料");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String deletFlag = (String)getPageInfo().getFilter().get("deleteFlag");
		if("*".equals(deletFlag)) {
			getPageInfo().getFilter().remove("deleteFlag");
		}
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
			System.out.println("lnkGoExecute.....");
			formLoad("query");
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
			if(firAgtSalesMapping.getOid() == null) {
				setMessage("查無資料");
				query();
				return Action.ERROR;
			}
			
			Result result = this.firAgtSalesMappingService.selectByOidForAps017(firAgtSalesMapping.getOid());
			if(result != null && result.getResObject() != null) {
				this.aps017EditVo = (Aps017EditVo)result.getResObject();
			}else {
				setMessage("查無資料");
				query();
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
	
	public String btnCreate() throws Exception {
		try{
			firAgtSalesMapping.setDeleteFlag("N");
			firAgtSalesMapping.setIcreate(getUserInfo().getUserId());
			firAgtSalesMapping.setDcreate(new Date());
			this.firAgtSalesMappingService.insertFirAgtSalesMapping(firAgtSalesMapping);
			
			setMessage("新增成功");
			
			firAgtSalesMapping = new FirAgtSalesMapping();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			formLoad("execute");
		}
		return Action.SUCCESS;
	}
	
	public String btnUpdate() throws Exception {
		try{
			Result result = this.firAgtSalesMappingService.findFirAgtSalesMappingByOid(this.aps017EditVo.getOid());
			if(result != null && result.getResObject() != null) {
				this.firAgtSalesMapping = (FirAgtSalesMapping)result.getResObject();
				
				this.firAgtSalesMapping.setBranchName(this.aps017EditVo.getBranchName());
				this.firAgtSalesMapping.setHandler1code(this.aps017EditVo.getHandler1code());
				this.firAgtSalesMapping.setRemark(this.aps017EditVo.getRemark());
				this.firAgtSalesMapping.setDeleteFlag(this.aps017EditVo.getDeleteFlag());
				this.firAgtSalesMapping.setIupdate(getUserInfo().getUserId());
				this.firAgtSalesMapping.setDupdate(new Date());
				
				this.firAgtSalesMappingService.updateFirAgtSalesMapping(this.firAgtSalesMapping);
				setMessage("修改成功");
			}else {
				setMessage("修改失敗：查無資料");
			}

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally {
			Result result = this.firAgtSalesMappingService.selectByOidForAps017(firAgtSalesMapping.getOid());
			if(result != null && result.getResObject() != null) {
				this.aps017EditVo = (Aps017EditVo)result.getResObject();
			}
		}
		return Action.SUCCESS;
	}

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
		
		// 檔案清單查詢
		Result result = this.firAgtSalesMappingService.findFirAgtSalesMappingForAps017ByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirAgtSalesMapping>)result.getResObject();
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

	public List<FirAgtSalesMapping> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirAgtSalesMapping> devResults) {
		this.devResults = devResults;
	}

	public FirAgtSalesMappingService getFirAgtSalesMappingService() {
		return firAgtSalesMappingService;
	}

	public void setFirAgtSalesMappingService(FirAgtSalesMappingService firAgtSalesMappingService) {
		this.firAgtSalesMappingService = firAgtSalesMappingService;
	}

	public FirAgtSalesMapping getFirAgtSalesMapping() {
		return firAgtSalesMapping;
	}

	public void setFirAgtSalesMapping(FirAgtSalesMapping firAgtSalesMapping) {
		this.firAgtSalesMapping = firAgtSalesMapping;
	}

	public PrpdnewcodeService getPrpdnewcodeService() {
		return prpdnewcodeService;
	}

	public void setPrpdnewcodeService(PrpdnewcodeService prpdnewcodeService) {
		this.prpdnewcodeService = prpdnewcodeService;
	}

	public Map<String, String> getBusinessnatureMap() {
		return businessnatureMap;
	}

	public void setBusinessnatureMap(Map<String, String> businessnatureMap) {
		this.businessnatureMap = businessnatureMap;
	}

	public Aps017EditVo getAps017EditVo() {
		return aps017EditVo;
	}

	public void setAps017EditVo(Aps017EditVo aps017EditVo) {
		this.aps017EditVo = aps017EditVo;
	}
	
}
