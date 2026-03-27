package com.sinosoft.sys.platform.company.web;

import java.util.List;

import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.util.IConstants;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

public class CompanyAction extends Struts2Action {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULTSIZE = 10;

	private String[] checkbox;

	/** 机构代码 */
	private String comCode;

	// 跳转标记
	private String type;

	private String opreateType;

	// model
	private SaaCompany prpDcompany;

	// 服务
	private CompanyService companyService;

	private PerfCodeService perfCodeService;

	private List<PerfCode> perfCodeTypeLists;

	private List<PerfCode> perfCodeLevelLists;

	private String typeListToStr;

	private String levelListToStr;

	private List<PerfCode> comTypeLists;

	private List<PerfCode> comLevelLists;

	public String companyJump() {
		logger.debug("company");
		logger.debug(type);
		if ("add".equals(type)) {
			return "add";// 转入增加页面
		} else if ("edit".equals(type)) {
			prpDcompany = companyService.findPrpDcompanyByComCode(comCode);
			prpDcompany.setUpperComCode(prpDcompany.getUpperComCode().trim());
			return "edit";// 转入操作页面
		} else {
			return "query";// 转入列表页面
		}
	}

	public String prepareQuery() throws Exception {
		perfCodeTypeLists = perfCodeService.findPerfCodeList("ComType");
		perfCodeLevelLists = perfCodeService.findPerfCodeList("ComLevel");
		return "success";
	}

	public String view() throws Exception {
		prpDcompany = companyService.getPrpDcompanyByComCode(comCode);
		PerfCode perfCodes = perfCodeService.findPerfCodeById("ComType", prpDcompany.getComType());
		if (perfCodes != null) {
			prpDcompany.setComTypeName(perfCodes.getCodeCName());
		}
		perfCodes = perfCodeService.findPerfCodeById("ComLevel", prpDcompany.getComLevel());
		if (perfCodes != null) {
			prpDcompany.setComLevelName(perfCodes.getCodeCName());
		}
		opreateType = "view";
		return "success";
	}

	// 结果列表
	// public String query() {
	// logger.debug("query");
	// if (pageNo == 0) {
	// pageNo = 1;
	// }
	// if (pageSize == 0) {
	// pageSize = 20;
	// }
	// try {
	// Page page = companyService.findPageByPrpDcompany(prpDcompany,
	// pageNo, pageSize);
	// writeJSONData(page, "comCode", "comCName", "comEName",
	// "addressCName", "addressEName", "postCode", "phoneNumber",
	// "upperComCode");
	// } catch (Exception e) {
	// this.writeJSONMsg(e.getMessage());}
	// return null;
	// }
	public String query() {
		String userCode = (String) getSession().getAttribute("UserCode");
		logger.debug("query");
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 50;
		}

		QueryRule queryRule = QueryRule.getInstance();

		if (prpDcompany.getComCode() != null && !"".equals(prpDcompany.getComCode())) {
			queryRule.addEqual("comCode", prpDcompany.getComCode());
		}
		if (prpDcompany.getComCName() != null && !"".equals(prpDcompany.getComCName())) {
			queryRule.addLike("comCName", "%" + prpDcompany.getComCName() + "%");
		}
		if (prpDcompany.getUpperComCode() != null && !"".equals(prpDcompany.getUpperComCode())) {
			queryRule.addEqual("upperComCode", prpDcompany.getUpperComCode());
		}
		if (prpDcompany.getValidStatus() != null && !"".equals(prpDcompany.getValidStatus())) {
			queryRule.addEqual("validStatus", prpDcompany.getValidStatus());
		}
		if (prpDcompany.getComType() != null && !"".equals(prpDcompany.getComType())) {
			queryRule.addEqual("comType", prpDcompany.getComType());
		}
		if (prpDcompany.getComLevel() != null && !"".equals(prpDcompany.getComLevel())) {
			queryRule.addEqual("comLevel", prpDcompany.getComLevel());
		}
		queryRule.addSql("virtualFlag = '1' or virtualFlag = '2'");
		queryRule.addAscOrder("comCode");

