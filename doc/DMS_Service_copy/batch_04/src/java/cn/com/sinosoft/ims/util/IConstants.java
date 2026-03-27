package cn.com.sinosoft.ims.util;

public class IConstants {
	/**
	 * 有效性
	 */
	public static final String VALIDSTATUS_VALID = "1"; // 有效状态
	public static final String VALIDSTATUS_INVALID = "0"; // 无效状态

	/**
	 * 帐号允许登录标志
	 */
	public static final String LoginPowerFlag_VALID = "1"; // 允许
	public static final String LoginPowerFlag_INVALID = "0"; // 不允许

	/**
	 * 服务分类
	 */
	public static final String SVRTYPE_DB = "1";// 数据库
	public static final String SVRTYPE_APPSERVER = "2";// 应用服务器
	public static final String SVRTYPE_APPSYSTEM = "3";// 应用系统
	/**
	 * 服务认证方式
	 */
	public static final String SVRLOGINMETHOD_CARD = "磁卡";
	public static final String SVRLOGINMETHOD_USBKEY = "USBKEY";
	public static final String SVRLOGINMETHOD_NAMEPWD = "用户名密码";
	/**
	 *服务管理权限状态
	 */
	public static final String MANAGERIGHTSTATUS_VALID = "1";
	public static final String MANAGERIGHTSTATUS_INVALID = "0";
	/**
	 *服务管理菜单状态
	 */
	public static final String MANAGEMENUTATUS_VALID = "1";
	public static final String MANAGEMENUTATUS_INVALID = "0";
	/**
	 *账户受管状态
	 */
	public static final String MANAGEACCSTATUS_VALID = "1";
	public static final String MANAGEACCSTATUS_INVALID = "0";
	/**
	 * 平台管理状态
	 */
	public static final String MANAGELOGINSTATUS_VALID = "1";
	public static final String MANAGELOGINSTATUS_INVALID = "0";
	/**
	 *账户同步状态
	 */
	public static final String ACCOUNTSYNCHSTATUS_VALID = "1";
	public static final String ACCOUNTSYNCHSTATUS_INVALID = "0";
	/**
	 *账户信息同步状态
	 */
	public static final String ACCOUNTMSGSYNCH_VALID = "1";
	public static final String ACCOUNTMSGSYNCH_INVALID = "0";
	/**
	 *使用账户登陆状态
	 */
	public static final String ACCOUNTLOGINSTATUS_VALID = "1";
	public static final String ACCOUNTLOGINSTATUS_INVALID = "0";

	/**
	 * 用户类型
	 */
	public static final String USERTYPE_STUFF = "01"; // 员工用户
	public static final String USERTYPE_SALES = "02"; // 业务员用户
	public static final String USERTYPE_VIRTUAL = "03"; // 虚拟用户（两）
	public static final String USERTYPE_PARTNERS = "04"; // 合作伙伴用户（两）
	public static final String USERTYPE_ENTERPRISE = "06"; // 企业客户
	public static final String USERTYPE_PERSONAL = "07"; // 个人客户
	public static final String USERTYPE_TEMPORARY = "98"; // 临时用户（两）
	public static final String USERTYPE_OTHER = "99"; // 其他用户（两）

	/**
	 * 用户分类
	 */
	public static final String USERSORT_PERSONAL = "01"; // 个人用户
	public static final String USERSORT_Enterprise = "02"; // 企业用户

	/**
	 * 用户来源
	 */
	public static final String USERSOURCE_HR = "HR"; // HR系统
	public static final String USERSOURCE_BPS = "BPS"; // BPS系统
	public static final String USERSOURCE_ECS = "ECS"; // 电子商务
	public static final String USERSOURCE_PRPALL = "PRPALL"; // 非车险承保
	public static final String USERSOURCE_PRPCAR = "PRPCAR"; // 车险承保
	public static final String USERSOURCE_NEWUW = "NEWUW"; // 新双核
	public static final String USERSOURCE_CLAIMCAR = "CLAIMCAR"; // 新车险理赔
	public static final String USERSOURCE_CLAIMALL = "CLAIMALL"; // 新非车险理赔
	public static final String USERSOURCE_VMS = "VMS"; // 新单证
	public static final String USERSOURCE_PMS = "PMS"; // 产品管理
	public static final String USERSOURCE_DMS = "DMS"; // 统一数据字典管理
	public static final String USERSOURCE_IMS = "ims"; // 统一用户管理
	public static final String USERSOURCE_CIF = "CIF"; // 客户管理

