
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table id="RepairComponentMessage" class="common" cellpadding="0" cellspacing="1">
				<tr>
					<td class="common" style="width: 20%">
						<s:text name="certainLoss.previousMessage" />
					</td>
					<!--历次留言-->
					<td class="common" style="wdith: 80%">
						<textarea name="OldmessageContext" cols="80" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td class="common" style="width: 20%">
						<s:text name="certainLoss.composeMessage" />
					</td>
					<!--撰写留言-->
					<td class="common" style="wdith: 80%">
						<textarea name="messageContext" cols="80" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td class="common" align="center" colspan="2">
						<input name="save" type="button" class="button" value="<s:text name='button.determine.value'/>" onclick="hideSubPage(this,'span_prpLcomponentSumDefLoss')">
						<!-- 确定-->
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
