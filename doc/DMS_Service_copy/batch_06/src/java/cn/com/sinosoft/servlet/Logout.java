package cn.com.sinosoft.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.sinosoft.ims.log.model.UtiILoginLog;
import cn.com.sinosoft.ims.log.service.facade.UtiILoginLogService;

import com.sinosoft.dmsdriver.service.server.DictAPIService;

public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// ------------保存用户退出时间 和 持续时间-------
		UtiILoginLog utiILoginLog = (UtiILoginLog) session
				.getAttribute("utiILoginLog");
		if (utiILoginLog == null) {
			if (session.getAttribute("edu.yale.its.tp.cas.client.filter.user") != null) {
				String casserverUrl = "";// 单点登录应用地址
				try {
					// 通过IP服务获取单点登录应用地址
					casserverUrl = DictAPIService.getUrlByCode("casServer");
				} catch (Exception e) {
					e.printStackTrace();
				}
				//modify by liuxiaofei 20110810 reason:单点登录退出时，无法正确跳转到登录页面     begin
//				String serviceUrl = "http://" 
//						+ request.getServerPort() + request.getContextPath();
				//modify by liuyikai 20121128 reason: request.getLocalAddr()没有这个方法，从逻辑上看应该是获取IP地址 begin
				String serviceUrl = "http://" + request.getRemoteHost() + ":"+ request.getServerPort() + request.getContextPath();
				//modify by liuyikai 20121128 reason: request.getLocalAddr()没有这个方法，从逻辑上看应该是获取IP地址 end
				//modify by liuxiaofei 20110810 reason:单点登录退出时，无法正确跳转到登录页面     end
				// 转向的最终登出Url地址
				String logoutUrl = casserverUrl + "/logout?service="
						+ serviceUrl;
				// 本应用的session销毁
				session.setAttribute("UserCode", null);
				session.setAttribute("UserName", null);
				session.setAttribute("ComCode", null);
				session.removeAttribute("UserCode");
				session.removeAttribute("UserName");
				session.removeAttribute("ComCode");
				session.invalidate();
				// 最终转向CAS注销地址
				response.sendRedirect(logoutUrl);
			} else {
				session.invalidate();
				response.sendRedirect("/" + request.getContextPath());
			}
		} else {
			utiILoginLog.setExitTime(new Date());
			UtiILoginLogService utiILoginLogService = (UtiILoginLogService) this
					.getApplicationContext().getBean("utiILoginLogService");
			// UtiILoginLogService utiILoginLogService = (UtiILoginLogService)
			// ServiceFactory.getService("utiILoginLogService");
			long hold = utiILoginLog.getExitTime().getTime()
					- utiILoginLog.getLoginTime().getTime();
			String holdTime = this.getTime(hold);
			utiILoginLog.setHoldTime(holdTime);
			utiILoginLogService.updateMethod(utiILoginLog);
			// --------------------------------------------
			session.invalidate();
			response.sendRedirect("/" + request.getContextPath());
		}
	}

	public ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
	}

	// 获得持续时间
	public String getTime(long hold) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = hold / dd;
		long hour = (hold - day * dd) / hh;
		long min = (hold - day * dd - hour * hh) / mi;
		long sec = (hold - day * dd - hour * hh - min * mi) / ss;
		String strday = day < 10 ? "0" + day : "" + day;
		String strhour = hour < 10 ? "0" + hour : "" + hour;
		String strmin = min < 10 ? "0" + min : "" + min;
		String strsec = sec < 10 ? "0" + sec : "" + sec;
		String holdTime = "";
		if (day == 0 && hour == 0 && min == 0 && sec == 0) {
			holdTime = "1秒";
		} else if (day == 0 && hour == 0 && min == 0) {
			holdTime = strsec + "秒";
		} else if (day == 0 && hour == 0) {
			holdTime = strmin + "分钟" + strsec + "秒";
		} else if (day == 0) {
			holdTime = strhour + "小时" + strmin + "分钟" + strsec + "秒";
		} else {
			holdTime = strday + "天" + strhour + "小时" + strmin + "分钟" + strsec
					+ "秒";
		}
		return holdTime;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
