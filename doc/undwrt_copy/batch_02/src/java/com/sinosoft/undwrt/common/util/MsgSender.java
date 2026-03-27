package com.sinosoft.undwrt.common.util;


import java.util.Collection;
/*
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
*/
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * add by Luojing 20070904.
 */
/**
 * 消息处理
 * @author Luojing
 * @Param rcverList 消息接收者列表
 * @Param msgContent　消息内容
 */
public class MsgSender {
	
	/** 屬性The sinosoft msg server domain. */
	String msgServerDomain =  null;

	/**
	 * Instantiates a new msg sender.
	 */
	public MsgSender() {
		try{
			msgServerDomain =  com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_SERVER_DOMAIN");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("start send message");
	}
   
   /**
	 * 发送及时消息,指定代理作为发送者.
	 * 
	 * @param rcverList
	 *            the rcver list
	 * @param msgContent
	 *            the msg content
	 */
	/*
	    public  void send(Collection rcverList,String msgContent) {
    	 try {

    		   Collection userList = rcverList;
    		   XMPPConnection con = new XMPPConnection("luojing");
    		   con.connect();
    		   System.out.println("finished connect");
    		   con.login("agent", "1111");
    		   System.out.println("finished login");
    		   ChatManager chatmanager = con.getChatManager();
    		   //Chat chat = null;
    		   
    		   for (int i=0;i < userList.size();i++){
 
    			   chatmanager.createChat((userList.toArray()[i]).toString()+"@luojing",
        				   new MessageListener() {
        			   		public void processMessage(Chat chat, Message message) {
        			   			System.out.println("Received message: " + message);
        			   			try {
        			   				chat.sendMessage(message.getBody());
        			   			} catch (XMPPException e) {
        			   				e.printStackTrace();
        			   			}
        			   		}
        		   }).sendMessage(msgContent);
    		   }
    		   con.disconnect();
    		  } catch (XMPPException e) {
    			  e.printStackTrace();
    		  }
    }
    */
	
	
    public  void send(Collection rcverList,String msgContent) {
   	 try {

   		   Collection userList = rcverList;
   		   XMPPConnection con = new XMPPConnection(msgServerDomain);
   		   con.login("agent", "1111");
   		   System.out.println("finished login");
   		   
   		   

   		   for (int i=0;i < userList.size();i++){
   			   System.out.println("receiver is " + (userList.toArray()[i]).toString()+"@" + msgServerDomain);
   			  // con.createChat((userList.toArray()[i]).toString()+"@" + msgServerDomain).sendMessage(msgContent);
   		   }
   		   //con.close();
   		  } catch (XMPPException e) {
   			  e.printStackTrace();
   		  }
   }
    
    /**
	 * 发送及时消息.
	 * 
	 * @param sender
	 *            the sender
	 * @param rcverList
	 *            the rcver list
	 * @param msgContent
	 *            the msg content
	 * @Param sender -消息发送者
	 * @Param rcverList 消息接收者列表
	 * @Param msgContent　消息内容
	 */
    public  void send(String sender,Collection rcverList,String msgContent) {
      	 try {

      		   Collection userList = rcverList;
      		   XMPPConnection con = new XMPPConnection(msgServerDomain);
      		   con.login(sender, "1111");
      		   System.out.println("finished login");
      		   

      		   for (int i=0;i < userList.size();i++){
      			   System.out.println("sender is " + sender);
      			   System.out.println("receiver is " + (userList.toArray()[i]).toString()+"@" + msgServerDomain);
      			  /// con.createChat((userList.toArray()[i]).toString()+"@"+msgServerDomain).sendMessage(msgContent);
      		   }
      		  // con.close();
      		  } catch (XMPPException e) {
      			  e.printStackTrace();
      		  }
      }
    /*
    public  void send(String sender,Collection rcverList,String msgContent) {
    	 try {
 
    		   Collection userList = rcverList;
    		   XMPPConnection con = new XMPPConnection("luojing");
    		   con.connect();
    		   System.out.println("finished connect");
    		   con.login(sender, "1111");
    		   System.out.println("finished login");
    		   ChatManager chatmanager = con.getChatManager();
    		   Chat chat = null;
    		   
    		   for (int i=0;i < userList.size();i++){
    			   chat = chatmanager.createChat((userList.toArray()[i]).toString()+"@luojing",
        				   new MessageListener() {
        			   		public void processMessage(Chat chat, Message message) {
        			   			System.out.println("Received message: " + message);
        			   			try {
        			   				chat.sendMessage(message.getBody());
        			   			} catch (XMPPException e) {
        			   				e.printStackTrace();
        			   			}
        			   		}
        		   });
        		   System.out.println("finished create chat");
        		   chat.sendMessage(msgContent);
        		   System.out.println("finished chat :" + msgContent);
    		   }
    		   con.disconnect();
    		  } catch (XMPPException e) {
    			  e.printStackTrace();
    		  }
    }
    */
}
