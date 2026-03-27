package com.sinosoft.app.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载操作手册方法，是使用servlet，同时，在applicationContext-acegi-security.xml
 * 中过滤器该方法，进行了单独的设置。 其命名规则，则不在名称後面增加Servlet字符，避免sessionFilter对其产生影响。
 * @author 中科软
 */
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadFile() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		// 1.获取下载资源
		ServletContext context = this.getServletContext();
		// / 代表当前Web应用的根目录 获取这个图片在硬盘的绝对路径
		String path = context.getRealPath(File.separator + "common") + File.separator + fileName;
		// 2通知浏览器以下载的方式等会打开发送的资源数据 //中文文件名要想正确显示，要经过url编码
		response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));

		// 3:读取资源文件
		FileInputStream fis = new FileInputStream(path);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = fis.read(buf)) != -1) {
			// 发送资源数据给浏览器
			response.getOutputStream().write(buf, 0, len);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
