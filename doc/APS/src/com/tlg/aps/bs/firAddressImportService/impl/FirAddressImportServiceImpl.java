package com.tlg.aps.bs.firAddressImportService.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firAddressImportService.FirAddressImportService;
import com.tlg.aps.vo.AddressFormatDataVo;
import com.tlg.aps.webService.formatAddressService.client.AddressFormatWsService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.prpins.entity.FirAddrImporterr;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAddrCkdataService;
import com.tlg.prpins.service.FirAddrImporterrService;
import com.tlg.prpins.service.FirAddrImportlistService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;
import com.tlg.util.WebserviceObjConvert;
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FirAddressImportServiceImpl implements FirAddressImportService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	
	private FirBatchLogService firBatchLogService;
	private FirAddrImportlistService firAddrImportlistService;
	private FirAddrCkdataService firAddrCkdataService;
	private FirAddrImporterrService firAddrImporterrService;
	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 start*/
	private AddressFormatWsService clientAddressFormatWsService;
	private static final Logger logger = Logger.getLogger(FirAddressImportServiceImpl.class);
	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 end*/
	
	@Override
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.insertFirBatchLog(firBatchLog);
	}

	@Override
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.updateFirBatchLog(firBatchLog);
	}

	@Override
	public Result updateFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception {
		return this.firAddrImportlistService.updateFirAddrImportlist(firAddrImportlist);
	}
	
	@Override
	public void truncateFirAddrCkdata() throws SystemException, Exception{
		firAddrCkdataService.truncate();
	}
	
	@Override
	public Result insertFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception {
		firAddrCkdataService.insertFirAddrCkdata(firAddrCkdata);
		return null;
	}
	
	@Override
	public Result insertFirAddrImporterr(FirAddrImporterr firAddrImporterr,UserInfo userInfo) throws SystemException, Exception {
		firAddrImporterr.setIcreate(userInfo.getUserId().toUpperCase());
		firAddrImporterr.setDcreate(new Date());
		firAddrImporterrService.insertFirAddrImporterr(firAddrImporterr);
		return null;
	}
	
	public int countFirAddrCkdata(Map params) throws SystemException, Exception {
		return firAddrCkdataService.countFirAddrCkdata(params);
	}
	
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----START*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result findFirAddrCkdataByPolicyno(String policyno) throws SystemException, Exception {
		Result result = new Result();
		Map params = new HashMap();
		params.put("policyno", policyno);
		Result tempResult = firAddrCkdataService.findFirAddrCkdataByParams(params);
		if(tempResult != null && tempResult.getResObject() != null) {
			List<FirAddrCkdata> searchResult = (List<FirAddrCkdata>)tempResult.getResObject();
			if(searchResult != null && searchResult.size() > 0) {
				result.setResObject(searchResult.get(0));
			}
		}
		
		return result;
	}
	
	@Override
	public Result updateFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception {
		firAddrCkdataService.updateFirAddrCkdata(firAddrCkdata);
		return null;
	}
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修----END*/

	/**mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 
	 *呼叫地址正規化webService
	 *@param String address
	 *@return AddressFormatDataVo*/
	@Override
	public AddressFormatDataVo addressFormat(String address) throws SystemException, Exception{
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
			tmp = new String(decryptedByteArray, StandardCharsets.UTF_8);
			logger.info("addressFormat returnValue : " + tmp);
			addressFormatDataVo = (AddressFormatDataVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, AddressFormatDataVo.class);
			if(addressFormatDataVo != null){
				return addressFormatDataVo;
			}
		} catch (JAXBException e) {
			logger.error("呼叫地址正規化WS失敗:" + e);
		} catch (Exception e) {
			logger.error("呼叫地址正規化WS失敗:" + e);
		}
		return null;
	}
	
	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirAddrImportlistService getFirAddrImportlistService() {
		return firAddrImportlistService;
	}

	public void setFirAddrImportlistService(FirAddrImportlistService firAddrImportlistService) {
		this.firAddrImportlistService = firAddrImportlistService;
	}
	
	public FirAddrCkdataService getFirAddrCkdataService() {
		return firAddrCkdataService;
	}

	public void setFirAddrCkdataService(FirAddrCkdataService firAddrCkdataService) {
		this.firAddrCkdataService = firAddrCkdataService;
	}

	public FirAddrImporterrService getFirAddrImporterrService() {
		return firAddrImporterrService;
	}

	public void setFirAddrImporterrService(FirAddrImporterrService firAddrImporterrService) {
		this.firAddrImporterrService = firAddrImporterrService;
	}

	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 start*/
	public AddressFormatWsService getClientAddressFormatWsService() {
		return clientAddressFormatWsService;
	}

	public void setClientAddressFormatWsService(AddressFormatWsService clientAddressFormatWsService) {
		this.clientAddressFormatWsService = clientAddressFormatWsService;
	}
	/*mantis：FIR0520，處理人員：BJ085，需求單編號：FIR0520 標的物地址正規化-FIR0183地址匯入排程調整 end*/
}
