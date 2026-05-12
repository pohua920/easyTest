package com.sinosoft.claim.claim.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 调整估损金额处理Facade
 * <p>
 * Title: 调整估损金额
 * </p>
 * <p>
 * Description: 对系统中的估损金额进行调整
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ModifySumClaimAction extends Struts2Action {
	/** 立案service */
	private ClaimService claimService = null;
	/** 立案主表service */
	private PrpLclaimService prpLclaimService = null;
	/** 核赔级别service */
	private UtiUwLevelService utiUwLevelService;
	//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)
	/** 權限service */
	private UtiUserGradeService utiUserGradeService;

	/**
	 * 修改估损金额
	 * @return 操作类型
	 * @throws Exception
	 */
	public String modifySumClaim() throws Exception {
		// 业务类型：ModifyBeforeQuery准备查询並显示信息列表；modifyDetail显示估损金额详细信息;modifySave保存修改後的信息;false出现异常
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = getRequest();
		HttpServletResponse httpServletResponse = getResponse();
		String editType = httpServletRequest.getParameter("editType");
		String claimNo = httpServletRequest.getParameter("claimNo");
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");		
		int gradeLevel = getGradeLevel(httpServletRequest,userDto);
		if (editType.equals("RegistBeforeQuery") || editType.equals("modifyBeforeQuery")) { // 准备查询並显示信息列表
			Page page = claimService.findClaimInforByCondition(httpServletRequest, httpServletResponse);
			editType = "modifyBeforeQuery";
			//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
			this.writeJSONData(page, "claimNo", "policyNo", "riskCode", "insuredName", "claimDate","remark");
			return NONE;
		} else if (editType.equals("modifyDetail")) { // 显示估损金额详细信息
			//mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天月曆天-START
			httpServletRequest.setAttribute("gradeLevel", ""+gradeLevel);//
			//條件變更為
			//  一般理賠人員 or 分公司理賠助理 or 分公司理賠科長
			// “立案後超過十個日之理賠案件，僅能由總公司覆核人員進行修改！”
			//  OR
			// “調整估損金額超過100萬元之理賠案件，僅能由總公司覆核人員進行修改！
			if(gradeLevel > 0){
				PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
				//mantis： CLM0271 ，處理人員：DP0713，需求單編號：CLM0271 新核心-取消非車險立案後10日僅能由總公司覆核人員調整估損金的鎖控 START
				String riskCode = prpLclaim.getRiskCode();//
				if(riskCode.equals("A01") || riskCode.equals("B01")){
					if(checkOverChangeDays(prpLclaim)) {
						throw new UserException(1, 10, "估損金額調整", "立案後超過十日，僅能由總公司覆核人員進行修改！");
					}
				}
				//mantis： CLM0271 ，處理人員：DP0713，需求單編號：CLM0271 新核心-取消非車險立案後10日僅能由總公司覆核人員調整估損金的鎖控 END
				if(checkOverMaxSumClaim(prpLclaim)) {
					throw new UserException(1, 10, "估損金額調整", "調整估損金額超過100萬元之理賠案件，僅能由總公司覆核人員進行修改！");
				}
			}
			claimService.findDetailByClaimNo(httpServletRequest, httpServletResponse);
			//mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天月曆天 -END
		} else if (editType.equals("modifySave")) { // 保存修改後的信息
			try {
				claimService.saveClaimLoss(httpServletRequest, httpServletResponse);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			String prpLclaimClaimNo = httpServletRequest.getParameter("prpLclaimClaimNo");
			claimService.updateSwflog(prpLclaimClaimNo);
			this.addActionMessage(getText("prompt.modifySumClaim.submit"));
			this.addActionMessage(getText("certainLoss.claims"));
			userDto.setUserMessage(prpLclaimClaimNo);
			return SUCCESS;
		} else if (editType.equals("back")) {// 返回
			httpServletRequest.setAttribute("showflg", "true");// 设置回显标识
			return "back";
		}
		String forward = editType;
		return forward;
	}

	/**
	 *  mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天月曆天
	 *  理賠權限:
	 *  0 = 003/009(理賠助理/部門理賠科長)&comcode =00(總公司)
	 *  1 = 003/009(理賠助理/部門理賠科長)&comcode !=00(非公司)
	 *  2 = 005(一般理賠人員)
	 * @param userDto
	 * @return
	 * @throws Exception
	 */
	private int getGradeLevel(HttpServletRequest request,UserDto userDto) throws Exception {
		int gradeLevel = 2;//一般理賠人員
		String userCode = userDto.getUserCode();
		String comCode = userDto.getComCode();
		for(UtiUserGrade grade : utiUserGradeService.findByConditions("USERCODE='"+userCode+"' and COMCODE = '"+comCode+"'")){
			if("00".equals(comCode) && ConstantCodes.GRADECODE_003.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 0);// 總公司理賠助理
				break;
			}else if("00".equals(comCode) && ConstantCodes.GRADECODE_009.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 0);// 總公司理賠科長
				break;
			}else if(!"00".equals(comCode) && ConstantCodes.GRADECODE_003.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 1);// 分公司理賠助理
			}else if(!"00".equals(comCode) && ConstantCodes.GRADECODE_009.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 1);// 分公司理賠科長
			}else if(ConstantCodes.GRADECODE_005.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 2);//一般理賠人員
			}
		}
		if(gradeLevel == 2){//一般理賠人員只能查詢自己的案子
			request.setAttribute("userCode", userCode);
		}else if(gradeLevel == 1){// 非總公司理賠助理or理賠科長只能查詢相同單位(comcode)理賠案件
			request.setAttribute("comCode", comCode);
		}
		return gradeLevel;
	}
	
	/**
	 *  mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天月曆天
	 *  检查调整估损金额权限
	 * @param userDto 
	 * @param riskCode
	 * @param claimNo
	 * @return
	 * true:超過100萬
	 * false:沒超過100萬
	 */
	private boolean checkOverMaxSumClaim(PrpLclaim prpLclaim) {
		//1.將判斷調整為所有險種估損金額調整超過100萬元則交由總公司處理
		Double sumClaim = prpLclaim.getSumClaim();// 总估损金额
		return sumClaim != null && new BigDecimal(sumClaim).compareTo(new BigDecimal(1000000)) > 0;
	}
	
	/**
	 *  mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天月曆天
	 *  使用者"一般理賠人員"角色 检查调整估损金额权限(十個月曆天)
	 *  使用者"非總公司理賠助理or理賠科長"角色检查调整估损金额权限(十個月曆天)
	 * @param riskCode
	 * @param claimNo
	 * @return
	 */
	private boolean checkOverChangeDays(PrpLclaim prpLclaim) {
		Calendar today = Calendar.getInstance();
		Calendar claimAfterDays = Calendar.getInstance();
		claimAfterDays.setTime(prpLclaim.getClaimDate());
		clearTime(today);
		clearTime(claimAfterDays);
		claimAfterDays.add(Calendar.DAY_OF_YEAR, 10);
		return !(claimAfterDays.after(today));//若系統日大於claimDate+10工作日，則不能修改
	}
	
	/**
	 * mantis： CLM0098 ，處理人員：BK007 蘇哲，需求單編號：CLM0098 新核心-調整估損金額功能-延長至十天工作天
	 * @param calendar
	 */
	private void clearTime(Calendar calendar){
		calendar.clear(Calendar.HOUR_OF_DAY); 
		calendar.clear(Calendar.AM_PM); 
		calendar.clear(Calendar.MINUTE); 
		calendar.clear(Calendar.SECOND); 
		calendar.clear(Calendar.MILLISECOND); 
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}
	
	//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)START
	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}
	//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)END

}