package com.sinosoft.claim.message.send;

import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.sysframework.reference.AppConfig;

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
public class Sender {
	/** 消息发送服务器 */
	private String server;
	/** 消息发送标志 */
	private String sendFlag;
	/** 消息发送连接对象 */
	private XMPPConnection connection;

	/** 构造方法 */
	public Sender() throws Exception {
		this.server = AppConfig.get("sysconst.MSG_SERVER_DOMAIN");
		this.sendFlag = AppConfig.get("sysconst.MSG_SEND_FLAG");
	}

	/**
	 * 發送消息方法
	 * @param userCode 發送用戶
	 * @param content 發送內容
	 * @throws Exception
	 */
	public void sendMsg(String userCode, String content) {
		if ("1".equals(sendFlag) && !isOnline(userCode + "@" + server)) {
			return;
		}
		Message msg = new Message(userCode + "@" + server, Message.Type.chat);
		msg.setBody(content);
		connection.sendPacket(msg);
	}

	/**
	 * 獲得連接方法
	 * @param userCode 發送用戶
	 * @param content 發送用戶密碼
	 * @throws Exception
	 */
	public void connect(String userName, String password) throws Exception {
		connection = new XMPPConnection(server);
		Listener lis = new Listener();
		connection.connect();
		connection.login(userName, password);
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		connection.addPacketListener(lis, filter);
	}

	/**
	 * 判斷用戶在線方法
	 * @param user 發送用戶
	 * @throws Exception
	 */
	public boolean isOnline(String user) {
		Roster roster = connection.getRoster();
		Collection<?> entries = roster.getEntries();
		Iterator<?> it = entries.iterator();
		while (it.hasNext()) {
			RosterEntry entry = (RosterEntry) it.next();
			if (user.equals(entry.getUser())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 斷開連接方法
	 * @throws Exception
	 */
	public void disconnect() {
		connection.disconnect();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		XMPPConnection.DEBUG_ENABLED = true;
		XMPPConnection connection = new XMPPConnection("localhost");
		Listener lis = new Listener();
		connection.connect();
		connection.login(ConstantCodes.MAINCOMPANYCOMCODE, "1111");
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		connection.addPacketListener(lis, filter);
		// Roster roster = connection.getRoster();
		// Collection<?> entries = roster.getEntries();
		// Iterator<?> it = entries.iterator();
		// while(it.hasNext()){
		// RosterEntry entry = (RosterEntry)entries.iterator().next();
		// System.out.println(entry.getUser());
		// }

		// System.out.println(roster.getPresence("0000000000").getStatus() + " "
		// + roster.getPresence("admin").getStatus());

		Message msg = new Message("0000000000@localhost", Message.Type.chat);
		msg.setBody("hello");
		connection.sendPacket(msg);
		connection.disconnect();
	}
}
