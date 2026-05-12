package com.sinosoft.app.common.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.model.PerfCodeId;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.sys.platform.common.Contacts;

public class PerfCodeAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private PerfCodeService perfCodeService;
	private PerfCode perfCode;
	private PerfCode perfCodeVo;
	private String codeType;
	private String operateType;

	/**
	 * @description 初始化代码类型管理页面
	 * @return
	 * @author 中科软
	 */
	public String initPerfCodeManage() throws Exception {
		System.out.println("initPerfCodeManage start");
		System.out.println("initPerfCodeManage end");
		return SUCCESS;
	}

	/**
	 * @description 查询代码类型
	 * @return
	 * @author 中科软
	 */
	public String queryPerfCode() throws Exception {
		System.out.println("queryPerfCode start");
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 100;
		}
		QueryRule queryRule = QueryRule.getInstance();
		try {
			queryRule.addEqual("id.codeType", perfCodeVo.getId().getCodeType());
			if (!StringUtil.isBlank(perfCodeVo.getId().getCodeCode())) {
				queryRule.addEqual("id.codeCode", perfCodeVo.getId().getCodeCode());

			}
			if (!StringUtil.isBlank(perfCodeVo.getCodeCName())) {
				String name = "%" + perfCodeVo.getCodeCName() + "%";
				queryRule.addLike("codeCName", name);
			}
			if (!StringUtil.isBlank(perfCodeVo.getValidStatus())) {
				queryRule.addEqual("validStatus", perfCodeVo.getValidStatus());
			}
			queryRule.addAscOrder("displayNo");
			Page page = perfCodeService.queryPerfCode(queryRule, pageNo, pageSize);
			writeJSONData(page, new String[] { "displayNo", "id", "codeCName", "codeEName", "validStatus" });
			System.out.println("queryPerfCode end");
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
			throw e;
		}
		return "none";
	}

	/**
	 * @description 查看代码类型详细信息。。。。。。。。。。。。暂时没用
	 * @return
	 * @author 中科软
	 */
	public String viewPerfCode() throws Exception {
		System.out.println("viewPerfType start");
		perfCode.getId().setCodeType(perfCodeVo.getId().getCodeType());
		perfCode = perfCodeService.findPerfCodeById(perfCode.getId().getCodeType(), perfCode.getId().getCodeCode());
		System.out.println("viewPerfType end");
		return SUCCESS;
	}

	/**
	 * @description 删除代码类型
	 * @return
	 * @author 中科软
	 */
	public String deletePerfCode() throws Exception {
		System.out.println("deletePerfCode start");
		perfCodeService.deletePerfCode(perfCode.getId().getCodeType(), perfCode.getId().getCodeCode());
		System.out.println("deletePerfCode end");
		return SUCCESS;
	}

	/**
	 * @description 初始化更新代码类型
	 * @return
	 * @author 中科软
	 */
	public String initUpdatePerfCode() throws Exception {
		System.out.println("initUpdatePerfCode start");
		operateType = Contacts.OperateUPDATE;
		perfCode.getId().setCodeType(perfCodeVo.getId().getCodeType());
		perfCode = perfCodeService.findPerfCodeById(perfCode.getId().getCodeType(), perfCode.getId().getCodeCode());
		System.out.println("initUpdatePerfCode end");
		return SUCCESS;
	}

	/**
	 * @description 初始化新增代码类型
	 * @return
	 * @author 中科软
	 */
	public String initPerfCodeInput() throws Exception {
		operateType = Contacts.OperateADD;
		perfCode = new PerfCode();
		PerfCodeId id = new PerfCodeId();
		id.setCodeType(perfCodeVo.getId().getCodeType());
		perfCode.setId(id);
		return SUCCESS;
	}

	/**
	 * @description 保存代码类型
	 * @return
	 * @author 中科软
	 */
	public String savePerfCode() throws Exception {
		perfCodeService.savePerfCode(perfCode, operateType);
		getRequest().setAttribute("operate", "query");
		return SUCCESS;
	}

	public PerfCodeService getPerfCodeService() {
		return perfCodeService;
	}

	public void setPerfCodeService(PerfCodeService perfCodeService) {
		this.perfCodeService = perfCodeService;
	}

	public PerfCode getPerfCode() {
		return perfCode;
	}

	public void setPerfCode(PerfCode perfCode) {
		this.perfCode = perfCode;
	}

	public PerfCode getPerfCodeVo() {
		return perfCodeVo;
	}

	public void setPerfCodeVo(PerfCode perfCodeVo) {
		this.perfCodeVo = perfCodeVo;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
