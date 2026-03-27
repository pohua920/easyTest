package cn.com.sinosoft.ims.log.web;

import ins.framework.exception.BusinessException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import ins.framework.common.ServiceFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.sinosoft.ims.log.model.UtiIOperateLog;
import cn.com.sinosoft.ims.log.service.facade.UtiIOperateLogService;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

public class OperateFilter implements Filter {

	private FilterConfig filterConfig = null;
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig .getServletContext());
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpServletResponse hResponse = (HttpServletResponse) response;
		HttpSession session = hRequest.getSession();
//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
//		WebApplicationContext webApplicationContext = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE); 
		String strServletPath = hRequest.getServletPath();
		Date loginTime = Calendar.getInstance().getTime();
		String requesetURL = hRequest.getRequestURI();
		String userCode = (String) session.getAttribute("UserCode");
		Date exitTime = Calendar.getInstance().getTime();
		long hold = exitTime.getTime()-loginTime.getTime();
		String holdTime = this.getTime(hold);
		UtiIOperateLog utiIOperateLog = new UtiIOperateLog();
		String[] paths = requesetURL.split("/");
		String pathSvr = paths[1];
		String pathTask = paths[2];
		String pathAction = paths[3];
//		UtiISvrService svrService = (UtiISvrService) webApplicationContext.getBean("utiISvrService");
		UtiISvrService utiISvrService = (UtiISvrService) ServiceFactory.getService("utiISvrService");
//		UtiIOperateLogService service = (UtiIOperateLogService) webApplicationContext.getBean("utiIOperateLogService");
		UtiIOperateLogService service = (UtiIOperateLogService) ServiceFactory.getService("utiIOperateLogService");
		String taskName = service.getCName(pathAction,pathTask);
		if(taskName.equals("isLogin")){
			//...
		}else{
			String name = utiISvrService.getUserNameByCode(userCode);
			utiIOperateLog.setUserCode(userCode);
			utiIOperateLog.setAccCode(userCode);
			utiIOperateLog.setExitTime(exitTime);
			utiIOperateLog.setLoginTime(loginTime);
			utiIOperateLog.setHoldTime(holdTime);
			utiIOperateLog.setSvrCode(pathSvr);
			utiIOperateLog.setTaskCode(pathTask);
			utiIOperateLog.setFuncCode(pathAction);
			utiIOperateLog.setDescription("用户"+name+"在"+pathSvr+"系统中执行了 "+taskName+" 操作");
			utiIOperateLog.setRequestURL(requesetURL);
			try{
				service.insertMethod(utiIOperateLog);
			}catch(Exception e){
				e.printStackTrace();
//				new Exception("未成功记录用户操作!");
			}
			//filterChain.doFilter(request, response);
		}
		filterChain.doFilter(request, response);
	}
	
//	}
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("---------------------init----------------");
		// TODO Auto-generated method stub
	}
	public String getTime(long hold){
		int ss=1000;
		int mi =ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = hold/dd;
		long hour = (hold-day*dd)/hh;
		long min = (hold-day*dd-hour*hh)/mi;
		long sec = (hold-day*dd-hour*hh-min*mi)/ss;
		String strday = day<10?"0"+day:""+day;
		String strhour = hour<10?"0"+hour:""+hour;
		String strmin = min<10?"0"+min:""+min;
		String strsec = sec<10?"0"+sec:""+sec;		
		String holdTime = "";
		if(day==0&&hour==0&&min==0&&sec==0){
			holdTime = "1秒";
		}else if(day==0&&hour==0&&min==0){
			holdTime = strsec+"秒";
		}else if(day==0&&hour==0){
			holdTime = strmin+"分钟"+strsec+"秒";
		}else if(day==0){
			holdTime = strhour+"小时"+strmin+"分钟"+strsec+"秒";
		}else{
			holdTime = strday+"天"+strhour+"小时"+strmin+"分钟"+strsec+"秒";
		}
		return holdTime;
	}

}
