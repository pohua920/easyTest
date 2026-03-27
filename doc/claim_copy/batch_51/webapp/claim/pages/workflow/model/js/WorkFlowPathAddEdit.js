 /*****************************************************************************
  * DESC       ：增加工作流模板路径JS
  * AUTHOR     ：weishixin
  * CREATEDATE ： 2004-08-01
  * MODIFYLIST ：   Name       Date            Reason/Contents
  *          ------------------------------------------------------
  ****************************************************************************/
 /**
  *@description 装入父界面的数据
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function loadForm() {
 	var pageCode = "";
 	var pageCodeData = "";
 	var recordCount = 0; //记录序号
 	setNodeOption();
 	//得到主界面的记录序号
 	recordCount = dialogArguments.fm.WorkFlowPathRecordOrder.value;
 	fm.WorkFlowPathRecordOrder.value = dialogArguments.fm.WorkFlowPathRecordOrder.value;

 	//得到界面的代码
 	pageCode = dialogArguments.fm.WorkFlowPathPageID.value
 	fm.WorkFlowPathPageID.value = dialogArguments.fm.WorkFlowPathPageID.value;
 	pageCodeData = pageCode + "_Data";
 	getMainFieldValue(pageCode, recordCount);
 	return true;
 }
 /**
  *@description 设置开始节点的option选项
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function setNodeOption() {
 	var optionString = ""; //option串
 	var nodeCount = 0; //节点个数
 	nodeCount = dialogArguments.document.getElementsByName("swfNodeNodeNo").length;
 	for (var i = 1; i < nodeCount; i++) {

 		if (i == nodeCount - 1) {
 			optionString = optionString + dialogArguments.fm.swfNodeNodeNo[i].value + FIELD_SEPARATOR + dialogArguments.fm.swfNodeNodeNo[i].value + "-" + dialogArguments.fm.swfNodeNodeName[i].value;
 		} else {
 			optionString = optionString + dialogArguments.fm.swfNodeNodeNo[i].value + FIELD_SEPARATOR + dialogArguments.fm.swfNodeNodeNo[i].value + "-" + dialogArguments.fm.swfNodeNodeName[i].value + GROUP_SEPARATOR;
 		}
 	}
 	setOption("swfPathStartNodeNo", optionString);
 	setOption("swfPathEndNodeNo", optionString);
 }
 /**
  *@description 设置开始节点名称选项
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function resetStartNodeName() {
 	var startNodeStr = fm.swfPathStartNodeNo.value;
 	var nodeName = "";
 	//以下得到名称
 	var nodeCount = dialogArguments.document.getElementsByName("swfNodeNodeNo").length;
 	for (var i = 1; i < nodeCount; i++) {
 		if (dialogArguments.fm.swfNodeNodeNo[i].value == startNodeStr) {
 			nodeName = dialogArguments.fm.swfNodeNodeName[i].value;
 		}
 	}
 	fm.swfPathStartNodeName.value = nodeName;
 	fm.swfPathPathName.value = "从" + nodeName + "到" + fm.swfPathEndNodeName.value;

 }

 /**
  *@description 设置开始节点名称选项
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function resetEndNodeName() {
 	var endNodeStr = fm.swfPathEndNodeNo.value;
 	var nodeName = "";
 	var nodeCount = dialogArguments.document.getElementsByName("swfNodeNodeNo").length;
 	for (var i = 1; i < nodeCount; i++) {
 		if (dialogArguments.fm.swfNodeNodeNo[i].value == endNodeStr) {
 			nodeName = dialogArguments.fm.swfNodeNodeName[i].value;
 		}
 	}
 	fm.swfPathEndNodeName.value = nodeName;
 	fm.swfPathPathName.value = "从" + fm.swfPathStartNodeName.value + "到" + nodeName;

 }
 /**
  *@description 取得主窗体界面上输入域的值
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function getMainFieldValue(DataPageCode, index) {
 	var i = 0;
 	var name = "";
 	var elements;
 	var fieldName = "";
 	var countLoss = 0; //人员费用的记录数
 	var firstIndexLoss = 0; //本人员的费用的起始index

 	//index--; //顺序改为以0开始
 	//得到Input域的名字
 	elements = getMainTableElements(DataPageCode);
 	index = index - 1;

 	for (i = 0; i < elements.length; i++) {
 		//得到人员费用索引起始位置及人员费用的记录数
 		if (elements[i].name == "personSerialNo") {
 			if (elements[i].value == index) {

 				countLoss = countLoss + 1;
 				if (firstIndexLoss == 0) {
 					firstIndexLoss = getMainElementOrder(elements[i]);
 				}
 			}
 		}
 		command = "document.getElementsByName('" + elements[i].name + "')[0].value = dialogArguments.document.getElementsByName('" + elements[i].name + "')[" + index + "].value;";
 		eval(command);
 	}

 }

 /**
  * 得到Table的所有元素
  * @param tableId 表名称
  * @return table的所有元素
  */
 function getMainTableElements(tableId) {
 	var i = 0;
 	var elements = new Array();
 	var tempElements = null;
 	var tbody;
 	var index = 0;
 	var tbodies = dialogArguments.document.getElementById(tableId).tBodies;
 	for (i = 0; i < tbodies.length; i++) {
 		tbody = tbodies.item(i);
 		tempElements = tbody.getElementsByTagName("INPUT"); //加入INPUT域

 		for (i = 0; i < tempElements.length; i++) {
 			if (tempElements[i].type == "button") {
 				//去掉button alert("button");
 			} else {
 				elements[index++] = tempElements[i];
 			}
 		}

 		tempElements = tbody.getElementsByTagName("SELECT"); //加入SELECT域
 		for (i = 0; i < tempElements.length; i++) {
 			elements[index++] = tempElements[i];
 		}

 		tempElements = tbody.getElementsByTagName("TEXTAREA"); //加入TEXTAREA域
 		for (i = 0; i < tempElements.length; i++) {
 			elements[index++] = tempElements[i];
 		}

 	}
 	return elements;
 }


 /**
  *@description 设置主窗体界面上输入域的值
  *@param       无
  *@return      通过返回true,否则返回false
  */
 function setMainFieldValue(dataPageCode, index) {
 	var i = 0;
 	var name = "";
 	var elements;

 	//index--; //顺序改为以0开始
 	//得到Input域的名字
 	elements = getMainTableElements(dataPageCode);
 	var textContext = "";
 	//index = index ;
 	for (i = 0; i < elements.length; i++) {
 		command = "dialogArguments.document.getElementsByName('" + elements[i].name + "')[" + index + "].value = document.getElementsByName('" + elements[i].name + "')[0].value ;";

 		eval(command);
 	}

 }

 /**
  * 将模式窗口中的数据保存到主界面
  */
 function saveModelForm() {
	 if (fm.swfPathDefaultFlag.value == "") {
	 		alert("請選擇缺省路徑");
	 		return false;
	 	}
	 	if (fm.swfPathPriority.value == "") {
	 		alert("請填寫優先級");
	 		return false;
	 	}
	 	if (fm.swfPathStartNodeNo.value == "") {
	 		alert("請選擇起始節點");
	 		return false;
	 	}
	 	if (fm.swfPathEndNodeNo.value == "") {
	 		alert("請選擇終止節點");
	 		return false;
	 	}
 	var pageCode = fm.WorkFlowPathPageID.value;
 	var dataPageCode = pageCode + "_Data";
 	var index = fm.WorkFlowPathRecordOrder.value;
 	setMainFieldValue(dataPageCode, index - 1);
 	window.close();
 }