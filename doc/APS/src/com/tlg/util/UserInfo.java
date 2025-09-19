/*
 * Created on 2005/3/2
 * 
 * Preferences - Java - Code Style - Code Templates
 */
package com.tlg.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 使用者登入資訊<br>
 * 使用者登入成功後，會查詢相關資料，並存放在session中。<br>
 * 從session中取得userInfo的做法如下：<br>
 * UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");<br>
 * 
 * <FONT COLOR='RED'>注意：此版本和CoreSystem及FNS下的UserInfo不同</FONT>
 */
@SuppressWarnings("unchecked")
public class UserInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private RelUserRoleService relUserRoleService;
//	
//	private CmnpEmployeeService cmnpEmployeeService;
	
	/**
	 * 使用者id
	 */
	private String userId = "";

	/**
	 * 使用者姓名
	 */
	private String userName = "";
	/**
	 *職稱代碼
	 */
	private String jobCode = "";
	/**
	 * 使用者電話
	 */
	private String tel = "";
	/**
	 * email
	 */
	private String email = "";

	/**
	 * 職稱id
	 */
	private String jobTitle = "";

	/**
	 * 代理人userId
	 */
	private String agent = "";

	private String assignorId = "";

	private String assignorName = "";

	private String assignorEmployeeId = "";

	private String logId = "";
	
	/**
	 * 目錄
	 */
	private StringBuffer menu = new StringBuffer();

	/**
	 * 角色
	 */
	private Vector<String> roleList = new Vector<String>();

	private String superUser = "";

	/**
	 * 出單單位代碼
	 */
	private String detailUnit;
	/**
	 * 出單單位中文名稱
	 */
	private String detailUnitName;
	/**
	 * 出單單位中文名稱(全名)
	 */
	private String detailUnitFullName;
	/**
	 * 出單單位中文名稱(簡稱)
	 */
	private String detailUnitAbbr;
	/**
	 * 出單單位分公司代碼
	 */
	private String branch;
	/**
	 * 出單單位分公司名稱
	 */
	private String branchName;
	/**
	 * 舊出單單位
	 */
	private String oldDetailUnit;
	/**
	 * 舊出單單位名稱
	 */
	private String oldDetailUnitName;
	/**
	 * 舊出單單位名稱(簡稱)
	 */
	private String oldDetailUnitAbbr;
	/**
	 * 舊公司代碼
	 */
	private String oldBranch;
	/**
	 * 舊公司名稱
	 */
	private String oldBranchName;

	/**
	 * 編制部門
	 */	
	private String hrUnit;
	/**
	 * 是否在職
	 */
	private String active;
	/**
	 * 以系統當天日期和禁止出單日來判斷是否已到禁止出單日 
	 * 
	 * default為N
	 * 
	 * Y/N
	 * Y:禁止出單
	 * N:允許出單
	 */
	private String prohibitUse = "N";
	/**
	 * 可以列印的ReportId
	 */
	private String prEnableReportId;
	/**
	 * 對應CMNP_EMPLOYEE. IBU_ORIGIN
	 */
	private String ichannel;
	/**
	 * 對應CMNP_EMPLOYEE. IBRANCH
	 */
	private String irecunit;
	
	private Map<String,Set<String>> sessionIdMap = new HashMap<String,Set<String>>();
	
	
	public void finalize() throws Throwable {

	}

	public UserInfo() {

	}

	public UserInfo(String userId)
			throws Exception {
//		SqlClient sqlClient = null;
		try {
			// 取得使用者所擁有權限
//			sqlClient = Transaction.getNonTsqlConnection();
//			this.relUserRoleService = relUserRoleService;
//			this.cmnpEmployeeService = cmnpEmployeeService;
			this.userId = StringUtil.nullToSpace(userId);

		} catch (Exception e) {
			System.out.println("UserInfo:UserInfo:" + e);
			throw e;
		} finally {
			clean();
//			try {
//				if(sqlClient != null){
//					sqlClient.close();
//					sqlClient = null;
//				}
//			} catch (Exception e) {
//				System.out.println("UserInfo:getUserRoleList:" + e);
//				throw e;
//			}
		}
	}


