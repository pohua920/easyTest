package com.sinosoft.claim.compensate.web;

import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfPathLogService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 車險理算撤銷簡易賠案處理
 * @author 中科軟
 */
public class CompensateCancelSimpleCaseAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 理算处理接口 */
	private CompensateService compensateService;
	/** 工作流处理接口 */
	private SwfLogService swfLogService;
	/** 備案处理接口 */
	private PrpLregistService prpLregistService;

	private PrpLclaimService prpLclaimService;

	private SwfPathLogService swfPathLogService;

	public String compensateCancelSimpleCase() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		String swfLogFlowID = request.getParameter("swfLogFlowID");
		String swfLogLogNo = request.getParameter("swfLogLogNo");
		if (DataUtils.dbNullToEmpty(swfLogFlowID).length() != 0 && DataUtils.dbNullToEmpty(swfLogLogNo).length() != 0) {
			String sql = " flowID = '" + swfLogFlowID + "' and startNodeNo = " + swfLogLogNo;
			List<SwfPathLog> tempList = this.swfPathLogService.findSwfPathLog(QueryRule.getInstance().addSql(sql));
			// 沒有查到本節點發起的後續任務，則表明可撤銷
			if (tempList != null && !tempList.isEmpty()) {
				throw new UserException(-1, 0, "簡易賠案", "該理算任務已存在後續任務，撤銷失敗！");
			}
		}
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		if (prpLclaim != null && "1".equals(prpLclaim.getSimpleFlag())) {
			PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
			if ("2".equals(prpLregist.getRegistType())) {// 關聯備案的撤銷，只要單證沒有提交則可
				String conditions = " flowID = '" + swfLogFlowID + "' and nodeType = 'certi' and nodeStatus = '4' ";
				List<SwfLog> tempList = this.swfLogService.findByConditions(conditions);
				if (tempList != null && !tempList.isEmpty()) {// 單證已處理完畢，不得撤銷
					throw new UserException(-1, 0, "簡易賠案", "單證任務已處理提交，撤銷失敗！");
				}
			}
		}
		SwfLog currSwfLog = new SwfLog(swfLogFlowID, Integer.parseInt(swfLogLogNo));
		try {
			this.compensateService.saveCancelSimpleCase(prpLclaim, currSwfLog);
			super.addActionMessage("簡易賠案撤銷成功！");
		} catch (UserException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, getText("prompt.compensate.back"), e.getMessage());// 理算退回
		}
		return SUCCESS;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public SwfPathLogService getSwfPathLogService() {
		return swfPathLogService;
	}

	public void setSwfPathLogService(SwfPathLogService swfPathLogService) {
		this.swfPathLogService = swfPathLogService;
	}

}
