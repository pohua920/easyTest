package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDexchId;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDexchService;

public class PrpDexchAction extends Struts2Action{
    private static Log logger= LogFactory.getLog(PrpDexchAction.class);
	    private static final long serialVersionUID = 1L;
	    private PrpDcodeService prpDcodeService;
	    private PrpDexchService prpDexchService;
	    private PrpDexch prpDexch;
		private String editType;
		private String chkbox;
		private PrpDexchId prpDexchId;
		private String showTime;//页面上显示的XXXX-XX-XX格式日期的字符串
		private List currencyList;
		private Map<String, String> currencyMap;
		public PrpDexchAction(){
		}
	    
	    public Map<String, String> getCurrencyMap() {
			return currencyMap;
		}

		public void setCurrencyMap(Map<String, String> currencyMap) {
			this.currencyMap = currencyMap;
		}

		public PrpDcodeService getPrpDcodeService() {
			return prpDcodeService;
		}

		public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
			this.prpDcodeService = prpDcodeService;
		}



		public List getCurrencyList() {
			return currencyList;
		}

		public void setCurrencyList(List currencyList) {
			this.currencyList = currencyList;
		}


		public String getShowTime() {
			return showTime;
		}

		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}


		public PrpDexchId getPrpDexchId() {
			return prpDexchId;
		}

		public void setPrpDexchId(PrpDexchId prpDexchId) {
			this.prpDexchId = prpDexchId;
		}

		public String getEditType() {
			return editType;
		}

		public void setEditType(String editType) {
			this.editType = editType;
		}

		public String getChkbox() {
			return chkbox;
		}

		public void setChkbox(String chkbox) {
			this.chkbox = chkbox;
		}

		public PrpDexch getPrpDexch() {
	        return prpDexch;
	    }
	    
	    public void setPrpDexch(PrpDexch prpDexch) {
	        this.prpDexch = prpDexch;
	    }
	    public PrpDexchService getPrpDexchService() {
	        return prpDexchService;
	    }
	    public void setPrpDexchService(PrpDexchService prpDexchService) {
	        this.prpDexchService = prpDexchService;
	    }
//---------------------------------------------------------------------
	    public String prepareQueryPrpDexch(){
			currencyMap = new HashMap<String, String>();
			currencyMap.put("", "请选择");
			currencyList = currencyList();
			if(currencyList!=null){
				for(int i =0;i<currencyList.size();i++){
					PrpDnewCode currency = (PrpDnewCode) currencyList.get(i);
					currencyMap.put(currency.getId().getCodeCode(), currency.getCodeCName());
				}
			}
	        return SUCCESS;
	    }
	    
	    public String queryPrpDexch(){
	        try {
	            Page page = prpDexchService.getPrpDexchList(prpDexch, this.pageNo,
	                    this.pageSize);
				this.writeJSONData(page, "id.baseCurrency", "id.exchCurrency", "exchRate","id.exchDate");
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	            }
	        return null;
	    }


		public String prepareInsertPrpDexch() {
			currencyMap = new HashMap<String, String>();
			currencyMap.put("", "请选择");
			currencyList = currencyList();
			if(currencyList!=null){
				for(int i =0;i<currencyList.size();i++){
					PrpDnewCode currency = (PrpDnewCode) currencyList.get(i);
					currencyMap.put(currency.getId().getCodeCode(), currency.getCodeCName());
				}
			}
			
			return SUCCESS;
		}

		public String prepareUpdatePrpDexch() throws ParseException {
//			prpDexch.getId().setExchDate(parseDate(showTime.trim(), "yyyy-MM-dd"));
			currencyMap = new HashMap<String, String>();
			currencyMap.put("", "请选择");
			 
//			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);//创建一个日期格式.
			prpDexch.getId().setExchDate(parseDate(showTime,"yyy-MM-dd"));
			currencyList = currencyList();
			if(currencyList!=null){
				for(int i =0;i<currencyList.size();i++){
					PrpDnewCode currency = (PrpDnewCode) currencyList.get(i);
					currencyMap.put(currency.getId().getCodeCode(), currency.getCodeCName());
				}
			}
			setPrpDexch(prpDexchService.findByPrimaryKey(prpDexch.getId()));
			return SUCCESS;
		}
		
		public String insertPrpDexch() {
			String userCode = getSession().getAttribute("UserCode").toString();
			prpDexchService.insertPrpDexch(prpDexch,userCode);
			return SUCCESS;
		}



		public String updatePrpDexch() {
			String userCode = getSession().getAttribute("UserCode").toString();
			prpDexchService.updatePrpDexch(prpDexch,userCode);
			setEditType("view");
			return SUCCESS;
		}

		public void deletePrpDexch() {
			String[] checkedValues = chkbox.split(",");
			List list = new ArrayList();
	    	for(int i=0;i<checkedValues.length;i++){
	    		String checkedValue = checkedValues[i];
	    		String[] code = checkedValue.split("@");
	    		prpDexchId = new PrpDexchId();
	    		prpDexchId.setBaseCurrency(code[0].trim());
	    		prpDexchId.setExchCurrency(code[1].trim());
	    		prpDexchId.setExchDate(parseDate(code[2].trim(),"yyy-MM-dd"));
				setPrpDexch(prpDexchService
						.findByPrimaryKey(prpDexchId));
//				prpDexchService.deletePrpDexch(prpDexch);
				list.add(prpDexch);
			}
	    	prpDexchService.deleteAll(list);
		}
		
		/**
		 * 将字符串转换成 Date类型
		 * */
		public static java.util.Date parseDate(String dateStr, String formatStr) {
			   SimpleDateFormat format = new SimpleDateFormat(formatStr);
			   try {
				   java.util.Date date = format.parse(dateStr);//格式是Fri Jul 03 00:00:00 CST 2009形式
				   java.sql.Date   date2   =   new   java.sql.Date(date.getTime());//格式是XXXX-XX-XX形式
				   Date ta2 = new java.sql.Timestamp(date2.getTime());   //格式是：XXXX-XX-XX XX:XX形式
				   return date2;
			        } catch (ParseException e) {
			         e.printStackTrace();
			          return null;
			        }
		 }
		/**
		 * 检查主键重复
		 * */
		public void checkPrpDexchKey(){
			if(prpDexchService.isSameKey(prpDexch.getId())){
				 renderText("sameKey");
			}
		}
		public List currencyList(){
			String codeType = "002";//币别的代码类型，币别代码更改的话此处要做相应更改。
			currencyList = prpDcodeService.codeList(codeType);
			return currencyList;
		}
}
