package com.sinosoft.undwrt.undwrtDeal.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import com.sinosoft.common.schema.model.PrpCPmain;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.platform.bl.facade.BLPrpDcompanyFacade;
import com.sinosoft.platform.bl.facade.BLPrpDriskFacade;
import com.sinosoft.platform.dto.domain.PrpDcompanyDto;
import com.sinosoft.platform.dto.domain.PrpDriskDto;
import com.sinosoft.product.blsvr.misc.BLPrpDproduct;
import com.sinosoft.prpall.dto.domain.PrpCPgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCPmainDto;
import com.sinosoft.prpall.dto.domain.PrpCgradeDto;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpCproductDto;
import com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpPheadDto;
import com.sinosoft.prpall.dto.domain.PrpPmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpPmainDto;
import com.sinosoft.prpall.dto.domain.PrpTgradeDto;
import com.sinosoft.prpall.dto.domain.PrpTmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCPmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCproduct;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPhead;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPheadCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTgrade;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTmain;
import com.sinosoft.prpall.schema.PrpPheadCovernoteSchema;
import com.sinosoft.prpall.schema.PrpPheadSchema;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.service.facade.PrpCpMainService;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.model.PrpDExpenseBalance;
import com.sinosoft.undwrt.common.model.PrpDExpenseControl;
import com.sinosoft.undwrt.common.service.facade.PrpDExpenseBalanceService;
import com.sinosoft.undwrt.common.service.facade.PrpDExpenseControlService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.WfGrade;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfGradeService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ExpenseControlDealService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.UndwrtService;
import com.sinosoft.utiall.blsvr.BLPrpDconfigCode;
import com.sinosoft.utility.database.DbPool;
import com.sinosoft.utility.string.ChgDate;
import com.sinosoft.utility.string.Str;

/**
 * The Class ExpenseControlDealServiceSpringImpl.
 */
