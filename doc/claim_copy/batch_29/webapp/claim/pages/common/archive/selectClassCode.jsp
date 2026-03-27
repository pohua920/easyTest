<%--
****************************************************************************
* DESC       ：操作成功提示页面
* AUTHOR     ：liuwei
* CREATEDATE ：2011-01-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.utiall.schema.*"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 滚动条样式定义 -->
<html>
<head>
<script src="/claim/common/js/date/WdatePicker.js"></script>
<script src="/claim/common/js/selectClassCode.js"></script>
</head>
<%
	List<PrpDclassSchema> list = new ArrayList<PrpDclassSchema>();
	PrpDclassSchema prpDclassSchema = new PrpDclassSchema();
	if (request.getAttribute("list") != null) {
		list = (List) request.getAttribute("list");
	}
%>
<body>
	<form name="fm">
		&nbsp;<b><s:text name="archive.pleaseSelectRisk" /> </b>
		<!-- 请选择险类 -->
		<br /> <br />
		<table align="center" class=common border="0">
			<tr align="center">
				<td colspan='2'>
					<input type='button' class='button' value='<s:text name="button.determine.value"/>' onClick="selectChecked();" />
				</td>
				<!-- 确定 -->
				<td colspan='2'>
					<input type='button' class='button' value='<s:text name="button.close.value"/>' onClick="closeWin();" />
				</td>
				<!-- 关闭 -->
			</tr>
		</table>
		<br /> <br />
		<table align="center" border="1" width="100%">
			<tr align="center">
				<th>
					<input type="checkbox" name="classCodeAll" width='15%' onclick="checkAll();">
				</th>
				<th width="20%">
					<s:text name="db.prpDclass.classCode" />
				</th>
				<!-- 险类代码 -->
				<th colspan='2' width="65%">
					<s:text name="db.prpDclass.className" />
				</th>
				<!-- 险类名称 -->
			</tr>
			<%
				for (int i = 0; i < list.size(); i++) {
					prpDclassSchema = list.get(i);
			%>
			<tr>
				<td align="center">
					<input type="checkbox" name="classCode" value="<%=prpDclassSchema.getClassCode()%>" onclick="checkPart(this);">
				</td>
				<td align="center"><%=prpDclassSchema.getClassCode()%></td>
				<td colspan='2' align="center"><%=prpDclassSchema.getClassName()%></td>
			</tr>
			<%
				}
			%>
		</table>
		<br /> <br />
		<table align="center" class=common border="0">
			<tr align="center">
				<td colspan='2'>
					<input type='button' class='button' value='<s:text name="button.determine.value"/>' onClick="selectChecked();" />
				</td>
				<!-- 确定 -->
				<td colspan='2'>
					<input type='button' class='button' value='<s:text name="button.close.value"/> ' onClick="closeWin();" />
				</td>
				<!-- 关闭 -->
			</tr>
		</table>
	</form>
</body>
</html>
