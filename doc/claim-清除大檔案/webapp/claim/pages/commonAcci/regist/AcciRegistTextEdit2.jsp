<%--
****************************************************************************
* DESC       ：录入/显示呈报信息 4为呈报信息
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" width="100%" id="ReportFlag" style="display: none">
	<tr class=mline>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText2)">
			<s:text name="commonAcci.regist.submitInfo" />
			<%--呈报信息--%>
			<br>
			<table class="common" align="center" id="RegistText2" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML2">${prpLregistText4Dto.context}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<script language="javascript">
//根据是否呈报的radio按钮，显示和隐藏呈报信息。
if (fm.prplregistReportFlag[0].checked) {
    ReportFlag.style.display = "";
} else {
    ReportFlag.style.display = "none";
}
</script>