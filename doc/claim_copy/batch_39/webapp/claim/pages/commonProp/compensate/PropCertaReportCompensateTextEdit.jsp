<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" cellpadding="5" cellspacing="1" id="Lltext" style="display:">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LlTextImg" onclick="showPage(this,LlText2)">
			<s:text name="compensate.damageDescription" />
			<%-- 定损说明 --%>
			<br>
			<table class="common" align="center" id="LlText2" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML"><bean:write name="prpLltextDto" property="context" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<script language="javascript">
	if (fm.prpLcompensateFinallyFlag[0].checked) {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
</script>