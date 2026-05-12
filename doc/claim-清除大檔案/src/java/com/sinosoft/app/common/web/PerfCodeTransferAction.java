package com.sinosoft.app.common.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import com.sinosoft.app.common.model.PerfCodeTransfer;
import com.sinosoft.app.common.model.PerfCodeTransferId;
import com.sinosoft.app.common.service.facade.PerfCodeTransferService;
import com.sinosoft.app.common.util.StringUtil;

public class PerfCodeTransferAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private PerfCodeTransferService perfCodeTransferService;
	private PerfCodeTransfer perfCodeTransfer;
	private PerfCodeTransfer perfCodeTransferVo;

	/**
	 * @description 初始化代码对照管理页面
	 * @return
	 * @author 中科软
	 */
	public String initPerfCodeTransferManage() throws Exception {
		System.out.println("initPerfCodeTransferManage start");
		perfCodeTransferVo = new PerfCodeTransfer();
		System.out.println("initPerfCodeTransferManage end");
		return SUCCESS;
	}

	/**
	 * @description 查询代码对照
	 * @return
	 * @author 中科软
	 */
	public String queryPerfCodeTransfer() throws Exception {
		System.out.println("queryPerfCodeTransfer start");
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 100;
		}
		QueryRule queryRule = QueryRule.getInstance();
		try {
			if (!StringUtil.isBlank(perfCodeTransferVo.getId().getTransferId())) {
				queryRule.addEqual("id.transferId", perfCodeTransferVo.getId().getTransferId());

			}
			if (!StringUtil.isBlank(perfCodeTransferVo.getId().getCodeType())) {
				queryRule.addEqual("id.codeType", perfCodeTransferVo.getId().getCodeType());

			}
			if (!StringUtil.isBlank(perfCodeTransferVo.getToCode())) {
				queryRule.addEqual("toCode", perfCodeTransferVo.getToCode());

			}
			if (!StringUtil.isBlank(perfCodeTransferVo.getId().getCodeCode())) {
				queryRule.addEqual("id.codeCode", perfCodeTransferVo.getId().getCodeCode());

			}
			if (!StringUtil.isBlank(perfCodeTransferVo.getValidStatus())) {
				queryRule.addEqual("validStatus", perfCodeTransferVo.getValidStatus());
			}
			queryRule.addAscOrder("id.transferId");
			Page page = perfCodeTransferService.queryPerfCodeTransfer(queryRule, pageNo, pageSize);
			writeJSONData(page, new String[] { "id", "id.codeType", "id.codeCode", "toCode", "validStatus", "remark" });
			System.out.println("queryPerfCodeTransfer end");
		} catch (Exception e) {
			e.printStackTrace();
			writeJSONMsg(e.getMessage());
			throw e;
		}
		return "none";
	}

	/**
	 * @description 删除代码对照
	 * @return
	 * @author 中科软
	 */
	public String deletePerfCodeTransfer() throws Exception {
		System.out.println("deletePerfCodeTransfer start");
		perfCodeTransferService.deletePerfCodeTransfer(perfCodeTransfer.getId());
		System.out.println("deletePerfCodeTransfer end");
		return SUCCESS;
	}

	/**
	 * @description 初始化更新代码对照
	 * @return
	 * @author 中科软
	 */
	public String initUpdatePerfCodeTransfer() throws Exception {
		System.out.println("initUpdatePerfCodeTransfer start");
		perfCodeTransfer = perfCodeTransferService.findPerfCodeTransferById(perfCodeTransfer.getId());
		System.out.println("initUpdatePerfCodeTransfer end");
		return SUCCESS;
	}

	/**
	 * @description 初始化新增代码对照
	 * @return
	 * @author 中科软
	 */
	public String initPerfCodeTransferInput() throws Exception {
		perfCodeTransfer = new PerfCodeTransfer();
		PerfCodeTransferId id = new PerfCodeTransferId();
		perfCodeTransfer.setId(id);
		return SUCCESS;
	}

	/**
	 * @description 保存代码对照
	 * @return
	 * @author 中科软
	 */
	public String savePerfCodeTransfer() throws Exception {
		perfCodeTransferService.savePerfCodeTransfer(perfCodeTransfer);
		getRequest().setAttribute("operate", "query");
		return SUCCESS;
	}

	public PerfCodeTransferService getPerfCodeTransferService() {
		return perfCodeTransferService;
	}

	public void setPerfCodeTransferService(PerfCodeTransferService perfCodeTransferService) {
		this.perfCodeTransferService = perfCodeTransferService;
	}

	public PerfCodeTransfer getPerfCodeTransfer() {
		return perfCodeTransfer;
	}

	public void setPerfCodeTransfer(PerfCodeTransfer perfCodeTransfer) {
		this.perfCodeTransfer = perfCodeTransfer;
	}

	public PerfCodeTransfer getPerfCodeTransferVo() {
		return perfCodeTransferVo;
	}

	public void setPerfCodeTransferVo(PerfCodeTransfer perfCodeTransferVo) {
		this.perfCodeTransferVo = perfCodeTransferVo;
	}

}
