//标准数据
addSchemaColumn(new schemaColumn("keyword","关键字","text",false));
addSchemaColumn(new schemaColumn("userCode","用户代码","varchar(10,1)",false));
addSchemaColumn(new schemaColumn("password","用户密码","char(10)",true));
addSchemaColumn(new schemaColumn("systemCode","系统代码","VARCHAR2(20)",false));

addSchemaColumn(new schemaColumn("vsMarkRemark","行业企业代码","CHAR(16)",false));
addSchemaColumn(new schemaColumn("vsProvideRemark","行业企业代码","CHAR(16)",false));
addSchemaColumn(new schemaColumn("vsRecycleRemark","行业企业代码","CHAR(16)",false));
addSchemaColumn(new schemaColumn("vsProvidePressBatchNo","批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsProvideStartSerialNo","起始流水号","CHAR(50)",false));
addSchemaColumn(new schemaColumn("vsProvideEndSerialNo","终止流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsRecycleStartSerialNo","起始流水号","CHAR(50)",false));
addSchemaColumn(new schemaColumn("vsRecycleEndSerialNo","终止流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("agUserGradeGroupCode","权限组号代码","CHAR(2)",true));

addSchemaColumn(new schemaColumn("agUserOldUserCode","被复制权限的员工","CHAR(10)",false));
addSchemaColumn(new schemaColumn("agUserNewUserCode","复制到的员工","CHAR(10)",false));

addSchemaColumn(new schemaColumn("agSendCheckBusinessType","业务类型","CHAR(3)",false));
addSchemaColumn(new schemaColumn("agSendCheckEnterpCode","行业企业代码","CHAR(16)",false));
addSchemaColumn(new schemaColumn("agSendCheckStartDate","初始签单日期","DATE",false));
addSchemaColumn(new schemaColumn("agSendCheckEndDate","终止签单日期","DATE",false));

addSchemaColumn(new schemaColumn("utiGroupRuleRule","规则","VARCHAR2(64000)",false));
addSchemaColumn(new schemaColumn("utiTaskRuleRule","规则","VARCHAR2(64000)",false));
addSchemaColumn(new schemaColumn("utiPlatConfigParamValue","参数取值","VARCHAR2(64000)",true));
addSchemaColumn(new schemaColumn("prpDClauseClauseTitle","条款标题","VARCHAR2(80)",false));


addSchemaColumn(new schemaColumn("utiGradeTaskGroupCodeCollection","权限组号代码","VARCHAR2(100)",false));
addSchemaColumn(new schemaColumn("utiSystemSystemCodeCollection","系统代码","VARCHAR2(200)",false));

addSchemaColumn(new schemaColumn("prpUserGradeUserCode","员工代码","CHAR(10)",false));
addSchemaColumn(new schemaColumn("prpUserGradeGroupCode","权限组号代码","CHAR(2)",false));
addSchemaColumn(new schemaColumn("prpDcustomerIdvNewCustomerCode","新的客户代码","VARCHAR2(20)",true));
addSchemaColumn(new schemaColumn("prpDcustomerUnitNewCustomerCode","新的客户代码","VARCHAR2(20)",true));



addSchemaColumn(new schemaColumn("comLevel","机构级别","VARCHAR2(10)",true));

addSchemaColumn(new schemaColumn("comType1","机构类型","VARCHAR2(5)",true));
addSchemaColumn(new schemaColumn("comType2","机构类型","VARCHAR2(5)",true));
addSchemaColumn(new schemaColumn("comType3","机构类型","VARCHAR2(5)",true));
addSchemaColumn(new schemaColumn("comType4","机构类型","VARCHAR2(5)",true));
addSchemaColumn(new schemaColumn("comType5","机构类型","VARCHAR2(5)",true));

addSchemaColumn(new schemaColumn("prpDuserPassword","密码","VARCHAR2(64)",false));


addSchemaColumn(new schemaColumn("AllCarSteal_value","全车盗抢险赔偿限额","INTEGER",false));
addSchemaColumn(new schemaColumn("AllowTempSplit_value","是否允许临分","CHAR(1)",false));
addSchemaColumn(new schemaColumn("Amount_value","车损最大保额","INTEGER",false));
addSchemaColumn(new schemaColumn("DisRate1_value","中间成本","NUMBER(14,2)",false));
addSchemaColumn(new schemaColumn("Discount_value","折扣率","NUMBER(14,2)",false));
addSchemaColumn(new schemaColumn("GroupCarSum_value","团车数量","INTEGER",false));
addSchemaColumn(new schemaColumn("MinusFlag_value","允许招标","CHAR(1)",false));
addSchemaColumn(new schemaColumn("ReinsUnit_value","是否允许划分风险单位","CHAR(1)",false));
addSchemaColumn(new schemaColumn("Selfignite_value","自燃险最大保额","INTEGER",false));
addSchemaColumn(new schemaColumn("ThirdDutyMan_value","三者险赔偿限额","INTEGER",false));
addSchemaColumn(new schemaColumn("UnitPolicy_value","联(共)保","CHAR(1)",false));
addSchemaColumn(new schemaColumn("UseYears_value","使用年限","INTEGER",false));
addSchemaColumn(new schemaColumn("WriteOffDays_value","允许保单註銷天数","INTEGER",false));

addSchemaColumn(new schemaColumn("TransModeComb_codeDesc","运输方式描述","VARCHAR2(255)",false));


addSchemaColumn(new schemaColumn("riskRootPath","险种文件夹根路径","VARCHAR2(255)",false));
addSchemaColumn(new schemaColumn("templateRiskCode","模板险种代码","VARCHAR2(4)",false));
addSchemaColumn(new schemaColumn("newRiskCode","新险种代码","VARCHAR2(4)",false));

addSchemaColumn(new schemaColumn("prpDagreeTextContext","协议文本信息","VARCHAR2(64000)",true));
addSchemaColumn(new schemaColumn("prpDclauseContext","条款内容","VARCHAR2(64000)",true));

addSchemaColumn(new schemaColumn("identifyNo","身份证号","VARCHAR2(18)",true));
addSchemaColumn(new schemaColumn("nationality","民族","VARCHAR2(20)",true));
addSchemaColumn(new schemaColumn("birthday","出生日期","DATE",true));
addSchemaColumn(new schemaColumn("enrollDate","入公司时间","DATE",true));

addSchemaColumn(new schemaColumn("prpDcustomerUnitCustomerCName","客户名称","VARCHAR2(120)",false));
addSchemaColumn(new schemaColumn("prpDcustomerUnitAddressCName","客户地址","VARCHAR2(255)",false));
addSchemaColumn(new schemaColumn("prpDcustomerUnitPhoneNumber","电话","VARCHAR2(30)",false));
addSchemaColumn(new schemaColumn("prpDcustomerUnitLinkerName","联系人","VARCHAR2(20)",false));
addSchemaColumn(new schemaColumn("prpDcustomerUnitPostAddress","通信地址","VARCHAR2(255)",false));
addSchemaColumn(new schemaColumn("prpDcustomerUnitComcode","归属机构代码","VARCHAR2(8)",false));

addSchemaColumn(new schemaColumn("prpDcustomerIdvCustomerCName","客户名称","VARCHAR2(120)",false));
addSchemaColumn(new schemaColumn("prpDcustomerIdvAddressCName","客户地址","VARCHAR2(255)",false));
addSchemaColumn(new schemaColumn("prpDcustomerIdvLinkAddress","通信地址","VARCHAR2(255)",false));
addSchemaColumn(new schemaColumn("prpDcustomerIdvComcode","归属机构代码","VARCHAR2(8)",false));

addSchemaColumn(new schemaColumn("prpDagentAgentName","代理人名称","VARCHAR2(120)",false));
addSchemaColumn(new schemaColumn("prpDagentAddressName","地址名称","VARCHAR2(120)",false));
addSchemaColumn(new schemaColumn("prpDagentPostCode","邮编","VARCHAR2(6)",false));
addSchemaColumn(new schemaColumn("prpDagentAgentType","代理人类型","VARCHAR2(1)",false));
addSchemaColumn(new schemaColumn("prpDagentPermitNo","许可证号","VARCHAR2(20)",false));
addSchemaColumn(new schemaColumn("prpDagentPrincipalName","负责人","VARCHAR2(20)",false));
addSchemaColumn(new schemaColumn("prpDagentPhoneNumber","电话","VARCHAR2(30)",false));
addSchemaColumn(new schemaColumn("prpDagentComCode","归属机构代码","VARCHAR2(8)",false));

addSchemaColumn(new schemaColumn("comcode","归属机构","VARCHAR2(8)",false));

addSchemaColumn(new schemaColumn("prpDcarGroupGroupName","车型分组名","VARCHAR2(60)",false));
addSchemaColumn(new schemaColumn("prpDshipCountryCode","国家代码","VARCHAR2(4)",true));

addSchemaColumn(new schemaColumn("uwGradeUserCode","人员代码","VARCHAR2(10)",false));
addSchemaColumn(new schemaColumn("uwGradeModelNo","模板号","NUMBER",false));
addSchemaColumn(new schemaColumn("uwGradeNodeNo","节点号","NUMBER",false));
addSchemaColumn(new schemaColumn("uwGradeGroupNo","权限组号","NUMBER",false));
addSchemaColumn(new schemaColumn("uwGroupRiskCode","险种代码","VARCHAR2(4)",false));
addSchemaColumn(new schemaColumn("uwGroupComCode","部门代码","VARCHAR2(10)",false));
addSchemaColumn(new schemaColumn("uwGroupGroupNo","权限组号","NUMBER",false));
addSchemaColumn(new schemaColumn("uwGroupGroupDesc","权限组描述","VARCHAR2(60)",false));

addSchemaColumn(new schemaColumn("utiBulletinContentContext","内容","VARCHAR2(64000)",false));
addSchemaColumn(new schemaColumn("utiDiscussContentContext","内容","VARCHAR2(64000)",false)); 
addSchemaColumn(new schemaColumn("utiMessageContentMessageContent","内容","VARCHAR2(64000)",false));
addSchemaColumn(new schemaColumn("prpDagreementSettleCycle","结算周期","INTEGER",false));
addSchemaColumn(new schemaColumn("prpDagreementSettleDay","首个结算日","INTEGER",false));