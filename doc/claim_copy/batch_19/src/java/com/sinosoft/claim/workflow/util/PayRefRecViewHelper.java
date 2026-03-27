package com.sinosoft.claim.workflow.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.ApHeadService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.compensate.vo.PayRefRecDto;
import com.sinosoft.claim.schema.model.PrpJPayRefRecHis;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.service.facade.PrpJPayRefRecHisService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;

/**
 * <p>
 * Title: PayRefRecViewHelper
 * </p>
 * <p>
 * Description:赔款支付情况ViewHelper类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司(c) 2010
 * </p>
 * @author 中科软
 */
public class PayRefRecViewHelper {
	/** 代码服务 */
	private CodeService codeService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 收付财务中间表服务 */
	private ApHeadService apHeadService;
	/** 实收实付转储信息服务 */
	private PrpJPayRefRecHisService prpJPayRefRecHisService;

	/**
	 * 默认构造方法
	 */
	public PayRefRecViewHelper() {
	}

	/**
	 * 输出赔款支付情况到页面
	 * @param flowID 工作流ID
	 * @return void
	 * @throws Exception
	 */
	public void dtoToView(HttpServletRequest httpServletRequest, String flowID) throws Exception {
		SwfLog swfLog = this.workFlowService.findNodeByPrimaryKey(flowID, 1);
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		if (swfLog != null) {
			swfLogList = this.workFlowService.findNodesByFlowID(flowID);
		} else {
			List<SwfLogStore> swfLogStoreList = this.workFlowService.findStoreNodesByFlowID(flowID);
			if (swfLogStoreList != null && !swfLogStoreList.isEmpty()) {
				for (SwfLogStore swfLogStore : swfLogStoreList) {
					swfLogList.add(swfLogStore.toSwfLog());
				}
			}
		}
		List<PayRefRecDto> payRefRecList = new ArrayList<PayRefRecDto>();
		for (SwfLog tempSwfLog : swfLogList) {
			if ("veric".equals(tempSwfLog.getNodeType()) && "4".equals(tempSwfLog.getNodeStatus())) {
				String compensateNo = tempSwfLog.getKeyOut();
				String conditions = " certino = '" + compensateNo + "' and realpayrefflag = '1' and certitype in ('C','Y')";
				List<PrpJPayRefRecHis> prpJpayRefRecHisList = this.prpJPayRefRecHisService.findPrpJPayRefRecHis(conditions);
				if (prpJpayRefRecHisList == null || prpJpayRefRecHisList.isEmpty()) {
					PayRefRecDto payRefRecDto = new PayRefRecDto();
					payRefRecDto.setCompensateNo(compensateNo);
					payRefRecDto.setStatus("收付系統處理中");
					payRefRecList.add(payRefRecDto);
				} else {
					PayRefRecDto payRefRecDto = null;
					for (PrpJPayRefRecHis prpJpayRefRecHis : prpJpayRefRecHisList) {
						payRefRecDto = new PayRefRecDto();
						payRefRecDto.setCompensateNo(compensateNo);
						payRefRecDto.setStatus("支付成功");
						payRefRecDto.setPayRefReason(prpJpayRefRecHis.getId().getPayRefReason());
						payRefRecDto.setReasonName(this.codeService.translateCodeCode("PayRefReason", prpJpayRefRecHis.getId().getPayRefReason(), true));
						payRefRecDto.setPayDate(CommonUtils.getYearToDayStr(prpJpayRefRecHis.getPayRefDate()));
						payRefRecDto.setPayName(prpJpayRefRecHis.getOwnerName());
						payRefRecDto.setAmount(prpJpayRefRecHis.getPayRefFee());
						payRefRecList.add(payRefRecDto);
					}
				}
			}
		}
		httpServletRequest.setAttribute("payRefRecList", payRefRecList);
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public ApHeadService getApHeadService() {
		return apHeadService;
	}

	public void setApHeadService(ApHeadService apHeadService) {
		this.apHeadService = apHeadService;
	}

	public PrpJPayRefRecHisService getPrpJPayRefRecHisService() {
		return prpJPayRefRecHisService;
	}

	public void setPrpJPayRefRecHisService(PrpJPayRefRecHisService prpJPayRefRecHisService) {
		this.prpJPayRefRecHisService = prpJPayRefRecHisService;
	}

}
