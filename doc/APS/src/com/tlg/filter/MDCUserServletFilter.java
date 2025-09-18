package com.tlg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.tlg.util.UserInfo;

/**
 * Simple Servlet Filter that sets the Log4J MDC to the currently logged-in
 * user. This filter is intended to be used with JBoss&acute;s http-invoker web
 * application to identify/separate parallel fat client RMI requests.
 * 
 * $id: $
 * 
 * @author Matthias G&auml;rtner
 * 
 */
public class MDCUserServletFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		boolean bUserAdded = false;
		if (session != null && session.getAttribute("userInfo") != null) {
			// Put the principal's name into the message diagnostic
			// context. May be shown using %X{username} in the layout
			// pattern.
			UserInfo ufo = (UserInfo)session.getAttribute("userInfo");
			MDC.put("userId", ufo.getUserId());
			bUserAdded = true;
		}

		try {
			// Continue processing the rest of the filter chain.
			chain.doFilter(request, response);
		} finally {
			if (bUserAdded) {
				// Remove the added element again - only if added.
				MDC.remove("userId");
			}
		}
	}

	public void destroy() {
	}
}
