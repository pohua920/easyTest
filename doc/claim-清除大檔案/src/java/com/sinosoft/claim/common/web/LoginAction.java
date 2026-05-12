package com.sinosoft.claim.common.web;

import ins.framework.utils.StringUtils;
import ins.framework.web.Struts2Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.IConstants;
import com.sinosoft.platform.ui.control.action.LogUtils;
import com.sinosoft.platform.ui.control.action.UIPowerAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.sysframework.web.control.OnlineUsers;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.error.UserException;
import com.tlg.commons.util.api.rest.adLogin.AdLogin;
import com.tlg.commons.util.api.rest.adLogin.entity.AdLoginVo;
import com.tlg.commons.util.api.rest.adLogin.entity.ApplyTokenResponseVo;
import com.tlg.commons.util.api.rest.adLogin.util.IPUtil;

public class LoginAction extends Struts2Action {

	/**
	 * 登录处理页面
	 */
	private static final long serialVersionUID = 1L;
	/** 用户代码 */
	private String userCode;
	/** 密码 */
	private String password;
	/** 机构代码 */
	private String comCode;
	/** 岗位代码 */
	private String gradeCodes;
	/** 系统代码 */
	private String systemCode;
	/** 机构处理接口 */
	private PrpDcompanyService prpDcompanyService;
	/** 用户处理接口 */
	private PrpDuserService prpDuserService;
	/** 代码处理接口 */
	private CodeService codeService;
	
	private String cardNo; //mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證
	
