/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * AddressFormatWsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ctbcins.webServicesClient.addressCompare;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * @author bi086
 *
 */
public interface AddressFormatWsService extends java.rmi.Remote {
    public java.lang.String compare(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception;
    public java.lang.String queryStatus(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception;
    public java.lang.String format(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception;
}
