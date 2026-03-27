package com.ctbcins.webServicesClient.addressCompare;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * @author bi086
 *
 */
public class AddressFormatWsServiceProxy implements com.ctbcins.webServicesClient.addressCompare.AddressFormatWsService {
  private String _endpoint = null;
  private com.ctbcins.webServicesClient.addressCompare.AddressFormatWsService addressFormatWsService = null;
  
  public AddressFormatWsServiceProxy() {
    _initAddressFormatWsServiceProxy();
  }
  
  public AddressFormatWsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initAddressFormatWsServiceProxy();
  }
  
  private void _initAddressFormatWsServiceProxy() {
    try {
      addressFormatWsService = (new com.ctbcins.webServicesClient.addressCompare.impl.AddressFormatWsServiceImplServiceLocator()).getAddressFormatWsServiceImplPort();
      if (addressFormatWsService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)addressFormatWsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)addressFormatWsService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (addressFormatWsService != null)
      ((javax.xml.rpc.Stub)addressFormatWsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ctbcins.webServicesClient.addressCompare.AddressFormatWsService getAddressFormatWsService() {
    if (addressFormatWsService == null)
      _initAddressFormatWsServiceProxy();
    return addressFormatWsService;
  }
  
  public java.lang.String compare(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception{
    if (addressFormatWsService == null)
      _initAddressFormatWsServiceProxy();
    return addressFormatWsService.compare(str);
  }
  
  public java.lang.String queryStatus(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception{
    if (addressFormatWsService == null)
      _initAddressFormatWsServiceProxy();
    return addressFormatWsService.queryStatus(str);
  }
  
  public java.lang.String format(java.lang.String str) throws java.rmi.RemoteException, com.ctbcins.webServicesClient.addressCompare.Exception{
    if (addressFormatWsService == null)
      _initAddressFormatWsServiceProxy();
    return addressFormatWsService.format(str);
  }
  
  
}