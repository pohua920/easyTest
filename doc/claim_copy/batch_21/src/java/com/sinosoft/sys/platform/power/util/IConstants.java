package com.sinosoft.sys.platform.power.util;

/**
 * SaaTask——SaaGrade
 * 权限对应功能常量
 * @author 中科软
 * 
 */
public class IConstants {
	
	// Field descriptor #10 Ljava/lang/String;
	public static final java.lang.String INVALID = "invalid";

	// Field descriptor #10 Ljava/lang/String;
	public static final java.lang.String FIELD_SEPARATOR = "_FIELD_SEPARATOR_";

	// Field descriptor #10 Ljava/lang/String;
	public static final java.lang.String GROUP_SEPARATOR = "_GROUP_SEPARATOR_";

	public static final String MODULE_MENU = "menu"; // 菜单

	public static final String SAA = "ewps.system.powerManage"; // 权限平台系统

	public static final String SAA_GRADE = "sales.gradeManage"; // 岗位管理

	public static final String SAA_INSTEAD = "SAA_Instead"; // 代岗授权

	public static final String SAA_USERPOWER = "ewps.userPower.config"; // 人员权限

	public static final String SAA_TASKS = "SAA_Tasks"; // 功能管理

	public static final String SAA_USERPOWER_CONFIG = "ewps.userPower.config"; // 人员权限详细配置

	public static final String SAA_USERPOWER_AUTHADMIN = "ewps.userPower.grant"; // 管理人员分级授权

	public static final String SAA_USERPOWER_POWERFULLCOPY = "ewps.userPower.powerFullCopy"; // 人员权限完全复制

	public static final String SAA_USERPOWER_POWERDATAEXPL = "ewps.userPower.exportUserPower"; // 人员权限数据导出

	public static final String SAA_USERPOWER_POWERDATAIMP = "SAA_UserPower_PowerDataImp"; // 人员权限数据导入

	public static final String SAA_GRADE_QUERY = "ewps.gradeManage"; // 岗位查询权限

	public static final String SAA_GRADE_INSERT = "ewps.gradeManage"; // 岗位增加权限

	public static final String SAA_GRADE_UPDATE = "ewps.gradeManage"; // 岗位修改权限

	public static final String SAA_GRADE_DELETE = "ewps.gradeManage"; // 岗位删除权限

	public static final String SAA_INSTEAD_QUERY = "SAA_Instead_Query"; // 代岗人员查询权限

	public static final String SAA_INSTEAD_INSERT = "SAA_Instead_Insert"; // 代岗授权执行权限

	public static final String SAA_INSTEAD_BACK = "SAA_Instead_Back"; // 代岗权限回收权限

	public static final String SAA_TASKS_QUERY = "SAA_Tasks_Query"; // 系统功能查询

	public static final String SAA_TASKS_INSERT = "SAA_Tasks_Insert"; // 系统功能增加

	public static final String SAA_TASKS_UPDATE = "SAA_Tasks_Update"; // 系统功能更新

	public static final String SAA_TASKS_DELETE = "SAA_Tasks_Delete"; // 系统功能删除

	public static final String SAA_USERPOWER_CONFIG_USERGRADE = "ewps.system.powerManage.grade"; // 人员岗位授予权限

	public static final String SAA_USERPOWER_CONFIG_USERADDPOWER = "ewps.system.powerManage.power"; // 人员岗位业务范围授予权限

	public static final String SAA_USERSQUERY = "SAA_UsersQuery";// 权限平台中人员查询
	public static final String SAA_COMSQUERY = "SAA_ComsQuery";// 权限平台中人员查询

	public static final String ALL_BUSINESSLINE_CODE_KEY = "cn.com.sinosoft.saa.web.CodeInputAction.getRiskTree.allllbusinesslineCode";

	// agent功能
	public static final String SAA_AGENTPOWER = "SAA_AgentPower"; // 代理人员权限
	public static final String SAA_AGENTPOWER_USERQUERY = "SAA_AgentPower_UerQuery"; // 代理人员查询
	public static final String SAA_AGENTPOWER_COMQUERY = "SAA_AgentPower_ComQuery"; // 代理机构查询
	public static final String SAA_AGENTPOWER_AGENTUSERPOWER = "SAA_AgentPower_AgentUserPower"; // 代理人员授权
	/** saa_authTask */
	public static final String SAA_AUTHTASK = "SAA_AuthTask"; // 自动初始化功能代码


	/** 系统管理  **/
	public static final String EWPS_SYSTEM_COMPANY = "ewps.system.company"; // 机构管理
	public static final String EWPS_SYSTEM_COMPANY_HRSYNC = "ewps.system.company.hrsync"; // 机构管理
	public static final String EWPS_SYSTEM_COMPANY_COMMANAGER = "ewps.system.company.commanager"; // 机构管理
	public static final String EWPS_SYSTEM_USER = "ewps.system.user"; //
	public static final String EWPS_SYSTEM_USER_HRSYNC = "ewps.system.user.hrsync"; // 机构管理
	public static final String EWPS_SYSTEM_USER_USERMANAGER = "ewps.system.user.usermanager"; // 机构管理
	public static final String EWPS_SYSTEM_POWERMANAGE = "ewps.system.powerManage"; // 权限管理
	public static final String EWPS_SYSTEM_POWERMANAGE_POWER = "ewps.system.powerManage.power"; // 权限详细设置
	public static final String EWPS_SYSTEM_SAA_GRADE = "ewps.system.saa.grade"; // 人员岗位授予权限
	public static final String EWPS_SYSTEM_SAA_POWER = "ewps.system.saa.power"; // 人员岗位业务范围授予权限
	
