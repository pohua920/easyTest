<%--
****************************************************************************
mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增(query)
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
<html>
<head>
<!-- 公用函数 -->
<%@ include file="CommonStyle.html"%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<script type="text/javascript">
	var riskCodes = $.parseJSON('${riskCodeCollection}');
</script>
<script src="${ctx }/pages/undwrt/common/js/Common.js"></script>
<script src="${ctx }/pages/undwrt/common/js/WfLogQuery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/pages/undwrt/css/Standard.css">
</head>
<body>
	<form name="fm" method="post" action="${ctx }/CommonHeapCheckTask.do?actionType=query">
		<input type="hidden" name="HandType" value="22">
		<input type="hidden" name="EditType" value='${param.EditType}'>
		<input type="hidden" name="rtnCount" value="${requestScope.rtnCount}">
		<input type="hidden" name="choseNodeStatus" value="">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class=listtitle>
				<td colspan="4" style="width: 100%">
					<s:text name="title.undwrtBeforeEdit.SearchTasks" />
					<%--核赔任务查询 --%>
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr>
				<td class="title4" style="width: 15%">
					賠付代號
					：
				</td>
				<%-- 業務號碼 --%>
				<td class="input4" style="width: 35%">
					<select class="common" name="payCodeType" onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);">
						<option value="1">一般賠案</option>
						<option value="2">同業</option>
						<option value="3">健保局</option>
					</select>
				</td>
				<td class="title4" style="width: 15%">
					<s:text name="uwcondition.InsuranceCategories" />：<%--险种大类 --%>
				</td>
				<td class="input4" style="width: 35%">
					<input type="hidden" name="riskCategoryTag" value="=">
					<select class="common" id="riskCategory" name="riskCategory" onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);" disabled="true" >
						<option value=""><s:text name="uwcondition.FullRisk" /><%--全险种 --%></option>
						<option value="D" selected><s:text name="uwcondition.AutoRisk" /><%--车险 --%></option>
						<option value="Y"><s:text name="uwcondition.MarineRisk" /><%--水险 --%></option>
						<option value="E"><s:text name="uwcondition.KeenIdeaRisk" /><%--意健 --%></option>
						<option value="G"><s:text name="uwcondition.engineeringrisk" /><%--工程 --%></option>
						<option value="Z"><s:text name="uwcondition.liabilityrisk" /><%--责任 --%></option>
						<option value="Q"><s:text name="uwcondition.firerisk" /><%--火险 --%></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title4">
					賠付對象統一編號/身分證號
					：
				</td>
				<td class="input4">
					<input class=query type="text" name="uniformNo" MaxLength="10" onkeypress="" onclick="">
				</td>
				<td class="title4" style="width: 15%">
					<s:text name="db.prpDdbs.riskCode" />：<%-- 險種--%>
				</td>
				<td class="input4" style="width: 35%">
					<input type="hidden" name="riskCodeTag" value="=">
					<select class="common" name="riskCode" >
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="title4">
					<s:text name="undwrt.SubmissionTime" />：<%--提交時間--%>
				</td>
				<td class="input4">
					<input type="hidden" name="flowInTime1Tag" value=">=">
					<rc:rcDate name="flowInTime1" title="起始提交時間" style="width:120px" value="${requestScope.startDate}" />
					&nbsp;
					<s:text name="prompt.to" />
					&nbsp;
					<input type="hidden" name="flowInTime2Tag" value="<=">
					<rc:rcDate name="flowInTime2" title="終止提交時間" style="width:120px" value="${requestScope.endDate}" />
				</td>
				<td class="title4" style="width: 15%">
					費用類型:
				</td>
				<td class="input4" style="width: 35%">
					<select class="common" id="paymentKind" name="paymentKind" onchange=""  >
						<option value="">請選擇</option>
						<option value="1">修車廠</option>
						<option value="2">材料商</option>
						<option value="3">公司行號</option>
						<option value="4">個人</option>
						<option value="5">公證公司</option>
						<option value="6">健保局</option>
						<option value="7">同業</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="input4" colspan="4">
					<font color='red'>
						<marquee behavior=alternate scrollamount=2>
							預設提交時間是兩個月以內，請分公司不定期地調整時間範圍，檢查是否有遺漏任務尚未處理。
						</marquee>
					</font>
				</td>
			</tr>
			
			<tr>
				<td class="title4">
					<s:text name="db.prpDshortrate.validStatus" />：<%-- 狀態 --%>
				</td>
				<td class="input4" colspan="3">
					<input type="checkbox" name="nodeStatus" value="1" checked onclick="checkNodeStatus('1');">
					<s:text name="specialCase.ToProcessed" />
					<%--待处理 --%>
					&nbsp;&nbsp;
					<input type="checkbox" name="nodeStatus" value="2" checked onclick="checkNodeStatus('2');">
					<s:text name="check.dealingWith" />
					<%--正在处理--%>
					&nbsp;&nbsp;
					<input type="checkbox" name="nodeStatus" value="3" checked onclick="checkNodeStatus('3');">
					<s:text name="uwcondition.HandlingCirculation" />
					<%--已处理未流转 --%>
					&nbsp;&nbsp;
					<input type="checkbox" name="nodeStatus" value="5" checked onclick="checkNodeStatus('5');">
					<s:text name="uwcondition.backModified" />
					<%--打回修改 --%>
					&nbsp;&nbsp;
					<input type="checkbox" name="nodeStatus" value="4" onclick="checkNodeStatus('4');">
					<s:text name="uwcondition.ProcessedCirculation" />
					<%--已处理流转 --%>
					&nbsp;&nbsp;
					<input type="checkbox" name="nodeStatus" value="0" onclick="checkNodeStatus('0');">
					<s:text name="uwcondition.dealtTransfer" />
					<%--已处理完毕 --%>
				</td>
			</tr>
			<%--<tr>
				<td class="input4" style="color: red" colspan="2">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 -->
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。 -->
				</td>
				<td class="input4" style="color: red" colspan="2" align='center'>
					<input type=button id="button" name="urgentCaseButton" style="color: #000000;background-image:url(${ctx}/images/BgLongButton.gif);text-align: center;height: 24px;width: 150px;border: none;"
						value="<s:text name="title.compensate.emergencyCaseListing" />" onClick="queryUndwrtUrgentCase();">
					紧急案件清单
					<br> <font color='red'><s:text name="prompt.regist.emergencyCaseList" /></font>
					点此按钮显示权限范围内所有紧急案件清单
				</td>
			</tr> --%>
		</table>
		&nbsp;
		<table class=two>
			<tr>
				<td align=center>
					<Input class="button" name="buttonSubmit" type="button" value="<s:text name="button.query.value" />" onclick="validateForm(this);">
					<%--查 询 --%>
				</td>
			</tr>
		</table>
	</form>
