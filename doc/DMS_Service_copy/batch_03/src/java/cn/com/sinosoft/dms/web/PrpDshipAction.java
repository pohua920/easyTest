package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDshipService;

public class PrpDshipAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private PrpDshipService prpDshipService;
	private PrpDship prpDship;
	private String shipCode;
	private String editType;
	private String chkbox;
	private Map<String, String> currencyMap;
	private List currencyList;
	private PrpDcodeService prpDcodeService;

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

	public Map<String, String> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(Map<String, String> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public String getChkbox() {
		return chkbox;
	}

	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}

	public PrpDshipService getPrpDshipService() {
		return prpDshipService;
	}

	public void setPrpDshipService(PrpDshipService prpDshipService) {
		this.prpDshipService = prpDshipService;
	}

	public PrpDship getPrpDship() {
		return prpDship;
	}

	public void setPrpDship(PrpDship prpDship) {
		this.prpDship = prpDship;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String prepareQueryPrpDship() {
		// 此处填补权限控制逻辑
		return SUCCESS;
	}

	public String prepareInsertPrpDship() {
		currencyMap = new HashMap<String, String>();
		currencyMap.put("" , "请选择");
		currencyList = currencyList();
		if(currencyList!=null){
			for(int i =0;i<currencyList.size();i++){
				PrpDnewCode currency = (PrpDnewCode) currencyList.get(i);
				currencyMap.put(currency.getId().getCodeCode(), currency.getCodeCName());
			}
		}
		return SUCCESS;
	}

	public String insertPrpDship() {
		logger.debug("【插入新的代码】");
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDshipService.insertPrpDship(prpDship,userCode);
		return SUCCESS;
	}

	public String queryPrpDship() {
		logger.debug("【查询金融机构代码prpDship开始】");
		try {
			Page page = prpDshipService.getPrpDshipList(prpDship, this.pageNo,
					this.pageSize);
			logger.debug("【查询结果数：" + page.getTotalCount() + "】");
			this.writeJSONData(page, "shipCode", "shortHandCode", "shipCName",
					"shipEName", "validStatus");
			logger.debug("【writeJSONData over】");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	public String prepareUpdatePrpDship() {
		// TODO 编辑代码的权限校验
		currencyMap = new HashMap<String, String>();
		currencyMap.put("" , "请选择");
		currencyList = currencyList();
		if(currencyList!=null){
			for(int i =0;i<currencyList.size();i++){
				PrpDnewCode currency = (PrpDnewCode) currencyList.get(i);
				currencyMap.put(currency.getId().getCodeCode(), currency.getCodeCName());
			}
		}
		setPrpDship(prpDshipService.findByPrimaryKey(getShipCode()));
		return SUCCESS;
	}

		public List currencyList(){
			String codeType = "002";//币别的代码类型，币别代码更改的话此处要做相应更改。
			currencyList = prpDcodeService.codeList(codeType);
			return currencyList;
		}

	public String updatePrpDship() {
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDshipService.updatePrpDship(prpDship,userCode);
		setEditType("view");
		return SUCCESS;
	}

	public void deletePrpDship() {
		List list = new ArrayList();
		String[] checkedValues = chkbox.split(",");
    	for(int i=0;i<checkedValues.length;i++){
    		String code = checkedValues[i];
			setPrpDship(prpDshipService.findByPrimaryKey(code));
//			prpDshipService.deletePrpDship(prpDship);
			list.add(prpDship);
		}
    	prpDshipService.deleteAll(list);
	}
	
    public void changeValidStatus(){
    	String userCode = getSession().getAttribute("UserCode").toString();
		shipCode = prpDship.getShipCode();
		prpDship = prpDshipService.findByPrimaryKey(shipCode);
		String validStatus = prpDship.getValidStatus();
		if("1".equals(validStatus)){
				prpDship.setValidStatus("0");
				prpDshipService.updatePrpDship(prpDship,userCode);
		}else{
				prpDship.setValidStatus("1");
				prpDshipService.updatePrpDship(prpDship,userCode);
		}
	}
}
