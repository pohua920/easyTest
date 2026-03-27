/**

 * 这是PhysicalDataModel_1标准数据定义文件,请勿编辑此文件<br>

 * 创建于 2004-09-21 13:10:54.921<br>

 * JToolpad(1.2.14) Vendor:zhouxianli@sinosoft.com.cn

 */

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaPre","单证号字冠","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaSerialNo","单证流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkPressBatchNo","印刷批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkBusinessNo","业务号","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaAmount","单据金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkUseDate","使用日期","DATE",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVisaStatus","单证状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkProvideTimes","发放次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkRecycleTimes","回收次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkVerifiedCancelFlag","是否核销标志","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsUnUsedMarkBeforeStatus","丢失前状态","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsUnUsedMarkFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsCodeVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCodeVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsCodeVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCodeVisaPre","单证号字冠","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsCodeVisaNoLength","单证流水号长度","SMALLINT",false));

addSchemaColumn(new schemaColumn("vsCodeDesignerCode","单证格式制定人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsCodeDesignerName","单证格式制定人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsCodeApproverCode","审批人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsCodeApproverName","审批人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsCodeHeight","长","DECIMAL(6,2)",false));

addSchemaColumn(new schemaColumn("vsCodeWidth","宽","DECIMAL(6,2)",false));
/* modify by anhua begin 20050720 */
//颜色修改为非必填项
//addSchemaColumn(new schemaColumn("vsCodeColor","颜色","CHAR(20)",false));
addSchemaColumn(new schemaColumn("vsCodeColor","颜色","CHAR(20)",true));
/* modify by anhua end 20050720 */
addSchemaColumn(new schemaColumn("vsCodeParValue","面值","DECIMAL(7,3)",true));

addSchemaColumn(new schemaColumn("vsCodePageFormat","纸张规格","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsCodeCalcUnit","计量单位","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsCodeReleaseDate","发布时间","DATE",false));

addSchemaColumn(new schemaColumn("vsCodeStartUseDate","开始使用时间","DATE",true));

addSchemaColumn(new schemaColumn("vsCodeEndUseDate","停止使用时间","DATE",true));

addSchemaColumn(new schemaColumn("vsCodeObverse","原件正面图像文件名","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsCodeVerso","原件反面图像文件名","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsCodeWarningCount","报警数量限","INTEGER",true));

addSchemaColumn(new schemaColumn("vsCodeOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsCodeComCode","归属机构代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsCodeComName","归属机构名称","CHAR(60)",false));

addSchemaColumn(new schemaColumn("vsCodeVisaIssueFlag","颁布标志","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCodeVisaFlag","是否需要复核","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsCodeNewVisaCode","最新单证代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsCodeValidStatus","有效标志","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsCodeRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsCodeFlag","备用标志","CHAR(10)",true));

/* add by anhua begin 20050720 */
//单证-版本登记,增加字段：联次、正本联次、副本联次
addSchemaColumn(new schemaColumn("vsCodeTotalSheet","联次","INTEGER",true));

addSchemaColumn(new schemaColumn("vsCodeOriginSheet","正本联次","INTEGER",true));

addSchemaColumn(new schemaColumn("vsCodeCopySheet","副本联次","INTEGER",true));
/* add by anhua end 20050720 */



addSchemaColumn(new schemaColumn("vsMarkVisaPre","单证号字冠","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsMarkVisaSerialNo","单证流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsMarkVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsMarkPressBatchNo","印刷批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsMarkBusinessNo","业务号","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsMarkVisaAmount","单据金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsMarkUseDate","使用日期","DATE",false));

addSchemaColumn(new schemaColumn("vsMarkUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsMarkUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsMarkVisaStatus","单证状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkProvideTimes","发放次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsMarkRecycleTimes","回收次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsMarkVerifiedCancelFlag","是否核销标志","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsMarkBeforeStatus","丢失前状态","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsMarkRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsMarkFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsNoMarkVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsNoMarkVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsNoMarkPressBatchNo","印刷批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsNoMarkQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsNoMarkUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsNoMarkUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsNoMarkUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsNoMarkUseDate","使用时间","DATE",false));

addSchemaColumn(new schemaColumn("vsNoMarkVisaStatus","单证状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsNoMarkBeforeStatus","丢失前状态","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsNoMarkRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsNoMarkFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsNoPressBusinessNo","业务号","CHAR(30)",false));

addSchemaColumn(new schemaColumn("vsNoPressVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsNoPressVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsNoPressUseDate","使用日期","DATE",false));

