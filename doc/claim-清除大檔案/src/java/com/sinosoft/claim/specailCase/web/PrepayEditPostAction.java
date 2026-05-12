/*
 * @(#)PrepayEditPostAction.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import java.util.HashMap;
import java.util.Map;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.vo.PrepayDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;
import com.sinosoft.claim.specailCase.util.DAAPrepayViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrepayEditPostAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	
	/** 预陪数据收集 */
	private DAAPrepayViewHelper daaPrepayViewHelper;
	/** 通赔服务*/
	private GeneralClaimService generalClaimService;
	/** 预陪服务 */
	private PrepayService prepayService;
	/** 重开赔案 */
	private PrpLrecaseService prpLrecaseService;
	/** 单号生成规则 */
	private BillService billService;
	/** 工作流数据收集 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 立案服务*/
	private PrpLclaimService prpLclaimService;

	/**
	 * 预配处理暂存、提交
	 * @return
	 * @throws Exception
	 */
	public String prepayEditPost() throws Exception {
		this.clearMessages();
		String forward = ""; // 向前流转
		HttpServletRequest httpServletRequest = this.getRequest();
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		String riskCodeTemp = httpServletRequest.getParameter("prpLprepayRiskCode");
		// String taskType=""; //表示是特殊赔案中的预赔的新增，还是修改，如果是新增，则会增加新的工作流信息，
		// 只要设置taskType="T"类型的就可以了。
		String riskCode = riskCodeTemp;
		String comCode = httpServletRequest.getParameter("prpLprepayComCode");
		int year = DateTime.current().getYear();
		String preCompensateNo = ""; // 预赔号
		// 险种
		if (true) {
			// 暂时这么修改，没有判断重复提交的
			// 如果是新登记，则从取号表中取预赔号码，如果是修改，则保持原来的preCompensateNo不变
			// 取预赔号
			preCompensateNo = httpServletRequest.getParameter("prpLprepayPreCompensateNo");
			if (preCompensateNo == null || preCompensateNo.length() < 1 || preCompensateNo.trim().equals("")) {
				String tableName = "prplprepay";
				String prpLprepayPolicyNo = httpServletRequest.getParameter("prpLprepayPolicyNo");
				String claimNoTemp = httpServletRequest.getParameter("prpLprepayClaimNo");
				//预陪号取立案的出险原因
				PrpLclaim prpLclaimTemp = prpLclaimService.findPrpLclaim(claimNoTemp);
				Map<String,Object> infoMap = new HashMap<String,Object>();
				infoMap.put("damageCode",prpLclaimTemp.getDamageCode());
				infoMap.put("policyNo",prpLprepayPolicyNo);
				preCompensateNo = billService.getNoByPolciyYear(tableName, riskCode,infoMap);
			}
			httpServletRequest.setAttribute("preCompensateNo", preCompensateNo);
			httpServletRequest.setAttribute("prpLprepayPreCompensateNo", preCompensateNo);
			// 用viewHelper整理界面输入
			PrepayDto prepayDto = null;
			prepayDto = daaPrepayViewHelper.viewToDto(httpServletRequest);

			// 如果提交核赔则状态为9
			if (httpServletRequest.getParameter("buttonSaveType").equals("4")) {
				prepayDto.getPrpLprepay().setUnderWriteFlag("9");
			}
			// 异常测试
			// 工作流处理过程(属於特殊)
			// -----------------------------------------------------
			// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
			SwfLog swfLogDtoDealNode = new SwfLog();
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));

			// 如果下一个节点是结案的话，那么直接是赔案号就可以了
			swfLogDtoDealNode.setNextBusinessNo(preCompensateNo);
			swfLogDtoDealNode.setKeyIn(prepayDto.getPrpLprepay().getClaimNo());
			swfLogDtoDealNode.setKeyOut(preCompensateNo);
			swfLogDtoDealNode.setNodeStatus(httpServletRequest.getParameter("buttonSaveType"));

			WorkFlowDto workFlowDto = this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
			// ------------------------------------------------------------
			// 保存预赔信息

			if (workFlowDto.getCheckClose()) {
				String msg = "該工作流程已經關閉，" + "\n\r如果需要操作，請聯繫管理員重新開啟工作流!";
				throw new UserException(1, 2, "工作流", msg);
			}

			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				// 增加对核保核赔系统的接口调用。
				boolean isSubmitUndwrt = false;
				if (swfLogDtoDealNode.getNodeStatus().equals("4")) {
					isSubmitUndwrt = true;
//					// add by caozhigang 必须在承保地核赔 20090531 start
//					boolean generalFlag = generalClaimService.isGeneral(prepayDto.getPrpLprepay().getClaimNo(), user);
//					if (generalFlag) {
//						throw new UserException(-1,-1,"核賠權限","核賠必須在承保地進行！請將任務暫存後，由通賠崗轉回給承保地！");
//					}
				}
				// 因为在核赔流程中，会查询和修改prpLprepay表的信息，所有需要先提交hibernate的事务，保存到数据库中，
				// 在核赔中才能查询出来和修改表信息。liudaoping 2013-03-06
				// 後续可以修改了核赔的流程後，不用dbmanager查询後，在修改放到一个事物中
//				prepayService.save(prepayDto);
				if (workFlowDto.getSubmit()) {
					String businessNo = prpLrecaseService.findJbpmBusinessNo(prepayDto.getPrpLclaim().getClaimNo(), false);
					prepayService.saveBpm(businessNo, prepayDto, workFlowDto, user, preCompensateNo, isSubmitUndwrt);
				} else {
					prepayService.save(prepayDto, workFlowDto, user, preCompensateNo, isSubmitUndwrt);
				}
			} else {
				// 如果提交核赔失败则状态为0
				prepayDto.getPrpLprepay().setUnderWriteFlag("0");
				prepayService.save(prepayDto);
				this.addActionMessage(";注意:沒有發現與工作流流程相關任何數據！！");
			}

			httpServletRequest.setAttribute("prpLprepay", prepayDto.getPrpLprepay());
		} else {
			throw new UserException(1, 3, "0000", "請不要重複提交！");
		}
		this.addActionMessage(this.getText("db.prpLprepay.preCompensateNo"));
		this.addActionMessage(preCompensateNo);
		forward = "success";
		return forward;
	}

	public DAAPrepayViewHelper getDaaPrepayViewHelper() {
		return daaPrepayViewHelper;
	}

	public void setDaaPrepayViewHelper(DAAPrepayViewHelper daaPrepayViewHelper) {
		this.daaPrepayViewHelper = daaPrepayViewHelper;
	}

	public GeneralClaimService getGeneralClaimService() {
		return generalClaimService;
	}

	public void setGeneralClaimService(GeneralClaimService generalClaimService) {
		this.generalClaimService = generalClaimService;
	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

}
