<%--
****************************************************************************
* DESC       ：实体资料调阅超时查询结果页面
* AUTHOR     ： liuwei
* CREATEDATE ： 2011-01-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLDocArchiveDto"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="title.archive.entityDataReadOuttimeQueryPage" /></title>
<!-- 实体资料调阅超时查询结果页 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%-- 公用函数 --%>
<script src="/claim/common/js/Common.js"></script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/archiveQuery.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="title.archive.entityDataReadOuttimeQueryPage" />
				</td>
				<!-- 实体资料调阅超时查询 -->
			</tr>
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<select class="tag" name="claimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="claimNo" class="query">
				</td>
				<td class="title">
					<s:text name="db.prpLregist.policyNo" />：
				</td>
				<td class="input">
					<select class="tag" name="policyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="policyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpCmain.insuredName" />：
				</td>
				<!-- 被保险人名称 -->
				<td class="input">
					<select class="tag" name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="insuredName" class="query">
				</td>
				<td class="title">
					<s:text name="archive.applicantName" />：
				</td>
				<!-- 申请人名称 -->
				<td class="input">
					<select class="tag" name="applicantNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type="text" name="applicantName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="archive.readData" />：
				</td>
				<!-- 调阅日期 -->
				<td class="input">
					<select class="tag" name="startReviewDateSign">
						<option value="=">=&nbsp;</option>
					</select>
					<input type="text" name="startReviewDate" class="query">
					<img style='' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.startReviewDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class="title">
					<s:text name="archive.expectedReturnDate" />
				</td>
				<!-- 预计归还日期： -->
				<td class="input">
					<select class="tag" name="estimateReturnDateSign">
						<option value="=">=&nbsp;</option>
					</select>
					<input type="text" name="estimateReturnDate" class="query">
					<img style='' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.estimateReturnDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<!-- "="符号，必须精确查询。 -->
					<s:text name="prompt.schedule.query2" />
					<!-- "=*"符号，前匹配後模糊的查询。 -->
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="center">
					<input type="submit" id="button" class='button' value="<s:text name='button.query.value' />">
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td></td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="6">
					<b><s:text name="archive.entityDataReadOuttimeQueryResult" /></b>
				</td>
				<!-- 实体资料调阅超时查询结果 -->
			</tr>
			<tr class="listtitle">
				<td>
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td>
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td>
					<s:text name="db.prpCmain.insuredName" />
				</td>
				<!-- 被保险人名称 -->
				<td>
					<s:text name="archive.applicantName" />
				</td>
				<!-- 申请人名称 -->
				<td>
					<s:text name="archive.readData" />
				</td>
				<!-- 调阅日期 -->
				<td>
					<s:text name="archive.expectedReturnDateResult" />
				</td>
				<!-- 预计归还日期 -->
			</tr>
			<%--<logic:notEmpty  name="prpLDocArchiveDto" property="archiveList">
                --%>
			<c:if test="${prpLDocArchiveDto.archiveList != null}">
				<%--<logic:iterate id="archiveList1" name="prpLDocArchiveDto" property="archiveList">
                    --%>
				<c:forEach var="archiveList1" items="${prpLDocArchiveDto.archiveList}">
					<logic:equal value="red" name="archiveList1" property="flagColor">
						<tr class="common" style="color: red;">
					</logic:equal>
					<logic:equal value="" name="archiveList1" property="flagColor">
						<tr class="common">
					</logic:equal>
					<td>${archiveList1.claimNo}</td>
					<td>${archiveList1.policyNo}</td>
					<td>${archiveList1.insuredName}</td>
					<td>${archiveList1.applicantName}</td>
					<td>${archiveList1.startReviewDate}</td>
					<td>${archiveList1.estimateReturnDate}</td>
					</tr>
				</c:forEach>
				<%--</logic:iterate>
                --%>
			</c:if>
			<%--</logic:notEmpty>
            --%>
			<tr>
				<%--
                <td colspan="6">
                    <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <bean:define id="pageview" name="prpLDocArchiveDto" property="turnPageDto"/>
                            <%
                                PrpLDocArchiveDto prpDocArchiveDto = (PrpLDocArchiveDto) request.getAttribute("prpLDocArchiveDto");
                                int curPage = prpDocArchiveDto.getTurnPageDto().getPageNo();
                             %>
                             <%@include file="/common/pub/TurnOverPage.jsp" %>
                        </tr>
                    </table>
                </td>
            --%>
			</tr>
		</table>
		<input type="hidden" name="editType" value="overtime">
	</form>
</body>
</html>