addSchemaColumn(new schemaColumn("vsNoPressUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsNoPressUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsNoPressUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsNoPressUseExplain","使用说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsNoPressRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsNoPressFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsStorageVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStorageVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsStorageVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsStoragePressBatchNo","批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsStorageApplyComCode","申印单位代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsStorageApplyComNakme","申印单位名称","CHAR(80)",false));

addSchemaColumn(new schemaColumn("vsStorageHandlerCode","申印经办人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsStorageHandlerName","申印经办人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsStorageDutyCode","申印单位负责人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsStorageDutyName","申印单位负责人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsStorageApproverCode","办公室审批人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsStorageApproverName","办公室审批人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsStorageApplyDate","申印日期","DATE",true));

addSchemaColumn(new schemaColumn("vsStorageCalcUnit","核算计量单位","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsStorageApplyCalcQuantity","申印数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsStorageCalcUnitQuantity","入库数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsStorageUnitPrice","单价","DECIMAL(9,3)",true));

addSchemaColumn(new schemaColumn("vsStorageBindPages","装订张数","INTEGER",false));

addSchemaColumn(new schemaColumn("vsStorageBindWay","装订方式","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStorageStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsStorageEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsStorageStorageQuantity","实际入库数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsStorageStoragePlace","库存保管地点","CHAR(60)",true));

addSchemaColumn(new schemaColumn("vsStorageShelfNo","货架号","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsStoragePrinteryCode","印刷厂代码","CHAR(8)",true));

addSchemaColumn(new schemaColumn("vsStorageStorageDate","单证实际入库日期","DATE",false));

addSchemaColumn(new schemaColumn("vsStorageStorageExplain","入库说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsStorageInvoiceNo","发票号","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsStorageInvoiceDate","开票日期","DATE",true));

addSchemaColumn(new schemaColumn("vsStoragePaidFlag","是否已结付","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsStoragePayMode","付款方式","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsStoragePayDate","付款日期","DATE",true));

addSchemaColumn(new schemaColumn("vsStoragePayExplain","付款说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsStorageCheckerType","入库登记复核人类型","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsStorageCheckerCode","入库登记复核人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsStorageCheckerName","入库登记复核人名称","CHAR(60)",true));

addSchemaColumn(new schemaColumn("vsStorageOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsStorageComCode","归属机构代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsStorageComName","归属机构名称","CHAR(60)",false));

addSchemaColumn(new schemaColumn("vsStorageInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsStorageInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsStorageRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsStorageFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsDrawDrawID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsDrawVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsDrawVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsDrawVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsDrawProviderType","发放人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsDrawProviderCode","发放人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsDrawProviderName","发放人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsDrawAccepterType","领用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsDrawAccepterCode","领用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsDrawAccepterName","领用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsDrawApproverCode","领用申请审批人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsDrawApproverName","领用申请审批人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsDrawHandlerCode","领用申请经办人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsDrawHandlerName","领用申请经办人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsDrawQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsDrawOperateExplain","领用操作说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsDrawOperateDate","申领日期","DATE",false));

addSchemaColumn(new schemaColumn("vsDrawOperateStatus","操作状态","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsDrawOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsDrawInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsDrawInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsDrawRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsDrawFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsProvideProvideID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsProvideVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsProvideVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsProvideVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsProvidePressBatchNo","批次","CHAR(16)",true));

addSchemaColumn(new schemaColumn("vsProvideProviderType","发放人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsProvideProviderCode","发放人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsProvideProviderName","发放人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsProvideAccepterType","领用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsProvideAccepterCode","领用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsProvideAccepterName","领用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsProvideApproverCode","发放审批人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsProvideApproverName","发放审批人人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsProvideHandlerCode","发放经办人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsProvideHandlerName","发放经办人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsProvideStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsProvideEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsProvideQuantity","基本单位发放数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsProvideDrawID","申领ID","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsProvideOperateExplain","发放操作说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsProvideOperateDate","发放日期","DATE",false));

addSchemaColumn(new schemaColumn("vsProvideCalcUnitQuantity","计量单位发放数量","INTEGER",true));

addSchemaColumn(new schemaColumn("vsProvideSettledFlag","是否结算","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsProvideSettleAmount","结算金额","DECIMAL(10,4)",true));

