package com.sinosoft.claim.remnant.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.remnant.service.facade.RemnantService;
import com.sinosoft.claim.remnant.util.RemnantViewHelper;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * 残余物处理
 * @author 中科软
 *
 */
public class RemnantSaveAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 残余物ViewHelper */
	private RemnantViewHelper remnantViewHelper;
	/** 残余物Service*/
	private RemnantService remnantService;
	/** 重开赔案Service*/
	private PrpLrecaseService prpLrecaseService;

	/**残余物页面提交
	 * @return
	 * @throws Exception
	 */
	public String remnantSave() throws Exception {
		String forward = "failure";
		HttpServletRequest httpServletRequest = this.getRequest();
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		String editType = httpServletRequest.getParameter("editType");
		// 设置防止重复提交
		this.clearErrorsAndMessages();
		String strLastAccessedTime = "" + httpServletRequest.getSession().getLastAccessedTime() / 1000;
		String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldRegistLastAccessedTime");
		if (!oldLastAccessedTime.trim().equals("")) {
			this.addActionMessage("殘餘物處理");
			httpServletRequest.setAttribute("errorMessage", "請不要重複提交！");
			this.addActionMessage("請不要重複提交！");
			return forward;
		}
		// 取号
		String compensateNo = httpServletRequest.getParameter("prpLcompensateCompensateNo");
		RemnantDto remnantDto = new RemnantDto();
		String claimNo = httpServletRequest.getParameter("prpLcompensateClaimNo"); // 立案号
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setJbpmDto(new JbpmDto());
		if(DataUtils.emptyToNull(DataUtils.dbNullToEmpty(swfLogFlowID))!=null){
			SwfLog currSwfLog = new SwfLog(swfLogFlowID,Integer.parseInt(swfLogLogNo));
			workFlowDto.setCurrSwfLog(currSwfLog);
			workFlowDto.setSubmit(true);
		}
		if ("add".equals(editType) || "edit".equals(editType)) {
			// 处理页面提交信息
			remnantDto = remnantViewHelper.viewToDto(httpServletRequest, claimNo);
			if(DataUtils.emptyToNull(compensateNo) == null){
				compensateNo = remnantDto.getPrpLcompensate().getCompensateNo();
			}
			if("add".equals(editType)){
				workFlowDto.setCreate(true);
				workFlowDto.setSubmit(true);
				workFlowDto.setBessinessNo(compensateNo);
			}else{
				if (workFlowDto.getCurrSwfLog() == null) {// 駁回修改再次提交審批時，開啟審批工作流的情況
					workFlowDto.setCreate(true);
					workFlowDto.setSubmit(true);
					workFlowDto.setBessinessNo(compensateNo);
				}
			}
			remnantService.saveBpm(remnantDto,workFlowDto);
			this.addActionMessage("殘餘物處理任務提交成功！");
			this.addActionMessage("殘餘物處理計算書號：" + compensateNo);
		} else if ("undwrt".equals(editType)) {
			if ("".equals(compensateNo) || compensateNo == null) {
				throw new Exception("程式異常，請聯系系統管理員！");
			} else {
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				remnantService.saveUndwrtPass(compensateNo, workFlowDto);
				httpServletRequest.setAttribute("user", user);
			}
			this.addActionMessage("殘餘物任務審批成功！");
			this.addActionMessage("殘餘物計算書號：" + compensateNo);
		} 
		else if ("withdrawal".equals(editType)) {
			if ("".equals(compensateNo) || compensateNo == null) {
				throw new Exception("程式異常，請聯系系統管理員！");
			} else {
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				remnantService.saveUndwrtBack(compensateNo, workFlowDto);
				httpServletRequest.setAttribute("user", user);
			}
			this.addActionMessage("殘餘物任務退回成功！");
			this.addActionMessage("殘餘物計算書號：" + compensateNo);
		}
		forward = "success";
		httpServletRequest.getSession().setAttribute("oldRegistLastAccessedTime", strLastAccessedTime);
		return forward;

	}
	

	public RemnantViewHelper getRemnantViewHelper() {
		return remnantViewHelper;
	}

	public void setRemnantViewHelper(RemnantViewHelper remnantViewHelper) {
		this.remnantViewHelper = remnantViewHelper;
	}

	public RemnantService getRemnantService() {
		return remnantService;
	}

	public void setRemnantService(RemnantService remnantService) {
		this.remnantService = remnantService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}
	
}