	public String login() throws Exception {
		try {
			//mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 start
			boolean cardCheck = isCardCheckOpen();
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 start
			ApplyTokenResponseVo responseVo = null;
			if(cardCheck){
				if(StringUtils.isEmpty(userCode)||StringUtils.isEmpty(password)||StringUtils.isEmpty(this.cardNo)){
					this.getRequest().setAttribute("loginErrMsg", "請輸入員工代號、密碼或識別證！");
					return "false";
				}
				gradeCodes = "";
				userCode = userCode.trim();
				password = password.trim();
				cardNo = cardNo.trim();
				
				AdLogin ad = new AdLogin();
				responseVo = ad.getToken();
				
				AdLoginVo adLoginVo = new AdLoginVo();
				adLoginVo.setUserId(this.userCode.toUpperCase());
				adLoginVo.setPwd(this.password);
				adLoginVo.setCheckCardNo("Y");
				adLoginVo.setCardNo(this.cardNo);
				adLoginVo.setIp(IPUtil.getIpAddr(this.getRequest()));
				responseVo = ad.adValidate(adLoginVo, responseVo.getToken());
				
				//00000→ AD驗證成功 13000 → AD無此帳號
				if(!"00000".equals(responseVo.getCode()) && !"20000".equals(responseVo.getCode())){
					this.getRequest().setAttribute("loginErrMsg", "登入失敗，請確認員工代號、密碼或識別證是否正確！(" + responseVo.getCode() + ")");
					return "false";
				}
			}
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 end
			//mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關 end
			// 根据comCode查出对应的comName
			PrpDcompany prpDcompany = prpDcompanyService.query(comCode);
			String comName = prpDcompany.getComCName();
			String comLevel = prpDcompany.getComLevel();
			// 如果ComLevel为A级则改为1.(直辖市的A级对应1级)
			if (comLevel.equals("A")) {
				comLevel = "1";
			}
	
			// 後台检验並返回登录信息
			DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
			HttpSession session = this.getSession(true);
			PrpDuser prpDuser = prpDuserService.findPrpDuser(userCode);
	
			PrpDuserDto prpDuserDto = new PrpDuserDto();
			prpDuserDto.setUserCode(userCode);
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 start
			//AD帳號不存在時，走原本的判斷
			//mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關
			if(!cardCheck || "20000".equals(responseVo.getCode())){
				prpDuserDto.setPassword(password); 
			}
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 
			prpDuserDto.setLoginComCode(comCode);
			prpDuserDto.setLoginGradeCodes(gradeCodes);
			prpDuserDto.setLoginSystemCode(systemCode);
			// 以下信息 表中无此字段，为对象中增加的属性 如何处理
			prpDuserDto.setComName(comName);
			prpDuserDto.setSid(session.getId());
			prpDuserDto.setRemoteAddr(this.getRequest().getRemoteAddr());
			prpDuserDto.setLoginTime(now);
			if (prpDuser != null) {
				prpDuserDto.setUserName(prpDuser.getUserName());
				prpDuserDto.setUserEName(prpDuser.getUserEName());
				prpDuserDto.setComCode(prpDuser.getComCode());
				session.setAttribute("userName", prpDuserDto.getUserName());
				session.setAttribute("userCode", userCode);

			}
			session.removeAttribute("user");// 在登录前先清空user
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 start
			//AD帳號不存在時，走原本的判斷
			//mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關
			if(!cardCheck || "20000".equals(responseVo.getCode())){
				//CLM9999 柏樺千萬別送  這行關掉 CLAIM就不用密碼囉
//				UIPowerAction.loginnew(prpDuserDto);
			}
			//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 end
			UserDto user = new UserDto();
			user.setUserCode(prpDuserDto.getUserCode());
			user.setPassword(prpDuserDto.getPassword());
			user.setComCode(prpDuserDto.getLoginComCode());
			// 转换用户名称，用户相关部门等等信息
			// 系统区域代码读取
			if (prpDcompany.getSysAreaCode() == null || "".equals(prpDcompany.getSysAreaCode())) {
				String comCode = codeService.getRegistComCode(prpDuserDto.getLoginComCode());
				PrpDcompany prpDcompanyDto = prpDcompanyService.findByPrimaryKey(comCode);
				prpDcompany.setSysAreaCode(prpDcompanyDto.getSysAreaCode());
			}
			user.setUserName(UICodeAction.getInstance().translateUserCode(prpDuserDto.getUserCode(), true));
			user.setComLevel(prpDcompany.getComLevel());
			user.setSysAreaCode(prpDcompany.getSysAreaCode());
			user.setComName(prpDcompany.getComCName());
			user.setLoginSystem("claim");
			// 增加定损价格信息的展现控制功能
			user.setShowPriceFlag(UICodeAction.getInstance().getUserShowPriceFlag(user.getUserCode()));
			// 增加简易赔案操作的控制功能
			boolean quickcaseWrite = false;
			quickcaseWrite = UIPowerAction.checkPowerReturn(prpDuserDto, "claim.quickcase.insert");
			user.setQuickCaseWritePower(quickcaseWrite);
			session.setMaxInactiveInterval(Integer.parseInt(AppConfig.get("sysconst.SESSION_EXPIRED_SECONDS")));
			session.setAttribute("user", user);
			session.setAttribute("prpDuser", prpDuserDto);
			session.setAttribute("password", prpDuserDto.getPassword());
			session.setAttribute("comCode", prpDuserDto.getLoginComCode());
			session.setAttribute("comName", prpDuserDto.getComName());
			session.setAttribute("levelNo", comLevel);
			// 得到用户的权限
			OnlineUsers.getInstance().addUser(prpDuserDto.getSid(), prpDuserDto);
			LogUtils.info(prpDuserDto, IConstants.MODULE_LOGIN, prpDuserDto.getUserName() + "登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關
	 * @return
	 */
	public static boolean isCardCheckOpen() {
		LinkedHashMap<Integer, String> prams = new LinkedHashMap<>();
		prams.put(1, "CARDCHECKSWITCH");
		return "1".equals(selectDB("select valueType from uticonfig where configcode = ?",prams));
	}

	/**
	 * mantis： CLM0110 ，處理人員：BK007 蘇哲，需求單編號：CLM0110.新核心-NFC讀卡機驗證新增開關
	 * @return
	 */
	private static String selectDB(String sql,LinkedHashMap<Integer,String> prams){
		DBManager dbManager = new DBManager();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String result = "";
		try {
			dbManager.open(SysConfig.getProperty("DDCCDATASOURCE"));
			connection = dbManager.getConnection();
			statement = connection.prepareStatement(sql); 
			for(Entry<Integer, String> entry:prams.entrySet()){
				statement.setString(entry.getKey(), entry.getValue());
			}
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(resultSet != null){
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(statement != null){
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				dbManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getGradeCodes() {
		return gradeCodes;
	}

	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 start
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	//mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證 end
}