addSchemaColumn(new schemaColumn("vsProvideOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsProvideInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsProvideInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsProvideRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsProvideFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsRecycleRecycleID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsRecycleVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsRecycleVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsRecycleVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsRecyclePressBatchNo","批次","CHAR(16)",true));

addSchemaColumn(new schemaColumn("vsRecycleStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsRecycleEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsRecycleQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsRecycleSubmitType","回收提交人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsRecycleSubmitCode","回收提交人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsRecycleSubmitName","回收提交人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsRecycleConfirmType","回收确认人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsRecycleConfirmCode","回收确认人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsRecycleConfirmName","回收确认人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsRecycleApproverCode","回收审批人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsRecycleApproverName","回收审批人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsRecycleHandlerCode","回收经办人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsRecycleHandlerName","回收经办人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsRecycleOperatorSubmitCode","回收提交操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsRecycleOperatorSubmitName","回收提交操作员名称","CHAR(30)",false));

addSchemaColumn(new schemaColumn("vsRecycleSubmitDate","回收提交日期","DATE",false));

addSchemaColumn(new schemaColumn("vsRecycleRecycleExplain","回收提交说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsRecycleOperatorConCode","回收确认操作员代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsRecycleOperatorConName","回收确认操作员名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsRecycleConfirmDate","回收确认日期","DATE",true));

addSchemaColumn(new schemaColumn("vsRecycleInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsRecycleInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsRecycleVisaStatus","单证状态","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsRecycleTempStopFlag","是否暂时停用标志","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsRecycleRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsRecycleFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsExhaustExhaustID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsExhaustOperateStatus","操作代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsExhaustVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsExhaustVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsExhaustVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsExhaustStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsExhaustEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsExhaustQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsExhaustUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsExhaustUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsExhaustUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsExhaustApproverCode","审批人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsExhaustApproverName","审批人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsExhaustHandlerCode","经办人代码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsExhaustHandlerName","经办人名称","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsExhaustOperateExplain","操作说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsExhaustOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsExhaustInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsExhaustInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsExhaustRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsExhaustFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsCancelCancelID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsCancelVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCancelVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsCancelVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCancelPressBatchNo","批次","CHAR(16)",true));

addSchemaColumn(new schemaColumn("vsCancelStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsCancelEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsCancelQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsCancelCancelerType","核销人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCancelCancelerCode","核销人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCancelCancelerName","核销人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsCancelAccepterType","领用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCancelAccepterCode","领用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCancelAccepterName","领用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsCancelSubmitDate","核销日期","DATE",false));

addSchemaColumn(new schemaColumn("vsCancelCancelExplain","核销说明","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsCancelOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsCancelInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsCancelInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsCancelRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsCancelFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsSettleSettleID","结算ID","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsSettleProvideID","发放ID","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsSettleVisaCode","单证代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsSettleVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsSettleStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsSettleEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsSettleQuantity","结算数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsSettleSettleUnitType","结算人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsSettleSettleUnitCode","结算人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsSettleSettleUnitName","结算人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsSettleSettleAmount","结算金额","DECIMAL(10,4)",false));

addSchemaColumn(new schemaColumn("vsSettleSettleDate","结算日期","DATE",false));

addSchemaColumn(new schemaColumn("vsSettleFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsFindFindID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsFindVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsFindVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsFindStartSerialNo","起始流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsFindEndSerialNo","终止流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsFindQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsFindUserType","丢失找回登记人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsFindUserCode","丢失找回登记人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsFindUserName","丢失找回登记人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsFindOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsFindInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsFindInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsFindRemark","备注","CHAR(255)",true));

addSchemaColumn(new schemaColumn("vsFindFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsDossierDossierNo","档案号","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsDossierVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsDossierVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsDossierStartSerialNo","起始流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsDossierEndSerialNo","终止流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsDossierQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsDossierUserType","归档登记人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsDossierUserCode","归档登记人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsDossierUserName","归档登记人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsDossierOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsDossierInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsDossierInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsDossierRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsDossierFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsStorageSubVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStorageSubPressBatchNo","批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsStorageSubStartDate","起始日期","DATE",true));

addSchemaColumn(new schemaColumn("vsStorageSubEndDate","终止日期","DATE",false));

addSchemaColumn(new schemaColumn("vsStorageSubLowPrice","单据金额下限","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsStorageSubUpPrice","单据金额上限","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsStorageSubCollateFlag","财务对帐标志","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsStorageSubTransFlag","提取标志","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsStorageSubFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsMarkSubVisaPre","单证号字冠","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsMarkSubVisaSerialNo","单证流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsMarkSubVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkSubCodePassword","密码","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsMarkSubStartDate","起始日期","DATE",true));