//	public UserInfo(HttpServletRequest request, Attributes attrs,
//			String userId, Vector roleList) throws Exception {
//
//		try {
//			this.userId = userId;
//			this.roleList = roleList;
//			this.userName = StringUtil.nullToSpace(attrs.get("displayName")
//					.get().toString());
//			this.departmentName = StringUtil.nullToSpace(attrs
//					.get("department").get().toString());
//
//		} catch (Exception e) {
//			System.out.println("UserInfo:UserInfo:" + e);
//			request.setAttribute("exception", e);
//			throw e;
//		} finally {
//			clean();
//		}
//	}

	public UserInfo(HttpServletRequest request, String userId,
			String employeeId, Vector roleList)
			throws Exception {

		try {
			this.setUserId(userId);
			this.setRoleList(roleList);
			this.setAgent("");
		} catch (Exception e) {
			System.out.println("UserInfo:UserInfo:" + e);
			request.setAttribute("exception", e);
			throw e;
		} finally {
			clean();
		}
	}



	/**
	 * 清除垃圾
	 * 
	 * @throws Exception
	 */
	private void clean() throws Exception {
	}




	public String getDetailUnit() {
		return detailUnit;
	}
	
	public String getHrUnit() {
		return hrUnit;
	}

	public String getActive() {
		return active;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the tel.
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            The tel to set.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public StringBuffer getMenu() {
		return menu;
	}

	public void setMenu(StringBuffer menu) {
		this.menu = menu;
	}

	public String getAgent() {
		// if("".equals(this.agent)){
		// return this.userId;
		// }else{
		return agent;
		// }
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return 傳回 roleList。
	 */
	public Vector<String> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList
	 *            要設定的 roleList。
	 */
	public void setRoleList(Vector<String> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return 傳回 logId。
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            要設定的 logId。
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * @return 傳回 jobTitle。
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOldDetailUnit() {
		return oldDetailUnit;
	}

	public void setOldDetailUnit(String oldDetailUnit) {
		this.oldDetailUnit = oldDetailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public void setHrUnit(String hrUnit) {
		this.hrUnit = hrUnit;
	}

	public String getProhibitUse() {
		return prohibitUse;
	}

	public void setProhibitUse(String prohibitUse) {
		this.prohibitUse = prohibitUse;
	}

	public String getDetailUnitName() {
		return detailUnitName;
	}

	public void setDetailUnitName(String detailUnitName) {
		this.detailUnitName = detailUnitName;
	}
	
	public String getDetailUnitFullName() {
		return detailUnitFullName;
	}
	
	public void setDetailUnitFullName(String detailUnitFullName) {
		this.detailUnitFullName = detailUnitFullName;
	}
	
	public String getDetailUnitAbbr() {
		return detailUnitAbbr;
	}
	
	public void setDetailUnitAbbr(String detailUnitAbbr) {
		this.detailUnitAbbr = detailUnitAbbr;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getAssignorId() {
		return assignorId;
	}

	public void setAssignorId(String assignorId) {
		this.assignorId = assignorId;
	}

	public String getAssignorName() {
		return assignorName;
	}

	public void setAssignorName(String assignorName) {
		this.assignorName = assignorName;
	}

	public String getAssignorEmployeeId() {
		return assignorEmployeeId;
	}

	public void setAssignorEmployeeId(String assignorEmployeeId) {
		this.assignorEmployeeId = assignorEmployeeId;
	}

	public String getPrEnableReportId() {
		return prEnableReportId;
	}

	public void setPrEnableReportId(String prEnableReportId) {
		this.prEnableReportId = prEnableReportId;
	}

	public String getOldBranch() {
		return oldBranch;
	}

	public void setOldBranch(String oldBranch) {
		this.oldBranch = oldBranch;
	}

	public String getOldDetailUnitName() {
		return oldDetailUnitName;
	}

	public void setOldDetailUnitName(String oldDetailUnitName) {
		this.oldDetailUnitName = oldDetailUnitName;
	}

	public String getOldDetailUnitAbbr() {
		return oldDetailUnitAbbr;
	}

	public void setOldDetailUnitAbbr(String oldDetailUnitAbbr) {
		this.oldDetailUnitAbbr = oldDetailUnitAbbr;
	}

	public String getOldBranchName() {
		return oldBranchName;
	}

	public void setOldBranchName(String oldBranchName) {
		this.oldBranchName = oldBranchName;
	}

	public String getSuperUser() {
		return superUser;
	}

	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}

	public Map<String, Set<String>> getSessionIdMap() {
		return sessionIdMap;
	}

	public void setSessionIdMap(Map<String, Set<String>> sessionIdMap) {
		this.sessionIdMap = sessionIdMap;
	}

	public String getIchannel() {
		return ichannel;
	}

	public void setIchannel(String ichannel) {
		this.ichannel = ichannel;
	}

	public String getIrecunit() {
		return irecunit;
	}

	public void setIrecunit(String irecunit) {
		this.irecunit = irecunit;
	}
}