</body>
<script language="javascript">
	$(document).ready(function () {
	    $('#riskCategory').change();
	    if('${requestScope.rtnCount}' === "0"){
	    	alert("查無資料!!請重新查詢");
	    }
	});
	
	function underlingValue() {
		if (fm.underling.value == "Y") {
			fm.underling.value = "N";
		} else if (fm.underling.value == "N") {
			fm.underling.value = "Y";
		}
	}
	
	function otherClear() {
		//fm.businessNo.value = "";
		//fm.policyNo.value = "";
		//fm.claimNo.value = "";
		//fm.comCode.value = "";	
	}
	function queryUndwrtUrgentCase() {
		var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase";	
		var newWindow = window.open(linkURL,"紧急案件清单","width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	}
	
	function validateForm(field) {
		try{
			var flowDate1_field = $("input[name^=flowInTime1_]").val();
			var fd1 = flowDate1_field.split("-");
			$("input[name=flowInTime1]").val(1911+parseInt(fd1[0],10)+"-"+fd1[1]+"-"+fd1[2]);
		}catch(e){}

		try{
			var flowDate2_field = $("input[name^=flowInTime2_]").val();
			var fd2 = flowDate2_field.split("-");
			$("input[name=flowInTime2]").val(1911+parseInt(fd2[0],10)+"-"+fd2[1]+"-"+fd2[2]);
		}catch(e){}
		
		debugger;
		if(fm.uniformNo.value ==""){
			alert("必須填寫 賠付對象統一編號/身分證號");
			return false;
		}
		if (fm.uniformNo.value.length==8){
			if(!checkUniformNo(fm.uniformNo.value)) {
				alert("請錄入正確的統一編號");
				return false;
			}
		}else{ 
			if (!/^[A-Za-z]/.test(fm.uniformNo.value)){
				alert("請錄入正確的身份證號");
				return false;
			}
			if(!checkIdentifyNumber(fm.uniformNo.value, "9")) {//這個共用方法會自己產出錯誤訊息
				return false;
			}
		} 
		var nodeStatusObj = document.fm.nodeStatus;
		var choseNodeStatus;
		for(var i =0;i<nodeStatusObj.length;i++){
			if(nodeStatusObj.item(i).checked){
				choseNodeStatus+=","+i;
			}
		}
		document.fm.choseNodeStatus.value = choseNodeStatus;
		/*
		if (fm.EditType.value == "deal" || fm.EditType.value == "query") {
			if (nodeStatusObj.item(0).checked == false && nodeStatusObj.item(1).checked == false &&
				nodeStatusObj.item(2).checked == false && nodeStatusObj.item(3).checked == false &&
				nodeStatusObj.item(4).checked == false && nodeStatusObj.item(5).checked == false) {
				alert("必须选择任務状态！");
				return false;
			}
				//核赔已处理完毕任務查询增加必输入查询条件
			if (nodeStatusObj.item(5).checked == true) {
				if (trim(fm.businessNo.value) == '' && trim(fm.policyNo.value) == '' && trim(fm.claimNo.value) == '') {
					alert("請輸入 業務號 或 保單號 或 立案號 進行查詢！");
					return false;
				}
			}
		}*/
		// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
		field.disabled = true;
		fm.submit();
	}
</script>
</html>