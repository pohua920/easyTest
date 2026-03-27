package com.sinosoft.app.common.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;
import ins.platform.sui.ac.DwrCodeAction;
import ins.platform.sui.ac.DwrCodeActionInterface;
import ins.platform.sui.ac.vo.CodeCondition;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.user.service.facade.UserService;

public class DwrForCodeAction extends Struts2Action implements DwrCodeActionInterface {
	private static final long serialVersionUID = 1L;
	private List<Object[]> comCodeList;
	private CompanyService companyService;
	private UserService userService;

	public DwrForCodeAction() {
		DwrCodeAction.registCodeType("ComCode", this);
		DwrCodeAction.registCodeType("ExamTaskNo", this);
		DwrCodeAction.registCodeType("UserCode", this);
	}

	@Override
	public Object[] findCodeByName(String codeType, Object name) {
		if ("ComCode".equals(codeType)) {
			System.out.println("-------findCodeByName--------");

		}
		return null;
	}

	@Override
	public List<Object[]> findDwrCode(CodeCondition cond) {
		if ("ComCode".equals(cond.getCodeType())) {
			comCodeList = new ArrayList<Object[]>();
			QueryRule rule = QueryRule.getInstance();
			rule.addLike("comCode", cond.getQuery());
			rule.addEqual("validStatus", "1");
			List<SaaCompany> CompanyList = companyService.getCompany(rule);
			if (CompanyList != null) {
				Object[] obj = null;
				for (SaaCompany dcompany : CompanyList) {
					obj = new Object[2];
					obj[0] = dcompany.getComCode();
					obj[1] = dcompany.getComCName();
					comCodeList.add(obj);
				}
				return comCodeList;

			}
		} else if ("ExamTaskNo".equals(cond.getCodeType())) {
			return null;

		} else if ("UserCode".equals(cond.getCodeType())) {

			return userService.getUser();

		}
		return null;
	}

	@Override
	public Object[] findNameByCode(String codeType, Object code) {
		if ("ComCode".equals(codeType)) {
			System.out.println("-------findNameByCode--------");
		}
		return null;
	}

	/*-------------------以下是get/set方法--------------------*/
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
}
