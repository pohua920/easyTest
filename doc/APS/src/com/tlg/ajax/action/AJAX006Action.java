package com.tlg.ajax.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firPanhsinFeedbackFile.RenewalDataProcessingService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class AJAX006Action extends BaseAction implements Serializable {
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */

	private RenewalDataProcessingService renewalDataProcessingService;
	//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
	private FirVerifyDatasService firVerifyDatasService;
	private String result;
	private String codetype;
	private String codecode;
	private String identifynumber;
	private String date;
	private Aps016DetailVo aps016DetailVo;

	public String renewalBasicDataCheck() {
		try {
			Map<String, Object> resultMap = renewalDataProcessingService.basicDataCheck(aps016DetailVo,getUserInfo().getUserId());
			result = JsonUtil.getJSONString(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String saveDataToCheck() {
		try {
			String errMsg = renewalDataProcessingService.theFinalDataCheck(aps016DetailVo);
			Map<String,Object> map = new LinkedHashMap<>();
			if (!StringUtil.isSpace(errMsg)) {
				map.put("isExist", false);
				map.put("errMsg", errMsg);
			}else{
				map.put("isExist", true);
			}
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String checkExtracomcode() {
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			Map<String,String> returnMap = firVerifyDatasService.checkExtracomcode(aps016DetailVo.getExtracomcode(), aps016DetailVo.getBusinessnature());
			result = JsonUtil.getJSONString(returnMap);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String findPrpdNewCodeBycodetype() {
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			String codecname = firVerifyDatasService.findPrpdNewCode(codetype, codecode);
			Map<String,Object> map = new LinkedHashMap<>();
			if (!StringUtil.isSpace(codecname)) {
				map.put("isExist", true);
				map.put("codename", codecname);
			}else{
				map.put("isExist", false);
			}
			result = JsonUtil.getJSONString(map);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	public String verifyIdentifynumber() throws Exception {
		Map<String, String> resultMap = new HashMap<>();
		try {
			resultMap = firVerifyDatasService.verifyID(identifynumber);
		} catch (Exception e) {
			logger.error("verifyID WS Exception",e);
			resultMap.put("errMsg", "證號檢核WS異常:" + e);
		}
		result = JsonUtil.getJSONString(resultMap);
		return Action.SUCCESS;
	}
	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/
	
	//判斷日期是否小於系統日期
	public String checkDateLessThanNewDate(){
		Map<String, Object> map = compareDate();
		try {
			result = JsonUtil.getJSONString(map);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String checkStructureLeval(){
		Map<String,Object> map = new LinkedHashMap<>();
		String wallmaterial = aps016DetailVo.getWallmaterial();
		String roofmaterial = aps016DetailVo.getRoofmaterial();
		String startdate = aps016DetailVo.getStartdateCheck().replace("/", "");
		int floorNum = Integer.parseInt(aps016DetailVo.getSumfloors());
		String structure = "";
			Map<String, String> structMap;
			try {
				//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
				structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial, startdate, floorNum);
				if(!structMap.isEmpty()) {
					structure = structMap.get("structureno");
					map.put("structure", structure);
					//mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業
					map.put("structureText", aps016DetailVo.getWallname()+aps016DetailVo.getRoofname()+aps016DetailVo.getSumfloors()+"層樓"+structMap.get("structureText"));
				}else {
					map.put("errMsg","查無建物等級，無法計算保費;");
				}
				result = JsonUtil.getJSONString(map);
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Action.SUCCESS;
	}
	
	public String checkHandleridentifynumber(){
		Map<String, String> resultMap = new LinkedHashMap<>();
		String handleridentifynumber = aps016DetailVo.getHandleridentifynumber();
		String extracomcode = aps016DetailVo.getExtracomcode();
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			resultMap = firVerifyDatasService.findPrpyddagentByParams(handleridentifynumber, aps016DetailVo.getBusinessnature());
			if(!resultMap.containsKey("errMsg")) {
				if(StringUtil.isSpace(extracomcode)) {
					extracomcode = resultMap.get("extracomcode");					
				}
				//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
				resultMap = firVerifyDatasService.checkExtracomcode(extracomcode, aps016DetailVo.getBusinessnature());
				resultMap.put("extracomcode", extracomcode);
			}
			result = JsonUtil.getJSONString(resultMap);
			
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String findAS400Data(){
		Map<String, String> resultMap = new LinkedHashMap<>();
		String oldpolicyno = aps016DetailVo.getOldpolicyno();
		Map<String, String> params = new HashMap<>();
		params.put("oldpolicyno", oldpolicyno);
		params.put("businessnature", "I99065");
		try {
			//mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
			resultMap = firVerifyDatasService.findFirAgtrnAs400Data(params);
			result = JsonUtil.getJSONString(resultMap);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業  start*/
	@SuppressWarnings("unchecked")
	public String findCoreData(){
		Map<String, String> resultMap = new LinkedHashMap<>();
		try {
			//mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 
			resultMap = firVerifyDatasService.findFirAgtrnCoreData(aps016DetailVo, getUserInfo().getUserId());
			result = JsonUtil.getJSONString(resultMap);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業  end*/
	
	private Map<String, Object> compareDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setLenient(false);
		Date today = new Date();
		Map<String,Object> map = new LinkedHashMap<>();
		try {
			date = rocToAd(date,"/");
			Date compareDate = sdf.parse(date);
			if(today.compareTo(compareDate) < 0) {
				map.put("isExist", false);
				map.put("errMsg", "生日/註冊日不可大於系統日期");
			}else{
				map.put("isExist", true);
			}
		} catch (Exception e) {
			map.put("isExist", false);
			map.put("errMsg", "生日/註冊日需為合理日期");
		}
		return map;
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public RenewalDataProcessingService getRenewalDataProcessingService() {
		return renewalDataProcessingService;
	}

	public void setRenewalDataProcessingService(RenewalDataProcessingService renewalDataProcessingService) {
		this.renewalDataProcessingService = renewalDataProcessingService;
	}

	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 start*/
	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}
	/*mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 end*/

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCodecode() {
		return codecode;
	}

	public void setCodecode(String codecode) {
		this.codecode = codecode;
	}

	public String getIdentifynumber() {
		return identifynumber;
	}

	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Aps016DetailVo getAps016DetailVo() {
		return aps016DetailVo;
	}

	public void setAps016DetailVo(Aps016DetailVo aps016DetailVo) {
		this.aps016DetailVo = aps016DetailVo;
	}
}