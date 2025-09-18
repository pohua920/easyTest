package com.tlg.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.tlg.sys.entity.LoginLog;
import com.tlg.sys.entity.TraceRecord;
import com.tlg.sys.service.AuthorityService;
import com.tlg.sys.service.LoginLogService;
import com.tlg.sys.service.TraceRecordService;
import com.tlg.sys.util.AuthWithoutCheck;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

@SuppressWarnings("serial")
public class MethodAuthorizationInterceptor extends MethodFilterInterceptor{

	private AuthorityService authorityService;
	private LoginLogService loginLogService;
	private TraceRecordService traceRecordService;
//	private SearchInfoLogService searchInfoLogService;

	@SuppressWarnings("unchecked")
	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception  {
		String message = "請您重新登入~";
		Map queryfilter = null;
		ServletContext sc = null;
		String actionResult = "";
		try{
			String namespace = ai.getProxy().getNamespace();
			String systemId = "";
			if(!"".equals(namespace)){
//				systemId = namespace.substring(1,namespace.lastIndexOf("/")).toUpperCase();
				systemId = namespace.split("/")[1].toUpperCase();
				if(systemId.indexOf("UTILS") != -1){
					systemId = "UTILS/AJAX";
				}
			}
			
			
			
			String actionName = ai.getProxy().getAction().getClass().getSimpleName();
			String actionPageAuthorityName = systemId + "-" + actionName + "PageAuthority";
			String actionMethod = ai.getProxy().getMethod();
			//不須檢查
			if(AuthWithoutCheck.neverCheckProgram(systemId, actionName.toUpperCase())){
				return ai.invoke();
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			Map session = ai.getInvocationContext().getSession();
			if(session == null || session.get("userInfo") == null){
				if (!"/sys/001".equals(namespace) && !"btn_login".equals(actionMethod)) {
					request.getSession().invalidate();
					request.setAttribute("message",message);
					return "index";
				}
			}
			
			if (!"/sys/001".equals(namespace) && !"/sys/002".equals(namespace) && !"btn_login".equals(actionMethod)) {
				System.out.println(">>> MethodAuthorizationInterceptor doIntercept start ");
				long start = System.currentTimeMillis();
				
				sc = ServletActionContext.getServletContext();
				UserInfo ufo = (UserInfo)session.get("userInfo");
				//檢查LoginLog是否存在，若不存在則表示已被登出
				queryfilter = new HashMap();
				queryfilter.put("userId", ufo.getUserId());
				queryfilter.put("logId", ufo.getLogId());
//				queryfilter.put("sessionId", request.getSession().getId());

				int loginLogCount = loginLogService.countLoginLog(queryfilter);
				if(loginLogCount == 0){
					request.getSession().invalidate();
					return "index";
				}
				if("execute".equals(actionMethod)){
					actionMethod = "default";
				}
				if("Y".equals(sc.getAttribute("isOuter"))){
					if(!AuthWithoutCheck.checkOutterHostProgram(systemId,actionName)){
						request.setAttribute("message", "您沒有權限使用此功能！");
						return "input";
					}
				}
				
				Result result = loginLogService.findLoginLogByParams(queryfilter);
				Collection loginLogCol = (ArrayList)result.getResObject();
				//檢查是否要檢查權限
				if("Y".equals(sc.getAttribute("authorityCheck"))){
					ArrayList authorityList = null;
					if(session.get(actionPageAuthorityName) != null){
						authorityList = (ArrayList)session.get(actionPageAuthorityName);
					}else{
						queryfilter.clear();
						queryfilter.put("systemId", systemId.toUpperCase());
						queryfilter.put("programId", actionName.toUpperCase());
						queryfilter.put("functionId", actionMethod.toUpperCase());
						int count = authorityService.jurisdictionCount(queryfilter);
						if(count == 0){
							request.setAttribute("message", "您沒有權限使用此功能！");
							return "input";
						}

						authorityList = (ArrayList)authorityService.jurisdictionFindByPage(systemId.toUpperCase(),actionName.toUpperCase(), ufo.getUserId(), ufo.getRoleList()).getResObject();
					}
					session.put(actionPageAuthorityName, authorityList);
					
					if(!authorityList.contains(actionMethod)){
						request.setAttribute("message", "您沒有權限使用此功能！");
						return "input";
					}
//					if(count == 0){
//						request.setAttribute("message", "您沒有權限使用此功能！");
//						return "input";
//					}
				}

				//判斷使用是否須要記錄查詢條件
				Map<String,String[]> parameterMap = request.getParameterMap();
				Map<String, Object> tmpMap = new HashMap<String, Object>();
				tmpMap.putAll(parameterMap);
//				searchInfoLogService.createSearchInfoLogFromIntercept(systemId, actionName, actionMethod, tmpMap, ufo);
				
				//確認是否要紀錄使用者動作
				if("Y".equals(sc.getAttribute("enableTraceUserRecord"))){
					//新增使用者操作紀錄
					TraceRecord traceRecord = new TraceRecord();
					traceRecord.setUserId(ufo.getUserId());
					traceRecord.setSystemId(systemId);
					traceRecord.setProgramId(actionName);
					traceRecord.setFunctionId(actionMethod);
					traceRecord.setIp(request.getRemoteAddr());
					traceRecord.setOriginalLogId(ufo.getLogId());
					traceRecord.setServerIp(InetAddress.getLocalHost().getHostName());
					traceRecord.setServerPort(String.valueOf(request.getLocalPort()));
					traceRecord.setCreater(ufo.getUserId());
					traceRecord.setCreatetime(new Timestamp(new Date().getTime()));
					
					result = traceRecordService.insertTraceRecord(traceRecord, ufo);
					if(result.getResObject() == null){
						//TODO
						//新增TraceRecord 失敗
					}
				}
				
				actionResult = ai.invoke();
				session = ai.getInvocationContext().getSession();
				
				//修改LoginLog
				Iterator it = (Iterator) loginLogCol.iterator();
				while(it.hasNext()){
					LoginLog login = (LoginLog)it.next();
					login.setServerIp(InetAddress.getLocalHost().getHostName());
					login.setServerPort(String.valueOf(request.getLocalPort()));
					login.setContextPath(request.getContextPath());
					login.setCurrentSystem(systemId);
					login.setCurrentProgram(actionName);
					login.setModifyer(ufo.getUserId());
					login.setModifytime(new Timestamp(new Date().getTime()));
					Map<String,Object> map = null;
					if(login.getSessionobj() != null){
						map = (HashMap<String,Object>)login.toObject(login.getSessionobj());
					}else{
						map = new HashMap<String,Object>();
					}
					map.putAll(session);
					login.setSessionobj(login.toBytes(map));
					result = loginLogService.updateLoginLog(login, ufo);
					if(result.getResObject() == null){
						//TODO
						//修改LoginLog失敗
					}
					session.putAll(map);
					ai.getInvocationContext().setSession(session);
				}
				long end = System.currentTimeMillis();
				System.out.println(">>> MethodAuthorizationInterceptor doIntercept end " + ( end - start));
			}else{
				return ai.invoke();
			}
		}catch (Exception e) {
			StringBuffer callerStr = new StringBuffer();
			//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400 START
			//因應 sql server環境升級:jdk1.7->1.8/tomcat7->8:修改1.factory 2.mssql url add ;encrypt=false
//			callerStr.append(
//					sun.reflect.Reflection.getCallerClass(2) + " >>>" + 
//					sun.reflect.Reflection.getCallerClass(3) + " >>> "	+ 
//					sun.reflect.Reflection.getCallerClass(4) + " >>> " + 
//					sun.reflect.Reflection.getCallerClass(5) + " >>> " + 
//					sun.reflect.Reflection.getCallerClass(6) + " >>> " + 
//					sun.reflect.Reflection.getCallerClass(7) + " >>> " + 
//					sun.reflect.Reflection.getCallerClass(8) + " "
//			);
			//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400 END
			System.out.println(callerStr.toString());
			e.printStackTrace();
			
			throw e;
		}finally{
			queryfilter = null;
		}

		return actionResult;
	}
	
	/**
	 * 判斷是否為外網主機
	 * @param host
	 * @return
	 * @throws UnknownHostException 
	 */
	private boolean checkOutterHost() throws UnknownHostException{
		boolean isOuter = false;
//		System.out.println(">>>>>>>>>>> 主機：" + InetAddress.getLocalHost().getHostName());
		/**
		 * TODO 暫時先不擋，看後續討論再決定
		 */
//		if("unb2b-02".equals(InetAddress.getLocalHost().getHostName()) && "CAR".equals(systemId)){
//			return true;
//		}
		return isOuter;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public TraceRecordService getTraceRecordService() {
		return traceRecordService;
	}

	public void setTraceRecordService(TraceRecordService traceRecordService) {
		this.traceRecordService = traceRecordService;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public LoginLogService getLoginLogService() {
		return loginLogService;
	}

//	public SearchInfoLogService getSearchInfoLogService() {
//		return searchInfoLogService;
//	}
//
//	public void setSearchInfoLogService(SearchInfoLogService searchInfoLogService) {
//		this.searchInfoLogService = searchInfoLogService;
//	}

}