	/** 总公司活动量**/
	public static final String EWPS_ACTIVITY = "ewps.activity";
	/** 总公司活动量--------部门**/
	public static final String EWPS_ACTIVITY_DEPART = "ewps.activity.depart";//部门周活动量
	public static final String EWPS_ACTIVITY_DEPART_PLAN = "ewps.activity.depart.plan";//部门周计划
	public static final String EWPS_ACTIVITY_DEPART_IMPORT = "ewps.activity.depart.import";//部门周活动量导出
	public static final String EWPS_ACTIVITY_DEPART_SUMMARY = "ewps.activity.depart.summary";//部门周总结
	public static final String EWPS_ACTIVITY_DEPART_PLANINPUT = "ewps.activity.depart.planInput";//部门周计划录入
	public static final String EWPS_ACTIVITY_DEPART_SUMMARYINPUT = "ewps.activity.depart.summaryInput";//部门周总结录入
	
	public static final String EWPS_ACTIVITY_VICEDEPART = "ewps.activity.vdepart";//部门周活动量
	public static final String EWPS_ACTIVITY_VICEDEPART_PLAN = "ewps.activity.vdepart.plan";//部门周计划
	public static final String EWPS_ACTIVITY_VICEDEPART_IMPORT = "ewps.activity.vdepart.import";//部门周活动量导出
	public static final String EWPS_ACTIVITY_VICEDEPART_SUMMARY = "ewps.activity.vdepart.summary";//部门周总结
	public static final String EWPS_ACTIVITY_VICEDEPART_PLANINPUT = "ewps.activity.vdepart.planInput";//部门周计划录入
	public static final String EWPS_ACTIVITY_VICEDEPART_SUMMARYINPUT = "ewps.activity.vdepart.summaryInput";//部门周总结录入
	
	/** 总公司活动量--------处室**/
	public static final String EWPS_ACTIVITY_OFFICE = "ewps.activity.office";//处室周活动量
	public static final String EWPS_ACTIVITY_OFFICE_PLAN = "ewps.activity.office.plan";//处室周计划
	public static final String EWPS_ACTIVITY_OFFICE_IMPORT = "ewps.activity.office.import";//处室周活动量导出
	public static final String EWPS_ACTIVITY_OFFICE_SUMMARY = "ewps.activity.office.summary";//处室周总结
	/** 总公司活动量--------个人**/
	public static final String EWPS_ACTIVITY_PERSONAL = "ewps.activity.personal";//个人周活动量
	public static final String EWPS_ACTIVITY_PERSONAL_PLAN = "ewps.activity.personal.plan";//个人周计划
	public static final String EWPS_ACTIVITY_PERSONAL_IMPORT = "ewps.activity.personal.import";//个人周活动量导出
	public static final String EWPS_ACTIVITY_PERSONAL_SUMMARY = "ewps.activity.personal.summary";//个人周总结
	/** 总公司活动量--------分管总审阅**/
	public static final String EWPS_ACTIVITY_REVIEW = "ewps.activity.review";//分管总审阅
	public static final String EWPS_ACTIVITY_REVIEW_PLAN = "ewps.activity.review.plan";//部门周计划
	public static final String EWPS_ACTIVITY_REVIEW_COMPLETE = "ewps.activity.review.complete";//部门周活动量导出
	/** 总公司活动量--------二级机构班子**/
	public static final String EWPS_ACTIVITY_BRANCH = "ewps.activity.brach";//二级机构班子
	public static final String EWPS_ACTIVITY_BRANCH_MEMBER = "ewps.activity.branch.member";//机构班子录入
	public static final String EWPS_ACTIVITY_BRANCH_WEEKPLAN = "ewps.activity.branch.weekPlan";//周工作明细检视
	public static final String EWPS_ACTIVITY_BRANCH_PLAN = "ewps.activity.branch.plan";//周工作填写
	public static final String EWPS_ACTIVITY_BRANCH_ACTIVITY = "ewps.activity.branch.activity";//活动量填写
	public static final String EWPS_ACTIVITY_BRANCH_COMPANY = "ewps.activity.branch.company";//机构活动量检视
	public static final String EWPS_ACTIVITY_BRANCH_DITCH = "ewps.activity.branch.ditch";//渠道活动量检视
	public static final String EWPS_ACTIVITY_BRANCH_PLANUPDATE = "ewps.activity.branch.planUpdate";//周计划查询与修改
	public static final String EWPS_ACTIVITY_BRANCH_MEMBERQUERY = "ewps.activity.branch.memberQuery";//班子成员查询
}
