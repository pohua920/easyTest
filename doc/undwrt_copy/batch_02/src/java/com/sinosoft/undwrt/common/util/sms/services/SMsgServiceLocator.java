/**
 * SMsgServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sinosoft.undwrt.common.util.sms.services;

/**
 * The Class SMsgServiceLocator.
 */
public class SMsgServiceLocator extends org.apache.axis.client.Service implements com.sinosoft.undwrt.common.util.sms.services.SMsgService {

    /**
	 * Instantiates a new s msg service locator.
	 */
    public SMsgServiceLocator() {
    }


    /**
	 * Instantiates a new s msg service locator.
	 * 
	 * @param config
	 *            the config
	 */
    public SMsgServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
	 * Instantiates a new s msg service locator.
	 * 
	 * @param wsdlLoc
	 *            the wsdl loc
	 * @param sName
	 *            the s name
	 * @throws ServiceException
	 *             the service exception
	 */
    public SMsgServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SMsg
    /** 屬性The sinosoft S msg_address. */
    private java.lang.String SMsg_address = "http://10.1.17.110:80/axis/services/SMsg";

    /**
	 * 獲取屬性the sinosoft s msg address.
	 * 
	 * @return 屬性the sinosoft s msg address的值
	 * @see com.sinosoft.undwrt.common.util.sms.services.SMsgService#getSMsgAddress()
	 */
    public java.lang.String getSMsgAddress() {
        return SMsg_address;
    }

    // The WSDD service name defaults to the port name.
    /** 屬性The sinosoft S msg wsdd service name. */
    private java.lang.String SMsgWSDDServiceName = "SMsg";

    /**
	 * 獲取屬性the sinosoft s msg wsdd service name.
	 * 
	 * @return 屬性the sinosoft s msg wsdd service name的值
	 */
    public java.lang.String getSMsgWSDDServiceName() {
        return SMsgWSDDServiceName;
    }

    /**
	 * 設置屬性the sinosoft s msg wsdd service name.
	 * 
	 * @param name
	 *            待設置的the sinosoft s msg wsdd service name的值
	 */
    public void setSMsgWSDDServiceName(java.lang.String name) {
        SMsgWSDDServiceName = name;
    }

    /**
	 * 獲取屬性the sinosoft s msg.
	 * 
	 * @return 屬性the sinosoft s msg的值
	 * @throws ServiceException
	 *             the service exception
	 * @see com.sinosoft.undwrt.common.util.sms.services.SMsgService#getSMsg()
	 */
    public com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType getSMsg() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SMsg_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSMsg(endpoint);
    }

    /**
	 * 獲取屬性the sinosoft s msg.
	 * 
	 * @param portAddress
	 *            the port address
	 * @return 屬性the sinosoft s msg的值
	 * @throws ServiceException
	 *             the service exception
	 * @see com.sinosoft.undwrt.common.util.sms.services.SMsgService#getSMsg(java.net.URL)
	 */
    public com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType getSMsg(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.sinosoft.undwrt.common.util.sms.services.SMsgSoapBindingStub _stub = new com.sinosoft.undwrt.common.util.sms.services.SMsgSoapBindingStub(portAddress, this);
            _stub.setPortName(getSMsgWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
	 * 設置屬性the sinosoft s msg endpoint address.
	 * 
	 * @param address
	 *            待設置的the sinosoft s msg endpoint address的值
	 */
    public void setSMsgEndpointAddress(java.lang.String address) {
        SMsg_address = address;
    }

    /**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 * 
	 * @param serviceEndpointInterface
	 *            the service endpoint interface
	 * @return 屬性the sinosoft port的值
	 * @throws ServiceException
	 *             the service exception
	 */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.sinosoft.undwrt.common.util.sms.services.SMsgSoapBindingStub _stub = new com.sinosoft.undwrt.common.util.sms.services.SMsgSoapBindingStub(new java.net.URL(SMsg_address), this);
                _stub.setPortName(getSMsgWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 * 
	 * @param portName
	 *            the port name
	 * @param serviceEndpointInterface
	 *            the service endpoint interface
	 * @return 屬性the sinosoft port的值
	 * @throws ServiceException
	 *             the service exception
	 */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SMsg".equals(inputPortName)) {
            return getSMsg();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    /**
	 * 獲取屬性the sinosoft service name.
	 * 
	 * @return 屬性the sinosoft service name的值
	 * @see org.apache.axis.client.Service#getServiceName()
	 */
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.1.17.110:80/axis/services/SMsg", "SMsgService");
    }

    /** 屬性The sinosoft ports. */
    private java.util.HashSet ports = null;

    /**
	 * Gets the 屬性The sinosoft ports.
	 * 
	 * @return the 屬性The sinosoft ports
	 * @see org.apache.axis.client.Service#getPorts()
	 */
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.1.17.110:80/axis/services/SMsg", "SMsg"));
        }
        return ports.iterator();
    }

    /**
	 * Set the endpoint address for the specified port name.
	 * 
	 * @param portName
	 *            the port name
	 * @param address
	 *            the address
	 * @throws ServiceException
	 *             the service exception
	 */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SMsg".equals(portName)) {
            setSMsgEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
	 * Set the endpoint address for the specified port name.
	 * 
	 * @param portName
	 *            the port name
	 * @param address
	 *            the address
	 * @throws ServiceException
	 *             the service exception
	 */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
