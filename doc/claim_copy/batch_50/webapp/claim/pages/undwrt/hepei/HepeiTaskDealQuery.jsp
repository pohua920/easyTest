<%--
****************************************************************************
* DESC	 ：核赔查询输入界面
* AUTHOR	 ： claim
* CREATEDATE ： 2013-06-22
* MODIFYLIST ： Name	 Date			Reason/Contents
*		------------------------------------------------------
							zhangshi		20130512				修改模糊查询为右模糊查询，替换CommonStringOption.jsp为CommonStringOption2.jsp
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
	<form name="fm" method="post" action="${ctx }/hepeiTaskDeal.do?actionType=query">
		<input type="hidden" name="HandType" value="22">
		<input type="hidden" name="EditType" value='${param.EditType}'>
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
					<s:text name="compensate.computeBookNum" />
					：
				</td>
				<%-- 業務號碼 --%>
				<td class="input4" style="width: 35%">
					<select class="tag" name="businessNoTag"><%@include file="CommonStringOption2.jsp"%></select>
					<input class=query type="text" name="businessNo" MaxLength="25" onkeypress="return isInteger(this)" onclick="registNoClear()">
				</td>
				<td class="title4" style="width: 15%">
					<s:text name="uwcondition.InsuranceCategories" />：<%--险种大类 --%>
				</td>
				<td class="input4" style="width: 35%">
					<input type="hidden" name="riskCategoryTag" value="=">
					<select class="common" name="riskCategory" onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);">
						<option value=""><s:text name="uwcondition.FullRisk" /><%--全险种 --%></option>
						<option value="D"><s:text name="uwcondition.AutoRisk" /><%--车险 --%></option>
						<option value="Y"><s:text name="uwcondition.MarineRisk" /><%--水险 --%></option>
						<option value="E"><s:text name="uwcondition.KeenIdeaRisk" /><%--意健 --%></option>
						<option value="G"><s:text name="uwcondition.engineeringrisk" /><%--工程 --%></option>
						<option value="Z"><s:text name="uwcondition.liabilityrisk" /><%--责任 --%></option>
						<option value="Q"><s:text name="uwcondition.firerisk" /><%--火险 --%></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title4" style="width: 15%">
					<s:text name="prompt.queRegist.PolicyNo" />
					：
					<%--保單號碼--%>
				</td>
				<td class="input4" style="width: 35%">
					<select class="tag" name="policyNoTag"><%@include file="CommonStringOption2.jsp"%></select>
					<input class=query type="text" name="policyNo" MaxLength="25" onkeypress="" onclick="registNoClear()">
				</td>
				<td class="title4" rowspan="5" style="width: 15%">
					<s:text name="db.prpDdbs.riskCode" />：<%-- 險種--%>
				</td>
				<td class="input4" rowspan="5" style="width: 35%">
					<input type="hidden" name="riskCodeTag" value="=">
					<select class="common" name="riskCode" size="10" multiple >
					</select>
				</td>
			</tr>
			<tr>
				<td class="title4">
					<s:text name="db.prpLclaim.claimNo" />：<%-- 立案號碼 --%>
				</td>
				<td class="input4">
					<select class="tag" name="claimNoTag"><%@include file="CommonStringOption2.jsp"%></select>
					<input class=query type="text" name="claimNo" MaxLength="21" onkeypress="" onclick="registNoClear()">
				</td>
			</tr>
			<tr>
				<td class="title4">
					<s:text name="db.prpDcompany.comCode" />：<%--機構代碼 --%>
				</td>
				<td class="input4">
					<select class="tag" name="comCodeTag"><%@include file="CommonStringOption2.jsp"%></select>
					<input class=query type="text" name="comCode" MaxLength="8" onclick="registNoClear()">
				</td>
			</tr>
			<tr>
				<td class="title4">
					<s:text name="uwcondition.ContainsLowerLevels" />：<%-- 包含下階 --%>
				</td>
				<td class="input4">
					<input type="hidden" name="underling" value="N">
					<select name="selectUnderling" class="common" style="width: 200px;">
						<c:forEach items="${requestScope.nodeCollection}" var="nodeListDto">
							<option value="${nodeListDto.nodeNo}">${nodeListDto.nodeName }</option>
						</c:forEach>
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
			</tr>
			<tr>
				<td class="input4" colspan="4">
					<font color='red'>
						<marquee behavior=alternate scrollamount=2>
							<s:text name="uwcondition.Message" />
							<%--默认提交时间是一个月以内，请分公司不定期地调整时间范围，检查是否有遗漏任务尚未处理。 --%>
						</marquee>
					</font>
				</td>
			</tr>
			<tr>
				<td class='title4'>
					<s:text name="db.prpCmain.insured" />ID：<%-- 被保險人ID--%>
				</td>
				<td class='input4'>
					<select class=tag name="InsuredIdentifyNumberSign">
						<option value="=">=</option>
					</select>
					<input type=text name="InsuredIdentifyNumber" class="query" value="<c:out value="${param.InsuredIdentifyNumber}"/>">
				</td>
				<td class='title4'>
					<s:text name="db.prpLregist.appliNameCode" />ID：<%-- 要保人身份证号--%>
				</td>
				<td class='input4'>
					<select class=tag name="AppliIdentifyNumberSign">
						<option value="=">=</option>
					</select>
					<input type=text name="AppliIdentifyNumber" class="query" value="<c:out value="${param.AppliIdentifyNumber}"/>">
				</td>
			</tr>
			<tr>
				<td class='title4'>
					<s:text name="query.damageDate" />：<%-- 事故日期 --%>
				</td>
				<td class='input4' align="left">
					<rc:rcDate name="damageStartDate" style="width:140px" value="${requestScope.damageStartDate}" />
					&nbsp;
					<s:text name="prompt.to" />
					&nbsp;
					<rc:rcDate name="damageEndDate" style="width:140px" value="${requestScope.damageEndDate}" />
				</td>
				<td class='title4'>
					<s:text name="prompt.queRegist.RegistNo" />：<%-- 備案號碼 --%>
				</td>
				<td class='input4'>
					<select class=tag name="RegistNoTag">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query" onclick="otherClear()">
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
			<tr>
				<td class="input4" style="color: red" colspan="2">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。 --%>
				</td>
				<td class="input4" style="color: red" colspan="2" align='center'>
					<input type=button id="button" name="urgentCaseButton" style="color: #000000;background-image:url(${ctx}/images/BgLongButton.gif);text-align: center;height: 24px;width: 150px;border: none;"
						value="<s:text name="title.compensate.emergencyCaseListing" />" onClick="queryUndwrtUrgentCase();">
					<%--紧急案件清单 --%>
					<br> <font color='red'><s:text name="prompt.regist.emergencyCaseList" /></font>
					<%--点此按钮显示权限范围内所有紧急案件清单 --%>
				</td>
			</tr>
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
	function underlingValue() {
		if (fm.underling.value == "Y") {
			fm.underling.value = "N";
		} else if (fm.underling.value == "N") {
			fm.underling.value = "Y";
		}
	}
	function registNoClear() {
		fm.RegistNo.value = "";	
	}
	function otherClear() {
		fm.businessNo.value = "";
		fm.policyNo.value = "";
		fm.claimNo.value = "";
		fm.comCode.value = "";	
	}
	function queryUndwrtUrgentCase() {
		var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase";	
		var newWindow = window.open(linkURL,"紧急案件清单","width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	}
	
	function validateForm(field) {
	var nodeStatusObj = document.fm.nodeStatus;
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
	}
	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}
</script>
</html>