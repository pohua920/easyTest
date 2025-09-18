package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firBotRenewalFileService.FirBotMaintainService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps055BotDetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.Prpduser;
import com.tlg.prpins.entity.Prpyddagent;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.prpins.service.PrpyddagentService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
@SuppressWarnings("serial")
public class AJAX016Action extends BaseAction implements Serializable {
	private String result;
	private String batchNo;
	private String checkType;
	
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirVerifyDatasService firVerifyDatasService;
	private FirBotMaintainService firBotMaintainService;
	private PrpyddagentService prpyddagentService;
	private PrpduserService prpduserService;
	
	private Aps055BotDetailVo botDetailVo;
	private String insuredDataStr;
	
	
	//台銀續保處理作業資料檢查
	public String verifyBotBasicData() throws Exception {
		try {
			
			List<FirAgtTocoreInsured> insuredList = new ArrayList<>();
			if(!StringUtil.isSpace(this.insuredDataStr)){
				ObjectMapper objectMapper = new ObjectMapper();
				insuredList = objectMapper.readValue(this.insuredDataStr, new TypeReference<ArrayList<FirAgtTocoreInsured>>(){}); 
			}
			botDetailVo.setInsuredList(insuredList);
			
			Map<String, Object> resultMap = firBotMaintainService.basicDataCheck(botDetailVo, getUserInfo().getUserId(), checkType);
			result = JsonUtil.getJSONString(resultMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String checkExtracomcode() {
		try {
			Map<String,String> returnMap = firVerifyDatasService.checkExtracomcode(botDetailVo.getExtracomcode(), botDetailVo.getBusinessnature());
			result = JsonUtil.getJSONString(returnMap);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	//針對台銀業務員查詢判斷是否存在，同時取得分行資訊及對應服務人員
	public String checkHandleridentifynumber(){
		Map<String,String> returnMap = new HashMap<>();
		Map<String,String> params = new HashMap<>();
		params.put("businesssource", "I99004");
		params.put("verifyremark", "1");
		params.put("agentid", botDetailVo.getHandleridentifynumber());
		try {
			Result searchResult = prpyddagentService.findPrpyddagentByParams(params);
			
			if(searchResult.getResObject() != null) {
				List<Prpyddagent> agentList = (List<Prpyddagent>) searchResult.getResObject();
				returnMap.put("extracomcode", agentList.get(0).getSalecomcode());
				returnMap.put("extracomname", agentList.get(0).getSalecomname());
				returnMap.put("orgicode", agentList.get(0).getOrgicode());
				
				params.clear();
				params.put("usercode", agentList.get(0).getServicepersoncode());
				params.put("validstatus", "1");
				searchResult = prpduserService.findPrpduserByParams(params);
				if(searchResult.getResObject() != null) {
					List<Prpduser> userList = (List<Prpduser>) searchResult.getResObject();
					returnMap.put("handler1code", agentList.get(0).getServicepersoncode());
					returnMap.put("comcode", userList.get(0).getComcode());
				}else {
					returnMap.put("errMsg", "查無服務人員或服務人員已失效;");
				}
			}else {
				returnMap.put("errMsg", "業務員登錄證號不存在或無效，請與通路部聯繫確認。");
			}
			
			result = JsonUtil.getJSONString(returnMap);
			
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String checkStructureLeval(){
		Map<String,Object> map = new LinkedHashMap<>();
		String wallmaterial = botDetailVo.getWallmaterial();
		String roofmaterial = botDetailVo.getRoofmaterial();
		String startdate = botDetailVo.getStartdate().replace("/", "");
		int floorNum = Integer.parseInt(botDetailVo.getSumfloors());
		String structure = "";
			Map<String, String> structMap;
			try {
				structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial, startdate, floorNum);
				if(!structMap.isEmpty()) {
					structure = structMap.get("structureno");
					map.put("structure", structure);
					map.put("structureText", botDetailVo.getWallname()+botDetailVo.getRoofname()+floorNum+"層樓"+(structMap.get("structureText")));
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
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public FirBotMaintainService getFirBotMaintainService() {
		return firBotMaintainService;
	}

	public void setFirBotMaintainService(FirBotMaintainService firBotMaintainService) {
		this.firBotMaintainService = firBotMaintainService;
	}

	public Aps055BotDetailVo getBotDetailVo() {
		return botDetailVo;
	}

	public void setBotDetailVo(Aps055BotDetailVo botDetailVo) {
		this.botDetailVo = botDetailVo;
	}

	public String getInsuredDataStr() {
		return insuredDataStr;
	}

	public void setInsuredDataStr(String insuredDataStr) {
		this.insuredDataStr = insuredDataStr;
	}

	public PrpyddagentService getPrpyddagentService() {
		return prpyddagentService;
	}

	public void setPrpyddagentService(PrpyddagentService prpyddagentService) {
		this.prpyddagentService = prpyddagentService;
	}

	public PrpduserService getPrpduserService() {
		return prpduserService;
	}

	public void setPrpduserService(PrpduserService prpduserService) {
		this.prpduserService = prpduserService;
	}
}