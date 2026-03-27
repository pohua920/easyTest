/***************************************************************************
 * Description: 处理核保任务js
 * Author     : 项目组
 * CreateDate : 2005-4-1 14:28
 * UpdateLog  ： Name       Date            Reason/Contents
 *             zhangruifeng 2007-12-17      增加在车险联合出单时审核商业险投保单时显示关联交强险投保单详细信息按钮
              yanglibo     2008-08-07      增加核保时保存要对保额值进行判断
              yanglibo     2008-08-26      把lanning增加的保单号参数去掉
 *             zhangfan     2008-09-02      showBusinessInfo方法的修改
 ****************************************************************************/

/** 检验一项任务是否可以被处理* */
function checkTask(i) {
	// 获得指定表单域的信息
	if (isNaN(fm.FlowID.length)) {
		fm.iFlowID.value = fm.FlowID.value;
		fm.iLogNo.value = fm.LogNo.value;
		fm.iBusinessNo.value = fm.BusinessNo.value;
		fm.iBusinessType.value = fm.BusinessType.value;
		fm.iContractNo.value = fm.ContractNo.value;
		fm.iPackageID.value = fm.PackageID.value;
		fm.iModelNo.value = fm.ModelNo.value;
		fm.iNodeNo.value = fm.NodeNo.value;
		fm.iFlowStatus.value = fm.FlowStatus.value;
		fm.iDeptCode.value = fm.DeptCode.value;
		fm.iFlowInTime.value = fm.FlowInTime.value;
		fm.iNodeStatus.value = fm.NodeStatus.value;
		fm.iRiskCode.value = fm.RiskCode.value;
		fm.iClassCode.value = fm.ClassCode.value;
		fm.iNodeName.value = fm.NodeName.value;
		fm.Superpay.value = fm.Superpay.value;//超商標誌
		fm.iNormastatus.value = fm.normastatus.value; //mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
		//③若‘處理狀態’是『待處理』或『正在處理』且‘作業狀態’不是『收到回覆拒保』或『收到回覆可承保』，需查詢‘人工維護開關’，若‘人工維護開關’狀態爲『開啓』，可直接進入到處理核保任務頁面做後續的人工核保處理。如果爲『關閉』則提示“ ‘拒限保’，‘名單檢測’, ‘風險評級’ 狀態未設置完成，不能進行後續處理”
		if(fm.WorkStatus.value!="03" && fm.WorkStatus.value!="04"&& fm.WorkStatus.value!="00"){//WorkStatus:作業狀態-----00爲‘不執行’，因此也不需要走AML系統
			if(fm.NodeStatus.value==1||fm.NodeStatus.value==2){//NodeStatus:處理狀態
				if(fm.ValueType.value==0){//ValueType:‘人工維護開關’狀態
					window.alert(i18n.messages.notContinue);//拒限保，名單檢測，風險評級狀態未設置完成，不能進行後續處理！
					return false;
				}
			}
		}
	} else {
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
		fm.iClassCode.value = fm.ClassCode[i].value;
		fm.iNodeName.value = fm.NodeName[i].value;
		fm.ValueType.value = fm.ValueType[i].value;
		fm.Superpay.value = fm.Superpay[i].value;//超商標誌
		fm.iNormastatus.value = fm.normastatus[i].value; //mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
		//③若‘處理狀態’是『待處理』或『正在處理』且‘作業狀態’不是『收到回覆拒保』或『收到回覆可承保』，需查詢‘人工維護開關’，若‘人工維護開關’狀態爲『開啓』，可直接進入到處理核保任務頁面做後續的人工核保處理。如果爲『關閉』則提示“ ‘拒限保’，‘名單檢測’, ‘風險評級’ 狀態未設置完成，不能進行後續處理”
		if(fm.WorkStatus[i].value!="03" && fm.WorkStatus[i].value!="04"&& fm.WorkStatus[i].value!="00"){//WorkStatus:作業狀態-----00爲‘不執行’，因此也不需要走AML系統
			if(fm.NodeStatus[i].value==1||fm.NodeStatus[i].value==2){//NodeStatus:處理狀態
				if(fm.ValueType[i].value==0){//ValueType:‘人工維護開關’狀態
					window.alert(i18n.messages.notContinue);//拒限保'，'名單檢測', '風險評級' 狀態未設置完成，不能進行後續處理!!
					return false;
				}
			}
		}
	}
	
	//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
	//批單暫時不處理
	if(fm.iBusinessType.value == "B" || fm.iBusinessType.value == "T"){
		if(fm.iNormastatus.value != "3" && fm.iNormastatus.value != "7"){
			
			var result = addressCompareAjax(fm.iBusinessNo.value, fm.iBusinessType.value);
			if(result.code == 'ERROR'){
				alert(result.msg);
				return false;
			}
			if(result.status == '1'){
				alert(i18n.messages.addressInvalid);//地址正規化未完成判定，不能進行後續處理！
				return false;
			}else{
				var obj = document.getElementById('addressFormatTd-' + i);
				obj.innerHTML = '人工判定完成';
			}
		}
	}

	//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END
	
	fm.action = "/undwrt/taskCheck/commonCheckTask.do";
	fm.method = "post";
	fm.submit();
}
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href; // 获取主机地址之后的目录，如：
	// uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName); // 获取主机地址，如：
	// http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos); // 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

