package com.sinosoft.claim.archive.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.archive.util.ArchiveViewHelper;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveService;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utiall.blsvr.BLPrpDclass;
import com.sinosoft.utiall.schema.PrpDclassSchema;

/**
 * 分发HTTP GET 实体资料归档调阅查询
 * <p>
 * Title: 理赔实体资料归档调阅查询信息
 * </p>
 * <p>
 * Description: 理赔实体资料归档调阅查询信息系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class ArchiveQueryAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 资料归档viewHelper */
	private ArchiveViewHelper archiveViewHelper;
	/** 资料归档调阅主表接口service */
	private PrpLDocArchiveService prpLDocArchiveService;

	/**
	 * 归档查询
	 * @return
	 * @throws Exception
	 */
	public String archiveQuery() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String editType = httpServletRequest.getParameter("editType");
		if ("selectClassCode".equals(editType)) {
			List<PrpDclassSchema> list = new ArrayList<PrpDclassSchema>();
			PrpDclassSchema prpDclassSchema = null;
			BLPrpDclass blPrpDclass = new BLPrpDclass();
			blPrpDclass.query(" 1=1 and validstatus='1'");
			for (int temp = 0; temp < blPrpDclass.getSize(); temp++) {
				prpDclassSchema = blPrpDclass.getArr(temp);
				list.add(prpDclassSchema);
			}
			httpServletRequest.setAttribute("list", list);
		}
		if ("archiveBefore".equals(editType) || "query".equals(editType) || "apply".equals(editType)) {
			String recordPerPage = httpServletRequest.getParameter("pageSize");// 每页显示的行数
			if (recordPerPage == null || "".equals(recordPerPage)) {
				recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
			}
			String pageNo = httpServletRequest.getParameter("pageNo");// 页码
			if (pageNo == null || pageNo.trim().equals("")) {
				pageNo = "1";
			}
			int intRecordPerPage = Integer.parseInt(recordPerPage);
			int intPageNo = Integer.parseInt(pageNo);
			Page page = archiveViewHelper.setPrpLDocArchiveDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			if ("query".equals(editType)) {
				this.writeJSONData(page, "claimNo", "insuredName", "endCaseDate", "applicantName", "applyDate", "estimateReturnDate", "status");
			} else {
				this.writeJSONData(page, "claimNo", "policyNo", "insuredName", "endCaseDate", "sumDutyPaid");
			}
			return NONE;
		} else if ("applyFinish".equals(editType)) {
			archiveViewHelper.setPrpLDocArchiveDtoToView(httpServletRequest);
		} else if ("audit".equals(editType)) {
			String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");// 每页显示的行数
			String pageNo = httpServletRequest.getParameter("pageNo");// 页码
			if (pageNo == null || pageNo.trim().equals("")) {
				pageNo = "1";
			}
			archiveViewHelper.setPrpLDocArchiveLogDtoToView(httpServletRequest, pageNo, recordPerPage);
		} else if ("auditFinish".equals(editType)) {
			archiveViewHelper.setPrpLDocArchiveLogDtoToView(httpServletRequest);
		} else if ("extension".equals(editType)) {
			PrpLDocArchive prpLDocArchive = archiveViewHelper.extensionDtoToView(httpServletRequest);
			if (prpLDocArchive == null) {
				String content = "輸入的賠案號不正確！";
				httpServletRequest.setAttribute("content", content);
				httpServletRequest.setAttribute("message", "faile");
				return "faile";
			}

			if ((prpLDocArchive.getApplyDeferno() == null ? 0 : prpLDocArchive.getApplyDeferno()) == 1) {
				String content = "該賠案已經申請過一次延期！";
				httpServletRequest.setAttribute("content", content);
				httpServletRequest.setAttribute("message", "faile");
				return "faile";
			}

			httpServletRequest.setAttribute("prpLDocArchiveDto", prpLDocArchive);
		} else if ("retrival".equals(editType)) {
			PrpLDocArchiveLog prpLDocArchiveLog = archiveViewHelper.retrivalDtoToView(httpServletRequest);
			if (prpLDocArchiveLog == null) {
				String content = "輸入的賠案號不正確！";
				httpServletRequest.setAttribute("content", content);
				httpServletRequest.setAttribute("message", "faile");
				return "faile";
			}

			PrpLDocArchive prpLDocArchive = prpLDocArchiveService.findPrpLDocArchive(httpServletRequest.getParameter("claimNo"));
			httpServletRequest.setAttribute("prpLDocArchiveDto", prpLDocArchive);
			httpServletRequest.setAttribute("prpLDocArchiveLogDto", prpLDocArchiveLog);
		} else if ("toarchive".equals(editType)) {
			PrpLDocArchiveLog prpLDocArchiveLog = archiveViewHelper.toarchiveDtoToView(httpServletRequest);
			if (prpLDocArchiveLog == null) {
				String content = "輸入的賠案號不正確！";
				httpServletRequest.setAttribute("content", content);
				httpServletRequest.setAttribute("message", "faile");
				return "faile";
			}

			PrpLDocArchive prpLDocArchiveDto = prpLDocArchiveService.findPrpLDocArchive(httpServletRequest.getParameter("claimNo"));
			httpServletRequest.setAttribute("prpLDocArchiveDto", prpLDocArchiveDto);
			httpServletRequest.setAttribute("prpLDocArchiveLogDto", prpLDocArchiveLog);
		} else if ("overtime".equals(editType)) {
			logger.debug("查询满足条件的 公告信息");
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			try {
				page = archiveViewHelper.overtimeDtoToView(httpServletRequest, pageNo, pageSize);
				this.writeJSONData(page, "claimNo", "policyNo", "insuredName", "applicantName", "startReviewDate", "estimateReturnDate");
			} catch (Exception e) {
				e.printStackTrace();
				this.writeJSONMsg(e.getMessage());
			}
			return NONE;
		}
		return editType;
	}

	public ArchiveViewHelper getArchiveViewHelper() {
		return archiveViewHelper;
	}

	public void setArchiveViewHelper(ArchiveViewHelper archiveViewHelper) {
		this.archiveViewHelper = archiveViewHelper;
	}

	public PrpLDocArchiveService getPrpLDocArchiveService() {
		return prpLDocArchiveService;
	}

	public void setPrpLDocArchiveService(PrpLDocArchiveService prpLDocArchiveService) {
		this.prpLDocArchiveService = prpLDocArchiveService;
	}

}
