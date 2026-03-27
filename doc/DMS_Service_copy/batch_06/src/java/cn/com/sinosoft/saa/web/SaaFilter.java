package cn.com.sinosoft.saa.web;

import ins.framework.exception.BusinessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.ims.util.IConstants;

import com.sinosoft.bpsdriver.domain.getMethodTaskList.MethodTaskResInfo;
import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;


public class SaaFilter implements Filter {

//	private FilterConfig filterConfig = null;
	private static Map<String,String> methodTaskMap = new HashMap<String,String>();
	private static Log log = LogFactory.getLog(SaaFilter.class);
	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException,BusinessException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpSession session = hRequest.getSession();
		request.setCharacterEncoding(IConstants.ENCODING_UTF8);
		//获取方法代码
		String strServletPath = hRequest.getServletPath();
		//如果需要参数来判断操作类型，需 统一使用editType参数
		String editType = hRequest.getParameter("editType");
		String userCode = (String) session.getAttribute("UserCode");
		//获取系统变量，
//		String systemSplit = ReadProperties.getString("systemSplit");
		if (editType != null) {
			strServletPath += editType;
		}
		log.debug("*********ServletPath********"+strServletPath);
		boolean flag = false;
		//如果taskCode为空，说明不用控制
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		if (methodTaskMap.size() <= 0) {
			List<MethodTaskResInfo> list = new ArrayList<MethodTaskResInfo>(0);
			try {
				// 调用接口，获取方法功能列表
				list = saaAPIService.getMethodTaskList("dms");
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("获取方法功能列表异常！", false);
			}
			MethodTaskResInfo methodTaskInfo = null;
			// 循环遍历方法功能列表，将数据初始化到全局静态变量methodTaskMap中
			for (int i = 0; i < list.size(); i++) {
				methodTaskInfo = list.get(i);
				// methodCode必须唯一
				methodTaskMap.put(methodTaskInfo.getMETHODCODE(),
						methodTaskInfo.getTASKCODE());
			}
		}
		String taskCode = methodTaskMap.get(strServletPath);
		if (taskCode != null) {
			try {
				flag = saaAPIService.checkPower("dms", userCode, taskCode, "1");
			} catch (Exception e) {
				throw new BusinessException(e.getMessage(), false);
			}
			// 如果没有权限，则抛出异常信息
			if (!flag) {
				throw new BusinessException("您没有该功能权限，请核实！", false);
			}
		}
			
			
		filterChain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
//		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//		if (methodTaskMap.size() <= 0) {
//			List<MethodTaskResInfo> list = new ArrayList<MethodTaskResInfo>(0);
//			try {
//				//调用接口，获取方法功能列表
//				list = saaAPIService.getMethodTaskList("dms");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			MethodTaskResInfo  methodTaskInfo = null;
//			//循环遍历方法功能列表，将数据初始化到全局静态变量methodTaskMap中
//			for (int i = 0; i < list.size(); i++) {
//				methodTaskInfo = list.get(i);
//				//methodCode必须唯一
//				methodTaskMap.put(methodTaskInfo.getMETHODCODE(), methodTaskInfo.getTASKCODE());
//			}
//		}
	}
}