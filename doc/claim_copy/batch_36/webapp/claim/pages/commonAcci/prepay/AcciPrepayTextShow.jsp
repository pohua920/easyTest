<!--
****************************************************************************
* DESC       ：显示预赔文字页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<table class="common" align="center" width="100%">
	<tr>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
			<s:text name="prepay.compensationReport" />：
			<%--预赔报告--%>
			<br>
			<table class="common" align="center" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<iframe class="Composition" ID="Composition" MARGINHEIGHT="1" MARGINWIDTH="1" width="0" height="0"> </iframe>
							<input type="hidden" stype="width:300px" name="prpLptextContextInnerHTML" value='${prpLptext.context}'>
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLptextContext" readonly="true" class="readonly"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
