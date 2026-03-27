package com.sinosoft.undwrt.common.servlet;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.exception.BusinessException;
import ins.framework.exception.PermissionException;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * The Class ExceptionMessageBean.
 */
public class ExceptionMessageBean {

	/** Log4j 蹇呰閰嶇疆. */
	private final Logger logger = Logger.getLogger(ExceptionMessageBean.class);

	/** 鍒濆缂撳瓨瀹炰緥. */
	private static CacheService cacheManager = CacheManager.getInstance("Exception");

	/** 屬性標題. */
	public String title = ""; // 淇℃伅
	
	/** 屬性跳轉頁面返回結果. */
	public String content = ""; // 璇︾粏淇℃伅
	
	/** 屬性The sinosoft string writer. */
	public StringWriter stringWriter = new StringWriter();

	/**
	 * Execute excption message.
	 * 
	 * @param exception
	 *            the exception
	 * @param request
	 *            the request
	 */
	public void executeExcptionMessage(Throwable exception,
			HttpServletRequest request) {
		if (exception == null) {
			exception = (Throwable) request
					.getAttribute("javax.servlet.error.exception");
		}

		if (exception != null) {
			Throwable throwable = null;
			if (exception instanceof ServletException) {
				throwable = ((ServletException) exception).getRootCause();
			} else {
				throwable = exception;
			}
			if (throwable instanceof BusinessException) {
				throwable = (BusinessException) throwable;
			} 
//				else if (throwable instanceof DelegationException) {
//				throwable = throwable.getCause();
//			}
			title = throwable.getMessage();
			if (throwable instanceof PermissionException) {
				throwable = (PermissionException) throwable;
				title = "鎮ㄦ病鏈夋鍔熻兘鐨勬搷浣滄潈闄愶紝璇蜂笌绠＄悊鍛樿仈绯伙紒";
			}
			throwable.printStackTrace(new PrintWriter(stringWriter));
		}
		logger.error(this.getStringWriter().toString());
	}

	/**
	 * 獲取屬性the sinosoft exception message.
	 * 
	 * @return 屬性the sinosoft exception message的值
	 */
	public String getExceptionMessage() {
		String exceptionMessage = "";
		boolean isDebug = false;
		String key = cacheManager.generateCacheKey("exxeptionDebugType");
		Object result = cacheManager.getCache(key);
		if (result != null) {
			isDebug = (Boolean) result;
		} else {
			isDebug = true;
		}
		//寮?惎debug璋冭瘯淇℃伅锛岄〉闈㈠彲浠ョ湅瑙佸叏閮ㄥ爢鏍堜俊鎭?
		if (isDebug) {
			exceptionMessage = this.getStringWriter().toString();
		}
		cacheManager.putCache(key, isDebug);
		return exceptionMessage;
	}

	/**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 設置屬性標題.
	 * 
	 * @param title
	 *            待設置的標題的值
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性the sinosoft string writer.
	 * 
	 * @return 屬性the sinosoft string writer的值
	 */
	public StringWriter getStringWriter() {
		return stringWriter;
	}

	/**
	 * 設置屬性the sinosoft string writer.
	 * 
	 * @param stringWriter
	 *            待設置的the sinosoft string writer的值
	 */
	public void setStringWriter(StringWriter stringWriter) {
		this.stringWriter = stringWriter;
	}

}
