package com.sinosoft.claim.common.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.inject.Inject;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.tlg.commons.util.api.rest.blockChain.CompulsoryCaseQuery310;
import com.tlg.commons.util.api.rest.blockChain.vo.CompulsoryQueryResultVo;
import com.tlg.commons.util.api.rest.blockChain.vo.CompulsoryQueryVo;
import com.tlg.commons.util.api.rest.blockChain.vo.CompulsoryResultVo;

import ins.framework.web.Struts2Action;

/**
 *mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業
 */
public class CommonWebServiceAction extends Struts2Action {
	private static final long serialVersionUID = 8552168125905172061L;
	
	@Inject(value = "struts.devMode")
	private String devMode;//0181 devMode
	
	private CodeService codeService;
	
	public String compulsoryCaseQuery310() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			UUID uuid = UUID.randomUUID();
			
			CompulsoryCaseQuery310 bcs = new CompulsoryCaseQuery310();
			CompulsoryQueryVo queryVo = new CompulsoryQueryVo();

			String[] idNubmer = request.getParameter("idNumber").split(",");
			String[] idNubmerType = request.getParameter("idNumberType").split(",");
			String[] prpLregistDamageStartDate = request.getParameter("prpLregistDamageStartDate").split("-");
			String prpLregistDamageStartHour = request.getParameter("prpLregistDamageStartHour");
			String prpLregistDamageStartMinute = request.getParameter("prpLregistDamageStartMinute");
			String result="";
			boolean create=true;
			int count = 0;
			
			int year = Integer.parseInt(prpLregistDamageStartDate[0]);
			int month = Integer.parseInt(prpLregistDamageStartDate[1]);
			int day = Integer.parseInt(prpLregistDamageStartDate[2]);
			int hour = Integer.parseInt(prpLregistDamageStartHour);
			int minute=Integer.parseInt(prpLregistDamageStartMinute);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month-1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			
//			for(String idN:idNubmer){
			for(int i=0;i<idNubmer.length;i++){
				if(!"".equals(idNubmer[i])){
					System.out.println(++count+" UUID ===== " + uuid);
					queryVo.setHitTime(cal.getTime());//出險日期時間
					queryVo.setIdNumber(idNubmer[i]);//受害人身分證
					queryVo.setIdNumberType(idNubmerType[i]);//ID_NUMBER 身分證字號  ARC_NUMBER 居留證號 PASSPORT_NUMBER  護照號碼

					
					HttpSession session = request.getSession();
					UserDto user = (UserDto) session.getAttribute("user");
	
					CompulsoryQueryResultVo compulsoryQueryResultVo = new CompulsoryQueryResultVo();
					try{
						compulsoryQueryResultVo = bcs.compulsoryQuery(queryVo, user.getUserCode());
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					System.out.println("queryVo.getIdNumber() = " + queryVo.getIdNumber());
					System.out.println("compulsoryHistoryCaseVo.getStatus() = " + compulsoryQueryResultVo.getStatus());
					System.out.println("compulsoryQueryResultVo.getMessages() = " + compulsoryQueryResultVo.getMessages());
					System.out.println("compulsoryQueryResultVo.getCode() = " + compulsoryQueryResultVo.getCode());
//					if(count==3){//for test
//						create = false;
//						result+="\r\nidNumber:"+idN+",mspId:TEST"+",actionType:TEST_ADD"+"。";
//					}
					System.out.println("compulsoryQueryResultVo.getHistoryCases() = " + compulsoryQueryResultVo.getHistoryCases());
					CompulsoryResultVo compulsoryResultVo = new CompulsoryResultVo();
					if(compulsoryQueryResultVo.getStatus().equalsIgnoreCase("SUCCESS")){
						compulsoryResultVo = compulsoryQueryResultVo.getResult();
						System.out.println("compulsoryResultVo.getCaseId():" + compulsoryResultVo.getCaseId());
						System.out.println("compulsoryResultVo.getActionType():" + compulsoryResultVo.getActionType());
						System.out.println("compulsoryResultVo.getMspId():" + compulsoryResultVo.getMspId());
						if(!"CREATE".equals(compulsoryResultVo.getActionType())){
							create = false;
						}
						//參照action_type的對應說明(compulsory_case_action_type)
						//action_type 
						//CREATE = 可建立
						//CONTINUE = 流程進行中
						//CONTINUE_TO_NEW_APPORTION =可建立接續攤賠
						//REJECT = 不可建立
		
					}else{
						create = false;
					}
					String actionType = "";
					if("CREATE".equals(compulsoryResultVo.getActionType())){
						actionType = "可建立";
					}else if("CONTINUE".equals(compulsoryResultVo.getActionType())){
						actionType = "流程進行中";
					}else if("REJECT".equals(compulsoryResultVo.getActionType())){
						actionType = "不可建立";
					}else if("CONTINUE_TO_NEW_APPORTION".equals(compulsoryResultVo.getActionType())){
						actionType = "可建立接續攤賠";
					}
					StringBuilder condition = new StringBuilder();
					condition.append(" validStatus = '1' and codeType = '").append("BlockChain_MSPID").append("' ");
					condition.append(" order by codeCode ");

					List<PrpDcode> list = this.codeService.findPrpDcodeByConditions(condition.toString());
					String mspIdString = "無";
					for(PrpDcode pd : list){
						if(pd.getId().getCodeCode().equals(compulsoryResultVo.getMspId())){
							mspIdString = pd.getCodeCName();
						}
					}
//					result+="\r\n【區塊鏈狀態：\"id_number\":\""+idN+"\",\"action_type\": \""+compulsoryResultVo.getActionType()+"\",\"msp_id\": \""+compulsoryResultVo.getMspId()+"\"】";
					result+="\r\n【區塊鏈狀態 > 查詢("+idNubmer[i]+")後可進行的動作：\""+actionType+"\"，受理公司代號：\""+mspIdString+"\"】";
				}
			}

			if(!create){
				jsonMap.put("result", "0|"+result);
			}else{
				jsonMap.put("result", "1|"+result);
			}
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
		}
		//0181 devMode START
		if(null!=devMode && "true".equals(devMode) && jsonMap.get("result")==null){//僅有測試環境 且 區塊鏈異常連結 出-1給前端測試人員做confirm的選擇 
			jsonMap.put("result", "-1|\r\n【區塊鏈狀態 > 查詢後可進行的動作：測試進行中,，受理公司代號： \"-1\"】");
		}
		//0181 devMode END
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
    }

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
	
}