	/**
	 * 国别
	 */
	public static final String COUNTRY_CHINA = "001"; // 中国

	/**
	 * 漫游状态
	 */
	public static final String ROAMINGSTATUS_AUTO = "01"; // 自动
	public static final String ROAMINGSTATUS_HANDLE = "02"; // 手动

	/**
	 * 漫游分类
	 */
	public static final String ROAMINGTYPE_ROAMING = "01"; // 漫游
	public static final String ROAMINGTYPE_BACK = "02"; // 收回

	/**
	 * 项目类型
	 */
	public static final String ItemType_Write = "0";// 录入类型

	public static final String ItemType_Obtain = "1";// 从数据源中得到类型

	public static final String ItemType_Option = "2";// 单项选择项目

	public static final String ItemType_CheckBox = "3";// 多项选择项目

	/**
	 * 选项类型
	 */
	public static final String OptionType_Option = "2";// 单项选择项目

	public static final String OptionType_CheckBox = "3";// 多项选择项目

	/**
	 * 录入类型
	 */
	public static final String InputType_TextArea = "0";// 文本框类型

	public static final String InputType_Text = "1";// 文本类型

	public static final String InputType_Number = "2";// 数值类型

	/**
	 * 审核变量
	 */
	public static final String DEF_NAME = "userAudit";

	public static final String AUDITTASKID = "auditTaskId";

	public static final String APPLICANTCODE = "applicantCode";

	public static final String APPLICANTNAME = "applicantName";

	public static final String USERCODE = "userCode";

	public static final String USERNAME = "userName";

	public static final String VERIFYUSERCODE = "verifyUserCode";

	public static final String VERIFYUSERNAME = "verifyUserName";

	public static final String COMCODE = "comCode";

	public static final String COMNAME = "comName";

	public static final String VERIFYNODE = "verifyNode";

	public static final String VERIFYLEVEL = "verifyLevel";

	public static final String NODESTATUS = "nodeStatus";

	public static final String VERIFYOPINION = "verifyOpinion";

	public static final String VERIFYDATE = "verifyDate";

	public static final String AUDITADMIN = "admin";

	public static final String TASKNAME = "taskName";

	public static final String USERCOMNAME = "userComName";

	public static final String FIRSTAUDITOR = "admin1";

	public static final String SECONDAUDITOR = "admin2";

	public static final String THIRDAUDITOR = "admin3";

	public static final String NEWUSERAPPLICATION = "新增用户";

	public static final String CHANGEUSERVALIDATE = "更改用户有效性";

	public static final String REPLYAPPLY = "重新提交申请";
	// 任务环节
	public static final String VERIFYNODE_END = "0"; // 结束

	public static final String VERIFYNODE_START = "01"; // 开始

	public static final String VERIFYNODE_FIRSTAUDIT = "1"; // 一审

	public static final String VERIFYNODE_SECONDAUDIT = "2"; // 二审

	public static final String VERIFYNODE_THIRDAUDIT = "3"; // 三审

	public static final String VERIFYNODE_REJECT = "4"; // 驳回

	public static final String VERIFYNODE_USER = "5";

	// 任务状态

	public static final String NODESTATUS_APPLICATIONSUBMIT = "1"; // 提交申请

	public static final String NODESTATUS_NOTVERIFY = "0"; // 未审核

	public static final String NODESTATUS_FIRSTDISAPPROVE = "10"; // 一审不通过

	public static final String NODESTATUS_FIRSTAPPROVE = "11"; // 一审通过

	public static final String NODESTATUS_FIRSTREJECT = "12"; // 一审驳回

	public static final String NODESTATUS_SECONDDISAPPROVE = "20"; // 二审不通过

	public static final String NODESTATUS_SECONDAPPROVE = "21"; // 二审通过

	public static final String NODESTATUS_SECONDREJECT = "22"; // 二审驳回

	public static final String NODESTATUS_THIRDDISAPPROVE = "30"; // 三审不通过

