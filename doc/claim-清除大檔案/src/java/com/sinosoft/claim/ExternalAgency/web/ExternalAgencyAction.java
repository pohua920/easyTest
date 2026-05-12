package com.sinosoft.claim.ExternalAgency.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.ExternalAgency.service.facade.ExternalagencyService;
import com.sinosoft.claim.ExternalAgency.util.ExternalAgencyViewHelper;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.sysframework.reference.AppConfig;

public class ExternalAgencyAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 外部机构服务 */
	private ExternalagencyService externalagencyService;
	/** 外部机构数据收集*/
	private ExternalAgencyViewHelper externalAgencyViewHelper;

	public ExternalAgencyViewHelper getExternalAgencyViewHelper() {
		return externalAgencyViewHelper;
	}

	public void setExternalAgencyViewHelper(ExternalAgencyViewHelper externalAgencyViewHelper) {
		this.externalAgencyViewHelper = externalAgencyViewHelper;
	}

	public ExternalagencyService getExternalagencyService() {
		return externalagencyService;
	}

	public void setExternalagencyService(ExternalagencyService externalagencyService) {
		this.externalagencyService = externalagencyService;
	}

	/**
	 * 公估信息处理
	 * @return 页面类型
	 * @throws Exception
	 */
	public String externalAgency() throws NumberFormatException, Exception {
		HttpServletRequest httpServletRequest = getRequest();
//		HttpSession session = httpServletRequest.getSession();
		String forward = "";
		String editType = httpServletRequest.getParameter("editType");
//		String recordPerPage = "";
//		try {
//			recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String pageno = httpServletRequest.getParameter("pageNo");
		PrpLexternalAgency prplexternalagency = null;
		if (pageno == null || pageno.trim().equals("")) {
			pageno = "1";
		}
		if ("queryResult".equals(editType)) {
			logger.debug("查询满足条件的 公告信息");
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			try {
				page = this.externalAgencyViewHelper.externalAgencyDtoToView(getRequest(), pageNo, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.writeJSONData(page, "id", "comcname", "juridicalperson", "validStatus");
			return NONE;
		} else if ("delete".equals(editType)) {
			String strComCode = httpServletRequest.getParameter("comCode");
			String strComType = httpServletRequest.getParameter("comtype");
			logger.debug("查询满足条件的 公告信息");
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			try {
				externalagencyService.deleteByConditions(strComCode, strComType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				externalAgencyViewHelper.externalAgencyDtoToView(httpServletRequest, pageNo, pageSize);
				httpServletRequest.setAttribute("showflg", "true");// 设置回显标识
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = "queryMain";
		} else if ("add".equals(editType)) {
			forward = editType;
		} else if ("insertSave".equals(editType)) {
			try {
				prplexternalagency = externalAgencyViewHelper.viewToDto(httpServletRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				externalagencyService.insert(prplexternalagency);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = "result";
		} else if ("show".equals(editType)) {
			try {
				logger.debug("查询满足条件的 公告信息");
				if (pageNo == 0) {
					pageNo = 1;
				}
				if (pageSize == 0) {
					pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
				}
				prplexternalagency = externalAgencyViewHelper.externalagencyShow(getRequest(), pageNo, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = editType;
		} else if ("update".equals(editType)) {
			try {
				prplexternalagency = externalAgencyViewHelper.externalagencyUpdate(httpServletRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = "update";
		} else if ("updateSave".equals(editType)) {
			try {
				prplexternalagency = externalAgencyViewHelper.viewToDto(httpServletRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				externalagencyService.update(prplexternalagency);
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = "result";

		}

		return forward;
	}
}
