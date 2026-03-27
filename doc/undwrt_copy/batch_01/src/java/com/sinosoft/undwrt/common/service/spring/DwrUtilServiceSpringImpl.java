package com.sinosoft.undwrt.common.service.spring;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ctbcins.util.WebserviceObjConvert;
import com.ctbcins.webServicesClient.addressCompare.impl.AddressFormatWsServiceImplServiceLocator;
import com.ctbcins.webServicesVo.AddressCompareStatusVo;
import com.sinosoft.undwrt.common.service.facade.DwrUtilService;
import com.sinosoft.undwrt.common.service.facade.PlatConfigRuleService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonService;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
public class DwrUtilServiceSpringImpl extends GenericDaoHibernate implements DwrUtilService {

	private final Logger logger = Logger.getLogger(DwrUtilServiceSpringImpl.class);
	private CommonService commonService;
	
	/**
	 *  查詢最新地址比對結果，如果已完成則更新狀態為7-人工判定完成
	 * @throws Exception 
	 */
	public String addressCompareQueryStatus(String jsonStr) throws Exception{
		AddressCompareStatusVo vo = new AddressCompareStatusVo();
		try{
			
			JSONObject jSONObject = JSONObject.fromString(jsonStr);
			Map<String, Class> classMap = new HashMap<String, Class>();
			vo = (AddressCompareStatusVo)JSONObject.toBean(jSONObject, AddressCompareStatusVo.class, classMap);

			if("".equals(vo.getBusinessNo()) || vo.getBusinessNo() == null){
				vo.setCode("ERROR");
	        	vo.setMsg("無法取得業務號");
			}
			
			if("".equals(vo.getBusinessType()) || vo.getBusinessType() == null){
				vo.setCode("ERROR");
	        	vo.setMsg("無法取得操作類型");
			}
			String endPoint = ((PlatConfigRuleService) ServiceFactory.getService("platConfigRuleServiceNew")).getPlatConfigRuleAll("ADDRESS_FORMAT_WS_URL", "1").trim();
//			String endPoint = "http://192.168.190.32:8180/CWP/webService/addressFormatService?wsdl";
			AddressFormatWsServiceImplServiceLocator address = new AddressFormatWsServiceImplServiceLocator();
			address.setAddressFormatWsServiceImplPortEndpointAddress(endPoint);
	        String str = WebserviceObjConvert.convertObjToBase64Str(AddressCompareStatusVo.class, vo);
	        
	        String resultStr = address.getAddressFormatWsServiceImplPort().queryStatus(str);
	        vo = (AddressCompareStatusVo) WebserviceObjConvert.convertBase64StrToObj(resultStr, AddressCompareStatusVo.class);
	        if(vo == null){
	        	throw new Exception("ERROR");
	        }
	        if("1".equals(vo.getStatus())){
	        	vo.setCode("ERROR");
	        	vo.setMsg("尚有未判定之地址、電話、電子郵件相關資料...\r\n\r\n請先進行判定，才能進行核保動作～");
	        }else{
	        	commonService.updateNomastatus(vo.getBusinessType(), vo.getBusinessNo());
	        }
			
		}catch (Exception e) {
			e.printStackTrace();
			vo.setCode("ERROR");
			vo.setMsg("系統異常，請重新操作");
		}
		
		return JSONObject.fromObject(vo).toString();
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}


}
