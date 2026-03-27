package com.sinosoft.sys.platform.power.web;

import java.util.List;

import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.exception.BusinessException;
import ins.framework.web.Struts2Action;

@SuppressWarnings("serial")
public class SaaUserAction extends Struts2Action {
	private String userCode;
	private String comCode;
	private String comName;
	private String saaGradeCode;
	private SaaUser saaUser;
	private SaaUserService saaUserService;
	private SaaPowerService saaPowerService;
	private CompanyService companyService;

	/**
	 * 查询用户基本信息来进行权限配置
	 * 
	 * @author 中科软
	 * @throws Exception
	 */
	public String query() throws Exception {
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		QueryRule queryRule = QueryRule.getInstance();
		if (getSaaUser().getUserCode() != null
				&& !"".equals(getSaaUser().getUserCode())) {
			queryRule.addEqual("userCode", getSaaUser().getUserCode());
		}
		if (getSaaUser().getUserName() != null
				&& !"".equals(getSaaUser().getUserName())) {
			queryRule.addEqual("userName", getSaaUser().getUserName());
		}
		if (getSaaUser().getComCode() != null
				&& !"".equals(getSaaUser().getComCode())) {
			queryRule.addEqual("comCode", getSaaUser().getComCode());
		}
//		if (comName != null && !"".equals(comName)) {
////			queryRule.addLike("comCName", comName);
//		}
		queryRule.addAscOrder("userCode");
		try {
//			String comCodeStr = "";
//			QueryRule rule = QueryRule.getInstance();
//			if (comName != null && !"".equals(comName)) {
//				rule.addLike("comCName", comName);
//				List<SaaCompany> perfCodeList = companyService.getCompany(rule);
//				for (SaaCompany itemCompany : perfCodeList) {
//					comCodeStr += ",'" + itemCompany.getComCode() + "'";
//				}
//				queryRule.addSql("saauser.comCode in (" + comCodeStr.substring(1) + ")");
//			}
			Page page = saaUserService.queryUserTranslateCode(queryRule,pageNo, pageSize);
			writeJSONData(page, new String[] {"userCode", "userName", "comCode" , "validStatus"});
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
		}
		return "none";
	}
	

	public void queryUserJSP() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_INSTEAD_QUERY, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("您沒有人員代崗授權查詢權限！", false);
		}
		saaUserService.queryUserJSP(userCode, comCode, saaGradeCode, userCode1);
	}

	public void queryUserJSPByUserCode() {
		String userCode1 = (String) getSession().getAttribute("UserCode");
		boolean hasPower = saaPowerService.checkPower(userCode1,
				IConstants.SAA_USERPOWER_POWERFULLCOPY, (Integer) getSession()
						.getAttribute("PowerType"), "");
		if (!hasPower) {
			throw new BusinessException("您沒有人員權限復制權限！", false);
		}
		saaUserService.queryUserJSPByUserCode(userCode, userCode1);
	}

	public String queryUser() {
		try {
			Page page = saaUserService.getUserList(saaUser, this.pageNo,this.pageSize,(String) getSession().getAttribute("UserCode"));
			this.writeJSONData(page, "userCode", "userName", "comCode","validStatus","comCName");
		} catch (Exception e) {
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	public String queryAgentUser() {
		try {
			Page page = saaUserService.getAgengUserList(saaUser, this.pageNo,
					this.pageSize,
					(String) getSession().getAttribute("UserCode"));
			this.writeJSONData(page, "userCode", "userName", "comCode",
					"validStatus");
		} catch (Exception e) {
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public String getSaaGradeCode() {
		return saaGradeCode;
	}

	public void setSaaGradeCode(String saaGradeCode) {
		this.saaGradeCode = saaGradeCode;
	}

	public SaaUser getSaaUser() {
		return saaUser;
	}

	public void setSaaUser(SaaUser saaUser) {
		this.saaUser = saaUser;
	}

	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}
	
	

}
