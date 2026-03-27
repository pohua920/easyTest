package com.sinosoft.claim.updatepwd.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.fubon.password.webservice.ModifyPasswordService;
import com.model.vo.UserVO;
import com.sinosoft.claim.dto.domain.PrpDuserDto;
import com.sinosoft.claim.ui.control.action.UILogonAction;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 修改用户登录密码
 * <p>
 * Title: 修改登录密码
 * </p>
 * <p>
 * Description: 修改理赔操作员登录密码
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * @author 中科软
 * @version 1.0
 */

public class UpdatePwdAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4341680281782370947L;

	public String updatePwd() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = getRequest();
		String typeFrom = httpServletRequest.getParameter("typeFrom");
		String forward = "";
		if ("out".equals(typeFrom)) {
			String userCode = httpServletRequest.getParameter("userCode");
			String oldPassword = httpServletRequest.getParameter("oldPassword");
			String newPassword = httpServletRequest.getParameter("newPassword");
			UILogonAction uiLogonAction = new UILogonAction();
			PrpDuserDto prpDuserDto = (PrpDuserDto) uiLogonAction.checkUser(userCode, oldPassword);
			if (prpDuserDto != null) {
				prpDuserDto.setPassword(newPassword);
				prpDuserDto.setPasswordExpireDate(prpDuserDto.getPasswordExpireDate().addMonth(1));
				uiLogonAction.updatePwd(prpDuserDto); // 修改密码
				// 给予密码修改成功提示信息
				this.addActionMessage(getText("update.password.success"));
				forward = "successOut";
			} else {
				// 给予密码修改失败信息
				this.addActionMessage(getText("update.password.false"));
				httpServletRequest.setAttribute("showMessage", "原密碼輸入有誤,請重新輸入！");
				forward = "changePWD";
			}
		} else {
			String userCode = httpServletRequest.getParameter("userCode");
			String oldPassword = httpServletRequest.getParameter("oldPassword");
			String newPassword = httpServletRequest.getParameter("newPassword");
			UILogonAction uiLogonAction = new UILogonAction();
			PrpDuserDto prpDuserDto = (PrpDuserDto) uiLogonAction.checkUser(userCode, oldPassword);
			if (prpDuserDto != null) {
				ModifyPasswordService modifyPasswordService = new ModifyPasswordService();
				UserVO userVO = new UserVO();
				userVO.setUserCode(userCode);
				userVO.setOldPassword(oldPassword);
				userVO.setNewPassword(newPassword);
				userVO.setOperateType(UserVO.MODIFYPSW);
				String errorInfo = modifyPasswordService.modifyPassword(userVO);
				if (errorInfo != null && !"".equals(errorInfo)) {
					throw new Exception(errorInfo);
				}
				prpDuserDto.setPassword(newPassword);
				prpDuserDto.setPasswordExpireDate(prpDuserDto.getPasswordExpireDate().addMonth(1));
				uiLogonAction.updatePwd(prpDuserDto); // 修改密码
				// 给予密码修改成功提示信息
				this.addActionMessage(getText("update.password.success"));
				forward = "success";
			} else {
				// 给予密码修改失败信息
//				this.addActionMessage(getText("update.password.false"));
				throw new UserException(-1003,-98,getText("update.password.false"),"原密碼輸入有誤，請重新輸入!");
//				forward = "failure";
			}
		}
		return forward;
	}
}