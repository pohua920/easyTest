package com.sinosoft.undwrt.common.web;

import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.IConstants;
import com.sinosoft.platform.ui.control.action.LogUtils;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.web.control.OnlineUsers;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.CoreService;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;
import com.tlg.util.api.rest.adLogin.AdLogin;
import com.tlg.util.api.rest.adLogin.entity.AdLoginVo;
import com.tlg.util.api.rest.adLogin.entity.ApplyTokenResponseVo;
import com.tlg.util.api.rest.adLogin.util.IPUtil;

/**
 * 系統登錄處理類.
 */
public class LoginAction extends Struts2Action {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 屬性用戶代碼. */
	private String userCode;

	/** 屬性密碼. */
	private String password;

	/** 屬性機構代碼. */
	private String comCode;

	/** 屬性密碼職位代碼. */
	private String gradeCodes;

	/** 屬性系統代碼. */
	private String systemCode;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性用戶訊息接口. */
	private PrpDuserService prpDuserService;

	/** 核保級別設定接口 */
	private UtiUwLevelService utiUwLevelService;

	/**
	 * 登入系統.
	 * 
	 * @return the string
	 * @throws Exception
	 *             異常
	 */
	public String login() throws Exception {
		// 改为从登录页面获取
		gradeCodes = "";
		// 根据comCode查出对应的comName
		PrpDcompany prpdcompany = prpDcompanyService.findByPrimaryKey(comCode);
		String comName = prpdcompany.getComCName();

		// 后台检验并返回登录信息
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		HttpSession session = this.getSession(true);
		PrpDuser prpduser = prpDuserService.getUser(userCode);

		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto.setUserCode(userCode);
		prpDuserDto.setPassword(password);
		prpDuserDto.setLoginComCode(comCode);
		prpDuserDto.setLoginGradeCodes(gradeCodes);
		prpDuserDto.setLoginSystemCode(systemCode);
		prpDuserDto.setComName(comName);
		prpDuserDto.setSid(session.getId());
		prpDuserDto.setRemoteAddr(this.getRequest().getRemoteAddr());
		prpDuserDto.setLoginTime(now);
		// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 START
	    String logUserCode = "";//先預設IP，userCode有值再塞入
	    try {
			if (this.getRequest().getHeader("HTTP_X_FORWARDED_FOR") == null) {
				logUserCode = this.getRequest().getRemoteAddr();
		    } else {
		    	logUserCode = this.getRequest().getHeader("HTTP_X_FORWARDED_FOR");
		    }
	    } catch (Exception exception) {
	    	exception.printStackTrace();
	    } 
		// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 END

		try {
			// mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證  START
		    ApplyTokenResponseVo responseVo = null;
		    if(StringUtils.isEmpty(userCode)||StringUtils.isEmpty(password)){
				throw new IllegalArgumentException("請輸入員工代號、密碼");
			}
			gradeCodes = "";
			userCode = userCode.trim();
			password = password.trim();
			
			AdLogin ad = new AdLogin();
			responseVo = ad.getToken();
			
			AdLoginVo adLoginVo = new AdLoginVo();
			adLoginVo.setUserId(this.userCode.toUpperCase());
			adLoginVo.setPwd(this.password);
			adLoginVo.setCheckCardNo("N");
//			adLoginVo.setCardNo(this.cardNo);
			adLoginVo.setIp(IPUtil.getIpAddr(this.getRequest()));
			responseVo = ad.adValidate(adLoginVo, responseVo.getToken());
			
			//00000→ AD驗證成功 13000 → AD無此帳號
			if(!"00000".equals(responseVo.getCode())){
				throw new IllegalArgumentException("登入失敗，請確認員工代號、密碼是否正確！(" + responseVo.getCode() + ")");
			}
			// mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證 END
			if (prpduser != null) {
				prpDuserDto.setUserName(prpduser.getUserName());
				prpDuserDto.setUserEName(prpduser.getUserEName());
				session.setAttribute("userName", prpDuserDto.getUserName());
				session.setAttribute("userCode", userCode);
               //add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin	
				session.setAttribute("date", prpduser.getPasswordExpireDate());
				session.setAttribute("now", now);
				if(prpduser.getPasswordExpireDate().getTime()<now.getTime()){
				session.setAttribute("nolog", "nolog");
                //add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin	
				}
			}
		 	session.removeAttribute("user");
           //add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin	
		 	session.setAttribute("user", prpDuserDto);
		 // mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證 
//			UIPowerAction.loginnew(prpDuserDto);

		    //session.setAttribute("user", prpDuserDto);
            //add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin	
			session.setAttribute("password", prpDuserDto.getPassword());
			session.setAttribute("comCode", prpDuserDto.getLoginComCode());
			session.setAttribute("comName", prpDuserDto.getComName());
			session.setAttribute("myUserCode", prpDuserDto.getUserCode());
			session.setAttribute("myUserName", prpDuserDto.getUserName());
			session.setAttribute("myComCode", prpDuserDto.getLoginComCode());
			session.setAttribute("myComCName", prpdcompany.getComCName());

			OnlineUsers.getInstance().addUser(prpDuserDto.getSid(), prpDuserDto);
			LogUtils.info(prpDuserDto, IConstants.MODULE_LOGIN, prpDuserDto.getUserName() + getText("undwrt.action.login.loginSuccess"));
			
			// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 START
			if(StringUtils.isNotBlank(userCode)){
				logUserCode = userCode;
			}
			((CoreService)ServiceFactory.getService("coreService")).insertCoreLoginRecord("UNDWRT", logUserCode,
					new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()), "00000");// 紀錄使用者登錄"成功"
			// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 END
			
		} catch (Exception e) {
			// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 START
			if(StringUtils.isNotBlank(userCode)){
				logUserCode = userCode;
			}
			((CoreService)ServiceFactory.getService("coreService")).insertCoreLoginRecord("UNDWRT", logUserCode,
					new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()), "00001");// 紀錄使用者登錄"失敗"
			// mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄 END
			
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 獲取屬性用戶代碼.
	 * 
	 * @return 屬性用戶代碼的值
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 設置屬性用戶代碼.
	 * 
	 * @param userCode
	 *            待設置的用戶代碼的值
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 獲取屬性密碼.
	 * 
	 * @return 屬性密碼的值
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 設置屬性密碼.
	 * 
	 * @param password
	 *            待設置的密碼的值
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 獲取屬性密碼職位代碼.
	 * 
	 * @return 屬性密碼職位代碼的值
	 */
	public String getGradeCodes() {
		return gradeCodes;
	}

	/**
	 * 設置屬性密碼職位代碼.
	 * 
	 * @param gradeCodes
	 *            待設置的密碼職位代碼的值
	 */
	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}

	/**
	 * 獲取屬性系統代碼.
	 * 
	 * @return 屬性系統代碼的值
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * 設置屬性系統代碼.
	 * 
	 * @param systemCode
	 *            待設置的系統代碼的值
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	/**
	 * 獲取屬性用戶訊息接口.
	 * 
	 * @return 屬性用戶訊息接口的值
	 */
	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	/**
	 * 設置屬性用戶訊息接口.
	 * 
	 * @param prpDuserService
	 *            待設置的用戶訊息接口的值
	 */
	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	/**
	 * 獲取核保級別設定接口.
	 * 
	 * @return the 核保級別設定接口
	 */
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	/**
	 * 設置核保級別設定接口.
	 * 
	 * @param utiUwLevelService
	 *            待設置的核保級別設定接口的值
	 */
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}
}
