package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeComId;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDtypeService;
import cn.com.sinosoft.saa.util.OpenExcel;
public class PrpDcodeAction extends Struts2Action {

	/**
	 * 代码
	 */
	private static final long serialVersionUID = 1L;
	private PrpDcodeService prpDcodeService;
	private PrpDtypeService prpDtypeService;
	private PrpDnewCode prpDcode;
	private PrpDcompany prpDcompany;
	private PrpDnewCodeCom prpDnewCodeCom;
	private PrpDnewCodeComId prpDnewCodeComId;
	private String codeType;
	private String codeCode;
	private String editType;
	private String chkbox;
	private String uplevel;// 上级代码的codeCode
	private PrpDtype prpDtype;
	private Map<String, String> upCodeMap;
	//add by duanfa 2011-06-23 批量导入
	private File importFile;
	
	
	public Map<String, String> getUpCodeMap() {
		return upCodeMap;
	}

	public void setUpCodeMap(Map<String, String> upCodeMap) {
		this.upCodeMap = upCodeMap;
	}

	public PrpDtypeService getPrpDtypeService() {
		return prpDtypeService;
	}

	public void setPrpDtypeService(PrpDtypeService prpDtypeService) {
		this.prpDtypeService = prpDtypeService;
	}

	public String getUplevel() {
		return uplevel;
	}

	public void setUplevel(String uplevel) {
		this.uplevel = uplevel;
	}

	public PrpDtype getPrpDtype() {
		return prpDtype;
	}

