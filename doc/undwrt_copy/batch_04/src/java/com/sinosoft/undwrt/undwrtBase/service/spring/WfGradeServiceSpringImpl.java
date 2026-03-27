package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.platform.bl.facade.BLPrpDuserFacade;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.prpall.dto.domain.PrpCPgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpPheadDto;
import com.sinosoft.prpall.dto.domain.PrpTgradeDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPhead;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTmain;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.WfGrade;
import com.sinosoft.undwrt.undwrtBase.model.WfGradeId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.utility.database.DbPool;
import com.sinosoft.utility.string.ChgDate;

/**
 * 定級信息實現類.
 */
public class WfGradeServiceSpringImpl extends
		GenericDaoHibernate<WfGrade, WfGradeId> implements WfGradeService {

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/**
	 * 保存定級信息.
	 * 
	 * @param wfLogService
	 *            工作流日誌接口
	 * @param iFlowID
	 *            工作流號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iUserCode
	 *            員工代碼
	 * @param iOpertorCode
	 *            操作員代碼
	 * @param iGradeCode
	 *            業務級別代碼
	 * @param iGradeValue
	 *            業務級別分值
	 * @param iMaxUsableRate
	 *            最大可用費用率
	 * @param iBrokerRate
	 *            經紀人傭金率
	 * @param iAgentRate
	 *            代理手續費用率
	 * @param iOrgRate
	 *            營銷組織利益率
	 * @param iBreakevenRate
	 *            盈虧平衡點利率
	 * @param iExtRate1
	 *            交換率1
	 * @param iExtRate2
	 *            交換率2
	 * @param iExtRate3
	 *            交換率3
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#saveWfGrade(com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService,
	 *      java.lang.String, int, int, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveWfGrade(WfLogService wfLogService, String iFlowId,
			int iModelNo, int iNodeNo, String iBusinessType,
			String iBusinessNo, String iUserCode, String iOpertorCode,
			String iGradeCode, String iGradeValue, String iMaxUsableRate,
			String iBrokerRate, String iAgentRate, String iOrgRate,
			String iBreakevenRate, String iExtRate1, String iExtRate2,
			String iExtRate3) throws Exception {

		try {
			// 保存定级轨迹信息
			this.saveWfGrade2(wfLogService, iFlowId, iModelNo, iNodeNo,
					iBusinessType, iBusinessNo, iUserCode, iOpertorCode,
					iGradeCode, iGradeValue, iMaxUsableRate, iBrokerRate,
					iAgentRate, iOrgRate, iBreakevenRate, iExtRate1, iExtRate2,
					iExtRate3);
			// 核保核批通过的对定级信息的后续处理
			// blWfGradeAction.echoGrade(iDBManager,iBusinessType,iBusinessNo);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 保存定級信息.
	 * 
	 * @param wfLogService
	 *            工作流日誌接口
	 * @param iFlowID
	 *            工作流號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iUserCode
	 *            員工代碼
	 * @param iOpertorCode
	 *            操作員代碼
	 * @param iGradeCode
	 *            業務級別代碼
	 * @param iGradeValue
	 *            業務級別分值
	 * @param iMaxUsableRate
	 *            最大可用費用率
	 * @param iBrokerRate
	 *            經紀人傭金率
	 * @param iAgentRate
	 *            代理手續費用率
	 * @param iOrgRate
	 *            營銷組織利益率
	 * @param iBreakevenRate
	 *            盈虧平衡點利率
	 * @param iExtRate1
	 *            交換率1
	 * @param iExtRate2
	 *            交換率2
	 * @param iExtRate3
	 *            交換率3
	 * @throws Exception
	 *             異常
	 */
	private void saveWfGrade2(WfLogService wfLogService, String iFlowId,
			int iModelNo, int iNodeNo, String iBusinessType,
			String iBusinessNo, String iUserCode, String iOpertorCode,
			String iGradeCode, String iGradeValue, String iMaxUsableRate,
			String iBrokerRate, String iAgentRate, String iOrgRate,
			String iBreakevenRate, String iExtRate1, String iExtRate2,
			String iExtRate3) {

		WfLog wfLogPreDto = new WfLog();
		WfLog wfLogCurrDto = new WfLog();
		// WfLogDto wfLogNextDto = new
		// WfLogDto();//获取下一条Wflog数据(核保员处理后的下一条Wflog数据)
		// WfLogDto wfLogDto = new WfLogDto();
		// SWfNodeDto wfNodeDto = new SWfNodeDto();
		WfGrade wfGradeDto = null;// 定级信息
		BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
		PrpDuserDto prpDuserDto = null;

		ChgDate chgDate = new ChgDate();
		String strWherePart = "FlowID='" + iFlowId.trim() + "'";
		int intCount = 0;
		String strOpertorName = "";
		try {
			if (!iOpertorCode.equals("")) {
				prpDuserDto = blPrpDuserFacade.findByPrimaryKey(iOpertorCode);
				if (prpDuserDto != null) {
					strOpertorName = prpDuserDto.getUserName();
				}
			}

			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strWherePart);
			intCount = this.getCount(queryRule);

			// 获取当前Wflog数据(核保员处理时的当前Wflog数据)
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", iFlowId).addEqual("id.logNo",
					intCount - 1);
			wfLogCurrDto = wfLogService.findByPrimaryKey(queryRule);

			// 获取上一条Wflog数据(核保员处理时的上一条Wflog数据)
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", iFlowId).addEqual("id.logNo",
					intCount - 2);
			wfLogPreDto = wfLogService.findByPrimaryKey(queryRule);
			// wfLogNextDto = wfLogDto;
			// wfNodeDto = dbWfNode.findByPrimaryKey(wfLogNextDto.getModelNo(),
			// wfLogNextDto.getNodeNo());
			// 如果上一条Wflog的NodeNo为1则是从核心提交上来的业务，要记录手工和自动定级信息，否则只记录手工定级信息

			if (wfLogPreDto.getNodeNo() == 1) {
				// 自动定级

				wfGradeDto = getAutoGrade(iFlowId, wfLogCurrDto.getId()
						.getLogNo(), iModelNo, wfLogCurrDto.getNodeNo(),
						iOpertorCode, strOpertorName, iBusinessType,
						iBusinessNo);

				if (!wfGradeDto.getGradeCode().equals("")) {
					// insert(wfGradeDto);
				}
				// 手工定级
				wfGradeDto = getManualGrade(iFlowId, wfLogCurrDto.getId()
						.getLogNo(), iModelNo, wfLogCurrDto.getNodeNo(),
						iOpertorCode, strOpertorName, iBusinessType,
						iBusinessNo, iGradeCode, iGradeValue, iMaxUsableRate,
						iBrokerRate, iAgentRate, iOrgRate, iBreakevenRate,
						iExtRate1, iExtRate2, iExtRate3);
				// insert(wfGradeDto);
			} else {

				wfGradeDto = getManualGrade(iFlowId, wfLogCurrDto.getId()
						.getLogNo(), iModelNo, wfLogCurrDto.getNodeNo(),
						iOpertorCode, strOpertorName, iBusinessType,
						iBusinessNo, iGradeCode, iGradeValue, iMaxUsableRate,
						iBrokerRate, iAgentRate, iOrgRate, iBreakevenRate,
						iExtRate1, iExtRate2, iExtRate3);
				// insert(wfGradeDto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 獲取屬性定級代碼.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return 屬性定級代碼的值
	 * @throws Exception
	 *             異常
	 */
	@Override
	public String getPreGradeCode(String flowID) throws Exception {
		String strSql = "";
		String strPreGradeCode = "";// 前次分级

		WfGrade wfGradeDto = null;
		Iterator iterator = null;

		if (flowID != null) {

			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowID);
			queryRule.addNotEqual("id.gradeMode", "1");
			queryRule.addAscOrder("id.logNo");
			Collection colWfGrade = this.findListByQueryRule(queryRule);

			iterator = colWfGrade.iterator();
			while (iterator.hasNext()) {
				wfGradeDto = (WfGrade) iterator.next();
				strPreGradeCode = wfGradeDto.getGradeCode();
			}
		}

		return strPreGradeCode;
	}

	/**
	 * 根據條件查詢定級信息.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的定級信息集合
	 * @throws Exception
	 *             異常
	 */
	@Override
	public List<WfGrade> findByConditions(QueryRule queryRule) throws Exception {

		List<WfGrade> list = null;
		list = this.find(queryRule);

		return list;
	}

	/**
	 * 获取自动定级信息.
	 * 
	 * @param iFlowId
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iOperatorCode
	 *            操作員代碼
	 * @param iOperatorName
	 *            操作員名稱
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @return WfGradeDto 定級信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#getAutoGrade(java.lang.String,
	 *      int, int, int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public WfGrade getAutoGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取手工定级信息.
	 * 
	 * @param iFlowId
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iOperatorCode
	 *            操作員代碼
	 * @param iOperatorName
	 *            操作員名稱
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iGradeCode
	 *            業務級別代碼
	 * @param iGradeValue
	 *            業務級別分值
	 * @param iMaxUsableRate
	 *            最大可用費用率
	 * @param iBrokerRate
	 *            經紀人傭金率
	 * @param iAgentRate
	 *            代理手續費用率
	 * @param iOrgRate
	 *            營銷組織利益率
	 * @param iBreakevenRate
	 *            盈虧平衡點利率
	 * @param iExtRate1
	 *            交換率1
	 * @param iExtRate2
	 *            交換率2
	 * @param iExtRate3
	 *            交換率3
	 * @return WfGradeDto 定級信息類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#getManualGrade(java.lang.String,
	 *      int, int, int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public WfGrade getManualGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo, String iGradeCode,
			String iGradeValue, String iMaxUsableRate, String iBrokerRate,
			String iAgentRate, String iOrgRate, String iBreakevenRate,
			String iExtRate1, String iExtRate2, String iExtRate3)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 核保核批通過的對定級信息的後續處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iClassCode
	 *            險類代碼
	 * @param strRiskCode
	 *            險種
	 * @param iFlowId
	 *            工作流號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#echoGrade(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void echoGrade(String iBusinessType, String iBusinessNo,
			String iClassCode, String strRiskCode, String iFlowId)
			throws Exception {

		WfGrade wfGradeDto = null;
		// DBPrpTgrade dbPrpTgrade = new DBPrpTgrade(dbManager);
		// DBPrpCgrade dbPrpCgrade = new DBPrpCgrade(dbManager);
		// DBPrpCPgrade dbPrpCPgrade = new DBPrpCPgrade(dbManager);
		PrpCmainDto prpCmainDto = null;
		PrpPheadDto prpPheadDto = null;
		PrpTgradeDto prpTgradeDto = null;
		PrpCgradeDto prpCgradeDto = null;
		PrpCPgradeDto prpCPgradeDto = null;
		Collection col = null;
		Iterator iterator = null;
		String strSql = "";
		strSql = "";
		String strPolicyNo = "";
		boolean isUnderWrite = false;// 是否核保通过
		boolean isAutoUnderWrite = false;// 是否自动核保通过
		String strUnderWriteFlag = "";
		if (!iClassCode.equals("A")
				&& !iClassCode.equals("B")
				&& (!strRiskCode.equals("9997") && !strRiskCode.equals("9998") && !strRiskCode
						.equals("9999"))) {
			if (iBusinessType.equals("T")) {
				strSql = "ProposalNo='" + iBusinessNo + "'";
				strUnderWriteFlag = policyService.getPrpTmainByProposalNo(
						iBusinessNo).getUnderWriteFlag();
				if (strUnderWriteFlag.equals("1")
						|| strUnderWriteFlag.equals("5")) {
					isUnderWrite = true;
				} else if (strUnderWriteFlag.equals("3")
						|| strUnderWriteFlag.equals("6")) {
					isAutoUnderWrite = true;
				}
			} else if (iBusinessType.equals("P")) {
				strSql = "PolicyNo='" + iBusinessNo + "'";
				strUnderWriteFlag = policyService.getPrpCmainByPolicyNo(
						iBusinessNo).getUnderWriteFlag();
				if (strUnderWriteFlag.equals("1")
						|| strUnderWriteFlag.equals("5")) {
					isUnderWrite = true;
				} else if (strUnderWriteFlag.equals("3")
						|| strUnderWriteFlag.equals("6")) {
					isAutoUnderWrite = true;
				}
			} else if (iBusinessType.equals("E")) {
				strSql = "EndorseNo='" + iBusinessNo + "'";
				strUnderWriteFlag = endorseService.getPrpPheadByEndorseNo(
						iBusinessNo).getUnderWriteFlag();
				if (strUnderWriteFlag.equals("1")
						|| strUnderWriteFlag.equals("5")) {
					isUnderWrite = true;
				} else if (strUnderWriteFlag.equals("3")
						|| strUnderWriteFlag.equals("6")) {
					isAutoUnderWrite = true;
				}
			}
			if (isUnderWrite) {
				// 获取定级轨迹信息 begin
				QueryRule queryRule = QueryRule.getInstance();
				if (iBusinessType.equals("T")) {
					queryRule.addEqual("id.flowId", iFlowId)
							.addEqual("id.gradeMode", "0")
							.addAscOrder("id.logNo");
				} else if (iBusinessType.equals("P")) {
					queryRule.addEqual("id.flowId", iFlowId)
							.addEqual("id.gradeMode", "0")
							.addAscOrder("id.logNo");
				} else if (iBusinessType.equals("E")) {
					queryRule.addEqual("id.flowId", iFlowId)
							.addEqual("id.gradeMode", "0")
							.addAscOrder("id.logNo");

				}

				col = this.findByConditions(queryRule);
				iterator = col.iterator();
				while (iterator.hasNext()) {
					wfGradeDto = (WfGrade) iterator.next();
				}
				// 获取定级轨迹信息 end
				// 获取保单号 begin
				if (iBusinessType.equals("T")) {
					strSql = "ProposalNo='" + iBusinessNo + "'";
					col = null;
					iterator = null;
					col = (Collection) policyService
							.getPrpCmainByProposalNo(iBusinessNo);
					iterator = col.iterator();
					while (iterator.hasNext()) {
						prpCmainDto = (PrpCmainDto) iterator.next();
						strPolicyNo = prpCmainDto.getPolicyNo();
					}
				} else if (iBusinessType.equals("P")) {
					strPolicyNo = iBusinessNo;
				} else if (iBusinessType.equals("E")) {
					strSql = "EndorseNo='" + iBusinessNo + "'";
					col = null;
					iterator = null;
					col = (Collection) endorseService
							.getPrpPheadByEndorseNo(iBusinessNo);
					iterator = col.iterator();
					while (iterator.hasNext()) {
						prpPheadDto = (PrpPheadDto) iterator.next();
						strPolicyNo = prpPheadDto.getPolicyNo();
					}
				}
				// 获取保单号 end
				if (wfGradeDto != null) {
					if (iBusinessType.equals("T") || iBusinessType.equals("P")) {
						// prpTgradeDto =
						// dbPrpTgrade.findByPrimaryKey(iBusinessNo);
						// prpCgradeDto =
						// dbPrpCgrade.findByPrimaryKey(strPolicyNo);
						if (prpTgradeDto != null) {
							prpTgradeDto.setManualGradeCode(wfGradeDto
									.getGradeCode());
							prpTgradeDto.setManualGradeValue(wfGradeDto
									.getGradeValue());
							prpTgradeDto.setManualMaxUsableRate(wfGradeDto
									.getMaxUsableRate());
							prpTgradeDto.setManualOrgRate(wfGradeDto
									.getOrgRate());
							prpTgradeDto.setManualAgentRate(wfGradeDto
									.getAgentRate());
							prpTgradeDto.setManualBreakevenRate(wfGradeDto
									.getBreakevenRate());
							prpTgradeDto.setManualBrokerRate(wfGradeDto
									.getBrokerRate());
							prpTgradeDto.setManualExt1Rate(wfGradeDto
									.getExtRate1());
							prpTgradeDto.setManualExt2Rate(wfGradeDto
									.getExtRate2());
							prpTgradeDto.setManualExt3Rate(wfGradeDto
									.getExtRate3());
							// dbPrpTgrade.update(prpTgradeDto);
						}
						if (prpCgradeDto != null) {
							prpCgradeDto.setManualGradeCode(wfGradeDto
									.getGradeCode());
							prpCgradeDto.setManualGradeValue(wfGradeDto
									.getGradeValue());
							prpCgradeDto.setManualMaxUsableRate(wfGradeDto
									.getMaxUsableRate());
							prpCgradeDto.setManualOrgRate(wfGradeDto
									.getOrgRate());
							prpCgradeDto.setManualAgentRate(wfGradeDto
									.getAgentRate());
							prpCgradeDto.setManualBreakevenRate(wfGradeDto
									.getBreakevenRate());
							prpCgradeDto.setManualBrokerRate(wfGradeDto
									.getBrokerRate());
							prpCgradeDto.setManualExt1Rate(wfGradeDto
									.getExtRate1());
							prpCgradeDto.setManualExt2Rate(wfGradeDto
									.getExtRate2());
							prpCgradeDto.setManualExt3Rate(wfGradeDto
									.getExtRate3());
							// dbPrpCgrade.update(prpCgradeDto);
						}
					} else if (iBusinessType.equals("E")) {
						// prpCPgradeDto =
						// dbPrpCPgrade.findByPrimaryKey(strPolicyNo);
						if (prpCPgradeDto != null) {
							prpCPgradeDto.setManualGradeCode(wfGradeDto
									.getGradeCode());
							prpCPgradeDto.setManualGradeValue(wfGradeDto
									.getGradeValue());
							prpCPgradeDto.setManualMaxUsableRate(wfGradeDto
									.getMaxUsableRate());
							prpCPgradeDto.setManualOrgRate(wfGradeDto
									.getOrgRate());
							prpCPgradeDto.setManualAgentRate(wfGradeDto
									.getAgentRate());
							prpCPgradeDto.setManualBreakevenRate(wfGradeDto
									.getBreakevenRate());
							prpCPgradeDto.setManualBrokerRate(wfGradeDto
									.getBrokerRate());
							prpCPgradeDto.setManualExt1Rate(wfGradeDto
									.getExtRate1());
							prpCPgradeDto.setManualExt2Rate(wfGradeDto
									.getExtRate2());
							prpCPgradeDto.setManualExt3Rate(wfGradeDto
									.getExtRate3());
							// dbPrpCPgrade.update(prpCPgradeDto);
						}
						// prpCgradeDto =
						// dbPrpCgrade.findByPrimaryKey(strPolicyNo);
						if (prpCgradeDto != null) {
							prpCgradeDto.setManualGradeCode(wfGradeDto
									.getGradeCode());
							prpCgradeDto.setManualGradeValue(wfGradeDto
									.getGradeValue());
							prpCgradeDto.setManualMaxUsableRate(wfGradeDto
									.getMaxUsableRate());
							prpCgradeDto.setManualOrgRate(wfGradeDto
									.getOrgRate());
							prpCgradeDto.setManualAgentRate(wfGradeDto
									.getAgentRate());
							prpCgradeDto.setManualBreakevenRate(wfGradeDto
									.getBreakevenRate());
							prpCgradeDto.setManualBrokerRate(wfGradeDto
									.getBrokerRate());
							prpCgradeDto.setManualExt1Rate(wfGradeDto
									.getExtRate1());
							prpCgradeDto.setManualExt2Rate(wfGradeDto
									.getExtRate2());
							prpCgradeDto.setManualExt3Rate(wfGradeDto
									.getExtRate3());
							// dbPrpCgrade.update(prpCgradeDto);
						}
					}
				}
			}
			if (isAutoUnderWrite) {
				// 获取保单号 begin
				if (iBusinessType.equals("T")) {
					strSql = "ProposalNo='" + iBusinessNo + "'";
					col = null;
					iterator = null;
					col = (Collection) policyService
							.getPrpCmainByProposalNo(iBusinessNo);
					iterator = col.iterator();
					while (iterator.hasNext()) {
						prpCmainDto = (PrpCmainDto) iterator.next();
						strPolicyNo = prpCmainDto.getPolicyNo();
					}
				} else if (iBusinessType.equals("P")) {
					strPolicyNo = iBusinessNo;
				} else if (iBusinessType.equals("E")) {
					strSql = "EndorseNo='" + iBusinessNo + "'";
					col = null;
					iterator = null;
					col = (Collection) endorseService
							.getPrpPheadByEndorseNo(iBusinessNo);
					iterator = col.iterator();
					while (iterator.hasNext()) {
						prpPheadDto = (PrpPheadDto) iterator.next();
						strPolicyNo = prpPheadDto.getPolicyNo();
					}
				}
				// 获取保单号 end
				if (iBusinessType.equals("T") || iBusinessType.equals("P")) {
					// prpTgradeDto = dbPrpTgrade.findByPrimaryKey(iBusinessNo);
					// prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
					if (prpTgradeDto != null) {
						prpTgradeDto.setManualGradeCode(prpTgradeDto
								.getAutoGradeCode());
						prpTgradeDto.setManualGradeValue(prpTgradeDto
								.getAutoGradeValue());
						prpTgradeDto.setManualMaxUsableRate(prpTgradeDto
								.getAutoMaxUsableRate());
						prpTgradeDto.setManualOrgRate(prpTgradeDto
								.getAutoOrgRate());
						prpTgradeDto.setManualAgentRate(prpTgradeDto
								.getAutoAgentRate());
						prpTgradeDto.setManualBreakevenRate(prpTgradeDto
								.getAutoBreakevenRate());
						prpTgradeDto.setManualBrokerRate(prpTgradeDto
								.getAutoBrokerRate());
						prpTgradeDto.setManualExt1Rate(prpTgradeDto
								.getAutoExt1Rate());
						prpTgradeDto.setManualExt2Rate(prpTgradeDto
								.getAutoExt2Rate());
						prpTgradeDto.setManualExt3Rate(prpTgradeDto
								.getAutoExt3Rate());
						// dbPrpTgrade.update(prpTgradeDto);
					}
					if (prpCgradeDto != null) {
						prpCgradeDto.setManualGradeCode(prpCgradeDto
								.getAutoGradeCode());
						prpCgradeDto.setManualGradeValue(prpCgradeDto
								.getAutoGradeValue());
						prpCgradeDto.setManualMaxUsableRate(prpCgradeDto
								.getAutoMaxUsableRate());
						prpCgradeDto.setManualOrgRate(prpCgradeDto
								.getAutoOrgRate());
						prpCgradeDto.setManualAgentRate(prpCgradeDto
								.getAutoAgentRate());
						prpCgradeDto.setManualBreakevenRate(prpCgradeDto
								.getAutoBreakevenRate());
						prpCgradeDto.setManualBrokerRate(prpCgradeDto
								.getAutoBrokerRate());
						prpCgradeDto.setManualExt1Rate(prpCgradeDto
								.getAutoExt1Rate());
						prpCgradeDto.setManualExt2Rate(prpCgradeDto
								.getAutoExt2Rate());
						prpCgradeDto.setManualExt3Rate(prpCgradeDto
								.getAutoExt3Rate());
						// dbPrpCgrade.update(prpCgradeDto);
					}
				} else if (iBusinessType.equals("E")) {
					// prpCPgradeDto =
					// dbPrpCPgrade.findByPrimaryKey(strPolicyNo);
					if (prpCPgradeDto != null) {
						prpCPgradeDto.setManualGradeCode(prpCPgradeDto
								.getAutoGradeCode());
						prpCPgradeDto.setManualGradeValue(prpCPgradeDto
								.getAutoGradeValue());
						prpCPgradeDto.setManualMaxUsableRate(prpCPgradeDto
								.getAutoMaxUsableRate());
						prpCPgradeDto.setManualOrgRate(prpCPgradeDto
								.getAutoOrgRate());
						prpCPgradeDto.setManualAgentRate(prpCPgradeDto
								.getAutoAgentRate());
						prpCPgradeDto.setManualBreakevenRate(prpCPgradeDto
								.getAutoBreakevenRate());
						prpCPgradeDto.setManualBrokerRate(prpCPgradeDto
								.getAutoBrokerRate());
						prpCPgradeDto.setManualExt1Rate(prpCPgradeDto
								.getAutoExt1Rate());
						prpCPgradeDto.setManualExt2Rate(prpCPgradeDto
								.getAutoExt2Rate());
						prpCPgradeDto.setManualExt3Rate(prpCPgradeDto
								.getAutoExt3Rate());
						// dbPrpCPgrade.update(prpCPgradeDto);
					}
					// prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
					if (prpCgradeDto != null) {
						prpCgradeDto.setManualGradeCode(prpCgradeDto
								.getAutoGradeCode());
						prpCgradeDto.setManualGradeValue(prpCgradeDto
								.getAutoGradeValue());
						prpCgradeDto.setManualMaxUsableRate(prpCgradeDto
								.getAutoMaxUsableRate());
						prpCgradeDto.setManualOrgRate(prpCgradeDto
								.getAutoOrgRate());
						prpCgradeDto.setManualAgentRate(prpCgradeDto
								.getAutoAgentRate());
						prpCgradeDto.setManualBreakevenRate(prpCgradeDto
								.getAutoBreakevenRate());
						prpCgradeDto.setManualBrokerRate(prpCgradeDto
								.getAutoBrokerRate());
						prpCgradeDto.setManualExt1Rate(prpCgradeDto
								.getAutoExt1Rate());
						prpCgradeDto.setManualExt2Rate(prpCgradeDto
								.getAutoExt2Rate());
						prpCgradeDto.setManualExt3Rate(prpCgradeDto
								.getAutoExt3Rate());
						// dbPrpCgrade.update(prpCgradeDto);
					}
				}
			}
		}
	}

	/**
	 * 根據條件查找定級信息.
	 * 
	 * @param dbpool
	 *            數據管理對象
	 * @param conditions
	 *            條件
	 * @return 滿足查詢條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#findByConditions(com.sinosoft.utility.database.DbPool,
	 *      java.lang.String)
	 */
	@Override
	public Collection findByConditions(DbPool dbpool, String conditions)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查詢符合條件的記錄條數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄條數
	 * @throws Exception
	 *             異常
	 */
	@Override
	public int getCount(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule).size();
	}

	/**
	 * 保存定級信息.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            員工代碼
	 * @param strOperatorCode
	 *            操作員代碼
	 * @param strGradeCode
	 *            業務級別代碼
	 * @param strGradeValue
	 *            業務級別分值
	 * @param strMaxUsableRate
	 *            最大可用費用率
	 * @param strBrokerRate
	 *            經紀人傭金率
	 * @param strAgentRate
	 *            代理手續費用率
	 * @param strOrgRate
	 *            營銷組織利益率
	 * @param strBreakevenRate
	 *            盈虧平衡點利率
	 * @param strExtRate1
	 *            交換率1
	 * @param strExtRate2
	 *            交換率2
	 * @param strExtRate3
	 *            交換率3
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#saveWfGrade(java.lang.String,
	 *      int, int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void saveWfGrade(String flowID, int modelNo, int nodeNo,
			String certiType, String businessNo, String userCode,
			String strOperatorCode, String strGradeCode, String strGradeValue,
			String strMaxUsableRate, String strBrokerRate, String strAgentRate,
			String strOrgRate, String strBreakevenRate, String strExtRate1,
			String strExtRate2, String strExtRate3) {
		// TODO Auto-generated method stub

	}

	/**
	 * 根據條件查找定級信息.
	 * 
	 * @param queryRule
	 *            業務規則
	 * @return 滿足查詢條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService#findListByQueryRule(ins.framework.common.QueryRule)
	 */
	@Override
	public List findListByQueryRule(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
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
