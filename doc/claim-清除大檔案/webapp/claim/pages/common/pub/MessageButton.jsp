<%--
//reason: 统一使用ClaimPub.js 里的 openWinSave()方法，便於维护
<input type="image" src="/claim/images/butMessageSave.gif" width="80" height="21" name="messageSave"  value="讨论留言" onclick="openWinSave();return false;">&nbsp;<input type="image" src="/claim/images/btnMessageView.gif" name="messageView" value="查看留言" width="80" height="21" onclick="openWinQuery();return false;">
--%>
<input type="button" name="messageSave" value="<s:text name='button.claimsProcessingRecords.value' />" class="bigbutton"
	onclick="return openWinSave( fm.prpLregistRegistNo.value,fm.prpLregistPolicyNo.value,fm.prpLregistRiskCode.value,'regis','');">
&nbsp;&nbsp;
<%--赔案处理记录--%>
