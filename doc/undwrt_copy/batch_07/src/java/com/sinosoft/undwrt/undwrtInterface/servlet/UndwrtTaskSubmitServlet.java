package com.sinosoft.undwrt.undwrtInterface.servlet;

import ins.framework.common.ServiceFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.common.schema.model.PrpTmain;
import org.apache.log4j.Logger;

import com.sinosoft.prpins.policy.web.EndorseAction;
import com.sinosoft.undwrt.undwrtInterface.service.spring.TaskServiceSpringImpl;

public class UndwrtTaskSubmitServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private Logger loggerRenewal = Logger.getLogger(UndwrtTaskSubmitServlet.class); 
	public void init() throws ServletException {
		//System.out.println("submit undwrt server start!!!");
	}

	/**
	 *  Process the HTTP Post request
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException ex) {
		}
		
		String tempStr = ""; // 存放请求字符串
		InputStream in = request.getInputStream(); // 通过http连接获取一个输入流
		BufferedReader rd = new BufferedReader(new InputStreamReader(in));// 用输入流构造读取流
		String requestStr = "";
		tempStr = ""; // 读取时临时存放一行
		while (true) {
			tempStr = rd.readLine();
			if (tempStr == null)
				break;
			requestStr = requestStr + tempStr;
		}
		SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//requestStr = "T,9CF201400000012";//测试数据
		String[] inputStr = requestStr.split(",");
		String businessNo = inputStr[1];
		String businessType = inputStr[0];
		rd.close();
		in.close();
		String returnFlowId = "";	//返回工作流号
		BufferedOutputStream ou = null; // 返回信息输出流
		String underWriteFlag = "";//記錄提交核保前的核保狀態
		try {
			if (businessNo != null && businessType != null && !"".equals(businessNo.trim()) && !"".equals(businessType.trim())) {
				if("renewal".equals(businessType)){
					TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
					loggerRenewal.error("開始時間："+logFormat.format(new Date())+"查詢提交核保前的核保狀態"+businessNo+"單號類型"+businessType);
					long begin= System.currentTimeMillis();
					underWriteFlag = taskService.queryUnderWriteFlag(businessNo, "BA");//查詢提交核保前的核保狀態
					long end = System.currentTimeMillis();
					loggerRenewal.error("結束時間："+logFormat.format(new Date())+"查詢提交核保前的核保狀態"+businessNo+"單號類型"+businessType);
					loggerRenewal.error("查詢提交核保前的核保狀態所用時間差:----------"+(begin-end));
					loggerRenewal.error("開始時間："+logFormat.format(new Date())+"關聯單報價單核保問題"+businessNo+"單號類型"+businessType);
					long begin1= System.currentTimeMillis();
					taskService.checkDateByThread(businessNo, businessType);//關聯單報價單核保問題
					long end1 = System.currentTimeMillis();
					loggerRenewal.error("結束時間："+logFormat.format(new Date())+"關聯單報價單核保問題"+businessNo+"單號類型"+businessType);
					loggerRenewal.error("關聯單報價單核保問題所用時間差:----------"+(begin1-end1));
					returnFlowId = "";
				}else{
					TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
					underWriteFlag = taskService.queryUnderWriteFlag(businessNo, businessType);//查詢提交核保前的核保狀態
					System.out.println("=======開始進入提交核保========"+businessNo+"========"+businessType+"==============");
					returnFlowId = taskService.checkData(businessNo, businessType);//關聯單報價單核保問題
				}
			} else {
				returnFlowId = "";
			}
		} catch (Exception ex) {
			//add by xuhuilling mantis 4906 重覆點擊提交核保begin
			System.out.println("核保出現異常==================businessNo========");
			if(ex.getMessage()!=null&&!"".equals(ex.getMessage())&&!"null".equals(ex.getMessage())){
				returnFlowId = ex.getMessage();
			}
			System.out.println("==========="+returnFlowId+"=============");
			//add by xuhuilling mantis 4906 重覆點擊提交核保begin
			if("B".equals(businessType)) {
				System.out.println("進入異常處理==================businessNo========");
				TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
				taskService.checkMainSubQatSubmit(businessType, businessNo);//關聯單報價單核保問題
				System.out.println("異常處理的核保狀態=============="+underWriteFlag);
				if(!"".equals(underWriteFlag) && null != underWriteFlag){
					taskService.updateUnderWriteFlag(businessNo, businessType, underWriteFlag);//異常時更新核保狀態為初始值
				}
				System.out.println("異常處理結束==================businessNo========");
			}
			ex.printStackTrace();
		} finally {
			ou = new BufferedOutputStream(response.getOutputStream());
			ou.write(new String(returnFlowId.getBytes(),"GBK").getBytes());
			ou.flush();
			ou.close();
		}
	}

	/**
	 * Process the HTTP Get request
	 *	
	 * @param request			HttpServletRequest请求
	 * @param response			HttpServletResponse响应
	 * @throws ServletException	Servlet异常
	 * @throws IOException		IO异常
	 *	
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Clean up resources
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
	}
}
