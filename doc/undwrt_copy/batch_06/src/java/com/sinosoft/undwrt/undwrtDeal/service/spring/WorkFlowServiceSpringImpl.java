package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;

import java.sql.SQLException;

import com.sinosoft.platform.bl.facade.BLUtiOperateLogFacade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPhead;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPheadCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTmain;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.WfFlowMain;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfNodeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfFlowMainService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WorkFlowService;
import com.sinosoft.utiall.blsvr.BLPrpDcompany;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.utility.string.ChgDate;

/**
 * 工作流實現類.
 */
public class WorkFlowServiceSpringImpl implements WorkFlowService {

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性工作流主表接口. */
	private WfFlowMainService wfFlowMainService;

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/** 屬性工作流節點定義接口. */
	private SwfNodeService swfNodeService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/**
	 * 關閉工作流.
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void close(String flowID) throws SQLException, Exception {
		int intCount = 0;
		WfLog wfLogDto = new WfLog();
		WfFlowMain wfFlowMainDto = new WfFlowMain();
		String strSubmitTime = new DateTime().current().toString()
				.substring(0, 19);

		QueryRule queryRule = QueryRule.getInstance();
		try {
			wfLogService.updateNodeStatusByFlowID(flowID);

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			intCount = wfLogService.getCount(queryRule);

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addEqual("id.logNo", intCount);
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			wfLogDto.setSubmitTime(strSubmitTime);
			wfLogService.update(wfLogDto);

			WfLog wfLogCurrDto = new WfLog();
			WfLog wfLogNextDto = new WfLog();
			SwfNode wfNodeDto = new SwfNode();

			wfLogCurrDto = wfLogService.findByPrimaryKey(queryRule);
			wfLogNextDto = wfLogDto;

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.modelNo", wfLogNextDto.getModelNo());
			queryRule.addEqual("id.nodeNo", wfLogNextDto.getNodeNo());
			wfNodeDto = swfNodeService.findByPrimaryKey(queryRule);

			String strLogSystemCode = "";
			String strLogRiskCode = "";
			String strLogBusinessType = "";
			String strLogBusinessNo = "";
			int intLogLogNo = 0;
			String strLogIsJFeeFlag = "";
			String strLogIsAutoUnderWrite = "";
			String strLogIsILog = "";
			String strLogOperateType = "";
			String strLogOperateTime = "";
			String strLogComCode = "";
			String strLogMakeCom = "";
			String strLogOperatorCode = "";
			String strLogIP = "";
			// 审核通过
			if (wfLogCurrDto.getNodeNo() < wfLogNextDto.getNodeNo()
					&& wfNodeDto.getEndFlag().equals("1")) {
				if (wfLogCurrDto.getBusinessType().equals("T")
						|| wfLogCurrDto.getBusinessType().equals("P")
						|| wfLogCurrDto.getBusinessType().equals("E")) {
					strLogSystemCode = "undwrt";
					strLogRiskCode = wfLogCurrDto.getRiskCode();
					if (wfLogCurrDto.getBusinessType().equals("T")) {
						strLogBusinessType = "T";
						strLogOperateType = "undwrt.hebao.proposalpasstime";
						strLogIsJFeeFlag = policyService
								.getPrpTmainByProposalNo(
										wfLogCurrDto.getBusinessNo())
								.getJfeeFlag();
						if (null == strLogIsJFeeFlag
								|| strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
						strLogIsAutoUnderWrite = policyService
								.getPrpTmainByProposalNo(
										wfLogCurrDto.getBusinessNo())
								.getUnderWriteFlag();
						if (strLogIsAutoUnderWrite.equals("3")
								|| strLogIsAutoUnderWrite.equals("6")) {
							strLogIsAutoUnderWrite = "1";
						} else {
							strLogIsAutoUnderWrite = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("P")) {
						strLogBusinessType = "P";
						strLogOperateType = "undwrt.hebao.policypasstime";
						if ("9999".equals(strLogRiskCode)
								|| "9998".equals(strLogRiskCode)
								|| "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = policyService
									.getPrpCmainByPolicyNo(
											wfLogCurrDto.getBusinessNo())
									.getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag
								|| strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
						if ("9999".equals(strLogRiskCode)
								|| "9998".equals(strLogRiskCode)
								|| "9997".equals(strLogRiskCode)) {
							/*
							 * strLogIsAutoUnderWrite = new DBPrpCmainCovernote(
							 * dbManager).findByPrimaryKey(
							 * wfLogCurrDto.getBusinessNo())
							 * .getUnderWriteFlag();
							 */
						} else {
							strLogIsAutoUnderWrite = policyService
									.getPrpCmainByPolicyNo(
											wfLogCurrDto.getBusinessNo())
									.getUnderWriteFlag();
						}
						if (strLogIsAutoUnderWrite.equals("3")
								|| strLogIsAutoUnderWrite.equals("6")) {
							strLogIsAutoUnderWrite = "1";
						} else {
							strLogIsAutoUnderWrite = "0";
						}
					} else if (wfLogCurrDto.getBusinessType().equals("E")) {
						strLogBusinessType = "E";
						strLogOperateType = "undwrt.hebao.endorsepasstime";
						if ("9999".equals(strLogRiskCode)
								|| "9998".equals(strLogRiskCode)
								|| "9997".equals(strLogRiskCode)) {
							strLogIsJFeeFlag = "0";
						} else {
							strLogIsJFeeFlag = endorseService
									.getPrpPheadByEndorseNo(
											wfLogCurrDto.getBusinessNo())
									.getJfeeFlag();
						}
						if (null == strLogIsJFeeFlag
								|| strLogIsJFeeFlag.equals("")) {
							strLogIsJFeeFlag = "0";
						}
						if ("9999".equals(strLogRiskCode)
								|| "9998".equals(strLogRiskCode)
								|| "9997".equals(strLogRiskCode)) {
							/*
							 * strLogIsAutoUnderWrite = new DBPrpPheadCovernote(
							 * dbManager).findByPrimaryKey(
							 * wfLogCurrDto.getBusinessNo())
							 * .getUnderWriteFlag();
							 */
						} else {
							strLogIsAutoUnderWrite = endorseService
									.getPrpPheadByEndorseNo(
											wfLogCurrDto.getBusinessNo())
									.getUnderWriteFlag();
						}
						if (strLogIsAutoUnderWrite.equals("3")
								|| strLogIsAutoUnderWrite.equals("6")) {
							strLogIsAutoUnderWrite = "1";
						} else {
							strLogIsAutoUnderWrite = "0";
						}
					}

					BLUtiOperateLogFacade blUtiOperateLogFacade = new BLUtiOperateLogFacade();
					ChgDate chgDate = new ChgDate();
					strLogBusinessNo = wfLogCurrDto.getBusinessNo();
					strLogOperateTime = chgDate
							.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					strLogComCode = wfLogCurrDto.getComCode();
					strLogMakeCom = wfLogCurrDto.getMakeCom();
					strLogOperatorCode = wfLogCurrDto.getOperatorCode();
					intLogLogNo = wfLogCurrDto.getId().getLogNo();
					if (isILog(strLogRiskCode, strLogComCode)) {
						strLogIsILog = "1";
					} else {
						strLogIsILog = "0";
					}
					// 审核通过后回写业务分级信息
					// 暂时不再使用此表数据20130716
					/*
					 * wfGradeService.echoGrade(dbManager, wfLogCurrDto
					 * .getBusinessType(), wfLogCurrDto.getBusinessNo(),
					 * wfLogCurrDto.getClassCode(), wfLogCurrDto .getRiskCode(),
					 * wfLogCurrDto.getId() .getFlowId());
					 */
					blUtiOperateLogFacade.save(strLogSystemCode,
							strLogRiskCode, strLogBusinessType,
							strLogBusinessNo, intLogLogNo, strLogIsJFeeFlag,
							strLogIsAutoUnderWrite, strLogIsILog,
							strLogOperateType, strLogOperateTime,
							strLogComCode, strLogMakeCom, strLogOperatorCode,
							strLogIP);
				}
			}

			wfFlowMainDto = wfFlowMainService.findByPrimaryKey(flowID);
			if (wfFlowMainDto != null) {
				wfFlowMainDto.setFlowStatus("0");
				wfFlowMainDto.setCloseDate(new DateTime().current().toString()
						.substring(0, 19));
				wfFlowMainDto.setStoreFlag("1");// 设置转储标志，1/需要转储 2/已转储
				wfFlowMainService.update(wfFlowMainDto);
			}
		} catch (SQLException se) {
			throw se;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根據險種和機構判斷是否是否使用規則引擎.
	 * 
	 * @param iRiskCode
	 *            險種代碼
	 * @param iComCode
	 *            歸屬機構代碼
	 * @return true 使用規則引擎 false 不使用規則引擎
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public boolean isILog(String iRiskCode, String iComCode)
			throws UserException, Exception {
		boolean isILog = false;
		String strWhere = "";
		String strWhereCom = " 1=1 And validstatus = '1'  Start With Comcode = '"
				+ iComCode
				+ "' "
				+ " Connect By Prior Uppercomcode = Comcode And Uppercomcode <> Prior Comcode ";
		// 递归查询所有的上级机构
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		blPrpDcompany.query(strWhereCom);
		strWhereCom = "'" + iComCode + "'";
		for (int i = 0; i < blPrpDcompany.getSize(); i++) {
			strWhereCom += ",'" + blPrpDcompany.getArr(i).getComCode() + "' ";
		}

		strWhere = " funtype = 'ILog' And recordtype = 'ILog' And riskcode = '"
				+ iRiskCode + "' " + " And comcode in (" + strWhereCom + ")"
				+ " And validStatus = '1'";
		// 是否使用ILog配置在PrpDconfigCode表里面。
		BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		blPrpDconfigCode.query(strWhere);
		if (blPrpDconfigCode.getSize() > 0) {
			isILog = true;
		}
		return isILog;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	/**
	 * 獲取屬性工作流主表接口.
	 * 
	 * @return 屬性工作流主表接口的值
	 */
	public WfFlowMainService getWfFlowMainService() {
		return wfFlowMainService;
	}

	/**
	 * 設置屬性工作流主表接口.
	 * 
	 * @param wfFlowMainService
	 *            待設置的工作流主表接口的值
	 */
	public void setWfFlowMainService(WfFlowMainService wfFlowMainService) {
		this.wfFlowMainService = wfFlowMainService;
	}

	/**
	 * 獲取屬性定級信息接口.
	 * 
	 * @return 屬性定級信息接口的值
	 */
	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	/**
	 * 設置屬性定級信息接口.
	 * 
	 * @param wfGradeService
	 *            待設置的定級信息接口的值
	 */
	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}

	/**
	 * 獲取屬性工作流節點定義接口.
	 * 
	 * @return 屬性工作流節點定義接口的值
	 */
	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	/**
	 * 設置屬性工作流節點定義接口.
	 * 
	 * @param swfNodeService
	 *            待設置的工作流節點定義接口的值
	 */
	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

}