package com.sinosoft.app.common;

import java.util.HashMap;

public final class CodeConstants {

	public static final class Template {
		public static final String PLANTEMP_NAME = "PlanTemplate.rar";
		public static final String KPITEMP_NAME = "KPITemplate.rar";
		public static final String KPIDATATEMP_NAME = "KPIDataTemplate.rar";

		public static final String PLANDATA_TEPLATE = "/pages/performance/Template/PlanTemplate.rar";
		public static final String KPI_TEPLATE = "/pages/performance/Template/KPITemplate.rar";
		public static final String KPIDATA_TEPLATE = "/pages/performance/Template/KPIDataTemplate.rar";
	}

	public static final String TOP_USERCODE = "00000000";
	public static final String MAINCOMPANYCOMCODE = "00";
	public static final String DEMILITER = "|";
	public static final String VALID = "1"; // 有效
	public static final String INVALID = "0"; // 无效
	public static final double COUNTONEDAY = 24 * 3600 * 1000;

	public static final class StaticNum {
		public static final int ZERO = 0;
		public static final int ONE = 1;
		public static final int TWO = 2;
		public static final int THREE = 3;
		public static final int SIX = 6;
		public static final int ELEVEN = 11;
		public static final int FIVE = 5;
	}

	/** 语言 */
	public static final class Language {
		/** 英语 */
		public static final String ENGLISH = "E";
		/** 汉语 */
		public static final String CHINEXE = "C";
	}

	/** 数据库处理语言 */
	public static final class SQLLanguage {
		/** 升序 */
		public static final String ASC = " asc ";
		/** 降序 */
		public static final String DESC = " desc ";
		/** 大於 */
		public static final String GREATER = " > ";
		/** 小於 */
		public static final String LESS = " < ";
		/** 並且 */
		public static final String AND = " AND ";
	}

	/** 系统角色代码 */
	public static final class AgentRole {
		/** 坐席组长 */
		public static final String TEAMLEADER_CODE = "0002";
		/** 中心MIS岗 */
		public static final String CENTERMIS_CODE = "0103";
		/** 数据MIS岗 */
		public static final String DATAMIS_CODE = "0104";
		/** 活动经理岗 */
		public static final String CAMPAIGNMANAGER_CODE = "0101";
		/** 活动审核岗 */
		public static final String CAMPAIGNCHECK_CODE = "0102";
		/** 质检员 */
		public static final String QUALITYQA_CODE = "0105";
		/** 质检团队 */
		public static final String QUALITYTEAM_CODE = "0106";
		/** 质检管理岗 */
		public static final String QUALITYMANAGE_CODE = "0107";

		/** 坐席组长 */
		public static final String TEAMLEADER = "teamLeader";
		/** 中心MIS岗 */
		public static final String CENTERMIS = "centerMis";
		/** 数据MIS岗 */
		public static final String DATAMIS = "dataMis";
		/** 活动经理岗 */
		public static final String CAMPAIGNMANAGER = "campaignManager";
		/** 活动审核岗 */
		public static final String CAMPAIGNCHECK = "campaignCheck";

	}

	/** 节点-功能代码对照表 */
	public static final HashMap<String, String> NODE_TASKCODE_MAP = new HashMap<String, String>();
	static {
	}

