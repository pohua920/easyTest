package com.sinosoft.undwrt.common.util;

import java.net.InetAddress;
import java.util.Date;
import javax.servlet.http.*;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.dto.domain.PrpDcompanyDto;
import com.sinosoft.platform.ui.model.PrpDcompanyFindByPrimaryKeyCommand;
import com.sinosoft.undwrt.pub.InternationalizationUtil;

/**
 * <p>
 * Title: uwweb
 * </p>
 * <p>
 * Description: 公用session对象
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * .
 * 
 * @author Luyang
 * @version 1.0
 */
public class CommonSession implements java.io.Serializable {
	
	/**
	 * Instantiates a new common session.
	 */
	public CommonSession() {
	}

	// 登陆成功后处理将用户相关信息存入session对象
	/**
	 * Sets the logon session.
	 * 
	 * @param req
	 *            the req
	 * @param prpDuserDto
	 *            the prp duser dto
	 * @throws Exception
	 *             the exception
	 */
	public static void setLogonSession(HttpServletRequest req,
			PrpDuserDto prpDuserDto) throws Exception {
		HttpSession session = req.getSession(true);
		try {
			session.setAttribute("myUserCode", prpDuserDto.getUserCode());
			session.setAttribute("myUserName", prpDuserDto.getUserName());
			session.setAttribute("myComCode", prpDuserDto.getComCode());
			PrpDcompanyDto prpDcompanyDto = (PrpDcompanyDto) new PrpDcompanyFindByPrimaryKeyCommand(
					prpDuserDto.getComCode()).execute();
			session.setAttribute("myComCName", prpDcompanyDto.getComCName());
			InetAddress address = InetAddress.getByName("");
			prpDuserDto.setRemoteAddr("local:" + address.getLocalHost()
					+ " server:" + req.getRemoteAddr());
			prpDuserDto.setLoginTime(new DateTime(new Date(),
					DateTime.YEAR_TO_SECOND));
			prpDuserDto.setLoginSystemCode("undwrt");
			prpDuserDto.setLoginSystem("核保核赔处理系统");
			session.setAttribute("prpDuserDto", prpDuserDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 設置屬性the sinosoft handle session.
	 * 
	 * @param req
	 *            待設置的the sinosoft handle session的值
	 */
	public static void setHandleSession(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		String handType = req.getParameter("handType");
		String editType = req.getParameter("editType");
		String handTitle = "";
		String editTitle = "";
    	InternationalizationUtil internal = new InternationalizationUtil();


		if (handType.equals("11")) {
			handTitle = internal.getText("undwrt.handTitle");
		}
		if (handType.equals("12")) {
			handTitle = "审核";//报价单审核
		} else if (handType.equals("22")) {
			handTitle = "核赔";
		} else if (handType.equals("33")) {
			handTitle = "核损";
		}
		if (editType.equals("deal") || editType.equals("submit")) {
			editTitle = internal.getText("undwrt.editTitle");
		} else if (editType.equals("query")) {
			editTitle = internal.getText("undwrt.editTitle");
		}
		// 更新session对象中业务类型及处理类型
		session.setAttribute("handType", handType);
		session.setAttribute("editType", editType);
		session.setAttribute("handTitle", handTitle);
		session.setAttribute("editTitle", editTitle);
	}

	/**
	 * Sets the handle session.
	 * 
	 * @param req
	 *            the req
	 * @param handType
	 *            the hand type
	 * @param editType
	 *            the edit type
	 */
	public static void setHandleSession(HttpServletRequest req,
			String handType, String editType) {
		HttpSession session = req.getSession(true);
		String handTitle = "";
		String editTitle = "";
    	InternationalizationUtil internal = new InternationalizationUtil();
		if (handType.equals("11")) {
			handTitle = internal.getText("undwrt.handTitle");
		} else if (handType.equals("22")) {
			handTitle = "核赔";
		} else if (handType.equals("33")) {
			handTitle = "核损";
		}
		if (editType.equals("deal") || editType.equals("submit")) {
			editTitle = internal.getText("undwrt.editTitle");
		} else if (editType.equals("query")) {
			editTitle = internal.getText("undwrt.editTitle");
		}
		// 更新session对象中业务类型及处理类型
		session.setAttribute("handType", "11");
		session.setAttribute("editType", editType);
		session.setAttribute("handTitle", handTitle);
		session.setAttribute("editTitle", editTitle);
	}
}
