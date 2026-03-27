<br/>
<TABLE id="buttonTable" cellpadding="0" cellspacing="0" align='center'>
  <TR>
	   <TD align='center'>
	   <c:if test="${actionType=='Guide'||actionType=='CaseTransfer'||actionType=='TaskTransfer'}">
	     	<input class='button' type='button' name='button' value='<s:text name="undwrt.Submit"/>' onclick="return giveInsert();"><%--提交 --%>
	   </c:if>
	   <c:if test="${actionType=='prepareReceiveInsert'}">
	   		<input class='button' type='button' name='button' value='<s:text name="general.receive"/>' onclick="return receiveInsert();"><%--接收 --%>
	   </c:if>
	   <c:if test="${actionType=='prepareRegainInsert'}">
	   		<input class='button' type='button' name='button' value='<s:text name="general.getBack"/>' onclick="return receiveInsert();"><%--收回 --%>
	   </c:if>
	   </TD>
	   <TD align='center'>
	     <input class='button' type='button' name='button' value='<s:text name="prompt.cancel"/>' onclick="history.back(-1);"><%--取消 --%>
	   </TD>
  </TR>
</TABLE>