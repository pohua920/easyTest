<%--
****************************************************************************
* DESC		：添加单证页面
* AUTHOR	 ：理赔组
* CREATEDATE ： 2014-04-15
* MODIFYLIST ：	Name		Date			Reason/Contents
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%" >
	<!--表示显示多行的-->
	<tr >
		<td class="subformtitle" colspan="4">
			<img style="cursor:hand;" src="/claim/images/butCollapseBlue.gif"
			name="DocImg" onclick="showPage(this,spanDoc)">
			<s:text name="claim.addDocument" /><br><%--添加单证--%>
			<span style="display:none">
			<table class="common" style="display:none" id="Doc_Data" cellspacing="1" cellpadding="0">
				<tbody>
					<tr>
						<td class="input">
							<input name="prpLdocDocCode" class="codecode" style="width:100%" maxlength=3
							ondblclick="code_CodeSelect(this, 'DocCode');"
							onkeyup= "code_CodeSelect(this, 'DocCode');">
						</td>
						<td class="input">
							<input name="prpLdocDocName" class="codename"
							ondblclick="code_CodeSelect(this, 'DocCode','-1','always','none','post');"
							onkeyup= "code_CodeSelect(this, 'DocCode','-1','always','none','post');">
						</td>
						<td class="input">
							<input name="prpLdocDocCount" class="common" style="width:100%">
						</td>
						<td class="input" colspan="2">
							<input name="prpLdocSignInDate" class="common" style="width:100%" description="<s:text name='db.prpLdoc.signInDate'/>">
						</td>
						<td class="input" style='width:4%' colspan="2" align="center">
							<div>
								<input type=button name="buttonDocDelete"  class="smallbutton" onclick="deleteRow(this,'Doc')" value="-" style="cursor: hand">
								<input type="hidden" name="prpLdocFlag">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			</span>
			<span  id="spanDoc" style="display:none" cellspacing="1" cellpadding="0">
			<%-- 多行输入展现域 --%>
			<table class="common" style="width:100%" id="Doc" >
				<thead>
				<tr>
					<td class="centertitle" style="width:20%">
						<s:text name="db.prpLdoc.docCode" /><%--单证代码--%>
					</td>
					<td class="centertitle" style="width:40%" >
						<s:text name="db.prpLdoc.docName" /><%--单证名称--%>
					</td>
					<td class="centertitle" style="width:10%">
						<s:text name="db.prpLdoc.docCount" /><%--单证张数--%>
					</td>
					<td class="centertitle" style="width:20%" colspan="2">
						<s:text name="db.prpLdoc.signInDate" /><%--签收日期--%>
					</td>
					<td class="title" style="width:4%" >&nbsp;</td>
				</tr>
				</thead>
				<tfoot>
					<tr>
						<td class="title" colspan=5 style="width:96%"><s:text name="prompt.certify.addRemove" /></td><%--(按"+"号键增加信息，按"-"号键删除信息)--%>
						<td class="title" align="right" style="width:4%">
							<div align="center">
								<input type="button" value="+" onclick="insertRow('Doc')"  class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<s:iterator id="Doc" value="docList" status="status1">
					<s:if test="%{#status1.count % 2} == 0">
						out.print("<tr class=oddrow>");
					</s:if>
					<s:else>
						out.print("<tr class=oddrow>");
					</s:else>
					<td class="input">
						<input name="prpLdocDocCode" class="codecode" style="width:100%" maxlength=3 value="<bean:write name='Doc' property='docCode'/>"
							ondblclick="code_CodeSelect(this, 'DocCode');"
							onkeyup= "code_CodeSelect(this, 'DocCode');">
					</td>
					<td class="input">
						<input name="prpLdocDocName" class="codename" value="<bean:write name='Doc' property='docName'/>"
						ondblclick="code_CodeSelect(this, 'DocCode','-1','always','none','post');"
						onkeyup= "code_CodeSelect(this, 'DocCode','-1','always','none','post');">
					</td>
					<td class="input">
						<input name="prpLdocDocCount" class="common" style="width:100%" value="<bean:write name='Doc' property='docCount'/>">
					</td>
					<td class="input" colspan="2">
						<input name="prpLdocSignInDate" class="common" style="width:100%" description="<s:text name='db.prpLdoc.signInDate'/>" value="<bean:write name='Doc' property='signInDate'/>">
					</td>
					<td class="input" style='width:4%' colspan="2" align="center">
						<div>
							<input type=button name="buttonDocDelete"  class="smallbutton" onclick="deleteRow(this,'Doc')" value="-" style="cursor: hand">
							<input type="hidden" name="prpLdocFlag">
						</div>
					 </td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</span>
		</td>
	</tr>
</table>