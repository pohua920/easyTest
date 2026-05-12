package com.sinosoft.claim.message.send;


import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
/**
 * <p>
 * Title: 即时消息
 * </p>
 * <p>
 * Description: 即时消息
 * </p>
 * @author 中科软
 * @version
 */
public class Listener implements PacketListener {
	
//	private static final Log logger = LogFactory.getLog(PacketListener.class);

	/**
	 * @param pak 監聽消息包 該類為監聽類 必須要有
	 * @throws Exception 
	 */
	public void processPacket(Packet pak) {
//		Message msg = (Message) pak;
//		logger.info("from:" + msg.getFrom() + "    to:" + msg.getTo()
//				+ "\nmsg:" + msg.getBody());
	}
}