addSchemaColumn(new schemaColumn("vsMarkSubEndDate","终止日期","DATE",false));

addSchemaColumn(new schemaColumn("vsMarkSubConfirmTime","确认时间","DATE",true));

addSchemaColumn(new schemaColumn("vsMarkSubLowPrice","单据金额下限","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsMarkSubUpPrice","单据金额上限","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsMarkSubAgentType","发放对象类型","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsMarkSubAgentCode","发放对象代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsMarkSubAgentName","发放对象名称","CHAR(120)",true));

addSchemaColumn(new schemaColumn("vsMarkSubCollateFlag","财务对帐标志","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsMarkSubTransFlag","提取标志","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsMarkSubFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsCollateCollateID","对帐ID","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsCollateRecycleID","回收ID","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsCollateVisaCode","单证代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCollateVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsCollateStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsCollateEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsCollateQuantity","对帐数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsCollateCollateUnitType","对帐人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCollateCollateUnitCode","对帐人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCollateCollateUnitName","对帐人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsCollateCollateAmount","对帐金额","DECIMAL(10,4)",false));

addSchemaColumn(new schemaColumn("vsCollateCollateDate","对帐日期","DATE",false));

addSchemaColumn(new schemaColumn("vsCollateFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsRecyclesubRecycleID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsRecyclesubVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsRecyclesubRecycleAmount","退卡金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsRecyclesubRecycleFee","退卡费用","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsRecyclesubVisaAmount","单证金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsRecyclesubFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsAgHandOverHandOverID","交接序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverHandOverType","交接状态","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverVisaCode","单证代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsAgHandOverVisaStatus","状态标志","CHAR(2)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverSubmitType","提交人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverSubmitCode","提交人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverSubmitName","提交人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverAccepterType","接受人类型","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverAccepterCode","接受人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverAccepterName","接受人名称","CHAR(120)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverApproverCode","提交经办人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverApproverName","提交经办人名称","CHAR(120)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverHandlerCode","接受经办人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverHandlerName","接受经办人名称","CHAR(120)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverConfirmCode","确认人代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverConfirmDate","确认日期","DATE",true));

addSchemaColumn(new schemaColumn("vsAgHandOverOperatorCode","操作员代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsAgHandOverInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsAgHandOverInputTime","操作时间","DATE",false));

addSchemaColumn(new schemaColumn("vsAgHandOverRemark","备注","CHAR(200)",true));

