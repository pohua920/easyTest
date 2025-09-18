package com.tlg.aps.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.miClaimDownloadService.MiClaimDownloadService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS045Action extends BaseAction {
	
	private static final String SORTBY = "sortBy";
	private static final String SORTTYPE = "sortType";
	private static final String OIDAMLQUERYOBJMAIN = "oidAmlQueryObjMain";
	private static final String EXCEPTION = "exception";
	private String executeDate;
	private String confirm;
	private ConfigUtil configUtil;
	private MiClaimDownloadService miClaimDownloadService;
	private TmpfetclaimmainService tmpfetclaimmainService;

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			this.executeDate = getCurrentDate();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String btnDownload() throws Exception {
		try{
			
			if(StringUtil.isSpace(this.executeDate)){
				throw new SystemException("請輸入執行年月(民國年月)！");
			}
			
			if(!this.executeDate.matches("^\\d+$")){
				throw new SystemException("執行年月的格式為：民國年月，例如：11205，必須都為數字");
			}
			
			if(Integer.parseInt(this.executeDate.substring(3)) > 12){
				throw new SystemException("月份不可大於12");
			}
			
//			int currentDate = Integer.parseInt(getCurrentDate());
//			
//			if(Integer.parseInt(this.executeDate) > currentDate){
//				throw new SystemException("執行年月不可大於" + currentDate );
//			}
			
			if(!"Y".equals(this.confirm)){
				
				Map<String, String> params = new HashMap<String, String>();
				int count = tmpfetclaimmainService.countTmpfetclaimmain(params);
				if(count > 0){
					this.confirm = "Y";
					return Action.SUCCESS;
				}
			}else{
				this.confirm = "";
			}
			
			Result result = miClaimDownloadService.download(this.executeDate);
			if(result.getResObject() != null){
				setMessage("執行完成");
			}else{
				setMessage("執行完成，結果有異常，請收mail！");
			}
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	private String getCurrentDate() throws Exception{
		
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1911;
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if(month == 1){
			year = year - 1;
			month = 12;
		}else{
			month = month - 1;
		}
		return year + "" + StringUtil.adjustNumber(String.valueOf(month), "0", 2, true);
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public MiClaimDownloadService getMiClaimDownloadService() {
		return miClaimDownloadService;
	}

	public void setMiClaimDownloadService(
			MiClaimDownloadService miClaimDownloadService) {
		this.miClaimDownloadService = miClaimDownloadService;
	}

	
	public static void main(String args[]){
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);

		
		System.out.println("year = " + year + "month = " + month);
	
		Calendar c = Calendar.getInstance();
		c.set(2023, 12, 1);
		System.out.println(c.getTime());
		
		System.out.println( "11206".substring(3));
		
	}

	public TmpfetclaimmainService getTmpfetclaimmainService() {
		return tmpfetclaimmainService;
	}

	public void setTmpfetclaimmainService(
			TmpfetclaimmainService tmpfetclaimmainService) {
		this.tmpfetclaimmainService = tmpfetclaimmainService;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

}
