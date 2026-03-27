<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" cellpadding="5" cellspacing="1" id="Lltext"
	style="display:">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
				name="LlTextImg" onclick="showPage(this,LlText2)">
			<input class=readonly readonly name="tdLltextTitle"
				value="<s:text name='compensate.adjustReport'/>">
			<%-- 理算报告 --%>
			<textarea name='backLltextContent' style="display: none"></textarea>
			<br>
			<table class="common" align="center" id="LlText2"
				style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML">${prpLltext.context}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<s:if test="#recaseFlag == '0' || #editType == 'SHOW'">
	<script language="javascript">
		changePrpLcompensateFinallyFlag();
	</script>
</s:if>
<s:else>
	<script language="javascript">
		changePrpLcompensateFinallyFlag1();
	</script>
</s:else>