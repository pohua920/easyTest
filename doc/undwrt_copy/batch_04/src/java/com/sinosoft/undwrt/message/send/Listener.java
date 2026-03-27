package com.sinosoft.undwrt.message.send;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * The Class Listener.
 */
public class Listener implements PacketListener {
	
	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(PacketListener.class);


	/**
	 * Process packet.
	 * 
	 * @param pak
	 *            the pak
	 * @see org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack.packet.Packet)
	 */
	public void processPacket(Packet pak) {
//		Message msg = (Message) pak;
//		logger.info("from:" + msg.getFrom() + "    to:" + msg.getTo()
//				+ "\nmsg:" + msg.getBody());
	}
}
