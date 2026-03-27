//
//Generate By
//
//JTools (Version 1.0.0.1)
//(C) Copyright Zhouxianli 2003.  All rights reserved.
//Visit http://www.jtools.org
//
//Any qustion mail zhouxianli@126.com
//Generate at 2003-10-16 10:37:55
//
//
/**

 * 基本數據校驗

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: Sinosoft</p>

 * @author 中科软

 * @version 1.0

 */
/**

    結構爲 字段名，字段描述，類型，是否允許爲空

           4項都是必需的

 */
//初始化數組
var columnIndex = 0;

//標準數據模板

//PrpinsDB標準校驗數據 .DO NOT EDIT THIS



var schemaColumns = new Array(); //存儲字段結構的數組



function schemaColumn(name, desc, dataType, allowNulls)

{

	this.name = name;

	this.desc = desc;

	this.dataType = dataType;

	this.allowNulls = allowNulls;

}



function getSchemaColumn(name)

{

	for (var i = 0; i < schemaColumns.length; i++)

	{

		if (name == schemaColumns[i].name)

			return schemaColumns[i];

	}

	return null;

}



schemaColumns[columnIndex++] = new schemaColumn("wfModelMainModelNo", "模板編號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainModelName", "模板名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainAuthorCode", "創建者", "VARCHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainRightId", "權限", "VARCHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainCreateDate", "創建日期", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainModifyDate", "修改日期", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainModelType", "模板類型", "CHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainModelAttr", "模板屬性", "CHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("odelStatus", "模板狀態", "CHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainCloseService", "CloseService", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainActiveService", "ActiveService", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelMainFlag", "標志位", "CHAR(2)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfNodeModelNo", "模板編號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeModeName", "模板名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeNodeNo", "節點編號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeNodeName", "節點名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeTimeLimit", "處理時限", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("ndFlag", "結束標志", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeCriterion", "處理要求", "VARCHAR(255)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeFlag", "備用標志", "CHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeTaskNo", "任務編號", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("fNodeTaskType", "任務類型", "CHAR(3)", true);

schemaColumns[columnIndex++] = new schemaColumn("odeType", "節點類型", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeUnitCode", "辦理部門", "VARCHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeUnitName", "部門名稱", "VARCHAR(40)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeHandlerCode", "辦理人員編碼", "VARCHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodeHandlerName", "辦理人員名稱", "VARCHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodePosX", "PosX", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("wfNodePosY", "PosY", "INTEGER", true);



schemaColumns[columnIndex++] = new schemaColumn("wfPathModelNo", "模板編號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathModelName", "模板名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathPathNo", "程邊號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathPathName", "路徑名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathStartNodeNo", "起始節點", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathStartNodeName", "起始節點名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathEndNodeNo", "終止節點", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathEndNodeName", "終止節點名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathPriority", "優先級別", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathDefaultFlag", "是否缺省值", "CHAR(1)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathFlag", "備用標志", "CHAR(10)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathConditionStatus", "是否存在流轉條件", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathForwardServices", "正向流轉所調用服務名", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathBackwardServices", "逆向流轉所調用服務名", "CHAR(20)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfConditionModelNo", "模板編碼", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionPathNo", "流程邊編碼", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionConditionNo", "條件編碼", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionSerialNo", "序號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionValidStatus", "是否有效標", "CHAR(1)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionConfigType", "配置類型標志", "CHAR(1)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionConfigText", "配置描述", "VARCHAR(255)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionBusinessKey", "業務鍵值", "CHAR(18)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionDBName", "資料庫名稱", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionTableName", "表名", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionDataType", "資料欄位數據類型", "VARCHAR(255)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionColumnName", "資料欄位名", "CHAR(20)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionColumnDesc", "資料欄位描述", "VARCHAR(60)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionOperator", "運算符", "CHAR(30)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionValue", "比較值", "VARCHAR(255)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfConditionFlag", "標志資料欄位", "CHAR(10)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainFlowID", "流程編號", "CHAR(22)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainFlowName", "流程名稱", "VARCHAR(30)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainStatus", "該流程的狀態", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainCreatDate", "創建該流程的時間", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainModelNo", "模板號", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("wfFlowMainFlag", "標志位", "CHAR(2)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfLogFlowID", "流程編號", "CHAR(22)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfLogLogNo", "序號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfLogModelNo", "模板號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfLogNodeNo", "當前節點號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfLogNodeName", "當前節點名稱", "VARCHAR(100)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfLogBusinessNo", "業務號", "VARCHAR(22)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogHandleDept", "處理部門", "CHAR(10)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogHandlerCode", "處理人員代碼", "CHAR(10)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogHandlerName", "處理人員名稱", "VARCHAR(30)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogFlowInTime", "流入時間", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogTimeLimit", "處理時限", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogHandleTime", "處理時間", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogSubmitTime", "提交時間", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogNodeStatus", "節點狀態", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogFlowStatus", "流狀態", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogPackageID", "明細信息包ID", "CHAR(16)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogFlag", "備用標志", "CHAR(10)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogTaskNo", "任務編號", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogTaskType", "任務類型", "CHAR(3)", true);

schemaColumns[columnIndex++] = new schemaColumn("askType", "任務類型", "CHAR(3)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogNodeType", "節點類型", "CHAR(1)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogTitleStr", "任務備注", "VARCHAR(255)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogBusinessType", "業務類型", "CHAR(4)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogRiskCode", "險種代碼", "CHAR(3)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogKeyIn", "任務接收載體鍵值", "VARCHAR(22)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogKeyOut", "任務發送載體鍵值", "VARCHAR(22)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogDeptName", "部門名稱", "VARCHAR(40)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogSubFlowID", "子流程編號", "CHAR(22)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogMainFlowID", "主流程編號", "CHAR(22)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogPosX", "節點X坐標", "INTEGER", true);

schemaColumns[columnIndex++] = new schemaColumn("wfLogPosY", "節點Y坐標", "INTEGER", true);



schemaColumns[columnIndex++] = new schemaColumn("wfPackagePackageID", "工作流信息包ID", "CHAR(16)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPackageDetailNo", "明細項序號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPackageDetailContent", "明細項內容", "VARCHAR(76)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPackageFlag", "備用標志", "CHAR(10)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfPathLogFlowID", "流程編號", "CHAR(22)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogPathNo", "流程邊號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogModelNo", "模板號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogPathName", "路徑名稱", "VARCHAR(100)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogStartNodeNo", "起始節點", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogStartNodeName", "起始節點名稱", "VARCHAR(100)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogEndNodeNo", "終止節點", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogEndNodeName", "終止節點名稱", "VARCHAR(100)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogFlowInTime", "流入時間", "DATE", true);

schemaColumns[columnIndex++] = new schemaColumn("wfPathLogFlag", "標志位", "CHAR(2)", true);



schemaColumns[columnIndex++] = new schemaColumn("wfModelUseModelNo", "模板編號", "INTEGER", false);

schemaColumns[columnIndex++] = new schemaColumn("wfModelUseRiskCode", "險種代碼", "CHAR(3)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfModelUseComCode", "部門編碼", "CHAR(10)", false);

schemaColumns[columnIndex++] = new schemaColumn("wfModelUseModelStatus", "模板狀態", "CHAR(2)", true);

schemaColumns[columnIndex++] = new schemaColumn("wfModelUseFlag", "標志", "CHAR(2)", true);