		try {
			Page page = companyService.findCompanyByRule(userCode, IConstants.EWPS_SYSTEM_COMPANY_COMMANAGER, queryRule, pageNo, pageSize);
			writeJSONData(page, "comCode", "comCName", "comTypeName", "comLevelName", "upperComCode", "validStatus", "upperComName");
		} catch (Exception e) {
			this.writeJSONMsg(e.getMessage());
		}

		return NONE;
	}

	public void delete(String comCode) {
		System.out.println("aaaaaaaa");
		companyService.deletePrpDcompany(comCode);
	}

	// 添加
	public String addCompany() {
		logger.debug("add");
		companyService.addPrpDcompany(prpDcompany);
		return SUCCESS;

	}

	public String prepareUpdate() throws Exception {
		prpDcompany = companyService.getPrpDcompanyByComCode(comCode);
		PerfCode perfCodes = perfCodeService.findPerfCodeById("ComType", prpDcompany.getComType());
		if (perfCodes != null) {
			prpDcompany.setComTypeName(perfCodes.getCodeCName());
		}
		perfCodes = perfCodeService.findPerfCodeById("ComLevel", prpDcompany.getComLevel());
		if (perfCodes != null) {
			prpDcompany.setComLevelName(perfCodes.getCodeCName());
		}
		comTypeLists = perfCodeService.findPerfCodeList("ComType");
		comLevelLists = perfCodeService.findPerfCodeList("ComLevel");
		opreateType = "edit";
		return "success";
	}

	public String prepareCompanyAdd() {
		opreateType = "add";
		return "success";
	}

	public String add() {
		companyService.addPrpDcompany(prpDcompany);
		return SUCCESS;
	}

	// 修改
	public String change() {
		companyService.updatePrpDcompany(prpDcompany);
		return SUCCESS;

	}

	public String update() {
		companyService.updatePrpDcompany(prpDcompany);
		getRequest().setAttribute("operate", "query");
		return SUCCESS;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public SaaCompany getPrpDcompany() {
		return prpDcompany;
	}

	public void setPrpDcompany(SaaCompany prpDcompany) {
		this.prpDcompany = prpDcompany;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComCode() {
		return comCode;
	}

	public String getOpreateType() {
		return opreateType;
	}

	public void setOpreateType(String opreateType) {
		this.opreateType = opreateType;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	public static int getDEFAULTSIZE() {
		return DEFAULTSIZE;
	}

	public void setPerfCodeService(PerfCodeService perfCodeService) {
		this.perfCodeService = perfCodeService;
	}

	public PerfCodeService getPerfCodeService() {
		return perfCodeService;
	}

	public void setPerfCodeTypeLists(List<PerfCode> perfCodeTypeLists) {
		this.perfCodeTypeLists = perfCodeTypeLists;
	}

	public List<PerfCode> getPerfCodeTypeLists() {
		return perfCodeTypeLists;
	}

	public void setPerfCodeLevelLists(List<PerfCode> perfCodeLevelLists) {
		this.perfCodeLevelLists = perfCodeLevelLists;
	}

	public List<PerfCode> getPerfCodeLevelLists() {
		return perfCodeLevelLists;
	}

	public void setTypeListToStr(String typeListToStr) {
		this.typeListToStr = typeListToStr;
	}

	public String getTypeListToStr() {
		return typeListToStr;
	}

	public void setLevelListToStr(String levelListToStr) {
		this.levelListToStr = levelListToStr;
	}

	public String getLevelListToStr() {
		return levelListToStr;
	}

	public List<PerfCode> getComTypeLists() {
		return comTypeLists;
	}

	public void setComTypeLists(List<PerfCode> comTypeLists) {
		this.comTypeLists = comTypeLists;
	}

	public List<PerfCode> getComLevelLists() {
		return comLevelLists;
	}

	public void setComLevelLists(List<PerfCode> comLevelLists) {
		this.comLevelLists = comLevelLists;
	}

}
