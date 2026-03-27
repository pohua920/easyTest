package com.sinosoft.undwrt.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sinosoft.platform.bl.facade.BLPrpDuserFacade;
import com.sinosoft.platform.dto.domain.PrpDuserCADto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService;

import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

/**
 * 修改密碼處理類.
 */
public class UserModifyPwdAction extends Struts2Action {

	/**
	 * 修改密碼.
	 * 
	 * @return the string
	 * @throws UserException
	 *             用戶異常
	 * @throws Exception
	 *             異常
	 */
	public String modifyPwd() throws UserException, Exception {

		String forward = "";
		String userCode = "";
		String oldPassword = "";
		String newPassword = "";
		String retypeNewPassword = "";
		HttpServletRequest req = this.getRequest();
		userCode = req.getParameter("userCode");
		oldPassword = req.getParameter("oldPassword");
		newPassword = req.getParameter("newPassword");
		retypeNewPassword = req.getParameter("retypeNewPassword");
		  //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin
		  TaskService taskService = (TaskService)ServiceFactory.getService("taskService");
		   PrpDuserCADto  prpDuserDto=null;
		    prpDuserDto=taskService.findByPrimaryKey(userCode);
		    prpDuserDto.setValidExpireDate(DateTime.current().addMonth(Integer.parseInt("6")));
		    taskService.update(prpDuserDto);
		 //add by songzhewen 20170301 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改end
		try {
			this.modifyPwd(userCode, oldPassword, newPassword,
					retypeNewPassword);
			forward = "success";
			req.setAttribute("content", getText("undwrt.action.modifyPwd.updatePwdSuccess"));
		} catch (Exception e) {
			throw e;
		}
		return forward;
	}

	/**
	 * 修改密碼.
	 * 
	 * @param usercode
	 *            用戶代碼
	 * @param oldPwd
	 *            舊密碼
	 * @param newPwd
	 *            新密碼
	 * @param retypeNewPassword
	 *            驗證密碼
	 * @throws Exception
	 *             異常
	 */
	public void modifyPwd(String usercode, String oldPwd, String newPwd,
			String retypeNewPassword) throws Exception {

		BLPrpDuserFacade blPrpUserFacade = new BLPrpDuserFacade();
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto = blPrpUserFacade.findByPrimaryKey(usercode);
		prpDuserDto.setOldPasswd(oldPwd); // 旧密码
		prpDuserDto.setNewPasswd(newPwd); // 新密码
		prpDuserDto.setRepeatPasswd(retypeNewPassword); // 验证密码
		blPrpUserFacade.modifyPassword(prpDuserDto);
	}

}