	public void setPrpDtype(PrpDtype prpDtype) {
		this.prpDtype = prpDtype;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDnewCode getPrpDcode() {
		return prpDcode;
	}

	public void setPrpDcode(PrpDnewCode prpDcode) {
		this.prpDcode = prpDcode;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeCode() {
		return codeCode;
	}

	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
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
// add by duanfa 2011-06-23 批量导入方法
	public String importCodeExcel(){
		String userCode = getSession().getAttribute("UserCode").toString();	
		OpenExcel excelUtil = new OpenExcel();
		List<Object[]> content = null;
		try {
			content = excelUtil.readExcel(importFile, 1, 1, 0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Object[] row:content ){
			if(row[0]!=null&&!row[0].toString().trim().equals("")&&
					row[2]!=null&&!row[2].toString().trim().equals("")&&
					row[3]!=null&&!row[3].toString().trim().equals("")&&
					row[4]!=null&&!row[4].toString().trim().equals("")&&
					row[9]!=null&&!row[9].toString().trim().equals("")
			){
				PrpDnewCode prpdcode = new PrpDnewCode();
				prpDtype = prpDtypeService.findByPrimaryKey(row[0].toString());
				if(prpDtype==null){
					//excel 导入文件中代码类型不正确
					continue;
				}
				prpdcode.setPrpDtype(prpDtype);
				PrpDnewCodeId id = new PrpDnewCodeId();
				id.setCodeCode(row[2].toString().trim());
				id.setCodeType(prpDtype.getCodeType());
				prpdcode.setId(id);
				
				prpdcode.setNewCodeCode(row[2].toString().trim());
				prpdcode.setCodeCName(row[3].toString().trim());
				prpdcode.setCodeEName(row[4].toString().trim());
				prpdcode.setCodeLevel(row[5]==null?"":row[5].toString().trim());
				prpdcode.setUpperCode(row[6]==null?"":row[6].toString().trim());
				prpdcode.setCodeCdesc(row[7]==null?"":row[7].toString().trim());
				prpdcode.setUpperCode(row[8]==null?"":row[8].toString().trim());
				String validStatus = row[9].toString().trim().substring(0,1);
				if(validStatus.equals("0")){
					prpdcode.setValidStatus(validStatus);
				}else if(validStatus.equals("1")){
					prpdcode.setValidStatus(validStatus);
				}else{
					continue;
				}
				prpDcodeService.insertPrpDcode(prpdcode, userCode);
			}
		}
		
		return SUCCESS;
	}
	
	
	
	// -------------------------------------------------------------
	public String prepareQueryPrpDcode() {
		prpDtype = prpDtypeService.findByPrimaryKey(codeType);
		return SUCCESS;
	}

	public String prepareInsertPrpDcode() {
//		upCodeMap = prpDcodeService.upCodeMap(prpDcode.getId().getCodeType());
		return SUCCESS;
	}

	public String prepareUpdatePrpDcode() throws Exception {
		/*****************************2010-1-14****去掉上下级关系*******start********
		uplevel = prpDcodeService.getuplevel(prpDcode.getId());
		upCodeMap = prpDcodeService.upCodeMap(prpDcode.getId().getCodeType());
		upCodeMap.remove(prpDcode.getId().getCodeCode());// 删除代码类型是本身的数据防止用户选择自己作为上级代码！
		**********************************去掉上下级关系***end************/
		setPrpDcode(prpDcodeService.findByPrimaryKey(prpDcode.getId()));
		if(prpDcode.getOldCodeType()!= null){
			prpDcode.setOldCodeType(prpDcode.getOldCodeType().trim());
		}
		return SUCCESS;
	}

	public String insertPrpDcode() throws Exception {
		String userCode = getSession().getAttribute("UserCode").toString();	
		prpDcode.setNewCodeCode(prpDcode.getId().getCodeCode());
		prpDcodeService.insertPrpDcode(prpDcode,userCode);
		/*delete by duanfa 2011-06-22 直接保存，不进行总颁代码的验证
		 * String comCode =  prpDnewCodeCom.getId().getComCode();//从页面中获得prpdnewcodecom的comCode字段的值
		HttpServletRequest request = getRequest();
		String value = request.getParameter("radio1");
		//判断，如果等于总公司，comCode的值是空或者null，那么增加总颁代码，prpDnewCode
			if(value.equals("1")){
				prpDcodeService.insertPrpDcode(prpDcode,userCode);
				}		
			//判断，如果等于分公司，comCode的值不为空并且不等于null，那么增加省颁代码，prpDnewCodeCom
			else {
				PrpDnewCodeCom prpDnewCodeCom = new PrpDnewCodeCom();
				PrpDnewCodeComId prpDnewCodeComId = new PrpDnewCodeComId();
				prpDnewCodeComId.setComCode(comCode);
				prpDnewCodeComId.setCodeType(prpDcode.getId().getCodeType());
				prpDnewCodeComId.setCodeCode(prpDcode.getId().getCodeCode());
				prpDnewCodeCom.setId(prpDnewCodeComId);
				prpDnewCodeCom.setCodeCName(prpDcode.getCodeCName());
				prpDnewCodeCom.setCodeEName(prpDcode.getCodeEName());
				prpDnewCodeCom.setUpperCode(prpDcode.getUpperCode());
				prpDnewCodeCom.setNewCodeCode(prpDcode.getNewCodeCode());
				prpDnewCodeCom.setOldCodeCode(prpDcode.getOldCodeCode());
				prpDnewCodeCom.setCommonFlag(prpDcode.getCommonFlag());
				prpDnewCodeCom.setValidDate(prpDcode.getValidDate());
				prpDnewCodeCom.setInvalidDate(prpDcode.getInvalidDate());
				prpDnewCodeCom.setValidStatus(prpDcode.getValidStatus());
				prpDnewCodeCom.setFlag(prpDcode.getFlag());
				prpDcodeService.insertPrpDnewCodeCom(prpDnewCodeCom, userCode);
			}		*/
		return SUCCESS;
	}

	public String queryPrpDcode() {
		try {
			Page page = prpDcodeService.getPrpDcodeList(prpDcode,this.pageNo,
					this.pageSize);
			this.writeJSONData(page, "id.codeType", "id.codeCode", "codeCName",
					"codeEName", "validStatus");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	public String updatePrpDcode() throws Exception {
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDcodeService.updatePrpDcode(prpDcode,userCode);
		setEditType("view");
		return SUCCESS;
	}

	public void deletePrpDcode() throws Exception {
		// 以后在jsp页面增加多选框实现批量删除
		String[] checkedValues = chkbox.split(",");
		for (int i = 0; i < checkedValues.length; i++) {
			String checkedValue = checkedValues[i];
			String[] code = checkedValue.split("@");
			PrpDnewCodeId prpDcodeId = new PrpDnewCodeId();
			prpDcodeId.setCodeCode(code[0].trim());
			prpDcodeId.setCodeType(code[1].trim());
			setPrpDcode(prpDcodeService.findByPrimaryKey(prpDcodeId));
			prpDcodeService.deletePrpDcode(prpDcode);
		}
	}

	public void changeValidStatus() {
		String userCode = getSession().getAttribute("UserCode").toString();
		PrpDnewCodeId id = prpDcode.getId();
		prpDcode = prpDcodeService.findByPrimaryKey(id);
		String validStatus = prpDcode.getValidStatus();
		if ("1".equals(validStatus)) {
			prpDcode.setValidStatus("0");
			prpDcodeService.updatePrpDcode(prpDcode,userCode);
		} else {
			prpDcode.setValidStatus("1");
			prpDcodeService.updatePrpDcode(prpDcode,userCode);

		}
	}

	public PrpDnewCodeCom getPrpDnewCodeCom() {
		return prpDnewCodeCom;
	}

	public void setPrpDnewCodeCom(PrpDnewCodeCom prpDnewCodeCom) {
		this.prpDnewCodeCom = prpDnewCodeCom;
	}

	public PrpDcompany getPrpDcompany() {
		return prpDcompany;
	}

	public void setPrpDcompany(PrpDcompany prpDcompany) {
		this.prpDcompany = prpDcompany;
	}

	public PrpDnewCodeComId getPrpDnewCodeComId() {
		return prpDnewCodeComId;
	}

	public void setPrpDnewCodeComId(PrpDnewCodeComId prpDnewCodeComId) {
		this.prpDnewCodeComId = prpDnewCodeComId;
	}

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

}
