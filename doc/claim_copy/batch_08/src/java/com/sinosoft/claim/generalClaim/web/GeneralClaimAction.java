package com.sinosoft.claim.generalClaim.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.generalClaim.util.GeneralClaimViewHelper;
import com.sinosoft.claim.generalClaim.vo.GeneralClaimDto;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 代查勘管理Action
 * @author 中科软
 */
@SuppressWarnings("serial")
public class GeneralClaimAction extends Struts2Action {

	/** 业务类型 CaseTransfer-理賠案件轉移，TaskTransfer-理賠任務轉移，Guide-代查勘委託 */
	private String actionType;
	/**待查勘数据收集*/
	private GeneralClaimViewHelper generalClaimViewHelper;
	/** 通赔服务 */
	private GeneralClaimService generalClaimService;
	
	/**
	 * 进入输入备案号页面
	 * @return
	 * @throws Exception
	 */
	public String givePrepare() throws Exception {
		return "GivePrepare";
	}
	/**
	 * 代查勘处理
	 * @return 页面类型
	 * @throws Exception
	 */
	public String generalClaimBeforeEdit() throws Exception {
		this.clearErrorsAndMessages();
		logger.info("准备查询代查勘信息");
		HttpServletRequest httpServletRequest = getRequest();
//		Page page = null;
//		if (pageNo == 0) {
//			pageNo = 1;
//		}
//		if (pageSize == 0) {
//			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
//		}
		try {
			generalClaimViewHelper.guideDtoToView(httpServletRequest);
			return actionType;
//			if (actionType.equals("giveInsert")) {
//				httpServletRequest.setAttribute("editType", editType);
//				generalClaimViewHelper.giveInsert(httpServletRequest);
//				return "success";
//			}
//			if (actionType.equals("prepareReceiveInsert") || actionType.equals("prepareRegainInsert")) {
//				generalClaimViewHelper.prepareReceiveInsert(httpServletRequest);
//				httpServletRequest.setAttribute("actionType", actionType);
//				return actionType;
//			}
//			if (actionType.equals("receiveInsert")) {
//				generalClaimViewHelper.receiveInsert(httpServletRequest);
//				return "success";
//			}
//			if (actionType.equals("receiveQuery")) {
//				page = generalClaimViewHelper.receiveQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "policyNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "currentnode");
//			}
//			if (actionType.equals("regainQuery")) {
//				page = generalClaimViewHelper.regainQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "policyNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "currentnode");
//			}
//			if (actionType.equals("historyQuery")) {
//				// 查询理赔节点状态信息,整理输入，用於初始界面显示
//				page = generalClaimViewHelper.historyQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "receiveoperatorname", "receivetime", "currentnode", "remark", "givecomcode", "receivecomcode");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof Exception) {
				throw new UserException(-98, -1007, "", e.getMessage()==null?"通賠任務提交失敗":e.getMessage());
			}
		}
		// 如果没有找到信息，返回到出错页面
		return "failure";
	}
	/**
	 * 代查勘处理
	 * @return 页面类型
	 * @throws Exception
	 */
	public String generalClaimEditPost() throws Exception {
		this.clearErrorsAndMessages();
		logger.info("准备查询代查勘信息");
		HttpServletRequest httpServletRequest = getRequest();
//		Page page = null;
//		if (pageNo == 0) {
//			pageNo = 1;
//		}
//		if (pageSize == 0) {
//			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
//		}
		try {
//			if ("QUERY".equals(editType)) {
//				return "givePrepare";
//			}
//			if (actionType.equals("guide")) {
//				httpServletRequest.setAttribute("actionType", actionType);
//				httpServletRequest.setAttribute("editType", editType);
//				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
//				generalClaimViewHelper.guide(httpServletRequest);
//				return editType;
//			}
			GeneralClaimDto generalClaimDto = generalClaimViewHelper.giveInsertViewToDto(httpServletRequest);
			generalClaimService.giveInsert(generalClaimDto);
			HttpSession session = httpServletRequest.getSession();
			UserDto userDto = (UserDto) session.getAttribute("user");
			String registNo = (httpServletRequest.getParameter("registNo")).trim();
			if("CaseTransfer".equals(actionType)){
				userDto.setUserMessage("理賠案件轉移提交成功。\r\n備案號碼為："+registNo);
			}else if("TaskTransfer".equals(actionType)){
				userDto.setUserMessage("理賠任務轉移提交成功。\r\n備案號碼為："+registNo);
			}else{
				userDto.setUserMessage("代查勘委託任務提交成功。\r\n備案號碼為："+registNo);
			}
			httpServletRequest.setAttribute("user", userDto);
			return "success";
			
//			if (actionType.equals("receiveInsert")) {
//				generalClaimViewHelper.receiveInsert(httpServletRequest);
//				return "success";
//			}
//			if (actionType.equals("receiveQuery")) {
//				page = generalClaimViewHelper.receiveQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "policyNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "currentnode");
//			}
//			if (actionType.equals("regainQuery")) {
//				page = generalClaimViewHelper.regainQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "policyNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "currentnode");
//			}
//			if (actionType.equals("historyQuery")) {
//				// 查询理赔节点状态信息,整理输入，用於初始界面显示
//				page = generalClaimViewHelper.historyQuery(httpServletRequest, pageNo, pageSize);
//				this.writeJSONData(page, "registNo", "givecomname", "receivecomname", "giveoperatorname", "givetime", "receiveoperatorname", "receivetime", "currentnode", "remark", "givecomcode", "receivecomcode");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof Exception) {
				throw new UserException(-98, -1007, "", e.getMessage()==null?"代查勘管理任務提交失敗":e.getMessage());
			}
		}
		// 如果没有找到信息，返回到出错页面
		return "failure";
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public GeneralClaimViewHelper getGeneralClaimViewHelper() {
		return generalClaimViewHelper;
	}

	public void setGeneralClaimViewHelper(GeneralClaimViewHelper generalClaimViewHelper) {
		this.generalClaimViewHelper = generalClaimViewHelper;
	}
	public GeneralClaimService getGeneralClaimService() {
		return generalClaimService;
	}
	public void setGeneralClaimService(GeneralClaimService generalClaimService) {
		this.generalClaimService = generalClaimService;
	}

}
