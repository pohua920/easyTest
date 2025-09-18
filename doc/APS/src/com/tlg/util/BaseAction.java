package com.tlg.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware, ServletContextAware {

	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(this.getClass());

	private PageInfo pageInfo;
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)START
	private PageInfo pageInfo2;
	private String pageInfoName2;
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)END

	private Map<String, String> filter;

	private String pageInfoName;

	private Map<String, Object> session;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private ServletContext context;

	private Map<String, String> rptParam;

	public BaseAction() {

	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
		session.put(this.getPageInfoName(), this.pageInfo);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getFilter() {
		if (pageInfo.getFilter() == null) {
			this.pageInfo.setFilter(new HashMap());
			session.put(this.getPageInfoName(), this.pageInfo);
			this.filter = new HashMap<String, String>();
		} else {
			this.filter = this.pageInfo.getFilter();
		}
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
		this.pageInfo.setFilter(this.filter);
	}

	public String getPageInfoName() {
		this.pageInfoName = this.getRequest().getContextPath().replaceAll("/", "") + "-" + this.getClass().getSimpleName() + "PageInfo";
		return this.pageInfoName;
	}

	public void setPageInfoName(String pageInfoName) {
		this.pageInfoName = pageInfoName;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.pageInfo = (PageInfo) session.get(this.getPageInfoName());
		if (this.pageInfo == null) {
			this.pageInfo = new PageInfo();
			this.filter = new HashMap<String, String>();
			this.pageInfo.setPageSize(10);
			this.pageInfo.setFilter(this.filter);
			session.put(this.getPageInfoName(), this.pageInfo);
		}
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ServletContext getContext() {
		return context;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.context = arg0;
	}

	public String execute() throws Exception {
		String target = "success";
		try {

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return target;
	}

	// 報表method-------------------------------------------------
	public String getReportRealPath() {
		return Constants.rpTemplatePath;
	}

	public Map<String, String> getRptParam() {
		return rptParam;
	}

	public void setRptParam(Map<String, String> rptParam) {
		this.rptParam = rptParam;
	}

	// 共用method-------------------------------------------------
	// 存訊息在 requestScope
	public void setMessage(String msg) throws Exception {
		if (!StringUtil.isSpace(msg)) {
			getRequest().setAttribute("message", msg);
		}
	}

	// 取得UserInfo
	public UserInfo getUserInfo() {
		return (UserInfo) getSession().get("userInfo");
	}
	
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)START
	public PageInfo getPageInfo2() {
		if(pageInfo2 == null) {
			if(session.containsKey(this.getRequest().getContextPath().replaceAll("/", "") + "-" + this.getClass().getSimpleName() + "PageInfo2")) {
				PageInfo info2 = (PageInfo) session.get(this.getRequest().getContextPath().replaceAll("/", "") + "-" + this.getClass().getSimpleName() + "PageInfo2");
				if(info2 != null) {
					pageInfo2 = info2;
				}
			}
		}
		return pageInfo2;
	}

	public void setPageInfo2(PageInfo pageInfo2) {
		this.pageInfo2 = pageInfo2;
		session.put(this.getPageInfoName2(), this.pageInfo2);
	}
	

	public String getPageInfoName2() {
		this.pageInfoName2 = this.getRequest().getContextPath().replaceAll("/", "") + "-" + this.getClass().getSimpleName() + "PageInfo2";
		return pageInfoName2;
	}

	public void setPageInfoName2(String pageInfoName2) {
		this.pageInfoName2 = pageInfoName2;
	}
	
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)END

}