addSchemaColumn(new schemaColumn("vsAgHandOverFlag","标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsCodeCompanyVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyVisaName","单证类型名称","VARCHAR2(50)",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyComCode","机构代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyComName","机构名称","VARCHAR2(60)",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyOperatorCode","操作员代码","VARCHAR2(10)",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyOperateDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsCodeCompanyFlag","标志位","CHAR(2)",true));



addSchemaColumn(new schemaColumn("vsCodeSetVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsCodeSetVisaName","单证类型名称","CHAR(60)",true));

addSchemaColumn(new schemaColumn("vsCodeSetRiskCode","险种代码","CHAR(4)",false));

addSchemaColumn(new schemaColumn("vsCodeSetCertiType","单证类型","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsCodeSetComCode","机构代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsCodeSetComName","机构名称","CHAR(60)",true));

addSchemaColumn(new schemaColumn("vsCodeSetRationType","定额类型","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsCodeSetValidStatus","效力状态","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsCodeSetOperatorCode","操作员代码","VARCHAR2(10)",false));

addSchemaColumn(new schemaColumn("vsCodeSetOperateDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsCodeSetFlag","标志位","CHAR(2)",true));



addSchemaColumn(new schemaColumn("vsPrinteryPrinteryCode","印刷厂代码","CHAR(8)",false));

addSchemaColumn(new schemaColumn("vsPrinteryPrinteryName","印刷厂名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsPrinteryPostAddress","地址","CHAR(40)",false));

addSchemaColumn(new schemaColumn("vsPrinteryPostCode","邮编","CHAR(6)",true));

addSchemaColumn(new schemaColumn("vsPrinteryLinkerName","联系人","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsPrinteryPhoneNumber","联系电话","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsPrinteryValidStatus","当前状态","CHAR(3)",false));

addSchemaColumn(new schemaColumn("vsPrinteryRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsPrinteryOperatorCode","操作员代码","VARCHAR2(10)",false));

addSchemaColumn(new schemaColumn("vsPrinteryOperateDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsPrinteryFlag","标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsLevelUnitType","级别单位类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsLevelUnitCode","级别单位代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsLevelUnitName","级别单位名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsLevelComCode","归属部门代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsLevelComName","归属部门名称","CHAR(60)",false));

addSchemaColumn(new schemaColumn("vsLevelLevelNo","级别代码","INTEGER",false));

addSchemaColumn(new schemaColumn("vsLevelManageLevel","可管理级别数","INTEGER",false));

addSchemaColumn(new schemaColumn("vsLevelValidStatus","当前状态","CHAR(3)",false));

addSchemaColumn(new schemaColumn("vsLevelRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsLevelFlag","标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsLimitVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsLimitVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsLimitUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsLimitUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsLimitUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsLimitProvideLimit","单证发放数量限制","INTEGER",true));

addSchemaColumn(new schemaColumn("vsLimitProvidedCount","已发放单证数量","INTEGER",true));

addSchemaColumn(new schemaColumn("vsLimitOperatorCode","操作员代码","CHAR(10)",false));

addSchemaColumn(new schemaColumn("vsLimitInputDate","操作日期","DATE",false));

addSchemaColumn(new schemaColumn("vsLimitInputTime","操作时间","Datetime",true));

addSchemaColumn(new schemaColumn("vsLimitRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsLimitFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsProvideSubProvideID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsProvideSubAgentType","发放最终对象类型","CHAR(1)",true));

addSchemaColumn(new schemaColumn("vsProvideSubAgentCode","发放对象代码","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsProvideSubAgentName","发放对象名称","CHAR(120)",true));

addSchemaColumn(new schemaColumn("vsProvideSubVisaAmount","单据金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsProvideSubFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsMarkHisVisaPre","单证号字冠","CHAR(10)",true));

addSchemaColumn(new schemaColumn("vsMarkHisVisaSerialNo","单证流水号","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsMarkHisVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkHisVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsMarkHisPressBatchNo","印刷批次","CHAR(16)",false));

addSchemaColumn(new schemaColumn("vsMarkHisBusinessNo","业务号","CHAR(30)",true));

addSchemaColumn(new schemaColumn("vsMarkHisVisaAmount","单据金额","DECIMAL(15,3)",true));

addSchemaColumn(new schemaColumn("vsMarkHisUseDate","使用日期","DATE",false));

addSchemaColumn(new schemaColumn("vsMarkHisUserType","使用人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsMarkHisUserCode","使用人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkHisUserName","使用人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsMarkHisVisaStatus","单证状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsMarkHisProvideTimes","发放次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsMarkHisRecycleTimes","回收次数","INTEGER",true));

addSchemaColumn(new schemaColumn("vsMarkHisVerifiedCancelFlag","是否核销标志","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsMarkHisBeforeStatus","丢失前状态","CHAR(20)",true));

addSchemaColumn(new schemaColumn("vsMarkHisRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsMarkHisFlag","备用标志","CHAR(10)",true));



addSchemaColumn(new schemaColumn("vsStatusTransTransID","序号","CHAR(24)",false));

addSchemaColumn(new schemaColumn("vsStatusTransVisaCode","单证类型代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStatusTransVisaName","单证类型名称","CHAR(50)",false));

addSchemaColumn(new schemaColumn("vsStatusTransVisaKind","单证种类","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsStatusTransStartSerialNo","起始流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsStatusTransEndSerialNo","终止流水号","CHAR(50)",true));

addSchemaColumn(new schemaColumn("vsStatusTransQuantity","数量","INTEGER",false));

addSchemaColumn(new schemaColumn("vsStatusTransUserType","状态转换人类型","CHAR(1)",false));

addSchemaColumn(new schemaColumn("vsStatusTransUserCode","状态转换人代码","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStatusTransUserName","状态转换人名称","CHAR(120)",false));

addSchemaColumn(new schemaColumn("vsStatusTransUseDate","状态转换日期","DATE",false));

addSchemaColumn(new schemaColumn("vsStatusTransOldStatus","转换前状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStatusTransVisaStatus","转换后状态","CHAR(20)",false));

addSchemaColumn(new schemaColumn("vsStatusTransRemark","备注","VARCHAR(255)",true));

addSchemaColumn(new schemaColumn("vsStatusTransFlag","备用标志","CHAR(10)",true));



