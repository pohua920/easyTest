/*****************************************************************************
 * DESC       ：损失部位脚本函数
 * AUTHOR     ：理赔项目组
 * CREATEDATE ：2005-04-08
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
//损失部位模块合到涉案车辆信息中，相应模块做调整
//损失部位变化後,损失部位名称也跟着改变

function getPartName(field) {
	var index = parseInt(getElementOrder(field)) - 1;
	var list = field;
	fm.partName[index].value = list.options[list.selectedIndex].text;
}


//以下打开零件代码页面

function openCompCodeWin(PageCode, Field) {
	var index = parseInt(getElementOrder(Field)) - 1;
	var partCode = fm.partCode(index).value;
	var pageUrl = "/claim/registGetCompCode.do?strIndex=" + index + "&partCode=" + partCode;
	window.open(pageUrl, "openCompCodeWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
}

//取得零件代码与名称

function getCompCode(field) {
	var compCodeName = field;
	var index = compCodeName.indexOf("-");
	var compCode = compCodeName.substring(0, index);
	var compName = compCodeName.substring(index + 1);

	var index1 = fm.txtIndex.value;
	var PreviousFlag = fm.txtPreviousFlag.value;

	//修理项目清单模块
	if (PreviousFlag == "repairFee") {
		window.opener.fm.prpLrepairFeeCompName[index1].value = compName;
		window.opener.fm.prpLrepairFeeCompCode[index1].value = compCode;
		window.close();
	} else if (PreviousFlag == "component") {
		window.opener.fm.prpLcomponentCompName[index1].value = compName;
		window.opener.fm.prpLcomponentCompCode[index1].value = compCode;
		window.close();
	} else {
		window.opener.fm.compName[index1].value = compName;
		window.opener.fm.compCode[index1].value = compCode;
		window.close();
	}
}

//取得其它损失代码

function getLossItemCode(field) {
	var LossItemCodeName = field;
	var index = LossItemCodeName.indexOf("-");
	var lossItemCode = LossItemCodeName.substring(0, index);
	var LossItemName = LossItemCodeName.substring(index + 1);

	var index1 = fm.txtIndex.value;
	window.opener.fm.prpLthirdLossItemCode[index1].value = lossItemCode;
	window.opener.fm.prpLthirdLossItemName[index1].value = LossItemName;
	window.close();
}


/**
 * 设置setPrpLthirdCarLossItemNo
 */

function setPrpLthirdCarLossItemNo() {
	var count = getElementCount("prpLthirdCarLossItemNo");
	for (var i = 0; i < count; i++) {
		if (count != 1) {
			fm.prpLthirdCarLossItemNo[i].value = i;
		}
	}
}


/**
 * 车牌号码变化时，触发该事件
 */

function getCarLossLicenseNo(field) {
	var index = 0; //当前table索引
	index = getElementOrder(field) - 1;

	//给车牌号码赋值
	var count = getElementCount("prpLthirdCarLossLicenseNo");
	for (var j = 1; j < count; j++) {
		if (fm.RelateSerialNo[j].value == index) {
			fm.prpLthirdCarLossLicenseNo[j].value = fm.prpLthirdPartyLicenseNo[index].value;
			fm.prpLthirdCarLossLicenseNo[j].readOnly = true;
		}
	}
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	try{
		prpLthirdPartyLicenseNoRebulid();
	}catch(e){} 
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
	return true;
}
/***
 * 车辆损失添加之前判断
  */
function beforeInsertThirdCarLoss(pageCode,pageCode_Data,btnField,csFieldName,psFieldName){
	var p = findParents(btnField,"prpLthirdPartyLicenseNo");
	if(p!=null && p != undefined){
		var $p = $(p);
		var prpLthirdPartyLicenseNo = $p.find(":input[name='prpLthirdPartyLicenseNo']").val();
		var prpLthirdPartyDutyPercent = $p.find(":input[name='prpLthirdPartyDutyPercent']").val();
		if (prpLthirdPartyLicenseNo == null || prpLthirdPartyLicenseNo.length == 0) {
		errorMessage("牌照號碼不能為空!");
		return false;
	}
	var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
		if (prpLthirdPartyLicenseNo.search(re) == -1) {
		alert("輸入的牌照號碼格式不正確");
		return false;
	}
		if (prpLthirdPartyDutyPercent == null || prpLthirdPartyDutyPercent.length == 0) {
		errorMessage("对本涉案车责任比例不能为空!");
		return false;
	}
		return true;
	}
	return false;
}
/***
 * 添加损失块后
 * @param ThirdCarLossObject
 */
function afterInsertThirdCarLoss(ThirdCarLossObject,pageCode,btnField,csFieldName,psFieldName){
	var p = findParents(btnField,"prpLthirdPartyLicenseNo");//找到所属父块涉案车
	if(p!=null && p != undefined){
		var $p = $(p);
		var prpLthirdPartyLicenseNo = $p.find(":input[name='prpLthirdPartyLicenseNo']").val();
		$(ThirdCarLossObject).find(":input[name='prpLthirdCarLossLicenseNo']").val(prpLthirdPartyLicenseNo);
	}
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */
//得到当前的行所在当前页位置
function getRowCurrPage(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0; //人员费用的记录数
	var firstIndexLoss = 0; //本人员的费用的起始index
	var recentRowNo = 0;
	var orderCurrent = 0; //当前行所在当前面的位置
	recentRowNo = parseInt(getElementOrder(field));
	var RelateSerialNo = 0;
	RelateSerialNo = parseInt(fm.RelateSerialNo(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("ThirdParty");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "RelateSerialNo") {

			if (parseInt(elements[i].value) == RelateSerialNo) {
				countLoss = countLoss + 1;
				if (firstIndexLoss == 0) {
					firstIndexLoss = getElementOrder(elements[i]);
				}
			}
		}
	}

	orderCurrent = recentRowNo - firstIndexLoss;
	return orderCurrent;
}
//Modify by chenrenda add begin 20050408



/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */

function insertRowOfThirdProp(pageCode, dataPageCode, field) {

	var index = 0; //当前table索引
	var elements = null;
	var oTBODY = document.getElementById(pageCode).tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");
	for (var i = 0; i < oTBODYData.rows.length; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}

	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "RelateSerialNo") {
			elements[i].value = index;
		}
	}
	//给损失项目序号赋值,並给车牌号码赋值
	var count = getElementCount("prpLthirdPropItemNo");
	var k = 0;
	for (var j = 1; j < count; j++) {

		fm.prpLthirdPropItemNo[j].value = j;
	}
	return true;
}