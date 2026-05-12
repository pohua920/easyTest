/*
 * @(#)SpeicalCaseApplyBeforeEditAction.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.specailCase.util.SpecialCaseViewHelper;
import com.sinosoft.claim.ui.control.action.UICodeAction;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class SpeicalCaseApplyBeforeEditAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	private SpecialCaseViewHelper specialCaseViewHelper;
	private String dfFlag = "";
	private String editType = "";
	private String nodeType = "";
	private String bussinessNo = "";

	/**
	 * 特殊赔案申请
	 * @return
	 * @throws Exception
	 */
	public String speicalCaseApplyBeforeEdit() throws Exception {
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest httpServletRequest = this.getRequest();
		String forward = ""; // 向前

		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 1.特殊申请,整理输入，用於初始界面显示
		if (editType.equals("CANCEL")) {// 借用cancel的申请了
			// modify by lixiang start at 2006-09-14
			// 由於增加了这个控制，所以一般商业险的预赔也是要做的，所以将这个控制去掉了。
			specialCaseViewHelper.buessinessNoDtoToView(httpServletRequest);
		}
		// 查询结案信息,整理输入，用於初始界面显示
		// 取得forward
		forward = "success";
		if (editType.equals("ApplySchedule")) {
			specialCaseViewHelper.applyScheduleDtoToViewDtoToView(httpServletRequest);
			forward = "ApplySchedule";
		}
		List<PrpLclaim> prpLclaimList = (List<PrpLclaim>) httpServletRequest.getAttribute("prpLclaimList");
		if (prpLclaimList != null && prpLclaimList.size() > 0) {
			String configCode = null;
			UICodeAction uiCodeAction = UICodeAction.getInstance();
			for (int i = 0; i < prpLclaimList.size(); i++) {
				PrpLclaim prpLclaim = prpLclaimList.get(i);
				configCode = uiCodeAction.translateRiskCodetoConfigCode(prpLclaim.getRiskCode());
				prpLclaim.setConfigCode(configCode);
			}
			httpServletRequest.setAttribute("prpLclaimList", prpLclaimList);
		}
		return forward;

	}

	public SpecialCaseViewHelper getSpecialCaseViewHelper() {
		return specialCaseViewHelper;
	}

	public void setSpecialCaseViewHelper(SpecialCaseViewHelper specialCaseViewHelper) {
		this.specialCaseViewHelper = specialCaseViewHelper;
	}

	public String getDfFlag() {
		return dfFlag;
	}

	public void setDfFlag(String dfFlag) {
		this.dfFlag = dfFlag;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}
}
