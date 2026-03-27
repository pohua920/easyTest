/**
 * SMsg_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sinosoft.undwrt.common.util.sms.services;

/**
 * The Interface SMsg_PortType.
 */
public interface SMsg_PortType extends java.rmi.Remote {
    
    /**
	 * Main.
	 * 
	 * @param args
	 *            the args
	 * @throws RemoteException
	 *             the remote exception
	 */
    public void main(java.lang.String[] args) throws java.rmi.RemoteException;
    
    /**
	 * Invoke.
	 * 
	 * @param shell
	 *            the shell
	 * @return the java.lang. object
	 * @throws RemoteException
	 *             the remote exception
	 */
    public java.lang.Object invoke(java.lang.String shell) throws java.rmi.RemoteException;
    
    /**
	 * Inits the.
	 * 
	 * @param dbIp
	 *            the db ip
	 * @param dbName
	 *            the db name
	 * @param dbPort
	 *            the db port
	 * @param user
	 *            the user
	 * @param pwd
	 *            the pwd
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int init(java.lang.String dbIp, java.lang.String dbName, java.lang.String dbPort, java.lang.String user, java.lang.String pwd) throws java.rmi.RemoteException;
    
    /**
	 * Release.
	 * 
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int release() throws java.rmi.RemoteException;
    
    /**
	 * Flush host.
	 * 
	 * @return true, if successful
	 * @throws RemoteException
	 *             the remote exception
	 */
    public boolean flushHost() throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, long smID) throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param srcID
	 *            the src id
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, long smID, long srcID) throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param url
	 *            the url
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, long smID, java.lang.String url) throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param srcID
	 *            the src id
	 * @param url
	 *            the url
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, long smID, long srcID, java.lang.String url) throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param sendTime
	 *            the send time
	 * @param smID
	 *            the sm id
	 * @param srcID
	 *            the src id
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, java.lang.String sendTime, long smID, long srcID) throws java.rmi.RemoteException;
    
    /**
	 * Send sm.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param srcID
	 *            the src id
	 * @param url
	 *            the url
	 * @param sendTime
	 *            the send time
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendSM(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, java.lang.String content, long smID, long srcID, java.lang.String url, java.lang.String sendTime) throws java.rmi.RemoteException;
    
    /**
	 * Recv rpt.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @return the java.lang. string
	 * @throws RemoteException
	 *             the remote exception
	 */
    public java.lang.String recvRPT(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd) throws java.rmi.RemoteException;
    
    /**
	 * Send pdu.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param msgFmt
	 *            the msg fmt
	 * @param tpPID
	 *            the tp pid
	 * @param tpUdhi
	 *            the tp udhi
	 * @param feeTerminalID
	 *            the fee terminal id
	 * @param feeType
	 *            the fee type
	 * @param feeCode
	 *            the fee code
	 * @param feeUserType
	 *            the fee user type
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendPDU(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, byte[] content, long smID, int msgFmt, int tpPID, int tpUdhi, java.lang.String feeTerminalID, java.lang.String feeType, java.lang.String feeCode, int feeUserType) throws java.rmi.RemoteException;
    
    /**
	 * Send pdu.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @param mobiles
	 *            the mobiles
	 * @param content
	 *            the content
	 * @param smID
	 *            the sm id
	 * @param srcID
	 *            the src id
	 * @param msgFmt
	 *            the msg fmt
	 * @param tpPID
	 *            the tp pid
	 * @param tpUdhi
	 *            the tp udhi
	 * @param feeTerminalID
	 *            the fee terminal id
	 * @param feeType
	 *            the fee type
	 * @param feeCode
	 *            the fee code
	 * @param feeUserType
	 *            the fee user type
	 * @return the int
	 * @throws RemoteException
	 *             the remote exception
	 */
    public int sendPDU(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd, java.lang.String[] mobiles, byte[] content, long smID, long srcID, int msgFmt, int tpPID, int tpUdhi, java.lang.String feeTerminalID, java.lang.String feeType, java.lang.String feeCode, int feeUserType) throws java.rmi.RemoteException;
    
    /**
	 * Recv mo.
	 * 
	 * @param apiCode
	 *            the api code
	 * @param loginName
	 *            the login name
	 * @param loginPwd
	 *            the login pwd
	 * @return the java.lang. string
	 * @throws RemoteException
	 *             the remote exception
	 */
    public java.lang.String recvMo(java.lang.String apiCode, java.lang.String loginName, java.lang.String loginPwd) throws java.rmi.RemoteException;
    
    /**
	 * Check time.
	 * 
	 * @param sendTime
	 *            the send time
	 * @return true, if successful
	 * @throws RemoteException
	 *             the remote exception
	 */
    public boolean checkTime(java.lang.String sendTime) throws java.rmi.RemoteException;
}
