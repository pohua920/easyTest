<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="ins.framework.common.*"%>
<html>
<script language='javascript'>
	function exportToExcel(exportType){
		var editType = "";
		if(fm.flag.value=="compensate"){
			editType = "exportToExcel";//理算
		}else{
			editType = "undwrtExportToExcel";//核赔
		}
		var oldAction = fm.action;
		fm.action="/claim/wfLogQuery.do?editType="+editType+"&nodeType=urgentCase&exportType="+exportType;
	    fm.submit();
	    fm.action=oldAction;
	}
</script>
<head>
 	<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	<%@include file="/common/meta_js.jsp"%>
	<%@ include file="/common/taglibs.jsp"%>
	<title>緊急案件清單</title>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="fm" name = "fm"  method="post">
		<input type="hidden" name="flag" value="<c:out value='${flag}'/>">
		<table class="common" cellpadding="3" cellspacing="2">
	      <THEAD>
				<TR class="tableHead">
					<TD width=100% class="centertitle" colspan="3">
						<s:text name="title.compensate.emergencyCaseListing" />
					</TD>
				</TR>
				<%--紧急案件清单 --%>
	      </thead>
			<tr align='right'>
				<TD class="right" align="right" width=60%>
					<s:text name="compensate.fileExportWay" />
					：</font>
				</TD>
				<%--文件导出方式 --%>
		        <TD class="right" width=20% align="right">
					<select name="exportType">
						<option value="1" <c:if test="${exportType==1}">selected</c:if>>
							<s:text name="compensate.redWarnExport" />
						</option>
						<%--红色预警导出 --%>
						<option value="2" <c:if test="${exportType==2}">selected</c:if>>
							<s:text name="compensate.yellowWarnExport" />
						</option>
						<%--黄色预警导出 --%>
						<option value="3" <c:if test="${exportType==3}">selected</c:if>>
							<s:text name="compensate.allExport" />
						</option>
						<%--全部导出 --%>
					</select>
			    </TD>
			    <TD class="right" width=20%>
			    	<input type=button id="button" name="urgentCaseButton" class='button' value="導出爲Excel" onClick="exportToExcel(fm.exportType.value);">
			    </TD>
			</tr>
			<tr>
				<TD class="left" align="center" colspan="3">
					<font color="red"><s:text name="compensate.instructions1" /><br> <s:text name="compensate.instructions2" /><br> <s:text name="compensate.instructions3" /><br> <s:text
							name="compensate.instructions4" /><br> <s:text name="compensate.instructions5" /><br> <s:text name="compensate.instructions6" /><br> <s:text name="compensate.instructions7" />&#9;<s:text
							name="compensate.instructions8" />&#9;<s:text name="compensate.instructions9" /></font>
				</TD>
			</tr>
		</table>
		<TABLE  cellpadding="3" cellspacing="1"  class="common" id=urgentCaseTable >
		  <THEAD>
		    <TR class="tableHead">
					<TD width=3% class="centertitle">
						<s:text name="db.prpDrate.serialNo" />
					</TD>
					<%--序号 --%>
					<TD width=12% class="centertitle">
						<s:text name="prompt.queRegist.RegistNo" />
					</TD>
					<%--报案号 --%>
					<TD width=15% class="centertitle">
						<s:text name="prompt.queRegist.PolicyNo" />
					</TD>
					<%--保单号 --%>
					<TD width=17% class="centertitle">
						<s:text name="db.prpLregist.insuredName" />
					</TD>
					<%--被保险人 --%>
					<TD width=9% class="centertitle">
						<s:text name="db.prpLlawsuit.operatorCode" />
					</TD>
					<%--操作员 --%>
					<TD width=13% class="centertitle">
						<s:text name="compensate.insuranceComCode" />
					</TD>
					<%--承保机构代码 --%>
					<TD width=10% class="centertitle">
						<s:text name="claim.intoTime" />
					</TD>
					<%--流入时间 --%>
					<TD width=10% class="centertitle">
						<s:text name="db.prpLclaimStatus.status" />
					</TD>
					<%--案件状态 --%>
					<TD width=18% class="centertitle">
						<s:text name="compensate.waitTime" />
					</TD>
					<%--等待时间（天） --%>
			</TR>
		  </thead>
		  <tbody>
		  	<c:forEach var="swfLog" items="${requestScope.swfLoglist}" varStatus="status">
			   <TR class=content bgcolor='${swfLog.flag}'>
						<td align="center">
							<c:out value="${status.count}" />
						</td>
						<TD align="center">
							<c:out value="${swfLog.registNo}" />
						</TD>
						<TD align="center">
							<c:out value="${swfLog.policyNo}" />
						</TD>
						<TD align="center">
							<c:out value="${swfLog.insuredName}" />
						</TD>
						<TD align="center">
							<c:out value="${swfLog.handlerName}" />
						</TD>
						<TD align="center">
							<c:out value="${swfLog.comCode}" />
			      </TD>
						<TD align="center">
							<!-- <c:out value="${swfLog.flowInTime}"/> -->
			      <rc:rcDate name="flowInTime"  class="readonly" readonly="true" wdatePicker="false"   style="width:150px" value="${swfLog.flowInTime}" format="yyyy-MM-dd HH:mm:ss" />
			      </TD>
						<TD align="center">
							<c:out value="${swfLog.nodeStatus}" />
						</TD>
						<TD align="center">
							<c:out value="${swfLog.timeLimit}" />
						</TD>
			    </TR>
		      </c:forEach>
		  </tbody>
		  <tfoot>
		      <tr >
		         <td colspan="20" align="center">
						<%
							Page pageRecode = (Page) request.getAttribute("page");
						%>
		            <input type="hidden" name="rowsPerPage" value="<%=pageRecode.getPageSize() %>">
		            <input type="hidden" name="pageNo" value="<%=pageRecode.getCurrentPageNo() %>">
		            <app:navigate objectName="page"/>
		            <c:choose>
		               <c:when test="${requestScope.flag=='compensate'}">
		                   <input type="hidden" name="editType" value="urgentCase">
		               </c:when>
		               <c:otherwise>
		                   <input type="hidden" name="editType" value="undwrtUrgentCase">
		               </c:otherwise>
		            </c:choose>
		         </td>
		       </tr>
		  </tfoot>
		</TABLE>
	</form>
</body>
</html>