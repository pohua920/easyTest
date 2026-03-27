package cn.com.sinosoft.ims.svr.web;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.ims.svr.model.UtiISvr;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;
import cn.com.sinosoft.ims.util.IConstants;

public class UtiISvrAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private UtiISvrService utiISvrService;
	private UtiISvr utiISvr;
	private String creatorName;
	private String updaterName;
	private String companyName;
	private String companyCode;
	private String utilitySvrCode;
	private String svrCodeInCompany;
	private String svrCode;
	private String type;
	private Map<Integer, String> companyCodeMap = new HashMap<Integer, String>();
	private Map<String,String>  svrCodeMap =new HashMap<String,String>();
	private List companylist = new ArrayList();
	private String position;

	// private SaaTask task = new SaaTask ();

	/*
	 * 查询服务--跳转到页面
	 */
	public String prepareQuerySvr() {

		return "SUCCESS";
	}

	/*
	 * 查询服务
	 */
	@SuppressWarnings( { "static-access", "unchecked" })
	public String querySvr() {
		String userCode = (String)getSession().getAttribute("UserCode");
		
		try {
			// utiISvr = (UtiISvr) getSession().getAttribute("utiISvr");
			Page page = utiISvrService.getSvrList(utiISvr, userCode,this.pageNo,
					this.pageSize);
			this.writeJSONData(page, "svrCode", "svrName", "position", "companyCode",
					"svrCodeInCompany", "svrType", "validStatus",
					"svrLoginMethod", "creatorName", "updaterName");
		} catch (Exception e) {
		//	e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	/*
	 * 增加服务--跳转到页面
	 */
	public String selectPost(){
		return "success";
	}
	public String prepareInsertSvr() {
		utiISvr = null;
		String userCode= (String) getSession().getAttribute("UserCode");
		svrCodeMap = utiISvrService.getSvrListMap(userCode);
		companyCodeMap = utiISvrService.getCompanyListMap();
		return "SUCCESS";
	}

	/*
	 * 增加服务
	 */
	public String insertSvr() {
		// String message = "";
		try {
//			String result = checkFormula(utiISvr.getSvrLoginMethod());
//			if (result.equals("false")) {
//				return "FAIL";
//			} else {
				String UserCode = (String) getSession()
						.getAttribute("UserCode");
				String scode = utiISvr.getSvrCode();
				
				
				if (utiISvrService.getSvrByCode(scode) != null) {
					return "REPEAT";
				}
				if("1".equals(position)){
					utiISvr.setCompanyCode(IConstants.ComCode_Head);
					utiISvrService.insertSvrMethod(UserCode, "", utiISvr);
				}else{
					companyCodeMap = utiISvrService.getCompanyListMap();
				    companyName = companyCodeMap.get(companyCode);
					utiISvrService.insertSvrMethod(UserCode, companyCode, utiISvr);
				}
				return "SUCCESS";
//			}
		} catch (Exception e) {
		 e.printStackTrace();
			return "FAIL";
		}
	}

	/*
	 * 修改服务--获取服务代码
	 */
	public String prepareModifySvr() {
		if("省集中".equals(position)){
			position = "2";
		}else{
			position = "1";
		}
		// String svrcode = getRequest().getParameter("svrcode");
		utiISvr = utiISvrService.getSvrByCode(svrCode);
		String UserCode = (String) getSession()
		.getAttribute("UserCode");
		svrCodeMap = utiISvrService.getSvrListMap(UserCode);
		companyCodeMap = utiISvrService.getCompanyListMap();
//		companylist = utiISvrService.getCompanyCodeList();
		companyCode = utiISvr.getCompanyCode();
//		if(code != null || "".equals(code)){
//			companyName = (String) companyCodeMap.get(code);
//		}
//		 task = utiISvrService.getTaskByCode(svrcode);
//		 getRequest().setAttribute("parentCode", task.getParentCode());
		transWords(utiISvr);
		return "SUCCESS";

	}

	/*
	 * 修改服务
	 */
	public String modifySvr() {
		String name = "";
		String UserCode = (String) getSession().getAttribute("UserCode");
	//	companyCodeMap = utiISvrService.getCompanyCodeMap();
		companyCodeMap = utiISvrService.getCompanyListMap();
		if(companyName==null){
			name = "";
		}else{
//			name = companyCodeMap.get(Integer.parseInt(companyName));
			companyCode = utiISvrService.getSystemCodeByName(name);
		}
		Date updateTime = new Date(System.currentTimeMillis());
		utiISvr.setCompanyCode(companyCode);
		utiISvr.setUpdaterCode(UserCode);
		utiISvr.setUpdateDate(updateTime);
		utiISvrService.modifySvrMethod(UserCode, utiISvr);
		// String taskCode = utiISvr.getSvrCode();
		// task = utiISvrService.getTaskByCode(taskCode);
		// utiISvrService.modifyTaskMethod(UserCode, task, utiISvr.getSvrName()
		// );
		return "SUCCESS";
	}

	/*
	 * 查看服务
	 */
	public String viewSvr() {
		String userCode = (String)getSession().getAttribute("UserCode");
		svrCodeMap = utiISvrService.getSvrListMap(userCode);
		if("省集中".equals(position)){
			position = "2";
		}else{
			position = "1";
		}
		companyCodeMap = utiISvrService.getCompanyCodeMap();
		utiISvr = utiISvrService.getSvrByCode(svrCode);
		if (utiISvr.getCreatorCode() != null) {
			creatorName = utiISvrService.getUserNameByCode(utiISvr
					.getCreatorCode());
		}
		if (utiISvr.getUpdaterCode() != null) {
			updaterName = utiISvrService.getUserNameByCode(utiISvr
					.getUpdaterCode());
		}
		if (utiISvr.getCompanyCode() != null) {
			companyCode = utiISvr.getCompanyCode();
			companyName = utiISvrService.getCompanyNameByCode(companyCode);
		}
		transWords(utiISvr);
		return "SUCCESS";
	}


	/*
	 * 注销/启动服务
	 */
	public String changeValidStatus() {
		// String codeListNew ="";
		// HttpServletRequest request =
		// (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		// String codeList =getRequest().getParameter("svrcode");
		utiISvr = utiISvrService.getSvrByCode(svrCode);
		// System.out.println(codeList);
		// String[] svrcodes = codeList.split(" and ");
		// System.out.println(svrcodes[0].toString());
		utiISvrService.changeValids(utiISvr);
		// request.setAttribute("utiISvr", utiISvr);
		return "SUCCESS";
	}

	/*
	 * 查询服务状态--页面的跳转
	 */
	public String prepareQuerySvrParam() {

		return "SUCCESS";
	}

	/*
	 * 查询服务状态
	 */
	/*
	 * public String svrStatusQuery(){ try { Page page =
	 * utiISvrService.getSvrList(utiISvr, this.pageNo, this.pageSize);
	 * this.writeJSONData(page, "svrCode", "svrName","manageRightStatus"
	 * ,"manageMenuStatus"
	 * ,"manageLoginStatus","manageAccStatus","accSyncStatus",
	 * "accMsgSyncStatus","accLoginStatus"); } catch (Exception e) {
	 * this.writeJSONMsg(e.getMessage()); } return null; }
	 */
	/*
	 * 更改管理权限状态
	 */
	public String changeStatus() {
		String codeList = getRequest().getParameter("svrcode");
		String id = getRequest().getParameter("status");
		String[] svrcodes = codeList.split(" and ");
		utiISvrService.changeStatus(svrcodes, id, utiISvr);
		return "SUCCESS";
	}

	/*-------- 校验认证方式----------------------------*/

	public String checkFormula(String svrLoginMethod) throws Exception {
		Binding binding = new Binding();
		String value = null;
		String flag = "true";
		binding.setVariable("card", true);
		binding.setVariable("usbkey", true);
		binding.setVariable("nameAndPwd", true);
		GroovyShell shell = new GroovyShell(binding);
		try {
			value = (String)shell.evaluate(svrLoginMethod);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "false";
		} finally {
			return flag;
		}
	}

	/*-----------页面字符转换-----------------------*/
	public void transWords(UtiISvr utiISvr) {
		String loginMethods = utiISvr.getSvrLoginMethod();
		if (loginMethods == null) {
			return;
		}
		if (loginMethods.indexOf("card") >= 0) {
			loginMethods = loginMethods.replace("card",
					IConstants.SVRLOGINMETHOD_CARD);
		}
		if (loginMethods.indexOf("usbkey") >= 0) {
			loginMethods = loginMethods.replace("2",
					IConstants.SVRLOGINMETHOD_USBKEY);
		}
		if (loginMethods.indexOf("nameAndPwd") >= 0) {
			loginMethods = loginMethods.replace("nameAndPwd",
					IConstants.SVRLOGINMETHOD_NAMEPWD);
		}
		if (loginMethods.indexOf("&") >= 0) {
			loginMethods = loginMethods.replace("&", "和");
		}
		if (loginMethods.indexOf("|") >= 0) {
			loginMethods = loginMethods.replace("|", "或");
		}
		// System.out.println(loginMethods);
		getRequest().setAttribute("loginMethods", loginMethods);
	}

	/*--------------------getter and setter---------------------------------------------------*/

	public String setMethod() {
		return "SUCCESS";
	}

	public UtiISvrService getUtiISvrService() {
		return utiISvrService;
	}

	public void setUtiISvrService(UtiISvrService utiISvrService) {
		this.utiISvrService = utiISvrService;
	}

	public UtiISvr getUtiISvr() {
		return utiISvr;
	}

	public void setUtiISvr(UtiISvr utiISvr) {
		this.utiISvr = utiISvr;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSvrCodeInCompany() {
		return svrCodeInCompany;
	}

	public void setSvrCodeInCompany(String svrCodeInCompany) {
		this.svrCodeInCompany = svrCodeInCompany;
	}

	public Map getCompanyCodeMap() {
		return companyCodeMap;
	}

	public void setCompanyCodeMap(Map companyCodeMap) {
		this.companyCodeMap = companyCodeMap;
	}

	public String getSvrCode() {
		return svrCode;
	}

	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map getSvrCodeMap() {
		return svrCodeMap;
	}

	public void setSvrCodeMap(Map svrCodeMap) {
		this.svrCodeMap = svrCodeMap;
	}

	public String getUtilitySvrCode() {
		return utilitySvrCode;
	}

	public void setUtilitySvrCode(String utilitySvrCode) {
		this.utilitySvrCode = utilitySvrCode;
	}
	
}