	/** 机构参数代码-系统参数代码 */
	public static final HashMap<String, String> COMPARAMCODE_CODETYPE_MAP = new HashMap<String, String>();
	static {
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.PERSONGRADINGPREMIUMS, "PersonDutyId");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.ASSESSFREQUENCY, "AssessResult");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.ASSESSINDICATORS, "AssessIndicators");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.RISKCONVERSIONRATE, "RiskType");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.BUSSCHANNELRATE, "BussChannel");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.TAXCALCULATIONFORMULA, "TaxStartPoint");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.PERSONWELFARE, "PersonDutyId");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.MANAGERDISTILLRATE, "PersonDutyId");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.ALLOWANCERELEASEWAY, "PersonDutyId");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.RENEWINSURANCERATEWEIGHTCONFIG, "ClassCode");
		COMPARAMCODE_CODETYPE_MAP.put(comParamValueType.RENEWINSURANCEASSESSCONFIG, "ClassCode");
	}

	/** 机构参数代码-TaskCode */
	public static final HashMap<String, String> COMPARAMCODE_TASKCODE_MAP = new HashMap<String, String>();
	static {
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.ASSESSFREQUENCY, "sales.comParamConfig.sysStandardConfig.khpdsz");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.BENNIANJIHUABAOFEI, "sales.comParamConfig.sysStandardConfig.bnjhbf");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.YUGUYINGSHOUYUE, "sales.comParamConfig.sysStandardConfig.ygysye");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.MANAGERDISTILLRATE, "sales.comParamConfig.sysStandardConfig.tdjltjxs");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.ALLOWANCERELEASEWAY, "sales.comParamConfig.sysStandardConfig.fdjthffs");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.RENEWINSURANCERATEWEIGHTCONFIG, "sales.comParamConfig.sysStandardConfig.xblqzsz");
		COMPARAMCODE_TASKCODE_MAP.put(comParamValueType.RENEWINSURANCEASSESSCONFIG, "sales.comParamConfig.sysStandardConfig.xbkhsz");
	}
	/** 分页缺省值-0 */
	public static final int PAGE_DEFU = 0;
	/** 页序号默认值-1 */
	public static final int PAGENO_INIT = 1;
	/** 页宽默认值-10 */
	public static final int PAGESIZE_INIT = 10;

	/** 查询时间间隔范围 */
	public static final int QUERY_DATE_AREA = -365;

	/** 处理审核状态 */
	public static final class VerifyStatus {
		/** 审核通过 */
		public static final String VERIFY_Y = "1";
		/** 审核不通过 */
		public static final String VERIFY_N = "0";
		/** 待审核 */
		public static final String VERIFY_W = "9";
		/** 离司审核通过 */
	}

	/** 人员状态 */
	public static final class PersonType {
		/** 销售人员 */
		public static final String SALESMAN = "1";
		/** 经理 */
		public static final String MANAGER = "3";
	}

	/** 团队是否是专业团队 */
	public static final class TeamIfProTeam {
		/** 专业团队 */
		public static final String YES = "1";
		/** 非专业团队 */
		public static final String NO = "3";
	}

	/** 处理审核类型 */
	public static final class VerifyType {
		/** 入职 */
		public static final String IN_COM = "1";
		/** 离司 */
		public static final String OUT_COM = "2";
	}

	/** 处理PrpSperson表有效状态类型 */
	public static final class PrpSpersonValidStatus {
		/** 在职 */
		public static final String ON_JOB = "1";
		/** 离司/无效 */
		public static final String LEAVE_COM = "0";
		/** 离司退回 */
		public static final String LEAVE_COM_B = "3";
		/** 离司待审核 */
		public static final String LEAVE_COM_W = "9";
	}

	public static final class TeamModifyType {
		/** 团队主管变更操作 */
		public static final String TEAM_MANAGER_MODIFY = "managerM";
		/** 团队变更归属机构操作 */
		public static final String TEAM_COM_MODIFY = "comM";
		/** 团队变更团队类型操作 */
		public static final String TEAM_TYPE_MODIFY = "typeM";
	}

	/** 团队变更类型 对应PrpSteamHis表中的ChangeType字段 */
	public static final class TeamChangeType {
		/** 团队新增 */
		public static final String TEAM_ADD = "1";
		/** 团队主管变更 */
		public static final String TEAM_MANAGER_CHANGE = "2";
		/** 团队合並 */
		public static final String TEAM_UNITE = "3";
		/** 团队裂变 */
		public static final String TEAM_FISSILE = "4";
		/** 团队撤销 */
		public static final String TEAM_CANCLE = "5";
		/** 团队变更归属机构 */
		public static final String TEAM_COM_CHANGE = "6";
		/** 团队变更团队类型 */
		public static final String TEAM_TYPE_CHANGE = "7";
	}

	/** 人员变更类型 对应PrpSpersonHis表中的ChangeType字段 */
	public static final class PersonChangeType {
		/** 加入团队 */
		public static final String PERSON_JOINTEAM = "A";
		/** 离开团队 */
		public static final String PERSON_LEAVETEAM = "L";
		/** 入职 */
		public static final String PERSON_INCOM = "I";
		/** 离司 */
		public static final String PERSON_OUTCOM = "O";
		/** 变更职级 */
		public static final String PERSON_CHANGE_PSOT = "P";
		/** 变更归属团队 */
		public static final String PERSON_CHANGE_TEAM = "T";
		/** 变更归属机构 */
		public static final String PERSON_CHANGE_COM = "C";
	}

	/** 代理人类型 */
	public static final class AgentType {
		/** 专业代理 */
		public static final String PROFESSIONAL_AGENT = "1900201";
		/** 兼业代理 */
		public static final String UNPROFESSIONAL_AGENT = "1900202";
		/** 银行代理 */
		public static final String BANK_AGENT = "190020201";
		/** 邮政代理 */
		public static final String POST_AGENT = "190020202";
		/** 车行代理 */
		public static final String CAR_AGENT = "190020203";
		/** 其它 */
		public static final String OTH_AGENT = "190020299";
		/** 个人代理 */
		public static final String PERSON_AGENT = "1900203";
		/** 经纪公司 */
		public static final String BROKERAGE_FIRM = "1900301";
	}

	/** 代理类型标志 */
	public static final class AgentTypeFlag {
		/** 个人代理人 */
		public static final String AGENT_PERSONNAL = "1";
		/** 代理机构 */
		public static final String AGENT_COMPANY = "2";
	}

	/** 代理人操作结点类型 */
	public static final class OperateNodeType {
		/** 查询操作 */
		public static final String AGENT_QUERY = "query";
		/** 修改操作 */
		public static final String AGENT_MODIFY = "modify";
	}

	/** 老数据补录 */
	public static final String OLDDATA_RECORD = "O";
	/** 新增数据 */
	public static final String NEWDATA = "N";

	/** 保单拆分标志 */
	public static final class SplitFlag {
		public static final String ONE = "1"; // 已经拆分
		public static final String ZERO = "0"; // 未拆分
	}

	/** 保单拆分标志 */
	public static final class AssessType {
		public static final String ASSESS = "0"; // 绩效考核
		public static final String SALARY = "1"; // 薪酬计算
	}

	/** 考核任务状态 */
	public static final class AssessTaskStatus {
		public static final String TASK_NOT_RUN = "0";// 未执行

		public static final String DATA_PRETREATMENT_RUNNING = "1";// 数据预处理执行中

		public static final String DATA_PRETREATMENT_OVER = "2";// 数据预处理执行结束

		public static final String TASK_RUNNING = "3";// 考核任务执行中

		public static final String TASK_PART_RUN = "4";// 部分执行

		public static final String TASK_RUN_OVER = "5";// 已执行

		public static final String TASK_INVALID = "6";// 无效
	}

	/** 考核任务操作类型 */
	public static final class AssessTaskOperType {
		public static final String TASK_MODIFY = "Modify";// 修改操作
		public static final String TASK_DROP = "Drop";// 作废操作
		public static final String TASK_RUN_SALARY = "RunSalary";// 考核执行操作
		public static final String TASK_RUN_ASSESS = "RunAssess";// 薪酬执行操作
	}

	/** 考核任务范围 */
	public static final class AssessTaskScope {
		public static final String COM_ASSESS = "01";// 机构考核
		public static final String TEAM_ASSESS = "02";// 团队考核
		public static final String PERSON_ASSESS = "05";// 人员考核

	}

	/** 数据预处理执行方式 */
	public static final class ProcessRunType {
		public static final String DS_RUN = "01";// 定时执行
		public static final String SS_RUN = "02";// 实时执行

	}

	/** 考核频度 */
	public static final class AssessFrequency {
		public static final String MONTH_ASSESS = "Month";// 月度考核
		public static final String QUARTER_ASSESS = "Quarter";// 季度考核
		public static final String HALFYEAR_ASSESS = "HalfYear";// 半年考核
		public static final String YEAR_ASSESS = "Year";// 年度考核
	}

	/** 机构参数值类型 */
	public static final class comParamValueType {
		public static final String PERSONGRADINGPREMIUMS = "YWYDJBF";// 业务员定级保费
		public static final String ASSESSFREQUENCY = "KHPDSZ";// 考核频度设置
		public static final String ASSESSINDICATORS = "KHZBSZ";// 考核指标设置
		public static final String PERSONWELFARE = "YWYFLSZ";// 业务员福利设置
		public static final String RISKCONVERSIONRATE = "XZZSXS";// 险种折算系数
		public static final String BUSSCHANNELRATE = "YWLYXS";// 业务来源系数
		public static final String MANAGERDISTILLRATE = "TDJLTJXS";// 团队经理提奖系数
		public static final String ALLOWANCERELEASEWAY = "FDJTHFFS";// 浮动津贴核发方式
		public static final String TAXCALCULATIONFORMULA = "SJJSGS";// 税金计算公式
		public static final String MONTHLYPERFORMANCE = "MonthlyPerformance";// 月度绩效
		public static final String SUMPREMIUM = "SumPremium";// 保费收入
		public static final String MANAGEMENTPERFORMANCE = "ManagementPerformance";// 营销员实收保费
		public static final String CLIENTMANAGERPERFORMANCE = "ClientManagerPerformance";// 业务员月度绩效
		public static final String RENEWINSURANCERATEWEIGHTCONFIG = "XBLQZSZ";// 续保率权重设置
		public static final String RENEWINSURANCEASSESSCONFIG = "XBKHSZ";// 续保考核设置
		public static final String BENNIANJIHUABAOFEI = "BNJHBF";// 本年计划保费
		public static final String YUGUYINGSHOUYUE = "YGYSYE";// 预估应收余额
	}
}
