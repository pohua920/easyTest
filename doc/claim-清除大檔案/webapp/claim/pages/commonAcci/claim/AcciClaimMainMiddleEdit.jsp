
<tr>
	<td class="title">
		<s:text name="db.prpLclaim.lossName" />:
	</td>
	<%--受损标的--%>
	<td class="input" colspan='3'>
		<input type=text name="prpLclaimLossName" title="受損標的" class="input" value="<bean:write name='prpLclaimDto' property='lossName' filter='true' />">
	</td>
</tr>
