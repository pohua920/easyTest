/*
 * @(#)ReplevySaveAction.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.replevy.service.facade.ReplevyService;
import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class ReplevySaveAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/**追偿Service*/
	private ReplevyService replevyService;
	/**理算Service*/
	private CompensateService compensateService;
	/**重开赔案Service*/
	private PrpLrecaseService prpLrecaseService;
	/**追偿ViewHelper*/
	private ReplevyViewHelper replevyViewHelper;
	/**单号Service*/
	private BillService billService;
	/**  */
	private PrpLlossService prpLlossService;

	/**追偿的保存方法
	 * @return
	 * @throws Exception
	 */
	public String replevySave() throws Exception {

		String forward = "failure";
		HttpServletRequest httpServletRequest = this.getRequest();
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		String editType = httpServletRequest.getParameter("editType");
		// 设置防止重复提交
		this.clearErrorsAndMessages();
		String strLastAccessedTime = "" + httpServletRequest.getSession().getLastAccessedTime() / 1000;
		String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldRegistLastAccessedTime");
		if (!oldLastAccessedTime.trim().equals("")) {
			this.addActionMessage("追償處理");
			httpServletRequest.setAttribute("errorMessage", "請不要重複提交！");
			this.addActionMessage("請不要重複提交！");
			return forward;
		}
		// 取号
		String compensateNo = httpServletRequest.getParameter("prpLcompensateCompensateNo");
		CompensateDto compensateDto = new CompensateDto();
		String claimNo = httpServletRequest.getParameter("prpLreplevyClaimNo"); // 立案号
		try {
			WorkFlowDto workFlowDto = new WorkFlowDto();
			workFlowDto.setJbpmDto(new JbpmDto());
			if(DataUtils.emptyToNull(DataUtils.dbNullToEmpty(swfLogFlowID))!=null){
				SwfLog currSwfLog = new SwfLog(swfLogFlowID,Integer.parseInt(swfLogLogNo));
				workFlowDto.setCurrSwfLog(currSwfLog);
				workFlowDto.setSubmit(true);
			}
			if ("addQuery".equals(editType) || "editQuery".equals(editType) || "ADD".equals(editType) || "EDIT".equals(editType)) {
				boolean checkFlag = false;// 新追偿处理提交前是否需要判断存在未处理完毕的追偿任务
				// 追償登錄，生成登錄計算書，R + claimNo + 00 ，
				if ("addQuery".equals(editType) && DataUtils.emptyToNull(compensateNo) == null) {
					compensateNo = "R" + claimNo + "00";
				} else if ("ADD".equals(editType)) {//追償登錄
					int year = DateTime.current().getYear();
					String comCode = httpServletRequest.getParameter("ComCode");
					String tableName = "prplReplevy";
					compensateNo = this.billService.getNo(tableName, httpServletRequest.getParameter("prpLreplevyClaimNo"), comCode, year);
					checkFlag = true;
					workFlowDto.setCreate(true);
					workFlowDto.setSubmit(true);
					workFlowDto.setBessinessNo(compensateNo);
				} else {//駁回修改、追償登錄修改
					if (workFlowDto.getCurrSwfLog() == null) {// 駁回修改再次提交審批時，開啟審批工作流的情況
						workFlowDto.setCreate(true);
						workFlowDto.setSubmit(true);
						workFlowDto.setBessinessNo(compensateNo);
					}
				}
				// 处理页面提交信息
				compensateDto = replevyViewHelper.viewToDto(httpServletRequest, compensateNo);
				if(checkFlag){
					String conditions = " compensateno like 'R"+claimNo+"%' order by compensateno desc ";
		 			List<PrpLcompensate> list = this.compensateService.findByConditions(conditions);
					if(list.size() >=2 && !"1".equals(list.get(0).getUnderWriteFlag())){
						//包括登录的计算书，存在2张以上（含），且最后一张未审核通过，则不能再做登录处理
						throw new UserException(1, 3, "追償處理", "該賠案已有追償任務正處理進行中！");
					}
				}
				boolean editQueryFlow = false;//追償協商是否需要審核  true ： 需要審核
				if("editQuery".equals(editType)){//追償協商修改
					editQueryFlow = checkSubmit(compensateDto);
					workFlowDto.setCreate(editQueryFlow);
					workFlowDto.setSubmit(editQueryFlow);
					if(!editQueryFlow){//
						compensateDto.getPrpLcompensate().setUnderWriteFlag("0");
					}
				}
				replevyService.saveBpm(compensateDto,workFlowDto);
				if ("addQuery".equals(editType)) {
					this.addActionMessage("追償登錄訊息保存成功！");
					this.addActionMessage("立案號碼："+claimNo);
				} else if("editQuery".equals(editType)){
					if (editQueryFlow) {
						this.addActionMessage("追償協商訊息保存成功，且已提交審核！");
					} else {
						this.addActionMessage("追償協商訊息保存成功！");
					}
					this.addActionMessage("立案號碼："+claimNo);
				} else {
					this.addActionMessage("追償任務提交審核成功！");
					this.addActionMessage("追償計算書號：" + compensateNo);
				}
			} else if ("UNDWRT".equals(editType)) {
				if ("".equals(compensateNo) || compensateNo == null) {
					throw new Exception("程式異常，請聯系系統管理員！");
				} else {
					replevyService.saveUndwrtPass(compensateNo, workFlowDto);
				}
				this.addActionMessage("追償任務審批處理提交成功！");
				this.addActionMessage("業務號碼：" + compensateNo);
			} else if ("WITHDRAWAL".equals(editType)) {
				if ("".equals(compensateNo) || compensateNo == null) {
					throw new Exception("程式異常，請聯系系統管理員！");
				} else {
					replevyService.saveUndwrtBack(compensateNo, workFlowDto);
				}
				if(compensateNo.endsWith("00")){
					this.addActionMessage("登錄訊息修改審核駁回處理提交成功！");
					this.addActionMessage("業務號碼：" + compensateNo);
				}else{
					this.addActionMessage("追償任務審批駁回處理提交成功！");
					this.addActionMessage("業務號碼：" + compensateNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		forward = "success";
		httpServletRequest.getSession().setAttribute("oldRegistLastAccessedTime", strLastAccessedTime);
		return forward;
	}

	/***
	 * 判斷追償協商是否需要走審核流程。
	 * 規則：若沒有增加、減少或修改法務預估金額訊息，則無需走協商審核
	 * @param compensateDto
	 * @return
	 * @throws Exception
	 */
	private boolean checkSubmit(CompensateDto compensateDto) throws Exception{
		//mantis： CLM0100 ，處理人員：BK007 蘇哲，需求單編號：CLM0100.新核心-追償協商流程簡化(協商免確認
//		String compensateNo = compensateDto.getPrpLcompensate().getCompensateNo();
//		List<PrpLloss> prpLlossList = this.prpLlossService.findByConditions(" compensateNo = '" + compensateNo + "'");
//		Map<String , PrpLloss> mapPrpLloss =  new HashMap<String , PrpLloss>();
//		String key = null;
//		for (PrpLloss prpLloss : prpLlossList) {
//			key = prpLloss.getPolicyNo() + "_" + prpLloss.getItemKindNo() + "_" + prpLloss.getKindCode() ;
//			mapPrpLloss.put(key, prpLloss);
//		}
//		for(PrpLloss prpLloss : compensateDto.getPrpLlossList()){
//			key = prpLloss.getPolicyNo() + "_" + prpLloss.getItemKindNo() + "_" + prpLloss.getKindCode() ;
//			if(mapPrpLloss.containsKey(key)){//
//				PrpLloss origPrpLloss = mapPrpLloss.get(key);
//				mapPrpLloss.remove(key);
//				if(origPrpLloss.getSumLoss() - prpLloss.getSumLoss() != 0){//法務預估金額有修改
//					return true;
//				}
//			} else {//法務預估金額，有增加險別的預估
//				return true;
//			}
//		}
//		//沒有修改險別法務預估金額，也沒有新增險別預估
//		if(!mapPrpLloss.isEmpty()){// 判斷是否有刪除險別法務預估
//			return true;
//		}
		return false;//未修改法務預估訊息
	}
	
	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}

	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public ReplevyService getReplevyService() {
		return replevyService;
	}

	public void setReplevyService(ReplevyService replevyService) {
		this.replevyService = replevyService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

}
