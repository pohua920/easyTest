<%--
****************************************************************************
* DESC       ：定损结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLverifyLossDto"%>
<html:html locale="true">
<script language="javascript">
<!--
	
<%--案件状态标志处理--%>
	function submitForm() {
		if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0)
				|| (fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0)
				|| (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0)
				|| (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8)
				|| (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2))
					|| "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("車險必須輸入備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢！");
			return false;
		}
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {
			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.caseFlag.value = ref;
		fm.pageNo.value = "1";
		fm.searchFlag.value = "true";
		fm.submit();//提交
	}
//-->
</script>
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<%@include file="/common/meta_js.jsp"%>
<html:base />
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/certainLossQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certainLoss.queryCertainLoss" />
				</td>
			</tr>
			<%--查询定损信息--%>
			<tr>
				<td class='title'>
					<s:text name="regist.prpLregist.registNo" />
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />:
				</td>
				<%--保单号 --%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<%
				String nodeType = request.getParameter("nodeType");
			%>
			<tr>
				<%
					if (!"certa".equals(nodeType)) {
				%>
				<td class='title'></td>
				<td class='input'>
					<input name="LicenseNoSign" type="hidden">
					<input name="LicenseNo" class="query" type="hidden">
				</td>
				<%
					} else {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<%
					}
				%>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />:
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />:
				</td>
				<%--案件状态--%>
				<td class='input' align="left">
					<input type="hidden" name="caseFlag" value="">
					<!--<input type="checkbox" name="status" value="1">未处理-->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号或者保单号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan=4>
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
					<input type="hidden" name="editType2" value="SHOW">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=7 class="formtitle">
					<s:text name="certainLoss.queryCertainLoss" />
				</td>
			</tr>
			<%--查询定损信息--%>
			<tr>
				<td class="centertitle">
					<s:text name="regist.prpLregist.serialNo" />
				</td>
				<%--序号--%>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />:
				</td>
				<%--案件状态--%>
				<td class="centertitle">
					<s:text name="prpLregist.registNo" />：
				</td>
				<%--报案号--%>
				<td class="centertitle">
					<s:text name="certainLoss.lossMarkName" />
				</td>
				<%--损失标的名称--%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="certainLoss.feeCode" />
				</td>
				<%--定损人代码--%>
				<td class="centertitle">
					<s:text name="certainLoss.feeDateGeneration" />
				</td>
				<%--定损/代定损日期--%>
			</tr>
			<%
				int index = 0;
			%>
			<c:if test="${prpLverifyLossDto.verifyLossList!=null}">
				<c:forEach var="prpLverifyLoss1" items="${prpLverifyLoss.verifyLossList}" varStatus="status">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center"><%=index + 1%>
						</td>
						<td align="center">
							<c:if test="${prpLverifyLoss1.status==0}">
								<s:text name="common.status.untreated" />
								<%--未处理--%>
							</c:if>
							<c:if test="${prpLverifyLoss1.status==2}">
								<s:text name="common.status.intreating" />
								<%--正处理--%>
							</c:if>
							<c:if test="${prpLverifyLoss1.status==3}">
								<s:text name="common.status.backDeal" />
								<%--回退並处理--%>
							</c:if>
							<c:if test="${prpLverifyLoss1.status==4}">
								<s:text name="common.status.submited" />
								<%--已提交--%>
							</c:if>
							<c:if test="${prpLverifyLoss1.status==5}">
								<s:text name="common.status.revoked" />
								<%--已撤消--%>
							</c:if>
						</td>
						<td align="center">
							<a
								href="/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=${prpLverifyLoss1.registNo}&editType=${prpLverifyLoss.editType}&riskCode=${prpLverifyLoss1.riskCode}&lossItemCode=${prpLverifyLoss1.lossItemCode}&lossItemName=${prpLverifyLoss1.lossItemName}">${prpLverifyLoss1.registNo}</a>
						</td>
						<td align="center">
							<c:if test="${prpLverifyLoss1.lossItemCode!=0}">
								<c:if test="${prpLverifyLoss1.lossItemCode!=-1}">
						        ${prpLverifyLoss1.lossItemCode}&nbsp;
						        </c:if>
							</c:if>
							${prpLverifyLoss1.lossItemName}
						</td>
						<td align="center">
							<!--reason:强三查询-->
							<c:forEach var="currelatepolicyNo" items="${prpLverifyLoss1.relatepolicyNo}" varStatus="status">
		        				${currelatepolicyNo}<br>
							</c:forEach>
						</td>
						<td align="center">${prpLverifyLoss1.handlerCode}</td>
						<td align="center">${prpLverifyLoss1.defLossDate}</td>
					</tr>
					<%
						index++;
					%>
				</c:forEach>
			</c:if>
			<tr class="listtail">
				<td colspan="7">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							${prpLverifyLoss.[turnPage]}
							<%
								PrpLverifyLossDto prpLverifyLossDto = (PrpLverifyLossDto) request.getAttribute("prpLverifyLossDto");
									int curPage = prpLverifyLossDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
	</form>
	<%@include file="/common/meta_js.jsp"%>
</body>
</html:html>