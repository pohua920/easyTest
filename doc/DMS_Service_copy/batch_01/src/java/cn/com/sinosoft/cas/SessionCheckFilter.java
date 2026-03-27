package cn.com.sinosoft.cas;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import cn.com.sinosoft.saa.model.SaaUser;
import cn.com.sinosoft.saa.service.facade.SaaUserService;
import edu.yale.its.tp.cas.client.filter.CASFilter;
import ins.framework.common.ServiceFactory;

public class SessionCheckFilter implements Filter {
	private String tokenName;// 默认为sid
	public void destroy() {

	}

	public void doFilter(ServletRequest requ, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) requ;
		HttpServletResponse response = (HttpServletResponse) resp;
		String sessionId = null;
		HttpSession session = request.getSession(false);
		
		try {
			if(session.getAttribute("ComCode")==null){
				if (session != null) {
					SaaUserService saaUserService=(SaaUserService)ServiceFactory.getService("saaUserService");
					sessionId = request.getSession().getId();
					String cassessionuser=session.getAttribute(CASFilter.CAS_FILTER_USER).toString();
					session.setAttribute("UserCode",cassessionuser);
					SaaUser saaUser=saaUserService.findSaaUserByUserCode(cassessionuser);
					session.setAttribute("ComCode",saaUser.getComCode());
				} else {
					sessionId = "";
				}
			}
			
		} catch (Exception e) {
			SaaUserService saaUserService=(SaaUserService)ServiceFactory.getService("saaUserService");
			sessionId = request.getSession().getId();
			String cassessionuser=session.getAttribute(CASFilter.CAS_FILTER_USER).toString();
			session.setAttribute("UserCode",cassessionuser);
 			SaaUser saaUser=saaUserService.findSaaUserByUserCode(cassessionuser);
			session.setAttribute("ComCode",saaUser.getComCode());
		}
			
		String sid = request.getParameter(tokenName);
		if (sid != null) {
			if (!sid.equals(sessionId)) {
				throw new IllegalStateException("Session invalid.");
			}
		}
		URLRewriteRequestWrapper wrappedResponse = new URLRewriteRequestWrapper(
				response, sessionId, tokenName);
		chain.doFilter(request, wrappedResponse);
	}

	public void init(FilterConfig filterConfig) {
		tokenName = filterConfig.getInitParameter("tokenName");
		if (tokenName == null || tokenName.trim().length() == 0) {
			tokenName = "sid";
		}
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

}

class URLRewriteRequestWrapper extends HttpServletResponseWrapper {
	private String sessionId;
	private String tokenName;

	public URLRewriteRequestWrapper(HttpServletResponse response,
			String sessionId, String tokenName) {
		super(response);
		this.sessionId = sessionId;
		this.tokenName = tokenName;
	}

	public String encodeRedirectUrl(String url) {
		return processEncodeURL(super.encodeRedirectUrl(url), sessionId);
	}

	public String encodeRedirectURL(String url) {
		return processEncodeURL(super.encodeRedirectURL(url), sessionId);
	}

	public String encodeUrl(String url) {
		return processEncodeURL(super.encodeUrl(url), sessionId);
	}

	public String encodeURL(String url) {
		return processEncodeURL(super.encodeURL(url), sessionId);
	}

	public String processEncodeURL(String url, String sessionId) {
		if (sessionId == null) {
			return url;
		}
		if (url.indexOf(tokenName) != -1) {
			return url;
		}
		StringBuffer buffer = new StringBuffer(url);
		if (url.indexOf('?') == -1) {
			buffer.append('?');
		} else {
			buffer.append('&');
		}
		buffer.append(tokenName);
		buffer.append('=').append(sessionId);
		return buffer.toString();

	}
}