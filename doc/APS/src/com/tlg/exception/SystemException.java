package com.tlg.exception;

import com.tlg.util.EventLogger;
import com.tlg.util.Message;



public class SystemException extends RuntimeException {
	private EventLogger log = new EventLogger(this.getClass());
	public SystemException() {
		super();
	}


	public SystemException(String msg, Throwable cause) {
		super(msg, cause);
		cause.printStackTrace();
		log.logError(msg + " - " + cause.toString() + " - " + cause.getStackTrace()[0]);		
	}
	
	public SystemException(Message message, Throwable cause) {
		super(message.toString(), cause);
		cause.printStackTrace();
		log.logError(message.toString() + " - " + cause.toString() + " - " + cause.getStackTrace()[0]);		
	}

	public SystemException(String msg) {	
		super(msg);
		log.logError(msg);
	}

	public SystemException(Message message) {	
		super(message.toString());
		log.logError(message.toString());
	}

	public SystemException(Throwable cause) {		
		super(cause);
		cause.printStackTrace();
		log.logError(cause.toString()+ " - " + cause.getStackTrace()[0]);		
	}

}