	public static final String NODESTATUS_THIRDAPPROVE = "31"; // 三审通过

	public static final String NODESTATUS_THIRDREJECT = "33"; // 三审驳回

	public static final String NODESTATUS_USERCANCELTASK = "00"; // 用户取消任务

	public static final String NODESTATUS_USERREPLAYSUBMIT = "111";// 重新提交

	// 配置文件路径
	public static String Properties_Path = "system";// 部署IMS机构

	//modify by duanfa 20110726 start 总公司改为31000000
//	public static String ComCode_Head = "00000000";
	public static String ComCode_Head = "31000000";// 部署在总公司的IMS机构代码
	//modify by duanfa 20110726 end
	public static String SvrCode_IMS = "ims";

	// 同步请求标识
	public static String RequestFlag_UserMaintain = "0";// 对用户进行的维护标识（用户增加、信息修改【修改、漫游/收回、注销/激活】）
	public static String RequestFlag_UserTypeMaintain = "1";// 用户类型维护
	public static String RequestFlag_UserCompanyMaintain = "2";// 用户机构调整

	// 同步账户请求标识
	public static String RequestFlag_AccountAddOrUpdate = "1";// 对账户进行维护标识（账户增加、修改、注销/激活、用户账户匹配）
	public static String RequestFlag_AccountLogOnOrOut = "2"; // 账户注销/激活
	// public static String RequestFlag_AccountActivate = "6";//账户激活
	public static String RequestFlag_AccountUserMatch = "8";// 用户账户匹配

	// public String
	public static String JmsConfig_HD = "/spring/HDJmsConfig.xml";
	public static String JmsConfig_BC = "/spring/BCJmsConfig.xml";

	public static String loginStatus_ACC = "2";// 使用统一账户代码
	public static String loginStatus_FACC = "1";// 使用原来账户代码
	public static String loginStatus_ALL = "0";// 两者都可以使用
	public static String DB_ORACLE = "oracle";
	public static String DB_INFORMIX = "informix";
	public static String ENCODING_UTF8 = "UTF-8";
	public static String ENCODING_GBK = "GBK";
	
	//系统是否分离
	public static String SPLIT_YES = "yes";
	public static String SPLIT_NO = "no";
	//是否集成审核
	public static String USERAUDIT_YES = "yes";
	public static String USERAUDIT_NO = "no";
	
	//addpower方法需要的参数定义
	public static String SVRCODE = "dms";
	//机构代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_COMCODE = "dms_prpDcompany";
	
	public static String PRPDCOMPANY_BM = "prpDcompany.comCode";
	
	//汽车经销商代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDDEALER_COMCODE = "dms_prpDdealer";
	
	public static String PRPDDEALER_BM = "prpDdealer.comCode";
	
	//专管专营代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDRESOURCE_COMCODE = "dms_prpDresourceMenu";
	
	public static String PRPDRESOURCE_BM = "prpDresource.comCode";
	
	//国管局项目PICC联系人代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDSETTLEMENTLKR_COMCODE = "dms_prpDsettlementLkr";
	
	public static String PRPDSETTLEMENTLKR_BM = "prpDsettlementLkr.comCode";
	
	//共保体代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDCOINS_COMCODE = "dms_prpDcoins";
	
	public static String PRPDCOINS_BM = "prpDcoins.id.comCode";
	
	//渠道代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDAGENTALL_COMCODE = "dms_prpDagent";
	
	public static String PRPDAGENTALL_BM = "prpDagentAll.comCode";
	
	public static String PRPDAGENT_BM = "prpDagent.comCode";
	
	//金融机构代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDBANK_COMCODE = "dms_prpDbank";
	
	public static String PRPDBANK_BM = "prpDbank.comCode";
	
	//项目代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDPROJECT_COMCODE = "dms_prpDproject";
	
	public static String PRPDPROJECT_BM = "prpDproject.comCode";
	
	//特别约定代码菜单（允许机构之内的数据查询，功能代码taskCode）
	public static String SEARCH_PRPDRISKENGAGE_COMCODE = "dms_prpDriskEngage";
	
	public static String PRPDRISKENGAGE_BM = "prpDriskEngage.areaCode";
	
	
}
