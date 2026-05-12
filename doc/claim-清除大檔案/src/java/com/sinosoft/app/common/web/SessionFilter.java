package com.sinosoft.app.common.web;

import ins.framework.common.ServiceFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.sinosoft.app.common.service.facade.PerfCodeTransferService;
import com.sinosoft.app.common.util.TimeUtil;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.user.service.facade.UserService;

import cn.com.sinosoft.saa.service.facade.PowerBean;

public class SessionFilter implements Filter {

	public void init(FilterConfig filterconfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		initSession((HttpServletRequest) request);
		// 上周周六
		request.setAttribute("lastSaturday", TimeUtil.getWeekSaturday());
		// 周一
		request.setAttribute("monday", TimeUtil.getWeekMonday());
		// 周五
		request.setAttribute("friday", TimeUtil.getWeekFriday());
		// 周六
		request.setAttribute("saturdaty", TimeUtil.getNextWeekSaturday());
		// 下周五
		request.setAttribute("nextMonday", TimeUtil.getNextWeekMonday());
		// 下周一
		request.setAttribute("nextFriday", TimeUtil.getNextWeekFriday());
		// 当前时间
		request.setAttribute("today", TimeUtil.getDate());
		chain.doFilter(request, response);
	}

	private void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				UserDetails ud = (UserDetails) principal;
				String userCode = ud.getUsername();
				String oldUserCode = (String) session.getAttribute("UserCode");

				if (userCode.equals(oldUserCode)) { // session exists

				} else {
					// set session for user
					UserService userService = (UserService) ServiceFactory.getService("userService");
					CompanyService companyService = (CompanyService) ServiceFactory.getService("companyService");
					PerfCodeTransferService perfCodeTransferService = (PerfCodeTransferService) ServiceFactory.getService("perfCodeTransferService");
					SaaUser prpDuser = userService.findUserByUserCode(userCode);
					String departCode = "";
					String departName = "";
					String companyCode = "";
					String companyName = "";
					/********* add by linsiming 增加部门名称和机构名称 start ********/

					/********* add by linsiming 增加部门名称和机构名称 end ********/
					session.setAttribute("UserCode", userCode);
					session.setAttribute("UserName", prpDuser.getUserName());
					session.setAttribute("ComCode", prpDuser.getComCode());
					session.setAttribute("PowerType", 1);
					PowerBean.clear(userCode);

				}
			}
		}
	}
}
