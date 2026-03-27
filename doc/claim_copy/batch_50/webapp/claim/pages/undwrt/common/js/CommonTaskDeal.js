/***************************************************************************
 * Description: 处理核保任务js
 * Author     : 理赔组
 * CreateDate : 2013-4-1 14:28
 * UpdateLog  ： Name       Date            Reason/Contents
 *
 ****************************************************************************/
/**查看保单**/
function policyPrint() {
	var BizNo = fm.PolicyNo.value;
	var vPrpallUrl = fm.prpallUrl.value;
	//var BizNo="801012007119993000008" ;
	var messagedo = vPrpallUrl + "commonship/pub/UIEditReportPrint.jsp?BizNo=" + BizNo;
	win = window.open(messagedo, "",
		"status=no,resizable=no,scrollbars=yes,width=600,Height=600,left=200,top=70");
}
/**检验一项任务是否可以被处理**/
function checkTask(i) {
	//获得指定表单域的信息
	fm.iFlowID.value = fm.FlowID[i].value;
	fm.iLogNo.value = fm.LogNo[i].value;
	fm.iBusinessNo.value = fm.BusinessNo[i].value;
	fm.iBusinessType.value = fm.BusinessType[i].value;
	fm.iContractNo.value = fm.ContractNo[i].value;
	fm.iPackageID.value = fm.PackageID[i].value;
	fm.iModelNo.value = fm.ModelNo[i].value;
	fm.iNodeNo.value = fm.NodeNo[i].value;
	fm.iFlowStatus.value = fm.FlowStatus[i].value;
	fm.iDeptCode.value = fm.DeptCode[i].value;
	fm.iFlowInTime.value = fm.FlowInTime[i].value;
	fm.iNodeStatus.value = fm.NodeStatus[i].value;
	fm.iRiskCode.value = fm.RiskCode[i].value;
	var iFlowID = fm.FlowID[i].value;
	fm.action = "/claim/CommonCheckTask.do?iFlowID=" + iFlowID + "&strBusinessNo=" + fm.iBusinessNo.value;
	fm.method = "post";
	fm.submit();
}

/**显示业务的详细信息**/
function showBusinessInfo() {
	var vBusinessNo = fm.hiBusinessNo.value; //取业务号
	var vCertiType = fm.hiBusinessType.value; //取业务类型
	var vBizType = "";
	var vRiskCode = fm.riskCode.value; //取险种代码
	var vRiskClass = fm.classCode.value; //得到险类
	var vUserCode = fm.OperatorCode.value; //得到人员
	var vRiskCodeTemp = vRiskCode;
	//add by zhulei 20060426 增加登陆机构传参myComCode
	var vMyComCode = fm.myComCode.value;

	if (vCertiType == "T" || vCertiType == "P" || vCertiType == "E") {
		if (vCertiType == "T") vBizType = "PROPOSAL";
		else if (vCertiType == "P") vBizType = "POLICY";
		else if (vCertiType == "E") vBizType = "ENDORSE";
		//xiaojian_leave：通用险种临时处理
		if ("1303,1304,1305,1801,1907".indexOf(vRiskCode) > -1) {
			vRiskCodeTemp = "00Z1";
		}
		//add by zhulei 20060329 通用险种临时处理
		if ("0207,1302".indexOf(vRiskCode) > -1) {
			vRiskCodeTemp = "00Q1";
		}
		var vURL = '/prpall/' + vRiskCodeTemp + '/tbcbpg/UIPrPoEn' + vRiskCodeTemp + 'Show.jsp?BIZTYPE=' + vBizType + '&SHOWTYPE=SHOW&BizNo=' + vBusinessNo + '&RiskCode=' + vRiskCode + '&UserCode=' + vUserCode + '&myComCode=' + vMyComCode;
		window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "C") {
		/*核赔实赔信息*/
		var vURL = "";

		vURL = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + vBusinessNo + "&editType=SHOW&riskCode=" + vRiskCode + "&paramUndwrtCompe=DAA";

		/*
    if(vRiskClass == "J" || vRiskClass == "G"||vRiskClass == "B"||vRiskClass == "T"||vRiskClass=="I"||vRiskClass=="S"||vRiskClass=="H"||vRiskClass=="L") vRiskClass = "Q";
    if(vRiskClass == "Q" || vRiskClass == "Z")
    {
      vURL = '/prpall/'+ "ClassCodeQ" + '/lp/compensate/UIL' + vRiskClass + 'CompensateShow.jsp?CompensateNo=' + vBusinessNo ;
    }
    else if (vRiskClass=="E")
    {
         vURL = '/prpall/'+ "ClassCodeE" + '/lp/compensate/UIL' + vRiskClass + 'CompensateShow.jsp?CompensateNo=' + vBusinessNo ;
    }
    else
    {
      if(vRiskCode=="DAS")
         vRiskCode="0501";
        vURL = '/prpall/'+ vRiskCode + '/lp/compensate/UIL' + vRiskCode + 'CompensateShow.jsp?CompensateNo=' + vBusinessNo ;
          }

    */
		window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "Y") {
		/*核赔实赔信息*/
		var vURL = "";

		vURL = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + vBusinessNo + "&editType=SHOW&riskCode=" + vRiskCode;

		/*
    if(vRiskClass == "Q" || vRiskClass == "Z" || vRiskClass == "J" || vRiskClass == "G" ||vRiskClass == "B"||vRiskClass == "T"||vRiskClass == "Y"||vRiskClass == "E"||vRiskClass == "S"||vRiskClass=="H"||vRiskClass=="I")
    {
      vURL = '/prpall/'+ "commonship" + '/lp/prepay/UILPrepayShow.jsp?PreCompensateNo=' + vBusinessNo ;
    }
    else
    {     //核赔预赔信息
      if(vRiskCode=="DAS")
       vRiskCode="0501";
      var vURL = '/prpall/'+ vRiskCode + '/lp/prepay/UIL' + vRiskCode + 'PrepayShow.jsp?PreCompensateNo=' + vBusinessNo ;
    }
    */
		window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else {
		errorMessage("无法确定详细信息！");
	}
}

//选择审批片语

function changeNotion(field) {
	var strNotion = field.value;
	if (isEmptyField(fm.HandleText) && !isEmptyField(field)) {
		fm.HandleText.value = strNotion;
	} else {
		if (!isEmptyField(field))
			fm.HandleText.value = fm.HandleText.value + "\n" + strNotion;
	}
}

//隐藏下发修改

function changeNotion1(field) {
	var strNotion = field.value;
	if (strNotion == "同意承保") {
		fm.submitJunior.style.display = "none";
	} else {
		fm.submitJunior.style.display = "";
	}

	if (isEmptyField(fm.HandleText) && !isEmptyField(field)) {
		fm.HandleText.value = strNotion;
	} else {
		if (!isEmptyField(field))
			fm.HandleText.value = fm.HandleText.value + "\n" + strNotion;
	}
}

//查看轨迹信息

function viewTranceInfo() {
	var submitStr;
	var BusinessNo;
	BusinessNo = fm.BusinessNo.value;
	//fm.target = "fraSubmit";
	submitStr = "/claim/CommonViewTrace.do?BusinessNo=" + BusinessNo;
	window.open(submitStr, '轨迹信息', 'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

//查看资料信息

function showMaterialInfo() {
	var submitStr;
	var BusinessNo;
	BusinessNo = fm.BusinessNo.value;
	//fm.target = "fraSubmit";
	submitStr = "/claim/materialInfo.do?BusinessNo=" + BusinessNo;
	window.open(submitStr, '资料信息', 'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//保存任务

function saveTask(field) {
	var flag = false;

	//需要拆分危险单位的险种的处理--Start
	if (fm.riskUnitFlag.value == '1' && fm.HandType.value == '11') {
		//delete by lihua 20060515 begin
		/* var flag1 = false;
    var flag2 = false;
    var flag3 = false;
    var flag4 = false;
    var flag5 = false;*/
		//delete by lihua 20060515 end
		var dangerNoCount = fm.dangerNo.length;
		var sPremium = 0;

		for (i = 1; i < dangerNoCount; i++) {
			sPremium += parseFloat(fm.premium[i].value);
		}

		sPremium = mathRound(parseFloat(sPremium));

		if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
			window.alert("拆分危險單位後的總保費與原保費不相等" + "\n" + "     原始保費爲" + parseFloat(fm.tolPremium.value) + "\n" +
					"    現在保費爲" + parseFloat(sPremium));
			return;
		} else {}
		//delete by lihua 20060515 begin
		/*
    if(fm.riskLevel[1].value!="")  //风险等级
    {
    fm.hiRiskLevel.value = fm.riskLevel[1].value;
    flag1 = true;
     }else if( fm.allRiskLevel.value!="" && flag1 == false )
    {
    fm.hiRiskLevel.value = fm.allRiskLevel.value;
    flag1 = true;
    }

    if(fm.retCurrency[1].value!= "")   //自留额币种
    {
     fm.hiRetCurrency.value = fm.retCurrency[1].value;
     flag2 = true;
    }else if( fm.allRetentionCurrency.value!="" && flag2 == false )
    {
     fm.hiRetCurrency.value = fm.allRetentionCurrency.value;
     flag2 = true;
    }

    if(fm.retentionValue[1].value!="" && parseFloat(fm.retentionValue[1].value)!=0)    //自留额金额
    {
     fm.hiRetentionValue.value = fm.retentionValue[1].value;
     flag3 = true;
    }else if( fm.allRetentionValue.value!="" && flag3 == false )
    {
     fm.hiRetentionValue.value = fm.allRetentionValue.value;
     flag3= true;
    }

   if(fm.dangerItemKind[1].value!="")     //标的类型
   {
    fm.hiDangerItemKind.value = fm.dangerItemKind[1].value;
    flag4= true;
   }else if(fm.itemKind.value!="" && flag4 == false )
   {
    fm.hiDangerItemKind.value = fm.itemKind.value;
    flag4 = true;
   }

   if(fm.riskLevelDesc[1].value!="")     //风险名称
   {
    fm.hiRiskLevelDesc.value = fm.riskLevelDesc[1].value;
    flag5 = true;
   }else if( fm.allRiskLevelDesc.value!="" && flag5 == false )
   {
    fm.hiRiskLevelDesc.value = fm.allRiskLevelDesc.value;
    flag5 = true;
   }
*/
		//delete by lihua 20060515 end

		if (fm.dangerItemKind[1].value != "") //标志位
		{
			if (fm.dangerFlag[1].checked == true) {
				fm.hiDangerFlag.value = "10";
			} else {
				fm.hiDangerFlag.value = "00";
			}
		}

		//delete by lihua 20060515 begin
		/* else if(fm.allDangerFlag.checked == true)
  {
   fm.hiDangerFlag.value = "10";
  } else
  {
   fm.hiDangerFlag.value = "00";
  }
*/
		//delete by lihua 20060515 end
		if (fm.retentionValue[1].value == "" && fm.allRetentionValue.value == "") {
			fm.hiRetentionValue.value = 0.0;
		}
		/*
  if(fm.riskLevel[1].value="" && fm.allRiskLevel.value="")
  {
   errorMessage("请输入风险等级信息");
   return;
  }
  if(fm.retCurrency[1].value="" && fm.allRetentionCurrency.value= "")
  {
   errorMessage("请输入自留额币种信息");
   return;
  }
  if(fm.retentionValue[1].value="" && fm.allRetentionValue.value="")
  {
   errorMessage("请输入自留额信息");
   return;
  }

  if(fm.riskLevel[1].value="" && fm.allRiskKind.value="")
  {
   errorMessage("请输入风险类别信息");
   return;
  }
  */
	} //对需要拆分危险单位的处理--End---



	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見！");
		fm.HandleText.focus();
		return false;
	}
	fm.DealType.value = "save";
	fm.target = "fraInterface";
	fm.action = "/claim/CommonDealTask.do";
	disabledButton(field);
	fm.submit();

}

//保存任务前之提交

function submitTaskBefore(submitDirection,field) {
	//对需要拆分危险单位的险种的处理
	if (fm.riskUnitFlag.value == '1' && fm.HandType.value == '11') {
		var dangerNoCount = fm.dangerNo.length;
		var sPremium = 0;
		for (i = 1; i < dangerNoCount; i++) {
			//window.alert(sPremium + "-" + fm.premium[i].value);
			sPremium += parseFloat(fm.premium[i].value);
		}

		sPremium = mathRound(parseFloat(sPremium));
		if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
			//modify begin by zhaijq 20051227 从共保保额保费不再重新计算
			if (fm.dangerCoinsFlag[1].value == "2") {
				window.alert("從共保業務，份額保費爲:" + parseFloat(fm.tolPremium.value) +
						"\n" + "    總保費爲:" + parseFloat(sPremium));
			} else {
				window.alert("拆分危險單位後的總保費與原保費不相等" + "\n" + "     原始保費爲" + parseFloat(fm.tolPremium.value) + "\n" +
						"    現在保費爲" + parseFloat(sPremium));
				return;
			}
			//modify end by zhaijq 20051227

		}
	}

	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見！");
		fm.HandleText.focus();
		return false;
	}

	fm.DealType.value = "submit";
	fm.target = "fraInterface";
	fm.action = "/claim/CommonDealTask.do";
	fm.SubmitDirection.value = submitDirection;
	disabledButton(field);
	fm.submit();
}

//拒保提交

function submitRefuse() {
	fm.DealType.value = "refuse";
	fm.target = "fraInterface";
	fm.action = "/claim/CommonDealTask.do";
	fm.submit();
}

//提交任务节点

function submitTask() {
	var intCount = fm.radSelectNode.length;
	var FlowID, ModelNo, NodeNo, BusinessNo, FlowStatus, HandlerCode, Flag, CertiType;
	var strURL;
	var singleSubmit, multiSubmit;
	var i = 0;
	fm.action = "";
	intCount = intCount - 1;
	for (i = 0; i < intCount; i++) {
		if (fm.radSelectNode[i].checked == true) {
			fm.selectNodeNo.value = fm.NodeNo[i].value;
			fm.selectNodeName.value = fm.NodeName[i].value;
			fm.target = "fraInterface";
			fm.action = "/claim/CommonSubmitTask.do";
			if (fm.FlowStatus.value == "0") {
				if (confirm("確實要提交高階嗎？")) {
					fm.submitTip.value = "提交高階";
					fm.ok.disabled = true;
					fm.submit();
				}
				return;
			} else if (fm.FlowStatus.value == "1") {
				if (confirm("確實要下发修改吗？")) {
					fm.submitTip.value = "下发修改";
					fm.ok.disabled = true;
					fm.submit();
				}
				return;
			}
		}
	}
	errorMessage("请选择一条提交的节点");
}

//提交审核通过

function submitPass(field) {
	var businessType = fm.hiBusinessType.value;
	var classCode = fm.classCode.value;
	//意健险团体单必须先录入PML值才能审核通过
	for (var i = 1; i < getElementCount("speValue", fm); i++) {
		//modify begin by zhaijq 20060414 2799含意外险要求输入PML值
		//if (parseFloat(fm.speValue[i].value)==0.00  && (fm.hiClassCode.value == "26" || fm.hiClassCode.value == "27") && fm.policyType.value == "02")
		if (parseFloat(fm.speValue[i].value) == 0.00 && (fm.hiClassCode.value == "26" || fm.hiClassCode.value == "27") && fm.policyType.value == "02" && fm.includeAccident.value == "Y")
		//modify end by zhaijq 20060414
		{
			alert("請先輸入PML值才能審核通過!");
			return;
		}
	}
	//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 START
	if (classCode == 'A'){
		if (fm.itemKindCheck){
			//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 START
			if(!checkDateBetweenHaventDuplicateCase()){
				return false;
			}		
			//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 END
		}
	}
	//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 END
	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見!");
		fm.HandleText.focus();
		return false;
	}

	if (businessType == 'T' || businessType == 'P' || businessType == 'E') {
		if (checkRetenValueIsZero() == false) {
			return false;
		}
	}
	//判断是否做了风险评估
	if (fm.HandType.value != 22) {
		if (checkRetenValueIsZero() == false) {
			return false;
		}
	}
	if ((fm.HandType.value != 22) && (fm.notion.value != "同意承保")) {
		alert("系統信息:\n\n" + "審核片語，請選“同意承保”才能審核通過！");
		fm.notion.focus();
		return false;
	}

	if ("" != fm.exceptions.value) {
		if (confirm("是否例外事項？")) {
			KMessageBox.ShowConfirm('', '系統提示', '確實要提交審核通過嗎？', function () {
				fm.passBtn.disabled = true;
				fm.FlowStatus.value = "0";
				fm.submitTip.value = "審核通過";
				fm.action = "/claim/CommonSubmitTask.do";
				disabledButton(field);
				fm.submit();
			}, function () {

			});
		} else {
			fm.DealType.value = "submit";
			fm.target = "fraInterface";
			fm.action = "/claim/CommonDealTask.do";
			fm.SubmitDirection.value = "SubmitJunior";
			fm.notion.options.length = 0;
			fm.notion.options.add(new Option("收款人與被保險人不一致", "收款人與被保險人不一致"));
			fm.HandleText.value = "收款人與被保險人不一致";
			disabledButton(field);
			fm.submit();
			return false;
		}
	} else {
		if (confirm("確實要提交審核通過嗎？")) {
			fm.passBtn.disabled = true;
			fm.FlowStatus.value = "0";
			fm.submitTip.value = "審核通過";
			fm.action = "/claim/CommonSubmitTask.do";
			disabledButton(field);
			fm.submit();
		}
	}
}