public class ExpenseControlDealServiceSpringImpl extends GenericDaoHibernate
		implements ExpenseControlDealService {

	/** 屬性費用聯動控制策略接口. */
	private PrpDExpenseControlService prpDExpenseControlService;

	/** 屬性費用聯動接口. */
	private PrpDExpenseBalanceService prpDExpenseBalanceService;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性定級信息接口. */
	private WfGradeService wfGradeService;

	/** 屬性核保系統接口. */
	private UndwrtService undwrtService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性要保書訊息接口. */
	private PrpCpMainService prpCpMainService;

	/**
	 * 費用聯動控制處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種代碼
	 * @param iProductCode
	 *            產品代碼
	 * @param wfGradeDto
	 *            頂級信息類
	 * @return 成功返回true,失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean dealExpenseControl(String iBusinessType, String iBusinessNo,
			String iComCode, String iRiskCode, String iProductCode,
			WfGrade wfGradeDto) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
		BLPrpDriskFacade blPrpDriskFacade = new BLPrpDriskFacade();
		BLPrpDproduct blPrpDproduct = new BLPrpDproduct();
		BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		DBPrpTgrade dbPrpTgrade = null;
		DBPrpCgrade dbPrpCgrade = null;
		DBPrpCPgrade dbPrpCPgrade = null;
		DBPrpPheadCovernote dbPrpPheadCovernote = null;
		PrpDcompanyDto prpDcompanyDto = null;
		PrpDriskDto prpDriskDto = null;
		PrpDExpenseBalance prpDExpenseBalanceDto = null;
		PrpDExpenseControl prpDExpenseControlDto = null;
		PrpTgradeDto prpTgradeDto = null;
		PrpCgradeDto prpCgradeDto = null;
		PrpCPgradeDto prpCPgradeDto = null;
		PrpPhead prpPhead = null;
		PrpPheadCovernoteDto prpPheadCovernoteDto = null;

		ChgDate nowDate = new ChgDate();

		double dblExpenseBalance = 0.0d;// 核定费用结余
		double dblCurrExpenseBalance = 0.0d;// 当前核定费用结余
		double dblRealFee = 0.0d;// 实际发生费用
		String strExpenseType = "";// 费用模式
		String strEndorType = "";// 批改类型
		boolean blnIsExpenseControl = false;// 是否费用控制
		boolean blnReturn = false;
		String strSQL = "";
		String strPolicyNo = "";
		int intSize = 0;
		int intExpenseControlLevel = 0;// 核保级别配置数据
		int intNodeNo = 0;// 当前核保节点
		boolean blAutoCheck = false;// 自动核保

		QueryRule queryRule = QueryRule.getInstance();

		blPrpDconfigCode.getFunNameOrFunType("0000000000", "0000",
				"ExpenseControlLevel", nowDate.getCurrentTime("yyyy-MM-dd"));
		if (blPrpDconfigCode.getSize() > 0) {
			intExpenseControlLevel = Integer.parseInt(blPrpDconfigCode
					.getArr(0).getFunName());
		}
		intNodeNo = this.getCurrNodeNo(iBusinessNo);

		blAutoCheck = this.getAutoCheck(iBusinessNo);

		if (wfGradeDto == null) {
			if (iBusinessType.equals("T")) {
				// 暂时不再使用此表20130802
				// dbPrpTgrade = new DBPrpTgrade(dbManager);
				prpTgradeDto = dbPrpTgrade.findByPrimaryKey(iBusinessNo);
				if (prpTgradeDto != null) {
					wfGradeDto = new WfGrade();
					wfGradeDto.setGradeCode(prpTgradeDto.getAutoGradeCode());
					wfGradeDto.setMaxUsableRate(prpTgradeDto
							.getAutoMaxUsableRate());
					wfGradeDto.setBrokerRate(prpTgradeDto.getAutoBrokerRate());
					wfGradeDto.setAgentRate(prpTgradeDto.getAutoAgentRate());
					wfGradeDto.setOrgRate(prpTgradeDto.getAutoOrgRate());
					wfGradeDto.setBreakevenRate(prpTgradeDto
							.getAutoBreakevenRate());
					wfGradeDto.setExtRate1(prpTgradeDto.getAutoExt1Rate());
					wfGradeDto.setExtRate2(prpTgradeDto.getAutoExt2Rate());
					wfGradeDto.setExtRate3(prpTgradeDto.getAutoExt3Rate());
				}
			} else if (iBusinessType.equals("P")) {
				// 暂时不再使用此表20130802
				// dbPrpCgrade = new DBPrpCgrade(dbManager);
				prpCgradeDto = dbPrpCgrade.findByPrimaryKey(iBusinessNo);
				if (prpCgradeDto != null) {
					wfGradeDto = new WfGrade();
					wfGradeDto.setGradeCode(prpCgradeDto.getAutoGradeCode());
					wfGradeDto.setMaxUsableRate(prpCgradeDto
							.getAutoMaxUsableRate());
					wfGradeDto.setBrokerRate(prpCgradeDto.getAutoBrokerRate());
					wfGradeDto.setAgentRate(prpCgradeDto.getAutoAgentRate());
					wfGradeDto.setOrgRate(prpCgradeDto.getAutoOrgRate());
					wfGradeDto.setBreakevenRate(prpCgradeDto
							.getAutoBreakevenRate());
					wfGradeDto.setExtRate1(prpCgradeDto.getAutoExt1Rate());
					wfGradeDto.setExtRate2(prpCgradeDto.getAutoExt2Rate());
					wfGradeDto.setExtRate3(prpCgradeDto.getAutoExt3Rate());
				}
			} else if (iBusinessType.equals("E")) {
				// 暂时不再使用此表20130802
				// dbPrpPheadCovernote = new DBPrpPheadCovernote(dbManager);
				// dbPrpCPgrade = new DBPrpCPgrade(dbManager);
				prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
				prpPheadCovernoteDto = dbPrpPheadCovernote
						.findByPrimaryKey(iBusinessNo);
				if (prpPhead != null) {
					strPolicyNo = prpPhead.getPolicyNo();
				}
				if (prpPheadCovernoteDto != null) {
					strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
				}
				prpCPgradeDto = dbPrpCPgrade.findByPrimaryKey(strPolicyNo);
				if (prpCPgradeDto != null) {
					wfGradeDto = new WfGrade();
					wfGradeDto.setGradeCode(prpCPgradeDto.getAutoGradeCode());
					wfGradeDto.setMaxUsableRate(prpCPgradeDto
							.getAutoMaxUsableRate());
					wfGradeDto.setBrokerRate(prpCPgradeDto.getAutoBrokerRate());
					wfGradeDto.setAgentRate(prpCPgradeDto.getAutoAgentRate());
					wfGradeDto.setOrgRate(prpCPgradeDto.getAutoOrgRate());
					wfGradeDto.setBreakevenRate(prpCPgradeDto
							.getAutoBreakevenRate());
					wfGradeDto.setExtRate1(prpCPgradeDto.getAutoExt1Rate());
					wfGradeDto.setExtRate2(prpCPgradeDto.getAutoExt2Rate());
					wfGradeDto.setExtRate3(prpCPgradeDto.getAutoExt3Rate());
				}
			}
		}

		queryRule.addEqual("businessNo", iBusinessNo);
		Collection<WfLog> colGradeGroupDetail = wfLogService
				.findByQueryRuleList(queryRule);
		// 获取'费用联动控制策略'数据
		prpDExpenseControlDto = prpDExpenseControlService
				.getExpenseControl(iComCode);
		if (prpDExpenseControlDto == null) {
			blnReturn = true;
			// throw new Exception("没有配置相应的'费用联动控制策略',请和系统管理员联系!");
		} else {
			prpDExpenseBalanceDto = prpDExpenseBalanceService
					.getPrpDExpenseBalance(prpDExpenseControlDto.getId()
							.getComCode(), iRiskCode, iProductCode);
			strExpenseType = prpDExpenseControlDto.getId().getExpenseType();
			if (prpDExpenseBalanceDto == null) {
				blnReturn = true;
				// throw new Exception("没有配置相应的'费用联动核定费用',请和系统管理员联系!");
			} else {

				// 机构名称赋值
				if (!prpDExpenseBalanceDto.getId().getComCode().equals("")) {
					prpDcompanyDto = blPrpDcompanyFacade
							.findByPrimaryKey(prpDExpenseBalanceDto.getId()
									.getComCode());
					if (prpDcompanyDto != null) {
						prpDExpenseBalanceDto.getId().setComName(
								prpDcompanyDto.getComCName());
					}
				}
				// 险种名称赋值
				if (!prpDExpenseBalanceDto.getId().getRiskCode().equals("")) {
					prpDriskDto = blPrpDriskFacade
							.findByPrimaryKey(prpDExpenseBalanceDto.getId()
									.getRiskCode());
					if (prpDriskDto != null) {
						prpDExpenseBalanceDto.getId().setRiskName(
								prpDriskDto.getRiskCName());
					}
				}
				// 产品名称赋值
				if (!prpDExpenseBalanceDto.getId().getRiskCode().equals("")
						&& !prpDExpenseBalanceDto.getId().getProductCode()
								.equals("")) {
					strSQL = " 1=1";
					strSQL += Str.convertString("RiskCode",
							prpDExpenseBalanceDto.getId().getRiskCode(), "=");
					strSQL += Str
							.convertString("ProductCode", prpDExpenseBalanceDto
									.getId().getProductCode(), "=");

					blPrpDproduct.query(strSQL);
					intSize = blPrpDproduct.getSize();
					if (intSize > 0) {
						prpDExpenseBalanceDto.getId().setProductName(
								blPrpDproduct.getArr(0).getProductCName());
					}
				}

				if (iBusinessType.equals("T")) {
					// 暂时不再使用此表20130802
					// dbPrpTgrade = new DBPrpTgrade(dbManager);
					prpTgradeDto = dbPrpTgrade.findByPrimaryKey(iBusinessNo);
					if (prpTgradeDto != null
							&& !wfGradeDto.getGradeCode().equals("R")) {
						if (colGradeGroupDetail != null) {
							if (colGradeGroupDetail.iterator().hasNext()) {
								blnIsExpenseControl = false;
							} else {
								blnIsExpenseControl = true;
							}
						} else {
							blnIsExpenseControl = true;
						}
					} else {
						blnIsExpenseControl = false;
					}
				} else if (iBusinessType.equals("P")) {
					// 暂时不再使用此表20130802
					// dbPrpCgrade = new DBPrpCgrade(dbManager);
					prpCgradeDto = dbPrpCgrade.findByPrimaryKey(iBusinessNo);
					if (prpCgradeDto != null
							&& !wfGradeDto.getGradeCode().equals("R")) {
						if (colGradeGroupDetail != null) {
							if (colGradeGroupDetail.iterator().hasNext()) {
								blnIsExpenseControl = false;
							} else {
								blnIsExpenseControl = true;
							}
						} else {
							blnIsExpenseControl = true;
						}
					} else {
						blnIsExpenseControl = false;
					}
				} else if (iBusinessType.equals("E")) {
					// 暂时不再使用此表20130802
					// dbPrpPheadCovernote = new DBPrpPheadCovernote(dbManager);
					// dbPrpCgrade = new DBPrpCgrade(dbManager);
					// dbPrpCPgrade = new DBPrpCPgrade(dbManager);
					prpPhead = endorseService
							.getPrpPheadByEndorseNo(iBusinessNo);
					prpPheadCovernoteDto = dbPrpPheadCovernote
							.findByPrimaryKey(iBusinessNo);

					if (prpPhead != null) {
						strPolicyNo = prpPhead.getPolicyNo();
					}
					if (prpPheadCovernoteDto != null) {
						strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
					}
					prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
					prpCPgradeDto = dbPrpCPgrade.findByPrimaryKey(strPolicyNo);

					strEndorType = this
							.getEndorType(iBusinessType, iBusinessNo);

					if (prpCgradeDto != null && prpCPgradeDto != null
							&& !wfGradeDto.getGradeCode().equals("R")) {
						if (colGradeGroupDetail != null) {
							if (colGradeGroupDetail.iterator().hasNext()) {
								blnIsExpenseControl = false;
							} else {
								blnIsExpenseControl = true;
							}
						} else {
							blnIsExpenseControl = true;
						}
					} else {
						blnIsExpenseControl = false;
					}
				}
				if (blnIsExpenseControl) {
					if (strEndorType.equals("19") || strEndorType.equals("21")) {// 退保和注销时不进行是否费用结余是否大于0的判定
						blnReturn = true;
					} else if (blAutoCheck) {// 自动核保业务不进行是否费用结余是否大于0的判定
						blnReturn = true;
					} else if (intNodeNo >= intExpenseControlLevel) {// 1C以上核保人员核保通过时不进行是否费用结余是否大于0的判定
						blnReturn = true;
					} else {
						dblExpenseBalance = this.getExpenseBalance(
								iBusinessType, iBusinessNo, strExpenseType,
								prpDExpenseBalanceDto, wfGradeDto);
						dblRealFee = this.getRealFee(iBusinessType,
								iBusinessNo, wfGradeDto);
						dblCurrExpenseBalance = this.getCurrExpenseBalance(
								strExpenseType, prpDExpenseBalanceDto);
						System.out.println("dblExpenseBalance==="
								+ dblExpenseBalance);
						System.out.println("dblCurrExpenseBalance==="
								+ dblCurrExpenseBalance);
						System.out.println("dblRealFee===" + dblRealFee);
						System.out
								.println("dblCurrExpenseBalance+dblRealFee==="
										+ (dblCurrExpenseBalance + dblRealFee));
						if (dblRealFee < 0) {
							if (dblCurrExpenseBalance + dblRealFee < 0) {
								blnReturn = false;
								throw new Exception(
										internal.getText("undwrt.service.expenseControl.institution")
												+ prpDExpenseBalanceDto.getId()
														.getComName()
												+ internal
														.getText("undwrt.service.expenseControl.insuranceType")
												+ prpDExpenseBalanceDto.getId()
														.getRiskName()
												+ internal
														.getText("undwrt.service.expenseControl.belong")
												+ internal
														.getText("undwrt.service.expenseControl.feeBalance")
												+ new DecimalFormat("#,##0.00")
														.format(dblCurrExpenseBalance)
												+ ","
												+ internal
														.getText("undwrt.service.expenseControl.actralFee")
												+ new DecimalFormat("#,##0.00")
														.format(dblRealFee)
												+ "."
												+ internal
														.getText("undwrt.service.expenseControl.cannotCheckPass"));
								// 下的当前核定费用结余是：***，当前业务的实际发生费用是（本位币）：***。合计费用结余小于零,不能核保通过!
							} else {
								blnReturn = true;
							}
						} else {
							blnReturn = true;
						}
					}
				} else {
					blnReturn = true;
				}
			}
		}
		return blnReturn;
	}

	/**
	 * 根據業務號從wflog表中提取當前系統中核保級別.
	 * 
	 * @param iBusinessNo
	 *            業務號
	 * @return 核保級別
	 * @throws Exception
	 *             異常
	 */
	public int getCurrNodeNo(String iBusinessNo) throws Exception {
		WfLog wfLogDto = null;
		Collection<WfLog> col = null;
		Iterator<WfLog> iterator = null;
		int intNodeNo = 0;
		String strFlowID = "";
		int intCount = 0;

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("businessNo", iBusinessNo);
		intCount = wfLogService.getCount(queryRule);

		col = wfLogService.findByQueryRuleList(queryRule);
		iterator = col.iterator();
		while (iterator.hasNext()) {
			wfLogDto = iterator.next();
			break;
		}
		if (wfLogDto != null) {
			strFlowID = wfLogDto.getId().getFlowId();
		}
		if (!strFlowID.equals("")) {
			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.flowId", strFlowID);
			queryRule.addEqual("id.logNo", intCount - 1);
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			intNodeNo = wfLogDto.getNodeNo();
		}

		return intNodeNo;
	}

	/**
	 * 根據業務號判斷是否是自動核保.
	 * 
	 * @param iBusinessNo
	 *            業務號
	 * @return false 自動核保，true 人工核保
	 * @throws Exception
	 *             異常
	 */
	public boolean getAutoCheck(String iBusinessNo) throws Exception {
		WfLog wfLogDto = null;
		Collection col = null;
		Iterator iterator = null;
		boolean blAutoCheck = false;// 自动核保
		String strFlowID = "";
		int intCount = 0;
		int intModelNo = 0;

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("businessNo", iBusinessNo);
		intCount = wfLogService.getCount(queryRule);

		col = wfLogService.findByQueryRuleList(queryRule);
		iterator = col.iterator();
		while (iterator.hasNext()) {
			wfLogDto = (WfLog) iterator.next();
			break;
		}
		if (wfLogDto != null) {
			strFlowID = wfLogDto.getId().getFlowId();
		}
		if (!strFlowID.equals("")) {
			queryRule.getQueryRuleList().clear();
			queryRule.getRuleList().clear();
			queryRule.addEqual("id.flowId", strFlowID);
			queryRule.addEqual("id.logNo", intCount - 1);
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			if (wfLogDto.getModelNo() == 99
					&& wfLogDto.getResultCode().equals("1")) {
				blAutoCheck = true;
			} else if (wfLogDto.getModelNo() == 24) {
				intModelNo = undwrtService.getModelNo("12",wfLogDto.getClassCode(),
						wfLogDto.getRiskCode(), wfLogDto.getComCode());
				if (intModelNo == wfLogDto.getModelNo()) {
					blAutoCheck = true;
				}
			}
		}

		return blAutoCheck;
	}

	/**
	 * 獲取批改類型.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @return 批改類型
	 * @throws Exception
	 *             異常
	 */
	public String getEndorType(String iBusinessType, String iBusinessNo)
			throws Exception {
		// 暂时不再使用此表20130802
		// DBPrpPheadCovernote dbPrpPheadCovernote = new
		// DBPrpPheadCovernote(dbManager);
		PrpPhead prpPhead = null;
		PrpPheadCovernoteDto prpPheadCovernoteDto = null;

		String strEndorType = "";

		if (iBusinessType.equals("E")) {
			prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
			// prpPheadCovernoteDto =
			// dbPrpPheadCovernote.findByPrimaryKey(iBusinessNo);

			// 普通业务处理
			if (prpPhead != null) {
				strEndorType = prpPhead.getEndorType();
			}
			// Covernote表数据处理
			if (prpPheadCovernoteDto != null) {
				strEndorType = prpPheadCovernoteDto.getEndorType();
			}
		}
		return strEndorType;
	}

	/**
	 * 獲取核定費用結余.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iExpenseType
	 *            費用模式
	 * @param prpDExpenseBalanceDto
	 *            費用聯動核定費用類
	 * @param wfGradeDto
	 *            定級訊息類
	 * @return 核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double getExpenseBalance(String iBusinessType, String iBusinessNo,
			String iExpenseType, PrpDExpenseBalance prpDExpenseBalanceDto,
			WfGrade wfGradeDto) throws Exception {
		DBManager dbManager = new DBManager();
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		DBPrpTmain dbPrpTmain = null;
		DBPrpCmain dbPrpCmain = null;
		DBPrpCPmain dbPrpCPmain = null;
		DBPrpPmain dbPrpPmain = null;
		DBPrpPhead dbPrpPhead = null;
		DBPrpCmainCovernote dbPrpCmainCovernote = null;
		DBPrpCPmainCovernote dbPrpCPmainCovernote = null;
		DBPrpPmainCovernote dbPrpPmainCovernote = null;
		DBPrpPheadCovernote dbPrpPheadCovernote = null;
		DBPrpCgrade dbPrpCgrade = null;

		PrpTmain prpTmain = null;
		PrpCmain prpCmain = null;
		PrpCPmain prpCPmain = null;
		PrpPmain prpPmain = null;
		PrpPhead prpPhead = null;
		PrpCmainCovernoteDto prpCmainCovernoteDto = null;
		PrpCPmainCovernoteDto prpCPmainCovernoteDto = null;
		PrpPmainCovernoteDto prpPmainCovernoteDto = null;
		PrpPheadCovernoteDto prpPheadCovernoteDto = null;
		PrpCgradeDto prpCgradeDto = null;

		double dblExpenseBalance = 0.0d;// 核定费用结余
		double dblSumPremium = 0.0d;// 保费
		double dblOriginSumPremium = 0.0d;// 原始保费

		double dblMaxUsableRate = 0.0d;// 最大可用费用率
		double dblBrokerRate = 0.0d;// 经纪人佣金率
		double dblAgentRate = 0.0d;// 代理手续费率
		double dblOrgRate = 0.0d;// 营销组织利益率
		double dblBreakevenRate = 0.0d;// 基准营销费用率
		double dblExt1Rate = 0.0d;// 扩展费率1
		double dblExt2Rate = 0.0d;// 扩展费率2
		double dblExt3Rate = 0.0d;// 扩展费率3

		double dblOriginMaxUsableRate = 0.0d;// 原始最大可用费用率
		double dblOriginBrokerRate = 0.0d;// 原始经纪人佣金率
		double dblOriginAgentRate = 0.0d;// 原始代理手续费率
		double dblOriginOrgRate = 0.0d;// 原始营销组织利益率
		double dblOriginBreakevenRate = 0.0d;// 原始基准营销费用率
		double dblOriginExt1Rate = 0.0d;// 原始扩展费率1
		double dblOriginExt2Rate = 0.0d;// 原始扩展费率2
		double dblOriginExt3Rate = 0.0d;// 原始扩展费率3

		String strPolicyNo = "";

		dblMaxUsableRate = wfGradeDto.getMaxUsableRate();
		// 计算实际发生费用时只需用到最大可用费用率.代理手续费率.经纪人佣金率,其他费用率不使用,暂时保留(默认值为0)

		if (iBusinessType.equals("T")) {

			prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
			dblSumPremium = prpTmain.getSumPremium().doubleValue()
					* prpTmain.getExchangeRate().doubleValue();
			dblAgentRate = prpTmain.getDisRate().doubleValue() / 100;
		} else if (iBusinessType.equals("P")) {
			dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);

			prpCmain = policyService.getPrpCmainByPolicyNo(iBusinessNo);
			prpCmainCovernoteDto = dbPrpCmainCovernote
					.findByPrimaryKey(iBusinessNo);

			if (prpCmain != null) {
				dblSumPremium = prpCmain.getSumPremium().doubleValue()
						* prpCmain.getExchangeRate().doubleValue();
				dblAgentRate = prpCmain.getDisRate().doubleValue() / 100;
			}
			if (prpCmainCovernoteDto != null) {
				dblSumPremium = prpCmainCovernoteDto.getSumPremium()
						* prpCmainCovernoteDto.getExchangeRate();
				dblAgentRate = prpCmainCovernoteDto.getDisRate() / 100;
			}
		} else {
			dbPrpCgrade = new DBPrpCgrade(dbManager);
			dbPrpCPmainCovernote = new DBPrpCPmainCovernote(dbManager);
			dbPrpPmainCovernote = new DBPrpPmainCovernote(dbManager);
			dbPrpPheadCovernote = new DBPrpPheadCovernote(dbManager);

			prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
			prpPheadCovernoteDto = dbPrpPheadCovernote
					.findByPrimaryKey(iBusinessNo);
			// 普通业务处理
			if (prpPhead != null) {
				strPolicyNo = prpPhead.getPolicyNo();
				prpCPmain = prpCpMainService
						.getPrpCpMainByPolicyNo(strPolicyNo);
				prpPmain = endorseService.getPrpPheadByEndorseNo(iBusinessNo)
						.getPrpPmains().get(0);
				prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
				dblSumPremium = prpCPmain.getSumPremium().doubleValue()
						* prpCPmain.getExchangeRate().doubleValue();
				dblAgentRate = prpCPmain.getDisRate().doubleValue() / 100;
				dblOriginSumPremium = prpPmain.getSumPremium().doubleValue()
						* prpPmain.getExchangeRate().doubleValue();
				dblOriginAgentRate = prpPmain.getDisRate().doubleValue() / 100;
			}
			// Covernote表数据处理
			if (prpPheadCovernoteDto != null) {
				strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
				prpCPmainCovernoteDto = dbPrpCPmainCovernote
						.findByPrimaryKey(strPolicyNo);
				prpPmainCovernoteDto = dbPrpPmainCovernote
						.findByPrimaryKey(iBusinessNo);
				prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
				dblSumPremium = prpCPmainCovernoteDto.getSumPremium()
						* prpCPmainCovernoteDto.getExchangeRate();
				dblAgentRate = prpCPmainCovernoteDto.getDisRate() / 100;
				dblOriginSumPremium = prpPmainCovernoteDto.getSumPremium()
						* prpPmainCovernoteDto.getExchangeRate();
				dblOriginAgentRate = prpPmainCovernoteDto.getDisRate() / 100;
			}

			// P表数据
			dblOriginMaxUsableRate = prpCgradeDto.getManualMaxUsableRate();
			// 计算实际发生费用时只需用到最大可用费用率.代理手续费率.经纪人佣金率,其他费用率不使用,暂时保留(默认值为0)
		}

		dblExpenseBalance = this.calculateExpenseBalance(prpDExpenseBalanceDto,
				iExpenseType, dblSumPremium, dblOriginSumPremium,
				dblMaxUsableRate, dblBrokerRate, dblAgentRate, dblOrgRate,
				dblBreakevenRate, dblExt1Rate, dblExt2Rate, dblExt3Rate,
				dblOriginMaxUsableRate, dblOriginBrokerRate,
				dblOriginAgentRate, dblOriginOrgRate, dblOriginBreakevenRate,
				dblOriginExt1Rate, dblOriginExt2Rate, dblOriginExt3Rate);
		dbManager.close();
		return dblExpenseBalance;
	}

	/**
	 * 計算核定費用結余.
	 * 
	 * @param prpDExpenseBalanceDto
	 *            費用聯動核定費用類
	 * @param iExpenseType
	 *            費用模式
	 * @param dblSumPremium
	 *            保費
	 * @param dblOriginSumPremium
	 *            原始保費
	 * @param dblMaxUsableRate
	 *            最大可用費用率
	 * @param dblBrokerRate
	 *            經紀人傭金率
	 * @param dblAgentRate
	 *            代理手續費率
	 * @param dblOrgRate
	 *            營銷組織利益率
	 * @param dblBreakevenRate
	 *            營銷組織利益率
	 * @param dblExt1Rate
	 *            擴展費率1
	 * @param dblExt2Rate
	 *            擴展費率2
	 * @param dblExt3Rate
	 *            擴展費率3
	 * @param dblOriginMaxUsableRate
	 *            原始最大可用費用率
	 * @param dblOriginBrokerRate
	 *            原始經紀人傭金率
	 * @param dblOriginAgentRate
	 *            原始代理手續費率
	 * @param dblOriginOrgRate
	 *            原始營銷組織利益率
	 * @param dblOriginBreakevenRate
	 *            原始基准營銷費用率
	 * @param dblOriginExt1Rate
	 *            原始擴展費率1
	 * @param dblOriginExt2Rate
	 *            原始擴展費率2
	 * @param dblOriginExt3Rate
	 *            原始擴展費率3
	 * @return 核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double calculateExpenseBalance(
			PrpDExpenseBalance prpDExpenseBalanceDto, String iExpenseType,
			double dblSumPremium, double dblOriginSumPremium,
			double dblMaxUsableRate, double dblBrokerRate, double dblAgentRate,
			double dblOrgRate, double dblBreakevenRate, double dblExt1Rate,
			double dblExt2Rate, double dblExt3Rate,
			double dblOriginMaxUsableRate, double dblOriginBrokerRate,
			double dblOriginAgentRate, double dblOriginOrgRate,
			double dblOriginBreakevenRate, double dblOriginExt1Rate,
			double dblOriginExt2Rate, double dblOriginExt3Rate)
			throws Exception {
		double dblExpenseBalance = 0.0d;

		if (iExpenseType.equals("01")) {// 计划费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getPlanFee()
					+ prpDExpenseBalanceDto.getId().getLoanFee()
					+ prpDExpenseBalanceDto.getId().getPlanFeeChg()
					+ (dblSumPremium
							* (dblMaxUsableRate - dblBrokerRate - dblAgentRate
									- dblOrgRate - dblBreakevenRate
									- dblExt1Rate - dblExt2Rate - dblExt3Rate) - dblOriginSumPremium
							* (dblOriginMaxUsableRate - dblOriginBrokerRate
									- dblOriginAgentRate - dblOriginOrgRate
									- dblOriginBreakevenRate
									- dblOriginExt1Rate - dblOriginExt2Rate - dblOriginExt3Rate));
		} else {// 实际费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getActualFeeChg()
					+ prpDExpenseBalanceDto.getId().getLoanFee()
					+ (dblSumPremium
							* (dblMaxUsableRate - dblBrokerRate - dblAgentRate
									- dblOrgRate - dblBreakevenRate
									- dblExt1Rate - dblExt2Rate - dblExt3Rate) - dblOriginSumPremium
							* (dblOriginMaxUsableRate - dblOriginBrokerRate
									- dblOriginAgentRate - dblOriginOrgRate
									- dblOriginBreakevenRate
									- dblOriginExt1Rate - dblOriginExt2Rate - dblOriginExt3Rate));
		}

		return dblExpenseBalance;
	}

	/**
	 * 獲取實際發生費用.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param wfGradeDto
	 *            定級信息
	 * @return 實際發生費用
	 * @throws Exception
	 *             異常
	 * @desc 获取实际发生费用
	 */
	public double getRealFee(String iBusinessType, String iBusinessNo,
			WfGrade wfGradeDto) throws Exception {

		DBManager dbManager = new DBManager();
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		DBPrpCgrade dbPrpCgrade = null;
		DBPrpCmainCovernote dbPrpCmainCovernote = null;
		DBPrpCPmainCovernote dbPrpCPmainCovernote = null;
		DBPrpPmainCovernote dbPrpPmainCovernote = null;
		DBPrpPheadCovernote dbPrpPheadCovernote = null;

		PrpTmain prpTmain = null;
		PrpCmain prpCmain = null;
		PrpCPmain prpCPmain = null;
		PrpPmain prpPmain = null;
		PrpPhead prpPhead = null;
		PrpCgradeDto prpCgradeDto = null;
		PrpCmainCovernoteDto prpCmainCovernoteDto = null;
		PrpCPmainCovernoteDto prpCPmainCovernoteDto = null;
		PrpPmainCovernoteDto prpPmainCovernoteDto = null;
		PrpPheadCovernoteDto prpPheadCovernoteDto = null;

		double dblRealFee = 0.0d;// 实际发生费用
		double dblSumPremium = 0.0d;// 保费
		double dblOriginSumPremium = 0.0d;// 原始保费

		double dblMaxUsableRate = 0.0d;// 最大可用费用率
		double dblBrokerRate = 0.0d;// 经纪人佣金率
		double dblAgentRate = 0.0d;// 代理手续费率
		double dblOrgRate = 0.0d;// 营销组织利益率
		double dblBreakevenRate = 0.0d;// 基准营销费用率
		double dblExt1Rate = 0.0d;// 扩展费率1
		double dblExt2Rate = 0.0d;// 扩展费率2
		double dblExt3Rate = 0.0d;// 扩展费率3

		double dblOriginMaxUsableRate = 0.0d;// 原始最大可用费用率
		double dblOriginBrokerRate = 0.0d;// 原始经纪人佣金率
		double dblOriginAgentRate = 0.0d;// 原始代理手续费率
		double dblOriginOrgRate = 0.0d;// 原始营销组织利益率
		double dblOriginBreakevenRate = 0.0d;// 原始基准营销费用率
		double dblOriginExt1Rate = 0.0d;// 原始扩展费率1
		double dblOriginExt2Rate = 0.0d;// 原始扩展费率2
		double dblOriginExt3Rate = 0.0d;// 原始扩展费率3

		String strPolicyNo = "";
		boolean blnChgPremium = false;

		dblMaxUsableRate = wfGradeDto.getMaxUsableRate();
		// 计算实际发生费用时只需用到最大可用费用率.代理手续费率.经纪人佣金率,其他费用率不使用,暂时保留(默认值为0)

		if (iBusinessType.equals("T")) {

			prpTmain = policyService.getPrpTmainByProposalNo(iBusinessNo);
			dblSumPremium = prpTmain.getSumPremium().doubleValue()
					* prpTmain.getExchangeRate().doubleValue();
			dblAgentRate = prpTmain.getDisRate().doubleValue() / 100;
		} else if (iBusinessType.equals("P")) {
			dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);

			prpCmain = policyService.getPrpCmainByPolicyNo(iBusinessNo);
			prpCmainCovernoteDto = dbPrpCmainCovernote
					.findByPrimaryKey(iBusinessNo);

			if (prpCmain != null) {
				dblSumPremium = prpCmain.getSumPremium().doubleValue()
						* prpCmain.getExchangeRate().doubleValue();
				dblAgentRate = prpCmain.getDisRate().doubleValue() / 100;
			}
			if (prpCmainCovernoteDto != null) {
				dblSumPremium = prpCmainCovernoteDto.getSumPremium()
						* prpCmainCovernoteDto.getExchangeRate();
				dblAgentRate = prpCmainCovernoteDto.getDisRate() / 100;
			}
		} else {
			dbPrpCPmainCovernote = new DBPrpCPmainCovernote(dbManager);
			dbPrpPmainCovernote = new DBPrpPmainCovernote(dbManager);
			dbPrpPheadCovernote = new DBPrpPheadCovernote(dbManager);
			dbPrpCgrade = new DBPrpCgrade(dbManager);

			prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
			prpPheadCovernoteDto = dbPrpPheadCovernote
					.findByPrimaryKey(iBusinessNo);
			// 普通业务处理
			if (prpPhead != null) {
				strPolicyNo = prpPhead.getPolicyNo();
				prpCPmain = prpCpMainService
						.getPrpCpMainByPolicyNo(strPolicyNo);
				prpPmain = endorseService.getPrpPheadByEndorseNo(iBusinessNo)
						.getPrpPmains().get(0);
				prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
				if (prpPmain.getChgPremium().doubleValue() == 0) {
					blnChgPremium = false;
				} else {
					blnChgPremium = true;
				}
				// 如果没有发生保费变化则对换率取批改前的对换率
				if (blnChgPremium) {
					dblSumPremium = prpCPmain.getSumPremium().doubleValue()
							* prpCPmain.getExchangeRate().doubleValue();
				} else {
					dblSumPremium = prpCPmain.getSumPremium().doubleValue()
							* prpPmain.getExchangeRate().doubleValue();
				}
				dblAgentRate = prpCPmain.getDisRate().doubleValue() / 100;
				dblOriginSumPremium = prpPmain.getSumPremium().doubleValue()
						* prpPmain.getExchangeRate().doubleValue();
				dblOriginAgentRate = prpPmain.getDisRate().doubleValue() / 100;
			}
			// Covernote表数据处理
			if (prpPheadCovernoteDto != null) {
				strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
				prpCPmainCovernoteDto = dbPrpCPmainCovernote
						.findByPrimaryKey(strPolicyNo);
				prpPmainCovernoteDto = dbPrpPmainCovernote
						.findByPrimaryKey(iBusinessNo);
				prpCgradeDto = dbPrpCgrade.findByPrimaryKey(strPolicyNo);
				if (prpPmainCovernoteDto.getChgPremium() == 0) {
					blnChgPremium = false;
				} else {
					blnChgPremium = true;
				}
				// 如果没有发生保费变化则对换率取批改前的对换率
				if (blnChgPremium) {
					dblSumPremium = prpCPmainCovernoteDto.getSumPremium()
							* prpCPmainCovernoteDto.getExchangeRate();
				} else {
					dblSumPremium = prpCPmainCovernoteDto.getSumPremium()
							* prpPmainCovernoteDto.getExchangeRate();
				}
				dblAgentRate = prpCPmainCovernoteDto.getDisRate() / 100;
				dblOriginSumPremium = prpPmainCovernoteDto.getSumPremium()
						* prpPmainCovernoteDto.getExchangeRate();
				dblOriginAgentRate = prpPmainCovernoteDto.getDisRate() / 100;
			}

			// P表数据
			dblOriginMaxUsableRate = prpCgradeDto.getManualMaxUsableRate();
			// 计算实际发生费用时只需用到最大可用费用率.代理手续费率.经纪人佣金率,其他费用率不使用,暂时保留(默认值为0)
		}

		dblRealFee = this.calculateRealFee(dblSumPremium, dblOriginSumPremium,
				dblMaxUsableRate, dblBrokerRate, dblAgentRate, dblOrgRate,
				dblBreakevenRate, dblExt1Rate, dblExt2Rate, dblExt3Rate,
				dblOriginMaxUsableRate, dblOriginBrokerRate,
				dblOriginAgentRate, dblOriginOrgRate, dblOriginBreakevenRate,
				dblOriginExt1Rate, dblOriginExt2Rate, dblOriginExt3Rate);
		dbManager.close();
		return dblRealFee;
	}

	/**
	 * 計算實際發生費用.
	 * 
	 * @param dblSumPremium
	 *            保費
	 * @param dblOriginSumPremium
	 *            原始保費
	 * @param dblMaxUsableRate
	 *            最大可用費用率
	 * @param dblBrokerRate
	 *            經紀人傭金率
	 * @param dblAgentRate
	 *            代理手續費率
	 * @param dblOrgRate
	 *            營銷組織利益率
	 * @param dblBreakevenRate
	 *            營銷組織利益率
	 * @param dblExt1Rate
	 *            擴展費率1
	 * @param dblExt2Rate
	 *            擴展費率2
	 * @param dblExt3Rate
	 *            擴展費率3
	 * @param dblOriginMaxUsableRate
	 *            原始最大可用費用率
	 * @param dblOriginBrokerRate
	 *            原始經紀人傭金率
	 * @param dblOriginAgentRate
	 *            原始代理手續費率
	 * @param dblOriginOrgRate
	 *            原始營銷組織利益率
	 * @param dblOriginBreakevenRate
	 *            原始基准營銷費用率
	 * @param dblOriginExt1Rate
	 *            原始擴展費率1
	 * @param dblOriginExt2Rate
	 *            原始擴展費率2
	 * @param dblOriginExt3Rate
	 *            原始擴展費率3
	 * @return 實際發生費用
	 * @throws Exception
	 *             異常
	 */
	public double calculateRealFee(double dblSumPremium,
			double dblOriginSumPremium, double dblMaxUsableRate,
			double dblBrokerRate, double dblAgentRate, double dblOrgRate,
			double dblBreakevenRate, double dblExt1Rate, double dblExt2Rate,
			double dblExt3Rate, double dblOriginMaxUsableRate,
			double dblOriginBrokerRate, double dblOriginAgentRate,
			double dblOriginOrgRate, double dblOriginBreakevenRate,
			double dblOriginExt1Rate, double dblOriginExt2Rate,
			double dblOriginExt3Rate) throws Exception {
		double dblRealFee = 0.0d;

		dblRealFee = dblSumPremium
				* (dblMaxUsableRate - dblBrokerRate - dblAgentRate - dblOrgRate
						- dblBreakevenRate - dblExt1Rate - dblExt2Rate - dblExt3Rate)
				- dblOriginSumPremium
				* (dblOriginMaxUsableRate - dblOriginBrokerRate
						- dblOriginAgentRate - dblOriginOrgRate
						- dblOriginBreakevenRate - dblOriginExt1Rate
						- dblOriginExt2Rate - dblOriginExt3Rate);

		return dblRealFee;
	}

	/**
	 * 獲取當前核定費用結余.
	 * 
	 * @param iExpenseType
	 *           費用模式
	 * @param prpDExpenseBalanceDto
	 *            费用联动核定费用類
	 * @return 當前核定費用結余
	 * @throws Exception
	 *            異常
	 */
	public double getCurrExpenseBalance(String iExpenseType,
			PrpDExpenseBalance prpDExpenseBalanceDto) throws Exception {
		double dblExpenseBalance = 0.0d;

		if (iExpenseType.equals("01")) {// 计划费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getPlanFee()
					+ prpDExpenseBalanceDto.getId().getLoanFee()
					+ prpDExpenseBalanceDto.getId().getPlanFeeChg();
		} else {// 实际费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getActualFeeChg()
					+ prpDExpenseBalanceDto.getId().getLoanFee();
		}

		return dblExpenseBalance;
	}

	/**
	 * 在批單核批通過後獲取最新的核定費用結余.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @return 批單核批通過後獲取最新的核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double getExpenseBalance(String iBusinessType, String iBusinessNo)
			throws Exception {
		DBPrpPheadCovernote dbPrpPheadCovernote = null;
		DBPrpCproduct dbPrpCproduct = null;
		DBPrpCPgrade dbPrpCPgrade = null;

		PrpDExpenseBalance prpDExpenseBalanceDto = null;
		PrpDExpenseControl prpDExpenseControlDto = null;
		PrpPhead prpPhead = null;
		PrpPheadCovernoteDto prpPheadCovernoteDto = null;
		PrpCproductDto prpCproductDto = null;
		PrpCPgradeDto prpCPgradeDto = null;

		Collection col = null;
		Iterator iterator = null;
		String strSql = "";

		double dblExpenseBalance = 0.0d;// 核定费用结余
		String strComCode = "";// 归属机构
		String strRiskCode = "";// 险种代码
		String strProductCode = "";// 产品代码
		String strPolicyNo = "";// 保单号
		String strExpenseType = "";// 费用模式
		String strEndorType = "";// 批改类型
		boolean blnIsExpenseControl = false;// 是否费用控制

		DBManager dbManager = new DBManager();
		try {
			dbManager.open("undwrtDataSource");
			dbManager.beginTransaction();

			if (iBusinessType.equals("E")) {
				dbPrpPheadCovernote = new DBPrpPheadCovernote(dbManager);
				dbPrpCproduct = new DBPrpCproduct(dbManager);
				dbPrpCPgrade = new DBPrpCPgrade(dbManager);

				prpPhead = endorseService.getPrpPheadByEndorseNo(iBusinessNo);
				prpPheadCovernoteDto = dbPrpPheadCovernote
						.findByPrimaryKey(iBusinessNo);
				// 普通业务处理
				if (prpPhead != null) {
					strPolicyNo = prpPhead.getPolicyNo();
					strComCode = prpPhead.getComCode();
					strRiskCode = prpPhead.getRiskCode();
					strEndorType = prpPhead.getEndorType();
				}
				// Covernote表数据处理
				if (prpPheadCovernoteDto != null) {
					strPolicyNo = prpPheadCovernoteDto.getPolicyNo();
					strComCode = prpPheadCovernoteDto.getComCode();
					strRiskCode = prpPheadCovernoteDto.getRiskCode();
					strEndorType = prpPheadCovernoteDto.getEndorType();
				}
				// 产品代码处理
				strSql = " PolicyNo='" + strPolicyNo + "'";
				col = dbPrpCproduct.findByConditions(strSql);
				iterator = col.iterator();
				while (iterator.hasNext()) {
					prpCproductDto = (PrpCproductDto) iterator.next();
					strProductCode = prpCproductDto.getProductCode();
				}

				prpCPgradeDto = dbPrpCPgrade.findByPrimaryKey(strPolicyNo);

				if (prpCPgradeDto != null
						&& (strEndorType.equals("19") || strEndorType
								.equals("21"))) {
					blnIsExpenseControl = true;
				}

				if (blnIsExpenseControl) {
					// 获取'费用联动控制策略'数据
					prpDExpenseControlDto = prpDExpenseControlService
							.getExpenseControl(strComCode);
					if (prpDExpenseControlDto == null) {
						// code
					} else {
						prpDExpenseBalanceDto = prpDExpenseBalanceService
								.getPrpDExpenseBalance(prpDExpenseControlDto
										.getId().getComCode(), strRiskCode,
										strProductCode);

						strExpenseType = prpDExpenseControlDto.getId()
								.getExpenseType();
						if (prpDExpenseBalanceDto == null) {
							// code
						} else {
							dblExpenseBalance = this.getCurrExpenseBalanceTwo(
									strExpenseType, prpDExpenseBalanceDto);
						}
					}
				}
			}

			dbManager.commitTransaction();
		} catch (Exception exception) {
			dbManager.rollbackTransaction();
			throw exception;
		} finally {
			dbManager.close();
		}

		return dblExpenseBalance;
	}

	/**
	 * 獲取當前核定費用結余.
	 * 
	 * @param iExpenseType
	 *           費用模式
	 * @param prpDExpenseBalanceDto
	 *            费用联动核定费用類
	 * @return 當前核定費用結余
	 * @throws Exception
	 *            異常
	 */
	public double getCurrExpenseBalanceTwo(String iExpenseType,
			PrpDExpenseBalance prpDExpenseBalanceDto) throws Exception {
		double dblExpenseBalance = 0.0d;// 核定费用结余

		dblExpenseBalance = this.calculateCurrExpenseBalance(
				prpDExpenseBalanceDto, iExpenseType);

		return dblExpenseBalance;
	}

	/**
	 *計算當前核定費用結余.
	 * 
	 * @param prpDExpenseBalanceDto
	 *            費用聯動核定費用類
	 * @param iExpenseType
	 *            費用模式
	 * @return 當前核定費用結余
	 * @throws Exception
	 *             異常
	 */
	public double calculateCurrExpenseBalance(
			PrpDExpenseBalance prpDExpenseBalanceDto, String iExpenseType)
			throws Exception {
		double dblExpenseBalance = 0.0d;

		if (iExpenseType.equals("01")) {// 计划费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getPlanFee()
					+ prpDExpenseBalanceDto.getId().getLoanFee()
					+ prpDExpenseBalanceDto.getId().getPlanFeeChg();
		} else {// 实际费用模式
			dblExpenseBalance = prpDExpenseBalanceDto.getId().getMaxUsableFee()
					- prpDExpenseBalanceDto.getId().getBrokerFee()
					- prpDExpenseBalanceDto.getId().getAgentFee()
					- prpDExpenseBalanceDto.getId().getOrgFee()
					- prpDExpenseBalanceDto.getId().getBreakevenFee()
					- prpDExpenseBalanceDto.getId().getOthFee1()
					- prpDExpenseBalanceDto.getId().getOthFee2()
					- prpDExpenseBalanceDto.getId().getOthFee3()
					+ prpDExpenseBalanceDto.getId().getActualFeeChg()
					+ prpDExpenseBalanceDto.getId().getLoanFee();
		}

		return dblExpenseBalance;
	}

	/**
	 * 回寫費用聯動控制處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iPolicyNo
	 *            保單號
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種代碼
	 * @param iProductCode
	 *            產品代碼
	 * @param iUserCode
	 *            用戶代碼
	 * @throws Exception
	 *             異常
	 */
	public void echoExpenseControl(String iBusinessType, String iBusinessNo,
			String iPolicyNo, String iComCode, String iRiskCode,
			String iProductCode, String iUserCode) throws Exception {
		com.sinosoft.prpall.dbsvr.tb.DBPrpTgrade dbPrpTgrade = new com.sinosoft.prpall.dbsvr.tb.DBPrpTgrade();
		com.sinosoft.prpall.dbsvr.cb.DBPrpCgrade dbPrpCgrade = new com.sinosoft.prpall.dbsvr.cb.DBPrpCgrade();
		com.sinosoft.prpall.dbsvr.cb.DBPrpCPgrade dbPrpCPgrade = new com.sinosoft.prpall.dbsvr.cb.DBPrpCPgrade();
		// com.sinosoft.prpall.dbsvr.pg.DBPrpPheadCovernote dbPrpPheadCovernote
		// = new com.sinosoft.prpall.dbsvr.pg.DBPrpPheadCovernote();

		PrpDExpenseBalance prpDExpenseBalanceDto = null;
		PrpDExpenseControl prpDExpenseControlDto = null;
		PrpPhead prpPhead = null;
		// PrpPheadCovernoteSchema prpPheadCovernoteSchema = null;

		WfGrade wfGradeDto = null;
		Collection col = null;
		Iterator iterator = null;
		String strSql = "";

		boolean blnIsExpenseControl = false;// 是否费用控制
		String strPolicyNo = "";

		QueryRule queryRule = QueryRule.getInstance();

		iUserCode = this.getOperatorCode(iBusinessNo);
		// 不再使用20130175
		/*
		 * dbPrpTgrade.getInfo(dbpool,iBusinessNo);
		 * dbPrpCgrade.getInfo(dbpool,iBusinessNo);
		 */

		if (iBusinessType.equals("T")) {
			strSql = "BusinessNo='" + iBusinessNo
					+ "' and GradeMode='0' order by logno";
		} else if (iBusinessType.equals("P")) {
			strSql = "BusinessNo='" + iBusinessNo
					+ "' and GradeMode='0' order by logno";
		} else if (iBusinessType.equals("E")) {
			strSql = "BusinessNo='" + iBusinessNo
					+ "' and GradeMode='0' order by logno";
		}
		queryRule.addSql(strSql);
		col = wfGradeService.findByConditions(queryRule);
		iterator = col.iterator();
		while (iterator.hasNext()) {
			wfGradeDto = (WfGrade) iterator.next();
		}
		if (wfGradeDto == null) {
			wfGradeDto = new WfGrade();
		}

		// 获取'费用联动控制策略'数据
		prpDExpenseControlDto = prpDExpenseControlService
				.getExpenseControl(iComCode);
		if (prpDExpenseControlDto == null) {
			// throw new Exception("没有配置相应的'费用联动控制策略',请和系统管理员联系!");
		} else {
			prpDExpenseBalanceDto = prpDExpenseBalanceService
					.getPrpDExpenseBalanceTwo(prpDExpenseControlDto.getId()
							.getComCode(), iRiskCode, iProductCode);

			if (prpDExpenseBalanceDto == null) {
				// throw new Exception("没有配置相应的'费用联动核定费用',请和系统管理员联系!");
			} else {
				if (iBusinessType.equals("T")
						&& !dbPrpTgrade.getProposalNo().equals("")) {
					if (!wfGradeDto.getGradeCode().equals("R")
							&& !wfGradeDto.getGradeCode().equals("")) {
						blnIsExpenseControl = true;
					} else if (!dbPrpTgrade.getAutoGradeCode().equals("R")) {
						blnIsExpenseControl = true;
					}
				} else if (iBusinessType.equals("P")
						&& !dbPrpCgrade.getPolicyNo().equals("")) {
					if (!wfGradeDto.getGradeCode().equals("R")
							&& !wfGradeDto.getGradeCode().equals("")) {
						blnIsExpenseControl = true;
					} else if (!dbPrpCgrade.getAutoGradeCode().equals("R")) {
						blnIsExpenseControl = true;
					}
				} else if (iBusinessType.equals("E")) {
					if (endorseService.getPrpPheadByEndorseNo(iBusinessNo) != null) {
						prpPhead = endorseService
								.getPrpPheadByEndorseNo(iBusinessNo);
						strPolicyNo = prpPhead.getPolicyNo();
						/*
						 * dbPrpCgrade.getInfo(dbpool,strPolicyNo);
						 * dbPrpCPgrade.getInfo(dbpool,strPolicyNo);
						 */
						if (!dbPrpCgrade.getManualGradeCode().equals("")) {
							if (!wfGradeDto.getGradeCode().equals("R")
									&& !wfGradeDto.getGradeCode().equals("")) {
								blnIsExpenseControl = true;
							} else if (!dbPrpCPgrade.getAutoGradeCode().equals(
									"R")) {
								blnIsExpenseControl = true;
							}
						} else {
							blnIsExpenseControl = false;
						}
					}
					/*
					 * else
					 * if(dbPrpPheadCovernote.findByEndorseNo(dbpool,iBusinessNo
					 * ).size()>0){ prpPheadCovernoteSchema =
					 * (PrpPheadCovernoteSchema
					 * )dbPrpPheadCovernote.findByEndorseNo
					 * (dbpool,iBusinessNo).get(0); strPolicyNo =
					 * prpPheadCovernoteSchema.getPolicyNo();
					 * dbPrpCgrade.getInfo(dbpool,strPolicyNo);
					 * dbPrpCPgrade.getInfo(dbpool,strPolicyNo);
					 * if(!dbPrpCgrade.getManualGradeCode().equals("")){
					 * if(!wfGradeDto
					 * .getGradeCode().equals("R")&&!wfGradeDto.getGradeCode
					 * ().equals("")){ blnIsExpenseControl = true; }else
					 * if(!dbPrpCPgrade.getAutoGradeCode().equals("R")){
					 * blnIsExpenseControl = true; } }else{ blnIsExpenseControl
					 * = false; } }
					 */
					else {
						blnIsExpenseControl = false;
					}
				} else {
					blnIsExpenseControl = false;
				}

				if (blnIsExpenseControl) {
					// this.echoExpenseDetailInfo(dbpool,prpDExpenseBalanceDto,iBusinessType,iBusinessNo,iPolicyNo,iProductCode,iUserCode);
				}
			}
		}
	}

	/**
	 * 根據業務號從wflog表中取操作人員代碼
	 * 
	 * @param iBusinessNo
	 *            業務號
	 * @return 操作人員代碼
	 * @throws Exception
	 *             異常
	 */
	public String getOperatorCode(String iBusinessNo) throws Exception {
		String strOperatorCode = "";
		String strFlowID = "";
		int intCount = 0;
		WfLog wfLogDto = null;
		Collection col = null;
		Iterator iterator = null;

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("businessNo", iBusinessNo);

		try {
			// 取操作人员代码
			intCount = wfLogService.getCount(queryRule);
			col = wfLogService.findByQueryRuleList(queryRule);
			iterator = col.iterator();
			while (iterator.hasNext()) {
				wfLogDto = (WfLog) iterator.next();
				break;
			}
			if (wfLogDto != null) {
				strFlowID = wfLogDto.getId().getFlowId();
			}
			if (!strFlowID.equals("")) {
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.flowId", strFlowID);
				queryRule.addEqual("id.logNo", intCount);
				wfLogDto = wfLogService.findByPrimaryKey(queryRule);
				strOperatorCode = wfLogDto.getOperatorCode();
			}
		} catch (Exception exception) {
			throw exception;
		}

		return strOperatorCode;
	}

	/**
	 * 獲取屬性費用聯動控制策略接口.
	 * 
	 * @return 屬性費用聯動控制策略接口的值
	 */
	public PrpDExpenseControlService getPrpDExpenseControlService() {
		return prpDExpenseControlService;
	}

	/**
	 * 設置屬性費用聯動控制策略接口.
	 * 
	 * @param prpDExpenseControlService
	 *            待設置的費用聯動控制策略接口的值
	 */
	public void setPrpDExpenseControlService(
			PrpDExpenseControlService prpDExpenseControlService) {
		this.prpDExpenseControlService = prpDExpenseControlService;
	}

	/**
	 * 獲取屬性費用聯動接口.
	 * 
	 * @return 屬性費用聯動接口的值
	 */
	public PrpDExpenseBalanceService getPrpDExpenseBalanceService() {
		return prpDExpenseBalanceService;
	}

	/**
	 * 設置屬性費用聯動接口.
	 * 
	 * @param prpDExpenseBalanceService
	 *            待設置的費用聯動接口的值
	 */
	public void setPrpDExpenseBalanceService(
			PrpDExpenseBalanceService prpDExpenseBalanceService) {
		this.prpDExpenseBalanceService = prpDExpenseBalanceService;
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
	 * 獲取屬性核保系統接口.
	 * 
	 * @return 屬性核保系統接口的值
	 */
	public UndwrtService getUndwrtService() {
		return undwrtService;
	}

	/**
	 * 設置屬性核保系統接口.
	 * 
	 * @param undwrtService
	 *            待設置的核保系統接口的值
	 */
	public void setUndwrtService(UndwrtService undwrtService) {
		this.undwrtService = undwrtService;
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
	 * 獲取屬性要保書訊息接口.
	 * 
	 * @return 屬性要保書訊息接口的值
	 */
	public PrpCpMainService getPrpCpMainService() {
		return prpCpMainService;
	}

	/**
	 * 設置屬性要保書訊息接口.
	 * 
	 * @param prpCpMainService
	 *            待設置的要保書訊息接口的值
	 */
	public void setPrpCpMainService(PrpCpMainService prpCpMainService) {
		this.prpCpMainService = prpCpMainService;
	}

}
