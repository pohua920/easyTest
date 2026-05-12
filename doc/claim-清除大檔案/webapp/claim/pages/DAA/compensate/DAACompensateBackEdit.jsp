<%--
****************************************************************************
* DESC       ：理算退回定损录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-04-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.adjustBackDeal" /> <%-- 理算回退处理 --%></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script language="javascript">	  
function saveForm() {
	var haveCompensate = fm.haveCompensate.value;
	var permitBack = fm.permitBack.value
	var rowcount = fm.txtcertaadd.length;
	var nowrow = 1;

	if (permitBack == "0") {
		alert("此案件已经有提交的计算书，不能进行理算回退操作，详细可以查看计算书的情况!");
		return false;
	}

	if ((haveCompensate == "1") && (!window.confirm("理算退回，原有计算书信息不再保留，请确认是否继续！"))) {
		return false;
	}
	var haveselect = -1;
	for (nowrow = 1; nowrow < rowcount; nowrow++) {
		if (fm.txtcertaadd[nowrow].checked == true) {
			haveselect = 1;
			fm.selectCerta[nowrow].value = "1";
		}
	}

	if (haveselect == -1) {
		alert("请至少选择一个可供回退的定损任務!");
		return false;
	}
	fm.submit(); //提交
}
</script>
</head>
<body class="interface" onload="initPage();">
	<form name=fm action="/claim/compensate/compensateBackSave.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${param.editType == 'ADD' || param.editType == 'EDIT'}">
			<s:token></s:token>
		</c:if>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td width="30%">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="12">
								<img src="${ctx}/images/bgBarLeft.gif" width="12" height="19">
							</td>
							<td class="formtitle">
								<s:text name="compensate.adjustBackDeal" />
							</td>
							<%-- 理算回退处理 --%>
							<td width="11">
								<img src=" images/bgBarRight.gif" width="11" height="19">
							</td>
						</tr>
					</table>
				</td>
				<input type="hidden" name="swfLogFlowID" class="common" value="<c:out value='${param.swfLogFlowID}' />">
				<input type="hidden" name="swfLogLogNo" class="common" value="<c:out value='${param.swfLogLogNo}' />">
				<td width="70%" align="right">
					<font color="#666666"> <s:text name="prompt.check.note" /> <%-- 注 --%>：“<font color="#FF0000">*</font>” <s:text name="prompt.check.fieldWill" /> <%-- 为必选项 --%>，“<img
						src="images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">” <s:text name="prompt.check.doubleClick" /> <%-- 为双击选择项 --%>。
					</font>
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 100%">
			<tr>
				<td class="title" colspan="4" style="width: 100%">
					<s:text name="compensate.adjustBackDeal" />
				</td>
				<%-- 理算回退处理 --%>
			</tr>
			<tr>
				<td class="title" style="width: 15%">
					<s:text name="db.view_larrearage.policyNo" />:
				</td>
				<%-- 保单号 --%>
				<td class="input" style="width: 35%">
					<input type=text name="prpLverifyLossPolicyNoShow" class="readonly" readonly="true" style="width: 140px" value="<c:out value='${prpLverifyLoss.policyNo}' />">
				</td>
				<td class="title" style="width: 15%" style="valign:bottom">
					<s:text name="prpLclaim.claimNo" />:
				</td>
				<%-- 立案号 --%>
				<td class="input" style="width: 35%">
					<input type=text name="prpLverifyLossClaimNoShow" class="readonly" readonly="true" style="width: 140px" value="<c:out value='${prpLverifyLoss.claimNo}' />">
					<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLverifyLossPolicyNoShow.value);return false;">
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 15%">
					<s:text name="prpLregist.registNo" />:
				</td>
				<%-- 报案号 --%>
				<td class="input" style="width: 35%">
					<input type=text name="prpLverifyLossRegistNoShow" class="readonly" readonly="true"  value="<c:out value='${prpLverifyLoss.id.registNo}'/>">
				</td>
				<td class="title" style="width: 15%">
					<s:text name="compensate.backReason" />:
				</td>
				<%-- 退回原因 --%>
				<td class="input" style='width: 35%'>
					<s:select name="compensateOpinion" list="#request.compensateBackOptionsList" listKey="key" listValue="value" value="#request.prpLverifyLoss.compensateOpinion" style="width:85%" />
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 15%">
					<s:text name="compensate.adjustmentBackTime" />:
				</td>
				<%-- 理算回退时间 --%>
				<td class="common" style="width: 35%">
					<rc:rcDate name="prpLverifyLossCompensateBackDate" value="${prpLverifyLoss.compensateBackDate}" class="readonly" wdatePicker="false"/>
				</td>
				<td class="title" style="width: 15%" style="valign:bottom">
					<s:text name="compensate.adjustmentBackPeople" />:
				</td>
				<%-- 理算回退人员 --%>
				<td class="common" style="width: 35%">
					<input name="prpLverifyLossCompensateApproverCode" class="readonly" readonly maxlength=10 style="width: 40%" value="<c:out value='${prpLverifyLoss.compensateApproverCode}'/>">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input name="prpLverifyLossCompensateApproverName" class="readonly" readonly maxlength="100" style="width: 45%" value="<c:out value='${prpLverifyLoss.compensateApproverName}'/>">
				</td>
			</tr>
		</table>
		<input name="permitBack" type=hidden value="<c:out value='${prpLverifyLoss.flag}'/>">
		<c:if test="${empty prpLcompensate.compensateList}">
			<input name="haveCompensate" type=hidden value="0">
		</c:if>
		<c:if test="${not empty prpLcompensate.compensateList}">
			<input name="haveCompensate" type=hidden value="1">
			<table align="center" border="0" cellpadding="0" cellspacing="1" bgcolor="#2D8EE1" class="title" width="100%">
				<tr>
					<td style="width: 100%"><%@include file="/pages/common/compensate/CompensateMainBeforeEdit.jsp"%></td>
				</tr>
			</table>
		</c:if>
		</br>
		<table id="CertainLoss" align="center" border="0" cellpadding="0" cellspacing="0" bgcolor="#2D8EE1" class="title" width="100%">
			<tr>
				<td style="width: 100%">
					<input type="hidden" name="carLossSize" value="1">
					<table border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#2D8EE1" class="title" width="100%">
						<tr>
							<td class="common" style="text-align: left;" colspan="3">
								<s:text name="compensate.feeBackChoose" />
							</td>
							<%-- 定损退回选择 --%>
						</tr>
						<tr class="common" style="display: none">
							<td colspan="3">
								<input type="checkbox" name="txtcertaadd" style="width: 15px">
								<input type="hidden" name="lossitemCode" value="">
								<input type="hidden" name="nodeType" value="">
								<input type="hidden" name="selectCerta" value="">
							</td>
						</tr>
						<tr>
							<td class="centertitle" style="width: 1%">
								<s:text name="regist.prpLregist.serialNo" />
								<%-- 序号 --%>
							</td>
							<td class="centertitle" style="width: 15%">
								<s:text name="compensate.underly" />
								<%-- 标的 --%>
							</td>
							<td class="centertitle" style="width: 55%">
								<s:text name="compensate.contentFee" />
								<%-- 定损内容 --%>
							</td>
						</tr>
						<c:set var="lossItemTitle" value="" scope="page"/>
						<c:forEach items="${prpLverifyLoss.verifyLossList}" var="verifyLoss" varStatus="stat">
							<c:if test="${stat.index%2==0}">
								<tr class=oddrow>
							</c:if>
							<c:if test="${stat.index%2!=0}">
								<tr class=oddrow>
							</c:if>
							<c:choose>
								<c:when test="${verifyLoss.id.nodeType=='propc'}">
									<c:set var="lossItemTitle" value="財產" scope="page"/>
								</c:when>
								<c:when test="${verifyLoss.id.nodeType=='wound'}">
									<c:set var="lossItemTitle" value="人傷" scope="page"/>
								</c:when>
								<c:when test="${verifyLoss.id.nodeType=='certa'&&verifyLoss.id.lossItemCode=='1'}">
									<c:set var="lossItemTitle" value="標的車輛" scope="page"/>
								</c:when>
								<c:when test="${verifyLoss.id.nodeType=='certa'&&verifyLoss.id.lossItemCode!='1'}">
									<c:set var="lossItemTitle" value="第三方車輛" scope="page"/>
								</c:when>
							</c:choose>
							<td class="title" style="text-align: center">
								<input type="checkbox" name="txtcertaadd" checked style="width: 15px">
								&nbsp;
								<c:out value='${stat.count}' />
							</td>
							<td class="title">${lossItemTitle }：
								<c:out value='${verifyLoss.lossItemName}' />
							</td>
							<td class="title">${lossItemTitle }<s:text name="compensate.fee" />
								<%-- 定损 --%>
								<input type="hidden" name="lossitemCode" value="<c:out value='${verifyLoss.id.lossItemCode}'/>">
								<input type="hidden" name="nodeType" value="${verifyLoss.id.nodeType}">
								<input type="hidden" name="selectCerta" value="">
							</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td>
					<input type="hidden" name=buttonSaveType value="2">
				</td>
			</tr>
			<tr>
				<td class=button style="width: 20%" align="center">
					<!--确定按钮-->
					<input type="button" name=buttonSave class='button' value="<s:text name='button.determine.value' />" onclick="return saveForm();">
				</td>
				<td class=button style="width: 33%" align="center">
					<!--返回按钮-->
					<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="history.back();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
