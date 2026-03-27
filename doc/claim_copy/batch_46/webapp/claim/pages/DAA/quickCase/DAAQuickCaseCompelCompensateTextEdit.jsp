<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="CompelRegistTextImg" onclick="showPage(this,CompelRegistText)">
			<s:text name="title.quickCase.makeAdjustmentReport" />
			<!-- 交强险理算报告 -->
			<br>
			<table class="common" align="center" id="CompelRegistText" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;">
							<textarea style="wrap: hard" rows="15" cols="80" name="compelPrpLctextContextInnerHTML"><bean:write name="compelPrpLctextDto" property="context" /></textarea>
							<br>
							<%
								//如果是从双核系统来调用,则不能显示生成理算报告按钮. 2005-08-11
								if (!"noDisplay".equals(request.getParameter("claimFlag"))) {
							%>
							<input type="button" class=bigbutton name="buttonGenerateCtext" value="產生理算报告" onclick="GenerateCtextFlag('0');compelGenerateCtext();">
							<%
								}
							%>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>