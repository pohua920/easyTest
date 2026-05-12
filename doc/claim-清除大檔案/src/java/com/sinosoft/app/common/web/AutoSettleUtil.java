package com.sinosoft.app.common.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.app.common.util.AutoSettleConstants;

public class AutoSettleUtil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Log logger = LogFactory.getLog(this.getClass());

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		this.logger.info("请求URL【" + req.getRequestURL() + "】,请求参数【" + req.getQueryString() + "】,客户端ip【" + req.getRemoteAddr() + "】,请求方法【" + req.getMethod() + "'】;服务端IP【" + req.getServerName() + "】服务端PORT【" + req.getServerPort() + "】,请求票据【"
				+ req.getParameter("TICKET") + "】");

		// 初始成功标志,1为成功执行,非1失败
		String responseStr = "1";
		// 判断发送类型
		String jobName = req.getParameter("jobName");
		this.logger.info("parameter[jobName]=" + jobName);
		String settleDate = req.getParameter("date");
		String flag_voucher = req.getParameter("flag_voucher");
		try {
			long identify = System.currentTimeMillis();
			this.logger.info("任務【" + AutoSettleConstants.jobNameMap.get(jobName) + "】【" + identify + "】开始");
			if (AutoSettleConstants.SENDMES_JOBNAME.equalsIgnoreCase(jobName)) {
				// 提前一天工作提醒
				AutoSendEmail autoSendEmail = new AutoSendEmail();
				autoSendEmail.getMes();
			} else if (AutoSettleConstants.SENDMESFRIDAY_JOBNAME.equalsIgnoreCase(jobName)) {
			} else {
				responseStr = "任務名称【" + jobName + "】不匹配";
				this.logger.error(responseStr);
			}
			this.logger.info("任務【" + AutoSettleConstants.jobNameMap.get(jobName) + "】【" + identify + "】结束");
		} catch (Throwable e) {
			this.logger.error("执行任務【" + jobName + "】发生异常:" + e.getMessage(), e);
			e.printStackTrace();
			// 异常信息
			responseStr = e.getMessage();
		} finally {
			// 回传异步任务系统信息
			OutputStream outputStream = resp.getOutputStream();
			if (responseStr == null) {
				responseStr = "";
			}
			outputStream.write(responseStr.getBytes());
			outputStream.flush();
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
