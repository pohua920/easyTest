/*
 * Created on 2005/4/11
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.tlg.util;

import org.apache.log4j.Logger;

/**
 * 分級處理log
 * 
 * @author Ryan
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class EventLogger {

	Logger log;

	/*
	 * log.error("ERROR"); log.debug("DEBUG"); log.warn("WARN");
	 * log.info("INFO"); log.trace("TRACE");
	 */

	public EventLogger(Class c) {
		log = Logger.getLogger(c);
	}

	// log trace message
	public void logTrace(String msg) {
		log.trace(msg);
	}

	public void logDebug(String msg) {
		log.debug(msg);
	}

	public void logError(String msg) {
		log.error(msg);
	}

	public void logWarn(String msg) {
		log.warn(msg);
	}

	public void logInfo(String msg) {
		log.info(msg);
	}
	
	public void logThrow(String msg,Throwable t) {
		log.error(this.getClass().getName(), t);
	}
}