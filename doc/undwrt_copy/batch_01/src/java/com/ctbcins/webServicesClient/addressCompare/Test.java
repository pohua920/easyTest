package com.ctbcins.webServicesClient.addressCompare;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import com.ctbcins.util.WebserviceObjConvert;
import com.ctbcins.webServicesClient.addressCompare.impl.AddressFormatWsServiceImplServiceLocator;
import com.ctbcins.webServicesVo.AddressCompareStatusVo;


/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * @author bi086
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws RemoteException 
	 * @throws ServiceException 
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception, RemoteException, ServiceException, JAXBException, UnsupportedEncodingException{
		String endPoint = "http://192.168.190.32:8180/CWP/webService/addressFormatService?wsdl";
		
		AddressFormatWsServiceImplServiceLocator address = new AddressFormatWsServiceImplServiceLocator();
		address.setAddressFormatWsServiceImplPortEndpointAddress(endPoint);

		AddressCompareStatusVo vo = new AddressCompareStatusVo();
        vo.setBusinessNo("9A01202100002045");
        
        String str = WebserviceObjConvert.convertObjToBase64Str(AddressCompareStatusVo.class, vo);
        
        String resultStr = address.getAddressFormatWsServiceImplPort().queryStatus(str);
        
        vo = (AddressCompareStatusVo) WebserviceObjConvert.convertBase64StrToObj(resultStr, AddressCompareStatusVo.class);
        
        System.out.println("vo.getStatus() = " + vo.getStatus());
        
    	System.exit(0);

	}

}
