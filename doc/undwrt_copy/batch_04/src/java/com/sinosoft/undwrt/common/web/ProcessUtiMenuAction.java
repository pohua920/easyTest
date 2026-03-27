package com.sinosoft.undwrt.common.web;

import ins.framework.web.Struts2Action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.model.UtiMenuShowMenuCommand;
import com.sinosoft.undwrt.common.service.facade.MenuService;


/**
 * 展示功能選單處理類.
 */
public class ProcessUtiMenuAction extends Struts2Action{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 屬性功能選單樣式. */
	private String menuStyle;
	
	/** 屬性功能選單接口. */
	private MenuService undwrtMenuService;

	/**
	 * 展示功能選單.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String showMenu() throws Exception{
		String value = "";
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
        HttpSession session = this.getSession();
        
        PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
        String menuStyle = request.getParameter("menuStyle");
        UtiMenuShowMenuCommand command;
        
        if (user.getCurrentRiskCode().trim().equals("")) {
        	
        	value = undwrtMenuService.showMenu(user.getLoginComCode(), user
                    .getUserCode(), user.getLoginGradeCodes(), user
                    .getLoginSystemCode(), menuStyle);
        } else {
        	value = undwrtMenuService.showMenu(user.getLoginComCode(), user
                    .getUserCode(), user.getLoginGradeCodes(), user
                    .getLoginSystemCode(), user.getCurrentRiskCode(), menuStyle);
        }
        
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
	
	/**
	 * 獲取屬性功能選單樣式.
	 * 
	 * @return 屬性功能選單樣式的值
	 */
	public String getMenuStyle() {
		return menuStyle;
	}

	/**
	 * 設置屬性功能選單樣式.
	 * 
	 * @param menuStyle
	 *            待設置的功能選單樣式的值
	 */
	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	/**
	 * 獲取屬性功能選單接口.
	 * 
	 * @return 屬性功能選單接口的值
	 */
	public MenuService getUndwrtMenuService() {
		return undwrtMenuService;
	}

	/**
	 * 設置屬性功能選單接口.
	 * 
	 * @param undwrtMenuService
	 *            待設置的功能選單接口的值
	 */
	public void setUndwrtMenuService(MenuService undwrtMenuService) {
		this.undwrtMenuService = undwrtMenuService;
	}
    
}
