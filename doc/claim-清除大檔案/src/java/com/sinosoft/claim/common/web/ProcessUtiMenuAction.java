package com.sinosoft.claim.common.web;

import ins.framework.web.Struts2Action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.MenuService;
import com.sinosoft.platform.dto.domain.PrpDuserDto;

public class ProcessUtiMenuAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 菜单样式 */
	private String menuStyle;
	/** 菜单接口 */
	private MenuService menuService;

	public String showMenu() throws Exception {
		String value = "";
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		HttpSession session = this.getSession();

		PrpDuserDto user = (PrpDuserDto) (session.getAttribute("prpDuser"));
		String menuStyle = request.getParameter("menuStyle");

		if (user.getCurrentRiskCode().trim().equals("")) {
			value = menuService.showMenu(user.getLoginComCode(), user.getUserCode(), user.getLoginGradeCodes(), user.getLoginSystemCode(), menuStyle);
		} else {
			value = menuService.showMenu(user.getLoginComCode(), user.getUserCode(), user.getLoginGradeCodes(), user.getLoginSystemCode(), user.getCurrentRiskCode(), menuStyle);
		}

		//mantis：CLM0293 ，處理人員： DP0713 ，需求單編號：理賠系統加入使用者登入系統的Log記錄 START
		try {
			System.out.println("\r\n------CLM0293(payObjectInfoQuery) start-----\r\ncomcode="+user.getLoginComCode()+"/usercode:"+user.getUserCode()+"/GradeCodes:"+user.getLoginGradeCodes()+"/SystemCode:"+user.getLoginSystemCode()+"\r\n"+value+"\r\n-----CLM0293(payObjectInfoQuery) end-----\r\n");
		} catch (Exception e) {
			//throw e; 
		}
		//mantis：CLM0293 ，處理人員： DP0713 ，需求單編號：理賠系統加入使用者登入系統的Log記錄 END
		
		try {
			response.setContentType("text/html; charset=" + "GBK");
			OutputStream os = response.getOutputStream();
			os.write(value.getBytes());
			os.flush();
		} catch (Exception e) {
			throw e;
		}
		return NONE;
	}

	public String getMenuStyle() {
		return menuStyle;
	}

	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}
