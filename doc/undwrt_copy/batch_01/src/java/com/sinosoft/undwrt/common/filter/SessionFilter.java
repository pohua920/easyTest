package com.sinosoft.undwrt.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.platform.dto.domain.PrpDuserDto;

public class SessionFilter implements Filter {

	public void init(FilterConfig filterconfig) throws ServletException {
	}

	public void destroy() {
	}

	/** 过滤方法 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		String request_uri = httprequest.getRequestURI();

		// 放行的.do请求
		int index1 = request_uri.indexOf("pass.do");
		if (index1 > -1) {
			chain.doFilter(request, response);
		} else {
			// 对SESSION是否有效进行校验
			checkSession((HttpServletRequest) request, (HttpServletResponse) response);
			chain.doFilter(request, response);
		}
	}

	/** 校验SESSION是否有效，主要判断session里面是否有user */
	private boolean checkSession(HttpServletRequest request, HttpServletResponse response) {
		// 如果Session失效，返回null;
		HttpSession session = request.getSession(false);
		String URI = request.getRequestURI();
		// 如果是在单点登陆-自动登陆，则不进行检查
		if (URI.contains("index_portal.jsp"))
			return true;
		// 如果是在登陆，则不进行检查
		if (URI.contains("index.jsp"))
			return true;
		if (!URI.contains("."))
			return true;

		if ((null != request.getParameter("userCode") && null != request.getParameter("password"))) {
			return true;
		} else {
			try {
				//承保系统查看历次审核意见绕开登陆校验  add by zhanghuanqi 20141015 begin
				String uri = request.getRequestURI();
				if(uri.indexOf("commonViewTrace") != -1){
					return true;
				}
				//承保系统查看历次审核意见绕开登陆校验  add by zhanghuanqi 20141015 end
				PrpDuserDto prpDuserDto = null;
				prpDuserDto = (PrpDuserDto) session.getAttribute("user");

				// 获取页面中的用户，如果为空 返回登录页面
				if (null == prpDuserDto) {
					response.sendRedirect("/undwrt");
					session.setAttribute("LoginMsg", "會話超時，請重新登入！");
					return false;
				}
			} catch (Exception e) {
				try {
					response.sendRedirect("/undwrt");
					session.setAttribute("LoginMsg", "會話超時，請重新登入！");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
}