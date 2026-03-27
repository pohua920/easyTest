<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" width="100%" id="Lltext" style="display:">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="LlTextImg" onclick="showPage(this,LlText2)">
			<s:text name="commonAcci.compensate.formulaCalculate" />
			<%--计算公式--%>
			<br>
			<table class="common" align="center" id="LlText2" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLctextContextAccientTextInnerHTML">${prpLctextAccidentText.context}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
