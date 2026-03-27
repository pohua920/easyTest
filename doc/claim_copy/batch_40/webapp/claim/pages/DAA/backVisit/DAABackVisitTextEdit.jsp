<%--
****************************************************************************
* DESC       ：显示客户意见
* AUTHOR     ：理赔项目组
* CREATEDATE ：2005-03-24
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="BackVisitTextImg" onclick="showPage(this,BackVisitText)">
			<s:text name="backVisit.customerOpinion" />
			<%-- 客户意见 --%>
			<br>
			<table class="common" cellpadding="5" cellspacing="1" align="center" id="BackVisitText" style="display:">
				<tbody>
					<tr>
						<td>
							<textarea name="prpLbackVisitTextContent" class="common" style="BACKGROUND-COLOR: #ffffff" wrap="hard" rows="15" cols="80"><bean:write name="prpLbackVisitTextDto" property="content" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
