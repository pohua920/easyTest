package com.sinosoft.undwrt.message.send;


import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;



import com.sinosoft.sysframework.reference.AppConfig;


/**
 * The Class Sender.
 */
public class Sender {
	
	/** 屬性The sinosoft server. */
	private String server;
	
	/** 屬性The sinosoft send flag. */
	private String sendFlag;
	
	/** 屬性The sinosoft connection. */
	private XMPPConnection connection;
	
	/**
	 * Instantiates a new sender.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public Sender() throws Exception{
		this.server = AppConfig.get("sysconst.MSG_SERVER_DOMAIN");
		this.sendFlag = AppConfig.get("sysconst.MSG_SEND_FLAG");
	}
	
	/**
	 * Send msg.
	 * 
	 * @param userCode
	 *            the user code
	 * @param content
	 *            the content
	 */
	public void sendMsg(String userCode,String content)
	{ 
		if("1".equals(sendFlag) && !isOnline(userCode +"@" + server)){
			System.out.print("2222222");
			return;
		}
		System.out.print("33333333");
		Message msg = new Message(userCode +"@" + server, Message.Type.chat);
		System.out.print(userCode +"@" + server);
		msg.setBody(content);	
		connection.sendPacket(msg);
		System.out.print("发送成功！");
	}
	
	/**
	 * Connect.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @throws Exception
	 *             the exception
	 */
	public void connect(String userName,String password)throws Exception{
		System.out.println("即时通讯--连接服务器");
		//XMPPConnection.DEBUG_ENABLED = true; 
		connection = new XMPPConnection(server);
		Listener lis = new Listener();
		connection.connect();
		System.out.println("即时通讯--连接成功");

		connection.login(userName, password);
		System.out.println("即时通讯--登陆成功");

		PacketFilter filter = new MessageTypeFilter(Message.Type.chat); 
		connection.addPacketListener(lis, filter);
	}
	
	/**
	 * Checks if is online.
	 * 
	 * @param user
	 *            the user
	 * @return true, if is online
	 */
	public boolean isOnline(String user){
		Roster roster = connection.getRoster();
		Collection entries = roster.getEntries();
		Iterator it = entries.iterator();
		while(it.hasNext()){
			RosterEntry entry = (RosterEntry)it.next();
			if(user.equals(entry.getUser())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Disconnect.
	 */
	public void disconnect()
	{

		connection.disconnect();
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		XMPPConnection.DEBUG_ENABLED = true; 
		XMPPConnection connection = new XMPPConnection("localhost");
		Listener lis = new Listener();
		connection.connect();
		connection.login("0000000000", "1111");
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat); 
		connection.addPacketListener(lis, filter);
		Roster roster = connection.getRoster();
		Collection entries = roster.getEntries();
		Iterator it = entries.iterator();
		while(it.hasNext()){
			RosterEntry entry = (RosterEntry)it.next();
			 System.out.println(entry.getUser());
		}

		System.out.println(roster.getPresence("0000000000").getStatus() + " " + roster.getPresence("admin").getStatus());
		
		Message msg = new Message("0000000000@localhost", Message.Type.chat);
		msg.setBody("hello");
		connection.sendPacket(msg);
		connection.disconnect();
	}
}