// 授权控制 chengyisheng 2011-11-04
function checkAuthorizeTask(i) {
	// 获得指定表单域的信息
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
	fm.iNodeName.value = fm.NodeName[i].value;

	fm.action = "/undwrt/taskCheck/commonCheckTask.do?authorize=Authorize";
	fm.method = "post";
	fm.submit();
}
//
// /**显示业务的详细信息**/
function showBusinessInfo(ComCode) {
	var vBusinessNo = fm.hiBusinessNo.value; // 取业务号
	var vCertiType = fm.hiBusinessType.value; // 取业务类型
	var prpallIP = "http://localhost:7001";
	if(vCertiType != "B"){
		var vBizType = "";
		var vRiskCode = fm.riskCode.value; // 取险种代码
		var vCommonRisk = fm.CommonRisk.value;// 取通用险种代码
		var vRiskClass = fm.classCode.value; // 得到险类
		var vUserCode = fm.OperatorCode.value; // 得到人员
		
		var vRiskCodeTemp = vRiskCode;
		// add by zhouhui 20090722 begin 免导团单查看详细信息时，调用自己的页面
		var vPolicySort = "";
		if (fm.PolicySort) {
			vPolicySort = fm.PolicySort.value;
		}
		// add by zhouhui 20090722 end 免导团单查看详细信息时，调用自己的页面

		// add by zhulei 20060426 增加登陆机构传参myComCode

		var vMyComCode = "";
		if (ComCode != null && ComCode != "") {
			vMyComCode = ComCode;
		} else {
			vMyComCode = fm.comCode.value;
		}
	}
	
	if (vCertiType == "T" || vCertiType == "P") {
		//查询承保的要保书地址连接
		var vURL = prpallIP + "/prpins/policy/browseproposalForCommonview.do?bizNo=" + vBusinessNo + "&riskCode=" + vRiskCode + "&systemCode=commonview";
		vURL = '' + vURL;
		// add by zhouhui 20090722 begin 免导团单查看详细信息时，调用自己的页面
		if (vPolicySort == 'M') {
			vURL = prpallIP
					+ '/prpins/commonship/tbcbpg/UIPrPoEnCollectShow.jsp?BIZTYPE='
					+ vBizType + '&SHOWTYPE=SHOW&BizNo=' + vBusinessNo
					+ '&RiskCode=' + vRiskCode + '&UserCode=' + vUserCode
					+ '&myComCode=' + vMyComCode;
		}
		// add by zhouhui 20090722 end 免导团单查看详细信息时，调用自己的页面
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "E") {
		//查询承保的批单信息连接地址
		var vURL = prpallIP + "/prpins/endorse/browseEndorseForClaim.do?applyNo=" + vBusinessNo + "&systemCode=claim";
		vURL = '' + vURL;
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "B") {
		//查询承保系统报价单查询地址
		var vURL = prpallIP + "/prpins/policy/browseproposalForCommonview.do?proposalNo=" + 
			vBusinessNo + "&editType=PO_VIEW&visaFlag=0&SysCode=quotation";
		vURL = '' + vURL;
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "C") {
		/* 核赔实赔信息 */
		var vURL = "";

		vURL = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo="
				+ vBusinessNo
				+ "&editType=SHOW&riskCode="
				+ vRiskCode
				+ "&paramUndwrtCompe=DAA";

		/*
		 * if(vRiskClass == "J" || vRiskClass == "G"||vRiskClass ==
		 * "B"||vRiskClass ==
		 * "T"||vRiskClass=="I"||vRiskClass=="S"||vRiskClass=="H"||vRiskClass=="L")
		 * vRiskClass = "Q"; if(vRiskClass == "Q" || vRiskClass == "Z") { vURL =
		 * '/prpall/'+ "ClassCodeQ" + '/lp/compensate/UIL' + vRiskClass +
		 * 'CompensateShow.jsp?CompensateNo=' + vBusinessNo ; } else if
		 * (vRiskClass=="E") { vURL = '/prpall/'+ "ClassCodeE" +
		 * '/lp/compensate/UIL' + vRiskClass +
		 * 'CompensateShow.jsp?CompensateNo=' + vBusinessNo ; } else {
		 * if(vRiskCode=="DAS") vRiskCode="0501"; vURL = '/prpall/'+ vRiskCode +
		 * '/lp/compensate/UIL' + vRiskCode + 'CompensateShow.jsp?CompensateNo=' +
		 * vBusinessNo ; }
		 * 
		 */
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

	} else if (vCertiType == "Y") {
		/* 核赔实赔信息 */
		var vURL = "";

		vURL = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo="
				+ vBusinessNo + "&editType=SHOW&riskCode=" + vRiskCode;

		/*
		 * if(vRiskClass == "Q" || vRiskClass == "Z" || vRiskClass == "J" ||
		 * vRiskClass == "G" ||vRiskClass == "B"||vRiskClass == "T"||vRiskClass ==
		 * "Y"||vRiskClass == "E"||vRiskClass ==
		 * "S"||vRiskClass=="H"||vRiskClass=="I") { vURL = '/prpall/'+
		 * "commonship" + '/lp/prepay/UILPrepayShow.jsp?PreCompensateNo=' +
		 * vBusinessNo ; } else { //核赔预赔信息 if(vRiskCode=="DAS")
		 * vRiskCode="0501"; var vURL = '/prpall/'+ vRiskCode + '/lp/prepay/UIL' +
		 * vRiskCode + 'PrepayShow.jsp?PreCompensateNo=' + vBusinessNo ; }
		 */
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else {
		errorMessage(i18n.messages.cannotConfirmDetail);
	}
}
//修改报价单保费，费率等信息
function editBusinessInfo() {
	var vBusinessNo = fm.hiBusinessNo.value; // 取业务号
	var vCertiType = fm.hiBusinessType.value; // 取业务类型
	var riskCode=fm.riskCode.value;
	var prpallIP = fm.PrpallIp.value;
		//查询承保系统报价单查询地址
		var vURL = prpallIP+ "/prpins/policy/updateProposalForUndwrt.do?bizNo=" + 
			vBusinessNo + "&editType=PO_VIEW&riskCode="+riskCode+"&systemCode=undwrt&SysCode=quotation";
		vURL = '' + vURL;
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		//window.showModalDialog(vURL,riskCode,"dialogWidth=1000px;dialogHeight=500px;dialogTop=15px;dialogLeft=10px;scroll=1;resizable=1;status=0");

}


// //选择审批片语
function changeNotion(field) {
	var strNotion = field.value;
	if (isEmptyField(fm.HandleText) && !isEmptyField(field)) {
		fm.HandleText.value = strNotion;
	} else {
		if (!isEmptyField(field))
			fm.HandleText.value = fm.HandleText.value + "\n" + strNotion;
	}
}

//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 
function validateMember(){
	var validate1 = fm.validIdentifyNumber.value;
	var validate2 = fm.validstatusUsercode.value;
	if(validate1 == "false"){
		alert("業務人員失效請下發修改");
		return false;
	}
	if(validate2 == "false"){
		alert("服務人員失效請下發修改");
		return false;
	}
	return true;
}
//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end 

// 隐藏下发修改
function changeNotion1(field) {
	var NotionCname = field.options[field.selectedIndex].text;
	var strNotion = field.value;
	fm.HandleText.value = "";
	
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   start 原因  業務人員失效減核問題-核保系統檢核 
	if(strNotion == "001" && !validateMember()){
		field.value = fm.notionFlag.value;
		return;
	}else{
		fm.notionFlag.value = field.value;
	}
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end 
	
	// modify by zhangfan 03-23-2007 操作不同，可使用的按钮不同。
	// modify by yanglibo 2010-02-03 reason:TASK-2956
	if (strNotion == "001" || strNotion == "006" || strNotion == "007") {
		fm.submitJunior.disabled = "enable";// 下发
		fm.butCancel.disabled = "enable";// 放弃
	} else {
		fm.submitJunior.disabled = "";// 下发
		fm.butCancel.disabled = "";// 放弃
	}

	if (strNotion == "002" || strNotion == "003" || strNotion == "004") {
		fm.passBtn.disabled = "enable";// 通过
		fm.submitSuperior.disabled = "enable";// 上交
	} else {
		if (strNotion == "005") {
			fm.passBtn.disabled = "enable";// 通过
		} else {
			fm.passBtn.disabled = "";// 通过
			fm.submitSuperior.disabled = "";// 上交
		}
		fm.submitSuperior.disabled = "";// 上交
	}
	// modify by zhangfan END
	//add by xuhuiling 需求150 當狀態是2時判斷 begin
//	if(strNotion == "002"){
////		alert(fm.workStatus.value);
////		if(fm.workStatus.value!=null){
//		var workStatus = fm.workStatus.value;
//		}
//		var valueType = fm.valueType.value;
//		if(workStatus!="03"){
//			//0為關閉，1為開啟
//			if(valueType==0){
//				alert("'拒限保'，'名單檢測', '風險評級' 狀態未設置完成，不能進行後續處理");
//				return false;
//			}else{
//				fm.workStatus.value = "07";
//				alert(fm.workStatus.value);
//			}
//		}
//	}
	//add by xuhuiling 需求150 當狀態是2時判斷 end
	if (isEmptyField(fm.HandleText) && !isEmptyField(field)) {
		fm.HandleText.value = NotionCname;
	} else {
		if (!isEmptyField(field))
			fm.HandleText.value = fm.HandleText.value + "\n" + NotionCname;
	}
}

function changeNotion2(field) {
	var NotionCname = field.options[field.selectedIndex].text;
	var strNotion = field.value;
	fm.HandleText.value = "";
	// modify by zhangfan 03-23-2007 操作不同，可使用的按钮不同。
	// modify by yanglibo 2010-02-03 reason:TASK-2956
	if (strNotion == "001" || strNotion == "006" || strNotion == "007") {
		fm.submitJunior.disabled = "enable";// 下发
		// fm.butCancel.disabled ="enable";//放弃
	} else {
		fm.submitJunior.disabled = "";// 下发
		// fm.butCancel.disabled ="";//放弃
	}

	if (strNotion == "002" || strNotion == "003" || strNotion == "004") {
		fm.passBtn.disabled = "enable";// 通过
		fm.submitSuperior.disabled = "enable";// 上交
	} else {
		if (strNotion == "005") {
			fm.passBtn.disabled = "enable";// 通过
		} else {
			fm.passBtn.disabled = "";// 通过
			fm.submitSuperior.disabled = "";// 上交
		}
		fm.submitSuperior.disabled = "";// 上交
	}
	// modify by zhangfan END
//	if(strNotion == "002"){
//		if(fm.workStatus.value!=null){
//		var workStatus = fm.workStatus.value;
//		}
//		var valueType = fm.valueType.value;
//		if(workStatus!="03"){
//			//0为关闭，1为开启
//			if(valueType==0){
//				alert("'拒限保'，'名單檢測', '風險評級' 狀態未設置完成，不能進行後續處理");
//				return false;
//			}else{
////				alert("workStatus");
////				fm.refuseLimiteInsurance.value = "";
////				fm.listDetection.value = "";
////				fm.riskRating.value = "";
//				fm.workStatus.value = "07";
//				alert(fm.workStatus.value);
//			}
//		}
//	}
	//add by xuhuiling 需求150 當狀態是2時判斷
	if (isEmptyField(fm.HandleText) && !isEmptyField(field)) {
		fm.HandleText.value = NotionCname;
	} else {
		if (!isEmptyField(field))
			fm.HandleText.value = fm.HandleText.value + "\n" + NotionCname;
	}
}

// 查看轨迹信息
function viewTranceInfo() {
	var submitStr;
	var BusinessNo;
	BusinessNo = fm.BusinessNo.value;
	var businessType = fm.BusinessType.value;
	// fm.target = "fraSubmit";
	submitStr = "/undwrt/common/commonViewTrace.do?businessNo=" + BusinessNo
			+ "&businessType=" + businessType;
	window
			.open(
					submitStr,
					i18n.messages.trackInfor,
					'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// 查看规则引擎返回信息yanglibo 20081027 begin
function viewIlogInfo() {
	var FlowId;
	var LogNo;
	var submitStr;
	FlowId = fm.FlowId.value;
	LogNo = fm.LogNo.value;
	submitStr = "/undwrt/ilogInfo.do?FlowId=" + FlowId + "&LogNo=" + LogNo;
	window
			.open(
					submitStr,
					i18n.messages.trackInfor,
					'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
function viewIlogMainSubInfo() {
	var FlowId;
	var LogNo;
	var strMainPolicyNo
	var submitStr;
	FlowId = fm.FlowId.value;
	LogNo = fm.LogNo.value;
	strMainPolicyNo = fm.strMainPolicyNo.value;
	submitStr = "/undwrt/ilogInfo.do?FlowId=" + FlowId + "&LogNo=" + LogNo
			+ "&strMainPolicyNo=" + strMainPolicyNo;
	window
			.open(
					submitStr,
					i18n.messages.trackInfor,
					'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

}

// add by yanglibo 20090812 begin reason:显示保单的历年承保、理赔信息
function viewPolicyClaimInfo() {
	var businessNo;
	var itype;
	if (fm.BusinessType.value == 'E') {
		businessNo = fm.hiPolicyNo.value;
	} else if (fm.BusinessType.value == 'T') {
		businessNo = fm.proposalNo.value;
	}
	itype = fm.BusinessType.value;
	var submitStr = "/undwrt/CommonBIPolicyClaimView.do?BusinessNo="
			+ businessNo + "&BusinessType=" + itype;
	window
			.open(
					submitStr,
					i18n.messages.historyInfor,
					'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// add by yanglibo 20090812 begin reason:显示保单的历年承保、理赔信息

// 查看资料信息
function showMaterialInfo() {
	var submitStr;
	var BusinessNo;
	BusinessNo = fm.BusinessNo.value;
	// fm.target = "fraSubmit";
	submitStr = "/undwrt/materialInfo.do?BusinessNo=" + BusinessNo;
	window
			.open(
					submitStr,
					i18n.messages.dataInfor,
					'width=640,height=300,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

// 保存任务
function saveTask() {
	var flag = false;

	// 需要拆分风险评估的险种的处理--Start
	if (fm.riskUnitFlag.value == '1' && fm.handType.value == '11') {
		// delete by lihua 20060515 begin
		/*
		 * var flag1 = false; var flag2 = false; var flag3 = false; var flag4 =
		 * false; var flag5 = false;
		 */
		// delete by lihua 20060515 end
		var dangerNoCount = fm.dangerNo.length;
		var sPremium = 0;

		for (i = 1; i < dangerNoCount; i++) {
			sPremium += parseFloat(fm.premium[i].value);
		}

		sPremium = mathRound(parseFloat(sPremium));

		if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
			window.alert(i18n.messages.premiumNotEqual + "\n" + "    "
					+ i18n.messages.originalPremium
					+ parseFloat(fm.tolPremium.value) + "\n" + "    "
					+ i18n.messages.presentPremium + parseFloat(sPremium));
			return;
		} else {
		}
		// delete by lihua 20060515 begin
		/*
		 * if(fm.riskLevel[1].value!="") //风险等级 { fm.hiRiskLevel.value =
		 * fm.riskLevel[1].value; flag1 = true; }else if(
		 * fm.allRiskLevel.value!="" && flag1 == false ) { fm.hiRiskLevel.value =
		 * fm.allRiskLevel.value; flag1 = true; }
		 * 
		 * if(fm.retCurrency[1].value!= "") //自留额币种 { fm.hiRetCurrency.value =
		 * fm.retCurrency[1].value; flag2 = true; }else if(
		 * fm.allRetentionCurrency.value!="" && flag2 == false ) {
		 * fm.hiRetCurrency.value = fm.allRetentionCurrency.value; flag2 = true; }
		 * 
		 * if(fm.retentionValue[1].value!="" &&
		 * parseFloat(fm.retentionValue[1].value)!=0) //自留额金额 {
		 * fm.hiRetentionValue.value = fm.retentionValue[1].value; flag3 = true;
		 * }else if( fm.allRetentionValue.value!="" && flag3 == false ) {
		 * fm.hiRetentionValue.value = fm.allRetentionValue.value; flag3= true; }
		 * 
		 * if(fm.dangerItemKind[1].value!="") //标的类型 { fm.hiDangerItemKind.value =
		 * fm.dangerItemKind[1].value; flag4= true; }else
		 * if(fm.itemKind.value!="" && flag4 == false ) {
		 * fm.hiDangerItemKind.value = fm.itemKind.value; flag4 = true; }
		 * 
		 * if(fm.riskLevelDesc[1].value!="") //风险名称 { fm.hiRiskLevelDesc.value =
		 * fm.riskLevelDesc[1].value; flag5 = true; }else if(
		 * fm.allRiskLevelDesc.value!="" && flag5 == false ) {
		 * fm.hiRiskLevelDesc.value = fm.allRiskLevelDesc.value; flag5 = true; }
		 */
		// delete by lihua 20060515 end
		if (fm.dangerItemKind[1].value != "") // 标志位
		{
			if (fm.dangerFlag[1].checked == true) {
				fm.hiDangerFlag.value = "10";
			} else {
				fm.hiDangerFlag.value = "00";
			}
		}

		// delete by lihua 20060515 begin
		/*
		 * else if(fm.allDangerFlag.checked == true) { fm.hiDangerFlag.value =
		 * "10"; } else { fm.hiDangerFlag.value = "00"; }
		 */
		// delete by lihua 20060515 end
		if (fm.retentionValue[1].value == ""
				&& fm.allRetentionValue.value == "") {
			fm.hiRetentionValue.value = 0.0;
		}
		/*
		 * if(fm.riskLevel[1].value="" && fm.allRiskLevel.value="") {
		 * errorMessage("请输入风险等级信息"); return; } if(fm.retCurrency[1].value="" &&
		 * fm.allRetentionCurrency.value= "") { errorMessage("请输入自留额币种信息");
		 * return; } if(fm.retentionValue[1].value="" &&
		 * fm.allRetentionValue.value="") { errorMessage("请输入自留额信息"); return; }
		 * 
		 * if(fm.riskLevel[1].value="" && fm.allRiskKind.value="") {
		 * errorMessage("请输入风险类别信息"); return; }
		 */
	} // 对需要拆分风险评估的处理--End---

	else if (fm.requiredReins.value == "1" && fm.handType.value == '11') {
		// window.alert("不需要拆分风险评估，但需要分保试算的特殊处理");

		// delete by lihua 20060515 begin
		/*
		 * fm.hiRiskLevel.value = fm.allRiskLevel.value;
		 * 
		 * if( fm.allRetentionCurrency.value!="") { fm.hiRetCurrency.value =
		 * fm.allRetentionCurrency.value;
		 *  }
		 * 
		 * if( fm.allRetentionValue.value!="") { fm.hiRetentionValue.value =
		 * fm.allRetentionValue.value;
		 *  }
		 * 
		 * if(fm.itemKind.value!="") { fm.hiDangerItemKind.value =
		 * fm.itemKind.value;
		 *  }
		 * 
		 * if(fm.allRiskLevelDesc.value!="") { fm.hiRiskLevelDesc.value =
		 * fm.allRiskLevelDesc.value;
		 *  }
		 * 
		 */
		// delete by lihua 20060515 end
		// modify by lihua 20060515
		if (fm.dangerFlag.checked == true) {
			fm.hiDangerFlag.value = "10";
		} else {
			fm.hiDangerFlag.value = "00";
		}
		// modify by lihua
		if (fm.retentionValue.value == "") {
			fm.hiRetentionValue.value = 0.0;
		}
	}
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}
	setButtondisable();
	fm.DealType.value = "save";
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.submit();

}
function cancelTask() {
	setButtondisable();
	fm.DealType.value = "cancel";
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.submit();

}
function sendTaskOne(){
	var sendStr;
	sendStr = "/undwrt/handleTask/commonDealTask.do?FlowId=" + fm.FlowId.value + "&&LogNo=" + fm.LogNo.value + "&&DealType=send1";
	window.open(sendStr, "改派訊息", "width=700,height=350,top=150,left=350,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0");
	
}
function sendTaskTwo(){
	var operatorCode=fm.operatorCode.value;
	if(operatorCode==""||operatorCode==null){
		alert("改派人信息不能為空！");
		return false;
	}
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do?DealType=send2";
	fm.submit();
	window.close();
	
}

/**
 * 把一个浮点数，以小数点后几位四舍五入
 * 
 * @param srcValue
 *            要舍位的值
 * @param iCount
 *            要舍位到小数点后几位
 * @return 四舍五入后的数
 * @author duhaichao
 */
function adv_format(srcValuef, iCount) {
	var srcValue = srcValuef;
	var zs = true;
	// 判断是否是负数
	if (srcValue < 0) {
		srcValue = Math.abs(srcValue);
		zs = false;
	}
	var iB = Math.pow(10, iCount);
	var value1 = srcValue * iB;
	var anumber = new Array();
	var anumber1 = new Array();

	var fvalue = value1; // 保存原值
	var value2 = value1.toString();
	var idot = value2.indexOf(".");

	// 如果是小数
	if (idot != -1) {
		anumber = srcValue.toString().split(".");
		// 如果是科学计数法结果
		if (anumber[1].indexOf("e") != -1 || anumber[1].indexOf("E") != -1) {
			return Math.round(value1) / iB;
		}

		anumber1 = value2.split(".");
		if (anumber[1].length <= iCount) {
			return parseFloat(srcValuef, 10);
		}

		var fvalue3 = parseInt(anumber[1].substring(iCount, iCount + 1), 10);
		if (fvalue3 >= 5) {
			fvalue = parseInt(anumber1[0], 10) + 1;
		} else {
			// 对于传入的形如111.834999999998 的处理（传入的计算结果就是错误的，应为111.835）
			if (fvalue3 == 4
					&& anumber[1].length > 10
					&& parseInt(anumber[1].substring(iCount + 1, iCount + 2),
							10) == 9) {
				fvalue = parseInt(anumber1[0], 10) + 1;
			} else {
				fvalue = parseInt(anumber1[0], 10);
			}
		}
	}

	// 如果是负数就用0减四舍五入的绝对值
	if (zs) {
		return fvalue / iB;
	} else {
		return 0 - fvalue / iB;
	}
}

// 保存任务前之提交
function submitTaskBefore(submitDirection) {
	// 当审批意见为空时，对其中是否有回车的判断
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}
	// 对需要拆分风险评估的险种的处理
	if (fm.riskUnitFlag.value == '1' && fm.handType.value == '11')
	{
		var dangerNoCount = fm.dangerNo.length;
		var sPremium = 0;
		var ItemKindCurrency = fm.TemCurrency.value;
		for (i = 1; i < dangerNoCount; i++)
		{
			sPremium += parseFloat(fm.premium[i].value);
		}
		sPremium = mathRound(parseFloat(sPremium));
		var TtmFL = parseFloat(sPremium) / parseFloat(fm.tolPremium.value);
		var v = parseFloat(adv_format(parseFloat(fm.tolPremium.value)
					* TtmFL, 2));
		if (parseFloat(sPremium) != v)
		{
			// 从共保保额保费不再重新计算
			if (fm.dangerCoinsFlag[1].value == "2") {
				window.alert(i18n.messages.sharePremium
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.sumPremium + parseFloat(sPremium));
			} else {
				window.alert(i18n.messages.premiumNotEqual + "\n" + "     "
						+ i18n.messages.originalPremium
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.presentPremium + parseFloat(sPremium));
				return;
			}
		}
	}

	// 增加对非车险手工定级的校验
	/*
	 * if(fm.hiClassCode.value != "05" && (fm.riskCode.value!="9997" &&
	 * fm.riskCode.value!="9998" && fm.riskCode.value!="9999" )) {
	 * if(fm.HistoryBusiness.value != "1") { if(fm.ManualGrade.value == "1,1") {
	 * alert("系统信息:\n\n"+"请对该笔业务定级！"); fm.ManualGrade.focus(); return false; } } }
	 */
	fm.DealType.value = "submit";
	setButtondisable();
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.SubmitDirection.value = submitDirection;
	fm.submit();
}

// 拒保提交
function submitRefuse() {
	setButtondisable();
	fm.DealType.value = "refuse";
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.submit();
}

// 提交任务节点
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
			fm.action = "/undwrt/submitTask/commonDealSubmit.do";
			if (fm.FlowStatus.value == "0") {
				if (confirm(i18n.messages.submitSuperiorOrNot)) {
					fm.submitTip.value = i18n.messages.submitSuperior;
					fm.ok.disabled = true;
					fm.submit();
				}
				return;
			} else if (fm.FlowStatus.value == "1") {
				// if(confirm(i18n.messages.sendUpdateOrNot))
				if (confirm("確實要下發修改嗎?")) {
					// fm.submitTip.value = i18n.messages.sendUpdate;
					fm.submitTip.value = "下發修改";
					fm.ok.disabled = true;
					fm.submit();
				}
				return;
			}
		}
	}
	errorMessage(i18n.messages.chooseSubmitCode);
}

// 提交审核通过
function submitPass() {
//add by xuhuiling 需求150 點擊提交核保 begin
//	alert(fm.BusinessType.value+":edit");
//	alert(fm.BusinessNo.value+":businessNo");
	var busiNo = fm.BusinessNo.value
	var busiType = fm.BusinessType.value
	var strNotion = fm.notion.value;
	var workStatus = "";
	var valueType = "";
	var submitFlag ="00";
	
	//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 START
	if("EL" == fm.iRiskCode.value){
		debugger;
		var isSameTime = fm.isSameTime.value;
		if(null!=isSameTime && "null"!=isSameTime){
			if(isSameTime=="false"){
				if(!confirm("※工程險保單與該保單保險期間不一致，請確認。\r\n 如[不需要]調整，請點選[確認]，\r\n 如[需要]調整，請點選[取消]。")){
					return false;
				}
			}
		}
	}
	//mantis：LIA0262，處理人員：DP0713，需求單編號：LIA0262_EL新增檢核工程險保單號碼比對保期 END
	
	//mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月START
	//mantis： FIR0420 ，處理人員： DP0713 ，需求單編號：商火-調整貨物預約及保期檢核 START
	//需調整核保管理系統中保期起日早於系統日6個月的錯誤訊息判斷，於批改時僅判斷EndorType=01(變更保期) ，才檢核保期起日早於系統日6個月，其餘批改原因不做檢核。
	if("F01" == fm.iRiskCode.value && validateStartDateForF01() && 
			//fm.endorType.value!="85" && 

			// mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險)
			(fm.endorType.value=="01" || fm.endorType.value=="") && 
			!confirm("※請確認，保期起日早於系統日6個月。")
			){
			return false;
	}
	//mantis： FIR0420 ，處理人員： DP0713 ，需求單編號：商火-調整貨物預約及保期檢核 END
	//mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月END

	// mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) -- start
	// 工水責任共用，非共保件才做檢核，比照商火檢核規則
	var classCode = fm.classCode.value;
	var coinsFlag = fm.coinsFlag.value;
	if((classCode=="E" || classCode=="M" || classCode=="C")
			&& coinsFlag!="0"
			&& validateStartDateForF01()
			&& (fm.endorType.value=="01" || fm.endorType.value=="")
			&& !confirm("※請確認，保期起日早於系統日6個月。")	){
		return false;
	}
    // mantis：EGN0104，處理人員：DP0714，調整工程險共保件保期檢核為6個月(含水險及責任險) -- end

	// mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- start
	if (classCode=="E" 
			 || (classCode=="M" && fm.iRiskCode.value!="MC")
			 || fm.iRiskCode.value=="F01"){
		 validateStartDateForEMF();
	}
	if (fm.iRiskCode.value=="MC") {
		var rationCode = fm.rationCode.value; // 險別代號
		if (rationCode!="TB" && rationCode!="OP") {
			validateStartDateForEMF();
		}
	}
	// mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- end

	var callbackForStatus ={
		 success:function(res){
	    	 var jsonObject = YAHOO.lang.JSON.parse(res.responseText);
	    	 workStatus = jsonObject.workStatus;
	    	 valueType = jsonObject.valueType;
	    	 if(strNotion=="001"){//001：審核標語為同意
		    	 //03作業狀態是拒保，04是可承保,00是不執行
				 if(workStatus!="03"&&workStatus!="04"&&workStatus!="00"){
					 if(valueType!=1){
 						submitFlag = "01";
 						window.alert(i18n.messages.notContinue);//拒限保，名單檢測，風險評級狀態未設置完成，不能進行後續處理！
 						return false;
 					}
				 }
	    	 }
				// 判断是否进行了风险评估add by wangjun20130620
				/*var retcurrency = fm.retCurrency[1].value;
				if (retcurrency == "" || retcurrency == null) {
					alert(i18n.messages.riskEstimate);
					return false;
				}*/
				// add by yangxintao 审核通过 检查危险单位 占比是否是100%
				/*if (!checkdangerShare()) {
					return false;
				}*/
				var businessType = fm.hiBusinessType.value;
				// 意健险团体单必须先录入PML值才能审核通过
				/*
				 * if(fm.hiClassCode.value == "27"){ for (var i=1;i<getElementCount("speValue",fm);i++) {
				 * //modify begin by zhaijq 20060414 2799含意外险要求输入PML值 //if
				 * (parseFloat(fm.speValue[i].value)==0.00 && (fm.hiClassCode.value == "26" ||
				 * fm.hiClassCode.value == "27") && fm.policyType.value == "02") if
				 * (parseFloat(fm.speValue[i].value)==0.00 && (fm.hiClassCode.value == "26" ||
				 * fm.hiClassCode.value == "27") && fm.policyType.value == "02" &&
				 * fm.includeAccident.value == "Y") //modify end by zhaijq 20060414 {
				 * alert("请先录入PML值才能审核通过！"); return; } } }
				 */

				if (isEmptyField(fm.HandleText)) {

					alert(i18n.messages.systemInformation + "\n\n"
							+ i18n.messages.inputApproveAdvice);
					fm.HandleText.focus();
					return false;
				}
				if (businessType == 'T' || businessType == 'P' || businessType == 'E') {
					if (checkRetenValueIsZero() == false) {
						return false;
					}
				}

				// 判断是否做了风险评估
				/**
				 * if (fm.handType.value!=22) { if(checkRetenValueIsZero() ==false) { return
				 * false; } }
				 */

				//檢查照會
				if(!checkDealStatus()){
					return;
				}
				
				// add by zhaoning20090526 begin Reason:非车险在审核通过时和规则引擎交互(没有风险评估则不和规则引擎交互)
				if (fm.hiClassCode.value == "01" || fm.hiClassCode.value == "07"
						|| fm.hiClassCode.value == "08" || fm.hiClassCode.value == "09"
						|| fm.hiClassCode.value == "10") {
					// 对历史业务在双核系统中不和规则引擎交互
					if (fm.ResultCode.value != "") {
						if (submitILog() == false) {
							return false;
						}
					}
				}

				// add by zhaoning20090526 end
				if ((fm.handType.value != 22)
						&& ((fm.notion.value != "001") && (fm.notion.value != "006") && (fm.notion.value != "007") && (fm.notion.value != "008")))

				{
					alert(i18n.messages.systemInformation + "\n\n"
							+ i18n.messages.examinePhrase);
					fm.notion.focus();
					return false;
				}
				// add by yangxt Reason: 组合产品提示危险单位拆分
				if (fm.riskUnitFlag.value == "1" && fm.eRiskCode.length <= 2
						&& fm.hiBusinessType.value == "T") {
					if (!confirm(i18n.messages.combinationProducts)) {
						return false;
					}
				}

				// add by zhaoning20090424 begin Reason:增加对非车险手工定级的校验
				if (checkGradeInfo() == false) {
					return false;
				}
				// modify by yanglibo 20100113 begin reason:TASK-2720
				if (businessType == "E" && fm.hiClassCode.value != "A"
						&& fm.hiClassCode.value != "B" && fm.hiClassCode.value != "05"
						&& fm.riskCode.value != "9997" && fm.riskCode.value != "9998"
						&& fm.riskCode.value != "9999") {
					if (false&&fm.hiManualGradeCode.value != fm.ManualGrade.options[fm.ManualGrade.selectedIndex].text
							&& (fm.hiManualGradeCode.value != null && fm.hiManualGradeCode.value != "")) {
						if (confirm(i18n.messages.examinePassOrNot)) {
							if (businessType == "E" && fm.hiClassCode.value == "05"
									&& fm.hichgPremium.value < 0) {
								var planFeeCount = fm.hiplanFeeCount.value;
								if (planFeeCount > 0) {
									alert(i18n.messages.premiumSended);
								} else {
									alert(i18n.messages.premiumNotSend);
								}
							}
							setButtondisable();
							fm.passBtn.disabled = true;
							fm.FlowStatus.value = "0";
							fm.submitTip.value = i18n.messages.examinePass;
							fm.action = "/undwrt/submitTask/commonDealSubmit.do?flag=submitPass";
							fm.submit();
						}
					} else {
						if (confirm(i18n.messages.submitExaminePass)) {
							if (businessType == "E" && fm.hiClassCode.value == "05"
									&& fm.hichgPremium.value < 0) {
								var planFeeCount = fm.hiplanFeeCount.value;
								if (planFeeCount > 0) {
									alert(i18n.messages.premiumSended);
								} else {
									alert(i18n.messages.premiumNotSend);
								}
							}
							setButtondisable();
							fm.passBtn.disabled = true;
							fm.FlowStatus.value = "0";
							fm.submitTip.value = i18n.messages.examinePass;
							fm.action = "/undwrt/submitTask/commonDealSubmit.do?flag=submitPass";
							fm.submit();
						}
					}
				} else {
					if (confirm(i18n.messages.submitExaminePass)) {
						if (businessType == "E" && fm.hiClassCode.value == "05"
								&& fm.hichgPremium.value < 0) {
							var planFeeCount = fm.hiplanFeeCount.value;
							if (planFeeCount > 0) {
								alert(i18n.messages.premiumSended);
							} else {
								alert(i18n.messages.premiumNotSend);
							}
						}
						setButtondisable();
						fm.passBtn.disabled = true;
						fm.FlowStatus.value = "0";
						fm.submitTip.value = i18n.messages.examinePass;
						fm.action = getRootPath() + "/submitTask/commonDealSubmit.do?flag=submitPass";
						fm.method = "post";
						fm.submit();
					}
				}
    	 },
	     failure:function(res){
	    	 errorMessage("調用工作狀態或人工開關異常！");//		 	 
	     } 				 			
	};
	YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/getWorkStatusAndValueType.do?busiNo='+busiNo+'&busiType='+busiType, callbackForStatus, null,false);
//add by xuhuiling 需求150 點擊提交核保 end
	
}

// 设置提交节点
function setSelectNode() {
	var len = fm.radSelectNode.length;
	var i = 0;
	fm.SelectUser.value = ""; // 将处理人员置为空串
	for (i = 0; i < len; i++) {
		if (fm.radSelectNode[i].checked == true) {
			fm.SelectNode.value = fm.NodeName[i].value;
		}
	}
}

/** 保存处理的分保意向信息* */
function saveReins(RetentionRate, RetentionValue) {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=0&RetentionRate="
			+ RetentionRate.value + "&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

/** 分保意向信息提交再保* */
function transmitReins(RetentionRate, RetentionValue) {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=9&RetentionRate="
			+ RetentionRate.value + "&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

// 增加分保人
function openreinspage(Field) {
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;
	url = "/undwrt/common/CommonReinsInfo.jsp?Index=" + intIndex
			+ "&FieldName=" + fieldName + "&ReinsCode=" + Field.value
			+ "&FinalReinsCode=" + Field.value + "&PayCode=" + Field.value;
	window.open(url, "editwindow",
			"resizable=0,scrollbars,dependent,width=650,height=300");
}

// 给增加的分保人赋值
function selectReins(index) {
	var ReinsCode = window.opener.fm.ReinsCode[index].value;
	var FinalReinsCode = window.opener.fm.FinalReinsCode[index].value;
	var PayCode = window.opener.fm.PayCode[index].value;
	if (fm.SelReinsCode.length > 0) {
		if (ReinsCode.indexOf("*") >= 0 || ReinsCode == "" || ReinsCode == null) {
			window.opener.fm.ReinsCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.ReinsName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
		}
		if (fm.reinsType[fm.SelReinsCode.selectedIndex].value == "1") {
			window.opener.fm.reinsTypeCheckBox[index].checked = true;
		}
		if (FinalReinsCode.indexOf("*") >= 0 || FinalReinsCode == ""
				|| FinalReinsCode == null) {
			window.opener.fm.FinalReinsCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.FinalReinsName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.assessLevel[index].value = fm.assessLevel[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.assessLevel2[index].value = fm.assessLevel2[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.assessLevel3[index].value = fm.assessLevel3[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.assessLevel4[index].value = fm.assessLevel4[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.assessLevel5[index].value = fm.assessLevel5[fm.SelReinsCode.selectedIndex].value;
		}
		if (PayCode.indexOf("*") >= 0 || PayCode == "" || PayCode == null) {
			window.opener.fm.PayCode[index].value = fm.SelReinsCode.options[fm.SelReinsCode.selectedIndex].value;
			window.opener.fm.PayName[index].value = fm.ShortName[fm.SelReinsCode.selectedIndex].value;
		}
	} else {
		alert(i18n.messages.noChoosed);
	}
	window.close();
}

// 取消保存
function cancelForm() {
	window.close();
}

/** ************* 撤销任务 begin ****************** */

// 设置选中标志
function setUndoTaskCheckFlag(index) {
	if (fm.Delete[index].checked == true) {
		fm.CheckFlag[index].value = "Y";
	} else if (fm.Delete[index].checked == false) {
		fm.CheckFlag[index].value = "N";
	}
}

// 全部选中
function selectUndoTaskAll() {
	var flag = fm.selectedAll.checked;
	if (flag == true) {
		for ( var i = 0; i < fm.FlowID.length; i++) {
			fm.Delete[i].checked = true;
			fm.CheckFlag[i].value = "Y";
		}
	} else if (flag == false) {
		for ( var i = 0; i < fm.FlowID.length; i++) {
			fm.Delete[i].checked = false;
			fm.CheckFlag[i].value = "N";
		}
	}
}

// 提交要撤销的任务
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
		fm.action = "/undwrt/CommonUndoTask.do?EditType=submit";
		fm.submit();
	} else {
		errorMessage(i18n.messages.chooseWithdrawRecord);
	}
}

function showTask(i) {
	// 获得指定表单域的信息
	fm.iFlowID.value = fm.FlowID[i].value;
	fm.iLogNo.value = fm.LogNo[i].value;
	fm.iBusinessNo.value = fm.BusinessNo[i].value;
	fm.iBusinessType.value = fm.BusinessType[i].value; // 0623刘军加
	fm.action = "/undwrt/CommonCheckTask.do?EditType=query";
	fm.method = "post";
	fm.submit();
}

/** ************* 撤销任务 end ****************** */

function submitReinsHePei() {
	var submitStr;
	BusinessNo = fm.BusinessNo.value;
	BusinessType = fm.BusinessType.value;
	submitStr = "/undwrt/hepei/ShowPay.jsp?CertiType=" + BusinessType
			+ "&CertiNo=" + BusinessNo;
	window
			.open(
					submitStr,
					i18n.messages.intention,
					'width=700,height=400,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

/* 处理批量核保任务 */
function checkBatchTask(i) {
	// 获得指定表单域的信息
	if (isNaN(fm.FlowID.length)) 
	{
		fm.iFlowID.value = fm.FlowID.value;
		fm.iLogNo.value = fm.LogNo.value;
		fm.iBusinessNo.value = fm.BusinessNo.value;
		fm.iBusinessType.value = fm.BusinessType.value;
		fm.iContractNo.value = fm.ContractNo.value;
		fm.iPackageID.value = fm.PackageID.value;
		fm.iModelNo.value = fm.ModelNo.value;
		fm.iNodeNo.value = fm.NodeNo.value;
		fm.iFlowStatus.value = fm.FlowStatus.value;
		fm.iDeptCode.value = fm.DeptCode.value;
		fm.iFlowInTime.value = fm.FlowInTime.value;
		fm.iNodeStatus.value = fm.NodeStatus.value;
	}
	else
	{
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
	}
	var EditType = fm.EditType.value;
	var HandType = fm.handType.value;
	fm.action = "/undwrt/batchCheckPass/commonBatchTask.do?EditType=" + EditType + "&HandType="
			+ HandType;
	fm.method = "post";
	fm.submit();
}

/**
 * @description 弹出留言保存页面
 * @param 无
 * @return 通过返回true,否则返回false
 */
function openWinSave() {
	var businessNo = fm.BusinessNo.value;
	var messageId = fm.MessageId.value;
	// 注意获得保单号
	msg = window
			.open(
					"/undwrt/messageQueryInfo.do?businessNo=" + businessNo
							+ "&operateType=new" + "&messageId=" + messageId,
					"NewWindow",
					"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=550,Height=300");
}

function openWinQuery() {
	var win;
	var messageId = fm.MessageId.value;
	var businessNo = fm.BusinessNo.value;
	var messagedo = "/undwrt/common/messageRemarkQueryInfo.do?messageId="
			+ messageId + "&businessNo=" + businessNo + "&actionType=query";
	win = window
			.open(messagedo, "",
					"status=no,resizable=no,scrollbars=yes,width=600,Height=600,left=200,top=70");
}

function openOtherFees() {
	var businessNo = fm.BusinessNo.value;
	var win;
	var otherfeesdo = "/undwrt/otherFeesQueryInfo.do?businessNo=" + businessNo
			+ "&actionType=query&flag=T";
	win = window
			.open(otherfeesdo, i18n.messages.otherFeeTypeIn,
					"status=no,resizable=no,scrollbars=yes,width=600,Height=600,left=200,top=70");
}
function ywsbm() {
	var businessNo = fm.BusinessNo.value;
	var win;
	var otherfeesdo = "/undwrt/otherFeesQueryInfo.do?businessNo=" + businessNo
			+ "&actionType=query&flag=E";
	win = window
			.open(otherfeesdo, i18n.messages.bussinessCode,
					"status=no,resizable=no,scrollbars=yes,width=600,Height=600,left=200,top=70");
}

/** *********************** 指定人员处理 ************************** */
function selectPeople() {
	var NodeNo;
	var strURL;
	var intCount = fm.radSelectNode.length;
	intCount = intCount - 1;
	var i = 0;
	if (fm.SelectNode.value == "") {
		alert(i18n.messages.chooseSubmitCode);
		return;
	}
	for (i = 0; i < intCount; i++) {
		if (fm.radSelectNode[i].checked == true) {
			NodeNo = fm.NodeNo[i].value;
			if (NodeNo == 1) {
				alert(i18n.messages.cannotChoosePerson);
				return;
			}
			/* 设定隐含域数据，准备提交 */
			fm.selectNodeNo.value = fm.NodeNo[i].value;
			fm.selectNodeName.value = fm.NodeName[i].value;

			fm.target = "fraSubmit";
			fm.action = "/undwrt/submitUser.do";
			fm.method = "post";
			fm.submit();
			strURL = "/undwrt/hebao/SubmitUserList.jsp";
			window
					.open(
							strURL,
							i18n.messages.appointedPerson,
							'width=420,height=200,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
			return;
		}
	}
}

/** 选择提交人员* */
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
			window.opener.fm.SelectNode.value = oldvalue + "-"
					+ fm.people.options[i].value;
			window.opener.fm.SelectUser.value = fm.people.options[i].value;
			window.close();
			return;
		}
	}
	alert(i18n.messages.choosePerson);
}
/** ****************** 指定人员处理 end ********************** */

/** ****************** 东安个性函数 begin****************** */
// 历史投保信息列表页面
function showBusinessTotalInfo(BusinessNo) {
	var vURL = '/undwrt/common/CommonHistoryProposalList.jsp?BusinessNo='
			+ BusinessNo;
	window
			.open(
					vURL,
					i18n.messages.historyInsureInfor,
					'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

// 历史赔付信息列表页面
function showHistoryLossInfo(BusinessNo) {
	var vURL = '/undwrt/common/CommonHistoryLossList.jsp?BusinessNo='
			+ BusinessNo;
	window
			.open(
					vURL,
					i18n.messages.historyCompensateInfor,
					'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//同险累积查看信息
function similarRiskInfo(businessNo,businessType) {
	var reinsIP = fm.reinsIP.value;
	var vURL = reinsIP+"/reins/common/viewSameRiskAcc.do?businessNo="+ businessNo+"&businessType="+businessType;
	window
			.open(
					vURL,
					i18n.messages.similarRiskInfo,
					'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/** 分保试算* */
function simulateReins(RetentionRate, RetentionValue) {
	var submitStr;
	submitStr = "/undwrt/common/CommonReinsSimulate.jsp?RetentionRate="
			+ RetentionRate.value + "&RetentionValue=" + RetentionValue.value;
	fm.action = submitStr;
	fm.submit();
}

// 获取自留比例
function getRetentionRates(CertiNo, field) {
	var elementRates = fm.RetentionRates;
	for ( var i = elementRates.options.length - 1; i >= 0; i--) {
		elementRates.remove(i);
	}

	if (field.value == "")
		return;
	var strURL = "/undwrt/common/CommonReinsGetRetentionRates.jsp?CertiNo="
			+ CertiNo + "&RateCode=" + field.value;
	var vXmlText = getResponseXmlText(strURL);
	// 截掉头尾字符[]
	if (vXmlText.length >= 2)
		vXmlText = vXmlText.substring(1, vXmlText.length - 1);
	else
		vXmlText = "";
	var Rates = vXmlText.split("^");
	for ( var i = 0; i < Rates.length; i++) {
		var option = document.createElement("option");
		// modify by luyang 2005-3-4 13:13
		option.text = rightTrim(Rates[i]);
		option.value = rightTrim(Rates[i]);
		option.text = leftTrim(Rates[i]);
		option.value = leftTrim(Rates[i]);
		elementRates.add(option);
	}
}

// 离开域时校验是否超过合同最高自留额
function checkTopRetenValue(field) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";
	var value = parseFloat(strValue);

	if (value > 8500000) {
		alert(i18n.messages.retention);
		field.focus();
		field.select();
		return false;
	}
	return true;
}

// 离开域时的数字校验Decimal
function checkDecimal(field, p, s, MinValue, MaxValue) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";

	var desc = field.description;
	// 如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;

	if (!isNumeric(strValue)) {
		errorMessage(i18n.messages.inputValidDigit);
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
		errorMessage(i18n.messages.inputValid + desc + "\n"
				+ i18n.messages.integerLength + (p - s) + ","
				+ i18n.messages.decimalLength + s);
		field.focus();
		field.select();
		return false;
	}

	var value = parseFloat(strValue);
	if (MaxValue != null && MinValue != null && trim(MaxValue) != ""
			&& trim(MinValue) != "") {
		MinValue = parseFloat(MinValue);
		MaxValue = parseFloat(MaxValue);
		if (isNaN(value) || value > MaxValue || value < MinValue) {
			errorMessage(i18n.messages.inputValid + desc + "\n"
					+ i18n.messages.digitMin + MinValue + ","
					+ i18n.messages.max + MaxValue);
			field.focus();
			field.select();
			return false;
		}
	}
	return true;
}

// 使用xmlhttp访问页面，并获取数据
function getResponseXmlText(strURL) {
	var objXmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	objXmlHttp.Open("POST", strURL, false);
	objXmlHttp.setRequestHeader("Content-type", "text/xml");
	objXmlHttp.Send("");
	if (objXmlHttp.status == 200) {
		// modify by luyang 2005-3-4 13:13
		return objXmlHttp.responseText;
		// return objXmlHttp.responseXML.text;
	} else if (objXmlHttp.status == 404) {
		alert(i18n.messages.cannotFindPage + strURL);
		return "";
	} else {
		alert(i18n.messages.visit + strURL + i18n.messages.errorNo
				+ objXmlHttp.status);
		return "";
	}
}
/** ****************** 东安个性函数 end****************** */

/** 保存处理的分保意向信息* */
function saveReins() {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=0";
	fm.action = submitStr;
	fm.submit();
}

/** 分保意向信息提交再保* */
function transmitReins() {
	var submitStr = "/undwrt/common/CommonReinsSave.jsp?Reins=9";
	fm.action = submitStr;
	fm.submit();
}

/**
 * ***************** 校验风险评估拆分信息 begin add by luyang 2005-5-6 13:30
 * ****************
 */
// 原始标的相关值
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

// 检查投保单的所有风险评估主信息表单域
function checkAllDangerUnit() {
	var dangerUnitCount = fm.dangerNo.length;
	var i = 0;
	var amount = 0;
	var premium = 0;

	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage(i18n.messages.operateAgain);
		return false;
	}
	for (i = 1; i < dangerUnitCount; i++) {
		if (isEmptyField(fm.dangerNo[i])) {
			errorMessage(i18n.messages.inputSequence);
			fm.dangerNo[i].focus();
			return false;
		}
		if (isEmptyField(fm.dangerDesc[i])) {
			errorMessage(i18n.messages.inputRiskEvalution);
			fm.dangerDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevel[i])) {
			errorMessage(i18n.messages.inputRiskLevel);
			fm.riskLevel[i].focus();
			return false;
		}
		/*
		 * if(isEmptyField(fm.riskLevelDesc[i])) {
		 * errorMessage("请输入风险评估的风险描述！"); fm.riskLevelDesc[i].focus(); return
		 * false; }
		 */
		// alert(fm.kindFlag[i].value);

		if (isEmptyField(fm.currency[i])) {
			errorMessage(i18n.messages.inputRiskCurrency);
			fm.currency[i].focus();
			return false;
		}
		if (isEmptyField(fm.amount[i])) {
			errorMessage(i18n.messages.inputRiskCoverage);
			fm.amount[i].focus();
			return false;
		}
		if (isEmptyField(fm.premium[i])) {
			errorMessage(i18n.messages.inputRiskPremium);
			fm.premium[i].focus();
			return false;
		}
		if (isEmptyField(fm.dangerShare[i])) {
			errorMessage(i18n.messages.inputRiskRatio);
			fm.dangerShare[i].focus();
			return false;
		}
		if (fm.isSavaDangerUnit[i].value == "N") {
			errorMessage(i18n.messages.di + i + i18n.messages.clickCheckButtoon);
			return false;
		}
		amount += parseFloat(fm.amount[i].value);
		premium += parseFloat(fm.premium[i].value);

	}
	var flag = true;
	var msg = "";
	// 拆分后保额与标的总保额一致
	if (amount != sumAmount) {
		msg = i18n.messages.coverageConsistent + "\n"
				+ i18n.messages.presentBalance + (sumAmount - amount);
		flag = false;
	}
	if (premium != sumPremium) {
		if (msg != "")
			msg = "\n\n"
		msg += i18n.messages.premiumConsistent + "\n"
				+ i18n.messages.presentBalance + (sumPremium - premium);
		flag = false;
	}

	if (flag == false) {
		errorMessage(msg);
		return false;
	}

	return true;
}

// 检查投保单的某一行风险评估主信息表单域
function checkDangerUnit(i) {
	var dangerUnitCount = fm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage("风险评估无记录，请重新操作！");
		return false;
	}

	if (isEmptyField(fm.dangerNo[i])) {
		errorMessage(i18n.messages.inputSequence);
		fm.dangerNo[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerDesc[i])) {
		errorMessage(i18n.messages.inputRiskEvalution);
		fm.dangerDesc[i].focus();
		return false;
	}
	if (isEmptyField(fm.riskLevel[i])) {
		errorMessage(i18n.messages.inputRiskLevel);
		fm.riskLevel[i].focus();
		return false;
	}

	if (isEmptyField(fm.riskLevelDesc[i])) {
		errorMessage(i18n.messages.inputRiskEvalution);
		fm.riskLevelDesc[i].focus();
		return false;
	}
	// alert(fm.kindFlag[i].value);

	if (isEmptyField(fm.currency[i])) {
		errorMessage(i18n.messages.inputRiskCurrency);
		fm.currency[i].focus();
		return false;
	}
	if (isEmptyField(fm.amount[i])) {
		errorMessage(i18n.messages.inputRiskCoverage);
		fm.amount[i].focus();
		return false;
	}
	if (isEmptyField(fm.premium[i])) {
		errorMessage(i18n.messages.inputRiskPremium);
		fm.premium[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerShare[i])) {
		errorMessage(i18n.messages.inputRiskRatio);
		fm.chgAmount[i].focus();
		return false;
	}

	return true;
}

// 初始化投保单，保单的风险评估信息
function initDangerUnit() {
	// 核赔时不做处理
	if (fm.handType.value == 22) {
		return;
	}
	dangerInfo.style.display = "none";
	//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理START
	var riskCode = fm.riskCode.value;
	var validateStartDateForC = "AB、AR、AT、BN、CN、EL、EM、ER、MN、PB、PR、SC、TC、TD、TL";
	if(validateStartDateForC.indexOf(riskCode) > -1){
		var overThreeMonthMsg = fm.overThreeMonthMsg.value;// 超過三個月警語
		if(overThreeMonthMsg !=''){
			alert(overThreeMonthMsg);
		}
	}
	//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理END
	
}

// 在核保主页面上,初始化批单的风险评估信息
function initEndorseDangerUnit() {
	if (fm.riskUnitFlag.value == "0") // 某些险种不需要拆分风险评估时不初始化风险评估信息
	{
		return;
	}
	dangerInfo.style.display = "none";// 页面打开时拆分风险评估部分折叠
	var dangerUnitCount = fm.dangerNo.length;
	if (fm.itemKindNo != null) {
		var itemKindCount = fm.itemKindNo.length;
		var i = 0;
		var j = 0;
		// 风险评估拆分后相关值
		var amount = 0;
		var premium = 0;
		// 获取标的险别相关信息
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
				fm.dangerDesc[i].value = i18n.messages.riskDescription;
				fm.riskLevel[i].value = '000';
				fm.riskLevelDesc[i].value = i18n.messages.riskLevelDescription1;
				fm.currency[i].value = currency;
				fm.retentionValue[i].value = '0.00';
				fm.amount[i].value = sumAmount;
				fm.premium[i].value = sumPremium;
			}
		}

	}
}

/**
 * 根据隐藏域的值来设置计算保费复选框的选中情况
 * 
 */

function getCalFlag() {
	var elementCount = getElementCount("flag");// 获取到元素的个数

	if (elementCount > 1) // 个数大于1时的处理
	{

		for ( var index = 1; index < elementCount; index++) {

			if (fm.hiddenFlag[index].value.charAt(0) == "1") // 取第一位字段
			{
				fm.flag[index].checked = true;
			} else if (fm.hiddenFlag[index].value.charAt(0) == "0") {
				fm.flag[index].checked = false;
			}

			if (fm.hiddenFlag2[index].value.charAt(1) == "1") // 取第二位字段
			{
				fm.flag2[index].checked = true;
			} else if (fm.hiddenFlag2[index].value.charAt(1) == "0") {
				fm.flag2[index].checked = false;
			}
		}
	}

}

// 显示投保单，保单的风险评估子信息
function showDangerItem(field, pageCode, flag) {
	var classCode = fm.hiClassCode.value;
	if (typeof (fm.EditType) != 'undefined') {
		var editType = fm.EditType.value; // 判断是否是query状态
	} else {
		var editType = fm.editType.value; // 判断是否是query状态
	}
	var count = fm.dangerNo.length; // 得到风险评估的个数
	var currentRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	var riskCode = fm.riskCode.value;
	var riskUnitFlag = fm.riskUnitFlag.value; // 该险种是否允许拆分风险评估
	var includeAccident = fm.includeAccident.value;
	var dangerNo = 0; // fm.dangerNo[index].value;
	var index = 0; // 因为风险评估号和table中行号不是对应的，故设置index以便定位
	var isNewDangerInfo = 0;

	if (flag == 'NewDangerNo') {
		dangerNo = parseInt(fm.dangerNo[count - 2].value) + 1; // 点击新增的一个风险评估,风险评估号为最后一个序号加1
		fm.dangerNo[count - 1].value = dangerNo;
		index = count - 1;
		isNewDangerInfo = 1;
	} else // 查看存在的风险评估
	{
		dangerNo = fm.dangerNo[RowNo].value;
		index = RowNo;
		isNewDangerInfo = 0;
	}
	// modify end 2007-04-24 by lihua
	submitStr = "/undwrt/taskCheck/commonCheckTask.do?businessNo=" + businessNo
			+ "&businessType=" + businessType + "&riskCode=" + riskCode
			+ "&classCode=" + classCode + "&dangerNo=" + dangerNo
			+ "&showDangerItemFlag=1" + "&openerIndex=" + index
			+ "&NewDangerInfo=" + isNewDangerInfo + "&editType=" + editType
			+ "&riskUnitFlag=" + riskUnitFlag + "&includeAccident="
			+ includeAccident;
	window
			.open(
					submitStr,
					i18n.messages.checkRiskInfor,
					'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
}

// 核心调双核时显示风险评估子信息(enterFlag=1是起到一个标志位得作用，表示此处只是查看风险评估子信息)
// 核心调双核时显示风险评估子信息
function showPrpDangerItem(field, pageCode, flag) {
	var count = fm.dangerNo.length; // 得到风险评估的个数
	var currentRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	dangerNo = fm.dangerNo[RowNo].value;
	submitStr = "/undwrt/PrpQureyInfo.do?businessNo=" + businessNo
			+ "&businessType=" + businessType + "&dangerNo=" + dangerNo
			+ "&showDangerItemFlag=1&enterFlag=1";
	window
			.open(
					submitStr,
					i18n.messages.checkRiskInfor,
					'width=950,height=600,top=0,left=20,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
}

// 双核中的临分意向
function submitReins() {
	var count = fm.dangerNo.length;
//	if (count > 2) {
//		alert(i18n.messages.dontSplit);
//		return false;
//	}
	var retcurrency = fm.retCurrency[1].value;
	var BusinessNo = fm.BusinessNo.value;
	var BusinessType = fm.BusinessType.value;
	var riskCode = fm.iRiskCode.value;
	var elements =  document.getElementsByName("whetherFacing");
	var dangerNos = document.getElementsByName("dangerNo");
	var dangerNo="";
	var element="";
	var facingFlag = false;
	if(elements.length>0)
	{
		for(var i=1;i<elements.length-1;i++)
		{
			if(elements[i].value==1)
				{
					facingFlag=true;
				}
			element  = element+elements[i].value+",";
			dangerNo = dangerNo+dangerNos[i].value+",";
		}
		if(elements[elements.length-1].value==1)
		{
			facingFlag=true;
		}
		if(!facingFlag)
		{
			alert(i18n.messages.leastOnedangerNo);
			return false;
		}
		element  = element+elements[elements.length-1].value;
		dangerNo = dangerNo+dangerNos[elements.length-1].value;
	}
	var policyNo = "";
	var proposalNo = "";
	if (retcurrency == "" || retcurrency == null) {
		alert(i18n.messages.riskEstimate);
		return false;
	}
	// 设置ReinsFlag便于CommonCheckTaskFacade判断
	if (BusinessType != "E") {
		submitStr = "/undwrt/taskCheck/commonCheckTask.do?CertiType="
				+ BusinessType + "&CertiNo=" + BusinessNo + "&ReinsFlag=" + 1+"&whetherFacing=" + element+"&iRiskCode=" + riskCode+"&dangerNos=" + dangerNo;
	} else {
		// 批单
		policyNo = fm.hiPolicyNo.value;
		proposalNo = fm.hiProposalNo.value;
		submitStr = "/undwrt/taskCheck/commonCheckTask.do?CertiType="
				+ BusinessType + "&CertiNo=" + BusinessNo + "&policyNo="
				+ policyNo + "&proposalNo=" + proposalNo + "&ReinsFlag=" + 1+"&whetherFacing=" + element+"&iRiskCode=" + riskCode+"&dangerNos=" + dangerNo;
	}
	window
			.open(
					submitStr,
					i18n.messages.intention2,
					'width=1020,height=700,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
}

// 批单风险评估的子信息提交
function saveEndorseDangerItemTask()
{
	var count = DangerItemForm.itemKindNo.length - 1;
	var classCode = DangerItemForm.classCode.value;
	var amount = 0.0;
	var chgAmount = 0.0;
	var premium = 0.0;
	var chgPremium = 0.0;
	var index = DangerItemForm.openerIndex.value;
	var tolPremium = 0;
	var dangerShare = 0;

	var riskCode = DangerItemForm.riskCode[1].value;
	//批单同险号是否相同的校验20140718 by wangjun
	if(DangerItemForm.riskCode[1].value=="F01")
	{
		var map = getMap();
		for (i = 1; i < DangerItemForm.itemKindNo.length; i++) 
		{
			if (fm.checkDanger[i].checked == true) 
			{
				map.put(i,DangerItemForm.sameRiskNo[i].value);
			}
		}
		if(map.size()>1)
		{		
			var riskNo="";
			var flag=true;
			for (i = 1; i < DangerItemForm.itemKindNo.length; i++) 
			{
				if(map.get(i)!=undefined)
				{
					if(flag)
					{
						riskNo = map.get(i);
						flag = false;
					}
					if(riskNo!=map.get(i))
					{
						alert("請選擇同險號相同的條款進行危險單位拆分！");
						return false;
					}
				}
			}
		}
	}

	// 请输入标的序号
	for (i = 1; i <= count; i++) {
		if (DangerItemForm.itemKindNo[i].value == "") {
			alert(i18n.messages.inputObjectCode);
			DangerItemForm.itemKindNo[i].focus();
			return;
		}
	}
	
	//风险评级
	if (DangerItemForm.riskLevel[1].value == "") {
		window.alert(i18n.messages.riskLevelCannotNull);
		DangerItemForm.riskLevel[1].focus();
		return;
	}

	// 保额值与保额变化值不能同时为空、为零
	if (((DangerItemForm.amount[1].value == "") || (DangerItemForm.amount[1].value == 0))
			&& ((DangerItemForm.chgAmount[1].value == "") || (DangerItemForm.chgAmount[1].value == 0))) {
		window.alert(i18n.messages.coverageCannotNull);
		DangerItemForm.amount[1].focus();
		return;
	}

	// 危险等级描述不能为空
	if (DangerItemForm.riskLevelDesc[1].value == "") {
		window.alert(i18n.messages.dangerDescriptionNotNull);
		DangerItemForm.riskLevelDesc[1].focus();
		return;
	}

	// modify begin by zhaijq 20060414 2799含意外险要求输入PML值
	if ((classCode == "27" || classCode == "26")
			&& window.opener.fm.policyType.value == "02"
			&& DangerItemForm.includeAccident.value == "Y") {
		// modify end by zhaijq 20060414
		if (DangerItemForm.speCurrency[1].value == "") {
			window.alert(i18n.messages.currencyNotNull);
			DangerItemForm.speCurrency[1].focus();
			return;
		}

		if (DangerItemForm.speValue[1].value == ""
				|| parseFloat(DangerItemForm.speValue[1].value) == 0.00) {
			window.alert(i18n.messages.valueNotNull);
			DangerItemForm.speValue[1].focus();
			return;
		}
	}

	// 自留额币种不能为空
	if (DangerItemForm.retCurrency[1].value == "") {
		window.alert(i18n.messages.currencyNotNull2);
		DangerItemForm.retCurrency[1].focus();
		return;
	}

	// 自留额值不能为空
	if (DangerItemForm.retentionValue[1].value == "") {
		window.alert(i18n.messages.valueNotNull2);
		DangerItemForm.retentionValue[1].focus();
		return;
	}

	// 币种不能为空
	if (DangerItemForm.currency[1].value == "") {
		window.alert(i18n.messages.currencyNotNull3);
		return;
	}
	if (DangerItemForm.dangerItemFlag[1].checked == true) {
		DangerItemForm.hiDangerItemFlag[1].value = "10";
	} else {
		DangerItemForm.hiDangerItemFlag[1].value = "00";
	}
	
	calPDangerAmountPremium();

	amount = DangerItemForm.amount[1].value;
	premium = DangerItemForm.premium[1].value;
	chgamount = DangerItemForm.chgAmount[1].value;
	chgPremium = DangerItemForm.chgPremium[1].value;
	dangerShare = DangerItemForm.dangerShare[1].value;
	var allAmount = parseFloat(amount) + parseFloat(chgamount);
	var allPremium = parseFloat(premium) + parseFloat(chgPremium);
	window.alert(i18n.messages.sumCoverage + allAmount + "  "
			+ i18n.messages.sumPremium + allPremium + " "
			+ i18n.messages.occupyRatio + dangerShare);
	window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
	window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
	window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;

	var obj = document.getElementById('mySelect');
	var num = obj.selectedIndex;
	window.opener.fm.dangerItemKindName[index].value = obj.options[num].text;

	if (DangerItemForm.dangerItemFlag[1].checked == true) {// 标识位
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
	window.opener.fm.eRiskCode[index].value = DangerItemForm.riskCode[1].value;
	window.opener.fm.riskName[index].value = DangerItemForm.riskName[1].value;
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
	DangerItemForm.action = "/undwrt/handleTask/commonDealTask.do";
	DangerItemForm.submit();
}

// modify begin 2006-12-10 by lihua 保单拆分风险评估

// 投保单,保单的风险评估子信息提交
function saveDangerItemTask() {
	var classCode = DangerItemForm.classCode.value;
	var amount = 0.0;
	var premium = 0.0;
	var index = DangerItemForm.openerIndex.value;
	var dangerShare = 0;
	//同险号是否相同的校验20140718 by wangJun
	if(DangerItemForm.riskCode[1].value=="F01")
	{
		var map = getMap();
		for (i = 1; i < DangerItemForm.itemKindNo.length; i++) 
		{
			if (fm.checkDanger[i].checked == true) 
			{
				map.put(i,DangerItemForm.sameRiskNo[i].value);
			}
		}
		if(map.size()>1)
		{		
			var riskNo="";
			var flag=true;
			for (i = 1; i < DangerItemForm.itemKindNo.length; i++) 
			{
				if(map.get(i)!=undefined)
				{
					if(flag)
					{
						riskNo = map.get(i);
						flag = false;
					}
					if(riskNo!=map.get(i))
					{
						alert("請選擇同險號相同的條款進行危險單位拆分！");
						return false;
					}
				}
			}
		}
	}
	// 计算风险评估保额保费
	calDangerAmountPremium();
	amount = DangerItemForm.amount[1].value;
	premium = DangerItemForm.premium[1].value.toString().replace(/,/g, "");
	dangerShare = DangerItemForm.dangerShare[1].value;
	if ((classCode == "27" || classCode == "26")
			&& window.opener.fm.policyType.value == "02"
			&& DangerItemForm.includeAccident.value == "Y") {
		if (DangerItemForm.speCurrency[1].value == "") {
			alert(i18n.messages.currencyNotNull);
			DangerItemForm.speCurrency[1].focus();
			return;
		}
		if (DangerItemForm.speValue[1].value == ""
				|| parseFloat(DangerItemForm.speValue[1].value) == 0.00) {
			alert(i18n.messages.valueNotNull);
			DangerItemForm.speValue[1].focus();
			return;
		}
	}
	if (DangerItemForm.retCurrency[1].value == "") {
		window.alert(i18n.messages.currencyNotNull2);
		DangerItemForm.retCurrency[1].focus();
		return;
	}
	if (DangerItemForm.retentionValue[1].value == "") {
		window.alert(i18n.messages.valueNotNull2);
		DangerItemForm.retentionValue[1].focus();
		return;
	}

	// add by yanglibo 2008-08-07 begin
	if ((DangerItemForm.amount[1].value == "")
			|| (DangerItemForm.amount[1].value == 0)) {
		window.alert(i18n.messages.coverageValueNotNull);
		DangerItemForm.amount[1].focus();
		return;
	}

	// add by yanglibo 2008-08-07 end
	window.alert(i18n.messages.sumCoverage + amount + "  "
			+ i18n.messages.sumPremium + premium + "  "
			+ i18n.messages.occupyRatio + dangerShare);
	window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
	window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
	window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;
	var obj = document.getElementById('mySelect');
	var num = obj.selectedIndex;
	window.opener.fm.dangerItemKindName[index].value = obj.options[num].text;
	window.opener.fm.currency[index].value = DangerItemForm.currency[1].value;
	window.opener.fm.dangerShare[index].value = DangerItemForm.dangerShare[1].value;
	window.opener.fm.amount[index].value = amount;
	window.opener.fm.premium[index].value = premium;

	if (DangerItemForm.dangerItemFlag[1].checked == true) // 标识位
	{
		window.opener.fm.dangerFlag[index].checked = true;
		DangerItemForm.hiDangerItemFlag[1].value = "10";
	} else {
		window.opener.fm.dangerFlag[index].checked = false;
		DangerItemForm.hiDangerItemFlag[1].value = "00";
	}
	// 需求变更，不需要往主表带数据了
	window.opener.fm.riskLevel[index].value = DangerItemForm.riskLevel[1].value;
	window.opener.fm.eRiskCode[index].value = DangerItemForm.riskCode[1].value;
	window.opener.fm.riskName[index].value = DangerItemForm.riskName[1].value;
	window.opener.fm.riskLevelDesc[index].value = DangerItemForm.riskLevelDesc[1].value;
	window.opener.fm.retCurrency[index].value = DangerItemForm.retCurrency[1].value;
	window.opener.fm.retentionValue[index].value = DangerItemForm.retentionValue[1].value
			.toString().replace(/,/g, "");
	if (classCode == "27") {
		if (DangerItemForm.speCurrency[1].value == "") {
			DangerItemForm.speCurrency[1].value = DangerItemForm.currency[1].value;
		}
		window.opener.fm.speCurrency[index].value = DangerItemForm.speCurrency[1].value;
		window.opener.fm.speValue[index].value = DangerItemForm.speValue[1].value;
	}

	DangerItemForm.DealType.value = "saveDangerItem";
	DangerItemForm.method = "post";
	DangerItemForm.action = "/undwrt/handleTask/commonDealTask.do";
	DangerItemForm.submit();
}

// 根据计算金额复选框的选中标志来设置隐藏域的值
function setCalFlag() {
	var flagCount = 0;

	for ( var i = 0; i < DangerItemForm.elements.length; i++) // 查找DangerItemForm里的元素
	{
		if (DangerItemForm.elements[i].name == "CalculateFlag") {
			flagCount++;
		}
	}

	if (flagCount > 1) // 个数大于1的处理
	{
		for ( var index = 1; index < flagCount; index++) {
			if (DangerItemForm.CalculateFlag[index].checked == true) {
				DangerItemForm.hiCalculateFlag[index].value = "Y";
			} else if (DangerItemForm.CalculateFlag[index].checked == false) {
				DangerItemForm.hiCalculateFlag[index].value = "N";
			}
		}
	}
}

// 点击投保单的划分风险评估信息的删除按钮时同时删除在数据库中的主信息和子信息
function deleteTdangerInfo(field, pageCode) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var index = recentDeletedRowNo - 1;
	var businessType = fm.hiBusinessType.value;
	var businessNo = fm.hiBusinessNo.value;
	var dangerNo = fm.dangerNo[index].value;
	if (dangerNo == "1") {
		window.alert(i18n.messages.di + dangerNo
				+ i18n.messages.riskEstimateNotDelete);
		return;
	}
	var dangerNoCount = fm.dangerNo.length; // 风险评估信息条数
	// window.alert("删除信息时的风险评估号为" + dangerNo + "序号为" + index +"总数为" +
	// dangerNoCount);
	if (dangerNoCount == 2) {
		window.alert(i18n.messages.oneRiskEstimateMust);
		return false;
	}
	if (confirm(i18n.messages.deleteRiskEstimate)) {
		if (dangerNo != "") {
			submitStr = "/undwrt/handleTask/commonDealTask.do?businessType="
					+ businessType + "&businessNo=" + businessNo + "&dangerNo="
					+ dangerNo + "&DealType=delete";
			var newWindow = window
					.open(
							submitStr,
							i18n.messages.deleteRiskEstimateInfor,
							'width=800,height=600,top=300,left=300,toolbar=0,location=0,directories=0,menubar=0,scrollbars=0,resizable=0,status=0');
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
		window.alert(i18n.messages.oneRiskEstimateMust2);
		return false;
	}
	if (confirm(i18n.messages.deleteOriginalInfor)) {
		deleteRow_new(field, pageCode);
	}
}

// 点击批单的划分风险评估信息的删除按钮时，同时删除在数据库中的主信息和子信息
function deletePdangerInfo(field, pageCode) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var index = recentDeletedRowNo - 1;
	var businessType = fm.hiBusinessType.value;
	var businessNo = fm.hiBusinessNo.value;
	var dangerNo = fm.dangerNo[index].value;
	var dangerNoCount = fm.dangerNo.length; // 风险评估信息条数
	if (dangerNo == "1") {
		window.alert(i18n.messages.di + dangerNo
				+ i18n.messages.riskEstimateNotDelete);
		return;
	}
	// window.alert("删除信息时的风险评估号为" + dangerNo + "序号为" + index +"总数为" +
	// dangerNoCount);
	if (dangerNoCount == 2) {
		window.alert(i18n.messages.oneRiskEstimateMust);
		return false;
	}

	if (confirm(i18n.messages.deleteRiskEstimate)) {
		var recentDeletedRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
		var index = recentDeletedRowNo - 1;
		var businessType = fm.hiBusinessType.value;
		var businessNo = fm.hiBusinessNo.value;
		var dangerNo = fm.dangerNo[index].value;

		if (dangerNo != "") { // 删除数据库里的信息
			submitStr = "/undwrt/handleTask/commonDealTask.do?businessType="
					+ businessType + "&businessNo=" + businessNo + "&dangerNo="
					+ dangerNo + "&DealType=delete";
			var newWindow = window
					.open(
							submitStr,
							i18n.messages.deleteRiderInfor,
							'width=800,height=600,top=10,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');
			newWindow.focus();
			newWindow.close();
		}
		deleteRow_new(field, pageCode);
	}
	window.close();
}

function checkDangerNoIsEmpty(field) {
	var recentDeletedRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var index = recentDeletedRowNo - 1;

	if (fm.dangerNo[index].value != "") {
		window.alert(i18n.messages.sequenceNotUpdateAgain);
		fm.riskLevel[index].focus();
	} else {
		fm.dangerNo[index].focus();
	}
}

// //查看批单的某条风险评估子信息
function showEndorseDangerItem(field, pageCode, flag) {
	var editType = fm.EditType.value; // 判断是否是query状态
	var count = fm.dangerNo.length; // 得到风险评估的个数
	var currentRowNo = parseInt(getElementOrder(field)); // 得到当前行的行号
	var RowNo = currentRowNo - 1;
	var businessNo = fm.hiBusinessNo.value;
	var businessType = fm.hiBusinessType.value;
	var policyNo = fm.hiPolicyNo.value; // 保单号
	var riskCode = fm.riskCode.value;
	var classCode = fm.hiClassCode.value;
	var includeAccident = fm.includeAccident.value;
	var hiDangerNo = 0; // fm.dangerNo[index].value;
	var index = 0; // 因为风险评估号和table中行号不是对应的，故设置index以便定位
	var isNewDangerInfo = 0;
	if (flag == 'NewDangerNo') {
		hiDangerNo = parseInt(fm.dangerNo[count - 2].value) + 1; // 点击新增的一个风险评估,风险评估号为最后一个序号加1
		fm.dangerNo[count - 1].value = hiDangerNo;
		index = count - 1;
		isNewDangerInfo = 1;
	} else // 查看存在的风险评估
	{
		hiDangerNo = fm.dangerNo[RowNo].value;
		index = RowNo;
		isNewDangerInfo = 0;
	}
	// modify begin 2007-04-24 by lihua 按险种拆分风险评估
	var eRiskCode = fm.eRiskCode[hiDangerNo].value;
	if (eRiskCode == null || eRiskCode == "") {
		eRiskCode = riskCode;
	}
	// modify end 2007-04-24 by lihua
	submitStr = "/undwrt/taskCheck/commonCheckTask.do?businessNo=" + businessNo
			+ "&policyNo=" + policyNo + "&businessType=" + businessType
			+ "&riskCode=" + eRiskCode + "&classCode=" + classCode
			+ "&hiDangerNo=" + hiDangerNo + "&showDangerItemFlag=2"
			+ "&openerIndex=" + index + "&NewDangerInfo=" + isNewDangerInfo
			+ "&editType=" + editType + "&includeAccident=" + includeAccident;
	window
			.open(
					submitStr,
					i18n.messages.checkRiderRiskInfor,
					'width=950,height=600,top=50,left=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=0,status=0');

}

// 检验批单的风险评估某条主信息的表单域是否空
function checkEndorseDangerUnit(i) {
	var dangerUnitCount = fm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		errorMessage(i18n.messages.operateAgain);
		return false;
	}

	if (isEmptyField(fm.dangerNo[i])) {
		errorMessage(i18n.messages.inputSequence);
		fm.dangerNo[i].focus();
		return false;
	}
	if (isEmptyField(fm.dangerDesc[i])) {
		errorMessage(i18n.messages.inputRiskEvalution);
		fm.dangerDesc[i].focus();
		return false;
	}
	if (isEmptyField(fm.riskLevel[i])) {
		errorMessage(i18n.messages.inputRiskLevel);
		fm.riskLevel[i].focus();
		return false;
	}
	/*
	 * if(isEmptyField(fm.riskLevelDesc[i])) { errorMessage("请输入风险描述！");
	 * fm.riskLevelDesc[i].focus(); return false; }
	 */
	// alert(fm.kindFlag[i].value);
	if (isEmptyField(fm.currency[i])) {
		errorMessage(i18n.messages.inputRiskCurrency);
		fm.currency[i].focus();
		return false;
	}
	if (isEmptyField(fm.amount[i])) {
		errorMessage(i18n.messages.inputRiskCoverage);
		fm.amount[i].focus();
		return false;
	}
	if (isEmptyField(fm.premium[i])) {
		errorMessage(i18n.messages.inputRiskPremium);
		fm.premium[i].focus();
		return false;
	}
	if (isEmptyField(fm.chgAmount[i])) {
		errorMessage(i18n.messages.inputChangeCoverage);
		fm.chgAmount[i].focus();
		return false;
	}
	if (isEmptyField(fm.chgPremium[i])) {
		errorMessage(i18n.messages.inputChangePermium);
		fm.chgPremium[i].focus();
		return false;
	}
	return true;
}

// 保存批单的所有风险评估主信息
function saveEndorseTask() {
	//审批意见为空，提示请填写审批意见
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}
	setButtondisable();
	fm.DealType.value = "save";
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.submit();

}

// 检查批单所有的风险评估主信息的表单域
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
		errorMessage(i18n.messages.operateAgain);
		return false;
	}
	for (i = 1; i < dangerUnitCount; i++) {
		if (isEmptyField(fm.dangerNo[i])) {
			errorMessage(i18n.messages.inputSequence);
			fm.dangerNo[i].focus();
			return false;
		}

		if (isEmptyField(fm.dangerDesc[i])) {
			errorMessage(i18n.messages.inputRiskEvalution);
			fm.dangerDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevel[i])) {
			errorMessage(i18n.messages.inputRiskLevel);
			fm.riskLevel[i].focus();
			return false;
		}
		if (isEmptyField(fm.riskLevelDesc[i])) {
			errorMessage(i18n.messages.inputRiskEvalution);
			fm.riskLevelDesc[i].focus();
			return false;
		}
		if (isEmptyField(fm.currency[i])) {
			errorMessage(i18n.messages.inputRiskCurrency);
			fm.currency[i].focus();
			return false;
		}
		if (isEmptyField(fm.amount[i])) {
			errorMessage(i18n.messages.inputRiskCoverage);
			fm.amount[i].focus();
			return false;
		}
		if (isEmptyField(fm.premium[i])) {
			errorMessage(i18n.messages.inputRiskPremium);
			fm.premium[i].focus();
			return false;
		}
		if (isEmptyField(fm.chgAmount[i])) {
			errorMessage(i18n.messages.inputChangeCoverage);
			fm.chgAmount[i].focus();
			return false;
		}
		if (isEmptyField(fm.chgPremium[i])) {
			errorMessage(i18n.messages.inputChangePermium);
			fm.chgPremium[i].focus();
			return false;
		}
		if (fm.isSavaDangerUnit[i].value == "N") {
			errorMessage(i18n.messages.di + i + i18n.messages.clickCheckButtoon);
			return false;
		}
		amount += parseFloat(fm.amount[i].value);
		premium += parseFloat(fm.premium[i].value);
	}
	var flag = true;
	var msg = "";

	// 拆分后保额与标的总保额一致
	if (amount != sumAmount) {
		msg = i18n.messages.coverageConsistent + " \n"
				+ i18n.messages.presentBalance + (sumAmount - amount);
		flag = false;
	}
	if (premium != sumPremium) {
		if (msg != "")
			msg = "\n\n"
		msg += i18n.messages.premiumConsistent + "\n"
				+ i18n.messages.presentBalance + (sumPremium - premium);
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
	var submitStr = "/undwrt/common/CommonReinsSimulate.jsp?CertiNo=" + CertiNo
			+ "&CertiType=" + CertiType;
	window
			.open(
					submitStr,
					i18n.messages.trial,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}

// 在投保单,保单查看风险评估子信息时计算标的保额和保费
function calItemNumber() {
	var count = DangerItemForm.itemKindNo.length - 1;
	var amount = 0;
	var premium = 0;
	var tolPremium = 0;
	var dangerShare = 0;
	for (i = 1; i <= count; i++) {
		// modify begin by yangyd 070906 添加标的选择约束
		if (fm.checkDanger[i].checked == true) {
			premium += parseFloat(DangerItemForm.ItemPremium[i].value)
					* parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		// modify end by yangyd 070906 添加标的选择约束
		if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
			DangerItemForm.hiItemcalculateFlag[i].value = "Y";
			amount += parseFloat(DangerItemForm.ItemAmount[i].value)
					* parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
	}
	if (tolPremium == 0) {
		window.alert(i18n.messages.dataException);
		return;
	}
	dangerShare = premium / parseFloat(tolPremium) * 100;
	dangerShare = round(point(dangerShare, 5), 4);
	premium = round(point(premium, 3), 2);
	amount = round(point(amount, 3), 2);
	window.alert(i18n.messages.sumCoverage + amount + "   "
			+ i18n.messages.sumPremium + premium + "  "
			+ i18n.messages.occupyRatio + dangerShare + "%");
	DangerItemForm.amount[1].value = amount;
	DangerItemForm.premium[1].value = premium;
	DangerItemForm.dangerShare[1].value = dangerShare
}

// 在批单查看风险评估子信息时计算标的保额和保费
function calEndorseItemNumber() {
	var count = DangerItemForm.itemKindNo.length - 1;
	var amount = 0;
	var premium = 0;
	var chgAmount = 0;
	var chgPremium = 0;
	var tolPremium = 0;
	var dangerShare = 0;
	for (i = 1; i <= count; i++) {
		premium += parseFloat(DangerItemForm.ItemPremium[i].value)
				* parseFloat(DangerItemForm.exchangeRate[i].value);
		chgPremium += parseFloat(DangerItemForm.ItemchgPremium[i].value)
				* parseFloat(DangerItemForm.exchangeRate[i].value);
		if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
			DangerItemForm.hiItemcalculateFlag[i].value = "Y";
			amount += parseFloat(DangerItemForm.ItemAmount[i].value)
					* parseFloat(DangerItemForm.exchangeRate[i].value);
			chgAmount += parseFloat(DangerItemForm.ItemchgAmount[i].value)
					* parseFloat(DangerItemForm.exchangeRate[i].value);
		}
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
	}

	if (tolPremium == 0) {
		window.alert(i18n.messages.dataException);
		// return;
	}
	dangerShare = (premium + chgPremium) / parseFloat(tolPremium) * 100;
	dangerShare = round(point(dangerShare, 5), 4);
	premium = round(point(premium, 3), 2);
	amount = round(point(amount, 3), 2);
	chgAmount = round(point(chgAmount, 3), 2);
	chgPremium = round(point(chgPremium, 3), 2);
	window.alert(i18n.messages.sumCoverage + (amount + chgAmount) + "  "
			+ i18n.messages.sumPremium + (premium + chgPremium) + "  "
			+ i18n.messages.occupyRatio + dangerShare + "%");
	DangerItemForm.amount[1].value = amount;
	DangerItemForm.chgAmount[1].value = chgAmount;
	DangerItemForm.premium[1].value = premium;
	DangerItemForm.chgPremium[1].value = chgPremium;
	DangerItemForm.dangerShare[1].value = dangerShare;
}

// 对数字进行格式化,保证precision位
function point(number, precision) {
	if (isNaN(number))
		number = 0;

	var result = number.toString();
	if (result.indexOf(".") == -1)
		result = result + ".";

	result = result + newString("0", precision);
	result = result.substring(0, precision + result.indexOf(".") + 1);
	return result;
}

/**
 * 将给定字符串复制ｎ遍
 * 
 * @param intLength
 *            字符串长度
 * @return 字符串
 */
function newString(iString, iTimes) {
	var str = "";
	for ( var i = 0; i < iTimes; i++)
		str = str + iString;
	return str;
}

// 在查看风险评估子信息时初始化风险评估输入框
function initDangerUnitAtItem() {
	var dangerUnitCount = DangerItemForm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		insertRow('DangerUnit');
	} else {
		if (DangerItemForm.dangerAddress[1].value == "") {
			DangerItemForm.dangerAddress[1].value = DangerItemForm.ItemAddressName[1].value;
		}
	}
	var isNewDanger = DangerItemForm.isNewDangerInfo.value; // 新增风险评估时为1
	if (isNewDanger == 1) {
		DangerItemForm.hiDangerCoinsFlag[1].value = window.opener.fm.dangerCoinsFlag[1].value;
		DangerItemForm.hiDangerShareHolderFlag[1].value = window.opener.fm.dangerShareHolderFlag[1].value;
		DangerItemForm.hiDangerBusinessFlag[1].value = window.opener.fm.dangerBusinessFlag[1].value;
	}
	var dangerItemKindCount = DangerItemForm.dangerItemKind.length;
	for ( var i = 1; i < dangerItemKindCount; i++) {
		if (DangerItemForm.dangerItemKind[i].value == "")

		{
			for ( var j = 0; j < DangerItemForm.itemKind[i].length; j++) {

				// 默认标的类型为其他“Z99”
				if (DangerItemForm.itemKind[i].options[j].value == "Z99") {

					DangerItemForm.itemKind[i].options[j].selected = true;
					break;
				}
			}
		}
	}
	var count = DangerItemForm.checkDanger.length;
	if (DangerItemForm.riskUnitFlag.value == "1") {
		for (i = 1; i < count; i++) {
			DangerItemForm.checkDanger[i].checked = false;
		}
	}
	// /当除外责任是z99,默认进合约
	checkDangerItemFlag();
}

// 批单在查看风险评估子信息时初始化风险评估输入框
function initEndorseDangerUnitAtItem() {

	var dangerUnitCount = DangerItemForm.dangerNo.length;
	if (dangerUnitCount == null || dangerUnitCount <= 1) {
		insertRow('DangerUnit');
	}
	var isNewDanger = DangerItemForm.isNewDangerInfo.value; // 新增风险评估时为1
	if (DangerItemForm.itemKindNo.length != null && isNewDanger == 1) {
		var itemKindCount = DangerItemForm.itemKindNo.length - 1;
		var i = 0;
		var j = 0;
		var amount = 0;
		var premium = 0;
		var tolAmount = 0;
		var tolPremium = 0;
		if (itemKindCount == null) {
			sumAmount = 0;
			sumPremium = 0;
		} else {
			for (i = 1; i <= itemKindCount; i++) { // premium +=
													// parseFloat(DangerItemForm.ItemPremium[i].value)
													// *
													// parseFloat(DangerItemForm.exchangeRate[i].value);
				// sumPremium +=
				// parseFloat(DangerItemForm.ItemPremium[i].value);
				if (DangerItemForm.ItemcalculateFlag[i].value == "Y") {

					tolAmount += parseFloat(DangerItemForm.ItemAmount[i].value)
							* parseFloat(DangerItemForm.exchangeRate[i].value);
					tolPremium += parseFloat(DangerItemForm.ItemPremium[i].value)
							* parseFloat(DangerItemForm.exchangeRate[i].value);
				}
			}
		}
		premium = round(premium, 2);
		DangerItemForm.dangerNo[1].value = DangerItemForm.hiDangerNo.value;
		DangerItemForm.dangerDesc[1].value = "";
		DangerItemForm.dangerAddress[1].value = DangerItemForm.ItemAddressName[1].value;
		DangerItemForm.currency[1].value = DangerItemForm.ItemCurrency[1].value;
		// alert("==tolAmount="+tolAmount);
		// DangerItemForm.amount[1].value = tolAmount;
		DangerItemForm.chgAmount[1].value = 0.00;
		// alert("==tolPremium="+tolPremium);
		// DangerItemForm.premium[1].value = tolPremium;
		DangerItemForm.chgPremium[1].value = 0.00;
		DangerItemForm.dangerShare[1].value = "100.0000";
	}
	var dangerItemKindCount = DangerItemForm.dangerItemKind.length;
	for ( var i = 1; i < dangerItemKindCount; i++) {
		if (DangerItemForm.dangerItemKind[i].value == "") {
			for ( var j = 0; j < DangerItemForm.itemKind[i].length; j++) {
				// 默认标的类型为其他“Z99”
				if (DangerItemForm.itemKind[i].options[j].value == "Z99") {
					DangerItemForm.itemKind[i].options[j].selected = true;
					break;
				}
			}
		}
	}
	// /当除外责任是z99,默认进合约
	checkDangerItemFlag();
}

// 批单风险评估子信息页面关闭，提示保存
function endorseDangerItemTipBeforeClose() {
	var index = DangerItemForm.openerIndex.value;
	if (confirm(i18n.messages.confirmSave) == true) {
		window.opener.fm.dangerDesc[index].value = DangerItemForm.dangerDesc[1].value;
		window.opener.fm.dangerAddress[index].value = DangerItemForm.dangerAddress[1].value;
		window.opener.fm.dangerItemKind[index].value = DangerItemForm.itemKind[1].value;

		if (DangerItemForm.dangerItemFlag[1].checked == true) {// 标识位
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
	if (confirm(i18n.messages.dontSaveDataInfor) == true) {
		window.close();
	}

}
// modify begin 20060609 by lihua
// 提交分入
function reinsVerify() {
	var proposalNo = fm.proposalNo.value;
	var riskCode = fm.riskCode.value;
	var hiClassCode = fm.hiClassCode.value;
	fm.method = "post";
	var submitStr = "/undwrt/saveReins/enquiryReins.do?type=reinsVerify&proposalNo="
			+ proposalNo + "&riskCode=" + riskCode + "&hiClassCode="
			+ hiClassCode;
	window
			.open(
					submitStr,
					i18n.messages.submitConfirm,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// modify end 20060609 by lihua

// 分保试算
function simulateReinsByDanger() {
	// 新增是否做了风险评估的判断by wangjun20130620
	var retcurrency = fm.retCurrency[1].value;
	if (retcurrency == "" || retcurrency == null) {
		alert(i18n.messages.riskEstimate);
		return false;
	}

	// 判断是否做了风险评估
	if (checkRetenValueIsZero() == false) {
		return false;
	}

	var dangerNoCount = fm.dangerNo.length;
	var sPremium = 0;
	var flag1 = false;
	var flag2 = false;
	var flag4 = false;
	for (i = 1; i < dangerNoCount; i++) {
		// add by zhangpanlai begin 2007-09-11 当保额为0时禁止试算
		if (parseFloat(fm.amount[i].value) == 0) {
			window.alert(i18n.messages.dangerUnit + i.toString()
					+ i18n.messages.coverageCannotZero);
			return;
		}
		// add by zhangpanlai end 2007-09-11
		sPremium += parseFloat(fm.premium[i].value);
	}
	sPremium = mathRound(parseFloat(sPremium));
	// modify by yangxt 20100818 -- 危险单位与投保单币种不一致，导致校验失败，
	// 对于一个危险单位的情况取消校验，此为临时修改。
	if (dangerNoCount != 2) {
		if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
			// modify begin by zhaijq 20051227 从共保不校验总保额与风险评估总保额的大小
			if (fm.dangerCoinsFlag[1].value == "2") {
				window.alert(i18n.messages.sharePremium
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.sumPremium + parseFloat(sPremium));
			} else {
				window.alert(i18n.messages.premiumNotEqual + i18n.messages.cannotTrail + "\n" + "    "
						+ i18n.messages.originalPremium
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.presentPremium + parseFloat(sPremium));
				return;
			}
			// modify end by zhaijq 20051227
		}
	}
	// 意健险团体单必须先录入PML值才能进行分保试算
	for ( var i = 1; i < getElementCount("speValue", fm); i++) {
		// modify begin by zhaijq 20060414 2799含意外险要求输入PML值
		// if (parseFloat(fm.speValue[i].value)==0.00 && (fm.hiClassCode.value
		// == "26" || fm.hiClassCode.value == "27") && fm.policyType.value ==
		// "02")
		if (parseFloat(fm.speValue[i].value) == 0.00
				&& (fm.hiClassCode.value == "26" || fm.hiClassCode.value == "27")
				&& fm.policyType.value == "02"
				&& fm.includeAccident.value == "Y")
		// modify end by zhaijq 20060414
		{
			alert(i18n.messages.inputPMLValueFirst);
			return;
		}
	}
	var ClassCode = fm.hiClassCode.value;
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/undwrt/reinsTrialInfoView/reinsTrialInfo.do?CertiNo="
			+ CertiNo + "&CertiType=" + CertiType + "&ClassCode=" + ClassCode;
	window
			.open(
					submitStr,
					i18n.messages.trial,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

// 分批试算
function endorseSimulateReinsByDanger() {
	
	// 判断是否做了风险评估
	if (checkRetenValueIsZero() == false) {
		return false;
	}
	
	// 需要拆分风险评估的险种的处理--Start
	var dangerNoCount = fm.dangerNo.length;
	var sPremium = 0;
	var flag1 = false;
	var flag2 = false;
	var flag4 = false;
	for (i = 1; i < dangerNoCount; i++) {
		sPremium += parseFloat(fm.premium[i].value)
				+ parseFloat(fm.chgPremium[i].value);
	}
	sPremium = mathRound(parseFloat(sPremium));
	if (dangerNoCount != 2) {
		if (parseFloat(sPremium) != parseFloat(fm.tolPremium.value)) {
			// modify begin by zhaijq 20060214 从共保保额保费不再重新计算
			if (fm.coinsFlag.value == "2") {
				window.alert(i18n.messages.sharePremium
						+ i18n.messages.cannotTrail
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.sumPremium + parseFloat(sPremium));
			} else {
				window.alert(i18n.messages.premiumNotEqual + i18n.messages.cannotTrail + "\n" + "    "
						+ i18n.messages.originalPremium
						+ parseFloat(fm.tolPremium.value) + "\n" + "    "
						+ i18n.messages.presentPremium + parseFloat(sPremium));
				return;
			}
			// modify end by zhaijq 20060214
		}
	}
	var CertiNo = fm.hiBusinessNo.value;
	var ClassCode = fm.hiClassCode.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/undwrt/reinsTrialInfoView/reinsTrialInfo.do?CertiNo="
			+ CertiNo + "&CertiType=" + CertiType + "&ClassCode=" + ClassCode;
	window
			.open(
					submitStr,
					i18n.messages.trial,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

// 根据当前自留额判断是否做了风险评估
function checkRetenValueIsZero() {
	var retenValue = 0;
	//bh061 火險核保風險評估問題 20190103 start
	/*
	if (classCode == "F") {
		var dangerNoCount = parseInt(getElementCount("dangerNo", fm));
		for (i = 1; i < dangerNoCount; i++) {
			if (fm.riskLevel[i].value == "") {
				alert("請先對危險單位序號[" + fm.dangerNo[i].value + "]進行風險評估，再分保/分批試算！");
				return false;
			}
		}
	}
	*/
	//bh061 火險核保風險評估問題 20190103 end
	return true;
}

// 车险的分保试算
function simulateReinsByCar() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/undwrt/reinsTrialInfoView/reinsTrialInfo.do?CertiNo="
			+ CertiNo + "&CertiType=" + CertiType;
	window
			.open(
					submitStr,
					i18n.messages.trial,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

// 分摊试算 add by qinyongli 2005-8-23
function simulateReinsHepei() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var ClaimNo = fm.ClaimNo.value;
	var submitStr = "/undwrt/reinsTrialInfoView/reinsTrialInfo.do?CertiNo="
			+ CertiNo + "&CertiType=" + CertiType + "&ClaimNo=" + ClaimNo;
	window
			.open(
					submitStr,
					i18n.messages.trial2,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// 打开核保界面风险等级评估页面
function openDangerRiskInfo(Field, index) {
	var dangerNo = DangerItemForm.hiDangerNo.value;
	var businessNo = DangerItemForm.businessNo.value
	var riskCode = DangerItemForm.riskCode[1].value
	var classCode = DangerItemForm.classCode.value
	var businessType = DangerItemForm.hiBusinessType.value;
	var riskClass = DangerItemForm.riskClass[index].value;
	var itemKindCode = DangerItemForm.ItemKindCode[index].value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;
	var num = index;
	url = "/undwrt/dangerInfoView/commonDangerRiskLevel.do?Index=" + intIndex
			+ "&FieldName=" + fieldName + "&ReinsCode=" + Field.value
			+ "&businessNo=" + businessNo + "&dangerNo=" + dangerNo
			+ "&riskClass=" + riskClass + "&riskCode=" + riskCode
			+ "&classCode=" + classCode + "&businessType=" + businessType
			+ "&num=" + index+"&itemKindCode="+itemKindCode;
	window
			.open(url, "editwindow",
					"top=50,left=80,resizable=0,scrollbars,dependent,width=800,height=380");
}

// add by dongyanqi 增加查看功能20051012
// 查看风险等级评估
function viewDangerRiskInfo() {
	var dangerNo = DangerItemForm.hiDangerNo.value;
	var businessNo = DangerItemForm.businessNo.value
	var riskCode = DangerItemForm.riskCode.value
	var classCode = DangerItemForm.classCode.value
	var businessType = DangerItemForm.hiBusinessType.value;
	var riskLevelDesc = DangerItemForm.riskLevelDesc[1].value;

	var url = "/undwrt/dangerInfoView/commonDangerRiskInfoView.do?businessNo="
			+ businessNo + "&dangerNo=" + dangerNo + "&riskCode=" + riskCode
			+ "&riskLevelDesc=" + riskLevelDesc + "&classCode=" + classCode
			+ "&businessType=" + businessType;
	window
			.open(url, "editwindow",
					"top=50,left=80,resizable=0,scrollbars,dependent,width=600,height=500");
}
// add by dongyanqi 增加查看功能20051012
// 查看风险等级评估
function viewDangerRiskInfo2(field) {
	var index = getElementOrder(field);
	var dangerNo = fm.dangerNo[index].value;
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value
	var classCode = fm.hiClassCode.value
	var businessType = fm.hiBusinessType.value;
	var url = "/undwrt/dangerInfoView/commonDangerRiskInfoView.do?businessNo="
			+ businessNo + "&dangerNo=" + dangerNo + "&riskCode=" + riskCode
			+ "&classCode=" + classCode + "&businessType=" + businessType;
	window
			.open(url, "editwindow",
					"top=50,left=80,resizable=0,scrollbars,dependent,width=600,height=500");
}

// 风险等级评估
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
		if (fm.itemValue[i].value != "" && fm.itemValue[i].value >= 0
				&& fm.itemValue[i].value <= 100) {
			fm.hiItemValue[i].value = fm.itemValue[i].value;
		} else {
			window.alert(i18n.messages.inuptAgainValue2);
			fm.itemValue[i].focus();
			return;
		}

	}

	fm.method = "post";
	fm.action = "/undwrt/DangerRiskEvaluateFacade.do";
	fm.submit();
}

// 查看风险等级评估结果
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
			window.alert(i18n.messages.inputAgainValue);
			fm.itemValue[i].focus();
			return;
		}

	}

	fm.method = "post";
	fm.action = "/undwrt/DangerRiskEvaluateFacade.do?operateType=view";
	fm.submit();
}

// 风险等级评估结果回显到风险评估子信息
function reRetenValue() {
	try {
		if (fm.riskLevel.value == '' || fm.riskLevelDesc.value == ''
				|| fm.currency.value == '' || fm.retentionValue.value == '') {
		}
	} catch (ee) {
		return;
	}
	window.opener.DangerItemForm.riskLevel[1].value = fm.riskLevel.value;
	window.opener.DangerItemForm.riskLevelDesc[1].value = fm.riskLevelDesc.value;
	// modify begin 2008-03-12 by lihua 增加风险类别
	window.opener.DangerItemForm.riskClass[1].value = fm.riskClass.value;
	window.opener.DangerItemForm.riskClassDesc[1].value = fm.riskClassDesc.value;
	// modify end 2008-03-12 by lihua 增加风险类别
	window.opener.DangerItemForm.retCurrency[1].value = fm.currency.value;
	window.opener.DangerItemForm.retentionValue[1].value = fm.retentionValue.value;
	// modify begin 2008-02-19 by lihua 为改变除外责任改变自留额作铺垫，将自留额存储在一个备份的自留额子段中
	window.opener.DangerItemForm.retentionValueHidden[1].value = fm.retentionValue.value;
	// modify end 2008-02-19 by lihua
	window.close();

}

function reRetenValue2(index) {
	try {
		var num1 = fm.chooseFlag1.length;
		var count = num1;
		var flag = 0;
		if (count > 0) {
			for (i = 0; i < count; i++) {
				if (fm.chooseFlag1[i].checked) {
					window.opener.DangerItemForm.riskLevel[index].value = fm.riskLevel[i].value;
					window.opener.DangerItemForm.riskLevelDesc[index].value = fm.riskLevelDesc[i].value;
					window.opener.DangerItemForm.riskClass[index].value = fm.riskClass[i].value;
					window.opener.DangerItemForm.riskClassDesc[index].value = fm.riskClassDesc[i].value;
					window.opener.DangerItemForm.retCurrency[index].value = fm.currency[i].value;
					window.opener.DangerItemForm.retentionValue[index].value = fm.retentionValue[i].value;
					window.opener.DangerItemForm.heiRetentionValue.value = fm.retentionValue[i].value;
					window.opener.DangerItemForm.lowRetentionValue.value = fm.lowRetentionValue[i].value;
					flag = 1;
				}
			}
		}

		if (flag == 0) {
			window.alert(i18n.messages.chooseOneLeast);
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

// 针对整个投保单的风险等级评估结果回显
function reAllRetenValue() {
	try {
		if (fm.riskLevel.value == '' || fm.riskLevelDesc.value == ''
				|| fm.currency.value == '' || fm.retentionValue.value == '') {
		}
	} catch (ee) {
		return;
	}
	window.opener.fm.allRiskLevel.value = fm.riskLevel.value;
	window.opener.fm.allRiskLevelDesc.value = fm.riskLevelDesc.value;
	window.opener.fm.allRetentionCurrency.value = fm.currency.value;
	window.opener.fm.allRetentionValue.value = fm.retentionValue.value;
	window.close();
}

// 针对整个投保单的风险等级评估结果回显
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
			window.alert(i18n.messages.chooseOneLeast);
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

// 划分风险评估信息主界面时双击评估等级
function openEvaluateRiskInfo(Field) {
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value;
	var businessType = fm.hiBusinessType.value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;

	url = "/undwrt/CommonDangerRiskLevelFacade.do?Index=" + intIndex
			+ "&FieldName=" + fieldName + "&ReinsCode=" + Field.value
			+ "&businessNo=" + businessNo + "&riskCode=" + riskCode
			+ "&businessType=" + businessType;

	window
			.open(url, "editwindow",
					"top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
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
	if (fm.itemKind.value != "Z99"
			|| fm.itemKind.options[fm.itemKind.selectedIndex].text != i18n.messages.others) {
		if (fm.allDangerFlag.checked == true) {
			fm.allDangerFlag.checked = true;
		} else {

			fm.allDangerFlag.checked = false;
		}
	} else {
		// window.alert("标的类型为其他，不允许修改");
		fm.allDangerFlag.checked = false;
		return false;
	}
}

function pointTwo(s) {
	return point(s, 2);
}

// 对数字第三位四舍五入
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
			if (fm.dangerShare[i].value == 0) {
				window.alert(i18n.messages.divideAgainRisk);
				return false;
			}
			tempDangerShare += parseFloat(fm.dangerShare[i].value);
		}
		if (parseFloat(tempDangerShare) == 100) {
			// window.alert("占比正确");
			return;
		}
		errDangerShareValue = round(Math.abs(tempDangerShare - 100.00), 4);
		if (parseFloat(errDangerShareValue) > 0.0001) // 占比比例误差大于0.001
		{
			window.alert(i18n.messages.divideAgain);
			return false;

		} else { // 占比比例误差等于0.0001时，自动补0.0001
			for (i = 1; i < dangerNoCount - 1; i++) {// 求出前几个风险评估的占比比例
				tempDangerShare2 += parseFloat(fm.dangerShare[i].value);
			}
			newDanagerShare = round(parseFloat(100 - tempDangerShare2), 4);
			fm.dangerShare[dangerNoCount - 1].value = newDanagerShare;
			var url = "/undwrt/DangerInfoErrorModifyFacade.do?&errDangerShareValue="
					+ newDanagerShare
					+ "&maxDangerNo="
					+ maxDangerNo
					+ "&businessNo="
					+ businessNo
					+ "&businessType="
					+ businessType;
			var newWindow = window
					.open(url, "editwindow",
							"top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
			newWindow.focus();
			newWindow.close();
		}
	} catch (ee) {
	}
}
// ////
function updateExItemKind(field) {
	var businessNo = DangerItemForm.businessNo.value;
	var businessType = DangerItemForm.hiBusinessType.value;
	var dangerNo = DangerItemForm.hiDangerNo.value;
	var riskCode = field.value;

	if (riskCode.length < 4) {
		return false;
	}
	DangerItemForm.method = "post";
	if (businessType == "T") {
		DangerItemForm.action = "/undwrt/taskCheck/commonCheckTask.do?businessNo="
				+ businessNo
				+ "&businessType="
				+ businessType
				+ "&riskCode="
				+ riskCode
				+ "&iRiskCode="
				+ riskCode
				+ "&dangerNo="
				+ dangerNo
				+ "&showDangerItemFlag=1&editType=deal";
	} else if (businessType == "E") {
		var policyNo = DangerItemForm.policyNo.value;
		var classCode = DangerItemForm.classCode.value;
		var index = DangerItemForm.openerIndex.value;
		var isNewDangerInfo = DangerItemForm.isNewDangerInfo.value;
		var editType = DangerItemForm.editType.value;
		var includeAccident = DangerItemForm.includeAccident.value;

		DangerItemForm.action = "/undwrt/taskCheck/commonCheckTask.do?businessNo="
				+ businessNo
				+ "&policyNo="
				+ policyNo
				+ "&businessType="
				+ businessType
				+ "&riskCode="
				+ riskCode
				+ "&classCode="
				+ classCode
				+ "&hiDangerNo="
				+ dangerNo
				+ "&showDangerItemFlag=2"
				+ "&openerIndex="
				+ index
				+ "&NewDangerInfo="
				+ isNewDangerInfo
				+ "&editType="
				+ editType
				+ "&includeAccident=" + includeAccident;
	}
	DangerItemForm.submit();
}
// ////
// 提交任务时点取消返回查询页面
function backQuery() {
	fm.action = "/undwrt/taskMessage.do";
	fm.submit();
}

function submitEndorseTaskBefore(submitDirection) {
	// END
	// 校验风险评估划分比例是否100%
	// if (validateDangerData() == false)临时注掉三行,取消js验证
	// {
	// return;
	// }
	// add by zhaoning20090424 begin Reason:增加对非车险手工定级的校验
	// if(fm.hiClassCode.value != "05" && (fm.riskCode.value!="9997" &&
	// fm.riskCode.value!="9998" && fm.riskCode.value!="9999" ))
	// {
	// if(fm.HistoryBusiness.value != "1")
	// {
	// if(fm.ManualGrade.value == "1,1")
	// {
	// alert("系统信息:\n\n"+"请对该笔业务定级！");
	// fm.ManualGrade.focus();
	// return false;
	// }
	// }
	// }
	// add by zhaoning20090424 end
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}
	setButtondisable();
	fm.DealType.value = "submit";
	fm.SubmitDirection.value = submitDirection;
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.submit();
}

// add by dongyanqi核保查询页面增加显示风险评估信息
function showEvaluateRiskInfo(Field) {
	var businessNo = fm.hiBusinessNo.value;
	var riskCode = fm.riskCode.value;
	var businessType = fm.hiBusinessType.value;
	var fieldName = Field.name;
	var intIndex = parseInt(getElementOrder(Field)) - 1;

	url = "/undwrt/ShowEvaluateRiskInfo.do?Index=" + intIndex + "&FieldName="
			+ fieldName + "&ReinsCode=" + Field.value + "&businessNo="
			+ businessNo + "&riskCode=" + riskCode + "&businessType="
			+ businessType;
	// alert("url===="+URL);

	window
			.open(url, "editwindow",
					"top=50,left=80,resizable=0,scrollbars=1,dependent,width=600,height=500");
}
function showSimulateReins() {
	var CertiNo = fm.hiBusinessNo.value;
	var CertiType = fm.hiBusinessType.value;
	var submitStr = "/undwrt/ReinsTrialInfo.do?CertiNo=" + CertiNo
			+ "&CertiType=" + CertiType;
	window
			.open(
					submitStr,
					i18n.messages.trial,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

function comfirmRefuse() {
	if (confirm(i18n.messages.refuseSure)) {
		submitRefuse();
	}
}

// add by zhulei 20050725 拒保时，对审核意见必录的校验
function checkTextArea() {
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.inputApproveAdvice2);
		return false;
	}
	return true;
}

function forbidDelete() {
	alert(i18n.messages.notPermitDelete);
	return;
}

function checkSpeValue(field) {
	var curSpeValue = parseFloat(field.value.toString().replace(/,/g, ""));
	for ( var i = 1; i < DangerItemForm.speValue.length; i++) {
		if (curSpeValue > parseFloat(DangerItemForm.amount[i].value.toString()
				.replace(/,/g, ""))) {
			alert(i18n.messages.valueNotPermitCoverage);
			field.select();
			field.focus();
			return;
		}
		// add begin by zhaijq 20060316 PML值不允许为负值
		if (curSpeValue < 0) {
			alert(i18n.messages.valueNotPermit);
			field.select();
			field.focus();
			return;
		}
		// add end by zhaijq 20060316
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

	if (dblDisRate1Old != dblDisRate1
			&& confirm(i18n.messages.updateSpecialRate)) {
		dblDisRate1 = parseFloat(fm.DisRate1.value);
	} else {
		dblDisRate1 = parseFloat(fm.DisRate1Old.value);
	}
	if (isNaN(dblDisRate1))
		dblDisRate1 = 0;
	dblDisFee1 = calculateDisFee1(dblDisRate1);
	// xiaojian_leave：四舍五入的问题
	fm.DisRate1.value = point(dblDisRate1, 4);
	fm.DisFee1.value = point(dblDisFee1, 2);
	fm.target = "fraSubmit";
	fm.action = "/undwrt/SaveDisRate1.do";
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

	if (dblManageFeeRateOld != dblManageFeeRate
			&& confirm(i18n.messages.updateFeeRate)) {
		dblManageFeeRate = parseFloat(fm.ManageFeeRate.value);
	} else {
		dblManageFeeRate = parseFloat(fm.ManageFeeRateOld.value);
	}
	if (isNaN(dblManageFeeRate))
		dblManageFeeRate = 0;
	dblManageFee = calculateManageFee(dblManageFeeRate);
	fm.ManageFeeRate.value = point(dblManageFeeRate, 4);
	fm.ManageFee.value = point(dblManageFee, 2);
	fm.target = "fraSubmit";
	fm.action = "/undwrt/SavePrpExpense.do";
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
// add by zhulei 20060424 end 核保时可见、可修改费用比例 */

// 风险评估子信息中除外责任和进合约的选择时.
// 如果是Z99其他，则不允许修改进合约标志,
// 如果是B04,D07,Z96,Z97,Z98(申报业务)，则允许修改进合约标志，可以修改自留额

function checkDangerItemFlag(flag) {
	// modify begin 2008-02-19 by lihua 除外责任反复修改，从除外到非除外业务，自留额重新改回评估情况
	if (fm.itemKind[1].value == "Z99") {
		fm.dangerItemFlag[1].checked = true;
		fm.retentionValue[1].value = fm.retentionValueHidden[1].value;
	} else if (fm.itemKind[1].value == "Z98" || fm.itemKind[1].value == "Z97"
			|| fm.itemKind[1].value == "Z96" || fm.itemKind[1].value == "D07"
			|| fm.itemKind[1].value == "B04" || fm.itemKind[1].value == "T06"
			|| fm.itemKind[1].value == "T07") {
	} else if (fm.itemKind[1].value == "T99") {// 非合约业务险种
		fm.retentionValue[1].value = fm.retentionValueHidden[1].value;
	} else if (fm.itemKind[1].value == "T01" || fm.itemKind[1].value == "T05") {// modify
																				// begin
																				// 2008-12-31协议分保除外责任不清空自留额，选择不进入协议合约
		fm.dangerItemFlag[1].checked = false;
		fm.retentionValue[1].value = fm.retentionValueHidden[1].value;
	} else if (!(fm.itemKind[1].value == "Z98" || fm.itemKind[1].value == "Z97"
			|| fm.itemKind[1].value == "Z96" || fm.itemKind[1].value == "D07" || fm.itemKind[1].value == "B04")) {
		fm.dangerItemFlag[1].checked = false;
		fm.retentionValue[1].value = 0.0;
	}
}
// modify end 2008-02-19 by lihua

// add by wangshizhu 20070228 加入拆分风险评估
function calDangerAmountPremium() {
	var amount = 0.0;
	var premium = 0.0;
	// var tolAmount = 0.0;
	var tolPremium = 0.0;
	var dangerShare = 0.0;
	var hiDangerNo = DangerItemForm.hiDangerNo.value;
	var baseRate = parseFloat(DangerItemForm.baseRate[1].value);
	var coinsFlag = DangerItemForm.hiDangerCoinsFlag[1].value;
	var exchRate = 0.0;
	var currency = "";

	for (i = 1; i < DangerItemForm.itemKindNo.length; i++) {
		// modify begin by yangyd 调整占比计算方式
		// tolAmount = parseFloat(DangerItemForm.tolAmount[i].value);
		tolPremium = parseFloat(DangerItemForm.tolPremium[i].value);
		// modify end by yangyd 调整占比计算方式
		currency = DangerItemForm.currency2[i].value;
		if (fm.checkDanger[i].checked == true) {
			DangerItemForm.dangerFlag[i].value = hiDangerNo;
			exchRate = parseFloat(DangerItemForm.exchangeRate[i].value);

			if (DangerItemForm.ItemcalculateFlag[i].checked == true) {
				amount += parseFloat(DangerItemForm.ItemAmount[i].value
						.toString().replace(/,/g, "")
						* exchRate);
			}

			premium += parseFloat(DangerItemForm.ItemPremium[i].value
					.toString().replace(/,/g, "")
					* exchRate);
		} else {
			DangerItemForm.dangerFlag[i].value = "0";
		}

	}
	// add by zhukunlong reason:JPY's round in reins end:2009-3-18 11:11
	if (currency == "JPY") {
		premium = round(premium, 0);
	}
	// add by zhukunlong reason:JPY's round in reins end:2009-3-18 11:11
	if (coinsFlag == "2") {
		// add by zhukunlong reason:JPY's round in reins end:2009-3-18 11:11
		if (currency == "JPY") {
			premium = round(premium * 100 / baseRate, 0);
		} else {
			premium = round(premium * 100 / baseRate, 2);
		}
		// add by zhukunlong reason:JPY's round in reins end:2009-3-18 11:11
		amount = round(amount * 100 / baseRate, 2);
		tolPremium = round(tolPremium * 100 / baseRate, 2);
	} else {
		premium = round(premium, 2);
		amount = round(amount, 2);
		// tolAmount = round(point(tolAmount*exchRate,3),2);
	}
	if (tolPremium == 0) {
		dangerShare = 100.0;

	} else {
		// modify begin by yangyd 调整占比计算方式
		// dangerShare = round(point(amount*100/tolAmount,5),4);
		dangerShare = round(premium * 100 / tolPremium, 4);
		// modify end by yangyd 调整占比计算方式
	}
	if (DangerItemForm.riskCode.value != '1903') {
		DangerItemForm.amount[1].value = amount;
	}
	DangerItemForm.premium[1].value = premium;
	DangerItemForm.dangerShare[1].value = dangerShare;
	DangerItemForm.currency[1].value = currency;
}

// 计算批单风险评估的保额和保费
function calPDangerAmountPremium() {

	var tolAmount = 0.0;
	var amount = 0.0;
	var premium = 0.0;
	var dangerShare = 0.0;
	var hiDangerNo = DangerItemForm.hiDangerNo.value;
	var baseRate = parseFloat(DangerItemForm.baseRate[1].value);
	var coinsFlag = DangerItemForm.hiDangerCoinsFlag[1].value;
	var exchRate = 0.0;
	var currency = "";
	var chgamount = 0.0;
	var chgpremium = 0.0;
	var chgOriAmount = 0.0;
	var chgOriPremium = 0.0;

	var exchRate = 0.0;
	var coinsFlag = DangerItemForm.hiDangerCoinsFlag[1].value;

	for (i = 1; i < DangerItemForm.itemKindNo.length; i++) {
		tolAmount = parseFloat(DangerItemForm.tolAmount[i].value);
		exchRate = parseFloat(DangerItemForm.exchangeRate[i].value);
		currency = DangerItemForm.currency2[i].value;

		amount = parseFloat(DangerItemForm.amount[1].value);
		premium = parseFloat(DangerItemForm.premium[1].value);
		tolPremium = parseFloat(DangerItemForm.tolPremium[1].value);
		if (fm.checkDanger[i].checked == true) {
			// tolAmount = parseFloat(DangerItemForm.tolAmount[i].value);

			if (DangerItemForm.ItemcalculateFlag[i].value == "Y") {
				exchRate = parseFloat(DangerItemForm.exchangeRate[i].value);
				DangerItemForm.dangerFlag[i].value = hiDangerNo;
				chgamount += parseFloat((parseFloat(DangerItemForm.ItemAmount[i].value) + parseFloat(DangerItemForm.ItemchgAmount[i].value))
						* exchRate);
			}
			// modify begin by fengbo 20070420 附件险也计入保费
			chgpremium += parseFloat((parseFloat(DangerItemForm.ItemPremium[i].value) + parseFloat(DangerItemForm.ItemchgPremium[i].value))
					* exchRate);
			// modify end by fengbo 20070420 附件险也计入保费
		} else {
			DangerItemForm.dangerFlag[i].value = "0";
		}
	}
	if (tolAmount == 0) {
		dangerShare = 100.0;
	} else {
		// modify begin by yangyd 070906 调整占比生成
		// dangerShare = round(point((chgamount)*100/tolAmount,5),4);
		dangerShare = round((chgpremium) * 100 / tolPremium, 4);
		// modify end by yangyd 070906 调整占比生成
	}

	chgamount = chgamount - amount;
	chgpremium = chgpremium - premium;

	/*
	 * if (coinsFlag == "2") { amount = round(point(amount*100/baseRate,3),2);
	 * chgpremium = round(point(chgpremium*100/baseRate,3),2); chgamount =
	 * round(point(chgamount*100/baseRate,3),2); tolPremium =
	 * round(point(tolPremium*100/baseRate,3),2); }else{
	 */
	amount = round(amount, 2);
	chgpremium = round(chgpremium, 2);
	chgamount = round(chgamount, 2);
	tolAmount = round(tolAmount, 2);
	tolPremium = round(tolPremium, 2);
	// }
	if (DangerItemForm.riskCode.value != '1903') {
		DangerItemForm.chgAmount[1].value = chgamount;
	}
	DangerItemForm.chgPremium[1].value = chgpremium;
	DangerItemForm.dangerShare[1].value = dangerShare;
	DangerItemForm.currency[1].value = currency;
}

// add by chenqiuqiao 2007-5-14 费率规则帮助查看方法 begin
function viewRateRuleHelp() {
	var strURL = "/undwrt/help/HF" + fm.riskCode.value + "001"
			+ fm.language.value + ".pdf";
	var strWindowName = i18n.messages.rateRuleHelp;
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	window
			.open(
					strURL,
					strWindowName,
					'width='
							+ pageWidth
							+ ',height='
							+ pageHeight
							+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// add by chenqiuqiao 2007-5-14 费率规则帮助查看方法 end

// 提交分入确认（批单）
function reinsPolicyVerify(endorNo) {
	var endorNo = endorNo.value;
	var riskCode = fm.riskCode.value;
	var hiClassCode = fm.hiClassCode.value;
	var policyNo = fm.hiPolicyNo.value;

	fm.method = "post";
	var submitStr = "/undwrt/EnquiryFacade.do?type=reinsVerify&endorNo="
			+ endorNo + "&riskCode=" + riskCode + "&hiClassCode=" + hiClassCode
			+ "&policyNo=" + policyNo;
	window
			.open(
					submitStr,
					i18n.messages.submitConfirm,
					'width=750,height=600,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// 关联交强险投保单详细信息按钮
function showBusinessCIInfo() {
	var vBusinessNo = fm.businessNoCI.value; // 取业务号
	var vCertiType = fm.hiBusinessType.value; // 取业务类型
	var vBizType = "";
	var vRiskCode = fm.riskCodeCI.value; // 取险种代码
	var vCommonRisk = fm.CommonRisk.value;// 取通用险种代码
	var vRiskClass = fm.classCode.value; // 得到险类
	var vUserCode = fm.OperatorCode.value; // 得到人员
	var vRiskCodeTemp = vRiskCode;
	var vMyComCode = fm.comCode.value;
	var prpallIP = fm.PrpallIp.value;
	if (vCertiType == "T" || vCertiType == "P") {
		var vURL = prpallIP + "/prpins/policy/browseproposalForCommonview.do?bizNo=" + vBusinessNo + "&riskCode=" + vRiskCode + "&systemCode=commonview";
		window
				.open(
						vURL,
						i18n.messages.detailedMessage,
						'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	} else if (vCertiType == "E") {
			if (vCertiType == "T")
				vBizType = "PROPOSAL";
			else if (vCertiType == "P")
				vBizType = "POLICY";
			else if (vCertiType == "E")
				vBizType = "ENDORSE";
			var vURL = '/prpins/' + vRiskCodeTemp + '/tbcbpg/UIPrPoEn'
					+ vRiskCodeTemp + 'Show.jsp?BIZTYPE=' + vBizType
					+ '&SHOWTYPE=SHOW&BizNo=' + vBusinessNo + '&RiskCode='
					+ vRiskCode + '&UserCode=' + vUserCode + '&myComCode='
					+ vMyComCode;
			var vURL = prpallIP + "/prpins/policy/browseproposalForCommonview.do?bizNo=" + vBusinessNo + "&riskCode=" + vRiskCode + "&systemCode=commonview";
			vURL = prpallIP + vURL;
			window
					.open(
							vURL,
							i18n.messages.detailedMessage,
							'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
}

function setButtondisable() {
	fm.butSaveForm.disabled = true;
	fm.butCancel.disabled = true;
	fm.passBtn.disabled = true;
	fm.submitSuperior.disabled = true;
	fm.submitJunior.disabled = true;
}

/**
 * @description 校验核保员的权限
 * @rule 2A(含)权限以下核保人员只能向下调整分级结果，1C权限(含)权限以上核保人员可向上或向下调整分级结果
 * @param 无
 * @return 无
 */
function checkGrade() {
	var vNodeNo = "";// 核保节点
	var vAutoGradeValue = 0;// 自动定级分值
	var vAutoGradeCode = "";
	var vGradeValue = 0;// 手工定级分值
	var vGradeValues;
	var vGrade = "";
	vNodeNo = fm.NodeNo.value;
	vAutoGradeValue = parseFloat(fm.AutoGradeValue.value);
	vAutoGradeCode = fm.AutoGradeCode.value;
	for ( var i = 0; i < fm.ManualGrade.options.length; i++) {
		if (fm.ManualGrade.options[i].selected) {
			vGrade = fm.ManualGrade.options[i].value;
			vGradeValues = vGrade.split(",");
			vGradeValue = parseFloat(vGradeValues[0]);
			break;
		}
	}
	if (fm.ManualGrade.options[0].selected) {
		getDefaultGrade();// 获取定级初始值
	} else {
		if (parseInt(vNodeNo) <= 7) {
			if (vAutoGradeValue < vGradeValue
					&& (vAutoGradeCode != "R" && vAutoGradeCode != "R1" && vAutoGradeCode != "R2")) {
				alert(i18n.messages.adjustment + "\r\n"
						+ i18n.messages.limitNotEnough);
				getDefaultGrade();// 获取定级初始值
				fm.ManualGrade.selectedIndex = 0;
				return;
			} else {
				getGradeInfo();
			}
		} else {
			getGradeInfo();
		}
	}
}

// 获取级别信息.净费率
function getGradeInfo() {
	var vGradeCode = "";
	var vGradeValues;
	var vGrade = "";
	var dblMakeRate = 0;
	var dblMaxUsableRate = 0;
	var dblNetPremiumRate = 0;
	var vClassCode = "";

	vClassCode = fm.hiClassCode.value;

	dblMakeRate = parseFloat(fm.MakeRate.value);

	for ( var i = 0; i < fm.ManualGrade.options.length; i++) {
		if (fm.ManualGrade.options[i].selected) {
			vGrade = fm.ManualGrade.options[i].value;
			vGradeValues = vGrade.split(",");
			fm.GradeCode.value = fm.ManualGrade.options[i].text;
			fm.GradeValue.value = vGradeValues[0];
			fm.MaxUsableRate.value = parseFloat(vGradeValues[1]) * 100;
			dblMaxUsableRate = parseFloat(vGradeValues[1]);
			break;
		}
	}
	if (vClassCode == "01" || vClassCode == "07") {
		dblNetPremiumRate = round(
				round(dblMakeRate * (1 - dblMaxUsableRate), 4), 3);
	}
	fm.NetPremiumRate.value = round(dblNetPremiumRate, 3);
}

// 查看关联业务信息
function getRelBusiness() {
	var submitStr;
	var vBusinessNo;
	var vBusinessType = "";
	vBusinessNo = fm.BusinessNo.value;
	vBusinessType = fm.BusinessType.value;
	if (vBusinessType == "E") {
		vBusinessNo = fm.hiProposalNo.value;
	}
	submitStr = "/undwrt/CommonGradeGroupDetail.do?BusinessNo=" + vBusinessNo;
	window
			.open(
					submitStr,
					i18n.messages.relevanceBusinessInfor,
					'width=1024,height=480,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}

// 获取自动定级的净费率.最大可用费用率
function getDefaultGrade() {
	var vClassCode = "";
	var dblMakeRate = 0;
	var dblAutoMaxUsableRate = 0;
	var dblNetPremiumRate = 0;
	vClassCode = fm.hiClassCode.value
	dblMakeRate = parseFloat(fm.MakeRate.value);

	fm.MaxUsableRate.value = fm.AutoMaxUsableRate.value;
	if (vClassCode == "01" || vClassCode == "07") {
		dblAutoMaxUsableRate = parseFloat(fm.AutoMaxUsableRate.value) / 100;
		dblNetPremiumRate = round(round(dblMakeRate
				* (1 - dblAutoMaxUsableRate), 4), 3);
		fm.NetPremiumRate.value = round(dblNetPremiumRate, 3);
	}
}

// 查看定级轨迹信息
function getGradeTraceInfo() {
	var submitStr;
	var BusinessNo;
	BusinessNo = fm.BusinessNo.value;
	submitStr = "/undwrt/CommonGradeTrace.do?BusinessNo=" + BusinessNo;
	window
			.open(
					submitStr,
					i18n.messages.rankTrackInfor,
					'width=1024,height=480,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
}
// 校验定级信息
function checkGradeInfo() {
	// 非车险并且不是预约协议大保单则定级校验
	// if(fm.hiClassCode.value != "05" && (fm.riskCode.value!="9997" &&
	// fm.riskCode.value!="9998" && fm.riskCode.value!="9999" ))
	// {
	// if(fm.HistoryBusiness.value != "1")
	// {
	// var vDisRate = parseFloat(fm.DisRate.value);
	// var vMaxUsableRate = parseFloat(fm.MaxUsableRate.value);
	// if(fm.ManualGrade.value == "1,1")
	// {
	// alert("系统信息:\n\n"+"请对该笔业务定级！");
	// fm.ManualGrade.focus();
	// return false;
	// }
	// //非车险部提出:取消该校验.调整为使用费用控制策略进行控制
	// /*
	// if(vDisRate>0)
	// {
	// if(checkHistoryBusiness()==false)
	// {
	// if(vDisRate>vMaxUsableRate &&
	// fm.RelBusinessFlag.value=="0")//RelBusinessFlag为1是关联业务为0是非关联业务
	// {
	// alert("系统信息:\n\n"+"手续费率高于最大可用费用率，需要下发修改，继续协商代理手续费！");
	// return false;
	// }
	// }
	// }
	// */
	// }
	// }
}

function checkHistoryBusiness() {
	var strBusinessNo = fm.BusinessNo.value;
	var strBusinssType = fm.hiBusinessType.value;
	var vURL = "";
	var vXmlText = "";
	vURL = "/undwrt/common/CheckBusiness.jsp?BusinessNo=" + strBusinessNo
			+ "&BusinessType=" + strBusinssType;
	vXmlText = getILogResponseXmlText(vURL);
	if (vXmlText == "TRUE") {
		return true;
	} else {
		return false;
	}
}

function submitILog() {
	var strBusinessNo = fm.BusinessNo.value;
	var strBusinssType = fm.hiBusinessType.value;
	var vURL = "";
	var vXmlText = "";
	var vILogInfoURL = "";
	vILogInfoURL = "/undwrt/common/CommonILogFInfo.jsp"

	vURL = "/undwrt/common/SubmitILog.jsp?BusinessNo=" + strBusinessNo
			+ "&BusinessType=" + strBusinssType;
	vXmlText = getILogResponseXmlText(vURL);
	if (vXmlText == "Error") {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.checkInternate);
		return false;
	} else {
		// arrILogF共有六个元素分别是:操作种类,规则信息,核保通过级别,强制人工核保标志,再保继续进行标志,核保通过级别名称
		var arrILogF = vXmlText.split("^");
		// 如果规则引擎异常则返回异常信息,否则校验是否违反再保数据规则
		if (arrILogF[0] == "3") {
			window
					.showModalDialog(vILogInfoURL, arrILogF,
							'dialogHeight:650px;dialogWidth:600px;center:yes;resizable:no');
			return false;
		} else {
			// 再保继续进行标志：默认值0，可以继续进行再保操作，当该标志置为1时，不能继续再保操作
			if (arrILogF[4] == "1") {
				window
						.showModalDialog(vILogInfoURL, arrILogF,
								'dialogHeight:650px;dialogWidth:600px;center:yes;resizable:no');
				return false;
			} else {
				if (arrILogF[1] != "") {
					window
							.showModalDialog(vILogInfoURL, arrILogF,
									'dialogHeight:650px;dialogWidth:600px;center:yes;resizable:no');
					return true;
				}
			}
		}
		return true;
	}
}

// 使用xmlhttp访问页面，并获取数据
function getILogResponseXmlText(strURL) {
	var objXmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	objXmlHttp.Open("POST", strURL, false);
	objXmlHttp.setRequestHeader("Content-type", "text/xml");
	objXmlHttp.Send("");
	if (objXmlHttp.status == 200) {
		return objXmlHttp.responseXML.text;
	} else if (objXmlHttp.status == 404) {
		alert(i18n.messages.cannotFindPage + strURL);
		return "";
	} else {
		alert(i18n.messages.visit + strURL + i18n.messages.errorNo
				+ objXmlHttp.status);
		return "";
	}
}

function loadForm() {
	// 对分级信息进行初始化 begin
	if (fm.hiClassCode
			&& fm.hiClassCode.value != "05"
			&& (fm.riskCode.value != "9997" && fm.riskCode.value != "9998" && fm.riskCode.value != "9999")) {
		if (fm.HistoryBusiness && fm.HistoryBusiness.value != "1") {
			fm.ManualGrade.onchange();
		}
	}
	// 对分级信息进行初始化 end
}

function showNotifyInfo() {
	var vURL = "";
	var vBusinessNo = "";
	var vRiskCode = "";
	vBusinessNo = fm.hiBusinessNo.value; // 取业务号
	vRiskCode = fm.riskCode.value;
	vURL = "/prpins" + fm.NotifyPath.value + "?ProposalNo=" + vBusinessNo;
	window
			.open(
					vURL,
					i18n.messages.informInfor,
					'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
function getSelectedBizNoCount() {
	var i = 0;
	var j = 0;
	if (typeof (fm.checkboxSelect) == 'undefined') {
		return j;
	} else {
		if (typeof (fm.checkboxSelect.length) == 'undefined') {
			if(fm.checkboxSelect.checked)
			{
				return j + 1;
			}
			else
			{
				return j;
			}
		}
		for (i = 0; i < fm.checkboxSelect.length; i++) {
			if (fm.checkboxSelect[i].checked == true)
				j++;
		}
	}

	return j;

}

function Directlyissued() {
	if (getSelectedBizNoCount() <= 0) {
		alert(i18n.messages.chooseSendUpdateNo);
		return;
	}
	if (getSelectedBizNoCount() > 1) {
		alert(i18n.messages.chooseMoreData);
	}
	var HandType = fm.handType.value;
	fm.action = "/undwrt/passOffModify/cancelBatchTask.do?EditType=CancelDeal&HandType="
			+ HandType;
	fm.method = "post";
	fm.submit();

}

function checkRelBusiness(i) {
	var strBusinessNo = "";
	var strBusinessType = "";
	var strRiskCode = "";
	var vURL = "";
	var vXmlText = "";
	if (isNaN(fm.RiskCode.length)) {
		strRiskCode = fm.RiskCode.value;
		strBusinessType = fm.BusinessType.value;
		strBusinessNo = fm.BusinessNo.value;
	} else {
		strRiskCode = fm.RiskCode[i].value;
		strBusinessType = fm.BusinessType[i].value;
		strBusinessNo = fm.BusinessNo[i].value;
	}
	if (strRiskCode == "0507") {
		vURL = "/undwrt/common/CheckRelBusiness.jsp?BusinessNo="
				+ strBusinessNo + "&BusinessType=" + strBusinessType;
		vXmlText = getILogResponseXmlText(vURL);
		if (vXmlText != "") {
			alert(i18n.messages.chooseBussinessRisk + vXmlText
					+ i18n.messages.conductSend);
			fm.checkboxSelect[i].checked = false;
		}
	}
}

function compareRetentionValue() {
	var retentionValueNo = fm.retentionValue[1].value * 1;
	var lowRetentionValueNo = fm.lowRetentionValue.value * 1;
	var heiRetentionValueNo = fm.heiRetentionValue.value * 1;
	if (retentionValueNo < lowRetentionValueNo) {
		alert(i18n.messages.retentionCannotLess + lowRetentionValueNo);
		fm.retentionValue[1].value = lowRetentionValueNo;
		return;
	}
	if (retentionValueNo > heiRetentionValueNo) {
		alert(i18n.messages.retentionCannotGreater + heiRetentionValueNo);
		fm.retentionValue[1].value = heiRetentionValueNo;
		return;
	}
}

function checkdangerShare() {

	var sunShare = 0.0;
	var varShare;
	var riskCode = fm.strRiskCode.value;
	var ClassCode = riskCode.substring(0, 2);
	if (ClassCode == "05" || riskCode == "9997" || riskCode == "9999") {
		return true;
	} else {
		var count = fm.dangerShare.length;
		if (count > 1) {
			for (i = 1; i < count; i++) {
				varShare = fm.dangerShare[i].value;
				sunShare = sunShare + parseFloat(varShare);
			}
			sunShare = round(sunShare, 2);
			if (sunShare != 100) {
				alert(i18n.messages.dangerUnitRatio);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
//没有FhRiskItemKind表，临时注掉js校验，后期再改20140421
function checkItemKind(field) {
//	var itemkindstr = fm.hiRiskItemStr.value;
//	var index = parseInt(getElementOrder(field)) - 1;
//	var str = fm.ItemKindCode[index].value;
//	if (itemkindstr.indexOf(str) < 0) {
//		alert(i18n.messages.riskNotBelong + fm.hiRiskCode.value
//				+ i18n.messages.scope);
//		fm.checkDanger[index].checked = false;
//		return false;
//	}
}

// 报价审核提交
function submitTaskQta(taskCode) {
	//add by xuhuiling 需求150 點擊提交核保 begin
//	alert(fm.BusinessType.value+":edit");
//	alert(fm.BusinessNo.value+":businessNo");
	var busiNo = fm.BusinessNo.value
	var busiType = fm.BusinessType.value
	var strNotion = fm.notion.value;
	var workStatus = "";
	var valueType = "";
	var callbackForStatus ={
			 success:function(res){
		    	 var jsonObject = YAHOO.lang.JSON.parse(res.responseText);
		    	 workStatus = jsonObject.workStatus;
		    	 valueType = jsonObject.valueType;
		    	 if(strNotion=="001"){//001：審核標語為同意
			    	 //03作業狀態是拒保，04是可承保
					 if(workStatus!="03"&&workStatus!="04"&&workStatus!="00"){
						 if(valueType!=1){
	 						//01
	 						submitFlag = "01";
	 						alert("拒限保，名單檢測，風險評級狀態未設置完成，不能進行後續處理！");
	 						return false;
	 					}
					 }
		    	 }
//add by xuhuiling 需求150 點擊提交核保 end
	fm.taskCode.value = taskCode;
	var businessType = fm.hiBusinessType.value;

	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}

	if (confirm(i18n.messages.submitOrNot)) {
		setButtondisable();
		fm.action = "/undwrt/submitTask/commonDealSubmit.do";
		fm.submit();
	}
	// 判断参数是否为数组
	function isItArray(obj) {
		return '[object Array]' == Object.prototype.toString.call(obj);
	}
	  },
    failure:function(res){
   	 errorMessage("調用人工開關失敗！");//		 	 
      } 				 			
   };
YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/getWorkStatusAndValueType.do?busiNo='+busiNo+'&busiType='+busiType, callbackForStatus, null,false);
}
//add by xuhuiling 需求150 點擊提交核保 end
// add by tianbaiyu影像查询
function queryImage() {
	// 新影像上传改造20110628 lipei start
	// 如果是新影像功能上线前的案件，走老系统的程序
	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = fm.remoteUrl.value + "?" + fm.paramString.value
			+ "&allowUpload=false&allowModifiedImage=true";
	fm.target = "fraSubmit";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
	
}
// and by gss 影像上传
function uploadImage(userCode,comCode,businessNo) {
	
	var oldAction = fm.action;
	var oldTarget = fm.target;
	var pathPrefix = "prpins";
	//var pathPrefix = "prpall";
	//fm.action = "/undwrt/taskCheck/commonCheckTask1.do";
	//fm.target = "fraSubmit";
	fm.action="http://"+fm.remoteUrl1.value+"/filemanager/fileupload/FileUpload?userCode="+userCode+"&operatorCode="+userCode+"&comCode="+comCode+"&prpallURL=null&fileTransServiceUrl=http://"+fm.remoteUrl1.value+"/filemanager/services/FileTrans&fileIndexServiceUrl=http://"+fm.remoteUrl1.value+"/filemanager/services/FileIndex&batchFlag=0&pathPrefix="+pathPrefix+"&allowUpload=true&allowModeifedImage=false&isUpFileFlow=0&remarks=&bussNo="+businessNo;
	fm.target = "fraSubmit";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
	
}
//保存任务前之提交
function submitModifyTask(submitDirection,taskCode) {
	// 当审批意见为空时，对其中是否有回车的判断
	if (isEmptyField(fm.HandleText)) {
		alert(i18n.messages.systemInformation + "\n\n"
				+ i18n.messages.inputApproveAdvice);
		fm.HandleText.focus();
		return false;
	}
	fm.taskCode.value = taskCode;
	fm.DealType.value = "submit";
	setButtondisable();
	fm.target = "fraInterface";
	fm.action = "/undwrt/handleTask/commonDealTask.do";
	fm.SubmitDirection.value = submitDirection;
	fm.submit();
}
//报价单暂存功能函数
function saveQtaTask(submitDirection,taskCode) {
if (isEmptyField(fm.HandleText)) {
	alert(i18n.messages.systemInformation + "\n\n"
			+ i18n.messages.inputApproveAdvice);
	fm.HandleText.focus();
	return false;
}
setButtondisable();
fm.DealType.value = "save";
fm.target = "fraInterface";
fm.action = "/undwrt/handleTask/commonDealTask.do";
fm.submit();

}
//商火危险单位做临分js函数add by WangJun 20140306
function ChangeToValue(flag,name)
{
	var index = getElementOrder(flag) - 1 ;
	if(name[index].checked)
	{
		fm.whetherFacing[index].value="1";
	}
	else
	{
		fm.whetherFacing[index].value="0" 
	}
}
//理赔记录查看js函数
function openClaimInfo()
{
	var win;
	var businessNo = fm.BusinessNo.value;
	var claimInfo = "/undwrt/claimInfo/showClaimInfo.do?businessNo="
			+ businessNo+ "&actionType=queryClaimInfo";
	win = window
			.open(claimInfo, "",
					"status=no,resizable=no,scrollbars=yes,width=1000,Height=600,left=20,top=70");
}
//自定义js的map实现20140718 by wangJun
function getMap(){  
    var map_ = new Object();
    map_.put = function(key, value) {     
        map_[key+'_'] = value;     
    };     
    map_.get = function(key) {     
        return map_[key+'_'];     
    };     
    map_.remove = function(key) {     
        delete map_[key+'_'];     
    };     
    map_.keyset = function() {     
        var ret = "";     
        for(var p in map_) {
            if(typeof p == 'string' && p.substring(p.length-1) == "_") { 
            	ret += ",";     
                ret += p.substring(0,p.length-1);     
            }     
        }     
        if(ret == "") {     
            return ret.split(",");     
        } else {     
            return ret.substring(1).split(",");     
        }     
    };
    map_.size = function() {
    	var count = 0;
        for(var p in map_) {
            if(typeof p == 'string' && p.substring(p.length-1) == "_") { 
            	count=count+1;
            }     
        }
            return count;
    };
    return map_;
}

//add by xuhuiling 需求150 點擊查看按鈕的提示
function checkPrompt(busiNo,busiType,_this){
	//判斷人工開關，如果是開啟則
	//‘人工維護開關’狀態爲『關閉』，則需查看該筆報價單要保書或批單的‘作業狀態’， 當‘作業狀態’為『查詢中』的時候
	//1為開啟狀態,0為關閉狀態
	var workStatus = "";
	var valueType = "";
	
	
	var callbackForStatus ={
			 success:function(res){
		    	 workStatus = res.responseText;
		    	 if(workStatus=="02"){
		    		 window.alert(i18n.messages.queryingPleaseWait);
						return false;
					}
					if(workStatus=="03"||workStatus=="04"){
						window.alert(i18n.messages.refreshPage);
						return false;
					}
					//当作业状态是查询异常，查询超时，待再查询时可以调用AML系统
					if(workStatus=="01"||workStatus=="05"||workStatus=="06"){
						//調用AML系統的囘調方法
						var callAMLMessage ="";
						var callAMLSystem ={
								 success:function(res){
									 callAMLMessage = res.responseText;
							    	 alert(callAMLMessage);
							    	 _this.disabled = false;
						    	 },
							     failure:function(res){
							    	 errorMessage("調用AML系統異常！");
							    	 _this.disabled = false;
							     } 				 			
							};
						if(confirm("確認調用反洗錢系統么？")){
							_this.disabled = "disable";
							YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/amlSystem.do?busiNo='+busiNo+'&busiType='+busiType,callAMLSystem, "text");
						}
						return false;
					}
	    	 },
		     failure:function(res){
		    	 errorMessage("獲取作業狀態失敗！");	
		     } 				 			
		};
	
	var callbackForValueType ={
			success:function(res){
			    valueType = res.responseText;
			    if(valueType==1){
					//作業狀態為查詢中
			    	window.alert(i18n.messages.notUseAMLSystem);
					return false;
				}else{
					YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/hebaoTaskDealForWorkStatus.do?busiNo='+busiNo+'&busiType='+busiType, callbackForStatus, "text");
				}
			},
			failure:function(res){
				errorMessage("獲取人工開關失敗！");
			} 				 			
	};
	//modify by bh054 mantis5951 20180822
	var timeout;
	var waitSecond =5; // 倒數秒數
	var count = waitSecond;

	$(function() {

		// timeout = setTimeout(BtnCount, 1000); // 1s執行一次BtnCount
		$("input[name='viewRisk']").click(function() {
			$("input[name='viewRisk']").prop("disabled", true)
			timeout = setTimeout(BtnCount, 1000); // 1s執行一次BtnCount
		});

	});

	function BtnCount() {
		// 启动按钮
		if (count == 0) {
			$("input[name='viewRisk']").prop("disabled", false);
			$("input[name='viewRisk']").val("查看");
			clearTimeout(timeout); // 可取消由 setTimeout() 方法设置的 timeout
			count = waitSecond;
		} else {
			count--;
			$("input[name='viewRisk']").val("倒數(" + count.toString() + "秒)後可以按");
			setTimeout(BtnCount, 1000);
		}
	};
	YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/hebaoTaskDealForValueType.do', callbackForValueType, "text");

}
//add by songxin 
function checkPayRef(busiNo,busiType,_this){
	debugger;
	
//	var callbackForPayRef = {
//			success:function(res){
//				var flag = res.responseText;
//				if(flag == '1'){
////					alert("該要保書已經收費，不需進行此操作!");
//				}else if(flag == '2'){
//					//alert("符合條件，允許進行此操作!");
					_this.disabled = "disable";
					window.open("/undwrt/undwrtDeal/getPayFeeInfo.do?busiNo="+busiNo+"&busiType="+busiType, "繳費信息輸入頁面", "width=700,height=350,top=150,left=350,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0");
//				}else if(flag == '3'){
////					alert("該要保書不是預核保狀態，不需進行此操作!");
//				}else if(flag == '4'){
////					alert("該要保書不是收費出單的要保書，不需進行此操作!");
//				}else if(flag == '5'){
////					alert("該要保書不是強制險/任意險的要保書，不需進行此操作!");
//				}
//			},
//			failure:function(res){
//				errorMessage("查詢失敗");
//			} 
//	};
//	YAHOO.util.Connect.asyncRequest('POST','/undwrt/undwrtDeal/hebaoTaskDealForPayRef.do?busiNo='+busiNo,callbackForPayRef,"text");
}

//mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月START
function validateStartDateForF01(){
	var startDate = fm.startDate.value;// 保險起日
	startDate=parseInt(startDate.substring(0,startDate.indexOf("-")))+startDate.substring(startDate.indexOf("-"));
	var startDate2 = new Date(replace(replace(startDate,".0",""),"-","/"));
	startDate2.setHours(23);
	startDate2.setMinutes(59);
	startDate2.setSeconds(59);
	
	var sysDate = new Date();
	sysDate.setMonth( sysDate.getMonth() - 6 );
	
	if(startDate2 < sysDate){
		return true;
	}
	return false;
}
//mantis： FIR0351，處理人員：DP0713，需求單編號：保期起日新增檢核不得早於系統日六個月END

//mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- start
function validateStartDateForEMF() {
	var startDate = fm.startDate.value; // 保單起日
	if (startDate!='') {
		startDate=parseInt(startDate.substring(0,startDate.indexOf("-")))+startDate.substring(startDate.indexOf("-"));
		var startDate2 = new Date(replace(replace(startDate,".0",""),"-","/"));
		startDate2.setHours(23);
		startDate2.setMinutes(59);
		startDate2.setSeconds(59);
		
		var sysDate = new Date();
		sysDate.setMonth(sysDate.getMonth() + 3);
		
		if (startDate2 >= sysDate) {
			alert('保單起始日超過三個月，請確認資料!');
		}
	}
}
// mantis：EGN0109，處理人員：DP0714，新增檢核保期起日不能超過三個月(含工程險、商火、水險) -- end

//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
function addressCompareAjax(businessNo, type){
	var s = JSON.stringify(getAddressCompareObj(businessNo, type));
	var resultStr = invokeDwrMethod("DwrUtilService.addressCompareQueryStatus", s);
	var resultVo = JSON.parse(resultStr);
	return resultVo;
}

function getAddressCompareObj(businessNo, type){
	var obj = new Object();
	//地址類型 1-通訊(必填) 2-戶籍
	obj.businessNo = businessNo;
	obj.businessType = type;
	return obj;
}

/** 
 * 调用DWR的方法获取数据  
 * 
 * 作者：王致富
 * 日期：2010-01-27
 */
function invokeDwrMethod(methodName, params) {
	var _data;
 	//设置成同步
    dwr.engine.setAsync(false);
 	eval(methodName)(params,callBack);
 	//重新设置成异步
 	dwr.engine.setAsync(true);
 	
 	return _data;
 	
 	function callBack(data){
 		_data = data;
 	}
}
//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END