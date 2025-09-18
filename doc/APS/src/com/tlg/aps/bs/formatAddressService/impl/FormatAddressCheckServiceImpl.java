package com.tlg.aps.bs.formatAddressService.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.formatAddressService.FormatAddressCheckService;
import com.tlg.aps.vo.AddressFormatDataVo;
import com.tlg.aps.webService.formatAddressService.client.AddressFormatWsService;
import com.tlg.aps.webService.formatAddressService.client.Exception_Exception;
import com.tlg.util.WebserviceObjConvert;


/** mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化  **/
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FormatAddressCheckServiceImpl implements FormatAddressCheckService {
	
	private static final Logger logger = Logger.getLogger(FormatAddressCheckServiceImpl.class);
	
	private AddressFormatWsService clientAddressFormatWsService;

	@Override
	public Map<String,Object> formatAddressCheck(String address) {
		Map<String,Object> returnData = new HashMap<String,Object>();
		String soapxml;
		AddressFormatDataVo addressFormatDataVo = new AddressFormatDataVo();
		addressFormatDataVo.setAddress(address);
		addressFormatDataVo.setAppCode("APS");
		String returnValue = "";
		String tmp = "";
		byte[] decryptedByteArray;
		try {
			soapxml = WebserviceObjConvert.convertObjToBase64Str(AddressFormatDataVo.class, addressFormatDataVo);
			returnValue = clientAddressFormatWsService.formatAddress(soapxml);
			decryptedByteArray = Base64.decodeBase64(returnValue);
			tmp = new String(decryptedByteArray, "UTF-8");
			logger.info("queryDoubleIns returnValue : " + tmp);
			addressFormatDataVo = (AddressFormatDataVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, AddressFormatDataVo.class);
			if(addressFormatDataVo != null){
				if("S000".equals(addressFormatDataVo.getCode())){
					//mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
					returnData.put("addressFormatted", addressFormatDataVo.getAddressFormatted());
					returnData.put("postcode", addressFormatDataVo.getZip());
					//mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
					returnData.put("format", "Y");
					returnData.put("errmsg", "");
				} else {
					returnData.put("format", "N");
					returnData.put("errmsg", addressFormatDataVo.getMsg());//錯誤訊息
				}
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
			returnData.put("errmsg", "查詢地址正規化WS失敗");//錯誤訊息
		} catch (Exception_Exception e) {
			e.printStackTrace();
			returnData.put("errmsg", "查詢地址正規化WS失敗");//錯誤訊息
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			returnData.put("errmsg", "查詢地址正規化WS失敗");//錯誤訊息
		}
		
		return returnData;
	}

	public AddressFormatWsService getClientAddressFormatWsService() {
		return clientAddressFormatWsService;
	}

	public void setClientAddressFormatWsService(AddressFormatWsService clientAddressFormatWsService) {
		this.clientAddressFormatWsService = clientAddressFormatWsService;
	}

	


}
