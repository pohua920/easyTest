/*
 * @(#)CommonSessionUtil.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.vo.UserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 双核系统中的类迁移过来
 */
public class CommonSessionUtil implements java.io.Serializable {
	/** 机构信息服务 */
	private static PrpDcompanyService prpDcompanyService;

	public CommonSessionUtil() {
	}

	/***
	 * 登陆成功後处理将用户相关信息存入session对象
	 * @param req
	 * @param userDto
	 * @throws Exception
	 */
	public static void setLogonSession(HttpServletRequest req, UserDto userDto) throws Exception {
		HttpSession session = req.getSession(true);
		try {
			session.setAttribute("myUserCode", userDto.getUserCode());
			session.setAttribute("myUserName", userDto.getUserName());
			session.setAttribute("myComCode", userDto.getComCode());
			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(userDto.getComCode());
			session.setAttribute("myComCName", prpDcompany.getComCName());
			InetAddress address = InetAddress.getByName("");
			userDto.setRemoteAddr("local:" + address.getLocalHost() + " server:" + req.getRemoteAddr());
			userDto.setLoginTime(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
			userDto.setLoginSystem("undwrt");
			userDto.setLoginSystem("核保核賠處理系統");
			session.setAttribute("prpDuser", userDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public static void setHandleSession(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		String handType = req.getParameter("HandType");
		String editType = req.getParameter("EditType");
		String handTitle = "";
		String editTitle = "";

		if (handType.equals("11")) {
			handTitle = "核保";
		}
		if (handType.equals("12")) {
			handTitle = "審核";// 富邦报价单审核
		} else if (handType.equals("22")) {
			handTitle = "核賠";
		} else if (handType.equals("33")) {
			handTitle = "核損";

		}
		if (editType.equals("deal") || editType.equals("submit")) {
			editTitle = "處理";
		} else if (editType.equals("query")) {
			editTitle = "查詢";

			// 更新session对象中业务类型及处理类型
		}
		session.setAttribute("HandType", handType);
		session.setAttribute("EditType", editType);
		session.setAttribute("HandTitle", handTitle);
		session.setAttribute("EditTitle", editTitle);

	}

	public static void setHandleSession(HttpServletRequest req, String handType, String editType) {
		HttpSession session = req.getSession(true);
		String handTitle = "";
		String editTitle = "";

		if (handType.equals("11")) {
			handTitle = "核保";
		} else if (handType.equals("22")) {
			handTitle = "核賠";
		} else if (handType.equals("33")) {
			handTitle = "核損";

		}
		if (editType.equals("deal") || editType.equals("submit")) {
			editTitle = "處理";
		} else if (editType.equals("query")) {
			editTitle = "查詢";

			// 更新session对象中业务类型及处理类型
		}
		session.setAttribute("HandType", handType);
		session.setAttribute("EditType", editType);
		session.setAttribute("HandTitle", handTitle);
		session.setAttribute("EditTitle", editTitle);

	}

	public static PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public static void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		CommonSessionUtil.prpDcompanyService = prpDcompanyService;
	}

}
