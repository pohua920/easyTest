package com.tlg.aps.bs.metaAmlService.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.metaAmlService.AmlAS400Service;
import com.tlg.aps.vo.AmlInsuredListVo;
import com.tlg.aps.vo.AmlInsuredVo;
import com.tlg.aps.vo.AmlResponseVo;
import com.tlg.db2.entity.Com704wa;
import com.tlg.db2.entity.Com704wb;
import com.tlg.db2.service.Com704waService;
import com.tlg.db2.service.Com704wbService;
import com.tlg.exception.SystemException;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.WebserviceObjConvert;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AmlAS400ServiceImpl implements AmlAS400Service {
	
	private static final Logger logger = Logger.getLogger(AmlAS400ServiceImpl.class);
	private ConfigUtil configUtil;
	private Com704waService com704waService;
	private Com704wbService com704wbService;
	private com.tlg.aps.webService.metaAmlService.client.AmlService clientAmlQueryService;

	
	/**
	 * 找出未執行的資料
	 */
	@Override
	public void as400toMetaAmlWebService() throws SystemException, Exception {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("wa16IsNull", "Y");
		Result result = com704waService.findCom704waByParams(params);
		if(result.getResObject() == null){
			logger.debug("無資料可以送meta aml");
			Message msg = new Message();
			msg.setMessageString("無資料可以送meta aml");
			result.setMessage(msg);
			return;
		}
		ArrayList<Com704wa> com704waList = (ArrayList<Com704wa>)result.getResObject();
		sendAmlDataHandle(com704waList);
	}
	
	private void sendAmlDataHandle(ArrayList<Com704wa> com704waList) throws SystemException, Exception{
		
		if(com704waList != null){
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd HHmmssSSS");
			//逐筆送web service
			
			for(Com704wa com704wa:com704waList){
				AmlInsuredListVo amlInsuredListVo = new AmlInsuredListVo();
				amlInsuredListVo.setAppCode("AS400_SCHEDULE");
				amlInsuredListVo.setAmlType("2");
				amlInsuredListVo.setBusinessNo(com704wa.getWa02());
				amlInsuredListVo.setChannelType(com704wa.getWa03());
				amlInsuredListVo.setClassCode(com704wa.getWa04());
				amlInsuredListVo.setRiskCode(com704wa.getWa05());
				amlInsuredListVo.setComCode(com704wa.getWa06());
				amlInsuredListVo.setPrem(com704wa.getWa08());
				amlInsuredListVo.setType(com704wa.getWa09());
				if(StringUtil.isSpace(com704wa.getWa17())){
					amlInsuredListVo.setResend("0");
				}else{
					amlInsuredListVo.setResend(com704wa.getWa17());
				}
				
				
				//依據業務號找出要被保人資料
				Map params = new HashMap();
				params.put("wb01", com704wa.getWa02());
				Result result = com704wbService.findCom704wbByParams(params);
				if(result.getResObject() == null){
					//找不到資料時
					com704wa.setWa10("05");
					com704wa.setWa14("無法取得要被保人相關資料");
					com704wa.setWa16(sdFormat.format(new Date()));
					result = com704waService.updateCom704wa(com704wa);
					continue;
				}
				int count = 1;
				ArrayList<AmlInsuredVo> amlInsuredVoList = new ArrayList<AmlInsuredVo>();
				ArrayList<Com704wb> detailList = (ArrayList<Com704wb>)result.getResObject();
				for(Com704wb com704wb:detailList){
					AmlInsuredVo amlInsuredVo = new AmlInsuredVo();
					amlInsuredVo.setSerialNo(String.valueOf(count));
					amlInsuredVo.setId(com704wb.getWb03());
					amlInsuredVo.setInsuredType(com704wb.getWb04());
					amlInsuredVo.setInsuredFlag(com704wb.getWb05());
					amlInsuredVo.setName(com704wb.getWb06());
					amlInsuredVo.setEnName(com704wb.getWb07());
					amlInsuredVo.setGender(com704wb.getWb08());
					amlInsuredVo.setBirthday(com704wb.getWb09());
					amlInsuredVo.setNationCode(com704wb.getWb10());
					amlInsuredVo.setDangerOccupation(com704wb.getWb11());
					amlInsuredVo.setListedCabinetCompany(com704wb.getWb13());
					amlInsuredVoList.add(amlInsuredVo);
					count++;
				}
				amlInsuredListVo.setAmlInsuredList(amlInsuredVoList);
				AmlResponseVo amlResponseVo = sendAmlWebService(amlInsuredListVo);
				if(amlResponseVo == null){
					//找不到資料時
					com704wa.setWa10("06");
					com704wa.setWa14("傳送AML失敗！");
					com704wa.setWa16(sdFormat.format(new Date()));
					result = com704waService.updateCom704wa(com704wa);
					continue;
				}
				com704wa.setWa10(amlResponseVo.getWorkStatus());
				com704wa.setWa11(amlResponseVo.getRefuseLimiteInsurance());
				com704wa.setWa12(amlResponseVo.getRiskRating());
				com704wa.setWa13(amlResponseVo.getListDetection());
				com704wa.setWa14(amlResponseVo.getErrMsg());
				com704wa.setWa16(sdFormat.format(new Date()));
				result = com704waService.updateCom704wa(com704wa);
			}
		}
	}
	
	public AmlResponseVo sendAmlWebService(AmlInsuredListVo amlInsuredListVo) {
		AmlResponseVo amlResponseVo = null;
		try {
			
			if (amlInsuredListVo == null) {
				throw new SystemException("傳入物件不可為null");
			}
			String encodestr = WebserviceObjConvert.convertObjToBase64Str(AmlInsuredListVo.class, amlInsuredListVo);
			String result = clientAmlQueryService.amlQuery(encodestr);
			amlResponseVo = (AmlResponseVo) WebserviceObjConvert.convertBase64StrToObj(result, AmlResponseVo.class);
			
		} catch (SystemException se) {
			logger.debug(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return amlResponseVo;
	}
	

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public Com704waService getCom704waService() {
		return com704waService;
	}


	public void setCom704waService(Com704waService com704waService) {
		this.com704waService = com704waService;
	}

	public Com704wbService getCom704wbService() {
		return com704wbService;
	}

	public void setCom704wbService(Com704wbService com704wbService) {
		this.com704wbService = com704wbService;
	}

	public com.tlg.aps.webService.metaAmlService.client.AmlService getClientAmlQueryService() {
		return clientAmlQueryService;
	}

	public void setClientAmlQueryService(
			com.tlg.aps.webService.metaAmlService.client.AmlService clientAmlQueryService) {
		this.clientAmlQueryService = clientAmlQueryService;
	}

	
}
