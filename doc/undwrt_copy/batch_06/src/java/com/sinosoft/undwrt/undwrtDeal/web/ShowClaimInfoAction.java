package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.web.view.AbstractForm;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;
import com.sinosoft.undwrt.undwrtDeal.vo.ClaimInfoVo;

public class ShowClaimInfoAction extends Struts2Action {

	private String actionType;

	private String businessNo;
	
	List<ClaimInfoVo> listClaimInfoVo = new ArrayList<ClaimInfoVo>();

	private WfLogHelperService wfLogHelperService;

	public String claimInfoQuery() throws Exception {
		HttpServletRequest request = this.getRequest();
		ParamUtils paramUtils = new ParamUtils(request);
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("pageSize", 10);
		
		List<ClaimInfoVo> list = wfLogHelperService.similarClaimsInfo(businessNo, pageNo, pageSize);
		
		int startIndex = rowsPerPage * (pageNo - 1);
		int endIndex = startIndex + rowsPerPage;
		for (int i = startIndex; i < endIndex; i++) {
			if (i >= list.size()) {
				break;
			}
			listClaimInfoVo.add(list.get(i));
		}
		
		// 系统分页导航使用
		PageRecord pageRecord = new PageRecord(list.size(), pageNo, 1, rowsPerPage, list);
		request.setAttribute("fm", new AbstractForm(pageRecord));
		return SUCCESS;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public WfLogHelperService getWfLogHelperService() {
		return wfLogHelperService;
	}

	public void setWfLogHelperService(WfLogHelperService wfLogHelperService) {
		this.wfLogHelperService = wfLogHelperService;
	}

	public List<ClaimInfoVo> getListClaimInfoVo() {
		return listClaimInfoVo;
	}

	public void setListClaimInfoVo(List<ClaimInfoVo> listClaimInfoVo) {
		this.listClaimInfoVo = listClaimInfoVo;
	}
}
