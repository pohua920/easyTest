package com.sinosoft.app.common.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import com.sinosoft.app.common.model.PerfType;
import com.sinosoft.app.common.service.facade.PerfTypeService;
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.sys.platform.common.Contacts;

public class PerfTypeAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private PerfTypeService perfTypeService;
	private PerfType perfType;
	private PerfType perfTypeVo;
	private String operateType;

	/**
	 * @description 初始化代码类型管理页面
	 * @return
	 * @author 中科软
	 */
	public String initPerfTypeManage() throws Exception {
		System.out.println("initPerfTypeManage start");
		perfTypeVo = new PerfType();
		System.out.println("initPerfTypeManage end");
		return SUCCESS;
	}

	/**
	 * @description 查询代码类型
	 * @return
	 * @author 中科软
	 */
	public String queryPerfType() throws Exception {
		System.out.println("queryPerfType start");
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 100;
		}
		QueryRule queryRule = QueryRule.getInstance();
		try {
			if (!StringUtil.isBlank(perfTypeVo.getCodeType())) {
				queryRule.addEqual("codeType", perfTypeVo.getCodeType());

			}
			if (!StringUtil.isBlank(perfTypeVo.getCodeTypeDesc())) {
				String typeDesc = "%" + perfTypeVo.getCodeTypeDesc() + "%";
				queryRule.addLike("codeTypeDesc", typeDesc);
			}
			if (!StringUtil.isBlank(perfTypeVo.getValidStatus())) {
				queryRule.addEqual("validStatus", perfTypeVo.getValidStatus());
			}
			queryRule.addAscOrder("codeType");
			Page page = perfTypeService.queryPerfType(queryRule, pageNo, pageSize);
			writeJSONData(page, new String[] { "codeType", "codeTypeDesc", "validStatus", "codeNumber" });
			System.out.println("queryPerfType end");
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
			throw e;
		}
		return "none";
	}

	/**
	 * @description 删除代码类型
	 * @return
	 * @author 中科软
	 */
	public String deletePerfType() throws Exception {
		System.out.println("deletePerfType start");
		perfTypeService.deletePerfType(perfType.getCodeType());
		System.out.println("deletePerfType end");
		getRequest().setAttribute("operate", "query");
		return SUCCESS;
	}

	/**
	 * @description 初始化更新代码类型
	 * @return
	 * @author 中科软
	 */
	public String initUpdatePerfType() throws Exception {
		System.out.println("initUpdatePerfType start");
		operateType = Contacts.OperateUPDATE;
		perfType = perfTypeService.findByPK(perfType.getCodeType());
		System.out.println("initUpdatePerfType end");
		return SUCCESS;
	}

	/**
	 * @description 初始化新增代码类型
	 * @return
	 * @author 中科软
	 */
	public String initPerfTypeInput() throws Exception {
		operateType = Contacts.OperateADD;
		perfType = new PerfType();
		return SUCCESS;
	}

	/**
	 * @description 保存代码类型
	 * @return
	 * @author 中科软
	 */
	public String savePerfType() throws Exception {
		perfTypeService.savePerfType(perfType, operateType);
		getRequest().setAttribute("operate", "query");
		return SUCCESS;
	}

	public PerfTypeService getPerfTypeService() {
		return perfTypeService;
	}

	public void setPerfTypeService(PerfTypeService perfTypeService) {
		this.perfTypeService = perfTypeService;
	}

	public PerfType getPerfType() {
		return perfType;
	}

	public void setPerfType(PerfType perfType) {
		this.perfType = perfType;
	}

	public PerfType getPerfTypeVo() {
		return perfTypeVo;
	}

	public void setPerfTypeVo(PerfType perfTypeVo) {
		this.perfTypeVo = perfTypeVo;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
