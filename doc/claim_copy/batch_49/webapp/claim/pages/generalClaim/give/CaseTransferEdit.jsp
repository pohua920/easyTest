<%--
****************************************************************************
* DESC       ：代查勘委托页面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<html >
<head>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.generalClaimGive"></s:text></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 标签页样式 --%>
<script language="Javascript" src="${ctx }/pages/generalClaim/js/General.js"></script>
</head>
<body>
	<form name="fm" action="" method="post">
		<s:token></s:token>
		<input class='input' type="hidden" name="actionType" value="${actionType}">
		<input class='input' type="hidden" name="receiveComcode" value="${receiveComcode}">
		<table class="common" style="width: 100%" cellspacing="1" cellpadding="5">
			<thead>
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="general.case"/>
					</td>
					<%--案件信息 --%>
					<td class="subformtitle">
						<input type="button" name="showWorkFlower" value="賠案處理記錄" onclick="showWorkFlowerByRegistNo('${prpLregist.registNo}');" class="bigbutton"/>
					</td>
				</tr>
				<tr>
					<td class="centertitle">
						<s:text name="prompt.queRegist.RegistNo"/>
					</td>
					<%--报案号--%>
					<td class="centertitle">
						<s:text name="prompt.queRegist.PolicyNo" />
					</td>
					<%-- 保单号 --%>
					<td class="centertitle">
						<s:text name="db.prpLcharge.riskCode" />
					</td>
					<%-- 险种 --%>
					<td class="centertitle">
						<s:text name="general.insuredName" />
					</td>
					<%-- 被保险人 --%>
					<td class="centertitle">
						<s:text name="general.damageStartDate" />
					</td>
					<%-- 出险时间 --%>
					<td class="centertitle">
						<s:text name="general.reportDate" />
					</td>
					<%-- 报案时间 --%>
					<td class="centertitle">
						<s:text name="general.comCode" />
					</td>
					<%-- 承保机构代码 --%>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="input" align="center" style="width: 15%">
						<input class='input' type='hidden' name='registNo' value="${prpLregist.registNo}">
						${prpLregist.registNo}
					</td>
					<td class="input" align="center" style="width: 15%">${prpLregist.policyNo}</td>
					<td class="input" align="center" style="width: 10%">${prpLregist.riskCode}</td>
					<td class="input" align="center" style="width: 15%">${prpLregist.insuredName}</td>
					<td class="input" align="center" style="width: 15%">
						<rc:rcDate name="damageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.damageStartDate}" />
					</td>
					<td class="input" align="center" style="width: 15%">
						<rc:rcDate name="reportDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.reportDate}" />
					</td>
					<td class="input" align="center" style="width: 15%">${prpLregist.comCode}</td>
				</tr>
			</tbody>
		</table>
		<c:if test="${not empty claimStatusList}">
			<table class="common" style="width: 100%">
				<tr>
					<td class="subformtitle">
						<s:text name="general.warning" />
						<input class='input' type='hidden' name='permitFlag' value="NO">
					</td>
					<%--警告信息 --%>
				</tr>
				<c:forEach items="${requestScope.claimStatusList}" var="claimStatus" varStatus="stat">
					<tr>
						<td class="input" align="center" style="width: 10%">
							<font color='red'><c:out value="${stat.count}" />-${claimStatus}</font>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty claimStatusList}">
			<input class='input' type='hidden' name='permitFlag' value="YES">
		</c:if>
		<table id="GeneralClaim" class="common" style="width: 100%" cellspacing="1" cellpadding="11">
			<thead>
				<tr>
					<td class="subformtitle" colspan="11">
						<s:text name="general.scheduling" />
					</td>
					<%-- 任務调度 --%>
				</tr>
				<tr>
					<td class="centertitle" style="width: 3%;">
						<s:text name="db.prpCname.serialNo" />
					</td>
					<td class="centertitle" style="width: 5%;">
						<s:text name="db.prpCitemKind.riskCode" />
					</td>
					<td class="centertitle" style="width: 7%;">
						原處理單位代碼
					</td>
					<%-- 处理机构名称 --%>
					<td class="centertitle" style="width: 12%;">
						原處理單位名稱
					</td>
					<%-- 操作员代码 --%>
					<td class="centertitle" style="width: 5%;" >
						轉入單位代碼
					</td>
					<td class="centertitle" style="width: 12%;">
						轉入單位名稱
					</td>
					<td class="centertitle" style="width: 16%;" >
						理賠人員
					</td>
					<td class="centertitle" style="width: 7%;">
						<s:text name="db.prpCmain.operatorCode" /><%-- 操作员代码 --%>
					</td>
					<td class="centertitle" style="width: 11%;">
						<s:text name="guarantee.operateName" /><%-- 操作员名称 --%>
					</td>
					<td class="centertitle" style="width: 14%;">
						更改時間
					</td>
					<td class="centertitle" style="width: 8%;">
						更改原因
					</td>
				</tr>
			</thead>
			<tbody>
				<c:set var="countIndex" value="1" scope="page" />
				<c:forEach items="${requestScope.prpLgeneralClaimTaskLogList}" var="prpLgeneralClaimTaskLog" varStatus="stat">
					<tr name="trPrpLgeneralClaimTaskLog">
						<td class="input" align="center" style="width: 3%">
							${countIndex}
							<c:set var="countIndex" value="${countIndex +1}" scope="page" />
						</td>
						<td class="input" align="center" style="width: 5%">
							<c:out value='${prpLgeneralClaimTaskLog.riskCode}' />
						</td>
						<td class="input" align="center" style="width: 7%">
							${prpLgeneralClaimTaskLog.comCode}
						</td>
						<td class="input" align="center" style="width: 12%">
							<%-- 选择处理机构 --%>
							${prpLgeneralClaimTaskLog.comName}
						</td>
						<td class="input" align="center" style="width: 5%">
							${prpLgeneralClaimTaskLog.receiveComCode}
						</td>
						<td class="input" align="center" style="width: 12%">
							${prpLgeneralClaimTaskLog.receiveComName}
						</td>
						<td class="input" align="center" style="width: 16%">
							${prpLgeneralClaimTaskLog.receiveOperatorCode}&nbsp;&nbsp;&nbsp;&nbsp;${prpLgeneralClaimTaskLog.receiveOperatorName}
							
						</td>
						
						<td class="input" align="center" style="width: 7%">
							${prpLgeneralClaimTaskLog.giveOperatorCode}
						</td>
						<td class="input" align="center" style="width: 11%">
							${prpLgeneralClaimTaskLog.giveOperatorName}
						</td>
						<td class="input" align="center" style="width: 14%">
							<rc:rcDate name="giveTime"  value="${prpLgeneralClaimTaskLog.giveTime}" format="yyyy-MM-dd HH:mm:ss" wdatePicker="false" class="readonly" readonly="readonly"/>
						</td>
						<td class="input" align="center" style="width: 8%">
							<input type="hidden" name="extendString1" value="${prpLgeneralClaimTaskLog.extendString1 }">
							<input type="button" value="更改原因" class="button" onclick="showExtendString(this)">
						</td>
					</tr>
				</c:forEach>
				<c:forEach items="${requestScope.swflogDtoList}" var="swflogDto" varStatus="stat">
					<tr name="trPrpLgeneralClaimTaskLog">
						<td class="input" align="center" style="width: 3%">
							<input class='input' type='hidden' name='flowId' value="<c:out value='${swflogDto.id.flowID}'/>">
							<input class='input' type='hidden' name='logNo' value="<c:out value='${swflogDto.id.logNo}'/>">
							<input class='input' type='hidden' name='nodeType' value="<c:out value='${swflogDto.nodeType}'/>">
							<input class='input' type='hidden' name='nodeName' value="<c:out value='${swflogDto.nodeName}'/>">
							<input class='input' type='hidden' name='prpLgeneralClaimTaskLogCurrentNode' value="<c:out value='${swflogDto.nodeName}'/>">
							<input class='input' type='hidden' name='prpLgeneralClaimTaskLogCurrentNodeType' value="<c:out value='${swflogDto.nodeType}'/>">
							${countIndex}
							<c:set var="countIndex" value="${countIndex +1}" scope="page" />
						</td>
						<td class="input" align="center" style="width: 5%">

							<input class='readonly' readonly type="text" style="text-align:center" name='riskCode' value="${swflogDto.riskCode}">
						</td>
						<td class="input" align="center" style="width: 7%">
							<input class='readonly' readonly type="text" name='prpLgeneralClaimTaskLogComCode' style="text-align:center" value="${swflogDto.comCode}" >
						</td>
						<td class="input" align="center" style="width: 12%">
							<input class='readonly' readonly type="text" name='prpLgeneralClaimTaskLogComName' style="text-align:center" value="${swflogDto.comName}" >
						</td>
						<td class="input" align="center" style="width: 5%">
							<%-- 选择处理机构 --%>
							<input class='codecode' type="text" name="prpLgeneralClaimTaskLogReceiveComCode" value="" ondblclick="code_CodeSelect(this,'DeptCode','0,1','Y','Y');" onchange="code_CodeSelect(this,'DeptCode','0,1','Y','Y');" style="width: 70%;">
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="input" align="center" style="width: 12%">
							<%-- 选择处理机构 --%>
							<input class='codecode' type="text" name='prpLgeneralClaimTaskLogReceiveComName' value="" ondblclick="code_CodeSelect(this,'DeptCode','-1,0','Y','N');" onchange="code_CodeSelect(this,'DeptCode','-1,0','Y','N');" style="width: 90%;">
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="input" align="center" style="width: 16%">
							<input class='codecode' type="text" name='prpLgeneralClaimTaskLogReceiveOperatorCode' value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y','ALL');" onchange="code_CodeSelect(this, 'handerCode','0,1','Y','Y','ALL');" style="width: 30%;">
							<input class='codecode' type="text" name='prpLgeneralClaimTaskLogReceiveOperatorName' value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N','ALL');" onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N','ALL');" style="width: 60%;">
						</td>
						<td class="input" align="center" style="width: 7%">
							${user.userCode }
							<input  type="hidden" name="prpLgeneralClaimTaskLogGiveOperatorCode" value="${user.userCode }" >
						</td>
						<td class="input" align="center" style="width: 11%">
							${user.userName }
							<input  type="hidden" name='prpLgeneralClaimTaskLogGiveOperatorName' value="${user.userName }" >
						</td>
						<td class="input" align="center" style="width: 14%">
							<rc:rcDate name="prpLgeneralClaimTaskLogGiveTime"  defaultValue="0" format="yyyy-MM-dd HH:mm:ss" wdatePicker="false" class="readonly" readonly="readonly"/>
						</td>
						<td class="input" align="center" style="width: 8%">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table class="common" style="width: 100%" cellspacing="1" cellpadding="5">
			<tr >
				<td class="input" style="width: 10%;" align="right" >更改原因：</td>
				<td class="input" style="width: 90%;">
					<textarea rows="10" cols="30" name="prpLgeneralClaimTaskLogExtendString1"></textarea>
				</td>
			</tr>
		</table>
		<div style="width: 100%; height: 0px; background-color: #ffffff; overflow: no;">
			<%@include file="/pages/generalClaim/give/GeneralClaimGiveSave.jsp"%>
		</div>
		<div id="divExtendString" style="background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 600px;" align="left">
			<ul>
				<textarea rows="10" style="width: 100%;" readonly="readonly" name="textExtendString1"></textarea>
			</ul>
			<ul>
				<span align="center" style="padding-left: 200px;">
					<input type="button" value="關閉" class="button" onclick="closeExtendString(this);">
				</span>
			</ul>
		</div>
	</form>
</body>
</html>