<!--
****************************************************************************
* DESC       ：报案查询条件结果页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<script src="${ctx}/common/js/showpage.js">
	
</script>
<!-- 公用函数 -->
<script src="${ctx}/common/js/Common.js"></script>
<%@include file="/common/meta_js.jsp"%>
<script language="javascript">
<!--案件状态标志处理-->
	function submitForm(field) {
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {
			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.searchFlag.value = "true";
		//fm.pageNo.value="1";//查询後页面设为1
		fm.caseFlag.value = ref;
		// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
		field.disabled = true;
		fm.submit();//提交
	}
//-->
</script>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
</head>
<body onload="initPage();">
	<form name="fm" action="${ctx}/wfLogQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="specialCase.SpecialClaimsInformation" />
				</td>
			</tr>
			<%-- 查询特殊赔案信息  --%>
			</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo" />
					:
					<%-- 报案号 --%>
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					:
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />
					:
					<%-- 案件状态 --%>
				</td>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag" value="">
					<input type="checkbox" name="status" value="1">
					<s:text name="common.status.untreated" />
					<%-- 未处理 --%>
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%-- 正处理 --%>
					<input type="checkbox" name="status" value="3">
					<s:text name="specialCase.ReturningCompensation" />
					<%-- 核赔退回 --%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%-- 已提交 --%>
					<input type="checkbox" name="status" value="5">
					<s:text name="common.status.revoked" />
					<%-- 已撤消 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%-- "="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--  "=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="3">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm(this);">
					<input type="hidden" name="pageFlag">
					<input type="hidden" name="nodeType" value="speci">
					<input type="hidden" name="editType" value="specialQuery">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=6 class="formtitle">
					<s:text name="specialCase.SpecialClaimsQuery" />
					<%--  特殊赔案查询结果信息--%>
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />
					<%-- 案件状态 --%>
				</td>
				<td class="centertitle">
					<s:text name="sendUndwrt.BusinessNumber" />
					<!--业务号-->
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.operatorCode" />
					<!--操作人员-->
				</td>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.operatedate" />
					<%-- 操作时间 --%>
				</td>
				<td class="centertitle">
					<s:text name="check.claimType" />
					<%-- 赔案类型 --%>
				</td>
			</tr>
			<s:set var="swfLog_count" value="0" scope="page" />
			<c:if test="${swfLog.swfLogList!=null}">
				<s:set var="swfLog_count" value="#attr.swfLog.swfLogList.size()" scope="page" />
				<c:forEach var="swfLogTemp" items="${swfLog.swfLogList}" varStatus="swfLogTemp_status">
					<c:if test="${swfLogTemp_status.index%2==0}">
						<tr class="listodd">
					</c:if>
					<c:if test="${swfLogTemp_status.index%2!=0}">
						<tr class="listeven">
					</c:if>
					<td align="center">
						<c:if test="${swfLogTemp.nodeStatus==1}">
							<s:text name="common.status.untreated" />
							<%-- 未处理 --%>
						</c:if>
						<c:if test="${swfLogTemp.nodeStatus==2}">
							<s:text name="common.status.intreating" />
							<%-- 正处理 --%>
						</c:if>
						<c:if test="${swfLogTemp.nodeStatus==3}">
							<s:text name="specialCase.ReturningCompensation" />
							<%-- 核赔退回 --%>
						</c:if>
						<c:if test="${swfLogTemp.nodeStatus==4}">
							<s:text name="common.status.submited" />
							<%-- 已提交 --%>
						</c:if>
						<c:if test="${swfLogTemp.nodeStatus==5}">
							<s:text name="common.status.revoked" />
							<%-- 已撤消 --%>
						</c:if>
					</td>
					<c:if test="${swfLogTemp.typeFlag=='3' }">
						<td align="center">
							<a
								href="${ctx}/compensateFinishQueryList.do?policyNo=${swfLogTemp.policyNo}&prpLcompensateCompensateNo=${swfLogTemp.keyOut}}&editType=SHOW&riskCode=${swfLogTemp.riskCode}&swfLogFlowID=${swfLogTemp.id.flowID}&swfLogLogNo=${swfLogTemp.id.logNo}">${swfLogTemp.keyOut}</a>
						</td>
					</c:if>
					<c:if test="${swfLogTemp.typeFlag=='4' }">
						<td align="center">
							<a
								href="${ctx}/compensateFinishQueryList.do?policyNo=${swfLogTemp.policyNo}&prpLcompensateCompensateNo=${swfLogTemp.keyOut}&editType=SHOW&riskCode=${swfLogTemp.riskCode}&swfLogFlowID=${swfLogTemp.id.flowID}&swfLogLogNo=${swfLogTemp.id.logNo}">${swfLogTemp.keyOut}</a>
						</td>
					</c:if>
					<c:if test="${swfLogTemp.typeFlag=='5' }">
						<td align="center">
							<a
								href="${ctx}/prepayFinishQueryList.do?policyNo=${swfLogTemp.policyNo}&prpLprepayPrepayNo=${swfLogTemp.keyOut}&editType=SHOW&riskCode=${swfLogTemp.riskCode}&swfLogFlowID=${swfLogTemp.id.flowID}&swfLogLogNo=${swfLogTemp.id.logNo}">${swfLogTemp.keyOut}</a>
						</td>
					</c:if>
					<c:if test="${swfLogTemp.typeFlag=='7' }">
						<td align="center">
							<a
								href="${ctx}/prepayFinishQueryList.do?policyNo=${swfLogTemp.policyNo}&prpLprepayPrepayNo=${swfLogTemp.keyOut}&editType=SHOW&caseType=7&riskCode=${swfLogTemp.riskCode}&swfLogFlowID=${swfLogTemp.id.flowID}&swfLogLogNo=${swfLogTemp.id.logNo}">
								${swfLogTemp.keyOut}</a>
						</td>
					</c:if>
					<c:if test="${swfLogTemp.typeFlag=='8' }">
						<td align="center">
							<a
								href="${ctx}/prepayFinishQueryList.do?policyNo=${swfLogTemp.policyNo}&prpLprepayPrepayNo=${swfLogTemp.keyOut}&editType=SHOW&caseType=8&riskCode=${swfLogTemp.riskCode}&swfLogFlowID=${swfLogTemp.id.flowID}&swfLogLogNo=${swfLogTemp.id.logNo}">${swfLogTemp.keyOut}</a>
						</td>
					</c:if>
					<td align="center">${swfLogTemp.policyNo}</td>
					<td align="center">${swfLogTemp.handlerName}</td>
					<td align="center">
						<%-- ${swfLogTemp.handleTime}--%>
						<rc:rcDate name="handleTime" class="readonly" readonly="true" wdatePicker="false" style="width:120px" value="${swfLogTemp.handleTime}" />
					</td>
					<td align="center">${swfLogTemp.typeFlagName}</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr class="listtail">
				<s:text name="certainLoss.totalInquiries" />
				${swfLog_count }
				<s:text name="certainLoss.meetRecord" />
				<%-- 共查询出 --%>
				<%-- 条满足条件的记录 --%>
			</tr>
		</table>
		</table>
		</tr>
		</table>
	</form>
</body>
</html>