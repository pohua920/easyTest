package cn.com.sinosoft.filter;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.ims.util.ReadProperties;

import com.sinosoft.bpsdriver.domain.getUserMsg.UserMsgResInfo;
import com.sinosoft.bpsdriver.service.facade.UserMgrAPIService;
import com.sinosoft.bpsdriver.service.spring.UserMgrAPIServiceImpl;
import com.sinosoft.dmsdriver.service.server.DictAPIService;
import com.sinosoft.dmsdriver.util.SystemCode;

public class SessionFilter implements Filter {
	private static Log logger = LogFactory.getLog(SessionFilter.class);

	public void init(FilterConfig filterconfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		initSession((HttpServletRequest) request,
				(HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	/**
	 * @param request
	 * @param response
	 */
	private void initSession(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String deployCom = ReadProperties.getString("deployCom");
		session.setAttribute("deployCom", deployCom);
		/** *********单点登录**************start************ */
		if (session.getAttribute("edu.yale.its.tp.cas.client.filter.user") != null) {
			session.setAttribute("UserCode", session
					.getAttribute("edu.yale.its.tp.cas.client.filter.user"));
		}
		logger
				.debug("ca user is "
						+ session
								.getAttribute("edu.yale.its.tp.cas.client.filter.user"));
		/** ********单点登录***************end************** */
		// String powerType = IpSelectAction.getNetType(request);
		// if (!"0".equals(powerType)) {
		// session.setAttribute("PowerType", 2);
		// }else{
		session.setAttribute("PowerType", 1);
		// }
		try {
			if (session.getAttribute("UserCode") == null
					|| session.getAttribute("UserCode").toString().equals("")) {
				if (request.getParameter("userCode") != null
						&& !request.getParameter("userCode").equals("")) {
					logger.debug("UserCode is :"
							+ session.getAttribute("UserCode"));
				} else {
					// /***********单点登录**************start*************/
					// session.setAttribute("UserCode", session
					// .getAttribute("edu.yale.its.tp.cas.client.filter.user"));
					// logger.debug("ca user is
					// "+session.getAttribute("edu.yale.its.tp.cas.client.filter.user"));
					// /**********单点登录***************end***************/
					// session.setAttribute("UserCode", "nomole");
					// session.setAttribute("ComCode", "nomole");
					// logger.debug("UserCode is
					// :"+session.getAttribute("UserCode"));
					//modify by duanfa 20111021 获取验证码不重定向,承保系统查看条例页面也不重定向
					String uri = request.getRequestURI();
					if(!uri.endsWith("saaUserPower/imageValidate.do")&&!uri.endsWith("dictionary/prepareUpdatePrpDregulation.do")){
						response.sendRedirect(request.getContextPath());
					}
				}

			} else if (!session.getAttribute("UserCode").equals("nomole")) {
				logger
						.debug("UserCode is :"
								+ session.getAttribute("UserCode"));
				if (session.getAttribute("UserName") == null
						|| session.getAttribute("ComCode") == null) {
					String userCode = session.getAttribute("UserCode")
							.toString();
					UserMgrAPIService um = new UserMgrAPIServiceImpl();
					UserMsgResInfo userMsgInfo = um.getUserMsg(userCode);
					session.setAttribute("UserCode", userCode);
					session.setAttribute("UserName", userMsgInfo.getUSERNAME());
					session.setAttribute("ComCode", userMsgInfo.getCOMCODE());
					session.setAttribute("ComCName", DictAPIService
							.translateCode(SystemCode.DMS, "ComCode",
									userMsgInfo.getCOMCODE(), "C"));
				}
			} else {
				logger
						.debug("UserCode is :"
								+ session.getAttribute("UserCode"));
				//modify by duanfa 20111021 获取验证码不重定向,承保系统查看条例页面也不重定向
				String uri = request.getRequestURI();
				if(!uri.endsWith("saaUserPower/imageValidate.do")&&!uri.endsWith("dictionary/prepareUpdatePrpDregulation.do")){
					response.sendRedirect(request.getContextPath());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (request.getParameter("userCode") != null
					&& !"".equals(request.getParameter("UserCode"))) {
				logger
						.debug("UserCode is :"
								+ session.getAttribute("UserCode"));
			} else {
				logger
						.debug("UserCode is :"
								+ session.getAttribute("UserCode"));
				// session.setAttribute("UserCode", "nomole");
				// session.setAttribute("ComCode", "nomole");
				try {
					//modify by duanfa 20111021 获取验证码不重定向,承保系统查看条例页面也不重定向
					String uri = request.getRequestURI();
					if(!uri.endsWith("saaUserPower/imageValidate.do")&&!uri.endsWith("dictionary/prepareUpdatePrpDregulation.do")){
						response.sendRedirect(request.getContextPath());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
