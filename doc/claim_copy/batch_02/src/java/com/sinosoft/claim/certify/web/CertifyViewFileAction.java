/*
 * @(#)CertifyViewFileAction.java	Jan 27, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.web;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLcertifyImgService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 查看上传的单证信息
 */

public class CertifyViewFileAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private String isCase = "";
	private String editType = "";
	private String directType = "";
	private String display = "";
	private String businessNo = "";
	private String strLossItemName = "";
	private String nodeTypeUpload = "";
	private String itemcode = "";
	private int pageNo = 0;
	private PrpLcertifyImgService prpLcertifyImgService = null;
	private PrpLregistService prpLregistService = null;
	private Page page = null;
	private CodeService codeService;

	/**
	 * @return
	 * @throws Exception 查看单证的附件信息
	 */
	public String certifyViewFile() throws Exception {

		String strLossItemName = itemcode;

		// Modify by zhaolu 20060912 start
		QueryRule queryRule = QueryRule.getInstance();
		if (directType.equals("undefined")) {
			queryRule.addEqual("id.businessNo", businessNo);
			queryRule.addEqual("validStatus", "1");
			queryRule.addLike("typeCode", "99%");

		} else if (directType.length() <= 1) {
			queryRule.addEqual("id.businessNo", businessNo);
			queryRule.addEqual("validStatus", "1");
			queryRule.addLike("typeCode", "0" + directType + "%");
		} else {
			queryRule.addEqual("id.businessNo", businessNo);
			queryRule.addEqual("validStatus", "1");
			queryRule.addLike("typeCode", directType + "%");
		}
		// Modify by zhaolu 20060912 end
		// add by lixiang start at 2007-8-23
		// reasion:
		// 由於用户需要查看是按车辆进行过滤的，並且是照片顺序，必须等於上传的顺序，所以这里需要排序列,但是其他单证类型，需要特别处理一下
		// 2007-8-27
		if ("undefined".equals(strLossItemName)) {
			strLossItemName = editType;
		}
		queryRule.addEqual("id.lossItemCode", strLossItemName);
		queryRule.addAscOrder("id.serialNo");
		page = prpLcertifyImgService.findPrpLcertifyImg(queryRule, pageNo, pageSize);
		if (page != null && page.getResult() != null) {
			PrpLcertifyImg prpLcertifyImg = null;
			for (int i = 0; i < page.getResult().size(); i++) {
				prpLcertifyImg = (PrpLcertifyImg) page.getResult().get(i);
				String certifyUploadNodeName = codeService.translateCodeCode("ClaimNodeType", prpLcertifyImg.getUploadNodeFlag(), true);
				prpLcertifyImg.setUploadFileName(certifyUploadNodeName);
			}
		}
		String forward = "certifyViewFile";
		PrpLregist prpLregist = prpLregistService.findPrpLregist(businessNo);
		if (prpLregist != null && !"D".equals(ConstantCodes.carClassMap.get(prpLregist.getClassCode()))) {
			forward = "certify";
		}
		return forward;
	}

	public String getIsCase() {
		return isCase;
	}

	public void setIsCase(String isCase) {
		this.isCase = isCase;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getDirectType() {
		return directType;
	}

	public void setDirectType(String directType) {
		this.directType = directType;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		if (businessNo != null) {
			this.businessNo = businessNo.trim();
		} else {
			this.businessNo = "";
		}
	}

	public String getStrLossItemName() {
		return strLossItemName;
	}

	public void setStrLossItemName(String strLossItemName) {
		this.strLossItemName = strLossItemName;
	}

	public String getNodeTypeUpload() {
		return nodeTypeUpload;
	}

	public void setNodeTypeUpload(String nodeTypeUpload) {
		this.nodeTypeUpload = nodeTypeUpload;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public PrpLcertifyImgService getPrpLcertifyImgService() {
		return prpLcertifyImgService;
	}

	public void setPrpLcertifyImgService(PrpLcertifyImgService prpLcertifyImgService) {
		this.prpLcertifyImgService = prpLcertifyImgService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
