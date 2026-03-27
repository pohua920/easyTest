<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1" id="Lltext" style="display:">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="LlTextImg" onclick="showPage(this,LlText2)">
			<s:text name="db.prpLltext.text08" />
			<%-- 结案报告 --%>
			<br>
			<table class="common" align="center" id="LlText2" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML"><c:out value="${requestScope.prpLltext.context}" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<script language="javascript">
	if (fm.prpLcompensateFinallyFlag.value == "1") {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
</script>