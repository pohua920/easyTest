/*
 * @(#)CertifyEditPostAction.java	Jan 24, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.web;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certify.service.facade.CertifyService;
import com.sinosoft.claim.certify.util.CertifyPayeeViewHelper;
import com.sinosoft.claim.certify.util.DAACertifyViewHelper;
import com.sinosoft.claim.certify.vo.CertifyDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLcertifyPayee;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CertifyEditPostAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 单证viewHelper */
	private DAACertifyViewHelper daaCertifyViewHelper;
	/** 单证费用viewHelper */
	private CertifyPayeeViewHelper certifyPayeeViewHelper;
	/** 立案信息表接口service */
	private PrpLclaimService prpLclaimService;
	/** 单证service */
	private CertifyService certifyService;
	/** 备案号码 */
	private String registNo = "";
	/** swfLog联合主键 */
	private String swfLogFlowID = "";
	private String swfLogLogNo = "";
	/** 保单号码 */
	private String policyNo = "";
	/** 险种 */
	private String riskCode = "";
	/** 节点类型 */
	private String nodeType = "";
	/** 代码翻译service */
	private CodeService codeService;
	/** 险种险类代码对照表接口service */
	private UtiCodeTransferService utiCodeTransferService;
	/** 工作流整理viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流service */
	private WorkFlowService workFlowService;
	private PrpLregistService prpLregistService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private BusinessViewHelper businessViewHelper;

	/**
	 * 单证暂存、提交
	 * @return
	 * @throws Exception
	 */
	public String certifyEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String imageTypeListSize = request.getParameter("imageTypeListSize");
		// 暂时处理，相应险别在主险表中无对应数据时，查勘中的索赔清单不让保存。
		int listSize = Integer.parseInt(DataUtils.nullToZero(imageTypeListSize));
		if (listSize == 0) {
			//此單證未查詢到!
			throw new UserException(1, 3, this.getText("title.certifyBeforeEdit.editCertify"), getText("prompt.certify.certifyNotFound"));
		}
		String riskCode = request.getParameter("riskCode");
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		// 用viewHelper整理界面输入
		CertifyDto certifyDto = this.daaCertifyViewHelper.viewToDto(request);
		PrpLclaimStatus prpLclaimStatus = certifyDto.getPrpLclaimStatus();
		String buttonSaveType = request.getParameter("buttonSaveType"); // 提交类型
		if ("4".equals(buttonSaveType)) {
			String condition = " registNo = '" + prpLclaimStatus.getId().getBusinessNo() + "' and nodeStatus < 4 ";
			condition += " and (flowStatus = '1' or flowStatus = '2') ";
			condition += " and (nodeType = 'check' or nodeType = 'claim' ) ";
			List<SwfLog> list = this.workFlowService.findByConditions(condition);
			if (list != null && !list.isEmpty()) {
				if("E".equals(strRiskType)){
					throw new UserException(1, 3, "單證", "本案還有未處理完畢的立案、或調查任務，請處理后再提交！");
				}else{
					throw new UserException(1, 3, "單證", "本案還有未處理完畢的立案、或查勘任務，請處理后再提交！");
				}
			}
		}
		String claimNo = codeService.translateBusinessCode(prpLclaimStatus.getId().getBusinessNo(), true);
		// 在数据整理那里，把swfLogFlowID,和swfLogLogNo重新写到request里面了。。
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		WorkFlowDto workFlowDto = null;
		String actorId = request.getParameter("swfLogActorId");
		String keyOut = prpLclaimStatus.getId().getBusinessNo();
		if(WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId))!=null){
			workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, false, null, null, claimNo, keyOut, claimNo, null);
		}else{
			//workFlowDto = this.getWorkFlowDto(certifyDto,prpLclaimStatus);
	          workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, false, null, null, claimNo, keyOut, claimNo, null);

		}
		// 如果是提交的单证 到 理算这个环节，则需要判断是不是调度已经做完了，没做完则也把调度置成完成的状态
		if ("D".equals(strRiskType) && "4".equals(buttonSaveType)) {
			List<SwfLog> noSubmitNodesList = this.getWorkFlowService().findNodesByConditions(" flowID='" + swfLogFlowID + "' and nodeType='sched' and nodestatus<4");
			SwfLog wfLogDtoTemp = null;
			if (noSubmitNodesList != null && noSubmitNodesList.size() > 0) {
				// 完成调度任务
				wfLogDtoTemp = noSubmitNodesList.iterator().next();
				wfLogDtoTemp.setNodeStatus("4");
				wfLogDtoTemp.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				workFlowDto.setUpdateSwfLog2(wfLogDtoTemp);
			}
		}
		// 增加领款人信息
		if ("2".equals(buttonSaveType) || "4".equals(buttonSaveType)) {
			if ("E".equals(ConstantCodes.carClassMap.get(riskCode))) {
				List<PrpLcertifyPayee> prpLcertifyPayeeList = this.certifyPayeeViewHelper.viewTODtoList(request);
				certifyDto.setPrpLcertifyPayeeList(prpLcertifyPayeeList);
			}
		}
		// 保存单证信息
		if (workFlowViewHelper.checkDealDto(workFlowDto)) {
			this.certifyService.save(certifyDto, workFlowDto);
//			this.jbpmBusinessViewHelper.saveBusiness(this.certifyService,"save",workFlowDto,certifyDto);
		} else {
			this.certifyService.save(certifyDto);
		}
		// 单证成功数据上传平台
		this.clearErrorsAndMessages();
		this.addActionMessage(super.getText("prompt.certify.save"));
		this.addActionMessage(super.getText("db.prpLregist.registNo"));
		this.addActionMessage(prpLclaimStatus.getId().getBusinessNo());
		request.getSession().setAttribute("user", user);
		return SUCCESS;
	}

	/***
	 * 旧工作流引擎处理单证任务
	 * @param certifyDto 单证大对象
	 * @param prpLclaimStatus 节点状态信息
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(CertifyDto certifyDto, PrpLclaimStatus prpLclaimStatus) throws Exception {
		HttpServletRequest request = super.getRequest();
		String riskCode = request.getParameter("riskCode");
		String claimNo = codeService.translateBusinessCode(prpLclaimStatus.getId().getBusinessNo(), true);
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码????
		// 单证需要写的是registno,诶，所以要得到claimno作为下个节点的录入
		SwfLog swfLogDtoDealNode = new SwfLog();
		if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(swfLogFlowID)) != null && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(swfLogLogNo)) != null) {
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			try {
				swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
			} catch (NumberFormatException e) {
				swfLogDtoDealNode.getId().setLogNo(Integer.parseInt("0"));
			}
		} else {
			swfLogDtoDealNode.setNodeType("certi");
			swfLogDtoDealNode.setBusinessNo(prpLclaimStatus.getId().getBusinessNo());
		}
		// 意键险要求不需要立案就可以进行单证收集，此时保存业务号为报案号！
		String registNo = certifyDto.getPrpLcertifyCollect().getId().getBusinessNo();
		String buttonSaveType = request.getParameter("buttonSaveType"); // 提交类型
		String riskType = codeService.translateRiskCodetoRiskType(riskCode);
		if (ConstantCodes.CLASSCODE_E.equals(riskType)) {
			if (buttonSaveType != null && !buttonSaveType.equals("")) {
				if (buttonSaveType.equals("0")) {
					swfLogDtoDealNode.setNodeStatus("0");
				} else if (buttonSaveType.equals("2")) {
					swfLogDtoDealNode.setNodeStatus("2");
				} else {
					swfLogDtoDealNode.setNodeStatus("4");
				}
			}
			swfLogDtoDealNode.setNextBusinessNo(registNo);
			swfLogDtoDealNode.setKeyIn(registNo);
			swfLogDtoDealNode.setKeyOut(registNo);
		} else {
			swfLogDtoDealNode.setNodeStatus(prpLclaimStatus.getStatus());
			swfLogDtoDealNode.setNextBusinessNo(claimNo);
			swfLogDtoDealNode.setKeyIn(claimNo);
			swfLogDtoDealNode.setKeyOut(prpLclaimStatus.getId().getBusinessNo());
		}
		// 考虑是车险的时候，由於强三原因，有可能会产生2个理算，所以需要考虑如何同时生成2个理算任务，並且不会由於多个提交到理算的问题，引起
		// 同类合並的问题。。。
		// 需要判断成车险的。。。不能用截串的方式。。。
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		if ("D".equals(strRiskType) && "4".equals(buttonSaveType)) {
			// 由於可能会出现立案被注销的情况，所以目前只提交出立案没有注销的情况下，显示理算任务。
			String strSql = " registNo='" + registNo + "' and canceldate is null";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strSql);
			List<PrpLclaim> claimList = this.prpLclaimService.findPrpLclaim(queryRule);
			if (claimList != null && claimList.size() > 0) {
				// 形成理算任务的列表，由於要分riskcode,
				List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
				for (PrpLclaim prpLclaim : claimList) {
					SwfLog swfLogNextNode = new SwfLog();
					swfLogNextNode.setNodeNo(0);
					swfLogNextNode.setNodeType("compe");
					swfLogNextNode.setRiskCode(prpLclaim.getRiskCode());
					swfLogNextNode.setPolicyNo(prpLclaim.getPolicyNo());
					swfLogNextNode.setKeyIn(prpLclaim.getClaimNo());
					swfLogNextNode.setBusinessNo(prpLclaim.getClaimNo());
					nextNodeList.add(swfLogNextNode);
				}
				swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
				swfLogDtoDealNode.setSwfLogList(nextNodeList);
			} else {
				//單證	該報案尚未立案，立案已註銷!
				throw new UserException(1, 3, getText("query.documents"), getText("prompt.certify.claimCanceled"));
			}
		}
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
	}
	/***
	 * 轉發單證存儲請求
	 * 避免被sessionToken攔截
	 * @return
	 * @throws Exception
	 */
	public String certifySavePost() throws Exception{
		return this.certifyEditPost();
	}

	public DAACertifyViewHelper getDaaCertifyViewHelper() {
		return daaCertifyViewHelper;
	}

	public void setDaaCertifyViewHelper(DAACertifyViewHelper daaCertifyViewHelper) {
		this.daaCertifyViewHelper = daaCertifyViewHelper;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public CertifyService getCertifyService() {
		return certifyService;
	}

	public void setCertifyService(CertifyService certifyService) {
		this.certifyService = certifyService;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getSwfLogFlowID() {
		return swfLogFlowID;
	}

	public void setSwfLogFlowID(String swfLogFlowID) {
		this.swfLogFlowID = swfLogFlowID;
	}

	public String getSwfLogLogNo() {
		return swfLogLogNo;
	}

	public void setSwfLogLogNo(String swfLogLogNo) {
		this.swfLogLogNo = swfLogLogNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public CertifyPayeeViewHelper getCertifyPayeeViewHelper() {
		return certifyPayeeViewHelper;
	}

	public void setCertifyPayeeViewHelper(CertifyPayeeViewHelper certifyPayeeViewHelper) {
		this.certifyPayeeViewHelper = certifyPayeeViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}
	
	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