/**
 * 提交批次审核通过
 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增-提交批次审核通过
 * @param field
 * @returns {Boolean}
 */
function submitHeapPass(field) {
	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見!");
		fm.HandleText.focus();
		return false;
	}
	
	if (confirm("確實要提交審核通過嗎？")) {
		fm.passBtn.disabled = true;
		fm.action = "/claim/CommonHeapSubmitTask.do";
		disabledButton(field);
		fm.submit();
	}
}
/**
 * 提交批次放棄任務
 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增-提交批次审核通过
 * @param field
 */
function undoHeapTask(field) {
	if (!confirm("確認要放棄任務？")) {
		return false;
	}
	fm.action = "/claim/CommonHeapDealTask.do?DealType=undo";
	disabledButton(field);
	fm.submit();
}

//設定提交节点

function setSelectNode() {
	var len = fm.radSelectNode.length;
	var i = 0;
	fm.SelectUser.value = ""; //将处理人员置为空串
	for (i = 0; i < len; i++) {
		if (fm.radSelectNode[i].checked == true) {
			fm.SelectNode.value = fm.NodeName[i].value;
		}
	}
}


/**保存处理的分保意向信息**/
function saveReins(RetentionRate, RetentionValue) {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=0&RetentionRate=" + RetentionRate.value +
		"&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

/**分保意向信息提交再保**/
function transmitReins(RetentionRate, RetentionValue) {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=9&RetentionRate=" + RetentionRate.value +
		"&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

//增加分保人

function openreinspage(Field) {
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;
	url = "/undwrt/common/CommonReinsInfo.jsp?Index=" + intIndex + "&FieldName=" + fieldName + "&ReinsCode=" + Field.value + "&FinalReinsCode=" + Field.value + "&PayCode=" + Field.value;
	window.open(url, "editwindow", "resizable=0,scrollbars,dependent,width=650,height=300");
}

//给增加的分保人赋值

function selectReins(index) {
	var ReinsCode = window.opener.fm.ReinsCode[index].value;
	var FinalReinsCode = window.opener.fm.FinalReinsCode[index].value;
	var PayCode = window.opener.fm.PayCode[index].value;
	if (fm.SelReinsCode.length > 0) {
		if (ReinsCode.indexOf("*") >= 0 || ReinsCode == "" || ReinsCode == null) {
			window.opener.fm.ReinsCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.ReinsName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
		}
		if (FinalReinsCode.indexOf("*") >= 0 || FinalReinsCode == "" || FinalReinsCode == null) {
			window.opener.fm.FinalReinsCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.FinalReinsName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
		}
		if (PayCode.indexOf("*") >= 0 || PayCode == "" || PayCode == null) {
			window.opener.fm.PayCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.PayName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
		}
	} else {
		alert("無可選擇的分保人！");
	}
	window.close();
}

//取消保存

function cancelForm() {
	window.close();
}

/*************** 撤销任务 begin *******************/

// 設定选中标志
function setUndoTaskCheckFlag(index) {
	if (fm.Delete[index].checked == true) {
		fm.CheckFlag[index].value = "Y";
	} else if (fm.Delete[index].checked == false) {
		fm.CheckFlag[index].value = "N";
	}
}

//全部选中

function selectUndoTaskAll() {
	var flag = fm.selectedAll.checked;
	if (flag == true) {
		for (var i = 0; i < fm.FlowID.length; i++) {
			fm.Delete[i].checked = true;
			fm.CheckFlag[i].value = "Y";
		}
	} else if (flag == false) {
		for (var i = 0; i < fm.FlowID.length; i++) {
			fm.Delete[i].checked = false;
			fm.CheckFlag[i].value = "N";
		}
	}
}

//提交要撤销的任务

function submitUndoTask() {
	var intCount = fm.FlowID.length;
	var i = 0;
	var blnCheck = false;
	for (i = 0; i < intCount; i++) {
		if (fm.Delete[i].checked == true) {
			blnCheck = true;
			break;
		}
	}

	if (blnCheck) {
		fm.method = "post";
		fm.action = "/claim/CommonUndoTask.do?EditType=submit";
		fm.submit();
	} else {
		errorMessage("請選擇需要撤消的記錄");
	}
}

function showTask(i) {
	//获得指定表单域的信息
	fm.iFlowID.value = fm.FlowID[i].value;
	fm.iLogNo.value = fm.LogNo[i].value;
	fm.iBusinessNo.value = fm.BusinessNo[i].value;
	fm.iBusinessType.value = fm.BusinessType[i].value; //0623刘军加
	fm.action = "/claim/CommonCheckTask.do?EditType=query";
	fm.method = "post";
	fm.submit();
}

/*************** 撤销任务 end *******************/


function submitReinsHePei() {
	var submitStr;
	BusinessNo = fm.BusinessNo.value;
	BusinessType = fm.BusinessType.value;
	submitStr = "/undwrt/hepei/ShowPay.jsp?CertiType=" + BusinessType + "&CertiNo=" + BusinessNo;
	window.open(submitStr, '攤賠意向', 'width=700,height=400,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}


/* 处理批量核保任务 */
function checkBatchTask(i) {
	//获得指定表单域的信息
	fm.iFlowID.value = fm.FlowID[i].value;
	fm.iLogNo.value = fm.LogNo[i].value;
	fm.iBusinessNo.value = fm.BusinessNo[i].value;
	fm.iBusinessType.value = fm.BusinessType[i].value;
	fm.iContractNo.value = fm.ContractNo[i].value;
	fm.iPackageID.value = fm.PackageID[i].value;
	fm.iModelNo.value = fm.ModelNo[i].value;
	fm.iNodeNo.value = fm.NodeNo[i].value;
	fm.iFlowStatus.value = fm.FlowStatus[i].value;
	fm.iDeptCode.value = fm.DeptCode[i].value;
	fm.iFlowInTime.value = fm.FlowInTime[i].value;
	fm.iNodeStatus.value = fm.NodeStatus[i].value;

	var EditType = fm.EditType.value;
	var HandType = fm.HandType.value;
	fm.action = "/claim/BatchTask.do?EditType=" + EditType + "&HandType=" + HandType;
	fm.method = "post";
	fm.submit();
}

function openWinQuery() {
	var win;
	var businessNo = fm.RegistNo.value;
	var policyNo = fm.PolicyNo.value;
	var riskCode = fm.strRiskCode.value;
	var claimNo = fm.ClaimNo.value;
	var messagedo = "/claim/messageSave.do?businessNo=" + businessNo + "&nodeType=veric&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo
	win = window.open(messagedo, "",
		"status=no,resizable=yes,scrollbars=yes,width=600,Height=600,left=200,top=70");
}

/************************* 指定人员处理 ***************************/
function selectPeople() {
	var NodeNo;
	var strURL;
	var intCount = fm.radSelectNode.length;
	intCount = intCount - 1;
	var i = 0;
	if (fm.SelectNode.value == "") {
		alert("請先選擇一條節點");
		return;
	}
	for (i = 0; i < intCount; i++) {
		if (fm.radSelectNode[i].checked == true) {
			NodeNo = fm.NodeNo[i].value;
			if (NodeNo == 1) {
				alert("提交到出單員不能選擇人員");
				return;
			}
			/* 设定隐含域数据，准备提交 */
			fm.selectNodeNo.value = fm.NodeNo[i].value;
			fm.selectNodeName.value = fm.NodeName[i].value;

			fm.target = "fraSubmit";
			fm.action = "/claim/submitUser.do";
			fm.method = "post";
			fm.submit();
			strURL = "/undwrt/hebao/SubmitUserList.jsp";
			window.open(strURL, '指定人員', 'width=420,height=200,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
			return;
		}
	}
}

/**选择提交人员**/
function submitUser() {
	var j = fm.people.options.length;
	var oldvalue;
	var i = 0;
	for (i = 0; i < j; i++) {
		if (fm.people.options[i].selected) {
			oldvalue = window.opener.fm.SelectNode.value;
			if (oldvalue.indexOf("-") >= 0) {
				oldvalue = oldvalue.substring(0, oldvalue.indexOf("-"));
			}
			window.opener.fm.SelectNode.value = oldvalue + "-" + fm.people.options[i].value;
			window.opener.fm.SelectUser.value = fm.people.options[i].value;
			window.close();
			return;
		}
	}
	alert("請選擇一個人員");
}
/******************** 指定人员处理 end ***********************/

/******************** 东安个性函数 begin*******************/
//历史投保信息列表页面
function showBusinessTotalInfo(BusinessNo) {
	var vURL = '/undwrt/common/CommonHistoryProposalList.jsp?BusinessNo=' + BusinessNo;
	window.open(vURL, '歷史投保信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//历史赔付信息列表页面

function showHistoryLossInfo(BusinessNo) {
	var vURL = '/undwrt/common/CommonHistoryLossList.jsp?BusinessNo=' + BusinessNo;
	window.open(vURL, '歷史賠付信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**分保试算**/
function simulateReins(RetentionRate, RetentionValue) {
	var submitStr;
	submitStr = "/undwrt/common/CommonReinsSimulate.jsp?RetentionRate=" + RetentionRate.value +
		"&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

//获取自留比例

function getRetentionRates(CertiNo, field) {
	var elementRates = fm.RetentionRates;
	for (var i = elementRates.options.length - 1; i >= 0; i--) {
		elementRates.remove(i);
	}

	if (field.value == "")
		return;
	var strURL = "/undwrt/common/CommonReinsGetRetentionRates.jsp?CertiNo=" + CertiNo +
		"&RateCode=" + field.value;
	var vXmlText = getResponseXmlText(strURL);
	//截掉头尾字符[]
	if (vXmlText.length >= 2)
		vXmlText = vXmlText.substring(1, vXmlText.length - 1);
	else
		vXmlText = "";
	var Rates = vXmlText.split("^");
	for (var i = 0; i < Rates.length; i++) {
		var option = document.createElement("option");
		//modify by luyang 2005-3-4 13:13
		option.text = rightTrim(Rates[i]);
		option.value = rightTrim(Rates[i]);
		option.text = leftTrim(Rates[i]);
		option.value = leftTrim(Rates[i]);
		elementRates.add(option);
	}
}

//离开域时校验是否超过合同最高自留额

function checkTopRetenValue(field) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "") strValue = "0";
	var value = parseFloat(strValue);

	if (value > 8500000) {
		alert("輸入的自留額不能大於合約最高自留額850萬");
		field.focus();
		field.select();
		return false;
	}
	return true;
}

//离开域时的数字校验Decimal

function checkDecimal(field, p, s, MinValue, MaxValue) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";

	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;

	if (!isNumeric(strValue)) {
		errorMessage("請輸入合法的數字");
		field.focus();
		field.select();
		return false;
	}
	p = parseInt(p, 10);
	s = parseInt(s, 10);

	var pLength;
	var sLength;
	var position = strValue.indexOf(".");
	if (position > -1) {
		pLength = position;
		sLength = strValue.length - position - 1;
	} else {
		pLength = strValue.length;
		sLength = 0;
	}

	if (pLength > (p - s) || sLength > s) {
		errorMessage("請輸入合法的" + desc + "\n類型爲數字,整數位最長爲" + (p - s) + ",小數位最長爲" + s);
		field.focus();
		field.select();
		return false;
	}

	var value = parseFloat(strValue);
	if (MaxValue != null && MinValue != null && trim(MaxValue) != "" && trim(MinValue) != "") {
		MinValue = parseFloat(MinValue);
		MaxValue = parseFloat(MaxValue);
		if (isNaN(value) || value > MaxValue || value < MinValue) {
			errorMessage("請輸入合法的" + desc + "\n類型爲數字,最小值爲" + MinValue + ",最大值爲" + MaxValue);
			field.focus();
			field.select();
			return false;
		}
	}
	return true;
}

//使用xmlhttp访问页面，並获取数据

function getResponseXmlText(strURL) {
	var objXmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	objXmlHttp.Open("POST", strURL, false);
	objXmlHttp.setRequestHeader("Content-type", "text/xml");
	objXmlHttp.Send("");
	if (objXmlHttp.status == 200) {
		//modify by luyang 2005-3-4 13:13
		return objXmlHttp.responseText;
		//return objXmlHttp.responseXML.text;
	} else if (objXmlHttp.status == 404) {
		alert("找不到頁面：" + strURL);
		return "";
	} else {
		alert("訪問" + strURL + "出錯，錯誤號：" + objXmlHttp.status);
		return "";
	}
}
/******************** 东安个性函数 end*******************/

/**保存处理的分保意向信息**/
function saveReins() {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=0";
	fm.action = submitStr;
	fm.submit();
}

/**分保意向信息提交再保**/
function transmitReins() {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=9";
	fm.action = submitStr;
	fm.submit();
}


/*******************  校验危险单位拆分信息 begin add by luyang 2005-5-6 13:30 *****************/
//原始标的相关值
var sumAmount = 0;
var sumPremium = 0;
var currency = "";
var kindFlag = new Array();
var kindFlagText = new Array();
var kindFlagCount = 0;

function checkAllDangerUnitForm() {
	if (fm.itemKindNo != null) {
		if (checkAllDangerUnit()) {
			return true;
		} else {
			return false;
		}

	} else {
		return true;
	}
}


//检查投保单的所有危险单位主信息表单域

function checkAllDangerUnit() {
	var dangerUnitCount = fm.dangerNo.length;
	var i = 0;
	var amount = 0;
	var premium = 0;

	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage("危險單位無記錄，請重新操作！");
		return false;
	}
	for (i = 1; i < dangerUnitCount; i++) {
		if (isEmptyField(fm.dangerNo[i])) {
			errorMessage("請輸入序號！");
			fm.dangerNo[i].focus();
			return false;
		}
		if (isEmptyField(fm.dangerDesc[i])) {
			errorMessage("請輸入危險單位的危險單位描述！");
			fm.dangerDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevel[i])) {
			errorMessage("請輸入危險單位的風險等級！");
			fm.riskLevel[i].focus();
			return false;
		}
		/*
    if(isEmptyField(fm.riskLevelDesc[i]))
    {
      errorMessage("请输入危险单位的风险描述！");
      fm.riskLevelDesc[i].focus();
      return false;
    }*/
		//alert(fm.kindFlag[i].value);


		if (isEmptyField(fm.currency[i])) {
			errorMessage("請輸入危險單位的輸入幣別！");
			fm.currency[i].focus();
			return false;
		}
		if (isEmptyField(fm.amount[i])) {
			errorMessage("請輸入危險單位的保額！");
			fm.amount[i].focus();
			return false;
		}
		if (isEmptyField(fm.premium[i])) {
			errorMessage("請輸入危險單位的保費！");
			fm.premium[i].focus();
			return false;
		}
		if (isEmptyField(fm.dangerShare[i])) {
			errorMessage("請輸入危險單位的占比！");
			fm.dangerShare[i].focus();
			return false;
		}
		if (fm.isSavaDangerUnit[i].value == "N") {
			errorMessage("第" + i + "個危險單位還沒有設定險別歸類信息,請點選相應的查看子信息按鈕");
			return false;
		}
		amount += parseFloat(fm.amount[i].value);
		premium += parseFloat(fm.premium[i].value);


	}
	var flag = true;
	var msg = "";
	//拆分後保额与标的总保额一致
	if (amount != sumAmount) {
		msg = "拆分後保額應與原標的中保額一致！\n現差額：" + (sumAmount - amount);
		flag = false;
	}
	if (premium != sumPremium) {
		if (msg != "")
			msg = "\n\n"
		msg += "拆分後保費應與原標的中保費一致！\n現差額：" + (sumPremium - premium);
		flag = false;
	}

	if (flag == false) {
		errorMessage(msg);
		return false;
	}


	return true;
}

//检查投保单的某一行危险单位主信息表单域

function checkDangerUnit(i) {
	var dangerUnitCount = fm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage("危險單位無記錄，請重新操作！");
		return false;
	}

	if (isEmptyField(fm.dangerNo[i])) {
		errorMessage("請輸入序號！");
		fm.dangerNo[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerDesc[i])) {
		errorMessage("請輸入危險單位描述！");
		fm.dangerDesc[i].focus();
		return false;
	}
	if (isEmptyField(fm.riskLevel[i])) {
		errorMessage("請輸入風險等級！");
		fm.riskLevel[i].focus();
		return false;
	}

	if (isEmptyField(fm.riskLevelDesc[i])) {
		errorMessage("請輸入風險描述！");
		fm.riskLevelDesc[i].focus();
		return false;
	}
	//alert(fm.kindFlag[i].value);


	if (isEmptyField(fm.currency[i])) {
		errorMessage("請輸入幣別！");
		fm.currency[i].focus();
		return false;
	}
	if (isEmptyField(fm.amount[i])) {
		errorMessage("請輸入保額！");
		fm.amount[i].focus();
		return false;
	}
	if (isEmptyField(fm.premium[i])) {
		errorMessage("請輸入保費！");
		fm.premium[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerShare[i])) {
		errorMessage("請輸入占比！");
		fm.chgAmount[i].focus();
		return false;
	}


	return true;
}

//初始化投保单，保单的危险单位信息

function initDangerUnit() {
	//add by liping 080709 增加保费是否实收提示
	var payFee = fm.payFlag.value;
	var message = "";
	if (payFee == -1) {
		message = message + "此保單保費未繳,請慎重處理！！！ \n";
	} else if (payFee == 0) {
		message = message + "此保單已繳未繳全,請慎重處理！！！ \n";
	}
	if (message.length > 0) {
		alert(message);
	}
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - start
	//判斷是否在閉鎖期
	if(fm.isCloseBetween.value === 'true'){
		alert("請注意本賠次在閉鎖期！！！！！");
	}
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - end
	//核赔时不做处理
	if (fm.HandType.value == 22) {
		return;
	}
	dangerInfo.style.display = "none";
}

//在核保主页面上,初始化批单的危险单位信息

function initEndorseDangerUnit() {
	if (fm.riskUnitFlag.value == "0") //某些险种不需要拆分危险单位时不初始化危险单位信息
	{
		return;
	}
	dangerInfo.style.display = "none"; //页面打开时拆分危险单位部分折叠
	var dangerUnitCount = fm.dangerNo.length;
	if (fm.itemKindNo != null) {
		var itemKindCount = fm.itemKindNo.length;
		var i = 0;
		var j = 0;
		//危险单位拆分後相关值
		var amount = 0;
		var premium = 0;
		//获取标的险别相关信息
		if (itemKindCount == null) {
			sumAmount = parseFloat(fm.iAmount.value);
			sumPremium = parseFloat(fm.iPremium.value);
			currency = fm.iCurrency.value;
		} else {
			for (i = 0; i < itemKindCount; i++) {
				sumPremium += parseFloat(fm.iPremium[i].value);
				if (fm.calculateFlag[i].value == "Y") {
					sumAmount += parseFloat(fm.iAmount[i].value);
					currency = fm.iCurrency[i].value;
				}
			}
		}
		if (dangerUnitCount == null || dangerUnitCount <= 1) {
			insertRow('DangerUnit');
			dangerUnitCount = fm.dangerNo.length;
			for (i = 1; i < dangerUnitCount; i++) {
				fm.dangerNo[i].value = '1';
				fm.dangerDesc[i].value = '危險單位描述';
				fm.riskLevel[i].value = '000';
				fm.riskLevelDesc[i].value = '風險等級描述1';
				fm.currency[i].value = currency;
				fm.retentionValue[i].value = '0.00';
				fm.amount[i].value = sumAmount;
				fm.premium[i].value = sumPremium;
			}
		}

	}
}


/**
 *根据隐藏域的值来設定计算保费复选框的选中情况
 *
 */

function getCalFlag() {
	var elementCount = getElementCount("flag"); //获取到元素的个数

	if (elementCount > 1) //个数大於1时的处理
	{

		for (var index = 1; index < elementCount; index++) {

			if (fm.hiddenFlag[index].value.charAt(0) == "1") //取第一位字段
			{
				fm.flag[index].checked = true;
			} else if (fm.hiddenFlag[index].value.charAt(0) == "0") {
				fm.flag[index].checked = false;
			}



			if (fm.hiddenFlag2[index].value.charAt(1) == "1") //取第二位字段
			{
				fm.flag2[index].checked = true;
			} else if (fm.hiddenFlag2[index].value.charAt(1) == "0") {
				fm.flag2[index].checked = false;
			}
		}
	}

}



//显示投保单，保单的危险单位子信息

function showDangerItem(field, pageCode, flag) {
	var classCode = fm.hiClassCode.value;
	var editType = fm.EditType.value; //判断是否是query状态
	var count = fm.dangerNo.length; //得到危险单位的个数
	var currentRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	var riskCode = fm.riskCode.value;
	var riskUnitFlag = fm.riskUnitFlag.value; //该险种是否允许拆分危险单位
	var includeAccident = fm.includeAccident.value;
	var dangerNo = 0; //fm.dangerNo[index].value;
	var index = 0; //因为危险单位号和table中行号不是对应的，故設定index以便定位
	var isNewDangerInfo = 0;
	if (flag == 'NewDangerNo') {
		dangerNo = parseInt(fm.dangerNo[count - 2].value) + 1; //點選新增的一个危险单位,危险单位号为最後一个序号加1
		fm.dangerNo[count - 1].value = dangerNo;
		index = count - 1;
		isNewDangerInfo = 1;
	} else //查看存在的危险单位
	{
		dangerNo = fm.dangerNo[RowNo].value;
		index = RowNo;
		isNewDangerInfo = 0;
	}
	submitStr = "/claim/CommonCheckTask.do?businessNo=" + businessNo + "&businessType=" + businessType +
		"&riskCode=" + riskCode + "&classCode=" + classCode + "&dangerNo=" + dangerNo + "&showDangerItemFlag=1" +
		"&openerIndex=" + index + "&NewDangerInfo=" + isNewDangerInfo + "&editType=" + editType + "&riskUnitFlag=" + riskUnitFlag + "&includeAccident=" + includeAccident;
	window.open(submitStr, '查看劃分危險單位的子信息', 'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
}

//核心调双核时显示危险单位子信息(enterFlag=1是起到一个标志位得作用，表示此处只是查看危险单位子信息)
//核心调双核时显示危险单位子信息

function showPrpDangerItem(field, pageCode, flag) {
	var count = fm.dangerNo.length; //得到危险单位的个数
	var currentRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	dangerNo = fm.dangerNo[RowNo].value;
	submitStr = "/claim/PrpQureyInfo.do?businessNo=" + businessNo + "&businessType=" + businessType +
		"&dangerNo=" + dangerNo + "&showDangerItemFlag=1&enterFlag=1";
	window.open(submitStr, '查看劃分危險單位的子信息', 'width=950,height=600,top=0,left=20,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
}

//双核中的临分意向

function submitReins() {
	var BusinessNo = fm.BusinessNo.value;
	var BusinessType = fm.BusinessType.value;
	var policyNo = "";
	var proposalNo = "";
	// 設定ReinsFlag便於CommonCheckTaskFacade判断
	if (BusinessType != "E") {
		submitStr = "/claim/CommonCheckTask.do?CertiType=" + BusinessType + "&CertiNo=" + BusinessNo + "&ReinsFlag=" + 1;
	} else {
		//批单
		policyNo = fm.hiPolicyNo.value;
		proposalNo = fm.hiProposalNo.value;
		submitStr = "/claim/CommonCheckTask.do?CertiType=" + BusinessType + "&CertiNo=" + BusinessNo + "&policyNo=" + policyNo + "&proposalNo=" + proposalNo + "&ReinsFlag=" + 1;
	}
	window.open(submitStr, '臨分意向', 'width=1020,height=700,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
}

//批单危险单位的子信息提交

function saveEndorseDangerItemTask() {
	var count = DangerItemForm.itemKindNo.length - 1;
	var classCode = DangerItemForm.classCode.value;
	var amount = 0.0;
	var chgAmount = 0.0;
	var premium = 0.0;
	var chgPremium = 0.0;
	var index = DangerItemForm.openerIndex.value;
	var tolPremium = 0;
	var dangerShare = 0;
	/*
    alert("子信息条数为" + count);
    if (tolPremium == 0)
    {
     window.alert("数据异常，总保额为0");
    }
    */
	for (i = 1; i <= count; i++) {
		if (DangerItemForm.itemKindNo[i].value == "") {
			alert("請輸入標的序號");
			DangerItemForm.itemKindNo[i].focus();
			return;
		}
		if (DangerItemForm.ItemKindName[i].value == "") {
			alert("請輸入險別");
			DangerItemForm.ItemKindName[i].focus();
			return;
		}

		if (DangerItemForm.ItemCurrency[i].value == "") {
			alert("請輸入標的幣別");
			DangerItemForm.ItemCurrency[i].focus();
			return;
		}

	}

	//if(DangerItemForm.dangerDesc[1].value == ""){
	//    window.alert("危险单位描述不能为空");
	//    DangerItemForm.dangerDesc[1].focus();
	//    return;
	//}
	//
	//if(DangerItemForm.dangerAddress[1].value == ""){
	//     window.alert("地址不能为空");
	//     DangerItemForm.dangerAddress[1].focus();
	//     return;
	//}

	if (DangerItemForm.riskLevel[1].value == "") {
		window.alert("風險等級不能爲空");
		DangerItemForm.riskLevel[1].focus();
		return;
	}

	if (DangerItemForm.riskLevelDesc[1].value == "") {
		window.alert("危險等級描述不能爲空");
		DangerItemForm.riskLevelDesc[1].focus();
		return;
	}

	//modify begin by zhaijq 20060414 2799含意外险要求输入PML值
	//if ((classCode == "27" ||classCode =="26") && window.opener.fm.policyType.value == "02"
	if ((classCode == "27" || classCode == "26") && window.opener.fm.policyType.value == "02" && DangerItemForm.includeAccident.value == "Y") {
		//modify end by zhaijq 20060414
		if (DangerItemForm.speCurrency[1].value == "") {
			window.alert("意健險PML幣種不能爲空");
			DangerItemForm.speCurrency[1].focus();
			return;
		}

		if (DangerItemForm.speValue[1].value == "" || parseFloat(DangerItemForm.speValue[1].value) == 0.00) {
			window.alert("意健險PML值不能爲空");
			DangerItemForm.speValue[1].focus();
			return;
		}
	}

	if (DangerItemForm.retCurrency[1].value == "") {
		window.alert("自留額幣種不能爲空");
		DangerItemForm.retCurrency[1].focus();
		return;
	}

	if (DangerItemForm.retentionValue[1].value == "") {
		window.alert("自留額值不能爲空");
		DangerItemForm.retentionValue[1].focus();
		return;
	}

	if (DangerItemForm.currency[1].value == "") {
		window.alert("幣種不能爲空");
		return;
	}
	/*
    dangerShare = (premium +chgPremium) / parseFloat(tolPremium) * 100;
    dangerShare = round(point(dangerShare,5),4);
    premium     = round(point(premium,3),2);
    amount      = round(point(amount,3),2);
    chgAmount   = round(point(chgAmount,3),2);
    chgPremium  = round(point(chgPremium,3),2);
   */
	//modify begin by lihua 20060517 24590 提示“DangerItemForm.hidangerItemFlag.1为空或不是对象
	if (DangerItemForm.dangerItemFlag[1].checked == true) {
		DangerItemForm.hiDangerItemFlag[1].value = "10";
	} else {
		DangerItemForm.hiDangerItemFlag[1].value = "00";
	}
	//modify end by lihua 20060517 24590 提示“DangerItemForm.hidangerItemFlag.1为空或不是对象

	window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
	window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
	window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;

	if (DangerItemForm.dangerItemFlag[1].checked == true) { //标识位
		window.opener.fm.dangerItemFlag[index].checked = true;
	} else {
		window.opener.fm.dangerItemFlag[index].checked = false;
	}

	window.opener.fm.riskLevel[index].value = DangerItemForm.riskLevel[1].value;
	window.opener.fm.riskLevelDesc[index].value = DangerItemForm.riskLevelDesc[1].value;
	if (classCode == "27") {
		window.opener.fm.speCurrency[index].value = DangerItemForm.speCurrency[1].value;
		window.opener.fm.speValue[index].value = DangerItemForm.speValue[1].value;
	}
	window.opener.fm.retCurrency[index].value = DangerItemForm.retCurrency[1].value;
	window.opener.fm.retentionValue[index].value = DangerItemForm.retentionValue[1].value;
	window.opener.fm.currency[index].value = DangerItemForm.currency[1].value;
	window.opener.fm.dangerShare[index].value = DangerItemForm.dangerShare[1].value;
	window.opener.fm.amount[index].value = DangerItemForm.amount[1].value;
	window.opener.fm.chgAmount[index].value = DangerItemForm.chgAmount[1].value;
	window.opener.fm.premium[index].value = DangerItemForm.premium[1].value;
	window.opener.fm.chgPremium[index].value = DangerItemForm.chgPremium[1].value;

	DangerItemForm.DealType.value = "saveEndorseDangerItem";
	DangerItemForm.hiBusinessType.value = "E"
	DangerItemForm.method = "post";
	DangerItemForm.action = "/claim/CommonDealTask.do";
	DangerItemForm.submit();
}




//投保单,保单的危险单位子信息提交

function saveDangerItemTask() {
	var classCode = DangerItemForm.classCode.value;
	var count = DangerItemForm.itemKindNo.length - 1;
	var amount = 0.0;
	var premium = 0.0;
	var index = DangerItemForm.openerIndex.value;
	var tolPremium = 0;
	var dangerShare = 0;
	for (i = 1; i <= count; i++) {
		premium += parseFloat(DangerItemForm.ItemPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		//if(DangerItemForm.ItemcalculateFlag[i].value=="Y")
		if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
			DangerItemForm.hiItemcalculateFlag[i].value = "Y";
			amount += parseFloat(DangerItemForm.ItemAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
	}
	if (tolPremium == 0) {
		window.alert("數據異常，總保額爲0");
		return;
	}
	dangerShare = premium / parseFloat(tolPremium) * 100;
	dangerShare = round(point(dangerShare, 0), 0);
	amount = round(point(amount, 0), 0);
	premium = round(point(premium, 0), 0);
	//modify begin by zhaijq 20051227 从共保保额保费不再重新计算    
	if (DangerItemForm.hiDangerCoinsFlag[1].value != "2") {
		//modify begin by zhaijq 20060511 1903险种保额不重新计算
		//DangerItemForm.amount[1].value = amount;
		if (DangerItemForm.riskCode.value != '1903') {
			DangerItemForm.amount[1].value = amount;
		}
		//modify end by zhaijq 20060511        
		DangerItemForm.premium[1].value = premium;
	}
	//modify end by zhaijq 20051227
	DangerItemForm.dangerShare[1].value = dangerShare;
	//if(DangerItemForm.dangerDesc[1].value == "")
	//{
	//  window.alert("危险单位描述不能为空");
	//  DangerItemForm.dangerDesc[1].focus();
	//  return;
	//}
	//if(DangerItemForm.dangerAddress[1].value == "")
	// {
	//      window.alert("地址不能为空");
	//      DangerItemForm.dangerAddress[1].focus();
	//      return;
	//   }

	if (DangerItemForm.riskLevel[1].value == "") {
		window.alert("風險等級不能爲空");
		DangerItemForm.riskLevel[1].focus();
		return;
	}

	if (DangerItemForm.riskLevelDesc[1].value == "") {
		window.alert("危險等級描述不能爲空");
		DangerItemForm.riskLevelDesc[1].focus();
		return;
	}
	//modify begin by zhaijq 20060414 2799含意外险要求输入PML值
	//if ((classCode == "27" ||classCode =="26") && window.opener.fm.policyType.value == "02") {
	if ((classCode == "27" || classCode == "26") && window.opener.fm.policyType.value == "02" && DangerItemForm.includeAccident.value == "Y") {
		//modify end by zhaijq 20060414 
		if (DangerItemForm.speCurrency[1].value == "") {
			alert("意健險PML幣種不能爲空");
			DangerItemForm.speCurrency[1].focus();
			return;
		}
		if (DangerItemForm.speValue[1].value == "" || parseFloat(DangerItemForm.speValue[1].value) == 0.00) {
			alert("意健險PML值不能爲空");
			DangerItemForm.speValue[1].focus();
			return;
		}
	}

	if (DangerItemForm.retCurrency[1].value == "") {
		window.alert("自留額幣種不能爲空");
		DangerItemForm.retCurrency[1].focus();
		return;
	}
	if (DangerItemForm.retentionValue[1].value == "") {
		window.alert("自留額值不能爲空");
		DangerItemForm.retentionValue[1].focus();
		return;
	}
	if (DangerItemForm.currency[1].value == "") {
		window.alert("幣種不能爲空");
		return;
	}

	//modify begin by zhaijq 20060511 1903险种保额不重新计算
	//window.alert("该危险单位的总保额为:"+ amount + "  总保费:" + premium +" 占比:" +dangerShare);
	window.alert("該危險單位的總保額爲:" + DangerItemForm.amount[1].value + "  總保費:" + premium + " 占比:" + dangerShare);
	//modify end by zhaijq 20060511      
	window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
	window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
	window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;

	if (DangerItemForm.dangerItemFlag[1].checked == true) //标识位
	{
		window.opener.fm.dangerFlag[index].checked = true;
	} else {
		window.opener.fm.dangerFlag[index].checked = false;
	}
	window.opener.fm.riskLevel[index].value = DangerItemForm.riskLevel[1].value;
	window.opener.fm.riskLevelDesc[index].value = DangerItemForm.riskLevelDesc[1].value;
	window.opener.fm.retCurrency[index].value = DangerItemForm.retCurrency[1].value;
	window.opener.fm.retentionValue[index].value = DangerItemForm.retentionValue[1].value;
	if (classCode == "27") {
		window.opener.fm.speCurrency[index].value = DangerItemForm.speCurrency[1].value;
		window.opener.fm.speValue[index].value = DangerItemForm.speValue[1].value;
	}
	window.opener.fm.currency[index].value = DangerItemForm.currency[1].value;
	window.opener.fm.dangerShare[index].value = DangerItemForm.dangerShare[1].value;
	//modify begin by zhaijq 20051227 从共保保额保费不再重新计算
	if (DangerItemForm.hiDangerCoinsFlag[1].value != "2") {
		//modify begin by zhaijq 20060511 1903险种保额不重新计算
		//window.opener.fm.amount[index].value = amount;
		if (DangerItemForm.riskCode.value != '1903') {
			window.opener.fm.amount[index].value = amount;
		}
		//modify end by zhaijq 20060511           
		window.opener.fm.premium[index].value = premium;
	}
	//modify end by zhaijq 20051227
	//modify begin by lihua 20060517 24590 提示“DangerItemForm.hidangerItemFlag.1为空或不是对象
	if (DangerItemForm.dangerItemFlag[1].checked == true) {
		DangerItemForm.hiDangerItemFlag[1].value = "10";
	} else {
		DangerItemForm.hiDangerItemFlag[1].value = "00";
	}
	//modify end by lihua 20060517 24590 提示“DangerItemForm.hidangerItemFlag.1为空或不是对象
	DangerItemForm.DealType.value = "saveDangerItem";
	DangerItemForm.method = "post";
	DangerItemForm.action = "/claim/CommonDealTask.do";
	DangerItemForm.submit();
}


//根据计算金额复选框的选中标志来設定隐藏域的值

function setCalFlag() {
	var flagCount = 0;

	for (var i = 0; i < DangerItemForm.elements.length; i++) //查找DangerItemForm里的元素
	{
		if (DangerItemForm.elements[i].name == "CalculateFlag") {
			flagCount++;
		}
	}

	if (flagCount > 1) //个数大於1的处理
	{
		for (var index = 1; index < flagCount; index++) {
			if (DangerItemForm.CalculateFlag[index].checked == true) {
				DangerItemForm.hiCalculateFlag[index].value = "Y";
			} else if (DangerItemForm.CalculateFlag[index].checked == false) {
				DangerItemForm.hiCalculateFlag[index].value = "N";
			}
		}
	}
}


//點選投保单的划分危险单位信息的删除按钮时同时删除在数据库中的主信息和子信息

function deleteTdangerInfo(field, pageCode) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var index = recentDeletedRowNo - 1;
	var businessType = fm.hiBusinessType.value;
	var businessNo = fm.hiBusinessNo.value;
	var dangerNo = fm.dangerNo[index].value;
	var dangerNoCount = fm.dangerNo.length; //危险单位信息条数
	//window.alert("删除信息时的危险单位号为" + dangerNo + "序号为" + index +"总数为" + dangerNoCount);
	if (dangerNoCount == 2) {
		window.alert("系統要求必須有一條危險單位信息,不能刪除");
		return false;
	}
	if (confirm("確定要刪除這條危險單位信息及相關子信息嗎?")) {
		if (dangerNo != "") {
			submitStr = "/claim/CommonDealTask.do?businessType=" + businessType + "&businessNo=" + businessNo + "&dangerNo=" + dangerNo +
				"&DealType=delete";
			var newWindow = window.open(submitStr, '刪除危險單位信息', 'width=800,height=600,top=300,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=0,resizable=0,status=0');
			newWindow.focus();
			newWindow.close();
		}
		deleteRow_new(field, pageCode);
	}
	window.close();
}

function deleteDangerItemInfo(field, pageCode) {
	var itemKindNo = DangerItemForm.itemKindNo.length;
	if (itemKindNo == 2) {
		window.alert("每個危險單位要求必須有一條標的信息,不能刪除");
		return false;
	}
	if (confirm("確定要刪除這條原始標的信息嗎?")) {
		deleteRow_new(field, pageCode);
	}
}

//點選批单的划分危险单位信息的删除按钮时，同时删除在数据库中的主信息和子信息

function deletePdangerInfo(field, pageCode) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var index = recentDeletedRowNo - 1;
	var businessType = fm.hiBusinessType.value;
	var businessNo = fm.hiBusinessNo.value;
	var dangerNo = fm.dangerNo[index].value;
	var dangerNoCount = fm.dangerNo.length; //危险单位信息条数
	//window.alert("删除信息时的危险单位号为" + dangerNo + "序号为" + index +"总数为" + dangerNoCount);
	if (dangerNoCount == 2) {
		window.alert("系統要求必須有一條危險單位信息,不能刪除");
		return false;
	}

	if (confirm("確定要刪除這條危險單位信息及信息嗎?")) {
		var recentDeletedRowNo = parseInt(getElementOrder(field)); //得到當前行的行號
		var index = recentDeletedRowNo - 1;
		var businessType = fm.hiBusinessType.value;
		var businessNo = fm.hiBusinessNo.value;
		var dangerNo = fm.dangerNo[index].value;

		if (dangerNo != "") { //删除数据库里的信息
			submitStr = "/claim/CommonDealTask.do?businessType=" + businessType + "&businessNo=" + businessNo + "&dangerNo=" + dangerNo +
				"&DealType=delete";
			var newWindow = window.open(submitStr, '刪除批單的危險單位信息', 'width=800,height=600,top=10,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
			newWindow.focus();
			newWindow.close();
		}
		deleteRow_new(field, pageCode);
	}
	window.close();
}


function checkDangerNoIsEmpty(field) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var index = recentDeletedRowNo - 1;

	if (fm.dangerNo[index].value != "") {
		window.alert("序号不能再次修改");
		fm.riskLevel[index].focus();
	} else {
		fm.dangerNo[index].focus();
	}
}

////查看批单的某条危险单位子信息

function showEndorseDangerItem(field, pageCode, flag) {
	var editType = fm.EditType.value; //判断是否是query状态
	var count = fm.dangerNo.length; //得到危险单位的个数
	var currentRowNo = parseInt(getElementOrder(field)); //得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	var policyNo = fm.hiPolicyNo.value; //保单号
	var riskCode = fm.riskCode.value;
	var classCode = fm.hiClassCode.value;
	var includeAccident = fm.includeAccident.value;
	var hiDangerNo = 0; //fm.dangerNo[index].value;
	var index = 0; //因为危险单位号和table中行号不是对应的，故設定index以便定位
	var isNewDangerInfo = 0;
	if (flag == 'NewDangerNo') {
		hiDangerNo = parseInt(fm.dangerNo[count - 2].value) + 1; //點選新增的一个危险单位,危险单位号为最後一个序号加1
		fm.dangerNo[count - 1].value = hiDangerNo;
		index = count - 1;
		isNewDangerInfo = 1;
	} else //查看存在的危险单位
	{
		hiDangerNo = fm.dangerNo[RowNo].value;
		index = RowNo;
		isNewDangerInfo = 0;
	}

	submitStr = "/claim/CommonCheckTask.do?businessNo=" + businessNo + "&policyNo=" + policyNo + "&businessType=" + businessType +
		"&riskCode=" + riskCode + "&classCode=" + classCode + "&hiDangerNo=" + hiDangerNo + "&showDangerItemFlag=2" +
		"&openerIndex=" + index + "&NewDangerInfo=" + isNewDangerInfo + "&editType=" + editType + "&includeAccident=" + includeAccident;
	window.open(submitStr, '查看批單的劃分危險單位的子信息', 'width=950,height=600,top=50,left=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');

}


//检验批单的危险单位某条主信息的表单域是否空

function checkEndorseDangerUnit(i) {
	var dangerUnitCount = fm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage("危險單位無記錄，請重新操作！");
		return false;
	}

	if (isEmptyField(fm.dangerNo[i])) {
		errorMessage("請輸入序號！");
		fm.dangerNo[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerDesc[i])) {
		errorMessage("請輸入危險單位描述！");
		fm.dangerDesc[i].focus();
		return false;
	}
	if (isEmptyField(fm.riskLevel[i])) {
		errorMessage("請輸入風險等級！");
		fm.riskLevel[i].focus();
		return false;
	}
	/*
    if(isEmptyField(fm.riskLevelDesc[i]))
    {
      errorMessage("请输入风险描述！");
      fm.riskLevelDesc[i].focus();
      return false;
    }*/
	//alert(fm.kindFlag[i].value);

	if (isEmptyField(fm.currency[i])) {
		errorMessage("請輸入幣別！");
		fm.currency[i].focus();
		return false;
	}
	if (isEmptyField(fm.amount[i])) {
		errorMessage("請輸入保額！");
		fm.amount[i].focus();
		return false;
	}
	if (isEmptyField(fm.premium[i])) {
		errorMessage("請輸入保費！");
		fm.premium[i].focus();
		return false;
	}
	if (isEmptyField(fm.chgAmount[i])) {
		errorMessage("請輸入變化保額！");
		fm.chgAmount[i].focus();
		return false;
	}
	if (isEmptyField(fm.chgPremium[i])) {
		errorMessage("請輸入變化保費！");
		fm.chgPremium[i].focus();
		return false;
	}
	return true;
}

//保存批单的所有危险单位主信息

function saveEndorseTask() {
	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見！");
		fm.HandleText.focus();
		return false;
	}
	fm.DealType.value = "save";
	fm.target = "fraInterface";
	fm.action = "/claim/CommonDealTask.do";
	fm.submit();


}

//检查批单所有的危险单位主信息的表单域

function checkAllEndorseDangerUnitForm() {
	if (fm.itemKindNo != null) {
		if (checkAllEndorseDangerUnit()) {
			return true;
		} else {
			return false;
		}
	} else {
		return true;
	}
}

function checkAllEndorseDangerUnit() {
	var dangerUnitCount = fm.dangerNo.length;
	var i = 0;
	var amount = 0;
	var premium = 0;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage("危險單位無記錄，請重新操作！");
		return false;
	}
	for (i = 1; i < dangerUnitCount; i++) {
		if (isEmptyField(fm.dangerNo[i])) {
			errorMessage("請輸入序號！");
			fm.dangerNo[i].focus();
			return false;
		}

		if (isEmptyField(fm.dangerDesc[i])) {
			errorMessage("請輸入危險單位描述！");
			fm.dangerDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevel[i])) {
			errorMessage("請輸入風險等級！");
			fm.riskLevel[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevelDesc[i])) {
			errorMessage("請輸入風險描述！");
			fm.riskLevelDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.currency[i])) {
			errorMessage("請輸入幣別！");
			fm.currency[i].focus();
			return false;
		}
		if (isEmptyField(fm.amount[i])) {
			errorMessage("請輸入保額！");
			fm.amount[i].focus();
			return false;
		}
		if (isEmptyField(fm.premium[i])) {
			errorMessage("請輸入保費！");
			fm.premium[i].focus();
			return false;
		}
		if (isEmptyField(fm.chgAmount[i])) {
			errorMessage("請輸入變化保額！");
			fm.chgAmount[i].focus();
			return false;
		}
		if (isEmptyField(fm.chgPremium[i])) {
			errorMessage("請輸入變化保費！");
			fm.chgPremium[i].focus();
			return false;
		}
		if (fm.isSavaDangerUnit[i].value == "N") {
			errorMessage("第" + i + "個危險單位還沒有設定險別歸類信息,請點選相應的查看子信息按鈕");
			return false;
		}
		amount += parseFloat(fm.amount[i].value);
		premium += parseFloat(fm.premium[i].value);
	}
	var flag = true;
	var msg = "";

	//拆分後保额与标的总保额一致
	if (amount != sumAmount) {
		msg = "拆分後保額應與原標的中保額一致！\n現差額：" + (sumAmount - amount);
		flag = false;
	}
	if (premium != sumPremium) {
		if (msg != "")
			msg = "\n\n"
		msg += "拆分後保費應與原標的中保費一致！\n現差額：" + (sumPremium - premium);
		flag = false;
	}

	if (flag == false) {
		errorMessage(msg);
		return false;
	}
	return true;
}

function simulateReinsByDangerIndex() {
	var CertiNo = fm.certiNo.value;
	var CertiType = fm.certiType.value;
	fm.method = "post";
	var submitStr = "/undwrt/common/CommonReinsSimulate.jsp?CertiNo=" + CertiNo + "&CertiType=" + CertiType;
	window.open(submitStr, '分保試算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}

//在投保单,保单查看危险单位子信息时计算标的保额和保费

function calItemNumber() {
	var count = DangerItemForm.itemKindNo.length - 1;
	var amount = 0;
	var premium = 0;
	var tolPremium = 0;
	var dangerShare = 0;
	for (i = 1; i <= count; i++) {
		premium += parseFloat(DangerItemForm.ItemPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
			DangerItemForm.hiItemcalculateFlag[i].value = "Y";
			amount += parseFloat(DangerItemForm.ItemAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
	}
	if (tolPremium == 0) {
		window.alert("數據異常，總保額爲0");
		return;
	}
	dangerShare = premium / parseFloat(tolPremium) * 100;
	dangerShare = round(point(dangerShare, 0), 0);
	premium = round(point(premium, 0), 0);
	amount = round(point(amount, 0), 0);
	window.alert("該危險單位的總保額爲: " + amount + "  總保費: " + premium + " 占比: " + dangerShare + "%");
	DangerItemForm.amount[1].value = amount;
	DangerItemForm.premium[1].value = premium;
	DangerItemForm.dangerShare[1].value = dangerShare
}

//在批单查看危险单位子信息时计算标的保额和保费

function calEndorseItemNumber() {
	var count = DangerItemForm.itemKindNo.length - 1;
	var amount = 0;
	var premium = 0;
	var chgAmount = 0;
	var chgPremium = 0;
	var tolPremium = 0;
	var dangerShare = 0;
	for (i = 1; i <= count; i++) {
		premium += parseFloat(DangerItemForm.ItemPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		chgPremium += parseFloat(DangerItemForm.ItemchgPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
			DangerItemForm.hiItemcalculateFlag[i].value = "Y";
			amount += parseFloat(DangerItemForm.ItemAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
			chgAmount += parseFloat(DangerItemForm.ItemchgAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
	}

	if (tolPremium == 0) {
		window.alert("數據異常，總保額爲0");
		//return;
	}
	dangerShare = (premium + chgPremium) / parseFloat(tolPremium) * 100;
	dangerShare = round(point(dangerShare, 0), 0);
	premium = round(point(premium, 0), 0);
	amount = round(point(amount, 0), 0);
	chgAmount = round(point(chgAmount, 0), 0);
	chgPremium = round(point(chgPremium, 0), 0);
	window.alert("該危險單位的總保額爲: " + (amount + chgAmount) + "  總保費: " + (premium + chgPremium) + " 占比: " + dangerShare + "%");
	DangerItemForm.amount[1].value = amount;
	DangerItemForm.chgAmount[1].value = chgAmount;
	DangerItemForm.premium[1].value = premium;
	DangerItemForm.chgPremium[1].value = chgPremium;
	DangerItemForm.dangerShare[1].value = dangerShare;
}



//对数字进行格式化,保证precision位

function point(number, precision) {
	if(precision!=0){
	if (isNaN(number))
		number = 0;

	var result = number.toString();
	if (result.indexOf(".") == -1)
		result = result + ".";

	result = result + newString("0", precision);
	result = result.substring(0, precision + result.indexOf(".") + 1);
	}
	else{
		var result = number.toString();
		if(result.indexOf(".")==-1){
			result = parseInt(result);
		}
		else{
			result = round(result,0);
			result = parseInt(result);
		}
	}
	return result;
}

/**
 * 将给定字符串复制ｎ遍
 * @param intLength 字符串长度
 * @return 字符串
 */
function newString(iString, iTimes) {
	var str = "";
	for (var i = 0; i < iTimes; i++)
		str = str + iString;
	return str;
}

//在查看危险单位子信息时初始化危险单位输入框

function initDangerUnitAtItem() {
	var dangerUnitCount = DangerItemForm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		insertRow('DangerUnit');
	} else {
		if (DangerItemForm.dangerDesc[1].value == "") {
			DangerItemForm.dangerDesc[1].value = '危險單位描述';
		}
		if (DangerItemForm.dangerAddress[1].value == "") {
			DangerItemForm.dangerAddress[1].value = DangerItemForm.ItemAddressName[1].value;
			if (DangerItemForm.dangerAddress[1].value == "") {
				DangerItemForm.dangerAddress[1].value = "危險單位地址";
			}
		}
	}

	var isNewDanger = DangerItemForm.isNewDangerInfo.value; //新增危险单位时为1
	if (DangerItemForm.itemKindNo.length != null && isNewDanger == 1) {
		var itemKindCount = DangerItemForm.itemKindNo.length - 1;
		var i = 0;
		var j = 0;
		var amount = 0;
		var premium = 0;
		var tolAmount = 0;
		if (itemKindCount == null) {
			sumAmount = 0;
			sumPremium = 0;
		} else {
			for (i = 1; i <= itemKindCount; i++) { //premium += parseFloat(DangerItemForm.ItemPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
				//sumPremium += parseFloat(DangerItemForm.ItemPremium[i].value);
				if (DangerItemForm.ItemcalculateFlag[i].value == "Y") {
					tolAmount += parseFloat(DangerItemForm.ItemAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
				}
				tolPremium = DangerItemForm.tolPremium[1].value;
			}
		}
		premium = round(premium, 2);
		DangerItemForm.dangerNo[1].value = DangerItemForm.hiDangerNo.value;
		DangerItemForm.dangerDesc[1].value = '危險單位描述';
		DangerItemForm.dangerAddress[1].value = DangerItemForm.ItemAddressName[1].value;
		//DangerItemForm.dangerItemKind[1].value = 'AAA';
		DangerItemForm.riskLevel[1].value = '風險等級';
		DangerItemForm.riskLevelDesc[1].value = '風險等級描述';
		DangerItemForm.retCurrency[1].value = "";
		DangerItemForm.retentionValue[1].value = "";
		DangerItemForm.currency[1].value = DangerItemForm.ItemCurrency[1].value;
		DangerItemForm.amount[1].value = tolAmount;
		DangerItemForm.premium[1].value = tolPremium;
		DangerItemForm.dangerShare[1].value = "100.0000";
	}
	//add begin by zhaijq 20060112 默认标的类型为其他“Z99”
	var dangerItemKindCount = DangerItemForm.dangerItemKind.length;
	for (var i = 1; i < dangerItemKindCount; i++) {
		if (DangerItemForm.dangerItemKind[i].value == "") {
			for (var j = 0; j < DangerItemForm.itemKind[i].length; j++) {
				if (DangerItemForm.itemKind[i].options[j].value == "Z99") {
					DangerItemForm.itemKind[i].options[j].selected = true;
					break;
				}
			}
		}
	}
	//add end  by zhaijq 20060112
}

//批单在查看危险单位子信息时初始化危险单位输入框

function initEndorseDangerUnitAtItem() {
	//////////////
	var dangerUnitCount = DangerItemForm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		insertRow('DangerUnit');
	}
	var isNewDanger = DangerItemForm.isNewDangerInfo.value; //新增危险单位时为1
	if (DangerItemForm.itemKindNo.length != null && isNewDanger == 1) {
		var itemKindCount = DangerItemForm.itemKindNo.length - 1;
		var i = 0;
		var j = 0;
		var amount = 0;
		var premium = 0;
		var tolAmount = 0;
		if (itemKindCount == null) {
			sumAmount = 0;
			sumPremium = 0;
		} else {
			for (i = 1; i <= itemKindCount; i++) { //premium += parseFloat(DangerItemForm.ItemPremium[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
				//sumPremium += parseFloat(DangerItemForm.ItemPremium[i].value);
				if (DangerItemForm.ItemcalculateFlag[i].value == "Y") {
					tolAmount += parseFloat(DangerItemForm.ItemAmount[i].value) * parseFloat(DangerItemForm.exchangeRate[i].value);
				}
				tolPremium = DangerItemForm.tolPremium[1].value;
			}
		}
		premium = round(premium, 2);
		DangerItemForm.dangerNo[1].value = DangerItemForm.hiDangerNo.value;
		DangerItemForm.dangerDesc[1].value = '輸入描述';
		DangerItemForm.dangerAddress[1].value = DangerItemForm.ItemAddressName[1].value;
		//DangerItemForm.dangerItemKind[1].value = 'AAA';
		DangerItemForm.riskLevel[1].value = '';
		DangerItemForm.riskLevelDesc[1].value = '';
		DangerItemForm.retCurrency[1].value = "";
		DangerItemForm.retentionValue[1].value = "";
		DangerItemForm.currency[1].value = DangerItemForm.ItemCurrency[1].value;
		DangerItemForm.amount[1].value = tolAmount;
		DangerItemForm.chgAmount[1].value = 0.00;
		DangerItemForm.premium[1].value = tolPremium;
		DangerItemForm.chgPremium[1].value = 0.00;
		DangerItemForm.dangerShare[1].value = "100.0000";
	}
	/////////////////////

}


//批单危险单位子信息页面关闭，提示保存

function endorseDangerItemTipBeforeClose() {
	var index = DangerItemForm.openerIndex.value;
	if (confirm("請確認妳保存了當前信息後，再關閉?") == true) {
		window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
		window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
		window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;

		if (DangerItemForm.dangerItemFlag[1].checked == true) { //标识位
			window.opener.fm.dangerItemFlag[index].checked = true;
		} else {
			window.opener.fm.dangerItemFlag[index].checked = false;
		}

		window.opener.fm.riskLevel[index].value = DangerItemForm.riskLevel[1].value;
		window.opener.fm.riskLevelDesc[index].value = DangerItemForm.riskLevelDesc[1].value;

		if (DangerItemForm.classCode.value == '27') {
			window.opener.fm.speCurrency[index].value = DangerItemForm.speCurrency[1].value;
			window.opener.fm.speValue[index].value = DangerItemForm.speValue[1].value;
		}
		window.opener.fm.retCurrency[index].value = DangerItemForm.retCurrency[1].value;
		window.opener.fm.retentionValue[index].value = DangerItemForm.retentionValue[1].value;
		window.opener.fm.currency[index].value = DangerItemForm.currency[1].value;
		window.opener.fm.dangerShare[index].value = DangerItemForm.dangerShare[1].value;
		window.opener.fm.amount[index].value = DangerItemForm.amount[1].value;
		window.opener.fm.chgAmount[index].value = DangerItemForm.chgAmount[1].value;
		window.opener.fm.premium[index].value = DangerItemForm.premium[1].value;
		window.opener.fm.chgPremium[index].value = DangerItemForm.chgPremium[1].value;
		window.close();
	}

}


function tipBeforeClose() {
	if (confirm("妳確定不保存當前的數據信息嗎?") == true) {
		window.close();
	}

}
//modify begin 20060609 by lihua
//提交分入

function reinsVerify() {
	var proposalNo = fm.proposalNo.value;
	var riskCode = fm.riskCode.value;
	var hiClassCode = fm.hiClassCode.value;
	fm.method = "post";
	var submitStr = "/claim/EnquiryFacade.do?type=reinsVerify&proposalNo=" + proposalNo + "&riskCode=" + riskCode + "&hiClassCode=" + hiClassCode;
	window.open(submitStr, '提交分入確認', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

}
//modify end 20060609 by lihua

//分保试算

function simulateReinsByDanger() {
	//判断是否做了风险评估
	if (checkRetenValueIsZero() == false) {
		return false;
	}

	var dangerNoCount = fm.dangerNo.length;
	var sPremium = 0;
	var flag1 = false;
	var flag2 = false;
	var flag4 = false;
	for (i = 1; i < dangerNoCount; i++) {
		sPremium += parseFloat(fm.premium[i].value);
	}
	sPremium = mathRound(parseFloat(sPremium));
	if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
		//modify begin by zhaijq 20051227 从共保不校验总保额与危险单位总保额的大小
		if (fm.dangerCoinsFlag[1].value == "2") {
			window.alert("從共保業務，份額保費爲:" + parseFloat(fm.tolPremium.value) +
					"\n" + "    總保費爲:" + parseFloat(sPremium));
			} else {
				window.alert("拆分危險單位後的總保費與原保費不相等，不能進行分保試算，" + "\n" + "    原始保費爲:" + parseFloat(fm.tolPremium.value) +
					"\n" + "    現在保費爲:" + parseFloat(sPremium));
				return;
		}
		//modify end by zhaijq 20051227
	}
	//意健险团体单必须先录入PML值才能进行分保试算
	for (var i = 1; i < getElementCount("speValue", fm); i++) {
		//modify begin by zhaijq 20060414 2799含意外险要求输入PML值
		//if (parseFloat(fm.speValue[i].value)==0.00  && (fm.hiClassCode.value == "26" || fm.hiClassCode.value == "27") && fm.policyType.value == "02")
		if (parseFloat(fm.speValue[i].value) == 0.00 && (fm.hiClassCode.value == "26" || fm.hiClassCode.value == "27") && fm.policyType.value == "02" && fm.includeAccident.value == "Y")
		//modify end by zhaijq 20060414 
		{
			alert("請先輸入PML值再進行分保試算！");
			return;
		}
	}
	var ClassCode = fm.hiClassCode.value;
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/claim/ReinsTrialInfo.do?CertiNo=" + CertiNo + "&CertiType=" + CertiType + "&ClassCode=" + ClassCode;
	window.open(submitStr, '分保試算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

//分批试算

function endorseSimulateReinsByDanger() {
	//需要拆分危险单位的险种的处理--Start
	var dangerNoCount = fm.dangerNo.length;
	var sPremium = 0;
	var flag1 = false;
	var flag2 = false;
	var flag4 = false;
	for (i = 1; i < dangerNoCount; i++) {
		sPremium += parseFloat(fm.premium[i].value) + parseFloat(fm.chgPremium[i].value);
	}
	sPremium = mathRound(parseFloat(sPremium));
	if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
		//modify begin by zhaijq 20060214 从共保保额保费不再重新计算
		if (fm.coinsFlag.value == "2") {
			window.alert("從共保業務，份額保費爲:" + parseFloat(fm.tolPremium.value) +
					"\n" + "    總保費爲:" + parseFloat(sPremium));
			} else {
				window.alert("拆分危險單位後的總保費與原保費不相等，不能進行分保試算，" + "\n" + "    原始保費爲:" + parseFloat(fm.tolPremium.value) +
					"\n" + "    現在保費爲:" + parseFloat(sPremium));
				return;
		}
		//modify end by zhaijq 20060214
	}
	var CertiNo = fm.hiBusinessNo.value;
	var ClassCode = fm.hiClassCode.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/claim/ReinsTrialInfo.do?CertiNo=" + CertiNo + "&CertiType=" + CertiType + "&ClassCode=" + ClassCode;
	window.open(submitStr, '分保试算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

//根据当前自留额判断是否做了风险评估

function checkRetenValueIsZero() {
	var retenValue = 0;
	var dangerNoCount = parseInt(getElementCount("dangerNo", fm));
	var classCode = fm.hiClassCode.value;
	if (classCode == "01" || classCode == "07" || classCode == "08" ||
		classCode == "09" || classCode == "10") {
		for (i = 1; i < dangerNoCount; i++) {
			if (fm.riskLevel[i].value == "") {
				alert("請先進行風險評估後再分保試算");
				return false;
			}
		}
	}
	return true;
}

//车险的分保试算

function simulateReinsByCar() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/claim/ReinsTrialInfo.do?CertiNo=" + CertiNo + "&CertiType=" + CertiType;
	window.open(submitStr, '分保試算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

//分摊试算 add by qinyongli 2005-8-23

function simulateReinsHepei() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var ClaimNo = fm.ClaimNo.value;
	var submitStr = "/claim/ReinsTrialInfo.do?CertiNo=" + CertiNo + "&CertiType=" + CertiType + "&ClaimNo=" + ClaimNo;
	window.open(submitStr, '分攤試算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
//打开核保界面风险等级评估页面

function openDangerRiskInfo(Field) {
	var dangerNo = DangerItemForm.hiDangerNo.value;
	var businessNo = DangerItemForm.businessNo.value
	var riskCode = DangerItemForm.riskCode.value
	var classCode = DangerItemForm.classCode.value
	var businessType = DangerItemForm.hiBusinessType.value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;
	url = "/claim/CommonDangerRiskLevelFacade.do?Index=" + intIndex + "&FieldName=" + fieldName + "&ReinsCode=" + Field.value + "&businessNo=" + businessNo + "&dangerNo=" + dangerNo + "&riskCode=" + riskCode + "&classCode=" + classCode + "&businessType=" + businessType;
	window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars,dependent,width=600,height=500");
}

//add by dongyanqi 增加查看功能20051012
//查看风险等级评估

function viewDangerRiskInfo() {
	var dangerNo = DangerItemForm.hiDangerNo.value;
	var businessNo = DangerItemForm.businessNo.value
	var riskCode = DangerItemForm.riskCode.value
	var classCode = DangerItemForm.classCode.value
	var businessType = DangerItemForm.hiBusinessType.value;

	var url = "/claim/CommonDangerRiskInfoView.do?businessNo=" + businessNo + "&dangerNo=" + dangerNo + "&riskCode=" + riskCode + "&classCode=" + classCode + "&businessType=" + businessType;
	window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars,dependent,width=600,height=500");
}
//add by dongyanqi 增加查看功能20051012
//查看风险等级评估

function viewDangerRiskInfo2(field) {
	var index = getElementOrder(field);
	var dangerNo = fm.dangerNo[index].value;
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value
	var classCode = fm.hiClassCode.value
	var businessType = fm.hiBusinessType.value;
	var url = "/claim/CommonDangerRiskInfoView.do?businessNo=" + businessNo + "&dangerNo=" + dangerNo + "&riskCode=" + riskCode + "&classCode=" + classCode + "&businessType=" + businessType;
	window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars,dependent,width=600,height=500");
}


//风险等级评估

function riskEvaluate() {
	var temp1 = false;
	var temp2 = false;
	var temp3 = false;
	var temp4 = false;
	var temp5 = false;

	var flag = new Array();
	var hiItemValue = new Array();
	for (i = 0; i < 100; i++) {
		flag[i] = false;
	}

	try {
		var itemCodeCount = fm.itemCode.length;
		var itemValueCount = fm.itemValue.length;
		fm.riskKindText.value = fm.riskKind.options[fm.riskKind.selectedIndex].text;
	} catch (e) {
		return;
	}

	for (i = 1; i < itemCodeCount; i++) {
		if (fm.itemValue[i].value != "" && fm.itemValue[i].value >= 0 && fm.itemValue[i].value <= 100) {
			fm.hiItemValue[i].value = fm.itemValue[i].value;
		} else {
			window.alert("請重新輸入相應的分值(0-100)");
			fm.itemValue[i].focus();
			return;
		}

	}

	fm.method = "post";
	fm.action = "/claim/DangerRiskEvaluateFacade.do";
	fm.submit();
}

//查看风险等级评估结果

function viewRiskEvaluate() {
	var temp1 = false;
	var temp2 = false;
	var temp3 = false;
	var temp4 = false;
	var temp5 = false;

	var flag = new Array();
	var hiItemValue = new Array();
	for (i = 0; i < 100; i++) {
		flag[i] = false;
	}

	try {
		var itemCodeCount = fm.itemCode.length;
		var itemValueCount = fm.itemValue.length;
		fm.riskKindText.value = fm.riskKind.options[fm.riskKind.selectedIndex].text;
	} catch (e) {
		return;
	}

	for (i = 1; i < itemCodeCount; i++) {
		if (fm.itemValue[i].value != "" && fm.itemValue[i].value != 0) {
			fm.hiItemValue[i].value = fm.itemValue[i].value;
		} else {
			window.alert("請重新輸入相應的分值，分值不能爲空或0");
			fm.itemValue[i].focus();
			return;
		}

	}

	fm.method = "post";
	fm.action = "/claim/DangerRiskEvaluateFacade.do?operateType=view";
	fm.submit();
}

//风险等级评估结果回显到危险单位子信息

function reRetenValue() {
	try {
		if (fm.riskLevel.value == '' || fm.riskLevelDesc.value == '' || fm.currency.value == '' || fm.retentionValue.value == '') {}
	} catch (ee) {
		return;
	}
	window.opener.DangerItemForm.riskLevel[1].value = fm.riskLevel.value;
	window.opener.DangerItemForm.riskLevelDesc[1].value = fm.riskLevelDesc.value;
	window.opener.DangerItemForm.retCurrency[1].value = fm.currency.value;
	window.opener.DangerItemForm.retentionValue[1].value = fm.retentionValue.value;
	window.close();

}

function reRetenValue2() {
	try {
		var count = fm.chooseFlag.length;
		var flag = 0;
		if (count > 0) {
			for (i = 0; i < count; i++) {
				if (fm.chooseFlag[i].checked) {
					window.opener.DangerItemForm.riskLevel[1].value = fm.riskLevel[i - 1].value;
					window.opener.DangerItemForm.riskLevelDesc[1].value = fm.riskLevelDesc[i - 1].value;
					window.opener.DangerItemForm.retCurrency[1].value = fm.currency[i - 1].value;
					window.opener.DangerItemForm.retentionValue[1].value = fm.retentionValue[i - 1].value;
					flag = 1;
				}
			}
		}

		if (flag == 0) {
			window.alert("至少選擇一項");
			return;
		}
	} catch (ee) {
		window.opener.DangerItemForm.riskLevel[1].value = fm.riskLevel.value;
		window.opener.DangerItemForm.riskLevelDesc[1].value = fm.riskLevelDesc.value;
		window.opener.DangerItemForm.retCurrency[1].value = fm.currency.value;
		window.opener.DangerItemForm.retentionValue[1].value = fm.retentionValue.value;
	}

	window.close();
}

//针对整个投保单的风险等级评估结果回显

function reAllRetenValue() {
	try {
		if (fm.riskLevel.value == '' || fm.riskLevelDesc.value == '' || fm.currency.value == '' || fm.retentionValue.value == '') {}
	} catch (ee) {
		return;
	}
	window.opener.fm.allRiskLevel.value = fm.riskLevel.value;
	window.opener.fm.allRiskLevelDesc.value = fm.riskLevelDesc.value;
	window.opener.fm.allRetentionCurrency.value = fm.currency.value;
	window.opener.fm.allRetentionValue.value = fm.retentionValue.value;
	window.close();
}

//针对整个投保单的风险等级评估结果回显

function reAllRetenValue2() {
	try {
		var count = fm.chooseFlag.length;

		var flag = 0;

		if (count > 0) {
			for (i = 0; i < count; i++) {
				if (fm.chooseFlag[i].checked) {
					window.opener.fm.allRiskLevel.value = fm.riskLevel[i - 1].value;
					window.opener.fm.allRiskLevelDesc.value = fm.riskLevelDesc[i - 1].value;
					window.opener.fm.allRetentionCurrency.value = fm.currency[i - 1].value;
					window.opener.fm.allRetentionValue.value = fm.retentionValue[i - 1].value;
					flag = 1;
				}
			}
		}

		if (flag == 0) {
			window.alert("至少選擇一項");
			return;
		}
	} catch (ee) {
		window.opener.fm.allRiskLevel.value = fm.riskLevel.value;
		window.opener.fm.allRiskLevelDesc.value = fm.riskLevelDesc.value;
		window.opener.fm.allRetentionCurrency.value = fm.currency.value;
		window.opener.fm.allRetentionValue.value = fm.retentionValue.value;
	}

	window.close();
}

//划分危险单位信息主界面时双击评估等级

function openEvaluateRiskInfo(Field) {
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value;
	var businessType = fm.hiBusinessType.value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;

	url = "/claim/CommonDangerRiskLevelFacade.do?Index=" + intIndex + "&FieldName=" + fieldName + "&ReinsCode=" + Field.value + "&businessNo=" + businessNo + "&riskCode=" + riskCode + "&businessType=" + businessType;

	window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
}

function setDangerItemFlag() {
	if (DangerItemForm.dangerItemKind[1].value != "001") {
		DangerItemForm.dangerItemFlag[1].checked = true;
	} else {
		DangerItemForm.dangerItemFlag[1].checked = false;
	}
}

function setAllDangerItemFlag() {
	if (fm.itemKind.value != "Z99") {
		fm.allDangerFlag.checked = true;
	} else {
		fm.allDangerFlag.checked = false;
	}
}

function allowClick() {
	if (fm.itemKind.value != "Z99" || fm.itemKind.options[fm.itemKind.selectedIndex].text != "其他") {
		if (fm.allDangerFlag.checked == true) {
			fm.allDangerFlag.checked = true;
		} else {

			fm.allDangerFlag.checked = false;
		}
	} else {
		//window.alert("标的类型为其他，不允许修改");
		fm.allDangerFlag.checked = false;
		return false;
	}
}

function pointTwo(s) {
	return point(s, 0);
}

//对数字第三位四舍五入

function mathRound(number) {
	return round(number, 2);
}

function round(number, precision) {
	if (isNaN(number))
		number = 0;
	var prec = Math.pow(10, precision);
	var result = Math.round(number * prec);
	result = result / prec;
	return result;
}

function validateDangerData() {
	try {
		var dangerNoCount = fm.dangerNo.length;
		var maxDangerNo = fm.dangerNo[dangerNoCount - 1].value
		var tempDangerShare = 0;
		var errDangerShareValue = 0;
		var businessNo = fm.hiBusinessNo.value;
		var businessType = fm.hiBusinessType.value;
		var tempDangerShare2 = 0;
		var newDanagerShare = 0;
		for (i = 1; i < dangerNoCount; i++) {
			tempDangerShare += parseFloat(fm.dangerShare[i].value);
		}
		if (parseFloat(tempDangerShare) == 100) {
			//window.alert("占比正确");
			return;
		}
		errDangerShareValue = round(Math.abs(tempDangerShare - 100.00), 4);
		if (parseFloat(errDangerShareValue) > 0.0001) //占比比例误差大於0.001
		{
			window.alert("需要重新劃分危險單位,占比比例誤差較大,總和不等於100%");
		} else { //占比比例误差等於0.0001时，自动补0.0001
			for (i = 1; i < dangerNoCount - 1; i++) { //求出前几个危险单位的占比比例
			}
			newDanagerShare = round(parseFloat(100 - tempDangerShare2), 4);
			fm.dangerShare[dangerNoCount - 1].value = newDanagerShare;
			var url = "/claim/DangerInfoErrorModifyFacade.do?&errDangerShareValue=" + newDanagerShare + "&maxDangerNo=" + maxDangerNo + "&businessNo=" + businessNo + "&businessType=" + businessType;
			var newWindow = window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
			newWindow.focus();
			newWindow.close();
		}
	} catch (ee) {}
}

//提交任务时点取消返回查询页面

function backQuery() {
	fm.action = "/claim/taskMessage.do";
	fm.submit();
}

function submitEndorseTaskBefore(submitDirection) {
	if (isEmptyField(fm.HandleText)) {
		alert("系統信息:\n\n" + "請填寫審批意見！");
		fm.HandleText.focus();
		return false;
	}
	fm.DealType.value = "submit";
	fm.SubmitDirection.value = submitDirection;
	fm.target = "fraInterface";
	fm.action = "/claim/CommonDealTask.do";
	fm.submit();
}

//add by dongyanqi核保查询页面增加显示风险评估信息

function showEvaluateRiskInfo(Field) {
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value;
	var businessType = fm.hiBusinessType.value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;

	url = "/claim/ShowEvaluateRiskInfo.do?Index=" + intIndex + "&FieldName=" + fieldName + "&ReinsCode=" + Field.value + "&businessNo=" + businessNo + "&riskCode=" + riskCode + "&businessType=" + businessType;
	// alert("url===="+URL);

	window.open(url, "editwindow", "top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
}

function showSimulateReins() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/claim/ReinsTrialInfo.do?CertiNo=" + CertiNo + "&CertiType=" + CertiType;
	window.open(submitStr, '分保试算', 'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

function comfirmRefuse() {
	if (confirm("確實要拒保嗎？")) {
		submitRefuse();
	}
}

// add by zhulei 20050725 拒保时，对审核意见必录的校验

function checkTextArea() {
	if (isEmptyField(fm.HandleText)) {
		alert("拒保，請填寫審批意見！");
		return false;
	}
	return true;
}

function forbidDelete() {
	alert("不允許刪除！");
	return;
}

function checkSpeValue(field) {
	var curSpeValue = parseFloat(field.value);
	for (var i = 1; i < DangerItemForm.speValue.length; i++) {
		if (curSpeValue > parseFloat(DangerItemForm.amount[i].value)) {
			alert("意健險PML值不允許大於每一危險單位保額！");
			field.select();
			field.focus();
			return;
		}
		//add begin by zhaijq 20060316 PML值不允许为负值
		if (curSpeValue < 0) {
			alert("意健險PML值不允許小於0！");
			field.select();
			field.focus();
			return;
		}
		//add end by zhaijq 20060316
	}
	return;
}

/* add by xiaojian 20051204 begin reason：投保（保）单核保时可见、可修改特殊因子 */
function changeDisFee1(field) {
	var dblDisRate1Old = 0;
	var dblDisRate1 = 0;
	var dblDisFee1 = 0;

	dblDisRate1Old = parseFloat(fm.DisRate1Old.value);
	dblDisRate1 = parseFloat(fm.DisRate1.value);
	if (isNaN(dblDisRate1Old))
		dblDisRate1Old = 0;
	if (isNaN(dblDisRate1))
		dblDisRate1 = 0;

	if (dblDisRate1Old != dblDisRate1 &&
		confirm("您是否確定修改特殊因子比例？")) {
		dblDisRate1 = parseFloat(fm.DisRate1.value);
	} else {
		dblDisRate1 = parseFloat(fm.DisRate1Old.value);
	}
	if (isNaN(dblDisRate1))
		dblDisRate1 = 0;
	dblDisFee1 = calculateDisFee1(dblDisRate1);
	//xiaojian_leave：四舍五入的问题
	fm.DisRate1.value = point(dblDisRate1, 0);
	fm.DisFee1.value = point(dblDisFee1, 0);
	fm.target = "fraSubmit";
	fm.action = "/claim/SaveDisRate1.do";
	fm.submit();
	fm.target = "fraInterface";
}

function calculateDisFee1(dblDisRate1) {
	var dblPremium1 = 0;
	var dblDisFee1 = 0;
	dblPremium1 = parseFloat(fm.Premium1.value);
	if (isNaN(dblPremium1))
		dblPremium1 = 0;
	dblDisFee1 = dblPremium1 * dblDisRate1 / 100;
	return dblDisFee1;
}
/* add by xiaojian 20051204 end */

/* add by zhulei 20060424 begin 核保时可见、可修改费用比例 */
function changeManageFeeRate(field) {
	var dblManageFeeRateOld = 0;
	var dblManageFeeRate = 0;
	var dblManageFee = 0;

	dblManageFeeRateOld = parseFloat(fm.ManageFeeRateOld.value);
	dblManageFeeRate = parseFloat(fm.ManageFeeRate.value);
	if (isNaN(dblManageFeeRateOld))
		dblManageFeeRateOld = 0;
	if (isNaN(dblManageFeeRate))
		dblManageFeeRate = 0;

	if (dblManageFeeRateOld != dblManageFeeRate &&
		confirm("您是否确定修改费用比例？")) {
		dblManageFeeRate = parseFloat(fm.ManageFeeRate.value);
	} else {
		dblManageFeeRate = parseFloat(fm.ManageFeeRateOld.value);
	}
	if (isNaN(dblManageFeeRate))
		dblManageFeeRate = 0;
	dblManageFee = calculateManageFee(dblManageFeeRate);
	fm.ManageFeeRate.value = point(dblManageFeeRate, 0);
	fm.ManageFee.value = point(dblManageFee, 0);
	fm.target = "fraSubmit";
	fm.action = "/claim/SavePrpExpense.do";
	fm.submit();
	fm.target = "fraInterface";
}

function calculateManageFee(dblManageFeeRate) {
	var dblPremium = 0;
	var dblManageFee = 0;
	dblPremium = parseFloat(fm.Premium2.value);
	if (isNaN(dblPremium))
		dblPremium = 0;
	dblManageFee = dblPremium * dblManageFeeRate / 100;
	return dblManageFee;
}
//add by zhulei 20060424 end 核保时可见、可修改费用比例 */

//add by liuhqi begin 2006-10-11 
//危险单位子信息中除外责任和进合约的选择时.
//如果是Z99其他，则不允许修改进合约标志,

function checkDangerItemFlag(flag) {
	if (fm.itemKind[1].value == "Z99") {
		fm.dangerItemFlag[1].checked = true;
	} else if (flag == 1) {
		fm.dangerItemFlag[1].checked = false;
	}

}
//add by liuhaiqi end 2006-10-11 

//"放弃任务"按钮响应函数 add by xukefeng 2006-12-1

function undoTask(field) {
	if (!confirm("確認要放棄任務？")) {
		return false;
	}
	fm.action = "/claim/CommonDealTask.do?DealType=undo";
	disabledButton(field);
	fm.submit();
}

//按钮单击事件，用於相同保单号码多报案的显示 add by huabaoguo 2009-01-06

function buttonOnClick(actionName, policyNo, curRegistNo) {
	var messagedo = "/claim/" + actionName + ".do?policyNo=" + policyNo + "&curRegistNo=" + curRegistNo;

	window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,top=100,left=100,width=700,Height=500");
}

/** 表单提交时灰显按钮*/
function disabledButton(field){
	if(field.name=='butSaveForm' || field.name=='passBtn' || field.name=='submitSuperior' || field.name=='submitJunior'){
		jQuery("input[type=button]").each(function(){
			jQuery(this).attr("disabled","true");
		}
	  )
	}
}

/**
 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
 * @param field
 * @returns
 */
function checkDateBetweenHaventDuplicateCase(){
	var policyNo = fm.PolicyNo.value;
	var checkresult = true;
	jQuery.ajax({
		url : contextRootPath + "/compensate/checkDateBetweenHaventDuplicateCase.do?policyNo=" + policyNo ,
		type : "POST",
		dataType :"json",
		async : false,
		success :function(data){
			if(data.duplicateCase !="0"){
				// mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改
				alert("請複查本案車輛維修項目或代步車費用是否重複計算。");
				//checkresult = false;//改為只提示不擋
			}
		}
	});
	return checkresult;
}