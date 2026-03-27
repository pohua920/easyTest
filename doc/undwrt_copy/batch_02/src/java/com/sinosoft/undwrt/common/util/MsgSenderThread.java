package com.sinosoft.undwrt.common.util;

import java.util.Collection;
import java.util.ArrayList;

/**
 * The Class MsgSenderThread.
 */
public class MsgSenderThread extends Thread {
	
	/** ŒÙÐÔThe sinosoft rcvers. */
	ArrayList rcvers = new ArrayList();
	
	/** ŒÙÐÔThe sinosoft senders. */
	String senders = null;
	
	/** ŒÙÐÔThe sinosoft msg contents. */
	String msgContents = null;

	/**
	 * Instantiates a new msg sender thread.
	 * 
	 * @param sender
	 *            the sender
	 * @param rcverList
	 *            the rcver list
	 * @param msgContent
	 *            the msg content
	 */
	public MsgSenderThread(String sender,Collection rcverList,String msgContent) {
		senders =sender;
		msgContents = msgContent;
		rcvers = (ArrayList)rcverList;
		
	}


	/**
	 * Run.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run(){

		try{
			MsgSender msgSender = new MsgSender();
			msgSender.send(senders, rcvers, msgContents);

		}catch (Exception e){
		return;
		}
	}

}
