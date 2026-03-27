/**
 * SMsgService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sinosoft.undwrt.common.util.sms.services;

/**
 * The Interface SMsgService.
 */
public interface SMsgService extends javax.xml.rpc.Service {
    
    /**
	 * 獲取屬性the sinosoft s msg address.
	 * 
	 * @return 屬性the sinosoft s msg address的值
	 */
    public java.lang.String getSMsgAddress();

    /**
	 * 獲取屬性the sinosoft s msg.
	 * 
	 * @return 屬性the sinosoft s msg的值
	 * @throws ServiceException
	 *             the service exception
	 */
    public com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType getSMsg() throws javax.xml.rpc.ServiceException;

    /**
	 * 獲取屬性the sinosoft s msg.
	 * 
	 * @param portAddress
	 *            the port address
	 * @return 屬性the sinosoft s msg的值
	 * @throws ServiceException
	 *             the service exception
	 */
    public com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType getSMsg(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
